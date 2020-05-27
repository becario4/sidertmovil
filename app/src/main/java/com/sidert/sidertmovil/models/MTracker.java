package com.sidert.sidertmovil.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MTracker implements Serializable {

    @SerializedName("device_id")
    @Expose
    private int deviceId;

    @SerializedName("asesor_id")
    @Expose
    private String asesorId;

    @SerializedName("serie_id")
    @Expose
    private int serieId;

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

    @SerializedName("sended_at")
    @Expose
    private String sendedAt;

    public void setDeviceId(int deviceId){
        this.deviceId = deviceId;
    }

    public int getDeviceId(){
        return deviceId;
    }

    public String getAsesorId() {
        return asesorId;
    }

    public void setAsesorId(String asesorId) {
        this.asesorId = asesorId;
    }

    public int getSerieId() {
        return serieId;
    }

    public void setSerieId(int serieId) {
        this.serieId = serieId;
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

    public String getSendedAt() {
        return sendedAt;
    }

    public void setSendedAt(String sendedAt) {
        this.sendedAt = sendedAt;
    }
}
