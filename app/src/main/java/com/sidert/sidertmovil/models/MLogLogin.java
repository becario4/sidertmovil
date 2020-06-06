package com.sidert.sidertmovil.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MLogLogin implements Serializable {

    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("serie_id")
    @Expose
    private String serieId;

    @SerializedName("nombre_asesor")
    @Expose
    private String nombreAsesor;

    @SerializedName("usuario")
    @Expose
    private String usuario;

    @SerializedName("sucursal")
    @Expose
    private String sucursal;

    @SerializedName("sucursalid")
    @Expose
    private String sucursalid;

    @SerializedName("horariologin")
    @Expose
    private Object horariologin;

    @SerializedName("nivelbateria")
    @Expose
    private Object nivelbateria;

    @SerializedName("versionapp")
    @Expose
    private Object versionapp;

    @SerializedName("primeragestion")
    @Expose
    private Object primeragestion;

    @SerializedName("ultimagestion")
    @Expose
    private Object ultimagestion;

    @SerializedName("totalgestiones")
    @Expose
    private Object totalgestiones;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSerieId() {
        return serieId;
    }

    public void setSerieId(String serieId) {
        this.serieId = serieId;
    }

    public String getNombreAsesor() {
        return nombreAsesor;
    }

    public void setNombreAsesor(String nombreAsesor) {
        this.nombreAsesor = nombreAsesor;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getSucursal() {
        return sucursal;
    }

    public void setSucursal(String sucursal) {
        this.sucursal = sucursal;
    }

    public String getSucursalid() {
        return sucursalid;
    }

    public void setSucursalid(String sucursalid) {
        this.sucursalid = sucursalid;
    }

    public Object getHorariologin() {
        return horariologin;
    }

    public void setHorariologin(Object horariologin) {
        this.horariologin = horariologin;
    }

    public Object getNivelbateria() {
        return nivelbateria;
    }

    public void setNivelbateria(Object nivelbateria) {
        this.nivelbateria = nivelbateria;
    }

    public Object getVersionapp() {
        return versionapp;
    }

    public void setVersionapp(Object versionapp) {
        this.versionapp = versionapp;
    }

    public Object getPrimeragestion() {
        return primeragestion;
    }

    public void setPrimeragestion(Object primeragestion) {
        this.primeragestion = primeragestion;
    }

    public Object getUltimagestion() {
        return ultimagestion;
    }

    public void setUltimagestion(Object ultimagestion) {
        this.ultimagestion = ultimagestion;
    }

    public Object getTotalgestiones() {
        return totalgestiones;
    }

    public void setTotalgestiones(Object totalgestiones) {
        this.totalgestiones = totalgestiones;
    }
}
