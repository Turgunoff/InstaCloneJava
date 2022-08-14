package com.uz.instaclonejava.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.uz.instaclonejava.R;
import com.uz.instaclonejava.manager.AuthManager;
import com.uz.instaclonejava.manager.handler.AuthHandler;

/**
 * This is the login page. You can access here by email and password
 */
public class LogInActivity extends BaseActivity {
    Button logIn;
    TextView sign_up;
    EditText et_email;
    EditText et_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        logIn = findViewById(R.id.logIn);
        et_email = findViewById(R.id.et_email);
        et_password = findViewById(R.id.et_password);
        sign_up = findViewById(R.id.sign_up);

        logIn.setOnClickListener(view -> {
            String email = et_email.getText().toString().trim();
            String password = et_password.getText().toString().trim();

            if (!email.isEmpty() && !password.isEmpty())
                firebaseSignIn(email, password);
        });

        sign_up.setOnClickListener(view -> {
            callRegistrationActivity(context);
        });
    }

    private void firebaseSignIn(String email, String password) {
        showLoading(this);
        AuthManager.signIn(email, password, new AuthHandler() {
            @Override
            public void onSuccess(String uid) {
                dissmisLoading();
                Toast.makeText(context, getString(R.string.str_successfully), Toast.LENGTH_SHORT).show();
                callMainActivity(context);
            }

            @Override
            public void onError(Exception exception) {
                dissmisLoading();
                Toast.makeText(context, getString(R.string.str_log_in_failed), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
