package com.sidert.sidertmovil.v2.domain.entities;

import java.io.Serializable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tbl_cierre_dia_t")
public final class CierreDia implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    private Long id;
    @ColumnInfo(name = "asesor_id")
    private String asesorId;
    @ColumnInfo(name = "id_respuesta")
    private String idRespuesta;
    @ColumnInfo(name = "num_prestamo")
    private String numPrestamo;
    @ColumnInfo(name = "clave_cliente")
    private String claveCliente;
    @ColumnInfo(name = "medio_pago")
    private String medioPago;
    @ColumnInfo(name = "monto_depositado")
    private String montoDepositado;
    @ColumnInfo(name = "evidencia")
    private String evidencia;
    @ColumnInfo(name = "tipo_cierre")
    private String tipoCierre;
    @ColumnInfo(name = "tipo_prestamo")
    private String tipoPrestamo;
    @ColumnInfo(name = "fecha_inicio")
    private String fechaInicio;
    @ColumnInfo(name = "fecha_fin")
    private String fechaFin;
    @ColumnInfo(name = "fecha_envio")
    private String fechaEnvio;
    @ColumnInfo(name = "estatus")
    private Integer estatus;
    @ColumnInfo(name = "nombre")
    private String nombre;
    @ColumnInfo(name = "serial_id")
    private String serialId;


// Getters & Setters

    public Long getId(){
        return this.id;
    }

    public void setId(Long id){
        this.id = id;
    }
    public String getAsesorId(){
        return this.asesorId;
    }

    public void setAsesorId(String asesorId){
        this.asesorId = asesorId;
    }
    public String getIdRespuesta(){
        return this.idRespuesta;
    }

    public void setIdRespuesta(String idRespuesta){
        this.idRespuesta = idRespuesta;
    }
    public String getNumPrestamo(){
        return this.numPrestamo;
    }

    public void setNumPrestamo(String numPrestamo){
        this.numPrestamo = numPrestamo;
    }
    public String getClaveCliente(){
        return this.claveCliente;
    }

    public void setClaveCliente(String claveCliente){
        this.claveCliente = claveCliente;
    }
    public String getMedioPago(){
        return this.medioPago;
    }

    public void setMedioPago(String medioPago){
        this.medioPago = medioPago;
    }
    public String getMontoDepositado(){
        return this.montoDepositado;
    }

    public void setMontoDepositado(String montoDepositado){
        this.montoDepositado = montoDepositado;
    }
    public String getEvidencia(){
        return this.evidencia;
    }

    public void setEvidencia(String evidencia){
        this.evidencia = evidencia;
    }
    public String getTipoCierre(){
        return this.tipoCierre;
    }

    public void setTipoCierre(String tipoCierre){
        this.tipoCierre = tipoCierre;
    }
    public String getTipoPrestamo(){
        return this.tipoPrestamo;
    }

    public void setTipoPrestamo(String tipoPrestamo){
        this.tipoPrestamo = tipoPrestamo;
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
    public String getSerialId(){
        return this.serialId;
    }

    public void setSerialId(String serialId){
        this.serialId = serialId;
    }

}