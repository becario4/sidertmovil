package com.sidert.sidertmovil.v2.domain.entities;

import java.io.Serializable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tbl_direcciones_auto")
public final class DireccionesAuto implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_direccion")
    private Long idDireccion;
    @ColumnInfo(name = "tipo_direccion")
    private String tipoDireccion;
    @ColumnInfo(name = "latitud")
    private String latitud;
    @ColumnInfo(name = "longitud")
    private String longitud;
    @ColumnInfo(name = "calle")
    private String calle;
    @ColumnInfo(name = "num_exterior")
    private String numExterior;
    @ColumnInfo(name = "num_interior")
    private String numInterior;
    @ColumnInfo(name = "lote")
    private String lote;
    @ColumnInfo(name = "manzana")
    private String manzana;
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
    @ColumnInfo(name = "located_at")
    private String locatedAt;


// Getters & Setters

    public Long getIdDireccion(){
        return this.idDireccion;
    }

    public void setIdDireccion(Long idDireccion){
        this.idDireccion = idDireccion;
    }
    public String getTipoDireccion(){
        return this.tipoDireccion;
    }

    public void setTipoDireccion(String tipoDireccion){
        this.tipoDireccion = tipoDireccion;
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
    public String getCalle(){
        return this.calle;
    }

    public void setCalle(String calle){
        this.calle = calle;
    }
    public String getNumExterior(){
        return this.numExterior;
    }

    public void setNumExterior(String numExterior){
        this.numExterior = numExterior;
    }
    public String getNumInterior(){
        return this.numInterior;
    }

    public void setNumInterior(String numInterior){
        this.numInterior = numInterior;
    }
    public String getLote(){
        return this.lote;
    }

    public void setLote(String lote){
        this.lote = lote;
    }
    public String getManzana(){
        return this.manzana;
    }

    public void setManzana(String manzana){
        this.manzana = manzana;
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
    public String getLocatedAt(){
        return this.locatedAt;
    }

    public void setLocatedAt(String locatedAt){
        this.locatedAt = locatedAt;
    }

}