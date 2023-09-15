package com.sidert.sidertmovil.models.dto;

import com.google.common.base.Objects;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class BeneficiarioDto
        implements Serializable {

    @SerializedName("solicitud_id")
    private final Long solicitudId;
    @SerializedName("cliente_id")
    private final Integer clienteId;
    @SerializedName("grupo_id")
    private final Integer grupoId;
    @SerializedName("nombre_beneficiario")
    private final String nombreBeneficiario;
    @SerializedName("apellido_paterno")
    private final String apellidoPaterno;
    @SerializedName("apellido_materno")
    private final String apellidoMaterno;
    @SerializedName("parentesco")
    private final String parentesco;
    @SerializedName("serie_id")
    private final Integer serieid;

    public BeneficiarioDto(Long solicitudId,
                           Integer clienteId,
                           Integer grupoId,
                           String nombreBeneficiario,
                           String apellidoPaterno,
                           String apellidoMaterno,
                           String parentesco,
                           Integer serieid) {
        this.solicitudId = solicitudId;
        this.clienteId = clienteId;
        this.grupoId = grupoId;
        this.nombreBeneficiario = nombreBeneficiario;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.parentesco = parentesco;
        this.serieid = serieid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BeneficiarioDto that = (BeneficiarioDto) o;
        return Objects.equal(solicitudId, that.solicitudId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(solicitudId);
    }

    public Long getSolicitudId() {
        return solicitudId;
    }

    public Integer getClienteId() {
        return clienteId;
    }

    public Integer getGrupoId() {
        return grupoId;
    }

    public String getNombreBeneficiario() {
        return nombreBeneficiario;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public String getParentesco() {
        return parentesco;
    }

    public Integer getSerieid() {
        return serieid;
    }
}
