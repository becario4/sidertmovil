package com.sidert.sidertmovil.v2.domain.entities;

import java.io.Serializable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tbl_prestamos")
public final class Prestamos implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    private Long id;
    @ColumnInfo(name = "nombre_grupo")
    private String nombreGrupo;
    @ColumnInfo(name = "grupo_id")
    private String grupoId;
    @ColumnInfo(name = "cliente_id")
    private String clienteId;
    @ColumnInfo(name = "num_solicitud")
    private String numSolicitud;
    @ColumnInfo(name = "periodicidad")
    private String periodicidad;
    @ColumnInfo(name = "num_pagos")
    private String numPagos;
    @ColumnInfo(name = "estado_nacimiento")
    private String estadoNacimiento;
    @ColumnInfo(name = "genero")
    private String genero;
    @ColumnInfo(name = "nombre_cliente")
    private String nombreCliente;
    @ColumnInfo(name = "fecha_nacimiento")
    private String fechaNacimiento;
    @ColumnInfo(name = "edad")
    private String edad;
    @ColumnInfo(name = "monto")
    private String monto;
    @ColumnInfo(name = "fecha_entrega",defaultValue="''")
    private String fechaEntrega;
    @ColumnInfo(name = "estatus_prestamo_id",defaultValue="3")
    private Integer estatusPrestamoId;


// Getters & Setters

    public Long getId(){
        return this.id;
    }

    public void setId(Long id){
        this.id = id;
    }
    public String getNombreGrupo(){
        return this.nombreGrupo;
    }

    public void setNombreGrupo(String nombreGrupo){
        this.nombreGrupo = nombreGrupo;
    }
    public String getGrupoId(){
        return this.grupoId;
    }

    public void setGrupoId(String grupoId){
        this.grupoId = grupoId;
    }
    public String getClienteId(){
        return this.clienteId;
    }

    public void setClienteId(String clienteId){
        this.clienteId = clienteId;
    }
    public String getNumSolicitud(){
        return this.numSolicitud;
    }

    public void setNumSolicitud(String numSolicitud){
        this.numSolicitud = numSolicitud;
    }
    public String getPeriodicidad(){
        return this.periodicidad;
    }

    public void setPeriodicidad(String periodicidad){
        this.periodicidad = periodicidad;
    }
    public String getNumPagos(){
        return this.numPagos;
    }

    public void setNumPagos(String numPagos){
        this.numPagos = numPagos;
    }
    public String getEstadoNacimiento(){
        return this.estadoNacimiento;
    }

    public void setEstadoNacimiento(String estadoNacimiento){
        this.estadoNacimiento = estadoNacimiento;
    }
    public String getGenero(){
        return this.genero;
    }

    public void setGenero(String genero){
        this.genero = genero;
    }
    public String getNombreCliente(){
        return this.nombreCliente;
    }

    public void setNombreCliente(String nombreCliente){
        this.nombreCliente = nombreCliente;
    }
    public String getFechaNacimiento(){
        return this.fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento){
        this.fechaNacimiento = fechaNacimiento;
    }
    public String getEdad(){
        return this.edad;
    }

    public void setEdad(String edad){
        this.edad = edad;
    }
    public String getMonto(){
        return this.monto;
    }

    public void setMonto(String monto){
        this.monto = monto;
    }
    public String getFechaEntrega(){
        return this.fechaEntrega;
    }

    public void setFechaEntrega(String fechaEntrega){
        this.fechaEntrega = fechaEntrega;
    }
    public Integer getEstatusPrestamoId(){
        return this.estatusPrestamoId;
    }

    public void setEstatusPrestamoId(Integer estatusPrestamoId){
        this.estatusPrestamoId = estatusPrestamoId;
    }

}