package com.sidert.sidertmovil.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MRespGestionadas implements Serializable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("fechaDefuncion")
    @Expose
    private Object fechaDefuncion;
    @SerializedName("fechaPromesaPago")
    @Expose
    private Object fechaPromesaPago;
    @SerializedName("usuarioId")
    @Expose
    private Integer usuarioId;
    @SerializedName("prestamo_id")
    @Expose
    private Integer prestamoId;
    @SerializedName("prestamo_id_integrante")
    @Expose
    private Object prestamoIdIntegrante;
    @SerializedName("num_solicitud")
    @Expose
    private Integer numSolicitud;
    @SerializedName("tipo_gestion")
    @Expose
    private Integer tipoGestion;
    @SerializedName("latitud")
    @Expose
    private Double latitud;
    @SerializedName("longitud")
    @Expose
    private Double longitud;
    @SerializedName("contacto")
    @Expose
    private Integer contacto;
    @SerializedName("actualizar_telefono")
    @Expose
    private Integer actualizarTelefono;
    @SerializedName("telefono_nuevo")
    @Expose
    private Object telefonoNuevo;
    @SerializedName("resultado_pago")
    @Expose
    private Integer resultadoPago;
    @SerializedName("medio_pago")
    @Expose
    private Integer medioPago;
    @SerializedName("fecha_pago")
    @Expose
    private String fechaPago;
    @SerializedName("pagara_requerido")
    @Expose
    private Integer pagaraRequerido;
    @SerializedName("detalle_ficha")
    @Expose
    private Integer detalleFicha;
    @SerializedName("pago_realizado")
    @Expose
    private Double pagoRealizado;
    @SerializedName("imprimir_recibo")
    @Expose
    private Object imprimirRecibo;
    @SerializedName("folio")
    @Expose
    private Object folio;
    @SerializedName("res_impresion")
    @Expose
    private Object resImpresion;
    @SerializedName("evidencia")
    @Expose
    private String evidencia;
    @SerializedName("tipo_imagen")
    @Expose
    private Integer tipoImagen;
    @SerializedName("saldo_corte")
    @Expose
    private Double saldoCorte;
    @SerializedName("saldo_actual")
    @Expose
    private Double saldoActual;
    @SerializedName("estatus")
    @Expose
    private String estatus;
    @SerializedName("supervison_gerente")
    @Expose
    private Integer supervisonGerente;
    @SerializedName("firma")
    @Expose
    private Object firma;
    @SerializedName("fecha_establecida")
    @Expose
    private String fechaEstablecida;
    @SerializedName("dia_semana")
    @Expose
    private String diaSemana;
    @SerializedName("tipo_prestamo_id")
    @Expose
    private Integer tipoPrestamoId;
    @SerializedName("monto_amortizacion")
    @Expose
    private Double montoAmortizacion;
    @SerializedName("atraso")
    @Expose
    private Integer atraso;
    @SerializedName("fecha_envio_dispositivo")
    @Expose
    private String fechaEnvioDispositivo;
    @SerializedName("fecha_inicio_gestion")
    @Expose
    private String fechaInicioGestion;
    @SerializedName("fecha_fin_gestion")
    @Expose
    private String fechaFinGestion;
    @SerializedName("serial_id")
    @Expose
    private Object serialId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Object getFechaDefuncion() {
        return fechaDefuncion;
    }

    public void setFechaDefuncion(Object fechaDefuncion) {
        this.fechaDefuncion = fechaDefuncion;
    }

    public Object getFechaPromesaPago() {
        return fechaPromesaPago;
    }

    public void setFechaPromesaPago(Object fechaPromesaPago) {
        this.fechaPromesaPago = fechaPromesaPago;
    }

    public Integer getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Integer usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Integer getPrestamoId() {
        return prestamoId;
    }

    public void setPrestamoId(Integer prestamoId) {
        this.prestamoId = prestamoId;
    }

    public Object getPrestamoIdIntegrante() {
        return prestamoIdIntegrante;
    }

    public void setPrestamoIdIntegrante(Object prestamoIdIntegrante) {
        this.prestamoIdIntegrante = prestamoIdIntegrante;
    }

    public Integer getNumSolicitud() {
        return numSolicitud;
    }

    public void setNumSolicitud(Integer numSolicitud) {
        this.numSolicitud = numSolicitud;
    }

    public Integer getTipoGestion() {
        return tipoGestion;
    }

    public void setTipoGestion(Integer tipoGestion) {
        this.tipoGestion = tipoGestion;
    }

    public Double getLatitud() {
        return latitud;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    public Double getLongitud() {
        return longitud;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }

    public Integer getContacto() {
        return contacto;
    }

    public void setContacto(Integer contacto) {
        this.contacto = contacto;
    }

    public Integer getActualizarTelefono() {
        return actualizarTelefono;
    }

    public void setActualizarTelefono(Integer actualizarTelefono) {
        this.actualizarTelefono = actualizarTelefono;
    }

    public Object getTelefonoNuevo() {
        return telefonoNuevo;
    }

    public void setTelefonoNuevo(Object telefonoNuevo) {
        this.telefonoNuevo = telefonoNuevo;
    }

    public Integer getResultadoPago() {
        return resultadoPago;
    }

    public void setResultadoPago(Integer resultadoPago) {
        this.resultadoPago = resultadoPago;
    }

    public Integer getMedioPago() {
        return medioPago;
    }

    public void setMedioPago(Integer medioPago) {
        this.medioPago = medioPago;
    }

    public String getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(String fechaPago) {
        this.fechaPago = fechaPago;
    }

    public Integer getPagaraRequerido() {
        return pagaraRequerido;
    }

    public void setPagaraRequerido(Integer pagaraRequerido) {
        this.pagaraRequerido = pagaraRequerido;
    }

    public Integer getDetalleFicha() {
        return detalleFicha;
    }

    public void setDetalleFicha(Integer detalleFicha) {
        this.detalleFicha = detalleFicha;
    }

    public Double getPagoRealizado() {
        return pagoRealizado;
    }

    public void setPagoRealizado(Double pagoRealizado) {
        this.pagoRealizado = pagoRealizado;
    }

    public Object getImprimirRecibo() {
        return imprimirRecibo;
    }

    public void setImprimirRecibo(Object imprimirRecibo) {
        this.imprimirRecibo = imprimirRecibo;
    }

    public Object getFolio() {
        return folio;
    }

    public void setFolio(Object folio) {
        this.folio = folio;
    }

    public Object getResImpresion() {
        return resImpresion;
    }

    public void setResImpresion(Object resImpresion) {
        this.resImpresion = resImpresion;
    }

    public String getEvidencia() {
        return evidencia;
    }

    public void setEvidencia(String evidencia) {
        this.evidencia = evidencia;
    }

    public Integer getTipoImagen() {
        return tipoImagen;
    }

    public void setTipoImagen(Integer tipoImagen) {
        this.tipoImagen = tipoImagen;
    }

    public Double getSaldoCorte() {
        return saldoCorte;
    }

    public void setSaldoCorte(Double saldoCorte) {
        this.saldoCorte = saldoCorte;
    }

    public Double getSaldoActual() {
        return saldoActual;
    }

    public void setSaldoActual(Double saldoActual) {
        this.saldoActual = saldoActual;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    public Integer getSupervisonGerente() {
        return supervisonGerente;
    }

    public void setSupervisonGerente(Integer supervisonGerente) {
        this.supervisonGerente = supervisonGerente;
    }

    public Object getFirma() {
        return firma;
    }

    public void setFirma(Object firma) {
        this.firma = firma;
    }

    public String getFechaEstablecida() {
        return fechaEstablecida;
    }

    public void setFechaEstablecida(String fechaEstablecida) {
        this.fechaEstablecida = fechaEstablecida;
    }

    public String getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(String diaSemana) {
        this.diaSemana = diaSemana;
    }

    public Integer getTipoPrestamoId() {
        return tipoPrestamoId;
    }

    public void setTipoPrestamoId(Integer tipoPrestamoId) {
        this.tipoPrestamoId = tipoPrestamoId;
    }

    public Double getMontoAmortizacion() {
        return montoAmortizacion;
    }

    public void setMontoAmortizacion(Double montoAmortizacion) {
        this.montoAmortizacion = montoAmortizacion;
    }

    public Integer getAtraso() {
        return atraso;
    }

    public void setAtraso(Integer atraso) {
        this.atraso = atraso;
    }

    public String getFechaEnvioDispositivo() {
        return fechaEnvioDispositivo;
    }

    public void setFechaEnvioDispositivo(String fechaEnvioDispositivo) {
        this.fechaEnvioDispositivo = fechaEnvioDispositivo;
    }

    public String getFechaInicioGestion() {
        return fechaInicioGestion;
    }

    public void setFechaInicioGestion(String fechaInicioGestion) {
        this.fechaInicioGestion = fechaInicioGestion;
    }

    public String getFechaFinGestion() {
        return fechaFinGestion;
    }

    public void setFechaFinGestion(String fechaFinGestion) {
        this.fechaFinGestion = fechaFinGestion;
    }

    public Object getSerialId() {
        return serialId;
    }

    public void setSerialId(Object serialId) {
        this.serialId = serialId;
    }
}
