package com.sidert.sidertmovil.models.solicitudes.solicitudind.renovacion;

import android.database.Cursor;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.sidert.sidertmovil.models.BaseModel;
import com.sidert.sidertmovil.models.IFillModel;

import java.io.Serializable;

public class DocumentoRen extends BaseModel implements Serializable, IFillModel {
    public static final String TBL                         = "tbl_documentos_ren";
    protected static final String COL_ID_DOCUMENTO         = "id_documento";
    protected static final String COL_ID_SOLICITUD         = "id_solicitud";
    protected static final String COL_INE_FRONTAL          = "ine_frontal";
    protected static final String COL_INE_REVERSO          = "ine_reverso";
    protected static final String COL_CURP                 = "curp";
    protected static final String COL_COMPROBANTE          = "comprobante";
    protected static final String COL_CODIGO_BARRAS        = "codigo_barras";
    protected static final String COL_FIRMA_ASESOR         = "firma_asesor";
    protected static final String COL_ESTATUS_COMPLETADO   = "estatus_completado";
    protected static final String COL_INE_SELFIE           = "ine_selfie";
    protected static final String COL_COMPROBANTE_GARANTIA = "comprobante_garantia";
    protected static final String COL_INE_FRONTAL_AVAL     = "ine_frontal_aval";
    protected static final String COL_INE_REVERSO_AVAL     = "ine_reverso_aval";
    protected static final String COL_CURP_AVAL            = "curp_aval";
    protected static final String COL_COMPROBANTE_AVAL     = "comprobante_aval";

    @SerializedName(COL_ID_DOCUMENTO)
    @Expose
    private Integer idDocumento;

    @SerializedName(COL_ID_SOLICITUD)
    @Expose
    private Integer idSolicitud;

    @SerializedName(COL_INE_FRONTAL)
    @Expose
    private String ineFrontal;

    @SerializedName(COL_INE_REVERSO)
    @Expose
    private String ineReverso;

    @SerializedName(COL_CURP)
    @Expose
    private String curp;

    @SerializedName(COL_COMPROBANTE)
    @Expose
    private String comprobante;

    @SerializedName(COL_CODIGO_BARRAS)
    @Expose
    private String codigoBarras;

    @SerializedName(COL_FIRMA_ASESOR)
    @Expose
    private String firmaAsesor;

    @SerializedName(COL_ESTATUS_COMPLETADO)
    @Expose
    private Integer estatusCompletado;

    @SerializedName(COL_INE_SELFIE)
    @Expose
    private String ineSelfie;

    @SerializedName(COL_COMPROBANTE_GARANTIA)
    @Expose
    private String comprobanteGarantia;

    @SerializedName(COL_INE_FRONTAL_AVAL)
    @Expose
    private String ineFrontalAval;

    @SerializedName(COL_INE_REVERSO_AVAL)
    @Expose
    private String ineReversoAval;

    @SerializedName(COL_CURP_AVAL)
    @Expose
    private String curpAval;

    @SerializedName(COL_COMPROBANTE_AVAL)
    @Expose
    private String comprobanteAval;

    public Integer getIdDocumento() {
        return idDocumento;
    }

    public void setIdDocumento(Integer idDocumento) {
        this.idDocumento = idDocumento;
    }

    public Integer getIdSolicitud() {
        return idSolicitud;
    }

    public void setIdSolicitud(Integer idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    public String getIneFrontal() {
        return ineFrontal;
    }

    public void setIneFrontal(String ineFrontal) {
        this.ineFrontal = ineFrontal;
    }

    public String getIneReverso() {
        return ineReverso;
    }

    public void setIneReverso(String ineReverso) {
        this.ineReverso = ineReverso;
    }

    public String getCurp() {
        return curp;
    }

    public void setCurp(String curp) {
        this.curp = curp;
    }

    public String getComprobante() {
        return comprobante;
    }

    public void setComprobante(String comprobante) {
        this.comprobante = comprobante;
    }

    public String getCodigoBarras() {
        return codigoBarras;
    }

    public void setCodigoBarras(String codigoBarras) {
        this.codigoBarras = codigoBarras;
    }

    public String getFirmaAsesor() {
        return firmaAsesor;
    }

    public void setFirmaAsesor(String firmaAsesor) {
        this.firmaAsesor = firmaAsesor;
    }

    public Integer getEstatusCompletado() {
        return estatusCompletado;
    }

    public void setEstatusCompletado(Integer estatusCompletado) {
        this.estatusCompletado = estatusCompletado;
    }

    public String getIneSelfie() {
        return ineSelfie;
    }

    public void setIneSelfie(String ineSelfie) {
        this.ineSelfie = ineSelfie;
    }

    public String getComprobanteGarantia() {
        return comprobanteGarantia;
    }

    public void setComprobanteGarantia(String comprobanteGarantia) {
        this.comprobanteGarantia = comprobanteGarantia;
    }

    public String getIneFrontalAval() {
        return ineFrontalAval;
    }

    public void setIneFrontalAval(String ineFrontalAval) {
        this.ineFrontalAval = ineFrontalAval;
    }

    public String getIneReversoAval() {
        return ineReversoAval;
    }

    public void setIneReversoAval(String ineReversoAval) {
        this.ineReversoAval = ineReversoAval;
    }

    public String getCurpAval() {
        return curpAval;
    }

    public void setCurpAval(String curpAval) {
        this.curpAval = curpAval;
    }

    public String getComprobanteAval() {
        return comprobanteAval;
    }

    public void setComprobanteAval(String comprobanteAval) {
        this.comprobanteAval = comprobanteAval;
    }

    @Override
    public void fill(Cursor row) {
        this.row = row;

        idDocumento         = getInt(COL_ID_DOCUMENTO);
        idSolicitud         = getInt(COL_ID_SOLICITUD);
        ineFrontal          = getString(COL_INE_FRONTAL);
        ineReverso          = getString(COL_INE_REVERSO);
        curp                = getString(COL_CURP);
        comprobante         = getString(COL_COMPROBANTE);
        codigoBarras        = getString(COL_CODIGO_BARRAS);
        firmaAsesor         = getString(COL_FIRMA_ASESOR);
        estatusCompletado   = getInt(COL_ESTATUS_COMPLETADO);
        ineSelfie           = getString(COL_INE_SELFIE);
        comprobanteGarantia = getString(COL_COMPROBANTE_GARANTIA);
        ineFrontalAval      = getString(COL_INE_FRONTAL_AVAL);
        ineReversoAval      = getString(COL_INE_REVERSO_AVAL);
        curpAval            = getString(COL_CURP_AVAL);
        comprobanteAval     = getString(COL_COMPROBANTE_AVAL);
    }
}
