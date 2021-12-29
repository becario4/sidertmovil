package com.sidert.sidertmovil.models.catalogos;

import java.io.Serializable;

public class Colonia implements Serializable {

    private Integer id;

    private Integer coloniaId;

    private String nombre;

    private Integer cp;

    private Integer municipioId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getColoniaId() {
        return coloniaId;
    }

    public void setColoniaId(Integer coloniaId) {
        this.coloniaId = coloniaId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getCp() {
        return cp;
    }

    public void setCp(Integer cp) {
        this.cp = cp;
    }

    public Integer getMunicipioId() {
        return municipioId;
    }

    public void setMunicipioId(Integer municipioId) {
        this.municipioId = municipioId;
    }
}
