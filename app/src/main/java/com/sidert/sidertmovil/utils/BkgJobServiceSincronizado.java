package com.sidert.sidertmovil.utils;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.Handler;
import android.util.Log;

public class BkgJobServiceSincronizado extends JobService {
    @Override
    public boolean onStartJob(final JobParameters params) {
        Log.e(this.getClass().getSimpleName(),"onStartJobSincronizado");
        Handler mHandler = new Handler(getMainLooper());
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                Log.e("Sincronizado", "Service Job Sincronizado");
                //Toast.makeText(getApplicationContext(),"cerrar sesion job services", Toast.LENGTH_SHORT).show();
                SessionManager session = new SessionManager(getApplicationContext());
                if (session.getUser().get(6).equals("true")){
                    if (session.getUser().get(5).equals("ROLE_GERENTESUCURSAL")){
                        Servicios_Sincronizado servicios = new Servicios_Sincronizado();
                        if (NetworkStatus.haveWifi(getApplicationContext())) {
                            Log.e("JOB", "Con conexion a internet Geolocalizacion");
                            servicios.GetGeolocalizacion(getApplicationContext());
                            servicios.SaveGeolocalizacion(getApplicationContext());
                        }
                        else
                            Log.e("JOB", "Sin conexion a internet Geolocalizacion");
                    }
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
