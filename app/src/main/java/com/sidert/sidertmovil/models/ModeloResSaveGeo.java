package com.sidert.sidertmovil.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModeloResSaveGeo {

    @SerializedName("ficha")
    @Expose
    private Ficha ficha;

    @SerializedName("mensaje")
    @Expose
    private String mensaje;

    public Ficha getFicha() {
        return ficha;
    }

    public void setFicha(Ficha ficha) {
        this.ficha = ficha;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public class Ficha {

        @SerializedName("id")
        @Expose
        private Integer id;

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

        @SerializedName("ficha_id")
        @Expose
        private Integer fichaId;

        @SerializedName("foto_fachada")
        @Expose
        private String fotoFachada;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
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

    }
}
