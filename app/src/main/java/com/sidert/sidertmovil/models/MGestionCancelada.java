package com.sidert.sidertmovil.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class MGestionCancelada implements Serializable {
    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("data")
    @Expose
    private List<MRespuestaSolicitud> data = null;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public List<MRespuestaSolicitud> getData() {
        return data;
    }

    public void setData(List<MRespuestaSolicitud> data) {
        this.data = data;
    }
}
