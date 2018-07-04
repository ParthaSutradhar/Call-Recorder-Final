package com.xemosoft.call_recorder.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.xemosoft.call_recorder.activity.MainActivity;
import com.xemosoft.call_recorder.utility.Utility;

public class CallStatusReceiver extends BroadcastReceiver {

    private static final String TAG = CallStatusReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        if (MainActivity.isRunning) {
            Log.d(TAG,"Running Change UI");
            return;
        }
        Log.d(TAG,"Running Dont UI");
    }
}
