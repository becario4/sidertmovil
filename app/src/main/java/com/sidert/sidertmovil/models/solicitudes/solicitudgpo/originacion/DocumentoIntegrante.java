package com.sidert.sidertmovil.models.solicitudes.solicitudgpo.originacion;

import static com.sidert.sidertmovil.utils.Constants.ROOT_PATH;

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

public class DocumentoIntegrante extends BaseModel implements Serializable, IFillModel {
    public static final String TBL                       = "documentos_integrante";
    protected static final String COL_ID_DOCUMENTO       = "id_documento";
    protected static final String COL_ID_INTEGRANTE      = "id_integrante";
    protected static final String COL_INE_FRONTAL        = "ine_frontal";
    protected static final String COL_INE_REVERSO        = "ine_reverso";
    protected static final String COL_CURP               = "curp";
    protected static final String COL_COMPROBANTE        = "comprobante";
    protected static final String COL_ESTATUS_COMPLETADO = "estatus_completado";
    protected static final String COL_INE_SELFIE         = "ine_selfie";

    @SerializedName(COL_ID_DOCUMENTO)
    @Expose
    private Integer idDocumento;

    @SerializedName(COL_ID_INTEGRANTE)
    @Expose
    private Integer idIntegrante;

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

    @SerializedName(COL_ESTATUS_COMPLETADO)
    @Expose
    private Integer estatusCompletado;

    @SerializedName(COL_INE_SELFIE)
    @Expose
    private String ineSelfie;

    public Integer getIdDocumento() {
        return idDocumento;
    }

    public void setIdDocumento(Integer idDocumento) {
        this.idDocumento = idDocumento;
    }

    public Integer getIdIntegrante() {
        return idIntegrante;
    }

    public void setIdIntegrante(Integer idIntegrante) {
        this.idIntegrante = idIntegrante;
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

        if(foto_curp == null)
        {
            RequestBody attachmentEmpty = RequestBody.create(MediaType.parse("text/plain"), "");
            foto_curp = MultipartBody.Part.createFormData("curp", "", attachmentEmpty);
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

    @Override
    public void fill(Cursor row) {
        this.row = row;

        idDocumento       = getInt(COL_ID_DOCUMENTO);
        idIntegrante      = getInt(COL_ID_INTEGRANTE);
        ineFrontal        = getString(COL_INE_FRONTAL);
        ineReverso        = getString(COL_INE_REVERSO);
        curp              = getString(COL_CURP);
        comprobante       = getString(COL_COMPROBANTE);
        estatusCompletado = getInt(COL_ESTATUS_COMPLETADO);
        ineSelfie         = getString(COL_INE_SELFIE);
    }
}