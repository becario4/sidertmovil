package com.sidert.sidertmovil.models.solicitudes.solicitudind.renovacion;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DireccionRen implements Serializable {
    public final static String TBL = "tbl_direccion_ren";

    @SerializedName("id_direccion")
    @Expose
    private Integer idDireccion;

    @SerializedName("tipo_direccion")
    @Expose
    private String tipoDireccion;

    @SerializedName("latitud")
    @Expose
    private String latitud;

    @SerializedName("longitud")
    @Expose
    private String longitud;

    @SerializedName("calle")
    @Expose
    private String calle;

    @SerializedName("num_exterior")
    @Expose
    private String numExterior;

    @SerializedName("num_interior")
    @Expose
    private String numInterior;

    @SerializedName("lote")
    @Expose
    private String lote;

    @SerializedName("manzana")
    @Expose
    private String manzana;

    @SerializedName("cp")
    @Expose
    private String cp;

    @SerializedName("colonia")
    @Expose
    private String colonia;

    @SerializedName("ciudad")
    @Expose
    private String ciudad;

    @SerializedName("localidad")
    @Expose
    private String localidad;

    @SerializedName("municipio")
    @Expose
    private String municipio;

    @SerializedName("estado")
    @Expose
    private String estado;

    @SerializedName("located_at")
    @Expose
    private String locatedAt;

    public Integer getIdDireccion() {
        return idDireccion;
    }

    public void setIdDireccion(Integer idDireccion) {
        this.idDireccion = idDireccion;
    }

    public String getTipoDireccion() {
        return tipoDireccion;
    }

    public void setTipoDireccion(String tipoDireccion) {
        this.tipoDireccion = tipoDireccion;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getNumExterior() {
        return numExterior;
    }

    public void setNumExterior(String numExterior) {
        this.numExterior = numExterior;
    }

    public String getNumInterior() {
        return numInterior;
    }

    public void setNumInterior(String numInterior) {
        this.numInterior = numInterior;
    }

    public String getLote() {
        return lote;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }

    public String getManzana() {
        return manzana;
    }

    public void setManzana(String manzana) {
        this.manzana = manzana;
    }

    public String getCp() {
        return cp;
    }

    public void setCp(String cp) {
        this.cp = cp;
    }

    public String getColonia() {
        return colonia;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getLocatedAt() {
        return locatedAt;
    }

    public void setLocatedAt(String locatedAt) {
        this.locatedAt = locatedAt;
    }

}
