package com.sidert.sidertmovil.utils;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.database.DBhelper;
import com.sidert.sidertmovil.database.SidertTables;
import com.sidert.sidertmovil.models.MPlazos;
import com.sidert.sidertmovil.models.MTickets;
import com.sidert.sidertmovil.models.ModeloColonia;
import com.sidert.sidertmovil.models.ModeloEstados;
import com.sidert.sidertmovil.models.ModeloIdentificacion;
import com.sidert.sidertmovil.models.ModeloMunicipio;
import com.sidert.sidertmovil.models.ModeloOcupaciones;
import com.sidert.sidertmovil.models.ModeloSectores;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.sidert.sidertmovil.utils.Constants.TBL_PLAZOS_PRESTAMOS;
import static com.sidert.sidertmovil.utils.Constants.TICKETS;

public class Sincronizar_Catalogos {

    private Context ctx;

    public void GetEstados(final Context ctx){

        final AlertDialog pDialog = Popups.showLoadingDialog(ctx, R.string.please_wait, R.string.loading_info);
        pDialog.setCancelable(false);
        pDialog.show();
        SessionManager session = new SessionManager(ctx);

        ManagerInterface api = new RetrofitClient().generalRF(Constants.CONTROLLER_CATALOGOS, ctx).create(ManagerInterface.class);

        Call<List<ModeloEstados>> call = api.getEstados("Bearer "+ session.getUser().get(7));

        call.enqueue(new Callback<List<ModeloEstados>>() {
            @Override
            public void onResponse(Call<List<ModeloEstados>> call, Response<List<ModeloEstados>> response) {
                switch (response.code()){
                    case 200:
                        List<ModeloEstados> estados = response.body();
                        new RegistrarEstados()
                                .execute(estados, ctx);
                        break;
                }
                pDialog.dismiss();


            }
            @Override
            public void onFailure(Call<List<ModeloEstados>> call, Throwable t) {
                Log.e("FailEstados", t.getMessage());
                pDialog.dismiss();
            }
        });

    }

    public void GetMunicipios(final Context ctx){
        final AlertDialog pDialog = Popups.showLoadingDialog(ctx, R.string.please_wait, R.string.loading_info);
        pDialog.setCancelable(false);
        pDialog.show();
        final SessionManager session = new SessionManager(ctx);

        ManagerInterface api = new RetrofitClient().generalRF(Constants.CONTROLLER_CATALOGOS, ctx).create(ManagerInterface.class);

        Call<List<ModeloMunicipio>> call = api.getMunicipios("Bearer "+ session.getUser().get(7));

        call.enqueue(new Callback<List<ModeloMunicipio>>() {
            @Override
            public void onResponse(Call<List<ModeloMunicipio>> call, Response<List<ModeloMunicipio>> response) {
                switch (response.code()){
                    case 200:
                        List<ModeloMunicipio> municipios = response.body();
                        new RegistrarMunicipios()
                                .execute(municipios, ctx);
                        break;
                }
                pDialog.dismiss();

            }
            @Override
            public void onFailure(Call<List<ModeloMunicipio>> call, Throwable t) {
                Log.e("FailMunicipios", t.getMessage());
                pDialog.dismiss();
            }
        });

    }

    public void GetColonias(final Context ctx){
        this.ctx = ctx;
        SessionManager session = new SessionManager(ctx);


        ManagerInterface api = new RetrofitClient().generalRF(Constants.CONTROLLER_CATALOGOS, ctx).create(ManagerInterface.class);

        Call<List<ModeloColonia>> call = api.getColonias("Bearer "+ session.getUser().get(7));

        call.enqueue(new Callback<List<ModeloColonia>>() {
            @Override
            public void onResponse(Call<List<ModeloColonia>> call, Response<List<ModeloColonia>> response) {
                switch (response.code()){
                    case 200:
                        List<ModeloColonia> colonias = response.body();
                        new RegistrarColonias()
                                .execute(colonias, ctx);
                        break;
                }
                //loading.dismiss();

            }
            @Override
            public void onFailure(Call<List<ModeloColonia>> call, Throwable t) {
                Log.e("FailColonia", t.getMessage());
                //loading.dismiss();
            }
        });

    }

    public void GetSectores(final Context ctx){
        final AlertDialog pDialog = Popups.showLoadingDialog(ctx, R.string.please_wait, R.string.loading_info);
        pDialog.setCancelable(false);
        pDialog.show();
        SessionManager session = new SessionManager(ctx);

        ManagerInterface api = new RetrofitClient().generalRF(Constants.CONTROLLER_CATALOGOS, ctx).create(ManagerInterface.class);

        Call<List<ModeloSectores>> call = api.getSectores("Bearer "+ session.getUser().get(7));

        call.enqueue(new Callback<List<ModeloSectores>>() {
            @Override
            public void onResponse(Call<List<ModeloSectores>> call, Response<List<ModeloSectores>> response) {
                switch (response.code()){
                    case 200:
                        List<ModeloSectores> sectores = response.body();
                        new RegistrarSectores()
                            .execute(sectores, ctx);
                        break;
                }
                pDialog.dismiss();


            }
            @Override
            public void onFailure(Call<List<ModeloSectores>> call, Throwable t) {
                Log.e("FailColonia", t.getMessage());
                pDialog.dismiss();
            }
        });

    }

    public void GetOcupaciones(final Context ctx){
        final AlertDialog pDialog = Popups.showLoadingDialog(ctx, R.string.please_wait, R.string.loading_info);
        pDialog.setCancelable(false);
        pDialog.show();
        SessionManager session = new SessionManager(ctx);

        ManagerInterface api = new RetrofitClient().generalRF(Constants.CONTROLLER_CATALOGOS, ctx).create(ManagerInterface.class);

        Call<List<ModeloOcupaciones>> call = api.getOcupaciones("Bearer "+ session.getUser().get(7));

        call.enqueue(new Callback<List<ModeloOcupaciones>>() {
            @Override
            public void onResponse(Call<List<ModeloOcupaciones>> call, Response<List<ModeloOcupaciones>> response) {
                switch (response.code()){
                    case 200:
                        List<ModeloOcupaciones> ocupaciones = response.body();
                        new RegistrarOcupaciones()
                                .execute(ocupaciones, ctx);
                        break;
                }
                pDialog.dismiss();
            }
            @Override
            public void onFailure(Call<List<ModeloOcupaciones>> call, Throwable t) {
                Log.e("FailColonia", t.getMessage());
                pDialog.dismiss();
            }
        });

    }

    public void GetTipoIdentificacion(final Context ctx){
        final AlertDialog pDialog = Popups.showLoadingDialog(ctx, R.string.please_wait, R.string.loading_info);
        pDialog.setCancelable(false);
        pDialog.show();
        SessionManager session = new SessionManager(ctx);

        ManagerInterface api = new RetrofitClient().generalRF(Constants.CONTROLLER_CATALOGOS, ctx).create(ManagerInterface.class);

        Call<List<ModeloIdentificacion>> call = api.getIdentificaciones("Bearer "+ session.getUser().get(7));

        call.enqueue(new Callback<List<ModeloIdentificacion>>() {
            @Override
            public void onResponse(Call<List<ModeloIdentificacion>> call, Response<List<ModeloIdentificacion>> response) {
                switch (response.code()){
                    case 200:
                        List<ModeloIdentificacion> identificaciones = response.body();
                        new RegistrarIdentificaciones()
                                .execute(identificaciones, ctx);
                        break;
                }
                pDialog.dismiss();
            }
            @Override
            public void onFailure(Call<List<ModeloIdentificacion>> call, Throwable t) {
                Log.e("FailIdentificacion", t.getMessage());
                pDialog.dismiss();
            }
        });

    }

    public void GetCategoriasTickets(final Context ctx){
        this.ctx = ctx;
        SessionManager session = new SessionManager(ctx);


        ManagerInterface api = new RetrofitClient().generalRF(Constants.CONTROLLER_CATALOGOS, ctx).create(ManagerInterface.class);

        Call<List<MTickets>> call = api.getCategoriasTickets("Bearer "+ session.getUser().get(7),
                                                                "MOVIL");

        call.enqueue(new Callback<List<MTickets>>() {
            @Override
            public void onResponse(Call<List<MTickets>> call, Response<List<MTickets>> response) {
                Log.e("CodeTickets", "Code: "+response.code());
                switch (response.code()){
                    case 200:
                        List<MTickets> categorias = response.body();
                        new RegistrarCategorias()
                                .execute(categorias, ctx);
                        break;
                }
                //loading.dismiss();

            }
            @Override
            public void onFailure(Call<List<MTickets>> call, Throwable t) {
                Log.e("FailColonia", t.getMessage());
                //loading.dismiss();
            }
        });
    }

    public void GetPlazosPrestamo(final Context ctx){
        this.ctx = ctx;
        SessionManager session = new SessionManager(ctx);


        ManagerInterface api = new RetrofitClient().generalRF(Constants.CONTROLLER_CATALOGOS, ctx).create(ManagerInterface.class);

        Call<List<MPlazos>> call = api.getPlazosPrestamo("Bearer "+ session.getUser().get(7));

        call.enqueue(new Callback<List<MPlazos>>() {
            @Override
            public void onResponse(Call<List<MPlazos>> call, Response<List<MPlazos>> response) {
                Log.e("CodePlazos", "Code: "+response.code());
                switch (response.code()){
                    case 200:
                        List<MPlazos> plazos = response.body();
                        new RegistrarPlazos()
                                .execute(plazos, ctx);
                        break;
                }
                //loading.dismiss();

            }
            @Override
            public void onFailure(Call<List<MPlazos>> call, Throwable t) {
                Log.e("FailColonia", t.getMessage());
                //loading.dismiss();
            }
        });
    }

    public class RegistrarColonias extends AsyncTask<Object, Void, String> {
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
            // TODO Auto-generated method stub
            Log.i("doInBackground" , "Entra en doInBackground");
            List<ModeloColonia> colonias = (List<ModeloColonia>) params[0];
            Context ctx = (Context) params[1];
            if (colonias.size() > 0){
                DBhelper dBhelper = new DBhelper(ctx);
                SQLiteDatabase db = dBhelper.getWritableDatabase();
                Cursor row;
                for (int i = 0; i < colonias.size(); i++){
                    Log.e("Colonia", colonias.get(i).getNombre() +" id: " + colonias.get(i).getMunicipioId());
                    int id = colonias.get(i).getId();
                    String where = " WHERE colonia_id = ?";
                    String order = "";
                    String[] args =  new String[] {String.valueOf(colonias.get(i).getId())};
                    row = dBhelper.getRecords(SidertTables.SidertEntry.TABLE_COLONIAS, where, order, args);
                    //Log.v("CountSelect",row.getCount()+"");
                    if (row.getCount() == 0){
                        HashMap<Integer, String> values = new HashMap<>();
                        values.put(0, String.valueOf(colonias.get(i).getId()));
                        values.put(1, colonias.get(i).getNombre());
                        values.put(2, String.valueOf(colonias.get(i).getCp()));
                        values.put(3, String.valueOf(colonias.get(i).getMunicipioId()));
                        dBhelper.saveColonias(db, values);
                    }
                    row.close();
                }
            }

            return "termina";
        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            pDialog.dismiss();
            Toast.makeText(ctx, "Catálogos actualizados", Toast.LENGTH_SHORT).show();
            super.onPostExecute(result);

        }

    }

    public class RegistrarEstados extends AsyncTask<Object, Void, String> {
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
            // TODO Auto-generated method stub
            Log.i("doInBackground" , "Entra en doInBackground");
            List<ModeloEstados> estados = (List<ModeloEstados>) params[0];
            if (estados.size() > 0){
                DBhelper dBhelper = new DBhelper(ctx);
                SQLiteDatabase db = dBhelper.getWritableDatabase();
                Cursor row;
                for (int i = 0; i < estados.size(); i++){
                    //Log.e("Estado", estados.get(i).getNombre());
                    int id = estados.get(i).getId();
                    String where = " WHERE estado_id = ?";
                    String order = "";
                    String[] args =  new String[] {String.valueOf(estados.get(i).getId())};
                    row = dBhelper.getRecords(SidertTables.SidertEntry.TABLE_ESTADOS, where, order, args);
                    //Log.v("CountSelect",row.getCount()+"");
                    if (row.getCount() == 0){
                        HashMap<Integer, String> values = new HashMap<>();
                        values.put(0, String.valueOf(estados.get(i).getId()));
                        values.put(1, estados.get(i).getNombre());
                        values.put(2, String.valueOf(estados.get(i).getPaisId()));
                        dBhelper.saveEstados(db, values);
                    }
                    row.close();
                }
            }

            return "termina";
        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            pDialog.dismiss();
            super.onPostExecute(result);

        }

    }

    public class RegistrarMunicipios extends AsyncTask<Object, Void, String> {
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
            // TODO Auto-generated method stub
            Log.i("doInBackground" , "Entra en doInBackground");
            List<ModeloMunicipio> municipios = (List<ModeloMunicipio>) params[0];
            if (municipios.size() > 0){
                DBhelper dBhelper = new DBhelper(ctx);
                SQLiteDatabase db = dBhelper.getWritableDatabase();
                Cursor row;
                //Log.e("size muni", municipios.size()+"");
                for (int i = 0; i < municipios.size(); i++){
                    //Log.e("Municipio", municipios.get(i).getNombre());
                    int id = municipios.get(i).getId();
                    String where = " WHERE municipio_id = ?";
                    String order = "";
                    String[] args =  new String[] {String.valueOf(municipios.get(i).getId())};
                    row = dBhelper.getRecords(SidertTables.SidertEntry.TABLE_MUNICIPIOS, where, order, args);
                    //Log.v("CountSelect",row.getCount()+"");
                    if (row.getCount() == 0){
                        HashMap<Integer, String> values = new HashMap<>();
                        values.put(0, String.valueOf(municipios.get(i).getId()));
                        values.put(1, municipios.get(i).getNombre());
                        values.put(2, String.valueOf(municipios.get(i).getEstadoId()));
                        dBhelper.saveMunicipios(db, values);
                    }

                    row.close();
                }
            }
            return "termina";
        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            pDialog.dismiss();
            super.onPostExecute(result);

        }

    }

    public class RegistrarSectores extends AsyncTask<Object, Void, String> {
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
            // TODO Auto-generated method stub
            Log.i("doInBackground" , "Entra en doInBackground");
            List<ModeloSectores> sectores = (List<ModeloSectores>) params[0];
            if (sectores.size() > 0){
                DBhelper dBhelper = new DBhelper(ctx);
                SQLiteDatabase db = dBhelper.getWritableDatabase();
                Cursor row;
                for (int i = 0; i < sectores.size(); i++){
                    //Log.e("Colonia", sectores.get(i).getNombre());
                    int id = sectores.get(i).getId();
                    String where = " WHERE sector_id = ?";
                    String order = "";
                    String[] args =  new String[] {String.valueOf(sectores.get(i).getId())};
                    row = dBhelper.getRecords(SidertTables.SidertEntry.TABLE_SECTORES, where, order, args);
                    //Log.v("CountSelect",row.getCount()+"");
                    if (row.getCount() == 0){
                        HashMap<Integer, String> values = new HashMap<>();
                        values.put(0, String.valueOf(sectores.get(i).getId()));
                        values.put(1, sectores.get(i).getNombre());
                        values.put(2, String.valueOf(sectores.get(i).getNivelRiesgo()));
                        dBhelper.saveSectores(db, values);
                    }
                    row.close();
                }
            }
            return "termina";
        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            pDialog.dismiss();
            super.onPostExecute(result);

        }

    }

    public class RegistrarOcupaciones extends AsyncTask<Object, Void, String> {
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
            // TODO Auto-generated method stub
            Log.i("doInBackground" , "Entra en doInBackground");
            List<ModeloOcupaciones> ocupaciones = (List<ModeloOcupaciones>) params[0];
            if (ocupaciones.size() > 0){
                DBhelper dBhelper = new DBhelper(ctx);
                SQLiteDatabase db = dBhelper.getWritableDatabase();
                Cursor row;
                for (int i = 0; i < ocupaciones.size(); i++){
                    //Log.e("Colonia", ocupaciones.get(i).getNombre());
                    int id = ocupaciones.get(i).getId();
                    String where = " WHERE ocupacion_id = ?";
                    String order = "";
                    String[] args =  new String[] {String.valueOf(ocupaciones.get(i).getId())};
                    row = dBhelper.getRecords(SidertTables.SidertEntry.TABLE_OCUPACIONES, where, order, args);
                    //Log.v("CountSelect",row.getCount()+"");
                    if (row.getCount() == 0){
                        HashMap<Integer, String> values = new HashMap<>();
                        values.put(0, String.valueOf(ocupaciones.get(i).getId()));
                        values.put(1, ocupaciones.get(i).getNombre());
                        values.put(2, ocupaciones.get(i).getClave());
                        values.put(3, String.valueOf(ocupaciones.get(i).getNivelRiesgo()));
                        values.put(4, String.valueOf(ocupaciones.get(i).getSectorId()));
                        dBhelper.saveOcupaciones(db, values);
                    }
                    row.close();
                }
            }
            return "termina";
        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            pDialog.dismiss();
            super.onPostExecute(result);

        }

    }

    public class RegistrarIdentificaciones extends AsyncTask<Object, Void, String> {
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
            // TODO Auto-generated method stub
            Log.i("doInBackground" , "Entra en doInBackground");
            List<ModeloIdentificacion> identificaciones = (List<ModeloIdentificacion>) params[0];
            /*if (identificaciones.size() > 0){
                DBhelper dBhelper = new DBhelper(ctx);
                SQLiteDatabase db = dBhelper.getWritableDatabase();
                Cursor row;
                for (int i = 0; i < identificaciones.size(); i++){
                    //Log.e("Colonia", ocupaciones.get(i).getNombre());
                    int id = identificaciones.get(i).getId();
                    String where = " WHERE identificacion_id = ?";
                    String order = "";
                    String[] args =  new String[] {String.valueOf(identificaciones.get(i).getId())};
                    row = dBhelper.getRecords(SidertTables.SidertEntry.TABLE_IDENTIFICACIONES, where, order, args);
                    //Log.v("CountSelect",row.getCount()+"");
                    if (row.getCount() == 0){
                        HashMap<Integer, String> values = new HashMap<>();
                        values.put(0, String.valueOf(identificaciones.get(i).getId()));
                        values.put(1, identificaciones.get(i).getNombre());
                        dBhelper.saveIdentificaciones(db, values);
                    }
                    row.close();
                }
            }*/
            return "termina";
        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            pDialog.dismiss();
            super.onPostExecute(result);

        }

    }

    public class RegistrarCategorias extends AsyncTask<Object, Void, String> {
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
            // TODO Auto-generated method stub
            Log.i("doInBackground" , "Entra en doInBackground");
            List<MTickets> categorias = (List<MTickets>) params[0];
            if (categorias.size() > 0){
                DBhelper dBhelper = new DBhelper(ctx);
                SQLiteDatabase db = dBhelper.getWritableDatabase();
                Cursor row;
                for (MTickets item : categorias){
                    String where = " WHERE ticket_id = ?";
                    String order = "";
                    String[] args =  new String[] {String.valueOf(item.getId())};
                    row = dBhelper.getRecords(TICKETS, where, order, args);

                    Log.e("Existe", "asd"+row.getCount());
                    if (row.getCount() == 0){
                        HashMap<Integer, String> values = new HashMap<>();
                        values.put(0, String.valueOf(item.getId()));
                        values.put(1, item.getNombre());
                        values.put(2, String.valueOf(item.getPrioridad()));
                        values.put(3, String.valueOf(item.getEstatus()));
                        dBhelper.saveCategoriaTickets(db, values);
                    }
                    else{
                        row.moveToFirst();
                        ContentValues cv = new ContentValues();
                        cv.put("estatus", item.getEstatus());

                        db.update(TICKETS, cv, "ticket_id = ?", new String[]{String.valueOf(item.getId())});
                    }
                    row.close();
                }
            }
            return "termina";
        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            pDialog.dismiss();
            super.onPostExecute(result);

        }

    }

    public class RegistrarPlazos extends AsyncTask<Object, Void, String> {
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
            // TODO Auto-generated method stub
            Log.i("doInBackground" , "Entra en doInBackground");
            List<MPlazos> plazos = (List<MPlazos>) params[0];
            if (plazos.size() > 0){
                DBhelper dBhelper = new DBhelper(ctx);
                SQLiteDatabase db = dBhelper.getWritableDatabase();
                Cursor row;
                for (MPlazos item : plazos){
                    String where = " WHERE id_plazo_prestamo = ?";
                    String order = "";
                    String[] args =  new String[] {String.valueOf(item.getId())};
                    row = dBhelper.getRecords(TBL_PLAZOS_PRESTAMOS, where, order, args);

                    Log.e("Existe", "asd"+row.getCount());
                    if (row.getCount() == 0){
                        HashMap<Integer, String> values = new HashMap<>();
                        values.put(0, String.valueOf(item.getId()));
                        values.put(1, item.getNombre());
                        values.put(2, String.valueOf(item.getNumeroMeses()));
                        values.put(3, (item.getEstatus())?"1":"0");
                        dBhelper.savePlazosPrestamo(db, values);
                    }
                    else{
                        row.moveToFirst();
                        ContentValues cv = new ContentValues();
                        cv.put("estatus", (item.getEstatus())?"1":"0");

                        db.update(TBL_PLAZOS_PRESTAMOS, cv, "id_plazo_prestamo = ?", new String[]{String.valueOf(item.getId())});
                    }
                    row.close();
                }
            }
            return "termina";
        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            pDialog.dismiss();
            super.onPostExecute(result);

        }

    }
}

