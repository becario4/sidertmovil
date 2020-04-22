package com.sidert.sidertmovil.models;

public class MGestionada {

    private String idGestion;
    private String fechaGestion;
    private String resultado;
    private String contacto;
    private String comentarioBanco;
    private String monto;

    public String getIdGestion() {
        return idGestion;
    }

    public void setIdGestion(String idGestion) {
        this.idGestion = idGestion;
    }

    public String getFechaGestion() {
        return fechaGestion;
    }

    public void setFechaGestion(String fechaGestion) {
        this.fechaGestion = fechaGestion;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public String getComentarioBanco() {
        return comentarioBanco;
    }

    public void setComentarioBanco(String comentarioBanco) {
        this.comentarioBanco = comentarioBanco;
    }

    public String getMonto() {
        return monto;
    }

    public void setMonto(String monto) {
        this.monto = monto;
    }
}
