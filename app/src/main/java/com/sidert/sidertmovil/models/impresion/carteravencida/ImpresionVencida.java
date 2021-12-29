package com.sidert.sidertmovil.models.impresion.carteravencida;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ImpresionVencida implements Serializable {
    @SerializedName("_id")
    @Expose
    private Integer id;

    @SerializedName("num_prestamo_id_gestion")
    @Expose
    private String numPrestamoIdGestion;

    @SerializedName("asesor_id")
    @Expose
    private String asesorId;

    @SerializedName("folio")
    @Expose
    private Integer folio;

    @SerializedName("tipo_impresion")
    @Expose
    private String tipoImpresion;

    @SerializedName("monto")
    @Expose
    private String monto;

    @SerializedName("clave_cliente")
    @Expose
    private String claveCliente;

    @SerializedName("create_at")
    @Expose
    private String createAt;

    @SerializedName("sent_at")
    @Expose
    private String sentAt;

    @SerializedName("estatus")
    @Expose
    private Integer estatus;

    @SerializedName("num_prestamo")
    @Expose
    private String numPrestamo;

    @SerializedName("celular")
    @Expose
    private String celular;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumPrestamoIdGestion() {
        return numPrestamoIdGestion;
    }

    public void setNumPrestamoIdGestion(String numPrestamoIdGestion) {
        this.numPrestamoIdGestion = numPrestamoIdGestion;
    }

    public String getAsesorId() {
        return asesorId;
    }

    public void setAsesorId(String asesorId) {
        this.asesorId = asesorId;
    }

    public Integer getFolio() {
        return folio;
    }

    public void setFolio(Integer folio) {
        this.folio = folio;
    }

    public String getTipoImpresion() {
        return tipoImpresion;
    }

    public void setTipoImpresion(String tipoImpresion) {
        this.tipoImpresion = tipoImpresion;
    }

    public String getMonto() {
        return monto;
    }

    public void setMonto(String monto) {
        this.monto = monto;
    }

    public String getClaveCliente() {
        return claveCliente;
    }

    public void setClaveCliente(String claveCliente) {
        this.claveCliente = claveCliente;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public String getSentAt() {
        return sentAt;
    }

    public void setSentAt(String sentAt) {
        this.sentAt = sentAt;
    }

    public Integer getEstatus() {
        return estatus;
    }

    public void setEstatus(Integer estatus) {
        this.estatus = estatus;
    }

    public String getNumPrestamo() {
        return numPrestamo;
    }

    public void setNumPrestamo(String numPrestamo) {
        this.numPrestamo = numPrestamo;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }
}
