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
//import androidx.annotation.Nullable;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AlertDialog;
//import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.database.DBhelper;
import com.sidert.sidertmovil.utils.CanvasCustom;
import com.sidert.sidertmovil.utils.Miscellaneous;
import com.sidert.sidertmovil.utils.Popups;
import com.sidert.sidertmovil.utils.Servicios_Sincronizado;
import com.sidert.sidertmovil.utils.SessionManager;
import com.sidert.sidertmovil.utils.Validator;
import com.sidert.sidertmovil.utils.ValidatorTextView;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.sidert.sidertmovil.utils.Constants.FOLIO;
import static com.sidert.sidertmovil.utils.Constants.FORMAT_TIMESTAMP;
import static com.sidert.sidertmovil.utils.Constants.MESSAGE;
import static com.sidert.sidertmovil.utils.Constants.PICTURE;
import static com.sidert.sidertmovil.utils.Constants.REQUEST_CODE_CAMARA_TICKET;
import static com.sidert.sidertmovil.utils.Constants.REQUEST_CODE_IMPRESORA;
import static com.sidert.sidertmovil.utils.Constants.RES_PRINT;
import static com.sidert.sidertmovil.utils.Constants.ROOT_PATH;
import static com.sidert.sidertmovil.utils.Constants.TBL_RECIBOS_CC;
import static com.sidert.sidertmovil.utils.Constants.TBL_RECUPERACION_RECIBOS_CC;
import static com.sidert.sidertmovil.utils.Constants.TIMESTAMP;
import static com.sidert.sidertmovil.utils.Constants.question;

/**Clase para el formulario de recuperacion de circulo de credito*/
public class RecuperacionCC extends AppCompatActivity {

    private Context ctx;
    private Toolbar tbMain;

    private TextView tvTipo;
    private RadioGroup rgTipo;
    private TextView tvCostoConsulta;
    private LinearLayout llClienteGrupo;
    private TextView tvClienteGrupo;
    private EditText etClienteGrupo;
    private LinearLayout llCurpCliente;
    private TextView tvCurpCliente;
    private EditText etCurpCliente;
    private LinearLayout llAvalRepresentante;
    private TextView tvAvalRepresentante;
    private EditText etAvalRepresentate;
    private LinearLayout llIntegrantes;
    private EditText etIntegrantes;
    private EditText etMonto;
    private TextView tvMedioPago;

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

    public byte[] byteEvidencia;

    public String[] _medio_pago;
    public String[] _imprimir;
    public String[] _costo_consulta;

    private String folio_impreso = "";

    private int res_impresion = 0;

    private Uri imageUri;

    private String tipoImagen = "";

    private SessionManager session;

    private String tipoCredito = "";
    private String curp = "";
    private Long idRespuesta = 0L;

    private boolean hasFractionalPart = false;
    DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
    DecimalFormat dfnd = new DecimalFormat("#,###.##", symbols);

    private DBhelper dBhelper;
    private SQLiteDatabase db;

    private boolean isEdit = true;

    private Miscellaneous misc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperacion_c_c);

        _medio_pago     = getResources().getStringArray(R.array.medio_pago);
        _imprimir       = getResources().getStringArray(R.array.imprimir);
        _costo_consulta = getResources().getStringArray(R.array.costo_consulta);

        ctx = this;

        misc = new Miscellaneous();

        dBhelper    = new DBhelper(ctx);
        db          = dBhelper.getWritableDatabase();

        session = new SessionManager(ctx);

        tbMain = findViewById(R.id.tbMain);

        tvTipo          = findViewById(R.id.tvTipo);
        rgTipo          = findViewById(R.id.rgTipo);
        tvCostoConsulta         = findViewById(R.id.tvCostoConsulta);
        llClienteGrupo          = findViewById(R.id.llClienteGrupo);
        tvClienteGrupo          = findViewById(R.id.tvClienteGrupo);
        etClienteGrupo          = findViewById(R.id.etClienteGrupo);
        llCurpCliente           = findViewById(R.id.llCurpCliente);
        tvCurpCliente           = findViewById(R.id.tvCurpCliente);
        etCurpCliente           = findViewById(R.id.etCurpCliente);
        llAvalRepresentante     = findViewById(R.id.llAvalRepresentante);
        tvAvalRepresentante     = findViewById(R.id.tvAvalRepresentante);
        etAvalRepresentate      = findViewById(R.id.etAvalRepresentante);
        llIntegrantes   = findViewById(R.id.llIntegrantes);
        etIntegrantes   = findViewById(R.id.etIntegrantes);
        etMonto         = findViewById(R.id.etMonto);
        tvMedioPago     = findViewById(R.id.tvMedioPago);

        llDuracionPrestamo  = findViewById(R.id.llDuracionPrestamo);
        llImprimirRecibo    = findViewById(R.id.llImprimirRecibo);
        tvImprimirRecibo    = findViewById(R.id.tvImprimirRecibo);
        ibImprimir          = findViewById(R.id.ibImprimir);
        llFolioRecibo       = findViewById(R.id.llFolioRecibo);
        etFolioRecibo       = findViewById(R.id.etFolioRecibo);
        llFotoGaleria       = findViewById(R.id.llFotoGaleria);
        tvFotoGaleria       = findViewById(R.id.tvFotoGaleria);
        ibFoto              = findViewById(R.id.ibFoto);
        ibGaleria           = findViewById(R.id.ibGaleria);
        ivEvidencia         = findViewById(R.id.ivEvidencia);

        setSupportActionBar(tbMain);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Bundle b = getIntent().getExtras();

        if (b  != null) {
            tipoCredito = getIntent().getStringExtra("tipo_credito");
            curp = getIntent().getStringExtra("curp");
            idRespuesta = getIntent().getLongExtra("id_respuesta", 0);

        }

        /**Evento de radiogroup para mostrar ciertos campos dependiendo la seleccion*/
        rgTipo.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int rbId) {
                tvTipo.setError(null);
                switch (rbId){
                    case R.id.rbInd:/**si seleccionaron individual*/
                        etClienteGrupo.setFilters(new InputFilter[] {new InputFilter.LengthFilter(60)});
                        etClienteGrupo.setText("");
                        etCurpCliente.setText("");
                        tvClienteGrupo.setText("Nombre de Prospecto de Cliente");
                        etClienteGrupo.setHint("Nombre Completo");
                        tvCurpCliente.setText("Curp del Prospecto");
                        etCurpCliente.setHint("Curp del Prospecto");

                        llAvalRepresentante.setVisibility(View.GONE);
                        tvAvalRepresentante.setText("Nombre del Aval");
                        etAvalRepresentate.setHint("Nombre del Aval");
                        etIntegrantes.setText("1");
                        llIntegrantes.setVisibility(View.GONE);
                        break;
                    case R.id.rbGpo:/**seleccionaron grupal*/
                        etClienteGrupo.setFilters(new InputFilter[] {new InputFilter.LengthFilter(30)});
                        etClienteGrupo.setText("");
                        etCurpCliente.setText("");
                        etAvalRepresentate.setText("");
                        tvClienteGrupo.setText("Nombre de Prospecto de Grupo");
                        etClienteGrupo.setHint("Nombre del Grupo");
                        tvCurpCliente.setText("Curp del Representante");
                        etCurpCliente.setHint("Curp del Representante");
                        llAvalRepresentante.setVisibility(View.VISIBLE);
                        tvAvalRepresentante.setText("Nombre del Representante");
                        etAvalRepresentate.setHint("Nombre Completo");
                        etIntegrantes.setText("");
                        llIntegrantes.setVisibility(View.VISIBLE);
                        break;
                }
                llCurpCliente.setVisibility(View.VISIBLE);
                llClienteGrupo.setVisibility(View.VISIBLE);
            }
        });

        /**Al ingresar un valor en el campo de total de integrates se actualiza el monto en automatico*/
        etIntegrantes.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable e) {
                if (e.length() > 0){
                    if (!Miscellaneous.GetStr(tvCostoConsulta).isEmpty()) {
                        Double costo = Double.parseDouble(Miscellaneous.GetStr(tvCostoConsulta));
                        etMonto.setText(String.valueOf( costo * Integer.parseInt(e.toString())).replace("$", ""));
                    }
                }
                else{
                    etMonto.setText("0");
                }
            }
        });
        etMonto.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence cs, int i, int i1, int i2) {
                if (cs.toString().contains(String.valueOf(dfnd.getDecimalFormatSymbols().getDecimalSeparator())))
                {
                    hasFractionalPart = true;
                } else {
                    hasFractionalPart = false;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                etMonto.removeTextChangedListener(this);

                try {
                    int inilen, endlen;
                    inilen = etMonto.getText().length();
                    String v = s.toString().replace(String.valueOf(dfnd.getDecimalFormatSymbols().getGroupingSeparator()), "");
                    Number n = dfnd.parse(v);
                    int cp = etMonto.getSelectionStart();
                    if (hasFractionalPart) {
                        etMonto.setText(dfnd.format(n));
                    } else {
                        etMonto.setText(dfnd.format(n));
                    }
                    endlen = etMonto.getText().length();
                    int sel = (cp + (endlen - inilen));
                    if (sel > 0 && sel <= etMonto.getText().length()) {
                        etMonto.setSelection(sel);
                    } else {
                        // place cursor at the end?
                        etMonto.setSelection(etMonto.getText().length() - 1);
                    }
                } catch (NumberFormatException nfe) {
                    // do nothing?
                } catch (ParseException e) {
                    // do nothing?
                }

                etMonto.addTextChangedListener(this);
            }
        });

        /**Evento de click para obtener el costo por consulta*/
        tvCostoConsulta.setOnClickListener(tvCostoConsulta_OnClick);
        /**Evento de click para obtener el medio de pago*/
        tvMedioPago.setOnClickListener(tvMedioPago_OnClick);
        /**Evento de click para seleccionar si va a imprimir recibo o no*/
        tvImprimirRecibo.setOnClickListener(tvImprimirRecibo_OnClick);
        /**Evento de click para imprimir*/
        ibImprimir.setOnClickListener(ibImprimir_OnClick);
        /**Evento de click para tomar la fotografia*/
        ibFoto.setOnClickListener(ibFoto_OnClick);
        /**Evento de click para adjuntar un archivo de imagen*/
        ibGaleria.setOnClickListener(ibGaleria_OnClick);
        /**Evento de click para volver a capturar la evidencia*/
        ivEvidencia.setOnClickListener(ivEvidencia_OnClick);

        /**Funcion para inicializar los valores*/
        initComponents();
    }

    private View.OnClickListener tvCostoConsulta_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
            builder.setTitle(R.string.selected_option)
                    .setItems(_costo_consulta, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int position) {
                            tvCostoConsulta.setError(null);
                            tvCostoConsulta.setText(_costo_consulta[position].replace("$",""));

                            if (tipoCredito.equals("1")){
                                etMonto.setText(Miscellaneous.GetStr(tvCostoConsulta).replace("$", ""));
                            }
                            else{
                                if (!Miscellaneous.GetStr(etIntegrantes).isEmpty()){
                                    double total = Integer.parseInt(Miscellaneous.GetStr(etIntegrantes)) * Double.parseDouble(Miscellaneous.GetStr(tvCostoConsulta).replace("$", ""));
                                    etMonto.setText(Miscellaneous.moneyFormat(String.valueOf(total)).replace("$", ""));
                                }
                            }
                        }
                    });
            builder.create();
            builder.show();
        }
    };

    /**Evento de clic para seleccionar el medio de pago pero primero tendrá que llenar los campos anteriores*/
    private View.OnClickListener tvMedioPago_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Validator validator = new Validator();
            /**Valida si ya seleccionó el producto de individual o grupal*/
            if (rgTipo.getCheckedRadioButtonId() == R.id.rbInd || rgTipo.getCheckedRadioButtonId() == R.id.rbGpo)
            {
                /**Valida que los campos de nombre de cliente/grupo y curp esten llenos*/
                if (!validator.validate(etClienteGrupo, new String[]{validator.REQUIRED, validator.ONLY_TEXT}) &&
                    !validator.validate(etCurpCliente, new String[]{validator.REQUIRED, validator.CURP}))
                {
                    /**Valida que sea una curp valida*/
                    if (misc.CurpValidador(misc.GetStr(etCurpCliente)))
                    {
                        /**Valida el campo de aval/representante esten llenos*/
                        if(rgTipo.getCheckedRadioButtonId() == R.id.rbInd || (rgTipo.getCheckedRadioButtonId() == R.id.rbGpo && !validator.validate(etAvalRepresentate, new String[]{validator.REQUIRED, validator.ONLY_TEXT})))
                        {
                            /**Valida que el numero de integrantes sea mayor que 0*/
                            if (!validator.validate(etIntegrantes, new String[]{validator.REQUIRED, validator.YEARS}))
                            {
                                /**Muestra un dialog para poder seleccionar el medio de pago*/
                                AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
                                builder.setTitle(R.string.selected_option)
                                        .setItems(_medio_pago, new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int position) {
                                                tvMedioPago.setError(null);
                                                tvMedioPago.setText(_medio_pago[position]);
                                                byteEvidencia = null;
                                                /**Si selecciona medio de pago EFECTIVO tendra que imprimir recibos y tomar fotografia o adjunto*/
                                                if (misc.GetMedioPagoId(misc.GetStr(tvMedioPago)) == 6) {
                                                    llImprimirRecibo.setVisibility(View.VISIBLE);
                                                    ibGaleria.setEnabled(false);
                                                    ibGaleria.setBackground(ctx.getResources().getDrawable(R.drawable.btn_disable));
                                                    ibFoto.setVisibility(View.VISIBLE);
                                                    ibGaleria.setVisibility(View.VISIBLE);
                                                    llFotoGaleria.setVisibility(View.VISIBLE);
                                                    ivEvidencia.setVisibility(View.GONE);
                                                }
                                                else{
                                                    /**Medio de pago diferente a efectivo solo tomara una fotografia o adjunto*/
                                                    llImprimirRecibo.setVisibility(View.GONE);
                                                    llFolioRecibo.setVisibility(View.GONE);
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
                            else
                                etIntegrantes.setError("No ha seleccionado el total de integrantes que pagaron");
                        }
                    }
                    else
                        etCurpCliente.setError("No es una Curp Válida");
                }

            }
            else
            {
                Toast.makeText(ctx, "Falta seleccionar el tipo de credito", Toast.LENGTH_SHORT).show();
                tvTipo.setError("");
            }

        }
    };

    /**Evento de click para poder seleccionar si va a imprimir o va a capturar el folio de recibo manual*/
    private View.OnClickListener tvImprimirRecibo_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            /**Muestra dialogo para saber si va imprimir recibos*/
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

    /**Evento de click para imprimir los recibos de CC*/
    private View.OnClickListener ibImprimir_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            if (misc.GetStr(etCurpCliente).length() == 18 && misc.CurpValidador(misc.GetStr(etCurpCliente))) {
                int tipoPrestamo = (rgTipo.getCheckedRadioButtonId() == R.id.rbInd) ? 1 : 2;

                Intent i_formato_recibo = new Intent(ctx, FormatoRecibos.class);
                i_formato_recibo.putExtra("nombre", misc.RemoveTildesVocal(misc.GetStr(etClienteGrupo)));
                i_formato_recibo.putExtra("nombre_firma", misc.RemoveTildesVocal(misc.GetStr(etAvalRepresentate)));
                i_formato_recibo.putExtra("monto", misc.GetStr(etMonto));
                i_formato_recibo.putExtra("tipo", "CC");
                i_formato_recibo.putExtra("tipo_credito", tipoPrestamo);
                i_formato_recibo.putExtra("curp", misc.RemoveTildesVocal(misc.GetStr(etCurpCliente)));
                i_formato_recibo.putExtra("integrantes", Integer.parseInt(misc.GetStr(etIntegrantes)));
                i_formato_recibo.putExtra("res_impresion", 0);
                i_formato_recibo.putExtra("is_reeimpresion", false);
                startActivityForResult(i_formato_recibo, REQUEST_CODE_IMPRESORA);
            }
            else{
                etCurpCliente.setError("No es una Curp válida");
                Toast.makeText(ctx, "No es una Curp válida", Toast.LENGTH_SHORT).show();
            }
        }
    };

    /**Evento de click para tomar la fotografia para evidencia*/
    private View.OnClickListener ibFoto_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i = new Intent(ctx, CameraVertical.class);
            startActivityForResult(i, REQUEST_CODE_CAMARA_TICKET);
        }
    };

    /**Evento para adjuntar una imagen de galeria*/
    private View.OnClickListener ibGaleria_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            /**Valida si tiene los permisos de escritura y lectura de almacenamiento*/
            if (ContextCompat.checkSelfPermission(ctx, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                    || ContextCompat.checkSelfPermission(ctx,Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                requestPermissions(new String[] {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 123);
            } else {
                int compress = 10;/**Porcentaje de calidad de imagen de salida*/
                if( Build.MANUFACTURER.toUpperCase().equals("SAMSUNG"))/**Valida si es de un samsung para subir un poco la calidad*/
                    compress = 40;

                /**Libreria para recortar imagenes*/
                CropImage.activity()
                        .setAutoZoomEnabled(true)
                        .setMinCropWindowSize(3000,4000)
                        .setOutputCompressQuality(compress)
                        .start(RecuperacionCC.this);
            }

        }
    };

    /**Evento para saber si va a cambiar la fotografia/adjunto*/
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
                                            .start(RecuperacionCC.this);
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

    /**Funcion que establece si se va imprimir o va a capturar el folio de recibo manual*/
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

    /**Funcion para inicializar los campos en caso de que ya se hubiera guardado la gestion*/
    private void initComponents(){


        String sql = "";
        Cursor row = null;

        if (tipoCredito != null && curp != null) {
            /**Prepara la consulta para saber si existe una gestion ya guardada*/
            sql = "SELECT * FROM " + TBL_RECUPERACION_RECIBOS_CC + " WHERE tipo_credito = ? AND curp = ? AND _id = ?";
            row = db.rawQuery(sql, new String[]{tipoCredito, curp, String.valueOf(idRespuesta)});

            Log.e("ROW", "asd" + row.getCount());
            /**Si obtuvo alguna respuesta*/
            if (row.getCount() > 0) {
                row.moveToFirst();

                /**deshabilita el radiogroup*/
                for (int i = 0; i < rgTipo.getChildCount(); i++) {
                    ((RadioButton) rgTipo.getChildAt(i)).setEnabled(false);
                }

                Log.e("rowTipo", row.getString(1));
                /**Selecciona el tipo de credito*/
                if (tipoCredito.equals("1"))
                    rgTipo.check(R.id.rbInd);
                else
                    rgTipo.check(R.id.rbGpo);

                /**Llena los campos faltantes y los deshabilita*/

                tvCostoConsulta.setText(row.getString(15));
                tvCostoConsulta.setEnabled(false);
                etClienteGrupo.setText(row.getString(2));
                etClienteGrupo.setEnabled(false);
                etClienteGrupo.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
                etCurpCliente.setText(row.getString(3));
                etCurpCliente.setEnabled(false);
                etCurpCliente.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
                etAvalRepresentate.setText(row.getString(4));
                etAvalRepresentate.setEnabled(false);
                etAvalRepresentate.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
                etIntegrantes.setText(row.getString(5));
                etIntegrantes.setEnabled(false);
                etIntegrantes.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
                //idRespuesta = row.getLong(0);
                tvMedioPago.setText(row.getString(7));
                if (row.getString(7).equals("EFECTIVO") && row.getString(8).equals("SI")) {
                    llImprimirRecibo.setVisibility(View.VISIBLE);
                    ibGaleria.setEnabled(false);
                    ibGaleria.setBackground(ctx.getResources().getDrawable(R.drawable.btn_disable));
                    ibFoto.setVisibility(View.VISIBLE);
                    ibGaleria.setVisibility(View.VISIBLE);
                    llFotoGaleria.setVisibility(View.VISIBLE);
                    ivEvidencia.setVisibility(View.GONE);


                    String sqlImpresion = "SELECT * FROM " + TBL_RECIBOS_CC + " WHERE tipo_credito = ? AND curp = ?";
                    Cursor rowImpresion = db.rawQuery(sqlImpresion, new String[]{tipoCredito, curp});

                    Log.e("CountRecibos", rowImpresion.getCount()+"Total");
                    rowImpresion.moveToFirst();
                    tvImprimirRecibo.setText("SI");
                    SelectImprimirRecibos(0);
                    tvImprimirRecibo.setError(null);
                    llFolioRecibo.setVisibility(View.VISIBLE);
                    etFolioRecibo.setText("CC-" + session.getUser().get(0) + "-" + rowImpresion.getString(8));
                    tvMedioPago.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
                    tvMedioPago.setEnabled(false);
                    tvImprimirRecibo.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
                    tvImprimirRecibo.setEnabled(false);

                    if (!row.getString(10).isEmpty()) {
                        File evidenciaFile = new File(ROOT_PATH + "Evidencia/" + row.getString(10));
                        Uri uriEvidencia = Uri.fromFile(evidenciaFile);
                        Glide.with(ctx).load(uriEvidencia).centerCrop().into(ivEvidencia);
                        ibFoto.setVisibility(View.GONE);
                        ibGaleria.setVisibility(View.GONE);
                        ivEvidencia.setVisibility(View.VISIBLE);
                        byteEvidencia = misc.getBytesUri(ctx, uriEvidencia, 1);
                        tvFotoGaleria.setError(null);
                    }
                } else if (row.getString(7).equals("EFECTIVO") && row.getString(8).equals("NO")) {
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
                    etFolioRecibo.setText(row.getString(9));
                    etFolioRecibo.setEnabled(false);
                    etFolioRecibo.setError(null);
                    etFolioRecibo.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
                    tvMedioPago.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
                    tvMedioPago.setEnabled(false);
                    tvImprimirRecibo.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
                    tvImprimirRecibo.setEnabled(false);

                    if (!row.getString(10).isEmpty()) {
                        File evidenciaFile = new File(ROOT_PATH + "Evidencia/" + row.getString(10));
                        Uri uriEvidencia = Uri.fromFile(evidenciaFile);
                        Glide.with(ctx).load(uriEvidencia).centerCrop().into(ivEvidencia);
                        ibFoto.setVisibility(View.GONE);
                        ibGaleria.setVisibility(View.GONE);
                        ivEvidencia.setVisibility(View.VISIBLE);
                        byteEvidencia = misc.getBytesUri(ctx, uriEvidencia, 1);
                        tvFotoGaleria.setError(null);
                    }
                } else {
                    if (row.getString(7).isEmpty()) {
                        llImprimirRecibo.setVisibility(View.GONE);
                        ibGaleria.setEnabled(true);
                        ibGaleria.setBackground(ctx.getResources().getDrawable(R.drawable.round_corner_blue));
                        ibFoto.setVisibility(View.VISIBLE);
                        ibGaleria.setVisibility(View.VISIBLE);
                        llFotoGaleria.setVisibility(View.VISIBLE);
                        ivEvidencia.setVisibility(View.GONE);
                    } else {

                        File evidenciaFile = new File(ROOT_PATH + "Evidencia/" + row.getString(10));
                        Uri uriEvidencia = Uri.fromFile(evidenciaFile);
                        Glide.with(ctx).load(uriEvidencia).centerCrop().into(ivEvidencia);

                        byteEvidencia = misc.getBytesUri(ctx, uriEvidencia, 1);

                        tvFotoGaleria.setError(null);

                        ibFoto.setVisibility(View.GONE);
                        ibGaleria.setVisibility(View.GONE);
                        llFotoGaleria.setVisibility(View.VISIBLE);
                        ivEvidencia.setVisibility(View.VISIBLE);
                    }
                }

                if (row.getInt(14) == 1) {
                    tvMedioPago.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
                    tvMedioPago.setEnabled(false);
                    isEdit = false;
                    ibImprimir.setVisibility(View.GONE);
                }
                else if (row.getInt(14) == 2){
                    idRespuesta = 0L;
                    etAvalRepresentate.setText("");
                    etAvalRepresentate.setEnabled(true);
                    etAvalRepresentate.setBackground(getResources().getDrawable(R.drawable.et_rounded_edges));
                    if (rgTipo.getCheckedRadioButtonId() == R.id.rbInd)
                        etMonto.setText("17.50");
                    else {
                        etMonto.setText("0");

                        etCurpCliente.setText("");
                        etCurpCliente.setEnabled(true);
                        etCurpCliente.setBackground(getResources().getDrawable(R.drawable.et_rounded_edges));

                        etIntegrantes.setText("");
                        etIntegrantes.setEnabled(true);
                        etIntegrantes.setBackground(getResources().getDrawable(R.drawable.et_rounded_edges));

                    }
                    tvMedioPago.setEnabled(true);
                    tvMedioPago.setText("");
                    tvMedioPago.setBackground(getResources().getDrawable(R.drawable.et_rounded_edges));
                    tvImprimirRecibo.setText("");
                    tvImprimirRecibo.setBackground(getResources().getDrawable(R.drawable.et_rounded_edges));
                    tvImprimirRecibo.setEnabled(true);
                    etFolioRecibo.setText("");
                    ibImprimir.setVisibility(View.GONE);
                    etFolioRecibo.setBackground(getResources().getDrawable(R.drawable.et_rounded_edges));
                    llImprimirRecibo.setVisibility(View.GONE);
                    llFolioRecibo.setVisibility(View.GONE);
                    byteEvidencia = null;
                    ivEvidencia.setVisibility(View.GONE);
                    llFotoGaleria.setVisibility(View.GONE);


                }
            }
            invalidateOptionsMenu();
        }
    }

    /**Funcion para validar los campos requeridos y guardar la gestion*/
    private void Guardar(){
        boolean enviarInfo = false;
        Validator validator = new Validator();
        ValidatorTextView validatorTV = new ValidatorTextView();
        if (rgTipo.getCheckedRadioButtonId() == R.id.rbInd || rgTipo.getCheckedRadioButtonId() == R.id.rbGpo)
        {
            if (!validator.validate(etClienteGrupo, new String[]{validator.REQUIRED, validator.ONLY_TEXT}) &&
                !validator.validate(etCurpCliente, new String[]{validator.REQUIRED, validator.CURP}))
            {
                if (misc.CurpValidador(misc.GetStr(etCurpCliente))){
                    if (rgTipo.getCheckedRadioButtonId() == R.id.rbInd || (rgTipo.getCheckedRadioButtonId() == R.id.rbGpo && !validator.validate(etAvalRepresentate, new String[]{validator.REQUIRED, validator.ONLY_TEXT}))){
                        if (!validator.validate(etIntegrantes, new String[]{validator.REQUIRED, validator.YEARS}))
                        {
                            if (!validatorTV.validate(tvMedioPago, new String[]{validatorTV.REQUIRED}))
                            {
                                if (tvMedioPago.getText().toString().equals("EFECTIVO") &&
                                        !validatorTV.validate(tvImprimirRecibo, new String[]{validatorTV.REQUIRED})){
                                    if (!etFolioRecibo.getText().toString().trim().isEmpty()) {
                                        if (byteEvidencia != null) {
                                            if (idRespuesta > 0) {
                                                /**Cuando se realizo una impresion se creo antes el registro y ahorita solo va a actualizar ciertos valores*/
                                                ContentValues cv = new ContentValues();
                                                cv.put("tipo_imagen", tipoImagen);
                                                try {
                                                    cv.put("evidencia", misc.save(byteEvidencia, 2));
                                                } catch (IOException e) {
                                                    e.printStackTrace();
                                                }
                                                cv.put("fecha_termino", misc.ObtenerFecha(TIMESTAMP));
                                                cv.put("estatus", 1);
                                                db.update(TBL_RECUPERACION_RECIBOS_CC, cv, "_id = ?", new String[]{String.valueOf(idRespuesta)});
                                                enviarInfo = true;
                                            }
                                            else{
                                                /**Crea el registro de la gestion*/
                                                HashMap<Integer, String> params = new HashMap<>();
                                                params.put(0, (rgTipo.getCheckedRadioButtonId() == R.id.rbInd)?"1":"2");
                                                params.put(1, misc.RemoveTildesVocal(misc.GetStr(etClienteGrupo)));
                                                params.put(2, misc.RemoveTildesVocal(misc.GetStr(etCurpCliente)));
                                                params.put(3, misc.RemoveTildesVocal(misc.GetStr(etAvalRepresentate)));
                                                params.put(4, misc.GetStr(etIntegrantes));
                                                params.put(5, misc.GetStr(etMonto).replace(",",""));
                                                params.put(6, misc.GetStr(tvMedioPago));

                                                params.put(7, "NO");
                                                params.put(8, etFolioRecibo.getText().toString().trim());
                                                try {
                                                    params.put(9, misc.save(byteEvidencia, 2));
                                                } catch (IOException e) {
                                                    e.printStackTrace();
                                                }
                                                params.put(10, tipoImagen);
                                                params.put(11, misc.ObtenerFecha(TIMESTAMP));
                                                params.put(12, "");
                                                params.put(13, "1");
                                                params.put(14, misc.GetStr(tvCostoConsulta));

                                                idRespuesta = dBhelper.saveRecuperacionCC(db, params);

                                                enviarInfo = true;
                                            }
                                        }
                                        else
                                        {
                                            tvFotoGaleria.setError("");
                                            Toast.makeText(ctx, "Falta la imagen para guardar los datos", Toast.LENGTH_SHORT).show();
                                        }

                                    }
                                    else{
                                        Toast.makeText(ctx, "Falta el FOLIO el recibo", Toast.LENGTH_SHORT).show();
                                    }
                                }
                                else{
                                    /**valida que ya se tomó la evidencia (fotografia/galeria)*/
                                    if (byteEvidencia != null){
                                        /**Valida que no se haya guardado la gestion*/
                                        if (idRespuesta == 0) {
                                            /**Se crea el registro de la gestion*/
                                            HashMap<Integer, String> params = new HashMap<>();
                                            params.put(0, (rgTipo.getCheckedRadioButtonId() == R.id.rbInd)?"1":"2");
                                            params.put(1, misc.RemoveTildesVocal(misc.GetStr(etClienteGrupo)));
                                            params.put(2, misc.RemoveTildesVocal(misc.GetStr(etCurpCliente)));
                                            params.put(3, misc.RemoveTildesVocal(misc.GetStr(etAvalRepresentate)));
                                            params.put(4, misc.GetStr(etIntegrantes));
                                            params.put(5, misc.GetStr(etMonto).replace(",",""));
                                            params.put(6, misc.GetStr(tvMedioPago));
                                            params.put(7, "");
                                            params.put(8, "0");
                                            try {
                                                params.put(9, misc.save(byteEvidencia, 2));
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }
                                            params.put(10, tipoImagen);
                                            params.put(11, misc.ObtenerFecha(TIMESTAMP));
                                            params.put(12, "");
                                            params.put(13, "1");
                                            params.put(14, misc.GetStr(tvCostoConsulta));

                                            idRespuesta = dBhelper.saveRecuperacionCC(db, params);
                                            enviarInfo = true;

                                        }
                                        else{
                                            /**En caso de que ya hubiera una gestion registrada se actua estado parcial se actualiza*/
                                            ContentValues cv = new ContentValues();
                                            cv.put("tipo_imagen", tipoImagen);
                                            cv.put("fecha_termino", misc.ObtenerFecha(TIMESTAMP));
                                            cv.put("estatus", 1);
                                            db.update(TBL_RECUPERACION_RECIBOS_CC, cv, "_id = ?", new String[]{String.valueOf(idRespuesta)});
                                            enviarInfo = true;
                                        }
                                    }else{
                                        tvFotoGaleria.setError("");
                                        Toast.makeText(ctx, "Falta la imagen para guardar los datos", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        }
                        else
                            etIntegrantes.setError("No ha seleccionado el total de integrantes que pagaron");
                    }
                }
                else
                    etCurpCliente.setError("No corresponde a una CURP válida");
            }

        }
        else
        {
            Toast.makeText(ctx, "Falta seleccionar el tipo de credito", Toast.LENGTH_SHORT).show();
            tvTipo.setError("");
        }

        /**Valida si tiene que enviar la gestion realizada*/
        if (enviarInfo){
            Servicios_Sincronizado ss = new Servicios_Sincronizado();
            ss.SendRecibos(ctx, false);
            finish();
        }
    }

    /**Infla el menu para guardar la gestion o en caso de que este guardado se oculta el menu*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_save, menu);
        if (!isEdit)
            menu.getItem(0).setVisible(false);
        return true;
    }

    /**Acciones del menu*/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:/**Menu de retroceso del toolbar <- */
                finish();
                break;
            case R.id.save: /**Menu para guardar la gestion*/
                Guardar();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**Obtiene las respuestas de otras clases que se mandaron a llamar como la camara, impresiones, o galeria*/
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case REQUEST_CODE_IMPRESORA:/**Recibe la informacion de la impresion*/
                if (resultCode == Activity.RESULT_OK){
                    if (data != null){
                        Toast.makeText(ctx, data.getStringExtra(MESSAGE), Toast.LENGTH_SHORT).show();
                        /**valida si se realizaron impresiones original y/o copia*/
                        if(data.getIntExtra(RES_PRINT,0) > 0){

                            /**Busca si existe un registro de recuperacion con la informacion*/
                            String sql = "SELECT * FROM " + TBL_RECUPERACION_RECIBOS_CC + " WHERE tipo_credito = ? AND curp = ?";
                            Cursor row = db.rawQuery(sql, new String[]{(tipoCredito != null)?tipoCredito:"", (curp != null)?curp:""});

                            /**En caso de no existir el registro se crea en guardado parcial para que no pueda modificar los valores*/
                            if (row.getCount() == 0){
                                HashMap<Integer, String> params = new HashMap<>();
                                params.put(0, (rgTipo.getCheckedRadioButtonId() == R.id.rbInd)?"1":"2");
                                params.put(1, misc.RemoveTildesVocal(misc.GetStr(etClienteGrupo)));
                                params.put(2, misc.RemoveTildesVocal(misc.GetStr(etCurpCliente)));
                                params.put(3, misc.RemoveTildesVocal(misc.GetStr(etAvalRepresentate)));
                                params.put(4, misc.GetStr(etIntegrantes));
                                params.put(5, misc.GetStr(etMonto).replace(",",""));
                                params.put(6, misc.GetStr(tvMedioPago));
                                params.put(7, "SI");
                                params.put(8, String.valueOf(data.getIntExtra(FOLIO,0)));
                                params.put(9, "");
                                params.put(10, "");
                                params.put(11, "");
                                params.put(12, "");
                                params.put(13, "0");
                                params.put(14, misc.GetStr(tvCostoConsulta));

                                idRespuesta = dBhelper.saveRecuperacionCC(db, params);

                            }

                            /**deshabilita los campos anteriores a la impresion para que no pueda cambiar la informacion*/
                            tvCostoConsulta.setEnabled(false);
                            tvCostoConsulta.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
                            tvMedioPago.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
                            tvMedioPago.setEnabled(false);
                            tvImprimirRecibo.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
                            tvImprimirRecibo.setEnabled(false);
                            /**Coloca el folio que se imprimio*/
                            res_impresion = data.getIntExtra(RES_PRINT, 0);
                            folio_impreso = "CC-" + session.getUser().get(0) + "-" + data.getIntExtra(FOLIO,0);
                            etFolioRecibo.setText(folio_impreso);
                            tvImprimirRecibo.setError(null);
                            llFolioRecibo.setVisibility(View.VISIBLE);

                            for (int i = 0; i < rgTipo.getChildCount(); i++) {
                                ((RadioButton) rgTipo.getChildAt(i)).setEnabled(false);
                            }

                            etClienteGrupo.setEnabled(false);
                            etClienteGrupo.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
                            etCurpCliente.setEnabled(false);
                            etCurpCliente.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
                            etAvalRepresentate.setEnabled(false);
                            etAvalRepresentate.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
                            etIntegrantes.setEnabled(false);
                            etIntegrantes.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));

                        }
                    }
                }
                break;
            case REQUEST_CODE_CAMARA_TICKET:/**Recibe informacion de la fotografia capturada*/
                if (resultCode == Activity.RESULT_OK){
                    if (data != null){
                        tipoImagen = "FOTOGRAFIA";
                        ibFoto.setVisibility(View.GONE);
                        ibGaleria.setVisibility(View.GONE);
                        tvFotoGaleria.setError(null);
                        ivEvidencia.setVisibility(View.VISIBLE);
                        /**guarda la imagen obtenida en un array de bytes para posterior ser guardara en jpg*/
                        byteEvidencia = data.getByteArrayExtra(PICTURE);
                        /**Coloca la imagen en el contenedor de imageView*/
                        Glide.with(ctx).load(byteEvidencia).centerCrop().into(ivEvidencia);
                    }
                }
                break;
            case CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE:/**Recibe el archivo a adjuntar*/
                if (data != null){/**Valida que se esté recibiendo el archivo*/
                    try{
                        /**se establece el origen o procedencia de la imagen en este caso GELERIA*/
                        tipoImagen = "GALERIA";
                        CropImage.ActivityResult result = CropImage.getActivityResult(data);
                        imageUri = result.getUri();

                        /**Convierte la iamgen adjuntada a un array de byte*/
                        byteEvidencia = misc.getBytesUri(ctx, imageUri, 0);

                        ibFoto.setVisibility(View.GONE);
                        ibGaleria.setVisibility(View.GONE);
                        tvFotoGaleria.setError(null);
                        ivEvidencia.setVisibility(View.VISIBLE);

                        /**Al array de byte (imagen adjuntada) se le agrega la fecha y hora de cuando fue adjuntad para crear una nueva imagen con fecha y hora*/
                        View vCanvas = new CanvasCustom(ctx, new SimpleDateFormat(FORMAT_TIMESTAMP).format(Calendar.getInstance().getTime()));

                        Bitmap newBitMap = null;

                        Bitmap bitmap = BitmapFactory.decodeByteArray(byteEvidencia, 0, byteEvidencia.length);

                        Bitmap.Config config = bitmap.getConfig();

                        newBitMap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), config);
                        /**Aqui le coloca la fecha y hora a la imagen*/
                        Canvas canvas = new Canvas(newBitMap);
                        canvas.drawBitmap(bitmap, 0, 0, null);

                        vCanvas.draw(canvas);

                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        /**Se comprime la imagen para que no este tan pesada */
                        newBitMap.compress(Bitmap.CompressFormat.JPEG, 50, baos);

                        /**Se extraen la nueva imagen en array de byte para guardar posteriormente*/
                        byteEvidencia = baos.toByteArray();
                        /**Coloca la imagen en el contededor del imageView*/
                        Glide.with(ctx).load(baos.toByteArray()).centerCrop().into(ivEvidencia);

                    }catch (Exception e){
                        /**En caso de que haya adjuntado un archivo  con diferente formato al JPEG*/
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
}
