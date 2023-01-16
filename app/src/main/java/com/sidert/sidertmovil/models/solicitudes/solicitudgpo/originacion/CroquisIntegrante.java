package com.sidert.sidertmovil.models.solicitudes.solicitudgpo.originacion;

import android.database.Cursor;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.sidert.sidertmovil.models.BaseModel;
import com.sidert.sidertmovil.models.IFillModel;

import java.io.Serializable;

public class CroquisIntegrante extends BaseModel implements Serializable, IFillModel {
    public static final String TBL                              = "tbl_croquis_gpo";
    protected static final String COL_ID                        = "id";
    protected static final String COL_ID_INTEGRANTE             = "id_integrante";
    protected static final String COL_CALLE_PRINCIPAL           = "calle_principal";
    protected static final String COL_LATERAL_UNO               = "lateral_uno";
    protected static final String COL_LATERAL_DOS               = "lateral_dos";
    protected static final String COL_CALLE_TRASERA             = "calle_trasera";
    protected static final String COL_REFERENCIAS               = "referencias";
    protected static final String COL_ESTATUS_COMPLETADO        = "estatus_completado";
    protected static final String COL_CARACTERISTICAS_DOMICILIO = "caracteristicas_domicilio";

    @SerializedName(COL_ID)
    @Expose
    private Integer id;

    @SerializedName(COL_ID_INTEGRANTE)
    @Expose
    private Integer idIntegrante;

    @SerializedName(COL_CALLE_PRINCIPAL)
    @Expose
    private String callePrincipal;

    @SerializedName(COL_LATERAL_UNO)
    @Expose
    private String lateralUno;

    @SerializedName(COL_LATERAL_DOS)
    @Expose
    private String lateralDos;

    @SerializedName(COL_CALLE_TRASERA)
    @Expose
    private String calleTrasera;

    @SerializedName(COL_REFERENCIAS)
    @Expose
    private String referencias;

    @SerializedName(COL_ESTATUS_COMPLETADO)
    @Expose
    private Integer estatusCompletado;

    @SerializedName(COL_CARACTERISTICAS_DOMICILIO)
    @Expose
    private String caracteristicasDomicilio;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdIntegrante() {
        return idIntegrante;
    }

    public void setIdIntegrante(Integer idIntegrante) {
        this.idIntegrante = idIntegrante;
    }

    public String getCallePrincipal() {
        return callePrincipal;
    }

    public void setCallePrincipal(String callePrincipal) {
        this.callePrincipal = callePrincipal;
    }

    public String getLateralUno() {
        return lateralUno;
    }

    public void setLateralUno(String lateralUno) {
        this.lateralUno = lateralUno;
    }

    public String getLateralDos() {
        return lateralDos;
    }

    public void setLateralDos(String lateralDos) {
        this.lateralDos = lateralDos;
    }

    public String getCalleTrasera() {
        return calleTrasera;
    }

    public void setCalleTrasera(String calleTrasera) {
        this.calleTrasera = calleTrasera;
    }

    public String getReferencias() {
        return referencias;
    }

    public void setReferencias(String referencias) {
        this.referencias = referencias;
    }

    public Integer getEstatusCompletado() {
        return estatusCompletado;
    }

    public void setEstatusCompletado(Integer estatusCompletado) {
        this.estatusCompletado = estatusCompletado;
    }

    public String getCaracteristicasDomicilio() {
        return caracteristicasDomicilio;
    }

    public void setCaracteristicasDomicilio(String caracteristicasDomicilio) {
        this.caracteristicasDomicilio = caracteristicasDomicilio;
    }

    @Override
    public void fill(Cursor row) {
        this.row = row;

        id                       = getInt(COL_ID);
        idIntegrante             = getInt(COL_ID_INTEGRANTE);
        callePrincipal           = getString(COL_CALLE_PRINCIPAL);
        lateralUno               = getString(COL_LATERAL_UNO);
        lateralDos               = getString(COL_LATERAL_DOS);
        calleTrasera             = getString(COL_CALLE_TRASERA);
        referencias              = getString(COL_REFERENCIAS);
        estatusCompletado        = getInt(COL_ESTATUS_COMPLETADO);
        caracteristicasDomicilio = getString(COL_CARACTERISTICAS_DOMICILIO);
    }
}
