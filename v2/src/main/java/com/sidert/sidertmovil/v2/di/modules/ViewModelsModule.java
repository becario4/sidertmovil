package com.sidert.sidertmovil.v2.di.modules;

import com.sidert.sidertmovil.v2.di.annotations.ViewmodelKey;
import com.sidert.sidertmovil.v2.viewmodels.DescargaDatosViewModel;
import com.sidert.sidertmovil.v2.viewmodels.LoginViewModel;
import com.sidert.sidertmovil.v2.viewmodels.SplashSidertViewModel;

import androidx.lifecycle.ViewModel;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ViewModelsModule {

    @Binds
    @IntoMap
    @ViewmodelKey(DescargaDatosViewModel.class)
    abstract ViewModel bindDescargaDatosViewModel(DescargaDatosViewModel descargaDatosViewModel);


    @Binds
    @IntoMap
    @ViewmodelKey(SplashSidertViewModel.class)
    abstract ViewModel bindSplashSidertViewModel(SplashSidertViewModel splashSidertViewModel);

    @Binds
    @IntoMap
    @ViewmodelKey(LoginViewModel.class)
    abstract ViewModel bindLoginViewModel(LoginViewModel loginViewModel);


}
