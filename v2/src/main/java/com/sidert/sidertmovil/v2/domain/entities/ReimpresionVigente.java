package com.sidert.sidertmovil.v2.domain.entities;

import java.io.Serializable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tbl_reimpresion_vigente_t")
public final class ReimpresionVigente implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    private Long id;
    @ColumnInfo(name = "num_prestamo_id_gestion")
    private String numPrestamoIdGestion;
    @ColumnInfo(name = "tipo_reimpresion")
    private String tipoReimpresion;
    @ColumnInfo(name = "folio")
    private Integer folio;
    @ColumnInfo(name = "monto")
    private String monto;
    @ColumnInfo(name = "clv_cliente")
    private String clvCliente;
    @ColumnInfo(name = "asesor_id")
    private String asesorId;
    @ColumnInfo(name = "serie_id")
    private String serieId;
    @ColumnInfo(name = "create_at")
    private String createAt;
    @ColumnInfo(name = "sent_at")
    private String sentAt;
    @ColumnInfo(name = "estatus")
    private Integer estatus;
    @ColumnInfo(name = "num_prestamo")
    private Integer numPrestamo;
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
    public String getTipoReimpresion(){
        return this.tipoReimpresion;
    }

    public void setTipoReimpresion(String tipoReimpresion){
        this.tipoReimpresion = tipoReimpresion;
    }
    public Integer getFolio(){
        return this.folio;
    }

    public void setFolio(Integer folio){
        this.folio = folio;
    }
    public String getMonto(){
        return this.monto;
    }

    public void setMonto(String monto){
        this.monto = monto;
    }
    public String getClvCliente(){
        return this.clvCliente;
    }

    public void setClvCliente(String clvCliente){
        this.clvCliente = clvCliente;
    }
    public String getAsesorId(){
        return this.asesorId;
    }

    public void setAsesorId(String asesorId){
        this.asesorId = asesorId;
    }
    public String getSerieId(){
        return this.serieId;
    }

    public void setSerieId(String serieId){
        this.serieId = serieId;
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
    public Integer getNumPrestamo(){
        return this.numPrestamo;
    }

    public void setNumPrestamo(Integer numPrestamo){
        this.numPrestamo = numPrestamo;
    }
    public String getCelular(){
        return this.celular;
    }

    public void setCelular(String celular){
        this.celular = celular;
    }

}