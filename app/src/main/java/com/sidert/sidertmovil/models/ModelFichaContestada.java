package com.sidert.sidertmovil.models;

import java.io.Serializable;

public class ModelFichaContestada implements Serializable {

    private String TipoFormulario;
    private String NombreClienteGpo;
    private String FechaIni;
    private String FechaTer;
    private String FechaEnv;
    private String FechaFin;
    private String TipoFicha;
    private int Posicion;

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

    public String getFechaIni() {
        return FechaIni;
    }

    public void setFechaIni(String fechaIni) {
        FechaIni = fechaIni;
    }

    public String getFechaTer() {
        return FechaTer;
    }

    public void setFechaTer(String fechaTer) {
        FechaTer = fechaTer;
    }

    public String getFechaEnv() {
        return FechaEnv;
    }

    public void setFechaEnv(String fechaEnv) {
        FechaEnv = fechaEnv;
    }

    public String getFechaFin() {
        return FechaFin;
    }

    public void setFechaFin(String fechaFin) {
        FechaFin = fechaFin;
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
}
