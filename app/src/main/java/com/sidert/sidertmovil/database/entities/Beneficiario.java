package com.sidert.sidertmovil.database.entities;

import java.io.Serializable;

public class Beneficiario
        implements Serializable {

    public static final String TABLE = "beneficiario";
    public static final String ID = "_id";
    public static final String SOLICITUD_ID = "solicitud_id";
    public static final String INTEGRANTE_ID = "integrante_id";
    public static final String TIPO_SOLICITUD = "tipo_solicitud";
    public static final String SOLICITUD_REMOTA_ID = "solicitud_remota_id";
    public static final String NOMBRE = "nombre";
    public static final String PATERNO = "paterno";
    public static final String MATERNO = "materno";
    public static final String PARENTESCO = "parentesco";

    private Long id;
    private Integer solicitudId;
    private Integer integranteId;
    private String tipoSolicitud;
    private Integer solicitudRemotaId;
    private String nombre;
    private String paterno;
    private String materno;
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
}
