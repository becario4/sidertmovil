package com.sidert.sidertmovil.v2.domain.entities;

import java.io.Serializable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tbl_recibos_cc")
public final class RecibosCc implements Serializable {

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
    @ColumnInfo(name = "total_integrantes")
    private Integer totalIntegrantes;
    @ColumnInfo(name = "monto")
    private String monto;
    @ColumnInfo(name = "tipo_impresion")
    private String tipoImpresion;
    @ColumnInfo(name = "folio")
    private Integer folio;
    @ColumnInfo(name = "fecha_impresion")
    private String fechaImpresion;
    @ColumnInfo(name = "fecha_envio")
    private String fechaEnvio;
    @ColumnInfo(name = "estatus")
    private Integer estatus;


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
    public Integer getTotalIntegrantes(){
        return this.totalIntegrantes;
    }

    public void setTotalIntegrantes(Integer totalIntegrantes){
        this.totalIntegrantes = totalIntegrantes;
    }
    public String getMonto(){
        return this.monto;
    }

    public void setMonto(String monto){
        this.monto = monto;
    }
    public String getTipoImpresion(){
        return this.tipoImpresion;
    }

    public void setTipoImpresion(String tipoImpresion){
        this.tipoImpresion = tipoImpresion;
    }
    public Integer getFolio(){
        return this.folio;
    }

    public void setFolio(Integer folio){
        this.folio = folio;
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

}