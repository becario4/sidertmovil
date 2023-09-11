package com.sidert.sidertmovil.v2.di.modules;


import com.sidert.sidertmovil.v2.repositories.mappers.*;

import org.mapstruct.factory.Mappers;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public interface MapperModule {

    @Provides
    @Singleton
    static CatalogoMapper provideCatalogoMapper() {
        return Mappers.getMapper(CatalogoMapper.class);
    }

    @Provides
    @Singleton
    static PrestamoMapper providePrestamoMapper() {
        return Mappers.getMapper(PrestamoMapper.class);
    }

    @Provides
    @Singleton
    static PrestamoToRenovarMapper providePrestamoToRenovarMapper() {
        return Mappers.getMapper(PrestamoToRenovarMapper.class);
    }

    @Provides
    @Singleton
    static ReciboAgfCcMapper provideReciboAgfCcMapper() {
        return Mappers.getMapper(ReciboAgfCcMapper.class);
    }

    @Provides
    @Singleton
    static RecuperacionRecibosCcMappper provideRecuperacionRecibosCcMappper() {
        return Mappers.getMapper(RecuperacionRecibosCcMappper.class);
    }


}
