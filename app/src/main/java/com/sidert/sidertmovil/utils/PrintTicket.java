package com.sidert.sidertmovil.utils;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

/*import com.bitly.Bitly;
import com.bitly.Error;
import com.bitly.Response;*/
import com.sewoo.jpos.command.ESCPOS;
import com.sewoo.jpos.printer.ESCPOSPrinter;
import com.sewoo.jpos.printer.LKPrint;
import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.database.DBhelper;
import com.sidert.sidertmovil.models.MImpresion;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

import static com.sidert.sidertmovil.utils.Constants.TBL_IMPRESIONES_VENCIDA_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_IMPRESIONES_VIGENTE_T;

public class PrintTicket {

    private ESCPOSPrinter posPtr;
    private final char ESC = ESCPOS.ESC;
    private final char LF = ESCPOS.LF;
    private String linea;
    private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
    private String date = "";
    private DBhelper dBhelper;
    private SQLiteDatabase db;
    private SessionManager session;

    public void WriteTicket(Context ctx, MImpresion ticket) {
        posPtr = new ESCPOSPrinter();
        date = df.format(Calendar.getInstance().getTime());
        this.dBhelper = DBhelper.getInstance(ctx);
        db = dBhelper.getWritableDatabase();
        session = SessionManager.getInstance(ctx);
        HeadTicket(ctx, ticket);
        BodyTicket(ticket);
        FooterTicket(ctx, ticket);
    }

    private void HeadTicket(Context ctx, MImpresion ticket) {
        try {
            Bitmap bm;
            if (ticket.getTipoPrestamo().contains("VENCIDA"))
                bm = BitmapFactory.decodeResource(ctx.getResources(), R.drawable.logo_cv);
            else
                bm = BitmapFactory.decodeResource(ctx.getResources(), R.drawable.logo_impresion);
            posPtr.printBitmap(bm, LKPrint.LK_ALIGNMENT_CENTER);
            if (ticket.getResultPrint() == 0) {
                posPtr.printNormal(ESC + "|cA" + ESC + "|bC" + ESC + "|1C" + "Original");
            } else {
                posPtr.printNormal(ESC + "|cA" + ESC + "|bC" + ESC + "|1C" + "Copia");
            }
            dobleEspacio();
            posPtr.printNormal(ESC + "|lA" + ESC + "|bC" + ESC + "|1C" + "Fecha y hora:" + date);
            dobleEspacio();

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void BodyTicket(MImpresion data) {
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
            } else {
                if (data.getTipoGestion().equals("INDIVIDUAL")) {//individual vencida
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
                } else { //Grupal vencida
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

    private void FooterTicket(Context ctx, MImpresion data) {
        try {
            dobleEspacio();

            if (data.getTipoPrestamo().equals("VIGENTE") || data.getTipoPrestamo().equals("COBRANZA")) {
                if (data.getResultPrint() == 0) {
                    posPtr.printNormal(ESC + "|lA" + ESC + "|bC" + ESC + "|1C" + "Firma Asesor:");
                    dobleEspacio();
                    posPtr.printNormal(ESC + "|lA" + ESC + "|bC" + ESC + "|1C" + "________________________________");
                    posPtr.printNormal(ESC + "|lA" + ESC + "|bC" + ESC + "|1C" + replaceStr(data.getNombreAsesor().trim()));
                } else {
                    String titleSignature = (data.getTipoGestion().equals("GRUPAL")) ? "Firma Tesorero(a):" : "Firma Cliente:";
                    posPtr.printNormal(ESC + "|lA" + ESC + "|bC" + ESC + "|1C" + titleSignature);
                    dobleEspacio();
                    posPtr.printNormal(ESC + "|lA" + ESC + "|bC" + ESC + "|1C" + "________________________________");
                    posPtr.printNormal(ESC + "|lA" + ESC + "|bC" + ESC + "|1C" + replaceStr(data.getNombreFirma().trim()));
                }
            } else {
                if (data.getResultPrint() == 0) {
                    posPtr.printNormal(ESC + "|lA" + ESC + "|bC" + ESC + "|1C" + "Firma Gestor:");
                    dobleEspacio();
                    posPtr.printNormal(ESC + "|lA" + ESC + "|bC" + ESC + "|1C" + "________________________________");
                    posPtr.printNormal(ESC + "|lA" + ESC + "|bC" + ESC + "|1C" + replaceStr(data.getNombreAsesor().trim()));
                } else {
                    posPtr.printNormal(ESC + "|lA" + ESC + "|bC" + ESC + "|1C" + "Firma Cliente:");
                    dobleEspacio();
                    posPtr.printNormal(ESC + "|lA" + ESC + "|bC" + ESC + "|1C" + "________________________________");
                    posPtr.printNormal(ESC + "|lA" + ESC + "|bC" + ESC + "|1C" + replaceStr(data.getNombreFirma().trim()));
                }
            }

            if (data.getTipoPrestamo().equals("VIGENTE") || data.getTipoPrestamo().equals("COBRANZA")) {
                Cursor row;

                row = dBhelper.getRecords(TBL_IMPRESIONES_VIGENTE_T, "", " ORDER BY folio ASC", null);

                if (row.getCount() == 0) {
                    HashMap<Integer, String> params = new HashMap<>();
                    params.put(0, data.getNumeroPrestamo() + "-" + data.getIdGestion());
                    params.put(1, data.getAsesorId());
                    params.put(2, "1");
                    params.put(3, "O");
                    params.put(4, data.getMonto());
                    params.put(5, data.getClaveCliente());
                    params.put(6, Miscellaneous.ObtenerFecha("timestamp"));
                    params.put(7, "");
                    params.put(8, "0");
                    params.put(9, data.getNumeroPrestamo());
                    params.put(10, data.getTelefono());

                    dBhelper.saveImpresiones(db, params);

                    linea = line("Folio: ", "RC" + data.getAsesorId() + "-" + 1);

                    SendMess(ctx, data.getIdPrestamo(), data.getTelefono(), data.getNombre(), data.getMonto(), date, "1");
                } else {
                    row.moveToLast();
                    Log.e("num_prestamo_id_gestion", row.getString(4) + "  " + row.getString(1) + " = " + data.getNumeroPrestamo() + "-" + data.getIdGestion());
                    if (row.getString(1).equals(data.getNumeroPrestamo() + "-" + data.getIdGestion()) &&
                            row.getString(4).equals("O")) {
                        HashMap<Integer, String> params = new HashMap<>();
                        params.put(0, data.getNumeroPrestamo() + "-" + data.getIdGestion());
                        params.put(1, data.getAsesorId());
                        params.put(2, row.getString(3));
                        params.put(3, "C");
                        params.put(4, data.getMonto());
                        params.put(5, data.getClaveCliente());
                        params.put(6, Miscellaneous.ObtenerFecha("timestamp"));
                        params.put(7, "");
                        params.put(8, "0");
                        params.put(9, data.getNumeroPrestamo());
                        params.put(10, data.getTelefono());

                        dBhelper.saveImpresiones(db, params);

                        linea = line("Folio: ", "RC" + data.getAsesorId() + "-" + row.getString(3));
                    } else {
                        String sql = "SELECT folio FROM " + TBL_IMPRESIONES_VIGENTE_T + " WHERE num_prestamo_id_gestion = ? AND tipo_impresion = ? ";
                        Cursor row_folio = db.rawQuery(sql, new String[]{data.getNumeroPrestamo() + "-" + data.getIdGestion(), "O"});
                        if (row_folio.getCount() > 0) {
                            row_folio.moveToFirst();
                            HashMap<Integer, String> params = new HashMap<>();
                            params.put(0, data.getNumeroPrestamo() + "-" + data.getIdGestion());
                            params.put(1, data.getAsesorId());
                            params.put(2, row_folio.getString(0));
                            params.put(3, "O");
                            params.put(4, data.getMonto());
                            params.put(5, data.getClaveCliente());
                            params.put(6, Miscellaneous.ObtenerFecha("timestamp"));
                            params.put(7, "");
                            params.put(8, "0");
                            params.put(9, data.getNumeroPrestamo());
                            params.put(10, data.getTelefono());

                            dBhelper.saveImpresiones(db, params);
                            linea = line("Folio: ", "RC" + data.getAsesorId() + "-" + row_folio.getString(0));

                            SendMess(ctx, data.getIdPrestamo(), data.getTelefono(), data.getNombre(), data.getMonto(), date, row_folio.getString(0));
                        } else {
                            HashMap<Integer, String> params = new HashMap<>();
                            params.put(0, data.getNumeroPrestamo() + "-" + data.getIdGestion());
                            params.put(1, data.getAsesorId());
                            params.put(2, String.valueOf(row.getInt(3) + 1));
                            params.put(3, "O");
                            params.put(4, data.getMonto());
                            params.put(5, data.getClaveCliente());
                            params.put(6, Miscellaneous.ObtenerFecha("timestamp"));
                            params.put(7, "");
                            params.put(8, "0");
                            params.put(9, data.getNumeroPrestamo());
                            params.put(10, data.getTelefono());

                            dBhelper.saveImpresiones(db, params);
                            linea = line("Folio: ", "RC" + data.getAsesorId() + "-" + (row.getInt(3) + 1));

                            SendMess(ctx, data.getIdPrestamo(), data.getTelefono(), data.getNombre(), data.getMonto(), date, String.valueOf((row.getInt(3) + 1)));
                        }
                    }
                }
                row.close();
            } else if (data.getTipoPrestamo().equals("VENCIDA")) {
                //-----------------------------------------------------------------------------------------------------------------
                Cursor row;

                row = dBhelper.getRecords(TBL_IMPRESIONES_VENCIDA_T, "", " ORDER BY folio ASC", null);

                if (row.getCount() == 0) {

                    HashMap<Integer, String> params = new HashMap<>();
                    params.put(0, data.getNumeroPrestamo() + "-" + data.getIdGestion());
                    params.put(1, data.getAsesorId());
                    params.put(2, "1");
                    params.put(3, "O");
                    params.put(4, data.getMonto());
                    params.put(5, data.getClaveCliente());
                    params.put(6, Miscellaneous.ObtenerFecha("timestamp"));
                    params.put(7, "");
                    params.put(8, "0");
                    params.put(9, data.getNumeroPrestamo());
                    params.put(10, "");

                    dBhelper.saveImpresionesVencida(db, params);

                    linea = line("Folio: ", "CV" + data.getAsesorId() + "-" + 1);

                    HashMap<Integer, String> paramsCD = new HashMap<>();
                    paramsCD.put(0, session.getUser().get(0));
                    paramsCD.put(1, data.getIdGestion());
                    paramsCD.put(2, data.getNumeroPrestamo());
                    paramsCD.put(3, data.getClaveCliente());
                    paramsCD.put(4, "");
                    paramsCD.put(5, "");
                    paramsCD.put(6, "");
                    paramsCD.put(7, data.getTipoGestion());
                    paramsCD.put(8, "VENCIDA");
                    paramsCD.put(9, "");
                    paramsCD.put(10, "");
                    paramsCD.put(11, "");
                    paramsCD.put(12, "0");
                    paramsCD.put(13, data.getNombre());
                    paramsCD.put(14, "");

                    dBhelper.saveCierreDia(db, paramsCD);

                } else {
                    row.moveToLast();
                    Log.e("num_prestamo_id_gesVE", row.getString(4) + "  " + row.getString(1) + " = " + data.getNumeroPrestamo() + "-" + data.getIdGestion());
                    if (row.getString(1).equals(data.getNumeroPrestamo() + "-" + data.getIdGestion()) &&
                            row.getString(4).equals("O")) {
                        HashMap<Integer, String> params = new HashMap<>();
                        params.put(0, data.getNumeroPrestamo() + "-" + data.getIdGestion());
                        params.put(1, data.getAsesorId());
                        params.put(2, row.getString(3));
                        params.put(3, "C");
                        params.put(4, data.getMonto());
                        params.put(5, data.getClaveCliente());
                        params.put(6, Miscellaneous.ObtenerFecha("timestamp"));
                        params.put(7, "");
                        params.put(8, "0");
                        params.put(9, data.getNumeroPrestamo());
                        params.put(10, "");

                        dBhelper.saveImpresionesVencida(db, params);

                        linea = line("Folio: ", "CV" + data.getAsesorId() + "-" + row.getString(3));
                    } else {
                        String sql = "SELECT folio FROM " + TBL_IMPRESIONES_VENCIDA_T + " WHERE num_prestamo_id_gestion = ? AND tipo_impresion = ? ";
                        Cursor row_folio = db.rawQuery(sql, new String[]{data.getNumeroPrestamo() + "-" + data.getIdGestion(), "O"});
                        if (row_folio.getCount() > 0) {
                            row_folio.moveToFirst();
                            HashMap<Integer, String> params = new HashMap<>();
                            params.put(0, data.getNumeroPrestamo() + "-" + data.getIdGestion());
                            params.put(1, data.getAsesorId());
                            params.put(2, row_folio.getString(0));
                            params.put(3, "C");
                            params.put(4, data.getMonto());
                            params.put(5, data.getClaveCliente());
                            params.put(6, Miscellaneous.ObtenerFecha("timestamp"));
                            params.put(7, "");
                            params.put(8, "0");
                            params.put(9, data.getNumeroPrestamo());
                            params.put(10, "");

                            dBhelper.saveImpresionesVencida(db, params);

                            linea = line("Folio: ", "CV" + data.getAsesorId() + "-" + row_folio.getString(0));
                        } else {
                            HashMap<Integer, String> params = new HashMap<>();
                            params.put(0, data.getNumeroPrestamo() + "-" + data.getIdGestion());
                            params.put(1, data.getAsesorId());
                            params.put(2, String.valueOf(row.getInt(3) + 1));
                            params.put(3, "O");
                            params.put(4, data.getMonto());
                            params.put(5, data.getClaveCliente());
                            params.put(6, Miscellaneous.ObtenerFecha("timestamp"));
                            params.put(7, "");
                            params.put(8, "0");
                            params.put(9, data.getNumeroPrestamo());
                            params.put(10, "");

                            dBhelper.saveImpresionesVencida(db, params);
                            linea = line("Folio: ", "CV" + data.getAsesorId() + "-" + (row.getInt(3) + 1));

                            HashMap<Integer, String> paramsCD = new HashMap<>();
                            paramsCD.put(0, session.getUser().get(0));
                            paramsCD.put(1, data.getIdGestion());
                            paramsCD.put(2, data.getNumeroPrestamo());
                            paramsCD.put(3, data.getClaveCliente());
                            paramsCD.put(4, "");
                            paramsCD.put(5, "");
                            paramsCD.put(6, "");
                            paramsCD.put(7, data.getTipoGestion());
                            paramsCD.put(8, "VENCIDA");
                            paramsCD.put(9, "");
                            paramsCD.put(10, "");
                            paramsCD.put(11, "");
                            paramsCD.put(12, "0");
                            paramsCD.put(13, data.getNombre());
                            paramsCD.put(14, "");

                            dBhelper.saveCierreDia(db, paramsCD);
                        }
                        row_folio.close();
                    }
                }
                row.close();
                //-----------------------------------------------------------------------------------------------------------------
            }

            dobleEspacio();

            posPtr.printNormal(ESC + "|lA" + ESC + "|bC" + ESC + "|1C" + linea);
            dobleEspacio();

            if (data.getTipoPrestamo().equals("VIGENTE") || data.getTipoPrestamo().equals("COBRANZA")) {

                URL url = null;
                try {
                    //url = new  URL("http://sidert.ddns.net:83"+WebServicesRoutes.CONTROLLER_MOVIL+"pagos/"+AES.encrypt(data.getIdPrestamo()));
                    url = new URL(session.getDominio() + WebServicesRoutes.CONTROLLER_MOVIL + "pagos/" + AES.encrypt(data.getIdPrestamo()));
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }

                posPtr.printQRCode(url.toString(), 300, 4, 0, 100, 1);

                posPtr.printNormal(ESC + "|lA" + ESC + "|bC" + ESC + "|1C" + "Escanea el codigo para ver tu   ");
                posPtr.printNormal(ESC + "|lA" + ESC + "|bC" + ESC + "|1C" + "historial de pagos o ingresa al ");
                posPtr.printNormal(ESC + "|lA" + ESC + "|bC" + ESC + "|1C" + "siguiente link: " + url.toString());

                dobleEspacio();
            }

            posPtr.printNormal(ESC + "|lA" + ESC + "|bC" + ESC + "|1C" + "En caso de dudas o aclaraciones ");
            posPtr.printNormal(ESC + "|lA" + ESC + "|bC" + ESC + "|1C" + "comuniquese al 800 112 6666");

            Servicios_Sincronizado ss = new Servicios_Sincronizado();
            ss.SendImpresionesVi(ctx, false);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        dobleEspacio();
        dobleEspacio();

    }

    private void SendMess(Context ctx, final String prestamoId, final String phone, final String nombre, final String monto, final String fecha, final String folio) {
        URL url = null;
        try {
            url = new URL(session.getDominio() + WebServicesRoutes.CONTROLLER_MOVIL + "pagos/" + AES.encrypt(prestamoId));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        String clienteGpo = nombre;
        if (nombre.length() > 17)
            clienteGpo = nombre.substring(0, 15);

        Log.e("Comienza SMS", "Prepara SMS");
        String numeroCelular = phone; /**Coloca el numero celular del cliente y en caso que el asesor que esta imprimiendo sea el ASESOR de pruebas manda SMS al numero de SOPORTE*/

        if (session.getUser().get(9).equals("134")) /**USUARIO_ID del usuario ASESOR de pruebas*/
            numeroCelular = "2292641103"; //Telefono para cuando se quieren hacer pruebas
        String mess = clienteGpo + " pago por $" + monto + " pesos. Folio " + folio + ".\n Valida tus pagos: " + url.toString();
        Log.e("Mensaje", mess);
        Miscellaneous.SendMess(numeroCelular, mess);

    }

    private String money(String numero) {
        String currency;
        NumberFormat format = NumberFormat.getCurrencyInstance(Locale.US);
        currency = format.format(Double.parseDouble(numero));
        return currency;
    }

    private String line(String left, String rigth) {
        int nCaracteres = left.length() + rigth.length();
        String result = left;
        if (nCaracteres <= 32) {
            nCaracteres = 32 - nCaracteres;
            for (int i = 0; i < nCaracteres; i++) {
                result += " ";
            }
            result = result + rigth;
        } else {
            String[] partes = rigth.split(" ");
            nCaracteres = result.length();
            for (int i = 0; i < partes.length; i++) {
                nCaracteres = nCaracteres + partes[i].length();
                if (nCaracteres < 32) {
                    result += partes[i] + " ";
                    nCaracteres++;
                } else {
                    nCaracteres = nCaracteres - partes[i].length();
                    for (; nCaracteres < 32; nCaracteres++) {
                        result += " ";
                    }
                    result += partes[i] + " ";
                }
            }
        }
        return result;
    }

    private void dobleEspacio() {
        try {
            posPtr.printNormal("" + LF + LF);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private String replaceStr(String str) {
        String[] value = {"Á", "É", "Í", "Ó", "Ú", "á", "é", "í", "ó", "ú", "Ñ", "ñ"};
        String[] valueReplace = {"A", "E", "I", "O", "U", "a", "e", "i", "o", "u", "N", "n"};

        for (int i = 0; i < 12; i++) {
            str = str.replace(value[i], valueReplace[i]);
        }
        return str;
    }

}
