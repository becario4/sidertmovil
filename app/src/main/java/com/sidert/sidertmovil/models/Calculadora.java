package com.sidert.sidertmovil.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Calculadora implements Serializable{

    @SerializedName("periodopago")
    @Expose
    private Double periodoPago;
    @SerializedName("totalpago")
    @Expose
    private Double montoPago;

    public Double getPeriodoPago() {
        return periodoPago;
    }

    public void setPeriodoPago(Double periodoPago) {
        this.periodoPago = periodoPago;
    }

    public Double getMontoPago() {
        return montoPago;
    }

    public void setMontoPago(Double montoPago) {
        this.montoPago = montoPago;
    }
}

