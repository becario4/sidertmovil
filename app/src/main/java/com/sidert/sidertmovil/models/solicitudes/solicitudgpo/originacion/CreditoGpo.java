package com.sidert.sidertmovil.models.solicitudes.solicitudgpo.originacion;

import android.database.Cursor;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.sidert.sidertmovil.models.BaseModel;
import com.sidert.sidertmovil.models.IFillModel;

import java.io.Serializable;

public class CreditoGpo extends BaseModel implements Serializable, IFillModel {
    public final static String TBL = "tbl_credito_gpo";
    protected static final String COL_ID                 = "id";
    protected static final String COL_ID_SOLICITUD       = "id_solicitud";
    protected static final String COL_NOMBRE_GRUPO       = "nombre_grupo";
    protected static final String COL_PLAZO              = "plazo";
    protected static final String COL_PERIODICIDAD       = "periodicidad";
    protected static final String COL_FECHA_DESEMBOLSO   = "fecha_desembolso";
    protected static final String COL_DIA_DESEMBOLSO     = "dia_desembolso";
    protected static final String COL_HORA_VISITA        = "hora_visita";
    protected static final String COL_ESTATUS_COMPLETADO = "estatus_completado";

    @SerializedName(COL_ID)
    @Expose
    private Integer id;

    @SerializedName(COL_ID_SOLICITUD)
    @Expose
    private Integer idSolicitud;

    @SerializedName(COL_NOMBRE_GRUPO)
    @Expose
    private String nombreGrupo;

    @SerializedName(COL_PLAZO)
    @Expose
    private String plazo;

    @SerializedName(COL_PERIODICIDAD)
    @Expose
    private String periodicidad;

    @SerializedName(COL_FECHA_DESEMBOLSO)
    @Expose
    private String fechaDesembolso;

    @SerializedName(COL_DIA_DESEMBOLSO)
    @Expose
    private String diaDesembolso;

    @SerializedName(COL_HORA_VISITA)
    @Expose
    private String horaVisita;

    @SerializedName(COL_ESTATUS_COMPLETADO)
    @Expose
    private Integer estatusCompletado;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdSolicitud() {
        return idSolicitud;
    }

    public void setIdSolicitud(Integer idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    public String getNombreGrupo() {
        return nombreGrupo;
    }

    public void setNombreGrupo(String nombreGrupo) {
        this.nombreGrupo = nombreGrupo;
    }

    public String getPlazo() {
        return plazo;
    }

    public void setPlazo(String plazo) {
        this.plazo = plazo;
    }

    public String getPeriodicidad() {
        return periodicidad;
    }

    public void setPeriodicidad(String periodicidad) {
        this.periodicidad = periodicidad;
    }

    public String getFechaDesembolso() {
        return fechaDesembolso;
    }

    public void setFechaDesembolso(String fechaDesembolso) {
        this.fechaDesembolso = fechaDesembolso;
    }

    public String getDiaDesembolso() {
        return diaDesembolso;
    }

    public void setDiaDesembolso(String diaDesembolso) {
        this.diaDesembolso = diaDesembolso;
    }

    public String getHoraVisita() {
        return horaVisita;
    }

    public void setHoraVisita(String horaVisita) {
        this.horaVisita = horaVisita;
    }

    public Integer getEstatusCompletado() {
        return estatusCompletado;
    }

    public void setEstatusCompletado(Integer estatusCompletado) {
        this.estatusCompletado = estatusCompletado;
    }

    public int getPlazoAsInt()
    {
        int meses = 0;

        switch (plazo) {
            case "4 MESES":
                meses = 4;
                break;
            case "5 MESES":
                meses = 5;
                break;
            case "6 MESES":
                meses = 6;
                break;
            case "9 MESES":
                meses = 9;
                break;
        }

        return meses;
    }

    public int getPeriodicidadAsInt()
    {
        int no_periodicidad = 0;
        switch (periodicidad){
            case "SEMANAL":
                no_periodicidad = 7;
                break;
            case "CATORCENAL":
                no_periodicidad = 14;
                break;
            case "QUINCENAL":
                no_periodicidad = 15;
                break;
            case "MENSUAL":
                no_periodicidad = 30;
                break;
        }
        return no_periodicidad;
    }

    @Override
    public void fill(Cursor row) {
        this.row = row;

        id                = getInt(COL_ID);
        idSolicitud       = getInt(COL_ID_SOLICITUD);
        nombreGrupo       = getString(COL_NOMBRE_GRUPO);
        plazo             = getString(COL_PLAZO);
        periodicidad      = getString(COL_PERIODICIDAD);
        fechaDesembolso   = getString(COL_FECHA_DESEMBOLSO);
        diaDesembolso     = getString(COL_DIA_DESEMBOLSO);
        horaVisita        = getString(COL_HORA_VISITA);
        estatusCompletado = getInt(COL_ESTATUS_COMPLETADO);
    }
}
