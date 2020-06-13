package com.sidert.sidertmovil.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MCodigoOxxo implements Serializable {

    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("usuarioId")
    @Expose
    private Integer usuarioId;

    @SerializedName("numPrestamo")
    @Expose
    private String numPrestamo;

    @SerializedName("fechaAmortizacion")
    @Expose
    private String fechaAmortizacion;

    @SerializedName("montoAmortizacion")
    @Expose
    private Double montoAmortizacion;

    @SerializedName("nombrePdf")
    @Expose
    private String nombrePdf;

    @SerializedName("enabled")
    @Expose
    private Boolean enabled;

    @SerializedName("createdAt")
    @Expose
    private String createdAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Integer usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getNumPrestamo() {
        return numPrestamo;
    }

    public void setNumPrestamo(String numPrestamo) {
        this.numPrestamo = numPrestamo;
    }

    public String getFechaAmortizacion() {
        return fechaAmortizacion;
    }

    public void setFechaAmortizacion(String fechaAmortizacion) {
        this.fechaAmortizacion = fechaAmortizacion;
    }

    public Double getMontoAmortizacion() {
        return montoAmortizacion;
    }

    public void setMontoAmortizacion(Double montoAmortizacion) {
        this.montoAmortizacion = montoAmortizacion;
    }

    public String getNombrePdf() {
        return nombrePdf;
    }

    public void setNombrePdf(String nombrePdf) {
        this.nombrePdf = nombrePdf;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
