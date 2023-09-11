package com.sidert.sidertmovil.models.catalogos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Campanas implements Serializable {

    private Integer id;

    @SerializedName("id_campana")
    @Expose
    private Integer id_campanas;

    @SerializedName("tipo_campana")
    @Expose
    private String tipo_campanas;

    @SerializedName("estatus")
    @Expose
    private Integer estatus;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId_campanas(){return id_campanas;}

    public void setId_campanas(Integer id_campanas){this.id_campanas = id_campanas;}

    public String getTipo_campanas() {
        return tipo_campanas;
    }

    public void setTipo_campanas(String tipo_campanas) {
        this.tipo_campanas = tipo_campanas;
    }

    public Integer getEstatus(){return estatus;}

    public void setEstatus(Integer estatus){ this.estatus = estatus; }
}
