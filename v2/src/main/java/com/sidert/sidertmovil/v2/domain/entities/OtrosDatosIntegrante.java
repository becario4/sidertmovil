package com.sidert.sidertmovil.v2.domain.entities;

import java.io.Serializable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tbl_otros_datos_integrante")
public final class OtrosDatosIntegrante implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_otro")
    private Long idOtro;
    @ColumnInfo(name = "id_integrante")
    private Integer idIntegrante;
    @ColumnInfo(name = "clasificacion_riesgo")
    private String clasificacionRiesgo;
    @ColumnInfo(name = "medio_contacto")
    private String medioContacto;
    @ColumnInfo(name = "email")
    private String email;
    @ColumnInfo(name = "estado_cuenta")
    private String estadoCuenta;
    @ColumnInfo(name = "estatus_integrante")
    private Integer estatusIntegrante;
    @ColumnInfo(name = "monto_solicitado")
    private String montoSolicitado;
    @ColumnInfo(name = "casa_reunion")
    private Integer casaReunion;
    @ColumnInfo(name = "firma")
    private String firma;
    @ColumnInfo(name = "estatus_completado")
    private Integer estatusCompletado;
    @ColumnInfo(name = "latitud")
    private String latitud;
    @ColumnInfo(name = "longitud")
    private String longitud;
    @ColumnInfo(name = "located_at")
    private String locatedAt;
    @ColumnInfo(name = "tiene_firma",defaultValue="'SI'")
    private String tieneFirma;
    @ColumnInfo(name = "nombre_quien_firma",defaultValue="''")
    private String nombreQuienFirma;
    @ColumnInfo(name = "monto_refinanciar",defaultValue="''")
    private String montoRefinanciar;
    @ColumnInfo(name = "id_campana",defaultValue="'0'")
    private String idCampana;


// Getters & Setters

    public Long getIdOtro(){
        return this.idOtro;
    }

    public void setIdOtro(Long idOtro){
        this.idOtro = idOtro;
    }
    public Integer getIdIntegrante(){
        return this.idIntegrante;
    }

    public void setIdIntegrante(Integer idIntegrante){
        this.idIntegrante = idIntegrante;
    }
    public String getClasificacionRiesgo(){
        return this.clasificacionRiesgo;
    }

    public void setClasificacionRiesgo(String clasificacionRiesgo){
        this.clasificacionRiesgo = clasificacionRiesgo;
    }
    public String getMedioContacto(){
        return this.medioContacto;
    }

    public void setMedioContacto(String medioContacto){
        this.medioContacto = medioContacto;
    }
    public String getEmail(){
        return this.email;
    }

    public void setEmail(String email){
        this.email = email;
    }
    public String getEstadoCuenta(){
        return this.estadoCuenta;
    }

    public void setEstadoCuenta(String estadoCuenta){
        this.estadoCuenta = estadoCuenta;
    }
    public Integer getEstatusIntegrante(){
        return this.estatusIntegrante;
    }

    public void setEstatusIntegrante(Integer estatusIntegrante){
        this.estatusIntegrante = estatusIntegrante;
    }
    public String getMontoSolicitado(){
        return this.montoSolicitado;
    }

    public void setMontoSolicitado(String montoSolicitado){
        this.montoSolicitado = montoSolicitado;
    }
    public Integer getCasaReunion(){
        return this.casaReunion;
    }

    public void setCasaReunion(Integer casaReunion){
        this.casaReunion = casaReunion;
    }
    public String getFirma(){
        return this.firma;
    }

    public void setFirma(String firma){
        this.firma = firma;
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
    public String getTieneFirma(){
        return this.tieneFirma;
    }

    public void setTieneFirma(String tieneFirma){
        this.tieneFirma = tieneFirma;
    }
    public String getNombreQuienFirma(){
        return this.nombreQuienFirma;
    }

    public void setNombreQuienFirma(String nombreQuienFirma){
        this.nombreQuienFirma = nombreQuienFirma;
    }
    public String getMontoRefinanciar(){
        return this.montoRefinanciar;
    }

    public void setMontoRefinanciar(String montoRefinanciar){
        this.montoRefinanciar = montoRefinanciar;
    }
    public String getIdCampana(){
        return this.idCampana;
    }

    public void setIdCampana(String idCampana){
        this.idCampana = idCampana;
    }

}