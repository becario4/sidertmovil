package com.sidert.sidertmovil.v2.domain.entities;

import java.io.Serializable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tbl_cancelaciones")
public final class Cancelaciones implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    private Long id;
    @ColumnInfo(name = "id_respuesta")
    private String idRespuesta;
    @ColumnInfo(name = "id_solicitud")
    private String idSolicitud;
    @ColumnInfo(name = "tipo_gestion")
    private Integer tipoGestion;
    @ColumnInfo(name = "tipo_prestamo")
    private String tipoPrestamo;
    @ColumnInfo(name = "comentario_asesor")
    private String comentarioAsesor;
    @ColumnInfo(name = "comentario_admin")
    private String comentarioAdmin;
    @ColumnInfo(name = "estatus")
    private String estatus;
    @ColumnInfo(name = "fecha_solicitud")
    private String fechaSolicitud;
    @ColumnInfo(name = "fecha_aplicacion")
    private String fechaAplicacion;


// Getters & Setters

    public Long getId(){
        return this.id;
    }

    public void setId(Long id){
        this.id = id;
    }
    public String getIdRespuesta(){
        return this.idRespuesta;
    }

    public void setIdRespuesta(String idRespuesta){
        this.idRespuesta = idRespuesta;
    }
    public String getIdSolicitud(){
        return this.idSolicitud;
    }

    public void setIdSolicitud(String idSolicitud){
        this.idSolicitud = idSolicitud;
    }
    public Integer getTipoGestion(){
        return this.tipoGestion;
    }

    public void setTipoGestion(Integer tipoGestion){
        this.tipoGestion = tipoGestion;
    }
    public String getTipoPrestamo(){
        return this.tipoPrestamo;
    }

    public void setTipoPrestamo(String tipoPrestamo){
        this.tipoPrestamo = tipoPrestamo;
    }
    public String getComentarioAsesor(){
        return this.comentarioAsesor;
    }

    public void setComentarioAsesor(String comentarioAsesor){
        this.comentarioAsesor = comentarioAsesor;
    }
    public String getComentarioAdmin(){
        return this.comentarioAdmin;
    }

    public void setComentarioAdmin(String comentarioAdmin){
        this.comentarioAdmin = comentarioAdmin;
    }
    public String getEstatus(){
        return this.estatus;
    }

    public void setEstatus(String estatus){
        this.estatus = estatus;
    }
    public String getFechaSolicitud(){
        return this.fechaSolicitud;
    }

    public void setFechaSolicitud(String fechaSolicitud){
        this.fechaSolicitud = fechaSolicitud;
    }
    public String getFechaAplicacion(){
        return this.fechaAplicacion;
    }

    public void setFechaAplicacion(String fechaAplicacion){
        this.fechaAplicacion = fechaAplicacion;
    }

}