package com.sidert.sidertmovil.v2.viewmodels;

import org.jetbrains.annotations.NotNull;

import java.util.Map;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

@Singleton
public class ViewModelFactory implements ViewModelProvider.Factory {

    private final Map<Class<? extends ViewModel>, Provider<ViewModel>> viewModels;

    @Inject
    public ViewModelFactory(Map<Class<? extends ViewModel>, Provider<ViewModel>> viewModels) {
        this.viewModels = viewModels;
    }

    @NotNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NotNull Class<T> modelClass) {
        if (this.viewModels.containsKey(modelClass)) {
            Provider<ViewModel> viewmodel = this.viewModels.get(modelClass);
            if (viewmodel != null) {
                return (T) viewmodel.get();
            }
        }
        throw new UnsupportedOperationException();
    }

}
