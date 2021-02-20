package com.sidert.sidertmovil.models.circulocredito;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class GestionCirculoCredito implements Serializable {
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

    @SerializedName("integrantes")
    @Expose
    private Integer integrantes;

    @SerializedName("monto")
    @Expose
    private String monto;

    @SerializedName("medio_pago")
    @Expose
    private String medioPago;

    @SerializedName("imprimir_recibo")
    @Expose
    private String imprimirRecibo;

    @SerializedName("folio")
    @Expose
    private Integer folio;

    @SerializedName("evidencia")
    @Expose
    private String evidencia;

    @SerializedName("tipo_imagen")
    @Expose
    private String tipoImagen;

    @SerializedName("fecha_termino")
    @Expose
    private String fechaTermino;

    @SerializedName("fecha_envio")
    @Expose
    private String fechaEnvio;

    @SerializedName("estatus")
    @Expose
    private Integer estatus;

    @SerializedName("costo_consulta")
    @Expose
    private String costoConsulta;

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

    public Integer getIntegrantes() {
        return integrantes;
    }

    public void setIntegrantes(Integer integrantes) {
        this.integrantes = integrantes;
    }

    public String getMonto() {
        return monto;
    }

    public void setMonto(String monto) {
        this.monto = monto;
    }

    public String getMedioPago() {
        return medioPago;
    }

    public void setMedioPago(String medioPago) {
        this.medioPago = medioPago;
    }

    public String getImprimirRecibo() {
        return imprimirRecibo;
    }

    public void setImprimirRecibo(String imprimirRecibo) {
        this.imprimirRecibo = imprimirRecibo;
    }

    public Integer getFolio() {
        return folio;
    }

    public void setFolio(Integer folio) {
        this.folio = folio;
    }

    public String getEvidencia() {
        return evidencia;
    }

    public void setEvidencia(String evidencia) {
        this.evidencia = evidencia;
    }

    public String getTipoImagen() {
        return tipoImagen;
    }

    public void setTipoImagen(String tipoImagen) {
        this.tipoImagen = tipoImagen;
    }

    public String getFechaTermino() {
        return fechaTermino;
    }

    public void setFechaTermino(String fechaTermino) {
        this.fechaTermino = fechaTermino;
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

    public String getCostoConsulta() {
        return costoConsulta;
    }

    public void setCostoConsulta(String costoConsulta) {
        this.costoConsulta = costoConsulta;
    }
}
