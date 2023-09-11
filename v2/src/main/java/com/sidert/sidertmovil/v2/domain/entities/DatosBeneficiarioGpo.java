package com.sidert.sidertmovil.v2.domain.entities;

import java.io.Serializable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tbl_datos_beneficiario_gpo")
public final class DatosBeneficiarioGpo implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "idBeneficiario")
    private Long idbeneficiario;
    @ColumnInfo(name = "id_solicitud")
    private Integer idSolicitud;
    @ColumnInfo(name = "id_solicitud_integrante")
    private Integer idSolicitudIntegrante;
    @ColumnInfo(name = "id_cliente")
    private Integer idCliente;
    @ColumnInfo(name = "id_integrante")
    private Integer idIntegrante;
    @ColumnInfo(name = "id_grupo")
    private Integer idGrupo;
    @ColumnInfo(name = "nombre")
    private String nombre;
    @ColumnInfo(name = "paterno")
    private String paterno;
    @ColumnInfo(name = "materno")
    private String materno;
    @ColumnInfo(name = "parentesco")
    private String parentesco;
    @ColumnInfo(name = "serieid")
    private String serieid;


// Getters & Setters

    public Long getIdbeneficiario(){
        return this.idbeneficiario;
    }

    public void setIdbeneficiario(Long idbeneficiario){
        this.idbeneficiario = idbeneficiario;
    }
    public Integer getIdSolicitud(){
        return this.idSolicitud;
    }

    public void setIdSolicitud(Integer idSolicitud){
        this.idSolicitud = idSolicitud;
    }
    public Integer getIdSolicitudIntegrante(){
        return this.idSolicitudIntegrante;
    }

    public void setIdSolicitudIntegrante(Integer idSolicitudIntegrante){
        this.idSolicitudIntegrante = idSolicitudIntegrante;
    }
    public Integer getIdCliente(){
        return this.idCliente;
    }

    public void setIdCliente(Integer idCliente){
        this.idCliente = idCliente;
    }
    public Integer getIdIntegrante(){
        return this.idIntegrante;
    }

    public void setIdIntegrante(Integer idIntegrante){
        this.idIntegrante = idIntegrante;
    }
    public Integer getIdGrupo(){
        return this.idGrupo;
    }

    public void setIdGrupo(Integer idGrupo){
        this.idGrupo = idGrupo;
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
    public String getParentesco(){
        return this.parentesco;
    }

    public void setParentesco(String parentesco){
        this.parentesco = parentesco;
    }
    public String getSerieid(){
        return this.serieid;
    }

    public void setSerieid(String serieid){
        this.serieid = serieid;
    }

}