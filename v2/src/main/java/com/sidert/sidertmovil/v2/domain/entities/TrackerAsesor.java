package com.sidert.sidertmovil.v2.domain.entities;

import java.io.Serializable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tbl_tracker_asesor_t")
public final class TrackerAsesor implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    private Long id;
    @ColumnInfo(name = "asesor_id")
    private String asesorId;
    @ColumnInfo(name = "serie_id")
    private String serieId;
    @ColumnInfo(name = "nombre")
    private String nombre;
    @ColumnInfo(name = "latitud")
    private String latitud;
    @ColumnInfo(name = "longitud")
    private String longitud;
    @ColumnInfo(name = "bateria")
    private String bateria;
    @ColumnInfo(name = "created_at")
    private String createdAt;
    @ColumnInfo(name = "sended_at")
    private String sendedAt;
    @ColumnInfo(name = "estatus")
    private String estatus;


// Getters & Setters

    public Long getId(){
        return this.id;
    }

    public void setId(Long id){
        this.id = id;
    }
    public String getAsesorId(){
        return this.asesorId;
    }

    public void setAsesorId(String asesorId){
        this.asesorId = asesorId;
    }
    public String getSerieId(){
        return this.serieId;
    }

    public void setSerieId(String serieId){
        this.serieId = serieId;
    }
    public String getNombre(){
        return this.nombre;
    }

    public void setNombre(String nombre){
        this.nombre = nombre;
    }
    public String getLatitud(){
        return this.latitud;
    }

    public void setLatitud(String latitud){
        this.latitud = latitud;
    }
    public String getLongitud(){
        return this.longitud;
    }

    public void setLongitud(String longitud){
        this.longitud = longitud;
    }
    public String getBateria(){
        return this.bateria;
    }

    public void setBateria(String bateria){
        this.bateria = bateria;
    }
    public String getCreatedAt(){
        return this.createdAt;
    }

    public void setCreatedAt(String createdAt){
        this.createdAt = createdAt;
    }
    public String getSendedAt(){
        return this.sendedAt;
    }

    public void setSendedAt(String sendedAt){
        this.sendedAt = sendedAt;
    }
    public String getEstatus(){
        return this.estatus;
    }

    public void setEstatus(String estatus){
        this.estatus = estatus;
    }

}