package com.sidert.sidertmovil.models;

import java.io.Serializable;

public class ModeloCatalogoGral implements Serializable {

    private String id;
    private String nombre;
    private String extra;
    private String catalogo;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public String getCatalogo() {
        return catalogo;
    }

    public void setCatalogo(String catalogo) {
        this.catalogo = catalogo;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
