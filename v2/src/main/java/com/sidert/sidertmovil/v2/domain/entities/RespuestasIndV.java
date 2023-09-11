package com.sidert.sidertmovil.v2.domain.entities;

import java.io.Serializable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "respuestas_ind_v_t")
public final class RespuestasIndV implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    private Long id;
    @ColumnInfo(name = "id_prestamo")
    private String idPrestamo;
    @ColumnInfo(name = "latitud")
    private String latitud;
    @ColumnInfo(name = "longitud")
    private String longitud;
    @ColumnInfo(name = "contacto")
    private String contacto;
    @ColumnInfo(name = "comentario")
    private String comentario;
    @ColumnInfo(name = "actualizar_telefono")
    private String actualizarTelefono;
    @ColumnInfo(name = "nuevo_telefono")
    private String nuevoTelefono;
    @ColumnInfo(name = "resultado_gestion")
    private String resultadoGestion;
    @ColumnInfo(name = "motivo_no_pago")
    private String motivoNoPago;
    @ColumnInfo(name = "fecha_fallecimiento")
    private String fechaFallecimiento;
    @ColumnInfo(name = "fecha_monto_promesa")
    private String fechaMontoPromesa;
    @ColumnInfo(name = "monto_promesa")
    private String montoPromesa;
    @ColumnInfo(name = "medio_pago")
    private String medioPago;
    @ColumnInfo(name = "fecha_pago")
    private String fechaPago;
    @ColumnInfo(name = "pagara_requerido")
    private String pagaraRequerido;
    @ColumnInfo(name = "pago_realizado")
    private String pagoRealizado;
    @ColumnInfo(name = "imprimir_recibo")
    private String imprimirRecibo;
    @ColumnInfo(name = "folio")
    private String folio;
    @ColumnInfo(name = "evidencia")
    private String evidencia;
    @ColumnInfo(name = "tipo_imagen")
    private String tipoImagen;
    @ColumnInfo(name = "gerente")
    private String gerente;
    @ColumnInfo(name = "firma")
    private String firma;
    @ColumnInfo(name = "fecha_inicio")
    private String fechaInicio;
    @ColumnInfo(name = "fecha_fin")
    private String fechaFin;
    @ColumnInfo(name = "fecha_envio")
    private String fechaEnvio;
    @ColumnInfo(name = "estatus")
    private String estatus;
    @ColumnInfo(name = "res_impresion",defaultValue="'0'")
    private String resImpresion;
    @ColumnInfo(name = "estatus_pago",defaultValue="''")
    private String estatusPago;
    @ColumnInfo(name = "saldo_corte",defaultValue="''")
    private String saldoCorte;
    @ColumnInfo(name = "saldo_actual",defaultValue="''")
    private String saldoActual;
    @ColumnInfo(name = "dias_atraso",defaultValue="'0'")
    private String diasAtraso;
    @ColumnInfo(name = "serial_id",defaultValue="'0'")
    private String serialId;


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
    public String getLatitud(){
        return this.latitud;
    }

    public void setLatitud(String latitud){
        this.latitud = latitud;
    }
    public String getLongitud(){
        return this.longitud;
    }

    public void setLongitud(String longitud){
        this.longitud = longitud;
    }
    public String getContacto(){
        return this.contacto;
    }

    public void setContacto(String contacto){
        this.contacto = contacto;
    }
    public String getComentario(){
        return this.comentario;
    }

    public void setComentario(String comentario){
        this.comentario = comentario;
    }
    public String getActualizarTelefono(){
        return this.actualizarTelefono;
    }

    public void setActualizarTelefono(String actualizarTelefono){
        this.actualizarTelefono = actualizarTelefono;
    }
    public String getNuevoTelefono(){
        return this.nuevoTelefono;
    }

    public void setNuevoTelefono(String nuevoTelefono){
        this.nuevoTelefono = nuevoTelefono;
    }
    public String getResultadoGestion(){
        return this.resultadoGestion;
    }

    public void setResultadoGestion(String resultadoGestion){
        this.resultadoGestion = resultadoGestion;
    }
    public String getMotivoNoPago(){
        return this.motivoNoPago;
    }

    public void setMotivoNoPago(String motivoNoPago){
        this.motivoNoPago = motivoNoPago;
    }
    public String getFechaFallecimiento(){
        return this.fechaFallecimiento;
    }

    public void setFechaFallecimiento(String fechaFallecimiento){
        this.fechaFallecimiento = fechaFallecimiento;
    }
    public String getFechaMontoPromesa(){
        return this.fechaMontoPromesa;
    }

    public void setFechaMontoPromesa(String fechaMontoPromesa){
        this.fechaMontoPromesa = fechaMontoPromesa;
    }
    public String getMontoPromesa(){
        return this.montoPromesa;
    }

    public void setMontoPromesa(String montoPromesa){
        this.montoPromesa = montoPromesa;
    }
    public String getMedioPago(){
        return this.medioPago;
    }

    public void setMedioPago(String medioPago){
        this.medioPago = medioPago;
    }
    public String getFechaPago(){
        return this.fechaPago;
    }

    public void setFechaPago(String fechaPago){
        this.fechaPago = fechaPago;
    }
    public String getPagaraRequerido(){
        return this.pagaraRequerido;
    }

    public void setPagaraRequerido(String pagaraRequerido){
        this.pagaraRequerido = pagaraRequerido;
    }
    public String getPagoRealizado(){
        return this.pagoRealizado;
    }

    public void setPagoRealizado(String pagoRealizado){
        this.pagoRealizado = pagoRealizado;
    }
    public String getImprimirRecibo(){
        return this.imprimirRecibo;
    }

    public void setImprimirRecibo(String imprimirRecibo){
        this.imprimirRecibo = imprimirRecibo;
    }
    public String getFolio(){
        return this.folio;
    }

    public void setFolio(String folio){
        this.folio = folio;
    }
    public String getEvidencia(){
        return this.evidencia;
    }

    public void setEvidencia(String evidencia){
        this.evidencia = evidencia;
    }
    public String getTipoImagen(){
        return this.tipoImagen;
    }

    public void setTipoImagen(String tipoImagen){
        this.tipoImagen = tipoImagen;
    }
    public String getGerente(){
        return this.gerente;
    }

    public void setGerente(String gerente){
        this.gerente = gerente;
    }
    public String getFirma(){
        return this.firma;
    }

    public void setFirma(String firma){
        this.firma = firma;
    }
    public String getFechaInicio(){
        return this.fechaInicio;
    }

    public void setFechaInicio(String fechaInicio){
        this.fechaInicio = fechaInicio;
    }
    public String getFechaFin(){
        return this.fechaFin;
    }

    public void setFechaFin(String fechaFin){
        this.fechaFin = fechaFin;
    }
    public String getFechaEnvio(){
        return this.fechaEnvio;
    }

    public void setFechaEnvio(String fechaEnvio){
        this.fechaEnvio = fechaEnvio;
    }
    public String getEstatus(){
        return this.estatus;
    }

    public void setEstatus(String estatus){
        this.estatus = estatus;
    }
    public String getResImpresion(){
        return this.resImpresion;
    }

    public void setResImpresion(String resImpresion){
        this.resImpresion = resImpresion;
    }
    public String getEstatusPago(){
        return this.estatusPago;
    }

    public void setEstatusPago(String estatusPago){
        this.estatusPago = estatusPago;
    }
    public String getSaldoCorte(){
        return this.saldoCorte;
    }

    public void setSaldoCorte(String saldoCorte){
        this.saldoCorte = saldoCorte;
    }
    public String getSaldoActual(){
        return this.saldoActual;
    }

    public void setSaldoActual(String saldoActual){
        this.saldoActual = saldoActual;
    }
    public String getDiasAtraso(){
        return this.diasAtraso;
    }

    public void setDiasAtraso(String diasAtraso){
        this.diasAtraso = diasAtraso;
    }
    public String getSerialId(){
        return this.serialId;
    }

    public void setSerialId(String serialId){
        this.serialId = serialId;
    }

}