package com.sidert.sidertmovil.v2.domain.entities;

import java.io.Serializable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "localidades")
public final class Localidades implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    private Long id;
    @ColumnInfo(name = "id_localidad")
    private Integer idLocalidad;
    @ColumnInfo(name = "nombre")
    private String nombre;
    @ColumnInfo(name = "id_municipio")
    private Integer idMunicipio;


// Getters & Setters

    public Long getId(){
        return this.id;
    }

    public void setId(Long id){
        this.id = id;
    }
    public Integer getIdLocalidad(){
        return this.idLocalidad;
    }

    public void setIdLocalidad(Integer idLocalidad){
        this.idLocalidad = idLocalidad;
    }
    public String getNombre(){
        return this.nombre;
    }

    public void setNombre(String nombre){
        this.nombre = nombre;
    }
    public Integer getIdMunicipio(){
        return this.idMunicipio;
    }

    public void setIdMunicipio(Integer idMunicipio){
        this.idMunicipio = idMunicipio;
    }

}