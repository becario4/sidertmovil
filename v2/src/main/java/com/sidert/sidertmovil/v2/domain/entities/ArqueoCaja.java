package com.sidert.sidertmovil.v2.domain.entities;

import java.io.Serializable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tbl_arqueo_caja_t")
public final class ArqueoCaja implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    private Long id;
    @ColumnInfo(name = "id_gestion")
    private String idGestion;
    @ColumnInfo(name = "mil")
    private String mil;
    @ColumnInfo(name = "quinientos")
    private String quinientos;
    @ColumnInfo(name = "doscientos")
    private String doscientos;
    @ColumnInfo(name = "cien")
    private String cien;
    @ColumnInfo(name = "cincuenta")
    private String cincuenta;
    @ColumnInfo(name = "veinte")
    private String veinte;
    @ColumnInfo(name = "diez")
    private String diez;
    @ColumnInfo(name = "cinco")
    private String cinco;
    @ColumnInfo(name = "dos")
    private String dos;
    @ColumnInfo(name = "peso")
    private String peso;
    @ColumnInfo(name = "c_cincuenta")
    private String cCincuenta;
    @ColumnInfo(name = "c_veinte")
    private String cVeinte;
    @ColumnInfo(name = "c_diez")
    private String cDiez;


// Getters & Setters

    public Long getId(){
        return this.id;
    }

    public void setId(Long id){
        this.id = id;
    }
    public String getIdGestion(){
        return this.idGestion;
    }

    public void setIdGestion(String idGestion){
        this.idGestion = idGestion;
    }
    public String getMil(){
        return this.mil;
    }

    public void setMil(String mil){
        this.mil = mil;
    }
    public String getQuinientos(){
        return this.quinientos;
    }

    public void setQuinientos(String quinientos){
        this.quinientos = quinientos;
    }
    public String getDoscientos(){
        return this.doscientos;
    }

    public void setDoscientos(String doscientos){
        this.doscientos = doscientos;
    }
    public String getCien(){
        return this.cien;
    }

    public void setCien(String cien){
        this.cien = cien;
    }
    public String getCincuenta(){
        return this.cincuenta;
    }

    public void setCincuenta(String cincuenta){
        this.cincuenta = cincuenta;
    }
    public String getVeinte(){
        return this.veinte;
    }

    public void setVeinte(String veinte){
        this.veinte = veinte;
    }
    public String getDiez(){
        return this.diez;
    }

    public void setDiez(String diez){
        this.diez = diez;
    }
    public String getCinco(){
        return this.cinco;
    }

    public void setCinco(String cinco){
        this.cinco = cinco;
    }
    public String getDos(){
        return this.dos;
    }

    public void setDos(String dos){
        this.dos = dos;
    }
    public String getPeso(){
        return this.peso;
    }

    public void setPeso(String peso){
        this.peso = peso;
    }
    public String getCCincuenta(){
        return this.cCincuenta;
    }

    public void setCCincuenta(String cCincuenta){
        this.cCincuenta = cCincuenta;
    }
    public String getCVeinte(){
        return this.cVeinte;
    }

    public void setCVeinte(String cVeinte){
        this.cVeinte = cVeinte;
    }
    public String getCDiez(){
        return this.cDiez;
    }

    public void setCDiez(String cDiez){
        this.cDiez = cDiez;
    }

}