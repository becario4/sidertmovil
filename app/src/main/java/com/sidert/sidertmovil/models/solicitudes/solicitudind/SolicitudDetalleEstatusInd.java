package com.sidert.sidertmovil.models.solicitudes.solicitudind;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SolicitudDetalleEstatusInd implements Serializable {
    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("solicitud_estado_id")
    @Expose
    private Integer solicitudEstadoId;

    @SerializedName("tipo_solicitud")
    @Expose
    private Integer tipoSolicitud;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
}
