package com.sidert.sidertmovil.models.circulocredito;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ReciboCirculoCredito implements Serializable {
    private Integer id;

    @SerializedName("tipo_credito")
    @Expose
    private Integer tipoCredito;

    @SerializedName("nombre_uno")
    @Expose
    private String nombreUno;

    @SerializedName("curp")
    @Expose
    private String curp;

    @SerializedName("nombre_dos")
    @Expose
    private String nombreDos;

    @SerializedName("total_integrantes")
    @Expose
    private Integer totalIntegrantes;

    @SerializedName("monto")
    @Expose
    private String monto;

    @SerializedName("tipo_impresion")
    @Expose
    private String tipoImpresion;

    @SerializedName("folio")
    @Expose
    private Integer folio;

    @SerializedName("fecha_impresion")
    @Expose
    private String fechaImpresion;

    @SerializedName("fecha_envio")
    @Expose
    private String fechaEnvio;

    @SerializedName("estatus")
    @Expose
    private Integer estatus;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTipoCredito() {
        return tipoCredito;
    }

    public void setTipoCredito(Integer tipoCredito) {
        this.tipoCredito = tipoCredito;
    }

    public String getNombreUno() {
        return nombreUno;
    }

    public void setNombreUno(String nombreUno) {
        this.nombreUno = nombreUno;
    }

    public String getCurp() {
        return curp;
    }

    public void setCurp(String curp) {
        this.curp = curp;
    }

    public String getNombreDos() {
        return nombreDos;
    }

    public void setNombreDos(String nombreDos) {
        this.nombreDos = nombreDos;
    }

    public Integer getTotalIntegrantes() {
        return totalIntegrantes;
    }

    public void setTotalIntegrantes(Integer totalIntegrantes) {
        this.totalIntegrantes = totalIntegrantes;
    }

    public String getMonto() {
        return monto;
    }

    public void setMonto(String monto) {
        this.monto = monto;
    }

    public String getTipoImpresion() {
        return tipoImpresion;
    }

    public void setTipoImpresion(String tipoImpresion) {
        this.tipoImpresion = tipoImpresion;
    }

    public Integer getFolio() {
        return folio;
    }

    public void setFolio(Integer folio) {
        this.folio = folio;
    }

    public String getFechaImpresion() {
        return fechaImpresion;
    }

    public void setFechaImpresion(String fechaImpresion) {
        this.fechaImpresion = fechaImpresion;
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
}
