package com.sidert.sidertmovil.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build;

public class NetworkStatus {

    public static boolean haveNetworkConnection(Context ctx) {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }

    public static boolean haveWifi(Context ctx) {
        if(Build.VERSION.SDK_INT >= 23) {
            ConnectivityManager connectivityManager = ((ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE));
            boolean isWifiConnected = false;
            Network[] networks = connectivityManager.getAllNetworks();
            if (networks == null) {
                isWifiConnected = false;
            } else {
                for (Network network : networks) {
                    NetworkInfo info = connectivityManager.getNetworkInfo(network);
                    if (info != null && info.getType() == ConnectivityManager.TYPE_WIFI) {
                        if (info.isAvailable() && info.isConnected()) {
                            isWifiConnected = true;
                            break;
                        }
                    }
                }
            }
            return isWifiConnected;
        } else {
            boolean haveConnectedWifi = false;
            ConnectivityManager cm = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo[] netInfo = cm.getAllNetworkInfo();
            for (NetworkInfo ni : netInfo) {
                if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                    if (ni.isConnected())
                        haveConnectedWifi = true;
            }
            return haveConnectedWifi;
        }
    }
}
