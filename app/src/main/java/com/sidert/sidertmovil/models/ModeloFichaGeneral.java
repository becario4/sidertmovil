package com.sidert.sidertmovil.models;

import java.io.Serializable;

public class ModeloFichaGeneral implements Serializable {

    private String NombreClienteGpo;
    private String Direccion;
    private String FechaPago;
    private String DiaSemana;
    private String TipoFicha;
    private int Posicion;
    private boolean isChecked;

    public String getNombreClienteGpo() {
        return NombreClienteGpo;
    }

    public void setNombreClienteGpo(String nombreClienteGpo) {
        NombreClienteGpo = nombreClienteGpo;
    }

    public String getDireccion() {
        return Direccion;
    }

    public void setDireccion(String direccion) {
        Direccion = direccion;
    }

    public String getFechaPago() {
        return FechaPago;
    }

    public void setFechaPago(String fechaPago) {
        FechaPago = fechaPago;
    }

    public String getDiaSemana() {
        return DiaSemana;
    }

    public void setDiaSemana(String diaSemana) {
        DiaSemana = diaSemana;
    }

    public String getTipoFicha() {
        return TipoFicha;
    }

    public void setTipoFicha(String tipoFicha) {
        TipoFicha = tipoFicha;
    }

    public int getPosicion() {
        return Posicion;
    }

    public void setPosicion(int posicion) {
        Posicion = posicion;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
