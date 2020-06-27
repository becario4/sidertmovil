package com.sidert.sidertmovil.models;

public class MGestionada {

    private String idGestion;
    private String fechaGestion;
    private String resultado;
    private String contacto;
    private String comentarioBanco;
    private String monto;
    private String estatusCancel;
    private String estatisGestion;
    private String comentario = "";

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

    public String getEstatusCancel() {
        return estatusCancel;
    }

    public void setEstatusCancel(String estatusCancel) {
        this.estatusCancel = estatusCancel;
    }

    public String getEstatisGestion() {
        return estatisGestion;
    }

    public void setEstatisGestion(String estatisGestion) {
        this.estatisGestion = estatisGestion;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }
}
