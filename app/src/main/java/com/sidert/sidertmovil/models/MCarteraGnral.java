package com.sidert.sidertmovil.models;

public class MCarteraGnral {

    private String id_cliente = "";
    private String tipo = "";
    private String nombre = "";
    private String tesorera = "";
    private String diaSemana = "";
    private String direccion = "";
    private boolean is_ruta = false;
    private boolean is_obligatorio = false;
    private String tipoPrestamo = "";
    private int parcial = 0;
    private String diasMora = "";

    public String getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(String id_cliente) {
        this.id_cliente = id_cliente;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTesorera() {
        return tesorera;
    }

    public void setTesorera(String tesorera) {
        this.tesorera = tesorera;
    }

    public String getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(String diaSemana) {
        this.diaSemana = diaSemana;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public boolean isIs_ruta() {
        return is_ruta;
    }

    public void setIs_ruta(boolean is_ruta) {
        this.is_ruta = is_ruta;
    }

    public boolean isIs_obligatorio() {
        return is_obligatorio;
    }

    public void setIs_obligatorio(boolean is_obligatorio) {
        this.is_obligatorio = is_obligatorio;
    }

    public String getTipoPrestamo() {
        return tipoPrestamo;
    }

    public void setTipoPrestamo(String tipoPrestamo) {
        this.tipoPrestamo = tipoPrestamo;
    }

    public int getParcial() {
        return parcial;
    }

    public void setParcial(int parcial) {
        this.parcial = parcial;
    }

    public String getDiasMora() {
        return diasMora;
    }

    public void setDiasMora(String diasMora) {
        this.diasMora = diasMora;
    }
}
