package com.example.karpena2.boundserviceproject;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class ProgressService extends Service {
    public static final String TAG = ProgressService.class.getSimpleName();
    private IBinder myBinder = new MyBinder();
    private ScheduledExecutorService mScheduledExecutorService;
    private Integer mResult;


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

    public int getProgress() {

//        mScheduledExecutorService = Executors.newScheduledThreadPool(1);
//        ScheduledFuture<Integer> future = mScheduledExecutorService.schedule(new Callable<Integer>() {
//            @Override
//            public Integer call() throws Exception {
//                int currentProgress = 0;
//
//                currentProgress = currentProgress + 5;
//
//                return currentProgress;
//            }
//        }, 200, TimeUnit.MILLISECONDS);
//
//        try {
//            mResult = future.get();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }
//
//        return mResult;

        ProgressTaskCallable<Integer> taskCallable = new ProgressTaskCallable<>();
        IRunCallable<Integer> iRunCallable = new IRunCallable<>(taskCallable);
        ScheduledFuture future = mScheduledExecutorService.scheduleAtFixedRate(iRunCallable, 200, 200, TimeUnit.MILLISECONDS);

        try {
            mResult = (Integer) future.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return mResult;
    }

    class IRunCallable<Integer> implements Runnable {
        private Callable<Integer> mCallable;
        private java.lang.Integer mReturnedObject;
        private Exception mException = null;

        IRunCallable(Callable<Integer> callable) {
            mCallable = callable;
        }

        @Override
        public void run() {
            mReturnedObject = 0;
            mException = null;
            try {
                mReturnedObject = (java.lang.Integer) mCallable.call();
            } catch (Exception e) {
                mException = e;
            }
        }

        public Exception getException() {
            return mException;
        }

        public int get() {
            return mReturnedObject;
        }
    }

    class ProgressTaskCallable<Integer> implements Callable<java.lang.Integer> {

        @Override
        public java.lang.Integer call() throws Exception {
            int currentProgress = 0;

            currentProgress = currentProgress + 5;

            return currentProgress;
        }
    }


}
