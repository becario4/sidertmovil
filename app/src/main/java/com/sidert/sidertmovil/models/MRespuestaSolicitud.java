package com.sidert.sidertmovil.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MRespuestaSolicitud implements Serializable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("idRespuesta")
    @Expose
    private Integer idRespuesta;
    @SerializedName("comentario")
    @Expose
    private String comentario;
    @SerializedName("estatus")
    @Expose
    private String estatus;
    @SerializedName("tipoGestion")
    @Expose
    private Integer tipoGestion;
    @SerializedName("fechaAplicacion")
    @Expose
    private String fechaAplicacion;
    @SerializedName("tipoPrestamo")
    @Expose
    private String tipoPrestamo;
    @SerializedName("prestamoId")
    @Expose
    private String prestamoId;
    @SerializedName("pagoRealizado")
    @Expose
    private String pagoRealizado;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdRespuesta() {
        return idRespuesta;
    }

    public void setIdRespuesta(Integer idRespuesta) {
        this.idRespuesta = idRespuesta;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    public Integer getTipoGestion() {
        return tipoGestion;
    }

    public void setTipoGestion(Integer tipoGestion) {
        this.tipoGestion = tipoGestion;
    }

    public String getFechaAplicacion() {
        return fechaAplicacion;
    }

    public void setFechaAplicacion(String fechaAplicacion) {
        this.fechaAplicacion = fechaAplicacion;
    }

    public String getTipoPrestamo() {
        return tipoPrestamo;
    }

    public void setTipoPrestamo(String tipoPrestamo) {
        this.tipoPrestamo = tipoPrestamo;
    }

    public String getPrestamoId() {
        return prestamoId;
    }

    public void setPrestamoId(String prestamoId) {
        this.prestamoId = prestamoId;
    }

    public String getPagoRealizado() {
        return pagoRealizado;
    }

    public void setPagoRealizado(String pagoRealizado) {
        this.pagoRealizado = pagoRealizado;
    }
}
