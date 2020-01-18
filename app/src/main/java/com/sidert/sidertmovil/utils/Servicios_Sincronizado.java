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
        Log.e("GetGeolocalizacion", "Inicia la obtencion de fichas");
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
                                            if (rowGeoGG.getString(13).trim().isEmpty() && rowGeoGG.getString(16).trim().isEmpty()){
                                                new DescargarFotoFachada()
                                                        .execute(new GsonBuilder().create().toJson(modeloGeo.getGrupalesGestionadas().get(h)), rowGeoGG.getString(21), ctx);
                                            }
                                            break;
                                        case Constants.TIPO_TESORERO:
                                            if (rowGeoGG.getString(14).trim().isEmpty() && rowGeoGG.getString(17).trim().isEmpty()){
                                                new DescargarFotoFachada()
                                                        .execute(new GsonBuilder().create().toJson(modeloGeo.getGrupalesGestionadas().get(h)), rowGeoGG.getString(21), ctx);
                                            }
                                            break;
                                        case Constants.TIPO_SECRETARIO:
                                            if (rowGeoGG.getString(15).trim().isEmpty() && rowGeoGG.getString(18).trim().isEmpty()){
                                                new DescargarFotoFachada()
                                                        .execute(new GsonBuilder().create().toJson(modeloGeo.getGrupalesGestionadas().get(h)), rowGeoGG.getString(21), ctx);
                                            }
                                            break;
                                    }//Fin switch para ser actilizado el registro
                                }//Fin de IF que existe registro de gestion
                            }//Fin de For para guardado de Grupales Gestionadas
                        }//Fin de Grupales Gestionadas

                        if(modeloGeo.getIndividualesGestionadas().size() > 0){
                            for (int h = 0; h < modeloGeo.getIndividualesGestionadas().size(); h++){
                                Cursor rowGeoGG;
                                if (Constants.ENVIROMENT)
                                    rowGeoGG = dBhelper.getRecords(SidertTables.SidertEntry.TABLE_GEOLOCALIZACION, " WHERE ficha_id = '"+modeloGeo.getIndividualesGestionadas().get(h).getFichaId()+"'", "", null);
                                else
                                    rowGeoGG = dBhelper.getRecords(SidertTables.SidertEntry.TABLE_GEOLOCALIZACION_T, " WHERE ficha_id = '"+modeloGeo.getIndividualesGestionadas().get(h).getFichaId()+"'", "", null);

                                if (rowGeoGG.getCount() > 0){
                                    rowGeoGG.moveToFirst();

                                    switch(modeloGeo.getIndividualesGestionadas().get(h).getTipo()){
                                        case Constants.TIPO_CLIENTE:
                                            if (rowGeoGG.getString(13).trim().isEmpty() && rowGeoGG.getString(16).trim().isEmpty()){
                                                new DescargarFotoFachada()
                                                        .execute(new GsonBuilder().create().toJson(modeloGeo.getIndividualesGestionadas().get(h)), rowGeoGG.getString(21), ctx);
                                            }
                                            break;
                                        case Constants.TIPO_NEGOCIO:
                                            if (rowGeoGG.getString(14).trim().isEmpty() && rowGeoGG.getString(17).trim().isEmpty()){
                                                new DescargarFotoFachada()
                                                        .execute(new GsonBuilder().create().toJson(modeloGeo.getIndividualesGestionadas().get(h)), rowGeoGG.getString(21), ctx);
                                            }
                                            break;

                                        case Constants.TIPO_AVAL:
                                            if (rowGeoGG.getString(15).trim().isEmpty() && rowGeoGG.getString(18).trim().isEmpty()){
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
                if (row.getString(16).isEmpty() && !row.getString(13).isEmpty()){
                    Log.e("row", row.getString(13));
                }
                //Log.e("fecha_uno",String.valueOf(row.getString(16).isEmpty() && !row.getString(13).isEmpty())+" "+row.getString(16));
                if (row.getString(16).isEmpty() && !row.getString(13).isEmpty())
                    SendGeolocalizacion(ctx, row.getString(13), row.getString(1), 1);
                if (row.getString(17).isEmpty() && !row.getString(14).isEmpty())
                    SendGeolocalizacion(ctx, row.getString(14), row.getString(1), 2);
                if (row.getString(18).isEmpty() && !row.getString(15).isEmpty())
                    SendGeolocalizacion(ctx, row.getString(15), row.getString(1), 3);
                row.moveToNext();
            }
        }

        if (flag)
            loading.dismiss();
    }

    private void SendGeolocalizacion(final Context ctx, String respuesta, final String ficha_id, final int modulo){
        SessionManager session = new SessionManager(ctx);
        DBhelper dBhelper = new DBhelper(ctx);
        final SQLiteDatabase db = dBhelper.getWritableDatabase();
        if (NetworkStatus.haveNetworkConnection(ctx)){
            try {

                JSONObject jsonRes = new JSONObject(respuesta);
                Log.e("testRes", jsonRes.toString() + "fICHA: "+ficha_id);

                final File image = new File(Constants.ROOT_PATH + "Fachada/"+jsonRes.getString(Constants.FACHADA));

                RequestBody ficha_idBody = RequestBody.create(MultipartBody.FORM, ficha_id);
                RequestBody latBody = RequestBody.create(MultipartBody.FORM, jsonRes.getString(Constants.LATITUD));
                RequestBody lngBody = RequestBody.create(MultipartBody.FORM, jsonRes.getString(Constants.LONGITUD));
                RequestBody direccionBody = RequestBody.create(MultipartBody.FORM, jsonRes.getString(Constants.DIRECCION));
                RequestBody barcodeBody = RequestBody.create(MultipartBody.FORM, jsonRes.getString(Constants.CODEBARS));
                RequestBody fechaBody = RequestBody.create(MultipartBody.FORM, jsonRes.getString(Constants.FECHA));
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
                                                                fechaBody,
                                                                latBody,
                                                                lngBody,
                                                                direccionBody,
                                                                barcodeBody,
                                                                comentarioBody,
                                                                tipoBody,
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
}
