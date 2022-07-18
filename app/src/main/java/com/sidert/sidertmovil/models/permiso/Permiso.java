package com.sidert.sidertmovil.models.permiso;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Permiso implements Serializable {
    @SerializedName("id")
    @Expose
    private Long id;

    @SerializedName("nombre")
    @Expose
    private String nombre;

    @SerializedName("modulo_id")
    @Expose
    private Long moduloId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Long getModuloId() {
        return moduloId;
    }

    public void setModuloId(Long moduloId) {
        this.moduloId = moduloId;
    }
}
