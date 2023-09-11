package com.sidert.sidertmovil.v2.domain.entities;

import java.io.Serializable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tbl_centro_costo")
public final class CentroCosto implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private Long remoteId;
    @ColumnInfo(name = "centroCosto")
    private Integer centrocosto;


// Getters & Setters

    public Long getRemoteId(){
        return this.remoteId;
    }

    public void setRemoteId(Long remoteId){
        this.remoteId = remoteId;
    }
    public Integer getCentrocosto(){
        return this.centrocosto;
    }

    public void setCentrocosto(Integer centrocosto){
        this.centrocosto = centrocosto;
    }

}