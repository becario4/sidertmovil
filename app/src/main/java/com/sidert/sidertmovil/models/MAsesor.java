package com.sidert.sidertmovil.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MAsesor implements Serializable {

    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("nombre_asesor")
    @Expose
    private String nombreAsesor;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getNombreAsesor() {
        return nombreAsesor;
    }

    public void setNombreAsesor(String nombreAsesor) {
        this.nombreAsesor = nombreAsesor;
    }

    @Override
    public String toString() {
        return nombreAsesor;
    }
}
