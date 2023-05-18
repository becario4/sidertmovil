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
    public static final String COL_ID_BENEFICIARIO     = "id_Beneficiario";
    public static final String COL_ID_SOLICITUD        = "id_solicitud";
    public static final String COL_GRUPO_ID            = "grupo_id";
    public static final String COL_ID_INTEGRANTE       = "id_integrante";
    public static final String COL_NOMBRE_BENEFICIARIO = "nombreBeneficiario";
    public static final String COL_APELLIDO_PATERNO    = "apellidoPaterno";
    public static final String COL_APELLIDO_MATERNO    = "apellidoMaterno";
    public static final String COL_PARENTESCO          = "parentesco";
    public static final String COL_SERIEID             = "serieid";

    @SerializedName(COL_ID_BENEFICIARIO)
    @Expose
    private Integer id_beneficiario;

    @SerializedName(COL_ID_SOLICITUD)
    @Expose
    private  Long id_solicitud;

    @SerializedName(COL_GRUPO_ID)
    @Expose
    private Long grupo_id;

    @SerializedName(COL_ID_INTEGRANTE)
    @Expose
    private Integer id_integrante;

    @SerializedName(COL_NOMBRE_BENEFICIARIO)
    @Expose
    private String nombreBeneficiario;

    @SerializedName(COL_APELLIDO_PATERNO)
    @Expose
    private String apellidoPaterno;

    @SerializedName(COL_APELLIDO_MATERNO)
    @Expose
    private String apellidoMaterno;

    @SerializedName(COL_PARENTESCO)
    @Expose
    private String parentesco;

    @SerializedName(COL_SERIEID)
    @Expose
    private String serieid;


    public Long getId_solicitud() {
        return id_solicitud;
    }

    public void setId_solicitud(Long id_solicitud) {
        this.id_solicitud = id_solicitud;
    }

    public Long getGrupo_id() {
        return grupo_id;
    }

    public void setGrupo_id(Long grupo_id) {
        this.grupo_id = grupo_id;
    }

    public Integer getId_integrante() {
        return id_integrante;
    }

    public void setId_integrante(Integer id_integrante) {
        this.id_integrante = id_integrante;
    }

    public String getNombreBeneficiario() {
        return nombreBeneficiario;
    }

    public void setNombreBeneficiario(String nombreBeneficiario) {
        this.nombreBeneficiario = nombreBeneficiario;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public String getParentesco() {
        return parentesco;
    }

    public void setParentesco(String parentesco) {
        this.parentesco = parentesco;
    }

    public String getSerieid() {
        return serieid;
    }

    public void setSerieid(String serieid) {
        this.serieid = serieid;
    }

    @Override
    public void fill(Cursor row){
        this.row = row;

        id_solicitud        = getLong(COL_ID_SOLICITUD);
        id_solicitud        = getLong(COL_ID_SOLICITUD);
        grupo_id            = getLong(COL_GRUPO_ID);
        id_integrante       = getInt(COL_ID_INTEGRANTE);
        nombreBeneficiario  = getString(COL_NOMBRE_BENEFICIARIO);
        apellidoPaterno     = getString(COL_APELLIDO_PATERNO);
        apellidoMaterno     = getString(COL_APELLIDO_MATERNO);
        parentesco          = getString(COL_PARENTESCO);
        serieid             = getString(COL_SERIEID);
    }
}
