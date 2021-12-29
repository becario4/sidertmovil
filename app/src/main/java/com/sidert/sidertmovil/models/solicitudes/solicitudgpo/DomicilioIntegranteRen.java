package com.sidert.sidertmovil.models.solicitudes.solicitudgpo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DomicilioIntegranteRen implements Serializable {
    @SerializedName("id_domicilio")
    @Expose
    private Integer idDomicilio;

    @SerializedName("id_integrante")
    @Expose
    private Integer idIntegrante;

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

    @SerializedName("tipo_vivienda")
    @Expose
    private String tipoVivienda;

    @SerializedName("parentesco")
    @Expose
    private String parentesco;

    @SerializedName("otro_tipo_vivienda")
    @Expose
    private String otroTipoVivienda;

    @SerializedName("tiempo_vivir_sitio")
    @Expose
    private String tiempoVivirSitio;

    @SerializedName("foto_fachada")
    @Expose
    private String fotoFachada;

    @SerializedName("ref_domiciliaria")
    @Expose
    private String refDomiciliaria;

    @SerializedName("estatus_completado")
    @Expose
    private Integer estatusCompletado;

    @SerializedName("dependientes")
    @Expose
    private String dependientes;

    @SerializedName("located_at")
    @Expose
    private String locatedAt;

    public Integer getIdDomicilio() {
        return idDomicilio;
    }

    public void setIdDomicilio(Integer idDomicilio) {
        this.idDomicilio = idDomicilio;
    }

    public Integer getIdIntegrante() {
        return idIntegrante;
    }

    public void setIdIntegrante(Integer idIntegrante) {
        this.idIntegrante = idIntegrante;
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

    public String getTipoVivienda() {
        return tipoVivienda;
    }

    public void setTipoVivienda(String tipoVivienda) {
        this.tipoVivienda = tipoVivienda;
    }

    public String getParentesco() {
        return parentesco;
    }

    public void setParentesco(String parentesco) {
        this.parentesco = parentesco;
    }

    public String getOtroTipoVivienda() {
        return otroTipoVivienda;
    }

    public void setOtroTipoVivienda(String otroTipoVivienda) {
        this.otroTipoVivienda = otroTipoVivienda;
    }

    public String getTiempoVivirSitio() {
        return tiempoVivirSitio;
    }

    public void setTiempoVivirSitio(String tiempoVivirSitio) {
        this.tiempoVivirSitio = tiempoVivirSitio;
    }

    public String getFotoFachada() {
        return fotoFachada;
    }

    public void setFotoFachada(String fotoFachada) {
        this.fotoFachada = fotoFachada;
    }

    public String getRefDomiciliaria() {
        return refDomiciliaria;
    }

    public void setRefDomiciliaria(String refDomiciliaria) {
        this.refDomiciliaria = refDomiciliaria;
    }

    public Integer getEstatusCompletado() {
        return estatusCompletado;
    }

    public void setEstatusCompletado(Integer estatusCompletado) {
        this.estatusCompletado = estatusCompletado;
    }

    public String getDependientes() {
        return dependientes;
    }

    public void setDependientes(String dependientes) {
        this.dependientes = dependientes;
    }

    public String getLocatedAt() {
        return locatedAt;
    }

    public void setLocatedAt(String locatedAt) {
        this.locatedAt = locatedAt;
    }
}
