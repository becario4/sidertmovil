package com.sidert.sidertmovil.v2.domain.entities;

import java.io.Serializable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tbl_conyuge_integrante_ren")
public final class ConyugeIntegranteRen implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_conyuge")
    private Long idConyuge;
    @ColumnInfo(name = "id_integrante")
    private Integer idIntegrante;
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
    @ColumnInfo(name = "calle")
    private String calle;
    @ColumnInfo(name = "no_exterior")
    private String noExterior;
    @ColumnInfo(name = "no_interior")
    private String noInterior;
    @ColumnInfo(name = "manzana")
    private String manzana;
    @ColumnInfo(name = "lote")
    private String lote;
    @ColumnInfo(name = "cp")
    private String cp;
    @ColumnInfo(name = "colonia")
    private String colonia;
    @ColumnInfo(name = "ciudad")
    private String ciudad;
    @ColumnInfo(name = "localidad")
    private String localidad;
    @ColumnInfo(name = "municipio")
    private String municipio;
    @ColumnInfo(name = "estado")
    private String estado;
    @ColumnInfo(name = "ingresos_mensual")
    private String ingresosMensual;
    @ColumnInfo(name = "gasto_mensual")
    private String gastoMensual;
    @ColumnInfo(name = "tel_celular")
    private String telCelular;
    @ColumnInfo(name = "tel_trabajo")
    private String telTrabajo;
    @ColumnInfo(name = "estatus_completado")
    private Integer estatusCompletado;


// Getters & Setters

    public Long getIdConyuge(){
        return this.idConyuge;
    }

    public void setIdConyuge(Long idConyuge){
        this.idConyuge = idConyuge;
    }
    public Integer getIdIntegrante(){
        return this.idIntegrante;
    }

    public void setIdIntegrante(Integer idIntegrante){
        this.idIntegrante = idIntegrante;
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
    public String getCalle(){
        return this.calle;
    }

    public void setCalle(String calle){
        this.calle = calle;
    }
    public String getNoExterior(){
        return this.noExterior;
    }

    public void setNoExterior(String noExterior){
        this.noExterior = noExterior;
    }
    public String getNoInterior(){
        return this.noInterior;
    }

    public void setNoInterior(String noInterior){
        this.noInterior = noInterior;
    }
    public String getManzana(){
        return this.manzana;
    }

    public void setManzana(String manzana){
        this.manzana = manzana;
    }
    public String getLote(){
        return this.lote;
    }

    public void setLote(String lote){
        this.lote = lote;
    }
    public String getCp(){
        return this.cp;
    }

    public void setCp(String cp){
        this.cp = cp;
    }
    public String getColonia(){
        return this.colonia;
    }

    public void setColonia(String colonia){
        this.colonia = colonia;
    }
    public String getCiudad(){
        return this.ciudad;
    }

    public void setCiudad(String ciudad){
        this.ciudad = ciudad;
    }
    public String getLocalidad(){
        return this.localidad;
    }

    public void setLocalidad(String localidad){
        this.localidad = localidad;
    }
    public String getMunicipio(){
        return this.municipio;
    }

    public void setMunicipio(String municipio){
        this.municipio = municipio;
    }
    public String getEstado(){
        return this.estado;
    }

    public void setEstado(String estado){
        this.estado = estado;
    }
    public String getIngresosMensual(){
        return this.ingresosMensual;
    }

    public void setIngresosMensual(String ingresosMensual){
        this.ingresosMensual = ingresosMensual;
    }
    public String getGastoMensual(){
        return this.gastoMensual;
    }

    public void setGastoMensual(String gastoMensual){
        this.gastoMensual = gastoMensual;
    }
    public String getTelCelular(){
        return this.telCelular;
    }

    public void setTelCelular(String telCelular){
        this.telCelular = telCelular;
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