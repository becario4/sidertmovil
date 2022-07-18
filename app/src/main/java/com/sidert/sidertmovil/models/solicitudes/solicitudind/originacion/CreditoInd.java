package com.sidert.sidertmovil.models.solicitudes.solicitudind.originacion;

import android.database.Cursor;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.sidert.sidertmovil.models.BaseModel;
import com.sidert.sidertmovil.models.IFillModel;

import java.io.Serializable;

public class CreditoInd extends BaseModel implements Serializable, IFillModel {
    public final static String TBL                         = "tbl_credito_ind";
    protected static final String COL_ID_CREDITO           = "id_credito";
    protected static final String COL_ID_SOLICITUD         = "id_solicitud";
    protected static final String COL_PLAZO                = "plazo";
    protected static final String COL_PERIODICIDAD         = "periodicidad";
    protected static final String COL_FECHA_DESEMBOLSO     = "fecha_desembolso";
    protected static final String COL_DIA_DESEMBOLSO       = "dia_desembolso";
    protected static final String COL_HORA_VISITA          = "hora_visita";
    protected static final String COL_MONTO_PRESTAMO       = "monto_prestamo";
    protected static final String COL_DESTINO              = "destino";
    protected static final String COL_CLASIFICACION_RIESGO = "clasificacion_riesgo";
    protected static final String COL_ESTATUS_COMPLETADO   = "estatus_completado";
    protected static final String COL_MONTO_REFINANCIAR    = "monto_refinanciar";

    @SerializedName(COL_ID_CREDITO)
    @Expose
    private Integer idCredito;

    @SerializedName(COL_ID_SOLICITUD)
    @Expose
    private Integer idSolicitud;

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
    private String disDesembolso;

    @SerializedName(COL_HORA_VISITA)
    @Expose
    private String horaVisita;

    @SerializedName(COL_MONTO_PRESTAMO)
    @Expose
    private String montoPrestamo;

    @SerializedName(COL_DESTINO)
    @Expose
    private String destino;

    @SerializedName(COL_CLASIFICACION_RIESGO)
    @Expose
    private String clasificacionRiesgo;

    @SerializedName(COL_ESTATUS_COMPLETADO)
    @Expose
    private Integer estatusCompletado;

    @SerializedName(COL_MONTO_REFINANCIAR)
    @Expose
    private String montoRefinanciar;

    public Integer getIdCredito() {
        return idCredito;
    }

    public void setIdCredito(Integer idCredito) {
        this.idCredito = idCredito;
    }

    public Integer getIdSolicitud() {
        return idSolicitud;
    }

    public void setIdSolicitud(Integer idSolicitud) {
        this.idSolicitud = idSolicitud;
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

    public String getDisDesembolso() {
        return disDesembolso;
    }

    public void setDisDesembolso(String disDesembolso) {
        this.disDesembolso = disDesembolso;
    }

    public String getHoraVisita() {
        return horaVisita;
    }

    public void setHoraVisita(String horaVisita) {
        this.horaVisita = horaVisita;
    }

    public String getMontoPrestamo() {
        return montoPrestamo;
    }

    public void setMontoPrestamo(String montoPrestamo) {
        this.montoPrestamo = montoPrestamo;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public String getClasificacionRiesgo() {
        return clasificacionRiesgo;
    }

    public void setClasificacionRiesgo(String clasificacionRiesgo) {
        this.clasificacionRiesgo = clasificacionRiesgo;
    }

    public Integer getEstatusCompletado() {
        return estatusCompletado;
    }

    public void setEstatusCompletado(Integer estatusCompletado) {
        this.estatusCompletado = estatusCompletado;
    }

    public String getMontoRefinanciar() {
        return montoRefinanciar;
    }

    public void setMontoRefinanciar(String montoRefinanciar) {
        this.montoRefinanciar = montoRefinanciar;
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

        idCredito           = getInt(COL_ID_CREDITO);
        idSolicitud         = getInt(COL_ID_SOLICITUD);
        plazo               = getString(COL_PLAZO);
        periodicidad        = getString(COL_PERIODICIDAD);
        fechaDesembolso     = getString(COL_FECHA_DESEMBOLSO);
        disDesembolso       = getString(COL_DIA_DESEMBOLSO);
        horaVisita          = getString(COL_HORA_VISITA);
        montoPrestamo       = getString(COL_MONTO_PRESTAMO);
        destino             = getString(COL_DESTINO);
        clasificacionRiesgo = getString(COL_CLASIFICACION_RIESGO);
        estatusCompletado   = getInt(COL_ESTATUS_COMPLETADO);
        montoRefinanciar    = getString(COL_MONTO_REFINANCIAR);
    }
}
