package com.sidert.sidertmovil.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MPrestamosRenovar implements Serializable {

    @SerializedName("prestamo_id")
    @Expose
    private Integer prestamoId;
    @SerializedName("cliente_id")
    @Expose
    private Integer clienteId;
    @SerializedName("nombre")
    @Expose
    private String nombre;
    @SerializedName("grupo_id")
    @Expose
    private Integer grupoId;
    @SerializedName("num_solicitud")
    @Expose
    private Integer numSolicitud;
    @SerializedName("tipo_prestamo")
    @Expose
    private String tipoPrestamo;
    @SerializedName("no_prestamo")
    @Expose
    private String noPrestamo;
    @SerializedName("fecha_vencimiento")
    @Expose
    private String fechaVencimiento;
    @SerializedName("num_pagos")
    @Expose
    private Integer numPagos;

    public Integer getPrestamoId() {
        return prestamoId;
    }

    public void setPrestamoId(Integer prestamoId) {
        this.prestamoId = prestamoId;
    }

    public Integer getClienteId() {
        return clienteId;
    }

    public void setClienteId(Integer clienteId) {
        this.clienteId = clienteId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getGrupoId() {
        return grupoId;
    }

    public void setGrupoId(Integer grupoId) {
        this.grupoId = grupoId;
    }

    public Integer getNumSolicitud() {
        return numSolicitud;
    }

    public void setNumSolicitud(Integer numSolicitud) {
        this.numSolicitud = numSolicitud;
    }

    public String getTipoPrestamo() {
        return tipoPrestamo;
    }

    public void setTipoPrestamo(String tipoPrestamo) {
        this.tipoPrestamo = tipoPrestamo;
    }

    public String getNoPrestamo() {
        return noPrestamo;
    }

    public void setNoPrestamo(String noPrestamo) {
        this.noPrestamo = noPrestamo;
    }

    public String getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(String fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public Integer getNumPagos() {
        return numPagos;
    }

    public void setNumPagos(Integer numPagos) {
        this.numPagos = numPagos;
    }
}

