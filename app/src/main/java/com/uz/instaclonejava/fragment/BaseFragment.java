package com.uz.instaclonejava.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.uz.instaclonejava.R;
import com.uz.instaclonejava.activity.LogInActivity;

public class BaseFragment extends Fragment {

    AlertDialog progressDialog;

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

    @Override
    public void onDestroy() {
        super.onDestroy();
        dissmisLoading();
    }
}
