package com.sidert.sidertmovil.views.apoyogastosfunerarios;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AlertDialog;

import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.text.Editable;
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
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.activities.CameraVertical;
import com.sidert.sidertmovil.activities.FormatoRecibos;

import com.sidert.sidertmovil.models.apoyogastosfunerarios.Gestion;
import com.sidert.sidertmovil.models.apoyogastosfunerarios.GestionDao;
import com.sidert.sidertmovil.models.apoyogastosfunerarios.Prestamo;
import com.sidert.sidertmovil.models.apoyogastosfunerarios.PrestamoDao;
import com.sidert.sidertmovil.models.apoyogastosfunerarios.Recibo;
import com.sidert.sidertmovil.models.apoyogastosfunerarios.ReciboDao;
import com.sidert.sidertmovil.utils.CanvasCustom;
import com.sidert.sidertmovil.utils.Miscellaneous;
import com.sidert.sidertmovil.utils.Popups;
import com.sidert.sidertmovil.utils.Servicios_Sincronizado;
import com.sidert.sidertmovil.utils.SessionManager;
import com.sidert.sidertmovil.utils.Validator;
import com.sidert.sidertmovil.utils.ValidatorTextView;
import com.theartofdev.edmodo.cropper.CropImage;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.security.Guard;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import static com.sidert.sidertmovil.utils.Constants.FOLIO;
import static com.sidert.sidertmovil.utils.Constants.FORMAT_TIMESTAMP;
import static com.sidert.sidertmovil.utils.Constants.MESSAGE;
import static com.sidert.sidertmovil.utils.Constants.PICTURE;
import static com.sidert.sidertmovil.utils.Constants.REQUEST_CODE_CAMARA_TICKET;
import static com.sidert.sidertmovil.utils.Constants.REQUEST_CODE_IMPRESORA;
import static com.sidert.sidertmovil.utils.Constants.RES_PRINT;
import static com.sidert.sidertmovil.utils.Constants.ROOT_PATH;
import static com.sidert.sidertmovil.utils.Constants.TIMESTAMP;
import static com.sidert.sidertmovil.utils.Constants.question;

/**Clase solo para realizar la recuperacion de cobro en efectivo
 * solo para AGF (apoyo a gastos funerarios)*/
public class ReciboActivity extends AppCompatActivity {

    private Context ctx;

    GestionDao gestionDao;
    ReciboDao reciboDao;
    PrestamoDao prestamoDao;

    private SessionManager session;

    private LinearLayout llIntegrantes;

    private EditText etTipoCobro;
    private EditText etNombre;
    private EditText etFechDesembolso;
    private EditText etIntegrantes;
    private EditText etNumIntegrantesManual;
    private EditText etMeses;
    private EditText etMonto;

    private LinearLayout llImprimirRecibo;
    private LinearLayout llDuracionPrestamo;
    private LinearLayout llNumIntegrantesManual;
    private LinearLayout llQtNumIntegrantesManual;

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
    private TextView tvNumIntegrantesManual;

    public byte[] byteEvidencia;

    private String clienteId = "";

    Recibo recibo;
    Gestion gestion = null;
    Prestamo prestamo = null;

    private String folio_impreso = "";

    public String[] _medio_pago;
    public String[] _opcionesImpresionManual;
    public String[] _imprimir;

    private Uri imageUri;

    private String tipoImagen = "";

    private Validator validator = new Validator();
    private ValidatorTextView validatorTV = new ValidatorTextView();

    private boolean isEdit = true;

    private double costoUnitario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperacion_recibos);

        ctx = this;

        session = new SessionManager(ctx);

        Toolbar tbMain      = findViewById(R.id.tbMain);

        etTipoCobro         = findViewById(R.id.etTipoCobro);
        etNombre            = findViewById(R.id.etNombre);
        etFechDesembolso    = findViewById(R.id.etFechaDesembolso);
        etIntegrantes       = findViewById(R.id.etIntegrantes);
        etMeses             = findViewById(R.id.etMesesPrestamo);
        etMonto             = findViewById(R.id.etMonto);
        etNumIntegrantesManual = findViewById(R.id.etNumIntegrantesManual);

        llIntegrantes = findViewById(R.id.llIntegrantes);
        llNumIntegrantesManual = findViewById(R.id.llNumIntegrantesManual);
        llQtNumIntegrantesManual = findViewById(R.id.llQtNumIntegrantesManual);

        tvMedioPago            = findViewById(R.id.tvMedioPago);
        tvNumIntegrantesManual = findViewById(R.id.tvNumIntegrantesManual);

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

        /**Obtencion de datos que se enviaron entre clases*/
        recibo        = (Recibo) getIntent().getExtras().get("recibo");
        clienteId     = (recibo.getGrupoId().equals("1"))? getIntent().getStringExtra("cliente_id") : "";
        costoUnitario = getIntent().getDoubleExtra("costo_unitario", 0);

        setSupportActionBar(tbMain);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle(recibo.getNombre());

        etTipoCobro.setText("APOYO GASTOS FUNERARIOS");

        llDuracionPrestamo.setVisibility(View.VISIBLE);

        etNombre.setText(recibo.getNombre());
        etFechDesembolso.setText(getIntent().getStringExtra("fecha_entrega"));
        etIntegrantes.setText(String.valueOf(recibo.getNumIntegrantes()));
        etNumIntegrantesManual.setText("");
        etMeses.setText(String.valueOf(recibo.getPlazo()) + " MESES");
        etMonto.setText(Miscellaneous.moneyFormat(recibo.getMonto()));

        /**Se cargan arreglos como medios de pagos, y de impresion*/
        _medio_pago = getResources().getStringArray(R.array.medio_pago);
        //_imprimir = getResources().getStringArray(R.array.imprimir);
        _imprimir = new String[] {"SI"};
        _opcionesImpresionManual = new String[]{"SI", "NO"};

        /**Eventos de click para la recuperacion*/
        tvMedioPago.setOnClickListener(tvMedioPago_OnClick);
        tvNumIntegrantesManual.setOnClickListener(tvNumIntegrantesManual_OnClick);
        tvImprimirRecibo.setOnClickListener(tvImprimirRecibo_OnClick);

        ibImprimir.setOnClickListener(ibImprimir_OnClick);
        ibFoto.setOnClickListener(ibFoto_OnClick);
        ibGaleria.setOnClickListener(ibGaleria_OnClick);
        ivEvidencia.setOnClickListener(ivEvidencia_OnClick);

        etNumIntegrantesManual.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable e) {
                try {
                    recibo.setNumIntegrantes(Integer.parseInt(etNumIntegrantesManual.getText().toString()));
                }catch (Exception ex){
                    recibo.setNumIntegrantes(0);
                }

                recibo.setMonto(String.valueOf((recibo.getPlazo() * costoUnitario) * recibo.getNumIntegrantes()));
                etMonto.setText(Miscellaneous.moneyFormat(recibo.getMonto()));
            }
        });

        if(Integer.parseInt(recibo.getGrupoId()) > 1)
        {
            llIntegrantes.setVisibility(View.VISIBLE);
            llQtNumIntegrantesManual.setVisibility(View.VISIBLE);
        }
        else
        {
            tvNumIntegrantesManual.setText("SI");
        }

        initComponents();
    }

    /**Evento de click para seleccionar el medio de pago*/
    private View.OnClickListener tvMedioPago_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(!validatorTV.validate(tvNumIntegrantesManual, new String[]{validatorTV.REQUIRED})) {
                boolean continuar = true;

                if(etNumIntegrantesManual.getText().toString().trim().isEmpty() && tvNumIntegrantesManual.getText().equals("NO"))
                {
                    continuar = false;
                    etNumIntegrantesManual.setFocusable(true);
                    Toast.makeText(ctx, "Debe ingresar el número correcto de integrantes!", Toast.LENGTH_SHORT).show();
                }

                if(recibo.getNumIntegrantes() == 0 && tvNumIntegrantesManual.getText().equals("NO"))
                {
                    continuar = false;
                    etNumIntegrantesManual.setFocusable(true);
                    Toast.makeText(ctx, "El número de integrantes debe ser mayor a 0!", Toast.LENGTH_SHORT).show();
                }

                if(continuar)
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
                    builder.setTitle(R.string.selected_option)
                            .setItems(_medio_pago, (dialog, position) -> {
                                tvMedioPago.setError(null);
                                tvMedioPago.setText(_medio_pago[position]);
                                byteEvidencia = null;

                                /**En caso de seleccionar medio de pago EFECTIVO se habialitan opcion de impresion*/
                                if(Miscellaneous.GetMedioPagoId(Miscellaneous.GetStr(tvMedioPago)) == 6)
                                {
                                    llImprimirRecibo.setVisibility(View.VISIBLE);
                                    ibGaleria.setEnabled(false);
                                    ibGaleria.setBackground(ctx.getResources().getDrawable(R.drawable.btn_disable));
                                    ibFoto.setEnabled(false);
                                    ibFoto.setBackground(ctx.getResources().getDrawable(R.drawable.btn_disable));
                                    tvImprimirRecibo.setText("SI");
                                    SelectImprimirRecibos(0);
                                }
                                else{
                                    llImprimirRecibo.setVisibility(View.GONE);
                                    ibGaleria.setEnabled(true);
                                    ibGaleria.setBackground(ctx.getResources().getDrawable(R.drawable.round_corner_blue));
                                    ibGaleria.setVisibility(View.VISIBLE);
                                    ibFoto.setEnabled(true);
                                    ibFoto.setBackground(ctx.getResources().getDrawable(R.drawable.round_corner_blue));
                                    ibFoto.setVisibility(View.VISIBLE);
                                    llFotoGaleria.setVisibility(View.VISIBLE);
                                    ivEvidencia.setVisibility(View.GONE);
                                }
                            });
                    builder.create();
                    builder.show();
                }
            }
            else
            {
                tvNumIntegrantesManual.setFocusable(true);
                Toast.makeText(ctx, "Seleccione si el total de integrantes es correcto o no!", Toast.LENGTH_SHORT).show();
            }
        }
    };

    private View.OnClickListener tvNumIntegrantesManual_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
            builder.setTitle(R.string.selected_option)
                    .setItems(_opcionesImpresionManual, (dialog, position) -> {
                        tvNumIntegrantesManual.setError(null);
                        tvNumIntegrantesManual.setText(_opcionesImpresionManual[position]);
                        byteEvidencia = null;

                        if(tvNumIntegrantesManual.getText().equals("SI"))
                        {
                            llNumIntegrantesManual.setVisibility(View.GONE);
                            llIntegrantes.setVisibility(View.VISIBLE);
                            etNumIntegrantesManual.setText("");

                            recibo.setNumIntegrantes(Integer.parseInt(etIntegrantes.getText().toString()));
                            recibo.setMonto(String.valueOf((recibo.getPlazo() * costoUnitario) * recibo.getNumIntegrantes()));
                            etMonto.setText(Miscellaneous.moneyFormat(recibo.getMonto()));
                        }
                        else{
                            llNumIntegrantesManual.setVisibility(View.VISIBLE);
                        }
                    });
            builder.create();
            builder.show();
        }
    };

    /**Evento para seleccionar si va a imprimir o captura manual el folio de recibo manual*/
    private View.OnClickListener tvImprimirRecibo_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
            builder.setTitle(R.string.selected_option)
                    .setItems(new String[] {"SI"}, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int position) {
                            tvImprimirRecibo.setError(null);
                            tvImprimirRecibo.setText(_imprimir[position]);
                            /**Define la accion que va hacer dependiendo a lo seleccionado*/
                            SelectImprimirRecibos(position);
                        }
                    });
            builder.create();
            builder.show();
        }
    };

    /**Evento de click para realizar impresiones*/
    private View.OnClickListener ibImprimir_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            /**Se manda informacion para poder realizar la impresiones
             * como el monto a cobrar tipo de cobro y datos para realizar consultas*/
            Guardar(0);

            Intent i_formato_recibo = new Intent(ctx, FormatoRecibos.class);
            i_formato_recibo.putExtra("grupo_id", recibo.getGrupoId());
            i_formato_recibo.putExtra("num_solicitud", recibo.getNumSolicitud());
            i_formato_recibo.putExtra("nombre", recibo.getNombre());
            i_formato_recibo.putExtra("nombre_firma", recibo.getNombreFirma());
            i_formato_recibo.putExtra("monto", recibo.getMonto());
            i_formato_recibo.putExtra("tipo", recibo.getTipoRecibo());
            i_formato_recibo.putExtra("meses", recibo.getPlazo());
            i_formato_recibo.putExtra("res_impresion", 0);
            i_formato_recibo.putExtra("is_reeimpresion", false);
            startActivityForResult(i_formato_recibo, REQUEST_CODE_IMPRESORA);
        }
    };

    /**Evento para tomar una fotografia*/
    private View.OnClickListener ibFoto_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i = new Intent(ctx, CameraVertical.class);
            startActivityForResult(i, REQUEST_CODE_CAMARA_TICKET);
        }
    };

    /**Evento para adjuntar un archivo JPEG desde galeria con la libreria
     * del recortador de imagen*/
    private View.OnClickListener ibGaleria_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (ContextCompat.checkSelfPermission(ctx, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                    || ContextCompat.checkSelfPermission(ctx,Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                requestPermissions(new String[] {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 123);
            } else {
                int compress = 10;
                /**Valida si el dispositivo es un samsung comprimirá la imagen al 40%
                 * de lo contrario lo dejara al 10% de calidad para eso de los redmi*/
                if( Build.MANUFACTURER.toUpperCase().equals("SAMSUNG"))
                    compress = 40;
                CropImage.activity()
                        .setAutoZoomEnabled(true)
                        .setMinCropWindowSize(3000,4000)
                        .setOutputCompressQuality(compress)
                        .start(ReciboActivity.this);
            }
        }
    };

    /**Evento para cuando ya fue carpturada la evidencia y quieren cambiar de imagen siempre y cuando
     * no este guardada la recuperacion porque entonces solo le permitira visualizar la imagen*/
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
                                            .start(ReciboActivity.this);
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

    /**Funcion para estables acciones dependiendo a lo seccionado de impresiones
     * para saber si va a imprimir o solo va capturar folio manual del recibo*/
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

                    ibGaleria.setEnabled(false);
                    ibGaleria.setBackground(ctx.getResources().getDrawable(R.drawable.btn_disable));
                    ibFoto.setEnabled(false);
                    ibFoto.setBackground(ctx.getResources().getDrawable(R.drawable.btn_disable));
                }
                break;
            case 1: //No cuenta con bateria la impresora captura folio manual
                etFolioRecibo.setError(getResources().getString(R.string.campo_requerido));
                tvImprimirRecibo.setError(null);
                llFolioRecibo.setVisibility(View.VISIBLE);
                etFolioRecibo.setText("");
                etFolioRecibo.setEnabled(true);
                etFolioRecibo.setBackground(getResources().getDrawable(R.drawable.et_rounded_edges));
                etFolioRecibo.setHint(R.string.folio_sidert);
                etFolioRecibo.setInputType(InputType.TYPE_CLASS_NUMBER);
                ibImprimir.setVisibility(View.GONE);

                tvNumIntegrantesManual.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
                tvNumIntegrantesManual.setEnabled(false);
                etNumIntegrantesManual.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
                etNumIntegrantesManual.setEnabled(false);
                tvMedioPago.setEnabled(false);
                tvMedioPago.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
                ibGaleria.setVisibility(View.VISIBLE);
                ibFoto.setEnabled(true);
                ibFoto.setBackground(ctx.getResources().getDrawable(R.drawable.round_corner_blue));
                ibFoto.setVisibility(View.VISIBLE);
                llFotoGaleria.setVisibility(View.VISIBLE);
                ivEvidencia.setVisibility(View.GONE);

                break;
            default: // Sin seleccionar alguna opción o cualquier valor diferente
                tvImprimirRecibo.setError("");
                llFolioRecibo.setVisibility(View.GONE);
                ibImprimir.setVisibility(View.GONE);
                break;
        }
    }

    /**Funcion para obtener las respuestas de las otras clases como por ejemplo la de la camara
     * impresiones, o galeria*/
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case REQUEST_CODE_IMPRESORA:/**Respuesta a la peticion de la impresora*/
                if (resultCode == Activity.RESULT_OK){/**Valida que la clase de impresora mando una respuesta*/
                    if (data != null){
                        Toast.makeText(ctx, data.getStringExtra(MESSAGE), Toast.LENGTH_SHORT).show();

                        if(data.getIntExtra(RES_PRINT,0) == 1 || data.getIntExtra(RES_PRINT,0) == 2)
                        {
                            /**Se dehabilitan los campos anteriores a la impresion para que no haya modificaciones*/
                            tvMedioPago.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
                            tvMedioPago.setEnabled(false);
                            tvImprimirRecibo.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
                            tvImprimirRecibo.setEnabled(false);
                            folio_impreso = recibo.getTipoRecibo() + "-" + session.getUser().get(0) + "-" + data.getIntExtra(FOLIO,0);
                            etFolioRecibo.setText(folio_impreso);
                            tvImprimirRecibo.setError(null);
                            llFolioRecibo.setVisibility(View.VISIBLE);
                            tvNumIntegrantesManual.setEnabled(false);
                            tvNumIntegrantesManual.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
                            etNumIntegrantesManual.setEnabled(false);
                            etNumIntegrantesManual.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));

                            Guardar(0);

                            if(data.getIntExtra(FOLIO,0) != 0)
                            {
                                ibGaleria.setVisibility(View.VISIBLE);
                                ibFoto.setEnabled(true);
                                ibFoto.setBackground(ctx.getResources().getDrawable(R.drawable.round_corner_blue));
                                ibFoto.setVisibility(View.VISIBLE);
                                llFotoGaleria.setVisibility(View.VISIBLE);
                                ivEvidencia.setVisibility(View.GONE);
                            }
                        }
                    }
                }
                break;
            case REQUEST_CODE_CAMARA_TICKET:/**Respuesta a la peticion de la fotografia*/
                if (resultCode == Activity.RESULT_OK){ /**Valida que la clase de impresora mando una respuesta*/
                    if (data != null){
                        tipoImagen = "FOTOGRAFIA";/**Se define el origen de la evidencia*/
                        ibFoto.setVisibility(View.GONE);
                        ibGaleria.setVisibility(View.GONE);
                        tvFotoGaleria.setError(null);
                        ivEvidencia.setVisibility(View.VISIBLE);
                        /**Se almacena la informacion de la respuesta (Fotografia en array de byte) para guardar posteriormente*/
                        byteEvidencia = data.getByteArrayExtra(PICTURE);
                        /**Se coloca la fotografia en el contenedor del imageView*/
                        Glide.with(ctx).load(byteEvidencia).centerCrop().into(ivEvidencia);
                        Guardar(0);
                        if(byteEvidencia != null)
                        {
                            tvMedioPago.setEnabled(false);
                            tvMedioPago.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
                            tvNumIntegrantesManual.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
                            tvNumIntegrantesManual.setEnabled(false);
                            etNumIntegrantesManual.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
                            etNumIntegrantesManual.setEnabled(false);
                        }
                    }
                }
                break;
            case CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE:/**Respuesta a la peticion de la fotografia*/
                if (data != null){/**Valida que la respuesta venga con informacion*/
                    try{
                        tipoImagen = "GALERIA";/**Se define el origen de la evidencia*/
                        CropImage.ActivityResult result = CropImage.getActivityResult(data);
                        imageUri = result.getUri();/**Se obtiene la direccion donde esta alojado la iamgen*/

                        /**Convierte el archivo adjunto a un array de bytes*/
                        byteEvidencia = Miscellaneous.getBytesUri(ctx, imageUri, 0);

                        ibFoto.setVisibility(View.GONE);
                        ibGaleria.setVisibility(View.GONE);
                        tvFotoGaleria.setError(null);
                        ivEvidencia.setVisibility(View.VISIBLE);

                        /**Se le coloca la fecha y hora para saber cuando fue adjuntada*/
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

                        /**Se obtiene la nueva imagen con la fecha y hora*/
                        byteEvidencia = baos.toByteArray();

                        /**Se coloca en el contenedor del imageView*/
                        Glide.with(ctx).load(baos.toByteArray()).centerCrop().into(ivEvidencia);

                        Guardar(0);

                        if(byteEvidencia != null)
                        {
                            tvMedioPago.setEnabled(false);
                            tvMedioPago.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
                            tvNumIntegrantesManual.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
                            tvNumIntegrantesManual.setEnabled(false);
                            etNumIntegrantesManual.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
                            etNumIntegrantesManual.setEnabled(false);
                        }
                    }catch (Exception e){
                        /**En caso de adjuntar un archivo con diferente extension al JPEG mostrara un mensaje*/
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
        gestionDao = new GestionDao(ctx);
        reciboDao = new ReciboDao(ctx);
        prestamoDao = new PrestamoDao(ctx);

        if(recibo.getGrupoId().equals("1"))
        {
            gestion = gestionDao.findByClienteIdAndNumSolicitud(clienteId, recibo.getNumSolicitud());
            prestamo = prestamoDao.findByClienteIdAndNumSolicitud(clienteId, Integer.parseInt(recibo.getNumSolicitud()));
        }
        else
        {
            gestion = gestionDao.findByGrupoIdAndNumSolicitud(recibo.getGrupoId(), recibo.getNumSolicitud());
            prestamo = prestamoDao.findByGrupoIdAndNumSolicitud(Integer.parseInt(recibo.getGrupoId()), Integer.parseInt(recibo.getNumSolicitud()));
        }

        Log.e("AQUI RECIBO: ", new Gson().toJson(recibo));
        Log.e("AQUI GESTION", new Gson().toJson(gestion));
        Log.e("AQUI PRESTAMO", new Gson().toJson(prestamo));

        if(gestion != null)
        {
            tvMedioPago.setText(gestion.getMedioPago());

            if(gestion.getTotalIntegrantesManual() == null)
            {
                gestion.setTotalIntegrantesManual(0);
            }

            tvNumIntegrantesManual.setEnabled(false);

            if(gestion.getTotalIntegrantesManual() == 0)
            {
                tvNumIntegrantesManual.setText("SI");
            }
            else
            {
                tvNumIntegrantesManual.setText("NO");
            }

            etNumIntegrantesManual.setEnabled(false);

            if(tvNumIntegrantesManual.getText().equals("NO"))
            {
                llNumIntegrantesManual.setVisibility(View.VISIBLE);
                etNumIntegrantesManual.setText(String.valueOf(gestion.getTotalIntegrantesManual()));
            }

            if(gestion.getMedioPago().equals("EFECTIVO") && gestion.getImprimirRecibo().equals("SI"))
            {
                llImprimirRecibo.setVisibility(View.VISIBLE);
                ibGaleria.setEnabled(false);
                ibGaleria.setBackground(ctx.getResources().getDrawable(R.drawable.btn_disable));
                ibFoto.setVisibility(View.VISIBLE);
                ibGaleria.setVisibility(View.VISIBLE);
                llFotoGaleria.setVisibility(View.VISIBLE);
                ivEvidencia.setVisibility(View.GONE);

                tvImprimirRecibo.setText("SI");
                SelectImprimirRecibos(0);
                tvImprimirRecibo.setError(null);
                llFolioRecibo.setVisibility(View.VISIBLE);
                etFolioRecibo.setText(recibo.getTipoRecibo() + "-"+session.getUser().get(0)+"-"+ recibo.getFolio());
                tvNumIntegrantesManual.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
                tvNumIntegrantesManual.setEnabled(false);
                etNumIntegrantesManual.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
                etNumIntegrantesManual.setEnabled(false);
                tvMedioPago.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
                tvMedioPago.setEnabled(false);
                tvImprimirRecibo.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
                tvImprimirRecibo.setEnabled(false);

                if(tvNumIntegrantesManual.getText().equals("NO"))
                {
                    etNumIntegrantesManual.setText(String.valueOf(gestion.getTotalIntegrantesManual()));
                    etNumIntegrantesManual.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
                    etNumIntegrantesManual.setEnabled(false);
                }

                if(recibo.getFolio() != null && !recibo.getFolio().trim().equals(""))
                {
                    Log.e("AQUI", "ENTRE Y TENGO FOLIO");
                    ibFoto.setEnabled(true);
                    ibFoto.setBackground(ctx.getResources().getDrawable(R.drawable.round_corner_blue));
                    llFotoGaleria.setVisibility(View.VISIBLE);
                    ivEvidencia.setVisibility(View.GONE);
                }

                if(!gestion.getEvidencia().isEmpty())
                {
                    File evidenciaFile = new File(ROOT_PATH + "Evidencia/"+ gestion.getEvidencia());
                    Uri uriEvidencia = Uri.fromFile(evidenciaFile);
                    Glide.with(ctx).load(uriEvidencia).centerCrop().into(ivEvidencia);
                    ibFoto.setVisibility(View.GONE);
                    ibGaleria.setVisibility(View.GONE);
                    ivEvidencia.setVisibility(View.VISIBLE);
                    byteEvidencia = Miscellaneous.getBytesUri(ctx, uriEvidencia, 1);
                    tvFotoGaleria.setError(null);
                }
            }
            else if(gestion.getMedioPago().equals("EFECTIVO") && gestion.getImprimirRecibo().equals("NO"))
            {
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
                etFolioRecibo.setText(gestion.getFolioManual());
                etFolioRecibo.setEnabled(false);
                etFolioRecibo.setError(null);
                etFolioRecibo.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
                tvMedioPago.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
                tvMedioPago.setEnabled(false);
                tvNumIntegrantesManual.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
                tvNumIntegrantesManual.setEnabled(false);
                etNumIntegrantesManual.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
                etNumIntegrantesManual.setEnabled(false);
                tvImprimirRecibo.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
                tvImprimirRecibo.setEnabled(false);

                if (!gestion.getEvidencia().isEmpty()){
                    File evidenciaFile = new File(ROOT_PATH + "Evidencia/"+ gestion.getEvidencia());
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
                if (gestion.getEvidencia().isEmpty()) {
                    llImprimirRecibo.setVisibility(View.GONE);
                    ibGaleria.setEnabled(true);
                    ibGaleria.setBackground(ctx.getResources().getDrawable(R.drawable.round_corner_blue));
                    ibFoto.setVisibility(View.VISIBLE);
                    ibGaleria.setVisibility(View.VISIBLE);
                    llFotoGaleria.setVisibility(View.VISIBLE);
                    ivEvidencia.setVisibility(View.GONE);
                }
                else{
                    File evidenciaFile = new File(ROOT_PATH + "Evidencia/"+ gestion.getEvidencia());
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

            if(!gestion.getEvidencia().isEmpty()) {
              tvMedioPago.setEnabled(false);
              tvMedioPago.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
              tvNumIntegrantesManual.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
              tvNumIntegrantesManual.setEnabled(false);
              etNumIntegrantesManual.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
              etNumIntegrantesManual.setEnabled(false);
            }

            if(gestion.getEstatus() == 1 || gestion.getEstatus() == 2)
            {
                tvMedioPago.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
                tvMedioPago.setEnabled(false);
                tvNumIntegrantesManual.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
                tvNumIntegrantesManual.setEnabled(false);
                etNumIntegrantesManual.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
                etNumIntegrantesManual.setEnabled(false);
                ibImprimir.setVisibility(View.GONE);
            }

            if(gestion.getEstatus() == 2)
            {
                isEdit = false;
            }
        }

        if(tvMedioPago.getText().toString().trim().length() == 0)
        {
            ibFoto.setVisibility(View.GONE);
            ibGaleria.setVisibility(View.GONE);
        }

        invalidateOptionsMenu();
    }

    private void Guardar (Integer estatus){
        boolean guardar = true;
        List<Recibo> recibos = null;

        if(validatorTV.validate(tvMedioPago, new String[]{validatorTV.REQUIRED})) {
            Toast.makeText(ctx, "Seleccione el medio de pago!", Toast.LENGTH_SHORT).show();
            guardar = false;
        }

        //VALIDACIONES DE GUARDADO PARA TODOS LOS MEDIOS DE PAGO MENOS EFECTIVO
        if(guardar && Miscellaneous.GetMedioPagoId(Miscellaneous.GetStr(tvMedioPago)) != 6)
        {
            if(byteEvidencia == null)
            {
                guardar = false;
                Toast.makeText(ctx, "Debe tomar una fotografía del pago o adjuntar la imagen desde la galería!", Toast.LENGTH_SHORT).show();
            }
        }

        //VALIDACIONES DE GUARDADO PARA EFECTIVO CON IMPRESION
        if(guardar && tvMedioPago.getText().toString().equals("EFECTIVO")) {
            if(validatorTV.validate(tvImprimirRecibo, new String[]{validatorTV.REQUIRED}))
            {
                Toast.makeText(ctx, "Seleccione si va a imprimir recibos!", Toast.LENGTH_SHORT).show();
                guardar = false;
            }
            else
            {
                if(tvImprimirRecibo.getText().toString().equals("SI"))
                {
                    /*if(etFolioRecibo.getText().toString().trim().length() <= 0)
                    {
                        Toast.makeText(ctx, "No ha realizado la impresión del recibo!", Toast.LENGTH_SHORT).show();
                        guardar = false;
                    }*/
                }
            }
        }

        //VALIDACIONES DE GUARDADO PARA EFECTIVO SIN IMPRESION
        if(guardar && tvMedioPago.getText().toString().equals("EFECTIVO")){
            if(validatorTV.validate(tvImprimirRecibo, new String[]{validatorTV.REQUIRED}))
            {
                Toast.makeText(ctx, "Seleccione si va a imprimir recibos!", Toast.LENGTH_SHORT).show();
                guardar = false;
            }
            else
            {
                if(tvImprimirRecibo.getText().toString().equals("NO CUENTA CON BATERIA"))
                {
                    if(etFolioRecibo.getText().toString().trim().isEmpty())
                    {
                        guardar = false;
                        Toast.makeText(ctx, "Falta el folio del recibo!", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        if(byteEvidencia == null)
                        {
                            guardar = false;
                            Toast.makeText(ctx, "Debe tomar una fotografía del pago o adjuntar la imagen desde la galería!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        }

        if(guardar){
            if(gestion == null){
                gestion = new Gestion();
                gestion.setGrupoId(recibo.getGrupoId());
                gestion.setNumSolicitud(recibo.getNumSolicitud());
                gestion.setFechaEnvio("");
                gestion.setTipo(recibo.getTipoRecibo());
                gestion.setNombre(recibo.getNombre());
                gestion.setClienteId(clienteId);
            }

            gestion.setMedioPago(tvMedioPago.getText().toString());
            gestion.setMonto(recibo.getMonto());

            if(tvMedioPago.getText().toString().equals("EFECTIVO") && tvImprimirRecibo.getText().toString().equals("SI"))
            {
                gestion.setImprimirRecibo("SI");
                gestion.setFolioManual("");
                tvMedioPago.setEnabled(false);
                tvMedioPago.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
                tvNumIntegrantesManual.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
                tvNumIntegrantesManual.setEnabled(false);
                etNumIntegrantesManual.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
                etNumIntegrantesManual.setEnabled(false);
                tvImprimirRecibo.setEnabled(false);
                tvImprimirRecibo.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
            }
            else{
                gestion.setFolioManual(etFolioRecibo.getText().toString().trim());
                gestion.setImprimirRecibo("NO");
            }

            if(recibo.getGrupoId().equals("1"))
            {
                gestion.setTotalIntegrantes(1);
                gestion.setTotalIntegrantesManual(0);
            }
            else
            {
                if(tvNumIntegrantesManual.getText().equals("SI"))
                {
                    gestion.setTotalIntegrantes(recibo.getNumIntegrantes());
                    gestion.setTotalIntegrantesManual(0);
                }
                else
                {
                    if(prestamo != null)
                    {
                        gestion.setTotalIntegrantes(prestamo.getNumIntegrantes());
                    }

                    gestion.setTotalIntegrantesManual(recibo.getNumIntegrantes());
                }
            }

            if(byteEvidencia != null)
            {
                try {
                    gestion.setEvidencia(Miscellaneous.save(byteEvidencia, 2));
                    tvMedioPago.setEnabled(false);
                    tvMedioPago.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
                    tvNumIntegrantesManual.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
                    tvNumIntegrantesManual.setEnabled(false);
                    etNumIntegrantesManual.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
                    etNumIntegrantesManual.setEnabled(false);
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else
            {
                gestion.setEvidencia("");
            }

            gestion.setTipoImagen(tipoImagen);

            if(gestion.getGrupoId().equals("1"))
            {
                recibos = reciboDao.findAllByNombreAndNumSolicitud(gestion.getNombre(), gestion.getNumSolicitud());
            }
            else
            {
                recibos = reciboDao.findAllByGrupoIdAndNumSolicitud(gestion.getGrupoId(), gestion.getNumSolicitud());
            }

            if(
                gestion.getEvidencia() != null
                && !gestion.getEvidencia().equals("") && estatus > 0
                && (
                    recibos.size() > 1
                    || !tvMedioPago.getText().toString().equals("EFECTIVO")
                )
            )
            {
                gestion.setEstatus(1);
                gestion.setFechaTermino(Miscellaneous.ObtenerFecha(TIMESTAMP));
            }
            else
            {
                gestion.setEstatus(0);
                gestion.setFechaTermino("");
            }

            if(gestion.getId() == null)
            {
                gestion.setId(Integer.parseInt(String.valueOf(gestionDao.store(gestion))));
            }
            else
            {
                gestionDao.update(gestion.getId(), gestion);
            }

            if((gestion.getEvidencia() != null && !gestion.getEvidencia().equals("")) || (gestion.getFolioManual() != null && !gestion.getFolioManual().equals("")) || (recibo.getFolio() != null && !recibo.getFolio().trim().equals(""))){
                if(gestion.getEstatus() == 1)
                {
                    Servicios_Sincronizado ss = new Servicios_Sincronizado();
                    ss.SendRecibos(ctx, false);
                    finish();
                }
                else{
                    Servicios_Sincronizado ss = new Servicios_Sincronizado();
                    ss.SendRecibos(ctx, false);
                }
            }
        }
    }

    private void EnableInptus()
    {
        //SI SE HA GUARDADO PREVIAMENTE

        //SI ES NUEVO
    }

    /**se carga el menu para guardar la gestion pero se valida también si se ocultaria porque ya fue guardado*/
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
                Guardar(1);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
