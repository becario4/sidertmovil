package com.sidert.sidertmovil.models;

import java.io.Serializable;

public class ModelGeolocalizacion implements Serializable {

    private int id;
    private String asesor_nombre;
    private int tipo_form;
    private String nombre;
    private String num_solicitud;
    private String num_prestamo;
    private String fecha_desembolso;
    private String direccion;
    private String colonia;
    private String res_uno, res_dos, res_tres;
    private String fecha_env;
    private int status;
    private int total_integrantes = 0;
    private int total_contestadas = 0;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAsesor_nombre() {
        return asesor_nombre;
    }

    public void setAsesor_nombre(String asesor_nombre) {
        this.asesor_nombre = asesor_nombre;
    }

    public int getTipo_form() {
        return tipo_form;
    }

    public void setTipo_form(int tipo_form) {
        this.tipo_form = tipo_form;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNum_solicitud() {
        return num_solicitud;
    }

    public void setNum_solicitud(String num_solicitud) {
        this.num_solicitud = num_solicitud;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getColonia() {
        return colonia;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    public String getRes_uno() {
        return res_uno;
    }

    public void setRes_uno(String res_uno) {
        this.res_uno = res_uno;
    }

    public String getRes_dos() {
        return res_dos;
    }

    public void setRes_dos(String res_dos) {
        this.res_dos = res_dos;
    }

    public String getRes_tres() {
        return res_tres;
    }

    public void setRes_tres(String res_tres) {
        this.res_tres = res_tres;
    }

    public String getFecha_env() {
        return fecha_env;
    }

    public void setFecha_env(String fecha_env) {
        this.fecha_env = fecha_env;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getTotal_integrantes() {
        return total_integrantes;
    }

    public void setTotal_integrantes(int total_integrantes) {
        this.total_integrantes = total_integrantes;
    }

    public int getTotal_contestadas() {
        return total_contestadas;
    }

    public void setTotal_contestadas(int total_contestadas) {
        this.total_contestadas = total_contestadas;
    }

    public String getNum_prestamo() {
        return num_prestamo;
    }

    public void setNum_prestamo(String num_prestamo) {
        this.num_prestamo = num_prestamo;
    }

    public String getFecha_desembolso() {
        return fecha_desembolso;
    }

    public void setFecha_desembolso(String fecha_desembolso) {
        this.fecha_desembolso = fecha_desembolso;
    }
}
