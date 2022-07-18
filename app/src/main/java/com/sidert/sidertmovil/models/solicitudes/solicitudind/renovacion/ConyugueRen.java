package com.sidert.sidertmovil.models.solicitudes.solicitudind.renovacion;

import android.database.Cursor;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.sidert.sidertmovil.models.BaseModel;
import com.sidert.sidertmovil.models.IFillModel;

import java.io.Serializable;

public class ConyugueRen extends BaseModel implements Serializable, IFillModel {
    public final static String TBL                       = "tbl_conyuge_ind_ren";
    protected static final String COL_ID_CONYUGUE        = "id_conyuge";
    protected static final String COL_ID_SOLICITUD       = "id_solicitud";
    protected static final String COL_NOMBRE             = "nombre";
    protected static final String COL_PATERNO            = "paterno";
    protected static final String COL_MATERNO            = "materno";
    protected static final String COL_NACIONALIDAD       = "nacionalidad";
    protected static final String COL_OCUPACION          = "ocupacion";
    protected static final String COL_DIRECCION_ID       = "direccion_id";
    protected static final String COL_ING_MENSUAL        = "ing_mensual";
    protected static final String COL_GASTO_MENSUAL      = "gasto_mensual";
    protected static final String COL_TEL_CASA           = "tel_casa";
    protected static final String COL_TEL_CELULAR        = "tel_celular";
    protected static final String COL_ESTATUS_COMPLETADO = "estatus_completado";

    @SerializedName(COL_ID_CONYUGUE)
    @Expose
    private Integer idConyugue;

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

    @SerializedName(COL_NACIONALIDAD)
    @Expose
    private String nacionalidad;

    @SerializedName(COL_OCUPACION)
    @Expose
    private String ocupacion;

    @SerializedName(COL_DIRECCION_ID)
    @Expose
    private String direccionId;

    @SerializedName(COL_ING_MENSUAL)
    @Expose
    private String ingMensual;

    @SerializedName(COL_GASTO_MENSUAL)
    @Expose
    private String gastoMensual;

    @SerializedName(COL_TEL_CASA)
    @Expose
    private String telCasa;

    @SerializedName(COL_TEL_CELULAR)
    @Expose
    private String telCelular;

    @SerializedName(COL_ESTATUS_COMPLETADO)
    @Expose
    private Integer estatusCompletado;

    public Integer getIdConyugue() {
        return idConyugue;
    }

    public void setIdConyugue(Integer idConyugue) {
        this.idConyugue = idConyugue;
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

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public String getOcupacion() {
        return ocupacion;
    }

    public void setOcupacion(String ocupacion) {
        this.ocupacion = ocupacion;
    }

    public String getDireccionId() {
        return direccionId;
    }

    public void setDireccionId(String direccionId) {
        this.direccionId = direccionId;
    }

    public String getIngMensual() {
        return ingMensual;
    }

    public void setIngMensual(String ingMensual) {
        this.ingMensual = ingMensual;
    }

    public String getGastoMensual() {
        return gastoMensual;
    }

    public void setGastoMensual(String gastoMensual) {
        this.gastoMensual = gastoMensual;
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

    public Integer getEstatusCompletado() {
        return estatusCompletado;
    }

    public void setEstatusCompletado(Integer estatusCompletado) {
        this.estatusCompletado = estatusCompletado;
    }

    @Override
    public void fill(Cursor row) {
        this.row = row;

        idConyugue        = getInt(COL_ID_CONYUGUE);
        idSolicitud       = getInt(COL_ID_SOLICITUD);
        nombre            = getString(COL_NOMBRE);
        paterno           = getString(COL_PATERNO);
        materno           = getString(COL_MATERNO);
        nacionalidad      = getString(COL_NACIONALIDAD);
        ocupacion         = getString(COL_OCUPACION);
        direccionId       = getString(COL_DIRECCION_ID);
        ingMensual        = getString(COL_ING_MENSUAL);
        gastoMensual      = getString(COL_GASTO_MENSUAL);
        telCasa           = getString(COL_TEL_CASA);
        telCelular        = getString(COL_TEL_CELULAR);
        estatusCompletado = getInt(COL_ESTATUS_COMPLETADO);
    }
}
