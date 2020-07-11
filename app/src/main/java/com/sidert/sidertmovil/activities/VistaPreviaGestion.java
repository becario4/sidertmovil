package com.sidert.sidertmovil.activities;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import static com.sidert.sidertmovil.utils.Constants.ACTUALIZAR_TELEFONO;
import static com.sidert.sidertmovil.utils.Constants.COMENTARIO;
import static com.sidert.sidertmovil.utils.Constants.CONTACTO;
import static com.sidert.sidertmovil.utils.Constants.ESTATUS;
import static com.sidert.sidertmovil.utils.Constants.EVIDENCIA;
import static com.sidert.sidertmovil.utils.Constants.FECHA_DEFUNCION;
import static com.sidert.sidertmovil.utils.Constants.FECHA_DEPOSITO;
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
import static com.sidert.sidertmovil.utils.Constants.NUEVO_TELEFONO;
import static com.sidert.sidertmovil.utils.Constants.PAGO_REALIZADO;
import static com.sidert.sidertmovil.utils.Constants.PARAMS;
import static com.sidert.sidertmovil.utils.Constants.RESPONSE;
import static com.sidert.sidertmovil.utils.Constants.RESULTADO_PAGO;
import static com.sidert.sidertmovil.utils.Constants.RESUMEN_INTEGRANTES;
import static com.sidert.sidertmovil.utils.Constants.SALDO_ACTUAL;
import static com.sidert.sidertmovil.utils.Constants.SALDO_CORTE;
import static com.sidert.sidertmovil.utils.Constants.warning;


public class VistaPreviaGestion extends AppCompatActivity {

    private Context ctx;

    private Toolbar tbMain;

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

    private GoogleMap mMap;

    private Calendar mCalendar;
    private SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_TIMESTAMP, Locale.US);

    DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
    private DecimalFormat nFormat = new DecimalFormat("#,###.##", symbols);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_previa_gestion);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        ctx = this;

        tbMain          = findViewById(R.id.tbMain);

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

        Bundle datos = this.getIntent().getBundleExtra(PARAMS);

        Log.e("DatosEnviar", datos.toString());

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
                if (datos.getDouble(MONTO_REQUERIDO,0) - Miscellaneous.doubleFormatTV(etPagoRealizado) == 0)
                    etEstatusPago.setText(ctx.getResources().getString(R.string.pago_completo));
                else if (datos.getDouble(MONTO_REQUERIDO,0) - Miscellaneous.doubleFormatTV(etPagoRealizado) < 0)
                    etEstatusPago.setText(ctx.getResources().getString(R.string.pago_completo_adelanto));
                else if (datos.getDouble(MONTO_REQUERIDO,0) - Miscellaneous.doubleFormatTV(etPagoRealizado) > 0)
                    etEstatusPago.setText(ctx.getResources().getString(R.string.pago_parcial));
                else
                    etEstatusPago.setText(ctx.getResources().getString(R.string.pay_status));
                etEstatusPago.setVisibility(View.VISIBLE);

                SaldoActual.setVisibility(View.VISIBLE);
                etSaldoActual.setText(String.valueOf(nFormat.format(Miscellaneous.doubleFormatTV(etSaldoCorte) - Miscellaneous.doubleFormatTV(etPagoRealizado))));
                etSaldoActual.setVisibility(View.VISIBLE);

                etMedioPago.setText(datos.getString(MEDIO_PAGO));
                if (Miscellaneous.MedioPago(etMedioPago) >= 0 && Miscellaneous.MedioPago(etMedioPago) < 6 || Miscellaneous.MedioPago(etMedioPago) == 7 || Miscellaneous.MedioPago(etMedioPago) == 8){ //Banco y Oxxo
                    MedioPago.setVisibility(View.VISIBLE);
                    etMedioPago.setVisibility(View.VISIBLE);
                    Fecha.setVisibility(View.VISIBLE);
                    Fecha.setText("Fecha de Depósito");
                    etFecha.setText(datos.getString(FECHA_DEPOSITO));
                    etFecha.setVisibility(View.VISIBLE);
                    PagoRealizado.setVisibility(View.VISIBLE);
                    etPagoRealizado.setText(String.valueOf(nFormat.format(Double.parseDouble(datos.getString(PAGO_REALIZADO)))));
                    etPagoRealizado.setVisibility(View.VISIBLE);
                    if (Miscellaneous.MedioPago(etMedioPago) == 7){

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
                else if (Miscellaneous.MedioPago(etMedioPago) == 6 || datos.getString(MEDIO_PAGO).equals("EFECTIVO")){ //Efectivo o SIDERT
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
                    Toast.makeText(ctx, "No se encontraron datos", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
            else if (datos.getString(RESULTADO_PAGO).equals("NO PAGO")){//No Pago
                ResultadoGestion.setVisibility(View.VISIBLE);
                etResultadoGestion.setText(datos.getString(RESULTADO_PAGO));
                etResultadoGestion.setVisibility(View.VISIBLE);

                etMotivoNoPago.setText(datos.getString(MOTIVO_NO_PAGO));
                if (Miscellaneous.MotivoNoPago(etMotivoNoPago) == 1){ //Motivo de no pago Fallecimiento
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
                else if (Miscellaneous.MotivoNoPago(etMotivoNoPago) ==  0|| Miscellaneous.MotivoNoPago(etMotivoNoPago) == 2){ //Motivo de no pago Negación u Otro
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
                else if (Miscellaneous.MotivoNoPago(etMotivoNoPago) ==  3){ //PROMESA DE PAGO
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
                    Toast.makeText(ctx, "No se encontraron datos", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
            else{
                Toast.makeText(ctx, "No se encontraron datos", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(ctx, "No se encontraron datos", Toast.LENGTH_SHORT).show();
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
            i_result.putExtra(RESPONSE, true);
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
