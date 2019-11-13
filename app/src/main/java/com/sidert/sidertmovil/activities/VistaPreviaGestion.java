package com.sidert.sidertmovil.activities;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.utils.Constants;
import com.sidert.sidertmovil.utils.Miscellaneous;
import com.sidert.sidertmovil.utils.Popups;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.UUID;


public class VistaPreviaGestion extends AppCompatActivity {

    private Context ctx;

    private Toolbar tbMain;

    private MapView mapGestion;

    private TextView tvNuevoTelefono;
    private TextView tvMotivoAclaracion;
    private TextView tvMotivoNoPago;
    private TextView tvMotivoNoContacto;
    private TextView tvComentario;
    private TextView tvResultadoGestion;
    private TextView tvMedioPago;
    private TextView tvFecha;
    private TextView tvPagoRealizado;
    private TextView tvSaldoCorte;
    private TextView tvSaldoActual;
    private TextView tvEstusPago;
    private TextView tvEvidencia;
    private TextView tvFirma;
    private TextView tvFolioRecibo;
    private TextView tvNoDetalle;

    private EditText etContacto;
    private EditText etNuevoTelefono;
    private EditText etMotivoAclaracion;
    private EditText etMotivoNoPago;
    private EditText etMotivoNoContacto;
    private EditText etComentario;
    private EditText etResultadoGestion;
    private EditText etMedioPago;
    private EditText etFecha;
    private EditText etPagoRealizado;
    private EditText etEstatusPago;
    private EditText etSaldoCorte;
    private EditText etSaldoActual;
    private EditText etFolioRecibo;
    private EditText etNoDetalle;

    private ImageView ivEvidencia;
    private ImageView ivFirma;

    private Button btnConfirmar;
    private Button btnCancelar;

    private String integrantes = "";

    private JSONObject jsonResp = null;

    private GoogleMap mMap;
    private Marker mMarker;
    private String root = Environment.getExternalStorageDirectory().getAbsolutePath()+"/Android/data/com.sidert.sidertmovil/Files/";

    private Calendar mCalendar;
    private SimpleDateFormat sdf = new SimpleDateFormat(Constants.FORMAT_TIMESTAMP, Locale.US);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_previa_gestion);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        ctx = this;

        tbMain          = findViewById(R.id.tbMain);

        mapGestion  = findViewById(R.id.mapGestion);

        tvNuevoTelefono     = findViewById(R.id.tvNuevoTelefono);
        tvMotivoAclaracion  = findViewById(R.id.tvMotivoAclaracion);
        tvMotivoNoPago      = findViewById(R.id.tvMotivoNoPago);
        tvMotivoNoContacto  = findViewById(R.id.tvMotivoNoContacto);
        tvComentario        = findViewById(R.id.tvComentario);
        tvResultadoGestion  = findViewById(R.id.tvResultadoGestion);
        tvMedioPago         = findViewById(R.id.tvMedioPago);
        tvFecha             = findViewById(R.id.tvFecha);
        tvPagoRealizado     = findViewById(R.id.tvPagoRealizado);
        tvSaldoActual       = findViewById(R.id.tvSaldoActual);
        tvSaldoCorte        = findViewById(R.id.tvSaldoCorte);
        tvEstusPago         = findViewById(R.id.tvEstatusPago);
        tvEvidencia         = findViewById(R.id.tvEvidencia);
        tvFirma             = findViewById(R.id.tvFirma);
        tvFolioRecibo       = findViewById(R.id.tvFolioRecibo);
        tvNoDetalle         = findViewById(R.id.tvNoDetalle);

        etContacto          = findViewById(R.id.etContacto);
        etNuevoTelefono     = findViewById(R.id.etNuevoTelefono);
        etMotivoAclaracion  = findViewById(R.id.etMotivoAclaracion);
        etMotivoNoPago      = findViewById(R.id.etMotivoNoPago);
        etMotivoNoContacto  = findViewById(R.id.etMotivoNoContacto);
        etComentario        = findViewById(R.id.etComentario);
        etResultadoGestion  = findViewById(R.id.etResultadoGestion);
        etMedioPago         = findViewById(R.id.etMedioPago);
        etFecha             = findViewById(R.id.etFecha);
        etPagoRealizado     = findViewById(R.id.etPagoRealizado);
        etEstatusPago       = findViewById(R.id.etEstatusPago);
        etSaldoActual       = findViewById(R.id.etSaldoActual);
        etSaldoCorte        = findViewById(R.id.etSaldoCorte);
        etFolioRecibo       = findViewById(R.id.etFolioRecibo);
        etNoDetalle         = findViewById(R.id.etNoDetalle);

        ivEvidencia     = findViewById(R.id.ivEvidencia);
        ivFirma         = findViewById(R.id.ivFirma);

        btnConfirmar    = findViewById(R.id.btnConfirmar);
        btnCancelar     = findViewById(R.id.btnCancel);

        mCalendar = Calendar.getInstance();
        mapGestion.onCreate(savedInstanceState);
        setSupportActionBar(tbMain);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Bundle datos = this.getIntent().getBundleExtra(Constants.PARAMS);
        jsonResp = new JSONObject();

        ColocarUbicacionGestion(datos.getDouble(Constants.LATITUD), datos.getDouble(Constants.LONGITUD));
        try {
            jsonResp.put(Constants.LATITUD,datos.getDouble(Constants.LATITUD));
            jsonResp.put(Constants.LONGITUD, datos.getDouble(Constants.LONGITUD));
            jsonResp.put(Constants.CONTACTO, datos.getInt(Constants.CONTACTO));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (datos.getInt(Constants.CONTACTO) == 1){ //Si contacto cliente
            etContacto.setText("Contactó al Cliente");
            if (datos.getBoolean(Constants.ACTUALIZAR_TELEFONO)){
                tvNuevoTelefono.setVisibility(View.VISIBLE);
                etNuevoTelefono.setText(datos.getString(Constants.NUEVO_TELEFONO));
                etNuevoTelefono.setVisibility(View.VISIBLE);
                try {
                    jsonResp.put(Constants.NUEVO_TELEFONO, datos.getString(Constants.NUEVO_TELEFONO));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            try {
                jsonResp.put(Constants.ACTUALIZAR_TELEFONO, datos.getBoolean(Constants.ACTUALIZAR_TELEFONO));
                jsonResp.put(Constants.RESULTADO_PAGO, datos.getBoolean(Constants.RESULTADO_PAGO));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (datos.getBoolean(Constants.RESULTADO_PAGO)){ //Pago

                tvResultadoGestion.setVisibility(View.VISIBLE);
                etResultadoGestion.setText("Si Pagó");
                etResultadoGestion.setVisibility(View.VISIBLE);
                etPagoRealizado.setText(datos.getString(Constants.PAGO_REALIZADO));
                etPagoRealizado.setVisibility(View.VISIBLE);

                tvSaldoCorte.setVisibility(View.VISIBLE);
                etSaldoCorte.setText(String.valueOf(datos.getDouble(Constants.SALDO_CORTE)));
                etSaldoCorte.setVisibility(View.VISIBLE);

                tvEstusPago.setVisibility(View.VISIBLE);
                if (datos.getDouble(Constants.MONTO_REQUERIDO,0) - Miscellaneous.doubleFormat(etPagoRealizado) == 0)
                    etEstatusPago.setText(ctx.getResources().getString(R.string.pago_completo));
                else if (datos.getDouble(Constants.MONTO_REQUERIDO,0) - Miscellaneous.doubleFormat(etPagoRealizado) < 0)
                    etEstatusPago.setText(ctx.getResources().getString(R.string.pago_completo_adelanto));
                else if (datos.getDouble(Constants.MONTO_REQUERIDO,0) - Miscellaneous.doubleFormat(etPagoRealizado) > 0)
                    etEstatusPago.setText(ctx.getResources().getString(R.string.pago_parcial));
                else
                    etEstatusPago.setText(ctx.getResources().getString(R.string.pay_status));
                etEstatusPago.setVisibility(View.VISIBLE);

                tvSaldoActual.setVisibility(View.VISIBLE);
                etSaldoActual.setText(String.valueOf(Miscellaneous.doubleFormat(etSaldoCorte) - Miscellaneous.doubleFormat(etPagoRealizado)));
                etSaldoActual.setVisibility(View.VISIBLE);

                try {
                    jsonResp.put(Constants.POS_MEDIO_PAGO, datos.getInt(Constants.POS_MEDIO_PAGO));
                    jsonResp.put(Constants.MEDIO_PAGO, datos.getString(Constants.MEDIO_PAGO));
                    if (datos.containsKey(Constants.PAGO_REQUERIDO))
                        jsonResp.put(Constants.PAGO_REQUERIDO, datos.getBoolean(Constants.PAGO_REQUERIDO));
                    else
                        jsonResp.put(Constants.DETALLE_FICHA, datos.getBoolean(Constants.DETALLE_FICHA));
                    jsonResp.put(Constants.PAGO_REALIZADO, datos.getString(Constants.PAGO_REALIZADO));
                    jsonResp.put(Constants.SALDO_CORTE, datos.getDouble(Constants.SALDO_CORTE));
                    jsonResp.put(Constants.SALDO_ACTUAL, etSaldoActual.getText().toString().trim());
                    jsonResp.put(Constants.ESTATUS, etEstatusPago.getText().toString().trim());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if ((datos.getInt(Constants.POS_MEDIO_PAGO) > 0 && datos.getInt(Constants.POS_MEDIO_PAGO) < 7 || datos.getInt(Constants.POS_MEDIO_PAGO) == 8) &&  !datos.getString(Constants.MEDIO_PAGO).equals(Constants.EFECTIVO)){ //Banco y Oxxo
                    tvMedioPago.setVisibility(View.VISIBLE);
                    etMedioPago.setText(datos.getString(Constants.MEDIO_PAGO));
                    etMedioPago.setVisibility(View.VISIBLE);
                    tvFecha.setVisibility(View.VISIBLE);
                    tvFecha.setText("Fecha de Depósito");
                    etFecha.setText(datos.getString(Constants.FECHA_DEPOSITO));
                    etFecha.setVisibility(View.VISIBLE);
                    tvPagoRealizado.setVisibility(View.VISIBLE);
                    etPagoRealizado.setText(datos.getString(Constants.PAGO_REALIZADO));
                    etPagoRealizado.setVisibility(View.VISIBLE);
                    if (datos.containsKey(Constants.RESUMEN_INTEGRANTES)){
                        if (datos.getBoolean(Constants.RESUMEN_INTEGRANTES)){
                            integrantes = datos.getString(Constants.INTEGRANTES);
                            try {
                                jsonResp.put(Constants.INTEGRANTES, integrantes);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            Log.v("--","---------------------------------------------------------------");
                            Log.v("Integrantes", integrantes);
                            Log.v("--","---------------------------------------------------------------");
                        }
                        else{
                            tvNoDetalle.setVisibility(View.VISIBLE);
                            etNoDetalle.setVisibility(View.VISIBLE);
                            etNoDetalle.setText("No cuenta con el detalle de la ficha");
                        }
                    }
                    tvEvidencia.setVisibility(View.VISIBLE);
                    tvEvidencia.setText("Comprobante");
                    Glide.with(ctx).load(datos.getByteArray(Constants.EVIDENCIA)).centerCrop().into(ivEvidencia);
                    ivEvidencia.setVisibility(View.VISIBLE);

                    if (datos.getBoolean(Constants.GERENTE)) { //Si está el gerente
                        tvFirma.setVisibility(View.VISIBLE);
                        Glide.with(ctx).load(datos.getByteArray(Constants.FIRMA)).into(ivFirma);
                        ivFirma.setVisibility(View.VISIBLE);
                        try {
                            jsonResp.put(Constants.FIRMA, Miscellaneous.save(datos.getByteArray(Constants.FIRMA), 3));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    try {
                        jsonResp.put(Constants.GERENTE, datos.getBoolean(Constants.GERENTE));
                        jsonResp.put(Constants.FECHA_DEPOSITO, datos.getString(Constants.FECHA_DEPOSITO));
                        jsonResp.put(Constants.EVIDENCIA, Miscellaneous.save(datos.getByteArray(Constants.EVIDENCIA), 2));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else if ( datos.getInt(Constants.POS_MEDIO_PAGO) == 7 || datos.getString(Constants.MEDIO_PAGO).equals(Constants.EFECTIVO)){ //Efectivo o SIDERT
                    tvMedioPago.setVisibility(View.VISIBLE);
                    etMedioPago.setText(datos.getString(Constants.MEDIO_PAGO));
                    etMedioPago.setVisibility(View.VISIBLE);
                    tvPagoRealizado.setVisibility(View.VISIBLE);
                    if (datos.containsKey(Constants.RESUMEN_INTEGRANTES)){
                        if (datos.getBoolean(Constants.RESUMEN_INTEGRANTES)){
                            integrantes = datos.getString(Constants.INTEGRANTES);
                            try {
                                jsonResp.put(Constants.INTEGRANTES, integrantes);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        else{
                            tvNoDetalle.setVisibility(View.VISIBLE);
                            etNoDetalle.setVisibility(View.VISIBLE);
                            etNoDetalle.setText("No cuenta con el detalle de la ficha");
                        }
                    }
                    tvEvidencia.setVisibility(View.VISIBLE);
                    tvEvidencia.setText("Comprobante");

                    tvFolioRecibo.setVisibility(View.VISIBLE);
                    etFolioRecibo.setText(datos.getString(Constants.FOLIO_TICKET));
                    etFolioRecibo.setVisibility(View.VISIBLE);

                    Glide.with(ctx).load(datos.getByteArray(Constants.EVIDENCIA)).centerCrop().into(ivEvidencia);
                    ivEvidencia.setVisibility(View.VISIBLE);
                    if (datos.getBoolean(Constants.GERENTE)) { //Si está el gerente
                        tvFirma.setVisibility(View.VISIBLE);
                        Glide.with(ctx).load(datos.getByteArray(Constants.FIRMA)).into(ivFirma);
                        ivFirma.setVisibility(View.VISIBLE);
                        try {
                            jsonResp.put(Constants.FIRMA, Miscellaneous.save(datos.getByteArray(Constants.FIRMA), 3));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    try {
                        jsonResp.put(Constants.GERENTE, datos.getBoolean(Constants.GERENTE));
                        jsonResp.put(Constants.FOLIO_TICKET, datos.getString(Constants.FOLIO_TICKET));
                        jsonResp.put(Constants.IMPRESORA, datos.getBoolean(Constants.IMPRESORA));
                        jsonResp.put(Constants.EVIDENCIA, Miscellaneous.save(datos.getByteArray(Constants.EVIDENCIA), 2));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
                else {
                    Toast.makeText(ctx, "No se encontraron datos", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
            else if (!datos.getBoolean(Constants.RESULTADO_PAGO)){//No Pago
                tvResultadoGestion.setVisibility(View.VISIBLE);
                etResultadoGestion.setText("No Pagó");
                etResultadoGestion.setVisibility(View.VISIBLE);

                try {
                    jsonResp.put(Constants.POS_MOTIVO_NO_PAGO, datos.getInt(Constants.POS_MOTIVO_NO_PAGO));
                    jsonResp.put(Constants.MOTIVO_NO_PAGO, datos.getString(Constants.MOTIVO_NO_PAGO));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (datos.getInt(Constants.POS_MOTIVO_NO_PAGO) == 2){ //Motivo de no pago Fallecimiento
                    tvMotivoNoPago.setVisibility(View.VISIBLE);
                    etMotivoNoPago.setText(datos.getString(Constants.MOTIVO_NO_PAGO));
                    etMotivoNoPago.setVisibility(View.VISIBLE);
                    tvFecha.setVisibility(View.VISIBLE);
                    tvFecha.setText("Fecha de Defunción");
                    etFecha.setText(datos.getString(Constants.FECHA_DEFUNCION));
                    etFecha.setVisibility(View.VISIBLE);
                    tvComentario.setVisibility(View.VISIBLE);
                    etComentario.setText(datos.getString(Constants.COMENTARIO));
                    etComentario.setVisibility(View.VISIBLE);
                    tvEvidencia.setVisibility(View.VISIBLE);
                    Glide.with(ctx).load(datos.getByteArray(Constants.EVIDENCIA)).centerCrop().into(ivEvidencia);
                    ivEvidencia.setVisibility(View.VISIBLE);
                    try {
                        jsonResp.put(Constants.GERENTE, datos.getBoolean(Constants.GERENTE));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if (datos.getBoolean(Constants.GERENTE)) { //Si está el gerente
                        tvFirma.setVisibility(View.VISIBLE);
                        Glide.with(ctx).load(datos.getByteArray(Constants.FIRMA)).into(ivFirma);
                        ivFirma.setVisibility(View.VISIBLE);
                        try {
                            jsonResp.put(Constants.FIRMA, Miscellaneous.save(datos.getByteArray(Constants.FIRMA),3));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    try {
                        jsonResp.put(Constants.FECHA_DEFUNCION, datos.getString(Constants.FECHA_DEFUNCION));
                        jsonResp.put(Constants.COMENTARIO, datos.getString(Constants.COMENTARIO));
                        jsonResp.put(Constants.FACHADA, Miscellaneous.save(datos.getByteArray(Constants.EVIDENCIA),1));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else if (datos.getInt(Constants.POS_MOTIVO_NO_PAGO) ==  1|| datos.getInt(Constants.POS_MOTIVO_NO_PAGO) == 3){ //Motivo de no pago Negación u Otro
                    tvMotivoNoPago.setVisibility(View.VISIBLE);
                    etMotivoNoPago.setText(datos.getString(Constants.MOTIVO_NO_PAGO));
                    etMotivoNoPago.setVisibility(View.VISIBLE);
                    tvComentario.setVisibility(View.VISIBLE);
                    etComentario.setText(datos.getString(Constants.COMENTARIO));
                    etComentario.setVisibility(View.VISIBLE);
                    tvEvidencia.setVisibility(View.VISIBLE);
                    Glide.with(ctx).load(datos.getByteArray(Constants.EVIDENCIA)).centerCrop().into(ivEvidencia);
                    ivEvidencia.setVisibility(View.VISIBLE);
                    try {
                        jsonResp.put(Constants.GERENTE, datos.getBoolean(Constants.GERENTE));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if (datos.getBoolean(Constants.GERENTE)) { //Si está el gerente
                        tvFirma.setVisibility(View.VISIBLE);
                        Glide.with(ctx).load(datos.getByteArray(Constants.FIRMA)).into(ivFirma);
                        ivFirma.setVisibility(View.VISIBLE);
                        try {
                            jsonResp.put(Constants.FIRMA, Miscellaneous.save(datos.getByteArray(Constants.FIRMA),3));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    try {
                        jsonResp.put(Constants.COMENTARIO, datos.getString(Constants.COMENTARIO));
                        jsonResp.put(Constants.FACHADA, Miscellaneous.save(datos.getByteArray(Constants.EVIDENCIA),1));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
                else {
                    Toast.makeText(ctx, "No se encontraron datos", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
            else{
                Toast.makeText(ctx, "No se encontraron datos", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
        else if (datos.getInt(Constants.CONTACTO) == 2){ // Aclaración
            etContacto.setText("Aclaración de Ficha");
            tvMotivoAclaracion.setVisibility(View.VISIBLE);
            etMotivoAclaracion.setText(datos.getString(Constants.MOTIVO_ACLARACION));
            etMotivoAclaracion.setVisibility(View.VISIBLE);
            tvComentario.setVisibility(View.VISIBLE);
            etComentario.setText(datos.getString(Constants.COMENTARIO));
            etComentario.setVisibility(View.VISIBLE);
            if (datos.getBoolean(Constants.GERENTE)) { //Si está el gerente
                tvFirma.setVisibility(View.VISIBLE);
                Glide.with(ctx).load(datos.getByteArray(Constants.FIRMA)).into(ivFirma);
                ivFirma.setVisibility(View.VISIBLE);
                try {
                    jsonResp.put(Constants.FIRMA, Miscellaneous.save(datos.getByteArray(Constants.FIRMA),3));
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                jsonResp.put(Constants.MOTIVO_ACLARACION, datos.getString(Constants.MOTIVO_ACLARACION));
                jsonResp.put(Constants.COMENTARIO, datos.getString(Constants.COMENTARIO));
                jsonResp.put(Constants.GERENTE, datos.getBoolean(Constants.GERENTE));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        else if (datos.getInt(Constants.CONTACTO) == 0){ // No Contacto cliente
            etContacto.setText("No Contactó al Cliente");
            tvComentario.setVisibility(View.VISIBLE);
            etComentario.setVisibility(View.VISIBLE);
            if (datos.containsKey(Constants.MOTIVO_NO_CONTACTO)){
                tvMotivoNoContacto.setVisibility(View.VISIBLE);
                etMotivoNoContacto.setText(datos.getString(Constants.MOTIVO_NO_CONTACTO));
                etMotivoNoContacto.setVisibility(View.VISIBLE);
            }
            etComentario.setText(datos.getString(Constants.COMENTARIO));
            tvEvidencia.setVisibility(View.VISIBLE);
            Glide.with(ctx).load(datos.getByteArray(Constants.EVIDENCIA)).centerCrop().into(ivEvidencia);
            try {
                jsonResp.put(Constants.FACHADA, Miscellaneous.save(datos.getByteArray(Constants.EVIDENCIA),1));
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            ivEvidencia.setVisibility(View.VISIBLE);
            if (datos.getBoolean(Constants.GERENTE)) { //Si está el gerente
                tvFirma.setVisibility(View.VISIBLE);
                Glide.with(ctx).load(datos.getByteArray(Constants.FIRMA)).into(ivFirma);
                try {
                    jsonResp.put(Constants.FIRMA, Miscellaneous.save(datos.getByteArray(Constants.FIRMA),3));
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ivFirma.setVisibility(View.VISIBLE);
            }
            try {
                jsonResp.put(Constants.COMENTARIO, datos.getString(Constants.COMENTARIO));
                jsonResp.put(Constants.GERENTE, datos.getBoolean(Constants.GERENTE));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        else{ //No hay información
            Toast.makeText(ctx, "No se encontraron datos", Toast.LENGTH_SHORT).show();
            finish();
        }

        final AlertDialog success = Popups.showDialogMessage(this, Constants.warning,
                R.string.leyenda_guardar_gestion, R.string.accept, new Popups.DialogMessage() {
                    @Override
                    public void OnClickListener(AlertDialog dialog) {
                        dialog.dismiss();
                    }
                });
        success.setCancelable(false);
        success.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        success.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        success.show();

        btnCancelar.setOnClickListener(btnCancelar_OnClick);
        btnConfirmar.setOnClickListener(btnConfirmar_OnClick);

    }

    private void ColocarUbicacionGestion (final double lat, final double lon){
        mapGestion.onResume();
        try {
            MapsInitializer.initialize(ctx);
        } catch (Exception e) {
            e.printStackTrace();
        }

        mapGestion.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mGooglemap) {
                mMap = mGooglemap;
                if (ActivityCompat.checkSelfPermission(ctx, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                        ActivityCompat.checkSelfPermission(ctx, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                mMap.setMapStyle(
                        MapStyleOptions.loadRawResourceStyle(
                                ctx, R.raw.style_json));
                mMap.getUiSettings().setAllGesturesEnabled(false);
                mMap.getUiSettings().setMapToolbarEnabled(false);

                addMarker(lat,lon);

            }
        });
    }

    private void addMarker (double lat, double lng){
        LatLng coordenadas = new LatLng(lat,lng);

        //LatLng coordenada = new LatLng(19.201745,-96.162134);
        CameraUpdate ubication = CameraUpdateFactory.newLatLngZoom(coordenadas,15);

        mMap.addMarker(new MarkerOptions()
                .position(coordenadas)
                .title(""));

//        mMap.addMarker(new MarkerOptions()
//                .position(coordenada)
//                .title(""));

        mMap.animateCamera(ubication);

//        Polyline line = mMap.addPolyline(new PolylineOptions()
//                .add(new LatLng(lat, lng), new LatLng(19.201745,-96.162134))
//                .width(5)
//                .color(Color.RED));

    }

    private View.OnClickListener btnCancelar_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };

    private View.OnClickListener btnConfirmar_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i_result = new Intent();
            i_result.putExtra(Constants.RESPUESTA_GESTION, jsonResp.toString());
            i_result.putExtra(Constants.RESPONSE, true);
            setResult(RESULT_OK, i_result);
            finish();
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
