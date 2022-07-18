package com.sidert.sidertmovil.models.permiso;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.sidert.sidertmovil.models.ApiResponse;

import java.io.Serializable;

public class PermisoResponse extends ApiResponse {
    @SerializedName("data")
    @Expose
    private Permiso data;

    public Permiso getData() {
        return data;
    }

    public void setData(Permiso data) {
        this.data = data;
    }
}
