package com.sidert.sidertmovil.database.entities;

import com.google.common.base.MoreObjects;
import com.sidert.sidertmovil.database.EntitiesCommonsContants;

import java.io.Serializable;

public class SolicitudCampana implements Serializable {

    public static final String TABLE = "solicitud_campana";
    public static final String ID = "_id";
    public static final String SOLICITUD_ID = "solicitud_id";
    public static final String INTEGRANTE_ID = "integrante_id";
    public static final String TIPO_SOLICITUD = "tipo_solicitud";
    public static final String SOLICITUD_REMOTA_ID = "solicitud_remota_id";
    public static final String CAMAPANA_NOMBRE = "campana_nombre";
    public static final String NOMBRE_REFERIDO = "nombre_referido";

    private Long id;
    private Integer solicitudId;
    private Integer integranteId;
    private String tipoSolicitud;
    private Integer solicitudRemotaId;
    private String campanaNombre;
    private String nombreReferido;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSolicitudId() {
        return solicitudId;
    }

    public void setSolicitudId(Integer solicitudId) {
        this.solicitudId = solicitudId;
    }

    public Integer getIntegranteId() {
        return integranteId;
    }

    public void setIntegranteId(Integer integranteId) {
        this.integranteId = integranteId;
    }

    public String getTipoSolicitud() {
        return tipoSolicitud;
    }

    public void setTipoSolicitud(String tipoSolicitud) {
        this.tipoSolicitud = tipoSolicitud;
    }

    public Integer getSolicitudRemotaId() {
        return solicitudRemotaId;
    }

    public void setSolicitudRemotaId(Integer solicitudRemotaId) {
        this.solicitudRemotaId = solicitudRemotaId;
    }

    public String getCampanaNombre() {
        return campanaNombre;
    }

    public void setCampanaNombre(String campanaNombre) {
        this.campanaNombre = campanaNombre;
    }

    public String getNombreReferido() {
        return nombreReferido;
    }

    public void setNombreReferido(String nombreReferido) {
        this.nombreReferido = nombreReferido;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("solicitudId", solicitudId)
                .add("integranteId", integranteId)
                .add("tipoSolicitud", tipoSolicitud)
                .add("solicitudRemotaId", solicitudRemotaId)
                .add("campanaNombre", campanaNombre)
                .add("nombreReferido", nombreReferido)
                .toString();
    }
}
