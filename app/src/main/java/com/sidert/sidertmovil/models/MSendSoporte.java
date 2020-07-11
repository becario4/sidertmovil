package com.sidert.sidertmovil.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MSendSoporte implements Serializable {

    @SerializedName("usuarioId")
    @Expose
    private Long usuarioId;

    @SerializedName("categoria")
    @Expose
    private Long categoria;

    @SerializedName("clienteId")
    @Expose
    private Long clienteId;

    @SerializedName("grupoId")
    @Expose
    private Long grupoId;

    @SerializedName("noSolicitud")
    @Expose
    private Long noSolicitud;

    @SerializedName("folioImpresion")
    @Expose
    private String folioImpresion;

    @SerializedName("comentario")
    @Expose
    private String comentario;

    @SerializedName("fechaSolicitud")
    @Expose
    private String fechaSolicitud;

    @SerializedName("fechaEnvio")
    @Expose
    private String fechaEnvio;

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Long getCategoria() {
        return categoria;
    }

    public void setCategoria(Long categoria) {
        this.categoria = categoria;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public Long getGrupoId() {
        return grupoId;
    }

    public void setGrupoId(Long grupoId) {
        this.grupoId = grupoId;
    }

    public Long getNoSolicitud() {
        return noSolicitud;
    }

    public void setNoSolicitud(Long noSolicitud) {
        this.noSolicitud = noSolicitud;
    }

    public String getFolioImpresion() {
        return folioImpresion;
    }

    public void setFolioImpresion(String folioImpresion) {
        this.folioImpresion = folioImpresion;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public String getFechaSolicitud() {
        return fechaSolicitud;
    }

    public void setFechaSolicitud(String fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }

    public String getFechaEnvio() {
        return fechaEnvio;
    }

    public void setFechaEnvio(String fechaEnvio) {
        this.fechaEnvio = fechaEnvio;
    }
}
