package com.sidert.sidertmovil.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
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
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.database.DBhelper;
import com.sidert.sidertmovil.fragments.dialogs.dialog_cancel_gestion;
import com.sidert.sidertmovil.utils.Constants;
import com.sidert.sidertmovil.utils.Miscellaneous;
import com.sidert.sidertmovil.utils.Popups;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Objects;
import java.util.UUID;

import static com.sidert.sidertmovil.utils.Constants.ACTUALIZAR_TELEFONO;
import static com.sidert.sidertmovil.utils.Constants.CANCELACION;
import static com.sidert.sidertmovil.utils.Constants.CLIENTE;
import static com.sidert.sidertmovil.utils.Constants.COMENTARIO;
import static com.sidert.sidertmovil.utils.Constants.CONTACTO;
import static com.sidert.sidertmovil.utils.Constants.ESTATUS;
import static com.sidert.sidertmovil.utils.Constants.EVIDENCIA;
import static com.sidert.sidertmovil.utils.Constants.FECHA_DEFUNCION;
import static com.sidert.sidertmovil.utils.Constants.FECHA_DEPOSITO;
import static com.sidert.sidertmovil.utils.Constants.FIRMA;
import static com.sidert.sidertmovil.utils.Constants.FOLIO_TICKET;
import static com.sidert.sidertmovil.utils.Constants.GERENTE;
import static com.sidert.sidertmovil.utils.Constants.ID_RESPUESTA;
import static com.sidert.sidertmovil.utils.Constants.LATITUD;
import static com.sidert.sidertmovil.utils.Constants.LONGITUD;
import static com.sidert.sidertmovil.utils.Constants.MEDIO_PAGO;
import static com.sidert.sidertmovil.utils.Constants.MOTIVO_ACLARACION;
import static com.sidert.sidertmovil.utils.Constants.MOTIVO_NO_CONTACTO;
import static com.sidert.sidertmovil.utils.Constants.MOTIVO_NO_PAGO;
import static com.sidert.sidertmovil.utils.Constants.NOMBRE;
import static com.sidert.sidertmovil.utils.Constants.NUEVO_TELEFONO;
import static com.sidert.sidertmovil.utils.Constants.PAGO_REALIZADO;
import static com.sidert.sidertmovil.utils.Constants.RESULTADO_PAGO;
import static com.sidert.sidertmovil.utils.Constants.RESUMEN;
import static com.sidert.sidertmovil.utils.Constants.RESUMEN_INTEGRANTES;
import static com.sidert.sidertmovil.utils.Constants.ROOT_PATH;
import static com.sidert.sidertmovil.utils.Constants.TBL_CARTERA_GPO_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_CARTERA_IND_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_MIEMBROS_GPO_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_PRESTAMOS_GPO_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_PRESTAMOS_IND_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_RESPUESTAS_GPO_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_RESPUESTAS_IND_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_RESPUESTAS_IND_V_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_RESPUESTAS_INTEGRANTE_T;
import static com.sidert.sidertmovil.utils.Constants.TIPO_GESTION;
import static com.sidert.sidertmovil.utils.Constants.TIPO_IMAGEN;
import static com.sidert.sidertmovil.utils.Constants.TIPO_PRESTAMO;
import static com.sidert.sidertmovil.utils.NameFragments.DIALOGCANCELGESTION;

public class VistaGestion extends AppCompatActivity {

    private Context ctx;

    private Toolbar tbMain;
    private TextView tvTitulo;
    private TextView tvSubtitutlo;

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
    private TextView PagoRealizado;
    private TextView TipoImagen;
    private TextView Evidencia;
    private TextView Gerente;
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
    private TextView etFolioRecibo;
    private TextView etTipoImagen;
    private TextView etGerente;
    private TextView etNoDetalle;

    private ImageView ivEvidencia;
    private ImageView ivFirma;

    private byte[] byteEvidencia;

    private String idRespuesta = "";
    private String tipoGestion = "0";
    private String tipoPrestamo = "";

    private GoogleMap mMap;
    private Marker mMarker;
    private String root = Environment.getExternalStorageDirectory().getAbsolutePath()+"/Android/data/com.sidert.sidertmovil/Files/";

    private Calendar mCalendar;
    private SimpleDateFormat sdf = new SimpleDateFormat(Constants.FORMAT_TIMESTAMP, Locale.US);

    private boolean is_solicitud = false;

    private String resumen;
    private String cliente;

    private DBhelper dBhelper;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_gestion);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        ctx = this;

        dBhelper = new DBhelper(ctx);
        db = dBhelper.getWritableDatabase();

        tbMain          = findViewById(R.id.tbMain);

        tvTitulo        = findViewById(R.id.tvTitulo);
        tvSubtitutlo    = findViewById(R.id.tvSubtitulo);

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
        PagoRealizado     = findViewById(R.id.PagoRealizado);
        Evidencia         = findViewById(R.id.Evidencia);
        Firma             = findViewById(R.id.Firma);
        TipoImagen        = findViewById(R.id.TipoImagen);
        Gerente           = findViewById(R.id.Gerente);
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
        etFolioRecibo       = findViewById(R.id.tvFolioRecibo);
        etNoDetalle         = findViewById(R.id.tvNoDetalle);
        etTipoImagen        = findViewById(R.id.tvTipoImagen);
        etGerente           = findViewById(R.id.tvGerente);

        ivEvidencia     = findViewById(R.id.ivEvidencia);
        ivFirma         = findViewById(R.id.ivFirma);

        mCalendar = Calendar.getInstance();
        mapGestion.onCreate(savedInstanceState);
        setSupportActionBar(tbMain);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        final Bundle datos = this.getIntent().getBundleExtra(Constants.PARAMS);

        idRespuesta = datos.getString(ID_RESPUESTA);
        tipoGestion = datos.getString(TIPO_GESTION);
        tipoPrestamo = datos.getString(TIPO_PRESTAMO);
        resumen     = datos.getString(RESUMEN);
        cliente     = datos.getString(CLIENTE);

        if (datos.getString(CANCELACION).isEmpty() && datos.getString(ESTATUS).equals("2"))
            is_solicitud = true;

        invalidateOptionsMenu();


        //Log.e("latitud", String.valueOf(datos.getDouble(LATITUD)));
        //Log.e("longitud", String.valueOf(datos.getDouble(LONGITUD)));
        if (datos.getDouble(LATITUD) == 0 && datos.getDouble(LONGITUD) == 0){
            tvNoMapa.setVisibility(View.VISIBLE);
            mapGestion.setVisibility(View.GONE);
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
                etPagoRealizado.setText(datos.getString(PAGO_REALIZADO));
                etPagoRealizado.setVisibility(View.VISIBLE);

                etMedioPago.setText(datos.getString(MEDIO_PAGO));

                if ((Miscellaneous.MedioPago(etMedioPago) >= 0 && Miscellaneous.MedioPago(etMedioPago) < 6 || Miscellaneous.MedioPago(etMedioPago) == 7 || Miscellaneous.MedioPago(etMedioPago) == 8)){ //Banco y Oxxo
                    MedioPago.setVisibility(View.VISIBLE);
                    etMedioPago.setVisibility(View.VISIBLE);
                    Fecha.setVisibility(View.VISIBLE);
                    Fecha.setText("Fecha de Depósito");
                    etFecha.setText(datos.getString(FECHA_DEPOSITO));
                    etFecha.setVisibility(View.VISIBLE);
                    PagoRealizado.setVisibility(View.VISIBLE);
                    etPagoRealizado.setText(datos.getString(PAGO_REALIZADO));
                    etPagoRealizado.setVisibility(View.VISIBLE);

                    if (datos.containsKey(RESUMEN_INTEGRANTES)){
                        if (datos.getBoolean(RESUMEN_INTEGRANTES)){

                        }
                        else{
                            NoDetalle.setVisibility(View.VISIBLE);
                            etNoDetalle.setVisibility(View.VISIBLE);
                            etNoDetalle.setText("No cuenta con el detalle de la ficha");
                        }
                    }
                    TipoImagen.setVisibility(View.VISIBLE);
                    etTipoImagen.setVisibility(View.VISIBLE);
                    etTipoImagen.setText(datos.getString(TIPO_IMAGEN));
                    Evidencia.setVisibility(View.VISIBLE);
                    Evidencia.setText("Comprobante");
                    File evidenciaFile = new File(ROOT_PATH + "Evidencia/"+datos.getString(EVIDENCIA));
                    Uri uriEvidencia = Uri.fromFile(evidenciaFile);
                    byteEvidencia = Miscellaneous.getBytesUri(ctx, uriEvidencia, 1);
                    Glide.with(ctx).load(uriEvidencia).centerCrop().into(ivEvidencia);
                    ivEvidencia.setVisibility(View.VISIBLE);

                    Gerente.setVisibility(View.VISIBLE);
                    etGerente.setVisibility(View.VISIBLE);
                    etGerente.setText(datos.getString(GERENTE));
                    if (datos.getString(Constants.GERENTE).equals("SI")) { //Si está el gerente
                        Firma.setVisibility(View.VISIBLE);
                        File firmaFile = new File(ROOT_PATH + "Firma/"+datos.getString(FIRMA));
                        Uri uriFirma = Uri.fromFile(firmaFile);
                        Glide.with(ctx).load(uriFirma).into(ivFirma);
                        ivFirma.setVisibility(View.VISIBLE);
                    }

                }
                else if (Miscellaneous.MedioPago(etMedioPago) == 6 || datos.getString(MEDIO_PAGO).equals("EFECTIVO")){ //Efectivo o SIDERT
                    MedioPago.setVisibility(View.VISIBLE);
                    etMedioPago.setText(datos.getString(MEDIO_PAGO));
                    etMedioPago.setVisibility(View.VISIBLE);
                    PagoRealizado.setVisibility(View.VISIBLE);
                    Evidencia.setVisibility(View.VISIBLE);
                    Evidencia.setText("Comprobante");

                    FolioRecibo.setVisibility(View.VISIBLE);
                    etFolioRecibo.setText(datos.getString(FOLIO_TICKET));
                    etFolioRecibo.setVisibility(View.VISIBLE);
                    TipoImagen.setVisibility(View.VISIBLE);
                    etTipoImagen.setVisibility(View.VISIBLE);
                    etTipoImagen.setText(datos.getString(TIPO_IMAGEN));
                    File evidenciaFile = new File(ROOT_PATH + "Evidencia/"+datos.getString(EVIDENCIA));
                    Uri uriEvidencia = Uri.fromFile(evidenciaFile);
                    byteEvidencia = Miscellaneous.getBytesUri(ctx, uriEvidencia, 1);
                    Glide.with(ctx).load(uriEvidencia).centerCrop().into(ivEvidencia);
                    ivEvidencia.setVisibility(View.VISIBLE);
                    Gerente.setVisibility(View.VISIBLE);
                    etGerente.setVisibility(View.VISIBLE);
                    etGerente.setText(datos.getString(GERENTE));
                    if (datos.getString(Constants.GERENTE).equals("SI")) { //Si está el gerente
                        Firma.setVisibility(View.VISIBLE);
                        File firmaFile = new File(ROOT_PATH + "Firma/"+datos.getString(FIRMA));
                        Uri uriFirma = Uri.fromFile(firmaFile);
                        Glide.with(ctx).load(uriFirma).into(ivFirma);
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
                    TipoImagen.setVisibility(View.VISIBLE);
                    etTipoImagen.setVisibility(View.VISIBLE);
                    etTipoImagen.setText(datos.getString(TIPO_IMAGEN));
                    Evidencia.setVisibility(View.VISIBLE);
                    File fachadaFile = new File(ROOT_PATH + "Fachada/"+datos.getString(EVIDENCIA));
                    Uri uriFachada = Uri.fromFile(fachadaFile);
                    byteEvidencia = Miscellaneous.getBytesUri(ctx, uriFachada, 0);
                    Glide.with(ctx).load(uriFachada).centerCrop().into(ivEvidencia);
                    ivEvidencia.setVisibility(View.VISIBLE);

                    Gerente.setVisibility(View.VISIBLE);
                    etGerente.setVisibility(View.VISIBLE);
                    etGerente.setText(datos.getString(GERENTE));
                    if (datos.getString(Constants.GERENTE).equals("SI")) { //Si está el gerente
                        Firma.setVisibility(View.VISIBLE);
                        File firmaFile = new File(ROOT_PATH + "Firma/"+datos.getString(FIRMA));
                        Uri uriFirma = Uri.fromFile(firmaFile);
                        Glide.with(ctx).load(uriFirma).into(ivFirma);
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
                    TipoImagen.setVisibility(View.VISIBLE);
                    etTipoImagen.setVisibility(View.VISIBLE);
                    etTipoImagen.setText(datos.getString(TIPO_IMAGEN));
                    Evidencia.setVisibility(View.VISIBLE);
                    File fachadaFile = new File(ROOT_PATH + "Fachada/"+datos.getString(EVIDENCIA));
                    Uri uriFachada = Uri.fromFile(fachadaFile);
                    byteEvidencia = Miscellaneous.getBytesUri(ctx, uriFachada, 0);
                    Glide.with(ctx).load(uriFachada).centerCrop().into(ivEvidencia);
                    ivEvidencia.setVisibility(View.VISIBLE);

                    Gerente.setVisibility(View.VISIBLE);
                    etGerente.setVisibility(View.VISIBLE);
                    etGerente.setText(datos.getString(GERENTE));
                    if (datos.getString(Constants.GERENTE).equals("SI")) { //Si está el gerente
                        Firma.setVisibility(View.VISIBLE);
                        File firmaFile = new File(ROOT_PATH + "Firma/"+datos.getString(FIRMA));
                        Uri uriFirma = Uri.fromFile(firmaFile);
                        Glide.with(ctx).load(uriFirma).into(ivFirma);
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
            MotivoAclaracion.setVisibility(View.VISIBLE);
            etMotivoAclaracion.setText(datos.getString(MOTIVO_ACLARACION));
            etMotivoAclaracion.setVisibility(View.VISIBLE);
            Comentario.setVisibility(View.VISIBLE);
            etComentario.setText(datos.getString(COMENTARIO));
            etComentario.setVisibility(View.VISIBLE);
            Gerente.setVisibility(View.VISIBLE);
            etGerente.setVisibility(View.VISIBLE);
            etGerente.setText(datos.getString(GERENTE));
            if (datos.getString(Constants.GERENTE).equals("SI")) { //Si está el gerente
                Firma.setVisibility(View.VISIBLE);
                File firmaFile = new File(ROOT_PATH + "Firma/"+datos.getString(FIRMA));
                Uri uriFirma = Uri.fromFile(firmaFile);
                Glide.with(ctx).load(uriFirma).into(ivFirma);
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
            TipoImagen.setVisibility(View.VISIBLE);
            etTipoImagen.setVisibility(View.VISIBLE);
            etTipoImagen.setText(datos.getString(TIPO_IMAGEN));
            Evidencia.setVisibility(View.VISIBLE);
            File fachadaFile = new File(ROOT_PATH + "Fachada/"+datos.getString(EVIDENCIA));
            Uri uriFachada = Uri.fromFile(fachadaFile);
            byteEvidencia = Miscellaneous.getBytesUri(ctx, uriFachada, 0);
            Glide.with(ctx).load(uriFachada).centerCrop().into(ivEvidencia);

            Gerente.setVisibility(View.VISIBLE);
            etGerente.setVisibility(View.VISIBLE);
            etGerente.setText(datos.getString(GERENTE));
            ivEvidencia.setVisibility(View.VISIBLE);
            if (datos.getString(Constants.GERENTE).equals("SI")) { //Si está el gerente
                Firma.setVisibility(View.VISIBLE);
                File firmaFile = new File(ROOT_PATH + "Firma/"+datos.getString(FIRMA));
                Uri uriFirma = Uri.fromFile(firmaFile);
                Glide.with(ctx).load(uriFirma).into(ivFirma);

                ivFirma.setVisibility(View.VISIBLE);
            }
        }
        else{ //No hay información
            Toast.makeText(ctx, "No se encontraron datos", Toast.LENGTH_SHORT).show();
            finish();
        }

        mapGestion.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(LatLng latLng) {
                        final AlertDialog ubicacion_dlg = Popups.showDialogConfirm(ctx, Constants.question,
                                R.string.ver_en_maps, R.string.ver_maps, new Popups.DialogMessage() {
                                    @Override
                                    public void OnClickListener(AlertDialog dialog) {
                                        Intent i_maps = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse("geo:"+datos.getDouble(LATITUD)+","+datos.getDouble(LONGITUD)+"?z=16&q="+datos.getDouble(LATITUD)+","+datos.getDouble(LONGITUD)+"()"));
                                        i_maps.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
                                        startActivity(i_maps);
                                        dialog.dismiss();

                                    }
                                }, R.string.cancel, new Popups.DialogMessage() {
                                    @Override
                                    public void OnClickListener(AlertDialog dialog) {
                                        dialog.dismiss();
                                    }
                                });
                        Objects.requireNonNull(ubicacion_dlg.getWindow()).requestFeature(Window.FEATURE_NO_TITLE);
                        ubicacion_dlg.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                        ubicacion_dlg.show();
                    }
                });
            }
        });

        ivEvidencia.setOnClickListener(ivEvidencia_OnClick);

    }

    private View.OnClickListener ivEvidencia_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            final AlertDialog fachada_dlg = Popups.showDialogConfirm(ctx, Constants.question,
                    R.string.ver_fotografia, R.string.ver_imagen, new Popups.DialogMessage() {
                        @Override
                        public void OnClickListener(AlertDialog dialog) {
                            Intent i = new Intent(VistaGestion.this, VerImagen.class);
                            i.putExtra(Constants.IMAGEN, byteEvidencia);
                            startActivity(i);
                            dialog.dismiss();

                        }
                    }, R.string.cancel, new Popups.DialogMessage() {
                        @Override
                        public void OnClickListener(AlertDialog dialog) {
                            dialog.dismiss();
                        }
                    });
            Objects.requireNonNull(fachada_dlg.getWindow()).requestFeature(Window.FEATURE_NO_TITLE);
            fachada_dlg.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            fachada_dlg.show();
        }
    };

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

                mGooglemap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(LatLng latLng) {

                    }
                });
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

    public void solicitudCancel(){
        is_solicitud = false;
        invalidateOptionsMenu();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_cancel_gestiones, menu);
        if (!is_solicitud)
            menu.getItem(0).setVisible(is_solicitud);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.nvCancel:
                dialog_cancel_gestion dlg_cancel = new dialog_cancel_gestion();
                Bundle b = new Bundle();
                b.putString(ID_RESPUESTA, idRespuesta);
                b.putString(TIPO_GESTION, tipoGestion);
                b.putString(TIPO_PRESTAMO, tipoPrestamo);
                dlg_cancel.setArguments(b);
                dlg_cancel.show(getSupportFragmentManager(), DIALOGCANCELGESTION);
                break;
            case R.id.nvShare:
                if (resumen.isEmpty() && cliente.isEmpty()){
                    String sql = "";
                    Cursor row = null;

                    if (tipoGestion.equals("1") && tipoPrestamo.equals("VIGENTE")) {
                        sql = "SELECT r.fecha_fin, c.nombre FROM " + TBL_RESPUESTAS_IND_T + " AS r INNER JOIN " + TBL_PRESTAMOS_IND_T + " AS p ON r.id_prestamo = p.id_prestamo INNER JOIN " + TBL_CARTERA_IND_T + " AS c ON p.id_cliente = c.id_cartera WHERE r._id = ? LIMIT 1";
                        row = db.rawQuery(sql, new String[]{idRespuesta});
                    }
                    else if (tipoGestion.equals("2") && tipoPrestamo.equals("VIGENTE")){
                        sql = "SELECT r.fecha_fin, c.nombre FROM " + TBL_RESPUESTAS_GPO_T + " AS r INNER JOIN " + TBL_PRESTAMOS_GPO_T + " AS p ON r.id_prestamo = p.id_prestamo INNER JOIN " + TBL_CARTERA_GPO_T + " AS c ON p.id_grupo = c.id_cartera WHERE r._id = ? LIMIT 1";
                        row = db.rawQuery(sql, new String[]{idRespuesta});
                    }
                    else if (tipoGestion.equals("1") && tipoPrestamo.equals("VENCIDA")){
                        sql = "SELECT r.fecha_fin, c.nombre FROM " + TBL_RESPUESTAS_IND_V_T + " AS r INNER JOIN " + TBL_PRESTAMOS_IND_T + " AS p ON r.id_prestamo = p.id_prestamo INNER JOIN " + TBL_CARTERA_IND_T + " AS c ON p.id_cliente = c.id_cartera WHERE r._id = ? LIMIT 1";
                        row = db.rawQuery(sql, new String[]{idRespuesta});
                    }
                    else if(tipoGestion.equals("2") && tipoPrestamo.equals("VENCIDA")){
                        Log.e("Respuesta","Vencida Grupal Integrante: "+idRespuesta);

                        sql = "SELECT r.fecha_fin, m.nombre FROM " + TBL_RESPUESTAS_INTEGRANTE_T + " AS r INNER JOIN " + TBL_MIEMBROS_GPO_T + " AS m ON r.id_prestamo = m.id_prestamo_integrante WHERE r._id = ? LIMIT 1";
                        row = db.rawQuery(sql, new String[]{idRespuesta});
                    }

                    row.moveToFirst();

                    tvTitulo.setText(row.getString(1));
                    tvSubtitutlo.setText("Fin Gestión "+row.getString(0));

                    cliente = row.getString(1);

                    row.close();

                    String mPath = ROOT_PATH+"Resumen";
                    String name = "";
                    File img = null;

                    try {
                        // create bitmap screen capture
                        View v1 = getWindow().getDecorView().getRootView();
                        v1.setDrawingCacheEnabled(true);
                        Bitmap bitmap = Bitmap.createBitmap(v1.getDrawingCache());
                        v1.setDrawingCacheEnabled(false);
                        File directory = new File(mPath);
                        if(!directory.exists())
                        {
                            Log.v("Carpeta", "No existe Resumen");
                            directory.mkdir();
                        }

                        name = UUID.randomUUID().toString() + ".jpg";
                        img = new File(mPath+"/"+name);


                        FileOutputStream outputStream = new FileOutputStream(img);
                        int quality = 100;
                        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
                        outputStream.flush();
                        outputStream.close();

                        resumen = name;
                    } catch (Throwable e) {
                        // Several error may come out with file handling or DOM
                        e.printStackTrace();
                    }

                    HashMap<Integer, String> values = new HashMap();
                    values.put(0, idRespuesta);
                    values.put(1, name);
                    values.put(2, cliente);
                    values.put(3, tipoGestion);
                    dBhelper.saveResumenGestion(db, values);
                }

                File img = new File(ROOT_PATH+"Resumen/"+resumen);
                Uri imgUri = Uri.parse(img.getPath());
                Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
                whatsappIntent.setType("text/plain");
                whatsappIntent.setPackage("com.whatsapp");
                whatsappIntent.putExtra(Intent.EXTRA_TEXT, "Le comparto el resumen de la gestión del cliente " + cliente);
                whatsappIntent.putExtra(Intent.EXTRA_STREAM, imgUri);
                whatsappIntent.setType("image/jpeg");
                whatsappIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

                try {
                    ctx.startActivity(whatsappIntent);
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(ctx, "No cuenta con Whatsapp", Toast.LENGTH_SHORT).show();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
