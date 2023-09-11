package com.sidert.sidertmovil.v2.di.modules;

import android.content.Context;

import com.sidert.sidertmovil.utils.SessionManager;
import com.sidert.sidertmovil.v2.SidertMovilApplication;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public interface AppModule {

    @Provides
    @Singleton
    static ExecutorService executorService() {
        return Executors.newFixedThreadPool(3);
    }

    @Provides
    @Singleton
    static SessionManager sessionManager(SidertMovilApplication sidertMovilApplication) {
        Context context = sidertMovilApplication.getApplicationContext();
        return SessionManager.getInstance(context);
    }

    @Provides
    @Singleton
    @Named("asesorId")
    static String asesorId(SessionManager sessionManager) {
        return sessionManager.getUser().get(0);
    }


}
