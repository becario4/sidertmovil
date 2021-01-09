package com.sidert.sidertmovil.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MPrestamoRes {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("num_prestamo")
    @Expose
    private String numPrestamo;
    @SerializedName("num_solicitud")
    @Expose
    private Integer numSolicitud;
    @SerializedName("fecha_entrega")
    @Expose
    private String fechaEntrega;
    @SerializedName("monto_otorgado")
    @Expose
    private Double montoOtorgado;
    @SerializedName("monto_total")
    @Expose
    private Double montoTotal;
    @SerializedName("monto_amortizacion")
    @Expose
    private Double montoAmortizacion;
    @SerializedName("monto_requerido")
    @Expose
    private Double montoRequerido;
    @SerializedName("num_amortizacion")
    @Expose
    private Integer numAmortizacion;
    @SerializedName("fecha_establecida")
    @Expose
    private String fechaEstablecida;
    @SerializedName("cliente_id")
    @Expose
    private Integer clienteId;
    @SerializedName("tipo_cartera")
    @Expose
    private String tipoCartera;
    @SerializedName("pagada")
    @Expose
    private String pagada;
    @SerializedName("amortizaciones")
    @Expose
    private List<MAmortizacion> amortizaciones = null;
    @SerializedName("aval")
    @Expose
    private MAval aval;
    @SerializedName("pagos")
    @Expose
    private List<MPago> pagos = null;
    @SerializedName("tel_casa")
    @Expose
    private String telCasa;
    @SerializedName("tel_celular")
    @Expose
    private String telCelular;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumPrestamo() {
        return numPrestamo;
    }

    public void setNumPrestamo(String numPrestamo) {
        this.numPrestamo = numPrestamo;
    }

    public Integer getNumSolicitud() {
        return numSolicitud;
    }

    public void setNumSolicitud(Integer numSolicitud) {
        this.numSolicitud = numSolicitud;
    }

    public String getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(String fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public Double getMontoOtorgado() {
        return montoOtorgado;
    }

    public void setMontoOtorgado(Double montoOtorgado) {
        this.montoOtorgado = montoOtorgado;
    }

    public Double getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(Double montoTotal) {
        this.montoTotal = montoTotal;
    }

    public Double getMontoAmortizacion() {
        return montoAmortizacion;
    }

    public void setMontoAmortizacion(Double montoAmortizacion) {
        this.montoAmortizacion = montoAmortizacion;
    }

    public Double getMontoRequerido() {
        return montoRequerido;
    }

    public void setMontoRequerido(Double montoRequerido) {
        this.montoRequerido = montoRequerido;
    }

    public Integer getNumAmortizacion() {
        return numAmortizacion;
    }

    public void setNumAmortizacion(Integer numAmortizacion) {
        this.numAmortizacion = numAmortizacion;
    }

    public String getFechaEstablecida() {
        return fechaEstablecida;
    }

    public void setFechaEstablecida(String fechaEstablecida) {
        this.fechaEstablecida = fechaEstablecida;
    }

    public Integer getClienteId() {
        return clienteId;
    }

    public void setClienteId(Integer clienteId) {
        this.clienteId = clienteId;
    }

    public String getTipoCartera() {
        return tipoCartera;
    }

    public void setTipoCartera(String tipoCartera) {
        this.tipoCartera = tipoCartera;
    }

    public String getPagada() {
        return pagada;
    }

    public void setPagada(String pagada) {
        this.pagada = pagada;
    }

    public List<MAmortizacion> getAmortizaciones() {
        return amortizaciones;
    }

    public void setAmortizaciones(List<MAmortizacion> amortizaciones) {
        this.amortizaciones = amortizaciones;
    }

    public MAval getAval() {
        return aval;
    }

    public void setAval(MAval aval) {
        this.aval = aval;
    }

    public List<MPago> getPagos() {
        return pagos;
    }

    public void setPagos(List<MPago> pagos) {
        this.pagos = pagos;
    }

    public String getTelCasa() {
        return telCasa;
    }

    public void setTelCasa(String telCasa) {
        this.telCasa = telCasa;
    }

    public String getTelCelular() {
        return telCelular;
    }

    public void setTelCelular(String telCelular) {
        this.telCelular = telCelular;
    }
}
