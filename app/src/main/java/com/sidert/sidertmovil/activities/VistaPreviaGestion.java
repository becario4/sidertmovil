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

import static com.sidert.sidertmovil.utils.Constants.ESTATUS;
import static com.sidert.sidertmovil.utils.Constants.SALDO_ACTUAL;
import static com.sidert.sidertmovil.utils.Constants.SALDO_CORTE;


public class VistaPreviaGestion extends AppCompatActivity {

    private Context ctx;

    private Toolbar tbMain;

    private MapView mapGestion;

    private TextView NuevoTelefono;
    private TextView MotivoAclaracion;
    private TextView MotivoNoPago;
    private TextView MotivoNoContacto;
    private TextView Comentario;
    private TextView ResultadoGestion;
    private TextView MedioPago;
    private TextView Fecha;
    private TextView PagoRealizado;
    private TextView SaldoCorte;
    private TextView SaldoActual;
    private TextView EstusPago;
    private TextView Evidencia;
    private TextView Firma;
    private TextView FolioRecibo;
    private TextView NoDetalle;

    private TextView etContacto;
    private TextView etNuevoTelefono;
    private TextView etMotivoAclaracion;
    private TextView etMotivoNoPago;
    private TextView etMotivoNoContacto;
    private TextView etComentario;
    private TextView etResultadoGestion;
    private TextView etMedioPago;
    private TextView etFecha;
    private TextView etPagoRealizado;
    private TextView etEstatusPago;
    private TextView etSaldoCorte;
    private TextView etSaldoActual;
    private TextView etFolioRecibo;
    private TextView etNoDetalle;

    private ImageView ivEvidencia;
    private ImageView ivFirma;

    private Button btnConfirmar;
    private Button btnCancelar;

    private String integrantes = "";

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

        NuevoTelefono     = findViewById(R.id.NuevoTelefono);
        MotivoAclaracion  = findViewById(R.id.MotivoAclaracion);
        MotivoNoPago      = findViewById(R.id.MotivoNoPago);
        MotivoNoContacto  = findViewById(R.id.MotivoNoContacto);
        Comentario        = findViewById(R.id.Comentario);
        ResultadoGestion  = findViewById(R.id.ResultadoGestion);
        MedioPago         = findViewById(R.id.MedioPago);
        Fecha             = findViewById(R.id.Fecha);
        PagoRealizado     = findViewById(R.id.PagoRealizado);
        SaldoActual       = findViewById(R.id.SaldoActual);
        SaldoCorte        = findViewById(R.id.SaldoCorte);
        EstusPago         = findViewById(R.id.EstatusPago);
        Evidencia         = findViewById(R.id.Evidencia);
        Firma             = findViewById(R.id.Firma);
        FolioRecibo       = findViewById(R.id.FolioRecibo);
        NoDetalle         = findViewById(R.id.NoDetalle);

        etContacto          = findViewById(R.id.tvContacto);
        etNuevoTelefono     = findViewById(R.id.tvNuevoTelefono);
        etMotivoAclaracion  = findViewById(R.id.tvMotivoAclaracion);
        etMotivoNoPago      = findViewById(R.id.tvMotivoNoPago);
        etMotivoNoContacto  = findViewById(R.id.tvMotivoNoContacto);
        etComentario        = findViewById(R.id.tvComentario);
        etResultadoGestion  = findViewById(R.id.tvResultadoGestion);
        etMedioPago         = findViewById(R.id.tvMedioPago);
        etFecha             = findViewById(R.id.tvFecha);
        etPagoRealizado     = findViewById(R.id.tvPagoRealizado);
        etEstatusPago       = findViewById(R.id.tvEstatusPago);
        etSaldoActual       = findViewById(R.id.tvSaldoActual);
        etSaldoCorte        = findViewById(R.id.tvSaldoCorte);
        etFolioRecibo       = findViewById(R.id.tvFolioRecibo);
        etNoDetalle         = findViewById(R.id.tvNoDetalle);

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

        Log.e("DatosEnviar", datos.toString());
        ColocarUbicacionGestion(datos.getDouble(Constants.LATITUD), datos.getDouble(Constants.LONGITUD));

        if (datos.getString(Constants.CONTACTO).equals("SI")){ //Si contacto cliente
            etContacto.setText("SI CONTACTO AL CLIENTE");
            if (datos.getString(Constants.ACTUALIZAR_TELEFONO).equals("SI")){
                NuevoTelefono.setVisibility(View.VISIBLE);
                etNuevoTelefono.setText(datos.getString(Constants.NUEVO_TELEFONO));
                etNuevoTelefono.setVisibility(View.VISIBLE);
            }

            if (datos.getString(Constants.RESULTADO_PAGO).equals("PAGO")){ //Pago
                ResultadoGestion.setVisibility(View.VISIBLE);
                etResultadoGestion.setText("SI PAGO");
                etResultadoGestion.setVisibility(View.VISIBLE);
                etPagoRealizado.setText(datos.getString(Constants.PAGO_REALIZADO));
                etPagoRealizado.setVisibility(View.VISIBLE);

                SaldoCorte.setVisibility(View.VISIBLE);
                etSaldoCorte.setText(String.valueOf(datos.getDouble(Constants.SALDO_CORTE)));
                etSaldoCorte.setVisibility(View.VISIBLE);

                EstusPago.setVisibility(View.VISIBLE);
                if (datos.getDouble(Constants.MONTO_REQUERIDO,0) - Miscellaneous.doubleFormatTV(etPagoRealizado) == 0)
                    etEstatusPago.setText(ctx.getResources().getString(R.string.pago_completo));
                else if (datos.getDouble(Constants.MONTO_REQUERIDO,0) - Miscellaneous.doubleFormatTV(etPagoRealizado) < 0)
                    etEstatusPago.setText(ctx.getResources().getString(R.string.pago_completo_adelanto));
                else if (datos.getDouble(Constants.MONTO_REQUERIDO,0) - Miscellaneous.doubleFormatTV(etPagoRealizado) > 0)
                    etEstatusPago.setText(ctx.getResources().getString(R.string.pago_parcial));
                else
                    etEstatusPago.setText(ctx.getResources().getString(R.string.pay_status));
                etEstatusPago.setVisibility(View.VISIBLE);

                SaldoActual.setVisibility(View.VISIBLE);
                etSaldoActual.setText(String.valueOf(Miscellaneous.doubleFormatTV(etSaldoCorte) - Miscellaneous.doubleFormatTV(etPagoRealizado)));
                etSaldoActual.setVisibility(View.VISIBLE);

                etMedioPago.setText(datos.getString(Constants.MEDIO_PAGO));
                if ((Miscellaneous.MedioPago(etMedioPago) >= 0 && Miscellaneous.MedioPago(etMedioPago) < 6 || Miscellaneous.MedioPago(etMedioPago) == 7)){ //Banco y Oxxo
                    MedioPago.setVisibility(View.VISIBLE);
                    etMedioPago.setVisibility(View.VISIBLE);
                    Fecha.setVisibility(View.VISIBLE);
                    Fecha.setText("Fecha de Depósito");
                    etFecha.setText(datos.getString(Constants.FECHA_DEPOSITO));
                    etFecha.setVisibility(View.VISIBLE);
                    PagoRealizado.setVisibility(View.VISIBLE);
                    etPagoRealizado.setText(datos.getString(Constants.PAGO_REALIZADO));
                    etPagoRealizado.setVisibility(View.VISIBLE);
                    if (Miscellaneous.MedioPago(etMedioPago) == 7){

                    }
                    if (datos.containsKey(Constants.RESUMEN_INTEGRANTES)){
                        NoDetalle.setVisibility(View.VISIBLE);
                        etNoDetalle.setVisibility(View.VISIBLE);
                        if (datos.getBoolean(Constants.RESUMEN_INTEGRANTES)){
                            etNoDetalle.setText("Si cuenta con el detalle de la ficha");
                        }
                        else{
                            etNoDetalle.setText("No cuenta con el detalle de la ficha");
                        }
                    }
                    Evidencia.setVisibility(View.VISIBLE);
                    Evidencia.setText("Comprobante");
                    Glide.with(ctx).load(datos.getByteArray(Constants.EVIDENCIA)).centerCrop().into(ivEvidencia);
                    ivEvidencia.setVisibility(View.VISIBLE);

                    if (datos.getString(Constants.GERENTE).equals("SI")) { //Si está el gerente
                        Firma.setVisibility(View.VISIBLE);
                        Glide.with(ctx).load(datos.getByteArray(Constants.FIRMA)).into(ivFirma);
                        ivFirma.setVisibility(View.VISIBLE);
                    }
                }
                else if (Miscellaneous.MedioPago(etMedioPago) == 6 || datos.getString(Constants.MEDIO_PAGO).equals("EFECTIVO")){ //Efectivo o SIDERT
                    MedioPago.setVisibility(View.VISIBLE);
                    etMedioPago.setText(datos.getString(Constants.MEDIO_PAGO));
                    etMedioPago.setVisibility(View.VISIBLE);
                    PagoRealizado.setVisibility(View.VISIBLE);
                    if (datos.containsKey(Constants.RESUMEN_INTEGRANTES)){
                        NoDetalle.setVisibility(View.VISIBLE);
                        etNoDetalle.setVisibility(View.VISIBLE);
                        if (datos.getBoolean(Constants.RESUMEN_INTEGRANTES)){
                            etNoDetalle.setText("Si cuenta con el detalle de la ficha");
                        }
                        else{
                            etNoDetalle.setText("No cuenta con el detalle de la ficha");
                        }
                    }
                    Evidencia.setVisibility(View.VISIBLE);
                    Evidencia.setText("Comprobante");

                    FolioRecibo.setVisibility(View.VISIBLE);
                    etFolioRecibo.setText(datos.getString(Constants.FOLIO_TICKET));
                    etFolioRecibo.setVisibility(View.VISIBLE);

                    Glide.with(ctx).load(datos.getByteArray(Constants.EVIDENCIA)).centerCrop().into(ivEvidencia);
                    ivEvidencia.setVisibility(View.VISIBLE);
                    if (datos.getString(Constants.GERENTE).equals("SI")) { //Si está el gerente
                        Firma.setVisibility(View.VISIBLE);
                        Glide.with(ctx).load(datos.getByteArray(Constants.FIRMA)).into(ivFirma);
                        ivFirma.setVisibility(View.VISIBLE);
                    }
                }
                else {
                    Toast.makeText(ctx, "No se encontraron datos", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
            else if (datos.getString(Constants.RESULTADO_PAGO).equals("NO PAGO")){//No Pago
                ResultadoGestion.setVisibility(View.VISIBLE);
                etResultadoGestion.setText(datos.getString(Constants.RESULTADO_PAGO));
                etResultadoGestion.setVisibility(View.VISIBLE);

                etMotivoNoPago.setText(datos.getString(Constants.MOTIVO_NO_PAGO));
                if (Miscellaneous.MotivoNoPago(etMotivoNoPago) == 1){ //Motivo de no pago Fallecimiento
                    MotivoNoPago.setVisibility(View.VISIBLE);
                    etMotivoNoPago.setVisibility(View.VISIBLE);
                    Fecha.setVisibility(View.VISIBLE);
                    Fecha.setText("Fecha de Defunción");
                    etFecha.setText(datos.getString(Constants.FECHA_DEFUNCION));
                    etFecha.setVisibility(View.VISIBLE);
                    Comentario.setVisibility(View.VISIBLE);
                    etComentario.setText(datos.getString(Constants.COMENTARIO));
                    etComentario.setVisibility(View.VISIBLE);
                    Evidencia.setVisibility(View.VISIBLE);
                    Glide.with(ctx).load(datos.getByteArray(Constants.EVIDENCIA)).centerCrop().into(ivEvidencia);
                    ivEvidencia.setVisibility(View.VISIBLE);

                    if (datos.getString(Constants.GERENTE).equals("SI")) { //Si está el gerente
                        Firma.setVisibility(View.VISIBLE);
                        Glide.with(ctx).load(datos.getByteArray(Constants.FIRMA)).into(ivFirma);
                        ivFirma.setVisibility(View.VISIBLE);
                    }
                }
                else if (Miscellaneous.MotivoNoPago(etMotivoNoPago) ==  0|| Miscellaneous.MotivoNoPago(etMotivoNoPago) == 2){ //Motivo de no pago Negación u Otro
                    MotivoNoPago.setVisibility(View.VISIBLE);
                    etMotivoNoPago.setText(datos.getString(Constants.MOTIVO_NO_PAGO));
                    etMotivoNoPago.setVisibility(View.VISIBLE);
                    Comentario.setVisibility(View.VISIBLE);
                    etComentario.setText(datos.getString(Constants.COMENTARIO));
                    etComentario.setVisibility(View.VISIBLE);
                    Evidencia.setVisibility(View.VISIBLE);
                    Glide.with(ctx).load(datos.getByteArray(Constants.EVIDENCIA)).centerCrop().into(ivEvidencia);
                    ivEvidencia.setVisibility(View.VISIBLE);

                    if (datos.getString(Constants.GERENTE).equals("SI")) { //Si está el gerente
                        Firma.setVisibility(View.VISIBLE);
                        Glide.with(ctx).load(datos.getByteArray(Constants.FIRMA)).into(ivFirma);
                        ivFirma.setVisibility(View.VISIBLE);
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
        else if (datos.getString(Constants.CONTACTO).equals("ACLARACION")){ // Aclaración
            etContacto.setText("ACLARACION DE FICHA");
            MotivoAclaracion.setVisibility(View.VISIBLE);
            etMotivoAclaracion.setText(datos.getString(Constants.MOTIVO_ACLARACION));
            etMotivoAclaracion.setVisibility(View.VISIBLE);
            Comentario.setVisibility(View.VISIBLE);
            etComentario.setText(datos.getString(Constants.COMENTARIO));
            etComentario.setVisibility(View.VISIBLE);
            if (datos.getString(Constants.GERENTE).equals("SI")) { //Si está el gerente
                Firma.setVisibility(View.VISIBLE);
                Glide.with(ctx).load(datos.getByteArray(Constants.FIRMA)).into(ivFirma);
                ivFirma.setVisibility(View.VISIBLE);
            }
        }
        else if (datos.getString(Constants.CONTACTO).equals("NO")){ // No Contacto cliente
            etContacto.setText("NO CONTACTO AL CLIENTE");
            Comentario.setVisibility(View.VISIBLE);
            etComentario.setVisibility(View.VISIBLE);
            if (datos.containsKey(Constants.MOTIVO_NO_CONTACTO)){
                MotivoNoContacto.setVisibility(View.VISIBLE);
                etMotivoNoContacto.setText(datos.getString(Constants.MOTIVO_NO_CONTACTO));
                etMotivoNoContacto.setVisibility(View.VISIBLE);
            }
            etComentario.setText(datos.getString(Constants.COMENTARIO));
            Evidencia.setVisibility(View.VISIBLE);
            Glide.with(ctx).load(datos.getByteArray(Constants.EVIDENCIA)).centerCrop().into(ivEvidencia);

            ivEvidencia.setVisibility(View.VISIBLE);
            if (datos.getString(Constants.GERENTE).equals("SI")) { //Si está el gerente
                Firma.setVisibility(View.VISIBLE);
                Glide.with(ctx).load(datos.getByteArray(Constants.FIRMA)).into(ivFirma);

                ivFirma.setVisibility(View.VISIBLE);
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

        CameraUpdate ubication = CameraUpdateFactory.newLatLngZoom(coordenadas,15);

        mMap.addMarker(new MarkerOptions()
                .position(coordenadas)
                .title(""));

        mMap.animateCamera(ubication);
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
            if (etEstatusPago.getVisibility() == View.VISIBLE){
                i_result.putExtra(ESTATUS, etEstatusPago.getText().toString().trim().toUpperCase());
                i_result.putExtra(SALDO_CORTE, etSaldoCorte.getText().toString().trim().toUpperCase());
                i_result.putExtra(SALDO_ACTUAL, etSaldoActual.getText().toString().trim().toUpperCase());
            }
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
