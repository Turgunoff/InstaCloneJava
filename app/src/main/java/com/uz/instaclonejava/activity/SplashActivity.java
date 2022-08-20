package com.uz.instaclonejava.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.WindowManager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.util.Logger;
import com.google.firebase.messaging.FirebaseMessaging;
import com.uz.instaclonejava.R;
import com.uz.instaclonejava.manager.AuthManager;
import com.uz.instaclonejava.manager.PrefsManager;

public class SplashActivity extends BaseActivity {
    String TAG = String.valueOf(SplashActivity.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
        setContentView(R.layout.activity_splash);

        countDownTimer();
        loadFCMToke();
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

    public void loadFCMToke() {
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                if (!task.isSuccessful()) {
                    Log.d("@@@", "FCMregistration failed");
                    return;
                }

                String token = task.getResult();
                Log.d("@@@", token);
                new PrefsManager(SplashActivity.this).storeDeviceToken(token);
            }
        });
    }
}