package com.sidert.sidertmovil.models;

import android.content.ContentValues;

import com.sidert.sidertmovil.utils.Constants;

import java.io.Serializable;

public class ModeloFichaGeneral implements Serializable {

    private String TipoFormulario;
    private String NombreClienteGpo;
    private String NombreTesorera;
    private String Direccion;
    private String FechaPago;
    private String DiaSemana;
    private String TipoFicha;
    private int Posicion;
    private boolean isChecked;
    private String status;

    public String getTipoFormulario() {
        return TipoFormulario;
    }

    public void setTipoFormulario(String tipoFormulario) {
        TipoFormulario = tipoFormulario;
    }

    public String getNombreClienteGpo() {
        return NombreClienteGpo;
    }

    public void setNombreClienteGpo(String nombreClienteGpo) {
        NombreClienteGpo = nombreClienteGpo;
    }

    public String getNombreTesorera() {
        return NombreTesorera;
    }

    public void setNombreTesorera(String nombreTesorera) {
        NombreTesorera = nombreTesorera;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
