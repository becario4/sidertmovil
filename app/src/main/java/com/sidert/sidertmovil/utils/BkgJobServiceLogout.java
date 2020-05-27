package com.sidert.sidertmovil.utils;

import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import java.util.Calendar;

public class BkgJobServiceLogout extends JobService {
    @Override
    public boolean onStartJob(final JobParameters params) {
        Log.e(this.getClass().getSimpleName(),"onStartJob");
        Calendar c = Calendar.getInstance();

        int hour_day = c.get(Calendar.HOUR_OF_DAY);
        if (hour_day > 21) {
            Log.e("Logout", "Despues de la 10 cierra sesion");
            Handler mHandler = new Handler(getMainLooper());
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    Log.e("Logout", "Service Job");
                    //Toast.makeText(getApplicationContext(),"cerrar sesion job services", Toast.LENGTH_SHORT).show();
                    SessionManager session = new SessionManager(getApplicationContext());
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
                    jobFinished(params, false);
                }
            });
        }
        else {
            Log.e("Logout", "Antes de laa 10  no cierra sesion");
        }

        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }
}
