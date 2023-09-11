package com.sidert.sidertmovil.v2.domain.entities;

import java.io.Serializable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tbl_datos_credito_campana")
public final class DatosCreditoCampana implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    private Long id;
    @ColumnInfo(name = "id_solicitud")
    private Integer idSolicitud;
    @ColumnInfo(name = "id_originacion")
    private Integer idOriginacion;
    @ColumnInfo(name = "id_campana")
    private Integer idCampana;
    @ColumnInfo(name = "tipo_campana")
    private String tipoCampana;
    @ColumnInfo(name = "nombre_refiero")
    private String nombreRefiero;


// Getters & Setters

    public Long getId(){
        return this.id;
    }

    public void setId(Long id){
        this.id = id;
    }
    public Integer getIdSolicitud(){
        return this.idSolicitud;
    }

    public void setIdSolicitud(Integer idSolicitud){
        this.idSolicitud = idSolicitud;
    }
    public Integer getIdOriginacion(){
        return this.idOriginacion;
    }

    public void setIdOriginacion(Integer idOriginacion){
        this.idOriginacion = idOriginacion;
    }
    public Integer getIdCampana(){
        return this.idCampana;
    }

    public void setIdCampana(Integer idCampana){
        this.idCampana = idCampana;
    }
    public String getTipoCampana(){
        return this.tipoCampana;
    }

    public void setTipoCampana(String tipoCampana){
        this.tipoCampana = tipoCampana;
    }
    public String getNombreRefiero(){
        return this.nombreRefiero;
    }

    public void setNombreRefiero(String nombreRefiero){
        this.nombreRefiero = nombreRefiero;
    }

}