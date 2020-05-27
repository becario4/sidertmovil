package com.sidert.sidertmovil.utils;

import android.content.Context;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.util.Log;

import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import static android.os.Looper.getMainLooper;

public class WorkerLogout extends Worker {

    public WorkerLogout(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
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

                WorkManager workManager = WorkManager.getInstance();

                workManager.cancelAllWorkByTag(Constants.SINCRONIZADO);
            }
        });
        return Result.success();
    }
}
