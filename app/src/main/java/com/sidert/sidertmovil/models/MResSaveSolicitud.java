package com.sidert.sidertmovil.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MResSaveSolicitud implements Serializable {

    @SerializedName("mensaje")
    @Expose
    private String mensaje;
    @SerializedName("codigo")
    @Expose
    private Integer codigo;
    @SerializedName("id_solicitud")
    @Expose
    private Integer idSolicitud;

    @SerializedName("id_grupo")
    @Expose
    private Integer id_grupo;

    @SerializedName("id_cliente")
    @Expose
    private Integer id_cliente;


    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public Integer getIdSolicitud() {
        return idSolicitud;
    }

    public void setIdSolicitud(Integer idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    public Integer getId_grupo() {
        return id_grupo;
    }

    public void setId_grupo(Integer id_grupo) {
        this.id_grupo = id_grupo;
    }

    public Integer getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(Integer id_cliente) {
        this.id_cliente = id_cliente;
    }
}
