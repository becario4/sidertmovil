package com.sidert.sidertmovil.v2.domain.entities;

import java.io.Serializable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tbl_impresiones_vigente_t")
public final class ImpresionesVigente implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    private Long id;
    @ColumnInfo(name = "num_prestamo_id_gestion")
    private String numPrestamoIdGestion;
    @ColumnInfo(name = "asesor_id")
    private String asesorId;
    @ColumnInfo(name = "folio")
    private Integer folio;
    @ColumnInfo(name = "tipo_impresion")
    private String tipoImpresion;
    @ColumnInfo(name = "monto")
    private String monto;
    @ColumnInfo(name = "clave_cliente")
    private String claveCliente;
    @ColumnInfo(name = "create_at")
    private String createAt;
    @ColumnInfo(name = "sent_at")
    private String sentAt;
    @ColumnInfo(name = "estatus")
    private Integer estatus;
    @ColumnInfo(name = "num_prestamo")
    private String numPrestamo;
    @ColumnInfo(name = "celular")
    private String celular;


// Getters & Setters

    public Long getId(){
        return this.id;
    }

    public void setId(Long id){
        this.id = id;
    }
    public String getNumPrestamoIdGestion(){
        return this.numPrestamoIdGestion;
    }

    public void setNumPrestamoIdGestion(String numPrestamoIdGestion){
        this.numPrestamoIdGestion = numPrestamoIdGestion;
    }
    public String getAsesorId(){
        return this.asesorId;
    }

    public void setAsesorId(String asesorId){
        this.asesorId = asesorId;
    }
    public Integer getFolio(){
        return this.folio;
    }

    public void setFolio(Integer folio){
        this.folio = folio;
    }
    public String getTipoImpresion(){
        return this.tipoImpresion;
    }

    public void setTipoImpresion(String tipoImpresion){
        this.tipoImpresion = tipoImpresion;
    }
    public String getMonto(){
        return this.monto;
    }

    public void setMonto(String monto){
        this.monto = monto;
    }
    public String getClaveCliente(){
        return this.claveCliente;
    }

    public void setClaveCliente(String claveCliente){
        this.claveCliente = claveCliente;
    }
    public String getCreateAt(){
        return this.createAt;
    }

    public void setCreateAt(String createAt){
        this.createAt = createAt;
    }
    public String getSentAt(){
        return this.sentAt;
    }

    public void setSentAt(String sentAt){
        this.sentAt = sentAt;
    }
    public Integer getEstatus(){
        return this.estatus;
    }

    public void setEstatus(Integer estatus){
        this.estatus = estatus;
    }
    public String getNumPrestamo(){
        return this.numPrestamo;
    }

    public void setNumPrestamo(String numPrestamo){
        this.numPrestamo = numPrestamo;
    }
    public String getCelular(){
        return this.celular;
    }

    public void setCelular(String celular){
        this.celular = celular;
    }

}