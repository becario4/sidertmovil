package com.sidert.sidertmovil.models.catalogos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SucursalesLocalidades implements Serializable {

    private Integer id_Sucursal_localidad;

    @SerializedName("centrocosto")
    @Expose
    private Integer centro_costo;

    @SerializedName("id_municipio")
    @Expose
    private Integer id_municipio;

    @SerializedName("id_localidad")
    @Expose
    private  Integer id_localidad;

    @SerializedName("localidad")
    @Expose
    private  String localidad;

    public Integer getCentro_costo() {
        return centro_costo;
    }

    public void setCentro_costo(Integer centro_costo) {
        this.centro_costo = centro_costo;
    }

    public Integer getId_municipio() {
        return id_municipio;
    }

    public void setId_municipio(Integer id_municipio) {
        this.id_municipio = id_municipio;
    }

    public Integer getId_localidad() {
        return id_localidad;
    }

    public void setId_localidad(Integer id_localidad) {
        this.id_localidad = id_localidad;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

/*  @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("centrocosto")
    @Expose
    private Integer centroCosto;

    @SerializedName("id_municipio")
    @Expose
    private Integer id_municipio;

    @SerializedName("localidad")
    @Expose
    private String localidad;

    @SerializedName("colonia")
    @Expose
    private String colonia;

    @SerializedName("codigo_postal")
    @Expose
    private Integer codigo_postal;

    public Integer getId_Sucursal_localidad() {
        return id_Sucursal_localidad;
    }

    public void setId_Sucursal_localidad(Integer id_Sucursal_localidad) {
        this.id_Sucursal_localidad = id_Sucursal_localidad;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCentroCosto() {
        return centroCosto;
    }

    public void setCentroCosto(Integer centroCosto) {
        this.centroCosto = centroCosto;
    }

    public Integer getId_municipio() {
        return id_municipio;
    }

    public void setId_municipio(Integer id_municipio) {
        this.id_municipio = id_municipio;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public String getColonia() {
        return colonia;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    public Integer getCodigo_postal() {
        return codigo_postal;
    }

    public void setCodigo_postal(Integer codigo_postal) {
        this.codigo_postal = codigo_postal;
    }*/
}
