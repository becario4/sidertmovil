package com.sidert.sidertmovil.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MSolicitudRechazoInd implements Serializable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("solicitud_estado_id")
    @Expose
    private Integer solicitudEstadoId;
    @SerializedName("tipo_solicitud")
    @Expose
    private Integer tipoSolicitud;
    @SerializedName("estatus_cliente")
    @Expose
    private Object estatusCliente;
    @SerializedName("comentario_admin_cliente")
    @Expose
    private Object comentarioAdminCliente;
    @SerializedName("estatus_negocio")
    @Expose
    private Object estatusNegocio;
    @SerializedName("comentario_admin_negocio")
    @Expose
    private Object comentarioAdminNegocio;
    @SerializedName("estatus_aval")
    @Expose
    private Object estatusAval;
    @SerializedName("comentario_admin_aval")
    @Expose
    private Object comentarioAdminAval;
    @SerializedName("estatus_referencia")
    @Expose
    private Object estatusReferencia;
    @SerializedName("comentario_admin_referencia")
    @Expose
    private Object comentarioAdminReferencia;
    @SerializedName("estatus_croquis")
    @Expose
    private Object estatusCroquis;
    @SerializedName("comentario_admin_croquis")
    @Expose
    private Object comentarioAdminCroquis;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSolicitudEstadoId() {
        return solicitudEstadoId;
    }

    public Integer getTipoSolicitud() {
        return tipoSolicitud;
    }

    public void setTipoSolicitud(Integer tipoSolicitud) {
        this.tipoSolicitud = tipoSolicitud;
    }

    public void setSolicitudEstadoId(Integer solicitudEstadoId) {
        this.solicitudEstadoId = solicitudEstadoId;
    }

    public Object getEstatusCliente() {
        return estatusCliente;
    }

    public void setEstatusCliente(Object estatusCliente) {
        this.estatusCliente = estatusCliente;
    }

    public Object getComentarioAdminCliente() {
        return comentarioAdminCliente;
    }

    public void setComentarioAdminCliente(Object comentarioAdminCliente) {
        this.comentarioAdminCliente = comentarioAdminCliente;
    }

    public Object getEstatusNegocio() {
        return estatusNegocio;
    }

    public void setEstatusNegocio(Object estatusNegocio) {
        this.estatusNegocio = estatusNegocio;
    }

    public Object getComentarioAdminNegocio() {
        return comentarioAdminNegocio;
    }

    public void setComentarioAdminNegocio(Object comentarioAdminNegocio) {
        this.comentarioAdminNegocio = comentarioAdminNegocio;
    }

    public Object getEstatusAval() {
        return estatusAval;
    }

    public void setEstatusAval(Object estatusAval) {
        this.estatusAval = estatusAval;
    }

    public Object getComentarioAdminAval() {
        return comentarioAdminAval;
    }

    public void setComentarioAdminAval(Object comentarioAdminAval) {
        this.comentarioAdminAval = comentarioAdminAval;
    }

    public Object getEstatusReferencia() {
        return estatusReferencia;
    }

    public void setEstatusReferencia(Object estatusReferencia) {
        this.estatusReferencia = estatusReferencia;
    }

    public Object getComentarioAdminReferencia() {
        return comentarioAdminReferencia;
    }

    public void setComentarioAdminReferencia(Object comentarioAdminReferencia) {
        this.comentarioAdminReferencia = comentarioAdminReferencia;
    }

    public Object getEstatusCroquis() {
        return estatusCroquis;
    }

    public void setEstatusCroquis(Object estatusCroquis) {
        this.estatusCroquis = estatusCroquis;
    }

    public Object getComentarioAdminCroquis() {
        return comentarioAdminCroquis;
    }

    public void setComentarioAdminCroquis(Object comentarioAdminCroquis) {
        this.comentarioAdminCroquis = comentarioAdminCroquis;
    }
}
