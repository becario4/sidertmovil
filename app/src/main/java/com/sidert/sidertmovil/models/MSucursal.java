package com.sidert.sidertmovil.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MSucursal implements Serializable {

    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("nombre")
    @Expose
    private String nombre;

    @SerializedName("region_id")
    @Expose
    private Integer regionId;

    @SerializedName("centrocosto_id")
    @Expose
    private Integer centrocostoId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getRegionId() {
        return regionId;
    }

    public void setRegionId(Integer regionId) {
        this.regionId = regionId;
    }

    public Integer getCentrocostoId() {
        return centrocostoId;
    }

    public void setCentrocostoId(Integer centrocostoId) {
        this.centrocostoId = centrocostoId;
    }
}
