package com.sidert.sidertmovil.models;

import java.io.Serializable;

public class MPrestamo implements Serializable {

    private String nombre;
    private String desembolso;
    private String montoPrestamo;
    private String id;
    private String idPrestamo;
    private String montoRestante;
    private String montoAmortiz;
    private String estatus;
    private int tipo;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDesembolso() {
        return desembolso;
    }

    public void setDesembolso(String desembolso) {
        this.desembolso = desembolso;
    }

    public String getMontoPrestamo() {
        return montoPrestamo;
    }

    public void setMontoPrestamo(String montoPrestamo) {
        this.montoPrestamo = montoPrestamo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdPrestamo() {
        return idPrestamo;
    }

    public void setIdPrestamo(String idPrestamo) {
        this.idPrestamo = idPrestamo;
    }

    public String getMontoRestante() {
        return montoRestante;
    }

    public void setMontoRestante(String montoRestante) {
        this.montoRestante = montoRestante;
    }

    public String getMontoAmortiz() {
        return montoAmortiz;
    }

    public void setMontoAmortiz(String montoAmortiz) {
        this.montoAmortiz = montoAmortiz;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }
}
