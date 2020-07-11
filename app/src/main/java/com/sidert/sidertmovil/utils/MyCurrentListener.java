package com.sidert.sidertmovil.utils;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;

public class MyCurrentListener implements LocationListener {

    private evento event;
    public interface evento {
        void onComplete(String latitud, String longitud);
    }

    public MyCurrentListener(evento event) {
        this.event = event;
    }

    @Override
    public void onLocationChanged(Location location) {
        location.getLatitude();
        location.getLongitude();
        try {
            event.onComplete(String.valueOf(location.getLatitude()),String.valueOf(location.getLongitude()));
        }
        catch (Exception e){
            event.onComplete("0", "0");
        }

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

}