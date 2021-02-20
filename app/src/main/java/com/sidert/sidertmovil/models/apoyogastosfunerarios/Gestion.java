package com.sidert.sidertmovil.models.apoyogastosfunerarios;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Gestion implements Serializable {
    private Integer id;

    @SerializedName("grupo_id")
    @Expose
    private String grupoId;

    @SerializedName("num_solicitud")
    @Expose
    private String numSolicitud;

    @SerializedName("medio_pago")
    @Expose
    private String medioPago;

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

    @SerializedName("tipo")
    @Expose
    private String tipo;

    @SerializedName("nombre")
    @Expose
    private String nombre;

    @SerializedName("estatus")
    @Expose
    private Integer estatus;

    @SerializedName("monto")
    @Expose
    private String monto;

    @SerializedName("imprimir_recibo")
    @Expose
    private String imprimirRecibo;

    @SerializedName("folio_manual")
    @Expose
    private String folioManual;

    @SerializedName("cliente_id")
    @Expose
    private String clienteId;

    @SerializedName("total_integrantes")
    @Expose
    private Integer totalIntegrantes;

    @SerializedName("total_integrantes_manual")
    @Expose
    private Integer totalIntegrantesManual;

    private String folioImpresion;

    public String getFolioImpresion() {
        return folioImpresion;
    }

    public void setFolioImpresion(String folioImpresion) {
        this.folioImpresion = folioImpresion;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGrupoId() {
        return grupoId;
    }

    public void setGrupoId(String grupoId) {
        this.grupoId = grupoId;
    }

    public String getNumSolicitud() {
        return numSolicitud;
    }

    public void setNumSolicitud(String numSolicitud) {
        this.numSolicitud = numSolicitud;
    }

    public String getMedioPago() {
        return medioPago;
    }

    public void setMedioPago(String medioPago) {
        this.medioPago = medioPago;
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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getEstatus() {
        return estatus;
    }

    public void setEstatus(Integer estatus) {
        this.estatus = estatus;
    }

    public String getMonto() {
        return monto;
    }

    public void setMonto(String monto) {
        this.monto = monto;
    }

    public String getImprimirRecibo() {
        return imprimirRecibo;
    }

    public void setImprimirRecibo(String imprimirRecibo) {
        this.imprimirRecibo = imprimirRecibo;
    }

    public String getFolioManual() {
        return folioManual;
    }

    public void setFolioManual(String folioManual) {
        this.folioManual = folioManual;
    }

    public String getClienteId() {
        return clienteId;
    }

    public void setClienteId(String clienteId) {
        this.clienteId = clienteId;
    }

    public Integer getTotalIntegrantesManual() {
        return totalIntegrantesManual;
    }

    public void setTotalIntegrantesManual(Integer totalIntegrantesManual) {
        this.totalIntegrantesManual = totalIntegrantesManual;
    }

    public Integer getTotalIntegrantes() {
        return totalIntegrantes;
    }

    public void setTotalIntegrantes(Integer totalIntegrantes) {
        this.totalIntegrantes = totalIntegrantes;
    }
}
