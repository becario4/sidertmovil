package com.sidert.sidertmovil.v2.ui;

import android.os.Bundle;
import android.view.View;

import com.sidert.sidertmovil.v2.viewmodels.ViewModelFactory;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import dagger.android.support.DaggerAppCompatActivity;

public abstract class MVVMBaseActivity<V0 extends ViewDataBinding, M0 extends ViewModel>
        extends DaggerAppCompatActivity {

    private ViewModelFactory viewModelFactory;
    protected ViewModelProvider viewModelProvider;
    protected Class<M0> viewmodelClass;
    protected M0 viewmodel;
    protected V0 databinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setDatabinding();
        setViewmodelClass();
        provideViewModel();
        bindViewAndViewmodel();
        onBuildView(savedInstanceState);
        bind();
    }

    public abstract void setDatabinding();

    public abstract void setViewmodelClass();

    public abstract void bindViewAndViewmodel();

    private void provideViewModel() {
        this.viewModelProvider = new ViewModelProvider(this, viewModelFactory);
        this.viewmodel = this.viewModelProvider.get(viewmodelClass);
    }

    public void onBuildView(@Nullable Bundle savedInstanceState) {
        databinding.setLifecycleOwner(this);
        View root = databinding.getRoot();
        setContentView(root);
    }

    public abstract void bind();

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Inject
    public void setViewModelFactory(ViewModelFactory viewModelFactory) {
        this.viewModelFactory = viewModelFactory;
    }
}
