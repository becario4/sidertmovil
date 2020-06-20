package com.sidert.sidertmovil.activities;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.adapters.adapter_codigos_oxxo;
import com.sidert.sidertmovil.database.DBhelper;
import com.sidert.sidertmovil.fragments.dialogs.dialog_generar_codigo_oxxo;
import com.sidert.sidertmovil.models.MCierreDia;
import com.sidert.sidertmovil.models.MCodigoOxxo;
import com.sidert.sidertmovil.models.MPrestamo;
import com.sidert.sidertmovil.models.MResCodigoOxxo;
import com.sidert.sidertmovil.utils.Constants;
import com.sidert.sidertmovil.utils.ManagerInterface;
import com.sidert.sidertmovil.utils.Miscellaneous;
import com.sidert.sidertmovil.utils.NameFragments;
import com.sidert.sidertmovil.utils.NetworkStatus;
import com.sidert.sidertmovil.utils.Popups;
import com.sidert.sidertmovil.utils.RetrofitClient;
import com.sidert.sidertmovil.utils.SessionManager;
import com.sidert.sidertmovil.utils.WebServicesRoutes;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.sidert.sidertmovil.utils.Constants.CLAVE;
import static com.sidert.sidertmovil.utils.Constants.CONTROLLER_CODIGOS;
import static com.sidert.sidertmovil.utils.Constants.FECHA_AMORTIZACION;
import static com.sidert.sidertmovil.utils.Constants.ID_PRESTAMO;
import static com.sidert.sidertmovil.utils.Constants.MONTO_AMORTIZACION;
import static com.sidert.sidertmovil.utils.Constants.NOMBRE;
import static com.sidert.sidertmovil.utils.Constants.NUMERO_DE_PRESTAMO;
import static com.sidert.sidertmovil.utils.Constants.TBL_CODIGOS_OXXO;
import static com.sidert.sidertmovil.utils.Constants.TIPO;
import static com.sidert.sidertmovil.utils.Constants.bank;
import static com.sidert.sidertmovil.utils.NameFragments.DIALOGCODIGOOXXO;

public class CodigosOxxo extends AppCompatActivity {

    private Context ctx;
    private Toolbar tbMain;
    private RecyclerView rvCodigos;
    private adapter_codigos_oxxo adapter;

    private DBhelper dBhelper;
    private SQLiteDatabase db;

    private Long id_prestamo;
    private int tipo;
    private String numPrestamo = "";
    private String fechaAmortiz = "";
    private String montoAmortiz = "";
    private String clave = "";
    private String nombre = "";

    private SessionManager session;

    private FloatingActionButton fbGenerar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_codigos_oxxo);

        ctx = this;

        session = new SessionManager(ctx);

        dBhelper = new DBhelper(ctx);
        db = dBhelper.getWritableDatabase();

        tbMain = findViewById(R.id.tbMain);

        setSupportActionBar(tbMain);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        rvCodigos = findViewById(R.id.rvCodigos);

        rvCodigos.setLayoutManager(new LinearLayoutManager(ctx));
        rvCodigos.setHasFixedSize(false);

        fbGenerar = findViewById(R.id.fbGenerar);

        id_prestamo = getIntent().getLongExtra(ID_PRESTAMO, 0);
        tipo = getIntent().getIntExtra(TIPO,0);
        numPrestamo = getIntent().getStringExtra(NUMERO_DE_PRESTAMO);
        fechaAmortiz = getIntent().getStringExtra(FECHA_AMORTIZACION);
        montoAmortiz = getIntent().getStringExtra(MONTO_AMORTIZACION);
        clave = getIntent().getStringExtra(CLAVE);
        nombre = getIntent().getStringExtra(NOMBRE);

        fbGenerar.setOnClickListener(fbGenerar_OnClick);

        GetCodigos();

    }

    private View.OnClickListener fbGenerar_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            dialog_generar_codigo_oxxo dlg_oxxo = new dialog_generar_codigo_oxxo();
            Bundle b = new Bundle();
            b.putString(NOMBRE, nombre);
            b.putString(FECHA_AMORTIZACION, fechaAmortiz);
            b.putString(MONTO_AMORTIZACION, montoAmortiz);
            dlg_oxxo.setArguments(b);

            dlg_oxxo.show(getSupportFragmentManager(), DIALOGCODIGOOXXO);
        }
    };

    public void GenerarCodigo(){
        if (NetworkStatus.haveNetworkConnection(ctx)){
            final AlertDialog loading = Popups.showLoadingDialog(ctx,R.string.please_wait, R.string.loading_info);
            loading.show();

            ManagerInterface api = new RetrofitClient().generalRF(CONTROLLER_CODIGOS, ctx).create(ManagerInterface.class);

            Call<MResCodigoOxxo> call = api.generarCodigo(session.getUser().get(7),
                    session.getUser().get(9),
                    numPrestamo,
                    fechaAmortiz,
                    montoAmortiz,
                    tipo,
                    id_prestamo,
                    clave,
                    nombre,
                    session.getUser().get(1)+" "+session.getUser().get(2)+" "+session.getUser().get(3),
                    Miscellaneous.GetFechaDomingo());

            call.enqueue(new Callback<MResCodigoOxxo>() {
                @Override
                public void onResponse(Call<MResCodigoOxxo> call, Response<MResCodigoOxxo> response) {
                    Log.e("Codigo",String.valueOf(response.code()));
                    switch (response.code()){
                        case 200:
                            MResCodigoOxxo oxxo = response.body();
                            HashMap<Integer, String> params = new HashMap<>();
                            params.put(0, String.valueOf(oxxo.getData().getId()));
                            params.put(1, session.getUser().get(0));
                            params.put(2, oxxo.getData().getNumPrestamo());
                            params.put(3, oxxo.getData().getFechaAmortizacion());
                            params.put(4, String.valueOf(oxxo.getData().getMontoAmortizacion()));
                            params.put(5, oxxo.getData().getNombrePdf());
                            params.put(6, oxxo.getData().getCreatedAt());
                            params.put(7, oxxo.getData().getFechaVencimiento());

                            dBhelper.saveCodigosOxxo(db, params);

                            GetCodigos();

                            break;
                        case 201:
                            Toast.makeText(ctx, "Ya existe un código generado", Toast.LENGTH_SHORT).show();
                            break;
                        default:

                            break;
                    }

                    loading.dismiss();
                }

                @Override
                public void onFailure(Call<MResCodigoOxxo> call, Throwable t) {
                    Log.e("Error", "fail Codigo"+t.getMessage());
                }
            });

        }
        else{
            final AlertDialog error_connect = Popups.showDialogMessage(ctx, Constants.not_network,
                    R.string.not_network, R.string.accept, new Popups.DialogMessage() {
                        @Override
                        public void OnClickListener(AlertDialog dialog) {
                            dialog.dismiss();
                        }
                    });
            error_connect.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            error_connect.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            error_connect.show();
        }
    }

    private void GetCodigos(){
        String sql = "SELECT fecha_amortiz, monto_amortiz, nombre_pdf, fecha_vencimiento FROM " + TBL_CODIGOS_OXXO + " WHERE num_prestamo = ? ORDER BY created_at DESC";
        //String sql = "SELECT fecha_amortiz, monto_amortiz, nombre_pdf FROM " + TBL_CODIGOS_OXXO + " WHERE fecha_amortiz >= ?";
        Cursor row = db.rawQuery(sql, new String[]{numPrestamo});
        //Cursor row = db.rawQuery(sql, new String[]{Miscellaneous.ObtenerFecha("fecha_atraso")});

        Log.e("CodigosGenrrados", ""+row.getCount());
        if (row.getCount() > 0){
            row.moveToFirst();
            ArrayList<MCodigoOxxo> data = new ArrayList<>();
            for (int i = 0; i < row.getCount(); i++){
                MCodigoOxxo item = new MCodigoOxxo();
                item.setFechaAmortizacion(row.getString(0));
                item.setMontoAmortizacion(row.getDouble(1));
                item.setNombrePdf(row.getString(2));
                item.setFechaVencimiento(row.getString(3));
                data.add(item);
                row.moveToNext();
            }

            adapter = new adapter_codigos_oxxo(ctx, data, new adapter_codigos_oxxo.Event() {
                @Override
                public void CompartirClick(String nombrePDF) {
                    String url = session.getDominio().get(0)+session.getDominio().get(1)+ WebServicesRoutes.CONTROLLER_FICHAS +
                            WebServicesRoutes.PDF_CODIGOS_OXXO + nombrePDF;

                    Log.e("url_pdf", url);

                    String mensaje = "Estimado cliente de SIDERT: \n" +
                            "Accede desde este enlace, el cual es el único medio digital oficial para obtener tus referencias bancarias.\n" +
                            "Click Aquí para descargarlo: " + url + " \uD83D\uDC48\n" +
                            "Atención‼️ No aceptes imágenes y/o archivos adjuntos \n" +
                            "Cualquier duda y/o aclaración comunícate al 01 800 122 6666 \uD83D\uDCDE\n" +
                            "El enlace tiene una vigencia de 48 hrs";
                    Intent waIntent = new Intent(Intent.ACTION_SEND);
                    waIntent.setType("text/plain");
                    waIntent.setPackage("com.whatsapp");
                    if (waIntent != null) {
                        waIntent.putExtra(
                                Intent.EXTRA_TEXT,
                                mensaje);
                        startActivity(Intent.createChooser(waIntent, "Share with"));
                    } else
                        Toast.makeText(ctx, "WhatsApp not Installed", Toast.LENGTH_SHORT)
                                .show();
                }
            });

            rvCodigos.setAdapter(adapter);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 951:
                if (resultCode == 159){
                    if (data != null){
                        GenerarCodigo();
                    }
                }
                break;
        }
    }
}
