package com.sidert.sidertmovil.v2.domain.entities;

import java.io.Serializable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "cat_colonias")
public final class CatColonias implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    private Long id;
    @ColumnInfo(name = "colonia_id")
    private Integer coloniaId;
    @ColumnInfo(name = "colonia_nombre")
    private String coloniaNombre;
    @ColumnInfo(name = "cp")
    private Integer cp;
    @ColumnInfo(name = "municipio_id")
    private Integer municipioId;


// Getters & Setters

    public Long getId(){
        return this.id;
    }

    public void setId(Long id){
        this.id = id;
    }
    public Integer getColoniaId(){
        return this.coloniaId;
    }

    public void setColoniaId(Integer coloniaId){
        this.coloniaId = coloniaId;
    }
    public String getColoniaNombre(){
        return this.coloniaNombre;
    }

    public void setColoniaNombre(String coloniaNombre){
        this.coloniaNombre = coloniaNombre;
    }
    public Integer getCp(){
        return this.cp;
    }

    public void setCp(Integer cp){
        this.cp = cp;
    }
    public Integer getMunicipioId(){
        return this.municipioId;
    }

    public void setMunicipioId(Integer municipioId){
        this.municipioId = municipioId;
    }

}