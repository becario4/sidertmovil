package com.sidert.sidertmovil.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MTrackerAsesor implements Serializable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("asesor_nombre")
    @Expose
    private String asesorNombre;
    @SerializedName("latitud")
    @Expose
    private Double latitud;
    @SerializedName("longitud")
    @Expose
    private Double longitud;
    @SerializedName("bateria")
    @Expose
    private Double bateria;
    @SerializedName("generated_at")
    @Expose
    private String generatedAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAsesorNombre() {
        return asesorNombre;
    }

    public void setAsesorNombre(String asesorNombre) {
        this.asesorNombre = asesorNombre;
    }

    public Double getLatitud() {
        return latitud;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    public Double getLongitud() {
        return longitud;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }

    public Double getBateria() {
        return bateria;
    }

    public void setBateria(Double bateria) {
        this.bateria = bateria;
    }

    public String getGeneratedAt() {
        return generatedAt;
    }

    public void setGeneratedAt(String generatedAt) {
        this.generatedAt = generatedAt;
    }
}
