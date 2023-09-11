package com.sidert.sidertmovil.v2.domain.entities;

import java.io.Serializable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "documentos_integrante")
public final class DocumentosIntegrante implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_documento")
    private Long idDocumento;
    @ColumnInfo(name = "id_integrante")
    private Integer idIntegrante;
    @ColumnInfo(name = "ine_frontal")
    private String ineFrontal;
    @ColumnInfo(name = "ine_reverso")
    private String ineReverso;
    @ColumnInfo(name = "curp")
    private String curp;
    @ColumnInfo(name = "comprobante")
    private String comprobante;
    @ColumnInfo(name = "estatus_completado")
    private Integer estatusCompletado;
    @ColumnInfo(name = "ine_selfie")
    private String ineSelfie;


// Getters & Setters

    public Long getIdDocumento(){
        return this.idDocumento;
    }

    public void setIdDocumento(Long idDocumento){
        this.idDocumento = idDocumento;
    }
    public Integer getIdIntegrante(){
        return this.idIntegrante;
    }

    public void setIdIntegrante(Integer idIntegrante){
        this.idIntegrante = idIntegrante;
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

}