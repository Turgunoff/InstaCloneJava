package com.uz.instaclonejava.utils;

import android.app.Application;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.uz.instaclonejava.model.ScreenSize;

public class Utils {

    public static ScreenSize screenSize(Application context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        int deviceWidth = displayMetrics.widthPixels;
        int deviceHeight = displayMetrics.heightPixels;
        return new ScreenSize(deviceWidth, deviceHeight);

    }
}
