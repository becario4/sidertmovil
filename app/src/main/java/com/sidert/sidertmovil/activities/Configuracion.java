package com.sidert.sidertmovil.activities;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.ColorDrawable;
import android.os.Environment;
import android.os.Handler;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.database.DBhelper;
import com.sidert.sidertmovil.downloadapk.MyReceiverApk;
import com.sidert.sidertmovil.fragments.dialogs.dialog_pass_update_apk;
import com.sidert.sidertmovil.models.MResponseDefault;
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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.sidert.sidertmovil.utils.Constants.TIPO;

public class Configuracion extends AppCompatActivity {

    private Context ctx;
    private CardView cvSincronizarFichas;

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
        CardView cvFichasGestionadas = findViewById(R.id.cvFichasGestionadas);
        CardView cvCatalogos = findViewById(R.id.cvCatalogos);
        CardView cvDownloadApk = findViewById(R.id.cvDownloadApk);
        CardView cvFechaHora = findViewById(R.id.cvFechaHora);

        setSupportActionBar(tbMain);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        dBhelper = new DBhelper(ctx);
        db = dBhelper.getWritableDatabase();

        session = new SessionManager(ctx);

        cvSincronizarFichas.setOnClickListener(cvSincronizarFichas_OnClick);
        cvFichasGestionadas.setOnClickListener(cvFichasGestionadas_OnClick);
        cvCatalogos.setOnClickListener(cvCatalogos_OnClick);
        cvDownloadApk.setOnClickListener(cvDownloadApk_OnClick);
        cvFechaHora.setOnClickListener(cvFechaHora_OnClick);
    }

    private View.OnClickListener cvSincronizarFichas_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (NetworkStatus.haveNetworkConnection(ctx)) {
                cvSincronizarFichas.setEnabled(false);
                Handler handler_home = new Handler();
                handler_home.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (session.getUser().get(6).equals("true")) {
                            HashMap<Integer, String> params_sincro = new HashMap<>();
                            params_sincro.put(0, session.getUser().get(0));
                            params_sincro.put(1, Miscellaneous.ObtenerFecha("timestamp"));

                            dBhelper.saveSincronizado(db, Constants.SINCRONIZADO_T, params_sincro);

                            Servicios_Sincronizado ss = new Servicios_Sincronizado();
                            ss.SaveCierreDia(ctx, true);
                            ss.SaveGeolocalizacion(ctx, true);
                            ss.SaveRespuestaGestion(ctx, true);
                            ss.SendImpresionesVi(ctx, true);
                            ss.SendReimpresionesVi(ctx, true);
                            ss.SendTracker(ctx, true);
                            ss.GetTickets(ctx, true);

                            ss.SendOriginacionInd (ctx, false);
                            ss.SendOriginacionGpo(ctx, false);
                            //ss.CancelGestiones(ctx, true);
                            //ss.SendRecibos(ctx, true);
                            //ss.GetUltimosRecibos(ctx);
                            //ss.SendCancelGestiones(ctx, true);


                        }
                        cvSincronizarFichas.setEnabled(true);
                    }
                }, 3000);

            }
            else{
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

    private View.OnClickListener cvFichasGestionadas_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Servicios_Sincronizado sincronizado = new Servicios_Sincronizado();
            if (NetworkStatus.haveWifi(ctx))
                sincronizado.GetGeolocalizacion(ctx, true, true);
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

    private View.OnClickListener cvCatalogos_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            if (NetworkStatus.haveNetworkConnection(ctx)){
                Sincronizar_Catalogos catalogos = new Sincronizar_Catalogos();
                catalogos.GetEstados(ctx);
                /*catalogos.GetMunicipios(ctx);
                catalogos.GetOcupaciones(ctx);
                catalogos.GetSectores(ctx);
                catalogos.GetTipoIdentificacion(ctx);
                catalogos.GetColonias(ctx);*/
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


    public void DownloadApk(String password){

        if (NetworkStatus.haveNetworkConnection(ctx)){

            final AlertDialog loading = Popups.showLoadingDialog(ctx,R.string.please_wait, R.string.loading_info);
            loading.show();

            ManagerInterface api = new RetrofitClient().generalRF(Constants.CONTROLLER_APK, ctx).create(ManagerInterface.class);

            Call<MResponseDefault> call = api.downloadApk(password,
                    "Bearer "+ session.getUser().get(7));

            call.enqueue(new Callback<MResponseDefault>() {
                @Override
                public void onResponse(Call<MResponseDefault> call, Response<MResponseDefault> response) {
                    Log.e("ResponseApk", ""+response.code());
                    MResponseDefault res = response.body();
                    switch (response.code()){
                        case 200:
                            Toast.makeText(ctx, "Comienza la descarga", Toast.LENGTH_SHORT).show();
                            myReceiver.DownloadApk(session.getDominio().get(0) + session.getDominio().get(1) + WebServicesRoutes.CONTROLLER_FICHAS + WebServicesRoutes.WS_GET_DOWNLOAD_APK);
                            break;
                        case 404:
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
}
