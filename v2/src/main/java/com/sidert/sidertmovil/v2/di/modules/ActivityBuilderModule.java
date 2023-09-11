package com.sidert.sidertmovil.v2.di.modules;


import com.sidert.sidertmovil.v2.ui.DescargaDatosActivity;
import com.sidert.sidertmovil.v2.ui.LoginActivity;
import com.sidert.sidertmovil.v2.ui.MainV2Activity;
import com.sidert.sidertmovil.v2.ui.SplashSidertActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public interface ActivityBuilderModule {

    @ContributesAndroidInjector
    DescargaDatosActivity contributeDescargaDatos();

    @ContributesAndroidInjector
    SplashSidertActivity splashSidertActivity();

    @ContributesAndroidInjector
    LoginActivity loginActivity();

    @ContributesAndroidInjector
    MainV2Activity mainActivity();

}
