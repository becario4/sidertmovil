package com.sidert.sidertmovil.models.cartera.amortizaciones;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Amortizacion implements Serializable {
    @SerializedName("_id")
    @Expose
    private Integer id;

    @SerializedName("id_amortizacion")
    @Expose
    private String idAmortizacion;

    @SerializedName("id_prestamo")
    @Expose
    private String idPrestamo;

    @SerializedName("fecha")
    @Expose
    private String fecha;

    @SerializedName("fecha_pago")
    @Expose
    private String fechaPago;

    @SerializedName("capital")
    @Expose
    private String capital;

    @SerializedName("interes")
    @Expose
    private String interes;

    @SerializedName("iva")
    @Expose
    private String iva;

    @SerializedName("comision")
    @Expose
    private String comision;

    @SerializedName("total")
    @Expose
    private String total;

    @SerializedName("capital_pagado")
    @Expose
    private String capitalPagado;

    @SerializedName("interes_pagado")
    @Expose
    private String interesPagado;

    @SerializedName("iva_pagado")
    @Expose
    private String ivaPagado;

    @SerializedName("interes_moratorio_pagado")
    @Expose
    private String interesMoratorioPagado;

    @SerializedName("iva_moratorio_pagado")
    @Expose
    private String ivaMoratorioPagado;

    @SerializedName("comision_pagada")
    @Expose
    private String comisionPagada;

    @SerializedName("total_pagado")
    @Expose
    private String totalPagado;

    @SerializedName("pagado")
    @Expose
    private String pagado;

    @SerializedName("numero")
    @Expose
    private String numero;

    @SerializedName("dias_atraso")
    @Expose
    private String diasAtraso;

    @SerializedName("fecha_dispositivo")
    @Expose
    private String fechaDispositivo;

    @SerializedName("fecha_actualizado")
    @Expose
    private String fechaActualizado;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIdAmortizacion() {
        return idAmortizacion;
    }

    public void setIdAmortizacion(String idAmortizacion) {
        this.idAmortizacion = idAmortizacion;
    }

    public String getIdPrestamo() {
        return idPrestamo;
    }

    public void setIdPrestamo(String idPrestamo) {
        this.idPrestamo = idPrestamo;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(String fechaPago) {
        this.fechaPago = fechaPago;
    }

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public String getInteres() {
        return interes;
    }

    public void setInteres(String interes) {
        this.interes = interes;
    }

    public String getIva() {
        return iva;
    }

    public void setIva(String iva) {
        this.iva = iva;
    }

    public String getComision() {
        return comision;
    }

    public void setComision(String comision) {
        this.comision = comision;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getCapitalPagado() {
        return capitalPagado;
    }

    public void setCapitalPagado(String capitalPagado) {
        this.capitalPagado = capitalPagado;
    }

    public String getInteresPagado() {
        return interesPagado;
    }

    public void setInteresPagado(String interesPagado) {
        this.interesPagado = interesPagado;
    }

    public String getIvaPagado() {
        return ivaPagado;
    }

    public void setIvaPagado(String ivaPagado) {
        this.ivaPagado = ivaPagado;
    }

    public String getInteresMoratorioPagado() {
        return interesMoratorioPagado;
    }

    public void setInteresMoratorioPagado(String interesMoratorioPagado) {
        this.interesMoratorioPagado = interesMoratorioPagado;
    }

    public String getIvaMoratorioPagado() {
        return ivaMoratorioPagado;
    }

    public void setIvaMoratorioPagado(String ivaMoratorioPagado) {
        this.ivaMoratorioPagado = ivaMoratorioPagado;
    }

    public String getComisionPagada() {
        return comisionPagada;
    }

    public void setComisionPagada(String comisionPagada) {
        this.comisionPagada = comisionPagada;
    }

    public String getTotalPagado() {
        return totalPagado;
    }

    public void setTotalPagado(String totalPagado) {
        this.totalPagado = totalPagado;
    }

    public String getPagado() {
        return pagado;
    }

    public void setPagado(String pagado) {
        this.pagado = pagado;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getDiasAtraso() {
        return diasAtraso;
    }

    public void setDiasAtraso(String diasAtraso) {
        this.diasAtraso = diasAtraso;
    }

    public String getFechaDispositivo() {
        return fechaDispositivo;
    }

    public void setFechaDispositivo(String fechaDispositivo) {
        this.fechaDispositivo = fechaDispositivo;
    }

    public String getFechaActualizado() {
        return fechaActualizado;
    }

    public void setFechaActualizado(String fechaActualizado) {
        this.fechaActualizado = fechaActualizado;
    }
}
