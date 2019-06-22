package com.sidert.sidertmovil.models;

public class ImpressionsFields {

    private String externalID;
    private String asessorID;
    private String type;
    private String folio;
    private String amount;
    private String status;
    private String generatedAt;
    private String sendedAt;

    public ImpressionsFields(String externalID, String asessorID, String type, String folio, String amount, String status, String generatedAt, String sendedAt) {
        this.externalID = externalID;
        this.asessorID = asessorID;
        this.type = type;
        this.folio = folio;
        this.amount = amount;
        this.status = status;
        this.generatedAt = generatedAt;
        this.sendedAt = sendedAt;
    }

    public String getExternalID() {
        return externalID;
    }

    public String getAsessorID() {
        return asessorID;
    }

    public String getType() {
        return type;
    }

    public String getFolio() {
        return folio;
    }

    public String getAmount() {
        return amount;
    }

    public String getStatus() {
        return status;
    }

    public String getGeneratedAt() {
        return generatedAt;
    }

    public String getSendedAt() {
        return sendedAt;
    }
}
