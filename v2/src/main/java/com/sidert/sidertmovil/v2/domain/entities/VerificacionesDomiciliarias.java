package com.sidert.sidertmovil.v2.domain.entities;

import java.io.Serializable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tbl_verificaciones_domiciliarias")
public final class VerificacionesDomiciliarias implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    private Long id;
    @ColumnInfo(name = "verificacion_domiciliaria_id")
    private Integer verificacionDomiciliariaId;
    @ColumnInfo(name = "prestamo_id")
    private Integer prestamoId;
    @ColumnInfo(name = "cliente_id")
    private Integer clienteId;
    @ColumnInfo(name = "cliente_nombre")
    private String clienteNombre;
    @ColumnInfo(name = "cliente_nacionalidad")
    private String clienteNacionalidad;
    @ColumnInfo(name = "cliente_fecha_nacimiento")
    private String clienteFechaNacimiento;
    @ColumnInfo(name = "domicilio_direccion")
    private String domicilioDireccion;
    @ColumnInfo(name = "domicilio_referencia")
    private String domicilioReferencia;
    @ColumnInfo(name = "monto_solicitado")
    private String montoSolicitado;
    @ColumnInfo(name = "horario_localizacion")
    private String horarioLocalizacion;
    @ColumnInfo(name = "verificacion_tipo_id")
    private Integer verificacionTipoId;
    @ColumnInfo(name = "estatus")
    private Integer estatus;
    @ColumnInfo(name = "solicitante_id")
    private Integer solicitanteId;
    @ColumnInfo(name = "solicitud_id")
    private Integer solicitudId;
    @ColumnInfo(name = "asesor_serie_id")
    private String asesorSerieId;
    @ColumnInfo(name = "asesor_nombre")
    private String asesorNombre;
    @ColumnInfo(name = "usuario_id")
    private Integer usuarioId;
    @ColumnInfo(name = "sucursal_id")
    private Integer sucursalId;
    @ColumnInfo(name = "sucursal_nombre")
    private String sucursalNombre;
    @ColumnInfo(name = "fecha_asignacion")
    private String fechaAsignacion;
    @ColumnInfo(name = "fecha_expiracion")
    private String fechaExpiracion;
    @ColumnInfo(name = "created_at")
    private String createdAt;
    @ColumnInfo(name = "grupo_id")
    private Integer grupoId;
    @ColumnInfo(name = "grupo_nombre")
    private String grupoNombre;
    @ColumnInfo(name = "num_solicitud")
    private Integer numSolicitud;


// Getters & Setters

    public Long getId(){
        return this.id;
    }

    public void setId(Long id){
        this.id = id;
    }
    public Integer getVerificacionDomiciliariaId(){
        return this.verificacionDomiciliariaId;
    }

    public void setVerificacionDomiciliariaId(Integer verificacionDomiciliariaId){
        this.verificacionDomiciliariaId = verificacionDomiciliariaId;
    }
    public Integer getPrestamoId(){
        return this.prestamoId;
    }

    public void setPrestamoId(Integer prestamoId){
        this.prestamoId = prestamoId;
    }
    public Integer getClienteId(){
        return this.clienteId;
    }

    public void setClienteId(Integer clienteId){
        this.clienteId = clienteId;
    }
    public String getClienteNombre(){
        return this.clienteNombre;
    }

    public void setClienteNombre(String clienteNombre){
        this.clienteNombre = clienteNombre;
    }
    public String getClienteNacionalidad(){
        return this.clienteNacionalidad;
    }

    public void setClienteNacionalidad(String clienteNacionalidad){
        this.clienteNacionalidad = clienteNacionalidad;
    }
    public String getClienteFechaNacimiento(){
        return this.clienteFechaNacimiento;
    }

    public void setClienteFechaNacimiento(String clienteFechaNacimiento){
        this.clienteFechaNacimiento = clienteFechaNacimiento;
    }
    public String getDomicilioDireccion(){
        return this.domicilioDireccion;
    }

    public void setDomicilioDireccion(String domicilioDireccion){
        this.domicilioDireccion = domicilioDireccion;
    }
    public String getDomicilioReferencia(){
        return this.domicilioReferencia;
    }

    public void setDomicilioReferencia(String domicilioReferencia){
        this.domicilioReferencia = domicilioReferencia;
    }
    public String getMontoSolicitado(){
        return this.montoSolicitado;
    }

    public void setMontoSolicitado(String montoSolicitado){
        this.montoSolicitado = montoSolicitado;
    }
    public String getHorarioLocalizacion(){
        return this.horarioLocalizacion;
    }

    public void setHorarioLocalizacion(String horarioLocalizacion){
        this.horarioLocalizacion = horarioLocalizacion;
    }
    public Integer getVerificacionTipoId(){
        return this.verificacionTipoId;
    }

    public void setVerificacionTipoId(Integer verificacionTipoId){
        this.verificacionTipoId = verificacionTipoId;
    }
    public Integer getEstatus(){
        return this.estatus;
    }

    public void setEstatus(Integer estatus){
        this.estatus = estatus;
    }
    public Integer getSolicitanteId(){
        return this.solicitanteId;
    }

    public void setSolicitanteId(Integer solicitanteId){
        this.solicitanteId = solicitanteId;
    }
    public Integer getSolicitudId(){
        return this.solicitudId;
    }

    public void setSolicitudId(Integer solicitudId){
        this.solicitudId = solicitudId;
    }
    public String getAsesorSerieId(){
        return this.asesorSerieId;
    }

    public void setAsesorSerieId(String asesorSerieId){
        this.asesorSerieId = asesorSerieId;
    }
    public String getAsesorNombre(){
        return this.asesorNombre;
    }

    public void setAsesorNombre(String asesorNombre){
        this.asesorNombre = asesorNombre;
    }
    public Integer getUsuarioId(){
        return this.usuarioId;
    }

    public void setUsuarioId(Integer usuarioId){
        this.usuarioId = usuarioId;
    }
    public Integer getSucursalId(){
        return this.sucursalId;
    }

    public void setSucursalId(Integer sucursalId){
        this.sucursalId = sucursalId;
    }
    public String getSucursalNombre(){
        return this.sucursalNombre;
    }

    public void setSucursalNombre(String sucursalNombre){
        this.sucursalNombre = sucursalNombre;
    }
    public String getFechaAsignacion(){
        return this.fechaAsignacion;
    }

    public void setFechaAsignacion(String fechaAsignacion){
        this.fechaAsignacion = fechaAsignacion;
    }
    public String getFechaExpiracion(){
        return this.fechaExpiracion;
    }

    public void setFechaExpiracion(String fechaExpiracion){
        this.fechaExpiracion = fechaExpiracion;
    }
    public String getCreatedAt(){
        return this.createdAt;
    }

    public void setCreatedAt(String createdAt){
        this.createdAt = createdAt;
    }
    public Integer getGrupoId(){
        return this.grupoId;
    }

    public void setGrupoId(Integer grupoId){
        this.grupoId = grupoId;
    }
    public String getGrupoNombre(){
        return this.grupoNombre;
    }

    public void setGrupoNombre(String grupoNombre){
        this.grupoNombre = grupoNombre;
    }
    public Integer getNumSolicitud(){
        return this.numSolicitud;
    }

    public void setNumSolicitud(Integer numSolicitud){
        this.numSolicitud = numSolicitud;
    }

}