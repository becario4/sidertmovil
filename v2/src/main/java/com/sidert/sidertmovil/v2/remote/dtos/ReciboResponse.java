package com.sidert.sidertmovil.v2.remote.dtos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ReciboResponse implements Serializable {
    private Integer id;

    @SerializedName("grupo_id")
    @Expose
    private String grupoId;

    @SerializedName("num_solicitud")
    @Expose
    private String numSolicitud;

    @SerializedName("monto")
    @Expose
    private String monto;

    @SerializedName("folio")
    @Expose
    private String folio;

    @SerializedName("tipo")
    @Expose
    private String tipoRecibo;

    @SerializedName("tipo_impresion")
    @Expose
    private String tipoImpresion;

    @SerializedName("fecha_impreso")
    @Expose
    private String fechaImpresion;

    @SerializedName("fecha_envio")
    @Expose
    private String fechaEnvio;

    @SerializedName("estatus")
    @Expose
    private Integer estatus;

    @SerializedName("nombre")
    @Expose
    private String nombre;

    @SerializedName("plazo")
    @Expose
    private Integer plazo;

    @SerializedName("num_integrantes")
    @Expose
    private Integer numIntegrantes;

    private String nombreFirma;

    private String impresiones;

    public String getImpresiones() {
        return impresiones;
    }

    public void setImpresiones(String impresiones) {
        this.impresiones = impresiones;
    }

    public String getNombreFirma() {
        return nombreFirma;
    }

    public void setNombreFirma(String nombreFirma) {
        this.nombreFirma = nombreFirma;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGrupoId() {
        return grupoId;
    }

    public void setGrupoId(String grupoId) {
        this.grupoId = grupoId;
    }

    public String getNumSolicitud() {
        return numSolicitud;
    }

    public void setNumSolicitud(String numSolicitud) {
        this.numSolicitud = numSolicitud;
    }

    public String getMonto() {
        return monto;
    }

    public void setMonto(String monto) {
        this.monto = monto;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public String getTipoRecibo() {
        return tipoRecibo;
    }

    public void setTipoRecibo(String tipoRecibo) {
        this.tipoRecibo = tipoRecibo;
    }

    public String getTipoImpresion() {
        return tipoImpresion;
    }

    public void setTipoImpresion(String tipoImpresion) {
        this.tipoImpresion = tipoImpresion;
    }

    public String getFechaImpresion() {
        return fechaImpresion;
    }

    public void setFechaImpresion(String fechaImpresion) {
        this.fechaImpresion = fechaImpresion;
    }

    public String getFechaEnvio() {
        return fechaEnvio;
    }

    public void setFechaEnvio(String fechaEnvio) {
        this.fechaEnvio = fechaEnvio;
    }

    public Integer getEstatus() {
        return estatus;
    }

    public void setEstatus(Integer estatus) {
        this.estatus = estatus;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getPlazo() {
        return plazo;
    }

    public void setPlazo(Integer plazo) {
        this.plazo = plazo;
    }

    public Integer getNumIntegrantes() {
        return numIntegrantes;
    }

    public void setNumIntegrantes(Integer numIntegrantes) {
        this.numIntegrantes = numIntegrantes;
    }

}
