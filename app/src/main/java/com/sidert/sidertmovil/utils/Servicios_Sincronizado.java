package com.sidert.sidertmovil.utils;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.GsonBuilder;
import com.sidert.sidertmovil.MainActivity;
import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.database.DBhelper;
import com.sidert.sidertmovil.database.SidertTables;
import com.sidert.sidertmovil.models.MResSaveOriginacionInd;
import com.sidert.sidertmovil.models.ModeloGeolocalizacion;
import com.sidert.sidertmovil.models.ModeloResSaveGeo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Servicios_Sincronizado {

    public void GetGeolocalizacion(final Context ctx, final boolean showDG, final boolean incluir_gestiones){
        Log.e("GetGeolocalizacion", "Inicia la obtencion de fichas "+incluir_gestiones);
        SessionManager session = new SessionManager(ctx);
        final DBhelper dBhelper = new DBhelper(ctx);
        final SQLiteDatabase db = dBhelper.getWritableDatabase();

        final AlertDialog loading = Popups.showLoadingDialog(ctx, R.string.please_wait, R.string.loading_info);

        if (showDG)
            loading.show();

        ManagerInterface api = new RetrofitClient().generalRF(Constants.CONTROLLER_FICHAS).create(ManagerInterface.class);

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
                                if (Constants.ENVIROMENT)
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
                                    if (Constants.ENVIROMENT)
                                        dBhelper.saveRecordsGeo(db, SidertTables.SidertEntry.TABLE_GEOLOCALIZACION, params);
                                    else
                                        dBhelper.saveRecordsGeo(db, SidertTables.SidertEntry.TABLE_GEOLOCALIZACION_T, params);
                                }//Fin de IF que no existe registro de gestion para ser guardado
                            }//Fin For de Guardado de gestiones grupales
                        }//Fin de Grupales

                        if (modeloGeo.getIndividuales().size() > 0){
                            for (int i = 0; i < modeloGeo.getIndividuales().size(); i++){
                                Cursor rowGeo;
                                if (Constants.ENVIROMENT)
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
                                    if (Constants.ENVIROMENT)
                                        dBhelper.saveRecordsGeo(db, SidertTables.SidertEntry.TABLE_GEOLOCALIZACION, params);
                                    else
                                        dBhelper.saveRecordsGeo(db, SidertTables.SidertEntry.TABLE_GEOLOCALIZACION_T, params);
                                }//Fin de IF que no existe registro de gestion para ser guardado
                            }//Fin de For de Guardado de individuales
                        }//Fin de Individuales

                        if(modeloGeo.getGrupalesGestionadas().size() > 0){
                            for (int h = 0; h < modeloGeo.getGrupalesGestionadas().size(); h++){
                                Cursor rowGeoGG;
                                if (Constants.ENVIROMENT)
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
                                if (Constants.ENVIROMENT)
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
        if (Constants.ENVIROMENT)
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
                Log.e("testRes", jsonRes.toString() + "Ficha: "+ficha_id);

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
                    Log.e("test3","test3");
                    RequestBody imageBody =
                            RequestBody.create(
                                    MediaType.parse("image/*"), image);

                    body = MultipartBody.Part.createFormData("foto_fachada", image.getName(), imageBody);
                }


                ManagerInterface api = new RetrofitClient().generalRF(Constants.CONTROLLER_FICHAS).create(ManagerInterface.class);

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
                                if (Constants.ENVIROMENT)
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

                if (Constants.ENVIROMENT)
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
        if (Constants.ENVIROMENT)
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
                        MultipartBody.Part foto_ine_reverso = null;
                        MultipartBody.Part foto_curp = null;
                        MultipartBody.Part foto_comprobante = null;
                        MultipartBody.Part firma_asesor = null;
                        for (int j = 0; j < row_soli.getCount(); j++){

                            switch (row_soli.getInt(159)){
                                case 1:
                                    File image_ine_frontal = new File(Constants.ROOT_PATH + "Documentos/"+row_soli.getString(158));
                                    if (image_ine_frontal != null) {
                                        RequestBody imageBody =
                                                RequestBody.create(
                                                        MediaType.parse("image/*"), image_ine_frontal);

                                        foto_ine_frontal = MultipartBody.Part.createFormData("identificacion_frontal", image_ine_frontal.getName(), imageBody);
                                    }
                                    break;
                                case 2:
                                    File image_ine_reverso = new File(Constants.ROOT_PATH + "Documentos/"+row_soli.getString(158));
                                    if (image_ine_reverso != null) {
                                        RequestBody imageBody =
                                                RequestBody.create(
                                                        MediaType.parse("image/*"), image_ine_reverso);

                                        foto_ine_reverso = MultipartBody.Part.createFormData("identificacion_reverso", image_ine_reverso.getName(), imageBody);
                                    }
                                    break;
                                case 3:
                                    File image_curp = new File(Constants.ROOT_PATH + "Documentos/"+row_soli.getString(158));
                                    if (image_curp != null) {
                                        RequestBody imageBody =
                                                RequestBody.create(
                                                        MediaType.parse("image/*"), image_curp);

                                        foto_curp = MultipartBody.Part.createFormData("curp", image_curp.getName(), imageBody);
                                    }
                                    break;
                                case 4:
                                    File image_comprobante = new File(Constants.ROOT_PATH + "Documentos/"+row_soli.getString(158));
                                    if (image_comprobante != null) {
                                        RequestBody imageBody =
                                                RequestBody.create(
                                                        MediaType.parse("image/*"), image_comprobante);

                                        foto_comprobante = MultipartBody.Part.createFormData("comprobante_domicilio", image_comprobante.getName(), imageBody);
                                    }
                                    break;
                                case 6:
                                    File image_firma_asesor = new File(Constants.ROOT_PATH + "Firma/"+row_soli.getString(158));
                                    if (image_firma_asesor != null) {
                                        RequestBody imageBody =
                                                RequestBody.create(
                                                        MediaType.parse("image/*"), image_firma_asesor);

                                        firma_asesor = MultipartBody.Part.createFormData("firma_asesor", image_firma_asesor.getName(), imageBody);
                                    }
                                    break;
                            }
                            row_soli.moveToNext();
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
}
