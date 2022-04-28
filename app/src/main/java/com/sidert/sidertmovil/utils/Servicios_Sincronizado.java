package com.sidert.sidertmovil.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Environment;
import androidx.appcompat.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.database.DBhelper;
import com.sidert.sidertmovil.models.ConsultaCC;
import com.sidert.sidertmovil.models.MAmortizacion;
import com.sidert.sidertmovil.models.MAval;
import com.sidert.sidertmovil.models.MCierreDia;
import com.sidert.sidertmovil.models.MGestionCancelada;
import com.sidert.sidertmovil.models.MIntegrante;
import com.sidert.sidertmovil.models.MPago;
import com.sidert.sidertmovil.models.MPrestamoGpoRes;
import com.sidert.sidertmovil.models.MPrestamoRes;
import com.sidert.sidertmovil.models.MPrestamosRenovar;
import com.sidert.sidertmovil.models.MRenovacion;
import com.sidert.sidertmovil.models.MRenovacionGrupal;
import com.sidert.sidertmovil.models.MResCierreDia;
import com.sidert.sidertmovil.models.MResConsultaCC;
import com.sidert.sidertmovil.models.MResSaveSolicitud;
import com.sidert.sidertmovil.models.MResSoporte;
import com.sidert.sidertmovil.models.MResTicket;
import com.sidert.sidertmovil.models.MRespGestionadas;
import com.sidert.sidertmovil.models.MResponseDefault;
import com.sidert.sidertmovil.models.MResponseTracker;
import com.sidert.sidertmovil.models.MRespuestaGestion;
import com.sidert.sidertmovil.models.MRespuestaSolicitud;
import com.sidert.sidertmovil.models.MSendImpresion;
import com.sidert.sidertmovil.models.MSendSoporte;
import com.sidert.sidertmovil.models.MSolicitudAutorizar;
import com.sidert.sidertmovil.models.MSolicitudCancelacion;
import com.sidert.sidertmovil.models.MSolicitudRechazoGpo;
import com.sidert.sidertmovil.models.MSolicitudRechazoInd;
import com.sidert.sidertmovil.models.MTracker;
import com.sidert.sidertmovil.models.ModeloGeolocalizacion;
import com.sidert.sidertmovil.models.ModeloResSaveGeo;
import com.sidert.sidertmovil.models.apoyogastosfunerarios.ApoyoGastosFunerarios;
import com.sidert.sidertmovil.models.apoyogastosfunerarios.ApoyoGastosFunerariosDao;
import com.sidert.sidertmovil.models.apoyogastosfunerarios.ApoyoGastosFunerariosResponse;
import com.sidert.sidertmovil.models.apoyogastosfunerarios.Gestion;
import com.sidert.sidertmovil.models.apoyogastosfunerarios.GestionDao;
import com.sidert.sidertmovil.models.apoyogastosfunerarios.PrestamoDao;
import com.sidert.sidertmovil.models.apoyogastosfunerarios.Prestamo;
import com.sidert.sidertmovil.models.apoyogastosfunerarios.Recibo;
import com.sidert.sidertmovil.models.apoyogastosfunerarios.ReciboDao;
import com.sidert.sidertmovil.models.circulocredito.CirculoCredito;
import com.sidert.sidertmovil.models.circulocredito.CirculoCreditoResponse;
import com.sidert.sidertmovil.models.circulocredito.GestionCirculoCredito;
import com.sidert.sidertmovil.models.circulocredito.GestionCirculoCreditoDao;
import com.sidert.sidertmovil.models.circulocredito.ReciboCirculoCredito;
import com.sidert.sidertmovil.models.circulocredito.ReciboCirculoCreditoDao;
import com.sidert.sidertmovil.models.serviciossincronizados.ServicioSincronizado;
import com.sidert.sidertmovil.models.serviciossincronizados.ServicioSincronizadoDao;
import com.sidert.sidertmovil.models.solicitudes.PrestamoToRenovar;
import com.sidert.sidertmovil.models.solicitudes.PrestamoToRenovarDao;
import com.sidert.sidertmovil.models.solicitudes.Solicitud;
import com.sidert.sidertmovil.models.solicitudes.SolicitudDao;
import com.sidert.sidertmovil.models.solicitudes.SolicitudRen;
import com.sidert.sidertmovil.models.solicitudes.SolicitudRenDao;
import com.sidert.sidertmovil.models.solicitudes.solicitudgpo.CreditoGpo;
import com.sidert.sidertmovil.models.solicitudes.solicitudgpo.CreditoGpoDao;
import com.sidert.sidertmovil.models.solicitudes.solicitudgpo.CreditoGpoRen;
import com.sidert.sidertmovil.models.solicitudes.solicitudgpo.CreditoGpoRenDao;
import com.sidert.sidertmovil.models.solicitudes.solicitudgpo.IntegranteGpo;
import com.sidert.sidertmovil.models.solicitudes.solicitudgpo.IntegranteGpoDao;
import com.sidert.sidertmovil.models.solicitudes.solicitudgpo.IntegranteGpoRen;
import com.sidert.sidertmovil.models.solicitudes.solicitudgpo.IntegranteGpoRenDao;
import com.sidert.sidertmovil.models.solicitudes.solicitudgpo.SolicitudDetalleEstatusGpo;
import com.sidert.sidertmovil.models.solicitudes.solicitudind.Cliente;
import com.sidert.sidertmovil.models.solicitudes.solicitudind.ClienteDao;
import com.sidert.sidertmovil.models.solicitudes.solicitudind.ClienteRen;
import com.sidert.sidertmovil.models.solicitudes.solicitudind.ClienteRenDao;
import com.sidert.sidertmovil.models.solicitudes.solicitudind.SolicitudDetalleEstatusInd;
import com.sidert.sidertmovil.models.verificacionesdomiciliarias.GestionVerificacionDomiciliaria;
import com.sidert.sidertmovil.models.verificacionesdomiciliarias.GestionVerificacionDomiciliariaDao;
import com.sidert.sidertmovil.models.verificacionesdomiciliarias.VerificacionDomiciliaria;
import com.sidert.sidertmovil.models.verificacionesdomiciliarias.VerificacionDomiciliariaDao;
import com.sidert.sidertmovil.services.apoyogastosfunerarios.ApoyoGastosFunerariosService;
import com.sidert.sidertmovil.services.apoyogastosfunerarios.PrestamoService;
import com.sidert.sidertmovil.services.apoyogastosfunerarios.ReciboService;
import com.sidert.sidertmovil.services.circulocredito.CirculoCreditoService;
import com.sidert.sidertmovil.services.circulocredito.ReciboCirculoCreditoService;
import com.sidert.sidertmovil.services.serviciossincronizados.ServicioSincronizadoService;
import com.sidert.sidertmovil.services.solicitud.solicitudgpo.SolicitudGpoService;
import com.sidert.sidertmovil.services.solicitud.solicitudind.SolicitudIndService;
import com.sidert.sidertmovil.services.verificaciondomiciliaria.GestionVerificacionDomiciliariaService;
import com.sidert.sidertmovil.services.verificaciondomiciliaria.VerificacionDomiciliariaService;

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
        //Log.e("GetGeolocalizacion", "Inicia la obtencion de fichas "+incluir_gestiones);
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

                                String sql = "SELECT g.* FROM " + TBL_GEO_RESPUESTAS_T + " AS g LEFT JOIN " + TBL_CARTERA_IND_T + " AS ci ON ci.id_cartera = g.id_cartera LEFT JOIN " + TBL_PRESTAMOS_IND_T + " AS pi ON pi.id_cliente = ci.id_cartera  WHERE g.tipo_geolocalizacion = ? AND pi.id_prestamo = ?";
                                rowGeoGi = db.rawQuery(sql, new String[]{modeloGeo.getIndividualesGestionadas().get(h).getTipo(), String.valueOf(modeloGeo.getIndividualesGestionadas().get(h).getPrestamoId())});

                                Log.e("-", "------------------------------------------------------");
                                Log.e("SQL_IND", sql);
                                Log.e("SQL_IND", modeloGeo.getIndividualesGestionadas().get(h).getTipo());
                                Log.e("-", "------------------------------------------------------");

                                if (rowGeoGi.getCount() > 0){
                                    rowGeoGi.moveToFirst();

                                    Log.e("ACtualza", "individual");
                                    if (modeloGeo.getIndividualesGestionadas().get(h).getComentario().contains("PAPE")) {
                                        Log.e("-", "------------------------------------------------------");
                                        Log.e("latitud", modeloGeo.getIndividualesGestionadas().get(h).getLatitud());
                                        Log.e("longitud", modeloGeo.getIndividualesGestionadas().get(h).getLongitud());
                                        Log.e("direccion_capturada", modeloGeo.getIndividualesGestionadas().get(h).getDireccion());
                                        Log.e("codigo_barras", modeloGeo.getIndividualesGestionadas().get(h).getBarcode());
                                        Log.e("fachada", modeloGeo.getIndividualesGestionadas().get(h).getFotoFachada().replace("\"", ""));
                                        Log.e("comentario", modeloGeo.getIndividualesGestionadas().get(h).getComentario());
                                        Log.e("fecha_fin_geo", modeloGeo.getIndividualesGestionadas().get(h).getFechaGestionFin());
                                        Log.e("fecha_envio_geo", modeloGeo.getIndividualesGestionadas().get(h).getFechaEnvio());
                                        Log.e("Clave", rowGeoGi.getString(17));
                                        Log.e("CartraId", rowGeoGi.getString(1));


                                        Log.e("-", "------------------------------------------------------");
                                    }
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

            /*String fileNameReport = "dGFibGUtY29i.csv";
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

                String title = "id_prestamo,num_solicitud,respuesta" + "\n";
                try {
                    out.append(title);
                } catch (IOException e) {
                    e.printStackTrace();
                }*/
                for (int i = 0; i < row.getCount(); i++) {
                    HashMap<String, String> params = new HashMap<>();
                    params.put("id_prestamo", row.getString(1));
                    params.put("num_solicitud", row.getString(29));
                    JSONObject json_res = Miscellaneous.RowTOJson(row, ctx);
                    params.put("respuesta", json_res.toString());
                    params.put("tipo", row.getString(38));

                    /*String val = row.getString(1) + "," + row.getString(29) + "," + json_res.toString() + "\n";
                    try {
                        out.append(val);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }*/

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

                /*
                try {
                    out.flush();
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }*/

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
                item.setMedioPago(Miscellaneous.GetMedioPagoId(row.getString(5)));
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

    public void SendGestionesVerDom(Context ctx, boolean flag) {
        final AlertDialog loading = Popups.showLoadingDialog(ctx, R.string.please_wait, R.string.loading_info);
        loading.setCancelable(false);

        GestionVerificacionDomiciliariaDao gestionDao = new GestionVerificacionDomiciliariaDao(ctx);

        List<GestionVerificacionDomiciliaria> gestiones = gestionDao.findAllByEstatus(1L);

        for(GestionVerificacionDomiciliaria gestion : gestiones)
        {
            new GuardarVerDom().execute(ctx, gestion);
        }

        if(flag) loading.dismiss();
    }

    public void GetGestionesVerDom(Context ctx, boolean flag) {
        final AlertDialog loading = Popups.showLoadingDialog(ctx, R.string.please_wait, R.string.loading_info);

        if (flag) loading.show();

        SessionManager session = new SessionManager(ctx);
        VerificacionDomiciliariaDao verificacionDao = new VerificacionDomiciliariaDao(ctx);

        VerificacionDomiciliariaService verificacionService = new RetrofitClient().newInstance(ctx).create(VerificacionDomiciliariaService.class);
        Call<List<VerificacionDomiciliaria>> call = verificacionService.show("Bearer "+ session.getUser().get(7));

        call.enqueue(new Callback<List<VerificacionDomiciliaria>>() {
            @Override
            public void onResponse(Call<List<VerificacionDomiciliaria>> call, Response<List<VerificacionDomiciliaria>> response) {
                switch (response.code()){
                    case 200:
                        List<VerificacionDomiciliaria> verificaciones = response.body();

                        if (verificaciones != null && verificaciones.size() > 0){
                            for (VerificacionDomiciliaria item : verificaciones){
                                VerificacionDomiciliaria verificacionDb = null;

                                verificacionDb = verificacionDao.findByVerificacionDomiciliariaId(item.getVerificacionDomiciliariaId());

                                if(verificacionDb != null)
                                {
                                    item.setId(verificacionDb.getId());
                                    verificacionDao.update(item);
                                }
                                else
                                {
                                    verificacionDao.store(item);
                                }
                            }
                        }
                        loading.dismiss();
                        break;
                    default:
                        Log.e("ERROR AQUI PREST AUT", response.message());
                        loading.dismiss();
                        break;
                }
            }

            @Override
            public void onFailure(Call<List<VerificacionDomiciliaria>> call, Throwable t) {
                Log.e("ErrorAgf", "Fail AGG"+t.getMessage());
                loading.dismiss();
                t.printStackTrace();
            }
        });
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
                        json_solicitud.put(K_TIPO_SOLICITUD, "ORIGINACION");
                        int montoRefinanciar = 0;
                        if(row_soli.getString(11) != null && !row_soli.getString(11).isEmpty()) Integer.parseInt(row_soli.getString(11).replace(",",""));
                        json_solicitud.put(K_MONTO_REFINANCIAR, montoRefinanciar);
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
                        json_solicitante.put(K_LOCATED_AT, row_dir.getString(15));
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
                        json_solicitante.put(K_SOL_LATITUD, row_soli.getString(38));
                        json_solicitante.put(K_SOL_LONGITUD, row_soli.getString(39));
                        json_solicitante.put(K_SOL_LOCATED_AT, row_soli.getString(40));
                        json_solicitante.put(K_TIENE_FIRMA, "SI");
                        json_solicitante.put(K_NOMBRE_QUIEN_FIRMA, "");


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
                        json_negocio.put(K_LOCATED_AT, row_dir.getString(15));
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
                        //json_negocio.put(K_CAPACIDAD_PAGO, row_soli.getString(17).replace(",",""));//se intercambia con monto maximo
                        json_negocio.put(K_CAPACIDAD_PAGO, row_soli.getString(20).replace(",",""));
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

                        //json_negocio.put(K_MONTO_MAXIMO, row_soli.getString(20).replace(",",""));//se intercambia con capacidad de pago
                        json_negocio.put(K_MONTO_MAXIMO, row_soli.getString(17).replace(",",""));

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
                        json_aval.put(K_LOCATED_AT, row_dir.getString(15));
                        row_dir.close();

                        json_aval.put(K_TIPO_VIVIENDA, row_soli.getString(20));
                        if (row_soli.getString(20).equals("CASA FAMILIAR") || row_soli.getString(20).equals("CASA RENTADA")){
                            json_aval.put(K_NOMBRE_TITULAR, row_soli.getString(21));
                            json_aval.put(K_PARENTESCO_TITULAR, row_soli.getString(22));
                        }
                        json_aval.put(K_CARACTERISTICAS_DOMICILIO, row_soli.getString(23));
                        json_aval.put(K_TIENE_NEGOCIO, row_soli.getInt(25) == 1);
                        if (row_soli.getInt(25) == 1) {
                            json_aval.put(K_NOMBRE_NEGOCIO, row_soli.getString(26).trim().toUpperCase());
                            json_aval.put(K_ANTIGUEDAD, row_soli.getInt(24));
                        }
                        json_aval.put(K_INGRESO_MENSUAL, Double.parseDouble(row_soli.getString(27).replace(",","")));
                        json_aval.put(K_INGRESOS_OTROS, Double.parseDouble(row_soli.getString(28).replace(",","")));
                        json_aval.put(K_GASTO_MENSUAL, Double.parseDouble(row_soli.getString(29).replace(",","")));
                        json_aval.put(K_GASTO_AGUA, Double.parseDouble(row_soli.getString(30).replace(",","")));
                        json_aval.put(K_GASTO_LUZ, Double.parseDouble(row_soli.getString(31).replace(",","")));
                        json_aval.put(K_GASTO_TELEFONO, Double.parseDouble(row_soli.getString(32).replace(",","")));
                        json_aval.put(K_GASTO_RENTA, Double.parseDouble(row_soli.getString(33).replace(",","")));
                        json_aval.put(K_GASTO_OTROS, Double.parseDouble(row_soli.getString(34).replace(",","")));
                        //json_aval.put(K_MONTO_MAXIMO, Double.parseDouble(row_soli.getString(38).replace(",","")));//se intercambiar con capacidad de pago
                        //json_aval.put(K_CAPACIDAD_PAGO, Double.parseDouble(row_soli.getString(35).replace(",","")));//se intercambia con monto maximo
                        json_aval.put(K_MONTO_MAXIMO, Double.parseDouble(row_soli.getString(35).replace(",","")));
                        json_aval.put(K_CAPACIDAD_PAGO, Double.parseDouble(row_soli.getString(38).replace(",","")));

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
                        json_aval.put(K_TEL_CASA, row_soli.getString(41));
                        json_aval.put(K_TEL_CELULAR, row_soli.getString(42));
                        json_aval.put(K_TEL_MENSAJE, row_soli.getString(43));
                        json_aval.put(K_TEL_TRABAJO, row_soli.getString(44));
                        json_aval.put(K_EMAIL, row_soli.getString(45));
                        json_aval.put(K_FOTO_FACHADA, row_soli.getString(46));
                        String fachadaAval = row_soli.getString(46);
                        json_aval.put(K_REFERENCIA_DOMICILIARIA, row_soli.getString(47));
                        json_aval.put(K_FIRMA, row_soli.getString(48));
                        json_aval.put(K_AVAL_LATITUD, row_soli.getString(52));
                        json_aval.put(K_AVAL_LONGITUD, row_soli.getString(53));
                        json_aval.put(K_AVAL_LOCATED_AT, row_soli.getString(54));
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
                        json_croquis.put(K_CARACTERISTICAS_DOMICILIO, row_soli.getString(9));
                        json_solicitud.put(K_SOLICITANTE_CROQUIS, json_croquis);
                        row_soli.close(); //Cierra datos del croquis

                        Log.e("croquis", json_croquis.toString());

                        row_soli = dBhelper.getRecords(TBL_POLITICAS_PLD_IND, " WHERE id_solicitud = ?", "", new String[]{row.getString(0)});
                        row_soli.moveToFirst();
                        JSONObject json_politicas = new JSONObject();
                        json_politicas.put(K_PROPIETARIO, row_soli.getInt(2) == 1);
                        json_politicas.put(K_PROVEEDOR_RECURSOS, row_soli.getInt(3) == 1);
                        json_politicas.put(K_POLITICAMENTE_EXP, row_soli.getInt(4) == 1);
                        json_solicitud.put(K_SOLICITANTE_POLITICAS, json_politicas);
                        row_soli.close(); //Cierra datos de politicas pld
                        Log.e("politicas", json_politicas.toString());

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

                        String identificacionSelfie = "";
                        if(row_soli.getString(9) != null)
                        {
                            json_documentos.put(K_IDENTIFICACION_SELFIE, row_soli.getString(9));
                            identificacionSelfie = row_soli.getString(9);
                        }
                        else
                        {
                            json_documentos.put(K_IDENTIFICACION_SELFIE, "");
                        }

                        String comprobanteGarantia = "";
                        if(row_soli.getString(10) != null)
                        {
                            json_documentos.put(K_COMPROBANTE_GARANTIA, row_soli.getString(10));
                            comprobanteGarantia = row_soli.getString(10);
                        }
                        else
                        {
                            json_documentos.put(K_COMPROBANTE_GARANTIA, "");
                        }

                        String identificacionFrontalAval = "";
                        if(row_soli.getString(11) != null)
                        {
                            json_documentos.put(K_IDENTIFICACION_FRONTAL_AVAL, row_soli.getString(11));
                            identificacionFrontalAval = row_soli.getString(11);
                        }
                        else
                        {
                            json_documentos.put(K_IDENTIFICACION_FRONTAL_AVAL, "");
                        }

                        String identificacionReversoAval = "";
                        if(row_soli.getString(12) != null)
                        {
                            json_documentos.put(K_IDENTIFICACION_REVERSO_AVAL, row_soli.getString(12));
                            identificacionReversoAval = row_soli.getString(12);
                        }
                        else
                        {
                            json_documentos.put(K_IDENTIFICACION_REVERSO_AVAL, "");
                        }

                        String curpAval = "";
                        if(row_soli.getString(13) != null)
                        {
                            json_documentos.put(K_CURP_AVAL, row_soli.getString(13));
                            curpAval = row_soli.getString(13);
                        }
                        else
                        {
                            json_documentos.put(K_CURP_AVAL, "");
                        }

                        String comprobanteAval = "";
                        if(row_soli.getString(14) != null)
                        {
                            json_documentos.put(K_COMPROBANTE_DOMICILIO_AVAL, row_soli.getString(14));
                            comprobanteAval = row_soli.getString(14);
                        }
                        else
                        {
                            json_documentos.put(K_COMPROBANTE_DOMICILIO_AVAL, "");
                        }

                        json_solicitud.put(K_SOLICITANTE_DOCUMENTOS, json_documentos);

                        row_soli.close(); //Cierre de datos de documentos

                        Log.e("documentos", json_documentos.toString());
                        new GuardarSolicitudInd().execute(
                                ctx,
                                json_solicitud,
                                fachadaCli,
                                firmaCli,
                                fachadaNeg,
                                fachadaAval,
                                firmaAval,
                                identiFrontal,
                                identiReverso,
                                curp,
                                comprobante,
                                firmaAsesor,
                                row.getString(0),
                                row.getLong(4),
                                identificacionSelfie,
                                comprobanteGarantia,
                                identificacionFrontalAval,
                                identificacionReversoAval,
                                curpAval,
                                comprobanteAval
                        );

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                row.moveToNext();
            }
        }

    }

    public void SendRenovacionInd (Context ctx, boolean flag){
        final AlertDialog loading = Popups.showLoadingDialog(ctx, R.string.please_wait, R.string.loading_info);
        final DBhelper dBhelper = new DBhelper(ctx);
        final SQLiteDatabase db = dBhelper.getWritableDatabase();

        if (flag) loading.show();

        Cursor row = dBhelper.getRecords(TBL_SOLICITUDES_REN, " WHERE tipo_solicitud = 1 AND estatus = 1", "", null);

        if (row.getCount() > 0){
            row.moveToFirst();

            Log.e("count solicitudes", row.getCount()+" total");
            for (int i = 0; i < row.getCount(); i++){
                Cursor row_soli = dBhelper.getRecords(TBL_CREDITO_IND_REN, " WHERE id_solicitud = ?", "", new String[]{row.getString(0)});

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
                    json_solicitud.put(K_DESTINO_PRESTAMO, row_soli.getString(13));
                    json_solicitud.put(K_CLASIFICACION_RIESGO, row_soli.getString(14));
                    json_solicitud.put(K_COMPORTAMIENTO_PAGO, row_soli.getString(10));
                    json_solicitud.put(K_OBSERVACIONES, row_soli.getString(12));
                    json_solicitud.put(K_TIPO_SOLICITUD, "RENOVACION");
                    json_solicitud.put(K_CLIENTE_ID, row_soli.getLong(11));
                    int montoRefinanciar = 0;
                    if(row_soli.getString(16) != null && !row_soli.getString(16).isEmpty()) Integer.parseInt(row_soli.getString(16).replace(",",""));
                    json_solicitud.put(K_MONTO_REFINANCIAR, montoRefinanciar);

                    row_soli.close();//Cierra datos de credito
                    json_solicitud.put(K_FECHA_INICIO, row.getString(6));
                    json_solicitud.put(K_FECHA_TERMINO, row.getString(7));
                    json_solicitud.put(K_FECHA_ENVIO, Miscellaneous.ObtenerFecha(TIMESTAMP));

                    row_soli = dBhelper.getRecords(TBL_CLIENTE_IND_REN, " WHERE id_solicitud = ?", "", new String[]{row.getString(0)});
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
                    Cursor row_dir = dBhelper.getRecords(TBL_DIRECCIONES_REN, " WHERE id_direccion = ? AND tipo_direccion = ?", "", new String[]{row_soli.getString(22), "CLIENTE"});
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
                    json_solicitante.put(K_LOCATED_AT, row_dir.getString(15));
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
                    json_solicitante.put(K_SOL_LATITUD, row_soli.getString(38));
                    json_solicitante.put(K_SOL_LONGITUD, row_soli.getString(39));
                    json_solicitante.put(K_SOL_LOCATED_AT, row_soli.getString(40));
                    json_solicitante.put(K_TIENE_FIRMA, "SI");
                    json_solicitante.put(K_NOMBRE_QUIEN_FIRMA, "");

                    json_solicitud.put(K_SOLICITANTE, json_solicitante);
                    String fachadaCli = row_soli.getString(32);
                    String firmaCli = row_soli.getString(34);
                    row_soli.close(); //Cierra datos del solicitante

                    Log.e("solicitante", json_solicitante.toString());

                    if (estadoCivil.equals("CASADO(A)") ||
                            estadoCivil.equals("UNIÓN LIBRE")) {
                        JSONObject json_conyuge = new JSONObject();
                        row_soli = dBhelper.getRecords(TBL_CONYUGE_IND_REN, " WHERE id_solicitud = ?", "", new String[]{row.getString(0)});
                        row_soli.moveToFirst();
                        json_conyuge.put(K_NOMBRE, row_soli.getString(2));
                        json_conyuge.put(K_PATERNO, row_soli.getString(3));
                        json_conyuge.put(K_MATERNO, row_soli.getString(4));
                        json_conyuge.put(K_NACIONALIDAD, row_soli.getString(5));
                        json_conyuge.put(K_OCUPACION, row_soli.getString(6));

                        row_dir = dBhelper.getRecords(TBL_DIRECCIONES_REN, " WHERE id_direccion = ? AND tipo_direccion = ?", "", new String[]{row_soli.getString(7), "CONYUGE"});
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

                    Log.e("Solicitud", json_solicitud.toString());
                    if (montoPres >= 30000){
                        row_soli = dBhelper.getRecords(TBL_ECONOMICOS_IND_REN, " WHERE id_solicitud = ?", "", new String[]{row.getString(0)});
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



                    row_soli = dBhelper.getRecords(TBL_NEGOCIO_IND_REN, " WHERE id_solicitud = ?", "", new String[]{row.getString(0)});
                    row_soli.moveToFirst();
                    JSONObject json_negocio = new JSONObject();
                    json_negocio.put(K_NOMBRE, row_soli.getString(2));

                    row_dir = dBhelper.getRecords(TBL_DIRECCIONES_REN, " WHERE id_direccion = ? AND tipo_direccion = ?", "", new String[]{row_soli.getString(3), "NEGOCIO"});
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
                    json_negocio.put(K_LOCATED_AT, row_dir.getString(15));
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
                    //json_negocio.put(K_CAPACIDAD_PAGO, row_soli.getString(17).replace(",",""));//se intercambio con monto maximo
                    json_negocio.put(K_MONTO_MAXIMO, row_soli.getString(17).replace(",",""));
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

                    //json_negocio.put(K_MONTO_MAXIMO, row_soli.getString(20).replace(",",""));//se intercambia con capacidad de pago
                    json_negocio.put(K_CAPACIDAD_PAGO, row_soli.getString(20).replace(",",""));//se intercambia con capacidad de pago

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

                    row_soli = dBhelper.getRecords(TBL_AVAL_IND_REN, " WHERE id_solicitud = ?", "", new String[]{row.getString(0)});
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

                    row_dir = dBhelper.getRecords(TBL_DIRECCIONES_REN, " WHERE id_direccion = ? AND tipo_direccion = ?", "", new String[]{row_soli.getString(19), "AVAL"});
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
                    json_aval.put(K_LOCATED_AT, row_dir.getString(15));
                    row_dir.close();

                    json_aval.put(K_TIPO_VIVIENDA, row_soli.getString(20));
                    if (row_soli.getString(20).equals("CASA FAMILIAR") || row_soli.getString(20).equals("CASA RENTADA")){
                        json_aval.put(K_NOMBRE_TITULAR, row_soli.getString(21));
                        json_aval.put(K_PARENTESCO_TITULAR, row_soli.getString(22));
                    }
                    json_aval.put(K_CARACTERISTICAS_DOMICILIO, row_soli.getString(23));
                    json_aval.put(K_TIENE_NEGOCIO, row_soli.getInt(25) == 1);
                    if (row_soli.getInt(25) == 1) {
                        json_aval.put(K_NOMBRE_NEGOCIO, row_soli.getString(26).trim().toUpperCase());
                        json_aval.put(K_ANTIGUEDAD, row_soli.getInt(24));
                    }
                    json_aval.put(K_INGRESO_MENSUAL, Double.parseDouble(row_soli.getString(27).replace(",","")));
                    json_aval.put(K_INGRESOS_OTROS, Double.parseDouble(row_soli.getString(28).replace(",","")));
                    json_aval.put(K_GASTO_MENSUAL, Double.parseDouble(row_soli.getString(29).replace(",","")));
                    json_aval.put(K_GASTO_AGUA, Double.parseDouble(row_soli.getString(30).replace(",","")));
                    json_aval.put(K_GASTO_LUZ, Double.parseDouble(row_soli.getString(31).replace(",","")));
                    json_aval.put(K_GASTO_TELEFONO, Double.parseDouble(row_soli.getString(32).replace(",","")));
                    json_aval.put(K_GASTO_RENTA, Double.parseDouble(row_soli.getString(33).replace(",","")));
                    json_aval.put(K_GASTO_OTROS, Double.parseDouble(row_soli.getString(34).replace(",","")));
                    //json_aval.put(K_MONTO_MAXIMO, Double.parseDouble(row_soli.getString(38).replace(",","")));//se intercambia con capacidad de pago
                    json_aval.put(K_CAPACIDAD_PAGO, Double.parseDouble(row_soli.getString(38).replace(",","")));
                    //json_aval.put(K_CAPACIDAD_PAGO, Double.parseDouble(row_soli.getString(35).replace(",","")));//se intercambia con monto maximo
                    json_aval.put(K_MONTO_MAXIMO, Double.parseDouble(row_soli.getString(35).replace(",","")));

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
                    json_aval.put(K_TEL_CASA, row_soli.getString(41));
                    json_aval.put(K_TEL_CELULAR, row_soli.getString(42));
                    json_aval.put(K_TEL_MENSAJE, row_soli.getString(43));
                    json_aval.put(K_TEL_TRABAJO, row_soli.getString(44));
                    json_aval.put(K_EMAIL, row_soli.getString(45));
                    json_aval.put(K_FOTO_FACHADA, row_soli.getString(46));
                    String fachadaAval = row_soli.getString(46);
                    json_aval.put(K_REFERENCIA_DOMICILIARIA, row_soli.getString(47));
                    json_aval.put(K_FIRMA, row_soli.getString(48));
                    json_aval.put(K_AVAL_LATITUD, row_soli.getString(52));
                    json_aval.put(K_AVAL_LONGITUD, row_soli.getString(53));
                    json_aval.put(K_AVAL_LOCATED_AT, row_soli.getString(54));
                    String firmaAval = row_soli.getString(48);

                    json_solicitud.put(K_SOLICITANTE_AVAL, json_aval);

                    row_soli.close(); //Cierre de datos del aval

                    Log.e("aval", json_aval.toString());

                    row_soli = dBhelper.getRecords(TBL_REFERENCIA_IND_REN, " WHERE id_solicitud = ?", "", new String[]{row.getString(0)});
                    row_soli.moveToFirst();

                    JSONObject json_referencia = new JSONObject();
                    json_referencia.put(K_NOMBRE, row_soli.getString(2));
                    json_referencia.put(K_PATERNO, row_soli.getString(3));
                    json_referencia.put(K_MATERNO, row_soli.getString(4));
                    json_referencia.put(K_FECHA_NACIMIENTO, row_soli.getString(5));

                    row_dir = dBhelper.getRecords(TBL_DIRECCIONES_REN, " WHERE id_direccion = ? AND tipo_direccion = ?", "", new String[]{row_soli.getString(6), "REFERENCIA"});
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

                    row_soli = dBhelper.getRecords(TBL_CROQUIS_IND_REN, " WHERE id_solicitud = ?", "", new String[]{row.getString(0)});
                    row_soli.moveToFirst();
                    JSONObject json_croquis = new JSONObject();
                    json_croquis.put(K_CALLE_ENFRENTE, row_soli.getString(2));
                    json_croquis.put(K_CALLE_LATERAL_IZQ, row_soli.getString(3));
                    json_croquis.put(K_CALLE_LATERAL_DER, row_soli.getString(4));
                    json_croquis.put(K_CALLE_ATRAS, row_soli.getString(5));
                    json_croquis.put(K_REFERENCIAS, row_soli.getString(6));
                    json_croquis.put(K_CARACTERISTICAS_DOMICILIO, row_soli.getString(9));

                    json_solicitud.put(K_SOLICITANTE_CROQUIS, json_croquis);
                    row_soli.close(); //Cierra datos del croquis

                    row_soli = dBhelper.getRecords(TBL_POLITICAS_PLD_IND_REN, " WHERE id_solicitud = ?", "", new String[]{row.getString(0)});
                    row_soli.moveToFirst();
                    JSONObject json_politicas = new JSONObject();
                    json_politicas.put(K_PROPIETARIO, row_soli.getInt(2) == 1);
                    json_politicas.put(K_PROVEEDOR_RECURSOS, row_soli.getInt(3) == 1);
                    json_politicas.put(K_POLITICAMENTE_EXP, row_soli.getInt(4) == 1);
                    json_solicitud.put(K_SOLICITANTE_POLITICAS, json_politicas);
                    row_soli.close(); //Cierra datos de politicas pld


                    row_soli = dBhelper.getRecords(TBL_DOCUMENTOS_REN, " WHERE id_solicitud = ?", "", new String[]{row.getString(0)});
                    row_soli.moveToFirst();
                    JSONObject json_documentos = new JSONObject();
                    json_documentos.put(K_IDENTIFICACION_FRONTAL, row_soli.getString(2));
                    String identiFrontal = row_soli.getString(2);
                    json_documentos.put(K_IDENTIFICACION_REVERSO, row_soli.getString(3));
                    String identiReverso = row_soli.getString(3);
                    //json_documentos.put(K_CURP, row_soli.getString(4));
                    //String curp = row_soli.getString(4);
                    json_documentos.put(K_COMPROBANTE_DOMICILIO, row_soli.getString(5));
                    String comprobante = row_soli.getString(5);
                    json_documentos.put(K_CODIGO_BARRAS, row_soli.getString(6));
                    json_documentos.put(K_FIRMA_ASESOR, row_soli.getString(7));
                    String firmaAsesor = row_soli.getString(7);
                    String identificacionSelfie = "";
                    if(row_soli.getString(9) != null)
                    {
                        json_documentos.put(K_IDENTIFICACION_SELFIE, row_soli.getString(9));
                        identificacionSelfie = row_soli.getString(9);
                    }
                    else
                    {
                        json_documentos.put(K_IDENTIFICACION_SELFIE, "");
                    }

                    String comprobanteGarantia = "";
                    if(row_soli.getString(10) != null)
                    {
                        json_documentos.put(K_COMPROBANTE_GARANTIA, row_soli.getString(10));
                        comprobanteGarantia = row_soli.getString(10);
                    }
                    else
                    {
                        json_documentos.put(K_COMPROBANTE_GARANTIA, "");
                    }

                    String identificacionFrontalAval = "";
                    if(row_soli.getString(11) != null)
                    {
                        json_documentos.put(K_IDENTIFICACION_FRONTAL_AVAL, row_soli.getString(11));
                        identificacionFrontalAval = row_soli.getString(11);
                    }
                    else
                    {
                        json_documentos.put(K_IDENTIFICACION_FRONTAL_AVAL, "");
                    }

                    String identificacionReversoAval = "";
                    if(row_soli.getString(12) != null)
                    {
                        json_documentos.put(K_IDENTIFICACION_REVERSO_AVAL, row_soli.getString(12));
                        identificacionReversoAval = row_soli.getString(12);
                    }
                    else
                    {
                        json_documentos.put(K_IDENTIFICACION_REVERSO_AVAL, "");
                    }

                    String curpAval = "";
                    if(row_soli.getString(13) != null)
                    {
                        json_documentos.put(K_CURP_AVAL, row_soli.getString(13));
                        curpAval = row_soli.getString(13);
                    }
                    else
                    {
                        json_documentos.put(K_CURP_AVAL, "");
                    }

                    String comprobanteAval = "";
                    if(row_soli.getString(14) != null)
                    {
                        json_documentos.put(K_COMPROBANTE_DOMICILIO_AVAL, row_soli.getString(14));
                        comprobanteAval = row_soli.getString(14);
                    }
                    else
                    {
                        json_documentos.put(K_COMPROBANTE_DOMICILIO_AVAL, "");
                    }

                    json_solicitud.put(K_SOLICITANTE_DOCUMENTOS, json_documentos);

                    row_soli.close(); //Cierre de datos de documentos

                    Log.e("documentos", json_documentos.toString());
                    Log.e("-----","--------------------------------------------------------");
                    Log.e("Renovacion",json_solicitud.toString());
                    Log.e("-----","--------------------------------------------------------");
                    new GuardarRenovacionInd().execute(
                        ctx,
                        json_solicitud,
                        fachadaCli,
                        firmaCli,
                        fachadaNeg,
                        fachadaAval,
                        firmaAval,
                        identiFrontal,
                        identiReverso,
                        comprobante,
                        firmaAsesor,
                        row.getString(0),
                        row.getLong(4),
                        identificacionSelfie,
                        comprobanteGarantia,
                        identificacionFrontalAval,
                        identificacionReversoAval,
                        curpAval,
                        comprobanteAval
                    );

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                row.moveToNext();
            }
        }

    }

    public void SendIntegranteOriginacionGpo(Context ctx, int iIndex, int iTotalRegistros, String sDato0, Long lDato4, String sDato6, String sDato12, String sDato14, String sDato15, String sDato16, String sDato17, String sDato19)
    {
        final DBhelper dBhelper = new DBhelper(ctx);
        final SQLiteDatabase db = dBhelper.getWritableDatabase();

        String sql;
        Cursor rowTotal;
        int iTotal;

        if(iTotalRegistros < 0)
        {
            sql = "SELECT i.*, t.*, d.*, n.*, c.*, o.*, docu.*, p.*, cro.* FROM " + TBL_INTEGRANTES_GPO + " AS i " +
                    "INNER JOIN " + TBL_TELEFONOS_INTEGRANTE + " AS t ON i.id = t.id_integrante " +
                    "INNER JOIN " + TBL_DOMICILIO_INTEGRANTE + " AS d ON i.id = d.id_integrante " +
                    "INNER JOIN " + TBL_NEGOCIO_INTEGRANTE + " AS n ON i.id = n.id_integrante " +
                    "INNER JOIN " + TBL_CONYUGE_INTEGRANTE + " AS c ON i.id = c.id_integrante " +
                    "INNER JOIN " + TBL_OTROS_DATOS_INTEGRANTE + " AS o ON i.id = o.id_integrante " +
                    "INNER JOIN " + TBL_CROQUIS_GPO + " AS cro ON i.id = cro.id_integrante " +
                    "INNER JOIN " + TBL_POLITICAS_PLD_INTEGRANTE + " AS p ON i.id = p.id_integrante " +
                    "INNER JOIN " + TBL_DOCUMENTOS_INTEGRANTE + " AS docu ON i.id = docu.id_integrante " +
                    "WHERE i.id_credito = ? AND i.estatus_completado = ? " +
                    "order by i.id_credito ";
            rowTotal = db.rawQuery(sql, new String[]{sDato12, "1"});
            iTotal = rowTotal.getCount();
        }
        else
        {
            iTotal = iTotalRegistros;
        }

        String sqlIntegrante = "SELECT " +
                "i.id," +
                "i.id_credito," +
                "i.cargo," +
                "i.nombre," +
                "i.paterno," +
                "i.materno," +
                "i.fecha_nacimiento," +
                "i.edad," +
                "i.genero," +
                "i.estado_nacimiento," +
                "i.rfc," +
                "i.curp," +
                "i.curp_digito_veri," +
                "i.tipo_identificacion," +
                "i.no_identificacion," +
                "i.nivel_estudio," +
                "i.ocupacion," +
                "i.estado_civil," +
                "i.bienes," +
                "i.estatus_rechazo," +
                "i.comentario_rechazo," +
                "i.estatus_completado," +
                "i.id_solicitud_integrante," +
                "t.id_telefonico," +
                "t.id_integrante," +
                "t.tel_casa," +
                "t.tel_celular," +
                "t.tel_mensaje," +
                "t.tel_trabajo," +
                "t.estatus_completado," +
                "d.id_domicilio," +
                "d.id_integrante," +
                "d.latitud," +
                "d.longitud," +
                "d.calle," +
                "d.no_exterior," +
                "d.no_interior," +
                "d.manzana," +
                "d.lote," +
                "d.cp," +
                "d.colonia," +
                "d.ciudad," +
                "d.localidad," +
                "d.municipio," +
                "d.estado," +
                "d.tipo_vivienda," +
                "d.parentesco," +
                "d.otro_tipo_vivienda," +
                "d.tiempo_vivir_sitio," +
                "d.foto_fachada," +
                "d.ref_domiciliaria," +
                "d.estatus_completado," +
                "d.dependientes," +
                "n.id_negocio," +
                "n.id_integrante," +
                "n.nombre," +
                "n.latitud," +
                "n.longitud," +
                "n.calle," +
                "n.no_exterior," +
                "n.no_interior," +
                "n.manzana," +
                "n.lote," +
                "n.cp," +
                "n.colonia," +
                "n.ciudad," +
                "n.localidad," +
                "n.municipio," +
                "n.estado," +
                "n.destino_credito," +
                "n.otro_destino_credito," +
                "n.ocupacion," +
                "n.actividad_economica," +
                "n.antiguedad," +
                "n.ing_mensual," +
                "n.ing_otros," +
                "n.gasto_semanal," +
                "n.capacidad_pago," +
                "n.monto_maximo," +
                "n.medios_pago," +
                "n.otro_medio_pago," +
                "n.num_ope_mensuales," +
                "n.num_ope_mensuales_efectivo," +
                "n.foto_fachada," +
                "n.ref_domiciliaria," +
                "n.estatus_rechazo," +
                "n.comentario_rechazo," +
                "n.estatus_completado," +
                "c.id_conyuge," +
                "c.id_integrante," +
                "c.nombre," +
                "c.paterno," +
                "c.materno," +
                "c.nacionalidad," +
                "c.ocupacion," +
                "c.calle," +
                "c.no_exterior," +
                "c.no_interior," +
                "c.manzana," +
                "c.lote," +
                "c.cp," +
                "c.colonia," +
                "c.ciudad," +
                "c.localidad," +
                "c.municipio," +
                "c.estado," +
                "c.ingresos_mensual," +
                "c.gasto_mensual," +
                "c.tel_celular," +
                "c.tel_trabajo," +
                "c.estatus_completado," +
                "o.id_otro," +
                "o.id_integrante," +
                "o.clasificacion_riesgo," +
                "o.medio_contacto," +
                "o.email," +
                "o.estado_cuenta," +
                "o.estatus_integrante," +
                "o.monto_solicitado," +
                "o.casa_reunion," +
                "o.firma," +
                "o.estatus_completado," +
                "docu.id_documento," +
                "docu.id_integrante," +
                "docu.ine_frontal," +
                "docu.ine_reverso," +
                "docu.curp," +
                "docu.comprobante," +
                "docu.estatus_completado," +
                "p.id_politica," +
                "p.id_integrante," +
                "p.propietario_real," +
                "p.proveedor_recursos," +
                "p.persona_politica," +
                "p.estatus_completado," +
                "cro.id," +
                "cro.id_integrante," +
                "cro.calle_principal," +
                "cro.lateral_uno," +
                "cro.lateral_dos," +
                "cro.calle_trasera," +
                "cro.referencias," +
                "cro.estatus_completado," +
                "d.located_at," +
                "n.located_at," +
                "o.latitud," +
                "o.longitud," +
                "o.located_at," +
                "o.tiene_firma," +
                "o.nombre_quien_firma," +
                "o.monto_refinanciar," +
                "cro.caracteristicas_domicilio," +
                "docu.ine_selfie" +
            " FROM " + TBL_INTEGRANTES_GPO + " AS i " +
            "INNER JOIN " + TBL_TELEFONOS_INTEGRANTE + " AS t ON i.id = t.id_integrante " +
            "INNER JOIN " + TBL_DOMICILIO_INTEGRANTE + " AS d ON i.id = d.id_integrante " +
            "INNER JOIN " + TBL_NEGOCIO_INTEGRANTE + " AS n ON i.id = n.id_integrante " +
            "INNER JOIN " + TBL_CONYUGE_INTEGRANTE + " AS c ON i.id = c.id_integrante " +
            "INNER JOIN " + TBL_OTROS_DATOS_INTEGRANTE + " AS o ON i.id = o.id_integrante " +
            "INNER JOIN " + TBL_CROQUIS_GPO + " AS cro ON i.id = cro.id_integrante " +
            "INNER JOIN " + TBL_POLITICAS_PLD_INTEGRANTE + " AS p ON i.id = p.id_integrante " +
            "INNER JOIN " + TBL_DOCUMENTOS_INTEGRANTE + " AS docu ON i.id = docu.id_integrante " +
            "WHERE i.id_credito = ? AND i.estatus_completado = ? " +
            "order by i.id_credito " +
            "limit 1 "
            //"limit 1 offset " + iIndex
        ;

        Cursor rowIntegrante = db.rawQuery(sqlIntegrante, new String[]{sDato12, "1"});

        if(rowIntegrante.getCount() > 0) {
            rowIntegrante.moveToFirst();
            String id_solicitud = sDato0;
            Log.e("Count", "a: " + sDato14);
            Log.e("ORIGINACION_GPO", "Total: " + rowIntegrante.getCount());
            // for (int j = 0; j < rowIntegrante.getCount(); j++){
            JSONObject json_solicitud = new JSONObject();
            try {
                json_solicitud.put(K_TOTAL_INTEGRANTES, rowIntegrante.getCount());
                json_solicitud.put(K_NOMBRE_GRUPO, sDato14.trim().toUpperCase());
                json_solicitud.put(K_PLAZO, Miscellaneous.GetPlazo(sDato15));
                json_solicitud.put(K_PERIODICIDAD, Miscellaneous.GetPeriodicidad(sDato16));
                json_solicitud.put(K_FECHA_DESEMBOLSO, sDato17);
                json_solicitud.put(K_HORA_VISITA, sDato19);
                json_solicitud.put(K_TIPO_SOLICITUD, "ORIGINACION");
                json_solicitud.put(K_FECHA_INICIO, sDato6);
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
                json_solicitante.put(K_LOCATED_AT, rowIntegrante.getString(143));

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
                json_solicitante.put(K_SOL_LATITUD, rowIntegrante.getString(145));
                json_solicitante.put(K_SOL_LONGITUD, rowIntegrante.getString(146));
                json_solicitante.put(K_SOL_LOCATED_AT, rowIntegrante.getString(147));
                json_solicitante.put(K_TIENE_FIRMA, rowIntegrante.getString(148));
                json_solicitante.put(K_NOMBRE_QUIEN_FIRMA, rowIntegrante.getString(149));
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
                int montoRefinanciar = 0;
                if(rowIntegrante.getString(150) != null && !rowIntegrante.getString(150).isEmpty()) montoRefinanciar = Integer.parseInt(rowIntegrante.getString(150).replace(",",""));
                json_otros_datos.put(K_MONTO_REFINANCIAR, montoRefinanciar);
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
                json_negocio.put(K_LOCATED_AT, rowIntegrante.getString(144));
                json_negocio.put(K_DESTINO_CREDITO, rowIntegrante.getString(69));
                if (rowIntegrante.getString(69).contains("OTRO"))
                    json_negocio.put(K_OTRO_DESTINO_CREDITO, rowIntegrante.getString(70));
                json_negocio.put(K_OCUPACION, rowIntegrante.getString(71));
                json_negocio.put(K_ACTIVIDAD_ECONOMICA, rowIntegrante.getString(72));
                json_negocio.put(K_ANTIGUEDAD, rowIntegrante.getInt(73));
                json_negocio.put(K_INGRESO_MENSUAL, rowIntegrante.getString(74));
                json_negocio.put(K_INGRESOS_OTROS, rowIntegrante.getString(75));
                json_negocio.put(K_GASTO_MENSUAL, rowIntegrante.getString(76));
                //json_negocio.put(K_CAPACIDAD_PAGO, rowIntegrante.getString(77));//se intercambia con monto maximo
                json_negocio.put(K_MONTO_MAXIMO, rowIntegrante.getString(77));
                //json_negocio.put(K_MONTO_MAXIMO, rowIntegrante.getString(78));//se intercambia con capacidad de pago
                json_negocio.put(K_CAPACIDAD_PAGO, rowIntegrante.getString(78));
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

                String identiSelfie = "";
                if(rowIntegrante.getString(152) != null)
                {
                    json_documentos.put(K_IDENTIFICACION_SELFIE, rowIntegrante.getString(152));
                    identiSelfie = rowIntegrante.getString(152);
                }
                else
                {
                    json_documentos.put(K_IDENTIFICACION_SELFIE, "");
                }

                json_solicitud.put(K_SOLICITANTE_DOCUMENTOS, json_documentos);

                JSONObject json_politicas = new JSONObject();
                json_politicas.put(K_PROPIETARIO, rowIntegrante.getInt(131) == 1);
                json_politicas.put(K_PROVEEDOR_RECURSOS, rowIntegrante.getInt(132) == 1);
                json_politicas.put(K_POLITICAMENTE_EXP, rowIntegrante.getInt(133) == 1);
                json_solicitud.put(K_SOLICITANTE_POLITICAS, json_politicas);

                if (rowIntegrante.getInt(119) == 1) {
                    JSONObject json_croquis = new JSONObject();
                    json_croquis.put(K_CALLE_ENFRENTE, rowIntegrante.getString(137));
                    json_croquis.put(K_CALLE_LATERAL_IZQ, rowIntegrante.getString(138));
                    json_croquis.put(K_CALLE_LATERAL_DER, rowIntegrante.getString(139));
                    json_croquis.put(K_CALLE_ATRAS, rowIntegrante.getString(140));
                    json_croquis.put(K_REFERENCIAS, rowIntegrante.getString(141));
                    json_croquis.put(K_CARACTERISTICAS_DOMICILIO, rowIntegrante.getString(151));
                    json_solicitud.put(K_SOLICITANTE_CROQUIS, json_croquis);
                }

                Log.e("_", "_______________________________________________________________________-");
                Log.e("solicitudInt", json_solicitud.toString());
                Log.e("_", "_______________________________________________________________________-");

                Log.e("AQUI ID SOLICITUD", String.valueOf(rowIntegrante.getInt(22)));

                String solicitudGrupalIdValidado = String.valueOf(lDato4);
                if(rowIntegrante.getInt(22) == 0) solicitudGrupalIdValidado = "0";

                new GuardarSolicitudGpo().execute(
                        ctx,
                        json_solicitud,
                        fachadaCli,
                        firmaCli,
                        fachadaNeg,
                        identiFrontal,
                        identiReverso,
                        curp,
                        comprobante,
                        rowIntegrante.getString(0),
                        rowIntegrante.getString(1),
                        id_solicitud,
                        String.valueOf(rowIntegrante.getLong(22)),
                        solicitudGrupalIdValidado,//String.valueOf(lDato4),
                        iIndex,
                        iTotal,
                        sDato0,
                        lDato4,
                        sDato6,
                        sDato12,
                        sDato14,
                        sDato15,
                        sDato16,
                        sDato17,
                        sDato19,
                        identiSelfie
                );

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
            //rowIntegrante.moveToNext();
        //}
        rowIntegrante.close();
    }

    public void SendOriginacionGpo (Context ctx, boolean flag){
        final AlertDialog loading = Popups.showLoadingDialog(ctx, R.string.please_wait, R.string.loading_info);

        if (flag) loading.show();

        final DBhelper dBhelper = new DBhelper(ctx);
        final SQLiteDatabase db = dBhelper.getWritableDatabase();

        String sql = "SELECT s.*, c.* FROM " + TBL_SOLICITUDES + " AS s INNER JOIN " +TBL_CREDITO_GPO + " AS c ON s.id_solicitud = c.id_solicitud WHERE s.tipo_solicitud = 2 AND s.estatus = 1";
        Cursor row = db.rawQuery(sql, null);
        //Cursor row = dBhelper.getRecords(TBL_SOLICITUDES, " WHERE tipo_solicitud = 2", "", null);

        if (row.getCount() > 0){
            row.moveToFirst();

            for(int i = 0; i < row.getCount(); i++)
            {
                SendIntegranteOriginacionGpo(
                    ctx,
                    0,
                    -1,
                    row.getString(0),
                    row.getLong(4),
                    row.getString(6),
                    row.getString(12),
                    row.getString(14),
                    row.getString(15),
                    row.getString(16),
                    row.getString(17),
                    row.getString(19)
                );

                row.moveToNext();
            }

            Log.e("count solicitudes", row.getCount()+" total");
        }

        row.close();
    }

    public void GetPrestamos(Context ctx, boolean flag)
    {
        SessionManager session = new SessionManager(ctx);
        PrestamoDao prestamoDao = new PrestamoDao(ctx);
        ApoyoGastosFunerariosDao agfDao = new ApoyoGastosFunerariosDao(ctx);

        PrestamoService prestamoService = new RetrofitClient().newInstance(ctx).create(PrestamoService.class);
        Call<List<Prestamo>> call = prestamoService.show("Bearer "+ session.getUser().get(7));

        call.enqueue(new Callback<List<Prestamo>>() {
            @Override
            public void onResponse(Call<List<Prestamo>> call, Response<List<Prestamo>> response) {
                switch (response.code()){
                    case 200:
                        List<Prestamo> prestamos = response.body();

                        if (prestamos != null && prestamos.size() > 0){
                            for (Prestamo item : prestamos){
                                Prestamo prestamoDb = null;
                                ApoyoGastosFunerarios agf = null;

                                if (item.getGrupoId() > 1) {
                                    prestamoDb = prestamoDao.findByGrupoIdAndNumSolicitud(item.getGrupoId(), item.getNumSolicitud());
                                }
                                else
                                {
                                    prestamoDb = prestamoDao.findByClienteIdAndNumSolicitud(item.getClienteId(), item.getNumSolicitud());
                                }

                                if(prestamoDb != null)
                                {
                                    if (item.getGrupoId() > 1) {
                                        agf = agfDao.findByGrupoIdAndNumSolicitud(item.getGrupoId(), item.getNumSolicitud());
                                    }
                                    else
                                    {
                                        agf = agfDao.findByClienteIdAndNumSolicitud(item.getClienteId(), item.getNumSolicitud());
                                    }

                                    if(agf == null)
                                    {
                                        prestamoDao.update(prestamoDb.getId(), item);
                                    }
                                }
                                else
                                {
                                    prestamoDao.store(item);
                                }
                            }
                        }
                        break;
                    default:
                        Log.e("ERROR AQUI PREST AUT", response.message());
                        break;
                }
            }

            @Override
            public void onFailure(Call<List<Prestamo>> call, Throwable t) {
                Log.e("ErrorAgf", "Fail AGG"+t.getMessage());
                t.printStackTrace();
            }
        });
    }

    public void SendIntegranteRenovacionGpo(Context ctx, int iIndex, int iTotalRegistros, String sDato0, Long lDato4, String sDato6, String sDato7, String sDato12, String sDato14, String sDato15, String sDato16, String sDato17, String sDato19, String sDato21, String sDato23)
    {
        final DBhelper dBhelper = new DBhelper(ctx);
        final SQLiteDatabase db = dBhelper.getWritableDatabase();

        String sql;
        Cursor rowTotal;
        int iTotal;

        if(iTotalRegistros < 0)
        {
            sql = "SELECT i.*, t.*, d.*, n.*, c.*, o.*, docu.*, p.*, cro.* FROM " + TBL_INTEGRANTES_GPO_REN + " AS i " +
                "INNER JOIN " + TBL_TELEFONOS_INTEGRANTE_REN + " AS t ON i.id = t.id_integrante " +
                "INNER JOIN " + TBL_DOMICILIO_INTEGRANTE_REN + " AS d ON i.id = d.id_integrante " +
                "INNER JOIN " + TBL_NEGOCIO_INTEGRANTE_REN + " AS n ON i.id = n.id_integrante " +
                "INNER JOIN " + TBL_CONYUGE_INTEGRANTE_REN + " AS c ON i.id = c.id_integrante " +
                "INNER JOIN " + TBL_OTROS_DATOS_INTEGRANTE_REN + " AS o ON i.id = o.id_integrante " +
                "INNER JOIN " + TBL_CROQUIS_GPO_REN + " AS cro ON i.id = cro.id_integrante " +
                "INNER JOIN " + TBL_POLITICAS_PLD_INTEGRANTE_REN + " AS p ON i.id = p.id_integrante " +
                "INNER JOIN " + TBL_DOCUMENTOS_INTEGRANTE_REN + " AS docu ON i.id = docu.id_integrante " +
                "WHERE i.id_credito = ? " +
                //"AND i.id_solicitud_integrante = ? " +
                "AND i.estatus_completado = 1 " +
                "order by i.id_credito"
            ;

            //rowTotal = db.rawQuery(sql, new String[]{sDato12, "0"});
            rowTotal = db.rawQuery(sql, new String[]{sDato12});
            iTotal = rowTotal.getCount();
        }
        else
        {
            iTotal = iTotalRegistros;
        }

        String sqlIntegrante = "SELECT " +
                "i.id," +
                "i.id_credito," +
                "i.cargo," +
                "i.nombre," +
                "i.paterno," +
                "i.materno," +
                "i.fecha_nacimiento," +
                "i.edad," +
                "i.genero," +
                "i.estado_nacimiento," +
                "i.rfc," +
                "i.curp," +
                "i.curp_digito_veri," +
                "i.tipo_identificacion," +
                "i.no_identificacion," +
                "i.nivel_estudio," +
                "i.ocupacion," +
                "i.estado_civil," +
                "i.bienes," +
                "i.estatus_rechazo," +
                "i.comentario_rechazo," +
                "i.estatus_completado," +
                "i.id_solicitud_integrante," +
                "i.is_nuevo," +
                "i.cliente_id," +
                "t.id_telefonico," +
                "t.id_integrante," +
                "t.tel_casa," +
                "t.tel_celular," +
                "t.tel_mensaje," +
                "t.tel_trabajo," +
                "t.estatus_completado," +
                "d.id_domicilio," +
                "d.id_integrante," +
                "d.latitud," +
                "d.longitud," +
                "d.calle," +
                "d.no_exterior," +
                "d.no_interior," +
                "d.manzana," +
                "d.lote," +
                "d.cp," +
                "d.colonia," +
                "d.ciudad," +
                "d.localidad," +
                "d.municipio," +
                "d.estado," +
                "d.tipo_vivienda," +
                "d.parentesco," +
                "d.otro_tipo_vivienda," +
                "d.tiempo_vivir_sitio," +
                "d.foto_fachada," +
                "d.ref_domiciliaria," +
                "d.estatus_completado," +
                "d.dependientes," +
                "n.id_negocio," +
                "n.id_integrante," +
                "n.nombre," +
                "n.latitud," +
                "n.longitud," +
                "n.calle," +
                "n.no_exterior," +
                "n.no_interior," +
                "n.manzana," +
                "n.lote," +
                "n.cp," +
                "n.colonia," +
                "n.ciudad," +
                "n.localidad," +
                "n.municipio," +
                "n.estado," +
                "n.destino_credito," +
                "n.otro_destino_credito," +
                "n.ocupacion," +
                "n.actividad_economica," +
                "n.antiguedad," +
                "n.ing_mensual," +
                "n.ing_otros," +
                "n.gasto_semanal," +
                "n.capacidad_pago," +
                "n.monto_maximo," +
                "n.medios_pago," +
                "n.otro_medio_pago," +
                "n.num_ope_mensuales," +
                "n.num_ope_mensuales_efectivo," +
                "n.foto_fachada," +
                "n.ref_domiciliaria," +
                "n.estatus_rechazo," +
                "n.comentario_rechazo," +
                "n.estatus_completado," +
                "c.id_conyuge," +
                "c.id_integrante," +
                "c.nombre," +
                "c.paterno," +
                "c.materno," +
                "c.nacionalidad," +
                "c.ocupacion," +
                "c.calle," +
                "c.no_exterior," +
                "c.no_interior," +
                "c.manzana," +
                "c.lote," +
                "c.cp," +
                "c.colonia," +
                "c.ciudad," +
                "c.localidad," +
                "c.municipio," +
                "c.estado," +
                "c.ingresos_mensual," +
                "c.gasto_mensual," +
                "c.tel_celular," +
                "c.tel_trabajo," +
                "c.estatus_completado," +
                "o.id_otro," +
                "o.id_integrante," +
                "o.clasificacion_riesgo," +
                "o.medio_contacto," +
                "o.email," +
                "o.estado_cuenta," +
                "o.estatus_integrante," +
                "o.monto_solicitado," +
                "o.casa_reunion," +
                "o.firma," +
                "o.estatus_completado," +
                "docu.id_documento," +
                "docu.id_integrante," +
                "docu.ine_frontal," +
                "docu.ine_reverso," +
                "docu.curp," +
                "docu.comprobante," +
                "docu.estatus_completado," +
                "p.id_politica," +
                "p.id_integrante," +
                "p.propietario_real," +
                "p.proveedor_recursos," +
                "p.persona_politica," +
                "p.estatus_completado," +
                "cro.id," +
                "cro.id_integrante," +
                "cro.calle_principal," +
                "cro.lateral_uno," +
                "cro.lateral_dos," +
                "cro.calle_trasera," +
                "cro.referencias," +
                "cro.estatus_completado," +
                "d.located_at," +
                "n.located_at," +
                "o.latitud," +
                "o.longitud," +
                "o.located_at," +
                "o.tiene_firma," +
                "o.nombre_quien_firma," +
                "o.monto_refinanciar," +
                "cro.caracteristicas_domicilio," +
                "docu.ine_selfie" +
            " FROM " + TBL_INTEGRANTES_GPO_REN + " AS i " +
            "INNER JOIN " + TBL_TELEFONOS_INTEGRANTE_REN + " AS t ON i.id = t.id_integrante " +
            "INNER JOIN " + TBL_DOMICILIO_INTEGRANTE_REN + " AS d ON i.id = d.id_integrante " +
            "INNER JOIN " + TBL_NEGOCIO_INTEGRANTE_REN + " AS n ON i.id = n.id_integrante " +
            "INNER JOIN " + TBL_CONYUGE_INTEGRANTE_REN + " AS c ON i.id = c.id_integrante " +
            "INNER JOIN " + TBL_OTROS_DATOS_INTEGRANTE_REN + " AS o ON i.id = o.id_integrante " +
            "INNER JOIN " + TBL_CROQUIS_GPO_REN + " AS cro ON i.id = cro.id_integrante " +
            "INNER JOIN " + TBL_POLITICAS_PLD_INTEGRANTE_REN + " AS p ON i.id = p.id_integrante " +
            "INNER JOIN " + TBL_DOCUMENTOS_INTEGRANTE_REN + " AS docu ON i.id = docu.id_integrante " +
            "WHERE i.id_credito = ? " +
            //"AND i.id_solicitud_integrante = ? " +
            "AND i.estatus_completado = 1 " +
            "order by i.id_credito " +
            "limit 1"
        ;

        //Cursor rowIntegrante = db.rawQuery(sqlIntegrante, new String[]{sDato12, "0"});
        Cursor rowIntegrante = db.rawQuery(sqlIntegrante, new String[]{sDato12});
        rowIntegrante.moveToFirst();

        String id_solicitud = sDato0;
        Long id_solicitud_grupal = lDato4;

        Log.e("Count","a: "+ lDato4);
        Log.e("RENOVACION_GPO", "Total: "+rowIntegrante.getCount());

        for (int j = 0; j < rowIntegrante.getCount(); j++)
        {
            JSONObject json_solicitud = new JSONObject();
            try {
                json_solicitud.put(K_TOTAL_INTEGRANTES, rowIntegrante.getCount());
                json_solicitud.put(K_NOMBRE_GRUPO, sDato14.trim().toUpperCase());
                json_solicitud.put(K_PLAZO, Miscellaneous.GetPlazo(sDato15));
                json_solicitud.put(K_PERIODICIDAD, Miscellaneous.GetPeriodicidad(sDato16));
                json_solicitud.put(K_FECHA_DESEMBOLSO, sDato17);
                json_solicitud.put(K_HORA_VISITA, sDato19);
                json_solicitud.put(K_TIPO_SOLICITUD, "RENOVACION");
                json_solicitud.put(K_OBSERVACIONES, sDato21);
                json_solicitud.put(K_GRUPO_ID, sDato23);
                json_solicitud.put(K_FECHA_INICIO, sDato6);
                json_solicitud.put(K_FECHA_TERMINO, sDato7);
                json_solicitud.put(K_FECHA_ENVIO, Miscellaneous.ObtenerFecha(TIMESTAMP));

                JSONObject json_solicitante = new JSONObject();
                json_solicitante.put(K_CLIENTE_ID, rowIntegrante.getLong(24));
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

                json_solicitante.put(K_LATITUD, rowIntegrante.getString(34));
                json_solicitante.put(K_LONGITUD, rowIntegrante.getString(35));
                json_solicitante.put(K_CALLE, rowIntegrante.getString(36));
                json_solicitante.put(K_NO_EXTERIOR, rowIntegrante.getString(37));
                json_solicitante.put(K_NO_INTERIOR, rowIntegrante.getString(38));
                json_solicitante.put(K_NO_MANZANA, rowIntegrante.getString(39));
                json_solicitante.put(K_NO_LOTE, rowIntegrante.getString(40));
                json_solicitante.put(K_CODIGO_POSTAL, rowIntegrante.getInt(41));
                json_solicitante.put(K_COLONIA, rowIntegrante.getString(42));
                json_solicitante.put(K_CIUDAD, rowIntegrante.getString(43));
                json_solicitante.put(K_LOCALIDAD, rowIntegrante.getString(44));
                json_solicitante.put(K_MUNICIPIO, rowIntegrante.getString(45));
                json_solicitante.put(K_ESTADO, rowIntegrante.getString(46));
                json_solicitante.put(K_LOCATED_AT, rowIntegrante.getString(143));

                json_solicitante.put(K_TIPO_VIVIENDA, rowIntegrante.getString(47));
                if (rowIntegrante.getString(47).equals("CASA FAMILIAR"))
                    json_solicitante.put(K_PARENTESCO, rowIntegrante.getString(48));
                else if (rowIntegrante.getString(47).equals("OTRO"))
                    json_solicitante.put(K_OTRO_TIPO_VIVIENDA, rowIntegrante.getString(49));

                json_solicitante.put(K_TIEMPO_VIVIR_SITIO, rowIntegrante.getInt(50));
                json_solicitante.put(K_DEPENDIENTES_ECONOMICO, rowIntegrante.getInt(54));
                json_solicitante.put(K_FOTO_FACHADA, rowIntegrante.getString(51));
                String fachadaCli = rowIntegrante.getString(51);
                json_solicitante.put(K_REFERENCIA_DOMICILIARIA, rowIntegrante.getString(52));

                json_solicitante.put(K_TEL_CASA, rowIntegrante.getString(27));
                json_solicitante.put(K_TEL_CELULAR, rowIntegrante.getString(28));
                json_solicitante.put(K_TEL_MENSAJE, rowIntegrante.getString(29));
                json_solicitante.put(K_TEL_TRABAJO, rowIntegrante.getString(30));

                json_solicitante.put(K_MEDIO_CONTACTO, rowIntegrante.getString(116));
                json_solicitante.put(K_EMAIL, rowIntegrante.getString(117));
                json_solicitante.put(K_ESTADO_CUENTA, rowIntegrante.getString(118));
                json_solicitante.put(K_FIRMA, rowIntegrante.getString(122));
                String firmaCli = rowIntegrante.getString(122);
                json_solicitante.put(K_SOL_LATITUD, rowIntegrante.getString(147));//145
                json_solicitante.put(K_SOL_LONGITUD, rowIntegrante.getString(148));//146
                json_solicitante.put(K_SOL_LOCATED_AT, rowIntegrante.getString(149));//147
                json_solicitante.put(K_TIENE_FIRMA, rowIntegrante.getString(150));//148
                json_solicitante.put(K_NOMBRE_QUIEN_FIRMA, rowIntegrante.getString(151));//149
                json_solicitud.put(K_SOLICITANTE, json_solicitante);

                Log.e("solicitante", json_solicitante.toString());

                JSONObject json_otros_datos = new JSONObject();
                json_otros_datos.put(K_CARGO, rowIntegrante.getInt(2));
                json_otros_datos.put(K_CLASIFICACION_RIESGO, rowIntegrante.getString(115));
                json_otros_datos.put(K_ESTATUS_INTEGRANTE, rowIntegrante.getInt(119));
                json_otros_datos.put(K_MONTO_PRESTAMO, rowIntegrante.getInt(120));
                json_otros_datos.put(K_MONTO_LETRA, (Miscellaneous.cantidadLetra(rowIntegrante.getString(120)).toUpperCase() + " PESOS MEXICANOS").replace("  ", " "));
                json_otros_datos.put(K_CASA_REUNION, (rowIntegrante.getInt(121) == 1));
                int montoRefinanciar = 0;
                if(rowIntegrante.getString(152) != null && !rowIntegrante.getString(152).isEmpty()) montoRefinanciar = Integer.parseInt(rowIntegrante.getString(152).replace(",",""));
                json_otros_datos.put(K_MONTO_REFINANCIAR, montoRefinanciar);
                json_solicitud.put(K_OTROS_DATOS, json_otros_datos);


                if (rowIntegrante.getString(17).equals("CASADO(A)") ||
                        rowIntegrante.getString(17).equals("UNIÓN LIBRE")) {
                    JSONObject json_conyuge = new JSONObject();

                    json_conyuge.put(K_NOMBRE, rowIntegrante.getString(92));
                    json_conyuge.put(K_PATERNO, rowIntegrante.getString(93));
                    json_conyuge.put(K_MATERNO, rowIntegrante.getString(94));
                    json_conyuge.put(K_NACIONALIDAD, rowIntegrante.getString(95));
                    json_conyuge.put(K_OCUPACION, rowIntegrante.getString(96));
                    json_conyuge.put(K_CALLE, rowIntegrante.getString(97));
                    json_conyuge.put(K_NO_EXTERIOR, rowIntegrante.getString(98));
                    json_conyuge.put(K_NO_INTERIOR, rowIntegrante.getString(99));
                    json_conyuge.put(K_NO_MANZANA, rowIntegrante.getString(100));
                    json_conyuge.put(K_NO_LOTE, rowIntegrante.getString(101));
                    json_conyuge.put(K_CODIGO_POSTAL, rowIntegrante.getInt(102));
                    json_conyuge.put(K_COLONIA, rowIntegrante.getString(103));
                    json_conyuge.put(K_CIUDAD, rowIntegrante.getString(104));
                    json_conyuge.put(K_LOCALIDAD, rowIntegrante.getString(105));
                    json_conyuge.put(K_MUNICIPIO, rowIntegrante.getString(106));
                    json_conyuge.put(K_ESTADO, rowIntegrante.getString(107));
                    json_conyuge.put(K_INGRESO_MENSUAL, rowIntegrante.getDouble(108));
                    json_conyuge.put(K_GASTO_MENSUAL, rowIntegrante.getDouble(109));
                    json_conyuge.put(K_TEL_CELULAR, rowIntegrante.getString(110));
                    json_conyuge.put(K_TEL_CASA, rowIntegrante.getString(111));

                    json_solicitud.put(K_SOLICITANTE_CONYUGE, json_conyuge);

                    Log.e("conyuge", json_conyuge.toString());
                }

                JSONObject json_negocio = new JSONObject();
                json_negocio.put(K_NOMBRE, rowIntegrante.getString(57));
                json_negocio.put(K_LATITUD, rowIntegrante.getString(58));
                json_negocio.put(K_LONGITUD, rowIntegrante.getString(59));
                json_negocio.put(K_CALLE, rowIntegrante.getString(60));
                json_negocio.put(K_NO_EXTERIOR, rowIntegrante.getString(61));
                json_negocio.put(K_NO_INTERIOR, rowIntegrante.getString(62));
                json_negocio.put(K_NO_MANZANA, rowIntegrante.getString(63));
                json_negocio.put(K_NO_LOTE, rowIntegrante.getString(64));
                json_negocio.put(K_CODIGO_POSTAL, rowIntegrante.getInt(65));
                json_negocio.put(K_COLONIA, rowIntegrante.getString(66));
                json_negocio.put(K_CIUDAD, rowIntegrante.getString(67));
                json_negocio.put(K_LOCALIDAD, rowIntegrante.getString(68));
                json_negocio.put(K_MUNICIPIO, rowIntegrante.getString(69));
                json_negocio.put(K_ESTADO, rowIntegrante.getString(70));
                json_negocio.put(K_LOCATED_AT, rowIntegrante.getString(144));
                json_negocio.put(K_DESTINO_CREDITO, rowIntegrante.getString(71));
                if (rowIntegrante.getString(71).contains("OTRO"))
                    json_negocio.put(K_OTRO_DESTINO_CREDITO, rowIntegrante.getString(72));
                json_negocio.put(K_OCUPACION, rowIntegrante.getString(73));
                json_negocio.put(K_ACTIVIDAD_ECONOMICA, rowIntegrante.getString(74));
                json_negocio.put(K_ANTIGUEDAD, rowIntegrante.getInt(75));
                json_negocio.put(K_INGRESO_MENSUAL, rowIntegrante.getString(76));
                json_negocio.put(K_INGRESOS_OTROS, rowIntegrante.getString(77));
                json_negocio.put(K_GASTO_MENSUAL, rowIntegrante.getString(78));
                //json_negocio.put(K_CAPACIDAD_PAGO, rowIntegrante.getString(79));//se intercambia con monto maximo
                json_negocio.put(K_MONTO_MAXIMO, rowIntegrante.getString(79));
                //json_negocio.put(K_MONTO_MAXIMO, rowIntegrante.getString(80));//se intercambia con capacidad de pago
                json_negocio.put(K_CAPACIDAD_PAGO, rowIntegrante.getString(80));
                String aux = "";
                if (!rowIntegrante.getString(81).trim().isEmpty()) {
                    String[] medios = rowIntegrante.getString(81).split(",");

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

                if (rowIntegrante.getString(81).contains("OTRO"))
                    json_negocio.put(K_OTRO_MEDIOS_PAGOS, rowIntegrante.getString(82));

                json_negocio.put(K_NUM_OPERACIONES_MENSUAL, rowIntegrante.getInt(83));

                if (rowIntegrante.getString(81).contains("EFECTIVO"))
                    json_negocio.put(K_NUM_OPERACIONES_MENSUAL_EFECTIVO, rowIntegrante.getInt(84));

                json_negocio.put(K_FOTO_FACHADA, rowIntegrante.getString(85));
                String fachadaNeg = rowIntegrante.getString(85);
                json_negocio.put(K_REFERENCIA_NEGOCIO, rowIntegrante.getString(86));

                json_solicitud.put(K_SOLICITANTE_NEGOCIO, json_negocio);

                Log.e("negocio", json_negocio.toString());

                JSONObject json_documentos = new JSONObject();
                json_documentos.put(K_IDENTIFICACION_FRONTAL, rowIntegrante.getString(126));
                String identiFrontal = rowIntegrante.getString(126);
                json_documentos.put(K_IDENTIFICACION_REVERSO, rowIntegrante.getString(127));
                String identiReverso = rowIntegrante.getString(127);
                json_documentos.put(K_CURP, rowIntegrante.getString(128));
                String curp = rowIntegrante.getString(128);
                json_documentos.put(K_COMPROBANTE_DOMICILIO, rowIntegrante.getString(129));
                String comprobante = rowIntegrante.getString(129);

                String identiSelfie = "";
                if(rowIntegrante.getString(154) != null)
                {
                    json_documentos.put(K_IDENTIFICACION_SELFIE, rowIntegrante.getString(154));
                    identiSelfie = rowIntegrante.getString(154);
                }
                else
                {
                    json_documentos.put(K_IDENTIFICACION_SELFIE, "");
                }

                json_solicitud.put(K_SOLICITANTE_DOCUMENTOS, json_documentos);

                JSONObject json_politicas = new JSONObject();
                json_politicas.put(K_PROPIETARIO, rowIntegrante.getInt(133) == 1);
                json_politicas.put(K_PROVEEDOR_RECURSOS, rowIntegrante.getInt(134) == 1);
                json_politicas.put(K_POLITICAMENTE_EXP, rowIntegrante.getInt(135) == 1);
                json_solicitud.put(K_SOLICITANTE_POLITICAS, json_politicas);

                if (rowIntegrante.getInt(121) == 1){
                    JSONObject json_croquis = new JSONObject();
                    json_croquis.put(K_CALLE_ENFRENTE, rowIntegrante.getString(139));
                    json_croquis.put(K_CALLE_LATERAL_IZQ, rowIntegrante.getString(140));
                    json_croquis.put(K_CALLE_LATERAL_DER, rowIntegrante.getString(141));
                    json_croquis.put(K_CALLE_ATRAS, rowIntegrante.getString(142));
                    json_croquis.put(K_REFERENCIAS, rowIntegrante.getString(143));
                    json_croquis.put(K_CARACTERISTICAS_DOMICILIO, rowIntegrante.getString(153));
                    json_solicitud.put(K_SOLICITANTE_CROQUIS, json_croquis);
                }

                Log.e("_","_______________________________________________________________________-");
                Log.e("solicitudInt", json_solicitud.toString());
                Log.e("_","_______________________________________________________________________-");

                new GuardarRenovacionGpo().execute(
                    ctx,
                    json_solicitud,
                    fachadaCli,
                    firmaCli,
                    fachadaNeg,
                    identiFrontal,
                    identiReverso,
                    curp,
                    comprobante,
                    rowIntegrante.getString(0),
                    rowIntegrante.getString(1),
                    id_solicitud,
                    String.valueOf(rowIntegrante.getLong(22)),
                    String.valueOf(lDato4),
                    iIndex,
                    iTotal,
                    sDato0,
                    lDato4,
                    sDato6,
                    sDato7,
                    sDato12,
                    sDato14,
                    sDato15,
                    sDato16,
                    sDato17,
                    sDato19,
                    sDato21,
                    sDato23,
                    identiSelfie
                );
            } catch (JSONException e) {
                e.printStackTrace();
            }
            rowIntegrante.moveToNext();
        }

        rowIntegrante.close();
    }

    public void SendRenovacionGpo (Context ctx, boolean flag){
        final AlertDialog loading = Popups.showLoadingDialog(ctx, R.string.please_wait, R.string.loading_info);

        if (flag)
            loading.show();
        SessionManager session = new SessionManager(ctx);
        final DBhelper dBhelper = new DBhelper(ctx);
        final SQLiteDatabase db = dBhelper.getWritableDatabase();

        String sql = "SELECT s.id_solicitud, s.vol_solicitud, s.usuario_id, s.tipo_solicitud, s.id_originacion, s.nombre, s.fecha_inicio, s.fecha_termino, s.fecha_envio, s.fecha_dispositivo, s.fecha_guardado, s.estatus, c.* FROM " + TBL_SOLICITUDES_REN + " AS s INNER JOIN " +TBL_CREDITO_GPO_REN + " AS c ON s.id_solicitud = c.id_solicitud WHERE s.tipo_solicitud = 2 AND s.estatus = 1";
        Cursor row = db.rawQuery(sql, null);
        //Cursor row = dBhelper.getRecords(TBL_SOLICITUDES, " WHERE tipo_solicitud = 2", "", null);


        if (row.getCount() > 0){
            row.moveToFirst();

            for(int i = 0; i < row.getCount(); i++)
            {
                SendIntegranteRenovacionGpo(
                    ctx,
                    0,
                    -1,
                    row.getString(0),
                    row.getLong(4),
                    row.getString(6),
                    row.getString(7),
                    row.getString(12),
                    row.getString(14),
                    row.getString(15),
                    row.getString(16),
                    row.getString(17),
                    row.getString(19),
                    row.getString(21),
                    row.getString(23)
                );

                row.moveToNext();
            }

            Log.e("count solicitudes", row.getCount()+" total");
        }

        row.close();
    }

    public void MontoAutorizado (Context ctx, boolean flag){
        final AlertDialog loading = Popups.showLoadingDialog(ctx, R.string.please_wait, R.string.loading_info);

        if (flag)
            loading.show();
        SessionManager session = new SessionManager(ctx);
        final DBhelper dBhelper = new DBhelper(ctx);
        final SQLiteDatabase db = dBhelper.getWritableDatabase();

        String sql = "SELECT * FROM " + TBL_SOLICITUDES_AUTO + " WHERE estatus = ? AND solicitud = ?";
        Cursor rs = db.rawQuery(sql, new String[]{"1", "1"});

        Log.e("MontAuto", rs.getCount()+" TOTAL");
        if (rs.getCount() > 0){
            rs.moveToFirst();
            for (int i = 0; i < rs.getCount(); i++){
                sql = "SELECT * FROM " + TBL_CREDITO_IND_AUTO + " WHERE id_solicitud = ?";
                Cursor rc = db.rawQuery(sql, new String[]{rs.getString(0)});
                rc.moveToFirst();
                Log.e("tipoSolicitud", rs.getLong(1)+" Tipo");
                new SendMontoAutorizado().execute(
                    ctx,                //Contexto
                    rs.getString(0), //id_solicitud
                    rs.getLong(1),   //TipoSolicitud
                    rs.getLong(4),   //SolicitudId
                    rc.getLong(16),   //MontoAutorizado
                    rc.getLong(0),   //creditoId
                    rs.getLong(0)
                );
                rc.close();

                rs.moveToNext();
            }
        }
        rs.close();

        Log.e("SoliGPO", "Comienza obtener grupales");
        //obtiene a los grupales
        sql = "SELECT * FROM " + TBL_SOLICITUDES_AUTO + " WHERE estatus = ? AND solicitud = ?";
        rs = db.rawQuery(sql, new String[]{"1", "2"});
        Log.e("SoliGPO", "TotalSolicitudes"+rs.getCount());
        if (rs.getCount() > 0){
            rs.moveToFirst();
            for (int i = 0; i < rs.getCount(); i++) {
                sql = "SELECT * FROM " + TBL_CREDITO_GPO_AUTO + " WHERE id_solicitud = ?";
                Cursor rc = db.rawQuery(sql, new String[]{rs.getString(0)});
                Log.e("SoliGPO", "TotalCreditos"+rc.getCount());
                if (rc.getCount() > 0){
                    rc.moveToFirst();
                    for (int j = 0; j < rc.getCount(); j++){
                        sql = "SELECT i.*, o.monto_autorizado FROM " + TBL_INTEGRANTES_GPO_AUTO + " AS i " +
                                "INNER JOIN " +TBL_OTROS_DATOS_INTEGRANTE_AUTO + " AS o ON o.id_integrante = i.id " +
                                "WHERE i.id_credito = ? AND o.estatus_completado = ? AND i.estatus_completado = ?";
                        Cursor ri = db.rawQuery(sql, new String[]{rc.getString(0), "1", "1"});
                        Log.e("SoliGPO", "TotalIntegrantes"+ri.getCount());
                        if (ri.getCount() > 0){
                            ri.moveToFirst();
                            for (int k = 0; k < ri.getCount(); k ++){
                                /*Log.e("Autorizado","idMovil: "+ ri.getString(0)+"\n"+
                                        "TipoSoli: "+rs.getLong(1)+"\n"+
                                        "SolicitudId: "+ri.getLong(22)+"\n"+
                                        "Nombre: "+ri.getString(3)+"\n"+
                                        "montoAuto: "+ri.getLong(23));*/
                                new SendMontoAutorizado().execute(
                                        ctx,                //Contexto
                                        ri.getString(0), //id_solicitud
                                        rs.getLong(1),   //TipoSolicitud
                                        ri.getLong(22),   //SolicitudId
                                        ri.getLong(23),   //MontoAutorizado
                                        rc.getLong(0),   //creditoId
                                        rs.getLong(0)
                                );
                                ri.moveToNext();
                            }
                        }
                        ri.close();

                        rc.moveToNext();
                    }
                    rc.close();
                }
                rs.moveToNext();
            }
        }


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
                                            Cursor row_pago = dBhelper.getRecords(TBL_PAGOS_T, " WHERE id_prestamo = ? AND fecha = ? AND monto = ? AND banco = ?", "",
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
                                            Cursor row_pago = dBhelper.getRecords(TBL_PAGOS_T, " WHERE id_prestamo = ? AND fecha = ? AND monto = ? AND banco = ?", "",
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

        String sql = "SELECT * FROM (SELECT vi._id, vi.num_prestamo_id_gestion, vi.asesor_id, vi.folio, vi.tipo_impresion, vi.monto, vi.clave_cliente, vi.create_at, vi.sent_at, vi.estatus, vi.num_prestamo, pi.tipo_cartera, '1' AS tipo_gestion, 'VIGENTE' AS tipo_prestamo, vi.celular FROM " + TBL_IMPRESIONES_VIGENTE_T + " AS vi INNER JOIN " + TBL_PRESTAMOS_IND_T + " AS pi ON vi.num_prestamo = pi.num_prestamo WHERE vi.num_prestamo LIKE '%-L%' UNION SELECT vi2._id, vi2.num_prestamo_id_gestion, vi2.asesor_id, vi2.folio, vi2.tipo_impresion, vi2.monto, vi2.clave_cliente, vi2.create_at, vi2.sent_at, vi2.estatus, vi2.num_prestamo, pg.tipo_cartera, '2' AS tipo_gestion, 'VIGENTE' AS tipo_prestamo, vi2.celular FROM " + TBL_IMPRESIONES_VIGENTE_T + " AS vi2 INNER JOIN " + TBL_PRESTAMOS_GPO_T + " AS pg ON vi2.num_prestamo = pg.num_prestamo WHERE vi2.num_prestamo NOT LIKE '%-L%' UNION SELECT v._id, v.num_prestamo_id_gestion, v.asesor_id, v.folio, v.tipo_impresion, v.monto, v.clave_cliente, v.create_at, v.sent_at, v.estatus, v.num_prestamo, pvi.tipo_cartera, '1' AS tipo_gestion, 'VENCIDA' AS tipo_prestamo, v.celular FROM " + TBL_IMPRESIONES_VENCIDA_T + " AS v INNER JOIN " + TBL_PRESTAMOS_IND_T + " AS pvi ON v.num_prestamo = pvi.num_prestamo WHERE v.num_prestamo LIKE '%-L%' UNION SELECT vg._id, vg.num_prestamo_id_gestion, vg.asesor_id, vg.folio, vg.tipo_impresion, vg.monto, vg.clave_cliente, vg.create_at, vg.sent_at, vg.estatus, vg.num_prestamo, pvg.tipo_cartera, '2' AS tipo_gestion, 'VENCIDA' AS tipo_prestamo, vg.celular FROM " + TBL_IMPRESIONES_VENCIDA_T + " AS vg INNER JOIN " + TBL_PRESTAMOS_GPO_T + " AS pvg ON vg.num_prestamo = pvg.num_prestamo WHERE vg.num_prestamo NOT LIKE '%-L%') AS imp WHERE estatus = ?";

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
                        item.setCelular(row.getString(14));

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

        GestionDao gestionDao = new GestionDao(ctx);
        GestionCirculoCreditoDao gestionCirculoCreditoDao = new GestionCirculoCreditoDao(ctx);
        PrestamoDao prestamoDao = new PrestamoDao(ctx);
        ReciboDao reciboDao = new ReciboDao(ctx);
        ReciboCirculoCreditoDao reciboCirculoCreditoDao = new ReciboCirculoCreditoDao(ctx);

        List<Gestion> gestiones;
        List<GestionCirculoCredito> gestionesCirculoCredito;
        List<Integer> estatus = new ArrayList<Integer>();

        //SE USA EN AGF Y CIRCULO
        estatus.add(0);
        estatus.add(1);

        gestiones = gestionDao.findAllByEstatus(estatus);

        for(Gestion g : gestiones)
        {
            List<Recibo> recibos = null;

            if(g.getGrupoId().equals("1"))
            {
                recibos = reciboDao.findAllByNombreAndNumSolicitud(g.getNombre(), g.getNumSolicitud());
            }
            else
            {
                recibos = reciboDao.findAllByGrupoIdAndNumSolicitud(g.getGrupoId(), g.getNumSolicitud());
            }

            if(recibos.size() > 0)
            {
                for(Recibo r : recibos)
                {
                    new GuardarAgf().execute(ctx, g, r);
                }
            }
            else
            {
                Recibo r = new Recibo();
                Prestamo p = null;

                if(g.getGrupoId().equals("1"))
                {
                    p = prestamoDao.findByClienteIdAndNumSolicitud(g.getClienteId(), Integer.parseInt(g.getNumSolicitud()));
                }
                else
                {
                    p = prestamoDao.findByGrupoIdAndNumSolicitud(Integer.parseInt(g.getGrupoId()), Integer.parseInt(g.getNumSolicitud()));
                }

                r.setPlazo( (p == null)? 0 : p.getPlazo() );

                new GuardarAgf().execute(ctx, g, r);
            }
        }

        gestionesCirculoCredito = gestionCirculoCreditoDao.findAllByEstatus(estatus);

        for(GestionCirculoCredito gcc : gestionesCirculoCredito)
        {
            List<ReciboCirculoCredito> recibosCirculoCredito = reciboCirculoCreditoDao.findAllByCurp(gcc.getCurp());

            if(recibosCirculoCredito.size() > 0)
            {
                for(ReciboCirculoCredito rcc : recibosCirculoCredito)
                {

                    if(gcc.getFolio() == 0)
                    {
                        gcc.setFolio(rcc.getFolio());
                        gestionCirculoCreditoDao.update(gcc.getId(), gcc);
                    }

                    new GuardarCC().execute(ctx, gcc, rcc);
                }
            }
            else
            {
                ReciboCirculoCredito rcc = new ReciboCirculoCredito();

                if(gcc.getFolio() > 0 || (gcc.getEvidencia() != null && !gcc.getEvidencia().equals("")))
                {
                    new GuardarCC().execute(ctx, gcc, rcc);
                }
            }
        }

        if (showDG)
            loading.dismiss();

    }

    public void CloseGestionesApoyoGastosFunerarios(Context ctx)
    {
        GestionDao gestionDao = new GestionDao(ctx);
        PrestamoDao prestamoDao = new PrestamoDao(ctx);
        ReciboDao reciboDao = new ReciboDao(ctx);

        List<Gestion> gestiones;
        List<Integer> estatus = new ArrayList<Integer>();

        //SE USA EN AGF
        estatus.add(0);
        estatus.add(1);

        gestiones = gestionDao.findAllByEstatusLastWeek(estatus);

        for(Gestion g : gestiones)
        {
            if(g.getEstatus() == 0) g.setEstatus(1);

            List<Recibo> recibos = null;

            if(g.getGrupoId().equals("1"))
            {
                recibos = reciboDao.findAllByNombreAndNumSolicitud(g.getNombre(), g.getNumSolicitud());
            }
            else
            {
                recibos = reciboDao.findAllByGrupoIdAndNumSolicitud(g.getGrupoId(), g.getNumSolicitud());
            }

            if(recibos.size() > 0)
            {
                for(Recibo r : recibos)
                {
                    new GuardarAgf().execute(ctx, g, r);
                }
            }
            else
            {
                Recibo r = new Recibo();
                Prestamo p = null;

                if(g.getGrupoId().equals("1"))
                {
                    p = prestamoDao.findByClienteIdAndNumSolicitud(g.getClienteId(), Integer.parseInt(g.getNumSolicitud()));
                }
                else
                {
                    p = prestamoDao.findByGrupoIdAndNumSolicitud(Integer.parseInt(g.getGrupoId()), Integer.parseInt(g.getNumSolicitud()));
                }

                r.setPlazo( (p == null)? 0 : p.getPlazo() );

                new GuardarAgf().execute(ctx, g, r);
            }
        }
    }

    public void CloseGestionesCobroCirculoCredtio(Context ctx)
    {

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

    public void GetSolicitudesRechazadasInd(Context ctx, boolean showDG){
        final DBhelper dBhelper = new DBhelper(ctx);
        final SQLiteDatabase db = dBhelper.getWritableDatabase();
        SessionManager session = new SessionManager(ctx);

        ManagerInterface api = new RetrofitClient().generalRF(CONTROLLER_SOLICITUDES, ctx).create(ManagerInterface.class);

        Call<List<MSolicitudRechazoInd>> call = api.getSolicitudRechazoInd("Bearer "+ session.getUser().get(7));

        call.enqueue(new Callback<List<MSolicitudRechazoInd>>() {
            @Override
            public void onResponse(Call<List<MSolicitudRechazoInd>> call, Response<List<MSolicitudRechazoInd>> response) {

                switch (response.code()){
                    case 200:
                        List<MSolicitudRechazoInd> solicitudes = response.body();
                        if (solicitudes.size() > 0){
                            for (MSolicitudRechazoInd item : solicitudes){
                                ContentValues cv;
                                String sql = "";
                                Cursor row = null;
                                if (item.getTipoSolicitud() == 1) {

                                    Log.e("EstautsXXXXXX",item.getSolicitudEstadoId()+" XXXXXXXXXx");
                                    //                 0                1               2              3                4                 5             6             7               8
                                    sql = "SELECT s.id_solicitud, cre.id_credito, cli.id_cliente, con.id_conyuge, eco.id_economico, neg.id_negocio, ava.id_aval, ref.id_referencia, cro.id FROM " + TBL_SOLICITUDES + " AS s " +
                                            "JOIN " + TBL_CREDITO_IND + " AS cre ON s.id_solicitud = cre.id_solicitud " +
                                            "JOIN " + TBL_CLIENTE_IND + " AS cli ON s.id_solicitud = cli.id_solicitud " +
                                            "JOIN " + TBL_CONYUGE_IND + " AS con ON s.id_solicitud = con.id_solicitud " +
                                            "JOIN " + TBL_ECONOMICOS_IND + " AS eco ON s.id_solicitud = eco.id_solicitud " +
                                            "JOIN " + TBL_NEGOCIO_IND + " AS neg ON s.id_solicitud = neg.id_solicitud " +
                                            "JOIN " + TBL_AVAL_IND + " AS ava ON s.id_solicitud = ava.id_solicitud " +
                                            "JOIN " + TBL_REFERENCIA_IND + " AS ref ON s.id_solicitud = ref.id_solicitud " +
                                            "JOIN " + TBL_CROQUIS_IND + " AS cro ON s.id_solicitud = cro.id_solicitud " +
                                            "WHERE s.id_originacion = ? AND s.estatus >= 2";
                                    row = db.rawQuery(sql, new String[]{String.valueOf(item.getId())});

                                    Log.e("XXXXCount", row.getCount()+" Total");
                                    if (row.getCount() > 0) {
                                        row.moveToFirst();
                                        if (item.getSolicitudEstadoId() == 4) { //Actualiza solicitudes de originacion que fueron rechazadas por error de datos

                                            cv = new ContentValues();
                                            cv.put("comentario_rechazo", "");
                                            db.update(TBL_CLIENTE_IND, cv, "id_solicitud = ? AND id_cliente = ?", new String[]{row.getString(0), row.getString(2)});

                                            if (item.getEstatusCliente() != null && !(Boolean) item.getEstatusCliente()) {
                                                cv = new ContentValues();
                                                cv.put("estatus", 0);
                                                cv.put("fecha_guardado", "");
                                                cv.put("fecha_termino", "");
                                                db.update(TBL_SOLICITUDES, cv, "id_solicitud = ?", new String[]{row.getString(0)});

                                                cv = new ContentValues();
                                                cv.put("estatus_completado", 0);
                                                cv.put("comentario_rechazo", Miscellaneous.validStr(item.getComentarioAdminCliente()));
                                                int i_update = db.update(TBL_CLIENTE_IND, cv, "id_solicitud = ? AND id_cliente = ?", new String[]{row.getString(0), row.getString(2)});
                                                Log.e("Update", "Update: " + i_update);

                                                cv = new ContentValues();
                                                cv.put("estatus_completado", 0);
                                                db.update(TBL_CONYUGE_IND, cv, "id_solicitud = ? AND id_conyuge = ?", new String[]{row.getString(0), row.getString(3)});

                                                cv = new ContentValues();
                                                cv.put("estatus_completado", 0);
                                                db.update(TBL_DOCUMENTOS, cv, "id_solicitud = ? ", new String[]{row.getString(0)});
                                            }

                                            if (item.getEstatusNegocio() != null && !(Boolean) item.getEstatusNegocio()) {
                                                cv = new ContentValues();
                                                cv.put("estatus", 0);
                                                cv.put("fecha_guardado", "");
                                                cv.put("fecha_termino", "");
                                                db.update(TBL_SOLICITUDES, cv, "id_solicitud = ?", new String[]{row.getString(0)});

                                                cv = new ContentValues();
                                                cv.put("estatus_completado", 0);
                                                cv.put("comentario_rechazo", Miscellaneous.validStr(item.getComentarioAdminNegocio()));
                                                db.update(TBL_NEGOCIO_IND, cv, "id_solicitud = ? AND id_negocio = ?", new String[]{row.getString(0), row.getString(5)});
                                            }

                                            Log.e("NEgocioValieCli", String.valueOf((item.getEstatusCliente() == null || (item.getEstatusCliente() != null && !(Boolean) item.getEstatusCliente()))));
                                            Log.e("negocioVal", String.valueOf(item.getEstatusAval() != null && !(Boolean) item.getEstatusAval()));
                                            if (item.getEstatusAval() != null && !(Boolean) item.getEstatusAval()) {
                                                cv = new ContentValues();
                                                cv.put("estatus", 0);
                                                cv.put("fecha_guardado", "");
                                                cv.put("fecha_termino", "");
                                                db.update(TBL_SOLICITUDES, cv, "id_solicitud = ?", new String[]{row.getString(0)});

                                                cv = new ContentValues();
                                                cv.put("estatus_completado", 0);
                                                cv.put("comentario_rechazo", Miscellaneous.validStr(item.getComentarioAdminAval()));
                                                db.update(TBL_AVAL_IND, cv, "id_solicitud = ? AND id_aval = ?", new String[]{row.getString(0), row.getString(6)});
                                            }

                                            Log.e("ReferenciaXXX", String.valueOf((item.getEstatusCliente() == null || (item.getEstatusCliente() != null && !(Boolean) item.getEstatusCliente()))
                                                    && item.getEstatusReferencia() != null && !(Boolean) item.getEstatusReferencia()));

                                            if (item.getEstatusReferencia() != null && !(Boolean) item.getEstatusReferencia()) {
                                                cv = new ContentValues();
                                                cv.put("estatus", 0);
                                                cv.put("fecha_guardado", "");
                                                cv.put("fecha_termino", "");
                                                db.update(TBL_SOLICITUDES, cv, "id_solicitud = ?", new String[]{row.getString(0)});

                                                cv = new ContentValues();
                                                cv.put("estatus_completado", 0);
                                                cv.put("comentario_rechazo", Miscellaneous.validStr(item.getComentarioAdminReferencia()));
                                                db.update(TBL_REFERENCIA_IND, cv, "id_solicitud = ? AND id_referencia = ?", new String[]{row.getString(0), row.getString(7)});
                                            }

                                            if (item.getEstatusCroquis() != null && !(Boolean) item.getEstatusCroquis()) {
                                                cv = new ContentValues();
                                                cv.put("estatus", 0);
                                                cv.put("fecha_guardado", "");
                                                cv.put("fecha_termino", "");
                                                db.update(TBL_SOLICITUDES, cv, "id_solicitud = ?", new String[]{row.getString(0)});

                                                cv = new ContentValues();
                                                cv.put("estatus_completado", 0);
                                                cv.put("comentario_rechazo", Miscellaneous.validStr(item.getComentarioAdminCroquis()));
                                                db.update(TBL_CROQUIS_IND, cv, "id_solicitud = ? AND id = ?", new String[]{row.getString(0), row.getString(8)});
                                            }
                                        }
                                        else if (item.getSolicitudEstadoId() == 2) { //Actualiza solicitudes de originacion que fueron solicitudes rechazadas
                                            Log.e("XXXXXXXX","xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
                                            Log.e("Comentario",  Miscellaneous.validStr(item.getComentarioAdminCliente()));
                                            cv = new ContentValues();
                                            cv.put("estatus", 5);
                                            db.update(TBL_SOLICITUDES, cv, "id_solicitud = ?", new String[]{row.getString(0)});

                                            cv = new ContentValues();
                                            cv.put("comentario_rechazo", Miscellaneous.validStr("NO AUTORIZADO - " + item.getComentarioAdminCliente()));
                                            db.update(TBL_CLIENTE_IND, cv, "id_solicitud = ? AND id_cliente = ?", new String[]{row.getString(0), row.getString(8)});
                                        }
                                    }
                                    row.close();
                                }
                                else{ //Actualiza solicitudes de renovacion que fueron rechazadas por la administradora
                                    sql = "SELECT s.id_solicitud, cre.id_credito, cli.id_cliente, con.id_conyuge, eco.id_economico, neg.id_negocio, ava.id_aval, ref.id_referencia, cro.id FROM " + TBL_SOLICITUDES_REN + " AS s " +
                                            "JOIN " + TBL_CREDITO_IND_REN + " AS cre ON s.id_solicitud = cre.id_solicitud " +
                                            "JOIN " + TBL_CLIENTE_IND_REN + " AS cli ON s.id_solicitud = cli.id_solicitud " +
                                            "JOIN " + TBL_CONYUGE_IND_REN + " AS con ON s.id_solicitud = con.id_solicitud " +
                                            "JOIN " + TBL_ECONOMICOS_IND_REN + " AS eco ON s.id_solicitud = eco.id_solicitud " +
                                            "JOIN " + TBL_NEGOCIO_IND_REN + " AS neg ON s.id_solicitud = neg.id_solicitud " +
                                            "JOIN " + TBL_AVAL_IND_REN + " AS ava ON s.id_solicitud = ava.id_solicitud " +
                                            "JOIN " + TBL_REFERENCIA_IND_REN + " AS ref ON s.id_solicitud = ref.id_solicitud " +
                                            "JOIN " + TBL_CROQUIS_IND_REN + " AS cro ON s.id_solicitud = cro.id_solicitud " +
                                            "WHERE s.id_originacion = ? AND s.estatus >= 2";
                                    row = db.rawQuery(sql, new String[]{String.valueOf(item.getId())});

                                    if (row.getCount() > 0) {
                                        row.moveToFirst();
                                        if (item.getSolicitudEstadoId() == 4) {//Actualiza solicitud de originacion que rechazo la adminitradora para correccion de datos

                                            cv = new ContentValues();
                                            cv.put("comentario_rechazo", "");
                                            db.update(TBL_CLIENTE_IND_REN, cv, "id_solicitud = ? AND id_cliente = ?", new String[]{row.getString(0), row.getString(2)});

                                            if (item.getEstatusCliente() != null && !(Boolean) item.getEstatusCliente()) {
                                                cv = new ContentValues();
                                                cv.put("estatus", 0);
                                                cv.put("fecha_guardado", "");
                                                cv.put("fecha_termino", "");
                                                db.update(TBL_SOLICITUDES_REN, cv, "id_solicitud = ?", new String[]{row.getString(0)});

                                                cv = new ContentValues();
                                                cv.put("estatus_completado", 0);
                                                cv.put("comentario_rechazo", Miscellaneous.validStr(item.getComentarioAdminCliente()));
                                                int i_update = db.update(TBL_CLIENTE_IND_REN, cv, "id_solicitud = ? AND id_cliente = ?", new String[]{row.getString(0), row.getString(2)});
                                                Log.e("Update", "Update: " + i_update);

                                                cv = new ContentValues();
                                                cv.put("estatus_completado", 0);
                                                db.update(TBL_CONYUGE_IND_REN, cv, "id_solicitud = ? AND id_conyuge = ?", new String[]{row.getString(0), row.getString(3)});

                                                cv = new ContentValues();
                                                cv.put("estatus_completado", 0);
                                                db.update(TBL_DOCUMENTOS_REN, cv, "id_solicitud = ? ", new String[]{row.getString(0)});
                                            }

                                            if (item.getEstatusNegocio() != null && !(Boolean) item.getEstatusNegocio()) {
                                                cv = new ContentValues();
                                                cv.put("estatus", 0);
                                                cv.put("fecha_guardado", "");
                                                cv.put("fecha_termino", "");
                                                db.update(TBL_SOLICITUDES_REN, cv, "id_solicitud = ?", new String[]{row.getString(0)});

                                                cv = new ContentValues();
                                                cv.put("estatus_completado", 0);
                                                cv.put("comentario_rechazo", Miscellaneous.validStr(item.getComentarioAdminNegocio()));
                                                db.update(TBL_NEGOCIO_IND_REN, cv, "id_solicitud = ? AND id_negocio = ?", new String[]{row.getString(0), row.getString(5)});
                                            }

                                            if (item.getEstatusAval() != null && !(Boolean) item.getEstatusAval()) {
                                                cv = new ContentValues();
                                                cv.put("estatus", 0);
                                                cv.put("fecha_guardado", "");
                                                cv.put("fecha_termino", "");
                                                db.update(TBL_SOLICITUDES_REN, cv, "id_solicitud = ?", new String[]{row.getString(0)});

                                                cv = new ContentValues();
                                                cv.put("estatus_completado", 0);
                                                cv.put("comentario_rechazo", Miscellaneous.validStr(item.getComentarioAdminAval()));
                                                db.update(TBL_AVAL_IND_REN, cv, "id_solicitud = ? AND id_aval = ?", new String[]{row.getString(0), row.getString(6)});
                                            }

                                            if (item.getEstatusReferencia() != null && !(Boolean) item.getEstatusReferencia()) {
                                                cv = new ContentValues();
                                                cv.put("estatus", 0);
                                                cv.put("fecha_guardado", "");
                                                cv.put("fecha_termino", "");
                                                db.update(TBL_SOLICITUDES_REN, cv, "id_solicitud = ?", new String[]{row.getString(0)});

                                                cv = new ContentValues();
                                                cv.put("estatus_completado", 0);
                                                cv.put("comentario_rechazo", Miscellaneous.validStr(item.getComentarioAdminReferencia()));
                                                db.update(TBL_REFERENCIA_IND_REN, cv, "id_solicitud = ? AND id_referencia = ?", new String[]{row.getString(0), row.getString(7)});
                                            }

                                            if (item.getEstatusCroquis() != null && !(Boolean) item.getEstatusCroquis()) {
                                                cv = new ContentValues();
                                                cv.put("estatus", 0);
                                                cv.put("fecha_guardado", "");
                                                cv.put("fecha_termino", "");
                                                db.update(TBL_SOLICITUDES_REN, cv, "id_solicitud = ?", new String[]{row.getString(0)});

                                                cv = new ContentValues();
                                                cv.put("estatus_completado", 0);
                                                cv.put("comentario_rechazo", Miscellaneous.validStr(item.getComentarioAdminCroquis()));
                                                db.update(TBL_CROQUIS_IND_REN, cv, "id_solicitud = ? AND id = ?", new String[]{row.getString(0), row.getString(8)});
                                            }
                                        }
                                        else if (item.getSolicitudEstadoId() == 2) { //Solicitud de renovacion rechazada
                                            Log.e("AQUI RECHAZI IND idsol", row.getString(0));
                                            Log.e("AQUI RECHAZI IND idcli", row.getString(8));


                                            cv = new ContentValues();
                                            cv.put("estatus", 5);
                                            db.update(TBL_SOLICITUDES_REN, cv, "id_solicitud = ?", new String[]{row.getString(0)});

                                            cv = new ContentValues();
                                            cv.put("comentario_rechazo", Miscellaneous.validStr("NO AUTORIZADO - " + item.getComentarioAdminCliente()));
                                            db.update(TBL_CLIENTE_IND_REN, cv, "id_solicitud = ? AND id_cliente = ?", new String[]{row.getString(0), row.getString(8)});
                                        }
                                    }
                                }
                                row.close();

                            }
                        }
                        break;
                }
            }

            @Override
            public void onFailure(Call<List<MSolicitudRechazoInd>> call, Throwable t) {

            }
        });

    }

    public void GetSolicitudesRechazadasGpo(Context ctx, boolean showDG){
        final DBhelper dBhelper = new DBhelper(ctx);
        final SQLiteDatabase db = dBhelper.getWritableDatabase();
        SessionManager session = new SessionManager(ctx);

        ManagerInterface api = new RetrofitClient().generalRF(CONTROLLER_SOLICITUDES, ctx).create(ManagerInterface.class);

        Call<List<MSolicitudRechazoGpo>> call = api.getSolicitudRechazoGpo("Bearer "+ session.getUser().get(7));

        call.enqueue(new Callback<List<MSolicitudRechazoGpo>>() {
            @Override
            public void onResponse(Call<List<MSolicitudRechazoGpo>> call, Response<List<MSolicitudRechazoGpo>> response) {
                Log.e("CodeGetRechazados", response.code()+" xD");
                Log.e("Mesage,Rechas",response.message());
                switch (response.code()){
                    case 200:
                        Log.e("AQUI RECHAZADO", response.body().toString());
                        List<MSolicitudRechazoGpo> solicitudes = response.body();
                        if (solicitudes.size() > 0){
                            for (MSolicitudRechazoGpo item : solicitudes){
                                ContentValues cv;
                                String sql = "";
                                Cursor row = null;
                                if (item.getTipoSolicitud() == 1) { //rechazo de solicitud de originacion
                                    Log.e("IDSOLCIITUD", "ID: "+item.getIdSolicitudIntegrante());
                                    //                 0                        1                   2              3                4              5               6                    7               8                  9
                                    sql = "SELECT i.id AS id_integrante, tel.id_telefonico, dom.id_domicilio, neg.id_negocio, con.id_conyuge, otr.id_otro, cro.id AS id_croquis, pol.id_politica, doc.id_documento, sol.id_solicitud FROM " + TBL_INTEGRANTES_GPO + " AS i " +
                                            "JOIN " + TBL_CREDITO_GPO + " AS cre ON i.id_credito = cre.id " +
                                            "JOIN " + TBL_SOLICITUDES + " AS sol ON cre.id_solicitud = sol.id_solicitud " +
                                            "JOIN " + TBL_TELEFONOS_INTEGRANTE + " AS tel ON i.id = tel.id_integrante " +
                                            "JOIN " + TBL_DOMICILIO_INTEGRANTE + " AS dom ON i.id = doc.id_integrante " +
                                            "JOIN " + TBL_NEGOCIO_INTEGRANTE + " AS neg ON i.id = neg.id_integrante " +
                                            "JOIN " + TBL_CONYUGE_INTEGRANTE + " AS con ON i.id = con.id_integrante " +
                                            "JOIN " + TBL_OTROS_DATOS_INTEGRANTE + " AS otr ON i.id = otr.id_integrante " +
                                            "JOIN " + TBL_CROQUIS_GPO + " AS cro ON i.id = cro.id_integrante " +
                                            "JOIN " + TBL_POLITICAS_PLD_INTEGRANTE + " AS pol ON i.id = pol.id_integrante " +
                                            "JOIN " + TBL_DOCUMENTOS_INTEGRANTE + " AS doc ON i.id = doc.id_integrante " +
                                            "WHERE i.id_solicitud_integrante = ? AND i.estatus_completado >= 2";

                                    row = db.rawQuery(sql, new String[]{String.valueOf(item.getIdSolicitudIntegrante())});

                                    if (row.getCount() > 0){ //Existe algun registro de originacion
                                        row.moveToFirst();
                                        if (item.getSolicitudEstadoIdIntegrante() == 4) { //Es rechazo parcial
                                            cv = new ContentValues();
                                            cv.put("cargo", item.getCargo());
                                            cv.put("estatus_completado", 0);
                                            cv.put("comentario_rechazo", item.getComentarioAdmin());
                                            db.update(TBL_INTEGRANTES_GPO, cv, "id = ?", new String[]{row.getString(0)});

                                            cv = new ContentValues();
                                            cv.put("estatus_completado", 0);
                                            db.update(TBL_TELEFONOS_INTEGRANTE, cv, "id_telefonico = ?", new String[]{row.getString(1)});

                                            cv = new ContentValues();
                                            cv.put("estatus_completado", 0);
                                            db.update(TBL_DOMICILIO_INTEGRANTE, cv, "id_domicilio = ?", new String[]{row.getString(2)});

                                            cv = new ContentValues();
                                            cv.put("estatus_completado", 0);
                                            db.update(TBL_NEGOCIO_INTEGRANTE, cv, "id_negocio = ?", new String[]{row.getString(3)});

                                            cv = new ContentValues();
                                            cv.put("estatus_completado", 0);
                                            db.update(TBL_CONYUGE_INTEGRANTE, cv, "id_conyuge = ?", new String[]{row.getString(4)});

                                            cv = new ContentValues();
                                            cv.put("estatus_completado", 0);
                                            if (item.getCargo() == 3)
                                                cv.put("casa_reunion", 1);
                                            db.update(TBL_OTROS_DATOS_INTEGRANTE, cv, "id_otro = ?", new String[]{row.getString(5)});

                                            cv = new ContentValues();
                                            cv.put("estatus_completado", 0);
                                            db.update(TBL_CROQUIS_GPO, cv, "id = ?", new String[]{row.getString(6)});

                                            cv = new ContentValues();
                                            cv.put("estatus_completado", 0);
                                            db.update(TBL_POLITICAS_PLD_INTEGRANTE, cv, "id_politica = ?", new String[]{row.getString(7)});

                                            cv = new ContentValues();
                                            cv.put("estatus_completado", 0);
                                            db.update(TBL_DOCUMENTOS_INTEGRANTE, cv, "id_documento = ?", new String[]{row.getString(8)});

                                            cv = new ContentValues();
                                            cv.put("estatus", 0);
                                            cv.put("id_originacion", String.valueOf(item.getId()));
                                            cv.put("fecha_termino", "");
                                            cv.put("fecha_envio", "");
                                            cv.put("fecha_guardado", "");
                                            db.update(TBL_SOLICITUDES, cv, "id_solicitud = ?", new String[]{row.getString(9)});
                                        }
                                        else{ //Es rechazo completo de la solicitud
                                            cv = new ContentValues();
                                            //cv.put("estatus_completado", 0);
                                            cv.put("comentario_rechazo", item.getComentarioAdmin());
                                            db.update(TBL_INTEGRANTES_GPO, cv, "id = ?", new String[]{row.getString(0)});

                                            cv = new ContentValues();

                                            if(item.getSolicitudEstadoIdSolicitud() == 2) cv.put("estatus", 5);

                                            cv.put("id_originacion", String.valueOf(item.getId()));
                                            //cv.put("fecha_termino", "");
                                            //cv.put("fecha_envio", "");
                                            //cv.put("fecha_guardado", "");
                                            db.update(TBL_SOLICITUDES, cv, "id_solicitud = ?", new String[]{row.getString(9)});
                                        }
                                    }
                                }
                                else{ //rechazo de solicitud grupal de renovacion
                                    //                 0                        1                   2              3                4              5               6                    7               8                  9
                                    sql = "SELECT " +
                                            "i.id AS id_integrante, " +
                                            "tel.id_telefonico, " +
                                            "dom.id_domicilio, " +
                                            "neg.id_negocio, " +
                                            "con.id_conyuge, " +
                                            "otr.id_otro, " +
                                            "cro.id AS id_croquis, " +
                                            "pol.id_politica, " +
                                            "doc.id_documento, " +
                                            "sol.id_solicitud " +
                                        "FROM " + TBL_INTEGRANTES_GPO_REN + " AS i " +
                                        "JOIN " + TBL_CREDITO_GPO_REN + " AS cre ON i.id_credito = cre.id " +
                                        "JOIN " + TBL_SOLICITUDES_REN + " AS sol ON cre.id_solicitud = sol.id_solicitud " +
                                        "JOIN " + TBL_TELEFONOS_INTEGRANTE_REN + " AS tel ON i.id = tel.id_integrante " +
                                        "JOIN " + TBL_DOMICILIO_INTEGRANTE_REN + " AS dom ON i.id = doc.id_integrante " +
                                        "JOIN " + TBL_NEGOCIO_INTEGRANTE_REN + " AS neg ON i.id = neg.id_integrante " +
                                        "JOIN " + TBL_CONYUGE_INTEGRANTE_REN + " AS con ON i.id = con.id_integrante " +
                                        "JOIN " + TBL_OTROS_DATOS_INTEGRANTE_REN + " AS otr ON i.id = otr.id_integrante " +
                                        "JOIN " + TBL_CROQUIS_GPO_REN + " AS cro ON i.id = cro.id_integrante " +
                                        "JOIN " + TBL_POLITICAS_PLD_INTEGRANTE_REN + " AS pol ON i.id = pol.id_integrante " +
                                        "JOIN " + TBL_DOCUMENTOS_INTEGRANTE_REN + " AS doc ON i.id = doc.id_integrante " +
                                        "WHERE i.id_solicitud_integrante = ? AND i.estatus_completado >= 2";

                                    row = db.rawQuery(sql, new String[]{String.valueOf(item.getIdSolicitudIntegrante())});

                                    if (row.getCount() > 0){ //Existe algun registro de renovacion
                                        row.moveToFirst();
                                        if (item.getSolicitudEstadoIdIntegrante() == 4) { //Es rechazo parcial
                                            cv = new ContentValues();
                                            cv.put("estatus_completado", 0);
                                            cv.put("comentario_rechazo", item.getComentarioAdmin());
                                            db.update(TBL_INTEGRANTES_GPO_REN, cv, "id = ?", new String[]{row.getString(0)});

                                            cv = new ContentValues();
                                            cv.put("estatus_completado", 0);
                                            db.update(TBL_TELEFONOS_INTEGRANTE_REN, cv, "id_telefonico = ?", new String[]{row.getString(1)});

                                            cv = new ContentValues();
                                            cv.put("estatus_completado", 0);
                                            db.update(TBL_DOMICILIO_INTEGRANTE_REN, cv, "id_domicilio = ?", new String[]{row.getString(2)});

                                            cv = new ContentValues();
                                            cv.put("estatus_completado", 0);
                                            db.update(TBL_NEGOCIO_INTEGRANTE_REN, cv, "id_negocio = ?", new String[]{row.getString(3)});

                                            cv = new ContentValues();
                                            cv.put("estatus_completado", 0);
                                            db.update(TBL_CONYUGE_INTEGRANTE_REN, cv, "id_conyuge = ?", new String[]{row.getString(4)});

                                            cv = new ContentValues();
                                            cv.put("estatus_completado", 0);
                                            db.update(TBL_OTROS_DATOS_INTEGRANTE_REN, cv, "id_otro = ?", new String[]{row.getString(5)});

                                            cv = new ContentValues();
                                            cv.put("estatus_completado", 0);
                                            db.update(TBL_CROQUIS_GPO_REN, cv, "id = ?", new String[]{row.getString(6)});

                                            cv = new ContentValues();
                                            cv.put("estatus_completado", 0);
                                            db.update(TBL_POLITICAS_PLD_INTEGRANTE_REN, cv, "id_politica = ?", new String[]{row.getString(7)});

                                            cv = new ContentValues();
                                            cv.put("estatus_completado", 0);
                                            db.update(TBL_DOCUMENTOS_INTEGRANTE_REN, cv, "id_documento = ?", new String[]{row.getString(8)});

                                            cv = new ContentValues();
                                            cv.put("estatus", 0);
                                            cv.put("id_originacion", String.valueOf(item.getId()));
                                            cv.put("fecha_termino", "");
                                            cv.put("fecha_envio", "");
                                            cv.put("fecha_guardado", "");
                                            db.update(TBL_SOLICITUDES_REN, cv, "id_solicitud = ?", new String[]{row.getString(9)});
                                        }
                                        else{//Es rechazo de solcitud completo
                                            cv = new ContentValues();
                                            //cv.put("estatus_completado", 0);
                                            cv.put("comentario_rechazo", item.getComentarioAdmin());
                                            db.update(TBL_INTEGRANTES_GPO_REN, cv, "id = ?", new String[]{row.getString(0)});

                                            cv = new ContentValues();//cv.put("estatus", 0);

                                            if(item.getSolicitudEstadoIdSolicitud() == 2) cv.put("estatus", 5);

                                            cv.put("id_originacion", String.valueOf(item.getId()));
                                            //cv.put("fecha_termino", "");
                                            //cv.put("fecha_envio", "");
                                            //cv.put("fecha_guardado", "");
                                            db.update(TBL_SOLICITUDES_REN, cv, "id_solicitud = ?", new String[]{row.getString(9)});
                                        }

                                    }

                                }
                                row.close();

                            }
                        }
                        break;
                    default:
                        try {
                            Log.e("ERROR " + response.code(), response.errorBody().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Log.e("ERROR " + response.code(), response.message());
                        break;
                }
            }

            @Override
            public void onFailure(Call<List<MSolicitudRechazoGpo>> call, Throwable t) {
                Log.e("ERROR ", t.getMessage());
            }
        });

    }

    public void GetSolicitudesEstatusInd(Context ctx, boolean showDG){
        SessionManager session = new SessionManager(ctx);
        SolicitudIndService solicitudIndService = new RetrofitClient().newInstance(ctx).create(SolicitudIndService.class);
        Call<List<SolicitudDetalleEstatusInd>> call = solicitudIndService.showEstatusSolicitudes("Bearer " + session.getUser().get(7));

        call.enqueue(new Callback<List<SolicitudDetalleEstatusInd>>() {
            @Override
            public void onResponse(Call<List<SolicitudDetalleEstatusInd>> call, Response<List<SolicitudDetalleEstatusInd>> response) {
                switch (response.code()){
                    case 200:
                        List<SolicitudDetalleEstatusInd> solicitudes = response.body();

                        for(SolicitudDetalleEstatusInd se : solicitudes)
                        {
                            if(se.getTipoSolicitud() == 1)
                            {
                                Log.e("AQUI ORIGINACION IND", String.valueOf(se.getSolicitudEstadoId()));
                                ClienteDao clienteDao = new ClienteDao(ctx);
                                SolicitudDao solicitudDao = new SolicitudDao(ctx);

                                Cliente cliente = null;
                                Solicitud solicitud = solicitudDao.findByIdOriginacion(se.getId());

                                if(solicitud != null) cliente = clienteDao.findByIdSolicitud(solicitud.getIdSolicitud());

                                if(cliente != null)
                                {
                                    String comentario = "";

                                    Log.e("AQUI CLIENTE ID", String.valueOf(cliente.getIdCliente()));
                                    Log.e("AQUI SOLICITUD ESTADO", String.valueOf(se.getSolicitudEstadoId()));

                                    if(se.getSolicitudEstadoId() == 1)
                                    {
                                        solicitud.setEstatus(2);
                                        comentario = "EN REVISIÓN";
                                    }
                                    else if(se.getSolicitudEstadoId() == 3)
                                    {
                                        solicitud.setEstatus(3);
                                        comentario = "VALIDADO";
                                    }
                                    else
                                    {
                                        //solicitud.setEstatus(3);
                                        //comentario = cliente.getComentarioRechazo();
                                    }

                                    if(se.getSolicitudEstadoId() == 2) solicitud.setEstatus(5);


                                    Log.e("AQUI comentario", comentario);

                                    cliente.setComentarioRechazo(comentario);
                                    clienteDao.updateEstatus(cliente);

                                    solicitudDao.updateEstatus(solicitud);
                                }
                            }
                            else
                            {
                                ClienteRenDao clienteDao = new ClienteRenDao(ctx);
                                SolicitudRenDao solicitudDao = new SolicitudRenDao(ctx);

                                ClienteRen cliente = null;
                                SolicitudRen solicitud = solicitudDao.findByIdOriginacion(se.getId());

                                if(solicitud != null) cliente = clienteDao.findByIdSolicitud(solicitud.getIdSolicitud());

                                if(cliente != null)
                                {
                                    String comentario = "";

                                    if(se.getSolicitudEstadoId() == 1)
                                    {
                                        comentario = "EN REVISIÓN";
                                        solicitud.setEstatus(2);
                                    }
                                    else if(se.getSolicitudEstadoId() == 3)
                                    {
                                        comentario = "VALIDADO";
                                        solicitud.setEstatus(3);
                                    }
                                    else
                                    {
                                        //comentario = cliente.getComentarioRechazo();
                                        //solicitud.setEstatus(3);
                                    }

                                    if(se.getSolicitudEstadoId() == 2) solicitud.setEstatus(5);

                                    cliente.setComentarioRechazo(comentario);
                                    clienteDao.updateEstatus(cliente);

                                    solicitudDao.updateEstatus(solicitud);
                                }
                            }
                        }
                        break;
                    default:
                        Log.e("AQUI ", response.message());
                        break;
                }
            }

            @Override
            public void onFailure(Call<List<SolicitudDetalleEstatusInd>> call, Throwable t) {
                Log.e("Error", "failAGF" + t.getMessage());
            }
        });
    }

    public void GetSolicitudesEstatusGpo(Context ctx, boolean showDG){
        SessionManager session = new SessionManager(ctx);
        SolicitudGpoService solicitudGpoService = new RetrofitClient().newInstance(ctx).create(SolicitudGpoService.class);
        Call<List<SolicitudDetalleEstatusGpo>> call = solicitudGpoService.showEstatusSolicitudes("Bearer " + session.getUser().get(7));

        call.enqueue(new Callback<List<SolicitudDetalleEstatusGpo>>() {
            @Override
            public void onResponse(Call<List<SolicitudDetalleEstatusGpo>> call, Response<List<SolicitudDetalleEstatusGpo>> response) {
                switch (response.code()){
                    case 200:
                        List<SolicitudDetalleEstatusGpo> solicitudes = response.body();

                        for(SolicitudDetalleEstatusGpo se : solicitudes)
                        {
                            if(se.getTipoSolicitud() == 1)
                            {
                                CreditoGpoDao creditoDao = new CreditoGpoDao(ctx);
                                IntegranteGpoDao integranteDao = new IntegranteGpoDao(ctx);
                                SolicitudDao solicitudDao = new SolicitudDao(ctx);

                                CreditoGpo credito = null;
                                IntegranteGpo integrante = null;
                                Solicitud solicitud = null;

                                integrante = integranteDao.findByIdSolicitudIntegrante(se.getIdSolicitudIntegrante());

                                if(integrante != null) credito = creditoDao.findById(integrante.getIdCredito());
                                if(credito != null) solicitud = solicitudDao.findByIdSolicitud(credito.getIdSolicitud());

                                if(solicitud != null)
                                {
                                    String comentario = "";

                                    if(se.getSolicitudEstadoIdIntegrante() == 1)
                                    {
                                        comentario = "EN REVISIÓN";
                                    }
                                    else if (se.getSolicitudEstadoIdIntegrante() == 3)
                                    {
                                        comentario = "VALIDADO";
                                    }
                                    else
                                    {
                                        //comentario = se.getComentarioAdmin();
                                    }

                                    if(se.getSolicitudEstadoIdSolicitud() == 2) solicitud.setEstatus(5);
                                    if(se.getSolicitudEstadoIdSolicitud() == 3) solicitud.setEstatus(3);

                                    Log.e("AQUI ORI", String.valueOf(se.getId()));

                                    integrante.setComentarioRechazo(comentario);
                                    integranteDao.updateEstatus(integrante);

                                    solicitud.setIdOriginacion(se.getId());
                                    solicitudDao.updateEstatus(solicitud);
                                }
                            }
                            else
                            {
                                CreditoGpoRenDao creditoDao = new CreditoGpoRenDao(ctx);
                                IntegranteGpoRenDao integranteDao = new IntegranteGpoRenDao(ctx);
                                SolicitudRenDao solicitudDao = new SolicitudRenDao(ctx);

                                CreditoGpoRen credito = null;
                                IntegranteGpoRen integrante = null;
                                SolicitudRen solicitud = null;

                                integrante = integranteDao.findByIdSolicitudIntegrante(se.getIdSolicitudIntegrante());

                                if(integrante != null) credito = creditoDao.findById(integrante.getIdCredito());
                                if(credito != null) solicitud = solicitudDao.findByIdSolicitud(credito.getIdSolicitud());

                                if(solicitud != null)
                                {
                                    String comentario = "";

                                    if(se.getSolicitudEstadoIdIntegrante() == 1)
                                    {
                                        comentario = "EN REVISIÓN";
                                    }
                                    else if (se.getSolicitudEstadoIdIntegrante() == 3)
                                    {
                                        comentario = "VALIDADO";
                                    }
                                    else
                                    {
                                        //comentario = se.getComentarioAdmin();
                                    }


                                    Log.e("AQUI REN", String.valueOf(se.getId()));

                                    if(se.getSolicitudEstadoIdSolicitud() == 2) solicitud.setEstatus(5);
                                    if(se.getSolicitudEstadoIdSolicitud() == 3) solicitud.setEstatus(3);

                                    integrante.setComentarioRechazo(comentario);
                                    integranteDao.updateEstatus(integrante);

                                    solicitud.setIdOriginacion(se.getId());
                                    solicitudDao.updateEstatus(solicitud);
                                }
                            }
                        }
                        break;
                    default:
                        Log.e("AQUI ", response.message());
                        break;
                }
            }

            @Override
            public void onFailure(Call<List<SolicitudDetalleEstatusGpo>> call, Throwable t) {
                Log.e("Error", "failAGF" + t.getMessage());
            }
        });
    }

    public void GetPrestamosAutorizados(final Context ctx, boolean showDG){
        final DBhelper dBhelper = new DBhelper(ctx);
        final SQLiteDatabase db = dBhelper.getWritableDatabase();
        SessionManager session = new SessionManager(ctx);

        ManagerInterface api = new RetrofitClient().generalRF(CONTROLLER_MOVIL, ctx).create(ManagerInterface.class);

        Call<MSolicitudAutorizar> call = api.getSolicitudesAutorizadas(3L,
                                                                       "Bearer "+ session.getUser().get(7));

        call.enqueue(new Callback<MSolicitudAutorizar>() {
            @Override
            public void onResponse(Call<MSolicitudAutorizar> call, Response<MSolicitudAutorizar> response) {
                Log.e("CodeSolicitude", "Autorizar: "+response.code());
                switch (response.code()){
                    case 200:
                        MSolicitudAutorizar mSolicitud = response.body();
                        for (MSolicitudAutorizar.SolicitudIndividual item : mSolicitud.getSolicitudesIndividuales()){
                            String sql = "SELECT * FROM "+TBL_SOLICITUDES_AUTO + " WHERE id_originacion = ?";
                            Cursor row = db.rawQuery(sql, new String[]{String.valueOf(item.getPrestamo().getSolicitudId())});
                            if (row.getCount() == 0) {
                                MSolicitudAutorizar.Prestamo pre = item.getPrestamo();
                                MSolicitudAutorizar.Cliente cli = item.getCliente();
                                MSolicitudAutorizar.Conyuge con = item.getConyuge();
                                MSolicitudAutorizar.Economicos eco = item.getEconomicos();
                                MSolicitudAutorizar.Negocio neg = item.getNegocio();
                                MSolicitudAutorizar.Aval ava = item.getAval();
                                MSolicitudAutorizar.Referencia ref = item.getReferencia();
                                MSolicitudAutorizar.Croquis cro = item.getCroquis();
                                MSolicitudAutorizar.Politicas pol = item.getPoliticas();


                                long id = 0;
                                long id_cliente = 0;
                                long id_direccion_cli = 0;
                                long id_direccion_cony = 0;
                                long id_direccion_neg = 0;
                                long id_direccion_aval = 0;
                                long id_direccion_ref = 0;

                                String nombre = (Miscellaneous.validStr(item.getCliente().getNombre()) + " " +
                                        Miscellaneous.validStr(item.getCliente().getPaterno()) + " " +
                                        Miscellaneous.validStr(item.getCliente().getMaterno())).trim().toUpperCase();

                                HashMap<Integer, String> params = new HashMap<>();

                                params.put(0, "1");                                      //TIPO SOLICITUD
                                params.put(1, pre.getAsesor());                          //ASESOR
                                params.put(2, ((pre.getTipoSolicitud() == 1) ? "ORIGINACION" : "RENOVACION")); //TIPO SOLICITUD
                                params.put(3, String.valueOf(pre.getSolicitudId()));    //SOLICITUD ID
                                params.put(4, nombre);                                  //NOMBRE CLIENTE
                                params.put(5, "");                                      //FECHA ENVIO
                                params.put(6, "0");                                     //ESTATUS

                                Log.e("PArams", params.toString());
                                id = dBhelper.saveSolicitudesAuto(db, params);

                                //Inserta registro de datos del credito
                                params = new HashMap<>();
                                params.put(0, String.valueOf(id));                                      //ID SOLICITUD
                                params.put(1, Miscellaneous.GetPlazo(pre.getPlazo()));                  //PLAZO
                                params.put(2, Miscellaneous.GetPeriodicidad(pre.getPeriodicida()));     //PERIODICIDAD
                                params.put(3, pre.getFechaDesembolso());                                //FECHA DESEMBOLSO
                                params.put(4, "");                                                      //DIA DESEMBOLSO
                                params.put(5, pre.getHoraVisita());                                     //HORA VISITA
                                params.put(6, String.valueOf(pre.getMonto()));                          //MONTO PRESTAMO
                                params.put(7, "");                                                      //CICLO
                                params.put(8, "");                                                      //CREDITO ANTERIOR
                                params.put(9, "");                                                       //COMPORTAMIENTO PAGO
                                params.put(10, "");                                                     //NUM CLIENTE
                                params.put(11, Miscellaneous.validStr(pre.getObservaciones()));         //OBSERVACIONES
                                params.put(12, pre.getPrestamoDestino());                                                      //DESTINO
                                params.put(13, pre.getClasificacion());//CLASIFICACION RIESGO
                                params.put(14, "0");                                                     //ESTATUS COMPLETO
                                params.put(15, "");                                                     //MONTO AUTORIZADO

                                dBhelper.saveDatosCreditoAuto(db, params);

                                //Inserta registro de direccion del cliente
                                params = new HashMap<>();
                                params.put(0, "CLIENTE");                                                              //TIPO DIRECCION
                                params.put(1, Miscellaneous.validStr(cli.getLatitud()));                 //LATITUD
                                params.put(2, Miscellaneous.validStr(cli.getLongitud()));                //LONGITUD
                                params.put(3, Miscellaneous.validStr(cli.getCalle()));                   //CALLE
                                params.put(4, Miscellaneous.validStr(cli.getNoExterior()));              //NO EXTERIOR
                                params.put(5, Miscellaneous.validStr(cli.getNoInterior()));              //NO INTERIOR
                                params.put(6, Miscellaneous.validStr(cli.getNoManzana()));               //MANZANA
                                params.put(7, Miscellaneous.validStr(cli.getNoLote()));                  //LOTE
                                params.put(8, String.valueOf(Miscellaneous.validInt(cli.getCodigoPostal())));//CP
                                params.put(9, Miscellaneous.GetColonia(ctx, Miscellaneous.validInt(cli.getColoniaId())));//COLONIA
                                params.put(10, Miscellaneous.validStr(cli.getCiudad()));                 //CIUDAD
                                params.put(11, Miscellaneous.GetLocalidad(ctx, Miscellaneous.validInt(cli.getLocalidadId())));//LOCALIDAD
                                params.put(12, Miscellaneous.GetMunicipio(ctx, Miscellaneous.validInt(item.getCliente().getMunicipioId())));//MUNICIPIO
                                params.put(13, Miscellaneous.GetEstado(ctx, Miscellaneous.validInt(item.getCliente().getEstadoId())));//ESTADO

                                id_direccion_cli = dBhelper.saveDirecciones(db, params, 3);

                                //Inserta registro de datos del cliente

                                params = new HashMap<>();
                                params.put(0, String.valueOf(id));                                      //ID SOLICITUD
                                params.put(1, Miscellaneous.validStr(cli.getNombre().trim().toUpperCase()));      //NOMBRE
                                params.put(2, Miscellaneous.validStr(cli.getPaterno().trim().toUpperCase()));     //PATERNO
                                params.put(3, Miscellaneous.validStr(cli.getMaterno().trim().toUpperCase()));     //MATERNO
                                params.put(4, Miscellaneous.validStr(cli.getFechaNacimiento()));                  //FECHA NACIMIENTO
                                params.put(5, Miscellaneous.GetEdad(cli.getFechaNacimiento()));//EDAD
                                params.put(6, String.valueOf(cli.getGenero()));           //GENERO
                                params.put(7, Miscellaneous.GetEstado(ctx, Miscellaneous.validInt(cli.getEstadoNacimiento())));//ESTADO NACIMIENTO
                                params.put(8, Miscellaneous.validStr(Miscellaneous.validStr(cli.getRfc())));     //RFC
                                params.put(9, Miscellaneous.validStr(Miscellaneous.validStr(cli.getCurp())));     //CURP
                                params.put(10, "");                                                     //CURP DIGITO VERI
                                params.put(11, Miscellaneous.GetOcupacion(ctx, Miscellaneous.validInt(cli.getOcupacionId()))); //OCUPACION
                                params.put(12, Miscellaneous.GetSector(ctx, Miscellaneous.validInt(cli.getOcupacionId())));   //ACTIVIDAD ECONOMICA
                                params.put(13, Miscellaneous.GetTipoIdentificacion(ctx, cli.getIdentificacionTipoId()));                                                     //TIPO IDENTIFICACION
                                params.put(14, Miscellaneous.validStr(cli.getNoIdentificacion()));//NUM IDENTIFICACION
                                params.put(15, Miscellaneous.GetEstudio(ctx, cli.getEstudioNivelId()));   //NIVEL ESTUDIO
                                params.put(16, Miscellaneous.GetEstadoCivil(ctx, cli.getEstadoCivilId()));//ESTATUS CIVIL
                                params.put(17, String.valueOf(cli.getRegimenBienId()));                   //BIENES
                                params.put(18, Miscellaneous.GetViviendaTipo(ctx, cli.getViviendaTipoId()));  //TIPO VIVIENDA
                                params.put(19, "");                                                     //PARENTESCO
                                params.put(20, "");                                                     //OTRO TIPO VIVIENDA
                                params.put(21, String.valueOf(id_direccion_cli));                           //DIRECCION ID
                                params.put(22, Miscellaneous.validStr(cli.getTelCasa())); //TEL CASA
                                params.put(23, Miscellaneous.validStr(cli.getTelCelular())); //TEL CELULAR
                                params.put(24, Miscellaneous.validStr(cli.getTelMensaje())); //TEL MENSAJES
                                params.put(25, Miscellaneous.validStr(cli.getTelTrabajo())); //TEL TRABAJO
                                if (!Miscellaneous.validStr(cli.getTiempoVivirSitio()).isEmpty())
                                    params.put(26, cli.getTiempoVivirSitio());
                                else
                                    params.put(26, "0");                                                //TIEMPO VIVIR SITIO
                                params.put(27, Miscellaneous.validStr(cli.getDependientesEconomico()));  //DEPENDIENTES
                                params.put(28, Miscellaneous.GetMedioContacto(ctx, cli.getMedioContactoId()));                                                     //MEDIO CONTACTO
                                params.put(29, Miscellaneous.validStr(cli.getEstadoCuenta()));//ESTADO CUENTA
                                params.put(30, Miscellaneous.validStr(cli.getEmail()));   //EMAIL
                                params.put(31, "");                                                     //FOTO FACHADA
                                params.put(32, Miscellaneous.validStr(cli.getReferencia()));//REF DOMICILIARIA
                                params.put(33, "");                                                     //FIRMA
                                params.put(34, "0");                                                    //ESTATUS RECHAZO
                                params.put(35, "");                                                     //COMENTARIO RECHAZO
                                params.put(36, "1");                                                    //ESTATUS COMPLETO

                                id_cliente = dBhelper.saveDatosPersonales(db, params, 3);

                                if (cli.getEstadoCivilId() == 2 || cli.getEstadoCivilId() == 5) {
                                    Log.e("Conyuge", "Registra conyuge");
                                    //Inserta registro de direccion del cliente
                                    params = new HashMap<>();
                                    params.put(0, "CONYUGE");                                               //TIPO DIRECCION
                                    params.put(1, Miscellaneous.validStr(con.getLatitud()));  //LATITUD
                                    params.put(2, Miscellaneous.validStr(con.getLongitud())); //LONGITUD
                                    params.put(3, Miscellaneous.validStr(con.getCalle()));    //CALLE
                                    params.put(4, Miscellaneous.validStr(con.getNoExterior()));//NO EXTERIOR
                                    params.put(5, Miscellaneous.validStr(con.getNoInterior()));//NO INTERIOR
                                    params.put(6, Miscellaneous.validStr(con.getNoManzana()));//MANZANA
                                    params.put(7, Miscellaneous.validStr(con.getNoLote()));   //LOTE
                                    params.put(8, (con.getCodigoPostal() == 0) ? "" : String.valueOf(con.getCodigoPostal())); //CP
                                    params.put(9, Miscellaneous.GetColonia(ctx, Miscellaneous.validInt(con.getColoniaId()))); //COLONIA
                                    params.put(10, Miscellaneous.validStr(con.getCiudad()));  //CIUDAD
                                    params.put(11, Miscellaneous.GetLocalidad(ctx, Miscellaneous.validInt(con.getLocalidadId()))); //LOCALIDAD
                                    params.put(12, Miscellaneous.GetMunicipio(ctx, Miscellaneous.validInt(con.getMunicipioId()))); //MUNICIPIO
                                    params.put(13, Miscellaneous.GetEstado(ctx, Miscellaneous.validInt(con.getEstadoId()))); //ESTADO

                                    id_direccion_cony = dBhelper.saveDirecciones(db, params, 3);

                                    //Inserta registro de datos conyuge
                                    params = new HashMap<>();
                                    params.put(0, String.valueOf(id));                      //ID SOLICITUD
                                    params.put(1, Miscellaneous.validStr(con.getNombre()).toUpperCase()); //NOMBRE
                                    params.put(2, Miscellaneous.validStr(con.getPaterno()).toUpperCase());//PATERNO
                                    params.put(3, Miscellaneous.validStr(con.getMaterno()).toUpperCase());//MATERNO
                                    params.put(4, Miscellaneous.validStr(con.getNacionalidad()));         //NACIONALIDAD
                                    params.put(5, Miscellaneous.GetOcupacion(ctx, Miscellaneous.validInt(con.getOcupacionId()))); //OCUPACION
                                    params.put(6, String.valueOf(id_direccion_cony));                                   //DIRECCION ID
                                    params.put(7, String.valueOf(con.getIngresoMensual()));               //ING MENSUAL
                                    params.put(8, String.valueOf(con.getGastoMensual()));                 //GASTO MENSUAL
                                    params.put(9, Miscellaneous.validStr(con.getTelCasa()));              //TEL CASA
                                    params.put(10, Miscellaneous.validStr(con.getTelCelular()));          //TEL CELULAR
                                    params.put(11, "0");                                                                //ESTATUS COMPLETADO

                                    dBhelper.saveDatosConyuge(db, params, 3);
                                }

                                if (pre.getMonto() > 29000) {
                                    //Inserta registro de datos economicos
                                    params = new HashMap<>();
                                    params.put(0, String.valueOf(id));                         //ID SOLICITUD
                                    params.put(1, eco.getPropiedades());                       //PROPIEDADES
                                    params.put(2, String.valueOf(eco.getValorAproximado()));   //VALOR APROXIMADO
                                    params.put(3, eco.getUbicacion());                         //UBICACION
                                    params.put(4, String.valueOf(eco.getIngreso()));           //INGRESO
                                    params.put(5, "0");                                        //ESTATUS COMPLETADO

                                    dBhelper.saveDatosEconomicos(db, params, 3);
                                }

                                //Inserta registro de direccion del negocio
                                params = new HashMap<>();
                                params.put(0, "NEGOCIO");                                               //TIPO DIRECCION
                                params.put(1, Miscellaneous.validStr(neg.getLatitud()));  //LATITUD
                                params.put(2, Miscellaneous.validStr(neg.getLongitud())); //LONGITUD
                                params.put(3, Miscellaneous.validStr(neg.getCalle()).toUpperCase());    //CALLE
                                params.put(4, Miscellaneous.validStr(neg.getNoExterior())); //NO EXTERIOR
                                params.put(5, Miscellaneous.validStr(neg.getNoInterior())); //NO INTERIOR
                                params.put(6, Miscellaneous.validStr(neg.getNoManzana()));  //MANZANA
                                params.put(7, Miscellaneous.validStr(neg.getNoLote()));     //LOTE
                                params.put(8, (neg.getCodigoPostal() == 0) ? "" : String.valueOf(neg.getCodigoPostal()));                                                     //CP
                                params.put(9, Miscellaneous.GetColonia(ctx, neg.getColoniaId())); //COLONIA
                                params.put(10, item.getNegocio().getCiudad());                           //CIUDAD
                                params.put(11, Miscellaneous.GetLocalidad(ctx, neg.getLocalidadId()));  //LOCALIDAD
                                params.put(12, Miscellaneous.GetMunicipio(ctx, neg.getMunicipioId()));  //MUNICIPIO
                                params.put(13, Miscellaneous.GetEstado(ctx, neg.getEstadoId())); //ESTADO

                                id_direccion_neg = dBhelper.saveDirecciones(db, params, 3);

                                //Inserta registro de negocio
                                params = new HashMap<>();
                                params.put(0, String.valueOf(id));                  //ID SOLICITUD
                                params.put(1, Miscellaneous.validStr(neg.getNombre()));   //NOMBRE
                                params.put(2, String.valueOf(id_direccion_neg));        //DIRECCION ID
                                params.put(3, Miscellaneous.GetOcupacion(ctx, neg.getOcupacionId())); //OCUPACION
                                params.put(4, Miscellaneous.GetSector(ctx, neg.getOcupacionId()));    //ACTIVIDAD ECONOMICA
                                params.put(5, Miscellaneous.GetDestinoCredito(ctx, neg.getDestinoCreditoId())); //DESTINO CREDITO
                                params.put(6, Miscellaneous.validStr(neg.getOtroDestinoCredito())); //OTRO DESTINO
                                params.put(7, String.valueOf(neg.getAntiguedad()));       //ANTIGUEDAD
                                params.put(8, String.valueOf(neg.getIngresoMensual()));   //ING MENSUAL
                                params.put(9, String.valueOf(neg.getIngresosOtros()));    //ING OTROS
                                params.put(10, String.valueOf(neg.getGastoMensual()));    //GASTO SEMANAL
                                params.put(11, String.valueOf(neg.getGastoAgua()));       //GASTO AGUA
                                params.put(12, String.valueOf(neg.getGastoLuz()));        //GASTO LUZ
                                params.put(13, String.valueOf(neg.getGastoTelefono()));   //GASTO TELEFONO
                                params.put(14, String.valueOf(neg.getGastoRenta()));      //GASTO RENTA
                                params.put(15, String.valueOf(neg.getGastoOtros()));      //GASTO OTROS
                                params.put(16, String.valueOf(neg.getCapacidadPago()));   //CAPACIDAD PAGO
                                params.put(17, Miscellaneous.GetMediosPagoSoli(ctx, neg.getMediosPagosIds())); //MEDIO PAGO
                                params.put(18, neg.getOtroMedioPago());                   //OTRO MEDIO PAGO
                                params.put(19, String.valueOf(neg.getMontoMaximo())); //MONTO MAXIMO
                                int numOper = 30 / pre.getPeriodicida();
                                params.put(20, String.valueOf(numOper));  //NUM OPERACION MENSUALES
                                params.put(21, String.valueOf(neg.getNumOperacionesMensualesEfectivo())); //NUM OPERACION EFECTIVO
                                params.put(22, neg.getDiasVenta());                 //DIAS VENTA
                                params.put(23, "");                                  //FOTO FACHADA
                                params.put(24, neg.getReferencia());                                  //REF DOMICILIARIA
                                params.put(25, "1");                                 //ESTATUS COMPLETADO
                                params.put(26, "");                                  //COMENTARIO RECHAZO

                                dBhelper.saveDatosNegocio(db, params, 3);

                                //Inserta registro de direccion del aval
                                params = new HashMap<>();
                                params.put(0, "AVAL");                                                 //TIPO DIRECCION
                                params.put(1, Miscellaneous.validStr(ava.getLatitud()));    //LATITUD
                                params.put(2, Miscellaneous.validStr(ava.getLongitud()));   //LONGITUD
                                params.put(3, Miscellaneous.validStr(ava.getCalle()));      //CALLE
                                params.put(4, Miscellaneous.validStr(ava.getNoExterior())); //NO EXTERIOR
                                params.put(5, Miscellaneous.validStr(ava.getNoInterior())); //NO INTERIOR
                                params.put(6, Miscellaneous.validStr(ava.getNoManzana()));  //MANZANA
                                params.put(7, Miscellaneous.validStr(ava.getNoLote()));     //LOTE
                                params.put(8, (ava.getCodigoPostal() == 0) ? "" : String.valueOf(ava.getCodigoPostal())); //CP
                                params.put(9, Miscellaneous.GetColonia(ctx, ava.getColoniaId())); //COLONIA
                                params.put(10, Miscellaneous.validStr(item.getAval().getCiudad()));     //CIUDAD
                                params.put(11, Miscellaneous.GetLocalidad(ctx, ava.getLocalidadId())); //LOCALIDAD
                                params.put(12, Miscellaneous.GetMunicipio(ctx, ava.getMunicipioId())); //MUNICIPIO
                                params.put(13, Miscellaneous.GetEstado(ctx, ava.getEstadoId())); //ESTADO

                                id_direccion_aval = dBhelper.saveDirecciones(db, params, 3);

                                //Inserta registro del aval
                                params = new HashMap<>();
                                params.put(0, String.valueOf(id));                  //ID SOLICITUD
                                params.put(1, Miscellaneous.validStr(ava.getNombre()).toUpperCase());          //NOMBRE
                                params.put(2, Miscellaneous.validStr(ava.getPaterno()).toUpperCase());         //PATERNO
                                params.put(3, Miscellaneous.validStr(ava.getMaterno()).toUpperCase());         //MATERNO
                                params.put(4, Miscellaneous.validStr(ava.getFechaNacimiento()));               //FECHA NACIMIENTO
                                params.put(5, String.valueOf(ava.getEdad()));                                  //EDAD
                                params.put(6, String.valueOf(Miscellaneous.validInt(ava.getGenero())));        //GENERO
                                params.put(7, Miscellaneous.GetEstado(ctx, Miscellaneous.validInt(ava.getEstadoNacimientoId())));//ESTADO NACIMIENTO
                                params.put(8, Miscellaneous.validStr(ava.getRfc()));                           //RFC
                                params.put(9, Miscellaneous.validStr(ava.getCurp()));                          //CURP
                                params.put(10, "");                                                                       //CURP DIGITO
                                params.put(11, Miscellaneous.GetParentesco(ctx, Miscellaneous.validInt(ava.getParentescoSolicitanteId()))); //PARENTESCO CLIENTE
                                params.put(12, Miscellaneous.GetTipoIdentificacion(ctx, Miscellaneous.validInt(ava.getIdentificacionTipoId()))); //TIPO IDENTIFICACION
                                params.put(13, Miscellaneous.validStr(ava.getNoIdentificacion()));             //NUM IDENTIFICACION
                                params.put(14, Miscellaneous.GetOcupacion(ctx, Miscellaneous.validInt(ava.getOcupacionId())));         //OCUPACION
                                params.put(15, Miscellaneous.GetSector(ctx, Miscellaneous.validInt(ava.getOcupacionId())));            //ACTIVIDAD ECONOMICA
                                params.put(16, "");                                                                       //DESTINO CREDITO
                                params.put(17, "");                                                                       //OTRO DESTINO
                                params.put(18, String.valueOf(id_direccion_aval));                                        //DIRECCION ID
                                params.put(19, Miscellaneous.GetViviendaTipo(ctx, Miscellaneous.validInt(ava.getViviendaTipoId())));   //TIPO VIVIENDA
                                params.put(20, Miscellaneous.validStr(ava.getNombreTitular()));                //NOMBRE TITULAR
                                params.put(21, Miscellaneous.GetParentesco(ctx, Miscellaneous.validInt(ava.getParentescoTitularId()))); //PARENTESCO
                                params.put(22, Miscellaneous.validStr(ava.getCaracteristicasDomicilio()));     //CARACTERISTICAS DOMICILIO
                                params.put(23, String.valueOf(Miscellaneous.validInt(ava.getAntiguedad())));                           //ANTIGUEDAD
                                params.put(24, (ava.getTieneNegocio() != null && ava.getTieneNegocio()) ? "1" : "2");                               //TIENE NEGOCIO
                                params.put(25, Miscellaneous.validStr(ava.getNombreNegocio()));                //NOMBRE NEGOCIO
                                params.put(26, String.valueOf(ava.getIngresoMensual()));   //ING MENSUAL
                                params.put(27, String.valueOf(ava.getIngresosOtros()));    //ING OTROS
                                params.put(28, String.valueOf(ava.getGastoMensual()));     //GASTO SEMANAL
                                params.put(29, String.valueOf(ava.getGastoAgua()));        //GASTO AGUA
                                params.put(30, String.valueOf(ava.getGastoLuz()));         //GASTO LUZ
                                params.put(31, String.valueOf(ava.getGastoTelefono()));    //GASTO TELEFONO
                                params.put(32, String.valueOf(ava.getGastoRenta()));       //GASTO RENTA
                                params.put(33, String.valueOf(ava.getGastoOtros()));       //GASTO OTROS
                                params.put(34, String.valueOf(ava.getCapacidadPago()));    //CAPACIDAD PAGOS
                                params.put(35, Miscellaneous.GetMediosPagoSoli(ctx, ava.getMediosPago()));                                 //MEDIO PAGO
                                params.put(36, ava.getOtroMedioPago());                   //OTRO MEDIO PAGO
                                params.put(37, String.valueOf(ava.getMontoMaximo()));      //MONTO MAXIMO
                                params.put(38, Miscellaneous.validStr(ava.getHoraLocalizacion()));            //HORARIO LOCALIZACION
                                params.put(39, ava.getActivosObservables());                                 //ACTIVOS OBSERVABLES
                                params.put(40, Miscellaneous.validStr(ava.getTelCasa()));                     //TEL CASA
                                params.put(41, Miscellaneous.validStr(ava.getTelCelular()));                  //TEL CELULAR
                                params.put(42, Miscellaneous.validStr(ava.getTelMensaje()));                  //TEL MENSAJES
                                params.put(43, Miscellaneous.validStr(ava.getTelTrabajo()));                  //TEL TRABAJO
                                params.put(44, Miscellaneous.validStr(ava.getEmail()));                       //EMAIL
                                params.put(45, "");                                 //FOTO FACHADA
                                params.put(46, Miscellaneous.validStr(ava.getReferencia()));                 //REF DOMICILIARIA
                                params.put(47, "");                                 //FIRMA
                                params.put(48, "0");                                //ESTATUS RECHAZO
                                params.put(49, "");                                 //COMENTARIO RECHAZO
                                params.put(50, "0");                                //ESTATUS RECHAZO

                                dBhelper.saveDatosAval(db, params, 3);

                                //Inserta registro de direccion del referencia
                                params = new HashMap<>();
                                params.put(0, "REFERENCIA");                                                 //TIPO DIRECCION
                                params.put(1, "");                                                           //LATITUD
                                params.put(2, "");                                                           //LONGITUD
                                params.put(3, Miscellaneous.validStr(ref.getCalle()));      //CALLE
                                params.put(4, Miscellaneous.validStr(ref.getNoExterior())); //NO EXTERIOR
                                params.put(5, Miscellaneous.validStr(ref.getNoInterior())); //NO INTERIOR
                                params.put(6, Miscellaneous.validStr(ref.getNoManzana()));  //MANZANA
                                params.put(7, Miscellaneous.validStr(ref.getNoLote()));     //LOTE
                                params.put(8, (ref.getCodigoPostal() == 0) ? "" : String.valueOf(ref.getCodigoPostal()));                                                     //CP
                                params.put(9, Miscellaneous.GetColonia(ctx, Miscellaneous.validInt(ref.getColoniaId()))); //COLONIA
                                params.put(10, Miscellaneous.validStr(ref.getCiudad()));    //CIUDAD
                                params.put(11, Miscellaneous.GetLocalidad(ctx, Miscellaneous.validInt(ref.getLocalidadId()))); //LOCALIDAD
                                params.put(12, Miscellaneous.GetMunicipio(ctx, Miscellaneous.validInt(ref.getMunicipioId())));                                                    //MUNICIPIO
                                params.put(13, Miscellaneous.GetEstado(ctx, Miscellaneous.validInt(ref.getEstadoId())));                                                    //ESTADO

                                id_direccion_ref = dBhelper.saveDirecciones(db, params, 3);

                                //Inserta registro de referencia
                                params = new HashMap<>();
                                params.put(0, String.valueOf(id));                                                     //ID SOLICITUD
                                params.put(1, Miscellaneous.validStr(ref.getNombre().toUpperCase())); //NOMBRE
                                params.put(2, Miscellaneous.validStr(ref.getPaterno().toUpperCase()));//PATERNO
                                params.put(3, Miscellaneous.validStr(ref.getMaterno().toUpperCase()));//MATERNO
                                params.put(4, Miscellaneous.validStr(ref.getFechaNacimiento()));      //FECHA NACIMIENTO
                                params.put(5, String.valueOf(id_direccion_ref));                      //DIRECCION ID
                                params.put(6, Miscellaneous.validStr(ref.getTelCelular()));           //TEL_CELULAR
                                params.put(7, "0");                                                   //ESTATUS COMPLETADO
                                params.put(8, "");                                                    //COMENTARIO RECHAZO

                                dBhelper.saveReferencia(db, params, 3);

                                //Inserta registro de croquis
                                params = new HashMap<>();
                                params.put(0, String.valueOf(id));                  //ID SOLICITUD
                                params.put(1, cro.getCalleEnfrente());              //CALLE PRINCIPAL
                                params.put(2, cro.getLateralDerecha());             //LATERAL UNO
                                params.put(3, cro.getLateralIzquierda());           //LATERAL DOS
                                params.put(4, cro.getCalleAtras());                 //CALLE TRASERA
                                params.put(5, cro.getReferencias());                //REFERENCIAS
                                params.put(6, "0");                                 //ESTATUS COMPLETADO
                                params.put(7, "");                                  //COMENTARIO RECHAZO

                                dBhelper.saveCroquisInd(db, params, 3);

                                //Inserta registro de politicas
                                params = new HashMap<>();
                                params.put(0, String.valueOf(id));                       //ID SOLICITUD
                                params.put(1, (pol.getPropietario()) ? "1" : "2");           //PROPIERATIO REAL
                                params.put(2, (pol.getProveedorRecursos()) ? "1" : "2");     //PROVEEDOR RECURSOS
                                params.put(3, (pol.getPoliticamenteExpuesto()) ? "1" : "2"); //PERSONA POLITICA
                                params.put(4, "0");                                      //ESTATUS COMPLETADO

                                dBhelper.savePoliticasInd(db, params, 3);

                            }

                        }

                        for (MSolicitudAutorizar.SolicitudGrupal item : mSolicitud.getSolicitudesGrupales()){
                            String sql = "SELECT * FROM "+TBL_SOLICITUDES_AUTO + " WHERE id_originacion = ?";
                            Cursor row = db.rawQuery(sql, new String[]{String.valueOf(item.getPrestamoGpo().getSolicitudId())});
                            if (row.getCount() == 0){

                                HashMap<Integer, String> params = new HashMap<>();
                                params.put(0, "2");                                      //TIPO SOLICITUD
                                params.put(1, item.getPrestamoGpo().getAsesor());        //ASESOR
                                params.put(2, ((item.getPrestamoGpo().getTipoSolicitud() == 1) ? "ORIGINACION" : "RENOVACION")); //TIPO SOLICITUD
                                params.put(3, String.valueOf(item.getPrestamoGpo().getSolicitudId()));    //SOLICITUD ID
                                params.put(4, item.getPrestamoGpo().getNombreGrupo());  //NOMBRE grupo
                                params.put(5, "");                                      //FECHA ENVIO
                                params.put(6, "0");                                     //ESTATUS

                                Log.e("Params", params.toString());
                                Long idSoliGpo = dBhelper.saveSolicitudesAuto(db, params);

                                params = new HashMap<>();
                                params.put(0, String.valueOf(idSoliGpo));
                                params.put(1, item.getPrestamoGpo().getNombreGrupo());
                                params.put(2, Miscellaneous.GetPlazo(item.getPrestamoGpo().getPlazo()));
                                params.put(3, Miscellaneous.GetPeriodicidad(item.getPrestamoGpo().getPeriodicida()));
                                params.put(4, item.getPrestamoGpo().getFechaDesembolso());
                                params.put(5, Miscellaneous.DiaSemana(item.getPrestamoGpo().getFechaDesembolso()));
                                params.put(6, item.getPrestamoGpo().getHoraVisita());
                                params.put(7,"0");
                                params.put(8, "");
                                params.put(9, "0");
                                params.put(10, "");

                                Long id_credito = dBhelper.saveDatosCreditoGpoRen(db, params, 2);

                                for (MSolicitudAutorizar.Integrantes inte : item.getIntegrantes()){

                                    int tipo = 3;
                                    long id = 0;
                                    //Inserta registro de integrante
                                    params = new HashMap<>();
                                    params.put(0, String.valueOf(id_credito));                              //ID CREDITO
                                    params.put(1, String.valueOf(inte.getTipoIntegrante()));                                //CARGO
                                    params.put(2, inte.getCliente().getNombre().trim().toUpperCase());      //NOMBRE(S)
                                    params.put(3, inte.getCliente().getPaterno().trim().toUpperCase());     //PATERNO
                                    params.put(4, inte.getCliente().getMaterno().trim().toUpperCase());     //MATERNO
                                    params.put(5, inte.getCliente().getFechaNacimiento());                  //FECHA NACIMIENTO
                                    params.put(6, String.valueOf(inte.getCliente().getEdad()));             //EDAD
                                    params.put(7, String.valueOf(inte.getCliente().getGenero()));           //GENERO
                                    params.put(8, Miscellaneous.GetEstado(ctx, inte.getCliente().getEstadoNacimiento())); //ESTADO NACIMIENTO
                                    params.put(9, inte.getCliente().getRfc());                              //RFC
                                    params.put(10, inte.getCliente().getCurp());                            //CURP
                                    params.put(11, "");                                                     //CURP DIGITO VERI
                                    params.put(12, Miscellaneous.GetTipoIdentificacion(ctx, inte.getCliente().getIdentificacionTipoId()));//TIPO IDENTIFICACION
                                    params.put(13, inte.getCliente().getNoIdentificacion());                //NO IDENTIFICACION
                                    params.put(14, Miscellaneous.GetEstudio(ctx, inte.getCliente().getEstudioNivelId()));//NIVEL ESTUDIO
                                    params.put(15, Miscellaneous.GetOcupacion(ctx, inte.getCliente().getOcupacionId()));//OCUPACION
                                    params.put(16, Miscellaneous.GetEstadoCivil(ctx, inte.getCliente().getEstadoCivilId()));//ESTADO CIVIL
                                    params.put(17, String.valueOf(inte.getCliente().getRegimenBienId()));   //BIENES
                                    params.put(18, "0");                                                    //ESTATUS RECHAZO
                                    params.put(19, "");                                                     //COMENTARIO RECHAZO
                                    params.put(20, "1");                                                    //ESTATUS COMPLETO
                                    params.put(21, String.valueOf(inte.getIdSolicitudIntegrante()));        //ID SOLICITUD INTEGRANTE

                                    id = dBhelper.saveIntegrantesGpo(db, params, tipo);

                                    //Inserta registro de datos telefonicos
                                    params = new HashMap<>();
                                    params.put(0, String.valueOf(id));              //ID INTEGRANTE
                                    params.put(1, Miscellaneous.validStr(inte.getCliente().getTelCasa()));      //TEL CASA
                                    params.put(2, Miscellaneous.validStr(inte.getCliente().getTelCelular()));   //TEL CELULAR
                                    params.put(3, Miscellaneous.validStr(inte.getCliente().getTelMensaje()));   //TEL MENSAJES
                                    params.put(4, Miscellaneous.validStr(inte.getCliente().getTelTrabajo()));   //TEL TRABAJO
                                    params.put(5, "1");                                                         //ESTATUS COMPLETADO

                                    dBhelper.saveDatosTelefonicos(db, params, tipo);

                                    //Inserta registro de datos domicilio
                                    params = new HashMap<>();
                                    params.put(0, String.valueOf(id));          //ID INTEGRANTE
                                    params.put(1, Miscellaneous.validStr(inte.getCliente().getLatitud()));      //LATITUD
                                    params.put(2, Miscellaneous.validStr(inte.getCliente().getLongitud()));     //LONGITUD
                                    params.put(3, Miscellaneous.validStr(inte.getCliente().getCalle()));        //CALLE
                                    params.put(4, Miscellaneous.validStr(inte.getCliente().getNoExterior()));   //NO_EXTERIOR
                                    params.put(5, Miscellaneous.validStr(inte.getCliente().getNoInterior()));   //NO INTERIOR
                                    params.put(6, Miscellaneous.validStr(inte.getCliente().getNoManzana()));    //MANZANA
                                    params.put(7, Miscellaneous.validStr(inte.getCliente().getNoLote()));       //LOTE
                                    params.put(8, String.valueOf(inte.getCliente().getCodigoPostal()));         //CP
                                    params.put(9, Miscellaneous.GetColonia(ctx, inte.getCliente().getColoniaId()));//COLONIA
                                    params.put(10, Miscellaneous.validStr(inte.getCliente().getCiudad()));      //CIUDAD
                                    params.put(11, Miscellaneous.GetLocalidad(ctx, inte.getCliente().getLocalidadId()));//LOCALIDAD
                                    params.put(12, Miscellaneous.GetMunicipio(ctx, inte.getCliente().getMunicipioId()));//MUNICIPIO
                                    params.put(13, Miscellaneous.GetEstado(ctx, inte.getCliente().getEstadoId()));//ESTADO
                                    params.put(14, Miscellaneous.GetViviendaTipo(ctx, inte.getCliente().getViviendaTipoId()));//TIPO VIVIENDA
                                    params.put(15, Miscellaneous.GetParentesco(ctx, inte.getCliente().getParentescoId()));//PARENTESCO
                                    params.put(16, Miscellaneous.validStr(inte.getCliente().getOtroTipoVivienda()));//OTRO TIPO VIVIENDA
                                    params.put(17, String.valueOf(Miscellaneous.validInt(inte.getCliente().getTiempoVivirSitio())));//TIEMPO VIVIR SITIO
                                    params.put(18, "");                         //FOTO FACHADA
                                    params.put(19, Miscellaneous.validStr(inte.getCliente().getReferencia()));   //REF DOMICILIARIA
                                    params.put(20, "1");                        //ESTATUS COMPLETO
                                    Log.e("DependientesEco", String.valueOf(Miscellaneous.validInt(inte.getCliente().getDependientesEconomico())));
                                    params.put(21, String.valueOf(Miscellaneous.validInt(inte.getCliente().getDependientesEconomico())));//DEPENDIENTES ECONOMICOS

                                    dBhelper.saveDatosDomicilio(db, params, tipo);

                                    //Inserta registro de negocio
                                    params = new HashMap<>();
                                    params.put(0, String.valueOf(id));          //ID INTEGRANTE
                                    params.put(1, Miscellaneous.validStr(inte.getNegocio().getNombre()));     //NOMBRE
                                    params.put(2, Miscellaneous.validStr(inte.getNegocio().getLatitud()));    //LATITID
                                    params.put(3, Miscellaneous.validStr(inte.getNegocio().getLongitud()));   //LONGITUD
                                    params.put(4, Miscellaneous.validStr(inte.getNegocio().getCalle()));      //CALLE
                                    params.put(5, Miscellaneous.validStr(inte.getNegocio().getNoExterior())); //NO EXTERIOR
                                    params.put(6, Miscellaneous.validStr(inte.getNegocio().getNoInterior())); //NO INTERIOR
                                    params.put(7, Miscellaneous.validStr(inte.getNegocio().getNoManzana()));  //MANZANA
                                    params.put(8, Miscellaneous.validStr(inte.getNegocio().getNoLote()));     //LOTE
                                    params.put(9, String.valueOf(inte.getNegocio().getCodigoPostal()));       //CP
                                    params.put(10, Miscellaneous.GetColonia(ctx, inte.getNegocio().getColoniaId()));//COLONIA
                                    params.put(11, Miscellaneous.validStr(inte.getNegocio().getCiudad()));    //CIUDAD
                                    params.put(12, Miscellaneous.GetLocalidad(ctx, inte.getNegocio().getLocalidadId())); //LOCALIDAD
                                    params.put(13, Miscellaneous.GetMunicipio(ctx, inte.getNegocio().getMunicipioId())); //MUNICIPIO
                                    params.put(14, Miscellaneous.GetEstado(ctx, inte.getNegocio().getEstadoId())); //ESTADO
                                    params.put(15, Miscellaneous.GetDestinoCredito(ctx, inte.getNegocio().getDestinoCreditoId())); //DESTINO CREDITO
                                    params.put(16, Miscellaneous.validStr(inte.getNegocio().getOtroDestinoCredito())); //OTRO DESTINO CREDITO
                                    params.put(17, Miscellaneous.GetOcupacion(ctx, inte.getNegocio().getOcupacionId())); //OCUPACION
                                    params.put(18, Miscellaneous.GetSector(ctx, inte.getNegocio().getSectorId())); //ACTIVIDAD ECONOMICA
                                    params.put(19, String.valueOf(Miscellaneous.validInt(inte.getNegocio().getAntiguedad()))); //ANTIGUEDA
                                    params.put(20, String.valueOf(inte.getNegocio().getIngresoMensual()));   //INGRESO MENSUAL
                                    params.put(21, String.valueOf(inte.getNegocio().getIngresosOtros()));    //INGRESOS OTROS
                                    params.put(22, String.valueOf(inte.getNegocio().getGastoMensual()));     //GASTO MENSUAL
                                    params.put(23, String.valueOf(inte.getNegocio().getCapacidadPago()));    //CAPACIDAD DE PAGO
                                    params.put(24, String.valueOf(inte.getNegocio().getMontoMaximo()));      //MONTO MAXIMO
                                    params.put(25, Miscellaneous.GetMediosPagoSoli(ctx, inte.getNegocio().getMediosPagosIds()));                          //MEDIOS PAGO
                                    params.put(26, Miscellaneous.validStr(inte.getNegocio().getOtroMedioPago()));                          //OTRO MEDIO DE PAGO
                                    int numOper = 30 /item.getPrestamoGpo().getPeriodicida();
                                    params.put(27, String.valueOf(numOper));                          //NUM OPERACIONES MENSUALES
                                    params.put(28, String.valueOf(Miscellaneous.validInt(inte.getNegocio().getNumOperacionesMensualesEfectivo())));                          //NUM OPERACIONES MENSUALES EFECTIVO
                                    params.put(29,"");                          //FOTO FACHADA
                                    params.put(30, Miscellaneous.validStr(inte.getNegocio().getReferencia()));                          //REFERENCIA DOMICILIARIA
                                    params.put(31,"0");                         //ESTATUS RECHAZO
                                    params.put(32,"");                          //COMENTARIO RECHAZADO
                                    params.put(33,"1");                         //ESTATUS COMPLETADO

                                    dBhelper.saveDatosNegocioGpo(db, params, tipo);

                                    if (inte.getCliente().getEstadoCivilId() == 2 || inte.getCliente().getEstadoCivilId() == 5) {
                                        //Inserta registro del conyuge
                                        MSolicitudAutorizar.Conyuge c = inte.getConyuge();
                                        params = new HashMap<>();
                                        params.put(0, String.valueOf(id));          //ID INTEGRANTE
                                        params.put(1, Miscellaneous.validStr(c.getNombre()).trim().toUpperCase());       //NOMBRE
                                        params.put(2, Miscellaneous.validStr(c.getPaterno()).trim().toUpperCase());      //PATERNO
                                        params.put(3, Miscellaneous.validStr(c.getMaterno()).trim().toUpperCase());      //MATERNO
                                        params.put(4, Miscellaneous.validStr(c.getNacionalidad()).trim().toUpperCase()); //NACIONALIDAD
                                        params.put(5, Miscellaneous.GetOcupacion(ctx, c.getOcupacionId()));              //OCUPACION
                                        params.put(6, Miscellaneous.validStr(c.getCalle()).trim().toUpperCase());        //CALLE
                                        params.put(7, Miscellaneous.validStr(c.getNoExterior()).trim().toUpperCase());   //NO EXTERIOR
                                        params.put(8, Miscellaneous.validStr(c.getNoInterior()).trim().toUpperCase());   //NO INTERIOR
                                        params.put(9, Miscellaneous.validStr(c.getNoManzana()).trim().toUpperCase());    //MANZANA
                                        params.put(10, Miscellaneous.validStr(c.getNoLote()).trim().toUpperCase());      //LOTE
                                        params.put(11, String.valueOf(c.getCodigoPostal()));                             //CP
                                        params.put(12, Miscellaneous.GetColonia(ctx, c.getColoniaId()));                 //COLONIA
                                        params.put(13, Miscellaneous.validStr(c.getCiudad()).trim().toUpperCase());      //CIUDAD
                                        params.put(14, Miscellaneous.GetLocalidad(ctx, c.getLocalidadId()));             //LOCALIDAD
                                        params.put(15, Miscellaneous.GetMunicipio(ctx, c.getMunicipioId()));             //MUNICIPIO
                                        params.put(16, Miscellaneous.GetEstado(ctx, c.getEstadoId()));                   //ESTADO
                                        params.put(17, String.valueOf(c.getIngresoMensual()));                           //INGRESO MENSUAL
                                        params.put(18, String.valueOf(c.getGastoMensual()));                             //GASTO MENSUAL
                                        params.put(19, Miscellaneous.validStr(c.getTelCasa()));                          //TEL CASA
                                        params.put(20, Miscellaneous.validStr(c.getTelCelular()));                       //TEL CELULAR
                                        params.put(21, "1");                         //ESTATUS COMPLETADO

                                        dBhelper.saveDatosConyugeGpo(db, params, tipo);
                                    } //fin de registrar conyuge

                                    //Inserta otros datos del integrante
                                    params = new HashMap<>();
                                    params.put(0, String.valueOf(id));                                                         //ID INTEGRANTE
                                    params.put(1, Miscellaneous.validStr(inte.getClasificacion()));                            //CLASIFICACION RIESGO
                                    params.put(2, Miscellaneous.GetMedioContacto(ctx, inte.getCliente().getMedioContactoId()));//MEDIO CONTACTO
                                    params.put(3, inte.getCliente().getEmail());                                               //EMAIL
                                    params.put(4, inte.getCliente().getEstadoCuenta());                                        //ESTADO CUENTA
                                    params.put(5, String.valueOf(inte.getEstatusIntegrante()));                                //ESTATUS INTEGRANTE
                                    params.put(6, String.valueOf(inte.getMonto()));                                            //MONTO SOLICITADO
                                    params.put(7, (inte.getCasaReunion())?"1":"2");                                            //CASA REUNION
                                    params.put(8, "");                                                                         //FIRMA
                                    params.put(9, "0");                                                                        //ESTATUS COMPLETADO
                                    params.put(10, "");                                                                        //MONTO AUTORIZADO
                                    dBhelper.saveDatosOtrosGpoAuto(db, params);

                                    if (inte.getCasaReunion()){
                                        //Inserta registro de croquis
                                        params = new HashMap<>();
                                        params.put(0, String.valueOf(id));                      //ID SOLICITUD
                                        params.put(1, inte.getCroquis().getCalleEnfrente());    //CALLE PRINCIPAL
                                        params.put(2, inte.getCroquis().getLateralIzquierda()); //LATERAL UNO
                                        params.put(3, inte.getCroquis().getLateralDerecha());   //LATERAL DOS
                                        params.put(4, inte.getCroquis().getCalleAtras());       //CALLE TRASERA
                                        params.put(5, inte.getCroquis().getReferencias());      //REFERENCIAS
                                        params.put(6, "1");                                     //ESTATUS COMPLETADO

                                        dBhelper.saveCroquisGpo(db, params, tipo);
                                    } //fin de registro croquis

                                    params = new HashMap<>();
                                    params.put(0, String.valueOf(id));                                       //ID INTEGRANTE
                                    params.put(1, (inte.getPoliticas().getPropietario())?"1":"2");           //PROPIETARIO REAL
                                    params.put(2, (inte.getPoliticas().getProveedorRecursos())?"1":"2");     //PROVEEDOR RECURSOS
                                    params.put(3, (inte.getPoliticas().getPoliticamenteExpuesto())?"1":"2"); //PERSONA POLITICA
                                    params.put(4, "1");                     //ESTATUS COMPLETADO

                                    dBhelper.savePoliticasIntegrante(db, params, tipo);

                                }
                            }
                        }

                        break;
                }
            }

            @Override
            public void onFailure(Call<MSolicitudAutorizar> call, Throwable t) {
                Log.e("Autorizada", t.getMessage()+" Solicitud");
            }
        });
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

        sql = "SELECT * FROM (SELECT vri._id, vri.num_prestamo_id_gestion, vri.tipo_reimpresion, vri.folio, vri.monto, vri.clv_cliente, vri.asesor_id, vri.serie_id, vri.create_at, vri.sent_at, vri.estatus, vri.num_prestamo, pi.tipo_cartera, '1' AS tipo_gestion, 'VIGENTE' AS tipo_prestamo, vri.celular FROM " + TBL_REIMPRESION_VIGENTE_T + " AS vri INNER JOIN " + TBL_PRESTAMOS_IND_T + " AS pi ON vri.num_prestamo = pi.num_prestamo WHERE vri.num_prestamo LIKE '%-L%' AND pi.tipo_cartera IN ('VIGENTE','COBRANZA') UNION SELECT vri2._id, vri2.num_prestamo_id_gestion, vri2.tipo_reimpresion, vri2.folio, vri2.monto, vri2.clv_cliente, vri2.asesor_id, vri2.serie_id, vri2.create_at, vri2.sent_at, vri2.estatus, vri2.num_prestamo, pg.tipo_cartera, '2' AS tipo_gestion, 'VIGENTE' AS tipo_prestamo, vri2.celular FROM " + TBL_REIMPRESION_VIGENTE_T + " AS vri2 INNER JOIN " + TBL_PRESTAMOS_GPO_T + " AS pg ON vri2.num_prestamo = pg.num_prestamo WHERE vri2.num_prestamo NOT LIKE '%-L%' AND pg.tipo_cartera IN ('VIGENTE','COBRANZA') UNION SELECT vi._id, vi.num_prestamo_id_gestion, vi.tipo_reimpresion, vi.folio, vi.monto, vi.clv_cliente, vi.asesor_id, vi.serie_id, vi.create_at, vi.sent_at, vi.estatus, vi.num_prestamo, pvi.tipo_cartera, '1' AS tipo_gestion, 'VENCIDA' AS tipo_prestamo, vi.celular FROM " + TBL_REIMPRESION_VIGENTE_T + " AS vi INNER JOIN " + TBL_PRESTAMOS_IND_T + " AS pvi ON vi.num_prestamo = pvi.num_prestamo WHERE vi.num_prestamo LIKE '%-L%' AND pvi.tipo_cartera IN ('VENCIDA') UNION SELECT vg._id, vg.num_prestamo_id_gestion, vg.tipo_reimpresion, vg.folio, vg.monto, vg.clv_cliente, vg.asesor_id, vg.serie_id, vg.create_at, vg.sent_at, vg.estatus, vg.num_prestamo, pvg.tipo_cartera, '2' AS tipo_gestion, 'VENCIDA' AS tipo_prestamo, vg.celular FROM " + TBL_REIMPRESION_VIGENTE_T + " AS vg INNER JOIN " + TBL_PRESTAMOS_GPO_T + " AS pvg ON vg.num_prestamo = pvg.num_prestamo WHERE vg.num_prestamo NOT LIKE '%-L%' AND pvg.tipo_cartera IN ('VENCIDA')) AS imp WHERE estatus = ?";

        row = db.rawQuery(sql, new String[]{"0"});

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
                    item.setCelular(row.getString(15));

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
        final SessionManager session = new SessionManager(ctx);
        ReciboDao reciboDao = new ReciboDao(ctx);

        ReciboService reciboService = new RetrofitClient().newInstance(ctx).create(ReciboService.class);
        Call<List<Recibo>> call = reciboService.last(session.getUser().get(9), "Bearer "+ session.getUser().get(7));

        call.enqueue(new Callback<List<Recibo>>() {
            @Override
            public void onResponse(Call<List<Recibo>> call, Response<List<Recibo>> response) {
                switch (response.code()) {
                    case 200:
                        List<Recibo> data = response.body();
                        for (Recibo item : data){
                            Recibo reciboDb = null;

                            if(item.getFolio() != null)
                            {
                                String[] folio = item.getFolio().split("-");
                                item.setFolio(folio[2]);
                            }

                            if(item.getFechaImpresion() != null)
                            {
                                item.setFechaImpresion(item.getFechaImpresion().substring(0, 19).replace("T", " "));
                            }

                            if(item.getFechaEnvio() != null)
                            {
                                item.setFechaEnvio(item.getFechaEnvio().substring(0, 19).replace("T", " "));
                            }

                            item.setEstatus(1);

                            if(item.getGrupoId().equals("1"))
                            {
                                reciboDb = reciboDao.findByGrupoIdAndNumSolicitudAndTipoImpresion(item.getGrupoId(), item.getNumSolicitud(), item.getTipoImpresion());
                            }
                            else
                            {
                                reciboDb = reciboDao.findByNombreAndNumSolicitudAndTipoImpresion(item.getNombre(), item.getNumSolicitud(), item.getTipoImpresion());
                            }

                            if(reciboDb == null)
                            {
                                reciboDao.store(item);
                            }
                            else
                            {
                                reciboDao.update(reciboDb.getId(), item);
                            }
                        }
                        break;
                    default:
                        Log.e("ERROR AQUI ULT REC", response.message());
                        break;
                }
            }

            @Override
            public void onFailure(Call<List<Recibo>> call, Throwable t) {

            }
        });
    }

    //Obtiene el ultimo folio cobrado de circulo de credito
    public void GetUltimosRecibosCC(Context ctx){
        final SessionManager session = new SessionManager(ctx);
        ReciboCirculoCreditoDao reciboCCDao = new ReciboCirculoCreditoDao(ctx);
        GestionCirculoCreditoDao gestionCCDao = new GestionCirculoCreditoDao(ctx);

        ReciboCirculoCreditoService reciboCCService = new RetrofitClient().newInstance(ctx).create(ReciboCirculoCreditoService.class);

        Call<List<CirculoCredito>> call = reciboCCService.show("Bearer "+ session.getUser().get(7), Long.parseLong(session.getUser().get(9)));

        call.enqueue(new Callback<List<CirculoCredito>>() {
            @Override
            public void onResponse(Call<List<CirculoCredito>> call, Response<List<CirculoCredito>> response) {
                switch (response.code()){
                    case 200:
                        List<CirculoCredito> items = response.body();
                        Integer index = 0;

                        for(CirculoCredito cc : items)
                        {
                            index++;

                            String[] folio = null;
                            String folioManual = null;

                            if(cc.getFolio() != null && !cc.getFolio().equals(""))
                            {
                                folio = cc.getFolio().split("-");
                            }

                            if(cc.getFolioManual() != null && !cc.getFolioManual().equals(""))
                            {
                                folioManual = (String) cc.getFolioManual();
                            }

                            GestionCirculoCredito gestionCC = gestionCCDao.findByCurp(cc.getCurp());

                            if(gestionCC == null)
                            {
                                gestionCC = new GestionCirculoCredito();
                                gestionCC.setTipoCredito((cc.getProducto().equals("CREDITO INDIVIDUAL"))? 1 : 2);
                                gestionCC.setNombreUno(cc.getClienteGrupo());
                                gestionCC.setCurp(cc.getCurp());
                                gestionCC.setNombreDos(cc.getAvalRepresentante());
                                gestionCC.setIntegrantes(cc.getIntegrantes());
                                gestionCC.setMonto(cc.getMonto());
                                gestionCC.setMedioPago(Miscellaneous.GetMedioPago(cc.getMedioPagoId()));

                                if(folioManual != null)
                                {
                                    gestionCC.setImprimirRecibo("NO");
                                    gestionCC.setFolio(Integer.parseInt(folioManual));
                                }

                                if(folio != null)
                                {
                                    gestionCC.setImprimirRecibo("SI");
                                    gestionCC.setFolio(Integer.parseInt(folio[2]));
                                }

                                if(folioManual == null && folio == null){
                                    gestionCC.setImprimirRecibo("NO");
                                    gestionCC.setFolio(0);
                                }

                                gestionCC.setEvidencia("");
                                gestionCC.setTipoImagen("");
                                gestionCC.setFechaTermino("");
                                gestionCC.setFechaEnvio(cc.getFechaEnvio().substring(0,19).replace("T", " "));
                                gestionCC.setEstatus(2);

                                Double costoConsulta = Double.parseDouble(gestionCC.getMonto());

                                gestionCC.setCostoConsulta("");

                                if(gestionCC.getIntegrantes() != null && gestionCC.getIntegrantes() > 0 && costoConsulta > 0)
                                {
                                    costoConsulta = costoConsulta/gestionCC.getIntegrantes();

                                    if(costoConsulta > 20){
                                        gestionCC.setCostoConsulta("22.50");
                                    }
                                    else
                                    {
                                        gestionCC.setCostoConsulta("17.50");
                                    }
                                }

                                if(gestionCC.getIntegrantes() == 0){
                                    if(costoConsulta > 20){
                                        gestionCC.setCostoConsulta("22.50");
                                    }
                                    else
                                    {
                                        gestionCC.setCostoConsulta("17.50");
                                    }
                                }


                                gestionCCDao.store(gestionCC);
                            }


                            if( index == items.size())
                            {
                                ReciboCirculoCredito reciboCC = reciboCCDao.findByCurpAndTipoImpresion(cc.getCurp(), cc.getTipoImpresion());

                                if(reciboCC == null && folio != null){
                                    reciboCC = new ReciboCirculoCredito();
                                    reciboCC.setTipoCredito((cc.getProducto().equals("CREDITO INDIVIDUAL"))? 1 : 2);
                                    reciboCC.setNombreUno(cc.getClienteGrupo());
                                    reciboCC.setCurp(cc.getCurp());
                                    reciboCC.setNombreDos(cc.getAvalRepresentante());
                                    reciboCC.setTotalIntegrantes(cc.getIntegrantes());
                                    reciboCC.setMonto(cc.getMonto());
                                    reciboCC.setTipoImpresion(cc.getTipoImpresion());
                                    reciboCC.setFolio(Integer.parseInt(folio[2]));
                                    reciboCC.setFechaImpresion(cc.getFechaImpreso().substring(0,19).replace("T", " "));
                                    reciboCC.setFechaEnvio(cc.getFechaEnvio().substring(0,19).replace("T", " "));
                                    reciboCC.setEstatus(1);

                                    reciboCCDao.store(reciboCC);
                                }
                            }
                        }
                        break;
                    default:
                        Log.e("ERROR AQUI ULT REC CC", response.message());
                        break;
                }
            }

            @Override
            public void onFailure(Call<List<CirculoCredito>> call, Throwable t) {

            }
        });
    }

    public void GetPrestamosToRenovar(final Context ctx){
        final DBhelper dBhelper = new DBhelper(ctx);
        final SQLiteDatabase db = dBhelper.getWritableDatabase();
        final SessionManager session = new SessionManager(ctx);
        ManagerInterface api = new RetrofitClient().generalRF(CONTROLLER_MOVIL, ctx).create(ManagerInterface.class);

        Call<List<MPrestamosRenovar>> call = api.getPrestamoToRenovar("Bearer "+ session.getUser().get(7));

        call.enqueue(new Callback<List<MPrestamosRenovar>>() {
            @Override
            public void onResponse(Call<List<MPrestamosRenovar>> call, Response<List<MPrestamosRenovar>> response) {
                switch (response.code()){
                    case 200:
                        Log.e("AQUI", String.valueOf(response.code()));
                        List<MPrestamosRenovar> prestamos = response.body();
                        if (prestamos.size() > 0){
                            for(MPrestamosRenovar item : prestamos){
                                if (item.getTipoPrestamo().equals("INDIVIDUAL")) {
                                    String sql = "SELECT * FROM " + TBL_PRESTAMOS_TO_RENOVAR + " WHERE asesor_id = ? AND prestamo_id = ? and cliente_id = ?";
                                    Cursor row = db.rawQuery(sql, new String[]{session.getUser().get(0), String.valueOf(item.getPrestamoId()), String.valueOf(item.getClienteId()) });

                                    if (row.getCount() == 0) {
                                        HashMap<Integer, String> params = new HashMap<>();
                                        params.put(0, session.getUser().get(0));
                                        params.put(1, String.valueOf(item.getPrestamoId()));
                                        params.put(2, String.valueOf(item.getClienteId()));
                                        params.put(3, item.getNombre());
                                        params.put(4, item.getNoPrestamo().trim());
                                        params.put(5, item.getFechaVencimiento());
                                        params.put(6, String.valueOf(item.getNumPagos()));
                                        params.put(7, "0");
                                        params.put(8, "1");
                                        params.put(9, String.valueOf(item.getGrupoId()));

                                        dBhelper.savePrestamosToRenovar(db, params);

                                    }
                                    row.close();
                                }
                                else{
                                    String sql = "SELECT * FROM " + TBL_PRESTAMOS_TO_RENOVAR + " WHERE asesor_id = ? AND grupo_id = ? and tipo_prestamo = 2 AND prestamo_id = ?";
                                    Cursor row = db.rawQuery(sql, new String[]{session.getUser().get(0), String.valueOf(item.getGrupoId()), String.valueOf(item.getPrestamoId())});

                                    if (row.getCount() == 0) {
                                        HashMap<Integer, String> params = new HashMap<>();
                                        params.put(0, session.getUser().get(0));
                                        params.put(1, String.valueOf(item.getPrestamoId()));
                                        params.put(2, String.valueOf(item.getClienteId()));
                                        params.put(3, item.getNombre());
                                        params.put(4, item.getNoPrestamo().trim());
                                        params.put(5, item.getFechaVencimiento());
                                        params.put(6, String.valueOf(item.getNumPagos()));
                                        params.put(7, "0");
                                        params.put(8, "2");
                                        params.put(9, String.valueOf(item.getGrupoId()));

                                        dBhelper.savePrestamosToRenovar(db, params);

                                    }
                                    row.close();
                                }
                            }
                            GetDatosRenovar(ctx);
                        }
                        break;
                    default:
                        Log.e("AQUI", String.valueOf(response.code()));
                        break;
                }
            }

            @Override
            public void onFailure(Call<List<MPrestamosRenovar>> call, Throwable t) {
                Log.e("AQUI", t.getMessage());
            }
        });
    }

    public void GetPrestamosToRenovarForce(final Context ctx){
        final DBhelper dBhelper = new DBhelper(ctx);
        final SQLiteDatabase db = dBhelper.getWritableDatabase();
        final SessionManager session = new SessionManager(ctx);
        ManagerInterface api = new RetrofitClient().generalRF(CONTROLLER_MOVIL, ctx).create(ManagerInterface.class);

        Call<List<MPrestamosRenovar>> call = api.getPrestamoToRenovar("Bearer "+ session.getUser().get(7));

        call.enqueue(new Callback<List<MPrestamosRenovar>>() {
            @Override
            public void onResponse(Call<List<MPrestamosRenovar>> call, Response<List<MPrestamosRenovar>> response) {
                switch (response.code()){
                    case 200:
                        List<MPrestamosRenovar> prestamos = response.body();
                        if (prestamos.size() > 0){
                            for(MPrestamosRenovar item : prestamos){
                                if (item.getTipoPrestamo().equals("INDIVIDUAL")) {
                                    String sql = "SELECT * FROM " + TBL_PRESTAMOS_TO_RENOVAR + " WHERE asesor_id = ? AND prestamo_id = ? and cliente_id = ?";
                                    Cursor row = db.rawQuery(sql, new String[]{session.getUser().get(0), String.valueOf(item.getPrestamoId()), String.valueOf(item.getClienteId()) });

                                    if (row.getCount() == 0) {
                                        HashMap<Integer, String> params = new HashMap<>();
                                        params.put(0, session.getUser().get(0));
                                        params.put(1, String.valueOf(item.getPrestamoId()));
                                        params.put(2, String.valueOf(item.getClienteId()));
                                        params.put(3, item.getNombre());
                                        params.put(4, item.getNoPrestamo().trim());
                                        params.put(5, item.getFechaVencimiento());
                                        params.put(6, String.valueOf(item.getNumPagos()));
                                        params.put(7, "0");
                                        params.put(8, "1");
                                        params.put(9, String.valueOf(item.getGrupoId()));

                                        dBhelper.savePrestamosToRenovar(db, params);

                                    }
                                    row.close();
                                }
                                else{
                                    String sql = "SELECT * FROM " + TBL_PRESTAMOS_TO_RENOVAR + " WHERE asesor_id = ? AND grupo_id = ? and tipo_prestamo = 2";
                                    Cursor row = db.rawQuery(sql, new String[]{session.getUser().get(0), String.valueOf(item.getGrupoId())});

                                    if (row.getCount() == 0) {
                                        HashMap<Integer, String> params = new HashMap<>();
                                        params.put(0, session.getUser().get(0));
                                        params.put(1, String.valueOf(item.getPrestamoId()));
                                        params.put(2, String.valueOf(item.getClienteId()));
                                        params.put(3, item.getNombre());
                                        params.put(4, item.getNoPrestamo().trim());
                                        params.put(5, item.getFechaVencimiento());
                                        params.put(6, String.valueOf(item.getNumPagos()));
                                        params.put(7, "0");
                                        params.put(8, "2");
                                        params.put(9, String.valueOf(item.getGrupoId()));

                                        dBhelper.savePrestamosToRenovar(db, params);

                                    }
                                    row.close();
                                }
                            }
                            GetDatosRenovarForce(ctx);
                        }
                        break;
                }
            }

            @Override
            public void onFailure(Call<List<MPrestamosRenovar>> call, Throwable t) {

            }
        });
    }

    private void GetDatosRenovar(Context ctx){
        final DBhelper dBhelper = new DBhelper(ctx);
        final SQLiteDatabase db = dBhelper.getWritableDatabase();
        SolicitudRenDao solicitudRenDao = new SolicitudRenDao(ctx);
        PrestamoToRenovarDao prestamoToRenovarDao = new PrestamoToRenovarDao(ctx);

        String sql = "SELECT _id, prestamo_id, cliente_id, tipo_prestamo, grupo_id, cliente_nombre, fecha_vencimiento  FROM " + TBL_PRESTAMOS_TO_RENOVAR;
        Cursor row = db.rawQuery(sql, new String[]{});

        if (row.getCount() > 0) {
            row.moveToFirst();

            for (int x = 0; x < row.getCount(); x++) {
                Log.e("AQUI ASIGNAR UNO ", row.getString(5));

                List<SolicitudRen> solicitudesRen = solicitudRenDao.findByNombreAndPrestamoId(row.getString(5), row.getInt(1));

                Log.e("AQUI ENCONTRADOS ", String.valueOf(solicitudesRen.size()));

                if (solicitudesRen.size() == 1) {
                    Log.e("AQUI ENCONTRADO ", String.valueOf(solicitudesRen.get(0).getPrestamoId()));

                    if (solicitudesRen.get(0).getPrestamoId() == null || solicitudesRen.get(0).getPrestamoId() == 0) {
                        Log.e("AQUI ASIGNAR PRESTAMO ID UNO ", solicitudesRen.get(0).getNombre());
                        solicitudesRen.get(0).setPrestamoId(row.getInt(1));
                        solicitudRenDao.updatePrestamo(solicitudesRen.get(0));
                    }
                } else if (solicitudesRen.size() > 1) {
                    for (int i = 0; i < solicitudesRen.size(); i++) {
                        Log.e("AQUI ENCONTRADO ", String.valueOf(solicitudesRen.get(i).getPrestamoId()));

                        if (solicitudesRen.get(i).getPrestamoId() == null || solicitudesRen.get(i).getPrestamoId() == 0) {
                            Log.e("AQUI ASIGNAR PRESTAMO ID DOS ", solicitudesRen.get(i).getNombre());

                            PrestamoToRenovar prestamoToRenovarTemp = prestamoToRenovarDao.findByClienteNombreAndPosition(row.getString(5), i);
                            if(prestamoToRenovarTemp != null)
                            {
                                solicitudesRen.get(i).setPrestamoId(prestamoToRenovarTemp.getPrestamoId());
                                solicitudRenDao.updatePrestamo(solicitudesRen.get(i));
                            }

                        }
                    }
                }

                row.moveToNext();
            }
        }

        row.close();

        //SE GUARDAN LOS QUE NO HAN SIDO SINCRONIZADOS
        sql = "SELECT _id, prestamo_id, cliente_id, tipo_prestamo, grupo_id, cliente_nombre, fecha_vencimiento  FROM " + TBL_PRESTAMOS_TO_RENOVAR + " WHERE descargado = ?";
        row = db.rawQuery(sql, new String[]{"0"});

        if (row.getCount() > 0){
            row.moveToFirst();

            for (int i = 0; i < row.getCount(); i++){
                if (row.getInt(4) == 1)
                    new RegistrarDatosRenovacion().
                            execute(ctx, row.getString(0), row.getString(1), row.getString(2), row.getString(6));
                else
                    new RegistrarDatosRenovacionGpo().
                            execute(ctx, row.getString(0), row.getString(4), row.getString(5), row.getString(6));
                row.moveToNext();
            }
        }
        row.close();

    }

    private void GetDatosRenovarForce(Context ctx){
        final DBhelper dBhelper = new DBhelper(ctx);
        final SQLiteDatabase db = dBhelper.getWritableDatabase();

        //SE GUARDAN LOS QUE YA SE SINCRONIZAR PERO DE FORMA PARCIAL
        String sqlParcial = "SELECT _id, prestamo_id, cliente_id, tipo_prestamo, grupo_id, cliente_nombre, fecha_vencimiento  FROM " + TBL_PRESTAMOS_TO_RENOVAR + " WHERE descargado = ?";
        Cursor rowParcial = db.rawQuery(sqlParcial, new String[]{"1"});

        if (rowParcial.getCount() > 0){
            rowParcial.moveToFirst();
            Log.e("AQUI", rowParcial.getString(4));
            for (int i = 0; i < rowParcial.getCount(); i++){
                if (rowParcial.getInt(4) > 1)
                    new RegistrarDatosRenovacionGpoForce().
                            execute(ctx, rowParcial.getString(0), rowParcial.getString(4), rowParcial.getString(5), rowParcial.getString(6));
                rowParcial.moveToNext();
            }
        }
        rowParcial.close();

    }

    public void SendConsultaCC(Context ctx, boolean showDG){

        final AlertDialog loading = Popups.showLoadingDialog(ctx, R.string.please_wait, R.string.loading_info);
        loading.setCancelable(false);

        //if ((!((Activity) ctx).isFinishing())) {
        if (showDG)
            loading.show();
        //}
        final SessionManager session = new SessionManager(ctx);

        Long suc = 0L;
        Integer usuario = 0;
        String token = "";
        try {
            JSONObject jso = session.getSucursales().getJSONObject(0);
            suc = jso.getLong("id");
            usuario=Integer.parseInt(session.getUser().get(9));
            token=session.getUser().get(7);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final DBhelper dBhelper = new DBhelper(ctx);
        final SQLiteDatabase db = dBhelper.getWritableDatabase();
        String sql = "SELECT * FROM " + TBL_CONSULTA_CC + " WHERE estatus = ?  order by _id  " ;
        Cursor row = db.rawQuery(sql, new String[]{"0"});
        Integer iTotalRows = row.getCount();
        SendByOneCirculo( ctx,0,iTotalRows,db,suc,usuario,token );
        loading.dismiss();

    }

    private void SendByOneCirculo(Context ctx,Integer iOffSet, Integer iTotalRows,SQLiteDatabase db,Long suc,Integer user, String token) {
       String id ="";
        if (iOffSet<=iTotalRows){
        String sql = "SELECT * FROM " + TBL_CONSULTA_CC + " WHERE estatus = ? order by _id  limit 1 ";
        Cursor row = db.rawQuery(sql, new String[]{"0"});
        if (row.getCount() > 0){
            row.moveToFirst();
            for (int i = 0; i < row.getCount(); i++) {
                id = row.getString(0);
                System.out.println("ID: " + id + " OFFSET: " + iOffSet);
                ConsultaCC cc = new ConsultaCC();
                cc.setSucursal(suc);
                cc.setAsesor_id(user);
                cc.setUsuarioId(user);
                cc.setOrigen("MOVIL");
                cc.setApPaterno(row.getString(5));
                if (!row.getString(6).isEmpty()) cc.setApMaterno(row.getString(6));
                else cc.setApMaterno(" ");
                cc.setPrimerNombre(row.getString(3));
                if (!row.getString(4).isEmpty()) {
                    cc.setSegundoNombre(row.getString(4));
                } else {
                    cc.setSegundoNombre(" ");
                }
                cc.setMontoSolicitado(row.getInt(2));
                cc.setProducto(row.getString(1));
                cc.setFechaNac(row.getString(7));
                if (!row.getString(10).isEmpty()) {
                    cc.setCurp(row.getString(10));
                }
                cc.setRfc(row.getString(11));
                cc.setNacionalidad("MX");
                ConsultaCC.DomicilioPeticion dom = new ConsultaCC.DomicilioPeticion();
                dom.setDireccion(row.getString(12));
                dom.setColoniaPoblacion(row.getString(13));
                dom.setDelegacionMunicipio(row.getString(14));
                dom.setCiudad(row.getString(15));
                dom.setEstado(Miscellaneous.GetCodigoEstado(row.getString(16)));
                dom.setCP(row.getString(17));
                cc.setDomPeticion(dom);
                //Toast.makeText(ctx, "Comienza Consulta", Toast.LENGTH_SHORT).show();
                ManagerInterface api = new RetrofitClient().generalRF(CONTROLLER_API, ctx).create(ManagerInterface.class);
                Call<MResConsultaCC> call = api.setConsultaCC("Bearer " + token, cc);
                String finalId = id;
                call.enqueue(new Callback<MResConsultaCC>() {
                    @Override
                    public void onResponse(Call<MResConsultaCC> call, Response<MResConsultaCC> response) {
                        MResConsultaCC r = response.body();
                        ContentValues cv = new ContentValues();
                        switch (response.code()) {
                            case 200:
                                if (r.getOk() != null) {
                                    int credAbierto = 0;
                                    int credCerrado = 0;
                                    float saldVencido = 0;
                                    float saldActual = 0;
                                    Double peorPago = 0.0;
                                    for (MResConsultaCC.Credito item : r.getOk().getRes().getCreditos()) {
                                        if (!Miscellaneous.validStr(item.getFechaCierreCuenta()).isEmpty()) {
                                            credCerrado += 1;
                                        } else {
                                            credAbierto += 1;

                                            saldVencido += item.getSaldoVencido();

                                            saldActual += item.getSaldoActual();

                                            if (item.getPeorAtraso() != null) {
                                                if (item.getPeorAtraso() > peorPago) {
                                                    peorPago = item.getPeorAtraso();
                                                }
                                            }
                                        }
                                    }

                                    cv.put("saldo_actual", String.valueOf(saldActual));
                                    cv.put("saldo_vencido", String.valueOf(saldVencido));
                                    cv.put("peor_pago", String.valueOf(peorPago));
                                    cv.put("creditos_abiertos", String.valueOf(credAbierto));
                                    cv.put("credito_cerrados", String.valueOf(credCerrado));
                                    cv.put("preautorizacion", String.valueOf(r.getOk().getPreautorizacion()));
                                    cv.put("estatus", String.valueOf(response.code()));

                                    db.update(TBL_CONSULTA_CC, cv, "_id = ?", new String[]{finalId});

                                }
                                break;
                            case 202:
                                if (r.getOk() != null) {


                                    cv.put("errores", "" + r.getOk().getErr().getErrores().get(0).getMensaje());
                                    cv.put("estatus", String.valueOf(response.code()));
                                    cv.put("fecha_envio", Miscellaneous.ObtenerFecha(TIMESTAMP));
                                   db.update(TBL_CONSULTA_CC, cv, "_id = ?", new String[]{finalId});
                                }
                                break;
                        }
                        SendByOneCirculo(ctx, iOffSet + 1, iTotalRows, db, suc, user, token);

                    }

                    @Override
                    public void onFailure(Call<MResConsultaCC> call, Throwable t) {
                        Log.e("FALLO", " offset: " + iOffSet);
                        SendByOneCirculo(ctx, iOffSet + 1, iTotalRows, db, suc, user, token);
                    }
                });


            }

        }
            row.close();
       //
        }

    }

    //============================= TAREAS ASINCRONAS  =============================================

    public class  RegistrarDatosRenovacion extends AsyncTask<Object, Void, String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Object... obj) {
            final Context ctx = (Context)obj[0];
            final DBhelper dBhelper = new DBhelper(ctx);
            final SQLiteDatabase db = dBhelper.getWritableDatabase();
            final SessionManager session = new SessionManager(ctx);
            String _id = (String) obj[1];
            final String prestamoId = (String) obj[2];
            final String clienteId = (String) obj[3];
            final String fechaVencimiento = (String) obj[4];

            Log.e("AQUI: ", String.valueOf(prestamoId));
            Log.e("AQUI: ", String.valueOf(clienteId));
            ManagerInterface api = new RetrofitClient().generalRF(CONTROLLER_MOVIL, ctx).create(ManagerInterface.class);

            Call<MRenovacion> call = api.getPrestamoRenovar(
                                            prestamoId,
                                            clienteId,
                                            "Bearer "+ session.getUser().get(7));

            call.enqueue(new Callback<MRenovacion>() {
                @Override
                public void onResponse(Call<MRenovacion> call, Response<MRenovacion> response) {
                    switch (response.code()){
                        case 200:
                            MRenovacion item = response.body();

                            int tipoSolicitud = 2;

                            /*SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                            Date fechaInicial = null;
                            Date fechaFinal = null;
                            try {
                                fechaInicial = dateFormat.parse(fechaVencimiento);
                                fechaFinal=dateFormat.parse(Miscellaneous.ObtenerFecha(FECHA).toLowerCase());

                                int dias = (int) ((fechaFinal.getTime()-fechaInicial.getTime())/86400000);

                                if (dias >= 364)
                                    tipoSolicitud = 1;
                                else
                                    tipoSolicitud = 2;
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }*/


                            //int dias=(int) ((fechaFinal.getTime()-fechaInicial.getTime())/86400000);

                            long id = 0;
                            long id_cliente = 0;
                            long id_direccion_cli = 0;
                            long id_direccion_cony = 0;
                            long id_direccion_neg = 0;
                            long id_direccion_aval = 0;
                            long id_direccion_ref = 0;

                            String nombre = (Miscellaneous.validStr(item.getCliente().getNombre())+" "+
                                    Miscellaneous.validStr(item.getCliente().getPaterno())+" "+
                                    Miscellaneous.validStr(item.getCliente().getMaterno())).trim().toUpperCase();

                            HashMap<Integer, String> params = new HashMap<>();
                            params.put(0, ctx.getString(R.string.vol_solicitud));                               //VOL SOLICITUD
                            params.put(1,session.getUser().get(9));                 //USUARIO ID
                            params.put(2,"1");                                      //TIPO SOLICITUD
                            params.put(3,"0");                                      //ID ORIGINACION
                            params.put(4, nombre);                                  //NOMBRE
                            params.put(5, Miscellaneous.ObtenerFecha(TIMESTAMP));   //FECHA INICIO
                            params.put(6,"");                                       //FECHA TERMINO
                            params.put(7,"");                                       //FECHA ENVIO
                            params.put(8, Miscellaneous.ObtenerFecha(TIMESTAMP));   //FECHA DISPOSITIVO
                            params.put(9, "");                                      //FECHA GUARDADO
                            params.put(10, "0");                                    //ESTATUS
                            params.put(11, String.valueOf(item.getPrestamo().getPrestamodId())); //prestamo_id

                            id = dBhelper.saveSolicitudes(db, params, tipoSolicitud);

                            //Inserta registro de datos del credito
                            params = new HashMap<>();
                            params.put(0,String.valueOf(id));                                       //ID SOLICITUD
                            params.put(1,"");                                                       //PLAZO
                            params.put(2,"");                                                       //PERIODICIDAD
                            params.put(3,"");                                                       //FECHA DESEMBOLSO
                            params.put(4,"");                                                       //DIA DESEMBOLSO
                            params.put(5,"");                                                       //HORA VISITA
                            params.put(6,"");                                                       //MONTO PRESTAMO
                            params.put(7,String.valueOf(Miscellaneous.validInt(item.getPrestamo().getNumCiclo())));         //CICLO
                            params.put(8, String.valueOf(item.getPrestamo().getMonto()));           //CREDITO ANTERIOR
                            params.put(9,"");                                                       //COMPORTAMIENTO PAGO
                            params.put(10,String.valueOf(Miscellaneous.validInt(item.getPrestamo().getClienteId())));       //NUM CLIENTE
                            params.put(11,"");                                                      //OBSERVACIONES
                            params.put(12,"");                                                      //DESTINO
                            params.put(13,Miscellaneous.GetRiesgo(Miscellaneous.validInt(item.getPrestamo().getNivelRiesgo())));//CLASIFICACION RIESGO
                            params.put(14,"0");                                                     //ESTATUS COMPLETO

                            dBhelper.saveDatosCreditoRen(db, params);

                            //Inserta registro de direccion del cliente
                            params = new HashMap<>();
                            params.put(0, "CLIENTE");                                                              //TIPO DIRECCION
                            params.put(1, Miscellaneous.validStr(item.getCliente().getLatitud()));                 //LATITUD
                            params.put(2, Miscellaneous.validStr(item.getCliente().getLongitud()));                //LONGITUD
                            params.put(3, Miscellaneous.validStr(item.getCliente().getCalle()));                   //CALLE
                            params.put(4, Miscellaneous.validStr(item.getCliente().getNoExterior()));              //NO EXTERIOR
                            params.put(5, Miscellaneous.validStr(item.getCliente().getNoInterior()));              //NO INTERIOR
                            params.put(6, Miscellaneous.validStr(item.getCliente().getNoManzana()));               //MANZANA
                            params.put(7, Miscellaneous.validStr(item.getCliente().getNoLote()));                  //LOTE
                            params.put(8, String.valueOf(Miscellaneous.validInt(item.getCliente().getCodigoPostal())));//CP
                            params.put(9, Miscellaneous.GetColonia(ctx, Miscellaneous.validInt(item.getCliente().getColoniaId())));//COLONIA
                            params.put(10, Miscellaneous.validStr(item.getCliente().getCiudad()));                 //CIUDAD
                            params.put(11, Miscellaneous.GetLocalidad(ctx, Miscellaneous.validInt(item.getCliente().getLocalidadId())));//LOCALIDAD
                            params.put(12, Miscellaneous.GetMunicipio(ctx, Miscellaneous.validInt(item.getCliente().getMunicipioId())));//MUNICIPIO
                            params.put(13, Miscellaneous.GetEstado(ctx, Miscellaneous.validInt(item.getCliente().getEstadoId())));//ESTADO

                            id_direccion_cli = dBhelper.saveDirecciones(db, params, 2);

                            //Inserta registro de datos del cliente

                            params = new HashMap<>();
                            params.put(0, String.valueOf(id));                                      //ID SOLICITUD
                            params.put(1, Miscellaneous.validStr(item.getCliente().getNombre().trim().toUpperCase()));      //NOMBRE
                            params.put(2, Miscellaneous.validStr(item.getCliente().getPaterno().trim().toUpperCase()));     //PATERNO
                            params.put(3, Miscellaneous.validStr(item.getCliente().getMaterno().trim().toUpperCase()));     //MATERNO
                            params.put(4, Miscellaneous.validStr(item.getCliente().getFechaNacimiento()));                  //FECHA NACIMIENTO
                            params.put(5, Miscellaneous.GetEdad(item.getCliente().getFechaNacimiento()));//EDAD
                            params.put(6, String.valueOf(item.getCliente().getGenero()));           //GENERO
                            params.put(7, Miscellaneous.GetEstado(ctx, Miscellaneous.validInt(item.getCliente().getEstadoNacimiento())));//ESTADO NACIMIENTO
                            params.put(8, Miscellaneous.validStr(Miscellaneous.validStr(item.getCliente().getRfc())));     //RFC
                            params.put(9, Miscellaneous.validStr(Miscellaneous.validStr(item.getCliente().getCurp())));     //CURP
                            params.put(10, "");                                                     //CURP DIGITO VERI
                            params.put(11, Miscellaneous.GetOcupacion(ctx, Miscellaneous.validInt(item.getCliente().getOcupacionId()))); //OCUPACION
                            params.put(12, Miscellaneous.GetSector(ctx, Miscellaneous.validInt(item.getCliente().getOcupacionId())));   //ACTIVIDAD ECONOMICA
                            params.put(13, Miscellaneous.GetTipoIdentificacion(ctx, item.getCliente().getIdentificacionTipoId()));                                                     //TIPO IDENTIFICACION
                            params.put(14, Miscellaneous.validStr(item.getCliente().getNoIdentificacion()));//NUM IDENTIFICACION
                            params.put(15, Miscellaneous.GetEstudio(ctx, item.getCliente().getEstudioNivelId()));   //NIVEL ESTUDIO
                            params.put(16, Miscellaneous.GetEstadoCivil(ctx, item.getCliente().getEstadoCivilId()));//ESTATUS CIVIL
                            params.put(17, String.valueOf(item.getCliente().getRegimenBienId()));                   //BIENES
                            params.put(18, Miscellaneous.GetViviendaTipo(ctx, item.getCliente().getViviendaTipoId()));  //TIPO VIVIENDA
                            params.put(19, "");                                                     //PARENTESCO
                            params.put(20, "");                                                     //OTRO TIPO VIVIENDA
                            params.put(21, String.valueOf(id_direccion_cli));                           //DIRECCION ID
                            params.put(22, Miscellaneous.validStr(item.getCliente().getTelCasa())); //TEL CASA
                            params.put(23, Miscellaneous.validStr(item.getCliente().getTelCelular())); //TEL CELULAR
                            params.put(24, Miscellaneous.validStr(item.getCliente().getTelMensaje())); //TEL MENSAJES
                            params.put(25, Miscellaneous.validStr(item.getCliente().getTelTrabajo())); //TEL TRABAJO
                            if (!Miscellaneous.validStr(item.getCliente().getTiempoVivirSitio()).isEmpty())
                                    params.put(26, item.getCliente().getTiempoVivirSitio());
                            else
                                params.put(26, "0");                                                //TIEMPO VIVIR SITIO
                            params.put(27, Miscellaneous.validStr(item.getCliente().getDependientesEconomico()));  //DEPENDIENTES
                            params.put(28, Miscellaneous.GetMedioContacto(ctx, item.getCliente().getMedioContactoId()));                                                     //MEDIO CONTACTO
                            params.put(29, Miscellaneous.validStr(item.getCliente().getEstadoCuenta()));//ESTADO CUENTA
                            params.put(30, Miscellaneous.validStr(item.getCliente().getEmail()));   //EMAIL
                            params.put(31, "");                                                     //FOTO FACHADA
                            params.put(32, Miscellaneous.validStr(item.getCliente().getReferencia()));//REF DOMICILIARIA
                            params.put(33, "");                                                     //FIRMA
                            params.put(34, "0");                                                    //ESTATUS RECHAZO
                            params.put(35, "");                                                     //COMENTARIO RECHAZO
                            params.put(36, "0");                                                    //ESTATUS COMPLETO

                            id_cliente = dBhelper.saveDatosPersonales(db, params, 2);

                            //Inserta registro de direccion del cliente
                            params = new HashMap<>();

                            params.put(0, "CONYUGE");                                               //TIPO DIRECCION
                            params.put(1, Miscellaneous.validStr(item.getConyuge().getLatitud()));  //LATITUD
                            params.put(2, Miscellaneous.validStr(item.getConyuge().getLongitud())); //LONGITUD
                            params.put(3, Miscellaneous.validStr(item.getConyuge().getCalle()));    //CALLE
                            params.put(4, Miscellaneous.validStr(item.getConyuge().getNoExterior()));//NO EXTERIOR
                            params.put(5, Miscellaneous.validStr(item.getConyuge().getNoInterior()));//NO INTERIOR
                            params.put(6, Miscellaneous.validStr(item.getConyuge().getNoManzana()));//MANZANA
                            params.put(7, Miscellaneous.validStr(item.getConyuge().getNoLote()));   //LOTE
                            params.put(8, (item.getConyuge().getCodigoPostal() == 0)?"":String.valueOf(item.getConyuge().getCodigoPostal())); //CP
                            params.put(9, Miscellaneous.GetColonia(ctx, Miscellaneous.validInt(item.getConyuge().getColoniaId()))); //COLONIA
                            params.put(10, Miscellaneous.validStr(item.getConyuge().getCiudad()));  //CIUDAD
                            params.put(11, Miscellaneous.GetLocalidad(ctx, Miscellaneous.validInt(item.getConyuge().getLocalidadId()))); //LOCALIDAD
                            params.put(12, Miscellaneous.GetMunicipio(ctx, Miscellaneous.validInt(item.getConyuge().getMunicipioId()))); //MUNICIPIO
                            params.put(13, Miscellaneous.GetEstado(ctx, Miscellaneous.validInt(item.getConyuge().getEstadoId()))); //ESTADO

                            id_direccion_cony = dBhelper.saveDirecciones(db, params, 2);

                            //Inserta registro de datos conyuge
                            params = new HashMap<>();
                            params.put(0, String.valueOf(id));                      //ID SOLICITUD
                            params.put(1, Miscellaneous.validStr(item.getConyuge().getNombre()).toUpperCase()); //NOMBRE
                            params.put(2, Miscellaneous.validStr(item.getConyuge().getPaterno()).toUpperCase());//PATERNO
                            params.put(3, Miscellaneous.validStr(item.getConyuge().getMaterno()).toUpperCase());//MATERNO
                            params.put(4, Miscellaneous.validStr(item.getConyuge().getNacionalidad()));         //NACIONALIDAD
                            params.put(5, Miscellaneous.GetOcupacion(ctx, Miscellaneous.validInt(item.getConyuge().getOcupacionId()))); //OCUPACION
                            params.put(6, String.valueOf(id_direccion_cony));                                   //DIRECCION ID
                            params.put(7, String.valueOf(item.getConyuge().getIngresoMensual()));               //ING MENSUAL
                            params.put(8, String.valueOf(item.getConyuge().getGastoMensual()));                 //GASTO MENSUAL
                            params.put(9, Miscellaneous.validStr(item.getConyuge().getTelCasa()));              //TEL CASA
                            params.put(10, Miscellaneous.validStr(item.getConyuge().getTelCelular()));          //TEL CELULAR
                            params.put(11, "0");                                                                //ESTATUS COMPLETADO

                            dBhelper.saveDatosConyuge(db, params, 2);

                            //Inserta registro de datos economicos
                            params = new HashMap<>();
                            params.put(0, String.valueOf(id));                      //ID SOLICITUD
                            params.put(1, "");                                      //PROPIEDADES
                            params.put(2, "");                                      //VALOR APROXIMADO
                            params.put(3, "");                                      //UBICACION
                            params.put(4, "");                                      //INGRESO
                            params.put(5, "0");                                     //ESTATUS COMPLETADO

                            dBhelper.saveDatosEconomicos(db, params, 2);

                            //Inserta registro de direccion del negocio
                            params = new HashMap<>();
                            params.put(0, "NEGOCIO");                                               //TIPO DIRECCION
                            params.put(1, Miscellaneous.validStr(item.getNegocio().getLatitud()));  //LATITUD
                            params.put(2, Miscellaneous.validStr(item.getNegocio().getLongitud())); //LONGITUD
                            params.put(3, Miscellaneous.validStr(item.getNegocio().getCalle()).toUpperCase());    //CALLE
                            params.put(4, Miscellaneous.validStr(item.getNegocio().getNoExterior())); //NO EXTERIOR
                            params.put(5, Miscellaneous.validStr(item.getNegocio().getNoInterior())); //NO INTERIOR
                            params.put(6, Miscellaneous.validStr(item.getNegocio().getNoManzana()));  //MANZANA
                            params.put(7, Miscellaneous.validStr(item.getNegocio().getNoLote()));     //LOTE
                            params.put(8, (item.getNegocio().getCodigoPostal() == 0)?"":String.valueOf(item.getNegocio().getCodigoPostal()));                                                     //CP
                            params.put(9, Miscellaneous.GetColonia(ctx, item.getNegocio().getColoniaId())); //COLONIA
                            params.put(10, item.getNegocio().getCiudad());                           //CIUDAD
                            params.put(11, Miscellaneous.GetLocalidad(ctx, item.getNegocio().getLocalidadId()));  //LOCALIDAD
                            params.put(12, Miscellaneous.GetMunicipio(ctx, item.getNegocio().getMunicipioId()));  //MUNICIPIO
                            params.put(13, Miscellaneous.GetEstado(ctx, item.getNegocio().getEstadoId())); //ESTADO

                            id_direccion_neg = dBhelper.saveDirecciones(db, params, 2);

                            //Inserta registro de negocio
                            params = new HashMap<>();
                            params.put(0, String.valueOf(id));                  //ID SOLICITUD
                            params.put(1, Miscellaneous.validStr(item.getNegocio().getNombre()));   //NOMBRE
                            params.put(2, String.valueOf(id_direccion_neg));        //DIRECCION ID
                            params.put(3, Miscellaneous.GetOcupacion(ctx, item.getNegocio().getOcupacionId())); //OCUPACION
                            params.put(4, Miscellaneous.GetSector(ctx, item.getNegocio().getOcupacionId()));    //ACTIVIDAD ECONOMICA
                            params.put(5, Miscellaneous.GetDestinoCredito(ctx, item.getNegocio().getDestinoCreditoId())); //DESTINO CREDITO
                            params.put(6, Miscellaneous.validStr(item.getNegocio().getOtroDestinoCredito())); //OTRO DESTINO
                            params.put(7, String.valueOf(item.getNegocio().getAntiguedad()));       //ANTIGUEDAD
                            params.put(8, "");   //ING MENSUAL
                            params.put(9, "");    //ING OTROS
                            params.put(10, "");    //GASTO SEMANAL
                            params.put(11, "");       //GASTO AGUA
                            params.put(12, "");        //GASTO LUZ
                            params.put(13, "");   //GASTO TELEFONO
                            params.put(14, "");      //GASTO RENTA
                            params.put(15, "");      //GASTO OTROS
                            params.put(16, "");   //CAPACIDAD PAGO
                            params.put(17, "");                                  //MEDIO PAGO
                            params.put(18,"");                                  //OTRO MEDIO PAGO
                            params.put(19,"");                                  //MONTO MAXIMO
                            params.put(20,"0");                                 //NUM OPERACION MENSUALES
                            params.put(21,"0");                                 //NUM OPERACION EFECTIVO
                            params.put(22,"");                                  //DIAS VENTA
                            params.put(23,"");                                  //FOTO FACHADA
                            params.put(24,"");                                  //REF DOMICILIARIA
                            params.put(25,"0");                                 //ESTATUS COMPLETADO
                            params.put(26,"");                                  //COMENTARIO RECHAZO

                            dBhelper.saveDatosNegocio(db, params, 2);

                            //Inserta registro de direccion del aval
                            params = new HashMap<>();
                            params.put(0, "AVAL");                                                 //TIPO DIRECCION
                            params.put(1, Miscellaneous.validStr(item.getAval().getLatitud()));    //LATITUD
                            params.put(2, Miscellaneous.validStr(item.getAval().getLongitud()));   //LONGITUD
                            params.put(3, Miscellaneous.validStr(item.getAval().getCalle()));      //CALLE
                            params.put(4, Miscellaneous.validStr(item.getAval().getNoExterior())); //NO EXTERIOR
                            params.put(5, Miscellaneous.validStr(item.getAval().getNoInterior())); //NO INTERIOR
                            params.put(6, Miscellaneous.validStr(item.getAval().getNoManzana()));  //MANZANA
                            params.put(7, Miscellaneous.validStr(item.getAval().getNoLote()));     //LOTE
                            params.put(8, (item.getAval().getCodigoPostal() == 0)?"":String.valueOf(item.getAval().getCodigoPostal())); //CP
                            params.put(9, Miscellaneous.GetColonia(ctx, item.getAval().getColoniaId())); //COLONIA
                            params.put(10, Miscellaneous.validStr(item.getAval().getCiudad()));     //CIUDAD
                            params.put(11, Miscellaneous.GetLocalidad(ctx, item.getAval().getLocalidadId())); //LOCALIDAD
                            params.put(12, Miscellaneous.GetMunicipio(ctx, item.getAval().getMunicipioId())); //MUNICIPIO
                            params.put(13, Miscellaneous.GetEstado(ctx, item.getAval().getEstadoId())); //ESTADO

                            id_direccion_aval = dBhelper.saveDirecciones(db, params, 2);

                            //Inserta registro del aval
                            params = new HashMap<>();
                            params.put(0, String.valueOf(id));                  //ID SOLICITUD
                            params.put(1, Miscellaneous.validStr(item.getAval().getNombre()).toUpperCase());          //NOMBRE
                            params.put(2, Miscellaneous.validStr(item.getAval().getPaterno()).toUpperCase());         //PATERNO
                            params.put(3, Miscellaneous.validStr(item.getAval().getMaterno()).toUpperCase());         //MATERNO
                            params.put(4, Miscellaneous.validStr(item.getAval().getFechaNacimiento()));               //FECHA NACIMIENTO
                            params.put(5, String.valueOf(item.getAval().getEdad()));                                  //EDAD
                            params.put(6, String.valueOf(Miscellaneous.validInt(item.getAval().getGenero())));        //GENERO
                            params.put(7, Miscellaneous.GetEstado(ctx, Miscellaneous.validInt(item.getAval().getEstadoNacimientoId())));//ESTADO NACIMIENTO
                            params.put(8, Miscellaneous.validStr(item.getAval().getRfc()));                           //RFC
                            params.put(9, Miscellaneous.validStr(item.getAval().getCurp()));                          //CURP
                            params.put(10, "");                                                                       //CURP DIGITO
                            params.put(11, Miscellaneous.GetParentesco(ctx, Miscellaneous.validInt(item.getAval().getParentescoSolicitanteId()))); //PARENTESCO CLIENTE
                            params.put(12, Miscellaneous.GetTipoIdentificacion(ctx, Miscellaneous.validInt(item.getAval().getIdentificacionTipoId()))); //TIPO IDENTIFICACION
                            params.put(13, Miscellaneous.validStr(item.getAval().getNoIdentificacion()));             //NUM IDENTIFICACION
                            params.put(14, Miscellaneous.GetOcupacion(ctx, Miscellaneous.validInt(item.getAval().getOcupacionId())));         //OCUPACION
                            params.put(15, Miscellaneous.GetSector(ctx, Miscellaneous.validInt(item.getAval().getOcupacionId())));            //ACTIVIDAD ECONOMICA
                            params.put(16, "");                                                                       //DESTINO CREDITO
                            params.put(17, "");                                                                       //OTRO DESTINO
                            params.put(18, String.valueOf(id_direccion_aval));                                        //DIRECCION ID
                            params.put(19, Miscellaneous.GetViviendaTipo(ctx, Miscellaneous.validInt(item.getAval().getViviendaTipoId())));   //TIPO VIVIENDA
                            params.put(20, Miscellaneous.validStr(item.getAval().getNombreTitular()));                //NOMBRE TITULAR
                            params.put(21, Miscellaneous.GetParentesco(ctx, Miscellaneous.validInt(item.getAval().getParentescoTitularId()))); //PARENTESCO
                            params.put(22, Miscellaneous.validStr(item.getAval().getCaracteristicasDomicilio()));     //CARACTERISTICAS DOMICILIO
                            params.put(23, String.valueOf(Miscellaneous.validInt(item.getAval().getAntiguedad())));                           //ANTIGUEDAD
                            params.put(24, (item.getAval().getTieneNegocio() != null && item.getAval().getTieneNegocio())?"1":"0");                               //TIENE NEGOCIO
                            params.put(25, Miscellaneous.validStr(item.getAval().getNombreNegocio()));                //NOMBRE NEGOCIO
                            params.put(26, "");                                 //ING MENSUAL
                            params.put(27, "");                                 //ING OTROS
                            params.put(28, "");                                 //GASTO SEMANAL
                            params.put(29, "");                                 //GASTO AGUA
                            params.put(30, "");                                 //GASTO LUZ
                            params.put(31, "");                                 //GASTO TELEFONO
                            params.put(32, "");                                 //GASTO RENTA
                            params.put(33, "");                                 //GASTO OTROS
                            params.put(34, "");                                 //CAPACIDAD PAGOS
                            params.put(35, "");                                 //MEDIO PAGO
                            params.put(36, "");                                 //OTRO MEDIO PAGO
                            params.put(37, "");                                 //MONTO MAXIMO
                            params.put(38, Miscellaneous.validStr(item.getAval().getHoraLocalizacion()));            //HORARIO LOCALIZACION
                            params.put(39, "");                                 //ACTIVOS OBSERVABLES
                            params.put(40, Miscellaneous.validStr(item.getAval().getTelCasa()));                     //TEL CASA
                            params.put(41, Miscellaneous.validStr(item.getAval().getTelCelular()));                  //TEL CELULAR
                            params.put(42, Miscellaneous.validStr(item.getAval().getTelMensaje()));                  //TEL MENSAJES
                            params.put(43, Miscellaneous.validStr(item.getAval().getTelTrabajo()));                  //TEL TRABAJO
                            params.put(44, Miscellaneous.validStr(item.getAval().getEmail()));                       //EMAIL
                            params.put(45, "");                                 //FOTO FACHADA
                            params.put(46, Miscellaneous.validStr(item.getAval().getReferencia()));                 //REF DOMICILIARIA
                            params.put(47, "");                                 //FIRMA
                            params.put(48, "0");                                //ESTATUS RECHAZO
                            params.put(49, "");                                 //COMENTARIO RECHAZO
                            params.put(50, "0");                                //ESTATUS RECHAZO

                            dBhelper.saveDatosAval(db, params, 2);

                            //Inserta registro de direccion del referencia
                            params = new HashMap<>();
                            params.put(0, "REFERENCIA");                                                 //TIPO DIRECCION
                            params.put(1, "");                                                           //LATITUD
                            params.put(2, "");                                                           //LONGITUD
                            params.put(3, Miscellaneous.validStr(item.getReferencia().getCalle()));      //CALLE
                            params.put(4, Miscellaneous.validStr(item.getReferencia().getNoExterior())); //NO EXTERIOR
                            params.put(5, Miscellaneous.validStr(item.getReferencia().getNoInterior())); //NO INTERIOR
                            params.put(6, Miscellaneous.validStr(item.getReferencia().getNoManzana()));  //MANZANA
                            params.put(7, Miscellaneous.validStr(item.getReferencia().getNoLote()));     //LOTE
                            params.put(8, (item.getReferencia().getCodigoPostal() == 0)?"":String.valueOf(item.getReferencia().getCodigoPostal()));                                                     //CP
                            params.put(9, Miscellaneous.GetColonia(ctx, Miscellaneous.validInt(item.getReferencia().getColoniaId()))); //COLONIA
                            params.put(10, Miscellaneous.validStr(item.getReferencia().getCiudad()));    //CIUDAD
                            params.put(11, Miscellaneous.GetLocalidad(ctx, Miscellaneous.validInt(item.getReferencia().getLocalidadId()))); //LOCALIDAD
                            params.put(12, Miscellaneous.GetMunicipio(ctx, Miscellaneous.validInt(item.getReferencia().getMunicipioId())));                                                    //MUNICIPIO
                            params.put(13, Miscellaneous.GetEstado(ctx, Miscellaneous.validInt(item.getReferencia().getEstadoId())));                                                    //ESTADO

                            id_direccion_ref = dBhelper.saveDirecciones(db, params, 2);

                            //Inserta registro de referencia
                            params = new HashMap<>();
                            params.put(0, String.valueOf(id));                                                     //ID SOLICITUD
                            params.put(1, Miscellaneous.validStr(item.getReferencia().getNombre().toUpperCase())); //NOMBRE
                            params.put(2, Miscellaneous.validStr(item.getReferencia().getPaterno().toUpperCase()));//PATERNO
                            params.put(3, Miscellaneous.validStr(item.getReferencia().getMaterno().toUpperCase()));//MATERNO
                            params.put(4, Miscellaneous.validStr(item.getReferencia().getFechaNacimiento()));      //FECHA NACIMIENTO
                            params.put(5, String.valueOf(id_direccion_ref));                                       //DIRECCION ID
                            params.put(6, Miscellaneous.validStr(item.getReferencia().getTelCelular()));           //TEL_CELULAR
                            params.put(7, "0");                                                                    //ESTATUS COMPLETADO
                            params.put(8, "");                                                                     //COMENTARIO RECHAZO

                            dBhelper.saveReferencia(db, params, 2);

                            //Inserta registro de croquis
                            params = new HashMap<>();
                            params.put(0, String.valueOf(id));                  //ID SOLICITUD
                            params.put(1, "");                                  //CALLE PRINCIPAL
                            params.put(2, "");                                  //LATERAL UNO
                            params.put(3, "");                                  //LATERAL DOS
                            params.put(4, "");                                  //CALLE TRASERA
                            params.put(5, "");                                  //REFERENCIAS
                            params.put(6, "0");                                 //ESTATUS COMPLETADO
                            params.put(7, "");                                  //COMENTARIO RECHAZO

                            dBhelper.saveCroquisInd(db, params, 2);

                            //Inserta registro de politicas
                            params = new HashMap<>();
                            params.put(0, String.valueOf(id));                  //ID SOLICITUD
                            params.put(1, "0");                                 //PROPIERATIO REAL
                            params.put(2, "0");                                 //PROVEEDOR RECURSOS
                            params.put(3, "0");                                 //PERSONA POLITICA
                            params.put(4, "0");                                 //ESTATUS COMPLETADO

                            dBhelper.savePoliticasInd(db, params, 2);

                            //Inseta registro de documentos
                            params = new HashMap<>();
                            params.put(0,String.valueOf(id));       //ID SOLICITUD
                            params.put(1, "");                      //INE FRONTAL
                            params.put(2, "");                      //INE REVERSO
                            params.put(3, "");                      //CURP
                            params.put(4, "");                      //COMPROBANTE
                            params.put(5, "");                      //CODIGO BARRAS
                            params.put(6, "");                      //FIRMA ASESOR
                            params.put(7, "0");                     //ESTATUS COMPLETADO

                            dBhelper.saveDocumentosClientes(db, params, 2);

                            ContentValues cv = new ContentValues();
                            cv.put("descargado", 1);
                            db.update(TBL_PRESTAMOS_TO_RENOVAR, cv, "prestamo_id = ? AND cliente_id = ?", new String[]{prestamoId, clienteId});

                            break;
                        default:
                            try {
                                Log.e("ERROR " + response.code(), response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            Log.e("ERROR " + response.code(), response.message());
                            break;
                    }
                }

                @Override
                public void onFailure(Call<MRenovacion> call, Throwable t) {

                }
            });
            return "";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }

    public class  RegistrarDatosRenovacionGpo extends AsyncTask<Object, Void, String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Object... obj) {
            final Context ctx = (Context)obj[0];
            final DBhelper dBhelper = new DBhelper(ctx);
            final SQLiteDatabase db = dBhelper.getWritableDatabase();
            final SessionManager session = new SessionManager(ctx);
            String _id = (String) obj[1];
            final String grupoId = (String) obj[2];
            final String nombreGpo = (String) obj[3];
            final String fechaVencimiento = (String) obj[4];

            ManagerInterface api = new RetrofitClient().generalRF(CONTROLLER_MOVIL, ctx).create(ManagerInterface.class);

            Log.e("AQUI GRUPO0", grupoId);

            Call<MRenovacionGrupal> call = api.getPrestamoRenovarGpo(
                    grupoId,
                    "Bearer "+ session.getUser().get(7));

            call.enqueue(new Callback<MRenovacionGrupal>() {
                @Override
                public void onResponse(Call<MRenovacionGrupal> call, Response<MRenovacionGrupal> response) {
                    switch (response.code()){
                        case 200:
                            MRenovacionGrupal item = response.body();
                            long id_solicitud;
                            // Crea la solicitud de credito grupal
                            HashMap<Integer, String> params = new HashMap<>();
                            params.put(0, ctx.getString(R.string.vol_solicitud));   //VOL SOLICITUD
                            params.put(1,session.getUser().get(9));                 //USUARIO ID
                            params.put(2,"2");                                      //TIPO SOLICITUD
                            params.put(3,"0");                                      //ID ORIGINACION
                            params.put(4, nombreGpo);                               //NOMBRE
                            params.put(5, Miscellaneous.ObtenerFecha(TIMESTAMP));   //FECHA INICIO
                            params.put(6,"");                                       //FECHA TERMINO
                            params.put(7,"");                                       //FECHA ENVIO
                            params.put(8, Miscellaneous.ObtenerFecha(TIMESTAMP));   //FECHA DISPOSITIVO
                            params.put(9, "");                                      //FECHA GUARDADO
                            params.put(10, "0");                                    //ESTATUS
                            params.put(11, String.valueOf(item.getPrestamo().getPrestamodId())); //prestamo_id

                            id_solicitud = dBhelper.saveSolicitudes(db, params, 2);

                            //Inserta registro de datos del credito
                            long id_credito;
                            params = new HashMap<>();
                            params.put(0, String.valueOf(id_solicitud));
                            params.put(1, nombreGpo);
                            params.put(2, "");
                            params.put(3, "");
                            params.put(4, "");
                            params.put(5, "");
                            params.put(6, "");
                            params.put(7,"0");
                            params.put(8, "");
                            params.put(9, String.valueOf(item.getPrestamo().getNumCiclo()));
                            params.put(10, grupoId);

                            id_credito = dBhelper.saveDatosCreditoGpoRen(db, params, 1);

                            for (MRenovacionGrupal.Integrante integrante :item.getIntegrantes()){
                                MRenovacionGrupal.Cliente cliente = integrante.getCliente();
                                MRenovacionGrupal.Negocio negocio = integrante.getNegocio();
                                MRenovacionGrupal.Conyuge conyuge = integrante.getConyuge();

                                long id = 0;
                                //Inserta registro de integrante
                                params = new HashMap<>();
                                params.put(0, String.valueOf(id_credito));                              //ID CREDITO
                                params.put(1, String.valueOf(cliente.getTipoIntegrante()));             //CARGO
                                params.put(2, Miscellaneous.validStr(cliente.getNombre()).trim().toUpperCase()); //NOMBRE(S)
                                params.put(3, Miscellaneous.validStr(cliente.getPaterno()).trim().toUpperCase());//PATERNO
                                params.put(4, Miscellaneous.validStr(cliente.getMaterno()).trim().toUpperCase());//MATERNO
                                params.put(5, Miscellaneous.validStr(cliente.getFechaNacimiento().trim()));                            //FECHA NACIMIENTO
                                params.put(6, Miscellaneous.GetEdad(cliente.getFechaNacimiento()));     //EDAD
                                params.put(7, String.valueOf(cliente.getGenero()));                     //GENERO
                                params.put(8, Miscellaneous.GetEstado(ctx, cliente.getEstadoNacimiento())); //ESTADO NACIMIENTO
                                params.put(9, Miscellaneous.validStr(cliente.getRfc()));                //RFC
                                params.put(10, Miscellaneous.validStr(cliente.getCurp()));               //CURP
                                params.put(11, "");                                                     //CURP DIGITO VERI
                                params.put(12, Miscellaneous.GetTipoIdentificacion(ctx, cliente.getIdentificacionTipoId())); //TIPO IDENTIFICACION
                                params.put(13, Miscellaneous.validStr(cliente.getNoIdentificacion()));  //NO IDENTIFICACION
                                params.put(14, Miscellaneous.GetEstudio(ctx, cliente.getEstudioNivelId())); //NIVEL ESTUDIO
                                params.put(15, Miscellaneous.GetOcupacion(ctx, cliente.getOcupacionId()));  //OCUPACION
                                params.put(16, Miscellaneous.GetEstadoCivil(ctx, cliente.getEstadoCivilId())); //ESTADO CIVIL
                                params.put(17, String.valueOf(cliente.getRegimenBienId()));              //BIENES
                                params.put(18, "0");                                                    //ESTATUS RECHAZO
                                params.put(19, "");                                                     //COMENTARIO RECHAZO
                                params.put(20, "0");                                                    //ESTATUS COMPLETO
                                params.put(21, "0");                                                    //ID SOLICITUD INTEGRANTE
                                params.put(22, "0");                                                    //IS NUEVO
                                params.put(23, String.valueOf(integrante.getClienteId()));              //CLIENTE ID
                                params.put(24, String.valueOf(integrante.getCiclo()));
                                params.put(25, integrante.getMontoPrestamoAnterior());

                                id = dBhelper.saveIntegrantesGpoRen(db, params);

                                //Inserta registro de datos telefonicos
                                params = new HashMap<>();
                                params.put(0, String.valueOf(id));              //ID INTEGRANTE
                                params.put(1, Miscellaneous.validStr(cliente.getTelCasa()));            //TEL CASA
                                params.put(2, Miscellaneous.validStr(cliente.getTelCelular()));         //TEL CELULAR
                                params.put(3, Miscellaneous.validStr(cliente.getTelMensaje()));         //TEL MENSAJES
                                params.put(4, Miscellaneous.validStr(cliente.getTelTrabajo()));         //TEL TRABAJO
                                params.put(5, "0");                                                     //ESTATUS COMPLETADO

                                dBhelper.saveDatosTelefonicos(db, params, 2);

                                //Inserta registro de datos domicilio
                                params = new HashMap<>();
                                params.put(0, String.valueOf(id));                                          //ID INTEGRANTE
                                params.put(1, Miscellaneous.validStr(cliente.getLatitud()));                //LATITUD
                                params.put(2, Miscellaneous.validStr(cliente.getLongitud()));               //LONGITUD
                                params.put(3, Miscellaneous.validStr(cliente.getCalle()));                  //CALLE
                                params.put(4, Miscellaneous.validStr(cliente.getNoExterior()));             //NO_EXTERIOR
                                params.put(5, Miscellaneous.validStr(cliente.getNoInterior()));             //NO INTERIOR
                                params.put(6, Miscellaneous.validStr(cliente.getNoManzana()));              //MANZANA
                                params.put(7, Miscellaneous.validStr(cliente.getNoLote()));                 //LOTE
                                params.put(8, String.valueOf(Miscellaneous.validInt(cliente.getCodigoPostal()))); //CP
                                params.put(9, Miscellaneous.GetColonia(ctx, Miscellaneous.validInt(cliente.getColoniaId())));//COLONIA
                                params.put(10, Miscellaneous.validStr(cliente.getCiudad()));                //CIUDAD
                                params.put(11, Miscellaneous.GetLocalidad(ctx, Miscellaneous.validInt(cliente.getLocalidadId())));  //LOCALIDAD
                                params.put(12, Miscellaneous.GetMunicipio(ctx, Miscellaneous.validInt(cliente.getMunicipioId())));  //MUNICIPIO
                                params.put(13, Miscellaneous.GetEstado(ctx, Miscellaneous.validInt(cliente.getEstadoId())));//ESTADO
                                params.put(14, Miscellaneous.GetViviendaTipo(ctx, Miscellaneous.validInt(cliente.getViviendaTipoId()))); //TIPO VIVIENDA
                                params.put(15, Miscellaneous.GetParentesco(ctx, Miscellaneous.validInt(cliente.getParentescoId()))); //PARENTESCO
                                params.put(16, Miscellaneous.validStr(cliente.getOtroTipoVivienda()));       //OTRO TIPO VIVIENDA
                                if (!Miscellaneous.validStr(cliente.getTiempoVivirSitio()).isEmpty())
                                    params.put(17, cliente.getTiempoVivirSitio());
                                else
                                    params.put(17, "0");                                                     //TIEMPO VIVIR SITIO
                                params.put(18, "");                                                          //FOTO FACHADA
                                params.put(19, Miscellaneous.validStr(cliente.getReferencia()));             //REF DOMICILIARIA
                                params.put(20, "0");                                                         //ESTATUS COMPLETO
                                params.put(21, "");                                                          //DEPENDIENTES ECONOMICOS

                                dBhelper.saveDatosDomicilio(db, params, 2);

                                //Inserta registro de negocio
                                params = new HashMap<>();
                                params.put(0, String.valueOf(id));                                                          //ID INTEGRANTE
                                params.put(1, Miscellaneous.validStr(negocio.getNombre()));                                 //NOMBRE
                                params.put(2, Miscellaneous.validStr(negocio.getLatitud()));                                //LATITID
                                params.put(3, Miscellaneous.validStr(negocio.getLongitud()));                               //LONGITUD
                                params.put(4, Miscellaneous.validStr(negocio.getCalle()));                                  //CALLE
                                params.put(5, Miscellaneous.validStr(negocio.getNoExterior()));                             //NO EXTERIOR
                                params.put(6, Miscellaneous.validStr(negocio.getNoInterior()));                             //NO INTERIOR
                                params.put(7, Miscellaneous.validStr(negocio.getNoManzana()));                              //MANZANA
                                params.put(8, Miscellaneous.validStr(negocio.getNoLote()));                                 //LOTE
                                params.put(9, String.valueOf(Miscellaneous.validInt(negocio.getCodigoPostal())));           //CP
                                params.put(10, Miscellaneous.GetColonia(ctx, Miscellaneous.validInt(negocio.getColoniaId())));//COLONIA
                                params.put(11, Miscellaneous.validStr(negocio.getCiudad()));                                //CIUDAD
                                params.put(12, Miscellaneous.GetLocalidad(ctx, Miscellaneous.validInt(negocio.getLocalidadId())));//LOCALIDAD
                                params.put(13, Miscellaneous.GetMunicipio(ctx, Miscellaneous.validInt(negocio.getMunicipioId())));//MUNICIPIO
                                params.put(14, Miscellaneous.GetEstado(ctx, Miscellaneous.validInt(negocio.getEstadoId())));//ESTADO
                                params.put(15, Miscellaneous.GetDestinoCredito(ctx, Miscellaneous.validInt(negocio.getDestinoCreditoId())));//DESTINO CREDITO
                                params.put(16, Miscellaneous.validStr(negocio.getOtroDestinoCredito()));                    //OTRO DESTINO CREDITO
                                params.put(17, Miscellaneous.GetOcupacion(ctx, Miscellaneous.validInt(negocio.getOcupacionId())));//OCUPACION
                                params.put(18, Miscellaneous.GetSector(ctx, Miscellaneous.validInt(negocio.getSectorId())));//ACTIVIDAD ECONOMICA
                                params.put(19, String.valueOf(Miscellaneous.validInt(negocio.getAntiguedad())));            //ANTIGUEDA
                                params.put(20, "");                                                                         //INGRESO MENSUAL
                                params.put(21, "");                                                                         //INGRESOS OTROS
                                params.put(22, "");                                                                         //GASTO MENSUAL
                                params.put(23, "");                                                                         //CAPACIDAD DE PAGO
                                params.put(24, "");                                                                         //MONTO MAXIMO
                                params.put(25, "");                                                                         //MEDIOS PAGO
                                params.put(26, "");                                                                         //OTRO MEDIO DE PAGO
                                params.put(27, "");                                                                         //NUM OPERACIONES MENSUALES
                                params.put(28, "");                                                                         //NUM OPERACIONES MENSUALES EFECTIVO
                                params.put(29, "");                                                                         //FOTO FACHADA
                                params.put(30, "");                                                                         //REFERENCIA DOMICILIARIA
                                params.put(31, "0");                                                                        //ESTATUS RECHAZO
                                params.put(32, "");                                                                         //COMENTARIO RECHAZADO
                                params.put(33, "0");                                                                        //ESTATUS COMPLETADO

                                dBhelper.saveDatosNegocioGpo(db, params, 2);

                                //Inserta registro del conyuge
                                params = new HashMap<>();
                                params.put(0, String.valueOf(id));                                        //ID INTEGRANTE
                                params.put(1, Miscellaneous.validStr(conyuge.getNombre()));               //NOMBRE
                                params.put(2, Miscellaneous.validStr(conyuge.getPaterno()));              //PATERNO
                                params.put(3, Miscellaneous.validStr(conyuge.getMaterno()));              //MATERNO
                                params.put(4, Miscellaneous.validStr(conyuge.getNacionalidad()));         //NACIONALIDAD
                                params.put(5, Miscellaneous.GetOcupacion(ctx, negocio.getOcupacionId())); //OCUPACION
                                params.put(6, Miscellaneous.validStr(conyuge.getCalle()));                //CALLE
                                params.put(7, Miscellaneous.validStr(conyuge.getNoExterior()));           //NO EXTERIOR
                                params.put(8, Miscellaneous.validStr(conyuge.getNoInterior()));           //NO INTERIOR
                                params.put(9, Miscellaneous.validStr(conyuge.getNoManzana()));            //MANZANA
                                params.put(10, Miscellaneous.validStr(conyuge.getNoLote()));              //LOTE
                                params.put(11, String.valueOf(conyuge.getCodigoPostal()));                //CP
                                params.put(12, Miscellaneous.GetColonia(ctx, conyuge.getColoniaId()));    //COLONIA
                                params.put(13, Miscellaneous.validStr(conyuge.getCiudad()));              //CIUDAD
                                params.put(14, Miscellaneous.GetLocalidad(ctx, conyuge.getLocalidadId()));//LOCALIDAD
                                params.put(15, Miscellaneous.GetMunicipio(ctx, conyuge.getMunicipioId()));//MUNICIPIO
                                params.put(16, Miscellaneous.GetEstado(ctx, conyuge.getEstadoId()));      //ESTADO
                                params.put(17, "");                                                       //INGRESO MENSUAL
                                params.put(18, "");                                                       //GASTO MENSUAL
                                params.put(19, Miscellaneous.validStr(conyuge.getTelCasa()));             //TEL CASA
                                params.put(20, Miscellaneous.validStr(conyuge.getTelCelular()));          //TEL CELULAR
                                params.put(21, "0");                                                      //ESTATUS COMPLETADO

                                dBhelper.saveDatosConyugeGpo(db, params, 2);

                                //Inserta otros datos del integrante
                                params = new HashMap<>();
                                params.put(0, String.valueOf(id));                                                  //ID INTEGRANTE
                                params.put(1, "");                                                                  //CLASIFICACION RIESGO
                                params.put(2, Miscellaneous.GetMedioContacto(ctx, cliente.getMedioContactoId()));   //MEDIO CONTACTO
                                params.put(3, Miscellaneous.validStr(cliente.getEmail()));                          //EMAIL
                                params.put(4, Miscellaneous.validStr(cliente.getEstadoCuenta()));                   //ESTADO CUENTA
                                params.put(5, "2");                                                                 //ESTATUS INTEGRANTE
                                params.put(6, "");                                                                  //MONTO SOLICITADO
                                if (cliente.getTipoIntegrante() == 3)
                                    params.put(7, "1");                                                             //CASA REUNION
                                else
                                    params.put(7, "0");                                                             //CASA REUNION
                                params.put(8, "");                                                                  //FIRMA
                                params.put(9, "0");                                                                 //ESTATUS COMPLETADO

                                dBhelper.saveDatosOtrosGpo(db, params, 2);

                                //Inserta registro de croquis
                                params = new HashMap<>();
                                params.put(0, String.valueOf(id));                  //ID SOLICITUD
                                params.put(1, "");                                  //CALLE PRINCIPAL
                                params.put(2, "");                                  //LATERAL UNO
                                params.put(3, "");                                  //LATERAL DOS
                                params.put(4, "");                                  //CALLE TRASERA
                                params.put(5, "");                                  //REFERENCIAS
                                params.put(6, "0");                                 //ESTATUS COMPLETADO

                                dBhelper.saveCroquisGpo(db, params, 2);

                                //Inserta registro de politicas de integrante
                                params = new HashMap<>();
                                params.put(0, String.valueOf(id));      //ID INTEGRANTE
                                params.put(1, "0");                     //PROPIETARIO REAL
                                params.put(2, "0");                     //PROVEEDOR RECURSOS
                                params.put(3, "0");                     //PERSONA POLITICA
                                params.put(4, "0");                     //ESTATUS COMPLETADO

                                dBhelper.savePoliticasIntegrante(db, params, 2);

                                //Inserta registro de documentos de integrante
                                params = new HashMap<>();
                                params.put(0, String.valueOf(id));      //ID INTEGRANTE
                                params.put(1, "");                      //INE FRONTAL
                                params.put(2, "");                      //INE REVERSO
                                params.put(3, "");                      //CURP
                                params.put(4, "");                      //COMPROBANTE
                                params.put(5, "0");                     //ESTATUS COMPLETADO

                                dBhelper.saveDocumentosIntegrante(db, params, 2);
                            }

                            ContentValues cv = new ContentValues();
                            cv.put("descargado", 1);
                            db.update(TBL_PRESTAMOS_TO_RENOVAR, cv, "grupo_id = ?", new String[]{grupoId});
                            break;
                        default:
                            try {
                                Log.e("ERROR " + response.code(), response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            Log.e("ERROR " + response.code(), response.message());
                            break;

                    }
                }

                @Override
                public void onFailure(Call<MRenovacionGrupal> call, Throwable t) {

                }
            });


            return "";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }

    public class  RegistrarDatosRenovacionGpoForce extends AsyncTask<Object, Void, String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Object... obj) {
            final Context ctx = (Context)obj[0];
            final DBhelper dBhelper = new DBhelper(ctx);
            final SQLiteDatabase db = dBhelper.getWritableDatabase();
            final SessionManager session = new SessionManager(ctx);
            String _id = (String) obj[1];
            final String grupoId = (String) obj[2];
            final String nombreGpo = (String) obj[3];
            final String fechaVencimiento = (String) obj[4];

            ManagerInterface api = new RetrofitClient().generalRF(CONTROLLER_MOVIL, ctx).create(ManagerInterface.class);

            Log.e("AQUI GRUPO0", grupoId);

            Call<MRenovacionGrupal> call = api.getPrestamoRenovarGpo(
                    grupoId,
                    "Bearer "+ session.getUser().get(7));

            call.enqueue(new Callback<MRenovacionGrupal>() {
                @Override
                public void onResponse(Call<MRenovacionGrupal> call, Response<MRenovacionGrupal> response) {
                    switch (response.code()){
                        case 200:
                            MRenovacionGrupal item = response.body();

                            Log.e("AQUI", Miscellaneous.ConvertToJson(item));

                            HashMap<Integer, String> params = new HashMap<>();
                            params.put(0, ctx.getString(R.string.vol_solicitud));   //VOL SOLICITUD
                            params.put(1,session.getUser().get(9));                 //USUARIO ID
                            params.put(2,"2");                                      //TIPO SOLICITUD
                            params.put(3,"0");                                      //ID ORIGINACION
                            params.put(4, nombreGpo);                               //NOMBRE
                            params.put(5, Miscellaneous.ObtenerFecha(TIMESTAMP));   //FECHA INICIO
                            params.put(6,"");                                       //FECHA TERMINO
                            params.put(7,"");                                       //FECHA ENVIO
                            params.put(8, Miscellaneous.ObtenerFecha(TIMESTAMP));   //FECHA DISPOSITIVO
                            params.put(9, "");                                      //FECHA GUARDADO
                            params.put(10, "0");                                    //ESTATUS

                            String sqlSolicitud = "SELECT *  FROM " + TBL_SOLICITUDES_REN + " WHERE nombre = ?";
                            Cursor rowSolicitud = db.rawQuery(sqlSolicitud, new String[]{nombreGpo});

                            long id_solicitud = 0;

                            if(rowSolicitud.getCount() > 0)
                            {
                                rowSolicitud.moveToFirst();
                                id_solicitud = rowSolicitud.getInt(0);
                            }

                            rowSolicitud.close();

                            String sqlCredito= "SELECT *  FROM " + TBL_CREDITO_GPO_REN + " WHERE id_solicitud = ?";
                            Cursor rowCredito = db.rawQuery(sqlCredito, new String[]{String.valueOf(id_solicitud)});

                            long id_credito = 0;

                            if(rowCredito.getCount() > 0)
                            {
                                rowCredito.moveToFirst();
                                id_credito = rowCredito.getInt(0);
                            }

                            rowCredito.close();

                            Log.e("AQUI", String.valueOf(id_credito));

                            for (MRenovacionGrupal.Integrante integrante :item.getIntegrantes()) {

                                MRenovacionGrupal.Cliente cliente = integrante.getCliente();
                                MRenovacionGrupal.Negocio negocio = integrante.getNegocio();
                                MRenovacionGrupal.Conyuge conyuge = integrante.getConyuge();
                                Log.e("AQUI", String.valueOf(integrante.getClienteId()));

                                Log.e("AQUI", "INTEGRANTE");
                                String sqlIntegrante = "SELECT *  FROM " + TBL_INTEGRANTES_GPO_REN + " WHERE cliente_id = ? or (trim(nombre) = ? and trim(paterno) = ? and trim(materno) = ?)";
                                Cursor rowIntegrante = db.rawQuery(sqlIntegrante, new String[]{String.valueOf(integrante.getClienteId()).trim(), Miscellaneous.validStr(cliente.getNombre()).toUpperCase().trim(), Miscellaneous.validStr(cliente.getPaterno()).toUpperCase().trim(), Miscellaneous.validStr(cliente.getMaterno()).toUpperCase().trim()});

                                if (rowIntegrante.getCount() == 0  && id_solicitud > 0 && id_credito > 0) {
                                    Log.e("AQUI", "store");
                                    long id = 0;
                                    //Inserta registro de integrante
                                    params = new HashMap<>();
                                    params.put(0, String.valueOf(id_credito));                              //ID CREDITO
                                    params.put(1, String.valueOf(cliente.getTipoIntegrante()));             //CARGO
                                    params.put(2, Miscellaneous.validStr(cliente.getNombre()).trim().toUpperCase()); //NOMBRE(S)
                                    params.put(3, Miscellaneous.validStr(cliente.getPaterno()).trim().toUpperCase());//PATERNO
                                    params.put(4, Miscellaneous.validStr(cliente.getMaterno()).trim().toUpperCase());//MATERNO
                                    params.put(5, Miscellaneous.validStr(cliente.getFechaNacimiento().trim()));                            //FECHA NACIMIENTO
                                    params.put(6, Miscellaneous.GetEdad(cliente.getFechaNacimiento()));     //EDAD
                                    params.put(7, String.valueOf(cliente.getGenero()));                     //GENERO
                                    params.put(8, Miscellaneous.GetEstado(ctx, cliente.getEstadoNacimiento())); //ESTADO NACIMIENTO
                                    params.put(9, Miscellaneous.validStr(cliente.getRfc()));                //RFC
                                    params.put(10, Miscellaneous.validStr(cliente.getCurp()));               //CURP
                                    params.put(11, "");                                                     //CURP DIGITO VERI
                                    params.put(12, Miscellaneous.GetTipoIdentificacion(ctx, cliente.getIdentificacionTipoId())); //TIPO IDENTIFICACION
                                    params.put(13, Miscellaneous.validStr(cliente.getNoIdentificacion()));  //NO IDENTIFICACION
                                    params.put(14, Miscellaneous.GetEstudio(ctx, cliente.getEstudioNivelId())); //NIVEL ESTUDIO
                                    params.put(15, Miscellaneous.GetOcupacion(ctx, cliente.getOcupacionId()));  //OCUPACION
                                    params.put(16, Miscellaneous.GetEstadoCivil(ctx, cliente.getEstadoCivilId())); //ESTADO CIVIL
                                    params.put(17, String.valueOf(cliente.getRegimenBienId()));              //BIENES
                                    params.put(18, "0");                                                    //ESTATUS RECHAZO
                                    params.put(19, "");                                                     //COMENTARIO RECHAZO
                                    params.put(20, "0");                                                    //ESTATUS COMPLETO
                                    params.put(21, "0");                                                    //ID SOLICITUD INTEGRANTE
                                    params.put(22, "0");                                                    //IS NUEVO
                                    params.put(23, String.valueOf(integrante.getClienteId()));              //CLIENTE ID
                                    params.put(24, String.valueOf(integrante.getCiclo()));
                                    params.put(25, integrante.getMontoPrestamoAnterior());

                                    id = dBhelper.saveIntegrantesGpoRen(db, params);

                                    //Inserta registro de datos telefonicos
                                    params = new HashMap<>();
                                    params.put(0, String.valueOf(id));              //ID INTEGRANTE
                                    params.put(1, Miscellaneous.validStr(cliente.getTelCasa()));            //TEL CASA
                                    params.put(2, Miscellaneous.validStr(cliente.getTelCelular()));         //TEL CELULAR
                                    params.put(3, Miscellaneous.validStr(cliente.getTelMensaje()));         //TEL MENSAJES
                                    params.put(4, Miscellaneous.validStr(cliente.getTelTrabajo()));         //TEL TRABAJO
                                    params.put(5, "0");                                                     //ESTATUS COMPLETADO

                                    dBhelper.saveDatosTelefonicos(db, params, 2);

                                    //Inserta registro de datos domicilio
                                    params = new HashMap<>();
                                    params.put(0, String.valueOf(id));                                          //ID INTEGRANTE
                                    params.put(1, Miscellaneous.validStr(cliente.getLatitud()));                //LATITUD
                                    params.put(2, Miscellaneous.validStr(cliente.getLongitud()));               //LONGITUD
                                    params.put(3, Miscellaneous.validStr(cliente.getCalle()));                  //CALLE
                                    params.put(4, Miscellaneous.validStr(cliente.getNoExterior()));             //NO_EXTERIOR
                                    params.put(5, Miscellaneous.validStr(cliente.getNoInterior()));             //NO INTERIOR
                                    params.put(6, Miscellaneous.validStr(cliente.getNoManzana()));              //MANZANA
                                    params.put(7, Miscellaneous.validStr(cliente.getNoLote()));                 //LOTE
                                    params.put(8, String.valueOf(Miscellaneous.validInt(cliente.getCodigoPostal()))); //CP
                                    params.put(9, Miscellaneous.GetColonia(ctx, Miscellaneous.validInt(cliente.getColoniaId())));//COLONIA
                                    params.put(10, Miscellaneous.validStr(cliente.getCiudad()));                //CIUDAD
                                    params.put(11, Miscellaneous.GetLocalidad(ctx, Miscellaneous.validInt(cliente.getLocalidadId())));  //LOCALIDAD
                                    params.put(12, Miscellaneous.GetMunicipio(ctx, Miscellaneous.validInt(cliente.getMunicipioId())));  //MUNICIPIO
                                    params.put(13, Miscellaneous.GetEstado(ctx, Miscellaneous.validInt(cliente.getEstadoId())));//ESTADO
                                    params.put(14, Miscellaneous.GetViviendaTipo(ctx, Miscellaneous.validInt(cliente.getViviendaTipoId()))); //TIPO VIVIENDA
                                    params.put(15, Miscellaneous.GetParentesco(ctx, Miscellaneous.validInt(cliente.getParentescoId()))); //PARENTESCO
                                    params.put(16, Miscellaneous.validStr(cliente.getOtroTipoVivienda()));       //OTRO TIPO VIVIENDA
                                    if (!Miscellaneous.validStr(cliente.getTiempoVivirSitio()).isEmpty())
                                        params.put(17, cliente.getTiempoVivirSitio());
                                    else
                                        params.put(17, "0");                                                     //TIEMPO VIVIR SITIO
                                    params.put(18, "");                                                          //FOTO FACHADA
                                    params.put(19, Miscellaneous.validStr(cliente.getReferencia()));             //REF DOMICILIARIA
                                    params.put(20, "0");                                                         //ESTATUS COMPLETO
                                    params.put(21, "");                                                          //DEPENDIENTES ECONOMICOS

                                    dBhelper.saveDatosDomicilio(db, params, 2);

                                    //Inserta registro de negocio
                                    params = new HashMap<>();
                                    params.put(0, String.valueOf(id));                                                          //ID INTEGRANTE
                                    params.put(1, Miscellaneous.validStr(negocio.getNombre()));                                 //NOMBRE
                                    params.put(2, Miscellaneous.validStr(negocio.getLatitud()));                                //LATITID
                                    params.put(3, Miscellaneous.validStr(negocio.getLongitud()));                               //LONGITUD
                                    params.put(4, Miscellaneous.validStr(negocio.getCalle()));                                  //CALLE
                                    params.put(5, Miscellaneous.validStr(negocio.getNoExterior()));                             //NO EXTERIOR
                                    params.put(6, Miscellaneous.validStr(negocio.getNoInterior()));                             //NO INTERIOR
                                    params.put(7, Miscellaneous.validStr(negocio.getNoManzana()));                              //MANZANA
                                    params.put(8, Miscellaneous.validStr(negocio.getNoLote()));                                 //LOTE
                                    params.put(9, String.valueOf(Miscellaneous.validInt(negocio.getCodigoPostal())));           //CP
                                    params.put(10, Miscellaneous.GetColonia(ctx, Miscellaneous.validInt(negocio.getColoniaId())));//COLONIA
                                    params.put(11, Miscellaneous.validStr(negocio.getCiudad()));                                //CIUDAD
                                    params.put(12, Miscellaneous.GetLocalidad(ctx, Miscellaneous.validInt(negocio.getLocalidadId())));//LOCALIDAD
                                    params.put(13, Miscellaneous.GetMunicipio(ctx, Miscellaneous.validInt(negocio.getMunicipioId())));//MUNICIPIO
                                    params.put(14, Miscellaneous.GetEstado(ctx, Miscellaneous.validInt(negocio.getEstadoId())));//ESTADO
                                    params.put(15, Miscellaneous.GetDestinoCredito(ctx, Miscellaneous.validInt(negocio.getDestinoCreditoId())));//DESTINO CREDITO
                                    params.put(16, Miscellaneous.validStr(negocio.getOtroDestinoCredito()));                    //OTRO DESTINO CREDITO
                                    params.put(17, Miscellaneous.GetOcupacion(ctx, Miscellaneous.validInt(negocio.getOcupacionId())));//OCUPACION
                                    params.put(18, Miscellaneous.GetSector(ctx, Miscellaneous.validInt(negocio.getSectorId())));//ACTIVIDAD ECONOMICA
                                    params.put(19, String.valueOf(Miscellaneous.validInt(negocio.getAntiguedad())));            //ANTIGUEDA
                                    params.put(20, "");                                                                         //INGRESO MENSUAL
                                    params.put(21, "");                                                                         //INGRESOS OTROS
                                    params.put(22, "");                                                                         //GASTO MENSUAL
                                    params.put(23, "");                                                                         //CAPACIDAD DE PAGO
                                    params.put(24, "");                                                                         //MONTO MAXIMO
                                    params.put(25, "");                                                                         //MEDIOS PAGO
                                    params.put(26, "");                                                                         //OTRO MEDIO DE PAGO
                                    params.put(27, "");                                                                         //NUM OPERACIONES MENSUALES
                                    params.put(28, "");                                                                         //NUM OPERACIONES MENSUALES EFECTIVO
                                    params.put(29, "");                                                                         //FOTO FACHADA
                                    params.put(30, "");                                                                         //REFERENCIA DOMICILIARIA
                                    params.put(31, "0");                                                                        //ESTATUS RECHAZO
                                    params.put(32, "");                                                                         //COMENTARIO RECHAZADO
                                    params.put(33, "0");                                                                        //ESTATUS COMPLETADO

                                    dBhelper.saveDatosNegocioGpo(db, params, 2);

                                    //Inserta registro del conyuge
                                    params = new HashMap<>();
                                    params.put(0, String.valueOf(id));                                        //ID INTEGRANTE
                                    params.put(1, Miscellaneous.validStr(conyuge.getNombre()));               //NOMBRE
                                    params.put(2, Miscellaneous.validStr(conyuge.getPaterno()));              //PATERNO
                                    params.put(3, Miscellaneous.validStr(conyuge.getMaterno()));              //MATERNO
                                    params.put(4, Miscellaneous.validStr(conyuge.getNacionalidad()));         //NACIONALIDAD
                                    params.put(5, Miscellaneous.GetOcupacion(ctx, negocio.getOcupacionId())); //OCUPACION
                                    params.put(6, Miscellaneous.validStr(conyuge.getCalle()));                //CALLE
                                    params.put(7, Miscellaneous.validStr(conyuge.getNoExterior()));           //NO EXTERIOR
                                    params.put(8, Miscellaneous.validStr(conyuge.getNoInterior()));           //NO INTERIOR
                                    params.put(9, Miscellaneous.validStr(conyuge.getNoManzana()));            //MANZANA
                                    params.put(10, Miscellaneous.validStr(conyuge.getNoLote()));              //LOTE
                                    params.put(11, String.valueOf(conyuge.getCodigoPostal()));                //CP
                                    params.put(12, Miscellaneous.GetColonia(ctx, conyuge.getColoniaId()));    //COLONIA
                                    params.put(13, Miscellaneous.validStr(conyuge.getCiudad()));              //CIUDAD
                                    params.put(14, Miscellaneous.GetLocalidad(ctx, conyuge.getLocalidadId()));//LOCALIDAD
                                    params.put(15, Miscellaneous.GetMunicipio(ctx, conyuge.getMunicipioId()));//MUNICIPIO
                                    params.put(16, Miscellaneous.GetEstado(ctx, conyuge.getEstadoId()));      //ESTADO
                                    params.put(17, "");                                                       //INGRESO MENSUAL
                                    params.put(18, "");                                                       //GASTO MENSUAL
                                    params.put(19, Miscellaneous.validStr(conyuge.getTelCasa()));             //TEL CASA
                                    params.put(20, Miscellaneous.validStr(conyuge.getTelCelular()));          //TEL CELULAR
                                    params.put(21, "0");                                                      //ESTATUS COMPLETADO

                                    dBhelper.saveDatosConyugeGpo(db, params, 2);

                                    //Inserta otros datos del integrante
                                    params = new HashMap<>();
                                    params.put(0, String.valueOf(id));                                                  //ID INTEGRANTE
                                    params.put(1, "");                                                                  //CLASIFICACION RIESGO
                                    params.put(2, Miscellaneous.GetMedioContacto(ctx, cliente.getMedioContactoId()));   //MEDIO CONTACTO
                                    params.put(3, Miscellaneous.validStr(cliente.getEmail()));                          //EMAIL
                                    params.put(4, Miscellaneous.validStr(cliente.getEstadoCuenta()));                   //ESTADO CUENTA
                                    params.put(5, "2");                                                                 //ESTATUS INTEGRANTE
                                    params.put(6, "");                                                                  //MONTO SOLICITADO
                                    if (cliente.getTipoIntegrante() == 3)
                                        params.put(7, "1");                                                             //CASA REUNION
                                    else
                                        params.put(7, "0");                                                             //CASA REUNION
                                    params.put(8, "");                                                                  //FIRMA
                                    params.put(9, "0");                                                                 //ESTATUS COMPLETADO

                                    dBhelper.saveDatosOtrosGpo(db, params, 2);

                                    //Inserta registro de croquis
                                    params = new HashMap<>();
                                    params.put(0, String.valueOf(id));                  //ID SOLICITUD
                                    params.put(1, "");                                  //CALLE PRINCIPAL
                                    params.put(2, "");                                  //LATERAL UNO
                                    params.put(3, "");                                  //LATERAL DOS
                                    params.put(4, "");                                  //CALLE TRASERA
                                    params.put(5, "");                                  //REFERENCIAS
                                    params.put(6, "0");                                 //ESTATUS COMPLETADO

                                    dBhelper.saveCroquisGpo(db, params, 2);

                                    //Inserta registro de politicas de integrante
                                    params = new HashMap<>();
                                    params.put(0, String.valueOf(id));      //ID INTEGRANTE
                                    params.put(1, "0");                     //PROPIETARIO REAL
                                    params.put(2, "0");                     //PROVEEDOR RECURSOS
                                    params.put(3, "0");                     //PERSONA POLITICA
                                    params.put(4, "0");                     //ESTATUS COMPLETADO

                                    dBhelper.savePoliticasIntegrante(db, params, 2);

                                    //Inserta registro de documentos de integrante
                                    params = new HashMap<>();
                                    params.put(0, String.valueOf(id));      //ID INTEGRANTE
                                    params.put(1, "");                      //INE FRONTAL
                                    params.put(2, "");                      //INE REVERSO
                                    params.put(3, "");                      //CURP
                                    params.put(4, "");                      //COMPROBANTE
                                    params.put(5, "0");                     //ESTATUS COMPLETADO

                                    dBhelper.saveDocumentosIntegrante(db, params, 2);
                                }
                                else
                                {
                                    Log.e("AQUI", "ignore");
                                }

                                rowIntegrante.close();
                            }

                            ContentValues cv = new ContentValues();
                            cv.put("descargado", 1);
                            db.update(TBL_PRESTAMOS_TO_RENOVAR, cv, "grupo_id = ?", new String[]{grupoId});

                            break;
                        default:
                            try {
                                Log.e("ERROR " + response.code(), response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            Log.e("ERROR " + response.code(), response.message());
                            break;

                    }
                }

                @Override
                public void onFailure(Call<MRenovacionGrupal> call, Throwable t) {
                    Log.e("AQUI", t.getMessage());
                }
            });


            return "";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
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
                            db.update(TBL_TRACKER_ASESOR_T, cv, "_id = ?", new String[]{id_tracker});
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
                            db.update(TBL_IMPRESIONES_VIGENTE_T, cv,
                                    "_id = ?", new String[]{id_impresion});
                        else if (tipo_impresion == 2) //Vencida
                            db.update(TBL_IMPRESIONES_VENCIDA_T, cv,
                                    "_id = ?", new String[]{id_impresion});
                        else if (tipo_impresion == 3) //Reimpresiones Vigente/Vencida
                            db.update(TBL_REIMPRESION_VIGENTE_T, cv,
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

                            Log.e("xxxx", "Cartera:"+row.getString(0));
                            Log.e("xxxx","nombre:"+row.getString(2));

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
            Long solicitudId = (Long) params[13];
            String ineSelfie = (String) params[14];
            String comprobanteGarantia = (String) params[15];
            String ineFrontalAval = (String) params[16];
            String ineReversoAval = (String) params[17];
            String curpAval = (String) params[18];
            String comprobanteAval = (String) params[19];

            SessionManager session = new SessionManager(ctx);
            DBhelper dBhelper = new DBhelper(ctx);
            final SQLiteDatabase db = dBhelper.getWritableDatabase();


            MultipartBody.Part fachada_cliente = null;
            File image_fachada_cliente = null;
            if(fachadaCli != null && !fachadaCli.equals("")) image_fachada_cliente = new File(ROOT_PATH + "Fachada/"+fachadaCli);
            if (image_fachada_cliente != null) {
                RequestBody imageBodyFachadaCli =
                        RequestBody.create(
                                MediaType.parse("image/*"), image_fachada_cliente);

                fachada_cliente = MultipartBody.Part.createFormData("fachada_cliente", image_fachada_cliente.getName(), imageBodyFachadaCli);
            }

            MultipartBody.Part firma_cliente = null;
            File image_firma_cliente = new File(ROOT_PATH + "Firma/"+firmaCli);
            if (image_firma_cliente != null) {
                RequestBody imageBodyFirmaCli =
                        RequestBody.create(
                                MediaType.parse("image/*"), image_firma_cliente);

                firma_cliente = MultipartBody.Part.createFormData("firma_cliente", image_firma_cliente.getName(), imageBodyFirmaCli);
            }

            MultipartBody.Part fachada_negocio = null;
            File image_fachada_negocio = null;
            if(fachadaNeg != null && !fachadaNeg.equals("")) image_fachada_negocio = new File(ROOT_PATH + "Fachada/"+fachadaNeg);
            if (image_fachada_negocio != null) {
                RequestBody imageBodyFachadaNeg =
                        RequestBody.create(
                                MediaType.parse("image/*"), image_fachada_negocio);

                fachada_negocio = MultipartBody.Part.createFormData("fachada_negocio", image_fachada_negocio.getName(), imageBodyFachadaNeg);
            }

            MultipartBody.Part fachada_aval = null;
            File image_fachada_aval = null;
            if(fachadaAval != null && !fachadaAval.equals("")) image_fachada_aval = new File(ROOT_PATH + "Fachada/"+fachadaAval);
            if (image_fachada_aval != null) {
                RequestBody imageBodyFachadaAval =
                        RequestBody.create(
                                MediaType.parse("image/*"), image_fachada_aval);

                fachada_aval = MultipartBody.Part.createFormData("fachada_aval", image_fachada_aval.getName(), imageBodyFachadaAval);
            }

            MultipartBody.Part firma_aval = null;
            File image_firma_aval = new File(ROOT_PATH + "Firma/"+firmaAval);
            if (image_firma_aval != null) {
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

            MultipartBody.Part ine_selfie = null;
            File image_ine_selfie = null;
            if(ineSelfie != null && !ineSelfie.equals("")) image_ine_selfie = new File(ROOT_PATH + "Documentos/"+ineSelfie);
            if (image_ine_selfie != null) {
                RequestBody imageBody =
                        RequestBody.create(
                                MediaType.parse("image/*"), image_ine_selfie);

                ine_selfie = MultipartBody.Part.createFormData("identificacion_selfie", image_ine_selfie.getName(), imageBody);
            }

            MultipartBody.Part comprobante_garantia = null;
            File image_comprobante_garantia = null;
            if(comprobanteGarantia != null && !comprobanteGarantia.equals("")) image_comprobante_garantia = new File(ROOT_PATH + "Documentos/"+comprobanteGarantia);
            if (image_comprobante_garantia != null) {
                RequestBody imageBody =
                        RequestBody.create(
                                MediaType.parse("image/*"), image_comprobante_garantia);

                comprobante_garantia = MultipartBody.Part.createFormData("comprobante_garantia", image_comprobante_garantia.getName(), imageBody);
            }

            MultipartBody.Part ine_frontal_aval = null;
            File image_ine_frontal_aval = null;
            if(ineFrontalAval != null && !ineFrontalAval.equals("")) image_ine_frontal_aval = new File(ROOT_PATH + "Documentos/"+ineFrontalAval);
            if (image_ine_frontal_aval != null) {
                RequestBody imageBody =
                        RequestBody.create(
                                MediaType.parse("image/*"), image_ine_frontal_aval);

                ine_frontal_aval = MultipartBody.Part.createFormData("identificacion_frontal_aval", image_ine_frontal_aval.getName(), imageBody);
            }

            MultipartBody.Part ine_reverso_aval = null;
            File image_ine_reverso_aval = null;
            if(ineReversoAval != null && !ineReversoAval.equals("")) image_ine_reverso_aval = new File(ROOT_PATH + "Documentos/"+ineReversoAval);
            if (image_ine_reverso_aval != null) {
                RequestBody imageBody =
                        RequestBody.create(
                                MediaType.parse("image/*"), image_ine_reverso_aval);

                ine_reverso_aval = MultipartBody.Part.createFormData("identificacion_reverso_aval", image_ine_reverso_aval.getName(), imageBody);
            }

            MultipartBody.Part curp_aval = null;
            File image_curp_aval = null;
            if(curpAval != null && !curpAval.equals("")) image_curp_aval = new File(ROOT_PATH + "Documentos/"+curpAval);
            if (image_curp_aval != null) {
                RequestBody imageBody =
                        RequestBody.create(
                                MediaType.parse("image/*"), image_curp_aval);

                curp_aval = MultipartBody.Part.createFormData("curp_aval", image_curp_aval.getName(), imageBody);
            }

            MultipartBody.Part comprobante_aval = null;
            File image_comprobante_aval = null;
            if(comprobanteAval != null && !comprobanteAval.equals("")) image_comprobante_aval = new File(ROOT_PATH + "Documentos/"+comprobanteAval);
            if (image_comprobante_aval != null) {
                RequestBody imageBody =
                        RequestBody.create(
                                MediaType.parse("image/*"), image_comprobante_aval);

                comprobante_aval = MultipartBody.Part.createFormData("comprobante_domicilio_aval", image_comprobante_aval.getName(), imageBody);
            }

            RequestBody solicitudBody = RequestBody.create(MultipartBody.FORM, solicitud.toString());

            RequestBody solicitudIdBody = RequestBody.create(MultipartBody.FORM, String.valueOf(solicitudId));

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
                    firma_asesor,
                    solicitudIdBody,
                    ine_selfie,
                    comprobante_garantia,
                    ine_frontal_aval,
                    ine_reverso_aval,
                    curp_aval,
                    comprobante_aval
                    );

            ContentValues cv = new ContentValues();
            cv.put("fecha_envio", Miscellaneous.ObtenerFecha(TIMESTAMP));

            call.enqueue(new Callback<MResSaveSolicitud>() {
                @Override
                public void onResponse(Call<MResSaveSolicitud> call, Response<MResSaveSolicitud> response) {
                    Log.e("Respuesta code", response.code()+" codigo");

                    switch (response.code()){
                        case 200:
                            MResSaveSolicitud res = response.body();

                            cv.put("estatus", "2");
                            cv.put("id_originacion",res.getIdSolicitud());
                            cv.put("fecha_guardado", Miscellaneous.ObtenerFecha(TIMESTAMP));
                            db.update(TBL_SOLICITUDES, cv, "id_solicitud = ?", new String[]{id});
                            break;
                        default:
                            try {
                                Log.e("ERROR " + response.code(), response.errorBody().string());

                            } catch (IOException e) {
                                e.printStackTrace();
                                Log.e("AQUI", e.getMessage());
                            }
                            break;
                    }

                }

                @Override
                public void onFailure(Call<MResSaveSolicitud> call, Throwable t) {
                    Log.e("Error", "failSolicitud");
                    Log.e("Error", t.getMessage());
                    t.printStackTrace();

                }
            });

            return "";
        }
    }

    public class GuardarRenovacionInd extends AsyncTask<Object, Void, String>{

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
            //String curp = (String) params[9];
            String comprobante = (String) params[9];
            String firmaAsesor = (String) params[10];
            final String id = (String) params[11];
            Long solicitudId = (Long) params[12];
            String ineSelfie = (String) params[13];
            String comprobanteGarantia = (String) params[14];
            String ineFrontalAval = (String) params[15];
            String ineReversoAval = (String) params[16];
            String curpAval = (String) params[17];
            String comprobanteAval = (String) params[18];

            SessionManager session = new SessionManager(ctx);
            DBhelper dBhelper = new DBhelper(ctx);
            final SQLiteDatabase db = dBhelper.getWritableDatabase();


            MultipartBody.Part fachada_cliente = null;
            File image_fachada_cliente = null;
            if(fachadaCli != null && !fachadaCli.equals("")) image_fachada_cliente = new File(ROOT_PATH + "Fachada/"+fachadaCli);
            if (image_fachada_cliente != null) {
                RequestBody imageBodyFachadaCli =
                        RequestBody.create(
                                MediaType.parse("image/*"), image_fachada_cliente);

                fachada_cliente = MultipartBody.Part.createFormData("fachada_cliente", image_fachada_cliente.getName(), imageBodyFachadaCli);
            }

            MultipartBody.Part firma_cliente = null;
            File image_firma_cliente = new File(ROOT_PATH + "Firma/"+firmaCli);
            if (image_firma_cliente != null) {
                RequestBody imageBodyFirmaCli =
                        RequestBody.create(
                                MediaType.parse("image/*"), image_firma_cliente);

                firma_cliente = MultipartBody.Part.createFormData("firma_cliente", image_firma_cliente.getName(), imageBodyFirmaCli);
            }

            MultipartBody.Part fachada_negocio = null;
            File image_fachada_negocio = null;
            if(fachadaNeg != null && !fachadaNeg.equals("")) image_fachada_negocio = new File(ROOT_PATH + "Fachada/"+fachadaNeg);
            if (image_fachada_negocio != null) {
                RequestBody imageBodyFachadaNeg =
                        RequestBody.create(
                                MediaType.parse("image/*"), image_fachada_negocio);

                fachada_negocio = MultipartBody.Part.createFormData("fachada_negocio", image_fachada_negocio.getName(), imageBodyFachadaNeg);
            }

            MultipartBody.Part fachada_aval = null;
            File image_fachada_aval = null;
            if(fachadaAval != null && !fachadaAval.equals("")) image_fachada_aval = new File(ROOT_PATH + "Fachada/"+fachadaAval);
            if (image_fachada_aval != null) {
                RequestBody imageBodyFachadaAval =
                        RequestBody.create(
                                MediaType.parse("image/*"), image_fachada_aval);

                fachada_aval = MultipartBody.Part.createFormData("fachada_aval", image_fachada_aval.getName(), imageBodyFachadaAval);
            }

            MultipartBody.Part firma_aval = null;
            File image_firma_aval = null;
            if(firmaAval != null) image_firma_aval = new File(ROOT_PATH + "Firma/"+firmaAval);
            if (image_firma_aval != null) {
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

            RequestBody attachmentEmpty = RequestBody.create(MediaType.parse("text/plain"), "");
            MultipartBody.Part foto_curp = MultipartBody.Part.createFormData("curp", "", attachmentEmpty);
            /*File image_curp = new File(ROOT_PATH + "Documentos/"+curp);
            if (image_curp != null) {
                RequestBody imageBody =
                        RequestBody.create(
                                MediaType.parse("image/*"), image_curp);

                foto_curp = MultipartBody.Part.createFormData("curp", image_curp.getName(), imageBody);
            }*/

            File image_comprobante = new File(ROOT_PATH + "Documentos/"+comprobante);
            MultipartBody.Part foto_comprobante = null;
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

            MultipartBody.Part ine_selfie = null;
            File image_ine_selfie = null;
            if(ineSelfie != null && !ineSelfie.equals("")) image_ine_selfie = new File(ROOT_PATH + "Documentos/"+ineSelfie);
            if (image_ine_selfie != null) {
                RequestBody imageBody =
                        RequestBody.create(
                                MediaType.parse("image/*"), image_ine_selfie);

                ine_selfie = MultipartBody.Part.createFormData("identificacion_selfie", image_ine_selfie.getName(), imageBody);
            }

            MultipartBody.Part comprobante_garantia = null;
            File image_comprobante_garantia = null;
            if(comprobanteGarantia != null && !comprobanteGarantia.equals("")) image_comprobante_garantia = new File(ROOT_PATH + "Documentos/"+comprobanteGarantia);
            if (image_comprobante_garantia != null) {
                RequestBody imageBody =
                        RequestBody.create(
                                MediaType.parse("image/*"), image_comprobante_garantia);

                comprobante_garantia = MultipartBody.Part.createFormData("comprobante_garantia", image_comprobante_garantia.getName(), imageBody);
            }

            MultipartBody.Part ine_frontal_aval = null;
            File image_ine_frontal_aval = null;
            if(ineFrontalAval != null && !ineFrontalAval.equals("")) image_ine_frontal_aval = new File(ROOT_PATH + "Documentos/"+ineFrontalAval);
            if (image_ine_frontal_aval != null) {
                RequestBody imageBody =
                        RequestBody.create(
                                MediaType.parse("image/*"), image_ine_frontal_aval);

                ine_frontal_aval = MultipartBody.Part.createFormData("identificacion_frontal_aval", image_ine_frontal_aval.getName(), imageBody);
            }

            MultipartBody.Part ine_reverso_aval = null;
            File image_ine_reverso_aval = null;
            if(ineReversoAval != null && !ineReversoAval.equals("")) image_ine_reverso_aval = new File(ROOT_PATH + "Documentos/"+ineReversoAval);
            if (image_ine_reverso_aval != null) {
                RequestBody imageBody =
                        RequestBody.create(
                                MediaType.parse("image/*"), image_ine_reverso_aval);

                ine_reverso_aval = MultipartBody.Part.createFormData("identificacion_reverso_aval", image_ine_reverso_aval.getName(), imageBody);
            }

            MultipartBody.Part curp_aval = null;
            File image_curp_aval = null;
            if(curpAval != null && !curpAval.equals("")) image_curp_aval = new File(ROOT_PATH + "Documentos/"+curpAval);
            if (image_curp_aval != null) {
                RequestBody imageBody =
                        RequestBody.create(
                                MediaType.parse("image/*"), image_curp_aval);

                curp_aval = MultipartBody.Part.createFormData("curp_aval", image_curp_aval.getName(), imageBody);
            }

            MultipartBody.Part comprobante_aval = null;
            File image_comprobante_aval = null;
            if(comprobanteAval != null && !comprobanteAval.equals("")) image_comprobante_aval = new File(ROOT_PATH + "Documentos/"+comprobanteAval);
            if (image_comprobante_aval != null) {
                RequestBody imageBody =
                        RequestBody.create(
                                MediaType.parse("image/*"), image_comprobante_aval);

                comprobante_aval = MultipartBody.Part.createFormData("comprobante_domicilio_aval", image_comprobante_aval.getName(), imageBody);
            }

            RequestBody solicitudBody = RequestBody.create(MultipartBody.FORM, solicitud.toString());

            RequestBody solicitudIdBody = RequestBody.create(MultipartBody.FORM, String.valueOf(solicitudId));

            Log.e("solicitud", solicitud.toString());
            ManagerInterface api = new RetrofitClient().generalRF(CONTROLLER_SOLICITUDES, ctx).create(ManagerInterface.class);

            MultipartBody.Part partNull = null;

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
                    firma_asesor,
                    solicitudIdBody,
                    ine_selfie,
                    comprobante_garantia,
                    ine_frontal_aval,
                    ine_reverso_aval,
                    curp_aval,
                    comprobante_aval
                );

            ContentValues cv = new ContentValues();
            cv.put("fecha_envio", Miscellaneous.ObtenerFecha(TIMESTAMP));

            call.enqueue(new Callback<MResSaveSolicitud>() {
                @Override
                public void onResponse(Call<MResSaveSolicitud> call, Response<MResSaveSolicitud> response) {
                    Log.e("Respuesta code", response.code()+" codigo");

                    switch (response.code()){
                        case 200:
                            MResSaveSolicitud res = response.body();
                            cv.put("estatus", "2");
                            cv.put("id_originacion",res.getIdSolicitud());
                            cv.put("fecha_guardado", Miscellaneous.ObtenerFecha(TIMESTAMP));
                            db.update(TBL_SOLICITUDES_REN, cv, "id_solicitud = ?", new String[]{id});
                            break;
                        default:
                            try {
                                Log.e("ERROR " + response.code(), response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                    }

                }

                @Override
                public void onFailure(Call<MResSaveSolicitud> call, Throwable t) {
                    Log.e("ERROR", t.getMessage());
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
            final String id_solicitud_integrante = (String) params[12];
            final String id_solicitud_grupal = (String) params[13];
            int iIndex = (int) params[14];
            int iTotal = (int) params[15];
            String sDato0 = (String) params[16];
            Long lDato4 = (Long) params[17];
            String sDato6 = (String) params[18];
            String sDato12 = (String) params[19];
            String sDato14 = (String) params[20];
            String sDato15 = (String) params[21];
            String sDato16 = (String) params[22];
            String sDato17 = (String) params[23];
            String sDato19 = (String) params[24];
            String ineSelfie = (String) params[25];

            SessionManager session = new SessionManager(ctx);
            DBhelper dBhelper = new DBhelper(ctx);
            final SQLiteDatabase db = dBhelper.getWritableDatabase();


            MultipartBody.Part fachada_cliente = null;
            File image_fachada_cliente = null;
            if(fachadaCli != null && !fachadaCli.equals("")) image_fachada_cliente = new File(ROOT_PATH + "Fachada/"+fachadaCli);
            if (image_fachada_cliente != null) {
                RequestBody imageBodyFachadaCli =
                        RequestBody.create(
                                MediaType.parse("image/*"), image_fachada_cliente);

                fachada_cliente = MultipartBody.Part.createFormData("fachada_cliente", image_fachada_cliente.getName(), imageBodyFachadaCli);
            }

            MultipartBody.Part firma_cliente = null;
            File image_firma_cliente = new File(ROOT_PATH + "Firma/"+firmaCli);
            if (image_firma_cliente != null) {
                RequestBody imageBodyFirmaCli =
                        RequestBody.create(
                                MediaType.parse("image/*"), image_firma_cliente);

                firma_cliente = MultipartBody.Part.createFormData("firma_cliente", image_firma_cliente.getName(), imageBodyFirmaCli);
            }

            MultipartBody.Part fachada_negocio = null;
            File image_fachada_negocio = null;
            if(fachadaNeg != null && !fachadaNeg.equals("")) image_fachada_negocio = new File(ROOT_PATH + "Fachada/"+fachadaNeg);
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

            MultipartBody.Part ine_selfie = null;
            File image_ine_selfie = null;
            if(ineSelfie != null && !ineSelfie.equals("")) image_ine_selfie = new File(ROOT_PATH + "Documentos/"+ineSelfie);
            if (image_ine_selfie != null) {
                RequestBody imageBody =
                        RequestBody.create(
                                MediaType.parse("image/*"), image_ine_selfie);

                ine_selfie = MultipartBody.Part.createFormData("identificacion_selfie", image_ine_selfie.getName(), imageBody);
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

            RequestBody solicitudIdBody = RequestBody.create(MultipartBody.FORM, id_solicitud_grupal);

            RequestBody solicitudIntegranteIdBody = RequestBody.create(MultipartBody.FORM, id_solicitud_integrante);

            ManagerInterface api = new RetrofitClient().generalRF(CONTROLLER_SOLICITUDES, ctx).create(ManagerInterface.class);

            Call<MResSaveSolicitud> call = api.guardarOriginacionGpo("Bearer "+ session.getUser().get(7),
                    solicitudBody,
                    fachada_cliente,
                    firma_cliente,
                    fachada_negocio,
                    foto_ine_frontal,
                    foto_ine_reverso,
                    foto_curp,
                    foto_comprobante,
                    solicitudIdBody,
                    solicitudIntegranteIdBody,
                    ine_selfie);

            call.enqueue(new Callback<MResSaveSolicitud>() {
                @Override
                public void onResponse(Call<MResSaveSolicitud> call, Response<MResSaveSolicitud> response) {
                    Log.e("Respuesta code", response.code()+" codigo");

                    switch (response.code()){
                        case 200:
                            MResSaveSolicitud res = response.body();
                            ContentValues cv = new ContentValues();
                            cv.put("id_solicitud_integrante", res.getIdSolicitud());
                            cv.put("estatus_completado", 2);
                            db.update(TBL_INTEGRANTES_GPO, cv, "id = ?", new String[]{id});

                            String sql = "SELECT id_solicitud_integrante FROM " + TBL_INTEGRANTES_GPO + " WHERE id_credito = ? AND id_solicitud_integrante = ?";
                            Cursor r = db.rawQuery(sql, new String[]{credito, "0"});
                            if (r.getCount() == 0){
                                cv = new ContentValues();
                                cv.put("estatus", "2");
                                cv.put("fecha_guardado", Miscellaneous.ObtenerFecha(TIMESTAMP));
                                db.update(TBL_SOLICITUDES, cv, "id_solicitud = ?", new String[]{id_solicitud});
                            }

                            if(iIndex + 1 < iTotal)
                            {
                                SendIntegranteOriginacionGpo(ctx, iIndex + 1, iTotal, sDato0, lDato4, sDato6, sDato12, sDato14, sDato15, sDato16, sDato17, sDato19);
                            }
                            break;
                        case 500:
                            Log.e("500", response.message());
                            Log.e("500", response.errorBody().toString());
                            if(iIndex + 1 < iTotal)
                            {
                                SendIntegranteOriginacionGpo(ctx, iIndex + 1, iTotal, sDato0, lDato4, sDato6, sDato12, sDato14, sDato15, sDato16, sDato17, sDato19);
                            }
                            break;
                        default:
                            if(iIndex + 1 < iTotal)
                            {
                                SendIntegranteOriginacionGpo(ctx, iIndex + 1, iTotal, sDato0, lDato4, sDato6, sDato12, sDato14, sDato15, sDato16, sDato17, sDato19);
                            }
                            break;
                    }

                }

                @Override
                public void onFailure(Call<MResSaveSolicitud> call, Throwable t) {
                    Log.e("Error", "failSolicitud"+t.getMessage());
                    if(iIndex + 1 < iTotal)
                    {
                        SendIntegranteOriginacionGpo(ctx, iIndex + 1, iTotal, sDato0, lDato4, sDato6, sDato12, sDato14, sDato15, sDato16, sDato17, sDato19);
                    }
                }
            });

            return "";
        }
    }

    public class GuardarRenovacionGpo extends AsyncTask<Object, Void, String>{

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
            final String id_solicitud_integrante = (String) params[12];
            final String id_solicitud_grupal = (String) params[13];
            int iIndex = (int) params[14];
            int iTotal = (int) params[15];
            String sDato0 = (String) params[16];
            Long lDato4 = (Long) params[17];
            String sDato6 = (String) params[18];
            String sDato7 = (String) params[19];
            String sDato12 = (String) params[20];
            String sDato14 = (String) params[21];
            String sDato15 = (String) params[22];
            String sDato16 = (String) params[23];
            String sDato17 = (String) params[24];
            String sDato19 = (String) params[25];
            String sDato21 = (String) params[26];
            String sDato23 = (String) params[27];
            String ineSelfie = (String) params[28];

            SessionManager session = new SessionManager(ctx);
            DBhelper dBhelper = new DBhelper(ctx);
            final SQLiteDatabase db = dBhelper.getWritableDatabase();


            MultipartBody.Part fachada_cliente = null;
            File image_fachada_cliente = null;
            if(fachadaCli != null && !fachadaCli.equals("")) image_fachada_cliente = new File(ROOT_PATH + "Fachada/"+fachadaCli);
            if (image_fachada_cliente != null) {
                RequestBody imageBodyFachadaCli =
                        RequestBody.create(
                                MediaType.parse("image/*"), image_fachada_cliente);

                fachada_cliente = MultipartBody.Part.createFormData("fachada_cliente", image_fachada_cliente.getName(), imageBodyFachadaCli);
            }

            MultipartBody.Part firma_cliente = null;
            File image_firma_cliente = new File(ROOT_PATH + "Firma/"+firmaCli);
            if (image_firma_cliente != null) {
                RequestBody imageBodyFirmaCli =
                        RequestBody.create(
                                MediaType.parse("image/*"), image_firma_cliente);

                firma_cliente = MultipartBody.Part.createFormData("firma_cliente", image_firma_cliente.getName(), imageBodyFirmaCli);
            }

            MultipartBody.Part fachada_negocio = null;
            File image_fachada_negocio = null;
            if(fachadaNeg != null && !fachadaNeg.equals("")) image_fachada_negocio = new File(ROOT_PATH + "Fachada/"+fachadaNeg);
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
            File image_curp = null;
            if(curp != null && !curp.equals("")) image_curp =  new File(ROOT_PATH + "Documentos/"+curp);
            if (image_curp != null) {
                RequestBody imageBody =
                        RequestBody.create(
                                MediaType.parse("image/*"), image_curp);

                foto_curp = MultipartBody.Part.createFormData("curp", image_curp.getName(), imageBody);
            }
            else{
                RequestBody attachmentEmpty = RequestBody.create(MediaType.parse("text/plain"), "");
                foto_curp = MultipartBody.Part.createFormData("curp", "", attachmentEmpty);
            }

            MultipartBody.Part foto_comprobante = null;
            File image_comprobante = new File(ROOT_PATH + "Documentos/"+comprobante);
            if (image_comprobante != null) {
                RequestBody imageBody =
                        RequestBody.create(
                                MediaType.parse("image/*"), image_comprobante);

                foto_comprobante = MultipartBody.Part.createFormData("comprobante_domicilio", image_comprobante.getName(), imageBody);
            }

            MultipartBody.Part ine_selfie = null;
            File image_ine_selfie = null;
            if(ineSelfie != null && !ineSelfie.equals("")) image_ine_selfie = new File(ROOT_PATH + "Documentos/"+ineSelfie);
            if (image_ine_selfie != null) {
                RequestBody imageBody =
                        RequestBody.create(
                                MediaType.parse("image/*"), image_ine_selfie);

                ine_selfie = MultipartBody.Part.createFormData("identificacion_selfie", image_ine_selfie.getName(), imageBody);
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
            RequestBody solicitudIdBody = RequestBody.create(MultipartBody.FORM, id_solicitud_grupal);
            RequestBody solicitudIntegranteIdBody = RequestBody.create(MultipartBody.FORM, id_solicitud_integrante);

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
                    foto_comprobante,
                    solicitudIdBody,
                    solicitudIntegranteIdBody,
                    ine_selfie);

            call.enqueue(new Callback<MResSaveSolicitud>() {
                @Override
                public void onResponse(Call<MResSaveSolicitud> call, Response<MResSaveSolicitud> response) {
                    Log.e("Respuesta code", response.code()+" codigo");

                    switch (response.code()){
                        case 200:
                            MResSaveSolicitud res = response.body();
                            ContentValues cv = new ContentValues();
                            cv.put("id_solicitud_integrante",res.getIdSolicitud());
                            cv.put("estatus_completado",2);
                            db.update(TBL_INTEGRANTES_GPO_REN, cv, "id = ?", new String[]{id});

                            String sql = "SELECT id_solicitud_integrante FROM " + TBL_INTEGRANTES_GPO_REN + " WHERE id_credito = ? AND id_solicitud_integrante = ?";
                            Cursor r = db.rawQuery(sql, new String[]{credito, "0"});
                            if (r.getCount() == 0){
                                cv = new ContentValues();
                                cv.put("estatus", "2");
                                cv.put("fecha_guardado", Miscellaneous.ObtenerFecha(TIMESTAMP));
                                db.update(TBL_SOLICITUDES_REN, cv, "id_solicitud = ?", new String[]{id_solicitud});
                            }
                            if(iIndex + 1 < iTotal)
                            {
                                SendIntegranteRenovacionGpo(ctx, iIndex + 1, iTotal, sDato0, lDato4, sDato6, sDato7, sDato12, sDato14, sDato15, sDato16, sDato17, sDato19, sDato21, sDato23);
                            }
                            break;
                        default:
                            try {
                                Log.e("ERROR " + response.code(), response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            if(iIndex + 1 < iTotal)
                            {
                                SendIntegranteRenovacionGpo(ctx, iIndex + 1, iTotal, sDato0, lDato4, sDato6, sDato7, sDato12, sDato14, sDato15, sDato16, sDato17, sDato19, sDato21, sDato23);
                            }
                            break;
                    }

                }

                @Override
                public void onFailure(Call<MResSaveSolicitud> call, Throwable t) {
                    Log.e("Error", t.getMessage());
                    Log.e("Error", "failSolicitud");
                    if(iIndex + 1 < iTotal)
                    {
                        SendIntegranteRenovacionGpo(ctx, iIndex + 1, iTotal, sDato0, lDato4, sDato6, sDato7, sDato12, sDato14, sDato15, sDato16, sDato17, sDato19, sDato21, sDato23);
                    }
                }
            });

            return "";
        }
    }

    public class SendMontoAutorizado extends AsyncTask<Object, Void, String>{

        @Override
        protected String doInBackground(Object... params) {

            Context ctx = (Context) params[0];
            final String id = (String) params[1];
            final Long tipoSolicitud = (Long) params[2];
            Long solicitudId = (Long) params[3];
            Long montoAuto = (Long) params[4];
            final Long creditoId = (Long) params[5];
            final Long solitudIdMovil = (Long) params[6];

            //Long tipo = (tipoSolicitud.equals("ORIGINACION"))?1L:2L;

            SessionManager session = new SessionManager(ctx);
            DBhelper dBhelper = new DBhelper(ctx);
            final SQLiteDatabase db = dBhelper.getWritableDatabase();

            ManagerInterface api = new RetrofitClient().generalRF(CONTROLLER_MOVIL, ctx).create(ManagerInterface.class);

            Call<MResponseDefault> call = api.postMontoAutorizado(
                                                        tipoSolicitud,
                                                        solicitudId,
                                                        montoAuto,
                                             "Bearer "+ session.getUser().get(7));

            call.enqueue(new Callback<MResponseDefault>() {
                @Override
                public void onResponse(Call<MResponseDefault> call, Response<MResponseDefault> response) {
                    Log.e("CodeMontoAuto", "Code: "+response.code());
                    switch (response.code()){
                        case 200:
                            ContentValues cv = new ContentValues();
                            if (tipoSolicitud == 1) {
                                cv.put("estatus", 2);
                                cv.put("fecha_envio", Miscellaneous.ObtenerFecha(TIMESTAMP));
                                db.update(TBL_SOLICITUDES_AUTO, cv, "id_solicitud = ?", new String[]{id});
                            }
                            else{
                                cv.put("estatus_completado", 2);
                                db.update(TBL_INTEGRANTES_GPO_AUTO, cv, "id = ?", new String[]{id});

                                String sql = "SELECT * FROM " + TBL_INTEGRANTES_GPO_AUTO + " WHERE id_credito = ? AND estatus_completado = ?";
                                Cursor r = db.rawQuery(sql, new String[]{String.valueOf(creditoId), "1"});
                                if (r.getCount() == 0){
                                    cv = new ContentValues();
                                    cv.put("estatus", "2");
                                    cv.put("fecha_envio", Miscellaneous.ObtenerFecha(TIMESTAMP));
                                    db.update(TBL_SOLICITUDES_AUTO, cv, "id_solicitud = ?", new String[]{String.valueOf(solitudIdMovil)});
                                }
                            }
                            break;
                    }
                }

                @Override
                public void onFailure(Call<MResponseDefault> call, Throwable t) {
                    Log.e("CodeMontoAuto", "Fail: "+t.getMessage());
                }
            });

            return "";
        }
    }

    public class GuardarAgf extends AsyncTask<Object, Void, String>{

        @Override
        protected String doInBackground(final Object... params) {
            Context ctx     = (Context) params[0];
            Gestion gestion = (Gestion) params[1];
            Recibo recibo   = (Recibo) params[2];

            GestionDao gestionDao = new GestionDao(ctx);
            ReciboDao reciboDao = new ReciboDao(ctx);

            SessionManager session = new SessionManager(ctx);
            ApoyoGastosFunerariosService agfService = new RetrofitClient().newInstance(ctx).create(ApoyoGastosFunerariosService.class);

            MultipartBody.Part foto = null;

            if(gestion.getEstatus() != null && !gestion.getEvidencia().isEmpty() && gestion.getEvidencia() != null)
            {
                File image_foto = new File(ROOT_PATH + "Evidencia/"+ gestion.getEvidencia());
                if (image_foto != null)
                {
                    RequestBody imageEvidencia = RequestBody.create(MediaType.parse("image/*"), image_foto);
                    foto = MultipartBody.Part.createFormData("foto", image_foto.getName(), imageEvidencia);
                }
            }

            RequestBody grupoIdBody = RequestBody.create(MultipartBody.FORM, gestion.getGrupoId());
            RequestBody numSolicitudBody = RequestBody.create(MultipartBody.FORM, gestion.getNumSolicitud());
            RequestBody nombreBody = RequestBody.create(MultipartBody.FORM, gestion.getNombre());
            RequestBody medioPagoIdBody = RequestBody.create(MultipartBody.FORM, String.valueOf(Miscellaneous.GetMedioPagoId(gestion.getMedioPago())));
            RequestBody evidenciaBody = RequestBody.create(MultipartBody.FORM, gestion.getEvidencia());
            RequestBody tipoImagenBody = RequestBody.create(MultipartBody.FORM, (gestion.getTipoImagen().equals("GALERIA")?"2":"1"));
            RequestBody folioManualBody = RequestBody.create(MultipartBody.FORM, gestion.getFolioManual());
            RequestBody clienteIdBody = RequestBody.create(MultipartBody.FORM, gestion.getClienteId());
            RequestBody tipoBody = RequestBody.create(MultipartBody.FORM, gestion.getTipo());
            RequestBody fechaTerminoBody = RequestBody.create(MultipartBody.FORM, gestion.getFechaTermino());
            RequestBody folioBody;
            RequestBody tipoImpresionBody;
            RequestBody fechaImpresoBody;

            folioBody = RequestBody.create(MultipartBody.FORM, "");
            tipoImpresionBody = RequestBody.create(MultipartBody.FORM, "");
            fechaImpresoBody = RequestBody.create(MultipartBody.FORM, "");

            if (Miscellaneous.GetMedioPagoId(gestion.getMedioPago()) == 6 &&  (gestion.getFolioManual().isEmpty() || gestion.getFolioManual() == null) && recibo.getTipoImpresion() != null) {
                folioBody = RequestBody.create(MultipartBody.FORM, "AGF-" + session.getUser().get(0) + "-" + recibo.getFolio());
                tipoImpresionBody = RequestBody.create(MultipartBody.FORM, recibo.getTipoImpresion());
                fechaImpresoBody = RequestBody.create(MultipartBody.FORM, recibo.getFechaImpresion());
            }

            RequestBody montoBody = RequestBody.create(MultipartBody.FORM, gestion.getMonto());

            RequestBody fechaEnvioBody;

            if(recibo.getFechaEnvio() != null && !recibo.getFechaEnvio().trim().equals(""))
            {
                fechaEnvioBody = RequestBody.create(MultipartBody.FORM, recibo.getFechaEnvio());
            }
            else
            {
                fechaEnvioBody = RequestBody.create(MultipartBody.FORM, Miscellaneous.ObtenerFecha(TIMESTAMP));
            }

            RequestBody plazoBody = RequestBody.create(MultipartBody.FORM, String.valueOf(recibo.getPlazo()));
            RequestBody totalIntegrantes = RequestBody.create(MultipartBody.FORM, String.valueOf(gestion.getTotalIntegrantes()));
            RequestBody totalIntegrantesManual = RequestBody.create(MultipartBody.FORM, String.valueOf(gestion.getTotalIntegrantesManual()));

            Call<ApoyoGastosFunerariosResponse> call = agfService.store(
                "Bearer " + session.getUser().get(7),
                grupoIdBody,
                numSolicitudBody,
                nombreBody,
                medioPagoIdBody,
                evidenciaBody,
                tipoImagenBody,
                folioBody,
                tipoImpresionBody,
                montoBody,
                fechaImpresoBody,
                fechaEnvioBody,
                folioManualBody,
                clienteIdBody,
                tipoBody,
                fechaTerminoBody,
                foto,
                plazoBody,
                totalIntegrantes,
                totalIntegrantesManual
            );

            call.enqueue(new Callback<ApoyoGastosFunerariosResponse>() {
                @Override
                public void onResponse(Call<ApoyoGastosFunerariosResponse> call, Response<ApoyoGastosFunerariosResponse> response) {
                    switch (response.code()){
                        case 200:
                            if(gestion.getEstatus() == 0)
                            {
                                if(recibo.getId() != null)
                                {
                                    if(recibo.getFechaEnvio() == null || recibo.getFechaEnvio().trim().equals(""))
                                    {
                                        recibo.setFechaEnvio(Miscellaneous.ObtenerFecha(TIMESTAMP));
                                    }

                                    reciboDao.update(recibo.getId(), recibo);
                                }
                            }
                            else{
                                if(recibo.getId() != null) {
                                    if(recibo.getFechaEnvio() == null || recibo.getFechaEnvio().trim().equals(""))
                                    {
                                        recibo.setFechaEnvio(Miscellaneous.ObtenerFecha(TIMESTAMP));
                                    }

                                    recibo.setEstatus(1);
                                    reciboDao.update(recibo.getId(), recibo);
                                }

                                gestion.setEstatus(2);

                                if(gestion.getFechaEnvio() == null || gestion.getFechaEnvio().trim().equals(""))
                                {
                                    //gestion.setFechaEnvio(Miscellaneous.ObtenerFecha(TIMESTAMP));
                                    if(recibo.getFechaEnvio() != null)
                                    {
                                        gestion.setFechaEnvio(recibo.getFechaEnvio());
                                    }
                                    else {
                                        gestion.setFechaEnvio(Miscellaneous.ObtenerFecha(TIMESTAMP));
                                    }
                                }

                                gestionDao.update(gestion.getId(), gestion);
                            }

                            break;
                        default:
                            Log.e("AQUI ", response.message());
                            break;
                    }
                }
                @Override
                public void onFailure(Call<ApoyoGastosFunerariosResponse> call, Throwable t) {
                    Log.e("Error", "failAGF" + t.getMessage());
                }
            });

            return "";
        }
    }

    public class GuardarCC extends AsyncTask<Object, Void, String>{
        @Override
        protected String doInBackground(Object... params) {
            Context ctx = (Context) params[0];
            GestionCirculoCreditoDao gestionCirculoCreditoDao = new GestionCirculoCreditoDao(ctx);
            GestionCirculoCredito gestionCC = (GestionCirculoCredito) params[1];
            ReciboCirculoCreditoDao reciboCirculoCreditoDao = new ReciboCirculoCreditoDao(ctx);
            ReciboCirculoCredito reciboCC = (ReciboCirculoCredito) params[2];
            SessionManager session = new SessionManager(ctx);
            CirculoCreditoService circuloCreditoService = new RetrofitClient().newInstance(ctx).create(CirculoCreditoService.class);

            MultipartBody.Part foto = null;

            if(gestionCC.getEvidencia() != null && !gestionCC.getEvidencia().equals("")){
                File image_foto = new File(ROOT_PATH + "Evidencia/" + gestionCC.getEvidencia());
                if (image_foto != null)
                {
                    RequestBody imageEvidencia = RequestBody.create(MediaType.parse("image/*"), image_foto);
                    foto = MultipartBody.Part.createFormData("evidencia", image_foto.getName(), imageEvidencia);
                }
            }
            else
            {
                MultipartBody.Part.createFormData("evidencia", "");
            }

            RequestBody productoBody = RequestBody.create(MultipartBody.FORM, (gestionCC.getTipoCredito().equals("1"))?"CREDITO INDIVIDUAL":"CREDITO GRUPAL");
            RequestBody clienteGpoBody = RequestBody.create(MultipartBody.FORM, gestionCC.getNombreUno());
            RequestBody avalRepresentanteBody = RequestBody.create(MultipartBody.FORM, gestionCC.getNombreDos());
            RequestBody curpBody = RequestBody.create(MultipartBody.FORM, gestionCC.getCurp());
            RequestBody integrantesBody = RequestBody.create(MultipartBody.FORM, String.valueOf(gestionCC.getIntegrantes()));
            RequestBody montoBody = RequestBody.create(MultipartBody.FORM, gestionCC.getMonto());
            RequestBody medioPagoIdBody = RequestBody.create(MultipartBody.FORM, String.valueOf(Miscellaneous.GetMedioPagoId(gestionCC.getMedioPago())));
            RequestBody nombreImagenBody = RequestBody.create(MultipartBody.FORM, gestionCC.getEvidencia());
            RequestBody tipoImagenBody = RequestBody.create(MultipartBody.FORM, (gestionCC.getTipoImagen().equals("FOTOGRAFIA")?"1":"2"));
            RequestBody fechaTerminoBody = RequestBody.create(MultipartBody.FORM, gestionCC.getFechaTermino());
            RequestBody impresionBody;
            RequestBody folioBody;
            RequestBody tipoImpresionBody;
            RequestBody fechaImpresoBody;

            if (Miscellaneous.GetMedioPagoId(gestionCC.getMedioPago()) == 6 &&  reciboCC.getId() != null)
            {
                folioBody = RequestBody.create(MultipartBody.FORM, "CC-" + session.getUser().get(0) + "-" + reciboCC.getFolio());
                tipoImpresionBody = RequestBody.create(MultipartBody.FORM, reciboCC.getTipoImpresion());
                fechaImpresoBody = RequestBody.create(MultipartBody.FORM, reciboCC.getFechaImpresion());
                impresionBody = RequestBody.create(MultipartBody.FORM, "true");
            }
            else if(Miscellaneous.GetMedioPagoId(gestionCC.getMedioPago()) == 6 &&  reciboCC.getId() == null)
            {
                folioBody = RequestBody.create(MultipartBody.FORM, String.valueOf(gestionCC.getFolio()));
                tipoImpresionBody = RequestBody.create(MultipartBody.FORM, "");
                fechaImpresoBody = RequestBody.create(MultipartBody.FORM, "");
                impresionBody = RequestBody.create(MultipartBody.FORM, "false");
            }
            else {
                folioBody = RequestBody.create(MultipartBody.FORM, "");
                tipoImpresionBody = RequestBody.create(MultipartBody.FORM, "");
                fechaImpresoBody = RequestBody.create(MultipartBody.FORM, "");
                impresionBody = RequestBody.create(MultipartBody.FORM, "false");
            }

            //RequestBody fechaEnvioBody = RequestBody.create(MultipartBody.FORM, Miscellaneous.ObtenerFecha(TIMESTAMP));
            RequestBody fechaEnvioBody;

            if(reciboCC.getFechaEnvio() != null && !reciboCC.getFechaEnvio().trim().equals(""))
            {
                fechaEnvioBody = RequestBody.create(MultipartBody.FORM, reciboCC.getFechaEnvio());
            }
            else
            {
                fechaEnvioBody = RequestBody.create(MultipartBody.FORM, Miscellaneous.ObtenerFecha(TIMESTAMP));
            }

            Call<CirculoCreditoResponse> call = circuloCreditoService.store(
                "Bearer "+ session.getUser().get(7),
                productoBody,
                clienteGpoBody,
                avalRepresentanteBody,
                curpBody,
                integrantesBody,
                montoBody,
                medioPagoIdBody,
                nombreImagenBody,
                tipoImagenBody,
                impresionBody,
                folioBody,
                tipoImpresionBody,
                fechaImpresoBody,
                fechaEnvioBody,
                fechaTerminoBody,
                foto
            );

            call.enqueue(new Callback<CirculoCreditoResponse>() {
                @Override
                public void onResponse(Call<CirculoCreditoResponse> call, Response<CirculoCreditoResponse> response) {
                    if(response.code() == 200)
                    {
                        if(gestionCC.getEstatus() == 0)
                        {
                            if(reciboCC.getId() != null)
                            {
                                if(reciboCC.getFechaEnvio() == null || reciboCC.getFechaEnvio().trim().equals(""))
                                {
                                    reciboCC.setFechaEnvio(Miscellaneous.ObtenerFecha(TIMESTAMP));
                                }

                                reciboCirculoCreditoDao.update(reciboCC.getId(), reciboCC);
                            }
                        }
                        else
                        {
                            if(reciboCC.getId() != null)
                            {
                                if(reciboCC.getFechaEnvio() == null || reciboCC.getFechaEnvio().trim().equals(""))
                                {
                                    reciboCC.setFechaEnvio(Miscellaneous.ObtenerFecha(TIMESTAMP));
                                }

                                reciboCC.setEstatus(1);
                                reciboCirculoCreditoDao.update(reciboCC.getId(), reciboCC);
                            }

                            if(gestionCC.getFechaEnvio() == null || gestionCC.getFechaEnvio().trim().equals(""))
                            {
                                gestionCC.setFechaEnvio(Miscellaneous.ObtenerFecha(TIMESTAMP));
                            }

                            gestionCC.setEstatus(2);
                            gestionCirculoCreditoDao.update(gestionCC.getId(), gestionCC);
                        }
                    }
                    else
                    {
                        Log.e("AQUI ", response.message());
                    }
                }

                @Override
                public void onFailure(Call<CirculoCreditoResponse> call, Throwable t) {
                    Log.e("Error", "Fail CirculoCreditoService" + t.getMessage());
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

    public class PostConsulta extends AsyncTask<Object, Void, String>{

        @Override
        protected String doInBackground(Object... obj) {
            Context ctx = (Context) obj[0];
            final String id = (String) obj[1];
            final ConsultaCC cc = (ConsultaCC) obj[2];

            Log.e("Comienza", "Obtener la consulta: "+ Miscellaneous.ConvertToJson(cc));

            SessionManager session = new SessionManager(ctx);
            DBhelper dBhelper = new DBhelper(ctx);
            final SQLiteDatabase db = dBhelper.getWritableDatabase();

            ManagerInterface api = new RetrofitClient().generalRF(CONTROLLER_API, ctx).create(ManagerInterface.class);


                Call<MResConsultaCC> call = api.setConsultaCC(
                        "Bearer " + session.getUser().get(7),
                        cc);




            call.enqueue(new Callback<MResConsultaCC>() {
                @Override
                public void onResponse(Call<MResConsultaCC> call, Response<MResConsultaCC> response) {
                    System.out.println(response.body());
                    Log.e("response",Miscellaneous.ConvertToJson(response.errorBody()));
                    Log.e("response",Miscellaneous.ConvertToJson(response.body()));
                    MResConsultaCC r = response.body();

                    ContentValues cv = new ContentValues();
                    switch (response.code()){
                        case 200:
                            if (r.getOk() != null) {
                                int credAbierto = 0;
                                int credCerrado = 0;
                                float saldVencido = 0;
                                float saldActual = 0;
                                Double peorPago = 0.0;


                                for (MResConsultaCC.Credito item : r.getOk().getRes().getCreditos()){
                                    if (!Miscellaneous.validStr(item.getFechaCierreCuenta()).isEmpty()) {
                                        credCerrado += 1;
                                    }
                                    else {
                                        credAbierto += 1;

                                        saldVencido += item.getSaldoVencido();

                                        saldActual += item.getSaldoActual();

                                        if (item.getPeorAtraso() != null) {
                                            if (item.getPeorAtraso() > peorPago) {
                                                peorPago = item.getPeorAtraso();
                                            }
                                        }
                                    }
                                }

                                cv.put("saldo_actual", String.valueOf(saldActual));
                                cv.put("saldo_vencido", String.valueOf(saldVencido));
                                cv.put("peor_pago", String.valueOf(peorPago));
                                cv.put("creditos_abiertos", String.valueOf(credAbierto));
                                cv.put("credito_cerrados", String.valueOf(credCerrado));
                                cv.put("preautorizacion", String.valueOf(r.getOk().getPreautorizacion()));
                                cv.put("estatus", String.valueOf(response.code()));
                                db.update(TBL_CONSULTA_CC, cv, "_id = ?", new String[]{id});

                            }
                            break;
                        case 202:
                            if (r.getOk() != null) {

                                System.out.println(Miscellaneous.ConvertToJson(response));
                                cv.put("errores",""+r.getOk().getErr().getErrores().get(0).getMensaje());
                                cv.put("estatus", String.valueOf(response.code()));
                                cv.put("fecha_envio", Miscellaneous.ObtenerFecha(TIMESTAMP));
                                db.update(TBL_CONSULTA_CC, cv, "_id = ?", new String[]{id});
                            }
                            break;
                    }
                }

                @Override
                public void onFailure(Call<MResConsultaCC> call, Throwable t) {
                   Log.e("Fail", "Consulta CC: "+ t.getMessage());
                }
            });





            return "";
        }
    }

    public class GuardarVerDom extends AsyncTask<Object, Void, String>{
        @Override
        protected String doInBackground(final Object... params) {
            Context ctx = (Context) params[0];
            GestionVerificacionDomiciliaria gestion = (GestionVerificacionDomiciliaria) params[1];

            GestionVerificacionDomiciliariaDao gestionDao = new GestionVerificacionDomiciliariaDao(ctx);

            gestion.setFechaEnvio(Miscellaneous.ObtenerFecha("timestamp"));
            gestion.setEstatus(2);

            //JSONObject gestionJson = gestion.getJson();
            Gson gson = new Gson();
            String gestionJson = gson.toJson(gestion);

            SessionManager session = new SessionManager(ctx);
            GestionVerificacionDomiciliariaService gestionService = new RetrofitClient().newInstance(ctx).create(GestionVerificacionDomiciliariaService.class);

            MultipartBody.Part fotoFachada = null;

            if(!gestion.getFotoFachada().isEmpty() && gestion.getFotoFachada() != null)
            {
                File image_foto = new File(ROOT_PATH + "Fachada/"+ gestion.getFotoFachada());
                if (image_foto != null)
                {
                    RequestBody imageEvidencia = RequestBody.create(MediaType.parse("image/*"), image_foto);
                    fotoFachada = MultipartBody.Part.createFormData("foto_fachada", image_foto.getName(), imageEvidencia);
                }
            }

            RequestBody gestionBody = RequestBody.create(MultipartBody.FORM, gestionJson);

            Log.e("AQUI VERDOM", gestionJson);

            Call<GestionVerificacionDomiciliaria> call = gestionService.store(
                    "Bearer " + session.getUser().get(7),
                    gestionBody,
                    fotoFachada
            );

            call.enqueue(new Callback<GestionVerificacionDomiciliaria>() {
                @Override
                public void onResponse(Call<GestionVerificacionDomiciliaria> call, Response<GestionVerificacionDomiciliaria> response) {
                    switch (response.code()){
                        case 200:
                            gestionDao.update(gestion);
                            break;
                        default:
                            try {
                                Log.e("ERROR " + response.code(), response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            Log.e("ERROR " + response.code(), response.message());
                    }
                }
                @Override
                public void onFailure(Call<GestionVerificacionDomiciliaria> call, Throwable t) {
                    Log.e("ERROR ", t.getMessage());
                }
            });

            return "";
        }
    }

    public void GetServiciosSincronizados(Context ctx, boolean flag) {
        final AlertDialog loading = Popups.showLoadingDialog(ctx, R.string.please_wait, R.string.loading_info);
        loading.setCancelable(false);

        SessionManager session = new SessionManager(ctx);

        ServicioSincronizadoDao servicioSincronizadoDao = new ServicioSincronizadoDao(ctx);

        ServicioSincronizadoService servicioSincronizadoService = new RetrofitClient().newInstance(ctx).create(ServicioSincronizadoService.class);

        Call<List<ServicioSincronizado>> call = servicioSincronizadoService.show("Bearer "+ session.getUser().get(7));

        call.enqueue(new Callback<List<ServicioSincronizado>>() {
            @Override
            public void onResponse(Call<List<ServicioSincronizado>> call, Response<List<ServicioSincronizado>> response) {
                switch (response.code()){
                    case 200:
                        List<ServicioSincronizado> serviciosSincronizados = response.body();

                        if (serviciosSincronizados != null && serviciosSincronizados.size() > 0){
                            for (ServicioSincronizado item : serviciosSincronizados){
                                Log.e("AQUI SS ID", String.valueOf(item.getId()));

                                ServicioSincronizado servicioSincronizado = servicioSincronizadoDao.findById(item.getId());

                                if(servicioSincronizado != null)
                                {
                                    servicioSincronizadoDao.update(item);
                                }
                                else
                                {
                                    servicioSincronizadoDao.store(item);
                                }
                            }
                        }
                        loading.dismiss();
                        break;
                    default:
                        Log.e("ERROR AQUI PREST AUT", response.message());
                        loading.dismiss();
                        break;
                }
            }

            @Override
            public void onFailure(Call<List<ServicioSincronizado>> call, Throwable t) {
                Log.e("ErrorAgf", "Fail AGG"+t.getMessage());
                loading.dismiss();
                t.printStackTrace();
            }
        });
    }



}
