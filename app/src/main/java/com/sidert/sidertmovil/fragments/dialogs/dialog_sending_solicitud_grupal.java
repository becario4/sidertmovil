package com.sidert.sidertmovil.fragments.dialogs;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.database.DBhelper;
import com.sidert.sidertmovil.database.EntitiesCommonsContants;
import com.sidert.sidertmovil.database.dao.BeneficiariosDao;
import com.sidert.sidertmovil.database.dao.SolicitudCampanaDao;
import com.sidert.sidertmovil.database.entities.Beneficiario;
import com.sidert.sidertmovil.database.entities.SolicitudCampana;
import com.sidert.sidertmovil.models.MResSaveSolicitud;
import com.sidert.sidertmovil.models.SolicitudCreditoErrorMessage;
import com.sidert.sidertmovil.models.catalogos.MedioPagoDao;
import com.sidert.sidertmovil.models.solicitudes.Solicitud;
import com.sidert.sidertmovil.models.solicitudes.SolicitudDao;
import com.sidert.sidertmovil.models.solicitudes.SolicitudRen;
import com.sidert.sidertmovil.models.solicitudes.SolicitudRenDao;
import com.sidert.sidertmovil.models.solicitudes.solicitudgpo.originacion.ConyugueIntegrante;
import com.sidert.sidertmovil.models.solicitudes.solicitudgpo.originacion.ConyugueIntegranteDao;
import com.sidert.sidertmovil.models.solicitudes.solicitudgpo.originacion.CreditoGpo;
import com.sidert.sidertmovil.models.solicitudes.solicitudgpo.originacion.CreditoGpoDao;
import com.sidert.sidertmovil.models.solicitudes.solicitudgpo.originacion.CroquisIntegrante;
import com.sidert.sidertmovil.models.solicitudes.solicitudgpo.originacion.CroquisIntegranteDao;
import com.sidert.sidertmovil.models.solicitudes.solicitudgpo.originacion.DocumentoIntegrante;
import com.sidert.sidertmovil.models.solicitudes.solicitudgpo.originacion.DocumentoIntegranteDao;
import com.sidert.sidertmovil.models.solicitudes.solicitudgpo.originacion.DomicilioIntegrante;
import com.sidert.sidertmovil.models.solicitudes.solicitudgpo.originacion.DomicilioIntegranteDao;
import com.sidert.sidertmovil.models.solicitudes.solicitudgpo.originacion.IntegranteGpo;
import com.sidert.sidertmovil.models.solicitudes.solicitudgpo.originacion.IntegranteGpoDao;
import com.sidert.sidertmovil.models.solicitudes.solicitudgpo.originacion.NegocioIntegrante;
import com.sidert.sidertmovil.models.solicitudes.solicitudgpo.originacion.NegocioIntegranteDao;
import com.sidert.sidertmovil.models.solicitudes.solicitudgpo.originacion.OtrosDatosIntegrante;
import com.sidert.sidertmovil.models.solicitudes.solicitudgpo.originacion.OtrosDatosIntegranteDao;
import com.sidert.sidertmovil.models.solicitudes.solicitudgpo.originacion.PoliticaPldIntegrante;
import com.sidert.sidertmovil.models.solicitudes.solicitudgpo.originacion.PoliticaPldIntegranteDao;
import com.sidert.sidertmovil.models.solicitudes.solicitudgpo.originacion.TelefonoIntegrante;
import com.sidert.sidertmovil.models.solicitudes.solicitudgpo.originacion.TelefonoIntegranteDao;
import com.sidert.sidertmovil.models.solicitudes.solicitudgpo.renovacion.ConyugueIntegranteRen;
import com.sidert.sidertmovil.models.solicitudes.solicitudgpo.renovacion.ConyugueIntegranteRenDao;
import com.sidert.sidertmovil.models.solicitudes.solicitudgpo.renovacion.CreditoGpoRen;
import com.sidert.sidertmovil.models.solicitudes.solicitudgpo.renovacion.CreditoGpoRenDao;
import com.sidert.sidertmovil.models.solicitudes.solicitudgpo.renovacion.CroquisIntegranteRen;
import com.sidert.sidertmovil.models.solicitudes.solicitudgpo.renovacion.CroquisIntegranteRenDao;
import com.sidert.sidertmovil.models.solicitudes.solicitudgpo.renovacion.DocumentoIntegranteRen;
import com.sidert.sidertmovil.models.solicitudes.solicitudgpo.renovacion.DocumentoIntegranteRenDao;
import com.sidert.sidertmovil.models.solicitudes.solicitudgpo.renovacion.DomicilioIntegranteRen;
import com.sidert.sidertmovil.models.solicitudes.solicitudgpo.renovacion.DomicilioIntegranteRenDao;
import com.sidert.sidertmovil.models.solicitudes.solicitudgpo.renovacion.IntegranteGpoRen;
import com.sidert.sidertmovil.models.solicitudes.solicitudgpo.renovacion.IntegranteGpoRenDao;
import com.sidert.sidertmovil.models.solicitudes.solicitudgpo.renovacion.NegocioIntegranteRen;
import com.sidert.sidertmovil.models.solicitudes.solicitudgpo.renovacion.NegocioIntegranteRenDao;
import com.sidert.sidertmovil.models.solicitudes.solicitudgpo.renovacion.OtrosDatosIntegranteRen;
import com.sidert.sidertmovil.models.solicitudes.solicitudgpo.renovacion.OtrosDatosIntegranteRenDao;
import com.sidert.sidertmovil.models.solicitudes.solicitudgpo.renovacion.PoliticaPldIntegranteRen;
import com.sidert.sidertmovil.models.solicitudes.solicitudgpo.renovacion.PoliticaPldIntegranteRenDao;
import com.sidert.sidertmovil.models.solicitudes.solicitudgpo.renovacion.TelefonoIntegranteRen;
import com.sidert.sidertmovil.models.solicitudes.solicitudgpo.renovacion.TelefonoIntegranteRenDao;
import com.sidert.sidertmovil.services.solicitud.solicitudgpo.SolicitudGpoService;
import com.sidert.sidertmovil.utils.Miscellaneous;
import com.sidert.sidertmovil.utils.RetrofitClient;
import com.sidert.sidertmovil.utils.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

import static com.sidert.sidertmovil.utils.Constants.ES_RENOVACION;
import static com.sidert.sidertmovil.utils.Constants.ID_SOLICITUD;
import static com.sidert.sidertmovil.utils.Constants.K_ACTIVIDAD_ECONOMICA;
import static com.sidert.sidertmovil.utils.Constants.K_ANTIGUEDAD;
import static com.sidert.sidertmovil.utils.Constants.K_BIENES;
import static com.sidert.sidertmovil.utils.Constants.K_CALLE;
import static com.sidert.sidertmovil.utils.Constants.K_CALLE_ATRAS;
import static com.sidert.sidertmovil.utils.Constants.K_CALLE_ENFRENTE;
import static com.sidert.sidertmovil.utils.Constants.K_CALLE_LATERAL_DER;
import static com.sidert.sidertmovil.utils.Constants.K_CALLE_LATERAL_IZQ;
import static com.sidert.sidertmovil.utils.Constants.K_CAPACIDAD_PAGO;
import static com.sidert.sidertmovil.utils.Constants.K_CARACTERISTICAS_DOMICILIO;
import static com.sidert.sidertmovil.utils.Constants.K_CARGO;
import static com.sidert.sidertmovil.utils.Constants.K_CASA_REUNION;
import static com.sidert.sidertmovil.utils.Constants.K_CIUDAD;
import static com.sidert.sidertmovil.utils.Constants.K_CLASIFICACION_RIESGO;
import static com.sidert.sidertmovil.utils.Constants.K_CLIENTE_ID;
import static com.sidert.sidertmovil.utils.Constants.K_CODIGO_POSTAL;
import static com.sidert.sidertmovil.utils.Constants.K_COLONIA;
import static com.sidert.sidertmovil.utils.Constants.K_COMPROBANTE_DOMICILIO;
import static com.sidert.sidertmovil.utils.Constants.K_CURP;
import static com.sidert.sidertmovil.utils.Constants.K_DEPENDIENTES_ECONOMICO;
import static com.sidert.sidertmovil.utils.Constants.K_DESTINO_CREDITO;
import static com.sidert.sidertmovil.utils.Constants.K_EDAD;
import static com.sidert.sidertmovil.utils.Constants.K_EMAIL;
import static com.sidert.sidertmovil.utils.Constants.K_ESTADO;
import static com.sidert.sidertmovil.utils.Constants.K_ESTADO_CIVIL;
import static com.sidert.sidertmovil.utils.Constants.K_ESTADO_CUENTA;
import static com.sidert.sidertmovil.utils.Constants.K_ESTADO_NACIMIENTO;
import static com.sidert.sidertmovil.utils.Constants.K_ESTATUS_INTEGRANTE;
import static com.sidert.sidertmovil.utils.Constants.K_FECHA_DESEMBOLSO;
import static com.sidert.sidertmovil.utils.Constants.K_FECHA_ENVIO;
import static com.sidert.sidertmovil.utils.Constants.K_FECHA_INICIO;
import static com.sidert.sidertmovil.utils.Constants.K_FECHA_NACIMIENTO;
import static com.sidert.sidertmovil.utils.Constants.K_FECHA_TERMINO;
import static com.sidert.sidertmovil.utils.Constants.K_FIRMA;
import static com.sidert.sidertmovil.utils.Constants.K_FOTO_FACHADA;
import static com.sidert.sidertmovil.utils.Constants.K_GASTO_MENSUAL;
import static com.sidert.sidertmovil.utils.Constants.K_GENERO;
import static com.sidert.sidertmovil.utils.Constants.K_GRUPO_ID;
import static com.sidert.sidertmovil.utils.Constants.K_HORA_VISITA;
import static com.sidert.sidertmovil.utils.Constants.K_IDENTIFICACION_FRONTAL;
import static com.sidert.sidertmovil.utils.Constants.K_IDENTIFICACION_REVERSO;
import static com.sidert.sidertmovil.utils.Constants.K_IDENTIFICACION_SELFIE;
import static com.sidert.sidertmovil.utils.Constants.K_IDENTIFICACION_TIPO;
import static com.sidert.sidertmovil.utils.Constants.K_ID_CAMPANA;
import static com.sidert.sidertmovil.utils.Constants.K_INGRESOS_OTROS;
import static com.sidert.sidertmovil.utils.Constants.K_INGRESO_MENSUAL;
import static com.sidert.sidertmovil.utils.Constants.K_LATITUD;
import static com.sidert.sidertmovil.utils.Constants.K_LOCALIDAD;
import static com.sidert.sidertmovil.utils.Constants.K_LOCATED_AT;
import static com.sidert.sidertmovil.utils.Constants.K_LONGITUD;
import static com.sidert.sidertmovil.utils.Constants.K_MATERNO;
import static com.sidert.sidertmovil.utils.Constants.K_MEDIOS_PAGOS;
import static com.sidert.sidertmovil.utils.Constants.K_MEDIO_CONTACTO;
import static com.sidert.sidertmovil.utils.Constants.K_MONTO_LETRA;
import static com.sidert.sidertmovil.utils.Constants.K_MONTO_MAXIMO;
import static com.sidert.sidertmovil.utils.Constants.K_MONTO_PRESTAMO;
import static com.sidert.sidertmovil.utils.Constants.K_MONTO_REFINANCIAR;
import static com.sidert.sidertmovil.utils.Constants.K_MUNICIPIO;
import static com.sidert.sidertmovil.utils.Constants.K_NACIONALIDAD;
import static com.sidert.sidertmovil.utils.Constants.K_NIVEL_ESTUDIO;
import static com.sidert.sidertmovil.utils.Constants.K_NOMBRE;
import static com.sidert.sidertmovil.utils.Constants.K_NOMBRE_GRUPO;
import static com.sidert.sidertmovil.utils.Constants.K_NOMBRE_QUIEN_FIRMA;
import static com.sidert.sidertmovil.utils.Constants.K_NO_EXTERIOR;
import static com.sidert.sidertmovil.utils.Constants.K_NO_IDENTIFICACION;
import static com.sidert.sidertmovil.utils.Constants.K_NO_INTERIOR;
import static com.sidert.sidertmovil.utils.Constants.K_NO_LOTE;
import static com.sidert.sidertmovil.utils.Constants.K_NO_MANZANA;
import static com.sidert.sidertmovil.utils.Constants.K_NUM_OPERACIONES_MENSUAL;
import static com.sidert.sidertmovil.utils.Constants.K_NUM_OPERACIONES_MENSUAL_EFECTIVO;
import static com.sidert.sidertmovil.utils.Constants.K_OBSERVACIONES;
import static com.sidert.sidertmovil.utils.Constants.K_OCUPACION;
import static com.sidert.sidertmovil.utils.Constants.K_OTROS_DATOS;
import static com.sidert.sidertmovil.utils.Constants.K_OTRO_DESTINO_CREDITO;
import static com.sidert.sidertmovil.utils.Constants.K_OTRO_MEDIOS_PAGOS;
import static com.sidert.sidertmovil.utils.Constants.K_OTRO_TIPO_VIVIENDA;
import static com.sidert.sidertmovil.utils.Constants.K_PARENTESCO;
import static com.sidert.sidertmovil.utils.Constants.K_PATERNO;
import static com.sidert.sidertmovil.utils.Constants.K_PERIODICIDAD;
import static com.sidert.sidertmovil.utils.Constants.K_PLAZO;
import static com.sidert.sidertmovil.utils.Constants.K_POLITICAMENTE_EXP;
import static com.sidert.sidertmovil.utils.Constants.K_PROPIETARIO;
import static com.sidert.sidertmovil.utils.Constants.K_PROVEEDOR_RECURSOS;
import static com.sidert.sidertmovil.utils.Constants.K_REFERENCIAS;
import static com.sidert.sidertmovil.utils.Constants.K_REFERENCIA_DOMICILIARIA;
import static com.sidert.sidertmovil.utils.Constants.K_REFERENCIA_NEGOCIO;
import static com.sidert.sidertmovil.utils.Constants.K_RFC;
import static com.sidert.sidertmovil.utils.Constants.K_SOLICITANTE;
import static com.sidert.sidertmovil.utils.Constants.K_SOLICITANTE_CONYUGE;
import static com.sidert.sidertmovil.utils.Constants.K_SOLICITANTE_CROQUIS;
import static com.sidert.sidertmovil.utils.Constants.K_SOLICITANTE_DOCUMENTOS;
import static com.sidert.sidertmovil.utils.Constants.K_SOLICITANTE_NEGOCIO;
import static com.sidert.sidertmovil.utils.Constants.K_SOLICITANTE_POLITICAS;
import static com.sidert.sidertmovil.utils.Constants.K_SOL_LATITUD;
import static com.sidert.sidertmovil.utils.Constants.K_SOL_LOCATED_AT;
import static com.sidert.sidertmovil.utils.Constants.K_SOL_LONGITUD;
import static com.sidert.sidertmovil.utils.Constants.K_TEL_CASA;
import static com.sidert.sidertmovil.utils.Constants.K_TEL_CELULAR;
import static com.sidert.sidertmovil.utils.Constants.K_TEL_MENSAJE;
import static com.sidert.sidertmovil.utils.Constants.K_TEL_TRABAJO;
import static com.sidert.sidertmovil.utils.Constants.K_TIEMPO_VIVIR_SITIO;
import static com.sidert.sidertmovil.utils.Constants.K_TIENE_FIRMA;
import static com.sidert.sidertmovil.utils.Constants.K_TIPO_SOLICITUD;
import static com.sidert.sidertmovil.utils.Constants.K_TIPO_VIVIENDA;
import static com.sidert.sidertmovil.utils.Constants.K_TOTAL_INTEGRANTES;
import static com.sidert.sidertmovil.utils.Constants.TIMESTAMP;
import static com.sidert.sidertmovil.utils.Miscellaneous.runInMainThread;


public class dialog_sending_solicitud_grupal extends DialogFragment {
    private static final String PROGRESS_SEND_MEMBERS_FORMAT = "Enviando integrante: %d de %d";
    private static final String TITLE_FORMAT = "ENVIANDO SOLICITUD DE: %s DEL GRUPO %s";
    private static final String ERROR_TITLE_FORMAT = "ERROR EN EL ENVIO DE: %s";
    private static final String SUCCESS_TITLE_FORMAT = "ENVIO COMPLETADO: %s";
    private ImageButton closeDialogButton;
    private ProgressBar pbSending;
    private TextView infoView;
    private TextView tvTitle;
    private Context ctx;
    private SessionManager session;
    private ExecutorService executorService;
    private final SolicitudCampana defaultCamapana = new SolicitudCampana();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.popup_sending_solicitud_grupal, container, false);
        ctx = getContext();
        session = SessionManager.getInstance(ctx);
        closeDialogButton = v.findViewById(R.id.close_button);
        pbSending = v.findViewById(R.id.pbSending);
        infoView = v.findViewById(R.id.infoView);
        tvTitle = v.findViewById(R.id.tvTitle);
        executorService = Executors.newFixedThreadPool(10);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        closeDialogButton.setOnClickListener((imageView) -> dismiss());
        Bundle args = getArguments();
        if (args != null) {
            defaultCamapana.setCampanaNombre("NINGUNO");
            defaultCamapana.setNombreReferido("NINGUNO");

            showInfoProgress(0, 0);
            long idSolicitud = getArguments().getLong(ID_SOLICITUD);
            boolean esRenovacion = getArguments().getBoolean(ES_RENOVACION);
            if (esRenovacion) {
                sendRenovacion(idSolicitud);
            } else {
                sendOriginacion(idSolicitud);
            }
        }

    }

    private void sendRenovacion(long idSolicitud) {
        SolicitudRenDao solicitudRenDao = new SolicitudRenDao(ctx);
        SolicitudRen solicitudRen = solicitudRenDao.findByIdSolicitud((int) idSolicitud);

        if (solicitudRen != null) {
            CreditoGpoRenDao creditoGpoRenDao = new CreditoGpoRenDao(ctx);
            CreditoGpoRen creditoGpoRen = creditoGpoRenDao.findByIdSolicitud(solicitudRen.getIdSolicitud());

            Integer creditoId = creditoGpoRen.getId();
            IntegranteGpoRenDao integranteGpoRenDao = new IntegranteGpoRenDao(ctx);
            List<IntegranteGpoRen> integranteGpoRenList = integranteGpoRenDao.findAllByIdCredito(creditoId);

            if (!integranteGpoRenList.isEmpty()) {
                Runnable runnable = () -> {
                    int totalMembers = integranteGpoRenList.size();
                    showInfoProgress(0, totalMembers);
                    ResponseResultWrapper responseResultWrapper = null;
                    for (int i = 0; i < integranteGpoRenList.size(); i++) {
                        IntegranteGpoRen integranteGpoRen = integranteGpoRenList.get(i);
                        String integranteNombreCompleto = integranteGpoRen.getNombreCompleto();
                        showInfoProgress(i + 1, totalMembers);
                        this.updateTitleView(integranteNombreCompleto, solicitudRen.getNombre());
                        if (integranteGpoRen.getEstatusCompletado() < 2) {
                            responseResultWrapper = envioDeIntegrantesRenovacion(integranteGpoRen, totalMembers, creditoGpoRen, solicitudRen);
                            if (responseResultWrapper.code == 500) {
                                updateTitleViewError(integranteNombreCompleto, responseResultWrapper);
                                break;
                            } else {
                                updateTitleViewSuccess(integranteNombreCompleto);
                            }
                        }
                    }
                    if (responseResultWrapper != null && responseResultWrapper.code == 200) {
                        Integer totalMembersThatWereSuccessfullySubmitted = integranteGpoRenDao.countIntegrantesWihtStatusSuccessBycreditoId(creditoId);
                        if (totalMembersThatWereSuccessfullySubmitted.equals(totalMembers)) {
                            solicitudRenDao.setCompletado(solicitudRen);
                            runInMainThread(() -> Toast.makeText(ctx, "¡Solicitud enviada!", Toast.LENGTH_LONG).show());
                            FragmentActivity activity = this.getActivity();
                            if (activity != null) {
                                activity.finish();
                            }
                        } else {
                            runInMainThread(() -> Toast.makeText(ctx, "No todos los integrantes fueron enviados correctamente, favor de trata de nuevo", Toast.LENGTH_LONG).show());
                            this.dismiss();
                        }
                    }
                };
                this.executorService.submit(runnable);
            }
        }
    }


    private ResponseResultWrapper envioDeIntegrantesRenovacion(@NonNull final IntegranteGpoRen integranteGpoRen, int totalIntegrantesRenovacion, CreditoGpoRen creditoGpoRen, SolicitudRen solicitudRen) {

        JSONObject json_solicitud = new JSONObject();
        try {
            Integer integranteId = integranteGpoRen.getId();
            Integer solicitudIntegranteId = integranteGpoRen.getIdSolicitudIntegrante();
            String estadoCivil = integranteGpoRen.getEstadoCivil();
            Integer creditoGpoId = creditoGpoRen.getId();

            DomicilioIntegranteRenDao domicilioIntegranteRenDao = new DomicilioIntegranteRenDao(ctx);
            DomicilioIntegranteRen domicilioIntegranteRen = domicilioIntegranteRenDao.findByIdIntegrante(integranteId.longValue());
            OtrosDatosIntegranteRenDao otrosDatosIntegranteRenDao = new OtrosDatosIntegranteRenDao(ctx);
            OtrosDatosIntegranteRen otrosDatosIntegranteRen = otrosDatosIntegranteRenDao.findByIdIntegrante(integranteId);
            ConyugueIntegranteRenDao conyugueIntegranteRenDao = new ConyugueIntegranteRenDao(ctx);
            ConyugueIntegranteRen conyugueIntegranteRen = conyugueIntegranteRenDao.findByIdIntegrante(integranteId);
            NegocioIntegranteRenDao negocioIntegranteRenDao = new NegocioIntegranteRenDao(ctx);
            NegocioIntegranteRen negocioIntegranteRen = negocioIntegranteRenDao.findByIdIntegrante(integranteId);
            DocumentoIntegranteRenDao documentoIntegranteRenDao = new DocumentoIntegranteRenDao(ctx);
            DocumentoIntegranteRen documentoIntegranteRen = documentoIntegranteRenDao.findByIdIntegrante(integranteId);
            PoliticaPldIntegranteRenDao politicaPldIntegranteRenDao = new PoliticaPldIntegranteRenDao(ctx);
            PoliticaPldIntegranteRen politicaPldIntegranteRen = politicaPldIntegranteRenDao.findByIdIntegrante(integranteId);
            CroquisIntegranteRenDao croquisIntegranteRenDao = new CroquisIntegranteRenDao(ctx);
            CroquisIntegranteRen croquisIntegranteRen = croquisIntegranteRenDao.findByIdIntegrante(integranteId);

            SolicitudCampanaDao solicitudCampanaDao = DBhelper.getInstance(ctx).getSolicitudCampanaDao();
            SolicitudCampana solicitudCampana = solicitudCampanaDao
                    .findBySolicitudId(creditoGpoId.longValue(), integranteId, EntitiesCommonsContants.TIPO_SOLICITUD_GRUPAL_RENOVACION)
                    .orElse(defaultCamapana);

            BeneficiariosDao beneficiariosDao = DBhelper.getInstance(ctx).getBeneficiariosDao();
            Optional<Beneficiario> beneficiarioOptional = beneficiariosDao.findBySolicitudId(creditoGpoId.longValue(), integranteId, EntitiesCommonsContants.TIPO_SOLICITUD_GRUPAL_RENOVACION);

            FillSolicitudRen(json_solicitud, totalIntegrantesRenovacion, creditoGpoRen, solicitudRen);
            FillIntegranteRen(json_solicitud, integranteGpoRen, otrosDatosIntegranteRen, domicilioIntegranteRen);
            FillOtrosDatosIntegranteRen(json_solicitud, integranteGpoRen, otrosDatosIntegranteRen);
            if (estadoCivil.equals("CASADO(A)") || estadoCivil.equals("UNION LIBRE")) {
                FillConyugueIntegranteRen(json_solicitud, conyugueIntegranteRen);
            }
            FillNegocioIntegranteRen(json_solicitud, negocioIntegranteRen);
            FillDocumentosIntegranteRen(json_solicitud, documentoIntegranteRen);
            FillPLDIntegranteRen(json_solicitud, politicaPldIntegranteRen);
            if (otrosDatosIntegranteRen.getCasaReunion() == 1) {
                FillCroquisIntegranteRen(json_solicitud, croquisIntegranteRen);
            }

            fillSolicitudCampana(json_solicitud, solicitudCampana);

            if (beneficiarioOptional.isPresent()) {
                fillBeneficiario(json_solicitud, beneficiarioOptional.get());
            }

            MultipartBody.Part fachada_cliente = domicilioIntegranteRen.getFachadaMBPart();
            MultipartBody.Part firma_cliente = otrosDatosIntegranteRen.getFirmaMBPart();
            MultipartBody.Part fachada_negocio = negocioIntegranteRen.getFachadaMBPart();
            MultipartBody.Part foto_ine_frontal = documentoIntegranteRen.getFotoIneFrontalMBPart();
            MultipartBody.Part foto_ine_reverso = documentoIntegranteRen.getFotoIneReversoMBPart();
            MultipartBody.Part foto_curp = documentoIntegranteRen.getCurpMBPart();
            MultipartBody.Part foto_comprobante = documentoIntegranteRen.getComprobanteMBPart();
            MultipartBody.Part ine_selfie = documentoIntegranteRen.getIneSelfieMBPart();
            RequestBody solicitudBody = RequestBody.create(json_solicitud.toString(), MultipartBody.FORM);
            RequestBody solicitudIdBody = RequestBody.create(String.valueOf(solicitudRen.getIdOriginacion()), MultipartBody.FORM);
            RequestBody solicitudIntegranteIdBody = RequestBody.create(solicitudIntegranteId.toString(), MultipartBody.FORM);

            SolicitudGpoService solicitudGpoService = RetrofitClient.newInstance(ctx).create(SolicitudGpoService.class);
            Call<MResSaveSolicitud> call = solicitudGpoService.saveSolicitudGpo(
                    "Bearer " + session.getUser().get(7),
                    solicitudBody,
                    fachada_cliente,
                    firma_cliente,
                    fachada_negocio,
                    foto_ine_frontal,
                    foto_ine_reverso,
                    foto_curp,
                    foto_comprobante,
                    solicitudIdBody,
                    solicitudIntegranteIdBody,
                    ine_selfie
            );

            Future<ResponseResultWrapper> futureResponse = executorService.submit(() -> {

                Response<MResSaveSolicitud> response = call.execute();
                int status = response.code();
                MResSaveSolicitud res = response.body();
                ResponseResultWrapper responseResultWrapper = new ResponseResultWrapper();
                if (status == 200 && res != null) {
                    IntegranteGpoRenDao integranteGpoRenDao = new IntegranteGpoRenDao(ctx);
                    integranteGpoRenDao.setCompletado(integranteGpoRen, res.getIdSolicitud());
                    integranteGpoRen.setEstatusCompletado(2);
                    responseResultWrapper.code = 200;
                    responseResultWrapper.message = "OK!";
                    return responseResultWrapper;
                } else {
                    try (ResponseBody errorBody = response.errorBody()) {
                        responseResultWrapper.code = 500;
                        responseResultWrapper.kind = 2;
                        responseResultWrapper.message = (errorBody == null) ? "Error no identificado" : errorBody.string();
                        return responseResultWrapper;
                    }
                }
            });

            return futureResponse.get();
        } catch (Exception e) {
            ResponseResultWrapper responseResultWrapper = new ResponseResultWrapper();
            responseResultWrapper.code = 500;
            responseResultWrapper.message = e.getMessage();
            return responseResultWrapper;
        }
    }

    private void sendOriginacion(long idSolicitud) {
        SolicitudDao solicitudDao = new SolicitudDao(ctx);
        Solicitud solicitud = solicitudDao.findByIdSolicitud((int) idSolicitud);

        if (solicitud != null) {
            CreditoGpoDao creditoGpoDao = new CreditoGpoDao(ctx);
            CreditoGpo creditoGpo = creditoGpoDao.findByIdSolicitud(solicitud.getIdSolicitud());

            IntegranteGpoDao integranteGpoDao = new IntegranteGpoDao(ctx);
            List<IntegranteGpo> integranteGpoList = integranteGpoDao.findAllByIdCredito(creditoGpo.getId());

            if (!integranteGpoList.isEmpty()) {

                Runnable runnable = () -> {
                    int totalIntegrantes = integranteGpoList.size();
                    this.showInfoProgress(0, totalIntegrantes);
                    ResponseResultWrapper responseResultWrapper = null;
                    for (int count = 0; count < integranteGpoList.size(); count++) {
                        IntegranteGpo integranteGpo = integranteGpoList.get(count);
                        String integranteNombreCompleto = integranteGpo.getNombreCompleto();
                        this.showInfoProgress(count + 1, totalIntegrantes);
                        this.updateTitleView(integranteNombreCompleto, solicitud.getNombre());
                        responseResultWrapper = sendIntegrantePendiente(solicitud, integranteGpo, totalIntegrantes, creditoGpo);
                        if (responseResultWrapper.code == 200) {
                            this.updateTitleViewSuccess(integranteNombreCompleto);
                        } else {
                            this.updateTitleViewError(integranteNombreCompleto, responseResultWrapper);
                            break;
                        }
                    }
                    if (responseResultWrapper != null && responseResultWrapper.code == 200) {
                        int totalSuccess = integranteGpoDao.countIntegrantesWihtStatusSuccessBycreditoId(creditoGpo.getId());
                        if (totalIntegrantes == totalSuccess) {
                            solicitudDao.setCompletado(solicitud);
                            runInMainThread(() -> Toast.makeText(ctx, "¡Solicitud enviada!", Toast.LENGTH_LONG).show());
                            FragmentActivity activity = this.getActivity();
                            if (activity != null) {
                                activity.finish();
                            }
                        } else {
                            runInMainThread(() -> Toast.makeText(ctx, "No todos los integrantes fueron enviados correctamente, favor de trata de nuevo", Toast.LENGTH_LONG).show());
                            this.dismiss();
                        }
                    }
                };
                this.executorService.submit(runnable);

            }
        }
    }

    private ResponseResultWrapper sendIntegrantePendiente(final Solicitud solicitud, final IntegranteGpo integranteGpo, int totalIntegrantes, CreditoGpo creditoGpo) {

        Integer integranteId = integranteGpo.getId();
        String estadoCivil = integranteGpo.getEstadoCivil();
        Integer solicitudIntegranteId = integranteGpo.getIdSolicitudIntegrante();
        Integer creditoGpoId = creditoGpo.getId();

        JSONObject json_solicitud = new JSONObject();

        try {
            DomicilioIntegranteDao domicilioIntegranteDao = new DomicilioIntegranteDao(ctx);
            DomicilioIntegrante domicilioIntegrante = domicilioIntegranteDao.findByIdIntegrante(Long.valueOf(integranteId));
            OtrosDatosIntegranteDao otrosDatosIntegranteDao = new OtrosDatosIntegranteDao(ctx);
            OtrosDatosIntegrante otrosDatosIntegrante = otrosDatosIntegranteDao.findByIdIntegrante(integranteId);
            ConyugueIntegranteDao conyugueIntegranteDao = new ConyugueIntegranteDao(ctx);
            ConyugueIntegrante conyugueIntegrante = conyugueIntegranteDao.findByIdIntegrante(integranteId);
            NegocioIntegranteDao negocioIntegranteDao = new NegocioIntegranteDao(ctx);
            NegocioIntegrante negocioIntegrante = negocioIntegranteDao.findByIdIntegrante(integranteId);
            DocumentoIntegranteDao documentoIntegranteDao = new DocumentoIntegranteDao(ctx);
            DocumentoIntegrante documentoIntegrante = documentoIntegranteDao.findByIdIntegrante(integranteId);
            PoliticaPldIntegranteDao politicaPldIntegranteDao = new PoliticaPldIntegranteDao(ctx);
            PoliticaPldIntegrante politicaPldIntegrante = politicaPldIntegranteDao.findByIdIntegrante(integranteId);
            CroquisIntegranteDao croquisIntegranteDao = new CroquisIntegranteDao(ctx);
            CroquisIntegrante croquisIntegrante = croquisIntegranteDao.findByIdIntegrante(integranteId);

            SolicitudCampanaDao solicitudCampanaDao = DBhelper.getInstance(ctx).getSolicitudCampanaDao();
            SolicitudCampana solicitudCampana = solicitudCampanaDao
                    .findBySolicitudId(creditoGpoId.longValue(), integranteId, EntitiesCommonsContants.TIPO_SOLICITUD_GRUPAL_ORIGINACION)
                    .orElse(defaultCamapana);

            BeneficiariosDao beneficiariosDao = DBhelper.getInstance(ctx).getBeneficiariosDao();
            Optional<Beneficiario> beneficiarioOptional = beneficiariosDao.findBySolicitudId(creditoGpoId.longValue(), integranteId, EntitiesCommonsContants.TIPO_SOLICITUD_GRUPAL_ORIGINACION);

            FillSolicitud(json_solicitud, totalIntegrantes, creditoGpo, solicitud);
            FillIntegrante(json_solicitud, integranteGpo, otrosDatosIntegrante, domicilioIntegrante);
            FillOtrosDatosIntegrante(json_solicitud, integranteGpo, otrosDatosIntegrante);
            if (estadoCivil.equals("CASADO(A)") || estadoCivil.equals("UNION LIBRE")) {
                FillConyugueIntegrante(json_solicitud, conyugueIntegrante);
            }
            FillNegocioIntegrante(json_solicitud, negocioIntegrante);
            FillDocumentosIntegrante(json_solicitud, documentoIntegrante);
            FillPLDIntegrante(json_solicitud, politicaPldIntegrante);
            if (otrosDatosIntegrante.getCasaReunion() == 1) {
                FillCroquisIntegrante(json_solicitud, croquisIntegrante);
            }
            fillSolicitudCampana(json_solicitud, solicitudCampana);
            if (beneficiarioOptional.isPresent()) {
                fillBeneficiario(json_solicitud, beneficiarioOptional.get());
            }

            MultipartBody.Part fachada_cliente = domicilioIntegrante.getFachadaMBPart();
            MultipartBody.Part firma_cliente = otrosDatosIntegrante.getFirmaMBPart();
            MultipartBody.Part fachada_negocio = negocioIntegrante.getFachadaMBPart();
            MultipartBody.Part foto_ine_frontal = documentoIntegrante.getFotoIneFrontalMBPart();
            MultipartBody.Part foto_ine_reverso = documentoIntegrante.getFotoIneReversoMBPart();
            MultipartBody.Part foto_curp = documentoIntegrante.getCurpMBPart();
            MultipartBody.Part foto_comprobante = documentoIntegrante.getComprobanteMBPart();
            MultipartBody.Part ine_selfie = documentoIntegrante.getIneSelfieMBPart();
            RequestBody solicitudBody = RequestBody.create(json_solicitud.toString(), MultipartBody.FORM);
            RequestBody solicitudIdBody = RequestBody.create(String.valueOf(solicitud.getIdOriginacion()), MultipartBody.FORM);
            RequestBody solicitudIntegranteIdBody = RequestBody.create(String.valueOf(solicitudIntegranteId), MultipartBody.FORM);

            SolicitudGpoService solicitudGpoService = RetrofitClient.newInstance(ctx).create(SolicitudGpoService.class);
            Call<MResSaveSolicitud> call = solicitudGpoService.saveSolicitudGpo(
                    "Bearer " + session.getUser().get(7),
                    solicitudBody,
                    fachada_cliente,
                    firma_cliente,
                    fachada_negocio,
                    foto_ine_frontal,
                    foto_ine_reverso,
                    foto_curp,
                    foto_comprobante,
                    solicitudIdBody,
                    solicitudIntegranteIdBody,
                    ine_selfie
            );

            Future<ResponseResultWrapper> future = this.executorService.submit(() -> {
                Response<MResSaveSolicitud> response = call.execute();
                int statusCode = response.code();
                MResSaveSolicitud res = response.body();
                ResponseResultWrapper responseResultWrapper = new ResponseResultWrapper();
                if (statusCode == 200 && res != null) {
                    SolicitudDao solicitudDao = new SolicitudDao(ctx);

                    IntegranteGpoDao integranteGpoDao = new IntegranteGpoDao(ctx);
                    integranteGpoDao.setCompletado(integranteGpo, res.getIdSolicitud(), res.getId_grupo(), res.getId_cliente());

                    solicitudDao.updateIdCliente(res, solicitud);
                    solicitudDao.solicitudEnviadaGpo(solicitud);

                    integranteGpo.setEstatusCompletado(2);

                    responseResultWrapper.code = 200;
                    responseResultWrapper.message = "OK";
                    return responseResultWrapper;

                } else {
                    try (ResponseBody errorBody = response.errorBody()) {
                        responseResultWrapper.code = 500;
                        responseResultWrapper.kind = 2;
                        responseResultWrapper.message = (errorBody == null) ? "Error no identificado" : errorBody.string();
                        return responseResultWrapper;
                    }
                }
            });
            return future.get();
        } catch (Exception e) {
            ResponseResultWrapper responseResultWrapper = new ResponseResultWrapper();
            responseResultWrapper.code = 500;
            responseResultWrapper.message = e.getMessage();
            return responseResultWrapper;
        }
    }

    private void fillBeneficiario(JSONObject jsonSolicitud, Beneficiario beneficiario) throws JSONException {
        JSONObject jsonBeneficiario = new JSONObject();
        jsonBeneficiario.put("nombre", beneficiario.getNombre());
        jsonBeneficiario.put("paterno", beneficiario.getPaterno());
        jsonBeneficiario.put("materno", beneficiario.getMaterno());
        jsonBeneficiario.put("parentesco", beneficiario.getParentesco());
        jsonSolicitud.put("beneficiario_agf", jsonBeneficiario);
    }

    private void fillSolicitudCampana(JSONObject jsonSolicitud, SolicitudCampana solicitudCampana) throws JSONException {
        JSONObject jsonCampana = new JSONObject();
        jsonCampana.put("nombre_referido", solicitudCampana.getNombreReferido());
        jsonCampana.put("nombre_campana", solicitudCampana.getCampanaNombre());
        jsonSolicitud.put("solicitud_campana", jsonCampana);
    }


    private void FillConyugueIntegranteRen(JSONObject json_solicitud, ConyugueIntegranteRen conyugueIntegranteRen) throws JSONException {
        JSONObject json_conyuge = new JSONObject();

        json_conyuge.put(K_NOMBRE, conyugueIntegranteRen.getNombre().trim().toUpperCase());
        json_conyuge.put(K_PATERNO, conyugueIntegranteRen.getPaterno().trim().toUpperCase());
        json_conyuge.put(K_MATERNO, conyugueIntegranteRen.getMaterno().trim().toUpperCase());
        json_conyuge.put(K_NACIONALIDAD, conyugueIntegranteRen.getNacionalidad());
        json_conyuge.put(K_OCUPACION, conyugueIntegranteRen.getOcupacion());
        json_conyuge.put(K_CALLE, conyugueIntegranteRen.getCalle().trim().toUpperCase());
        json_conyuge.put(K_NO_EXTERIOR, conyugueIntegranteRen.getNoExterior().trim().toUpperCase());
        json_conyuge.put(K_NO_INTERIOR, conyugueIntegranteRen.getNoInterior().trim().toUpperCase());
        json_conyuge.put(K_NO_MANZANA, conyugueIntegranteRen.getManzana().trim().toUpperCase());
        json_conyuge.put(K_NO_LOTE, conyugueIntegranteRen.getLote().trim().toUpperCase());
        json_conyuge.put(K_CODIGO_POSTAL, conyugueIntegranteRen.getCp());
        json_conyuge.put(K_COLONIA, conyugueIntegranteRen.getColonia());
        json_conyuge.put(K_CIUDAD, conyugueIntegranteRen.getCiudad());
        json_conyuge.put(K_LOCALIDAD, conyugueIntegranteRen.getLocalidad());
        json_conyuge.put(K_MUNICIPIO, conyugueIntegranteRen.getMunicipio());
        json_conyuge.put(K_ESTADO, conyugueIntegranteRen.getEstado());
        json_conyuge.put(K_INGRESO_MENSUAL, conyugueIntegranteRen.getIngresoMensual());
        json_conyuge.put(K_GASTO_MENSUAL, conyugueIntegranteRen.getGastoMensual());
        json_conyuge.put(K_TEL_CELULAR, conyugueIntegranteRen.getTelCelular());
        json_conyuge.put(K_TEL_CASA, conyugueIntegranteRen.getTelTrabajo());

        json_solicitud.put(K_SOLICITANTE_CONYUGE, json_conyuge);
    }

    private void FillSolicitudRen(JSONObject json_solicitud, int totalIntegrantesRenovacion, CreditoGpoRen creditoGpoRen, SolicitudRen solicitudRen) throws JSONException {
        json_solicitud.put(K_TOTAL_INTEGRANTES, totalIntegrantesRenovacion);
        json_solicitud.put(K_NOMBRE_GRUPO, creditoGpoRen.getNombreGrupo().trim().toUpperCase());
        json_solicitud.put(K_PLAZO, creditoGpoRen.getPlazoAsInt());
        json_solicitud.put(K_PERIODICIDAD, creditoGpoRen.getPeriodicidadAsInt());
        json_solicitud.put(K_FECHA_DESEMBOLSO, creditoGpoRen.getFechaDesembolso());
        json_solicitud.put(K_HORA_VISITA, creditoGpoRen.getHoraVisita());
        json_solicitud.put(K_TIPO_SOLICITUD, "RENOVACION");
        json_solicitud.put(K_OBSERVACIONES, creditoGpoRen.getObservaciones().trim().toUpperCase());
        json_solicitud.put(K_GRUPO_ID, creditoGpoRen.getGrupoId());
        json_solicitud.put(K_FECHA_INICIO, solicitudRen.getFechaInicio());
        json_solicitud.put(K_FECHA_TERMINO, solicitudRen.getFechaTermino());
        json_solicitud.put(K_FECHA_ENVIO, Miscellaneous.ObtenerFecha(TIMESTAMP));
    }

    private void FillIntegranteRen(JSONObject json_solicitud, IntegranteGpoRen integrante, OtrosDatosIntegranteRen otrosDatosIntegranteRen, DomicilioIntegranteRen domicilioIntegranteRen) throws JSONException {
        TelefonoIntegranteRenDao telefonoIntegranteRenDao = new TelefonoIntegranteRenDao(ctx);
        TelefonoIntegranteRen telefonoIntegranteRen = telefonoIntegranteRenDao.findByIdIntegrante(integrante.getId());

        JSONObject json_solicitante = new JSONObject();

        json_solicitante.put(K_CLIENTE_ID, (integrante.getClienteId().isEmpty()) ? "0" : integrante.getClienteId());
        json_solicitante.put(K_NOMBRE, integrante.getNombre().trim().toUpperCase());
        json_solicitante.put(K_PATERNO, integrante.getPaterno().trim().toUpperCase());
        json_solicitante.put(K_MATERNO, integrante.getMaterno().trim().toUpperCase());
        json_solicitante.put(K_FECHA_NACIMIENTO, integrante.getFechaNacimiento());
        json_solicitante.put(K_EDAD, integrante.getEdad());
        json_solicitante.put(K_GENERO, integrante.getGenero());
        json_solicitante.put(K_ESTADO_NACIMIENTO, integrante.getEstadoNacimiento());
        json_solicitante.put(K_RFC, integrante.getRfc());
        json_solicitante.put(K_CURP, integrante.getCurp() + integrante.getCurpDigitoVeri());
        json_solicitante.put(K_IDENTIFICACION_TIPO, integrante.getTipoIdentificacion());
        json_solicitante.put(K_NO_IDENTIFICACION, integrante.getNoIdentificacion());
        json_solicitante.put(K_NIVEL_ESTUDIO, integrante.getNivelEstudio());
        json_solicitante.put(K_OCUPACION, integrante.getOcupacion());
        json_solicitante.put(K_ESTADO_CIVIL, integrante.getEstadoCivil());
        if (integrante.getEstadoCivil().equals("CASADO(A)"))
            json_solicitante.put(K_BIENES, (integrante.getBienes() == 1) ? "MANCOMUNADOS" : "SEPARADOS");


        json_solicitante.put(K_LATITUD, domicilioIntegranteRen.getLatitud());
        json_solicitante.put(K_LONGITUD, domicilioIntegranteRen.getLongitud());
        json_solicitante.put(K_CALLE, domicilioIntegranteRen.getCalle().trim().toUpperCase());
        json_solicitante.put(K_NO_EXTERIOR, domicilioIntegranteRen.getNoExterior().trim().toUpperCase());
        json_solicitante.put(K_NO_INTERIOR, domicilioIntegranteRen.getNoInterior().trim().toUpperCase());
        json_solicitante.put(K_NO_MANZANA, domicilioIntegranteRen.getManzana().trim().toUpperCase());
        json_solicitante.put(K_NO_LOTE, domicilioIntegranteRen.getLote().trim().toUpperCase());
        json_solicitante.put(K_CODIGO_POSTAL, domicilioIntegranteRen.getCp());
        json_solicitante.put(K_COLONIA, domicilioIntegranteRen.getColonia());
        json_solicitante.put(K_CIUDAD, domicilioIntegranteRen.getCiudad().trim().toUpperCase());
        json_solicitante.put(K_LOCALIDAD, domicilioIntegranteRen.getLocalidad());
        json_solicitante.put(K_MUNICIPIO, domicilioIntegranteRen.getMunicipio());
        json_solicitante.put(K_ESTADO, domicilioIntegranteRen.getEstado());
        json_solicitante.put(K_LOCATED_AT, domicilioIntegranteRen.getLocatedAt());
        json_solicitante.put(K_TIPO_VIVIENDA, domicilioIntegranteRen.getTipoVivienda());
        if (domicilioIntegranteRen.getTipoVivienda().equals("CASA FAMILIAR"))
            json_solicitante.put(K_PARENTESCO, domicilioIntegranteRen.getParentesco());
        else if (domicilioIntegranteRen.getTipoVivienda().equals("OTRO"))
            json_solicitante.put(K_OTRO_TIPO_VIVIENDA, domicilioIntegranteRen.getOtroTipoVivienda());
        json_solicitante.put(K_TIEMPO_VIVIR_SITIO, domicilioIntegranteRen.getTiempoVivirSitio());
        json_solicitante.put(K_DEPENDIENTES_ECONOMICO, domicilioIntegranteRen.getDependientes());
        json_solicitante.put(K_FOTO_FACHADA, domicilioIntegranteRen.getFotoFachada());
        json_solicitante.put(K_REFERENCIA_DOMICILIARIA, domicilioIntegranteRen.getRefDomiciliaria().trim().toUpperCase());

        json_solicitante.put(K_TEL_CASA, telefonoIntegranteRen.getTelCasa());
        json_solicitante.put(K_TEL_CELULAR, telefonoIntegranteRen.getTelCelular());
        json_solicitante.put(K_TEL_MENSAJE, telefonoIntegranteRen.getTelMensaje());
        json_solicitante.put(K_TEL_TRABAJO, telefonoIntegranteRen.getTelTrabajo());

        json_solicitante.put(K_MEDIO_CONTACTO, otrosDatosIntegranteRen.getMedioContacto());
        json_solicitante.put(K_EMAIL, otrosDatosIntegranteRen.getEmail().trim().toUpperCase());
        json_solicitante.put(K_ESTADO_CUENTA, otrosDatosIntegranteRen.getEstadoCuenta());
        json_solicitante.put(K_FIRMA, otrosDatosIntegranteRen.getFirma());
        json_solicitante.put(K_SOL_LATITUD, otrosDatosIntegranteRen.getLatitud());
        json_solicitante.put(K_SOL_LONGITUD, otrosDatosIntegranteRen.getLongitud());
        json_solicitante.put(K_SOL_LOCATED_AT, otrosDatosIntegranteRen.getLocatedAt());
        json_solicitante.put(K_TIENE_FIRMA, otrosDatosIntegranteRen.getTieneFirma());
        json_solicitante.put(K_NOMBRE_QUIEN_FIRMA, otrosDatosIntegranteRen.getNombreQuienFirma());

        json_solicitud.put(K_SOLICITANTE, json_solicitante);
    }

    private void FillOtrosDatosIntegranteRen(JSONObject json_solicitud, IntegranteGpoRen integranteGpoRen, OtrosDatosIntegranteRen otrosDatosIntegranteRen) throws JSONException {
        JSONObject json_otros_datos = new JSONObject();

        json_otros_datos.put(K_CARGO, integranteGpoRen.getCargo());
        json_otros_datos.put(K_CLASIFICACION_RIESGO, otrosDatosIntegranteRen.getClasificacionRiesgo());
        json_otros_datos.put(K_ESTATUS_INTEGRANTE, otrosDatosIntegranteRen.getEstatusIntegrante());
        json_otros_datos.put(K_MONTO_PRESTAMO, otrosDatosIntegranteRen.getMontoSolicitado());
        json_otros_datos.put(K_ID_CAMPANA, otrosDatosIntegranteRen.getId_campana());


        json_otros_datos.put(K_MONTO_LETRA, (
                Miscellaneous.cantidadLetra(
                        otrosDatosIntegranteRen.getMontoSolicitado()).toUpperCase() +
                        " PESOS MEXICANOS"
        ).replace("  ", " "));

        json_otros_datos.put(K_CASA_REUNION, (otrosDatosIntegranteRen.getCasaReunion() == 1));

        int montoRefinanciar = 0;

        if (
                otrosDatosIntegranteRen.getMontoRefinanciar() != null
                        && !otrosDatosIntegranteRen.getMontoRefinanciar().isEmpty()
        )
            montoRefinanciar = Integer.parseInt(otrosDatosIntegranteRen.getMontoRefinanciar().replace(",", ""));

        json_otros_datos.put(K_MONTO_REFINANCIAR, montoRefinanciar);

        json_solicitud.put(K_OTROS_DATOS, json_otros_datos);
    }

    private void FillNegocioIntegranteRen(JSONObject json_solicitud, NegocioIntegranteRen negocioIntegranteRen) throws JSONException {
        MedioPagoDao medioPagoDao = new MedioPagoDao(ctx);

        JSONObject json_negocio = new JSONObject();

        json_negocio.put(K_NOMBRE, negocioIntegranteRen.getNombre());
        json_negocio.put(K_LATITUD, negocioIntegranteRen.getLatitud());
        json_negocio.put(K_LONGITUD, negocioIntegranteRen.getLongitud());
        json_negocio.put(K_CALLE, negocioIntegranteRen.getCalle().trim().toUpperCase());
        json_negocio.put(K_NO_EXTERIOR, negocioIntegranteRen.getNoExterior().trim().toUpperCase());
        json_negocio.put(K_NO_INTERIOR, negocioIntegranteRen.getNoInterior().trim().toUpperCase());
        json_negocio.put(K_NO_MANZANA, negocioIntegranteRen.getManzana().trim().toUpperCase());
        json_negocio.put(K_NO_LOTE, negocioIntegranteRen.getLote().trim().toUpperCase());
        json_negocio.put(K_CODIGO_POSTAL, negocioIntegranteRen.getCp());
        json_negocio.put(K_COLONIA, negocioIntegranteRen.getColonia());
        json_negocio.put(K_CIUDAD, negocioIntegranteRen.getCiudad());
        json_negocio.put(K_LOCALIDAD, negocioIntegranteRen.getLocalidad());
        json_negocio.put(K_MUNICIPIO, negocioIntegranteRen.getMunicipio());
        json_negocio.put(K_ESTADO, negocioIntegranteRen.getEstado());
        json_negocio.put(K_LOCATED_AT, negocioIntegranteRen.getLocatedAt());
        json_negocio.put(K_DESTINO_CREDITO, negocioIntegranteRen.getDestinoCredito());
        if (negocioIntegranteRen.getDestinoCredito().contains("OTRO"))
            json_negocio.put(K_OTRO_DESTINO_CREDITO, negocioIntegranteRen.getOtroDestinoCredito());
        json_negocio.put(K_OCUPACION, negocioIntegranteRen.getOcupacion());
        json_negocio.put(K_ACTIVIDAD_ECONOMICA, negocioIntegranteRen.getActividadEconomica());
        json_negocio.put(K_ANTIGUEDAD, negocioIntegranteRen.getAntiguedad());
        json_negocio.put(K_INGRESO_MENSUAL, negocioIntegranteRen.getIngMensual());
        json_negocio.put(K_INGRESOS_OTROS, negocioIntegranteRen.getIngOtros());
        json_negocio.put(K_GASTO_MENSUAL, negocioIntegranteRen.getGastoSemanal());
        json_negocio.put(K_MONTO_MAXIMO, negocioIntegranteRen.getCapacidadPago()); //SE INTERCAMBIA MONTO Y CAPACIDAD
        json_negocio.put(K_CAPACIDAD_PAGO, negocioIntegranteRen.getMontoMaximo());//SE INTERCAMBIA MONTO Y CAPACIDAD
        json_negocio.put(K_MEDIOS_PAGOS, medioPagoDao.findIdsByNombres(negocioIntegranteRen.getMediosPago()));
        if (negocioIntegranteRen.getMediosPago().contains("OTRO"))
            json_negocio.put(K_OTRO_MEDIOS_PAGOS, negocioIntegranteRen.getOtroMedioPago());
        json_negocio.put(K_NUM_OPERACIONES_MENSUAL, negocioIntegranteRen.getNumOpeMensuales());
        if (negocioIntegranteRen.getMediosPago().contains("EFECTIVO"))
            json_negocio.put(K_NUM_OPERACIONES_MENSUAL_EFECTIVO, negocioIntegranteRen.getNumOpeMensualesEfectivo());
        json_negocio.put(K_FOTO_FACHADA, negocioIntegranteRen.getFotoFachada());
        json_negocio.put(K_REFERENCIA_NEGOCIO, negocioIntegranteRen.getRefDomiciliaria());

        json_solicitud.put(K_SOLICITANTE_NEGOCIO, json_negocio);
    }

    private void FillDocumentosIntegranteRen(JSONObject json_solicitud, DocumentoIntegranteRen documentoIntegranteRen) throws JSONException {
        JSONObject json_documentos = new JSONObject();

        json_documentos.put(K_IDENTIFICACION_FRONTAL, documentoIntegranteRen.getIneFrontal());
        json_documentos.put(K_IDENTIFICACION_REVERSO, documentoIntegranteRen.getIneReverso());
        json_documentos.put(K_CURP, documentoIntegranteRen.getCurp());
        json_documentos.put(K_COMPROBANTE_DOMICILIO, documentoIntegranteRen.getComprobante());
        if (documentoIntegranteRen.getIneSelfie() != null)
            json_documentos.put(K_IDENTIFICACION_SELFIE, documentoIntegranteRen.getIneSelfie());
        else
            json_documentos.put(K_IDENTIFICACION_SELFIE, "");

        json_solicitud.put(K_SOLICITANTE_DOCUMENTOS, json_documentos);
    }

    private void FillPLDIntegranteRen(JSONObject json_solicitud, PoliticaPldIntegranteRen pldIntegranteRen) throws JSONException {
        JSONObject json_politicas = new JSONObject();
        json_politicas.put(K_PROPIETARIO, pldIntegranteRen.getPropietarioReal() == 1);
        json_politicas.put(K_PROVEEDOR_RECURSOS, pldIntegranteRen.getProveedorRecursos() == 1);
        json_politicas.put(K_POLITICAMENTE_EXP, pldIntegranteRen.getPersonaPolitica() == 1);
        json_solicitud.put(K_SOLICITANTE_POLITICAS, json_politicas);
    }

    private void FillCroquisIntegranteRen(JSONObject json_solicitud, CroquisIntegranteRen croquisIntegranteRen) throws JSONException {
        JSONObject json_croquis = new JSONObject();

        json_croquis.put(K_CALLE_ENFRENTE, croquisIntegranteRen.getCallePrincipal().trim().toUpperCase());
        json_croquis.put(K_CALLE_LATERAL_IZQ, croquisIntegranteRen.getLateralUno().trim().toUpperCase());
        json_croquis.put(K_CALLE_LATERAL_DER, croquisIntegranteRen.getLateralDos().trim().toUpperCase());
        json_croquis.put(K_CALLE_ATRAS, croquisIntegranteRen.getCalleTrasera().trim().toUpperCase());
        json_croquis.put(K_REFERENCIAS, croquisIntegranteRen.getReferencias().trim().toUpperCase());
        json_croquis.put(K_CARACTERISTICAS_DOMICILIO, croquisIntegranteRen.getCaracteristicasDomicilio().trim().toUpperCase());

        json_solicitud.put(K_SOLICITANTE_CROQUIS, json_croquis);
    }

    private void FillConyugueIntegrante(JSONObject json_solicitud, ConyugueIntegrante conyugueIntegrante) throws JSONException {
        JSONObject json_conyuge = new JSONObject();

        json_conyuge.put(K_NOMBRE, conyugueIntegrante.getNombre().trim().toUpperCase());
        json_conyuge.put(K_PATERNO, conyugueIntegrante.getPaterno().trim().toUpperCase());
        json_conyuge.put(K_MATERNO, conyugueIntegrante.getMaterno().trim().toUpperCase());
        json_conyuge.put(K_NACIONALIDAD, conyugueIntegrante.getNacionalidad());
        json_conyuge.put(K_OCUPACION, conyugueIntegrante.getOcupacion());
        json_conyuge.put(K_CALLE, conyugueIntegrante.getCalle().trim().toUpperCase());
        json_conyuge.put(K_NO_EXTERIOR, conyugueIntegrante.getNoExterior().trim().toUpperCase());
        json_conyuge.put(K_NO_INTERIOR, conyugueIntegrante.getNoInterior().trim().toUpperCase());
        json_conyuge.put(K_NO_MANZANA, conyugueIntegrante.getManzana().trim().toUpperCase());
        json_conyuge.put(K_NO_LOTE, conyugueIntegrante.getLote().trim().toUpperCase());
        json_conyuge.put(K_CODIGO_POSTAL, conyugueIntegrante.getCp());
        json_conyuge.put(K_COLONIA, conyugueIntegrante.getColonia());
        json_conyuge.put(K_CIUDAD, conyugueIntegrante.getCiudad());
        json_conyuge.put(K_LOCALIDAD, conyugueIntegrante.getLocalidad());
        json_conyuge.put(K_MUNICIPIO, conyugueIntegrante.getMunicipio());
        json_conyuge.put(K_ESTADO, conyugueIntegrante.getEstado());
        json_conyuge.put(K_INGRESO_MENSUAL, conyugueIntegrante.getIngresoMensual());
        json_conyuge.put(K_GASTO_MENSUAL, conyugueIntegrante.getGastoMensual());
        json_conyuge.put(K_TEL_CELULAR, conyugueIntegrante.getTelCelular());
        json_conyuge.put(K_TEL_CASA, conyugueIntegrante.getTelTrabajo());

        json_solicitud.put(K_SOLICITANTE_CONYUGE, json_conyuge);
    }

    private void FillSolicitud(JSONObject json_solicitud, int totalIntegrantes, CreditoGpo creditoGpo, Solicitud solicitud) throws JSONException {
        json_solicitud.put(K_TOTAL_INTEGRANTES, totalIntegrantes);
        json_solicitud.put(K_NOMBRE_GRUPO, creditoGpo.getNombreGrupo().trim().toUpperCase());
        json_solicitud.put(K_PLAZO, creditoGpo.getPlazoAsInt());
        json_solicitud.put(K_PERIODICIDAD, creditoGpo.getPeriodicidadAsInt());
        json_solicitud.put(K_FECHA_DESEMBOLSO, creditoGpo.getFechaDesembolso());
        json_solicitud.put(K_HORA_VISITA, creditoGpo.getHoraVisita());
        json_solicitud.put(K_TIPO_SOLICITUD, "ORIGINACION");
        json_solicitud.put(K_FECHA_INICIO, solicitud.getFechaInicio());
        json_solicitud.put(K_FECHA_TERMINO, solicitud.getFechaTermino());
        json_solicitud.put(K_FECHA_ENVIO, Miscellaneous.ObtenerFecha(TIMESTAMP));
    }

    private void FillIntegrante(JSONObject json_solicitud, IntegranteGpo integrante, OtrosDatosIntegrante otrosDatosIntegrante, DomicilioIntegrante domicilioIntegrante) throws JSONException {
        TelefonoIntegranteDao telefonoIntegranteDao = new TelefonoIntegranteDao(ctx);
        TelefonoIntegrante telefonoIntegrante = telefonoIntegranteDao.findByIdIntegrante(integrante.getId());

        JSONObject json_solicitante = new JSONObject();

        json_solicitante.put(K_NOMBRE, integrante.getNombre().trim().toUpperCase());
        json_solicitante.put(K_PATERNO, integrante.getPaterno().trim().toUpperCase());
        json_solicitante.put(K_MATERNO, integrante.getMaterno().trim().toUpperCase());
        json_solicitante.put(K_FECHA_NACIMIENTO, integrante.getFechaNacimiento());
        json_solicitante.put(K_EDAD, integrante.getEdad());
        json_solicitante.put(K_GENERO, integrante.getGenero());
        json_solicitante.put(K_ESTADO_NACIMIENTO, integrante.getEstadoNacimiento());
        json_solicitante.put(K_RFC, integrante.getRfc());
        json_solicitante.put(K_CURP, integrante.getCurp() + integrante.getCurpDigitoVeri());
        json_solicitante.put(K_IDENTIFICACION_TIPO, integrante.getTipoIdentificacion());
        json_solicitante.put(K_NO_IDENTIFICACION, integrante.getNoIdentificacion());
        json_solicitante.put(K_NIVEL_ESTUDIO, integrante.getNivelEstudio());
        json_solicitante.put(K_OCUPACION, integrante.getOcupacion());
        json_solicitante.put(K_ESTADO_CIVIL, integrante.getEstadoCivil());
        if (integrante.getEstadoCivil().equals("CASADO(A)"))
            json_solicitante.put(K_BIENES, (integrante.getBienes() == 1) ? "MANCOMUNADOS" : "SEPARADOS");


        json_solicitante.put(K_LATITUD, domicilioIntegrante.getLatitud());
        json_solicitante.put(K_LONGITUD, domicilioIntegrante.getLongitud());
        json_solicitante.put(K_CALLE, domicilioIntegrante.getCalle().trim().toUpperCase());
        json_solicitante.put(K_NO_EXTERIOR, domicilioIntegrante.getNoExterior().trim().toUpperCase());
        json_solicitante.put(K_NO_INTERIOR, domicilioIntegrante.getNoInterior().trim().toUpperCase());
        json_solicitante.put(K_NO_MANZANA, domicilioIntegrante.getManzana().trim().toUpperCase());
        json_solicitante.put(K_NO_LOTE, domicilioIntegrante.getLote().trim().toUpperCase());
        json_solicitante.put(K_CODIGO_POSTAL, domicilioIntegrante.getCp());
        json_solicitante.put(K_COLONIA, domicilioIntegrante.getColonia());
        json_solicitante.put(K_CIUDAD, domicilioIntegrante.getCiudad().trim().toUpperCase());
        json_solicitante.put(K_LOCALIDAD, domicilioIntegrante.getLocalidad());
        json_solicitante.put(K_MUNICIPIO, domicilioIntegrante.getMunicipio());
        json_solicitante.put(K_ESTADO, domicilioIntegrante.getEstado());
        json_solicitante.put(K_LOCATED_AT, domicilioIntegrante.getLocatedAt());
        json_solicitante.put(K_TIPO_VIVIENDA, domicilioIntegrante.getTipoVivienda());
        if (domicilioIntegrante.getTipoVivienda().equals("CASA FAMILIAR"))
            json_solicitante.put(K_PARENTESCO, domicilioIntegrante.getParentesco());
        else if (domicilioIntegrante.getTipoVivienda().equals("OTRO"))
            json_solicitante.put(K_OTRO_TIPO_VIVIENDA, domicilioIntegrante.getOtroTipoVivienda());
        json_solicitante.put(K_TIEMPO_VIVIR_SITIO, domicilioIntegrante.getTiempoVivirSitio());
        json_solicitante.put(K_DEPENDIENTES_ECONOMICO, domicilioIntegrante.getDependientes());
        json_solicitante.put(K_FOTO_FACHADA, domicilioIntegrante.getFotoFachada());
        json_solicitante.put(K_REFERENCIA_DOMICILIARIA, domicilioIntegrante.getRefDomiciliaria().trim().toUpperCase());

        json_solicitante.put(K_TEL_CASA, telefonoIntegrante.getTelCasa());
        json_solicitante.put(K_TEL_CELULAR, telefonoIntegrante.getTelCelular());
        json_solicitante.put(K_TEL_MENSAJE, telefonoIntegrante.getTelMensaje());
        json_solicitante.put(K_TEL_TRABAJO, telefonoIntegrante.getTelTrabajo());

        json_solicitante.put(K_MEDIO_CONTACTO, otrosDatosIntegrante.getMedioContacto());
        json_solicitante.put(K_EMAIL, otrosDatosIntegrante.getEmail().trim().toUpperCase());
        json_solicitante.put(K_ESTADO_CUENTA, otrosDatosIntegrante.getEstadoCuenta());
        json_solicitante.put(K_FIRMA, otrosDatosIntegrante.getFirma());
        json_solicitante.put(K_SOL_LATITUD, otrosDatosIntegrante.getLatitud());
        json_solicitante.put(K_SOL_LONGITUD, otrosDatosIntegrante.getLongitud());
        json_solicitante.put(K_SOL_LOCATED_AT, otrosDatosIntegrante.getLocatedAt());
        json_solicitante.put(K_TIENE_FIRMA, otrosDatosIntegrante.getTieneFirma());
        json_solicitante.put(K_NOMBRE_QUIEN_FIRMA, otrosDatosIntegrante.getNombreQuienFirma());

        json_solicitud.put(K_SOLICITANTE, json_solicitante);
    }

    private void FillOtrosDatosIntegrante(JSONObject json_solicitud, IntegranteGpo integranteGpo, OtrosDatosIntegrante otrosDatosIntegrante) throws JSONException {
        JSONObject json_otros_datos = new JSONObject();

        json_otros_datos.put(K_CARGO, integranteGpo.getCargo());
        json_otros_datos.put(K_CLASIFICACION_RIESGO, otrosDatosIntegrante.getClasificacionRiesgo());
        json_otros_datos.put(K_ESTATUS_INTEGRANTE, otrosDatosIntegrante.getEstatusIntegrante());
        json_otros_datos.put(K_MONTO_PRESTAMO, otrosDatosIntegrante.getMontoSolicitado());
        json_otros_datos.put(K_ID_CAMPANA, otrosDatosIntegrante.getId_campana());

        json_otros_datos.put(K_MONTO_LETRA, (
                Miscellaneous.cantidadLetra(
                        otrosDatosIntegrante.getMontoSolicitado()).toUpperCase() +
                        " PESOS MEXICANOS"
        ).replace("  ", " "));

        json_otros_datos.put(K_CASA_REUNION, (otrosDatosIntegrante.getCasaReunion() == 1));

        int montoRefinanciar = 0;

        if (
                otrosDatosIntegrante.getMontoRefinanciar() != null
                        && !otrosDatosIntegrante.getMontoRefinanciar().isEmpty()
        )
            montoRefinanciar = Integer.parseInt(otrosDatosIntegrante.getMontoRefinanciar().replace(",", ""));

        json_otros_datos.put(K_MONTO_REFINANCIAR, montoRefinanciar);

        json_solicitud.put(K_OTROS_DATOS, json_otros_datos);
    }

    private void FillNegocioIntegrante(JSONObject json_solicitud, NegocioIntegrante negocioIntegrante) throws JSONException {
        MedioPagoDao medioPagoDao = new MedioPagoDao(ctx);

        JSONObject json_negocio = new JSONObject();

        json_negocio.put(K_NOMBRE, negocioIntegrante.getNombre());
        json_negocio.put(K_LATITUD, negocioIntegrante.getLatitud());
        json_negocio.put(K_LONGITUD, negocioIntegrante.getLongitud());
        json_negocio.put(K_CALLE, negocioIntegrante.getCalle().trim().toUpperCase());
        json_negocio.put(K_NO_EXTERIOR, negocioIntegrante.getNoExterior().trim().toUpperCase());
        json_negocio.put(K_NO_INTERIOR, negocioIntegrante.getNoInterior().trim().toUpperCase());
        json_negocio.put(K_NO_MANZANA, negocioIntegrante.getManzana().trim().toUpperCase());
        json_negocio.put(K_NO_LOTE, negocioIntegrante.getLote().trim().toUpperCase());
        json_negocio.put(K_CODIGO_POSTAL, negocioIntegrante.getCp());
        json_negocio.put(K_COLONIA, negocioIntegrante.getColonia());
        json_negocio.put(K_CIUDAD, negocioIntegrante.getCiudad());
        json_negocio.put(K_LOCALIDAD, negocioIntegrante.getLocalidad());
        json_negocio.put(K_MUNICIPIO, negocioIntegrante.getMunicipio());
        json_negocio.put(K_ESTADO, negocioIntegrante.getEstado());
        json_negocio.put(K_LOCATED_AT, negocioIntegrante.getLocatedAt());
        json_negocio.put(K_DESTINO_CREDITO, negocioIntegrante.getDestinoCredito());
        if (negocioIntegrante.getDestinoCredito().contains("OTRO"))
            json_negocio.put(K_OTRO_DESTINO_CREDITO, negocioIntegrante.getOtroDestinoCredito());
        json_negocio.put(K_OCUPACION, negocioIntegrante.getOcupacion());
        json_negocio.put(K_ACTIVIDAD_ECONOMICA, negocioIntegrante.getActividadEconomica());
        json_negocio.put(K_ANTIGUEDAD, negocioIntegrante.getAntiguedad());
        json_negocio.put(K_INGRESO_MENSUAL, negocioIntegrante.getIngMensual());
        json_negocio.put(K_INGRESOS_OTROS, negocioIntegrante.getIngOtros());
        json_negocio.put(K_GASTO_MENSUAL, negocioIntegrante.getGastoSemanal());
        json_negocio.put(K_MONTO_MAXIMO, negocioIntegrante.getCapacidadPago()); //SE INTERCAMBIA MONTO Y CAPACIDAD
        json_negocio.put(K_CAPACIDAD_PAGO, negocioIntegrante.getMontoMaximo());//SE INTERCAMBIA MONTO Y CAPACIDAD
        json_negocio.put(K_MEDIOS_PAGOS, medioPagoDao.findIdsByNombres(negocioIntegrante.getMediosPago()));
        if (negocioIntegrante.getMediosPago().contains("OTRO"))
            json_negocio.put(K_OTRO_MEDIOS_PAGOS, negocioIntegrante.getOtroMedioPago());
        json_negocio.put(K_NUM_OPERACIONES_MENSUAL, negocioIntegrante.getNumOpeMensuales());
        if (negocioIntegrante.getMediosPago().contains("EFECTIVO"))
            json_negocio.put(K_NUM_OPERACIONES_MENSUAL_EFECTIVO, negocioIntegrante.getNumOpeMensualesEfectivo());
        json_negocio.put(K_FOTO_FACHADA, negocioIntegrante.getFotoFachada());
        json_negocio.put(K_REFERENCIA_NEGOCIO, negocioIntegrante.getRefDomiciliaria());

        json_solicitud.put(K_SOLICITANTE_NEGOCIO, json_negocio);
    }

    private void FillDocumentosIntegrante(JSONObject json_solicitud, DocumentoIntegrante documentoIntegrante) throws JSONException {
        JSONObject json_documentos = new JSONObject();

        json_documentos.put(K_IDENTIFICACION_FRONTAL, documentoIntegrante.getIneFrontal());
        json_documentos.put(K_IDENTIFICACION_REVERSO, documentoIntegrante.getIneReverso());
        json_documentos.put(K_CURP, documentoIntegrante.getCurp());
        json_documentos.put(K_COMPROBANTE_DOMICILIO, documentoIntegrante.getComprobante());
        if (documentoIntegrante.getIneSelfie() != null)
            json_documentos.put(K_IDENTIFICACION_SELFIE, documentoIntegrante.getIneSelfie());
        else
            json_documentos.put(K_IDENTIFICACION_SELFIE, "");

        json_solicitud.put(K_SOLICITANTE_DOCUMENTOS, json_documentos);
    }

    private void FillPLDIntegrante(JSONObject json_solicitud, PoliticaPldIntegrante pldIntegrante) throws JSONException {
        JSONObject json_politicas = new JSONObject();
        json_politicas.put(K_PROPIETARIO, pldIntegrante.getPropietarioReal() == 1);
        json_politicas.put(K_PROVEEDOR_RECURSOS, pldIntegrante.getProveedorRecursos() == 1);
        json_politicas.put(K_POLITICAMENTE_EXP, pldIntegrante.getPersonaPolitica() == 1);
        json_solicitud.put(K_SOLICITANTE_POLITICAS, json_politicas);
    }

    private void FillCroquisIntegrante(JSONObject json_solicitud, CroquisIntegrante croquisIntegrante) throws JSONException {
        JSONObject json_croquis = new JSONObject();

        json_croquis.put(K_CALLE_ENFRENTE, croquisIntegrante.getCallePrincipal().trim().toUpperCase());
        json_croquis.put(K_CALLE_LATERAL_IZQ, croquisIntegrante.getLateralUno().trim().toUpperCase());
        json_croquis.put(K_CALLE_LATERAL_DER, croquisIntegrante.getLateralDos().trim().toUpperCase());
        json_croquis.put(K_CALLE_ATRAS, croquisIntegrante.getCalleTrasera().trim().toUpperCase());
        json_croquis.put(K_REFERENCIAS, croquisIntegrante.getReferencias().trim().toUpperCase());
        json_croquis.put(K_CARACTERISTICAS_DOMICILIO, croquisIntegrante.getCaracteristicasDomicilio().trim().toUpperCase());

        json_solicitud.put(K_SOLICITANTE_CROQUIS, json_croquis);
    }

    private void updateTitleView(String memberName, String groupName) {
        runInMainThread(() -> this.tvTitle.setText(String.format(TITLE_FORMAT, memberName, groupName)));
    }

    private void updateTitleViewError(String memberName, ResponseResultWrapper responseResultWrapper) {
        runInMainThread(() -> {
            this.tvTitle.setTextColor(Color.RED);
            this.tvTitle.setText(String.format(ERROR_TITLE_FORMAT, memberName));
            this.pbSending.setVisibility(View.INVISIBLE);
            this.closeDialogButton.setVisibility(View.VISIBLE);
            if (responseResultWrapper.kind == 2) {
                try {
                    SolicitudCreditoErrorMessage solicitudCreditoErrorMessage = Miscellaneous.jsonToObject(responseResultWrapper.message, SolicitudCreditoErrorMessage.class);
                    Spanned spanned = Html.fromHtml(String.format("<p>%s</p> <p>%s</p>", solicitudCreditoErrorMessage.getMensaje(), solicitudCreditoErrorMessage.getError()), Html.FROM_HTML_SEPARATOR_LINE_BREAK_DIV);
                    this.infoView.setText(spanned);
                } catch (Exception e) {
                    this.infoView.setText(responseResultWrapper.message);
                }
            } else {
                this.infoView.setText(responseResultWrapper.message);
            }
        });
    }

    private void updateTitleViewSuccess(String memberName) {
        runInMainThread(() -> this.tvTitle.setText(String.format(SUCCESS_TITLE_FORMAT, memberName)));
    }

    private void showInfoProgress(int currentProgress, int totalProgress) {
        runInMainThread(() -> this.infoView.setText(String.format(PROGRESS_SEND_MEMBERS_FORMAT, currentProgress, totalProgress)));
    }

    private static final class ResponseResultWrapper {
        String message;
        int code;
        int kind = 1;
    }


}
