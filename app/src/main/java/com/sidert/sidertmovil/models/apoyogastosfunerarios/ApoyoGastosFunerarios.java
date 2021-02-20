package com.sidert.sidertmovil.models.apoyogastosfunerarios;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ApoyoGastosFunerarios implements Serializable {
    private Integer id;

    @SerializedName("prestamo_id")
    @Expose
    private String prestamoId;

    @SerializedName("asesor_id")
    @Expose
    private String asesorId;

    @SerializedName("tipo_recibo")
    @Expose
    private String tipoRecibo;

    @SerializedName("tipo_impresion")
    @Expose
    private String tipoImpresion;

    @SerializedName("folio")
    @Expose
    private String folio;

    @SerializedName("monto")
    @Expose
    private String monto;

    @SerializedName("clave")
    @Expose
    private String clave;

    @SerializedName("nombre")
    @Expose
    private String nombre;

    @SerializedName("ap_paterno")
    @Expose
    private String apPaterno;

    @SerializedName("ap_materno")
    @Expose
    private String apMaterno;

    @SerializedName("fecha_impreso")
    @Expose
    private String fechaImpreso;

    @SerializedName("fecha_envio")
    @Expose
    private String fechaEnvio;

    @SerializedName("estatus")
    @Expose
    private Integer estatus;

    @SerializedName("curp")
    @Expose
    private String curp;

    @SerializedName("monto_original")
    @Expose
    private String montoOriginal;

    @SerializedName("integrantesOriginales")
    @Expose
    private Integer integrantesOrigiales;

    @SerializedName("num_solicitud")
    @Expose
    private Integer numSolicitud;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPrestamoId() {
        return prestamoId;
    }

    public void setPrestamoId(String prestamoId) {
        this.prestamoId = prestamoId;
    }

    public String getAsesorId() {
        return asesorId;
    }

    public void setAsesorId(String asesorId) {
        this.asesorId = asesorId;
    }

    public String getTipoRecibo() {
        return tipoRecibo;
    }

    public void setTipoRecibo(String tipoRecibo) {
        this.tipoRecibo = tipoRecibo;
    }

    public String getTipoImpresion() {
        return tipoImpresion;
    }

    public void setTipoImpresion(String tipoImpresion) {
        this.tipoImpresion = tipoImpresion;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
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

    public String getApPaterno() {
        return apPaterno;
    }

    public void setApPaterno(String apPaterno) {
        this.apPaterno = apPaterno;
    }

    public String getApMaterno() {
        return apMaterno;
    }

    public void setApMaterno(String apMaterno) {
        this.apMaterno = apMaterno;
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

    public Integer getEstatus() {
        return estatus;
    }

    public void setEstatus(Integer estatus) {
        this.estatus = estatus;
    }

    public String getCurp() {
        return curp;
    }

    public void setCurp(String curp) {
        this.curp = curp;
    }

    public String getMontoOriginal() {
        return montoOriginal;
    }

    public void setMontoOriginal(String montoOriginal) {
        this.montoOriginal = montoOriginal;
    }

    public Integer getIntegrantesOrigiales() {
        return integrantesOrigiales;
    }

    public void setIntegrantesOrigiales(Integer integrantesOrigiales) {
        this.integrantesOrigiales = integrantesOrigiales;
    }

    public Integer getNumSolicitud() {
        return numSolicitud;
    }

    public void setNumSolicitud(Integer numSolicitud) {
        this.numSolicitud = numSolicitud;
    }
}
