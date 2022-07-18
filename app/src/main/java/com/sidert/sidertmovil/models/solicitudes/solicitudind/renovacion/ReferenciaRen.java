package com.sidert.sidertmovil.models.solicitudes.solicitudind.renovacion;

import android.database.Cursor;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.sidert.sidertmovil.models.BaseModel;
import com.sidert.sidertmovil.models.IFillModel;

import java.io.Serializable;

public class ReferenciaRen extends BaseModel implements Serializable, IFillModel {
    public static final String TBL                       = "tbl_referencia_ind_ren";
    protected static final String COL_ID_REFERENCIA      = "id_referencia";
    protected static final String COL_ID_SOLICITUD       = "id_solicitud";
    protected static final String COL_NOMBRE             = "nombre";
    protected static final String COL_PATERNO            = "paterno";
    protected static final String COL_MATERNO            = "materno";
    protected static final String COL_FECHA_NACIMIENTO   = "fecha_nacimiento";
    protected static final String COL_DIRECCION_ID       = "direccion_id";
    protected static final String COL_TEL_CELULAR        = "tel_celular";
    protected static final String COL_ESTATUS_COMPLETADO = "estatus_completado";
    protected static final String COL_COMENTARIO_RECHAZO = "comentario_rechazo";

    @SerializedName(COL_ID_REFERENCIA)
    @Expose
    private Integer idReferencia;

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

    @SerializedName(COL_DIRECCION_ID)
    @Expose
    private String direccionId;

    @SerializedName(COL_TEL_CELULAR)
    @Expose
    private String telCelular;

    @SerializedName(COL_ESTATUS_COMPLETADO)
    @Expose
    private Integer estatusCompletado;

    @SerializedName(COL_COMENTARIO_RECHAZO)
    @Expose
    private String comentarioRechazo;

    public Integer getIdReferencia() {
        return idReferencia;
    }

    public void setIdReferencia(Integer idReferencia) {
        this.idReferencia = idReferencia;
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

    public String getDireccionId() {
        return direccionId;
    }

    public void setDireccionId(String direccionId) {
        this.direccionId = direccionId;
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

    public String getComentarioRechazo() {
        return comentarioRechazo;
    }

    public void setComentarioRechazo(String comentarioRechazo) {
        this.comentarioRechazo = comentarioRechazo;
    }

    @Override
    public void fill(Cursor row) {
        this.row = row;

        idReferencia      = getInt(COL_ID_REFERENCIA);
        idSolicitud       = getInt(COL_ID_SOLICITUD);
        nombre            = getString(COL_NOMBRE);
        paterno           = getString(COL_PATERNO);
        materno           = getString(COL_MATERNO);
        direccionId       = getString(COL_DIRECCION_ID);
        telCelular        = getString(COL_TEL_CELULAR);
        fechaNacimiento   = getString(COL_FECHA_NACIMIENTO);
        estatusCompletado = getInt(COL_ESTATUS_COMPLETADO);
        comentarioRechazo = getString(COL_COMENTARIO_RECHAZO);
    }
}