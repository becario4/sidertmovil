package com.sidert.sidertmovil.models.documentosclientes;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DocumentoCliente implements Serializable {
    protected static final String TBL = "tbl_documentos_clientes";
    protected static final String COL_ID = "id";
    protected static final String COL_GRUPO_ID = "grupo_id";
    protected static final String COL_CLIENTE_ID = "cliente_id";
    protected static final String COL_CLAVECLIENTE = "clavecliente";
    protected static final String COL_PRESTAMO_ID = "prestamo_id";
    protected static final String COL_NUM_SOLICITUD = "num_solicitud";
    protected static final String COL_FECHA = "fecha";
    protected static final String COL_TIPO = "tipo";
    protected static final String COL_ARCHIVO_BASE64 = "archivo_base64";

    public static final String TIPO_INE = "ine";
    public static final String TIPO_COM_DOM = "comprobante_domicilio";

    private Integer id;

    @SerializedName("grupo_id")
    @Expose
    private Integer grupoId;

    @SerializedName("cliente_id")
    @Expose
    private Integer clienteId;

    @SerializedName("clavecliente")
    @Expose
    private String clavecliente;

    @SerializedName("prestamo_id")
    @Expose
    private Integer prestamoId;

    @SerializedName("num_solicitud")
    @Expose
    private Integer numSolicitud;

    @SerializedName("fecha")
    @Expose
    private String fecha;

    @SerializedName("tipo")
    @Expose
    private String tipo;

    @SerializedName("archivo_base64")
    @Expose
    private String archivoBase64;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGrupoId() {
        return grupoId;
    }

    public void setGrupoId(Integer grupoId) {
        this.grupoId = grupoId;
    }

    public Integer getClienteId() {
        return clienteId;
    }

    public void setClienteId(Integer clienteId) {
        this.clienteId = clienteId;
    }

    public String getClavecliente() {
        return clavecliente;
    }

    public void setClavecliente(String clavecliente) {
        this.clavecliente = clavecliente;
    }

    public Integer getPrestamoId() {
        return prestamoId;
    }

    public void setPrestamoId(Integer prestamoId) {
        this.prestamoId = prestamoId;
    }

    public Integer getNumSolicitud() {
        return numSolicitud;
    }

    public void setNumSolicitud(Integer numSolicitud) {
        this.numSolicitud = numSolicitud;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getArchivoBase64() {
        return archivoBase64;
    }

    public void setArchivoBase64(String archvioBase64) {
        this.archivoBase64 = archvioBase64;
    }
}
