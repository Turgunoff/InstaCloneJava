package com.uz.instaclonejava.utils;

import android.app.Dialog;
import android.content.Context;
import android.provider.Settings;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.uz.instaclonejava.R;

public class Utils {

    public static String getDeviceID(Context context) {
        String device_id = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        return device_id;
    }

    public static void dialogDouble(Context context, String title, DialogListener callback) {
        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.view_dialog_double);
        dialog.setCanceledOnTouchOutside(true);
        TextView d_title = dialog.findViewById(R.id.d_title);
        TextView d_confirm = dialog.findViewById(R.id.d_confirm);
        TextView d_cancel = dialog.findViewById(R.id.d_cancel);
        d_title.setText(title);
        d_confirm.setOnClickListener(view -> {
            dialog.dismiss();
            callback.onCallback(true);
        });
        d_cancel.setOnClickListener(view -> {
            dialog.dismiss();
            callback.onCallback(false);
        });
        dialog.show();
    }

    public void dialogSingle(Context context, String title, DialogListener callback) {
        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.view_dialog_single);
        dialog.setCanceledOnTouchOutside(true);
        TextView d_title = dialog.findViewById(R.id.d_title);
        TextView d_confirm = dialog.findViewById(R.id.d_confirm);
        d_title.setText(title);
        d_confirm.setOnClickListener(view -> {
            dialog.dismiss();
            callback.onCallback(true);
        });
        dialog.show();
    }

    public interface DialogListener {
        void onCallback(Boolean isChosen);
    }
}
