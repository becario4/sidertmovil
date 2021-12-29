package com.sidert.sidertmovil.models.cierrededia;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CierreDeDia implements Serializable {
    @SerializedName("_id")
    @Expose
    private Integer id;

    @SerializedName("asesor_id")
    @Expose
    private String asesorId;

    @SerializedName("id_respuesta")
    @Expose
    private String idRespuesta;

    @SerializedName("num_prestamo")
    @Expose
    private String numPrestamo;

    @SerializedName("clave_cliente")
    @Expose
    private String claveCliente;

    @SerializedName("medio_pago")
    @Expose
    private String medioPago;

    @SerializedName("monto_depositado")
    @Expose
    private String montoDepositado;

    @SerializedName("evidencia")
    @Expose
    private String evidencia;

    @SerializedName("tipo_cierre")
    @Expose
    private String tipoCierre;

    @SerializedName("tipo_prestamo")
    @Expose
    private String tipoPrestamo;

    @SerializedName("fecha_inicio")
    @Expose
    private String fechaInicio;

    @SerializedName("fecha_fin")
    @Expose
    private String fechaFin;

    @SerializedName("fecha_envio")
    @Expose
    private String fechaEnvio;

    @SerializedName("estatus")
    @Expose
    private Integer estatus;

    @SerializedName("nombre")
    @Expose
    private String nombre;

    @SerializedName("serial_id")
    @Expose
    private String serialId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAsesorId() {
        return asesorId;
    }

    public void setAsesorId(String asesorId) {
        this.asesorId = asesorId;
    }

    public String getIdRespuesta() {
        return idRespuesta;
    }

    public void setIdRespuesta(String idRespuesta) {
        this.idRespuesta = idRespuesta;
    }

    public String getNumPrestamo() {
        return numPrestamo;
    }

    public void setNumPrestamo(String numPrestamo) {
        this.numPrestamo = numPrestamo;
    }

    public String getClaveCliente() {
        return claveCliente;
    }

    public void setClaveCliente(String claveCliente) {
        this.claveCliente = claveCliente;
    }

    public String getMedioPago() {
        return medioPago;
    }

    public void setMedioPago(String medioPago) {
        this.medioPago = medioPago;
    }

    public String getMontoDepositado() {
        return montoDepositado;
    }

    public void setMontoDepositado(String montoDepositado) {
        this.montoDepositado = montoDepositado;
    }

    public String getEvidencia() {
        return evidencia;
    }

    public void setEvidencia(String evidencia) {
        this.evidencia = evidencia;
    }

    public String getTipoCierre() {
        return tipoCierre;
    }

    public void setTipoCierre(String tipoCierre) {
        this.tipoCierre = tipoCierre;
    }

    public String getTipoPrestamo() {
        return tipoPrestamo;
    }

    public void setTipoPrestamo(String tipoPrestamo) {
        this.tipoPrestamo = tipoPrestamo;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getFechaEnvio() {
        return fechaEnvio;
    }

    public void setFechaEnvio(String fechaEnvio) {
        this.fechaEnvio = fechaEnvio;
    }

    public Integer getEstatus() {
        return estatus;
    }

    public void setEstatus(Integer estatus) {
        this.estatus = estatus;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getSerialId() {
        return serialId;
    }

    public void setSerialId(String serialId) {
        this.serialId = serialId;
    }
}
