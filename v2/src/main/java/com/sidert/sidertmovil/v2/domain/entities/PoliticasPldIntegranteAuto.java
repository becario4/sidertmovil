package com.sidert.sidertmovil.v2.domain.entities;

import java.io.Serializable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tbl_politicas_pld_integrante_auto")
public final class PoliticasPldIntegranteAuto implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_politica")
    private Long idPolitica;
    @ColumnInfo(name = "id_integrante")
    private Integer idIntegrante;
    @ColumnInfo(name = "propietario_real")
    private Integer propietarioReal;
    @ColumnInfo(name = "proveedor_recursos")
    private Integer proveedorRecursos;
    @ColumnInfo(name = "persona_politica")
    private Integer personaPolitica;
    @ColumnInfo(name = "estatus_completado")
    private Integer estatusCompletado;


// Getters & Setters

    public Long getIdPolitica(){
        return this.idPolitica;
    }

    public void setIdPolitica(Long idPolitica){
        this.idPolitica = idPolitica;
    }
    public Integer getIdIntegrante(){
        return this.idIntegrante;
    }

    public void setIdIntegrante(Integer idIntegrante){
        this.idIntegrante = idIntegrante;
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