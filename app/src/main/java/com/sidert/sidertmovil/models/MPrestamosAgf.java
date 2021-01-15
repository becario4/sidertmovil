package com.sidert.sidertmovil.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MPrestamosAgf implements Serializable {

    @SerializedName("nombre_grupo")
    @Expose
    private String nombreGrupo;
    @SerializedName("grupo_id")
    @Expose
    private Integer grupoId;
    @SerializedName("cliente_id")
    @Expose
    private String clienteId;
    @SerializedName("num_solicitud")
    @Expose
    private Integer numSolicitud;
    @SerializedName("periodicidad")
    @Expose
    private Integer periodicidad;
    @SerializedName("num_pagos")
    @Expose
    private Integer numPagos;
    @SerializedName("estado_nacimiento_id")
    @Expose
    private String estadoNacimientoId;
    @SerializedName("genero")
    @Expose
    private String genero;
    @SerializedName("nombre_cliente")
    @Expose
    private String nombreCliente;
    @SerializedName("fecha_nacimiento")
    @Expose
    private String fechaNacimiento;
    @SerializedName("edad")
    @Expose
    private String edad;
    @SerializedName("monto")
    @Expose
    private String monto;

    public String getNombreGrupo() {
        return nombreGrupo;
    }

    public void setNombreGrupo(String nombreGrupo) {
        this.nombreGrupo = nombreGrupo;
    }

    public Integer getGrupoId() {
        return grupoId;
    }

    public void setGrupoId(Integer grupoId) {
        this.grupoId = grupoId;
    }

    public String getClienteId() {
        return clienteId;
    }

    public void setClienteId(String clienteId) {
        this.clienteId = clienteId;
    }

    public Integer getNumSolicitud() {
        return numSolicitud;
    }

    public void setNumSolicitud(Integer numSolicitud) {
        this.numSolicitud = numSolicitud;
    }

    public Integer getPeriodicidad() {
        return periodicidad;
    }

    public void setPeriodicidad(Integer periodicidad) {
        this.periodicidad = periodicidad;
    }

    public Integer getNumPagos() {
        return numPagos;
    }

    public void setNumPagos(Integer numPagos) {
        this.numPagos = numPagos;
    }

    public String getEstadoNacimientoId() {
        return estadoNacimientoId;
    }

    public void setEstadoNacimientoId(String estadoNacimientoId) {
        this.estadoNacimientoId = estadoNacimientoId;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }

    public String getMonto() {
        return monto;
    }

    public void setMonto(String monto) {
        this.monto = monto;
    }
}
