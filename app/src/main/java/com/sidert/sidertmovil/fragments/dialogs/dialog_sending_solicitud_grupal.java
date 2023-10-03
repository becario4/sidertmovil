package com.sidert.sidertmovil.fragments.dialogs;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.database.DBhelper;
import com.sidert.sidertmovil.models.MResSaveSolicitud;
import com.sidert.sidertmovil.models.catalogos.MedioPagoDao;
import com.sidert.sidertmovil.models.datosCampañas.datosCampanaGpo;
import com.sidert.sidertmovil.models.datosCampañas.datosCampanaGpoRen;
import com.sidert.sidertmovil.models.dto.BeneficiarioDto;
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
import com.sidert.sidertmovil.services.beneficiario.BeneficiarioService;
import com.sidert.sidertmovil.services.solicitud.solicitudgpo.SolicitudGpoService;
import com.sidert.sidertmovil.utils.ManagerInterface;
import com.sidert.sidertmovil.utils.Miscellaneous;
import com.sidert.sidertmovil.utils.RetrofitClient;
import com.sidert.sidertmovil.utils.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

import static com.sidert.sidertmovil.utils.Constants.*;


public class dialog_sending_solicitud_grupal extends DialogFragment {
    private Context ctx;
    private SessionManager session;
    private ImageView ivClose;
    private TextView tvError;
    private TextView tvTitle;
    private TextView tvNombreIntegrante;
    private TextView tvTotalIntegrante;
    private TextView tvNumIntegrante;
    private ExecutorService executorService;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.popup_sending_solicitud_grupal, container, false);
        ctx = getContext();
        session = SessionManager.getInstance(ctx);
        ivClose = v.findViewById(R.id.ivClose);
        tvError = v.findViewById(R.id.tvError);
        tvTitle = v.findViewById(R.id.tvTitle);
        tvNombreIntegrante = v.findViewById(R.id.tvNombreIntegrante);
        tvTotalIntegrante = v.findViewById(R.id.tvTotalIntegrante);
        tvNumIntegrante = v.findViewById(R.id.tvNumIntegrante);
        executorService = Executors.newSingleThreadExecutor();

        Bundle args = getArguments();
        if (args != null) {
            long idSolicitud = getArguments().getLong(ID_SOLICITUD);
            boolean esRenovacion = getArguments().getBoolean(ES_RENOVACION);
            if (esRenovacion) {
                sendRenovacion(idSolicitud);
            } else {
                SendOriginacion(idSolicitud);
            }
        }

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ivClose.setOnClickListener((imageView) -> dismiss());
    }

    private void sendRenovacion(long idSolicitud) {
        SolicitudRenDao solicitudRenDao = new SolicitudRenDao(ctx);
        SolicitudRen solicitudRen = solicitudRenDao.findByIdSolicitud(Math.toIntExact(idSolicitud));

        if (solicitudRen != null) {
            CreditoGpoRenDao creditoGpoRenDao = new CreditoGpoRenDao(ctx);
            CreditoGpoRen creditoGpoRen = creditoGpoRenDao.findByIdSolicitud(solicitudRen.getIdSolicitud());
            tvTitle.setText("ENVIANDO SOLICITUD DE " + creditoGpoRen.getNombreGrupo());

            Integer creditoId = creditoGpoRen.getId();
            IntegranteGpoRenDao integranteGpoRenDao = new IntegranteGpoRenDao(ctx);
            List<IntegranteGpoRen> integranteGpoRenList = integranteGpoRenDao.findAllByIdCredito(creditoId);

            Integer totalMembers = integranteGpoRenList.size();

            if (totalMembers > 0) {
                tvTotalIntegrante.setText(String.valueOf(totalMembers));

                for (int i = 0; i < integranteGpoRenList.size(); i++) {
                    IntegranteGpoRen integranteGpoRen = integranteGpoRenList.get(i);
                    tvNumIntegrante.setText(String.valueOf(i + 1));
                    tvNombreIntegrante.setText(integranteGpoRen.getNombreCompleto());
                    if (integranteGpoRen.getEstatusCompletado() < 2) {
                        envioDeIntegrantesRenovacion(integranteGpoRen, totalMembers, creditoGpoRen, solicitudRen);
                    }
                }

                Integer totalMembersThatWereSuccessfullySubmitted = integranteGpoRenDao.countIntegrantesWihtStatusSuccessBycreditoId(creditoId);

                if (totalMembersThatWereSuccessfullySubmitted.equals(totalMembers)) {
                    solicitudRenDao.setCompletado(solicitudRen);
                    Toast.makeText(ctx, "¡Solicitud enviada!", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(ctx, "No todos los integrantes fueron enviados correctamente, porfavor trata de nuevo", Toast.LENGTH_LONG).show();
                }
                getActivity().finish();
            }
        }
    }


    private void envioDeIntegrantesRenovacion(@NonNull final IntegranteGpoRen integranteGpoRen, int totalIntegrantesRenovacion, CreditoGpoRen creditoGpoRen, SolicitudRen solicitudRen) {

        JSONObject json_solicitud = new JSONObject();
        try {
            Integer integranteId = integranteGpoRen.getId();
            Integer solicitudIntegranteId = integranteGpoRen.getIdSolicitudIntegrante();
            String estadoCivil = integranteGpoRen.getEstadoCivil();


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


            Future<Integer> futureResponse = executorService.submit(() -> {
                try {
                    Response<MResSaveSolicitud> response = call.execute();
                    int status = response.code();
                    if (status == 200) {
                        MResSaveSolicitud res = response.body();
                        IntegranteGpoRenDao integranteGpoRenDao = new IntegranteGpoRenDao(ctx);
                        integranteGpoRenDao.setCompletado(integranteGpoRen, res.getIdSolicitud());
                        integranteGpoRen.setEstatusCompletado(2);
                        enviarDatosBeneficiarioRenovacion(integranteGpoRen);
                        enviarDatosCampanaGpoRen(integranteGpoRen);
                        tvNombreIntegrante.setText("ENVIO COMPLETADO: " + integranteGpoRen.getNombreCompleto());
                    } else {
                        tvNombreIntegrante.setText("ERROR EN EL ENVIO DE: " + integranteGpoRen.getNombreCompleto());
                        try (ResponseBody errorBody = response.errorBody()) {
                            if (errorBody == null) {
                                showError("Error no identificado");
                            } else {
                                String errorMessage = errorBody.string();
                                showError(errorMessage);
                            }
                        } catch (IOException e) {
                            showError(e.getMessage());
                        }
                    }
                    return 200;
                } catch (IOException ioException) {
                    showError(ioException.getMessage());
                    return 500;
                }
            });

            int responseCodeResult = futureResponse.get(5, TimeUnit.MINUTES);
            Timber.tag(this.getClass().getName()).i("Codigo de error: %d", responseCodeResult);
        } catch (Exception e) {
            showError(e.getMessage());
        }
    }

    @SuppressLint("Range")
    public void enviarDatosBeneficiarioRenovacion(IntegranteGpoRen integrante) {
        DBhelper dBhelper = DBhelper.getInstance(ctx);
        BeneficiarioDto beneficiarioDto = null;

        Cursor row = dBhelper.getBeneficiarioRen(TBL_DATOS_BENEFICIARIO_GPO_REN, String.valueOf(integrante.getClienteId()));
        if (row != null && row.moveToFirst()) {
            String serieId = session.getUser().get(0);
            beneficiarioDto = new BeneficiarioDto(
                    row.getLong(row.getColumnIndex("id_originacion")),
                    row.getInt(row.getColumnIndex("id_cliente")),
                    row.getInt(row.getColumnIndex("id_grupo")),
                    row.getString(row.getColumnIndex("nombre")),
                    row.getString(row.getColumnIndex("paterno")),
                    row.getString(row.getColumnIndex("materno")),
                    row.getString(row.getColumnIndex("parentesco")),
                    serieId
            );
        } else {
            Toast.makeText(ctx, "NO SE ENCONTRARON DATOS", Toast.LENGTH_SHORT).show();
        }

        if (beneficiarioDto != null) {
            BeneficiarioService sa = RetrofitClient.generalRF(CONTROLLER_API, ctx).create(BeneficiarioService.class);
            Call<Map<String, Object>> call = sa.senDataBeneficiario(
                    "Bearer " + session.getUser().get(7),
                    beneficiarioDto
            );
            call.enqueue(new Callback<Map<String, Object>>() {
                @Override
                public void onResponse(Call<Map<String, Object>> call, Response<Map<String, Object>> response) {
                    if (response.code() == 200) {
                        Map<String, Object> res = response.body();
                        Timber.tag("AQUI:").e("REGISTRO COMPLETO");
                    }
                }

                @Override
                public void onFailure(Call<Map<String, Object>> call, Throwable t) {
                    Timber.tag("AQUI;").e(" NO SE REGISTRO NADA");
                }
            });
        }
    }

    public void enviarDatosCampanaGpoRen(IntegranteGpoRen integrante) {
        DBhelper dBhelper = DBhelper.getInstance(ctx);
        String queryWhere = " WHERE id_solicitud = ?";
        String[] params = {String.valueOf(integrante.getId())};

        try (Cursor row = dBhelper.getRecords(TBL_DATOS_CREDITO_CAMPANA_GPO_REN, queryWhere, " ", params)) {
            if (row != null && row.moveToFirst()) {
                datosCampanaGpoRen dato = new datosCampanaGpoRen();
                dato.fill(row);
                ManagerInterface api = RetrofitClient.generalRF(CONTROLLER_API, ctx).create(ManagerInterface.class);

                Call<datosCampanaGpoRen> call = api.saveCreditoCampanaGpoRen(
                        "Bearer " + session.getUser().get(7),
                        Long.valueOf(dato.getId_originacion()),
                        dato.getId_campana(),
                        dato.getTipo_campana(),
                        dato.getNombre_refiero()
                );

                Future<?> future = this.executorService.submit(() -> {
                    try {
                        Response<datosCampanaGpoRen> response = call.execute();
                        if (response.code() == 200) {
                            processCampaniaResponse(response);
                        } else {
                            Log.e("AQUI:", "NO SE REGISTRO LA CAMPAÑA");
                        }
                    } catch (IOException e) {
                        Log.e("AQUI:", "NO SE REGISTRO LA CAMPAÑA");
                    }
                });
                future.get(5, TimeUnit.MINUTES);
            } else {
                Toast.makeText(ctx, "NO SE ENCONTRARON DATOS", Toast.LENGTH_SHORT).show();
            }
        } catch (ExecutionException | InterruptedException | TimeoutException ignored) {
        }
    }

    private void processCampaniaResponse(Response<datosCampanaGpoRen> response) {
        try (okhttp3.Response res = response.raw()) {
            ResponseBody responseBody = res.body();
            Timber.tag(this.getClass().getName()).i("Service response: %s", responseBody.string());
            Log.e("AQUI:", "REGISTRO COMPLETO CREDITO CAMPAÑA");
        } catch (IOException ignored) {
        }
    }


    private void SendOriginacion(long idSolicitud) {
        SolicitudDao solicitudDao = new SolicitudDao(ctx);
        Solicitud solicitud = solicitudDao.findByIdSolicitud(Integer.valueOf(String.valueOf(idSolicitud)));

        if (solicitud != null) {
            CreditoGpoDao creditoGpoDao = new CreditoGpoDao(ctx);
            CreditoGpo creditoGpo = creditoGpoDao.findByIdSolicitud(solicitud.getIdSolicitud());
            tvTitle.setText("ENVIANDO SOLICITUD DE " + creditoGpo.getNombreGrupo());

            IntegranteGpoDao integranteGpoDao = new IntegranteGpoDao(ctx);
            List<IntegranteGpo> integranteGpoList = integranteGpoDao.findAllByIdCredito(creditoGpo.getId());
            int totalIntegrantes = integranteGpoList.size();
            if (totalIntegrantes > 0) {
                tvTotalIntegrante.setText(String.valueOf(totalIntegrantes));
                for (int count = 0; count < integranteGpoList.size(); count++) {
                    tvNumIntegrante.setText(String.valueOf(count + 1));
                    IntegranteGpo integranteGpo = integranteGpoList.get(count);
                    SendIntegrantePendiente(solicitud, integranteGpo, totalIntegrantes, creditoGpo);
                }
                int totalSuccess = integranteGpoDao.countIntegrantesWihtStatusSuccessBycreditoId(creditoGpo.getId());
                if (totalIntegrantes == totalSuccess) {
                    solicitudDao.setCompletado(solicitud);
                    Toast.makeText(ctx, "¡Solicitud enviada!", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(ctx, "No todos los integrantes fueron enviados correctamente, favor de trata de nuevo", Toast.LENGTH_LONG).show();
                }
                getActivity().finish();
            }
        }
    }

    private void SendIntegrantePendiente(final Solicitud solicitud, final IntegranteGpo integranteGpo, int totalIntegrantes, CreditoGpo creditoGpo) {

        tvNombreIntegrante.setText(integranteGpo.getNombreCompleto());
        Integer integranteId = integranteGpo.getId();
        String estadoCivil = integranteGpo.getEstadoCivil();
        Integer solicitudIntegranteId = integranteGpo.getIdSolicitudIntegrante();

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

            Future<Integer> future = this.executorService.submit(() -> {
                Response<MResSaveSolicitud> response = call.execute();
                int statusCode = response.code();
                if (statusCode == 200) {
                    SolicitudDao solicitudDao = new SolicitudDao(ctx);
                    MResSaveSolicitud res = response.body();

                    IntegranteGpoDao integranteGpoDao = new IntegranteGpoDao(ctx);
                    integranteGpoDao.setCompletado(integranteGpo, res.getIdSolicitud(), res.getId_grupo(), res.getId_cliente());

                    solicitudDao.updateIdCliente(res, solicitud);
                    solicitudDao.solicitudEnviadaGpo(solicitud);

                    integranteGpo.setEstatusCompletado(2);
                    enviarDatosCampanaGpo(integranteGpo);
                    enviarDatosBeneficiario(integranteGpo);
                    return 200;
                } else {
                    try (ResponseBody responseError = response.errorBody()) {
                        if (responseError != null) {
                            String err = responseError.string();
                            showError(err);
                        }
                    } catch (IOException e) {
                        showError(e.getMessage());
                    }
                    return 500;
                }
            });

            Integer responseCodeResult = future.get(5, TimeUnit.MINUTES);
            Timber.tag(this.getClass().getName()).i("Codigo de error: %d", responseCodeResult);

        } catch (Exception e) {
            showError(e.getMessage());
        }
    }

    @SuppressLint("Range")
    public void enviarDatosBeneficiario(IntegranteGpo integrante) {
        DBhelper dBhelper = DBhelper.getInstance(ctx);
        try (Cursor row = dBhelper.getBeneficiarioA(TBL_DATOS_BENEFICIARIO_GPO, String.valueOf(integrante.getIdSolicitudIntegrante()))) {
            if (row != null && row.moveToFirst()) {
                String serieId = session.getUser().get(0);
                BeneficiarioDto beneficiarioDto = new BeneficiarioDto(
                        row.getLong(row.getColumnIndex("id_originacion")),
                        row.getInt(row.getColumnIndex("id_cliente")),
                        row.getInt(row.getColumnIndex("id_grupo")),
                        row.getString(row.getColumnIndex("nombre")),
                        row.getString(row.getColumnIndex("paterno")),
                        row.getString(row.getColumnIndex("materno")),
                        row.getString(row.getColumnIndex("parentesco")),
                        serieId
                );

                BeneficiarioService sa = RetrofitClient.generalRF(CONTROLLER_API, ctx).create(BeneficiarioService.class);
                Call<Map<String, Object>> call = sa.senDataBeneficiario(
                        "Bearer " + session.getUser().get(7),
                        beneficiarioDto
                );
                call.enqueue(new Callback<Map<String, Object>>() {
                    @Override
                    public void onResponse(Call<Map<String, Object>> call, Response<Map<String, Object>> response) {
                        if (response.code() == 200) {
                            Map<String, Object> res = response.body();
                            Timber.tag("AQUI:").e("REGISTRO COMPLETO");
                        }
                    }

                    @Override
                    public void onFailure(Call<Map<String, Object>> call, Throwable t) {
                        Timber.tag("AQUI;").e(" NO SE REGISTRO NADA");
                    }
                });
            }
        }

        Toast.makeText(ctx, "NO SE ENCONTRARON DATOS", Toast.LENGTH_SHORT).show();
    }


    public void enviarDatosCampanaGpo(IntegranteGpo integrante) {
        DBhelper dBhelper = DBhelper.getInstance(ctx);
        String queryWhere = " WHERE id_solicitud = ?";
        String[] params = {String.valueOf(integrante.getId())};

        try (Cursor row = dBhelper.getRecords(TBL_DATOS_CREDITO_CAMPANA_GPO, queryWhere, " ", params)) {
            if (row != null && row.moveToFirst()) {
                datosCampanaGpo dato = new datosCampanaGpo();
                dato.fill(row);
                ManagerInterface api = RetrofitClient.generalRF(CONTROLLER_API, ctx).create(ManagerInterface.class);
                Call<datosCampanaGpo> call = api.saveCreditoCampanaGpo(
                        "Bearer " + session.getUser().get(7),
                        Long.valueOf(dato.getId_originacion()),
                        dato.getId_campana(),
                        dato.getTipo_campana(),
                        dato.getNombre_refiero()
                );

                Future<Integer> future = executorService.submit(() -> {
                    try {
                        Response<datosCampanaGpo> response = call.execute();
                        if (response.code() == 200) {
                            datosCampanaGpo res = response.body();
                            Log.e("AQUI:", "REGISTRO COMPLETO CREDITO CAMPAÑA");
                            return 200;

                        } else {
                            Log.e("AQUI:", "NO SE REGISTRO LA CAMPAÑA");
                        }
                    } catch (Exception e) {
                        Log.e("AQUI:", "NO SE REGISTRO LA CAMPAÑA");
                    }
                    return 500;
                });
                future.get(5, TimeUnit.MINUTES);
            } else {
                Toast.makeText(ctx, "NO SE ENCONTRARON DATOS", Toast.LENGTH_SHORT).show();
            }
        } catch (ExecutionException | InterruptedException | TimeoutException ignored) {
        }
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

    private void showError(String message) {
        Log.e("AQUI", message);

        int limit = message.length();

        if (limit > 1000) limit = 1000;

        tvError.setText(message.substring(0, limit));
        tvError.setVisibility(View.VISIBLE);
        ivClose.setVisibility(View.VISIBLE);
    }
}
