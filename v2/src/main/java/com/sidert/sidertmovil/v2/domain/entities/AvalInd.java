package com.sidert.sidertmovil.v2.domain.entities;

import java.io.Serializable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tbl_aval_ind")
public final class AvalInd implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_aval")
    private Long idAval;
    @ColumnInfo(name = "id_solicitud")
    private Integer idSolicitud;
    @ColumnInfo(name = "nombre")
    private String nombre;
    @ColumnInfo(name = "paterno")
    private String paterno;
    @ColumnInfo(name = "materno")
    private String materno;
    @ColumnInfo(name = "fecha_nacimiento")
    private String fechaNacimiento;
    @ColumnInfo(name = "edad")
    private String edad;
    @ColumnInfo(name = "genero")
    private Integer genero;
    @ColumnInfo(name = "estado_nacimiento")
    private String estadoNacimiento;
    @ColumnInfo(name = "rfc")
    private String rfc;
    @ColumnInfo(name = "curp")
    private String curp;
    @ColumnInfo(name = "curp_digito")
    private String curpDigito;
    @ColumnInfo(name = "parentesco_cliente")
    private String parentescoCliente;
    @ColumnInfo(name = "tipo_identificacion")
    private String tipoIdentificacion;
    @ColumnInfo(name = "no_identificacion")
    private String noIdentificacion;
    @ColumnInfo(name = "ocupacion")
    private String ocupacion;
    @ColumnInfo(name = "actividad_economica")
    private String actividadEconomica;
    @ColumnInfo(name = "destino_credito")
    private String destinoCredito;
    @ColumnInfo(name = "otro_destino")
    private String otroDestino;
    @ColumnInfo(name = "direccion_id")
    private String direccionId;
    @ColumnInfo(name = "tipo_vivienda")
    private String tipoVivienda;
    @ColumnInfo(name = "nombre_titular")
    private String nombreTitular;
    @ColumnInfo(name = "parentesco")
    private String parentesco;
    @ColumnInfo(name = "caracteristicas_domicilio")
    private String caracteristicasDomicilio;
    @ColumnInfo(name = "antigueda")
    private Integer antigueda;
    @ColumnInfo(name = "tiene_negocio")
    private Integer tieneNegocio;
    @ColumnInfo(name = "nombre_negocio")
    private String nombreNegocio;
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
    @ColumnInfo(name = "capacidad_pagos")
    private String capacidadPagos;
    @ColumnInfo(name = "medio_pago")
    private String medioPago;
    @ColumnInfo(name = "otro_medio_pago")
    private String otroMedioPago;
    @ColumnInfo(name = "monto_maximo")
    private String montoMaximo;
    @ColumnInfo(name = "horario_localizacion")
    private String horarioLocalizacion;
    @ColumnInfo(name = "activos_observables")
    private String activosObservables;
    @ColumnInfo(name = "tel_casa")
    private String telCasa;
    @ColumnInfo(name = "tel_celular")
    private String telCelular;
    @ColumnInfo(name = "tel_mensajes")
    private String telMensajes;
    @ColumnInfo(name = "tel_trabajo")
    private String telTrabajo;
    @ColumnInfo(name = "email")
    private String email;
    @ColumnInfo(name = "foto_fachada")
    private String fotoFachada;
    @ColumnInfo(name = "ref_domiciliaria")
    private String refDomiciliaria;
    @ColumnInfo(name = "firma")
    private String firma;
    @ColumnInfo(name = "estatus_rechazo")
    private Integer estatusRechazo;
    @ColumnInfo(name = "comentario_rechazo")
    private String comentarioRechazo;
    @ColumnInfo(name = "estatus_completado")
    private Integer estatusCompletado;
    @ColumnInfo(name = "latitud")
    private String latitud;
    @ColumnInfo(name = "longitud")
    private String longitud;
    @ColumnInfo(name = "located_at")
    private String locatedAt;


// Getters & Setters

    public Long getIdAval(){
        return this.idAval;
    }

    public void setIdAval(Long idAval){
        this.idAval = idAval;
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
    public String getPaterno(){
        return this.paterno;
    }

    public void setPaterno(String paterno){
        this.paterno = paterno;
    }
    public String getMaterno(){
        return this.materno;
    }

    public void setMaterno(String materno){
        this.materno = materno;
    }
    public String getFechaNacimiento(){
        return this.fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento){
        this.fechaNacimiento = fechaNacimiento;
    }
    public String getEdad(){
        return this.edad;
    }

    public void setEdad(String edad){
        this.edad = edad;
    }
    public Integer getGenero(){
        return this.genero;
    }

    public void setGenero(Integer genero){
        this.genero = genero;
    }
    public String getEstadoNacimiento(){
        return this.estadoNacimiento;
    }

    public void setEstadoNacimiento(String estadoNacimiento){
        this.estadoNacimiento = estadoNacimiento;
    }
    public String getRfc(){
        return this.rfc;
    }

    public void setRfc(String rfc){
        this.rfc = rfc;
    }
    public String getCurp(){
        return this.curp;
    }

    public void setCurp(String curp){
        this.curp = curp;
    }
    public String getCurpDigito(){
        return this.curpDigito;
    }

    public void setCurpDigito(String curpDigito){
        this.curpDigito = curpDigito;
    }
    public String getParentescoCliente(){
        return this.parentescoCliente;
    }

    public void setParentescoCliente(String parentescoCliente){
        this.parentescoCliente = parentescoCliente;
    }
    public String getTipoIdentificacion(){
        return this.tipoIdentificacion;
    }

    public void setTipoIdentificacion(String tipoIdentificacion){
        this.tipoIdentificacion = tipoIdentificacion;
    }
    public String getNoIdentificacion(){
        return this.noIdentificacion;
    }

    public void setNoIdentificacion(String noIdentificacion){
        this.noIdentificacion = noIdentificacion;
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
    public String getDireccionId(){
        return this.direccionId;
    }

    public void setDireccionId(String direccionId){
        this.direccionId = direccionId;
    }
    public String getTipoVivienda(){
        return this.tipoVivienda;
    }

    public void setTipoVivienda(String tipoVivienda){
        this.tipoVivienda = tipoVivienda;
    }
    public String getNombreTitular(){
        return this.nombreTitular;
    }

    public void setNombreTitular(String nombreTitular){
        this.nombreTitular = nombreTitular;
    }
    public String getParentesco(){
        return this.parentesco;
    }

    public void setParentesco(String parentesco){
        this.parentesco = parentesco;
    }
    public String getCaracteristicasDomicilio(){
        return this.caracteristicasDomicilio;
    }

    public void setCaracteristicasDomicilio(String caracteristicasDomicilio){
        this.caracteristicasDomicilio = caracteristicasDomicilio;
    }
    public Integer getAntigueda(){
        return this.antigueda;
    }

    public void setAntigueda(Integer antigueda){
        this.antigueda = antigueda;
    }
    public Integer getTieneNegocio(){
        return this.tieneNegocio;
    }

    public void setTieneNegocio(Integer tieneNegocio){
        this.tieneNegocio = tieneNegocio;
    }
    public String getNombreNegocio(){
        return this.nombreNegocio;
    }

    public void setNombreNegocio(String nombreNegocio){
        this.nombreNegocio = nombreNegocio;
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
    public String getCapacidadPagos(){
        return this.capacidadPagos;
    }

    public void setCapacidadPagos(String capacidadPagos){
        this.capacidadPagos = capacidadPagos;
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
    public String getHorarioLocalizacion(){
        return this.horarioLocalizacion;
    }

    public void setHorarioLocalizacion(String horarioLocalizacion){
        this.horarioLocalizacion = horarioLocalizacion;
    }
    public String getActivosObservables(){
        return this.activosObservables;
    }

    public void setActivosObservables(String activosObservables){
        this.activosObservables = activosObservables;
    }
    public String getTelCasa(){
        return this.telCasa;
    }

    public void setTelCasa(String telCasa){
        this.telCasa = telCasa;
    }
    public String getTelCelular(){
        return this.telCelular;
    }

    public void setTelCelular(String telCelular){
        this.telCelular = telCelular;
    }
    public String getTelMensajes(){
        return this.telMensajes;
    }

    public void setTelMensajes(String telMensajes){
        this.telMensajes = telMensajes;
    }
    public String getTelTrabajo(){
        return this.telTrabajo;
    }

    public void setTelTrabajo(String telTrabajo){
        this.telTrabajo = telTrabajo;
    }
    public String getEmail(){
        return this.email;
    }

    public void setEmail(String email){
        this.email = email;
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
    public String getFirma(){
        return this.firma;
    }

    public void setFirma(String firma){
        this.firma = firma;
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
    public String getLocatedAt(){
        return this.locatedAt;
    }

    public void setLocatedAt(String locatedAt){
        this.locatedAt = locatedAt;
    }

}