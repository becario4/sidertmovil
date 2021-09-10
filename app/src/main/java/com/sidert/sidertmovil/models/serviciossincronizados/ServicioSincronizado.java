package com.sidert.sidertmovil.models.serviciossincronizados;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ServicioSincronizado implements Serializable {
    private Integer id;

    @SerializedName("nombre")
    @Expose
    private String nombre;

    @SerializedName("estatus")
    @Expose
    private Integer estatus;

    @SerializedName("posicion")
    @Expose
    private Integer posicion;

    @SerializedName("ejecutado")
    @Expose
    private Integer ejecutado;

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

    public Integer getEstatus() {
        return estatus;
    }

    public void setEstatus(Integer estatus) {
        this.estatus = estatus;
    }

    public Integer getPosicion() {
        return posicion;
    }

    public void setPosicion(Integer posicion) {
        this.posicion = posicion;
    }

    public Integer getEjecutado() {
        return ejecutado;
    }

    public void setEjecutado(Integer ejecutado) {
        this.ejecutado = ejecutado;
    }
}
