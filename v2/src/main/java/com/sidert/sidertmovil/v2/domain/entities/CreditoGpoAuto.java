package com.sidert.sidertmovil.v2.domain.entities;

import java.io.Serializable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tbl_credito_gpo_auto")
public final class CreditoGpoAuto implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private Long remoteId;
    @ColumnInfo(name = "id_solicitud")
    private Integer idSolicitud;
    @ColumnInfo(name = "nombre_grupo")
    private String nombreGrupo;
    @ColumnInfo(name = "plazo")
    private String plazo;
    @ColumnInfo(name = "periodicidad")
    private String periodicidad;
    @ColumnInfo(name = "fecha_desembolso")
    private String fechaDesembolso;
    @ColumnInfo(name = "dia_desembolso")
    private String diaDesembolso;
    @ColumnInfo(name = "hora_visita")
    private String horaVisita;
    @ColumnInfo(name = "estatus_completado")
    private Integer estatusCompletado;
    @ColumnInfo(name = "observaciones")
    private String observaciones;
    @ColumnInfo(name = "ciclo")
    private String ciclo;
    @ColumnInfo(name = "grupo_id")
    private String grupoId;


// Getters & Setters

    public Long getRemoteId(){
        return this.remoteId;
    }

    public void setRemoteId(Long remoteId){
        this.remoteId = remoteId;
    }
    public Integer getIdSolicitud(){
        return this.idSolicitud;
    }

    public void setIdSolicitud(Integer idSolicitud){
        this.idSolicitud = idSolicitud;
    }
    public String getNombreGrupo(){
        return this.nombreGrupo;
    }

    public void setNombreGrupo(String nombreGrupo){
        this.nombreGrupo = nombreGrupo;
    }
    public String getPlazo(){
        return this.plazo;
    }

    public void setPlazo(String plazo){
        this.plazo = plazo;
    }
    public String getPeriodicidad(){
        return this.periodicidad;
    }

    public void setPeriodicidad(String periodicidad){
        this.periodicidad = periodicidad;
    }
    public String getFechaDesembolso(){
        return this.fechaDesembolso;
    }

    public void setFechaDesembolso(String fechaDesembolso){
        this.fechaDesembolso = fechaDesembolso;
    }
    public String getDiaDesembolso(){
        return this.diaDesembolso;
    }

    public void setDiaDesembolso(String diaDesembolso){
        this.diaDesembolso = diaDesembolso;
    }
    public String getHoraVisita(){
        return this.horaVisita;
    }

    public void setHoraVisita(String horaVisita){
        this.horaVisita = horaVisita;
    }
    public Integer getEstatusCompletado(){
        return this.estatusCompletado;
    }

    public void setEstatusCompletado(Integer estatusCompletado){
        this.estatusCompletado = estatusCompletado;
    }
    public String getObservaciones(){
        return this.observaciones;
    }

    public void setObservaciones(String observaciones){
        this.observaciones = observaciones;
    }
    public String getCiclo(){
        return this.ciclo;
    }

    public void setCiclo(String ciclo){
        this.ciclo = ciclo;
    }
    public String getGrupoId(){
        return this.grupoId;
    }

    public void setGrupoId(String grupoId){
        this.grupoId = grupoId;
    }

}