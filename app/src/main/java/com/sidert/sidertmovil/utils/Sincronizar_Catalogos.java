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

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Sincronizar_Catalogos {

    public void GetEstados(final Context ctx){
        SessionManager session = new SessionManager(ctx);

        ManagerInterface api = new RetrofitClient().generalRF(Constants.CONTROLLER_CATALOGOS).create(ManagerInterface.class);

        Call<List<ModeloEstados>> call = api.getEstados("Bearer "+ "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJtYXRlcm5vIjoiRnJhbmNpc2NvIiwicGF0ZXJubyI6IkxlZG8iLCJ1c2VyX25hbWUiOiJwcm9ncmFtYWRvcjAyIiwic2NvcGUiOlsicmVhZCIsIndyaXRlIl0sInNlcmllaWQiOiI1MDUiLCJpZCI6MSwiZXhwIjoxNTc4MjUwODEzLCJub21icmUiOiJKYXZpZXIiLCJhdXRob3JpdGllcyI6WyJST0xFX1NVUEVSIl0sImp0aSI6IjM0ODA3YjViLTcyZWMtNGQyOS04ZDU3LTFjNTM1YzBhZjdlNSIsImVtYWlsIjoicHJvZ3JhbWFkb3IwMkBzaWRlcnQuY29tLm14IiwiY2xpZW50X2lkIjoiYW5kcm9pZGFwcCJ9.KwwNUoNXFoYYz2NkspHdhLys4vYJxUJnstptkh60B0MQXa_yuSbZk4HEpH3Bzv9cxeMReoEkgRaP-nqHd_XFNVvSmt5VR_SMBTnsp7u6cbovUz5NvIO-N1t0WiGxivVrkHeOrUCGz_XyfHFxZS4Rn-tEm8w5kKjzIaDDGsw1DCpQ2bBjG6rAxTsBJdGMMMGCUdHHdiZklGUkTZPnbZJCVsGhxBDvKtrdT5Ft-kh4P1bAArX5nr06etE2dRZQv6MZE0V4S8YlGB3FF-UFPFZv53FTwv43hfq1PPm7yl2SNTBnWN1Zvj0KMAytOfmfZjbLdZ-8J2tufH4kSqvezhGVcA");

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
                                Log.e("Estado", estados.get(i).getNombre());
                                int id = estados.get(i).getId();
                                String where = " WHERE estado_id = ?";
                                String order = "";
                                String[] args =  new String[] {String.valueOf(estados.get(i).getId())};
                                row = dBhelper.getRecords(SidertTables.SidertEntry.TABLE_ESTADOS, where, order, args);
                                Log.v("CountSelect",row.getCount()+"");
                                if (row.getCount() == 0){
                                    HashMap<Integer, String> params = new HashMap<>();
                                    params.put(0, String.valueOf(estados.get(i).getId()));
                                    params.put(1, estados.get(i).getNombre());
                                    params.put(2, String.valueOf(estados.get(i).getPaisId()));
                                    dBhelper.saveEstados(db, params);
                                }
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

        Call<List<ModeloMunicipio>> call = api.getMunicipios("Bearer "+ "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJtYXRlcm5vIjoiRnJhbmNpc2NvIiwicGF0ZXJubyI6IkxlZG8iLCJ1c2VyX25hbWUiOiJwcm9ncmFtYWRvcjAyIiwic2NvcGUiOlsicmVhZCIsIndyaXRlIl0sInNlcmllaWQiOiI1MDUiLCJpZCI6MSwiZXhwIjoxNTc4MjUwODEzLCJub21icmUiOiJKYXZpZXIiLCJhdXRob3JpdGllcyI6WyJST0xFX1NVUEVSIl0sImp0aSI6IjM0ODA3YjViLTcyZWMtNGQyOS04ZDU3LTFjNTM1YzBhZjdlNSIsImVtYWlsIjoicHJvZ3JhbWFkb3IwMkBzaWRlcnQuY29tLm14IiwiY2xpZW50X2lkIjoiYW5kcm9pZGFwcCJ9.KwwNUoNXFoYYz2NkspHdhLys4vYJxUJnstptkh60B0MQXa_yuSbZk4HEpH3Bzv9cxeMReoEkgRaP-nqHd_XFNVvSmt5VR_SMBTnsp7u6cbovUz5NvIO-N1t0WiGxivVrkHeOrUCGz_XyfHFxZS4Rn-tEm8w5kKjzIaDDGsw1DCpQ2bBjG6rAxTsBJdGMMMGCUdHHdiZklGUkTZPnbZJCVsGhxBDvKtrdT5Ft-kh4P1bAArX5nr06etE2dRZQv6MZE0V4S8YlGB3FF-UFPFZv53FTwv43hfq1PPm7yl2SNTBnWN1Zvj0KMAytOfmfZjbLdZ-8J2tufH4kSqvezhGVcA");

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
                            for (int i = 0; i < municipios.size(); i++){
                                Log.e("Municipio", municipios.get(i).getNombre());
                                int id = municipios.get(i).getId();
                                String where = " WHERE municipio_id = ?";
                                String order = "";
                                String[] args =  new String[] {String.valueOf(municipios.get(i).getId())};
                                row = dBhelper.getRecords(SidertTables.SidertEntry.TABLE_MUNICIPIOS, where, order, args);
                                Log.v("CountSelect",row.getCount()+"");
                                if (row.getCount() == 0){
                                    HashMap<Integer, String> params = new HashMap<>();
                                    params.put(0, String.valueOf(municipios.get(i).getId()));
                                    params.put(1, municipios.get(i).getNombre());
                                    params.put(2, String.valueOf(municipios.get(i).getEstadoId()));
                                    dBhelper.saveMunicipios(db, params);
                                }
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

        Call<List<ModeloColonia>> call = api.getColonias("Bearer "+ "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJtYXRlcm5vIjoiRnJhbmNpc2NvIiwicGF0ZXJubyI6IkxlZG8iLCJ1c2VyX25hbWUiOiJwcm9ncmFtYWRvcjAyIiwic2NvcGUiOlsicmVhZCIsIndyaXRlIl0sInNlcmllaWQiOiI1MDUiLCJpZCI6MSwiZXhwIjoxNTc4MjUwODEzLCJub21icmUiOiJKYXZpZXIiLCJhdXRob3JpdGllcyI6WyJST0xFX1NVUEVSIl0sImp0aSI6IjM0ODA3YjViLTcyZWMtNGQyOS04ZDU3LTFjNTM1YzBhZjdlNSIsImVtYWlsIjoicHJvZ3JhbWFkb3IwMkBzaWRlcnQuY29tLm14IiwiY2xpZW50X2lkIjoiYW5kcm9pZGFwcCJ9.KwwNUoNXFoYYz2NkspHdhLys4vYJxUJnstptkh60B0MQXa_yuSbZk4HEpH3Bzv9cxeMReoEkgRaP-nqHd_XFNVvSmt5VR_SMBTnsp7u6cbovUz5NvIO-N1t0WiGxivVrkHeOrUCGz_XyfHFxZS4Rn-tEm8w5kKjzIaDDGsw1DCpQ2bBjG6rAxTsBJdGMMMGCUdHHdiZklGUkTZPnbZJCVsGhxBDvKtrdT5Ft-kh4P1bAArX5nr06etE2dRZQv6MZE0V4S8YlGB3FF-UFPFZv53FTwv43hfq1PPm7yl2SNTBnWN1Zvj0KMAytOfmfZjbLdZ-8J2tufH4kSqvezhGVcA");

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
                                Log.e("Colonia", colonias.get(i).getNombre());
                                int id = colonias.get(i).getId();
                                String where = " WHERE colonia_id = ?";
                                String order = "";
                                String[] args =  new String[] {String.valueOf(colonias.get(i).getId())};
                                row = dBhelper.getRecords(SidertTables.SidertEntry.TABLE_COLONIAS, where, order, args);
                                Log.v("CountSelect",row.getCount()+"");
                                if (row.getCount() == 0){
                                    HashMap<Integer, String> params = new HashMap<>();
                                    params.put(0, String.valueOf(colonias.get(i).getId()));
                                    params.put(1, colonias.get(i).getNombre());
                                    params.put(2, String.valueOf(colonias.get(i).getMunicipioId()));
                                    dBhelper.saveColonias(db, params);
                                }
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
            public void onFailure(Call<List<ModeloColonia>> call, Throwable t) {
                Log.e("FailColonia", t.getMessage());
            }
        });

    }
}
