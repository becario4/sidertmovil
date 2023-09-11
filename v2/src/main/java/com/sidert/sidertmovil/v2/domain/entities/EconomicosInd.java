package com.sidert.sidertmovil.v2.domain.entities;

import java.io.Serializable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tbl_economicos_ind")
public final class EconomicosInd implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_economico")
    private Long idEconomico;
    @ColumnInfo(name = "id_solicitud")
    private Integer idSolicitud;
    @ColumnInfo(name = "propiedades")
    private String propiedades;
    @ColumnInfo(name = "valor_aproximado")
    private String valorAproximado;
    @ColumnInfo(name = "ubicacion")
    private String ubicacion;
    @ColumnInfo(name = "ingreso")
    private String ingreso;
    @ColumnInfo(name = "estatus_completado")
    private Integer estatusCompletado;


// Getters & Setters

    public Long getIdEconomico(){
        return this.idEconomico;
    }

    public void setIdEconomico(Long idEconomico){
        this.idEconomico = idEconomico;
    }
    public Integer getIdSolicitud(){
        return this.idSolicitud;
    }

    public void setIdSolicitud(Integer idSolicitud){
        this.idSolicitud = idSolicitud;
    }
    public String getPropiedades(){
        return this.propiedades;
    }

    public void setPropiedades(String propiedades){
        this.propiedades = propiedades;
    }
    public String getValorAproximado(){
        return this.valorAproximado;
    }

    public void setValorAproximado(String valorAproximado){
        this.valorAproximado = valorAproximado;
    }
    public String getUbicacion(){
        return this.ubicacion;
    }

    public void setUbicacion(String ubicacion){
        this.ubicacion = ubicacion;
    }
    public String getIngreso(){
        return this.ingreso;
    }

    public void setIngreso(String ingreso){
        this.ingreso = ingreso;
    }
    public Integer getEstatusCompletado(){
        return this.estatusCompletado;
    }

    public void setEstatusCompletado(Integer estatusCompletado){
        this.estatusCompletado = estatusCompletado;
    }

}