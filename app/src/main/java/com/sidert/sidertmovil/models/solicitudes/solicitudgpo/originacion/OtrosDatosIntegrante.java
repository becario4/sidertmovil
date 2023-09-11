package com.sidert.sidertmovil.models.solicitudes.solicitudgpo.originacion;

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

public class OtrosDatosIntegrante  extends BaseModel implements Serializable, IFillModel {
    public final static String TBL                         = "tbl_otros_datos_integrante";
    protected static final String COL_ID_OTRO              = "id_otro";
    protected static final String COL_ID_INTEGRANTE        = "id_integrante";
    protected static final String COL_CLASIFICACION_RIESGO = "clasificacion_riesgo";
    protected static final String COL_MEDIO_CONTACTO       = "medio_contacto";
    protected static final String COL_EMAIL                = "email";
    protected static final String COL_ESTADO_CUENTA        = "estado_cuenta";
    protected static final String COL_ESTATUS_INTEGRANTE   = "estatus_integrante";
    protected static final String COL_MONTO_SOLICITADO     = "monto_solicitado";
    protected static final String COL_CASA_REUNION         = "casa_reunion";
    protected static final String COL_FIRMA                = "firma";
    protected static final String COL_ESTATUS_COMPLETADO   = "estatus_completado";
    protected static final String COL_LATITUD              = "latitud";
    protected static final String COL_LONGITUD             = "longitud";
    protected static final String COL_LOCATED_AT           = "located_at";
    protected static final String COL_TIENE_FIRMA          = "tiene_firma";
    protected static final String COL_NOMBRE_QUIEN_FIRMA   = "nombre_quien_firma";
    protected static final String COL_MONTO_REFINANCIAR    = "monto_refinanciar";
    protected static final String COL_ID_CAMPANA           = "id_campana";

    @SerializedName(COL_ID_OTRO)
    @Expose
    private Integer idOtro;

    @SerializedName(COL_ID_INTEGRANTE)
    @Expose
    private Integer idIntegrante;

    @SerializedName(COL_CLASIFICACION_RIESGO)
    @Expose
    private String clasificacionRiesgo;

    @SerializedName(COL_MEDIO_CONTACTO)
    @Expose
    private String medioContacto;

    @SerializedName(COL_EMAIL)
    @Expose
    private String email;

    @SerializedName(COL_ESTADO_CUENTA)
    @Expose
    private String estadoCuenta;

    @SerializedName(COL_ESTATUS_INTEGRANTE)
    @Expose
    private Integer estatusIntegrante;

    @SerializedName(COL_MONTO_SOLICITADO)
    @Expose
    private String montoSolicitado;

    @SerializedName(COL_CASA_REUNION)
    @Expose
    private Integer casaReunion;

    @SerializedName(COL_FIRMA)
    @Expose
    private String firma;

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

    @SerializedName(COL_TIENE_FIRMA)
    @Expose
    private String tieneFirma;

    @SerializedName(COL_NOMBRE_QUIEN_FIRMA)
    @Expose
    private String nombreQuienFirma;

    @SerializedName(COL_MONTO_REFINANCIAR)
    @Expose
    private String montoRefinanciar;

    @SerializedName(COL_ID_CAMPANA)
    @Expose
    private Integer id_campana;



    public Integer getIdOtro() {
        return idOtro;
    }

    public void setIdOtro(Integer idOtro) {
        this.idOtro = idOtro;
    }

    public Integer getIdIntegrante() {
        return idIntegrante;
    }

    public void setIdIntegrante(Integer idIntegrante) {
        this.idIntegrante = idIntegrante;
    }

    public String getClasificacionRiesgo() {
        return clasificacionRiesgo;
    }

    public void setClasificacionRiesgo(String clasificacionRiesgo) {
        this.clasificacionRiesgo = clasificacionRiesgo;
    }

    public String getMedioContacto() {
        return medioContacto;
    }

    public void setMedioContacto(String medioContacto) {
        this.medioContacto = medioContacto;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEstadoCuenta() {
        return estadoCuenta;
    }

    public void setEstadoCuenta(String estadoCuenta) {
        this.estadoCuenta = estadoCuenta;
    }

    public Integer getEstatusIntegrante() {
        return estatusIntegrante;
    }

    public void setEstatusIntegrante(Integer estatusIntegrante) {
        this.estatusIntegrante = estatusIntegrante;
    }

    public String getMontoSolicitado() {
        return montoSolicitado;
    }

    public void setMontoSolicitado(String montoSolicitado) {
        this.montoSolicitado = montoSolicitado;
    }

    public Integer getCasaReunion() {
        return casaReunion;
    }

    public void setCasaReunion(Integer casaReunion) {
        this.casaReunion = casaReunion;
    }

    public String getFirma() {
        return firma;
    }

    public void setFirma(String firma) {
        this.firma = firma;
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

    public String getTieneFirma() {
        return tieneFirma;
    }

    public void setTieneFirma(String tieneFirma) {
        this.tieneFirma = tieneFirma;
    }

    public String getNombreQuienFirma() {
        return nombreQuienFirma;
    }

    public void setNombreQuienFirma(String nombreQuienFirma) {
        this.nombreQuienFirma = nombreQuienFirma;
    }

    public String getMontoRefinanciar() {
        return montoRefinanciar;
    }

    public void setMontoRefinanciar(String montoRefinanciar) {
        this.montoRefinanciar = montoRefinanciar;
    }

    public Integer getId_campana() {
        return id_campana;
    }

    public void setId_campana(Integer id_campana) {
        this.id_campana = id_campana;
    }

    public MultipartBody.Part getFirmaMBPart() {
        MultipartBody.Part firma_cliente = null;
        File image_firma_cliente = null;

        if (firma != null && !firma.equals(""))
            image_firma_cliente = new File(ROOT_PATH + "Firma/" + firma);

        if (image_firma_cliente != null) {
            RequestBody imageBodyFirmaCli = RequestBody.create(MediaType.parse("image/*"), image_firma_cliente);
            firma_cliente = MultipartBody.Part.createFormData("firma_cliente", image_firma_cliente.getName(), imageBodyFirmaCli);
        }

        return firma_cliente;
    }






    @Override
    public void fill(Cursor row) {
        this.row = row;

        idOtro              = getInt(COL_ID_OTRO);
        idIntegrante        = getInt(COL_ID_INTEGRANTE);
        clasificacionRiesgo = getString(COL_CLASIFICACION_RIESGO);
        medioContacto       = getString(COL_MEDIO_CONTACTO);
        email               = getString(COL_EMAIL);
        estadoCuenta        = getString(COL_ESTADO_CUENTA);
        estatusIntegrante   = getInt(COL_ESTATUS_INTEGRANTE);
        montoSolicitado     = getString(COL_MONTO_SOLICITADO);
        casaReunion         = getInt(COL_CASA_REUNION);
        firma               = getString(COL_FIRMA);
        estatusCompletado   = getInt(COL_ESTATUS_COMPLETADO);
        latitud             = getString(COL_LATITUD);
        longitud            = getString(COL_LONGITUD);
        locatedAt           = getString(COL_LOCATED_AT);
        tieneFirma          = getString(COL_TIENE_FIRMA);
        nombreQuienFirma    = getString(COL_NOMBRE_QUIEN_FIRMA);
        montoRefinanciar    = getString(COL_MONTO_REFINANCIAR);
        id_campana          = getInt(COL_ID_CAMPANA);
    }
}

