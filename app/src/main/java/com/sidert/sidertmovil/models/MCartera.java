package com.sidert.sidertmovil.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MCartera {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("clave")
    @Expose
    private String clave;
    @SerializedName("nombre")
    @Expose
    private String nombre;
    @SerializedName("direccion")
    @Expose
    private String direccion;
    @SerializedName("asesor_nombre")
    @Expose
    private String asesorNombre;
    @SerializedName("serie_id")
    @Expose
    private String serieId;
    @SerializedName("usuario_id")
    @Expose
    private Integer usuarioId;
    @SerializedName("dia")
    @Expose
    private String dia;
    @SerializedName("num_solicitud")
    @Expose
    private String numSolicitud;
    @SerializedName("cartera_tipo")
    @Expose
    private String carteraTipo;
    @SerializedName("tesorero")
    @Expose
    private String tesorero;
    @SerializedName("colonia")
    @Expose
    private String colonia;
    @SerializedName("ruta")
    @Expose
    private Boolean ruta;
    @SerializedName("geo_cliente")
    @Expose
    private Boolean geoCliente;
    @SerializedName("geo_aval")
    @Expose
    private Boolean geoAval;
    @SerializedName("geo_negocio")
    @Expose
    private Boolean geoNegocio;
    @SerializedName("geolocalizadas")
    @Expose
    private String geolocalizadas;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getAsesorNombre() {
        return asesorNombre;
    }

    public void setAsesorNombre(String asesorNombre) {
        this.asesorNombre = asesorNombre;
    }

    public String getSerieId() {
        return serieId;
    }

    public void setSerieId(String serieId) {
        this.serieId = serieId;
    }

    public Integer getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Integer usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public String getNumSolicitud() {
        return numSolicitud;
    }

    public void setNumSolicitud(String numSolicitud) {
        this.numSolicitud = numSolicitud;
    }

    public String getCarteraTipo() {
        return carteraTipo;
    }

    public void setCarteraTipo(String carteraTipo) {
        this.carteraTipo = carteraTipo;
    }

    public String getTesorero() {
        return tesorero;
    }

    public void setTesorero(String tesorero) {
        this.tesorero = tesorero;
    }

    public String getColonia() {
        return colonia;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    public Boolean getRuta() {
        return ruta;
    }

    public void setRuta(Boolean ruta) {
        this.ruta = ruta;
    }

    public Boolean getGeoCliente() {
        return geoCliente;
    }

    public void setGeoCliente(Boolean geoCliente) {
        this.geoCliente = geoCliente;
    }

    public Boolean getGeoAval() {
        return geoAval;
    }

    public void setGeoAval(Boolean geoAval) {
        this.geoAval = geoAval;
    }

    public Boolean getGeoNegocio() {
        return geoNegocio;
    }

    public void setGeoNegocio(Boolean geoNegocio) {
        this.geoNegocio = geoNegocio;
    }

    public String getGeolocalizadas() {
        return geolocalizadas;
    }

    public void setGeolocalizadas(String geolocalizadas) {
        this.geolocalizadas = geolocalizadas;
    }
}
