package com.example.karpena2.boundserviceproject;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {
    public static final int HALF_PROGRESS = 50;
    public static final int ZERO_PROGRESS = 0;
    private ProgressBar mProgressBar;
    private Button mButton;
    private ProgressService mProgressService;
    boolean isBound = false;

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            ProgressService.MyBinder binder = (ProgressService.MyBinder) iBinder;
            mProgressService = binder.getservice();
            isBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mProgressService = null;
            isBound = false;

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(this, ProgressService.class);
        bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);

        mProgressBar = findViewById(R.id.progress_bar);
        mProgressBar.setVisibility(View.VISIBLE);

        mButton = findViewById(R.id.button);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeProgress();
            }
        });



    }

    private void changeProgress() {
        // This method will change the current progress bar progress value on 50%. But not till less than 0%
        int currentProgress = mProgressBar.getProgress();

        if (currentProgress >= HALF_PROGRESS) {
            mProgressBar.setProgress(currentProgress - HALF_PROGRESS);
        } else {
            mProgressBar.setProgress(ZERO_PROGRESS);
        }
    }


}
