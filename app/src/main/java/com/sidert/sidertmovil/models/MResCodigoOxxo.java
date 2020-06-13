package com.sidert.sidertmovil.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MResCodigoOxxo implements Serializable {

    @SerializedName("data")
    @Expose
    private MCodigoOxxo codigoOxxo;

    @SerializedName("code")
    @Expose
    private Integer code;

    @SerializedName("mensaje")
    @Expose
    private String mensaje;

    public MCodigoOxxo getData() {
        return codigoOxxo;
    }

    public void setData(MCodigoOxxo codigoOxxo) {
        this.codigoOxxo = codigoOxxo;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
