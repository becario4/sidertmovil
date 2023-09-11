package com.sidert.sidertmovil.v2.domain.entities;

import java.io.Serializable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tbl_solicitudes_auto")
public final class SolicitudesAuto implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_solicitud")
    private Long idSolicitud;
    @ColumnInfo(name = "solicitud")
    private String solicitud;
    @ColumnInfo(name = "asesor")
    private String asesor;
    @ColumnInfo(name = "tipo_solicitud")
    private String tipoSolicitud;
    @ColumnInfo(name = "id_originacion")
    private Integer idOriginacion;
    @ColumnInfo(name = "nombre")
    private String nombre;
    @ColumnInfo(name = "fecha_envio")
    private String fechaEnvio;
    @ColumnInfo(name = "estatus")
    private Integer estatus;


// Getters & Setters

    public Long getIdSolicitud(){
        return this.idSolicitud;
    }

    public void setIdSolicitud(Long idSolicitud){
        this.idSolicitud = idSolicitud;
    }
    public String getSolicitud(){
        return this.solicitud;
    }

    public void setSolicitud(String solicitud){
        this.solicitud = solicitud;
    }
    public String getAsesor(){
        return this.asesor;
    }

    public void setAsesor(String asesor){
        this.asesor = asesor;
    }
    public String getTipoSolicitud(){
        return this.tipoSolicitud;
    }

    public void setTipoSolicitud(String tipoSolicitud){
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
    public String getFechaEnvio(){
        return this.fechaEnvio;
    }

    public void setFechaEnvio(String fechaEnvio){
        this.fechaEnvio = fechaEnvio;
    }
    public Integer getEstatus(){
        return this.estatus;
    }

    public void setEstatus(Integer estatus){
        this.estatus = estatus;
    }

}