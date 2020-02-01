package com.sidert.sidertmovil.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModeloOcupaciones {

    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("clave")
    @Expose
    private String clave;

    @SerializedName("nombre")
    @Expose
    private String nombre;

    @SerializedName("nivel_riesgo")
    @Expose
    private Integer nivelRiesgo;

    @SerializedName("sector_id")
    @Expose
    private Integer sectorId;

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

    public Integer getNivelRiesgo() {
        return nivelRiesgo;
    }

    public void setNivelRiesgo(Integer nivelRiesgo) {
        this.nivelRiesgo = nivelRiesgo;
    }

    public Integer getSectorId() {
        return sectorId;
    }

    public void setSectorId(Integer sectorId) {
        this.sectorId = sectorId;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
