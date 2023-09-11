package com.sidert.sidertmovil.v2.domain.entities;

import java.io.Serializable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tbl_pagos_ind_t")
public final class PagosInd implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    private Long id;
    @ColumnInfo(name = "id_prestamo")
    private String idPrestamo;
    @ColumnInfo(name = "fecha")
    private String fecha;
    @ColumnInfo(name = "monto")
    private String monto;
    @ColumnInfo(name = "banco")
    private String banco;
    @ColumnInfo(name = "fecha_dispositivo")
    private String fechaDispositivo;
    @ColumnInfo(name = "fecha_actualizado")
    private String fechaActualizado;


// Getters & Setters

    public Long getId(){
        return this.id;
    }

    public void setId(Long id){
        this.id = id;
    }
    public String getIdPrestamo(){
        return this.idPrestamo;
    }

    public void setIdPrestamo(String idPrestamo){
        this.idPrestamo = idPrestamo;
    }
    public String getFecha(){
        return this.fecha;
    }

    public void setFecha(String fecha){
        this.fecha = fecha;
    }
    public String getMonto(){
        return this.monto;
    }

    public void setMonto(String monto){
        this.monto = monto;
    }
    public String getBanco(){
        return this.banco;
    }

    public void setBanco(String banco){
        this.banco = banco;
    }
    public String getFechaDispositivo(){
        return this.fechaDispositivo;
    }

    public void setFechaDispositivo(String fechaDispositivo){
        this.fechaDispositivo = fechaDispositivo;
    }
    public String getFechaActualizado(){
        return this.fechaActualizado;
    }

    public void setFechaActualizado(String fechaActualizado){
        this.fechaActualizado = fechaActualizado;
    }

}