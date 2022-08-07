package com.uz.instaclonejava.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.uz.instaclonejava.R;

/**
 * This is the login page. You can access here by email and password
 */
public class LogInActivity extends BaseActivity {
    Button logIn;
    TextView sign_up;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        logIn = findViewById(R.id.logIn);
        sign_up = findViewById(R.id.sign_up);

        logIn.setOnClickListener(view -> {
            Intent intent = new Intent(LogInActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        sign_up.setOnClickListener(view -> {
            Intent intent = new Intent(LogInActivity.this, RegistrationActivity.class);
            startActivity(intent);
            finish();
        });
    }
}