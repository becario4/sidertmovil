package com.sidert.sidertmovil.models.solicitudes.solicitudgpo.originacion;

import static java.lang.Long.getLong;

import android.database.Cursor;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.sidert.sidertmovil.models.BaseModel;
import com.sidert.sidertmovil.models.IFillModel;

import java.io.Serializable;

public class BeneficiarioIntegrante extends BaseModel implements Serializable, IFillModel {

    public static final String TBL                     = "tbl_datos_beneficiario_gpo";
    public static final String  COL_ID_BENEFICIARIO              = "idBeneficiario";
    public static final String  COL_ID_SOLICITUD                 = "id_solicitud";
    public static final String  COL_ID_SOLICITUD_INTEGRANTE      = "id_solicitud_integrante";
    public static final String  COL_ID_CLIENTE                   = "id_cliente";
    public static final String  COL_ID_INTEGRANTE                = "id_integrante";
    public static final String  COL_ID_GRUPO                     = "id_grupo";
    public static final String  COL_NOMBRE_BENEFICIARIO          = "nombre";
    public static final String  COL_APELLIDO_PATERNO             = "paterno";
    public static final String  COL_APELLIDO_MATERNO             = "materno";
    public static final String  COL_PARENTESCO                   = "parentesco";
    public static final String  COL_SERIEID                      = "serieid";

    @SerializedName(COL_ID_BENEFICIARIO)
    @Expose
    private Integer id_beneficiario;

    @SerializedName(COL_ID_SOLICITUD)
    @Expose
    private Integer id_solicitud;

    @SerializedName(COL_ID_SOLICITUD_INTEGRANTE)
    @Expose
    private Integer id_solicitud_integrante;

    @SerializedName(COL_ID_CLIENTE)
    @Expose
    private Integer id_cliente;

    @SerializedName(COL_ID_INTEGRANTE)
    @Expose
    private Integer id_integrante;

    @SerializedName(COL_ID_GRUPO)
    @Expose
    private Integer id_grupo;

    @SerializedName(COL_NOMBRE_BENEFICIARIO)
    @Expose
    private String nombre;

    @SerializedName(COL_APELLIDO_PATERNO)
    @Expose
    private String paterno;


    @SerializedName(COL_APELLIDO_MATERNO)
    @Expose
    private String materno;

    @SerializedName(COL_PARENTESCO)
    @Expose
    private String parentesco;


    @SerializedName(COL_SERIEID)
    @Expose
    private Integer serie_id;

    public static String getTBL() {
        return TBL;
    }

    public Integer getId_beneficiario() {
        return id_beneficiario;
    }

    public void setId_beneficiario(Integer id_beneficiario) { this.id_beneficiario = id_beneficiario; }

    public Integer getId_solicitud() {
        return id_solicitud;
    }

    public void setId_solicitud(Integer id_solicitud) {
        this.id_solicitud = id_solicitud;
    }

    public Integer getId_solicitud_integrante() {
        return id_solicitud_integrante;
    }

    public void setId_solicitud_integrante(Integer id_solicitud_integrante) { this.id_solicitud_integrante = id_solicitud_integrante; }

    public Integer getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(Integer id_cliente) {
        this.id_cliente = id_cliente;
    }

    public Integer getId_integrante() { return id_integrante;}

    public void setId_integrante(Integer id_integrante) { this.id_integrante = id_integrante; }

    public Integer getId_grupo() {
        return id_grupo;
    }

    public void setId_grupo(Integer id_grupo) {
        this.id_grupo = id_grupo;
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

    public String getParentesco() {
        return parentesco;
    }

    public void setParentesco(String parentesco) {
        this.parentesco = parentesco;
    }

    public Integer getSerie_id() {
        return serie_id;
    }

    public void setSerie_id(Integer serie_id) {
        this.serie_id = serie_id;
    }


    @Override
    public void fill(Cursor row){
        this.row = row;

        id_solicitud            = getInt(COL_ID_SOLICITUD);
        id_solicitud_integrante = getInt(COL_ID_SOLICITUD_INTEGRANTE);
        id_cliente              = getInt(COL_ID_CLIENTE);
        id_integrante           = getInt(COL_ID_INTEGRANTE);
        id_grupo                = getInt(COL_ID_GRUPO);
        nombre                  = getString(COL_NOMBRE_BENEFICIARIO);
        paterno                 = getString(COL_APELLIDO_PATERNO);
        materno                 = getString(COL_APELLIDO_MATERNO);
        parentesco              = getString(COL_PARENTESCO);
        serie_id                = getInt(COL_SERIEID);
    }
}
