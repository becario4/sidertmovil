package com.sidert.sidertmovil.v2.domain.entities;

import java.io.Serializable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tbl_soporte")
public final class Soporte implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    private Long id;
    @ColumnInfo(name = "categoria_id")
    private Integer categoriaId;
    @ColumnInfo(name = "tipo_ficha")
    private Integer tipoFicha;
    @ColumnInfo(name = "nombre")
    private String nombre;
    @ColumnInfo(name = "grupo_id")
    private Integer grupoId;
    @ColumnInfo(name = "cliente_id")
    private Integer clienteId;
    @ColumnInfo(name = "num_solicitud")
    private Integer numSolicitud;
    @ColumnInfo(name = "folio_impresion")
    private Integer folioImpresion;
    @ColumnInfo(name = "comentario")
    private String comentario;
    @ColumnInfo(name = "folio_solicitud")
    private String folioSolicitud;
    @ColumnInfo(name = "turno")
    private Integer turno;
    @ColumnInfo(name = "comentario_admin")
    private String comentarioAdmin;
    @ColumnInfo(name = "fecha_solicitud")
    private String fechaSolicitud;
    @ColumnInfo(name = "fecha_envio")
    private String fechaEnvio;
    @ColumnInfo(name = "estatus_ticket")
    private String estatusTicket;
    @ColumnInfo(name = "estatus_envio")
    private Integer estatusEnvio;


// Getters & Setters

    public Long getId(){
        return this.id;
    }

    public void setId(Long id){
        this.id = id;
    }
    public Integer getCategoriaId(){
        return this.categoriaId;
    }

    public void setCategoriaId(Integer categoriaId){
        this.categoriaId = categoriaId;
    }
    public Integer getTipoFicha(){
        return this.tipoFicha;
    }

    public void setTipoFicha(Integer tipoFicha){
        this.tipoFicha = tipoFicha;
    }
    public String getNombre(){
        return this.nombre;
    }

    public void setNombre(String nombre){
        this.nombre = nombre;
    }
    public Integer getGrupoId(){
        return this.grupoId;
    }

    public void setGrupoId(Integer grupoId){
        this.grupoId = grupoId;
    }
    public Integer getClienteId(){
        return this.clienteId;
    }

    public void setClienteId(Integer clienteId){
        this.clienteId = clienteId;
    }
    public Integer getNumSolicitud(){
        return this.numSolicitud;
    }

    public void setNumSolicitud(Integer numSolicitud){
        this.numSolicitud = numSolicitud;
    }
    public Integer getFolioImpresion(){
        return this.folioImpresion;
    }

    public void setFolioImpresion(Integer folioImpresion){
        this.folioImpresion = folioImpresion;
    }
    public String getComentario(){
        return this.comentario;
    }

    public void setComentario(String comentario){
        this.comentario = comentario;
    }
    public String getFolioSolicitud(){
        return this.folioSolicitud;
    }

    public void setFolioSolicitud(String folioSolicitud){
        this.folioSolicitud = folioSolicitud;
    }
    public Integer getTurno(){
        return this.turno;
    }

    public void setTurno(Integer turno){
        this.turno = turno;
    }
    public String getComentarioAdmin(){
        return this.comentarioAdmin;
    }

    public void setComentarioAdmin(String comentarioAdmin){
        this.comentarioAdmin = comentarioAdmin;
    }
    public String getFechaSolicitud(){
        return this.fechaSolicitud;
    }

    public void setFechaSolicitud(String fechaSolicitud){
        this.fechaSolicitud = fechaSolicitud;
    }
    public String getFechaEnvio(){
        return this.fechaEnvio;
    }

    public void setFechaEnvio(String fechaEnvio){
        this.fechaEnvio = fechaEnvio;
    }
    public String getEstatusTicket(){
        return this.estatusTicket;
    }

    public void setEstatusTicket(String estatusTicket){
        this.estatusTicket = estatusTicket;
    }
    public Integer getEstatusEnvio(){
        return this.estatusEnvio;
    }

    public void setEstatusEnvio(Integer estatusEnvio){
        this.estatusEnvio = estatusEnvio;
    }

}