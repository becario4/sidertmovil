package com.sidert.sidertmovil.utils;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.sewoo.jpos.command.ESCPOS;
import com.sewoo.jpos.printer.ESCPOSPrinter;
import com.sewoo.jpos.printer.LKPrint;
import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.database.DBhelper;
import com.sidert.sidertmovil.models.MImpresion;
import com.sidert.sidertmovil.models.MReimpresion;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Locale;

import static com.sidert.sidertmovil.utils.Constants.TIMESTAMP;

public class ReprintTicket {

    private ESCPOSPrinter posPtr;
    private final char ESC = ESCPOS.ESC;
    private final char LF = ESCPOS.LF;
    private String linea;

    private DBhelper dBhelper;
    private SQLiteDatabase db;
    private SessionManager session;

    public void WriteTicket (Context ctx, MReimpresion ticket) {
        posPtr = new ESCPOSPrinter();
        this.dBhelper = new DBhelper(ctx);
        db = dBhelper.getWritableDatabase();
        session = new SessionManager(ctx);

        HashMap<Integer, String> params = new HashMap<>();
        params.put(0,ticket.getNumeroPrestamo()+"-"+ticket.getIdGestion());
        params.put(1, ticket.getTipo_impresion());
        params.put(2, ticket.getFolio());
        params.put(3, ticket.getMonto());
        params.put(4, ticket.getClaveCliente());
        params.put(5, session.getUser().get(0));
        params.put(6, session.getUser().get(0));
        params.put(7, Miscellaneous.ObtenerFecha("timestamp"));
        params.put(8, "");
        params.put(9, "0");
        params.put(10, ticket.getNumeroPrestamo());
        params.put(11, "");

        dBhelper.saveReimpresionVigente(db, params);

        Servicios_Sincronizado ss = new Servicios_Sincronizado();
        ss.SendReimpresionesVi(ctx, false);

        HeadTicket(ctx, ticket);
        BodyTicket(ticket);
        FooterTicket(ticket);
    }

    private void HeadTicket (Context ctx, MReimpresion ticket){
        try {
            Bitmap bm;
            if (ticket.getTipoPrestamo().contains("VENCIDA"))
                bm = BitmapFactory.decodeResource(ctx.getResources(), R.drawable.logo_cv);
            else
                bm = BitmapFactory.decodeResource(ctx.getResources(), R.drawable.logo_impresion);
            posPtr.printBitmap(bm, LKPrint.LK_ALIGNMENT_CENTER);
            if (ticket.getTipo_impresion().equals("O")){
                posPtr.printNormal(ESC + "|cA" + ESC + "|bC" + ESC + "|1C" + "Original");

            }else{
                posPtr.printNormal(ESC + "|cA" + ESC + "|bC" + ESC + "|1C" + "Copia");
            }
            dobleEspacio();
            posPtr.printNormal(ESC + "|cA" + ESC + "|bC" + ESC + "|4C" + "REIMPRESION");
            dobleEspacio();
            posPtr.printNormal(ESC + "|lA" + ESC + "|bC" + ESC + "|1C" + "Fecha y hora:"+Miscellaneous.ObtenerFecha(TIMESTAMP));
            dobleEspacio();

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void BodyTicket (MReimpresion data){
        try {

            if (data.getTipoPrestamo().contains("VENCIDA")) {
                linea = line("Empresa:", "SERVICIOS INTEGRALES PARA EL DESARROLLO RURAL DEL TROPICO SA DE CV SOFOM ENR.(SIDERT)");
                posPtr.printNormal(ESC + "|lA" + ESC + "|bC" + ESC + "|1C" + linea + LF);
            }

            String nombreCampo;

            if (data.getTipoPrestamo().equals("VIGENTE") || data.getTipoPrestamo().equals("COBRANZA")) {

                nombreCampo = "Numero De Prestamo: ";
                linea = line(nombreCampo, data.getNumeroPrestamo().trim());
                posPtr.printNormal(ESC + "|lA" + ESC + "|bC" + ESC + "|1C" + linea + LF);
                nombreCampo = (data.getTipoGestion().equals("GRUPAL")) ? "Clave de Grupo: " : "Numero de Cliente: ";
                linea = line(nombreCampo, data.getNumeroCliente().trim());
                posPtr.printNormal(ESC + "|lA" + ESC + "|bC" + ESC + "|1C" + linea + LF);
                nombreCampo = (data.getTipoGestion().equals("GRUPAL")) ? "Nombre del Grupo: " : "Nombre del Cliente: ";
                linea = line(nombreCampo, replaceStr(data.getNombre().trim()));
                posPtr.printNormal(ESC + "|lA" + ESC + "|bC" + ESC + "|1C" + linea + LF);
                nombreCampo = (data.getTipoGestion().equals("GRUPAL")) ? "Monto total del prestamo grupal: " : "Monto del prestamo: ";
                linea = line(nombreCampo, money(String.valueOf(data.getMontoPrestamo()).trim()));
                posPtr.printNormal(ESC + "|lA" + ESC + "|bC" + ESC + "|1C" + linea + LF);
                nombreCampo = "Monto pago requerido: ";
                linea = line(nombreCampo, money(String.valueOf(data.getPagoRequerido()).trim()));
                posPtr.printNormal(ESC + "|lA" + ESC + "|bC" + ESC + "|1C" + linea + LF);
                nombreCampo = "Monto pago realizado: ";
                linea = line(nombreCampo, money(String.valueOf(data.getMonto())).trim());
                posPtr.printNormal(ESC + "|lA" + ESC + "|bC" + ESC + "|1C" + linea + LF);

                if (!data.getFechaUltimoPago().isEmpty()) {
                    //----------------- nuevos campos ----------------
                    nombreCampo = "Fecha Ultimo Pago: ";
                    linea = line(nombreCampo, data.getFechaUltimoPago());
                    posPtr.printNormal(ESC + "|lA" + ESC + "|bC" + ESC + "|1C" + linea + LF);

                    nombreCampo = "Monto Ultimo Pago: ";
                    linea = line(nombreCampo, money(String.valueOf(data.getMontoUltimoPago())).trim());
                    posPtr.printNormal(ESC + "|lA" + ESC + "|bC" + ESC + "|1C" + linea + LF);
                    //------------------------------------------------
                }

            }
            else{
                if (data.getTipoGestion().equals("INDIVIDUAL")){//individual vencida
                    nombreCampo = "Numero De Prestamo: ";
                    linea = line(nombreCampo, data.getNumeroPrestamo().trim());
                    posPtr.printNormal(ESC + "|lA" + ESC + "|bC" + ESC + "|1C" + linea + LF);
                    nombreCampo = "Numero de Cliente: ";
                    linea = line(nombreCampo, data.getNumeroCliente().trim());
                    posPtr.printNormal(ESC + "|lA" + ESC + "|bC" + ESC + "|1C" + linea + LF);
                    nombreCampo = "Nombre del Cliente: ";
                    linea = line(nombreCampo, replaceStr(data.getNombre().trim()));
                    posPtr.printNormal(ESC + "|lA" + ESC + "|bC" + ESC + "|1C" + linea + LF);
                    nombreCampo = "Monto pago realizado: ";
                    linea = line(nombreCampo, money(String.valueOf(data.getMonto())).trim());
                    posPtr.printNormal(ESC + "|lA" + ESC + "|bC" + ESC + "|1C" + linea + LF);
                }
                else{ //Grupal vencida
                    nombreCampo = "Numero De Prestamo: ";
                    linea = line(nombreCampo, data.getNumeroPrestamo().trim());
                    posPtr.printNormal(ESC + "|lA" + ESC + "|bC" + ESC + "|1C" + linea + LF);
                    nombreCampo = "Numero de Cliente: ";
                    linea = line(nombreCampo, data.getNumeroCliente().trim());
                    posPtr.printNormal(ESC + "|lA" + ESC + "|bC" + ESC + "|1C" + linea + LF);
                    nombreCampo = "Nombre del Grupo: ";
                    linea = line(nombreCampo, replaceStr(data.getNombreGrupo().trim()));
                    posPtr.printNormal(ESC + "|lA" + ESC + "|bC" + ESC + "|1C" + linea + LF);
                    nombreCampo = "Nombre del Integrante: ";
                    linea = line(nombreCampo, replaceStr(data.getNombre().trim()));
                    posPtr.printNormal(ESC + "|lA" + ESC + "|bC" + ESC + "|1C" + linea + LF);
                    nombreCampo = "Monto pago realizado: ";
                    linea = line(nombreCampo, money(String.valueOf(data.getMonto())).trim());
                    posPtr.printNormal(ESC + "|lA" + ESC + "|bC" + ESC + "|1C" + linea + LF);
                }

            }

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private void FooterTicket(MReimpresion data){
        try {
            dobleEspacio();

            if (data.getTipoPrestamo().equals("VIGENTE") || data.getTipoPrestamo().equals("COBRANZA")){
                if (data.getTipo_impresion().equals("O")){
                    posPtr.printNormal(ESC + "|lA" + ESC + "|bC" + ESC + "|1C" + "Firma Asesor:");
                    dobleEspacio();
                    posPtr.printNormal(ESC + "|lA" + ESC + "|bC" + ESC + "|1C" + "________________________________");
                    posPtr.printNormal(ESC + "|lA" + ESC + "|bC" + ESC + "|1C" + replaceStr(data.getNombreAsesor().trim()));
                }
                else{
                    String titleSignature = (data.getTipoGestion().equals("GRUPAL"))?"Firma Tesorero(a):":"Firma Cliente:";
                    posPtr.printNormal(ESC + "|lA" + ESC + "|bC" + ESC + "|1C" + titleSignature);
                    dobleEspacio();
                    posPtr.printNormal(ESC + "|lA" + ESC + "|bC" + ESC + "|1C" + "________________________________");
                    posPtr.printNormal(ESC + "|lA" + ESC + "|bC" + ESC + "|1C" + replaceStr(data.getNombreFirma().trim()));
                }
            }
            else{
                if (data.getTipo_impresion().equals("O")){
                    posPtr.printNormal(ESC + "|lA" + ESC + "|bC" + ESC + "|1C" + "Firma Gestor:");
                    dobleEspacio();
                    posPtr.printNormal(ESC + "|lA" + ESC + "|bC" + ESC + "|1C" + "________________________________");
                    posPtr.printNormal(ESC + "|lA" + ESC + "|bC" + ESC + "|1C" + replaceStr(data.getNombreAsesor().trim()));
                }
                else{
                    if (data.getTipoPrestamo().equals("VENCIDA") && data .getTipoGestion().equals("INDIVIDUAL")){
                        posPtr.printNormal(ESC + "|lA" + ESC + "|bC" + ESC + "|1C" + "Firma Cliente:");
                        dobleEspacio();
                        posPtr.printNormal(ESC + "|lA" + ESC + "|bC" + ESC + "|1C" + "________________________________");
                        posPtr.printNormal(ESC + "|lA" + ESC + "|bC" + ESC + "|1C" + replaceStr(data.getNombreFirma().trim()));
                    }
                    else {
                        posPtr.printNormal(ESC + "|lA" + ESC + "|bC" + ESC + "|1C" + "Firma Cliente:");
                        dobleEspacio();
                        posPtr.printNormal(ESC + "|lA" + ESC + "|bC" + ESC + "|1C" + "________________________________");
                        posPtr.printNormal(ESC + "|lA" + ESC + "|bC" + ESC + "|1C" + replaceStr(data.getNombreFirma().trim()));

                    }
                }
            }

            if (data.getTipoPrestamo().equals("VIGENTE") || data.getTipoPrestamo().equals("COBRANZA")){
                linea = line("Folio: ", "RC"+data.getAsesorId()+"-"+(data.getFolio()));
            }
            else if (data.getTipoPrestamo().equals("VENCIDA")){
                linea = line("Folio: ", "CV"+data.getAsesorId()+"-"+(data.getFolio()));
            }

            dobleEspacio();

            posPtr.printNormal(ESC + "|lA" + ESC + "|bC" + ESC + "|1C" + linea);
            dobleEspacio();

            if (data.getTipoPrestamo().equals("VIGENTE") || data.getTipoPrestamo().equals("COBRANZA")) {

                URL url = null;
                try {
                    //url = new  URL("http://sidert.ddns.net:83"+WebServicesRoutes.CONTROLLER_MOVIL+"pagos/"+AES.encrypt(data.getIdPrestamo()));
                    url = new  URL(session.getDominio().get(0)+session.getDominio().get(1)+WebServicesRoutes.CONTROLLER_MOVIL+"pagos/"+AES.encrypt(data.getIdPrestamo()));
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }

                posPtr.printQRCode(url.toString(), 300, 4, 0, 100, 1);

                posPtr.printNormal(ESC + "|lA" + ESC + "|bC" + ESC + "|1C" + "Escanea el codigo para ver tu   ");
                posPtr.printNormal(ESC + "|lA" + ESC + "|bC" + ESC + "|1C" + "historial de pagos o ingresa al ");
                posPtr.printNormal(ESC + "|lA" + ESC + "|bC" + ESC + "|1C" + "siguiente link: "+url.toString());

                dobleEspacio();
            }

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
