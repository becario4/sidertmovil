package com.sidert.sidertmovil.v2.domain.entities;

import java.io.Serializable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "fichas_t")
public final class Fichas implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    private Long id;
    @ColumnInfo(name = "asesor_id")
    private String asesorId;
    @ColumnInfo(name = "external_id")
    private String externalId;
    @ColumnInfo(name = "form")
    private String form;
    @ColumnInfo(name = "fecha_asig")
    private String fechaAsig;
    @ColumnInfo(name = "dia_semana")
    private String diaSemana;
    @ColumnInfo(name = "fecha_pago")
    private String fechaPago;
    @ColumnInfo(name = "prestamo_obj")
    private String prestamoObj;
    @ColumnInfo(name = "nombre")
    private String nombre;
    @ColumnInfo(name = "clave")
    private String clave;
    @ColumnInfo(name = "datos_obj")
    private String datosObj;
    @ColumnInfo(name = "aval_obj")
    private String avalObj;
    @ColumnInfo(name = "presidenta_obj")
    private String presidentaObj;
    @ColumnInfo(name = "tesorera_obj")
    private String tesoreraObj;
    @ColumnInfo(name = "secretaria_obj")
    private String secretariaObj;
    @ColumnInfo(name = "reporte_omega_obj")
    private String reporteOmegaObj;
    @ColumnInfo(name = "tabla_pagos_obj")
    private String tablaPagosObj;
    @ColumnInfo(name = "impresion")
    private Integer impresion;
    @ColumnInfo(name = "fecha_ini")
    private String fechaIni;
    @ColumnInfo(name = "fecha_ter")
    private String fechaTer;
    @ColumnInfo(name = "fecha_env")
    private String fechaEnv;
    @ColumnInfo(name = "fecha_con")
    private String fechaCon;
    @ColumnInfo(name = "respuesta")
    private String respuesta;
    @ColumnInfo(name = "status")
    private Integer status;
    @ColumnInfo(name = "timestamp")
    private String timestamp;
    @ColumnInfo(name = "is_ruta",defaultValue="0")
    private Integer isRuta;


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
    public String getExternalId(){
        return this.externalId;
    }

    public void setExternalId(String externalId){
        this.externalId = externalId;
    }
    public String getForm(){
        return this.form;
    }

    public void setForm(String form){
        this.form = form;
    }
    public String getFechaAsig(){
        return this.fechaAsig;
    }

    public void setFechaAsig(String fechaAsig){
        this.fechaAsig = fechaAsig;
    }
    public String getDiaSemana(){
        return this.diaSemana;
    }

    public void setDiaSemana(String diaSemana){
        this.diaSemana = diaSemana;
    }
    public String getFechaPago(){
        return this.fechaPago;
    }

    public void setFechaPago(String fechaPago){
        this.fechaPago = fechaPago;
    }
    public String getPrestamoObj(){
        return this.prestamoObj;
    }

    public void setPrestamoObj(String prestamoObj){
        this.prestamoObj = prestamoObj;
    }
    public String getNombre(){
        return this.nombre;
    }

    public void setNombre(String nombre){
        this.nombre = nombre;
    }
    public String getClave(){
        return this.clave;
    }

    public void setClave(String clave){
        this.clave = clave;
    }
    public String getDatosObj(){
        return this.datosObj;
    }

    public void setDatosObj(String datosObj){
        this.datosObj = datosObj;
    }
    public String getAvalObj(){
        return this.avalObj;
    }

    public void setAvalObj(String avalObj){
        this.avalObj = avalObj;
    }
    public String getPresidentaObj(){
        return this.presidentaObj;
    }

    public void setPresidentaObj(String presidentaObj){
        this.presidentaObj = presidentaObj;
    }
    public String getTesoreraObj(){
        return this.tesoreraObj;
    }

    public void setTesoreraObj(String tesoreraObj){
        this.tesoreraObj = tesoreraObj;
    }
    public String getSecretariaObj(){
        return this.secretariaObj;
    }

    public void setSecretariaObj(String secretariaObj){
        this.secretariaObj = secretariaObj;
    }
    public String getReporteOmegaObj(){
        return this.reporteOmegaObj;
    }

    public void setReporteOmegaObj(String reporteOmegaObj){
        this.reporteOmegaObj = reporteOmegaObj;
    }
    public String getTablaPagosObj(){
        return this.tablaPagosObj;
    }

    public void setTablaPagosObj(String tablaPagosObj){
        this.tablaPagosObj = tablaPagosObj;
    }
    public Integer getImpresion(){
        return this.impresion;
    }

    public void setImpresion(Integer impresion){
        this.impresion = impresion;
    }
    public String getFechaIni(){
        return this.fechaIni;
    }

    public void setFechaIni(String fechaIni){
        this.fechaIni = fechaIni;
    }
    public String getFechaTer(){
        return this.fechaTer;
    }

    public void setFechaTer(String fechaTer){
        this.fechaTer = fechaTer;
    }
    public String getFechaEnv(){
        return this.fechaEnv;
    }

    public void setFechaEnv(String fechaEnv){
        this.fechaEnv = fechaEnv;
    }
    public String getFechaCon(){
        return this.fechaCon;
    }

    public void setFechaCon(String fechaCon){
        this.fechaCon = fechaCon;
    }
    public String getRespuesta(){
        return this.respuesta;
    }

    public void setRespuesta(String respuesta){
        this.respuesta = respuesta;
    }
    public Integer getStatus(){
        return this.status;
    }

    public void setStatus(Integer status){
        this.status = status;
    }
    public String getTimestamp(){
        return this.timestamp;
    }

    public void setTimestamp(String timestamp){
        this.timestamp = timestamp;
    }
    public Integer getIsRuta(){
        return this.isRuta;
    }

    public void setIsRuta(Integer isRuta){
        this.isRuta = isRuta;
    }

}