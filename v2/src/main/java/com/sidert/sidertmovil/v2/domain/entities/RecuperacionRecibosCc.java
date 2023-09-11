package com.sidert.sidertmovil.v2.domain.entities;

import java.io.Serializable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tbl_recuperacion_recibos_cc")
public final class RecuperacionRecibosCc implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    private Long id;
    @ColumnInfo(name = "tipo_credito")
    private Integer tipoCredito;
    @ColumnInfo(name = "nombre_uno")
    private String nombreUno;
    @ColumnInfo(name = "curp")
    private String curp;
    @ColumnInfo(name = "nombre_dos")
    private String nombreDos;
    @ColumnInfo(name = "integrantes")
    private Integer integrantes;
    @ColumnInfo(name = "monto")
    private String monto;
    @ColumnInfo(name = "medio_pago")
    private String medioPago;
    @ColumnInfo(name = "imprimir_recibo")
    private String imprimirRecibo;
    @ColumnInfo(name = "folio")
    private Integer folio;
    @ColumnInfo(name = "evidencia")
    private String evidencia;
    @ColumnInfo(name = "tipo_imagen")
    private String tipoImagen;
    @ColumnInfo(name = "fecha_termino")
    private String fechaTermino;
    @ColumnInfo(name = "fecha_envio")
    private String fechaEnvio;
    @ColumnInfo(name = "estatus")
    private Integer estatus;
    @ColumnInfo(name = "costo_consulta")
    private String costoConsulta;


// Getters & Setters

    public Long getId(){
        return this.id;
    }

    public void setId(Long id){
        this.id = id;
    }
    public Integer getTipoCredito(){
        return this.tipoCredito;
    }

    public void setTipoCredito(Integer tipoCredito){
        this.tipoCredito = tipoCredito;
    }
    public String getNombreUno(){
        return this.nombreUno;
    }

    public void setNombreUno(String nombreUno){
        this.nombreUno = nombreUno;
    }
    public String getCurp(){
        return this.curp;
    }

    public void setCurp(String curp){
        this.curp = curp;
    }
    public String getNombreDos(){
        return this.nombreDos;
    }

    public void setNombreDos(String nombreDos){
        this.nombreDos = nombreDos;
    }
    public Integer getIntegrantes(){
        return this.integrantes;
    }

    public void setIntegrantes(Integer integrantes){
        this.integrantes = integrantes;
    }
    public String getMonto(){
        return this.monto;
    }

    public void setMonto(String monto){
        this.monto = monto;
    }
    public String getMedioPago(){
        return this.medioPago;
    }

    public void setMedioPago(String medioPago){
        this.medioPago = medioPago;
    }
    public String getImprimirRecibo(){
        return this.imprimirRecibo;
    }

    public void setImprimirRecibo(String imprimirRecibo){
        this.imprimirRecibo = imprimirRecibo;
    }
    public Integer getFolio(){
        return this.folio;
    }

    public void setFolio(Integer folio){
        this.folio = folio;
    }
    public String getEvidencia(){
        return this.evidencia;
    }

    public void setEvidencia(String evidencia){
        this.evidencia = evidencia;
    }
    public String getTipoImagen(){
        return this.tipoImagen;
    }

    public void setTipoImagen(String tipoImagen){
        this.tipoImagen = tipoImagen;
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
    public Integer getEstatus(){
        return this.estatus;
    }

    public void setEstatus(Integer estatus){
        this.estatus = estatus;
    }
    public String getCostoConsulta(){
        return this.costoConsulta;
    }

    public void setCostoConsulta(String costoConsulta){
        this.costoConsulta = costoConsulta;
    }

}