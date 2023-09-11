package com.sidert.sidertmovil.v2.domain.entities;

import java.io.Serializable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tbl_credito_ind_auto")
public final class CreditoIndAuto implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_credito")
    private Long idCredito;
    @ColumnInfo(name = "id_solicitud")
    private Integer idSolicitud;
    @ColumnInfo(name = "plazo")
    private String plazo;
    @ColumnInfo(name = "periodicidad")
    private String periodicidad;
    @ColumnInfo(name = "fecha_desembolso")
    private String fechaDesembolso;
    @ColumnInfo(name = "dia_desembolso")
    private String diaDesembolso;
    @ColumnInfo(name = "hora_visita")
    private String horaVisita;
    @ColumnInfo(name = "monto_prestamo")
    private String montoPrestamo;
    @ColumnInfo(name = "ciclo")
    private String ciclo;
    @ColumnInfo(name = "credito_anterior")
    private String creditoAnterior;
    @ColumnInfo(name = "comportamiento_pago")
    private String comportamientoPago;
    @ColumnInfo(name = "num_cliente")
    private String numCliente;
    @ColumnInfo(name = "observaciones")
    private String observaciones;
    @ColumnInfo(name = "destino")
    private String destino;
    @ColumnInfo(name = "clasificacion_riesgo")
    private String clasificacionRiesgo;
    @ColumnInfo(name = "estatus_completado")
    private Integer estatusCompletado;
    @ColumnInfo(name = "monto_autorizado")
    private String montoAutorizado;


// Getters & Setters

    public Long getIdCredito(){
        return this.idCredito;
    }

    public void setIdCredito(Long idCredito){
        this.idCredito = idCredito;
    }
    public Integer getIdSolicitud(){
        return this.idSolicitud;
    }

    public void setIdSolicitud(Integer idSolicitud){
        this.idSolicitud = idSolicitud;
    }
    public String getPlazo(){
        return this.plazo;
    }

    public void setPlazo(String plazo){
        this.plazo = plazo;
    }
    public String getPeriodicidad(){
        return this.periodicidad;
    }

    public void setPeriodicidad(String periodicidad){
        this.periodicidad = periodicidad;
    }
    public String getFechaDesembolso(){
        return this.fechaDesembolso;
    }

    public void setFechaDesembolso(String fechaDesembolso){
        this.fechaDesembolso = fechaDesembolso;
    }
    public String getDiaDesembolso(){
        return this.diaDesembolso;
    }

    public void setDiaDesembolso(String diaDesembolso){
        this.diaDesembolso = diaDesembolso;
    }
    public String getHoraVisita(){
        return this.horaVisita;
    }

    public void setHoraVisita(String horaVisita){
        this.horaVisita = horaVisita;
    }
    public String getMontoPrestamo(){
        return this.montoPrestamo;
    }

    public void setMontoPrestamo(String montoPrestamo){
        this.montoPrestamo = montoPrestamo;
    }
    public String getCiclo(){
        return this.ciclo;
    }

    public void setCiclo(String ciclo){
        this.ciclo = ciclo;
    }
    public String getCreditoAnterior(){
        return this.creditoAnterior;
    }

    public void setCreditoAnterior(String creditoAnterior){
        this.creditoAnterior = creditoAnterior;
    }
    public String getComportamientoPago(){
        return this.comportamientoPago;
    }

    public void setComportamientoPago(String comportamientoPago){
        this.comportamientoPago = comportamientoPago;
    }
    public String getNumCliente(){
        return this.numCliente;
    }

    public void setNumCliente(String numCliente){
        this.numCliente = numCliente;
    }
    public String getObservaciones(){
        return this.observaciones;
    }

    public void setObservaciones(String observaciones){
        this.observaciones = observaciones;
    }
    public String getDestino(){
        return this.destino;
    }

    public void setDestino(String destino){
        this.destino = destino;
    }
    public String getClasificacionRiesgo(){
        return this.clasificacionRiesgo;
    }

    public void setClasificacionRiesgo(String clasificacionRiesgo){
        this.clasificacionRiesgo = clasificacionRiesgo;
    }
    public Integer getEstatusCompletado(){
        return this.estatusCompletado;
    }

    public void setEstatusCompletado(Integer estatusCompletado){
        this.estatusCompletado = estatusCompletado;
    }
    public String getMontoAutorizado(){
        return this.montoAutorizado;
    }

    public void setMontoAutorizado(String montoAutorizado){
        this.montoAutorizado = montoAutorizado;
    }

}