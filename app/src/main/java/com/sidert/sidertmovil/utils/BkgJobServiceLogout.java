package com.sidert.sidertmovil.utils;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

public class BkgJobServiceLogout extends JobService {
    @Override
    public boolean onStartJob(final JobParameters params) {
        Log.e(this.getClass().getSimpleName(),"onStartJob");
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
                        session.getUser().get(7));
                jobFinished(params, false);
            }
        });

        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }
}
