package com.sidert.sidertmovil.models.solicitudes.solicitudgpo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class NegocioIntegrante implements Serializable {
    @SerializedName("id_negocio")
    @Expose
    private Integer idNegocio;

    @SerializedName("id_integrante")
    @Expose
    private Integer idIntegrante;

    @SerializedName("nombre")
    @Expose
    private String nombre;

    @SerializedName("latitud")
    @Expose
    private String latitud;

    @SerializedName("longitud")
    @Expose
    private String longitud;

    @SerializedName("calle")
    @Expose
    private String calle;

    @SerializedName("no_exterior")
    @Expose
    private String noExterior;

    @SerializedName("no_interior")
    @Expose
    private String noInterior;

    @SerializedName("manzana")
    @Expose
    private String manzana;

    @SerializedName("lote")
    @Expose
    private String lote;

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

    public Integer getIdNegocio() {
        return idNegocio;
    }

    public void setIdNegocio(Integer idNegocio) {
        this.idNegocio = idNegocio;
    }

    public Integer getIdIntegrante() {
        return idIntegrante;
    }

    public void setIdIntegrante(Integer idIntegrante) {
        this.idIntegrante = idIntegrante;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public String getNoExterior() {
        return noExterior;
    }

    public void setNoExterior(String noExterior) {
        this.noExterior = noExterior;
    }

    public String getNoInterior() {
        return noInterior;
    }

    public void setNoInterior(String noInterior) {
        this.noInterior = noInterior;
    }

    public String getManzana() {
        return manzana;
    }

    public void setManzana(String manzana) {
        this.manzana = manzana;
    }

    public String getLote() {
        return lote;
    }

    public void setLote(String lote) {
        this.lote = lote;
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
}
