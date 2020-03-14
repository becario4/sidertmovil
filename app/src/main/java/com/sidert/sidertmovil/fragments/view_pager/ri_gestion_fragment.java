package com.sidert.sidertmovil.fragments.view_pager;


import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.location.LocationManager;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
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
import com.sidert.sidertmovil.activities.ArqueoDeCaja;
import com.sidert.sidertmovil.activities.CameraVertical;
import com.sidert.sidertmovil.activities.RecuperacionIndividual;
import com.sidert.sidertmovil.activities.PrintSeewoo;
import com.sidert.sidertmovil.activities.CapturarFirma;
import com.sidert.sidertmovil.database.DBhelper;
import com.sidert.sidertmovil.fragments.dialogs.dialog_date_picker;
import com.sidert.sidertmovil.models.OrderModel;
import com.sidert.sidertmovil.utils.Constants;
import com.sidert.sidertmovil.utils.Miscellaneous;
import com.sidert.sidertmovil.utils.MyCurrentListener;
import com.sidert.sidertmovil.utils.NameFragments;
import com.sidert.sidertmovil.utils.Popups;
import com.sidert.sidertmovil.utils.Validator;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import static com.sidert.sidertmovil.utils.Constants.ACTUALIZAR_TELEFONO;
import static com.sidert.sidertmovil.utils.Constants.COMENTARIO;
import static com.sidert.sidertmovil.utils.Constants.CONTACTO;
import static com.sidert.sidertmovil.utils.Constants.DATE;
import static com.sidert.sidertmovil.utils.Constants.DATE_CURRENT;
import static com.sidert.sidertmovil.utils.Constants.DAY_CURRENT;
import static com.sidert.sidertmovil.utils.Constants.ENVIROMENT;
import static com.sidert.sidertmovil.utils.Constants.EVIDENCIA;
import static com.sidert.sidertmovil.utils.Constants.FACHADA;
import static com.sidert.sidertmovil.utils.Constants.FECHAS_POST;
import static com.sidert.sidertmovil.utils.Constants.FECHA_DEFUNCION;
import static com.sidert.sidertmovil.utils.Constants.FECHA_DEPOSITO;
import static com.sidert.sidertmovil.utils.Constants.FICHAS;
import static com.sidert.sidertmovil.utils.Constants.FICHAS_T;
import static com.sidert.sidertmovil.utils.Constants.FIRMA;
import static com.sidert.sidertmovil.utils.Constants.FIRMA_IMAGE;
import static com.sidert.sidertmovil.utils.Constants.FOLIO;
import static com.sidert.sidertmovil.utils.Constants.FOLIO_TICKET;
import static com.sidert.sidertmovil.utils.Constants.FORMAT_DATE_GNRAL;
import static com.sidert.sidertmovil.utils.Constants.GERENTE;
import static com.sidert.sidertmovil.utils.Constants.IDENTIFIER;
import static com.sidert.sidertmovil.utils.Constants.IMPRESORA;
import static com.sidert.sidertmovil.utils.Constants.LATITUD;
import static com.sidert.sidertmovil.utils.Constants.LONGITUD;
import static com.sidert.sidertmovil.utils.Constants.MEDIO_PAGO;
import static com.sidert.sidertmovil.utils.Constants.MESSAGE;
import static com.sidert.sidertmovil.utils.Constants.MONTH_CURRENT;
import static com.sidert.sidertmovil.utils.Constants.MOTIVO_ACLARACION;
import static com.sidert.sidertmovil.utils.Constants.MOTIVO_NO_PAGO;
import static com.sidert.sidertmovil.utils.Constants.NOMBRE_GRUPO;
import static com.sidert.sidertmovil.utils.Constants.NUEVO_TELEFONO;
import static com.sidert.sidertmovil.utils.Constants.ORDER_ID;
import static com.sidert.sidertmovil.utils.Constants.PAGO_REALIZADO;
import static com.sidert.sidertmovil.utils.Constants.PAGO_REQUERIDO;
import static com.sidert.sidertmovil.utils.Constants.PICTURE;
import static com.sidert.sidertmovil.utils.Constants.POS_MEDIO_PAGO;
import static com.sidert.sidertmovil.utils.Constants.POS_MOTIVO_NO_PAGO;
import static com.sidert.sidertmovil.utils.Constants.REQUEST_CODE_ARQUEO_CAJA;
import static com.sidert.sidertmovil.utils.Constants.REQUEST_CODE_CAMARA_FACHADA;
import static com.sidert.sidertmovil.utils.Constants.REQUEST_CODE_CAMARA_TICKET;
import static com.sidert.sidertmovil.utils.Constants.REQUEST_CODE_GALERIA;
import static com.sidert.sidertmovil.utils.Constants.REQUEST_CODE_IMPRESORA;
import static com.sidert.sidertmovil.utils.Constants.RESULTADO_PAGO;
import static com.sidert.sidertmovil.utils.Constants.RES_PRINT;
import static com.sidert.sidertmovil.utils.Constants.ROOT_PATH;
import static com.sidert.sidertmovil.utils.Constants.SAVE;
import static com.sidert.sidertmovil.utils.Constants.TIPO;
import static com.sidert.sidertmovil.utils.Constants.TIPO_IMAGEN;
import static com.sidert.sidertmovil.utils.Constants.YEAR_CURRENT;
import static com.sidert.sidertmovil.utils.Constants.camara;
import static com.sidert.sidertmovil.utils.Constants.firma;
import static com.sidert.sidertmovil.utils.Constants.question;

public class ri_gestion_fragment extends Fragment {

    private Context ctx;

    private TextView tvExternalID;
    private TextView tvContactoCliente;
    private TextView tvActualizarTelefono;
    private TextView tvResultadoGestion;
    private TextView tvMedioPago;
    private TextView tvPagaraRequerido;
    private TextView tvArqueoCaja;
    private TextView tvmapa;
    private TextView tvImprimirRecibo;
    private TextView tvFotoGaleria;
    private TextView tvFirma;
    private TextView tvMotivoAclaracion;
    private TextView tvEstaGerente;
    private TextView tvFotoFachada;
    //private TextView tvMotivoNoPago;
    public TextView tvMotivoNoPagoxxx;
    public TextView tvFechaDefuncion;

    public EditText etActualizarTelefono;
    public EditText etComentario;
    public EditText etMotivoAclaracion;
    public EditText etMontoPagoRequerido;
    //public EditText etFechaDefuncion;
    public EditText etPagoRealizado;
    public EditText etFechaDeposito;
    public EditText etFolioRecibo;

    //public Spinner spMotivoNoPago;
    public Spinner spMedioPago;

    public RadioGroup rgContactoCliente;
    public RadioGroup rgActualizarTelefono;
    public RadioGroup rgResultadoPago;
    public RadioGroup rgPagaraRequerido;
    public RadioGroup rgRecibos;
    public RadioGroup rgEstaGerente;

    public RadioButton rbSiRequerido;
    public RadioButton rbNoRequerido;
    public RadioButton rbArqueoCaja;
    public RadioButton rbSiGerente;
    public RadioButton rbNoGerente;

    private ImageButton ibMap;
    private ImageButton ibFoto;
    private ImageButton ibFotoFachada;
    private ImageButton ibGaleria;
    private ImageButton ibFirma;
    private ImageButton ibArqueoCaja;
    private ImageButton ibImprimir;

    private ImageView ivFotoFachada;
    private ImageView ivFirma;
    private ImageView ivEvidencia;

    private LinearLayout llActualizarTelefono;
    private LinearLayout llComentario;
    private LinearLayout llFotoFachada;
    private LinearLayout llDatosDefuncion;
    private LinearLayout llMotivoAclaracion;
    private LinearLayout llMotivoNoPago;
    private LinearLayout llMontoPagoRequerido;
    private LinearLayout llMedioPago;
    private LinearLayout llResultadoGestion;
    private LinearLayout llArqueoCaja;
    private LinearLayout llPagaraRequerido;
    private LinearLayout llMontoPagoRealizado;
    private LinearLayout llFechaDeposito;
    private LinearLayout llImprimirRecibo;
    private LinearLayout llFolioRecibo;
    private LinearLayout llFotoGaleria;
    private LinearLayout llEstaGerente;
    private LinearLayout llFirma;

    private String[] _motivo_aclaracion;
    private String[] _motivo_no_pago;

    private Calendar myCalendar;
    private SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_DATE_GNRAL, Locale.US);
    private Date minDate;

    private final int REQUEST_CODE_SIGNATURE = 456;

    RecuperacionIndividual parent;
    private Validator validator;

    private boolean flagUbicacion = false;

    private LocationManager locationManager;
    private MyCurrentListener locationListener;

    private ProgressBar pbLoading;

    private MapView mapView;
    private GoogleMap mMap;
    private Marker mMarker;
    public LatLng latLngGestion;

    private Uri imageUri;

    public byte[] byteEvidencia;
    public byte[] byteFirma;

    private String folio_impreso = "";

    private boolean flag_menu = false;

    private JSONObject jsonResponse;

    private DBhelper dBhelper;
    private SQLiteDatabase db;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view       = inflater.inflate(R.layout.fragment_ri_gestion, container, false);
        ctx             = getContext();
        jsonResponse         = new JSONObject();
        dBhelper        = new DBhelper(ctx);
        db              = dBhelper.getWritableDatabase();

        parent                = (RecuperacionIndividual) getActivity();
        assert parent != null;
        parent.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        tvExternalID            = view.findViewById(R.id.tvExternalID);
        tvContactoCliente       = view.findViewById(R.id.tvContactoCliente);
        tvActualizarTelefono    = view.findViewById(R.id.tvActualizarTelefono);
        tvResultadoGestion      = view.findViewById(R.id.tvResultadoGestion);
        tvMedioPago             = view.findViewById(R.id.tvMedioPago);
        tvPagaraRequerido       = view.findViewById(R.id.tvPagaraRequerido);
        tvArqueoCaja            = view.findViewById(R.id.tvArqueoCaja);
        tvMotivoAclaracion      = view.findViewById(R.id.tvMotivoAclaracion);
        tvEstaGerente           = view.findViewById(R.id.tvEstaGerente);
        tvFotoFachada           = view.findViewById(R.id.tvFotoFachada);
        //tvMotivoNoPago          = view.findViewById(R.id.tvMotivoNoPago);
        tvMotivoNoPagoxxx       = view.findViewById(R.id.tvMotivoNoPagoxxx);
        tvmapa                  = view.findViewById(R.id.tvMapa);
        tvImprimirRecibo        = view.findViewById(R.id.tvImprimirRecibo);
        tvFotoGaleria           = view.findViewById(R.id.tvFotoGaleria);
        tvFirma                 = view.findViewById(R.id.tvFirma);

        etActualizarTelefono    = view.findViewById(R.id.etActualizarTelefono);
        etComentario            = view.findViewById(R.id.etComentario);
        etMotivoAclaracion      = view.findViewById(R.id.etMotivoAclaracion);
        etMontoPagoRequerido    = view.findViewById(R.id.etMontoPagoRequerido);
        tvFechaDefuncion        = view.findViewById(R.id.tvFechaDefuncion);
        etPagoRealizado         = view.findViewById(R.id.etPagoRealizado);
        etFechaDeposito         = view.findViewById(R.id.etFechaDeposito);
        etFolioRecibo           = view.findViewById(R.id.etFolioRecibo);

        spMedioPago             = view.findViewById(R.id.spMedioPago);
        //spMotivoNoPago          = view.findViewById(R.id.spMotivoNoPago);

        rgContactoCliente       = view.findViewById(R.id.rgContactoCliente);
        rgActualizarTelefono    = view.findViewById(R.id.rgActualizarTelefono);
        rgResultadoPago         = view.findViewById(R.id.rgResultadoPago);
        rgPagaraRequerido       = view.findViewById(R.id.rgPagaraRequerido);
        rgRecibos               = view.findViewById(R.id.rgRecibos);
        rgEstaGerente           = view.findViewById(R.id.rgEstaGerente);

        rbSiRequerido   = view.findViewById(R.id.rbSiRequerido);
        rbNoRequerido   = view.findViewById(R.id.rbNoRequerido);
        rbArqueoCaja    = view.findViewById(R.id.rbArqueoCaja);
        rbSiGerente     = view.findViewById(R.id.rbSiGerente);
        rbNoGerente     = view.findViewById(R.id.rbNoGerente);

        ibMap           = view.findViewById(R.id.ibMap);
        ibFotoFachada   = view.findViewById(R.id.ibFotoFachada);
        ibFoto          = view.findViewById(R.id.ibFoto);
        ibGaleria       = view.findViewById(R.id.ibGaleria);
        ibFirma         = view.findViewById(R.id.ibFirma);
        ibArqueoCaja    = view.findViewById(R.id.ibArqueoCaja);
        ibImprimir      = view.findViewById(R.id.ibImprimir);

        ivFotoFachada   = view.findViewById(R.id.ivFotoFachada);
        ivFirma         = view.findViewById(R.id.ivFirma);
        ivEvidencia     = view.findViewById(R.id.ivEvidencia);

        pbLoading       = view.findViewById(R.id.pbLoading);

        mapView         = view.findViewById(R.id.mapGestion);

        llComentario            = view.findViewById(R.id.llComentario);
        llActualizarTelefono    = view.findViewById(R.id.llActualizarTelefono);
        llResultadoGestion      = view.findViewById(R.id.llResultadoGestion);
        llFotoFachada           = view.findViewById(R.id.llFotoFachada);
        llDatosDefuncion        = view.findViewById(R.id.llDatosDefuncion);
        llMotivoAclaracion      = view.findViewById(R.id.llMotivoAclaracion);
        llMotivoNoPago          = view.findViewById(R.id.llMotivoNoPago);
        llMontoPagoRequerido    = view.findViewById(R.id.llMontoPagoRequerido);
        llMedioPago             = view.findViewById(R.id.llMedioPago);
        llArqueoCaja            = view.findViewById(R.id.llArqueoCaja);
        llPagaraRequerido       = view.findViewById(R.id.llPagaraRequerido);
        llMontoPagoRealizado    = view.findViewById(R.id.llMontoPagoRealizado);
        llImprimirRecibo        = view.findViewById(R.id.llImprimirRecibo);
        llFolioRecibo           = view.findViewById(R.id.llFolioRecibo);
        llFechaDeposito         = view.findViewById(R.id.llFechaDeposito);
        llFotoGaleria           = view.findViewById(R.id.llFotoGaleria);
        llEstaGerente           = view.findViewById(R.id.llEstaGerente);
        llFirma                 = view.findViewById(R.id.llFirma);

        myCalendar              = Calendar.getInstance();

        spMedioPago.setPrompt(getResources().getString(R.string.payment_method));
        //spMotivoNoPago.setPrompt(getResources().getString(R.string.reason_no_payment));

        mapView.onCreate(savedInstanceState);
        locationManager = (LocationManager) parent.getSystemService(Context.LOCATION_SERVICE);
        validator = new Validator();

        for (int i = 0; i < llEstaGerente.getChildCount(); i++) {
            View child = llEstaGerente.getChildAt(i);
            child.setEnabled(false);
        }

        try {
            minDate = sdf.parse("2019-08-01");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        _motivo_aclaracion = getResources().getStringArray(R.array.outdated_information);
        _motivo_no_pago = getResources().getStringArray(R.array.reason_no_pay);

        // TextView Click
        tvMotivoNoPagoxxx.setOnClickListener(tvMotivoNoPagoxxx_OnClick);

        // EditText Click
        etMotivoAclaracion.setOnClickListener(etMotivoAclaracion_OnClick);
        tvFechaDefuncion.setOnClickListener(tvFechaDefuncion_OnClick);
        etFechaDeposito.setOnClickListener(etFechaDeposito_OnClick);

        // ImageButton Click
        ibMap.setOnClickListener(ibMap_OnClick);
        ibFoto.setOnClickListener(ibFoto_OnClick);
        ibGaleria.setOnClickListener(ibGaleria_OnClick);
        ibFotoFachada.setOnClickListener(ibFotoFachada_OnClick);
        ibFirma.setOnClickListener(ibFirma_OnClick);
        ibImprimir.setOnClickListener(ibImprimir_OnClick);
        ibArqueoCaja.setOnClickListener(ibArqueoCaja_OnClick);

        etComentario.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() > 0){
                    try {
                        jsonResponse.put(COMENTARIO, s.toString().trim().toUpperCase());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else{
                    try {
                        jsonResponse.put(COMENTARIO, "");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                UpdateTemporal(jsonResponse);
            }
        });

        // RadioGroup Click
        rgContactoCliente.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                Log.e("----------","Contacto Cliente");
                tvContactoCliente.setError(null);
                tvActualizarTelefono.setError(null);
                tvMotivoAclaracion.setError(null);
                etMotivoAclaracion.setError(null);
                tvFotoFachada.setError(null);
                int pos = 0;
                switch (checkedId) {
                    case R.id.rbSiContacto:
                        pos = 1;
                        break;
                    case R.id.rbNoContacto:
                        pos = 2;
                        break;
                    case R.id.rbAclaracion:
                        pos = 3;
                        break;
                    default:
                        tvContactoCliente.setError("");
                        pos = 0;
                        break;
                }
                try {
                    jsonResponse.put(CONTACTO, pos);
                    UpdateTemporal(jsonResponse);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Log.e("----------","Contacto Cliente  "  + pos);
                SelectContactoCliente(pos);

            }
        });

        rgActualizarTelefono.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                Log.e("----------","Actualiza telefono");
                tvActualizarTelefono.setError(null);
                int pos = 0;
                switch (checkedId){
                    case R.id.rbSiActualizar:
                        pos = 1;
                        break;
                    case R.id.rbNoActualizar:
                        pos = 2;
                        break;
                    default:
                        etActualizarTelefono.setText("");
                        pos = 0;
                        break;
                }

                try {
                    jsonResponse.put(ACTUALIZAR_TELEFONO, pos);
                    UpdateTemporal(jsonResponse);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                SelectActualizarTelefono(pos);
            }
        });

        rgResultadoPago.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                tvResultadoGestion.setError(null);
                int pos = 0;
                switch (checkedId) {
                    case R.id.rbPago:
                        pos = 1;
                        break;
                    case R.id.rbNoPago:
                        pos = 2;
                        break;
                    default:
                        pos = 0;
                        break;
                }

                Log.e("----------","Resultado pago:   "+  pos);
                try {
                    jsonResponse.put(RESULTADO_PAGO, pos);
                    UpdateTemporal(jsonResponse);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                SelectResultadoGestion(pos);

            }
        });

        rgPagaraRequerido.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                Log.e("----------","Pago requerido");
                tvPagaraRequerido.setError(null);
                switch (checkedId){
                    case R.id.rbSiRequerido:
                        if (Double.parseDouble(etMontoPagoRequerido.getText().toString().trim()) > 0){
                            SelectPagoRequerido(1);
                        }
                        else {
                            Toast.makeText(ctx, "No se pueden capturar pagos iguales a cero", Toast.LENGTH_SHORT).show();
                            rbSiRequerido.setChecked(false);
                            tvPagaraRequerido.setError("");
                            SelectPagoRequerido(-1);
                        }
                        break;
                    case R.id.rbNoRequerido:
                        SelectPagoRequerido(0);
                        break;
                    default:
                        tvPagaraRequerido.setError("");
                        SelectPagoRequerido(-1);
                        break;
                }
            }
        });

        rgRecibos.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                Log.e("----------","Recibos");
                switch (checkedId){
                    case R.id.rbSiRecibo:
                        SelectImprimirRecibos(1);
                        break;
                    case R.id.rbNoRecibo:
                        SelectImprimirRecibos(0);
                        break;
                    default:
                        SelectImprimirRecibos(-1);
                        break;
                }
            }
        });

        rgEstaGerente.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                Log.e("----------","Esta el gerente");
                tvEstaGerente.setError(null);
                int pos = 0;
                switch (checkedId){
                    case R.id.rbSiGerente:
                        pos = 1;
                        break;
                    case R.id.rbNoGerente:
                        pos = 2;
                        break;
                    default:
                        tvEstaGerente.setError("");
                        pos = 0;
                        break;
                }

                try {
                    jsonResponse.put(GERENTE, pos);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                UpdateTemporal(jsonResponse);
                SelectEstaGerente(pos);

            }
        });

        // Spinner Click
        spMedioPago.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.e("----------","medio pago");
                SelectMedioPago(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        /*spMotivoNoPago.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SelectMotivoNoPago(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/

        try {
            initComponents();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // ImageView Click
        if (parent.flagRespuesta) {
            ivFirma.setOnClickListener(ivFirma_OnClick);
            ivEvidencia.setOnClickListener(ivEvidencia_OnClick);
            ivFotoFachada.setOnClickListener(ivFotoFachada_OnClick);
        }
        return view;
    }

    /*@Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);



    }*/

    private View.OnClickListener ivFirma_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            final AlertDialog firma_dlg = Popups.showDialogConfirm(ctx, firma,
                    R.string.capturar_nueva_firma, R.string.accept, new Popups.DialogMessage() {
                        @Override
                        public void OnClickListener(AlertDialog dialog) {
                            Intent sig = new Intent(ctx, CapturarFirma.class);
                            startActivityForResult(sig, REQUEST_CODE_SIGNATURE);
                            dialog.dismiss();

                        }
                    }, R.string.cancel, new Popups.DialogMessage() {
                        @Override
                        public void OnClickListener(AlertDialog dialog) {
                            dialog.dismiss();
                        }
                    });
            Objects.requireNonNull(firma_dlg.getWindow()).requestFeature(Window.FEATURE_NO_TITLE);
            firma_dlg.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            firma_dlg.show();
        }
    };

    private View.OnClickListener ivEvidencia_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (spMedioPago.getSelectedItemPosition() > 0 && spMedioPago.getSelectedItemPosition() < 7 || spMedioPago.getSelectedItemPosition() == 8){
                final AlertDialog evidencia_dlg = Popups.showDialogConfirmImage(ctx, question,
                        R.string.capturar_foto_galeria, R.string.fotografia, new Popups.DialogMessage() {
                            @Override
                            public void OnClickListener(AlertDialog dialog) {
                                Intent i = new Intent(parent, CameraVertical.class);
                                i.putExtra(ORDER_ID, parent.ficha_ri.getId());
                                startActivityForResult(i, REQUEST_CODE_CAMARA_TICKET);
                                dialog.dismiss();

                            }
                        }, R.string.galeria, new Popups.DialogMessage() {
                            @Override
                            public void OnClickListener(AlertDialog dialog) {
                                Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                                gallery.setAction(Intent.ACTION_GET_CONTENT);
                                startActivityForResult(Intent.createChooser(gallery, "Select Picture"), REQUEST_CODE_GALERIA);
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
            else{
                final AlertDialog evidencia_dlg = Popups.showDialogConfirm(ctx, question,
                        R.string.capturar_nueva_fotografia, R.string.fotografia, new Popups.DialogMessage() {
                            @Override
                            public void OnClickListener(AlertDialog dialog) {
                                Intent i = new Intent(parent, CameraVertical.class);
                                i.putExtra(ORDER_ID, parent.ficha_ri.getId());
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
            }
        }
    };

    private View.OnClickListener ivFotoFachada_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            final AlertDialog fachada_cam_dlg = Popups.showDialogConfirm(ctx, camara,
                    R.string.capturar_nueva_fotografia, R.string.accept, new Popups.DialogMessage() {
                        @Override
                        public void OnClickListener(AlertDialog dialog) {
                            Intent i = new Intent(parent, CameraVertical.class);
                            i.putExtra(ORDER_ID, parent.ficha_ri.getId());
                            startActivityForResult(i, REQUEST_CODE_CAMARA_FACHADA);
                            dialog.dismiss();

                        }
                    }, R.string.cancel, new Popups.DialogMessage() {
                        @Override
                        public void OnClickListener(AlertDialog dialog) {
                            dialog.dismiss();
                        }
                    });
            Objects.requireNonNull(fachada_cam_dlg.getWindow()).requestFeature(Window.FEATURE_NO_TITLE);
            fachada_cam_dlg.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            fachada_cam_dlg.show();
        }
    };

    // TextView Listener Action
    private View.OnClickListener tvMotivoNoPagoxxx_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
            builder.setTitle(R.string.selected_option)
                    .setItems(R.array.reason_no_pay, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int position) {
                            tvMotivoNoPagoxxx.setError(null);
                            tvMotivoNoPagoxxx.setText(_motivo_no_pago[position]);
                            try {
                                jsonResponse.put(MOTIVO_NO_PAGO, _motivo_no_pago[position].toUpperCase());
                                UpdateTemporal(jsonResponse);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            SelectMotivoNoPago(position);
                        }
                    });
            builder.create();
            builder.show();
        }
    };

    private View.OnClickListener etMotivoAclaracion_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
            builder.setTitle(R.string.selected_option)
                    .setItems(R.array.outdated_information, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int position) {
                            tvMotivoAclaracion.setError(null);
                            etMotivoAclaracion.setText(_motivo_aclaracion[position]);
                            try {
                                jsonResponse.put(MOTIVO_ACLARACION, _motivo_aclaracion[position].toUpperCase());
                                UpdateTemporal(jsonResponse);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
            builder.create();
            builder.show();
        }
    };

    private View.OnClickListener ibMap_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            pbLoading.setVisibility(View.VISIBLE);
            ibMap.setEnabled(false);
            locationManager = (LocationManager) parent.getSystemService(Context.LOCATION_SERVICE);

            locationListener = new MyCurrentListener(new MyCurrentListener.evento() {
                @Override
                public void onComplete(String latitud, String longitud) {

                    ibMap.setEnabled(true);
                    if (!latitud.isEmpty() && !longitud.isEmpty()){
                        tvmapa.setError(null);
                        mapView.setVisibility(View.VISIBLE);
                        try {
                            jsonResponse.put(LATITUD, Double.parseDouble(latitud));
                            jsonResponse.put(LONGITUD, Double.parseDouble(longitud));
                            UpdateTemporal(jsonResponse);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        ColocarUbicacionGestion(Double.parseDouble(latitud), Double.parseDouble(longitud));
                    }
                    else{
                        pbLoading.setVisibility(View.GONE);
                        tvmapa.setError("");
                        Toast.makeText(ctx, getResources().getString(R.string.no_ubicacion), Toast.LENGTH_SHORT).show();
                    }



                    flagUbicacion = true;
                    if (flagUbicacion){
                        CancelUbicacion();
                    }
                }
            });
            if (ActivityCompat.checkSelfPermission(ctx, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(ctx, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            }

            locationManager.requestSingleUpdate(LocationManager.NETWORK_PROVIDER, locationListener,null);

        }
    };

    private View.OnClickListener ibFoto_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i = new Intent(parent, CameraVertical.class);
            i.putExtra(ORDER_ID, parent.ficha_ri.getId());
            startActivityForResult(i, REQUEST_CODE_CAMARA_TICKET);
        }
    };

    private View.OnClickListener ibFotoFachada_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i = new Intent(parent, CameraVertical.class);
            i.putExtra(ORDER_ID, parent.ficha_ri.getId());
            startActivityForResult(i, REQUEST_CODE_CAMARA_FACHADA);
        }
    };

    private View.OnClickListener ibGaleria_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
            gallery.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(gallery, "Select Picture"), REQUEST_CODE_GALERIA);
            //startActivityForResult(gallery, REQUEST_CODE_GALERIA);
        }
    };

    private View.OnClickListener ibFirma_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent sig = new Intent(ctx, CapturarFirma.class);
            sig.putExtra(TIPO,"");
            startActivityForResult(sig, REQUEST_CODE_SIGNATURE);
        }
    };

    private View.OnClickListener ibArqueoCaja_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i_arqueoCaja = new Intent(ctx, ArqueoDeCaja.class);
            i_arqueoCaja.putExtra(PAGO_REALIZADO, Double.parseDouble(etPagoRealizado.getText().toString()));
            i_arqueoCaja.putExtra(PAGO_REQUERIDO, parent.ficha_ri.getPrestamo().getPagoSemanal());
            i_arqueoCaja.putExtra(NOMBRE_GRUPO, parent.ficha_ri.getCliente().getNombre());
            i_arqueoCaja.putExtra(ORDER_ID, parent.ficha_ri.getId());
            startActivityForResult(i_arqueoCaja, REQUEST_CODE_ARQUEO_CAJA);
        }
    };

    private View.OnClickListener ibImprimir_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (!etPagoRealizado.getText().toString().trim().isEmpty() && Double.parseDouble(etPagoRealizado.getText().toString().trim()) > 0){
                Intent i = new Intent(ctx, PrintSeewoo.class);
                OrderModel order = new OrderModel(parent.ficha_ri.getId(),
                        "002",
                        parent.ficha_ri.getPrestamo().getMontoPrestamo(),
                        parent.ficha_ri.getPrestamo().getPagoSemanal(),
                        Miscellaneous.doubleFormat(etPagoRealizado),
                        parent.ficha_ri.getCliente().getNumeroCliente(),
                        parent.ficha_ri.getPrestamo().getNumeroDePrestamo(),
                        parent.ficha_ri.getCliente().getNombre(),
                        "NOMBRE DEL ALGUN ASESOR",
                        0);

                i.putExtra("order",order);
                i.putExtra("tag",true);

                startActivityForResult(i,REQUEST_CODE_IMPRESORA);
            }
            else
                Toast.makeText(ctx, "No ha capturado el pago realizado del cliente", Toast.LENGTH_SHORT).show();

        }
    };


    // EditText Click
    private View.OnClickListener etFechaDeposito_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            DatePickerDialog dpd = new DatePickerDialog(ctx, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                    myCalendar.set(Calendar.YEAR,year);
                    myCalendar.set(Calendar.MONTH,month);
                    myCalendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                    setDatePicked(etFechaDeposito);

                }
            },myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH));
            dpd.getDatePicker().setMaxDate(new Date().getTime());
            dpd.getDatePicker().setMinDate(minDate.getTime());
            dpd.show();
        }
    };

    private View.OnClickListener tvFechaDefuncion_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (parent.flagRespuesta) {
                myCalendar = Calendar.getInstance();
                dialog_date_picker dialogDatePicker = new dialog_date_picker();
                Bundle b = new Bundle();

                b.putInt(YEAR_CURRENT, myCalendar.get(Calendar.YEAR));
                b.putInt(MONTH_CURRENT, myCalendar.get(Calendar.MONTH));
                b.putInt(DAY_CURRENT, myCalendar.get(Calendar.DAY_OF_MONTH));
                b.putString(DATE_CURRENT, sdf.format(myCalendar.getTime()));
                b.putInt(IDENTIFIER, 5);
                b.putBoolean(FECHAS_POST, false);
                dialogDatePicker.setArguments(b);
                dialogDatePicker.setTargetFragment(ri_gestion_fragment.this,123);
                dialogDatePicker.show(parent.getSupportFragmentManager(), NameFragments.DIALOGDATEPICKER);
            }
            /*DatePickerDialog dpd = new DatePickerDialog(ctx, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    myCalendar.set(Calendar.YEAR,year);
                    myCalendar.set(Calendar.MONTH,month);
                    myCalendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                    setDatePicked(etFechaDefuncion);

                }
            },myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH));
            dpd.getDatePicker().setMaxDate(Calendar.getInstance().getTimeInMillis());
            dpd.show();*/
        }
    };

    private void setDatePicked(EditText et){
        sdf.setTimeZone(myCalendar.getTimeZone());
        et.setError(null);
        et.setText(sdf.format(myCalendar.getTime()));
    }

    private void initComponents() throws JSONException {
        tvExternalID.setText(parent.ficha_ri.getId());
        tvmapa.setError("");
        tvContactoCliente.setError("");
        etMontoPagoRequerido.setText(String.valueOf(parent.ficha_ri.getPrestamo().getPagoSemanal()));

        if (parent.jsonRes != null){
            jsonResponse = parent.jsonRes;
            Log.e("jsonParent", parent.jsonRes.toString());
            if (parent.jsonRes.has(LATITUD) && parent.jsonRes.has(LONGITUD)){
                try {
                    tvmapa.setError(null);
                    mapView.setVisibility(View.VISIBLE);
                    ColocarUbicacionGestion(parent.jsonRes.getDouble(LATITUD), parent.jsonRes.getDouble(LONGITUD));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            if (parent.jsonRes.has(CONTACTO)){
                if (!parent.flagRespuesta){
                    for(int i = 0; i < rgContactoCliente.getChildCount(); i++){
                        ((RadioButton) rgContactoCliente.getChildAt(i)).setEnabled(false);
                    }
                }
                switch (parent.jsonRes.getInt(CONTACTO)) {
                    case 2: //NO CONTACTO
                        ((RadioButton) rgContactoCliente.getChildAt(1)).setChecked(true);

                        if (parent.jsonRes.has(COMENTARIO)){
                            etComentario.setText(parent.jsonRes.getString(COMENTARIO));
                            etComentario.setVisibility(View.VISIBLE);
                            etComentario.setError(null);
                            if (!parent.flagRespuesta)
                                etComentario.setEnabled(false);
                        }

                        if (parent.jsonRes.has(FACHADA)){
                            File fachadaFile = new File(ROOT_PATH + "Fachada/"+parent.jsonRes.getString(FACHADA));
                            Uri uriFachada = Uri.fromFile(fachadaFile);
                            Glide.with(ctx).load(uriFachada).into(ivFotoFachada);
                            ibFotoFachada.setVisibility(View.GONE);
                            ivFotoFachada.setVisibility(View.VISIBLE);
                            byteEvidencia = getBytesUri(uriFachada, 1);
                            tvFotoFachada.setError(null);
                        }

                        rgEstaGerente.setVisibility(View.VISIBLE);
                        if (parent.jsonRes.has(GERENTE)){
                            ((RadioButton) rgEstaGerente.getChildAt(1)).setChecked(true);
                            if (!parent.flagRespuesta){
                                for(int i = 0; i < rgEstaGerente.getChildCount(); i++){
                                    ((RadioButton) rgEstaGerente.getChildAt(i)).setEnabled(false);
                                }
                            }
                            if (parent.jsonRes.getInt(GERENTE) == 1){
                                ((RadioButton) rgEstaGerente.getChildAt(0)).setChecked(true);
                                if (parent.jsonRes.has(FIRMA)){
                                    File firmaFile = new File(ROOT_PATH + "Firma/"+parent.jsonRes.getString(FIRMA));
                                    Uri uriFirma = Uri.fromFile(firmaFile);
                                    Glide.with(ctx).load(uriFirma).into(ivFirma);
                                    ibFirma.setVisibility(View.GONE);
                                    ivFirma.setVisibility(View.VISIBLE);
                                    byteFirma = getBytesUri(uriFirma, 1);
                                    tvFirma.setError(null);
                                }
                            }
                        }
                        break;
                    case 1: //SI CONTACTO
                        if (!parent.flagRespuesta){
                            for(int i = 0; i < rgActualizarTelefono.getChildCount(); i++){
                                ((RadioButton) rgActualizarTelefono.getChildAt(i)).setEnabled(false);
                            }
                            etActualizarTelefono.setEnabled(false);
                        }

                        Log.e("0xxxxxxx", parent.jsonRes.toString());
                        ((RadioButton) rgContactoCliente.getChildAt(0)).setChecked(true);
                        Log.e("0.5xxxxxxx", parent.jsonRes.toString());
                        if (parent.jsonRes.has(ACTUALIZAR_TELEFONO)){
                            ((RadioButton) rgActualizarTelefono.getChildAt(1)).setChecked(true);
                            if (parent.jsonRes.getInt(ACTUALIZAR_TELEFONO) == 1){
                                ((RadioButton) rgActualizarTelefono.getChildAt(0)).setChecked(true);
                                if (parent.jsonRes.has(NUEVO_TELEFONO)){
                                    etActualizarTelefono.setText(parent.jsonRes.getString(NUEVO_TELEFONO));
                                    etActualizarTelefono.setError(null);
                                    etActualizarTelefono.setVisibility(View.VISIBLE);
                                }
                            }
                        }

                        Log.e("1xxxxxxx", parent.jsonRes.toString());
                        if (parent.jsonRes.has(RESULTADO_PAGO)){
                            if (!parent.flagRespuesta){
                                for(int i = 0; i < rgResultadoPago.getChildCount(); i++){
                                    ((RadioButton) rgResultadoPago.getChildAt(i)).setEnabled(false);
                                }
                            }
                            Log.e("2xxxxxxx", parent.jsonRes.toString());
                            Log.e("xxxxxxx",parent.jsonRes.getString(RESULTADO_PAGO)+"asdas");
                            switch ((parent.jsonRes.getInt(RESULTADO_PAGO))){
                                case 2: //No Pago
                                    if (!parent.flagRespuesta) {
                                        //spMotivoNoPago.setEnabled(false);
                                        etComentario.setEnabled(false);
                                        tvFechaDefuncion.setEnabled(false);
                                    }

                                    ((RadioButton) rgResultadoPago.getChildAt(1)).setChecked(true);
                                    //spMotivoNoPago.setVisibility(View.VISIBLE);
                                   Log.e("motivo no pago", parent.jsonRes.getString(MOTIVO_NO_PAGO));
                                    tvMotivoNoPagoxxx.setText(parent.jsonRes.getString(MOTIVO_NO_PAGO));

                                    if (parent.jsonRes.has(FECHA_DEFUNCION)){
                                        tvFechaDefuncion.setText(parent.jsonRes.getString(FECHA_DEFUNCION));
                                        tvFechaDefuncion.setError(null);
                                        tvFechaDefuncion.setVisibility(View.VISIBLE);
                                    }

                                    if (parent.jsonRes.has(COMENTARIO)){
                                        etComentario.setText(parent.jsonRes.getString(COMENTARIO));
                                        etComentario.setVisibility(View.VISIBLE);
                                        etComentario.setError(null);
                                        if (!parent.flagRespuesta)
                                            etComentario.setEnabled(false);
                                    }

                                    if (parent.jsonRes.has(FACHADA)){
                                        File fachadaFile = new File(ROOT_PATH + "Fachada/"+parent.jsonRes.getString(FACHADA));
                                        Uri uriFachada = Uri.fromFile(fachadaFile);
                                        Glide.with(ctx).load(uriFachada).into(ivFotoFachada);
                                        ibFotoFachada.setVisibility(View.GONE);
                                        ivFotoFachada.setVisibility(View.VISIBLE);
                                        byteEvidencia = getBytesUri(uriFachada, 1);
                                        tvFotoFachada.setError(null);
                                    }

                                    rgEstaGerente.setVisibility(View.VISIBLE);
                                    if (parent.jsonRes.has(GERENTE)){
                                        ((RadioButton) rgEstaGerente.getChildAt(1)).setChecked(true);
                                        if (!parent.flagRespuesta){
                                            for(int i = 0; i < rgEstaGerente.getChildCount(); i++){
                                                ((RadioButton) rgEstaGerente.getChildAt(i)).setEnabled(false);
                                            }
                                        }
                                        if (parent.jsonRes.getInt(GERENTE) == 1){
                                            ((RadioButton) rgEstaGerente.getChildAt(0)).setChecked(true);
                                            if (parent.jsonRes.has(FIRMA)){
                                                File firmaFile = new File(ROOT_PATH + "Firma/"+parent.jsonRes.getString(FIRMA));
                                                Uri uriFirma = Uri.fromFile(firmaFile);
                                                Glide.with(ctx).load(uriFirma).into(ivFirma);
                                                ibFirma.setVisibility(View.GONE);
                                                ivFirma.setVisibility(View.VISIBLE);
                                                byteFirma = getBytesUri(uriFirma, 1);
                                                tvFirma.setError(null);
                                            }
                                        }
                                    }
                                    break;
                                case 1: // Si Pago
                                    ((RadioButton) rgResultadoPago.getChildAt(0)).setChecked(true);
                                    if (parent.jsonRes.has(POS_MEDIO_PAGO)){
                                        spMedioPago.setSelection(parent.jsonRes.getInt(POS_MEDIO_PAGO));

                                        if (parent.jsonRes.has(PAGO_REQUERIDO)){
                                            ((RadioButton) rgPagaraRequerido.getChildAt(1)).setChecked(true);
                                            if (!parent.flagRespuesta){
                                                for(int i = 0; i < rgPagaraRequerido.getChildCount(); i++){
                                                    ((RadioButton) rgPagaraRequerido.getChildAt(i)).setEnabled(false);
                                                }
                                                etPagoRealizado.setEnabled(false);
                                            }
                                            if (parent.jsonRes.getBoolean(PAGO_REQUERIDO)){
                                                ((RadioButton) rgPagaraRequerido.getChildAt(0)).setChecked(true);
                                            }

                                            etPagoRealizado.setText(parent.jsonRes.getString(PAGO_REALIZADO));
                                        }

                                        if (parent.jsonRes.has(FECHA_DEPOSITO)){
                                            etFechaDeposito.setText(parent.jsonRes.getString(FECHA_DEPOSITO));
                                            etFechaDeposito.setError(null);
                                        }

                                        if (parent.jsonRes.getInt(POS_MEDIO_PAGO) == 7){
                                            if (parent.jsonRes.has(IMPRESORA)){
                                                ((RadioButton) rgRecibos.getChildAt(1)).setChecked(true);
                                                etFolioRecibo.setEnabled(true);
                                                if (!parent.flagRespuesta){
                                                    for(int i = 0; i < rgRecibos.getChildCount(); i++){
                                                        ((RadioButton) rgRecibos.getChildAt(i)).setEnabled(false);
                                                    }
                                                    etFolioRecibo.setEnabled(false);
                                                }
                                                if (parent.jsonRes.getBoolean(IMPRESORA)){
                                                    ((RadioButton) rgRecibos.getChildAt(0)).setChecked(true);

                                                    if (parent.jsonRes.has(FOLIO_TICKET)){
                                                        ibImprimir.setVisibility(View.VISIBLE);
                                                        if (!parent.flagRespuesta)
                                                            ibImprimir.setVisibility(View.GONE);
                                                        etFolioRecibo.setEnabled(false);
                                                        llFolioRecibo.setVisibility(View.VISIBLE);
                                                        etFolioRecibo.setText(parent.jsonRes.getString(FOLIO_TICKET));
                                                        etFolioRecibo.setError(null);
                                                        folio_impreso = parent.jsonRes.getString(FOLIO_TICKET);
                                                    }
                                                    else {
                                                        ibImprimir.setVisibility(View.VISIBLE);
                                                    }
                                                }
                                                else{
                                                    ibImprimir.setVisibility(View.GONE);
                                                    llFolioRecibo.setVisibility(View.VISIBLE);
                                                    etFolioRecibo.setText(parent.jsonRes.getString(FOLIO_TICKET));
                                                    etFolioRecibo.setError(null);
                                                }
                                            }
                                        }

                                        if (parent.jsonRes.has(EVIDENCIA)){
                                            Log.v("PATH_EVIDENCIA", ROOT_PATH + "Evidencia/"+parent.jsonRes.getString(EVIDENCIA));
                                            File evidenciaFile = new File(ROOT_PATH + "Evidencia/"+parent.jsonRes.getString(EVIDENCIA));
                                            Uri uriEvidencia = Uri.fromFile(evidenciaFile);
                                            Glide.with(ctx).load(uriEvidencia).into(ivEvidencia);
                                            ibFoto.setVisibility(View.GONE);
                                            ibGaleria.setVisibility(View.GONE);
                                            ivEvidencia.setVisibility(View.VISIBLE);
                                            byteEvidencia = getBytesUri(uriEvidencia, 1);
                                            tvFotoGaleria.setError(null);
                                        }

                                        if (parent.jsonRes.has(GERENTE)){
                                            ((RadioButton) rgEstaGerente.getChildAt(1)).setChecked(true);
                                            if (!parent.flagRespuesta){
                                                for(int i = 0; i < rgEstaGerente.getChildCount(); i++){
                                                    ((RadioButton) rgEstaGerente.getChildAt(i)).setEnabled(false);
                                                }
                                            }
                                            if (parent.jsonRes.getBoolean(GERENTE)){
                                                ((RadioButton) rgEstaGerente.getChildAt(0)).setChecked(true);
                                                if (parent.jsonRes.has(FIRMA)){
                                                    Log.v("PATH_FIRMA", ROOT_PATH + "Firma/"+parent.jsonRes.getString(FIRMA));
                                                    File firmaFile = new File(ROOT_PATH + "Firma/"+parent.jsonRes.getString(FIRMA));
                                                    Uri uriFirma = Uri.fromFile(firmaFile);
                                                    Glide.with(ctx).load(uriFirma).into(ivFirma);
                                                    ibFirma.setVisibility(View.GONE);
                                                    ivFirma.setVisibility(View.VISIBLE);
                                                    byteFirma = getBytesUri(uriFirma, 1);
                                                    tvFirma.setError(null);
                                                }
                                            }
                                        }
                                    }
                                    break;
                            }
                        }

                        break;
                    case 3:
                        ((RadioButton) rgContactoCliente.getChildAt(2)).setChecked(true);
                        if (parent.jsonRes.has(MOTIVO_ACLARACION)){
                            etMotivoAclaracion.setText(parent.jsonRes.getString(MOTIVO_ACLARACION));
                            etMotivoAclaracion.setVisibility(View.VISIBLE);
                            tvMotivoAclaracion.setError(null);
                            if (!parent.flagRespuesta)
                                etMotivoAclaracion.setEnabled(false);
                        }
                        if (parent.jsonRes.has(COMENTARIO)){
                            etComentario.setText(parent.jsonRes.getString(COMENTARIO));
                            etComentario.setVisibility(View.VISIBLE);
                            etComentario.setError(null);
                            if (!parent.flagRespuesta)
                                etComentario.setEnabled(false);
                        }

                        rgEstaGerente.setVisibility(View.VISIBLE);
                        if (parent.jsonRes.has(GERENTE)){
                            ((RadioButton) rgEstaGerente.getChildAt(1)).setChecked(true);
                            if (!parent.flagRespuesta){
                                for(int i = 0; i < rgEstaGerente.getChildCount(); i++){
                                    ((RadioButton) rgEstaGerente.getChildAt(i)).setEnabled(false);
                                }
                            }
                            if (parent.jsonRes.getInt(GERENTE) == 1){
                                ((RadioButton) rgEstaGerente.getChildAt(0)).setChecked(true);
                                if (parent.jsonRes.has(FIRMA)){
                                    File firmaFile = new File(ROOT_PATH + "Firma/"+parent.jsonRes.getString(FIRMA));
                                    Uri uriFirma = Uri.fromFile(firmaFile);
                                    Glide.with(ctx).load(uriFirma).into(ivFirma);
                                    ibFirma.setVisibility(View.GONE);
                                    ivFirma.setVisibility(View.VISIBLE);
                                    byteFirma = getBytesUri(uriFirma, 1);
                                    tvFirma.setError(null);
                                }
                            }
                        }

                        break;
                }
            }
        }
    }

    //=========================  Comportamientos  ================================================
    private void SelectContactoCliente (int pos){
        if (rbSiGerente.isChecked() || rbNoGerente.isChecked()) tvEstaGerente.setError(null);
        else tvEstaGerente.setError("");
        switch (pos){
            case 1: // Si contacto cliente
                tvActualizarTelefono.setError("");
                tvResultadoGestion.setError("");
                etMotivoAclaracion.setText("");
                if (parent.jsonRes != null) {
                    try {
                        Log.e("1ResultadoGestion", parent.jsonRes.getString(RESULTADO_PAGO));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                rgResultadoPago.clearCheck();
                if (parent.jsonRes != null) {
                    try {
                        Log.e("2ResultadoGestion", parent.jsonRes.getString(RESULTADO_PAGO));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                SelectResultadoGestion(-1);
                rgEstaGerente.clearCheck();
                SelectEstaGerente(-1);
                llResultadoGestion.setVisibility(View.VISIBLE);
                llActualizarTelefono.setVisibility(View.VISIBLE);
                llComentario.setVisibility(View.GONE);
                llFotoFachada.setVisibility(View.GONE);
                llEstaGerente.setVisibility(View.GONE);
                llFirma.setVisibility(View.GONE);
                llMotivoAclaracion.setVisibility(View.GONE);
                break;
            case 2: // No contacto cliente
                tvFotoFachada.setError("");
                etMotivoAclaracion.setText("");
                rgResultadoPago.clearCheck();
                SelectResultadoGestion(-1);
                rgActualizarTelefono.clearCheck();
                SelectActualizarTelefono(-1);
                llActualizarTelefono.setVisibility(View.GONE);
                rgEstaGerente.clearCheck();
                SelectEstaGerente(-1);
                byteEvidencia = null;
                ivFotoFachada.setVisibility(View.GONE);
                ibFotoFachada.setVisibility(View.VISIBLE);
                llResultadoGestion.setVisibility(View.GONE);
                llComentario.setVisibility(View.VISIBLE);
                etComentario.setError(getResources().getString(R.string.campo_requerido));
                llFotoFachada.setVisibility(View.VISIBLE);
                llEstaGerente.setVisibility(View.VISIBLE);
                llMotivoAclaracion.setVisibility(View.GONE);
                break;
            case 3: // Aclaración
                tvMotivoAclaracion.setError("");
                etMotivoAclaracion.setText("");
                etComentario.setError(getResources().getString(R.string.campo_requerido));
                etComentario.setText("");
                rgResultadoPago.clearCheck();
                SelectResultadoGestion(-1);
                rgEstaGerente.clearCheck();
                SelectEstaGerente(-1);
                byteEvidencia = null;
                ivFotoFachada.setVisibility(View.GONE);
                ibFotoFachada.setVisibility(View.VISIBLE);
                rgActualizarTelefono.clearCheck();
                SelectActualizarTelefono(-1);
                llActualizarTelefono.setVisibility(View.GONE);
                llResultadoGestion.setVisibility(View.GONE);
                llComentario.setVisibility(View.VISIBLE);
                llFotoFachada.setVisibility(View.GONE);
                llEstaGerente.setVisibility(View.VISIBLE);
                llMotivoAclaracion.setVisibility(View.VISIBLE);
                break;
            default: //Sin seleccionar una opción o cualquier otro valor
                etMotivoAclaracion.setText("");
                rgResultadoPago.clearCheck();
                rgActualizarTelefono.clearCheck();
                SelectActualizarTelefono(-1);
                llActualizarTelefono.setVisibility(View.GONE);
                llResultadoGestion.setVisibility(View.GONE);
                llComentario.setVisibility(View.GONE);
                llFotoFachada.setVisibility(View.GONE);
                llEstaGerente.setVisibility(View.GONE);
                llMotivoAclaracion.setVisibility(View.GONE);
                break;
        }
    }
    private void SelectActualizarTelefono(int pos){
        switch (pos){
            case 1:
                etActualizarTelefono.setVisibility(View.VISIBLE);
                etActualizarTelefono.setError(getResources().getString(R.string.campo_requerido));
                break;
            case 2:
                etActualizarTelefono.setText("");
                etActualizarTelefono.setVisibility(View.GONE);
                break;
            default:
                tvActualizarTelefono.setError("");
                etActualizarTelefono.setText("");
                etActualizarTelefono.setVisibility(View.GONE);
                break;
        }
    }
    private void SelectResultadoGestion(int pos){
        switch (pos){
            case 1: //Si Pago
                //spMotivoNoPago.setSelection(0);
                SelectMotivoNoPago(0);
                llComentario.setVisibility(View.GONE);
                llMedioPago.setVisibility(View.VISIBLE);
                llMontoPagoRequerido.setVisibility(View.VISIBLE);
                llMotivoNoPago.setVisibility(View.GONE);
                llFotoFachada.setVisibility(View.GONE);
                llEstaGerente.setVisibility(View.GONE);
                break;
            case 2: //No Pago
                //tvMotivoNoPago.setError("");
                tvFotoFachada.setError("");
                spMedioPago.setSelection(0);
                SelectMedioPago(0);
                rgPagaraRequerido.clearCheck();
                SelectPagoRequerido(-1);
                llMedioPago.setVisibility(View.GONE);
                llMontoPagoRequerido.setVisibility(View.GONE);
                llComentario.setVisibility(View.VISIBLE);
                etComentario.setError(getResources().getString(R.string.campo_requerido));
                llMotivoNoPago.setVisibility(View.VISIBLE);
                llFotoFachada.setVisibility(View.VISIBLE);
                llEstaGerente.setVisibility(View.VISIBLE);
                break;
            default: //Sin seleccionar una opción o cualquier otro valor
                tvResultadoGestion.setError("");
                spMedioPago.setSelection(0);
                SelectMedioPago(0);
                //spMotivoNoPago.setSelection(0);
                SelectMotivoNoPago(0);
                rgPagaraRequerido.clearCheck();
                SelectPagoRequerido(-1);
                llMedioPago.setVisibility(View.GONE);
                llMontoPagoRequerido.setVisibility(View.GONE);
                llComentario.setVisibility(View.GONE);
                llMotivoNoPago.setVisibility(View.GONE);
                llFotoFachada.setVisibility(View.GONE);
                llEstaGerente.setVisibility(View.GONE);
                break;
        }
    }
    private void SelectMedioPago (int pos){
        if (!parent.flagRespuesta) {
            spMedioPago.setEnabled(false);
            if (pos > 0 && pos < 7 || pos == 8) {
                etFechaDeposito.setEnabled(false);
            } else {
                etFechaDeposito.setEnabled(true);
            }
        }

        if (rbSiRequerido.isChecked() || rbNoRequerido.isChecked()) tvPagaraRequerido.setError(null);
        else tvPagaraRequerido.setError("");

        tvMedioPago.setError(null);
        switch (pos){
            case 0: // Opción "Seleccione una opción"
                tvMedioPago.setError("");
                rgPagaraRequerido.clearCheck();
                SelectPagoRequerido(-1);
                llPagaraRequerido.setVisibility(View.GONE);
                llFechaDeposito.setVisibility(View.GONE);
                llFotoGaleria.setVisibility(View.GONE);
                llImprimirRecibo.setVisibility(View.GONE);
                llEstaGerente.setVisibility(View.GONE);
                break;
            case 1: // Banamex
            case 2: // Banorte
            case 3: // Telecom
            case 4: // Bansefi
            case 5: // Bancomer
            case 6: // Oxxo
            case 8: // Sidert
                if (byteEvidencia != null)
                    tvFotoGaleria.setError(null);
                else
                    tvFotoGaleria.setError("");

                Log.v("Aqui paso"," primero");
                if (etFechaDeposito.getText().toString().isEmpty())
                    etFechaDeposito.setError(getResources().getString(R.string.campo_requerido));
                ibGaleria.setEnabled(true);
                ibGaleria.setBackground(ctx.getResources().getDrawable(R.drawable.round_corner_blue));
                llPagaraRequerido.setVisibility(View.VISIBLE);
                llFechaDeposito.setVisibility(View.VISIBLE);
                llMontoPagoRequerido.setVisibility(View.VISIBLE);
                llImprimirRecibo.setVisibility(View.GONE);
                llFotoGaleria.setVisibility(View.VISIBLE);
                llEstaGerente.setVisibility(View.VISIBLE);
                break;
            case 7: // Efectivo
                if (byteEvidencia != null)
                    tvFotoGaleria.setError(null);
                else
                    tvFotoGaleria.setError("");
                ibGaleria.setEnabled(false);
                ibGaleria.setBackground(ctx.getResources().getDrawable(R.drawable.btn_disable));
                if (!etFolioRecibo.getText().toString().trim().isEmpty())
                    tvImprimirRecibo.setError(null);
                else
                    tvImprimirRecibo.setError("");
                llPagaraRequerido.setVisibility(View.VISIBLE);
                llFechaDeposito.setVisibility(View.GONE);
                llMontoPagoRequerido.setVisibility(View.VISIBLE);
                llImprimirRecibo.setVisibility(View.VISIBLE);
                llFotoGaleria.setVisibility(View.VISIBLE);
                llEstaGerente.setVisibility(View.VISIBLE);
                break;
            default: //Sin seleccionar una opción o cualquier otro valor
                tvMedioPago.setError("");
                rgPagaraRequerido.clearCheck();
                rgRecibos.clearCheck();
                SelectImprimirRecibos(-1);
                SelectPagoRequerido(-1);
                ivEvidencia.setVisibility(View.GONE);
                llPagaraRequerido.setVisibility(View.GONE);
                llFechaDeposito.setVisibility(View.GONE);
                llFotoGaleria.setVisibility(View.GONE);
                llImprimirRecibo.setVisibility(View.GONE);
                llEstaGerente.setVisibility(View.GONE);
                break;
        }
    }
    private void SelectMotivoNoPago (int pos){
        //tvMotivoNoPago.setError(null);
        switch (pos){
            case 0: // Negación de pago
            case 2: // Otro
                llDatosDefuncion.setVisibility(View.GONE);
                break;
            case 1: //Fallecimiento
                tvFechaDefuncion.setError("");
                if (!tvFechaDefuncion.getText().toString().trim().isEmpty())
                    tvFechaDefuncion.setError(null);
                llDatosDefuncion.setVisibility(View.VISIBLE);
                break;
            default: //Sin seleccionar una opción o cualquier otro valor
                //tvMotivoNoPago.setError("");
                llDatosDefuncion.setVisibility(View.GONE);
                break;
        }
    }
    private void SelectPagoRequerido (int pos){
        switch (pos){
            case -1: //Sin seleccionar una opción o cualquier otro valor
                etPagoRealizado.setText(String.valueOf(parent.ficha_ri.getPrestamo().getPagoRealizado()));
                etPagoRealizado.setEnabled(false);
                llMontoPagoRealizado.setVisibility(View.GONE);
                break;
            case 0: // No pagará requerido
                etPagoRealizado.setEnabled(true);
                llMontoPagoRealizado.setVisibility(View.VISIBLE);
                break;
            case 1: // Si pagará requerido
                etPagoRealizado.setText(String.valueOf(parent.ficha_ri.getPrestamo().getPagoRealizado()));
                etPagoRealizado.setEnabled(false);
                llMontoPagoRealizado.setVisibility(View.VISIBLE);
                break;
        }
    }
    private void SelectImprimirRecibos(int pos){
        switch (pos){
            case 0: //No cuenta con bateria la impresora
                Log.v("aqui pasa", "Select Imprimir recibos");
                etFolioRecibo.setError(getResources().getString(R.string.campo_requerido));
                tvImprimirRecibo.setError(null);
                llFolioRecibo.setVisibility(View.VISIBLE);
                etFolioRecibo.setText("");
                etFolioRecibo.setEnabled(true);
                etFolioRecibo.setHint(R.string.folio_sidert);
                etFolioRecibo.setInputType(InputType.TYPE_CLASS_NUMBER);
                ibImprimir.setVisibility(View.GONE);
                break;
            case 1: // Imprimir Recibos
                ibImprimir.setVisibility(View.VISIBLE);
                if (!folio_impreso.trim().isEmpty()) {
                    tvImprimirRecibo.setError(null);
                    etFolioRecibo.setText(folio_impreso);
                    etFolioRecibo.setEnabled(false);
                    etFolioRecibo.setError(null);
                    llFolioRecibo.setVisibility(View.VISIBLE);
                }
                else {
                    tvImprimirRecibo.setError("");
                    llFolioRecibo.setVisibility(View.GONE);
                }
                break;
            default: // Sin seleccionar alguna opción o cualquier valor diferente
                tvImprimirRecibo.setError("");
                llFolioRecibo.setVisibility(View.GONE);
                ibImprimir.setVisibility(View.GONE);
                break;
        }

    }
    private void SelectEstaGerente (int pos){
        switch (pos){
            case 1: // Si está el gerente
                if (ivFirma.getVisibility() == View.VISIBLE && byteFirma != null)
                    tvFirma.setError(null);
                else
                    tvFirma.setError("");
                llFirma.setVisibility(View.VISIBLE);
                break;
            case 2: // No está el gerente
                llFirma.setVisibility(View.GONE);
                break;
            default: // Sin seleccionar alguna opción o cualquier valor diferente
                byteFirma = null;
                ivFirma.setVisibility(View.GONE);
                ibFirma.setVisibility(View.VISIBLE);
                llFirma.setVisibility(View.GONE);
                break;
        }
    }
    //===================== Listener GPS  =======================================================
    private void ColocarUbicacionGestion (final double lat, final double lon){
        mapView.onResume();
        try {
            MapsInitializer.initialize(parent.getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mGooglemap) {
                mMap = mGooglemap;
                if (ActivityCompat.checkSelfPermission(ctx, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(ctx, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

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
        latLngGestion = coordenadas;
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

        pbLoading.setVisibility(View.GONE);
        ibMap.setVisibility(View.GONE);
    }

    private void CancelUbicacion (){
        if (flagUbicacion)
            locationManager.removeUpdates(locationListener);
    }

    private byte[] getBytesUri (Uri uri_img, int tipo_imagen){
        byte[] compressedByteArray = null;

        switch (tipo_imagen){
            case 0:
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(ctx.getContentResolver() , uri_img);
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream);
                    compressedByteArray = stream.toByteArray();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case 1:
                try {
                    InputStream iStream =   ctx.getContentResolver().openInputStream(uri_img);
                    ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
                    int bufferSize = 1024;
                    byte[] buffer = new byte[bufferSize];

                    int len = 0;
                    while ((len = iStream.read(buffer)) != -1) {
                        byteBuffer.write(buffer, 0, len);
                    }
                    compressedByteArray = byteBuffer.toByteArray();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
        }
        return compressedByteArray;
    }

    //===================  Resultado de activities  ===========================================
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case REQUEST_CODE_SIGNATURE:
                if (resultCode == Activity.RESULT_OK){
                    if (data != null){
                        ibFirma.setVisibility(View.GONE);
                        ivFirma.setVisibility(View.VISIBLE);
                        tvFirma.setError(null);
                        Glide.with(ctx)
                                .load(data.getByteArrayExtra(FIRMA_IMAGE))
                                .into(ivFirma);
                        byteFirma = data.getByteArrayExtra(FIRMA_IMAGE);

                        try {
                            jsonResponse.put(FIRMA,  Miscellaneous.save(byteFirma, 3));
                            UpdateTemporal(jsonResponse);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                break;
            case REQUEST_CODE_IMPRESORA:
                if (resultCode == Activity.RESULT_OK){
                    //if (data != null){
                    if (false){
                        Toast.makeText(ctx, data.getStringExtra(MESSAGE), Toast.LENGTH_SHORT).show();
                        if(data.getIntExtra(RES_PRINT,0) == 1 || data.getIntExtra(RES_PRINT,0) == 2){
                            folio_impreso = "Asesor951-" + String.valueOf(data.getIntExtra(FOLIO,0));
                            etFolioRecibo.setText(folio_impreso);
                            tvImprimirRecibo.setError(null);
                            llFolioRecibo.setVisibility(View.VISIBLE);
                        }
                    }
                    else{
                        ibImprimir.setVisibility(View.GONE);
                        folio_impreso = "Asesor951-" +1;
                        etFolioRecibo.setText(folio_impreso);
                        tvImprimirRecibo.setError(null);
                        llFolioRecibo.setVisibility(View.VISIBLE);
                    }
                }
                break;
            case REQUEST_CODE_GALERIA:
                if (data != null){
                    ibFoto.setVisibility(View.GONE);
                    ibGaleria.setVisibility(View.GONE);
                    tvFotoGaleria.setError(null);
                    imageUri = data.getData();
                    ivEvidencia.setVisibility(View.VISIBLE);
                    Glide.with(ctx).load(imageUri).centerCrop().into(ivEvidencia);

                    byteEvidencia = getBytesUri(imageUri, 0);
                }
                break;
            case REQUEST_CODE_CAMARA_TICKET:
                if (resultCode == Activity.RESULT_OK){
                    if (data != null){
                        ibFoto.setVisibility(View.GONE);
                        ibGaleria.setVisibility(View.GONE);
                        tvFotoGaleria.setError(null);
                        ivEvidencia.setVisibility(View.VISIBLE);
                        byteEvidencia = data.getByteArrayExtra(PICTURE);
                        Glide.with(ctx).load(byteEvidencia).centerCrop().into(ivEvidencia);

                    }
                }
                break;
            case REQUEST_CODE_CAMARA_FACHADA:
                if (resultCode == Activity.RESULT_OK){
                    if (data != null){
                        ibFotoFachada.setVisibility(View.GONE);
                        tvFotoFachada.setError(null);
                        ivFotoFachada.setVisibility(View.VISIBLE);
                        byteEvidencia = data.getByteArrayExtra(PICTURE);
                        Glide.with(ctx).load(byteEvidencia).centerCrop().into(ivFotoFachada);

                        try {
                            jsonResponse.put(TIPO_IMAGEN,  "FACHADA");
                            jsonResponse.put(FACHADA,  Miscellaneous.save(byteEvidencia, 1));
                            UpdateTemporal(jsonResponse);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                break;
            case REQUEST_CODE_ARQUEO_CAJA:
                if (resultCode == Activity.RESULT_OK){
                    if (data != null){
                        if (data.getBooleanExtra(SAVE,false)){
                            tvArqueoCaja.setError(null);
                            rbArqueoCaja.setChecked(true);
                        }
                        else {
                            tvArqueoCaja.setError("");
                            rbArqueoCaja.setChecked(false);
                        }
                    }
                }
                break;
            case 123:
                if (resultCode == 321){
                    if (data != null){
                        tvFechaDefuncion.setText(data.getStringExtra(DATE));
                    }
                }
                break;
        }
    }

    private void UpdateTemporal (JSONObject json){
        Log.e("json", json.toString());
        ContentValues cv = new ContentValues();
        cv.put("respuesta", json.toString());
        if (ENVIROMENT)
            db.update(FICHAS, cv, "external_id = ?", new String[]{parent.ficha_ri.getId()});
        else
            db.update(FICHAS_T, cv, "external_id = ?",new String[]{parent.ficha_ri.getId()});
    }
}
