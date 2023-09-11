package com.sidert.sidertmovil.v2.repositories;


import com.sidert.sidertmovil.models.circulocredito.CirculoCredito;
import com.sidert.sidertmovil.utils.SessionManager;
import com.sidert.sidertmovil.v2.domain.daos.RecibosCcDao;
import com.sidert.sidertmovil.v2.domain.daos.RecuperacionRecibosCcDao;
import com.sidert.sidertmovil.v2.domain.entities.RecibosCc;
import com.sidert.sidertmovil.v2.domain.entities.RecuperacionRecibosCc;
import com.sidert.sidertmovil.v2.remote.datasource.ReciboCcRemoteDatasource;
import com.sidert.sidertmovil.v2.repositories.mappers.RecuperacionRecibosCcMappper;
import com.sidert.sidertmovil.v2.utils.ExecutorUtil;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Call;
import retrofit2.Response;
import timber.log.Timber;

@Singleton
public final class RecibosCcRepository extends BaseRepository {

    private static final String TAG = RecibosCcRepository.class.getName();
    private final SessionManager sessionManager;
    private final RecibosCcDao reciboCCDao;
    private final RecuperacionRecibosCcDao gestionCCDao;
    private final ReciboCcRemoteDatasource reciboCcRemoteDatasource;
    private final RecuperacionRecibosCcMappper recuperacionRecibosCcMappper;

    @Inject
    public RecibosCcRepository(ExecutorUtil executorUtil,
                               SessionManager sessionManager,
                               RecibosCcDao reciboCCDao,
                               RecuperacionRecibosCcDao gestionCCDao,
                               ReciboCcRemoteDatasource reciboCcRemoteDatasource,
                               RecuperacionRecibosCcMappper recuperacionRecibosCcMappper
    ) {
        super(executorUtil, sessionManager);
        this.sessionManager = sessionManager;
        this.reciboCCDao = reciboCCDao;
        this.gestionCCDao = gestionCCDao;
        this.reciboCcRemoteDatasource = reciboCcRemoteDatasource;
        this.recuperacionRecibosCcMappper = recuperacionRecibosCcMappper;
    }

    public void getUltimosRecibosCc() {
        Call<List<CirculoCredito>> call = reciboCcRemoteDatasource.show(Long.parseLong(sessionManager.getUser().get(9)));
        try {
            Response<List<CirculoCredito>> response = executorUtil.process(call);
            if (response.code() == 200) {
                List<CirculoCredito> items = response.body();
                if (items == null) return;
                for (int index = 0; index < items.size(); index++) {
                    CirculoCredito cc = items.get(index);
                    Optional<RecuperacionRecibosCc> optionalRecuperacionRecibosCc = executorUtil.runTaskInThread(() -> gestionCCDao.findByCurp(cc.getCurp()));
                    if (!optionalRecuperacionRecibosCc.isPresent()) {
                        RecuperacionRecibosCc gestionCc = executorUtil.runTaskInThread(() -> recuperacionRecibosCcMappper.remoteToLocalRecuperacion(cc));
                        executorUtil.runTaskInThread(() -> gestionCCDao.insert(gestionCc));
                    }
                }
                CirculoCredito lastItem = items.get(items.size() - 1);
                String[] folio = lastItem.processFolio();
                Optional<RecibosCc> reciboCC = executorUtil.runTaskInThread(() -> reciboCCDao.findByCurpAndTipoImpresion(lastItem.getCurp(), lastItem.getTipoImpresion()));
                if (!reciboCC.isPresent() && folio != null) {
                    executorUtil.runTaskInThread(() -> {
                        RecibosCc lastReciboCC = recuperacionRecibosCcMappper.remoteToLocalRecuperacionLast(lastItem);
                        reciboCCDao.insert(lastReciboCC);
                    });
                }
            } else {
                Timber.tag(TAG).i("ERROR AQUI ULT REC CC %s", response.message());
            }

        } catch (Exception ex) {
            Timber.tag(TAG).e(ex, "ERROR AQUI ULT REC CC");
        }
    }
}
