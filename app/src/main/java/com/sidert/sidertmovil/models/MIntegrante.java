package com.sidert.sidertmovil.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MIntegrante implements Serializable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("num_solicitud")
    @Expose
    private Integer numSolicitud;

    @SerializedName("prestamo_id")
    @Expose
    private Integer prestamoId;

    @SerializedName("grupo_id")
    @Expose
    private Integer grupoId;
    @SerializedName("nombre")
    @Expose
    private String nombre;
    @SerializedName("tipo")
    @Expose
    private String tipo;
    @SerializedName("direccion")
    @Expose
    private String direccion;
    @SerializedName("tel_celular")
    @Expose
    private String telCelular;
    @SerializedName("tel_casa")
    @Expose
    private String telCasa;
    @SerializedName("monto_requerido")
    @Expose
    private String montoRequerido;
    @SerializedName("monto_prestamo")
    @Expose
    private String montoPrestamo;
    @SerializedName("clave")
    @Expose
    private String clave;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNumSolicitud() {
        return numSolicitud;
    }

    public void setNumSolicitud(Integer numSolicitud) {
        this.numSolicitud = numSolicitud;
    }

    public Integer getPrestamoId() {
        return prestamoId;
    }

    public void setPrestamoId(Integer prestamoId) {
        this.prestamoId = prestamoId;
    }

    public Integer getGrupoId() {
        return grupoId;
    }

    public void setGrupoId(Integer grupoId) {
        this.grupoId = grupoId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelCelular() {
        return telCelular;
    }

    public void setTelCelular(String telCelular) {
        this.telCelular = telCelular;
    }

    public String getTelCasa() {
        return telCasa;
    }

    public void setTelCasa(String telCasa) {
        this.telCasa = telCasa;
    }

    public String getMontoRequerido() {
        return montoRequerido;
    }

    public void setMontoRequerido(String montoRequerido) {
        this.montoRequerido = montoRequerido;
    }

    public String getMontoPrestamo() {
        return montoPrestamo;
    }

    public void setMontoPrestamo(String montoPrestamo) {
        this.montoPrestamo = montoPrestamo;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }
}
