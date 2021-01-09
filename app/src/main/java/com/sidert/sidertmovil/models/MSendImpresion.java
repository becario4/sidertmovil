package com.sidert.sidertmovil.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MSendImpresion implements Serializable {

    @SerializedName("asesorid")
    @Expose
    private String asesorid;
    @SerializedName("clavecliente")
    @Expose
    private String clavecliente;
    @SerializedName("errores")
    @Expose
    private String errores;
    @SerializedName("external_id")
    @Expose
    private String externalId;
    @SerializedName("folio")
    @Expose
    private String folio;
    @SerializedName("generated_at")
    @Expose
    private String generatedAt;
    @SerializedName("monto_realizado")
    @Expose
    private Double montoRealizado;
    @SerializedName("num_prestamo_id_gestion")
    @Expose
    private String numPrestamoIdGestion;
    @SerializedName("sended_at")
    @Expose
    private String sendedAt;
    @SerializedName("tipo")
    @Expose
    private String tipo;
    @SerializedName("tipoCartera")
    @Expose
    private Integer tipoCartera;
    @SerializedName("celular")
    @Expose
    private String celular;

    public String getAsesorid() {
        return asesorid;
    }

    public void setAsesorid(String asesorid) {
        this.asesorid = asesorid;
    }

    public String getClavecliente() {
        return clavecliente;
    }

    public void setClavecliente(String clavecliente) {
        this.clavecliente = clavecliente;
    }

    public String getErrores() {
        return errores;
    }

    public void setErrores(String errores) {
        this.errores = errores;
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

    public String getGeneratedAt() {
        return generatedAt;
    }

    public void setGeneratedAt(String generatedAt) {
        this.generatedAt = generatedAt;
    }

    public Double getMontoRealizado() {
        return montoRealizado;
    }

    public void setMontoRealizado(Double montoRealizado) {
        this.montoRealizado = montoRealizado;
    }

    public String getNumPrestamoIdGestion() {
        return numPrestamoIdGestion;
    }

    public void setNumPrestamoIdGestion(String numPrestamoIdGestion) {
        this.numPrestamoIdGestion = numPrestamoIdGestion;
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

    public Integer getTipoCartera() {
        return tipoCartera;
    }

    public void setTipoCartera(Integer tipoCartera) {
        this.tipoCartera = tipoCartera;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }
}
