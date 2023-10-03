package com.sidert.sidertmovil.v2.di.modules;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;

import com.sewoo.port.android.BluetoothPort;
import com.sidert.sidertmovil.v2.SidertMovilApplication;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import timber.log.Timber;

@Module
public interface BluetoothModule {

    String TAG = BluetoothModule.class.getName();

    @Provides
    @Singleton
    static BluetoothManager provideBluetoothManager(SidertMovilApplication sidertMovilApplication) {
        Timber.tag(TAG).i("Iniciando configuracion de bluetooth");
        return (BluetoothManager) sidertMovilApplication.getSystemService(Context.BLUETOOTH_SERVICE);
    }

    @Provides
    @Singleton
    static BluetoothAdapter provideBluetoothAdapter(BluetoothManager bluetoothManager) {
        return bluetoothManager.getAdapter();
    }

    @Provides
    @Singleton
    static BluetoothPort provideBluetoothPort() {
        BluetoothPort bluetoothPort = BluetoothPort.getInstance();
//        bluetoothPort.SetMacFilter(false);
        return bluetoothPort;
    }

}
