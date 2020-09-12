package com.sidert.sidertmovil.utils;


import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.GsonBuilder;
import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.database.DBhelper;
import com.sidert.sidertmovil.models.MAmortizacion;
import com.sidert.sidertmovil.models.MAval;
import com.sidert.sidertmovil.models.MCierreDia;
import com.sidert.sidertmovil.models.MGestionCancelada;
import com.sidert.sidertmovil.models.MIntegrante;
import com.sidert.sidertmovil.models.MPago;
import com.sidert.sidertmovil.models.MPrestamoGpoRes;
import com.sidert.sidertmovil.models.MPrestamoRes;
import com.sidert.sidertmovil.models.MResCierreDia;
import com.sidert.sidertmovil.models.MResRecibo;
import com.sidert.sidertmovil.models.MResSaveOriginacionInd;
import com.sidert.sidertmovil.models.MResSaveSolicitud;
import com.sidert.sidertmovil.models.MResSoporte;
import com.sidert.sidertmovil.models.MResTicket;
import com.sidert.sidertmovil.models.MResUltimoRecibo;
import com.sidert.sidertmovil.models.MRespGestionadas;
import com.sidert.sidertmovil.models.MResponseTracker;
import com.sidert.sidertmovil.models.MRespuestaGestion;
import com.sidert.sidertmovil.models.MRespuestaSolicitud;
import com.sidert.sidertmovil.models.MSendImpresion;
import com.sidert.sidertmovil.models.MSendRecibo;
import com.sidert.sidertmovil.models.MSendSoporte;
import com.sidert.sidertmovil.models.MSolicitudCancelacion;
import com.sidert.sidertmovil.models.MTracker;
import com.sidert.sidertmovil.models.ModeloGeolocalizacion;
import com.sidert.sidertmovil.models.ModeloResSaveGeo;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.sidert.sidertmovil.utils.Constants.*;

public class Servicios_Sincronizado {

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
        loading.setCancelable(false);
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


                final File image = new File(ROOT_PATH + "Fachada/"+respuesta.getString(FACHADA));

                RequestBody latBody = RequestBody.create(MultipartBody.FORM, respuesta.getString(LATITUD));
                RequestBody lngBody = RequestBody.create(MultipartBody.FORM, respuesta.getString(LONGITUD));
                RequestBody direccionBody = RequestBody.create(MultipartBody.FORM, respuesta.getString(DIRECCION));
                RequestBody barcodeBody = RequestBody.create(MultipartBody.FORM, respuesta.getString(CODEBARS));
                RequestBody fechaDispositivoBody = RequestBody.create(MultipartBody.FORM, respuesta.getString(FECHA_DISPOSITIVO));
                RequestBody fechaGestionIniBody = RequestBody.create(MultipartBody.FORM, respuesta.getString(FECHA_INI_GEO));
                RequestBody fechaGestionFinBody = RequestBody.create(MultipartBody.FORM, respuesta.getString(FECHA_FIN_GEO));
                RequestBody fechaEnvioBody = RequestBody.create(MultipartBody.FORM, Miscellaneous.ObtenerFecha(TIMESTAMP));
                RequestBody comentarioBody = RequestBody.create(MultipartBody.FORM, respuesta.getString(COMENTARIO));
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
                        //Log.e("FailSaveImage", t.getMessage());
                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    public void SaveRespuestaGestion(Context ctx, boolean showDG){

        final AlertDialog loading = Popups.showLoadingDialog(ctx, R.string.please_wait, R.string.loading_info);
        loading.setCancelable(false);

        //if ((!((Activity) ctx).isFinishing())) {
            if (showDG)
                loading.show();
        //}
        SessionManager session = new SessionManager(ctx);
        final DBhelper dBhelper = new DBhelper(ctx);
        final SQLiteDatabase db = dBhelper.getWritableDatabase();

        Cursor row;

        String query;

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

                    Log.e("res_envio", json_res.toString());

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

        //if ((!((Activity) ctx).isFinishing())) {
            if (showDG)
                loading.dismiss();
        //}

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
                    imagen_evidencia = new File(ROOT_PATH + "Fachada/" + imagen);
                    break;
                case "1":
                case "2":
                    imagen_evidencia = new File(ROOT_PATH + "Evidencia/" + imagen);
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
            final File image_firma = new File(ROOT_PATH + "Firma/"+firma);
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
                    //Log.e("FailSaveGestion", t.getMessage());
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

        Cursor row = dBhelper.getRecords(TBL_SOLICITUDES, " WHERE tipo_solicitud = 1 AND estatus = 1", "", null);

        if (row.getCount() > 0){
            row.moveToFirst();

            Log.e("count solicitudes", row.getCount()+" total");
            for (int i = 0; i < row.getCount(); i++){
                Cursor row_soli = dBhelper.getRecords(TBL_CREDITO_IND, " WHERE id_solicitud = ?", "", new String[]{row.getString(0)});

                    row_soli.moveToFirst();
                    JSONObject json_solicitud = new JSONObject();
                    try {
                        Log.e("Plazo", row_soli.getString(2)+" plazo");
                        json_solicitud.put(K_PLAZO, Miscellaneous.GetPlazo(row_soli.getString(2)));
                        json_solicitud.put(K_PERIODICIDAD, Miscellaneous.GetPeriodicidad(row_soli.getString(3)));
                        json_solicitud.put(K_FECHA_DESEMBOLSO, row_soli.getString(4));
                        json_solicitud.put(K_HORA_VISITA, row_soli.getString(6));
                        json_solicitud.put(K_MONTO_PRESTAMO, Integer.parseInt(row_soli.getString(7).replace(",","")));
                        int montoPres = Integer.parseInt(row_soli.getString(7).replace(",",""));
                        json_solicitud.put(K_MONTO_LETRA, (Miscellaneous.cantidadLetra(row_soli.getString(7).replace(",","")).toUpperCase() + " PESOS MEXICANOS").replace("  ", " "));
                        json_solicitud.put(K_DESTINO_PRESTAMO, row_soli.getString(8));
                        json_solicitud.put(K_CLASIFICACION_RIESGO, row_soli.getString(9));
                        row_soli.close();//Cierra datos de credito
                        json_solicitud.put(K_FECHA_INICIO, row.getString(6));
                        json_solicitud.put(K_FECHA_TERMINO, row.getString(7));
                        json_solicitud.put(K_FECHA_ENVIO, Miscellaneous.ObtenerFecha(TIMESTAMP));

                        row_soli = dBhelper.getRecords(TBL_CLIENTE_IND, " WHERE id_solicitud = ?", "", new String[]{row.getString(0)});
                        row_soli.moveToFirst();
                        JSONObject json_solicitante = new JSONObject();
                        json_solicitante.put(K_NOMBRE, row_soli.getString(2));
                        json_solicitante.put(K_PATERNO, row_soli.getString(3));
                        json_solicitante.put(K_MATERNO, row_soli.getString(4));
                        json_solicitante.put(K_FECHA_NACIMIENTO, row_soli.getString(5));
                        json_solicitante.put(K_EDAD, row_soli.getInt(6));
                        json_solicitante.put(K_GENERO, row_soli.getInt(7));
                        json_solicitante.put(K_ESTADO_NACIMIENTO, row_soli.getString(8));
                        json_solicitante.put(K_RFC, row_soli.getString(9));
                        json_solicitante.put(K_CURP, row_soli.getString(10) + row_soli.getString(11));
                        json_solicitante.put(K_OCUPACION, row_soli.getString(12));
                        json_solicitante.put(K_ACTIVIDAD_ECONOMICA, row_soli.getString(13));
                        json_solicitante.put(K_IDENTIFICACION_TIPO, row_soli.getString(14));
                        json_solicitante.put(K_NO_IDENTIFICACION, row_soli.getString(15));
                        json_solicitante.put(K_NIVEL_ESTUDIO, row_soli.getString(16));
                        String estadoCivil = row_soli.getString(17);
                        json_solicitante.put(K_ESTADO_CIVIL, row_soli.getString(17));
                        if (row_soli.getString(17).equals("CASADO(A)"))
                            json_solicitante.put(K_BIENES, (row_soli.getInt(18) == 1)?"MANCOMUNADOS":"SEPARADOS");
                        json_solicitante.put(K_TIPO_VIVIENDA, row_soli.getString(19));
                        if (row_soli.getString(19).equals("CASA FAMILIAR"))
                            json_solicitante.put(K_PARENTESCO, row_soli.getString(20));
                        else if (row_soli.getString(19).equals("OTRO"))
                            json_solicitante.put(K_OTRO_TIPO_VIVIENDA, row_soli.getString(21));
                        Cursor row_dir = dBhelper.getRecords(TBL_DIRECCIONES, " WHERE id_direccion = ? AND tipo_direccion = ?", "", new String[]{row_soli.getString(22), "CLIENTE"});
                        row_dir.moveToFirst();
                        json_solicitante.put(K_LATITUD, row_dir.getString(2));
                        json_solicitante.put(K_LONGITUD, row_dir.getString(3));
                        json_solicitante.put(K_CALLE, row_dir.getString(4));
                        json_solicitante.put(K_NO_EXTERIOR, row_dir.getString(5));
                        json_solicitante.put(K_NO_INTERIOR, row_dir.getString(6));
                        json_solicitante.put(K_NO_LOTE, row_dir.getString(7));
                        json_solicitante.put(K_NO_MANZANA, row_dir.getString(8));
                        json_solicitante.put(K_CODIGO_POSTAL, row_dir.getInt(9));
                        json_solicitante.put(K_COLONIA, row_dir.getString(10));
                        json_solicitante.put(K_CIUDAD, row_dir.getString(11));
                        json_solicitante.put(K_LOCALIDAD, row_dir.getString(12));
                        json_solicitante.put(K_MUNICIPIO,row_dir.getString(13));
                        json_solicitante.put(K_ESTADO, row_dir.getString(14));
                        row_dir.close();
                        json_solicitante.put(K_TEL_CASA, row_soli.getString(23));
                        json_solicitante.put(K_TEL_CELULAR, row_soli.getString(24));
                        json_solicitante.put(K_TEL_MENSAJE, row_soli.getString(25));
                        json_solicitante.put(K_TEL_TRABAJO, row_soli.getString(26));
                        json_solicitante.put(K_TIEMPO_VIVIR_SITIO, row_soli.getInt(27));
                        json_solicitante.put(K_DEPENDIENTES_ECONOMICO, row_soli.getInt(28));
                        json_solicitante.put(K_MEDIO_CONTACTO, row_soli.getString(29));
                        json_solicitante.put(K_ESTADO_CUENTA, row_soli.getString(30));
                        json_solicitante.put(K_EMAIL, row_soli.getString(31));
                        json_solicitante.put(K_FOTO_FACHADA, row_soli.getString(32));
                        json_solicitante.put(K_REFERENCIA_DOMICILIARIA, row_soli.getString(33));
                        json_solicitante.put(K_FIRMA, row_soli.getString(34));

                        json_solicitud.put(K_SOLICITANTE, json_solicitante);
                        String fachadaCli = row_soli.getString(32);
                        String firmaCli = row_soli.getString(34);
                        row_soli.close(); //Cierra datos del solicitante

                        Log.e("solicitante", json_solicitante.toString());

                        if (estadoCivil.equals("CASADO(A)") ||
                            estadoCivil.equals("UNIÓN LIBRE")) {
                            JSONObject json_conyuge = new JSONObject();
                            row_soli = dBhelper.getRecords(TBL_CONYUGE_IND, " WHERE id_solicitud = ?", "", new String[]{row.getString(0)});
                            row_soli.moveToFirst();
                            json_conyuge.put(K_NOMBRE, row_soli.getString(2));
                            json_conyuge.put(K_PATERNO, row_soli.getString(3));
                            json_conyuge.put(K_MATERNO, row_soli.getString(4));
                            json_conyuge.put(K_NACIONALIDAD, row_soli.getString(5));
                            json_conyuge.put(K_OCUPACION, row_soli.getString(6));

                            row_dir = dBhelper.getRecords(TBL_DIRECCIONES, " WHERE id_direccion = ? AND tipo_direccion = ?", "", new String[]{row_soli.getString(7), "CONYUGE"});
                            row_dir.moveToFirst();
                            json_conyuge.put(K_CALLE, row_dir.getString(4));
                            json_conyuge.put(K_NO_EXTERIOR,row_dir.getString(5));
                            json_conyuge.put(K_NO_INTERIOR, row_dir.getString(6));
                            json_conyuge.put(K_NO_LOTE, row_dir.getString(7));
                            json_conyuge.put(K_NO_MANZANA, row_dir.getString(8));
                            json_conyuge.put(K_CODIGO_POSTAL, row_dir.getInt(9));
                            json_conyuge.put(K_COLONIA, row_dir.getString(10));
                            json_conyuge.put(K_CIUDAD, row_dir.getString(11));
                            json_conyuge.put(K_LOCALIDAD,row_dir.getString(12));
                            json_conyuge.put(K_MUNICIPIO,row_dir.getString(13));
                            json_conyuge.put(K_ESTADO, row_dir.getString(14));
                            row_dir.close();
                            json_conyuge.put(K_INGRESO_MENSUAL, Double.parseDouble(row_soli.getString(8).replace(",","")));
                            json_conyuge.put(K_GASTO_MENSUAL, Double.parseDouble(row_soli.getString(9).replace(",","")));
                            json_conyuge.put(K_TEL_CASA, row_soli.getString(10));
                            json_conyuge.put(K_TEL_CELULAR, row_soli.getString(11));

                            json_solicitud.put(K_SOLICITANTE_CONYUGE, json_conyuge);
                            row_soli.close(); //Cierra datos de conyuge

                            Log.e("conyuge", json_conyuge.toString());
                        }

                        Log.e("Solicituf", json_solicitud.toString());
                        if (montoPres >= 30000){
                            row_soli = dBhelper.getRecords(TBL_ECONOMICOS_IND, " WHERE id_solicitud = ?", "", new String[]{row.getString(0)});
                            row_soli.moveToFirst();
                            JSONObject json_economicos = new JSONObject();
                            json_economicos.put(K_PROPIEDADES, row_soli.getString(2));
                            json_economicos.put(K_VALOR_APROXIMADO, row_soli.getString(3));
                            json_economicos.put(K_UBICACION, row_soli.getString(4));
                            json_economicos.put(K_INGRESO, row_soli.getString(5).replace(",",""));

                            json_solicitud.put(K_SOLICITANTE_DATOS_ECONOMICOS, json_economicos);
                            row_soli.close(); //Cierra datos economicos

                            Log.e("economicos", json_economicos.toString());
                        }



                        row_soli = dBhelper.getRecords(TBL_NEGOCIO_IND, " WHERE id_solicitud = ?", "", new String[]{row.getString(0)});
                        row_soli.moveToFirst();
                        JSONObject json_negocio = new JSONObject();
                        json_negocio.put(K_NOMBRE, row_soli.getString(2));

                        row_dir = dBhelper.getRecords(TBL_DIRECCIONES, " WHERE id_direccion = ? AND tipo_direccion = ?", "", new String[]{row_soli.getString(3), "NEGOCIO"});
                        row_dir.moveToFirst();
                        json_negocio.put(K_LATITUD, row_dir.getString(2));
                        json_negocio.put(K_LONGITUD, row_dir.getString(3));
                        json_negocio.put(K_CALLE, row_dir.getString(4));
                        json_negocio.put(K_NO_EXTERIOR, row_dir.getString(5));
                        json_negocio.put(K_NO_INTERIOR, row_dir.getString(6));
                        json_negocio.put(K_NO_LOTE, row_dir.getString(7));
                        json_negocio.put(K_NO_MANZANA, row_dir.getString(8));
                        json_negocio.put(K_CODIGO_POSTAL, row_dir.getInt(9));
                        json_negocio.put(K_COLONIA, row_dir.getString(10));
                        json_negocio.put(K_CIUDAD, row_dir.getString(11));
                        json_negocio.put(K_LOCALIDAD, row_dir.getString(12));
                        json_negocio.put(K_MUNICIPIO, row_dir.getString(13));
                        json_negocio.put(K_ESTADO, row_dir.getString(14));
                        row_dir.close(); //Cierra datos de direccion del negocio

                        json_negocio.put(K_OCUPACION, row_soli.getString(4));
                        json_negocio.put(K_ACTIVIDAD_ECONOMICA, row_soli.getString(5));
                        json_negocio.put(K_DESTINO_CREDITO, row_soli.getString(6));
                        if (row_soli.getString(6).contains("OTRO"))
                            json_negocio.put(K_OTRO_DESTINO_CREDITO, row_soli.getString(7));
                        json_negocio.put(K_ANTIGUEDAD, row_soli.getString(8));
                        json_negocio.put(K_INGRESO_MENSUAL, row_soli.getString(9).replace(",",""));
                        json_negocio.put(K_INGRESOS_OTROS, row_soli.getString(10).replace(",",""));
                        json_negocio.put(K_GASTO_MENSUAL, row_soli.getString(11).replace(",",""));
                        json_negocio.put(K_GASTO_AGUA, row_soli.getString(12).replace(",",""));
                        json_negocio.put(K_GASTO_LUZ, row_soli.getString(13).replace(",",""));
                        json_negocio.put(K_GASTO_TELEFONO, row_soli.getString(14).replace(",",""));
                        json_negocio.put(K_GASTO_RENTA, row_soli.getString(15).replace(",",""));
                        json_negocio.put(K_GASTO_OTROS, row_soli.getString(16).replace(",",""));
                        json_negocio.put(K_CAPACIDAD_PAGO, row_soli.getString(17).replace(",",""));
                        String aux = "";
                        if (!row_soli.getString(18).trim().isEmpty()){
                            String[] medios = row_soli.getString(18).split(",");

                            if (medios.length > 0){
                                for (int m = 0; m < medios.length; m++){
                                    if (m == 0)
                                        aux = "'"+medios[m].trim()+"'";
                                    else
                                        aux += ","+"'"+medios[m].trim()+"'";
                                }
                            }
                        }

                        String sql = "SELECT * FROM " + TBL_MEDIOS_PAGO_ORI + " WHERE nombre IN ("+aux+")";
                        Cursor row_medio_pago  = db.rawQuery(sql, null);
                        if (row_medio_pago.getCount() > 0){
                            row_medio_pago.moveToFirst();
                            String medios_pagos_ids = "";
                            for(int k = 0; k < row_medio_pago.getCount(); k++){
                                if (k == 0)
                                    medios_pagos_ids = row_medio_pago.getString(1).trim();
                                else
                                    medios_pagos_ids += ","+row_medio_pago.getString(1).trim();

                                row_medio_pago.moveToNext();
                            }
                            json_negocio.put(K_MEDIOS_PAGOS, medios_pagos_ids);
                        }
                        else{
                            json_negocio.put(K_MEDIOS_PAGOS, "");
                        }
                        row_medio_pago.close();

                        if (row_soli.getString(18).contains("OTRO"))
                            json_negocio.put(K_OTRO_MEDIOS_PAGOS, row_soli.getString(19));

                        json_negocio.put(K_MONTO_MAXIMO, row_soli.getString(20).replace(",",""));

                        json_negocio.put(K_NUM_OPERACIONES_MENSUAL, row_soli.getInt(21));

                        if (row_soli.getString(18).contains("EFECTIVO"))
                            json_negocio.put(K_NUM_OPERACIONES_MENSUAL_EFECTIVO, row_soli.getInt(22));

                        json_negocio.put(K_DIAS_VENTA, row_soli.getString(23));
                        json_negocio.put(K_FOTO_FACHADA, row_soli.getString(24));
                        String fachadaNeg = row_soli.getString(24);
                        json_negocio.put(K_REFERENCIA_NEGOCIO, row_soli.getString(25));

                        json_solicitud.put(K_SOLICITANTE_NEGOCIO, json_negocio);
                        row_soli.close(); // Cierra datos del negocio

                        Log.e("negocio", json_negocio.toString());

                        row_soli = dBhelper.getRecords(TBL_AVAL_IND, " WHERE id_solicitud = ?", "", new String[]{row.getString(0)});
                        row_soli.moveToFirst();
                        JSONObject json_aval = new JSONObject();
                        json_aval.put(K_NOMBRE, row_soli.getString(2));
                        json_aval.put(K_PATERNO, row_soli.getString(3));
                        json_aval.put(K_MATERNO, row_soli.getString(4));
                        json_aval.put(K_FECHA_NACIMIENTO, row_soli.getString(5));
                        json_aval.put(K_EDAD, row_soli.getInt(6));
                        json_aval.put(K_GENERO, row_soli.getInt(7));
                        json_aval.put(K_ESTADO_NACIMIENTO, row_soli.getString(8));
                        json_aval.put(K_RFC, row_soli.getString(9));
                        json_aval.put(K_CURP, row_soli.getString(10)+row_soli.getString(11));
                        json_aval.put(K_PARENTESCO_SOLICITANTE, row_soli.getString(12));
                        json_aval.put(K_IDENTIFICACION_TIPO, row_soli.getString(13));
                        json_aval.put(K_NO_IDENTIFICACION, row_soli.getString(14));
                        json_aval.put(K_OCUPACION, row_soli.getString(15));
                        json_aval.put(K_ACTIVIDAD_ECONOMICA, row_soli.getString(16));

                        row_dir = dBhelper.getRecords(TBL_DIRECCIONES, " WHERE id_direccion = ? AND tipo_direccion = ?", "", new String[]{row_soli.getString(19), "AVAL"});
                        row_dir.moveToFirst();
                        json_aval.put(K_LATITUD, row_dir.getString(2));
                        json_aval.put(K_LONGITUD, row_dir.getString(3));
                        json_aval.put(K_CALLE, row_dir.getString(4));
                        json_aval.put(K_NO_EXTERIOR, row_dir.getString(5));
                        json_aval.put(K_NO_INTERIOR, row_dir.getString(6));
                        json_aval.put(K_NO_LOTE, row_dir.getString(7));
                        json_aval.put(K_NO_MANZANA, row_dir.getString(8));
                        json_aval.put(K_CODIGO_POSTAL, row_dir.getInt(9));
                        json_aval.put(K_COLONIA, row_dir.getString(10));
                        json_aval.put(K_CIUDAD,row_dir.getString(11));
                        json_aval.put(K_LOCALIDAD, row_dir.getString(12));
                        json_aval.put(K_MUNICIPIO,row_dir.getString(13));
                        json_aval.put(K_ESTADO, row_dir.getString(14));
                        row_dir.close();

                        json_aval.put(K_TIPO_VIVIENDA, row_soli.getString(20));
                        if (row_soli.getString(20).equals("CASA FAMILIAR") || row_soli.getString(20).equals("CASA RENTADA")){
                            json_aval.put(K_NOMBRE_TITULAR, row_soli.getString(21));
                            json_aval.put(K_PARENTESCO_TITULAR, row_soli.getString(22));
                        }
                        json_aval.put(K_CARACTERISTICAS_DOMICILIO, row_soli.getString(23));
                        json_aval.put(K_TIENE_NEGOCIO, row_soli.getInt(25) == 1);
                        if (row_soli.getInt(25) == 1)
                            json_aval.put(K_NOMBRE_NEGOCIO, row_soli.getString(26).trim().toUpperCase());
                        json_aval.put(K_INGRESO_MENSUAL, Double.parseDouble(row_soli.getString(27).replace(",","")));
                        json_aval.put(K_INGRESOS_OTROS, Double.parseDouble(row_soli.getString(28).replace(",","")));
                        json_aval.put(K_GASTO_MENSUAL, Double.parseDouble(row_soli.getString(29).replace(",","")));
                        json_aval.put(K_GASTO_AGUA, Double.parseDouble(row_soli.getString(30).replace(",","")));
                        json_aval.put(K_GASTO_LUZ, Double.parseDouble(row_soli.getString(31).replace(",","")));
                        json_aval.put(K_GASTO_TELEFONO, Double.parseDouble(row_soli.getString(32).replace(",","")));
                        json_aval.put(K_GASTO_RENTA, Double.parseDouble(row_soli.getString(33).replace(",","")));
                        json_aval.put(K_GASTO_OTROS, Double.parseDouble(row_soli.getString(34).replace(",","")));
                        json_aval.put(K_MONTO_MAXIMO, Double.parseDouble(row_soli.getString(38).replace(",","")));
                        json_aval.put(K_CAPACIDAD_PAGO, Double.parseDouble(row_soli.getString(35).replace(",","")));

                        aux = "";
                        if (!row_soli.getString(36).trim().isEmpty()){
                            String[] medios = row_soli.getString(36).split(",");

                            if (medios.length > 0){
                                for (int m = 0; m < medios.length; m++){
                                    if (m == 0)
                                        aux = "'"+medios[m].trim()+"'";
                                    else
                                        aux += ","+"'"+medios[m].trim()+"'";
                                }
                            }
                        }

                        sql = "SELECT * FROM " + TBL_MEDIOS_PAGO_ORI + " WHERE nombre IN ("+aux+")";
                        row_medio_pago  = db.rawQuery(sql, null);
                        if (row_medio_pago.getCount() > 0){
                            row_medio_pago.moveToFirst();
                            String medios_pagos_ids = "";
                            for(int k = 0; k < row_medio_pago.getCount(); k++){
                                if (k == 0)
                                    medios_pagos_ids = row_medio_pago.getString(1).trim();
                                else
                                    medios_pagos_ids += ","+row_medio_pago.getString(1).trim();

                                row_medio_pago.moveToNext();
                            }
                            json_aval.put(K_MEDIOS_PAGOS, medios_pagos_ids);
                        }
                        else{
                            json_aval.put(K_MEDIOS_PAGOS, "");
                        }
                        row_medio_pago.close();

                        if (row_soli.getString(36).contains("OTRO"))
                            json_aval.put(K_OTRO_MEDIOS_PAGOS, row_soli.getString(37));

                        json_aval.put(K_HORA_LOCALIZACION, row_soli.getString(39));
                        json_aval.put(K_ACTIVOS_OBSERVABLES, row_soli.getString(40));
                        json_aval.put(K_ANTIGUEDAD, row_soli.getInt(24));
                        json_aval.put(K_TEL_CASA, row_soli.getString(41));
                        json_aval.put(K_TEL_CELULAR, row_soli.getString(42));
                        json_aval.put(K_TEL_MENSAJE, row_soli.getString(43));
                        json_aval.put(K_TEL_TRABAJO, row_soli.getString(44));
                        json_aval.put(K_EMAIL, row_soli.getString(45));
                        json_aval.put(K_FOTO_FACHADA, row_soli.getString(46));
                        String fachadaAval = row_soli.getString(46);
                        json_aval.put(K_REFERENCIA_DOMICILIARIA, row_soli.getString(47));
                        json_aval.put(K_FIRMA, row_soli.getString(48));
                        String firmaAval = row_soli.getString(48);

                        json_solicitud.put(K_SOLICITANTE_AVAL, json_aval);

                        row_soli.close(); //Cierre de datos del aval

                        Log.e("aval", json_aval.toString());

                        row_soli = dBhelper.getRecords(TBL_REFERENCIA_IND, " WHERE id_solicitud = ?", "", new String[]{row.getString(0)});
                        row_soli.moveToFirst();

                        JSONObject json_referencia = new JSONObject();
                        json_referencia.put(K_NOMBRE, row_soli.getString(2));
                        json_referencia.put(K_PATERNO, row_soli.getString(3));
                        json_referencia.put(K_MATERNO, row_soli.getString(4));
                        json_referencia.put(K_FECHA_NACIMIENTO, row_soli.getString(5));

                        row_dir = dBhelper.getRecords(TBL_DIRECCIONES, " WHERE id_direccion = ? AND tipo_direccion = ?", "", new String[]{row_soli.getString(6), "REFERENCIA"});
                        row_dir.moveToFirst();
                        json_referencia.put(K_CALLE, row_dir.getString(4));
                        json_referencia.put(K_NO_EXTERIOR, row_dir.getString(5));
                        json_referencia.put(K_NO_INTERIOR, row_dir.getString(6));
                        json_referencia.put(K_NO_LOTE, row_dir.getString(7));
                        json_referencia.put(K_NO_MANZANA, row_dir.getString(8));
                        json_referencia.put(K_CODIGO_POSTAL, row_dir.getInt(9));
                        json_referencia.put(K_COLONIA, row_dir.getString(10));
                        json_referencia.put(K_CIUDAD,row_dir.getString(11));
                        json_referencia.put(K_LOCALIDAD, row_dir.getString(12));
                        json_referencia.put(K_MUNICIPIO,row_dir.getString(13));
                        json_referencia.put(K_ESTADO, row_dir.getString(14));
                        row_dir.close();//Cierre de datos de referencia
                        json_referencia.put(K_TEL_CELULAR, row_soli.getString(7));

                        json_solicitud.put(K_SOLICITANTE_REFERENCIA, json_referencia);
                        row_soli.close(); //Cierre de datos de referencia

                        Log.e("referencia", json_referencia.toString());

                        row_soli = dBhelper.getRecords(TBL_CROQUIS_IND, " WHERE id_solicitud = ?", "", new String[]{row.getString(0)});
                        row_soli.moveToFirst();
                        JSONObject json_croquis = new JSONObject();
                        json_croquis.put(K_CALLE_ENFRENTE, row_soli.getString(2));
                        json_croquis.put(K_CALLE_LATERAL_IZQ, row_soli.getString(3));
                        json_croquis.put(K_CALLE_LATERAL_DER, row_soli.getString(4));
                        json_croquis.put(K_CALLE_ATRAS, row_soli.getString(5));
                        json_croquis.put(K_REFERENCIAS, row_soli.getString(6));

                        json_solicitud.put(K_SOLICITANTE_CROQUIS, json_croquis);
                        row_soli.close(); //Cierra datos del croquis

                        row_soli = dBhelper.getRecords(TBL_POLITICAS_PLD_IND, " WHERE id_solicitud = ?", "", new String[]{row.getString(0)});
                        row_soli.moveToFirst();
                        JSONObject json_politicas = new JSONObject();
                        json_politicas.put(K_PROPIETARIO, row_soli.getInt(2) == 1);
                        json_politicas.put(K_PROVEEDOR_RECURSOS, row_soli.getInt(3) == 1);
                        json_politicas.put(K_POLITICAMENTE_EXP, row_soli.getInt(4) == 1);
                        json_solicitud.put(K_SOLICITANTE_POLITICAS, json_politicas);
                        row_soli.close(); //Cierra datos de politicas pld


                        row_soli = dBhelper.getRecords(TBL_DOCUMENTOS, " WHERE id_solicitud = ?", "", new String[]{row.getString(0)});
                        row_soli.moveToFirst();
                        JSONObject json_documentos = new JSONObject();
                        json_documentos.put(K_IDENTIFICACION_FRONTAL, row_soli.getString(2));
                        String identiFrontal = row_soli.getString(2);
                        json_documentos.put(K_IDENTIFICACION_REVERSO, row_soli.getString(3));
                        String identiReverso = row_soli.getString(3);
                        json_documentos.put(K_CURP, row_soli.getString(4));
                        String curp = row_soli.getString(4);
                        json_documentos.put(K_COMPROBANTE_DOMICILIO, row_soli.getString(5));
                        String comprobante = row_soli.getString(5);
                        json_documentos.put(K_CODIGO_BARRAS, row_soli.getString(6));
                        json_documentos.put(K_FIRMA_ASESOR, row_soli.getString(7));
                        String firmaAsesor = row_soli.getString(7);

                        json_solicitud.put(K_SOLICITANTE_DOCUMENTOS, json_documentos);

                        row_soli.close(); //Cierre de datos de documentos

                        Log.e("documentos", json_documentos.toString());
                        new GuardarSolicitudInd().execute(ctx, json_solicitud, fachadaCli, firmaCli, fachadaNeg, fachadaAval, firmaAval,identiFrontal, identiReverso, curp,comprobante, firmaAsesor, row.getString(0));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                row.moveToNext();
            }
        }

    }

    public void SendOriginacionGpo (Context ctx, boolean flag){
        final AlertDialog loading = Popups.showLoadingDialog(ctx, R.string.please_wait, R.string.loading_info);

        if (flag)
            loading.show();
        SessionManager session = new SessionManager(ctx);
        final DBhelper dBhelper = new DBhelper(ctx);
        final SQLiteDatabase db = dBhelper.getWritableDatabase();

        String sql = "SELECT s.*, c.* FROM " + TBL_SOLICITUDES + " AS s INNER JOIN " +TBL_CREDITO_GPO + " AS c ON s.id_solicitud = c.id_solicitud WHERE s.tipo_solicitud = 2 AND s.estatus = 1";
        Cursor row = db.rawQuery(sql, null);
        //Cursor row = dBhelper.getRecords(TBL_SOLICITUDES, " WHERE tipo_solicitud = 2", "", null);


        if (row.getCount() > 0){
            row.moveToFirst();

            for (int i = 0; i < row.getCount(); i++){
                String sqlIntegrante = "SELECT i.*, t.*, d.*, n.*, c.*, o.*, docu.*, p.*, cro.* FROM " + TBL_INTEGRANTES_GPO + " AS i " +
                        "INNER JOIN " + TBL_TELEFONOS_INTEGRANTE + " AS t ON i.id = t.id_integrante " +
                        "INNER JOIN " + TBL_DOMICILIO_INTEGRANTE + " AS d ON i.id = d.id_integrante " +
                        "INNER JOIN " + TBL_NEGOCIO_INTEGRANTE + " AS n ON i.id = n.id_integrante " +
                        "INNER JOIN " + TBL_CONYUGE_INTEGRANTE + " AS c ON i.id = c.id_integrante " +
                        "INNER JOIN " + TBL_OTROS_DATOS_INTEGRANTE + " AS o ON i.id = o.id_integrante " +
                        "INNER JOIN " + TBL_CROQUIS_GPO + " AS cro ON i.id = cro.id_integrante " +
                        "INNER JOIN " + TBL_POLITICAS_PLD_INTEGRANTE + " AS p ON i.id = p.id_integrante " +
                        "INNER JOIN " + TBL_DOCUMENTOS_INTEGRANTE + " AS docu ON i.id = docu.id_integrante " +
                        "WHERE i.id_credito = ? AND i.id_solicitud_integrante = ?";
                Cursor rowIntegrante = db.rawQuery(sqlIntegrante, new String[]{row.getString(12), "0"});
                rowIntegrante.moveToFirst();
                String id_solicitud = row.getString(0);
                Log.e("Count","a: "+row.getString(14));
                Log.e("ORIGINACION_GPO", "Total: "+rowIntegrante.getCount());
                for (int j = 0; j < rowIntegrante.getCount(); j++){
                    JSONObject json_solicitud = new JSONObject();
                    try {
                        json_solicitud.put(K_TOTAL_INTEGRANTES, rowIntegrante.getCount());
                        json_solicitud.put(K_NOMBRE_GRUPO, row.getString(14).trim().toUpperCase());
                        json_solicitud.put(K_PLAZO, Miscellaneous.GetPlazo(row.getString(15)));
                        json_solicitud.put(K_PERIODICIDAD, Miscellaneous.GetPeriodicidad(row.getString(16)));
                        json_solicitud.put(K_FECHA_DESEMBOLSO, row.getString(17));
                        json_solicitud.put(K_HORA_VISITA, row.getString(19));

                        json_solicitud.put(K_FECHA_INICIO, row.getString(6));
                        json_solicitud.put(K_FECHA_TERMINO, Miscellaneous.ObtenerFecha(TIMESTAMP));
                        json_solicitud.put(K_FECHA_ENVIO, Miscellaneous.ObtenerFecha(TIMESTAMP));

                        JSONObject json_solicitante = new JSONObject();
                        json_solicitante.put(K_NOMBRE, rowIntegrante.getString(3));
                        json_solicitante.put(K_PATERNO, rowIntegrante.getString(4));
                        json_solicitante.put(K_MATERNO, rowIntegrante.getString(5));
                        json_solicitante.put(K_FECHA_NACIMIENTO, rowIntegrante.getString(6));
                        json_solicitante.put(K_EDAD, rowIntegrante.getInt(7));
                        json_solicitante.put(K_GENERO, rowIntegrante.getInt(8));
                        json_solicitante.put(K_ESTADO_NACIMIENTO, rowIntegrante.getString(9));
                        json_solicitante.put(K_RFC, rowIntegrante.getString(10));
                        json_solicitante.put(K_CURP, rowIntegrante.getString(11) + rowIntegrante.getString(12));
                        json_solicitante.put(K_IDENTIFICACION_TIPO, rowIntegrante.getString(13));
                        json_solicitante.put(K_NO_IDENTIFICACION, rowIntegrante.getString(14));
                        json_solicitante.put(K_NIVEL_ESTUDIO, rowIntegrante.getString(15));
                        json_solicitante.put(K_OCUPACION, rowIntegrante.getString(16));

                        json_solicitante.put(K_ESTADO_CIVIL, rowIntegrante.getString(17));
                        if (rowIntegrante.getString(17).equals("CASADO(A)"))
                            json_solicitante.put(K_BIENES, (rowIntegrante.getInt(18) == 1) ? "MANCOMUNADOS" : "SEPARADOS");

                        json_solicitante.put(K_LATITUD, rowIntegrante.getString(32));
                        json_solicitante.put(K_LONGITUD, rowIntegrante.getString(33));
                        json_solicitante.put(K_CALLE, rowIntegrante.getString(34));
                        json_solicitante.put(K_NO_EXTERIOR, rowIntegrante.getString(35));
                        json_solicitante.put(K_NO_INTERIOR, rowIntegrante.getString(36));
                        json_solicitante.put(K_NO_MANZANA, rowIntegrante.getString(37));
                        json_solicitante.put(K_NO_LOTE, rowIntegrante.getString(38));
                        json_solicitante.put(K_CODIGO_POSTAL, rowIntegrante.getInt(39));
                        json_solicitante.put(K_COLONIA, rowIntegrante.getString(40));
                        json_solicitante.put(K_CIUDAD, rowIntegrante.getString(41));
                        json_solicitante.put(K_LOCALIDAD, rowIntegrante.getString(42));
                        json_solicitante.put(K_MUNICIPIO, rowIntegrante.getString(43));
                        json_solicitante.put(K_ESTADO, rowIntegrante.getString(44));

                        json_solicitante.put(K_TIPO_VIVIENDA, rowIntegrante.getString(45));
                        if (rowIntegrante.getString(45).equals("CASA FAMILIAR"))
                            json_solicitante.put(K_PARENTESCO, rowIntegrante.getString(46));
                        else if (rowIntegrante.getString(45).equals("OTRO"))
                            json_solicitante.put(K_OTRO_TIPO_VIVIENDA, rowIntegrante.getString(47));

                        json_solicitante.put(K_TIEMPO_VIVIR_SITIO, rowIntegrante.getInt(48));
                        json_solicitante.put(K_DEPENDIENTES_ECONOMICO, rowIntegrante.getInt(52));
                        json_solicitante.put(K_FOTO_FACHADA, rowIntegrante.getString(49));
                        String fachadaCli = rowIntegrante.getString(49);
                        json_solicitante.put(K_REFERENCIA_DOMICILIARIA, rowIntegrante.getString(50));

                        json_solicitante.put(K_TEL_CASA, rowIntegrante.getString(25));
                        json_solicitante.put(K_TEL_CELULAR, rowIntegrante.getString(26));
                        json_solicitante.put(K_TEL_MENSAJE, rowIntegrante.getString(27));
                        json_solicitante.put(K_TEL_TRABAJO, rowIntegrante.getString(28));

                        json_solicitante.put(K_MEDIO_CONTACTO, rowIntegrante.getString(114));
                        json_solicitante.put(K_EMAIL, rowIntegrante.getString(115));
                        json_solicitante.put(K_ESTADO_CUENTA, rowIntegrante.getString(116));
                        json_solicitante.put(K_FIRMA, rowIntegrante.getString(120));
                        String firmaCli = rowIntegrante.getString(120);
                        json_solicitud.put(K_SOLICITANTE, json_solicitante);

                        Log.e("solicitante", json_solicitante.toString());

                        JSONObject json_otros_datos = new JSONObject();
                        json_otros_datos.put(K_CARGO, rowIntegrante.getInt(2));
                        json_otros_datos.put(K_CLASIFICACION_RIESGO, rowIntegrante.getString(113));
                        json_otros_datos.put(K_ESTATUS_INTEGRANTE, rowIntegrante.getInt(117));
                        json_otros_datos.put(K_MONTO_PRESTAMO, rowIntegrante.getInt(118));
                        json_otros_datos.put(K_MONTO_LETRA, (Miscellaneous.cantidadLetra(rowIntegrante.getString(118)).toUpperCase() + " PESOS MEXICANOS").replace("  ", " "));
                        json_otros_datos.put(K_CASA_REUNION, (rowIntegrante.getInt(119) == 1));

                        json_solicitud.put(K_OTROS_DATOS, json_otros_datos);


                        if (rowIntegrante.getString(17).equals("CASADO(A)") ||
                                rowIntegrante.getString(17).equals("UNIÓN LIBRE")) {
                            JSONObject json_conyuge = new JSONObject();

                            json_conyuge.put(K_NOMBRE, rowIntegrante.getString(90));
                            json_conyuge.put(K_PATERNO, rowIntegrante.getString(91));
                            json_conyuge.put(K_MATERNO, rowIntegrante.getString(92));
                            json_conyuge.put(K_NACIONALIDAD, rowIntegrante.getString(93));
                            json_conyuge.put(K_OCUPACION, rowIntegrante.getString(94));
                            json_conyuge.put(K_CALLE, rowIntegrante.getString(95));
                            json_conyuge.put(K_NO_EXTERIOR, rowIntegrante.getString(96));
                            json_conyuge.put(K_NO_INTERIOR, rowIntegrante.getString(97));
                            json_conyuge.put(K_NO_MANZANA, rowIntegrante.getString(98));
                            json_conyuge.put(K_NO_LOTE, rowIntegrante.getString(98));
                            json_conyuge.put(K_CODIGO_POSTAL, rowIntegrante.getInt(100));
                            json_conyuge.put(K_COLONIA, rowIntegrante.getString(101));
                            json_conyuge.put(K_CIUDAD, rowIntegrante.getString(102));
                            json_conyuge.put(K_LOCALIDAD, rowIntegrante.getString(103));
                            json_conyuge.put(K_MUNICIPIO, rowIntegrante.getString(104));
                            json_conyuge.put(K_ESTADO, rowIntegrante.getString(105));
                            json_conyuge.put(K_INGRESO_MENSUAL, rowIntegrante.getDouble(106));
                            json_conyuge.put(K_GASTO_MENSUAL, rowIntegrante.getDouble(107));
                            json_conyuge.put(K_TEL_CELULAR, rowIntegrante.getString(108));
                            json_conyuge.put(K_TEL_CASA, rowIntegrante.getString(109));

                            json_solicitud.put(K_SOLICITANTE_CONYUGE, json_conyuge);

                            Log.e("conyuge", json_conyuge.toString());
                        }

                        JSONObject json_negocio = new JSONObject();
                        json_negocio.put(K_NOMBRE, rowIntegrante.getString(55));
                        json_negocio.put(K_LATITUD, rowIntegrante.getString(56));
                        json_negocio.put(K_LONGITUD, rowIntegrante.getString(57));
                        json_negocio.put(K_CALLE, rowIntegrante.getString(58));
                        json_negocio.put(K_NO_EXTERIOR, rowIntegrante.getString(59));
                        json_negocio.put(K_NO_INTERIOR, rowIntegrante.getString(60));
                        json_negocio.put(K_NO_MANZANA, rowIntegrante.getString(61));
                        json_negocio.put(K_NO_LOTE, rowIntegrante.getString(62));
                        json_negocio.put(K_CODIGO_POSTAL, rowIntegrante.getInt(63));
                        json_negocio.put(K_COLONIA, rowIntegrante.getString(64));
                        json_negocio.put(K_CIUDAD, rowIntegrante.getString(65));
                        json_negocio.put(K_LOCALIDAD, rowIntegrante.getString(66));
                        json_negocio.put(K_MUNICIPIO, rowIntegrante.getString(67));
                        json_negocio.put(K_ESTADO, rowIntegrante.getString(68));
                        json_negocio.put(K_DESTINO_CREDITO, rowIntegrante.getString(69));
                        if (rowIntegrante.getString(69).contains("OTRO"))
                            json_negocio.put(K_OTRO_DESTINO_CREDITO, rowIntegrante.getString(70));
                        json_negocio.put(K_OCUPACION, rowIntegrante.getString(71));
                        json_negocio.put(K_ACTIVIDAD_ECONOMICA, rowIntegrante.getString(72));
                        json_negocio.put(K_ANTIGUEDAD, rowIntegrante.getInt(73));
                        json_negocio.put(K_INGRESO_MENSUAL, rowIntegrante.getString(74));
                        json_negocio.put(K_INGRESOS_OTROS, rowIntegrante.getString(75));
                        json_negocio.put(K_GASTO_MENSUAL, rowIntegrante.getString(76));
                        json_negocio.put(K_CAPACIDAD_PAGO, rowIntegrante.getString(77));
                        json_negocio.put(K_MONTO_MAXIMO, rowIntegrante.getString(78));
                        String aux = "";
                        if (!rowIntegrante.getString(79).trim().isEmpty()) {
                            String[] medios = rowIntegrante.getString(79).split(",");

                            if (medios.length > 0) {
                                for (int m = 0; m < medios.length; m++) {
                                    if (m == 0)
                                        aux = "'" + medios[m].trim() + "'";
                                    else
                                        aux += "," + "'" + medios[m].trim() + "'";
                                }
                            }
                        }

                        String sqlMediosPago = "SELECT * FROM " + TBL_MEDIOS_PAGO_ORI + " WHERE nombre IN (" + aux + ")";
                        Cursor row_medio_pago = db.rawQuery(sqlMediosPago, null);
                        if (row_medio_pago.getCount() > 0) {
                            row_medio_pago.moveToFirst();
                            String medios_pagos_ids = "";
                            for (int k = 0; k < row_medio_pago.getCount(); k++) {
                                if (k == 0)
                                    medios_pagos_ids = row_medio_pago.getString(1).trim();
                                else
                                    medios_pagos_ids += "," + row_medio_pago.getString(1).trim();

                                row_medio_pago.moveToNext();
                            }
                            json_negocio.put(K_MEDIOS_PAGOS, medios_pagos_ids);
                        } else {
                            json_negocio.put(K_MEDIOS_PAGOS, "");
                        }
                        row_medio_pago.close();

                        if (rowIntegrante.getString(79).contains("OTRO"))
                            json_negocio.put(K_OTRO_MEDIOS_PAGOS, rowIntegrante.getString(80));

                        json_negocio.put(K_NUM_OPERACIONES_MENSUAL, rowIntegrante.getInt(81));

                        if (rowIntegrante.getString(79).contains("EFECTIVO"))
                            json_negocio.put(K_NUM_OPERACIONES_MENSUAL_EFECTIVO, rowIntegrante.getInt(82));

                        json_negocio.put(K_FOTO_FACHADA, rowIntegrante.getString(83));
                        String fachadaNeg = rowIntegrante.getString(83);
                        json_negocio.put(K_REFERENCIA_NEGOCIO, rowIntegrante.getString(84));

                        json_solicitud.put(K_SOLICITANTE_NEGOCIO, json_negocio);

                        Log.e("negocio", json_negocio.toString());

                        JSONObject json_documentos = new JSONObject();
                        json_documentos.put(K_IDENTIFICACION_FRONTAL, rowIntegrante.getString(124));
                        String identiFrontal = rowIntegrante.getString(124);
                        json_documentos.put(K_IDENTIFICACION_REVERSO, rowIntegrante.getString(125));
                        String identiReverso = rowIntegrante.getString(125);
                        json_documentos.put(K_CURP, rowIntegrante.getString(126));
                        String curp = rowIntegrante.getString(126);
                        json_documentos.put(K_COMPROBANTE_DOMICILIO, rowIntegrante.getString(127));
                        String comprobante = rowIntegrante.getString(127);

                        json_solicitud.put(K_SOLICITANTE_DOCUMENTOS, json_documentos);

                        JSONObject json_politicas = new JSONObject();
                        json_politicas.put(K_PROPIETARIO, rowIntegrante.getInt(131) == 1);
                        json_politicas.put(K_PROVEEDOR_RECURSOS, rowIntegrante.getInt(132) == 1);
                        json_politicas.put(K_POLITICAMENTE_EXP, rowIntegrante.getInt(133) == 1);
                        json_solicitud.put(K_SOLICITANTE_POLITICAS, json_politicas);

                        if (rowIntegrante.getInt(119) == 1){
                            JSONObject json_croquis = new JSONObject();
                            json_croquis.put(K_CALLE_ENFRENTE, rowIntegrante.getString(137));
                            json_croquis.put(K_CALLE_LATERAL_IZQ, rowIntegrante.getString(138));
                            json_croquis.put(K_CALLE_LATERAL_DER, rowIntegrante.getString(139));
                            json_croquis.put(K_CALLE_ATRAS, rowIntegrante.getString(140));
                            json_croquis.put(K_REFERENCIAS, rowIntegrante.getString(141));

                            json_solicitud.put(K_SOLICITANTE_CROQUIS, json_croquis);
                        }

                        Log.e("_","_______________________________________________________________________-");
                        Log.e("solicitudInt", json_solicitud.toString());
                        Log.e("_","_______________________________________________________________________-");
                        new GuardarSolicitudGpo().execute(ctx, json_solicitud, fachadaCli, firmaCli, fachadaNeg, identiFrontal,
                                identiReverso, curp,comprobante, rowIntegrante.getString(0),rowIntegrante.getString(1), id_solicitud);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    rowIntegrante.moveToNext();
                }
                rowIntegrante.close();
                row.moveToNext();
            }

            Log.e("count solicitudes", row.getCount()+" total");

        }
        row.close();

    }

    //Obtiene los datos de un prestamo en especifico
    public void GetPrestamo(final Context ctx, final int id_cartera, int tipo_prestamo){
        final AlertDialog loading = Popups.showLoadingDialog(ctx, R.string.please_wait, R.string.loading_info);
        loading.show();

        SessionManager session = new SessionManager(ctx);
        ManagerInterface api = new RetrofitClient().generalRF(CONTROLLER_MOVIL, ctx).create(ManagerInterface.class);

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
                                    values.put(12, (prestamos.get(i).getPagada().equals("PAGADA"))?"1":"0");                     //PAGADA
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
                                    cv.put("pagada", (prestamos.get(i).getPagada().equals("PAGADA"))?"1":"0");
                                    cv.put("fecha_actualizado", Miscellaneous.ObtenerFecha("timestamp"));

                                    db.update(TBL_PRESTAMOS_IND_T, cv,
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

                                        db.update(TBL_AVAL_T, cv_aval,
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

                                            db.update(TBL_AMORTIZACIONES_T, cv_amortiz,
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

                                                dBhelper.savePagos(db, TBL_PAGOS_T, cv_pago);
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

                                    db.update(TBL_PRESTAMOS_GPO_T, cv_prestamo,
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

                                            db.update(TBL_MIEMBROS_GPO_T, cv_miembro,
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

                                            db.update(TBL_AMORTIZACIONES_T, cv_amortiz,
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

                                                dBhelper.savePagos(db, TBL_PAGOS_T, cv_pago);
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
        loading.setCancelable(false);

        //if ((!((Activity) ctx).isFinishing())) {
            if (showDG)
                loading.show();
        //}
        SessionManager session = new SessionManager(ctx);
        final DBhelper dBhelper = new DBhelper(ctx);
        final SQLiteDatabase db = dBhelper.getWritableDatabase();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Cursor row;
        String sql = "SELECT * FROM (SELECT vi.*, pi.tipo_cartera, '1' AS tipo_gestion, 'VIGENTE' AS tipo_prestamo FROM " + TBL_IMPRESIONES_VIGENTE_T + " AS vi INNER JOIN " + TBL_PRESTAMOS_IND_T + " AS pi ON vi.num_prestamo = pi.num_prestamo WHERE vi.num_prestamo LIKE '%-L%' UNION SELECT vi2.*, pg.tipo_cartera, '2' AS tipo_gestion, 'VIGENTE' AS tipo_prestamo FROM " + TBL_IMPRESIONES_VIGENTE_T + " AS vi2 INNER JOIN " + TBL_PRESTAMOS_GPO_T + " AS pg ON vi2.num_prestamo = pg.num_prestamo WHERE vi2.num_prestamo NOT LIKE '%-L%' UNION SELECT v.*, pvi.tipo_cartera, '1' AS tipo_gestion, 'VENCIDA' AS tipo_prestamo FROM " + TBL_IMPRESIONES_VENCIDA_T + " AS v INNER JOIN " + TBL_PRESTAMOS_IND_T + " AS pvi ON v.num_prestamo = pvi.num_prestamo WHERE v.num_prestamo LIKE '%-L%' UNION SELECT vg.*, pvg.tipo_cartera, '2' AS tipo_gestion, 'VENCIDA' AS tipo_prestamo FROM " + TBL_IMPRESIONES_VENCIDA_T + " AS vg INNER JOIN " + TBL_PRESTAMOS_GPO_T + " AS pvg ON vg.num_prestamo = pvg.num_prestamo WHERE vg.num_prestamo NOT LIKE '%-L%') AS imp WHERE estatus = ?";

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
                if (row_gestion.getCount() > 0) {
                    row_gestion.moveToFirst();
                    fInicio = row_gestion.getString(0);

                    Log.e("FechaInicio", fInicio);
                    Calendar cal = Calendar.getInstance();
                    try {
                        Date inicioGes = sdf.parse(fInicio);
                        cal.setTime(inicioGes);
                        String weekOfYear = (cal.get(Calendar.WEEK_OF_YEAR) < 10) ? "0" + cal.get(Calendar.WEEK_OF_YEAR) : String.valueOf(cal.get(Calendar.WEEK_OF_YEAR));
                        String nomenclatura = Miscellaneous.GetNomenclatura(row.getInt(12), row.getString(11));
                        external_id = cal.get(Calendar.YEAR) + weekOfYear + row.getString(2) + row.getString(10) + nomenclatura;
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
                }

                row.moveToNext();
            }
        }

        //if ((!((Activity) ctx).isFinishing())) {
            if (showDG)
                loading.dismiss();
        //}
    }

    public void SendRecibos(Context ctx, boolean showDG){
        final AlertDialog loading = Popups.showLoadingDialog(ctx, R.string.please_wait, R.string.loading_info);
        loading.setCancelable(false);

        //if (showDG)
            //loading.show();
        SessionManager session = new SessionManager(ctx);
        final DBhelper dBhelper = new DBhelper(ctx);
        final SQLiteDatabase db = dBhelper.getWritableDatabase();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String sql = "SELECT * FROM " + TBL_RECIBOS + " WHERE estatus = ?";
        Cursor row = db.rawQuery(sql, new String[]{"0"});

        if (row.getCount() > 0){
            row.moveToFirst();
            for (int i = 0; i < row.getCount(); i++){
                MSendRecibo recibo = new MSendRecibo();
                recibo.setPrestamoId(row.getLong(1));
                recibo.setAsesorId(row.getString(2));
                recibo.setTipo_recibo(row.getString(3));
                recibo.setTipoImpresion(row.getString(4));
                recibo.setFolio(row.getLong(5));
                recibo.setMonto(row.getString(6));
                recibo.setClave(row.getString(7));
                recibo.setNombre(row.getString(8));
                recibo.setFechaImpreso(row.getString(11));
                recibo.setFechaEnvio(Miscellaneous.ObtenerFecha(TIMESTAMP));
                recibo.setCurp(row.getString(14));
                recibo.setUsuarioId(Long.parseLong(session.getUser().get(9)));

                new GuardarRecibo()
                        .execute(ctx,
                                recibo,
                                row.getString(0));
                row.moveToNext();
            }
        }
        row.close();

        if (showDG)
            loading.dismiss();

    }

    public void SendSoporte(Context ctx, boolean showDG){
        final AlertDialog loading = Popups.showLoadingDialog(ctx, R.string.please_wait, R.string.loading_info);

        //if (showDG)
        //loading.show();
        SessionManager session = new SessionManager(ctx);
        final DBhelper dBhelper = new DBhelper(ctx);
        final SQLiteDatabase db = dBhelper.getWritableDatabase();

        String sql = "SELECT * FROM " + TBL_SOPORTE + " WHERE estatus_envio = ?";
        Cursor row = db.rawQuery(sql, new String[]{"0"});

        if (row.getCount() > 0){
            row.moveToFirst();
            for (int i = 0; i < row.getCount(); i++){
                MSendSoporte soporte = new MSendSoporte();
                soporte.setUsuarioId(Long.parseLong(session.getUser().get(9)));
                soporte.setCategoria(row.getLong(1));
                if (row.getInt(2) == 1) {
                    soporte.setClienteId(row.getLong(5));
                    soporte.setNoSolicitud(row.getLong(6));
                }
                else if (row.getInt(2) == 2) {
                    soporte.setGrupoId(row.getLong(4));
                    soporte.setNoSolicitud(row.getLong(6));
                }

                if (row.getInt(7) > 0)
                    soporte.setFolioImpresion(row.getString(7));

                soporte.setComentario(row.getString(8));
                soporte.setFechaSolicitud(row.getString(12));
                soporte.setFechaEnvio(Miscellaneous.ObtenerFecha(TIMESTAMP));

                new GuardarSoporte()
                        .execute(ctx,
                                soporte,
                                row.getString(0));
                row.moveToNext();
            }
        }
        row.close();

        if (showDG)
            loading.dismiss();

    }

    public void SendCancelGestiones(Context ctx, boolean showDG){
        final AlertDialog loading = Popups.showLoadingDialog(ctx, R.string.please_wait, R.string.loading_info);
        loading.setCancelable(false);
        if (showDG)
            loading.show();

        final DBhelper dBhelper = new DBhelper(ctx);
        final SQLiteDatabase db = dBhelper.getWritableDatabase();



        Cursor row = dBhelper.getRecords(TBL_CANCELACIONES, " WHERE estatus = ''", "", null);

        if (row.getCount() > 0){
            row.moveToFirst();
            for (int i = 0; i < row.getCount(); i++){
                new GuardarCancelGestiones().execute(ctx, row.getString(3), row.getString(4), row.getString(1), row.getString(5), row.getString(0));
                row.moveToNext();
            }
        }
        row.close();

        if (showDG)
            loading.dismiss();


    }

    //Envia las reimpresiones realizadas
    public void SendReimpresionesVi (Context ctx, boolean showDG){

        final AlertDialog loading = Popups.showLoadingDialog(ctx, R.string.please_wait, R.string.loading_info);
        loading.setCancelable(false);

        //if ((!((Activity) ctx).isFinishing())) {
            if (showDG)
                loading.show();
        //}

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

        //if ((!((Activity) ctx).isFinishing())) {
            if (showDG)
                loading.dismiss();
        //}
    }

    public void SendTracker(Context ctx, boolean showDG) {

        SessionManager session = new SessionManager(ctx);
        final DBhelper dBhelper = new DBhelper(ctx);
        final SQLiteDatabase db = dBhelper.getWritableDatabase();

        Cursor row;

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

    public void CancelGestiones(final Context ctx, final boolean showDG){
        final AlertDialog loading = Popups.showLoadingDialog(ctx, R.string.please_wait, R.string.loading_info);
        loading.setCancelable(false);

        if (showDG)
            loading.show();

        SessionManager session = new SessionManager(ctx);
        final DBhelper dBhelper = new DBhelper(ctx);
        final SQLiteDatabase db = dBhelper.getWritableDatabase();

        ManagerInterface api = new RetrofitClient().generalRF(CONTROLLER_MOVIL, ctx).create(ManagerInterface.class);

        Call<MGestionCancelada> call = api.getGestionesCanceladas(session.getUser().get(9),"Bearer "+ session.getUser().get(7));
        call.enqueue(new Callback<MGestionCancelada>() {
            @Override
            public void onResponse(Call<MGestionCancelada> call, Response<MGestionCancelada> response) {
                Log.e("CodeCancel","asd "+response.code());
                MGestionCancelada data = response.body();
                switch (response.code()){
                    case 200:
                        if (data.getData().size() > 0){
                            List<MRespuestaSolicitud> items = data.getData();
                            Log.e("Size", items.get(0).getEstatus());
                            for(int i = 0; i < items.size(); i++) {
                                MRespuestaSolicitud item = items.get(i);

                                Cursor row_cancel = dBhelper.getRecords(TBL_CANCELACIONES, " WHERE fecha_aplicacion <> '' AND id_solicitud = ?", "", new String[]{String.valueOf(item.getId())});
                                if (row_cancel.getCount() > 0) {
                                    Log.e("Comentario", item.getComentario());
                                    if (item.getTipoGestion() == 1 && item.getTipoPrestamo().equals("VIGENTE")) {
                                        if (item.getEstatus().equals("CANCELADA")) {
                                            Cursor row = dBhelper.getRecords(TBL_CANCELACIONES, " WHERE id_solicitud = ?", "", new String[]{String.valueOf(item.getId())});
                                            if (row.getCount() > 0) {
                                                row.moveToFirst();

                                                ContentValues cv = new ContentValues();
                                                cv.put("estatus", 3);
                                                db.update(TBL_RESPUESTAS_IND_T, cv, "_id = ?", new String[]{row.getString(1)});

                                                cv = new ContentValues();
                                                cv.put("estatus", item.getEstatus());
                                                cv.put("comentario_admin", item.getComentario());
                                                cv.put("fecha_aplicacion", item.getFechaAplicacion());
                                                db.update(TBL_CANCELACIONES, cv, "id_solicitud = ?", new String[]{String.valueOf(item.getId())});

                                                Toast.makeText(ctx, "Cancelada", Toast.LENGTH_SHORT).show();
                                                ActualiziaAmortizIndVi(ctx, item.getPrestamoId(), item.getPagoRealizado());

                                            }
                                        } else if (item.getEstatus().equals("RECHAZADA")) {
                                            //db.delete(TBL_CANCELACIONES, "id_solicitud = ?", new String[]{String.valueOf(item.getId())});
                                            ContentValues cv = new ContentValues();
                                            cv.put("estatus", item.getEstatus());
                                            cv.put("comentario_admin", item.getComentario());
                                            cv.put("fecha_aplicacion", item.getFechaAplicacion());
                                            db.update(TBL_CANCELACIONES, cv, "id_solicitud = ?", new String[]{String.valueOf(item.getId())});
                                        }

                                    } else if (item.getTipoGestion() == 2 && item.getTipoPrestamo().equals("VIGENTE")) {
                                        if (item.getEstatus().equals("CANCELADA")) {
                                            Cursor row = dBhelper.getRecords(TBL_CANCELACIONES, " WHERE id_solicitud = ?", "", new String[]{String.valueOf(item.getId())});
                                            if (row.getCount() > 0) {
                                                row.moveToFirst();
                                                Log.e("Cancela", "grupal vigente");

                                                ContentValues cv = new ContentValues();
                                                cv.put("estatus", 3);
                                                db.update(TBL_RESPUESTAS_GPO_T, cv, "_id = ?", new String[]{row.getString(1)});

                                                cv = new ContentValues();
                                                cv.put("estatus", item.getEstatus());
                                                cv.put("comentario_admin", item.getComentario());
                                                cv.put("fecha_aplicacion", item.getFechaAplicacion());
                                                db.update(TBL_CANCELACIONES, cv, "id_solicitud = ?", new String[]{String.valueOf(item.getId())});

                                                ActualiziaAmortizGpoVi(ctx, item.getPrestamoId(), item.getPagoRealizado());
                                            }
                                        } else if (item.getEstatus().equals("RECHAZADA")) {
                                            ContentValues cv = new ContentValues();
                                            cv.put("estatus", item.getEstatus());
                                            cv.put("comentario_admin", item.getComentario());
                                            cv.put("fecha_aplicacion", item.getFechaAplicacion());
                                            db.update(TBL_CANCELACIONES, cv, "id_solicitud = ?", new String[]{String.valueOf(item.getId())});
                                            //db.delete(TBL_CANCELACIONES, "id_solicitud = ?", new String[]{String.valueOf(item.getId())});
                                        }
                                    } else if (item.getTipoGestion() == 1 && item.getTipoPrestamo().equals("VENCIDA")) {
                                        if (item.getEstatus().equals("CANCELADA")) {
                                            Cursor row = dBhelper.getRecords(TBL_CANCELACIONES, " WHERE id_solicitud = ?", "", new String[]{String.valueOf(item.getId())});
                                            if (row.getCount() > 0) {
                                                row.moveToFirst();

                                                ContentValues cv = new ContentValues();
                                                cv.put("estatus", 3);
                                                db.update(TBL_RESPUESTAS_IND_V_T, cv, "_id = ?", new String[]{row.getString(1)});

                                                cv = new ContentValues();
                                                cv.put("estatus", item.getEstatus());
                                                cv.put("comentario_admin", item.getComentario());
                                                cv.put("fecha_aplicacion", item.getFechaAplicacion());
                                                db.update(TBL_CANCELACIONES, cv, "id_solicitud = ?", new String[]{String.valueOf(item.getId())});

                                                ActualiziaAmortizIndVe(ctx, item.getPrestamoId(), item.getPagoRealizado());
                                            }
                                        } else if (item.getEstatus().equals("RECHAZADA")) {
                                            ContentValues cv = new ContentValues();
                                            cv.put("estatus", item.getEstatus());
                                            cv.put("comentario_admin", item.getComentario());
                                            cv.put("fecha_aplicacion", item.getFechaAplicacion());
                                            db.update(TBL_CANCELACIONES, cv, "id_solicitud = ?", new String[]{String.valueOf(item.getId())});
                                            //db.delete(TBL_CANCELACIONES, "id_solicitud = ?", new String[]{String.valueOf(item.getId())});
                                        }
                                    } else if (item.getTipoGestion() == 2 && item.getTipoPrestamo().equals("VENCIDA")) {
                                        if (item.getEstatus().equals("CANCELADA")) {
                                            Cursor row = dBhelper.getRecords(TBL_CANCELACIONES, " WHERE id_solicitud = ?", "", new String[]{String.valueOf(item.getId())});
                                            if (row.getCount() > 0) {
                                                row.moveToFirst();

                                                ContentValues cv = new ContentValues();
                                                cv.put("estatus", 3);
                                                db.update(TBL_RESPUESTAS_INTEGRANTE_T, cv, "_id = ?", new String[]{row.getString(1)});

                                                cv = new ContentValues();
                                                cv.put("estatus", item.getEstatus());
                                                cv.put("comentario_admin", item.getComentario());
                                                cv.put("fecha_aplicacion", item.getFechaAplicacion());
                                                db.update(TBL_CANCELACIONES, cv, "id_solicitud = ?", new String[]{String.valueOf(item.getId())});

                                                ActualiziaAmortizIntVe(ctx, item.getPrestamoId(), item.getPagoRealizado());
                                            }
                                        } else if (item.getEstatus().equals("RECHAZADA")) {
                                            ContentValues cv = new ContentValues();
                                            cv.put("estatus", item.getEstatus());
                                            cv.put("comentario_admin", item.getComentario());
                                            cv.put("fecha_aplicacion", item.getFechaAplicacion());
                                            db.update(TBL_CANCELACIONES, cv, "id_solicitud = ?", new String[]{String.valueOf(item.getId())});
                                            //db.delete(TBL_CANCELACIONES, "id_solicitud = ?", new String[]{String.valueOf(item.getId())});
                                        }
                                    }
                                }
                                row_cancel.close();
                            }
                        }
                        break;
                }
                if (showDG)
                    loading.dismiss();
            }

            @Override
            public void onFailure(Call<MGestionCancelada> call, Throwable t) {
                //Log.e("FailCancel", "asd "+ t.getMessage());
                if (showDG)
                    loading.dismiss();
            }
        });
    }

    public void ActualiziaAmortizIndVi(Context ctx, String idPrestamo, String pagoRealizado){
        DBhelper dBhelper = new DBhelper(ctx);
        SQLiteDatabase db = dBhelper.getWritableDatabase();
        SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_DATE_GNRAL);

            Log.e("amortizaciones", "Actualiza");
            String sqlAmortiz = "SELECT _id, total, total_pagado, pagado, fecha, numero FROM " + TBL_AMORTIZACIONES_T + " WHERE id_prestamo = ? AND CAST(total_pagado AS DOUBLE) > 0 ORDER BY numero DESC";
            Cursor row_amortiz = db.rawQuery(sqlAmortiz, new String[]{idPrestamo});
            if (row_amortiz.getCount() > 0){
                row_amortiz.moveToFirst();
                Double abono = 0.0;
                if (!pagoRealizado.trim().isEmpty())
                    abono = Double.parseDouble(pagoRealizado);

                for (int i = 0; i < row_amortiz.getCount(); i++){

                    Double pendiente = row_amortiz.getDouble(2);

                    Double res = abono - pendiente;

                    if (res >= 0){
                        ContentValues cv_amortiz = new ContentValues();
                        cv_amortiz.put("total_pagado", "0");
                        cv_amortiz.put("pagado", "PENDIENTE");
                        cv_amortiz.put("dias_atraso", 0);
                        db.update(TBL_AMORTIZACIONES_T, cv_amortiz, "id_prestamo = ? AND numero = ?", new String[]{idPrestamo, row_amortiz.getString(5)});

                        abono = res;
                    }
                    else{
                        ContentValues cv_amortiz = new ContentValues();
                        cv_amortiz.put("total_pagado", abono);
                        cv_amortiz.put("pagado", "PARCIAL");

                        cv_amortiz.put("dias_atraso", Miscellaneous.GetDiasAtraso(row_amortiz.getString(4)));
                        db.update(TBL_AMORTIZACIONES_T, cv_amortiz, "id_prestamo = ? AND numero = ?", new String[]{idPrestamo, row_amortiz.getString(5)});

                        break;
                    }

                    row_amortiz.moveToNext();
                }

            }
            row_amortiz.close();

            sqlAmortiz = "SELECT SUM(a.total_pagado) AS suma_pagos, p.monto_total FROM " + TBL_AMORTIZACIONES_T + " AS a INNER JOIN "+TBL_PRESTAMOS_IND_T+" AS p ON p.id_prestamo = a.id_prestamo WHERE a.id_prestamo = ?";
            row_amortiz = db.rawQuery(sqlAmortiz, new String[]{idPrestamo});

            if (row_amortiz.getCount() > 0){
                row_amortiz.moveToFirst();
                if (row_amortiz.getDouble(0) >= row_amortiz.getDouble(1)){
                    ContentValues c = new ContentValues();
                    c.put("pagada", 1);
                    db.update(TBL_PRESTAMOS_IND_T, c, "id_prestamo = ?", new String[]{idPrestamo});
                }
                else{
                    ContentValues c = new ContentValues();
                    c.put("pagada", 0);
                    db.update(TBL_PRESTAMOS_IND_T, c, "id_prestamo = ?", new String[]{idPrestamo});
                }

            }
            row_amortiz.close();

    }

    public void ActualiziaAmortizGpoVi(Context ctx, String idPrestamo, String pagoRealizado){
        DBhelper dBhelper = new DBhelper(ctx);
        SQLiteDatabase db = dBhelper.getWritableDatabase();

        Log.e("Comienza", "Actualiza amortizaciones gpo");
        String sqlAmortiz = "SELECT _id, total, total_pagado, pagado, fecha, numero FROM " + TBL_AMORTIZACIONES_T + " WHERE id_prestamo = ? AND CAST(total_pagado AS DOUBLE) > 0 ORDER BY numero DESC";
        Cursor row_amortiz = db.rawQuery(sqlAmortiz, new String[]{idPrestamo});
        Log.e("AmortiGPo", row_amortiz.getCount()+ " total");
        if (row_amortiz.getCount() > 0){
            row_amortiz.moveToFirst();
            Double abono = 0.0;
            if (!pagoRealizado.trim().isEmpty())
                abono = Double.parseDouble(pagoRealizado);

            for (int i = 0; i < row_amortiz.getCount(); i++){

                Double pendiente = row_amortiz.getDouble(2);

                Double res = abono - pendiente;

                if (res >= 0){
                    ContentValues cv_amortiz = new ContentValues();
                    cv_amortiz.put("total_pagado", "0");
                    cv_amortiz.put("pagado", "PENDIENTE");
                    cv_amortiz.put("dias_atraso", 0);
                    db.update(TBL_AMORTIZACIONES_T, cv_amortiz, "id_prestamo = ? AND numero = ?", new String[]{idPrestamo, row_amortiz.getString(5)});

                    abono = res;
                }
                else{
                    ContentValues cv_amortiz = new ContentValues();
                    cv_amortiz.put("total_pagado", abono);
                    cv_amortiz.put("pagado", "PARCIAL");

                    cv_amortiz.put("dias_atraso", Miscellaneous.GetDiasAtraso(row_amortiz.getString(4)));
                    db.update(TBL_AMORTIZACIONES_T, cv_amortiz, "id_prestamo = ? AND numero = ?", new String[]{idPrestamo, row_amortiz.getString(5)});

                    break;
                }

                /*if (abono > pendiente){
                    ContentValues cv_amortiz = new ContentValues();
                    cv_amortiz.put("total_pagado", row_amortiz.getString(1));
                    cv_amortiz.put("pagado", "PAGADO");
                    cv_amortiz.put("dias_atraso", Miscellaneous.GetDiasAtraso(row_amortiz.getString(4)));
                    db.update(TBL_AMORTIZACIONES_T, cv_amortiz, "id_prestamo = ? AND numero = ?", new String[]{idPrestamo, row_amortiz.getString(5)});
                    abono = abono - pendiente;
                }
                else if (abono == pendiente){
                    ContentValues cv_amortiz = new ContentValues();
                    cv_amortiz.put("total_pagado", row_amortiz.getString(1));
                    cv_amortiz.put("pagado", "PAGADO");
                    cv_amortiz.put("dias_atraso", Miscellaneous.GetDiasAtraso(row_amortiz.getString(4)));
                    db.update(TBL_AMORTIZACIONES_T, cv_amortiz, "id_prestamo = ? AND numero = ?", new String[]{idPrestamo, row_amortiz.getString(5)});
                    abono = 0.0;
                }
                else if (abono > 0 && abono < pendiente){
                    ContentValues cv_amortiz = new ContentValues();
                    cv_amortiz.put("total_pagado", abono);
                    cv_amortiz.put("pagado", "PARCIAL");
                    abono = 0.0;
                    cv_amortiz.put("dias_atraso", Miscellaneous.GetDiasAtraso(row_amortiz.getString(4)));
                    db.update(TBL_AMORTIZACIONES_T, cv_amortiz, "id_prestamo = ? AND numero = ?", new String[]{idPrestamo, row_amortiz.getString(5)});

                }
                else
                    break;*/

                row_amortiz.moveToNext();
            }

        }
        row_amortiz.close();

        sqlAmortiz = "SELECT SUM(a.total_pagado) AS suma_pagos, p.monto_total FROM " + TBL_AMORTIZACIONES_T + " AS a INNER JOIN "+TBL_PRESTAMOS_GPO_T+" AS p ON p.id_prestamo = a.id_prestamo WHERE a.id_prestamo = ?";
        row_amortiz = db.rawQuery(sqlAmortiz, new String[]{idPrestamo});

        if (row_amortiz.getCount() > 0){
            row_amortiz.moveToFirst();
            if (row_amortiz.getDouble(0) >= row_amortiz.getDouble(1)){
                ContentValues c = new ContentValues();
                c.put("pagada", 1);
                db.update(TBL_PRESTAMOS_GPO_T, c, "id_prestamo = ?", new String[]{idPrestamo});
            }
            else{
                ContentValues c = new ContentValues();
                c.put("pagada", 0);
                db.update(TBL_PRESTAMOS_GPO_T, c, "id_prestamo = ?", new String[]{idPrestamo});
            }

        }
        row_amortiz.close();
    }

    public void ActualiziaAmortizIndVe(Context ctx, String idPrestamo, String pagoRealizado){
        DBhelper dBhelper = new DBhelper(ctx);
        SQLiteDatabase db = dBhelper.getWritableDatabase();

        String sqlAmortiz = "SELECT _id, total, total_pagado, pagado, fecha, numero FROM " + TBL_AMORTIZACIONES_T + " WHERE id_prestamo = ? AND CAST(total_pagado AS DOUBLE) > 0 ORDER BY numero DESC";
        Cursor row_amortiz = db.rawQuery(sqlAmortiz, new String[]{idPrestamo});
        if (row_amortiz.getCount() > 0){
            row_amortiz.moveToFirst();

            Double abono = 0.0;
            if (!pagoRealizado.trim().isEmpty())
                abono = Double.parseDouble(pagoRealizado);

            for (int i = 0; i < row_amortiz.getCount(); i++){

                Double pendiente = row_amortiz.getDouble(2);

                Double res = abono - pendiente;

                if (res >= 0){
                    ContentValues cv_amortiz = new ContentValues();
                    cv_amortiz.put("total_pagado", "0");
                    cv_amortiz.put("pagado", "PENDIENTE");
                    cv_amortiz.put("dias_atraso", 0);
                    db.update(TBL_AMORTIZACIONES_T, cv_amortiz, "id_prestamo = ? AND numero = ?", new String[]{idPrestamo, row_amortiz.getString(5)});

                    abono = res;
                }
                else{
                    ContentValues cv_amortiz = new ContentValues();
                    cv_amortiz.put("total_pagado", abono);
                    cv_amortiz.put("pagado", "PARCIAL");

                    cv_amortiz.put("dias_atraso", Miscellaneous.GetDiasAtraso(row_amortiz.getString(4)));
                    db.update(TBL_AMORTIZACIONES_T, cv_amortiz, "id_prestamo = ? AND numero = ?", new String[]{idPrestamo, row_amortiz.getString(5)});

                    break;
                }
                row_amortiz.moveToNext();
            }

        }
        row_amortiz.close();

        sqlAmortiz = "SELECT SUM(a.total_pagado) AS suma_pagos, p.monto_total FROM " + TBL_AMORTIZACIONES_T + " AS a INNER JOIN "+TBL_PRESTAMOS_IND_T+" AS p ON p.id_prestamo = a.id_prestamo WHERE a.id_prestamo = ?";
        row_amortiz = db.rawQuery(sqlAmortiz, new String[]{idPrestamo});

        if (row_amortiz.getCount() > 0){
            row_amortiz.moveToFirst();
            if (row_amortiz.getDouble(0) >= row_amortiz.getDouble(1)){
                ContentValues c = new ContentValues();
                c.put("pagada", 1);
                db.update(TBL_PRESTAMOS_IND_T, c, "id_prestamo = ?", new String[]{idPrestamo});
            }
            else{
                ContentValues c = new ContentValues();
                c.put("pagada", 0);
                db.update(TBL_PRESTAMOS_IND_T, c, "id_prestamo = ?", new String[]{idPrestamo});
            }

        }
        row_amortiz.close();
    }

    public void ActualiziaAmortizIntVe(Context ctx, String idPrestamo, String pagoRealizado){
        DBhelper dBhelper = new DBhelper(ctx);
        SQLiteDatabase db = dBhelper.getWritableDatabase();

        String sqlAmortiz = "SELECT _id, total, total_pagado, pagado, fecha, numero FROM " + TBL_AMORTIZACIONES_T + " WHERE id_prestamo = ? AND CAST(total_pagado AS DOUBLE) > 0 ORDER BY numero DESC";
        Cursor row_amortiz = db.rawQuery(sqlAmortiz, new String[]{idPrestamo});
        if (row_amortiz.getCount() > 0){
            row_amortiz.moveToFirst();

            Double abono = 0.0;
            if (!pagoRealizado.trim().isEmpty())
                abono = Double.parseDouble(pagoRealizado);

            for (int i = 0; i < row_amortiz.getCount(); i++){

                Double pendiente = row_amortiz.getDouble(2);

                Double res = abono - pendiente;

                if (res >= 0){
                    ContentValues cv_amortiz = new ContentValues();
                    cv_amortiz.put("total_pagado", "0");
                    cv_amortiz.put("pagado", "PENDIENTE");
                    cv_amortiz.put("dias_atraso", 0);
                    db.update(TBL_AMORTIZACIONES_T, cv_amortiz, "id_prestamo = ? AND numero = ?", new String[]{idPrestamo, row_amortiz.getString(5)});

                    abono = res;
                }
                else{
                    ContentValues cv_amortiz = new ContentValues();
                    cv_amortiz.put("total_pagado", abono);
                    cv_amortiz.put("pagado", "PARCIAL");

                    cv_amortiz.put("dias_atraso", Miscellaneous.GetDiasAtraso(row_amortiz.getString(4)));
                    db.update(TBL_AMORTIZACIONES_T, cv_amortiz, "id_prestamo = ? AND numero = ?", new String[]{idPrestamo, row_amortiz.getString(5)});

                    break;
                }

                row_amortiz.moveToNext();
            }

        }
        row_amortiz.close();

        sqlAmortiz = "SELECT SUM(a.total_pagado) AS suma_pagos, p.monto_total FROM " + TBL_AMORTIZACIONES_T + " AS a INNER JOIN "+TBL_PRESTAMOS_GPO_T+" AS p ON p.id_prestamo = a.id_prestamo WHERE a.id_prestamo = ?";
        row_amortiz = db.rawQuery(sqlAmortiz, new String[]{idPrestamo});

        if (row_amortiz.getCount() > 0){
            row_amortiz.moveToFirst();
            if (row_amortiz.getDouble(0) >= row_amortiz.getDouble(1)){
                ContentValues c = new ContentValues();
                c.put("pagada", 1);
                db.update(TBL_PRESTAMOS_GPO_T, c, "id_prestamo = ?", new String[]{idPrestamo});
            }
            else{
                ContentValues c = new ContentValues();
                c.put("pagada", 0);
                db.update(TBL_PRESTAMOS_GPO_T, c, "id_prestamo = ?", new String[]{idPrestamo});
            }

        }
        row_amortiz.close();
    }

    //Obtiene los tickets creados
    public void GetTickets(final Context ctx, final boolean showDG){
        final AlertDialog loading = Popups.showLoadingDialog(ctx, R.string.please_wait, R.string.loading_info);
        loading.setCancelable(false);

        //if ((!((Activity) ctx).isFinishing())) {
            if (showDG)
                loading.show();
        //}

        final DBhelper dBhelper = new DBhelper(ctx);
        final SQLiteDatabase db = dBhelper.getWritableDatabase();
        SessionManager session = new SessionManager(ctx);
        ManagerInterface api = new RetrofitClient().generalRF(CONTROLLER_SOPORTE, ctx).create(ManagerInterface.class);

        Call<List<MResTicket>> call = api.getTickets(session.getUser().get(9),"Bearer "+ session.getUser().get(7));
        call.enqueue(new Callback<List<MResTicket>>() {
            @Override
            public void onResponse(Call<List<MResTicket>> call, Response<List<MResTicket>> response) {
                Log.e("CodeGetTickets"," :"+response.code());
                switch (response.code()) {
                    case 200:
                        List<MResTicket> data = response.body();
                        if (data.size() > 0){
                            for (MResTicket item : data){
                                Cursor row = dBhelper.getRecords(TBL_SOPORTE, " WHERE folio_solicitud = ?", "", new String[]{String.valueOf(item.getId())});
                                Log.e("RowCheca", String.valueOf(row.getCount()));
                                if (row.getCount() == 0){
                                    //Crea ticket
                                    HashMap<Integer, String> params = new HashMap<>();
                                    params.put(0, String.valueOf(item.getCategoria()));
                                    if (item.getClienteId() == 0 && item.getGrupoId() == 0) {
                                        params.put(1, "0");
                                        params.put(4, "0");
                                        params.put(3, "0");
                                        params.put(5, "0");
                                    }
                                    else if (item.getClienteId() > 0) {
                                        params.put(1, "1");
                                        params.put(4, String.valueOf(item.getClienteId()));
                                        params.put(3, "0");
                                        params.put(5, String.valueOf(item.getNoSolicitud()));
                                    }
                                    else {
                                        params.put(1, "2");
                                        params.put(3, String.valueOf(item.getGrupoId()));
                                        params.put(4, "0");
                                        params.put(5, String.valueOf(item.getNoSolicitud()));
                                    }
                                    params.put(2, "");

                                    if (item.getCategoria() == 1)
                                        params.put(6, String.valueOf(item.getCategoria()));
                                    else
                                        params.put(6, "0");

                                    params.put(7, item.getComentario());
                                    params.put(8, String.valueOf(item.getId()));
                                    params.put(9, "0");
                                    params.put(10, item.getComentarioTecnico());
                                    params.put(11, item.getFechaSolicitud());
                                    params.put(12, item.getFechaEnvio());
                                    params.put(13, item.getEstatus());
                                    params.put(14, "1");

                                    Log.e("Params", params.toString());
                                    dBhelper.saveSoporte(db, params);
                                }
                                else{
                                    //Actualiza ticket
                                    row.moveToFirst();
                                    ContentValues cv = new ContentValues();
                                    cv.put("estatus_ticket", item.getEstatus());
                                    cv.put("comentario_admin", item.getComentarioTecnico());

                                    db.update(TBL_SOPORTE, cv, "_id = ?", new String[]{row.getString(0)});
                                }
                                row.close();
                            }
                        }
                        break;
                }

                //if ((!((Activity) ctx).isFinishing())) {
                    if (showDG)
                        loading.dismiss();
                //}
            }

            @Override
            public void onFailure(Call<List<MResTicket>> call, Throwable t) {
                //if ((!((Activity) ctx).isFinishing())) {
                    if (showDG)
                        loading.dismiss();
                //}
            }
        });
    }

    public void GetGestionadas(final Context ctx, final String tipo, final boolean showDG){

        final AlertDialog loading = Popups.showLoadingDialog(ctx, R.string.please_wait, R.string.loading_info);
        loading.setCancelable(false);

        //if ((!((Activity) ctx).isFinishing())) {
        if (showDG)
            loading.show();
        //}

        SessionManager session = new SessionManager(ctx);
        ManagerInterface api = new RetrofitClient().generalRF(CONTROLLER_MOVIL, ctx).create(ManagerInterface.class);
        Call<List<MRespGestionadas>> call = api.getGestionadas("Bearer "+ session.getUser().get(7));

        call.enqueue(new Callback<List<MRespGestionadas>>() {
            @Override
            public void onResponse(Call<List<MRespGestionadas>> call, Response<List<MRespGestionadas>> response) {
                Log.e("CodeGestionadas", "Code: "+response.code());
                switch (response.code()){
                    case 200:
                        List<MRespGestionadas> gestionadas = response.body();
                        if (gestionadas.size() > 0){
                            new GetGestionadas().execute(ctx, tipo, gestionadas);
                        }
                        break;
                }
                if (showDG)
                    loading.dismiss();
            }

            @Override
            public void onFailure(Call<List<MRespGestionadas>> call, Throwable t) {
                Log.e("Fail", "Error"+t.getMessage());
                if (showDG)
                    loading.dismiss();
            }
        });
    }

    //Obtiene los recibos creados
    public void GetUltimosRecibos(final Context ctx){
        final DBhelper dBhelper = new DBhelper(ctx);
        final SQLiteDatabase db = dBhelper.getWritableDatabase();
        final SessionManager session = new SessionManager(ctx);
        ManagerInterface api = new RetrofitClient().generalRF(CONTROLLER_RECIBOS, ctx).create(ManagerInterface.class);

        Call<List<MResUltimoRecibo>> call = api.getUltimosRecibos(session.getUser().get(0),"Bearer "+ session.getUser().get(7));
        call.enqueue(new Callback<List<MResUltimoRecibo>>() {
            @Override
            public void onResponse(Call<List<MResUltimoRecibo>> call, Response<List<MResUltimoRecibo>> response) {
                Log.e("RecibosGet", "asd: "+response.code());
                switch (response.code()) {
                    case 200:
                       List<MResUltimoRecibo> data = response.body();
                       for (MResUltimoRecibo item : data){
                           String sql = "SELECT * FROM " + TBL_RECIBOS + " WHERE curp = ? AND tipo_recibo = ? AND tipo_impresion = ? AND folio = ?";
                           Cursor row = db.rawQuery(sql, new String[]{item.getCurp(), item.getTipoRecibo(), item.getTipoImpresion(), String.valueOf(item.getFolio())});
                           if (row.getCount() > 0){
                               row.moveToFirst();
                               ContentValues cv = new ContentValues();
                               cv.put("fecha_envio", item.getFechaEnvio());
                               db.update(TBL_RECIBOS, cv, "_id = ?", new String[]{row.getString(0)});

                           }
                           else{
                               HashMap<Integer, String> params = new HashMap<>();
                               params.put(0, String.valueOf(item.getPrestamoId()));
                               params.put(1, session.getUser().get(0));
                               params.put(2, item.getTipoRecibo());
                               params.put(3, item.getTipoImpresion());
                               params.put(4, String.valueOf(item.getFolio()));
                               params.put(5, item.getMonto());
                               params.put(6, item.getClave());
                               params.put(7, item.getNombre());
                               params.put(8, "");
                               params.put(9, "");
                               params.put(10, item.getFechaImpreso());
                               params.put(11, item.getFechaEnvio());
                               params.put(12, "1");
                               params.put(13, item.getCurp());
                               dBhelper.saveRecibos(db, params);
                           }
                           row.close();
                       }
                       break;
                }
            }

            @Override
            public void onFailure(Call<List<MResUltimoRecibo>> call, Throwable t) {

            }
        });
    }

    public class RegistrarCierreDia extends AsyncTask<Object, Void, String>{

        @Override
        protected String doInBackground(Object... params) {
            Context ctx = (Context) params[0];
            final MCierreDia item = (MCierreDia) params[1];

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
                final File image_evidencia = new File(ROOT_PATH + "CierreDia/"+item.getEvidencia());
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
                        //Log.e("FailCierre", t.getMessage());
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

    public class GuardarRecibo extends AsyncTask<Object, Void, String>{

        @Override
        protected String doInBackground(final Object... params) {
            Context ctx = (Context) params[0];
            final MSendRecibo recibo = (MSendRecibo) params[1];

            Log.e("Recibo", Miscellaneous.ConvertToJson(recibo));
            final String id_recibo = (String) params[2];

            DBhelper dBhelper = new DBhelper(ctx);
            final SQLiteDatabase db = dBhelper.getWritableDatabase();
            SessionManager session = new SessionManager(ctx);

            ManagerInterface api = new RetrofitClient().generalRF(CONTROLLER_RECIBOS, ctx).create(ManagerInterface.class);


            Call<MResRecibo> call = api.guardarRecibo(recibo,
                    "Bearer "+ session.getUser().get(7));

            call.enqueue(new Callback<MResRecibo>() {
                @Override
                public void onResponse(Call<MResRecibo> call, Response<MResRecibo> response) {
                    Log.e("Recibo", "Code: "+response.code());
                    switch (response.code()){
                        case 200:
                            ContentValues cv = new ContentValues();
                            cv.put("fecha_envio", Miscellaneous.ObtenerFecha(TIMESTAMP));
                            cv.put("estatus", 1);
                            db.update(TBL_RECIBOS, cv, "_id = ?", new String[]{id_recibo});
                            break;
                    }
                }

                @Override
                public void onFailure(Call<MResRecibo> call, Throwable t) {

                }
            });

            return "";
        }
    }

    public class GuardarSoporte extends AsyncTask<Object, Void, String>{

        @Override
        protected String doInBackground(final Object... params) {
            Context ctx = (Context) params[0];
            final MSendSoporte soporte = (MSendSoporte) params[1];

            Log.e("Soporte", Miscellaneous.ConvertToJson(soporte));
            final String id_soporte = (String) params[2];

            DBhelper dBhelper = new DBhelper(ctx);
            final SQLiteDatabase db = dBhelper.getWritableDatabase();
            SessionManager session = new SessionManager(ctx);

            ManagerInterface api = new RetrofitClient().generalRF(CONTROLLER_SOPORTE, ctx).create(ManagerInterface.class);


            Call<MResSoporte> call = api.guardarTicket(soporte,
                    "Bearer "+ session.getUser().get(7));

            call.enqueue(new Callback<MResSoporte>() {
                @Override
                public void onResponse(Call<MResSoporte> call, Response<MResSoporte> response) {
                    Log.e("Soporte", "Code: "+response.code());
                    switch (response.code()){
                        case 200:
                            MResSoporte res = response.body();
                            ContentValues cv = new ContentValues();
                            cv.put("fecha_envio", Miscellaneous.ObtenerFecha(TIMESTAMP));
                            cv.put("folio_solicitud", res.getFolio());
                            cv.put("estatus_ticket", "ABIERTO");
                            cv.put("estatus_envio", 1);
                            int i = db.update(TBL_SOPORTE, cv, "_id = ?", new String[]{id_soporte});
                            Log.e("Actualizado", "Vlues: "+i);
                            break;
                    }
                }

                @Override
                public void onFailure(Call<MResSoporte> call, Throwable t) {

                }
            });

            return "";
        }
    }

    public class GuardarCancelGestiones extends AsyncTask<Object, Void, String>{

        @Override
        protected String doInBackground(Object... params) {
            Context context = (Context) params[0];
            String tipoGestion = (String) params[1];
            String tipoPrestamo = (String) params[2];
            String idRespuesta = (String) params[3];
            String comentario = (String) params[4];
            final String _id = (String) params[5];

            DBhelper dBhelper = new DBhelper(context);
            final SQLiteDatabase db = dBhelper.getWritableDatabase();
            SessionManager session = new SessionManager(context);

            String sql = "";
            if (tipoGestion.equals("1") && tipoPrestamo.equals("VIGENTE"))
                sql = "SELECT i.fecha_inicio, i.fecha_fin, i.id_prestamo, p.num_solicitud, 'VIGENTE' AS tipo, 1 AS tipo_gestion FROM " + TBL_RESPUESTAS_IND_T + " AS i INNER JOIN " + TBL_PRESTAMOS_IND_T + " AS p ON p.id_prestamo = i.id_prestamo WHERE i._id = ?";
            else if(tipoGestion.equals("2") && tipoPrestamo.equals("VIGENTE"))
                sql = "SELECT g.fecha_inicio, g.fecha_fin, g.id_prestamo, p.num_solicitud, 'VIGENTE' AS tipo, 2 AS tipo_gestion FROM " + TBL_RESPUESTAS_GPO_T + " AS g INNER JOIN " + TBL_PRESTAMOS_GPO_T + " AS p ON p.id_prestamo = g.id_prestamo WHERE g._id = ?";
            else if(tipoGestion.equals("1") && tipoPrestamo.equals("VENCIDA"))
                sql = "SELECT i.fecha_inicio, i.fecha_fin, i.id_prestamo, p.num_solicitud, 'VENCIDA' AS tipo, 1 AS tipo_gestion FROM " + TBL_RESPUESTAS_IND_V_T + " AS i INNER JOIN " + TBL_PRESTAMOS_IND_T + " AS p ON p.id_prestamo = i.id_prestamo WHERE i._id = ?";
            else if(tipoGestion.equals("2") && tipoPrestamo.equals("VENCIDA"))
                sql = "SELECT g.fecha_inicio, g.fecha_fin, g.id_prestamo, p.num_solicitud, 'VENCIDA' AS tipo, 2 AS tipo_gestion FROM " + TBL_RESPUESTAS_INTEGRANTE_T + " AS g INNER JOIN " + TBL_PRESTAMOS_GPO_T + " AS p ON p.id_prestamo = g.id_prestamo WHERE g._id = ?";
            Cursor row = db.rawQuery(sql, new String[]{idRespuesta});

            if (row.getCount() > 0){
                row.moveToFirst();
                try {

                    JSONObject item = new JSONObject();
                    item.put("fecha_inicio_gestion", row.getString(0));
                    item.put("fecha_fin_gestion", row.getString(1));
                    item.put("tipo_gestion", row.getInt(5));

                    RequestBody idPrestamoBody = RequestBody.create(MultipartBody.FORM, row.getString(2));
                    RequestBody numSolicitudBody = RequestBody.create(MultipartBody.FORM, row.getString(3));
                    RequestBody respuestaBody = RequestBody.create(MultipartBody.FORM, item.toString());
                    RequestBody tipoBody = RequestBody.create(MultipartBody.FORM, row.getString(4));
                    RequestBody comentarioBody = RequestBody.create(MultipartBody.FORM, comentario.trim().toUpperCase());
                    RequestBody fechaSoliBody = RequestBody.create(MultipartBody.FORM, Miscellaneous.ObtenerFecha(TIMESTAMP));

                    ManagerInterface api = new RetrofitClient().generalRF(CONTROLLER_MOVIL, context).create(ManagerInterface.class);

                    Call<MSolicitudCancelacion> call = api.solicitudCancelar("Bearer "+ session.getUser().get(7),
                            idPrestamoBody,
                            numSolicitudBody,
                            respuestaBody,
                            tipoBody,
                            comentarioBody,
                            fechaSoliBody);

                    call.enqueue(new Callback<MSolicitudCancelacion>() {
                        @Override
                        public void onResponse(Call<MSolicitudCancelacion> call, Response<MSolicitudCancelacion> response) {
                            MSolicitudCancelacion resp = response.body();
                            switch (response.code()){
                                case 200:
                                    ContentValues cv = new ContentValues();
                                    cv.put("id_solicitud", resp.getIdCancelacion());
                                    cv.put("estatus", "PENDIENTE");
                                    db.update(TBL_CANCELACIONES, cv, "_id = ?", new String[]{String.valueOf(_id)});
                                    break;
                            }
                        }

                        @Override
                        public void onFailure(Call<MSolicitudCancelacion> call, Throwable t) {

                        }
                    });



                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            row.close();
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

    public class GuardarSolicitudInd extends AsyncTask<Object, Void, String>{

        @Override
        protected String doInBackground(final Object... params) {
            Context ctx = (Context) params[0];
            JSONObject solicitud = (JSONObject) params[1];
            String fachadaCli = (String) params[2];
            String firmaCli = (String) params[3];
            String fachadaNeg = (String) params[4];
            String fachadaAval = (String) params[5];
            String firmaAval = (String) params[6];
            String identifiFrontal = (String) params[7];
            String identifiReverso = (String) params[8];
            String curp = (String) params[9];
            String comprobante = (String) params[10];
            String firmaAsesor = (String) params[11];
            final String id = (String) params[12];

            SessionManager session = new SessionManager(ctx);
            DBhelper dBhelper = new DBhelper(ctx);
            final SQLiteDatabase db = dBhelper.getWritableDatabase();


            MultipartBody.Part fachada_cliente = null;
            File image_fachada_cliente = new File(ROOT_PATH + "Fachada/"+fachadaCli);

            if (image_fachada_cliente != null) {
                RequestBody imageBodyFachadaCli =
                        RequestBody.create(
                                MediaType.parse("image/*"), image_fachada_cliente);

                fachada_cliente = MultipartBody.Part.createFormData("fachada_cliente", image_fachada_cliente.getName(), imageBodyFachadaCli);
            }

            MultipartBody.Part firma_cliente = null;
            File image_firma_cliente = new File(ROOT_PATH + "Firma/"+firmaCli);
            if (image_fachada_cliente != null) {
                RequestBody imageBodyFirmaCli =
                        RequestBody.create(
                                MediaType.parse("image/*"), image_firma_cliente);

                firma_cliente = MultipartBody.Part.createFormData("firma_cliente", image_firma_cliente.getName(), imageBodyFirmaCli);
            }

            MultipartBody.Part fachada_negocio = null;
            File image_fachada_negocio = new File(ROOT_PATH + "Fachada/"+fachadaNeg);
            if (image_fachada_negocio != null) {
                RequestBody imageBodyFachadaNeg =
                        RequestBody.create(
                                MediaType.parse("image/*"), image_fachada_negocio);

                fachada_negocio = MultipartBody.Part.createFormData("fachada_negocio", image_fachada_negocio.getName(), imageBodyFachadaNeg);
            }

            MultipartBody.Part fachada_aval = null;
            File image_fachada_aval = new File(ROOT_PATH + "Fachada/"+fachadaAval);
            if (image_fachada_aval != null) {
                RequestBody imageBodyFachadaAval =
                        RequestBody.create(
                                MediaType.parse("image/*"), image_fachada_aval);

                fachada_aval = MultipartBody.Part.createFormData("fachada_aval", image_fachada_aval.getName(), imageBodyFachadaAval);
            }

            MultipartBody.Part firma_aval = null;
            File image_firma_aval = new File(ROOT_PATH + "Firma/"+firmaAval);
            if (image_fachada_aval != null) {
                RequestBody imageBodyFirmaAval =
                        RequestBody.create(
                                MediaType.parse("image/*"), image_firma_aval);

                firma_aval = MultipartBody.Part.createFormData("firma_aval", image_firma_aval.getName(), imageBodyFirmaAval);
            }

            MultipartBody.Part foto_ine_frontal = null;
            File image_ine_frontal = new File(ROOT_PATH + "Documentos/"+identifiFrontal);
            if (image_ine_frontal != null) {
                RequestBody imageBody =
                        RequestBody.create(
                                MediaType.parse("image/*"), image_ine_frontal);

                foto_ine_frontal = MultipartBody.Part.createFormData("identificacion_frontal", image_ine_frontal.getName(), imageBody);
            }

            MultipartBody.Part foto_ine_reverso = null;
            File image_ine_reverso = new File(ROOT_PATH + "Documentos/"+identifiReverso);
            if (image_ine_reverso != null) {
                RequestBody imageBody =
                        RequestBody.create(
                                MediaType.parse("image/*"), image_ine_reverso);

                foto_ine_reverso = MultipartBody.Part.createFormData("identificacion_reverso", image_ine_reverso.getName(), imageBody);
            }

            MultipartBody.Part foto_curp = null;
            File image_curp = new File(ROOT_PATH + "Documentos/"+curp);
            if (image_curp != null) {
                RequestBody imageBody =
                        RequestBody.create(
                                MediaType.parse("image/*"), image_curp);

                foto_curp = MultipartBody.Part.createFormData("curp", image_curp.getName(), imageBody);
            }

            MultipartBody.Part foto_comprobante = null;
            File image_comprobante = new File(ROOT_PATH + "Documentos/"+comprobante);
            if (image_comprobante != null) {
                RequestBody imageBody =
                        RequestBody.create(
                                MediaType.parse("image/*"), image_comprobante);

                foto_comprobante = MultipartBody.Part.createFormData("comprobante_domicilio", image_comprobante.getName(), imageBody);
            }

            MultipartBody.Part firma_asesor = null;

            File image_firma_asesor = new File(ROOT_PATH + "Firma/"+firmaAsesor);
            if (image_firma_asesor != null) {
                RequestBody imageBody =
                        RequestBody.create(
                                MediaType.parse("image/*"), image_firma_asesor);

                firma_asesor = MultipartBody.Part.createFormData("firma_asesor", image_firma_asesor.getName(), imageBody);
            }

            RequestBody solicitudBody = RequestBody.create(MultipartBody.FORM, solicitud.toString());

            Log.e("solicitud", solicitud.toString());
            ManagerInterface api = new RetrofitClient().generalRF(CONTROLLER_SOLICITUDES, ctx).create(ManagerInterface.class);

            Call<MResSaveSolicitud> call = api.guardarOriginacionInd("Bearer "+ session.getUser().get(7),
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

            call.enqueue(new Callback<MResSaveSolicitud>() {
                @Override
                public void onResponse(Call<MResSaveSolicitud> call, Response<MResSaveSolicitud> response) {
                    Log.e("Respuesta code", response.code()+" codigo");

                    switch (response.code()){
                        case 200:
                            MResSaveSolicitud res = response.body();
                            ContentValues cv = new ContentValues();
                            cv.put("estatus", "2");
                            cv.put("id_originacion",res.getIdSolicitud());
                            cv.put("fecha_guardado", Miscellaneous.ObtenerFecha(TIMESTAMP));
                            db.update(TBL_SOLICITUDES, cv, "id_solicitud = ?", new String[]{id});
                            break;
                        case 500:

                            break;
                    }

                }

                @Override
                public void onFailure(Call<MResSaveSolicitud> call, Throwable t) {
                   Log.e("Error", "failSolicitud");

                }
            });

            return "";
        }
    }

    public class GuardarSolicitudGpo extends AsyncTask<Object, Void, String>{

        @Override
        protected String doInBackground(final Object... params) {
            Context ctx = (Context) params[0];
            JSONObject solicitud = (JSONObject) params[1];
            String fachadaCli = (String) params[2];
            String firmaCli = (String) params[3];
            String fachadaNeg = (String) params[4];
            String identifiFrontal = (String) params[5];
            String identifiReverso = (String) params[6];
            String curp = (String) params[7];
            String comprobante = (String) params[8];
            final String id = (String) params[9];
            final String credito = (String) params[10];
            final String id_solicitud = (String) params[11];

            SessionManager session = new SessionManager(ctx);
            DBhelper dBhelper = new DBhelper(ctx);
            final SQLiteDatabase db = dBhelper.getWritableDatabase();


            MultipartBody.Part fachada_cliente = null;
            File image_fachada_cliente = new File(ROOT_PATH + "Fachada/"+fachadaCli);

            if (image_fachada_cliente != null) {
                RequestBody imageBodyFachadaCli =
                        RequestBody.create(
                                MediaType.parse("image/*"), image_fachada_cliente);

                fachada_cliente = MultipartBody.Part.createFormData("fachada_cliente", image_fachada_cliente.getName(), imageBodyFachadaCli);
            }

            MultipartBody.Part firma_cliente = null;
            File image_firma_cliente = new File(ROOT_PATH + "Firma/"+firmaCli);
            if (image_fachada_cliente != null) {
                RequestBody imageBodyFirmaCli =
                        RequestBody.create(
                                MediaType.parse("image/*"), image_firma_cliente);

                firma_cliente = MultipartBody.Part.createFormData("firma_cliente", image_firma_cliente.getName(), imageBodyFirmaCli);
            }

            MultipartBody.Part fachada_negocio = null;
            File image_fachada_negocio = new File(ROOT_PATH + "Fachada/"+fachadaNeg);
            if (image_fachada_negocio != null) {
                RequestBody imageBodyFachadaNeg =
                        RequestBody.create(
                                MediaType.parse("image/*"), image_fachada_negocio);

                fachada_negocio = MultipartBody.Part.createFormData("fachada_negocio", image_fachada_negocio.getName(), imageBodyFachadaNeg);
            }

            MultipartBody.Part foto_ine_frontal = null;
            File image_ine_frontal = new File(ROOT_PATH + "Documentos/"+identifiFrontal);
            if (image_ine_frontal != null) {
                RequestBody imageBody =
                        RequestBody.create(
                                MediaType.parse("image/*"), image_ine_frontal);

                foto_ine_frontal = MultipartBody.Part.createFormData("identificacion_frontal", image_ine_frontal.getName(), imageBody);
            }

            MultipartBody.Part foto_ine_reverso = null;
            File image_ine_reverso = new File(ROOT_PATH + "Documentos/"+identifiReverso);
            if (image_ine_reverso != null) {
                RequestBody imageBody =
                        RequestBody.create(
                                MediaType.parse("image/*"), image_ine_reverso);

                foto_ine_reverso = MultipartBody.Part.createFormData("identificacion_reverso", image_ine_reverso.getName(), imageBody);
            }

            MultipartBody.Part foto_curp = null;
            File image_curp = new File(ROOT_PATH + "Documentos/"+curp);
            if (image_curp != null) {
                RequestBody imageBody =
                        RequestBody.create(
                                MediaType.parse("image/*"), image_curp);

                foto_curp = MultipartBody.Part.createFormData("curp", image_curp.getName(), imageBody);
            }

            MultipartBody.Part foto_comprobante = null;
            File image_comprobante = new File(ROOT_PATH + "Documentos/"+comprobante);
            if (image_comprobante != null) {
                RequestBody imageBody =
                        RequestBody.create(
                                MediaType.parse("image/*"), image_comprobante);

                foto_comprobante = MultipartBody.Part.createFormData("comprobante_domicilio", image_comprobante.getName(), imageBody);
            }

            /*MultipartBody.Part firma_asesor = null;

            File image_firma_asesor = new File(ROOT_PATH + "Firma/"+firmaAsesor);
            if (image_firma_asesor != null) {
                RequestBody imageBody =
                        RequestBody.create(
                                MediaType.parse("image/*"), image_firma_asesor);

                firma_asesor = MultipartBody.Part.createFormData("firma_asesor", image_firma_asesor.getName(), imageBody);
            }*/

            RequestBody solicitudBody = RequestBody.create(MultipartBody.FORM, solicitud.toString());

            Log.e("solicitud", solicitud.toString());
            ManagerInterface api = new RetrofitClient().generalRF(CONTROLLER_SOLICITUDES, ctx).create(ManagerInterface.class);

            Call<MResSaveSolicitud> call = api.guardarOriginacionGpo("Bearer "+ session.getUser().get(7),
                    solicitudBody,
                    fachada_cliente,
                    firma_cliente,
                    fachada_negocio,
                    foto_ine_frontal,
                    foto_ine_reverso,
                    foto_curp,
                    foto_comprobante);

            call.enqueue(new Callback<MResSaveSolicitud>() {
                @Override
                public void onResponse(Call<MResSaveSolicitud> call, Response<MResSaveSolicitud> response) {
                    Log.e("Respuesta code", response.code()+" codigo");

                    switch (response.code()){
                        case 200:
                            MResSaveSolicitud res = response.body();
                            ContentValues cv = new ContentValues();
                            cv.put("id_solicitud_integrante",res.getIdSolicitud());
                            db.update(TBL_INTEGRANTES_GPO, cv, "id = ?", new String[]{id});

                            String sql = "SELECT id_solicitud_integrante FROM " + TBL_INTEGRANTES_GPO + " WHERE id_credito = ? AND id_solicitud_integrante = ?";
                            Cursor r = db.rawQuery(sql, new String[]{credito, "0"});
                            if (r.getCount() == 0){
                                cv = new ContentValues();
                                cv.put("estatus", "2");
                                cv.put("fecha_guardado", Miscellaneous.ObtenerFecha(TIMESTAMP));
                                db.update(TBL_SOLICITUDES, cv, "id_solicitud = ?", new String[]{id_solicitud});
                            }
                            break;
                        case 500:

                            break;
                    }

                }

                @Override
                public void onFailure(Call<MResSaveSolicitud> call, Throwable t) {
                    Log.e("Error", "failSolicitud");

                }
            });

            return "";
        }
    }

    public class GetGestionadas extends AsyncTask<Object, Void, Integer>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Integer doInBackground(Object... obj) {
            Context ctx = (Context)obj[0];
            final DBhelper dBhelper = new DBhelper(ctx);
            final SQLiteDatabase db = dBhelper.getWritableDatabase();
            final String tipo = (String) obj[1];
            List<MRespGestionadas> gestionadas = (List<MRespGestionadas>) obj[2];

            Log.e("Total", gestionadas.size()+" Total");

            for(MRespGestionadas item : gestionadas){
                String sql = "";
                Cursor row;
                HashMap<Integer, String> params = new HashMap<>();
                if (tipo.equals("VIGENTE")){
                    if (item.getTipoGestion() == 1){
                        sql = "SELECT * FROM " + TBL_RESPUESTAS_IND_T + " WHERE id_prestamo = ? AND fecha_inicio = ? AND fecha_fin = ?";
                        row = db.rawQuery(sql,new String[]{String.valueOf(item.getPrestamoId()), item.getFechaInicioGestion().substring(0,19).replace("T"," "),item.getFechaFinGestion().substring(0,19).replace("T"," ")});

                        if (row.getCount() == 0){
                            params.put(0, String.valueOf(item.getPrestamoId())); //ID PRESTAMO
                            params.put(1, String.valueOf(item.getLatitud())); //LATITUD
                            params.put(2, String.valueOf(item.getLongitud())); //LONGITUD
                            params.put(3, "SI"); //CONTACTO
                            params.put(4, ""); //MOTIVO ACLARACION
                            params.put(5, ""); //COMENTARIO
                            params.put(6, Miscellaneous.GetConfirmacion(item.getActualizarTelefono())); //ACTUALIZAR TELEFONO
                            params.put(7, Miscellaneous.validStr(item.getTelefonoNuevo())); //NUEVO TELEFONO
                            params.put(8, "PAGO"); //RESULTADO GESTION
                            params.put(9, ""); //MOTIVO NO PAGO
                            params.put(10, ""); //FECHA FALLECIMIENTO
                            params.put(11, Miscellaneous.GetMedioPago(item.getMedioPago())); //MEDIO PAGO
                            params.put(12, Miscellaneous.validStr(item.getFechaPago())); //FECHA PAGO
                            params.put(13, Miscellaneous.GetMedioPago(item.getPagaraRequerido())); //PAGARA REQUERIDO
                            params.put(14, String.valueOf(item.getPagoRealizado())); //PAGO REALIZADO
                            if (item.getMedioPago() != 6) {
                                params.put(15, ""); //IMPRIMIR RECIBO
                                params.put(16, ""); //FOLIO
                                params.put(25, "0"); //RES IMPRESION
                            }
                            else {
                                Log.e("FolioXXX", Miscellaneous.validStr(item.getImprimirRecibo()));
                                params.put(15, Miscellaneous.GetImprimirRecibo(Integer.parseInt(Miscellaneous.validStr(item.getImprimirRecibo()).replace(".0","")))); //IMPRIMIR RECIBO
                                params.put(16, Miscellaneous.validStr(item.getFolio())); //FOLIO
                                params.put(25, String.valueOf(item.getResImpresion())); //RES IMPRESION
                            }
                            params.put(17, ""); //EVIDENCIA
                            params.put(18, Miscellaneous.GetTipoImagen(item.getTipoImagen())); //TIPO IMAGEN
                            params.put(19, Miscellaneous.GetConfirmacion(item.getSupervisonGerente())); //GERENTE
                            params.put(20, ""); //FIRMA
                            params.put(21, item.getFechaInicioGestion().substring(0,19).replace("T"," ")); //FECHA INICIO
                            params.put(22, item.getFechaFinGestion().substring(0,19).replace("T"," ")); //FECHA FIN
                            params.put(23, item.getFechaEnvioDispositivo().substring(0,19).replace("T"," ")); //FECHA ENVIO
                            params.put(24, "2"); //ESTATUS
                            params.put(26, item.getEstatus().replace("0", "")); //ESTATUS PAGO
                            params.put(27, String.valueOf(item.getSaldoCorte())); //SALDO CORTE
                            params.put(28, String.valueOf(item.getSaldoActual())); //SALDO ACTUAL
                            params.put(29, String.valueOf(item.getAtraso())); //DIAS ATRASO

                            dBhelper.saveRespuestasInd(db, TBL_RESPUESTAS_IND_T, params);

                            String sqlAmortiz = "SELECT _id, total, total_pagado, pagado, fecha, numero FROM " + TBL_AMORTIZACIONES_T + " WHERE id_prestamo = ? AND CAST(total AS DOUBLE) > CAST(total_pagado AS DOUBLE) ORDER BY numero ASC";
                            Cursor row_amortiz = db.rawQuery(sqlAmortiz, new String[]{String.valueOf(item.getPrestamoId())});
                            Log.e("RowAmortiz",String.valueOf(row_amortiz.getCount()));
                            Log.e("--","------------------------------------------------");
                            if (row_amortiz.getCount() > 0){
                                row_amortiz.moveToFirst();
                                Log.e("ItemPago", String.valueOf(item.getPagoRealizado()));
                                Double abono = item.getPagoRealizado();
                                for (int i = 0; i < row_amortiz.getCount(); i++){

                                    Double pendiente = row_amortiz.getDouble(1) - row_amortiz.getDouble(2);

                                    if (abono > pendiente){
                                        ContentValues cv_amortiz = new ContentValues();
                                        cv_amortiz.put("total_pagado", row_amortiz.getString(1));
                                        cv_amortiz.put("pagado", "PAGADO");
                                        cv_amortiz.put("dias_atraso", Miscellaneous.GetDiasAtraso(row_amortiz.getString(4)));
                                        db.update(TBL_AMORTIZACIONES_T, cv_amortiz, "id_prestamo = ? AND numero = ?", new String[]{String.valueOf(item.getPrestamoId()), row_amortiz.getString(5)});
                                        abono = abono - pendiente;
                                    }
                                    else if (abono == pendiente){
                                        ContentValues cv_amortiz = new ContentValues();
                                        cv_amortiz.put("total_pagado", row_amortiz.getString(1));
                                        cv_amortiz.put("pagado", "PAGADO");
                                        cv_amortiz.put("dias_atraso", Miscellaneous.GetDiasAtraso(row_amortiz.getString(4)));
                                        db.update(TBL_AMORTIZACIONES_T, cv_amortiz, "id_prestamo = ? AND numero = ?", new String[]{String.valueOf(item.getPrestamoId()), row_amortiz.getString(5)});
                                        abono = 0.0;
                                    }
                                    else if (abono > 0 && abono < pendiente){
                                        ContentValues cv_amortiz = new ContentValues();
                                        cv_amortiz.put("total_pagado", (row_amortiz.getDouble(2) + abono));
                                        cv_amortiz.put("pagado", "PARCIAL");
                                        abono = 0.0;
                                        cv_amortiz.put("dias_atraso", Miscellaneous.GetDiasAtraso(row_amortiz.getString(4)));
                                        db.update(TBL_AMORTIZACIONES_T, cv_amortiz, "id_prestamo = ? AND numero = ?", new String[]{String.valueOf(item.getPrestamoId()), row_amortiz.getString(5)});
                                    }
                                    else
                                        break;

                                    row_amortiz.moveToNext();
                                }

                            }
                            row_amortiz.close();

                            sqlAmortiz = "SELECT SUM(a.total_pagado) AS suma_pagos, p.monto_total FROM " + TBL_AMORTIZACIONES_T + " AS a INNER JOIN "+TBL_PRESTAMOS_IND_T+" AS p ON p.id_prestamo = a.id_prestamo WHERE a.id_prestamo = ?";
                            row_amortiz = db.rawQuery(sqlAmortiz, new String[]{String.valueOf(item.getPrestamoId())});

                            if (row_amortiz.getCount() > 0){
                                row_amortiz.moveToFirst();
                                if (row_amortiz.getDouble(0) >= row_amortiz.getDouble(1)){
                                    ContentValues c = new ContentValues();
                                    c.put("pagada", 1);
                                    db.update(TBL_PRESTAMOS_IND_T, c, "id_prestamo = ?", new String[]{String.valueOf(item.getPrestamoId())});
                                }

                            }
                            row_amortiz.close();
                        }
                        row.close();
                    }
                    else if (item.getTipoGestion() == 2){
                        sql = "SELECT * FROM " + TBL_RESPUESTAS_GPO_T + " WHERE id_prestamo = ? AND fecha_inicio = ? AND fecha_fin = ?";
                        row = db.rawQuery(sql,new String[]{String.valueOf(item.getPrestamoId()), item.getFechaInicioGestion().substring(0,19).replace("T"," "),item.getFechaFinGestion().substring(0,19).replace("T"," ")});

                        if (row.getCount() == 0){
                            params.put(0, String.valueOf(item.getPrestamoId())); //ID PRESTAMO
                            params.put(1, String.valueOf(item.getLatitud())); //LATITUD
                            params.put(2, String.valueOf(item.getLongitud())); //LONGITUD
                            params.put(3, "SI"); //CONTACTO
                            params.put(4, ""); //MOTIVO ACLARACION
                            params.put(5, ""); //COMENTARIO
                            params.put(6, Miscellaneous.GetConfirmacion(item.getActualizarTelefono())); //ACTUALIZAR TELEFONO
                            params.put(7, Miscellaneous.validStr(item.getTelefonoNuevo())); //NUEVO TELEFONO
                            params.put(8, "PAGO"); //RESULTADO GESTION
                            params.put(9, ""); //MOTIVO NO PAGO
                            params.put(10, ""); //FECHA FALLECIMIENTO
                            params.put(11, Miscellaneous.GetMedioPago(item.getMedioPago())); //MEDIO PAGO
                            params.put(12, Miscellaneous.validStr(item.getFechaPago())); //FECHA PAGO
                            params.put(13, Miscellaneous.GetConfirmacion(item.getDetalleFicha())); //DETALLE FICHA
                            params.put(14, String.valueOf(item.getPagoRealizado())); //PAGO REALIZADO
                            if (item.getMedioPago() != 6) {
                                params.put(15, ""); //IMPRIMIR RECIBO
                                params.put(16, ""); //FOLIO
                                params.put(25, "0"); //RES_IMPRESION
                            }
                            else{
                                params.put(15, Miscellaneous.GetImprimirRecibo(Integer.parseInt(Miscellaneous.validStr(item.getImprimirRecibo()).replace(".0","")))); //IMPRIMIR RECIBO
                                params.put(16, Miscellaneous.validStr(item.getFolio())); //FOLIO
                                params.put(25, String.valueOf(item.getResImpresion())); //RES_IMPRESION
                            }
                            params.put(17, ""); //EVIDENCIA
                            params.put(18, Miscellaneous.GetTipoImagen(item.getTipoImagen())); //TIPO IMAGEN
                            params.put(19, Miscellaneous.GetConfirmacion(item.getSupervisonGerente())); //GERENTE
                            params.put(20, ""); //FIRMA
                            params.put(21, item.getFechaInicioGestion().substring(0,19).replace("T"," ")); //FECHA INICIO
                            params.put(22, item.getFechaFinGestion().substring(0,19).replace("T"," ")); //FECHA FIN
                            params.put(23, item.getFechaEnvioDispositivo().substring(0,19).replace("T"," ")); //FECHA ENVIO
                            params.put(24, "2"); //ESTATUS
                            params.put(26, "0"); //ARQUEO CAJA
                            params.put(27, item.getEstatus().replace("0", "")); //ESTATUS PAGO
                            params.put(28, String.valueOf(item.getSaldoCorte())); //SALDO CORTE
                            params.put(29, String.valueOf(item.getSaldoActual())); //SALDO ACTUAL
                            params.put(30, String.valueOf(item.getAtraso())); //DIAS ATRASO

                            dBhelper.saveRespuestasGpo(db, params);

                            String sqlAmortiz = "SELECT _id, total, total_pagado, pagado, fecha, numero FROM " + TBL_AMORTIZACIONES_T + " WHERE id_prestamo = ? AND CAST(total AS DOUBLE) > CAST(total_pagado AS DOUBLE) ORDER BY numero ASC";
                            Cursor row_amortiz = db.rawQuery(sqlAmortiz, new String[]{String.valueOf(item.getPrestamoId())});
                            if (row_amortiz.getCount() > 0){
                                row_amortiz.moveToFirst();
                                Double abono = item.getPagoRealizado();

                                for (int i = 0; i < row_amortiz.getCount(); i++){

                                    Double pendiente = row_amortiz.getDouble(1) - row_amortiz.getDouble(2);

                                    if (abono > pendiente){
                                        ContentValues cv_amortiz = new ContentValues();
                                        cv_amortiz.put("total_pagado", row_amortiz.getString(1));
                                        cv_amortiz.put("pagado", "PAGADO");
                                        cv_amortiz.put("dias_atraso", Miscellaneous.GetDiasAtraso(row_amortiz.getString(4)));
                                        db.update(TBL_AMORTIZACIONES_T, cv_amortiz, "id_prestamo = ? AND numero = ?", new String[]{String.valueOf(item.getPrestamoId()), row_amortiz.getString(5)});
                                        abono = abono - pendiente;
                                    }
                                    else if (abono == pendiente){
                                        ContentValues cv_amortiz = new ContentValues();
                                        cv_amortiz.put("total_pagado", row_amortiz.getString(1));
                                        cv_amortiz.put("pagado", "PAGADO");
                                        cv_amortiz.put("dias_atraso", Miscellaneous.GetDiasAtraso(row_amortiz.getString(4)));
                                        db.update(TBL_AMORTIZACIONES_T, cv_amortiz, "id_prestamo = ? AND numero = ?", new String[]{String.valueOf(item.getPrestamoId()), row_amortiz.getString(5)});
                                        abono = 0.0;
                                    }
                                    else if (abono > 0 && abono < pendiente){
                                        ContentValues cv_amortiz = new ContentValues();
                                        cv_amortiz.put("total_pagado", (row_amortiz.getDouble(2) + abono));
                                        cv_amortiz.put("pagado", "PARCIAL");
                                        abono = 0.0;
                                        cv_amortiz.put("dias_atraso", Miscellaneous.GetDiasAtraso(row_amortiz.getString(4)));
                                        db.update(TBL_AMORTIZACIONES_T, cv_amortiz, "id_prestamo = ? AND numero = ?", new String[]{String.valueOf(item.getPrestamoId()), row_amortiz.getString(5)});

                                    }
                                    else
                                        break;

                                    row_amortiz.moveToNext();
                                }

                            }
                            row_amortiz.close();
                        }
                        row.close();
                    }
                }
                else if (tipo.equals("VENCIDA")){
                    if (item.getTipoGestion() == 1){
                        sql = "SELECT * FROM " + TBL_RESPUESTAS_IND_V_T + " WHERE id_prestamo = ? AND fecha_inicio = ? AND fecha_fin = ?";
                        row = db.rawQuery(sql,new String[]{String.valueOf(item.getPrestamoId()), item.getFechaInicioGestion().substring(0,19).replace("T"," "),item.getFechaFinGestion().substring(0,19).replace("T"," ")});

                        if (row.getCount() == 0){
                            params.put(0, String.valueOf(item.getPrestamoId())); //ID PRESTAMO
                            params.put(1, String.valueOf(item.getLatitud())); //LATITUD
                            params.put(2, String.valueOf(item.getLongitud())); //LONGITUD
                            params.put(3, "SI"); //CONTACTO
                            params.put(4, ""); //COMENTARIO
                            params.put(5, Miscellaneous.GetConfirmacion(item.getActualizarTelefono())); //ACTUALIZAR TELEFONO
                            params.put(6, Miscellaneous.validStr(item.getTelefonoNuevo())); //NUEVO TELEFONO
                            params.put(7, "PAGO"); //RESULTADO GESTION
                            params.put(8, ""); //MOTIVO NO PAGO
                            params.put(9, ""); //FECHA FALLECIMIENTO
                            params.put(10, ""); //FECHA MONTO PROMESA
                            params.put(11, ""); //MONTO PROMESA
                            params.put(12, Miscellaneous.GetMedioPago(item.getMedioPago())); //MEDIO PAGO
                            params.put(13, Miscellaneous.validStr(item.getFechaPago())); //FECHA PAGO
                            params.put(14, Miscellaneous.GetConfirmacion(item.getPagaraRequerido())); //PAGARA REQUERIDO
                            params.put(15, String.valueOf(item.getPagoRealizado())); //PAGO REALIZADO
                            if (item.getMedioPago() != 6) {
                                params.put(16, ""); //IMPRIMIR RECIBO
                                params.put(17, ""); //FOLIO
                                params.put(26, "0"); //RES IMPRESION
                            }
                            else{
                                params.put(16, Miscellaneous.GetImprimirRecibo(Integer.parseInt(Miscellaneous.validStr(item.getImprimirRecibo()).replace(".0","")))); //IMPRIMIR RECIBO
                                params.put(17, Miscellaneous.validStr(item.getFolio())); //FOLIO
                                params.put(26, String.valueOf(item.getResImpresion())); //RES IMPRESION
                            }
                            params.put(18, ""); //EVIDENCIA
                            params.put(19, Miscellaneous.GetTipoImagen(item.getTipoImagen())); //TIPO IMAGEN
                            params.put(20, Miscellaneous.GetConfirmacion(item.getSupervisonGerente())); //GERENTE
                            params.put(21, ""); //FIRMA
                            params.put(22, item.getFechaInicioGestion().substring(0,19).replace("T"," ")); //FECHA INICIO
                            params.put(23, item.getFechaFinGestion().substring(0,19).replace("T"," ")); //FECHA FIN
                            params.put(24, item.getFechaEnvioDispositivo().substring(0,19).replace("T"," ")); //FECHA ENVIO
                            params.put(25, "2"); //ESTATUS
                            params.put(27, item.getEstatus().replace("0", "")); //ESTATUS PAGO
                            params.put(28, String.valueOf(item.getSaldoCorte())); //SALDO CORTE
                            params.put(29, String.valueOf(item.getSaldoActual())); //SALDO ACTUAL
                            params.put(30, String.valueOf(item.getAtraso())); //DIAS ATRASO
                            params.put(31, Miscellaneous.validStr(item.getSerialId())); //SERIAL ID

                            dBhelper.saveRespuestasVencidasInd(db, params);

                            String sqlAmortiz = "SELECT _id, total, total_pagado, pagado, fecha, numero FROM " + TBL_AMORTIZACIONES_T + " WHERE id_prestamo = ? AND CAST(total AS DOUBLE) > CAST(total_pagado AS DOUBLE) ORDER BY numero ASC";
                            Cursor row_amortiz = db.rawQuery(sqlAmortiz, new String[]{String.valueOf(item.getPrestamoId())});
                            if (row_amortiz.getCount() > 0){
                                row_amortiz.moveToFirst();
                                Double abono = item.getPagoRealizado();
                                for (int i = 0; i < row_amortiz.getCount(); i++){

                                    Double pendiente = row_amortiz.getDouble(1) - row_amortiz.getDouble(2);

                                    if (abono > pendiente){
                                        ContentValues cv_amortiz = new ContentValues();
                                        cv_amortiz.put("total_pagado", row_amortiz.getString(1));
                                        cv_amortiz.put("pagado", "PAGADO");
                                        cv_amortiz.put("dias_atraso", Miscellaneous.GetDiasAtraso(row_amortiz.getString(4)));
                                        db.update(TBL_AMORTIZACIONES_T, cv_amortiz, "id_prestamo = ? AND numero = ?", new String[]{String.valueOf(item.getPrestamoId()), row_amortiz.getString(5)});
                                        abono = abono - pendiente;
                                    }
                                    else if (abono == pendiente){
                                        ContentValues cv_amortiz = new ContentValues();
                                        cv_amortiz.put("total_pagado", row_amortiz.getString(1));
                                        cv_amortiz.put("pagado", "PAGADO");
                                        cv_amortiz.put("dias_atraso", Miscellaneous.GetDiasAtraso(row_amortiz.getString(4)));
                                        db.update(TBL_AMORTIZACIONES_T, cv_amortiz, "id_prestamo = ? AND numero = ?", new String[]{String.valueOf(item.getPrestamoId()), row_amortiz.getString(5)});
                                        abono = 0.0;
                                    }
                                    else if (abono > 0 && abono < pendiente){
                                        ContentValues cv_amortiz = new ContentValues();
                                        cv_amortiz.put("total_pagado", abono);
                                        cv_amortiz.put("pagado", "PARCIAL");
                                        abono = 0.0;
                                        cv_amortiz.put("dias_atraso", Miscellaneous.GetDiasAtraso(row_amortiz.getString(4)));
                                        db.update(TBL_AMORTIZACIONES_T, cv_amortiz, "id_prestamo = ? AND numero = ?", new String[]{String.valueOf(item.getPrestamoId()), row_amortiz.getString(5)});

                                    }
                                    else
                                        break;

                                    row_amortiz.moveToNext();
                                }

                            }
                            row_amortiz.close();

                        }
                        row.close();
                    }
                    else if (item.getTipoGestion() == 2){

                        sql = "SELECT * FROM " + TBL_RESPUESTAS_INTEGRANTE_T + " WHERE id_prestamo = ? AND fecha_inicio = ? AND fecha_fin = ?";

                        row = db.rawQuery(sql,new String[]{String.valueOf(item.getPrestamoId()), item.getFechaInicioGestion().substring(0,19).replace("T"," "),item.getFechaFinGestion().substring(0,19).replace("T"," ")});

                        if (row.getCount() == 0) {
                            params.put(0, String.valueOf(item.getPrestamoId())); //ID PRESTAMO
                            params.put(1, Miscellaneous.validStr(item.getPrestamoIdIntegrante()).replace(".0", "")); //ID INTEGRANTE
                            params.put(2, String.valueOf(item.getLatitud())); //LATITUD
                            params.put(3, String.valueOf(item.getLongitud())); //LONGITUD
                            params.put(4, "SI"); //CONTACTO
                            params.put(5, ""); //COMENTARIO
                            params.put(6, Miscellaneous.GetConfirmacion(item.getActualizarTelefono())); //ACTUALIZAR TELEGONO
                            params.put(7, Miscellaneous.validStr(item.getTelefonoNuevo())); //NUEVO TELEFONO
                            params.put(8, "PAGO"); //RESULTADO GESTION
                            params.put(9, ""); //MOTIVO NO PAGO
                            params.put(10, ""); //FECHA FALLECIMIENTO
                            params.put(11, ""); //FECHA MONTO PROMESA
                            params.put(12, ""); //MONTO PROMESA
                            params.put(13, Miscellaneous.GetMedioPago(item.getMedioPago())); //MEDIO PAGO
                            params.put(14, Miscellaneous.validStr(item.getFechaPago())); //FECHA PAGO
                            params.put(15, Miscellaneous.GetConfirmacion(item.getPagaraRequerido())); //PAGARA REQUERIDO
                            params.put(16, String.valueOf(item.getPagoRealizado())); //PAGO REALIZADO
                            if (item.getMedioPago() != 6){
                                params.put(17, ""); //IMPRIMIR RECIBO
                                params.put(18, ""); //FOLIO
                                params.put(27, "0"); //RES IMPRESION
                            }
                            else{
                                params.put(17, Miscellaneous.GetImprimirRecibo(Integer.parseInt(Miscellaneous.validStr(item.getImprimirRecibo()).replace(".0","")))); //IMPRIMIR RECIBO
                                params.put(18, Miscellaneous.validStr(item.getFolio())); //FOLIO
                                params.put(27, String.valueOf(item.getResImpresion())); //RES IMPRESION
                            }
                            params.put(19, ""); //EVIDENCIA
                            params.put(20, Miscellaneous.GetTipoImagen(item.getTipoImagen())); //TIPO IMAGEN
                            params.put(21, Miscellaneous.GetConfirmacion(item.getSupervisonGerente())); //GERENTE
                            params.put(22, ""); //FIRMA
                            params.put(23, item.getFechaInicioGestion().substring(0,19).replace("T"," ")); //FECHA INICIO
                            params.put(24, item.getFechaFinGestion().substring(0,19).replace("T"," ")); //FECHA FIN
                            params.put(25, item.getFechaEnvioDispositivo().substring(0,19).replace("T"," ")); //FECHA ENVIO
                            params.put(26, "2"); //ESTATUS
                            params.put(28, item.getEstatus().replace("0", "")); //ESTATUS PAGO
                            params.put(29, String.valueOf(item.getSaldoCorte())); //SALDO CORTE
                            params.put(30, String.valueOf(item.getSaldoActual())); //SALFO ACTUAL
                            params.put(31, String.valueOf(item.getAtraso())); //DIAS ATRASO
                            params.put(32, Miscellaneous.validStr(item.getSerialId())); //SERIAL ID

                            dBhelper.saveRespuestasVencidasInt(db, params);

                            String sqlAmortiz = "SELECT _id, total, total_pagado, pagado, fecha, numero FROM " + TBL_AMORTIZACIONES_T + " WHERE id_prestamo = ? AND CAST(total AS DOUBLE) > CAST(total_pagado AS DOUBLE) ORDER BY numero ASC";
                            Cursor row_amortiz = db.rawQuery(sqlAmortiz, new String[]{String.valueOf(item.getPrestamoId())});
                            if (row_amortiz.getCount() > 0){
                                row_amortiz.moveToFirst();
                                Double abono = item.getPagoRealizado();
                                for (int i = 0; i < row_amortiz.getCount(); i++){

                                    Double pendiente = row_amortiz.getDouble(1) - row_amortiz.getDouble(2);

                                    if (abono > pendiente){
                                        ContentValues cv_amortiz = new ContentValues();
                                        cv_amortiz.put("total_pagado", row_amortiz.getString(1));
                                        cv_amortiz.put("pagado", "PAGADO");
                                        cv_amortiz.put("dias_atraso", Miscellaneous.GetDiasAtraso(row_amortiz.getString(4)));
                                        db.update(TBL_AMORTIZACIONES_T, cv_amortiz, "id_prestamo = ? AND numero = ?", new String[]{String.valueOf(item.getPrestamoId()), row_amortiz.getString(5)});
                                        abono = abono - pendiente;
                                    }
                                    else if (abono == pendiente){
                                        ContentValues cv_amortiz = new ContentValues();
                                        cv_amortiz.put("total_pagado", row_amortiz.getString(1));
                                        cv_amortiz.put("pagado", "PAGADO");
                                        cv_amortiz.put("dias_atraso", Miscellaneous.GetDiasAtraso(row_amortiz.getString(4)));
                                        db.update(TBL_AMORTIZACIONES_T, cv_amortiz, "id_prestamo = ? AND numero = ?", new String[]{String.valueOf(item.getPrestamoId()), row_amortiz.getString(5)});
                                        abono = 0.0;
                                    }
                                    else if (abono > 0 && abono < pendiente){
                                        ContentValues cv_amortiz = new ContentValues();
                                        cv_amortiz.put("total_pagado", abono);
                                        cv_amortiz.put("pagado", "PARCIAL");
                                        abono = 0.0;
                                        cv_amortiz.put("dias_atraso", Miscellaneous.GetDiasAtraso(row_amortiz.getString(4)));
                                        db.update(TBL_AMORTIZACIONES_T, cv_amortiz, "id_prestamo = ? AND numero = ?", new String[]{String.valueOf(item.getPrestamoId()), row_amortiz.getString(5)});
                                    }
                                    else
                                        break;

                                    row_amortiz.moveToNext();
                                }

                            }
                            row_amortiz.close();
                        }
                        row.close();
                    }
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
        }
    }

}
