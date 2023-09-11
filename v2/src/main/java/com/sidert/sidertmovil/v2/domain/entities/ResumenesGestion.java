package com.sidert.sidertmovil.v2.domain.entities;

import java.io.Serializable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tbl_resumenes_gestion")
public final class ResumenesGestion implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    private Long id;
    @ColumnInfo(name = "id_respuesta")
    private String idRespuesta;
    @ColumnInfo(name = "nombre_resumen")
    private String nombreResumen;
    @ColumnInfo(name = "nombre_cliente")
    private String nombreCliente;
    @ColumnInfo(name = "tipo_gestion")
    private String tipoGestion;


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
    public String getNombreResumen(){
        return this.nombreResumen;
    }

    public void setNombreResumen(String nombreResumen){
        this.nombreResumen = nombreResumen;
    }
    public String getNombreCliente(){
        return this.nombreCliente;
    }

    public void setNombreCliente(String nombreCliente){
        this.nombreCliente = nombreCliente;
    }
    public String getTipoGestion(){
        return this.tipoGestion;
    }

    public void setTipoGestion(String tipoGestion){
        this.tipoGestion = tipoGestion;
    }

}