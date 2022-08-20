package com.uz.instaclonejava.manager;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefsManager {

    static SharedPreferences preferences;

    public PrefsManager(Context context) {
        preferences = context.getSharedPreferences("insta_db", Context.MODE_PRIVATE);
    }

    public void storeDeviceToken(String token) {
        SharedPreferences.Editor prefsEditor = preferences.edit();
        prefsEditor.putString("device_token", token);
        prefsEditor.apply();
    }

    public static String loadDeviceToken() {
        return preferences.getString("device_token", "");
    }
}
