package com.sidert.sidertmovil.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SynchronizeBD {

    @SerializedName("asesorid")
    @Expose
    private String asesorid;

    @SerializedName("errores")
    @Expose
    private Object errores;

    @SerializedName("externalid")
    @Expose
    private String externalid;

    @SerializedName("folio")
    @Expose
    private String folio;

    @SerializedName("generated_at")
    @Expose
    private String generatedAt;

    @SerializedName("monto_realizado")
    @Expose
    private float montoRealizado;

    @SerializedName("sended_at")
    @Expose
    private String sendedAt;

    @SerializedName("tipo")
    @Expose
    private String tipo;

    public String getAsesorid() {
            return asesorid;
        }

    public void setAsesorid(String asesorid) {
            this.asesorid = asesorid;
        }

    public Object getErrores() {
            return errores;
        }

    public void setErrores(Object errores) {
            this.errores = errores;
        }

    public String getExternalid() {
            return externalid;
        }

    public void setExternalid(String externalid) {
            this.externalid = externalid;
        }

    public String getFolio() {
            return folio;
        }

    public void setFolio(String folio) {
            this.folio = folio;
        }

    public String getGeneratedAt() {
            return generatedAt;
        }

    public void setGeneratedAt(String generatedAt) {
            this.generatedAt = generatedAt;
        }

    public float getMontoRealizado() {
            return montoRealizado;
        }

    public void setMontoRealizado(float montoRealizado) {
            this.montoRealizado = montoRealizado;
        }

    public String getSendedAt() {
            return sendedAt;
        }

    public void setSendedAt(String sendedAt) {
            this.sendedAt = sendedAt;
        }

    public String getTipo() {
            return tipo;
        }

    public void setTipo(String tipo) {
            this.tipo = tipo;
        }
}


