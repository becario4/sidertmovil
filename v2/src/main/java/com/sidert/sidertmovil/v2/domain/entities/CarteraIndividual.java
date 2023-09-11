package com.sidert.sidertmovil.v2.domain.entities;

import java.io.Serializable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "cartera_individual_t")
public final class CarteraIndividual implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    private Long id;
    @ColumnInfo(name = "id_cartera")
    private String idCartera;
    @ColumnInfo(name = "clave")
    private String clave;
    @ColumnInfo(name = "nombre")
    private String nombre;
    @ColumnInfo(name = "direccion")
    private String direccion;
    @ColumnInfo(name = "asesor_nombre")
    private String asesorNombre;
    @ColumnInfo(name = "serie_id")
    private String serieId;
    @ColumnInfo(name = "is_ruta")
    private Integer isRuta;
    @ColumnInfo(name = "ruta_obligado")
    private Integer rutaObligado;
    @ColumnInfo(name = "usuario_id")
    private String usuarioId;
    @ColumnInfo(name = "dia")
    private String dia;
    @ColumnInfo(name = "num_solicitud")
    private String numSolicitud;
    @ColumnInfo(name = "fecha_dispositivo")
    private String fechaDispositivo;
    @ColumnInfo(name = "fecha_actualizado")
    private String fechaActualizado;
    @ColumnInfo(name = "colonia")
    private String colonia;
    @ColumnInfo(name = "geo_cliente")
    private Integer geoCliente;
    @ColumnInfo(name = "geo_aval")
    private Integer geoAval;
    @ColumnInfo(name = "geo_negocio")
    private Integer geoNegocio;
    @ColumnInfo(name = "estatus",defaultValue="'1'")
    private String estatus;
    @ColumnInfo(name = "cc",defaultValue="''")
    private String cc;
    @ColumnInfo(name = "agf",defaultValue="''")
    private String agf;
    @ColumnInfo(name = "curp",defaultValue="''")
    private String curp;
    @ColumnInfo(name = "dias_atraso",defaultValue="'0'")
    private String diasAtraso;


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
    public String getClave(){
        return this.clave;
    }

    public void setClave(String clave){
        this.clave = clave;
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
    public String getAsesorNombre(){
        return this.asesorNombre;
    }

    public void setAsesorNombre(String asesorNombre){
        this.asesorNombre = asesorNombre;
    }
    public String getSerieId(){
        return this.serieId;
    }

    public void setSerieId(String serieId){
        this.serieId = serieId;
    }
    public Integer getIsRuta(){
        return this.isRuta;
    }

    public void setIsRuta(Integer isRuta){
        this.isRuta = isRuta;
    }
    public Integer getRutaObligado(){
        return this.rutaObligado;
    }

    public void setRutaObligado(Integer rutaObligado){
        this.rutaObligado = rutaObligado;
    }
    public String getUsuarioId(){
        return this.usuarioId;
    }

    public void setUsuarioId(String usuarioId){
        this.usuarioId = usuarioId;
    }
    public String getDia(){
        return this.dia;
    }

    public void setDia(String dia){
        this.dia = dia;
    }
    public String getNumSolicitud(){
        return this.numSolicitud;
    }

    public void setNumSolicitud(String numSolicitud){
        this.numSolicitud = numSolicitud;
    }
    public String getFechaDispositivo(){
        return this.fechaDispositivo;
    }

    public void setFechaDispositivo(String fechaDispositivo){
        this.fechaDispositivo = fechaDispositivo;
    }
    public String getFechaActualizado(){
        return this.fechaActualizado;
    }

    public void setFechaActualizado(String fechaActualizado){
        this.fechaActualizado = fechaActualizado;
    }
    public String getColonia(){
        return this.colonia;
    }

    public void setColonia(String colonia){
        this.colonia = colonia;
    }
    public Integer getGeoCliente(){
        return this.geoCliente;
    }

    public void setGeoCliente(Integer geoCliente){
        this.geoCliente = geoCliente;
    }
    public Integer getGeoAval(){
        return this.geoAval;
    }

    public void setGeoAval(Integer geoAval){
        this.geoAval = geoAval;
    }
    public Integer getGeoNegocio(){
        return this.geoNegocio;
    }

    public void setGeoNegocio(Integer geoNegocio){
        this.geoNegocio = geoNegocio;
    }
    public String getEstatus(){
        return this.estatus;
    }

    public void setEstatus(String estatus){
        this.estatus = estatus;
    }
    public String getCc(){
        return this.cc;
    }

    public void setCc(String cc){
        this.cc = cc;
    }
    public String getAgf(){
        return this.agf;
    }

    public void setAgf(String agf){
        this.agf = agf;
    }
    public String getCurp(){
        return this.curp;
    }

    public void setCurp(String curp){
        this.curp = curp;
    }
    public String getDiasAtraso(){
        return this.diasAtraso;
    }

    public void setDiasAtraso(String diasAtraso){
        this.diasAtraso = diasAtraso;
    }

}