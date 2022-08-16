package com.uz.instaclonejava.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.uz.instaclonejava.R;
import com.uz.instaclonejava.manager.AuthManager;
import com.uz.instaclonejava.manager.DBManager;
import com.uz.instaclonejava.manager.handler.AuthHandler;
import com.uz.instaclonejava.manager.handler.DBUserHandler;
import com.uz.instaclonejava.model.User;

/**
 * This is the Registration page. Here you can register using FullName, Email and Password
 */
public class RegistrationActivity extends BaseActivity {
    Button registration;
    EditText et_fullName;
    EditText et_email;
    EditText et_password;
    TextView sign_up;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        registration = findViewById(R.id.registration);
        et_fullName = findViewById(R.id.et_fullName);
        et_email = findViewById(R.id.et_email);
        et_password = findViewById(R.id.et_password);
        sign_up = findViewById(R.id.sign_up);
        registration.setOnClickListener(view -> {
            String fullName = et_fullName.getText().toString().trim();
            String email = et_email.getText().toString().trim();
            String password = et_password.getText().toString().trim();

            if (!fullName.isEmpty() && !email.isEmpty() && !password.isEmpty()) {
                User user = new User(fullName, email, password, "");
                firebaseSignUp(user);
            }
        });
        sign_up.setOnClickListener(view -> callLogInActivity(context));
    }

    private void firebaseSignUp(User user) {
        showLoading(this);
        AuthManager.signUp(user.getEmail(), user.getPassword(), new AuthHandler() {
            @Override
            public void onSuccess(String uid) {
                user.setUid(uid);
                Toast.makeText(context, getString(R.string.str_registration_success), Toast.LENGTH_SHORT).show();
                storeUserToDB(user);
            }

            @Override
            public void onError(Exception exception) {
                dissmisLoading();
                Toast.makeText(context, getString(R.string.str_registration_failed), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void storeUserToDB(User user) {
        DBManager.storeUser(user, new DBUserHandler() {
            @Override
            public void onSuccess(User user) {
                dissmisLoading();
                callMainActivity(context);
            }

            @Override
            public void onError(Exception e) {

            }
        });
    }
}