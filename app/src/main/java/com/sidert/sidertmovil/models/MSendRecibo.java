package com.sidert.sidertmovil.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

public class MSendRecibo implements Serializable {

    @SerializedName("prestamoId")
    @Expose
    private Long prestamoId;

    @SerializedName("asesorId")
    @Expose
    private String asesorId;

    @SerializedName("tipoRecibo")
    @Expose
    private String tipo_recibo;

    @SerializedName("tipoImpresion")
    @Expose
    private String tipoImpresion;

    @SerializedName("folio")
    @Expose
    private Long folio;

    @SerializedName("monto")
    @Expose
    private String monto;

    @SerializedName("clave")
    @Expose
    private String clave;

    @SerializedName("nombre")
    @Expose
    private String nombre;

    @SerializedName("curp")
    @Expose
    private String curp;

    @SerializedName("fechaImpreso")
    @Expose
    private String fechaImpreso;

    @SerializedName("fechaEnvio")
    @Expose
    private String fechaEnvio;

    @SerializedName("usuarioId")
    @Expose
    private Long usuarioId;

    public Long getPrestamoId() {
        return prestamoId;
    }

    public void setPrestamoId(Long prestamoId) {
        this.prestamoId = prestamoId;
    }

    public String getAsesorId() {
        return asesorId;
    }

    public void setAsesorId(String asesorId) {
        this.asesorId = asesorId;
    }

    public String getTipo_recibo() {
        return tipo_recibo;
    }

    public void setTipo_recibo(String tipo_recibo) {
        this.tipo_recibo = tipo_recibo;
    }

    public String getTipoImpresion() {
        return tipoImpresion;
    }

    public void setTipoImpresion(String tipoImpresion) {
        this.tipoImpresion = tipoImpresion;
    }

    public Long getFolio() {
        return folio;
    }

    public void setFolio(Long folio) {
        this.folio = folio;
    }

    public String getMonto() {
        return monto;
    }

    public void setMonto(String monto) {
        this.monto = monto;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCurp() {
        return curp;
    }

    public void setCurp(String curp) {
        this.curp = curp;
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

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }
}
