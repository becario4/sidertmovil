package com.sidert.sidertmovil.v2.domain.entities;

import java.io.Serializable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tbl_croquis_gpo")
public final class CroquisGpo implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private Long remoteId;
    @ColumnInfo(name = "id_integrante")
    private Integer idIntegrante;
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
    @ColumnInfo(name = "caracteristicas_domicilio",defaultValue="''")
    private String caracteristicasDomicilio;


// Getters & Setters

    public Long getRemoteId(){
        return this.remoteId;
    }

    public void setRemoteId(Long remoteId){
        this.remoteId = remoteId;
    }
    public Integer getIdIntegrante(){
        return this.idIntegrante;
    }

    public void setIdIntegrante(Integer idIntegrante){
        this.idIntegrante = idIntegrante;
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
    public String getCaracteristicasDomicilio(){
        return this.caracteristicasDomicilio;
    }

    public void setCaracteristicasDomicilio(String caracteristicasDomicilio){
        this.caracteristicasDomicilio = caracteristicasDomicilio;
    }

}