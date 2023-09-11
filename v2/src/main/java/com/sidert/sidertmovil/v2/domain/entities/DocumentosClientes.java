package com.sidert.sidertmovil.v2.domain.entities;

import java.io.Serializable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tbl_documentos_clientes")
public final class DocumentosClientes implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private Long remoteId;
    @ColumnInfo(name = "grupo_id")
    private Integer grupoId;
    @ColumnInfo(name = "cliente_id")
    private Integer clienteId;
    @ColumnInfo(name = "clavecliente")
    private String clavecliente;
    @ColumnInfo(name = "prestamo_id")
    private Integer prestamoId;
    @ColumnInfo(name = "num_solicitud")
    private Integer numSolicitud;
    @ColumnInfo(name = "fecha")
    private String fecha;
    @ColumnInfo(name = "tipo")
    private String tipo;
    @ColumnInfo(name = "archivo_base64")
    private String archivoBase64;


// Getters & Setters

    public Long getRemoteId(){
        return this.remoteId;
    }

    public void setRemoteId(Long remoteId){
        this.remoteId = remoteId;
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
    public String getClavecliente(){
        return this.clavecliente;
    }

    public void setClavecliente(String clavecliente){
        this.clavecliente = clavecliente;
    }
    public Integer getPrestamoId(){
        return this.prestamoId;
    }

    public void setPrestamoId(Integer prestamoId){
        this.prestamoId = prestamoId;
    }
    public Integer getNumSolicitud(){
        return this.numSolicitud;
    }

    public void setNumSolicitud(Integer numSolicitud){
        this.numSolicitud = numSolicitud;
    }
    public String getFecha(){
        return this.fecha;
    }

    public void setFecha(String fecha){
        this.fecha = fecha;
    }
    public String getTipo(){
        return this.tipo;
    }

    public void setTipo(String tipo){
        this.tipo = tipo;
    }
    public String getArchivoBase64(){
        return this.archivoBase64;
    }

    public void setArchivoBase64(String archivoBase64){
        this.archivoBase64 = archivoBase64;
    }

}