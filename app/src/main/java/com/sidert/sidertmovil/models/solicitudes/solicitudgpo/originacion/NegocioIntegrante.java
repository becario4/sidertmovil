package com.sidert.sidertmovil.models.solicitudes.solicitudgpo.originacion;

import static com.sidert.sidertmovil.utils.Constants.CIUDAD;
import static com.sidert.sidertmovil.utils.Constants.ID_INTEGRANTE;
import static com.sidert.sidertmovil.utils.Constants.NOMBRE;
import static com.sidert.sidertmovil.utils.Constants.ROOT_PATH;

import android.database.Cursor;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.sidert.sidertmovil.models.BaseModel;
import com.sidert.sidertmovil.models.IFillModel;

import java.io.File;
import java.io.Serializable;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class NegocioIntegrante extends BaseModel implements Serializable, IFillModel {
    public final static String TBL                            = "tbl_negocio_integrante";
    public final static String COL_ID_NEGOCIO                 = "id_negocio";
    public final static String COL_ID_INTEGRANTE              = "id_integrante";
    public final static String COL_NOMBRE                     = "nombre";
    public final static String COL_LATITUD                    = "latitud";
    public final static String COL_LONGITUD                   = "longitud";
    public final static String COL_CALLE                      = "calle";
    public final static String COL_NO_EXTERIOR                = "no_exterior";
    public final static String COL_NO_INTERIOR                = "no_interior";
    public final static String COL_MANZANA                    = "manzana";
    public final static String COL_LOTE                       = "lote";
    public final static String COL_CP                         = "cp";
    public final static String COL_COLONIA                    = "colonia";
    public final static String COL_CIUDAD                     = "ciudad";
    public final static String COL_LOCALIDAD                  = "localidad";
    public final static String COL_MUNICIPIO                  = "municipio";
    public final static String COL_ESTADO                     = "estado";
    public final static String COL_DESTINO_CREDITO            = "destino_credito";
    public final static String COL_OTRO_DESTINO_CREDITO       = "otro_destino_credito";
    public final static String COL_OCUPACION                  = "ocupacion";
    public final static String COL_ACTIVIDAD_ECONOMICA        = "actividad_economica";
    public final static String COL_ANTIGUEDAD                 = "antiguedad";
    public final static String COL_ING_MENSUAL                = "ing_mensual";
    public final static String COL_ING_OTROS                  = "ing_otros";
    public final static String COL_GASTO_SEMANAL              = "gasto_semanal";
    public final static String COL_CAPACIDAD_PAGO             = "capacidad_pago";
    public final static String COL_MONTO_MAXIMO               = "monto_maximo";
    public final static String COL_MEDIOS_PAGO                = "medios_pago";
    public final static String COL_OTRO_MEDIO_PAGO            = "otro_medio_pago";
    public final static String COL_NUM_OPE_MENSUALES          = "num_ope_mensuales";
    public final static String COL_NUM_OPE_MENSUALES_EFECTIVO = "num_ope_mensuales_efectivo";
    public final static String COL_FOTO_FACHADA               = "foto_fachada";
    public final static String COL_REF_DOMICILIARIA           = "ref_domiciliaria";
    public final static String COL_ESTATUS_RECHAZO            = "estatus_rechazo";
    public final static String COL_COMENTARIO_RECHAZO         = "comentario_rechazo";
    public final static String COL_ESTATUS_COMPLETADO         = "estatus_completado";
    public final static String COL_LOCATED_AT                 = "located_at";
    public final static String COL_TIENE_NEGOCIO              = "tiene_negocio";
    public final static String COL_UBICADO_EN_DOM_CLI         = "ubicado_en_dom_cli";

    @SerializedName("id_negocio")
    @Expose
    private Integer idNegocio;

    @SerializedName("id_integrante")
    @Expose
    private Integer idIntegrante;

    @SerializedName("nombre")
    @Expose
    private String nombre;

    @SerializedName("latitud")
    @Expose
    private String latitud;

    @SerializedName("longitud")
    @Expose
    private String longitud;

    @SerializedName("calle")
    @Expose
    private String calle;

    @SerializedName("no_exterior")
    @Expose
    private String noExterior;

    @SerializedName("no_interior")
    @Expose
    private String noInterior;

    @SerializedName("manzana")
    @Expose
    private String manzana;

    @SerializedName("lote")
    @Expose
    private String lote;

    @SerializedName("cp")
    @Expose
    private String cp;

    @SerializedName("colonia")
    @Expose
    private String colonia;

    @SerializedName("ciudad")
    @Expose
    private String ciudad;

    @SerializedName("localidad")
    @Expose
    private String localidad;

    @SerializedName("municipio")
    @Expose
    private String municipio;

    @SerializedName("estado")
    @Expose
    private String estado;

    @SerializedName("destino_credito")
    @Expose
    private String destinoCredito;

    @SerializedName("otro_destino_credito")
    @Expose
    private String otroDestinoCredito;

    @SerializedName("ocupacion")
    @Expose
    private String ocupacion;

    @SerializedName("actividad_economica")
    @Expose
    private String actividadEconomica;

    @SerializedName("antiguedad")
    @Expose
    private String antiguedad;

    @SerializedName("ing_mensual")
    @Expose
    private String ingMensual;

    @SerializedName("ing_otros")
    @Expose
    private String ingOtros;

    @SerializedName("gasto_semanal")
    @Expose
    private String gastoSemanal;

    @SerializedName("capacidad_pago")
    @Expose
    private String capacidadPago;

    @SerializedName("monto_maximo")
    @Expose
    private String montoMaximo;

    @SerializedName("medios_pago")
    @Expose
    private String mediosPago;

    @SerializedName("otro_medio_pago")
    @Expose
    private String otroMedioPago;

    @SerializedName("num_ope_mensuales")
    @Expose
    private Integer numOpeMensuales;

    @SerializedName("num_ope_mensuales_efectivo")
    @Expose
    private Integer numOpeMensualesEfectivo;

    @SerializedName("foto_fachada")
    @Expose
    private String fotoFachada;

    @SerializedName("ref_domiciliaria")
    @Expose
    private String refDomiciliaria;

    @SerializedName("estatus_rechazo")
    @Expose
    private Integer estatusRechazo;

    @SerializedName("comentario_rechazo")
    @Expose
    private String comentarioRechazo;

    @SerializedName("estatus_completado")
    @Expose
    private Integer estatusCompletado;

    @SerializedName("located_at")
    @Expose
    private String locatedAt;

    @SerializedName("tiene_negocio")
    @Expose
    private String tieneNegocio;

    @SerializedName("ubicado_en_dom_cli")
    @Expose
    private String ubicadoEnDomCli;

    public Integer getIdNegocio() {
        return idNegocio;
    }

    public void setIdNegocio(Integer idNegocio) {
        this.idNegocio = idNegocio;
    }

    public Integer getIdIntegrante() {
        return idIntegrante;
    }

    public void setIdIntegrante(Integer idIntegrante) {
        this.idIntegrante = idIntegrante;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getNoExterior() {
        return noExterior;
    }

    public void setNoExterior(String noExterior) {
        this.noExterior = noExterior;
    }

    public String getNoInterior() {
        return noInterior;
    }

    public void setNoInterior(String noInterior) {
        this.noInterior = noInterior;
    }

    public String getManzana() {
        return manzana;
    }

    public void setManzana(String manzana) {
        this.manzana = manzana;
    }

    public String getLote() {
        return lote;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }

    public String getCp() {
        return cp;
    }

    public void setCp(String cp) {
        this.cp = cp;
    }

    public String getColonia() {
        return colonia;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getDestinoCredito() {
        return destinoCredito;
    }

    public void setDestinoCredito(String destinoCredito) {
        this.destinoCredito = destinoCredito;
    }

    public String getOtroDestinoCredito() {
        return otroDestinoCredito;
    }

    public void setOtroDestinoCredito(String otroDestinoCredito) {
        this.otroDestinoCredito = otroDestinoCredito;
    }

    public String getOcupacion() {
        return ocupacion;
    }

    public void setOcupacion(String ocupacion) {
        this.ocupacion = ocupacion;
    }

    public String getActividadEconomica() {
        return actividadEconomica;
    }

    public void setActividadEconomica(String actividadEconomica) {
        this.actividadEconomica = actividadEconomica;
    }

    public String getAntiguedad() {
        return antiguedad;
    }

    public void setAntiguedad(String antiguedad) {
        this.antiguedad = antiguedad;
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

    public String getCapacidadPago() {
        return capacidadPago;
    }

    public void setCapacidadPago(String capacidadPago) {
        this.capacidadPago = capacidadPago;
    }

    public String getMontoMaximo() {
        return montoMaximo;
    }

    public void setMontoMaximo(String montoMaximo) {
        this.montoMaximo = montoMaximo;
    }

    public String getMediosPago() {
        return mediosPago;
    }

    public void setMediosPago(String mediosPago) {
        this.mediosPago = mediosPago;
    }

    public String getOtroMedioPago() {
        return otroMedioPago;
    }

    public void setOtroMedioPago(String otroMedioPago) {
        this.otroMedioPago = otroMedioPago;
    }

    public Integer getNumOpeMensuales() {
        return numOpeMensuales;
    }

    public void setNumOpeMensuales(Integer numOpeMensuales) {
        this.numOpeMensuales = numOpeMensuales;
    }

    public Integer getNumOpeMensualesEfectivo() {
        return numOpeMensualesEfectivo;
    }

    public void setNumOpeMensualesEfectivo(Integer numOpeMensualesEfectivo) {
        this.numOpeMensualesEfectivo = numOpeMensualesEfectivo;
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

    public String getLocatedAt() {
        return locatedAt;
    }

    public void setLocatedAt(String locatedAt) {
        this.locatedAt = locatedAt;
    }

    public String getTieneNegocio() {
        return tieneNegocio;
    }

    public void setTieneNegocio(String tieneNegocio) {
        this.tieneNegocio = tieneNegocio;
    }

    public String getUbicadoEnDomCli() {
        return ubicadoEnDomCli;
    }

    public void setUbicadoEnDomCli(String ubicadoEnDomCli) {
        this.ubicadoEnDomCli = ubicadoEnDomCli;
    }

    public MultipartBody.Part getFachadaMBPart() {
        MultipartBody.Part fachada_negocio = null;
        File image_fachada_negocio = null;

        if(fotoFachada != null && !fotoFachada.equals("")) image_fachada_negocio = new File(ROOT_PATH + "Fachada/" + fotoFachada);

        if (image_fachada_negocio != null) {
            RequestBody imageBodyFachadaNeg = RequestBody.create(MediaType.parse("image/*"), image_fachada_negocio);
            fachada_negocio = MultipartBody.Part.createFormData("fachada_negocio", image_fachada_negocio.getName(), imageBodyFachadaNeg);
        }

        return fachada_negocio;
    }

    @Override
    public void fill(Cursor row) {
        this.row = row;

        idNegocio               = getInt(COL_ID_NEGOCIO);
        idIntegrante            = getInt(ID_INTEGRANTE);
        nombre                  = getString(COL_NOMBRE);
        latitud                 = getString(COL_LATITUD);
        longitud                = getString(COL_LONGITUD);
        calle                   = getString(COL_CALLE);
        noExterior              = getString(COL_NO_EXTERIOR);
        noInterior              = getString(COL_NO_INTERIOR);
        manzana                 = getString(COL_MANZANA);
        lote                    = getString(COL_LOTE);
        cp                      = getString(COL_CP);
        colonia                 = getString(COL_COLONIA);
        ciudad                  = getString(COL_CIUDAD);
        localidad               = getString(COL_LOCALIDAD);
        municipio               = getString(COL_MUNICIPIO);
        estado                  = getString(COL_ESTADO);
        destinoCredito          = getString(COL_DESTINO_CREDITO);
        otroDestinoCredito      = getString(COL_OTRO_DESTINO_CREDITO);
        ocupacion               = getString(COL_OCUPACION);
        actividadEconomica      = getString(COL_ACTIVIDAD_ECONOMICA);
        antiguedad              = getString(COL_ANTIGUEDAD);
        ingMensual              = getString(COL_ING_MENSUAL);
        ingOtros                = getString(COL_ING_OTROS);
        gastoSemanal            = getString(COL_GASTO_SEMANAL);
        capacidadPago           = getString(COL_CAPACIDAD_PAGO);
        montoMaximo             = getString(COL_MONTO_MAXIMO);
        mediosPago              = getString(COL_MEDIOS_PAGO);
        otroMedioPago           = getString(COL_OTRO_MEDIO_PAGO);
        numOpeMensuales         = getInt(COL_NUM_OPE_MENSUALES);
        numOpeMensualesEfectivo = getInt(COL_NUM_OPE_MENSUALES_EFECTIVO);
        fotoFachada             = getString(COL_FOTO_FACHADA);
        refDomiciliaria         = getString(COL_REF_DOMICILIARIA);
        estatusRechazo          = getInt(COL_ESTATUS_RECHAZO);
        comentarioRechazo       = getString(COL_COMENTARIO_RECHAZO);
        estatusCompletado       = getInt(COL_ESTATUS_COMPLETADO);
        locatedAt               = getString(COL_LOCATED_AT);
        tieneNegocio            = getString(COL_TIENE_NEGOCIO);
        ubicadoEnDomCli         = getString(COL_UBICADO_EN_DOM_CLI);
    }


}
