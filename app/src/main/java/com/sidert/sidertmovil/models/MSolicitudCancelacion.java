package com.sidert.sidertmovil.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MSolicitudCancelacion implements Serializable {

    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("mensaje")
    @Expose
    private String mensaje;
    @SerializedName("id_cancelacion")
    @Expose
    private Integer idCancelacion;

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

    public Integer getIdCancelacion() {
        return idCancelacion;
    }

    public void setIdCancelacion(Integer idCancelacion) {
        this.idCancelacion = idCancelacion;
    }
}
