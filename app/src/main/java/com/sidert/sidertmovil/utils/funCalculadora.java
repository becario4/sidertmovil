package com.sidert.sidertmovil.utils;

import java.text.NumberFormat;
import java.util.Locale;

public class funCalculadora {

    public static String GetTipoProductoA(String producto){
        String productoA = " ";
        switch (producto){
            case "INDIVIDUALES":
                productoA = "L007";
                break;

            case "GRUPALES":
                productoA = "L008";
                break;
        }
        return productoA;
    }

    public static String GetTipoCliente(String cliente){
        String clienteA = " ";
        switch (cliente){
            case "NUEVO":
                clienteA = "NUEVO";
                break;

            case "RENOVADO":
                clienteA = "RENOVADO";
                break;
        }
        return clienteA;
    }


    public static String moneyFormatA(String cash){
        int money = Integer.parseInt(cash);
        int min = 1000;
        int max = 90000;
        boolean bandera = true;
            if((money >= min && money <= max) && (money%1000 == 0)){
                return cash;
            }else {
                return null;
            }
    }

    public static int getPeriodicidadA(String periodo){
        int periodoA=0;
        if(periodo!=null){
            if(periodo.equals("SEMANAL")){
                periodoA = 7;
            }else if(periodo.equals("CATORCENAL")){
                periodoA = 14;
            }else if(periodo.equals("QUINCENAL")){
                periodoA = 15;
            }else if(periodo.equals("MENSUAL")){
                periodoA = 30;
            }
        }
        return periodoA;
    }

    public static int getAmortizaciones(String periodo, String plazo){
        int plazoA = 0;
        if(periodo != null && plazo != null){
            if(periodo.equals("SEMANAL") && plazo.equals("4 MESES")){
                plazoA = 16;
            }else if(periodo.equals("SEMANAL") && plazo.equals("6 MESES")){
                plazoA = 24;
            }else if(periodo.equals("SEMANAL") && plazo.equals("9 MESES")){
                plazoA = 36;
            }else if(periodo.equals("SEMANAL") && plazo.equals("12 MESES")){
                plazoA = 48;
            }else if(periodo.contains("CATORCENAL")  && plazo.equals("4 MESES")){
                plazoA = 8;
            }else if(periodo.contains("CATORCENAL")  && plazo.equals("6 MESES")){
                plazoA = 12;
            }else if(periodo.contains("CATORCENAL")  && plazo.equals("9 MESES")){
                plazoA = 18;
            }else if(periodo.contains("CATORCENAL")  && plazo.equals("12 MESES")){
                plazoA = 24;
            } else if(periodo.equals("QUINCENAL") && plazo.equals("4 MESES")){
                plazoA = 8;
            }else if( periodo.equals("QUINCENAL") && plazo.equals("6 MESES")){
                plazoA = 12;
            }else if(periodo.equals("QUINCENAL") && plazo.equals("9 MESES")){
                plazoA = 18;
            }else if( periodo.equals("QUINCENAL") && plazo.equals("12 MESES")){
                plazoA = 24;
            }else if(periodo.equals("MENSUAL") && plazo.equals("4 MESES")){
                plazoA = 4;
            }else if(periodo.equals("MENSUAL") && plazo.equals("6 MESES")){
                plazoA = 6;
            }else if(periodo.equals("MENSUAL") && plazo.equals("9 MESES")){
                plazoA = 9;
            }else if(periodo.equals("MENSUAL") && plazo.equals("12 MESES")){
                plazoA = 12;
            }
        }
        return plazoA;
    }

    public static int getAmortizacionesRenovados(String periodo, String plazo){
        int amortizacionA = 0;
        if(periodo != null && plazo != null){
            if(periodo.equals("SEMANAL") && plazo.equals("16 SEMANAS")){
                amortizacionA = 16;
            }else if(periodo.equals("SEMANAL") && plazo.equals("20 SEMANAS")){
                amortizacionA = 20;
            }else if(periodo.equals("SEMANAL") && plazo.equals("24 SEMANAS")){
                amortizacionA = 24;
            }else if(periodo.equals("CATORCENAL") && plazo.equals("8 CATORCENAS")){
                amortizacionA = 8;
            }else if(periodo.equals("CATORCENAL") && plazo.equals("10 CATORCENAS")){
                amortizacionA = 10;
            }else if(periodo.equals("CATORCENAL") && plazo.equals("12 CATORCENAS")) {
                amortizacionA = 12;
            } else if(periodo.equals("QUINCENAL")  && plazo.equals("8 QUINCENAS")){
                amortizacionA = 8;
            }else if(periodo.equals("QUINCENAL") && plazo.equals("10 QUINCENAS")){
                amortizacionA = 10;
            }else if(periodo.equals("QUINCENAL") && plazo.equals("12 QUINCENAS")){
                amortizacionA =12;
            }else if(periodo.equals("MENSUAL") && plazo.equals("6 MESES")){
                amortizacionA = 6;
            }else if(periodo.equals("MENSUAL") && plazo.equals("9 MESES")){
                amortizacionA = 9;
            }else if(periodo.equals("MENSUAL") && plazo.equals("12 MESES")){
                amortizacionA = 12;
            }
        }
        return amortizacionA;
    }

}
