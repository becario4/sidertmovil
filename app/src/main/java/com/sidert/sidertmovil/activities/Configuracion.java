package com.sidert.sidertmovil.activities;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;

import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.database.DBhelper;
import com.sidert.sidertmovil.utils.Constants;
import com.sidert.sidertmovil.utils.Miscellaneous;
import com.sidert.sidertmovil.utils.NetworkStatus;
import com.sidert.sidertmovil.utils.Popups;
import com.sidert.sidertmovil.utils.Servicios_Sincronizado;
import com.sidert.sidertmovil.utils.SessionManager;
import com.sidert.sidertmovil.utils.Sincronizar_Catalogos;

import java.util.HashMap;

public class Configuracion extends AppCompatActivity {

    private Context ctx;
    private Toolbar tbMain;
    private CardView cvSincronizarFichas;
    private CardView cvFichasGestionadas;
    private CardView cvCatalogos;

    private SessionManager session;

    private DBhelper dBhelper;
    private SQLiteDatabase db;

    private int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracion);

        ctx = this;
        tbMain = findViewById(R.id.tbMain);
        cvSincronizarFichas = findViewById(R.id.cvSincronizarFichas);
        cvFichasGestionadas = findViewById(R.id.cvFichasGestionadas);
        cvCatalogos         = findViewById(R.id.cvCatalogos);

        setSupportActionBar(tbMain);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        dBhelper = new DBhelper(ctx);
        db = dBhelper.getWritableDatabase();

        session = new SessionManager(ctx);

        cvSincronizarFichas.setOnClickListener(cvSincronizarFichas_OnClick);
        cvFichasGestionadas.setOnClickListener(cvFichasGestionadas_OnClick);
        cvCatalogos.setOnClickListener(cvCatalogos_OnClick);
    }

    private View.OnClickListener cvSincronizarFichas_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.e("Count", "total"+(count++));
            cvSincronizarFichas.setEnabled(false);
            Handler handler_home=new Handler();
            handler_home.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (session.getUser().get(6).equals("true")) {
                        HashMap<Integer, String> params_sincro = new HashMap<>();
                        params_sincro.put(0, session.getUser().get(0));
                        params_sincro.put(1, Miscellaneous.ObtenerFecha("timestamp"));

                        if (Constants.ENVIROMENT)
                            dBhelper.saveSincronizado(db, Constants.SINCRONIZADO, params_sincro);
                        else
                            dBhelper.saveSincronizado(db, Constants.SINCRONIZADO_T, params_sincro);

                        Servicios_Sincronizado sincronizado = new Servicios_Sincronizado();
                        sincronizado.GetGeolocalizacion(ctx, true, false);
                        sincronizado.SaveCierreDia(ctx, true);
                        sincronizado.SaveGeolocalizacion(ctx, true);
                        sincronizado.SaveRespuestaGestion(ctx, true);
                        sincronizado.SendImpresionesVi(ctx, true);
                        sincronizado.SendReimpresionesVi(ctx, true);
                        sincronizado.SendTracker(ctx, true);

                    }
                    cvSincronizarFichas.setEnabled(true);
                }
            },3000);

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
                success.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
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
                catalogos.GetMunicipios(ctx);
                catalogos.GetOcupaciones(ctx);
                catalogos.GetSectores(ctx);
                catalogos.GetTipoIdentificacion(ctx);
                catalogos.GetColonias(ctx);
            }
            else{
                final AlertDialog not_network = Popups.showDialogMessage(ctx, Constants.not_network,
                        R.string.not_wifi, R.string.accept, new Popups.DialogMessage() {
                            @Override
                            public void OnClickListener(AlertDialog dialog) {
                                dialog.dismiss();
                            }
                        });
                not_network.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                not_network.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                not_network.show();
            }
        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
