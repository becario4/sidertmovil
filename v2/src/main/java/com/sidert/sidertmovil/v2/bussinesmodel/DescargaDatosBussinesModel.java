package com.sidert.sidertmovil.v2.bussinesmodel;

import android.content.ContentValues;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.BatteryManager;
import android.util.Log;

import com.sidert.sidertmovil.database.DBhelper;
import com.sidert.sidertmovil.models.MAmortizacion;
import com.sidert.sidertmovil.models.MAval;
import com.sidert.sidertmovil.models.MCartera;
import com.sidert.sidertmovil.models.MImpresionRes;
import com.sidert.sidertmovil.models.MIntegrante;
import com.sidert.sidertmovil.models.MPago;
import com.sidert.sidertmovil.models.MPrestamoGpoRes;
import com.sidert.sidertmovil.models.MPrestamoRes;
import com.sidert.sidertmovil.models.MSendImpresion;
import com.sidert.sidertmovil.models.MSolicitudAutorizar;
import com.sidert.sidertmovil.models.MSolicitudRechazoGpo;
import com.sidert.sidertmovil.models.MSolicitudRechazoInd;
import com.sidert.sidertmovil.models.cartera.amortizaciones.Amortizacion;
import com.sidert.sidertmovil.models.cartera.amortizaciones.AmortizacionDao;
import com.sidert.sidertmovil.models.documentosclientes.DocumentoCliente;
import com.sidert.sidertmovil.models.documentosclientes.DocumentoClienteDao;
import com.sidert.sidertmovil.models.verificacionesdomiciliarias.VerificacionDomiciliaria;
import com.sidert.sidertmovil.models.verificacionesdomiciliarias.VerificacionDomiciliariaDao;
import com.sidert.sidertmovil.services.expedientes.DocumentoClienteService;
import com.sidert.sidertmovil.utils.Constants;
import com.sidert.sidertmovil.utils.Miscellaneous;
import com.sidert.sidertmovil.utils.RetrofitClient;
import com.sidert.sidertmovil.utils.SessionManager;
import com.sidert.sidertmovil.v2.R;
import com.sidert.sidertmovil.v2.SidertMovilApplication;
import com.sidert.sidertmovil.v2.remote.datasource.CarteraRemoteDatasource;
import com.sidert.sidertmovil.v2.remote.datasource.DocumentoRemoteDatasource;
import com.sidert.sidertmovil.v2.remote.datasource.ImpresionesRemoteDatasource;
import com.sidert.sidertmovil.v2.remote.datasource.PrestamoRemoteDatasource;
import com.sidert.sidertmovil.v2.remote.datasource.SolicitudesRemoteDatasource;
import com.sidert.sidertmovil.v2.remote.datasource.VerificacionDomiciliariaRemoteDatasource;
import com.sidert.sidertmovil.v2.repositories.PrestamoRepository;
import com.sidert.sidertmovil.v2.repositories.PrestamosRenovarRepository;
import com.sidert.sidertmovil.v2.repositories.ReciboAgfCcRepository;
import com.sidert.sidertmovil.v2.repositories.RecibosCcRepository;
import com.sidert.sidertmovil.v2.services.CatalogoSyncService;
import com.sidert.sidertmovil.v2.utils.ExecutorUtil;
import com.sidert.sidertmovil.v2.utils.PrestamoIdTipo;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import javax.inject.Inject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import timber.log.Timber;

import static com.sidert.sidertmovil.utils.Constants.FECHA;
import static com.sidert.sidertmovil.utils.Constants.FORMAT_DATE_GNRAL;
import static com.sidert.sidertmovil.utils.Constants.TBL_AMORTIZACIONES_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_AVAL_IND;
import static com.sidert.sidertmovil.utils.Constants.TBL_AVAL_IND_REN;
import static com.sidert.sidertmovil.utils.Constants.TBL_AVAL_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_CARTERA_GPO_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_CARTERA_IND_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_CLIENTE_IND;
import static com.sidert.sidertmovil.utils.Constants.TBL_CLIENTE_IND_REN;
import static com.sidert.sidertmovil.utils.Constants.TBL_CONYUGE_IND;
import static com.sidert.sidertmovil.utils.Constants.TBL_CONYUGE_IND_REN;
import static com.sidert.sidertmovil.utils.Constants.TBL_CONYUGE_INTEGRANTE;
import static com.sidert.sidertmovil.utils.Constants.TBL_CONYUGE_INTEGRANTE_REN;
import static com.sidert.sidertmovil.utils.Constants.TBL_CREDITO_GPO;
import static com.sidert.sidertmovil.utils.Constants.TBL_CREDITO_GPO_REN;
import static com.sidert.sidertmovil.utils.Constants.TBL_CREDITO_IND;
import static com.sidert.sidertmovil.utils.Constants.TBL_CREDITO_IND_REN;
import static com.sidert.sidertmovil.utils.Constants.TBL_CROQUIS_GPO;
import static com.sidert.sidertmovil.utils.Constants.TBL_CROQUIS_GPO_REN;
import static com.sidert.sidertmovil.utils.Constants.TBL_CROQUIS_IND;
import static com.sidert.sidertmovil.utils.Constants.TBL_CROQUIS_IND_REN;
import static com.sidert.sidertmovil.utils.Constants.TBL_DOCUMENTOS;
import static com.sidert.sidertmovil.utils.Constants.TBL_DOCUMENTOS_INTEGRANTE;
import static com.sidert.sidertmovil.utils.Constants.TBL_DOCUMENTOS_INTEGRANTE_REN;
import static com.sidert.sidertmovil.utils.Constants.TBL_DOCUMENTOS_REN;
import static com.sidert.sidertmovil.utils.Constants.TBL_DOMICILIO_INTEGRANTE;
import static com.sidert.sidertmovil.utils.Constants.TBL_DOMICILIO_INTEGRANTE_REN;
import static com.sidert.sidertmovil.utils.Constants.TBL_ECONOMICOS_IND;
import static com.sidert.sidertmovil.utils.Constants.TBL_ECONOMICOS_IND_REN;
import static com.sidert.sidertmovil.utils.Constants.TBL_IMPRESIONES_VENCIDA_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_IMPRESIONES_VIGENTE_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_INTEGRANTES_GPO;
import static com.sidert.sidertmovil.utils.Constants.TBL_INTEGRANTES_GPO_REN;
import static com.sidert.sidertmovil.utils.Constants.TBL_MIEMBROS_GPO_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_NEGOCIO_IND;
import static com.sidert.sidertmovil.utils.Constants.TBL_NEGOCIO_IND_REN;
import static com.sidert.sidertmovil.utils.Constants.TBL_NEGOCIO_INTEGRANTE;
import static com.sidert.sidertmovil.utils.Constants.TBL_NEGOCIO_INTEGRANTE_REN;
import static com.sidert.sidertmovil.utils.Constants.TBL_OTROS_DATOS_INTEGRANTE;
import static com.sidert.sidertmovil.utils.Constants.TBL_OTROS_DATOS_INTEGRANTE_REN;
import static com.sidert.sidertmovil.utils.Constants.TBL_PAGOS_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_POLITICAS_PLD_INTEGRANTE;
import static com.sidert.sidertmovil.utils.Constants.TBL_POLITICAS_PLD_INTEGRANTE_REN;
import static com.sidert.sidertmovil.utils.Constants.TBL_PRESTAMOS_GPO_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_PRESTAMOS_IND_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_REFERENCIA_IND;
import static com.sidert.sidertmovil.utils.Constants.TBL_REFERENCIA_IND_REN;
import static com.sidert.sidertmovil.utils.Constants.TBL_REIMPRESION_VIGENTE_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_RESPUESTAS_GPO_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_RESPUESTAS_IND_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_RESPUESTAS_IND_V_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_RESPUESTAS_INTEGRANTE_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_SOLICITUDES;
import static com.sidert.sidertmovil.utils.Constants.TBL_SOLICITUDES_AUTO;
import static com.sidert.sidertmovil.utils.Constants.TBL_SOLICITUDES_REN;
import static com.sidert.sidertmovil.utils.Constants.TBL_TELEFONOS_CLIENTE;
import static com.sidert.sidertmovil.utils.Constants.TBL_TELEFONOS_INTEGRANTE;
import static com.sidert.sidertmovil.utils.Constants.TBL_TELEFONOS_INTEGRANTE_REN;
import static com.sidert.sidertmovil.utils.Constants.TIMESTAMP;

public class DescargaDatosBussinesModel extends BaseBussinesModel {
    private static final String TAG = DescargaDatosBussinesModel.class.getName();
    private static final SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_DATE_GNRAL, Locale.US);
    private final CatalogoSyncService catalogoSyncService;
    private final PrestamoRepository prestamoRepository;
    private final ReciboAgfCcRepository reciboAgfCcRepository;
    private final RecibosCcRepository recibosCcRepository;
    private final PrestamosRenovarRepository prestamosRenovarRepository;
    private final SolicitudesRemoteDatasource solicitudesRemoteDatasource;
    private final ImpresionesRemoteDatasource impresionesRemoteDatasource;
    private final PrestamoRemoteDatasource prestamoRemoteDatasource;
    private final DocumentoRemoteDatasource documentoRemoteDatasource;
    private final VerificacionDomiciliariaRemoteDatasource verificacionDomiciliariaRemoteDatasource;
    private final CarteraRemoteDatasource carteraRemoteDatasource;
    private final Timber.Tree klassTag;
    private ShowInfo showInfo;
    private CarteraLoadingProcess carteraLoadingProcess;

    @Inject
    public DescargaDatosBussinesModel(
            SidertMovilApplication sidertMovilApplication,
            ExecutorUtil executorUtil,
            SessionManager sessionManager,
            CatalogoSyncService catalogoSyncService,
            PrestamoRepository prestamoRepository,
            ReciboAgfCcRepository reciboAgfCcRepository,
            RecibosCcRepository recibosCcRepository,
            PrestamosRenovarRepository prestamosRenovarRepository,
            SolicitudesRemoteDatasource solicitudesRemoteDatasource,
            ImpresionesRemoteDatasource impresionesRemoteDatasource,
            PrestamoRemoteDatasource prestamoRemoteDatasource,
            DocumentoRemoteDatasource documentoRemoteDatasource,
            VerificacionDomiciliariaRemoteDatasource verificacionDomiciliariaRemoteDatasource,
            CarteraRemoteDatasource carteraRemoteDatasource
    ) {
        super(sidertMovilApplication, executorUtil, sessionManager);
        this.catalogoSyncService = catalogoSyncService;
        this.prestamoRepository = prestamoRepository;
        this.reciboAgfCcRepository = reciboAgfCcRepository;
        this.recibosCcRepository = recibosCcRepository;
        this.prestamosRenovarRepository = prestamosRenovarRepository;
        this.solicitudesRemoteDatasource = solicitudesRemoteDatasource;
        this.impresionesRemoteDatasource = impresionesRemoteDatasource;
        this.prestamoRemoteDatasource = prestamoRemoteDatasource;
        this.documentoRemoteDatasource = documentoRemoteDatasource;
        this.verificacionDomiciliariaRemoteDatasource = verificacionDomiciliariaRemoteDatasource;
        this.carteraRemoteDatasource = carteraRemoteDatasource;
        this.klassTag = Timber.tag(TAG);
    }

    public void startSync(Contract contract, ShowInfo showInfo, CarteraLoadingProcess carteraLoadingProcess) {
        this.showInfo = showInfo;
        this.carteraLoadingProcess = carteraLoadingProcess;
        this.catalogoSyncService.startSync();

        this.sendImpresionesVi();
        this.SendReimpresionesVi();

        this.getRecibosPrestamos();
        this.getUltimosRecibos();
        this.getUltimosRecibosCc();

        this.getSolicitudesRechazadasInd();
        this.getSolicitudesRechazadasGpo();
        this.getPrestamosAutorizados();
        this.getGestionesVerDom();
        this.getUltimasImpresiones();
        this.getCartera();
        this.getPrestamos();
        this.getPrestamosToRenovar();

        contract.finish();
    }

    private void printInfoInUI(String msg, int check) {
        if (showInfo != null) {
            this.showInfo.showInfoInScreen(msg, check);
        }
    }

    private void safeCarteraLoadingProcess(int increment, int total) {
        if (carteraLoadingProcess != null) {
            this.carteraLoadingProcess.advance(increment, total);
        }
    }

    private void getRecibosPrestamos() {
        printInfoInUI("Cargando recibos de prestamos", -999);
        klassTag.i("Cargando prestamos");
        prestamoRepository.getPrestamos();
        printInfoInUI("Cargando recibos de prestamos", 0);
    }

    private void getUltimosRecibos() {
        printInfoInUI("Cargando ultimos recibos de prestamos", -1);
        klassTag.i("Cargando Ultimos recibos");
        reciboAgfCcRepository.getUltimosRecibos();
        printInfoInUI("Cargando ultimos recibos de prestamos", 1);
    }

    private void getUltimosRecibosCc() {
        printInfoInUI("Cargando recibos de circulo de credito", -2);
        klassTag.i("Cargando recibos de circulo de credito");
        recibosCcRepository.getUltimosRecibosCc();
        printInfoInUI("Cargando recibos de circulo de credito", 2);
    }

    private void getPrestamosToRenovar() {
        printInfoInUI("Cargando prestamos por renovar", -10);
        klassTag.i("Cargando presamos por renovar");
        prestamosRenovarRepository.getPrestamosToRenovarOld();
        printInfoInUI("Cargando prestamos por renovar", 10);
    }

    private void sendImpresionesVi() {

        final DBhelper dBhelper = DBhelper.getInstance(sidertMovilApplication);
        final SQLiteDatabase db = dBhelper.getWritableDatabase();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Cursor row;

        String sql = "SELECT * FROM (SELECT vi._id, vi.num_prestamo_id_gestion, vi.asesor_id, vi.folio, vi.tipo_impresion, vi.monto, vi.clave_cliente, vi.create_at, vi.sent_at, vi.estatus, vi.num_prestamo, pi.tipo_cartera, '1' AS tipo_gestion, 'VIGENTE' AS tipo_prestamo, vi.celular FROM " + TBL_IMPRESIONES_VIGENTE_T + " AS vi INNER JOIN " + TBL_PRESTAMOS_IND_T + " AS pi ON vi.num_prestamo = pi.num_prestamo WHERE vi.num_prestamo LIKE '%-L%' UNION SELECT vi2._id, vi2.num_prestamo_id_gestion, vi2.asesor_id, vi2.folio, vi2.tipo_impresion, vi2.monto, vi2.clave_cliente, vi2.create_at, vi2.sent_at, vi2.estatus, vi2.num_prestamo, pg.tipo_cartera, '2' AS tipo_gestion, 'VIGENTE' AS tipo_prestamo, vi2.celular FROM " + TBL_IMPRESIONES_VIGENTE_T + " AS vi2 INNER JOIN " + TBL_PRESTAMOS_GPO_T + " AS pg ON vi2.num_prestamo = pg.num_prestamo WHERE vi2.num_prestamo NOT LIKE '%-L%' UNION SELECT v._id, v.num_prestamo_id_gestion, v.asesor_id, v.folio, v.tipo_impresion, v.monto, v.clave_cliente, v.create_at, v.sent_at, v.estatus, v.num_prestamo, pvi.tipo_cartera, '1' AS tipo_gestion, 'VENCIDA' AS tipo_prestamo, v.celular FROM " + TBL_IMPRESIONES_VENCIDA_T + " AS v INNER JOIN " + TBL_PRESTAMOS_IND_T + " AS pvi ON v.num_prestamo = pvi.num_prestamo WHERE v.num_prestamo LIKE '%-L%' UNION SELECT vg._id, vg.num_prestamo_id_gestion, vg.asesor_id, vg.folio, vg.tipo_impresion, vg.monto, vg.clave_cliente, vg.create_at, vg.sent_at, vg.estatus, vg.num_prestamo, pvg.tipo_cartera, '2' AS tipo_gestion, 'VENCIDA' AS tipo_prestamo, vg.celular FROM " + TBL_IMPRESIONES_VENCIDA_T + " AS vg INNER JOIN " + TBL_PRESTAMOS_GPO_T + " AS pvg ON vg.num_prestamo = pvg.num_prestamo WHERE vg.num_prestamo NOT LIKE '%-L%') AS imp WHERE estatus = ?";

        Log.e("sqlImpresion", sql);
        row = db.rawQuery(sql, new String[]{"0"});

        if (row.getCount() > 0) {
            row.moveToFirst();
            for (int i = 0; i < row.getCount(); i++) {
                List<MSendImpresion> impresiones = new ArrayList<>();
                MSendImpresion item = new MSendImpresion();
                String external_id = "";
                String fInicio = "";
                Cursor row_gestion;
                int tipo_impresion = (row.getString(13).equals("VIGENTE")) ? 1 : 2;
                if (row.getInt(12) == 1) { //Busca respuestas en individual
                    String[] str = row.getString(1).split("-");

                    if (row.getString(13).equals("VIGENTE"))
                        row_gestion = dBhelper.customSelect(TBL_RESPUESTAS_IND_T, "fecha_inicio", " WHERE _id = ?", "", new String[]{str[2]});
                    else
                        row_gestion = dBhelper.customSelect(TBL_RESPUESTAS_IND_V_T, "fecha_inicio", " WHERE _id = ?", "", new String[]{str[2]});
                } else { //Busca respuestas en grupal
                    String[] str = row.getString(1).split("-");

                    if (row.getString(13).equals("VIGENTE"))
                        row_gestion = dBhelper.customSelect(TBL_RESPUESTAS_GPO_T, "fecha_inicio", " WHERE _id = ?", "", new String[]{str[1]});
                    else
                        row_gestion = dBhelper.customSelect(TBL_RESPUESTAS_INTEGRANTE_T, "fecha_inicio", " WHERE _id = ?", "", new String[]{str[1]});
                }
                if (row_gestion.getCount() > 0) {
                    row_gestion.moveToFirst();
                    fInicio = row_gestion.getString(0);

                    Log.e("FechaInicio", fInicio);
                    Calendar cal = Calendar.getInstance();
                    try {
                        Date inicioGes = sdf.parse(fInicio);
                        cal.setTime(inicioGes);
                        String weekOfYear = (cal.get(Calendar.WEEK_OF_YEAR) < 10) ? "0" + cal.get(Calendar.WEEK_OF_YEAR) : String.valueOf(cal.get(Calendar.WEEK_OF_YEAR));
                        String nomenclatura = Miscellaneous.GetNomenclatura(row.getInt(12), row.getString(11));
                        external_id = cal.get(Calendar.YEAR) + weekOfYear + row.getString(2) + row.getString(10) + nomenclatura;
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    try {
                        item.setAsesorid(row.getString(2));
                        item.setExternalId(external_id);
                        item.setFolio(row.getString(3));
                        item.setTipo(row.getString(4));
                        item.setMontoRealizado(row.getDouble(5));
                        item.setSendedAt(Miscellaneous.ObtenerFecha(TIMESTAMP));
                        item.setGeneratedAt(row.getString(7));
                        item.setClavecliente(row.getString(6));
                        item.setErrores("");
                        item.setNumPrestamoIdGestion(row.getString(1));
                        item.setTipoCartera(Miscellaneous.GetIdTipoPrestamo(row.getString(11)));
                        item.setCelular(row.getString(14));

                        Log.e("JSON", Miscellaneous.ConvertToJson(item));
                        impresiones.add(item);

                        String impresionId = row.getString(0);
                        Call<List<String>> api = this.impresionesRemoteDatasource.guardarImpresiones(impresiones);
                        Response<List<String>> response = this.executorUtil.process(api);

                        List<String> data = response.body();
                        if (response.code() == 200 && data.size() > 0) {
                            ContentValues cv = new ContentValues();
                            cv.put("sent_at", Miscellaneous.ObtenerFecha(TIMESTAMP));
                            cv.put("estatus", "1");

                            if (tipo_impresion == 1) //Vigente
                                db.update(TBL_IMPRESIONES_VIGENTE_T, cv,
                                        "_id = ?", new String[]{impresionId});
                            else if (tipo_impresion == 2) //Vencida
                                db.update(TBL_IMPRESIONES_VENCIDA_T, cv,
                                        "_id = ?", new String[]{impresionId});
                            else if (tipo_impresion == 3) //Reimpresiones Vigente/Vencida
                                db.update(TBL_REIMPRESION_VIGENTE_T, cv,
                                        "_id = ?", new String[]{impresionId});
                        }
                    } catch (Exception e) {
                        this.klassTag.e(e);
                    }
                }
                row.moveToNext();
            }
        }
    }

    public void SendReimpresionesVi() {

        final DBhelper dBhelper = DBhelper.getInstance(sidertMovilApplication);
        final SQLiteDatabase db = dBhelper.getWritableDatabase();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Cursor row;
        String sql;

        sql = "SELECT * FROM (SELECT vri._id, vri.num_prestamo_id_gestion, vri.tipo_reimpresion, vri.folio, vri.monto, vri.clv_cliente, vri.asesor_id, vri.serie_id, vri.create_at, vri.sent_at, vri.estatus, vri.num_prestamo, pi.tipo_cartera, '1' AS tipo_gestion, 'VIGENTE' AS tipo_prestamo, vri.celular FROM " + TBL_REIMPRESION_VIGENTE_T + " AS vri INNER JOIN " + TBL_PRESTAMOS_IND_T + " AS pi ON vri.num_prestamo = pi.num_prestamo WHERE vri.num_prestamo LIKE '%-L%' AND pi.tipo_cartera IN ('VIGENTE','COBRANZA') UNION SELECT vri2._id, vri2.num_prestamo_id_gestion, vri2.tipo_reimpresion, vri2.folio, vri2.monto, vri2.clv_cliente, vri2.asesor_id, vri2.serie_id, vri2.create_at, vri2.sent_at, vri2.estatus, vri2.num_prestamo, pg.tipo_cartera, '2' AS tipo_gestion, 'VIGENTE' AS tipo_prestamo, vri2.celular FROM " + TBL_REIMPRESION_VIGENTE_T + " AS vri2 INNER JOIN " + TBL_PRESTAMOS_GPO_T + " AS pg ON vri2.num_prestamo = pg.num_prestamo WHERE vri2.num_prestamo NOT LIKE '%-L%' AND pg.tipo_cartera IN ('VIGENTE','COBRANZA') UNION SELECT vi._id, vi.num_prestamo_id_gestion, vi.tipo_reimpresion, vi.folio, vi.monto, vi.clv_cliente, vi.asesor_id, vi.serie_id, vi.create_at, vi.sent_at, vi.estatus, vi.num_prestamo, pvi.tipo_cartera, '1' AS tipo_gestion, 'VENCIDA' AS tipo_prestamo, vi.celular FROM " + TBL_REIMPRESION_VIGENTE_T + " AS vi INNER JOIN " + TBL_PRESTAMOS_IND_T + " AS pvi ON vi.num_prestamo = pvi.num_prestamo WHERE vi.num_prestamo LIKE '%-L%' AND pvi.tipo_cartera IN ('VENCIDA') UNION SELECT vg._id, vg.num_prestamo_id_gestion, vg.tipo_reimpresion, vg.folio, vg.monto, vg.clv_cliente, vg.asesor_id, vg.serie_id, vg.create_at, vg.sent_at, vg.estatus, vg.num_prestamo, pvg.tipo_cartera, '2' AS tipo_gestion, 'VENCIDA' AS tipo_prestamo, vg.celular FROM " + TBL_REIMPRESION_VIGENTE_T + " AS vg INNER JOIN " + TBL_PRESTAMOS_GPO_T + " AS pvg ON vg.num_prestamo = pvg.num_prestamo WHERE vg.num_prestamo NOT LIKE '%-L%' AND pvg.tipo_cartera IN ('VENCIDA')) AS imp WHERE estatus = ?";

        row = db.rawQuery(sql, new String[]{"0"});

        if (row.getCount() > 0) {
            row.moveToFirst();
            for (int i = 0; i < row.getCount(); i++) {
                List<MSendImpresion> impresiones = new ArrayList<>();
                MSendImpresion item = new MSendImpresion();

                String external_id = "";
                String fInicio = "";
                Cursor row_gestion;
                Log.e("NumPresTamoGestion", row.getString(1));
                if (row.getInt(13) == 1) {
                    String[] str = row.getString(1).split("-");
                    Log.e("iDGestion", str[2] + "   " + row.getString(14));
                    if (row.getString(14).equals("VIGENTE"))
                        row_gestion = dBhelper.customSelect(TBL_RESPUESTAS_IND_T, "fecha_inicio", " WHERE _id = ?", "", new String[]{str[2]});
                    else
                        row_gestion = dBhelper.customSelect(TBL_RESPUESTAS_IND_V_T, "fecha_inicio", " WHERE _id = ?", "", new String[]{str[2]});
                } else {
                    String[] str = row.getString(1).split("-");
                    Log.e("iDGestion", str[1]);
                    if (row.getString(14).equals("VIGENTE"))
                        row_gestion = dBhelper.customSelect(TBL_RESPUESTAS_GPO_T, "fecha_inicio", " WHERE _id = ?", "", new String[]{str[1]});
                    else
                        row_gestion = dBhelper.customSelect(TBL_RESPUESTAS_INTEGRANTE_T, "fecha_inicio", " WHERE _id = ?", "", new String[]{str[1]});
                }
                Log.e("RowReimp", row_gestion.getCount() + " Reimpresion");
                row_gestion.moveToFirst();
                fInicio = row_gestion.getString(0);

                Log.e("FechaInicio", fInicio);
                Calendar cal = Calendar.getInstance();
                try {
                    Date inicioGes = sdf.parse(fInicio);
                    cal.setTime(inicioGes);
                    String weekOfYear = (cal.get(Calendar.WEEK_OF_YEAR) < 10) ? "0" + cal.get(Calendar.WEEK_OF_YEAR) : String.valueOf(cal.get(Calendar.WEEK_OF_YEAR));
                    String nomenclatura = Miscellaneous.GetNomenclatura(row.getInt(13), row.getString(12));
                    external_id = cal.get(Calendar.YEAR) + weekOfYear + row.getString(6) + row.getString(11) + nomenclatura;
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                try {
                    item.setAsesorid(row.getString(6));
                    item.setExternalId(external_id);
                    item.setFolio(row.getString(3));
                    item.setTipo("R" + row.getString(2));
                    item.setMontoRealizado(row.getDouble(4));
                    item.setSendedAt(Miscellaneous.ObtenerFecha(TIMESTAMP));
                    item.setGeneratedAt(row.getString(8));
                    item.setClavecliente(row.getString(5));
                    item.setErrores("");
                    item.setNumPrestamoIdGestion(row.getString(1));
                    item.setTipoCartera(Miscellaneous.GetIdTipoPrestamo(row.getString(12)));
                    item.setCelular(row.getString(15));

                    Log.e("JSON", Miscellaneous.ConvertToJson(item));
                    impresiones.add(item);

                    Call<List<String>> call = this.impresionesRemoteDatasource.guardarImpresiones(impresiones);
                    int tipoImpresion = 3;
                    String impresionId = row.getString(0);

                    Response<List<String>> response = this.executorUtil.process(call);
                    Log.e("Response", response.code() + "zzzz");
                    List<String> data = response.body();
                    if (response.code() == 200 && data.size() > 0) {
                        ContentValues cv = new ContentValues();
                        cv.put("sent_at", Miscellaneous.ObtenerFecha(TIMESTAMP));
                        cv.put("estatus", "1");

                        if (tipoImpresion == 1) //Vigente
                            db.update(TBL_IMPRESIONES_VIGENTE_T, cv,
                                    "_id = ?", new String[]{impresionId});
                        else if (tipoImpresion == 2) //Vencida
                            db.update(TBL_IMPRESIONES_VENCIDA_T, cv,
                                    "_id = ?", new String[]{impresionId});
                        else if (tipoImpresion == 3) //Reimpresiones Vigente/Vencida
                            db.update(TBL_REIMPRESION_VIGENTE_T, cv,
                                    "_id = ?", new String[]{impresionId});
                    }

                } catch (Exception e) {
                    this.klassTag.e(e);
                }

                row.moveToNext();
            }
        }
    }

    private void getUltimasImpresiones() {
        printInfoInUI("Cargando ultimas impresiones", -7);
        DBhelper dBhelper = DBhelper.getInstance(sidertMovilApplication);
        SQLiteDatabase db = dBhelper.getWritableDatabase();

        //Se obtiene el nivel de bateria*/
        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = sidertMovilApplication.registerReceiver(null, ifilter);
        if (batteryStatus != null) {
            int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
            int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
            final float battery = (level / (float) scale) * 100;
            //Se calcula el nivel de bateria*/
            //Se obtiene la ubicacion del dispositivo*/

            final String latitud = this.sessionManager.getLatitud();
            final String longitud = this.sessionManager.getLongitud();

            /*En caso de obtener la ubicaion se prepara la peticion con los datos
             * de nivel de bateria, ubicacion, version de la app el serie id*/
            Call<List<MImpresionRes>> call;
            if (!latitud.isEmpty() && !longitud.isEmpty()) {
                call = impresionesRemoteDatasource.getUltimasImpresiones(sessionManager.getUser().get(0),
                        String.valueOf(battery),
                        sidertMovilApplication.getString(R.string.app_version),
                        latitud,
                        longitud);
            } else {
                /*En caso de no obtener la ubicaion se prepara la peticion con los datos
                 * de version de la app el serie id*/
                call = impresionesRemoteDatasource.getUltimasImpresiones(sessionManager.getUser().get(0),
                        String.valueOf(battery),
                        sidertMovilApplication.getString(R.string.app_version),
                        "",
                        "");
            }

            if (call != null) {
                try {
                    /*Se realiza la peticion para obtener el ultimo recibo de vigente y vencida,
                     * ademas guarda version de app, bateria y ubicacion
                     * */
                    Response<List<MImpresionRes>> response = executorUtil.runTaskInThread(call::execute);

                    //Lee el codigo de respuesta de peticion*/
                    if (response.isSuccessful()) {
                        //Se obtiene el ultimo folio registrado en DB*/
                        List<MImpresionRes> impresiones = response.body();

                        for (MImpresionRes item : Objects.requireNonNull(impresiones)) {
                            Cursor row;
                            //Obtiene el tipo de impresion(Vigente/Vencida) y el numero de prestamo*/
                            HashMap<Integer, String> data = Miscellaneous.GetNumPrestamo(item.getExternalId());

                            Log.e("TipoCartera", String.valueOf(item.getTipoCartera()));
                            if (item.getTipoCartera() == -1) {//Si el tipo Cartera fue Vigente*/

                                if (data.get(0).equals("Vigente")) {
                                    //Se busca esa impresion si ya se tiene guarda en el movil*/
                                    Log.e("nPre folio tipo_imp", data.get(1) + " " + String.valueOf(item.getFolio()) + " " + String.valueOf(item.getTipo()));
                                    row = dBhelper.getRecords(TBL_IMPRESIONES_VIGENTE_T, " WHERE num_prestamo = ?  AND folio = ? AND tipo_impresion = ?", "", new String[]{data.get(1), String.valueOf(item.getFolio()), String.valueOf(item.getTipo())});

                                    //En caso de existir solo actualizar fecha de envio y estatus*/
                                    if (row.getCount() > 0) {
                                        ContentValues cv = new ContentValues();
                                        cv.put("sent_at", item.getSendedAt());
                                        cv.put("estatus", "1");
                                        db.update(TBL_IMPRESIONES_VIGENTE_T, cv,
                                                "num_prestamo = ? AND folio = ? AND tipo_impresion = ?", new String[]{
                                                        data.get(1), item.getFolio(), item.getTipo()});

                                    } else {
                                        //En caso de que NO exista registrar la impresion*/

                                        HashMap<Integer, String> params = new HashMap<>();

                                        if (item.getNumPrestamoIdGestion().trim().isEmpty())
                                            params.put(0, data.get(1));  //num_prestamo_id_gestion
                                        else
                                            params.put(0, String.valueOf(item.getNumPrestamoIdGestion()));  //num_prestamo_id_gestion
                                        params.put(1, item.getAsesorid());                              //asesor_id
                                        params.put(2, item.getFolio());                                 //folio
                                        params.put(3, item.getTipo());                                  //tipo_impresion
                                        params.put(4, String.valueOf(item.getMontoRealizado()));        //monto
                                        params.put(5, item.getClavecliente());                          //clave_cliente
                                        params.put(6, item.getGeneratedAt());                           //created_at
                                        params.put(7, item.getSendedAt());                              //sent_at
                                        params.put(8, "1");                                             //estatus
                                        Log.e("NumPrestamo", data.get(1));
                                        params.put(9, data.get(1));                                     //num_prestamo
                                        params.put(10, "");                                             //celular
                                        //Guardar la impresion en vigente*/
                                        dBhelper.saveImpresiones(db, params);
                                    }
                                    row.close();
                                }
                                //En caso de ser una impresion de vencida*/
                                else if (data.get(0).equals("Vencida")) {

                                    //Buscar si existe esa impresion en la tabla de vencida*/
                                    Log.e("nPre folio tipo_imp", data.get(1) + " " + String.valueOf(item.getFolio()) + " " + String.valueOf(item.getTipo()));
                                    row = dBhelper.getRecords(TBL_IMPRESIONES_VENCIDA_T, " WHERE num_prestamo = ?  AND folio = ? AND tipo_impresion = ?", "", new String[]{data.get(1), String.valueOf(item.getFolio()), String.valueOf(item.getTipo())});

                                    //En caso de existir esa impresion*/
                                    if (row.getCount() > 0) {
                                        //Actualizar la fecha de envio y el estatus de la impresion*/
                                        ContentValues cv = new ContentValues();
                                        cv.put("sent_at", item.getSendedAt());
                                        cv.put("estatus", "1");
                                        db.update(TBL_IMPRESIONES_VENCIDA_T, cv,
                                                "num_prestamo = ? AND folio = ? AND tipo_impresion = ?", new String[]{
                                                        data.get(1), item.getFolio(), item.getTipo()});

                                    } else {
                                        //En caso de No existir registrar la impresion*/
                                        HashMap<Integer, String> params = new HashMap<>();
                                        if (item.getNumPrestamoIdGestion().trim().isEmpty())
                                            params.put(0, data.get(1));  //num_prestamo_id_gestion
                                        else
                                            params.put(0, String.valueOf(item.getNumPrestamoIdGestion()));  //num_prestamo_id_gestion
                                        params.put(1, item.getAsesorid());                              //asesor_id
                                        params.put(2, item.getFolio());                                 //folio
                                        params.put(3, item.getTipo());                                  //tipo_impresion
                                        params.put(4, String.valueOf(item.getMontoRealizado()));        //monto
                                        params.put(5, item.getClavecliente());                          //clave_cliente
                                        params.put(6, item.getGeneratedAt());                           //created_at
                                        params.put(7, item.getSendedAt());                              //sent_at
                                        params.put(8, "1");                                             //estatus
                                        Log.e("NumPrestamo", data.get(1));
                                        params.put(9, data.get(1));                                     //num_prestamo
                                        params.put(10, "");                                             //celular
                                        //Guarda la impresion de vencida en la tabla de vencida*/
                                        dBhelper.saveImpresionesVencida(db, params);
                                    }
                                    row.close();
                                }
                            } else {
                                //Si tipo cartera en vigente o cobranza*/
                                if (item.getTipoCartera() == 0 || item.getTipoCartera() == 1) {//VIGENTE

                                    //Busco la impresion en la tabla de vigente*/
                                    Log.e("nPre folio tipo_imp", data.get(1) + " " + String.valueOf(item.getFolio()) + " " + String.valueOf(item.getTipo()));
                                    row = dBhelper.getRecords(TBL_IMPRESIONES_VIGENTE_T, " WHERE num_prestamo = ? AND folio = ? AND tipo_impresion = ?", "", new String[]{data.get(1), item.getFolio(), item.getTipo()});

                                    //En caso de existir la impresion en la tabla*/
                                    Log.e("CountRow", String.valueOf(row.getCount()) + " asda");
                                    if (row.getCount() > 0) {
                                        //Actualiza fecha de envio y estatus de la impresion*/
                                        ContentValues cv = new ContentValues();
                                        cv.put("sent_at", item.getSendedAt());
                                        cv.put("estatus", "1");
                                        db.update(TBL_IMPRESIONES_VIGENTE_T, cv,
                                                "num_prestamo = ? AND folio = ? AND tipo_impresion = ?", new String[]{
                                                        data.get(1), item.getFolio(), item.getTipo()});

                                    } else {
                                        //En caso de NO existir registrar la impresion en Vigente*/
                                        HashMap<Integer, String> params = new HashMap<>();
                                        if (item.getNumPrestamoIdGestion().trim().isEmpty())
                                            params.put(0, data.get(1));  //num_prestamo_id_gestion
                                        else
                                            params.put(0, String.valueOf(item.getNumPrestamoIdGestion()));  //num_prestamo_id_gestion
                                        params.put(1, item.getAsesorid());                              //asesor_id
                                        params.put(2, item.getFolio());                                 //folioco
                                        params.put(3, item.getTipo());                                  //tipo_impresion
                                        params.put(4, String.valueOf(item.getMontoRealizado()));        //monto
                                        params.put(5, item.getClavecliente());                          //clave_cliente
                                        params.put(6, item.getGeneratedAt());                           //created_at
                                        params.put(7, item.getSendedAt());                              //sent_at
                                        params.put(8, "1");                                             //estatus
                                        params.put(9, data.get(1));                                     //num_prestamo
                                        params.put(10, "");                                             //celular
                                        //Guarda la impresion en la tabla de vigente*/
                                        dBhelper.saveImpresiones(db, params);
                                    }
                                    row.close();
                                }
                                //Si tipo cartera es Vencida*/
                                else if (item.getTipoCartera() == 4) {//VENCIDA

                                    //Busca la impresion en la tabla de vencida*/
                                    row = dBhelper.getRecords(TBL_IMPRESIONES_VENCIDA_T, " WHERE num_prestamo = ?  AND folio = ? AND tipo_impresion = ?", "", new String[]{data.get(1), String.valueOf(item.getFolio()), String.valueOf(item.getTipo())});

                                    //En caso de existir en la tabla*/
                                    if (row.getCount() > 0) {
                                        //Actualiza los campos de fecha de envio y estatus de la impresion*/
                                        ContentValues cv = new ContentValues();
                                        cv.put("sent_at", item.getSendedAt());
                                        cv.put("estatus", "1");
                                        db.update(TBL_IMPRESIONES_VENCIDA_T, cv,
                                                "num_prestamo = ? AND folio = ? AND tipo_impresion = ?", new String[]{
                                                        data.get(1), item.getFolio(), item.getTipo()});

                                    } else {
                                        //En caso de NO existir registrar la impresion en la tabla de vencida*/
                                        HashMap<Integer, String> params = new HashMap<>();
                                        if (item.getNumPrestamoIdGestion().trim().isEmpty())
                                            params.put(0, data.get(1));  //num_prestamo_id_gestion
                                        else
                                            params.put(0, String.valueOf(item.getNumPrestamoIdGestion()));  //num_prestamo_id_gestion
                                        params.put(1, item.getAsesorid());                              //asesor_id
                                        params.put(2, item.getFolio());                                 //folio
                                        params.put(3, item.getTipo());                                  //tipo_impresion
                                        params.put(4, String.valueOf(item.getMontoRealizado()));        //monto
                                        params.put(5, item.getClavecliente());                          //clave_cliente
                                        params.put(6, item.getGeneratedAt());                           //created_at
                                        params.put(7, item.getSendedAt());                              //sent_at
                                        params.put(8, "1");                                             //estatus
                                        params.put(9, data.get(1));                                     //num_prestamo
                                        params.put(10, "");                                             //celular
                                        //Guarda la impresion en la tabla de vencida*/
                                        dBhelper.saveImpresionesVencida(db, params);
                                    }
                                    row.close();
                                }
                            }
                        }
                    }
                } catch (ExecutionException | InterruptedException |
                         TimeoutException executionException) {
                    this.klassTag.e(executionException);
                }
            }
        }
        printInfoInUI("Cargando ultimas impresiones", 7);
    }

    private void getPrestamos() {
        printInfoInUI("Cargando prestamos", -9);
        DBhelper dBhelper = DBhelper.getInstance(sidertMovilApplication);
        SQLiteDatabase db = dBhelper.getWritableDatabase();


        List<PrestamoIdTipo> prestamoIdTipoList = getPrestamosFromDatabase(db);
        int iTotalRows = prestamoIdTipoList.size();
        safeCarteraLoadingProcess(0, iTotalRows);
        //Si obtiene resultado de cartera*/
        if (iTotalRows > 0) {
            //Recorre el listado de la cartera*/
            for (int i = 0; i < prestamoIdTipoList.size(); i++) {
                PrestamoIdTipo prestamoIdTipo = prestamoIdTipoList.get(i);
                //Valida el tipo de cartera Individual|Grupal*/
                int iPrestamoId = prestamoIdTipo.getId();
                if (prestamoIdTipo.getTipo() == 1) {
                    //Individual
                    //Realiza la consulta del prestamo individual de la cartera
                    getPrestamoInd(iPrestamoId, dBhelper, db);
                } else {
                    // Grupal
                    // Busca el prestamos grupal de la cartera
                    getPrestmoGpo(iPrestamoId, dBhelper, db);
                }
                safeCarteraLoadingProcess(i + 1, iTotalRows);
            }
        }
        printInfoInUI("Cargando prestamos", 9);

    }

    private List<PrestamoIdTipo> getPrestamosFromDatabase(SQLiteDatabase db) {
        printInfoInUI("Cargando prestamos a la base de datos", -1);
        List<PrestamoIdTipo> prestamoIdTipoList = new ArrayList<>();
        String query = "SELECT * FROM (SELECT id_cartera,'1' AS tipo FROM " + TBL_CARTERA_IND_T + " UNION SELECT id_cartera,'2' AS tipo FROM " + TBL_CARTERA_GPO_T + ") AS cartera order by id_cartera, tipo";
        final Cursor row = db.rawQuery(query, null);
        while (row.moveToNext()) {
            int id = row.getInt(0);
            int tipo = row.getInt(1);
            PrestamoIdTipo prestamoIdTipo = new PrestamoIdTipo(id, tipo);
            prestamoIdTipoList.add(prestamoIdTipo);
        }
        return prestamoIdTipoList;
    }

    private void getPrestamoInd(final int id, DBhelper dBhelper, SQLiteDatabase db) {
        //Se prepara la peticion para obtener los prestamos colocando el id de la cartera
        Call<List<MPrestamoRes>> call = prestamoRemoteDatasource.getPrestamosInd(id);
        //Se realiza la peticion para obtener los prestamos
        try {
            Response<List<MPrestamoRes>> listResponse = this.executorUtil.runTaskInThread(call::execute);

            int code = listResponse.code();
            if (listResponse.isSuccessful()) {
                Log.e("ind", "id_carteta: " + id + " code" + code);
                //Se valida el codigo de respuesta*/
                //Se obtiene el listado de prestamos*/
                List<MPrestamoRes> prestamos = listResponse.body();
                if (Objects.isNull(prestamos)) return;

                if (prestamos.size() > 0) {
                    //Se actualiza el estatus de la cartera para que se visualice */
                    ContentValues cv_cartera = new ContentValues();
                    cv_cartera.put("estatus", "1");
                    db.update(TBL_CARTERA_IND_T, cv_cartera, "id_cartera = ?", new String[]{String.valueOf(id)});

                    Cursor row;
                    //Se hace el recorrido de los prestamos obtenidos de la consulta*/
                    for (int i = 0; i < prestamos.size(); i++) {
                        String where = " WHERE id_prestamo = ?";
                        String order = "";
                        String[] args = new String[]{String.valueOf(prestamos.get(i).getId())};
                        //Se busca el prestamo si se encuentre registrado en el movil*/
                        row = dBhelper.getRecords(TBL_PRESTAMOS_IND_T, where, order, args);

                        //Si no exisite en el dispositivo se registra*/
                        if (row.getCount() == 0) { //Registra el prestamo de ind
                            row.close();
                            //Registra los datos del prestamo individual*/
                            HashMap<Integer, String> values = new HashMap<>();
                            values.put(0, String.valueOf(prestamos.get(i).getId()));                    //ID PRESTAMO
                            values.put(1, String.valueOf(prestamos.get(i).getClienteId()));             //CLIENTE ID
                            values.put(2, prestamos.get(i).getNumPrestamo());                           //NUM_PRESTAMO
                            values.put(3, String.valueOf(prestamos.get(i).getNumSolicitud()));          //NUM_SOLICITUD
                            values.put(4, prestamos.get(i).getFechaEntrega());                          //FECHA_ENTREGA
                            values.put(5, String.valueOf(Miscellaneous.validDbl(prestamos.get(i).getMontoOtorgado())));         //MONTO OTORGADO
                            values.put(6, String.valueOf(Miscellaneous.validDbl(prestamos.get(i).getMontoTotal())));            //MONTO TOTAL
                            values.put(7, String.valueOf(Miscellaneous.validDbl(prestamos.get(i).getMontoAmortizacion())));     //MONTO AMORTIZACION
                            values.put(8, String.valueOf(Miscellaneous.validDbl(prestamos.get(i).getMontoRequerido())));        //MONTO REQUERIDO
                            values.put(9, String.valueOf(Miscellaneous.validInt(prestamos.get(i).getNumAmortizacion())));       //NUM AMORTIZACION
                            values.put(10, Miscellaneous.validStr(prestamos.get(i).getFechaEstablecida()));                     //FECHA ESTABLECIDA
                            values.put(11, prestamos.get(i).getTipoCartera());                          //TIPO CARTERA
                            values.put(12, (prestamos.get(i).getPagada().equals("PAGADA")) ? "1" : "0");    //PAGADA
                            values.put(13, Miscellaneous.ObtenerFecha(TIMESTAMP));                      //FECHA CREACION
                            values.put(14, Miscellaneous.ObtenerFecha(TIMESTAMP));                      //FECHA ACTUALIZACION

                            dBhelper.savePrestamosInd(db, TBL_PRESTAMOS_IND_T, values);

                            //Si de los datos del prestamo vienen datos del aval se registran datos del aval*/
                            if (prestamos.get(i).getAval() != null) {
                                //Se registran datos del aval*/
                                MAval mAval = prestamos.get(i).getAval();
                                HashMap<Integer, String> values_aval = new HashMap<>();
                                values_aval.put(0, String.valueOf(prestamos.get(i).getId()));            //ID PRESTAMO
                                values_aval.put(1, String.valueOf(mAval.getId()));                       //AVAL ID
                                values_aval.put(2, mAval.getNombre());                                   //NOMBRE
                                values_aval.put(3, mAval.getParentesco());                               //PARENTESCO
                                values_aval.put(4, mAval.getDireccion());                                //DIRECCIO
                                values_aval.put(5, mAval.getTelefono());                                 //TELEFONO
                                values_aval.put(6, Miscellaneous.ObtenerFecha(TIMESTAMP));               //FECHA CREACION
                                values_aval.put(7, Miscellaneous.ObtenerFecha(TIMESTAMP));               //FECHA ACTUALIZACION

                                dBhelper.saveAval(db, TBL_AVAL_T, values_aval);

                            }

                            //Si tiene listado de amortizaciones se registran las amortizaciones*/
                            if (prestamos.get(i).getAmortizaciones().size() > 0) {
                                //Se hace recorrido del listado de amortizaciones*/
                                for (int j = 0; j < prestamos.get(i).getAmortizaciones().size(); j++) {
                                    //Registra el listado de amortizaciones del prestamo*/
                                    MAmortizacion mAmortizacion = prestamos.get(i).getAmortizaciones().get(j);
                                    HashMap<Integer, String> values_amortiz = new HashMap<>();
                                    values_amortiz.put(0, String.valueOf(mAmortizacion.getId()));                                   //ID AMORTIZACION
                                    values_amortiz.put(1, String.valueOf(mAmortizacion.getPrestamoId()));                           //ID PRESTAMOS
                                    values_amortiz.put(2, mAmortizacion.getFecha());                                                //FECHA
                                    values_amortiz.put(3, (mAmortizacion.getFechaPago() != null) ? mAmortizacion.getFechaPago() : "");  //FECHA PAGO
                                    values_amortiz.put(4, String.valueOf(mAmortizacion.getCapital()));                              //CAPITAL
                                    values_amortiz.put(5, String.valueOf(mAmortizacion.getInteres()));                              //INTERES
                                    values_amortiz.put(6, String.valueOf(mAmortizacion.getIva()));                                  //IVA
                                    values_amortiz.put(7, String.valueOf(mAmortizacion.getComision()));                             //COMISION
                                    values_amortiz.put(8, String.valueOf(mAmortizacion.getTotal()));                                //TOTAL
                                    values_amortiz.put(9, String.valueOf(mAmortizacion.getCapitalPagado()));                        //CAPITAL PAGADO
                                    values_amortiz.put(10, String.valueOf(mAmortizacion.getInteresPagado()));                       //INTERES PAGADO
                                    values_amortiz.put(11, String.valueOf(mAmortizacion.getIvaPagado()));                           //IVA PAGADO
                                    values_amortiz.put(12, String.valueOf(mAmortizacion.getInteresMoratorioPagado()));              //INTERES MORATORIO PAGADO
                                    values_amortiz.put(13, String.valueOf(mAmortizacion.getIvaMoratorioPagado()));                  //IVA_MORATORIO PAGADO
                                    values_amortiz.put(14, String.valueOf(mAmortizacion.getComisionPagada()));                      //COMISION PAGADA
                                    values_amortiz.put(15, String.valueOf(mAmortizacion.getTotalPagado()));                         //TOTAL PAGADO
                                    values_amortiz.put(16, String.valueOf(mAmortizacion.getPagado()));                              //PAGADO
                                    values_amortiz.put(17, String.valueOf(mAmortizacion.getNumero()));                              //NUMERO
                                    values_amortiz.put(18, String.valueOf(mAmortizacion.getDiasAtraso()));                          //DIAS ATRASO
                                    values_amortiz.put(19, Miscellaneous.ObtenerFecha(TIMESTAMP));                                  //FECHA DISPOSITIVO
                                    values_amortiz.put(20, Miscellaneous.ObtenerFecha(TIMESTAMP));                                  //FECHA ACTUALIZADO

                                    dBhelper.saveAmortizaciones(db, TBL_AMORTIZACIONES_T, values_amortiz);
                                }
                            }

                            //Si tiene listado de pagos se registran*/
                            if (prestamos.get(i).getPagos() != null && prestamos.get(i).getPagos().size() > 0) {
                                //Se hace el recorrido de pagos para hacer los registros  de pagos*/
                                for (int k = 0; k < prestamos.get(i).getPagos().size(); k++) {
                                    //Se hace el registro de los pagos realizados*/
                                    MPago mPago = prestamos.get(i).getPagos().get(k);
                                    HashMap<Integer, String> values_pago = new HashMap<>();
                                    values_pago.put(0, String.valueOf(prestamos.get(i).getId()));            //ID PRESTAMO
                                    values_pago.put(1, mPago.getFecha());                                    //FECHA
                                    values_pago.put(2, String.valueOf(mPago.getMonto()));                    //MONTO
                                    values_pago.put(3, Miscellaneous.validStr(mPago.getBanco()));            //BANCO
                                    values_pago.put(4, Miscellaneous.ObtenerFecha(TIMESTAMP));               //FECHA DISPOSITIVO
                                    values_pago.put(5, Miscellaneous.ObtenerFecha(TIMESTAMP));               //FECHA ACTUALIZADO

                                    dBhelper.savePagos(db, TBL_PAGOS_T, values_pago);
                                }
                            }

                            //Registra los datos de telefono de casa y celular del cliente ligado al prestamo*/
                            HashMap<Integer, String> values_tel = new HashMap<>();
                            values_tel.put(0, String.valueOf(prestamos.get(i).getId()));                    //ID_PRESTAMO
                            values_tel.put(1, Miscellaneous.validStr(prestamos.get(i).getTelCasa()));       //TEL_CASA
                            values_tel.put(2, Miscellaneous.validStr(prestamos.get(i).getTelCelular()));    //TEL_CELULAR
                            dBhelper.saveTelefonosCli(db, values_tel);
                        }
                        //En caso de existir el prestamo en el movil actualizar los datos*/
                        else { //Actualiza la prestamo de ind
                            row.moveToFirst();
                            //Actualiza datos del prestamo*/
                            ContentValues cv = new ContentValues();
                            cv.put("fecha_entrega", prestamos.get(i).getFechaEntrega());
                            cv.put("monto_otorgado", String.valueOf(prestamos.get(i).getMontoOtorgado()));
                            cv.put("monto_total", String.valueOf(prestamos.get(i).getMontoTotal()));
                            cv.put("monto_amortizacion", String.valueOf(prestamos.get(i).getMontoAmortizacion()));
                            cv.put("monto_requerido", String.valueOf(prestamos.get(i).getMontoRequerido()));
                            cv.put("num_amortizacion", String.valueOf(prestamos.get(i).getNumAmortizacion()));
                            cv.put("fecha_establecida", prestamos.get(i).getFechaEstablecida());
                            cv.put("tipo_cartera", prestamos.get(i).getTipoCartera());

                            /**Si ya estaba pagado ese prestamo se valida con la nueva informacion obtenida
                             * para saber si hay que volverlo activo o sigue igual a pagado*/
                            if (row.getInt(13) == 0)
                                cv.put("pagada", (prestamos.get(i).getPagada().equals("PAGADA")) ? "1" : "0");
                            cv.put("fecha_actualizado", Miscellaneous.ObtenerFecha(TIMESTAMP));

                            db.update(TBL_PRESTAMOS_IND_T, cv, "_id = ?", new String[]{row.getString(0)});

                            /**Busca todas aquella respuestas correspondientes al prestamos y que fueron de PAGO
                             * para saber si se quita de ruta porque ya se gestionó*/
                            String sql = "SELECT * FROM " + TBL_RESPUESTAS_IND_T + " WHERE id_prestamo = ? AND contacto = ? AND resultado_gestion = ?";
                            Cursor rowRuta = db.rawQuery(sql, new String[]{String.valueOf(prestamos.get(i).getId()), "SI", "PAGO"});

                            if (rowRuta.getCount() > 0) {
                                rowRuta.moveToFirst();
                                int weekFechaEst = 0;
                                Calendar calFechaEst = Calendar.getInstance();

                                try {
                                    //Obtiene el valor del numero de semana del año del dia actual*/
                                    Date dFechaEstablecida = sdf.parse(Miscellaneous.ObtenerFecha(FECHA.toLowerCase()));
                                    calFechaEst.setTime(dFechaEstablecida);
                                    weekFechaEst = calFechaEst.get(Calendar.WEEK_OF_YEAR);

                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }

                                double sumPago = 0;
                                for (int r = 0; r < rowRuta.getCount(); r++) {
                                    //Obtiene la fecha de inicio de gestion*/
                                    String[] fechaFinGes = rowRuta.getString(23).split(" ");
                                    Date dFechaEstablecida = null;
                                    try {
                                        //Obtiene el valor del numero de semana del año de la fecha cuando inicio la gestion*/
                                        dFechaEstablecida = sdf.parse(fechaFinGes[0]);
                                        calFechaEst.setTime(dFechaEstablecida);

                                        //Valida si corresponden a la misma semana*/
                                        if (calFechaEst.get(Calendar.WEEK_OF_YEAR) == weekFechaEst) {
                                            sumPago += rowRuta.getDouble(15);
                                        }
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }

                                    rowRuta.moveToNext();
                                }
                                try {
                                    //Si los pagos realizados son mayores al monto requerido se remueve de ruta*/
                                    if (sumPago >= prestamos.get(i).getMontoRequerido()) {
                                        ContentValues cvInd = new ContentValues();
                                        cvInd.put("is_ruta", 0);
                                        cvInd.put("ruta_obligado", 0);

                                        db.update(TBL_CARTERA_IND_T, cvInd, "id_cartera = ?", new String[]{row.getString(2)});
                                    }
                                } catch (NumberFormatException e) {

                                }

                            }
                            rowRuta.close();

                            //Si se obtienen datos del aval*/
                            if (prestamos.get(i).getAval() != null) {
                                //Actualiza datos del aval*/
                                MAval mAval = prestamos.get(i).getAval();
                                ContentValues cv_aval = new ContentValues();
                                cv_aval.put("id_aval", String.valueOf(mAval.getId()));                      //AVAL ID
                                cv_aval.put("nombre", mAval.getNombre());                                   //NOMBRE
                                cv_aval.put("parentesco", mAval.getParentesco());                           //PARENTESCO
                                cv_aval.put("direccion", mAval.getDireccion());                             //DIRECCIO
                                cv_aval.put("telefono", mAval.getTelefono());                               //TELEFONO
                                cv_aval.put("fecha_actualizado", Miscellaneous.ObtenerFecha(TIMESTAMP));    //FECHA ACTUALIZACION

                                db.update(TBL_AVAL_T, cv_aval, "id_prestamo = ?", new String[]{String.valueOf(prestamos.get(i).getId())});
                            }

                            //Si se obtiene datos de amortizaciones se actualizan datos*/
                            if (prestamos.get(i).getAmortizaciones().size() > 0) {
                                MAmortizacion mAmortizacionExiste = prestamos.get(i).getAmortizaciones().get(0);
                                AmortizacionDao amortizacionDao = new AmortizacionDao(sidertMovilApplication);
                                Amortizacion amortizacionExiste = amortizacionDao.findByIdAmortizacion(String.valueOf(mAmortizacionExiste.getId()));

                                Log.e("AQUI", "EXISTO EN PRESTAMOS");

                                if (amortizacionExiste != null) {

                                    Log.e("AQUI AMORTIZACION EXISTO", amortizacionExiste.getIdAmortizacion());

                                    //Se realiza recorrido para el listado de amortizaciones*/
                                    for (int j = 0; j < prestamos.get(i).getAmortizaciones().size(); j++) {
                                        //Actualiza los datos de amortizacion del prestamo*/
                                        MAmortizacion mAmortizacion = prestamos.get(i).getAmortizaciones().get(j);
                                        ContentValues cv_amortiz = new ContentValues();
                                        cv_amortiz.put("fecha", mAmortizacion.getFecha());                                                      //FECHA
                                        cv_amortiz.put("fecha_pago", (mAmortizacion.getFechaPago() != null) ? mAmortizacion.getFechaPago() : "");   //FECHA PAGO
                                        cv_amortiz.put("capital", String.valueOf(mAmortizacion.getCapital()));                                  //CAPITAL
                                        cv_amortiz.put("interes", String.valueOf(mAmortizacion.getInteres()));                                  //INTERES
                                        cv_amortiz.put("iva", String.valueOf(mAmortizacion.getIva()));                                          //IVA
                                        cv_amortiz.put("comision", String.valueOf(mAmortizacion.getComision()));                                //COMISION
                                        cv_amortiz.put("total", String.valueOf(mAmortizacion.getTotal()));                                      //TOTAL
                                        cv_amortiz.put("capital_pagado", String.valueOf(mAmortizacion.getCapitalPagado()));                     //CAPITAL PAGADO
                                        cv_amortiz.put("interes_pagado", String.valueOf(mAmortizacion.getInteresPagado()));                     //INTERES PAGADO
                                        cv_amortiz.put("iva_pagado", String.valueOf(mAmortizacion.getIvaPagado()));                             //IVA PAGADO
                                        cv_amortiz.put("interes_moratorio_pagado", String.valueOf(mAmortizacion.getInteresMoratorioPagado()));  //INTERES MORATORIO PAGADO
                                        cv_amortiz.put("iva_moratorio_pagado", String.valueOf(mAmortizacion.getIvaMoratorioPagado()));          //IVA_MORATORIO PAGADO
                                        cv_amortiz.put("comision_pagada", String.valueOf(mAmortizacion.getComisionPagada()));                   //COMISION PAGADA
                                        String sqlAmortiz = "SELECT total, total_pagado FROM " + TBL_AMORTIZACIONES_T + " WHERE id_amortizacion = ? AND id_prestamo = ?";
                                        Cursor rowAmortiz = db.rawQuery(sqlAmortiz, new String[]{String.valueOf(mAmortizacion.getId()), String.valueOf(prestamos.get(i).getId())});
                                        if (rowAmortiz.getCount() > 0) {
                                            rowAmortiz.moveToFirst();
                                            if (rowAmortiz.getDouble(0) > rowAmortiz.getDouble(1)) {
                                                cv_amortiz.put("total_pagado", String.valueOf(mAmortizacion.getTotalPagado()));                         //TOTAL PAGADO
                                                cv_amortiz.put("pagado", String.valueOf(mAmortizacion.getPagado()));                                    //PAGADO
                                                cv_amortiz.put("numero", String.valueOf(mAmortizacion.getNumero()));                                    //NUMERO
                                                cv_amortiz.put("dias_atraso", String.valueOf(mAmortizacion.getDiasAtraso()));                           //DIAS ATRASO
                                            }
                                        }
                                        rowAmortiz.close();
                                        cv_amortiz.put("fecha_actualizado", Miscellaneous.ObtenerFecha(TIMESTAMP));                             //FECHA ACTUALIZADO

                                        db.update(TBL_AMORTIZACIONES_T, cv_amortiz, "id_amortizacion = ? AND id_prestamo = ?", new String[]{String.valueOf(mAmortizacion.getId()), String.valueOf(prestamos.get(i).getId())});
                                    }
                                } else {
                                    Log.e("AQUI AMORTIZACION NO EXISTO", String.valueOf(prestamos.get(i).getId()));

                                    amortizacionDao.deleteByIdPrestamo(String.valueOf(prestamos.get(i).getId()));

                                    for (int j = 0; j < prestamos.get(i).getAmortizaciones().size(); j++) {
                                        MAmortizacion mAmortizacion = prestamos.get(i).getAmortizaciones().get(j);

                                        Amortizacion amortizacion = new Amortizacion();
                                        amortizacion.setIdAmortizacion(String.valueOf(mAmortizacion.getId()));
                                        amortizacion.setIdPrestamo(String.valueOf(mAmortizacion.getPrestamoId()));
                                        amortizacion.setFecha(mAmortizacion.getFecha());
                                        amortizacion.setFechaPago((mAmortizacion.getFechaPago() != null) ? mAmortizacion.getFechaPago() : "");
                                        amortizacion.setCapital(String.valueOf(mAmortizacion.getCapital()));
                                        amortizacion.setInteres(String.valueOf(mAmortizacion.getInteres()));
                                        amortizacion.setIva(String.valueOf(mAmortizacion.getIva()));
                                        amortizacion.setComision(String.valueOf(mAmortizacion.getComision()));
                                        amortizacion.setTotal(String.valueOf(mAmortizacion.getTotal()));
                                        amortizacion.setCapitalPagado(String.valueOf(mAmortizacion.getCapitalPagado()));
                                        amortizacion.setInteresPagado(String.valueOf(mAmortizacion.getInteresPagado()));
                                        amortizacion.setIvaPagado(String.valueOf(mAmortizacion.getIvaPagado()));
                                        amortizacion.setInteresMoratorioPagado(String.valueOf(mAmortizacion.getInteresMoratorioPagado()));
                                        amortizacion.setIvaMoratorioPagado(String.valueOf(mAmortizacion.getIvaMoratorioPagado()));
                                        amortizacion.setComisionPagada(String.valueOf(mAmortizacion.getComisionPagada()));
                                        amortizacion.setTotalPagado(String.valueOf(mAmortizacion.getTotalPagado()));
                                        amortizacion.setPagado(String.valueOf(mAmortizacion.getPagado()));
                                        amortizacion.setNumero(String.valueOf(mAmortizacion.getNumero()));
                                        amortizacion.setDiasAtraso(String.valueOf(mAmortizacion.getDiasAtraso()));
                                        amortizacion.setFechaDispositivo(Miscellaneous.ObtenerFecha(TIMESTAMP));
                                        amortizacion.setFechaActualizado(Miscellaneous.ObtenerFecha(TIMESTAMP));

                                        amortizacionDao.store(amortizacion);
                                    }
                                }
                            }

                            //Si se obtienen pagos  del prestamo se actualizan datos*/
                            if (prestamos.get(i).getPagos() != null && prestamos.get(i).getPagos().size() > 0) {
                                for (int k = 0; k < prestamos.get(i).getPagos().size(); k++) {
                                    MPago mPago = prestamos.get(i).getPagos().get(k);
                                    Cursor row_pago = dBhelper.getRecords(TBL_PAGOS_T, " WHERE id_prestamo = ? AND fecha = ? AND monto = ? AND banco = ?", "",
                                            new String[]{String.valueOf(prestamos.get(i).getId()), mPago.getFecha(), String.valueOf(mPago.getMonto()), mPago.getBanco(),});
                                    if (row_pago.getCount() == 0) {
                                        //Se crea registro por si es un pago nuevo*/
                                        HashMap<Integer, String> cv_pago = new HashMap<>();
                                        cv_pago.put(0, String.valueOf(prestamos.get(i).getId()));            //ID PRESTAMO
                                        cv_pago.put(1, mPago.getFecha());                                    //FECHA
                                        cv_pago.put(2, String.valueOf(mPago.getMonto()));                    //MONTO
                                        cv_pago.put(3, Miscellaneous.validStr(mPago.getBanco()));            //BANCO
                                        cv_pago.put(4, Miscellaneous.ObtenerFecha(TIMESTAMP));               //FECHA DISPOSITIVO
                                        cv_pago.put(5, Miscellaneous.ObtenerFecha(TIMESTAMP));               //FECHA ACTUALIZADO

                                        dBhelper.savePagos(db, TBL_PAGOS_T, cv_pago);
                                    }
                                    row_pago.close();
                                }
                            }

                            //Actualiza datos de telefono celular del prestamo*/
                            ContentValues cv_telefonos = new ContentValues();
                            cv_telefonos.put("tel_casa", Miscellaneous.validStr(prestamos.get(i).getTelCasa()));
                            cv_telefonos.put("tel_celular", Miscellaneous.validStr(prestamos.get(i).getTelCelular()));
                            db.update(TBL_TELEFONOS_CLIENTE, cv_telefonos, "id_prestamo = ?", new String[]{String.valueOf(prestamos.get(i).getId())});
                        }
                        row.close();

                        //BUSCAR SI SE TIENE LOS DOCUMENTOS DE EXPEDIENTES
                        if (sessionManager.getUser().get(5).contains("ROLE_GESTOR")) {
                            DocumentoClienteDao documentoClienteDao = new DocumentoClienteDao(sidertMovilApplication);
                            List<DocumentoCliente> documentosClientes = documentoClienteDao.findAllByPrestamoId(prestamos.get(i).getId());

                            if (documentosClientes.size() <= 1) {
                                DocumentoClienteService api2 = RetrofitClient.generalRF(Constants.CONTROLLER_MOVIL, sidertMovilApplication).create(DocumentoClienteService.class);
                                Call<List<DocumentoCliente>> callDocumentoCliente = api2.show(prestamos.get(i).getId(), "Bearer " + sessionManager.getUser().get(7));

                                try {
                                    Response<List<DocumentoCliente>> response = this.executorUtil.runTaskInThread(callDocumentoCliente::execute);

                                    Log.e("ind", "id_carteta: " + id + " code" + response.code());
                                    if (response.code() == 200) {
                                        List<DocumentoCliente> documentosClientesResponse = response.body();
                                        for (DocumentoCliente dc : documentosClientesResponse) {
                                            DocumentoCliente dcTemp = documentoClienteDao.findByPrestamoIdAndTipo(dc.getPrestamoId(), dc.getTipo());
                                            if (dcTemp == null) {
                                                DocumentoCliente documentoCliente = new DocumentoCliente();
                                                documentoCliente.setArchivoBase64(dc.getArchivoBase64());
                                                documentoCliente.setClavecliente(dc.getClavecliente());
                                                documentoCliente.setClienteId(dc.getClienteId());
                                                documentoCliente.setFecha(dc.getFecha());
                                                documentoCliente.setGrupoId(dc.getGrupoId());
                                                //documentoCliente.setId(dc.getId());
                                                documentoCliente.setNumSolicitud(dc.getNumSolicitud());
                                                documentoCliente.setPrestamoId(dc.getPrestamoId());
                                                documentoCliente.setTipo(dc.getTipo());
                                                documentoClienteDao.store(documentoCliente);
                                            }
                                        }
                                    }
                                } catch (ExecutionException |
                                         InterruptedException executionException) {
                                    this.klassTag.e(executionException);
                                }
                            }
                        }
                    } //Fin Ciclo For
                }//Fin IF
                else {
                    /**En caso de que no se obtuvieron datos de prestamos se cambia el estatus
                     * de la cartera para que no se muestre*/
                    ContentValues cv = new ContentValues();
                    cv.put("estatus", "0");
                    db.update(TBL_CARTERA_IND_T, cv, "id_cartera = ?", new String[]{String.valueOf(id)});
                }
            } else {
                Log.e("ERROR AQUI", listResponse.message());
            }
        } catch (ExecutionException executionException) {
            Log.e("DescargaDatosError", executionException.getMessage(), executionException);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (TimeoutException e) {
            throw new RuntimeException(e);
        }
    }

    private void getPrestmoGpo(final int id, DBhelper dBhelper, SQLiteDatabase db) {
        //Se prepara la peticion colocando el id de la cartera */
        Timber.tag(TAG).i("Prestamo del grupo actual %s", id);
        Call<List<MPrestamoGpoRes>> call = this.prestamoRemoteDatasource.getPrestamosGpo(id);
        //Se realiza la peticion para obtener los datos del prestamo grupal*/

        try {
            Response<List<MPrestamoGpoRes>> response = this.executorUtil.runTaskInThread(call::execute);
            if (response.isSuccessful()) {
                //Se obtiene el listado de prestamo*/
                List<MPrestamoGpoRes> prestamos = response.body();
                if (prestamos.size() > 0) {

                    Timber.tag(TAG).i("Procesando prestamo con el id %d", id);
                    //Actualiza el estatus de la cartera para que se siga visualizando*/
                    ContentValues cv_cartera = new ContentValues();
                    cv_cartera.put("estatus", "1");
                    db.update(TBL_CARTERA_GPO_T, cv_cartera, "id_cartera = ?", new String[]{String.valueOf(id)});

                    Cursor row;

                    //Se recorre el listado de prestamos grupales*/
                    for (int i = 0; i < prestamos.size(); i++) {
                        String where = " WHERE id_prestamo = ?";
                        String order = "";
                        String[] args = new String[]{String.valueOf(prestamos.get(i).getId())};

                        //Se busca el prestamo para saber si existe en el movil*/
                        row = dBhelper.getRecords(TBL_PRESTAMOS_GPO_T, where, order, args);

                        //En caso de no existir en el movil*/
                        if (row.getCount() == 0) { //Registra el prestamo de gpo
                            //Se registra el prestamo en el movil*/
                            HashMap<Integer, String> values = new HashMap<>();
                            values.put(0, String.valueOf(prestamos.get(i).getId()));                    //ID PRESTAMO
                            values.put(1, String.valueOf(prestamos.get(i).getGrupoId()));               //GRUPO ID
                            values.put(2, prestamos.get(i).getNumPrestamo());                           //NUM_PRESTAMO
                            values.put(3, String.valueOf(prestamos.get(i).getNumSolicitud()));          //NUM_SOLICITUD
                            values.put(4, prestamos.get(i).getFechaEntrega());                          //FECHA_ENTREGA
                            values.put(5, String.valueOf(Miscellaneous.validDbl(prestamos.get(i).getMontoOtorgado())));         //MONTO OTORGADO
                            values.put(6, String.valueOf(Miscellaneous.validDbl(prestamos.get(i).getMontoTotal())));            //MONTO TOTAL
                            values.put(7, String.valueOf(Miscellaneous.validDbl(prestamos.get(i).getMontoAmortizacion())));     //MONTO AMORTIZACION
                            values.put(8, String.valueOf(Miscellaneous.validDbl(prestamos.get(i).getMontoRequerido())));        //MONTO REQUERIDO
                            values.put(9, String.valueOf(Miscellaneous.validInt(prestamos.get(i).getNumAmortizacion())));       //NUM AMORTIZACION
                            values.put(10, Miscellaneous.validStr(prestamos.get(i).getFechaEstablecida()));                     //FECHA ESTABLECIDA
                            values.put(11, prestamos.get(i).getTipoCartera());                          //TIPO CARTERA
                            values.put(12, (prestamos.get(i).getPagada().equals("PAGADA")) ? "1" : "0");    //PAGADA
                            values.put(13, Miscellaneous.ObtenerFecha(TIMESTAMP));                      //FECHA CREACION
                            values.put(14, Miscellaneous.ObtenerFecha(TIMESTAMP));                      //FECHA ACTUALIZACION

                            dBhelper.savePrestamosGpo(db, TBL_PRESTAMOS_GPO_T, values);

                            //Si se obtienen datos de integrantes*/
                            if (prestamos.get(i).getIntegrantes() != null) {
                                //Se recorre el listado de integrantes para ser registrados*/
                                for (int l = 0; l < prestamos.get(i).getIntegrantes().size(); l++) {
                                    //Se registra los datos del integrante*/
                                    MIntegrante mIntegrante = prestamos.get(i).getIntegrantes().get(l);
                                    HashMap<Integer, String> values_miembro = new HashMap<>();
                                    values_miembro.put(0, String.valueOf(prestamos.get(i).getId()));            //ID PRESTAMO
                                    values_miembro.put(1, String.valueOf(mIntegrante.getId()));                 //INTEGRANTE ID
                                    values_miembro.put(2, String.valueOf(mIntegrante.getNumSolicitud()));       //NUM SOLICITUD
                                    values_miembro.put(3, String.valueOf(mIntegrante.getGrupoId()));            //GRUPO ID
                                    values_miembro.put(4, mIntegrante.getNombre());                             //NOMBRE
                                    values_miembro.put(5, mIntegrante.getDireccion());                          //DIRECCION
                                    values_miembro.put(6, mIntegrante.getTelCasa());                            //TEL CASA
                                    values_miembro.put(7, mIntegrante.getTelCelular());                         //TEL CELULAR
                                    values_miembro.put(8, mIntegrante.getTipo());                               //TIPO
                                    values_miembro.put(9, mIntegrante.getMontoPrestamo());                      //MONTO PRESTAMO
                                    values_miembro.put(10, mIntegrante.getMontoRequerido());                     //MONTO REQUERIDO
                                    values_miembro.put(11, Miscellaneous.ObtenerFecha(TIMESTAMP));               //FECHA CREACION
                                    values_miembro.put(12, Miscellaneous.ObtenerFecha(TIMESTAMP));               //FECHA ACTUALIZACION
                                    values_miembro.put(13, mIntegrante.getClave());                              //CLAVE
                                    values_miembro.put(14, String.valueOf(mIntegrante.getPrestamoId()));         //ID PRESTAMO
                                    values_miembro.put(15, "");                                                  //CURP
                                    //values_miembro.put(15, mIntegrante.getCurp());                               //CURP
                                    dBhelper.saveMiembros(db, values_miembro);

                                    //BUSCAR SI SE TIENE LOS DOCUMENTOS DE EXPEDIENTES
                                    List<String> user = sessionManager.getUser();
                                    String roles = user.get(5);
                                    if (roles != null && roles.contains("ROLE_GESTOR")) {
                                        DocumentoClienteDao documentoClienteDao = new DocumentoClienteDao(sidertMovilApplication);
                                        List<DocumentoCliente> documentosClientes = documentoClienteDao.findAllByPrestamoId(mIntegrante.getPrestamoId());

                                        if (documentosClientes.size() <= 1) {
                                            DocumentoClienteService api2 = RetrofitClient.generalRF(Constants.CONTROLLER_MOVIL, sidertMovilApplication).create(DocumentoClienteService.class);
                                            Call<List<DocumentoCliente>> callDocumentoCliente = api2.show(mIntegrante.getPrestamoId(), "Bearer " + sessionManager.getUser().get(7));

                                            try {

                                                Response<List<DocumentoCliente>> response22 = this.executorUtil.runTaskInThread(callDocumentoCliente::execute);
                                                Log.e("ind", "id_carteta: " + id + " code" + response22.code());
                                                if (response22.code() == 200) {

                                                    List<DocumentoCliente> documentosClientesResponse = response22.body();

                                                    for (DocumentoCliente dc : documentosClientesResponse) {
                                                        DocumentoCliente dcTemp = documentoClienteDao.findByPrestamoIdAndTipo(dc.getPrestamoId(), dc.getTipo());
                                                        if (dcTemp == null) {
                                                            DocumentoCliente documentoCliente = new DocumentoCliente();

                                                            documentoCliente.setArchivoBase64(dc.getArchivoBase64());
                                                            documentoCliente.setClavecliente(dc.getClavecliente());
                                                            documentoCliente.setClienteId(dc.getClienteId());
                                                            documentoCliente.setFecha(dc.getFecha());
                                                            documentoCliente.setGrupoId(dc.getGrupoId());
                                                            //documentoCliente.setId(dc.getId());
                                                            documentoCliente.setNumSolicitud(dc.getNumSolicitud());
                                                            documentoCliente.setPrestamoId(dc.getPrestamoId());
                                                            documentoCliente.setTipo(dc.getTipo());

                                                            documentoClienteDao.store(documentoCliente);
                                                        }
                                                    }
                                                }
                                            } catch (ExecutionException executionException) {
                                                Log.e("DescargaDatosError", executionException.getMessage(), executionException);
                                            } catch (InterruptedException e) {
                                                throw new RuntimeException(e);
                                            }
                                        }
                                    }
                                }
                            }

                            //Si se tienen datos de amortizaciones obtenidos en la peticion*/
                            if (prestamos.get(i).getAmortizaciones().size() > 0) {
                                //Se recorre el listado de amortizaciones*/
                                for (int j = 0; j < prestamos.get(i).getAmortizaciones().size(); j++) {
                                    //Se registran los datos de las amortizaciones*/
                                    MAmortizacion mAmortizacion = prestamos.get(i).getAmortizaciones().get(j);
                                    HashMap<Integer, String> values_amortiz = new HashMap<>();
                                    values_amortiz.put(0, String.valueOf(mAmortizacion.getId()));                      //ID AMORTIZACION
                                    values_amortiz.put(1, String.valueOf(mAmortizacion.getPrestamoId()));              //ID PRESTAMOS
                                    values_amortiz.put(2, mAmortizacion.getFecha());                                   //FECHA
                                    values_amortiz.put(3, (mAmortizacion.getFechaPago() != null) ? mAmortizacion.getFechaPago() : "");        //FECHA PAGO
                                    values_amortiz.put(4, String.valueOf(mAmortizacion.getCapital()));                 //CAPITAL
                                    values_amortiz.put(5, String.valueOf(mAmortizacion.getInteres()));                 //INTERES
                                    values_amortiz.put(6, String.valueOf(mAmortizacion.getIva()));                     //IVA
                                    values_amortiz.put(7, String.valueOf(mAmortizacion.getComision()));                //COMISION
                                    values_amortiz.put(8, String.valueOf(mAmortizacion.getTotal()));                   //TOTAL
                                    values_amortiz.put(9, String.valueOf(mAmortizacion.getCapitalPagado()));           //CAPITAL PAGADO
                                    values_amortiz.put(10, String.valueOf(mAmortizacion.getInteresPagado()));          //INTERES PAGADO
                                    values_amortiz.put(11, String.valueOf(mAmortizacion.getIvaPagado()));              //IVA PAGADO
                                    values_amortiz.put(12, String.valueOf(mAmortizacion.getInteresMoratorioPagado())); //INTERES MORATORIO PAGADO
                                    values_amortiz.put(13, String.valueOf(mAmortizacion.getIvaMoratorioPagado()));     //IVA_MORATORIO PAGADO
                                    values_amortiz.put(14, String.valueOf(mAmortizacion.getComisionPagada()));         //COMISION PAGADA
                                    values_amortiz.put(15, String.valueOf(mAmortizacion.getTotalPagado()));            //TOTAL PAGADO
                                    values_amortiz.put(16, String.valueOf(mAmortizacion.getPagado()));                 //PAGADO
                                    values_amortiz.put(17, String.valueOf(mAmortizacion.getNumero()));                 //NUMERO
                                    values_amortiz.put(18, String.valueOf(mAmortizacion.getDiasAtraso()));             //DIAS ATRASO
                                    values_amortiz.put(19, Miscellaneous.ObtenerFecha(TIMESTAMP));                     //FECHA DISPOSITIVO
                                    values_amortiz.put(20, Miscellaneous.ObtenerFecha(TIMESTAMP));                     //FECHA ACTUALIZADO

                                    dBhelper.saveAmortizaciones(db, TBL_AMORTIZACIONES_T, values_amortiz);
                                }
                            }

                            //Si se tienen datos de pagos obtenidos en la peticion*/
                            if (prestamos.get(i).getPagos() != null && prestamos.get(i).getPagos().size() > 0) {
                                //Se recorre el listado de pagos*/
                                for (int k = 0; k < prestamos.get(i).getPagos().size(); k++) {
                                    //Registra los pagos */
                                    MPago mPago = prestamos.get(i).getPagos().get(k);
                                    HashMap<Integer, String> values_pago = new HashMap<>();
                                    values_pago.put(0, String.valueOf(prestamos.get(i).getId()));            //ID PRESTAMO
                                    values_pago.put(1, mPago.getFecha());                                    //FECHA
                                    values_pago.put(2, String.valueOf(mPago.getMonto()));                    //MONTO
                                    values_pago.put(3, mPago.getBanco());                                     //BANCO
                                    values_pago.put(4, Miscellaneous.ObtenerFecha(TIMESTAMP));               //FECHA DISPOSITIVO
                                    values_pago.put(5, Miscellaneous.ObtenerFecha(TIMESTAMP));               //FECHA ACTUALIZADO

                                    dBhelper.savePagos(db, TBL_PAGOS_T, values_pago);
                                }
                            }
                        } else { //Actualiza la prestamo gpo
                            //Actualiza los datos del prestamo*/
                            row.moveToFirst();
                            ContentValues cv_prestamo = new ContentValues();
                            cv_prestamo.put("fecha_entrega", prestamos.get(i).getFechaEntrega());                          //FECHA_ENTREGA
                            cv_prestamo.put("monto_otorgado", String.valueOf(prestamos.get(i).getMontoOtorgado()));         //MONTO OTORGADO
                            cv_prestamo.put("monto_total", String.valueOf(prestamos.get(i).getMontoTotal()));            //MONTO TOTAL
                            cv_prestamo.put("monto_amortizacion", String.valueOf(prestamos.get(i).getMontoAmortizacion()));     //MONTO AMORTIZACION
                            cv_prestamo.put("monto_requerido", String.valueOf(prestamos.get(i).getMontoRequerido()));        //MONTO REQUERIDO
                            cv_prestamo.put("num_amortizacion", String.valueOf(prestamos.get(i).getNumAmortizacion()));       //NUM AMORTIZACION
                            cv_prestamo.put("fecha_establecida", prestamos.get(i).getFechaEstablecida());                     //FECHA ESTABLECIDA
                            cv_prestamo.put("tipo_cartera", prestamos.get(i).getTipoCartera());                          //TIPO CARTERA
                            if (row.getInt(13) == 0)
                                cv_prestamo.put("pagada", (prestamos.get(i).getPagada().equals("PAGADA")) ? "1" : "0");                     //PAGADA
                            cv_prestamo.put("fecha_actualizado", Miscellaneous.ObtenerFecha(TIMESTAMP));                      //FECHA ACTUALIZACION

                            db.update(TBL_PRESTAMOS_GPO_T, cv_prestamo, "_id = ?", new String[]{row.getString(0)});

                            /**Se buscan las respuestas de grupo que correspondan al prestamo y que sean de pago para
                             * saber si se remueven de ruta o no*/
                            String sql = "SELECT * FROM " + TBL_RESPUESTAS_GPO_T + " WHERE id_prestamo = ? AND contacto = ? AND resultado_gestion = ?";
                            Cursor rowRuta = db.rawQuery(sql, new String[]{String.valueOf(prestamos.get(i).getId()), "SI", "PAGO"});

                            if (rowRuta.getCount() > 0) {
                                rowRuta.moveToFirst();
                                int weekFechaEst = 0;
                                Calendar calFechaEst = Calendar.getInstance();

                                try {
                                    //Obtiene el numero de la semana del año del dia actual*/
                                    Date dFechaEstablecida = sdf.parse(Miscellaneous.ObtenerFecha(FECHA.toLowerCase()));
                                    calFechaEst.setTime(dFechaEstablecida);
                                    weekFechaEst = calFechaEst.get(Calendar.WEEK_OF_YEAR);

                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }

                                double sumPago = 0;

                                for (int r = 0; r < rowRuta.getCount(); r++) {
                                    //Se obtiene la fecha de fin gestion de la respuesta*/
                                    String[] fechaFinGes = rowRuta.getString(23).split(" ");
                                    Date dFechaEstablecida = null;
                                    try {
                                        //Obtiene el numero de semana del año con la fecha de fin gestion*/
                                        dFechaEstablecida = sdf.parse(fechaFinGes[0]);
                                        calFechaEst.setTime(dFechaEstablecida);
                                        //Se compara el numero de semana de la gestion con el numero de la semana actual*/
                                        if (calFechaEst.get(Calendar.WEEK_OF_YEAR) == weekFechaEst) {
                                            sumPago += rowRuta.getDouble(15);
                                        }
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }

                                    rowRuta.moveToNext();
                                }
                                try {
                                    //Si la suma de los pagos de las recuperaciones es mayor o igual a lo requerido se quita de ruta*/
                                    if (sumPago >= prestamos.get(i).getMontoRequerido()) {
                                        ContentValues cvGpo = new ContentValues();
                                        cvGpo.put("is_ruta", 0);
                                        cvGpo.put("ruta_obligado", 0);

                                        db.update(TBL_CARTERA_GPO_T, cvGpo, "id_cartera = ?", new String[]{String.valueOf(row.getString(2))});
                                    }
                                } catch (NumberFormatException e) {

                                }
                            }
                            rowRuta.close();

                            //Actualiza datos de los integrantes*/
                            if (prestamos.get(i).getIntegrantes() != null) {
                                for (int l = 0; l < prestamos.get(i).getIntegrantes().size(); l++) {


                                    //Actualzia los datos del integrante*/
                                    MIntegrante mIntegrante = prestamos.get(i).getIntegrantes().get(l);


                                    String sqlIntegrante = "" +
                                            "SELECT " +
                                            "m.* " +
                                            "FROM " + TBL_MIEMBROS_GPO_T + " AS m " +
                                            "WHERE m.id_prestamo = ? " +
                                            "AND m.id_integrante = ? ";

                                    Cursor rowIntegrante = db.rawQuery(sqlIntegrante, new String[]{String.valueOf(prestamos.get(i).getId()), String.valueOf(mIntegrante.getId())});

                                    if (rowIntegrante.getCount() > 0) {
                                        ContentValues cv_miembro = new ContentValues();
                                        cv_miembro.put("nombre", mIntegrante.getNombre());                                      //NOMBRE
                                        cv_miembro.put("direccion", mIntegrante.getDireccion());                                //DIRECCION
                                        cv_miembro.put("tel_casa", mIntegrante.getTelCasa());                                   //TEL CASA
                                        cv_miembro.put("tel_celular", mIntegrante.getTelCelular());                             //TEL CELULAR
                                        cv_miembro.put("tipo_integrante", mIntegrante.getTipo());                               //TIPO
                                        cv_miembro.put("monto_prestamo", mIntegrante.getMontoPrestamo());                       //MONTO PRESTAMO
                                        cv_miembro.put("monto_requerido", mIntegrante.getMontoRequerido());                     //MONTO REQUERIDO
                                        cv_miembro.put("fecha_actualizado", Miscellaneous.ObtenerFecha(TIMESTAMP));             //FECHA ACTUALIZACION
                                        cv_miembro.put("clave", mIntegrante.getClave());                                        //CLAVE
                                        cv_miembro.put("id_prestamo_integrante", String.valueOf(mIntegrante.getPrestamoId()));  //PRESTAMO ID
                                        cv_miembro.put("curp", "");                                                             //CURP
                                        //cv_miembro.put("curp", mIntegrante.getCurp());                                          //CURP

                                        db.update(TBL_MIEMBROS_GPO_T, cv_miembro, "id_prestamo = ? AND id_integrante = ?",
                                                new String[]{String.valueOf(prestamos.get(i).getId()), String.valueOf(mIntegrante.getId())});
                                    } else {
                                        HashMap<Integer, String> values_miembro = new HashMap<>();
                                        values_miembro.put(0, String.valueOf(prestamos.get(i).getId()));            //ID PRESTAMO
                                        values_miembro.put(1, String.valueOf(mIntegrante.getId()));                 //INTEGRANTE ID
                                        values_miembro.put(2, String.valueOf(mIntegrante.getNumSolicitud()));       //NUM SOLICITUD
                                        values_miembro.put(3, String.valueOf(mIntegrante.getGrupoId()));            //GRUPO ID
                                        values_miembro.put(4, mIntegrante.getNombre());                             //NOMBRE
                                        values_miembro.put(5, mIntegrante.getDireccion());                          //DIRECCION
                                        values_miembro.put(6, mIntegrante.getTelCasa());                            //TEL CASA
                                        values_miembro.put(7, mIntegrante.getTelCelular());                         //TEL CELULAR
                                        values_miembro.put(8, mIntegrante.getTipo());                               //TIPO
                                        values_miembro.put(9, mIntegrante.getMontoPrestamo());                      //MONTO PRESTAMO
                                        values_miembro.put(10, mIntegrante.getMontoRequerido());                     //MONTO REQUERIDO
                                        values_miembro.put(11, Miscellaneous.ObtenerFecha(TIMESTAMP));               //FECHA CREACION
                                        values_miembro.put(12, Miscellaneous.ObtenerFecha(TIMESTAMP));               //FECHA ACTUALIZACION
                                        values_miembro.put(13, mIntegrante.getClave());                              //CLAVE
                                        values_miembro.put(14, String.valueOf(mIntegrante.getPrestamoId()));         //ID PRESTAMO
                                        values_miembro.put(15, "");                                                  //CURP
                                        //values_miembro.put(15, mIntegrante.getCurp());                               //CURP
                                        dBhelper.saveMiembros(db, values_miembro);

                                    }

                                    rowIntegrante.close();

                                    //BUSCAR SI SE TIENE LOS DOCUMENTOS DE EXPEDIENTES
                                    if (sessionManager.getUser().get(5).contains("ROLE_GESTOR")) {
                                        DocumentoClienteDao documentoClienteDao = new DocumentoClienteDao(sidertMovilApplication);
                                        List<DocumentoCliente> documentosClientes = documentoClienteDao.findAllByPrestamoId(mIntegrante.getPrestamoId());

                                        if (documentosClientes.size() <= 1) {
                                            Call<List<DocumentoCliente>> callDocumentoCliente = documentoRemoteDatasource.show(mIntegrante.getPrestamoId());

                                            try {
                                                Response<List<DocumentoCliente>> response22 = executorUtil.runTaskInThread(callDocumentoCliente::execute);
                                                Timber.tag(TAG).e("Cartera Id: %d | Http Status: %d", id, response22.code());
                                                if (response22.isSuccessful()) {
                                                    List<DocumentoCliente> documentosClientesResponse = response22.body();
                                                    if (documentosClientesResponse != null) {
                                                        for (DocumentoCliente dc : documentosClientesResponse) {
                                                            DocumentoCliente dcTemp = documentoClienteDao.findByPrestamoIdAndTipo(dc.getPrestamoId(), dc.getTipo());
                                                            if (dcTemp == null) {
                                                                DocumentoCliente documentoCliente = new DocumentoCliente();

                                                                documentoCliente.setArchivoBase64(dc.getArchivoBase64());
                                                                documentoCliente.setClavecliente(dc.getClavecliente());
                                                                documentoCliente.setClienteId(dc.getClienteId());
                                                                documentoCliente.setFecha(dc.getFecha());
                                                                documentoCliente.setGrupoId(dc.getGrupoId());
                                                                //documentoCliente.setId(dc.getId());
                                                                documentoCliente.setNumSolicitud(dc.getNumSolicitud());
                                                                documentoCliente.setPrestamoId(dc.getPrestamoId());
                                                                documentoCliente.setTipo(dc.getTipo());

                                                                documentoClienteDao.store(documentoCliente);
                                                            }
                                                        }
                                                    }
                                                }
                                            } catch (ExecutionException executionException) {
                                                Timber.tag(TAG).e(executionException);
                                            } catch (InterruptedException e) {
                                                throw new RuntimeException(e);
                                            }
                                        }
                                    }
                                }
                            }//Termina If de actualizado de integrantes

                            //Actualiza los datos de las amortizaciones*/
                            if (prestamos.get(i).getAmortizaciones().size() > 0) {
                                MAmortizacion mAmortizacionExiste = prestamos.get(i).getAmortizaciones().get(0);
                                AmortizacionDao amortizacionDao = new AmortizacionDao(sidertMovilApplication);
                                Amortizacion amortizacionExiste = amortizacionDao.findByIdAmortizacion(String.valueOf(mAmortizacionExiste.getId()));

                                if (amortizacionExiste != null) {
                                    for (int j = 0; j < prestamos.get(i).getAmortizaciones().size(); j++) {
                                        MAmortizacion mAmortizacion = prestamos.get(i).getAmortizaciones().get(j);
                                        ContentValues cv_amortiz = new ContentValues();
                                        cv_amortiz.put("fecha", mAmortizacion.getFecha());                                                //FECHA
                                        cv_amortiz.put("fecha_pago", (mAmortizacion.getFechaPago() != null) ? mAmortizacion.getFechaPago() : "");  //FECHA PAGO
                                        cv_amortiz.put("capital", String.valueOf(mAmortizacion.getCapital()));                            //CAPITAL
                                        cv_amortiz.put("interes", String.valueOf(mAmortizacion.getInteres()));                            //INTERES
                                        cv_amortiz.put("iva", String.valueOf(mAmortizacion.getIva()));                                    //IVA
                                        cv_amortiz.put("comision", String.valueOf(mAmortizacion.getComision()));                          //COMISION
                                        cv_amortiz.put("total", String.valueOf(mAmortizacion.getTotal()));                                //TOTAL
                                        cv_amortiz.put("capital_pagado", String.valueOf(mAmortizacion.getCapitalPagado()));               //CAPITAL PAGADO
                                        cv_amortiz.put("interes_pagado", String.valueOf(mAmortizacion.getInteresPagado()));               //INTERES PAGADO
                                        cv_amortiz.put("iva_pagado", String.valueOf(mAmortizacion.getIvaPagado()));                       //IVA PAGADO
                                        cv_amortiz.put("interes_moratorio_pagado", String.valueOf(mAmortizacion.getInteresMoratorioPagado()));//INTERES MORATORIO PAGADO
                                        cv_amortiz.put("iva_moratorio_pagado", String.valueOf(mAmortizacion.getIvaMoratorioPagado()));                  //IVA_MORATORIO PAGADO
                                        cv_amortiz.put("comision_pagada", String.valueOf(mAmortizacion.getComisionPagada()));                      //COMISION PAGADA
                                        String sqlAmortiz = "SELECT total, total_pagado FROM " + TBL_AMORTIZACIONES_T + " WHERE id_amortizacion = ? AND id_prestamo = ?";
                                        Cursor rowAmortiz = db.rawQuery(sqlAmortiz, new String[]{String.valueOf(mAmortizacion.getId()), String.valueOf(prestamos.get(i).getId())});
                                        if (rowAmortiz.getCount() > 0) {
                                            rowAmortiz.moveToFirst();
                                            if (rowAmortiz.getDouble(0) > rowAmortiz.getDouble(1)) {
                                                cv_amortiz.put("total_pagado", String.valueOf(mAmortizacion.getTotalPagado()));                         //TOTAL PAGADO
                                                cv_amortiz.put("pagado", String.valueOf(mAmortizacion.getPagado()));                              //PAGADO
                                                cv_amortiz.put("numero", String.valueOf(mAmortizacion.getNumero()));                              //NUMERO
                                                cv_amortiz.put("dias_atraso", String.valueOf(mAmortizacion.getDiasAtraso()));                          //DIAS ATRASO
                                            }
                                        }
                                        rowAmortiz.close();
                                        cv_amortiz.put("fecha_actualizado", Miscellaneous.ObtenerFecha(TIMESTAMP)); //FECHA ACTUALIZADO

                                        db.update(TBL_AMORTIZACIONES_T, cv_amortiz, "id_amortizacion = ? AND id_prestamo = ?", new String[]{String.valueOf(mAmortizacion.getId()), String.valueOf(prestamos.get(i).getId())});
                                    }
                                } else {
                                    amortizacionDao.deleteByIdPrestamo(String.valueOf(prestamos.get(i).getId()));

                                    for (int j = 0; j < prestamos.get(i).getAmortizaciones().size(); j++) {
                                        MAmortizacion mAmortizacion = prestamos.get(i).getAmortizaciones().get(j);

                                        Amortizacion amortizacion = new Amortizacion();
                                        amortizacion.setIdAmortizacion(String.valueOf(mAmortizacion.getId()));
                                        amortizacion.setIdPrestamo(String.valueOf(mAmortizacion.getPrestamoId()));
                                        amortizacion.setFecha(mAmortizacion.getFecha());
                                        amortizacion.setFechaPago((mAmortizacion.getFechaPago() != null) ? mAmortizacion.getFechaPago() : "");
                                        amortizacion.setCapital(String.valueOf(mAmortizacion.getCapital()));
                                        amortizacion.setInteres(String.valueOf(mAmortizacion.getInteres()));
                                        amortizacion.setIva(String.valueOf(mAmortizacion.getIva()));
                                        amortizacion.setComision(String.valueOf(mAmortizacion.getComision()));
                                        amortizacion.setTotal(String.valueOf(mAmortizacion.getTotal()));
                                        amortizacion.setCapitalPagado(String.valueOf(mAmortizacion.getCapitalPagado()));
                                        amortizacion.setInteresPagado(String.valueOf(mAmortizacion.getInteresPagado()));
                                        amortizacion.setIvaPagado(String.valueOf(mAmortizacion.getIvaPagado()));
                                        amortizacion.setInteresMoratorioPagado(String.valueOf(mAmortizacion.getInteresMoratorioPagado()));
                                        amortizacion.setIvaMoratorioPagado(String.valueOf(mAmortizacion.getIvaMoratorioPagado()));
                                        amortizacion.setComisionPagada(String.valueOf(mAmortizacion.getComisionPagada()));
                                        amortizacion.setTotalPagado(String.valueOf(mAmortizacion.getTotalPagado()));
                                        amortizacion.setPagado(String.valueOf(mAmortizacion.getPagado()));
                                        amortizacion.setNumero(String.valueOf(mAmortizacion.getNumero()));
                                        amortizacion.setDiasAtraso(String.valueOf(mAmortizacion.getDiasAtraso()));
                                        amortizacion.setFechaDispositivo(Miscellaneous.ObtenerFecha(TIMESTAMP));
                                        amortizacion.setFechaActualizado(Miscellaneous.ObtenerFecha(TIMESTAMP));

                                        amortizacionDao.store(amortizacion);
                                    }
                                }
                            }//Termina If de Actualizado de amortizaciones

                            //Registra nuevos pagos si es que no existen en el movil*/
                            if (prestamos.get(i).getPagos() != null && prestamos.get(i).getPagos().size() > 0) {
                                for (int k = 0; k < prestamos.get(i).getPagos().size(); k++) {
                                    MPago mPago = prestamos.get(i).getPagos().get(k);

                                    Cursor row_pago = dBhelper.getRecords(TBL_PAGOS_T, " WHERE id_prestamo = ? AND fecha = ? AND monto = ? AND banco = ?", "",
                                            new String[]{String.valueOf(prestamos.get(i).getId()), mPago.getFecha(), String.valueOf(mPago.getMonto()), mPago.getBanco()});
                                    if (row_pago.getCount() == 0) {
                                        HashMap<Integer, String> cv_pago = new HashMap<>();
                                        cv_pago.put(0, String.valueOf(prestamos.get(i).getId()));            //ID PRESTAMO
                                        cv_pago.put(1, mPago.getFecha());                                    //FECHA
                                        cv_pago.put(2, String.valueOf(mPago.getMonto()));                    //MONTO
                                        cv_pago.put(3, mPago.getBanco());                                     //BANCO
                                        cv_pago.put(4, Miscellaneous.ObtenerFecha(TIMESTAMP));               //FECHA DISPOSITIVO
                                        cv_pago.put(5, Miscellaneous.ObtenerFecha(TIMESTAMP));               //FECHA ACTUALIZADO

                                        dBhelper.savePagos(db, TBL_PAGOS_T, cv_pago);
                                    }
                                    row_pago.close();
                                }
                            }//Termina If de Actualizar pagos

                        }
                        row.close();

                    } //Fin Ciclo For
                }//Fin IF
                else {
                    ContentValues cv = new ContentValues();
                    cv.put("estatus", "0");
                    db.update(TBL_CARTERA_GPO_T, cv, "id_cartera = ?", new String[]{String.valueOf(id)});
                }
            }

        } catch (ExecutionException | InterruptedException | TimeoutException executionException) {
            this.klassTag.e(executionException);
        }

    }

    private void getSolicitudesRechazadasInd() {
        printInfoInUI("Cargando solicitudes rechazadas", -3);

        final DBhelper dBhelper = DBhelper.getInstance(sidertMovilApplication);
        final SQLiteDatabase db = dBhelper.getWritableDatabase();

        Call<List<MSolicitudRechazoInd>> call = this.solicitudesRemoteDatasource.getSolicitudRechazoInd();

        try {
            Response<List<MSolicitudRechazoInd>> response = this.executorUtil.runTaskInThread(call::execute);
            if (response.code() == 200) {
                List<MSolicitudRechazoInd> solicitudes = response.body();
                if (solicitudes == null) {
                    return;
                }
                if (solicitudes.size() > 0) {
                    for (MSolicitudRechazoInd item : solicitudes) {
                        ContentValues cv;
                        String sql = "";
                        Cursor row = null;
                        if (item.getTipoSolicitud() == 1) {

                            Timber.tag(TAG).i("Id de solicitud: %d", item.getSolicitudEstadoId());
                            //                 0                1               2              3                4                 5             6             7               8
                            sql = "SELECT s.id_solicitud, cre.id_credito, cli.id_cliente, con.id_conyuge, eco.id_economico, neg.id_negocio, ava.id_aval, ref.id_referencia, cro.id FROM " + TBL_SOLICITUDES + " AS s " +
                                    "JOIN " + TBL_CREDITO_IND + " AS cre ON s.id_solicitud = cre.id_solicitud " +
                                    "JOIN " + TBL_CLIENTE_IND + " AS cli ON s.id_solicitud = cli.id_solicitud " +
                                    "JOIN " + TBL_CONYUGE_IND + " AS con ON s.id_solicitud = con.id_solicitud " +
                                    "JOIN " + TBL_ECONOMICOS_IND + " AS eco ON s.id_solicitud = eco.id_solicitud " +
                                    "JOIN " + TBL_NEGOCIO_IND + " AS neg ON s.id_solicitud = neg.id_solicitud " +
                                    "JOIN " + TBL_AVAL_IND + " AS ava ON s.id_solicitud = ava.id_solicitud " +
                                    "JOIN " + TBL_REFERENCIA_IND + " AS ref ON s.id_solicitud = ref.id_solicitud " +
                                    "JOIN " + TBL_CROQUIS_IND + " AS cro ON s.id_solicitud = cro.id_solicitud " +
                                    "WHERE s.id_originacion = ? AND s.estatus >= 2";
                            row = db.rawQuery(sql, new String[]{String.valueOf(item.getId())});

                            this.klassTag.i("%d Totales", row.getCount());
                            if (row.getCount() > 0) {
                                row.moveToFirst();
                                if (item.getSolicitudEstadoId() == 4) { //Actualiza solicitudes de originacion que fueron rechazadas por error de datos

                                    cv = new ContentValues();
                                    cv.put("comentario_rechazo", "");
                                    db.update(TBL_CLIENTE_IND, cv, "id_solicitud = ? AND id_cliente = ?", new String[]{row.getString(0), row.getString(2)});

                                    if (item.getEstatusCliente() != null && !(Boolean) item.getEstatusCliente()) {
                                        cv = new ContentValues();
                                        cv.put("estatus", 0);
                                        cv.put("fecha_guardado", "");
                                        cv.put("fecha_termino", "");
                                        db.update(TBL_SOLICITUDES, cv, "id_solicitud = ?", new String[]{row.getString(0)});

                                        cv = new ContentValues();
                                        cv.put("estatus_completado", 0);
                                        cv.put("comentario_rechazo", Miscellaneous.validStr(item.getComentarioAdminCliente()));
                                        int i_update = db.update(TBL_CLIENTE_IND, cv, "id_solicitud = ? AND id_cliente = ?", new String[]{row.getString(0), row.getString(2)});
                                        this.klassTag.i("Update: %d", i_update);

                                        cv = new ContentValues();
                                        cv.put("estatus_completado", 0);
                                        db.update(TBL_CONYUGE_IND, cv, "id_solicitud = ? AND id_conyuge = ?", new String[]{row.getString(0), row.getString(3)});

                                        cv = new ContentValues();
                                        cv.put("estatus_completado", 0);
                                        db.update(TBL_DOCUMENTOS, cv, "id_solicitud = ? ", new String[]{row.getString(0)});
                                    }

                                    if (item.getEstatusNegocio() != null && !(Boolean) item.getEstatusNegocio()) {
                                        cv = new ContentValues();
                                        cv.put("estatus", 0);
                                        cv.put("fecha_guardado", "");
                                        cv.put("fecha_termino", "");
                                        db.update(TBL_SOLICITUDES, cv, "id_solicitud = ?", new String[]{row.getString(0)});

                                        cv = new ContentValues();
                                        cv.put("estatus_completado", 0);
                                        cv.put("comentario_rechazo", Miscellaneous.validStr(item.getComentarioAdminNegocio()));
                                        db.update(TBL_NEGOCIO_IND, cv, "id_solicitud = ? AND id_negocio = ?", new String[]{row.getString(0), row.getString(5)});
                                    }

                                    this.klassTag.i("NEgocioValieCli:" + (item.getEstatusCliente() == null || (item.getEstatusCliente() != null && !(Boolean) item.getEstatusCliente())));
                                    this.klassTag.i("NegocioVal:" + (item.getEstatusAval() != null && !(Boolean) item.getEstatusAval()));
                                    if (item.getEstatusAval() != null && !(Boolean) item.getEstatusAval()) {
                                        cv = new ContentValues();
                                        cv.put("estatus", 0);
                                        cv.put("fecha_guardado", "");
                                        cv.put("fecha_termino", "");
                                        db.update(TBL_SOLICITUDES, cv, "id_solicitud = ?", new String[]{row.getString(0)});

                                        cv = new ContentValues();
                                        cv.put("estatus_completado", 0);
                                        cv.put("comentario_rechazo", Miscellaneous.validStr(item.getComentarioAdminAval()));
                                        db.update(TBL_AVAL_IND, cv, "id_solicitud = ? AND id_aval = ?", new String[]{row.getString(0), row.getString(6)});
                                    }

                                    this.klassTag.i("Referencia: %s", String.valueOf((item.getEstatusCliente() == null || (item.getEstatusCliente() != null && !(Boolean) item.getEstatusCliente()))
                                            && item.getEstatusReferencia() != null && !(Boolean) item.getEstatusReferencia()));

                                    if (item.getEstatusReferencia() != null && !(Boolean) item.getEstatusReferencia()) {
                                        cv = new ContentValues();
                                        cv.put("estatus", 0);
                                        cv.put("fecha_guardado", "");
                                        cv.put("fecha_termino", "");
                                        db.update(TBL_SOLICITUDES, cv, "id_solicitud = ?", new String[]{row.getString(0)});

                                        cv = new ContentValues();
                                        cv.put("estatus_completado", 0);
                                        cv.put("comentario_rechazo", Miscellaneous.validStr(item.getComentarioAdminReferencia()));
                                        db.update(TBL_REFERENCIA_IND, cv, "id_solicitud = ? AND id_referencia = ?", new String[]{row.getString(0), row.getString(7)});
                                    }

                                    if (item.getEstatusCroquis() != null && !(Boolean) item.getEstatusCroquis()) {
                                        cv = new ContentValues();
                                        cv.put("estatus", 0);
                                        cv.put("fecha_guardado", "");
                                        cv.put("fecha_termino", "");
                                        db.update(TBL_SOLICITUDES, cv, "id_solicitud = ?", new String[]{row.getString(0)});

                                        cv = new ContentValues();
                                        cv.put("estatus_completado", 0);
                                        cv.put("comentario_rechazo", Miscellaneous.validStr(item.getComentarioAdminCroquis()));
                                        db.update(TBL_CROQUIS_IND, cv, "id_solicitud = ? AND id = ?", new String[]{row.getString(0), row.getString(8)});
                                    }
                                } else if (item.getSolicitudEstadoId() == 2) { //Actualiza solicitudes de originacion que fueron solicitudes rechazadas
                                    this.klassTag.i("Comentario %s", Miscellaneous.validStr(item.getComentarioAdminCliente()));
                                    cv = new ContentValues();
                                    cv.put("estatus", 5);
                                    db.update(TBL_SOLICITUDES, cv, "id_solicitud = ?", new String[]{row.getString(0)});

                                    cv = new ContentValues();
                                    cv.put("comentario_rechazo", Miscellaneous.validStr("NO AUTORIZADO - " + item.getComentarioAdminCliente()));
                                    db.update(TBL_CLIENTE_IND, cv, "id_solicitud = ? AND id_cliente = ?", new String[]{row.getString(0), row.getString(8)});
                                }
                            }
                            row.close();
                        } else { //Actualiza solicitudes de renovacion que fueron rechazadas por la administradora
                            sql = "SELECT s.id_solicitud, cre.id_credito, cli.id_cliente, con.id_conyuge, eco.id_economico, neg.id_negocio, ava.id_aval, ref.id_referencia, cro.id FROM " + TBL_SOLICITUDES_REN + " AS s " +
                                    "JOIN " + TBL_CREDITO_IND_REN + " AS cre ON s.id_solicitud = cre.id_solicitud " +
                                    "JOIN " + TBL_CLIENTE_IND_REN + " AS cli ON s.id_solicitud = cli.id_solicitud " +
                                    "JOIN " + TBL_CONYUGE_IND_REN + " AS con ON s.id_solicitud = con.id_solicitud " +
                                    "JOIN " + TBL_ECONOMICOS_IND_REN + " AS eco ON s.id_solicitud = eco.id_solicitud " +
                                    "JOIN " + TBL_NEGOCIO_IND_REN + " AS neg ON s.id_solicitud = neg.id_solicitud " +
                                    "JOIN " + TBL_AVAL_IND_REN + " AS ava ON s.id_solicitud = ava.id_solicitud " +
                                    "JOIN " + TBL_REFERENCIA_IND_REN + " AS ref ON s.id_solicitud = ref.id_solicitud " +
                                    "JOIN " + TBL_CROQUIS_IND_REN + " AS cro ON s.id_solicitud = cro.id_solicitud " +
                                    "WHERE s.id_originacion = ? AND s.estatus >= 2";
                            row = db.rawQuery(sql, new String[]{String.valueOf(item.getId())});

                            if (row.getCount() > 0) {
                                row.moveToFirst();
                                if (item.getSolicitudEstadoId() == 4) {//Actualiza solicitud de originacion que rechazo la adminitradora para correccion de datos

                                    cv = new ContentValues();
                                    cv.put("comentario_rechazo", "");
                                    db.update(TBL_CLIENTE_IND_REN, cv, "id_solicitud = ? AND id_cliente = ?", new String[]{row.getString(0), row.getString(2)});

                                    if (item.getEstatusCliente() != null && !(Boolean) item.getEstatusCliente()) {
                                        cv = new ContentValues();
                                        cv.put("estatus", 0);
                                        cv.put("fecha_guardado", "");
                                        cv.put("fecha_termino", "");
                                        db.update(TBL_SOLICITUDES_REN, cv, "id_solicitud = ?", new String[]{row.getString(0)});

                                        cv = new ContentValues();
                                        cv.put("estatus_completado", 0);
                                        cv.put("comentario_rechazo", Miscellaneous.validStr(item.getComentarioAdminCliente()));
                                        int i_update = db.update(TBL_CLIENTE_IND_REN, cv, "id_solicitud = ? AND id_cliente = ?", new String[]{row.getString(0), row.getString(2)});
                                        Log.e("Update", "Update: " + i_update);

                                        cv = new ContentValues();
                                        cv.put("estatus_completado", 0);
                                        db.update(TBL_CONYUGE_IND_REN, cv, "id_solicitud = ? AND id_conyuge = ?", new String[]{row.getString(0), row.getString(3)});

                                        cv = new ContentValues();
                                        cv.put("estatus_completado", 0);
                                        db.update(TBL_DOCUMENTOS_REN, cv, "id_solicitud = ? ", new String[]{row.getString(0)});
                                    }

                                    if (item.getEstatusNegocio() != null && !(Boolean) item.getEstatusNegocio()) {
                                        cv = new ContentValues();
                                        cv.put("estatus", 0);
                                        cv.put("fecha_guardado", "");
                                        cv.put("fecha_termino", "");
                                        db.update(TBL_SOLICITUDES_REN, cv, "id_solicitud = ?", new String[]{row.getString(0)});

                                        cv = new ContentValues();
                                        cv.put("estatus_completado", 0);
                                        cv.put("comentario_rechazo", Miscellaneous.validStr(item.getComentarioAdminNegocio()));
                                        db.update(TBL_NEGOCIO_IND_REN, cv, "id_solicitud = ? AND id_negocio = ?", new String[]{row.getString(0), row.getString(5)});
                                    }

                                    if (item.getEstatusAval() != null && !(Boolean) item.getEstatusAval()) {
                                        cv = new ContentValues();
                                        cv.put("estatus", 0);
                                        cv.put("fecha_guardado", "");
                                        cv.put("fecha_termino", "");
                                        db.update(TBL_SOLICITUDES_REN, cv, "id_solicitud = ?", new String[]{row.getString(0)});

                                        cv = new ContentValues();
                                        cv.put("estatus_completado", 0);
                                        cv.put("comentario_rechazo", Miscellaneous.validStr(item.getComentarioAdminAval()));
                                        db.update(TBL_AVAL_IND_REN, cv, "id_solicitud = ? AND id_aval = ?", new String[]{row.getString(0), row.getString(6)});
                                    }

                                    if (item.getEstatusReferencia() != null && !(Boolean) item.getEstatusReferencia()) {
                                        cv = new ContentValues();
                                        cv.put("estatus", 0);
                                        cv.put("fecha_guardado", "");
                                        cv.put("fecha_termino", "");
                                        db.update(TBL_SOLICITUDES_REN, cv, "id_solicitud = ?", new String[]{row.getString(0)});

                                        cv = new ContentValues();
                                        cv.put("estatus_completado", 0);
                                        cv.put("comentario_rechazo", Miscellaneous.validStr(item.getComentarioAdminReferencia()));
                                        db.update(TBL_REFERENCIA_IND_REN, cv, "id_solicitud = ? AND id_referencia = ?", new String[]{row.getString(0), row.getString(7)});
                                    }

                                    if (item.getEstatusCroquis() != null && !(Boolean) item.getEstatusCroquis()) {
                                        cv = new ContentValues();
                                        cv.put("estatus", 0);
                                        cv.put("fecha_guardado", "");
                                        cv.put("fecha_termino", "");
                                        db.update(TBL_SOLICITUDES_REN, cv, "id_solicitud = ?", new String[]{row.getString(0)});

                                        cv = new ContentValues();
                                        cv.put("estatus_completado", 0);
                                        cv.put("comentario_rechazo", Miscellaneous.validStr(item.getComentarioAdminCroquis()));
                                        db.update(TBL_CROQUIS_IND_REN, cv, "id_solicitud = ? AND id = ?", new String[]{row.getString(0), row.getString(8)});
                                    }
                                } else if (item.getSolicitudEstadoId() == 2) { //Solicitud de renovacion rechazada
                                    this.klassTag.e("AQUI RECHAZI IND idsol: %s", row.getString(0));
                                    this.klassTag.e("AQUI RECHAZI IND idcli: %s", row.getString(8));


                                    cv = new ContentValues();
                                    cv.put("estatus", 5);
                                    db.update(TBL_SOLICITUDES_REN, cv, "id_solicitud = ?", new String[]{row.getString(0)});

                                    cv = new ContentValues();
                                    cv.put("comentario_rechazo", Miscellaneous.validStr("NO AUTORIZADO - " + item.getComentarioAdminCliente()));
                                    db.update(TBL_CLIENTE_IND_REN, cv, "id_solicitud = ? AND id_cliente = ?", new String[]{row.getString(0), row.getString(8)});
                                }
                            }
                        }
                        row.close();

                    }
                }
            }
            printInfoInUI("Cargando solicitudes rechazadas", 3);
        } catch (ExecutionException | InterruptedException | TimeoutException e) {
            this.klassTag.e(e);
        }

    }

    private void getSolicitudesRechazadasGpo() {
        printInfoInUI("Cargando solicitudes rechazadas de grupo", -4);

        final DBhelper dBhelper = DBhelper.getInstance(sidertMovilApplication);
        final SQLiteDatabase db = dBhelper.getWritableDatabase();

        Call<List<MSolicitudRechazoGpo>> call = solicitudesRemoteDatasource.getSolicitudRechazoGpo();
        try {
            Response<List<MSolicitudRechazoGpo>> response = this.executorUtil.runTaskInThread(call::execute);
            if (response.code() == 200) {
                Log.e("AQUI RECHAZADO", response.body().toString());
                List<MSolicitudRechazoGpo> solicitudes = response.body();
                if (solicitudes.size() > 0) {
                    for (MSolicitudRechazoGpo item : solicitudes) {
                        ContentValues cv;
                        String sql = "";
                        Cursor row = null;
                        if (item.getTipoSolicitud() == 1) { //rechazo de solicitud de originacion
                            Log.e("IDSOLCIITUD", "ID: " + item.getIdSolicitudIntegrante());
                            //                 0                        1                   2              3                4              5               6                    7               8                  9
                            sql = "SELECT i.id AS id_integrante, tel.id_telefonico, dom.id_domicilio, neg.id_negocio, con.id_conyuge, otr.id_otro, cro.id AS id_croquis, pol.id_politica, doc.id_documento, sol.id_solicitud FROM " + TBL_INTEGRANTES_GPO + " AS i " +
                                    "JOIN " + TBL_CREDITO_GPO + " AS cre ON i.id_credito = cre.id " +
                                    "JOIN " + TBL_SOLICITUDES + " AS sol ON cre.id_solicitud = sol.id_solicitud " +
                                    "JOIN " + TBL_TELEFONOS_INTEGRANTE + " AS tel ON i.id = tel.id_integrante " +
                                    "JOIN " + TBL_DOMICILIO_INTEGRANTE + " AS dom ON i.id = doc.id_integrante " +
                                    "JOIN " + TBL_NEGOCIO_INTEGRANTE + " AS neg ON i.id = neg.id_integrante " +
                                    "JOIN " + TBL_CONYUGE_INTEGRANTE + " AS con ON i.id = con.id_integrante " +
                                    "JOIN " + TBL_OTROS_DATOS_INTEGRANTE + " AS otr ON i.id = otr.id_integrante " +
                                    "JOIN " + TBL_CROQUIS_GPO + " AS cro ON i.id = cro.id_integrante " +
                                    "JOIN " + TBL_POLITICAS_PLD_INTEGRANTE + " AS pol ON i.id = pol.id_integrante " +
                                    "JOIN " + TBL_DOCUMENTOS_INTEGRANTE + " AS doc ON i.id = doc.id_integrante " +
                                    "WHERE i.id_solicitud_integrante = ? AND i.estatus_completado >= 2";

                            row = db.rawQuery(sql, new String[]{String.valueOf(item.getIdSolicitudIntegrante())});

                            if (row.getCount() > 0) { //Existe algun registro de originacion
                                row.moveToFirst();
                                if (item.getSolicitudEstadoIdIntegrante() == 4) { //Es rechazo parcial
                                    cv = new ContentValues();
                                    cv.put("cargo", item.getCargo());
                                    cv.put("estatus_completado", 0);
                                    cv.put("comentario_rechazo", item.getComentarioAdmin());
                                    db.update(TBL_INTEGRANTES_GPO, cv, "id = ?", new String[]{row.getString(0)});

                                    cv = new ContentValues();
                                    cv.put("estatus_completado", 0);
                                    db.update(TBL_TELEFONOS_INTEGRANTE, cv, "id_telefonico = ?", new String[]{row.getString(1)});

                                    cv = new ContentValues();
                                    cv.put("estatus_completado", 0);
                                    db.update(TBL_DOMICILIO_INTEGRANTE, cv, "id_domicilio = ?", new String[]{row.getString(2)});

                                    cv = new ContentValues();
                                    cv.put("estatus_completado", 0);
                                    db.update(TBL_NEGOCIO_INTEGRANTE, cv, "id_negocio = ?", new String[]{row.getString(3)});

                                    cv = new ContentValues();
                                    cv.put("estatus_completado", 0);
                                    db.update(TBL_CONYUGE_INTEGRANTE, cv, "id_conyuge = ?", new String[]{row.getString(4)});

                                    cv = new ContentValues();
                                    cv.put("estatus_completado", 0);
                                    if (item.getCargo() == 3)
                                        cv.put("casa_reunion", 1);
                                    db.update(TBL_OTROS_DATOS_INTEGRANTE, cv, "id_otro = ?", new String[]{row.getString(5)});

                                    cv = new ContentValues();
                                    cv.put("estatus_completado", 0);
                                    db.update(TBL_CROQUIS_GPO, cv, "id = ?", new String[]{row.getString(6)});

                                    cv = new ContentValues();
                                    cv.put("estatus_completado", 0);
                                    db.update(TBL_POLITICAS_PLD_INTEGRANTE, cv, "id_politica = ?", new String[]{row.getString(7)});

                                    cv = new ContentValues();
                                    cv.put("estatus_completado", 0);
                                    db.update(TBL_DOCUMENTOS_INTEGRANTE, cv, "id_documento = ?", new String[]{row.getString(8)});

                                    cv = new ContentValues();
                                    cv.put("estatus", 0);
                                    cv.put("id_originacion", String.valueOf(item.getId()));
                                    cv.put("fecha_termino", "");
                                    cv.put("fecha_envio", "");
                                    cv.put("fecha_guardado", "");
                                    db.update(TBL_SOLICITUDES, cv, "id_solicitud = ?", new String[]{row.getString(9)});
                                } else { //Es rechazo completo de la solicitud
                                    cv = new ContentValues();
                                    //cv.put("estatus_completado", 0);
                                    cv.put("comentario_rechazo", item.getComentarioAdmin());
                                    db.update(TBL_INTEGRANTES_GPO, cv, "id = ?", new String[]{row.getString(0)});

                                    cv = new ContentValues();

                                    if (item.getSolicitudEstadoIdSolicitud() == 2)
                                        cv.put("estatus", 5);

                                    cv.put("id_originacion", String.valueOf(item.getId()));
                                    //cv.put("fecha_termino", "");
                                    //cv.put("fecha_envio", "");
                                    //cv.put("fecha_guardado", "");
                                    db.update(TBL_SOLICITUDES, cv, "id_solicitud = ?", new String[]{row.getString(9)});
                                }
                            }
                        } else { //rechazo de solicitud grupal de renovacion
                            //                 0                        1                   2              3                4              5               6                    7               8                  9
                            sql = "SELECT " +
                                    "i.id AS id_integrante, " +
                                    "tel.id_telefonico, " +
                                    "dom.id_domicilio, " +
                                    "neg.id_negocio, " +
                                    "con.id_conyuge, " +
                                    "otr.id_otro, " +
                                    "cro.id AS id_croquis, " +
                                    "pol.id_politica, " +
                                    "doc.id_documento, " +
                                    "sol.id_solicitud " +
                                    "FROM " + TBL_INTEGRANTES_GPO_REN + " AS i " +
                                    "JOIN " + TBL_CREDITO_GPO_REN + " AS cre ON i.id_credito = cre.id " +
                                    "JOIN " + TBL_SOLICITUDES_REN + " AS sol ON cre.id_solicitud = sol.id_solicitud " +
                                    "JOIN " + TBL_TELEFONOS_INTEGRANTE_REN + " AS tel ON i.id = tel.id_integrante " +
                                    "JOIN " + TBL_DOMICILIO_INTEGRANTE_REN + " AS dom ON i.id = doc.id_integrante " +
                                    "JOIN " + TBL_NEGOCIO_INTEGRANTE_REN + " AS neg ON i.id = neg.id_integrante " +
                                    "JOIN " + TBL_CONYUGE_INTEGRANTE_REN + " AS con ON i.id = con.id_integrante " +
                                    "JOIN " + TBL_OTROS_DATOS_INTEGRANTE_REN + " AS otr ON i.id = otr.id_integrante " +
                                    "JOIN " + TBL_CROQUIS_GPO_REN + " AS cro ON i.id = cro.id_integrante " +
                                    "JOIN " + TBL_POLITICAS_PLD_INTEGRANTE_REN + " AS pol ON i.id = pol.id_integrante " +
                                    "JOIN " + TBL_DOCUMENTOS_INTEGRANTE_REN + " AS doc ON i.id = doc.id_integrante " +
                                    "WHERE i.id_solicitud_integrante = ? AND i.estatus_completado >= 2";

                            row = db.rawQuery(sql, new String[]{String.valueOf(item.getIdSolicitudIntegrante())});

                            if (row.getCount() > 0) { //Existe algun registro de renovacion
                                row.moveToFirst();
                                if (item.getSolicitudEstadoIdIntegrante() == 4) { //Es rechazo parcial
                                    cv = new ContentValues();
                                    cv.put("estatus_completado", 0);
                                    cv.put("comentario_rechazo", item.getComentarioAdmin());
                                    db.update(TBL_INTEGRANTES_GPO_REN, cv, "id = ?", new String[]{row.getString(0)});

                                    cv = new ContentValues();
                                    cv.put("estatus_completado", 0);
                                    db.update(TBL_TELEFONOS_INTEGRANTE_REN, cv, "id_telefonico = ?", new String[]{row.getString(1)});

                                    cv = new ContentValues();
                                    cv.put("estatus_completado", 0);
                                    db.update(TBL_DOMICILIO_INTEGRANTE_REN, cv, "id_domicilio = ?", new String[]{row.getString(2)});

                                    cv = new ContentValues();
                                    cv.put("estatus_completado", 0);
                                    db.update(TBL_NEGOCIO_INTEGRANTE_REN, cv, "id_negocio = ?", new String[]{row.getString(3)});

                                    cv = new ContentValues();
                                    cv.put("estatus_completado", 0);
                                    db.update(TBL_CONYUGE_INTEGRANTE_REN, cv, "id_conyuge = ?", new String[]{row.getString(4)});

                                    cv = new ContentValues();
                                    cv.put("estatus_completado", 0);
                                    db.update(TBL_OTROS_DATOS_INTEGRANTE_REN, cv, "id_otro = ?", new String[]{row.getString(5)});

                                    cv = new ContentValues();
                                    cv.put("estatus_completado", 0);
                                    db.update(TBL_CROQUIS_GPO_REN, cv, "id = ?", new String[]{row.getString(6)});

                                    cv = new ContentValues();
                                    cv.put("estatus_completado", 0);
                                    db.update(TBL_POLITICAS_PLD_INTEGRANTE_REN, cv, "id_politica = ?", new String[]{row.getString(7)});

                                    cv = new ContentValues();
                                    cv.put("estatus_completado", 0);
                                    db.update(TBL_DOCUMENTOS_INTEGRANTE_REN, cv, "id_documento = ?", new String[]{row.getString(8)});

                                    cv = new ContentValues();
                                    cv.put("estatus", 0);
                                    cv.put("id_originacion", String.valueOf(item.getId()));
                                    cv.put("fecha_termino", "");
                                    cv.put("fecha_envio", "");
                                    cv.put("fecha_guardado", "");
                                    db.update(TBL_SOLICITUDES_REN, cv, "id_solicitud = ?", new String[]{row.getString(9)});
                                } else {//Es rechazo de solcitud completo
                                    cv = new ContentValues();
                                    //cv.put("estatus_completado", 0);
                                    cv.put("comentario_rechazo", item.getComentarioAdmin());
                                    db.update(TBL_INTEGRANTES_GPO_REN, cv, "id = ?", new String[]{row.getString(0)});

                                    cv = new ContentValues();//cv.put("estatus", 0);

                                    if (item.getSolicitudEstadoIdSolicitud() == 2)
                                        cv.put("estatus", 5);

                                    cv.put("id_originacion", String.valueOf(item.getId()));
                                    //cv.put("fecha_termino", "");
                                    //cv.put("fecha_envio", "");
                                    //cv.put("fecha_guardado", "");
                                    db.update(TBL_SOLICITUDES_REN, cv, "id_solicitud = ?", new String[]{row.getString(9)});
                                }

                            }

                        }
                        row.close();

                    }
                    printInfoInUI("Cargando solicitudes rechazadas de grupo", 4);

                }
            } else {
                try (ResponseBody errorResponse = response.errorBody()) {
                    if (errorResponse != null) {
                        klassTag.e("ERROR %d %s", response.code(), errorResponse.string());
                    }
                } catch (IOException ex) {
                    this.klassTag.e(ex);
                }
                klassTag.e("ERROR %d %s", response.code(), response.message());
            }
        } catch (ExecutionException | InterruptedException | TimeoutException ex) {
            this.klassTag.e(ex);
        }

    }

    private void getPrestamosAutorizados() {
        printInfoInUI("Cargando prestamos autorizados", -5);

        final DBhelper dBhelper = DBhelper.getInstance(this.sidertMovilApplication);
        final SQLiteDatabase db = dBhelper.getWritableDatabase();


        Call<MSolicitudAutorizar> call = this.solicitudesRemoteDatasource.getSolicitudesAutorizadas(3L);
        try {
            Response<MSolicitudAutorizar> response = this.executorUtil.runTaskInThread(call::execute);
            Log.e("CodeSolicitude", "Autorizar: " + response.code());
            if (response.code() == 200) {
                MSolicitudAutorizar mSolicitud = response.body();
                List<MSolicitudAutorizar.SolicitudIndividual> solicitudesIndividuales = mSolicitud.getSolicitudesIndividuales();
                solicitudesIndividuales = (solicitudesIndividuales == null) ? Collections.emptyList() : solicitudesIndividuales;

                for (MSolicitudAutorizar.SolicitudIndividual item : solicitudesIndividuales) {
                    String sql = "SELECT * FROM " + TBL_SOLICITUDES_AUTO + " WHERE id_originacion = ?";
                    Cursor row = db.rawQuery(sql, new String[]{String.valueOf(item.getPrestamo().getSolicitudId())});
                    if (row.getCount() == 0) {
                        MSolicitudAutorizar.Prestamo pre = item.getPrestamo();
                        MSolicitudAutorizar.Cliente cli = item.getCliente();
                        MSolicitudAutorizar.Conyuge con = item.getConyuge();
                        MSolicitudAutorizar.Economicos eco = item.getEconomicos();
                        MSolicitudAutorizar.Negocio neg = item.getNegocio();
                        MSolicitudAutorizar.Aval ava = item.getAval();
                        MSolicitudAutorizar.Referencia ref = item.getReferencia();
                        MSolicitudAutorizar.Croquis cro = item.getCroquis();
                        MSolicitudAutorizar.Politicas pol = item.getPoliticas();

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

                        params.put(0, "1");                                      //TIPO SOLICITUD
                        params.put(1, pre.getAsesor());                          //ASESOR
                        params.put(2, ((pre.getTipoSolicitud() == 1) ? "ORIGINACION" : "RENOVACION")); //TIPO SOLICITUD
                        params.put(3, String.valueOf(pre.getSolicitudId()));    //SOLICITUD ID
                        params.put(4, nombre);                                  //NOMBRE CLIENTE
                        params.put(5, "");                                      //FECHA ENVIO
                        params.put(6, "0");                                     //ESTATUS

                        Log.e("PArams", params.toString());
                        id = dBhelper.saveSolicitudesAuto(db, params);

                        //Inserta registro de datos del credito
                        params = new HashMap<>();
                        params.put(0, String.valueOf(id));                                      //ID SOLICITUD
                        params.put(1, Miscellaneous.GetPlazo(pre.getPlazo()));                  //PLAZO
                        params.put(2, Miscellaneous.GetPeriodicidad(pre.getPeriodicida()));     //PERIODICIDAD
                        params.put(3, pre.getFechaDesembolso());                                //FECHA DESEMBOLSO
                        params.put(4, "");                                                      //DIA DESEMBOLSO
                        params.put(5, pre.getHoraVisita());                                     //HORA VISITA
                        params.put(6, String.valueOf(pre.getMonto()));                          //MONTO PRESTAMO
                        params.put(7, "");                                                      //CICLO
                        params.put(8, "");                                                      //CREDITO ANTERIOR
                        params.put(9, "");                                                       //COMPORTAMIENTO PAGO
                        params.put(10, "");                                                     //NUM CLIENTE
                        params.put(11, Miscellaneous.validStr(pre.getObservaciones()));         //OBSERVACIONES
                        params.put(12, pre.getPrestamoDestino());                                                      //DESTINO
                        params.put(13, pre.getClasificacion());//CLASIFICACION RIESGO
                        params.put(14, "0");                                                     //ESTATUS COMPLETO
                        params.put(15, "");                                                     //MONTO AUTORIZADO

                        dBhelper.saveDatosCreditoAuto(db, params);

                        //Inserta registro de direccion del cliente
                        params = new HashMap<>();
                        params.put(0, "CLIENTE");                                                              //TIPO DIRECCION
                        params.put(1, Miscellaneous.validStr(cli.getLatitud()));                 //LATITUD
                        params.put(2, Miscellaneous.validStr(cli.getLongitud()));                //LONGITUD
                        params.put(3, Miscellaneous.validStr(cli.getCalle()));                   //CALLE
                        params.put(4, Miscellaneous.validStr(cli.getNoExterior()));              //NO EXTERIOR
                        params.put(5, Miscellaneous.validStr(cli.getNoInterior()));              //NO INTERIOR
                        params.put(6, Miscellaneous.validStr(cli.getNoManzana()));               //MANZANA
                        params.put(7, Miscellaneous.validStr(cli.getNoLote()));                  //LOTE
                        params.put(8, String.valueOf(Miscellaneous.validInt(cli.getCodigoPostal())));//CP
                        params.put(9, Miscellaneous.GetColonia(this.sidertMovilApplication, Miscellaneous.validInt(cli.getColoniaId())));//COLONIA
                        params.put(10, Miscellaneous.validStr(cli.getCiudad()));                 //CIUDAD
                        params.put(11, Miscellaneous.GetLocalidad(this.sidertMovilApplication, Miscellaneous.validInt(cli.getLocalidadId())));//LOCALIDAD
                        params.put(12, Miscellaneous.GetMunicipio(this.sidertMovilApplication, Miscellaneous.validInt(item.getCliente().getMunicipioId())));//MUNICIPIO
                        params.put(13, Miscellaneous.GetEstado(this.sidertMovilApplication, Miscellaneous.validInt(item.getCliente().getEstadoId())));//ESTADO

                        id_direccion_cli = dBhelper.saveDirecciones(db, params, 3);

                        //Inserta registro de datos del cliente

                        params = new HashMap<>();
                        params.put(0, String.valueOf(id));                                      //ID SOLICITUD
                        params.put(1, Miscellaneous.validStr(cli.getNombre().trim().toUpperCase()));      //NOMBRE
                        params.put(2, Miscellaneous.validStr(cli.getPaterno().trim().toUpperCase()));     //PATERNO
                        params.put(3, Miscellaneous.validStr(cli.getMaterno().trim().toUpperCase()));     //MATERNO
                        params.put(4, Miscellaneous.validStr(cli.getFechaNacimiento()));                  //FECHA NACIMIENTO
                        params.put(5, Miscellaneous.GetEdad(cli.getFechaNacimiento()));//EDAD
                        params.put(6, String.valueOf(cli.getGenero()));           //GENERO
                        params.put(7, Miscellaneous.GetEstado(this.sidertMovilApplication, Miscellaneous.validInt(cli.getEstadoNacimiento())));//ESTADO NACIMIENTO
                        params.put(8, Miscellaneous.validStr(Miscellaneous.validStr(cli.getRfc())));     //RFC
                        params.put(9, Miscellaneous.validStr(Miscellaneous.validStr(cli.getCurp())));     //CURP
                        params.put(10, "");                                                     //CURP DIGITO VERI
                        params.put(11, Miscellaneous.GetOcupacion(this.sidertMovilApplication, Miscellaneous.validInt(cli.getOcupacionId()))); //OCUPACION
                        params.put(12, Miscellaneous.GetSector(this.sidertMovilApplication, Miscellaneous.validInt(cli.getOcupacionId())));   //ACTIVIDAD ECONOMICA
                        params.put(13, Miscellaneous.GetTipoIdentificacion(this.sidertMovilApplication, cli.getIdentificacionTipoId()));                                                     //TIPO IDENTIFICACION
                        params.put(14, Miscellaneous.validStr(cli.getNoIdentificacion()));//NUM IDENTIFICACION
                        params.put(15, Miscellaneous.GetEstudio(this.sidertMovilApplication, cli.getEstudioNivelId()));   //NIVEL ESTUDIO
                        params.put(16, Miscellaneous.GetEstadoCivil(this.sidertMovilApplication, cli.getEstadoCivilId()));//ESTATUS CIVIL
                        params.put(17, String.valueOf(cli.getRegimenBienId()));                   //BIENES
                        params.put(18, Miscellaneous.GetViviendaTipo(this.sidertMovilApplication, cli.getViviendaTipoId()));  //TIPO VIVIENDA
                        params.put(19, "");                                                     //PARENTESCO
                        params.put(20, "");                                                     //OTRO TIPO VIVIENDA
                        params.put(21, String.valueOf(id_direccion_cli));                           //DIRECCION ID
                        params.put(22, Miscellaneous.validStr(cli.getTelCasa())); //TEL CASA
                        params.put(23, Miscellaneous.validStr(cli.getTelCelular())); //TEL CELULAR
                        params.put(24, Miscellaneous.validStr(cli.getTelMensaje())); //TEL MENSAJES
                        params.put(25, Miscellaneous.validStr(cli.getTelTrabajo())); //TEL TRABAJO
                        if (!Miscellaneous.validStr(cli.getTiempoVivirSitio()).isEmpty())
                            params.put(26, cli.getTiempoVivirSitio());
                        else
                            params.put(26, "0");                                                //TIEMPO VIVIR SITIO
                        params.put(27, Miscellaneous.validStr(cli.getDependientesEconomico()));  //DEPENDIENTES
                        params.put(28, Miscellaneous.GetMedioContacto(this.sidertMovilApplication, cli.getMedioContactoId()));                                                     //MEDIO CONTACTO
                        params.put(29, Miscellaneous.validStr(cli.getEstadoCuenta()));//ESTADO CUENTA
                        params.put(30, Miscellaneous.validStr(cli.getEmail()));   //EMAIL
                        params.put(31, "");                                                     //FOTO FACHADA
                        params.put(32, Miscellaneous.validStr(cli.getReferencia()));//REF DOMICILIARIA
                        params.put(33, "");                                                     //FIRMA
                        params.put(34, "0");                                                    //ESTATUS RECHAZO
                        params.put(35, "");                                                     //COMENTARIO RECHAZO
                        params.put(36, "1");                                                    //ESTATUS COMPLETO

                        id_cliente = dBhelper.saveDatosPersonales(db, params, 3);

                        if (cli.getEstadoCivilId() == 2 || cli.getEstadoCivilId() == 5) {
                            Log.e("Conyuge", "Registra conyuge");
                            //Inserta registro de direccion del cliente
                            params = new HashMap<>();
                            params.put(0, "CONYUGE");                                               //TIPO DIRECCION
                            params.put(1, Miscellaneous.validStr(con.getLatitud()));  //LATITUD
                            params.put(2, Miscellaneous.validStr(con.getLongitud())); //LONGITUD
                            params.put(3, Miscellaneous.validStr(con.getCalle()));    //CALLE
                            params.put(4, Miscellaneous.validStr(con.getNoExterior()));//NO EXTERIOR
                            params.put(5, Miscellaneous.validStr(con.getNoInterior()));//NO INTERIOR
                            params.put(6, Miscellaneous.validStr(con.getNoManzana()));//MANZANA
                            params.put(7, Miscellaneous.validStr(con.getNoLote()));   //LOTE
                            params.put(8, (con.getCodigoPostal() == 0) ? "" : String.valueOf(con.getCodigoPostal())); //CP
                            params.put(9, Miscellaneous.GetColonia(this.sidertMovilApplication, Miscellaneous.validInt(con.getColoniaId()))); //COLONIA
                            params.put(10, Miscellaneous.validStr(con.getCiudad()));  //CIUDAD
                            params.put(11, Miscellaneous.GetLocalidad(this.sidertMovilApplication, Miscellaneous.validInt(con.getLocalidadId()))); //LOCALIDAD
                            params.put(12, Miscellaneous.GetMunicipio(this.sidertMovilApplication, Miscellaneous.validInt(con.getMunicipioId()))); //MUNICIPIO
                            params.put(13, Miscellaneous.GetEstado(this.sidertMovilApplication, Miscellaneous.validInt(con.getEstadoId()))); //ESTADO

                            id_direccion_cony = dBhelper.saveDirecciones(db, params, 3);

                            //Inserta registro de datos conyuge
                            params = new HashMap<>();
                            params.put(0, String.valueOf(id));                      //ID SOLICITUD
                            params.put(1, Miscellaneous.validStr(con.getNombre()).toUpperCase()); //NOMBRE
                            params.put(2, Miscellaneous.validStr(con.getPaterno()).toUpperCase());//PATERNO
                            params.put(3, Miscellaneous.validStr(con.getMaterno()).toUpperCase());//MATERNO
                            params.put(4, Miscellaneous.validStr(con.getNacionalidad()));         //NACIONALIDAD
                            params.put(5, Miscellaneous.GetOcupacion(this.sidertMovilApplication, Miscellaneous.validInt(con.getOcupacionId()))); //OCUPACION
                            params.put(6, String.valueOf(id_direccion_cony));                                   //DIRECCION ID
                            params.put(7, String.valueOf(con.getIngresoMensual()));               //ING MENSUAL
                            params.put(8, String.valueOf(con.getGastoMensual()));                 //GASTO MENSUAL
                            params.put(9, Miscellaneous.validStr(con.getTelCasa()));              //TEL CASA
                            params.put(10, Miscellaneous.validStr(con.getTelCelular()));          //TEL CELULAR
                            params.put(11, "0");                                                                //ESTATUS COMPLETADO

                            dBhelper.saveDatosConyuge(db, params, 3);
                        }

                        if (pre.getMonto() > 29000) {
                            //Inserta registro de datos economicos
                            params = new HashMap<>();
                            params.put(0, String.valueOf(id));                         //ID SOLICITUD
                            params.put(1, eco.getPropiedades());                       //PROPIEDADES
                            params.put(2, String.valueOf(eco.getValorAproximado()));   //VALOR APROXIMADO
                            params.put(3, eco.getUbicacion());                         //UBICACION
                            params.put(4, String.valueOf(eco.getIngreso()));           //INGRESO
                            params.put(5, "0");                                        //ESTATUS COMPLETADO

                            dBhelper.saveDatosEconomicos(db, params, 3);
                        }

                        //Inserta registro de direccion del negocio
                        params = new HashMap<>();
                        params.put(0, "NEGOCIO");                                               //TIPO DIRECCION
                        params.put(1, Miscellaneous.validStr(neg.getLatitud()));  //LATITUD
                        params.put(2, Miscellaneous.validStr(neg.getLongitud())); //LONGITUD
                        params.put(3, Miscellaneous.validStr(neg.getCalle()).toUpperCase());    //CALLE
                        params.put(4, Miscellaneous.validStr(neg.getNoExterior())); //NO EXTERIOR
                        params.put(5, Miscellaneous.validStr(neg.getNoInterior())); //NO INTERIOR
                        params.put(6, Miscellaneous.validStr(neg.getNoManzana()));  //MANZANA
                        params.put(7, Miscellaneous.validStr(neg.getNoLote()));     //LOTE
                        params.put(8, (neg.getCodigoPostal() == 0) ? "" : String.valueOf(neg.getCodigoPostal()));                                                     //CP
                        params.put(9, Miscellaneous.GetColonia(this.sidertMovilApplication, neg.getColoniaId())); //COLONIA
                        params.put(10, item.getNegocio().getCiudad());                           //CIUDAD
                        params.put(11, Miscellaneous.GetLocalidad(this.sidertMovilApplication, neg.getLocalidadId()));  //LOCALIDAD
                        params.put(12, Miscellaneous.GetMunicipio(this.sidertMovilApplication, neg.getMunicipioId()));  //MUNICIPIO
                        params.put(13, Miscellaneous.GetEstado(this.sidertMovilApplication, neg.getEstadoId())); //ESTADO

                        id_direccion_neg = dBhelper.saveDirecciones(db, params, 3);

                        //Inserta registro de negocio
                        String diasVentaNegocio = neg.getDiasVenta();
                        diasVentaNegocio = diasVentaNegocio == null ? "" : diasVentaNegocio;

                        params = new HashMap<>();
                        params.put(0, String.valueOf(id));                  //ID SOLICITUD
                        params.put(1, Miscellaneous.validStr(neg.getNombre()));   //NOMBRE
                        params.put(2, String.valueOf(id_direccion_neg));        //DIRECCION ID
                        params.put(3, Miscellaneous.GetOcupacion(this.sidertMovilApplication, neg.getOcupacionId())); //OCUPACION
                        params.put(4, Miscellaneous.GetSector(this.sidertMovilApplication, neg.getOcupacionId()));    //ACTIVIDAD ECONOMICA
                        params.put(5, Miscellaneous.GetDestinoCredito(this.sidertMovilApplication, neg.getDestinoCreditoId())); //DESTINO CREDITO
                        params.put(6, Miscellaneous.validStr(neg.getOtroDestinoCredito())); //OTRO DESTINO
                        params.put(7, String.valueOf(neg.getAntiguedad()));       //ANTIGUEDAD
                        params.put(8, String.valueOf(neg.getIngresoMensual()));   //ING MENSUAL
                        params.put(9, String.valueOf(neg.getIngresosOtros()));    //ING OTROS
                        params.put(10, String.valueOf(neg.getGastoMensual()));    //GASTO SEMANAL
                        params.put(11, String.valueOf(neg.getGastoAgua()));       //GASTO AGUA
                        params.put(12, String.valueOf(neg.getGastoLuz()));        //GASTO LUZ
                        params.put(13, String.valueOf(neg.getGastoTelefono()));   //GASTO TELEFONO
                        params.put(14, String.valueOf(neg.getGastoRenta()));      //GASTO RENTA
                        params.put(15, String.valueOf(neg.getGastoOtros()));      //GASTO OTROS
                        params.put(16, String.valueOf(neg.getCapacidadPago()));   //CAPACIDAD PAGO
                        params.put(17, Miscellaneous.GetMediosPagoSoli(this.sidertMovilApplication, neg.getMediosPagosIds())); //MEDIO PAGO
                        params.put(18, neg.getOtroMedioPago());                   //OTRO MEDIO PAGO
                        params.put(19, String.valueOf(neg.getMontoMaximo())); //MONTO MAXIMO
                        int numOper = 30 / pre.getPeriodicida();
                        params.put(20, String.valueOf(numOper));  //NUM OPERACION MENSUALES
                        params.put(21, String.valueOf(neg.getNumOperacionesMensualesEfectivo())); //NUM OPERACION EFECTIVO
                        params.put(22, diasVentaNegocio);                 //DIAS VENTA
                        params.put(23, "");                                  //FOTO FACHADA
                        params.put(24, neg.getReferencia());                                  //REF DOMICILIARIA
                        params.put(25, "1");                                 //ESTATUS COMPLETADO
                        params.put(26, "");                                  //COMENTARIO RECHAZO

                        dBhelper.saveDatosNegocio(db, params, 3);

                        //Inserta registro de direccion del aval
                        params = new HashMap<>();
                        params.put(0, "AVAL");                                                 //TIPO DIRECCION
                        params.put(1, Miscellaneous.validStr(ava.getLatitud()));    //LATITUD
                        params.put(2, Miscellaneous.validStr(ava.getLongitud()));   //LONGITUD
                        params.put(3, Miscellaneous.validStr(ava.getCalle()));      //CALLE
                        params.put(4, Miscellaneous.validStr(ava.getNoExterior())); //NO EXTERIOR
                        params.put(5, Miscellaneous.validStr(ava.getNoInterior())); //NO INTERIOR
                        params.put(6, Miscellaneous.validStr(ava.getNoManzana()));  //MANZANA
                        params.put(7, Miscellaneous.validStr(ava.getNoLote()));     //LOTE
                        params.put(8, (ava.getCodigoPostal() == 0) ? "" : String.valueOf(ava.getCodigoPostal())); //CP
                        params.put(9, Miscellaneous.GetColonia(this.sidertMovilApplication, ava.getColoniaId())); //COLONIA
                        params.put(10, Miscellaneous.validStr(item.getAval().getCiudad()));     //CIUDAD
                        params.put(11, Miscellaneous.GetLocalidad(this.sidertMovilApplication, ava.getLocalidadId())); //LOCALIDAD
                        params.put(12, Miscellaneous.GetMunicipio(this.sidertMovilApplication, ava.getMunicipioId())); //MUNICIPIO
                        params.put(13, Miscellaneous.GetEstado(this.sidertMovilApplication, ava.getEstadoId())); //ESTADO

                        id_direccion_aval = dBhelper.saveDirecciones(db, params, 3);

                        //Inserta registro del aval
                        params = new HashMap<>();
                        params.put(0, String.valueOf(id));                  //ID SOLICITUD
                        params.put(1, Miscellaneous.validStr(ava.getNombre()).toUpperCase());          //NOMBRE
                        params.put(2, Miscellaneous.validStr(ava.getPaterno()).toUpperCase());         //PATERNO
                        params.put(3, Miscellaneous.validStr(ava.getMaterno()).toUpperCase());         //MATERNO
                        params.put(4, Miscellaneous.validStr(ava.getFechaNacimiento()));               //FECHA NACIMIENTO
                        params.put(5, String.valueOf(ava.getEdad()));                                  //EDAD
                        params.put(6, String.valueOf(Miscellaneous.validInt(ava.getGenero())));        //GENERO
                        params.put(7, Miscellaneous.GetEstado(this.sidertMovilApplication, Miscellaneous.validInt(ava.getEstadoNacimientoId())));//ESTADO NACIMIENTO
                        params.put(8, Miscellaneous.validStr(ava.getRfc()));                           //RFC
                        params.put(9, Miscellaneous.validStr(ava.getCurp()));                          //CURP
                        params.put(10, "");                                                                       //CURP DIGITO
                        params.put(11, Miscellaneous.GetParentesco(this.sidertMovilApplication, Miscellaneous.validInt(ava.getParentescoSolicitanteId()))); //PARENTESCO CLIENTE
                        params.put(12, Miscellaneous.GetTipoIdentificacion(this.sidertMovilApplication, Miscellaneous.validInt(ava.getIdentificacionTipoId()))); //TIPO IDENTIFICACION
                        params.put(13, Miscellaneous.validStr(ava.getNoIdentificacion()));             //NUM IDENTIFICACION
                        params.put(14, Miscellaneous.GetOcupacion(this.sidertMovilApplication, Miscellaneous.validInt(ava.getOcupacionId())));         //OCUPACION
                        params.put(15, Miscellaneous.GetSector(this.sidertMovilApplication, Miscellaneous.validInt(ava.getOcupacionId())));            //ACTIVIDAD ECONOMICA
                        params.put(16, "");                                                                       //DESTINO CREDITO
                        params.put(17, "");                                                                       //OTRO DESTINO
                        params.put(18, String.valueOf(id_direccion_aval));                                        //DIRECCION ID
                        params.put(19, Miscellaneous.GetViviendaTipo(this.sidertMovilApplication, Miscellaneous.validInt(ava.getViviendaTipoId())));   //TIPO VIVIENDA
                        params.put(20, Miscellaneous.validStr(ava.getNombreTitular()));                //NOMBRE TITULAR
                        params.put(21, Miscellaneous.GetParentesco(this.sidertMovilApplication, Miscellaneous.validInt(ava.getParentescoTitularId()))); //PARENTESCO
                        params.put(22, Miscellaneous.validStr(ava.getCaracteristicasDomicilio()));     //CARACTERISTICAS DOMICILIO
                        params.put(23, String.valueOf(Miscellaneous.validInt(ava.getAntiguedad())));                           //ANTIGUEDAD
                        params.put(24, (ava.getTieneNegocio() != null && ava.getTieneNegocio()) ? "1" : "2");                               //TIENE NEGOCIO
                        params.put(25, Miscellaneous.validStr(ava.getNombreNegocio()));                //NOMBRE NEGOCIO
                        params.put(26, String.valueOf(ava.getIngresoMensual()));   //ING MENSUAL
                        params.put(27, String.valueOf(ava.getIngresosOtros()));    //ING OTROS
                        params.put(28, String.valueOf(ava.getGastoMensual()));     //GASTO SEMANAL
                        params.put(29, String.valueOf(ava.getGastoAgua()));        //GASTO AGUA
                        params.put(30, String.valueOf(ava.getGastoLuz()));         //GASTO LUZ
                        params.put(31, String.valueOf(ava.getGastoTelefono()));    //GASTO TELEFONO
                        params.put(32, String.valueOf(ava.getGastoRenta()));       //GASTO RENTA
                        params.put(33, String.valueOf(ava.getGastoOtros()));       //GASTO OTROS
                        params.put(34, String.valueOf(ava.getCapacidadPago()));    //CAPACIDAD PAGOS
                        params.put(35, Miscellaneous.GetMediosPagoSoli(this.sidertMovilApplication, ava.getMediosPago()));                                 //MEDIO PAGO
                        params.put(36, ava.getOtroMedioPago() == null ? "" : ava.getOtroMedioPago());                   //OTRO MEDIO PAGO
                        params.put(37, String.valueOf(ava.getMontoMaximo() == null ? 0 : ava.getMontoMaximo()));      //MONTO MAXIMO
                        params.put(38, Miscellaneous.validStr(ava.getHoraLocalizacion()));            //HORARIO LOCALIZACION
                        params.put(39, ava.getActivosObservables());                                 //ACTIVOS OBSERVABLES
                        params.put(40, Miscellaneous.validStr(ava.getTelCasa()));                     //TEL CASA
                        params.put(41, Miscellaneous.validStr(ava.getTelCelular()));                  //TEL CELULAR
                        params.put(42, Miscellaneous.validStr(ava.getTelMensaje()));                  //TEL MENSAJES
                        params.put(43, Miscellaneous.validStr(ava.getTelTrabajo()));                  //TEL TRABAJO
                        params.put(44, Miscellaneous.validStr(ava.getEmail()));                       //EMAIL
                        params.put(45, "");                                 //FOTO FACHADA
                        params.put(46, Miscellaneous.validStr(ava.getReferencia()));                 //REF DOMICILIARIA
                        params.put(47, "");                                 //FIRMA
                        params.put(48, "0");                                //ESTATUS RECHAZO
                        params.put(49, "");                                 //COMENTARIO RECHAZO
                        params.put(50, "0");                                //ESTATUS RECHAZO

                        dBhelper.saveDatosAval(db, params, 3);

                        //Inserta registro de direccion del referencia
                        params = new HashMap<>();
                        params.put(0, "REFERENCIA");                                                 //TIPO DIRECCION
                        params.put(1, "");                                                           //LATITUD
                        params.put(2, "");                                                           //LONGITUD
                        params.put(3, Miscellaneous.validStr(ref.getCalle()));      //CALLE
                        params.put(4, Miscellaneous.validStr(ref.getNoExterior())); //NO EXTERIOR
                        params.put(5, Miscellaneous.validStr(ref.getNoInterior())); //NO INTERIOR
                        params.put(6, Miscellaneous.validStr(ref.getNoManzana()));  //MANZANA
                        params.put(7, Miscellaneous.validStr(ref.getNoLote()));     //LOTE
                        params.put(8, (ref.getCodigoPostal() == 0) ? "" : String.valueOf(ref.getCodigoPostal()));                                                     //CP
                        params.put(9, Miscellaneous.GetColonia(this.sidertMovilApplication, Miscellaneous.validInt(ref.getColoniaId()))); //COLONIA
                        params.put(10, Miscellaneous.validStr(ref.getCiudad()));    //CIUDAD
                        params.put(11, Miscellaneous.GetLocalidad(this.sidertMovilApplication, Miscellaneous.validInt(ref.getLocalidadId()))); //LOCALIDAD
                        params.put(12, Miscellaneous.GetMunicipio(this.sidertMovilApplication, Miscellaneous.validInt(ref.getMunicipioId())));                                                    //MUNICIPIO
                        params.put(13, Miscellaneous.GetEstado(this.sidertMovilApplication, Miscellaneous.validInt(ref.getEstadoId())));                                                    //ESTADO

                        id_direccion_ref = dBhelper.saveDirecciones(db, params, 3);

                        //Inserta registro de referencia
                        params = new HashMap<>();
                        params.put(0, String.valueOf(id));                                                     //ID SOLICITUD
                        params.put(1, Miscellaneous.validStr(ref.getNombre().toUpperCase())); //NOMBRE
                        params.put(2, Miscellaneous.validStr(ref.getPaterno().toUpperCase()));//PATERNO
                        params.put(3, Miscellaneous.validStr(ref.getMaterno().toUpperCase()));//MATERNO
                        params.put(4, Miscellaneous.validStr(ref.getFechaNacimiento()));      //FECHA NACIMIENTO
                        params.put(5, String.valueOf(id_direccion_ref));                      //DIRECCION ID
                        params.put(6, Miscellaneous.validStr(ref.getTelCelular()));           //TEL_CELULAR
                        params.put(7, "0");                                                   //ESTATUS COMPLETADO
                        params.put(8, "");                                                    //COMENTARIO RECHAZO

                        dBhelper.saveReferencia(db, params, 3);

                        //Inserta registro de croquis
                        params = new HashMap<>();
                        params.put(0, String.valueOf(id));                  //ID SOLICITUD
                        params.put(1, cro.getCalleEnfrente());              //CALLE PRINCIPAL
                        params.put(2, cro.getLateralDerecha());             //LATERAL UNO
                        params.put(3, cro.getLateralIzquierda());           //LATERAL DOS
                        params.put(4, cro.getCalleAtras());                 //CALLE TRASERA
                        params.put(5, cro.getReferencias());                //REFERENCIAS
                        params.put(6, "0");                                 //ESTATUS COMPLETADO
                        params.put(7, "");                                  //COMENTARIO RECHAZO

                        dBhelper.saveCroquisInd(db, params, 3);

                        //Inserta registro de politicas
                        params = new HashMap<>();
                        params.put(0, String.valueOf(id));                       //ID SOLICITUD
                        params.put(1, (pol.getPropietario()) ? "1" : "2");           //PROPIERATIO REAL
                        params.put(2, (pol.getProveedorRecursos()) ? "1" : "2");     //PROVEEDOR RECURSOS
                        params.put(3, (pol.getPoliticamenteExpuesto()) ? "1" : "2"); //PERSONA POLITICA
                        params.put(4, "0");                                      //ESTATUS COMPLETADO

                        dBhelper.savePoliticasInd(db, params, 3);

                    }

                }

                List<MSolicitudAutorizar.SolicitudGrupal> solicitudGrupals = mSolicitud.getSolicitudesGrupales();
                solicitudGrupals = (solicitudGrupals == null) ? Collections.emptyList() : solicitudGrupals;

                for (MSolicitudAutorizar.SolicitudGrupal item : solicitudGrupals) {
                    String sql = "SELECT * FROM " + TBL_SOLICITUDES_AUTO + " WHERE id_originacion = ?";
                    Cursor row = db.rawQuery(sql, new String[]{String.valueOf(item.getPrestamoGpo().getSolicitudId())});
                    if (row.getCount() == 0) {

                        MSolicitudAutorizar.PrestamoGpo prestamoGrupo = item.getPrestamoGpo();
                        Integer solicitudId = prestamoGrupo.getSolicitudId();
                        Integer prestamoGruapalTipoSolicitud = prestamoGrupo.getTipoSolicitud();

                        if (prestamoGruapalTipoSolicitud == null) {
                            Timber.tag("DescargaDatosBussinesModel").e("Tipo de solicitud en nulo");
                            Timber.tag("DescargaDatosBussinesModel").e("Numero de solicitud " + solicitudId);
                        }


                        prestamoGruapalTipoSolicitud = (prestamoGruapalTipoSolicitud == null) ? 1 : prestamoGruapalTipoSolicitud;
                        String tipoPrestamoGrupal = prestamoGruapalTipoSolicitud == 1 ? "ORIGINACION" : "RENOVACION";

                        HashMap<Integer, String> params = new HashMap<>();
                        params.put(0, "2");                                     //TIPO SOLICITUD
                        params.put(1, item.getPrestamoGpo().getAsesor());       //ASESOR
                        params.put(2, tipoPrestamoGrupal);                      //TIPO SOLICITUD
                        params.put(3, String.valueOf(item.getPrestamoGpo().getSolicitudId()));    //SOLICITUD ID
                        params.put(4, item.getPrestamoGpo().getNombreGrupo());  //NOMBRE grupo
                        params.put(5, "");                                      //FECHA ENVIO
                        params.put(6, "0");                                     //ESTATUS

                        Log.e("Params", params.toString());
                        Long idSoliGpo = dBhelper.saveSolicitudesAuto(db, params);

                        params = new HashMap<>();
                        params.put(0, String.valueOf(idSoliGpo));
                        params.put(1, item.getPrestamoGpo().getNombreGrupo());
                        params.put(2, Miscellaneous.GetPlazo(item.getPrestamoGpo().getPlazo()));
                        params.put(3, Miscellaneous.GetPeriodicidad(item.getPrestamoGpo().getPeriodicida()));
                        params.put(4, item.getPrestamoGpo().getFechaDesembolso());
                        params.put(5, Miscellaneous.DiaSemana(item.getPrestamoGpo().getFechaDesembolso()));
                        params.put(6, item.getPrestamoGpo().getHoraVisita());
                        params.put(7, "0");
                        params.put(8, "");
                        params.put(9, "0");
                        params.put(10, "");

                        Long id_credito = dBhelper.saveDatosCreditoGpoRen(db, params, 2);

                        List<MSolicitudAutorizar.Integrantes> integrantesEnSolicitud = item.getIntegrantes();
                        integrantesEnSolicitud = (integrantesEnSolicitud == null) ? Collections.emptyList() : integrantesEnSolicitud;

                        for (MSolicitudAutorizar.Integrantes inte : integrantesEnSolicitud) {

                            int tipo = 3;
                            long id = 0;
                            //Inserta registro de integrante
                            params = new HashMap<>();
                            params.put(0, String.valueOf(id_credito));                              //ID CREDITO
                            params.put(1, String.valueOf(inte.getTipoIntegrante()));                                //CARGO
                            params.put(2, inte.getCliente().getNombre().trim().toUpperCase());      //NOMBRE(S)
                            params.put(3, inte.getCliente().getPaterno().trim().toUpperCase());     //PATERNO
                            params.put(4, inte.getCliente().getMaterno().trim().toUpperCase());     //MATERNO
                            params.put(5, inte.getCliente().getFechaNacimiento());                  //FECHA NACIMIENTO
                            params.put(6, String.valueOf(inte.getCliente().getEdad()));             //EDAD
                            params.put(7, String.valueOf(inte.getCliente().getGenero()));           //GENERO
                            params.put(8, Miscellaneous.GetEstado(this.sidertMovilApplication, inte.getCliente().getEstadoNacimiento())); //ESTADO NACIMIENTO
                            params.put(9, inte.getCliente().getRfc());                              //RFC
                            params.put(10, inte.getCliente().getCurp());                            //CURP
                            params.put(11, "");                                                     //CURP DIGITO VERI
                            params.put(12, Miscellaneous.GetTipoIdentificacion(this.sidertMovilApplication, inte.getCliente().getIdentificacionTipoId()));//TIPO IDENTIFICACION
                            params.put(13, inte.getCliente().getNoIdentificacion());                //NO IDENTIFICACION
                            params.put(14, Miscellaneous.GetEstudio(this.sidertMovilApplication, inte.getCliente().getEstudioNivelId()));//NIVEL ESTUDIO
                            params.put(15, Miscellaneous.GetOcupacion(this.sidertMovilApplication, inte.getCliente().getOcupacionId()));//OCUPACION
                            params.put(16, Miscellaneous.GetEstadoCivil(this.sidertMovilApplication, inte.getCliente().getEstadoCivilId()));//ESTADO CIVIL
                            params.put(17, String.valueOf(inte.getCliente().getRegimenBienId()));   //BIENES
                            params.put(18, "0");                                                    //ESTATUS RECHAZO
                            params.put(19, "");                                                     //COMENTARIO RECHAZO
                            params.put(20, "1");                                                    //ESTATUS COMPLETO
                            params.put(21, String.valueOf(inte.getIdSolicitudIntegrante()));        //ID SOLICITUD INTEGRANTE

                            id = dBhelper.saveIntegrantesGpo(db, params, tipo);

                            //Inserta registro de datos telefonicos
                            params = new HashMap<>();
                            params.put(0, String.valueOf(id));              //ID INTEGRANTE
                            params.put(1, Miscellaneous.validStr(inte.getCliente().getTelCasa()));      //TEL CASA
                            params.put(2, Miscellaneous.validStr(inte.getCliente().getTelCelular()));   //TEL CELULAR
                            params.put(3, Miscellaneous.validStr(inte.getCliente().getTelMensaje()));   //TEL MENSAJES
                            params.put(4, Miscellaneous.validStr(inte.getCliente().getTelTrabajo()));   //TEL TRABAJO
                            params.put(5, "1");                                                         //ESTATUS COMPLETADO

                            dBhelper.saveDatosTelefonicos(db, params, tipo);

                            //Inserta registro de datos domicilio
                            params = new HashMap<>();
                            params.put(0, String.valueOf(id));          //ID INTEGRANTE
                            params.put(1, Miscellaneous.validStr(inte.getCliente().getLatitud()));      //LATITUD
                            params.put(2, Miscellaneous.validStr(inte.getCliente().getLongitud()));     //LONGITUD
                            params.put(3, Miscellaneous.validStr(inte.getCliente().getCalle()));        //CALLE
                            params.put(4, Miscellaneous.validStr(inte.getCliente().getNoExterior()));   //NO_EXTERIOR
                            params.put(5, Miscellaneous.validStr(inte.getCliente().getNoInterior()));   //NO INTERIOR
                            params.put(6, Miscellaneous.validStr(inte.getCliente().getNoManzana()));    //MANZANA
                            params.put(7, Miscellaneous.validStr(inte.getCliente().getNoLote()));       //LOTE
                            params.put(8, String.valueOf(inte.getCliente().getCodigoPostal()));         //CP
                            params.put(9, Miscellaneous.GetColonia(this.sidertMovilApplication, inte.getCliente().getColoniaId()));//COLONIA
                            params.put(10, Miscellaneous.validStr(inte.getCliente().getCiudad()));      //CIUDAD
                            params.put(11, Miscellaneous.GetLocalidad(this.sidertMovilApplication, inte.getCliente().getLocalidadId()));//LOCALIDAD
                            params.put(12, Miscellaneous.GetMunicipio(this.sidertMovilApplication, inte.getCliente().getMunicipioId()));//MUNICIPIO
                            params.put(13, Miscellaneous.GetEstado(this.sidertMovilApplication, inte.getCliente().getEstadoId()));//ESTADO
                            params.put(14, Miscellaneous.GetViviendaTipo(this.sidertMovilApplication, inte.getCliente().getViviendaTipoId()));//TIPO VIVIENDA
                            params.put(15, Miscellaneous.GetParentesco(this.sidertMovilApplication, inte.getCliente().getParentescoId()));//PARENTESCO
                            params.put(16, Miscellaneous.validStr(inte.getCliente().getOtroTipoVivienda()));//OTRO TIPO VIVIENDA
                            params.put(17, String.valueOf(Miscellaneous.validInt(inte.getCliente().getTiempoVivirSitio())));//TIEMPO VIVIR SITIO
                            params.put(18, "");                         //FOTO FACHADA
                            params.put(19, Miscellaneous.validStr(inte.getCliente().getReferencia()));   //REF DOMICILIARIA
                            params.put(20, "1");                        //ESTATUS COMPLETO
                            Log.e("DependientesEco", String.valueOf(Miscellaneous.validInt(inte.getCliente().getDependientesEconomico())));
                            params.put(21, String.valueOf(Miscellaneous.validInt(inte.getCliente().getDependientesEconomico())));//DEPENDIENTES ECONOMICOS

                            dBhelper.saveDatosDomicilio(db, params, tipo);

                            //Inserta registro de negocio
                            MSolicitudAutorizar.Negocio integranteNegocio = inte.getNegocio();
                            if (integranteNegocio != null) {

                                params = new HashMap<>();
                                params.put(0, String.valueOf(id));          //ID INTEGRANTE
                                params.put(1, Miscellaneous.validStr(integranteNegocio.getNombre()));     //NOMBRE
                                params.put(2, Miscellaneous.validStr(integranteNegocio.getLatitud()));    //LATITID
                                params.put(3, Miscellaneous.validStr(integranteNegocio.getLongitud()));   //LONGITUD
                                params.put(4, Miscellaneous.validStr(integranteNegocio.getCalle()));      //CALLE
                                params.put(5, Miscellaneous.validStr(integranteNegocio.getNoExterior())); //NO EXTERIOR
                                params.put(6, Miscellaneous.validStr(integranteNegocio.getNoInterior())); //NO INTERIOR
                                params.put(7, Miscellaneous.validStr(integranteNegocio.getNoManzana()));  //MANZANA
                                params.put(8, Miscellaneous.validStr(integranteNegocio.getNoLote()));     //LOTE
                                params.put(9, String.valueOf(integranteNegocio.getCodigoPostal()));       //CP
                                params.put(10, Miscellaneous.GetColonia(this.sidertMovilApplication, integranteNegocio.getColoniaId()));//COLONIA
                                params.put(11, Miscellaneous.validStr(integranteNegocio.getCiudad()));    //CIUDAD
                                params.put(12, Miscellaneous.GetLocalidad(this.sidertMovilApplication, integranteNegocio.getLocalidadId())); //LOCALIDAD
                                params.put(13, Miscellaneous.GetMunicipio(this.sidertMovilApplication, integranteNegocio.getMunicipioId())); //MUNICIPIO
                                params.put(14, Miscellaneous.GetEstado(this.sidertMovilApplication, integranteNegocio.getEstadoId())); //ESTADO
                                params.put(15, Miscellaneous.GetDestinoCredito(this.sidertMovilApplication, integranteNegocio.getDestinoCreditoId())); //DESTINO CREDITO
                                params.put(16, Miscellaneous.validStr(integranteNegocio.getOtroDestinoCredito())); //OTRO DESTINO CREDITO
                                params.put(17, Miscellaneous.GetOcupacion(this.sidertMovilApplication, integranteNegocio.getOcupacionId())); //OCUPACION
                                params.put(18, Miscellaneous.GetSector(this.sidertMovilApplication, integranteNegocio.getSectorId())); //ACTIVIDAD ECONOMICA
                                params.put(19, String.valueOf(Miscellaneous.validInt(integranteNegocio.getAntiguedad()))); //ANTIGUEDA
                                params.put(20, String.valueOf(integranteNegocio.getIngresoMensual()));   //INGRESO MENSUAL
                                params.put(21, String.valueOf(integranteNegocio.getIngresosOtros()));    //INGRESOS OTROS
                                params.put(22, String.valueOf(integranteNegocio.getGastoMensual()));     //GASTO MENSUAL
                                params.put(23, String.valueOf(integranteNegocio.getCapacidadPago()));    //CAPACIDAD DE PAGO
                                params.put(24, String.valueOf(integranteNegocio.getMontoMaximo()));      //MONTO MAXIMO
                                params.put(25, Miscellaneous.GetMediosPagoSoli(this.sidertMovilApplication, integranteNegocio.getMediosPagosIds()));                          //MEDIOS PAGO
                                params.put(26, Miscellaneous.validStr(integranteNegocio.getOtroMedioPago()));                          //OTRO MEDIO DE PAGO
                                int numOper = 30 / item.getPrestamoGpo().getPeriodicida();
                                params.put(27, String.valueOf(numOper));                          //NUM OPERACIONES MENSUALES
                                params.put(28, String.valueOf(Miscellaneous.validInt(integranteNegocio.getNumOperacionesMensualesEfectivo())));                          //NUM OPERACIONES MENSUALES EFECTIVO
                                params.put(29, "");                          //FOTO FACHADA
                                params.put(30, Miscellaneous.validStr(integranteNegocio.getReferencia()));                          //REFERENCIA DOMICILIARIA
                                params.put(31, "0");                         //ESTATUS RECHAZO
                                params.put(32, "");                          //COMENTARIO RECHAZADO
                                params.put(33, "1");                         //ESTATUS COMPLETADO

                                dBhelper.saveDatosNegocioGpo(db, params, tipo);
                            }


                            if (inte.getCliente().getEstadoCivilId() == 2 || inte.getCliente().getEstadoCivilId() == 5) {
                                //Inserta registro del conyuge
                                MSolicitudAutorizar.Conyuge conyuge = inte.getConyuge();
                                if (conyuge != null) {
                                    params = new HashMap<>();
                                    params.put(0, String.valueOf(id));          //ID INTEGRANTE
                                    params.put(1, Miscellaneous.validStr(conyuge.getNombre()).trim().toUpperCase());       //NOMBRE
                                    params.put(2, Miscellaneous.validStr(conyuge.getPaterno()).trim().toUpperCase());      //PATERNO
                                    params.put(3, Miscellaneous.validStr(conyuge.getMaterno()).trim().toUpperCase());      //MATERNO
                                    params.put(4, Miscellaneous.validStr(conyuge.getNacionalidad()).trim().toUpperCase()); //NACIONALIDAD
                                    params.put(5, Miscellaneous.GetOcupacion(this.sidertMovilApplication, conyuge.getOcupacionId()));              //OCUPACION
                                    params.put(6, Miscellaneous.validStr(conyuge.getCalle()).trim().toUpperCase());        //CALLE
                                    params.put(7, Miscellaneous.validStr(conyuge.getNoExterior()).trim().toUpperCase());   //NO EXTERIOR
                                    params.put(8, Miscellaneous.validStr(conyuge.getNoInterior()).trim().toUpperCase());   //NO INTERIOR
                                    params.put(9, Miscellaneous.validStr(conyuge.getNoManzana()).trim().toUpperCase());    //MANZANA
                                    params.put(10, Miscellaneous.validStr(conyuge.getNoLote()).trim().toUpperCase());      //LOTE
                                    params.put(11, String.valueOf(conyuge.getCodigoPostal()));                             //CP
                                    params.put(12, Miscellaneous.GetColonia(this.sidertMovilApplication, conyuge.getColoniaId()));                 //COLONIA
                                    params.put(13, Miscellaneous.validStr(conyuge.getCiudad()).trim().toUpperCase());      //CIUDAD
                                    params.put(14, Miscellaneous.GetLocalidad(this.sidertMovilApplication, conyuge.getLocalidadId()));             //LOCALIDAD
                                    params.put(15, Miscellaneous.GetMunicipio(this.sidertMovilApplication, conyuge.getMunicipioId()));             //MUNICIPIO
                                    params.put(16, Miscellaneous.GetEstado(this.sidertMovilApplication, conyuge.getEstadoId()));                   //ESTADO
                                    params.put(17, String.valueOf(conyuge.getIngresoMensual()));                           //INGRESO MENSUAL
                                    params.put(18, String.valueOf(conyuge.getGastoMensual()));                             //GASTO MENSUAL
                                    params.put(19, Miscellaneous.validStr(conyuge.getTelCasa()));                          //TEL CASA
                                    params.put(20, Miscellaneous.validStr(conyuge.getTelCelular()));                       //TEL CELULAR
                                    params.put(21, "1");                         //ESTATUS COMPLETADO
                                    dBhelper.saveDatosConyugeGpo(db, params, tipo);
                                }
                            } //fin de registrar conyuge

                            //Inserta otros datos del integrante
                            params = new HashMap<>();
                            params.put(0, String.valueOf(id));                                                         //ID INTEGRANTE
                            params.put(1, Miscellaneous.validStr(inte.getClasificacion()));                            //CLASIFICACION RIESGO
                            params.put(2, Miscellaneous.GetMedioContacto(this.sidertMovilApplication, inte.getCliente().getMedioContactoId()));//MEDIO CONTACTO
                            params.put(3, inte.getCliente().getEmail());                                               //EMAIL
                            params.put(4, inte.getCliente().getEstadoCuenta());                                        //ESTADO CUENTA
                            params.put(5, String.valueOf(inte.getEstatusIntegrante()));                                //ESTATUS INTEGRANTE
                            params.put(6, String.valueOf(inte.getMonto()));                                            //MONTO SOLICITADO
                            params.put(7, (inte.getCasaReunion()) ? "1" : "2");                                            //CASA REUNION
                            params.put(8, "");                                                                         //FIRMA
                            params.put(9, "0");                                                                        //ESTATUS COMPLETADO
                            params.put(10, "");                                                                        //MONTO AUTORIZADO
                            dBhelper.saveDatosOtrosGpoAuto(db, params);

                            if (inte.getCasaReunion()) {
                                //Inserta registro de croquis
                                params = new HashMap<>();
                                params.put(0, String.valueOf(id));                      //ID SOLICITUD
                                params.put(1, inte.getCroquis().getCalleEnfrente());    //CALLE PRINCIPAL
                                params.put(2, inte.getCroquis().getLateralIzquierda()); //LATERAL UNO
                                params.put(3, inte.getCroquis().getLateralDerecha());   //LATERAL DOS
                                params.put(4, inte.getCroquis().getCalleAtras());       //CALLE TRASERA
                                params.put(5, inte.getCroquis().getReferencias());      //REFERENCIAS
                                params.put(6, "1");                                     //ESTATUS COMPLETADO

                                dBhelper.saveCroquisGpo(db, params, tipo);
                            } //fin de registro croquis

                            params = new HashMap<>();
                            params.put(0, String.valueOf(id));                                       //ID INTEGRANTE
                            params.put(1, (inte.getPoliticas().getPropietario()) ? "1" : "2");           //PROPIETARIO REAL
                            params.put(2, (inte.getPoliticas().getProveedorRecursos()) ? "1" : "2");     //PROVEEDOR RECURSOS
                            params.put(3, (inte.getPoliticas().getPoliticamenteExpuesto()) ? "1" : "2"); //PERSONA POLITICA
                            params.put(4, "1");                     //ESTATUS COMPLETADO

                            dBhelper.savePoliticasIntegrante(db, params, tipo);

                        }
                    }
                }
                printInfoInUI("Cargando prestamos autorizados", 5);
            } else {
                this.klassTag.e("CodeSolicitudeAutorizar Autorizar: %s", response);
            }
        } catch (ExecutionException | InterruptedException | TimeoutException e) {
            this.klassTag.e(e);
        }

    }

    private void getGestionesVerDom() {
        printInfoInUI("Cargando gestiones", -6);

        VerificacionDomiciliariaDao verificacionDao = new VerificacionDomiciliariaDao(this.sidertMovilApplication);

        Call<List<VerificacionDomiciliaria>> call = verificacionDomiciliariaRemoteDatasource.show();

        try {
            Response<List<VerificacionDomiciliaria>> response = this.executorUtil.runTaskInThread(call::execute);
            if (response.code() == 200) {
                List<VerificacionDomiciliaria> verificaciones = response.body();

                if (verificaciones != null && verificaciones.size() > 0) {
                    for (VerificacionDomiciliaria item : verificaciones) {
                        VerificacionDomiciliaria verificacionDb;
                        verificacionDb = verificacionDao.findByVerificacionDomiciliariaId(item.getVerificacionDomiciliariaId());
                        if (verificacionDb != null) {
                            item.setId(verificacionDb.getId());
                            verificacionDao.update(item);
                        } else {
                            verificacionDao.store(item);
                        }
                    }
                }
            } else {
                Log.e("ERROR AQUI PREST AUT", response.message());
            }
            printInfoInUI("Cargando gestiones", 6);
        } catch (ExecutionException | InterruptedException | TimeoutException e) {
            this.klassTag.e(e);
        }
    }

    private void getCartera() {
        printInfoInUI("Cargando cartera", -8);
        DBhelper dBhelper = DBhelper.getInstance(sidertMovilApplication);
        SQLiteDatabase db = dBhelper.getWritableDatabase();

        this.klassTag.i("GETCARTERA");

        //Se prepara la peticion colocando el id del usuario*/
        Call<List<MCartera>> call = carteraRemoteDatasource.getCartera(sessionManager.getUser().get(9));

        //Se realiza la peticion de obtencion solo de la cartera*/
        try {
            Response<List<MCartera>> response = this.executorUtil.runTaskInThread(call::execute);
            this.klassTag.i("Code cartera %s", String.valueOf(response.code()));
            //Se valida el codigo de respuesta*/
            if (response.code() == 200) {//Obtiene un listado de la cartera asignado*/
                List<MCartera> cartera = response.body();
                if (cartera == null) {
                    return;
                }

                if (cartera.size() > 0) {
                    Cursor row;
                    safeCarteraLoadingProcess(0, cartera.size());
                    //Recorre toda la cartera*/
                    for (int i = 0; i < cartera.size(); i++) {
                        String where = " WHERE id_cartera = ?";
                        String order = "";
                        String[] args = new String[]{String.valueOf(cartera.get(i).getId())};
                        //Se valida el tipo de cartera Individual o grupal*/
                        switch (Miscellaneous.GetTipoCartera(cartera.get(i).getCarteraTipo())) {
                            case 1://Individual*/

                                //Se busca si existe la cartera registrada en movil*/
                                row = dBhelper.getRecords(TBL_CARTERA_IND_T, where, order, args);

                                //En caso de no existir registra la cartera*/
                                if (row.getCount() == 0) { //Registra la cartera de ind
                                    row.close();
                                    //Registra la cartera*/
                                    HashMap<Integer, String> values = new HashMap<>();
                                    values.put(0, String.valueOf(cartera.get(i).getId()));              //ID
                                    values.put(1, cartera.get(i).getClave());                           //CLAVE
                                    values.put(2, cartera.get(i).getNombre());                          //NOMBRE
                                    values.put(3, cartera.get(i).getDireccion());                       //DIRECCION
                                    values.put(4, cartera.get(i).getAsesorNombre());                    //ASESOR NOMBRE
                                    values.put(5, cartera.get(i).getSerieId());                         //SERIE ID
                                    values.put(6, (cartera.get(i).getRuta()) ? "1" : "0");                  //IS RUTA
                                    values.put(7, (cartera.get(i).getRuta()) ? "1" : "0");                  //RUTA OBLIGADO
                                    values.put(8, String.valueOf(cartera.get(i).getUsuarioId()));       //USUARIO ID
                                    values.put(9, cartera.get(i).getDia());                             //DIA
                                    values.put(10, cartera.get(i).getNumSolicitud());                   //NUM SOLICITUD
                                    values.put(11, Miscellaneous.ObtenerFecha(TIMESTAMP));              //FECHA CREACION
                                    values.put(12, Miscellaneous.ObtenerFecha(TIMESTAMP));              //FECHA ACTUALIZACION
                                    values.put(13, cartera.get(i).getColonia());                        //COLONIA
                                    values.put(14, (cartera.get(i).getGeoCliente() ? "1" : "0"));           //GEO CLIENTE
                                    values.put(15, (cartera.get(i).getGeoAval() ? "1" : "0"));              //GEO AVAL
                                    values.put(16, (cartera.get(i).getGeoNegocio() ? "1" : "0"));           //GEO NEGOCIO
                                    values.put(17, "0");                                                //CC
                                    values.put(18, "0");                                                //AGF
                                    values.put(19, "");                                                 //CURP
                                    values.put(20, String.valueOf(cartera.get(i).getDiasAtraso()));     //DIAS ATRASO
                                            /*values.put(17, (cartera.get(i).getCcInd()?"1":"0"));                //CC
                                            values.put(18, (cartera.get(i).getAgfInd()?"1":"0"));               //AGF
                                            values.put(19, cartera.get(i).getCurp());                           //CURP*/

                                    dBhelper.saveCarteraInd(db, TBL_CARTERA_IND_T, values);

                                } else { //Actualiza la cartera de ind
                                    //Actualiza los datos de la cartera porque ya existe*/
                                    row.close();
                                    ContentValues cv = new ContentValues();
                                    cv.put("nombre", cartera.get(i).getNombre());
                                    cv.put("direccion", cartera.get(i).getDireccion());
                                    cv.put("asesor_nombre", cartera.get(i).getAsesorNombre());
                                    cv.put("serie_id", cartera.get(i).getSerieId());
                                    cv.put("is_ruta", (cartera.get(i).getRuta()) ? 1 : 0);
                                    cv.put("ruta_obligado", (cartera.get(i).getRuta()) ? 1 : 0);
                                    cv.put("usuario_id", String.valueOf(cartera.get(i).getUsuarioId()));
                                    cv.put("dia", cartera.get(i).getDia());
                                    cv.put("num_solicitud", cartera.get(i).getNumSolicitud());
                                    cv.put("fecha_actualizado", Miscellaneous.ObtenerFecha(TIMESTAMP));
                                    cv.put("colonia", cartera.get(i).getColonia());
                                    cv.put("geo_cliente", (cartera.get(i).getGeoCliente() ? 1 : 0));
                                    cv.put("geo_aval", (cartera.get(i).getGeoAval() ? 1 : 0));
                                    cv.put("geo_negocio", (cartera.get(i).getGeoNegocio() ? 1 : 0));
                                    cv.put("cc", 0);
                                    cv.put("agf", 0);
                                    cv.put("curp", "");
                                    cv.put("dias_atraso", String.valueOf(cartera.get(i).getDiasAtraso()));
                                            /*cv.put("cc", (cartera.get(i).getCcInd()?1:0));
                                            cv.put("agf", (cartera.get(i).getAgfInd()?1:0));
                                            cv.put("curp", cartera.get(i).getCurp());*/

                                    db.update(TBL_CARTERA_IND_T, cv, "id_cartera = ?", new String[]{String.valueOf(cartera.get(i).getId())});

                                }

                                row.close();
                                break;
                            case 2://Grupal*/

                                //Busca si existe la cartera en el movil*/
                                row = dBhelper.getRecords("cartera_grupo_t", where, order, args);

                                //En caso de no existir registra la cartera*/
                                if (row.getCount() == 0) { //Registra la cartera de gpo
                                    row.close();
                                    HashMap<Integer, String> values = new HashMap<>();
                                    values.put(0, String.valueOf(cartera.get(i).getId()));              //ID
                                    values.put(1, cartera.get(i).getClave());                           //CLAVE
                                    values.put(2, cartera.get(i).getNombre());                          //NOMBRE
                                    values.put(3, cartera.get(i).getTesorero());                        //TESORERA
                                    values.put(4, cartera.get(i).getDireccion());                       //DIRECCION
                                    values.put(5, cartera.get(i).getAsesorNombre());                    //ASESOR NOMBRE
                                    values.put(6, cartera.get(i).getSerieId());                         //SERIE ID
                                    values.put(7, (cartera.get(i).getRuta()) ? "1" : "0");                  //IS RUTA
                                    values.put(8, (cartera.get(i).getRuta()) ? "1" : "0");                  //RUTA OBLIGADO
                                    values.put(9, String.valueOf(cartera.get(i).getUsuarioId()));       //USUARIO ID
                                    values.put(10, cartera.get(i).getDia());                            //DIA
                                    values.put(11, cartera.get(i).getNumSolicitud());                   //NUM SOLICITUD
                                    values.put(12, Miscellaneous.ObtenerFecha(TIMESTAMP));              //FECHA CREACION
                                    values.put(13, Miscellaneous.ObtenerFecha(TIMESTAMP));              //FECHA ACTUALIZACION
                                    values.put(14, cartera.get(i).getColonia());                        //COLONIA
                                    values.put(15, cartera.get(i).getGeolocalizadas());                 //GEOLOCALIZADAS
                                    values.put(16, "{}");                                               //CC
                                    values.put(17, "{}");                                               //AGF
                                    values.put(18, String.valueOf(cartera.get(i).getDiasAtraso()));     //DIAS_ATRASO
                                            /*values.put(16, cartera.get(i).getCcGpo());                          //CC
                                            values.put(17, cartera.get(i).getAgfGpo());                         //AGF*/

                                    dBhelper.saveCarteraGpo(db, TBL_CARTERA_GPO_T, values);
                                } else { //Actualiza la cartera de gpo
                                    //Actualiza la cartera por que existe en el movil*/
                                    row.close();
                                    ContentValues cv = new ContentValues();
                                    cv.put("nombre", cartera.get(i).getNombre());
                                    cv.put("tesorera", cartera.get(i).getTesorero());
                                    cv.put("direccion", cartera.get(i).getDireccion());
                                    cv.put("asesor_nombre", cartera.get(i).getAsesorNombre());
                                    cv.put("serie_id", cartera.get(i).getSerieId());
                                    cv.put("is_ruta", (cartera.get(i).getRuta()) ? 1 : 0);
                                    cv.put("ruta_obligado", (cartera.get(i).getRuta()) ? 1 : 0);
                                    cv.put("usuario_id", String.valueOf(cartera.get(i).getUsuarioId()));
                                    cv.put("dia", cartera.get(i).getDia());
                                    cv.put("num_solicitud", cartera.get(i).getNumSolicitud());
                                    cv.put("fecha_actualizado", Miscellaneous.ObtenerFecha(TIMESTAMP));
                                    cv.put("colonia", cartera.get(i).getColonia());
                                    cv.put("geolocalizadas", cartera.get(i).getGeolocalizadas());
                                    cv.put("dias_atraso", String.valueOf(cartera.get(i).getDiasAtraso()));

                                    db.update(TBL_CARTERA_GPO_T, cv, "id_cartera = ?", new String[]{String.valueOf(cartera.get(i).getId())});
                                }
                                row.close();
                                break;
                        }//Fin SWITCH
                        safeCarteraLoadingProcess(i + 1, cartera.size());
                    } //Fin Ciclo For
                }//Fin IF
                //Pasa al siguiente proceso que es obtener prestamos*/
                printInfoInUI("Cargando cartera", 8);
            }
        } catch (InterruptedException | TimeoutException | ExecutionException exception) {
            this.klassTag.e(exception);
        }
    }

    @FunctionalInterface
    public interface Contract {
        void finish();
    }

    @FunctionalInterface
    public interface ShowInfo {
        void showInfoInScreen(String msg, int checkToRelease);
    }

    @FunctionalInterface
    public interface CarteraLoadingProcess {
        void advance(int increment, int total);
    }

}
