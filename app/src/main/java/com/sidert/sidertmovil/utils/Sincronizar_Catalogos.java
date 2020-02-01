package com.sidert.sidertmovil.utils;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.sidert.sidertmovil.database.DBhelper;
import com.sidert.sidertmovil.database.SidertTables;
import com.sidert.sidertmovil.models.ModeloColonia;
import com.sidert.sidertmovil.models.ModeloEstados;
import com.sidert.sidertmovil.models.ModeloMunicipio;
import com.sidert.sidertmovil.models.ModeloOcupaciones;
import com.sidert.sidertmovil.models.ModeloSectores;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Sincronizar_Catalogos {

    public void GetEstados(final Context ctx){
        SessionManager session = new SessionManager(ctx);

        ManagerInterface api = new RetrofitClient().generalRF(Constants.CONTROLLER_CATALOGOS).create(ManagerInterface.class);

        Call<List<ModeloEstados>> call = api.getEstados("Bearer "+ session.getUser().get(7));

        call.enqueue(new Callback<List<ModeloEstados>>() {
            @Override
            public void onResponse(Call<List<ModeloEstados>> call, Response<List<ModeloEstados>> response) {
                switch (response.code()){
                    case 200:
                        List<ModeloEstados> estados = response.body();
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
                                    HashMap<Integer, String> params = new HashMap<>();
                                    params.put(0, String.valueOf(estados.get(i).getId()));
                                    params.put(1, estados.get(i).getNombre());
                                    params.put(2, String.valueOf(estados.get(i).getPaisId()));
                                    dBhelper.saveEstados(db, params);
                                }
                                row.close();
                            }
                            Toast.makeText(ctx, "Catálogo Estados Sincronizado", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(ctx, "No se encontró información", Toast.LENGTH_SHORT).show();
                        }
                        break;
                }


            }
            @Override
            public void onFailure(Call<List<ModeloEstados>> call, Throwable t) {
                Log.e("FailEstados", t.getMessage());
            }
        });

    }

    public void GetMunicipios(final Context ctx){
        final SessionManager session = new SessionManager(ctx);

        ManagerInterface api = new RetrofitClient().generalRF(Constants.CONTROLLER_CATALOGOS).create(ManagerInterface.class);

        Call<List<ModeloMunicipio>> call = api.getMunicipios("Bearer "+ session.getUser().get(7));

        call.enqueue(new Callback<List<ModeloMunicipio>>() {
            @Override
            public void onResponse(Call<List<ModeloMunicipio>> call, Response<List<ModeloMunicipio>> response) {
                switch (response.code()){
                    case 200:
                        List<ModeloMunicipio> municipios = response.body();
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
                                    HashMap<Integer, String> params = new HashMap<>();
                                    params.put(0, String.valueOf(municipios.get(i).getId()));
                                    params.put(1, municipios.get(i).getNombre());
                                    params.put(2, String.valueOf(municipios.get(i).getEstadoId()));
                                    dBhelper.saveMunicipios(db, params);
                                }

                                row.close();
                            }
                            Toast.makeText(ctx, "Base de datos sincronizada", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(ctx, "No se encontró información", Toast.LENGTH_SHORT).show();
                        }
                        break;
                }

            }
            @Override
            public void onFailure(Call<List<ModeloMunicipio>> call, Throwable t) {
                Log.e("FailMunicipios", t.getMessage());
            }
        });

    }

    public void GetColonias(final Context ctx){
        SessionManager session = new SessionManager(ctx);

        ManagerInterface api = new RetrofitClient().generalRF(Constants.CONTROLLER_CATALOGOS).create(ManagerInterface.class);

        Call<List<ModeloColonia>> call = api.getColonias("Bearer "+ session.getUser().get(7));

        call.enqueue(new Callback<List<ModeloColonia>>() {
            @Override
            public void onResponse(Call<List<ModeloColonia>> call, Response<List<ModeloColonia>> response) {
                switch (response.code()){
                    case 200:
                        List<ModeloColonia> colonias = response.body();
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
                                    HashMap<Integer, String> params = new HashMap<>();
                                    params.put(0, String.valueOf(colonias.get(i).getId()));
                                    params.put(1, colonias.get(i).getNombre());
                                    params.put(2, String.valueOf(colonias.get(i).getCp()));
                                    params.put(3, String.valueOf(colonias.get(i).getMunicipioId()));
                                    dBhelper.saveColonias(db, params);
                                }
                                row.close();
                            }
                            Toast.makeText(ctx, "Catálogo de Colonias actualizadas", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(ctx, "No se encontró información", Toast.LENGTH_SHORT).show();
                        }
                        break;
                }


            }
            @Override
            public void onFailure(Call<List<ModeloColonia>> call, Throwable t) {
                Log.e("FailColonia", t.getMessage());
            }
        });

    }

    public void GetSectores(final Context ctx){
        SessionManager session = new SessionManager(ctx);

        ManagerInterface api = new RetrofitClient().generalRF(Constants.CONTROLLER_CATALOGOS).create(ManagerInterface.class);

        Call<List<ModeloSectores>> call = api.getSectores("Bearer "+ session.getUser().get(7));

        call.enqueue(new Callback<List<ModeloSectores>>() {
            @Override
            public void onResponse(Call<List<ModeloSectores>> call, Response<List<ModeloSectores>> response) {
                switch (response.code()){
                    case 200:
                        List<ModeloSectores> sectores = response.body();
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
                                    HashMap<Integer, String> params = new HashMap<>();
                                    params.put(0, String.valueOf(sectores.get(i).getId()));
                                    params.put(1, sectores.get(i).getNombre());
                                    params.put(2, String.valueOf(sectores.get(i).getNivelRiesgo()));
                                    dBhelper.saveSectores(db, params);
                                }
                                row.close();
                            }
                            Toast.makeText(ctx, "Catálogo de Sectores Actualizada", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(ctx, "No se encontró información", Toast.LENGTH_SHORT).show();
                        }
                        break;
                }


            }
            @Override
            public void onFailure(Call<List<ModeloSectores>> call, Throwable t) {
                Log.e("FailColonia", t.getMessage());
            }
        });

    }

    public void GetOcupaciones(final Context ctx){
        SessionManager session = new SessionManager(ctx);

        ManagerInterface api = new RetrofitClient().generalRF(Constants.CONTROLLER_CATALOGOS).create(ManagerInterface.class);

        Call<List<ModeloOcupaciones>> call = api.getOcupaciones("Bearer "+ session.getUser().get(7));

        call.enqueue(new Callback<List<ModeloOcupaciones>>() {
            @Override
            public void onResponse(Call<List<ModeloOcupaciones>> call, Response<List<ModeloOcupaciones>> response) {
                switch (response.code()){
                    case 200:
                        List<ModeloOcupaciones> ocupaciones = response.body();
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
                                    HashMap<Integer, String> params = new HashMap<>();
                                    params.put(0, String.valueOf(ocupaciones.get(i).getId()));
                                    params.put(1, ocupaciones.get(i).getNombre());
                                    params.put(2, ocupaciones.get(i).getClave());
                                    params.put(3, String.valueOf(ocupaciones.get(i).getNivelRiesgo()));
                                    params.put(4, String.valueOf(ocupaciones.get(i).getSectorId()));
                                    dBhelper.saveOcupaciones(db, params);
                                }
                                row.close();
                            }
                            Toast.makeText(ctx, "Catálogo de Ocupaciones Actualizada", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(ctx, "No se encontró información", Toast.LENGTH_SHORT).show();
                        }
                        break;
                }


            }
            @Override
            public void onFailure(Call<List<ModeloOcupaciones>> call, Throwable t) {
                Log.e("FailColonia", t.getMessage());
            }
        });

    }
}
