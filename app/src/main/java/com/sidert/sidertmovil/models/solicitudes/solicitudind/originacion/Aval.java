package com.sidert.sidertmovil.models.solicitudes.solicitudind.originacion;

import android.database.Cursor;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.sidert.sidertmovil.models.IFillModel;

import java.io.File;
import java.io.Serializable;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static com.sidert.sidertmovil.utils.Constants.ROOT_PATH;

public class Aval implements Serializable, IFillModel {
    public static final String TBL                              = "tbl_aval_ind";
    protected static final String COL_ID_AVAL                   = "id_aval";
    protected static final String COL_ID_SOLICITUD              = "id_solicitud";
    protected static final String COL_NOMBRE                    = "nombre";
    protected static final String COL_PATERNO                   = "paterno";
    protected static final String COL_MATERNO                   = "materno";
    protected static final String COL_FECHA_NACIMIENTO          = "fecha_nacimiento";
    protected static final String COL_EDAD                      = "edad";
    protected static final String COL_GENERO                    = "genero";
    protected static final String COL_ESTADO_NACIMIENTO         = "estado_nacimiento";
    protected static final String COL_RFC                       = "rfc";
    protected static final String COL_CURP                      = "curp";
    protected static final String COL_CURP_DIGITO               = "curp_digito";
    protected static final String COL_PARENTESCO_CLIENTE        = "parentesco_cliente";
    protected static final String COL_TIPO_IDENTIFICACION       = "tipo_identificacion";
    protected static final String COL_NO_IDENTIFICACION         = "no_identificacion";
    protected static final String COL_OCUPACION                 = "ocupacion";
    protected static final String COL_ACTIVIDAD_ECONOMICA       = "actividad_economica";
    protected static final String COL_DESTINO_CREDITO           = "destino_credito";
    protected static final String COL_OTRO_DESTINO              = "otro_destino";
    protected static final String COL_DIRECCION_ID              = "direccion_id";
    protected static final String COL_TIPO_VIVIENDA             = "tipo_vivienda";
    protected static final String COL_NOMBRE_TITULAR            = "nombre_titular";
    protected static final String COL_PARENTESCO                = "parentesco";
    protected static final String COL_CARACTERISTICAS_DOMICILIO = "caracteristicas_domicilio";
    protected static final String COL_ANTIGUEDAD                = "antigueda";
    protected static final String COL_TIENE_NEGOCIO             = "tiene_negocio";
    protected static final String COL_NOMBRE_NEGOCIO            = "nombre_negocio";
    protected static final String COL_ING_MENSUAL               = "ing_mensual";
    protected static final String COL_ING_OTROS                 = "ing_otros";
    protected static final String COL_GASTO_SEMANAL             = "gasto_semanal";
    protected static final String COL_GASTO_AGUA                = "gasto_agua";
    protected static final String COL_GASTO_LUZ                 = "gasto_luz";
    protected static final String COL_GASTO_TELEFONO            = "gasto_telefono";
    protected static final String COL_GASTO_RENTA               = "gasto_renta";
    protected static final String COL_GASTO_OTROS               = "gasto_otros";
    protected static final String COL_CAPACIDAD_PAGOS           = "capacidad_pagos";
    protected static final String COL_MEDIO_PAGO                = "medio_pago";
    protected static final String COL_OTRO_MEDIO_PAGO           = "otro_medio_pago";
    protected static final String COL_MONTO_MAXIMO              = "monto_maximo";
    protected static final String COL_HORARIO_LOCALIZACION      = "horario_localizacion";
    protected static final String COL_ACTIVOS_OBSERVABLES       = "activos_observables";
    protected static final String COL_TEL_CASA                  = "tel_casa";
    protected static final String COL_TEL_CELULAR               = "tel_celular";
    protected static final String COL_TEL_MENSAJES              = "tel_mensajes";
    protected static final String COL_TEL_TRABAJO               = "tel_trabajo";
    protected static final String COL_EMAIL                     = "email";
    protected static final String COL_FOTO_FACHADA              = "foto_fachada";
    protected static final String COL_REF_DOMICILIARIA          = "ref_domiciliaria";
    protected static final String COL_FIRMA                     = "firma";
    protected static final String COL_ESTATUS_RECHAZO           = "estatus_rechazo";
    protected static final String COL_COMENTARIO_RECHAZO        = "comentario_rechazo";
    protected static final String COL_ESTATUS_COMPLETADO        = "estatus_completado";
    protected static final String COL_LATITUD                   = "latitud";
    protected static final String COL_LONGITUD                  = "longitud";
    protected static final String COL_LOCATED_AT                = "located_at";

    @SerializedName(COL_ID_AVAL)
    @Expose
    private Integer idAval;

    @SerializedName(COL_ID_SOLICITUD)
    @Expose
    private Integer idSolicitud;

    @SerializedName(COL_NOMBRE)
    @Expose
    private String nombre;

    @SerializedName(COL_PATERNO)
    @Expose
    private String paterno;

    @SerializedName(COL_MATERNO)
    @Expose
    private String materno;

    @SerializedName(COL_FECHA_NACIMIENTO)
    @Expose
    private String fechaNacimiento;

    @SerializedName(COL_EDAD)
    @Expose
    private String edad;

    @SerializedName(COL_GENERO)
    @Expose
    private Integer genero;

    @SerializedName(COL_ESTADO_NACIMIENTO)
    @Expose
    private String estadoNacimiento;

    @SerializedName(COL_RFC)
    @Expose
    private String rfc;

    @SerializedName(COL_CURP)
    @Expose
    private String curp;

    @SerializedName(COL_CURP_DIGITO)
    @Expose
    private String curpDigito;

    @SerializedName(COL_PARENTESCO_CLIENTE)
    @Expose
    private String parentescoCliente;

    @SerializedName(COL_TIPO_IDENTIFICACION)
    @Expose
    private String tipoIdentificacion;

    @SerializedName(COL_NO_IDENTIFICACION)
    @Expose
    private String noIdentificacion;

    @SerializedName(COL_OCUPACION)
    @Expose
    private String ocupacion;

    @SerializedName(COL_ACTIVIDAD_ECONOMICA)
    @Expose
    private String actividadEconomica;

    @SerializedName(COL_DESTINO_CREDITO)
    @Expose
    private String destinoCredito;

    @SerializedName(COL_OTRO_DESTINO)
    @Expose
    private String otroDestino;

    @SerializedName(COL_DIRECCION_ID)
    @Expose
    private String direccionId;

    @SerializedName(COL_TIPO_VIVIENDA)
    @Expose
    private String tipoVivienda;

    @SerializedName(COL_NOMBRE_TITULAR)
    @Expose
    private String nombreTitular;

    @SerializedName(COL_PARENTESCO)
    @Expose
    private String parentesco;

    @SerializedName(COL_CARACTERISTICAS_DOMICILIO)
    @Expose
    private String caracteristicasDomicilio;

    @SerializedName(COL_ANTIGUEDAD)
    @Expose
    private Integer antigueda;

    @SerializedName(COL_TIENE_NEGOCIO)
    @Expose
    private Integer tieneNegocio;

    @SerializedName(COL_NOMBRE_NEGOCIO)
    @Expose
    private String nombreNegocio;

    @SerializedName(COL_ING_MENSUAL)
    @Expose
    private String ingMensual;

    @SerializedName(COL_ING_OTROS)
    @Expose
    private String ingOtros;

    @SerializedName(COL_GASTO_SEMANAL)
    @Expose
    private String gastoSemanal;

    @SerializedName(COL_GASTO_AGUA)
    @Expose
    private String gastoAgua;

    @SerializedName(COL_GASTO_LUZ)
    @Expose
    private String gastoLuz;

    @SerializedName(COL_GASTO_TELEFONO)
    @Expose
    private String gastoTelefono;

    @SerializedName(COL_GASTO_RENTA)
    @Expose
    private String gastoRenta;

    @SerializedName(COL_GASTO_OTROS)
    @Expose
    private String gastoOtros;

    @SerializedName(COL_CAPACIDAD_PAGOS)
    @Expose
    private String capacidadPagos;

    @SerializedName(COL_MEDIO_PAGO)
    @Expose
    private String medioPago;

    @SerializedName(COL_OTRO_MEDIO_PAGO)
    @Expose
    private String otroMedioPago;

    @SerializedName(COL_MONTO_MAXIMO)
    @Expose
    private String montoMaximo;

    @SerializedName(COL_HORARIO_LOCALIZACION)
    @Expose
    private String horarioLocalizacion;

    @SerializedName(COL_ACTIVOS_OBSERVABLES)
    @Expose
    private String activosObservables;

    @SerializedName(COL_TEL_CASA)
    @Expose
    private String telCasa;

    @SerializedName(COL_TEL_CELULAR)
    @Expose
    private String telCelular;

    @SerializedName(COL_TEL_MENSAJES)
    @Expose
    private String telMensajes;

    @SerializedName(COL_TEL_TRABAJO)
    @Expose
    private String telTrabajo;

    @SerializedName(COL_EMAIL)
    @Expose
    private String email;

    @SerializedName(COL_FOTO_FACHADA)
    @Expose
    private String fotoFachada;

    @SerializedName(COL_REF_DOMICILIARIA)
    @Expose
    private String refDomiciliaria;

    @SerializedName(COL_FIRMA)
    @Expose
    private String firma;

    @SerializedName(COL_ESTATUS_RECHAZO)
    @Expose
    private Integer estatusRechazo;

    @SerializedName(COL_COMENTARIO_RECHAZO)
    @Expose
    private String comentarioRechazo;

    @SerializedName(COL_ESTATUS_COMPLETADO)
    @Expose
    private Integer estatusCompletado;

    @SerializedName(COL_LATITUD)
    @Expose
    private String latitud;

    @SerializedName(COL_LONGITUD)
    @Expose
    private String longitud;

    @SerializedName(COL_LOCATED_AT)
    @Expose
    private String locatedAt;

    public String getActividadEconomica() {
        return actividadEconomica;
    }

    public void setActividadEconomica(String actividadEconomica) {
        this.actividadEconomica = actividadEconomica;
    }

    public String getCaracteristicasDomicilio() {
        return caracteristicasDomicilio;
    }

    public void setCaracteristicasDomicilio(String caracteristicasDomicilio) {
        this.caracteristicasDomicilio = caracteristicasDomicilio;
    }

    public Integer getAntigueda() {
        return antigueda;
    }

    public void setAntigueda(Integer antigueda) {
        this.antigueda = antigueda;
    }

    public String getCapacidadPagos() {
        return capacidadPagos;
    }

    public void setCapacidadPagos(String capacidadPagos) {
        this.capacidadPagos = capacidadPagos;
    }

    public String getActivosObservables() {
        return activosObservables;
    }

    public void setActivosObservables(String activosObservables) {
        this.activosObservables = activosObservables;
    }

    public Integer getIdAval() {
        return idAval;
    }

    public void setIdAval(Integer idAval) {
        this.idAval = idAval;
    }

    public Integer getIdSolicitud() {
        return idSolicitud;
    }

    public void setIdSolicitud(Integer idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPaterno() {
        return paterno;
    }

    public void setPaterno(String paterno) {
        this.paterno = paterno;
    }

    public String getMaterno() {
        return materno;
    }

    public void setMaterno(String materno) {
        this.materno = materno;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }

    public Integer getGenero() {
        return genero;
    }

    public void setGenero(Integer genero) {
        this.genero = genero;
    }

    public String getEstadoNacimiento() {
        return estadoNacimiento;
    }

    public void setEstadoNacimiento(String estadoNacimiento) {
        this.estadoNacimiento = estadoNacimiento;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getCurp() {
        return curp;
    }

    public void setCurp(String curp) {
        this.curp = curp;
    }

    public String getCurpDigito() {
        return curpDigito;
    }

    public void setCurpDigito(String curpDigito) {
        this.curpDigito = curpDigito;
    }

    public String getParentescoCliente() {
        return parentescoCliente;
    }

    public void setParentescoCliente(String parentescoCliente) {
        this.parentescoCliente = parentescoCliente;
    }

    public String getTipoIdentificacion() {
        return tipoIdentificacion;
    }

    public void setTipoIdentificacion(String tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }

    public String getNoIdentificacion() {
        return noIdentificacion;
    }

    public void setNoIdentificacion(String noIdentificacion) {
        this.noIdentificacion = noIdentificacion;
    }

    public String getOcupacion() {
        return ocupacion;
    }

    public void setOcupacion(String ocupacion) {
        this.ocupacion = ocupacion;
    }

    public String getDestinoCredito() {
        return destinoCredito;
    }

    public void setDestinoCredito(String destinoCredito) {
        this.destinoCredito = destinoCredito;
    }

    public String getOtroDestino() {
        return otroDestino;
    }

    public void setOtroDestino(String otroDestino) {
        this.otroDestino = otroDestino;
    }

    public String getDireccionId() {
        return direccionId;
    }

    public void setDireccionId(String direccionId) {
        this.direccionId = direccionId;
    }

    public String getTipoVivienda() {
        return tipoVivienda;
    }

    public void setTipoVivienda(String tipoVivienda) {
        this.tipoVivienda = tipoVivienda;
    }

    public String getNombreTitular() {
        return nombreTitular;
    }

    public void setNombreTitular(String nombreTitular) {
        this.nombreTitular = nombreTitular;
    }

    public String getParentesco() {
        return parentesco;
    }

    public void setParentesco(String parentesco) {
        this.parentesco = parentesco;
    }

    public Integer getTieneNegocio() {
        return tieneNegocio;
    }

    public void setTieneNegocio(Integer tieneNegocio) {
        this.tieneNegocio = tieneNegocio;
    }

    public String getNombreNegocio() {
        return nombreNegocio;
    }

    public void setNombreNegocio(String nombreNegocio) {
        this.nombreNegocio = nombreNegocio;
    }

    public String getIngMensual() {
        return ingMensual;
    }

    public void setIngMensual(String ingMensual) {
        this.ingMensual = ingMensual;
    }

    public String getIngOtros() {
        return ingOtros;
    }

    public void setIngOtros(String ingOtros) {
        this.ingOtros = ingOtros;
    }

    public String getGastoSemanal() {
        return gastoSemanal;
    }

    public void setGastoSemanal(String gastoSemanal) {
        this.gastoSemanal = gastoSemanal;
    }

    public String getGastoAgua() {
        return gastoAgua;
    }

    public void setGastoAgua(String gastoAgua) {
        this.gastoAgua = gastoAgua;
    }

    public String getGastoLuz() {
        return gastoLuz;
    }

    public void setGastoLuz(String gastoLuz) {
        this.gastoLuz = gastoLuz;
    }

    public String getGastoTelefono() {
        return gastoTelefono;
    }

    public void setGastoTelefono(String gastoTelefono) {
        this.gastoTelefono = gastoTelefono;
    }

    public String getGastoRenta() {
        return gastoRenta;
    }

    public void setGastoRenta(String gastoRenta) {
        this.gastoRenta = gastoRenta;
    }

    public String getGastoOtros() {
        return gastoOtros;
    }

    public void setGastoOtros(String gastoOtros) {
        this.gastoOtros = gastoOtros;
    }

    public String getMedioPago() {
        return medioPago;
    }

    public void setMedioPago(String medioPago) {
        this.medioPago = medioPago;
    }

    public String getOtroMedioPago() {
        return otroMedioPago;
    }

    public void setOtroMedioPago(String otroMedioPago) {
        this.otroMedioPago = otroMedioPago;
    }

    public String getMontoMaximo() {
        return montoMaximo;
    }

    public void setMontoMaximo(String montoMaximo) {
        this.montoMaximo = montoMaximo;
    }

    public String getHorarioLocalizacion() {
        return horarioLocalizacion;
    }

    public void setHorarioLocalizacion(String horarioLocalizacion) {
        this.horarioLocalizacion = horarioLocalizacion;
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

    public String getTelMensajes() {
        return telMensajes;
    }

    public void setTelMensajes(String telMensajes) {
        this.telMensajes = telMensajes;
    }

    public String getTelTrabajo() {
        return telTrabajo;
    }

    public void setTelTrabajo(String telTrabajo) {
        this.telTrabajo = telTrabajo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFotoFachada() {
        return fotoFachada;
    }

    public void setFotoFachada(String fotoFachada) {
        this.fotoFachada = fotoFachada;
    }

    public String getRefDomiciliaria() {
        return refDomiciliaria;
    }

    public void setRefDomiciliaria(String refDomiciliaria) {
        this.refDomiciliaria = refDomiciliaria;
    }

    public String getFirma() {
        return firma;
    }

    public void setFirma(String firma) {
        this.firma = firma;
    }

    public Integer getEstatusRechazo() {
        return estatusRechazo;
    }

    public void setEstatusRechazo(Integer estatusRechazo) {
        this.estatusRechazo = estatusRechazo;
    }

    public String getComentarioRechazo() {
        return comentarioRechazo;
    }

    public void setComentarioRechazo(String comentarioRechazo) {
        this.comentarioRechazo = comentarioRechazo;
    }

    public Integer getEstatusCompletado() {
        return estatusCompletado;
    }

    public void setEstatusCompletado(Integer estatusCompletado) {
        this.estatusCompletado = estatusCompletado;
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

    public String getLocatedAt() {
        return locatedAt;
    }

    public void setLocatedAt(String locatedAt) {
        this.locatedAt = locatedAt;
    }

    @Override
    public void fill(Cursor row) {
        idAval                   = row.getInt(row.getColumnIndex(COL_ID_AVAL));
        idSolicitud              = row.getInt(row.getColumnIndex(COL_ID_SOLICITUD));
        nombre                   = row.getString(row.getColumnIndex(COL_NOMBRE));
        paterno                  = row.getString(row.getColumnIndex(COL_PATERNO));
        materno                  = row.getString(row.getColumnIndex(COL_MATERNO));
        fechaNacimiento          = row.getString(row.getColumnIndex(COL_FECHA_NACIMIENTO));
        edad                     = row.getString(row.getColumnIndex(COL_EDAD));
        genero                   = row.getInt(row.getColumnIndex(COL_GENERO));
        estadoNacimiento         = row.getString(row.getColumnIndex(COL_ESTADO_NACIMIENTO));
        rfc                      = row.getString(row.getColumnIndex(COL_RFC));
        curp                     = row.getString(row.getColumnIndex(COL_CURP));
        curpDigito               = row.getString(row.getColumnIndex(COL_CURP_DIGITO));
        parentescoCliente        = row.getString(row.getColumnIndex(COL_PARENTESCO_CLIENTE));
        tipoIdentificacion       = row.getString(row.getColumnIndex(COL_TIPO_IDENTIFICACION));
        noIdentificacion         = row.getString(row.getColumnIndex(COL_NO_IDENTIFICACION));
        ocupacion                = row.getString(row.getColumnIndex(COL_OCUPACION));
        actividadEconomica       = row.getString(row.getColumnIndex(COL_ACTIVIDAD_ECONOMICA));
        destinoCredito           = row.getString(row.getColumnIndex(COL_DESTINO_CREDITO));
        otroDestino              = row.getString(row.getColumnIndex(COL_OTRO_DESTINO));
        direccionId              = row.getString(row.getColumnIndex(COL_DIRECCION_ID));
        tipoVivienda             = row.getString(row.getColumnIndex(COL_TIPO_VIVIENDA));
        nombreTitular            = row.getString(row.getColumnIndex(COL_NOMBRE_TITULAR));
        parentesco               = row.getString(row.getColumnIndex(COL_PARENTESCO));
        caracteristicasDomicilio = row.getString(row.getColumnIndex(COL_CARACTERISTICAS_DOMICILIO));
        antigueda                = row.getInt(row.getColumnIndex(COL_ANTIGUEDAD));
        tieneNegocio             = row.getInt(row.getColumnIndex(COL_TIENE_NEGOCIO));
        nombreNegocio            = row.getString(row.getColumnIndex(COL_NOMBRE_NEGOCIO));
        ingMensual               = row.getString(row.getColumnIndex(COL_ING_MENSUAL));
        ingOtros                 = row.getString(row.getColumnIndex(COL_ING_OTROS));
        gastoSemanal             = row.getString(row.getColumnIndex(COL_GASTO_SEMANAL));
        gastoAgua                = row.getString(row.getColumnIndex(COL_GASTO_AGUA));
        gastoLuz                 = row.getString(row.getColumnIndex(COL_GASTO_LUZ));
        gastoTelefono            = row.getString(row.getColumnIndex(COL_GASTO_TELEFONO));
        gastoRenta               = row.getString(row.getColumnIndex(COL_GASTO_RENTA));
        gastoOtros               = row.getString(row.getColumnIndex(COL_GASTO_OTROS));
        capacidadPagos           = row.getString(row.getColumnIndex(COL_CAPACIDAD_PAGOS));
        medioPago                = row.getString(row.getColumnIndex(COL_MEDIO_PAGO));
        otroMedioPago            = row.getString(row.getColumnIndex(COL_OTRO_MEDIO_PAGO));
        montoMaximo              = row.getString(row.getColumnIndex(COL_MONTO_MAXIMO));
        horarioLocalizacion      = row.getString(row.getColumnIndex(COL_HORARIO_LOCALIZACION));
        activosObservables       = row.getString(row.getColumnIndex(COL_ACTIVOS_OBSERVABLES));
        telCasa                  = row.getString(row.getColumnIndex(COL_TEL_CASA));
        telCelular               = row.getString(row.getColumnIndex(COL_TEL_CELULAR));
        telMensajes              = row.getString(row.getColumnIndex(COL_TEL_MENSAJES));
        telTrabajo               = row.getString(row.getColumnIndex(COL_TEL_TRABAJO));
        email                    = row.getString(row.getColumnIndex(COL_EMAIL));
        fotoFachada              = row.getString(row.getColumnIndex(COL_FOTO_FACHADA));
        refDomiciliaria          = row.getString(row.getColumnIndex(COL_REF_DOMICILIARIA));
        firma                    = row.getString(row.getColumnIndex(COL_FIRMA));
        estatusRechazo           = row.getInt(row.getColumnIndex(COL_ESTATUS_RECHAZO));
        comentarioRechazo        = row.getString(row.getColumnIndex(COL_COMENTARIO_RECHAZO));
        estatusCompletado        = row.getInt(row.getColumnIndex(COL_ESTATUS_COMPLETADO));
        latitud                  = row.getString(row.getColumnIndex(COL_LATITUD));
        longitud                 = row.getString(row.getColumnIndex(COL_LONGITUD));
        locatedAt                = row.getString(row.getColumnIndex(COL_LOCATED_AT));

    }

    public MultipartBody.Part getFachadaMBPart()
    {
        MultipartBody.Part fachada_aval = null;
        File image_fachada_aval = null;

        if(fotoFachada != null && !fotoFachada.equals(""))
            image_fachada_aval = new File(ROOT_PATH + "Fachada/" + fotoFachada);

        if (image_fachada_aval != null) {
            RequestBody imageBodyFachadaAval = RequestBody.create(MediaType.parse("image/*"), image_fachada_aval);
            fachada_aval = MultipartBody.Part.createFormData("fachada_aval", image_fachada_aval.getName(), imageBodyFachadaAval);
        }

        return fachada_aval;
    }

    public MultipartBody.Part getFirmaMBPart()
    {
        MultipartBody.Part firma_aval = null;
        File image_firma_aval = null;

        if(firma != null && !firma.equals(""))
            image_firma_aval = new File(ROOT_PATH + "Firma/" + firma);

        if (image_firma_aval != null) {
            RequestBody imageBodyFirmaAval = RequestBody.create(MediaType.parse("image/*"), image_firma_aval);
            firma_aval = MultipartBody.Part.createFormData("firma_aval", image_firma_aval.getName(), imageBodyFirmaAval);
        }

        return firma_aval;
    }

}
