package com.sidert.sidertmovil.utils;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.util.Log;

import com.sidert.sidertmovil.database.DBhelper;
import com.sidert.sidertmovil.database.SidertTables;

import java.util.HashMap;

public class BkgJobServiceSincronizado extends JobService {
    @Override
    public boolean onStartJob(final JobParameters params) {
        Log.e(this.getClass().getSimpleName(),"onStartJobSincronizado");
        final SessionManager session = new SessionManager(getApplicationContext());
        DBhelper dBhelper = new DBhelper(getApplicationContext());
        SQLiteDatabase db = dBhelper.getWritableDatabase();


        if (session.getUser().get(6).equals("true")){
            HashMap<Integer, String> params_sincro = new HashMap<>();
            params_sincro.put(0, session.getUser().get(0));
            params_sincro.put(1, Miscellaneous.ObtenerFecha("timestamp"));

            if (Constants.ENVIROMENT)
                dBhelper.saveSincronizado(db, Constants.SINCRONIZADO, params_sincro);
            else
                dBhelper.saveSincronizado(db, Constants.SINCRONIZADO_T, params_sincro);
        }


        Handler mHandler = new Handler(getMainLooper());
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                Log.e("Sincronizado", "Service Job Sincronizado Bkg");
                Log.e("Timestamp Sincronizado", Miscellaneous.ObtenerFecha("timestamp"));
                //Toast.makeText(getApplicationContext(),"cerrar sesion job services", Toast.LENGTH_SHORT).show();

                if (session.getUser().get(6).equals("true")){
                    Servicios_Sincronizado servicios = new Servicios_Sincronizado();
                    if (NetworkStatus.haveNetworkConnection(getApplicationContext())) {
                        Log.e("JOB", "Con conexion a internet Geolocalizacion");
                        servicios.GetGeolocalizacion(getApplicationContext(), false, false);
                        servicios.SaveGeolocalizacion(getApplicationContext(), false);
                        servicios.SaveRespuestaGestion(getApplicationContext(), false);
                        servicios.SendImpresionesVi(getApplicationContext(), false);
                        servicios.SendReimpresionesVi(getApplicationContext(), false);
                    }
                    else
                        Log.e("JOB", "Sin conexion a internet Geolocalizacion");
                }
                jobFinished(params, true);
            }
        });

        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }
}
