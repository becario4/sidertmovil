package com.sidert.sidertmovil.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.sewoo.jpos.command.ESCPOS;
import com.sewoo.jpos.printer.ESCPOSPrinter;
import com.sewoo.jpos.printer.LKPrint;
import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.models.OrderModel;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class PrintTicket {

    private ESCPOSPrinter posPtr;
    private final char ESC = ESCPOS.ESC;
    private final char LF = ESCPOS.LF;
    private String linea;
    private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.US);
    private String date = "";

    public void WriteTicket (Context ctx, OrderModel ticket) {
        posPtr = new ESCPOSPrinter();
        date = df.format(Calendar.getInstance().getTime());
        HeadTicket(ctx, ticket);
        BodyTicket(ticket);
        FooterTicket(ticket);
    }

    private void HeadTicket (Context ctx, OrderModel ticket){
        try {
            Bitmap bm = BitmapFactory.decodeResource(ctx.getResources(), R.drawable.logo_sidert_impresion);
            posPtr.printBitmap(bm, LKPrint.LK_ALIGNMENT_CENTER);
            if (ticket.getResultPrint() == 0){
                posPtr.printNormal(ESC + "|cA" + ESC + "|bC" + ESC + "|1C" + "Original");

            }else{
                posPtr.printNormal(ESC + "|cA" + ESC + "|bC" + ESC + "|1C" + "Copia");
            }
            dobleEspacio();
            posPtr.printNormal(ESC + "|lA" + ESC + "|bC" + ESC + "|1C" + "Fecha y hora:"+date);
            dobleEspacio();

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void BodyTicket (OrderModel data){
        try {
            String nombreCampo;

            nombreCampo = "Numero De Prestamo: ";
            linea = line(nombreCampo, data.getNumLoan().trim());
            posPtr.printNormal(ESC + "|lA" + ESC + "|bC" + ESC + "|1C" + linea + LF);
            nombreCampo = (data.getExternalID().contains("rg"))?"Numero de Grupo: " : "Numero de Cliente: ";
            linea = line(nombreCampo, data.getNumClient().trim());
            posPtr.printNormal(ESC + "|lA" + ESC + "|bC" + ESC + "|1C" + linea + LF);
            nombreCampo = (data.getExternalID().contains("rg"))?"Nombre del Grupo: " : "Nombre del Cliente: ";
            linea = line(nombreCampo, replaceStr(data.getNameClient().trim()));
            posPtr.printNormal(ESC + "|lA" + ESC + "|bC" + ESC + "|1C" + linea + LF);
            nombreCampo = (data.getExternalID().contains("rg"))?"Monto total del prestamo grupal: " : "Monto del prestamo: ";
            linea = line(nombreCampo, money(String.valueOf(data.getAmountLoan()).trim()));
            posPtr.printNormal(ESC + "|lA" + ESC + "|bC" + ESC + "|1C" + linea + LF);
            nombreCampo = "Monto pago requerido: ";
            linea = line(nombreCampo, money(String.valueOf(data.getAmountRequired()).trim()));
            posPtr.printNormal(ESC + "|lA" + ESC + "|bC" + ESC + "|1C" + linea + LF);
            nombreCampo = "Monto pago realizado: ";
            linea = line(nombreCampo, money(String.valueOf(data.getAmountMade())).trim());
            posPtr.printNormal(ESC + "|lA" + ESC + "|bC" + ESC + "|1C" + linea + LF);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private void FooterTicket(OrderModel data){
        try {
            dobleEspacio();

            if (data.getExternalID().contains("ri") || data.getExternalID().contains("rg") || data.getExternalID().contains("ci") || data.getExternalID().contains("cg")){
                if (data.getResultPrint()== 0){
                    String titleSignature = (data.getExternalID().contains("rg") || data.getExternalID().contains("cg"))?"Firma Tesorero(a):":"Firma Cliente:";
                    posPtr.printNormal(ESC + "|lA" + ESC + "|bC" + ESC + "|1C" + titleSignature);
                    dobleEspacio();
                    posPtr.printNormal(ESC + "|lA" + ESC + "|bC" + ESC + "|1C" + "________________________________");
                    posPtr.printNormal(ESC + "|lA" + ESC + "|bC" + ESC + "|1C" + replaceStr(data.getNameClient().trim()));
                }
                else{
                    posPtr.printNormal(ESC + "|lA" + ESC + "|bC" + ESC + "|1C" + "Firma Asesor:");
                    dobleEspacio();
                    posPtr.printNormal(ESC + "|lA" + ESC + "|bC" + ESC + "|1C" + "________________________________");
                    posPtr.printNormal(ESC + "|lA" + ESC + "|bC" + ESC + "|1C" + replaceStr(data.getNameAsessor().trim()));
                }
            }
            else{
                if (data.getResultPrint() == 0){
                    posPtr.printNormal(ESC + "|lA" + ESC + "|bC" + ESC + "|1C" + "Firma Gestor:");
                    dobleEspacio();
                    posPtr.printNormal(ESC + "|lA" + ESC + "|bC" + ESC + "|1C" + "________________________________");
                    posPtr.printNormal(ESC + "|lA" + ESC + "|bC" + ESC + "|1C" + replaceStr(data.getNameAsessor().trim()));
                }
                else{
                    if (data.getExternalID().contains("cvi")){
                        posPtr.printNormal(ESC + "|lA" + ESC + "|bC" + ESC + "|1C" + "Firma Cliente:");
                        dobleEspacio();
                        posPtr.printNormal(ESC + "|lA" + ESC + "|bC" + ESC + "|1C" + "________________________________");
                        posPtr.printNormal(ESC + "|lA" + ESC + "|bC" + ESC + "|1C" + replaceStr(data.getNameClient().trim()));
                    }
                    else {
                        posPtr.printNormal(ESC + "|lA" + ESC + "|bC" + ESC + "|1C" + "");
                        dobleEspacio();
                        posPtr.printNormal(ESC + "|lA" + ESC + "|bC" + ESC + "|1C" + "________________________________");
                        posPtr.printNormal(ESC + "|lA" + ESC + "|bC" + ESC + "|1C" + "NOMBRE Y FIRMA DEL CLIENTE");
                    }
                }
            }

            if (data.getExternalID().contains("ri") || data.getExternalID().contains("rg") || data.getExternalID().contains("ci") || data.getExternalID().contains("cg")){
                linea = line("Folio: ", "Asesor"+data.getAsessorID()+"-"+1);
            }
            else{
                linea = line("Folio: ", "Gestor"+data.getAsessorID()+"-"+1);
            }

            dobleEspacio();

            posPtr.printNormal(ESC + "|lA" + ESC + "|bC" + ESC + "|1C" + linea);
            dobleEspacio();

            posPtr.printNormal(ESC + "|lA" + ESC + "|bC" + ESC + "|1C" + "En caso de dudas o aclaraciones ");
            posPtr.printNormal(ESC + "|lA" + ESC + "|bC" + ESC + "|1C" + "comuniquese al 01 800 112 6666");

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        dobleEspacio();
        dobleEspacio();

    }

    private String money(String numero) {
        String currency;
        NumberFormat format = NumberFormat.getCurrencyInstance(Locale.US);
        currency = format.format(Double.parseDouble(numero));
        return currency;
    }

    private String line(String left, String rigth){
        int nCaracteres = left.length() + rigth.length();
        String result = left;
        if(nCaracteres <= 32){
            nCaracteres = 32-nCaracteres;
            for(int i = 0; i < nCaracteres; i++){
                result += " ";
            }
            result = result + rigth;
        }else{
            String[] partes = rigth.split(" ");
            nCaracteres = result.length();
            for(int i = 0; i < partes.length; i++){
                nCaracteres = nCaracteres + partes[i].length();
                if(nCaracteres < 32){
                    result += partes[i] + " ";
                    nCaracteres++;
                }else{
                    nCaracteres = nCaracteres - partes[i].length();
                    for(; nCaracteres < 32; nCaracteres++){
                        result += " ";
                    }
                    result += partes[i] + " ";
                }
            }
        }
        return result;
    }

    private void dobleEspacio(){
        try {
            posPtr.printNormal(""+LF + LF);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private String replaceStr (String str){
        String[] value = {"Á", "É", "Í", "Ó", "Ú", "á", "é", "í", "ó", "ú", "Ñ", "ñ"};
        String[] valueReplace = {"A", "E", "I", "O", "U", "a", "e", "i", "o", "u","N", "n"};

        for (int i = 0; i < 12; i++){
            str = str.replace(value[i],valueReplace[i]);
        }
        return str;
    }
}
