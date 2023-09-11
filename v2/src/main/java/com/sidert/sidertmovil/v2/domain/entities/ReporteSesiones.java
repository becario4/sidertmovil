package com.sidert.sidertmovil.v2.domain.entities;

import java.io.Serializable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tbl_reporte_sesiones")
public final class ReporteSesiones implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    private Long id;
    @ColumnInfo(name = "id")
    private Integer remoteId;
    @ColumnInfo(name = "serie_id")
    private String serieId;
    @ColumnInfo(name = "nombre_asesor")
    private String nombreAsesor;
    @ColumnInfo(name = "usuario")
    private String usuario;
    @ColumnInfo(name = "sucursal")
    private String sucursal;
    @ColumnInfo(name = "sucursalid")
    private String sucursalid;
    @ColumnInfo(name = "horariologin")
    private String horariologin;
    @ColumnInfo(name = "nivelbateria")
    private String nivelbateria;
    @ColumnInfo(name = "versionapp")
    private String versionapp;
    @ColumnInfo(name = "primeragestion")
    private String primeragestion;
    @ColumnInfo(name = "ultimagestion")
    private String ultimagestion;
    @ColumnInfo(name = "totalgestiones")
    private String totalgestiones;


// Getters & Setters

    public Long getId(){
        return this.id;
    }

    public void setId(Long id){
        this.id = id;
    }
    public Integer getRemoteId(){
        return this.remoteId;
    }

    public void setRemoteId(Integer remoteId){
        this.remoteId = remoteId;
    }
    public String getSerieId(){
        return this.serieId;
    }

    public void setSerieId(String serieId){
        this.serieId = serieId;
    }
    public String getNombreAsesor(){
        return this.nombreAsesor;
    }

    public void setNombreAsesor(String nombreAsesor){
        this.nombreAsesor = nombreAsesor;
    }
    public String getUsuario(){
        return this.usuario;
    }

    public void setUsuario(String usuario){
        this.usuario = usuario;
    }
    public String getSucursal(){
        return this.sucursal;
    }

    public void setSucursal(String sucursal){
        this.sucursal = sucursal;
    }
    public String getSucursalid(){
        return this.sucursalid;
    }

    public void setSucursalid(String sucursalid){
        this.sucursalid = sucursalid;
    }
    public String getHorariologin(){
        return this.horariologin;
    }

    public void setHorariologin(String horariologin){
        this.horariologin = horariologin;
    }
    public String getNivelbateria(){
        return this.nivelbateria;
    }

    public void setNivelbateria(String nivelbateria){
        this.nivelbateria = nivelbateria;
    }
    public String getVersionapp(){
        return this.versionapp;
    }

    public void setVersionapp(String versionapp){
        this.versionapp = versionapp;
    }
    public String getPrimeragestion(){
        return this.primeragestion;
    }

    public void setPrimeragestion(String primeragestion){
        this.primeragestion = primeragestion;
    }
    public String getUltimagestion(){
        return this.ultimagestion;
    }

    public void setUltimagestion(String ultimagestion){
        this.ultimagestion = ultimagestion;
    }
    public String getTotalgestiones(){
        return this.totalgestiones;
    }

    public void setTotalgestiones(String totalgestiones){
        this.totalgestiones = totalgestiones;
    }

}