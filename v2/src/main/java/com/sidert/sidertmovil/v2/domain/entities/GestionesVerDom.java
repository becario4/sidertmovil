package com.sidert.sidertmovil.v2.domain.entities;

import java.io.Serializable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tbl_gestiones_ver_dom")
public final class GestionesVerDom implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    private Long id;
    @ColumnInfo(name = "verificacion_domiciliaria_id")
    private Integer verificacionDomiciliariaId;
    @ColumnInfo(name = "latitud")
    private String latitud;
    @ColumnInfo(name = "longitud")
    private String longitud;
    @ColumnInfo(name = "foto_fachada")
    private String fotoFachada;
    @ColumnInfo(name = "domicilio_coincide")
    private Integer domicilioCoincide;
    @ColumnInfo(name = "comentario")
    private String comentario;
    @ColumnInfo(name = "estatus")
    private Integer estatus;
    @ColumnInfo(name = "usuario_id")
    private Integer usuarioId;
    @ColumnInfo(name = "usuario_nombre")
    private String usuarioNombre;
    @ColumnInfo(name = "fecha_inicio")
    private String fechaInicio;
    @ColumnInfo(name = "fecha_fin")
    private String fechaFin;
    @ColumnInfo(name = "fecha_envio")
    private String fechaEnvio;
    @ColumnInfo(name = "created_at")
    private String createdAt;


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
    public String getLatitud(){
        return this.latitud;
    }

    public void setLatitud(String latitud){
        this.latitud = latitud;
    }
    public String getLongitud(){
        return this.longitud;
    }

    public void setLongitud(String longitud){
        this.longitud = longitud;
    }
    public String getFotoFachada(){
        return this.fotoFachada;
    }

    public void setFotoFachada(String fotoFachada){
        this.fotoFachada = fotoFachada;
    }
    public Integer getDomicilioCoincide(){
        return this.domicilioCoincide;
    }

    public void setDomicilioCoincide(Integer domicilioCoincide){
        this.domicilioCoincide = domicilioCoincide;
    }
    public String getComentario(){
        return this.comentario;
    }

    public void setComentario(String comentario){
        this.comentario = comentario;
    }
    public Integer getEstatus(){
        return this.estatus;
    }

    public void setEstatus(Integer estatus){
        this.estatus = estatus;
    }
    public Integer getUsuarioId(){
        return this.usuarioId;
    }

    public void setUsuarioId(Integer usuarioId){
        this.usuarioId = usuarioId;
    }
    public String getUsuarioNombre(){
        return this.usuarioNombre;
    }

    public void setUsuarioNombre(String usuarioNombre){
        this.usuarioNombre = usuarioNombre;
    }
    public String getFechaInicio(){
        return this.fechaInicio;
    }

    public void setFechaInicio(String fechaInicio){
        this.fechaInicio = fechaInicio;
    }
    public String getFechaFin(){
        return this.fechaFin;
    }

    public void setFechaFin(String fechaFin){
        this.fechaFin = fechaFin;
    }
    public String getFechaEnvio(){
        return this.fechaEnvio;
    }

    public void setFechaEnvio(String fechaEnvio){
        this.fechaEnvio = fechaEnvio;
    }
    public String getCreatedAt(){
        return this.createdAt;
    }

    public void setCreatedAt(String createdAt){
        this.createdAt = createdAt;
    }

}