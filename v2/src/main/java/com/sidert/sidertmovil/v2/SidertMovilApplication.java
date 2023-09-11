package com.sidert.sidertmovil.v2;

import android.provider.Settings;

import com.google.firebase.FirebaseApp;
import com.sidert.sidertmovil.utils.Miscellaneous;
import com.sidert.sidertmovil.v2.di.ApplicationComponent;
import com.sidert.sidertmovil.v2.di.DaggerApplicationComponent;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;
import timber.log.Timber;
import timber.log.Timber.DebugTree;

public class SidertMovilApplication extends DaggerApplication {

    @Override
    protected AndroidInjector<SidertMovilApplication> applicationInjector() {
        FirebaseApp.initializeApp(this);
        Timber.plant(new DebugTree());
        String url = this.getString(R.string.api_url);
        String userName = this.getString(R.string.api_user);
        String password = this.getString(R.string.api_password);
        String defaultAsesor = this.getString(R.string.default_asesor);
        String androidAppId = this.getString(R.string.default_mac);

        androidAppId = androidAppId.trim();

        if (androidAppId.isEmpty()) {
            androidAppId = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID).toUpperCase();
        }

        String auth = Miscellaneous.authorization(userName, password);

        ApplicationComponent.Factory appFactory = DaggerApplicationComponent.factory();
        return appFactory.create(this, url, auth, defaultAsesor, androidAppId);
    }

}
