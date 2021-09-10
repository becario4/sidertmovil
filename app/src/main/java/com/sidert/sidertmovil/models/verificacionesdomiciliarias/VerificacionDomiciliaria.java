package com.sidert.sidertmovil.models.verificacionesdomiciliarias;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class VerificacionDomiciliaria implements Serializable {
    @SerializedName("id")
    @Expose
    private Long id;

    @SerializedName("verificacion_domiciliaria_id")
    @Expose(serialize = false)
    private Long verificacionDomiciliariaId;

    @SerializedName("prestamo_id")
    @Expose
    private Long prestamoId;

    @SerializedName("num_solicitud")
    @Expose
    private Long numSolicitud;

    @SerializedName("grupo_id")
    @Expose
    private Long grupoId;

    @SerializedName("grupo_nombre")
    @Expose
    private String grupoNombre;

    @SerializedName("cliente_id")
    @Expose
    private Long clienteId;

    @SerializedName("cliente_nombre")
    @Expose
    private String clienteNombre;

    @SerializedName("cliente_nacionalidad")
    @Expose
    private String clienteNacionalidad;

    @SerializedName("cliente_fecha_nacimiento")
    @Expose
    private String clienteFechaNacimiento;

    @SerializedName("domicilio_direccion")
    @Expose
    private String domicilioDireccion;

    @SerializedName("domicilio_referencia")
    @Expose
    private String domicilioReferencia;

    @SerializedName("monto_solicitado")
    @Expose
    private String montoSolicitado;

    @SerializedName("horario_localizacion")
    @Expose
    private String horarioLocalizacion;

    @SerializedName("verificacion_tipo_id")
    @Expose
    private Integer verificacionTipoId;

    @SerializedName("estatus")
    @Expose
    private Integer estatus;

    @SerializedName("solicitante_id")
    @Expose
    private Long solicitanteId;

    @SerializedName("solicitud_id")
    @Expose
    private Long solicitudId;

    @SerializedName("asesor_serie_id")
    @Expose
    private String asesorSerieId;

    @SerializedName("asesor_nombre")
    @Expose
    private String asesorNombre;

    @SerializedName("usuario_id")
    @Expose
    private Long usuarioId;

    @SerializedName("sucursal_id")
    @Expose
    private Long sucursalId;

    @SerializedName("sucursal_nombre")
    @Expose
    private String sucursalNombre;

    @SerializedName("fecha_asignacion")
    @Expose
    private String fechaAsignacion;

    @SerializedName("fecha_expiracion")
    @Expose
    private String fechaExpiracion;

    @SerializedName("created_at")
    @Expose
    private String createdAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getVerificacionDomiciliariaId() {
        return verificacionDomiciliariaId;
    }

    public void setVerificacionDomiciliariaId(Long verificacionDomiciliariaId) {
        this.verificacionDomiciliariaId = verificacionDomiciliariaId;
    }

    public Long getPrestamoId() {
        return prestamoId;
    }

    public void setPrestamoId(Long prestamoId) {
        this.prestamoId = prestamoId;
    }

    public Long getGrupoId() {
        return grupoId;
    }

    public void setGrupoId(Long grupoId) {
        this.grupoId = grupoId;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public String getClienteNombre() {
        return clienteNombre;
    }

    public void setClienteNombre(String clienteNombre) {
        this.clienteNombre = clienteNombre;
    }

    public String getClienteNacionalidad() {
        return clienteNacionalidad;
    }

    public void setClienteNacionalidad(String clienteNacionalidad) {
        this.clienteNacionalidad = clienteNacionalidad;
    }

    public String getClienteFechaNacimiento() {
        return clienteFechaNacimiento;
    }

    public void setClienteFechaNacimiento(String clienteFechaNacimiento) {
        this.clienteFechaNacimiento = clienteFechaNacimiento;
    }

    public String getDomicilioDireccion() {
        return domicilioDireccion;
    }

    public void setDomicilioDireccion(String domicilioDireccion) {
        this.domicilioDireccion = domicilioDireccion;
    }

    public String getDomicilioReferencia() {
        return domicilioReferencia;
    }

    public void setDomicilioReferencia(String domicilioReferencia) {
        this.domicilioReferencia = domicilioReferencia;
    }

    public String getMontoSolicitado() {
        return montoSolicitado;
    }

    public void setMontoSolicitado(String montoSolicitado) {
        this.montoSolicitado = montoSolicitado;
    }

    public String getHorarioLocalizacion() {
        return horarioLocalizacion;
    }

    public void setHorarioLocalizacion(String horarioLocalizacion) {
        this.horarioLocalizacion = horarioLocalizacion;
    }

    public Integer getVerificacionTipoId() {
        return verificacionTipoId;
    }

    public void setVerificacionTipoId(Integer verificacionTipoId) {
        this.verificacionTipoId = verificacionTipoId;
    }

    public Integer getEstatus() {
        return estatus;
    }

    public void setEstatus(Integer estatus) {
        this.estatus = estatus;
    }

    public Long getSolicitanteId() {
        return solicitanteId;
    }

    public void setSolicitanteId(Long solicitanteId) {
        this.solicitanteId = solicitanteId;
    }

    public Long getSolicitudId() {
        return solicitudId;
    }

    public void setSolicitudId(Long solicitudId) {
        this.solicitudId = solicitudId;
    }

    public String getAsesorSerieId() {
        return asesorSerieId;
    }

    public void setAsesorSerieId(String asesorSerieId) {
        this.asesorSerieId = asesorSerieId;
    }

    public String getAsesorNombre() {
        return asesorNombre;
    }

    public void setAsesorNombre(String asesorNombre) {
        this.asesorNombre = asesorNombre;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Long getSucursalId() {
        return sucursalId;
    }

    public void setSucursalId(Long sucursalId) {
        this.sucursalId = sucursalId;
    }

    public String getSucursalNombre() {
        return sucursalNombre;
    }

    public void setSucursalNombre(String sucursalNombre) {
        this.sucursalNombre = sucursalNombre;
    }

    public String getFechaAsignacion() {
        return fechaAsignacion;
    }

    public void setFechaAsignacion(String fechaAsignacion) {
        this.fechaAsignacion = fechaAsignacion;
    }

    public String getFechaExpiracion() {
        return fechaExpiracion;
    }

    public void setFechaExpiracion(String fechaExpiracion) {
        this.fechaExpiracion = fechaExpiracion;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getGrupoNombre() {
        return grupoNombre;
    }

    public void setGrupoNombre(String grupoNombre) {
        this.grupoNombre = grupoNombre;
    }

    public Long getNumSolicitud() {
        return numSolicitud;
    }

    public void setNumSolicitud(Long numSolicitud) {
        this.numSolicitud = numSolicitud;
    }
}
