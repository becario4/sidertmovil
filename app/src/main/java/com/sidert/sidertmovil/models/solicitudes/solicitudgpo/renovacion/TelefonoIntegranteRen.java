package com.sidert.sidertmovil.models.solicitudes.solicitudgpo.renovacion;

import android.database.Cursor;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.sidert.sidertmovil.models.BaseModel;
import com.sidert.sidertmovil.models.IFillModel;

import java.io.Serializable;

public class TelefonoIntegranteRen extends BaseModel implements Serializable, IFillModel {
    public final static String TBL                  = "tbl_telefonos_integrante_ren";
    protected static final String COL_ID_TELEFONICO = "id_telefonico";
    protected static final String COL_ID_INTEGRANTE = "id_integrante";
    protected static final String COL_TEL_CASA      = "tel_casa";
    protected static final String COL_TEL_CELULAR   = "tel_celular";
    protected static final String COL_TEL_MENSAJE   = "tel_mensaje";
    protected static final String COL_TEL_TRABAJO   = "tel_trabajo";
    protected static final String COL_ESTATUS_COMPLETADO   = "estatus_completado";

    @SerializedName(COL_ID_TELEFONICO)
    @Expose
    private Integer idTelefonico;

    @SerializedName(COL_ID_INTEGRANTE)
    @Expose
    private Integer idIntegrante;

    @SerializedName(COL_TEL_CASA)
    @Expose
    private String telCasa;

    @SerializedName(COL_TEL_CELULAR)
    @Expose
    private String telCelular;

    @SerializedName(COL_TEL_MENSAJE)
    @Expose
    private String telMensaje;

    @SerializedName(COL_TEL_TRABAJO)
    @Expose
    private String telTrabajo;

    @SerializedName(COL_ESTATUS_COMPLETADO)
    @Expose
    private Integer estatusCompletado;

    public Integer getIdTelefonico() {
        return idTelefonico;
    }

    public void setIdTelefonico(Integer idTelefonico) {
        this.idTelefonico = idTelefonico;
    }

    public Integer getIdIntegrante() {
        return idIntegrante;
    }

    public void setIdIntegrante(Integer idIntegrante) {
        this.idIntegrante = idIntegrante;
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

    public String getTelMensaje() {
        return telMensaje;
    }

    public void setTelMensaje(String telMensaje) {
        this.telMensaje = telMensaje;
    }

    public String getTelTrabajo() {
        return telTrabajo;
    }

    public void setTelTrabajo(String telTrabajo) {
        this.telTrabajo = telTrabajo;
    }

    @Override
    public void fill(Cursor row) {
        this.row = row;

        idTelefonico = getInt(COL_ID_TELEFONICO);
        idIntegrante = getInt(COL_ID_INTEGRANTE);
        telCasa      = getString(COL_TEL_CASA);
        telCelular   = getString(COL_TEL_CELULAR);
        telMensaje   = getString(COL_TEL_MENSAJE);
        telTrabajo   = getString(COL_TEL_TRABAJO);
        estatusCompletado = getInt(COL_ESTATUS_COMPLETADO);

    }
}
