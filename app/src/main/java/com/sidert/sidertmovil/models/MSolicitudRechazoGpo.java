package com.sidert.sidertmovil.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MSolicitudRechazoGpo implements Serializable {

    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("id_solicitud_integrante")
    @Expose
    private Integer idSolicitudIntegrante;

    @SerializedName("solicitud_estado_id")
    @Expose
    private Integer solicitudEstadoId;

    @SerializedName("tipo_solicitud")
    @Expose
    private Integer tipoSolicitud;

    @SerializedName("aprobado")
    @Expose
    private Boolean aprobado;

    @SerializedName("comentario_admin")
    @Expose
    private String comentarioAdmin;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdSolicitudIntegrante() {
        return idSolicitudIntegrante;
    }

    public void setIdSolicitudIntegrante(Integer idSolicitudIntegrante) {
        this.idSolicitudIntegrante = idSolicitudIntegrante;
    }

    public Integer getSolicitudEstadoId() {
        return solicitudEstadoId;
    }

    public void setSolicitudEstadoId(Integer solicitudEstadoId) {
        this.solicitudEstadoId = solicitudEstadoId;
    }

    public Integer getTipoSolicitud() {
        return tipoSolicitud;
    }

    public void setTipoSolicitud(Integer tipoSolicitud) {
        this.tipoSolicitud = tipoSolicitud;
    }

    public Boolean getAprobado() {
        return aprobado;
    }

    public void setAprobado(Boolean aprobado) {
        this.aprobado = aprobado;
    }

    public String getComentarioAdmin() {
        return comentarioAdmin;
    }

    public void setComentarioAdmin(String comentarioAdmin) {
        this.comentarioAdmin = comentarioAdmin;
    }
}
