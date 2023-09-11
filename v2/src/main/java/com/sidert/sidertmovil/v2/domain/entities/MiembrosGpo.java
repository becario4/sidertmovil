package com.sidert.sidertmovil.v2.domain.entities;

import java.io.Serializable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tbl_miembros_gpo_t")
public final class MiembrosGpo implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    private Long id;
    @ColumnInfo(name = "id_prestamo")
    private String idPrestamo;
    @ColumnInfo(name = "id_integrante")
    private String idIntegrante;
    @ColumnInfo(name = "num_solicitud")
    private String numSolicitud;
    @ColumnInfo(name = "id_grupo")
    private String idGrupo;
    @ColumnInfo(name = "nombre")
    private String nombre;
    @ColumnInfo(name = "direccion")
    private String direccion;
    @ColumnInfo(name = "tel_casa")
    private String telCasa;
    @ColumnInfo(name = "tel_celular")
    private String telCelular;
    @ColumnInfo(name = "tipo_integrante")
    private String tipoIntegrante;
    @ColumnInfo(name = "monto_prestamo")
    private String montoPrestamo;
    @ColumnInfo(name = "monto_requerido")
    private String montoRequerido;
    @ColumnInfo(name = "fecha_dispositivo")
    private String fechaDispositivo;
    @ColumnInfo(name = "fecha_actualizado")
    private String fechaActualizado;
    @ColumnInfo(name = "clave")
    private String clave;
    @ColumnInfo(name = "id_prestamo_integrante")
    private String idPrestamoIntegrante;
    @ColumnInfo(name = "curp",defaultValue="''")
    private String curp;


// Getters & Setters

    public Long getId(){
        return this.id;
    }

    public void setId(Long id){
        this.id = id;
    }
    public String getIdPrestamo(){
        return this.idPrestamo;
    }

    public void setIdPrestamo(String idPrestamo){
        this.idPrestamo = idPrestamo;
    }
    public String getIdIntegrante(){
        return this.idIntegrante;
    }

    public void setIdIntegrante(String idIntegrante){
        this.idIntegrante = idIntegrante;
    }
    public String getNumSolicitud(){
        return this.numSolicitud;
    }

    public void setNumSolicitud(String numSolicitud){
        this.numSolicitud = numSolicitud;
    }
    public String getIdGrupo(){
        return this.idGrupo;
    }

    public void setIdGrupo(String idGrupo){
        this.idGrupo = idGrupo;
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
    public String getTelCasa(){
        return this.telCasa;
    }

    public void setTelCasa(String telCasa){
        this.telCasa = telCasa;
    }
    public String getTelCelular(){
        return this.telCelular;
    }

    public void setTelCelular(String telCelular){
        this.telCelular = telCelular;
    }
    public String getTipoIntegrante(){
        return this.tipoIntegrante;
    }

    public void setTipoIntegrante(String tipoIntegrante){
        this.tipoIntegrante = tipoIntegrante;
    }
    public String getMontoPrestamo(){
        return this.montoPrestamo;
    }

    public void setMontoPrestamo(String montoPrestamo){
        this.montoPrestamo = montoPrestamo;
    }
    public String getMontoRequerido(){
        return this.montoRequerido;
    }

    public void setMontoRequerido(String montoRequerido){
        this.montoRequerido = montoRequerido;
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
    public String getClave(){
        return this.clave;
    }

    public void setClave(String clave){
        this.clave = clave;
    }
    public String getIdPrestamoIntegrante(){
        return this.idPrestamoIntegrante;
    }

    public void setIdPrestamoIntegrante(String idPrestamoIntegrante){
        this.idPrestamoIntegrante = idPrestamoIntegrante;
    }
    public String getCurp(){
        return this.curp;
    }

    public void setCurp(String curp){
        this.curp = curp;
    }

}