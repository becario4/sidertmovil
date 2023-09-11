package com.sidert.sidertmovil.v2.domain.entities;

import java.io.Serializable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tbl_catalogo_campanas")
public final class CatalogoCampanas implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    private Long id;
    @ColumnInfo(name = "id_campana")
    private Integer idCampana;
    @ColumnInfo(name = "tipo_campana")
    private String tipoCampana;
    @ColumnInfo(name = "estatus")
    private Integer estatus;


// Getters & Setters

    public Long getId(){
        return this.id;
    }

    public void setId(Long id){
        this.id = id;
    }
    public Integer getIdCampana(){
        return this.idCampana;
    }

    public void setIdCampana(Integer idCampana){
        this.idCampana = idCampana;
    }
    public String getTipoCampana(){
        return this.tipoCampana;
    }

    public void setTipoCampana(String tipoCampana){
        this.tipoCampana = tipoCampana;
    }
    public Integer getEstatus(){
        return this.estatus;
    }

    public void setEstatus(Integer estatus){
        this.estatus = estatus;
    }

}