package com.sidert.sidertmovil.utils;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.google.gson.GsonBuilder;
import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.database.DBhelper;
import com.sidert.sidertmovil.database.SidertTables;
import com.sidert.sidertmovil.models.MAmortizacion;
import com.sidert.sidertmovil.models.MAval;
import com.sidert.sidertmovil.models.MCartera;
import com.sidert.sidertmovil.models.MIntegrante;
import com.sidert.sidertmovil.models.MPago;
import com.sidert.sidertmovil.models.MPrestamoGpoRes;
import com.sidert.sidertmovil.models.MPrestamoRes;
import com.sidert.sidertmovil.models.MResSaveOriginacionInd;
import com.sidert.sidertmovil.models.MRespuestaGestion;
import com.sidert.sidertmovil.models.ModeloGeolocalizacion;
import com.sidert.sidertmovil.models.ModeloResSaveGeo;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
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

import static com.sidert.sidertmovil.utils.Constants.CONTROLLER_FICHAS;
import static com.sidert.sidertmovil.utils.Constants.CONTROLLER_MOVIL;
import static com.sidert.sidertmovil.utils.Constants.ENVIROMENT;
import static com.sidert.sidertmovil.utils.Constants.TBL_AMORTIZACIONES;
import static com.sidert.sidertmovil.utils.Constants.TBL_AMORTIZACIONES_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_AVAL;
import static com.sidert.sidertmovil.utils.Constants.TBL_AVAL_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_CARTERA_GPO;
import static com.sidert.sidertmovil.utils.Constants.TBL_CARTERA_GPO_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_CARTERA_IND;
import static com.sidert.sidertmovil.utils.Constants.TBL_CARTERA_IND_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_MIEMBROS_GPO;
import static com.sidert.sidertmovil.utils.Constants.TBL_MIEMBROS_GPO_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_PAGOS;
import static com.sidert.sidertmovil.utils.Constants.TBL_PAGOS_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_PRESTAMOS_GPO;
import static com.sidert.sidertmovil.utils.Constants.TBL_PRESTAMOS_GPO_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_PRESTAMOS_IND;
import static com.sidert.sidertmovil.utils.Constants.TBL_PRESTAMOS_IND_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_RESPUESTAS_GPO;
import static com.sidert.sidertmovil.utils.Constants.TBL_RESPUESTAS_GPO_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_RESPUESTAS_IND;
import static com.sidert.sidertmovil.utils.Constants.TBL_RESPUESTAS_IND_T;
import static com.sidert.sidertmovil.utils.Constants.TIMESTAMP;

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

        ManagerInterface api = new RetrofitClient().generalRF(CONTROLLER_FICHAS).create(ManagerInterface.class);

        Call<ModeloGeolocalizacion> call = api.getGeolocalizcion("1",
                incluir_gestiones,
                "Bearer "+ session.getUser().get(7));
        call.enqueue(new Callback<ModeloGeolocalizacion>() {
            @Override
            public void onResponse(Call<ModeloGeolocalizacion> call, Response<ModeloGeolocalizacion> response) {

                Log.e("Geolocalizacion Code", ""+response.code());
                switch (response.code()) {
                    case 200:
                        ModeloGeolocalizacion modeloGeo = response.body();
                        HashMap<Integer, String> params;
                        if (modeloGeo.getGrupales().size() > 0){
                            for (int i = 0; i < modeloGeo.getGrupales().size(); i++){
                                Cursor rowGeo;
                                if (ENVIROMENT)
                                    rowGeo = dBhelper.getRecords(SidertTables.SidertEntry.TABLE_GEOLOCALIZACION, " WHERE ficha_id = '"+modeloGeo.getGrupales().get(i).getFicha_id()+"'", "", null);
                                else
                                    rowGeo = dBhelper.getRecords(SidertTables.SidertEntry.TABLE_GEOLOCALIZACION_T, " WHERE ficha_id = '"+modeloGeo.getGrupales().get(i).getFicha_id()+"'", "", null);
                                if (rowGeo.getCount() == 0) {
                                    params = new HashMap<>();
                                    params.put(0, String.valueOf(modeloGeo.getGrupales().get(i).getFicha_id()));
                                    params.put(1, modeloGeo.getGrupales().get(i).getAsesor_nombre());
                                    params.put(2, "2");
                                    params.put(3, modeloGeo.getGrupales().get(i).getGrupoNombre());
                                    params.put(4, String.valueOf(modeloGeo.getGrupales().get(i).getNumSolicitud()));
                                    params.put(5, String.valueOf(modeloGeo.getGrupales().get(i).getGrupoId()));
                                    params.put(6, String.valueOf(modeloGeo.getGrupales().get(i).getGrupoId()));
                                    params.put(7, Miscellaneous.GetIntegrante(modeloGeo.getGrupales().get(i).getIntegrantes(), "TESORERO").getClienteDireccion());
                                    params.put(8, Miscellaneous.GetIntegrante(modeloGeo.getGrupales().get(i).getIntegrantes(), "TESORERO").getClienteColonia());
                                    params.put(9, modeloGeo.getGrupales().get(i).getFechaEntrega());
                                    params.put(10, modeloGeo.getGrupales().get(i).getFechaVencimiento());
                                    params.put(11, Miscellaneous.JsonConvertGpo(modeloGeo.getGrupales().get(i)));
                                    params.put(12, "");
                                    params.put(13, "");
                                    params.put(14, "");
                                    params.put(15, "");
                                    params.put(16, "");
                                    params.put(17, "");
                                    params.put(18, "");
                                    params.put(19, "");
                                    params.put(20, "0");
                                    params.put(21, Miscellaneous.ObtenerFecha("timestamp"));
                                    if (ENVIROMENT)
                                        dBhelper.saveRecordsGeo(db, SidertTables.SidertEntry.TABLE_GEOLOCALIZACION, params);
                                    else
                                        dBhelper.saveRecordsGeo(db, SidertTables.SidertEntry.TABLE_GEOLOCALIZACION_T, params);
                                }//Fin de IF que no existe registro de gestion para ser guardado
                            }//Fin For de Guardado de gestiones grupales
                        }//Fin de Grupales

                        if (modeloGeo.getIndividuales().size() > 0){
                            for (int i = 0; i < modeloGeo.getIndividuales().size(); i++){
                                Cursor rowGeo;
                                if (ENVIROMENT)
                                    rowGeo = dBhelper.getRecords(SidertTables.SidertEntry.TABLE_GEOLOCALIZACION, " WHERE ficha_id = '"+modeloGeo.getIndividuales().get(i).getFicha_id()+"'", "", null);
                                else
                                    rowGeo = dBhelper.getRecords(SidertTables.SidertEntry.TABLE_GEOLOCALIZACION_T, " WHERE ficha_id = '"+modeloGeo.getIndividuales().get(i).getFicha_id()+"'", "", null);
                                if (rowGeo.getCount() == 0) {
                                    params = new HashMap<>();
                                    params.put(0, String.valueOf(modeloGeo.getIndividuales().get(i).getFicha_id()));
                                    params.put(1, modeloGeo.getIndividuales().get(i).getAsesor_nombre());
                                    params.put(2, "1");
                                    params.put(3, modeloGeo.getIndividuales().get(i).getClienteNombre());
                                    params.put(4, String.valueOf(modeloGeo.getIndividuales().get(i).getNumSolicitud()));
                                    params.put(5, String.valueOf(modeloGeo.getIndividuales().get(i).getClienteId()));
                                    params.put(6, String.valueOf(modeloGeo.getIndividuales().get(i).getClienteClave()));
                                    params.put(7, modeloGeo.getIndividuales().get(i).getClienteDireccion());
                                    params.put(8, modeloGeo.getIndividuales().get(i).getClienteColonia());
                                    params.put(9, modeloGeo.getIndividuales().get(i).getFechaEntrega());
                                    params.put(10, modeloGeo.getIndividuales().get(i).getFechaVencimiento());
                                    params.put(11, Miscellaneous.JsonConvertInd(modeloGeo.getIndividuales().get(i)));
                                    params.put(12, "");
                                    params.put(13, "");
                                    params.put(14, "");
                                    params.put(15, "0");
                                    params.put(16, "0");
                                    params.put(17, "0");
                                    params.put(18, "");
                                    params.put(19, "");
                                    params.put(20, "0");
                                    params.put(21, Miscellaneous.ObtenerFecha("timestamp"));
                                    if (ENVIROMENT)
                                        dBhelper.saveRecordsGeo(db, SidertTables.SidertEntry.TABLE_GEOLOCALIZACION, params);
                                    else
                                        dBhelper.saveRecordsGeo(db, SidertTables.SidertEntry.TABLE_GEOLOCALIZACION_T, params);
                                }//Fin de IF que no existe registro de gestion para ser guardado
                            }//Fin de For de Guardado de individuales
                        }//Fin de Individuales

                        if(modeloGeo.getGrupalesGestionadas().size() > 0){
                            for (int h = 0; h < modeloGeo.getGrupalesGestionadas().size(); h++){
                                Cursor rowGeoGG;
                                if (ENVIROMENT)
                                    rowGeoGG = dBhelper.getRecords(SidertTables.SidertEntry.TABLE_GEOLOCALIZACION, " WHERE ficha_id = '"+modeloGeo.getGrupalesGestionadas().get(h).getFichaId()+"'", "", null);
                                else
                                    rowGeoGG = dBhelper.getRecords(SidertTables.SidertEntry.TABLE_GEOLOCALIZACION_T, " WHERE ficha_id = '"+modeloGeo.getGrupalesGestionadas().get(h).getFichaId()+"'", "", null);

                                if (rowGeoGG.getCount() > 0){
                                    rowGeoGG.moveToFirst();

                                    switch(modeloGeo.getGrupalesGestionadas().get(h).getIntegranteTipo()){
                                        case Constants.TIPO_PRESIDENTE:
                                            if (rowGeoGG.getString(13).trim().isEmpty() && (rowGeoGG.getString(16).trim().isEmpty() || rowGeoGG.getString(16).equals("0"))){
                                                new DescargarFotoFachada()
                                                        .execute(new GsonBuilder().create().toJson(modeloGeo.getGrupalesGestionadas().get(h)), rowGeoGG.getString(21), ctx);
                                            }
                                            break;
                                        case Constants.TIPO_TESORERO:
                                            if (rowGeoGG.getString(14).trim().isEmpty() && (rowGeoGG.getString(17).trim().isEmpty() || rowGeoGG.getString(17).equals("0"))){
                                                new DescargarFotoFachada()
                                                        .execute(new GsonBuilder().create().toJson(modeloGeo.getGrupalesGestionadas().get(h)), rowGeoGG.getString(21), ctx);
                                            }
                                            break;
                                        case Constants.TIPO_SECRETARIO:
                                            if (rowGeoGG.getString(15).trim().isEmpty() && (rowGeoGG.getString(18).trim().isEmpty() || rowGeoGG.getString(18).equals("0"))){
                                                new DescargarFotoFachada()
                                                        .execute(new GsonBuilder().create().toJson(modeloGeo.getGrupalesGestionadas().get(h)), rowGeoGG.getString(21), ctx);
                                            }
                                            break;
                                    }//Fin switch para ser actilizado el registro
                                }//Fin de IF que existe registro de gestion
                            }//Fin de For para guardado de Grupales Gestionadas
                        }//Fin de Grupales Gestionadas

                        Log.e("Individuales conte", modeloGeo.getIndividualesGestionadas().size()+" ------------");
                        if(modeloGeo.getIndividualesGestionadas().size() > 0){
                            for (int h = 0; h < modeloGeo.getIndividualesGestionadas().size(); h++){
                                Cursor rowGeoGG;
                                if (ENVIROMENT)
                                    rowGeoGG = dBhelper.getRecords(SidertTables.SidertEntry.TABLE_GEOLOCALIZACION, " WHERE ficha_id = '"+modeloGeo.getIndividualesGestionadas().get(h).getFichaId()+"'", "", null);
                                else
                                    rowGeoGG = dBhelper.getRecords(SidertTables.SidertEntry.TABLE_GEOLOCALIZACION_T, " WHERE ficha_id = '"+modeloGeo.getIndividualesGestionadas().get(h).getFichaId()+"'", "", null);

                                Log.e("siContiene", rowGeoGG.getCount()+"++++++++++");
                                if (rowGeoGG.getCount() > 0){
                                    rowGeoGG.moveToFirst();

                                    switch(modeloGeo.getIndividualesGestionadas().get(h).getTipo()){
                                        case Constants.TIPO_CLIENTE:
                                            Log.e("datos", rowGeoGG.getString(13));
                                            Log.e("fecha", rowGeoGG.getString(16));
                                            Log.e("Cliente", (rowGeoGG.getString(13).trim().isEmpty() && (rowGeoGG.getString(16).trim().isEmpty() || rowGeoGG.getString(16).equals("0")))+"  xxxxx   ");
                                            if (rowGeoGG.getString(13).trim().isEmpty() && rowGeoGG.getString(16).trim().isEmpty()){
                                                new DescargarFotoFachada()
                                                        .execute(new GsonBuilder().create().toJson(modeloGeo.getIndividualesGestionadas().get(h)), rowGeoGG.getString(21), ctx);
                                            }
                                            break;
                                        case Constants.TIPO_NEGOCIO:
                                            if (rowGeoGG.getString(14).trim().isEmpty() && (rowGeoGG.getString(17).trim().isEmpty() || rowGeoGG.getString(17).equals("0"))){
                                                new DescargarFotoFachada()
                                                        .execute(new GsonBuilder().create().toJson(modeloGeo.getIndividualesGestionadas().get(h)), rowGeoGG.getString(21), ctx);
                                            }
                                            break;

                                        case Constants.TIPO_AVAL:
                                            if (rowGeoGG.getString(15).trim().isEmpty() && (rowGeoGG.getString(18).trim().isEmpty() || rowGeoGG.getString(18).equals("0"))){
                                                new DescargarFotoFachada()
                                                        .execute(new GsonBuilder().create().toJson(modeloGeo.getIndividualesGestionadas().get(h)), rowGeoGG.getString(21), ctx);
                                            }
                                            break;
                                    }//Fin switch para ser actilizado el registro
                                }//Fin de IF que existe registro de gestion
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
        SessionManager session = new SessionManager(ctx);
        final DBhelper dBhelper = new DBhelper(ctx);
        final SQLiteDatabase db = dBhelper.getWritableDatabase();

        Cursor row;
        if (ENVIROMENT)
            row = dBhelper.getRecords(SidertTables.SidertEntry.TABLE_GEOLOCALIZACION, "", "", null);
        else
            row = dBhelper.getRecords(SidertTables.SidertEntry.TABLE_GEOLOCALIZACION_T, "", "", null);

        if (row.getCount() > 0){
            row.moveToFirst();
            for(int i = 0; i < row.getCount(); i++){
                if ((row.getString(16).isEmpty() || row.getString(16).equals("0")) && !row.getString(13).isEmpty()){
                    Log.e("row", row.getString(13));
                }
                //Log.e("fecha_uno",String.valueOf(row.getString(16).isEmpty() && !row.getString(13).isEmpty())+" "+row.getString(16));
                if (row.getString(16).isEmpty() && !row.getString(13).isEmpty())
                    SendGeolocalizacion(ctx, row.getString(13), row.getString(22), row.getString(1), 1);
                if (row.getString(17).isEmpty() && !row.getString(14).isEmpty())
                    SendGeolocalizacion(ctx, row.getString(14), row.getString(22), row.getString(1), 2);
                if (row.getString(18).isEmpty() && !row.getString(15).isEmpty())
                    SendGeolocalizacion(ctx, row.getString(15), row.getString(22), row.getString(1), 3);
                row.moveToNext();
            }
        }

        if (flag)
            loading.dismiss();
    }

    private void SendGeolocalizacion(final Context ctx, String respuesta, final String fecha_dispositivo, final String ficha_id, final int modulo){
        SessionManager session = new SessionManager(ctx);
        DBhelper dBhelper = new DBhelper(ctx);
        final SQLiteDatabase db = dBhelper.getWritableDatabase();
        if (NetworkStatus.haveNetworkConnection(ctx)){
            try {

                JSONObject jsonRes = new JSONObject(respuesta);

                final File image = new File(Constants.ROOT_PATH + "Fachada/"+jsonRes.getString(Constants.FACHADA));

                RequestBody ficha_idBody = RequestBody.create(MultipartBody.FORM, ficha_id);
                RequestBody latBody = RequestBody.create(MultipartBody.FORM, jsonRes.getString(Constants.LATITUD));
                RequestBody lngBody = RequestBody.create(MultipartBody.FORM, jsonRes.getString(Constants.LONGITUD));
                RequestBody direccionBody = RequestBody.create(MultipartBody.FORM, jsonRes.getString(Constants.DIRECCION));
                RequestBody barcodeBody = RequestBody.create(MultipartBody.FORM, jsonRes.getString(Constants.CODEBARS));
                RequestBody fechaDispositivoBody = RequestBody.create(MultipartBody.FORM, fecha_dispositivo);
                RequestBody fechaGestionIniBody;
                if (jsonRes.has(Constants.FECHA_INI))
                    fechaGestionIniBody = RequestBody.create(MultipartBody.FORM, jsonRes.getString(Constants.FECHA_INI));
                else
                    fechaGestionIniBody = RequestBody.create(MultipartBody.FORM, jsonRes.getString(Constants.FECHA));
                RequestBody fechaGestionFinBody = RequestBody.create(MultipartBody.FORM, jsonRes.getString(Constants.FECHA));
                RequestBody fechaEnvioBody = RequestBody.create(MultipartBody.FORM, Miscellaneous.ObtenerFecha("timestamp"));
                RequestBody comentarioBody = RequestBody.create(MultipartBody.FORM, jsonRes.getString(Constants.COMENTARIO));
                RequestBody tipoBody = RequestBody.create(MultipartBody.FORM, jsonRes.getString(Constants.TIPO));
                MultipartBody.Part body = null;
                if (image != null) {
                    RequestBody imageBody =
                            RequestBody.create(
                                    MediaType.parse("image/*"), image);

                    body = MultipartBody.Part.createFormData("foto_fachada", image.getName(), imageBody);
                }

                ManagerInterface api = new RetrofitClient().generalRF(CONTROLLER_FICHAS).create(ManagerInterface.class);

                Call<ModeloResSaveGeo> call = api.guardarGeo("Bearer "+ session.getUser().get(7),
                                                                ficha_idBody,
                                                                latBody,
                                                                lngBody,
                                                                direccionBody,
                                                                barcodeBody,
                                                                comentarioBody,
                                                                tipoBody,
                                                                fechaDispositivoBody,
                                                                fechaGestionIniBody,
                                                                fechaGestionFinBody,
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

                                if (modulo == 1){
                                    valores.put("fecha_env_uno",Miscellaneous.ObtenerFecha("timestamp"));
                                }
                                else if (modulo == 2){
                                    valores.put("fecha_env_dos",Miscellaneous.ObtenerFecha("timestamp"));
                                }
                                else if (modulo == 3){
                                    valores.put("fecha_env_tres",Miscellaneous.ObtenerFecha("timestamp"));
                                }
                                valores.put("fecha_env",Miscellaneous.ObtenerFecha("timestamp"));
                                if (ENVIROMENT)
                                    db.update(SidertTables.SidertEntry.TABLE_GEOLOCALIZACION, valores, "ficha_id = '"+ficha_id+"'", null);
                                else
                                    db.update(SidertTables.SidertEntry.TABLE_GEOLOCALIZACION_T, valores, "ficha_id = '"+ficha_id+"'", null);


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

    public void SaveRespuestaGestion(Context ctx, boolean flag){

        final AlertDialog loading = Popups.showLoadingDialog(ctx, R.string.please_wait, R.string.loading_info);

        if (flag)
            loading.show();
        SessionManager session = new SessionManager(ctx);
        final DBhelper dBhelper = new DBhelper(ctx);
        final SQLiteDatabase db = dBhelper.getWritableDatabase();

        Cursor row;

        String query;
        if (ENVIROMENT)
            query = "SELECT * FROM (SELECT i._id,i.id_prestamo,i.latitud,i.longitud,i.contacto,i.motivo_aclaracion,i.comentario,i.actualizar_telefono,i.nuevo_telefono,i.resultado_gestion,i.motivo_no_pago,i.fecha_fallecimiento,i.medio_pago,i.fecha_pago,i.pagara_requerido AS x,i.pago_realizado,i.imprimir_recibo,i.folio,i.evidencia,i.tipo_imagen,i.gerente,i.firma,i.fecha_inicio,i.fecha_fin,i.res_impresion,i.estatus_pago,i.saldo_corte,i.saldo_actual,'1' AS tipo_gestion,pi.num_solicitud,pi.fecha_establecida, ci.dia AS dia_semana, pi.monto_requerido, pi.tipo_cartera, pi.monto_amortizacion, i.dias_atraso FROM "+ TBL_RESPUESTAS_IND + " AS i INNER JOIN " + TBL_PRESTAMOS_IND + " AS pi ON i.id_prestamo = pi.id_prestamo INNER JOIN " + TBL_CARTERA_IND + " AS ci ON pi.id_cliente = ci.id_cartera WHERE i.estatus = ? UNION SELECT  g._id,g.id_prestamo,g.latitud,g.longitud,g.contacto,g.motivo_aclaracion,g.comentario,g.actualizar_telefono,g.nuevo_telefono,g.resultado_gestion,g.motivo_no_pago,g.fecha_fallecimiento,g.medio_pago,g.fecha_pago,g.detalle_ficha AS x,g.pago_realizado,g.imprimir_recibo,g.folio,g.evidencia,g.tipo_imagen,g.gerente,g.firma,g.fecha_inicio,g.fecha_fin,g.res_impresion,g.estatus_pago,g.saldo_corte,g.saldo_actual,'2' AS tipo_gestion,pg.num_solicitud,pg.fecha_establecida, cg.dia AS dia_semana, pg.monto_requerido, pg.tipo_cartera, pg.monto_amortizacion, g.dias_atraso FROM " + TBL_RESPUESTAS_GPO + " AS g INNER JOIN " + TBL_PRESTAMOS_GPO + " AS pg ON g.id_prestamo = pg.id_prestamo INNER JOIN " + TBL_CARTERA_GPO + " AS cg ON pg.id_grupo = cg.id_cartera WHERE g.estatus = ?) AS respuestas";
        else
            query = "SELECT * FROM (SELECT i._id,i.id_prestamo,i.latitud,i.longitud,i.contacto,i.motivo_aclaracion,i.comentario,i.actualizar_telefono,i.nuevo_telefono,i.resultado_gestion,i.motivo_no_pago,i.fecha_fallecimiento,i.medio_pago,i.fecha_pago,i.pagara_requerido AS x,i.pago_realizado,i.imprimir_recibo,i.folio,i.evidencia,i.tipo_imagen,i.gerente,i.firma,i.fecha_inicio,i.fecha_fin,i.res_impresion,i.estatus_pago,i.saldo_corte,i.saldo_actual,'1' AS tipo_gestion,pi.num_solicitud,pi.fecha_establecida, ci.dia AS dia_semana, pi.monto_requerido, pi.tipo_cartera, pi.monto_amortizacion, i.dias_atraso FROM "+ TBL_RESPUESTAS_IND_T + " AS i INNER JOIN " + TBL_PRESTAMOS_IND_T + " AS pi ON i.id_prestamo = pi.id_prestamo INNER JOIN " + TBL_CARTERA_IND_T + " AS ci ON pi.id_cliente = ci.id_cartera WHERE i.estatus = ? UNION SELECT  g._id,g.id_prestamo,g.latitud,g.longitud,g.contacto,g.motivo_aclaracion,g.comentario,g.actualizar_telefono,g.nuevo_telefono,g.resultado_gestion,g.motivo_no_pago,g.fecha_fallecimiento,g.medio_pago,g.fecha_pago,g.detalle_ficha AS x,g.pago_realizado,g.imprimir_recibo,g.folio,g.evidencia,g.tipo_imagen,g.gerente,g.firma,g.fecha_inicio,g.fecha_fin,g.res_impresion,g.estatus_pago,g.saldo_corte,g.saldo_actual,'2' AS tipo_gestion,pg.num_solicitud,pg.fecha_establecida, cg.dia AS dia_semana, pg.monto_requerido, pg.tipo_cartera, pg.monto_amortizacion, g.dias_atraso FROM " + TBL_RESPUESTAS_GPO_T + " AS g INNER JOIN " + TBL_PRESTAMOS_GPO_T + " AS pg ON g.id_prestamo = pg.id_prestamo INNER JOIN " + TBL_CARTERA_GPO_T + " AS cg ON pg.id_grupo = cg.id_cartera WHERE g.estatus = ?) AS respuestas";

        row = db.rawQuery(query, new String[]{"1", "1"});

        if (row.getCount() > 0){
            row.moveToFirst();

            for (int i = 0; i < row.getCount(); i++){
                HashMap<String, String> params = new HashMap<>();
                params.put("id_prestamo", row.getString(1));
                params.put("num_solicitud", row.getString(29));
                JSONObject json_res = Miscellaneous.RowTOJson(row, ctx);
                params.put("respuesta", json_res.toString());

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

                    SendrespuestaGestion(ctx, params, row.getString(0), evidencia, tipo_imagen, firma);
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

    private void SendrespuestaGestion(final Context ctx, HashMap<String, String> params, final String _id, String imagen, String tipo_imagen, String firma){
        SessionManager session = new SessionManager(ctx);
        final DBhelper dBhelper = new DBhelper(ctx);
        final SQLiteDatabase db = dBhelper.getWritableDatabase();
        if (NetworkStatus.haveNetworkConnection(ctx)) {
            RequestBody idPrestamoBody = RequestBody.create(MultipartBody.FORM, params.get("id_prestamo"));
            RequestBody numSolicitudBody = RequestBody.create(MultipartBody.FORM, params.get("num_solicitud"));
            RequestBody respuestaBody = RequestBody.create(MultipartBody.FORM, params.get("respuesta"));

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

            ManagerInterface api = new RetrofitClient().generalRF(CONTROLLER_MOVIL).create(ManagerInterface.class);

            Call<MRespuestaGestion> call = api.guardarRespuesta("Bearer "+ session.getUser().get(7),
                    idPrestamoBody,
                    numSolicitudBody,
                    respuestaBody,
                    evidenciaBody,
                    firmaBody);

            call.enqueue(new Callback<MRespuestaGestion>() {
                @Override
                public void onResponse(Call<MRespuestaGestion> call, Response<MRespuestaGestion> response) {
                    Log.e("ResponseSave", "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
                    MRespuestaGestion r = response.body();
                    switch (response.code()){
                        case 200:
                            ContentValues cv = new ContentValues();
                            cv.put("fecha_envio", Miscellaneous.ObtenerFecha(TIMESTAMP));
                            cv.put("estatus", 2);
                            if (ENVIROMENT)
                                db.update(TBL_RESPUESTAS_IND, cv, "_id = ?", new String[]{_id});
                            else
                                db.update(TBL_RESPUESTAS_IND_T, cv, "_id = ?", new String[]{_id});

                            break;
                        default:
                            Log.e("Mensaje Code", response.code()+" : "+response.message());
                            //Toast.makeText(ctx, "No se logró enviar codigo: " +response.code(), Toast.LENGTH_SHORT).show();
                            break;
                    }
                }

                @Override
                public void onFailure(Call<MRespuestaGestion> call, Throwable t) {
                    Log.e("FailSaveImagexxxxxx", t.getMessage());
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
            /*pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Cargando Imagen");
            pDialog.setCancelable(true);
            pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pDialog.show();*/
        }

        @Override
        protected String doInBackground(Object... params) {
            // TODO Auto-generated method stub
            Log.i("doInBackground" , "Entra en doInBackground");

            JSONObject jsonGeo = null;
            Context ctx = (Context) params[2];
            int status = Integer.parseInt((String) params[1]);
            try {
                JSONObject data = new JSONObject((String) params[0]);
                Log.e("DataGestion", data.toString());
                jsonGeo = new JSONObject();


                if (false){
                    jsonGeo.put(Constants.LATITUD, 0);
                    jsonGeo.put(Constants.LONGITUD, 0);
                }
                else{
                    jsonGeo.put(Constants.LATITUD, data.getDouble("latitud"));
                    jsonGeo.put(Constants.LONGITUD, data.getDouble("longitud"));
                }

                jsonGeo.put(Constants.DIRECCION, data.getString("direccion").trim());
                jsonGeo.put(Constants.CODEBARS, data.getString("barcode").trim());
                try {
                    if(!data.getString("foto_fachada").trim().isEmpty()) {
                        jsonGeo.put(Constants.FACHADA, Miscellaneous.save(
                                Miscellaneous.descargarImagen(
                                        WebServicesRoutes.BASE_URL +
                                                WebServicesRoutes.CONTROLLER_FICHAS +
                                                WebServicesRoutes.IMAGES_GEOLOCALIZACION +
                                                data.getString("foto_fachada")), 1));
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
                jsonGeo.put(Constants.COMENTARIO, data.getString("comentario").trim());
                jsonGeo.put(Constants.FECHA, data.getString("fecha_respuesta"));

                ContentValues valores = new ContentValues();
                switch (data.getString("integrante_tipo")){
                    case Constants.TIPO_CLIENTE:
                        jsonGeo.put(Constants.TIPO, Constants.TIPO_CLIENTE);
                        valores.put("res_uno",jsonGeo.toString());
                        valores.put("fecha_env_uno",data.getString("fecha_recepcion"));
                        break;
                    case Constants.TIPO_NEGOCIO:
                        jsonGeo.put(Constants.TIPO, Constants.TIPO_NEGOCIO);
                        valores.put("res_dos",jsonGeo.toString());
                        valores.put("fecha_env_dos",data.getString("fecha_recepcion"));
                        break;
                    case Constants.TIPO_AVAL:
                        jsonGeo.put(Constants.TIPO, Constants.TIPO_AVAL);
                        valores.put("res_tres",jsonGeo.toString());
                        valores.put("fecha_env_tres",data.getString("fecha_recepcion"));
                        break;
                    case Constants.TIPO_PRESIDENTE:
                        jsonGeo.put(Constants.TIPO, Constants.TIPO_PRESIDENTE);
                        valores.put("res_uno",jsonGeo.toString());
                        valores.put("fecha_env_uno",data.getString("fecha_recepcion"));
                        break;
                    case Constants.TIPO_TESORERO:
                        jsonGeo.put(Constants.TIPO, Constants.TIPO_TESORERO);
                        valores.put("res_dos",jsonGeo.toString());
                        valores.put("fecha_env_dos",data.getString("fecha_recepcion"));
                        break;
                    case Constants.TIPO_SECRETARIO:
                        jsonGeo.put(Constants.TIPO, Constants.TIPO_SECRETARIO);
                        valores.put("res_tres",jsonGeo.toString());
                        valores.put("fecha_env_tres",data.getString("fecha_recepcion"));
                        break;
                }

                valores.put("status", status+1);

                DBhelper dBhelper = new DBhelper(ctx);
                SQLiteDatabase db = dBhelper.getWritableDatabase();

                if (ENVIROMENT)
                    db.update(Constants.GEOLOCALIZACION, valores, "ficha_id = '"+data.getString("ficha_id")+"'", null);
                else
                    db.update(Constants.GEOLOCALIZACION_T, valores, "ficha_id = '"+data.getString("ficha_id")+"'", null);

            } catch (JSONException e) {
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
                        ManagerInterface api = new RetrofitClient().generalRF(Constants.CONTROLLER_SOLICITUDES).create(ManagerInterface.class);

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

    public void GetCartera(final  Context ctx){
        this.ctx = ctx;

        SessionManager session = new SessionManager(ctx);
        final AlertDialog loading = Popups.showLoadingDialog(ctx, R.string.please_wait, R.string.loading_info);
        loading.show();

        ManagerInterface api = new RetrofitClient().generalRF(Constants.CONTROLLER_MOVIL).create(ManagerInterface.class);

        Call<List<MCartera>> call = api.getCartera("58","Bearer "+ session.getUser().get(7));

        call.enqueue(new Callback<List<MCartera>>() {
            @Override
            public void onResponse(Call<List<MCartera>> call, Response<List<MCartera>> response) {
                Log.e("Code cartera", String.valueOf(response.code()));
                switch (response.code()){
                    case 200:
                        List<MCartera> cartera = response.body();
                        RegistrarCartera rCartera = new RegistrarCartera();
                        try {
                            String res = rCartera.execute(cartera, ctx).get();
                            Log.e("RespuestaCartera", res);
                            if (res.toUpperCase().equals("TERMINA")){
                                GetPrestamos(ctx);
                            }
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        break;
                    case 403:
                        Log.e("Mensaje", response.raw().toString());
                        break;
                }
                loading.dismiss();

            }
            @Override
            public void onFailure(Call<List<MCartera>> call, Throwable t) {
                Log.e("FailCartera", t.getMessage());
                loading.dismiss();
            }
        });

    }

    public void GetPrestamos (Context ctx){
        this.ctx = ctx;

        DBhelper dBhelper = new DBhelper(ctx);
        SQLiteDatabase db = dBhelper.getWritableDatabase();

        Cursor row;
        String query;
        if (ENVIROMENT)
            query = "SELECT * FROM (SELECT id_cartera, '1' AS tipo FROM " + TBL_CARTERA_IND + " UNION SELECT id_cartera,'2' AS tipo FROM "+TBL_CARTERA_GPO +") AS cartera ";
        else
            query = "SELECT * FROM (SELECT id_cartera,'1' AS tipo FROM " + TBL_CARTERA_IND_T + " UNION SELECT id_cartera,'2' AS tipo FROM "+TBL_CARTERA_GPO_T +") AS cartera ";

        row = db.rawQuery(query, null);

        if (row.getCount() > 0){
            row.moveToFirst();

            for (int i = 0; i < row.getCount(); i++){
                ObtenerPrestamos prestamos = new ObtenerPrestamos();
                try {
                    String res = prestamos.execute(row.getInt(0), ctx, row.getInt(1)).get();
                    Log.e("ResPrestamos", res);
                    row.moveToNext();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }
        row.close();
    }

    public class RegistrarCartera extends AsyncTask<Object, Void, String> {
        AlertDialog pDialog;
        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            pDialog = Popups.showLoadingDialog(ctx, R.string.please_wait, R.string.loading_info);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(Object... params) {
            List<MCartera> cartera = (List<MCartera>) params[0];
            if (cartera.size() > 0){
                DBhelper dBhelper = new DBhelper(ctx);
                SQLiteDatabase db = dBhelper.getWritableDatabase();
                Cursor row;

                for (int i = 0; i < cartera.size(); i++){
                    String where = " WHERE id_cartera = ?";
                    String order = "";
                    String[] args =  new String[] {String.valueOf(cartera.get(i).getId())};
                    switch (Miscellaneous.GetTipoCartera(cartera.get(i).getCarteraTipo())){
                        case 1:
                            if (ENVIROMENT)
                                row = dBhelper.getRecords(TBL_CARTERA_IND, where, order, args);
                            else
                                row = dBhelper.getRecords(TBL_CARTERA_IND_T, where, order, args);

                            if (row.getCount() == 0){ //Registra la cartera de ind
                                HashMap<Integer, String> values = new HashMap<>();
                                values.put(0, String.valueOf(cartera.get(i).getId()));              //ID
                                values.put(1, cartera.get(i).getClave());                           //CLAVE
                                values.put(2, cartera.get(i).getNombre());                          //NOMBRE
                                values.put(3, cartera.get(i).getDireccion());                       //DIRECCION
                                values.put(4, cartera.get(i).getAsesorNombre());                    //ASESOR NOMBRE
                                values.put(5, cartera.get(i).getSerieId());                         //SERIE ID
                                values.put(6, (cartera.get(i).getRuta())?"1":"0");                  //IS RUTA
                                values.put(7, (cartera.get(i).getRuta())?"1":"0");                  //RUTA OBLIGADO
                                values.put(8, String.valueOf(cartera.get(i).getUsuarioId()));       //USUARIO ID
                                values.put(9, cartera.get(i).getDia());                             //DIA
                                values.put(10, cartera.get(i).getNumSolicitud());                   //NUM SOLICITUD
                                values.put(11, Miscellaneous.ObtenerFecha(TIMESTAMP));              //FECHA CREACION
                                values.put(12, Miscellaneous.ObtenerFecha(TIMESTAMP));              //FECHA ACTUALIZACION
                                values.put(13, cartera.get(i).getColonia());                        //COLONIA

                                if (ENVIROMENT)
                                    dBhelper.saveCarteraInd(db, TBL_CARTERA_IND, values);
                                else
                                    dBhelper.saveCarteraInd(db, TBL_CARTERA_IND_T, values);
                            }
                            else{ //Actualiza la cartera de ind
                                ContentValues cv = new ContentValues();
                                cv.put("nombre", cartera.get(i).getNombre());
                                cv.put("direccion", cartera.get(i).getDireccion());
                                cv.put("asesor_nombre", cartera.get(i).getAsesorNombre());
                                cv.put("serie_id", cartera.get(i).getSerieId());
                                cv.put("is_ruta", (cartera.get(i).getRuta())?1:0);
                                cv.put("ruta_obligado", (cartera.get(i).getRuta())?1:0);
                                cv.put("usuario_id", String.valueOf(cartera.get(i).getUsuarioId()));
                                cv.put("dia", cartera.get(i).getDia());
                                cv.put("num_solicitud", cartera.get(i).getNumSolicitud());
                                cv.put("fecha_actualizado", Miscellaneous.ObtenerFecha(TIMESTAMP));
                                cv.put("colonia", cartera.get(i).getColonia());

                                if (ENVIROMENT)
                                    db.update(TBL_CARTERA_IND, cv, "id_cartera = ?", new String[]{String.valueOf(cartera.get(i).getId())});
                                else
                                    db.update(TBL_CARTERA_IND_T, cv, "id_cartera = ?", new String[]{String.valueOf(cartera.get(i).getId())});
                            }
                            break;
                        case 2:
                            if (ENVIROMENT)
                                row = dBhelper.getRecords(TBL_CARTERA_GPO, where, order, args);
                            else
                                row = dBhelper.getRecords(TBL_CARTERA_GPO_T, where, order, args);

                            if (row.getCount() == 0){ //Registra la cartera de gpo
                                HashMap<Integer, String> values = new HashMap<>();
                                values.put(0, String.valueOf(cartera.get(i).getId()));              //ID
                                values.put(1, cartera.get(i).getClave());                           //CLAVE
                                values.put(2, cartera.get(i).getNombre());                          //NOMBRE
                                values.put(3, cartera.get(i).getTesorero());                        //TESORERA
                                values.put(4, cartera.get(i).getDireccion());                       //DIRECCION
                                values.put(5, cartera.get(i).getAsesorNombre());                    //ASESOR NOMBRE
                                values.put(6, cartera.get(i).getSerieId());                         //SERIE ID
                                values.put(7, (cartera.get(i).getRuta())?"1":"0");                  //IS RUTA
                                values.put(8, (cartera.get(i).getRuta())?"1":"0");                  //RUTA OBLIGADO
                                values.put(9, String.valueOf(cartera.get(i).getUsuarioId()));       //USUARIO ID
                                values.put(10, cartera.get(i).getDia());                            //DIA
                                values.put(11, cartera.get(i).getNumSolicitud());                   //NUM SOLICITUD
                                values.put(12, Miscellaneous.ObtenerFecha(TIMESTAMP));              //FECHA CREACION
                                values.put(13, Miscellaneous.ObtenerFecha(TIMESTAMP));              //FECHA ACTUALIZACION
                                values.put(14, cartera.get(i).getColonia());                        //COLONIA

                                if (ENVIROMENT)
                                    dBhelper.saveCarteraGpo(db, TBL_CARTERA_GPO, values);
                                else
                                    dBhelper.saveCarteraGpo(db, TBL_CARTERA_GPO_T, values);
                            }
                            else{ //Actualiza la cartera de gpo
                                ContentValues cv = new ContentValues();
                                cv.put("nombre", cartera.get(i).getNombre());
                                cv.put("tesorera", cartera.get(i).getTesorero());
                                cv.put("direccion", cartera.get(i).getDireccion());
                                cv.put("asesor_nombre", cartera.get(i).getAsesorNombre());
                                cv.put("serie_id", cartera.get(i).getSerieId());
                                cv.put("is_ruta", (cartera.get(i).getRuta())?1:0);
                                cv.put("ruta_obligado", (cartera.get(i).getRuta())?1:0);
                                cv.put("usuario_id", String.valueOf(cartera.get(i).getUsuarioId()));
                                cv.put("dia", cartera.get(i).getDia());
                                cv.put("num_solicitud", cartera.get(i).getNumSolicitud());
                                cv.put("fecha_actualizado", Miscellaneous.ObtenerFecha(TIMESTAMP));
                                cv.put("colonia", cartera.get(i).getColonia());

                                if (ENVIROMENT)
                                    db.update(TBL_CARTERA_GPO, cv, "id_cartera = ?", new String[]{String.valueOf(cartera.get(i).getId())});
                                else
                                    db.update(TBL_CARTERA_GPO_T, cv, "id_cartera = ?", new String[]{String.valueOf(cartera.get(i).getId())});
                            }
                            break;
                    }//Fin SWITCH
                } //Fin Ciclo For
            }//Fin IF

            return "TERMINA";
        }

        @Override
        protected void onPostExecute(String result) {
            pDialog.dismiss();
            super.onPostExecute(result);

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
            ManagerInterface api = new RetrofitClient().generalRF(Constants.CONTROLLER_MOVIL).create(ManagerInterface.class);

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
                                Log.e("Prestamo", "Actualiza Prestamo");
                                        /*ContentValues cv = new ContentValues();
                                        cv.put("nombre", cartera.get(i).getNombre());
                                        cv.put("direccion", cartera.get(i).getDireccion());
                                        cv.put("asesor_nombre", cartera.get(i).getAsesorNombre());
                                        cv.put("serie_id", cartera.get(i).getSerieId());
                                        cv.put("is_ruta", 0);
                                        cv.put("ruta_obligado", 0);
                                        cv.put("usuario_id", String.valueOf(cartera.get(i).getUsuarioId()));
                                        cv.put("dia", cartera.get(i).getDia());
                                        cv.put("num_solicitud", cartera.get(i).getNumSolicitud());
                                        cv.put("fecha_actualizado", Miscellaneous.ObtenerFecha("timestamp"));*/

                                //if (ENVIROMENT)
                                //db.update(TBL_CARTERA_IND, cv, "id_cartera = ?", new String[]{String.valueOf(cartera.get(i).getId())});
                                //else
                                //db.update(TBL_CARTERA_IND_T, cv, "id_cartera = ?", new String[]{String.valueOf(cartera.get(i).getId())});
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
                                row = dBhelper.getRecords(TBL_PRESTAMOS_GPO, where, order, args);
                            else
                                row = dBhelper.getRecords(TBL_PRESTAMOS_GPO_T, where, order, args);

                            if (row.getCount() == 0){ //Registra el prestamo de ind
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
                                values.put(12, (prestamos.get(i).getPagada())?"1":"0");                     //PAGADA
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

                                        if (ENVIROMENT)
                                            dBhelper.saveMiembros(db, TBL_MIEMBROS_GPO, values_miembro);
                                        else
                                            dBhelper.saveMiembros(db, TBL_MIEMBROS_GPO_T, values_miembro);
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
                            else{ //Actualiza la cartera de ind
                                Log.e("Prestamo", "Actualiza Prestamo");
                                        /*ContentValues cv = new ContentValues();
                                        cv.put("nombre", cartera.get(i).getNombre());
                                        cv.put("direccion", cartera.get(i).getDireccion());
                                        cv.put("asesor_nombre", cartera.get(i).getAsesorNombre());
                                        cv.put("serie_id", cartera.get(i).getSerieId());
                                        cv.put("is_ruta", 0);
                                        cv.put("ruta_obligado", 0);
                                        cv.put("usuario_id", String.valueOf(cartera.get(i).getUsuarioId()));
                                        cv.put("dia", cartera.get(i).getDia());
                                        cv.put("num_solicitud", cartera.get(i).getNumSolicitud());
                                        cv.put("fecha_actualizado", Miscellaneous.ObtenerFecha("timestamp"));*/

                                //if (ENVIROMENT)
                                //db.update(TBL_CARTERA_IND, cv, "id_cartera = ?", new String[]{String.valueOf(cartera.get(i).getId())});
                                //else
                                //db.update(TBL_CARTERA_IND_T, cv, "id_cartera = ?", new String[]{String.valueOf(cartera.get(i).getId())});
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

}
