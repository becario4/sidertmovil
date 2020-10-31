package com.sidert.sidertmovil.activities;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.database.DBhelper;
import com.sidert.sidertmovil.fragments.view_pager.recuperacion_ind_fragment;
import com.sidert.sidertmovil.utils.CanvasCustom;
import com.sidert.sidertmovil.utils.Miscellaneous;
import com.sidert.sidertmovil.utils.Popups;
import com.sidert.sidertmovil.utils.Servicios_Sincronizado;
import com.sidert.sidertmovil.utils.SessionManager;
import com.sidert.sidertmovil.utils.Validator;
import com.sidert.sidertmovil.utils.ValidatorTextView;
import com.theartofdev.edmodo.cropper.CropImage;

import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Objects;

import static com.sidert.sidertmovil.utils.Constants.FOLIO;
import static com.sidert.sidertmovil.utils.Constants.FORMAT_TIMESTAMP;
import static com.sidert.sidertmovil.utils.Constants.MESSAGE;
import static com.sidert.sidertmovil.utils.Constants.ORDER_ID;
import static com.sidert.sidertmovil.utils.Constants.PICTURE;
import static com.sidert.sidertmovil.utils.Constants.REQUEST_CODE_CAMARA_TICKET;
import static com.sidert.sidertmovil.utils.Constants.REQUEST_CODE_GALERIA;
import static com.sidert.sidertmovil.utils.Constants.REQUEST_CODE_IMPRESORA;
import static com.sidert.sidertmovil.utils.Constants.RES_PRINT;
import static com.sidert.sidertmovil.utils.Constants.ROOT_PATH;
import static com.sidert.sidertmovil.utils.Constants.TBL_RECIBOS_AGF_CC;
import static com.sidert.sidertmovil.utils.Constants.TBL_RECUPERACION_RECIBOS;
import static com.sidert.sidertmovil.utils.Constants.TIMESTAMP;
import static com.sidert.sidertmovil.utils.Constants.question;
import static com.sidert.sidertmovil.utils.Constants.warning;

public class RecuperacionRecibos extends AppCompatActivity {

    private Context ctx;

    private SessionManager session;

    private LinearLayout llIntegrantes;

    private EditText etTipoCobro;
    private EditText etNombre;
    private EditText etIntegrantes;
    private EditText etMeses;
    private EditText etMonto;

    private LinearLayout llImprimirRecibo;
    private LinearLayout llDuracionPrestamo;
    private TextView tvImprimirRecibo;
    private ImageButton ibImprimir;
    private LinearLayout llFolioRecibo;
    private EditText etFolioRecibo;
    private LinearLayout llFotoGaleria;
    private TextView tvFotoGaleria;
    private ImageButton ibFoto;
    private ImageButton ibGaleria;
    private ImageView ivEvidencia;

    private TextView tvMedioPago;

    public byte[] byteEvidencia;

    private DBhelper dBhelper;
    private SQLiteDatabase db;

    private String grupoId ="";
    private String numSolicitud = "";
    private String nombre ="";
    private String nomFirma = "";
    private String monto = "0.0";
    private String tipo = "";
    private String clienteId = "";
    private int meses = 0;

    //private HashMap<Integer, String> item;

    private String folio_impreso = "";

    public String[] _medio_pago;
    public String[] _imprimir;

    private int res_impresion = 0;

    private Uri imageUri;

    private String tipoImagen = "";

    private Validator validator = new Validator();
    private ValidatorTextView validatorTV = new ValidatorTextView();

    private Long idRespuesta = 0L;

    private boolean isEdit = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperacion_recibos);

        ctx = this;

        dBhelper = new DBhelper(ctx);
        db = dBhelper.getWritableDatabase();

        session = new SessionManager(ctx);

        Toolbar tbMain      = findViewById(R.id.tbMain);

        etTipoCobro         = findViewById(R.id.etTipoCobro);
        etNombre            = findViewById(R.id.etNombre);
        etIntegrantes       = findViewById(R.id.etIntegrantes);
        etMeses             = findViewById(R.id.etMesesPrestamo);
        etMonto             = findViewById(R.id.etMonto);

        llIntegrantes   = findViewById(R.id.llIntegrantes);

        tvMedioPago     = findViewById(R.id.tvMedioPago);

        llDuracionPrestamo  = findViewById(R.id.llDuracionPrestamo);
        llImprimirRecibo = findViewById(R.id.llImprimirRecibo);
        tvImprimirRecibo    = findViewById(R.id.tvImprimirRecibo);
        ibImprimir            = findViewById(R.id.ibImprimir);
        llFolioRecibo       = findViewById(R.id.llFolioRecibo);
        etFolioRecibo       = findViewById(R.id.etFolioRecibo);
        llFotoGaleria       = findViewById(R.id.llFotoGaleria);
        tvFotoGaleria       = findViewById(R.id.tvFotoGaleria);
        ibFoto              = findViewById(R.id.ibFoto);
        ibGaleria           = findViewById(R.id.ibGaleria);
        ivEvidencia         = findViewById(R.id.ivEvidencia);


        grupoId      = getIntent().getStringExtra("grupo_id");
        numSolicitud = getIntent().getStringExtra("num_solicitud");
        nombre       = getIntent().getStringExtra("nombre");
        nomFirma     = getIntent().getStringExtra("nombre_firma");
        monto        = getIntent().getStringExtra("monto");
        meses        = getIntent().getIntExtra("meses",0);
        tipo         = getIntent().getStringExtra("tipo");
        clienteId    = getIntent().getStringExtra("cliente_id");

        if (!getIntent().getBooleanExtra("print_recibo", false)){
            AlertDialog solicitud;
            solicitud = Popups.showDialogMessage(this, warning,
                    "No se puede hacer el cobro de Apoyo a gastos funerarios porque el monto es mayor a $29,000.00", R.string.accept, new Popups.DialogMessage() {
                        @Override
                        public void OnClickListener(AlertDialog dialog) {
                            dialog.dismiss();
                            finish();
                        }
                    });
            solicitud.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            solicitud.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            solicitud.show();
        }

        setSupportActionBar(tbMain);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle(nombre);

        etTipoCobro.setText("CÍRCULO DE CRÉDITO");
        if (tipo.equals("AGF"))
            etTipoCobro.setText("APOYO GASTOS FUNERARIOS");

        if (tipo.equals("AGF") && getIntent().getIntExtra("integrantes",0) > 0){
            llIntegrantes.setVisibility(View.VISIBLE);
            llDuracionPrestamo.setVisibility(View.VISIBLE);
        }

        etNombre.setText(nombre);
        etIntegrantes.setText(String.valueOf(getIntent().getIntExtra("integrantes", 0)));
        etMeses.setText(getIntent().getIntExtra("meses",0)+" MESES");
        etMonto.setText(Miscellaneous.moneyFormat(monto));

        _medio_pago = getResources().getStringArray(R.array.medio_pago);
        _imprimir = getResources().getStringArray(R.array.imprimir);

        tvMedioPago.setOnClickListener(tvMedioPago_OnClick);
        tvImprimirRecibo.setOnClickListener(tvImprimirRecibo_OnClick);

        ibImprimir.setOnClickListener(ibImprimir_OnClick);
        ibFoto.setOnClickListener(ibFoto_OnClick);
        ibGaleria.setOnClickListener(ibGaleria_OnClick);
        ivEvidencia.setOnClickListener(ivEvidencia_OnClick);

        initComponents();
    }

    private View.OnClickListener tvMedioPago_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
            builder.setTitle(R.string.selected_option)
                    .setItems(_medio_pago, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int position) {
                            tvMedioPago.setError(null);
                            tvMedioPago.setText(_medio_pago[position]);
                            byteEvidencia = null;
                            if (Miscellaneous.MedioPago(tvMedioPago) == 6) {
                                llImprimirRecibo.setVisibility(View.VISIBLE);
                                ibGaleria.setEnabled(false);
                                ibGaleria.setBackground(ctx.getResources().getDrawable(R.drawable.btn_disable));
                                ibFoto.setVisibility(View.VISIBLE);
                                ibGaleria.setVisibility(View.VISIBLE);
                                llFotoGaleria.setVisibility(View.VISIBLE);
                                ivEvidencia.setVisibility(View.GONE);
                            }
                            else{
                                llImprimirRecibo.setVisibility(View.GONE);
                                ibGaleria.setEnabled(true);
                                ibGaleria.setBackground(ctx.getResources().getDrawable(R.drawable.round_corner_blue));
                                ibFoto.setVisibility(View.VISIBLE);
                                ibGaleria.setVisibility(View.VISIBLE);
                                llFotoGaleria.setVisibility(View.VISIBLE);
                                ivEvidencia.setVisibility(View.GONE);
                            }
                        }
                    });
            builder.create();
            builder.show();
        }
    };

    private View.OnClickListener tvImprimirRecibo_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
            builder.setTitle(R.string.selected_option)
                    .setItems(R.array.imprimir, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int position) {
                            tvImprimirRecibo.setError(null);
                            tvImprimirRecibo.setText(_imprimir[position]);
                            SelectImprimirRecibos(position);
                        }
                    });
            builder.create();
            builder.show();
        }
    };

    private View.OnClickListener ibImprimir_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i_formato_recibo = new Intent(ctx, FormatoRecibos.class);
            i_formato_recibo.putExtra("grupo_id", grupoId);
            i_formato_recibo.putExtra("num_solicitud", numSolicitud);
            i_formato_recibo.putExtra("nombre", nombre);
            i_formato_recibo.putExtra("nombre_firma", nomFirma);
            i_formato_recibo.putExtra("monto", monto);
            i_formato_recibo.putExtra("tipo", tipo);
            i_formato_recibo.putExtra("meses", meses);
            i_formato_recibo.putExtra("res_impresion", 0);
            i_formato_recibo.putExtra("is_reeimpresion", false);
            startActivityForResult(i_formato_recibo,REQUEST_CODE_IMPRESORA);

        }
    };

    private View.OnClickListener ibFoto_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i = new Intent(ctx, CameraVertical.class);
            startActivityForResult(i, REQUEST_CODE_CAMARA_TICKET);
        }
    };

    private View.OnClickListener ibGaleria_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (ContextCompat.checkSelfPermission(ctx, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                    || ContextCompat.checkSelfPermission(ctx,Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                requestPermissions(new String[] {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 123);
            } else {
                int compress = 10;
                if( Build.MANUFACTURER.toUpperCase().equals("SAMSUNG"))
                    compress = 40;
                CropImage.activity()
                        .setAutoZoomEnabled(true)
                        .setMinCropWindowSize(3000,4000)
                        .setOutputCompressQuality(compress)
                        .start(RecuperacionRecibos.this);
            }

        }
    };

    private View.OnClickListener ivEvidencia_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (isEdit) {
                if (tvMedioPago.getText().toString().trim().toUpperCase().equals("EFECTIVO")) {
                    final AlertDialog evidencia_dlg = Popups.showDialogConfirm(ctx, question,
                            R.string.capturar_nueva_fotografia, R.string.fotografia, new Popups.DialogMessage() {
                                @Override
                                public void OnClickListener(AlertDialog dialog) {
                                    Intent i = new Intent(ctx, CameraVertical.class);
                                    startActivityForResult(i, REQUEST_CODE_CAMARA_TICKET);
                                    dialog.dismiss();

                                }
                            }, R.string.cancel, new Popups.DialogMessage() {
                                @Override
                                public void OnClickListener(AlertDialog dialog) {
                                    dialog.dismiss();
                                }
                            });
                    Objects.requireNonNull(evidencia_dlg.getWindow()).requestFeature(Window.FEATURE_NO_TITLE);
                    evidencia_dlg.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                    evidencia_dlg.show();
                } else {
                    final AlertDialog evidencia_dlg = Popups.showDialogConfirmImage(ctx, question,
                            R.string.capturar_foto_galeria, R.string.fotografia, new Popups.DialogMessage() {
                                @Override
                                public void OnClickListener(AlertDialog dialog) {
                                    Intent i = new Intent(ctx, CameraVertical.class);
                                    startActivityForResult(i, REQUEST_CODE_CAMARA_TICKET);
                                    dialog.dismiss();

                                }
                            }, R.string.galeria, new Popups.DialogMessage() {
                                @Override
                                public void OnClickListener(AlertDialog dialog) {
                                    int compress = 10;
                                    if (Build.MANUFACTURER.toUpperCase().equals("SAMSUNG"))
                                        compress = 40;
                                    CropImage.activity()
                                            .setAutoZoomEnabled(true)
                                            .setMinCropWindowSize(3000, 4000)
                                            .setOutputCompressQuality(compress)
                                            .start(RecuperacionRecibos.this);
                                    dialog.dismiss();
                                }
                            }, R.string.cancel, new Popups.DialogMessage() {
                                @Override
                                public void OnClickListener(AlertDialog dialog) {
                                    dialog.dismiss();
                                }
                            });
                    Objects.requireNonNull(evidencia_dlg.getWindow()).requestFeature(Window.FEATURE_NO_TITLE);
                    evidencia_dlg.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                    evidencia_dlg.show();
                }
            }
        }
    };

    private void SelectImprimirRecibos(int pos){
        switch (pos){
            case 0: // Imprimir Recibos
                ibImprimir.setVisibility(View.VISIBLE);
                if (!folio_impreso.trim().isEmpty()) {
                    tvImprimirRecibo.setError(null);
                    etFolioRecibo.setText(folio_impreso);
                    etFolioRecibo.setEnabled(false);
                    etFolioRecibo.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
                    etFolioRecibo.setError(null);
                    llFolioRecibo.setVisibility(View.VISIBLE);
                }
                else {
                    llFolioRecibo.setVisibility(View.GONE);
                }
                break;
            case 1: //No cuenta con bateria la impresora
                Log.v("aqui pasa", "Select Imprimir recibos");
                etFolioRecibo.setError(getResources().getString(R.string.campo_requerido));
                tvImprimirRecibo.setError(null);
                llFolioRecibo.setVisibility(View.VISIBLE);
                etFolioRecibo.setText("");
                etFolioRecibo.setEnabled(true);
                etFolioRecibo.setBackground(getResources().getDrawable(R.drawable.et_rounded_edges));
                etFolioRecibo.setHint(R.string.folio_sidert);
                etFolioRecibo.setInputType(InputType.TYPE_CLASS_NUMBER);
                ibImprimir.setVisibility(View.GONE);
                break;
            default: // Sin seleccionar alguna opción o cualquier valor diferente
                tvImprimirRecibo.setError("");
                llFolioRecibo.setVisibility(View.GONE);
                ibImprimir.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case REQUEST_CODE_IMPRESORA:
                if (resultCode == Activity.RESULT_OK){
                    if (data != null){
                        Toast.makeText(ctx, data.getStringExtra(MESSAGE), Toast.LENGTH_SHORT).show();
                        if(data.getIntExtra(RES_PRINT,0) == 1 || data.getIntExtra(RES_PRINT,0) == 2){

                            String sql = "SELECT * FROM " + TBL_RECUPERACION_RECIBOS + " WHERE grupo_id = ? AND num_solicitud = ? AND tipo = ?";
                            Cursor row = db.rawQuery(sql, new String[]{grupoId, numSolicitud, tipo});

                            if (row.getCount() == 0){
                                HashMap<Integer, String> params = new HashMap<>();
                                params.put(0, grupoId);
                                params.put(1, numSolicitud);
                                params.put(2, tvMedioPago.getText().toString());
                                params.put(3, "");
                                params.put(4, "");
                                params.put(5, "");
                                params.put(6, "");
                                params.put(7, tipo);
                                params.put(8, nombre);
                                params.put(9, "0");
                                params.put(10, monto);
                                params.put(11, "SI");
                                params.put(12, "");
                                if (tipo.equals("CC"))
                                    params.put(13, clienteId);
                                else
                                    params.put(13, "");

                                idRespuesta = dBhelper.saveRecuperacionRecibos(db, params);

                            }

                            tvMedioPago.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
                            tvMedioPago.setEnabled(false);
                            tvImprimirRecibo.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
                            tvImprimirRecibo.setEnabled(false);
                            res_impresion = data.getIntExtra(RES_PRINT, 0);
                            folio_impreso = tipo + "-" + session.getUser().get(0) + "-" + data.getIntExtra(FOLIO,0);
                            etFolioRecibo.setText(folio_impreso);
                            tvImprimirRecibo.setError(null);
                            llFolioRecibo.setVisibility(View.VISIBLE);

                        }
                    }
                }
                break;
            case REQUEST_CODE_CAMARA_TICKET:
                if (resultCode == Activity.RESULT_OK){
                    if (data != null){
                        tipoImagen = "FOTOGRAFIA";
                        ibFoto.setVisibility(View.GONE);
                        ibGaleria.setVisibility(View.GONE);
                        tvFotoGaleria.setError(null);
                        ivEvidencia.setVisibility(View.VISIBLE);
                        byteEvidencia = data.getByteArrayExtra(PICTURE);
                        Glide.with(ctx).load(byteEvidencia).centerCrop().into(ivEvidencia);
                    }
                }
                break;
            case CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE:
                if (data != null){
                    try{
                        tipoImagen = "GALERIA";
                        CropImage.ActivityResult result = CropImage.getActivityResult(data);
                        imageUri = result.getUri();

                        byteEvidencia = Miscellaneous.getBytesUri(ctx, imageUri, 0);

                        ibFoto.setVisibility(View.GONE);
                        ibGaleria.setVisibility(View.GONE);
                        tvFotoGaleria.setError(null);
                        ivEvidencia.setVisibility(View.VISIBLE);

                        View vCanvas = new CanvasCustom(ctx, new SimpleDateFormat(FORMAT_TIMESTAMP).format(Calendar.getInstance().getTime()));

                        Bitmap newBitMap = null;
                        Bitmap bitmap = BitmapFactory.decodeByteArray(byteEvidencia, 0, byteEvidencia.length);

                        Bitmap.Config config = bitmap.getConfig();

                        newBitMap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), config);
                        Canvas canvas = new Canvas(newBitMap);
                        canvas.drawBitmap(bitmap, 0, 0, null);

                        vCanvas.draw(canvas);

                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        newBitMap.compress(Bitmap.CompressFormat.JPEG, 50, baos);

                        byteEvidencia = baos.toByteArray();

                        Glide.with(ctx).load(baos.toByteArray()).centerCrop().into(ivEvidencia);

                    }catch (Exception e){
                        AlertDialog success = Popups.showDialogMessage(ctx, "",
                                R.string.error_image, R.string.accept, new Popups.DialogMessage() {
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
                break;
        }
    }

    private void initComponents(){


        String sql = "";
        Cursor row = null;
        if (tipo.equals("AGF")) {
            sql = "SELECT * FROM " + TBL_RECUPERACION_RECIBOS + " WHERE grupo_id = ? AND num_solicitud = ? AND tipo = ?";
            row = db.rawQuery(sql, new String[]{grupoId, numSolicitud, tipo});
        }
        else{
            Log.e("CLIENTE_ID", clienteId+" aaaa");
            sql = "SELECT * FROM " + TBL_RECUPERACION_RECIBOS + " WHERE grupo_id = ? AND num_solicitud = ? AND tipo = ? AND cliente_id = ?";
            row = db.rawQuery(sql, new String[]{grupoId, numSolicitud, tipo, clienteId});
        }

        Log.e("ROW", "asd"+row.getCount());
        if (row.getCount() > 0){
            row.moveToFirst();

            idRespuesta = row.getLong(0);
            tvMedioPago.setText(row.getString(3));
            if (row.getString(3).equals("EFECTIVO") && row.getString(12).equals("SI")) {
                llImprimirRecibo.setVisibility(View.VISIBLE);
                ibGaleria.setEnabled(false);
                ibGaleria.setBackground(ctx.getResources().getDrawable(R.drawable.btn_disable));
                ibFoto.setVisibility(View.VISIBLE);
                ibGaleria.setVisibility(View.VISIBLE);
                llFotoGaleria.setVisibility(View.VISIBLE);
                ivEvidencia.setVisibility(View.GONE);


                String sqlImpresion = "SELECT * FROM " + TBL_RECIBOS_AGF_CC + " WHERE grupo_id = ? AND num_solicitud = ? AND tipo_recibo = ? AND nombre = ?";
                Cursor rowImpresion = db.rawQuery(sqlImpresion, new String[]{grupoId, numSolicitud, tipo, nombre});

                rowImpresion.moveToFirst();
                tvImprimirRecibo.setText("SI");
                SelectImprimirRecibos(0);
                tvImprimirRecibo.setError(null);
                llFolioRecibo.setVisibility(View.VISIBLE);
                etFolioRecibo.setText(tipo+"-"+session.getUser().get(0)+"-"+rowImpresion.getString(4));
                tvMedioPago.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
                tvMedioPago.setEnabled(false);
                tvImprimirRecibo.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
                tvImprimirRecibo.setEnabled(false);

                if (!row.getString(4).isEmpty()){
                    File evidenciaFile = new File(ROOT_PATH + "Evidencia/"+row.getString(4));
                    Uri uriEvidencia = Uri.fromFile(evidenciaFile);
                    Glide.with(ctx).load(uriEvidencia).centerCrop().into(ivEvidencia);
                    ibFoto.setVisibility(View.GONE);
                    ibGaleria.setVisibility(View.GONE);
                    ivEvidencia.setVisibility(View.VISIBLE);
                    byteEvidencia = Miscellaneous.getBytesUri(ctx, uriEvidencia, 1);
                    tvFotoGaleria.setError(null);
                }
            }
            else if (row.getString(3).equals("EFECTIVO") && row.getString(12).equals("NO")) {
                llImprimirRecibo.setVisibility(View.VISIBLE);
                ibGaleria.setEnabled(false);
                ibGaleria.setBackground(ctx.getResources().getDrawable(R.drawable.btn_disable));
                ibFoto.setVisibility(View.VISIBLE);
                ibGaleria.setVisibility(View.VISIBLE);
                llFotoGaleria.setVisibility(View.VISIBLE);
                ivEvidencia.setVisibility(View.GONE);

                tvImprimirRecibo.setText("NO CUENTA CON BATERIA");
                SelectImprimirRecibos(1);
                tvImprimirRecibo.setError(null);
                llFolioRecibo.setVisibility(View.VISIBLE);
                etFolioRecibo.setText(row.getString(13));
                etFolioRecibo.setEnabled(false);
                etFolioRecibo.setError(null);
                etFolioRecibo.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
                tvMedioPago.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
                tvMedioPago.setEnabled(false);
                tvImprimirRecibo.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
                tvImprimirRecibo.setEnabled(false);

                if (!row.getString(4).isEmpty()){
                    File evidenciaFile = new File(ROOT_PATH + "Evidencia/"+row.getString(4));
                    Uri uriEvidencia = Uri.fromFile(evidenciaFile);
                    Glide.with(ctx).load(uriEvidencia).centerCrop().into(ivEvidencia);
                    ibFoto.setVisibility(View.GONE);
                    ibGaleria.setVisibility(View.GONE);
                    ivEvidencia.setVisibility(View.VISIBLE);
                    byteEvidencia = Miscellaneous.getBytesUri(ctx, uriEvidencia, 1);
                    tvFotoGaleria.setError(null);
                }
            }
            else{
                if (row.getString(4).isEmpty()) {
                    llImprimirRecibo.setVisibility(View.GONE);
                    ibGaleria.setEnabled(true);
                    ibGaleria.setBackground(ctx.getResources().getDrawable(R.drawable.round_corner_blue));
                    ibFoto.setVisibility(View.VISIBLE);
                    ibGaleria.setVisibility(View.VISIBLE);
                    llFotoGaleria.setVisibility(View.VISIBLE);
                    ivEvidencia.setVisibility(View.GONE);
                }
                else{

                    File evidenciaFile = new File(ROOT_PATH + "Evidencia/"+row.getString(4));
                    Uri uriEvidencia = Uri.fromFile(evidenciaFile);
                    Glide.with(ctx).load(uriEvidencia).centerCrop().into(ivEvidencia);

                    byteEvidencia = Miscellaneous.getBytesUri(ctx, uriEvidencia, 1);

                    tvFotoGaleria.setError(null);

                    ibFoto.setVisibility(View.GONE);
                    ibGaleria.setVisibility(View.GONE);
                    llFotoGaleria.setVisibility(View.VISIBLE);
                    ivEvidencia.setVisibility(View.VISIBLE);
                }
            }

            if (row.getInt(10) == 1 || row.getInt(10) == 2){
                tvMedioPago.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
                tvMedioPago.setEnabled(false);
                isEdit = false;
                ibImprimir.setVisibility(View.GONE);
            }
        }
        invalidateOptionsMenu();
    }

    private void Guardar (){
        boolean enviarInfo = false;
        if (!validatorTV.validate(tvMedioPago, new String[]{validatorTV.REQUIRED})){
            if (tvMedioPago.getText().toString().equals("EFECTIVO") &&
                !validatorTV.validate(tvImprimirRecibo, new String[]{validatorTV.REQUIRED})){
                if (!etFolioRecibo.getText().toString().trim().isEmpty()) {
                    if (byteEvidencia != null) {
                        if (idRespuesta > 0) {
                            ContentValues cv = new ContentValues();
                            cv.put("tipo_imagen", tipoImagen);
                            try {
                                cv.put("evidencia", Miscellaneous.save(byteEvidencia, 2));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            cv.put("fecha_termino", Miscellaneous.ObtenerFecha(TIMESTAMP));
                            cv.put("estatus", 1);
                            db.update(TBL_RECUPERACION_RECIBOS, cv, "_id = ?", new String[]{String.valueOf(idRespuesta)});
                            enviarInfo = true;
                        }
                        else{
                            HashMap<Integer, String> params = new HashMap<>();
                            params.put(0, grupoId);
                            params.put(1, numSolicitud);
                            params.put(2, tvMedioPago.getText().toString());
                            try {
                                params.put(3, Miscellaneous.save(byteEvidencia, 2));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            params.put(4, tipoImagen);
                            params.put(5, Miscellaneous.ObtenerFecha(TIMESTAMP));
                            params.put(6, "");
                            params.put(7, tipo);
                            params.put(8, nombre);
                            params.put(9, "1");
                            params.put(10, monto);
                            params.put(11, "NO");
                            params.put(12, etFolioRecibo.getText().toString().trim());
                            if (tipo.equals("CC"))
                                params.put(13, clienteId);
                            else
                                params.put(13, "");
                            idRespuesta = dBhelper.saveRecuperacionRecibos(db, params);
                            enviarInfo = true;
                        }
                    }
                }
                else{
                    Toast.makeText(ctx, "Falta el FOLIO el recibo", Toast.LENGTH_SHORT).show();
                }
            }
            else{
                if (byteEvidencia != null){
                    if (idRespuesta == 0) {
                        HashMap<Integer, String> params = new HashMap<>();
                        params.put(0, grupoId);
                        params.put(1, numSolicitud);
                        params.put(2, tvMedioPago.getText().toString());
                        try {
                            params.put(3, Miscellaneous.save(byteEvidencia, 2));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        params.put(4, tipoImagen);
                        params.put(5, Miscellaneous.ObtenerFecha(TIMESTAMP));
                        params.put(6, "");
                        params.put(7, tipo);
                        params.put(8, nombre);
                        params.put(9, "1");
                        params.put(10, monto);
                        params.put(11, "");
                        params.put(12, "");
                        if (tipo.equals("CC"))
                            params.put(13, clienteId);
                        else
                            params.put(13, "");

                        idRespuesta = dBhelper.saveRecuperacionRecibos(db, params);
                        enviarInfo = true;

                    }
                    else{
                        ContentValues cv = new ContentValues();
                        cv.put("tipo_imagen", tipoImagen);
                        cv.put("fecha_termino", Miscellaneous.ObtenerFecha(TIMESTAMP));
                        cv.put("estatus", 1);
                        db.update(TBL_RECUPERACION_RECIBOS, cv, "_id = ?", new String[]{String.valueOf(idRespuesta)});
                        enviarInfo = true;
                    }
                }else{
                    tvFotoGaleria.setError("");
                    Toast.makeText(ctx, "Falta la imagen para guardar los datos", Toast.LENGTH_SHORT).show();
                }
            }
        }

        if (enviarInfo){
            Servicios_Sincronizado ss = new Servicios_Sincronizado();
            ss.SendRecibos(ctx, false);
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_save, menu);
        if (!isEdit)
            menu.getItem(0).setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.save:
                Guardar();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
