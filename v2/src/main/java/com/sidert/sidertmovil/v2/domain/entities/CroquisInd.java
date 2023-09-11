package com.sidert.sidertmovil.v2.domain.entities;

import java.io.Serializable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tbl_croquis_ind")
public final class CroquisInd implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private Long remoteId;
    @ColumnInfo(name = "id_solicitud")
    private Integer idSolicitud;
    @ColumnInfo(name = "calle_principal")
    private String callePrincipal;
    @ColumnInfo(name = "lateral_uno")
    private String lateralUno;
    @ColumnInfo(name = "lateral_dos")
    private String lateralDos;
    @ColumnInfo(name = "calle_trasera")
    private String calleTrasera;
    @ColumnInfo(name = "referencias")
    private String referencias;
    @ColumnInfo(name = "estatus_completado")
    private Integer estatusCompletado;
    @ColumnInfo(name = "comentario_rechazo")
    private String comentarioRechazo;
    @ColumnInfo(name = "caracteristicas_domicilio",defaultValue="''")
    private String caracteristicasDomicilio;


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
    public String getCallePrincipal(){
        return this.callePrincipal;
    }

    public void setCallePrincipal(String callePrincipal){
        this.callePrincipal = callePrincipal;
    }
    public String getLateralUno(){
        return this.lateralUno;
    }

    public void setLateralUno(String lateralUno){
        this.lateralUno = lateralUno;
    }
    public String getLateralDos(){
        return this.lateralDos;
    }

    public void setLateralDos(String lateralDos){
        this.lateralDos = lateralDos;
    }
    public String getCalleTrasera(){
        return this.calleTrasera;
    }

    public void setCalleTrasera(String calleTrasera){
        this.calleTrasera = calleTrasera;
    }
    public String getReferencias(){
        return this.referencias;
    }

    public void setReferencias(String referencias){
        this.referencias = referencias;
    }
    public Integer getEstatusCompletado(){
        return this.estatusCompletado;
    }

    public void setEstatusCompletado(Integer estatusCompletado){
        this.estatusCompletado = estatusCompletado;
    }
    public String getComentarioRechazo(){
        return this.comentarioRechazo;
    }

    public void setComentarioRechazo(String comentarioRechazo){
        this.comentarioRechazo = comentarioRechazo;
    }
    public String getCaracteristicasDomicilio(){
        return this.caracteristicasDomicilio;
    }

    public void setCaracteristicasDomicilio(String caracteristicasDomicilio){
        this.caracteristicasDomicilio = caracteristicasDomicilio;
    }

}