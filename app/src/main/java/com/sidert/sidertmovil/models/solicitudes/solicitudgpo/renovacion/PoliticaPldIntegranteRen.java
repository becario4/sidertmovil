package com.sidert.sidertmovil.models.solicitudes.solicitudgpo.renovacion;

import android.database.Cursor;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.sidert.sidertmovil.models.BaseModel;
import com.sidert.sidertmovil.models.IFillModel;

import java.io.Serializable;

public class PoliticaPldIntegranteRen extends BaseModel implements Serializable, IFillModel {
    public static final String TBL                       = "tbl_politicas_pld_integrante_ren";
    protected static final String COL_ID_POLITICA        = "id_politica";
    protected static final String COL_ID_INTEGRANTE      = "id_integrante";
    protected static final String COL_PROPIETARIO_REAL   = "propietario_real";
    protected static final String COL_PROVEEDOR_RECURSOS = "proveedor_recursos";
    protected static final String COL_PERSONA_POLITICA   = "persona_politica";
    protected static final String COL_ESTATUS_COMPLETADO = "estatus_completado";

    @SerializedName(COL_ID_POLITICA)
    @Expose
    private Integer idPolitica;

    @SerializedName(COL_ID_INTEGRANTE)
    @Expose
    private Integer idIntegrante;

    @SerializedName(COL_PROPIETARIO_REAL)
    @Expose
    private Integer propietarioReal;

    @SerializedName(COL_PROVEEDOR_RECURSOS)
    @Expose
    private Integer proveedorRecursos;

    @SerializedName(COL_PERSONA_POLITICA)
    @Expose
    private Integer personaPolitica;

    @SerializedName(COL_ESTATUS_COMPLETADO)
    @Expose
    private Integer estatusCompletado;


    public Integer getIdPolitica() {
        return idPolitica;
    }

    public void setIdPolitica(Integer idPolitica) {
        this.idPolitica = idPolitica;
    }

    public Integer getIdIntegrante() {
        return idIntegrante;
    }

    public void setIdIntegrante(Integer idIntegrante) {
        this.idIntegrante = idIntegrante;
    }

    public Integer getPropietarioReal() {
        return propietarioReal;
    }

    public void setPropietarioReal(Integer propietarioReal) {
        this.propietarioReal = propietarioReal;
    }

    public Integer getProveedorRecursos() {
        return proveedorRecursos;
    }

    public void setProveedorRecursos(Integer proveedorRecursos) {
        this.proveedorRecursos = proveedorRecursos;
    }

    public Integer getPersonaPolitica() {
        return personaPolitica;
    }

    public void setPersonaPolitica(Integer personaPolitica) {
        this.personaPolitica = personaPolitica;
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

        idPolitica        = getInt(COL_ID_POLITICA);
        idIntegrante      = getInt(COL_ID_INTEGRANTE);
        propietarioReal   = getInt(COL_PROPIETARIO_REAL);
        proveedorRecursos = getInt(COL_PROVEEDOR_RECURSOS);
        personaPolitica   = getInt(COL_PERSONA_POLITICA);
        estatusCompletado = getInt(COL_ESTATUS_COMPLETADO);
    }
}
