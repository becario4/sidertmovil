package com.sidert.sidertmovil.v2.repositories;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.sidert.sidertmovil.database.DBhelper;
import com.sidert.sidertmovil.models.MPrestamosRenovar;
import com.sidert.sidertmovil.models.MRenovacion;
import com.sidert.sidertmovil.models.MRenovacionGrupal;
import com.sidert.sidertmovil.models.solicitudes.PrestamoToRenovar;
import com.sidert.sidertmovil.models.solicitudes.PrestamoToRenovarDao;
import com.sidert.sidertmovil.models.solicitudes.SolicitudRen;
import com.sidert.sidertmovil.models.solicitudes.SolicitudRenDao;
import com.sidert.sidertmovil.utils.Miscellaneous;
import com.sidert.sidertmovil.utils.SessionManager;
import com.sidert.sidertmovil.v2.R;
import com.sidert.sidertmovil.v2.SidertMovilApplication;
import com.sidert.sidertmovil.v2.domain.daos.AvalRenDao;
import com.sidert.sidertmovil.v2.domain.daos.CatColoniasDao;
import com.sidert.sidertmovil.v2.domain.daos.ClienteIndRenDao;
import com.sidert.sidertmovil.v2.domain.daos.ConyugeIndRenDao;
import com.sidert.sidertmovil.v2.domain.daos.ConyugeIntegranteRenDao;
import com.sidert.sidertmovil.v2.domain.daos.CreditoGpoRenDao;
import com.sidert.sidertmovil.v2.domain.daos.CreditoIndRenDao;
import com.sidert.sidertmovil.v2.domain.daos.CroquisGpoRenDao;
import com.sidert.sidertmovil.v2.domain.daos.CroquisIndDao;
import com.sidert.sidertmovil.v2.domain.daos.DestinosCreditoDao;
import com.sidert.sidertmovil.v2.domain.daos.DireccionesRenDao;
import com.sidert.sidertmovil.v2.domain.daos.DocumentosIntegranteRenDao;
import com.sidert.sidertmovil.v2.domain.daos.DocumentosRenDao;
import com.sidert.sidertmovil.v2.domain.daos.DomicilioIntegranteRenDao;
import com.sidert.sidertmovil.v2.domain.daos.EconomicosIndRenDao;
import com.sidert.sidertmovil.v2.domain.daos.EstadosCivilesDao;
import com.sidert.sidertmovil.v2.domain.daos.EstadosDao;
import com.sidert.sidertmovil.v2.domain.daos.IdentificacionTipoDao;
import com.sidert.sidertmovil.v2.domain.daos.IntegrantesGpoRenDao;
import com.sidert.sidertmovil.v2.domain.daos.LocalidadesDao;
import com.sidert.sidertmovil.v2.domain.daos.MediosContactoDao;
import com.sidert.sidertmovil.v2.domain.daos.MunicipiosDao;
import com.sidert.sidertmovil.v2.domain.daos.NegocioIndRenDao;
import com.sidert.sidertmovil.v2.domain.daos.NegocioIntegranteRenDao;
import com.sidert.sidertmovil.v2.domain.daos.NivelesEstudiosDao;
import com.sidert.sidertmovil.v2.domain.daos.OcupacionesDao;
import com.sidert.sidertmovil.v2.domain.daos.OtrosDatosIntegranteRenDao;
import com.sidert.sidertmovil.v2.domain.daos.ParentescosDao;
import com.sidert.sidertmovil.v2.domain.daos.PoliticasPldIndDao;
import com.sidert.sidertmovil.v2.domain.daos.PoliticasPldIntegranteRenDao;
import com.sidert.sidertmovil.v2.domain.daos.PrestamosToRenovarDao;
import com.sidert.sidertmovil.v2.domain.daos.ReferenciaIndRenDao;
import com.sidert.sidertmovil.v2.domain.daos.SectoresDao;
import com.sidert.sidertmovil.v2.domain.daos.SolicitudesRenDao;
import com.sidert.sidertmovil.v2.domain.daos.TelefonosIntegranteRenDao;
import com.sidert.sidertmovil.v2.domain.daos.ViviendaTiposDao;
import com.sidert.sidertmovil.v2.domain.entities.AvalRen;
import com.sidert.sidertmovil.v2.domain.entities.CatColonias;
import com.sidert.sidertmovil.v2.domain.entities.ClienteIndRen;
import com.sidert.sidertmovil.v2.domain.entities.ConyugeIndRen;
import com.sidert.sidertmovil.v2.domain.entities.ConyugeIntegranteRen;
import com.sidert.sidertmovil.v2.domain.entities.CreditoGpoRen;
import com.sidert.sidertmovil.v2.domain.entities.CreditoIndRen;
import com.sidert.sidertmovil.v2.domain.entities.CroquisGpoRen;
import com.sidert.sidertmovil.v2.domain.entities.CroquisInd;
import com.sidert.sidertmovil.v2.domain.entities.DestinosCredito;
import com.sidert.sidertmovil.v2.domain.entities.DireccionesRen;
import com.sidert.sidertmovil.v2.domain.entities.DocumentosIntegranteRen;
import com.sidert.sidertmovil.v2.domain.entities.DocumentosRen;
import com.sidert.sidertmovil.v2.domain.entities.DomicilioIntegranteRen;
import com.sidert.sidertmovil.v2.domain.entities.EconomicosIndRen;
import com.sidert.sidertmovil.v2.domain.entities.Estados;
import com.sidert.sidertmovil.v2.domain.entities.EstadosCiviles;
import com.sidert.sidertmovil.v2.domain.entities.IdentificacionTipo;
import com.sidert.sidertmovil.v2.domain.entities.IntegrantesGpoRen;
import com.sidert.sidertmovil.v2.domain.entities.Localidades;
import com.sidert.sidertmovil.v2.domain.entities.MediosContacto;
import com.sidert.sidertmovil.v2.domain.entities.Municipios;
import com.sidert.sidertmovil.v2.domain.entities.NegocioIndRen;
import com.sidert.sidertmovil.v2.domain.entities.NegocioIntegranteRen;
import com.sidert.sidertmovil.v2.domain.entities.NivelesEstudios;
import com.sidert.sidertmovil.v2.domain.entities.Ocupaciones;
import com.sidert.sidertmovil.v2.domain.entities.OtrosDatosIntegranteRen;
import com.sidert.sidertmovil.v2.domain.entities.Parentescos;
import com.sidert.sidertmovil.v2.domain.entities.PoliticasPldInd;
import com.sidert.sidertmovil.v2.domain.entities.PoliticasPldIntegranteRen;
import com.sidert.sidertmovil.v2.domain.entities.PrestamosToRenovar;
import com.sidert.sidertmovil.v2.domain.entities.ReferenciaIndRen;
import com.sidert.sidertmovil.v2.domain.entities.Sectores;
import com.sidert.sidertmovil.v2.domain.entities.SolicitudesRen;
import com.sidert.sidertmovil.v2.domain.entities.TelefonosIntegranteRen;
import com.sidert.sidertmovil.v2.domain.entities.ViviendaTipos;
import com.sidert.sidertmovil.v2.remote.datasource.PrestamosPorRenovarRemoteDatasource;
import com.sidert.sidertmovil.v2.repositories.mappers.PrestamoToRenovarMapper;
import com.sidert.sidertmovil.v2.utils.ExecutorUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import javax.inject.Inject;
import javax.inject.Singleton;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import timber.log.Timber;

import static com.sidert.sidertmovil.utils.Constants.TBL_PRESTAMOS_TO_RENOVAR;
import static com.sidert.sidertmovil.utils.Constants.TIMESTAMP;

@Singleton
public final class PrestamosRenovarRepository
        extends BaseRepository {

    private static final String TAG = PrestamosRenovarRepository.class.getName();
    private final PrestamosPorRenovarRemoteDatasource prestamosPorRenovarRemoteDatasource;
    private final PrestamosToRenovarDao prestamosToRenovarDao;
    private final SidertMovilApplication sidertMovilApplication;
    private final SolicitudesRenDao solicitudesRenDao;
    private final CreditoIndRenDao creditoIndRenDao;
    private final DireccionesRenDao direccionesRenDao;
    private final CatColoniasDao catColoniasDao;
    private final LocalidadesDao localidadesDao;
    private final MunicipiosDao municipiosDao;
    private final EstadosDao estadosDao;
    private final ClienteIndRenDao clienteIndRenDao;
    private final OcupacionesDao ocupacionesDao;
    private final SectoresDao sectoresDao;
    private final IdentificacionTipoDao identificacionTipoDao;
    private final NivelesEstudiosDao nivelesEstudiosDao;
    private final EstadosCivilesDao estadosCivilesDao;
    private final ViviendaTiposDao viviendaTiposDao;
    private final MediosContactoDao mediosContactoDao;
    private final ConyugeIndRenDao conyugeIndRenDao;
    private final EconomicosIndRenDao economicosIndRenDao;
    private final NegocioIndRenDao negocioIndRenDao;
    private final AvalRenDao avalRenDao;
    private final ParentescosDao parentescosDao;
    private final ReferenciaIndRenDao referenciaIndRenDao;
    private final CroquisIndDao croquisIndDao;
    private final PoliticasPldIndDao politicasPldIndDao;
    private final DocumentosRenDao documentosRenDao;
    private final CreditoGpoRenDao creditoGpoRenDao;
    private final PrestamoToRenovarMapper prestamoToRenovarMapper;
    private final IntegrantesGpoRenDao integrantesGpoRenDao;
    private final TelefonosIntegranteRenDao telefonosIntegranteRenDao;
    private final DomicilioIntegranteRenDao domicilioIntegranteRenDao;
    private final DestinosCreditoDao destinosCreditoDao;
    private final NegocioIntegranteRenDao negocioIntegranteRenDao;
    private final ConyugeIntegranteRenDao conyugeIntegranteRenDao;
    private final OtrosDatosIntegranteRenDao otrosDatosIntegranteRenDao;
    private final CroquisGpoRenDao croquisGpoRenDao;
    private final DocumentosIntegranteRenDao documentosIntegranteRenDao;

    private final PoliticasPldIntegranteRenDao politicasPldIntegranteRenDao;

    private final Timber.Tree logTag;

    @Inject
    public PrestamosRenovarRepository(ExecutorUtil executorUtil,
                                      SessionManager sessionManager,
                                      SidertMovilApplication sidertMovilApplication,
                                      PrestamosPorRenovarRemoteDatasource prestamosPorRenovarRemoteDatasource,
                                      PrestamosToRenovarDao prestamosToRenovarDao,
                                      SolicitudesRenDao solicitudesRenDao,
                                      CreditoIndRenDao creditoIndRenDao,
                                      DireccionesRenDao direccionesRenDao,
                                      CatColoniasDao catColoniasDao,
                                      LocalidadesDao localidadesDao,
                                      MunicipiosDao municipiosDao,
                                      EstadosDao estadosDao,
                                      ClienteIndRenDao clienteIndRenDao,
                                      OcupacionesDao ocupacionesDao,
                                      SectoresDao sectoresDao,
                                      IdentificacionTipoDao identificacionTipoDao,
                                      NivelesEstudiosDao nivelesEstudiosDao,
                                      EstadosCivilesDao estadosCivilesDao,
                                      ViviendaTiposDao viviendaTiposDao,
                                      MediosContactoDao mediosContactoDao,
                                      ConyugeIndRenDao conyugeIndRenDao,
                                      EconomicosIndRenDao economicosIndRenDao,
                                      NegocioIndRenDao negocioIndRenDao,
                                      AvalRenDao avalRenDao,
                                      ParentescosDao parentescosDao,
                                      ReferenciaIndRenDao referenciaIndRenDao,
                                      PrestamoToRenovarMapper prestamoToRenovarMapper,
                                      CroquisIndDao croquisIndDao,
                                      PoliticasPldIndDao politicasPldIndDao,
                                      DocumentosRenDao documentosRenDao,
                                      CreditoGpoRenDao creditoGpoRenDao,
                                      IntegrantesGpoRenDao integrantesGpoRenDao,
                                      TelefonosIntegranteRenDao telefonosIntegranteRenDao,
                                      DomicilioIntegranteRenDao domicilioIntegranteRenDao,
                                      DestinosCreditoDao destinosCreditoDao,
                                      NegocioIntegranteRenDao negocioIntegranteRenDao,
                                      ConyugeIntegranteRenDao conyugeIntegranteRenDao,
                                      OtrosDatosIntegranteRenDao otrosDatosIntegranteRenDao,
                                      CroquisGpoRenDao croquisGpoRenDao,
                                      DocumentosIntegranteRenDao documentosIntegranteRenDao,
                                      PoliticasPldIntegranteRenDao politicasPldIntegranteRenDao

    ) {
        super(executorUtil, sessionManager);
        this.prestamosPorRenovarRemoteDatasource = prestamosPorRenovarRemoteDatasource;
        this.prestamosToRenovarDao = prestamosToRenovarDao;
        this.sidertMovilApplication = sidertMovilApplication;
        this.solicitudesRenDao = solicitudesRenDao;
        this.creditoIndRenDao = creditoIndRenDao;
        this.direccionesRenDao = direccionesRenDao;
        this.catColoniasDao = catColoniasDao;
        this.localidadesDao = localidadesDao;
        this.municipiosDao = municipiosDao;
        this.estadosDao = estadosDao;
        this.clienteIndRenDao = clienteIndRenDao;
        this.ocupacionesDao = ocupacionesDao;
        this.sectoresDao = sectoresDao;
        this.identificacionTipoDao = identificacionTipoDao;
        this.nivelesEstudiosDao = nivelesEstudiosDao;
        this.estadosCivilesDao = estadosCivilesDao;
        this.viviendaTiposDao = viviendaTiposDao;
        this.mediosContactoDao = mediosContactoDao;
        this.conyugeIndRenDao = conyugeIndRenDao;
        this.economicosIndRenDao = economicosIndRenDao;
        this.negocioIndRenDao = negocioIndRenDao;
        this.avalRenDao = avalRenDao;
        this.parentescosDao = parentescosDao;
        this.referenciaIndRenDao = referenciaIndRenDao;
        this.croquisIndDao = croquisIndDao;
        this.politicasPldIndDao = politicasPldIndDao;
        this.documentosRenDao = documentosRenDao;
        this.creditoGpoRenDao = creditoGpoRenDao;
        this.prestamoToRenovarMapper = prestamoToRenovarMapper;
        this.integrantesGpoRenDao = integrantesGpoRenDao;
        this.telefonosIntegranteRenDao = telefonosIntegranteRenDao;
        this.domicilioIntegranteRenDao = domicilioIntegranteRenDao;
        this.destinosCreditoDao = destinosCreditoDao;
        this.negocioIntegranteRenDao = negocioIntegranteRenDao;
        this.conyugeIntegranteRenDao = conyugeIntegranteRenDao;
        this.otrosDatosIntegranteRenDao = otrosDatosIntegranteRenDao;
        this.croquisGpoRenDao = croquisGpoRenDao;
        this.documentosIntegranteRenDao = documentosIntegranteRenDao;
        this.politicasPldIntegranteRenDao = politicasPldIntegranteRenDao;
        this.logTag = Timber.tag(TAG);
    }

    public void getPrestamosToRenovarOld() {
        final DBhelper dBhelper = DBhelper.getInstance(sidertMovilApplication);
        final SQLiteDatabase db = dBhelper.getWritableDatabase();

        Call<List<MPrestamosRenovar>> call = this.prestamosPorRenovarRemoteDatasource.getPrestamoToRenovar();
        try {
            Response<List<MPrestamosRenovar>> response = this.executorUtil.process(call);
            if (response.code() == 200) {
                Log.e("AQUI", String.valueOf(response.code()));
                List<MPrestamosRenovar> prestamos = response.body();
                if (prestamos.size() > 0) {
                    for (MPrestamosRenovar item : prestamos) {
                        if (item.getTipoPrestamo().equals("INDIVIDUAL")) {
                            String sql = "SELECT * FROM " + TBL_PRESTAMOS_TO_RENOVAR + " WHERE asesor_id = ? AND prestamo_id = ? and cliente_id = ?";
                            Cursor row = db.rawQuery(sql, new String[]{sessionManager.getUser().get(0), String.valueOf(item.getPrestamoId()), String.valueOf(item.getClienteId())});

                            if (row.getCount() == 0) {
                                HashMap<Integer, String> params = new HashMap<>();
                                params.put(0, sessionManager.getUser().get(0));
                                params.put(1, String.valueOf(item.getPrestamoId()));
                                params.put(2, String.valueOf(item.getClienteId()));
                                params.put(3, item.getNombre());
                                params.put(4, item.getNoPrestamo().trim());
                                params.put(5, item.getFechaVencimiento());
                                params.put(6, String.valueOf(item.getNumPagos()));
                                params.put(7, "0");
                                params.put(8, "1");
                                params.put(9, String.valueOf(item.getGrupoId()));

                                dBhelper.savePrestamosToRenovar(db, params);

                            }
                            row.close();
                        } else {
                            String sql = "SELECT * FROM " + TBL_PRESTAMOS_TO_RENOVAR + " WHERE asesor_id = ? AND grupo_id = ? and tipo_prestamo = 2 AND prestamo_id = ?";
                            Cursor row = db.rawQuery(sql, new String[]{sessionManager.getUser().get(0), String.valueOf(item.getGrupoId()), String.valueOf(item.getPrestamoId())});

                            if (row.getCount() == 0) {
                                HashMap<Integer, String> params = new HashMap<>();
                                params.put(0, sessionManager.getUser().get(0));
                                params.put(1, String.valueOf(item.getPrestamoId()));
                                params.put(2, String.valueOf(item.getClienteId()));
                                params.put(3, item.getNombre());
                                params.put(4, (item.getNoPrestamo() == null) ? "N/A" : item.getNoPrestamo().trim()); //recive dato null, pendiente a soporte
                                params.put(5, item.getFechaVencimiento());
                                params.put(6, String.valueOf(item.getNumPagos()));
                                params.put(7, "0");
                                params.put(8, "2");
                                params.put(9, String.valueOf(item.getGrupoId()));
                                dBhelper.savePrestamosToRenovar(db, params);
                            }
                            row.close();
                        }
                    }
                    GetDatosRenovarOld();
                }
            } else {
                Log.e("AQUI", String.valueOf(response.code()));
            }
        } catch (ExecutionException | InterruptedException e) {
            this.logTag.e(e);
        }
    }

    public void GetDatosRenovarOld() {
        final DBhelper dBhelper = DBhelper.getInstance(sidertMovilApplication);
        final SQLiteDatabase db = dBhelper.getWritableDatabase();
        SolicitudRenDao solicitudRenDao = new SolicitudRenDao(sidertMovilApplication);
        PrestamoToRenovarDao prestamoToRenovarDao = new PrestamoToRenovarDao(sidertMovilApplication);

        String sql = "SELECT _id, prestamo_id, cliente_id, tipo_prestamo, grupo_id, cliente_nombre, fecha_vencimiento  FROM " + TBL_PRESTAMOS_TO_RENOVAR;
        Cursor row = db.rawQuery(sql, new String[]{});

        if (row.getCount() > 0) {
            row.moveToFirst();
            for (int x = 0; x < row.getCount(); x++) {
                Log.e("AQUI ASIGNAR UNO ", row.getString(5));

                List<SolicitudRen> solicitudesRen = solicitudRenDao.findByNombreAndPrestamoId(row.getString(5), row.getInt(1));

                Log.e("AQUI ENCONTRADOS ", String.valueOf(solicitudesRen.size()));

                if (solicitudesRen.size() == 1) {
                    Log.e("AQUI ENCONTRADO ", String.valueOf(solicitudesRen.get(0).getPrestamoId()));

                    if (solicitudesRen.get(0).getPrestamoId() == null || solicitudesRen.get(0).getPrestamoId() == 0) {
                        Log.e("AQUI ASIGNAR PRESTAMO ID UNO ", solicitudesRen.get(0).getNombre());
                        solicitudesRen.get(0).setPrestamoId(row.getInt(1));
                        solicitudRenDao.updatePrestamo(solicitudesRen.get(0));
                    }
                } else if (solicitudesRen.size() > 1) {
                    for (int i = 0; i < solicitudesRen.size(); i++) {
                        Log.e("AQUI ENCONTRADO ", String.valueOf(solicitudesRen.get(i).getPrestamoId()));

                        if (solicitudesRen.get(i).getPrestamoId() == null || solicitudesRen.get(i).getPrestamoId() == 0) {
                            Log.e("AQUI ASIGNAR PRESTAMO ID DOS ", solicitudesRen.get(i).getNombre());

                            PrestamoToRenovar prestamoToRenovarTemp = prestamoToRenovarDao.findByClienteNombreAndPosition(row.getString(5), i);
                            if (prestamoToRenovarTemp != null) {
                                solicitudesRen.get(i).setPrestamoId(prestamoToRenovarTemp.getPrestamoId());
                                solicitudRenDao.updatePrestamo(solicitudesRen.get(i));
                            }

                        }
                    }
                }
                row.moveToNext();
            }
        }

        row.close();

        //SE GUARDAN LOS QUE NO HAN SIDO SINCRONIZADOS
        sql = "SELECT _id, prestamo_id, cliente_id, tipo_prestamo, grupo_id, cliente_nombre, fecha_vencimiento  FROM " + TBL_PRESTAMOS_TO_RENOVAR + " WHERE descargado = ?";
        row = db.rawQuery(sql, new String[]{"0"});

        if (row.getCount() > 0) {
            row.moveToFirst();
            for (int i = 0; i < row.getCount(); i++) {
                if (row.getInt(4) == 1) {
                    try {
                        Integer prestamoId = row.getInt(1);
                        Integer clienteId = row.getInt(2);
                        RegistrarDatosRenovacion datosRenovacion = new RegistrarDatosRenovacion(sidertMovilApplication, sessionManager, prestamoId, clienteId);
                        String itsAllOk = this.executorUtil.runTaskInThread(datosRenovacion);
                    } catch (ExecutionException | InterruptedException | TimeoutException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    try {
                        Integer grupoId = row.getInt(4);
                        String nombreGrupo = row.getString(5);
                        RegistrarDatosRenovacionGpo renovacionGpo = new RegistrarDatosRenovacionGpo(sidertMovilApplication, sessionManager, grupoId, nombreGrupo);
                        String itsAllOK = this.executorUtil.runTaskInThread(renovacionGpo);
                    } catch (ExecutionException | InterruptedException | TimeoutException e) {
                        throw new RuntimeException(e);
                    }
                }
                row.moveToNext();
            }
        }
        row.close();
    }

    public class RegistrarDatosRenovacion implements Callable<String> {
        private final Context ctx;
        private final SessionManager session;
        private final Integer prestamoId;
        private final Integer clienteId;
        private final DBhelper dBhelper;
        private final SQLiteDatabase db;

        public RegistrarDatosRenovacion(Context ctx, SessionManager session, Integer prestamoId, Integer clienteId) {
            this.ctx = ctx;
            this.session = session;
            this.prestamoId = prestamoId;
            this.clienteId = clienteId;
            this.dBhelper = DBhelper.getInstance(ctx);
            this.db = this.dBhelper.getWritableDatabase();
        }

        @Override
        public String call() throws Exception {


            Log.e("AQUI: ", String.valueOf(prestamoId));
            Log.e("AQUI: ", String.valueOf(clienteId));

            Call<MRenovacion> call = prestamosPorRenovarRemoteDatasource.getPrestamoToRenovar(prestamoId, clienteId);
            Response<MRenovacion> response = executorUtil.process(call);

            if (response.code() == 200) {
                MRenovacion item = response.body();
                int tipoSolicitud = 2;
                long id = 0;
                long id_cliente = 0;
                long id_direccion_cli = 0;
                long id_direccion_cony = 0;
                long id_direccion_neg = 0;
                long id_direccion_aval = 0;
                long id_direccion_ref = 0;

                String nombre = (Miscellaneous.validStr(item.getCliente().getNombre()) + " " +
                        Miscellaneous.validStr(item.getCliente().getPaterno()) + " " +
                        Miscellaneous.validStr(item.getCliente().getMaterno())).trim().toUpperCase();

                HashMap<Integer, String> params = new HashMap<>();
                params.put(0, ctx.getString(com.sidert.sidertmovil.R.string.vol_solicitud));                               //VOL SOLICITUD
                params.put(1, session.getUser().get(9));                 //USUARIO ID
                params.put(2, "1");                                      //TIPO SOLICITUD
                params.put(3, "0");                                      //ID ORIGINACION
                params.put(4, nombre);                                  //NOMBRE
                params.put(5, Miscellaneous.ObtenerFecha(TIMESTAMP));   //FECHA INICIO
                params.put(6, "");                                       //FECHA TERMINO
                params.put(7, "");                                       //FECHA ENVIO
                params.put(8, Miscellaneous.ObtenerFecha(TIMESTAMP));   //FECHA DISPOSITIVO
                params.put(9, "");                                      //FECHA GUARDADO
                params.put(10, "0");                                    //ESTATUS
                params.put(11, String.valueOf(item.getPrestamo().getPrestamodId())); //prestamo_id

                id = dBhelper.saveSolicitudes(db, params, tipoSolicitud);

                //Inserta registro de datos del credito
                params = new HashMap<>();
                params.put(0, String.valueOf(id));                                       //ID SOLICITUD
                params.put(1, "");                                                       //PLAZO
                params.put(2, "");                                                       //PERIODICIDAD
                params.put(3, "");                                                       //FECHA DESEMBOLSO
                params.put(4, "");                                                       //DIA DESEMBOLSO
                params.put(5, "");                                                       //HORA VISITA
                params.put(6, "");                                                       //MONTO PRESTAMO
                params.put(7, String.valueOf(Miscellaneous.validInt(item.getPrestamo().getNumCiclo())));         //CICLO
                params.put(8, String.valueOf(item.getPrestamo().getMonto()));           //CREDITO ANTERIOR
                params.put(9, "");                                                       //COMPORTAMIENTO PAGO
                params.put(10, String.valueOf(Miscellaneous.validInt(item.getPrestamo().getClienteId())));       //NUM CLIENTE
                params.put(11, "");                                                      //OBSERVACIONES
                params.put(12, "");                                                      //DESTINO
                params.put(13, Miscellaneous.GetRiesgo(Miscellaneous.validInt(item.getPrestamo().getNivelRiesgo())));//CLASIFICACION RIESGO
                params.put(14, "0");                                                     //ESTATUS COMPLETO

                dBhelper.saveDatosCreditoRen(db, params);

                //Inserta registro de direccion del cliente
                params = new HashMap<>();
                params.put(0, "CLIENTE");                                                              //TIPO DIRECCION
                params.put(1, Miscellaneous.validStr(item.getCliente().getLatitud()));                 //LATITUD
                params.put(2, Miscellaneous.validStr(item.getCliente().getLongitud()));                //LONGITUD
                params.put(3, Miscellaneous.validStr(item.getCliente().getCalle()));                   //CALLE
                params.put(4, Miscellaneous.validStr(item.getCliente().getNoExterior()));              //NO EXTERIOR
                params.put(5, Miscellaneous.validStr(item.getCliente().getNoInterior()));              //NO INTERIOR
                params.put(6, Miscellaneous.validStr(item.getCliente().getNoManzana()));               //MANZANA
                params.put(7, Miscellaneous.validStr(item.getCliente().getNoLote()));                  //LOTE
                params.put(8, String.valueOf(Miscellaneous.validInt(item.getCliente().getCodigoPostal())));//CP
                params.put(9, Miscellaneous.GetColonia(ctx, Miscellaneous.validInt(item.getCliente().getColoniaId())));//COLONIA
                params.put(10, Miscellaneous.validStr(item.getCliente().getCiudad()));                 //CIUDAD
                params.put(11, Miscellaneous.GetLocalidad(ctx, Miscellaneous.validInt(item.getCliente().getLocalidadId())));//LOCALIDAD
                params.put(12, Miscellaneous.GetMunicipio(ctx, Miscellaneous.validInt(item.getCliente().getMunicipioId())));//MUNICIPIO
                params.put(13, Miscellaneous.GetEstado(ctx, Miscellaneous.validInt(item.getCliente().getEstadoId())));//ESTADO

                id_direccion_cli = dBhelper.saveDirecciones(db, params, 2);

                //Inserta registro de datos del cliente

                params = new HashMap<>();
                params.put(0, String.valueOf(id));                                      //ID SOLICITUD
                params.put(1, Miscellaneous.validStr(item.getCliente().getNombre().trim().toUpperCase()));      //NOMBRE
                params.put(2, Miscellaneous.validStr(item.getCliente().getPaterno().trim().toUpperCase()));     //PATERNO
                params.put(3, Miscellaneous.validStr(item.getCliente().getMaterno().trim().toUpperCase()));     //MATERNO
                params.put(4, Miscellaneous.validStr(item.getCliente().getFechaNacimiento()));                  //FECHA NACIMIENTO
                params.put(5, Miscellaneous.GetEdad(item.getCliente().getFechaNacimiento()));//EDAD
                params.put(6, String.valueOf(item.getCliente().getGenero()));           //GENERO
                params.put(7, Miscellaneous.GetEstado(ctx, Miscellaneous.validInt(item.getCliente().getEstadoNacimiento())));//ESTADO NACIMIENTO
                params.put(8, Miscellaneous.validStr(Miscellaneous.validStr(item.getCliente().getRfc())));     //RFC
                params.put(9, Miscellaneous.validStr(Miscellaneous.validStr(item.getCliente().getCurp())));     //CURP
                params.put(10, "");                                                     //CURP DIGITO VERI
                params.put(11, Miscellaneous.GetOcupacion(ctx, Miscellaneous.validInt(item.getCliente().getOcupacionId()))); //OCUPACION
                params.put(12, Miscellaneous.GetSector(ctx, Miscellaneous.validInt(item.getCliente().getOcupacionId())));   //ACTIVIDAD ECONOMICA
                params.put(13, Miscellaneous.GetTipoIdentificacion(ctx, item.getCliente().getIdentificacionTipoId()));                                                     //TIPO IDENTIFICACION
                params.put(14, Miscellaneous.validStr(item.getCliente().getNoIdentificacion()));//NUM IDENTIFICACION
                params.put(15, Miscellaneous.GetEstudio(ctx, item.getCliente().getEstudioNivelId()));   //NIVEL ESTUDIO
                params.put(16, Miscellaneous.GetEstadoCivil(ctx, item.getCliente().getEstadoCivilId()));//ESTATUS CIVIL
                params.put(17, String.valueOf(item.getCliente().getRegimenBienId()));                   //BIENES
                params.put(18, Miscellaneous.GetViviendaTipo(ctx, item.getCliente().getViviendaTipoId()));  //TIPO VIVIENDA
                params.put(19, "");                                                     //PARENTESCO
                params.put(20, "");                                                     //OTRO TIPO VIVIENDA
                params.put(21, String.valueOf(id_direccion_cli));                           //DIRECCION ID
                params.put(22, Miscellaneous.validStr(item.getCliente().getTelCasa())); //TEL CASA
                params.put(23, Miscellaneous.validStr(item.getCliente().getTelCelular())); //TEL CELULAR
                params.put(24, Miscellaneous.validStr(item.getCliente().getTelMensaje())); //TEL MENSAJES
                params.put(25, Miscellaneous.validStr(item.getCliente().getTelTrabajo())); //TEL TRABAJO
                if (!Miscellaneous.validStr(item.getCliente().getTiempoVivirSitio()).isEmpty())
                    params.put(26, item.getCliente().getTiempoVivirSitio());
                else
                    params.put(26, "0");                                                //TIEMPO VIVIR SITIO
                params.put(27, Miscellaneous.validStr(item.getCliente().getDependientesEconomico()));  //DEPENDIENTES
                params.put(28, Miscellaneous.GetMedioContacto(ctx, item.getCliente().getMedioContactoId()));                                                     //MEDIO CONTACTO
                params.put(29, Miscellaneous.validStr(item.getCliente().getEstadoCuenta()));//ESTADO CUENTA
                params.put(30, Miscellaneous.validStr(item.getCliente().getEmail()));   //EMAIL
                params.put(31, "");                                                     //FOTO FACHADA
                params.put(32, Miscellaneous.validStr(item.getCliente().getReferencia()));//REF DOMICILIARIA
                params.put(33, "");                                                     //FIRMA
                params.put(34, "0");                                                    //ESTATUS RECHAZO
                params.put(35, "");                                                     //COMENTARIO RECHAZO
                params.put(36, "0");                                                    //ESTATUS COMPLETO

                id_cliente = dBhelper.saveDatosPersonales(db, params, 2);

                //Inserta registro de direccion del cliente
                params = new HashMap<>();

                params.put(0, "CONYUGE");                                               //TIPO DIRECCION
                params.put(1, Miscellaneous.validStr(item.getConyuge().getLatitud()));  //LATITUD
                params.put(2, Miscellaneous.validStr(item.getConyuge().getLongitud())); //LONGITUD
                params.put(3, Miscellaneous.validStr(item.getConyuge().getCalle()));    //CALLE
                params.put(4, Miscellaneous.validStr(item.getConyuge().getNoExterior()));//NO EXTERIOR
                params.put(5, Miscellaneous.validStr(item.getConyuge().getNoInterior()));//NO INTERIOR
                params.put(6, Miscellaneous.validStr(item.getConyuge().getNoManzana()));//MANZANA
                params.put(7, Miscellaneous.validStr(item.getConyuge().getNoLote()));   //LOTE
                params.put(8, (item.getConyuge().getCodigoPostal() == 0) ? "" : String.valueOf(item.getConyuge().getCodigoPostal())); //CP
                params.put(9, Miscellaneous.GetColonia(ctx, Miscellaneous.validInt(item.getConyuge().getColoniaId()))); //COLONIA
                params.put(10, Miscellaneous.validStr(item.getConyuge().getCiudad()));  //CIUDAD
                params.put(11, Miscellaneous.GetLocalidad(ctx, Miscellaneous.validInt(item.getConyuge().getLocalidadId()))); //LOCALIDAD
                params.put(12, Miscellaneous.GetMunicipio(ctx, Miscellaneous.validInt(item.getConyuge().getMunicipioId()))); //MUNICIPIO
                params.put(13, Miscellaneous.GetEstado(ctx, Miscellaneous.validInt(item.getConyuge().getEstadoId()))); //ESTADO

                id_direccion_cony = dBhelper.saveDirecciones(db, params, 2);

                //Inserta registro de datos conyuge
                params = new HashMap<>();
                params.put(0, String.valueOf(id));                      //ID SOLICITUD
                params.put(1, Miscellaneous.validStr(item.getConyuge().getNombre()).toUpperCase()); //NOMBRE
                params.put(2, Miscellaneous.validStr(item.getConyuge().getPaterno()).toUpperCase());//PATERNO
                params.put(3, Miscellaneous.validStr(item.getConyuge().getMaterno()).toUpperCase());//MATERNO
                params.put(4, Miscellaneous.validStr(item.getConyuge().getNacionalidad()));         //NACIONALIDAD
                params.put(5, Miscellaneous.GetOcupacion(ctx, Miscellaneous.validInt(item.getConyuge().getOcupacionId()))); //OCUPACION
                params.put(6, String.valueOf(id_direccion_cony));                                   //DIRECCION ID
                params.put(7, String.valueOf(item.getConyuge().getIngresoMensual()));               //ING MENSUAL
                params.put(8, String.valueOf(item.getConyuge().getGastoMensual()));                 //GASTO MENSUAL
                params.put(9, Miscellaneous.validStr(item.getConyuge().getTelCasa()));              //TEL CASA
                params.put(10, Miscellaneous.validStr(item.getConyuge().getTelCelular()));          //TEL CELULAR
                params.put(11, "0");                                                                //ESTATUS COMPLETADO

                dBhelper.saveDatosConyuge(db, params, 2);

                //Inserta registro de datos economicos
                params = new HashMap<>();
                params.put(0, String.valueOf(id));                      //ID SOLICITUD
                params.put(1, "");                                      //PROPIEDADES
                params.put(2, "");                                      //VALOR APROXIMADO
                params.put(3, "");                                      //UBICACION
                params.put(4, "");                                      //INGRESO
                params.put(5, "0");                                     //ESTATUS COMPLETADO

                dBhelper.saveDatosEconomicos(db, params, 2);

                //Inserta registro de direccion del negocio
                params = new HashMap<>();
                params.put(0, "NEGOCIO");                                               //TIPO DIRECCION
                params.put(1, Miscellaneous.validStr(item.getNegocio().getLatitud()));  //LATITUD
                params.put(2, Miscellaneous.validStr(item.getNegocio().getLongitud())); //LONGITUD
                params.put(3, Miscellaneous.validStr(item.getNegocio().getCalle()).toUpperCase());    //CALLE
                params.put(4, Miscellaneous.validStr(item.getNegocio().getNoExterior())); //NO EXTERIOR
                params.put(5, Miscellaneous.validStr(item.getNegocio().getNoInterior())); //NO INTERIOR
                params.put(6, Miscellaneous.validStr(item.getNegocio().getNoManzana()));  //MANZANA
                params.put(7, Miscellaneous.validStr(item.getNegocio().getNoLote()));     //LOTE
                params.put(8, (item.getNegocio().getCodigoPostal() == 0) ? "" : String.valueOf(item.getNegocio().getCodigoPostal()));                                                     //CP
                params.put(9, Miscellaneous.GetColonia(ctx, item.getNegocio().getColoniaId())); //COLONIA
                params.put(10, item.getNegocio().getCiudad());                           //CIUDAD
                params.put(11, Miscellaneous.GetLocalidad(ctx, item.getNegocio().getLocalidadId()));  //LOCALIDAD
                params.put(12, Miscellaneous.GetMunicipio(ctx, item.getNegocio().getMunicipioId()));  //MUNICIPIO
                params.put(13, Miscellaneous.GetEstado(ctx, item.getNegocio().getEstadoId())); //ESTADO

                id_direccion_neg = dBhelper.saveDirecciones(db, params, 2);

                //Inserta registro de negocio
                params = new HashMap<>();
                params.put(0, String.valueOf(id));                  //ID SOLICITUD
                params.put(1, Miscellaneous.validStr(item.getNegocio().getNombre()));   //NOMBRE
                params.put(2, String.valueOf(id_direccion_neg));        //DIRECCION ID
                params.put(3, Miscellaneous.GetOcupacion(ctx, item.getNegocio().getOcupacionId())); //OCUPACION
                params.put(4, Miscellaneous.GetSector(ctx, item.getNegocio().getOcupacionId()));    //ACTIVIDAD ECONOMICA
                params.put(5, Miscellaneous.GetDestinoCredito(ctx, item.getNegocio().getDestinoCreditoId())); //DESTINO CREDITO
                params.put(6, Miscellaneous.validStr(item.getNegocio().getOtroDestinoCredito())); //OTRO DESTINO
                params.put(7, String.valueOf(item.getNegocio().getAntiguedad()));       //ANTIGUEDAD
                params.put(8, "");   //ING MENSUAL
                params.put(9, "");    //ING OTROS
                params.put(10, "");    //GASTO SEMANAL
                params.put(11, "");       //GASTO AGUA
                params.put(12, "");        //GASTO LUZ
                params.put(13, "");   //GASTO TELEFONO
                params.put(14, "");      //GASTO RENTA
                params.put(15, "");      //GASTO OTROS
                params.put(16, "");   //CAPACIDAD PAGO
                params.put(17, "");                                  //MEDIO PAGO
                params.put(18, "");                                  //OTRO MEDIO PAGO
                params.put(19, "");                                  //MONTO MAXIMO
                params.put(20, "0");                                 //NUM OPERACION MENSUALES
                params.put(21, "0");                                 //NUM OPERACION EFECTIVO
                params.put(22, "");                                  //DIAS VENTA
                params.put(23, "");                                  //FOTO FACHADA
                params.put(24, "");                                  //REF DOMICILIARIA
                params.put(25, "0");                                 //ESTATUS COMPLETADO
                params.put(26, "");                                  //COMENTARIO RECHAZO

                dBhelper.saveDatosNegocio(db, params, 2);

                //Inserta registro de direccion del aval
                params = new HashMap<>();
                params.put(0, "AVAL");                                                 //TIPO DIRECCION
                params.put(1, Miscellaneous.validStr(item.getAval().getLatitud()));    //LATITUD
                params.put(2, Miscellaneous.validStr(item.getAval().getLongitud()));   //LONGITUD
                params.put(3, Miscellaneous.validStr(item.getAval().getCalle()));      //CALLE
                params.put(4, Miscellaneous.validStr(item.getAval().getNoExterior())); //NO EXTERIOR
                params.put(5, Miscellaneous.validStr(item.getAval().getNoInterior())); //NO INTERIOR
                params.put(6, Miscellaneous.validStr(item.getAval().getNoManzana()));  //MANZANA
                params.put(7, Miscellaneous.validStr(item.getAval().getNoLote()));     //LOTE
                params.put(8, (item.getAval().getCodigoPostal() == 0) ? "" : String.valueOf(item.getAval().getCodigoPostal())); //CP
                params.put(9, Miscellaneous.GetColonia(ctx, item.getAval().getColoniaId())); //COLONIA
                params.put(10, Miscellaneous.validStr(item.getAval().getCiudad()));     //CIUDAD
                params.put(11, Miscellaneous.GetLocalidad(ctx, item.getAval().getLocalidadId())); //LOCALIDAD
                params.put(12, Miscellaneous.GetMunicipio(ctx, item.getAval().getMunicipioId())); //MUNICIPIO
                params.put(13, Miscellaneous.GetEstado(ctx, item.getAval().getEstadoId())); //ESTADO

                id_direccion_aval = dBhelper.saveDirecciones(db, params, 2);

                //Inserta registro del aval
                params = new HashMap<>();
                params.put(0, String.valueOf(id));                  //ID SOLICITUD
                params.put(1, Miscellaneous.validStr(item.getAval().getNombre()).toUpperCase());          //NOMBRE
                params.put(2, Miscellaneous.validStr(item.getAval().getPaterno()).toUpperCase());         //PATERNO
                params.put(3, Miscellaneous.validStr(item.getAval().getMaterno()).toUpperCase());         //MATERNO
                params.put(4, Miscellaneous.validStr(item.getAval().getFechaNacimiento()));               //FECHA NACIMIENTO
                params.put(5, String.valueOf(item.getAval().getEdad()));                                  //EDAD
                params.put(6, String.valueOf(Miscellaneous.validInt(item.getAval().getGenero())));        //GENERO
                params.put(7, Miscellaneous.GetEstado(ctx, Miscellaneous.validInt(item.getAval().getEstadoNacimientoId())));//ESTADO NACIMIENTO
                params.put(8, Miscellaneous.validStr(item.getAval().getRfc()));                           //RFC
                params.put(9, Miscellaneous.validStr(item.getAval().getCurp()));                          //CURP
                params.put(10, "");                                                                       //CURP DIGITO
                params.put(11, Miscellaneous.GetParentesco(ctx, Miscellaneous.validInt(item.getAval().getParentescoSolicitanteId()))); //PARENTESCO CLIENTE
                params.put(12, Miscellaneous.GetTipoIdentificacion(ctx, Miscellaneous.validInt(item.getAval().getIdentificacionTipoId()))); //TIPO IDENTIFICACION
                params.put(13, Miscellaneous.validStr(item.getAval().getNoIdentificacion()));             //NUM IDENTIFICACION
                params.put(14, Miscellaneous.GetOcupacion(ctx, Miscellaneous.validInt(item.getAval().getOcupacionId())));         //OCUPACION
                params.put(15, Miscellaneous.GetSector(ctx, Miscellaneous.validInt(item.getAval().getOcupacionId())));            //ACTIVIDAD ECONOMICA
                params.put(16, "");                                                                       //DESTINO CREDITO
                params.put(17, "");                                                                       //OTRO DESTINO
                params.put(18, String.valueOf(id_direccion_aval));                                        //DIRECCION ID
                params.put(19, Miscellaneous.GetViviendaTipo(ctx, Miscellaneous.validInt(item.getAval().getViviendaTipoId())));   //TIPO VIVIENDA
                params.put(20, Miscellaneous.validStr(item.getAval().getNombreTitular()));                //NOMBRE TITULAR
                params.put(21, Miscellaneous.GetParentesco(ctx, Miscellaneous.validInt(item.getAval().getParentescoTitularId()))); //PARENTESCO
                params.put(22, Miscellaneous.validStr(item.getAval().getCaracteristicasDomicilio()));     //CARACTERISTICAS DOMICILIO
                params.put(23, String.valueOf(Miscellaneous.validInt(item.getAval().getAntiguedad())));                           //ANTIGUEDAD
                params.put(24, (item.getAval().getTieneNegocio() != null && item.getAval().getTieneNegocio()) ? "1" : "0");                               //TIENE NEGOCIO
                params.put(25, Miscellaneous.validStr(item.getAval().getNombreNegocio()));                //NOMBRE NEGOCIO
                params.put(26, "");                                 //ING MENSUAL
                params.put(27, "");                                 //ING OTROS
                params.put(28, "");                                 //GASTO SEMANAL
                params.put(29, "");                                 //GASTO AGUA
                params.put(30, "");                                 //GASTO LUZ
                params.put(31, "");                                 //GASTO TELEFONO
                params.put(32, "");                                 //GASTO RENTA
                params.put(33, "");                                 //GASTO OTROS
                params.put(34, "");                                 //CAPACIDAD PAGOS
                params.put(35, "");                                 //MEDIO PAGO
                params.put(36, "");                                 //OTRO MEDIO PAGO
                params.put(37, "");                                 //MONTO MAXIMO
                params.put(38, Miscellaneous.validStr(item.getAval().getHoraLocalizacion()));            //HORARIO LOCALIZACION
                params.put(39, "");                                 //ACTIVOS OBSERVABLES
                params.put(40, Miscellaneous.validStr(item.getAval().getTelCasa()));                     //TEL CASA
                params.put(41, Miscellaneous.validStr(item.getAval().getTelCelular()));                  //TEL CELULAR
                params.put(42, Miscellaneous.validStr(item.getAval().getTelMensaje()));                  //TEL MENSAJES
                params.put(43, Miscellaneous.validStr(item.getAval().getTelTrabajo()));                  //TEL TRABAJO
                params.put(44, Miscellaneous.validStr(item.getAval().getEmail()));                       //EMAIL
                params.put(45, "");                                 //FOTO FACHADA
                params.put(46, Miscellaneous.validStr(item.getAval().getReferencia()));                 //REF DOMICILIARIA
                params.put(47, "");                                 //FIRMA
                params.put(48, "0");                                //ESTATUS RECHAZO
                params.put(49, "");                                 //COMENTARIO RECHAZO
                params.put(50, "0");                                //ESTATUS RECHAZO

                dBhelper.saveDatosAval(db, params, 2);

                //Inserta registro de direccion del referencia
                params = new HashMap<>();
                params.put(0, "REFERENCIA");                                                 //TIPO DIRECCION
                params.put(1, "");                                                           //LATITUD
                params.put(2, "");                                                           //LONGITUD
                params.put(3, Miscellaneous.validStr(item.getReferencia().getCalle()));      //CALLE
                params.put(4, Miscellaneous.validStr(item.getReferencia().getNoExterior())); //NO EXTERIOR
                params.put(5, Miscellaneous.validStr(item.getReferencia().getNoInterior())); //NO INTERIOR
                params.put(6, Miscellaneous.validStr(item.getReferencia().getNoManzana()));  //MANZANA
                params.put(7, Miscellaneous.validStr(item.getReferencia().getNoLote()));     //LOTE
                params.put(8, (item.getReferencia().getCodigoPostal() == 0) ? "" : String.valueOf(item.getReferencia().getCodigoPostal()));                                                     //CP
                params.put(9, Miscellaneous.GetColonia(ctx, Miscellaneous.validInt(item.getReferencia().getColoniaId()))); //COLONIA
                params.put(10, Miscellaneous.validStr(item.getReferencia().getCiudad()));    //CIUDAD
                params.put(11, Miscellaneous.GetLocalidad(ctx, Miscellaneous.validInt(item.getReferencia().getLocalidadId()))); //LOCALIDAD
                params.put(12, Miscellaneous.GetMunicipio(ctx, Miscellaneous.validInt(item.getReferencia().getMunicipioId())));                                                    //MUNICIPIO
                params.put(13, Miscellaneous.GetEstado(ctx, Miscellaneous.validInt(item.getReferencia().getEstadoId())));                                                    //ESTADO

                id_direccion_ref = dBhelper.saveDirecciones(db, params, 2);

                //Inserta registro de referencia
                params = new HashMap<>();
                params.put(0, String.valueOf(id));                                                     //ID SOLICITUD
                params.put(1, Miscellaneous.validStr(item.getReferencia().getNombre().toUpperCase())); //NOMBRE
                params.put(2, Miscellaneous.validStr(item.getReferencia().getPaterno().toUpperCase()));//PATERNO
                params.put(3, Miscellaneous.validStr(item.getReferencia().getMaterno().toUpperCase()));//MATERNO
                params.put(4, Miscellaneous.validStr(item.getReferencia().getFechaNacimiento()));      //FECHA NACIMIENTO
                params.put(5, String.valueOf(id_direccion_ref));                                       //DIRECCION ID
                params.put(6, Miscellaneous.validStr(item.getReferencia().getTelCelular()));           //TEL_CELULAR
                params.put(7, "0");                                                                    //ESTATUS COMPLETADO
                params.put(8, "");                                                                     //COMENTARIO RECHAZO

                dBhelper.saveReferencia(db, params, 2);

                //Inserta registro de croquis
                params = new HashMap<>();
                params.put(0, String.valueOf(id));                  //ID SOLICITUD
                params.put(1, "");                                  //CALLE PRINCIPAL
                params.put(2, "");                                  //LATERAL UNO
                params.put(3, "");                                  //LATERAL DOS
                params.put(4, "");                                  //CALLE TRASERA
                params.put(5, "");                                  //REFERENCIAS
                params.put(6, "0");                                 //ESTATUS COMPLETADO
                params.put(7, "");                                  //COMENTARIO RECHAZO

                dBhelper.saveCroquisInd(db, params, 2);

                //Inserta registro de politicas
                params = new HashMap<>();
                params.put(0, String.valueOf(id));                  //ID SOLICITUD
                params.put(1, "0");                                 //PROPIERATIO REAL
                params.put(2, "0");                                 //PROVEEDOR RECURSOS
                params.put(3, "0");                                 //PERSONA POLITICA
                params.put(4, "0");                                 //ESTATUS COMPLETADO

                dBhelper.savePoliticasInd(db, params, 2);

                //Inseta registro de documentos
                params = new HashMap<>();
                params.put(0, String.valueOf(id));       //ID SOLICITUD
                params.put(1, "");                      //INE FRONTAL
                params.put(2, "");                      //INE REVERSO
                params.put(3, "");                      //CURP
                params.put(4, "");                      //COMPROBANTE
                params.put(5, "");                      //CODIGO BARRAS
                params.put(6, "");                      //FIRMA ASESOR
                params.put(7, "0");                     //ESTATUS COMPLETADO

                dBhelper.saveDocumentosClientes(db, params, 2);

                ContentValues cv = new ContentValues();
                cv.put("descargado", 1);
                db.update(TBL_PRESTAMOS_TO_RENOVAR, cv, "prestamo_id = ? AND cliente_id = ?", new String[]{prestamoId.toString(), clienteId.toString()});
            } else {
                try {
                    Log.e("ERROR " + response.code(), response.errorBody().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Log.e("ERROR " + response.code(), response.message());
            }
            return "";
        }
    }

    public class RegistrarDatosRenovacionGpo implements Callable<String> {

        private final Context ctx;
        private final SessionManager session;
        private final Integer grupoId;
        private final String nombreGpo;
        private final DBhelper dBhelper;
        private final SQLiteDatabase db;

        public RegistrarDatosRenovacionGpo(Context ctx, SessionManager session, Integer grupoId, String nombreGpo) {
            this.ctx = ctx;
            this.session = session;
            this.grupoId = grupoId;
            this.nombreGpo = nombreGpo;
            this.dBhelper = DBhelper.getInstance(ctx);
            this.db = this.dBhelper.getWritableDatabase();
        }

        @Override
        public String call() {

            Call<MRenovacionGrupal> call = prestamosPorRenovarRemoteDatasource.getPrestamoGrupalToRenovar(grupoId);
            try {
                Response<MRenovacionGrupal> response = executorUtil.process(call);
                if (response.code() == 200) {
                    MRenovacionGrupal item = response.body();

                    // Crea la solicitud de credito grupal
                    HashMap<Integer, String> params = new HashMap<>();
                    params.put(0, ctx.getString(com.sidert.sidertmovil.R.string.vol_solicitud));   //VOL SOLICITUD
                    params.put(1, session.getUser().get(9));                 //USUARIO ID
                    params.put(2, "2");                                      //TIPO SOLICITUD
                    params.put(3, "0");                                      //ID ORIGINACION
                    params.put(4, nombreGpo);                               //NOMBRE
                    params.put(5, Miscellaneous.ObtenerFecha(TIMESTAMP));   //FECHA INICIO
                    params.put(6, "");                                       //FECHA TERMINO
                    params.put(7, "");                                       //FECHA ENVIO
                    params.put(8, Miscellaneous.ObtenerFecha(TIMESTAMP));   //FECHA DISPOSITIVO
                    params.put(9, "");                                      //FECHA GUARDADO
                    params.put(10, "0");                                    //ESTATUS
                    params.put(11, String.valueOf(item.getPrestamo().getPrestamodId())); //prestamo_id

                    Long id_solicitud = dBhelper.saveSolicitudes(db, params, 2);

                    //Inserta registro de datos del credito
                    params = new HashMap<>();
                    params.put(0, String.valueOf(id_solicitud));
                    params.put(1, nombreGpo);
                    params.put(2, "");
                    params.put(3, "");
                    params.put(4, "");
                    params.put(5, "");
                    params.put(6, "");
                    params.put(7, "0");
                    params.put(8, "");
                    params.put(9, String.valueOf(item.getPrestamo().getNumCiclo()));
                    params.put(10, grupoId.toString());

                    Long id_credito = dBhelper.saveDatosCreditoGpoRen(db, params, 1);

                    for (MRenovacionGrupal.Integrante integrante : item.getIntegrantes()) {
                        MRenovacionGrupal.Cliente cliente = integrante.getCliente();
                        MRenovacionGrupal.Negocio negocio = integrante.getNegocio();
                        MRenovacionGrupal.Conyuge conyuge = integrante.getConyuge();

                        //Inserta registro de integrante
                        params = new HashMap<>();
                        params.put(0, String.valueOf(id_credito));                              //ID CREDITO
                        params.put(1, String.valueOf(cliente.getTipoIntegrante()));             //CARGO
                        params.put(2, Miscellaneous.validStr(cliente.getNombre()).trim().toUpperCase()); //NOMBRE(S)
                        params.put(3, Miscellaneous.validStr(cliente.getPaterno()).trim().toUpperCase());//PATERNO
                        params.put(4, Miscellaneous.validStr(cliente.getMaterno()).trim().toUpperCase());//MATERNO
                        params.put(5, Miscellaneous.validStr(cliente.getFechaNacimiento().trim()));                            //FECHA NACIMIENTO
                        params.put(6, Miscellaneous.GetEdad(cliente.getFechaNacimiento()));     //EDAD
                        params.put(7, String.valueOf(cliente.getGenero()));                     //GENERO
                        params.put(8, Miscellaneous.GetEstado(ctx, cliente.getEstadoNacimiento())); //ESTADO NACIMIENTO
                        params.put(9, Miscellaneous.validStr(cliente.getRfc()));                //RFC
                        params.put(10, Miscellaneous.validStr(cliente.getCurp()));               //CURP
                        params.put(11, "");                                                     //CURP DIGITO VERI
                        params.put(12, Miscellaneous.GetTipoIdentificacion(ctx, cliente.getIdentificacionTipoId())); //TIPO IDENTIFICACION
                        params.put(13, Miscellaneous.validStr(cliente.getNoIdentificacion()));  //NO IDENTIFICACION
                        params.put(14, Miscellaneous.GetEstudio(ctx, cliente.getEstudioNivelId())); //NIVEL ESTUDIO
                        params.put(15, Miscellaneous.GetOcupacion(ctx, cliente.getOcupacionId()));  //OCUPACION
                        params.put(16, Miscellaneous.GetEstadoCivil(ctx, cliente.getEstadoCivilId())); //ESTADO CIVIL
                        params.put(17, String.valueOf(cliente.getRegimenBienId()));              //BIENES
                        params.put(18, "0");                                                    //ESTATUS RECHAZO
                        params.put(19, "");                                                     //COMENTARIO RECHAZO
                        params.put(20, "0");                                                    //ESTATUS COMPLETO
                        params.put(21, "0");                                                    //ID SOLICITUD INTEGRANTE
                        params.put(22, "0");                                                    //IS NUEVO
                        params.put(23, String.valueOf(integrante.getClienteId()));              //CLIENTE ID
                        params.put(24, String.valueOf(integrante.getCiclo()));
                        params.put(25, integrante.getMontoPrestamoAnterior());

                        Long id = dBhelper.saveIntegrantesGpoRen(db, params);

                        //Inserta registro de datos telefonicos
                        params = new HashMap<>();
                        params.put(0, String.valueOf(id));              //ID INTEGRANTE
                        params.put(1, Miscellaneous.validStr(cliente.getTelCasa()));            //TEL CASA
                        params.put(2, Miscellaneous.validStr(cliente.getTelCelular()));         //TEL CELULAR
                        params.put(3, Miscellaneous.validStr(cliente.getTelMensaje()));         //TEL MENSAJES
                        params.put(4, Miscellaneous.validStr(cliente.getTelTrabajo()));         //TEL TRABAJO
                        params.put(5, "0");                                                     //ESTATUS COMPLETADO

                        dBhelper.saveDatosTelefonicos(db, params, 2);

                        //Inserta registro de datos domicilio
                        params = new HashMap<>();
                        params.put(0, String.valueOf(id));                                          //ID INTEGRANTE
                        params.put(1, Miscellaneous.validStr(cliente.getLatitud()));                //LATITUD
                        params.put(2, Miscellaneous.validStr(cliente.getLongitud()));               //LONGITUD
                        params.put(3, Miscellaneous.validStr(cliente.getCalle()));                  //CALLE
                        params.put(4, Miscellaneous.validStr(cliente.getNoExterior()));             //NO_EXTERIOR
                        params.put(5, Miscellaneous.validStr(cliente.getNoInterior()));             //NO INTERIOR
                        params.put(6, Miscellaneous.validStr(cliente.getNoManzana()));              //MANZANA
                        params.put(7, Miscellaneous.validStr(cliente.getNoLote()));                 //LOTE
                        params.put(8, String.valueOf(Miscellaneous.validInt(cliente.getCodigoPostal()))); //CP
                        params.put(9, Miscellaneous.GetColonia(ctx, Miscellaneous.validInt(cliente.getColoniaId())));//COLONIA
                        params.put(10, Miscellaneous.validStr(cliente.getCiudad()));                //CIUDAD
                        params.put(11, Miscellaneous.GetLocalidad(ctx, Miscellaneous.validInt(cliente.getLocalidadId())));  //LOCALIDAD
                        params.put(12, Miscellaneous.GetMunicipio(ctx, Miscellaneous.validInt(cliente.getMunicipioId())));  //MUNICIPIO
                        params.put(13, Miscellaneous.GetEstado(ctx, Miscellaneous.validInt(cliente.getEstadoId())));//ESTADO
                        params.put(14, Miscellaneous.GetViviendaTipo(ctx, Miscellaneous.validInt(cliente.getViviendaTipoId()))); //TIPO VIVIENDA
                        params.put(15, Miscellaneous.GetParentesco(ctx, Miscellaneous.validInt(cliente.getParentescoId()))); //PARENTESCO
                        params.put(16, Miscellaneous.validStr(cliente.getOtroTipoVivienda()));       //OTRO TIPO VIVIENDA
                        if (!Miscellaneous.validStr(cliente.getTiempoVivirSitio()).isEmpty())
                            params.put(17, cliente.getTiempoVivirSitio());
                        else
                            params.put(17, "0");                                                     //TIEMPO VIVIR SITIO
                        params.put(18, "");                                                          //FOTO FACHADA
                        params.put(19, Miscellaneous.validStr(cliente.getReferencia()));             //REF DOMICILIARIA
                        params.put(20, "0");                                                         //ESTATUS COMPLETO
                        params.put(21, "");                                                          //DEPENDIENTES ECONOMICOS

                        dBhelper.saveDatosDomicilio(db, params, 2);

                        //Inserta registro de negocio
                        params = new HashMap<>();
                        params.put(0, String.valueOf(id));                                                          //ID INTEGRANTE
                        params.put(1, Miscellaneous.validStr(negocio.getNombre()));                                 //NOMBRE
                        params.put(2, Miscellaneous.validStr(negocio.getLatitud()));                                //LATITID
                        params.put(3, Miscellaneous.validStr(negocio.getLongitud()));                               //LONGITUD
                        params.put(4, Miscellaneous.validStr(negocio.getCalle()));                                  //CALLE
                        params.put(5, Miscellaneous.validStr(negocio.getNoExterior()));                             //NO EXTERIOR
                        params.put(6, Miscellaneous.validStr(negocio.getNoInterior()));                             //NO INTERIOR
                        params.put(7, Miscellaneous.validStr(negocio.getNoManzana()));                              //MANZANA
                        params.put(8, Miscellaneous.validStr(negocio.getNoLote()));                                 //LOTE
                        params.put(9, String.valueOf(Miscellaneous.validInt(negocio.getCodigoPostal())));           //CP
                        params.put(10, Miscellaneous.GetColonia(ctx, Miscellaneous.validInt(negocio.getColoniaId())));//COLONIA
                        params.put(11, Miscellaneous.validStr(negocio.getCiudad()));                                //CIUDAD
                        params.put(12, Miscellaneous.GetLocalidad(ctx, Miscellaneous.validInt(negocio.getLocalidadId())));//LOCALIDAD
                        params.put(13, Miscellaneous.GetMunicipio(ctx, Miscellaneous.validInt(negocio.getMunicipioId())));//MUNICIPIO
                        params.put(14, Miscellaneous.GetEstado(ctx, Miscellaneous.validInt(negocio.getEstadoId())));//ESTADO
                        params.put(15, Miscellaneous.GetDestinoCredito(ctx, Miscellaneous.validInt(negocio.getDestinoCreditoId())));//DESTINO CREDITO
                        params.put(16, Miscellaneous.validStr(negocio.getOtroDestinoCredito()));                    //OTRO DESTINO CREDITO
                        params.put(17, Miscellaneous.GetOcupacion(ctx, Miscellaneous.validInt(negocio.getOcupacionId())));//OCUPACION
                        params.put(18, Miscellaneous.GetSector(ctx, Miscellaneous.validInt(negocio.getSectorId())));//ACTIVIDAD ECONOMICA
                        params.put(19, String.valueOf(Miscellaneous.validInt(negocio.getAntiguedad())));            //ANTIGUEDA
                        params.put(20, "");                                                                         //INGRESO MENSUAL
                        params.put(21, "");                                                                         //INGRESOS OTROS
                        params.put(22, "");                                                                         //GASTO MENSUAL
                        params.put(23, "");                                                                         //CAPACIDAD DE PAGO
                        params.put(24, "");                                                                         //MONTO MAXIMO
                        params.put(25, "");                                                                         //MEDIOS PAGO
                        params.put(26, "");                                                                         //OTRO MEDIO DE PAGO
                        params.put(27, "");                                                                         //NUM OPERACIONES MENSUALES
                        params.put(28, "");                                                                         //NUM OPERACIONES MENSUALES EFECTIVO
                        params.put(29, "");                                                                         //FOTO FACHADA
                        params.put(30, "");                                                                         //REFERENCIA DOMICILIARIA
                        params.put(31, "0");                                                                        //ESTATUS RECHAZO
                        params.put(32, "");                                                                         //COMENTARIO RECHAZADO
                        params.put(33, "0");                                                                        //ESTATUS COMPLETADO

                        dBhelper.saveDatosNegocioGpo(db, params, 2);

                        //Inserta registro del conyuge
                        params = new HashMap<>();
                        params.put(0, String.valueOf(id));                                        //ID INTEGRANTE
                        params.put(1, Miscellaneous.validStr(conyuge.getNombre()));               //NOMBRE
                        params.put(2, Miscellaneous.validStr(conyuge.getPaterno()));              //PATERNO
                        params.put(3, Miscellaneous.validStr(conyuge.getMaterno()));              //MATERNO
                        params.put(4, Miscellaneous.validStr(conyuge.getNacionalidad()));         //NACIONALIDAD
                        params.put(5, Miscellaneous.GetOcupacion(ctx, negocio.getOcupacionId())); //OCUPACION
                        params.put(6, Miscellaneous.validStr(conyuge.getCalle()));                //CALLE
                        params.put(7, Miscellaneous.validStr(conyuge.getNoExterior()));           //NO EXTERIOR
                        params.put(8, Miscellaneous.validStr(conyuge.getNoInterior()));           //NO INTERIOR
                        params.put(9, Miscellaneous.validStr(conyuge.getNoManzana()));            //MANZANA
                        params.put(10, Miscellaneous.validStr(conyuge.getNoLote()));              //LOTE
                        params.put(11, String.valueOf(conyuge.getCodigoPostal()));                //CP
                        params.put(12, Miscellaneous.GetColonia(ctx, conyuge.getColoniaId()));    //COLONIA
                        params.put(13, Miscellaneous.validStr(conyuge.getCiudad()));              //CIUDAD
                        params.put(14, Miscellaneous.GetLocalidad(ctx, conyuge.getLocalidadId()));//LOCALIDAD
                        params.put(15, Miscellaneous.GetMunicipio(ctx, conyuge.getMunicipioId()));//MUNICIPIO
                        params.put(16, Miscellaneous.GetEstado(ctx, conyuge.getEstadoId()));      //ESTADO
                        params.put(17, "");                                                       //INGRESO MENSUAL
                        params.put(18, "");                                                       //GASTO MENSUAL
                        params.put(19, Miscellaneous.validStr(conyuge.getTelCasa()));             //TEL CASA
                        params.put(20, Miscellaneous.validStr(conyuge.getTelCelular()));          //TEL CELULAR
                        params.put(21, "0");                                                      //ESTATUS COMPLETADO

                        dBhelper.saveDatosConyugeGpo(db, params, 2);

                        //Inserta otros datos del integrante
                        params = new HashMap<>();
                        params.put(0, String.valueOf(id));                                                  //ID INTEGRANTE
                        params.put(1, "");                                                                  //CLASIFICACION RIESGO
                        params.put(2, Miscellaneous.GetMedioContacto(ctx, cliente.getMedioContactoId()));   //MEDIO CONTACTO
                        params.put(3, Miscellaneous.validStr(cliente.getEmail()));                          //EMAIL
                        params.put(4, Miscellaneous.validStr(cliente.getEstadoCuenta()));                   //ESTADO CUENTA
                        params.put(5, "2");                                                                 //ESTATUS INTEGRANTE
                        params.put(6, "");                                                                  //MONTO SOLICITADO
                        if (cliente.getTipoIntegrante() == 3)
                            params.put(7, "1");                                                             //CASA REUNION
                        else
                            params.put(7, "0");                                                             //CASA REUNION
                        params.put(8, "");                                                                  //FIRMA
                        params.put(9, "0");                                                                 //ESTATUS COMPLETADO

                        dBhelper.saveDatosOtrosGpo(db, params, 2);

                        //Inserta registro de croquis
                        params = new HashMap<>();
                        params.put(0, String.valueOf(id));                  //ID SOLICITUD
                        params.put(1, "");                                  //CALLE PRINCIPAL
                        params.put(2, "");                                  //LATERAL UNO
                        params.put(3, "");                                  //LATERAL DOS
                        params.put(4, "");                                  //CALLE TRASERA
                        params.put(5, "");                                  //REFERENCIAS
                        params.put(6, "0");                                 //ESTATUS COMPLETADO

                        dBhelper.saveCroquisGpo(db, params, 2);

                        //Inserta registro de politicas de integrante
                        params = new HashMap<>();
                        params.put(0, String.valueOf(id));      //ID INTEGRANTE
                        params.put(1, "0");                     //PROPIETARIO REAL
                        params.put(2, "0");                     //PROVEEDOR RECURSOS
                        params.put(3, "0");                     //PERSONA POLITICA
                        params.put(4, "0");                     //ESTATUS COMPLETADO

                        dBhelper.savePoliticasIntegrante(db, params, 2);

                        //Inserta registro de documentos de integrante
                        params = new HashMap<>();
                        params.put(0, String.valueOf(id));      //ID INTEGRANTE
                        params.put(1, "");                      //INE FRONTAL
                        params.put(2, "");                      //INE REVERSO
                        params.put(3, "");                      //CURP
                        params.put(4, "");                      //COMPROBANTE
                        params.put(5, "0");                     //ESTATUS COMPLETADO

                        dBhelper.saveDocumentosIntegrante(db, params, 2);
                    }

                    ContentValues cv = new ContentValues();
                    cv.put("descargado", 1);
                    db.update(TBL_PRESTAMOS_TO_RENOVAR, cv, "grupo_id = ?", new String[]{grupoId.toString()});
                } else {
                    try {
                        Log.e("ERROR " + response.code(), response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Log.e("ERROR " + response.code(), response.message());
                }

            } catch (ExecutionException | InterruptedException e) {
            }
            return "";
        }
    }


    public void getPrestamosToRenovar() {

        Call<List<MPrestamosRenovar>> call = prestamosPorRenovarRemoteDatasource.getPrestamoToRenovar();

        try {
            Response<List<MPrestamosRenovar>> response = executorUtil.process(call);
            Timber.tag(TAG).i("Codigo de respuesta %s", response.code());

            if (response.code() == 200) {
                List<MPrestamosRenovar> prestamos = response.body();
                if (prestamos != null) {
                    if (prestamos.size() > 0) {
                        for (MPrestamosRenovar item : prestamos) {
                            String asesorId = this.sessionManager.getUser().get(0);
                            if (item.getTipoPrestamo().equals("INDIVIDUAL")) {
                                Optional<PrestamosToRenovar> prestamoToRenovar = executorUtil.runTaskInThread(() -> prestamosToRenovarDao.findByAsesorIdAndPrestamoIdAndClienteId(asesorId, item.getPrestamoId(), String.valueOf(item.getClienteId())));
                                if (!prestamoToRenovar.isPresent()) {
                                    executorUtil.runTaskInThread(() -> {
                                        PrestamosToRenovar prestamosToRenovar = this.prestamoToRenovarMapper.remoteToLocal(item);
                                        this.prestamosToRenovarDao.insert(prestamosToRenovar);
                                    });
                                }
                            } else {
                                Optional<PrestamosToRenovar> prestamoToRenovarGrupal = executorUtil.runTaskInThread(() -> prestamosToRenovarDao.findByAsesorIdAndGrupoIdAndClienteId(asesorId, item.getGrupoId(), String.valueOf(item.getPrestamoId())));
                                if (!prestamoToRenovarGrupal.isPresent()) {
                                    executorUtil.runTaskInThread(() -> {
                                        PrestamosToRenovar prestamosToRenovar = this.prestamoToRenovarMapper.remoteToLocal(item);
                                        this.prestamosToRenovarDao.insert(prestamosToRenovar);
                                    });
                                }
                            }
                        }
                        getDatosRenovar();
                    }
                }
            }
        } catch (Exception ex) {
            Timber.tag(TAG).e(ex);
        }
    }

    private void getDatosRenovar() throws ExecutionException, InterruptedException, TimeoutException {

        List<PrestamosToRenovar> prestamosToRenovar = this.executorUtil.runTaskInThread(this.prestamosToRenovarDao::getAll);
        for (PrestamosToRenovar toRenovar : prestamosToRenovar) {
            List<SolicitudesRen> solicitudDeRonovacion = this.executorUtil.runTaskInThread(() -> this.solicitudesRenDao.findByNombreAndPrestamoId(toRenovar.getClienteNombre(), toRenovar.getPrestamoId()));

            if (solicitudDeRonovacion.size() == 1) {
                SolicitudesRen solicitudesRen = solicitudDeRonovacion.get(0);
                Long prestamoRenovarId = toRenovar.getId();

                Integer prestamoId = solicitudesRen.getPrestamoId();
                prestamoId = prestamoId == null ? 0 : prestamoId;

                if (prestamoId == 0) {
                    solicitudesRen.setPrestamoId(Math.toIntExact(prestamoRenovarId));
                    this.executorUtil.runTaskInThread(() -> {
                        this.solicitudesRenDao.update(solicitudesRen);
                    });
                }

            } else {
                //Long prestamoRenovarId = toRenovar.getId();
                String clienteNombre = toRenovar.getClienteNombre();
                for (int i = 0; i < solicitudDeRonovacion.size(); i++) {
                    SolicitudesRen solicitudesRen = solicitudDeRonovacion.get(i);
                    int index = i;
                    Optional<PrestamosToRenovar> prestamoToRenovarTempOp = this.executorUtil.runTaskInThread(() -> prestamosToRenovarDao.findByClienteNombreAndPosition(clienteNombre, index));
                    if (prestamoToRenovarTempOp.isPresent()) {
                        this.executorUtil.runTaskInThread(() -> {
                            PrestamosToRenovar prestamoToRenovarTemp = prestamoToRenovarTempOp.get();
                            solicitudesRen.setPrestamoId(prestamoToRenovarTemp.getPrestamoId());
                            solicitudesRenDao.update(solicitudesRen);
                        });
                    }
                }
            }
        }

        List<PrestamosToRenovar> prestamosToRenovarList = this.executorUtil.runTaskInThread(() -> this.prestamosToRenovarDao.findByDescargoStatus(0));
        for (PrestamosToRenovar toRenovar : prestamosToRenovarList) {
            Integer prestamoToRenovarRemoteId = toRenovar.getPrestamoId();
            Integer prestamoToRenoverClienteId = toRenovar.getClienteId();
            int grupo = Integer.parseInt(toRenovar.getGrupoId());
            String nombreGrupo = toRenovar.getClienteNombre();
            if (grupo == 1) {
                registraRenovacion(prestamoToRenovarRemoteId, prestamoToRenoverClienteId);
            } else {
                registraRenovacionGrupal(grupo, nombreGrupo);
            }
        }
    }

    private void registraRenovacionGrupal(Integer grupoPorRenovarId, String nombreGrupoPorRenovar) {
        Call<MRenovacionGrupal> prestamoGrupalToRenovar = this.prestamosPorRenovarRemoteDatasource.getPrestamoGrupalToRenovar(grupoPorRenovarId);
        try {
            Response<MRenovacionGrupal> response = executorUtil.process(prestamoGrupalToRenovar);
            if (response.code() == 200) {
                executorUtil.runTaskInThread(() -> {
                    MRenovacionGrupal item = response.body();
                    if (item == null) return;
                    String volumenSolicitud = this.sidertMovilApplication.getString(R.string.vol_solicitud);
                    Integer asesorId = Integer.parseInt(this.sessionManager.getUser().get(0));

                    SolicitudesRen solicitudesRen = new SolicitudesRen();
                    solicitudesRen.setVolSolicitud(volumenSolicitud);
                    solicitudesRen.setUsuarioId(asesorId);
                    solicitudesRen.setTipoSolicitud(2);
                    solicitudesRen.setIdOriginacion(0);
                    solicitudesRen.setNombre(nombreGrupoPorRenovar);
                    solicitudesRen.setFechaInicio(Miscellaneous.ObtenerFecha(TIMESTAMP));
                    solicitudesRen.setFechaTermino("");
                    solicitudesRen.setFechaEnvio("");
                    solicitudesRen.setFechaDispositivo(Miscellaneous.ObtenerFecha(TIMESTAMP));
                    solicitudesRen.setFechaGuardado("");
                    solicitudesRen.setEstatus(0);
                    solicitudesRen.setPrestamoId(item.getPrestamo().getPrestamodId());
                    Long solicitudId = this.solicitudesRenDao.insert(solicitudesRen);

                    //Inserta registro de datos del credito
                    CreditoGpoRen creditoGpoRen = new CreditoGpoRen();
                    creditoGpoRen.setIdSolicitud(Math.toIntExact(solicitudId));
                    creditoGpoRen.setNombreGrupo(nombreGrupoPorRenovar);
                    creditoGpoRen.setPlazo("");
                    creditoGpoRen.setPeriodicidad("");
                    creditoGpoRen.setFechaDesembolso("");
                    creditoGpoRen.setDiaDesembolso("");
                    creditoGpoRen.setHoraVisita("");
                    creditoGpoRen.setEstatusCompletado(0);
                    creditoGpoRen.setObservaciones("");
                    creditoGpoRen.setCiclo("");
                    creditoGpoRen.setGrupoId(String.valueOf(grupoPorRenovarId));
                    Long creditoId = this.creditoGpoRenDao.insert(creditoGpoRen);

                    for (MRenovacionGrupal.Integrante integrante : item.getIntegrantes()) {
                        MRenovacionGrupal.Cliente cliente = integrante.getCliente();
                        MRenovacionGrupal.Negocio negocio = integrante.getNegocio();
                        MRenovacionGrupal.Conyuge conyuge = integrante.getConyuge();

                        Optional<Estados> optionalEstados = this.estadosDao.findById(cliente.getEstadoNacimiento().longValue());
                        Optional<IdentificacionTipo> optionalIdentificacionTipo = this.identificacionTipoDao.findById(cliente.getIdentificacionTipoId().longValue());
                        Optional<NivelesEstudios> optionalNivelesEstudios = this.nivelesEstudiosDao.findById(cliente.getEstudioNivelId().longValue());
                        Optional<Ocupaciones> optionalOcupaciones = this.ocupacionesDao.findById(cliente.getOcupacionId().longValue());
                        Optional<EstadosCiviles> optionalEstadosCiviles = this.estadosCivilesDao.findById(cliente.getEstadoCivilId().longValue());

                        //Inserta registro de integrante
                        IntegrantesGpoRen integrantesGpoRen = new IntegrantesGpoRen();
                        integrantesGpoRen.setIdCredito(creditoId.intValue());
                        integrantesGpoRen.setCargo(cliente.getTipoIntegrante());
                        integrantesGpoRen.setNombre(Miscellaneous.validStr(cliente.getNombre()).trim().toUpperCase());
                        integrantesGpoRen.setPaterno(Miscellaneous.validStr(cliente.getPaterno()).trim().toUpperCase());
                        integrantesGpoRen.setMaterno(Miscellaneous.validStr(cliente.getMaterno()).trim().toUpperCase());
                        integrantesGpoRen.setFechaNacimiento(Miscellaneous.validStr(cliente.getFechaNacimiento().trim()));
                        integrantesGpoRen.setEdad(Miscellaneous.GetEdad(cliente.getFechaNacimiento()));
                        integrantesGpoRen.setGenero(cliente.getGenero());
                        integrantesGpoRen.setRfc(Miscellaneous.validStr(cliente.getRfc()));
                        integrantesGpoRen.setCurp(Miscellaneous.validStr(cliente.getCurp()));
                        integrantesGpoRen.setCurpDigitoVeri("");
                        integrantesGpoRen.setNoIdentificacion(Miscellaneous.validStr(cliente.getNoIdentificacion()));
                        integrantesGpoRen.setBienes(cliente.getRegimenBienId());
                        integrantesGpoRen.setEstatusRechazo(0);
                        integrantesGpoRen.setComentarioRechazo("");
                        integrantesGpoRen.setEstatusCompletado(0);
                        integrantesGpoRen.setIdSolicitudIntegrante(0);
                        integrantesGpoRen.setIsNuevo(0);
                        integrantesGpoRen.setClienteId(String.valueOf(integrante.getClienteId()));
                        integrantesGpoRen.setCiclo(integrante.getCiclo());
                        integrantesGpoRen.setMontoPrestamoAnterior(integrante.getMontoPrestamoAnterior());

                        optionalEstados.ifPresent(estados -> integrantesGpoRen.setEstadoNacimiento(estados.getEstadoNombre()));
                        optionalIdentificacionTipo.ifPresent(identificacionTipo -> integrantesGpoRen.setTipoIdentificacion(identificacionTipo.getNombre()));
                        optionalNivelesEstudios.ifPresent(nivelesEstudios -> integrantesGpoRen.setNivelEstudio(nivelesEstudios.getNombre()));
                        optionalOcupaciones.ifPresent(ocupaciones -> integrantesGpoRen.setOcupacion(ocupaciones.getOcupacionNombre()));
                        optionalEstadosCiviles.ifPresent(estadosCiviles -> integrantesGpoRen.setEstadoCivil(estadosCiviles.getNombre()));

                        Long nuevoIntegranteId = this.integrantesGpoRenDao.insert(integrantesGpoRen);

                        //Inserta registro de datos telefonicos
                        TelefonosIntegranteRen telefonosIntegranteRen = new TelefonosIntegranteRen();
                        telefonosIntegranteRen.setIdIntegrante(nuevoIntegranteId.intValue());
                        telefonosIntegranteRen.setTelCasa(Miscellaneous.validStr(cliente.getTelCasa()));
                        telefonosIntegranteRen.setTelCelular(Miscellaneous.validStr(cliente.getTelCelular()));
                        telefonosIntegranteRen.setTelMensaje(Miscellaneous.validStr(cliente.getTelMensaje()));
                        telefonosIntegranteRen.setTelTrabajo(Miscellaneous.validStr(cliente.getTelTrabajo()));
                        telefonosIntegranteRen.setEstatusCompletado(0);
                        this.telefonosIntegranteRenDao.insert(telefonosIntegranteRen);

                        //Inserta registro de datos domicilio
                        Optional<CatColonias> optionalCatColoniasDomicilio = this.catColoniasDao.findById(Long.valueOf(cliente.getColoniaId()));
                        Optional<Localidades> optionalLocalidadesDomicilio = this.localidadesDao.findById(Long.valueOf(cliente.getLocalidadId()));
                        Optional<Municipios> optionalMunicipiosDomicilio = this.municipiosDao.findById(Long.valueOf(cliente.getMunicipioId()));
                        Optional<Estados> optionalEstadosDomicilio = this.estadosDao.findById(Long.valueOf(cliente.getEstadoId()));
                        Optional<ViviendaTipos> optionalViviendaTiposDomicilio = this.viviendaTiposDao.findById(Long.valueOf(cliente.getViviendaTipoId()));
                        Optional<Parentescos> optionalParenescosDomicilio = this.parentescosDao.findById(Long.valueOf(cliente.getParentescoId()));

                        DomicilioIntegranteRen domicilioIntegranteRen = new DomicilioIntegranteRen();
                        domicilioIntegranteRen.setIdIntegrante(nuevoIntegranteId.intValue());                                        //ID INTEGRANTE
                        domicilioIntegranteRen.setLatitud(Miscellaneous.validStr(cliente.getLatitud()));                //LATITUD
                        domicilioIntegranteRen.setLongitud(Miscellaneous.validStr(cliente.getLongitud()));               //LONGITUD
                        domicilioIntegranteRen.setCalle(Miscellaneous.validStr(cliente.getCalle()));                  //CALLE
                        domicilioIntegranteRen.setNoExterior(Miscellaneous.validStr(cliente.getNoExterior()));             //NO_EXTERIOR
                        domicilioIntegranteRen.setNoInterior(Miscellaneous.validStr(cliente.getNoInterior()));             //NO INTERIOR
                        domicilioIntegranteRen.setManzana(Miscellaneous.validStr(cliente.getNoManzana()));              //MANZANA
                        domicilioIntegranteRen.setLote(Miscellaneous.validStr(cliente.getNoLote()));                 //LOTE
                        domicilioIntegranteRen.setCp(String.valueOf(Miscellaneous.validInt(cliente.getCodigoPostal()))); //CP
                        domicilioIntegranteRen.setCiudad(Miscellaneous.validStr(cliente.getCiudad()));                //CIUDAD
                        domicilioIntegranteRen.setOtroTipoVivienda(Miscellaneous.validStr(cliente.getOtroTipoVivienda()));       //OTRO TIPO VIVIENDA
                        if (!Miscellaneous.validStr(cliente.getTiempoVivirSitio()).isEmpty())
                            domicilioIntegranteRen.setTiempoVivirSitio(cliente.getTiempoVivirSitio());
                        else
                            domicilioIntegranteRen.setTiempoVivirSitio("0");                                                     //TIEMPO VIVIR SITIO
                        domicilioIntegranteRen.setFotoFachada("");                                                          //FOTO FACHADA
                        domicilioIntegranteRen.setRefDomiciliaria(Miscellaneous.validStr(cliente.getReferencia()));             //REF DOMICILIARIA
                        domicilioIntegranteRen.setEstatusCompletado(0);                                                         //ESTATUS COMPLETO
                        domicilioIntegranteRen.setDependientes("");                                                          //DEPENDIENTES ECONOMICOS

                        optionalCatColoniasDomicilio.ifPresent(entity -> domicilioIntegranteRen.setColonia(entity.getColoniaNombre()));
                        optionalLocalidadesDomicilio.ifPresent(entity -> domicilioIntegranteRen.setLocalidad(entity.getNombre()));
                        optionalMunicipiosDomicilio.ifPresent(entity -> domicilioIntegranteRen.setMunicipio(entity.getMunicipioNombre()));
                        optionalEstadosDomicilio.ifPresent(entity -> domicilioIntegranteRen.setEstado(entity.getEstadoNombre()));
                        optionalViviendaTiposDomicilio.ifPresent(entity -> domicilioIntegranteRen.setTipoVivienda(entity.getNombre()));
                        optionalParenescosDomicilio.ifPresent(entity -> domicilioIntegranteRen.setParentesco(entity.getNombre()));

                        this.domicilioIntegranteRenDao.insert(domicilioIntegranteRen);

                        //Inserta registro de negocio
                        Optional<CatColonias> optionalCatColoniasNegocio = this.catColoniasDao.findById(Long.valueOf(negocio.getColoniaId()));
                        Optional<Localidades> optionalLocalidadesNegocio = this.localidadesDao.findById(Long.valueOf(negocio.getLocalidadId()));
                        Optional<Municipios> optionalMunicipiosNegocio = this.municipiosDao.findById(Long.valueOf(negocio.getMunicipioId()));
                        Optional<Estados> optionalEstadosNegocio = this.estadosDao.findById(Long.valueOf(negocio.getEstadoId()));
                        Optional<DestinosCredito> optionalDestinoCredito = this.destinosCreditoDao.findById(Long.valueOf(negocio.getDestinoCreditoId()));
                        Optional<Ocupaciones> optionalOcupacion = this.ocupacionesDao.findById(Long.valueOf(negocio.getOcupacionId()));
                        Optional<Sectores> optionalSector = this.sectoresDao.findById(Long.valueOf(negocio.getSectorId()));

                        NegocioIntegranteRen negocioIntegranteRen = new NegocioIntegranteRen();
                        negocioIntegranteRen.setIdIntegrante(nuevoIntegranteId.intValue());
                        negocioIntegranteRen.setNombre(Miscellaneous.validStr(negocio.getNombre()));
                        negocioIntegranteRen.setLatitud(Miscellaneous.validStr(negocio.getLatitud()));                                //LATITID
                        negocioIntegranteRen.setLongitud(Miscellaneous.validStr(negocio.getLongitud()));                               //LONGITUD
                        negocioIntegranteRen.setCalle(Miscellaneous.validStr(negocio.getCalle()));                                  //CALLE
                        negocioIntegranteRen.setNoExterior(Miscellaneous.validStr(negocio.getNoExterior()));                             //NO EXTERIOR
                        negocioIntegranteRen.setNoInterior(Miscellaneous.validStr(negocio.getNoInterior()));                             //NO INTERIOR
                        negocioIntegranteRen.setManzana(Miscellaneous.validStr(negocio.getNoManzana()));                              //MANZANA
                        negocioIntegranteRen.setLote(Miscellaneous.validStr(negocio.getNoLote()));                                 //LOTE
                        negocioIntegranteRen.setCp(String.valueOf(Miscellaneous.validInt(negocio.getCodigoPostal())));           //CP
                        negocioIntegranteRen.setCiudad(Miscellaneous.validStr(negocio.getCiudad()));                                //CIUDAD
                        negocioIntegranteRen.setAntiguedad(String.valueOf(Miscellaneous.validInt(negocio.getAntiguedad())));            //ANTIGUEDA
                        negocioIntegranteRen.setIngMensual("");                                                                         //INGRESO MENSUAL
                        negocioIntegranteRen.setIngOtros("");                                                                         //INGRESOS OTROS
                        negocioIntegranteRen.setGastoSemanal("");                                                                         //GASTO MENSUAL
                        negocioIntegranteRen.setCapacidadPago("");                                                                         //CAPACIDAD DE PAGO
                        negocioIntegranteRen.setMontoMaximo("");                                                                         //MONTO MAXIMO
                        negocioIntegranteRen.setMediosPago("");                                                                         //MEDIOS PAGO
                        negocioIntegranteRen.setOtroMedioPago("");                                                                         //OTRO MEDIO DE PAGO
                        negocioIntegranteRen.setNumOpeMensuales(0);                                                                         //NUM OPERACIONES MENSUALES
                        negocioIntegranteRen.setNumOpeMensualesEfectivo(0);                                                                         //NUM OPERACIONES MENSUALES EFECTIVO
                        negocioIntegranteRen.setFotoFachada("");                                                                         //FOTO FACHADA
                        negocioIntegranteRen.setRefDomiciliaria("");                                                                         //REFERENCIA DOMICILIARIA
                        negocioIntegranteRen.setEstatusRechazo(0);                                                                        //ESTATUS RECHAZO
                        negocioIntegranteRen.setComentarioRechazo("");                                                                         //COMENTARIO RECHAZADO
                        negocioIntegranteRen.setEstatusCompletado(0);                                                                        //ESTATUS COMPLETADO
                        negocioIntegranteRen.setDestinoCredito(Miscellaneous.validStr(negocio.getOtroDestinoCredito()));                    //OTRO DESTINO CREDITO

                        optionalCatColoniasNegocio.ifPresent(entity -> negocioIntegranteRen.setColonia(entity.getColoniaNombre()));
                        optionalLocalidadesNegocio.ifPresent(entity -> negocioIntegranteRen.setLocalidad(entity.getNombre()));
                        optionalMunicipiosNegocio.ifPresent(entity -> negocioIntegranteRen.setMunicipio(entity.getMunicipioNombre()));
                        optionalEstadosNegocio.ifPresent(entity -> negocioIntegranteRen.setEstado(entity.getEstadoNombre()));
                        optionalDestinoCredito.ifPresent(entity -> negocioIntegranteRen.setDestinoCredito(entity.getNombre()));
                        optionalOcupacion.ifPresent(entity -> negocioIntegranteRen.setOcupacion(entity.getOcupacionNombre()));
                        optionalSector.ifPresent(entity -> negocioIntegranteRen.setActividadEconomica(entity.getSectorNombre()));

                        this.negocioIntegranteRenDao.insert(negocioIntegranteRen);


                        //Inserta registro del conyuge
                        Optional<CatColonias> optionalCatColoniasConyuge = this.catColoniasDao.findById(Long.valueOf(conyuge.getColoniaId()));
                        Optional<Localidades> optionalLocalidadesConyuge = this.localidadesDao.findById(Long.valueOf(conyuge.getLocalidadId()));
                        Optional<Municipios> optionalMunicipiosConyuge = this.municipiosDao.findById(Long.valueOf(conyuge.getMunicipioId()));
                        Optional<Estados> optionalEstadosConyuge = this.estadosDao.findById(Long.valueOf(conyuge.getEstadoId()));
                        Optional<Ocupaciones> optionalOcupacionConyuge = this.ocupacionesDao.findById(Long.valueOf(conyuge.getOcupacionId()));

                        ConyugeIntegranteRen conyugeIntegranteRen = new ConyugeIntegranteRen();
                        conyugeIntegranteRen.setIdIntegrante(nuevoIntegranteId.intValue());                                        //ID INTEGRANTE
                        conyugeIntegranteRen.setNombre(Miscellaneous.validStr(conyuge.getNombre()));               //NOMBRE
                        conyugeIntegranteRen.setPaterno(Miscellaneous.validStr(conyuge.getPaterno()));              //PATERNO
                        conyugeIntegranteRen.setMaterno(Miscellaneous.validStr(conyuge.getMaterno()));              //MATERNO
                        conyugeIntegranteRen.setNacionalidad(Miscellaneous.validStr(conyuge.getNacionalidad()));         //NACIONALIDAD
                        conyugeIntegranteRen.setCiudad(Miscellaneous.validStr(conyuge.getCalle()));                //CALLE
                        conyugeIntegranteRen.setNoExterior(Miscellaneous.validStr(conyuge.getNoExterior()));           //NO EXTERIOR
                        conyugeIntegranteRen.setNoInterior(Miscellaneous.validStr(conyuge.getNoInterior()));           //NO INTERIOR
                        conyugeIntegranteRen.setManzana(Miscellaneous.validStr(conyuge.getNoManzana()));            //MANZANA
                        conyugeIntegranteRen.setLote(Miscellaneous.validStr(conyuge.getNoLote()));              //LOTE
                        conyugeIntegranteRen.setCp(String.valueOf(conyuge.getCodigoPostal()));                //CP
                        conyugeIntegranteRen.setCiudad(Miscellaneous.validStr(conyuge.getCiudad()));              //CIUDAD
                        conyugeIntegranteRen.setIngresosMensual("");                                                       //INGRESO MENSUAL
                        conyugeIntegranteRen.setGastoMensual("");                                                       //GASTO MENSUAL
                        conyugeIntegranteRen.setTelTrabajo(Miscellaneous.validStr(conyuge.getTelCasa()));             //TEL CASA
                        conyugeIntegranteRen.setTelCelular(Miscellaneous.validStr(conyuge.getTelCelular()));          //TEL CELULAR
                        conyugeIntegranteRen.setEstatusCompletado(0);                                                      //ESTATUS COMPLETADO
                        optionalCatColoniasConyuge.ifPresent(entity -> conyugeIntegranteRen.setColonia(entity.getColoniaNombre()));
                        optionalLocalidadesConyuge.ifPresent(entity -> conyugeIntegranteRen.setLocalidad(entity.getNombre()));
                        optionalMunicipiosConyuge.ifPresent(entity -> conyugeIntegranteRen.setMunicipio(entity.getMunicipioNombre()));
                        optionalEstadosConyuge.ifPresent(entity -> conyugeIntegranteRen.setEstado(entity.getEstadoNombre()));
                        optionalOcupacionConyuge.ifPresent(entity -> conyugeIntegranteRen.setOcupacion(entity.getOcupacionNombre()));
                        this.conyugeIntegranteRenDao.insert(conyugeIntegranteRen);


                        Optional<MediosContacto> mediosContactoComoOtrosDatosOptional = this.mediosContactoDao.findById(cliente.getMedioContactoId().longValue());

                        OtrosDatosIntegranteRen otrosDatosIntegranteRen = new OtrosDatosIntegranteRen();
                        otrosDatosIntegranteRen.setIdIntegrante(nuevoIntegranteId.intValue());
                        otrosDatosIntegranteRen.setClasificacionRiesgo("");
                        otrosDatosIntegranteRen.setEmail(cliente.getEmail());
                        otrosDatosIntegranteRen.setEstadoCuenta(cliente.getEstadoCuenta());
                        otrosDatosIntegranteRen.setEstatusIntegrante(0);
                        otrosDatosIntegranteRen.setMontoSolicitado("0");
                        mediosContactoComoOtrosDatosOptional.ifPresent(entity -> otrosDatosIntegranteRen.setMedioContacto(entity.getNombre()));

                        if (cliente.getTipoIntegrante() == 3) {
                            otrosDatosIntegranteRen.setCasaReunion(1);                                                             //CASA REUNION
                        } else {
                            otrosDatosIntegranteRen.setCasaReunion(0);                                                             //CASA REUNION
                        }
                        otrosDatosIntegranteRen.setFirma("");                                                                  //FIRMA
                        otrosDatosIntegranteRen.setEstatusCompletado(0);                                                                 //ESTATUS COMPLETADO
                        this.otrosDatosIntegranteRenDao.insert(otrosDatosIntegranteRen);

                        //Inserta registro de croquis
                        CroquisGpoRen croquisGpoRen = new CroquisGpoRen();
                        croquisGpoRen.setIdIntegrante(nuevoIntegranteId.intValue());
                        croquisGpoRen.setCallePrincipal("");
                        croquisGpoRen.setLateralUno("");
                        croquisGpoRen.setLateralDos("");
                        croquisGpoRen.setCalleTrasera("");
                        croquisGpoRen.setReferencias("");
                        croquisGpoRen.setEstatusCompletado(0);
                        this.croquisGpoRenDao.insert(croquisGpoRen);

                        //Inserta registro de politicas de integrante
                        PoliticasPldIntegranteRen politicasPldIntegranteRen = new PoliticasPldIntegranteRen();
                        politicasPldIntegranteRen.setIdIntegrante(nuevoIntegranteId.intValue());
                        politicasPldIntegranteRen.setPropietarioReal(0);
                        politicasPldIntegranteRen.setProveedorRecursos(0);
                        politicasPldIntegranteRen.setPersonaPolitica(0);
                        politicasPldIntegranteRen.setEstatusCompletado(0);
                        this.politicasPldIntegranteRenDao.insert(politicasPldIntegranteRen);

                        //Inserta registro de documentos de integrante
                        DocumentosIntegranteRen documentosIntegranteRen = new DocumentosIntegranteRen();
                        documentosIntegranteRen.setIdIntegrante(nuevoIntegranteId.intValue());
                        documentosIntegranteRen.setIneFrontal("");
                        documentosIntegranteRen.setIneReverso("");
                        documentosIntegranteRen.setCurp("");
                        documentosIntegranteRen.setComprobante("");
                        documentosIntegranteRen.setEstatusCompletado(0);
                        this.documentosIntegranteRenDao.insert(documentosIntegranteRen);
                    }
                    this.prestamosToRenovarDao.updateDownloadStatusByGrupoId(grupoPorRenovarId);
                });
            } else {
                try (ResponseBody responseBodyError = response.errorBody()) {
                    if (responseBodyError != null) {
                        String errorTxt = responseBodyError.string();
                        logTag.e(errorTxt);
                    } else {
                        logTag.e("Error en la creacion de una nueva renovacion de credito grupal");
                    }
                } catch (IOException e) {
                    logTag.e(e);
                }
            }
        } catch (ExecutionException | InterruptedException e) {
            logTag.e(e);
        }
    }

    public void registraRenovacion(Integer prestamoToRenovarRemoteId, Integer prestamoToRenovarClienteId) {

        try {
            Call<MRenovacion> prestamoToRenovar = this.prestamosPorRenovarRemoteDatasource.getPrestamoToRenovar(prestamoToRenovarRemoteId, prestamoToRenovarClienteId);
            Response<MRenovacion> response = executorUtil.process(prestamoToRenovar);
            if (response.code() == 200) {
                MRenovacion item = response.body();
                if (item == null) {
                    return;
                }
                executorUtil.runTaskInThread(() -> {
                    Long numeroSolicitudId = crearNuevaSolicitudDeRenovacion(item);
                    crearNuevoCreditoIndividualDeRenovacion(item, numeroSolicitudId);
                    crearNuevoClienteIndividualDeRenovacion(item, numeroSolicitudId);
                    crearNuevoConyugeDeClineteIndDeRenovacion(item, numeroSolicitudId);
                    creareNuevoDatosEconomicosDeRenovacion(numeroSolicitudId);
                    crearNuevosDatosDeNegocioDeClienteIndividualDeRenovacion(item, numeroSolicitudId);
                    creareNuevosDatosDeAvalDelClienteIndividualDeRenovacion(item, numeroSolicitudId);
                    creareNuevosDatosDeReferenciaDelClienteIndividualDerenovacion(item, numeroSolicitudId);
                    creareNuevoCroquisDeClienteDeRenovacion(numeroSolicitudId);
                    crearNuevosPoliticasPldDeClineteIndividualDeRenovacion(numeroSolicitudId);
                    creareNuevosDocumentosDeClienteIndividualDeRenovacion(numeroSolicitudId);
                    this.prestamosToRenovarDao.updateDownloadStatusByPrestamoIdAndClienteId(prestamoToRenovarRemoteId, prestamoToRenovarClienteId);
                });
            } else {
                try (ResponseBody responseBodyError = response.errorBody()) {
                    if (responseBodyError != null) {
                        String errorTxt = responseBodyError.string();
                        logTag.e(errorTxt);
                    } else {
                        logTag.e("Error en la creacion de una nueva renovacion de credito individual");
                    }
                } catch (IOException e) {
                    logTag.e(e);
                }
            }
        } catch (ExecutionException | InterruptedException e) {
            logTag.e(e);
        }
    }

    private void creareNuevosDocumentosDeClienteIndividualDeRenovacion(Long numeroSolicitudId) {
        DocumentosRen documentosRen = new DocumentosRen();
        documentosRen.setIdSolicitud(Math.toIntExact(numeroSolicitudId));
        documentosRen.setIneFrontal("");
        documentosRen.setIneReverso("");
        documentosRen.setCurp("");
        documentosRen.setComprobante("");
        documentosRen.setCodigoBarras("");
        documentosRen.setFirmaAsesor("");
        documentosRen.setEstatusCompletado(0);
        this.documentosRenDao.insert(documentosRen);
    }

    private void crearNuevosPoliticasPldDeClineteIndividualDeRenovacion(Long numeroSolicitudId) {
        PoliticasPldInd politicasPldInd = new PoliticasPldInd();
        politicasPldInd.setIdSolicitud(Math.toIntExact(numeroSolicitudId));
        politicasPldInd.setPropietarioReal(0);
        politicasPldInd.setProveedorRecursos(0);
        politicasPldInd.setPersonaPolitica(0);
        politicasPldInd.setEstatusCompletado(0);
        this.politicasPldIndDao.insert(politicasPldInd);
    }

    private void creareNuevoCroquisDeClienteDeRenovacion(Long numeroSolicitudId) {
        CroquisInd croquisInd = new CroquisInd();
        croquisInd.setIdSolicitud(Math.toIntExact(numeroSolicitudId));
        croquisInd.setCallePrincipal("");
        croquisInd.setLateralUno("");
        croquisInd.setLateralDos("");
        croquisInd.setCalleTrasera("");
        croquisInd.setReferencias("");
        croquisInd.setEstatusCompletado(0);
        croquisInd.setComentarioRechazo("");
        this.croquisIndDao.insert(croquisInd);
    }

    private void creareNuevosDatosDeReferenciaDelClienteIndividualDerenovacion(MRenovacion item, Long numeroSolicitudId) {
        Optional<CatColonias> optionalCatColoniasReferencia = this.catColoniasDao.findById(Long.valueOf(item.getReferencia().getColoniaId()));
        Optional<Municipios> optionalMunicipiosReferencia = this.municipiosDao.findById(Long.valueOf(item.getReferencia().getMunicipioId()));
        Optional<Estados> optionalEstadosReferencia = this.estadosDao.findById(Long.valueOf(item.getReferencia().getEstadoId()));

        //Inserta registro de direccion del referencia
        DireccionesRen direccionesRenReferencia = new DireccionesRen();
        direccionesRenReferencia.setTipoDireccion("REFERENCIA");
        direccionesRenReferencia.setLatitud("0");
        direccionesRenReferencia.setLongitud("0");
        direccionesRenReferencia.setCalle(Miscellaneous.validStr(item.getReferencia().getCalle()));
        direccionesRenReferencia.setNumExterior(Miscellaneous.validStr(item.getReferencia().getNoExterior()));
        direccionesRenReferencia.setNumInterior(Miscellaneous.validStr(item.getReferencia().getNoInterior()));
        direccionesRenReferencia.setManzana(Miscellaneous.validStr(item.getReferencia().getNoManzana()));
        direccionesRenReferencia.setLote(Miscellaneous.validStr(item.getReferencia().getNoLote()));
        direccionesRenReferencia.setCp(item.getReferencia().getCodigoPostal() == 0 ? "" : String.valueOf(item.getReferencia().getCodigoPostal()));
        direccionesRenReferencia.setCiudad(item.getReferencia().getCiudad());
        optionalCatColoniasReferencia.ifPresent(colonias -> direccionesRenReferencia.setColonia(colonias.getColoniaNombre()));
        optionalMunicipiosReferencia.ifPresent(municipios -> direccionesRenReferencia.setMunicipio(municipios.getMunicipioNombre()));
        optionalEstadosReferencia.ifPresent(estados -> direccionesRenReferencia.setEstado(estados.getEstadoNombre()));
        if (item.getAval().getLocalidadId() != null) {
            Optional<Localidades> optionalLocalidadesReferencia = this.localidadesDao.findById(Long.valueOf(item.getAval().getLocalidadId()));
            optionalLocalidadesReferencia.ifPresent(localidades -> direccionesRenReferencia.setLocalidad(localidades.getNombre()));
        } else {
            direccionesRenReferencia.setLocalidad("1");
        }
        this.direccionesRenDao.insert(direccionesRenReferencia);

        Long direccionReferenciaid = direccionesRenReferencia.getIdDireccion();

        //Inserta registro de referencia
        ReferenciaIndRen referenciaIndRen = new ReferenciaIndRen();
        referenciaIndRen.setIdSolicitud(Math.toIntExact(numeroSolicitudId));
        referenciaIndRen.setNombre(item.getReferencia().getNombre().toUpperCase());
        referenciaIndRen.setPaterno(item.getReferencia().getPaterno().toUpperCase());
        referenciaIndRen.setMaterno(item.getReferencia().getMaterno().toUpperCase());
        referenciaIndRen.setFechaNacimiento(Miscellaneous.validStr(item.getReferencia().getFechaNacimiento()));
        referenciaIndRen.setDireccionId(String.valueOf(direccionReferenciaid));
        referenciaIndRen.setTelCelular(item.getReferencia().getTelCelular());
        referenciaIndRen.setEstatusCompletado(0);
        referenciaIndRen.setComentarioRechazo("");
        this.referenciaIndRenDao.insert(referenciaIndRen);
    }

    private void creareNuevosDatosDeAvalDelClienteIndividualDeRenovacion(MRenovacion item, Long numeroSolicitudId) {
        Optional<CatColonias> optionalCatColoniasAval = this.catColoniasDao.findById(Long.valueOf(item.getNegocio().getColoniaId()));
        Optional<Localidades> optionalLocalidadesAval = this.localidadesDao.findById(Long.valueOf(item.getNegocio().getLocalidadId()));
        Optional<Municipios> optionalMunicipiosAval = this.municipiosDao.findById(Long.valueOf(item.getNegocio().getMunicipioId()));
        Optional<Estados> optionalEstadosAval = this.estadosDao.findById(Long.valueOf(item.getNegocio().getEstadoId()));

        DireccionesRen direccionesRenAval = new DireccionesRen();
        direccionesRenAval.setTipoDireccion("NEGOCIO");
        direccionesRenAval.setLatitud(Miscellaneous.validStr(item.getAval().getLatitud()));
        direccionesRenAval.setLongitud(Miscellaneous.validStr(item.getAval().getLongitud()));
        direccionesRenAval.setCalle(Miscellaneous.validStr(item.getAval().getCalle()));
        direccionesRenAval.setNumExterior(Miscellaneous.validStr(item.getAval().getNoExterior()));
        direccionesRenAval.setNumInterior(Miscellaneous.validStr(item.getAval().getNoInterior()));
        direccionesRenAval.setManzana(Miscellaneous.validStr(item.getAval().getNoManzana()));
        direccionesRenAval.setLote(Miscellaneous.validStr(item.getAval().getNoLote()));
        direccionesRenAval.setCp(item.getAval().getCodigoPostal() == 0 ? "" : String.valueOf(item.getAval().getCodigoPostal()));
        optionalCatColoniasAval.ifPresent(colonias -> direccionesRenAval.setColonia(colonias.getColoniaNombre()));
        optionalLocalidadesAval.ifPresent(localidades -> direccionesRenAval.setLocalidad(localidades.getNombre()));
        optionalMunicipiosAval.ifPresent(municipios -> direccionesRenAval.setMunicipio(municipios.getMunicipioNombre()));
        optionalEstadosAval.ifPresent(estados -> direccionesRenAval.setEstado(estados.getEstadoNombre()));
        Long direccionAvalId = this.direccionesRenDao.insert(direccionesRenAval);

        Optional<Estados> estadoAvalOptional = this.estadosDao.findById(Long.valueOf(item.getAval().getEstadoNacimientoId()));
        Optional<Parentescos> parentescoSolicitanteAvalOptional = this.parentescosDao.findById(Long.valueOf(item.getAval().getParentescoSolicitanteId()));
        Optional<IdentificacionTipo> tipoIdentificacionAvalOptional = this.identificacionTipoDao.findById(Long.valueOf(item.getAval().getIdentificacionTipoId()));
        Optional<Ocupaciones> ocupacionAvalOptional = this.ocupacionesDao.findById(Long.valueOf(item.getAval().getOcupacionId()));
        Optional<Sectores> sectorAvalOptional = this.sectoresDao.findById(Long.valueOf(item.getAval().getSectorId()));
        Optional<ViviendaTipos> tipoViviendaAvalOptional = this.viviendaTiposDao.findById(Long.valueOf(item.getAval().getViviendaTipoId()));
        Optional<Parentescos> parentescoTitularAvalOptional = this.parentescosDao.findById(Long.valueOf(item.getAval().getParentescoTitularId()));

        //Inserta registro del aval
        AvalRen avalRen = new AvalRen();
        avalRen.setIdSolicitud(Math.toIntExact(numeroSolicitudId));
        avalRen.setNombre(Miscellaneous.validStr(item.getAval().getNombre()).toUpperCase());
        avalRen.setPaterno(Miscellaneous.validStr(item.getAval().getPaterno()).toUpperCase());
        avalRen.setMaterno(Miscellaneous.validStr(item.getAval().getMaterno()).toUpperCase());
        avalRen.setFechaNacimiento(Miscellaneous.validStr(item.getAval().getFechaNacimiento()));
        avalRen.setEdad(String.valueOf(item.getAval().getEdad()));
        avalRen.setGenero(item.getAval().getGenero());
        avalRen.setRfc(Miscellaneous.validStr(item.getAval().getRfc()));
        avalRen.setCurp(Miscellaneous.validStr(item.getAval().getCurp()));
        avalRen.setCurpDigito("");
        avalRen.setDestinoCredito("");
        avalRen.setOtroDestino("");
        avalRen.setDireccionId(String.valueOf(direccionAvalId));
        avalRen.setNombreTitular(Miscellaneous.validStr(item.getAval().getNombreTitular()));
        avalRen.setCaracteristicasDomicilio(Miscellaneous.validStr(item.getAval().getCaracteristicasDomicilio()));
        avalRen.setAntigueda(item.getAval().getAntiguedad());
        avalRen.setTieneNegocio((item.getAval().getTieneNegocio() != null && item.getAval().getTieneNegocio()) ? 1 : 0);
        avalRen.setNombreNegocio(Miscellaneous.validStr(item.getAval().getNombreNegocio()));
        avalRen.setIngMensual("");
        avalRen.setIngOtros("");
        avalRen.setGastoSemanal("");
        avalRen.setGastoAgua("");
        avalRen.setGastoLuz("");
        avalRen.setGastoTelefono("");
        avalRen.setGastoRenta("");
        avalRen.setGastoOtros("");
        avalRen.setCapacidadPagos("");
        avalRen.setMedioPago("");
        avalRen.setOtroMedioPago("");
        avalRen.setMontoMaximo("");
        avalRen.setHorarioLocalizacion(Miscellaneous.validStr(item.getAval().getHoraLocalizacion()));
        avalRen.setActivosObservables("");
        avalRen.setTelCasa(Miscellaneous.validStr(item.getAval().getTelCasa()));
        avalRen.setTelCelular(Miscellaneous.validStr(item.getAval().getTelCelular()));
        avalRen.setTelMensajes(Miscellaneous.validStr(item.getAval().getTelMensaje()));
        avalRen.setTelTrabajo(Miscellaneous.validStr(item.getAval().getTelTrabajo()));
        avalRen.setEmail(Miscellaneous.validStr(item.getAval().getEmail()));
        avalRen.setFotoFachada("");
        avalRen.setRefDomiciliaria(Miscellaneous.validStr(item.getAval().getReferencia()));
        avalRen.setFirma("");
        avalRen.setEstatusRechazo(0);
        avalRen.setComentarioRechazo("");
        avalRen.setEstatusRechazo(0);
        avalRen.setNoIdentificacion(item.getAval().getNoIdentificacion());
        estadoAvalOptional.ifPresent(estados -> avalRen.setEstadoNacimiento(estados.getEstadoNombre()));
        parentescoSolicitanteAvalOptional.ifPresent(parentescos -> avalRen.setParentesco(parentescos.getNombre()));
        tipoIdentificacionAvalOptional.ifPresent(identificacionTipo -> avalRen.setTipoIdentificacion(identificacionTipo.getNombre()));
        ocupacionAvalOptional.ifPresent(ocupaciones -> avalRen.setOcupacion(ocupaciones.getOcupacionNombre()));
        sectorAvalOptional.ifPresent(sectores -> avalRen.setActividadEconomica(sectores.getSectorNombre()));
        tipoViviendaAvalOptional.ifPresent(viviendaTipos -> avalRen.setTipoVivienda(viviendaTipos.getNombre()));
        parentescoTitularAvalOptional.ifPresent(parentescos -> avalRen.setParentesco(parentescos.getNombre()));
        this.avalRenDao.insert(avalRen);
    }

    private void crearNuevosDatosDeNegocioDeClienteIndividualDeRenovacion(MRenovacion item, Long numeroSolicitudId) {

        Optional<CatColonias> optionalCatColoniasNegocio = this.catColoniasDao.findById(Long.valueOf(item.getNegocio().getColoniaId()));
        Optional<Localidades> optionalLocalidadesNegocio = this.localidadesDao.findById(Long.valueOf(item.getNegocio().getLocalidadId()));
        Optional<Municipios> optionalMunicipiosNegocio = this.municipiosDao.findById(Long.valueOf(item.getNegocio().getMunicipioId()));
        Optional<Estados> optionalEstadosNegocio = this.estadosDao.findById(Long.valueOf(item.getNegocio().getEstadoId()));

        //Inserta registro de direccion del negocio
        DireccionesRen direccionesRenNegocio = new DireccionesRen();
        direccionesRenNegocio.setTipoDireccion("NEGOCIO");
        direccionesRenNegocio.setLatitud(Miscellaneous.validStr(item.getNegocio().getLatitud()));
        direccionesRenNegocio.setLongitud(Miscellaneous.validStr(item.getNegocio().getLongitud()));
        direccionesRenNegocio.setCalle(Miscellaneous.validStr(item.getNegocio().getCalle()));
        direccionesRenNegocio.setNumExterior(Miscellaneous.validStr(item.getNegocio().getNoExterior()));
        direccionesRenNegocio.setNumInterior(Miscellaneous.validStr(item.getNegocio().getNoInterior()));
        direccionesRenNegocio.setManzana(Miscellaneous.validStr(item.getNegocio().getNoManzana()));
        direccionesRenNegocio.setLote(Miscellaneous.validStr(item.getNegocio().getNoLote()));
        direccionesRenNegocio.setCp(item.getNegocio().getCodigoPostal() == 0 ? "" : String.valueOf(item.getNegocio().getCodigoPostal()));
        optionalCatColoniasNegocio.ifPresent(colonias -> direccionesRenNegocio.setColonia(colonias.getColoniaNombre()));
        optionalLocalidadesNegocio.ifPresent(localidades -> direccionesRenNegocio.setLocalidad(localidades.getNombre()));
        optionalMunicipiosNegocio.ifPresent(municipios -> direccionesRenNegocio.setMunicipio(municipios.getMunicipioNombre()));
        optionalEstadosNegocio.ifPresent(estados -> direccionesRenNegocio.setEstado(estados.getEstadoNombre()));
        Long direccionNegocioId = this.direccionesRenDao.insert(direccionesRenNegocio);

        NegocioIndRen negocioIndRen = new NegocioIndRen();
        negocioIndRen.setIdSolicitud(Math.toIntExact(numeroSolicitudId));
        negocioIndRen.setNombre(Miscellaneous.validStr(item.getNegocio().getNombre()));
        negocioIndRen.setDireccionId(String.valueOf(direccionNegocioId));
        negocioIndRen.setIngMensual("");
        negocioIndRen.setIngOtros("");
        negocioIndRen.setGastoSemanal("");
        negocioIndRen.setGastoAgua("");
        negocioIndRen.setGastoLuz("");
        negocioIndRen.setGastoTelefono("");
        negocioIndRen.setGastoRenta("");
        negocioIndRen.setGastoOtros("");
        negocioIndRen.setMedioPago("");
        negocioIndRen.setOtroMedioPago("");
        negocioIndRen.setMontoMaximo("");
        negocioIndRen.setNumOperacionMensuales(0);
        negocioIndRen.setNumOperacionEfectivo(0);
        negocioIndRen.setDiasVenta("1");
        negocioIndRen.setFotoFachada("");
        negocioIndRen.setRefDomiciliaria("");
        negocioIndRen.setEstatusCompletado(0);
        negocioIndRen.setComentarioRechazo("");
        this.negocioIndRenDao.insert(negocioIndRen);
    }

    private void creareNuevoDatosEconomicosDeRenovacion(Long numeroSolicitudId) {
        EconomicosIndRen economicosIndRen = new EconomicosIndRen();
        economicosIndRen.setIdSolicitud(Math.toIntExact(numeroSolicitudId));
        economicosIndRen.setPropiedades("");
        economicosIndRen.setValorAproximado("");
        economicosIndRen.setUbicacion("");
        economicosIndRen.setIngreso("");
        economicosIndRen.setEstatusCompletado(0);
        this.economicosIndRenDao.insert(economicosIndRen);
    }

    private void crearNuevoConyugeDeClineteIndDeRenovacion(MRenovacion item, Long numeroSolicitudId) {
        //Inserta registro de direccion del cliente
        Optional<CatColonias> optionalCatColoniasConyuge = this.catColoniasDao.findById(Long.valueOf(item.getCliente().getColoniaId()));
        Optional<Localidades> optionalLocalidadesConyuge = this.localidadesDao.findById(Long.valueOf(item.getCliente().getLocalidadId()));
        Optional<Municipios> optionalMunicipiosConyuge = this.municipiosDao.findById(Long.valueOf(item.getCliente().getMunicipioId()));
        Optional<Estados> optionalEstadosConyuge = this.estadosDao.findById(Long.valueOf(item.getCliente().getEstadoId()));

        DireccionesRen direccionesRenConyuge = new DireccionesRen();
        direccionesRenConyuge.setTipoDireccion("CONYUGE");
        direccionesRenConyuge.setLatitud(Miscellaneous.validStr(item.getConyuge().getLatitud()));
        direccionesRenConyuge.setLongitud(Miscellaneous.validStr(item.getConyuge().getLongitud()));
        direccionesRenConyuge.setCalle(Miscellaneous.validStr(item.getConyuge().getCalle()));
        direccionesRenConyuge.setNumExterior(Miscellaneous.validStr(item.getConyuge().getNoExterior()));
        direccionesRenConyuge.setNumInterior(Miscellaneous.validStr(item.getConyuge().getNoInterior()));
        direccionesRenConyuge.setManzana(Miscellaneous.validStr(item.getConyuge().getNoManzana()));
        direccionesRenConyuge.setLote(Miscellaneous.validStr(item.getConyuge().getNoLote()));
        direccionesRenConyuge.setCp(item.getConyuge().getCodigoPostal() == 0 ? "" : String.valueOf(item.getConyuge().getCodigoPostal()));
        optionalCatColoniasConyuge.ifPresent(colonias -> direccionesRenConyuge.setColonia(colonias.getColoniaNombre()));
        optionalLocalidadesConyuge.ifPresent(localidades -> direccionesRenConyuge.setLocalidad(localidades.getNombre()));
        optionalMunicipiosConyuge.ifPresent(municipios -> direccionesRenConyuge.setMunicipio(municipios.getMunicipioNombre()));
        optionalEstadosConyuge.ifPresent(estados -> direccionesRenConyuge.setEstado(estados.getEstadoNombre()));
        Long direccionConyugeId = this.direccionesRenDao.insert(direccionesRenConyuge);

        //Inserta registro de datos conyuge
        Optional<Ocupaciones> optionalOcupacionesConyuge = ocupacionesDao.findById(Long.valueOf(item.getConyuge().getOcupacionId()));

        ConyugeIndRen conyugeIndRen = new ConyugeIndRen();
        conyugeIndRen.setIdSolicitud(Math.toIntExact(numeroSolicitudId));
        conyugeIndRen.setNombre(Miscellaneous.validStr(item.getConyuge().getNombre()).toUpperCase());
        conyugeIndRen.setPaterno(Miscellaneous.validStr(item.getConyuge().getPaterno()).toUpperCase());
        conyugeIndRen.setMaterno(Miscellaneous.validStr(item.getConyuge().getMaterno()).toUpperCase());
        conyugeIndRen.setNacionalidad(Miscellaneous.validStr(item.getConyuge().getNacionalidad()));
        conyugeIndRen.setDireccionId(String.valueOf(direccionConyugeId));
        conyugeIndRen.setIngMensual(String.valueOf(item.getConyuge().getIngresoMensual()));
        conyugeIndRen.setGastoMensual(String.valueOf(item.getConyuge().getGastoMensual()));
        conyugeIndRen.setTelCasa(Miscellaneous.validStr(item.getConyuge().getTelCasa()));
        conyugeIndRen.setTelCelular(Miscellaneous.validStr(item.getConyuge().getTelCelular()));
        conyugeIndRen.setEstatusCompletado(0);
        optionalOcupacionesConyuge.ifPresent(ocupaciones -> conyugeIndRen.setOcupacion(ocupaciones.getOcupacionNombre()));
        conyugeIndRenDao.insert(conyugeIndRen);
    }

    private void crearNuevoClienteIndividualDeRenovacion(MRenovacion item, Long numeroSolicitudId) {
        MRenovacion.Cliente clineteRenovacion = item.getCliente();

        Optional<CatColonias> optionalCatColonias = this.catColoniasDao.findById(Long.valueOf(clineteRenovacion.getColoniaId()));
        Optional<Localidades> optionalLocalidades = this.localidadesDao.findById(Long.valueOf(clineteRenovacion.getLocalidadId()));
        Optional<Municipios> optionalMunicipios = this.municipiosDao.findById(Long.valueOf(clineteRenovacion.getMunicipioId()));
        Optional<Estados> optionalEstados = this.estadosDao.findById(Long.valueOf(clineteRenovacion.getEstadoId()));
        Optional<Ocupaciones> optionalOcupaciones = this.ocupacionesDao.findById(Long.valueOf(clineteRenovacion.getOcupacionId()));
        Optional<Sectores> optionalSectores = this.sectoresDao.findById(Long.valueOf(clineteRenovacion.getOcupacionId()));
        Optional<IdentificacionTipo> optionalIdentificacionTipo = this.identificacionTipoDao.findById(Long.valueOf(clineteRenovacion.getIdentificacionTipoId()));
        Optional<NivelesEstudios> optionalNivelesEstudios = this.nivelesEstudiosDao.findById(Long.valueOf(clineteRenovacion.getEstudioNivelId()));
        Optional<EstadosCiviles> optionalEstadosCiviles = this.estadosCivilesDao.findById(Long.valueOf(clineteRenovacion.getEstadoCivilId()));
        Optional<ViviendaTipos> optionalViviendaTipos = this.viviendaTiposDao.findById(Long.valueOf(clineteRenovacion.getViviendaTipoId()));
        Optional<MediosContacto> optionalMediosContacto = this.mediosContactoDao.findById(Long.valueOf(clineteRenovacion.getMedioContactoId()));

        DireccionesRen direccionRen = new DireccionesRen();
        direccionRen.setTipoDireccion("CLIENTE");
        direccionRen.setLatitud(Miscellaneous.validStr(clineteRenovacion.getLongitud()));
        direccionRen.setLongitud(Miscellaneous.validStr(clineteRenovacion.getLongitud()));
        direccionRen.setCalle(Miscellaneous.validStr(clineteRenovacion.getCalle()));
        direccionRen.setNumExterior(Miscellaneous.validStr(clineteRenovacion.getNoExterior()));
        direccionRen.setNumInterior(Miscellaneous.validStr(clineteRenovacion.getNoInterior()));
        direccionRen.setManzana(Miscellaneous.validStr(clineteRenovacion.getNoManzana()));
        direccionRen.setLote(Miscellaneous.validStr(clineteRenovacion.getNoLote()));
        direccionRen.setCp(String.valueOf(Miscellaneous.validInt(clineteRenovacion.getCodigoPostal())));
        direccionRen.setCiudad(Miscellaneous.validStr(clineteRenovacion.getCiudad()));
        optionalCatColonias.ifPresent(catColonias -> direccionRen.setColonia(catColonias.getColoniaNombre()));
        optionalLocalidades.ifPresent(localidades -> direccionRen.setLocalidad(localidades.getNombre()));
        optionalMunicipios.ifPresent(municipios -> direccionRen.setMunicipio(municipios.getMunicipioNombre()));
        optionalEstados.ifPresent(estados -> direccionRen.setEstado(estados.getEstadoNombre()));
        Long dicreccionClienteId = direccionesRenDao.insert(direccionRen);

        //Inserta registro de datos del cliente
        ClienteIndRen clienteIndRen = new ClienteIndRen();
        clienteIndRen.setIdSolicitud(Math.toIntExact(numeroSolicitudId));
        clienteIndRen.setNombre(Miscellaneous.validStr(item.getCliente().getNombre().trim().toUpperCase()));
        clienteIndRen.setPaterno(Miscellaneous.validStr(item.getCliente().getPaterno().trim().toUpperCase()));
        clienteIndRen.setMaterno(Miscellaneous.validStr(item.getCliente().getMaterno().trim().toUpperCase()));
        clienteIndRen.setFechaNacimiento(Miscellaneous.validStr(item.getCliente().getFechaNacimiento()));
        clienteIndRen.setEdad(Miscellaneous.GetEdad(item.getCliente().getFechaNacimiento()));
        clienteIndRen.setGenero(item.getCliente().getGenero());
        clienteIndRen.setRfc(Miscellaneous.validStr(Miscellaneous.validStr(item.getCliente().getRfc())));
        clienteIndRen.setCurp(Miscellaneous.validStr(Miscellaneous.validStr(item.getCliente().getCurp())));
        clienteIndRen.setCurpDigitoVeri("");
        clienteIndRen.setNoIdentificacion(Miscellaneous.validStr(item.getCliente().getNoIdentificacion()));
        clienteIndRen.setBienes(item.getCliente().getRegimenBienId());
        clienteIndRen.setParentesco("");
        clienteIndRen.setOtroTipoVivienda("");
        clienteIndRen.setDireccionId(String.valueOf(dicreccionClienteId));
        clienteIndRen.setTelCasa(Miscellaneous.validStr(item.getCliente().getTelCasa()));
        clienteIndRen.setTelCelular(Miscellaneous.validStr(item.getCliente().getTelCelular()));
        clienteIndRen.setTelMensajes(Miscellaneous.validStr(item.getCliente().getTelMensaje()));
        clienteIndRen.setTelTrabajo(Miscellaneous.validStr(item.getCliente().getTelTrabajo()));
        if (!Miscellaneous.validStr(item.getCliente().getTiempoVivirSitio()).isEmpty())
            clienteIndRen.setTiempoVivirSitio(Integer.parseInt(item.getCliente().getTiempoVivirSitio()));
        else
            clienteIndRen.setTiempoVivirSitio(0);
        clienteIndRen.setDependientes(Miscellaneous.validStr(item.getCliente().getDependientesEconomico()));
        clienteIndRen.setEstadoCuenta(Miscellaneous.validStr(item.getCliente().getEstadoCuenta()));
        clienteIndRen.setEmail(Miscellaneous.validStr(item.getCliente().getEmail()));
        clienteIndRen.setFotoFachada("");
        clienteIndRen.setRefDomiciliaria(Miscellaneous.validStr(item.getCliente().getReferencia()));
        clienteIndRen.setFirma("");
        clienteIndRen.setEstatusRechazo(0);
        clienteIndRen.setComentarioRechazo("");
        clienteIndRen.setEstatusCompletado(0);
        optionalOcupaciones.ifPresent(ocupaciones -> clienteIndRen.setOcupacion(ocupaciones.getOcupacionNombre()));
        optionalSectores.ifPresent(sectores -> clienteIndRen.setActividadEconomica(sectores.getSectorNombre()));
        optionalIdentificacionTipo.ifPresent(identificacionTipo -> clienteIndRen.setTipoIdentificacion(identificacionTipo.getNombre()));
        optionalNivelesEstudios.ifPresent(nivelesEstudios -> clienteIndRen.setNivelEstudio(nivelesEstudios.getNombre()));
        optionalEstadosCiviles.ifPresent(estadosCiviles -> clienteIndRen.setEstadoCivil(estadosCiviles.getNombre()));
        optionalViviendaTipos.ifPresent(viviendaTipos -> clienteIndRen.setTipoVivienda(viviendaTipos.getNombre()));
        optionalMediosContacto.ifPresent(mediosContacto -> clienteIndRen.setMedioContacto(mediosContacto.getNombre()));
        optionalEstados.ifPresent(estados -> clienteIndRen.setEstadoNacimiento(estados.getEstadoNombre()));
        clienteIndRenDao.insert(clienteIndRen);
    }

    private void crearNuevoCreditoIndividualDeRenovacion(MRenovacion item, Long numeroSolicitudId) {
        CreditoIndRen creditoIndRen = new CreditoIndRen();
        creditoIndRen.setIdSolicitud(Math.toIntExact(numeroSolicitudId));
        creditoIndRen.setPlazo("");
        creditoIndRen.setPeriodicidad("");
        creditoIndRen.setFechaDesembolso("");
        creditoIndRen.setDiaDesembolso("");
        creditoIndRen.setHoraVisita("");
        creditoIndRen.setMontoPrestamo("");
        creditoIndRen.setCiclo(String.valueOf(Miscellaneous.validInt(item.getPrestamo().getNumCiclo())));
        creditoIndRen.setCreditoAnterior(String.valueOf(item.getPrestamo().getMonto()));
        creditoIndRen.setComportamientoPago("");
        creditoIndRen.setNumCliente(String.valueOf(Miscellaneous.validInt(item.getPrestamo().getClienteId())));
        creditoIndRen.setObservaciones("");
        creditoIndRen.setDestino("");
        creditoIndRen.setClasificacionRiesgo(Miscellaneous.GetRiesgo(Miscellaneous.validInt(item.getPrestamo().getNivelRiesgo())));
        creditoIndRen.setEstatusCompletado(0);
        creditoIndRenDao.insert(creditoIndRen);
    }

    private Long crearNuevaSolicitudDeRenovacion(MRenovacion item) {
        MRenovacion.Cliente clineteRenovacion = item.getCliente();

        String nombre = (Miscellaneous.validStr(clineteRenovacion.getNombre()) + " " +
                Miscellaneous.validStr(clineteRenovacion.getPaterno()) + " " +
                Miscellaneous.validStr(clineteRenovacion.getMaterno())).trim().toUpperCase();
        Integer asesorId = Integer.parseInt(this.sessionManager.getUser().get(0));
        String volumenSolicitud = this.sidertMovilApplication.getString(R.string.vol_solicitud);

        SolicitudesRen solicitudesRen = new SolicitudesRen();
        solicitudesRen.setVolSolicitud(volumenSolicitud);
        solicitudesRen.setUsuarioId(asesorId);
        solicitudesRen.setTipoSolicitud(1);
        solicitudesRen.setIdOriginacion(0);
        solicitudesRen.setNombre(nombre);
        solicitudesRen.setFechaInicio(Miscellaneous.ObtenerFecha(TIMESTAMP));
        solicitudesRen.setFechaTermino("");
        solicitudesRen.setFechaEnvio("");
        solicitudesRen.setFechaDispositivo(Miscellaneous.ObtenerFecha(TIMESTAMP));
        solicitudesRen.setFechaGuardado("");
        solicitudesRen.setEstatus(0);
        solicitudesRen.setPrestamoId(item.getPrestamo().getPrestamodId());
        return this.solicitudesRenDao.insert(solicitudesRen);
    }


}
