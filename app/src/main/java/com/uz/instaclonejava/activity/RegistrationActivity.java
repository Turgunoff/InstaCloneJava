package com.uz.instaclonejava.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.uz.instaclonejava.R;

/**
 * This is the Registration page. Here you can register using FullName, Email and Password
 */
public class RegistrationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
    }
}