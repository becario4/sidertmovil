package com.sidert.sidertmovil.models;

import com.google.gson.annotations.SerializedName;

public class MailBoxPLD {

    @SerializedName("asesorid")
    private String asessorid;

    @SerializedName("fecha")
    private String date;

    @SerializedName("asunto")
    private String subject;

    @SerializedName("motivo")
    private String reason;

    public MailBoxPLD(String asessorid, String date, String subject, String reason) {
        this.asessorid = asessorid;
        this.date = date;
        this.subject = subject;
        this.reason = reason;
    }

}
