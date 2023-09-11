package com.sidert.sidertmovil.v2.domain.entities;

import java.io.Serializable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "geo")
public final class Geo implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    private Long id;
    @ColumnInfo(name = "id_cartera")
    private String idCartera;
    @ColumnInfo(name = "num_solicitud")
    private String numSolicitud;
    @ColumnInfo(name = "tipo_ficha")
    private Integer tipoFicha;
    @ColumnInfo(name = "tipo_geolocalizacion")
    private String tipoGeolocalizacion;
    @ColumnInfo(name = "id_integrante")
    private String idIntegrante;
    @ColumnInfo(name = "nombre")
    private String nombre;
    @ColumnInfo(name = "direccion")
    private String direccion;
    @ColumnInfo(name = "latitud")
    private String latitud;
    @ColumnInfo(name = "longitud")
    private String longitud;
    @ColumnInfo(name = "direccion_capturada")
    private String direccionCapturada;
    @ColumnInfo(name = "codigo_barras")
    private String codigoBarras;
    @ColumnInfo(name = "fachada")
    private String fachada;
    @ColumnInfo(name = "comentario")
    private String comentario;
    @ColumnInfo(name = "fecha_fin_geo")
    private String fechaFinGeo;
    @ColumnInfo(name = "fecha_envio_geo")
    private String fechaEnvioGeo;
    @ColumnInfo(name = "estatus")
    private Integer estatus;
    @ColumnInfo(name = "clave")
    private String clave;
    @ColumnInfo(name = "id_tipocartera",defaultValue="'0'")
    private String idTipocartera;


// Getters & Setters

    public Long getId(){
        return this.id;
    }

    public void setId(Long id){
        this.id = id;
    }
    public String getIdCartera(){
        return this.idCartera;
    }

    public void setIdCartera(String idCartera){
        this.idCartera = idCartera;
    }
    public String getNumSolicitud(){
        return this.numSolicitud;
    }

    public void setNumSolicitud(String numSolicitud){
        this.numSolicitud = numSolicitud;
    }
    public Integer getTipoFicha(){
        return this.tipoFicha;
    }

    public void setTipoFicha(Integer tipoFicha){
        this.tipoFicha = tipoFicha;
    }
    public String getTipoGeolocalizacion(){
        return this.tipoGeolocalizacion;
    }

    public void setTipoGeolocalizacion(String tipoGeolocalizacion){
        this.tipoGeolocalizacion = tipoGeolocalizacion;
    }
    public String getIdIntegrante(){
        return this.idIntegrante;
    }

    public void setIdIntegrante(String idIntegrante){
        this.idIntegrante = idIntegrante;
    }
    public String getNombre(){
        return this.nombre;
    }

    public void setNombre(String nombre){
        this.nombre = nombre;
    }
    public String getDireccion(){
        return this.direccion;
    }

    public void setDireccion(String direccion){
        this.direccion = direccion;
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
    public String getDireccionCapturada(){
        return this.direccionCapturada;
    }

    public void setDireccionCapturada(String direccionCapturada){
        this.direccionCapturada = direccionCapturada;
    }
    public String getCodigoBarras(){
        return this.codigoBarras;
    }

    public void setCodigoBarras(String codigoBarras){
        this.codigoBarras = codigoBarras;
    }
    public String getFachada(){
        return this.fachada;
    }

    public void setFachada(String fachada){
        this.fachada = fachada;
    }
    public String getComentario(){
        return this.comentario;
    }

    public void setComentario(String comentario){
        this.comentario = comentario;
    }
    public String getFechaFinGeo(){
        return this.fechaFinGeo;
    }

    public void setFechaFinGeo(String fechaFinGeo){
        this.fechaFinGeo = fechaFinGeo;
    }
    public String getFechaEnvioGeo(){
        return this.fechaEnvioGeo;
    }

    public void setFechaEnvioGeo(String fechaEnvioGeo){
        this.fechaEnvioGeo = fechaEnvioGeo;
    }
    public Integer getEstatus(){
        return this.estatus;
    }

    public void setEstatus(Integer estatus){
        this.estatus = estatus;
    }
    public String getClave(){
        return this.clave;
    }

    public void setClave(String clave){
        this.clave = clave;
    }
    public String getIdTipocartera(){
        return this.idTipocartera;
    }

    public void setIdTipocartera(String idTipocartera){
        this.idTipocartera = idTipocartera;
    }

}