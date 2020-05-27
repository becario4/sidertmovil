package com.sidert.sidertmovil.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MAmortizacion {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("prestamo_id")
    @Expose
    private Integer prestamoId;
    @SerializedName("fecha")
    @Expose
    private String fecha;
    @SerializedName("fecha_pago")
    @Expose
    private String fechaPago;
    @SerializedName("capital")
    @Expose
    private Double capital;
    @SerializedName("interes")
    @Expose
    private Double interes;
    @SerializedName("iva")
    @Expose
    private Double iva;
    @SerializedName("comision")
    @Expose
    private Double comision;
    @SerializedName("total")
    @Expose
    private Double total;
    @SerializedName("capital_pagado")
    @Expose
    private Double capitalPagado;
    @SerializedName("interes_pagado")
    @Expose
    private Double interesPagado;
    @SerializedName("iva_pagado")
    @Expose
    private Double ivaPagado;
    @SerializedName("interes_moratorio_pagado")
    @Expose
    private Double interesMoratorioPagado;
    @SerializedName("iva_moratorio_pagado")
    @Expose
    private Double ivaMoratorioPagado;
    @SerializedName("comision_pagada")
    @Expose
    private Double comisionPagada;
    @SerializedName("total_pagado")
    @Expose
    private Double totalPagado;
    @SerializedName("pagado")
    @Expose
    private String pagado;
    @SerializedName("numero")
    @Expose
    private Integer numero;
    @SerializedName("dias_atraso")
    @Expose
    private String diasAtraso;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPrestamoId() {
        return prestamoId;
    }

    public void setPrestamoId(Integer prestamoId) {
        this.prestamoId = prestamoId;
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

    public Double getCapital() {
        return capital;
    }

    public void setCapital(Double capital) {
        this.capital = capital;
    }

    public Double getInteres() {
        return interes;
    }

    public void setInteres(Double interes) {
        this.interes = interes;
    }

    public Double getIva() {
        return iva;
    }

    public void setIva(Double iva) {
        this.iva = iva;
    }

    public Double getComision() {
        return comision;
    }

    public void setComision(Double comision) {
        this.comision = comision;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Double getCapitalPagado() {
        return capitalPagado;
    }

    public void setCapitalPagado(Double capitalPagado) {
        this.capitalPagado = capitalPagado;
    }

    public Double getInteresPagado() {
        return interesPagado;
    }

    public void setInteresPagado(Double interesPagado) {
        this.interesPagado = interesPagado;
    }

    public Double getIvaPagado() {
        return ivaPagado;
    }

    public void setIvaPagado(Double ivaPagado) {
        this.ivaPagado = ivaPagado;
    }

    public Double getInteresMoratorioPagado() {
        return interesMoratorioPagado;
    }

    public void setInteresMoratorioPagado(Double interesMoratorioPagado) {
        this.interesMoratorioPagado = interesMoratorioPagado;
    }

    public Double getIvaMoratorioPagado() {
        return ivaMoratorioPagado;
    }

    public void setIvaMoratorioPagado(Double ivaMoratorioPagado) {
        this.ivaMoratorioPagado = ivaMoratorioPagado;
    }

    public Double getComisionPagada() {
        return comisionPagada;
    }

    public void setComisionPagada(Double comisionPagada) {
        this.comisionPagada = comisionPagada;
    }

    public Double getTotalPagado() {
        return totalPagado;
    }

    public void setTotalPagado(Double totalPagado) {
        this.totalPagado = totalPagado;
    }

    public String getPagado() {
        return pagado;
    }

    public void setPagado(String pagado) {
        this.pagado = pagado;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getDiasAtraso() {
        return diasAtraso;
    }

    public void setDiasAtraso(String diasAtraso) {
        this.diasAtraso = diasAtraso;
    }
}
