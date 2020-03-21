package com.sidert.sidertmovil.models;

import java.io.Serializable;

public class MTicketCC implements Serializable {

    private String _id;
    private String asesor_id;
    private String nombre_cliente;
    private String tipo_impresion;
    private int folio;
    private String fecha_impresion;
    private String fecha_envio;
    private int tipo_recibo;
    private int estatus;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getAsesor_id() {
        return asesor_id;
    }

    public void setAsesor_id(String asesor_id) {
        this.asesor_id = asesor_id;
    }

    public String getNombre_cliente() {
        return nombre_cliente;
    }

    public void setNombre_cliente(String nombre_cliente) {
        this.nombre_cliente = nombre_cliente;
    }

    public String getTipo_impresion() {
        return tipo_impresion;
    }

    public void setTipo_impresion(String tipo_impresion) {
        this.tipo_impresion = tipo_impresion;
    }

    public int getFolio() {
        return folio;
    }

    public void setFolio(int folio) {
        this.folio = folio;
    }

    public String getFecha_impresion() {
        return fecha_impresion;
    }

    public void setFecha_impresion(String fecha_impresion) {
        this.fecha_impresion = fecha_impresion;
    }

    public String getFecha_envio() {
        return fecha_envio;
    }

    public void setFecha_envio(String fecha_envio) {
        this.fecha_envio = fecha_envio;
    }

    public int getEstatus() {
        return estatus;
    }

    public int getTipo_recibo() {
        return tipo_recibo;
    }

    public void setTipo_recibo(int tipo_recibo) {
        this.tipo_recibo = tipo_recibo;
    }

    public void setEstatus(int estatus) {
        this.estatus = estatus;
    }
}
