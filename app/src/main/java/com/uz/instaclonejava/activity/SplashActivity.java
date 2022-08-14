package com.uz.instaclonejava.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.WindowManager;

import com.uz.instaclonejava.R;
import com.uz.instaclonejava.manager.AuthManager;

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
        setContentView(R.layout.activity_splash);

        countDownTimer();
    }

    private void countDownTimer() {
        new CountDownTimer(1000, 1000) {

            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                if (AuthManager.isSignedIn()) {
                    callMainActivity(context);
                } else {
                    callLogInActivity(context);
                }
            }
        }.start();
    }
}