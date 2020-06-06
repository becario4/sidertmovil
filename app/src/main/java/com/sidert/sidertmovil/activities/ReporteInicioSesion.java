package com.sidert.sidertmovil.activities;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.adapters.adapter_inicio_sesion;
import com.sidert.sidertmovil.models.MLogLogin;
import com.sidert.sidertmovil.utils.Constants;
import com.sidert.sidertmovil.utils.ManagerInterface;
import com.sidert.sidertmovil.utils.NetworkStatus;
import com.sidert.sidertmovil.utils.Popups;
import com.sidert.sidertmovil.utils.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.sidert.sidertmovil.utils.Constants.CONTROLLER_API;

public class ReporteInicioSesion extends AppCompatActivity {

    private Context ctx;
    private Toolbar tbMain;

    private RecyclerView rvLogin;
    private adapter_inicio_sesion adatper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reporte_inicio_sesion);
        ctx = this;

        tbMain = findViewById(R.id.tbMain);

        setSupportActionBar(tbMain);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        rvLogin = findViewById(R.id.rvLogin);
        rvLogin.setLayoutManager(new LinearLayoutManager(ctx));
        rvLogin.setHasFixedSize(false);

        GetReporteLogin();
    }

    private void GetReporteLogin(){
        if (NetworkStatus.haveNetworkConnection(ctx)){
            final AlertDialog loading = Popups.showLoadingDialog(ctx, R.string.please_wait, R.string.loading_info);
            loading.show();

            ManagerInterface api = new RetrofitClient().generalRF(CONTROLLER_API, ctx).create(ManagerInterface.class);

            Call<List<MLogLogin>> call = api.getLogAsesores("2020-06-05",
                                                            "2020-06-05",
                                                            "0",
                                                            "0",
                                                            "0");

            call.enqueue(new Callback<List<MLogLogin>>() {
                @Override
                public void onResponse(Call<List<MLogLogin>> call, Response<List<MLogLogin>> response) {
                    switch (response.code()){
                        case 200:
                            List<MLogLogin> logLogin = response.body();
                            ArrayList<MLogLogin> data = new ArrayList<>();
                            if (logLogin.size() > 0){
                                for (MLogLogin item : logLogin){
                                    if (item.getUsuario().contains("ASESOR") || item.getUsuario().contains("GESTOR"))
                                        data.add(item);
                                }

                                adatper = new adapter_inicio_sesion(ctx, data);
                                rvLogin.setAdapter(adatper);

                            }
                            break;
                    }
                    loading.dismiss();
                }

                @Override
                public void onFailure(Call<List<MLogLogin>> call, Throwable t) {
                    loading.dismiss();
                }
            });
        }
        else{
            final AlertDialog not_network = Popups.showDialogMessage(ctx, Constants.not_network,
                    R.string.not_network, R.string.accept, new Popups.DialogMessage() {
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_filtro, menu);
        menu.getItem(1).setVisible(false);

        final MenuItem menuItem = menu.findItem(R.id.nvFiltro);
        View actionView = MenuItemCompat.getActionView(menuItem);
        TextView tvContFiltros = actionView.findViewById(R.id.filtro_bagde);
        tvContFiltros.setVisibility(View.GONE);
        actionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOptionsItemSelected(menuItem);
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.nvFiltro:
                Toast.makeText(ctx, "Filtro", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
