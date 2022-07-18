package com.sidert.sidertmovil.models.solicitudes.solicitudind.originacion;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.File;
import java.io.Serializable;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static com.sidert.sidertmovil.utils.Constants.ROOT_PATH;

public class Cliente implements Serializable {
    public final static String TBL                         = "tbl_cliente_ind";

    @SerializedName("id_cliente")
    @Expose
    private Integer idCliente;

    @SerializedName("id_solicitud")
    @Expose
    private Integer idSolicitud;

    @SerializedName("nombre")
    @Expose
    private String nombre;

    @SerializedName("paterno")
    @Expose
    private String paterno;

    @SerializedName("materno")
    @Expose
    private String materno;

    @SerializedName("fecha_nacimiento")
    @Expose
    private String fechaNacimiento;

    @SerializedName("edad")
    @Expose
    private String edad;

    @SerializedName("genero")
    @Expose
    private Integer genero;

    @SerializedName("estado_nacimiento")
    @Expose
    private String estadoNacimiento;

    @SerializedName("rfc")
    @Expose
    private String rfc;

    @SerializedName("curp")
    @Expose
    private String curp;

    @SerializedName("curp_digito_veri")
    @Expose
    private String curpDigitoVeri;

    @SerializedName("ocupacion")
    @Expose
    private String ocupacion;

    @SerializedName("actividad_economica")
    @Expose
    private String actividadEconomica;

    @SerializedName("tipoIdentificacion")
    @Expose
    private String tipoIdentificacion;

    @SerializedName("no_identificacion")
    @Expose
    private String noIdentificacion;

    @SerializedName("nivel_estudio")
    @Expose
    private String nivelEstudio;

    @SerializedName("estado_civil")
    @Expose
    private String estadoCivil;

    @SerializedName("bienes")
    @Expose
    private Integer bienes;

    @SerializedName("tipo_vivienda")
    @Expose
    private String tipoVivienda;

    @SerializedName("parentesco")
    @Expose
    private String parentesco;

    @SerializedName("otro_tipo_vivienda")
    @Expose
    private String otroTipoVivienda;

    @SerializedName("direccion_id")
    @Expose
    private String direccionId;

    @SerializedName("tel_casa")
    @Expose
    private String telCasa;

    @SerializedName("tel_celular")
    @Expose
    private String telCelular;

    @SerializedName("tel_mensajes")
    @Expose
    private String telMensajes;

    @SerializedName("tel_trabajo")
    @Expose
    private String telTrabajo;

    @SerializedName("tiempo_vivir_sitio")
    @Expose
    private Integer tiempoVivirSitio;

    @SerializedName("dependientes")
    @Expose
    private String dependientes;

    @SerializedName("medio_contacto")
    @Expose
    private String medioContacto;

    @SerializedName("estado_cuenta")
    @Expose
    private String estadoCuenta;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("foto_fachada")
    @Expose
    private String fotoFachada;

    @SerializedName("ref_domiciliaria")
    @Expose
    private String refDomiciliaria;

    @SerializedName("firma")
    @Expose
    private String firma;

    @SerializedName("estatus_rechazo")
    @Expose
    private Integer estatusRechazo;

    @SerializedName("comentario_rechazo")
    @Expose
    private String comentarioRechazo;

    @SerializedName("estatus_completado")
    @Expose
    private Integer estatusCompletado;

    @SerializedName("latitud")
    @Expose
    private String latitud;

    @SerializedName("longitud")
    @Expose
    private String longitud;

    @SerializedName("located_at")
    @Expose
    private String locatedAt;

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public Integer getIdSolicitud() {
        return idSolicitud;
    }

    public void setIdSolicitud(Integer idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPaterno() {
        return paterno;
    }

    public void setPaterno(String paterno) {
        this.paterno = paterno;
    }

    public String getMaterno() {
        return materno;
    }

    public void setMaterno(String materno) {
        this.materno = materno;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }

    public Integer getGenero() {
        return genero;
    }

    public void setGenero(Integer genero) {
        this.genero = genero;
    }

    public String getEstadoNacimiento() {
        return estadoNacimiento;
    }

    public void setEstadoNacimiento(String estadoNacimiento) {
        this.estadoNacimiento = estadoNacimiento;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getCurp() {
        return curp;
    }

    public void setCurp(String curp) {
        this.curp = curp;
    }

    public String getCurpDigitoVeri() {
        return curpDigitoVeri;
    }

    public void setCurpDigitoVeri(String curpDigitoVeri) {
        this.curpDigitoVeri = curpDigitoVeri;
    }

    public String getOcupacion() {
        return ocupacion;
    }

    public void setOcupacion(String ocupacion) {
        this.ocupacion = ocupacion;
    }

    public String getActividadEconomica() {
        return actividadEconomica;
    }

    public void setActividadEconomica(String actividadEconomica) {
        this.actividadEconomica = actividadEconomica;
    }

    public String getTipoIdentificacion() {
        return tipoIdentificacion;
    }

    public void setTipoIdentificacion(String tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }

    public String getNoIdentificacion() {
        return noIdentificacion;
    }

    public void setNoIdentificacion(String noIdentificacion) {
        this.noIdentificacion = noIdentificacion;
    }

    public String getNivelEstudio() {
        return nivelEstudio;
    }

    public void setNivelEstudio(String nivelEstudio) {
        this.nivelEstudio = nivelEstudio;
    }

    public String getEstadoCivil() {
        return estadoCivil;
    }

    public void setEstadoCivil(String estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    public Integer getBienes() {
        return bienes;
    }

    public void setBienes(Integer bienes) {
        this.bienes = bienes;
    }

    public String getTipoVivienda() {
        return tipoVivienda;
    }

    public void setTipoVivienda(String tipoVivienda) {
        this.tipoVivienda = tipoVivienda;
    }

    public String getParentesco() {
        return parentesco;
    }

    public void setParentesco(String parentesco) {
        this.parentesco = parentesco;
    }

    public String getOtroTipoVivienda() {
        return otroTipoVivienda;
    }

    public void setOtroTipoVivienda(String otroTipoVivienda) {
        this.otroTipoVivienda = otroTipoVivienda;
    }

    public String getDireccionId() {
        return direccionId;
    }

    public void setDireccionId(String direccionId) {
        this.direccionId = direccionId;
    }

    public String getTelCasa() {
        return telCasa;
    }

    public void setTelCasa(String telCasa) {
        this.telCasa = telCasa;
    }

    public String getTelCelular() {
        return telCelular;
    }

    public void setTelCelular(String telCelular) {
        this.telCelular = telCelular;
    }

    public String getTelMensajes() {
        return telMensajes;
    }

    public void setTelMensajes(String telMensajes) {
        this.telMensajes = telMensajes;
    }

    public String getTelTrabajo() {
        return telTrabajo;
    }

    public void setTelTrabajo(String telTrabajo) {
        this.telTrabajo = telTrabajo;
    }

    public Integer getTiempoVivirSitio() {
        return tiempoVivirSitio;
    }

    public void setTiempoVivirSitio(Integer tiempoVivirSitio) {
        this.tiempoVivirSitio = tiempoVivirSitio;
    }

    public String getDependientes() {
        return dependientes;
    }

    public void setDependientes(String dependientes) {
        this.dependientes = dependientes;
    }

    public String getMedioContacto() {
        return medioContacto;
    }

    public void setMedioContacto(String medioContacto) {
        this.medioContacto = medioContacto;
    }

    public String getEstadoCuenta() {
        return estadoCuenta;
    }

    public void setEstadoCuenta(String estadoCuenta) {
        this.estadoCuenta = estadoCuenta;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFotoFachada() {
        return fotoFachada;
    }

    public void setFotoFachada(String fotoFachada) {
        this.fotoFachada = fotoFachada;
    }

    public String getRefDomiciliaria() {
        return refDomiciliaria;
    }

    public void setRefDomiciliaria(String refDomiciliaria) {
        this.refDomiciliaria = refDomiciliaria;
    }

    public String getFirma() {
        return firma;
    }

    public void setFirma(String firma) {
        this.firma = firma;
    }

    public Integer getEstatusRechazo() {
        return estatusRechazo;
    }

    public void setEstatusRechazo(Integer estatusRechazo) {
        this.estatusRechazo = estatusRechazo;
    }

    public String getComentarioRechazo() {
        return comentarioRechazo;
    }

    public void setComentarioRechazo(String comentarioRechazo) {
        this.comentarioRechazo = comentarioRechazo;
    }

    public Integer getEstatusCompletado() {
        return estatusCompletado;
    }

    public void setEstatusCompletado(Integer estatusCompletado) {
        this.estatusCompletado = estatusCompletado;
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

    public String getLocatedAt() {
        return locatedAt;
    }

    public void setLocatedAt(String locatedAt) {
        this.locatedAt = locatedAt;
    }

    public MultipartBody.Part getFachadaMBPart() {
        MultipartBody.Part fachada_cliente = null;
        File image_fachada_cliente = null;

        if (fotoFachada != null && !fotoFachada.equals(""))
            image_fachada_cliente = new File(ROOT_PATH + "Fachada/" + fotoFachada);

        if (image_fachada_cliente != null) {
            RequestBody imageBodyFachadaCli = RequestBody.create(MediaType.parse("image/*"), image_fachada_cliente);
            fachada_cliente = MultipartBody.Part.createFormData("fachada_cliente", image_fachada_cliente.getName(), imageBodyFachadaCli);
        }

        return fachada_cliente;
    }

    public MultipartBody.Part getFirmaMBPart() {
        MultipartBody.Part firma_cliente = null;
        File image_firma_cliente = null;

        if (firma != null && !firma.equals(""))
            image_firma_cliente = new File(ROOT_PATH + "Firma/" + firma);

        if (image_firma_cliente != null) {
            RequestBody imageBodyFirmaCli = RequestBody.create(MediaType.parse("image/*"), image_firma_cliente);
             firma_cliente = MultipartBody.Part.createFormData("firma_cliente", image_firma_cliente.getName(), imageBodyFirmaCli);
        }

        return firma_cliente;
    }
}
