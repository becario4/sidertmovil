package com.sidert.sidertmovil.activities;


import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
//import android.support.v4.app.ActivityCompat;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
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
import com.google.android.gms.maps.model.MarkerOptions;
import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.utils.Miscellaneous;
import com.sidert.sidertmovil.utils.Popups;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;
import java.util.UUID;

import static com.sidert.sidertmovil.utils.Constants.ACTUALIZAR_TELEFONO;
import static com.sidert.sidertmovil.utils.Constants.COMENTARIO;
import static com.sidert.sidertmovil.utils.Constants.CONTACTO;
import static com.sidert.sidertmovil.utils.Constants.ESTATUS;
import static com.sidert.sidertmovil.utils.Constants.EVIDENCIA;
import static com.sidert.sidertmovil.utils.Constants.FECHA_DEFUNCION;
import static com.sidert.sidertmovil.utils.Constants.FECHA_DEPOSITO;
import static com.sidert.sidertmovil.utils.Constants.FECHA_FIN;
import static com.sidert.sidertmovil.utils.Constants.FECHA_PROMESA_PAGO;
import static com.sidert.sidertmovil.utils.Constants.FIRMA;
import static com.sidert.sidertmovil.utils.Constants.FOLIO_TICKET;
import static com.sidert.sidertmovil.utils.Constants.FORMAT_TIMESTAMP;
import static com.sidert.sidertmovil.utils.Constants.GERENTE;
import static com.sidert.sidertmovil.utils.Constants.LATITUD;
import static com.sidert.sidertmovil.utils.Constants.LONGITUD;
import static com.sidert.sidertmovil.utils.Constants.MEDIO_PAGO;
import static com.sidert.sidertmovil.utils.Constants.MONTO_PROMESA;
import static com.sidert.sidertmovil.utils.Constants.MONTO_REQUERIDO;
import static com.sidert.sidertmovil.utils.Constants.MOTIVO_ACLARACION;
import static com.sidert.sidertmovil.utils.Constants.MOTIVO_NO_CONTACTO;
import static com.sidert.sidertmovil.utils.Constants.MOTIVO_NO_PAGO;
import static com.sidert.sidertmovil.utils.Constants.NOMBRE;
import static com.sidert.sidertmovil.utils.Constants.NUEVO_TELEFONO;
import static com.sidert.sidertmovil.utils.Constants.PAGO_REALIZADO;
import static com.sidert.sidertmovil.utils.Constants.PARAMS;
import static com.sidert.sidertmovil.utils.Constants.RESPONSE;
import static com.sidert.sidertmovil.utils.Constants.RESULTADO_PAGO;
import static com.sidert.sidertmovil.utils.Constants.RESUMEN_INTEGRANTES;
import static com.sidert.sidertmovil.utils.Constants.ROOT_PATH;
import static com.sidert.sidertmovil.utils.Constants.SALDO_ACTUAL;
import static com.sidert.sidertmovil.utils.Constants.SALDO_CORTE;
import static com.sidert.sidertmovil.utils.Constants.SCREEN_SHOT;
import static com.sidert.sidertmovil.utils.Constants.TIMESTAMP;
import static com.sidert.sidertmovil.utils.Constants.UBICACION;
import static com.sidert.sidertmovil.utils.Constants.firma;
import static com.sidert.sidertmovil.utils.Constants.warning;


public class VistaPreviaGestion extends AppCompatActivity {

    private Context ctx;

    private Toolbar tbMain;
    private TextView tvtitulo;
    private TextView tvSubtitulo;

    private MapView mapGestion;

    private TextView tvNoMapa;
    private TextView NuevoTelefono;
    private TextView MotivoAclaracion;
    private TextView MotivoNoPago;
    private TextView MotivoNoContacto;
    private TextView Comentario;
    private TextView ResultadoGestion;
    private TextView MedioPago;
    private TextView Fecha;
    private TextView MontoPromesa;
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
    private TextView etMontoPromesa;
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

    private String nombre = "";

    private GoogleMap mMap;

    private Bundle datos;

    private Calendar mCalendar;
    private SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_TIMESTAMP, Locale.US);

    DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
    private DecimalFormat nFormat = new DecimalFormat("#,###.##", symbols);

    private Miscellaneous m;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_previa_gestion);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        ctx = this;

        m = new Miscellaneous();

        tbMain          = findViewById(R.id.tbMain);
        tvtitulo        = findViewById(R.id.tvTitulo);
        tvSubtitulo     = findViewById(R.id.tvSubtitulo);

        mapGestion  = findViewById(R.id.mapGestion);

        tvNoMapa          = findViewById(R.id.tvNoMapa);
        NuevoTelefono     = findViewById(R.id.NuevoTelefono);
        MotivoAclaracion  = findViewById(R.id.MotivoAclaracion);
        MotivoNoPago      = findViewById(R.id.MotivoNoPago);
        MotivoNoContacto  = findViewById(R.id.MotivoNoContacto);
        Comentario        = findViewById(R.id.Comentario);
        ResultadoGestion  = findViewById(R.id.ResultadoGestion);
        MedioPago         = findViewById(R.id.MedioPago);
        Fecha             = findViewById(R.id.Fecha);
        MontoPromesa      = findViewById(R.id.MontoPromesa);
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
        etMontoPromesa      = findViewById(R.id.tvMontoPromesa);
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

        datos = this.getIntent().getBundleExtra(PARAMS);

        Log.e("DatosEnviar", datos.toString());

        tvtitulo.setText("Resumen de gestión");

        nombre = datos.getString(NOMBRE);


        if (datos.getDouble(LATITUD) == 0 && datos.getDouble(LONGITUD) == 0){
            mapGestion.setVisibility(View.GONE);
            tvNoMapa.setVisibility(View.VISIBLE);
        }
        else
            ColocarUbicacionGestion(datos.getDouble(LATITUD), datos.getDouble(LONGITUD));

        if (datos.getString(CONTACTO).equals("SI")){ //Si contacto cliente
            etContacto.setText("SI CONTACTO AL CLIENTE");
            if (datos.getString(ACTUALIZAR_TELEFONO).equals("SI")){
                NuevoTelefono.setVisibility(View.VISIBLE);
                etNuevoTelefono.setText(datos.getString(NUEVO_TELEFONO));
                etNuevoTelefono.setVisibility(View.VISIBLE);
            }

            if (datos.getString(RESULTADO_PAGO).equals("PAGO")){ //Pago
                ResultadoGestion.setVisibility(View.VISIBLE);
                etResultadoGestion.setText("SI PAGO");
                etResultadoGestion.setVisibility(View.VISIBLE);
                etPagoRealizado.setText(String.valueOf(nFormat.format(Double.parseDouble(datos.getString(PAGO_REALIZADO).replace(",","")))));
                etPagoRealizado.setVisibility(View.VISIBLE);

                SaldoCorte.setVisibility(View.VISIBLE);
                etSaldoCorte.setText(String.valueOf(nFormat.format(datos.getDouble(SALDO_CORTE))));
                etSaldoCorte.setVisibility(View.VISIBLE);

                Log.e("MONTOREQUERIDO", "# "+datos.getDouble(MONTO_REQUERIDO,0));
                EstusPago.setVisibility(View.VISIBLE);
                if (datos.getDouble(MONTO_REQUERIDO,0) - m.doubleFormatTV(etPagoRealizado) == 0)
                    etEstatusPago.setText(ctx.getResources().getString(R.string.pago_completo));
                else if (datos.getDouble(MONTO_REQUERIDO,0) - m.doubleFormatTV(etPagoRealizado) < 0)
                    etEstatusPago.setText(ctx.getResources().getString(R.string.pago_completo_adelanto));
                else if (datos.getDouble(MONTO_REQUERIDO,0) - m.doubleFormatTV(etPagoRealizado) > 0)
                    etEstatusPago.setText(ctx.getResources().getString(R.string.pago_parcial));
                else
                    etEstatusPago.setText(ctx.getResources().getString(R.string.pay_status));
                etEstatusPago.setVisibility(View.VISIBLE);

                SaldoActual.setVisibility(View.VISIBLE);
                etSaldoActual.setText(String.valueOf(nFormat.format(m.doubleFormatTV(etSaldoCorte) - m.doubleFormatTV(etPagoRealizado))));
                etSaldoActual.setVisibility(View.VISIBLE);

                etMedioPago.setText(datos.getString(MEDIO_PAGO));
                if (m.GetMedioPagoId(m.GetStr(etMedioPago)) >= 0 && m.GetMedioPagoId(m.GetStr(etMedioPago)) < 6 || m.GetMedioPagoId(m.GetStr(etMedioPago)) == 7 || m.GetMedioPagoId(m.GetStr(etMedioPago)) == 8){ //Banco y Oxxo
                    MedioPago.setVisibility(View.VISIBLE);
                    etMedioPago.setVisibility(View.VISIBLE);
                    Fecha.setVisibility(View.VISIBLE);
                    Fecha.setText("Fecha de Depósito");
                    etFecha.setText(datos.getString(FECHA_DEPOSITO));
                    etFecha.setVisibility(View.VISIBLE);
                    PagoRealizado.setVisibility(View.VISIBLE);
                    etPagoRealizado.setText(String.valueOf(nFormat.format(Double.parseDouble(datos.getString(PAGO_REALIZADO)))));
                    etPagoRealizado.setVisibility(View.VISIBLE);
                    if (m.GetMedioPagoId(m.GetStr(etMedioPago)) == 7){

                    }
                    if (datos.containsKey(RESUMEN_INTEGRANTES)){
                        NoDetalle.setVisibility(View.VISIBLE);
                        etNoDetalle.setVisibility(View.VISIBLE);
                        if (datos.getBoolean(RESUMEN_INTEGRANTES)){
                            etNoDetalle.setText("Si cuenta con el detalle de la ficha");
                        }
                        else{
                            etNoDetalle.setText("No cuenta con el detalle de la ficha");
                        }
                    }
                    Evidencia.setVisibility(View.VISIBLE);
                    Evidencia.setText("Comprobante");
                    Glide.with(ctx).load(datos.getByteArray(EVIDENCIA)).centerCrop().into(ivEvidencia);
                    ivEvidencia.setVisibility(View.VISIBLE);

                    if (datos.getString(GERENTE).equals("SI")) { //Si está el gerente
                        Firma.setVisibility(View.VISIBLE);
                        Glide.with(ctx).load(datos.getByteArray(FIRMA)).into(ivFirma);
                        ivFirma.setVisibility(View.VISIBLE);
                    }
                }
                else if (m.GetMedioPagoId(m.GetStr(etMedioPago)) == 6 || datos.getString(MEDIO_PAGO).equals("EFECTIVO")){ //Efectivo o SIDERT
                    MedioPago.setVisibility(View.VISIBLE);
                    etMedioPago.setText(datos.getString(MEDIO_PAGO));
                    etMedioPago.setVisibility(View.VISIBLE);
                    PagoRealizado.setVisibility(View.VISIBLE);
                    if (datos.containsKey(RESUMEN_INTEGRANTES)){
                        NoDetalle.setVisibility(View.VISIBLE);
                        etNoDetalle.setVisibility(View.VISIBLE);
                        if (datos.getBoolean(RESUMEN_INTEGRANTES)){
                            etNoDetalle.setText("Si cuenta con el detalle de la ficha");
                        }
                        else{
                            etNoDetalle.setText("No cuenta con el detalle de la ficha");
                        }
                    }
                    Evidencia.setVisibility(View.VISIBLE);
                    Evidencia.setText("Comprobante");

                    FolioRecibo.setVisibility(View.VISIBLE);
                    etFolioRecibo.setText(datos.getString(FOLIO_TICKET));
                    etFolioRecibo.setVisibility(View.VISIBLE);

                    Glide.with(ctx).load(datos.getByteArray(EVIDENCIA)).centerCrop().into(ivEvidencia);
                    ivEvidencia.setVisibility(View.VISIBLE);
                    if (datos.getString(GERENTE).equals("SI")) { //Si está el gerente
                        Firma.setVisibility(View.VISIBLE);
                        Glide.with(ctx).load(datos.getByteArray(FIRMA)).into(ivFirma);
                        ivFirma.setVisibility(View.VISIBLE);
                    }
                }
                else {
                    Toast.makeText(ctx, R.string.not_found, Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
            else if (datos.getString(RESULTADO_PAGO).equals("NO PAGO")){//No Pago
                ResultadoGestion.setVisibility(View.VISIBLE);
                etResultadoGestion.setText(datos.getString(RESULTADO_PAGO));
                etResultadoGestion.setVisibility(View.VISIBLE);

                etMotivoNoPago.setText(datos.getString(MOTIVO_NO_PAGO));
                if (m.GetIdMotivoNoPago(m.GetStr(etMotivoNoPago)) == 1){ //Motivo de no pago Fallecimiento
                    MotivoNoPago.setVisibility(View.VISIBLE);
                    etMotivoNoPago.setVisibility(View.VISIBLE);
                    Fecha.setVisibility(View.VISIBLE);
                    Fecha.setText("Fecha de Defunción");
                    etFecha.setText(datos.getString(FECHA_DEFUNCION));
                    etFecha.setVisibility(View.VISIBLE);
                    Comentario.setVisibility(View.VISIBLE);
                    etComentario.setText(datos.getString(COMENTARIO));
                    etComentario.setVisibility(View.VISIBLE);
                    Evidencia.setVisibility(View.VISIBLE);
                    Glide.with(ctx).load(datos.getByteArray(EVIDENCIA)).centerCrop().into(ivEvidencia);
                    ivEvidencia.setVisibility(View.VISIBLE);

                    if (datos.getString(GERENTE).equals("SI")) { //Si está el gerente
                        Firma.setVisibility(View.VISIBLE);
                        Glide.with(ctx).load(datos.getByteArray(FIRMA)).into(ivFirma);
                        ivFirma.setVisibility(View.VISIBLE);
                    }
                }
                else if (m.GetIdMotivoNoPago(m.GetStr(etMotivoNoPago)) ==  0|| m.GetIdMotivoNoPago(m.GetStr(etMotivoNoPago)) == 2){ //Motivo de no pago Negación u Otro
                    MotivoNoPago.setVisibility(View.VISIBLE);
                    etMotivoNoPago.setText(datos.getString(MOTIVO_NO_PAGO));
                    etMotivoNoPago.setVisibility(View.VISIBLE);
                    Comentario.setVisibility(View.VISIBLE);
                    etComentario.setText(datos.getString(COMENTARIO));
                    etComentario.setVisibility(View.VISIBLE);
                    Evidencia.setVisibility(View.VISIBLE);
                    Glide.with(ctx).load(datos.getByteArray(EVIDENCIA)).centerCrop().into(ivEvidencia);
                    ivEvidencia.setVisibility(View.VISIBLE);

                    if (datos.getString(GERENTE).equals("SI")) { //Si está el gerente
                        Firma.setVisibility(View.VISIBLE);
                        Glide.with(ctx).load(datos.getByteArray(FIRMA)).into(ivFirma);
                        ivFirma.setVisibility(View.VISIBLE);
                    }
                }
                else if (m.GetIdMotivoNoPago(m.GetStr(etMotivoNoPago)) ==  3){ //PROMESA DE PAGO
                    Fecha.setVisibility(View.VISIBLE);
                    Fecha.setText("Fecha de Promesa de Pago");
                    etFecha.setText(datos.getString(FECHA_PROMESA_PAGO));
                    etFecha.setVisibility(View.VISIBLE);

                    MontoPromesa.setVisibility(View.VISIBLE);
                    etMontoPromesa.setText(datos.getString(MONTO_PROMESA));
                    etMontoPromesa.setVisibility(View.VISIBLE);

                    Evidencia.setVisibility(View.VISIBLE);
                    Glide.with(ctx).load(datos.getByteArray(EVIDENCIA)).centerCrop().into(ivEvidencia);
                    ivEvidencia.setVisibility(View.VISIBLE);

                    if (datos.getString(GERENTE).equals("SI")) { //Si está el gerente
                        Firma.setVisibility(View.VISIBLE);
                        Glide.with(ctx).load(datos.getByteArray(FIRMA)).into(ivFirma);
                        ivFirma.setVisibility(View.VISIBLE);
                    }
                }
                else {
                    Toast.makeText(ctx, R.string.not_found, Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
            else{
                Toast.makeText(ctx, R.string.not_found, Toast.LENGTH_SHORT).show();
                finish();
            }
        }
        else if (datos.getString(CONTACTO).equals("ACLARACION")){ // Aclaración
            etContacto.setText("ACLARACION DE FICHA");

            if (datos.containsKey(MOTIVO_ACLARACION)) {
                MotivoAclaracion.setVisibility(View.VISIBLE);
                etMotivoAclaracion.setText(datos.getString(MOTIVO_ACLARACION));
                etMotivoAclaracion.setVisibility(View.VISIBLE);
            }
            Comentario.setVisibility(View.VISIBLE);
            etComentario.setText(datos.getString(COMENTARIO));
            etComentario.setVisibility(View.VISIBLE);
            if (datos.getString(GERENTE).equals("SI")) { //Si está el gerente
                Firma.setVisibility(View.VISIBLE);
                Glide.with(ctx).load(datos.getByteArray(FIRMA)).into(ivFirma);
                ivFirma.setVisibility(View.VISIBLE);
            }
        }
        else if (datos.getString(CONTACTO).equals("NO")){ // No Contacto cliente
            etContacto.setText("NO CONTACTO AL CLIENTE");
            Comentario.setVisibility(View.VISIBLE);
            etComentario.setVisibility(View.VISIBLE);
            if (datos.containsKey(MOTIVO_NO_CONTACTO)){
                MotivoNoContacto.setVisibility(View.VISIBLE);
                etMotivoNoContacto.setText(datos.getString(MOTIVO_NO_CONTACTO));
                etMotivoNoContacto.setVisibility(View.VISIBLE);
            }
            etComentario.setText(datos.getString(COMENTARIO));
            Evidencia.setVisibility(View.VISIBLE);
            Glide.with(ctx).load(datos.getByteArray(EVIDENCIA)).centerCrop().into(ivEvidencia);

            ivEvidencia.setVisibility(View.VISIBLE);
            if (datos.getString(GERENTE).equals("SI")) { //Si está el gerente
                Firma.setVisibility(View.VISIBLE);
                Glide.with(ctx).load(datos.getByteArray(FIRMA)).into(ivFirma);

                ivFirma.setVisibility(View.VISIBLE);
            }
        }
        else{ //No hay información
            Toast.makeText(ctx, R.string.not_found, Toast.LENGTH_SHORT).show();
            finish();
        }

        final AlertDialog success = Popups.showDialogMessage(this, warning,
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

        NoDetalle.setVisibility(View.GONE);
        etNoDetalle.setVisibility(View.GONE);
        EstusPago.setVisibility(View.GONE);
        etEstatusPago.setVisibility(View.GONE);
        etNuevoTelefono.setVisibility(View.GONE);
        NuevoTelefono.setVisibility(View.GONE);
        Firma.setVisibility(View.GONE);
        ivFirma.setVisibility(View.GONE);

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

            String fechaFin = m.ObtenerFecha(TIMESTAMP);
            tvtitulo.setText(nombre);
            tvSubtitulo.setText("Fin Gestión "+fechaFin);

            /*GoogleMap.SnapshotReadyCallback callback = new GoogleMap.SnapshotReadyCallback() {
                Bitmap bitmap;

                @Override
                public void onSnapshotReady(Bitmap snapshot) {
                    // TODO Auto-generated method stub
                    bitmap = snapshot;
                    try {
                        FileOutputStream out = new FileOutputStream(Environment.getExternalStorageDirectory().toString() + "/" + m.ObtenerFecha(TIMESTAMP).replace(" ","") + ".jpg");

                        // above "/mnt ..... png" => is a storage path (where image will be stored) + name of image you can customize as per your Requirement

                        bitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };

            mMap.snapshot(callback);*/
            String name = "";
            File img = null;

            try {

                // create bitmap screen capture
                View v1 = getWindow().getDecorView().getRootView();
                v1.setDrawingCacheEnabled(true);
                Bitmap bitmap = Bitmap.createBitmap(v1.getDrawingCache());
                v1.setDrawingCacheEnabled(false);

               /* View vCanvas = new CustomCanvasResumen(ctx, m.ObtenerFecha(TIMESTAMP), "Alejandro Isaias Lopez");

                Canvas canvas = new Canvas(bitmap);
                canvas.drawBitmap(bitmap, 0, 0, null);

                vCanvas.draw(canvas);

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);*/


                //String mPath = Environment.getExternalStorageDirectory().toString() + "/" + m.ObtenerFecha(TIMESTAMP).replace(" ","") + ".jpg";
                String mPath = ROOT_PATH+"Resumen";
                name = UUID.randomUUID().toString() + ".jpg";
                int quality = 100;
                //Intent whatsappIntent = new Intent(Intent.ACTION_SEND);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    Log.e("AQUI", "entre a mayor igual que");
                    try {
                        FileOutputStream fos;
                        ContentResolver resolver = getContentResolver();
                        ContentValues contentValues = new ContentValues();
                        contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, name);
                        contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg");
                        contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES + File.separator + "SidertMovil");
                        Uri imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
                        fos = (FileOutputStream) resolver.openOutputStream(Objects.requireNonNull(imageUri));
                        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, fos);
                        /*whatsappIntent.setType("text/plain");
                        whatsappIntent.setPackage("com.whatsapp");
                        whatsappIntent.putExtra(Intent.EXTRA_TEXT, "Le comparto el resumen de la gestión del cliente " + nombre);
                        whatsappIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
                        whatsappIntent.setType("image/jpeg");
                        whatsappIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        */

                        Intent i_result = new Intent();
                        if (etEstatusPago.getVisibility() == View.VISIBLE){
                            i_result.putExtra(ESTATUS, etEstatusPago.getText().toString().trim().toUpperCase());
                            i_result.putExtra(SALDO_CORTE, etSaldoCorte.getText().toString().trim().toUpperCase());
                            i_result.putExtra(SALDO_ACTUAL, etSaldoActual.getText().toString().trim().toUpperCase());
                        }

                        if (datos.getDouble(LATITUD) == 0 && datos.getDouble(LONGITUD) == 0){
                            i_result.putExtra(UBICACION, false);
                        }

                        Log.e("AQUI PATH", Environment.DIRECTORY_PICTURES + File.separator + "SidertMovil");
                        i_result.putExtra(FECHA_FIN, fechaFin);
                        i_result.putExtra(NOMBRE, name);
                        i_result.putExtra(RESPONSE, true);
                        //i_result.putExtra(SCREEN_SHOT, img.getPath());
                        //i_result.putExtra(SCREEN_SHOT, Environment.DIRECTORY_PICTURES + File.separator + "SidertMovil" + File.separator + name);
                        i_result.putExtra(SCREEN_SHOT, imageUri.getPath());
                        setResult(RESULT_OK, i_result);


                        finish();
                    }
                    catch(Exception ex)
                    {
                        Log.e("AQUI", ex.getMessage());
                    }
                }
                else
                {
                    File directory = new File(mPath);

                    if(!directory.exists())
                    {
                        directory.mkdirs();
                    }

                    img = new File(mPath+"/"+name);

                    FileOutputStream outputStream = new FileOutputStream(img);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
                    outputStream.flush();
                    outputStream.close();

                   /* Uri imgUri = Uri.parse(img.getPath());
                    whatsappIntent.setType("text/plain");
                    whatsappIntent.setPackage("com.whatsapp");
                    whatsappIntent.putExtra(Intent.EXTRA_TEXT, "Le comparto el resumen de la gestión del cliente " + nombre);
                    whatsappIntent.putExtra(Intent.EXTRA_STREAM, imgUri);
                    whatsappIntent.setType("image/jpeg");
                    whatsappIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);*/

                    Intent i_result = new Intent();
                    if (etEstatusPago.getVisibility() == View.VISIBLE){
                        i_result.putExtra(ESTATUS, etEstatusPago.getText().toString().trim().toUpperCase());
                        i_result.putExtra(SALDO_CORTE, etSaldoCorte.getText().toString().trim().toUpperCase());
                        i_result.putExtra(SALDO_ACTUAL, etSaldoActual.getText().toString().trim().toUpperCase());
                    }

                    if (datos.getDouble(LATITUD) == 0 && datos.getDouble(LONGITUD) == 0){
                        i_result.putExtra(UBICACION, false);
                    }

                    i_result.putExtra(FECHA_FIN, fechaFin);
                    i_result.putExtra(NOMBRE, name);
                    i_result.putExtra(RESPONSE, true);
                    //i_result.putExtra(SCREEN_SHOT, img.getPath());
                    i_result.putExtra(SCREEN_SHOT, img.getAbsolutePath());
                    setResult(RESULT_OK, i_result);


                    finish();
                }

                /*try {
                    ctx.startActivity(whatsappIntent);
                } catch (android.content.ActivityNotFoundException ex) {
                    Log.e("AQUI", ex.getMessage());
                    Toast.makeText(ctx, "No cuenta con Whatsapp", Toast.LENGTH_SHORT).show();
                }
                catch (Exception ex)
                {
                    Toast.makeText(ctx, ex.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e("AQUI", ex.getMessage());
                }*/

            } catch (Throwable e) {
                Toast.makeText(ctx, e.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("AQUI", e.getMessage());
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
