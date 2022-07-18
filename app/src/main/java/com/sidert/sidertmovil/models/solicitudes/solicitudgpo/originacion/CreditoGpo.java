package com.sidert.sidertmovil.models.solicitudes.solicitudgpo.originacion;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CreditoGpo implements Serializable {
    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("id_solicitud")
    @Expose
    private Integer idSolicitud;

    @SerializedName("nombre_grupo")
    @Expose
    private String nombreGrupo;

    @SerializedName("plazo")
    @Expose
    private String plazo;

    @SerializedName("periodicidad")
    @Expose
    private String periodicidad;

    @SerializedName("fecha_desembolso")
    @Expose
    private String fechaDesembolso;

    @SerializedName("dia_desembolso")
    @Expose
    private String disDesembolso;

    @SerializedName("hora_visita")
    @Expose
    private String horaVisita;

    @SerializedName("estatus_completado")
    @Expose
    private Integer estatusCompletado;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdSolicitud() {
        return idSolicitud;
    }

    public void setIdSolicitud(Integer idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    public String getNombreGrupo() {
        return nombreGrupo;
    }

    public void setNombreGrupo(String nombreGrupo) {
        this.nombreGrupo = nombreGrupo;
    }

    public String getPlazo() {
        return plazo;
    }

    public void setPlazo(String plazo) {
        this.plazo = plazo;
    }

    public String getPeriodicidad() {
        return periodicidad;
    }

    public void setPeriodicidad(String periodicidad) {
        this.periodicidad = periodicidad;
    }

    public String getFechaDesembolso() {
        return fechaDesembolso;
    }

    public void setFechaDesembolso(String fechaDesembolso) {
        this.fechaDesembolso = fechaDesembolso;
    }

    public String getDisDesembolso() {
        return disDesembolso;
    }

    public void setDisDesembolso(String disDesembolso) {
        this.disDesembolso = disDesembolso;
    }

    public String getHoraVisita() {
        return horaVisita;
    }

    public void setHoraVisita(String horaVisita) {
        this.horaVisita = horaVisita;
    }

    public Integer getEstatusCompletado() {
        return estatusCompletado;
    }

    public void setEstatusCompletado(Integer estatusCompletado) {
        this.estatusCompletado = estatusCompletado;
    }
}
