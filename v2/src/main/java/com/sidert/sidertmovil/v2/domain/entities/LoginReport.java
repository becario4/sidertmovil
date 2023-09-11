package com.sidert.sidertmovil.v2.domain.entities;

import java.io.Serializable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "login_report_t")
public final class LoginReport implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    private Long id;
    @ColumnInfo(name = "serie_id")
    private String serieId;
    @ColumnInfo(name = "nombre")
    private String nombre;

    @ColumnInfo(name = "username")
    private String username;

    @ColumnInfo(name = "login_timestamp")
    private String loginTimestamp;
    @ColumnInfo(name = "fecha_env")
    private String fechaEnv;
    @ColumnInfo(name = "status")
    private Integer status;


// Getters & Setters

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSerieId() {
        return this.serieId;
    }

    public void setSerieId(String serieId) {
        this.serieId = serieId;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getLoginTimestamp() {
        return this.loginTimestamp;
    }

    public void setLoginTimestamp(String loginTimestamp) {
        this.loginTimestamp = loginTimestamp;
    }

    public String getFechaEnv() {
        return this.fechaEnv;
    }

    public void setFechaEnv(String fechaEnv) {
        this.fechaEnv = fechaEnv;
    }

    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}