package com.sidert.sidertmovil.models;

import java.io.Serializable;

public class MFichaContestada implements Serializable {

    private String idGestion;
    private String idPrestamo;
    private String nombre;
    private String tipoFicha;
    private String estatusFicha;
    private String estatusGestion;
    private String diaPago;
    private String timestamp;

    public String getIdGestion() {
        return idGestion;
    }

    public void setIdGestion(String idGestion) {
        this.idGestion = idGestion;
    }

    public String getIdPrestamo() {
        return idPrestamo;
    }

    public void setIdPrestamo(String idPrestamo) {
        this.idPrestamo = idPrestamo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipoFicha() {
        return tipoFicha;
    }

    public void setTipoFicha(String tipoFicha) {
        this.tipoFicha = tipoFicha;
    }

    public String getEstatusFicha() {
        return estatusFicha;
    }

    public void setEstatusFicha(String estatusFicha) {
        this.estatusFicha = estatusFicha;
    }

    public String getEstatusGestion() {
        return estatusGestion;
    }

    public void setEstatusGestion(String estatusGestion) {
        this.estatusGestion = estatusGestion;
    }

    public String getDiaPago() {
        return diaPago;
    }

    public void setDiaPago(String diaPago) {
        this.diaPago = diaPago;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
