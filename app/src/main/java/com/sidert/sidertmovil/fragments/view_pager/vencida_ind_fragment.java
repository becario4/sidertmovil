package com.sidert.sidertmovil.fragments.view_pager;

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
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
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
import com.sidert.sidertmovil.activities.CameraVertical;
import com.sidert.sidertmovil.activities.CapturarFirma;
import com.sidert.sidertmovil.activities.PrintSeewoo;
import com.sidert.sidertmovil.activities.VencidaIndividual;
import com.sidert.sidertmovil.activities.VistaPreviaGestion;
import com.sidert.sidertmovil.database.DBhelper;
import com.sidert.sidertmovil.fragments.dialogs.dialog_date_picker;
import com.sidert.sidertmovil.models.MImpresion;
import com.sidert.sidertmovil.utils.CanvasCustom;
import com.sidert.sidertmovil.utils.Miscellaneous;
import com.sidert.sidertmovil.utils.MyCurrentListener;
import com.sidert.sidertmovil.utils.NameFragments;
import com.sidert.sidertmovil.utils.NetworkStatus;
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

import static com.sidert.sidertmovil.utils.Constants.ACTUALIZAR_TELEFONO;
import static com.sidert.sidertmovil.utils.Constants.COMENTARIO;
import static com.sidert.sidertmovil.utils.Constants.CONTACTO;
import static com.sidert.sidertmovil.utils.Constants.DATE;
import static com.sidert.sidertmovil.utils.Constants.DATE_CURRENT;
import static com.sidert.sidertmovil.utils.Constants.DAY_CURRENT;
import static com.sidert.sidertmovil.utils.Constants.ESTATUS;
import static com.sidert.sidertmovil.utils.Constants.EVIDENCIA;
import static com.sidert.sidertmovil.utils.Constants.FECHAS_POST;
import static com.sidert.sidertmovil.utils.Constants.FECHA_DEFUNCION;
import static com.sidert.sidertmovil.utils.Constants.FECHA_DEPOSITO;
import static com.sidert.sidertmovil.utils.Constants.FECHA_FIN;
import static com.sidert.sidertmovil.utils.Constants.FECHA_PROMESA_PAGO;
import static com.sidert.sidertmovil.utils.Constants.FIRMA;
import static com.sidert.sidertmovil.utils.Constants.FIRMA_IMAGE;
import static com.sidert.sidertmovil.utils.Constants.FOLIO;
import static com.sidert.sidertmovil.utils.Constants.FOLIO_TICKET;
import static com.sidert.sidertmovil.utils.Constants.FORMAT_DATE_GNRAL;
import static com.sidert.sidertmovil.utils.Constants.FORMAT_TIMESTAMP;
import static com.sidert.sidertmovil.utils.Constants.GERENTE;
import static com.sidert.sidertmovil.utils.Constants.IDENTIFIER;
import static com.sidert.sidertmovil.utils.Constants.IMPRESORA;
import static com.sidert.sidertmovil.utils.Constants.LATITUD;
import static com.sidert.sidertmovil.utils.Constants.LONGITUD;
import static com.sidert.sidertmovil.utils.Constants.MEDIO_PAGO;
import static com.sidert.sidertmovil.utils.Constants.MESSAGE;
import static com.sidert.sidertmovil.utils.Constants.MONTH_CURRENT;
import static com.sidert.sidertmovil.utils.Constants.MONTO_PROMESA;
import static com.sidert.sidertmovil.utils.Constants.MONTO_REQUERIDO;
import static com.sidert.sidertmovil.utils.Constants.MOTIVO_NO_PAGO;
import static com.sidert.sidertmovil.utils.Constants.NOMBRE;
import static com.sidert.sidertmovil.utils.Constants.NUEVO_TELEFONO;
import static com.sidert.sidertmovil.utils.Constants.ORDER_ID;
import static com.sidert.sidertmovil.utils.Constants.PAGO_REALIZADO;
import static com.sidert.sidertmovil.utils.Constants.PAGO_REQUERIDO;
import static com.sidert.sidertmovil.utils.Constants.PARAMS;
import static com.sidert.sidertmovil.utils.Constants.PICTURE;
import static com.sidert.sidertmovil.utils.Constants.REQUEST_CODE_CAMARA_FACHADA;
import static com.sidert.sidertmovil.utils.Constants.REQUEST_CODE_CAMARA_TICKET;
import static com.sidert.sidertmovil.utils.Constants.REQUEST_CODE_FIRMA;
import static com.sidert.sidertmovil.utils.Constants.REQUEST_CODE_GALERIA;
import static com.sidert.sidertmovil.utils.Constants.REQUEST_CODE_IMPRESORA;
import static com.sidert.sidertmovil.utils.Constants.REQUEST_CODE_PREVIEW;
import static com.sidert.sidertmovil.utils.Constants.RESULTADO_PAGO;
import static com.sidert.sidertmovil.utils.Constants.RES_PRINT;
import static com.sidert.sidertmovil.utils.Constants.ROOT_PATH;
import static com.sidert.sidertmovil.utils.Constants.SALDO_ACTUAL;
import static com.sidert.sidertmovil.utils.Constants.SALDO_CORTE;
import static com.sidert.sidertmovil.utils.Constants.SCREEN_SHOT;
import static com.sidert.sidertmovil.utils.Constants.TBL_AMORTIZACIONES_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_PRESTAMOS_IND_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_RESPUESTAS_IND_V_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_TRACKER_ASESOR_T;
import static com.sidert.sidertmovil.utils.Constants.TERMINADO;
import static com.sidert.sidertmovil.utils.Constants.TIMESTAMP;
import static com.sidert.sidertmovil.utils.Constants.TIPO;
import static com.sidert.sidertmovil.utils.Constants.UBICACION;
import static com.sidert.sidertmovil.utils.Constants.YEAR_CURRENT;
import static com.sidert.sidertmovil.utils.Constants.camara;
import static com.sidert.sidertmovil.utils.Constants.firma;
import static com.sidert.sidertmovil.utils.Constants.question;


public class vencida_ind_fragment extends Fragment {

    private Context ctx;
    VencidaIndividual parent;

    public String[] _contacto;
    public String[] _confirmacion;
    public String[] _resultado_gestion;
    public String[] _medio_pago;
    public String[] _motivo_no_pago;
    public String[] _imprimir;

    public byte[] byteFirma;
    public byte[] byteEvidencia;

    private Uri imageUri;

    public TextView tvmapa;
    public TextView tvContacto;
    public TextView tvActualizarTelefono;
    public TextView tvResultadoGestion;
    public TextView tvMedioPago;
    public TextView tvFechaDeposito;
    public TextView tvMontoPagoRequerido;
    public TextView tvPagaraRequerido;
    public TextView tvImprimirRecibo;
    public TextView tvFotoGaleria;
    public TextView tvMotivoNoPago;
    public TextView tvFechaDefuncion;
    public TextView tvFechaPromesaPago;
    public TextView tvFachada;
    public TextView tvGerente;
    public TextView tvFirma;

    private ImageButton ibMap;
    private ImageButton ibFoto;
    private ImageButton ibImprimir;
    private ImageButton ibGaleria;
    private ImageButton ibFachada;
    private ImageButton ibFirma;

    private ImageView ivEvidencia;
    private ImageView ivFachada;
    private ImageView ivFirma;

    public EditText etActualizarTelefono;
    public EditText etPagoRealizado;
    public EditText etFolioRecibo;
    public EditText etComentario;
    public EditText etMontoPromesa;

    private LinearLayout llActualizarTelefono;
    private LinearLayout llResultadoGestion;
    private LinearLayout llMedioPago;
    private LinearLayout llFechaDeposito;
    private LinearLayout llMontoPagoRequerido;
    private LinearLayout llPagaraRequerido;
    private LinearLayout llMontoPagoRealizado;
    private LinearLayout llImprimirRecibo;
    private LinearLayout llFolioRecibo;
    private LinearLayout llFotoGaleria;
    private LinearLayout llMotivoNoPago;
    private LinearLayout llPromesaPago;
    private LinearLayout llDefuncion;
    private LinearLayout llComentario;
    private LinearLayout llFachada;
    private LinearLayout llGerente;
    private LinearLayout llFirma;

    private boolean flagUbicacion = false;

    private LocationManager locationManager;
    private MyCurrentListener locationListener;

    private ProgressBar pbLoading;

    private MapView mapView;
    private GoogleMap mMap;
    private Marker mMarker;
    public LatLng latLngGestion;

    private String folio_impreso = "";

    private DBhelper dBhelper;
    private SQLiteDatabase db;

    private Calendar myCalendar;
    private SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_DATE_GNRAL);

    private SessionManager session;

    private int res_impresion = 0;
    private int medio_pago_anterio = -1;

    private DecimalFormat df;
    private DecimalFormat dfnd;
    private boolean hasFractionalPart;

    private int _mediosPago = 0;

    private String fechaIni = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_vencida_ind, container, false);

        ctx = getContext();
        dBhelper        = new DBhelper(ctx);
        db              = dBhelper.getWritableDatabase();

        parent                = (VencidaIndividual) getActivity();
        assert parent != null;
        parent.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        session = new SessionManager(ctx);

        _contacto = getResources().getStringArray(R.array.contacto_cliente);
        _confirmacion = getResources().getStringArray(R.array.confirmacion);
        _resultado_gestion = getResources().getStringArray(R.array.resultado_gestion);
        _motivo_no_pago = getResources().getStringArray(R.array.motivo_no_pago_cv);
        _mediosPago = R.array.metodo_pago_cv;
        _medio_pago = getResources().getStringArray(R.array.metodo_pago_cv);

        _imprimir = getResources().getStringArray(R.array.imprimir);

        tvmapa                  = v.findViewById(R.id.tvMapa);
        tvContacto              = v.findViewById(R.id.tvContacto);
        tvActualizarTelefono    = v.findViewById(R.id.tvActualizarTelefono);
        tvResultadoGestion      = v.findViewById(R.id.tvResultadoGestion);
        tvMedioPago             = v.findViewById(R.id.tvMedioPago);
        tvFechaDeposito         = v.findViewById(R.id.tvFechaDeposito);
        tvMontoPagoRequerido    = v.findViewById(R.id.tvMontoPagoRequerido);
        tvPagaraRequerido       = v.findViewById(R.id.tvPagaraRequerido);
        tvImprimirRecibo        = v.findViewById(R.id.tvImprimirRecibo);
        tvFotoGaleria           = v.findViewById(R.id.tvFotoGaleria);
        tvMotivoNoPago          = v.findViewById(R.id.tvMotivoNoPago);
        tvFechaPromesaPago      = v.findViewById(R.id.tvFechaPromesaPago);
        tvFechaDefuncion        = v.findViewById(R.id.tvFechaDefuncion);
        tvFachada               = v.findViewById(R.id.tvFachada);
        tvGerente               = v.findViewById(R.id.tvGerente);
        tvFirma                 = v.findViewById(R.id.tvFirma);

        etActualizarTelefono    = v.findViewById(R.id.etActualizarTelefono);
        etPagoRealizado         = v.findViewById(R.id.etPagoRealizado);
        etFolioRecibo           = v.findViewById(R.id.etFolioRecibo);
        etMontoPromesa          = v.findViewById(R.id.etMontoPromesa);
        etComentario            = v.findViewById(R.id.etComentario);

        ibMap           = v.findViewById(R.id.ibMap);
        ibImprimir      = v.findViewById(R.id.ibImprimir);
        ibFoto          = v.findViewById(R.id.ibFoto);
        ibGaleria       = v.findViewById(R.id.ibGaleria);
        ibFachada       = v.findViewById(R.id.ibFachada);
        ibFirma         = v.findViewById(R.id.ibFirma);

        ivEvidencia     = v.findViewById(R.id.ivEvidencia);
        ivFachada       = v.findViewById(R.id.ivFachada);
        ivFirma         = v.findViewById(R.id.ivFirma);

        pbLoading       = v.findViewById(R.id.pbLoading);

        mapView         = v.findViewById(R.id.mapGestion);

        llActualizarTelefono = v.findViewById(R.id.llActualizarTelefono);
        llResultadoGestion   = v.findViewById(R.id.llResultadoGestion);
        llMedioPago          = v.findViewById(R.id.llMedioPago);
        llFechaDeposito      = v.findViewById(R.id.llFechaDeposito);
        llMontoPagoRequerido = v.findViewById(R.id.llMontoPagoRequerido);
        llPagaraRequerido    = v.findViewById(R.id.llPagaraRequerido);
        llMontoPagoRealizado = v.findViewById(R.id.llMontoPagoRealizado);
        llImprimirRecibo     = v.findViewById(R.id.llImprimirRecibo);
        llFolioRecibo        = v.findViewById(R.id.llFolioRecibo);
        llFotoGaleria        = v.findViewById(R.id.llFotoGaleria);
        llMotivoNoPago       = v.findViewById(R.id.llMotivoNoPago);
        llPromesaPago        = v.findViewById(R.id.llPromesaPago);
        llDefuncion          = v.findViewById(R.id.llDatosDefuncion);
        llComentario         = v.findViewById(R.id.llComentario);
        llFachada            = v.findViewById(R.id.llFachada);
        llGerente            = v.findViewById(R.id.llGerente);
        llFirma              = v.findViewById(R.id.llFirma);

        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
        df = new DecimalFormat("#,###.##", symbols);
        df.setDecimalSeparatorAlwaysShown(false);

        dfnd = new DecimalFormat("#,###", symbols);
        dfnd.setDecimalSeparatorAlwaysShown(false);
        dfnd.setDecimalFormatSymbols(symbols);

        hasFractionalPart = false;

        mapView.onCreate(savedInstanceState);
        locationManager = (LocationManager) parent.getSystemService(Context.LOCATION_SERVICE);

        // ImageButton Click
        ibMap.setOnClickListener(ibMap_OnClick);
        ibImprimir.setOnClickListener(ibImprimir_OnClick);
        ibFoto.setOnClickListener(ibFoto_OnClick);
        ibGaleria.setOnClickListener(ibGaleria_OnClick);
        ibFachada.setOnClickListener(ibFachada_OnClick);
        ibFirma.setOnClickListener(ibFirma_OnClick);

        // TextView Click
        tvContacto.setOnClickListener(tvContacto_OnClick);
        tvActualizarTelefono.setOnClickListener(tvActualizarTelefono_OnClick);
        tvResultadoGestion.setOnClickListener(tvResultadoGestion_OnClick);
        tvMedioPago.setOnClickListener(tvMedioPago_OnClick);
        tvFechaDeposito.setOnClickListener(tvFechaDeposito_OnClick);
        tvPagaraRequerido.setOnClickListener(tvPagaraRequerido_OnClick);
        tvImprimirRecibo.setOnClickListener(tvImprimirRecibo_OnClick);
        tvMotivoNoPago.setOnClickListener(tvMotivoNoPago_OnClick);
        tvFechaPromesaPago.setOnClickListener(tvFechaPromesaPago_OnClick);
        tvFechaDefuncion.setOnClickListener(tvFechaDefuncion_OnClick);
        tvGerente.setOnClickListener(tvGerente_OnClick);

        etComentario.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0){
                    Update("comentario", etComentario.getText().toString().trim().toUpperCase());
                }
                else
                    Update("comentario", "");
            }
        });

        etActualizarTelefono.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 10) {
                    if (!etActualizarTelefono.getText().toString().trim().isEmpty() &&
                            etActualizarTelefono.getText().toString().trim().length() == 10) {
                        Update("nuevo_telefono", etActualizarTelefono.getText().toString().trim());
                    }
                }
                else{
                    Update("nuevo_telefono", "");
                }
            }
        });

        etPagoRealizado.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s)
            {
                etPagoRealizado.removeTextChangedListener(this);

                try {
                    int inilen, endlen;
                    inilen = etPagoRealizado.getText().length();
                    Log.e("symbols", String.valueOf(df.getDecimalFormatSymbols().getGroupingSeparator())+" Symbols");
                    String v = s.toString().replace(String.valueOf(df.getDecimalFormatSymbols().getGroupingSeparator()), "");
                    Number n = df.parse(v);
                    int cp = etPagoRealizado.getSelectionStart();
                    if (hasFractionalPart) {
                        etPagoRealizado.setText(df.format(n));
                    } else {
                        etPagoRealizado.setText(dfnd.format(n));
                    }
                    endlen = etPagoRealizado.getText().length();
                    int sel = (cp + (endlen - inilen));
                    if (sel > 0 && sel <= etPagoRealizado.getText().length()) {
                        etPagoRealizado.setSelection(sel);
                    } else {
                        // place cursor at the end?
                        etPagoRealizado.setSelection(etPagoRealizado.getText().length() - 1);
                    }
                } catch (NumberFormatException nfe) {
                    // do nothing?
                } catch (ParseException e) {
                    // do nothing?
                }

                if (s.length() > 0) {
                    if (!etPagoRealizado.getText().toString().trim().isEmpty()) {
                        try {
                            if (Double.parseDouble(etPagoRealizado.getText().toString().trim().replace(",", "")) > 0) {
                                Update("pago_realizado", etPagoRealizado.getText().toString().trim().replace(",", ""));
                            }
                        }catch (NumberFormatException e){
                            etPagoRealizado.setText("");
                            Update("pago_realizado", "");
                        }
                    }
                }
                else{
                    Update("pago_realizado", "");
                }

                etPagoRealizado.addTextChangedListener(this);
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                if (s.toString().contains(String.valueOf(df.getDecimalFormatSymbols().getDecimalSeparator())))
                {
                    hasFractionalPart = true;
                } else {
                    hasFractionalPart = false;
                }
            }
        });

        etMontoPromesa.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s)
            {
                etMontoPromesa.removeTextChangedListener(this);

                try {
                    int inilen, endlen;
                    inilen = etMontoPromesa.getText().length();
                    String v = s.toString().replace(String.valueOf(df.getDecimalFormatSymbols().getGroupingSeparator()), "");
                    Number n = df.parse(v);
                    int cp = etMontoPromesa.getSelectionStart();
                    if (hasFractionalPart) {
                        etMontoPromesa.setText(df.format(n));
                    } else {
                        etMontoPromesa.setText(dfnd.format(n));
                    }
                    endlen = etMontoPromesa.getText().length();
                    int sel = (cp + (endlen - inilen));
                    if (sel > 0 && sel <= etMontoPromesa.getText().length()) {
                        etMontoPromesa.setSelection(sel);
                    } else {
                        // place cursor at the end?
                        etMontoPromesa.setSelection(etMontoPromesa.getText().length() - 1);
                    }
                } catch (NumberFormatException nfe) {
                    // do nothing?
                } catch (ParseException e) {
                    // do nothing?
                }

                if (s.length() > 0) {
                    if (!etMontoPromesa.getText().toString().trim().isEmpty()) {
                        try {
                            if (Double.parseDouble(etMontoPromesa.getText().toString().trim().replace(",", "")) > 0) {
                                Update("monto_promesa", etMontoPromesa.getText().toString().trim().replace(",", ""));
                            }
                        }catch (NumberFormatException e){
                            etMontoPromesa.setText("");
                            Update("monto_promesa", "");
                        }
                    }
                }
                else{
                    Update("monto_promesa", "");
                }

                etMontoPromesa.addTextChangedListener(this);
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                if (s.toString().contains(String.valueOf(df.getDecimalFormatSymbols().getDecimalSeparator())))
                {
                    hasFractionalPart = true;
                } else {
                    hasFractionalPart = false;
                }
            }
        });

        etFolioRecibo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (Miscellaneous.MedioPago(tvMedioPago) == 6){
                    if (s.length() > 0){
                        Update("folio", s.toString());
                    }
                    else{
                        Update("folio", s.toString());
                    }
                }
            }
        });

        ivFirma.setOnClickListener(ivFirma_OnClick);
        ivEvidencia.setOnClickListener(ivEvidencia_OnClick);
        ivFachada.setOnClickListener(ivFotoFachada_OnClick);

        init();

        return v;
    }

    private View.OnClickListener ibMap_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            pbLoading.setVisibility(View.VISIBLE);
            ibMap.setEnabled(false);
            locationManager = (LocationManager) parent.getSystemService(Context.LOCATION_SERVICE);
            final Handler myHandler = new Handler();
            locationListener = new MyCurrentListener(new MyCurrentListener.evento() {
                @Override
                public void onComplete(String latitud, String longitud) {

                    ibMap.setEnabled(true);
                    if (!latitud.isEmpty() && !longitud.isEmpty()){

                        tvmapa.setError(null);
                        mapView.setVisibility(View.VISIBLE);
                        ColocarUbicacionGestion(Double.parseDouble(latitud), Double.parseDouble(longitud));
                    }
                    else{
                        pbLoading.setVisibility(View.GONE);
                        tvmapa.setError("");
                        Toast.makeText(ctx, getResources().getString(R.string.no_ubicacion), Toast.LENGTH_SHORT).show();
                    }
                    myHandler.removeCallbacksAndMessages(null);

                    Cursor row;
                    row = dBhelper.getRecords(TBL_RESPUESTAS_IND_V_T, " WHERE id_prestamo = ?", " ORDER BY _id ASC",new String[]{parent.id_prestamo});
                    row.moveToLast();
                    if (row.getCount() == 0){
                        String fechaInicio = Miscellaneous.ObtenerFecha(TIMESTAMP);
                        fechaIni = fechaInicio;
                        HashMap<Integer, String> params = new HashMap<>();
                        params.put(0,parent.id_prestamo);
                        if (latitud.trim().isEmpty() && longitud.trim().isEmpty()) {
                            parent.latitud = "0";
                            parent.longitud = "0";
                            params.put(1, "0");
                            params.put(2, "0");
                        }
                        else{
                            params.put(1, latitud);
                            params.put(2, longitud);
                        }
                        params.put(3, "");
                        params.put(4, "");
                        params.put(5, "");
                        params.put(6, "");
                        params.put(7, "");
                        params.put(8, "");
                        params.put(9, "");
                        params.put(10, "");
                        params.put(11, "");
                        params.put(12, "");
                        params.put(13, "");
                        params.put(14, "");
                        params.put(15, "");
                        params.put(16, "");
                        params.put(17, "");
                        params.put(18, "");
                        params.put(19, "");
                        params.put(20, "");
                        params.put(21, "");
                        params.put(22, fechaInicio);
                        params.put(23, "");
                        params.put(24, "");
                        params.put(25, "0");
                        params.put(26, "0");
                        params.put(27, "");
                        params.put(28, "");
                        params.put(29, "");
                        params.put(30, "0");
                        params.put(31, fechaInicio.replace("-","").replace(" ", "").replace(":","")+"-"+session.getUser().get(0)+"-"+parent.num_prestamo+"-"+parent.clave_cliente);

                        long id;
                        id = dBhelper.saveRespuestasVencidasInd(db, params);

                        parent.id_respuesta = String.valueOf(id);
                    }
                    else{
                        if (row.getInt(26) > 0){
                            String fechaInicio = Miscellaneous.ObtenerFecha(TIMESTAMP);
                            HashMap<Integer, String> params = new HashMap<>();
                            params.put(0,parent.id_prestamo);
                            if (latitud.trim().isEmpty() && longitud.trim().isEmpty()) {
                                parent.latitud = "0";
                                parent.longitud = "0";
                                params.put(1, "0");
                                params.put(2, "0");
                            }
                            else{
                                params.put(1, latitud);
                                params.put(2, longitud);
                            }
                            params.put(3, "");
                            params.put(4, "");
                            params.put(5, "");
                            params.put(6, "");
                            params.put(7, "");
                            params.put(8, "");
                            params.put(9, "");
                            params.put(10, "");
                            params.put(11, "");
                            params.put(12, "");
                            params.put(13, "");
                            params.put(14, "");
                            params.put(15, "");
                            params.put(16, "");
                            params.put(17, "");
                            params.put(18, "");
                            params.put(19, "");
                            params.put(20, "");
                            params.put(21, "");
                            params.put(22, fechaInicio);
                            params.put(23, "");
                            params.put(24, "");
                            params.put(25, "0");
                            params.put(26, "0");
                            params.put(27, "");
                            params.put(28, "");
                            params.put(29, "");
                            params.put(30, "0");
                            params.put(31, fechaInicio.replace("-","").replace(" ", "").replace(":","")+"-"+session.getUser().get(0)+"-"+parent.num_prestamo+"-"+parent.clave_cliente);
                            long id;

                            id = dBhelper.saveRespuestasVencidasInd(db, params);

                            parent.id_respuesta = String.valueOf(id);
                        }
                        else{
                            Update("latitud", latitud);
                            Update("longitud", longitud);
                        }
                    }

                    flagUbicacion = true;
                    if (flagUbicacion){
                        CancelUbicacion();
                    }
                }
            });
            if (ActivityCompat.checkSelfPermission(ctx, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(ctx, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            }

            String provider;

            if (NetworkStatus.haveNetworkConnection(ctx)) {
                Log.e("Proveedor", "RED");
                provider = LocationManager.NETWORK_PROVIDER;
            } else {
                Log.e("Proveedor", "GPS");
                provider = LocationManager.GPS_PROVIDER;
            }

            locationManager.requestSingleUpdate(provider, locationListener, null);

            myHandler.postDelayed(new Runnable() {
                public void run() {
                    parent.latitud = "0";
                    parent.longitud = "0";
                    Cursor row;
                    row = dBhelper.getRecords(TBL_RESPUESTAS_IND_V_T, " WHERE id_prestamo = ?", " ORDER BY _id ASC",new String[]{parent.id_prestamo});
                    row.moveToLast();
                    if (row.getCount() == 0){
                        String fechaInicio = Miscellaneous.ObtenerFecha(TIMESTAMP);
                        HashMap<Integer, String> params = new HashMap<>();
                        params.put(0,parent.id_prestamo);
                        parent.latitud = "0";
                        parent.longitud = "0";
                        params.put(1, "0");
                        params.put(2, "0");
                        params.put(3, "");
                        params.put(4, "");
                        params.put(5, "");
                        params.put(6, "");
                        params.put(7, "");
                        params.put(8, "");
                        params.put(9, "");
                        params.put(10, "");
                        params.put(11, "");
                        params.put(12, "");
                        params.put(13, "");
                        params.put(14, "");
                        params.put(15, "");
                        params.put(16, "");
                        params.put(17, "");
                        params.put(18, "");
                        params.put(19, "");
                        params.put(20, "");
                        params.put(21, "");
                        params.put(22, fechaInicio);
                        params.put(23, "");
                        params.put(24, "");
                        params.put(25, "0");
                        params.put(26, "0");
                        params.put(27, "");
                        params.put(28, "");
                        params.put(29, "");
                        params.put(30, "0");
                        params.put(31, fechaInicio.replace("-","").replace(" ", "").replace(":","")+"-"+session.getUser().get(0)+"-"+parent.num_prestamo+"-"+parent.clave_cliente);

                        long id;

                        id = dBhelper.saveRespuestasVencidasInd(db, params);

                        parent.id_respuesta = String.valueOf(id);
                    }
                    else{
                        if (row.getInt(26) > 0){
                            String fechaInicio = Miscellaneous.ObtenerFecha(TIMESTAMP);
                            HashMap<Integer, String> params = new HashMap<>();
                            params.put(0,parent.id_prestamo);
                            parent.latitud = "0";
                            parent.longitud = "0";
                            params.put(1, "0");
                            params.put(2, "0");
                            params.put(3, "");
                            params.put(4, "");
                            params.put(5, "");
                            params.put(6, "");
                            params.put(7, "");
                            params.put(8, "");
                            params.put(9, "");
                            params.put(10, "");
                            params.put(11, "");
                            params.put(12, "");
                            params.put(13, "");
                            params.put(14, "");
                            params.put(15, "");
                            params.put(16, "");
                            params.put(17, "");
                            params.put(18, "");
                            params.put(19, "");
                            params.put(20, "");
                            params.put(21, "");
                            params.put(22, fechaInicio);
                            params.put(23, "");
                            params.put(24, "");
                            params.put(25, "0");
                            params.put(26, "0");
                            params.put(27, "");
                            params.put(28, "");
                            params.put(29, "");
                            params.put(30, "0");
                            params.put(31, fechaInicio.replace("-","").replace(" ", "").replace(":","")+"-"+session.getUser().get(0)+"-"+parent.num_prestamo+"-"+parent.clave_cliente);
                            long id;

                            id = dBhelper.saveRespuestasVencidasInd(db, params);

                            parent.id_respuesta = String.valueOf(id);
                        }
                        else{
                            Update("latitud", "0");
                            Update("longitud", "0");
                        }
                    }
                    locationManager.removeUpdates(locationListener);
                    pbLoading.setVisibility(View.GONE);
                    ibMap.setEnabled(true);
                    Toast.makeText(ctx, "No se logró obtener la ubicación", Toast.LENGTH_SHORT).show();
                }
            }, 60000);

        }
    };

    private View.OnClickListener ibImprimir_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (!etPagoRealizado.getText().toString().trim().isEmpty() && Double.parseDouble(etPagoRealizado.getText().toString().trim().replace(",","")) > 0){
                Intent i = new Intent(ctx, PrintSeewoo.class);
                MImpresion mImpresion = new MImpresion();
                mImpresion.setIdPrestamo(parent.id_prestamo);
                mImpresion.setIdGestion(parent.id_respuesta);
                mImpresion.setMonto(String.valueOf(Math.ceil(Double.parseDouble(etPagoRealizado.getText().toString().trim().replace(",","")))));
                mImpresion.setMontoPrestamo(parent.monto_prestamo);
                mImpresion.setNumeroPrestamo(parent.num_prestamo);
                mImpresion.setNumeroCliente(parent.num_cliente);
                mImpresion.setNombre(parent.nombre);
                mImpresion.setPagoRequerido(parent.monto_requerido);
                mImpresion.setNombreAsesor(session.getUser().get(1)+" "+session.getUser().get(2)+" "+session.getUser().get(3));
                mImpresion.setAsesorId(session.getUser().get(0));
                mImpresion.setTipoPrestamo("VENCIDA");
                mImpresion.setTipoGestion("INDIVIDUAL");
                mImpresion.setNombreFirma(parent.nombre);
                mImpresion.setResultPrint(res_impresion);
                mImpresion.setClaveCliente(parent.clave_cliente);

                i.putExtra("order", mImpresion);
                i.putExtra("tag",true);

                startActivityForResult(i,REQUEST_CODE_IMPRESORA);
            }
            else
                Toast.makeText(ctx, "No ha capturado el pago realizado del cliente", Toast.LENGTH_SHORT).show();
        }
    };

    private View.OnClickListener ibFoto_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i = new Intent(parent, CameraVertical.class);
            i.putExtra(ORDER_ID, parent.id_prestamo);
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
                        .start(ctx,vencida_ind_fragment.this);
            }
            /*if (ContextCompat.checkSelfPermission(ctx, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                    || ContextCompat.checkSelfPermission(ctx,Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                requestPermissions(new String[] {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 123);
            } else {
                CropImage.activity()
                        .setOutputCompressQuality(50)
                        .start(ctx,recuperacion_ind_fragment.this);
            }*/
            /*Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
            gallery.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(gallery, "Select Picture"), REQUEST_CODE_GALERIA);*/
        }
    };

    private View.OnClickListener ibFachada_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i = new Intent(parent, CameraVertical.class);
            i.putExtra(ORDER_ID, parent.id_prestamo);
            startActivityForResult(i, REQUEST_CODE_CAMARA_FACHADA);
        }
    };

    private View.OnClickListener ibFirma_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent sig = new Intent(ctx, CapturarFirma.class);
            sig.putExtra(TIPO,"");
            startActivityForResult(sig, REQUEST_CODE_FIRMA);
        }
    };

    private View.OnClickListener tvContacto_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
            builder.setTitle(R.string.selected_option)
                    .setItems(R.array.contacto_cliente, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int position) {
                            tvContacto.setError(null);
                            etComentario.setText("");
                            tvContacto.setText(_contacto[position]);
                            tvActualizarTelefono.setError(null);
                            tvFachada.setError(null);

                            Cursor row;
                            row = dBhelper.getRecords(TBL_RESPUESTAS_IND_V_T, " WHERE id_prestamo = ?", " ORDER BY _id ASC", new String[]{parent.id_prestamo});
                            row.moveToLast();
                            if (row.getCount() == 0) {
                                String fechaInicio = Miscellaneous.ObtenerFecha(TIMESTAMP);
                                fechaIni = fechaInicio;
                                HashMap<Integer, String> params = new HashMap<>();
                                params.put(0, parent.id_prestamo);
                                params.put(1, "");
                                params.put(2, "");
                                params.put(3, _contacto[position]);
                                params.put(4, "");
                                params.put(5, "");
                                params.put(6, "");
                                params.put(7, "");
                                params.put(8, "");
                                params.put(9, "");
                                params.put(10, "");
                                params.put(11, "");
                                params.put(12, "");
                                params.put(13, "");
                                params.put(14, "");
                                params.put(15, "");
                                params.put(16, "");
                                params.put(17, "");
                                params.put(18, "");
                                params.put(19, "");
                                params.put(20, "");
                                params.put(21, "");
                                params.put(22, fechaInicio);
                                params.put(23, "");
                                params.put(24, "");
                                params.put(25, "0");
                                params.put(26, "0");
                                params.put(27, "");
                                params.put(28, "");
                                params.put(29, "");
                                params.put(30, "0");
                                params.put(31, fechaInicio.replace("-","").replace(" ", "").replace(":","")+"-"+session.getUser().get(0)+"-"+parent.num_prestamo+"-"+parent.clave_cliente);
                                long id = 0;

                                id = dBhelper.saveRespuestasVencidasInd(db, params);

                                parent.id_respuesta = String.valueOf(id);
                            } else {
                                if (row.getInt(26) > 0) {
                                    String fechaInicio = Miscellaneous.ObtenerFecha(TIMESTAMP);
                                    HashMap<Integer, String> params = new HashMap<>();
                                    params.put(0, parent.id_prestamo);
                                    params.put(1, "");
                                    params.put(2, "");
                                    params.put(3, _contacto[position]);
                                    params.put(4, "");
                                    params.put(5, "");
                                    params.put(6, "");
                                    params.put(7, "");
                                    params.put(8, "");
                                    params.put(9, "");
                                    params.put(10, "");
                                    params.put(11, "");
                                    params.put(12, "");
                                    params.put(13, "");
                                    params.put(14, "");
                                    params.put(15, "");
                                    params.put(16, "");
                                    params.put(17, "");
                                    params.put(18, "");
                                    params.put(19, "");
                                    params.put(20, "");
                                    params.put(21, "");
                                    params.put(22, fechaInicio);
                                    params.put(23, "");
                                    params.put(24, "");
                                    params.put(25, "0");
                                    params.put(26, "0");
                                    params.put(27, "");
                                    params.put(28, "");
                                    params.put(29, "");
                                    params.put(30, "0");
                                    params.put(31, fechaInicio.replace("-","").replace(" ", "").replace(":","")+"-"+session.getUser().get(0)+"-"+parent.num_prestamo+"-"+parent.clave_cliente);
                                    long id = 0;

                                    id = dBhelper.saveRespuestasVencidasInd(db, params);

                                    parent.id_respuesta = String.valueOf(id);
                                } else {
                                    Update("contacto", _contacto[position]);

                                    Update("gerente", "");
                                    Update("firma", "");
                                    Update("evidencia", "");
                                    Update("tipo_imagen", "");
                                }
                            }
                            SelectContactoCliente(position);

                        }
                    });
            builder.create();
            builder.show();
        }
    };

    private View.OnClickListener tvActualizarTelefono_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
            builder.setTitle(R.string.selected_option)
                    .setItems(R.array.confirmacion, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int position) {
                            tvActualizarTelefono.setError(null);
                            tvActualizarTelefono.setText(_confirmacion[position]);
                            Update("actualizar_telefono", _confirmacion[position]);
                            SelectActualizarTelefono(position);
                        }
                    });
            builder.create();
            builder.show();
        }
    };

    private View.OnClickListener tvResultadoGestion_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
            builder.setTitle(R.string.selected_option)
                    .setItems(R.array.resultado_gestion, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int position) {
                            byteEvidencia = null;
                            ibFachada.setVisibility(View.VISIBLE);
                            ivFachada.setVisibility(View.GONE);
                            tvResultadoGestion.setError(null);
                            tvResultadoGestion.setText(_resultado_gestion[position]);

                            Update("resultado_gestion", _resultado_gestion[position]);

                            SelectResultadoGestion(position);
                        }
                    });
            builder.create();
            builder.show();

        }
    };

    private View.OnClickListener tvMedioPago_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
            builder.setTitle(R.string.selected_option)
                    .setItems(_mediosPago, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int position) {


                            tvMedioPago.setError(null);
                            tvMedioPago.setText(_medio_pago[position]);
                            if (Miscellaneous.MedioPago(tvMedioPago) == 6 && medio_pago_anterio >= 0) {
                                Update("evidencia", "");
                                Update("tipo_imagen", "");
                                byteEvidencia = null;

                                ibGaleria.setEnabled(false);
                                ibGaleria.setBackground(ctx.getResources().getDrawable(R.drawable.btn_disable));
                                ibFoto.setVisibility(View.VISIBLE);
                                ibGaleria.setVisibility(View.VISIBLE);
                                llFotoGaleria.setVisibility(View.VISIBLE);
                                ivEvidencia.setVisibility(View.GONE);
                            }
                            else if(Miscellaneous.MedioPago(tvMedioPago) >= 0 && medio_pago_anterio == 6){
                                byteEvidencia = null;
                                Update("evidencia", "");
                                Update("tipo_imagen", "");
                                ibGaleria.setEnabled(true);
                                ibGaleria.setBackground(ctx.getResources().getDrawable(R.drawable.round_corner_blue));
                                ibFoto.setVisibility(View.VISIBLE);
                                ibGaleria.setVisibility(View.VISIBLE);
                                llFotoGaleria.setVisibility(View.VISIBLE);
                                ivEvidencia.setVisibility(View.GONE);
                            }
                            medio_pago_anterio = position;
                            Update("medio_pago", _medio_pago[position]);
                            SelectMedioPago(Miscellaneous.MedioPago(tvMedioPago));
                        }
                    });
            builder.create();
            builder.show();
        }
    };

    private View.OnClickListener tvFechaDeposito_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            myCalendar = Calendar.getInstance();
            dialog_date_picker dialogDatePicker = new dialog_date_picker();
            Bundle b = new Bundle();

            b.putInt(YEAR_CURRENT, myCalendar.get(Calendar.YEAR));
            b.putInt(MONTH_CURRENT, myCalendar.get(Calendar.MONTH));
            b.putInt(DAY_CURRENT, myCalendar.get(Calendar.DAY_OF_MONTH));
            b.putString(DATE_CURRENT, sdf.format(myCalendar.getTime()));
            b.putInt(IDENTIFIER, 6);
            b.putBoolean(FECHAS_POST, false);
            dialogDatePicker.setArguments(b);
            dialogDatePicker.setTargetFragment(vencida_ind_fragment.this,812);
            dialogDatePicker.show(parent.getSupportFragmentManager(), NameFragments.DIALOGDATEPICKER);
        }
    };

    private View.OnClickListener tvPagaraRequerido_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
            builder.setTitle(R.string.selected_option)
                    .setItems(R.array.confirmacion, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int position) {
                            tvPagaraRequerido.setError(null);
                            tvPagaraRequerido.setText(_confirmacion[position]);
                            Update("pagara_requerido", _confirmacion[position]);
                            switch (position) {
                                case 0:
                                    if (Miscellaneous.MedioPago(tvMedioPago) == 6)
                                        etPagoRealizado.setText(String.valueOf(Math.ceil(Double.parseDouble(parent.monto_requerido))));
                                    else
                                        etPagoRealizado.setText(parent.monto_requerido);
                                    if (!etPagoRealizado.getText().toString().trim().isEmpty() && Double.parseDouble(etPagoRealizado.getText().toString().trim().replace(",","")) > 0) {
                                        SelectPagoRequerido(0);
                                    } else {
                                        Toast.makeText(ctx, "No se pueden capturar pagos iguales a cero", Toast.LENGTH_SHORT).show();
                                        tvPagaraRequerido.setText("");
                                        tvPagaraRequerido.setError("");
                                        SelectPagoRequerido(-1);
                                    }
                                    break;
                                case 1:
                                    if (Miscellaneous.MedioPago(tvMedioPago) == 6)
                                        etPagoRealizado.setText(String.valueOf(Math.ceil(Double.parseDouble(parent.monto_requerido))));
                                    else
                                        etPagoRealizado.setText(parent.monto_requerido);
                                    SelectPagoRequerido(1);
                                    break;
                                default:
                                    tvPagaraRequerido.setError("");
                                    SelectPagoRequerido(-1);
                                    break;
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
                            Update("imprimir_recibo", _imprimir[position]);
                            SelectImprimirRecibos(position);
                        }
                    });
            builder.create();
            builder.show();
        }
    };

    private View.OnClickListener tvMotivoNoPago_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
            builder.setTitle(R.string.selected_option)
                    .setItems(R.array.motivo_no_pago_cv, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int position) {
                            tvMotivoNoPago.setError(null);
                            tvMotivoNoPago.setText(_motivo_no_pago[position]);
                            Update("motivo_no_pago", _motivo_no_pago[position]);

                            SelectMotivoNoPago(position);
                        }
                    });
            builder.create();
            builder.show();
        }
    };

    private View.OnClickListener tvFechaPromesaPago_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            myCalendar = Calendar.getInstance();
            dialog_date_picker dialogDatePicker = new dialog_date_picker();
            Bundle b = new Bundle();

            b.putInt(YEAR_CURRENT, myCalendar.get(Calendar.YEAR));
            b.putInt(MONTH_CURRENT, myCalendar.get(Calendar.MONTH));
            b.putInt(DAY_CURRENT, myCalendar.get(Calendar.DAY_OF_MONTH));
            b.putString(DATE_CURRENT, sdf.format(myCalendar.getTime()));
            b.putInt(IDENTIFIER, 10);
            b.putBoolean(FECHAS_POST, true);
            dialogDatePicker.setArguments(b);
            dialogDatePicker.setTargetFragment(vencida_ind_fragment.this,213);
            dialogDatePicker.show(parent.getSupportFragmentManager(), NameFragments.DIALOGDATEPICKER);
        }
    };

    private View.OnClickListener tvFechaDefuncion_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
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
            dialogDatePicker.setTargetFragment(vencida_ind_fragment.this,123);
            dialogDatePicker.show(parent.getSupportFragmentManager(), NameFragments.DIALOGDATEPICKER);
        }
    };

    private View.OnClickListener tvGerente_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
            builder.setTitle(R.string.selected_option)
                    .setItems(R.array.confirmacion, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int position) {
                            tvGerente.setError(null);
                            tvGerente.setText(_confirmacion[position]);

                            Update("gerente", _confirmacion[position]);

                            SelectEstaGerente(position);

                        }
                    });
            builder.create();
            builder.show();
        }
    };

    private View.OnClickListener ivFirma_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            final AlertDialog firma_dlg = Popups.showDialogConfirm(ctx, firma,
                    R.string.capturar_nueva_firma, R.string.accept, new Popups.DialogMessage() {
                        @Override
                        public void OnClickListener(AlertDialog dialog) {
                            Intent sig = new Intent(ctx, CapturarFirma.class);
                            sig.putExtra(TIPO,"");
                            startActivityForResult(sig, REQUEST_CODE_FIRMA);
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
            if (tvMedioPago.getText().toString().trim().toUpperCase().equals("EFECTIVO")){
                final AlertDialog evidencia_dlg = Popups.showDialogConfirm(ctx, question,
                        R.string.capturar_nueva_fotografia, R.string.fotografia, new Popups.DialogMessage() {
                            @Override
                            public void OnClickListener(AlertDialog dialog) {
                                Intent i = new Intent(parent, CameraVertical.class);
                                i.putExtra(ORDER_ID, parent.id_prestamo);
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
            else{
                final AlertDialog evidencia_dlg = Popups.showDialogConfirmImage(ctx, question,
                        R.string.capturar_foto_galeria, R.string.fotografia, new Popups.DialogMessage() {
                            @Override
                            public void OnClickListener(AlertDialog dialog) {
                                Intent i = new Intent(parent, CameraVertical.class);
                                i.putExtra(ORDER_ID, parent.id_prestamo);
                                startActivityForResult(i, REQUEST_CODE_CAMARA_TICKET);
                                dialog.dismiss();

                            }
                        }, R.string.galeria, new Popups.DialogMessage() {
                            @Override
                            public void OnClickListener(AlertDialog dialog) {
                                int compress = 10;
                                if( Build.MANUFACTURER.toUpperCase().equals("SAMSUNG"))
                                    compress = 40;
                                CropImage.activity()
                                        .setAutoZoomEnabled(true)
                                        .setMinCropWindowSize(3000,4000)
                                        .setOutputCompressQuality(compress)
                                        .start(ctx,vencida_ind_fragment.this);
                                /*Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                                gallery.setAction(Intent.ACTION_GET_CONTENT);
                                startActivityForResult(Intent.createChooser(gallery, "Select Picture"), REQUEST_CODE_GALERIA);*/
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
                            i.putExtra(ORDER_ID, parent.id_prestamo);
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
        CameraUpdate ubication = CameraUpdateFactory.newLatLngZoom(coordenadas,15);

        mMap.addMarker(new MarkerOptions()
                .position(coordenadas)
                .title(""));

        mMap.animateCamera(ubication);

        pbLoading.setVisibility(View.GONE);
        ibMap.setVisibility(View.GONE);
    }
    private void CancelUbicacion (){
        if (flagUbicacion)
            locationManager.removeUpdates(locationListener);
    }

    //=========================  Comportamientos  ================================================
    private void SelectContactoCliente (int pos){
        if (!tvGerente.getText().toString().trim().isEmpty()) tvGerente.setError(null);
        else tvGerente.setError("");
        switch (pos){
            case 0: // Si contacto cliente
                tvActualizarTelefono.setError("");
                tvResultadoGestion.setError("");

                tvResultadoGestion.setText("");

                SelectResultadoGestion(-1);
                tvGerente.setText("");
                SelectEstaGerente(-1);
                llResultadoGestion.setVisibility(View.VISIBLE);
                llActualizarTelefono.setVisibility(View.VISIBLE);
                llComentario.setVisibility(View.GONE);
                llFachada.setVisibility(View.GONE);
                llGerente.setVisibility(View.GONE);
                llFirma.setVisibility(View.GONE);
                break;
            case 1: // No contacto cliente
                tvFachada.setError("");
                tvResultadoGestion.setText("");
                SelectResultadoGestion(-1);
                tvActualizarTelefono.setText("");
                SelectActualizarTelefono(-1);
                llActualizarTelefono.setVisibility(View.GONE);
                tvGerente.setText("");
                SelectEstaGerente(-1);
                byteEvidencia = null;
                ivFachada.setVisibility(View.GONE);
                ibFachada.setVisibility(View.VISIBLE);
                llResultadoGestion.setVisibility(View.GONE);
                llComentario.setVisibility(View.VISIBLE);
                etComentario.setError(getResources().getString(R.string.campo_requerido));
                llFachada.setVisibility(View.VISIBLE);
                llGerente.setVisibility(View.VISIBLE);
                break;
            case 2: // Aclaración
                etComentario.setError(getResources().getString(R.string.campo_requerido));
                etComentario.setText("");
                tvResultadoGestion.setText("");
                SelectResultadoGestion(-1);
                tvGerente.setText("");
                SelectEstaGerente(-1);
                byteEvidencia = null;
                ivFachada.setVisibility(View.GONE);
                ibFachada.setVisibility(View.VISIBLE);
                tvActualizarTelefono.setText("");
                SelectActualizarTelefono(-1);
                llActualizarTelefono.setVisibility(View.GONE);
                llResultadoGestion.setVisibility(View.GONE);
                llComentario.setVisibility(View.VISIBLE);
                llFachada.setVisibility(View.GONE);
                llGerente.setVisibility(View.VISIBLE);
                break;
            default: //Sin seleccionar una opción o cualquier otro valor
                tvResultadoGestion.setText("");
                tvActualizarTelefono.setText("");
                SelectActualizarTelefono(-1);
                llActualizarTelefono.setVisibility(View.GONE);
                llResultadoGestion.setVisibility(View.GONE);
                llComentario.setVisibility(View.GONE);
                llFachada.setVisibility(View.GONE);
                llGerente.setVisibility(View.GONE);
                break;
        }
    }
    private void SelectActualizarTelefono(int pos){
        switch (pos){
            case 0:
                etActualizarTelefono.setVisibility(View.VISIBLE);
                etActualizarTelefono.setError(getResources().getString(R.string.campo_requerido));
                break;
            case 1:
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
            case 0: //Si Pago
                tvMotivoNoPago.setText("");
                SelectMotivoNoPago(-1);
                llComentario.setVisibility(View.GONE);
                llMedioPago.setVisibility(View.VISIBLE);
                llMontoPagoRequerido.setVisibility(View.VISIBLE);
                llMotivoNoPago.setVisibility(View.GONE);
                llFachada.setVisibility(View.GONE);
                llGerente.setVisibility(View.GONE);
                break;
            case 1: //No Pago
                tvMotivoNoPago.setError("");
                tvFachada.setError("");
                tvMedioPago.setText("");
                SelectMedioPago(-1);
                tvPagaraRequerido.setText("");
                SelectPagoRequerido(-1);
                llMedioPago.setVisibility(View.GONE);
                llMontoPagoRequerido.setVisibility(View.GONE);
                llComentario.setVisibility(View.VISIBLE);
                etComentario.setError(getResources().getString(R.string.campo_requerido));
                llMotivoNoPago.setVisibility(View.VISIBLE);
                llFachada.setVisibility(View.VISIBLE);
                llGerente.setVisibility(View.VISIBLE);
                break;
            default: //Sin seleccionar una opción o cualquier otro valor
                tvResultadoGestion.setError("");
                tvMedioPago.setText("");
                SelectMedioPago(-1);
                tvMotivoNoPago.setText("");
                SelectMotivoNoPago(-1);
                tvPagaraRequerido.setText("");
                SelectPagoRequerido(-1);
                llMedioPago.setVisibility(View.GONE);
                llMontoPagoRequerido.setVisibility(View.GONE);
                llComentario.setVisibility(View.GONE);
                llMotivoNoPago.setVisibility(View.GONE);
                llFachada.setVisibility(View.GONE);
                llGerente.setVisibility(View.GONE);
                break;
        }
    }
    private void SelectMedioPago (int pos){
        if (!tvPagaraRequerido.getText().toString().trim().isEmpty()) tvPagaraRequerido.setError(null);
        else tvPagaraRequerido.setError("");

        tvMedioPago.setError(null);
        switch (pos){
            case -1: // Opción "Seleccione una opción"
                tvMedioPago.setError("");
                tvPagaraRequerido.setText("");
                SelectPagoRequerido(-1);
                llPagaraRequerido.setVisibility(View.GONE);
                llFechaDeposito.setVisibility(View.GONE);
                llFotoGaleria.setVisibility(View.GONE);
                llImprimirRecibo.setVisibility(View.GONE);
                llFolioRecibo.setVisibility(View.GONE);
                llGerente.setVisibility(View.GONE);
                break;
            case 0: // Banamex
            case 1: // Banorte
            case 2: // Bancomer
            case 3: // Telecom
            case 4: // Bansefi
            case 5: // Oxxo
            case 8: // Banamex722
                if (byteEvidencia != null)
                    tvFotoGaleria.setError(null);
                else
                    tvFotoGaleria.setError("");

                if (tvFechaDeposito.getText().toString().isEmpty())
                    tvFechaDeposito.setError(getResources().getString(R.string.campo_requerido));
                ibGaleria.setEnabled(true);
                ibGaleria.setBackground(ctx.getResources().getDrawable(R.drawable.round_corner_blue));
                tvPagaraRequerido.setEnabled(true);
                llPagaraRequerido.setVisibility(View.VISIBLE);
                llFechaDeposito.setVisibility(View.VISIBLE);
                llMontoPagoRequerido.setVisibility(View.VISIBLE);
                llImprimirRecibo.setVisibility(View.GONE);
                llFotoGaleria.setVisibility(View.VISIBLE);
                llFolioRecibo.setVisibility(View.GONE);
                llGerente.setVisibility(View.VISIBLE);
                break;
            case 15: // Oxxo Deprecated
                if (byteEvidencia != null)
                    tvFotoGaleria.setError(null);
                else
                    tvFotoGaleria.setError("");

                if (tvFechaDeposito.getText().toString().isEmpty())
                    tvFechaDeposito.setError(getResources().getString(R.string.campo_requerido));
                ibGaleria.setEnabled(true);
                ibGaleria.setBackground(ctx.getResources().getDrawable(R.drawable.round_corner_blue));
                llPagaraRequerido.setVisibility(View.VISIBLE);
                llFechaDeposito.setVisibility(View.VISIBLE);
                llMontoPagoRequerido.setVisibility(View.VISIBLE);
                tvPagaraRequerido.setText(_confirmacion[0]);
                tvPagaraRequerido.setEnabled(false);
                etPagoRealizado.setText(parent.monto_requerido);
                SelectPagoRequerido(0);
                llImprimirRecibo.setVisibility(View.GONE);
                llFotoGaleria.setVisibility(View.VISIBLE);
                llGerente.setVisibility(View.VISIBLE);
                break;
            case 7: //Sidert
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
                llFechaDeposito.setVisibility(View.VISIBLE);
                tvPagaraRequerido.setEnabled(true);
                llMontoPagoRequerido.setVisibility(View.VISIBLE);
                llImprimirRecibo.setVisibility(View.VISIBLE);
                tvImprimirRecibo.setText(_imprimir[1]);
                tvImprimirRecibo.setEnabled(false);
                SelectImprimirRecibos(Miscellaneous.Impresion(tvImprimirRecibo));
                llFotoGaleria.setVisibility(View.VISIBLE);
                llGerente.setVisibility(View.VISIBLE);
                break;
            case 6: // Efectivo
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
                tvPagaraRequerido.setEnabled(true);
                llMontoPagoRequerido.setVisibility(View.VISIBLE);
                llImprimirRecibo.setVisibility(View.VISIBLE);
                tvImprimirRecibo.setText("");
                tvImprimirRecibo.setEnabled(true);
                SelectImprimirRecibos(-1);
                llFotoGaleria.setVisibility(View.VISIBLE);
                llGerente.setVisibility(View.VISIBLE);
                break;
            default: //Sin seleccionar una opción o cualquier otro valor
                tvMedioPago.setError("");
                tvPagaraRequerido.setText("");
                tvImprimirRecibo.setText("");
                SelectImprimirRecibos(-1);
                SelectPagoRequerido(-1);
                ivEvidencia.setVisibility(View.GONE);
                tvPagaraRequerido.setEnabled(true);
                llPagaraRequerido.setVisibility(View.GONE);
                llFechaDeposito.setVisibility(View.GONE);
                llFotoGaleria.setVisibility(View.GONE);
                llImprimirRecibo.setVisibility(View.GONE);
                llFolioRecibo.setVisibility(View.GONE);
                llGerente.setVisibility(View.GONE);
                break;
        }
    }
    private void SelectPagoRequerido (int pos){
        switch (pos){
            case 0: // Si pagará requerido
                etPagoRealizado.setText(parent.monto_requerido);
                etPagoRealizado.setEnabled(false);
                llMontoPagoRealizado.setVisibility(View.VISIBLE);
                break;
            case 1: // No pagará requerido
                etPagoRealizado.setEnabled(true);
                llMontoPagoRealizado.setVisibility(View.VISIBLE);
                break;
            default:
                etPagoRealizado.setText(parent.monto_requerido);
                etPagoRealizado.setEnabled(false);
                llMontoPagoRealizado.setVisibility(View.GONE);
                llImprimirRecibo.setVisibility(View.GONE);
                llFolioRecibo.setVisibility(View.GONE);
                break;
        }
    }
    private void SelectImprimirRecibos(int pos){
        switch (pos){
            case 0: // Imprimir Recibos
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
            case 1: //No cuenta con bateria la impresora
                etFolioRecibo.setError(getResources().getString(R.string.campo_requerido));
                tvImprimirRecibo.setError(null);
                llFolioRecibo.setVisibility(View.VISIBLE);
                etFolioRecibo.setText("");
                etFolioRecibo.setEnabled(true);
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
    private void SelectMotivoNoPago (int pos){
        tvMotivoNoPago.setError(null);
        switch (pos){
            case 0: // Negación de pago
            case 2: // Otro
                llDefuncion.setVisibility(View.GONE);
                llPromesaPago.setVisibility(View.GONE);
                break;
            case 1: //Fallecimiento
                tvFechaDefuncion.setError("");
                if (!tvFechaDefuncion.getText().toString().trim().isEmpty())
                    tvFechaDefuncion.setError(null);
                llDefuncion.setVisibility(View.VISIBLE);

                tvFechaPromesaPago.setText("");
                Update("fecha_monto_promesa", "");
                etMontoPromesa.setText("");
                Update("monto_promesa", "");
                llPromesaPago.setVisibility(View.GONE);
                break;
            case 3: //Monto promesa
                tvFechaDefuncion.setText("");
                Update("fecha_fallecimiento", "");

                tvFechaPromesaPago.setError("");
                if (!tvFechaPromesaPago.getText().toString().trim().isEmpty())
                    tvFechaPromesaPago.setError(null);

                etMontoPromesa.setError("Este campo es requerido");
                if (!etMontoPromesa.getText().toString().trim().isEmpty())
                    etMontoPromesa.setError(null);

                llDefuncion.setVisibility(View.GONE);
                llPromesaPago.setVisibility(View.VISIBLE);
                break;
            default: //Sin seleccionar una opción o cualquier otro valor
                tvMotivoNoPago.setError("");
                llDefuncion.setVisibility(View.GONE);
                llPromesaPago.setVisibility(View.GONE);
                break;
        }
    }
    private void SelectEstaGerente (int pos){
        switch (pos){
            case 0: // Si está el gerente
                if (ivFirma.getVisibility() == View.VISIBLE && byteFirma != null)
                    tvFirma.setError(null);
                else
                    tvFirma.setError("");
                llFirma.setVisibility(View.VISIBLE);
                break;
            case 1: // No está el gerente
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

    private void init() {
        setHasOptionsMenu(true);
        parent.getSupportActionBar().show();
        tvMontoPagoRequerido.setText(String.valueOf(df.format(Double.parseDouble(parent.monto_requerido))));
        if (!parent.id_respuesta.isEmpty()){
            Cursor row;

            row = dBhelper.getRecords(TBL_RESPUESTAS_IND_V_T, " WHERE _id = ? AND id_prestamo = ?", "", new String[]{parent.id_respuesta, parent.id_prestamo});

            Log.e("CointVencida", ": "+row.getCount());
            if (row.getCount() > 0){
                row.moveToFirst();

                fechaIni = row.getString(23);
                res_impresion = row.getInt(27);

                if (!row.getString(2).isEmpty() && !row.getString(3).isEmpty()){
                    tvmapa.setError(null);
                    mapView.setVisibility(View.VISIBLE);
                    ColocarUbicacionGestion(row.getDouble(2), row.getDouble(3));
                }

                if (!row.getString(4).isEmpty()){ //CONTACTO
                    tvContacto.setText(row.getString(4));
                    switch (Miscellaneous.ContactoCliente(tvContacto)) {
                        case 0: //SI CONTACTO
                            SelectContactoCliente(Miscellaneous.ContactoCliente(tvContacto));

                            if (!row.getString(6).isEmpty()){//ACTUALIZAR TELEFONO
                                tvActualizarTelefono.setText(row.getString(6));
                                if (Miscellaneous.ActualizarTelefono(tvActualizarTelefono) == 0){
                                    if (!row.getString(7).isEmpty()){//NUEVO TELEFONO
                                        etActualizarTelefono.setText(row.getString(7));
                                        etActualizarTelefono.setError(null);
                                        etActualizarTelefono.setVisibility(View.VISIBLE);
                                    }
                                }
                            }

                            if (!row.getString(8).isEmpty()){//RESULTADO PAGO
                                tvResultadoGestion.setText(row.getString(8));
                                SelectResultadoGestion(Miscellaneous.ResultadoGestion(tvResultadoGestion));
                                switch (Miscellaneous.ResultadoGestion(tvResultadoGestion)){
                                    case 1: //No Pago
                                        tvMotivoNoPago.setText(row.getString(9));
                                        SelectMotivoNoPago(Miscellaneous.MotivoNoPago(tvMotivoNoPago));
                                        switch (Miscellaneous.MotivoNoPago(tvMotivoNoPago)){
                                            case 1:
                                                tvFechaDefuncion.setText(row.getString(10));
                                                break;
                                            case 3:
                                                tvFechaPromesaPago.setText(row.getString(11));
                                                etMontoPromesa.setText(row.getString(12));
                                                break;
                                        }

                                        if (!row.getString(5).isEmpty()){//COMENTARIO
                                            etComentario.setText(row.getString(5));
                                            etComentario.setVisibility(View.VISIBLE);
                                            etComentario.setError(null);
                                        }

                                        if (!row.getString(19).isEmpty() && !row.getString(20).isEmpty()){//FACHADA
                                            File fachadaFile = new File(ROOT_PATH + "Fachada/"+row.getString(19));
                                            Uri uriFachada = Uri.fromFile(fachadaFile);
                                            Glide.with(ctx).load(uriFachada).into(ivFachada);
                                            ibFachada.setVisibility(View.GONE);
                                            ivFachada.setVisibility(View.VISIBLE);
                                            byteEvidencia = Miscellaneous.getBytesUri(ctx, uriFachada, 1);
                                            tvFachada.setError(null);
                                        }

                                        tvGerente.setVisibility(View.VISIBLE);
                                        if (!row.getString(21).isEmpty()){//ESTA GERENTE
                                            tvGerente.setText(row.getString(21));
                                            SelectEstaGerente(Miscellaneous.Gerente(tvGerente));

                                            if (Miscellaneous.Gerente(tvGerente) == 0){

                                                if (!row.getString(22).isEmpty()){//FIRMA
                                                    File firmaFile = new File(ROOT_PATH + "Firma/"+row.getString(22));
                                                    Uri uriFirma = Uri.fromFile(firmaFile);
                                                    Glide.with(ctx).load(uriFirma).into(ivFirma);
                                                    ibFirma.setVisibility(View.GONE);
                                                    ivFirma.setVisibility(View.VISIBLE);
                                                    byteFirma = Miscellaneous.getBytesUri(ctx, uriFirma, 1);
                                                    tvFirma.setError(null);
                                                }
                                            }
                                        }
                                        break;
                                    case 0: // Si Pago
                                        if (!row.getString(13).isEmpty()){//MEDIO PAGO
                                            tvMedioPago.setText(row.getString(13));
                                            medio_pago_anterio = Miscellaneous.MedioPago(tvMedioPago);
                                            SelectMedioPago(Miscellaneous.MedioPago(tvMedioPago));
                                            if (!row.getString(15).isEmpty()){//PAGARA REQUERIDO

                                                tvPagaraRequerido.setText(row.getString(15));
                                                SelectPagoRequerido(Miscellaneous.PagoRequerido(tvPagaraRequerido));
                                                etPagoRealizado.setText(row.getString(16));
                                            }

                                            if (!row.getString(14).isEmpty()){//FECHA DEPOSITO
                                                tvFechaDeposito.setText(row.getString(14));
                                                tvFechaDeposito.setError(null);
                                            }

                                            if (Miscellaneous.MedioPago(tvMedioPago) == 6){ //EFECTIVO
                                                if (!row.getString(17).isEmpty()){//IMPRIMIRA RECIBOS
                                                    tvImprimirRecibo.setText(row.getString(17));
                                                    SelectImprimirRecibos(Miscellaneous.Impresion(tvImprimirRecibo));
                                                    etFolioRecibo.setEnabled(true);

                                                    if (Miscellaneous.Impresion(tvImprimirRecibo) == 0){ //SI IMPRIMIRA RECIBOS

                                                        if (!row.getString(18).isEmpty()){//FOLIO
                                                            etPagoRealizado.setEnabled(false);
                                                            etPagoRealizado.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));

                                                            tvContacto.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
                                                            tvContacto.setEnabled(false);
                                                            tvResultadoGestion.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
                                                            tvResultadoGestion.setEnabled(false);
                                                            tvMedioPago.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
                                                            tvMedioPago.setEnabled(false);
                                                            tvPagaraRequerido.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
                                                            tvPagaraRequerido.setEnabled(false);
                                                            tvImprimirRecibo.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
                                                            tvImprimirRecibo.setEnabled(false);
                                                            ibImprimir.setVisibility(View.VISIBLE);

                                                            etFolioRecibo.setEnabled(false);
                                                            llFolioRecibo.setVisibility(View.VISIBLE);
                                                            etFolioRecibo.setText(row.getString(18));
                                                            etFolioRecibo.setError(null);
                                                            folio_impreso = row.getString(18);
                                                        }
                                                        else {
                                                            ibImprimir.setVisibility(View.VISIBLE);
                                                        }
                                                    }
                                                    else{
                                                        ibImprimir.setVisibility(View.GONE);
                                                        llFolioRecibo.setVisibility(View.VISIBLE);
                                                        etFolioRecibo.setText(row.getString(18));
                                                        etFolioRecibo.setError(null);
                                                    }
                                                }
                                            }
                                            else if (Miscellaneous.MedioPago(tvMedioPago) == 7){
                                                ibImprimir.setVisibility(View.GONE);
                                                llFolioRecibo.setVisibility(View.VISIBLE);
                                                etFolioRecibo.setText(row.getString(18));
                                                etFolioRecibo.setError(null);
                                            }

                                            if (!row.getString(19).isEmpty() && !row.getString(20).isEmpty()){//FOTOGRAFIA O GALERIA
                                                File evidenciaFile = new File(ROOT_PATH + "Evidencia/"+row.getString(19));
                                                Uri uriEvidencia = Uri.fromFile(evidenciaFile);
                                                Glide.with(ctx).load(uriEvidencia).centerCrop().into(ivEvidencia);
                                                ibFoto.setVisibility(View.GONE);
                                                ibGaleria.setVisibility(View.GONE);
                                                ivEvidencia.setVisibility(View.VISIBLE);
                                                byteEvidencia = Miscellaneous.getBytesUri(ctx, uriEvidencia, 1);
                                                tvFotoGaleria.setError(null);
                                            }

                                            if (!row.getString(21).isEmpty()){//ESTA GERENTE
                                                tvGerente.setText(row.getString(21));

                                                SelectEstaGerente(Miscellaneous.Gerente(tvGerente));
                                                if (Miscellaneous.Gerente(tvGerente) == 0){//SI ESTA GERENTE
                                                    if (!row.getString(22).isEmpty()){//FIRMA
                                                        File firmaFile = new File(ROOT_PATH + "Firma/"+row.getString(22));
                                                        Uri uriFirma = Uri.fromFile(firmaFile);
                                                        Glide.with(ctx).load(uriFirma).into(ivFirma);
                                                        ibFirma.setVisibility(View.GONE);
                                                        ivFirma.setVisibility(View.VISIBLE);
                                                        byteFirma = Miscellaneous.getBytesUri(ctx, uriFirma, 1);
                                                        tvFirma.setError(null);
                                                    }
                                                }
                                            }
                                        }
                                        break;
                                }
                            }
                            break;
                        case 1: //NO CONTACTO
                            SelectContactoCliente(Miscellaneous.ContactoCliente(tvContacto));
                            if (!row.getString(5).isEmpty()){//COMENTARIO
                                etComentario.setText(row.getString(5));
                                etComentario.setVisibility(View.VISIBLE);
                                etComentario.setError(null);
                            }

                            if (!row.getString(19).isEmpty() && !row.getString(20).isEmpty()){
                                File fachadaFile = new File(ROOT_PATH + "Fachada/"+row.getString(19));
                                Uri uriFachada = Uri.fromFile(fachadaFile);
                                Glide.with(ctx).load(uriFachada).into(ivFachada);
                                ibFachada.setVisibility(View.GONE);
                                ivFachada.setVisibility(View.VISIBLE);
                                byteEvidencia = Miscellaneous.getBytesUri(ctx, uriFachada, 1);
                                tvFachada.setError(null);
                            }

                            tvGerente.setVisibility(View.VISIBLE);
                            if (!row.getString(21).isEmpty()){//ESTA GERENTE
                                tvGerente.setText(row.getString(21));
                                SelectEstaGerente(Miscellaneous.Gerente(tvGerente));

                                if (Miscellaneous.Gerente(tvGerente) == 0){
                                    if (!row.getString(22).isEmpty()){
                                        File firmaFile = new File(ROOT_PATH + "Firma/"+row.getString(22));
                                        Uri uriFirma = Uri.fromFile(firmaFile);
                                        Glide.with(ctx).load(uriFirma).into(ivFirma);
                                        ibFirma.setVisibility(View.GONE);
                                        ivFirma.setVisibility(View.VISIBLE);
                                        byteFirma = Miscellaneous.getBytesUri(ctx, uriFirma, 1);
                                        tvFirma.setError(null);
                                    }
                                }
                            }
                            break;
                        case 2:
                            SelectContactoCliente(Miscellaneous.ContactoCliente(tvContacto));

                            if (!row.getString(5).isEmpty()){//COMENTARIO
                                etComentario.setText(row.getString(5));
                                etComentario.setVisibility(View.VISIBLE);
                                etComentario.setError(null);
                            }

                            tvGerente.setVisibility(View.VISIBLE);
                            if (!row.getString(21).isEmpty()){//ESTA GERENTE
                                tvGerente.setText(row.getString(21));
                                SelectEstaGerente(Miscellaneous.Gerente(tvGerente));
                                if (Miscellaneous.Gerente(tvGerente) == 0){
                                    if (!row.getString(22).isEmpty()){
                                        File firmaFile = new File(ROOT_PATH + "Firma/"+row.getString(22));
                                        Uri uriFirma = Uri.fromFile(firmaFile);
                                        Glide.with(ctx).load(uriFirma).into(ivFirma);
                                        ibFirma.setVisibility(View.GONE);
                                        ivFirma.setVisibility(View.VISIBLE);
                                        byteFirma = Miscellaneous.getBytesUri(ctx, uriFirma, 1);
                                        tvFirma.setError(null);
                                    }
                                }
                            }
                            break;

                    }
                }

            }
        }
    }

    private void Update(String key, String value) {
        Log.e("update", key+": "+value);
        ContentValues cv = new ContentValues();
        cv.put(key, value);
        db.update(TBL_RESPUESTAS_IND_V_T, cv, "id_prestamo = ? AND _id = ?" ,new String[]{parent.id_prestamo, parent.id_respuesta});
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case REQUEST_CODE_FIRMA:
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
                            Update("firma", Miscellaneous.save(byteFirma, 3));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                break;
            case REQUEST_CODE_CAMARA_FACHADA:
                if (resultCode == Activity.RESULT_OK){
                    if (data != null){
                        ibFachada.setVisibility(View.GONE);
                        tvFachada.setError(null);
                        ivFachada.setVisibility(View.VISIBLE);
                        byteEvidencia = data.getByteArrayExtra(PICTURE);
                        Glide.with(ctx).load(byteEvidencia).centerCrop().into(ivFachada);

                        try {
                            Update("evidencia", Miscellaneous.save(byteEvidencia, 1));
                            Update("tipo_imagen", "FACHADA");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                break;
            case REQUEST_CODE_IMPRESORA:
                if (resultCode == Activity.RESULT_OK){
                    if (data != null){
                        Toast.makeText(ctx, data.getStringExtra(MESSAGE), Toast.LENGTH_SHORT).show();
                        if(data.getIntExtra(RES_PRINT,0) == 1 || data.getIntExtra(RES_PRINT,0) == 2){
                            res_impresion = data.getIntExtra(RES_PRINT, 0);
                            folio_impreso = "CV"+session.getUser().get(0) + "-" + String.valueOf(data.getIntExtra(FOLIO,0));
                            etFolioRecibo.setText(folio_impreso);
                            etPagoRealizado.setEnabled(false);
                            tvImprimirRecibo.setError(null);
                            llFolioRecibo.setVisibility(View.VISIBLE);

                            DisableFields();
                            Update("folio", folio_impreso);
                        }
                    }
                }
                break;
            case REQUEST_CODE_GALERIA:
                if (data != null){
                    try {
                        imageUri = data.getData();

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
                        newBitMap.compress(Bitmap.CompressFormat.JPEG, 70, baos);

                        byteEvidencia = baos.toByteArray();

                        Glide.with(ctx).load(baos.toByteArray()).centerCrop().into(ivEvidencia);

                        try {
                            Update("evidencia", Miscellaneous.save(byteEvidencia, 2));
                            Update("tipo_imagen", "GALERIA");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
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
            case CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE:
                    if (data != null) {
                        try {
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
                            newBitMap.compress(Bitmap.CompressFormat.JPEG, 70, baos);

                            byteEvidencia = baos.toByteArray();

                            Glide.with(ctx).load(baos.toByteArray()).centerCrop().into(ivEvidencia);

                            try {
                                Update("evidencia", Miscellaneous.save(byteEvidencia, 2));
                                Update("tipo_imagen", "GALERIA");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
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
            case REQUEST_CODE_CAMARA_TICKET:
                if (resultCode == Activity.RESULT_OK){
                    if (data != null){
                        ibFoto.setVisibility(View.GONE);
                        ibGaleria.setVisibility(View.GONE);
                        tvFotoGaleria.setError(null);
                        ivEvidencia.setVisibility(View.VISIBLE);
                        byteEvidencia = data.getByteArrayExtra(PICTURE);
                        Glide.with(ctx).load(byteEvidencia).centerCrop().into(ivEvidencia);
                        try {
                            Update("evidencia", Miscellaneous.save(byteEvidencia, 2));
                            Update("tipo_imagen", "FOTOGRAFIA");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                }
                break;
            case 123: //Fecha defuncion
                if (resultCode == 321){
                    if (data != null){
                        tvFechaDefuncion.setError(null);
                        tvFechaDefuncion.setText(data.getStringExtra(DATE));
                        Update("fecha_fallecimiento", tvFechaDefuncion.getText().toString());
                    }
                }
                break;
            case 812: //Fecha Deposito
                if (resultCode == 321){
                    if (data != null){
                        tvFechaDeposito.setError(null);
                        tvFechaDeposito.setText(data.getStringExtra(DATE));
                        Update("fecha_pago", tvFechaDeposito.getText().toString());

                    }
                }
                break;
            case 213: //Fecha de promesa de pago
                if (resultCode == 312){
                    if (data != null){
                        tvFechaPromesaPago.setError(null);
                        tvFechaPromesaPago.setText(data.getStringExtra(DATE));
                        Update("fecha_monto_promesa", tvFechaPromesaPago.getText().toString());

                    }
                }
                break;
            case REQUEST_CODE_PREVIEW:
                if (resultCode == Activity.RESULT_OK){
                    if (data != null){

                        if (data.hasExtra(UBICACION) && !data.getBooleanExtra(UBICACION, true)) {
                            String sqlTraker = "SELECT latitud, longitud FROM " + TBL_TRACKER_ASESOR_T + " WHERE created_at >= Datetime(?) AND created_at <= Datetime(?) ORDER BY created_at DESC";
                            Cursor rowTraker = db.rawQuery(sqlTraker, new String[]{fechaIni.substring(0, 10) + " 08:00:00", fechaIni});
                            if (rowTraker.getCount() > 0) {
                                rowTraker.moveToFirst();
                                ContentValues cv = new ContentValues();
                                cv.put("latitud", rowTraker.getString(0));
                                cv.put("longitud", rowTraker.getString(1));
                                db.update(TBL_RESPUESTAS_IND_V_T, cv, "_id = ?", new String[]{parent.id_respuesta});
                            }
                        }

                        ContentValues cv = new ContentValues();
                        if (data.hasExtra(ESTATUS)) {
                            cv.put("estatus_pago", data.getStringExtra(ESTATUS));
                            cv.put("saldo_corte", data.getStringExtra(SALDO_CORTE));
                            cv.put("saldo_actual", data.getStringExtra(SALDO_ACTUAL));
                        }
                        cv.put("dias_atraso", Miscellaneous.GetDiasAtraso(parent.fecha_establecida));
                        cv.put("fecha_fin", data.getStringExtra(FECHA_FIN));
                        cv.put("estatus", "1");

                        Log.e("idPresta", parent.id_prestamo);
                        db.update(TBL_RESPUESTAS_IND_V_T, cv, "id_prestamo = ? AND _id = ?" ,new String[]{parent.id_prestamo, parent.id_respuesta});

                        String sql = "SELECT * FROM " + TBL_RESPUESTAS_IND_V_T + " WHERE id_prestamo = ? AND contacto = ? AND resultado_gestion = ? AND estatus IN (?,?)";
                        Cursor row = db.rawQuery(sql, new String[]{parent.id_prestamo, "SI", "PAGO", "1", "2"});

                        if (row.getCount() > 0){
                            row.moveToFirst();
                            String sqlAmortiz = "SELECT _id, total, total_pagado, pagado, fecha, numero FROM " + TBL_AMORTIZACIONES_T + " WHERE id_prestamo = ? AND CAST(total AS DOUBLE) > CAST(total_pagado AS DOUBLE) ORDER BY numero ASC";
                            Cursor row_amortiz = db.rawQuery(sqlAmortiz, new String[]{parent.id_prestamo});
                            if (row_amortiz.getCount() > 0){
                                row_amortiz.moveToFirst();
                                Double abono = 0.0;
                                Log.e("pagoEtrealizado", etPagoRealizado.getText().toString().trim()+ " pago");
                                if (!etPagoRealizado.getText().toString().trim().isEmpty() && tvResultadoGestion.getText().toString().trim().toUpperCase().equals("PAGO"))
                                    abono = Double.parseDouble(etPagoRealizado.getText().toString().trim().replace(",", ""));
                                for (int i = 0; i < row_amortiz.getCount(); i++){

                                    Double pendiente = row_amortiz.getDouble(1) - row_amortiz.getDouble(2);

                                    if (abono > pendiente){
                                        ContentValues cv_amortiz = new ContentValues();
                                        cv_amortiz.put("total_pagado", row_amortiz.getString(1));
                                        cv_amortiz.put("pagado", "PAGADO");
                                        cv_amortiz.put("dias_atraso", Miscellaneous.GetDiasAtraso(row_amortiz.getString(4)));
                                        db.update(TBL_AMORTIZACIONES_T, cv_amortiz, "id_prestamo = ? AND numero = ?", new String[]{parent.id_prestamo, row_amortiz.getString(5)});
                                        abono = abono - pendiente;
                                    }
                                    else if (abono == pendiente){
                                        ContentValues cv_amortiz = new ContentValues();
                                        cv_amortiz.put("total_pagado", row_amortiz.getString(1));
                                        cv_amortiz.put("pagado", "PAGADO");
                                        cv_amortiz.put("dias_atraso", Miscellaneous.GetDiasAtraso(row_amortiz.getString(4)));
                                        db.update(TBL_AMORTIZACIONES_T, cv_amortiz, "id_prestamo = ? AND numero = ?", new String[]{parent.id_prestamo, row_amortiz.getString(5)});
                                        abono = 0.0;
                                    }
                                    else if (abono > 0 && abono < pendiente){
                                        ContentValues cv_amortiz = new ContentValues();
                                        cv_amortiz.put("total_pagado", (row_amortiz.getDouble(2) + abono));
                                        cv_amortiz.put("pagado", "PARCIAL");
                                        abono = 0.0;
                                        cv_amortiz.put("dias_atraso", Miscellaneous.GetDiasAtraso(row_amortiz.getString(4)));
                                        db.update(TBL_AMORTIZACIONES_T, cv_amortiz, "id_prestamo = ? AND numero = ?", new String[]{parent.id_prestamo, row_amortiz.getString(5)});

                                    }
                                    else
                                        break;

                                    row_amortiz.moveToNext();
                                }

                            }
                            row_amortiz.close();

                            sqlAmortiz = "SELECT SUM(a.total_pagado) AS suma_pagos, p.monto_total FROM " + TBL_AMORTIZACIONES_T + " AS a INNER JOIN "+TBL_PRESTAMOS_IND_T+" AS p ON p.id_prestamo = a.id_prestamo WHERE a.id_prestamo = ?";
                            row_amortiz = db.rawQuery(sqlAmortiz, new String[]{parent.id_prestamo});

                            if (row_amortiz.getCount() > 0){
                                row_amortiz.moveToFirst();
                                if (row_amortiz.getDouble(0) >= row_amortiz.getDouble(1)){
                                    ContentValues c = new ContentValues();
                                    c.put("pagada", 1);
                                    db.update(TBL_PRESTAMOS_IND_T, c, "id_prestamo = ?", new String[]{parent.id_prestamo});
                                }

                            }
                            row_amortiz.close();
                        }
                        row.close();

                        HashMap<Integer, String> values = new HashMap();
                        values.put(0, parent.id_respuesta);
                        values.put(1, data.getStringExtra(NOMBRE));
                        values.put(2, parent.nombre);
                        values.put(3, "1");
                        dBhelper.saveResumenGestion(db, values);

                        Toast.makeText(ctx, "Ficha Guardada con éxito.", Toast.LENGTH_SHORT).show();

                        Servicios_Sincronizado ss = new Servicios_Sincronizado();
                        ss.SaveRespuestaGestion(ctx, false);

                        Uri imgUri = Uri.parse(data.getStringExtra(SCREEN_SHOT));
                        Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
                        whatsappIntent.setType("text/plain");
                        whatsappIntent.setPackage("com.whatsapp");
                        whatsappIntent.putExtra(Intent.EXTRA_TEXT, "Le comparto el resumen de la gestión del cliente " + parent.nombre);
                        whatsappIntent.putExtra(Intent.EXTRA_STREAM, imgUri);
                        whatsappIntent.setType("image/jpeg");
                        whatsappIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

                        try {
                            ctx.startActivity(whatsappIntent);
                        } catch (android.content.ActivityNotFoundException ex) {
                            Toast.makeText(ctx, "No cuenta con Whatsapp", Toast.LENGTH_SHORT).show();
                        }

                        parent.finish();
                    }
                }
                break;
        }
    }

    private void GuardarGestion(){
        Validator validator = new Validator();
        ValidatorTextView validatorTV = new ValidatorTextView();
        Miscellaneous m = new Miscellaneous();
        Bundle b = new Bundle();

        b.putString(NOMBRE, parent.nombre);
        if (latLngGestion != null || (!parent.latitud.trim().isEmpty() && !parent.longitud.trim().isEmpty())){
            if (latLngGestion != null) {
                b.putDouble(LATITUD, latLngGestion.latitude);
                b.putDouble(LONGITUD, latLngGestion.longitude);
            }
            else {
                b.putDouble(LATITUD, 0);
                b.putDouble(LONGITUD, 0);
            }
            if (m.ContactoCliente(tvContacto) == 0) { //Si Contacto cliente
                b.putString(CONTACTO, tvContacto.getText().toString());
                if (!tvActualizarTelefono.getText().toString().isEmpty()){
                    if ((m.ActualizarTelefono(tvActualizarTelefono) == 0 && !validator.validate(etActualizarTelefono, new String[]{validator.REQUIRED, validator.PHONE})) || m.ActualizarTelefono(tvActualizarTelefono) == 1){
                        if (m.ActualizarTelefono(tvActualizarTelefono) == 0){
                            b.putString(ACTUALIZAR_TELEFONO, "SI");
                            b.putString(NUEVO_TELEFONO, etActualizarTelefono.getText().toString().trim());
                        }else {
                            b.putString(ACTUALIZAR_TELEFONO, "NO");
                        }
                        if (m.ResultadoGestion(tvResultadoGestion) == 0){ // Si pago
                            b.putString(RESULTADO_PAGO, "PAGO");
                            if (m.MedioPago(tvMedioPago) >= 0 && m.MedioPago(tvMedioPago) < 6 || m.MedioPago(tvMedioPago) == 7 || m.MedioPago(tvMedioPago) == 8 ){ // Medio de pago Bancos y Oxxo
                                b.putString(MEDIO_PAGO, tvMedioPago.getText().toString());
                                if (!tvFechaDeposito.getText().toString().trim().isEmpty()){ //Fecha de deposito capturada
                                    b.putString(FECHA_DEPOSITO, tvFechaDeposito.getText().toString().trim());
                                    if (!tvPagaraRequerido.getText().toString().isEmpty()){ //Selecionó que pagará requerido o no requerido
                                        b.putString(PAGO_REQUERIDO, "SI");
                                        if (!etPagoRealizado.getText().toString().trim().isEmpty() && Double.parseDouble(etPagoRealizado.getText().toString().trim().replace(",","")) > 0){ //El pago realizado es mayor a cero
                                            b.putDouble(SALDO_CORTE, parent.saldo_corte);
                                            b.putDouble(MONTO_REQUERIDO, Double.parseDouble(parent.monto_requerido));
                                            b.putString(PAGO_REALIZADO, etPagoRealizado.getText().toString().trim().replace(",",""));
                                            //-------------------------------------------------------
                                            if (m.MedioPago(tvMedioPago) == 7) {
                                                if (m.Impresion(tvImprimirRecibo) == 1) { //No imprimirá recibos
                                                    if (!etFolioRecibo.getText().toString().trim().isEmpty()) {
                                                        b.putString(IMPRESORA, tvImprimirRecibo.getText().toString());
                                                        b.putString(FOLIO_TICKET, etFolioRecibo.getText().toString().trim());
                                                    }
                                                    else
                                                        Toast.makeText(ctx, "No ha capturado el folio del recibo manual", Toast.LENGTH_SHORT).show();
                                                }
                                            }

                                            //----------------------------------------------------
                                            if (byteEvidencia != null){ //Ha capturado una evidencia (Fotografía al ticket)
                                                if (m.Gerente(tvGerente) == 0) { //Selecciono que si está el gerente
                                                    if (byteFirma != null) { //Capturó una firma
                                                        b.putByteArray(EVIDENCIA, byteEvidencia);
                                                        b.putString(GERENTE, "SI");
                                                        b.putByteArray(FIRMA, byteFirma);
                                                        b.putBoolean(TERMINADO, true);
                                                    } else //No ha capturado la firma
                                                        Toast.makeText(ctx, "Capture la firma del gerente", Toast.LENGTH_SHORT).show();
                                                } else if (m.Gerente(tvGerente) == 1) { //No se encuentra el Gerente
                                                    b.putByteArray(EVIDENCIA, byteEvidencia);
                                                    b.putString(GERENTE, "NO");
                                                    b.putBoolean(TERMINADO, true);
                                                } else //No ha seleccionado si está el gerente
                                                    Toast.makeText(ctx, "Confirme si el gerente está con usted", Toast.LENGTH_SHORT).show();
                                            }
                                            else //No ha capturado fotografía evidencia
                                                Toast.makeText(ctx, "No ha capturado una fotografía al ticket", Toast.LENGTH_SHORT).show();
                                        }
                                        else //El monto ingresado es igual a cero
                                            Toast.makeText(ctx, "No se pueden capturar montos iguales a cero", Toast.LENGTH_SHORT).show();
                                    }
                                    else //No ha seleccionado si pagará el pago requerido
                                        Toast.makeText(ctx, "No ha seleccionado si se pagará el requerido", Toast.LENGTH_SHORT).show();
                                }
                                else { //No ha seleccionado la fecha de depostio
                                    tvFechaDeposito.setError("");
                                    Toast.makeText(ctx, "No ha seleccionado la fecha de deposito", Toast.LENGTH_SHORT).show();
                                }
                            }
                            else if (m.MedioPago(tvMedioPago) == 6){ //Efectivo
                                b.putString(MEDIO_PAGO, tvMedioPago.getText().toString());
                                if (!tvPagaraRequerido.getText().toString().trim().isEmpty()){ //Selecionó que pagará requerido o no requerido
                                    b.putString(PAGO_REQUERIDO, "SI");
                                    if (!etPagoRealizado.getText().toString().trim().isEmpty() && Double.parseDouble(etPagoRealizado.getText().toString().replace(",","")) > 0){ //El pago realizado es mayor a cero
                                        b.putDouble(SALDO_CORTE, parent.saldo_corte);
                                        b.putDouble(MONTO_REQUERIDO, Double.parseDouble(parent.monto_requerido));
                                        b.putString(PAGO_REALIZADO, etPagoRealizado.getText().toString().trim().replace(",",""));
                                        if (m.Impresion(tvImprimirRecibo) == 0){ //Si imprimirá recibos
                                            if (!etFolioRecibo.getText().toString().trim().isEmpty()){
                                                b.putString(IMPRESORA, "SI");
                                                b.putString(FOLIO_TICKET, etFolioRecibo.getText().toString().trim());
                                                if (byteEvidencia != null){ //Ha capturado una evidencia (Fotografía al ticket)
                                                    b.putByteArray(EVIDENCIA, byteEvidencia);
                                                    if (m.Gerente(tvGerente) == 0) { //Selecciono que si está el gerente
                                                        if (byteFirma != null) { //Capturó una firma
                                                            b.putString(GERENTE, "SI");
                                                            b.putByteArray(FIRMA, byteFirma);
                                                            b.putBoolean(TERMINADO, true);
                                                        } else //No ha capturado la firma
                                                            Toast.makeText(ctx, "Capture la firma del gerente", Toast.LENGTH_SHORT).show();
                                                    } else if (m.Gerente(tvGerente) == 1) { //No se encuentra el Gerente
                                                        b.putString(GERENTE, "NO");
                                                        b.putBoolean(TERMINADO, true);
                                                    } else //No ha seleccionado si está el gerente
                                                        Toast.makeText(ctx, "Confirme si el gerente está con usted", Toast.LENGTH_SHORT).show();
                                                }
                                                else //No ha capturado fotografía evidencia
                                                    Toast.makeText(ctx, "No ha capturado una fotografía al ticket", Toast.LENGTH_SHORT).show();
                                            }
                                            else //No ha impreso ningun ticket
                                                Toast.makeText(ctx,"No ha realizado nignuna impresión", Toast.LENGTH_SHORT).show();
                                        }
                                        else if (m.Impresion(tvImprimirRecibo) == 1){ //No imprimirá recibos
                                            if (!etFolioRecibo.getText().toString().trim().isEmpty()){
                                                b.putString(IMPRESORA, "NO CUENTA CON BATERIA");
                                                b.putString(FOLIO_TICKET, etFolioRecibo.getText().toString().trim());
                                                if (byteEvidencia != null){ //Ha capturado una evidencia (Fotografía al ticket)
                                                    b.putByteArray(EVIDENCIA, byteEvidencia);
                                                    if (m.Gerente(tvGerente) == 0) { //Selecciono que si está el gerente
                                                        if (byteFirma != null) { //Capturó una firma
                                                            b.putString(GERENTE, "SI");
                                                            b.putByteArray(FIRMA, byteFirma);
                                                            b.putBoolean(TERMINADO, true);
                                                        } else //No ha capturado la firma
                                                            Toast.makeText(ctx, "Capture la firma del gerente", Toast.LENGTH_SHORT).show();
                                                    } else if (m.Gerente(tvGerente) == 1) { //No se encuentra el Gerente
                                                        b.putString(GERENTE, "NO");
                                                        b.putBoolean(TERMINADO, true);
                                                    } else //No ha seleccionado si está el gerente
                                                        Toast.makeText(ctx, "Confirme si el gerente está con usted", Toast.LENGTH_SHORT).show();
                                                }
                                                else //No ha capturado fotografía evidencia
                                                    Toast.makeText(ctx, "No ha capturado una fotografía al ticket", Toast.LENGTH_SHORT).show();
                                            }// No ha capturado el folio del recibo
                                            else
                                                Toast.makeText(ctx, "No ha capturado el folio del recibo", Toast.LENGTH_SHORT).show();
                                        }
                                        else //No ha seleccionado si imprimirá recibos
                                            Toast.makeText(ctx, "No ha confirmado si va imprimir recibos", Toast.LENGTH_SHORT).show();
                                    }
                                    else //El monto ingresado es igual a cero
                                        Toast.makeText(ctx, "No se pueden capturar montos iguales a cero", Toast.LENGTH_SHORT).show();
                                }
                                else //No ha seleccionado si pagará el pago requerido
                                    Toast.makeText(ctx, "No ha seleccionado si se pagará el requerido", Toast.LENGTH_SHORT).show();
                            }
                            else //No ha seleccionado algun medio de pago
                                Toast.makeText(ctx, "No ha seleccionado un medio de pago", Toast.LENGTH_SHORT).show();
                        }// ================ TERMINA PAGO  ==================================
                        else if (m.ResultadoGestion(tvResultadoGestion) == 1){ //No pago
                            b.putString(RESULTADO_PAGO, "NO PAGO");
                            if (m.MotivoNoPago(tvMotivoNoPago) == 0 || m.MotivoNoPago(tvMotivoNoPago) == 2){ //Motivo de no pago Negacion u Otra
                                b.putString(MOTIVO_NO_PAGO,tvMotivoNoPago.getText().toString());
                                if (!etComentario.getText().toString().trim().isEmpty()){ //El campo comentario es diferente de vacio
                                    b.putString(COMENTARIO, etComentario.getText().toString());
                                    if (byteEvidencia != null){
                                        b.putByteArray(EVIDENCIA, byteEvidencia);
                                        if (m.Gerente(tvGerente) == 0) { //Selecciono que si está el gerente
                                            if (byteFirma != null) { //Capturó una firma
                                                b.putString(GERENTE, "SI");
                                                b.putByteArray(FIRMA, byteFirma);
                                                b.putBoolean(TERMINADO, true);
                                            } else //No ha capturado la firma
                                                Toast.makeText(ctx, "Capture la firma del gerente", Toast.LENGTH_SHORT).show();
                                        } else if (m.Gerente(tvGerente) == 1) { //No se encuentra el Gerente
                                            b.putString(GERENTE, "NO");
                                            b.putBoolean(TERMINADO, true);
                                        } else //No ha seleccionado si está el gerente
                                            Toast.makeText(ctx, "Confirme si el gerente está con usted", Toast.LENGTH_SHORT).show();
                                    }
                                    else //no ha capturado la fotografía de la fachada
                                        Toast.makeText(ctx, "La Fotografía de la fachada es requerida.", Toast.LENGTH_SHORT).show();
                                }
                                else // No ha ingresado alguno comentario
                                    Toast.makeText(ctx, "El campo Comentario es requerido.", Toast.LENGTH_SHORT).show();
                            }
                            else if(m.MotivoNoPago(tvMotivoNoPago) == 1) { //Motivo de no pago fue Fallecimiento
                                b.getString(RESULTADO_PAGO, "NO PAGO");
                                b.putString(MOTIVO_NO_PAGO,tvMotivoNoPago.getText().toString());
                                if (!tvFechaDefuncion.getText().toString().trim().isEmpty()){ //El campo Fecha es diferente de vacio
                                    b.putString(FECHA_DEFUNCION, tvFechaDefuncion.getText().toString());
                                    if (!etComentario.getText().toString().trim().isEmpty()){ // El campo Comentario es diferente de vacio
                                        b.putString(COMENTARIO, etComentario.getText().toString());
                                        if (byteEvidencia != null){ //Capturo una fotografia de fachada
                                            b.putByteArray(EVIDENCIA, byteEvidencia);
                                            if (m.Gerente(tvGerente) == 0) { //Si está el gerente
                                                if (byteFirma != null) { //Capturó un firma
                                                    b.putString(GERENTE, "SI");
                                                    b.putByteArray(FIRMA, byteFirma);
                                                    b.putBoolean(TERMINADO, true);
                                                } else //No ha Capturado un Firma
                                                    Toast.makeText(ctx, "Capture la firma del gerente", Toast.LENGTH_SHORT).show();
                                            } else if (m.Gerente(tvGerente) == 1) { //No está el gerente
                                                b.putString(GERENTE, "NO");
                                                b.putBoolean(TERMINADO, true);
                                            } else //No ha seleccionado si está el gerente
                                                Toast.makeText(ctx, "Confirme si el gerente está con usted", Toast.LENGTH_SHORT).show();
                                        }
                                        else //No ha capturado una fotografia
                                            Toast.makeText(ctx, "La Fotografía de la fachada es requerida.", Toast.LENGTH_SHORT).show();
                                    }
                                    else //No ha ingresado algun comentario
                                        Toast.makeText(ctx, "El campo Comentario es requerido.", Toast.LENGTH_SHORT).show();
                                }
                                else //No ha seleccionado la fecha de defuncion
                                    Toast.makeText(ctx, "No ha seleccionado la fecha de defunción", Toast.LENGTH_SHORT).show();
                            }
                            else if(m.MotivoNoPago(tvMotivoNoPago) == 3){
                                b.getString(RESULTADO_PAGO, "NO PAGO");
                                b.putString(MOTIVO_NO_PAGO,tvMotivoNoPago.getText().toString());
                                if (!tvFechaPromesaPago.getText().toString().trim().isEmpty()){ //El campo Fecha de promesa de pago
                                    b.putString(FECHA_PROMESA_PAGO, tvFechaPromesaPago.getText().toString());
                                    if (!etMontoPromesa.getText().toString().trim().isEmpty() && Double.parseDouble(etMontoPromesa.getText().toString().replace(",","")) > 0) { //El monto promesa es mayor a cero
                                        b.putString(MONTO_PROMESA, etMontoPromesa.getText().toString().trim().replace(",",""));
                                        if (!etComentario.getText().toString().trim().isEmpty()) { // El campo Comentario es diferente de vacio
                                            b.putString(COMENTARIO, etComentario.getText().toString());
                                            if (byteEvidencia != null) { //Capturo una fotografia de fachada
                                                b.putByteArray(EVIDENCIA, byteEvidencia);
                                                if (m.Gerente(tvGerente) == 0) { //Si está el gerente
                                                    if (byteFirma != null) { //Capturó un firma
                                                        b.putString(GERENTE, "SI");
                                                        b.putByteArray(FIRMA, byteFirma);
                                                        b.putBoolean(TERMINADO, true);
                                                    } else //No ha Capturado un Firma
                                                        Toast.makeText(ctx, "Capture la firma del gerente", Toast.LENGTH_SHORT).show();
                                                } else if (m.Gerente(tvGerente) == 1) { //No está el gerente
                                                    b.putString(GERENTE, "NO");
                                                    b.putBoolean(TERMINADO, true);
                                                } else //No ha seleccionado si está el gerente
                                                    Toast.makeText(ctx, "Confirme si el gerente está con usted", Toast.LENGTH_SHORT).show();
                                            } else //No ha capturado una fotografia
                                                Toast.makeText(ctx, "La Fotografía de la fachada es requerida.", Toast.LENGTH_SHORT).show();
                                        } else //No ha ingresado algun comentario
                                            Toast.makeText(ctx, "El campo Comentario es requerido.", Toast.LENGTH_SHORT).show();
                                    } else //No ha ingresado el monto de promesa de pago
                                        Toast.makeText(ctx, "No ha ingresado el monto de promesa de pago", Toast.LENGTH_SHORT).show();
                                } else //No ha seleccionado la fecha de promesa de pago
                                    Toast.makeText(ctx, "No ha seleccionado la fecha de promesa de pago", Toast.LENGTH_SHORT).show();

                            }
                            else  //No ha seleccionado algun motivo de no pago
                                Toast.makeText(ctx, "No ha seleccionado motivo de no pago", Toast.LENGTH_SHORT).show();
                        } // ===================== TERMINA NO PAGO  =========================================
                        else //No ha seleccionado si pagó o no el cliente
                            Toast.makeText(ctx, "No ha seleccionado el resultado de la gestion", Toast.LENGTH_SHORT).show();
                    }
                    else //No ha ingresado el nuevo teléfono
                        Toast.makeText(ctx, "No ha ingresado el nuevo teléfono", Toast.LENGTH_SHORT).show();
                }
                else //No ha seleccionado si va actualizar el telefono
                    Toast.makeText(ctx, "No ha seleccionado si va actualizar el teléfono", Toast.LENGTH_SHORT).show();
            }
            else if(m.ContactoCliente(tvContacto) == 1) { //No contactó al cliente
                b.putString(CONTACTO, "NO");
                if (!etComentario.getText().toString().trim().isEmpty()) { //El campo comentario es diferente de vacio
                    b.putString(COMENTARIO, etComentario.getText().toString());
                    if (byteEvidencia != null) { //Ha capturado una fotografia de la fachada
                        b.putByteArray(EVIDENCIA, byteEvidencia);
                        if (m.Gerente(tvGerente) == 0) { // Seleccionó que está el gerente
                            if (byteFirma != null) { // Ha capturado un firma
                                b.putString(GERENTE, "SI");
                                b.putByteArray(FIRMA, byteFirma);
                                b.putBoolean(TERMINADO, true);
                            } else //No ha capturado un firma
                                Toast.makeText(ctx, "Capture la firma del gerente", Toast.LENGTH_SHORT).show();
                        } else if (m.Gerente(tvGerente) == 1) { //No se encuentra el gerente
                            b.putString(GERENTE, "NO");
                            b.putBoolean(TERMINADO, true);
                        } else //No ha seleccionado si está el gerente
                            Toast.makeText(ctx, "Confirme si el gerente está con usted", Toast.LENGTH_SHORT).show();
                    } else // No ha capturado una fotografia de fachada
                        Toast.makeText(ctx, "La Fotografía de la fachada es requerida.", Toast.LENGTH_SHORT).show();
                } else //No ha ingresado algun comentario
                    Toast.makeText(ctx, "El campo Comentario es obligatorio", Toast.LENGTH_SHORT).show();
            }
            else if(m.ContactoCliente(tvContacto) == 2) { //Seleccionó Aclaración
                b.putString(CONTACTO, "ACLARACION");
                if (!etComentario.getText().toString().trim().isEmpty()) { // Ingresó algun comentario
                    b.putString(COMENTARIO, etComentario.getText().toString());
                    if (m.Gerente(tvGerente) == 0) { //Seleccionó que está el gerente
                        if (byteFirma != null) { //Ha capturado una firma
                            b.putString(GERENTE, "SI");
                            b.putByteArray(FIRMA, byteFirma);
                            b.putBoolean(TERMINADO, true);
                        } else //No ha capturado una firma
                            Toast.makeText(ctx, "Capture la firma del gerente", Toast.LENGTH_SHORT).show();
                    } else if (m.Gerente(tvGerente) == 1) { //Seleccionó que no está el gerente
                        b.putString(GERENTE, "NO");
                        b.putBoolean(TERMINADO, true);
                    } else //No ha confirmado si está el gerente
                        Toast.makeText(ctx, "Confirme si el gerente está con usted", Toast.LENGTH_SHORT).show();
                } else //No ha ingresado algun comentario
                    Toast.makeText(ctx, "El campo Comentario es obligatorio", Toast.LENGTH_SHORT).show();
            }
            else //No ha seleccionado si conectado al cliente o es una aclaración
                Toast.makeText(ctx, "No ha seleccionado si contactó al cliente", Toast.LENGTH_SHORT).show();
        }
        else //No ha capturado la ubicación
            Toast.makeText(ctx,"Falta obtener la ubicación de la gestión", Toast.LENGTH_SHORT).show();

        Log.v("SIDERTMOVIL", b.toString());
        if (!b.isEmpty() && b.containsKey(TERMINADO)){
            Intent i_preview = new Intent(ctx, VistaPreviaGestion.class);
            i_preview.putExtra(PARAMS,b);
            startActivityForResult(i_preview, REQUEST_CODE_PREVIEW);
        }

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_save, menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save:
                GuardarGestion();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void DisableFields(){
        tvContacto.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
        tvContacto.setEnabled(false);
        tvResultadoGestion.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
        tvResultadoGestion.setEnabled(false);
        tvMedioPago.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
        tvMedioPago.setEnabled(false);
        tvPagaraRequerido.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
        tvPagaraRequerido.setEnabled(false);
        etPagoRealizado.setEnabled(false);
        etPagoRealizado.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
        tvImprimirRecibo.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
        tvImprimirRecibo.setEnabled(false);

    }
}
