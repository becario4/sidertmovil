package com.sidert.sidertmovil;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;

import com.google.firebase.FirebaseApp;
import com.sidert.sidertmovil.database.DBhelper;
import com.sidert.sidertmovil.models.catalogos.Localidad;
import com.sidert.sidertmovil.models.catalogos.LocalidadDao;
import com.sidert.sidertmovil.utils.Miscellaneous;
import com.sidert.sidertmovil.utils.Servicios_Sincronizado;
import com.sidert.sidertmovil.utils.SessionManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.NetworkInterface;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import static com.sidert.sidertmovil.database.SidertTables.SidertEntry.TABLE_COLONIAS;
import static com.sidert.sidertmovil.database.SidertTables.SidertEntry.TABLE_MUNICIPIOS;
import static com.sidert.sidertmovil.utils.Constants.LOGIN_REPORT_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_CATALOGOS_CAMPANAS;
import static com.sidert.sidertmovil.utils.Constants.TBL_DATOS_ENTREGA_CARTERA;
import static org.apache.commons.lang3.CharEncoding.UTF_8;

public class SplashSidertActivity extends AppCompatActivity {
    /** VERSION ORIGINAL - DEMO */
    private Context ctx;
    static DBhelper dBhelper;
    static SQLiteDatabase db;
    boolean colonia = false;
    boolean municipio = false;
    boolean localidad = false;
    boolean campana = false;
    boolean entCartera = false;

    @SuppressLint("HardwareIds")
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        dBhelper = DBhelper.getInstance(this);
        db = dBhelper.getWritableDatabase();
        dBhelper.onUpgrade(db, 1, 2);

        FirebaseApp.initializeApp(this);

        setContentView(R.layout.activity_splash_sidert);
        ctx = getApplicationContext();

        Cursor row;

        /**Clase donde se guardan todas las variables de sesion*/
        SessionManager session = SessionManager.getInstance(this);

        /**Es para obtener la direccion MAC y guardarlo en variables de sesion*/
        try {
            Log.e("MODEL", Build.MODEL);
            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());

            if(Build.MODEL.trim().equals("SM-A022M") || Build.MODEL.trim().equals("SM-A127M") || Build.MODEL.trim().equals("SM-S908E") || Build.MODEL.trim().equals("SM-A045M") || Build.MODEL.trim().equals("SM-A145M") )
            {
                Log.e("MAC", Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID).toUpperCase());
                session.setAddress(Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID).toUpperCase());
            }

            for (NetworkInterface nif : all) {
                Log.e("MAC NIF", nif.getName());



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
                Log.e("MAC NIF ADDRESS", newMacAddress);

                /**Se guarda la MacAddress en variable de sesion*/
                if(Build.MODEL.trim().equals("SM-A022M") || Build.MODEL.trim().equals("SM-A127M") || Build.MODEL.trim().equals("SM-S908E") || Build.MODEL.trim().equals("SM-A045M") || Build.MODEL.trim().equals("SM-A145M"))
                {
                    session.setAddress(Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID).toUpperCase());
                }
                else
                {
                    session.setAddress(newMacAddress.toUpperCase());
                }
            }
        } catch (Exception ex) {
            //handle exception
        }
        /** MODIFCAR EL CAMPO EDAD - VALIDAR CAMPOS DOCUMENTOS 29 - 05 - 2023*/
        //Log.e("Mac_address", Miscellaneous.DecodePassword("MkQ6UzQ6cjQ6EjM6YTQ6MkR="));
        session.setDominio("http://sidert.ddns.net:86");//PRUEBAS
        //session.setDominio("http://192.168.3.177:", "8083");//LOCALHOST
        //session.setDominio("http://192.168.0.125:", "8083");
        //session.setDominio("http://sidert.ddns.net:", "83");//PRODUCCION
        //session.setDominio("http://sidert.ddns.net:", "84");
        //session.setDominio("http://10.0.1.124:", "8086");
        /**Se obtiene el ultimo login registrado*/
        String sql = "SELECT * FROM " + LOGIN_REPORT_T + " ORDER BY login_timestamp DESC limit 1";
        row = db.rawQuery(sql, null);
        /**Encontraron registros de login*/
        if (row.getCount() > 0){
            row.moveToFirst();

            /**Se separa la fecha de la hora*/
            String[] fechaLogin = row.getString(3).split(" ");
            /**Se separa la fecha por segmentos (año - mes - dia)*/
            String[] newFecha = fechaLogin[0].split("-");
            /**Se valida cuantos dias de atraso tiene el ultimo login con la fecha actual*/

            int dias = Miscellaneous.GetDiasAtraso(newFecha[2]+"-"+newFecha[1]+"-"+newFecha[0]);

            /**Si los dias de atraso es mayor o igual a 1 se cierra sesion colocando en FLAG = false*/
            if (dias >= 1){
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

        }

        /**Funciones para registrar catalogos de colonias, municipios, localidades es para solo para
         las secciones de originacion y renovacion de los estados de
         Veracruz, Puebla, Tlaxcala y solo se registra por primera vez cuando se borran datos*/
        Servicios_Sincronizado ss = new Servicios_Sincronizado();
        ss.GetServiciosSincronizados(ctx, false);
        new RegistrarCampana().execute();
        new RegistrarColonias().execute();
        new RegistrarMunicipios().execute();
        new RegistrarLocalidades().execute();
        new RegistrarEntregaCartera().execute();


        /**Este proceso era antes de lanzar originacion y renovacion*/
        /*Handler handler_home=new Handler();
        handler_home.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent start = new Intent(ctx, MainActivity.class);
                start.putExtra("login", false);
                startActivity(start);
                finish();
            }
        },3000);*/

    }

    /**Registra las localidades de los estados de Veracruz, Puebla, Tlaxcala*/
    public class RegistrarLocalidades extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {
            LocalidadDao localidadDao = new LocalidadDao(ctx);
            List<Localidad> localidades = localidadDao.findAll();

            if(localidades.size() == 0) {
                Log.e("LOCALIDADES", "INTENTANDO REGISTRAR");
                try {
                    /**Lee el archivo que se encuentra en la carpeta res/raw/localidades*/
                    InputStream is = getResources().openRawResource(R.raw.localidades2);
                    BufferedReader reader = new BufferedReader(
                            new InputStreamReader(is, Charset.forName(UTF_8))
                    );
                    String line;
                    while ((line = reader.readLine()) != null) {
                        Log.e("LOCALIDADES", "LEYENDO ARCHIVO");
                        String[] localidad = line.split(",");

                        /**Registra las localidades*/
                        HashMap<Integer, String> values = new HashMap<>();
                        values.put(0, localidad[0].trim());
                        values.put(1, localidad[2].trim().toUpperCase());
                        values.put(2, localidad[1].trim());
                        dBhelper.saveLocalidades(db, values);
                    }
                } catch (IOException e) {
                    Log.e("LOCALIDADES", e.getMessage());
                }
            }

            return "Localidades";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            localidad = true;

            /**cuando se registren todas las localidades, municipios y colonias
             se validara en la clase MainActivity si requiere login o ya no*/
            //if (localidad && municipio && colonia){
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
            //}
        }
    }

    /**Registra las municipios de los estados de Veracruz, Puebla, Tlaxcala*/
    public class RegistrarMunicipios extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {
            Cursor row = dBhelper.getRecords(TABLE_MUNICIPIOS, "", "", null);
            if (row.getCount() == 0) {
                try {
                    /**Lee el archivo que se encuentra en la carpeta res/raw/municipios.csv*/
                    InputStream is = getResources().openRawResource(R.raw.municipios);
                    BufferedReader reader = new BufferedReader(
                            new InputStreamReader(is, Charset.forName(UTF_8))
                    );
                    String line;
                    while ((line = reader.readLine()) != null) {
                        String[] municipio = line.split(",");
                        Log.e("MUNICIPIOS", "LEYENDO ARCHIVO");

                        /**Registra los municipios*/
                        HashMap<Integer, String> values = new HashMap<>();
                        values.put(0, municipio[0]);
                        values.put(1, municipio[2]);
                        values.put(2, municipio[1]);
                        dBhelper.saveMunicipios(db, values);
                    }
                } catch (IOException e) {

                }
            }

            row.close();

            return "Municipios";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            municipio = true;

            /**cuando se registren todas las localidades, municipios y colonias
             se validara en la clase MainActivity si requiere login o ya no*/
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

    /**Registra las colonias de los estados de Veracruz, Puebla, Tlaxcala*/
    public class RegistrarColonias extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {
            Cursor row = dBhelper.getRecords(TABLE_COLONIAS, "", "", null);
            if (row.getCount() == 0) {
                try {
                    /**Lee el archivo que se encuentra en la carpeta res/raw/colonias.csv*/
                    InputStream is = getResources().openRawResource(R.raw.colonias);
                    BufferedReader reader = new BufferedReader(
                            new InputStreamReader(is, Charset.forName(UTF_8))
                    );
                    String line;
                    int i = 0;
                    while ((line = reader.readLine()) != null) {
                        String[] colonia = line.split(";");
                        Log.e("COLONIAS", "LEYENDO ARCHIVO");
                        /**Se registra las colonias*/
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

            row.close();

            return "Colonias";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            colonia =  true;

            /**cuando se registren todas las localidades, municipios y colonias
             se validara en la clase MainActivity si requiere login o ya no*/
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

    public class RegistrarCampana extends AsyncTask<Void, Void, String>{
        @Override
        protected String doInBackground(Void... voids){
            Cursor row = dBhelper.getRecords(TBL_CATALOGOS_CAMPANAS, "","",null);
            if(row.getCount() == 0){
                try {

                    InputStream is = getResources().openRawResource(R.raw.campana);
                    BufferedReader reader = new BufferedReader(
                            new InputStreamReader(is, Charset.forName(UTF_8))
                    );

                    String line;
                    int i = 0;
                    while ((line = reader.readLine()) != null) {
                        String[] campanas = line.split(",");

                        HashMap<Integer, String> values = new HashMap<>();
                        values.put(0, campanas[0]);
                        values.put(1, campanas[1]);
                        values.put(2, campanas[2]);
                        dBhelper.saveCatalogosCampanas(db, values);
                    }
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
            row.close();
            return "Campañas";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            campana =  true;

            if (localidad && municipio && campana){
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

    public class RegistrarEntregaCartera extends AsyncTask<Void, Void, String>{

        @Override
        protected String doInBackground(Void... voids){
            Cursor row = dBhelper.getRecords(TBL_DATOS_ENTREGA_CARTERA, "", "", null);
            if(row.getCount() == 0){
                try{
                    InputStream is = getResources().openRawResource(R.raw.entregacartera);
                    BufferedReader reader = new BufferedReader(
                            new InputStreamReader(is, Charset.forName(UTF_8))
                    );

                    String line;
                    while ((line = reader.readLine()) != null){
                        String entCartera[] = line.split(",");

                        HashMap<Integer,String> values = new HashMap<>();
                        values.put(0,entCartera[0]);
                        values.put(1,entCartera[1]);
                        values.put(2,entCartera[2]);
                        dBhelper.saveCatalogoEntregaCartera(db, values);
                    }

                }catch (IOException e){
                    e.printStackTrace();
                }
            }
            row.close();
            return "entCartera";
        }

        @Override
        protected void onPostExecute(String s){
            super.onPostExecute(s);
            if(localidad && municipio && colonia && campana){
                Handler handler_home = new Handler();
                handler_home.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent start = new Intent(ctx, MainActivity.class);
                        start.putExtra("login",false);
                        startActivity(start);
                        finish();
                    }
                },3000);
            }

        }



    }


}
