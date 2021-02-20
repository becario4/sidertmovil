package com.sidert.sidertmovil.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MResUltimoRecibo implements Serializable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("usuario_id")
    @Expose
    private Integer usuarioId;
    @SerializedName("asesor_id")
    @Expose
    private String asesorId;
    @SerializedName("grupo_id")
    @Expose
    private Integer grupoId;
    @SerializedName("num_solicitud")
    @Expose
    private Integer numSolicitud;
    @SerializedName("nombre")
    @Expose
    private String nombre;
    @SerializedName("medio_pago_id")
    @Expose
    private Integer medioPagoId;
    @SerializedName("evidencia")
    @Expose
    private String evidencia;
    @SerializedName("tipo_imagen")
    @Expose
    private Integer tipoImagen;
    @SerializedName("cliente_id")
    @Expose
    private Object clienteId;
    @SerializedName("folio")
    @Expose
    private String folio;
    @SerializedName("tipo")
    @Expose
    private String tipo;
    @SerializedName("folio_manual")
    @Expose
    private String folioManual;
    @SerializedName("monto")
    @Expose
    private Double monto;
    @SerializedName("tipo_impresion")
    @Expose
    private String tipoImpresion;
    @SerializedName("fecha_impreso")
    @Expose
    private String fechaImpreso;
    @SerializedName("fecha_termino")
    @Expose
    private String fechaTermino;
    @SerializedName("fecha_envio")
    @Expose
    private String fechaEnvio;
    @SerializedName("created_at")
    @Expose
    private String createdAt;

    @SerializedName("plazo")
    @Expose
    private Integer plazo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Integer usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getAsesorId() {
        return asesorId;
    }

    public void setAsesorId(String asesorId) {
        this.asesorId = asesorId;
    }

    public Integer getGrupoId() {
        return grupoId;
    }

    public void setGrupoId(Integer grupoId) {
        this.grupoId = grupoId;
    }

    public Integer getNumSolicitud() {
        return numSolicitud;
    }

    public void setNumSolicitud(Integer numSolicitud) {
        this.numSolicitud = numSolicitud;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getMedioPagoId() {
        return medioPagoId;
    }

    public void setMedioPagoId(Integer medioPagoId) {
        this.medioPagoId = medioPagoId;
    }

    public String getEvidencia() {
        return evidencia;
    }

    public void setEvidencia(String evidencia) {
        this.evidencia = evidencia;
    }

    public Integer getTipoImagen() {
        return tipoImagen;
    }

    public void setTipoImagen(Integer tipoImagen) {
        this.tipoImagen = tipoImagen;
    }

    public Object getClienteId() {
        return clienteId;
    }

    public void setClienteId(Object clienteId) {
        this.clienteId = clienteId;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getFolioManual() {
        return folioManual;
    }

    public void setFolioManual(String folioManual) {
        this.folioManual = folioManual;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
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

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getPlazo() {
        return plazo;
    }

    public void setPlazo(Integer plazo) {
        this.plazo = plazo;
    }
}
