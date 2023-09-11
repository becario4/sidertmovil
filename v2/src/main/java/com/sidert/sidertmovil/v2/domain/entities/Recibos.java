package com.sidert.sidertmovil.v2.domain.entities;

import java.io.Serializable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tbl_recibos")
public final class Recibos implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    private Long id;
    @ColumnInfo(name = "prestamo_id",defaultValue="''")
    private String prestamoId;
    @ColumnInfo(name = "asesor_id")
    private String asesorId;
    @ColumnInfo(name = "tipo_recibo")
    private String tipoRecibo;
    @ColumnInfo(name = "tipo_impresion")
    private String tipoImpresion;
    @ColumnInfo(name = "folio")
    private String folio;
    @ColumnInfo(name = "monto")
    private String monto;
    @ColumnInfo(name = "clave",defaultValue="''")
    private String clave;
    @ColumnInfo(name = "nombre")
    private String nombre;
    @ColumnInfo(name = "ap_paterno")
    private String apPaterno;
    @ColumnInfo(name = "ap_materno")
    private String apMaterno;
    @ColumnInfo(name = "fecha_impreso")
    private String fechaImpreso;
    @ColumnInfo(name = "fecha_envio")
    private String fechaEnvio;
    @ColumnInfo(name = "estatus")
    private Integer estatus;
    @ColumnInfo(name = "curp",defaultValue="''")
    private String curp;


// Getters & Setters

    public Long getId(){
        return this.id;
    }

    public void setId(Long id){
        this.id = id;
    }
    public String getPrestamoId(){
        return this.prestamoId;
    }

    public void setPrestamoId(String prestamoId){
        this.prestamoId = prestamoId;
    }
    public String getAsesorId(){
        return this.asesorId;
    }

    public void setAsesorId(String asesorId){
        this.asesorId = asesorId;
    }
    public String getTipoRecibo(){
        return this.tipoRecibo;
    }

    public void setTipoRecibo(String tipoRecibo){
        this.tipoRecibo = tipoRecibo;
    }
    public String getTipoImpresion(){
        return this.tipoImpresion;
    }

    public void setTipoImpresion(String tipoImpresion){
        this.tipoImpresion = tipoImpresion;
    }
    public String getFolio(){
        return this.folio;
    }

    public void setFolio(String folio){
        this.folio = folio;
    }
    public String getMonto(){
        return this.monto;
    }

    public void setMonto(String monto){
        this.monto = monto;
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
    public String getFechaImpreso(){
        return this.fechaImpreso;
    }

    public void setFechaImpreso(String fechaImpreso){
        this.fechaImpreso = fechaImpreso;
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
    public String getCurp(){
        return this.curp;
    }

    public void setCurp(String curp){
        this.curp = curp;
    }

}