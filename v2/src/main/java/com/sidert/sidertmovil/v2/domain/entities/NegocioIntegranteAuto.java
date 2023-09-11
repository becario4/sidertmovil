package com.sidert.sidertmovil.v2.domain.entities;

import java.io.Serializable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tbl_negocio_integrante_auto")
public final class NegocioIntegranteAuto implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_negocio")
    private Long idNegocio;
    @ColumnInfo(name = "id_integrante")
    private Integer idIntegrante;
    @ColumnInfo(name = "nombre")
    private String nombre;
    @ColumnInfo(name = "latitud")
    private String latitud;
    @ColumnInfo(name = "longitud")
    private String longitud;
    @ColumnInfo(name = "calle")
    private String calle;
    @ColumnInfo(name = "no_exterior")
    private String noExterior;
    @ColumnInfo(name = "no_interior")
    private String noInterior;
    @ColumnInfo(name = "manzana")
    private String manzana;
    @ColumnInfo(name = "lote")
    private String lote;
    @ColumnInfo(name = "cp")
    private String cp;
    @ColumnInfo(name = "colonia")
    private String colonia;
    @ColumnInfo(name = "ciudad")
    private String ciudad;
    @ColumnInfo(name = "localidad")
    private String localidad;
    @ColumnInfo(name = "municipio")
    private String municipio;
    @ColumnInfo(name = "estado")
    private String estado;
    @ColumnInfo(name = "destino_credito")
    private String destinoCredito;
    @ColumnInfo(name = "otro_destino_credito")
    private String otroDestinoCredito;
    @ColumnInfo(name = "ocupacion")
    private String ocupacion;
    @ColumnInfo(name = "actividad_economica")
    private String actividadEconomica;
    @ColumnInfo(name = "antiguedad")
    private String antiguedad;
    @ColumnInfo(name = "ing_mensual")
    private String ingMensual;
    @ColumnInfo(name = "ing_otros")
    private String ingOtros;
    @ColumnInfo(name = "gasto_semanal")
    private String gastoSemanal;
    @ColumnInfo(name = "capacidad_pago")
    private String capacidadPago;
    @ColumnInfo(name = "monto_maximo")
    private String montoMaximo;
    @ColumnInfo(name = "medios_pago")
    private String mediosPago;
    @ColumnInfo(name = "otro_medio_pago")
    private String otroMedioPago;
    @ColumnInfo(name = "num_ope_mensuales")
    private Integer numOpeMensuales;
    @ColumnInfo(name = "num_ope_mensuales_efectivo")
    private Integer numOpeMensualesEfectivo;
    @ColumnInfo(name = "foto_fachada")
    private String fotoFachada;
    @ColumnInfo(name = "ref_domiciliaria")
    private String refDomiciliaria;
    @ColumnInfo(name = "estatus_rechazo")
    private Integer estatusRechazo;
    @ColumnInfo(name = "comentario_rechazo")
    private String comentarioRechazo;
    @ColumnInfo(name = "estatus_completado")
    private Integer estatusCompletado;
    @ColumnInfo(name = "located_at")
    private String locatedAt;
    @ColumnInfo(name = "tiene_negocio",defaultValue="'SI'")
    private String tieneNegocio;
    @ColumnInfo(name = "ubicado_en_dom_cli",defaultValue="'NO'")
    private String ubicadoEnDomCli;


// Getters & Setters

    public Long getIdNegocio(){
        return this.idNegocio;
    }

    public void setIdNegocio(Long idNegocio){
        this.idNegocio = idNegocio;
    }
    public Integer getIdIntegrante(){
        return this.idIntegrante;
    }

    public void setIdIntegrante(Integer idIntegrante){
        this.idIntegrante = idIntegrante;
    }
    public String getNombre(){
        return this.nombre;
    }

    public void setNombre(String nombre){
        this.nombre = nombre;
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
    public String getCalle(){
        return this.calle;
    }

    public void setCalle(String calle){
        this.calle = calle;
    }
    public String getNoExterior(){
        return this.noExterior;
    }

    public void setNoExterior(String noExterior){
        this.noExterior = noExterior;
    }
    public String getNoInterior(){
        return this.noInterior;
    }

    public void setNoInterior(String noInterior){
        this.noInterior = noInterior;
    }
    public String getManzana(){
        return this.manzana;
    }

    public void setManzana(String manzana){
        this.manzana = manzana;
    }
    public String getLote(){
        return this.lote;
    }

    public void setLote(String lote){
        this.lote = lote;
    }
    public String getCp(){
        return this.cp;
    }

    public void setCp(String cp){
        this.cp = cp;
    }
    public String getColonia(){
        return this.colonia;
    }

    public void setColonia(String colonia){
        this.colonia = colonia;
    }
    public String getCiudad(){
        return this.ciudad;
    }

    public void setCiudad(String ciudad){
        this.ciudad = ciudad;
    }
    public String getLocalidad(){
        return this.localidad;
    }

    public void setLocalidad(String localidad){
        this.localidad = localidad;
    }
    public String getMunicipio(){
        return this.municipio;
    }

    public void setMunicipio(String municipio){
        this.municipio = municipio;
    }
    public String getEstado(){
        return this.estado;
    }

    public void setEstado(String estado){
        this.estado = estado;
    }
    public String getDestinoCredito(){
        return this.destinoCredito;
    }

    public void setDestinoCredito(String destinoCredito){
        this.destinoCredito = destinoCredito;
    }
    public String getOtroDestinoCredito(){
        return this.otroDestinoCredito;
    }

    public void setOtroDestinoCredito(String otroDestinoCredito){
        this.otroDestinoCredito = otroDestinoCredito;
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
    public String getAntiguedad(){
        return this.antiguedad;
    }

    public void setAntiguedad(String antiguedad){
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
    public String getCapacidadPago(){
        return this.capacidadPago;
    }

    public void setCapacidadPago(String capacidadPago){
        this.capacidadPago = capacidadPago;
    }
    public String getMontoMaximo(){
        return this.montoMaximo;
    }

    public void setMontoMaximo(String montoMaximo){
        this.montoMaximo = montoMaximo;
    }
    public String getMediosPago(){
        return this.mediosPago;
    }

    public void setMediosPago(String mediosPago){
        this.mediosPago = mediosPago;
    }
    public String getOtroMedioPago(){
        return this.otroMedioPago;
    }

    public void setOtroMedioPago(String otroMedioPago){
        this.otroMedioPago = otroMedioPago;
    }
    public Integer getNumOpeMensuales(){
        return this.numOpeMensuales;
    }

    public void setNumOpeMensuales(Integer numOpeMensuales){
        this.numOpeMensuales = numOpeMensuales;
    }
    public Integer getNumOpeMensualesEfectivo(){
        return this.numOpeMensualesEfectivo;
    }

    public void setNumOpeMensualesEfectivo(Integer numOpeMensualesEfectivo){
        this.numOpeMensualesEfectivo = numOpeMensualesEfectivo;
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
    public Integer getEstatusRechazo(){
        return this.estatusRechazo;
    }

    public void setEstatusRechazo(Integer estatusRechazo){
        this.estatusRechazo = estatusRechazo;
    }
    public String getComentarioRechazo(){
        return this.comentarioRechazo;
    }

    public void setComentarioRechazo(String comentarioRechazo){
        this.comentarioRechazo = comentarioRechazo;
    }
    public Integer getEstatusCompletado(){
        return this.estatusCompletado;
    }

    public void setEstatusCompletado(Integer estatusCompletado){
        this.estatusCompletado = estatusCompletado;
    }
    public String getLocatedAt(){
        return this.locatedAt;
    }

    public void setLocatedAt(String locatedAt){
        this.locatedAt = locatedAt;
    }
    public String getTieneNegocio(){
        return this.tieneNegocio;
    }

    public void setTieneNegocio(String tieneNegocio){
        this.tieneNegocio = tieneNegocio;
    }
    public String getUbicadoEnDomCli(){
        return this.ubicadoEnDomCli;
    }

    public void setUbicadoEnDomCli(String ubicadoEnDomCli){
        this.ubicadoEnDomCli = ubicadoEnDomCli;
    }

}