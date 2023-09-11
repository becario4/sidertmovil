package com.sidert.sidertmovil.v2.domain.entities;

import java.io.Serializable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tbl_amortizaciones_t")
public final class Amortizaciones implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    private Long id;
    @ColumnInfo(name = "id_amortizacion")
    private String idAmortizacion;
    @ColumnInfo(name = "id_prestamo")
    private String idPrestamo;
    @ColumnInfo(name = "fecha")
    private String fecha;
    @ColumnInfo(name = "fecha_pago")
    private String fechaPago;
    @ColumnInfo(name = "capital")
    private String capital;
    @ColumnInfo(name = "interes")
    private String interes;
    @ColumnInfo(name = "iva")
    private String iva;
    @ColumnInfo(name = "comision")
    private String comision;
    @ColumnInfo(name = "total")
    private String total;
    @ColumnInfo(name = "capital_pagado")
    private String capitalPagado;
    @ColumnInfo(name = "interes_pagado")
    private String interesPagado;
    @ColumnInfo(name = "iva_pagado")
    private String ivaPagado;
    @ColumnInfo(name = "interes_moratorio_pagado")
    private String interesMoratorioPagado;
    @ColumnInfo(name = "iva_moratorio_pagado")
    private String ivaMoratorioPagado;
    @ColumnInfo(name = "comision_pagada")
    private String comisionPagada;
    @ColumnInfo(name = "total_pagado")
    private String totalPagado;
    @ColumnInfo(name = "pagado")
    private String pagado;
    @ColumnInfo(name = "numero")
    private Integer numero;
    @ColumnInfo(name = "dias_atraso")
    private String diasAtraso;
    @ColumnInfo(name = "fecha_dispositivo")
    private String fechaDispositivo;
    @ColumnInfo(name = "fecha_actualizado")
    private String fechaActualizado;


// Getters & Setters

    public Long getId(){
        return this.id;
    }

    public void setId(Long id){
        this.id = id;
    }
    public String getIdAmortizacion(){
        return this.idAmortizacion;
    }

    public void setIdAmortizacion(String idAmortizacion){
        this.idAmortizacion = idAmortizacion;
    }
    public String getIdPrestamo(){
        return this.idPrestamo;
    }

    public void setIdPrestamo(String idPrestamo){
        this.idPrestamo = idPrestamo;
    }
    public String getFecha(){
        return this.fecha;
    }

    public void setFecha(String fecha){
        this.fecha = fecha;
    }
    public String getFechaPago(){
        return this.fechaPago;
    }

    public void setFechaPago(String fechaPago){
        this.fechaPago = fechaPago;
    }
    public String getCapital(){
        return this.capital;
    }

    public void setCapital(String capital){
        this.capital = capital;
    }
    public String getInteres(){
        return this.interes;
    }

    public void setInteres(String interes){
        this.interes = interes;
    }
    public String getIva(){
        return this.iva;
    }

    public void setIva(String iva){
        this.iva = iva;
    }
    public String getComision(){
        return this.comision;
    }

    public void setComision(String comision){
        this.comision = comision;
    }
    public String getTotal(){
        return this.total;
    }

    public void setTotal(String total){
        this.total = total;
    }
    public String getCapitalPagado(){
        return this.capitalPagado;
    }

    public void setCapitalPagado(String capitalPagado){
        this.capitalPagado = capitalPagado;
    }
    public String getInteresPagado(){
        return this.interesPagado;
    }

    public void setInteresPagado(String interesPagado){
        this.interesPagado = interesPagado;
    }
    public String getIvaPagado(){
        return this.ivaPagado;
    }

    public void setIvaPagado(String ivaPagado){
        this.ivaPagado = ivaPagado;
    }
    public String getInteresMoratorioPagado(){
        return this.interesMoratorioPagado;
    }

    public void setInteresMoratorioPagado(String interesMoratorioPagado){
        this.interesMoratorioPagado = interesMoratorioPagado;
    }
    public String getIvaMoratorioPagado(){
        return this.ivaMoratorioPagado;
    }

    public void setIvaMoratorioPagado(String ivaMoratorioPagado){
        this.ivaMoratorioPagado = ivaMoratorioPagado;
    }
    public String getComisionPagada(){
        return this.comisionPagada;
    }

    public void setComisionPagada(String comisionPagada){
        this.comisionPagada = comisionPagada;
    }
    public String getTotalPagado(){
        return this.totalPagado;
    }

    public void setTotalPagado(String totalPagado){
        this.totalPagado = totalPagado;
    }
    public String getPagado(){
        return this.pagado;
    }

    public void setPagado(String pagado){
        this.pagado = pagado;
    }
    public Integer getNumero(){
        return this.numero;
    }

    public void setNumero(Integer numero){
        this.numero = numero;
    }
    public String getDiasAtraso(){
        return this.diasAtraso;
    }

    public void setDiasAtraso(String diasAtraso){
        this.diasAtraso = diasAtraso;
    }
    public String getFechaDispositivo(){
        return this.fechaDispositivo;
    }

    public void setFechaDispositivo(String fechaDispositivo){
        this.fechaDispositivo = fechaDispositivo;
    }
    public String getFechaActualizado(){
        return this.fechaActualizado;
    }

    public void setFechaActualizado(String fechaActualizado){
        this.fechaActualizado = fechaActualizado;
    }

}