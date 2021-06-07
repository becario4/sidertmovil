package com.sidert.sidertmovil.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.Log;

import com.lvrenyang.io.Page;
import com.lvrenyang.io.Pos;
import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.database.DBhelper;
import com.sidert.sidertmovil.models.MImpresion;
import com.sidert.sidertmovil.models.MReimpresion;
import com.sidert.sidertmovil.models.RecibosAgfCC;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import static com.sidert.sidertmovil.utils.Constants.TBL_PRESTAMOS;
import static com.sidert.sidertmovil.utils.Constants.TBL_RECIBOS_AGF_CC;
import static com.sidert.sidertmovil.utils.Constants.TBL_RECIBOS_CC;
import static com.sidert.sidertmovil.utils.Constants.TIMESTAMP;

public class Prints {
    public static int PrintTicket(Context ctx, Pos pos, int nPrintWidth, boolean bCutter, boolean bDrawer, boolean bBeeper, int nCount, int nPrintContent, int nCompressMethod, RecibosAgfCC ticket, JSONObject object)
    {
        int bPrintResult = -6;
        byte[] status = new byte[1];
        SessionManager session = new SessionManager(ctx);
        DBhelper dBhelper = new DBhelper(ctx);
        SQLiteDatabase db = dBhelper.getWritableDatabase();
        Cursor row;
        HashMap<Integer, String> params;
        int count_folio = 0;

        if (pos.POS_RTQueryStatus(status, 1, 3000, 2) && ((status[0] & 0x12) == 0x12)) {
            if ((status[0] & 0x08) == 0) {
                if(pos.POS_QueryStatus(status, 3000, 2)) {
                    Bitmap bm = getImageFromAssetsFile(ctx, "logo_impresion.png");

                    for(int i = 0; i < nCount; ++i)
                    {
                        if(!pos.GetIO().IsOpened())
                            break;

                        if(nPrintContent >= 1)
                        {
                            pos.POS_FeedLine();
                            pos.POS_S_Align(1);
                            pos.POS_PrintPicture(bm, nPrintWidth, 0, 0);
                            pos.POS_S_Align(0);
                            pos.POS_TextOut("En caso de dudas o aclaraciones\r\n", 4, 0, 0x00, 0x00, 0, 0);
                            pos.POS_TextOut("comunicarse al 800 112 6666\r\n\r\n", 4, 0, 0x00, 0x00, 0, 0);
                            pos.POS_TextOut("SERVICIOS INTEGRALES PARA EL\r\nDESARROLLO RURAL DEL TROPICO SA DE CV SOFOM ENR.(SIDERT)\r\n", 4, 0, 0, 0, 0, 0);
                            pos.POS_S_Align(0);
                            bm = getImageFromAssetsFile(ctx, "line.png");
                            pos.POS_PrintPicture(bm, nPrintWidth, 0, 0);
                            //pos.POS_TextOut("________________________________\r\n", 4, 0, 0x00, 0x00, 0, 0);
                            pos.POS_S_Align(1);
                            if(ticket.getTipoRecibo().equals("CC"))
                            {
                                pos.POS_TextOut("CIRCULO DE CREDITO\r\n", 4, 0, 0, 0, 0, 0);
                            }
                            else if(ticket.getTipoRecibo().equals("AGF"))
                            {
                                pos.POS_TextOut("APOYO PARA GASTOS FUNERARIOS\r\n", 4, 0, 0, 0, 0, 0);
                            }

                            if(ticket.isReeimpresion())
                            {
                                pos.POS_TextOut("\r\nREIMPRESION\r\n", 4, 0, 1, 1, 0, 0x08);
                            }

                            if (ticket.getTipoImpresion().equals("O"))
                            {
                                pos.POS_TextOut("\r\nOriginal\r\n\r\n", 4, 0, 0, 0, 0, 0);
                            }
                            else
                            {
                                pos.POS_TextOut("\r\nCopia\r\n\r\n", 4, 0, 0, 0, 0, 0);
                            }
                            pos.POS_S_Align(0);
                            pos.POS_TextOut("Fecha y hora:" + Miscellaneous.ObtenerFecha("timestamp") + "\r\n", 4, 0, 0, 0, 0, 0);

                            if(ticket.getTipoRecibo().equals("AGF"))
                            {
                                pos.POS_TextOut("Recibos de:" + ticket.getNombre().trim() + "\r\n", 4, 0, 0, 0, 0, 0);
                                pos.POS_FeedLine();
                                pos.POS_S_Align(1);
                                pos.POS_TextOut("Monto del pago\r\n\r\n", 4, 0, 1, 0, 0, 0);
                            }
                            else
                            {
                                try{
                                    if (object.getInt("tipo_credito") == 1)
                                    {
                                        pos.POS_TextOut("Cliente: " + object.getString("nombre_uno") + "\r\n", 4, 0, 0, 0, 0, 0);
                                    }
                                    else
                                    {
                                        pos.POS_TextOut("Grupo: " + object.getString("nombre_uno") + "\r\n", 4, 0, 0, 0, 0, 0);
                                        pos.POS_TextOut("Responsable: " + object.getString("nombre_dos") + "\r\n", 4, 0, 0, 0, 0, 0);
                                        pos.POS_TextOut("Total Integrantes: " + object.getString("total_integrantes") + "\r\n", 4, 0, 0, 0, 0, 0);
                                    }
                                }
                                catch(JSONException jex)
                                {
                                    jex.printStackTrace();
                                }
                            }
                            pos.POS_S_Align(0);
                            pos.POS_TextOut("Monto:" + Miscellaneous.moneyFormat(ticket.getMonto()) + "\r\n", 4, 0, 1, 0, 0, 0);
                            pos.POS_S_Align(1);
                            pos.POS_TextOut("\r\n", 4, 0, 0, 0, 0, 0);
                            pos.POS_TextOut("Cantidad en letra\r\n", 4, 0, 0x00, 0x00, 0, 0);

                            if (ticket.getMonto().contains(".5"))
                            {
                                pos.POS_TextOut(Miscellaneous.cantidadLetra(ticket.getMonto()) + " pesos 50/100 M.N.  " + "\r\n\r\n", 4, 0, 0, 0, 0, 0);
                            }
                            else
                            {
                                pos.POS_TextOut(Miscellaneous.cantidadLetra(ticket.getMonto()) + " pesos 00/100 M.N.  " + "\r\n\r\n", 4, 0, 0, 0, 0, 0);
                            }
                            pos.POS_S_Align(0);
                            if (ticket.getTipoImpresion().equals("O")){ //Original
                                pos.POS_TextOut("Firma Asesor: \r\n\r\n", 4, 0, 0, 0, 0, 0);
                            //    pos.POS_TextOut("________________________________", 4, 0, 0, 0, 0, 0);
                            }
                            else {
                                pos.POS_TextOut("Firma Cliente: \r\n\r\n", 4, 0, 0, 0, 0, 0);
                            //    pos.POS_TextOut("________________________________", 4, 0, 0, 0, 0, 0);

                            }
                            bm = getImageFromAssetsFile(ctx, "linea2.png");
                            pos.POS_PrintPicture(bm, nPrintWidth, 0, 0);
                            pos.POS_TextOut("\r\n"+replaceStr(ticket.getNombreFirma().trim())+"\r\n\r\n", 4, 0, 0, 0, 0, 0);

                            String sql =  "";

                            if(ticket.getTipoRecibo().equals("AGF"))
                            {
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
                                    params.put(10, row.getString(11));
                                    dBhelper.saveRecibosAgfCc(db, params);

                                }
                                else if (row.getCount() >= 2) {
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
                                    params.put(10, row.getString(11));
                                    dBhelper.saveRecibosAgfCc(db, params);
                                }
                                else {
                                    String sqlPrestamo = "";
                                    Cursor rowPrestamo;
                                    String plazo = "0";

                                    if(ticket.getGrupoId().compareTo("1") == 0)
                                    {
                                        sqlPrestamo = "SELECT periodicidad, num_pagos FROM " + TBL_PRESTAMOS + " WHERE grupo_id = 1 AND num_solicitud = ? LIMIT 1";
                                        rowPrestamo = db.rawQuery(sqlPrestamo, new String[]{ticket.getNumSolicitud()});
                                    }
                                    else {
                                        sqlPrestamo = "SELECT periodicidad, num_pagos FROM " + TBL_PRESTAMOS + " WHERE grupo_id = ? AND num_solicitud = ? LIMIT 1";
                                        rowPrestamo = db.rawQuery(sqlPrestamo, new String[]{ticket.getGrupoId(), ticket.getNumSolicitud()});
                                    }

                                    if (rowPrestamo.getCount() > 0){
                                        rowPrestamo.moveToFirst();
                                        plazo = String.valueOf(Math.ceil((Double.parseDouble(rowPrestamo.getString(0)) * Double.parseDouble(rowPrestamo.getString(1)))/ Double.parseDouble("30")));
                                    }

                                    rowPrestamo.close();

                                    String sqlFolio = "SELECT MAX(cast(folio as int)) AS folio FROM " + TBL_RECIBOS_AGF_CC;
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
                                    params.put(10, plazo);
                                    dBhelper.saveRecibosAgfCc(db, params);

                                }
                                row.close();
                            }
                            else
                            {
                                try {
                                    int tipoCredito = 0;
                                    String curp = "";

                                    try {
                                        tipoCredito = object.getInt("tipo_credito");
                                        curp = object.getString("curp");
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                    sql = "SELECT * FROM " + TBL_RECIBOS_CC + " WHERE curp = ? AND tipo_credito = ?";
                                    row = db.rawQuery(sql, new String[]{ticket.getCurp(), String.valueOf(tipoCredito)});

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
                                        Log.e("REIMPRESION", "Comienza la reimpresion: " + row.getString(8));

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
                                        String sqlFolio = "SELECT MAX(cast(folio as int)) AS folio FROM " + TBL_RECIBOS_CC;
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
                                catch(JSONException jex)
                                {
                                    jex.printStackTrace();
                                }
                            }

                            switch (ticket.getTipoRecibo()){
                                case "CC":
                                    pos.POS_TextOut("Folio:             CC-" + session.getUser().get(0) + "-" + count_folio + "\r\n\r\n", 4, 0, 0, 0, 0, 0);
                                    break;
                                case "AGF":
                                    pos.POS_TextOut("Folio:             AGF-" + session.getUser().get(0) + "-" + count_folio + "\r\n\r\n", 4, 0, 0, 0, 0, 0);
                                   break;
                            }
                            pos.POS_S_Align(1);
                            pos.POS_TextOut("NO  OLVIDES  VERIFICAR  TU  PAGO\r\n", 4, 0, 0, 0, 0, 0);
                            pos.POS_TextOut("El recibo debe de coincidir con lo entregado.\r\n", 4, 0, 0, 0, 0, 0);
                            pos.POS_FeedLine();
                            pos.POS_S_Align(0);
                            pos.POS_TextOut("El presente se toma con las RESERVAS de la Ley respecto a intereses pactados y los que se generan conforme a lo establecido en el articulo 364 del codigo de Comercio Vigente. El presente pago no es una remision de deuda, implica una novacion de la obligacion inicial.\r\n\r\n\r\n", 4, 0, 0, 0, 0, 0);

                            Servicios_Sincronizado ss = new Servicios_Sincronizado();
                            ss.SendRecibos(ctx, false);
                        }
                    }



                    if(bBeeper)
                        pos.POS_Beep(1, 5);
                    if(bCutter)
                        pos.POS_CutPaper();
                    if(bDrawer)
                        pos.POS_KickDrawer(0, 100);

                    bPrintResult = pos.POS_TicketSucceed(0, 30000);
                } else {
                    bPrintResult = -8;
                }
            } else {
                bPrintResult = -4;
            }
        } else {
            bPrintResult = -7;
        }

        return bPrintResult;
    }
    public static int PrintTicketRyC(Context ctx, Pos pos, int nPrintWidth, boolean bCutter, boolean bDrawer, boolean bBeeper, int nCount, int nPrintContent, int nCompressMethod, MImpresion ticket)
    {
        int bPrintResult = -6;
        byte[] status = new byte[1];
        SessionManager session = new SessionManager(ctx);
        DBhelper dBhelper = new DBhelper(ctx);
        SQLiteDatabase db = dBhelper.getWritableDatabase();
        Cursor row;
        HashMap<Integer, String> params;
        int count_folio = 0;

        if (pos.POS_RTQueryStatus(status, 1, 3000, 2) && ((status[0] & 0x12) == 0x12)) {
            if ((status[0] & 0x08) == 0) {
                if(pos.POS_QueryStatus(status, 3000, 2)) {
                    Bitmap bm = getImageFromAssetsFile(ctx, "logo_impresion.png");

                    for(int i = 0; i < nCount; ++i)
                    {
                        if(!pos.GetIO().IsOpened())
                            break;

                        if(nPrintContent >= 1)
                        {
                            pos.POS_FeedLine();
                            pos.POS_S_Align(1);
                            if (ticket.getTipoPrestamo().contains("VENCIDA")){
                                bm = BitmapFactory.decodeResource(ctx.getResources(), R.drawable.logo_cv);
                            }
                             else{
                                bm = BitmapFactory.decodeResource(ctx.getResources(), R.drawable.logo_impresion);
                            }
                          pos.POS_PrintPicture(bm, nPrintWidth, 0, 0);

                        }
                    }



                    if(bBeeper)
                        pos.POS_Beep(1, 5);
                    if(bCutter)
                        pos.POS_CutPaper();
                    if(bDrawer)
                        pos.POS_KickDrawer(0, 100);

                    bPrintResult = pos.POS_TicketSucceed(0, 30000);
                } else {
                    bPrintResult = -8;
                }
            } else {
                bPrintResult = -4;
            }
        } else {
            bPrintResult = -7;
        }

        return bPrintResult;
    }
    public static int PrintTicket(Context ctx, Page page, int nPrintWidth, int nPrintHeight) {
        int bPrintResult = 1;

        Pos pos = new Pos();

        //pos.Set(page.GetIO());

       /* byte[] status = new byte[1];
        if(pos.POS_RTQueryStatus(status, 4, 3000, 2))
        {
            // 缺纸
            if((status[0] & 0x60) != 0)
            {
                // 参考开发文档上的资料可知，type=4的时候，查询的是纸张状态。
                // 当纸尽的时候，会置第5,6位为0x60。
                // 所以，这里查纸张状态之后，对结果与上0x60，如果非零，表明纸尽。
            }
        }
        */
        //int w = nPrintWidth;
        //int h = 76; // 75mm

        //Bitmap logo = getImageFromAssetsFile(ctx, "logo_impresion.png");


        //page.PageEnter();
        //page.SetPrintArea(0, 0, w, h, Page.DIRECTION_LEFT_TO_RIGHT);
        //page.DrawBitmap(logo, Page.HORIZONTALALIGNMENT_CENTER, 10, logo.getWidth(), logo.getHeight(), Page.BINARYALGORITHM_THRESHOLD);
        //page.DrawText("En caso de dudas o aclaraciones ", Page.HORIZONTALALIGNMENT_CENTER, 10, 1, 1, page.FONTTYPE_STANDARD, page.FONTSTYLE_BOLD);

        //page.DrawText("中国福利彩票", Page.HORIZONTALALIGNMENT_CENTER, 10, 1, 1, Page.FONTTYPE_STANDARD, Page.FONTSTYLE_BOLD);
        //page.DrawText("销售期	2015033", 0, 10, 0, 0, Page.FONTTYPE_STANDARD, 0);
        //page.DrawText("兑奖期	2015033", nPrintWidth/2, 10, 0, 0, Page.FONTTYPE_STANDARD, 0);
        //page.DrawText("站号	230902001", 0, 40, 0, 0, Page.FONTTYPE_STANDARD, 0);
        //page.DrawText("7639-A", nPrintWidth/2, 40, 0, 0, Page.FONTTYPE_STANDARD, 0);
        //page.DrawText("注数		5", 0, 70, 0, 0, Page.FONTTYPE_STANDARD, 0);
        //page.DrawText("金额	10.00", nPrintWidth/2, 70, 0, 0, Page.FONTTYPE_STANDARD, 0);
        //page.DrawText("A:  02  07  10  17  20  21  25", 0, 100, 1, 1, Page.FONTTYPE_SMALL, CaysnCanvas.FONTSTYLE_BOLD);
        //page.DrawText("A:  02  07  10  17  20  21  25", 0, 140, 1, 1, Page.FONTTYPE_SMALL, CaysnCanvas.FONTSTYLE_BOLD);
        //page.DrawText("A:  02  07  10  17  20  21  25", 0, 180, 1, 1, Page.FONTTYPE_SMALL, CaysnCanvas.FONTSTYLE_BOLD);
        //page.DrawText("A:  02  07  10  17  20  21  25", 0, 220, 1, 1, Page.FONTTYPE_SMALL, CaysnCanvas.FONTSTYLE_BOLD);
        //page.DrawText("A:  02  07  10  17  20  21  25", 0, 260, 1, 1, Page.FONTTYPE_SMALL, CaysnCanvas.FONTSTYLE_BOLD);
        //page.DrawText("A:  02  07  10  17  20  21  25", 0, 300, 1, 1, Page.FONTTYPE_SMALL, CaysnCanvas.FONTSTYLE_BOLD);

        /*
        page.SetLineHeight(40);
        String number = "A: 02 07 10 17 20 21 25\nA: 02 07 10 17 20 21 25\nA: 02 07 10 17 20 21 25\nA: 02 07 10 17 20 21 25\nA: 02 07 10 17 20 21 25\nA: 02 07 10 17 20 21 25\n";
        page.DrawText(number, 0, 100, 1, 1, Page.FONTTYPE_SMALL, Page.FONTSTYLE_BOLD);

        page.DrawBarcode("179736629632968", 0, 380, 2, 80, Page.FONTTYPE_STANDARD, Page.HRI_FONTPOSITION_NONE, Page.BARCODE_TYPE_CODE128);
        page.DrawQRCode("179736629632968", Page.HORIZONTALALIGNMENT_RIGHT, 380, 3, 5, 1);

        page.SetCharacterRightSpacing(4);
        page.DrawText("玩法 七乐彩 单式*1 开奖时间2016-09-30", 0, 480, 0, 0, Page.FONTTYPE_STANDARD, 0);
        page.DrawText("销售时间  20160923 15:10:47", 0, 510, 0, 0, Page.FONTTYPE_STANDARD, 0);
        page.DrawText("地址  福建厦门", 0, 540, 0, 0, Page.FONTTYPE_STANDARD, 0);
        page.DrawText("自助站点通兑	客服热线：0771-5787586", 0, 570, 0, 0, Page.FONTTYPE_STANDARD, 0);
        page.DrawText("自助站点通兑	客服热线：0771-5787586", 0, 750, 0, 0, Page.FONTTYPE_STANDARD, 0);
        */

        //page.PagePrint();
        //page.PageExit();

        // 使用POS里面的切刀指令
        //pos.POS_CutPaper();

        //bPrintResult = page.GetIO().IsOpened();
        /*
        if(page.GetIO().IsOpened())
        {
            bPrintResult = 1;
        }
        else
        {
            bPrintResult = 0;
        }*/

        return bPrintResult;
    }

    public static String ResultCodeToString(int code) {
        switch (code) {
            case 0:
                return "¡Impresión exitosa!";
            case -1:
                return "Desconectar";
            case -2:
                return "¡Error al escribir!";
            case -3:
                return "¡Error al leer!";
            case -4:
                return "¡La impresora está fuera de línea!";
            case -5:
                return "¡La impresora no tiene papel!";
            case -7:
                return "¡Error en la consulta de estado de conexión en tiempo real!";
            case -8:
                return "¡No se pudo consultar el estado!";
            case -6:
            default:
                return "¡Error desconocido!";
        }
    }

    public static Bitmap getImageFromAssetsFile(Context ctx, String fileName) {
        Bitmap image = null;
        AssetManager am = ctx.getResources().getAssets();

        try {
            InputStream is = am.open(fileName);
            image = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return image;
    }

    public static Bitmap resizeImage(Bitmap bitmap, int w, int h) {
        // load the origial Bitmap
        Bitmap BitmapOrg = bitmap;

        int width = BitmapOrg.getWidth();
        int height = BitmapOrg.getHeight();
        int newWidth = w;
        int newHeight = h;

        // calculate the scale
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;

        // create a matrix for the manipulation
        Matrix matrix = new Matrix();
        // resize the Bitmap
        matrix.postScale(scaleWidth, scaleHeight);
        // if you want to rotate the Bitmap
        // matrix.postRotate(45);

        // recreate the new Bitmap
        Bitmap resizedBitmap = Bitmap.createBitmap(BitmapOrg, 0, 0, width,
                height, matrix, true);

        // make a Drawable from Bitmap to allow to set the Bitmap
        // to the ImageView, ImageButton or what ever
        return resizedBitmap;
    }
    private static String replaceStr (String str){
        String[] value = {"Á", "É", "Í", "Ó", "Ú", "á", "é", "í", "ó", "ú", "Ñ", "ñ"};
        String[] valueReplace = {"A", "E", "I", "O", "U", "a", "e", "i", "o", "u","N", "n"};

        for (int i = 0; i < 12; i++){
            str = str.replace(value[i],valueReplace[i]);
        }
        return str;
    }

}

