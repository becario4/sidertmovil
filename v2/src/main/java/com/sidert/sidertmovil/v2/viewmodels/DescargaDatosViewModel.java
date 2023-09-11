package com.sidert.sidertmovil.v2.viewmodels;


import android.view.View;

import com.sidert.sidertmovil.v2.bussinesmodel.DescargaDatosBussinesModel;

import javax.inject.Inject;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DescargaDatosViewModel
        extends ViewModel {

    private final MutableLiveData<Integer> totalCartera = new MutableLiveData<>(0);
    private final MutableLiveData<Integer> avanceCartera = new MutableLiveData<>(0);
    private final MutableLiveData<String> statusLoading = new MutableLiveData<>("Descargando información, esto puede demorar varios minutos favor de no interrumpir la descargar");
    private final MutableLiveData<Boolean> recibosPrestamosProcess = new MutableLiveData<>(false);
    private final MutableLiveData<Boolean> ultimosRecibosProcess = new MutableLiveData<>(false);
    private final MutableLiveData<Boolean> ultimosRecibosCcProcess = new MutableLiveData<>(false);
    private final MutableLiveData<Boolean> solicitudesRechazadasIndProcess = new MutableLiveData<>(false);
    private final MutableLiveData<Boolean> solicitudesRechazadasGpoProcess = new MutableLiveData<>(false);
    private final MutableLiveData<Boolean> prestamosAutorizadosProcess = new MutableLiveData<>(false);
    private final MutableLiveData<Boolean> gestionesVerDomProcess = new MutableLiveData<>(false);
    private final MutableLiveData<Boolean> ultimasImpresionesProcess = new MutableLiveData<>(false);
    private final MutableLiveData<Boolean> carteraProcess = new MutableLiveData<>(false);
    private final MutableLiveData<Boolean> prestamosProcess = new MutableLiveData<>(false);
    private final MutableLiveData<Boolean> prestamosToRenovarProcess = new MutableLiveData<>(false);
    private final MutableLiveData<Boolean> downloadProcess = new MutableLiveData<>(false);
    private final DescargaDatosBussinesModel descargaDatosBussinesModel;

    @Inject
    public DescargaDatosViewModel(DescargaDatosBussinesModel descargaDatosBussinesModel) {
        this.descargaDatosBussinesModel = descargaDatosBussinesModel;
    }

    public void initSync() {
        Runnable runner = () -> this.descargaDatosBussinesModel.startSync(
                () -> downloadProcess.postValue(true),
                (String msg, int checkToRelease) -> {
                    statusLoading.postValue(msg);
                    switch (checkToRelease) {
                        case 0:
                            recibosPrestamosProcess.postValue(true);
                            break;
                        case 1:
                            ultimosRecibosProcess.postValue(true);
                            break;
                        case 2:
                            ultimosRecibosCcProcess.postValue(true);
                            break;
                        case 3:
                            solicitudesRechazadasIndProcess.postValue(true);
                            break;
                        case 4:
                            solicitudesRechazadasGpoProcess.postValue(true);
                            break;
                        case 5:
                            prestamosAutorizadosProcess.postValue(true);
                            break;
                        case 6:
                            gestionesVerDomProcess.postValue(true);
                            break;
                        case 7:
                            ultimasImpresionesProcess.postValue(true);
                            break;
                        case 8:
                            carteraProcess.postValue(true);
                            break;
                        case 9:
                            prestamosProcess.postValue(true);
                            break;
                        case 10:
                            prestamosToRenovarProcess.postValue(true);
                            break;
                    }
                },
                (increment, total) -> {
                    avanceCartera.postValue(increment);
                    totalCartera.postValue(total);
                });
        Thread thread = new Thread(runner);
        thread.start();
    }

    public MutableLiveData<Integer> getTotalCartera() {
        return totalCartera;
    }

    public MutableLiveData<Integer> getAvanceCartera() {
        return avanceCartera;
    }

    public MutableLiveData<String> getStatusLoading() {
        return statusLoading;
    }

    public MutableLiveData<Boolean> getRecibosPrestamosProcess() {
        return recibosPrestamosProcess;
    }

    public MutableLiveData<Boolean> getUltimosRecibosProcess() {
        return ultimosRecibosProcess;
    }

    public MutableLiveData<Boolean> getUltimosRecibosCcProcess() {
        return ultimosRecibosCcProcess;
    }

    public MutableLiveData<Boolean> getSolicitudesRechazadasIndProcess() {
        return solicitudesRechazadasIndProcess;
    }

    public MutableLiveData<Boolean> getSolicitudesRechazadasGpoProcess() {
        return solicitudesRechazadasGpoProcess;
    }

    public MutableLiveData<Boolean> getPrestamosAutorizadosProcess() {
        return prestamosAutorizadosProcess;
    }

    public MutableLiveData<Boolean> getGestionesVerDomProcess() {
        return gestionesVerDomProcess;
    }

    public MutableLiveData<Boolean> getUltimasImpresionesProcess() {
        return ultimasImpresionesProcess;
    }

    public MutableLiveData<Boolean> getCarteraProcess() {
        return carteraProcess;
    }

    public MutableLiveData<Boolean> getPrestamosProcess() {
        return prestamosProcess;
    }

    public MutableLiveData<Boolean> getPrestamosToRenovarProcess() {
        return prestamosToRenovarProcess;
    }

    public MutableLiveData<Boolean> getDownloadProcess() {
        return downloadProcess;
    }

    public DescargaDatosBussinesModel getDescargaDatosBussinesModel() {
        return descargaDatosBussinesModel;
    }
}
