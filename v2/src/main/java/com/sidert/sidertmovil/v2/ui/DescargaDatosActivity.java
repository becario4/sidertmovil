package com.sidert.sidertmovil.v2.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;

import com.sidert.sidertmovil.Home;
import com.sidert.sidertmovil.v2.databinding.ActivityDescargaDatosBinding;
import com.sidert.sidertmovil.v2.viewmodels.DescargaDatosViewModel;
import com.sidert.sidertmovil.v2.viewmodels.ViewModelFactory;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import dagger.android.support.DaggerAppCompatActivity;
import timber.log.Timber;

public class DescargaDatosActivity extends MVVMBaseActivity<ActivityDescargaDatosBinding, DescargaDatosViewModel> {

    private static final String TAG = DescargaDatosActivity.class.getName();

    @Override
    public void setDatabinding() {
        this.databinding = ActivityDescargaDatosBinding.inflate(this.getLayoutInflater());
    }

    @Override
    public void setViewmodelClass() {
        this.viewmodelClass = DescargaDatosViewModel.class;
    }

    @Override
    public void bindViewAndViewmodel() {
        this.databinding.setViewmodel(viewmodel);
    }

    @Override
    public void onBuildView(@Nullable Bundle savedInstanceState) {
        super.onBuildView(savedInstanceState);

        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(() -> {
            this.viewmodel.getStatusLoading().setValue("Iniciando descarga de datos");
            this.viewmodel.initSync();
            this.viewmodel.getDownloadProcess().observe(this, flag -> {
                if (flag) {
                    Timber.tag(TAG).i("Corrí sin detenerme a esperar a los demas.");
                    Intent home = new Intent(this, Home.class);
                    home.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    home.putExtra("login", false);
                    startActivity(home);
                }
            });
        }, 333);
    }

    @Override
    public void bind() {
        // Not implemented yet!
    }


}
