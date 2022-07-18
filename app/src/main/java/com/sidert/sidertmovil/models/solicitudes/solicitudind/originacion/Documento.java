package com.sidert.sidertmovil.models.solicitudes.solicitudind.originacion;

import android.database.Cursor;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.sidert.sidertmovil.models.BaseModel;
import com.sidert.sidertmovil.models.IFillModel;

import java.io.File;
import java.io.Serializable;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static com.sidert.sidertmovil.utils.Constants.ROOT_PATH;

public class Documento extends BaseModel implements Serializable, IFillModel {
    public static final String TBL                         = "tbl_documentos";
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

    public MultipartBody.Part getFotoIneFrontalMBPart()
    {
        MultipartBody.Part foto_ine_frontal = null;
        File image_ine_frontal = null;

        if(ineFrontal != null && !ineFrontal.equals(""))
            image_ine_frontal = new File(ROOT_PATH + "Documentos/" + ineFrontal);

        if (image_ine_frontal != null) {
            RequestBody imageBody = RequestBody.create(MediaType.parse("image/*"), image_ine_frontal);
            foto_ine_frontal = MultipartBody.Part.createFormData("identificacion_frontal", image_ine_frontal.getName(), imageBody);
        }

        return foto_ine_frontal;
    }

    public MultipartBody.Part getFotoIneReversoMBPart()
    {
        MultipartBody.Part foto_ine_reverso = null;
        File image_ine_reverso = null;

        if(ineReverso != null && !ineReverso.equals(""))
            image_ine_reverso = new File(ROOT_PATH + "Documentos/" + ineReverso);

        if (image_ine_reverso != null) {
            RequestBody imageBody = RequestBody.create(MediaType.parse("image/*"), image_ine_reverso);
            foto_ine_reverso = MultipartBody.Part.createFormData("identificacion_reverso", image_ine_reverso.getName(), imageBody);
        }

        return foto_ine_reverso;
    }

    public MultipartBody.Part getIneSelfieMBPart()
    {
        MultipartBody.Part ine_selfie = null;
        File image_ine_selfie = null;

        if(ineSelfie != null && !ineSelfie.equals(""))
            image_ine_selfie = new File(ROOT_PATH + "Documentos/" + ineSelfie);

        if (image_ine_selfie != null) {
            RequestBody imageBody = RequestBody.create(MediaType.parse("image/*"), image_ine_selfie);
            ine_selfie = MultipartBody.Part.createFormData("identificacion_selfie", image_ine_selfie.getName(), imageBody);
        }

        return ine_selfie;
    }


    public MultipartBody.Part getCurpMBPart()
    {
        MultipartBody.Part foto_curp = null;
        File image_curp = null;

        if(curp != null && !curp.equals(""))
            image_curp = new File(ROOT_PATH + "Documentos/" + curp);

        if (image_curp != null) {
            RequestBody imageBody = RequestBody.create(MediaType.parse("image/*"), image_curp);
            foto_curp = MultipartBody.Part.createFormData("curp", image_curp.getName(), imageBody);
        }

        return foto_curp;
    }

    public MultipartBody.Part getComprobanteMBPart()
    {
        MultipartBody.Part foto_comprobante = null;
        File image_comprobante = null;

        if(comprobante != null && !comprobante.equals(""))
            image_comprobante = new File(ROOT_PATH + "Documentos/" + comprobante);

        if (image_comprobante != null) {
            RequestBody imageBody = RequestBody.create(MediaType.parse("image/*"), image_comprobante);
            foto_comprobante = MultipartBody.Part.createFormData("comprobante_domicilio", image_comprobante.getName(), imageBody);
        }

        return foto_comprobante;
    }

    public MultipartBody.Part getFirmaAsesorMBPart()
    {
        MultipartBody.Part firma_asesor = null;
        File image_firma_asesor = null;

        if(firmaAsesor != null && !firmaAsesor.equals(""))
            image_firma_asesor = new File(ROOT_PATH + "Firma/" + firmaAsesor);

        if (image_firma_asesor != null) {
            RequestBody imageBody = RequestBody.create(MediaType.parse("image/*"), image_firma_asesor);
            firma_asesor = MultipartBody.Part.createFormData("firma_asesor", image_firma_asesor.getName(), imageBody);
        }

        return firma_asesor;
    }

    public MultipartBody.Part getComprobanteGarantiaMBPart()
    {
        MultipartBody.Part comprobante_garantia = null;
        File image_comprobante_garantia = null;

        if(comprobanteGarantia != null && !comprobanteGarantia.equals(""))
            image_comprobante_garantia = new File(ROOT_PATH + "Documentos/" + comprobanteGarantia);

        if (image_comprobante_garantia != null) {
            RequestBody imageBody = RequestBody.create(MediaType.parse("image/*"), image_comprobante_garantia);
            comprobante_garantia = MultipartBody.Part.createFormData("comprobante_garantia", image_comprobante_garantia.getName(), imageBody);
        }

        return comprobante_garantia;
    }

    public MultipartBody.Part getIneFrontalAvalMBPart()
    {
        MultipartBody.Part ine_frontal_aval = null;
        File image_ine_frontal_aval = null;

        if(ineFrontalAval != null && !ineFrontalAval.equals(""))
            image_ine_frontal_aval = new File(ROOT_PATH + "Documentos/" + ineFrontalAval);

        if (image_ine_frontal_aval != null) {
            RequestBody imageBody = RequestBody.create(MediaType.parse("image/*"), image_ine_frontal_aval);
            ine_frontal_aval = MultipartBody.Part.createFormData("identificacion_frontal_aval", image_ine_frontal_aval.getName(), imageBody);
        }

        return ine_frontal_aval;
    }

    public MultipartBody.Part getIneReversoAvalMBPart()
    {
        MultipartBody.Part ine_reverso_aval = null;
        File image_ine_reverso_aval = null;

        if(ineReversoAval != null && !ineReversoAval.equals(""))
            image_ine_reverso_aval = new File(ROOT_PATH + "Documentos/" + ineReversoAval);

        if (image_ine_reverso_aval != null) {
            RequestBody imageBody = RequestBody.create(MediaType.parse("image/*"), image_ine_reverso_aval);
            ine_reverso_aval = MultipartBody.Part.createFormData("identificacion_reverso_aval", image_ine_reverso_aval.getName(), imageBody);
        }

        return ine_reverso_aval;
    }

    public MultipartBody.Part getCurpAvalMBPart()
    {
        MultipartBody.Part curp_aval = null;
        File image_curp_aval = null;

        if(curpAval != null && !curpAval.equals(""))
            image_curp_aval = new File(ROOT_PATH + "Documentos/" + curpAval);

        if (image_curp_aval != null) {
            RequestBody imageBody = RequestBody.create(MediaType.parse("image/*"), image_curp_aval);
            curp_aval = MultipartBody.Part.createFormData("curp_aval", image_curp_aval.getName(), imageBody);
        }

        return curp_aval;
    }

    public MultipartBody.Part getComprobanteDomicilioAvalMBPart()
    {
        MultipartBody.Part comprobante_aval = null;
        File image_comprobante_aval = null;

        if(comprobanteAval != null && !comprobanteAval.equals(""))
            image_comprobante_aval = new File(ROOT_PATH + "Documentos/" + comprobanteAval);

        if (image_comprobante_aval != null) {
            RequestBody imageBody = RequestBody.create(MediaType.parse("image/*"), image_comprobante_aval);
            comprobante_aval = MultipartBody.Part.createFormData("comprobante_domicilio_aval", image_comprobante_aval.getName(), imageBody);
        }

        return comprobante_aval;
    }


}
