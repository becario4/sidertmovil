package com.sidert.sidertmovil.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class MSucursales implements Serializable {

    @SerializedName("nombre")
    @Expose
    private String nombre;
    @SerializedName("asesores")
    @Expose
    private List<MAsesor> asesores = null;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<MAsesor> getAsesores() {
        return asesores;
    }

    public void setAsesores(List<MAsesor> asesores) {
        this.asesores = asesores;
    }


}
