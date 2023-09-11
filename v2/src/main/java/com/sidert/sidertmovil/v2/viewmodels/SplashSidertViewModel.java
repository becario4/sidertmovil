package com.sidert.sidertmovil.v2.viewmodels;

import android.annotation.SuppressLint;

import com.sidert.sidertmovil.v2.bussinesmodel.SplashSidertBussinesModel;

import javax.inject.Inject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;

public class SplashSidertViewModel
        extends ViewModel {

    private static final String TAG = SplashSidertViewModel.class.getSimpleName();
    private final SplashSidertBussinesModel splashSidertBussinesModel;

    @Inject
    public SplashSidertViewModel(SplashSidertBussinesModel splashSidertBussinesModel) {
        this.splashSidertBussinesModel = splashSidertBussinesModel;
    }

    @SuppressLint("HardwareIds")
    public void saveMacInSession() {
        splashSidertBussinesModel.saveMacInSession();
    }

    public void removeUserIfSessionHasMoreThanOneDay(AppCompatActivity activity) {
        splashSidertBussinesModel.removeUserIfSessionHasMoreThanOneDay(activity);
    }
}