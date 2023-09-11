package com.sidert.sidertmovil.v2.domain.entities;

import java.io.Serializable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tbl_servicios_sincronizados")
public final class ServiciosSincronizados implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    private Long id;
    @ColumnInfo(name = "nombre")
    private String nombre;
    @ColumnInfo(name = "estatus")
    private Integer estatus;
    @ColumnInfo(name = "posicion")
    private Integer posicion;
    @ColumnInfo(name = "ejecutado")
    private Integer ejecutado;


// Getters & Setters

    public Long getId(){
        return this.id;
    }

    public void setId(Long id){
        this.id = id;
    }
    public String getNombre(){
        return this.nombre;
    }

    public void setNombre(String nombre){
        this.nombre = nombre;
    }
    public Integer getEstatus(){
        return this.estatus;
    }

    public void setEstatus(Integer estatus){
        this.estatus = estatus;
    }
    public Integer getPosicion(){
        return this.posicion;
    }

    public void setPosicion(Integer posicion){
        this.posicion = posicion;
    }
    public Integer getEjecutado(){
        return this.ejecutado;
    }

    public void setEjecutado(Integer ejecutado){
        this.ejecutado = ejecutado;
    }

}