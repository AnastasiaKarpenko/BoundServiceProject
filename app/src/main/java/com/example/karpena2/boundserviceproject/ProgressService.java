package com.example.karpena2.boundserviceproject;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class ProgressService extends Service {
    public static final String TAG = ProgressService.class.getSimpleName();
    private IBinder myBinder = new MyBinder();



    public ProgressService() {
    }

    @Override
    public void onCreate() {

        Log.d(TAG, "onCreate called");

    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind done");
        return myBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return false;
    }

    public class MyBinder extends Binder {

        ProgressService getservice() {
            return ProgressService.this;
        }
    }


}
