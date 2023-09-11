package com.sidert.sidertmovil.v2.domain.entities;

import java.io.Serializable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tbl_prestamos_ind_t")
public final class PrestamosInd implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    private Long id;
    @ColumnInfo(name = "id_prestamo")
    private String idPrestamo;
    @ColumnInfo(name = "id_cliente")
    private String idCliente;
    @ColumnInfo(name = "num_prestamo")
    private String numPrestamo;
    @ColumnInfo(name = "num_solicitud")
    private String numSolicitud;
    @ColumnInfo(name = "fecha_entrega")
    private String fechaEntrega;
    @ColumnInfo(name = "monto_otorgado")
    private String montoOtorgado;
    @ColumnInfo(name = "monto_total")
    private String montoTotal;
    @ColumnInfo(name = "monto_amortizacion")
    private String montoAmortizacion;
    @ColumnInfo(name = "monto_requerido")
    private String montoRequerido;
    @ColumnInfo(name = "num_amortizacion")
    private Integer numAmortizacion;
    @ColumnInfo(name = "fecha_establecida")
    private String fechaEstablecida;
    @ColumnInfo(name = "tipo_cartera")
    private String tipoCartera;
    @ColumnInfo(name = "pagada")
    private Integer pagada;
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
    public String getIdPrestamo(){
        return this.idPrestamo;
    }

    public void setIdPrestamo(String idPrestamo){
        this.idPrestamo = idPrestamo;
    }
    public String getIdCliente(){
        return this.idCliente;
    }

    public void setIdCliente(String idCliente){
        this.idCliente = idCliente;
    }
    public String getNumPrestamo(){
        return this.numPrestamo;
    }

    public void setNumPrestamo(String numPrestamo){
        this.numPrestamo = numPrestamo;
    }
    public String getNumSolicitud(){
        return this.numSolicitud;
    }

    public void setNumSolicitud(String numSolicitud){
        this.numSolicitud = numSolicitud;
    }
    public String getFechaEntrega(){
        return this.fechaEntrega;
    }

    public void setFechaEntrega(String fechaEntrega){
        this.fechaEntrega = fechaEntrega;
    }
    public String getMontoOtorgado(){
        return this.montoOtorgado;
    }

    public void setMontoOtorgado(String montoOtorgado){
        this.montoOtorgado = montoOtorgado;
    }
    public String getMontoTotal(){
        return this.montoTotal;
    }

    public void setMontoTotal(String montoTotal){
        this.montoTotal = montoTotal;
    }
    public String getMontoAmortizacion(){
        return this.montoAmortizacion;
    }

    public void setMontoAmortizacion(String montoAmortizacion){
        this.montoAmortizacion = montoAmortizacion;
    }
    public String getMontoRequerido(){
        return this.montoRequerido;
    }

    public void setMontoRequerido(String montoRequerido){
        this.montoRequerido = montoRequerido;
    }
    public Integer getNumAmortizacion(){
        return this.numAmortizacion;
    }

    public void setNumAmortizacion(Integer numAmortizacion){
        this.numAmortizacion = numAmortizacion;
    }
    public String getFechaEstablecida(){
        return this.fechaEstablecida;
    }

    public void setFechaEstablecida(String fechaEstablecida){
        this.fechaEstablecida = fechaEstablecida;
    }
    public String getTipoCartera(){
        return this.tipoCartera;
    }

    public void setTipoCartera(String tipoCartera){
        this.tipoCartera = tipoCartera;
    }
    public Integer getPagada(){
        return this.pagada;
    }

    public void setPagada(Integer pagada){
        this.pagada = pagada;
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