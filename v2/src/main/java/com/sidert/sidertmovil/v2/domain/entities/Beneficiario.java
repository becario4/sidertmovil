package com.sidert.sidertmovil.v2.domain.entities;

import java.io.Serializable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "beneficiario")
public class Beneficiario
        implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    private Long id;
    @ColumnInfo(name = "solicitud_id")
    private Integer solicitudId;
    @ColumnInfo(name = "integrante_id")
    private Integer integranteId;
    @ColumnInfo(name = "solicitud_remota_id")
    private Integer solicitudRemotaId;
    @ColumnInfo(name = "tipo_solicitud")
    private String tipoSolicitud;
    @ColumnInfo(name = "nombre")
    private String nombre;
    @ColumnInfo(name = "paterno")
    private String paterno;
    @ColumnInfo(name = "materno")
    private String materno;
    @ColumnInfo(name = "parentesco")
    private String parentesco;

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

    public Integer getSolicitudRemotaId() {
        return solicitudRemotaId;
    }

    public void setSolicitudRemotaId(Integer solicitudRemotaId) {
        this.solicitudRemotaId = solicitudRemotaId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPaterno() {
        return paterno;
    }

    public void setPaterno(String paterno) {
        this.paterno = paterno;
    }

    public String getMaterno() {
        return materno;
    }

    public void setMaterno(String materno) {
        this.materno = materno;
    }

    public String getParentesco() {
        return parentesco;
    }

    public void setParentesco(String parentesco) {
        this.parentesco = parentesco;
    }

    public String getTipoSolicitud() {
        return tipoSolicitud;
    }

    public void setTipoSolicitud(String tipoSolicitud) {
        this.tipoSolicitud = tipoSolicitud;
    }
}
