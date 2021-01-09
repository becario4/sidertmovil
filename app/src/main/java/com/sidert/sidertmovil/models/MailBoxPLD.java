package com.sidert.sidertmovil.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MailBoxPLD {

    @SerializedName("serie_id")
    @Expose
    private String serieId;

    @SerializedName("fecha")
    @Expose
    private String fecha;

    @SerializedName("asunto")
    @Expose
    private String asunto;

    @SerializedName("asunto_id")
    @Expose
    private Long asuntoId;

    @SerializedName("nombre_denunciado")
    @Expose
    private String nombreDenunciado;

    @SerializedName("puesto_denunciado")
    @Expose
    private String puestoDenunciado;

    @SerializedName("concepto")
    @Expose
    private String concepto;

    public MailBoxPLD(String serieId,
                      String fecha,
                      String nombre_denunciado,
                      String puesto_denunciado,
                      String asunto,
                      Long asuntoId,
                      String concepto) {
        this.serieId = serieId;
        this.fecha = fecha;
        this.nombreDenunciado = nombre_denunciado;
        this.puestoDenunciado = puesto_denunciado;
        this.asunto = asunto;
        this.asuntoId = asuntoId;
        this.concepto = concepto;
    }



}
