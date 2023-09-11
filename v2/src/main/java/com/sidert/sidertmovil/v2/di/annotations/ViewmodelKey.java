package com.sidert.sidertmovil.v2.di.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import androidx.lifecycle.ViewModel;
import dagger.MapKey;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@MapKey
public @interface ViewmodelKey {

    Class<? extends ViewModel> value();

}
