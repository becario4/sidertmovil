package com.sidert.sidertmovil.models;

import java.io.Serializable;

public class RecibosAgfCC implements Serializable {

    private String nombre;
    private String nombreFirma;
    private String monto;
    private String tipoRecibo;
    private String folio;
    private String tipoImpresion;
    private boolean isReeimpresion;
    private int resImpresion;
    private String grupoId;
    private String numSolicitud;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombreFirma() {
        return nombreFirma;
    }

    public void setNombreFirma(String nombreFirma) {
        this.nombreFirma = nombreFirma;
    }

    public String getMonto() {
        return monto;
    }

    public void setMonto(String monto) {
        this.monto = monto;
    }

    public String getTipoRecibo() {
        return tipoRecibo;
    }

    public void setTipoRecibo(String tipoRecibo) {
        this.tipoRecibo = tipoRecibo;
    }

    public String getTipoImpresion() {
        return tipoImpresion;
    }

    public void setTipoImpresion(String tipoImpresion) {
        this.tipoImpresion = tipoImpresion;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public boolean isReeimpresion() {
        return isReeimpresion;
    }

    public void setReeimpresion(boolean reeimpresion) {
        isReeimpresion = reeimpresion;
    }

    public int getResImpresion() {
        return resImpresion;
    }

    public void setResImpresion(int resImpresion) {
        this.resImpresion = resImpresion;
    }

    public String getGrupoId() {
        return grupoId;
    }

    public void setGrupoId(String grupoId) {
        this.grupoId = grupoId;
    }

    public String getNumSolicitud() {
        return numSolicitud;
    }

    public void setNumSolicitud(String numSolicitud) {
        this.numSolicitud = numSolicitud;
    }
}
