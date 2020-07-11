package com.sidert.sidertmovil.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MailBoxPLD {

    @SerializedName("asesor_id")
    @Expose
    private String asesorId;
    @SerializedName("fecha")
    @Expose
    private String fecha;
    @SerializedName("asunto")
    @Expose
    private String asunto;
    @SerializedName("nombre_denunciado")
    @Expose
    private String nombreDenunciado;
    @SerializedName("puesto_denunciado")
    @Expose
    private String puestoDenunciado;
    @SerializedName("motivo")
    @Expose
    private String motivo;

    public MailBoxPLD(String asessorid,
                      String date,
                      String nombre_denunciado,
                      String puesto_denunciado,
                      String subject,
                      String reason) {
        this.asesorId = asessorid;
        this.fecha = date;
        this.nombreDenunciado = nombre_denunciado;
        this.puestoDenunciado = puesto_denunciado;
        this.asunto = subject;
        this.motivo = reason;

    }

}
