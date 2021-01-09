package com.sidert.sidertmovil.utils;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.Toast;

import com.sewoo.jpos.command.ESCPOS;
import com.sewoo.jpos.printer.ESCPOSPrinter;
import com.sewoo.jpos.printer.LKPrint;
import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.database.DBhelper;
import com.sidert.sidertmovil.models.MFormatoRecibo;
import com.sidert.sidertmovil.models.RecibosAgfCC;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

import static com.sidert.sidertmovil.utils.Constants.TBL_RECIBOS_AGF_CC;
import static com.sidert.sidertmovil.utils.Constants.TBL_RECIBOS_CC;
import static com.sidert.sidertmovil.utils.Constants.TIMESTAMP;

public class PrintRecibos {

    private ESCPOSPrinter posPtr;
    private final char ESC = ESCPOS.ESC;
    private final char LF = ESCPOS.LF;
    private String linea;
    private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
    private String date = "";
    private SessionManager session;

    public void WriteTicket(Context ctx, RecibosAgfCC ticket, JSONObject object) {
        posPtr = new ESCPOSPrinter();
        date = df.format(Calendar.getInstance().getTime());
        session = new SessionManager(ctx);
        //PrintCode();
        HeadTicket(ctx, ticket);
        BodyTicket(ctx, ticket, object);
        try {
            FooterTicket(ctx, ticket, object);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void PrintCode(){
        try {
            posPtr.printQRCode("Alejandro Isaias", 300, 5, 0, 100, 1);
        } catch (UnsupportedEncodingException e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
    }

    private void HeadTicket(Context ctx, RecibosAgfCC ticket){
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

            Log.e("ReimpresionCC", String.valueOf(ticket.isReeimpresion()));

            if (ticket.isReeimpresion()) {
                dobleEspacio();
                posPtr.printNormal(ESC + "|cA" + ESC + "|bC" + ESC + "|4C" + "REIMPRESION");
            }
            dobleEspacio();

            if (ticket.getTipoImpresion().equals("O"))
                posPtr.printNormal(ESC + "|cA" + ESC + "|bC" + ESC + "|1C" + "Original");
            else
                posPtr.printNormal(ESC + "|cA" + ESC + "|bC" + ESC + "|1C" + "Copia");


            dobleEspacio();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void BodyTicket(Context ctx, RecibosAgfCC ticket, JSONObject object){
        try {
            linea = line("Fecha y hora:", Miscellaneous.ObtenerFecha("timestamp"));
            posPtr.printNormal(ESC + "|lA" + ESC + "|bC" + ESC + "|1C" + linea);

            if (ticket.getTipoRecibo().equals("AGF"))
            {
                linea = line("Recibimos de: ", ticket.getNombre().trim());
                posPtr.printNormal(ESC + "|lA" + ESC + "|bC" + ESC + "|1C" + linea + LF);
                linea = line("", "");
                posPtr.printNormal(ESC + "|lA" + ESC + "|bC" + ESC + "|1C" + linea + LF);
                posPtr.printNormal(ESC + "|cA" + ESC + "|bC" + ESC + "|2C" + " Monto del pago      ");
                posPtr.printNormal(ESC + "|lA" + ESC + "|bC" + ESC + "|1C");
            }
            else
            {
                if (object.getInt("tipo_credito") == 1) //creditos individuales
                {
                    linea = line("Cliente: ", object.getString("nombre_uno"));
                    posPtr.printNormal(ESC + "|lA" + ESC + "|bC" + ESC + "|1C" + linea + LF);

                    linea = line("Aval: ", object.getString("nombre_dos"));
                    posPtr.printNormal(ESC + "|lA" + ESC + "|bC" + ESC + "|1C" + linea + LF);
                }
                else //creditos grupales
                {
                    linea = line("Grupo: ", object.getString("nombre_uno"));
                    posPtr.printNormal(ESC + "|lA" + ESC + "|bC" + ESC + "|1C" + linea + LF);

                    linea = line("Responsable: ", object.getString("nombre_dos"));
                    posPtr.printNormal(ESC + "|lA" + ESC + "|bC" + ESC + "|1C" + linea + LF);

                    linea = line("Total Integrantes: "+object.getString("total_integrantes"), "");
                    posPtr.printNormal(ESC + "|lA" + ESC + "|bC" + ESC + "|1C" + linea + LF);
                }
            }

            linea = line("", "");
            posPtr.printNormal(ESC + "|lA" + ESC + "|bC" + ESC + "|1C" + linea + LF);

            posPtr.printNormal(ESC + "|cA" + ESC + "|bC" + ESC + "|2C" + "Monto:"+ Miscellaneous.moneyFormat(ticket.getMonto()));

            linea = line("", "");
            posPtr.printNormal(ESC + "|lA" + ESC + "|bC" + ESC + "|1C" + linea + LF);

            posPtr.printNormal(ESC + "|cA" + ESC + "|bC" + ESC + "|1C" + "      Cantidad  en  letra       ");
            if (ticket.getMonto().contains(".5"))
                posPtr.printNormal(ESC + "|cA" + ESC + "|bC" + ESC + "|1C" + Miscellaneous.cantidadLetra(ticket.getMonto())+"pesos 50/100 M.N.  ");
            else
                posPtr.printNormal(ESC + "|cA" + ESC + "|bC" + ESC + "|1C" + Miscellaneous.cantidadLetra(ticket.getMonto())+"pesos 00/100 M.N.  ");

            dobleEspacio();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void FooterTicket(Context ctx, RecibosAgfCC ticket, JSONObject object) throws JSONException {

        DBhelper dBhelper = new DBhelper(ctx);
        SQLiteDatabase db = dBhelper.getWritableDatabase();
        Cursor row;
        HashMap<Integer, String> params;
        int count_folio = 0;

        if (ticket.getTipoImpresion().equals("O")){ //Original
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
                posPtr.printNormal(ESC + "|lA" + ESC + "|bC" + ESC + "|1C" + ticket.getNombreFirma().trim());
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        String sql =  "";

        if (ticket.getTipoRecibo().equals("AGF")) {
            sql = "SELECT * FROM " + TBL_RECIBOS_AGF_CC + " WHERE grupo_id = ? AND tipo_recibo = ? AND num_solicitud = ? AND nombre = ?";
            row = db.rawQuery(sql, new String[]{ticket.getGrupoId(), ticket.getTipoRecibo(), ticket.getNumSolicitud(), ticket.getNombre()});

            if (row.getCount() == 1) {
                row.moveToFirst();
                count_folio = row.getInt(4);
                params = new HashMap();
                params.put(0, ticket.getGrupoId());
                params.put(1, ticket.getNumSolicitud());
                params.put(2, ticket.getMonto());
                params.put(3, row.getString(4));
                params.put(4, ticket.getTipoRecibo());
                params.put(5, "C");
                params.put(6, Miscellaneous.ObtenerFecha(TIMESTAMP));
                params.put(7, "");
                params.put(8, "0");
                params.put(9, ticket.getNombre());
                dBhelper.saveRecibosAgfCc(db, params);

            } else if (row.getCount() >= 2) {
                row.moveToFirst();
                count_folio = row.getInt(4);
                params = new HashMap();
                params.put(0, ticket.getGrupoId());
                params.put(1, ticket.getNumSolicitud());
                params.put(2, ticket.getMonto());
                params.put(3, row.getString(4));
                params.put(4, ticket.getTipoRecibo());
                if (ticket.isReeimpresion() && ticket.getTipoImpresion().equals("O"))
                    params.put(5, "RO");
                else
                    params.put(5, "RC");
                params.put(6, Miscellaneous.ObtenerFecha(TIMESTAMP));
                params.put(7, "");
                params.put(8, "0");
                params.put(9, ticket.getNombre());
                dBhelper.saveRecibosAgfCc(db, params);
            } else {
                String sqlFolio = "SELECT MAX(folio) AS folio FROM " + TBL_RECIBOS_AGF_CC;
                Cursor row_folio = db.rawQuery(sqlFolio, null);
                if (row_folio.getCount() > 0) {
                    row_folio.moveToFirst();
                    count_folio = (row_folio.getInt(0) + 1);
                } else {
                    count_folio = 1;
                }
                row_folio.close();

                params = new HashMap();
                params.put(0, ticket.getGrupoId());
                params.put(1, ticket.getNumSolicitud());
                params.put(2, ticket.getMonto());
                params.put(3, String.valueOf(count_folio));
                params.put(4, ticket.getTipoRecibo());
                params.put(5, "O");
                params.put(6, Miscellaneous.ObtenerFecha(TIMESTAMP));
                params.put(7, "");
                params.put(8, "0");
                params.put(9, ticket.getNombre());
                dBhelper.saveRecibosAgfCc(db, params);

            }
            row.close();
        }
        else
        {
            int tipoCredito = 0;
            String curp = "";
            try {
                tipoCredito = object.getInt("tipo_credito");
                curp = object.getString("curp");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            sql = "SELECT * FROM " + TBL_RECIBOS_CC + " WHERE curp = ? AND tipo_credito = ? AND nombre_dos = ?";
            row = db.rawQuery(sql, new String[]{ticket.getCurp(), String.valueOf(tipoCredito), object.getString("nombre_dos")});

            Log.e("Reimpresion", "count"+ row.getCount());
            if (row.getCount() == 1) {
                row.moveToFirst();
                count_folio = row.getInt(8);

                params = new HashMap();
                params.put(0, String.valueOf(tipoCredito));
                params.put(1, object.getString("nombre_uno"));
                params.put(2, object.getString("curp"));
                params.put(3, object.getString("nombre_dos"));
                params.put(4, object.getString("total_integrantes"));
                params.put(5, object.getString("monto"));
                params.put(6, "C");
                params.put(7, String.valueOf(count_folio));
                params.put(8, Miscellaneous.ObtenerFecha(TIMESTAMP));
                params.put(9, "");
                params.put(10, "0");
                dBhelper.saveRecibosCC(db, params);

            } else if (row.getCount() >= 2) {
                row.moveToFirst();

                count_folio = row.getInt(8);
                Log.e("REIMPRESION", "Comienza la reimpresion: "+row.getString(8));

                params = new HashMap();
                params.put(0, String.valueOf(tipoCredito));
                params.put(1, object.getString("nombre_uno"));
                params.put(2, object.getString("curp"));
                params.put(3, object.getString("nombre_dos"));
                params.put(4, object.getString("total_integrantes"));
                params.put(5, object.getString("monto"));
                if (ticket.isReeimpresion() && ticket.getTipoImpresion().equals("O"))
                    params.put(6, "RO");
                else
                    params.put(6, "RC");
                params.put(7, row.getString(8));
                params.put(8, Miscellaneous.ObtenerFecha(TIMESTAMP));
                params.put(9, "");
                params.put(10, "0");
                dBhelper.saveRecibosCC(db, params);

            } else {
                String sqlFolio = "SELECT MAX(folio) AS folio FROM " + TBL_RECIBOS_CC;
                Cursor row_folio = db.rawQuery(sqlFolio, null);
                if (row_folio.getCount() > 0) {
                    row_folio.moveToFirst();
                    count_folio = (row_folio.getInt(0) + 1);
                } else {
                    count_folio = 1;
                }
                row_folio.close();

                params = new HashMap();
                params.put(0, String.valueOf(tipoCredito));
                params.put(1, object.getString("nombre_uno"));
                params.put(2, object.getString("curp"));
                params.put(3, object.getString("nombre_dos"));
                params.put(4, object.getString("total_integrantes"));
                params.put(5, object.getString("monto"));
                params.put(6, "O");
                params.put(7, String.valueOf(count_folio));
                params.put(8, Miscellaneous.ObtenerFecha(TIMESTAMP));
                params.put(9, "");
                params.put(10, "0");
                dBhelper.saveRecibosCC(db, params);

            }
            row.close();
        }

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
