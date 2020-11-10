package com.sidert.sidertmovil;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.sidert.sidertmovil.database.DBhelper;
import com.sidert.sidertmovil.utils.Miscellaneous;
import com.sidert.sidertmovil.utils.SessionManager;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.NetworkInterface;
import java.nio.charset.Charset;
import java.text.Normalizer;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import static com.sidert.sidertmovil.database.SidertTables.SidertEntry.TABLE_COLONIAS;
import static com.sidert.sidertmovil.database.SidertTables.SidertEntry.TABLE_MUNICIPIOS;
import static com.sidert.sidertmovil.utils.Constants.LOCALIDADES;
import static com.sidert.sidertmovil.utils.Constants.LOGIN_REPORT_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_INTEGRANTES_GPO;
import static com.sidert.sidertmovil.utils.Constants.TBL_RESPUESTAS_INTEGRANTE_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_TRACKER_ASESOR_T;
import static org.apache.commons.lang3.CharEncoding.UTF_8;

public class SplashSidertActivity extends AppCompatActivity {

    private Context ctx;
    DBhelper dBhelper;
    SQLiteDatabase db;
    boolean colonia = false;
    boolean municipio = false;
    boolean localidad = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dBhelper = new DBhelper(this);
        db = dBhelper.getWritableDatabase();
        dBhelper.onUpgrade(db, 1, 2);

        setContentView(R.layout.activity_splash_sidert);
        ctx = getApplicationContext();

        Cursor row;

        SessionManager session = new SessionManager(this);

        try {
            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface nif : all) {
                if (!nif.getName().equalsIgnoreCase("wlan0")) continue;

                byte[] macBytes = nif.getHardwareAddress();
                /*if (macBytes == null) {
                    return "";
                }*/

                StringBuilder res1 = new StringBuilder();
                for (byte b : macBytes) {
                    res1.append(Integer.toHexString(b & 0xFF) + ":");
                }

                if (res1.length() > 0) {
                    res1.deleteCharAt(res1.length() - 1);
                }

                String[] mac = res1.toString().toUpperCase().split(":");

                for (int i = 0; i < mac.length; i++){
                    if (mac[i].length() == 1)
                        mac[i] = "0"+mac[i];
                }

                String newMacAddress = mac[0]+":"+mac[1]+":"+mac[2]+":"+mac[3]+":"+mac[4]+":"+mac[5];
                session.setAddress(newMacAddress.toUpperCase());
            }
        } catch (Exception ex) {
            //handle exception
        }


        //String fabricante = Build.MANUFACTURER;
        //String modelo = Build.MODEL;

        //Log.e("FABRICANTE", fabricante);
        //Log.e("MODELO", modelo);

        //session.setDominio("http://192.168.100.5:", "8080");
        //session.setDominio("http://sidert.ddns.net:", "83");

        String sql = "SELECT * FROM " + LOGIN_REPORT_T + " ORDER BY login_timestamp DESC limit 1";
        row = db.rawQuery(sql, null);

        if (row.getCount() > 0){
            row.moveToFirst();

            String[] fechaLogin = row.getString(3).split(" ");
            String[] newFecha = fechaLogin[0].split("-");
            int dias = Miscellaneous.GetDiasAtraso(newFecha[2]+"-"+newFecha[1]+"-"+newFecha[0]);

            if (dias >= 1){
                //session.deleteUser();
                session.setUser(session.getUser().get(0),
                        session.getUser().get(1),
                        session.getUser().get(2),
                        session.getUser().get(3),
                        session.getUser().get(4),
                        session.getUser().get(5),
                        false,
                        session.getUser().get(7),
                        session.getUser().get(8),
                        session.getUser().get(9));
            }
            Log.e("DiasLogin", String.valueOf(dias));
        }

        /*new RegistrarColonias().execute();
        new RegistrarMunicipios().execute();
        new RegistrarLocalidades().execute();*/

        Handler handler_home=new Handler();
        handler_home.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent start = new Intent(ctx, MainActivity.class);
                start.putExtra("login", false);
                startActivity(start);
                finish();
            }
        },3000);

    }

    public class RegistrarLocalidades extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {
            Cursor row = dBhelper.getRecords(LOCALIDADES, "", "", null);
            if (row.getCount() == 0) {
                try {
                    InputStream is = getResources().openRawResource(R.raw.localidades);
                    BufferedReader reader = new BufferedReader(
                            new InputStreamReader(is, Charset.forName(UTF_8))
                    );
                    String line;
                    while ((line = reader.readLine()) != null) {
                        String[] localidad = line.split(",");

                        HashMap<Integer, String> values = new HashMap<>();
                        values.put(0, localidad[0].trim());
                        values.put(1, localidad[2].trim().toUpperCase());
                        values.put(2, localidad[1].trim());
                        dBhelper.saveLocalidades(db, values);
                    }
                } catch (IOException e) {

                }
            }
            return "Localidades";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            localidad = true;

            if (localidad && municipio && colonia){
                Handler handler_home=new Handler();
                handler_home.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent start = new Intent(ctx, MainActivity.class);
                        start.putExtra("login", false);
                        startActivity(start);
                        finish();
                    }
                },3000);
            }
        }
    }

    public class RegistrarMunicipios extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {
            Cursor row = dBhelper.getRecords(TABLE_MUNICIPIOS, "", "", null);
            if (row.getCount() == 0) {
                try {
                    InputStream is = getResources().openRawResource(R.raw.municipios);
                    BufferedReader reader = new BufferedReader(
                            new InputStreamReader(is, Charset.forName(UTF_8))
                    );
                    String line;
                    while ((line = reader.readLine()) != null) {
                        String[] municipio = line.split(",");
                        HashMap<Integer, String> values = new HashMap<>();
                        values.put(0, municipio[0]);
                        values.put(1, municipio[2]);
                        values.put(2, municipio[1]);


                        dBhelper.saveMunicipios(db, values);
                    }
                } catch (IOException e) {

                }
            }
            return "Municipios";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            municipio = true;

            if (localidad && municipio && colonia){
                Handler handler_home=new Handler();
                handler_home.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent start = new Intent(ctx, MainActivity.class);
                        start.putExtra("login", false);
                        startActivity(start);
                        finish();
                    }
                },3000);
            }
        }
    }

    public class RegistrarColonias extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {
            Cursor row = dBhelper.getRecords(TABLE_COLONIAS, "", "", null);
            if (row.getCount() == 0) {
                try {
                    InputStream is = getResources().openRawResource(R.raw.colonias);
                    BufferedReader reader = new BufferedReader(
                            new InputStreamReader(is, Charset.forName(UTF_8))
                    );
                    String line;
                    int i = 0;
                    while ((line = reader.readLine()) != null) {
                        String[] colonia = line.split(";");
                        HashMap<Integer, String> values = new HashMap<>();
                        values.put(0, colonia[0].trim());
                        values.put(1, colonia[2].trim().toUpperCase());
                        values.put(2, colonia[3].trim());
                        values.put(3, colonia[1].trim());
                        dBhelper.saveColonias(db, values);
                        i += 1;
                    }
                } catch (IOException e) {

                }
            }


            return "Colonias";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            colonia =  true;

            if (localidad && municipio && colonia){
                Handler handler_home=new Handler();
                handler_home.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent start = new Intent(ctx, MainActivity.class);
                        start.putExtra("login", false);
                        startActivity(start);
                        finish();
                    }
                },3000);
            }
        }
    }

}
