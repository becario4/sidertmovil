package com.sidert.sidertmovil.utils;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.sidert.sidertmovil.R;

public class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

    private LayoutInflater inflater;

    public CustomInfoWindowAdapter(LayoutInflater inflater){
        this.inflater = inflater;
    }

    @Override
    public View getInfoContents(final Marker m) {
        View v = inflater.inflate(R.layout.infowindow_marker, null);
        String[] info = m.getTitle().split("&");
        ((TextView)v.findViewById(R.id.tvFecha)).setText(info[0]);
        ((TextView)v.findViewById(R.id.tvBateria)).setText(info[1]+"%");
        return v;
    }

    @Override
    public View getInfoWindow(Marker m) {
        return null;
    }
}
