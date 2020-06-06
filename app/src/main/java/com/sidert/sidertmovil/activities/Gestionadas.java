package com.sidert.sidertmovil.activities;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Window;
import android.widget.Toast;

import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.adapters.adapter_gestionadas_ind;
import com.sidert.sidertmovil.database.DBhelper;
import com.sidert.sidertmovil.models.MGestionada;
import com.sidert.sidertmovil.models.MRespuestaGestion;
import com.sidert.sidertmovil.utils.Constants;
import com.sidert.sidertmovil.utils.ManagerInterface;
import com.sidert.sidertmovil.utils.Miscellaneous;
import com.sidert.sidertmovil.utils.NetworkStatus;
import com.sidert.sidertmovil.utils.Popups;
import com.sidert.sidertmovil.utils.RetrofitClient;
import com.sidert.sidertmovil.utils.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.sidert.sidertmovil.utils.Constants.ACTUALIZAR_TELEFONO;
import static com.sidert.sidertmovil.utils.Constants.COMENTARIO;
import static com.sidert.sidertmovil.utils.Constants.CONTACTO;
import static com.sidert.sidertmovil.utils.Constants.CONTROLLER_MOVIL;
import static com.sidert.sidertmovil.utils.Constants.ENVIROMENT;
import static com.sidert.sidertmovil.utils.Constants.EVIDENCIA;
import static com.sidert.sidertmovil.utils.Constants.FECHA_DEFUNCION;
import static com.sidert.sidertmovil.utils.Constants.FECHA_DEPOSITO;
import static com.sidert.sidertmovil.utils.Constants.FECHA_PROMESA_PAGO;
import static com.sidert.sidertmovil.utils.Constants.FIRMA;
import static com.sidert.sidertmovil.utils.Constants.FOLIO_TICKET;
import static com.sidert.sidertmovil.utils.Constants.GERENTE;
import static com.sidert.sidertmovil.utils.Constants.ID_PRESTAMO;
import static com.sidert.sidertmovil.utils.Constants.IMPRESORA;
import static com.sidert.sidertmovil.utils.Constants.LATITUD;
import static com.sidert.sidertmovil.utils.Constants.LONGITUD;
import static com.sidert.sidertmovil.utils.Constants.MEDIO_PAGO;
import static com.sidert.sidertmovil.utils.Constants.MONTO_PROMESA;
import static com.sidert.sidertmovil.utils.Constants.MOTIVO_ACLARACION;
import static com.sidert.sidertmovil.utils.Constants.MOTIVO_NO_PAGO;
import static com.sidert.sidertmovil.utils.Constants.NUEVO_TELEFONO;
import static com.sidert.sidertmovil.utils.Constants.PAGO_REALIZADO;
import static com.sidert.sidertmovil.utils.Constants.PAGO_REQUERIDO;
import static com.sidert.sidertmovil.utils.Constants.PARAMS;
import static com.sidert.sidertmovil.utils.Constants.RESULTADO_PAGO;
import static com.sidert.sidertmovil.utils.Constants.TBL_CARTERA_IND_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_NAME;
import static com.sidert.sidertmovil.utils.Constants.TBL_PRESTAMOS_IND_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_RESPUESTAS_GPO;
import static com.sidert.sidertmovil.utils.Constants.TBL_RESPUESTAS_GPO_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_RESPUESTAS_IND;
import static com.sidert.sidertmovil.utils.Constants.TBL_RESPUESTAS_IND_T;
import static com.sidert.sidertmovil.utils.Constants.TIMESTAMP;
import static com.sidert.sidertmovil.utils.Constants.TIPO_GESTION;
import static com.sidert.sidertmovil.utils.Constants.TIPO_IMAGEN;
import static com.sidert.sidertmovil.utils.Constants.TIPO_PRESTAMO;

public class Gestionadas extends AppCompatActivity {

    private Context ctx;
    private Toolbar tbMain;

    private RecyclerView rvGestionadas;

    private adapter_gestionadas_ind adapter;

    private DBhelper dBhelper;
    private SQLiteDatabase db;

    private String id_prestamo = "0";

    private String tbl = "";

    private String tipo_prestamo = "";
    private int tipo_gestion  = 0;

    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestiondas);

        ctx = this;

        session = new SessionManager(ctx);

        dBhelper = new DBhelper(ctx);
        db = dBhelper.getWritableDatabase();

        tbMain = findViewById(R.id.TBmain);

        rvGestionadas = findViewById(R.id.rvGestionadas);

        rvGestionadas.setLayoutManager(new LinearLayoutManager(ctx));
        rvGestionadas.setHasFixedSize(false);

        id_prestamo = getIntent().getStringExtra(ID_PRESTAMO);
        tbl = getIntent().getStringExtra(TBL_NAME);
        tipo_prestamo = getIntent().getStringExtra(TIPO_PRESTAMO);
        tipo_gestion = getIntent().getIntExtra(TIPO_GESTION, 0);

        init();
    }

    private void init (){
        Cursor row = null;
        row = dBhelper.getRecords(tbl, " WHERE id_prestamo = ? AND estatus  IN(?,?)", " ORDER BY fecha_inicio DESC", new String[]{id_prestamo, "1","2" });

        if (row.getCount() > 0){
            row.moveToFirst();
            ArrayList<MGestionada> data = new ArrayList<>();
            for(int i = 0; i < row.getCount(); i++){
                MGestionada gestionadas = new MGestionada();
                gestionadas.setIdGestion(row.getString(0));
                if (tipo_gestion == 2 && tipo_prestamo.equals("VENCIDA"))
                    gestionadas.setFechaGestion(row.getString(24));
                else
                    gestionadas.setFechaGestion(row.getString(23));

                String contacto = "";
                if (tipo_gestion == 2 && tipo_prestamo.equals("VENCIDA"))
                    contacto = row.getString(5);
                else
                    contacto = row.getString(4);

                switch (contacto){
                    case "SI":
                        gestionadas.setContacto("SI CONTACTO");
                        if (tipo_prestamo.equals("VIGENTE") || tipo_gestion == 2 && tipo_prestamo.equals("VENCIDA"))
                            gestionadas.setResultado(row.getString(9));
                        else
                            gestionadas.setResultado(row.getString(8));
                        if (tipo_prestamo.equals("VIGENTE") && row.getString(9).equals("PAGO") || tipo_prestamo.equals("VENCIDA") && row.getString(8).equals("PAGO")) {
                            if (tipo_prestamo.equals("VIGENTE")) {
                                gestionadas.setComentarioBanco("MEDIO PAGO: " + row.getString(12));
                                gestionadas.setMonto(Miscellaneous.moneyFormat(row.getString(15)));
                            }
                            else if(tipo_gestion == 1 && tipo_prestamo.equals("VENCIDA")){
                                gestionadas.setComentarioBanco("MEDIO PAGO: " + row.getString(13));
                                gestionadas.setMonto(Miscellaneous.moneyFormat(row.getString(16)));
                            }
                            else if(tipo_gestion == 2 && tipo_prestamo.equals("VENCIDA")){
                                gestionadas.setComentarioBanco("MEDIO PAGO: " + row.getString(14));
                                gestionadas.setMonto(Miscellaneous.moneyFormat(row.getString(17)));
                            }

                        }
                        else {
                            if (tipo_prestamo.equals("VIGENTE") || tipo_gestion == 2 && tipo_prestamo.equals("VENCIDA"))
                                gestionadas.setComentarioBanco("COMENTARIO: " + row.getString(6));
                            else
                                gestionadas.setComentarioBanco("COMENTARIO: " + row.getString(5));
                            gestionadas.setMonto("");
                        }
                        break;
                    case "NO":
                        gestionadas.setContacto("NO CONTACTO");
                        if (tipo_prestamo.equals("VIGENTE") || tipo_gestion == 2 && tipo_prestamo.equals("VENCIDA")) {
                            gestionadas.setComentarioBanco("COMENTARIO: " + row.getString(6));
                            gestionadas.setResultado(row.getString(9));
                        }
                        else {
                            gestionadas.setComentarioBanco("COMENTARIO: " + row.getString(5));
                            gestionadas.setResultado(row.getString(8));
                        }

                        gestionadas.setMonto("");
                        break;
                    case "ACLARACION":
                        gestionadas.setContacto("ACLARACION");
                        if (tipo_prestamo.equals("VIGENTE"))
                            gestionadas.setComentarioBanco("MOTIVO: " + row.getString(5));
                        gestionadas.setResultado("");
                        gestionadas.setMonto("");
                        break;
                }

                data.add(gestionadas);
                Log.e("Gestionadas", Miscellaneous.ConvertToJson(gestionadas));
                row.moveToNext();
            }

            adapter = new adapter_gestionadas_ind(ctx, data, new adapter_gestionadas_ind.Event() {

                @Override
                public void GestionadaClick(MGestionada item) {
                    Cursor row;
                    row = dBhelper.getRecords(tbl, " WHERE _id = ?", "", new String[]{item.getIdGestion()});

                    if (row.getCount() > 0) {
                        row.moveToFirst();
                        Bundle b = new Bundle();
                        String contacto = "";
                        if (tipo_gestion == 2 && tipo_prestamo.equals("VENCIDA")) {
                            b.putDouble(LATITUD, row.getDouble(3));
                            b.putDouble(LONGITUD, row.getDouble(4));
                            b.putString(CONTACTO, row.getString(5));
                            contacto = row.getString(5);
                        }
                        else{
                            b.putDouble(LATITUD, row.getDouble(2));
                            b.putDouble(LONGITUD, row.getDouble(3));
                            b.putString(CONTACTO, row.getString(4));
                            contacto = row.getString(4);
                        }
                        switch (contacto) {
                            case "SI":
                                b.putString(ACTUALIZAR_TELEFONO, row.getString(7));
                                if (row.getString(7).equals("SI"))
                                    b.putString(NUEVO_TELEFONO, row.getString(8));

                                String medio_pago = "";
                                if (tipo_prestamo.equals("VIGENTE") || tipo_gestion == 2 && tipo_prestamo.equals("VENCIDA")) {
                                    b.putString(RESULTADO_PAGO, row.getString(9));
                                    medio_pago = row.getString(9);
                                } else if (tipo_gestion == 1 && tipo_prestamo.equals("VENCIDA")){
                                    b.putString(RESULTADO_PAGO, row.getString(8));
                                    medio_pago = row.getString(8);
                                }

                                switch (medio_pago) {
                                    case "PAGO":
                                        if (tipo_prestamo.equals("VIGENTE")) {
                                            b.putString(MEDIO_PAGO, row.getString(12));
                                            b.putString(FECHA_DEPOSITO, row.getString(13));
                                            b.putString(PAGO_REQUERIDO, row.getString(14));
                                            b.putString(PAGO_REALIZADO, row.getString(15));
                                            if (row.getString(12).equals("EFECTIVO") || row.getString(12).equals("SIDERT")) {
                                                b.putString(IMPRESORA, row.getString(16));
                                                b.putString(FOLIO_TICKET, row.getString(17));
                                            }
                                        } else if (tipo_gestion == 1 && tipo_prestamo.equals("VENCIDA")){
                                            b.putString(MEDIO_PAGO, row.getString(13));
                                            b.putString(FECHA_DEPOSITO, row.getString(14));
                                            b.putString(PAGO_REQUERIDO, row.getString(15));
                                            b.putString(PAGO_REALIZADO, row.getString(16));
                                            if (row.getString(13).equals("EFECTIVO") || row.getString(13).equals("SIDERT")) {
                                                b.putString(IMPRESORA, row.getString(17));
                                                b.putString(FOLIO_TICKET, row.getString(18));
                                            }
                                        }
                                        else if (tipo_gestion == 2 && tipo_prestamo.equals("VENCIDA")){
                                            b.putString(MEDIO_PAGO, row.getString(14));
                                            b.putString(FECHA_DEPOSITO, row.getString(15));
                                            b.putString(PAGO_REQUERIDO, row.getString(16));
                                            b.putString(PAGO_REALIZADO, row.getString(17));
                                            if (row.getString(14).equals("EFECTIVO") || row.getString(17).equals("SIDERT")) {
                                                b.putString(IMPRESORA, row.getString(18));
                                                b.putString(FOLIO_TICKET, row.getString(19));
                                            }
                                        }
                                        break;
                                    case "NO PAGO":
                                        if (tipo_prestamo.equals("VIGENTE") || tipo_gestion == 2 && tipo_prestamo.equals("VENCIDA")) {
                                            b.putString(MOTIVO_NO_PAGO, row.getString(10));
                                            if (row.getString(10).equals("FALLECIMIENTO"))
                                                b.putString(FECHA_DEFUNCION, row.getString(11));
                                            else if (row.getString(9).equals("PROMESA DE PAGO")){
                                                b.putString(FECHA_PROMESA_PAGO, row.getString(12));
                                                b.putString(MONTO_PROMESA, row.getString(13).trim().replace(",",""));
                                            }
                                            b.putString(COMENTARIO, row.getString(6));
                                        } else {
                                            b.putString(MOTIVO_NO_PAGO, row.getString(9));
                                            if (row.getString(9).equals("FALLECIMIENTO"))
                                                b.putString(FECHA_DEFUNCION, row.getString(10));
                                            else if (row.getString(9).equals("PROMESA DE PAGO")){
                                                b.putString(FECHA_PROMESA_PAGO, row.getString(11));
                                                b.putString(MONTO_PROMESA, row.getString(12).trim().replace(",",""));
                                            }

                                            b.putString(COMENTARIO, row.getString(5));
                                        }
                                        break;
                                }
                                break;
                            case "NO":
                                if (tipo_prestamo.equals("VIGENTE") || tipo_gestion == 2 && tipo_prestamo.equals("VENCIDA"))
                                    b.putString(COMENTARIO, row.getString(6));
                                else
                                    b.putString(COMENTARIO, row.getString(5));
                                break;
                            case "ACLARACION":
                                if (tipo_prestamo.equals("VIGENTE")) {
                                    b.putString(MOTIVO_ACLARACION, row.getString(5));
                                    b.putString(COMENTARIO, row.getString(6));
                                } else if (tipo_gestion == 1 && tipo_prestamo.equals("VENCIDA"))
                                    b.putString(COMENTARIO, row.getString(5));
                                else if (tipo_gestion == 2 && tipo_prestamo.equals("VENCIDA"))
                                    b.putString(COMENTARIO, row.getString(6));
                                break;
                        }

                        if (tipo_prestamo.equals("VIGENTE")){
                            b.putString(EVIDENCIA, row.getString(18));
                            b.putString(TIPO_IMAGEN, row.getString(19));
                            b.putString(GERENTE, row.getString(20));
                            if (row.getString(20).equals("SI"))
                                b.putString(FIRMA, row.getString(21));
                        }
                        else if (tipo_gestion == 1 && tipo_prestamo.equals("VENCIDA")){
                            b.putString(EVIDENCIA, row.getString(19));
                            b.putString(TIPO_IMAGEN, row.getString(20));
                            b.putString(GERENTE, row.getString(21));
                            if (row.getString(21).equals("SI"))
                                b.putString(FIRMA, row.getString(22));
                        }
                        else if (tipo_gestion == 2 && tipo_prestamo.equals("VENCIDA")){
                            b.putString(EVIDENCIA, row.getString(20));
                            b.putString(TIPO_IMAGEN, row.getString(21));
                            b.putString(GERENTE, row.getString(22));
                            if (row.getString(22).equals("SI"))
                                b.putString(FIRMA, row.getString(23));
                        }

                        Log.e("SIDERTMOVIL", b.toString());

                        Intent i_preview = new Intent(ctx, VistaGestion.class);
                        i_preview.putExtra(PARAMS,b);
                        startActivity(i_preview);
                    }
                }

                @Override
                public void SendClickLong(MGestionada item) {
                    //Toast.makeText(ctx, "Enviando Gestion", Toast.LENGTH_SHORT).show();
                    //SendForceGestion(item);
                }
            });

            rvGestionadas.setAdapter(adapter);
        }
    }

    private void SendForceGestion(final MGestionada item){
        if (NetworkStatus.haveNetworkConnection(ctx)){
            final AlertDialog loading = Popups.showLoadingDialog(ctx,R.string.please_wait, R.string.loading_info);
            loading.show();

            Cursor row;

            String query = "SELECT i._id,i.id_prestamo,i.latitud,i.longitud,i.contacto,i.motivo_aclaracion,i.comentario,i.actualizar_telefono,i.nuevo_telefono,i.resultado_gestion,i.motivo_no_pago,i.fecha_fallecimiento,i.medio_pago,i.fecha_pago,i.pagara_requerido AS x,i.pago_realizado,i.imprimir_recibo,i.folio,i.evidencia,i.tipo_imagen,i.gerente,i.firma,i.fecha_inicio,i.fecha_fin,i.res_impresion,i.estatus_pago,i.saldo_corte,i.saldo_actual,'1' AS tipo_gestion,pi.num_solicitud,pi.fecha_establecida, ci.dia AS dia_semana, pi.monto_requerido, pi.tipo_cartera, pi.monto_amortizacion, i.dias_atraso FROM "+ TBL_RESPUESTAS_IND_T + " AS i INNER JOIN " + TBL_PRESTAMOS_IND_T + " AS pi ON i.id_prestamo = pi.id_prestamo INNER JOIN " + TBL_CARTERA_IND_T + " AS ci ON pi.id_cliente = ci.id_cartera WHERE i._id = ?";

            row = db.rawQuery(query, new String[]{item.getIdGestion()});

            if (row.getCount() > 0){
                row.moveToFirst();

                for (int i = 0; i < row.getCount(); i++){
                    HashMap<String, String> params = new HashMap<>();
                    params.put("id_prestamo", row.getString(1));
                    params.put("num_solicitud", row.getString(29));
                    JSONObject json_res = Miscellaneous.RowTOJson(row, ctx);
                    params.put("respuesta", json_res.toString());

                    try {
                        String evidencia = "";
                        String tipo_imagen = "-1";
                        String firma = "";
                        if (json_res.has("evidencia"))
                            evidencia = json_res.getString("evidencia");
                        if (json_res.has("tipo_imagen"))
                            tipo_imagen = json_res.getString("tipo_imagen");
                        if (json_res.has("firma"))
                            firma = json_res.getString("firma");

                        Log.e("res_envio", json_res.toString());

                        RequestBody idPrestamoBody = RequestBody.create(MultipartBody.FORM, params.get("id_prestamo"));
                        RequestBody numSolicitudBody = RequestBody.create(MultipartBody.FORM, params.get("num_solicitud"));
                        RequestBody respuestaBody = RequestBody.create(MultipartBody.FORM, params.get("respuesta"));


                        MultipartBody.Part evidenciaBody = null;
                        File imagen_evidencia = null;

                        switch (tipo_imagen){
                            case "0":
                                imagen_evidencia = new File(Constants.ROOT_PATH + "Fachada/" + evidencia);
                                break;
                            case "1":
                            case "2":
                                imagen_evidencia = new File(Constants.ROOT_PATH + "Evidencia/" + evidencia);
                                break;
                        }

                        if (!evidencia.isEmpty() && imagen_evidencia != null) {
                            RequestBody imageBody =
                                    RequestBody.create(
                                            MediaType.parse("image/*"), imagen_evidencia);

                            evidenciaBody = MultipartBody.Part.createFormData("evidencia", imagen_evidencia.getName(), imageBody);
                        }
                        else {
                            RequestBody attachmentEmpty = RequestBody.create(MediaType.parse("text/plain"), "");
                            evidenciaBody = MultipartBody.Part.createFormData("evidencia", "", attachmentEmpty);
                        }

                        MultipartBody.Part firmaBody = null;
                        final File image_firma = new File(Constants.ROOT_PATH + "Firma/"+firma);
                        if (!firma.isEmpty() && image_firma != null) {
                            RequestBody imageBody =
                                    RequestBody.create(
                                            MediaType.parse("image/*"), image_firma);

                            firmaBody = MultipartBody.Part.createFormData("firma", image_firma.getName(), imageBody);
                        }
                        else {
                            RequestBody attachmentEmpty = RequestBody.create(MediaType.parse("text/plain"), "");
                            firmaBody = MultipartBody.Part.createFormData("firma", "", attachmentEmpty);
                        }

                        ManagerInterface api = new RetrofitClient().generalRF(CONTROLLER_MOVIL, ctx).create(ManagerInterface.class);

                        Log.e("idPRestamo", params.get("id_prestamo") );
                        Log.e("numSolicitud", params.get("num_solicitud"));
                        Log.e("RespuestaGes", params.get("respuesta"));
                        Call<MRespuestaGestion> call = api.guardarRespuesta("Bearer "+ session.getUser().get(7),
                                idPrestamoBody,
                                numSolicitudBody,
                                respuestaBody,
                                respuestaBody,
                                evidenciaBody,
                                firmaBody);

                        call.enqueue(new Callback<MRespuestaGestion>() {
                            @Override
                            public void onResponse(Call<MRespuestaGestion> call, Response<MRespuestaGestion> response) {
                                Log.e("Response", "Code: "+response.code());
                                switch (response.code()){
                                    case 200:
                                        Toast.makeText(ctx, "Ficha Sincronizada con éxito", Toast.LENGTH_SHORT).show();
                                        MRespuestaGestion r = response.body();
                                        ContentValues cv = new ContentValues();
                                        cv.put("fecha_envio", Miscellaneous.ObtenerFecha(TIMESTAMP));
                                        cv.put("estatus", 2);

                                        db.update(TBL_RESPUESTAS_IND_T, cv, "_id = ?", new String[]{item.getIdGestion()});

                                        break;
                                    default:
                                        //Log.e("Mensaje Code", response.code()+" : "+response.message());
                                        Toast.makeText(ctx, "No se logró enviar codigo: " +response.code(), Toast.LENGTH_SHORT).show();
                                        break;
                                }
                                loading.dismiss();
                                Toast.makeText(ctx, "Ficha Sincronizada con éxito", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(Call<MRespuestaGestion> call, Throwable t) {
                                Log.e("FailSaveImagexxxxxx", t.getMessage());
                                loading.dismiss();
                                Toast.makeText(ctx, "Falló al Sincronizar la ficha", Toast.LENGTH_SHORT).show();
                            }
                        });

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    row.moveToNext();
                }

            }
            row.close();
        }else{
            final AlertDialog error_network = Popups.showDialogMessage(ctx, Constants.not_network,
                    R.string.not_network, R.string.accept, new Popups.DialogMessage() {
                        @Override
                        public void OnClickListener(AlertDialog dialog) {
                            dialog.dismiss();
                        }
                    });
            error_network.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            error_network.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            error_network.show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        init();
    }
}
