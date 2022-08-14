package com.uz.instaclonejava.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.uz.instaclonejava.R;

/**
 * It's Base Activity
 */
public class BaseActivity extends AppCompatActivity {
    Context context;
    AlertDialog progressDialog;

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        context = this;
    }

    void showLoading(Activity activity) {
        if (activity == null) return;

        if (progressDialog != null && progressDialog.isShowing()) {
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            LayoutInflater inflater = activity.getLayoutInflater();
            builder.setView(inflater.inflate(R.layout.custom_progress_dialog, null));
            builder.setCancelable(true);
            progressDialog = builder.create();
            progressDialog.show();
        }
    }

    protected void dissmisLoading() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    void callMainActivity(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        startActivity(intent);
        finish();
    }

    void callRegistrationActivity(Context context) {
        Intent intent = new Intent(context, RegistrationActivity.class);
        startActivity(intent);
        finish();
    }

    void callLogInActivity(Context context) {
        Intent intent = new Intent(context, LogInActivity.class);
        startActivity(intent);
        finish();
    }
}
