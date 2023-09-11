package com.sidert.sidertmovil.v2.domain.entities;

import java.io.Serializable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tbl_datos_entrega_cartera")
public final class DatosEntregaCartera implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    private Long id;
    @ColumnInfo(name = "id_tipocartera")
    private Integer idTipocartera;
    @ColumnInfo(name = "tipo_EntregaCartera")
    private String tipoEntregacartera;
    @ColumnInfo(name = "estatus")
    private Integer estatus;


// Getters & Setters

    public Long getId(){
        return this.id;
    }

    public void setId(Long id){
        this.id = id;
    }
    public Integer getIdTipocartera(){
        return this.idTipocartera;
    }

    public void setIdTipocartera(Integer idTipocartera){
        this.idTipocartera = idTipocartera;
    }
    public String getTipoEntregacartera(){
        return this.tipoEntregacartera;
    }

    public void setTipoEntregacartera(String tipoEntregacartera){
        this.tipoEntregacartera = tipoEntregacartera;
    }
    public Integer getEstatus(){
        return this.estatus;
    }

    public void setEstatus(Integer estatus){
        this.estatus = estatus;
    }

}