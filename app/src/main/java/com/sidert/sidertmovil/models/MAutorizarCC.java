package com.sidert.sidertmovil.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MAutorizarCC implements Serializable {

    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("created_at")
    @Expose
    private String createdAt;

    @SerializedName("producto")
    @Expose
    private String producto;

    @SerializedName("usuario")
    @Expose
    private String usuario;

    @SerializedName("nombre_consulta")
    @Expose
    private String nombreConsulta;

    @SerializedName("rfc")
    @Expose
    private String rfc;

    @SerializedName("curp")
    @Expose
    private String curp;

    @SerializedName("preautorizacion")
    @Expose
    private String preautorizacion;

    @SerializedName("autorizacion")
    @Expose
    private String autorizacion;

    @SerializedName("error")
    @Expose
    private String error;

    @SerializedName("respuesta")
    @Expose
    private String respuesta;

    @SerializedName("sucursal")
    @Expose
    private String sucursal;

    @SerializedName("sucursal_id")
    @Expose
    private Integer sucursalId;

    @SerializedName("nombre_autorizado")
    @Expose
    private String nombreAutorizado;

    @SerializedName("origen")
    @Expose
    private String origen;

    @SerializedName("pdf")
    @Expose
    private String pdf;

    @SerializedName("monto_solicitado")
    @Expose
    private Integer montoSolicitado;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getNombreConsulta() {
        return nombreConsulta;
    }

    public void setNombreConsulta(String nombreConsulta) {
        this.nombreConsulta = nombreConsulta;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getCurp() {
        return curp;
    }

    public void setCurp(String curp) {
        this.curp = curp;
    }

    public String getPreautorizacion() {
        return preautorizacion;
    }

    public void setPreautorizacion(String preautorizacion) {
        this.preautorizacion = preautorizacion;
    }

    public String getAutorizacion() {
        return autorizacion;
    }

    public void setAutorizacion(String autorizacion) {
        this.autorizacion = autorizacion;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }

    public String getSucursal() {
        return sucursal;
    }

    public void setSucursal(String sucursal) {
        this.sucursal = sucursal;
    }

    public Integer getSucursalId() {
        return sucursalId;
    }

    public void setSucursalId(Integer sucursalId) {
        this.sucursalId = sucursalId;
    }

    public String getNombreAutorizado() {
        return nombreAutorizado;
    }

    public void setNombreAutorizado(String nombreAutorizado) {
        this.nombreAutorizado = nombreAutorizado;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getPdf() {
        return pdf;
    }

    public void setPdf(String pdf) {
        this.pdf = pdf;
    }

    public Integer getMontoSolicitado() {
        return montoSolicitado;
    }

    public void setMontoSolicitado(Integer montoSolicitado) {
        this.montoSolicitado = montoSolicitado;
    }
}
