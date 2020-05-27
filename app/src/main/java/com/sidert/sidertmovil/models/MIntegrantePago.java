package com.sidert.sidertmovil.models;

import java.io.Serializable;

public class MIntegrantePago implements Serializable {

    private String idIntegrante;
    private String idPrestamo;
    private String idGestion;
    private String nombre;
    private String montoRequerido;
    private String pagoRealizado;
    private String adelanto;
    private String solidario;
    private boolean pagoRequerido;
    private String tipoCartera;

    public String getIdIntegrante() {
        return idIntegrante;
    }

    public void setIdIntegrante(String idIntegrante) {
        this.idIntegrante = idIntegrante;
    }

    public String getIdPrestamo() {
        return idPrestamo;
    }

    public void setIdPrestamo(String idPrestamo) {
        this.idPrestamo = idPrestamo;
    }

    public String getIdGestion() {
        return idGestion;
    }

    public void setIdGestion(String idGestion) {
        this.idGestion = idGestion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getMontoRequerido() {
        return montoRequerido;
    }

    public void setMontoRequerido(String montoRequerido) {
        this.montoRequerido = montoRequerido;
    }

    public String getPagoRealizado() {
        return pagoRealizado;
    }

    public void setPagoRealizado(String pagoRealizado) {
        this.pagoRealizado = pagoRealizado;
    }

    public String getAdelanto() {
        return adelanto;
    }

    public void setAdelanto(String adelanto) {
        this.adelanto = adelanto;
    }

    public String getSolidario() {
        return solidario;
    }

    public void setSolidario(String solidario) {
        this.solidario = solidario;
    }

    public boolean isPagoRequerido() {
        return pagoRequerido;
    }

    public void setPagoRequerido(boolean pagoRequerido) {
        this.pagoRequerido = pagoRequerido;
    }

    public String getTipoCartera() {
        return tipoCartera;
    }

    public void setTipoCartera(String tipoCartera) {
        this.tipoCartera = tipoCartera;
    }

}
