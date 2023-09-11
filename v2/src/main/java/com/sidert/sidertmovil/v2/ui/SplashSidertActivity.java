package com.sidert.sidertmovil.v2.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.sidert.sidertmovil.v2.R;
import com.sidert.sidertmovil.v2.databinding.ActivitySplashSidertBinding;
import com.sidert.sidertmovil.v2.viewmodels.SplashSidertViewModel;

import androidx.annotation.Nullable;

public class SplashSidertActivity
        extends MVVMBaseActivity<ActivitySplashSidertBinding, SplashSidertViewModel> {

    @Override
    @SuppressLint("HardwareIds")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setDatabinding() {
        this.databinding = ActivitySplashSidertBinding.inflate(getLayoutInflater());
    }

    @Override
    public void setViewmodelClass() {
        this.viewmodelClass = SplashSidertViewModel.class;
    }

    @Override
    public void bindViewAndViewmodel() {
        this.databinding.setViewmodel(this.viewmodel);
    }

    @Override
    public void onBuildView(@Nullable Bundle savedInstanceState) {
        super.onBuildView(savedInstanceState);
        this.viewmodel.saveMacInSession();
        this.viewmodel.removeUserIfSessionHasMoreThanOneDay(this);
    }

    @Override
    public void bind() {
        // No implemented.
    }

}
