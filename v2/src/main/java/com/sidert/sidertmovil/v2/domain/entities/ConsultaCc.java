package com.sidert.sidertmovil.v2.domain.entities;

import java.io.Serializable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tbl_consulta_cc")
public final class ConsultaCc implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    private Long id;
    @ColumnInfo(name = "producto_credito")
    private String productoCredito;
    @ColumnInfo(name = "monto_solicitado")
    private String montoSolicitado;
    @ColumnInfo(name = "primer_nombre")
    private String primerNombre;
    @ColumnInfo(name = "segundo_nombre")
    private String segundoNombre;
    @ColumnInfo(name = "ap_paterno")
    private String apPaterno;
    @ColumnInfo(name = "ap_materno")
    private String apMaterno;
    @ColumnInfo(name = "fecha_nac")
    private String fechaNac;
    @ColumnInfo(name = "genero")
    private String genero;
    @ColumnInfo(name = "estado_nac")
    private String estadoNac;
    @ColumnInfo(name = "curp")
    private String curp;
    @ColumnInfo(name = "rfc")
    private String rfc;
    @ColumnInfo(name = "direccion")
    private String direccion;
    @ColumnInfo(name = "colonia")
    private String colonia;
    @ColumnInfo(name = "municipio")
    private String municipio;
    @ColumnInfo(name = "ciudad")
    private String ciudad;
    @ColumnInfo(name = "estado")
    private String estado;
    @ColumnInfo(name = "cp")
    private String cp;
    @ColumnInfo(name = "fecha_termino")
    private String fechaTermino;
    @ColumnInfo(name = "fecha_envio")
    private String fechaEnvio;
    @ColumnInfo(name = "saldo_actual")
    private String saldoActual;
    @ColumnInfo(name = "saldo_vencido")
    private String saldoVencido;
    @ColumnInfo(name = "peor_pago")
    private String peorPago;
    @ColumnInfo(name = "creditos_abiertos")
    private String creditosAbiertos;
    @ColumnInfo(name = "credito_cerrados")
    private String creditoCerrados;
    @ColumnInfo(name = "preautorizacion")
    private String preautorizacion;
    @ColumnInfo(name = "estatus")
    private String estatus;
    @ColumnInfo(name = "errores")
    private String errores;


// Getters & Setters

    public Long getId(){
        return this.id;
    }

    public void setId(Long id){
        this.id = id;
    }
    public String getProductoCredito(){
        return this.productoCredito;
    }

    public void setProductoCredito(String productoCredito){
        this.productoCredito = productoCredito;
    }
    public String getMontoSolicitado(){
        return this.montoSolicitado;
    }

    public void setMontoSolicitado(String montoSolicitado){
        this.montoSolicitado = montoSolicitado;
    }
    public String getPrimerNombre(){
        return this.primerNombre;
    }

    public void setPrimerNombre(String primerNombre){
        this.primerNombre = primerNombre;
    }
    public String getSegundoNombre(){
        return this.segundoNombre;
    }

    public void setSegundoNombre(String segundoNombre){
        this.segundoNombre = segundoNombre;
    }
    public String getApPaterno(){
        return this.apPaterno;
    }

    public void setApPaterno(String apPaterno){
        this.apPaterno = apPaterno;
    }
    public String getApMaterno(){
        return this.apMaterno;
    }

    public void setApMaterno(String apMaterno){
        this.apMaterno = apMaterno;
    }
    public String getFechaNac(){
        return this.fechaNac;
    }

    public void setFechaNac(String fechaNac){
        this.fechaNac = fechaNac;
    }
    public String getGenero(){
        return this.genero;
    }

    public void setGenero(String genero){
        this.genero = genero;
    }
    public String getEstadoNac(){
        return this.estadoNac;
    }

    public void setEstadoNac(String estadoNac){
        this.estadoNac = estadoNac;
    }
    public String getCurp(){
        return this.curp;
    }

    public void setCurp(String curp){
        this.curp = curp;
    }
    public String getRfc(){
        return this.rfc;
    }

    public void setRfc(String rfc){
        this.rfc = rfc;
    }
    public String getDireccion(){
        return this.direccion;
    }

    public void setDireccion(String direccion){
        this.direccion = direccion;
    }
    public String getColonia(){
        return this.colonia;
    }

    public void setColonia(String colonia){
        this.colonia = colonia;
    }
    public String getMunicipio(){
        return this.municipio;
    }

    public void setMunicipio(String municipio){
        this.municipio = municipio;
    }
    public String getCiudad(){
        return this.ciudad;
    }

    public void setCiudad(String ciudad){
        this.ciudad = ciudad;
    }
    public String getEstado(){
        return this.estado;
    }

    public void setEstado(String estado){
        this.estado = estado;
    }
    public String getCp(){
        return this.cp;
    }

    public void setCp(String cp){
        this.cp = cp;
    }
    public String getFechaTermino(){
        return this.fechaTermino;
    }

    public void setFechaTermino(String fechaTermino){
        this.fechaTermino = fechaTermino;
    }
    public String getFechaEnvio(){
        return this.fechaEnvio;
    }

    public void setFechaEnvio(String fechaEnvio){
        this.fechaEnvio = fechaEnvio;
    }
    public String getSaldoActual(){
        return this.saldoActual;
    }

    public void setSaldoActual(String saldoActual){
        this.saldoActual = saldoActual;
    }
    public String getSaldoVencido(){
        return this.saldoVencido;
    }

    public void setSaldoVencido(String saldoVencido){
        this.saldoVencido = saldoVencido;
    }
    public String getPeorPago(){
        return this.peorPago;
    }

    public void setPeorPago(String peorPago){
        this.peorPago = peorPago;
    }
    public String getCreditosAbiertos(){
        return this.creditosAbiertos;
    }

    public void setCreditosAbiertos(String creditosAbiertos){
        this.creditosAbiertos = creditosAbiertos;
    }
    public String getCreditoCerrados(){
        return this.creditoCerrados;
    }

    public void setCreditoCerrados(String creditoCerrados){
        this.creditoCerrados = creditoCerrados;
    }
    public String getPreautorizacion(){
        return this.preautorizacion;
    }

    public void setPreautorizacion(String preautorizacion){
        this.preautorizacion = preautorizacion;
    }
    public String getEstatus(){
        return this.estatus;
    }

    public void setEstatus(String estatus){
        this.estatus = estatus;
    }
    public String getErrores(){
        return this.errores;
    }

    public void setErrores(String errores){
        this.errores = errores;
    }

}