package com.sidert.sidertmovil.v2.services;

import com.sidert.sidertmovil.v2.domain.daos.*;
import com.sidert.sidertmovil.v2.domain.entities.*;
import com.sidert.sidertmovil.v2.repositories.mappers.*;
import com.sidert.sidertmovil.models.MCatalogo;
import com.sidert.sidertmovil.models.MPlazos;
import com.sidert.sidertmovil.models.MTickets;
import com.sidert.sidertmovil.models.ModeloOcupaciones;
import com.sidert.sidertmovil.models.ModeloSectores;
import com.sidert.sidertmovil.v2.remote.datasource.CatalogosRemoteDatasource;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Call;
import retrofit2.Response;
import timber.log.Timber;

@Singleton
public class CatalogoSyncService {

    private static final String TAG = "TAG[" + CatalogoSyncService.class.getSimpleName() + "]";
    private static final String SECTOR_LABEL = "SECTORES";
    private static final String OCUPACIONES_LABEL = "OCUPACIONES";
    private static final String IDENTIFICACIONES_LABEL = "IDENTIFICACIONES";
    private static final String VIVIENDA_LABEL = "VIVIENDA";
    private static final String MEDIO_CONTACTO_LABEL = "MEDIO_CONTACTO";
    private static final String DESTINO_CREDITO_LABEL = "DESTINO_CREDITO";
    private static final String ESTADO_CIVIL_LABEL = "ESTADO_CIVIL";
    private static final String NIVEL_ESTUDIO_LABEL = "NIVEL_ESTUDIO";
    private static final String PAGO_LABEL = "PAGO_ORIGINACION";
    private static final String PARENTESCO_LABEL = "PARENTESCO";
    private static final String TICKETS_LABEL = "TICKET";
    private static final String PLAZO_LABEL = "PLAZO";

    private final ExecutorService executorService;
    private final TicketsDao categoriaTicketDao;
    private final DestinosCreditoDao destinoCreditoDao;
    private final EstadosCivilesDao estadoCivilDao;
    private final IdentificacionTipoDao identificacionTipoDao;
    private final MediosContactoDao medioContactoDao;
    private final MediosPagoOriDao medioPagoOrigicacionDao;
    private final NivelesEstudiosDao nivelEstudioDao;
    private final OcupacionesDao ocupacionDao;
    private final ParentescosDao parentescoDao;
    private final PlazosPrestamosDao plazoDao;
    private final SectoresDao sectorDao;
    private final ViviendaTiposDao viviendaTipoDao;
    private final CatalogosRemoteDatasource catalogosRemoteDatasource;
    private final CatalogoMapper catalogoMapper;

    @Inject
    public CatalogoSyncService(ExecutorService executorService,
                               TicketsDao categoriaTicketDao,
                               DestinosCreditoDao destinoCreditoDao,
                               EstadosCivilesDao estadoCivilDao,
                               IdentificacionTipoDao identificacionTipoDao,
                               MediosContactoDao medioContactoDao,
                               MediosPagoOriDao medioPagoOrigicacionDao,
                               NivelesEstudiosDao nivelEstudioDao,
                               OcupacionesDao ocupacionDao,
                               ParentescosDao parentescoDao,
                               PlazosPrestamosDao plazoDao,
                               SectoresDao sectorDao,
                               ViviendaTiposDao viviendaTipoDao,
                               CatalogosRemoteDatasource catalogosRemoteDatasource,
                               CatalogoMapper catalogoMapper) {
        this.executorService = executorService;
        this.categoriaTicketDao = categoriaTicketDao;
        this.destinoCreditoDao = destinoCreditoDao;
        this.estadoCivilDao = estadoCivilDao;
        this.identificacionTipoDao = identificacionTipoDao;
        this.medioContactoDao = medioContactoDao;
        this.medioPagoOrigicacionDao = medioPagoOrigicacionDao;
        this.nivelEstudioDao = nivelEstudioDao;
        this.ocupacionDao = ocupacionDao;
        this.parentescoDao = parentescoDao;
        this.plazoDao = plazoDao;
        this.sectorDao = sectorDao;
        this.viviendaTipoDao = viviendaTipoDao;
        this.catalogosRemoteDatasource = catalogosRemoteDatasource;
        this.catalogoMapper = catalogoMapper;
    }

    public void startSync() {
        try {

            MyCallable<List<ModeloSectores>> result01 = new MyCallable<>(SECTOR_LABEL, this.catalogosRemoteDatasource.getSectores());
            MyCallable<List<ModeloOcupaciones>> result02 = new MyCallable<>(OCUPACIONES_LABEL, this.catalogosRemoteDatasource.getOcupaciones());
            MyCallable<List<MCatalogo>> result03 = new MyCallable<>(IDENTIFICACIONES_LABEL, this.catalogosRemoteDatasource.getIdentificaciones());
            MyCallable<List<MCatalogo>> result04 = new MyCallable<>(VIVIENDA_LABEL, this.catalogosRemoteDatasource.getViviendaTipos());
            MyCallable<List<MCatalogo>> result05 = new MyCallable<>(MEDIO_CONTACTO_LABEL, this.catalogosRemoteDatasource.getMediosContacto());
            MyCallable<List<MCatalogo>> result06 = new MyCallable<>(DESTINO_CREDITO_LABEL, this.catalogosRemoteDatasource.getDestinosCredito());
            MyCallable<List<MCatalogo>> result07 = new MyCallable<>(ESTADO_CIVIL_LABEL, this.catalogosRemoteDatasource.getEstadosCiviles());
            MyCallable<List<MCatalogo>> result08 = new MyCallable<>(NIVEL_ESTUDIO_LABEL, this.catalogosRemoteDatasource.getNivelesEstudios());
            MyCallable<List<MCatalogo>> result09 = new MyCallable<>(PAGO_LABEL, this.catalogosRemoteDatasource.getMediosPagoOrig());
            MyCallable<List<MCatalogo>> result10 = new MyCallable<>(PARENTESCO_LABEL, this.catalogosRemoteDatasource.getParentesco());
            MyCallable<List<MTickets>> result11 = new MyCallable<>(TICKETS_LABEL, this.catalogosRemoteDatasource.getCategoriasTickets("MOVIL"));
            MyCallable<List<MPlazos>> result12 = new MyCallable<>(PLAZO_LABEL, this.catalogosRemoteDatasource.getPlazosPrestamo());

            List<MyCallable<?>> callables = new ArrayList<>();
            callables.add(result01);
            callables.add(result02);
            callables.add(result03);
            callables.add(result04);
            callables.add(result05);
            callables.add(result06);
            callables.add(result07);
            callables.add(result08);
            callables.add(result09);
            callables.add(result10);
            callables.add(result11);
            callables.add(result12);

            this.executorService.invokeAll(callables)
                    .stream()
                    .map(future -> {
                        try {
                            return future.get();
                        } catch (ExecutionException | InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    })
                    .map(responseWrapper -> {
                        Response<?> response = responseWrapper.response;
                        String kindOfClass = responseWrapper.kindOfClass;
                        return (Runnable) () -> invokeRemoteSourceAndInsert(response, kindOfClass);
                    })
                    .forEach(runner -> {
                        try {
                            this.executorService.submit(runner).get();
                        } catch (ExecutionException | InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    });
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("unchecked")
    private void invokeRemoteSourceAndInsert(Response<?> response, String kindOfClass) {
        if (response.isSuccessful()) {
            Object body = response.body();
            Timber.tag(TAG).i("Procesando %s", kindOfClass);
            switch (kindOfClass) {
                case SECTOR_LABEL:
                    List<ModeloSectores> modeloSectores = (List<ModeloSectores>) body;
                    if (modeloSectores != null && modeloSectores.size() > 0) {
                        List<Sectores> sectorEntities = modeloSectores
                                .stream()
                                .map(catalogoMapper::sectorRemoteToLocal)
                                .collect(Collectors.toList());
                        sectorDao.insertAll(sectorEntities);
                    }
                    break;
                case OCUPACIONES_LABEL:
                    List<ModeloOcupaciones> modeloOcupaciones = (List<ModeloOcupaciones>) body;
                    if (modeloOcupaciones != null && modeloOcupaciones.size() > 0) {
                        List<Ocupaciones> ocupacionEntities = modeloOcupaciones
                                .stream()
                                .map(catalogoMapper::ocupacionRemoteToLocal)
                                .collect(Collectors.toList());
                        ocupacionDao.insertAll(ocupacionEntities);
                    }
                    break;
                case IDENTIFICACIONES_LABEL:
                    List<MCatalogo> indentificaciones = (List<MCatalogo>) body;
                    if (indentificaciones != null && indentificaciones.size() > 0) {
                        List<IdentificacionTipo> identificacionTipos = indentificaciones
                                .stream()
                                .map(catalogoMapper::indentificacionRemoteToLocal)
                                .collect(Collectors.toList());
                        identificacionTipoDao.insertAll(identificacionTipos);
                    }
                    break;
                case VIVIENDA_LABEL:
                    List<MCatalogo> viviendas = (List<MCatalogo>) body;
                    if (viviendas != null && viviendas.size() > 0) {
                        List<ViviendaTipos> viviendaTipos = viviendas
                                .stream()
                                .map(catalogoMapper::viviendaTipoRemoteToLocal)
                                .collect(Collectors.toList());
                        viviendaTipoDao.insertAll(viviendaTipos);
                    }
                    break;
                case MEDIO_CONTACTO_LABEL:
                    List<MCatalogo> mediosContactos = (List<MCatalogo>) body;
                    if (mediosContactos != null && mediosContactos.size() > 0) {
                        List<MediosContacto> medioContactos = mediosContactos
                                .stream()
                                .map(catalogoMapper::medioContactoRemoteToLocal)
                                .collect(Collectors.toList());
                        medioContactoDao.insertAll(medioContactos);
                    }
                    break;
                case DESTINO_CREDITO_LABEL:
                    List<MCatalogo> destinosCreditos = (List<MCatalogo>) body;
                    if (destinosCreditos != null && destinosCreditos.size() > 0) {
                        List<DestinosCredito> destinoCreditos = destinosCreditos
                                .stream()
                                .map(catalogoMapper::destinoCreditoRemoteToLocal)
                                .collect(Collectors.toList());
                        destinoCreditoDao.insertAll(destinoCreditos);
                    }
                    break;
                case ESTADO_CIVIL_LABEL:
                    List<MCatalogo> estadosCiviles = (List<MCatalogo>) body;
                    if (estadosCiviles != null && estadosCiviles.size() > 0) {
                        List<EstadosCiviles> estadoCivils = estadosCiviles
                                .stream()
                                .map(catalogoMapper::estadoCivilRemoteToLocal)
                                .collect(Collectors.toList());
                        estadoCivilDao.insertAll(estadoCivils);
                    }
                    break;
                case NIVEL_ESTUDIO_LABEL:
                    List<MCatalogo> nivelesEstudios = (List<MCatalogo>) body;
                    if (nivelesEstudios != null && nivelesEstudios.size() > 0) {
                        List<NivelesEstudios> nivelEstudios = nivelesEstudios
                                .stream()
                                .map(catalogoMapper::nivelEstudioRemoteToLocal)
                                .collect(Collectors.toList());
                        nivelEstudioDao.insertAll(nivelEstudios);
                    }
                    break;
                case PAGO_LABEL:
                    List<MCatalogo> pagos = (List<MCatalogo>) body;
                    if (pagos != null && pagos.size() > 0) {
                        List<MediosPagoOri> medioPagoOrigicacions = pagos
                                .stream()
                                .map(catalogoMapper::medioPagoOriginacionRemoteToLocal)
                                .collect(Collectors.toList());
                        medioPagoOrigicacionDao.insertAll(medioPagoOrigicacions);
                    }
                    break;
                case PARENTESCO_LABEL:

                    List<MCatalogo> parentescos = (List<MCatalogo>) body;
                    if (parentescos != null && parentescos.size() > 0) {
                        List<Parentescos> parentescoList = parentescos
                                .stream()
                                .map(catalogoMapper::parentescoRemoteToLocal)
                                .collect(Collectors.toList());
                        parentescoDao.insertAll(parentescoList);
                    }
                    break;
                case TICKETS_LABEL:

                    List<MTickets> tickets = (List<MTickets>) body;
                    if (tickets != null && tickets.size() > 0) {
                        List<Tickets> ticketList = tickets
                                .stream()
                                .map(catalogoMapper::categoriaTicketRemoteToLocal)
                                .collect(Collectors.toList());
                        categoriaTicketDao.insertAll(ticketList);
                    }
                    break;
                case PLAZO_LABEL:
                    List<MPlazos> plazos = (List<MPlazos>) body;
                    if (plazos != null && plazos.size() > 0) {
                        List<PlazosPrestamos> plazoList = plazos
                                .stream()
                                .map(catalogoMapper::plazoRemoteToLocal)
                                .collect(Collectors.toList());
                        plazoDao.insertAll(plazoList);
                    }
                    break;
            }
            Timber.tag(TAG).i("Se termino con la clase: %s", kindOfClass);
        }
    }

    private static final class MyCallable<T0> implements Callable<ResponseWrapper<T0>> {
        private final Call<T0> retrofitCall;
        private final String klass;

        private MyCallable(String klass, Call<T0> retrofitCall) {
            this.retrofitCall = retrofitCall;
            this.klass = klass;
        }

        @Override
        public ResponseWrapper<T0> call() throws Exception {
            ResponseWrapper<T0> wrapper = new ResponseWrapper<>();
            wrapper.response = this.retrofitCall.execute();
            wrapper.kindOfClass = klass;
            return wrapper;
        }
    }

    private static final class ResponseWrapper<T0> {
        Response<T0> response;
        String kindOfClass;
    }


}
