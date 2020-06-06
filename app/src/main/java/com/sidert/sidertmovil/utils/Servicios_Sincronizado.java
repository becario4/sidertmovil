package com.sidert.sidertmovil.utils;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.google.gson.GsonBuilder;
import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.database.DBhelper;
import com.sidert.sidertmovil.database.SidertTables;
import com.sidert.sidertmovil.models.MAmortizacion;
import com.sidert.sidertmovil.models.MAval;
import com.sidert.sidertmovil.models.MCartera;
import com.sidert.sidertmovil.models.MCierreDia;
import com.sidert.sidertmovil.models.MImpresion;
import com.sidert.sidertmovil.models.MImpresionRes;
import com.sidert.sidertmovil.models.MIntegrante;
import com.sidert.sidertmovil.models.MPago;
import com.sidert.sidertmovil.models.MPrestamoGpoRes;
import com.sidert.sidertmovil.models.MPrestamoRes;
import com.sidert.sidertmovil.models.MResCierreDia;
import com.sidert.sidertmovil.models.MResSaveOriginacionInd;
import com.sidert.sidertmovil.models.MResponseTracker;
import com.sidert.sidertmovil.models.MRespuestaGestion;
import com.sidert.sidertmovil.models.MSendImpresion;
import com.sidert.sidertmovil.models.MTracker;
import com.sidert.sidertmovil.models.ModeloGeolocalizacion;
import com.sidert.sidertmovil.models.ModeloResSaveGeo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Multipart;

import static com.sidert.sidertmovil.utils.Constants.CODEBARS;
import static com.sidert.sidertmovil.utils.Constants.COMENTARIO;
import static com.sidert.sidertmovil.utils.Constants.CONTROLLER_API;
import static com.sidert.sidertmovil.utils.Constants.CONTROLLER_MOVIL;
import static com.sidert.sidertmovil.utils.Constants.DIRECCION;
import static com.sidert.sidertmovil.utils.Constants.ENVIROMENT;
import static com.sidert.sidertmovil.utils.Constants.FACHADA;
import static com.sidert.sidertmovil.utils.Constants.FECHA_DISPOSITIVO;
import static com.sidert.sidertmovil.utils.Constants.FECHA_ENVIO;
import static com.sidert.sidertmovil.utils.Constants.FECHA_FIN_GEO;
import static com.sidert.sidertmovil.utils.Constants.FECHA_INI_GEO;
import static com.sidert.sidertmovil.utils.Constants.FECHA_RESPUESTA;
import static com.sidert.sidertmovil.utils.Constants.FICHA_TIPO;
import static com.sidert.sidertmovil.utils.Constants.LATITUD;
import static com.sidert.sidertmovil.utils.Constants.LONGITUD;
import static com.sidert.sidertmovil.utils.Constants.PRESTAMO_ID;
import static com.sidert.sidertmovil.utils.Constants.TBL_AMORTIZACIONES;
import static com.sidert.sidertmovil.utils.Constants.TBL_AMORTIZACIONES_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_AVAL;
import static com.sidert.sidertmovil.utils.Constants.TBL_AVAL_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_CARTERA_GPO;
import static com.sidert.sidertmovil.utils.Constants.TBL_CARTERA_GPO_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_CARTERA_IND;
import static com.sidert.sidertmovil.utils.Constants.TBL_CARTERA_IND_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_CIERRE_DIA_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_GEO_RESPUESTAS_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_IMPRESIONES_VENCIDA;
import static com.sidert.sidertmovil.utils.Constants.TBL_IMPRESIONES_VENCIDA_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_IMPRESIONES_VIGENTE;
import static com.sidert.sidertmovil.utils.Constants.TBL_IMPRESIONES_VIGENTE_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_MIEMBROS_GPO;
import static com.sidert.sidertmovil.utils.Constants.TBL_MIEMBROS_GPO_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_PAGOS;
import static com.sidert.sidertmovil.utils.Constants.TBL_PAGOS_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_PRESTAMOS_GPO;
import static com.sidert.sidertmovil.utils.Constants.TBL_PRESTAMOS_GPO_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_PRESTAMOS_IND;
import static com.sidert.sidertmovil.utils.Constants.TBL_PRESTAMOS_IND_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_REIMPRESION_VIGENTE;
import static com.sidert.sidertmovil.utils.Constants.TBL_REIMPRESION_VIGENTE_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_RESPUESTAS_GPO;
import static com.sidert.sidertmovil.utils.Constants.TBL_RESPUESTAS_GPO_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_RESPUESTAS_IND;
import static com.sidert.sidertmovil.utils.Constants.TBL_RESPUESTAS_IND_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_RESPUESTAS_IND_V_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_RESPUESTAS_INTEGRANTE_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_TRACKER_ASESOR;
import static com.sidert.sidertmovil.utils.Constants.TBL_TRACKER_ASESOR_T;
import static com.sidert.sidertmovil.utils.Constants.TEL_CELULAR_SECRETARIA;
import static com.sidert.sidertmovil.utils.Constants.TIMESTAMP;
import static com.sidert.sidertmovil.utils.Constants.TIPO;
import static com.sidert.sidertmovil.utils.Constants.firma;
import static com.sidert.sidertmovil.utils.Constants.print_off;

public class Servicios_Sincronizado {

    Context ctx;

    public void GetGeolocalizacion(final Context ctx, final boolean showDG, final boolean incluir_gestiones){
        Log.e("GetGeolocalizacion", "Inicia la obtencion de fichas "+incluir_gestiones);
        SessionManager session = new SessionManager(ctx);
        final DBhelper dBhelper = new DBhelper(ctx);
        final SQLiteDatabase db = dBhelper.getWritableDatabase();

        final AlertDialog loading = Popups.showLoadingDialog(ctx, R.string.please_wait, R.string.loading_info);

        if (showDG)
            loading.show();
        GetGeolocalizadas(ctx);

        ManagerInterface api = new RetrofitClient().generalRF(CONTROLLER_MOVIL, ctx).create(ManagerInterface.class);

        Call<ModeloGeolocalizacion> call = api.getGeolocalizacion("Bearer "+ session.getUser().get(7));
        call.enqueue(new Callback<ModeloGeolocalizacion>() {
            @Override
            public void onResponse(Call<ModeloGeolocalizacion> call, Response<ModeloGeolocalizacion> response) {

                Log.e("Geolocalizacion Code", ""+response.code());
                switch (response.code()) {
                    case 200:
                        ModeloGeolocalizacion modeloGeo = response.body();

                        if(modeloGeo.getGrupalesGestionadas().size() > 0){
                            for (int h = 0; h < modeloGeo.getGrupalesGestionadas().size(); h++){
                                Cursor rowGeoGG;

                                String sql = "SELECT g.* FROM " + TBL_GEO_RESPUESTAS_T + " AS g LEFT JOIN " + TBL_CARTERA_GPO_T + " AS cg ON cg.id_cartera = g.id_cartera LEFT JOIN " + TBL_PRESTAMOS_GPO_T + " AS pg ON pg.id_grupo = cg.id_cartera LEFT JOIN " + TBL_MIEMBROS_GPO_T + " AS m ON m.id_prestamo = pg.id_prestamo WHERE g.clave = ?";
                                rowGeoGG = db.rawQuery(sql, new String[]{modeloGeo.getGrupalesGestionadas().get(h).getTipo()});

                                if (rowGeoGG.getCount() > 0){
                                    rowGeoGG.moveToFirst();
                                    Log.e("ACtualza", "grupal");
                                    ContentValues cv = new ContentValues();
                                    cv.put("latitud", modeloGeo.getGrupalesGestionadas().get(h).getLatitud());
                                    cv.put("longitud", modeloGeo.getGrupalesGestionadas().get(h).getLongitud());
                                    cv.put("direccion_capturada", modeloGeo.getGrupalesGestionadas().get(h).getDireccion());
                                    cv.put("codigo_barras", modeloGeo.getGrupalesGestionadas().get(h).getBarcode());
                                    cv.put("fachada", modeloGeo.getGrupalesGestionadas().get(h).getFotoFachada().replace("\"",""));
                                    cv.put("comentario", modeloGeo.getGrupalesGestionadas().get(h).getComentario());
                                    cv.put("fecha_fin_geo", modeloGeo.getGrupalesGestionadas().get(h).getFechaGestionFin());
                                    cv.put("fecha_envio_geo", modeloGeo.getGrupalesGestionadas().get(h).getFechaEnvio());

                                    db.update(TBL_GEO_RESPUESTAS_T, cv, "id_cartera = ? AND clave = ?", new String[]{rowGeoGG.getString(1), rowGeoGG.getString(17)});

                                    new DescargarFotoFachada()
                                            .execute(new GsonBuilder().create().toJson(modeloGeo.getGrupalesGestionadas().get(h).getFotoFachada()), ctx, rowGeoGG.getString(0));

                                }//Fin de IF que existe registro de gestion
                                rowGeoGG.close();
                            }//Fin de For para guardado de Grupales Gestionadas
                        }//Fin de Grupales Gestionadas

                        Log.e("Individuales conte", modeloGeo.getIndividualesGestionadas().size()+" ------------");
                        if(modeloGeo.getIndividualesGestionadas().size() > 0){
                            for (int h = 0; h < modeloGeo.getIndividualesGestionadas().size(); h++){
                                Cursor rowGeoGi;

                                String sql = "SELECT g.* FROM " + TBL_GEO_RESPUESTAS_T + " AS g LEFT JOIN " + TBL_CARTERA_IND_T + " AS ci ON ci.id_cartera = g.id_cartera LEFT JOIN " + TBL_PRESTAMOS_IND_T + " AS pi ON pi.id_cliente = ci.id_cartera  WHERE g.tipo_geolocalizacion = ?";
                                rowGeoGi = db.rawQuery(sql, new String[]{modeloGeo.getIndividualesGestionadas().get(h).getTipo()});

                                Log.e("SQL_IND", sql);
                                if (rowGeoGi.getCount() > 0){
                                    rowGeoGi.moveToFirst();
                                    Log.e("ACtualza", "individual");
                                    ContentValues cv = new ContentValues();
                                    cv.put("latitud", modeloGeo.getIndividualesGestionadas().get(h).getLatitud());
                                    cv.put("longitud", modeloGeo.getIndividualesGestionadas().get(h).getLongitud());
                                    cv.put("direccion_capturada", modeloGeo.getIndividualesGestionadas().get(h).getDireccion());
                                    cv.put("codigo_barras", modeloGeo.getIndividualesGestionadas().get(h).getBarcode());
                                    cv.put("fachada", modeloGeo.getIndividualesGestionadas().get(h).getFotoFachada().replace("\"",""));
                                    cv.put("comentario", modeloGeo.getIndividualesGestionadas().get(h).getComentario());
                                    cv.put("fecha_fin_geo", modeloGeo.getIndividualesGestionadas().get(h).getFechaGestionFin());
                                    cv.put("fecha_envio_geo", modeloGeo.getIndividualesGestionadas().get(h).getFechaEnvio());

                                    db.update(TBL_GEO_RESPUESTAS_T, cv, "id_cartera = ? AND clave = ?", new String[]{rowGeoGi.getString(1), rowGeoGi.getString(17)});

                                    new DescargarFotoFachada()
                                            .execute(new GsonBuilder().create().toJson(modeloGeo.getIndividualesGestionadas().get(h).getFotoFachada()), ctx, rowGeoGi.getString(0));

                                }//Fin de IF que existe registro de gestion
                                rowGeoGi.close();
                            }//Fin de For para guardado de Grupales Gestionadas
                        }//Fin de Grupales Gestionadas

                        break;
                    default:
                        break;
                }

                if (showDG)
                    loading.dismiss();
            }

            @Override
            public void onFailure(Call<ModeloGeolocalizacion> call, Throwable t) {
                if (showDG)
                    loading.dismiss();
            }
        });
    }

    public void SaveGeolocalizacion(Context ctx, boolean flag){

        final AlertDialog loading = Popups.showLoadingDialog(ctx, R.string.please_wait, R.string.loading_info);

        if (flag)
            loading.show();

        final DBhelper dBhelper = new DBhelper(ctx);
        final SQLiteDatabase db = dBhelper.getWritableDatabase();

        Cursor row;

        String sql = "SELECT * FROM (SELECT gri.*, pi.id_prestamo, pi.fecha_dispositivo FROM " + TBL_GEO_RESPUESTAS_T + " AS gri INNER JOIN " + TBL_PRESTAMOS_IND_T + " AS pi ON pi.id_cliente = gri.id_cartera WHERE gri.estatus = 0 AND gri.tipo_ficha = 1 UNION SELECT grg.*, pg.id_prestamo, pg.fecha_dispositivo FROM "+TBL_GEO_RESPUESTAS_T+" AS grg INNER JOIN "+TBL_PRESTAMOS_GPO_T+" AS pg ON pg.id_grupo = grg.id_cartera WHERE grg.estatus = 0 AND grg.tipo_ficha = 2)  AS geo_respuestas";

        row = db.rawQuery(sql, null);

        if (row.getCount() > 0){
            row.moveToFirst();
            for(int i = 0; i < row.getCount(); i++){
                JSONObject object = new JSONObject();
                try {
                    if (row.getInt(3) == 1)
                        object.put(TIPO, row.getString(4));
                    else
                        object.put(TIPO, row.getString(17));
                    object.put(FICHA_TIPO, row.getInt(3));
                    object.put(PRESTAMO_ID, row.getLong(18));
                    object.put(CODEBARS, row.getString(11));
                    object.put(LATITUD, row.getString(8));
                    object.put(LONGITUD, row.getString(9));
                    object.put(DIRECCION, row.getString(10));
                    object.put(COMENTARIO, row.getString(13));
                    object.put(FACHADA, row.getString(12));
                    object.put(FECHA_DISPOSITIVO, row.getString(19));
                    object.put(FECHA_RESPUESTA, row.getString(14));
                    object.put(FECHA_INI_GEO, row.getString(14));
                    object.put(FECHA_FIN_GEO, row.getString(14));
                    object.put(FECHA_ENVIO, Miscellaneous.ObtenerFecha(TIMESTAMP));
                    SendGeolocalizacion(ctx, object, row.getLong(0));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                row.moveToNext();
            }
        }
        row.close();

        if (flag)
            loading.dismiss();
    }

    private void SendGeolocalizacion(final Context ctx, JSONObject respuesta, final Long _id){
        Log.e("JSONGeo", respuesta.toString());
        SessionManager session = new SessionManager(ctx);
        DBhelper dBhelper = new DBhelper(ctx);
        final SQLiteDatabase db = dBhelper.getWritableDatabase();
        if (NetworkStatus.haveNetworkConnection(ctx)){
            try {


                final File image = new File(Constants.ROOT_PATH + "Fachada/"+respuesta.getString(FACHADA));

                RequestBody latBody = RequestBody.create(MultipartBody.FORM, respuesta.getString(LATITUD));
                RequestBody lngBody = RequestBody.create(MultipartBody.FORM, respuesta.getString(LONGITUD));
                RequestBody direccionBody = RequestBody.create(MultipartBody.FORM, respuesta.getString(DIRECCION));
                RequestBody barcodeBody = RequestBody.create(MultipartBody.FORM, respuesta.getString(CODEBARS));
                RequestBody fechaDispositivoBody = RequestBody.create(MultipartBody.FORM, respuesta.getString(FECHA_DISPOSITIVO));
                RequestBody fechaGestionIniBody = RequestBody.create(MultipartBody.FORM, respuesta.getString(FECHA_INI_GEO));
                RequestBody fechaGestionFinBody = RequestBody.create(MultipartBody.FORM, respuesta.getString(FECHA_FIN_GEO));
                RequestBody fechaEnvioBody = RequestBody.create(MultipartBody.FORM, Miscellaneous.ObtenerFecha(TIMESTAMP));
                RequestBody comentarioBody = RequestBody.create(MultipartBody.FORM, respuesta.getString(Constants.COMENTARIO));
                RequestBody tipoBody = RequestBody.create(MultipartBody.FORM, respuesta.getString(TIPO));
                RequestBody fichaTipoBody = RequestBody.create(MultipartBody.FORM, respuesta.getString(FICHA_TIPO));
                RequestBody prestamoIdBody = RequestBody.create(MultipartBody.FORM, respuesta.getString(PRESTAMO_ID));
                MultipartBody.Part body = null;
                if (image != null) {
                    RequestBody imageBody =
                            RequestBody.create(
                                    MediaType.parse("image/*"), image);

                    body = MultipartBody.Part.createFormData("foto_fachada", image.getName(), imageBody);
                }

                ManagerInterface api = new RetrofitClient().generalRF(CONTROLLER_MOVIL, ctx).create(ManagerInterface.class);

                Call<ModeloResSaveGeo> call = api.guardarGeo("Bearer "+ session.getUser().get(7),
                                                                tipoBody,
                                                                fichaTipoBody,
                                                                prestamoIdBody,
                                                                latBody,
                                                                lngBody,
                                                                direccionBody,
                                                                barcodeBody,
                                                                comentarioBody,
                                                                fechaGestionIniBody,
                                                                fechaGestionIniBody,
                                                                fechaGestionFinBody,
                                                                fechaDispositivoBody,
                                                                fechaEnvioBody,
                                                                body);

                call.enqueue(new Callback<ModeloResSaveGeo>() {
                    @Override
                    public void onResponse(Call<ModeloResSaveGeo> call, Response<ModeloResSaveGeo> response) {
                        switch (response.code()){
                            case 200:
                                ModeloResSaveGeo res = response.body();
                                Log.e("ResponseGuardado", new GsonBuilder().create().toJson(res));

                                ContentValues valores = new ContentValues();
                                valores.put("fecha_envio_geo", Miscellaneous.ObtenerFecha("timestamp"));
                                valores.put("estatus", 1);

                                db.update(TBL_GEO_RESPUESTAS_T, valores, "_id = ?", new String[]{String.valueOf(_id)});

                                break;
                            default:
                                Log.e("Mensaje Code", response.code()+" : "+response.message());
                                //Toast.makeText(ctx, "No se logró enviar codigo: " +response.code(), Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }

                    @Override
                    public void onFailure(Call<ModeloResSaveGeo> call, Throwable t) {
                        Log.e("FailSaveImage", t.getMessage());
                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    public void SaveRespuestaGestion(Context ctx, boolean showDG){

        final AlertDialog loading = Popups.showLoadingDialog(ctx, R.string.please_wait, R.string.loading_info);

        if (showDG)
            loading.show();
        SessionManager session = new SessionManager(ctx);
        final DBhelper dBhelper = new DBhelper(ctx);
        final SQLiteDatabase db = dBhelper.getWritableDatabase();

        Cursor row;

        String x =  "SELECT * from " + TBL_RESPUESTAS_IND_V_T ;
        row = db.rawQuery(x, null);
        Log.e("countIndvidual", row.getCount()+" total");

        String query;

        if (ENVIROMENT)
            query = "SELECT * FROM (SELECT i._id,i.id_prestamo,i.latitud,i.longitud,i.contacto,i.motivo_aclaracion,i.comentario,i.actualizar_telefono,i.nuevo_telefono,i.resultado_gestion,i.motivo_no_pago,i.fecha_fallecimiento,i.medio_pago,i.fecha_pago,i.pagara_requerido AS x,i.pago_realizado,i.imprimir_recibo,i.folio,i.evidencia,i.tipo_imagen,i.gerente,i.firma,i.fecha_inicio,i.fecha_fin,i.res_impresion,i.estatus_pago,i.saldo_corte,i.saldo_actual,'1' AS tipo_gestion,pi.num_solicitud,pi.fecha_establecida, ci.dia AS dia_semana, pi.monto_requerido, pi.tipo_cartera, pi.monto_amortizacion, i.dias_atraso, '' AS fecha_promesa_pago, '' AS monto_promesa FROM "+ TBL_RESPUESTAS_IND + " AS i INNER JOIN " + TBL_PRESTAMOS_IND + " AS pi ON i.id_prestamo = pi.id_prestamo INNER JOIN " + TBL_CARTERA_IND + " AS ci ON pi.id_cliente = ci.id_cartera WHERE i.estatus = ? UNION SELECT  g._id,g.id_prestamo,g.latitud,g.longitud,g.contacto,g.motivo_aclaracion,g.comentario,g.actualizar_telefono,g.nuevo_telefono,g.resultado_gestion,g.motivo_no_pago,g.fecha_fallecimiento,g.medio_pago,g.fecha_pago,g.detalle_ficha AS x,g.pago_realizado,g.imprimir_recibo,g.folio,g.evidencia,g.tipo_imagen,g.gerente,g.firma,g.fecha_inicio,g.fecha_fin,g.res_impresion,g.estatus_pago,g.saldo_corte,g.saldo_actual,'2' AS tipo_gestion,pg.num_solicitud,pg.fecha_establecida, cg.dia AS dia_semana, pg.monto_requerido, pg.tipo_cartera, pg.monto_amortizacion, g.dias_atraso, '' AS fecha_promesa_pago, '' AS monto_promesa FROM " + TBL_RESPUESTAS_GPO + " AS g INNER JOIN " + TBL_PRESTAMOS_GPO + " AS pg ON g.id_prestamo = pg.id_prestamo INNER JOIN " + TBL_CARTERA_GPO + " AS cg ON pg.id_grupo = cg.id_cartera WHERE g.estatus = ?) AS respuestas";
        else
            query = "SELECT * FROM (SELECT i._id,i.id_prestamo,i.latitud,i.longitud,i.contacto,i.motivo_aclaracion,i.comentario,i.actualizar_telefono,i.nuevo_telefono,i.resultado_gestion,i.motivo_no_pago,i.fecha_fallecimiento,i.medio_pago,i.fecha_pago,i.pagara_requerido AS x,i.pago_realizado,i.imprimir_recibo,i.folio,i.evidencia,i.tipo_imagen,i.gerente,i.firma,i.fecha_inicio,i.fecha_fin,i.res_impresion,i.estatus_pago,i.saldo_corte,i.saldo_actual,'1' AS tipo_gestion,pi.num_solicitud,pi.fecha_establecida, ci.dia AS dia_semana, pi.monto_requerido, pi.tipo_cartera, pi.monto_amortizacion, i.dias_atraso, '' AS fecha_monto_promesa, '' AS monto_promesa, 'VIGENTE' AS tipo, 0 AS prestamo_id_integrante, '' AS serial_id FROM "+ TBL_RESPUESTAS_IND_T + " AS i INNER JOIN " + TBL_PRESTAMOS_IND_T + " AS pi ON i.id_prestamo = pi.id_prestamo INNER JOIN " + TBL_CARTERA_IND_T + " AS ci ON pi.id_cliente = ci.id_cartera WHERE i.estatus = ? UNION SELECT g._id,g.id_prestamo,g.latitud,g.longitud,g.contacto,g.motivo_aclaracion,g.comentario,g.actualizar_telefono,g.nuevo_telefono,g.resultado_gestion,g.motivo_no_pago,g.fecha_fallecimiento,g.medio_pago,g.fecha_pago,g.detalle_ficha AS x,g.pago_realizado,g.imprimir_recibo,g.folio,g.evidencia,g.tipo_imagen,g.gerente,g.firma,g.fecha_inicio,g.fecha_fin,g.res_impresion,g.estatus_pago,g.saldo_corte,g.saldo_actual,'2' AS tipo_gestion,pg.num_solicitud,pg.fecha_establecida, cg.dia AS dia_semana, pg.monto_requerido, pg.tipo_cartera, pg.monto_amortizacion, g.dias_atraso, '' AS fecha_monto_promesa, '' AS monto_promesa, 'VIGENTE' AS tipo, 0 AS prestamo_id_integrante, '' AS serial_id FROM " + TBL_RESPUESTAS_GPO_T + " AS g INNER JOIN " + TBL_PRESTAMOS_GPO_T + " AS pg ON g.id_prestamo = pg.id_prestamo INNER JOIN " + TBL_CARTERA_GPO_T + " AS cg ON pg.id_grupo = cg.id_cartera WHERE g.estatus = ? UNION SELECT vi._id, vi.id_prestamo, vi.latitud, vi.longitud, vi.contacto, '' AS motivo_aclaracion, vi.comentario, vi.actualizar_telefono, vi.nuevo_telefono, vi.resultado_gestion, vi.motivo_no_pago, vi.fecha_fallecimiento, vi.medio_pago, vi.fecha_pago, vi.pagara_requerido AS x, vi.pago_realizado, vi.imprimir_recibo, vi.folio, vi.evidencia, vi.tipo_imagen, vi.gerente, vi.firma, vi.fecha_inicio, vi.fecha_fin, vi.res_impresion, vi.estatus_pago, vi.saldo_corte, vi.saldo_actual,'1' AS tipo_gestion, pvi.num_solicitud, pvi.fecha_establecida, cvi.dia AS dia_semana, pvi.monto_requerido, 'VENCIDA' AS tipo_cartera, pvi.monto_amortizacion, vi.dias_atraso, vi.fecha_monto_promesa, vi.monto_promesa, 'VENCIDA' as tipo, 0 AS prestamo_id_integrante, vi.serial_id FROM "+TBL_RESPUESTAS_IND_V_T+" AS vi INNER JOIN " + TBL_PRESTAMOS_IND_T + " AS pvi ON vi.id_prestamo = pvi.id_prestamo INNER JOIN " + TBL_CARTERA_IND_T + " AS cvi ON pvi.id_cliente = cvi.id_cartera WHERE vi.estatus = ? UNION SELECT v._id, v.id_prestamo, v.latitud, v.longitud, v.contacto, '' AS motivo_aclaracion, v.comentario, v.actualizar_telefono, v.nuevo_telefono, v.resultado_gestion, v.motivo_no_pago, v.fecha_fallecimiento, v.medio_pago, v.fecha_pago,v.pagara_requerido AS x, v.pago_realizado, v.imprimir_recibo, v.folio, v.evidencia, v.tipo_imagen, v.gerente, v.firma, v.fecha_inicio, v.fecha_fin, v.res_impresion, v.estatus_pago,v.saldo_corte, v.saldo_actual,'2' AS tipo_gestion, pv.num_solicitud, pv.fecha_establecida, cv.dia AS dia_semana, mv.monto_requerido, 'VENCIDA' AS tipo_cartera, mv.monto_requerido AS monto_amortizacion, v.dias_atraso, v.fecha_monto_promesa, v.monto_promesa, 'VENCIDA' AS tipo, mv.id_prestamo_integrante AS prestamo_id_integrante, v.serial_id FROM " + TBL_RESPUESTAS_INTEGRANTE_T + " AS v INNER JOIN " + TBL_PRESTAMOS_GPO_T + " AS pv ON v.id_prestamo = pv.id_prestamo INNER JOIN " + TBL_CARTERA_GPO_T + " AS cv ON pv.id_grupo = cv.id_cartera INNER JOIN " + TBL_MIEMBROS_GPO_T + " AS mv ON mv.id_integrante = v.id_integrante WHERE v.estatus = ?) AS respuestas";

        row = db.rawQuery(query, new String[]{"1", "1", "1", "1"});

        Log.e("rowGestionada", row.getCount()+" total");
        if (row.getCount() > 0){
            row.moveToFirst();

            String fileNameReport = "dGFibGUtY29i.csv";
            File fileReport = new File(Environment.getExternalStorageDirectory().getAbsolutePath(),fileNameReport);

            Writer out = null;
            try {

                out = new BufferedWriter(new OutputStreamWriter(
                        new FileOutputStream(fileReport), "ISO-8859-1"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            String title = "id_prestamo,num_solicitud,respuesta"+"\n";
            try {
                out.append(title);
            } catch (IOException e) {
                e.printStackTrace();
            }
            for (int i = 0; i < row.getCount(); i++){
                HashMap<String, String> params = new HashMap<>();
                params.put("id_prestamo", row.getString(1));
                params.put("num_solicitud", row.getString(29));
                JSONObject json_res = Miscellaneous.RowTOJson(row, ctx);
                params.put("respuesta", json_res.toString());
                params.put("tipo", row.getString(38));

                String val = row.getString(1)+","+row.getString(29)+","+json_res.toString()+"\n";
                try {
                    out.append(val);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    String evidencia = "";
                    String tipo_imagen = "-1";
                    String firma = "";
                    if (json_res.has("evidencia"))
                        evidencia = json_res.getString("evidencia");
                    if (json_res.has("tipo_imagen"))
                        tipo_imagen = json_res.getString("tipo_imagen");
                    if (json_res.has("firma"))
                        firma = json_res.getString("firma");

                    //Log.e("res_envio", json_res.toString());

                    SendrespuestaGestion(ctx, params, row.getInt(28), row.getString(0), evidencia, tipo_imagen, firma, showDG);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                row.moveToNext();
            }

            try {
                out.flush();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        row.close();

        if (showDG)
            loading.dismiss();

    }

    public void SaveCierreDia(Context ctx, boolean showDG){
        final DBhelper dBhelper = new DBhelper(ctx);
        final SQLiteDatabase db = dBhelper.getWritableDatabase();

        Cursor row;

        String query =  "SELECT * FROM " + TBL_CIERRE_DIA_T + " WHERE estatus = 1";
        row = db.rawQuery(query, null);

        if (row.getCount() > 0){
            row.moveToFirst();

            for (int i = 0; i < row.getCount(); i++){
                MCierreDia item = new MCierreDia();
                item.setId(row.getString(0));
                item.setNombre(row.getString(14));
                item.setMonto(row.getString(6));
                item.setEvidencia(row.getString(7));
                item.setIdRespuesta(row.getString(2));
                item.setNumPrestamo(row.getString(3));
                item.setClaveCliente(row.getString(4));
                item.setTipoCierre(row.getInt(8));
                item.setTipoPrestamo(row.getString(9));
                item.setEstatus(row.getInt(13));
                item.setSerialId(row.getString(15));
                item.setMedioPago(Miscellaneous.GetIdMedioPago(row.getString(5)));
                item.setFechaInicio(row.getString(10));
                item.setFechaFin(row.getString(11));

                new RegistrarCierreDia().execute(ctx, item);
                row.moveToNext();
            }
        }

    }



    private void SendrespuestaGestion(final Context ctx, final HashMap<String, String> params, final int tipo_gestion, final String _id, String imagen, String tipo_imagen, String firma, final boolean DGshow){
        final AlertDialog loading = Popups.showLoadingDialog(ctx, R.string.please_wait, R.string.loading_info);

        if (DGshow)
            loading.show();
        SessionManager session = new SessionManager(ctx);
        final DBhelper dBhelper = new DBhelper(ctx);
        final SQLiteDatabase db = dBhelper.getWritableDatabase();
        if (NetworkStatus.haveNetworkConnection(ctx)) {
            RequestBody idPrestamoBody = RequestBody.create(MultipartBody.FORM, params.get("id_prestamo"));
            RequestBody numSolicitudBody = RequestBody.create(MultipartBody.FORM, params.get("num_solicitud"));
            RequestBody respuestaBody = RequestBody.create(MultipartBody.FORM, params.get("respuesta"));
            RequestBody tipoBody = RequestBody.create(MultipartBody.FORM, params.get("tipo"));


            MultipartBody.Part evidenciaBody = null;
            File imagen_evidencia = null;

            switch (tipo_imagen){
                case "0":
                    imagen_evidencia = new File(Constants.ROOT_PATH + "Fachada/" + imagen);
                    break;
                case "1":
                case "2":
                    imagen_evidencia = new File(Constants.ROOT_PATH + "Evidencia/" + imagen);
                    break;
            }

            if (!imagen.isEmpty() && imagen_evidencia != null) {
                RequestBody imageBody =
                        RequestBody.create(
                                MediaType.parse("image/*"), imagen_evidencia);

                evidenciaBody = MultipartBody.Part.createFormData("evidencia", imagen_evidencia.getName(), imageBody);
            }
            else {
                RequestBody attachmentEmpty = RequestBody.create(MediaType.parse("text/plain"), "");
                evidenciaBody = MultipartBody.Part.createFormData("evidencia", "", attachmentEmpty);
            }

            MultipartBody.Part firmaBody = null;
            final File image_firma = new File(Constants.ROOT_PATH + "Firma/"+firma);
            if (!firma.isEmpty() && image_firma != null) {
                RequestBody imageBody =
                        RequestBody.create(
                                MediaType.parse("image/*"), image_firma);

                firmaBody = MultipartBody.Part.createFormData("firma", image_firma.getName(), imageBody);
            }
            else {
                RequestBody attachmentEmpty = RequestBody.create(MediaType.parse("text/plain"), "");
                firmaBody = MultipartBody.Part.createFormData("firma", "", attachmentEmpty);
            }

            ManagerInterface api = new RetrofitClient().generalRF(CONTROLLER_MOVIL, ctx).create(ManagerInterface.class);

            Log.e("idPRestamo", params.get("id_prestamo") );
            Log.e("numSolicitud", params.get("num_solicitud"));
            Log.e("RespuestaGes", params.get("respuesta"));
            Log.e("RespuestaGes", params.get("tipo"));
            Call<MRespuestaGestion> call = api.guardarRespuesta("Bearer "+ session.getUser().get(7),
                    idPrestamoBody,
                    numSolicitudBody,
                    respuestaBody,
                    tipoBody,
                    evidenciaBody,
                    firmaBody);

            call.enqueue(new Callback<MRespuestaGestion>() {
                @Override
                public void onResponse(Call<MRespuestaGestion> call, Response<MRespuestaGestion> response) {
                    Log.e("Response", "Code: "+response.code());
                    switch (response.code()){
                        case 200:
                            MRespuestaGestion r = response.body();
                            ContentValues cv = new ContentValues();
                            cv.put("fecha_envio", Miscellaneous.ObtenerFecha(TIMESTAMP));
                            cv.put("estatus", 2);

                            if (params.get("tipo").equals("VIGENTE"))
                                db.update((tipo_gestion == 1)?TBL_RESPUESTAS_IND_T:TBL_RESPUESTAS_GPO_T, cv, "_id = ?", new String[]{_id});
                            else
                                db.update((tipo_gestion == 1)?TBL_RESPUESTAS_IND_V_T:TBL_RESPUESTAS_INTEGRANTE_T, cv, "_id = ?", new String[]{_id});

                            break;
                        default:
                            //Log.e("Mensaje Code", response.code()+" : "+response.message());
                            //Toast.makeText(ctx, "No se logró enviar codigo: " +response.code(), Toast.LENGTH_SHORT).show();
                            break;
                    }
                    if (DGshow)
                        loading.dismiss();
                }

                @Override
                public void onFailure(Call<MRespuestaGestion> call, Throwable t) {
                    Log.e("FailSaveGestion", t.getMessage());
                    if (DGshow)
                        loading.dismiss();
                }
            });
        }
    }

    public class DescargarFotoFachada extends AsyncTask<Object, Void, String> {

        //ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(Object... params) {

            String nombre_imagen = (String) params[0];
            Context ctx = (Context) params[1];
            String _id = (String) params[2];

            DBhelper dBhelper = new DBhelper(ctx);
            SessionManager session = new SessionManager(ctx);
            SQLiteDatabase db = dBhelper.getWritableDatabase();

            ContentValues cv = new ContentValues();

            try {
                cv.put("fachada", Miscellaneous.save(
                        Miscellaneous.descargarImagen(
                                 session.getDominio().get(0)+session.getDominio().get(1)+ WebServicesRoutes.CONTROLLER_FICHAS +
                                        WebServicesRoutes.IMAGES_GEOLOCALIZACION +
                                        nombre_imagen.replace("\"","")), 1));

                db.update(TBL_GEO_RESPUESTAS_T, cv, "_id = ?", new String[]{_id});
            } catch (IOException e) {
                e.printStackTrace();
            }


            return "termina";
        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);

        }

    }

    public void SendOriginacionInd (Context ctx, boolean flag){
        final AlertDialog loading = Popups.showLoadingDialog(ctx, R.string.please_wait, R.string.loading_info);

        if (flag)
            loading.show();
        SessionManager session = new SessionManager(ctx);
        final DBhelper dBhelper = new DBhelper(ctx);
        final SQLiteDatabase db = dBhelper.getWritableDatabase();

        Cursor row;
        if (ENVIROMENT)
            row = dBhelper.getRecords(Constants.SOLICITUDES_T, " WHERE tipo_solicitud = 1 AND estatus = 0", "", null);
        else
            row = dBhelper.getRecords(Constants.SOLICITUDES_T, " WHERE tipo_solicitud = 1 AND estatus = 0", "", null);

        if (row.getCount() > 0){
            row.moveToNext();

            Log.e("count solicitudes", row.getCount()+" total");
            for (int i = 0; i < row.getCount(); i++){
                Cursor row_soli = dBhelper.getOriginacionInd(row.getString(0), true);
                if (row_soli.getCount() > 0) {
                    Log.e("count solicitud", row_soli.getCount() + " total");
                    row_soli.moveToFirst();
                    JSONObject json_solicitud = new JSONObject();
                    try {
                        json_solicitud.put(Constants.K_PLAZO, Miscellaneous.GetPlazo(row_soli.getString(2)));
                        json_solicitud.put(Constants.K_PERIODICIDAD, Miscellaneous.GetPeriodicidad(row_soli.getString(3)));
                        json_solicitud.put(Constants.K_FECHA_DESEMBOLSO, row_soli.getString(4));
                        json_solicitud.put(Constants.K_HORA_VISITA, row_soli.getString(6));
                        json_solicitud.put(Constants.K_MONTO_PRESTAMO, row_soli.getInt(7));
                        json_solicitud.put(Constants.K_DESTINO_PRESTAMO, row_soli.getString(8));

                        JSONObject json_solicitante = new JSONObject();
                        json_solicitante.put(Constants.K_NOMBRE, row_soli.getString(12));
                        json_solicitante.put(Constants.K_PATERNO, row_soli.getString(13));
                        json_solicitante.put(Constants.K_MATERNO, row_soli.getString(14));
                        json_solicitante.put(Constants.K_FECHA_NACIMIENTO, row_soli.getString(15));
                        json_solicitante.put(Constants.K_GENERO, row_soli.getInt(17));
                        json_solicitante.put(Constants.K_ESTADO_NACIMIENTO, row_soli.getString(18));
                        json_solicitante.put(Constants.K_RFC, row_soli.getString(19));
                        json_solicitante.put(Constants.K_CURP, row_soli.getString(21) + row_soli.getString(22));
                        json_solicitante.put(Constants.K_OCUPACION, row_soli.getString(23));
                        json_solicitante.put(Constants.K_ACTIVIDAD_ECONOMICA, row_soli.getString(24));
                        json_solicitante.put(Constants.K_IDENTIFICACION_TIPO, row_soli.getString(25));
                        json_solicitante.put(Constants.K_NO_IDENTIFICACION, row_soli.getString(26));
                        json_solicitante.put(Constants.K_NIVEL_ESTUDIO, row_soli.getString(27));
                        json_solicitante.put(Constants.K_ESTADO_CIVIL, row_soli.getString(28));
                        if (row_soli.getString(28).equals("CASADA(O)"))
                            json_solicitante.put(Constants.K_BIENES, (row_soli.getInt(29) == 1)?"MANCOMUNADOS":"SEPARADOS");
                        json_solicitante.put(Constants.K_TIPO_VIVIENDA, row_soli.getString(30));
                        if (row_soli.getString(30).equals("CASA FAMILIAR"))
                            json_solicitante.put(Constants.K_PARENTESCO, row_soli.getString(31));
                        else if (row_soli.getString(30).equals("OTRO"))
                            json_solicitante.put(Constants.K_OTRO_TIPO_VIVIENDA, row_soli.getString(32));
                        json_solicitante.put(Constants.K_LATITUD, row_soli.getString(33));
                        json_solicitante.put(Constants.K_LONGITUD, row_soli.getString(34));
                        json_solicitante.put(Constants.K_CALLE, row_soli.getString(35));
                        json_solicitante.put(Constants.K_NO_EXTERIOR,row_soli.getString(36));
                        json_solicitante.put(Constants.K_NO_INTERIOR, row_soli.getString(37));
                        json_solicitante.put(Constants.K_NO_MANZANA, row_soli.getString(38));
                        json_solicitante.put(Constants.K_NO_LOTE, row_soli.getString(39));
                        json_solicitante.put(Constants.K_CODIGO_POSTAL, row_soli.getInt(40));
                        Cursor row_direccion = dBhelper.getDireccionByCP(row_soli.getString(40));
                        row_direccion.moveToFirst();
                        json_solicitante.put(Constants.K_COLONIA, row_soli.getString(41));
                        json_solicitante.put(Constants.K_MUNICIPIO,row_direccion.getString(4));
                        json_solicitante.put(Constants.K_ESTADO, row_direccion.getString(1));
                        row_direccion.close();
                        json_solicitante.put(Constants.K_TEL_CASA, row_soli.getString(42));
                        json_solicitante.put(Constants.K_TEL_CELULAR, row_soli.getString(43));
                        json_solicitante.put(Constants.K_TEL_MENSAJE, row_soli.getString(44));
                        json_solicitante.put(Constants.K_TEL_TRABAJO, row_soli.getString(45));
                        json_solicitante.put(Constants.K_TIEMPO_VIVIR_SITIO, row_soli.getInt(46));
                        json_solicitante.put(Constants.K_DEPENDIENTES_ECONOMICO, row_soli.getInt(47));
                        json_solicitante.put(Constants.K_MEDIO_CONTACTO, row_soli.getString(48));
                        json_solicitante.put(Constants.K_EMAIL, row_soli.getString(49));
                        json_solicitante.put(Constants.K_FOTO_FACHADA, row_soli.getString(50));
                        json_solicitante.put(Constants.K_REFERENCIA_DOMICILIARIA, row_soli.getString(51));
                        json_solicitante.put(Constants.K_FIRMA, row_soli.getString(52));

                        MultipartBody.Part fachada_cliente = null;
                        File image_fachada_cliente = new File(Constants.ROOT_PATH + "Fachada/"+row_soli.getString(50));
                        if (image_fachada_cliente != null) {
                            RequestBody imageBodyFachadaCli =
                                    RequestBody.create(
                                            MediaType.parse("image/*"), image_fachada_cliente);

                            fachada_cliente = MultipartBody.Part.createFormData("fachada_cliente", image_fachada_cliente.getName(), imageBodyFachadaCli);
                        }

                        MultipartBody.Part firma_cliente = null;
                        File image_firma_cliente = new File(Constants.ROOT_PATH + "Firma/"+row_soli.getString(52));
                        if (image_fachada_cliente != null) {
                            RequestBody imageBodyFirmaCli =
                                    RequestBody.create(
                                            MediaType.parse("image/*"), image_firma_cliente);

                            firma_cliente = MultipartBody.Part.createFormData("firma_cliente", image_firma_cliente.getName(), imageBodyFirmaCli);
                        }

                        json_solicitud.put(Constants.K_SOLICITANTE, json_solicitante);
                        if (row_soli.getString(28).equals("CASADA(O)") ||
                                row_soli.getString(28).equals("UNIÓN LIBRE")) {
                            JSONObject json_conyuge = new JSONObject();
                            json_conyuge.put(Constants.K_NOMBRE, row_soli.getString(58));
                            json_conyuge.put(Constants.K_PATERNO, row_soli.getString(59));
                            json_conyuge.put(Constants.K_MATERNO, row_soli.getString(60));
                            json_conyuge.put(Constants.K_OCUPACION, row_soli.getString(61));
                            json_conyuge.put(Constants.K_TEL_CELULAR, row_soli.getString(62));

                            json_solicitud.put(Constants.K_SOLICITANTE_CONYUGE, json_conyuge);
                        }

                        if (row_soli.getInt(7) >= 30000){
                            JSONObject json_economicos = new JSONObject();
                            json_economicos.put(Constants.K_PROPIEDADES, row_soli.getString(66));
                            json_economicos.put(Constants.K_VALOR_APROXIMADO, row_soli.getString(67));
                            json_economicos.put(Constants.K_UBICACION, row_soli.getString(68));
                            json_economicos.put(Constants.K_INGRESO, row_soli.getString(69));

                            json_solicitud.put(Constants.K_SOLICITANTE_DATOS_ECONOMICOS, json_economicos);
                        }

                        JSONObject json_negocio = new JSONObject();
                        json_negocio.put(Constants.K_NOMBRE, row_soli.getString(73));
                        json_negocio.put(Constants.K_LATITUD, row_soli.getString(74));
                        json_negocio.put(Constants.K_LONGITUD, row_soli.getString(75));
                        json_negocio.put(Constants.K_CALLE, row_soli.getString(76));
                        json_negocio.put(Constants.K_NO_INTERIOR, row_soli.getString(77));
                        json_negocio.put(Constants.K_NO_INTERIOR, row_soli.getString(78));
                        json_negocio.put(Constants.K_NO_MANZANA, row_soli.getString(79));
                        json_negocio.put(Constants.K_NO_LOTE, row_soli.getString(80));
                        json_negocio.put(Constants.K_CODIGO_POSTAL, row_soli.getInt(81));
                        json_negocio.put(Constants.K_COLONIA, row_soli.getString(82));
                        Cursor row_address_neg = dBhelper.getDireccionByCP(row_soli.getString(81));
                        row_address_neg.moveToFirst();
                        json_negocio.put(Constants.K_MUNICIPIO,row_address_neg.getString(4));
                        row_address_neg.close();
                        json_negocio.put(Constants.K_ACTIVIDAD_ECONOMICA, row_soli.getString(83));
                        json_negocio.put(Constants.K_ANTIGUEDAD, row_soli.getString(84));
                        json_negocio.put(Constants.K_INGRESO_MENSUAL, row_soli.getString(85));
                        json_negocio.put(Constants.K_INGRESOS_OTROS, row_soli.getString(86));
                        json_negocio.put(Constants.K_GASTO_SEMANAL, row_soli.getString(87));
                        json_negocio.put(Constants.K_GASTO_AGUA, row_soli.getString(88));
                        json_negocio.put(Constants.K_GASTO_LUZ, row_soli.getString(89));
                        json_negocio.put(Constants.K_GASTO_TELEFONO, row_soli.getString(90));
                        json_negocio.put(Constants.K_GASTO_RENTA, row_soli.getString(91));
                        json_negocio.put(Constants.K_GASTO_OTROS, row_soli.getString(92));
                        json_negocio.put(Constants.K_CAPACIDAD_PAGO, row_soli.getString(93));
                        json_negocio.put(Constants.K_DIAS_VENTA, row_soli.getString(94));
                        json_negocio.put(Constants.K_FOTO_FACHADA, row_soli.getString(95));
                        json_negocio.put(Constants.K_REFERENCIA_NEGOCIO, row_soli.getString(96));

                        MultipartBody.Part fachada_negocio = null;
                        File image_fachada_negocio = new File(Constants.ROOT_PATH + "Fachada/"+row_soli.getString(95));
                        if (image_fachada_negocio != null) {
                            RequestBody imageBodyFachadaNeg =
                                    RequestBody.create(
                                            MediaType.parse("image/*"), image_fachada_negocio);

                            fachada_negocio = MultipartBody.Part.createFormData("fachada_negocio", image_fachada_negocio.getName(), imageBodyFachadaNeg);
                        }

                        json_solicitud.put(Constants.K_SOLICITANTE_NEGOCIO, json_negocio);

                        JSONObject json_aval = new JSONObject();
                        json_aval.put(Constants.K_NOMBRE, row_soli.getString(100));
                        json_aval.put(Constants.K_PATERNO, row_soli.getString(101));
                        json_aval.put(Constants.K_MATERNO, row_soli.getString(102));
                        json_aval.put(Constants.K_FECHA_NACIMIENTO, row_soli.getString(103));
                        json_aval.put(Constants.K_GENERO, row_soli.getInt(105));
                        json_aval.put(Constants.K_ESTADO_NACIMIENTO, row_soli.getString(106));
                        json_aval.put(Constants.K_RFC, row_soli.getString(107));
                        json_aval.put(Constants.K_CURP, row_soli.getString(109)+row_soli.getString(110));
                        json_aval.put(Constants.K_IDENTIFICACION_TIPO, row_soli.getString(111));
                        json_aval.put(Constants.K_NO_IDENTIFICACION, row_soli.getString(112));
                        json_aval.put(Constants.K_OCUPACION, row_soli.getString(113));
                        json_aval.put(Constants.K_ACTIVIDAD_ECONOMICA, row_soli.getString(114));
                        json_aval.put(Constants.K_LATITUD, row_soli.getString(115));
                        json_aval.put(Constants.K_LONGITUD, row_soli.getString(116));
                        json_aval.put(Constants.K_CALLE, row_soli.getString(117));
                        json_aval.put(Constants.K_NO_EXTERIOR, row_soli.getString(118));
                        json_aval.put(Constants.K_NO_INTERIOR, row_soli.getString(119));
                        json_aval.put(Constants.K_NO_MANZANA, row_soli.getString(120));
                        json_aval.put(Constants.K_NO_LOTE, row_soli.getString(121));
                        json_aval.put(Constants.K_CODIGO_POSTAL, row_soli.getInt(122));
                        json_aval.put(Constants.K_COLONIA, row_soli.getString(123));
                        Cursor row_address_aval = dBhelper.getDireccionByCP(row_soli.getString(122));
                        row_address_aval.moveToFirst();
                        json_aval.put(Constants.K_MUNICIPIO,row_address_aval.getString(4));
                        json_aval.put(Constants.K_ESTADO, row_address_aval.getString(1));
                        row_address_aval.close();
                        json_aval.put(Constants.K_TIPO_VIVIENDA, row_soli.getString(124));
                        if (row_soli.getString(124).equals("CASA FAMILIAR")){
                            json_aval.put(Constants.K_NOMBRE_TITULAR, row_soli.getString(125));
                            json_aval.put(Constants.K_PARENTESCO, row_soli.getString(126));
                        }
                        json_aval.put(Constants.K_INGRESO_MENSUAL, row_soli.getDouble(127));
                        json_aval.put(Constants.K_INGRESOS_OTROS, row_soli.getDouble(128));
                        json_aval.put(Constants.K_GASTO_SEMANAL, row_soli.getDouble(129));
                        json_aval.put(Constants.K_GASTO_AGUA, row_soli.getDouble(130));
                        json_aval.put(Constants.K_GASTO_LUZ, row_soli.getDouble(131));
                        json_aval.put(Constants.K_GASTO_TELEFONO, row_soli.getDouble(132));
                        json_aval.put(Constants.K_GASTO_RENTA, row_soli.getDouble(133));
                        json_aval.put(Constants.K_GASTO_OTROS, row_soli.getDouble(134));
                        json_aval.put(Constants.K_HORA_LOCALIZACION, row_soli.getString(135));
                        json_aval.put(Constants.K_ANTIGUEDAD, row_soli.getInt(136));
                        json_aval.put(Constants.K_TEL_CASA, row_soli.getString(137));
                        json_aval.put(Constants.K_TEL_CELULAR, row_soli.getString(138));
                        json_aval.put(Constants.K_FOTO_FACHADA, row_soli.getString(139));
                        json_aval.put(Constants.K_REFERENCIA_DOMICILIARIA, row_soli.getString(140));
                        json_aval.put(Constants.K_FIRMA, row_soli.getString(141));

                        MultipartBody.Part fachada_aval = null;
                        File image_fachada_aval = new File(Constants.ROOT_PATH + "Fachada/"+row_soli.getString(139));
                        if (image_fachada_aval != null) {
                            RequestBody imageBodyFachadaAval =
                                    RequestBody.create(
                                            MediaType.parse("image/*"), image_fachada_aval);

                            fachada_aval = MultipartBody.Part.createFormData("fachada_aval", image_fachada_aval.getName(), imageBodyFachadaAval);
                        }

                        MultipartBody.Part firma_aval = null;
                        File image_firma_aval = new File(Constants.ROOT_PATH + "Firma/"+row_soli.getString(141));
                        if (image_fachada_aval != null) {
                            RequestBody imageBodyFirmaAval =
                                    RequestBody.create(
                                            MediaType.parse("image/*"), image_firma_aval);

                            firma_aval = MultipartBody.Part.createFormData("firma_aval", image_firma_aval.getName(), imageBodyFirmaAval);
                        }

                        json_solicitud.put(Constants.K_SOLICITANTE_AVAL, json_aval);

                        JSONObject json_referencia = new JSONObject();
                        json_referencia.put(Constants.K_NOMBRE, row_soli.getString(147));
                        json_referencia.put(Constants.K_PATERNO, row_soli.getString(148));
                        json_referencia.put(Constants.K_MATERNO, row_soli.getString(149));
                        json_referencia.put(Constants.K_CALLE, row_soli.getString(150));
                        json_referencia.put(Constants.K_CODIGO_POSTAL, row_soli.getInt(151));
                        json_referencia.put(Constants.K_COLONIA, row_soli.getString(152));
                        json_referencia.put(Constants.K_MUNICIPIO, row_soli.getString(153));
                        json_referencia.put(Constants.K_TEL_CELULAR, row_soli.getString(154));

                        json_solicitud.put(Constants.K_SOLICITANTE_REFERENCIA, json_referencia);

                        MultipartBody.Part foto_ine_frontal = null;
                        File image_ine_frontal = new File(Constants.ROOT_PATH + "Documentos/"+row_soli.getString(158));
                        if (image_ine_frontal != null) {
                            RequestBody imageBody =
                                    RequestBody.create(
                                            MediaType.parse("image/*"), image_ine_frontal);

                            foto_ine_frontal = MultipartBody.Part.createFormData("identificacion_frontal", image_ine_frontal.getName(), imageBody);
                        }

                        MultipartBody.Part foto_ine_reverso = null;
                        File image_ine_reverso = new File(Constants.ROOT_PATH + "Documentos/"+row_soli.getString(159));
                        if (image_ine_reverso != null) {
                            RequestBody imageBody =
                                    RequestBody.create(
                                            MediaType.parse("image/*"), image_ine_reverso);

                            foto_ine_reverso = MultipartBody.Part.createFormData("identificacion_reverso", image_ine_reverso.getName(), imageBody);
                        }

                        MultipartBody.Part foto_curp = null;
                        File image_curp = new File(Constants.ROOT_PATH + "Documentos/"+row_soli.getString(160));
                        if (image_curp != null) {
                            RequestBody imageBody =
                                    RequestBody.create(
                                            MediaType.parse("image/*"), image_curp);

                            foto_curp = MultipartBody.Part.createFormData("curp", image_curp.getName(), imageBody);
                        }

                        MultipartBody.Part foto_comprobante = null;
                        File image_comprobante = new File(Constants.ROOT_PATH + "Documentos/"+row_soli.getString(161));
                        if (image_comprobante != null) {
                            RequestBody imageBody =
                                    RequestBody.create(
                                            MediaType.parse("image/*"), image_comprobante);

                            foto_comprobante = MultipartBody.Part.createFormData("comprobante_domicilio", image_comprobante.getName(), imageBody);
                        }

                        MultipartBody.Part firma_asesor = null;
                        File image_firma_asesor = new File(Constants.ROOT_PATH + "Firma/"+row_soli.getString(163));
                        if (image_firma_asesor != null) {
                            RequestBody imageBody =
                                    RequestBody.create(
                                            MediaType.parse("image/*"), image_firma_asesor);

                            firma_asesor = MultipartBody.Part.createFormData("firma_asesor", image_firma_asesor.getName(), imageBody);
                        }

                        RequestBody solicitudBody = RequestBody.create(MultipartBody.FORM, json_solicitud.toString());


                        Log.e("solicitud", json_solicitud.toString());
                        ManagerInterface api = new RetrofitClient().generalRF(Constants.CONTROLLER_SOLICITUDES, ctx).create(ManagerInterface.class);

                        Call<MResSaveOriginacionInd> call = api.guardarOriginacionInd("Bearer "+ session.getUser().get(7),
                                solicitudBody,
                                fachada_cliente,
                                firma_cliente,
                                fachada_negocio,
                                fachada_aval,
                                firma_aval,
                                foto_ine_frontal,
                                foto_ine_reverso,
                                foto_curp,
                                foto_comprobante,
                                firma_asesor);

                        call.enqueue(new Callback<MResSaveOriginacionInd>() {
                            @Override
                            public void onResponse(Call<MResSaveOriginacionInd> call, Response<MResSaveOriginacionInd> response) {
                                Log.e("Respuesta code", response.code()+" codigo");
                                Log.e("Respuesta mensaje", response.body().toString());
                                switch (response.code()){
                                    case 200:
                                        Log.e("respuesta", response.body().toString());
                                        break;
                                }
                                loading.dismiss();
                            }

                            @Override
                            public void onFailure(Call<MResSaveOriginacionInd> call, Throwable t) {
                                loading.dismiss();
                                Log.e("Error", "fail");

                            }
                        });


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                row.moveToNext();
            }
        }

    }

    //Obtiene los datos de un prestamo en especifico
    public void GetPrestamo(final Context ctx, final int id_cartera, int tipo_prestamo){
        final AlertDialog loading = Popups.showLoadingDialog(ctx, R.string.please_wait, R.string.loading_info);
        loading.show();

        SessionManager session = new SessionManager(ctx);
        ManagerInterface api = new RetrofitClient().generalRF(Constants.CONTROLLER_MOVIL, ctx).create(ManagerInterface.class);

        if(tipo_prestamo == 1){ //Obtiene prestamos individuales
            Log.e("IdCarteraInd", id_cartera+"ads");
            Call<List<MPrestamoRes>> call = api.getPrestamosInd(id_cartera,"Bearer "+ session.getUser().get(7));
            call.enqueue(new Callback<List<MPrestamoRes>>() {
                @Override
                public void onResponse(Call<List<MPrestamoRes>> call, Response<List<MPrestamoRes>> response) {
                    Log.e("ind","id_carteta: "+id_cartera+ " code"+response.code());
                    if (response.code() == 200) {
                        List<MPrestamoRes> prestamos = response.body();
                        if (prestamos.size() > 0){
                            DBhelper dBhelper = new DBhelper(ctx);
                            SQLiteDatabase db = dBhelper.getWritableDatabase();
                            Cursor row;

                            Log.e("size antes for", ""+prestamos.size());
                            for (int i = 0; i < prestamos.size(); i++){
                                Log.e("Count i", ""+i);
                                Log.e("id_prestamo", ""+prestamos.get(i).getId());
                                String where = " WHERE id_prestamo = ?";
                                String order = "";
                                String[] args =  new String[] {String.valueOf(prestamos.get(i).getId())};

                                if (ENVIROMENT)
                                    row = dBhelper.getRecords(TBL_PRESTAMOS_IND, where, order, args);
                                else
                                    row = dBhelper.getRecords(TBL_PRESTAMOS_IND_T, where, order, args);

                                if (row.getCount() == 0){ //Registra el prestamo de ind
                                    Log.e("Prestamo", "Registra Prestamo");
                                    HashMap<Integer, String> values = new HashMap<>();
                                    values.put(0, String.valueOf(prestamos.get(i).getId()));                    //ID PRESTAMO
                                    values.put(1, String.valueOf(prestamos.get(i).getClienteId()));             //CLIENTE ID
                                    values.put(2, prestamos.get(i).getNumPrestamo());                           //NUM_PRESTAMO
                                    values.put(3, String.valueOf(prestamos.get(i).getNumSolicitud()));          //NUM_SOLICITUD
                                    values.put(4, prestamos.get(i).getFechaEntrega());                          //FECHA_ENTREGA
                                    values.put(5, String.valueOf(prestamos.get(i).getMontoOtorgado()));         //MONTO OTORGADO
                                    values.put(6, String.valueOf(prestamos.get(i).getMontoTotal()));            //MONTO TOTAL
                                    values.put(7, String.valueOf(prestamos.get(i).getMontoAmortizacion()));     //MONTO AMORTIZACION
                                    values.put(8, String.valueOf(prestamos.get(i).getMontoRequerido()));        //MONTO REQUERIDO
                                    values.put(9, String.valueOf(prestamos.get(i).getNumAmortizacion()));       //NUM AMORTIZACION
                                    values.put(10, prestamos.get(i).getFechaEstablecida());                     //FECHA ESTABLECIDA
                                    values.put(11, prestamos.get(i).getTipoCartera());                          //TIPO CARTERA
                                    values.put(12, (prestamos.get(i).getPagada())?"1":"0");                     //PAGADA
                                    values.put(13, Miscellaneous.ObtenerFecha(TIMESTAMP));                      //FECHA CREACION
                                    values.put(14, Miscellaneous.ObtenerFecha(TIMESTAMP));                      //FECHA ACTUALIZACION

                                    Log.e("ParamsPres", values.toString());
                                    Log.e("--","-----------------------------------------------------");
                                    if (ENVIROMENT)
                                        dBhelper.savePrestamosInd(db, TBL_PRESTAMOS_IND, values);
                                    else
                                        dBhelper.savePrestamosInd(db, TBL_PRESTAMOS_IND_T, values);

                                    if (prestamos.get(i).getAval() != null) {
                                        MAval mAval = prestamos.get(i).getAval();
                                        HashMap<Integer, String> values_aval = new HashMap<>();
                                        values_aval.put(0, String.valueOf(prestamos.get(i).getId()));            //ID PRESTAMO
                                        values_aval.put(1, String.valueOf(mAval.getId()));                       //AVAL ID
                                        values_aval.put(2, mAval.getNombre());                                   //NOMBRE
                                        values_aval.put(3, mAval.getParentesco());                               //PARENTESCO
                                        values_aval.put(4, mAval.getDireccion());                                //DIRECCIO
                                        values_aval.put(5, mAval.getTelefono());                                 //TELEFONO
                                        values_aval.put(6, Miscellaneous.ObtenerFecha(TIMESTAMP));               //FECHA CREACION
                                        values_aval.put(7, Miscellaneous.ObtenerFecha(TIMESTAMP));               //FECHA ACTUALIZACION

                                        if (ENVIROMENT)
                                            dBhelper.saveAval(db, TBL_AVAL, values_aval);
                                        else
                                            dBhelper.saveAval(db, TBL_AVAL_T, values_aval);

                                    }

                                    if (prestamos.get(i).getAmortizaciones().size() > 0){
                                        for (int j = 0; j < prestamos.get(i).getAmortizaciones().size(); j++){
                                            MAmortizacion mAmortizacion = prestamos.get(i).getAmortizaciones().get(j);
                                            HashMap<Integer, String> values_amortiz = new HashMap<>();
                                            values_amortiz.put(0, String.valueOf(mAmortizacion.getId()));                                   //ID AMORTIZACION
                                            values_amortiz.put(1, String.valueOf(mAmortizacion.getPrestamoId()));                           //ID PRESTAMOS
                                            values_amortiz.put(2, mAmortizacion.getFecha());                                                //FECHA
                                            values_amortiz.put(3, (mAmortizacion.getFechaPago() != null)?mAmortizacion.getFechaPago():"");  //FECHA PAGO
                                            values_amortiz.put(4, String.valueOf(mAmortizacion.getCapital()));                              //CAPITAL
                                            values_amortiz.put(5, String.valueOf(mAmortizacion.getInteres()));                              //INTERES
                                            values_amortiz.put(6, String.valueOf(mAmortizacion.getIva()));                                  //IVA
                                            values_amortiz.put(7, String.valueOf(mAmortizacion.getComision()));                             //COMISION
                                            values_amortiz.put(8, String.valueOf(mAmortizacion.getTotal()));                                //TOTAL
                                            values_amortiz.put(9, String.valueOf(mAmortizacion.getCapitalPagado()));                        //CAPITAL PAGADO
                                            values_amortiz.put(10, String.valueOf(mAmortizacion.getInteresPagado()));                       //INTERES PAGADO
                                            values_amortiz.put(11, String.valueOf(mAmortizacion.getIvaPagado()));                           //IVA PAGADO
                                            values_amortiz.put(12, String.valueOf(mAmortizacion.getInteresMoratorioPagado()));              //INTERES MORATORIO PAGADO
                                            values_amortiz.put(13, String.valueOf(mAmortizacion.getIvaMoratorioPagado()));                  //IVA_MORATORIO PAGADO
                                            values_amortiz.put(14, String.valueOf(mAmortizacion.getComisionPagada()));                      //COMISION PAGADA
                                            values_amortiz.put(15, String.valueOf(mAmortizacion.getTotalPagado()));                         //TOTAL PAGADO
                                            values_amortiz.put(16, String.valueOf(mAmortizacion.getPagado()));                              //PAGADO
                                            values_amortiz.put(17, String.valueOf(mAmortizacion.getNumero()));                              //NUMERO
                                            values_amortiz.put(18, String.valueOf(mAmortizacion.getDiasAtraso()));                          //DIAS ATRASO
                                            values_amortiz.put(19, Miscellaneous.ObtenerFecha(TIMESTAMP));                                  //FECHA DISPOSITIVO
                                            values_amortiz.put(20, Miscellaneous.ObtenerFecha(TIMESTAMP));                                  //FECHA ACTUALIZADO

                                            if (ENVIROMENT)
                                                dBhelper.saveAmortizaciones(db, TBL_AMORTIZACIONES, values_amortiz);
                                            else
                                                dBhelper.saveAmortizaciones(db, TBL_AMORTIZACIONES_T, values_amortiz);
                                        }
                                    }

                                    if (prestamos.get(i).getPagos() != null && prestamos.get(i).getPagos().size() > 0){
                                        for (int k = 0; k < prestamos.get(i).getPagos().size(); k++){
                                            MPago mPago = prestamos.get(i).getPagos().get(k);
                                            HashMap<Integer, String> values_pago = new HashMap<>();
                                            values_pago.put(0, String.valueOf(prestamos.get(i).getId()));            //ID PRESTAMO
                                            values_pago.put(1, mPago.getFecha());                                    //FECHA
                                            values_pago.put(2, String.valueOf(mPago.getMonto()));                    //MONTO
                                            values_pago.put(3,mPago.getBanco());                                     //BANCO
                                            values_pago.put(4, Miscellaneous.ObtenerFecha(TIMESTAMP));               //FECHA DISPOSITIVO
                                            values_pago.put(5, Miscellaneous.ObtenerFecha(TIMESTAMP));               //FECHA ACTUALIZADO

                                            if (ENVIROMENT)
                                                dBhelper.savePagos(db, TBL_PAGOS, values_pago);
                                            else
                                                dBhelper.savePagos(db, TBL_PAGOS_T, values_pago);
                                        }
                                    }
                                }
                                else{ //Actualiza la cartera de ind
                                    row.moveToFirst();
                                    ContentValues cv = new ContentValues();
                                    cv.put("fecha_entrega", prestamos.get(i).getFechaEntrega());
                                    cv.put("monto_otorgado", String.valueOf(prestamos.get(i).getMontoOtorgado()));
                                    cv.put("monto_total", String.valueOf(prestamos.get(i).getMontoTotal()));
                                    cv.put("monto_amortizacion", String.valueOf(prestamos.get(i).getMontoAmortizacion()));
                                    cv.put("monto_requerido", String.valueOf(prestamos.get(i).getMontoRequerido()));
                                    cv.put("num_amortizacion", String.valueOf(prestamos.get(i).getNumAmortizacion()));
                                    cv.put("fecha_establecida", prestamos.get(i).getFechaEstablecida());
                                    cv.put("tipo_cartera", prestamos.get(i).getTipoCartera());
                                    cv.put("pagada", (prestamos.get(i).getPagada())?"1":"0");
                                    cv.put("fecha_actualizado", Miscellaneous.ObtenerFecha("timestamp"));

                                    db.update((ENVIROMENT)?TBL_PRESTAMOS_IND:TBL_PRESTAMOS_IND_T, cv,
                                            "_id = ?", new String[]{row.getString(0)});
                                    if (prestamos.get(i).getAval() != null) {

                                        MAval mAval = prestamos.get(i).getAval();
                                        ContentValues cv_aval = new ContentValues();
                                        cv_aval.put("id_aval", String.valueOf(mAval.getId()));                      //AVAL ID
                                        cv_aval.put("nombre", mAval.getNombre());                                   //NOMBRE
                                        cv_aval.put("parentesco", mAval.getParentesco());                           //PARENTESCO
                                        cv_aval.put("direccion", mAval.getDireccion());                             //DIRECCIO
                                        cv_aval.put("telefono", mAval.getTelefono());                               //TELEFONO
                                        cv_aval.put("fecha_actualizado", Miscellaneous.ObtenerFecha(TIMESTAMP));    //FECHA ACTUALIZACION

                                        db.update((ENVIROMENT)?TBL_AVAL:TBL_AVAL_T, cv_aval,
                                                "id_prestamo = ?", new String[]{String.valueOf(prestamos.get(i).getId())});
                                    }

                                    if (prestamos.get(i).getAmortizaciones().size() > 0){
                                        for (int j = 0; j < prestamos.get(i).getAmortizaciones().size(); j++){
                                            MAmortizacion mAmortizacion = prestamos.get(i).getAmortizaciones().get(j);
                                            ContentValues cv_amortiz = new ContentValues();
                                            cv_amortiz.put("fecha", mAmortizacion.getFecha());                                                //FECHA
                                            cv_amortiz.put("fecha_pago", (mAmortizacion.getFechaPago() != null)?mAmortizacion.getFechaPago():"");  //FECHA PAGO
                                            cv_amortiz.put("capital", String.valueOf(mAmortizacion.getCapital()));                              //CAPITAL
                                            cv_amortiz.put("interes", String.valueOf(mAmortizacion.getInteres()));                              //INTERES
                                            cv_amortiz.put("iva", String.valueOf(mAmortizacion.getIva()));                                  //IVA
                                            cv_amortiz.put("comision", String.valueOf(mAmortizacion.getComision()));                             //COMISION
                                            cv_amortiz.put("total", String.valueOf(mAmortizacion.getTotal()));                                //TOTAL
                                            cv_amortiz.put("capital_pagado", String.valueOf(mAmortizacion.getCapitalPagado()));                        //CAPITAL PAGADO
                                            cv_amortiz.put("interes_pagado", String.valueOf(mAmortizacion.getInteresPagado()));                       //INTERES PAGADO
                                            cv_amortiz.put("iva_pagado", String.valueOf(mAmortizacion.getIvaPagado()));                           //IVA PAGADO
                                            cv_amortiz.put("interes_moratorio_pagado", String.valueOf(mAmortizacion.getInteresMoratorioPagado()));              //INTERES MORATORIO PAGADO
                                            cv_amortiz.put("iva_moratorio_pagado", String.valueOf(mAmortizacion.getIvaMoratorioPagado()));                  //IVA_MORATORIO PAGADO
                                            cv_amortiz.put("comision_pagada", String.valueOf(mAmortizacion.getComisionPagada()));                      //COMISION PAGADA
                                            cv_amortiz.put("total_pagado", String.valueOf(mAmortizacion.getTotalPagado()));                         //TOTAL PAGADO
                                            cv_amortiz.put("pagado", String.valueOf(mAmortizacion.getPagado()));                              //PAGADO
                                            cv_amortiz.put("numero", String.valueOf(mAmortizacion.getNumero()));                              //NUMERO
                                            cv_amortiz.put("dias_atraso", String.valueOf(mAmortizacion.getDiasAtraso()));                          //DIAS ATRASO
                                            cv_amortiz.put("fecha_actualizado", Miscellaneous.ObtenerFecha(TIMESTAMP)); //FECHA ACTUALIZADO

                                            db.update((ENVIROMENT)?TBL_AMORTIZACIONES:TBL_AMORTIZACIONES_T, cv_amortiz,
                                                    "id_amortizacion = ? AND id_prestamo = ?", new String[]{String.valueOf(mAmortizacion.getId()), String.valueOf(prestamos.get(i).getId())});
                                        }
                                    }

                                    if (prestamos.get(i).getPagos() != null &&prestamos.get(i).getPagos().size() > 0){
                                        for (int k = 0; k < prestamos.get(i).getPagos().size(); k++){
                                            MPago mPago = prestamos.get(i).getPagos().get(k);
                                            Cursor row_pago = dBhelper.getRecords((ENVIROMENT)?TBL_PAGOS:TBL_PAGOS_T, " WHERE id_prestamo = ? AND fecha = ? AND monto = ? AND banco = ?", "",
                                                    new String[]{String.valueOf(prestamos.get(i).getId()),mPago.getFecha(), String.valueOf(mPago.getMonto()), mPago.getBanco(),});
                                            if (row_pago.getCount() == 0){
                                                HashMap<Integer, String> cv_pago = new HashMap<>();
                                                cv_pago.put(0, String.valueOf(prestamos.get(i).getId()));            //ID PRESTAMO
                                                cv_pago.put(1, mPago.getFecha());                                    //FECHA
                                                cv_pago.put(2, String.valueOf(mPago.getMonto()));                    //MONTO
                                                cv_pago.put(3,mPago.getBanco());                                     //BANCO
                                                cv_pago.put(4, Miscellaneous.ObtenerFecha(TIMESTAMP));               //FECHA DISPOSITIVO
                                                cv_pago.put(5, Miscellaneous.ObtenerFecha(TIMESTAMP));               //FECHA ACTUALIZADO

                                                dBhelper.savePagos(db, (ENVIROMENT)?TBL_PAGOS:TBL_PAGOS_T, cv_pago);
                                            }
                                            row_pago.close();
                                        }
                                    }
                                }
                            } //Fin Ciclo For
                        }//Fin IF
                    }

                    loading.dismiss();
                }

                @Override
                public void onFailure(Call<List<MPrestamoRes>> call, Throwable t) {
                    loading.dismiss();
                }
            });

        }
        else if (tipo_prestamo == 2){ //Obtiene prestamos grupales
            Log.e("IDcartera",id_cartera+"asd");
            Call<List<MPrestamoGpoRes>> call = api.getPrestamosGpo(id_cartera,"Bearer "+ session.getUser().get(7));
            call.enqueue(new Callback<List<MPrestamoGpoRes>>() {
                @Override
                public void onResponse(Call<List<MPrestamoGpoRes>> call, Response<List<MPrestamoGpoRes>> response) {
                    Log.e("gpo","id_carteta: "+id_cartera+ " code"+response.code());
                    if (response.code() == 200){
                        List<MPrestamoGpoRes> prestamos = response.body();
                        if (prestamos.size() > 0){
                            DBhelper dBhelper = new DBhelper(ctx);
                            SQLiteDatabase db = dBhelper.getWritableDatabase();
                            Cursor row;

                            Log.e("size antes for", ""+prestamos.size());
                            for (int i = 0; i < prestamos.size(); i++){
                                Log.e("Count i", ""+i);
                                Log.e("id_prestamo", ""+prestamos.get(i).getId());
                                String where = " WHERE id_prestamo = ?";
                                String order = "";
                                String[] args =  new String[] {String.valueOf(prestamos.get(i).getId())};

                                if (ENVIROMENT)
                                    row = dBhelper.getRecords(TBL_PRESTAMOS_GPO, where, order, args);
                                else
                                    row = dBhelper.getRecords(TBL_PRESTAMOS_GPO_T, where, order, args);

                                if (row.getCount() == 0){ //Registra el prestamo de gpo
                                    Log.e("Prestamo", "Registra Prestamo");
                                    HashMap<Integer, String> values = new HashMap<>();
                                    values.put(0, String.valueOf(prestamos.get(i).getId()));                    //ID PRESTAMO
                                    values.put(1, String.valueOf(prestamos.get(i).getGrupoId()));               //GRUPO ID
                                    values.put(2, prestamos.get(i).getNumPrestamo());                           //NUM_PRESTAMO
                                    values.put(3, String.valueOf(prestamos.get(i).getNumSolicitud()));          //NUM_SOLICITUD
                                    values.put(4, prestamos.get(i).getFechaEntrega());                          //FECHA_ENTREGA
                                    values.put(5, String.valueOf(prestamos.get(i).getMontoOtorgado()));         //MONTO OTORGADO
                                    values.put(6, String.valueOf(prestamos.get(i).getMontoTotal()));            //MONTO TOTAL
                                    values.put(7, String.valueOf(prestamos.get(i).getMontoAmortizacion()));     //MONTO AMORTIZACION
                                    values.put(8, String.valueOf(prestamos.get(i).getMontoRequerido()));        //MONTO REQUERIDO
                                    values.put(9, String.valueOf(prestamos.get(i).getNumAmortizacion()));       //NUM AMORTIZACION
                                    values.put(10, prestamos.get(i).getFechaEstablecida());                     //FECHA ESTABLECIDA
                                    values.put(11, prestamos.get(i).getTipoCartera());                          //TIPO CARTERA
                                    values.put(12, (prestamos.get(i).getPagada().equals("PAGADA"))?"1":"0");    //PAGADA
                                    values.put(13, Miscellaneous.ObtenerFecha(TIMESTAMP));                      //FECHA CREACION
                                    values.put(14, Miscellaneous.ObtenerFecha(TIMESTAMP));                      //FECHA ACTUALIZACION

                                    if (ENVIROMENT)
                                        dBhelper.savePrestamosGpo(db, TBL_PRESTAMOS_GPO, values);
                                    else
                                        dBhelper.savePrestamosGpo(db, TBL_PRESTAMOS_GPO_T, values);

                                    if (prestamos.get(i).getIntegrantes() != null) {
                                        for (int l = 0; l < prestamos.get(i).getIntegrantes().size(); l++){
                                            MIntegrante mIntegrante = prestamos.get(i).getIntegrantes().get(l);
                                            HashMap<Integer, String> values_miembro = new HashMap<>();
                                            values_miembro.put(0, String.valueOf(prestamos.get(i).getId()));            //ID PRESTAMO
                                            values_miembro.put(1, String.valueOf(mIntegrante.getId()));                 //INTEGRANTE ID
                                            values_miembro.put(2, String.valueOf(mIntegrante.getNumSolicitud()));       //NUM SOLICITUD
                                            values_miembro.put(3, String.valueOf(mIntegrante.getGrupoId()));            //GRUPO ID
                                            values_miembro.put(4, mIntegrante.getNombre());                             //NOMBRE
                                            values_miembro.put(5, mIntegrante.getDireccion());                          //DIRECCION
                                            values_miembro.put(6, mIntegrante.getTelCasa());                            //TEL CASA
                                            values_miembro.put(7, mIntegrante.getTelCelular());                         //TEL CELULAR
                                            values_miembro.put(8, mIntegrante.getTipo());                               //TIPO
                                            values_miembro.put(9, mIntegrante.getMontoPrestamo());                      //MONTO PRESTAMO
                                            values_miembro.put(10, mIntegrante.getMontoRequerido());                     //MONTO REQUERIDO
                                            values_miembro.put(11, Miscellaneous.ObtenerFecha(TIMESTAMP));               //FECHA CREACION
                                            values_miembro.put(12, Miscellaneous.ObtenerFecha(TIMESTAMP));               //FECHA ACTUALIZACION
                                            values_miembro.put(13, mIntegrante.getClave());                              //CLAVE
                                            values_miembro.put(14, String.valueOf(mIntegrante.getPrestamoId()));         //ID PRESTAMO

                                            dBhelper.saveMiembros(db, values_miembro);

                                        }
                                    }

                                    if (prestamos.get(i).getAmortizaciones().size() > 0){
                                        for (int j = 0; j < prestamos.get(i).getAmortizaciones().size(); j++){
                                            MAmortizacion mAmortizacion = prestamos.get(i).getAmortizaciones().get(j);
                                            HashMap<Integer, String> values_amortiz = new HashMap<>();
                                            values_amortiz.put(0, String.valueOf(mAmortizacion.getId()));                      //ID AMORTIZACION
                                            values_amortiz.put(1, String.valueOf(mAmortizacion.getPrestamoId()));              //ID PRESTAMOS
                                            values_amortiz.put(2, mAmortizacion.getFecha());                                   //FECHA
                                            values_amortiz.put(3, (mAmortizacion.getFechaPago() != null)?mAmortizacion.getFechaPago():"");        //FECHA PAGO
                                            values_amortiz.put(4, String.valueOf(mAmortizacion.getCapital()));                 //CAPITAL
                                            values_amortiz.put(5, String.valueOf(mAmortizacion.getInteres()));                 //INTERES
                                            values_amortiz.put(6, String.valueOf(mAmortizacion.getIva()));                     //IVA
                                            values_amortiz.put(7, String.valueOf(mAmortizacion.getComision()));                //COMISION
                                            values_amortiz.put(8, String.valueOf(mAmortizacion.getTotal()));                   //TOTAL
                                            values_amortiz.put(9, String.valueOf(mAmortizacion.getCapitalPagado()));           //CAPITAL PAGADO
                                            values_amortiz.put(10, String.valueOf(mAmortizacion.getInteresPagado()));          //INTERES PAGADO
                                            values_amortiz.put(11, String.valueOf(mAmortizacion.getIvaPagado()));              //IVA PAGADO
                                            values_amortiz.put(12, String.valueOf(mAmortizacion.getInteresMoratorioPagado())); //INTERES MORATORIO PAGADO
                                            values_amortiz.put(13, String.valueOf(mAmortizacion.getIvaMoratorioPagado()));     //IVA_MORATORIO PAGADO
                                            values_amortiz.put(14, String.valueOf(mAmortizacion.getComisionPagada()));         //COMISION PAGADA
                                            values_amortiz.put(15, String.valueOf(mAmortizacion.getTotalPagado()));            //TOTAL PAGADO
                                            values_amortiz.put(16, String.valueOf(mAmortizacion.getPagado()));                 //PAGADO
                                            values_amortiz.put(17, String.valueOf(mAmortizacion.getNumero()));                 //NUMERO
                                            values_amortiz.put(18, String.valueOf(mAmortizacion.getDiasAtraso()));             //DIAS ATRASO
                                            values_amortiz.put(19, Miscellaneous.ObtenerFecha(TIMESTAMP));                     //FECHA DISPOSITIVO
                                            values_amortiz.put(20, Miscellaneous.ObtenerFecha(TIMESTAMP));                     //FECHA ACTUALIZADO

                                            if (ENVIROMENT)
                                                dBhelper.saveAmortizaciones(db, TBL_AMORTIZACIONES, values_amortiz);
                                            else
                                                dBhelper.saveAmortizaciones(db, TBL_AMORTIZACIONES_T, values_amortiz);
                                        }
                                    }

                                    if (prestamos.get(i).getPagos() != null &&prestamos.get(i).getPagos().size() > 0){
                                        for (int k = 0; k < prestamos.get(i).getPagos().size(); k++){
                                            MPago mPago = prestamos.get(i).getPagos().get(k);
                                            HashMap<Integer, String> values_pago = new HashMap<>();
                                            values_pago.put(0, String.valueOf(prestamos.get(i).getId()));            //ID PRESTAMO
                                            values_pago.put(1, mPago.getFecha());                                    //FECHA
                                            values_pago.put(2, String.valueOf(mPago.getMonto()));                    //MONTO
                                            values_pago.put(3,mPago.getBanco());                                     //BANCO
                                            values_pago.put(4, Miscellaneous.ObtenerFecha(TIMESTAMP));               //FECHA DISPOSITIVO
                                            values_pago.put(5, Miscellaneous.ObtenerFecha(TIMESTAMP));               //FECHA ACTUALIZADO

                                            if (ENVIROMENT)
                                                dBhelper.savePagos(db, TBL_PAGOS, values_pago);
                                            else
                                                dBhelper.savePagos(db, TBL_PAGOS_T, values_pago);
                                        }
                                    }
                                }
                                else{ //Actualiza la prestamo gpo
                                    row.moveToFirst();
                                    ContentValues cv_prestamo = new ContentValues();
                                    cv_prestamo.put("fecha_entrega", prestamos.get(i).getFechaEntrega());                          //FECHA_ENTREGA
                                    cv_prestamo.put("monto_otorgado", String.valueOf(prestamos.get(i).getMontoOtorgado()));         //MONTO OTORGADO
                                    cv_prestamo.put("monto_total", String.valueOf(prestamos.get(i).getMontoTotal()));            //MONTO TOTAL
                                    cv_prestamo.put("monto_amortizacion", String.valueOf(prestamos.get(i).getMontoAmortizacion()));     //MONTO AMORTIZACION
                                    cv_prestamo.put("monto_requerido", String.valueOf(prestamos.get(i).getMontoRequerido()));        //MONTO REQUERIDO
                                    cv_prestamo.put("num_amortizacion", String.valueOf(prestamos.get(i).getNumAmortizacion()));       //NUM AMORTIZACION
                                    cv_prestamo.put("fecha_establecida", prestamos.get(i).getFechaEstablecida());                     //FECHA ESTABLECIDA
                                    cv_prestamo.put("tipo_cartera", prestamos.get(i).getTipoCartera());                          //TIPO CARTERA
                                    cv_prestamo.put("pagada", (prestamos.get(i).getPagada().equals("PAGADA"))?"1":"0");                     //PAGADA
                                    cv_prestamo.put("fecha_actualizado", Miscellaneous.ObtenerFecha(TIMESTAMP));                      //FECHA ACTUALIZACION

                                    db.update((ENVIROMENT)?TBL_PRESTAMOS_GPO:TBL_PRESTAMOS_GPO_T, cv_prestamo,
                                            "_id = ?", new String[]{row.getString(0)});

                                    if (prestamos.get(i).getIntegrantes() != null) {
                                        for (int l = 0; l < prestamos.get(i).getIntegrantes().size(); l++){
                                            MIntegrante mIntegrante = prestamos.get(i).getIntegrantes().get(l);
                                            ContentValues cv_miembro = new ContentValues();
                                            cv_miembro.put("nombre", mIntegrante.getNombre());                                //NOMBRE
                                            cv_miembro.put("direccion", mIntegrante.getDireccion());                          //DIRECCION
                                            cv_miembro.put("tel_casa", mIntegrante.getTelCasa());                             //TEL CASA
                                            cv_miembro.put("tel_celular", mIntegrante.getTelCelular());                       //TEL CELULAR
                                            cv_miembro.put("tipo_integrante", mIntegrante.getTipo());                         //TIPO
                                            cv_miembro.put("monto_prestamo", mIntegrante.getMontoPrestamo());                 //MONTO PRESTAMO
                                            cv_miembro.put("monto_requerido", mIntegrante.getMontoRequerido());               //MONTO REQUERIDO
                                            cv_miembro.put("fecha_actualizado", Miscellaneous.ObtenerFecha(TIMESTAMP));       //FECHA ACTUALIZACION
                                            cv_miembro.put("clave", mIntegrante.getClave());                                  //CLAVE
                                            cv_miembro.put("id_prestamo_integrante", String.valueOf(mIntegrante.getPrestamoId()));       //PRESTAMO ID

                                            db.update((ENVIROMENT)?TBL_MIEMBROS_GPO:TBL_MIEMBROS_GPO_T, cv_miembro,
                                                    "id_prestamo = ? AND id_integrante = ?",
                                                    new String[]{String.valueOf(prestamos.get(i).getId()), String.valueOf(mIntegrante.getId())});
                                        }
                                    }//Termina If de actualizado de integrantes

                                    if (prestamos.get(i).getAmortizaciones().size() > 0){
                                        for (int j = 0; j < prestamos.get(i).getAmortizaciones().size(); j++){
                                            MAmortizacion mAmortizacion = prestamos.get(i).getAmortizaciones().get(j);
                                            ContentValues cv_amortiz = new ContentValues();
                                            cv_amortiz.put("fecha", mAmortizacion.getFecha());                                                //FECHA
                                            cv_amortiz.put("fecha_pago", (mAmortizacion.getFechaPago() != null)?mAmortizacion.getFechaPago():"");  //FECHA PAGO
                                            cv_amortiz.put("capital", String.valueOf(mAmortizacion.getCapital()));                              //CAPITAL
                                            cv_amortiz.put("interes", String.valueOf(mAmortizacion.getInteres()));                              //INTERES
                                            cv_amortiz.put("iva", String.valueOf(mAmortizacion.getIva()));                                  //IVA
                                            cv_amortiz.put("comision", String.valueOf(mAmortizacion.getComision()));                             //COMISION
                                            cv_amortiz.put("total", String.valueOf(mAmortizacion.getTotal()));                                //TOTAL
                                            cv_amortiz.put("capital_pagado", String.valueOf(mAmortizacion.getCapitalPagado()));                        //CAPITAL PAGADO
                                            cv_amortiz.put("interes_pagado", String.valueOf(mAmortizacion.getInteresPagado()));                       //INTERES PAGADO
                                            cv_amortiz.put("iva_pagado", String.valueOf(mAmortizacion.getIvaPagado()));                           //IVA PAGADO
                                            cv_amortiz.put("interes_moratorio_pagado", String.valueOf(mAmortizacion.getInteresMoratorioPagado()));              //INTERES MORATORIO PAGADO
                                            cv_amortiz.put("iva_moratorio_pagado", String.valueOf(mAmortizacion.getIvaMoratorioPagado()));                  //IVA_MORATORIO PAGADO
                                            cv_amortiz.put("comision_pagada", String.valueOf(mAmortizacion.getComisionPagada()));                      //COMISION PAGADA
                                            cv_amortiz.put("total_pagado", String.valueOf(mAmortizacion.getTotalPagado()));                         //TOTAL PAGADO
                                            cv_amortiz.put("pagado", String.valueOf(mAmortizacion.getPagado()));                              //PAGADO
                                            cv_amortiz.put("numero", String.valueOf(mAmortizacion.getNumero()));                              //NUMERO
                                            cv_amortiz.put("dias_atraso", String.valueOf(mAmortizacion.getDiasAtraso()));                          //DIAS ATRASO
                                            cv_amortiz.put("fecha_actualizado", Miscellaneous.ObtenerFecha(TIMESTAMP)); //FECHA ACTUALIZADO

                                            db.update((ENVIROMENT)?TBL_AMORTIZACIONES:TBL_AMORTIZACIONES_T, cv_amortiz,
                                                    "id_amortizacion = ? AND id_prestamo = ?", new String[]{String.valueOf(mAmortizacion.getId()), String.valueOf(prestamos.get(i).getId())});
                                        }
                                    }//Termina If de Actualizado de amortizaciones

                                    if (prestamos.get(i).getPagos() != null &&prestamos.get(i).getPagos().size() > 0){
                                        for (int k = 0; k < prestamos.get(i).getPagos().size(); k++){
                                            MPago mPago = prestamos.get(i).getPagos().get(k);
                                            Log.e("--","................................................");
                                            Log.e("prestamoId", String.valueOf(prestamos.get(i).getId()));
                                            Log.e("fecha", mPago.getFecha());
                                            Log.e("banco", mPago.getBanco());
                                            Log.e("monto", String.valueOf(mPago.getMonto()));
                                            Cursor row_pago = dBhelper.getRecords((ENVIROMENT)?TBL_PAGOS:TBL_PAGOS_T, " WHERE id_prestamo = ? AND fecha = ? AND monto = ? AND banco = ?", "",
                                                    new String[]{String.valueOf(prestamos.get(i).getId()),mPago.getFecha(), String.valueOf(mPago.getMonto()),mPago.getBanco()});
                                            Log.e("RowPago",row.getCount()+"asd");
                                            if (row_pago.getCount() == 0){
                                                Log.e("registra","Pago");
                                                HashMap<Integer, String> cv_pago = new HashMap<>();
                                                cv_pago.put(0, String.valueOf(prestamos.get(i).getId()));            //ID PRESTAMO
                                                cv_pago.put(1, mPago.getFecha());                                    //FECHA
                                                cv_pago.put(2, String.valueOf(mPago.getMonto()));                    //MONTO
                                                cv_pago.put(3,mPago.getBanco());                                     //BANCO
                                                cv_pago.put(4, Miscellaneous.ObtenerFecha(TIMESTAMP));               //FECHA DISPOSITIVO
                                                cv_pago.put(5, Miscellaneous.ObtenerFecha(TIMESTAMP));               //FECHA ACTUALIZADO

                                                dBhelper.savePagos(db, (ENVIROMENT)?TBL_PAGOS:TBL_PAGOS_T, cv_pago);
                                            }
                                            row_pago.close();
                                            Log.e("--","...............................................");
                                        }
                                    }//Termina If de Actualizar pagos

                                }
                            } //Fin Ciclo For
                        }//Fin IF
                    }
                    loading.dismiss();
                }
                @Override
                public void onFailure(Call<List<MPrestamoGpoRes>> call, Throwable t) {
                    loading.dismiss();
                }
            });
        }
    }

    //Enviar la impresiones realizadas
    public void SendImpresionesVi (Context ctx, boolean showDG){
        final AlertDialog loading = Popups.showLoadingDialog(ctx, R.string.please_wait, R.string.loading_info);

        if (showDG)
            loading.show();
        SessionManager session = new SessionManager(ctx);
        final DBhelper dBhelper = new DBhelper(ctx);
        final SQLiteDatabase db = dBhelper.getWritableDatabase();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Cursor row;
        String sql;
        if (ENVIROMENT)
            sql = "SELECT * FROM (SELECT vi.*, pi.tipo_cartera, '1' AS tipo_gestion FROM " + TBL_IMPRESIONES_VIGENTE + " AS vi INNER JOIN " + TBL_PRESTAMOS_IND + " AS pi ON vi.num_prestamo = pi.num_prestamo WHERE vi.num_prestamo LIKE '%-L%' UNION SELECT vi2.*, pg.tipo_cartera, '2' AS tipo_gestion FROM " + TBL_IMPRESIONES_VIGENTE + " AS vi2 INNER JOIN " + TBL_PRESTAMOS_GPO + " AS pg ON vi2.num_prestamo = pg.num_prestamo WHERE vi2.num_prestamo NOT LIKE '%-L%') AS imp WHERE estatus = ?";
        else
            sql = "SELECT * FROM (SELECT vi.*, pi.tipo_cartera, '1' AS tipo_gestion, 'VIGENTE' AS tipo_prestamo FROM " + TBL_IMPRESIONES_VIGENTE_T + " AS vi INNER JOIN " + TBL_PRESTAMOS_IND_T + " AS pi ON vi.num_prestamo = pi.num_prestamo WHERE vi.num_prestamo LIKE '%-L%' UNION SELECT vi2.*, pg.tipo_cartera, '2' AS tipo_gestion, 'VIGENTE' AS tipo_prestamo FROM " + TBL_IMPRESIONES_VIGENTE_T + " AS vi2 INNER JOIN " + TBL_PRESTAMOS_GPO_T + " AS pg ON vi2.num_prestamo = pg.num_prestamo WHERE vi2.num_prestamo NOT LIKE '%-L%' UNION SELECT v.*, pvi.tipo_cartera, '1' AS tipo_gestion, 'VENCIDA' AS tipo_prestamo FROM " + TBL_IMPRESIONES_VENCIDA_T + " AS v INNER JOIN " + TBL_PRESTAMOS_IND_T + " AS pvi ON v.num_prestamo = pvi.num_prestamo WHERE v.num_prestamo LIKE '%-L%' UNION SELECT vg.*, pvg.tipo_cartera, '2' AS tipo_gestion, 'VENCIDA' AS tipo_prestamo FROM " + TBL_IMPRESIONES_VENCIDA_T + " AS vg INNER JOIN " + TBL_PRESTAMOS_GPO_T + " AS pvg ON vg.num_prestamo = pvg.num_prestamo WHERE vg.num_prestamo NOT LIKE '%-L%') AS imp WHERE estatus = ?";

        Log.e("sqlImpresion", sql);
        row = db.rawQuery(sql, new String[]{"0"});

        Log.e("RowCount", row.getCount()+"....12345");

        if (row.getCount() > 0){
            row.moveToFirst();
            for(int i = 0; i < row.getCount(); i++){
                List<MSendImpresion> _impresiones = new ArrayList<>();
                MSendImpresion item = new MSendImpresion();
                String external_id =  "";
                String fInicio = "";
                Cursor row_gestion;
                int tipo_impresion = (row.getString(13).equals("VIGENTE"))?1:2;
                if (row.getInt(12) == 1){ //Busca respuestas en individual
                    String[] str = row.getString(1).split("-");

                    if (row.getString(13).equals("VIGENTE"))
                        row_gestion = dBhelper.customSelect(TBL_RESPUESTAS_IND_T, "fecha_inicio", " WHERE _id = ?", "", new String[]{str[2]});
                    else
                        row_gestion = dBhelper.customSelect(TBL_RESPUESTAS_IND_V_T, "fecha_inicio", " WHERE _id = ?", "", new String[]{str[2]});
                }
                else{ //Busca respuestas en grupal
                    String[] str = row.getString(1).split("-");

                    if (row.getString(13).equals("VIGENTE"))
                        row_gestion = dBhelper.customSelect(TBL_RESPUESTAS_GPO_T, "fecha_inicio", " WHERE _id = ?", "", new String[]{str[1]});
                    else
                        row_gestion = dBhelper.customSelect(TBL_RESPUESTAS_INTEGRANTE_T, "fecha_inicio", " WHERE _id = ?", "", new String[]{str[1]});
                }
                row_gestion.moveToFirst();
                fInicio = row_gestion.getString(0);

                Log.e("FechaInicio", fInicio);
                Calendar cal = Calendar.getInstance();
                try {
                    Date inicioGes = sdf.parse(fInicio);
                    cal.setTime(inicioGes);
                    String weekOfYear = (cal.get(Calendar.WEEK_OF_YEAR) < 10)?"0"+cal.get(Calendar.WEEK_OF_YEAR):String.valueOf(cal.get(Calendar.WEEK_OF_YEAR));
                    String nomenclatura = Miscellaneous.GetNomenclatura(row.getInt(12), row.getString(11));
                    external_id = cal.get(Calendar.YEAR)+weekOfYear+row.getString(2)+row.getString(10)+nomenclatura;
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                try {
                    item.setAsesorid(row.getString(2));
                    item.setExternalId(external_id);
                    item.setFolio(row.getString(3));
                    item.setTipo(row.getString(4));
                    item.setMontoRealizado(row.getDouble(5));
                    item.setSendedAt(Miscellaneous.ObtenerFecha(TIMESTAMP));
                    item.setGeneratedAt(row.getString(7));
                    item.setClavecliente(row.getString(6));
                    item.setErrores("");
                    item.setNumPrestamoIdGestion(row.getString(1));
                    item.setTipoCartera(Miscellaneous.GetIdTipoPrestamo(row.getString(11)));

                    Log.e("JSON", Miscellaneous.ConvertToJson(item));
                    _impresiones.add(item);
                    new GuardarImpresion()
                            .execute(ctx,
                                    _impresiones,
                                    row.getString(0),
                                    tipo_impresion);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                row.moveToNext();
            }
        }

        if (showDG)
            loading.dismiss();
    }

    //Envia las reimpresiones realizadas
    public void SendReimpresionesVi (Context ctx, boolean showDG){

        final AlertDialog loading = Popups.showLoadingDialog(ctx, R.string.please_wait, R.string.loading_info);

        if (showDG)
            loading.show();

        SessionManager session = new SessionManager(ctx);
        final DBhelper dBhelper = new DBhelper(ctx);
        final SQLiteDatabase db = dBhelper.getWritableDatabase();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Cursor row;
        String sql;

        sql = "SELECT * FROM (SELECT vri.*, pi.tipo_cartera, '1' AS tipo_gestion, 'VIGENTE' AS tipo_prestamo FROM " + TBL_REIMPRESION_VIGENTE_T + " AS vri INNER JOIN " + TBL_PRESTAMOS_IND_T + " AS pi ON vri.num_prestamo = pi.num_prestamo WHERE vri.num_prestamo LIKE '%-L%' AND pi.tipo_cartera IN ('VIGENTE','COBRANZA') UNION SELECT vri2.*, pg.tipo_cartera, '2' AS tipo_gestion, 'VIGENTE' AS tipo_prestamo FROM " + TBL_REIMPRESION_VIGENTE_T + " AS vri2 INNER JOIN " + TBL_PRESTAMOS_GPO_T + " AS pg ON vri2.num_prestamo = pg.num_prestamo WHERE vri2.num_prestamo NOT LIKE '%-L%' AND pg.tipo_cartera IN ('VIGENTE','COBRANZA') UNION SELECT vi.*, pvi.tipo_cartera, '1' AS tipo_gestion, 'VENCIDA' AS tipo_prestamo FROM " + TBL_REIMPRESION_VIGENTE_T + " AS vi INNER JOIN " + TBL_PRESTAMOS_IND_T + " AS pvi ON vi.num_prestamo = pvi.num_prestamo WHERE vi.num_prestamo LIKE '%-L%' AND pvi.tipo_cartera IN ('VENCIDA') UNION SELECT vg.*, pvg.tipo_cartera, '2' AS tipo_gestion, 'VENCIDA' AS tipo_prestamo FROM " + TBL_REIMPRESION_VIGENTE_T + " AS vg INNER JOIN " + TBL_PRESTAMOS_GPO_T + " AS pvg ON vg.num_prestamo = pvg.num_prestamo WHERE vg.num_prestamo NOT LIKE '%-L%' AND pvg.tipo_cartera IN ('VENCIDA')) AS imp WHERE estatus = ?";

        row = db.rawQuery(sql, new String[]{"0"});

        Log.e("RowCount", row.getCount()+"....12345");

        if (row.getCount() > 0){
            row.moveToFirst();
            for(int i = 0; i < row.getCount(); i++){
                List<MSendImpresion> _impresiones = new ArrayList<>();
                MSendImpresion item = new MSendImpresion();

                String external_id =  "";
                String fInicio = "";
                Cursor row_gestion;
                Log.e("NumPresTamoGestion", row.getString(1));
                if (row.getInt(13) == 1){
                    String[] str = row.getString(1).split("-");
                    Log.e("iDGestion", str[2]+"   "+row.getString(14));
                    if (row.getString(14).equals("VIGENTE"))
                        row_gestion = dBhelper.customSelect(TBL_RESPUESTAS_IND_T, "fecha_inicio", " WHERE _id = ?", "", new String[]{str[2]});
                    else
                        row_gestion = dBhelper.customSelect(TBL_RESPUESTAS_IND_V_T, "fecha_inicio", " WHERE _id = ?", "", new String[]{str[2]});
                }
                else{
                    String[] str = row.getString(1).split("-");
                    Log.e("iDGestion", str[1]);
                    if (row.getString(14).equals("VIGENTE"))
                        row_gestion = dBhelper.customSelect(TBL_RESPUESTAS_GPO_T, "fecha_inicio", " WHERE _id = ?", "", new String[]{str[1]});
                    else
                        row_gestion = dBhelper.customSelect(TBL_RESPUESTAS_INTEGRANTE_T, "fecha_inicio", " WHERE _id = ?", "", new String[]{str[1]});
                }
                Log.e("RowReimp", row_gestion.getCount()+" Reimpresion");
                row_gestion.moveToFirst();
                fInicio = row_gestion.getString(0);

                Log.e("FechaInicio", fInicio);
                Calendar cal = Calendar.getInstance();
                try {
                    Date inicioGes = sdf.parse(fInicio);
                    cal.setTime(inicioGes);
                    String weekOfYear = (cal.get(Calendar.WEEK_OF_YEAR) < 10)?"0"+cal.get(Calendar.WEEK_OF_YEAR):String.valueOf(cal.get(Calendar.WEEK_OF_YEAR));
                    String nomenclatura = Miscellaneous.GetNomenclatura(row.getInt(13), row.getString(12));
                    external_id = cal.get(Calendar.YEAR)+weekOfYear+row.getString(6)+row.getString(11)+nomenclatura;
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                try {
                    item.setAsesorid(row.getString(6));
                    item.setExternalId(external_id);
                    item.setFolio(row.getString(3));
                    item.setTipo("R"+row.getString(2));
                    item.setMontoRealizado(row.getDouble(4));
                    item.setSendedAt(Miscellaneous.ObtenerFecha(TIMESTAMP));
                    item.setGeneratedAt(row.getString(8));
                    item.setClavecliente(row.getString(5));
                    item.setErrores("");
                    item.setNumPrestamoIdGestion(row.getString(1));
                    item.setTipoCartera(Miscellaneous.GetIdTipoPrestamo(row.getString(12)));

                    Log.e("JSON", Miscellaneous.ConvertToJson(item));
                    _impresiones.add(item);
                    new GuardarImpresion()
                            .execute(ctx,
                                    _impresiones,
                                    row.getString(0),
                                    3);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                row.moveToNext();
            }
        }

        if (showDG)
            loading.dismiss();
    }

    public void SendTracker(Context ctx, boolean showDG) {

        SessionManager session = new SessionManager(ctx);
        final DBhelper dBhelper = new DBhelper(ctx);
        final SQLiteDatabase db = dBhelper.getWritableDatabase();

        Cursor row;

        if (ENVIROMENT)
            row = dBhelper.getRecords(TBL_TRACKER_ASESOR, " WHERE asesor_id = ? AND estatus = ?", "", new String[]{session.getUser().get(0), "0"});
        else
            row = dBhelper.getRecords(TBL_TRACKER_ASESOR_T, " WHERE asesor_id = ? AND estatus = ?", "", new String[]{session.getUser().get(0), "0"});

        if (row.getCount() > 0){
            row.moveToFirst();
            for (int i = 0; i < row.getCount(); i++){
                MTracker item = new MTracker();

                item.setDeviceId(row.getInt(0));
                item.setAsesorId(row.getString(1));
                item.setSerieId(row.getInt(2));
                item.setLatitud(row.getDouble(4));
                item.setLongitud(row.getDouble(5));
                item.setBateria(row.getDouble(6));
                item.setGeneratedAt(row.getString(7));
                item.setSendedAt(Miscellaneous.ObtenerFecha(TIMESTAMP));

                new RegistrarTracker().execute(ctx, item, row.getString(0));

                row.moveToNext();
            }
        }

    }

    public class RegistrarCierreDia extends AsyncTask<Object, Void, String>{

        @Override
        protected String doInBackground(Object... params) {
            Context ctx = (Context) params[0];
            final MCierreDia item = (MCierreDia) params[1];

            Log.e("Cursor", item.getNombre());
            Log.e("Cursor", item.getMonto());
            Log.e("Cursor", "MedioPago: "+item.getMedioPago());
            Log.e("Cursor", item.getClaveCliente());
            Log.e("Cursor", item.getEvidencia());
            Log.e("Cursor", item.getNumPrestamo());
            Log.e("Cursor", item.getSerialId());
            Log.e("Cursor", item.getFechaInicio());
            Log.e("Cursor", item.getFechaFin());



            DBhelper dBhelper = new DBhelper(ctx);
            final SQLiteDatabase db = dBhelper.getWritableDatabase();

            SessionManager session = new SessionManager(ctx);

            if (NetworkStatus.haveNetworkConnection(ctx)) {
                RequestBody numPrestamoBody = RequestBody.create(MultipartBody.FORM, item.getNumPrestamo());
                RequestBody claveClienteBody = RequestBody.create(MultipartBody.FORM, item.getClaveCliente());
                RequestBody serialIdBody = RequestBody.create(MultipartBody.FORM, item.getSerialId());
                RequestBody medioPagoBody = RequestBody.create(MultipartBody.FORM, String.valueOf(item.getMedioPago()));
                RequestBody montoDepositadoBody = RequestBody.create(MultipartBody.FORM, item.getMonto());
                RequestBody nombreImagenBody = RequestBody.create(MultipartBody.FORM, item.getEvidencia());
                RequestBody fechaInicioBody = RequestBody.create(MultipartBody.FORM, item.getFechaInicio());
                RequestBody fechaFinBody = RequestBody.create(MultipartBody.FORM, item.getFechaInicio());
                RequestBody fechaEnvioBody = RequestBody.create(MultipartBody.FORM, Miscellaneous.ObtenerFecha(TIMESTAMP));
                MultipartBody.Part envidenciaBody = null;
                final File image_evidencia = new File(Constants.ROOT_PATH + "CierreDia/"+item.getEvidencia());
                if (!item.getEvidencia().isEmpty() && image_evidencia != null) {
                    RequestBody imageBody =
                            RequestBody.create(
                                    MediaType.parse("image/*"), image_evidencia);

                    envidenciaBody = MultipartBody.Part.createFormData("fotografia", image_evidencia.getName(), imageBody);
                }

                ManagerInterface api = new RetrofitClient().generalRF(CONTROLLER_MOVIL, ctx).create(ManagerInterface.class);


                Call<MResCierreDia> call = api.saveCierreDia("Bearer "+ session.getUser().get(7),
                                                                        numPrestamoBody,
                                                                        claveClienteBody,
                                                                        serialIdBody,
                                                                        medioPagoBody,
                                                                        montoDepositadoBody,
                                                                        nombreImagenBody,
                                                                        fechaInicioBody,
                                                                        fechaFinBody,
                                                                        fechaEnvioBody,
                                                                        envidenciaBody);

                call.enqueue(new Callback<MResCierreDia>() {
                    @Override
                    public void onResponse(Call<MResCierreDia> call, Response<MResCierreDia> response) {
                        Log.e("CodeCierre", String.valueOf(response.code()));
                        switch (response.code()){
                            case 200:
                                ContentValues cv = new ContentValues();
                                cv.put("fecha_envio", Miscellaneous.ObtenerFecha(TIMESTAMP));
                                cv.put("estatus", 2);
                                db.update(TBL_CIERRE_DIA_T, cv, "_id = ?", new String[]{item.getId()});

                                break;
                        }
                    }

                    @Override
                    public void onFailure(Call<MResCierreDia> call, Throwable t) {
                        Log.e("FailCierre", t.getMessage());
                    }
                });


            }


            return "";
        }
    }

    public class RegistrarTracker extends AsyncTask<Object, Void, String>{

        @Override
        protected String doInBackground(Object... params) {
            Context ctx = (Context) params[0];
            MTracker tracker = (MTracker) params[1];
            final String id_tracker = (String) params[2];

            DBhelper dBhelper = new DBhelper(ctx);
            final SQLiteDatabase db = dBhelper.getWritableDatabase();
            SessionManager session = new SessionManager(ctx);

            ManagerInterface api = new RetrofitClient().generalRF(CONTROLLER_MOVIL, ctx).create(ManagerInterface.class);

            Call<MResponseTracker> call = api.guardarTracker(tracker,
                    "Bearer "+ session.getUser().get(7));

            call.enqueue(new Callback<MResponseTracker>() {
                @Override
                public void onResponse(Call<MResponseTracker> call, Response<MResponseTracker> response) {
                    Log.e("ResponceTracke", response.code()+"xxx");
                    switch (response.code()){
                        case 200:
                            ContentValues cv = new ContentValues();
                            cv.put("sended_at", Miscellaneous.ObtenerFecha(TIMESTAMP));
                            cv.put("estatus", 1);
                            db.update((ENVIROMENT)?TBL_TRACKER_ASESOR:TBL_TRACKER_ASESOR_T, cv, "_id = ?", new String[]{id_tracker});
                            break;
                    }
                }

                @Override
                public void onFailure(Call<MResponseTracker> call, Throwable t) {

                }
            });

            return "";
        }
    }

    public class ObtenerPrestamos extends AsyncTask<Object, Void, String>{

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(Object... params) {
            //AlertDialog pDialog;
            //pDialog = Popups.showLoadingDialog(ctx, R.string.please_wait, R.string.loading_info);
            //pDialog.setCancelable(false);
            //pDialog.show();
            final int id = (int)params[0];
            Context ctx = (Context)params[1];
            int tipo_prestamo = (int)params[2];

            SessionManager session = new SessionManager(ctx);
            ManagerInterface api = new RetrofitClient().generalRF(Constants.CONTROLLER_MOVIL, ctx).create(ManagerInterface.class);

            if(tipo_prestamo == 1){ //Obtiene prestamos individuales
                Call<List<MPrestamoRes>> call = api.getPrestamosInd(id,"Bearer "+ session.getUser().get(7));

                try {
                    List<MPrestamoRes> prestamos = call.execute().body();
                    //List<MPrestamoRes> prestamos = response.body();
                    Log.e("Cartera Size", prestamos.size()+"");
                    if (prestamos.size() > 0){
                        DBhelper dBhelper = new DBhelper(ctx);
                        SQLiteDatabase db = dBhelper.getWritableDatabase();
                        Cursor row;

                        Log.e("size antes for", ""+prestamos.size());
                        for (int i = 0; i < prestamos.size(); i++){
                            Log.e("Count i", ""+i);
                            Log.e("id_prestamo", ""+prestamos.get(i).getId());
                            String where = " WHERE id_prestamo = ?";
                            String order = "";
                            String[] args =  new String[] {String.valueOf(prestamos.get(i).getId())};

                            if (ENVIROMENT)
                                row = dBhelper.getRecords(TBL_PRESTAMOS_IND, where, order, args);
                            else
                                row = dBhelper.getRecords(TBL_PRESTAMOS_IND_T, where, order, args);

                            if (row.getCount() == 0){ //Registra el prestamo de ind
                                Log.e("Prestamo", "Registra Prestamo");
                                HashMap<Integer, String> values = new HashMap<>();
                                values.put(0, String.valueOf(prestamos.get(i).getId()));                    //ID PRESTAMO
                                values.put(1, String.valueOf(prestamos.get(i).getClienteId()));             //CLIENTE ID
                                values.put(2, prestamos.get(i).getNumPrestamo());                           //NUM_PRESTAMO
                                values.put(3, String.valueOf(prestamos.get(i).getNumSolicitud()));          //NUM_SOLICITUD
                                values.put(4, prestamos.get(i).getFechaEntrega());                          //FECHA_ENTREGA
                                values.put(5, String.valueOf(prestamos.get(i).getMontoOtorgado()));         //MONTO OTORGADO
                                values.put(6, String.valueOf(prestamos.get(i).getMontoTotal()));            //MONTO TOTAL
                                values.put(7, String.valueOf(prestamos.get(i).getMontoAmortizacion()));     //MONTO AMORTIZACION
                                values.put(8, String.valueOf(prestamos.get(i).getMontoRequerido()));        //MONTO REQUERIDO
                                values.put(9, String.valueOf(prestamos.get(i).getNumAmortizacion()));       //NUM AMORTIZACION
                                values.put(10, prestamos.get(i).getFechaEstablecida());                     //FECHA ESTABLECIDA
                                values.put(11, prestamos.get(i).getTipoCartera());                          //TIPO CARTERA
                                values.put(12, (prestamos.get(i).getPagada())?"1":"0");                     //PAGADA
                                values.put(13, Miscellaneous.ObtenerFecha(TIMESTAMP));                      //FECHA CREACION
                                values.put(14, Miscellaneous.ObtenerFecha(TIMESTAMP));                      //FECHA ACTUALIZACION

                                if (ENVIROMENT)
                                    dBhelper.savePrestamosInd(db, TBL_PRESTAMOS_IND, values);
                                else
                                    dBhelper.savePrestamosInd(db, TBL_PRESTAMOS_IND_T, values);

                                if (prestamos.get(i).getAval() != null) {
                                    MAval mAval = prestamos.get(i).getAval();
                                    HashMap<Integer, String> values_aval = new HashMap<>();
                                    values_aval.put(0, String.valueOf(prestamos.get(i).getId()));            //ID PRESTAMO
                                    values_aval.put(1, String.valueOf(mAval.getId()));                       //AVAL ID
                                    values_aval.put(2, mAval.getNombre());                                   //NOMBRE
                                    values_aval.put(3, mAval.getParentesco());                               //PARENTESCO
                                    values_aval.put(4, mAval.getDireccion());                                //DIRECCIO
                                    values_aval.put(5, mAval.getTelefono());                                 //TELEFONO
                                    values_aval.put(6, Miscellaneous.ObtenerFecha(TIMESTAMP));               //FECHA CREACION
                                    values_aval.put(7, Miscellaneous.ObtenerFecha(TIMESTAMP));               //FECHA ACTUALIZACION

                                    if (ENVIROMENT)
                                        dBhelper.saveAval(db, TBL_AVAL, values_aval);
                                    else
                                        dBhelper.saveAval(db, TBL_AVAL_T, values_aval);

                                }

                                if (prestamos.get(i).getAmortizaciones().size() > 0){
                                    for (int j = 0; j < prestamos.get(i).getAmortizaciones().size(); j++){
                                        MAmortizacion mAmortizacion = prestamos.get(i).getAmortizaciones().get(j);
                                        HashMap<Integer, String> values_amortiz = new HashMap<>();
                                        values_amortiz.put(0, String.valueOf(mAmortizacion.getId()));                                   //ID AMORTIZACION
                                        values_amortiz.put(1, String.valueOf(mAmortizacion.getPrestamoId()));                           //ID PRESTAMOS
                                        values_amortiz.put(2, mAmortizacion.getFecha());                                                //FECHA
                                        values_amortiz.put(3, (mAmortizacion.getFechaPago() != null)?mAmortizacion.getFechaPago():"");  //FECHA PAGO
                                        values_amortiz.put(4, String.valueOf(mAmortizacion.getCapital()));                              //CAPITAL
                                        values_amortiz.put(5, String.valueOf(mAmortizacion.getInteres()));                              //INTERES
                                        values_amortiz.put(6, String.valueOf(mAmortizacion.getIva()));                                  //IVA
                                        values_amortiz.put(7, String.valueOf(mAmortizacion.getComision()));                             //COMISION
                                        values_amortiz.put(8, String.valueOf(mAmortizacion.getTotal()));                                //TOTAL
                                        values_amortiz.put(9, String.valueOf(mAmortizacion.getCapitalPagado()));                        //CAPITAL PAGADO
                                        values_amortiz.put(10, String.valueOf(mAmortizacion.getInteresPagado()));                       //INTERES PAGADO
                                        values_amortiz.put(11, String.valueOf(mAmortizacion.getIvaPagado()));                           //IVA PAGADO
                                        values_amortiz.put(12, String.valueOf(mAmortizacion.getInteresMoratorioPagado()));              //INTERES MORATORIO PAGADO
                                        values_amortiz.put(13, String.valueOf(mAmortizacion.getIvaMoratorioPagado()));                  //IVA_MORATORIO PAGADO
                                        values_amortiz.put(14, String.valueOf(mAmortizacion.getComisionPagada()));                      //COMISION PAGADA
                                        values_amortiz.put(15, String.valueOf(mAmortizacion.getTotalPagado()));                         //TOTAL PAGADO
                                        values_amortiz.put(16, String.valueOf(mAmortizacion.getPagado()));                              //PAGADO
                                        values_amortiz.put(17, String.valueOf(mAmortizacion.getNumero()));                              //NUMERO
                                        values_amortiz.put(18, String.valueOf(mAmortizacion.getDiasAtraso()));                          //DIAS ATRASO
                                        values_amortiz.put(19, Miscellaneous.ObtenerFecha(TIMESTAMP));                                  //FECHA DISPOSITIVO
                                        values_amortiz.put(20, Miscellaneous.ObtenerFecha(TIMESTAMP));                                  //FECHA ACTUALIZADO

                                        if (ENVIROMENT)
                                            dBhelper.saveAmortizaciones(db, TBL_AMORTIZACIONES, values_amortiz);
                                        else
                                            dBhelper.saveAmortizaciones(db, TBL_AMORTIZACIONES_T, values_amortiz);
                                    }
                                }

                                if (prestamos.get(i).getPagos() != null &&prestamos.get(i).getPagos().size() > 0){
                                    for (int k = 0; k < prestamos.get(i).getPagos().size(); k++){
                                        MPago mPago = prestamos.get(i).getPagos().get(k);
                                        HashMap<Integer, String> values_pago = new HashMap<>();
                                        values_pago.put(0, String.valueOf(prestamos.get(i).getId()));            //ID PRESTAMO
                                        values_pago.put(1, mPago.getFecha());                                    //FECHA
                                        values_pago.put(2, String.valueOf(mPago.getMonto()));                    //MONTO
                                        values_pago.put(3,mPago.getBanco());                                     //BANCO
                                        values_pago.put(4, Miscellaneous.ObtenerFecha(TIMESTAMP));               //FECHA DISPOSITIVO
                                        values_pago.put(5, Miscellaneous.ObtenerFecha(TIMESTAMP));               //FECHA ACTUALIZADO

                                        if (ENVIROMENT)
                                            dBhelper.savePagos(db, TBL_PAGOS, values_pago);
                                        else
                                            dBhelper.savePagos(db, TBL_PAGOS_T, values_pago);
                                    }
                                }
                            }
                            else{ //Actualiza la cartera de ind
                                row.moveToFirst();
                                ContentValues cv = new ContentValues();
                                cv.put("fecha_entrega", prestamos.get(i).getFechaEntrega());
                                cv.put("monto_otorgado", String.valueOf(prestamos.get(i).getMontoOtorgado()));
                                cv.put("monto_total", String.valueOf(prestamos.get(i).getMontoTotal()));
                                cv.put("monto_amortizacion", String.valueOf(prestamos.get(i).getMontoAmortizacion()));
                                cv.put("monto_requerido", String.valueOf(prestamos.get(i).getMontoRequerido()));
                                cv.put("num_amortizacion", String.valueOf(prestamos.get(i).getNumAmortizacion()));
                                cv.put("fecha_establecida", prestamos.get(i).getFechaEstablecida());
                                cv.put("tipo_cartera", prestamos.get(i).getTipoCartera());
                                cv.put("pagada", (prestamos.get(i).getPagada())?"1":"0");
                                cv.put("fecha_actualizado", Miscellaneous.ObtenerFecha("timestamp"));

                                db.update((ENVIROMENT)?TBL_PRESTAMOS_IND:TBL_PRESTAMOS_IND_T, cv,
                                        "_id = ?", new String[]{row.getString(0)});
                                if (prestamos.get(i).getAval() != null) {

                                    MAval mAval = prestamos.get(i).getAval();
                                    ContentValues cv_aval = new ContentValues();
                                    cv_aval.put("id_aval", String.valueOf(mAval.getId()));                      //AVAL ID
                                    cv_aval.put("nombre", mAval.getNombre());                                   //NOMBRE
                                    cv_aval.put("parentesco", mAval.getParentesco());                           //PARENTESCO
                                    cv_aval.put("direccion", mAval.getDireccion());                             //DIRECCIO
                                    cv_aval.put("telefono", mAval.getTelefono());                               //TELEFONO
                                    cv_aval.put("fecha_actualizado", Miscellaneous.ObtenerFecha(TIMESTAMP));    //FECHA ACTUALIZACION

                                    db.update((ENVIROMENT)?TBL_AVAL:TBL_AVAL_T, cv_aval,
                                            "id_prestamo = ?", new String[]{String.valueOf(prestamos.get(i).getId())});
                                }

                                if (prestamos.get(i).getAmortizaciones().size() > 0){
                                    for (int j = 0; j < prestamos.get(i).getAmortizaciones().size(); j++){
                                        MAmortizacion mAmortizacion = prestamos.get(i).getAmortizaciones().get(j);
                                        ContentValues cv_amortiz = new ContentValues();
                                        cv_amortiz.put("fecha", mAmortizacion.getFecha());                                                //FECHA
                                        cv_amortiz.put("fecha_pago", (mAmortizacion.getFechaPago() != null)?mAmortizacion.getFechaPago():"");  //FECHA PAGO
                                        cv_amortiz.put("capital", String.valueOf(mAmortizacion.getCapital()));                              //CAPITAL
                                        cv_amortiz.put("interes", String.valueOf(mAmortizacion.getInteres()));                              //INTERES
                                        cv_amortiz.put("iva", String.valueOf(mAmortizacion.getIva()));                                  //IVA
                                        cv_amortiz.put("comision", String.valueOf(mAmortizacion.getComision()));                             //COMISION
                                        cv_amortiz.put("total", String.valueOf(mAmortizacion.getTotal()));                                //TOTAL
                                        cv_amortiz.put("capital_pagado", String.valueOf(mAmortizacion.getCapitalPagado()));                        //CAPITAL PAGADO
                                        cv_amortiz.put("interes_pagado", String.valueOf(mAmortizacion.getInteresPagado()));                       //INTERES PAGADO
                                        cv_amortiz.put("iva_pagado", String.valueOf(mAmortizacion.getIvaPagado()));                           //IVA PAGADO
                                        cv_amortiz.put("interes_moratorio_pagado", String.valueOf(mAmortizacion.getInteresMoratorioPagado()));              //INTERES MORATORIO PAGADO
                                        cv_amortiz.put("iva_moratorio_pagado", String.valueOf(mAmortizacion.getIvaMoratorioPagado()));                  //IVA_MORATORIO PAGADO
                                        cv_amortiz.put("comision_pagada", String.valueOf(mAmortizacion.getComisionPagada()));                      //COMISION PAGADA
                                        cv_amortiz.put("total_pagado", String.valueOf(mAmortizacion.getTotalPagado()));                         //TOTAL PAGADO
                                        cv_amortiz.put("pagado", String.valueOf(mAmortizacion.getPagado()));                              //PAGADO
                                        cv_amortiz.put("numero", String.valueOf(mAmortizacion.getNumero()));                              //NUMERO
                                        cv_amortiz.put("dias_atraso", String.valueOf(mAmortizacion.getDiasAtraso()));                          //DIAS ATRASO
                                        cv_amortiz.put("fecha_actualizado", Miscellaneous.ObtenerFecha(TIMESTAMP)); //FECHA ACTUALIZADO

                                        db.update((ENVIROMENT)?TBL_AMORTIZACIONES:TBL_AMORTIZACIONES_T, cv_amortiz,
                                                "id_amortizacion = ? AND id_prestamo = ?", new String[]{String.valueOf(mAmortizacion.getId()), String.valueOf(prestamos.get(i).getId())});
                                    }
                                }

                                if (prestamos.get(i).getPagos() != null &&prestamos.get(i).getPagos().size() > 0){
                                    for (int k = 0; k < prestamos.get(i).getPagos().size(); k++){
                                        MPago mPago = prestamos.get(i).getPagos().get(k);
                                        Cursor row_pago = dBhelper.getRecords((ENVIROMENT)?TBL_PAGOS:TBL_PAGOS_T, " WHERE id_prestamo = ? AND fecha = ? AND monto = ? AND banco = ?", "",
                                                new String[]{String.valueOf(prestamos.get(i).getId()),mPago.getFecha(), mPago.getBanco(), String.valueOf(mPago.getMonto())});
                                        if (row_pago.getCount() == 0){
                                            HashMap<Integer, String> cv_pago = new HashMap<>();
                                            cv_pago.put(0, String.valueOf(prestamos.get(i).getId()));            //ID PRESTAMO
                                            cv_pago.put(1, mPago.getFecha());                                    //FECHA
                                            cv_pago.put(2, String.valueOf(mPago.getMonto()));                    //MONTO
                                            cv_pago.put(3,mPago.getBanco());                                     //BANCO
                                            cv_pago.put(4, Miscellaneous.ObtenerFecha(TIMESTAMP));               //FECHA DISPOSITIVO
                                            cv_pago.put(5, Miscellaneous.ObtenerFecha(TIMESTAMP));               //FECHA ACTUALIZADO

                                            dBhelper.savePagos(db, (ENVIROMENT)?TBL_PAGOS:TBL_PAGOS_T, cv_pago);
                                        }
                                     row_pago.close();
                                    }
                                }
                            }
                        } //Fin Ciclo For
                    }//Fin IF

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else if (tipo_prestamo == 2){ //Obtiene prestamos grupales
                Call<List<MPrestamoGpoRes>> call = api.getPrestamosGpo(id,"Bearer "+ session.getUser().get(7));

                try {
                    List<MPrestamoGpoRes> prestamos = call.execute().body();
                    if (prestamos.size() > 0){
                        DBhelper dBhelper = new DBhelper(ctx);
                        SQLiteDatabase db = dBhelper.getWritableDatabase();
                        Cursor row;

                        Log.e("size antes for", ""+prestamos.size());
                        for (int i = 0; i < prestamos.size(); i++){
                            Log.e("Count i", ""+i);
                            Log.e("id_prestamo", ""+prestamos.get(i).getId());
                            String where = " WHERE id_prestamo = ?";
                            String order = "";
                            String[] args =  new String[] {String.valueOf(prestamos.get(i).getId())};

                            if (ENVIROMENT)
                                row = dBhelper.getRecords(TBL_PRESTAMOS_GPO, where, order, args);
                            else
                                row = dBhelper.getRecords(TBL_PRESTAMOS_GPO_T, where, order, args);

                            if (row.getCount() == 0){ //Registra el prestamo de gpo
                                Log.e("Prestamo", "Registra Prestamo");
                                HashMap<Integer, String> values = new HashMap<>();
                                values.put(0, String.valueOf(prestamos.get(i).getId()));                    //ID PRESTAMO
                                values.put(1, String.valueOf(prestamos.get(i).getGrupoId()));               //GRUPO ID
                                values.put(2, prestamos.get(i).getNumPrestamo());                           //NUM_PRESTAMO
                                values.put(3, String.valueOf(prestamos.get(i).getNumSolicitud()));          //NUM_SOLICITUD
                                values.put(4, prestamos.get(i).getFechaEntrega());                          //FECHA_ENTREGA
                                values.put(5, String.valueOf(prestamos.get(i).getMontoOtorgado()));         //MONTO OTORGADO
                                values.put(6, String.valueOf(prestamos.get(i).getMontoTotal()));            //MONTO TOTAL
                                values.put(7, String.valueOf(prestamos.get(i).getMontoAmortizacion()));     //MONTO AMORTIZACION
                                values.put(8, String.valueOf(prestamos.get(i).getMontoRequerido()));        //MONTO REQUERIDO
                                values.put(9, String.valueOf(prestamos.get(i).getNumAmortizacion()));       //NUM AMORTIZACION
                                values.put(10, prestamos.get(i).getFechaEstablecida());                     //FECHA ESTABLECIDA
                                values.put(11, prestamos.get(i).getTipoCartera());                          //TIPO CARTERA
                                values.put(12, (prestamos.get(i).getPagada().equals("PAGADA"))?"1":"0");    //PAGADA
                                values.put(13, Miscellaneous.ObtenerFecha(TIMESTAMP));                      //FECHA CREACION
                                values.put(14, Miscellaneous.ObtenerFecha(TIMESTAMP));                      //FECHA ACTUALIZACION

                                if (ENVIROMENT)
                                    dBhelper.savePrestamosGpo(db, TBL_PRESTAMOS_GPO, values);
                                else
                                    dBhelper.savePrestamosGpo(db, TBL_PRESTAMOS_GPO_T, values);

                                if (prestamos.get(i).getIntegrantes() != null) {
                                    for (int l = 0; l < prestamos.get(i).getIntegrantes().size(); l++){
                                        MIntegrante mIntegrante = prestamos.get(i).getIntegrantes().get(l);
                                        HashMap<Integer, String> values_miembro = new HashMap<>();
                                        values_miembro.put(0, String.valueOf(prestamos.get(i).getId()));            //ID PRESTAMO
                                        values_miembro.put(1, String.valueOf(mIntegrante.getId()));                 //INTEGRANTE ID
                                        values_miembro.put(2, String.valueOf(mIntegrante.getNumSolicitud()));       //NUM SOLICITUD
                                        values_miembro.put(3, String.valueOf(mIntegrante.getGrupoId()));            //GRUPO ID
                                        values_miembro.put(4, mIntegrante.getNombre());                             //NOMBRE
                                        values_miembro.put(5, mIntegrante.getDireccion());                          //DIRECCION
                                        values_miembro.put(6, mIntegrante.getTelCasa());                            //TEL CASA
                                        values_miembro.put(7, mIntegrante.getTelCelular());                         //TEL CELULAR
                                        values_miembro.put(8, mIntegrante.getTipo());                               //TIPO
                                        values_miembro.put(9, mIntegrante.getMontoPrestamo());                      //MONTO PRESTAMO
                                        values_miembro.put(10, mIntegrante.getMontoRequerido());                     //MONTO REQUERIDO
                                        values_miembro.put(11, Miscellaneous.ObtenerFecha(TIMESTAMP));               //FECHA CREACION
                                        values_miembro.put(12, Miscellaneous.ObtenerFecha(TIMESTAMP));               //FECHA ACTUALIZACION
                                        values_miembro.put(13, mIntegrante.getClave());                              //CLAVE
                                        values_miembro.put(14, String.valueOf(mIntegrante.getPrestamoId()));         //PRESTAMO ID


                                        dBhelper.saveMiembros(db, values_miembro);

                                    }
                                }

                                if (prestamos.get(i).getAmortizaciones().size() > 0){
                                    for (int j = 0; j < prestamos.get(i).getAmortizaciones().size(); j++){
                                        MAmortizacion mAmortizacion = prestamos.get(i).getAmortizaciones().get(j);
                                        HashMap<Integer, String> values_amortiz = new HashMap<>();
                                        values_amortiz.put(0, String.valueOf(mAmortizacion.getId()));                      //ID AMORTIZACION
                                        values_amortiz.put(1, String.valueOf(mAmortizacion.getPrestamoId()));              //ID PRESTAMOS
                                        values_amortiz.put(2, mAmortizacion.getFecha());                                   //FECHA
                                        values_amortiz.put(3, (mAmortizacion.getFechaPago() != null)?mAmortizacion.getFechaPago():"");        //FECHA PAGO
                                        values_amortiz.put(4, String.valueOf(mAmortizacion.getCapital()));                 //CAPITAL
                                        values_amortiz.put(5, String.valueOf(mAmortizacion.getInteres()));                 //INTERES
                                        values_amortiz.put(6, String.valueOf(mAmortizacion.getIva()));                     //IVA
                                        values_amortiz.put(7, String.valueOf(mAmortizacion.getComision()));                //COMISION
                                        values_amortiz.put(8, String.valueOf(mAmortizacion.getTotal()));                   //TOTAL
                                        values_amortiz.put(9, String.valueOf(mAmortizacion.getCapitalPagado()));           //CAPITAL PAGADO
                                        values_amortiz.put(10, String.valueOf(mAmortizacion.getInteresPagado()));          //INTERES PAGADO
                                        values_amortiz.put(11, String.valueOf(mAmortizacion.getIvaPagado()));              //IVA PAGADO
                                        values_amortiz.put(12, String.valueOf(mAmortizacion.getInteresMoratorioPagado())); //INTERES MORATORIO PAGADO
                                        values_amortiz.put(13, String.valueOf(mAmortizacion.getIvaMoratorioPagado()));     //IVA_MORATORIO PAGADO
                                        values_amortiz.put(14, String.valueOf(mAmortizacion.getComisionPagada()));         //COMISION PAGADA
                                        values_amortiz.put(15, String.valueOf(mAmortizacion.getTotalPagado()));            //TOTAL PAGADO
                                        values_amortiz.put(16, String.valueOf(mAmortizacion.getPagado()));                 //PAGADO
                                        values_amortiz.put(17, String.valueOf(mAmortizacion.getNumero()));                 //NUMERO
                                        values_amortiz.put(18, String.valueOf(mAmortizacion.getDiasAtraso()));             //DIAS ATRASO
                                        values_amortiz.put(19, Miscellaneous.ObtenerFecha(TIMESTAMP));                     //FECHA DISPOSITIVO
                                        values_amortiz.put(20, Miscellaneous.ObtenerFecha(TIMESTAMP));                     //FECHA ACTUALIZADO

                                        if (ENVIROMENT)
                                            dBhelper.saveAmortizaciones(db, TBL_AMORTIZACIONES, values_amortiz);
                                        else
                                            dBhelper.saveAmortizaciones(db, TBL_AMORTIZACIONES_T, values_amortiz);
                                    }
                                }

                                if (prestamos.get(i).getPagos() != null &&prestamos.get(i).getPagos().size() > 0){
                                    for (int k = 0; k < prestamos.get(i).getPagos().size(); k++){
                                        MPago mPago = prestamos.get(i).getPagos().get(k);
                                        HashMap<Integer, String> values_pago = new HashMap<>();
                                        values_pago.put(0, String.valueOf(prestamos.get(i).getId()));            //ID PRESTAMO
                                        values_pago.put(1, mPago.getFecha());                                    //FECHA
                                        values_pago.put(2, String.valueOf(mPago.getMonto()));                    //MONTO
                                        values_pago.put(3,mPago.getBanco());                                     //BANCO
                                        values_pago.put(4, Miscellaneous.ObtenerFecha(TIMESTAMP));               //FECHA DISPOSITIVO
                                        values_pago.put(5, Miscellaneous.ObtenerFecha(TIMESTAMP));               //FECHA ACTUALIZADO

                                        if (ENVIROMENT)
                                            dBhelper.savePagos(db, TBL_PAGOS, values_pago);
                                        else
                                            dBhelper.savePagos(db, TBL_PAGOS_T, values_pago);
                                    }
                                }


                            }
                            else{ //Actualiza la prestamo gpo
                                row.moveToFirst();
                                ContentValues cv_prestamo = new ContentValues();
                                cv_prestamo.put("fecha_entrega", prestamos.get(i).getFechaEntrega());                          //FECHA_ENTREGA
                                cv_prestamo.put("monto_otorgado", String.valueOf(prestamos.get(i).getMontoOtorgado()));         //MONTO OTORGADO
                                cv_prestamo.put("monto_total", String.valueOf(prestamos.get(i).getMontoTotal()));            //MONTO TOTAL
                                cv_prestamo.put("monto_amortizacion", String.valueOf(prestamos.get(i).getMontoAmortizacion()));     //MONTO AMORTIZACION
                                cv_prestamo.put("monto_requerido", String.valueOf(prestamos.get(i).getMontoRequerido()));        //MONTO REQUERIDO
                                cv_prestamo.put("num_amortizacion", String.valueOf(prestamos.get(i).getNumAmortizacion()));       //NUM AMORTIZACION
                                cv_prestamo.put("fecha_establecida", prestamos.get(i).getFechaEstablecida());                     //FECHA ESTABLECIDA
                                cv_prestamo.put("tipo_cartera", prestamos.get(i).getTipoCartera());                          //TIPO CARTERA
                                cv_prestamo.put("pagada", (prestamos.get(i).getPagada().equals("PAGADA"))?"1":"0");                     //PAGADA
                                cv_prestamo.put("fecha_actualizado", Miscellaneous.ObtenerFecha(TIMESTAMP));                      //FECHA ACTUALIZACION

                                db.update((ENVIROMENT)?TBL_PRESTAMOS_GPO:TBL_PRESTAMOS_GPO_T, cv_prestamo,
                                        "_id = ?", new String[]{row.getString(0)});

                                if (prestamos.get(i).getIntegrantes() != null) {
                                    for (int l = 0; l < prestamos.get(i).getIntegrantes().size(); l++){
                                        MIntegrante mIntegrante = prestamos.get(i).getIntegrantes().get(l);
                                        ContentValues cv_miembro = new ContentValues();
                                        cv_miembro.put("nombre", mIntegrante.getNombre());                              //NOMBRE
                                        cv_miembro.put("direccion", mIntegrante.getDireccion());                        //DIRECCION
                                        cv_miembro.put("tel_casa", mIntegrante.getTelCasa());                           //TEL CASA
                                        cv_miembro.put("tel_celular", mIntegrante.getTelCelular());                     //TEL CELULAR
                                        cv_miembro.put("tipo_integrante", mIntegrante.getTipo());                       //TIPO
                                        cv_miembro.put("monto_prestamo", mIntegrante.getMontoPrestamo());               //MONTO PRESTAMO
                                        cv_miembro.put("monto_requerido", mIntegrante.getMontoRequerido());             //MONTO REQUERIDO
                                        cv_miembro.put("fecha_actualizado", Miscellaneous.ObtenerFecha(TIMESTAMP));     //FECHA ACTUALIZACION
                                        cv_miembro.put("clave", mIntegrante.getClave());                                //CLAVE
                                        cv_miembro.put("id_prestamo_integrante", String.valueOf(mIntegrante.getPrestamoId()));     //PRESTAMO ID

                                        db.update((ENVIROMENT)?TBL_MIEMBROS_GPO:TBL_MIEMBROS_GPO_T, cv_miembro,
                                                "id_prestamo = ? AND id_integrante = ?",
                                        new String[]{String.valueOf(prestamos.get(i).getId()), String.valueOf(mIntegrante.getId())});
                                    }
                                }//Termina If de actualizado de integrantes

                                if (prestamos.get(i).getAmortizaciones().size() > 0){
                                    for (int j = 0; j < prestamos.get(i).getAmortizaciones().size(); j++){
                                        MAmortizacion mAmortizacion = prestamos.get(i).getAmortizaciones().get(j);
                                        ContentValues cv_amortiz = new ContentValues();
                                        cv_amortiz.put("fecha", mAmortizacion.getFecha());                                                //FECHA
                                        cv_amortiz.put("fecha_pago", (mAmortizacion.getFechaPago() != null)?mAmortizacion.getFechaPago():"");  //FECHA PAGO
                                        cv_amortiz.put("capital", String.valueOf(mAmortizacion.getCapital()));                              //CAPITAL
                                        cv_amortiz.put("interes", String.valueOf(mAmortizacion.getInteres()));                              //INTERES
                                        cv_amortiz.put("iva", String.valueOf(mAmortizacion.getIva()));                                  //IVA
                                        cv_amortiz.put("comision", String.valueOf(mAmortizacion.getComision()));                             //COMISION
                                        cv_amortiz.put("total", String.valueOf(mAmortizacion.getTotal()));                                //TOTAL
                                        cv_amortiz.put("capital_pagado", String.valueOf(mAmortizacion.getCapitalPagado()));                        //CAPITAL PAGADO
                                        cv_amortiz.put("interes_pagado", String.valueOf(mAmortizacion.getInteresPagado()));                       //INTERES PAGADO
                                        cv_amortiz.put("iva_pagado", String.valueOf(mAmortizacion.getIvaPagado()));                           //IVA PAGADO
                                        cv_amortiz.put("interes_moratorio_pagado", String.valueOf(mAmortizacion.getInteresMoratorioPagado()));              //INTERES MORATORIO PAGADO
                                        cv_amortiz.put("iva_moratorio_pagado", String.valueOf(mAmortizacion.getIvaMoratorioPagado()));                  //IVA_MORATORIO PAGADO
                                        cv_amortiz.put("comision_pagada", String.valueOf(mAmortizacion.getComisionPagada()));                      //COMISION PAGADA
                                        cv_amortiz.put("total_pagado", String.valueOf(mAmortizacion.getTotalPagado()));                         //TOTAL PAGADO
                                        cv_amortiz.put("pagado", String.valueOf(mAmortizacion.getPagado()));                              //PAGADO
                                        cv_amortiz.put("numero", String.valueOf(mAmortizacion.getNumero()));                              //NUMERO
                                        cv_amortiz.put("dias_atraso", String.valueOf(mAmortizacion.getDiasAtraso()));                          //DIAS ATRASO
                                        cv_amortiz.put("fecha_actualizado", Miscellaneous.ObtenerFecha(TIMESTAMP)); //FECHA ACTUALIZADO

                                        db.update((ENVIROMENT)?TBL_AMORTIZACIONES:TBL_AMORTIZACIONES_T, cv_amortiz,
                                                "id_amortizacion = ? AND id_prestamo = ?", new String[]{String.valueOf(mAmortizacion.getId()), String.valueOf(prestamos.get(i).getId())});
                                    }
                                }//Termina If de Actualizado de amortizaciones

                                if (prestamos.get(i).getPagos() != null &&prestamos.get(i).getPagos().size() > 0){
                                    for (int k = 0; k < prestamos.get(i).getPagos().size(); k++){
                                        MPago mPago = prestamos.get(i).getPagos().get(k);
                                        Cursor row_pago = dBhelper.getRecords((ENVIROMENT)?TBL_PAGOS:TBL_PAGOS_T, " WHERE id_prestamo = ? AND fecha = ? AND monto = ? AND banco = ?", "",
                                                new String[]{String.valueOf(prestamos.get(i).getId()),mPago.getFecha(), mPago.getBanco(), String.valueOf(mPago.getMonto())});
                                        if (row_pago.getCount() == 0){
                                            HashMap<Integer, String> cv_pago = new HashMap<>();
                                            cv_pago.put(0, String.valueOf(prestamos.get(i).getId()));            //ID PRESTAMO
                                            cv_pago.put(1, mPago.getFecha());                                    //FECHA
                                            cv_pago.put(2, String.valueOf(mPago.getMonto()));                    //MONTO
                                            cv_pago.put(3,mPago.getBanco());                                     //BANCO
                                            cv_pago.put(4, Miscellaneous.ObtenerFecha(TIMESTAMP));               //FECHA DISPOSITIVO
                                            cv_pago.put(5, Miscellaneous.ObtenerFecha(TIMESTAMP));               //FECHA ACTUALIZADO

                                            dBhelper.savePagos(db, (ENVIROMENT)?TBL_PAGOS:TBL_PAGOS_T, cv_pago);
                                        }
                                        row_pago.close();
                                    }
                                }//Termina If de Actualizar pagos

                            }
                        } //Fin Ciclo For
                    }//Fin IF

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            //pDialog.dismiss();
            return "FIN";
        }

        @Override
        protected void onPostExecute(String s) {

            super.onPostExecute(s);
        }
    }

    public class GuardarImpresion extends AsyncTask<Object, Void, String>{

        @Override
        protected String doInBackground(final Object... params) {
            Context ctx = (Context) params[0];
            List<MSendImpresion> impresion = (List<MSendImpresion>) params[1];
            final String id_impresion = (String) params[2];
            final int tipo_impresion = (int) params[3];
            DBhelper dBhelper = new DBhelper(ctx);
            final SQLiteDatabase db = dBhelper.getWritableDatabase();
            SessionManager session = new SessionManager(ctx);

            ManagerInterface api = new RetrofitClient().generalRF(CONTROLLER_API, ctx).create(ManagerInterface.class);


            Call<List<String>> call = api.guardarImpresiones(impresion,
                    "Bearer "+ session.getUser().get(7));

            call.enqueue(new Callback<List<String>>() {
                @Override
                public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                    Log.e("Response", response.code()+"zzzz");
                    List<String> data = response.body();
                    if (response.code() == 200 && data.size() > 0) {
                        ContentValues cv = new ContentValues();
                        cv.put("sent_at", Miscellaneous.ObtenerFecha(TIMESTAMP));
                        cv.put("estatus", "1");

                        if (tipo_impresion == 1) //Vigente
                            db.update((ENVIROMENT) ? TBL_IMPRESIONES_VIGENTE : TBL_IMPRESIONES_VIGENTE_T, cv,
                                    "_id = ?", new String[]{id_impresion});
                        else if (tipo_impresion == 2) //Vencida
                            db.update((ENVIROMENT) ? TBL_IMPRESIONES_VENCIDA : TBL_IMPRESIONES_VENCIDA_T, cv,
                                    "_id = ?", new String[]{id_impresion});
                        else if (tipo_impresion == 3) //Reimpresion Vigente
                            db.update((ENVIROMENT) ? TBL_REIMPRESION_VIGENTE : TBL_REIMPRESION_VIGENTE_T, cv,
                                    "_id = ?", new String[]{id_impresion});
                    }
                }

                @Override
                public void onFailure(Call<List<String>> call, Throwable t) {
                    Log.e("xxxxxx", t.getMessage()+"  xxxxx");
                }
            });

            return "";
        }
    }

    private void GetGeolocalizadas(Context ctx){
        DBhelper dBhelper= new DBhelper(ctx);
        SQLiteDatabase db = dBhelper.getWritableDatabase();
        Cursor row;
        //                                    0               1         2           3             4               5               6             7              8                9                   10                                                                0             1         2           3             4               5               6                   7               8                9              10
        String sql = "SELECT * FROM (SELECT ci.id_cartera, ci.clave, ci.nombre, ci.direccion, ci.colonia, ci.num_solicitud, ci.geo_cliente, ci.geo_aval, ci.geo_negocio, '' AS geolocalizadas, 1 AS tipo_ficha FROM " + TBL_CARTERA_IND_T + " AS ci UNION SELECT cg.id_cartera, cg.clave, cg.nombre, cg.direccion, cg.colonia, cg.num_solicitud,  0 AS geo_cliente, 0 AS geo_aval, 0 AS geo_negocio, geolocalizadas, 2 AS tipo_ficha FROM " + TBL_CARTERA_GPO_T + " AS cg LEFT JOIN "+TBL_PRESTAMOS_GPO_T+" AS pg ON pg.id_grupo = cg.id_cartera LEFT JOIN "+TBL_MIEMBROS_GPO_T+" AS m ON m.id_prestamo = pg.id_prestamo LEFT JOIN "+TBL_GEO_RESPUESTAS_T+" AS gr ON gr.id_integrante = m.id_integrante GROUP BY cg.id_cartera, cg.clave, cg.nombre, cg.direccion, cg.colonia, cg.num_solicitud, cg.asesor_nombre ) AS geo_res";
        row = db.rawQuery(sql, null);

        if (row.getCount() > 0){
            row.moveToFirst();
            for (int i = 0; i < row.getCount(); i++){
                String sql_geo = "";
                Cursor row_geo;
                if (row.getInt(10) == 1){ //Lee individuales
                    if (row.getInt(6) == 1){ //Si ya Geolocalizó el Cliente
                        sql_geo = "SELECT * FROM " + TBL_GEO_RESPUESTAS_T + " WHERE id_cartera = ? AND tipo_geolocalizacion = ?";
                        row_geo = db.rawQuery(sql_geo, new String[]{row.getString(0), "CLIENTE"});
                        if (row_geo.getCount() == 0){
                            HashMap<Integer, String> params = new HashMap<>();
                            params.put(0, row.getString(0));
                            params.put(1, row.getString(5));
                            params.put(2, "1");
                            params.put(3, "CLIENTE");
                            params.put(4, "0");
                            params.put(5, row.getString(2));
                            params.put(6, "");
                            params.put(7, "0");
                            params.put(8, "0");
                            params.put(9, "");
                            params.put(10, "");
                            params.put(11, "");
                            params.put(12, "");
                            params.put(13, Miscellaneous.ObtenerFecha(TIMESTAMP));
                            params.put(14, Miscellaneous.ObtenerFecha(TIMESTAMP));
                            params.put(15, "1");
                            params.put(16, "0");

                            dBhelper.saveGeoRespuestas(db, params);
                        }
                        row_geo.close();
                    }
                    if (row.getInt(7) == 1){ //Si ya Geolocalizó el Aval
                        sql_geo = "SELECT * FROM " + TBL_GEO_RESPUESTAS_T + " WHERE id_cartera = ? AND tipo_geolocalizacion = ?";
                        row_geo = db.rawQuery(sql_geo, new String[]{row.getString(0), "AVAL"});
                        if (row_geo.getCount() == 0) {
                            HashMap<Integer, String> params = new HashMap<>();
                            params.put(0, row.getString(0));
                            params.put(1, row.getString(5));
                            params.put(2, "1");
                            params.put(3, "AVAL");
                            params.put(4, "0");
                            params.put(5, row.getString(2));
                            params.put(6, "");
                            params.put(7, "0");
                            params.put(8, "0");
                            params.put(9, "");
                            params.put(10, "");
                            params.put(11, "");
                            params.put(12, "");
                            params.put(13, Miscellaneous.ObtenerFecha(TIMESTAMP));
                            params.put(14, Miscellaneous.ObtenerFecha(TIMESTAMP));
                            params.put(15, "1");
                            params.put(16, "0");

                            dBhelper.saveGeoRespuestas(db, params);
                        }
                        row_geo.close();
                    }

                    if (row.getInt(8) == 1){ //Si ya Geolocalizó el Negocio
                        sql_geo = "SELECT * FROM " + TBL_GEO_RESPUESTAS_T + " WHERE id_cartera = ? AND tipo_geolocalizacion = ?";
                        row_geo = db.rawQuery(sql_geo, new String[]{row.getString(0), "NEGOCIO"});
                        if (row_geo.getCount() == 0) {
                            HashMap<Integer, String> params = new HashMap<>();
                            params.put(0, row.getString(0));
                            params.put(1, row.getString(5));
                            params.put(2, "1");
                            params.put(3, "NEGOCIO");
                            params.put(4, "0");
                            params.put(5, row.getString(2));
                            params.put(6, "");
                            params.put(7, "0");
                            params.put(8, "0");
                            params.put(9, "");
                            params.put(10, "");
                            params.put(11, "");
                            params.put(12, "");
                            params.put(13, Miscellaneous.ObtenerFecha(TIMESTAMP));
                            params.put(14, Miscellaneous.ObtenerFecha(TIMESTAMP));
                            params.put(15, "1");
                            params.put(16, "0");

                            dBhelper.saveGeoRespuestas(db, params);
                        }
                        row_geo.close();
                    }
                }
                else{ //Lee grupales
                    if (row.getString(9).length()>2) {
                        String[] integrantes = row.getString(9).replace("{", "").replace("}", "").split(",");
                        if (integrantes.length > 0) {
                            for (int j = 0; j < integrantes.length; j++) {
                                sql_geo = "SELECT * FROM " + TBL_GEO_RESPUESTAS_T + " WHERE id_cartera = ? AND id_integrante = ?";
                                row_geo = db.rawQuery(sql_geo, new String[]{row.getString(0),integrantes[j] });
                                if (row_geo.getCount() == 0){
                                    Cursor row_integrante;
                                    String sql_integrante = "SELECT * FROM " + TBL_MIEMBROS_GPO_T + " WHERE id_integrante = ?";
                                    row_integrante = db.rawQuery(sql_integrante, new String[]{integrantes[j]});
                                    Log.e("Id_integrante", "xxx:"+integrantes[j]);
                                    if (row_integrante.getCount() > 0) {
                                        row_integrante.moveToFirst();
                                        HashMap<Integer, String> params = new HashMap<>();
                                        params.put(0, row.getString(0));
                                        params.put(1, row.getString(5));
                                        params.put(2, "2");
                                        params.put(3, row_integrante.getString(9));
                                        params.put(4, integrantes[j]);
                                        params.put(5, "");
                                        params.put(6, "");
                                        params.put(7, "0");
                                        params.put(8, "0");
                                        params.put(9, "");
                                        params.put(10, "");
                                        params.put(11, "");
                                        params.put(12, "");
                                        params.put(13, Miscellaneous.ObtenerFecha(TIMESTAMP));
                                        params.put(14, Miscellaneous.ObtenerFecha(TIMESTAMP));
                                        params.put(15, "1");
                                        params.put(16, row_integrante.getString(14));

                                        dBhelper.saveGeoRespuestas(db, params);
                                    }
                                    row_integrante.close();

                                }
                                row_geo.close();
                            }
                        }
                    }

                }
                row.moveToNext();
            }
        }
        row.close();
    }

}
