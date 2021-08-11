package com.altoque.delivery.utils;

import android.app.Application;
import android.content.Context;


public class initClass extends Application {
    private static initClass mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    public static synchronized initClass getInstance() {
        return mInstance;
    }

    public void setConnectivityListener(com.altoque.delivery.utils.ConnectivityReceiver.ConnectivityReciverListener listener) {
        com.altoque.delivery.utils.ConnectivityReceiver.connectivityReceiverListener = listener;
    }


}
