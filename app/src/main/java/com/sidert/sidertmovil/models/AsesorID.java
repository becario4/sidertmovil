package com.sidert.sidertmovil.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AsesorID {

    @SerializedName("asesorid")
    @Expose
    private String asesorid;

    public AsesorID(String asesorid) {
        this.asesorid = asesorid;
    }

}
