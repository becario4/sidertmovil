package com.sidert.sidertmovil.v2.repositories;

import com.sidert.sidertmovil.utils.SessionManager;
import com.sidert.sidertmovil.v2.domain.daos.RecibosAgfCcDao;
import com.sidert.sidertmovil.v2.domain.entities.RecibosAgfCc;
import com.sidert.sidertmovil.v2.remote.datasource.ReciboAgfCcRemoteDatasource;
import com.sidert.sidertmovil.v2.remote.dtos.ReciboResponse;
import com.sidert.sidertmovil.v2.repositories.mappers.ReciboAgfCcMapper;
import com.sidert.sidertmovil.v2.utils.ExecutorUtil;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Call;
import retrofit2.Response;
import timber.log.Timber;

@Singleton
public final class ReciboAgfCcRepository extends BaseRepository {

    private static final String TAG = ReciboAgfCcRepository.class.getName();

    private final ReciboAgfCcRemoteDatasource reciboAgfCcRemoteDatasource;
    private final ReciboAgfCcMapper reciboAgfCcMapper;
    private final RecibosAgfCcDao recibosAgfCcDao;
    private final Timber.Tree klassTag;

    @Inject
    public ReciboAgfCcRepository(ExecutorUtil executorUtil, SessionManager sessionManager, ReciboAgfCcRemoteDatasource reciboAgfCcRemoteDatasource, ReciboAgfCcMapper reciboAgfCcMapper, RecibosAgfCcDao recibosAgfCcDao) {
        super(executorUtil, sessionManager);
        this.reciboAgfCcRemoteDatasource = reciboAgfCcRemoteDatasource;
        this.reciboAgfCcMapper = reciboAgfCcMapper;
        this.recibosAgfCcDao = recibosAgfCcDao;

        klassTag = Timber.tag(TAG);
    }

    public void getUltimosRecibos() {
        Call<List<ReciboResponse>> call = reciboAgfCcRemoteDatasource.last(sessionManager.getUser().get(9));
        try {
            Response<List<ReciboResponse>> response = executorUtil.process(call);
            if (response.code() == 200) {
                List<ReciboResponse> data = response.body();
                if (data != null) {
                    for (ReciboResponse item : data) {
                        Optional<RecibosAgfCc> optionalRecibosAgfCc;

                        if (item.getFolio() != null) {
                            String[] folio = item.getFolio().split("-");
                            item.setFolio(folio[2]);
                        }

                        if (item.getFechaImpresion() != null) {
                            item.setFechaImpresion(item.getFechaImpresion().substring(0, 19).replace("T", " "));
                        }

                        if (item.getFechaEnvio() != null) {
                            item.setFechaEnvio(item.getFechaEnvio().substring(0, 19).replace("T", " "));
                        }

                        item.setEstatus(1);

                        if (item.getGrupoId().equals("1")) {
                            optionalRecibosAgfCc = executorUtil.runTaskInThread(() -> recibosAgfCcDao.findByGrupoIdAndNumSolicitudAndTipoImpresion(item.getGrupoId(), item.getNumSolicitud(), item.getTipoImpresion()));
                        } else {
                            optionalRecibosAgfCc = executorUtil.runTaskInThread(() -> recibosAgfCcDao.findByNombreAndNumSolicitudAndTipoImpresion(item.getNombre(), item.getNumSolicitud(), item.getTipoImpresion()));
                        }

                        if (optionalRecibosAgfCc.isPresent()) {
                            executorUtil.runTaskInThread(() -> {
                                RecibosAgfCc reciboAgf = optionalRecibosAgfCc.get();
                                reciboAgfCcMapper.updateLocalWithRemote(item, reciboAgf);
                                recibosAgfCcDao.update(reciboAgf);
                            });
                        } else {
                            executorUtil.runTaskInThread(() -> {
                                RecibosAgfCc recibosAgfCc = this.reciboAgfCcMapper.remoteToLocal(item);
                                recibosAgfCcDao.insert(recibosAgfCc);
                            });
                        }
                    }
                }
            } else {
                klassTag.e(response.message());
            }
        } catch (Exception ex) {
            klassTag.e(ex);
        }
    }


}
