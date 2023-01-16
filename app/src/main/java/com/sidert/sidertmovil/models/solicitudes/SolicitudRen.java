package com.sidert.sidertmovil.models.solicitudes;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SolicitudRen implements Serializable {
    public final static String TBL                         = "tbl_solicitudes_ren";

    @SerializedName("id_solicitud")
    @Expose
    private Integer idSolicitud;

    @SerializedName("vol_solicitud")
    @Expose
    private String volSolicitud;

    @SerializedName("usuario_id")
    @Expose
    private Integer usuarioId;

    @SerializedName("tipo_solicitud")
    @Expose
    private Integer tipoSolicitud;

    @SerializedName("id_originacion")
    @Expose
    private Integer idOriginacion;

    @SerializedName("nombre")
    @Expose
    private String nombre;

    @SerializedName("fecha_inicio")
    @Expose
    private String fechaInicio;

    @SerializedName("fecha_termino")
    @Expose
    private String fechaTermino;

    @SerializedName("fecha_envio")
    @Expose
    private String fechaEnvio;

    @SerializedName("fecha_dispositivo")
    @Expose
    private String fechaDispositivo;

    @SerializedName("fecha_guardado")
    @Expose
    private String fechaGuardado;

    @SerializedName("estatus")
    @Expose
    private Integer estatus;

    @SerializedName("prestamo_id")
    @Expose
    private Integer prestamoId;

    public Integer getIdSolicitud() {
        return idSolicitud;
    }

    public void setIdSolicitud(Integer idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    public String getVolSolicitud() {
        return volSolicitud;
    }

    public void setVolSolicitud(String volSolicitud) {
        this.volSolicitud = volSolicitud;
    }

    public Integer getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Integer usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Integer getTipoSolicitud() {
        return tipoSolicitud;
    }

    public void setTipoSolicitud(Integer tipoSolicitud) {
        this.tipoSolicitud = tipoSolicitud;
    }

    public Integer getIdOriginacion() {
        return idOriginacion;
    }

    public void setIdOriginacion(Integer idOriginacion) {
        this.idOriginacion = idOriginacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaTermino() {
        return fechaTermino;
    }

    public void setFechaTermino(String fechaTermino) {
        this.fechaTermino = fechaTermino;
    }

    public String getFechaEnvio() {
        return fechaEnvio;
    }

    public void setFechaEnvio(String fechaEnvio) {
        this.fechaEnvio = fechaEnvio;
    }

    public String getFechaDispositivo() {
        return fechaDispositivo;
    }

    public void setFechaDispositivo(String fechaDispositivo) {
        this.fechaDispositivo = fechaDispositivo;
    }

    public String getFechaGuardado() {
        return fechaGuardado;
    }

    public void setFechaGuardado(String fechaGuardado) {
        this.fechaGuardado = fechaGuardado;
    }

    public Integer getEstatus() {
        return estatus;
    }

    public void setEstatus(Integer estatus) {
        this.estatus = estatus;
    }

    public Integer getPrestamoId() {
        return prestamoId;
    }

    public void setPrestamoId(Integer prestamoId) {
        this.prestamoId = prestamoId;
    }
}
