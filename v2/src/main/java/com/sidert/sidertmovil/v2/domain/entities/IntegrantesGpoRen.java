package com.sidert.sidertmovil.v2.domain.entities;

import java.io.Serializable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tbl_integrantes_gpo_ren")
public final class IntegrantesGpoRen implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private Long remoteId;
    @ColumnInfo(name = "id_credito")
    private Integer idCredito;
    @ColumnInfo(name = "cargo")
    private Integer cargo;
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
    @ColumnInfo(name = "tipo_identificacion")
    private String tipoIdentificacion;
    @ColumnInfo(name = "no_identificacion")
    private String noIdentificacion;
    @ColumnInfo(name = "nivel_estudio")
    private String nivelEstudio;
    @ColumnInfo(name = "ocupacion")
    private String ocupacion;
    @ColumnInfo(name = "estado_civil")
    private String estadoCivil;
    @ColumnInfo(name = "bienes")
    private Integer bienes;
    @ColumnInfo(name = "estatus_rechazo")
    private Integer estatusRechazo;
    @ColumnInfo(name = "comentario_rechazo")
    private String comentarioRechazo;
    @ColumnInfo(name = "estatus_completado")
    private Integer estatusCompletado;
    @ColumnInfo(name = "id_solicitud_integrante")
    private Integer idSolicitudIntegrante;
    @ColumnInfo(name = "is_nuevo")
    private Integer isNuevo;
    @ColumnInfo(name = "cliente_id")
    private String clienteId;
    @ColumnInfo(name = "ciclo",defaultValue="0")
    private Integer ciclo;
    @ColumnInfo(name = "monto_prestamo_anterior",defaultValue="'0.00'")
    private String montoPrestamoAnterior;


// Getters & Setters

    public Long getRemoteId(){
        return this.remoteId;
    }

    public void setRemoteId(Long remoteId){
        this.remoteId = remoteId;
    }
    public Integer getIdCredito(){
        return this.idCredito;
    }

    public void setIdCredito(Integer idCredito){
        this.idCredito = idCredito;
    }
    public Integer getCargo(){
        return this.cargo;
    }

    public void setCargo(Integer cargo){
        this.cargo = cargo;
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
    public String getOcupacion(){
        return this.ocupacion;
    }

    public void setOcupacion(String ocupacion){
        this.ocupacion = ocupacion;
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
    public Integer getIdSolicitudIntegrante(){
        return this.idSolicitudIntegrante;
    }

    public void setIdSolicitudIntegrante(Integer idSolicitudIntegrante){
        this.idSolicitudIntegrante = idSolicitudIntegrante;
    }
    public Integer getIsNuevo(){
        return this.isNuevo;
    }

    public void setIsNuevo(Integer isNuevo){
        this.isNuevo = isNuevo;
    }
    public String getClienteId(){
        return this.clienteId;
    }

    public void setClienteId(String clienteId){
        this.clienteId = clienteId;
    }
    public Integer getCiclo(){
        return this.ciclo;
    }

    public void setCiclo(Integer ciclo){
        this.ciclo = ciclo;
    }
    public String getMontoPrestamoAnterior(){
        return this.montoPrestamoAnterior;
    }

    public void setMontoPrestamoAnterior(String montoPrestamoAnterior){
        this.montoPrestamoAnterior = montoPrestamoAnterior;
    }

}