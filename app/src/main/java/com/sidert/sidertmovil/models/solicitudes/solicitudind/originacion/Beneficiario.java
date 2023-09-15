package com.sidert.sidertmovil.models.solicitudes.solicitudind.originacion;

import android.database.Cursor;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.sidert.sidertmovil.models.BaseModel;
import com.sidert.sidertmovil.models.IFillModel;

import java.io.Serializable;

public class Beneficiario extends BaseModel implements Serializable, IFillModel{


    public static final String  TBL                              = "tbl_datos_beneficiario_ind";
    public static final String  COL_ID_BENEFICIARIO              = "idBeneficiario";
    public static final String  COL_ID_SOLICITUD                 = "id_solicitud";
    public static final String  COL_ID_ORIGINACION               = "id_originacion"; /** ID_ORIGICACION = SOLICITANTE_ID*/
    public static final String  COL_CLIENTE_ID                   = "id_cliente";
    public static final String  COL_GRUPO_ID                     = "id_grupo";
    public static final String  COL_NOMBRE_BENEFICIARIO          = "nombre";
    public static final String  COL_APELLIDO_PATERNO             = "paterno";
    public static final String  COL_APELLIDO_MATERNO             = "materno";
    public static final String  COL_PARENTESCO                   = "parentesco";
    public static final String  COL_SERIEID                      = "serieid";


    @SerializedName(COL_ID_BENEFICIARIO)
    @Expose
    private Integer idBeneficiario;

    @SerializedName(COL_ID_SOLICITUD)
    @Expose
    private Integer idSolicitud;

    @SerializedName(COL_ID_ORIGINACION)
    @Expose
    private Integer id_originacion;

    @SerializedName(COL_CLIENTE_ID)
    @Expose
    private Integer cliente_id;


    @SerializedName(COL_GRUPO_ID)
    @Expose
    private Integer grupo_id;

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
    private Integer serieId;

    public Integer getIdBeneficiario() { return idBeneficiario; }

    public void setIdBeneficiario(Integer idBeneficiario) { this.idBeneficiario = idBeneficiario; }

    public Integer getIdSolicitud() {
        return idSolicitud;
    }

    public void setIdSolicitud(Integer idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    public Integer getId_originacion() { return id_originacion; }

    public void setId_originacion(Integer id_originacion) { this.id_originacion = id_originacion; }

    public Integer getCliente_id() {
        return cliente_id;
    }

    public void setCliente_id(Integer cliente_id) {
        this.cliente_id = cliente_id;
    }

    public Integer getGrupo_id() {
        return grupo_id;
    }

    public void setGrupo_id(Integer grupo_id) {
        this.grupo_id = grupo_id;
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

    public Integer getSerieId() {
        return serieId;
    }

    public void setSerieId(Integer serieId) {
        this.serieId = serieId;
    }

    @Override
    public void fill(Cursor row){
        this.row = row;

        idBeneficiario      = getInt(COL_ID_BENEFICIARIO);
        idSolicitud         = getInt(COL_ID_SOLICITUD);
        id_originacion      = getInt(COL_ID_ORIGINACION);
        cliente_id          = getInt(COL_CLIENTE_ID);
        grupo_id            = getInt(COL_GRUPO_ID);
        nombre              = getString(COL_NOMBRE_BENEFICIARIO);
        paterno             = getString(COL_APELLIDO_PATERNO);
        materno             = getString(COL_APELLIDO_MATERNO);
        parentesco          = getString(COL_PARENTESCO);
        serieId             = getInt(COL_SERIEID);
    }
}
