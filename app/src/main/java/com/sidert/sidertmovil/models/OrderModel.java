package com.sidert.sidertmovil.models;

import java.io.Serializable;

public class OrderModel implements Serializable {

    private String externalID;
    private String asessorID;
    private double amountLoan;
    private double amountRequired;
    private double amountMade;
    private String numClient;
    private String numLoan;
    private String nameClient;
    private String nameAsessor;
    private int resultPrint;

    public OrderModel(String externalID, String asessorID, double amountLoan, double amountRequired, double amountMade, String numClient, String numLoan, String nameClient, String nameAsessor, int resultPrint) {
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

    public double getAmountLoan() {
        return amountLoan;
    }

    public double getAmountRequired() {
        return amountRequired;
    }

    public double getAmountMade() {
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

    public int getResultPrint() {
        return resultPrint;
    }

    public void setResultPrint(int resultPrint) {
        this.resultPrint = resultPrint;
    }
}
