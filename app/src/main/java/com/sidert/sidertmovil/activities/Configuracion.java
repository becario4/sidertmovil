package com.sidert.sidertmovil.activities;

import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.provider.Settings;
/*import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;*/
import android.os.Bundle;
/*import android.support.v7.widget.CardView;
import androidx.appcompat.widget.Toolbar;*/
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.webkit.CookieManager;
import android.webkit.URLUtil;
import android.widget.LinearLayout;
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

import static com.sidert.sidertmovil.utils.Constants.TBL_RECIBOS_AGF_CC;
import static com.sidert.sidertmovil.utils.Constants.TBL_RECUPERACION_RECIBOS_CC;
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

    private LinearLayout llBorrarCache;


    private String[] _tablas;

    private ArrayList<Integer> selectItemsDelete;

    private SessionManager session;
    private Miscellaneous m = new Miscellaneous();

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

        cvSincronizarFichas                      = findViewById(R.id.cvSincronizarFichas);
        cvSincronizarGeolocalizaciones           = findViewById(R.id.cvSincronizarGeolocalizaciones);
        cvSincronizarImpresiones                 = findViewById(R.id.cvSincronizarImpresiones);
        cvSincronizarRutas                       = findViewById(R.id.cvSincronizarRutas);
        cvSincronizarAgf                         = findViewById(R.id.cvSincronizarAgf);
        cvSincronizarCirculoCredito              = findViewById(R.id.cvSincronizarCirculoCredito);
        cvSincronizarOriginacion                 = findViewById(R.id.cvSincronizarOriginacion);
        cvSincronizarRenovaciones                = findViewById(R.id.cvSincronizarRenovaciones);
        cvSincronizarIntegrantesRen              = findViewById(R.id.cvSincronizarIntegrantesRen);
        cvSincronizarEstadoSolicitudes           = findViewById(R.id.cvSincronizarEstadoSolicitudes);
        cvSincronizarVerificacionesDomiciliarias = findViewById(R.id.cvSincronizarVerificacionesDomiciliarias);
        sincronizarColonias                      = findViewById(R.id.sincronizarColonias);
        sincronizarLocalidades                   = findViewById(R.id.sincronizarLocalidades);
        sincronizarMunicipios                    = findViewById(R.id.sincronizarMunicipios);
        cvCompletarIntegrantesRen                = findViewById(R.id.cvCompletarIntegrantesRen);
        cvCatSolicitud                           = findViewById(R.id.cvCatSolicitud);
        cvDescargarGuia                          = findViewById(R.id.cvDownloadGuia);
        cvBorrarCache                            = findViewById(R.id.cvBorrarCache);

        CardView cvFichasGestionadas = findViewById(R.id.cvFichasGestionadas);
        CardView cvCatalogos = findViewById(R.id.cvCatalogos);
        CardView cvDownloadApk = findViewById(R.id.cvDownloadApk);
        CardView cvFechaHora = findViewById(R.id.cvFechaHora);

        _tablas = getResources().getStringArray(R.array.tablas_cache);

        setSupportActionBar(tbMain);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        dBhelper = new DBhelper(ctx);
        db = dBhelper.getWritableDatabase();

        session = new SessionManager(ctx);

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

        llBorrarCache = findViewById(R.id.llBorrarCache);

        String dato = tipoUsuarioRol(session);

        if(dato.contains("ROLE_SUPER") || dato.contains("ROLE_ASESOR")){
            cvBorrarCache.setOnClickListener(cvBorrarCache_OnClick);
            llBorrarCache.setVisibility(View.VISIBLE);
        }else{
            llBorrarCache.setVisibility(View.GONE);
        }

        try {
            if (session.getUser().get(8) != null)
            {
                JSONArray jsonModulos = new JSONArray(session.getUser().get(8));
                for (int i = 0; i < jsonModulos.length(); i++) {
                    JSONObject item = jsonModulos.getJSONObject(i);
                    switch (item.getString("nombre").toLowerCase()) {
                        case "cartera":
                        {
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
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private View.OnClickListener cvCatSolicitud_OnClick = new View.OnClickListener(){
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
                    }
                }, 3000);
            }
        }
    };

    private View.OnClickListener cvDescargarGuia_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String valor = "guia_rapida.pdf";

            String result = tipoUsuarioRol(session);
            if(result.contains("ROLE_ASESOR") || result.contains("ROLE_GERENTEREGIONAL") || result.contains("ROLE_GERENTESUCURSAL") || result.contains("ROLE_GERENTECARTERAVENCIDA")){
                valor = "guia_asesor.pdf";

            }else if(result.contains("ROLE_GESTOR")){
                valor = "guia_rapida.pdf";
            }
            File guiaAsesor = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Documents/guia_asesor.pdf");
            File guiaRapida = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Documents/guia_rapida.pdf");

            if(guiaAsesor.exists() || guiaRapida.exists() ){
                Toast.makeText(ctx, "EL ARCHIVO YA EXISTE", Toast.LENGTH_SHORT).show();
            }else{

                try{
                    String base_url = "http://187.188.168.167:83/api/movil/descargarFile/" + valor + "/";
                    // session.getDominio().get(0) + session.getDominio().get(1) + WebServicesRoutes.CONTROLLER_DESCARGARGUIA+"/"+ valor+"/";

                    DownloadManager.Request request = new DownloadManager.Request(Uri.parse(base_url));
                    request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);

                    String texto = URLUtil.guessFileName(base_url,null,null);


                    request.setTitle(valor);
                    request.setDescription("Descargando el archivo por favor espera...");

                    String cookie = CookieManager.getInstance().getCookie(base_url);

                    request.allowScanningByMediaScanner();
                    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                    request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOCUMENTS,valor);

                    DownloadManager downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);

                    downloadManager.enqueue(request);

                    if(downloadManager != null){
                        Toast.makeText(ctx,"Guía descargada",Toast.LENGTH_SHORT).show();
                    }

                }catch (Exception e){
                    Toast.makeText(ctx, "Error" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }
    };

    private String tipoUsuarioRol(SessionManager sen){
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
            try{
                selectItemsDelete = new ArrayList<>();
                new AlertDialog.Builder(Configuracion.this)
                        .setTitle("Selecciona la cache a borrar")
                        .setMultiChoiceItems(_tablas, null, new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i, boolean isChecked) {
                                if(isChecked){
                                    selectItemsDelete.add(i);
                                }else if(selectItemsDelete.contains(i)){
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
                                if(dato != 0){
                                    Toast.makeText(ctx, "SE HA BORRADO LA CACHE", Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(ctx,"NO SE HA BORRADO LA CACHE", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int id) {
                        Toast.makeText(ctx,"NO SE HA BORRADO LA CACHE", Toast.LENGTH_SHORT).show();
                    }
                }).show();
            }catch (Exception e){
                Toast.makeText(ctx,"Error: " + e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        }
    };

    private Miscellaneous misce = new Miscellaneous();
    private int deleteTable(String tablas){
        String tablaDelete=tablas;
        Cursor eliminarTablet = null;
        int idTest = 1;//SIEMPRE SE ENVIA 1, DADO QUE 1, ES EL VALOR DE UN ESTATUS NO EVIADO
        int dato = 0;
        int validacion = 0;
        validacion = misce.validarEstatus(tablas,ctx);

        if(validacion > 0){
            dato = 0;
        }else {
           /* if(tablas.contains("tb_tabla_test")){
                eliminarTablet = dBhelper.deleteData(tablaDelete,"_id=?",new String[]{String.valueOf(idTest)});
                if(eliminarTablet.getCount()==0){
                    dato = 1; //EL DATO HA SIDO BORRADO
                }else{
                    dato = 0;//EL DATO NO FUE BORRADO
                }
            }else */
            if(tablas.contains(TBL_RECUPERACION_RECIBOS_CC)){
                eliminarTablet = dBhelper.deleteData(tablaDelete,new String[]{String.valueOf(idTest)});

                if(eliminarTablet.getCount()==0){
                    dato = 1;
                }else{
                    dato = 0;
                }
            }
            if(tablas.contains(TBL_RECIBOS_AGF_CC)){
                eliminarTablet = dBhelper.deleteData(tablaDelete,new String[]{String.valueOf(idTest)});
                if(eliminarTablet.getCount()==0){
                    dato = 1;
                }else{
                    dato = 0;
                }
            }
        }
        dato += dato;

        return dato;
    }


    /**Evento para sincronizar lo que esta pendiente de envio*/
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

            }
            else{
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

            }
            else{
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

            }
            else{
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

            }
            else{
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

            }
            else{
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

            }
            else{
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

            }
            else{
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

                /*SolicitudRenDao solicitudDao = new SolicitudRenDao(ctx);
                SolicitudRen solicitud = solicitudDao.findByNombre("AMANECER 7377");
                if(solicitud != null)
                {
                    CreditoGpoRenDao creditoGpoRenDao = new CreditoGpoRenDao(ctx);
                    CreditoGpoRen creditoGpoRen = creditoGpoRenDao.findByIdSolicitud(solicitud.getIdSolicitud());

                    if(creditoGpoRen != null)
                    {
                        IntegranteGpoRenDao integranteGpoRenDao = new IntegranteGpoRenDao(ctx);
                        List<IntegranteGpoRen> integrantes = integranteGpoRenDao.findByIdCredito(creditoGpoRen.getId());

                        for(IntegranteGpoRen i : integrantes)
                        {
                            i.setEstatusCompletado(1);
                            integranteGpoRenDao.saveEstatus(i);
                        }

                        solicitud.setEstatus(1);
                        solicitudDao.updateEstatus(solicitud);
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
                        ss.CancelGestiones(ctx, false);
                        ss.SendCancelGestiones(ctx, false);
                        ss.SendRenovacionInd(ctx, false);
                        ss.SendRenovacionGpo(ctx, false);
                    }
                    cvSincronizarRenovaciones.setEnabled(true);
                }, 3000);

            }
            else{
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

                        ss.GetPrestamosToRenovar(ctx);
                    }
                    cvSincronizarIntegrantesRen.setEnabled(true);
                }, 3000);

            }
            else{
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

            }
            else{
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

            }
            else{
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

            }
            else{
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

    /**Evento de obtencion de geolocalizaciones contestads*/
    private View.OnClickListener cvFichasGestionadas_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Servicios_Sincronizado sincronizado = new Servicios_Sincronizado();
            if (NetworkStatus.haveWifi(ctx)){
                sincronizado.GetGeolocalizacion(ctx, true, true);

            }


            else{
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

    /**Evento de actualziacion de catalogos pero algunas funciones tienen detalles no recuerdo cuales*/
    private View.OnClickListener cvCatalogos_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            if (NetworkStatus.haveNetworkConnection(ctx)){
                Sincronizar_Catalogos catalogos = new Sincronizar_Catalogos();
                catalogos.GetEstados(ctx);
                //catalogos.GetMunicipios(ctx);
                catalogos.GetOcupaciones(ctx);
                catalogos.GetSectores(ctx);
                catalogos.GetTipoIdentificacion(ctx);
                //catalogos.GetColonias(ctx);
                catalogos.GetCategoriasTickets(ctx);
                catalogos.GetPlazosPrestamo(ctx);
            }
            else{
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

                                if(colonia == null) {
                                    colonia = new Colonia();
                                    colonia.setColoniaId(Integer.parseInt(rowColonia[0].trim()));
                                    colonia.setNombre(rowColonia[2].trim().toUpperCase());
                                    colonia.setCp(Integer.parseInt(rowColonia[3].trim()));
                                    colonia.setMunicipioId(Integer.parseInt(rowColonia[1].trim()));

                                    long id = coloniaDao.store(colonia);

                                    if(id == 0)
                                    {
                                        Log.e("AQUI INSERTADO ERROR: ", colonia.getColoniaId() + " - " + colonia.getNombre());
                                    }
                                    else
                                    {
                                        Log.e("AQUI INSERTADO SUCCESS: ", colonia.getColoniaId() + " - " + colonia.getNombre());
                                    }

                                }
                                else
                                {
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

            }
            else{
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
                            InputStream is = getResources().openRawResource(R.raw.localidades);
                            BufferedReader reader = new BufferedReader(
                                    new InputStreamReader(is, Charset.forName(UTF_8))
                            );
                            String line;
                            int i = 0;
                            while ((line = reader.readLine()) != null) {
                                String[] row = line.split(",");

                                try{
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
                                catch(Exception e)
                                {
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

            }
            else{
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

                                if(municipio == null) {
                                    municipio = new Municipio();
                                    municipio.setMunicipioId(Integer.parseInt(row[0].trim()));
                                    municipio.setNombre(row[2].trim().toUpperCase());
                                    municipio.setEstadoId(Integer.parseInt(row[1].trim()));

                                    long id = municipioDao.store(municipio);

                                    if(id == 0)
                                    {
                                        Log.e("AQUI INSERTADO ERROR: ", municipio.getMunicipioId() + " - " + municipio.getNombre());
                                    }
                                    else
                                    {
                                        Log.e("AQUI INSERTADO SUCCESS: ", municipio.getMunicipioId() + " - " + municipio.getNombre());
                                    }

                                }
                                else
                                {
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

            }
            else{
                /**No tiene conexion a internet */
                AlertDialog error_connect = Popups.showDialogMessage(ctx, Constants.not_network,
                        R.string.not_network, R.string.accept, dialog -> dialog.dismiss());

                Objects.requireNonNull(error_connect.getWindow()).requestFeature(Window.FEATURE_NO_TITLE);
                error_connect.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                error_connect.show();
            }
        }

    };

    /**Evento para descargar el apk abriendo un dialogFragment para validar por contraseña primero*/
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

    /**Evento para abrir configuraciones para cambiar fecha y hora, se valida por contraseña*/
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


    /**Funcion para comenzar a descargar el apk*/
    public void DownloadApk(String password){

        /**Valida si tiene conexion a internet*/
        if (NetworkStatus.haveNetworkConnection(ctx)){

            final AlertDialog loading = Popups.showLoadingDialog(ctx,R.string.please_wait, R.string.loading_info);
            loading.show();

            /**Interfaz para realizar peticiones*/
            ManagerInterface api = new RetrofitClient().generalRF(Constants.CONTROLLER_APK, ctx).create(ManagerInterface.class);

            /**Prepara peticion para descargar el apk*/
            Call<MResponseDefault> call = api.downloadApk(password,
                    "Bearer "+ session.getUser().get(7));

            /**Realiza la peticion para descargar el apk*/
            call.enqueue(new Callback<MResponseDefault>() {
                @Override
                public void onResponse(Call<MResponseDefault> call, Response<MResponseDefault> response) {
                    Log.e("ResponseApk", ""+response.code());
                    MResponseDefault res = response.body();
                    switch (response.code()){
                        case 200:
                            /**Si tiene permitido descargar comienza el proceso para descargar*/
                            Toast.makeText(ctx, "Comienza la descarga", Toast.LENGTH_SHORT).show();
                            Log.e("Urlapk", session.getDominio().get(0) + session.getDominio().get(1) + WebServicesRoutes.CONTROLLER_FICHAS + WebServicesRoutes.WS_GET_DOWNLOAD_APK_NEW);
                            myReceiver.DownloadApk(session.getDominio().get(0) + session.getDominio().get(1) + WebServicesRoutes.CONTROLLER_FICHAS + WebServicesRoutes.WS_GET_DOWNLOAD_APK_NEW);
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
        }
        else{
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

    /**Funcion para validar la contraseña y saber si se le da permiso para cambiar fecha y hora no se ocupa esta oculta esta opcion*/
    public void SettingsApp(String password){
        Log.e("Inicia", "valida la constraseña"+password);
        if (NetworkStatus.haveNetworkConnection(ctx)){

            final AlertDialog loading = Popups.showLoadingDialog(ctx,R.string.please_wait, R.string.loading_info);
            loading.show();

            ManagerInterface api = new RetrofitClient().generalRF(Constants.CONTROLLER_APK, ctx).create(ManagerInterface.class);

            Call<MResponseDefault> call = api.settingsApp(password,
                    "Bearer "+ session.getUser().get(7));

            call.enqueue(new Callback<MResponseDefault>() {
                @Override
                public void onResponse(Call<MResponseDefault> call, Response<MResponseDefault> response) {
                    Log.e("ResponseApp", ""+response.code());
                    MResponseDefault res = response.body();
                    switch (response.code()){
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
        }
        else{
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

    private void Init(){
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

    private void ReactivarRenovacionGpo(String asesorId, String nombre, String fechaVencimiento)
    {
        if (session.getUser().get(0).equals(asesorId))
        {
            PrestamoToRenovarDao prestamoToRenovarDao = new PrestamoToRenovarDao(ctx);
            PrestamoToRenovar prestamoToRenovar = prestamoToRenovarDao.findLikeClienteNombreAndFechaVencimiento(nombre, fechaVencimiento);

            if(prestamoToRenovar != null) {
                SolicitudRenDao solicitudRenDao = new SolicitudRenDao(ctx);
                SolicitudRen solicitudRen = solicitudRenDao.findLikeNombreAndPrestamoId(nombre, prestamoToRenovar.getPrestamoId());

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
    }

    private void ReactivarOriginacionGpo(String asesorId, String nombre)
    {
        if (session.getUser().get(0).equals(asesorId))
        {
            SolicitudDao solicitudDao = new SolicitudDao(ctx);
            Solicitud solicitud = solicitudDao.findLikeNombre(nombre);

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
    }


}
