package com.sidert.sidertmovil.activities;

import android.app.DownloadManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.provider.Settings;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.webkit.CookieManager;
import android.webkit.URLUtil;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import com.sidert.sidertmovil.Home;
import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.database.DBhelper;
import com.sidert.sidertmovil.downloadapk.MyReceiverApk;
import com.sidert.sidertmovil.fragments.dialogs.dialog_pass_update_apk;
import com.sidert.sidertmovil.models.MRenovacion;
import com.sidert.sidertmovil.models.MResponseDefault;
import com.sidert.sidertmovil.models.catalogos.Colonia;
import com.sidert.sidertmovil.models.catalogos.ColoniaDao;
import com.sidert.sidertmovil.models.catalogos.Localidad;
import com.sidert.sidertmovil.models.catalogos.LocalidadDao;
import com.sidert.sidertmovil.models.catalogos.Municipio;
import com.sidert.sidertmovil.models.catalogos.MunicipioDao;
import com.sidert.sidertmovil.models.solicitudes.PrestamoToRenovar;
import com.sidert.sidertmovil.models.solicitudes.PrestamoToRenovarDao;
import com.sidert.sidertmovil.models.solicitudes.Solicitud;
import com.sidert.sidertmovil.models.solicitudes.SolicitudDao;
import com.sidert.sidertmovil.models.solicitudes.SolicitudRen;
import com.sidert.sidertmovil.models.solicitudes.SolicitudRenDao;
import com.sidert.sidertmovil.models.solicitudes.solicitudgpo.originacion.CreditoGpo;
import com.sidert.sidertmovil.models.solicitudes.solicitudgpo.originacion.CreditoGpoDao;
import com.sidert.sidertmovil.models.solicitudes.solicitudgpo.originacion.IntegranteGpo;
import com.sidert.sidertmovil.models.solicitudes.solicitudgpo.originacion.IntegranteGpoDao;
import com.sidert.sidertmovil.models.solicitudes.solicitudgpo.renovacion.CreditoGpoRen;
import com.sidert.sidertmovil.models.solicitudes.solicitudgpo.renovacion.CreditoGpoRenDao;
import com.sidert.sidertmovil.models.solicitudes.solicitudgpo.renovacion.IntegranteGpoRen;
import com.sidert.sidertmovil.models.solicitudes.solicitudgpo.renovacion.IntegranteGpoRenDao;
import com.sidert.sidertmovil.utils.Constants;
import com.sidert.sidertmovil.utils.ManagerInterface;
import com.sidert.sidertmovil.utils.Miscellaneous;
import com.sidert.sidertmovil.utils.NameFragments;
import com.sidert.sidertmovil.utils.NetworkStatus;
import com.sidert.sidertmovil.utils.Popups;
import com.sidert.sidertmovil.utils.RetrofitClient;
import com.sidert.sidertmovil.utils.Servicios_Sincronizado;
import com.sidert.sidertmovil.utils.SessionManager;
import com.sidert.sidertmovil.utils.Sincronizar_Catalogos;
import com.sidert.sidertmovil.utils.WebServicesRoutes;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

import static com.sidert.sidertmovil.utils.Constants.CONTROLLER_MOVIL;
import static com.sidert.sidertmovil.utils.Constants.TBL_PRESTAMOS_TO_RENOVAR;
import static com.sidert.sidertmovil.utils.Constants.TBL_RECIBOS_AGF_CC;
import static com.sidert.sidertmovil.utils.Constants.TBL_RECUPERACION_RECIBOS_CC;
import static com.sidert.sidertmovil.utils.Constants.TIMESTAMP;
import static com.sidert.sidertmovil.utils.Constants.TIPO;
import static org.apache.commons.lang3.CharEncoding.UTF_8;

public class Configuracion extends AppCompatActivity {

    private Context ctx;
    private CardView cvSincronizarFichas;
    private CardView cvSincronizarGeolocalizaciones;
    private CardView cvSincronizarImpresiones;
    private CardView cvSincronizarRutas;
    private CardView cvSincronizarAgf;
    private CardView cvSincronizarCirculoCredito;
    private CardView cvSincronizarOriginacion;
    private CardView cvSincronizarRenovaciones;
    private CardView cvSincronizarEstadoSolicitudes;
    private CardView cvSincronizarVerificacionesDomiciliarias;
    private CardView sincronizarColonias;
    private CardView sincronizarLocalidades;
    private CardView sincronizarMunicipios;
    private CardView cvSincronizarIntegrantesRen;
    private CardView cvCompletarIntegrantesRen;
    private CardView cvCatSolicitud;
    private CardView cvDescargarGuia;
    private CardView cvBorrarCache;
    private CardView cvSicronizarCampanas;

    private LinearLayout llAppUpdateMenu;


    private String[] _tablas;

    private ArrayList<Integer> selectItemsDelete;

    private SessionManager session;
    private DBhelper dBhelper;
    private SQLiteDatabase db;

    private MyReceiverApk myReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracion);

        Init();
        ctx = this;
        Toolbar tbMain = findViewById(R.id.tbMain);

        cvSincronizarFichas = findViewById(R.id.cvSincronizarFichas);
        cvSincronizarGeolocalizaciones = findViewById(R.id.cvSincronizarGeolocalizaciones);
        cvSincronizarImpresiones = findViewById(R.id.cvSincronizarImpresiones);
        cvSincronizarRutas = findViewById(R.id.cvSincronizarRutas);
        cvSincronizarAgf = findViewById(R.id.cvSincronizarAgf);
        cvSincronizarCirculoCredito = findViewById(R.id.cvSincronizarCirculoCredito);
        cvSincronizarOriginacion = findViewById(R.id.cvSincronizarOriginacion);
        cvSincronizarRenovaciones = findViewById(R.id.cvSincronizarRenovaciones);
        cvSincronizarIntegrantesRen = findViewById(R.id.cvSincronizarIntegrantesRen);
        cvSincronizarEstadoSolicitudes = findViewById(R.id.cvSincronizarEstadoSolicitudes);
        cvSincronizarVerificacionesDomiciliarias = findViewById(R.id.cvSincronizarVerificacionesDomiciliarias);
        sincronizarColonias = findViewById(R.id.sincronizarColonias);
        sincronizarLocalidades = findViewById(R.id.sincronizarLocalidades);
        sincronizarMunicipios = findViewById(R.id.sincronizarMunicipios);
        cvCompletarIntegrantesRen = findViewById(R.id.cvCompletarIntegrantesRen);
        cvCatSolicitud = findViewById(R.id.cvCatSolicitud);
        cvDescargarGuia = findViewById(R.id.cvDownloadGuia);
        cvBorrarCache = findViewById(R.id.cvBorrarCache);
        cvSicronizarCampanas = findViewById(R.id.cvCatalogosCampan);
        llAppUpdateMenu = findViewById(R.id.appUpdateMenu);

        CardView cvFichasGestionadas = findViewById(R.id.cvFichasGestionadas);
        CardView cvCatalogos = findViewById(R.id.cvCatalogos);
        CardView cvDownloadApk = findViewById(R.id.cvDownloadApk);
        CardView cvFechaHora = findViewById(R.id.cvFechaHora);

        _tablas = getResources().getStringArray(R.array.tablas_cache);

        setSupportActionBar(tbMain);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        dBhelper = DBhelper.getInstance(ctx);
        db = dBhelper.getWritableDatabase();

        session = SessionManager.getInstance(ctx);

        /**Evento de click para sincronizar lo que esté pendiente de envio...gestiones, impresiones, cierre de dia....*/
        cvSincronizarFichas.setOnClickListener(cvSincronizarFichas_OnClick);
        cvSincronizarGeolocalizaciones.setOnClickListener(cvSincronizarGeolocalizaciones_OnClick);
        cvSincronizarImpresiones.setOnClickListener(cvSincronizarImpresiones_OnClick);
        cvSincronizarRutas.setOnClickListener(cvSincronizarRutas_OnClick);
        cvSincronizarAgf.setOnClickListener(cvSincronizarAgf_OnClick);
        cvSincronizarCirculoCredito.setOnClickListener(cvSincronizarCirculoCredito_OnClick);
        cvSincronizarOriginacion.setOnClickListener(cvSincronizarOriginacion_OnClick);
        cvSincronizarRenovaciones.setOnClickListener(cvSincronizarRenovaciones_OnClick);
        cvSincronizarIntegrantesRen.setOnClickListener(cvSincronizarIntegrantesRen_OnClick);
        cvSincronizarEstadoSolicitudes.setOnClickListener(cvSincronizarEstadoSolicitudes_OnClick);
        cvSincronizarVerificacionesDomiciliarias.setOnClickListener(cvSincronizarVerificacionesDomiciliarias_OnClick);
        sincronizarColonias.setOnClickListener(sincronizarColonias_OnClick);
        sincronizarLocalidades.setOnClickListener(sincronizarLocalidades_OnClick);
        sincronizarMunicipios.setOnClickListener(sincronizarMunicipios_OnClick);
        cvCompletarIntegrantesRen.setOnClickListener(cvCompletarIntegrantesRen_OnClick);
        cvCatSolicitud.setOnClickListener(cvCatSolicitud_OnClick);
        cvSicronizarCampanas.setOnClickListener(cvSicronizarCampanas_OnClick);


        /**Evento de click para descargar las geolocalizaciones ya realizadas*/
        cvFichasGestionadas.setOnClickListener(cvFichasGestionadas_OnClick);
        /**Evento de click para descargar catalogos no se ocupa pero hay que corregir*/
        cvCatalogos.setOnClickListener(cvCatalogos_OnClick);
        /**Evento para para descargar el apk que se encuentra en el servidor*/
        cvDownloadApk.setOnClickListener(cvDownloadApk_OnClick);
        /**Evento para abrir la configuracion de fecha y hora se encuentra oculto este boton*/
        cvFechaHora.setOnClickListener(cvFechaHora_OnClick);
        /**Evento para descargar la guia de usuarios para asesores y gestores*/
        cvDescargarGuia.setOnClickListener(cvDescargarGuia_OnClick);
        /** Evento para borrar la cache del dispotisivo*/

        String dato = tipoUsuarioRol(session);

        if (dato.contains("ROLE_SUPER") || dato.contains("ROLE_ASESOR")) {
            cvBorrarCache.setOnClickListener(cvBorrarCache_OnClick);
            cvBorrarCache.setVisibility(View.VISIBLE);
        } else {
            cvBorrarCache.setVisibility(View.GONE);
        }

        try {
            if (session.getUser().get(8) != null) {
                JSONArray jsonModulos = new JSONArray(session.getUser().get(8));
                for (int i = 0; i < jsonModulos.length(); i++) {
                    JSONObject item = jsonModulos.getJSONObject(i);
                    String nombreItem = item.getString("nombre").toLowerCase();
                    Timber.tag(this.getClass().getSimpleName()).i(nombreItem);
                    switch (nombreItem) {
                        case "cartera": {
                            cvSincronizarFichas.setVisibility(View.VISIBLE);
                            break;
                        }
                        case "geolocalizar":
                            cvSincronizarGeolocalizaciones.setVisibility(View.VISIBLE);
                            break;
                        case "impresion":
                            cvSincronizarImpresiones.setVisibility(View.VISIBLE);
                            break;
                        case "ruta":
                            cvSincronizarRutas.setVisibility(View.VISIBLE);
                            break;
                        case "recuperacion agf":
                            cvSincronizarAgf.setVisibility(View.VISIBLE);
                            break;
                        case "recuperacion cc":
                            cvSincronizarCirculoCredito.setVisibility(View.VISIBLE);
                            break;
                        case "originacion":
                            cvSincronizarOriginacion.setVisibility(View.VISIBLE);
                            cvSincronizarEstadoSolicitudes.setVisibility(View.VISIBLE);
                            sincronizarColonias.setVisibility(View.VISIBLE);
                            sincronizarLocalidades.setVisibility(View.VISIBLE);
                            sincronizarMunicipios.setVisibility(View.VISIBLE);
                            cvCatSolicitud.setVisibility(View.VISIBLE);
                            break;
                        case "renovacion":
                            cvSincronizarRenovaciones.setVisibility(View.VISIBLE);
                            cvSincronizarIntegrantesRen.setVisibility(View.VISIBLE);
                            cvSincronizarEstadoSolicitudes.setVisibility(View.VISIBLE);
                            sincronizarColonias.setVisibility(View.VISIBLE);
                            sincronizarLocalidades.setVisibility(View.VISIBLE);
                            sincronizarMunicipios.setVisibility(View.VISIBLE);
                            cvCompletarIntegrantesRen.setVisibility(View.VISIBLE);
                            cvCatSolicitud.setVisibility(View.VISIBLE);
                            break;
                        case "solicitudes":
                            //menuGeneral.getItem(8).setVisible(true);
                            break;
                        case "verificacion domiciliaria":
                            cvSincronizarVerificacionesDomiciliarias.setVisibility(View.VISIBLE);
                            break;
                        case "sesiones":
                            //cvSincronizarGeolocalizaciones.setVisibility(View.GONE);
                            break;
                    }
                }

                int index = 1;
                for (int i = 0; i < llAppUpdateMenu.getChildCount(); i++) {
                    Object child = llAppUpdateMenu.getChildAt(i);
                    if (child instanceof CardView) {
                        CardView currentCardView = (CardView) child;
                        if (currentCardView.getVisibility() == View.VISIBLE) {
                            setMenuNumeration(currentCardView, index++);
                        }
                    }
                }


            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setMenuNumeration(CardView cardView, int index) {
        LinearLayout linearLayout = (LinearLayout) cardView.getChildAt(0);
        TextView textView = (TextView) linearLayout.getChildAt(1);
        String content = textView.getText().toString();
        String newText = String.format("%d .- %s", index, content);
        textView.setText(newText);
    }

    private View.OnClickListener cvCatSolicitud_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            /**Valida que se encuentre conectado a internet*/
            if (NetworkStatus.haveNetworkConnection(ctx)) {
                Handler handler_home = new Handler();
                handler_home.postDelayed(() -> {
                    /**Valida si esta la sesion activa*/
                    if (session.getUser().get(6).equals("true")) {
                        /**Procesos a enviar que esten en pendiente de envio*/
                        Sincronizar_Catalogos sc = new Sincronizar_Catalogos();
                        sc.GetCategoriasTickets(ctx);
                        sc.GetEstados(ctx);
                        sc.GetSectores(ctx);
                        sc.GetOcupaciones(ctx);
                        sc.GetMediosPagoOri(ctx);
                        sc.GetNivelesEstudios(ctx);
                        sc.GetEstadosCiviles(ctx);
                        sc.GetParentesco(ctx);
                        sc.GetTipoIdentificacion(ctx);
                        sc.GetViviendaTipos(ctx);
                        sc.GetMediosContacto(ctx);
                        sc.GetDestinosCredito(ctx);
                        sc.GetPlazosPrestamo(ctx);
                        sc.GetSucursalLocalidades(ctx);
                    }
                }, 3000);
            }
        }
    };

    private View.OnClickListener cvSicronizarCampanas_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Sincronizar_Catalogos catalogos = new Sincronizar_Catalogos();
            catalogos.GetCatalogosCampanas(ctx);
            catalogos.GetSucursalLocalidades(ctx);
        }
    };

    private View.OnClickListener cvDescargarGuia_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String valor = "guia_rapida.pdf";

            String result = tipoUsuarioRol(session);
            if (result.contains("ROLE_ASESOR") || result.contains("ROLE_GERENTEREGIONAL") || result.contains("ROLE_GERENTESUCURSAL") || result.contains("ROLE_GERENTECARTERAVENCIDA")) {
                valor = "guia_asesor.pdf";

            } else if (result.contains("ROLE_GESTOR")) {
                valor = "guia_rapida.pdf";
            }
            File guiaAsesor = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Documents/guia_asesor.pdf");
            File guiaRapida = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Documents/guia_rapida.pdf");

            if (guiaAsesor.exists() || guiaRapida.exists()) {
                Toast.makeText(ctx, "EL ARCHIVO YA EXISTE", Toast.LENGTH_SHORT).show();
            } else {

                try {
                    String base_url = "http://187.188.168.167:83/api/movil/descargarFile/" + valor + "/";
                    // session.getDominio().get(0) + session.getDominio().get(1) + WebServicesRoutes.CONTROLLER_DESCARGARGUIA+"/"+ valor+"/";

                    DownloadManager.Request request = new DownloadManager.Request(Uri.parse(base_url));
                    request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);

                    String texto = URLUtil.guessFileName(base_url, null, null);


                    request.setTitle(valor);
                    request.setDescription("Descargando el archivo por favor espera...");

                    String cookie = CookieManager.getInstance().getCookie(base_url);

                    request.allowScanningByMediaScanner();
                    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                    request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOCUMENTS, valor);

                    DownloadManager downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);

                    downloadManager.enqueue(request);

                    if (downloadManager != null) {
                        Toast.makeText(ctx, "Guía descargada", Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    Toast.makeText(ctx, "Error" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }
    };

    private String tipoUsuarioRol(SessionManager sen) {
        String tipoUser = " ";
        Home tipoRol = new Home();
        try {
            tipoUser = tipoRol.getTipoRolA(sen);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return tipoUser;
    }

    private View.OnClickListener cvBorrarCache_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            try {
                selectItemsDelete = new ArrayList<>();
                new AlertDialog.Builder(Configuracion.this)
                        .setTitle("Selecciona la cache a borrar")
                        .setMultiChoiceItems(_tablas, null, new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i, boolean isChecked) {
                                if (isChecked) {
                                    selectItemsDelete.add(i);
                                } else if (selectItemsDelete.contains(i)) {
                                    selectItemsDelete.remove(Integer.valueOf(i));
                                }
                            }
                        })
                        .setPositiveButton(R.string.accept, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int id) {
                                String tablas = " ";
                                Collections.sort(selectItemsDelete);
                                for (int i = 0; i < selectItemsDelete.size(); i++) {
                                    if (i == 0)
                                        tablas += _tablas[selectItemsDelete.get(i)];
                                    else
                                        tablas += ", " + _tablas[selectItemsDelete.get(i)];
                                }
                                int dato = deleteTable(tablas);
                                if (dato != 0) {
                                    Toast.makeText(ctx, "SE HA BORRADO LA CACHE", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(ctx, "NO SE HA BORRADO LA CACHE", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int id) {
                                Toast.makeText(ctx, "NO SE HA BORRADO LA CACHE", Toast.LENGTH_SHORT).show();
                            }
                        }).show();
            } catch (Exception e) {
                Toast.makeText(ctx, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    };

    private int deleteTable(String tablas) {
        String tablaDelete = tablas;
        Cursor eliminarTablet = null;
        int idTest = 1;//SIEMPRE SE ENVIA 1, DADO QUE 1, ES EL VALOR DE UN ESTATUS NO EVIADO
        int dato = 0;
        int validacion = 0;
        validacion = Miscellaneous.validarEstatus(tablas, ctx);

        if (validacion > 0) {
            dato = 0;
        } else {
           /* if(tablas.contains("tb_tabla_test")){
                eliminarTablet = dBhelper.deleteData(tablaDelete,"_id=?",new String[]{String.valueOf(idTest)});
                if(eliminarTablet.getCount()==0){
                    dato = 1; //EL DATO HA SIDO BORRADO
                }else{
                    dato = 0;//EL DATO NO FUE BORRADO
                }
            }else */
            if (tablas.contains(TBL_RECUPERACION_RECIBOS_CC)) {
                eliminarTablet = dBhelper.deleteData(tablaDelete, new String[]{String.valueOf(idTest)});

                if (eliminarTablet.getCount() == 0) {
                    dato = 1;
                } else {
                    dato = 0;
                }
            }
            if (tablas.contains(TBL_RECIBOS_AGF_CC)) {
                eliminarTablet = dBhelper.deleteData(tablaDelete, new String[]{String.valueOf(idTest)});
                if (eliminarTablet.getCount() == 0) {
                    dato = 1;
                } else {
                    dato = 0;
                }
            }
        }
        dato += dato;

        return dato;
    }

    /**
     * Evento para sincronizar lo que esta pendiente de envio
     */
    private View.OnClickListener cvSincronizarFichas_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            /**Valida que se encuentre conectado a internet*/
            if (NetworkStatus.haveNetworkConnection(ctx)) {
                Log.e("SYNCRO", session.getUser().get(6));
                /**Deshabilita el boton para no repetir el envio*/
                cvSincronizarFichas.setEnabled(false);
                /**Hilo para deshabilitar el boton por 3 segundos y despues habilitarlo*/
                Handler handler_home = new Handler();
                handler_home.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        /**Valida si esta la sesion activa*/
                        if (session.getUser().get(6).equals("true")) {
                            /**Genera un registro para log de sincronizacion*/
                            HashMap<Integer, String> params_sincro = new HashMap<>();
                            params_sincro.put(0, session.getUser().get(0));
                            params_sincro.put(1, Miscellaneous.ObtenerFecha("timestamp"));
                            dBhelper.saveSincronizado(db, Constants.SINCRONIZADO_T, params_sincro);

                            /**Procesos a enviar que esten en pendiente de envio*/
                            Servicios_Sincronizado ss = new Servicios_Sincronizado();
                            /**Envia cierres de dia que esten en pendiente de envio*/
                            ss.SaveCierreDia(ctx, false);
                            /**Envia geolocalizaciones que esten en pendiente de envio*/
                            //-ss.SaveGeolocalizacion(ctx, true);
                            /**Envia impresiones que esten en pendiente de envio*/
                            //-ss.SendImpresionesVi(ctx, true);
                            /**Envia reimpresiones que esten en pendiente de envio*/
                            //-ss.SendReimpresionesVi(ctx, true);
                            /**Envia la ubicacion del asesor que esten en pendiente de envio*/
                            ss.SendTracker(ctx, false);
                            /**Envia los reportes de mesa de ayuda que esten en pendiente de envio*/
                            ss.GetTickets(ctx, false);

                            /**Envia respuestas de VIGENTE, COBRANZA y VENCIDA de individual y grupal que esten en pendiente de envio*/
                            ss.SaveRespuestaGestion(ctx, true);

                            /**Esta funciones son de originacion y renovacion*/
                            /**------------------------------------------------*/
                            /**Envia la solicitudes que ya fueron autorizadas por el gerente colocando el monto autorizado*/
                            //-ss.MontoAutorizado(ctx, false);
                            /**Envia las solicitudes de originacion individual*/
                            //-ss.SendOriginacionInd(ctx, false);
                            /**Envia las solicitudes de originacion grupal*/
                            //-ss.SendOriginacionGpo(ctx, false);
                            /**Envia las solicitudes de renovacion individual*/
                            //-ss.SendRenovacionInd(ctx, false);
                            /**Envia las solicitudes de renovacion grupal*/
                            //-ss.SendRenovacionGpo(ctx, false);
                            /**Obtiene las solicitudes de originacion y renovacion individual que fueron rechazadas por la admin*/
                            //--ss.GetSolicitudesRechazadasInd(ctx, false);
                            /**Obtiene las solicitudes de originacion y renovacion grupal que fueron rechazadas por la admin*/
                            //--ss.GetSolicitudesRechazadasGpo(ctx, false);
                            /**OBTIENE LOS ESTATUS DE LAS SOLICITUDES**/
                            //--ss.GetSolicitudesEstatusInd(ctx, false);
                            //--ss.GetSolicitudesEstatusGpo(ctx, false);

                            /**Envia las consultas Realizadas de circulo de credito*/
                            //-ss.SendConsultaCC(ctx, false);
                            /**Envia las gestiones de cobros en efectivo de AGF y CC*/
                            //--ss.SendRecibos(ctx, false);
                            /**Obtiene el ultimo folio de impresiones de AGF y CC*/
                            //-ss.GetUltimosRecibos(ctx);
                            /**Obtiene los prestamos autorizados y vigentes para agf**/
                            //-ss.GetPrestamos(ctx, false);

                            /**Funciones de Cancelancion de gestiones y obtencion de respuesta de cancelacion ya no se ocuparon*/
                            //-ss.CancelGestiones(ctx, true);
                            //-ss.SendCancelGestiones(ctx, true);

                            //-ss.GetGestionesVerDom(ctx, true);
                            //-ss.SendGestionesVerDom(ctx, true);

                            /*BUSCAR Y REGISTRAR CIERRE DE DIA*/
                            Log.e("AQUI CIERRE DE DIA", session.getUser().get(0));
                            //if(session.getUser().get(0).equals("131"))
                            /*if(false)
                            {
                                GestionIndividualDao gestionIndividualDao = new GestionIndividualDao(ctx);
                                CierreDeDiaDao cierreDeDiaDao = new CierreDeDiaDao(ctx);
                                ImpresionVencidaDao impresionVencidaDao = new ImpresionVencidaDao(ctx);

                                GestionIndividual gestionIndividual = gestionIndividualDao.findByFolioAndIdPrestamo("112", "323557");
                                com.sidert.sidertmovil.models.cierrededia.CierreDeDia cierreDeDia = null;
                                ImpresionVencida impresionVencida = null;

                                if(gestionIndividual == null)
                                {
                                    gestionIndividual = new GestionIndividual();

                                    gestionIndividual.setIdPrestamo("323557");
                                    gestionIndividual.setLatitud("19.1977874");
                                    gestionIndividual.setLongitud("-96.2862257");
                                    gestionIndividual.setContacto("NO");
                                    gestionIndividual.setComentario("");
                                    gestionIndividual.setActualizarTelefono("NO");
                                    gestionIndividual.setNuevoTelefono("");
                                    gestionIndividual.setResultadoGestion("PAGO");
                                    gestionIndividual.setMotivoNoPago("");
                                    gestionIndividual.setFechaFallecimiento("");
                                    gestionIndividual.setFechaMontoPromesa("");
                                    gestionIndividual.setMontoPromesa("");
                                    gestionIndividual.setMedioPago("EFECTIVO");
                                    gestionIndividual.setFechaPago("2021-11-18 11:22:30");
                                    gestionIndividual.setPagaraRequerido("NO");
                                    gestionIndividual.setPagoRealizado("500");
                                    gestionIndividual.setImprimirRecibo("SI");
                                    gestionIndividual.setFolio("112");
                                    gestionIndividual.setEvidencia("bc0d8e78-4b3e-4ed2-be96-a6cfc4bb706b.jpg");
                                    gestionIndividual.setTipoImagen("FOTOGRAFIA");
                                    gestionIndividual.setGerente("NO");
                                    gestionIndividual.setFirma("");
                                    gestionIndividual.setFechaInicio("2021-12-21 11:22:22");
                                    gestionIndividual.setFechaFin("2021-12-21 11:27:41");
                                    gestionIndividual.setFechaEnvio("2021-12-21 11:27:43");
                                    gestionIndividual.setEstatus("2");
                                    gestionIndividual.setResImpresion("0");
                                    gestionIndividual.setEstatusPago("");
                                    gestionIndividual.setSaldoCorte("");
                                    gestionIndividual.setSaldoActual("");
                                    gestionIndividual.setDiasAtraso("825");
                                    gestionIndividual.setSerialId("20211221112222-599-983876-L007-1037774");

                                    gestionIndividual.setId(Integer.parseInt(gestionIndividualDao.store(gestionIndividual).toString()));

                                    impresionVencida = impresionVencidaDao.findByNumPrestamoAndCreateAt("983876-L007", "2021-12-21 11:23:19");

                                    if(impresionVencida == null) {
                                        impresionVencida = new ImpresionVencida();

                                        impresionVencida.setNumPrestamoIdGestion("983876-L007-" + gestionIndividual.getId());
                                        impresionVencida.setAsesorId(session.getUser().get(0));
                                        impresionVencida.setFolio(112);
                                        impresionVencida.setTipoImpresion("O");
                                        impresionVencida.setMonto("500");
                                        impresionVencida.setClaveCliente("1037774");
                                        impresionVencida.setCreateAt("2021-12-21 11:23:19");
                                        impresionVencida.setSentAt("2021-12-21 11:23:18");
                                        impresionVencida.setEstatus(1);
                                        impresionVencida.setNumPrestamo("983876-L007");
                                        impresionVencida.setCelular("");

                                        impresionVencida.setId(Integer.parseInt(impresionVencidaDao.store(impresionVencida).toString()));

                                        cierreDeDia = cierreDeDiaDao.findByIdRespuestaAndAsesorId("0", session.getUser().get(0));
                                    }

                                    if(cierreDeDia == null)
                                    {
                                        cierreDeDia = new CierreDeDia();
                                        cierreDeDia.setAsesorId(session.getUser().get(0));
                                        cierreDeDia.setIdRespuesta(gestionIndividual.getId().toString());
                                        cierreDeDia.setNumPrestamo("983876-L007");
                                        cierreDeDia.setClaveCliente("1037774");
                                        cierreDeDia.setMedioPago("");
                                        cierreDeDia.setMontoDepositado("");
                                        cierreDeDia.setEvidencia("");
                                        cierreDeDia.setTipoCierre("INDIVIDUAL");
                                        cierreDeDia.setTipoPrestamo("VENCIDA");
                                        cierreDeDia.setFechaInicio("");
                                        cierreDeDia.setFechaFin("");
                                        cierreDeDia.setFechaEnvio("");
                                        cierreDeDia.setEstatus(0);
                                        cierreDeDia.setNombre("EVANGELINA HERNANDEZ DEL RIO");
                                        cierreDeDia.setSerialId("");

                                        cierreDeDiaDao.store(cierreDeDia);
                                    }
                                }


                            }*/

                        }
                        cvSincronizarFichas.setEnabled(true);
                    }
                }, 3000);

            } else {
                /**No tiene conexion a internet */
                AlertDialog error_connect = Popups.showDialogMessage(ctx, Constants.not_network,
                        R.string.not_network, R.string.accept, new Popups.DialogMessage() {
                            @Override
                            public void OnClickListener(AlertDialog dialog) {
                                dialog.dismiss();
                            }
                        });
                Objects.requireNonNull(error_connect.getWindow()).requestFeature(Window.FEATURE_NO_TITLE);
                error_connect.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                error_connect.show();
            }
        }

    };

    private View.OnClickListener cvSincronizarGeolocalizaciones_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            /**Valida que se encuentre conectado a internet*/
            if (NetworkStatus.haveNetworkConnection(ctx)) {
                cvSincronizarGeolocalizaciones.setEnabled(false);

                Handler handler_home = new Handler();
                handler_home.postDelayed(() -> {
                    /**Valida si esta la sesion activa*/
                    if (session.getUser().get(6).equals("true")) {
                        HashMap<Integer, String> params_sincro = new HashMap<>();
                        params_sincro.put(0, session.getUser().get(0));
                        params_sincro.put(1, Miscellaneous.ObtenerFecha("timestamp"));
                        dBhelper.saveSincronizado(db, Constants.SINCRONIZADO_T, params_sincro);

                        /**Procesos a enviar que esten en pendiente de envio*/
                        Servicios_Sincronizado ss = new Servicios_Sincronizado();

                        ss.SaveGeolocalizacion(ctx, true);
                    }
                    cvSincronizarGeolocalizaciones.setEnabled(true);
                }, 3000);

            } else {
                /**No tiene conexion a internet */
                AlertDialog error_connect = Popups.showDialogMessage(ctx, Constants.not_network,
                        R.string.not_network, R.string.accept, new Popups.DialogMessage() {
                            @Override
                            public void OnClickListener(AlertDialog dialog) {
                                dialog.dismiss();
                            }
                        });
                Objects.requireNonNull(error_connect.getWindow()).requestFeature(Window.FEATURE_NO_TITLE);
                error_connect.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                error_connect.show();
            }
        }

    };

    private View.OnClickListener cvSincronizarImpresiones_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            /**Valida que se encuentre conectado a internet*/
            if (NetworkStatus.haveNetworkConnection(ctx)) {
                cvSincronizarImpresiones.setEnabled(false);

                Handler handler_home = new Handler();
                handler_home.postDelayed(() -> {
                    /**Valida si esta la sesion activa*/
                    if (session.getUser().get(6).equals("true")) {
                        HashMap<Integer, String> params_sincro = new HashMap<>();
                        params_sincro.put(0, session.getUser().get(0));
                        params_sincro.put(1, Miscellaneous.ObtenerFecha("timestamp"));
                        dBhelper.saveSincronizado(db, Constants.SINCRONIZADO_T, params_sincro);

                        /**Procesos a enviar que esten en pendiente de envio*/
                        Servicios_Sincronizado ss = new Servicios_Sincronizado();

                        ss.SendImpresionesVi(ctx, true);
                        ss.SendReimpresionesVi(ctx, true);
                    }
                    cvSincronizarImpresiones.setEnabled(true);
                }, 3000);

            } else {
                /**No tiene conexion a internet */
                AlertDialog error_connect = Popups.showDialogMessage(ctx, Constants.not_network,
                        R.string.not_network, R.string.accept, new Popups.DialogMessage() {
                            @Override
                            public void OnClickListener(AlertDialog dialog) {
                                dialog.dismiss();
                            }
                        });
                Objects.requireNonNull(error_connect.getWindow()).requestFeature(Window.FEATURE_NO_TITLE);
                error_connect.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                error_connect.show();
            }
        }

    };

    private View.OnClickListener cvSincronizarRutas_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            /**Valida que se encuentre conectado a internet*/
            if (NetworkStatus.haveNetworkConnection(ctx)) {
                cvSincronizarRutas.setEnabled(false);

                Handler handler_home = new Handler();
                handler_home.postDelayed(() -> {
                    /**Valida si esta la sesion activa*/
                    if (session.getUser().get(6).equals("true")) {
                        HashMap<Integer, String> params_sincro = new HashMap<>();
                        params_sincro.put(0, session.getUser().get(0));
                        params_sincro.put(1, Miscellaneous.ObtenerFecha("timestamp"));
                        dBhelper.saveSincronizado(db, Constants.SINCRONIZADO_T, params_sincro);

                        /**Procesos a enviar que esten en pendiente de envio*/
                        Servicios_Sincronizado ss = new Servicios_Sincronizado();

                        ss.SendTracker(ctx, true);
                    }
                    cvSincronizarRutas.setEnabled(true);
                }, 3000);

            } else {
                /**No tiene conexion a internet */
                AlertDialog error_connect = Popups.showDialogMessage(ctx, Constants.not_network,
                        R.string.not_network, R.string.accept, new Popups.DialogMessage() {
                            @Override
                            public void OnClickListener(AlertDialog dialog) {
                                dialog.dismiss();
                            }
                        });
                Objects.requireNonNull(error_connect.getWindow()).requestFeature(Window.FEATURE_NO_TITLE);
                error_connect.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                error_connect.show();
            }
        }

    };

    private View.OnClickListener cvSincronizarAgf_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            /**Valida que se encuentre conectado a internet*/
            if (NetworkStatus.haveNetworkConnection(ctx)) {
                cvSincronizarAgf.setEnabled(false);

                Handler handler_home = new Handler();
                handler_home.postDelayed(() -> {
                    /**Valida si esta la sesion activa*/
                    if (session.getUser().get(6).equals("true")) {
                        HashMap<Integer, String> params_sincro = new HashMap<>();
                        params_sincro.put(0, session.getUser().get(0));
                        params_sincro.put(1, Miscellaneous.ObtenerFecha("timestamp"));
                        dBhelper.saveSincronizado(db, Constants.SINCRONIZADO_T, params_sincro);

                        /**Procesos a enviar que esten en pendiente de envio*/
                        Servicios_Sincronizado ss = new Servicios_Sincronizado();

                        ss.GetUltimosRecibos(ctx);
                        ss.GetPrestamos(ctx, false);
                        ss.SendRecibos(ctx, false);
                    }
                    cvSincronizarAgf.setEnabled(true);
                }, 3000);

            } else {
                /**No tiene conexion a internet */
                AlertDialog error_connect = Popups.showDialogMessage(ctx, Constants.not_network,
                        R.string.not_network, R.string.accept, new Popups.DialogMessage() {
                            @Override
                            public void OnClickListener(AlertDialog dialog) {
                                dialog.dismiss();
                            }
                        });
                Objects.requireNonNull(error_connect.getWindow()).requestFeature(Window.FEATURE_NO_TITLE);
                error_connect.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                error_connect.show();
            }
        }

    };

    private View.OnClickListener cvSincronizarCirculoCredito_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            /**Valida que se encuentre conectado a internet*/
            if (NetworkStatus.haveNetworkConnection(ctx)) {
                cvSincronizarCirculoCredito.setEnabled(false);

                Handler handler_home = new Handler();
                handler_home.postDelayed(() -> {
                    /**Valida si esta la sesion activa*/
                    if (session.getUser().get(6).equals("true")) {
                        HashMap<Integer, String> params_sincro = new HashMap<>();
                        params_sincro.put(0, session.getUser().get(0));
                        params_sincro.put(1, Miscellaneous.ObtenerFecha("timestamp"));
                        dBhelper.saveSincronizado(db, Constants.SINCRONIZADO_T, params_sincro);

                        /**Procesos a enviar que esten en pendiente de envio*/
                        Servicios_Sincronizado ss = new Servicios_Sincronizado();

                        ss.SendConsultaCC(ctx, false);
                        ss.SendRecibos(ctx, false);
                    }
                    cvSincronizarCirculoCredito.setEnabled(true);
                }, 3000);

            } else {
                /**No tiene conexion a internet */
                AlertDialog error_connect = Popups.showDialogMessage(ctx, Constants.not_network,
                        R.string.not_network, R.string.accept, new Popups.DialogMessage() {
                            @Override
                            public void OnClickListener(AlertDialog dialog) {
                                dialog.dismiss();
                            }
                        });
                Objects.requireNonNull(error_connect.getWindow()).requestFeature(Window.FEATURE_NO_TITLE);
                error_connect.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                error_connect.show();
            }
        }

    };

    private View.OnClickListener cvSincronizarOriginacion_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            /**Valida que se encuentre conectado a internet*/
            if (NetworkStatus.haveNetworkConnection(ctx)) {
                cvSincronizarOriginacion.setEnabled(false);

                Handler handler_home = new Handler();
                handler_home.postDelayed(() -> {
                    /**Valida si esta la sesion activa*/
                    if (session.getUser().get(6).equals("true")) {

                        //ReactivarOriginacionGpo("402", "LA ESPERANZA");
                        //ReactivarOriginacionGpo("402", "LA COLONIA");


                        /*
                            if(session.getUser().get(0).equals("582")){
                            SolicitudDao solicitudDao = new SolicitudDao(ctx);
                            Solicitud solicitud = solicitudDao.findLikeNombre("CRISANTEMOS DE CAMARON");

                            if (solicitud != null) {
                                CreditoGpoDao creditoGpoDao = new CreditoGpoDao(ctx);
                                CreditoGpo creditoGpo = creditoGpoDao.findByIdSolicitud(solicitud.getIdSolicitud());

                                if(creditoGpo != null)
                                {
                                    IntegranteGpoDao integranteGpoDao = new IntegranteGpoDao(ctx);
                                    List<IntegranteGpo> integranteGpoList = integranteGpoDao.findAllByIdCredito(creditoGpo.getId());

                                    if(integranteGpoList.size() > 0)
                                    {
                                        for(IntegranteGpo iGpo : integranteGpoList)
                                        {
                                            iGpo.setEstatusCompletado(0);
                                            iGpo.setEstatusRechazo(0);
                                            integranteGpoDao.updateEstatus(iGpo);
                                            integranteGpoDao.saveEstatus(iGpo);
                                        }
                                    }

                                    solicitud.setEstatus(0);
                                    solicitudDao.updateEstatus(solicitud);
                                }
                            }
                        }
                        */
                        HashMap<Integer, String> params_sincro = new HashMap<>();
                        params_sincro.put(0, session.getUser().get(0));
                        params_sincro.put(1, Miscellaneous.ObtenerFecha("timestamp"));
                        dBhelper.saveSincronizado(db, Constants.SINCRONIZADO_T, params_sincro);

                        /**Procesos a enviar que esten en pendiente de envio*/
                        Servicios_Sincronizado ss = new Servicios_Sincronizado();

                        ss.SendOriginacionInd(ctx, false);
                        ss.SendOriginacionGpo(ctx, false);
                        ss.CancelGestiones(ctx, true);
                        ss.SendCancelGestiones(ctx, true);
                    }
                    cvSincronizarOriginacion.setEnabled(true);
                }, 3000);

            } else {
                /**No tiene conexion a internet */
                AlertDialog error_connect = Popups.showDialogMessage(ctx, Constants.not_network,
                        R.string.not_network, R.string.accept, new Popups.DialogMessage() {
                            @Override
                            public void OnClickListener(AlertDialog dialog) {
                                dialog.dismiss();
                            }
                        });
                Objects.requireNonNull(error_connect.getWindow()).requestFeature(Window.FEATURE_NO_TITLE);
                error_connect.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                error_connect.show();
            }
        }

    };

    private View.OnClickListener cvSincronizarRenovaciones_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            /**Valida que se encuentre conectado a internet*/
            if (NetworkStatus.haveNetworkConnection(ctx)) {
                cvSincronizarRenovaciones.setEnabled(false);

                Servicios_Sincronizado ss = new Servicios_Sincronizado();

                ss.GetPrestamosToRenovar(ctx);

                Handler handler_home = new Handler();
                handler_home.postDelayed(() -> {
                    /**Valida si esta la sesion activa*/
                    if (session.getUser().get(6).equals("true")) {
                        HashMap<Integer, String> params_sincro = new HashMap<>();
                        params_sincro.put(0, session.getUser().get(0));
                        params_sincro.put(1, Miscellaneous.ObtenerFecha("timestamp"));
                        dBhelper.saveSincronizado(db, Constants.SINCRONIZADO_T, params_sincro);

                        /**Procesos a enviar que esten en pendiente de envio*/


                    }
                    cvSincronizarRenovaciones.setEnabled(true);
                }, 3000);

            } else {
                /**No tiene conexion a internet */
                AlertDialog error_connect = Popups.showDialogMessage(ctx, Constants.not_network,
                        R.string.not_network, R.string.accept, new Popups.DialogMessage() {
                            @Override
                            public void OnClickListener(AlertDialog dialog) {
                                dialog.dismiss();
                            }
                        });
                Objects.requireNonNull(error_connect.getWindow()).requestFeature(Window.FEATURE_NO_TITLE);
                error_connect.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                error_connect.show();
            }
        }

    };
    //OBTENER RENOVACIONES
    private View.OnClickListener cvSincronizarIntegrantesRen_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            /**Valida que se encuentre conectado a internet*/
            if (NetworkStatus.haveNetworkConnection(ctx)) {
                cvSincronizarIntegrantesRen.setEnabled(false);

                /*if(session.getUser().get(0).equals("013"))
                {
                    PrestamoToRenovarDao prestamoToRenovarDao = new PrestamoToRenovarDao(ctx);
                    SolicitudRenDao solicitudRenDao = new SolicitudRenDao(ctx);

                    PrestamoToRenovar prestamoToRenovar = prestamoToRenovarDao.findLikeClienteNombre("LUISA OLIVARES CALLEJAS");
                    if(prestamoToRenovar != null)
                    {
                        prestamoToRenovar.setClienteNombre("LUISA OLIVARES CALLEJA");
                        prestamoToRenovarDao.update(prestamoToRenovar);
                    }

                    SolicitudRen solicitudRen = solicitudRenDao.findLikeNombre("LUISA OLIVARES CALLEJAS");
                    if(solicitudRen != null)
                    {
                        solicitudRen.setNombre("LUISA OLIVARES CALLEJA");
                        solicitudRenDao.update(solicitudRen);
                    }
                }*/

                Handler handler_home = new Handler();
                handler_home.postDelayed(() -> {
                    /**Valida si esta la sesion activa*/
                    if (session.getUser().get(6).equals("true")) {
                        HashMap<Integer, String> params_sincro = new HashMap<>();
                        params_sincro.put(0, session.getUser().get(0));
                        params_sincro.put(1, Miscellaneous.ObtenerFecha("timestamp"));
                        dBhelper.saveSincronizado(db, Constants.SINCRONIZADO_T, params_sincro);

                        /**Procesos a enviar que esten en pendiente de envio*/
                        Servicios_Sincronizado ss = new Servicios_Sincronizado();

                        //ReactivarRenovacionGpo("436", "LAS HIJAS DE LAS COMADRITAS 2435", "2022-07-29");
                        //ReactivarRenovacionGpo("402", "LA ESPERANZA 4075", "2022-07-21");
                        //ReactivarRenovacionGpo("134", "LOMAS VERDES 3821", "2022-07-26");
                        //ReactivarRenovacionGpo("082", "LAS DESALMADAS 3549", "2022-07-21");

                        ///ReactivarRenovacionGpo("310", "FE 3602", "2022-07-28");

                        /*
                        if (session.getUser().get(0).equals("403"))
                        {
                            PrestamoToRenovarDao prestamoToRenovarDao = new PrestamoToRenovarDao(ctx);
                            PrestamoToRenovar prestamoToRenovar = prestamoToRenovarDao.findLikeClienteNombreAndFechaVencimiento("MORALILLO", "2021-12-14");

                            if(prestamoToRenovar != null) {
                                SolicitudRenDao solicitudRenDao = new SolicitudRenDao(ctx);
                                SolicitudRen solicitudRen = solicitudRenDao.findLikeNombreAndPrestamoId("MORALILLO", prestamoToRenovar.getPrestamoId());

                                if (solicitudRen != null) {
                                    CreditoGpoRenDao creditoGpoRenDao = new CreditoGpoRenDao(ctx);
                                    CreditoGpoRen creditoGpoRen = creditoGpoRenDao.findByIdSolicitud(solicitudRen.getIdSolicitud());

                                    if(creditoGpoRen != null)
                                    {
                                        IntegranteGpoRenDao integranteGpoRenDao = new IntegranteGpoRenDao(ctx);
                                        List<IntegranteGpoRen> integranteGpoRenList = integranteGpoRenDao.findAllByIdCredito(creditoGpoRen.getId());

                                        if(integranteGpoRenList.size() > 0)
                                        {
                                            for(IntegranteGpoRen iGpoRen : integranteGpoRenList)
                                            {
                                                iGpoRen.setEstatusCompletado(2);
                                                iGpoRen.setEstatusRechazo(2);
                                                integranteGpoRenDao.updateEstatus(iGpoRen);
                                                integranteGpoRenDao.saveEstatus(iGpoRen);
                                            }
                                        }

                                        solicitudRen.setEstatus(2);
                                        solicitudRenDao.updateEstatus(solicitudRen);
                                    }
                                }
                            }

                            prestamoToRenovar = prestamoToRenovarDao.findLikeClienteNombreAndFechaVencimiento("MORALILLO", "2022-07-12");

                            if(prestamoToRenovar != null) {
                                SolicitudRenDao solicitudRenDao = new SolicitudRenDao(ctx);
                                SolicitudRen solicitudRen = solicitudRenDao.findLikeNombreAndPrestamoId("MORALILLO", prestamoToRenovar.getPrestamoId());

                                if (solicitudRen != null) {
                                    CreditoGpoRenDao creditoGpoRenDao = new CreditoGpoRenDao(ctx);
                                    CreditoGpoRen creditoGpoRen = creditoGpoRenDao.findByIdSolicitud(solicitudRen.getIdSolicitud());

                                    if(creditoGpoRen != null)
                                    {
                                        IntegranteGpoRenDao integranteGpoRenDao = new IntegranteGpoRenDao(ctx);
                                        List<IntegranteGpoRen> integranteGpoRenList = integranteGpoRenDao.findAllByIdCredito(creditoGpoRen.getId());

                                        if(integranteGpoRenList.size() > 0)
                                        {
                                            for(IntegranteGpoRen iGpoRen : integranteGpoRenList)
                                            {
                                                iGpoRen.setEstatusCompletado(0);
                                                iGpoRen.setEstatusRechazo(0);
                                                integranteGpoRenDao.updateEstatus(iGpoRen);
                                                integranteGpoRenDao.saveEstatus(iGpoRen);
                                            }
                                        }

                                        solicitudRen.setEstatus(0);
                                        solicitudRenDao.updateEstatus(solicitudRen);
                                    }
                                }
                            }
                        }*/

                        /*
                        if(session.getUser().get(0).equals("617"))
                        {
                            PrestamoToRenovarDao prestamoToRenovarDao = new PrestamoToRenovarDao(ctx);
                            PrestamoToRenovar prestamoToRenovar = prestamoToRenovarDao.findLikeClienteNombre("ACELERADA");

                            if(prestamoToRenovar != null) {
                                SolicitudRenDao solicitudRenDao = new SolicitudRenDao(ctx);
                                SolicitudRen solicitudRen = solicitudRenDao.findLikeNombre("ACELERADA");
                                if (solicitudRen != null) {
                                    solicitudRen.setPrestamoId(prestamoToRenovar.getPrestamoId());
                                    solicitudRenDao.updatePrestamo(solicitudRen);
                                }
                            }

                        }*/
                        /*
                        if (session.getUser().get(0).equals("015"))
                        {
                            PrestamoToRenovarDao prestamoToRenovarDao = new PrestamoToRenovarDao(ctx);
                            PrestamoToRenovar prestamoToRenovar = prestamoToRenovarDao.findLikeClienteNombre("LAS INIGUALABLES");

                            if(prestamoToRenovar != null) {
                                SolicitudRenDao solicitudRenDao = new SolicitudRenDao(ctx);
                                SolicitudRen solicitudRen = solicitudRenDao.findLikeNombre("LAS INIGUALABLES");

                                if (solicitudRen != null) {
                                    CreditoGpoRenDao creditoGpoRenDao = new CreditoGpoRenDao(ctx);
                                    CreditoGpoRen creditoGpoRen = creditoGpoRenDao.findByIdSolicitud(solicitudRen.getIdSolicitud());

                                    if(creditoGpoRen != null)
                                    {
                                        IntegranteGpoRenDao integranteGpoRenDao = new IntegranteGpoRenDao(ctx);
                                        List<IntegranteGpoRen> integranteGpoRenList = integranteGpoRenDao.findAllByIdCredito(creditoGpoRen.getId());

                                        if(integranteGpoRenList.size() > 0)
                                        {
                                            for(IntegranteGpoRen iGpoRen : integranteGpoRenList)
                                            {
                                                iGpoRen.setEstatusCompletado(0);
                                                iGpoRen.setEstatusRechazo(0);
                                                integranteGpoRenDao.updateEstatus(iGpoRen);
                                                integranteGpoRenDao.saveEstatus(iGpoRen);
                                            }
                                        }

                                        solicitudRen.setEstatus(0);
                                        solicitudRenDao.updateEstatus(solicitudRen);
                                    }
                                }
                            }
                        }
                        */

                        /*if(session.getUser().get(0).equals("403"))
                        {
                            PrestamoToRenovarDao prestamoToRenovarDao = new PrestamoToRenovarDao(ctx);
                            PrestamoToRenovar prestamoToRenovar = prestamoToRenovarDao.findLikeClienteNombre("SALVADOR AVILA VERA");

                            if(prestamoToRenovar != null) {
                                SolicitudRenDao solicitudRenDao = new SolicitudRenDao(ctx);
                                SolicitudRen solicitudRen = solicitudRenDao.findLikeNombre("SALVADOR AVILA VERA");
                                if (solicitudRen != null) {
                                    solicitudRen.setPrestamoId(prestamoToRenovar.getPrestamoId());
                                    solicitudRenDao.updatePrestamo(solicitudRen);
                                }
                            }

                        }*/

                        //ss.GetPrestamosToRenovar(ctx);
                        //new RegistrarDatosRenovacion(ctx).execute();
                        //new RegistrarDatosRenovacion(ctx).execute();
                    }
                    cvSincronizarIntegrantesRen.setEnabled(true);
                }, 3000);

            } else {
                /**No tiene conexion a internet */
                AlertDialog error_connect = Popups.showDialogMessage(ctx, Constants.not_network,
                        R.string.not_network, R.string.accept, new Popups.DialogMessage() {
                            @Override
                            public void OnClickListener(AlertDialog dialog) {
                                dialog.dismiss();
                            }
                        });
                Objects.requireNonNull(error_connect.getWindow()).requestFeature(Window.FEATURE_NO_TITLE);
                error_connect.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                error_connect.show();
            }
        }

    };

    private View.OnClickListener cvCompletarIntegrantesRen_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            /**Valida que se encuentre conectado a internet*/
            if (NetworkStatus.haveNetworkConnection(ctx)) {
                cvCompletarIntegrantesRen.setEnabled(false);

                Handler handler_home = new Handler();
                handler_home.postDelayed(() -> {
                    /**Valida si esta la sesion activa*/
                    if (session.getUser().get(6).equals("true")) {
                        HashMap<Integer, String> params_sincro = new HashMap<>();
                        params_sincro.put(0, session.getUser().get(0));
                        params_sincro.put(1, Miscellaneous.ObtenerFecha("timestamp"));
                        dBhelper.saveSincronizado(db, Constants.SINCRONIZADO_T, params_sincro);

                        /**Procesos a enviar que esten en pendiente de envio*/
                        Servicios_Sincronizado ss = new Servicios_Sincronizado();
                        ss.GetPrestamosToRenovarForce(ctx);
                    }
                    cvCompletarIntegrantesRen.setEnabled(true);
                }, 3000);

            } else {
                /**No tiene conexion a internet */
                AlertDialog error_connect = Popups.showDialogMessage(ctx, Constants.not_network,
                        R.string.not_network, R.string.accept, new Popups.DialogMessage() {
                            @Override
                            public void OnClickListener(AlertDialog dialog) {
                                dialog.dismiss();
                            }
                        });
                Objects.requireNonNull(error_connect.getWindow()).requestFeature(Window.FEATURE_NO_TITLE);
                error_connect.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                error_connect.show();
            }
        }

    };

    private View.OnClickListener cvSincronizarEstadoSolicitudes_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            /**Valida que se encuentre conectado a internet*/
            if (NetworkStatus.haveNetworkConnection(ctx)) {
                cvSincronizarEstadoSolicitudes.setEnabled(false);

                Handler handler_home = new Handler();
                handler_home.postDelayed(() -> {
                    /**Valida si esta la sesion activa*/
                    if (session.getUser().get(6).equals("true")) {
                        HashMap<Integer, String> params_sincro = new HashMap<>();
                        params_sincro.put(0, session.getUser().get(0));
                        params_sincro.put(1, Miscellaneous.ObtenerFecha("timestamp"));
                        dBhelper.saveSincronizado(db, Constants.SINCRONIZADO_T, params_sincro);

                        /**Procesos a enviar que esten en pendiente de envio*/
                        Servicios_Sincronizado ss = new Servicios_Sincronizado();

                        ss.GetSolicitudesRechazadasInd(ctx, false);
                        ss.GetSolicitudesRechazadasGpo(ctx, false);
                        ss.GetSolicitudesEstatusInd(ctx, false);
                        ss.GetSolicitudesEstatusGpo(ctx, false);
                        ss.MontoAutorizado(ctx, false);
                    }
                    cvSincronizarEstadoSolicitudes.setEnabled(true);
                }, 3000);

            } else {
                /**No tiene conexion a internet */
                AlertDialog error_connect = Popups.showDialogMessage(ctx, Constants.not_network,
                        R.string.not_network, R.string.accept, new Popups.DialogMessage() {
                            @Override
                            public void OnClickListener(AlertDialog dialog) {
                                dialog.dismiss();
                            }
                        });
                Objects.requireNonNull(error_connect.getWindow()).requestFeature(Window.FEATURE_NO_TITLE);
                error_connect.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                error_connect.show();
            }
        }

    };

    private View.OnClickListener cvSincronizarVerificacionesDomiciliarias_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            /**Valida que se encuentre conectado a internet*/
            if (NetworkStatus.haveNetworkConnection(ctx)) {
                cvSincronizarVerificacionesDomiciliarias.setEnabled(false);

                Handler handler_home = new Handler();
                handler_home.postDelayed(() -> {
                    /**Valida si esta la sesion activa*/
                    if (session.getUser().get(6).equals("true")) {
                        HashMap<Integer, String> params_sincro = new HashMap<>();
                        params_sincro.put(0, session.getUser().get(0));
                        params_sincro.put(1, Miscellaneous.ObtenerFecha("timestamp"));
                        dBhelper.saveSincronizado(db, Constants.SINCRONIZADO_T, params_sincro);

                        /**Procesos a enviar que esten en pendiente de envio*/
                        Servicios_Sincronizado ss = new Servicios_Sincronizado();

                        ss.GetGestionesVerDom(ctx, true);
                        ss.SendGestionesVerDom(ctx, true);
                    }
                    cvSincronizarVerificacionesDomiciliarias.setEnabled(true);
                }, 3000);

            } else {
                /**No tiene conexion a internet */
                AlertDialog error_connect = Popups.showDialogMessage(ctx, Constants.not_network,
                        R.string.not_network, R.string.accept, new Popups.DialogMessage() {
                            @Override
                            public void OnClickListener(AlertDialog dialog) {
                                dialog.dismiss();
                            }
                        });
                Objects.requireNonNull(error_connect.getWindow()).requestFeature(Window.FEATURE_NO_TITLE);
                error_connect.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                error_connect.show();
            }
        }

    };

    /**
     * Evento de obtencion de geolocalizaciones contestads
     */
    private View.OnClickListener cvFichasGestionadas_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Servicios_Sincronizado sincronizado = new Servicios_Sincronizado();
            if (NetworkStatus.haveWifi(ctx)) {
                sincronizado.GetGeolocalizacion(ctx, true, true);

            } else {
                final AlertDialog success = Popups.showDialogMessage(ctx, Constants.not_network,
                        R.string.not_wifi, R.string.accept, new Popups.DialogMessage() {
                            @Override
                            public void OnClickListener(AlertDialog dialog) {
                                dialog.dismiss();
                            }
                        });
                Objects.requireNonNull(success.getWindow()).requestFeature(Window.FEATURE_NO_TITLE);
                success.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                success.show();
            }
        }
    };

    /**
     * Evento de actualziacion de catalogos pero algunas funciones tienen detalles no recuerdo cuales
     */
    private View.OnClickListener cvCatalogos_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            if (NetworkStatus.haveNetworkConnection(ctx)) {
                Sincronizar_Catalogos catalogos = new Sincronizar_Catalogos();

                catalogos.GetEstados(ctx);
                catalogos.GetMunicipios(ctx);
                catalogos.GetOcupaciones(ctx);
                catalogos.GetSectores(ctx);
                catalogos.GetTipoIdentificacion(ctx);
                catalogos.GetCatalogosCampanas(ctx);
                catalogos.GetColonias(ctx);
                catalogos.GetCategoriasTickets(ctx);
                catalogos.GetPlazosPrestamo(ctx);

            } else {
                final AlertDialog not_network = Popups.showDialogMessage(ctx, Constants.not_network,
                        R.string.not_wifi, R.string.accept, new Popups.DialogMessage() {
                            @Override
                            public void OnClickListener(AlertDialog dialog) {
                                dialog.dismiss();
                            }
                        });
                Objects.requireNonNull(not_network.getWindow()).requestFeature(Window.FEATURE_NO_TITLE);
                not_network.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                not_network.show();
            }
        }
    };

    private View.OnClickListener sincronizarColonias_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            /**Valida que se encuentre conectado a internet*/
            if (NetworkStatus.haveNetworkConnection(ctx)) {
                sincronizarColonias.setEnabled(false);
                ColoniaDao coloniaDao = new ColoniaDao(ctx);
                Handler handler_home = new Handler();
                handler_home.postDelayed(() -> {
                    /**Valida si esta la sesion activa*/
                    if (session.getUser().get(6).equals("true")) {
                        try {
                            InputStream is = getResources().openRawResource(R.raw.colonias);
                            BufferedReader reader = new BufferedReader(
                                    new InputStreamReader(is, Charset.forName(UTF_8))
                            );
                            String line;
                            int i = 0;
                            while ((line = reader.readLine()) != null) {
                                String[] rowColonia = line.split(";");

                                Integer coloniaId = Integer.parseInt(rowColonia[0].trim());
                                Colonia colonia = coloniaDao.findByColoniaId(coloniaId);

                                if (colonia == null) {
                                    colonia = new Colonia();
                                    colonia.setColoniaId(Integer.parseInt(rowColonia[0].trim()));
                                    colonia.setNombre(rowColonia[2].trim().toUpperCase());
                                    colonia.setCp(Integer.parseInt(rowColonia[3].trim()));
                                    colonia.setMunicipioId(Integer.parseInt(rowColonia[1].trim()));

                                    long id = coloniaDao.store(colonia);

                                    if (id == 0) {
                                        Log.e("AQUI INSERTADO ERROR: ", colonia.getColoniaId() + " - " + colonia.getNombre());
                                    } else {
                                        Log.e("AQUI INSERTADO SUCCESS: ", colonia.getColoniaId() + " - " + colonia.getNombre());
                                    }

                                } else {
                                    Log.e("AQUI ENCONTRADO: ", colonia.getColoniaId() + " - " + colonia.getNombre());
                                }

                                i += 1;
                            }
                        } catch (IOException e) {
                            Log.e("Caragndo colonia error: ", e.getMessage());
                        }
                    }

                    sincronizarColonias.setEnabled(true);
                }, 3000);

            } else {
                /**No tiene conexion a internet */
                AlertDialog error_connect = Popups.showDialogMessage(ctx, Constants.not_network,
                        R.string.not_network, R.string.accept, dialog -> dialog.dismiss());

                Objects.requireNonNull(error_connect.getWindow()).requestFeature(Window.FEATURE_NO_TITLE);
                error_connect.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                error_connect.show();
            }
        }

    };

    private View.OnClickListener sincronizarLocalidades_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            /**Valida que se encuentre conectado a internet*/
            if (NetworkStatus.haveNetworkConnection(ctx)) {
                sincronizarLocalidades.setEnabled(false);
                LocalidadDao localidadDao = new LocalidadDao(ctx);
                Handler handler_home = new Handler();
                handler_home.postDelayed(() -> {
                    /**Valida si esta la sesion activa*/
                    if (session.getUser().get(6).equals("true")) {
                        try {
                            InputStream is = getResources().openRawResource(R.raw.localidades2);
                            BufferedReader reader = new BufferedReader(
                                    new InputStreamReader(is, Charset.forName(UTF_8))
                            );
                            String line;
                            int i = 0;
                            while ((line = reader.readLine()) != null) {
                                String[] row = line.split(",");

                                try {
                                    //if(row[0] != null && !row[0].trim().equals("")) {
                                    Integer localidadId = Integer.parseInt(row[0].trim());
                                    Localidad localidad = localidadDao.findByLocalidadId(localidadId);

                                    if (localidad == null) {
                                        localidad = new Localidad();
                                        localidad.setLocalidadId(Integer.parseInt(row[0].trim()));
                                        localidad.setNombre(row[2].trim().toUpperCase());
                                        localidad.setMunicipioId(Integer.parseInt(row[1].trim()));

                                        long id = localidadDao.store(localidad);

                                        if (id == 0) {
                                            Log.e("AQUI INSERTADO ERROR: ", localidad.getLocalidadId() + " - " + localidad.getNombre());
                                        } else {
                                            Log.e("AQUI INSERTADO SUCCESS: ", localidad.getLocalidadId() + " - " + localidad.getNombre());
                                        }

                                    } else {
                                        Log.e("AQUI ENCONTRADO: ", localidad.getLocalidadId() + " - " + localidad.getNombre());
                                    }
                                }
                                //else
                                catch (Exception e) {
                                    Log.e("AQUI LECTURA ERROR: ", e.getMessage());
                                    Log.e("AQUI LECTURA ERROR: ", ((row[0] != null) ? row[0].trim() : "") + " - " + ((row[2] != null) ? row[2].trim() : "") + " - " + ((row[1] != null) ? row[1].trim() : ""));
                                }

                                i += 1;
                            }
                        } catch (IOException e) {
                            Log.e("Caragndo colonia error: ", e.getMessage());
                        }
                    }

                    sincronizarLocalidades.setEnabled(true);
                }, 3000);

            } else {
                /**No tiene conexion a internet */
                AlertDialog error_connect = Popups.showDialogMessage(ctx, Constants.not_network,
                        R.string.not_network, R.string.accept, dialog -> dialog.dismiss());

                Objects.requireNonNull(error_connect.getWindow()).requestFeature(Window.FEATURE_NO_TITLE);
                error_connect.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                error_connect.show();
            }
        }

    };

    private View.OnClickListener sincronizarMunicipios_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            /**Valida que se encuentre conectado a internet*/
            if (NetworkStatus.haveNetworkConnection(ctx)) {
                sincronizarMunicipios.setEnabled(false);
                MunicipioDao municipioDao = new MunicipioDao(ctx);
                Handler handler_home = new Handler();
                handler_home.postDelayed(() -> {
                    /**Valida si esta la sesion activa*/
                    if (session.getUser().get(6).equals("true")) {
                        try {
                            InputStream is = getResources().openRawResource(R.raw.municipios);
                            BufferedReader reader = new BufferedReader(
                                    new InputStreamReader(is, Charset.forName(UTF_8))
                            );
                            String line;
                            int i = 0;
                            while ((line = reader.readLine()) != null) {
                                String[] row = line.split(",");

                                Integer municipioId = Integer.parseInt(row[0].trim());
                                Municipio municipio = municipioDao.findByMunicipioId(municipioId);

                                if (municipio == null) {
                                    municipio = new Municipio();
                                    municipio.setMunicipioId(Integer.parseInt(row[0].trim()));
                                    municipio.setNombre(row[2].trim().toUpperCase());
                                    municipio.setEstadoId(Integer.parseInt(row[1].trim()));

                                    long id = municipioDao.store(municipio);

                                    if (id == 0) {
                                        Log.e("AQUI INSERTADO ERROR: ", municipio.getMunicipioId() + " - " + municipio.getNombre());
                                    } else {
                                        Log.e("AQUI INSERTADO SUCCESS: ", municipio.getMunicipioId() + " - " + municipio.getNombre());
                                    }

                                } else {
                                    Log.e("AQUI ENCONTRADO: ", municipio.getMunicipioId() + " - " + municipio.getNombre());
                                }

                                i += 1;
                            }
                        } catch (IOException e) {
                            Log.e("Caragndo colonia error: ", e.getMessage());
                        }
                    }

                    sincronizarMunicipios.setEnabled(true);
                }, 3000);

            } else {
                /**No tiene conexion a internet */
                AlertDialog error_connect = Popups.showDialogMessage(ctx, Constants.not_network,
                        R.string.not_network, R.string.accept, dialog -> dialog.dismiss());

                Objects.requireNonNull(error_connect.getWindow()).requestFeature(Window.FEATURE_NO_TITLE);
                error_connect.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                error_connect.show();
            }
        }

    };

    /**
     * Evento para descargar el apk abriendo un dialogFragment para validar por contraseña primero
     */
    private View.OnClickListener cvDownloadApk_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            dialog_pass_update_apk dialogRoot = new dialog_pass_update_apk();
            Bundle b = new Bundle();
            b.putString(TIPO, "Download");
            dialogRoot.setArguments(b);
            dialogRoot.show(getSupportFragmentManager(), NameFragments.DIALOGUPDATEAPK);
        }
    };

    /**
     * Evento para abrir configuraciones para cambiar fecha y hora, se valida por contraseña
     */
    private View.OnClickListener cvFechaHora_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            dialog_pass_update_apk dialogRoot = new dialog_pass_update_apk();
            Bundle b = new Bundle();
            b.putString(TIPO, "Settings");
            dialogRoot.setArguments(b);
            dialogRoot.show(getSupportFragmentManager(), NameFragments.DIALOGUPDATEAPK);
        }
    };


    /**
     * Funcion para comenzar a descargar el apk
     */
    public void DownloadApk(String password) {

        /**Valida si tiene conexion a internet*/
        if (NetworkStatus.haveNetworkConnection(ctx)) {

            final AlertDialog loading = Popups.showLoadingDialog(ctx, R.string.please_wait, R.string.loading_info);
            loading.show();

            /**Interfaz para realizar peticiones*/
            ManagerInterface api = RetrofitClient.generalRF(Constants.CONTROLLER_APK, ctx).create(ManagerInterface.class);

            /**Prepara peticion para descargar el apk*/
            Call<MResponseDefault> call = api.downloadApk(password,
                    "Bearer " + session.getUser().get(7));

            /**Realiza la peticion para descargar el apk*/
            call.enqueue(new Callback<MResponseDefault>() {
                @Override
                public void onResponse(Call<MResponseDefault> call, Response<MResponseDefault> response) {
                    Log.e("ResponseApk", "" + response.code());
                    MResponseDefault res = response.body();
                    switch (response.code()) {
                        case 200:
                            /**Si tiene permitido descargar comienza el proceso para descargar*/
                            Toast.makeText(ctx, "Comienza la descarga", Toast.LENGTH_SHORT).show();
                            Log.e("Urlapk", session.getDominio() + WebServicesRoutes.CONTROLLER_FICHAS + WebServicesRoutes.WS_GET_DOWNLOAD_APK_NEW);
                            myReceiver.DownloadApk(session.getDominio() + WebServicesRoutes.CONTROLLER_FICHAS + WebServicesRoutes.WS_GET_DOWNLOAD_APK_NEW);
                            break;
                        case 404:
                            /**No tiene autorizado para descargar apk*/
                            AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
                            builder.setMessage("No está autorizado para descargar la nueva versión");
                            builder.setPositiveButton("Aceptar", null);
                            AlertDialog dialog = builder.create();
                            dialog.show();
                            break;
                        default:
                            AlertDialog.Builder builderD = new AlertDialog.Builder(ctx);
                            builderD.setMessage("Error al enviar los datos para la descarga");
                            builderD.setPositiveButton("Aceptar", null);
                            AlertDialog dialogD = builderD.create();
                            dialogD.show();
                            break;
                    }
                    loading.dismiss();
                }

                @Override
                public void onFailure(Call<MResponseDefault> call, Throwable t) {
                    loading.dismiss();
                    Toast.makeText(ctx, "Ha ocurrido un error durante la descarga fail", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            final AlertDialog not_network = Popups.showDialogMessage(ctx, Constants.not_network,
                    R.string.not_wifi, R.string.accept, new Popups.DialogMessage() {
                        @Override
                        public void OnClickListener(AlertDialog dialog) {
                            dialog.dismiss();
                        }
                    });
            Objects.requireNonNull(not_network.getWindow()).requestFeature(Window.FEATURE_NO_TITLE);
            not_network.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            not_network.show();
        }
    }

    /**
     * Funcion para validar la contraseña y saber si se le da permiso para cambiar fecha y hora no se ocupa esta oculta esta opcion
     */
    public void SettingsApp(String password) {
        Log.e("Inicia", "valida la constraseña" + password);
        if (NetworkStatus.haveNetworkConnection(ctx)) {

            final AlertDialog loading = Popups.showLoadingDialog(ctx, R.string.please_wait, R.string.loading_info);
            loading.show();

            ManagerInterface api = RetrofitClient.generalRF(Constants.CONTROLLER_APK, ctx).create(ManagerInterface.class);

            Call<MResponseDefault> call = api.settingsApp(password,
                    "Bearer " + session.getUser().get(7));

            call.enqueue(new Callback<MResponseDefault>() {
                @Override
                public void onResponse(Call<MResponseDefault> call, Response<MResponseDefault> response) {
                    Log.e("ResponseApp", "" + response.code());
                    MResponseDefault res = response.body();
                    switch (response.code()) {
                        case 200:
                            //ACTION_DATE_SETTINGS //configurar fecha y hora
                            //ACTION_WIFI_SETTINGS //configurar conexiones de wifi
                            startActivity(new Intent(Settings.ACTION_DATE_SETTINGS));
                            break;
                        case 404:
                            AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
                            builder.setMessage("No está autorizado para aplicar cambios en la configuración");
                            builder.setPositiveButton("Aceptar", null);
                            AlertDialog dialog = builder.create();
                            dialog.show();
                            break;
                        default:
                            AlertDialog.Builder builderD = new AlertDialog.Builder(ctx);
                            builderD.setMessage("Error al enviar los datos");
                            builderD.setPositiveButton("Aceptar", null);
                            AlertDialog dialogD = builderD.create();
                            dialogD.show();
                            break;
                    }
                    loading.dismiss();
                }

                @Override
                public void onFailure(Call<MResponseDefault> call, Throwable t) {
                    loading.dismiss();
                    Toast.makeText(ctx, "Ha ocurrido un error", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            final AlertDialog not_network = Popups.showDialogMessage(ctx, Constants.not_network,
                    R.string.not_wifi, R.string.accept, new Popups.DialogMessage() {
                        @Override
                        public void OnClickListener(AlertDialog dialog) {
                            dialog.dismiss();
                        }
                    });
            Objects.requireNonNull(not_network.getWindow()).requestFeature(Window.FEATURE_NO_TITLE);
            not_network.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            not_network.show();
        }
    }

    private void Init() {
        myReceiver = new MyReceiverApk(Configuracion.this);
        myReceiver.Register(myReceiver);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();
        myReceiver.DeleteRegister(myReceiver);
    }

    @Override
    protected void onResume() {
        super.onResume();
        myReceiver.Register(myReceiver);
    }

    private void ReactivarRenovacionGpo(String asesorId, String nombre, String fechaVencimiento) {
        if (session.getUser().get(0).equals(asesorId)) {
            PrestamoToRenovarDao prestamoToRenovarDao = new PrestamoToRenovarDao(ctx);
            PrestamoToRenovar prestamoToRenovar = prestamoToRenovarDao.findLikeClienteNombreAndFechaVencimiento(nombre, fechaVencimiento);

            if (prestamoToRenovar != null) {
                SolicitudRenDao solicitudRenDao = new SolicitudRenDao(ctx);
                SolicitudRen solicitudRen = solicitudRenDao.findLikeNombreAndPrestamoId(nombre, prestamoToRenovar.getPrestamoId());

                if (solicitudRen != null) {
                    CreditoGpoRenDao creditoGpoRenDao = new CreditoGpoRenDao(ctx);
                    CreditoGpoRen creditoGpoRen = creditoGpoRenDao.findByIdSolicitud(solicitudRen.getIdSolicitud());

                    if (creditoGpoRen != null) {
                        IntegranteGpoRenDao integranteGpoRenDao = new IntegranteGpoRenDao(ctx);
                        List<IntegranteGpoRen> integranteGpoRenList = integranteGpoRenDao.findAllByIdCredito(creditoGpoRen.getId());

                        if (integranteGpoRenList.size() > 0) {
                            for (IntegranteGpoRen iGpoRen : integranteGpoRenList) {
                                iGpoRen.setEstatusCompletado(0);
                                iGpoRen.setEstatusRechazo(0);
                                integranteGpoRenDao.updateEstatus(iGpoRen);
                                integranteGpoRenDao.saveEstatus(iGpoRen);
                            }
                        }

                        solicitudRen.setEstatus(0);
                        solicitudRenDao.updateEstatus(solicitudRen);
                    }
                }
            }
        }
    }

    private void ReactivarOriginacionGpo(String asesorId, String nombre) {
        if (session.getUser().get(0).equals(asesorId)) {
            SolicitudDao solicitudDao = new SolicitudDao(ctx);
            Solicitud solicitud = solicitudDao.findLikeNombre(nombre);

            if (solicitud != null) {
                CreditoGpoDao creditoGpoDao = new CreditoGpoDao(ctx);
                CreditoGpo creditoGpo = creditoGpoDao.findByIdSolicitud(solicitud.getIdSolicitud());

                if (creditoGpo != null) {
                    IntegranteGpoDao integranteGpoDao = new IntegranteGpoDao(ctx);
                    List<IntegranteGpo> integranteGpoList = integranteGpoDao.findAllByIdCredito(creditoGpo.getId());

                    if (integranteGpoList.size() > 0) {
                        for (IntegranteGpo iGpo : integranteGpoList) {
                            iGpo.setEstatusCompletado(0);
                            iGpo.setEstatusRechazo(0);
                            integranteGpoDao.updateEstatus(iGpo);
                            integranteGpoDao.saveEstatus(iGpo);
                        }
                    }

                    solicitud.setEstatus(0);
                    solicitudDao.updateEstatus(solicitud);
                }
            }
        }
    }


    public class RegistrarDatosRenovacion extends AsyncTask<Object, Void, String> {

        Context ctx1;

        public RegistrarDatosRenovacion(Context ctx) {
            this.ctx1 = ctx;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Object... obj) {
            final Context ctx = (Context) obj[0];
            final DBhelper dBhelper = DBhelper.getInstance(ctx);
            final SQLiteDatabase db = dBhelper.getWritableDatabase();
            final SessionManager session = SessionManager.getInstance(ctx);
            String _id = (String) obj[1];
            final String prestamoId = (String) obj[2];
            final String clienteId = (String) obj[3];
            final String fechaVencimiento = (String) obj[4];

            Log.e("AQUI: ", String.valueOf(prestamoId));
            Log.e("AQUI: ", String.valueOf(clienteId));

            ManagerInterface api = RetrofitClient.generalRF(CONTROLLER_MOVIL, ctx).create(ManagerInterface.class);

            Call<MRenovacion> call = api.getPrestamoRenovar(
                    //prestamoId,
                    "445392",
                    //clienteId,
                    "51482",
                    "Bearer " + session.getUser().get(7));

            call.enqueue(new Callback<MRenovacion>() {
                @Override
                public void onResponse(Call<MRenovacion> call, Response<MRenovacion> response) {
                    switch (response.code()) {
                        case 200:
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
                            params.put(0, ctx.getString(R.string.vol_solicitud));                               //VOL SOLICITUD
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
                            db.update(TBL_PRESTAMOS_TO_RENOVAR, cv, "prestamo_id = ? AND cliente_id = ?", new String[]{prestamoId, clienteId});

                            break;
                        default:
                            try {
                                Log.e("ERROR " + response.code(), response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            Log.e("ERROR " + response.code(), response.message());
                            break;
                    }
                }

                @Override
                public void onFailure(Call<MRenovacion> call, Throwable t) {

                }
            });
            return "";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }


}
