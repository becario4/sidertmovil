package com.sidert.sidertmovil.v2.domain.entities;

import java.io.Serializable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tbl_negocio_ind_ren")
public final class NegocioIndRen implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_negocio")
    private Long idNegocio;
    @ColumnInfo(name = "id_solicitud")
    private Integer idSolicitud;
    @ColumnInfo(name = "nombre")
    private String nombre;
    @ColumnInfo(name = "direccion_id")
    private String direccionId;
    @ColumnInfo(name = "ocupacion")
    private String ocupacion;
    @ColumnInfo(name = "actividad_economica")
    private String actividadEconomica;
    @ColumnInfo(name = "destino_credito")
    private String destinoCredito;
    @ColumnInfo(name = "otro_destino")
    private String otroDestino;
    @ColumnInfo(name = "antiguedad")
    private Integer antiguedad;
    @ColumnInfo(name = "ing_mensual")
    private String ingMensual;
    @ColumnInfo(name = "ing_otros")
    private String ingOtros;
    @ColumnInfo(name = "gasto_semanal")
    private String gastoSemanal;
    @ColumnInfo(name = "gasto_agua")
    private String gastoAgua;
    @ColumnInfo(name = "gasto_luz")
    private String gastoLuz;
    @ColumnInfo(name = "gasto_telefono")
    private String gastoTelefono;
    @ColumnInfo(name = "gasto_renta")
    private String gastoRenta;
    @ColumnInfo(name = "gasto_otros")
    private String gastoOtros;
    @ColumnInfo(name = "capacidad_pago")
    private String capacidadPago;
    @ColumnInfo(name = "medio_pago")
    private String medioPago;
    @ColumnInfo(name = "otro_medio_pago")
    private String otroMedioPago;
    @ColumnInfo(name = "monto_maximo")
    private String montoMaximo;
    @ColumnInfo(name = "num_operacion_mensuales")
    private Integer numOperacionMensuales;
    @ColumnInfo(name = "num_operacion_efectivo")
    private Integer numOperacionEfectivo;
    @ColumnInfo(name = "dias_venta")
    private String diasVenta;
    @ColumnInfo(name = "foto_fachada")
    private String fotoFachada;
    @ColumnInfo(name = "ref_domiciliaria")
    private String refDomiciliaria;
    @ColumnInfo(name = "estatus_completado")
    private Integer estatusCompletado;
    @ColumnInfo(name = "comentario_rechazo")
    private String comentarioRechazo;
    @ColumnInfo(name = "ubicado_en_dom_cli",defaultValue="'NO'")
    private String ubicadoEnDomCli;


// Getters & Setters

    public Long getIdNegocio(){
        return this.idNegocio;
    }

    public void setIdNegocio(Long idNegocio){
        this.idNegocio = idNegocio;
    }
    public Integer getIdSolicitud(){
        return this.idSolicitud;
    }

    public void setIdSolicitud(Integer idSolicitud){
        this.idSolicitud = idSolicitud;
    }
    public String getNombre(){
        return this.nombre;
    }

    public void setNombre(String nombre){
        this.nombre = nombre;
    }
    public String getDireccionId(){
        return this.direccionId;
    }

    public void setDireccionId(String direccionId){
        this.direccionId = direccionId;
    }
    public String getOcupacion(){
        return this.ocupacion;
    }

    public void setOcupacion(String ocupacion){
        this.ocupacion = ocupacion;
    }
    public String getActividadEconomica(){
        return this.actividadEconomica;
    }

    public void setActividadEconomica(String actividadEconomica){
        this.actividadEconomica = actividadEconomica;
    }
    public String getDestinoCredito(){
        return this.destinoCredito;
    }

    public void setDestinoCredito(String destinoCredito){
        this.destinoCredito = destinoCredito;
    }
    public String getOtroDestino(){
        return this.otroDestino;
    }

    public void setOtroDestino(String otroDestino){
        this.otroDestino = otroDestino;
    }
    public Integer getAntiguedad(){
        return this.antiguedad;
    }

    public void setAntiguedad(Integer antiguedad){
        this.antiguedad = antiguedad;
    }
    public String getIngMensual(){
        return this.ingMensual;
    }

    public void setIngMensual(String ingMensual){
        this.ingMensual = ingMensual;
    }
    public String getIngOtros(){
        return this.ingOtros;
    }

    public void setIngOtros(String ingOtros){
        this.ingOtros = ingOtros;
    }
    public String getGastoSemanal(){
        return this.gastoSemanal;
    }

    public void setGastoSemanal(String gastoSemanal){
        this.gastoSemanal = gastoSemanal;
    }
    public String getGastoAgua(){
        return this.gastoAgua;
    }

    public void setGastoAgua(String gastoAgua){
        this.gastoAgua = gastoAgua;
    }
    public String getGastoLuz(){
        return this.gastoLuz;
    }

    public void setGastoLuz(String gastoLuz){
        this.gastoLuz = gastoLuz;
    }
    public String getGastoTelefono(){
        return this.gastoTelefono;
    }

    public void setGastoTelefono(String gastoTelefono){
        this.gastoTelefono = gastoTelefono;
    }
    public String getGastoRenta(){
        return this.gastoRenta;
    }

    public void setGastoRenta(String gastoRenta){
        this.gastoRenta = gastoRenta;
    }
    public String getGastoOtros(){
        return this.gastoOtros;
    }

    public void setGastoOtros(String gastoOtros){
        this.gastoOtros = gastoOtros;
    }
    public String getCapacidadPago(){
        return this.capacidadPago;
    }

    public void setCapacidadPago(String capacidadPago){
        this.capacidadPago = capacidadPago;
    }
    public String getMedioPago(){
        return this.medioPago;
    }

    public void setMedioPago(String medioPago){
        this.medioPago = medioPago;
    }
    public String getOtroMedioPago(){
        return this.otroMedioPago;
    }

    public void setOtroMedioPago(String otroMedioPago){
        this.otroMedioPago = otroMedioPago;
    }
    public String getMontoMaximo(){
        return this.montoMaximo;
    }

    public void setMontoMaximo(String montoMaximo){
        this.montoMaximo = montoMaximo;
    }
    public Integer getNumOperacionMensuales(){
        return this.numOperacionMensuales;
    }

    public void setNumOperacionMensuales(Integer numOperacionMensuales){
        this.numOperacionMensuales = numOperacionMensuales;
    }
    public Integer getNumOperacionEfectivo(){
        return this.numOperacionEfectivo;
    }

    public void setNumOperacionEfectivo(Integer numOperacionEfectivo){
        this.numOperacionEfectivo = numOperacionEfectivo;
    }
    public String getDiasVenta(){
        return this.diasVenta;
    }

    public void setDiasVenta(String diasVenta){
        this.diasVenta = diasVenta;
    }
    public String getFotoFachada(){
        return this.fotoFachada;
    }

    public void setFotoFachada(String fotoFachada){
        this.fotoFachada = fotoFachada;
    }
    public String getRefDomiciliaria(){
        return this.refDomiciliaria;
    }

    public void setRefDomiciliaria(String refDomiciliaria){
        this.refDomiciliaria = refDomiciliaria;
    }
    public Integer getEstatusCompletado(){
        return this.estatusCompletado;
    }

    public void setEstatusCompletado(Integer estatusCompletado){
        this.estatusCompletado = estatusCompletado;
    }
    public String getComentarioRechazo(){
        return this.comentarioRechazo;
    }

    public void setComentarioRechazo(String comentarioRechazo){
        this.comentarioRechazo = comentarioRechazo;
    }
    public String getUbicadoEnDomCli(){
        return this.ubicadoEnDomCli;
    }

    public void setUbicadoEnDomCli(String ubicadoEnDomCli){
        this.ubicadoEnDomCli = ubicadoEnDomCli;
    }

}