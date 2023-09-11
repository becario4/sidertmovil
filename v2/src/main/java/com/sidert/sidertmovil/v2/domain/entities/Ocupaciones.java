package com.sidert.sidertmovil.v2.domain.entities;

import java.io.Serializable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "ocupaciones")
public final class Ocupaciones implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    private Long id;
    @ColumnInfo(name = "ocupacion_id")
    private Integer ocupacionId;
    @ColumnInfo(name = "ocupacion_nombre")
    private String ocupacionNombre;
    @ColumnInfo(name = "ocupacion_clave")
    private String ocupacionClave;
    @ColumnInfo(name = "nivel_riesgo")
    private Integer nivelRiesgo;
    @ColumnInfo(name = "sector_id")
    private Integer sectorId;


// Getters & Setters

    public Long getId(){
        return this.id;
    }

    public void setId(Long id){
        this.id = id;
    }
    public Integer getOcupacionId(){
        return this.ocupacionId;
    }

    public void setOcupacionId(Integer ocupacionId){
        this.ocupacionId = ocupacionId;
    }
    public String getOcupacionNombre(){
        return this.ocupacionNombre;
    }

    public void setOcupacionNombre(String ocupacionNombre){
        this.ocupacionNombre = ocupacionNombre;
    }
    public String getOcupacionClave(){
        return this.ocupacionClave;
    }

    public void setOcupacionClave(String ocupacionClave){
        this.ocupacionClave = ocupacionClave;
    }
    public Integer getNivelRiesgo(){
        return this.nivelRiesgo;
    }

    public void setNivelRiesgo(Integer nivelRiesgo){
        this.nivelRiesgo = nivelRiesgo;
    }
    public Integer getSectorId(){
        return this.sectorId;
    }

    public void setSectorId(Integer sectorId){
        this.sectorId = sectorId;
    }

}