package com.sidert.sidertmovil.utils;

import android.util.Log;

import com.sewoo.jpos.command.ESCPOS;
import com.sewoo.jpos.printer.ESCPOSPrinter;

import java.io.UnsupportedEncodingException;
import java.text.NumberFormat;
import java.util.Locale;

public class PrintTicket {

    private static final String TAG = "Recibos";
    private ESCPOSPrinter posPtr;
    private final char ESC = ESCPOS.ESC;
    private final char LF = ESCPOS.LF;
    private String linea;

    public String money(String numero) {
        String currency = "$0.00";
        try {
            NumberFormat format = NumberFormat.getCurrencyInstance(Locale.US);
            currency = format.format(Double.parseDouble(numero));
            //System.out.println("Currency in Canada : " + currency);
        }catch (Exception e){

        }
        return currency;
    }

    public String line(String left, String rigth){
        int nCaracteres = left.length()+rigth.length();
        String result = left;
        if(nCaracteres<=32){
            nCaracteres = 32-nCaracteres;
            for(int i = 0; i<nCaracteres;i++){
                result = result+" ";
            }
            result = result+rigth;
        }else{
            String[] partes = rigth.split(" ");
            nCaracteres = result.length();
            for(int i = 0; i<partes.length; i++){
                nCaracteres = nCaracteres+partes[i].length();
                if(nCaracteres<32){
                    result = result+partes[i]+" ";
                    nCaracteres++;
                }else{
                    nCaracteres = nCaracteres-partes[i].length();
                    for(; nCaracteres<32; nCaracteres++){
                        result = result+" ";
                    }
                    result = result+partes[i]+" ";
                }
                Log.e(TAG,"result: "+result);
            }
        }
        return result;
    }

    public void dobleEspacio(){
        try {
            posPtr.printNormal(""+LF + LF);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
