package com.sidert.sidertmovil.fragments;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.GsonBuilder;
import com.sidert.sidertmovil.Home;
import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.database.DBhelper;
import com.sidert.sidertmovil.database.SidertTables;
import com.sidert.sidertmovil.models.ModeloResSaveGeo;
import com.sidert.sidertmovil.utils.Constants;
import com.sidert.sidertmovil.utils.ManagerInterface;
import com.sidert.sidertmovil.utils.Miscellaneous;
import com.sidert.sidertmovil.utils.NetworkStatus;
import com.sidert.sidertmovil.utils.Popups;
import com.sidert.sidertmovil.utils.RetrofitClient;
import com.sidert.sidertmovil.utils.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class erase_table_fragment extends Fragment {

    private Context ctx;
    private Button btnImpresiones;
    private Button btnFichas;
    private Button btnImageGeo;
    private Home root;

    private DBhelper dBhelper;
    private SQLiteDatabase db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_erase_table, container, false);
        ctx = getContext();
        root = (Home) getActivity();

        dBhelper = new DBhelper(ctx);
        db = dBhelper.getWritableDatabase();
        btnImageGeo = view.findViewById(R.id.btnImageGeo);
        btnFichas = view.findViewById(R.id.btnFichas);
        btnImpresiones  = view.findViewById(R.id.btnImpresiones);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        btnImageGeo.setOnClickListener(btnImageGeo_OnClick);
        btnImpresiones.setOnClickListener(btnImpresiones_OnClick);
        btnFichas.setOnClickListener(btnFichas_OnClick);
    }

    private View.OnClickListener btnImageGeo_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            SaveGeolocalizacion();
        }
    };

    private View.OnClickListener btnImpresiones_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //db.execSQL("DELETE FROM " + SidertTables.SidertEntry.TABLE_NAME_LOG_ASESSOR);
            //db.execSQL("DELETE FROM " + SidertTables.SidertEntry.TABLE_NAME_LOG_MANAGER);
        }
    };

    private View.OnClickListener btnFichas_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //db.execSQL("DELETE FROM " + SidertTables.SidertEntry.TABLE_FICHAS_T);
        }
    };

    public void SaveGeolocalizacion(){
        SessionManager session = new SessionManager(ctx);
        final DBhelper dBhelper = new DBhelper(ctx);
        final SQLiteDatabase db = dBhelper.getWritableDatabase();

        Cursor row;
        if (Constants.ENVIROMENT)
            row = dBhelper.getRecords(SidertTables.SidertEntry.TABLE_GEOLOCALIZACION, "", "", null);
        else
            row = dBhelper.getRecords(SidertTables.SidertEntry.TABLE_GEOLOCALIZACION_T, "", "", null);

        if (row.getCount() > 0){
            final AlertDialog loading = Popups.showLoadingDialog(ctx,R.string.please_wait, R.string.loading_info);
            loading.show();
            row.moveToFirst();
            for(int i = 0; i < row.getCount(); i++){
                Log.e("fecha_uno",String.valueOf(row.getString(16).isEmpty() && !row.getString(13).isEmpty())+" "+row.getString(16));
                if (!row.getString(16).isEmpty() && !row.getString(13).isEmpty())
                    SendGeolocalizacion(ctx, row.getString(13), row.getString(1), 1);
                if (!row.getString(17).isEmpty() && !row.getString(14).isEmpty())
                    SendGeolocalizacion(ctx, row.getString(14), row.getString(1), 2);
                if (!row.getString(18).isEmpty() && !row.getString(15).isEmpty())
                    SendGeolocalizacion(ctx, row.getString(15), row.getString(1), 3);
                row.moveToNext();
            }
            loading.dismiss();
            Toast.makeText(ctx, "Sincronizado de imagenes realizado con éxito", Toast.LENGTH_SHORT).show();
        }
    }

    private void SendGeolocalizacion(final Context ctx, String respuesta, final String ficha_id, final int modulo){
        SessionManager session = new SessionManager(ctx);
        DBhelper dBhelper = new DBhelper(ctx);
        final SQLiteDatabase db = dBhelper.getWritableDatabase();
        if (NetworkStatus.haveNetworkConnection(ctx)){
            try {

                JSONObject jsonRes = new JSONObject(respuesta);
                Log.e("Ficha Id Env", jsonRes.toString() + "fICHA: "+ficha_id);

                final File image = new File(Constants.ROOT_PATH + "Fachada/"+jsonRes.getString(Constants.FACHADA));

                RequestBody actualizarBody = RequestBody.create(MultipartBody.FORM, "1");
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

                Call<ModeloResSaveGeo> call = api.guardarGeoUpdate("Bearer "+ session.getUser().get(7),
                        actualizarBody,
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
}
