package com.sidert.sidertmovil.v2.repositories;

import android.content.Context;

import com.sidert.sidertmovil.models.apoyogastosfunerarios.ApoyoGastosFunerarios;
import com.sidert.sidertmovil.models.apoyogastosfunerarios.ApoyoGastosFunerariosDao;
import com.sidert.sidertmovil.models.apoyogastosfunerarios.Prestamo;
import com.sidert.sidertmovil.models.apoyogastosfunerarios.PrestamoDao;
import com.sidert.sidertmovil.v2.domain.daos.*;
import com.sidert.sidertmovil.utils.SessionManager;
import com.sidert.sidertmovil.v2.domain.entities.Prestamos;
import com.sidert.sidertmovil.v2.domain.entities.Recibos;
import com.sidert.sidertmovil.v2.remote.datasource.PrestamoRemoteDatasource;
import com.sidert.sidertmovil.v2.remote.dtos.PrestamoResponse;
import com.sidert.sidertmovil.v2.repositories.mappers.PrestamoMapper;
import com.sidert.sidertmovil.v2.utils.ExecutorUtil;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Call;
import retrofit2.Response;
import timber.log.Timber;

@Singleton
public final class PrestamoRepository extends BaseRepository {

    private final PrestamoRemoteDatasource prestamoRemoteDatasource;
    private final PrestamosDao prestamosDao;
    private final PrestamoMapper prestamoMapper;
    private final RecibosDao recibosDao;

    @Inject
    public PrestamoRepository(ExecutorUtil executorUtil, SessionManager sessionManager, PrestamoRemoteDatasource prestamoRemoteDatasource, PrestamosDao prestamosDao, RecibosDao recibosDao, PrestamoMapper prestamoMapper) {
        super(executorUtil, sessionManager);
        this.prestamoRemoteDatasource = prestamoRemoteDatasource;
        this.prestamosDao = prestamosDao;
        this.recibosDao = recibosDao;
        this.prestamoMapper = prestamoMapper;
    }


    public void getPrestamos() {
        Call<List<PrestamoResponse>> call = prestamoRemoteDatasource.show();
        try {
            Response<List<PrestamoResponse>> response = executorUtil.process(call);
            if (response.code() == 200) {
                List<PrestamoResponse> prestamos = response.body();

                if (prestamos != null && prestamos.size() > 0) {
                    for (PrestamoResponse item : prestamos) {
                        Optional<Prestamos> prestamoOptional;
                        Optional<Recibos> recibo;

                        if (item.getGrupoId() > 1) {
                            prestamoOptional = executorUtil.runTaskInThread(() -> prestamosDao.findByGrupoIdAndNumSolicitud(item.getGrupoId(), item.getNumSolicitud()));
                        } else {
                            prestamoOptional = executorUtil.runTaskInThread(() -> prestamosDao.findByClienteIdAndNumSolicitud(item.getClienteId(), item.getNumSolicitud()));
                        }

                        if (prestamoOptional.isPresent()) {
                            Prestamos prestamo = prestamoOptional.get();
                            if (item.getGrupoId() > 1) {
                                recibo = executorUtil.runTaskInThread(() -> recibosDao.findByGrupoIdAndNumSolicitud(item.getGrupoId(), item.getNumSolicitud()));
                            } else {
                                recibo = executorUtil.runTaskInThread(() -> recibosDao.findByClienteIdAndNumSolicitud(item.getClienteId(), item.getNumSolicitud()));
                            }

                            if (recibo.isPresent()) {
                                executorUtil.runTaskInThread(() -> {
                                    prestamoMapper.updateLocalWithRemote(item, prestamo);
                                    prestamosDao.update(prestamo);
                                });
                            }
                        } else {
                            executorUtil.runTaskInThread(() -> prestamosDao.insert(prestamoMapper.remoteToLocal(item)));

                        }
                    }
                }
            } else {
                Timber.tag("ERROR AQUI PREST AUT").e(response.message());
            }
        } catch (Exception ex) {
            Timber.tag("ErrorAgf").e("Fail AGG%s", ex.getMessage());
            ex.printStackTrace();
        }
    }
}
