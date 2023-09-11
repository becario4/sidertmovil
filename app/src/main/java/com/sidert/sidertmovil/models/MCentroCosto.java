package com.sidert.sidertmovil.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MCentroCosto implements Serializable {
    private Integer id;

    @SerializedName("centrocosto_id")
    @Expose
    private Integer centroCosto_id;


    public Integer getCentroCosto_id() {
        return centroCosto_id;
    }

    public void setCentroCosto_id(Integer centroCosto_id) {
        this.centroCosto_id = centroCosto_id;
    }
}
