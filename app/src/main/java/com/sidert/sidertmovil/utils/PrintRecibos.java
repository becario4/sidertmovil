package com.sidert.sidertmovil.utils;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.sewoo.jpos.command.ESCPOS;
import com.sewoo.jpos.printer.ESCPOSPrinter;
import com.sewoo.jpos.printer.LKPrint;
import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.database.DBhelper;
import com.sidert.sidertmovil.models.MFormatoRecibo;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

import static com.sidert.sidertmovil.utils.Constants.ENVIROMENT;
import static com.sidert.sidertmovil.utils.Constants.RECIBOS_CIRCULO_CREDITO;
import static com.sidert.sidertmovil.utils.Constants.RECIBOS_CIRCULO_CREDITO_T;

public class PrintRecibos {

    private ESCPOSPrinter posPtr;
    private final char ESC = ESCPOS.ESC;
    private final char LF = ESCPOS.LF;
    private String linea;
    private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
    private String date = "";
    private SessionManager session;

    public void WriteTicket(Context ctx, MFormatoRecibo ticket) {
        posPtr = new ESCPOSPrinter();
        date = df.format(Calendar.getInstance().getTime());
        session = new SessionManager(ctx);
        HeadTicket(ctx, ticket);
        BodyTicket(ctx, ticket);
        FooterTicket(ctx, ticket);
    }

    private void HeadTicket(Context ctx, MFormatoRecibo ticket){
        try {
            Bitmap bm = BitmapFactory.decodeResource(ctx.getResources(), R.drawable.imagen1);
            posPtr.printBitmap(bm, LKPrint.LK_ALIGNMENT_CENTER);
            posPtr.printNormal(ESC + "|lA" + ESC + "|bC" + ESC + "|1C" + "En caso de dudas o aclaraciones ");
            posPtr.printNormal(ESC + "|lA" + ESC + "|bC" + ESC + "|1C" + "comunicarse al 01 800 112 6666");

            dobleEspacio();
            linea = line("", "SERVICIOS INTEGRALES PARA EL DESARROLLO RURAL DEL TROPICO SA DE CV SOFOM ENR.(SIDERT)");
            posPtr.printNormal(ESC + "|lA" + ESC + "|bC" + ESC + "|1C" + linea + LF);

            posPtr.printNormal(ESC + "|cA" + ESC + "|bC" + ESC + "|1C" + "--------------------------------");
            switch (ticket.getTipoRecibo()){
                case 1: //Circulo de credito
                    posPtr.printNormal(ESC + "|cA" + ESC + "|bC" + ESC + "|1C" + "CIRCULO DE CREDITO");
                    break;
            }

            if (ticket.isReeimpresion()) {
                dobleEspacio();
                posPtr.printNormal(ESC + "|cA" + ESC + "|bC" + ESC + "|4C" + "REIMPRESION");
            }
            dobleEspacio();

            if (ticket.isTipoImpresion()){
                posPtr.printNormal(ESC + "|cA" + ESC + "|bC" + ESC + "|1C" + "Original");
            }else{
                posPtr.printNormal(ESC + "|cA" + ESC + "|bC" + ESC + "|1C" + "Copia");
            }

            dobleEspacio();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void BodyTicket(Context ctx, MFormatoRecibo ticket){
        try {
            linea = line("Fecha y hora:", Miscellaneous.ObtenerFecha("timestamp"));
            posPtr.printNormal(ESC + "|lA" + ESC + "|bC" + ESC + "|1C" + linea);
            linea = line("Recibimos de: ", ticket.getNombreCliente().trim());
            posPtr.printNormal(ESC + "|lA" + ESC + "|bC" + ESC + "|1C" + linea + LF);
            linea = line("", "");
            posPtr.printNormal(ESC + "|lA" + ESC + "|bC" + ESC + "|1C" + linea + LF);
            posPtr.printNormal(ESC + "|cA" + ESC + "|bC" + ESC + "|2C" + " Monto del pago      ");
            posPtr.printNormal(ESC + "|lA" + ESC + "|bC" + ESC + "|1C");
            posPtr.printNormal(ESC + "|cA" + ESC + "|bC" + ESC + "|2C" + Miscellaneous.moneyFormat("17.5"));

            linea = line("", "");
            posPtr.printNormal(ESC + "|lA" + ESC + "|bC" + ESC + "|1C" + linea + LF);

            posPtr.printNormal(ESC + "|cA" + ESC + "|bC" + ESC + "|1C" + "      Cantidad  en  letra       ");
            switch (ticket.getTipoRecibo()){
                case 1: //Circulo de credito
                    posPtr.printNormal(ESC + "|cA" + ESC + "|bC" + ESC + "|1C" + "  Diecisiete pesos 50/100 M.N.  ");
                    break;
            }
            dobleEspacio();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private void FooterTicket(Context ctx, MFormatoRecibo ticket){

        DBhelper dBhelper = new DBhelper(ctx);
        SQLiteDatabase db = dBhelper.getWritableDatabase();
        Cursor row;
        HashMap<Integer, String> params;
        int count_folio = 0;

        if (ticket.isTipoImpresion()){ //Original
            try {
                posPtr.printNormal(ESC + "|lA" + ESC + "|bC" + ESC + "|1C" + "Firma Asesor:");
                dobleEspacio();
                posPtr.printNormal(ESC + "|lA" + ESC + "|bC" + ESC + "|1C" + "________________________________");
                posPtr.printNormal(ESC + "|lA" + ESC + "|bC" + ESC + "|1C" + session.getUser().get(1).trim());
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

        }
        else{ //Copia
            try {
                posPtr.printNormal(ESC + "|lA" + ESC + "|bC" + ESC + "|1C" + "Firma Cliente:");
                dobleEspacio();
                posPtr.printNormal(ESC + "|lA" + ESC + "|bC" + ESC + "|1C" + "________________________________");
                posPtr.printNormal(ESC + "|lA" + ESC + "|bC" + ESC + "|1C" + ticket.getNombreCliente().trim());
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        switch (ticket.getTipoRecibo()){
            case 1: //Circulo de credito
                if (ticket.isReeimpresion()){
                    linea = line("Folio:", "CC-" + session.getUser().get(0) + "-" + ticket.getFolio());
                }
                else {
                    if (ENVIROMENT)
                        row = dBhelper.getRecords(RECIBOS_CIRCULO_CREDITO, "", " ORDER BY folio DESC", null);
                    else
                        row = dBhelper.getRecords(RECIBOS_CIRCULO_CREDITO_T, "", " ORDER BY folio DESC", null);

                    if (row.getCount() > 0) {
                        row.moveToFirst();
                        if (row.getString(2).equals("O") && row.getString(3).equals(ticket.getNombreCliente())) {
                            count_folio = row.getInt(4);
                            params = new HashMap();
                            params.put(0, session.getUser().get(0));
                            params.put(1, "C");
                            params.put(2, ticket.getNombreCliente());
                            params.put(3, String.valueOf(count_folio));
                            params.put(4, Miscellaneous.ObtenerFecha("timestamp"));
                            params.put(5, "");
                            params.put(6, "1");
                            if (ENVIROMENT)
                                dBhelper.saveTicketCC(db, Constants.RECIBOS_CIRCULO_CREDITO, params);
                            else
                                dBhelper.saveTicketCC(db, Constants.RECIBOS_CIRCULO_CREDITO_T, params);
                        } else {
                            count_folio = row.getInt(4) + 1;
                            params = new HashMap();
                            params.put(0, session.getUser().get(0));
                            params.put(1, "O");
                            params.put(2, ticket.getNombreCliente());
                            params.put(3, String.valueOf(count_folio));
                            params.put(4, Miscellaneous.ObtenerFecha("timestamp"));
                            params.put(5, "");
                            params.put(6, "1");
                            if (ENVIROMENT)
                                dBhelper.saveTicketCC(db, Constants.RECIBOS_CIRCULO_CREDITO, params);
                            else
                                dBhelper.saveTicketCC(db, Constants.RECIBOS_CIRCULO_CREDITO_T, params);
                        }
                    } else {
                        count_folio = 1;
                        params = new HashMap();
                        params.put(0, session.getUser().get(0));
                        params.put(1, "O");
                        params.put(2, ticket.getNombreCliente());
                        params.put(3, String.valueOf(count_folio));
                        params.put(4, Miscellaneous.ObtenerFecha("timestamp"));
                        params.put(5, "");
                        params.put(6, "1");
                        if (ENVIROMENT)
                            dBhelper.saveTicketCC(db, Constants.RECIBOS_CIRCULO_CREDITO, params);
                        else
                            dBhelper.saveTicketCC(db, Constants.RECIBOS_CIRCULO_CREDITO_T, params);
                    }
                    linea = line("Folio:", "CC-" + session.getUser().get(0) + "-" + count_folio);
                }
                break;
        }

        dobleEspacio();

        try {
            posPtr.printNormal(ESC + "|lA" + ESC + "|bC" + ESC + "|1C" + linea);
            dobleEspacio();
            posPtr.printNormal(ESC + "|cA" + ESC + "|bC" + ESC + "|1C" + "NO  OLVIDES  VERIFICAR  TU  PAGO");

            posPtr.printNormal(ESC + "|cA" + ESC + "|bC" + ESC + "|1C" + "El recibo debe de coincidir con ");
            posPtr.printNormal(ESC + "|lA" + ESC + "|bC" + ESC + "|1C" + "lo entregado." + LF);
            dobleEspacio();
            posPtr.printNormal(ESC + "|lA" + ESC + "|bC" + ESC + "|1C" + "El presente se toma con las RESERVAS de la Ley respecto a intereses pactados y los que se generan conforme a lo establecido en el articulo 364 del codigo de Comercio Vigente. El presente pago no es una remision de deuda, implica una novacion de la obligacion inicial.");
            dobleEspacio();
            dobleEspacio();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

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
