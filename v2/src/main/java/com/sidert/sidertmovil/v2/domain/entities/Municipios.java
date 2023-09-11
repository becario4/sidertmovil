package com.sidert.sidertmovil.v2.domain.entities;

import java.io.Serializable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "municipios")
public final class Municipios implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    private Long id;
    @ColumnInfo(name = "municipio_id")
    private Integer municipioId;
    @ColumnInfo(name = "municipio_nombre")
    private String municipioNombre;
    @ColumnInfo(name = "estado_id")
    private Integer estadoId;


// Getters & Setters

    public Long getId(){
        return this.id;
    }

    public void setId(Long id){
        this.id = id;
    }
    public Integer getMunicipioId(){
        return this.municipioId;
    }

    public void setMunicipioId(Integer municipioId){
        this.municipioId = municipioId;
    }
    public String getMunicipioNombre(){
        return this.municipioNombre;
    }

    public void setMunicipioNombre(String municipioNombre){
        this.municipioNombre = municipioNombre;
    }
    public Integer getEstadoId(){
        return this.estadoId;
    }

    public void setEstadoId(Integer estadoId){
        this.estadoId = estadoId;
    }

}