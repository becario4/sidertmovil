package com.sidert.sidertmovil.v2.domain.entities;

import java.io.Serializable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "sincronizado_t")
public final class Sincronizado implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    private Long id;
    @ColumnInfo(name = "serie_id")
    private String serieId;
    @ColumnInfo(name = "timestamp")
    private String timestamp;


// Getters & Setters

    public Long getId(){
        return this.id;
    }

    public void setId(Long id){
        this.id = id;
    }
    public String getSerieId(){
        return this.serieId;
    }

    public void setSerieId(String serieId){
        this.serieId = serieId;
    }
    public String getTimestamp(){
        return this.timestamp;
    }

    public void setTimestamp(String timestamp){
        this.timestamp = timestamp;
    }

}