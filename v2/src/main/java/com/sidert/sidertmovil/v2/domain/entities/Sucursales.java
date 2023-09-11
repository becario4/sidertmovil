package com.sidert.sidertmovil.v2.domain.entities;

import java.io.Serializable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tbl_sucursales")
public final class Sucursales implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    private Long id;
    @ColumnInfo(name = "id")
    private Integer remoteId;
    @ColumnInfo(name = "nombre")
    private String nombre;
    @ColumnInfo(name = "region_id")
    private Integer regionId;
    @ColumnInfo(name = "centrocosto_id")
    private Integer centrocostoId;


// Getters & Setters

    public Long getId(){
        return this.id;
    }

    public void setId(Long id){
        this.id = id;
    }
    public Integer getRemoteId(){
        return this.remoteId;
    }

    public void setRemoteId(Integer remoteId){
        this.remoteId = remoteId;
    }
    public String getNombre(){
        return this.nombre;
    }

    public void setNombre(String nombre){
        this.nombre = nombre;
    }
    public Integer getRegionId(){
        return this.regionId;
    }

    public void setRegionId(Integer regionId){
        this.regionId = regionId;
    }
    public Integer getCentrocostoId(){
        return this.centrocostoId;
    }

    public void setCentrocostoId(Integer centrocostoId){
        this.centrocostoId = centrocostoId;
    }

}