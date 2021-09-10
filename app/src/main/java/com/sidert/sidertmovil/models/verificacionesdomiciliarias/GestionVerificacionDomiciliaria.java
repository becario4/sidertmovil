package com.sidert.sidertmovil.models.verificacionesdomiciliarias;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.json.JSONObject;

import java.io.Serializable;

import static com.sidert.sidertmovil.utils.Constants.K_CALLE_ENFRENTE;

public class GestionVerificacionDomiciliaria implements Serializable {
    @SerializedName("id")
    @Expose
    private Long id;

    @SerializedName("verificacion_domiciliaria_id")
    @Expose
    private Long verificacionDomiciliariaId;

    @SerializedName("latitud")
    @Expose
    private String latitud;

    @SerializedName("longitud")
    @Expose
    private String longitud;

    @SerializedName("foto_fachada")
    @Expose
    private String fotoFachada;

    @SerializedName("domicilio_coincide")
    @Expose
    private Integer domicilioCoincide;

    @SerializedName("comentario")
    @Expose
    private String comentario;

    @SerializedName("estatus")
    @Expose
    private Integer estatus;

    @SerializedName("usuario_id")
    @Expose
    private Long usuarioId;

    @SerializedName("usuario_nombre")
    @Expose
    private String usuarioNombre;

    @SerializedName("fecha_inicio")
    @Expose
    private String fechaInicio;

    @SerializedName("fecha_fin")
    @Expose
    private String fechaFin;

    @SerializedName("fecha_envio")
    @Expose
    private String fechaEnvio;

    @SerializedName("created_at")
    @Expose(serialize = false)
    private String createdAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getVerificacionDomiciliariaId() {
        return verificacionDomiciliariaId;
    }

    public void setVerificacionDomiciliariaId(Long verificacionDomiciliariaId) {
        this.verificacionDomiciliariaId = verificacionDomiciliariaId;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public String getFotoFachada() {
        return fotoFachada;
    }

    public void setFotoFachada(String fotoFachada) {
        this.fotoFachada = fotoFachada;
    }

    public Integer getDomicilioCoincide() {
        return domicilioCoincide;
    }

    public void setDomicilioCoincide(Integer domicilioCoincide) {
        this.domicilioCoincide = domicilioCoincide;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Integer getEstatus() {
        return estatus;
    }

    public void setEstatus(Integer estatus) {
        this.estatus = estatus;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getUsuarioNombre() {
        return usuarioNombre;
    }

    public void setUsuarioNombre(String usuarioNombre) {
        this.usuarioNombre = usuarioNombre;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
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

    public JSONObject getJson()
    {
        JSONObject json = new JSONObject();

        //json.put("id", this.id);

        return json;
    }
}
