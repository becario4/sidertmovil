package com.sidert.sidertmovil.v2.domain.entities;

import java.io.Serializable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tbl_conyuge_ind_ren")
public final class ConyugeIndRen implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_conyuge")
    private Long idConyuge;
    @ColumnInfo(name = "id_solicitud")
    private Integer idSolicitud;
    @ColumnInfo(name = "nombre")
    private String nombre;
    @ColumnInfo(name = "paterno")
    private String paterno;
    @ColumnInfo(name = "materno")
    private String materno;
    @ColumnInfo(name = "nacionalidad")
    private String nacionalidad;
    @ColumnInfo(name = "ocupacion")
    private String ocupacion;
    @ColumnInfo(name = "direccion_id")
    private String direccionId;
    @ColumnInfo(name = "ing_mensual")
    private String ingMensual;
    @ColumnInfo(name = "gasto_mensual")
    private String gastoMensual;
    @ColumnInfo(name = "tel_casa")
    private String telCasa;
    @ColumnInfo(name = "tel_celular")
    private String telCelular;
    @ColumnInfo(name = "estatus_completado")
    private Integer estatusCompletado;


// Getters & Setters

    public Long getIdConyuge(){
        return this.idConyuge;
    }

    public void setIdConyuge(Long idConyuge){
        this.idConyuge = idConyuge;
    }
    public Integer getIdSolicitud(){
        return this.idSolicitud;
    }

    public void setIdSolicitud(Integer idSolicitud){
        this.idSolicitud = idSolicitud;
    }
    public String getNombre(){
        return this.nombre;
    }

    public void setNombre(String nombre){
        this.nombre = nombre;
    }
    public String getPaterno(){
        return this.paterno;
    }

    public void setPaterno(String paterno){
        this.paterno = paterno;
    }
    public String getMaterno(){
        return this.materno;
    }

    public void setMaterno(String materno){
        this.materno = materno;
    }
    public String getNacionalidad(){
        return this.nacionalidad;
    }

    public void setNacionalidad(String nacionalidad){
        this.nacionalidad = nacionalidad;
    }
    public String getOcupacion(){
        return this.ocupacion;
    }

    public void setOcupacion(String ocupacion){
        this.ocupacion = ocupacion;
    }
    public String getDireccionId(){
        return this.direccionId;
    }

    public void setDireccionId(String direccionId){
        this.direccionId = direccionId;
    }
    public String getIngMensual(){
        return this.ingMensual;
    }

    public void setIngMensual(String ingMensual){
        this.ingMensual = ingMensual;
    }
    public String getGastoMensual(){
        return this.gastoMensual;
    }

    public void setGastoMensual(String gastoMensual){
        this.gastoMensual = gastoMensual;
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
    public Integer getEstatusCompletado(){
        return this.estatusCompletado;
    }

    public void setEstatusCompletado(Integer estatusCompletado){
        this.estatusCompletado = estatusCompletado;
    }

}