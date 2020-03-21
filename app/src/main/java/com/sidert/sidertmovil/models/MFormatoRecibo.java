package com.sidert.sidertmovil.models;

import java.io.Serializable;

public class MFormatoRecibo implements Serializable {

    private String nombreCliente;
    private String folio;
    private int tipoRecibo;
    private boolean tipoImpresion;
    private boolean reeimpresion;
    private int resImpresion;

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public int getTipoRecibo() {
        return tipoRecibo;
    }

    public void setTipoRecibo(int tipoRecibo) {
        this.tipoRecibo = tipoRecibo;
    }

    public boolean isTipoImpresion() {
        return tipoImpresion;
    }

    public void setTipoImpresion(boolean tipoImpresion) {
        this.tipoImpresion = tipoImpresion;
    }

    public boolean isReeimpresion() {
        return reeimpresion;
    }

    public void setReeimpresion(boolean reeimpresion) {
        this.reeimpresion = reeimpresion;
    }

    public int getResImpresion() {
        return resImpresion;
    }

    public void setResImpresion(int resImpresion) {
        this.resImpresion = resImpresion;
    }
}
