package com.sidert.sidertmovil.v2.di.modules;

import com.sewoo.jpos.printer.ESCPOSPrinter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public interface PrinterModule {

    @Provides
    @Singleton
    static ESCPOSPrinter provideESCPOSPrinter() {
        return new ESCPOSPrinter();
    }


}
