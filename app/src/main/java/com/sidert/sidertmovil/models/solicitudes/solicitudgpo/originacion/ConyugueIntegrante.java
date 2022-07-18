package com.sidert.sidertmovil.models.solicitudes.solicitudgpo.originacion;

import android.database.Cursor;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.sidert.sidertmovil.models.BaseModel;
import com.sidert.sidertmovil.models.IFillModel;

import java.io.Serializable;

public class ConyugueIntegrante extends BaseModel implements Serializable, IFillModel {
    public final static String TBL                       = "tbl_conyuge_integrante";
    protected static final String COL_ID_CONYUGUE        = "id_conyuge";
    protected static final String COL_ID_INTEGRANTE      = "id_integrante";
    protected static final String COL_NOMBRE             = "nombre";
    protected static final String COL_PATERNO            = "paterno";
    protected static final String COL_MATERNO            = "materno";
    protected static final String COL_NACIONALIDAD       = "nacionalidad";
    protected static final String COL_OCUPACION          = "ocupacion";
    protected static final String COL_CALLE              = "calle";
    protected static final String COL_NO_EXTERIOR        = "no_exterior";
    protected static final String COL_NO_INTERIOR        = "no_interior";
    protected static final String COL_MANZANA            = "manzana";
    protected static final String COL_LOTE               = "lote";
    protected static final String COL_CP                 = "cp";
    protected static final String COL_COLONIA            = "colonia";
    protected static final String COL_CIUDAD             = "ciudad";
    protected static final String COL_LOCALIDAD          = "localidad";
    protected static final String COL_MUNICIPIO          = "municipio";
    protected static final String COL_ESTADO             = "estado";
    protected static final String COL_INGRESOS_MENSUAL   = "ingresos_mensual";
    protected static final String COL_GASTO_MENSUAL      = "gasto_mensual";
    protected static final String COL_TEL_CELULAR        = "tel_celular";
    protected static final String COL_TEL_TRABAJO        = "tel_trabajo";
    protected static final String COL_ESTATUS_COMPLETADO = "estatus_completado";

    @SerializedName(COL_ID_CONYUGUE)
    @Expose
    private Integer idConyugue;

    @SerializedName(COL_ID_INTEGRANTE)
    @Expose
    private Integer idIntegrante;

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

    @SerializedName(COL_CALLE)
    @Expose
    private String calle;

    @SerializedName(COL_NO_EXTERIOR)
    @Expose
    private String noExterior;

    @SerializedName(COL_NO_INTERIOR)
    @Expose
    private String noInterior;

    @SerializedName(COL_MANZANA)
    @Expose
    private String manzana;

    @SerializedName(COL_LOTE)
    @Expose
    private String lote;

    @SerializedName(COL_CP)
    @Expose
    private String cp;

    @SerializedName(COL_COLONIA)
    @Expose
    private String colonia;

    @SerializedName(COL_CIUDAD)
    @Expose
    private String ciudad;

    @SerializedName(COL_LOCALIDAD)
    @Expose
    private String localidad;

    @SerializedName(COL_MUNICIPIO)
    @Expose
    private String municipio;

    @SerializedName(COL_ESTADO)
    @Expose
    private String estado;

    @SerializedName(COL_INGRESOS_MENSUAL)
    @Expose
    private String ingresoMensual;

    @SerializedName(COL_GASTO_MENSUAL)
    @Expose
    private String gastoMensual;

    @SerializedName(COL_TEL_CELULAR)
    @Expose
    private String telCelular;

    @SerializedName(COL_TEL_TRABAJO)
    @Expose
    private String telTrabajo;

    @SerializedName(COL_ESTATUS_COMPLETADO)
    @Expose
    private Integer estatusCompletado;

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public Integer getIdConyugue() {
        return idConyugue;
    }

    public void setIdConyugue(Integer idConyugue) {
        this.idConyugue = idConyugue;
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

    public String getIngresoMensual() {
        return ingresoMensual;
    }

    public void setIngresoMensual(String ingresoMensual) {
        this.ingresoMensual = ingresoMensual;
    }

    public String getGastoMensual() {
        return gastoMensual;
    }

    public void setGastoMensual(String gastoMensual) {
        this.gastoMensual = gastoMensual;
    }

    public String getTelCelular() {
        return telCelular;
    }

    public void setTelCelular(String telCelular) {
        this.telCelular = telCelular;
    }

    public String getTelTrabajo() {
        return telTrabajo;
    }

    public void setTelTrabajo(String telTrabajo) {
        this.telTrabajo = telTrabajo;
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
        idIntegrante       = getInt(COL_ID_INTEGRANTE);
        nombre            = getString(COL_NOMBRE);
        paterno           = getString(COL_PATERNO);
        materno           = getString(COL_MATERNO);
        nacionalidad      = getString(COL_NACIONALIDAD);
        ocupacion         = getString(COL_OCUPACION);
        calle             = getString(COL_CALLE);
        noExterior        = getString(COL_NO_EXTERIOR);
        noInterior        = getString(COL_NO_INTERIOR);
        manzana           = getString(COL_MANZANA);
        lote              = getString(COL_LOTE);
        cp                = getString(COL_CP);
        colonia           = getString(COL_COLONIA);
        ciudad            = getString(COL_CIUDAD);
        localidad         = getString(COL_LOCALIDAD);
        municipio         = getString(COL_MUNICIPIO);
        estado            = getString(COL_ESTADO);
        ingresoMensual    = getString(COL_INGRESOS_MENSUAL);
        gastoMensual      = getString(COL_GASTO_MENSUAL);
        telTrabajo        = getString(COL_TEL_TRABAJO);
        telCelular        = getString(COL_TEL_CELULAR);
        estatusCompletado = getInt(COL_ESTATUS_COMPLETADO);
    }
}
