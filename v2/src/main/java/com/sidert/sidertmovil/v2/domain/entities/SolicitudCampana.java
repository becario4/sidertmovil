package com.sidert.sidertmovil.v2.domain.entities;

import java.io.Serializable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "solicitud_campana")
public class SolicitudCampana
        implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    private Long id;
    @ColumnInfo(name = "solicitud_id")
    private Integer solicitudId;
    @ColumnInfo(name = "integrante_id")
    private Integer integranteId;
    @ColumnInfo(name = "tipo_solicitud")
    private String tipoSolicitud;
    @ColumnInfo(name = "solicitud_remota_id")
    private Integer solicitudRemotaId;
    @ColumnInfo(name = "campana_nombre")
    private String campanaNombre;
    @ColumnInfo(name = "nombre_referido")
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
}
