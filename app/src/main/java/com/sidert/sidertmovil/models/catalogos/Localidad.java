package com.sidert.sidertmovil.models.catalogos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Localidad implements Serializable {

    private Integer _id;

    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("municipioId")
    @Expose
    private Integer municipioId;

    @SerializedName("nombre")
    @Expose
    private String nombre;

   public Integer getId() {
        return _id;
    }

    public void setId(Integer id) { this._id = id; }

    public Integer getLocalidadId() {
        return id;
    }

    public void setLocalidadId(Integer localidadId) {
        this.id = localidadId;
    }

    public Integer getMunicipioId() {
        return municipioId;
    }

    public void setMunicipioId(Integer municipioId) {
        this.municipioId = municipioId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
