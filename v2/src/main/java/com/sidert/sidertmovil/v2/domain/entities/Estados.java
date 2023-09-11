package com.sidert.sidertmovil.v2.domain.entities;

import java.io.Serializable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "estados")
public final class Estados implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    private Long id;
    @ColumnInfo(name = "estado_id")
    private Integer estadoId;
    @ColumnInfo(name = "estado_nombre")
    private String estadoNombre;
    @ColumnInfo(name = "pais_id")
    private Integer paisId;


// Getters & Setters

    public Long getId(){
        return this.id;
    }

    public void setId(Long id){
        this.id = id;
    }
    public Integer getEstadoId(){
        return this.estadoId;
    }

    public void setEstadoId(Integer estadoId){
        this.estadoId = estadoId;
    }
    public String getEstadoNombre(){
        return this.estadoNombre;
    }

    public void setEstadoNombre(String estadoNombre){
        this.estadoNombre = estadoNombre;
    }
    public Integer getPaisId(){
        return this.paisId;
    }

    public void setPaisId(Integer paisId){
        this.paisId = paisId;
    }

}