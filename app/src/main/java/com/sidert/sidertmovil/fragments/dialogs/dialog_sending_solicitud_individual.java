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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.database.DBhelper;
import com.sidert.sidertmovil.models.MResSaveSolicitud;
import com.sidert.sidertmovil.models.catalogos.MedioPagoDao;
import com.sidert.sidertmovil.models.datosCampañas.datoCampana;
import com.sidert.sidertmovil.models.dto.BeneficiarioDto;
import com.sidert.sidertmovil.models.solicitudes.Solicitud;
import com.sidert.sidertmovil.models.solicitudes.SolicitudDao;
import com.sidert.sidertmovil.models.solicitudes.SolicitudRen;
import com.sidert.sidertmovil.models.solicitudes.SolicitudRenDao;
import com.sidert.sidertmovil.models.solicitudes.solicitudind.originacion.Aval;
import com.sidert.sidertmovil.models.solicitudes.solicitudind.originacion.AvalDao;
import com.sidert.sidertmovil.models.solicitudes.solicitudind.originacion.Beneficiario;
import com.sidert.sidertmovil.models.solicitudes.solicitudind.originacion.Cliente;
import com.sidert.sidertmovil.models.solicitudes.solicitudind.originacion.ClienteDao;
import com.sidert.sidertmovil.models.solicitudes.solicitudind.originacion.Conyugue;
import com.sidert.sidertmovil.models.solicitudes.solicitudind.originacion.ConyugueDao;
import com.sidert.sidertmovil.models.solicitudes.solicitudind.originacion.CreditoInd;
import com.sidert.sidertmovil.models.solicitudes.solicitudind.originacion.CreditoIndDao;
import com.sidert.sidertmovil.models.solicitudes.solicitudind.originacion.Croquis;
import com.sidert.sidertmovil.models.solicitudes.solicitudind.originacion.CroquisDao;
import com.sidert.sidertmovil.models.solicitudes.solicitudind.originacion.Direccion;
import com.sidert.sidertmovil.models.solicitudes.solicitudind.originacion.DireccionDao;
import com.sidert.sidertmovil.models.solicitudes.solicitudind.originacion.Documento;
import com.sidert.sidertmovil.models.solicitudes.solicitudind.originacion.DocumentoDao;
import com.sidert.sidertmovil.models.solicitudes.solicitudind.originacion.Economico;
import com.sidert.sidertmovil.models.solicitudes.solicitudind.originacion.EconomicoDao;
import com.sidert.sidertmovil.models.solicitudes.solicitudind.originacion.Negocio;
import com.sidert.sidertmovil.models.solicitudes.solicitudind.originacion.NegocioDao;
import com.sidert.sidertmovil.models.solicitudes.solicitudind.originacion.PoliticaPld;
import com.sidert.sidertmovil.models.solicitudes.solicitudind.originacion.PoliticaPldDao;
import com.sidert.sidertmovil.models.solicitudes.solicitudind.originacion.Referencia;
import com.sidert.sidertmovil.models.solicitudes.solicitudind.originacion.ReferenciaDao;
import com.sidert.sidertmovil.models.solicitudes.solicitudind.renovacion.AvalRen;
import com.sidert.sidertmovil.models.solicitudes.solicitudind.renovacion.AvalRenDao;
import com.sidert.sidertmovil.models.solicitudes.solicitudind.renovacion.ClienteRen;
import com.sidert.sidertmovil.models.solicitudes.solicitudind.renovacion.ClienteRenDao;
import com.sidert.sidertmovil.models.solicitudes.solicitudind.renovacion.ConyugueRen;
import com.sidert.sidertmovil.models.solicitudes.solicitudind.renovacion.ConyugueRenDao;
import com.sidert.sidertmovil.models.solicitudes.solicitudind.renovacion.CreditoIndRen;
import com.sidert.sidertmovil.models.solicitudes.solicitudind.renovacion.CreditoIndRenDao;
import com.sidert.sidertmovil.models.solicitudes.solicitudind.renovacion.CroquisRen;
import com.sidert.sidertmovil.models.solicitudes.solicitudind.renovacion.CroquisRenDao;
import com.sidert.sidertmovil.models.solicitudes.solicitudind.renovacion.DireccionRen;
import com.sidert.sidertmovil.models.solicitudes.solicitudind.renovacion.DireccionRenDao;
import com.sidert.sidertmovil.models.solicitudes.solicitudind.renovacion.DocumentoRen;
import com.sidert.sidertmovil.models.solicitudes.solicitudind.renovacion.DocumentoRenDao;
import com.sidert.sidertmovil.models.solicitudes.solicitudind.renovacion.EconomicoRen;
import com.sidert.sidertmovil.models.solicitudes.solicitudind.renovacion.EconomicoRenDao;
import com.sidert.sidertmovil.models.solicitudes.solicitudind.renovacion.NegocioRen;
import com.sidert.sidertmovil.models.solicitudes.solicitudind.renovacion.NegocioRenDao;
import com.sidert.sidertmovil.models.solicitudes.solicitudind.renovacion.PoliticaPldRen;
import com.sidert.sidertmovil.models.solicitudes.solicitudind.renovacion.PoliticaPldRenDao;
import com.sidert.sidertmovil.models.solicitudes.solicitudind.renovacion.ReferenciaRen;
import com.sidert.sidertmovil.models.solicitudes.solicitudind.renovacion.ReferenciaRenDao;
import com.sidert.sidertmovil.services.beneficiario.BeneficiarioService;
import com.sidert.sidertmovil.services.solicitud.solicitudind.SolicitudIndService;
import com.sidert.sidertmovil.utils.ManagerInterface;
import com.sidert.sidertmovil.utils.Miscellaneous;
import com.sidert.sidertmovil.utils.RetrofitClient;
import com.sidert.sidertmovil.utils.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Map;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

import static com.sidert.sidertmovil.utils.Constants.*;

public class dialog_sending_solicitud_individual extends DialogFragment {
    private Context ctx;

    ImageView ivClose;
    ProgressBar pbSending;
    TextView tvCliente;
    TextView tvError;
    TextView tvTitle;
    SessionManager session;
    Beneficiario beneficiario;
    private int var_temp = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.popup_sending_solicitud_individual, container, false);

        ctx = getContext();
        session = SessionManager.getInstance(ctx);

        ivClose = v.findViewById(R.id.ivClose);
        pbSending = v.findViewById(R.id.pbSending);
        tvCliente = v.findViewById(R.id.tvCliente);
        tvError = v.findViewById(R.id.tvError);
        tvTitle = v.findViewById(R.id.tvTitle);

        Long idSolicitud = getArguments().getLong(ID_SOLICITUD);
        boolean esRenovacion = getArguments().getBoolean(ES_RENOVACION);

        if (esRenovacion) {
            SendRenovacion(idSolicitud);
        } else {
            SendOriginacion(idSolicitud);

        }

        return v;
    }

    private void SendRenovacion(long idSolicitud) {
        SolicitudRenDao solicitudRenDao = new SolicitudRenDao(ctx);
        SolicitudRen solicitudRen = solicitudRenDao.findByIdSolicitud(Integer.parseInt(String.valueOf(idSolicitud)));

        if (solicitudRen != null) {
            tvCliente.setText(solicitudRen.getNombre());

            try {
                CreditoIndRenDao creditoIndRenDao = new CreditoIndRenDao(ctx);
                CreditoIndRen creditoIndRen = creditoIndRenDao.findByIdSolicitud(solicitudRen.getIdSolicitud());

                ClienteRenDao clienteRenDao = new ClienteRenDao(ctx);
                ClienteRen clienteRen = clienteRenDao.findByIdSolicitud(solicitudRen.getIdSolicitud());

                ConyugueRenDao conyugueRenDao = new ConyugueRenDao(ctx);
                ConyugueRen conyugueRen = conyugueRenDao.findByIdSolicitud(solicitudRen.getIdSolicitud());

                AvalRenDao avalRenDao = new AvalRenDao(ctx);
                AvalRen avalRen = avalRenDao.findByIdSolicitud(solicitudRen.getIdSolicitud());

                DocumentoRenDao documentoRenDao = new DocumentoRenDao(ctx);
                DocumentoRen documentoRen = documentoRenDao.findByIdSolicitud(solicitudRen.getIdSolicitud());

                EconomicoRenDao economicoRenDao = new EconomicoRenDao(ctx);
                EconomicoRen economicoRen = economicoRenDao.findByIdSolicitud(solicitudRen.getIdSolicitud());

                NegocioRenDao negocioRenDao = new NegocioRenDao(ctx);
                NegocioRen negocioRen = negocioRenDao.findByIdSolicitud(solicitudRen.getIdSolicitud());

                PoliticaPldRenDao politicaPldRenDao = new PoliticaPldRenDao(ctx);
                PoliticaPldRen politicaPldRen = politicaPldRenDao.findByIdSolicitud(solicitudRen.getIdSolicitud());

                ReferenciaRenDao referenciaRenDao = new ReferenciaRenDao(ctx);
                ReferenciaRen referenciaRen = referenciaRenDao.findByIdSolicitud(solicitudRen.getIdSolicitud());

                CroquisRenDao croquisRenDao = new CroquisRenDao(ctx);
                CroquisRen croquisRen = croquisRenDao.findByIdSolicitud(solicitudRen.getIdSolicitud());

                JSONObject json_solicitud = new JSONObject();

                fillSolicitudRenJson(json_solicitud, solicitudRen);
                fillCreditoRenJson(json_solicitud, creditoIndRen);
                fillClienteRenJson(json_solicitud, clienteRen, creditoIndRen);
                if ((clienteRen.getEstadoCivil().equals("CASADO(A)") || clienteRen.getEstadoCivil().equals("UNION LIBRE")) && conyugueRen != null)
                    fillConyugueRenJson(json_solicitud, conyugueRen);
                if (Integer.parseInt(creditoIndRen.getMontoPrestamo().replace(",", "")) >= 30000)
                    fillEconomicoRenJson(json_solicitud, economicoRen);
                fillNegocioRenJson(json_solicitud, negocioRen);
                fillAvalRenJson(json_solicitud, avalRen);
                fillReferenciaRenJson(json_solicitud, referenciaRen);
                fillCroquisRenJson(json_solicitud, croquisRen);
                fillDocumentoRenJson(json_solicitud, documentoRen);
                fillPoliticaPldRenJson(json_solicitud, politicaPldRen);

                MultipartBody.Part fachada_cliente = clienteRen.getFachadaMBPart();
                MultipartBody.Part firma_cliente = clienteRen.getFirmaMBPart();
                MultipartBody.Part fachada_negocio = negocioRen.getFachadaMBPart();
                MultipartBody.Part fachada_aval = avalRen.getFachadaMBPart();
                MultipartBody.Part firma_aval = avalRen.getFirmaMBPart();
                MultipartBody.Part foto_ine_frontal = documentoRen.getFotoIneFrontalMBPart();
                MultipartBody.Part foto_ine_reverso = documentoRen.getFotoIneReversoMBPart();
                MultipartBody.Part ine_selfie = documentoRen.getIneSelfieMBPart();
                MultipartBody.Part foto_curp = documentoRen.getCurpMBPart();
                MultipartBody.Part comprobante = documentoRen.getComprobanteMBPart();
                MultipartBody.Part firma_asesor = documentoRen.getFirmaAsesorMBPart();
                MultipartBody.Part comprobante_garantia = documentoRen.getComprobanteGarantiaMBPart();
                MultipartBody.Part ine_frontal_aval = documentoRen.getIneFrontalAvalMBPart();
                MultipartBody.Part ine_reverso_aval = documentoRen.getIneReversoAvalMBPart();
                MultipartBody.Part curp_aval = documentoRen.getCurpAvalMBPart();
                MultipartBody.Part comprobante_aval = documentoRen.getComprobanteDomicilioAvalMBPart();
                RequestBody solicitudBody = RequestBody.create(json_solicitud.toString(), MultipartBody.FORM);
                RequestBody solicitudIdBody = RequestBody.create(String.valueOf(solicitudRen.getIdOriginacion()), MultipartBody.FORM);

                /*Log.e("fachada_cliente", fachada_cliente.body().toString());
                Log.e("firma_cliente", firma_cliente.body().toString());
                Log.e("fachada_negocio", fachada_negocio.body().toString());
                Log.e("fachada_aval", (fachada_aval != null) ? fachada_aval.body().toString() : "vacio");
                Log.e("firma_aval", firma_aval.body().toString());
                Log.e("foto_ine_frontal", foto_ine_frontal.body().toString());
                Log.e("foto_ine_reverso", foto_ine_reverso.body().toString());
                Log.e("ine_selfie", ine_selfie.body().toString());
                Log.e("foto_curp", foto_curp.body().toString());
                Log.e("comprobante", comprobante.body().toString());
                Log.e("firma_asesor", firma_asesor.body().toString());
                Log.e("comprobante_garantia", comprobante_garantia.body().toString());
                Log.e("ine_frontal_aval", ine_frontal_aval.body().toString());
                Log.e("ine_reverso_aval", ine_reverso_aval.body().toString());
                Log.e("curp_aval", curp_aval.body().toString());
                Log.e("comprobante_aval", comprobante_aval.body().toString());
                */
                /*
                Log.e("fachada_cliente", clienteRen.getFotoFachada());
                Log.e("firma_cliente", clienteRen.getFirma());
                Log.e("fachada_negocio", negocioRen.getFotoFachada());
                Log.e("fachada_aval", (avalRen.getFotoFachada() != null) ? avalRen.getFotoFachada() : "vacio");
                Log.e("firma_aval", avalRen.getFirma());
                Log.e("foto_ine_frontal", documentoRen.getIneFrontal());
                Log.e("foto_ine_reverso", documentoRen.getIneReverso());
                Log.e("ine_selfie", documentoRen.getIneSelfie());
                Log.e("foto_curp", documentoRen.getCurp());
                Log.e("comprobante", documentoRen.getComprobante());
                Log.e("firma_asesor", documentoRen.getFirmaAsesor());
                Log.e("comprobante_garantia", documentoRen.getComprobanteGarantia());
                Log.e("ine_frontal_aval", documentoRen.getIneFrontalAval());
                Log.e("ine_reverso_aval", documentoRen.getIneReversoAval());
                Log.e("curp_aval", documentoRen.getCurpAval());
                Log.e("comprobante_aval", documentoRen.getComprobanteAval());*/

                SolicitudIndService solicitudIndService = RetrofitClient.newInstance(ctx).create(SolicitudIndService.class);
                Call<MResSaveSolicitud> call = solicitudIndService.saveRenovacion("Bearer " + session.getUser().get(7),
                        solicitudBody,
                        fachada_cliente,
                        firma_cliente,
                        fachada_negocio,
                        fachada_aval,
                        firma_aval,
                        foto_ine_frontal,
                        foto_ine_reverso,
                        foto_curp,
                        comprobante,
                        firma_asesor,
                        solicitudIdBody,
                        ine_selfie,
                        comprobante_garantia,
                        ine_frontal_aval,
                        ine_reverso_aval,
                        curp_aval,
                        comprobante_aval
                );

                call.enqueue(new Callback<MResSaveSolicitud>() {
                    @Override
                    public void onResponse(Call<MResSaveSolicitud> call, Response<MResSaveSolicitud> response) {
                        switch (response.code()) {
                            case 200:
                                MResSaveSolicitud res = response.body();

                                solicitudRen.setEstatus(2);
                                solicitudRen.setIdOriginacion(res.getIdSolicitud());
                                solicitudRenDao.updateIdClienteRenInd(res, solicitudRen);
                                solicitudRenDao.solicitudEnviada(solicitudRen);
                                enviarDatosCampana(solicitudRen.getIdSolicitud());
                                obtenerDatosBeneficiarioRen(solicitudRen.getIdSolicitud());
                                Toast.makeText(ctx, "¡Solicitud enviada!", Toast.LENGTH_LONG).show();
                                getActivity().finish();
                                break;
                            default:
                                try {
                                    Log.e("AQUI", "DEFAULT");
                                    showError(response.errorBody().string());
                                } catch (IOException e) {
                                    Log.e("AQUI", "DEFAULT catch");
                                    showError(e.getMessage());
                                }
                                break;
                        }
                    }

                    @Override
                    public void onFailure(Call<MResSaveSolicitud> call, Throwable t) {
                        Log.e("AQUI", "FAILURE");
                        showError(t.getMessage());
                    }

                });
            } catch (Exception e) {
                Log.e("AQUI", "exception");
                showError(e.getMessage());
            }
        }
    }

    private void SendOriginacion(long idSolicitud) {

        SolicitudDao solicitudDao = new SolicitudDao(ctx);
        Solicitud solicitud = solicitudDao.findByIdSolicitud(Integer.parseInt(String.valueOf(idSolicitud)));

        if (solicitud != null) {
            tvCliente.setText(solicitud.getNombre());

            try {
                CreditoIndDao creditoIndDao = new CreditoIndDao(ctx);
                CreditoInd creditoInd = creditoIndDao.findByIdSolicitud(solicitud.getIdSolicitud());

                ClienteDao clienteDao = new ClienteDao(ctx);
                Cliente cliente = clienteDao.findByIdSolicitud(solicitud.getIdSolicitud());

                ConyugueDao conyugueDao = new ConyugueDao(ctx);
                Conyugue conyugue = conyugueDao.findByIdSolicitud(solicitud.getIdSolicitud());

                AvalDao avalDao = new AvalDao(ctx);
                Aval aval = avalDao.findByIdSolicitud(solicitud.getIdSolicitud());

                DocumentoDao documentoDao = new DocumentoDao(ctx);
                Documento documento = documentoDao.findByIdSolicitud(solicitud.getIdSolicitud());

                EconomicoDao economicoDao = new EconomicoDao(ctx);
                Economico economico = economicoDao.findByIdSolicitud(solicitud.getIdSolicitud());

                NegocioDao negocioDao = new NegocioDao(ctx);
                Negocio negocio = negocioDao.findByIdSolicitud(solicitud.getIdSolicitud());

                PoliticaPldDao politicaPldDao = new PoliticaPldDao(ctx);
                PoliticaPld politicaPld = politicaPldDao.findByIdSolicitud(solicitud.getIdSolicitud());

                ReferenciaDao referenciaDao = new ReferenciaDao(ctx);
                Referencia referencia = referenciaDao.findByIdSolicitud(solicitud.getIdSolicitud());

                CroquisDao croquisDao = new CroquisDao(ctx);
                Croquis croquis = croquisDao.findByIdSolicitud(solicitud.getIdSolicitud());

                JSONObject json_solicitud = new JSONObject();

                fillSolicitudJson(json_solicitud, solicitud);
                fillCreditoJson(json_solicitud, creditoInd);
                fillClienteJson(json_solicitud, cliente);
                if ((cliente.getEstadoCivil().equals("CASADO(A)") || cliente.getEstadoCivil().equals("UNION LIBRE")) && conyugue != null)
                    fillConyugueJson(json_solicitud, conyugue);
                if (Integer.parseInt(creditoInd.getMontoPrestamo().replace(",", "")) >= 30000)
                    fillEconomicoJson(json_solicitud, economico);
                fillNegocioJson(json_solicitud, negocio);
                fillAvalJson(json_solicitud, aval);
                fillReferenciaJson(json_solicitud, referencia);
                //fillBeneficiario(json_solicitud,beneficiario);
                fillCroquisJson(json_solicitud, croquis);
                fillDocumentoJson(json_solicitud, documento);
                fillPoliticaPldJson(json_solicitud, politicaPld);

                MultipartBody.Part fachada_cliente = cliente.getFachadaMBPart();
                MultipartBody.Part firma_cliente = cliente.getFirmaMBPart();
                MultipartBody.Part fachada_negocio = negocio.getFachadaMBPart();
                MultipartBody.Part fachada_aval = aval.getFachadaMBPart();
                MultipartBody.Part firma_aval = aval.getFirmaMBPart();
                MultipartBody.Part foto_ine_frontal = documento.getFotoIneFrontalMBPart();
                MultipartBody.Part foto_ine_reverso = documento.getFotoIneReversoMBPart();
                MultipartBody.Part ine_selfie = documento.getIneSelfieMBPart();
                MultipartBody.Part foto_curp = documento.getCurpMBPart();
                MultipartBody.Part comprobante = documento.getComprobanteMBPart();
                MultipartBody.Part firma_asesor = documento.getFirmaAsesorMBPart();
                MultipartBody.Part comprobante_garantia = documento.getComprobanteGarantiaMBPart();
                MultipartBody.Part ine_frontal_aval = documento.getIneFrontalAvalMBPart();
                MultipartBody.Part ine_reverso_aval = documento.getIneReversoAvalMBPart();
                MultipartBody.Part curp_aval = documento.getCurpAvalMBPart();
                MultipartBody.Part comprobante_aval = documento.getComprobanteDomicilioAvalMBPart();

                RequestBody solicitudBody = RequestBody.create(MultipartBody.FORM, json_solicitud.toString());
                RequestBody solicitudIdBody = RequestBody.create(MultipartBody.FORM, String.valueOf(solicitud.getIdOriginacion()));
                /** aqui obtenemos el id_originacion*/
                SolicitudIndService solicitudIndService = RetrofitClient.newInstance(ctx).create(SolicitudIndService.class);
                Call<MResSaveSolicitud> call = solicitudIndService.saveOriginacion("Bearer " + session.getUser().get(7),
                        solicitudBody,
                        fachada_cliente,
                        firma_cliente,
                        fachada_negocio,
                        fachada_aval,
                        firma_aval,
                        foto_ine_frontal,
                        foto_ine_reverso,
                        foto_curp,
                        comprobante,
                        firma_asesor,
                        solicitudIdBody,
                        ine_selfie,
                        comprobante_garantia,
                        ine_frontal_aval,
                        ine_reverso_aval,
                        curp_aval,
                        comprobante_aval
                );

                call.enqueue(new Callback<MResSaveSolicitud>() {
                    @Override
                    public void onResponse(Call<MResSaveSolicitud> call, Response<MResSaveSolicitud> response) {
                        switch (response.code()) {
                            case 200:
                                MResSaveSolicitud res = response.body();
                                solicitud.setEstatus(2);
                                solicitud.setIdOriginacion(res.getIdSolicitud());
                                solicitudDao.solicitudEnviada(solicitud, res);
                                enviarDatosCampana(solicitud.getIdSolicitud());
                                obtenerDatosBeneficiario(solicitud.getIdSolicitud());

                                Toast.makeText(ctx, "¡Solicitud enviada!", Toast.LENGTH_LONG).show();
                                getActivity().finish();
                                break;
                            default:
                                try {
                                    showError(response.errorBody().string());
                                } catch (IOException e) {
                                    showError(e.getMessage());
                                }
                                break;
                        }
                    }

                    @Override
                    public void onFailure(Call<MResSaveSolicitud> call, Throwable t) {
                        showError(t.getMessage());
                    }

                });
            } catch (Exception e) {
                showError(e.getMessage());
            }
        }
    }

    private void showError(String message) {
        Log.e("AQUI", message);

        int limit = message.length();

        if (limit > 1000) limit = 1000;

        tvError.setText(message.substring(0, limit));
        tvError.setVisibility(View.VISIBLE);
        ivClose.setVisibility(View.VISIBLE);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ivClose.setOnClickListener(ivClose_OnClick);
    }

    private View.OnClickListener ivClose_OnClick = view -> {
        dismiss();
    };

    private void fillSolicitudJson(JSONObject json_solicitud, Solicitud solicitud) throws JSONException {
        json_solicitud.put(K_FECHA_INICIO, solicitud.getFechaInicio());
        json_solicitud.put(K_FECHA_TERMINO, solicitud.getFechaTermino());
        json_solicitud.put(K_FECHA_ENVIO, Miscellaneous.ObtenerFecha(TIMESTAMP));
    }

    private void fillCreditoJson(JSONObject json_solicitud, CreditoInd creditoInd) throws JSONException {
        json_solicitud.put(K_PLAZO, creditoInd.getPlazoAsInt());
        json_solicitud.put(K_PERIODICIDAD, creditoInd.getPeriodicidadAsInt());
        json_solicitud.put(K_FECHA_DESEMBOLSO, creditoInd.getFechaDesembolso());
        json_solicitud.put(K_HORA_VISITA, creditoInd.getHoraVisita());
        json_solicitud.put(K_MONTO_PRESTAMO, Integer.parseInt(creditoInd.getMontoPrestamo().replace(",", "")));
        json_solicitud.put(K_MONTO_LETRA, (Miscellaneous.cantidadLetra(creditoInd.getMontoPrestamo().replace(",", "")).toUpperCase() + " PESOS MEXICANOS").replace("  ", " "));
        json_solicitud.put(K_DESTINO_PRESTAMO, creditoInd.getDestino());
        json_solicitud.put(K_CLASIFICACION_RIESGO, creditoInd.getClasificacionRiesgo());
        json_solicitud.put(K_TIPO_SOLICITUD, "ORIGINACION");
        json_solicitud.put(K_ID_CAMPANA, creditoInd.getId_campana());
        if (creditoInd.getMontoRefinanciar() != null && !creditoInd.getMontoRefinanciar().isEmpty())
            Integer.parseInt(creditoInd.getMontoRefinanciar().replace(",", ""));
        else
            json_solicitud.put(K_MONTO_REFINANCIAR, 0);
    }

    private void fillClienteJson(JSONObject json_solicitud, Cliente cliente) throws JSONException {
        DireccionDao direccionDao = new DireccionDao(ctx);
        Direccion direccionCliente = direccionDao.findByIdDireccion(Long.parseLong(cliente.getDireccionId()));

        JSONObject json_solicitante = new JSONObject();

        json_solicitante.put(K_NOMBRE, cliente.getNombre());
        json_solicitante.put(K_PATERNO, cliente.getPaterno());
        json_solicitante.put(K_MATERNO, cliente.getMaterno());
        json_solicitante.put(K_FECHA_NACIMIENTO, cliente.getFechaNacimiento());
        json_solicitante.put(K_EDAD, cliente.getEdad());
        json_solicitante.put(K_GENERO, cliente.getGenero());
        json_solicitante.put(K_ESTADO_NACIMIENTO, cliente.getEstadoNacimiento());
        json_solicitante.put(K_RFC, cliente.getRfc());
        json_solicitante.put(K_CURP, cliente.getCurp() + cliente.getCurpDigitoVeri());
        json_solicitante.put(K_OCUPACION, cliente.getOcupacion());
        json_solicitante.put(K_ACTIVIDAD_ECONOMICA, cliente.getActividadEconomica());
        json_solicitante.put(K_IDENTIFICACION_TIPO, cliente.getTipoIdentificacion());
        json_solicitante.put(K_NO_IDENTIFICACION, cliente.getNoIdentificacion());
        json_solicitante.put(K_NIVEL_ESTUDIO, cliente.getNivelEstudio());

        json_solicitante.put(K_ESTADO_CIVIL, cliente.getEstadoCivil());

        if (cliente.getEstadoCivil().equals("CASADO(A)"))
            json_solicitante.put(K_BIENES, (cliente.getBienes() == 1) ? "MANCOMUNADOS" : "SEPARADOS");

        json_solicitante.put(K_TIPO_VIVIENDA, cliente.getTipoVivienda());

        if (cliente.getTipoVivienda().equals("CASA FAMILIAR"))
            json_solicitante.put(K_PARENTESCO, cliente.getParentesco());
        else if (cliente.getTipoVivienda().equals("OTRO"))
            json_solicitante.put(K_OTRO_TIPO_VIVIENDA, cliente.getOtroTipoVivienda());

        json_solicitante.put(K_LATITUD, direccionCliente.getLatitud());
        json_solicitante.put(K_LONGITUD, direccionCliente.getLongitud());
        json_solicitante.put(K_CALLE, direccionCliente.getCalle());
        json_solicitante.put(K_NO_EXTERIOR, direccionCliente.getNumExterior());
        json_solicitante.put(K_NO_INTERIOR, direccionCliente.getNumInterior());
        json_solicitante.put(K_NO_LOTE, direccionCliente.getLote());
        json_solicitante.put(K_NO_MANZANA, direccionCliente.getManzana());
        json_solicitante.put(K_CODIGO_POSTAL, direccionCliente.getCp());
        json_solicitante.put(K_COLONIA, direccionCliente.getColonia());
        json_solicitante.put(K_CIUDAD, direccionCliente.getCiudad());
        json_solicitante.put(K_LOCALIDAD, direccionCliente.getLocalidad());
        json_solicitante.put(K_MUNICIPIO, direccionCliente.getMunicipio());
        json_solicitante.put(K_ESTADO, direccionCliente.getEstado());
        json_solicitante.put(K_LOCATED_AT, direccionCliente.getLocatedAt());

        json_solicitante.put(K_TEL_CASA, cliente.getTelCasa());
        json_solicitante.put(K_TEL_CELULAR, cliente.getTelCelular());
        json_solicitante.put(K_TEL_MENSAJE, cliente.getTelMensajes());
        json_solicitante.put(K_TEL_TRABAJO, cliente.getTelTrabajo());
        json_solicitante.put(K_TIEMPO_VIVIR_SITIO, cliente.getTiempoVivirSitio());
        json_solicitante.put(K_DEPENDIENTES_ECONOMICO, cliente.getDependientes());
        json_solicitante.put(K_MEDIO_CONTACTO, cliente.getMedioContacto());
        json_solicitante.put(K_ESTADO_CUENTA, cliente.getEstadoCuenta());
        json_solicitante.put(K_EMAIL, cliente.getEmail());
        json_solicitante.put(K_FOTO_FACHADA, cliente.getFotoFachada());
        json_solicitante.put(K_REFERENCIA_DOMICILIARIA, cliente.getRefDomiciliaria());
        json_solicitante.put(K_FIRMA, cliente.getFirma());
        json_solicitante.put(K_SOL_LATITUD, cliente.getLatitud());
        json_solicitante.put(K_SOL_LONGITUD, cliente.getLongitud());
        json_solicitante.put(K_SOL_LOCATED_AT, cliente.getLocatedAt());
        json_solicitante.put(K_TIENE_FIRMA, "SI");
        json_solicitante.put(K_NOMBRE_QUIEN_FIRMA, "");
        json_solicitud.put(K_SOLICITANTE, json_solicitante);
    }

    private void fillConyugueJson(JSONObject json_solicitud, Conyugue conyugue) throws JSONException {
        JSONObject json_conyuge = new JSONObject();

        DireccionDao direccionConyugueDao = new DireccionDao(ctx);
        Direccion direccionConyugue = direccionConyugueDao.findByIdDireccion(Long.parseLong(conyugue.getDireccionId()));

        json_conyuge.put(K_NOMBRE, conyugue.getNombre());
        json_conyuge.put(K_PATERNO, conyugue.getPaterno());
        json_conyuge.put(K_MATERNO, conyugue.getMaterno());
        json_conyuge.put(K_NACIONALIDAD, conyugue.getNacionalidad());
        json_conyuge.put(K_OCUPACION, conyugue.getOcupacion());

        json_conyuge.put(K_CALLE, direccionConyugue.getCalle());
        json_conyuge.put(K_NO_EXTERIOR, direccionConyugue.getNumExterior());
        json_conyuge.put(K_NO_INTERIOR, direccionConyugue.getNumInterior());
        json_conyuge.put(K_NO_LOTE, direccionConyugue.getLote());
        json_conyuge.put(K_NO_MANZANA, direccionConyugue.getManzana());
        json_conyuge.put(K_CODIGO_POSTAL, direccionConyugue.getCp());
        json_conyuge.put(K_COLONIA, direccionConyugue.getColonia());
        json_conyuge.put(K_CIUDAD, direccionConyugue.getCiudad());
        json_conyuge.put(K_LOCALIDAD, direccionConyugue.getLocalidad());
        json_conyuge.put(K_MUNICIPIO, direccionConyugue.getMunicipio());
        json_conyuge.put(K_ESTADO, direccionConyugue.getEstado());

        json_conyuge.put(K_INGRESO_MENSUAL, Double.parseDouble(conyugue.getIngMensual().replace(",", "")));
        json_conyuge.put(K_GASTO_MENSUAL, Double.parseDouble(conyugue.getGastoMensual().replace(",", "")));
        json_conyuge.put(K_TEL_CASA, conyugue.getTelCasa());
        json_conyuge.put(K_TEL_CELULAR, conyugue.getTelCelular());

        json_solicitud.put(K_SOLICITANTE_CONYUGE, json_conyuge);
    }

    private void fillEconomicoJson(JSONObject json_solicitud, Economico economico) throws JSONException {
        JSONObject json_economicos = new JSONObject();

        json_economicos.put(K_PROPIEDADES, economico.getPropiedades());
        json_economicos.put(K_VALOR_APROXIMADO, economico.getValorAproximado());
        json_economicos.put(K_UBICACION, economico.getUbicacion());
        json_economicos.put(K_INGRESO, economico.getIngreso().replace(",", ""));

        json_solicitud.put(K_SOLICITANTE_DATOS_ECONOMICOS, json_economicos);
    }

    private void fillNegocioJson(JSONObject json_solicitud, Negocio negocio) throws JSONException {
        MedioPagoDao medioPagoDao = new MedioPagoDao(ctx);
        DireccionDao direccionNegocioDao = new DireccionDao(ctx);
        Direccion direccionNegocio = direccionNegocioDao.findByIdDireccion(Long.parseLong(negocio.getDireccionId()));

        JSONObject json_negocio = new JSONObject();

        json_negocio.put(K_NOMBRE, negocio.getNombre());
        json_negocio.put(K_LATITUD, direccionNegocio.getLatitud());
        json_negocio.put(K_LONGITUD, direccionNegocio.getLatitud());
        json_negocio.put(K_CALLE, direccionNegocio.getCalle());
        json_negocio.put(K_NO_EXTERIOR, direccionNegocio.getNumExterior());
        json_negocio.put(K_NO_INTERIOR, direccionNegocio.getNumInterior());
        json_negocio.put(K_NO_LOTE, direccionNegocio.getLote());
        json_negocio.put(K_NO_MANZANA, direccionNegocio.getManzana());
        json_negocio.put(K_CODIGO_POSTAL, direccionNegocio.getCp());
        json_negocio.put(K_COLONIA, direccionNegocio.getColonia());
        json_negocio.put(K_CIUDAD, direccionNegocio.getCiudad());
        json_negocio.put(K_LOCALIDAD, direccionNegocio.getLocalidad());
        json_negocio.put(K_MUNICIPIO, direccionNegocio.getMunicipio());
        json_negocio.put(K_ESTADO, direccionNegocio.getEstado());
        json_negocio.put(K_LOCATED_AT, direccionNegocio.getLocatedAt());
        json_negocio.put(K_OCUPACION, negocio.getOcupacion());
        json_negocio.put(K_ACTIVIDAD_ECONOMICA, negocio.getActividadEconomica());
        json_negocio.put(K_DESTINO_CREDITO, negocio.getDestinoCredito());
        if (negocio.getDestinoCredito().contains("OTRO"))
            json_negocio.put(K_OTRO_DESTINO_CREDITO, negocio.getOtroDestino());
        json_negocio.put(K_ANTIGUEDAD, negocio.getAntiguedad());
        json_negocio.put(K_INGRESO_MENSUAL, negocio.getIngMensual().replace(",", ""));
        json_negocio.put(K_INGRESOS_OTROS, negocio.getIngOtros().replace(",", ""));
        json_negocio.put(K_GASTO_MENSUAL, negocio.getGastoSemanal().replace(",", ""));
        json_negocio.put(K_GASTO_AGUA, negocio.getGastoAgua().replace(",", ""));
        json_negocio.put(K_GASTO_LUZ, negocio.getGastoLuz().replace(",", ""));
        json_negocio.put(K_GASTO_TELEFONO, negocio.getGastoTelefono().replace(",", ""));
        json_negocio.put(K_GASTO_RENTA, negocio.getGastoRenta().replace(",", ""));
        json_negocio.put(K_GASTO_OTROS, negocio.getGastoOtros().replace(",", ""));
        json_negocio.put(K_CAPACIDAD_PAGO, negocio.getMontoMaximo());
        if (negocio.getMedioPago().contains("OTRO"))
            json_negocio.put(K_OTRO_MEDIOS_PAGOS, negocio.getOtroMedioPago());
        Log.e("AQUI MEDIOS PAGO", negocio.getMedioPago());
        Log.e("AQUI MEDIOS PAGO", medioPagoDao.findIdsByNombres(negocio.getMedioPago()));
        json_negocio.put(K_MEDIOS_PAGOS, medioPagoDao.findIdsByNombres(negocio.getMedioPago()));
        json_negocio.put(K_MONTO_MAXIMO, negocio.getCapacidadPago());
        json_negocio.put(K_NUM_OPERACIONES_MENSUAL, negocio.getNumOperacionMensuales());
        if (negocio.getMedioPago().contains("EFECTIVO"))
            json_negocio.put(K_NUM_OPERACIONES_MENSUAL_EFECTIVO, negocio.getNumOperacionEfectivo());
        json_negocio.put(K_DIAS_VENTA, negocio.getDiasVenta());
        json_negocio.put(K_FOTO_FACHADA, negocio.getFotoFachada());
        json_negocio.put(K_REFERENCIA_NEGOCIO, negocio.getRefDomiciliaria());

        json_solicitud.put(K_SOLICITANTE_NEGOCIO, json_negocio);
    }

    private void fillAvalJson(JSONObject json_solicitud, Aval aval) throws JSONException {
        JSONObject json_aval = new JSONObject();

        MedioPagoDao medioPagoDao = new MedioPagoDao(ctx);
        DireccionDao direccionDao = new DireccionDao(ctx);
        Direccion direccionAval = direccionDao.findByIdDireccion(Long.parseLong(aval.getDireccionId()));

        json_aval.put(K_NOMBRE, aval.getNombre());
        json_aval.put(K_PATERNO, aval.getPaterno());
        json_aval.put(K_MATERNO, aval.getMaterno());
        json_aval.put(K_FECHA_NACIMIENTO, aval.getFechaNacimiento());
        json_aval.put(K_EDAD, aval.getEdad());
        json_aval.put(K_GENERO, aval.getGenero());
        json_aval.put(K_ESTADO_NACIMIENTO, aval.getEstadoNacimiento());
        json_aval.put(K_RFC, aval.getRfc());
        json_aval.put(K_CURP, aval.getCurp() + aval.getCurpDigito());
        json_aval.put(K_PARENTESCO_SOLICITANTE, aval.getParentescoCliente());
        json_aval.put(K_IDENTIFICACION_TIPO, aval.getTipoIdentificacion());
        json_aval.put(K_NO_IDENTIFICACION, aval.getNoIdentificacion());
        json_aval.put(K_OCUPACION, aval.getOcupacion());
        json_aval.put(K_ACTIVIDAD_ECONOMICA, aval.getActividadEconomica());

        json_aval.put(K_LATITUD, direccionAval.getLatitud());
        json_aval.put(K_LONGITUD, direccionAval.getLongitud());
        json_aval.put(K_CALLE, direccionAval.getCalle());
        json_aval.put(K_NO_EXTERIOR, direccionAval.getNumExterior());
        json_aval.put(K_NO_INTERIOR, direccionAval.getNumInterior());
        json_aval.put(K_NO_LOTE, direccionAval.getLote());
        json_aval.put(K_NO_MANZANA, direccionAval.getManzana());
        json_aval.put(K_CODIGO_POSTAL, direccionAval.getCp());
        json_aval.put(K_COLONIA, direccionAval.getColonia());
        json_aval.put(K_CIUDAD, direccionAval.getCiudad());
        json_aval.put(K_LOCALIDAD, direccionAval.getLocalidad());
        json_aval.put(K_MUNICIPIO, direccionAval.getMunicipio());
        json_aval.put(K_ESTADO, direccionAval.getEstado());
        json_aval.put(K_LOCATED_AT, direccionAval.getLocatedAt());

        json_aval.put(K_TIPO_VIVIENDA, aval.getTipoVivienda());

        if (aval.getTipoVivienda().equals("CASA FAMILIAR") || aval.getTipoVivienda().equals("CASA RENTADA")) {
            json_aval.put(K_NOMBRE_TITULAR, aval.getNombreTitular());
            json_aval.put(K_PARENTESCO_TITULAR, aval.getParentesco());
        }
        json_aval.put(K_CARACTERISTICAS_DOMICILIO, aval.getCaracteristicasDomicilio());
        json_aval.put(K_TIENE_NEGOCIO, aval.getTieneNegocio() == 1);
        if (aval.getTieneNegocio() == 1) {
            json_aval.put(K_NOMBRE_NEGOCIO, aval.getNombreNegocio().trim().toUpperCase());
            json_aval.put(K_ANTIGUEDAD, aval.getAntigueda());
        }

        json_aval.put(K_INGRESO_MENSUAL, Double.parseDouble(aval.getIngMensual().replace(",", "")));
        json_aval.put(K_INGRESOS_OTROS, Double.parseDouble(aval.getIngOtros().replace(",", "")));
        json_aval.put(K_GASTO_MENSUAL, Double.parseDouble(aval.getGastoSemanal().replace(",", "")));
        json_aval.put(K_GASTO_AGUA, Double.parseDouble(aval.getGastoAgua().replace(",", "")));
        json_aval.put(K_GASTO_LUZ, Double.parseDouble(aval.getGastoLuz().replace(",", "")));
        json_aval.put(K_GASTO_TELEFONO, Double.parseDouble(aval.getGastoTelefono().replace(",", "")));
        json_aval.put(K_GASTO_RENTA, Double.parseDouble(aval.getGastoRenta().replace(",", "")));
        json_aval.put(K_GASTO_OTROS, Double.parseDouble(aval.getGastoOtros().replace(",", "")));
        json_aval.put(K_MONTO_MAXIMO, Double.parseDouble(aval.getCapacidadPagos().replace(",", "")));
        json_aval.put(K_CAPACIDAD_PAGO, Double.parseDouble(aval.getMontoMaximo().replace(",", "")));
        json_aval.put(K_MEDIOS_PAGOS, medioPagoDao.findIdsByNombres(aval.getMedioPago()));
        if (aval.getMedioPago().contains("OTRO"))
            json_aval.put(K_OTRO_MEDIOS_PAGOS, aval.getOtroMedioPago());
        json_aval.put(K_HORA_LOCALIZACION, aval.getHorarioLocalizacion());
        json_aval.put(K_ACTIVOS_OBSERVABLES, aval.getActivosObservables());
        json_aval.put(K_TEL_CASA, aval.getTelCasa());
        json_aval.put(K_TEL_CELULAR, aval.getTelCelular());
        json_aval.put(K_TEL_MENSAJE, aval.getTelMensajes());
        json_aval.put(K_TEL_TRABAJO, aval.getTelTrabajo());
        json_aval.put(K_EMAIL, aval.getEmail());
        json_aval.put(K_FOTO_FACHADA, aval.getFotoFachada());
        json_aval.put(K_REFERENCIA_DOMICILIARIA, aval.getRefDomiciliaria());
        json_aval.put(K_FIRMA, aval.getFirma());
        json_aval.put(K_AVAL_LATITUD, aval.getLatitud());
        json_aval.put(K_AVAL_LONGITUD, aval.getLongitud());
        json_aval.put(K_AVAL_LOCATED_AT, aval.getLocatedAt());

        json_solicitud.put(K_SOLICITANTE_AVAL, json_aval);
    }

    private void fillReferenciaJson(JSONObject json_solicitud, Referencia referencia) throws JSONException {
        JSONObject json_referencia = new JSONObject();

        DireccionDao direccionDao = new DireccionDao(ctx);
        Direccion direccionReferencia = direccionDao.findByIdDireccion(Long.parseLong(referencia.getDireccionId()));

        json_referencia.put(K_NOMBRE, referencia.getNombre());
        json_referencia.put(K_PATERNO, referencia.getPaterno());
        json_referencia.put(K_MATERNO, referencia.getMaterno());
        json_referencia.put(K_FECHA_NACIMIENTO, referencia.getFechaNacimiento());

        json_referencia.put(K_CALLE, direccionReferencia.getCalle());
        json_referencia.put(K_NO_EXTERIOR, direccionReferencia.getNumExterior());
        json_referencia.put(K_NO_INTERIOR, direccionReferencia.getNumInterior());
        json_referencia.put(K_NO_LOTE, direccionReferencia.getLote());
        json_referencia.put(K_NO_MANZANA, direccionReferencia.getManzana());
        json_referencia.put(K_CODIGO_POSTAL, direccionReferencia.getCp());
        json_referencia.put(K_COLONIA, direccionReferencia.getColonia());
        json_referencia.put(K_CIUDAD, direccionReferencia.getCiudad());
        json_referencia.put(K_LOCALIDAD, direccionReferencia.getLocalidad());
        json_referencia.put(K_MUNICIPIO, direccionReferencia.getMunicipio());
        json_referencia.put(K_ESTADO, direccionReferencia.getEstado());

        json_referencia.put(K_TEL_CELULAR, referencia.getTelCelular());

        json_solicitud.put(K_SOLICITANTE_REFERENCIA, json_referencia);

    }

    private void fillCroquisJson(JSONObject json_solicitud, Croquis croquis) throws JSONException {
        JSONObject json_croquis = new JSONObject();

        json_croquis.put(K_CALLE_ENFRENTE, croquis.getCallePrincipal());
        json_croquis.put(K_CALLE_LATERAL_IZQ, croquis.getLateralUno());
        json_croquis.put(K_CALLE_LATERAL_DER, croquis.getLateralDos());
        json_croquis.put(K_CALLE_ATRAS, croquis.getCalleTrasera());
        json_croquis.put(K_REFERENCIAS, croquis.getReferencias());
        json_croquis.put(K_CARACTERISTICAS_DOMICILIO, croquis.getCaracteristicasDomicilio());

        json_solicitud.put(K_SOLICITANTE_CROQUIS, json_croquis);
    }

    private void fillPoliticaPldJson(JSONObject json_solicitud, PoliticaPld politicaPld) throws JSONException {
        JSONObject json_politicas = new JSONObject();

        json_politicas.put(K_PROPIETARIO, politicaPld.getPropietarioReal() == 1);
        json_politicas.put(K_PROVEEDOR_RECURSOS, politicaPld.getProveedorRecursos() == 1);
        json_politicas.put(K_POLITICAMENTE_EXP, politicaPld.getPersonaPolitica() == 1);

        json_solicitud.put(K_SOLICITANTE_POLITICAS, json_politicas);
    }

    private void fillDocumentoJson(JSONObject json_solicitud, Documento documento) throws JSONException {
        JSONObject json_documentos = new JSONObject();

        json_documentos.put(K_IDENTIFICACION_FRONTAL, documento.getIneFrontal());
        json_documentos.put(K_IDENTIFICACION_REVERSO, documento.getIneReverso());
        json_documentos.put(K_CURP, documento.getCurp());
        json_documentos.put(K_COMPROBANTE_DOMICILIO, documento.getComprobante());
        json_documentos.put(K_CODIGO_BARRAS, documento.getCodigoBarras());
        json_documentos.put(K_FIRMA_ASESOR, documento.getFirmaAsesor());

        if (documento.getIneSelfie() != null)
            json_documentos.put(K_IDENTIFICACION_SELFIE, documento.getIneSelfie());
        else
            json_documentos.put(K_IDENTIFICACION_SELFIE, "");

        if (documento.getComprobanteGarantia() != null)
            json_documentos.put(K_COMPROBANTE_GARANTIA, documento.getComprobanteGarantia());
        else
            json_documentos.put(K_COMPROBANTE_GARANTIA, "");

        if (documento.getIneFrontalAval() != null)
            json_documentos.put(K_IDENTIFICACION_FRONTAL_AVAL, documento.getIneFrontalAval());
        else
            json_documentos.put(K_IDENTIFICACION_FRONTAL_AVAL, "");

        if (documento.getIneReversoAval() != null)
            json_documentos.put(K_IDENTIFICACION_REVERSO_AVAL, documento.getIneReversoAval());
        else
            json_documentos.put(K_IDENTIFICACION_REVERSO_AVAL, "");

        if (documento.getCurpAval() != null)
            json_documentos.put(K_CURP_AVAL, documento.getCurpAval());
        else
            json_documentos.put(K_CURP_AVAL, "");

        if (documento.getComprobanteAval() != null)
            json_documentos.put(K_COMPROBANTE_DOMICILIO_AVAL, documento.getComprobanteAval());
        else
            json_documentos.put(K_COMPROBANTE_DOMICILIO_AVAL, "");

        json_solicitud.put(K_SOLICITANTE_DOCUMENTOS, json_documentos);
    }

    private void fillSolicitudRenJson(JSONObject json_solicitud, SolicitudRen solicitudRen) throws JSONException {
        json_solicitud.put(K_FECHA_INICIO, solicitudRen.getFechaInicio());
        json_solicitud.put(K_FECHA_TERMINO, solicitudRen.getFechaTermino());
        json_solicitud.put(K_FECHA_ENVIO, Miscellaneous.ObtenerFecha(TIMESTAMP));
    }

    private void fillCreditoRenJson(JSONObject json_solicitud, CreditoIndRen creditoIndRen) throws JSONException {
        json_solicitud.put(K_PLAZO, creditoIndRen.getPlazoAsInt());
        json_solicitud.put(K_PERIODICIDAD, creditoIndRen.getPeriodicidadAsInt());
        json_solicitud.put(K_FECHA_DESEMBOLSO, creditoIndRen.getFechaDesembolso());
        json_solicitud.put(K_HORA_VISITA, creditoIndRen.getHoraVisita());
        json_solicitud.put(K_MONTO_PRESTAMO, Integer.parseInt(creditoIndRen.getMontoPrestamo().replace(",", "")));
        json_solicitud.put(K_MONTO_LETRA, (Miscellaneous.cantidadLetra(creditoIndRen.getMontoPrestamo().replace(",", "")).toUpperCase() + " PESOS MEXICANOS").replace("  ", " "));
        json_solicitud.put(K_DESTINO_PRESTAMO, creditoIndRen.getDestino());
        json_solicitud.put(K_CLASIFICACION_RIESGO, creditoIndRen.getClasificacionRiesgo());
        json_solicitud.put(K_COMPORTAMIENTO_PAGO, creditoIndRen.getComportamientoPago());
        json_solicitud.put(K_OBSERVACIONES, creditoIndRen.getObservaciones());
        json_solicitud.put(K_TIPO_SOLICITUD, "RENOVACION");
        json_solicitud.put(K_ID_CAMPANA, creditoIndRen.getId_campana());
        if (creditoIndRen.getMontoRefinanciar() != null && !creditoIndRen.getMontoRefinanciar().isEmpty())
            Integer.parseInt(creditoIndRen.getMontoRefinanciar().replace(",", ""));
        else
            json_solicitud.put(K_MONTO_REFINANCIAR, 0);
    }

    private void fillClienteRenJson(JSONObject json_solicitud, ClienteRen clienteRen, CreditoIndRen creditoRen) throws JSONException {
        DireccionRenDao direccionRenDao = new DireccionRenDao(ctx);
        DireccionRen direccionClienteRen = direccionRenDao.findByIdDireccion(Long.parseLong(clienteRen.getDireccionId()));

        JSONObject json_solicitante = new JSONObject();

        json_solicitante.put(K_NOMBRE, clienteRen.getNombre());
        json_solicitante.put(K_PATERNO, clienteRen.getPaterno());
        json_solicitante.put(K_MATERNO, clienteRen.getMaterno());
        json_solicitante.put(K_FECHA_NACIMIENTO, clienteRen.getFechaNacimiento());
        json_solicitante.put(K_EDAD, clienteRen.getEdad());
        json_solicitante.put(K_GENERO, clienteRen.getGenero());
        json_solicitante.put(K_ESTADO_NACIMIENTO, clienteRen.getEstadoNacimiento());
        json_solicitante.put(K_RFC, clienteRen.getRfc());
        json_solicitante.put(K_CURP, clienteRen.getCurp() + clienteRen.getCurpDigitoVeri());
        json_solicitante.put(K_OCUPACION, clienteRen.getOcupacion());
        json_solicitante.put(K_ACTIVIDAD_ECONOMICA, clienteRen.getActividadEconomica());
        json_solicitante.put(K_IDENTIFICACION_TIPO, clienteRen.getTipoIdentificacion());
        json_solicitante.put(K_NO_IDENTIFICACION, clienteRen.getNoIdentificacion());
        json_solicitante.put(K_NIVEL_ESTUDIO, clienteRen.getNivelEstudio());

        json_solicitante.put(K_ESTADO_CIVIL, clienteRen.getEstadoCivil());

        if (clienteRen.getEstadoCivil().equals("CASADO(A)"))
            json_solicitante.put(K_BIENES, (clienteRen.getBienes() == 1) ? "MANCOMUNADOS" : "SEPARADOS");

        json_solicitante.put(K_TIPO_VIVIENDA, clienteRen.getTipoVivienda());

        if (clienteRen.getTipoVivienda().equals("CASA FAMILIAR"))
            json_solicitante.put(K_PARENTESCO, clienteRen.getParentesco());
        else if (clienteRen.getTipoVivienda().equals("OTRO"))
            json_solicitante.put(K_OTRO_TIPO_VIVIENDA, clienteRen.getOtroTipoVivienda());

        json_solicitante.put(K_LATITUD, direccionClienteRen.getLatitud());
        json_solicitante.put(K_LONGITUD, direccionClienteRen.getLongitud());
        json_solicitante.put(K_CALLE, direccionClienteRen.getCalle());
        json_solicitante.put(K_NO_EXTERIOR, direccionClienteRen.getNumExterior());
        json_solicitante.put(K_NO_INTERIOR, direccionClienteRen.getNumInterior());
        json_solicitante.put(K_NO_LOTE, direccionClienteRen.getLote());
        json_solicitante.put(K_NO_MANZANA, direccionClienteRen.getManzana());
        json_solicitante.put(K_CODIGO_POSTAL, direccionClienteRen.getCp());
        json_solicitante.put(K_COLONIA, direccionClienteRen.getColonia());
        json_solicitante.put(K_CIUDAD, direccionClienteRen.getCiudad());
        json_solicitante.put(K_LOCALIDAD, direccionClienteRen.getLocalidad());
        json_solicitante.put(K_MUNICIPIO, direccionClienteRen.getMunicipio());
        json_solicitante.put(K_ESTADO, direccionClienteRen.getEstado());
        json_solicitante.put(K_LOCATED_AT, direccionClienteRen.getLocatedAt());

        json_solicitante.put(K_TEL_CASA, clienteRen.getTelCasa());
        json_solicitante.put(K_TEL_CELULAR, clienteRen.getTelCelular());
        json_solicitante.put(K_TEL_MENSAJE, clienteRen.getTelMensajes());
        json_solicitante.put(K_TEL_TRABAJO, clienteRen.getTelTrabajo());
        json_solicitante.put(K_TIEMPO_VIVIR_SITIO, clienteRen.getTiempoVivirSitio());
        json_solicitante.put(K_DEPENDIENTES_ECONOMICO, clienteRen.getDependientes());
        json_solicitante.put(K_MEDIO_CONTACTO, clienteRen.getMedioContacto());
        json_solicitante.put(K_ESTADO_CUENTA, clienteRen.getEstadoCuenta());
        json_solicitante.put(K_EMAIL, clienteRen.getEmail());
        json_solicitante.put(K_FOTO_FACHADA, clienteRen.getFotoFachada());
        json_solicitante.put(K_REFERENCIA_DOMICILIARIA, clienteRen.getRefDomiciliaria());
        json_solicitante.put(K_FIRMA, clienteRen.getFirma());
        json_solicitante.put(K_SOL_LATITUD, clienteRen.getLatitud());
        json_solicitante.put(K_SOL_LONGITUD, clienteRen.getLongitud());
        json_solicitante.put(K_SOL_LOCATED_AT, clienteRen.getLocatedAt());
        json_solicitante.put(K_TIENE_FIRMA, "SI");
        json_solicitante.put(K_NOMBRE_QUIEN_FIRMA, "");

        json_solicitud.put(K_CLIENTE_ID, creditoRen.getNumCliente());
        json_solicitud.put(K_SOLICITANTE, json_solicitante);

    }

    private void fillConyugueRenJson(JSONObject json_solicitud, ConyugueRen conyugueRen) throws JSONException {
        JSONObject json_conyuge = new JSONObject();

        DireccionRenDao direccionConyugueRenDao = new DireccionRenDao(ctx);
        DireccionRen direccionConyugueRen = direccionConyugueRenDao.findByIdDireccion(Long.parseLong(conyugueRen.getDireccionId()));

        json_conyuge.put(K_NOMBRE, conyugueRen.getNombre());
        json_conyuge.put(K_PATERNO, conyugueRen.getPaterno());
        json_conyuge.put(K_MATERNO, conyugueRen.getMaterno());
        json_conyuge.put(K_NACIONALIDAD, conyugueRen.getNacionalidad());
        json_conyuge.put(K_OCUPACION, conyugueRen.getOcupacion());

        json_conyuge.put(K_CALLE, direccionConyugueRen.getCalle());
        json_conyuge.put(K_NO_EXTERIOR, direccionConyugueRen.getNumExterior());
        json_conyuge.put(K_NO_INTERIOR, direccionConyugueRen.getNumInterior());
        json_conyuge.put(K_NO_LOTE, direccionConyugueRen.getLote());
        json_conyuge.put(K_NO_MANZANA, direccionConyugueRen.getManzana());
        json_conyuge.put(K_CODIGO_POSTAL, direccionConyugueRen.getCp());
        json_conyuge.put(K_COLONIA, direccionConyugueRen.getColonia());
        json_conyuge.put(K_CIUDAD, direccionConyugueRen.getCiudad());
        json_conyuge.put(K_LOCALIDAD, direccionConyugueRen.getLocalidad());
        json_conyuge.put(K_MUNICIPIO, direccionConyugueRen.getMunicipio());
        json_conyuge.put(K_ESTADO, direccionConyugueRen.getEstado());

        json_conyuge.put(K_INGRESO_MENSUAL, Double.parseDouble(conyugueRen.getIngMensual().replace(",", "")));
        json_conyuge.put(K_GASTO_MENSUAL, Double.parseDouble(conyugueRen.getGastoMensual().replace(",", "")));
        json_conyuge.put(K_TEL_CASA, conyugueRen.getTelCasa());
        json_conyuge.put(K_TEL_CELULAR, conyugueRen.getTelCelular());

        json_solicitud.put(K_SOLICITANTE_CONYUGE, json_conyuge);
    }

    private void fillEconomicoRenJson(JSONObject json_solicitud, EconomicoRen economicoRen) throws JSONException {
        JSONObject json_economicos = new JSONObject();

        json_economicos.put(K_PROPIEDADES, economicoRen.getPropiedades());
        json_economicos.put(K_VALOR_APROXIMADO, economicoRen.getValorAproximado());
        json_economicos.put(K_UBICACION, economicoRen.getUbicacion());
        json_economicos.put(K_INGRESO, economicoRen.getIngreso().replace(",", ""));

        json_solicitud.put(K_SOLICITANTE_DATOS_ECONOMICOS, json_economicos);
    }

    private void fillNegocioRenJson(JSONObject json_solicitud, NegocioRen negocioRen) throws JSONException {
        MedioPagoDao medioPagoDao = new MedioPagoDao(ctx);
        DireccionRenDao direccionNegocioRenDao = new DireccionRenDao(ctx);
        DireccionRen direccionNegocioRen = direccionNegocioRenDao.findByIdDireccion(Long.parseLong(negocioRen.getDireccionId()));

        JSONObject json_negocio = new JSONObject();

        json_negocio.put(K_NOMBRE, negocioRen.getNombre());
        json_negocio.put(K_LATITUD, direccionNegocioRen.getLatitud());
        json_negocio.put(K_LONGITUD, direccionNegocioRen.getLatitud());
        json_negocio.put(K_CALLE, direccionNegocioRen.getCalle());
        json_negocio.put(K_NO_EXTERIOR, direccionNegocioRen.getNumExterior());
        json_negocio.put(K_NO_INTERIOR, direccionNegocioRen.getNumInterior());
        json_negocio.put(K_NO_LOTE, direccionNegocioRen.getLote());
        json_negocio.put(K_NO_MANZANA, direccionNegocioRen.getManzana());
        json_negocio.put(K_CODIGO_POSTAL, direccionNegocioRen.getCp());
        json_negocio.put(K_COLONIA, direccionNegocioRen.getColonia());
        json_negocio.put(K_CIUDAD, direccionNegocioRen.getCiudad());
        json_negocio.put(K_LOCALIDAD, direccionNegocioRen.getLocalidad());
        json_negocio.put(K_MUNICIPIO, direccionNegocioRen.getMunicipio());
        json_negocio.put(K_ESTADO, direccionNegocioRen.getEstado());
        json_negocio.put(K_LOCATED_AT, direccionNegocioRen.getLocatedAt());
        json_negocio.put(K_OCUPACION, negocioRen.getOcupacion());
        json_negocio.put(K_ACTIVIDAD_ECONOMICA, negocioRen.getActividadEconomica());
        json_negocio.put(K_DESTINO_CREDITO, negocioRen.getDestinoCredito());
        if (negocioRen.getDestinoCredito().contains("OTRO"))
            json_negocio.put(K_OTRO_DESTINO_CREDITO, negocioRen.getOtroDestino());
        json_negocio.put(K_ANTIGUEDAD, negocioRen.getAntiguedad());
        json_negocio.put(K_INGRESO_MENSUAL, negocioRen.getIngMensual().replace(",", ""));
        json_negocio.put(K_INGRESOS_OTROS, negocioRen.getIngOtros().replace(",", ""));
        json_negocio.put(K_GASTO_MENSUAL, negocioRen.getGastoSemanal().replace(",", ""));
        json_negocio.put(K_GASTO_AGUA, negocioRen.getGastoAgua().replace(",", ""));
        json_negocio.put(K_GASTO_LUZ, negocioRen.getGastoLuz().replace(",", ""));
        json_negocio.put(K_GASTO_TELEFONO, negocioRen.getGastoTelefono().replace(",", ""));
        json_negocio.put(K_GASTO_RENTA, negocioRen.getGastoRenta().replace(",", ""));
        json_negocio.put(K_GASTO_OTROS, negocioRen.getGastoOtros().replace(",", ""));
        json_negocio.put(K_CAPACIDAD_PAGO, negocioRen.getMontoMaximo());
        if (negocioRen.getMedioPago().contains("OTRO"))
            json_negocio.put(K_OTRO_MEDIOS_PAGOS, negocioRen.getOtroMedioPago());
        json_negocio.put(K_MEDIOS_PAGOS, medioPagoDao.findIdsByNombres(negocioRen.getMedioPago()));
        json_negocio.put(K_MONTO_MAXIMO, negocioRen.getCapacidadPago());
        json_negocio.put(K_NUM_OPERACIONES_MENSUAL, negocioRen.getNumOperacionMensuales());
        if (negocioRen.getMedioPago().contains("EFECTIVO"))
            json_negocio.put(K_NUM_OPERACIONES_MENSUAL_EFECTIVO, negocioRen.getNumOperacionEfectivo());
        json_negocio.put(K_DIAS_VENTA, negocioRen.getDiasVenta());
        json_negocio.put(K_FOTO_FACHADA, negocioRen.getFotoFachada());
        json_negocio.put(K_REFERENCIA_NEGOCIO, negocioRen.getRefDomiciliaria());

        json_solicitud.put(K_SOLICITANTE_NEGOCIO, json_negocio);
    }

    private void fillAvalRenJson(JSONObject json_solicitud, AvalRen avalRen) throws JSONException {
        JSONObject json_aval = new JSONObject();

        MedioPagoDao medioPagoDao = new MedioPagoDao(ctx);
        DireccionRenDao direccionRenDao = new DireccionRenDao(ctx);
        DireccionRen direccionAvalRen = direccionRenDao.findByIdDireccion(Long.parseLong(avalRen.getDireccionId()));

        json_aval.put(K_NOMBRE, avalRen.getNombre());
        json_aval.put(K_PATERNO, avalRen.getPaterno());
        json_aval.put(K_MATERNO, avalRen.getMaterno());
        json_aval.put(K_FECHA_NACIMIENTO, avalRen.getFechaNacimiento());
        json_aval.put(K_EDAD, avalRen.getEdad());
        json_aval.put(K_GENERO, avalRen.getGenero());
        json_aval.put(K_ESTADO_NACIMIENTO, avalRen.getEstadoNacimiento());
        json_aval.put(K_RFC, avalRen.getRfc());
        json_aval.put(K_CURP, avalRen.getCurp() + avalRen.getCurpDigito());
        json_aval.put(K_PARENTESCO_SOLICITANTE, avalRen.getParentescoCliente());
        json_aval.put(K_IDENTIFICACION_TIPO, avalRen.getTipoIdentificacion());
        json_aval.put(K_NO_IDENTIFICACION, avalRen.getNoIdentificacion());
        json_aval.put(K_OCUPACION, avalRen.getOcupacion());
        json_aval.put(K_ACTIVIDAD_ECONOMICA, avalRen.getActividadEconomica());

        json_aval.put(K_LATITUD, direccionAvalRen.getLatitud());
        json_aval.put(K_LONGITUD, direccionAvalRen.getLongitud());
        json_aval.put(K_CALLE, direccionAvalRen.getCalle());
        json_aval.put(K_NO_EXTERIOR, direccionAvalRen.getNumExterior());
        json_aval.put(K_NO_INTERIOR, direccionAvalRen.getNumInterior());
        json_aval.put(K_NO_LOTE, direccionAvalRen.getLote());
        json_aval.put(K_NO_MANZANA, direccionAvalRen.getManzana());
        json_aval.put(K_CODIGO_POSTAL, direccionAvalRen.getCp());
        json_aval.put(K_COLONIA, direccionAvalRen.getColonia());
        json_aval.put(K_CIUDAD, direccionAvalRen.getCiudad());
        json_aval.put(K_LOCALIDAD, direccionAvalRen.getLocalidad());
        json_aval.put(K_MUNICIPIO, direccionAvalRen.getMunicipio());
        json_aval.put(K_ESTADO, direccionAvalRen.getEstado());
        json_aval.put(K_LOCATED_AT, direccionAvalRen.getLocatedAt());

        json_aval.put(K_TIPO_VIVIENDA, avalRen.getTipoVivienda());

        if (avalRen.getTipoVivienda().equals("CASA FAMILIAR") || avalRen.getTipoVivienda().equals("CASA RENTADA")) {
            json_aval.put(K_NOMBRE_TITULAR, avalRen.getNombreTitular());
            json_aval.put(K_PARENTESCO_TITULAR, avalRen.getParentesco());
        }
        json_aval.put(K_CARACTERISTICAS_DOMICILIO, avalRen.getCaracteristicasDomicilio());
        json_aval.put(K_TIENE_NEGOCIO, avalRen.getTieneNegocio() == 1);
        if (avalRen.getTieneNegocio() == 1) {
            json_aval.put(K_NOMBRE_NEGOCIO, avalRen.getNombreNegocio().trim().toUpperCase());
            json_aval.put(K_ANTIGUEDAD, avalRen.getAntigueda());
        }

        json_aval.put(K_INGRESO_MENSUAL, Double.parseDouble(avalRen.getIngMensual().replace(",", "")));
        json_aval.put(K_INGRESOS_OTROS, Double.parseDouble(avalRen.getIngOtros().replace(",", "")));
        json_aval.put(K_GASTO_MENSUAL, Double.parseDouble(avalRen.getGastoSemanal().replace(",", "")));
        json_aval.put(K_GASTO_AGUA, Double.parseDouble(avalRen.getGastoAgua().replace(",", "")));
        json_aval.put(K_GASTO_LUZ, Double.parseDouble(avalRen.getGastoLuz().replace(",", "")));
        json_aval.put(K_GASTO_TELEFONO, Double.parseDouble(avalRen.getGastoTelefono().replace(",", "")));
        json_aval.put(K_GASTO_RENTA, Double.parseDouble(avalRen.getGastoRenta().replace(",", "")));
        json_aval.put(K_GASTO_OTROS, Double.parseDouble(avalRen.getGastoOtros().replace(",", "")));
        json_aval.put(K_MONTO_MAXIMO, Double.parseDouble(avalRen.getCapacidadPagos().replace(",", "")));
        json_aval.put(K_CAPACIDAD_PAGO, Double.parseDouble(avalRen.getMontoMaximo().replace(",", "")));
        json_aval.put(K_MEDIOS_PAGOS, medioPagoDao.findIdsByNombres(avalRen.getMedioPago()));
        if (avalRen.getMedioPago().contains("OTRO"))
            json_aval.put(K_OTRO_MEDIOS_PAGOS, avalRen.getOtroMedioPago());
        json_aval.put(K_HORA_LOCALIZACION, avalRen.getHorarioLocalizacion());
        json_aval.put(K_ACTIVOS_OBSERVABLES, avalRen.getActivosObservables());
        json_aval.put(K_TEL_CASA, avalRen.getTelCasa());
        json_aval.put(K_TEL_CELULAR, avalRen.getTelCelular());
        json_aval.put(K_TEL_MENSAJE, avalRen.getTelMensajes());
        json_aval.put(K_TEL_TRABAJO, avalRen.getTelTrabajo());
        json_aval.put(K_EMAIL, avalRen.getEmail());
        json_aval.put(K_FOTO_FACHADA, avalRen.getFotoFachada());
        json_aval.put(K_REFERENCIA_DOMICILIARIA, avalRen.getRefDomiciliaria());
        json_aval.put(K_FIRMA, avalRen.getFirma());
        json_aval.put(K_AVAL_LATITUD, avalRen.getLatitud());
        json_aval.put(K_AVAL_LONGITUD, avalRen.getLongitud());
        json_aval.put(K_AVAL_LOCATED_AT, avalRen.getLocatedAt());

        json_solicitud.put(K_SOLICITANTE_AVAL, json_aval);
    }

    private void fillReferenciaRenJson(JSONObject json_solicitud, ReferenciaRen referenciaRen) throws JSONException {
        JSONObject json_referencia = new JSONObject();

        DireccionRenDao direccionRenDao = new DireccionRenDao(ctx);
        DireccionRen direccionReferenciaRen = direccionRenDao.findByIdDireccion(Long.parseLong(referenciaRen.getDireccionId()));

        json_referencia.put(K_NOMBRE, referenciaRen.getNombre());
        json_referencia.put(K_PATERNO, referenciaRen.getPaterno());
        json_referencia.put(K_MATERNO, referenciaRen.getMaterno());
        json_referencia.put(K_FECHA_NACIMIENTO, referenciaRen.getFechaNacimiento());

        json_referencia.put(K_CALLE, direccionReferenciaRen.getCalle());
        json_referencia.put(K_NO_EXTERIOR, direccionReferenciaRen.getNumExterior());
        json_referencia.put(K_NO_INTERIOR, direccionReferenciaRen.getNumInterior());
        json_referencia.put(K_NO_LOTE, direccionReferenciaRen.getLote());
        json_referencia.put(K_NO_MANZANA, direccionReferenciaRen.getManzana());
        json_referencia.put(K_CODIGO_POSTAL, direccionReferenciaRen.getCp());
        json_referencia.put(K_COLONIA, direccionReferenciaRen.getColonia());
        json_referencia.put(K_CIUDAD, direccionReferenciaRen.getCiudad());
        json_referencia.put(K_LOCALIDAD, direccionReferenciaRen.getLocalidad());
        json_referencia.put(K_MUNICIPIO, direccionReferenciaRen.getMunicipio());
        json_referencia.put(K_ESTADO, direccionReferenciaRen.getEstado());

        json_referencia.put(K_TEL_CELULAR, referenciaRen.getTelCelular());

        json_solicitud.put(K_SOLICITANTE_REFERENCIA, json_referencia);

    }

    private void fillCroquisRenJson(JSONObject json_solicitud, CroquisRen croquisRen) throws JSONException {
        JSONObject json_croquis = new JSONObject();

        json_croquis.put(K_CALLE_ENFRENTE, croquisRen.getCallePrincipal());
        json_croquis.put(K_CALLE_LATERAL_IZQ, croquisRen.getLateralUno());
        json_croquis.put(K_CALLE_LATERAL_DER, croquisRen.getLateralDos());
        json_croquis.put(K_CALLE_ATRAS, croquisRen.getCalleTrasera());
        json_croquis.put(K_REFERENCIAS, croquisRen.getReferencias());
        json_croquis.put(K_CARACTERISTICAS_DOMICILIO, croquisRen.getCaracteristicasDomicilio());

        json_solicitud.put(K_SOLICITANTE_CROQUIS, json_croquis);
    }

    private void fillPoliticaPldRenJson(JSONObject json_solicitud, PoliticaPldRen politicaPldRen) throws JSONException {
        JSONObject json_politicas = new JSONObject();

        json_politicas.put(K_PROPIETARIO, politicaPldRen.getPropietarioReal() == 1);
        json_politicas.put(K_PROVEEDOR_RECURSOS, politicaPldRen.getProveedorRecursos() == 1);
        json_politicas.put(K_POLITICAMENTE_EXP, politicaPldRen.getPersonaPolitica() == 1);

        json_solicitud.put(K_SOLICITANTE_POLITICAS, json_politicas);
    }

    private void fillDocumentoRenJson(JSONObject json_solicitud, DocumentoRen documentoRen) throws JSONException {
        JSONObject json_documentos = new JSONObject();

        json_documentos.put(K_IDENTIFICACION_FRONTAL, documentoRen.getIneFrontal());
        json_documentos.put(K_IDENTIFICACION_REVERSO, documentoRen.getIneReverso());
        json_documentos.put(K_CURP, documentoRen.getCurp());
        json_documentos.put(K_COMPROBANTE_DOMICILIO, documentoRen.getComprobante());
        json_documentos.put(K_CODIGO_BARRAS, documentoRen.getCodigoBarras());
        json_documentos.put(K_FIRMA_ASESOR, documentoRen.getFirmaAsesor());

        if (documentoRen.getIneSelfie() != null)
            json_documentos.put(K_IDENTIFICACION_SELFIE, documentoRen.getIneSelfie());
        else
            json_documentos.put(K_IDENTIFICACION_SELFIE, "");

        if (documentoRen.getComprobanteGarantia() != null)
            json_documentos.put(K_COMPROBANTE_GARANTIA, documentoRen.getComprobanteGarantia());
        else
            json_documentos.put(K_COMPROBANTE_GARANTIA, "");

        if (documentoRen.getIneFrontalAval() != null)
            json_documentos.put(K_IDENTIFICACION_FRONTAL_AVAL, documentoRen.getIneFrontalAval());
        else
            json_documentos.put(K_IDENTIFICACION_FRONTAL_AVAL, "");

        if (documentoRen.getIneReversoAval() != null)
            json_documentos.put(K_IDENTIFICACION_REVERSO_AVAL, documentoRen.getIneReversoAval());
        else
            json_documentos.put(K_IDENTIFICACION_REVERSO_AVAL, "");

        if (documentoRen.getCurpAval() != null)
            json_documentos.put(K_CURP_AVAL, documentoRen.getCurpAval());
        else
            json_documentos.put(K_CURP_AVAL, "");

        if (documentoRen.getComprobanteAval() != null)
            json_documentos.put(K_COMPROBANTE_DOMICILIO_AVAL, documentoRen.getComprobanteAval());
        else
            json_documentos.put(K_COMPROBANTE_DOMICILIO_AVAL, "");

        json_solicitud.put(K_SOLICITANTE_DOCUMENTOS, json_documentos);
    }

    @SuppressLint("Range")
    public void obtenerDatosBeneficiario(int id_solicitud) {
        DBhelper dBhelper = DBhelper.getInstance(ctx);
        BeneficiarioDto beneficiarioDto = null;
        Cursor row = dBhelper.getBeneficiarioIndA(TBL_DATOS_BENEFICIARIO, String.valueOf(id_solicitud));

        if (row != null && row.moveToFirst()) {
            String serieId = session.getUser().get(0);
            beneficiarioDto = new BeneficiarioDto(
                    row.getLong(row.getColumnIndex("id_solicitud")),
                    row.getInt(row.getColumnIndex("id_cliente")),
                    row.getInt(row.getColumnIndex("id_grupo")),
                    row.getString(row.getColumnIndex("nombre")),
                    row.getString(row.getColumnIndex("paterno")),
                    row.getString(row.getColumnIndex("materno")),
                    row.getString(row.getColumnIndex("parentesco")),
                    Integer.parseInt(serieId)
            );
            row.close();
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

    @SuppressLint("Range")
    public void obtenerDatosBeneficiarioRen(int id_solicitud) {
        DBhelper dBhelper = DBhelper.getInstance(ctx);

        BeneficiarioDto beneficiarioDto = null;

        Cursor row = dBhelper.getBeneficiarioIndA(TBL_DATOS_BENEFICIARIO_REN, String.valueOf(id_solicitud));

        if (row.getCount() > 0) {
            row.moveToFirst();
            String serieId = session.getUser().get(0);
            beneficiarioDto = new BeneficiarioDto(
                    row.getLong(row.getColumnIndex("id_solicitud")),
                    row.getInt(row.getColumnIndex("id_cliente")),
                    row.getInt(row.getColumnIndex("id_grupo")),
                    row.getString(row.getColumnIndex("nombre")),
                    row.getString(row.getColumnIndex("paterno")),
                    row.getString(row.getColumnIndex("materno")),
                    row.getString(row.getColumnIndex("parentesco")),
                    Integer.parseInt(serieId)
            );
            row.close();
        } else {
            Toast.makeText(ctx, "NO SE ENCONTRARON LOS DATOS", Toast.LENGTH_SHORT).show();
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

    public void enviarDatosCampana(int id_solicitud) {
        DBhelper dBhelper = DBhelper.getInstance(ctx);

        datoCampana dc = new datoCampana();

        Cursor row = dBhelper.getRecords(TBL_DATOS_CREDITO_CAMPANA, " WHERE id_solicitud = ?", " ", new String[]{String.valueOf(id_solicitud)});

        if (row.getCount() > 0) {
            row.moveToFirst();
            dc.setId_originacion(row.getInt(2));
            dc.setId_campana(row.getInt(3));
            dc.setTipo_campana(row.getString(4));
            dc.setNombre_refiero(row.getString(5));
            row.close();
        } else {
            Toast.makeText(ctx, "NO SE ENCONTRARON LOS DATOS", Toast.LENGTH_SHORT).show();
        }

        ManagerInterface api = RetrofitClient.generalRF(CONTROLLER_API, ctx).create(ManagerInterface.class);

        Call<datoCampana> call = api.saveCreditoCampana(
                "Bearer " + session.getUser().get(7),
                Long.valueOf(dc.getId_originacion()),
                dc.getId_campana(),
                dc.getTipo_campana(),
                dc.getNombre_refiero()
        );

        call.enqueue(new Callback<datoCampana>() {
            @Override
            public void onResponse(Call<datoCampana> call, Response<datoCampana> response) {
                switch (response.code()) {
                    case 200:
                        Log.e("AQUI:", "REGISTRO COMPLETO CREDITO CAMPAÑA");
                        break;
                }
            }

            @Override
            public void onFailure(Call<datoCampana> call, Throwable t) {
                Log.e("AQUI:", "NO SE REGISTRO LA CAMPAÑA");
            }
        });
    }

}
