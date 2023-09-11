package com.sidert.sidertmovil.v2.remote.dtos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PrestamoResponse implements Serializable {
    private Integer id;
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

    @SerializedName("fecha_entrega")
    @Expose
    private String fechaEntrega;

    @SerializedName("estatus_prestamo_id")
    @Expose
    private Integer estatusPrestamoId;

    public Integer getEstatusPrestamoId() {
        return estatusPrestamoId;
    }

    public void setEstatusPrestamoId(Integer estatusPrestamoId) {
        this.estatusPrestamoId = estatusPrestamoId;
    }

    public String getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(String fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    private Integer estatusRecibo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public Integer getEstatusRecibo() {
        return estatusRecibo;
    }

    public void setEstatusRecibo(Integer estatusRecibo) {
        this.estatusRecibo = estatusRecibo;
    }

    public Integer getNumIntegrantes() {
        Integer contador = 0;
        String[] edades = getEdad().split(",");
        String[] montos = getMonto().split(",");

        for (int i = 0; i < edades.length; i++) {
            if (Integer.parseInt(edades[i]) < 75 && Long.parseLong(montos[i]) <= 29000) {
                contador++;
            }
        }

        return contador;
    }

    public Integer getPlazo() {
        Integer plazo = 0;

        Double periodicidad = Double.parseDouble(String.valueOf(getPeriodicidad()));
        Double numPagos = Double.parseDouble(String.valueOf(getNumPagos()));

        if (periodicidad > 0 && numPagos > 0) {
            plazo = (int) Math.ceil((periodicidad * numPagos) / Double.parseDouble("30"));
        }

        return plazo;
    }

    public String getTesorero() {
        String tesorero = "";

        String[] nombres = getNombreCliente().split(",");

        for (int i = 0; i < nombres.length; i++) {
            if (nombres[i].contains("3")) {
                tesorero = nombres[i].substring(1);
                break;
            }
        }

        return tesorero;
    }

    public String getNombreCliente(Integer index) {
        String nombre = "";

        String[] nombres = getNombreCliente().split(",");

        if (index < nombres.length) {
            nombre = nombres[index].substring(1);
        }

        return nombre;
    }
}
