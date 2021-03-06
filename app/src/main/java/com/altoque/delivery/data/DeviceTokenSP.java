package com.altoque.delivery.data;

import android.content.Context;
import android.content.SharedPreferences;

public class DeviceTokenSP {
    private static final String SHARED_PREF_NAME = "FCMSharedPref";
    private static final String TAG_TOKEN = "tagtoken";
    private static DeviceTokenSP mInstance;
    private static Context context;

    private DeviceTokenSP(Context context) {
        DeviceTokenSP.context = context;
    }

    public static synchronized DeviceTokenSP getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new DeviceTokenSP(context);
        }
        return mInstance;
    }

    //this method will save the device token to shared preferences
    public boolean saveDeviceToken(String token) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(TAG_TOKEN, token);
        editor.apply();
        return true;
    }

    //this method will fetch the device token from shared preferences
    public String getDeviceToken() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(TAG_TOKEN, null);
    }

}
