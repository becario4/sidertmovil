package com.sidert.sidertmovil.v2.domain.entities;

import java.io.Serializable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tbl_plazos_prestamos")
public final class PlazosPrestamos implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    private Long id;
    @ColumnInfo(name = "id_plazo_prestamo")
    private Integer idPlazoPrestamo;
    @ColumnInfo(name = "nombre")
    private String nombre;
    @ColumnInfo(name = "numero_plazos")
    private Integer numeroPlazos;
    @ColumnInfo(name = "estatus")
    private Integer estatus;


// Getters & Setters

    public Long getId(){
        return this.id;
    }

    public void setId(Long id){
        this.id = id;
    }
    public Integer getIdPlazoPrestamo(){
        return this.idPlazoPrestamo;
    }

    public void setIdPlazoPrestamo(Integer idPlazoPrestamo){
        this.idPlazoPrestamo = idPlazoPrestamo;
    }
    public String getNombre(){
        return this.nombre;
    }

    public void setNombre(String nombre){
        this.nombre = nombre;
    }
    public Integer getNumeroPlazos(){
        return this.numeroPlazos;
    }

    public void setNumeroPlazos(Integer numeroPlazos){
        this.numeroPlazos = numeroPlazos;
    }
    public Integer getEstatus(){
        return this.estatus;
    }

    public void setEstatus(Integer estatus){
        this.estatus = estatus;
    }

}