package com.sidert.sidertmovil.models;

import java.io.Serializable;

public class MFormatoRecibo implements Serializable {

    private String idPrestamo = "";
    private String asesorId;
    private String tipoRecibo;
    private boolean tipoImpresion;
    private String folio;
    private String monto;
    private String clave = "";
    private String nombre;
    private String apPaterno;
    private String apMaterno;
    private String fechaImpreso;
    private String fechaEnvio;
    private boolean reeimpresion;
    private int resImpresion;
    private String nombreCliente;

    public String getIdPrestamo() {
        return idPrestamo;
    }

    public void setIdPrestamo(String idPrestamo) {
        this.idPrestamo = idPrestamo;
    }

    public String getAsesorId() {
        return asesorId;
    }

    public void setAsesorId(String asesorId) {
        this.asesorId = asesorId;
    }

    public String getTipoRecibo() {
        return tipoRecibo;
    }

    public void setTipoRecibo(String tipoRecibo) {
        this.tipoRecibo = tipoRecibo;
    }

    public boolean isTipoImpresion() {
        return tipoImpresion;
    }

    public void setTipoImpresion(boolean tipoImpresion) {
        this.tipoImpresion = tipoImpresion;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public String getMonto() {
        return monto;
    }

    public void setMonto(String monto) {
        this.monto = monto;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApPaterno() {
        return apPaterno;
    }

    public void setApPaterno(String apPaterno) {
        this.apPaterno = apPaterno;
    }

    public String getApMaterno() {
        return apMaterno;
    }

    public void setApMaterno(String apMaterno) {
        this.apMaterno = apMaterno;
    }

    public String getFechaImpreso() {
        return fechaImpreso;
    }

    public void setFechaImpreso(String fechaImpreso) {
        this.fechaImpreso = fechaImpreso;
    }

    public String getFechaEnvio() {
        return fechaEnvio;
    }

    public void setFechaEnvio(String fechaEnvio) {
        this.fechaEnvio = fechaEnvio;
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

    public String getNombreCliente() {
        return nombre+" "+apPaterno+" "+apMaterno;
    }
}
