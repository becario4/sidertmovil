package com.sidert.sidertmovil.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

public class MSendRecibo implements Serializable {

    @SerializedName("grupo_id")
    @Expose
    private Long grupoId;

    @SerializedName("num_solicitud")
    @Expose
    private Long numSolicitud;

    @SerializedName("monto")
    @Expose
    private String monto;

    @SerializedName("folio")
    @Expose
    private Long folio;

    @SerializedName("tipo_recibo")
    @Expose
    private String tipoRecibo;

    @SerializedName("tipo_impresion")
    @Expose
    private String tipoImpresion;

    @SerializedName("fecha_impresion")
    @Expose
    private String fechaImpresion;

    @SerializedName("fecha_envio")
    @Expose
    private String fechaEnvio;

    @SerializedName("usuarioId")
    @Expose
    private Long usuarioId;

    @SerializedName("nombre")
    @Expose
    private String nombre;

    public Long getGrupoId() {
        return grupoId;
    }

    public void setGrupoId(Long grupoId) {
        this.grupoId = grupoId;
    }

    public Long getNumSolicitud() {
        return numSolicitud;
    }

    public void setNumSolicitud(Long numSolicitud) {
        this.numSolicitud = numSolicitud;
    }

    public String getMonto() {
        return monto;
    }

    public void setMonto(String monto) {
        this.monto = monto;
    }

    public Long getFolio() {
        return folio;
    }

    public void setFolio(Long folio) {
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

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
