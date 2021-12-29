package com.sidert.sidertmovil.models.gestion.carteravencida;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class GestionIndividual implements Serializable {
    @SerializedName("_id")
    @Expose
    private Integer id;

    @SerializedName("id_prestamo")
    @Expose
    private String idPrestamo;

    @SerializedName("latitud")
    @Expose
    private String latitud;

    @SerializedName("longitud")
    @Expose
    private String longitud;

    @SerializedName("contacto")
    @Expose
    private String contacto;

    @SerializedName("comentario")
    @Expose
    private String comentario;

    @SerializedName("actualizar_telefono")
    @Expose
    private String actualizarTelefono;

    @SerializedName("nuevo_telefono")
    @Expose
    private String nuevoTelefono;

    @SerializedName("resultado_gestion")
    @Expose
    private String resultadoGestion;

    @SerializedName("motivo_no_pago")
    @Expose
    private String motivoNoPago;

    @SerializedName("fecha_fallecimiento")
    @Expose
    private String fechaFallecimiento;

    @SerializedName("fecha_monto_promesa")
    @Expose
    private String fechaMontoPromesa;

    @SerializedName("monto_promesa")
    @Expose
    private String montoPromesa;

    @SerializedName("medio_pago")
    @Expose
    private String medioPago;

    @SerializedName("fecha_pago")
    @Expose
    private String fechaPago;

    @SerializedName("pagara_requerido")
    @Expose
    private String pagaraRequerido;

    @SerializedName("pago_realizado")
    @Expose
    private String pagoRealizado;

    @SerializedName("imprimir_recibo")
    @Expose
    private String imprimirRecibo;

    @SerializedName("folio")
    @Expose
    private String folio;

    @SerializedName("evidencia")
    @Expose
    private String evidencia;

    @SerializedName("tipo_imagen")
    @Expose
    private String tipoImagen;

    @SerializedName("gerente")
    @Expose
    private String gerente;

    @SerializedName("firma")
    @Expose
    private String firma;

    @SerializedName("fecha_inicio")
    @Expose
    private String fechaInicio;

    @SerializedName("fecha_fin")
    @Expose
    private String fechaFin;

    @SerializedName("fecha_envio")
    @Expose
    private String fechaEnvio;

    @SerializedName("estatus")
    @Expose
    private String estatus;

    @SerializedName("res_impresion")
    @Expose
    private String resImpresion;

    @SerializedName("estatus_pago")
    @Expose
    private String estatusPago;

    @SerializedName("saldo_corte")
    @Expose
    private String saldoCorte;

    @SerializedName("saldo_actual")
    @Expose
    private String saldoActual;

    @SerializedName("dias_atraso")
    @Expose
    private String diasAtraso;

    @SerializedName("serial_id")
    @Expose
    private String serialId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIdPrestamo() {
        return idPrestamo;
    }

    public void setIdPrestamo(String idPrestamo) {
        this.idPrestamo = idPrestamo;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public String getActualizarTelefono() {
        return actualizarTelefono;
    }

    public void setActualizarTelefono(String actualizarTelefono) {
        this.actualizarTelefono = actualizarTelefono;
    }

    public String getNuevoTelefono() {
        return nuevoTelefono;
    }

    public void setNuevoTelefono(String nuevoTelefono) {
        this.nuevoTelefono = nuevoTelefono;
    }

    public String getResultadoGestion() {
        return resultadoGestion;
    }

    public void setResultadoGestion(String resultadoGestion) {
        this.resultadoGestion = resultadoGestion;
    }

    public String getMotivoNoPago() {
        return motivoNoPago;
    }

    public void setMotivoNoPago(String motivoNoPago) {
        this.motivoNoPago = motivoNoPago;
    }

    public String getFechaFallecimiento() {
        return fechaFallecimiento;
    }

    public void setFechaFallecimiento(String fechaFallecimiento) {
        this.fechaFallecimiento = fechaFallecimiento;
    }

    public String getFechaMontoPromesa() {
        return fechaMontoPromesa;
    }

    public void setFechaMontoPromesa(String fechaMontoPromesa) {
        this.fechaMontoPromesa = fechaMontoPromesa;
    }

    public String getMontoPromesa() {
        return montoPromesa;
    }

    public void setMontoPromesa(String montoPromesa) {
        this.montoPromesa = montoPromesa;
    }

    public String getMedioPago() {
        return medioPago;
    }

    public void setMedioPago(String medioPago) {
        this.medioPago = medioPago;
    }

    public String getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(String fechaPago) {
        this.fechaPago = fechaPago;
    }

    public String getPagaraRequerido() {
        return pagaraRequerido;
    }

    public void setPagaraRequerido(String pagaraRequerido) {
        this.pagaraRequerido = pagaraRequerido;
    }

    public String getPagoRealizado() {
        return pagoRealizado;
    }

    public void setPagoRealizado(String pagoRealizado) {
        this.pagoRealizado = pagoRealizado;
    }

    public String getImprimirRecibo() {
        return imprimirRecibo;
    }

    public void setImprimirRecibo(String imprimirRecibo) {
        this.imprimirRecibo = imprimirRecibo;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public String getEvidencia() {
        return evidencia;
    }

    public void setEvidencia(String evidencia) {
        this.evidencia = evidencia;
    }

    public String getTipoImagen() {
        return tipoImagen;
    }

    public void setTipoImagen(String tipoImagen) {
        this.tipoImagen = tipoImagen;
    }

    public String getGerente() {
        return gerente;
    }

    public void setGerente(String gerente) {
        this.gerente = gerente;
    }

    public String getFirma() {
        return firma;
    }

    public void setFirma(String firma) {
        this.firma = firma;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getFechaEnvio() {
        return fechaEnvio;
    }

    public void setFechaEnvio(String fechaEnvio) {
        this.fechaEnvio = fechaEnvio;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    public String getResImpresion() {
        return resImpresion;
    }

    public void setResImpresion(String resImpresion) {
        this.resImpresion = resImpresion;
    }

    public String getEstatusPago() {
        return estatusPago;
    }

    public void setEstatusPago(String estatusPago) {
        this.estatusPago = estatusPago;
    }

    public String getSaldoCorte() {
        return saldoCorte;
    }

    public void setSaldoCorte(String saldoCorte) {
        this.saldoCorte = saldoCorte;
    }

    public String getSaldoActual() {
        return saldoActual;
    }

    public void setSaldoActual(String saldoActual) {
        this.saldoActual = saldoActual;
    }

    public String getDiasAtraso() {
        return diasAtraso;
    }

    public void setDiasAtraso(String diasAtraso) {
        this.diasAtraso = diasAtraso;
    }

    public String getSerialId() {
        return serialId;
    }

    public void setSerialId(String serialId) {
        this.serialId = serialId;
    }
}
