package com.sidert.sidertmovil.v2.domain.entities;

import java.io.Serializable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tbl_codigos_oxxo")
public final class CodigosOxxo implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    private Long id;
    @ColumnInfo(name = "id")
    private String remoteId;
    @ColumnInfo(name = "id_asesor")
    private String idAsesor;
    @ColumnInfo(name = "num_prestamo")
    private String numPrestamo;
    @ColumnInfo(name = "fecha_amortiz")
    private String fechaAmortiz;
    @ColumnInfo(name = "monto_amortiz")
    private String montoAmortiz;
    @ColumnInfo(name = "nombre_pdf")
    private String nombrePdf;
    @ColumnInfo(name = "created_at")
    private String createdAt;
    @ColumnInfo(name = "fecha_vencimiento")
    private String fechaVencimiento;


// Getters & Setters

    public Long getId(){
        return this.id;
    }

    public void setId(Long id){
        this.id = id;
    }
    public String getRemoteId(){
        return this.remoteId;
    }

    public void setRemoteId(String remoteId){
        this.remoteId = remoteId;
    }
    public String getIdAsesor(){
        return this.idAsesor;
    }

    public void setIdAsesor(String idAsesor){
        this.idAsesor = idAsesor;
    }
    public String getNumPrestamo(){
        return this.numPrestamo;
    }

    public void setNumPrestamo(String numPrestamo){
        this.numPrestamo = numPrestamo;
    }
    public String getFechaAmortiz(){
        return this.fechaAmortiz;
    }

    public void setFechaAmortiz(String fechaAmortiz){
        this.fechaAmortiz = fechaAmortiz;
    }
    public String getMontoAmortiz(){
        return this.montoAmortiz;
    }

    public void setMontoAmortiz(String montoAmortiz){
        this.montoAmortiz = montoAmortiz;
    }
    public String getNombrePdf(){
        return this.nombrePdf;
    }

    public void setNombrePdf(String nombrePdf){
        this.nombrePdf = nombrePdf;
    }
    public String getCreatedAt(){
        return this.createdAt;
    }

    public void setCreatedAt(String createdAt){
        this.createdAt = createdAt;
    }
    public String getFechaVencimiento(){
        return this.fechaVencimiento;
    }

    public void setFechaVencimiento(String fechaVencimiento){
        this.fechaVencimiento = fechaVencimiento;
    }

}