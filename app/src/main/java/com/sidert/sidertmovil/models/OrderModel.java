package com.sidert.sidertmovil.models;

import java.io.Serializable;

public class OrderModel implements Serializable {

    private String externalID;
    private String asessorID;
    private float amountLoan;
    private float amountRequired;
    private float amountMade;
    private String numClient;
    private String numLoan;
    private String nameClient;
    private String nameAsessor;
    private String resultPrint;

    public OrderModel(String externalID, String asessorID, float amountLoan, float amountRequired, float amountMade, String numClient, String numLoan, String nameClient, String nameAsessor, String resultPrint) {
        this.externalID = externalID;
        this.asessorID = asessorID;
        this.amountLoan = amountLoan;
        this.amountRequired = amountRequired;
        this.amountMade = amountMade;
        this.numClient = numClient;
        this.numLoan = numLoan;
        this.nameClient = nameClient;
        this.nameAsessor = nameAsessor;
        this.resultPrint = resultPrint;
    }

    public String getExternalID() {
        return externalID;
    }

    public String getAsessorID() {
        return asessorID;
    }

    public float getAmountLoan() {
        return amountLoan;
    }

    public float getAmountRequired() {
        return amountRequired;
    }

    public float getAmountMade() {
        return amountMade;
    }

    public String getNumClient() {
        return numClient;
    }

    public String getNumLoan() {
        return numLoan;
    }

    public String getNameClient() {
        return nameClient;
    }

    public String getNameAsessor() {
        return nameAsessor;
    }

    public String getResultPrint() {
        return resultPrint;
    }
}
