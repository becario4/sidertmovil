package com.sidert.sidertmovil.models.solicitudes;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PrestamoToRenovar  implements Serializable {
    @SerializedName("_id")
    @Expose
    private Integer id;

    @SerializedName("asesor_id")
    @Expose
    private String asesorId;

    @SerializedName("prestamo_id")
    @Expose
    private Integer prestamoId;

    @SerializedName("cliente_id")
    @Expose
    private Integer clienteId;

    @SerializedName("cliente_nombre")
    @Expose
    private String clienteNombre;

    @SerializedName("no_prestamo")
    @Expose
    private String noPrestamo;

    @SerializedName("fecha_vencimiento")
    @Expose
    private String fechaVencimiento;

    @SerializedName("num_pagos")
    @Expose
    private Integer numPagos;

    @SerializedName("descargado")
    @Expose
    private Integer descargado;

    @SerializedName("tipo_prestamo")
    @Expose
    private Integer tipoPrestamo;

    @SerializedName("grupo_id")
    @Expose
    private String grupoId;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAsesorId() {
        return asesorId;
    }

    public void setAsesorId(String asesorId) {
        this.asesorId = asesorId;
    }

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

    public String getClienteNombre() {
        return clienteNombre;
    }

    public void setClienteNombre(String clienteNombre) {
        this.clienteNombre = clienteNombre;
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

    public Integer getDescargado() {
        return descargado;
    }

    public void setDescargado(Integer descargado) {
        this.descargado = descargado;
    }

    public Integer getTipoPrestamo() {
        return tipoPrestamo;
    }

    public void setTipoPrestamo(Integer tipoPrestamo) {
        this.tipoPrestamo = tipoPrestamo;
    }

    public String getGrupoId() {
        return grupoId;
    }

    public void setGrupoId(String grupoId) {
        this.grupoId = grupoId;
    }
}
