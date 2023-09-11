package com.sidert.sidertmovil.v2.domain.entities;

import java.io.Serializable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tbl_documentos")
public final class Documentos implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_documento")
    private Long idDocumento;
    @ColumnInfo(name = "id_solicitud")
    private Integer idSolicitud;
    @ColumnInfo(name = "ine_frontal")
    private String ineFrontal;
    @ColumnInfo(name = "ine_reverso")
    private String ineReverso;
    @ColumnInfo(name = "curp")
    private String curp;
    @ColumnInfo(name = "comprobante")
    private String comprobante;
    @ColumnInfo(name = "codigo_barras")
    private String codigoBarras;
    @ColumnInfo(name = "firma_asesor")
    private String firmaAsesor;
    @ColumnInfo(name = "estatus_completado")
    private Integer estatusCompletado;
    @ColumnInfo(name = "ine_selfie")
    private String ineSelfie;
    @ColumnInfo(name = "comprobante_garantia")
    private String comprobanteGarantia;
    @ColumnInfo(name = "ine_frontal_aval")
    private String ineFrontalAval;
    @ColumnInfo(name = "ine_reverso_aval")
    private String ineReversoAval;
    @ColumnInfo(name = "curp_aval")
    private String curpAval;
    @ColumnInfo(name = "comprobante_aval")
    private String comprobanteAval;


// Getters & Setters

    public Long getIdDocumento(){
        return this.idDocumento;
    }

    public void setIdDocumento(Long idDocumento){
        this.idDocumento = idDocumento;
    }
    public Integer getIdSolicitud(){
        return this.idSolicitud;
    }

    public void setIdSolicitud(Integer idSolicitud){
        this.idSolicitud = idSolicitud;
    }
    public String getIneFrontal(){
        return this.ineFrontal;
    }

    public void setIneFrontal(String ineFrontal){
        this.ineFrontal = ineFrontal;
    }
    public String getIneReverso(){
        return this.ineReverso;
    }

    public void setIneReverso(String ineReverso){
        this.ineReverso = ineReverso;
    }
    public String getCurp(){
        return this.curp;
    }

    public void setCurp(String curp){
        this.curp = curp;
    }
    public String getComprobante(){
        return this.comprobante;
    }

    public void setComprobante(String comprobante){
        this.comprobante = comprobante;
    }
    public String getCodigoBarras(){
        return this.codigoBarras;
    }

    public void setCodigoBarras(String codigoBarras){
        this.codigoBarras = codigoBarras;
    }
    public String getFirmaAsesor(){
        return this.firmaAsesor;
    }

    public void setFirmaAsesor(String firmaAsesor){
        this.firmaAsesor = firmaAsesor;
    }
    public Integer getEstatusCompletado(){
        return this.estatusCompletado;
    }

    public void setEstatusCompletado(Integer estatusCompletado){
        this.estatusCompletado = estatusCompletado;
    }
    public String getIneSelfie(){
        return this.ineSelfie;
    }

    public void setIneSelfie(String ineSelfie){
        this.ineSelfie = ineSelfie;
    }
    public String getComprobanteGarantia(){
        return this.comprobanteGarantia;
    }

    public void setComprobanteGarantia(String comprobanteGarantia){
        this.comprobanteGarantia = comprobanteGarantia;
    }
    public String getIneFrontalAval(){
        return this.ineFrontalAval;
    }

    public void setIneFrontalAval(String ineFrontalAval){
        this.ineFrontalAval = ineFrontalAval;
    }
    public String getIneReversoAval(){
        return this.ineReversoAval;
    }

    public void setIneReversoAval(String ineReversoAval){
        this.ineReversoAval = ineReversoAval;
    }
    public String getCurpAval(){
        return this.curpAval;
    }

    public void setCurpAval(String curpAval){
        this.curpAval = curpAval;
    }
    public String getComprobanteAval(){
        return this.comprobanteAval;
    }

    public void setComprobanteAval(String comprobanteAval){
        this.comprobanteAval = comprobanteAval;
    }

}