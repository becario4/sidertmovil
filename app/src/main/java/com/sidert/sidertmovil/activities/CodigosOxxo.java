package com.sidert.sidertmovil.activities;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.ColorDrawable;
/*import androidx.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;*/
import android.os.Bundle;
/*import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;*/
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.adapters.adapter_codigos_oxxo;
import com.sidert.sidertmovil.database.DBhelper;
import com.sidert.sidertmovil.fragments.dialogs.dialog_generar_codigo_oxxo;
import com.sidert.sidertmovil.models.MCodigoOxxo;
import com.sidert.sidertmovil.models.MResCodigoOxxo;
import com.sidert.sidertmovil.utils.Constants;
import com.sidert.sidertmovil.utils.ManagerInterface;
import com.sidert.sidertmovil.utils.Miscellaneous;
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
import static com.sidert.sidertmovil.utils.NameFragments.DIALOGCODIGOOXXO;

/**Vista para visualizar los codigos oxxo generados y por generar*/
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

        /**Obtencion de datos del prestamo para generacion de codigos oxxo*/
        id_prestamo = getIntent().getLongExtra(ID_PRESTAMO, 0);
        tipo = getIntent().getIntExtra(TIPO,0);
        numPrestamo = getIntent().getStringExtra(NUMERO_DE_PRESTAMO);
        fechaAmortiz = getIntent().getStringExtra(FECHA_AMORTIZACION);
        montoAmortiz = getIntent().getStringExtra(MONTO_AMORTIZACION); //modificar si deseas agregar un monto personalizado
        clave = getIntent().getStringExtra(CLAVE);
        nombre = getIntent().getStringExtra(NOMBRE);

        /**Evento para generar un nuevo codigo oxxo*/
        fbGenerar.setOnClickListener(fbGenerar_OnClick);

        /**Funcion para obtener codigos oxxo ya generados*/
        GetCodigos();

    }

    /**Evento para abrir un dialog para generar el codigo oxxo*/
    private View.OnClickListener fbGenerar_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            dialog_generar_codigo_oxxo dlg_oxxo = new dialog_generar_codigo_oxxo();
            Bundle b = new Bundle();
            b.putString(NOMBRE, nombre);
            b.putString(FECHA_AMORTIZACION, fechaAmortiz);
            b.putString(MONTO_AMORTIZACION,montoAmortiz);
            dlg_oxxo.setArguments(b);

            dlg_oxxo.show(getSupportFragmentManager(), DIALOGCODIGOOXXO);
        }
    };

    /**Funcion para consumir servicio que generar el codigo de oxxo*/
    public void GenerarCodigo(){
        /**Valida si tiene conexion a internet*/
        if (NetworkStatus.haveNetworkConnection(ctx)){
            /**Loading de esperar mientras espera un respuesta*/
            final AlertDialog loading = Popups.showLoadingDialog(ctx,R.string.please_wait, R.string.loading_info);
            loading.show();

            /**Interfaz para realizar peticiones*/
            ManagerInterface api = new RetrofitClient().generalRF(CONTROLLER_CODIGOS, ctx).create(ManagerInterface.class);

            /**Se prepara la peticion colocando los debidos parametros*/
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

            /**Se realiza la peticion para generar el codigo oxxo*/
            call.enqueue(new Callback<MResCodigoOxxo>() {
                @Override
                public void onResponse(Call<MResCodigoOxxo> call, Response<MResCodigoOxxo> response) {
                    Log.e("Codigo",String.valueOf(response.code()));
                    switch (response.code()){
                        case 200:
                            /**Se obtiene el objeto del codigo solicitado y se guarda en el movil*/
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
                            /**Guarda codigo para despues ser compartido por whatsapp*/
                            dBhelper.saveCodigosOxxo(db, params);

                            /**Para actualizar el listado de codigos*/
                            GetCodigos();

                            break;
                        case 201:
                            /**En caso de que ese codigo ya fue generado y no lo tiene en el dispositivo*/
                            MResCodigoOxxo referencia = response.body();
                            MCodigoOxxo item = referencia.getData();
                            /**Se prepara la consulta para saber si no lo tiene registrado*/
                            String sql = "SELECT * FROM " + TBL_CODIGOS_OXXO + " WHERE num_prestamo = ? AND fecha_amortiz = ? AND nombre_pdf = ?";
                            Cursor row = db.rawQuery(sql, new String[]{item.getNumPrestamo(), item.getFechaAmortizacion(), item.getNombrePdf()});
                            Log.e("RowCount", String.valueOf(row.getCount())+"total");
                            if (row.getCount() == 0) {
                                /**En caso de no existir en el movil se registra*/
                                params = new HashMap<>();
                                params.put(0, String.valueOf(referencia.getData().getId()));
                                params.put(1, session.getUser().get(0));
                                params.put(2, referencia.getData().getNumPrestamo());
                                params.put(3, referencia.getData().getFechaAmortizacion());
                                params.put(4, String.valueOf(referencia.getData().getMontoAmortizacion()));
                                params.put(5, referencia.getData().getNombrePdf());
                                params.put(6, referencia.getData().getCreatedAt());
                                params.put(7, referencia.getData().getFechaVencimiento());

                                dBhelper.saveCodigosOxxo(db, params);

                                GetCodigos();
                            }
                            Toast.makeText(ctx, "Ya existe un código generado", Toast.LENGTH_SHORT).show();
                            break;
                        default:

                            break;
                    }

                    loading.dismiss();
                }

                @Override
                public void onFailure(Call<MResCodigoOxxo> call, Throwable t) {
                    Toast.makeText(ctx, "Ha ocurrido un error al generar el PDF de referencias bancarias", Toast.LENGTH_SHORT).show();
                    loading.dismiss();
                }
            });

        }
        else{
            /**No cuenta con conexion de internet*/
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

    /**Funcion para obtener los codigos ya generados y registrados en el movil*/
    private void GetCodigos(){
        /**Se prepara la consulta de obtencion de codigos y ordenados por fecha de creacion*/
        String sql = "SELECT fecha_amortiz, monto_amortiz, nombre_pdf, fecha_vencimiento FROM " + TBL_CODIGOS_OXXO + " WHERE num_prestamo = ? ORDER BY created_at DESC";
        Cursor row = db.rawQuery(sql, new String[]{numPrestamo});

        /**En caso de obtener registros*/
        if (row.getCount() > 0){
            row.moveToFirst();
            ArrayList<MCodigoOxxo> data = new ArrayList<>();
            /**Se recorre el resultado de la consulta*/
            for (int i = 0; i < row.getCount(); i++){
                MCodigoOxxo item = new MCodigoOxxo();
                item.setFechaAmortizacion(row.getString(0));
                item.setMontoAmortizacion(row.getDouble(1));
                item.setNombrePdf(row.getString(2));
                item.setFechaVencimiento(row.getString(3));
                Log.e("Fecha", row.getString(3));
                item.setEnabled(Miscellaneous.IsCurrentWeek(row.getString(3).substring(0,10)));
                data.add(item);
                row.moveToNext();
            }

            adapter = new adapter_codigos_oxxo(ctx, data, new adapter_codigos_oxxo.Event() {
                @Override
                public void CompartirClick(Boolean enabled, String nombrePDF) {
                    /**Evento para compartir la referencia por whatsapp*/
                    if (enabled) {
                        /**Se establece la URL dominio + puerto + controlador + funcion + nombre_archivo.pdf*/
                        String url = session.getDominio().get(0) + session.getDominio().get(1) +
                                WebServicesRoutes.CONTROLLER_FICHAS +
                                WebServicesRoutes.PDF_CODIGOS_OXXO + nombrePDF;

                        //http://sidert.ddns.net:83/api/fichas/uploads/codigos_oxxo/ nombre_del archivo.pdf

                        Log.e("url_pdf", url);

                        /**Se prepara el mensaje para compartir por whatsapp colocando la url generada anteriormente*/
                        String mensaje = "Estimado cliente SIDERT: \n" +
                                "Accede desde este enlace, el cual es el único medio digital oficial para obtener tus referencias bancarias.\n" +
                                "Click Aquí para descargarlo: " + url + " \uD83D\uDC48\n" +
                                "Atención‼️ No aceptes imágenes de tarjetas de débito u otras cuentas que no sean las que descargas desde el link \n" +
                                "Cualquier duda y/o aclaración comunícate al 800 122 6666 \uD83D\uDCDE\n" +
                                "El enlace tiene una vigencia de 48 hrs";

                        /**Se crea el intent para abrir whatsapp*/
                        Intent waIntent = new Intent(Intent.ACTION_SEND);
                        waIntent.setType("text/plain");
                        waIntent.setPackage("com.whatsapp");
                        /**Se verifica si tiene instalado la aplicacion de whatsapp*/
                        if (waIntent != null) {
                            waIntent.putExtra(
                                    Intent.EXTRA_TEXT,
                                    mensaje);
                            startActivity(Intent.createChooser(waIntent, "Share with"));
                        } else
                            Toast.makeText(ctx, "WhatsApp not Installed", Toast.LENGTH_SHORT)
                                    .show();
                    }
                    else{
                        /**En caso de que la referencia ya este caducada por la fecha de amortizacion*/
                        final AlertDialog dlg = Popups.showDialogMessage(ctx, Constants.warning,
                                R.string.referencia_no_disponible, R.string.accept, new Popups.DialogMessage() {
                                    @Override
                                    public void OnClickListener(AlertDialog dialog) {
                                        dialog.dismiss();
                                    }
                                });
                        dlg.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                        dlg.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                        dlg.show();
                    }
                }
            });

            rvCodigos.setAdapter(adapter);
        }
        row.close();
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
