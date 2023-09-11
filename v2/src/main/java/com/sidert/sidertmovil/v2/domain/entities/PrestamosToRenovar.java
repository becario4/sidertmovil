package com.sidert.sidertmovil.v2.domain.entities;

import java.io.Serializable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tbl_prestamos_to_renovar")
public final class PrestamosToRenovar implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    private Long id;
    @ColumnInfo(name = "asesor_id")
    private String asesorId;
    @ColumnInfo(name = "prestamo_id")
    private Integer prestamoId;
    @ColumnInfo(name = "cliente_id")
    private Integer clienteId;
    @ColumnInfo(name = "cliente_nombre")
    private String clienteNombre;
    @ColumnInfo(name = "no_prestamo")
    private String noPrestamo;
    @ColumnInfo(name = "fecha_vencimiento")
    private String fechaVencimiento;
    @ColumnInfo(name = "num_pagos")
    private Integer numPagos;
    @ColumnInfo(name = "descargado")
    private Integer descargado;
    @ColumnInfo(name = "tipo_prestamo")
    private Integer tipoPrestamo;
    @ColumnInfo(name = "grupo_id")
    private String grupoId;


// Getters & Setters

    public Long getId(){
        return this.id;
    }

    public void setId(Long id){
        this.id = id;
    }
    public String getAsesorId(){
        return this.asesorId;
    }

    public void setAsesorId(String asesorId){
        this.asesorId = asesorId;
    }
    public Integer getPrestamoId(){
        return this.prestamoId;
    }

    public void setPrestamoId(Integer prestamoId){
        this.prestamoId = prestamoId;
    }
    public Integer getClienteId(){
        return this.clienteId;
    }

    public void setClienteId(Integer clienteId){
        this.clienteId = clienteId;
    }
    public String getClienteNombre(){
        return this.clienteNombre;
    }

    public void setClienteNombre(String clienteNombre){
        this.clienteNombre = clienteNombre;
    }
    public String getNoPrestamo(){
        return this.noPrestamo;
    }

    public void setNoPrestamo(String noPrestamo){
        this.noPrestamo = noPrestamo;
    }
    public String getFechaVencimiento(){
        return this.fechaVencimiento;
    }

    public void setFechaVencimiento(String fechaVencimiento){
        this.fechaVencimiento = fechaVencimiento;
    }
    public Integer getNumPagos(){
        return this.numPagos;
    }

    public void setNumPagos(Integer numPagos){
        this.numPagos = numPagos;
    }
    public Integer getDescargado(){
        return this.descargado;
    }

    public void setDescargado(Integer descargado){
        this.descargado = descargado;
    }
    public Integer getTipoPrestamo(){
        return this.tipoPrestamo;
    }

    public void setTipoPrestamo(Integer tipoPrestamo){
        this.tipoPrestamo = tipoPrestamo;
    }
    public String getGrupoId(){
        return this.grupoId;
    }

    public void setGrupoId(String grupoId){
        this.grupoId = grupoId;
    }

}