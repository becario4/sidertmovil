package com.sidert.sidertmovil.v2.domain.entities;

import java.io.Serializable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tbl_domicilio_integrante")
public final class DomicilioIntegrante implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_domicilio")
    private Long idDomicilio;
    @ColumnInfo(name = "id_integrante")
    private Integer idIntegrante;
    @ColumnInfo(name = "latitud")
    private String latitud;
    @ColumnInfo(name = "longitud")
    private String longitud;
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
    @ColumnInfo(name = "tipo_vivienda")
    private String tipoVivienda;
    @ColumnInfo(name = "parentesco")
    private String parentesco;
    @ColumnInfo(name = "otro_tipo_vivienda")
    private String otroTipoVivienda;
    @ColumnInfo(name = "tiempo_vivir_sitio")
    private String tiempoVivirSitio;
    @ColumnInfo(name = "foto_fachada")
    private String fotoFachada;
    @ColumnInfo(name = "ref_domiciliaria")
    private String refDomiciliaria;
    @ColumnInfo(name = "estatus_completado")
    private Integer estatusCompletado;
    @ColumnInfo(name = "dependientes")
    private String dependientes;
    @ColumnInfo(name = "located_at")
    private String locatedAt;


// Getters & Setters

    public Long getIdDomicilio(){
        return this.idDomicilio;
    }

    public void setIdDomicilio(Long idDomicilio){
        this.idDomicilio = idDomicilio;
    }
    public Integer getIdIntegrante(){
        return this.idIntegrante;
    }

    public void setIdIntegrante(Integer idIntegrante){
        this.idIntegrante = idIntegrante;
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
    public String getTipoVivienda(){
        return this.tipoVivienda;
    }

    public void setTipoVivienda(String tipoVivienda){
        this.tipoVivienda = tipoVivienda;
    }
    public String getParentesco(){
        return this.parentesco;
    }

    public void setParentesco(String parentesco){
        this.parentesco = parentesco;
    }
    public String getOtroTipoVivienda(){
        return this.otroTipoVivienda;
    }

    public void setOtroTipoVivienda(String otroTipoVivienda){
        this.otroTipoVivienda = otroTipoVivienda;
    }
    public String getTiempoVivirSitio(){
        return this.tiempoVivirSitio;
    }

    public void setTiempoVivirSitio(String tiempoVivirSitio){
        this.tiempoVivirSitio = tiempoVivirSitio;
    }
    public String getFotoFachada(){
        return this.fotoFachada;
    }

    public void setFotoFachada(String fotoFachada){
        this.fotoFachada = fotoFachada;
    }
    public String getRefDomiciliaria(){
        return this.refDomiciliaria;
    }

    public void setRefDomiciliaria(String refDomiciliaria){
        this.refDomiciliaria = refDomiciliaria;
    }
    public Integer getEstatusCompletado(){
        return this.estatusCompletado;
    }

    public void setEstatusCompletado(Integer estatusCompletado){
        this.estatusCompletado = estatusCompletado;
    }
    public String getDependientes(){
        return this.dependientes;
    }

    public void setDependientes(String dependientes){
        this.dependientes = dependientes;
    }
    public String getLocatedAt(){
        return this.locatedAt;
    }

    public void setLocatedAt(String locatedAt){
        this.locatedAt = locatedAt;
    }

}