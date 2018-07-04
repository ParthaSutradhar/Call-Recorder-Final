package com.xemosoft.call_recorder.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import com.xemosoft.call_recorder.service.RecorderService;
import com.xemosoft.call_recorder.utility.Utility;

public class PhoneReceiver extends BroadcastReceiver {

    private static final String TAG = PhoneReceiver.class.getSimpleName();


    private static int lastState = TelephonyManager.CALL_STATE_IDLE;
    private static String savedNumber = null;
    private Intent serviceIntent = null;
    private TelephonyManager telephonyManager = null;

    @Override
    public void onReceive(final Context context, Intent intent) {

        serviceIntent = new Intent(context, RecorderService.class);
        telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

        if (intent.getAction().equals(Intent.ACTION_NEW_OUTGOING_CALL)) {
            savedNumber = intent.getExtras().getString(Intent.EXTRA_PHONE_NUMBER);
            telephonyManager.listen(new PhoneStateListener() {
                @Override
                public void onCallStateChanged(int state, String incomingNumber) {
                    switch (state) {
                        case TelephonyManager.CALL_STATE_RINGING:
                            break;
                        case TelephonyManager.CALL_STATE_OFFHOOK:
                            startRecorderService(context,savedNumber);
                            break;
                        case TelephonyManager.CALL_STATE_IDLE:
                            break;
                        default:
                            break;
                    }
                }
            }, PhoneStateListener.LISTEN_CALL_STATE);
        } else {
            String stateStr = intent.getExtras().getString(TelephonyManager.EXTRA_STATE);
            String number = intent.getExtras().getString(TelephonyManager.EXTRA_INCOMING_NUMBER);
            int state = 0;
            assert stateStr != null;
            if (stateStr.equals(TelephonyManager.EXTRA_STATE_IDLE)) {
                state = TelephonyManager.CALL_STATE_IDLE;
            } else if (stateStr.equals(TelephonyManager.EXTRA_STATE_OFFHOOK)) {
                state = TelephonyManager.CALL_STATE_OFFHOOK;
                startRecorderService(context,savedNumber);
            } else if (stateStr.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
                savedNumber = number;
                state = TelephonyManager.CALL_STATE_RINGING;
            }
            onCallStateChanged(context, savedNumber, state);
        }
    }

    private void onCallStateChanged(Context context, String number, int state) {
        if (lastState == state) {
            return;
        }
        switch (state) {
            case TelephonyManager.CALL_STATE_IDLE:
                Log.d(TAG, "Call State Is IDLE");
                stopRecorderService(context);
                break;
            case TelephonyManager.CALL_STATE_OFFHOOK:
                Log.d(TAG, "Call State Is ANSWERED");
                // Start A Recording Service
                startRecorderService(context, number);
                break;
            case TelephonyManager.CALL_STATE_RINGING:
                Log.d(TAG, "Call State Is Ringing");
                break;
        }
        lastState = state;
    }

    private void stopRecorderService(Context context) {
        if (Utility.getRecorderState(context)) {
            context.stopService(serviceIntent);
        }
    }


    private void startRecorderService(Context context, String number) {
        serviceIntent.putExtra("number", number);
        if (Utility.getRecorderState(context)) {
            context.startService(serviceIntent);
        } else {
            Toast.makeText(context, "Turn On Recorder", Toast.LENGTH_SHORT).show();
        }
    }
}
