package com.sidert.sidertmovil.v2.domain.entities;

import java.io.Serializable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tbl_telefonos_integrante_ren")
public final class TelefonosIntegranteRen implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_telefonico")
    private Long idTelefonico;
    @ColumnInfo(name = "id_integrante")
    private Integer idIntegrante;
    @ColumnInfo(name = "tel_casa")
    private String telCasa;
    @ColumnInfo(name = "tel_celular")
    private String telCelular;
    @ColumnInfo(name = "tel_mensaje")
    private String telMensaje;
    @ColumnInfo(name = "tel_trabajo")
    private String telTrabajo;
    @ColumnInfo(name = "estatus_completado")
    private Integer estatusCompletado;


// Getters & Setters

    public Long getIdTelefonico(){
        return this.idTelefonico;
    }

    public void setIdTelefonico(Long idTelefonico){
        this.idTelefonico = idTelefonico;
    }
    public Integer getIdIntegrante(){
        return this.idIntegrante;
    }

    public void setIdIntegrante(Integer idIntegrante){
        this.idIntegrante = idIntegrante;
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
    public String getTelMensaje(){
        return this.telMensaje;
    }

    public void setTelMensaje(String telMensaje){
        this.telMensaje = telMensaje;
    }
    public String getTelTrabajo(){
        return this.telTrabajo;
    }

    public void setTelTrabajo(String telTrabajo){
        this.telTrabajo = telTrabajo;
    }
    public Integer getEstatusCompletado(){
        return this.estatusCompletado;
    }

    public void setEstatusCompletado(Integer estatusCompletado){
        this.estatusCompletado = estatusCompletado;
    }

}