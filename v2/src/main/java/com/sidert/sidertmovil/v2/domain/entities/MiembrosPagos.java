package com.sidert.sidertmovil.v2.domain.entities;

import java.io.Serializable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tbl_miembros_pagos_t")
public final class MiembrosPagos implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    private Long id;
    @ColumnInfo(name = "id_integrante")
    private String idIntegrante;
    @ColumnInfo(name = "id_prestamo")
    private String idPrestamo;
    @ColumnInfo(name = "id_gestion")
    private String idGestion;
    @ColumnInfo(name = "nombre")
    private String nombre;
    @ColumnInfo(name = "monto_requerido")
    private String montoRequerido;
    @ColumnInfo(name = "pago_realizado")
    private String pagoRealizado;
    @ColumnInfo(name = "adelanto")
    private String adelanto;
    @ColumnInfo(name = "solidario")
    private String solidario;
    @ColumnInfo(name = "pago_requerido")
    private String pagoRequerido;


// Getters & Setters

    public Long getId(){
        return this.id;
    }

    public void setId(Long id){
        this.id = id;
    }
    public String getIdIntegrante(){
        return this.idIntegrante;
    }

    public void setIdIntegrante(String idIntegrante){
        this.idIntegrante = idIntegrante;
    }
    public String getIdPrestamo(){
        return this.idPrestamo;
    }

    public void setIdPrestamo(String idPrestamo){
        this.idPrestamo = idPrestamo;
    }
    public String getIdGestion(){
        return this.idGestion;
    }

    public void setIdGestion(String idGestion){
        this.idGestion = idGestion;
    }
    public String getNombre(){
        return this.nombre;
    }

    public void setNombre(String nombre){
        this.nombre = nombre;
    }
    public String getMontoRequerido(){
        return this.montoRequerido;
    }

    public void setMontoRequerido(String montoRequerido){
        this.montoRequerido = montoRequerido;
    }
    public String getPagoRealizado(){
        return this.pagoRealizado;
    }

    public void setPagoRealizado(String pagoRealizado){
        this.pagoRealizado = pagoRealizado;
    }
    public String getAdelanto(){
        return this.adelanto;
    }

    public void setAdelanto(String adelanto){
        this.adelanto = adelanto;
    }
    public String getSolidario(){
        return this.solidario;
    }

    public void setSolidario(String solidario){
        this.solidario = solidario;
    }
    public String getPagoRequerido(){
        return this.pagoRequerido;
    }

    public void setPagoRequerido(String pagoRequerido){
        this.pagoRequerido = pagoRequerido;
    }

}