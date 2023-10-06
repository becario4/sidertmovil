package com.sidert.sidertmovil.v2.di;

import com.sidert.sidertmovil.v2.SidertMovilApplication;
import com.sidert.sidertmovil.v2.di.modules.ActivityBuilderModule;
import com.sidert.sidertmovil.v2.di.modules.AppModule;
import com.sidert.sidertmovil.v2.di.modules.BluetoothModule;
import com.sidert.sidertmovil.v2.di.modules.DatabaseModule;
import com.sidert.sidertmovil.v2.di.modules.MapperModule;
import com.sidert.sidertmovil.v2.di.modules.NetworkModule;
import com.sidert.sidertmovil.v2.di.modules.PrinterModule;
import com.sidert.sidertmovil.v2.di.modules.ViewModelsModule;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {
        AndroidSupportInjectionModule.class,
        ActivityBuilderModule.class,
        AppModule.class,
        BluetoothModule.class,
        DatabaseModule.class,
        MapperModule.class,
        NetworkModule.class,
        PrinterModule.class,
        ViewModelsModule.class
})
public interface ApplicationComponent extends AndroidInjector<SidertMovilApplication> {

    @Component.Factory
    interface Factory {
        ApplicationComponent create(
                @BindsInstance SidertMovilApplication application,
                @BindsInstance @Named("baseUrl") String baseUrl,
                @BindsInstance @Named("androidRestAuth") String androidREstAuth,
                @BindsInstance @Named("defaultAsesor") String defaultAsesor,
                @BindsInstance @Named("androidApplicationId") String androidApplicationId

        );
    }

    @Named("baseUrl")
    String getBaseUrl();


}

