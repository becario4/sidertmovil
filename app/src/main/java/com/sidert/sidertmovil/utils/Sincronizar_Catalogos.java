package com.sidert.sidertmovil.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import androidx.appcompat.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.database.DBhelper;
import com.sidert.sidertmovil.database.SidertTables;
import com.sidert.sidertmovil.models.MCartera;
import com.sidert.sidertmovil.models.MCatalogo;
import com.sidert.sidertmovil.models.MPlazos;
import com.sidert.sidertmovil.models.MTickets;
import com.sidert.sidertmovil.models.ModeloColonia;
import com.sidert.sidertmovil.models.ModeloEstados;
import com.sidert.sidertmovil.models.ModeloMunicipio;
import com.sidert.sidertmovil.models.ModeloOcupaciones;
import com.sidert.sidertmovil.models.ModeloSectores;
import com.sidert.sidertmovil.models.catalogos.Campanas;
import com.sidert.sidertmovil.models.catalogos.CampanasDao;
import com.sidert.sidertmovil.models.catalogos.Localidad;
import com.sidert.sidertmovil.models.catalogos.SucursalesLocalidades;
import com.sidert.sidertmovil.models.catalogos.SucursalesLocalidadesDao;
import com.sidert.sidertmovil.services.Catalogos.CatalogosCampanas;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.sidert.sidertmovil.database.SidertTables.SidertEntry.TABLE_IDENTIFICACIONES;
import static com.sidert.sidertmovil.utils.Constants.CONTROLLER_CAMAPANAS;
import static com.sidert.sidertmovil.utils.Constants.CONTROLLER_CATALOGOS;
import static com.sidert.sidertmovil.utils.Constants.LOCALIDADES;
import static com.sidert.sidertmovil.utils.Constants.TBL_CATALOGOS_CAMPANAS;
import static com.sidert.sidertmovil.utils.Constants.TBL_DESTINOS_CREDITO;
import static com.sidert.sidertmovil.utils.Constants.TBL_ESTADOS_CIVILES;
import static com.sidert.sidertmovil.utils.Constants.TBL_IDENTIFICACIONES_TIPO;
import static com.sidert.sidertmovil.utils.Constants.TBL_MEDIOS_CONTACTO;
import static com.sidert.sidertmovil.utils.Constants.TBL_MEDIOS_PAGO_ORI;
import static com.sidert.sidertmovil.utils.Constants.TBL_NIVELES_ESTUDIOS;
import static com.sidert.sidertmovil.utils.Constants.TBL_PARENTESCOS;
import static com.sidert.sidertmovil.utils.Constants.TBL_PLAZOS_PRESTAMOS;
import static com.sidert.sidertmovil.utils.Constants.TBL_SUCURSALES_LOCALIDADES;
import static com.sidert.sidertmovil.utils.Constants.TBL_VIVIENDA_TIPOS;
import static com.sidert.sidertmovil.utils.Constants.TICKETS;
import static com.sidert.sidertmovil.utils.Constants.camara;
import static com.sidert.sidertmovil.utils.Constants.logout;
import static com.sidert.sidertmovil.utils.Constants.sidert;
import static com.sidert.sidertmovil.utils.WebServicesRoutes.CONTROLLER_CATALOGOS_CAMP;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Sincronizar_Catalogos {

    private Context ctx;

    private Integer centroCosto = 0;

    public void GetEstados(final Context ctx){

        final AlertDialog pDialog = Popups.showLoadingDialog(ctx, R.string.please_wait, R.string.loading_info);
        pDialog.setCancelable(false);
        pDialog.show();
        SessionManager session = SessionManager.getInstance(ctx);

        ManagerInterface api = RetrofitClient.generalRF(CONTROLLER_CATALOGOS, ctx).create(ManagerInterface.class);

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
        final SessionManager session = SessionManager.getInstance(ctx);

        ManagerInterface api = RetrofitClient.generalRF(CONTROLLER_CATALOGOS, ctx).create(ManagerInterface.class);

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
        SessionManager session = SessionManager.getInstance(ctx);


        ManagerInterface api = RetrofitClient.generalRF(CONTROLLER_CATALOGOS, ctx).create(ManagerInterface.class);

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
        SessionManager session = SessionManager.getInstance(ctx);

        ManagerInterface api = RetrofitClient.generalRF(CONTROLLER_CATALOGOS, ctx).create(ManagerInterface.class);

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
        SessionManager session = SessionManager.getInstance(ctx);

        ManagerInterface api = RetrofitClient.generalRF(CONTROLLER_CATALOGOS, ctx).create(ManagerInterface.class);

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
        SessionManager session = SessionManager.getInstance(ctx);

        ManagerInterface api = RetrofitClient.generalRF(CONTROLLER_CATALOGOS, ctx).create(ManagerInterface.class);

        Call<List<MCatalogo>> call = api.getIdentificaciones("Bearer "+ session.getUser().get(7));

        call.enqueue(new Callback<List<MCatalogo>>() {
            @Override
            public void onResponse(Call<List<MCatalogo>> call, Response<List<MCatalogo>> response) {
                switch (response.code()){
                    case 200:
                        List<MCatalogo> identificaciones = response.body();
                        new RegistrarCatalogos()
                                .execute(identificaciones, ctx, TBL_IDENTIFICACIONES_TIPO);
                        break;
                }
                pDialog.dismiss();
            }
            @Override
            public void onFailure(Call<List<MCatalogo>> call, Throwable t) {
                Log.e("FailIdentificacion", t.getMessage());
                pDialog.dismiss();
            }
        });

    }

    public void GetViviendaTipos(final Context ctx){
        final AlertDialog pDialog = Popups.showLoadingDialog(ctx, R.string.please_wait, R.string.loading_info);
        pDialog.setCancelable(false);
        pDialog.show();
        SessionManager session = SessionManager.getInstance(ctx);

        ManagerInterface api = RetrofitClient.generalRF(CONTROLLER_CATALOGOS, ctx).create(ManagerInterface.class);

        Call<List<MCatalogo>> call = api.getViviendaTipos("Bearer "+ session.getUser().get(7));

        call.enqueue(new Callback<List<MCatalogo>>() {
            @Override
            public void onResponse(Call<List<MCatalogo>> call, Response<List<MCatalogo>> response) {
                switch (response.code()){
                    case 200:
                        List<MCatalogo> vivienda = response.body();
                        new RegistrarCatalogos()
                                .execute(vivienda, ctx, TBL_VIVIENDA_TIPOS);
                        break;
                }
                pDialog.dismiss();
            }
            @Override
            public void onFailure(Call<List<MCatalogo>> call, Throwable t) {
                //Log.e("FailIdentificacion", t.getMessage());
                pDialog.dismiss();
            }
        });

    }

    public void GetMediosContacto(final Context ctx){
        final AlertDialog pDialog = Popups.showLoadingDialog(ctx, R.string.please_wait, R.string.loading_info);
        pDialog.setCancelable(false);
        pDialog.show();
        SessionManager session = SessionManager.getInstance(ctx);

        ManagerInterface api = RetrofitClient.generalRF(CONTROLLER_CATALOGOS, ctx).create(ManagerInterface.class);

        Call<List<MCatalogo>> call = api.getMediosContacto("Bearer "+ session.getUser().get(7));

        call.enqueue(new Callback<List<MCatalogo>>() {
            @Override
            public void onResponse(Call<List<MCatalogo>> call, Response<List<MCatalogo>> response) {
                switch (response.code()){
                    case 200:
                        List<MCatalogo> mediosContacto = response.body();
                        new RegistrarCatalogos()
                                .execute(mediosContacto, ctx, TBL_MEDIOS_CONTACTO);
                        break;
                }
                pDialog.dismiss();
            }
            @Override
            public void onFailure(Call<List<MCatalogo>> call, Throwable t) {
                //Log.e("FailIdentificacion", t.getMessage());
                pDialog.dismiss();
            }
        });

    }

    public void GetDestinosCredito(final Context ctx){
        final AlertDialog pDialog = Popups.showLoadingDialog(ctx, R.string.please_wait, R.string.loading_info);
        pDialog.setCancelable(false);
        pDialog.show();
        SessionManager session = SessionManager.getInstance(ctx);

        ManagerInterface api = RetrofitClient.generalRF(CONTROLLER_CATALOGOS, ctx).create(ManagerInterface.class);

        Call<List<MCatalogo>> call = api.getDestinosCredito("Bearer "+ session.getUser().get(7));

        call.enqueue(new Callback<List<MCatalogo>>() {
            @Override
            public void onResponse(Call<List<MCatalogo>> call, Response<List<MCatalogo>> response) {
                switch (response.code()){
                    case 200:
                        List<MCatalogo> destinosCredito = response.body();
                        new RegistrarCatalogos()
                                .execute(destinosCredito, ctx, TBL_DESTINOS_CREDITO);
                        break;
                }
                pDialog.dismiss();
            }
            @Override
            public void onFailure(Call<List<MCatalogo>> call, Throwable t) {
                //Log.e("FailIdentificacion", t.getMessage());
                pDialog.dismiss();
            }
        });

    }

    public void GetEstadosCiviles(final Context ctx){
        final AlertDialog pDialog = Popups.showLoadingDialog(ctx, R.string.please_wait, R.string.loading_info);
        pDialog.setCancelable(false);
        pDialog.show();
        SessionManager session = SessionManager.getInstance(ctx);

        ManagerInterface api = RetrofitClient.generalRF(CONTROLLER_CATALOGOS, ctx).create(ManagerInterface.class);

        Call<List<MCatalogo>> call = api.getEstadosCiviles("Bearer "+ session.getUser().get(7));

        call.enqueue(new Callback<List<MCatalogo>>() {
            @Override
            public void onResponse(Call<List<MCatalogo>> call, Response<List<MCatalogo>> response) {
                switch (response.code()){
                    case 200:
                        List<MCatalogo> estadosCiviles = response.body();
                        new RegistrarCatalogos()
                                .execute(estadosCiviles, ctx, TBL_ESTADOS_CIVILES);
                        break;
                }
                pDialog.dismiss();
            }
            @Override
            public void onFailure(Call<List<MCatalogo>> call, Throwable t) {
                Log.e("FailIdentificacion", t.getMessage());
                pDialog.dismiss();
            }
        });
    }

    public void GetNivelesEstudios(final Context ctx){
        final AlertDialog pDialog = Popups.showLoadingDialog(ctx, R.string.please_wait, R.string.loading_info);
        pDialog.setCancelable(false);
        pDialog.show();
        SessionManager session = SessionManager.getInstance(ctx);

        ManagerInterface api = RetrofitClient.generalRF(CONTROLLER_CATALOGOS, ctx).create(ManagerInterface.class);

        Call<List<MCatalogo>> call = api.getNivelesEstudios("Bearer "+ session.getUser().get(7));

        call.enqueue(new Callback<List<MCatalogo>>() {
            @Override
            public void onResponse(Call<List<MCatalogo>> call, Response<List<MCatalogo>> response) {
                switch (response.code()){
                    case 200:
                        List<MCatalogo> nivelesEstudios = response.body();
                        new RegistrarCatalogos()
                                .execute(nivelesEstudios, ctx, TBL_NIVELES_ESTUDIOS);
                        break;
                }
                pDialog.dismiss();
            }
            @Override
            public void onFailure(Call<List<MCatalogo>> call, Throwable t) {
                Log.e("FailIdentificacion", t.getMessage());
                pDialog.dismiss();
            }
        });
    }

    public void GetMediosPagoOri(final Context ctx){
        final AlertDialog pDialog = Popups.showLoadingDialog(ctx, R.string.please_wait, R.string.loading_info);
        pDialog.setCancelable(false);
        //pDialog.show();
        SessionManager session = SessionManager.getInstance(ctx);

        ManagerInterface api = RetrofitClient.generalRF(CONTROLLER_CATALOGOS, ctx).create(ManagerInterface.class);

        Call<List<MCatalogo>> call = api.getMediosPagoOrig("Bearer "+ session.getUser().get(7));

        call.enqueue(new Callback<List<MCatalogo>>() {
            @Override
            public void onResponse(Call<List<MCatalogo>> call, Response<List<MCatalogo>> response) {
                switch (response.code()){
                    case 200:
                        List<MCatalogo> mediosPagoOri = response.body();
                        new RegistrarCatalogos()
                                .execute(mediosPagoOri, ctx, TBL_MEDIOS_PAGO_ORI);
                        break;
                }
                //pDialog.dismiss();
            }
            @Override
            public void onFailure(Call<List<MCatalogo>> call, Throwable t) {
                Log.e("FailIdentificacion", t.getMessage());
                //pDialog.dismiss();
            }
        });
    }

    public void GetParentesco(final Context ctx){
        final AlertDialog pDialog = Popups.showLoadingDialog(ctx, R.string.please_wait, R.string.loading_info);
        pDialog.setCancelable(false);
        pDialog.show();
        SessionManager session = SessionManager.getInstance(ctx);

        ManagerInterface api = RetrofitClient.generalRF(CONTROLLER_CATALOGOS, ctx).create(ManagerInterface.class);

        Call<List<MCatalogo>> call = api.getParentesco("Bearer "+ session.getUser().get(7));

        call.enqueue(new Callback<List<MCatalogo>>() {
            @Override
            public void onResponse(Call<List<MCatalogo>> call, Response<List<MCatalogo>> response) {
                switch (response.code()){
                    case 200:
                        List<MCatalogo> parentesco = response.body();
                        new RegistrarCatalogos()
                                .execute(parentesco, ctx, TBL_PARENTESCOS);
                        break;
                }
                pDialog.dismiss();
            }
            @Override
            public void onFailure(Call<List<MCatalogo>> call, Throwable t) {
                Log.e("FailIdentificacion", t.getMessage());
                pDialog.dismiss();
            }
        });
    }

    public void GetCategoriasTickets(final Context ctx){
        this.ctx = ctx;
        SessionManager session = SessionManager.getInstance(ctx);


        ManagerInterface api = RetrofitClient.generalRF(CONTROLLER_CATALOGOS, ctx).create(ManagerInterface.class);

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
        SessionManager session = SessionManager.getInstance(ctx);


        ManagerInterface api = RetrofitClient.generalRF(CONTROLLER_CATALOGOS, ctx).create(ManagerInterface.class);

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

    public void GetCatalogosCampanas(final Context ctx){
        this.ctx = ctx;

        ManagerInterface api = RetrofitClient.generalRF(CONTROLLER_CATALOGOS, ctx).create(ManagerInterface.class);

        Call<List<Campanas>> call = api.obtenerCatalogos();

        call.enqueue(new Callback<List<Campanas>>() {
            @Override
            public void onResponse(Call<List<Campanas>> call, Response<List<Campanas>> response) {
                Log.e("CodeCampañas","Code:"+response.code());
                switch (response.code()){
                    case 200:
                        List<Campanas> campanas = response.body();
                        new RegistrarCampanas()
                                .execute(campanas,ctx);
                        break;
                }
            }
            @Override
            public void onFailure(Call<List<Campanas>> call, Throwable t) {
                Log.e("FailCampañas",t.getMessage());
            }
        });
    }

    public void GetLocalidades(final Context ctx){
        this.ctx = ctx;
        String municipio_id = "15";
        ManagerInterface api = RetrofitClient.generalRF(CONTROLLER_CATALOGOS,ctx).create(ManagerInterface.class);

        Call<List<Localidad>> call = api.getLocalidad(municipio_id);

        call.enqueue(new Callback<List<Localidad>>() {
            @Override
            public void onResponse(Call<List<Localidad>> call, Response<List<Localidad>> response) {
                Log.e("CodeLocalidad","Code:"+response.code());
                switch (response.code()){
                    case 200:
                        List<Localidad> localidads = response.body();
                        new RegistrarLocalidades()
                                .execute(localidads, ctx);
                        break;
                }
            }
            @Override
            public void onFailure(Call<List<Localidad>> call, Throwable t) {
                Log.e("FailIdentificacion", t.getMessage());
            }
        });
    }

    public void GetSucursalLocalidades(final Context ctx){
        this.ctx = ctx;
        SucursalesLocalidadesDao sl = new SucursalesLocalidadesDao(ctx);

        ManagerInterface api = RetrofitClient.generalRF(CONTROLLER_CATALOGOS,ctx).create(ManagerInterface.class);

        centroCosto  = sl.obtenerCentroCosto();

        Call<List<SucursalesLocalidades>> call = api.getSucursalLocalidades(centroCosto);


        call.enqueue(new Callback<List<SucursalesLocalidades>>() {
            @Override
            public void onResponse(Call<List<SucursalesLocalidades>> call, Response<List<SucursalesLocalidades>> response) {
                Log.e("CodeSucursalesLocalidad","Code:"+response.code());
                switch (response.code()){
                    case 200:
                        List<SucursalesLocalidades> sucursalesLocalidades = response.body();
                        new RegistrarLocalidades()
                                .execute(sucursalesLocalidades,ctx);

                }
            }

            @Override
            public void onFailure(Call<List<SucursalesLocalidades>> call, Throwable t) {

            }
        });
    }

    public class RegistrarColonias extends AsyncTask<Object, Void, String> {
        AlertDialog pDialog;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            //pDialog = Popups.showLoadingDialog(ctx, R.string.please_wait, R.string.loading_info);
            //pDialog.setCancelable(false);
            //pDialog.show();
        }

        @Override
        protected String doInBackground(Object... params) {
            // TODO Auto-generated method stub
            Log.i("doInBackground" , "Entra en doInBackground");
            List<ModeloColonia> colonias = (List<ModeloColonia>) params[0];
            Context ctx = (Context) params[1];
            if (colonias.size() > 0){
                DBhelper dBhelper = DBhelper.getInstance(ctx);
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
            //pDialog.dismiss();
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
                DBhelper dBhelper = DBhelper.getInstance(ctx);
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
                DBhelper dBhelper = DBhelper.getInstance(ctx);
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
                DBhelper dBhelper = DBhelper.getInstance(ctx);
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
                DBhelper dBhelper = DBhelper.getInstance(ctx);
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
            List<MCatalogo> identificaciones = (List<MCatalogo>) params[0];
            if (identificaciones.size() > 0){
                DBhelper dBhelper = DBhelper.getInstance(ctx);
                SQLiteDatabase db = dBhelper.getWritableDatabase();
                Cursor row;
                for (int i = 0; i < identificaciones.size(); i++){
                    int id = identificaciones.get(i).getId();
                    String where = " WHERE identificacion_id = ?";
                    String order = "";
                    String[] args =  new String[] {String.valueOf(id)};
                    row = dBhelper.getRecords(TABLE_IDENTIFICACIONES, where, order, args);
                    if (row.getCount() == 0){
                        HashMap<Integer, String> values = new HashMap<>();
                        values.put(0, String.valueOf(identificaciones.get(i).getId()));
                        values.put(1, identificaciones.get(i).getNombre());
                        dBhelper.saveIdentificaciones(db, values);
                    }
                    row.close();
                }
            };
            return "termina";
        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            pDialog.dismiss();
            super.onPostExecute(result);

        }

    }

    public class RegistrarCatalogos extends AsyncTask<Object, Void, String> {
        AlertDialog pDialog;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            //pDialog = Popups.showLoadingDialog(ctx, R.string.please_wait, R.string.loading_info);
            //pDialog.setCancelable(false);
            //pDialog.show();
        }

        @Override
        protected String doInBackground(Object... params) {
            // TODO Auto-generated method stub
            Log.i("doInBackground" , "Entra en doInBackground");
            List<MCatalogo> catalogo = (List<MCatalogo>) params[0];
            String tbl = (String) params[2];
            ctx = (Context) params[1];

            if (catalogo.size() > 0){
                DBhelper dBhelper = DBhelper.getInstance(ctx);
                SQLiteDatabase db = dBhelper.getWritableDatabase();
                Cursor row;
                for (int i = 0; i < catalogo.size(); i++){
                    int id = catalogo.get(i).getId();
                    String where = " WHERE id = ?";
                    String order = "";
                    String[] args =  new String[] {String.valueOf(id)};
                    row = dBhelper.getRecords(tbl, where, order, args);
                    if (row.getCount() == 0){
                        HashMap<Integer, String> values = new HashMap<>();
                        values.put(0, String.valueOf(catalogo.get(i).getId()));
                        values.put(1, catalogo.get(i).getNombre());
                        dBhelper.saveCatalogos(tbl, db, values);
                    }
                    row.close();
                }
            }
            return "termina";
        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            //pDialog.dismiss();
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
                DBhelper dBhelper = DBhelper.getInstance(ctx);
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
                DBhelper dBhelper = DBhelper.getInstance(ctx);
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

    public class RegistrarCampanas extends AsyncTask<Object, Void,String>{
        AlertDialog pDialog;

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            pDialog = Popups.showLoadingDialog(ctx,R.string.please_wait,R.string.loading_info);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(Object... params){
            Log.i("doInBackground", "Entra en doInBackground");
            List<Campanas> campanas = (List<Campanas>) params[0];
            HashMap<Integer,String> values = new HashMap<>();
            ContentValues cv = new ContentValues();
            DBhelper dBhelper = DBhelper.getInstance(ctx);
            SQLiteDatabase db = dBhelper.getWritableDatabase();
            Cursor row;
            if(campanas.size()>0){

                for (int i = 0; i<campanas.size();i++){
                    String where = " WHERE id_campana = ?";
                    String order = " ";

                    String[] args = new String[]{String.valueOf(campanas.get(i).getId())};
                    row = dBhelper.getCatalogosCam(TBL_CATALOGOS_CAMPANAS, where, args);

                    if(row.getCount() == 0){
                        row.moveToFirst();
                        values.put(1,String.valueOf(campanas.get(i).getId_campanas()));
                        values.put(2,campanas.get(i).getTipo_campanas());
                        values.put(3,String.valueOf(campanas.get(i).getEstatus()));
                        dBhelper.saveCatalogosCampanas(db,values);
                    }

                    if(row.getCount()>0){
                        row.moveToFirst();
                        cv.put("id_campana",campanas.get(i).getId_campanas());
                        cv.put("tipo_campana",campanas.get(i).getTipo_campanas());
                        cv.put("estatus",campanas.get(i).getEstatus());
                        db.update(TBL_CATALOGOS_CAMPANAS,cv,"id_campana = ?",new String[]{String.valueOf(campanas.get(i).getId_campanas())});
                    }
                    row.close();
                }
            }
            return "termino";
        }
        @Override
        protected void onPostExecute(String result){
            pDialog.dismiss();
            super.onPostExecute(result);
        }
    }
    /** NO TOCAR NADA EN ESTA PARTE - PORQUE ESTA EN FASE DE PRUEBAS - DEPENDE DE TODAS LAS VENTAS ABIERTAS*/
    public class RegistrarLocalidades extends AsyncTask<Object,Void, String>{
        AlertDialog pDialog;

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            pDialog = Popups.showLoadingDialog(ctx,R.string.please_wait,R.string.loading_info);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(Object... params){
            Log.i("doInBackground", "Entra en doInBackground");
            List<SucursalesLocalidades> slm = (List<SucursalesLocalidades>) params[0];

            HashMap<Integer,String> values = new HashMap<>();
            ContentValues cv = new ContentValues();
            DBhelper dBhelper = DBhelper.getInstance(ctx);
            SQLiteDatabase db = dBhelper.getWritableDatabase();
            Cursor row;
            if(slm.size()>0){
                for(int i=0;i<slm.size();i++){
                    String where = " WHERE id = ?";
                    String order = " ";
                    //String[] args = new String[]{String.valueOf(slm.get(i).getId())};

                    //row = dBhelper.getLocalidades(TBL_SUCURSALES_LOCALIDADES,where,args);

                    //if(row.moveToFirst()){
                        //values.put(1,String.valueOf(slm.get(i).getId()));
                        //values.put(2,String.valueOf(slm.get(i).getCentroCosto()));
                        //values.put(3,String.valueOf(slm.get(i).getId_municipio()));
                        //values.put(4,slm.get(i).getLocalidad());
                        //values.put(5,slm.get(i).getColonia());
                        //values.put(6,String.valueOf(slm.get(i).getCodigo_postal()));
                        dBhelper.saveSucursalLocalidad(db,values);
                    //}

                }
            }
            return "termino";
        }
        @Override
        protected void onPostExecute(String result){
            pDialog.dismiss();
            super.onPostExecute(result);
        }
    }
}