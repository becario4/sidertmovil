package com.sidert.sidertmovil.v2.domain.entities;

import java.io.Serializable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tbl_telefonos_cliente")
public final class TelefonosCliente implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    private Long id;
    @ColumnInfo(name = "id_prestamo")
    private String idPrestamo;
    @ColumnInfo(name = "tel_casa")
    private String telCasa;
    @ColumnInfo(name = "tel_celular")
    private String telCelular;


// Getters & Setters

    public Long getId(){
        return this.id;
    }

    public void setId(Long id){
        this.id = id;
    }
    public String getIdPrestamo(){
        return this.idPrestamo;
    }

    public void setIdPrestamo(String idPrestamo){
        this.idPrestamo = idPrestamo;
    }
    public String getTelCasa(){
        return this.telCasa;
    }

    public void setTelCasa(String telCasa){
        this.telCasa = telCasa;
    }
    public String getTelCelular(){
        return this.telCelular;
    }

    public void setTelCelular(String telCelular){
        this.telCelular = telCelular;
    }

}