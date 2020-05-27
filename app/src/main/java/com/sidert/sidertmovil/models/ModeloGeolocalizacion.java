package com.sidert.sidertmovil.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ModeloGeolocalizacion {

    @SerializedName("grupales_gestionadas")
    @Expose
    private List<GrupalesGestionada> grupalesGestionadas = null;
    @SerializedName("individuales_gestionadas")
    @Expose
    private List<IndividualesGestionada> individualesGestionadas = null;

    public List<GrupalesGestionada> getGrupalesGestionadas() {
        return grupalesGestionadas;
    }

    public void setGrupalesGestionadas(List<GrupalesGestionada> grupalesGestionadas) {
        this.grupalesGestionadas = grupalesGestionadas;
    }

    public List<IndividualesGestionada> getIndividualesGestionadas() {
        return individualesGestionadas;
    }

    public void setIndividualesGestionadas(List<IndividualesGestionada> individualesGestionadas) {
        this.individualesGestionadas = individualesGestionadas;
    }


    public class GrupalesGestionada {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("ficha_id")
        @Expose
        private Integer fichaId;
        @SerializedName("tipo")
        @Expose
        private String tipo;
        @SerializedName("barcode")
        @Expose
        private String barcode;
        @SerializedName("latitud")
        @Expose
        private String latitud;
        @SerializedName("longitud")
        @Expose
        private String longitud;
        @SerializedName("direccion")
        @Expose
        private String direccion;
        @SerializedName("comentario")
        @Expose
        private String comentario;
        @SerializedName("foto_fachada")
        @Expose
        private String fotoFachada;
        @SerializedName("fecha_gestion_inicio")
        @Expose
        private String fechaGestionInicio;
        @SerializedName("fecha_gestion_fin")
        @Expose
        private String fechaGestionFin;
        @SerializedName("fecha_recepcion_servidor")
        @Expose
        private String fechaRecepcionServidor;
        @SerializedName("fecha_dispositivo")
        @Expose
        private String fechaDispositivo;
        @SerializedName("fecha_envio")
        @Expose
        private String fechaEnvio;
        @SerializedName("fecha_respuesta")
        @Expose
        private String fechaRespuesta;
        @SerializedName("fecha_recepcion")
        @Expose
        private String fechaRecepcion;
        @SerializedName("prestamo_id")
        @Expose
        private Integer prestamoId;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getFichaId() {
            return fichaId;
        }

        public void setFichaId(Integer fichaId) {
            this.fichaId = fichaId;
        }

        public String getTipo() {
            return tipo;
        }

        public void setTipo(String tipo) {
            this.tipo = tipo;
        }

        public String getBarcode() {
            return barcode;
        }

        public void setBarcode(String barcode) {
            this.barcode = barcode;
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

        public String getDireccion() {
            return direccion;
        }

        public void setDireccion(String direccion) {
            this.direccion = direccion;
        }

        public String getComentario() {
            return comentario;
        }

        public void setComentario(String comentario) {
            this.comentario = comentario;
        }

        public String getFotoFachada() {
            return fotoFachada;
        }

        public void setFotoFachada(String fotoFachada) {
            this.fotoFachada = fotoFachada;
        }

        public String getFechaGestionInicio() {
            return fechaGestionInicio;
        }

        public void setFechaGestionInicio(String fechaGestionInicio) {
            this.fechaGestionInicio = fechaGestionInicio;
        }

        public String getFechaGestionFin() {
            return fechaGestionFin;
        }

        public void setFechaGestionFin(String fechaGestionFin) {
            this.fechaGestionFin = fechaGestionFin;
        }

        public String getFechaRecepcionServidor() {
            return fechaRecepcionServidor;
        }

        public void setFechaRecepcionServidor(String fechaRecepcionServidor) {
            this.fechaRecepcionServidor = fechaRecepcionServidor;
        }

        public String getFechaDispositivo() {
            return fechaDispositivo;
        }

        public void setFechaDispositivo(String fechaDispositivo) {
            this.fechaDispositivo = fechaDispositivo;
        }

        public String getFechaEnvio() {
            return fechaEnvio;
        }

        public void setFechaEnvio(String fechaEnvio) {
            this.fechaEnvio = fechaEnvio;
        }

        public String getFechaRespuesta() {
            return fechaRespuesta;
        }

        public void setFechaRespuesta(String fechaRespuesta) {
            this.fechaRespuesta = fechaRespuesta;
        }

        public String getFechaRecepcion() {
            return fechaRecepcion;
        }

        public void setFechaRecepcion(String fechaRecepcion) {
            this.fechaRecepcion = fechaRecepcion;
        }

        public Integer getPrestamoId() {
            return prestamoId;
        }

        public void setPrestamoId(Integer prestamoId) {
            this.prestamoId = prestamoId;
        }

    }

    public class IndividualesGestionada {
        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("barcode")
        @Expose
        private String barcode;
        @SerializedName("comentario")
        @Expose
        private String comentario;
        @SerializedName("direccion")
        @Expose
        private String direccion;
        @SerializedName("fecha_recepcion")
        @Expose
        private String fechaRecepcion;
        @SerializedName("fecha_respuesta")
        @Expose
        private String fechaRespuesta;
        @SerializedName("ficha_id")
        @Expose
        private Integer fichaId;
        @SerializedName("foto_fachada")
        @Expose
        private String fotoFachada;
        @SerializedName("latitud")
        @Expose
        private String latitud;
        @SerializedName("longitud")
        @Expose
        private String longitud;
        @SerializedName("tipo")
        @Expose
        private String tipo;
        @SerializedName("fecha_gestion_inicio")
        @Expose
        private String fechaGestionInicio;
        @SerializedName("fecha_gestion_fin")
        @Expose
        private String fechaGestionFin;
        @SerializedName("fecha_dispositivo")
        @Expose
        private String fechaDispositivo;
        @SerializedName("fecha_envio")
        @Expose
        private String fechaEnvio;
        @SerializedName("fecha_recepcion_servidor")
        @Expose
        private String fechaRecepcionServidor;
        @SerializedName("prestamo_id")
        @Expose
        private Integer prestamoId;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getBarcode() {
            return barcode;
        }

        public void setBarcode(String barcode) {
            this.barcode = barcode;
        }

        public String getComentario() {
            return comentario;
        }

        public void setComentario(String comentario) {
            this.comentario = comentario;
        }

        public String getDireccion() {
            return direccion;
        }

        public void setDireccion(String direccion) {
            this.direccion = direccion;
        }

        public String getFechaRecepcion() {
            return fechaRecepcion;
        }

        public void setFechaRecepcion(String fechaRecepcion) {
            this.fechaRecepcion = fechaRecepcion;
        }

        public String getFechaRespuesta() {
            return fechaRespuesta;
        }

        public void setFechaRespuesta(String fechaRespuesta) {
            this.fechaRespuesta = fechaRespuesta;
        }

        public Integer getFichaId() {
            return fichaId;
        }

        public void setFichaId(Integer fichaId) {
            this.fichaId = fichaId;
        }

        public String getFotoFachada() {
            return fotoFachada;
        }

        public void setFotoFachada(String fotoFachada) {
            this.fotoFachada = fotoFachada;
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

        public String getTipo() {
            return tipo;
        }

        public void setTipo(String tipo) {
            this.tipo = tipo;
        }

        public String getFechaGestionInicio() {
            return fechaGestionInicio;
        }

        public void setFechaGestionInicio(String fechaGestionInicio) {
            this.fechaGestionInicio = fechaGestionInicio;
        }

        public String getFechaGestionFin() {
            return fechaGestionFin;
        }

        public void setFechaGestionFin(String fechaGestionFin) {
            this.fechaGestionFin = fechaGestionFin;
        }

        public String getFechaDispositivo() {
            return fechaDispositivo;
        }

        public void setFechaDispositivo(String fechaDispositivo) {
            this.fechaDispositivo = fechaDispositivo;
        }

        public String getFechaEnvio() {
            return fechaEnvio;
        }

        public void setFechaEnvio(String fechaEnvio) {
            this.fechaEnvio = fechaEnvio;
        }

        public String getFechaRecepcionServidor() {
            return fechaRecepcionServidor;
        }

        public void setFechaRecepcionServidor(String fechaRecepcionServidor) {
            this.fechaRecepcionServidor = fechaRecepcionServidor;
        }

        public Integer getPrestamoId() {
            return prestamoId;
        }

        public void setPrestamoId(Integer prestamoId) {
            this.prestamoId = prestamoId;
        }

    }

}
