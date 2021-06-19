package com.altoque.delivery.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class ConnectivityReceiver extends BroadcastReceiver {

    public static ConnectivityReciverListener connectivityReceiverListener;

    public ConnectivityReceiver() {
        super();
    }


    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        if (connectivityReceiverListener != null) {
            connectivityReceiverListener.onNetworkConnectionChanger(isConnected);
        }
    }

    public static boolean isConnected() {
        ConnectivityManager cm = (ConnectivityManager)
                initClass.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activateNetwork = cm.getActiveNetworkInfo();
        return activateNetwork != null && activateNetwork.isConnectedOrConnecting();
    }

    public interface ConnectivityReciverListener {
        void onNetworkConnectionChanger(boolean isConnected);
    }
}
