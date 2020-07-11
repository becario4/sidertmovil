package com.sidert.sidertmovil.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MResTicket implements Serializable {

    @SerializedName("id")
    @Expose
    private Long id;
    @SerializedName("categoria")
    @Expose
    private Long categoria;
    @SerializedName("comentario")
    @Expose
    private String comentario;
    @SerializedName("imagenSolicitud")
    @Expose
    private String imagenSolicitud;
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
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("estatus")
    @Expose
    private String estatus;
    @SerializedName("comentarioTecnico")
    @Expose
    private String comentarioTecnico;
    @SerializedName("usuarioId")
    @Expose
    private Long usuarioId;
    @SerializedName("fechaSolicitud")
    @Expose
    private String fechaSolicitud;
    @SerializedName("fechaEnvio")
    @Expose
    private String fechaEnvio;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
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

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    public String getComentarioTecnico() {
        return comentarioTecnico;
    }

    public void setComentarioTecnico(String comentarioTecnico) {
        this.comentarioTecnico = comentarioTecnico;
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
