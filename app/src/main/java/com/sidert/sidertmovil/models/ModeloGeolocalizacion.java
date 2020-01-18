package com.sidert.sidertmovil.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ModeloGeolocalizacion {

    @SerializedName("grupales")
    @Expose
    private List<Grupale> grupales = null;

    @SerializedName("grupales_gestionadas")
    @Expose
    private List<GrupalesGestionada> grupalesGestionadas = null;

    @SerializedName("individuales")
    @Expose
    private List<Individuale> individuales = null;

    @SerializedName("individuales_gestionadas")
    @Expose
    private List<IndividualesGestionada> individualesGestionadas = null;

    public List<Grupale> getGrupales() {
        return grupales;
    }

    public void setGrupales(List<Grupale> grupales) {
        this.grupales = grupales;
    }

    public List<GrupalesGestionada> getGrupalesGestionadas() {
        return grupalesGestionadas;
    }

    public void setGrupalesGestionadas(List<GrupalesGestionada> grupalesGestionadas) {
        this.grupalesGestionadas = grupalesGestionadas;
    }

    public List<Individuale> getIndividuales() {
        return individuales;
    }

    public void setIndividuales(List<Individuale> individuales) {
        this.individuales = individuales;
    }

    public List<IndividualesGestionada> getIndividualesGestionadas() {
        return individualesGestionadas;
    }

    public void setIndividualesGestionadas(List<IndividualesGestionada> individualesGestionadas) {
        this.individualesGestionadas = individualesGestionadas;
    }

    public class Grupale {

        @SerializedName("integrantes")
        @Expose
        private List<Integrante> integrantes = null;

        @SerializedName("ficha_id")
        @Expose
        private Integer ficha_id;

        @SerializedName("asesor_nombre")
        @Expose
        private String asesor_nombre;

        @SerializedName("grupo_id")
        @Expose
        private Integer grupoId;

        @SerializedName("grupo_nombre")
        @Expose
        private String grupoNombre;

        @SerializedName("num_solicitud")
        @Expose
        private Integer numSolicitud;

        @SerializedName("fecha_entrega")
        @Expose
        private String fechaEntrega;

        @SerializedName("fecha_vencimiento")
        @Expose
        private String fechaVencimiento;

        public List<Integrante> getIntegrantes() {
            return integrantes;
        }

        public void setIntegrantes(List<Integrante> integrantes) {
            this.integrantes = integrantes;
        }

        public Integer getFicha_id() {
            return ficha_id;
        }

        public void setFicha_id(Integer ficha_id) {
            this.ficha_id = ficha_id;
        }

        public String getAsesor_nombre() {
            return asesor_nombre;
        }

        public void setAsesor_nombre(String asesor_nombre) {
            this.asesor_nombre = asesor_nombre;
        }

        public Integer getGrupoId() {
            return grupoId;
        }

        public void setGrupoId(Integer grupoId) {
            this.grupoId = grupoId;
        }

        public String getGrupoNombre() {
            return grupoNombre;
        }

        public void setGrupoNombre(String grupoNombre) {
            this.grupoNombre = grupoNombre;
        }

        public Integer getNumSolicitud() {
            return numSolicitud;
        }

        public void setNumSolicitud(Integer numSolicitud) {
            this.numSolicitud = numSolicitud;
        }

        public String getFechaEntrega() {
            return fechaEntrega;
        }

        public void setFechaEntrega(String fechaEntrega) {
            this.fechaEntrega = fechaEntrega;
        }

        public String getFechaVencimiento() {
            return fechaVencimiento;
        }

        public void setFechaVencimiento(String fechaVencimiento) {
            this.fechaVencimiento = fechaVencimiento;
        }

    }

    public class Individuale {

        @SerializedName("ficha_id")
        @Expose
        private Integer ficha_id;

        @SerializedName("asesor_nombre")
        @Expose
        private String asesor_nombre;


        @SerializedName("cliente_id")
        @Expose
        private Integer clienteId;

        @SerializedName("cliente_clave")
        @Expose
        private String clienteClave;

        @SerializedName("cliente_nombre")
        @Expose
        private String clienteNombre;

        @SerializedName("cliente_direccion")
        @Expose
        private String clienteDireccion;

        @SerializedName("cliente_colonia")
        @Expose
        private String clienteColonia;

        @SerializedName("aval_nombre")
        @Expose
        private String avalNombre;

        @SerializedName("aval_direccion")
        @Expose
        private String avalDireccion;

        @SerializedName("negocio_nombre")
        @Expose
        private String negocioNombre;

        @SerializedName("negocio_direccion")
        @Expose
        private String negocioDireccion;

        @SerializedName("num_solicitud")
        @Expose
        private Integer numSolicitud;

        @SerializedName("monto_otorgado")
        @Expose
        private Double montoOtorgado;

        @SerializedName("fecha_entrega")
        @Expose
        private String fechaEntrega;

        @SerializedName("fecha_vencimiento")
        @Expose
        private String fechaVencimiento;

        public Integer getFicha_id() {
            return ficha_id;
        }

        public void setFicha_id(Integer ficha_id) {
            this.ficha_id = ficha_id;
        }

        public String getAsesor_nombre() {
            return asesor_nombre;
        }

        public void setAsesor_nombre(String asesor_nombre) {
            this.asesor_nombre = asesor_nombre;
        }

        public Integer getClienteId() {
            return clienteId;
        }

        public void setClienteId(Integer clienteId) {
            this.clienteId = clienteId;
        }

        public String getClienteClave() {
            return clienteClave;
        }

        public void setClienteClave(String clienteClave) {
            this.clienteClave = clienteClave;
        }

        public String getClienteNombre() {
            return clienteNombre;
        }

        public void setClienteNombre(String clienteNombre) {
            this.clienteNombre = clienteNombre;
        }

        public String getClienteDireccion() {
            return clienteDireccion;
        }

        public void setClienteDireccion(String clienteDireccion) {
            this.clienteDireccion = clienteDireccion;
        }

        public String getClienteColonia() {
            return clienteColonia;
        }

        public void setClienteColonia(String clienteColonia) {
            this.clienteColonia = clienteColonia;
        }

        public String getAvalNombre() {
            return avalNombre;
        }

        public void setAvalNombre(String avalNombre) {
            this.avalNombre = avalNombre;
        }

        public String getAvalDireccion() {
            return avalDireccion;
        }

        public void setAvalDireccion(String avalDireccion) {
            this.avalDireccion = avalDireccion;
        }

        public String getNegocioNombre() {
            return negocioNombre;
        }

        public void setNegocioNombre(String negocioNombre) {
            this.negocioNombre = negocioNombre;
        }

        public String getNegocioDireccion() {
            return negocioDireccion;
        }

        public void setNegocioDireccion(String negocioDireccion) {
            this.negocioDireccion = negocioDireccion;
        }

        public Integer getNumSolicitud() {
            return numSolicitud;
        }

        public void setNumSolicitud(Integer numSolicitud) {
            this.numSolicitud = numSolicitud;
        }

        public Double getMontoOtorgado() {
            return montoOtorgado;
        }

        public void setMontoOtorgado(Double montoOtorgado) {
            this.montoOtorgado = montoOtorgado;
        }

        public String getFechaEntrega() {
            return fechaEntrega;
        }

        public void setFechaEntrega(String fechaEntrega) {
            this.fechaEntrega = fechaEntrega;
        }

        public String getFechaVencimiento() {
            return fechaVencimiento;
        }

        public void setFechaVencimiento(String fechaVencimiento) {
            this.fechaVencimiento = fechaVencimiento;
        }
    }

    public class Integrante {

        @SerializedName("cliente_id")
        @Expose
        private Integer clienteId;
        @SerializedName("cliente_clave")
        @Expose
        private String clienteClave;
        @SerializedName("cliente_nombre")
        @Expose
        private String clienteNombre;
        @SerializedName("cliente_direccion")
        @Expose
        private String clienteDireccion;
        @SerializedName("cliente_colonia")
        @Expose
        private String clienteColonia;
        @SerializedName("integrante_tipo")
        @Expose
        private String integranteTipo;
        @SerializedName("monto_otorgado")
        @Expose
        private Double montoOtorgado;

        public Integer getClienteId() {
            return clienteId;
        }

        public void setClienteId(Integer clienteId) {
            this.clienteId = clienteId;
        }

        public String getClienteClave() {
            return clienteClave;
        }

        public void setClienteClave(String clienteClave) {
            this.clienteClave = clienteClave;
        }

        public String getClienteNombre() {
            return clienteNombre;
        }

        public void setClienteNombre(String clienteNombre) {
            this.clienteNombre = clienteNombre;
        }

        public String getClienteDireccion() {
            return clienteDireccion;
        }

        public void setClienteDireccion(String clienteDireccion) {
            this.clienteDireccion = clienteDireccion;
        }

        public String getClienteColonia() {
            return clienteColonia;
        }

        public void setClienteColonia(String clienteColonia) {
            this.clienteColonia = clienteColonia;
        }

        public String getIntegranteTipo() {
            return integranteTipo;
        }

        public void setIntegranteTipo(String integranteTipo) {
            this.integranteTipo = integranteTipo;
        }

        public Double getMontoOtorgado() {
            return montoOtorgado;
        }

        public void setMontoOtorgado(Double montoOtorgado) {
            this.montoOtorgado = montoOtorgado;
        }

    }

    public class GrupalesGestionada {

        @SerializedName("ficha_id")
        @Expose
        private Integer fichaId;
        @SerializedName("integrante_tipo")
        @Expose
        private String integranteTipo;
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
        @SerializedName("fecha_respuesta")
        @Expose
        private String fechaRespuesta;
        @SerializedName("fecha_recepcion")
        @Expose
        private String fechaRecepcion;

        public Integer getFichaId() {
            return fichaId;
        }

        public void setFichaId(Integer fichaId) {
            this.fichaId = fichaId;
        }

        public String getIntegranteTipo() {
            return integranteTipo;
        }

        public void setIntegranteTipo(String integranteTipo) {
            this.integranteTipo = integranteTipo;
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

    }

    public class IndividualesGestionada {

        @SerializedName("ficha_id")
        @Expose
        private Integer fichaId;
        @SerializedName("integrante_tipo")
        @Expose
        private String integranteTipo;
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
        @SerializedName("fecha_respuesta")
        @Expose
        private String fechaRespuesta;
        @SerializedName("fecha_recepcion")
        @Expose
        private String fechaRecepcion;

        public Integer getFichaId() {
            return fichaId;
        }

        public void setFichaId(Integer fichaId) {
            this.fichaId = fichaId;
        }

        public String getIntegranteTipo() {
            return integranteTipo;
        }

        public void setIntegranteTipo(String integranteTipo) {
            this.integranteTipo = integranteTipo;
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

    }
}
