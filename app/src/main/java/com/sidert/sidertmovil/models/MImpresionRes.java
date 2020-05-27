package com.sidert.sidertmovil.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MImpresionRes implements Serializable {

    @SerializedName("tipoCartera")
    @Expose
    private int tipoCartera;
    @SerializedName("asesorid")
    @Expose
    private String asesorid;
    @SerializedName("external_id")
    @Expose
    private String externalId;
    @SerializedName("folio")
    @Expose
    private String folio;
    @SerializedName("tipo")
    @Expose
    private String tipo;
    @SerializedName("monto_realizado")
    @Expose
    private Double montoRealizado;
    @SerializedName("errores")
    @Expose
    private String errores;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("sended_at")
    @Expose
    private String sendedAt;
    @SerializedName("generated_at")
    @Expose
    private String generatedAt;
    @SerializedName("clavecliente")
    @Expose
    private String clavecliente;
    @SerializedName("num_prestamo_id_gestion")
    @Expose
    private String numPrestamoIdGestion;

    public int getTipoCartera() {
        return tipoCartera;
    }

    public void setTipoCartera(int tipoCartera) {
        this.tipoCartera = tipoCartera;
    }

    public String getAsesorid() {
        return asesorid;
    }

    public void setAsesorid(String asesorid) {
        this.asesorid = asesorid;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Double getMontoRealizado() {
        return montoRealizado;
    }

    public void setMontoRealizado(Double montoRealizado) {
        this.montoRealizado = montoRealizado;
    }

    public String getErrores() {
        return errores;
    }

    public void setErrores(String errores) {
        this.errores = errores;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getSendedAt() {
        return sendedAt;
    }

    public void setSendedAt(String sendedAt) {
        this.sendedAt = sendedAt;
    }

    public String getGeneratedAt() {
        return generatedAt;
    }

    public void setGeneratedAt(String generatedAt) {
        this.generatedAt = generatedAt;
    }

    public String getClavecliente() {
        return clavecliente;
    }

    public void setClavecliente(String clavecliente) {
        this.clavecliente = clavecliente;
    }

    public String getNumPrestamoIdGestion() {
        return numPrestamoIdGestion;
    }

    public void setNumPrestamoIdGestion(String numPrestamoIdGestion) {
        this.numPrestamoIdGestion = numPrestamoIdGestion;
    }
}
