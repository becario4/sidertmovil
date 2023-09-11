package com.sidert.sidertmovil.v2.domain.entities;

import java.io.Serializable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "geolocalizacion_t")
public final class Geolocalizacion implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    private Long id;
    @ColumnInfo(name = "ficha_id")
    private String fichaId;
    @ColumnInfo(name = "asesor_nombre")
    private String asesorNombre;
    @ColumnInfo(name = "tipo_ficha")
    private Integer tipoFicha;
    @ColumnInfo(name = "nombre")
    private String nombre;
    @ColumnInfo(name = "num_solicitud")
    private String numSolicitud;
    @ColumnInfo(name = "clie_gpo_id")
    private String clieGpoId;
    @ColumnInfo(name = "clie_gpo_clv")
    private String clieGpoClv;
    @ColumnInfo(name = "direccion")
    private String direccion;
    @ColumnInfo(name = "colonia")
    private String colonia;
    @ColumnInfo(name = "fecha_ent")
    private String fechaEnt;
    @ColumnInfo(name = "fecha_ven")
    private String fechaVen;
    @ColumnInfo(name = "data")
    private String data;
    @ColumnInfo(name = "res_uno")
    private String resUno;
    @ColumnInfo(name = "res_dos")
    private String resDos;
    @ColumnInfo(name = "res_tres")
    private String resTres;
    @ColumnInfo(name = "fecha_env_uno")
    private String fechaEnvUno;
    @ColumnInfo(name = "fecha_env_dos")
    private String fechaEnvDos;
    @ColumnInfo(name = "fecha_env_tres")
    private String fechaEnvTres;
    @ColumnInfo(name = "fecha_env")
    private String fechaEnv;
    @ColumnInfo(name = "fecha_ter")
    private String fechaTer;
    @ColumnInfo(name = "status")
    private Integer status;
    @ColumnInfo(name = "create_at")
    private String createAt;


// Getters & Setters

    public Long getId(){
        return this.id;
    }

    public void setId(Long id){
        this.id = id;
    }
    public String getFichaId(){
        return this.fichaId;
    }

    public void setFichaId(String fichaId){
        this.fichaId = fichaId;
    }
    public String getAsesorNombre(){
        return this.asesorNombre;
    }

    public void setAsesorNombre(String asesorNombre){
        this.asesorNombre = asesorNombre;
    }
    public Integer getTipoFicha(){
        return this.tipoFicha;
    }

    public void setTipoFicha(Integer tipoFicha){
        this.tipoFicha = tipoFicha;
    }
    public String getNombre(){
        return this.nombre;
    }

    public void setNombre(String nombre){
        this.nombre = nombre;
    }
    public String getNumSolicitud(){
        return this.numSolicitud;
    }

    public void setNumSolicitud(String numSolicitud){
        this.numSolicitud = numSolicitud;
    }
    public String getClieGpoId(){
        return this.clieGpoId;
    }

    public void setClieGpoId(String clieGpoId){
        this.clieGpoId = clieGpoId;
    }
    public String getClieGpoClv(){
        return this.clieGpoClv;
    }

    public void setClieGpoClv(String clieGpoClv){
        this.clieGpoClv = clieGpoClv;
    }
    public String getDireccion(){
        return this.direccion;
    }

    public void setDireccion(String direccion){
        this.direccion = direccion;
    }
    public String getColonia(){
        return this.colonia;
    }

    public void setColonia(String colonia){
        this.colonia = colonia;
    }
    public String getFechaEnt(){
        return this.fechaEnt;
    }

    public void setFechaEnt(String fechaEnt){
        this.fechaEnt = fechaEnt;
    }
    public String getFechaVen(){
        return this.fechaVen;
    }

    public void setFechaVen(String fechaVen){
        this.fechaVen = fechaVen;
    }
    public String getData(){
        return this.data;
    }

    public void setData(String data){
        this.data = data;
    }
    public String getResUno(){
        return this.resUno;
    }

    public void setResUno(String resUno){
        this.resUno = resUno;
    }
    public String getResDos(){
        return this.resDos;
    }

    public void setResDos(String resDos){
        this.resDos = resDos;
    }
    public String getResTres(){
        return this.resTres;
    }

    public void setResTres(String resTres){
        this.resTres = resTres;
    }
    public String getFechaEnvUno(){
        return this.fechaEnvUno;
    }

    public void setFechaEnvUno(String fechaEnvUno){
        this.fechaEnvUno = fechaEnvUno;
    }
    public String getFechaEnvDos(){
        return this.fechaEnvDos;
    }

    public void setFechaEnvDos(String fechaEnvDos){
        this.fechaEnvDos = fechaEnvDos;
    }
    public String getFechaEnvTres(){
        return this.fechaEnvTres;
    }

    public void setFechaEnvTres(String fechaEnvTres){
        this.fechaEnvTres = fechaEnvTres;
    }
    public String getFechaEnv(){
        return this.fechaEnv;
    }

    public void setFechaEnv(String fechaEnv){
        this.fechaEnv = fechaEnv;
    }
    public String getFechaTer(){
        return this.fechaTer;
    }

    public void setFechaTer(String fechaTer){
        this.fechaTer = fechaTer;
    }
    public Integer getStatus(){
        return this.status;
    }

    public void setStatus(Integer status){
        this.status = status;
    }
    public String getCreateAt(){
        return this.createAt;
    }

    public void setCreateAt(String createAt){
        this.createAt = createAt;
    }

}