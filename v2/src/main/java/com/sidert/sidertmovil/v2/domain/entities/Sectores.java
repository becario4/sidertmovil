package com.sidert.sidertmovil.v2.domain.entities;

import java.io.Serializable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "sectores")
public final class Sectores implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    private Long id;
    @ColumnInfo(name = "sector_id")
    private Integer sectorId;
    @ColumnInfo(name = "sector_nombre")
    private String sectorNombre;
    @ColumnInfo(name = "nivel_riesgo")
    private Integer nivelRiesgo;


// Getters & Setters

    public Long getId(){
        return this.id;
    }

    public void setId(Long id){
        this.id = id;
    }
    public Integer getSectorId(){
        return this.sectorId;
    }

    public void setSectorId(Integer sectorId){
        this.sectorId = sectorId;
    }
    public String getSectorNombre(){
        return this.sectorNombre;
    }

    public void setSectorNombre(String sectorNombre){
        this.sectorNombre = sectorNombre;
    }
    public Integer getNivelRiesgo(){
        return this.nivelRiesgo;
    }

    public void setNivelRiesgo(Integer nivelRiesgo){
        this.nivelRiesgo = nivelRiesgo;
    }

}