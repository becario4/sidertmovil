package com.sidert.sidertmovil.models.solicitudes.solicitudgpo.originacion;

import android.database.Cursor;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.sidert.sidertmovil.models.BaseModel;
import com.sidert.sidertmovil.models.IFillModel;

import java.io.Serializable;

public class IntegranteGpo extends BaseModel implements Serializable, IFillModel {
    public final static String TBL                            = "tbl_integrantes_gpo";
    protected static final String COL_ID                      = "id";
    protected static final String COL_ID_CREDITO              = "id_credito";
    protected static final String COL_CARGO                   = "cargo";
    protected static final String COL_NOMBRE                  = "nombre";
    protected static final String COL_PATERNO                 = "paterno";
    protected static final String COL_MATERNO                 = "materno";
    protected static final String COL_FECHA_NACIMIENTO        = "fecha_nacimiento";
    protected static final String COL_EDAD                    = "edad";
    protected static final String COL_GENERO                  = "genero";
    protected static final String COL_ESTADO_NACIMIENTO       = "estado_nacimiento";
    protected static final String COL_RFC                     = "rfc";
    protected static final String COL_CURP                    = "curp";
    protected static final String COL_CURP_DIGITO_VERI        = "curp_digito_veri";
    protected static final String COL_TIPO_IDENTIFICACION     = "tipo_identificacion";
    protected static final String COL_NO_IDENTIFICACION       = "no_identificacion";
    protected static final String COL_NIVEL_ESTUDIO           = "nivel_estudio";
    protected static final String COL_OCUPACION               = "ocupacion";
    protected static final String COL_ESTADO_CIVIL            = "estado_civil";
    protected static final String COL_BIENES                  = "bienes";
    protected static final String COL_ESTATUS_RECHAZO         = "estatus_rechazo";
    protected static final String COL_COMENTARIO_RECHAZO      = "comentario_rechazo";
    protected static final String COL_ESTATUS_COMPLETADO      = "estatus_completado";
    protected static final String COL_ID_SOLICITUD_INTEGRANTE = "id_solicitud_integrante";

    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("id_credito")
    @Expose
    private Integer idCredito;

    @SerializedName("cargo")
    @Expose
    private Integer cargo;

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

    @SerializedName("tipo_identificacion")
    @Expose
    private String tipoIdentificacion;

    @SerializedName("no_identificacion")
    @Expose
    private String noIdentificacion;

    @SerializedName("nivel_estudio")
    @Expose
    private String nivelEstudio;

    @SerializedName("ocupacion")
    @Expose
    private String ocupacion;

    @SerializedName("estado_civil")
    @Expose
    private String estadoCivil;

    @SerializedName("bienes")
    @Expose
    private Integer bienes;

    @SerializedName("estatus_rechazo")
    @Expose
    private Integer estatusRechazo;

    @SerializedName("comentario_rechazo")
    @Expose
    private String comentarioRechazo;

    @SerializedName("estatus_completado")
    @Expose
    private Integer estatusCompletado;

    @SerializedName("id_solicitud_integrante")
    @Expose
    private Integer idSolicitudIntegrante;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdCredito() {
        return idCredito;
    }

    public void setIdCredito(Integer idCredito) {
        this.idCredito = idCredito;
    }

    public Integer getCargo() {
        return cargo;
    }

    public void setCargo(Integer cargo) {
        this.cargo = cargo;
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

    public String getOcupacion() {
        return ocupacion;
    }

    public void setOcupacion(String ocupacion) {
        this.ocupacion = ocupacion;
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

    public Integer getIdSolicitudIntegrante() {
        return idSolicitudIntegrante;
    }

    public void setIdSolicitudIntegrante(Integer idSolicitudIntegrante) {
        this.idSolicitudIntegrante = idSolicitudIntegrante;
    }

    public String getNombreCompleto()
    {
        String nombreCompleto = "";

        if(nombre != null) nombreCompleto += nombre;
        if(paterno != null) nombreCompleto += " " + paterno;
        if(materno != null) nombreCompleto += " " + materno;

        return nombreCompleto.trim();
    }

    @Override
    public void fill(Cursor row) {
        this.row = row;

        id        = getInt(COL_ID);
        idCredito = getInt(COL_ID_CREDITO);
        cargo     = getInt(COL_CARGO);
        nombre    = getString(COL_NOMBRE);
        paterno   = getString(COL_PATERNO);
        materno   = getString(COL_MATERNO);
        fechaNacimiento    = getString(COL_FECHA_NACIMIENTO);
        edad               = getString(COL_EDAD);
        genero             = getInt(COL_GENERO);
        estadoNacimiento   = getString(COL_ESTADO_NACIMIENTO);
        rfc                = getString(COL_RFC);
        curp               = getString(COL_CURP);
        curpDigitoVeri     = getString(COL_CURP_DIGITO_VERI);
        tipoIdentificacion = getString(COL_TIPO_IDENTIFICACION);
        noIdentificacion   = getString(COL_NO_IDENTIFICACION);
        nivelEstudio       = getString(COL_NIVEL_ESTUDIO);
        ocupacion          = getString(COL_OCUPACION);
        estadoCivil        = getString(COL_ESTADO_CIVIL);
        bienes             = getInt(COL_BIENES);
        estatusRechazo     = getInt(COL_ESTATUS_RECHAZO);
        comentarioRechazo  = getString(COL_COMENTARIO_RECHAZO);
        estatusCompletado  = getInt(COL_ESTATUS_COMPLETADO);
        idSolicitudIntegrante = getInt(COL_ID_SOLICITUD_INTEGRANTE);
    }
}
