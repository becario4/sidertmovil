package com.sidert.sidertmovil.v2.domain.entities;

import java.io.Serializable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tbl_solicitudes")
public final class Solicitudes implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_solicitud")
    private Long idSolicitud;
    @ColumnInfo(name = "vol_solicitud")
    private String volSolicitud;
    @ColumnInfo(name = "usuario_id")
    private Integer usuarioId;
    @ColumnInfo(name = "tipo_solicitud")
    private Integer tipoSolicitud;
    @ColumnInfo(name = "id_originacion")
    private Integer idOriginacion;
    @ColumnInfo(name = "nombre")
    private String nombre;
    @ColumnInfo(name = "fecha_inicio")
    private String fechaInicio;
    @ColumnInfo(name = "fecha_termino")
    private String fechaTermino;
    @ColumnInfo(name = "fecha_envio")
    private String fechaEnvio;
    @ColumnInfo(name = "fecha_dispositivo")
    private String fechaDispositivo;
    @ColumnInfo(name = "fecha_guardado")
    private String fechaGuardado;
    @ColumnInfo(name = "estatus")
    private Integer estatus;


// Getters & Setters

    public Long getIdSolicitud(){
        return this.idSolicitud;
    }

    public void setIdSolicitud(Long idSolicitud){
        this.idSolicitud = idSolicitud;
    }
    public String getVolSolicitud(){
        return this.volSolicitud;
    }

    public void setVolSolicitud(String volSolicitud){
        this.volSolicitud = volSolicitud;
    }
    public Integer getUsuarioId(){
        return this.usuarioId;
    }

    public void setUsuarioId(Integer usuarioId){
        this.usuarioId = usuarioId;
    }
    public Integer getTipoSolicitud(){
        return this.tipoSolicitud;
    }

    public void setTipoSolicitud(Integer tipoSolicitud){
        this.tipoSolicitud = tipoSolicitud;
    }
    public Integer getIdOriginacion(){
        return this.idOriginacion;
    }

    public void setIdOriginacion(Integer idOriginacion){
        this.idOriginacion = idOriginacion;
    }
    public String getNombre(){
        return this.nombre;
    }

    public void setNombre(String nombre){
        this.nombre = nombre;
    }
    public String getFechaInicio(){
        return this.fechaInicio;
    }

    public void setFechaInicio(String fechaInicio){
        this.fechaInicio = fechaInicio;
    }
    public String getFechaTermino(){
        return this.fechaTermino;
    }

    public void setFechaTermino(String fechaTermino){
        this.fechaTermino = fechaTermino;
    }
    public String getFechaEnvio(){
        return this.fechaEnvio;
    }

    public void setFechaEnvio(String fechaEnvio){
        this.fechaEnvio = fechaEnvio;
    }
    public String getFechaDispositivo(){
        return this.fechaDispositivo;
    }

    public void setFechaDispositivo(String fechaDispositivo){
        this.fechaDispositivo = fechaDispositivo;
    }
    public String getFechaGuardado(){
        return this.fechaGuardado;
    }

    public void setFechaGuardado(String fechaGuardado){
        this.fechaGuardado = fechaGuardado;
    }
    public Integer getEstatus(){
        return this.estatus;
    }

    public void setEstatus(Integer estatus){
        this.estatus = estatus;
    }

}