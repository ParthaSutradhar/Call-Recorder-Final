package com.xemosoft.call_recorder.utility;

import android.app.ActivityManager;
import android.content.Context;
import android.content.SharedPreferences;

import com.xemosoft.call_recorder.R;

import java.util.List;

public class Utility {

    public static boolean setRecorderState(boolean state, Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getString(R.string.recorder_state),context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(context.getString(R.string.state),state);
        return editor.commit();
    }

    public static boolean getRecorderState(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getString(R.string.recorder_state),context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(context.getString(R.string.state),false);
    }
}
