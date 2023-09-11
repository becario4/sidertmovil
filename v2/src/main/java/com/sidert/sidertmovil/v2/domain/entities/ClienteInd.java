package com.sidert.sidertmovil.v2.domain.entities;

import java.io.Serializable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tbl_cliente_ind")
public final class ClienteInd implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_cliente")
    private Long idCliente;
    @ColumnInfo(name = "id_solicitud")
    private Integer idSolicitud;
    @ColumnInfo(name = "nombre")
    private String nombre;
    @ColumnInfo(name = "paterno")
    private String paterno;
    @ColumnInfo(name = "materno")
    private String materno;
    @ColumnInfo(name = "fecha_nacimiento")
    private String fechaNacimiento;
    @ColumnInfo(name = "edad")
    private String edad;
    @ColumnInfo(name = "genero")
    private Integer genero;
    @ColumnInfo(name = "estado_nacimiento")
    private String estadoNacimiento;
    @ColumnInfo(name = "rfc")
    private String rfc;
    @ColumnInfo(name = "curp")
    private String curp;
    @ColumnInfo(name = "curp_digito_veri")
    private String curpDigitoVeri;
    @ColumnInfo(name = "ocupacion")
    private String ocupacion;
    @ColumnInfo(name = "actividad_economica")
    private String actividadEconomica;
    @ColumnInfo(name = "tipo_identificacion")
    private String tipoIdentificacion;
    @ColumnInfo(name = "no_identificacion")
    private String noIdentificacion;
    @ColumnInfo(name = "nivel_estudio")
    private String nivelEstudio;
    @ColumnInfo(name = "estado_civil")
    private String estadoCivil;
    @ColumnInfo(name = "bienes")
    private Integer bienes;
    @ColumnInfo(name = "tipo_vivienda")
    private String tipoVivienda;
    @ColumnInfo(name = "parentesco")
    private String parentesco;
    @ColumnInfo(name = "otro_tipo_vivienda")
    private String otroTipoVivienda;
    @ColumnInfo(name = "direccion_id")
    private String direccionId;
    @ColumnInfo(name = "tel_casa")
    private String telCasa;
    @ColumnInfo(name = "tel_celular")
    private String telCelular;
    @ColumnInfo(name = "tel_mensajes")
    private String telMensajes;
    @ColumnInfo(name = "tel_trabajo")
    private String telTrabajo;
    @ColumnInfo(name = "tiempo_vivir_sitio")
    private Integer tiempoVivirSitio;
    @ColumnInfo(name = "dependientes")
    private String dependientes;
    @ColumnInfo(name = "medio_contacto")
    private String medioContacto;
    @ColumnInfo(name = "estado_cuenta")
    private String estadoCuenta;
    @ColumnInfo(name = "email")
    private String email;
    @ColumnInfo(name = "foto_fachada")
    private String fotoFachada;
    @ColumnInfo(name = "ref_domiciliaria")
    private String refDomiciliaria;
    @ColumnInfo(name = "firma")
    private String firma;
    @ColumnInfo(name = "estatus_rechazo")
    private Integer estatusRechazo;
    @ColumnInfo(name = "comentario_rechazo")
    private String comentarioRechazo;
    @ColumnInfo(name = "estatus_completado")
    private Integer estatusCompletado;
    @ColumnInfo(name = "latitud")
    private String latitud;
    @ColumnInfo(name = "longitud")
    private String longitud;
    @ColumnInfo(name = "located_at")
    private String locatedAt;


// Getters & Setters

    public Long getIdCliente(){
        return this.idCliente;
    }

    public void setIdCliente(Long idCliente){
        this.idCliente = idCliente;
    }
    public Integer getIdSolicitud(){
        return this.idSolicitud;
    }

    public void setIdSolicitud(Integer idSolicitud){
        this.idSolicitud = idSolicitud;
    }
    public String getNombre(){
        return this.nombre;
    }

    public void setNombre(String nombre){
        this.nombre = nombre;
    }
    public String getPaterno(){
        return this.paterno;
    }

    public void setPaterno(String paterno){
        this.paterno = paterno;
    }
    public String getMaterno(){
        return this.materno;
    }

    public void setMaterno(String materno){
        this.materno = materno;
    }
    public String getFechaNacimiento(){
        return this.fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento){
        this.fechaNacimiento = fechaNacimiento;
    }
    public String getEdad(){
        return this.edad;
    }

    public void setEdad(String edad){
        this.edad = edad;
    }
    public Integer getGenero(){
        return this.genero;
    }

    public void setGenero(Integer genero){
        this.genero = genero;
    }
    public String getEstadoNacimiento(){
        return this.estadoNacimiento;
    }

    public void setEstadoNacimiento(String estadoNacimiento){
        this.estadoNacimiento = estadoNacimiento;
    }
    public String getRfc(){
        return this.rfc;
    }

    public void setRfc(String rfc){
        this.rfc = rfc;
    }
    public String getCurp(){
        return this.curp;
    }

    public void setCurp(String curp){
        this.curp = curp;
    }
    public String getCurpDigitoVeri(){
        return this.curpDigitoVeri;
    }

    public void setCurpDigitoVeri(String curpDigitoVeri){
        this.curpDigitoVeri = curpDigitoVeri;
    }
    public String getOcupacion(){
        return this.ocupacion;
    }

    public void setOcupacion(String ocupacion){
        this.ocupacion = ocupacion;
    }
    public String getActividadEconomica(){
        return this.actividadEconomica;
    }

    public void setActividadEconomica(String actividadEconomica){
        this.actividadEconomica = actividadEconomica;
    }
    public String getTipoIdentificacion(){
        return this.tipoIdentificacion;
    }

    public void setTipoIdentificacion(String tipoIdentificacion){
        this.tipoIdentificacion = tipoIdentificacion;
    }
    public String getNoIdentificacion(){
        return this.noIdentificacion;
    }

    public void setNoIdentificacion(String noIdentificacion){
        this.noIdentificacion = noIdentificacion;
    }
    public String getNivelEstudio(){
        return this.nivelEstudio;
    }

    public void setNivelEstudio(String nivelEstudio){
        this.nivelEstudio = nivelEstudio;
    }
    public String getEstadoCivil(){
        return this.estadoCivil;
    }

    public void setEstadoCivil(String estadoCivil){
        this.estadoCivil = estadoCivil;
    }
    public Integer getBienes(){
        return this.bienes;
    }

    public void setBienes(Integer bienes){
        this.bienes = bienes;
    }
    public String getTipoVivienda(){
        return this.tipoVivienda;
    }

    public void setTipoVivienda(String tipoVivienda){
        this.tipoVivienda = tipoVivienda;
    }
    public String getParentesco(){
        return this.parentesco;
    }

    public void setParentesco(String parentesco){
        this.parentesco = parentesco;
    }
    public String getOtroTipoVivienda(){
        return this.otroTipoVivienda;
    }

    public void setOtroTipoVivienda(String otroTipoVivienda){
        this.otroTipoVivienda = otroTipoVivienda;
    }
    public String getDireccionId(){
        return this.direccionId;
    }

    public void setDireccionId(String direccionId){
        this.direccionId = direccionId;
    }
    public String getTelCasa(){
        return this.telCasa;
    }

    public void setTelCasa(String telCasa){
        this.telCasa = telCasa;
    }
    public String getTelCelular(){
        return this.telCelular;
    }

    public void setTelCelular(String telCelular){
        this.telCelular = telCelular;
    }
    public String getTelMensajes(){
        return this.telMensajes;
    }

    public void setTelMensajes(String telMensajes){
        this.telMensajes = telMensajes;
    }
    public String getTelTrabajo(){
        return this.telTrabajo;
    }

    public void setTelTrabajo(String telTrabajo){
        this.telTrabajo = telTrabajo;
    }
    public Integer getTiempoVivirSitio(){
        return this.tiempoVivirSitio;
    }

    public void setTiempoVivirSitio(Integer tiempoVivirSitio){
        this.tiempoVivirSitio = tiempoVivirSitio;
    }
    public String getDependientes(){
        return this.dependientes;
    }

    public void setDependientes(String dependientes){
        this.dependientes = dependientes;
    }
    public String getMedioContacto(){
        return this.medioContacto;
    }

    public void setMedioContacto(String medioContacto){
        this.medioContacto = medioContacto;
    }
    public String getEstadoCuenta(){
        return this.estadoCuenta;
    }

    public void setEstadoCuenta(String estadoCuenta){
        this.estadoCuenta = estadoCuenta;
    }
    public String getEmail(){
        return this.email;
    }

    public void setEmail(String email){
        this.email = email;
    }
    public String getFotoFachada(){
        return this.fotoFachada;
    }

    public void setFotoFachada(String fotoFachada){
        this.fotoFachada = fotoFachada;
    }
    public String getRefDomiciliaria(){
        return this.refDomiciliaria;
    }

    public void setRefDomiciliaria(String refDomiciliaria){
        this.refDomiciliaria = refDomiciliaria;
    }
    public String getFirma(){
        return this.firma;
    }

    public void setFirma(String firma){
        this.firma = firma;
    }
    public Integer getEstatusRechazo(){
        return this.estatusRechazo;
    }

    public void setEstatusRechazo(Integer estatusRechazo){
        this.estatusRechazo = estatusRechazo;
    }
    public String getComentarioRechazo(){
        return this.comentarioRechazo;
    }

    public void setComentarioRechazo(String comentarioRechazo){
        this.comentarioRechazo = comentarioRechazo;
    }
    public Integer getEstatusCompletado(){
        return this.estatusCompletado;
    }

    public void setEstatusCompletado(Integer estatusCompletado){
        this.estatusCompletado = estatusCompletado;
    }
    public String getLatitud(){
        return this.latitud;
    }

    public void setLatitud(String latitud){
        this.latitud = latitud;
    }
    public String getLongitud(){
        return this.longitud;
    }

    public void setLongitud(String longitud){
        this.longitud = longitud;
    }
    public String getLocatedAt(){
        return this.locatedAt;
    }

    public void setLocatedAt(String locatedAt){
        this.locatedAt = locatedAt;
    }

}