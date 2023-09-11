package com.sidert.sidertmovil.v2.domain.entities;

import java.io.Serializable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tbl_recibos_agf_cc")
public final class RecibosAgfCc implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    private Long id;
    @ColumnInfo(name = "grupo_id")
    private String grupoId;
    @ColumnInfo(name = "num_solicitud")
    private String numSolicitud;
    @ColumnInfo(name = "monto")
    private String monto;
    @ColumnInfo(name = "folio")
    private String folio;
    @ColumnInfo(name = "tipo_recibo")
    private String tipoRecibo;
    @ColumnInfo(name = "tipo_impresion")
    private String tipoImpresion;
    @ColumnInfo(name = "fecha_impresion")
    private String fechaImpresion;
    @ColumnInfo(name = "fecha_envio")
    private String fechaEnvio;
    @ColumnInfo(name = "estatus")
    private Integer estatus;
    @ColumnInfo(name = "nombre")
    private String nombre;
    @ColumnInfo(name = "plazo")
    private Integer plazo;


// Getters & Setters

    public Long getId(){
        return this.id;
    }

    public void setId(Long id){
        this.id = id;
    }
    public String getGrupoId(){
        return this.grupoId;
    }

    public void setGrupoId(String grupoId){
        this.grupoId = grupoId;
    }
    public String getNumSolicitud(){
        return this.numSolicitud;
    }

    public void setNumSolicitud(String numSolicitud){
        this.numSolicitud = numSolicitud;
    }
    public String getMonto(){
        return this.monto;
    }

    public void setMonto(String monto){
        this.monto = monto;
    }
    public String getFolio(){
        return this.folio;
    }

    public void setFolio(String folio){
        this.folio = folio;
    }
    public String getTipoRecibo(){
        return this.tipoRecibo;
    }

    public void setTipoRecibo(String tipoRecibo){
        this.tipoRecibo = tipoRecibo;
    }
    public String getTipoImpresion(){
        return this.tipoImpresion;
    }

    public void setTipoImpresion(String tipoImpresion){
        this.tipoImpresion = tipoImpresion;
    }
    public String getFechaImpresion(){
        return this.fechaImpresion;
    }

    public void setFechaImpresion(String fechaImpresion){
        this.fechaImpresion = fechaImpresion;
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
    public String getNombre(){
        return this.nombre;
    }

    public void setNombre(String nombre){
        this.nombre = nombre;
    }
    public Integer getPlazo(){
        return this.plazo;
    }

    public void setPlazo(Integer plazo){
        this.plazo = plazo;
    }

}