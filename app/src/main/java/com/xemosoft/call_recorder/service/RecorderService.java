package com.xemosoft.call_recorder.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;

public class RecorderService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_NOT_STICKY;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

        Intent intent = new Intent();
        intent.setAction("android.intent.action.updatelist");
        sendBroadcast(intent);

    }
}
