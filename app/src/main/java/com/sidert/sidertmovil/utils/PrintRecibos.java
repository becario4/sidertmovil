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
import static com.sidert.sidertmovil.utils.Constants.TBL_RECIBOS;
import static com.sidert.sidertmovil.utils.Constants.TIMESTAMP;

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
            Bitmap bm = BitmapFactory.decodeResource(ctx.getResources(), R.drawable.logo_impresion);
            posPtr.printBitmap(bm, LKPrint.LK_ALIGNMENT_CENTER);
            posPtr.printNormal(ESC + "|lA" + ESC + "|bC" + ESC + "|1C" + "En caso de dudas o aclaraciones ");
            posPtr.printNormal(ESC + "|lA" + ESC + "|bC" + ESC + "|1C" + "comunicarse al 01 800 112 6666");

            dobleEspacio();
            linea = line("", "SERVICIOS INTEGRALES PARA EL DESARROLLO RURAL DEL TROPICO SA DE CV SOFOM ENR.(SIDERT)");
            posPtr.printNormal(ESC + "|lA" + ESC + "|bC" + ESC + "|1C" + linea + LF);

            posPtr.printNormal(ESC + "|cA" + ESC + "|bC" + ESC + "|1C" + "--------------------------------");
            switch (ticket.getTipoRecibo()){
                case "CC": //Circulo de credito
                    posPtr.printNormal(ESC + "|cA" + ESC + "|bC" + ESC + "|1C" + "CIRCULO DE CREDITO");
                    break;
                case "AGF": //Apoyo a gastos funerario
                    posPtr.printNormal(ESC + "|cA" + ESC + "|bC" + ESC + "|1C" + "APOYO PARA GASTOS FUNERARIOS");
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

            posPtr.printNormal(ESC + "|cA" + ESC + "|bC" + ESC + "|2C" + Miscellaneous.moneyFormat(ticket.getMonto()));


            linea = line("", "");
            posPtr.printNormal(ESC + "|lA" + ESC + "|bC" + ESC + "|1C" + linea + LF);

            posPtr.printNormal(ESC + "|cA" + ESC + "|bC" + ESC + "|1C" + "      Cantidad  en  letra       ");
            switch (ticket.getTipoRecibo()){
                case "CC": //Circulo de credito
                    posPtr.printNormal(ESC + "|cA" + ESC + "|bC" + ESC + "|1C" + "  Diecisiete pesos 50/100 M.N.  ");
                    break;
                case "AGF": //Apoyo a Gastos Funerarios
                    if (ticket.getMonto().contains(".5"))
                        posPtr.printNormal(ESC + "|cA" + ESC + "|bC" + ESC + "|1C" + Miscellaneous.cantidadLetra(ticket.getMonto())+"pesos 50/100 M.N.  ");
                    else
                        posPtr.printNormal(ESC + "|cA" + ESC + "|bC" + ESC + "|1C" + Miscellaneous.cantidadLetra(ticket.getMonto())+"pesos 00/100 M.N.  ");
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
        String sql = "SELECT * FROM " + TBL_RECIBOS + " WHERE prestamo_id = ? AND tipo_recibo = ? AND curp = ?";
        row = db.rawQuery(sql, new String[]{ticket.getIdPrestamo(), ticket.getTipoRecibo(), ticket.getCurp()});

         if (row.getCount() == 1){
            row.moveToFirst();
            count_folio = row.getInt( 5);
            params = new HashMap();
            params.put(0, ticket.getIdPrestamo());
            params.put(1, session.getUser().get(0));
            params.put(2, ticket.getTipoRecibo());
            params.put(3, "C");
            params.put(4, row.getString(5));
            params.put(5, ticket.getMonto());
            params.put(6, ticket.getClave());
            params.put(7, ticket.getNombreCliente());
            params.put(8, "");
            params.put(9, "");
            params.put(10, Miscellaneous.ObtenerFecha(TIMESTAMP));
            params.put(11, "");
            params.put(12, "0");
            params.put(13, ticket.getCurp());
            dBhelper.saveRecibos(db, params);
        }
        else if (row.getCount() >= 2){
            row.moveToFirst();
            count_folio = row.getInt( 5);
            params = new HashMap();
            params.put(0, ticket.getIdPrestamo());
            params.put(1, session.getUser().get(0));
            params.put(2, ticket.getTipoRecibo());
            if (ticket.isReeimpresion() && ticket.isTipoImpresion())
                params.put(3, "RO");
            else
                params.put(3, "RC");
            params.put(4, row.getString(5));
            params.put(5, ticket.getMonto());
            params.put(6, ticket.getClave());
            params.put(7, ticket.getNombreCliente());
            params.put(8, "");
            params.put(9, "");
            params.put(10, Miscellaneous.ObtenerFecha(TIMESTAMP));
            params.put(11, "");
            params.put(12, "0");
            params.put(13, ticket.getCurp());
            dBhelper.saveRecibos(db, params);
        }
        else{
            String sqlFolio = "SELECT MAX(folio) AS folio FROM " + TBL_RECIBOS;
            Cursor row_folio = db.rawQuery(sqlFolio, null);
            if (row_folio.getCount() > 0){
                row_folio.moveToFirst();
                count_folio = (row_folio.getInt(0) + 1);
            }
            else{
                count_folio = 1;
            }
            row_folio.close();

            params = new HashMap();
            params.put(0, ticket.getIdPrestamo());
            params.put(1, session.getUser().get(0));
            params.put(2, ticket.getTipoRecibo());
            params.put(3, "O");
            params.put(4, String.valueOf(count_folio));
            params.put(5, ticket.getMonto());
            params.put(6, ticket.getClave());
            params.put(7, ticket.getNombreCliente());
            params.put(8, "");
            params.put(9, "");
            params.put(10, Miscellaneous.ObtenerFecha(TIMESTAMP));
            params.put(11, "");
            params.put(12, "0");
            params.put(13, ticket.getCurp());
            dBhelper.saveRecibos(db, params);
        }
        row.close();

        switch (ticket.getTipoRecibo()){
            case "CC":
                linea = line("Folio:", "CC-" + session.getUser().get(0) + "-" + count_folio);
                break;
            case "AGF":
                linea = line("Folio:", "AGF-" + session.getUser().get(0) + "-" + count_folio);
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

        Servicios_Sincronizado ss = new Servicios_Sincronizado();
        ss.SendRecibos(ctx, false);

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
