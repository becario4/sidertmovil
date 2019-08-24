package com.sidert.sidertmovil.utils;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

public class MyCurrentListener implements LocationListener {

    private String lati, longi;
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
        String myLocation = "Latitude = " + location.getLatitude() + " Longitude = " + location.getLongitude();

        this.lati = ""+location.getLatitude();
        this.longi = ""+location.getLongitude();
        event.onComplete(String.valueOf(location.getLatitude()),String.valueOf(location.getLongitude()));
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