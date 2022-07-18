package com.sidert.sidertmovil.models.solicitudes.solicitudind.renovacion;

import android.database.Cursor;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.sidert.sidertmovil.models.BaseModel;
import com.sidert.sidertmovil.models.IFillModel;

import java.io.Serializable;

public class EconomicoRen extends BaseModel implements Serializable, IFillModel {
    public final static String TBL                       = "tbl_economico_ind_ren";
    protected static final String COL_ID_ECONOMICO       = "id_economico";
    protected static final String COL_ID_SOLICITUD       = "id_solicitud";
    protected static final String COL_PROPIEDADES        = "propiedades";
    protected static final String COL_VALOR_APROXIMADO   = "valor_aproximado";
    protected static final String COL_UBICACION          = "ubicacion";
    protected static final String COL_INGRESO            = "ingreso";
    protected static final String COL_ESTATUS_COMPLETADO = "estatus_completado";

    @SerializedName(COL_ID_ECONOMICO)
    @Expose
    private Integer idEconomico;

    @SerializedName(COL_ID_SOLICITUD)
    @Expose
    private Integer idSolicitud;

    @SerializedName(COL_PROPIEDADES)
    @Expose
    private String propiedades;

    @SerializedName(COL_VALOR_APROXIMADO)
    @Expose
    private String valorAproximado;

    @SerializedName(COL_UBICACION)
    @Expose
    private String ubicacion;

    @SerializedName(COL_INGRESO)
    @Expose
    private String ingreso;

    @SerializedName("estatus_completado")
    @Expose
    private Integer estatusCompletado;

    public Integer getIdEconomico() {
        return idEconomico;
    }

    public void setIdEconomico(Integer idEconomico) {
        this.idEconomico = idEconomico;
    }

    public Integer getIdSolicitud() {
        return idSolicitud;
    }

    public void setIdSolicitud(Integer idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    public String getPropiedades() {
        return propiedades;
    }

    public void setPropiedades(String propiedades) {
        this.propiedades = propiedades;
    }

    public String getValorAproximado() {
        return valorAproximado;
    }

    public void setValorAproximado(String valorAproximado) {
        this.valorAproximado = valorAproximado;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getIngreso() {
        return ingreso;
    }

    public void setIngreso(String ingreso) {
        this.ingreso = ingreso;
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

        idEconomico       = getInt(COL_ID_ECONOMICO);
        idSolicitud       = getInt(COL_ID_SOLICITUD);
        propiedades       = getString(COL_PROPIEDADES);
        valorAproximado   = getString(COL_VALOR_APROXIMADO);
        ubicacion         = getString(COL_UBICACION);
        ingreso           = getString(COL_INGRESO);
        estatusCompletado = getInt(COL_ESTATUS_COMPLETADO);
    }
}
