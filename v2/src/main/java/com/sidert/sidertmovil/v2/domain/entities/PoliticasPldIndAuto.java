package com.sidert.sidertmovil.v2.domain.entities;

import java.io.Serializable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tbl_politicas_pld_ind_auto")
public final class PoliticasPldIndAuto implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private Long remoteId;
    @ColumnInfo(name = "id_solicitud")
    private Integer idSolicitud;
    @ColumnInfo(name = "propietario_real")
    private Integer propietarioReal;
    @ColumnInfo(name = "proveedor_recursos")
    private Integer proveedorRecursos;
    @ColumnInfo(name = "persona_politica")
    private Integer personaPolitica;
    @ColumnInfo(name = "estatus_completado")
    private Integer estatusCompletado;


// Getters & Setters

    public Long getRemoteId(){
        return this.remoteId;
    }

    public void setRemoteId(Long remoteId){
        this.remoteId = remoteId;
    }
    public Integer getIdSolicitud(){
        return this.idSolicitud;
    }

    public void setIdSolicitud(Integer idSolicitud){
        this.idSolicitud = idSolicitud;
    }
    public Integer getPropietarioReal(){
        return this.propietarioReal;
    }

    public void setPropietarioReal(Integer propietarioReal){
        this.propietarioReal = propietarioReal;
    }
    public Integer getProveedorRecursos(){
        return this.proveedorRecursos;
    }

    public void setProveedorRecursos(Integer proveedorRecursos){
        this.proveedorRecursos = proveedorRecursos;
    }
    public Integer getPersonaPolitica(){
        return this.personaPolitica;
    }

    public void setPersonaPolitica(Integer personaPolitica){
        this.personaPolitica = personaPolitica;
    }
    public Integer getEstatusCompletado(){
        return this.estatusCompletado;
    }

    public void setEstatusCompletado(Integer estatusCompletado){
        this.estatusCompletado = estatusCompletado;
    }

}