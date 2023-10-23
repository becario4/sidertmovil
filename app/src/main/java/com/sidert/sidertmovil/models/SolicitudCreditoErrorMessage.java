package com.sidert.sidertmovil.models;

import com.google.common.base.Objects;

import java.io.Serializable;

public class SolicitudCreditoErrorMessage
        implements Serializable {

    private String mensaje;
    private String error;

    public SolicitudCreditoErrorMessage(String mensaje, String error) {
        this.mensaje = mensaje;
        this.error = error;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SolicitudCreditoErrorMessage that = (SolicitudCreditoErrorMessage) o;
        return Objects.equal(mensaje, that.mensaje) && Objects.equal(error, that.error);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(mensaje, error);
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
