package com.sidert.sidertmovil.models.circulocredito;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CirculoCredito implements Serializable {
    @SerializedName("producto")
    @Expose
    private String producto;
    @SerializedName("cliente_grupo")
    @Expose
    private String clienteGrupo;
    @SerializedName("aval_representante")
    @Expose
    private String avalRepresentante;
    @SerializedName("curp")
    @Expose
    private String curp;
    @SerializedName("integrantes")
    @Expose
    private Integer integrantes;
    @SerializedName("monto")
    @Expose
    private String monto;
    @SerializedName("medio_pago_id")
    @Expose
    private Integer medioPagoId;
    @SerializedName("folio")
    @Expose
    private String folio;
    @SerializedName("folio_manual")
    @Expose
    private Object folioManual;
    @SerializedName("tipo_impresion")
    @Expose
    private String tipoImpresion;
    @SerializedName("fecha_impreso")
    @Expose
    private String fechaImpreso;
    @SerializedName("fecha_envio")
    @Expose
    private String fechaEnvio;

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public String getClienteGrupo() {
        return clienteGrupo;
    }

    public void setClienteGrupo(String clienteGrupo) {
        this.clienteGrupo = clienteGrupo;
    }

    public String getAvalRepresentante() {
        return avalRepresentante;
    }

    public void setAvalRepresentante(String avalRepresentante) {
        this.avalRepresentante = avalRepresentante;
    }

    public String getCurp() {
        return curp;
    }

    public void setCurp(String curp) {
        this.curp = curp;
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

    public Integer getMedioPagoId() {
        return medioPagoId;
    }

    public void setMedioPagoId(Integer medioPagoId) {
        this.medioPagoId = medioPagoId;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public Object getFolioManual() {
        return folioManual;
    }

    public void setFolioManual(Object folioManual) {
        this.folioManual = folioManual;
    }

    public String getTipoImpresion() {
        return tipoImpresion;
    }

    public void setTipoImpresion(String tipoImpresion) {
        this.tipoImpresion = tipoImpresion;
    }

    public String getFechaImpreso() {
        return fechaImpreso;
    }

    public void setFechaImpreso(String fechaImpreso) {
        this.fechaImpreso = fechaImpreso;
    }

    public String getFechaEnvio() {
        return fechaEnvio;
    }

    public void setFechaEnvio(String fechaEnvio) {
        this.fechaEnvio = fechaEnvio;
    }
}
