package com.sidert.sidertmovil.v2.domain.entities;

import java.io.Serializable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tbl_sucursales_localidades")
public final class SucursalesLocalidades implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_sucursales_localidades")
    private Long idSucursalesLocalidades;
    @ColumnInfo(name = "centrocosto")
    private Integer centrocosto;
    @ColumnInfo(name = "id_municipio")
    private Integer idMunicipio;
    @ColumnInfo(name = "id_localidad")
    private Integer idLocalidad;
    @ColumnInfo(name = "localidad")
    private String localidad;


// Getters & Setters

    public Long getIdSucursalesLocalidades(){
        return this.idSucursalesLocalidades;
    }

    public void setIdSucursalesLocalidades(Long idSucursalesLocalidades){
        this.idSucursalesLocalidades = idSucursalesLocalidades;
    }
    public Integer getCentrocosto(){
        return this.centrocosto;
    }

    public void setCentrocosto(Integer centrocosto){
        this.centrocosto = centrocosto;
    }
    public Integer getIdMunicipio(){
        return this.idMunicipio;
    }

    public void setIdMunicipio(Integer idMunicipio){
        this.idMunicipio = idMunicipio;
    }
    public Integer getIdLocalidad(){
        return this.idLocalidad;
    }

    public void setIdLocalidad(Integer idLocalidad){
        this.idLocalidad = idLocalidad;
    }
    public String getLocalidad(){
        return this.localidad;
    }

    public void setLocalidad(String localidad){
        this.localidad = localidad;
    }

}