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
import android.widget.RadioButton;
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
import com.sidert.sidertmovil.activities.CapturarFirma;
import com.sidert.sidertmovil.activities.IntegrantesGpo;
import com.sidert.sidertmovil.activities.PrintSeewoo;
import com.sidert.sidertmovil.activities.RecuperacionGrupal;
import com.sidert.sidertmovil.activities.VistaPreviaGestion;
import com.sidert.sidertmovil.database.DBhelper;
import com.sidert.sidertmovil.fragments.dialogs.dialog_date_picker;
import com.sidert.sidertmovil.models.MImpresion;
import com.sidert.sidertmovil.models.MIntegrantePago;
import com.sidert.sidertmovil.utils.CanvasCustom;
import com.sidert.sidertmovil.utils.Constants;
import com.sidert.sidertmovil.utils.Miscellaneous;
import com.sidert.sidertmovil.utils.MyCurrentListener;
import com.sidert.sidertmovil.utils.NameFragments;
import com.sidert.sidertmovil.utils.NetworkStatus;
import com.sidert.sidertmovil.utils.Popups;
import com.sidert.sidertmovil.utils.Servicios_Sincronizado;
import com.sidert.sidertmovil.utils.SessionManager;
import com.sidert.sidertmovil.utils.Validator;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Objects;

import static com.sidert.sidertmovil.utils.Constants.ACTUALIZAR_TELEFONO;
import static com.sidert.sidertmovil.utils.Constants.COMENTARIO;
import static com.sidert.sidertmovil.utils.Constants.CONTACTO;
import static com.sidert.sidertmovil.utils.Constants.DATE;
import static com.sidert.sidertmovil.utils.Constants.DATE_CURRENT;
import static com.sidert.sidertmovil.utils.Constants.DAY_CURRENT;
import static com.sidert.sidertmovil.utils.Constants.DETALLE_FICHA;
import static com.sidert.sidertmovil.utils.Constants.ESTATUS;
import static com.sidert.sidertmovil.utils.Constants.EVIDENCIA;
import static com.sidert.sidertmovil.utils.Constants.FECHA;
import static com.sidert.sidertmovil.utils.Constants.FECHAS_POST;
import static com.sidert.sidertmovil.utils.Constants.FECHA_DEFUNCION;
import static com.sidert.sidertmovil.utils.Constants.FECHA_DEPOSITO;
import static com.sidert.sidertmovil.utils.Constants.FECHA_FIN;
import static com.sidert.sidertmovil.utils.Constants.FIRMA;
import static com.sidert.sidertmovil.utils.Constants.FIRMA_IMAGE;
import static com.sidert.sidertmovil.utils.Constants.FOLIO;
import static com.sidert.sidertmovil.utils.Constants.FOLIO_TICKET;
import static com.sidert.sidertmovil.utils.Constants.FORMAT_DATE_GNRAL;
import static com.sidert.sidertmovil.utils.Constants.FORMAT_TIMESTAMP;
import static com.sidert.sidertmovil.utils.Constants.GERENTE;
import static com.sidert.sidertmovil.utils.Constants.IDENTIFIER;
import static com.sidert.sidertmovil.utils.Constants.ID_GESTION;
import static com.sidert.sidertmovil.utils.Constants.ID_PRESTAMO;
import static com.sidert.sidertmovil.utils.Constants.IMPRESORA;
import static com.sidert.sidertmovil.utils.Constants.INTEGRANTES;
import static com.sidert.sidertmovil.utils.Constants.MEDIO_PAGO;
import static com.sidert.sidertmovil.utils.Constants.MESSAGE;
import static com.sidert.sidertmovil.utils.Constants.MONTH_CURRENT;
import static com.sidert.sidertmovil.utils.Constants.MONTO_REQUERIDO;
import static com.sidert.sidertmovil.utils.Constants.MOTIVO_ACLARACION;
import static com.sidert.sidertmovil.utils.Constants.MOTIVO_NO_PAGO;
import static com.sidert.sidertmovil.utils.Constants.NOMBRE;
import static com.sidert.sidertmovil.utils.Constants.NOMBRE_GRUPO;
import static com.sidert.sidertmovil.utils.Constants.NUEVO_TELEFONO;
import static com.sidert.sidertmovil.utils.Constants.ORDER_ID;
import static com.sidert.sidertmovil.utils.Constants.PAGO_REALIZADO;
import static com.sidert.sidertmovil.utils.Constants.PARAMS;
import static com.sidert.sidertmovil.utils.Constants.PICTURE;
import static com.sidert.sidertmovil.utils.Constants.REQUEST_CODE_ARQUEO_CAJA;
import static com.sidert.sidertmovil.utils.Constants.REQUEST_CODE_CAMARA_FACHADA;
import static com.sidert.sidertmovil.utils.Constants.REQUEST_CODE_CAMARA_TICKET;
import static com.sidert.sidertmovil.utils.Constants.REQUEST_CODE_FIRMA;
import static com.sidert.sidertmovil.utils.Constants.REQUEST_CODE_GALERIA;
import static com.sidert.sidertmovil.utils.Constants.REQUEST_CODE_IMPRESORA;
import static com.sidert.sidertmovil.utils.Constants.REQUEST_CODE_INTEGRANTES_GPO;
import static com.sidert.sidertmovil.utils.Constants.REQUEST_CODE_PREVIEW;
import static com.sidert.sidertmovil.utils.Constants.RESPONSE;
import static com.sidert.sidertmovil.utils.Constants.RESULTADO_PAGO;
import static com.sidert.sidertmovil.utils.Constants.RESUMEN_INTEGRANTES;
import static com.sidert.sidertmovil.utils.Constants.RES_PRINT;
import static com.sidert.sidertmovil.utils.Constants.ROOT_PATH;
import static com.sidert.sidertmovil.utils.Constants.SALDO_ACTUAL;
import static com.sidert.sidertmovil.utils.Constants.SALDO_CORTE;
import static com.sidert.sidertmovil.utils.Constants.SAVE;
import static com.sidert.sidertmovil.utils.Constants.SCREEN_SHOT;
import static com.sidert.sidertmovil.utils.Constants.TBL_AMORTIZACIONES_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_CARTERA_GPO_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_MIEMBROS_GPO;
import static com.sidert.sidertmovil.utils.Constants.TBL_MIEMBROS_GPO_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_MIEMBROS_PAGOS;
import static com.sidert.sidertmovil.utils.Constants.TBL_MIEMBROS_PAGOS_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_PAGOS_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_PRESTAMOS_GPO_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_RESPUESTAS_GPO;
import static com.sidert.sidertmovil.utils.Constants.TBL_RESPUESTAS_GPO_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_TRACKER_ASESOR_T;
import static com.sidert.sidertmovil.utils.Constants.TERMINADO;
import static com.sidert.sidertmovil.utils.Constants.TIMESTAMP;
import static com.sidert.sidertmovil.utils.Constants.TIPO;
import static com.sidert.sidertmovil.utils.Constants.TIPO_CARTERA;
import static com.sidert.sidertmovil.utils.Constants.UBICACION;
import static com.sidert.sidertmovil.utils.Constants.YEAR_CURRENT;
import static com.sidert.sidertmovil.utils.Constants.camara;
import static com.sidert.sidertmovil.utils.Constants.firma;
import static com.sidert.sidertmovil.utils.Constants.question;

public class recuperacion_gpo_fragment extends Fragment {

    private Context ctx;

    private String[] _contacto;
    private String[] _motivo_aclaracion;
    private String[] _confirmacion;
    private String[] _resultado_gestion;
    private String[] _medio_pago;
    private String[] _motivo_no_pago;
    private String[] _imprimir;

    private TextView tvContacto;
    private TextView tvActualizarTelefono;
    private TextView tvResultadoGestion;
    private TextView tvMedioPago;
    private TextView tvFechaDeposito;
    private TextView tvMontoPagoRequerido;
    private TextView tvDetalleFicha;
    private TextView tvArqueoCaja;
    private TextView tvMotivoAclaracion;
    private TextView tvFechaDefuncion;
    private TextView tvGerente;
    private TextView tvFachada;
    private TextView tvMotivoNoPago;
    private TextView tvmapa;
    private TextView tvFirma;
    private TextView tvImprimirRecibo;
    private TextView tvFotoGaleria;

    private EditText etActualizarTelefono;
    private EditText etComentario;

    private EditText etPagoRealizado;
    private EditText etFolioRecibo;

    private ImageButton ibMap;
    private ImageButton ibFachada;
    private ImageButton ibFoto;
    private ImageButton ibGaleria;
    private ImageButton ibFirma;
    private ImageButton ibIntegrantes;
    private ImageButton ibArqueoCaja;
    private ImageButton ibImprimir;

    private ImageView ivFachada;
    private ImageView ivEvidencia;
    private ImageView ivFirma;

    private Uri imageUri;

    public RadioButton rbIntegrantes;
    private RadioButton rbArqueoCaja;

    private LinearLayout llActualizarTelefono;
    private LinearLayout llComentario;
    private LinearLayout llFachada;
    private LinearLayout llDatosDefuncion;
    private LinearLayout llMotivoAclaracion;
    private LinearLayout llMotivoNoPago;
    private LinearLayout llMontoPagoRequerido;
    private LinearLayout llMedioPago;
    private LinearLayout llResultadoGestion;
    private LinearLayout llArqueoCaja;
    private LinearLayout llFotoGaleria;
    private LinearLayout llIntegrantes;
    private LinearLayout llDetalleFicha;
    private LinearLayout llMontoPagoRealizado;
    private LinearLayout llFechaDeposito;
    private LinearLayout llImprimirRecibo;
    private LinearLayout llFolioRecibo;
    private LinearLayout llGerente;
    private LinearLayout llFirma;

    private Calendar myCalendar;
    private SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_DATE_GNRAL, Locale.US);
    private Date minDate;

    private Validator validator;

    private boolean flagUbicacion = false;

    private ProgressBar pbLoading;

    private MapView mapView;
    private GoogleMap mMap;
    private Marker mMarker;

    private LocationManager locationManager;
    private MyCurrentListener locationListener;
    public LatLng latLngGestion;

    RecuperacionGrupal parent;

    private DBhelper dBhelper;
    private SQLiteDatabase db;

    public byte[] byteEvidencia;
    public byte[] byteFirma;

    public String folio_impreso = "";
    private int res_impresion = 0;
    private int medio_pago_anterio = -1;
    private SessionManager session;

    private DecimalFormat nFormat;
    private DecimalFormat df;
    private DecimalFormat dfnd;
    private boolean hasFractionalPart;

    private int _mediosPago = 0;

    private Miscellaneous m;

    private String fechaIni = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_recuperacion_gpo, container, false);

        ctx = getContext();
        session         = new SessionManager(ctx);
        dBhelper        = new DBhelper(ctx);
        db              = dBhelper.getWritableDatabase();

        m = new Miscellaneous();

        parent                = (RecuperacionGrupal) getActivity();
        assert parent != null;
        parent.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        _contacto = getResources().getStringArray(R.array.contacto_cliente);
        _motivo_aclaracion = getResources().getStringArray(R.array.outdated_information);
        _confirmacion = getResources().getStringArray(R.array.confirmacion);
        _resultado_gestion = getResources().getStringArray(R.array.resultado_gestion);
        _motivo_no_pago = getResources().getStringArray(R.array.reason_no_pay);

        if (parent.tipo_cartera.contains("VENCIDA")) {
            _mediosPago = R.array.metodo_pago_cv;
            _medio_pago = getResources().getStringArray(R.array.metodo_pago_cv);
        }
        else {
            _mediosPago = R.array.medio_pago;
            _medio_pago = getResources().getStringArray(R.array.medio_pago);
        }
        _imprimir = getResources().getStringArray(R.array.imprimir);

        tvActualizarTelefono    = v.findViewById(R.id.tvActualizarTelefono);
        tvContacto              = v.findViewById(R.id.tvContacto);
        tvResultadoGestion      = v.findViewById(R.id.tvResultadoGestion);
        tvMedioPago             = v.findViewById(R.id.tvMedioPago);
        tvFechaDeposito         = v.findViewById(R.id.tvFechaDeposito);
        tvMontoPagoRequerido    = v.findViewById(R.id.tvMontoPagoRequerido);
        tvDetalleFicha          = v.findViewById(R.id.tvDetalleFicha);
        tvArqueoCaja            = v.findViewById(R.id.tvArqueoCaja);
        tvMotivoAclaracion      = v.findViewById(R.id.tvMotivoAclaracion);
        tvGerente               = v.findViewById(R.id.tvGerente);
        tvFachada               = v.findViewById(R.id.tvFachada);
        tvMotivoNoPago          = v.findViewById(R.id.tvMotivoNoPago);
        tvFechaDefuncion        = v.findViewById(R.id.tvFechaDefuncion);
        tvmapa                  = v.findViewById(R.id.tvMapa);
        tvFirma                 = v.findViewById(R.id.tvFirma);
        tvImprimirRecibo        = v.findViewById(R.id.tvImprimirRecibo);
        tvFotoGaleria           = v.findViewById(R.id.tvFotoGaleria);

        etActualizarTelefono    = v.findViewById(R.id.etActualizarTelefono);
        etComentario            = v.findViewById(R.id.etComentario);
        etPagoRealizado         = v.findViewById(R.id.etPagoRealizado);
        etFolioRecibo           = v.findViewById(R.id.etFolioRecibo);

        ibMap           = v.findViewById(R.id.ibMap);
        ibFachada       = v.findViewById(R.id.ibFachada);
        ibFoto          = v.findViewById(R.id.ibFoto);
        ibGaleria       = v.findViewById(R.id.ibGaleria);
        ibFirma         = v.findViewById(R.id.ibFirma);
        ibIntegrantes   = v.findViewById(R.id.ibIntegrantes);
        ibArqueoCaja    = v.findViewById(R.id.ibArqueoCaja);
        ibImprimir      = v.findViewById(R.id.ibImprimir);

        ivFachada       = v.findViewById(R.id.ivFachada);
        ivEvidencia     = v.findViewById(R.id.ivEvidencia);
        ivFirma         = v.findViewById(R.id.ivFirma);

        pbLoading   = v.findViewById(R.id.pbLoading);

        mapView     = v.findViewById(R.id.mapGestion);

        rbIntegrantes   = v.findViewById(R.id.rbIntegrantes);
        rbArqueoCaja    = v.findViewById(R.id.rbArqueoCaja);

        llActualizarTelefono    = v.findViewById(R.id.llActualizarTelefono);
        llComentario            = v.findViewById(R.id.llComentario);
        llIntegrantes           = v.findViewById(R.id.llIntegrantes);
        llResultadoGestion      = v.findViewById(R.id.llResultadoGestion);
        llFachada               = v.findViewById(R.id.llFachada);
        llDatosDefuncion        = v.findViewById(R.id.llDatosDefuncion);
        llMotivoAclaracion      = v.findViewById(R.id.llMotivoAclaracion);
        llMotivoNoPago          = v.findViewById(R.id.llMotivoNoPago);
        llMontoPagoRequerido    = v.findViewById(R.id.llMontoPagoRequerido);
        llMedioPago             = v.findViewById(R.id.llMedioPago);
        llArqueoCaja            = v.findViewById(R.id.llArqueoCaja);
        llDetalleFicha          = v.findViewById(R.id.llDetalleFicha);
        llMontoPagoRealizado    = v.findViewById(R.id.llMontoPagoRealizado);
        llImprimirRecibo        = v.findViewById(R.id.llImprimirRecibo);
        llFolioRecibo           = v.findViewById(R.id.llFolioRecibo);
        llFechaDeposito         = v.findViewById(R.id.llFechaDeposito);
        llFotoGaleria           = v.findViewById(R.id.llFotoGaleria);
        llGerente               = v.findViewById(R.id.llGerente);
        llFirma                 = v.findViewById(R.id.llFirma);

        myCalendar      = Calendar.getInstance();

        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
        df = new DecimalFormat("#,###.##", symbols);
        df.setDecimalSeparatorAlwaysShown(true);
        dfnd = new DecimalFormat("#,###", symbols);
        dfnd.setDecimalSeparatorAlwaysShown(false);
        nFormat = new DecimalFormat("###,###.##", symbols);

        hasFractionalPart = false;

        mapView.onCreate(savedInstanceState);
        locationManager = (LocationManager) parent.getSystemService(Context.LOCATION_SERVICE);

        // ImageButton Click
        ibMap.setOnClickListener(ibMap_OnClick);
        ibIntegrantes.setOnClickListener(ibIntegrantes_OnClick);
        ibArqueoCaja.setOnClickListener(ibArqueoCaja_OnClick);
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
        tvDetalleFicha.setOnClickListener(tvDetalleFicha_OnClick);
        tvImprimirRecibo.setOnClickListener(tvImprimirRecibo_OnClick);
        tvMotivoNoPago.setOnClickListener(tvMotivoNoPago_OnClick);
        tvMotivoAclaracion.setOnClickListener(tvMotivoAclaracion_OnClick);
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
                    Update("comentario", m.GetStr(etComentario));
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
                    if (!m.GetStr(etActualizarTelefono).isEmpty() &&
                            m.GetStr(etActualizarTelefono).length() == 10) {
                        Update("nuevo_telefono", m.GetStr(etActualizarTelefono));
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
                    inilen = m.GetStr(etPagoRealizado).length();

                    String v = s.toString().replace(String.valueOf(df.getDecimalFormatSymbols().getGroupingSeparator()), "");
                    Number n = df.parse(v);
                    int cp = etPagoRealizado.getSelectionStart();
                    if (hasFractionalPart) {
                        etPagoRealizado.setText(df.format(n));
                    } else {
                        etPagoRealizado.setText(dfnd.format(n));
                    }
                    endlen = m.GetStr(etPagoRealizado).length();
                    int sel = (cp + (endlen - inilen));
                    if (sel > 0 && sel <= m.GetStr(etPagoRealizado).length()) {
                        etPagoRealizado.setSelection(sel);
                    } else {
                        // place cursor at the end?
                        etPagoRealizado.setSelection(m.GetStr(etPagoRealizado).length() - 1);
                    }
                } catch (NumberFormatException nfe) {
                    // do nothing?
                } catch (ParseException e) {
                    // do nothing?
                }

                if (s.length() > 0) {
                    if (!m.GetStr(etPagoRealizado).isEmpty()) {
                        try {
                            if (Double.parseDouble(m.GetStr(etPagoRealizado).replace(",","")) > 0) {
                                Update("pago_realizado", m.GetStr(etPagoRealizado).replace(",",""));
                                if (Double.parseDouble(m.GetStr(etPagoRealizado).replace(",","")) >= 10000 && m.GetMedioPagoId(m.GetStr(tvMedioPago)) == 6)
                                    llArqueoCaja.setVisibility(View.VISIBLE);
                                else
                                    llArqueoCaja.setVisibility(View.GONE);


                            }
                        } catch (NumberFormatException e){
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

        etFolioRecibo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (m.GetMedioPagoId(m.GetStr(tvMedioPago)) == 6){
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

                    Cursor row = dBhelper.getRecords(TBL_RESPUESTAS_GPO_T, " WHERE id_prestamo = ?", " ORDER BY _id ASC",new String[]{parent.id_prestamo});
                    row.moveToLast();
                    if (row.getCount() == 0){
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
                        fechaIni = m.ObtenerFecha(TIMESTAMP);
                        params.put(21, fechaIni);
                        params.put(22, "");
                        params.put(23, "");
                        params.put(24, "0");
                        params.put(25, "0");
                        params.put(26, "0");
                        params.put(27, "0");
                        params.put(28, "0");
                        params.put(29, "0");
                        params.put(30, "0");

                        long id = dBhelper.saveRespuestasGpo(db, params);
                        parent.id_respuesta = String.valueOf(id);
                    }
                    else{
                        if (row.getInt(25) > 0){
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
                            fechaIni = m.ObtenerFecha(TIMESTAMP);
                            params.put(21, fechaIni);
                            params.put(22, "");
                            params.put(23, "");
                            params.put(24, "0");
                            params.put(25, "0");
                            params.put(26, "0");
                            params.put(27, "0");
                            params.put(28, "0");
                            params.put(29, "0");
                            params.put(30, "0");

                            long id = dBhelper.saveRespuestasGpo(db, params);
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

                    Cursor row = dBhelper.getRecords(TBL_RESPUESTAS_GPO_T, " WHERE id_prestamo = ?", " ORDER BY _id ASC",new String[]{parent.id_prestamo});
                    row.moveToLast();
                    if (row.getCount() == 0){
                        HashMap<Integer, String> params = new HashMap<>();
                        params.put(0,parent.id_prestamo);
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
                        fechaIni = m.ObtenerFecha(TIMESTAMP);
                        params.put(21, fechaIni);
                        params.put(22, "");
                        params.put(23, "");
                        params.put(24, "0");
                        params.put(25, "0");
                        params.put(26, "0");
                        params.put(27, "0");
                        params.put(28, "0");
                        params.put(29, "0");
                        params.put(30, "0");

                        long id = dBhelper.saveRespuestasGpo(db, params);
                        parent.id_respuesta = String.valueOf(id);
                    }
                    else{
                        if (row.getInt(25) > 0){
                            HashMap<Integer, String> params = new HashMap<>();
                            params.put(0,parent.id_prestamo);
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
                            fechaIni = m.ObtenerFecha(TIMESTAMP);
                            params.put(21, fechaIni);
                            params.put(22, "");
                            params.put(23, "");
                            params.put(24, "0");
                            params.put(25, "0");
                            params.put(26, "0");
                            params.put(27, "0");
                            params.put(28, "0");
                            params.put(29, "0");
                            params.put(30, "0");

                            long id = dBhelper.saveRespuestasGpo(db, params);
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

    private View.OnClickListener ibIntegrantes_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Cursor row = dBhelper.getRecords(TBL_MIEMBROS_PAGOS_T, " WHERE id_gestion = ?", "", new String[]{parent.id_respuesta});

            if (row.getCount() == 0){
                row.close();

                row = dBhelper.getRecords(TBL_MIEMBROS_GPO_T, " WHERE id_prestamo = ?", "", new String[]{parent.id_prestamo});

                if (row.getCount() > 0){
                    row.moveToFirst();
                    for (int i = 0; i < row.getCount(); i++){
                        HashMap<Integer, String> params = new HashMap<>();
                        params.put(0, row.getString(2));
                        params.put(1, row.getString(1));
                        params.put(2, parent.id_respuesta);
                        params.put(3, row.getString(5));
                        params.put(4, row.getString(11));
                        params.put(5, "0");
                        params.put(6, "0");
                        params.put(7, "0");
                        params.put(8, "0");

                        dBhelper.saveMiembrosPagos(db, params);
                        row.moveToNext();
                    }
                }
            }
            row.close();

            Intent i_integrantes = new Intent(ctx, IntegrantesGpo.class);
            i_integrantes.putExtra(NOMBRE_GRUPO, parent.nombre);
            i_integrantes.putExtra(ID_PRESTAMO, parent.id_prestamo);
            i_integrantes.putExtra(ID_GESTION, parent.id_respuesta);
            i_integrantes.putExtra(TIPO_CARTERA, parent.tipo_cartera);

            row = dBhelper.getRecords(TBL_MIEMBROS_PAGOS_T, " WHERE id_gestion = ?", "", new String[]{parent.id_respuesta});

            ArrayList<MIntegrantePago> integrantesPagos = new ArrayList<>();
            if (row.getCount() > 0){
                row.moveToFirst();
                for(int i = 0; i < row.getCount(); i++){
                    MIntegrantePago item = new MIntegrantePago();
                    item.setIdPrestamo(row.getString(2));
                    item.setIdIntegrante(row.getString(1));
                    item.setIdGestion(row.getString(3));
                    item.setNombre(row.getString(4));
                    item.setMontoRequerido(row.getString(5));
                    item.setPagoRealizado(row.getString(6));
                    item.setAdelanto(row.getString(7));
                    item.setSolidario(row.getString(8));
                    item.setPagoRequerido(row.getInt(9) == 1);
                    item.setTipoCartera(parent.tipo_cartera);

                    integrantesPagos.add(item);
                    row.moveToNext();
                }
            }
            i_integrantes.putExtra(INTEGRANTES, integrantesPagos);

            startActivityForResult(i_integrantes, REQUEST_CODE_INTEGRANTES_GPO);

        }
    };

    private View.OnClickListener ibArqueoCaja_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i_arqueoCaja = new Intent(ctx, ArqueoDeCaja.class);
            i_arqueoCaja.putExtra(PAGO_REALIZADO, Double.parseDouble(m.GetStr(etPagoRealizado).replace(",","")));
            i_arqueoCaja.putExtra(NOMBRE_GRUPO, parent.nombre);
            i_arqueoCaja.putExtra(ID_GESTION, parent.id_respuesta);
            startActivityForResult(i_arqueoCaja, REQUEST_CODE_ARQUEO_CAJA);
        }
    };

    private View.OnClickListener ibImprimir_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (!m.GetStr(etPagoRealizado).isEmpty() && Double.parseDouble(m.GetStr(etPagoRealizado).replace(",","")) > 0){
                Intent i = new Intent(ctx, PrintSeewoo.class);
                MImpresion mImpresion = new MImpresion();

                String fechaPago = "";
                String montoPago = "";
                Cursor rowPago = dBhelper.getRecords(TBL_PAGOS_T, " WHERE id_prestamo = ?", " ORDER BY fecha ASC", new String[]{parent.id_prestamo});
                if (rowPago.getCount() > 0){
                    rowPago.moveToFirst();

                    fechaPago = rowPago.getString(2);
                    montoPago = rowPago.getString(3);

                }
                rowPago.close();

                Log.e("FechaPago", fechaPago);
                Log.e("montoPago", montoPago);

                if (parent.tipo_cartera.contains("VENCIDA")){

                    String sql = "SELECT p.*, m.* FROM " + TBL_MIEMBROS_PAGOS_T + " AS p INNER JOIN " + TBL_MIEMBROS_GPO_T + " AS m ON p.id_integrante = m.id_integrante WHERE p.id_gestion = ? AND p.pago_realizado > 0";

                    Cursor row = db.rawQuery(sql, new String[]{parent.id_respuesta});
                    if (row.getCount() > 0){
                        row.moveToFirst();
                        mImpresion.setMontoPrestamo(row.getString(20));
                        mImpresion.setNombre(row.getString(4));
                        mImpresion.setPagoRequerido(row.getString(5)); //
                        mImpresion.setNombreFirma(row.getString(4));
                        mImpresion.setClaveCliente(parent.clave_grupo);
                    }

                    mImpresion.setNumeroCliente(parent.clave_grupo);
                    mImpresion.setIdPrestamo(parent.id_prestamo);
                    mImpresion.setIdGestion(parent.id_respuesta);
                    mImpresion.setMonto(String.valueOf(Math.ceil(Double.parseDouble(m.GetStr(etPagoRealizado).replace(",", "")))));
                    mImpresion.setNumeroPrestamo(parent.num_prestamo);
                    mImpresion.setNombreAsesor(session.getUser().get(1) + " " + session.getUser().get(2) + " " + session.getUser().get(3));
                    mImpresion.setAsesorId(session.getUser().get(0));
                    mImpresion.setTipoPrestamo(parent.tipo_cartera);
                    mImpresion.setTipoGestion("INDIVIDUAL");
                    mImpresion.setResultPrint(res_impresion);

                    i.putExtra("order", mImpresion);
                    i.putExtra("tag",true);

                    startActivityForResult(i,REQUEST_CODE_IMPRESORA);

                }
                else {
                    mImpresion.setIdPrestamo(parent.id_prestamo);
                    mImpresion.setIdGestion(parent.id_respuesta);
                    mImpresion.setMonto(String.valueOf(Math.ceil(Double.parseDouble(m.GetStr(etPagoRealizado).replace(",", "")))));
                    mImpresion.setMontoPrestamo(parent.monto_prestamo);
                    mImpresion.setNumeroPrestamo(parent.num_prestamo);
                    mImpresion.setNumeroCliente(parent.clave_grupo);
                    mImpresion.setNombre(parent.nombre);
                    mImpresion.setPagoRequerido(String.valueOf(parent.monto_requerido));
                    mImpresion.setNombreAsesor(session.getUser().get(1) + " " + session.getUser().get(2) + " " + session.getUser().get(3));
                    mImpresion.setAsesorId(session.getUser().get(0));
                    mImpresion.setTipoPrestamo(parent.tipo_cartera);
                    mImpresion.setTipoGestion("GRUPAL");
                    mImpresion.setNombreFirma(parent.tesorera);
                    mImpresion.setResultPrint(res_impresion);
                    mImpresion.setClaveCliente(parent.clave_grupo);
                    mImpresion.setFechaUltimoPago(fechaPago);
                    mImpresion.setMontoUltimoPago(montoPago);
                    mImpresion.setTelefono(parent.telTesorero);
                    i.putExtra("order", mImpresion);
                    i.putExtra("tag",true);

                    startActivityForResult(i,REQUEST_CODE_IMPRESORA);
                }
            }

            else {
                Toast.makeText(ctx, "No ha capturado el pago realizado del cliente", Toast.LENGTH_SHORT).show();
            }

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
                        .start(ctx,recuperacion_gpo_fragment.this);
            }

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
                            tvContacto.setText(_contacto[position]);
                            tvMotivoAclaracion.setError(null);
                            tvFachada.setError(null);
                            etComentario.setText("");
                            tvGerente.setText("");
                            byteFirma = null;
                            byteEvidencia = null;
                            ibFachada.setVisibility(View.VISIBLE);
                            ivFachada.setVisibility(View.GONE);

                            ibFirma.setVisibility(View.VISIBLE);
                            ivFirma.setVisibility(View.GONE);
                            SelectEstaGerente(m.GetIdConfirmacion(m.GetStr(tvGerente)));

                            Cursor row = dBhelper.getRecords(TBL_RESPUESTAS_GPO_T, " WHERE id_prestamo = ?", " ORDER BY _id ASC", new String[]{parent.id_prestamo});
                            row.moveToLast();

                            if (row.getCount() == 0) {
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
                                fechaIni = m.ObtenerFecha(TIMESTAMP);
                                params.put(21, fechaIni);
                                params.put(22, "");
                                params.put(23, "");
                                params.put(24, "0");
                                params.put(25, "0");
                                params.put(26, "0");
                                params.put(27, "0");
                                params.put(28, "0");
                                params.put(29, "0");
                                params.put(30, "0");

                                long id = dBhelper.saveRespuestasGpo(db, params);

                                parent.id_respuesta = String.valueOf(id);
                            } else {
                                Log.e("RespuestaEstatus", row.getString(25)+" xxxxx");
                                if (row.getInt(25) > 0) {
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
                                    fechaIni = m.ObtenerFecha(TIMESTAMP);
                                    params.put(21, fechaIni);
                                    params.put(22, "");
                                    params.put(23, "");
                                    params.put(24, "0");
                                    params.put(25, "0");
                                    params.put(26, "0");
                                    params.put(27, "0");
                                    params.put(28, "0");
                                    params.put(29, "0");
                                    params.put(30, "0");
                                    long id = dBhelper.saveRespuestasGpo(db, params);

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
                            if (m.GetMedioPagoId(m.GetStr(tvMedioPago)) == 6 && medio_pago_anterio >= 0) {
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
                            else if(m.GetMedioPagoId(m.GetStr(tvMedioPago)) >= 0 && medio_pago_anterio == 6){
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
                            medio_pago_anterio = m.GetMedioPagoId(m.GetStr(tvMedioPago));

                            Update("medio_pago", _medio_pago[position]);
                            SelectMedioPago(m.GetMedioPagoId(m.GetStr(tvMedioPago)));
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
                b.putInt(IDENTIFIER, 8);
                b.putBoolean(FECHAS_POST, false);
                dialogDatePicker.setArguments(b);
                dialogDatePicker.setTargetFragment(recuperacion_gpo_fragment.this,812);
                dialogDatePicker.show(parent.getSupportFragmentManager(), NameFragments.DIALOGDATEPICKER);
        }
    };

    private View.OnClickListener tvDetalleFicha_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
            builder.setTitle(R.string.selected_option)
                    .setItems(R.array.confirmacion, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int position) {
                            tvDetalleFicha.setError(null);
                            tvDetalleFicha.setText(_confirmacion[position]);
                            Update("detalle_ficha", _confirmacion[position]);
                            SelectDetalleFicha(position);
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
                    .setItems(R.array.reason_no_pay, new DialogInterface.OnClickListener() {
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

    private View.OnClickListener tvMotivoAclaracion_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
            builder.setTitle(R.string.selected_option)
                    .setItems(R.array.outdated_information, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int position) {
                            tvMotivoAclaracion.setError(null);
                            tvMotivoAclaracion.setText(_motivo_aclaracion[position]);
                            Update("motivo_aclaracion", _motivo_aclaracion[position]);
                        }
                    });
            builder.create();
            builder.show();
        }
    };

    private View.OnClickListener tvFechaDefuncion_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //if (parent.flagRespuesta) {
                myCalendar = Calendar.getInstance();
                dialog_date_picker dialogDatePicker = new dialog_date_picker();
                Bundle b = new Bundle();

                b.putInt(YEAR_CURRENT, myCalendar.get(Calendar.YEAR));
                b.putInt(MONTH_CURRENT, myCalendar.get(Calendar.MONTH));
                b.putInt(DAY_CURRENT, myCalendar.get(Calendar.DAY_OF_MONTH));
                b.putString(DATE_CURRENT, sdf.format(myCalendar.getTime()));
                b.putInt(IDENTIFIER, 7);
                b.putBoolean(FECHAS_POST, false);
                dialogDatePicker.setArguments(b);
                dialogDatePicker.setTargetFragment(recuperacion_gpo_fragment.this,123);
                dialogDatePicker.show(parent.getSupportFragmentManager(), NameFragments.DIALOGDATEPICKER);
            //}
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
            if (m.GetStr(tvMedioPago).equals("EFECTIVO")){
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
                                        .start(ctx,recuperacion_gpo_fragment.this);
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

    //===================== Comportamientos  ===============================================
    private void SelectContactoCliente (int pos){
        if (!m.GetStr(tvGerente).isEmpty()) tvGerente.setError(null);
        else tvGerente.setError("");
        switch (pos){
            case 0: // Si contacto cliente
                tvResultadoGestion.setError("");
                tvMotivoAclaracion.setText("");
                tvResultadoGestion.setText("");
                SelectResultadoGestion(-1);
                llComentario.setVisibility(View.GONE);
                tvActualizarTelefono.setText("");
                SelectActualizarTelefono(-1);
                llActualizarTelefono.setVisibility(View.VISIBLE);
                llResultadoGestion.setVisibility(View.VISIBLE);
                llFachada.setVisibility(View.GONE);
                llGerente.setVisibility(View.GONE);
                llFirma.setVisibility(View.GONE);
                llMotivoAclaracion.setVisibility(View.GONE);
                break;
            case 1: // No contacto cliente
                tvFachada.setError("");
                tvMotivoAclaracion.setText("");
                tvResultadoGestion.setText("");
                SelectResultadoGestion(-1);
                llComentario.setVisibility(View.VISIBLE);
                etComentario.setError(getResources().getString(R.string.campo_requerido));
                tvActualizarTelefono.setText("");
                SelectActualizarTelefono(-1);
                llActualizarTelefono.setVisibility(View.GONE);
                llResultadoGestion.setVisibility(View.GONE);
                llFachada.setVisibility(View.VISIBLE);
                llGerente.setVisibility(View.VISIBLE);
                llMotivoAclaracion.setVisibility(View.GONE);
                break;
            case 2: // Aclaración
                tvMotivoAclaracion.setError("");
                etComentario.setError(getResources().getString(R.string.campo_requerido));
                etComentario.setText("");
                tvActualizarTelefono.setText("");
                SelectActualizarTelefono(-1);
                llActualizarTelefono.setVisibility(View.GONE);
                tvResultadoGestion.setText("");
                SelectResultadoGestion(-1);
                llResultadoGestion.setVisibility(View.GONE);
                llFachada.setVisibility(View.GONE);
                llComentario.setVisibility(View.VISIBLE);
                llGerente.setVisibility(View.VISIBLE);
                llMotivoAclaracion.setVisibility(View.VISIBLE);
                break;
            default: //Sin seleccionar una opción o cualquier otro valor
                tvMotivoAclaracion.setText("");
                tvResultadoGestion.setText("");
                tvActualizarTelefono.setText("");
                SelectActualizarTelefono(-1);
                llActualizarTelefono.setVisibility(View.GONE);
                llComentario.setVisibility(View.GONE);
                llResultadoGestion.setVisibility(View.GONE);
                llFachada.setVisibility(View.GONE);
                llGerente.setVisibility(View.GONE);
                llMotivoAclaracion.setVisibility(View.GONE);
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
    private void SelectResultadoGestion (int pos){
        switch (pos){
            case 0: //Si pago
                tvMotivoNoPago.setText("");
                SelectMotivoNoPago(-1);
                llMedioPago.setVisibility(View.VISIBLE);
                llMontoPagoRequerido.setVisibility(View.VISIBLE);
                llComentario.setVisibility(View.GONE);
                llMotivoNoPago.setVisibility(View.GONE);
                llFachada.setVisibility(View.GONE);
                llGerente.setVisibility(View.GONE);
                break;
            case 1: // No Pago
                tvMotivoNoPago.setError("");
                tvFachada.setError("");
                tvMedioPago.setText("");
                SelectMotivoNoPago(-1);
                SelectMedioPago(-1);
                tvDetalleFicha.setText("");
                SelectDetalleFicha(-1);
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
                tvDetalleFicha.setText("");
                SelectDetalleFicha(-1);
                llMedioPago.setVisibility(View.GONE);
                llMontoPagoRequerido.setVisibility(View.GONE);
                llComentario.setVisibility(View.GONE);
                llMotivoNoPago.setVisibility(View.GONE);
                llFachada.setVisibility(View.GONE);
                llGerente.setVisibility(View.GONE);
                break;
        }
    }
    private void SelectMotivoNoPago (int pos){
        tvMotivoNoPago.setError(null);
        switch (pos){
            case -1: // Opción "Seleccione una opcion"
                tvMotivoNoPago.setError("");
                tvMotivoNoPago.setText("");
                llDatosDefuncion.setVisibility(View.GONE);
                break;
            case 0: // Negación de pago
            case 2: // Otro
                llDatosDefuncion.setVisibility(View.GONE);
                break;
            case 1: //Fallecimiento
                tvFechaDefuncion.setError("");
                if (!m.GetStr(tvFechaDefuncion).isEmpty())
                    tvFechaDefuncion.setError(null);
                llDatosDefuncion.setVisibility(View.VISIBLE);
                break;
            default: //Sin seleccionar una opción o cualquier otro valor
                tvMotivoNoPago.setError("");
                llDatosDefuncion.setVisibility(View.GONE);
                tvMotivoNoPago.setText("");
                break;
        }
    }
    private void SelectMedioPago (int pos){
        Log.e("MedioPago", ""+pos);
        tvMedioPago.setError(null);
        switch (pos) {
            case -1: // Opción "Seleccione una opción"
                tvMedioPago.setError("");
                tvDetalleFicha.setText("");
                SelectDetalleFicha(-1);
                llDetalleFicha.setVisibility(View.GONE);
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
                if (m.GetStr(tvFechaDeposito).isEmpty())
                    tvFechaDeposito.setError(getResources().getString(R.string.campo_requerido));
                llDetalleFicha.setVisibility(View.VISIBLE);
                tvDetalleFicha.setEnabled(true);
                llFechaDeposito.setVisibility(View.VISIBLE);
                ibGaleria.setEnabled(true);
                ibGaleria.setBackground(ctx.getResources().getDrawable(R.drawable.round_corner_blue));
                llMontoPagoRequerido.setVisibility(View.VISIBLE);
                llImprimirRecibo.setVisibility(View.GONE);
                llFotoGaleria.setVisibility(View.VISIBLE);
                llGerente.setVisibility(View.VISIBLE);
                llFolioRecibo.setVisibility(View.GONE);
                //SelectImprimirRecibos(-1);
                break;
            case 15: // OXXO
                if (byteEvidencia != null)
                    tvFotoGaleria.setError(null);
                else
                    tvFotoGaleria.setError("");
                if (m.GetStr(tvFechaDeposito).isEmpty())
                    tvFechaDeposito.setError(getResources().getString(R.string.campo_requerido));
                ibGaleria.setEnabled(true);
                ibGaleria.setBackground(ctx.getResources().getDrawable(R.drawable.round_corner_blue));
                llDetalleFicha.setVisibility(View.VISIBLE);
                llFechaDeposito.setVisibility(View.VISIBLE);
                llMontoPagoRequerido.setVisibility(View.VISIBLE);
                tvDetalleFicha.setText(_confirmacion[0]);
                tvDetalleFicha.setEnabled(false);
                etPagoRealizado.setText(String.valueOf(parent.monto_requerido));
                SelectDetalleFicha(0);
                llImprimirRecibo.setVisibility(View.GONE);
                llFotoGaleria.setVisibility(View.VISIBLE);
                llGerente.setVisibility(View.VISIBLE);
                //SelectImprimirRecibos(-1);
                break;
            case 7: //SIDERT
                if (byteEvidencia != null)
                    tvFotoGaleria.setError(null);
                else
                    tvFotoGaleria.setError("");
                ibGaleria.setEnabled(false);
                ibGaleria.setBackground(ctx.getResources().getDrawable(R.drawable.btn_disable));
                if (!m.GetStr(etFolioRecibo).isEmpty())
                    tvImprimirRecibo.setError(null);
                else
                    tvImprimirRecibo.setError("");
                llDetalleFicha.setVisibility(View.VISIBLE);
                llFechaDeposito.setVisibility(View.VISIBLE);
                tvDetalleFicha.setEnabled(true);
                llMontoPagoRequerido.setVisibility(View.VISIBLE);
                llImprimirRecibo.setVisibility(View.VISIBLE);
                tvImprimirRecibo.setText(_imprimir[1]);
                tvImprimirRecibo.setEnabled(false);
                SelectImprimirRecibos(m.GetIdImpresion(m.GetStr(tvImprimirRecibo)));
                llFotoGaleria.setVisibility(View.VISIBLE);
                llGerente.setVisibility(View.VISIBLE);
                break;
            case 6: // Efectivo
                if (byteEvidencia != null)
                    tvFotoGaleria.setError(null);
                else
                    tvFotoGaleria.setError("");

                if (!m.GetStr(etPagoRealizado).isEmpty() && Double.parseDouble(m.GetStr(etPagoRealizado).replace(",","")) > 10000) {
                    llArqueoCaja.setVisibility(View.VISIBLE);
                }
                llDetalleFicha.setVisibility(View.VISIBLE);
                ibGaleria.setEnabled(false);
                ibGaleria.setBackground(ctx.getResources().getDrawable(R.drawable.btn_disable));
                if (!m.GetStr(etFolioRecibo).isEmpty())
                    tvImprimirRecibo.setError(null);
                else
                    tvImprimirRecibo.setError("");
                llFechaDeposito.setVisibility(View.GONE);
                llMontoPagoRequerido.setVisibility(View.VISIBLE);
                tvDetalleFicha.setEnabled(true);
                tvImprimirRecibo.setEnabled(true);
                llImprimirRecibo.setVisibility(View.VISIBLE);
                llFotoGaleria.setVisibility(View.VISIBLE);
                llGerente.setVisibility(View.VISIBLE);
                tvImprimirRecibo.setText("");
                SelectImprimirRecibos(-1);
                break;
            default: //Sin seleccionar una opción o cualquier otro valor
                tvMedioPago.setError("");
                tvDetalleFicha.setText("");
                SelectDetalleFicha(-1);
                tvImprimirRecibo.setText("");
                SelectImprimirRecibos(-1);
                ivEvidencia.setVisibility(View.GONE);
                llDetalleFicha.setVisibility(View.GONE);
                llFechaDeposito.setVisibility(View.GONE);
                llFotoGaleria.setVisibility(View.GONE);
                llImprimirRecibo.setVisibility(View.GONE);
                llFolioRecibo.setVisibility(View.GONE);
                llGerente.setVisibility(View.GONE);
                tvDetalleFicha.setEnabled(true);
                break;
        }
    }
    private void SelectDetalleFicha (int pos){
        switch (pos){
            case 0: // Si cuenta con detalle
                etPagoRealizado.setEnabled(false);
                llIntegrantes.setVisibility(View.VISIBLE);
                llMontoPagoRealizado.setVisibility(View.VISIBLE);
                break;
            case 1: // No cuenta con detalle
                etPagoRealizado.setError(null);
                etPagoRealizado.setEnabled(true);
                llIntegrantes.setVisibility(View.GONE);
                llMontoPagoRealizado.setVisibility(View.VISIBLE);
                break;
            default:
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
                    llFolioRecibo.setVisibility(View.VISIBLE);
                    etFolioRecibo.setText(folio_impreso);
                    etFolioRecibo.setEnabled(false);
                    etFolioRecibo.setError(null);
                }
                else {
                    tvImprimirRecibo.setError("");
                    llFolioRecibo.setVisibility(View.GONE);
                }
                break;
            case 1: //No cuenta con bateria la impresora
                tvImprimirRecibo.setError(null);
                ibImprimir.setVisibility(View.GONE);
                llFolioRecibo.setVisibility(View.VISIBLE);
                etFolioRecibo.setText("");
                etFolioRecibo.setHint(R.string.folio_sidert);
                etFolioRecibo.setError(getResources().getString(R.string.campo_requerido));
                etFolioRecibo.setInputType(InputType.TYPE_CLASS_NUMBER);
                etFolioRecibo.setEnabled(true);
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
            case 0: // Si está el gerente
                tvFirma.setError("");
                if (ivFirma.getVisibility() == View.VISIBLE)
                    tvFirma.setError(null);
                else
                    tvFirma.setError("");
                llFirma.setVisibility(View.VISIBLE);
                break;
            case 1: // No está el gerente
                llFirma.setVisibility(View.GONE);
                break;
            default: // Sin seleccionar alguna opción o cualquier valor diferente
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

    private void init(){
        setHasOptionsMenu(true);
        parent.getSupportActionBar().show();
        tvMontoPagoRequerido.setText(String.valueOf(nFormat.format(parent.monto_requerido)));

        if (!parent.id_respuesta.isEmpty()){
            Cursor row = dBhelper.getRecords(TBL_RESPUESTAS_GPO_T, " WHERE _id = ? AND id_prestamo = ?", "", new String[]{parent.id_respuesta, parent.id_prestamo});

            if (row.getCount() > 0) {
                row.moveToFirst();

                fechaIni = row.getString(22);
                res_impresion = Integer.parseInt(row.getString(26));

                if (!row.getString(2).isEmpty() && !row.getString(3).isEmpty()){
                    tvmapa.setError(null);
                    mapView.setVisibility(View.VISIBLE);
                    ColocarUbicacionGestion(row.getDouble(2), row.getDouble(3));
                }

                Log.e("Contacto", String.valueOf(!row.getString(4).isEmpty()));
                if (!row.getString(4).isEmpty()){
                    tvContacto.setText(row.getString(4));
                    Log.e("Contacto",""+m.GetIdContacto(m.GetStr(tvContacto)));
                    switch (m.GetIdContacto(m.GetStr(tvContacto))) {
                        case 0: //SI CONTACTO
                            SelectContactoCliente(m.GetIdContacto(m.GetStr(tvContacto)));

                            if (!row.getString(7).isEmpty()){//ACTUALIZAR TELEFONO
                                tvActualizarTelefono.setText(row.getString(7));
                                if (m.GetIdConfirmacion(m.GetStr(tvActualizarTelefono)) == 0){
                                    etActualizarTelefono.setVisibility(View.VISIBLE);
                                    if (!row.getString(8).isEmpty()){//NUEVO TELEFONO
                                        etActualizarTelefono.setText(row.getString(8));
                                        etActualizarTelefono.setError(null);
                                    }
                                }
                            }

                            if (!row.getString(9).isEmpty()){//RESULTADO PAGO
                                tvResultadoGestion.setText(row.getString(9));
                                SelectResultadoGestion(m.GetIdPago(m.GetStr(tvResultadoGestion)));
                                switch (m.GetIdPago(m.GetStr(tvResultadoGestion))){
                                    case 1: //No Pago

                                        tvMotivoNoPago.setText(row.getString(10));

                                        if (!row.getString(11).isEmpty()){//FECHA DE DEFUNCION
                                            SelectMotivoNoPago(m.GetIdMotivoNoPago(m.GetStr(tvMotivoNoPago)));
                                            tvFechaDefuncion.setText(row.getString(11));
                                            tvFechaDefuncion.setError(null);
                                            tvFechaDefuncion.setVisibility(View.VISIBLE);
                                        }

                                        if (!row.getString(6).isEmpty()){//COMENTARIO
                                            etComentario.setText(row.getString(6));
                                            etComentario.setVisibility(View.VISIBLE);
                                            etComentario.setError(null);
                                        }

                                        if (!row.getString(18).isEmpty() && !row.getString(19).isEmpty()){//FACHADA
                                            File fachadaFile = new File(ROOT_PATH + "Fachada/"+row.getString(18));
                                            Uri uriFachada = Uri.fromFile(fachadaFile);
                                            Glide.with(ctx).load(uriFachada).into(ivFachada);
                                            ibFachada.setVisibility(View.GONE);
                                            ivFachada.setVisibility(View.VISIBLE);
                                            byteEvidencia = m.getBytesUri(ctx, uriFachada, 1);
                                            tvFachada.setError(null);
                                        }

                                        tvGerente.setVisibility(View.VISIBLE);
                                        if (!row.getString(20).isEmpty()){//ESTA GERENTE
                                            tvGerente.setText(row.getString(20));
                                            SelectEstaGerente(m.GetIdConfirmacion(m.GetStr(tvGerente)));

                                            if (m.GetIdConfirmacion(m.GetStr(tvGerente)) == 0){

                                                if (!row.getString(21).isEmpty()){//FIRMA
                                                    File firmaFile = new File(ROOT_PATH + "Firma/"+row.getString(21));
                                                    Uri uriFirma = Uri.fromFile(firmaFile);
                                                    Glide.with(ctx).load(uriFirma).into(ivFirma);
                                                    ibFirma.setVisibility(View.GONE);
                                                    ivFirma.setVisibility(View.VISIBLE);
                                                    byteFirma = m.getBytesUri(ctx, uriFirma, 1);
                                                    tvFirma.setError(null);
                                                }
                                            }
                                        }
                                        break;
                                    case 0: // Si Pago
                                        if (!row.getString(12).isEmpty()){//MEDIO PAGO
                                            tvMedioPago.setText(row.getString(12));
                                            medio_pago_anterio = m.GetMedioPagoId(m.GetStr(tvMedioPago));
                                            SelectMedioPago(m.GetMedioPagoId(m.GetStr(tvMedioPago)));
                                            if (!row.getString(14).isEmpty()){//DETALLE DE FICHA

                                                tvDetalleFicha.setText(row.getString(14));
                                                SelectDetalleFicha(m.PagoRequerido(tvDetalleFicha));
                                                if (m.PagoRequerido(tvDetalleFicha) == 0){
                                                    Cursor row_pago = dBhelper.getRecords(TBL_MIEMBROS_PAGOS_T, " WHERE id_gestion = ?", "", new String[]{parent.id_respuesta});

                                                    if (row_pago.getCount() > 0)
                                                        rbIntegrantes.setChecked(true);

                                                    row_pago.close();
                                                }
                                                etPagoRealizado.setText(row.getString(15));

                                            }

                                            if (!row.getString(13).isEmpty()){//FECHA DEPOSITO
                                                tvFechaDeposito.setText(row.getString(13));
                                                tvFechaDeposito.setError(null);
                                            }

                                            if (m.GetMedioPagoId(m.GetStr(tvMedioPago)) == 6){ //EFECTIVO
                                                if (!row.getString(16).isEmpty()){//IMPRIMIRA RECIBOS
                                                    tvImprimirRecibo.setText(row.getString(16));
                                                    SelectImprimirRecibos(m.GetIdImpresion(m.GetStr(tvImprimirRecibo)));
                                                    etFolioRecibo.setEnabled(true);
                                                    rbArqueoCaja.setChecked(row.getInt(27) == 1);

                                                    if (m.GetIdImpresion(m.GetStr(tvImprimirRecibo)) == 0){ //SI IMPRIMIRA RECIBOS

                                                        if (!row.getString(17).isEmpty()){//FOLIO
                                                            etPagoRealizado.setEnabled(false);
                                                            etPagoRealizado.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));

                                                            tvContacto.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
                                                            tvContacto.setEnabled(false);
                                                            tvResultadoGestion.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
                                                            tvResultadoGestion.setEnabled(false);
                                                            tvMedioPago.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
                                                            tvMedioPago.setEnabled(false);
                                                            tvDetalleFicha.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
                                                            tvDetalleFicha.setEnabled(false);
                                                            ibIntegrantes.setEnabled(false);
                                                            tvImprimirRecibo.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
                                                            tvImprimirRecibo.setEnabled(false);
                                                            ibImprimir.setVisibility(View.VISIBLE);

                                                            etFolioRecibo.setEnabled(false);
                                                            llFolioRecibo.setVisibility(View.VISIBLE);
                                                            etFolioRecibo.setText(row.getString(17));
                                                            etFolioRecibo.setError(null);
                                                            folio_impreso = row.getString(17);
                                                        }
                                                        else {
                                                            ibImprimir.setVisibility(View.VISIBLE);
                                                        }
                                                    }
                                                    else{
                                                        ibImprimir.setVisibility(View.GONE);
                                                        llFolioRecibo.setVisibility(View.VISIBLE);
                                                        etFolioRecibo.setText(row.getString(17));
                                                        etFolioRecibo.setError(null);
                                                    }
                                                }
                                            }
                                            else if (m.GetMedioPagoId(m.GetStr(tvMedioPago)) == 7){
                                                ibImprimir.setVisibility(View.GONE);
                                                llFolioRecibo.setVisibility(View.VISIBLE);
                                                etFolioRecibo.setText(row.getString(17));
                                                etFolioRecibo.setError(null);
                                            }

                                            if (!row.getString(18).isEmpty() && !row.getString(19).isEmpty()){//FOTOGRAFIA O GALERIA
                                                File evidenciaFile = new File(ROOT_PATH + "Evidencia/"+row.getString(18));
                                                Uri uriEvidencia = Uri.fromFile(evidenciaFile);
                                                Glide.with(ctx).load(uriEvidencia).centerCrop().into(ivEvidencia);
                                                ibFoto.setVisibility(View.GONE);
                                                ibGaleria.setVisibility(View.GONE);
                                                ivEvidencia.setVisibility(View.VISIBLE);
                                                byteEvidencia = m.getBytesUri(ctx, uriEvidencia, 1);
                                                tvFotoGaleria.setError(null);
                                            }

                                            if (!row.getString(20).isEmpty()){//ESTA GERENTE
                                                tvGerente.setText(row.getString(20));

                                                SelectEstaGerente(m.GetIdConfirmacion(m.GetStr(tvGerente)));
                                                if (m.GetIdConfirmacion(m.GetStr(tvGerente)) == 0){//SI ESTA GERENTE
                                                    if (!row.getString(21).isEmpty()){//FIRMA
                                                        File firmaFile = new File(ROOT_PATH + "Firma/"+row.getString(21));
                                                        Uri uriFirma = Uri.fromFile(firmaFile);
                                                        Glide.with(ctx).load(uriFirma).into(ivFirma);
                                                        ibFirma.setVisibility(View.GONE);
                                                        ivFirma.setVisibility(View.VISIBLE);
                                                        byteFirma = m.getBytesUri(ctx, uriFirma, 1);
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
                            SelectContactoCliente(m.GetIdContacto(m.GetStr(tvContacto)));
                            if (!row.getString(6).isEmpty()){//COMENTARIO
                                etComentario.setText(row.getString(6));
                                etComentario.setVisibility(View.VISIBLE);
                                etComentario.setError(null);
                            }

                            if (!row.getString(18).isEmpty() && !row.getString(19).isEmpty()){
                                File fachadaFile = new File(ROOT_PATH + "Fachada/"+row.getString(18));
                                Uri uriFachada = Uri.fromFile(fachadaFile);
                                Glide.with(ctx).load(uriFachada).into(ivFachada);
                                ibFachada.setVisibility(View.GONE);
                                ivFachada.setVisibility(View.VISIBLE);
                                byteEvidencia = m.getBytesUri(ctx, uriFachada, 1);
                                tvFachada.setError(null);
                            }

                            tvGerente.setVisibility(View.VISIBLE);
                            if (!row.getString(20).isEmpty()){//ESTA GERENTE
                                tvGerente.setText(row.getString(20));
                                SelectEstaGerente(m.GetIdConfirmacion(m.GetStr(tvGerente)));

                                if (m.GetIdConfirmacion(m.GetStr(tvGerente)) == 0){
                                    if (!row.getString(21).isEmpty()){
                                        File firmaFile = new File(ROOT_PATH + "Firma/"+row.getString(21));
                                        Uri uriFirma = Uri.fromFile(firmaFile);
                                        Glide.with(ctx).load(uriFirma).into(ivFirma);
                                        ibFirma.setVisibility(View.GONE);
                                        ivFirma.setVisibility(View.VISIBLE);
                                        byteFirma = m.getBytesUri(ctx, uriFirma, 1);
                                        tvFirma.setError(null);
                                    }
                                }
                            }
                            break;
                        case 2:
                            SelectContactoCliente(m.GetIdContacto(m.GetStr(tvContacto)));
                            if (!row.getString(5).isEmpty()){//MOTIVO ACLARACION
                                tvMotivoAclaracion.setText(row.getString(5));
                                tvMotivoAclaracion.setVisibility(View.VISIBLE);
                                tvMotivoAclaracion.setError(null);

                            }
                            if (!row.getString(6).isEmpty()){//COMENTARIO
                                etComentario.setText(row.getString(6));
                                etComentario.setVisibility(View.VISIBLE);
                                etComentario.setError(null);
                            }

                            tvGerente.setVisibility(View.VISIBLE);
                            if (!row.getString(20).isEmpty()){//ESTA GERENTE
                                tvGerente.setText(row.getString(20));
                                SelectEstaGerente(m.GetIdConfirmacion(m.GetStr(tvGerente)));
                                if (m.GetIdConfirmacion(m.GetStr(tvGerente)) == 0){
                                    if (!row.getString(21).isEmpty()){
                                        File firmaFile = new File(ROOT_PATH + "Firma/"+row.getString(21));
                                        Uri uriFirma = Uri.fromFile(firmaFile);
                                        Glide.with(ctx).load(uriFirma).into(ivFirma);
                                        ibFirma.setVisibility(View.GONE);
                                        ivFirma.setVisibility(View.VISIBLE);
                                        byteFirma = m.getBytesUri(ctx, uriFirma, 1);
                                        tvFirma.setError(null);
                                    }
                                }
                            }
                            break;
                    }
                }
            }
            row.close();
        }
    }

    private void Update(String key, String value) {
        Log.e("update", key+": "+value);
        ContentValues cv = new ContentValues();
        cv.put(key, value);

        db.update(TBL_RESPUESTAS_GPO_T, cv, "id_prestamo = ? AND _id = ?" ,new String[]{parent.id_prestamo, parent.id_respuesta});
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
                            Update("firma", m.save(byteFirma, 3));
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
                            Update("evidencia", m.save(byteEvidencia, 1));
                            Update("tipo_imagen", "FACHADA");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                break;
            case REQUEST_CODE_INTEGRANTES_GPO:
                if (resultCode == Activity.RESULT_OK){
                    if (data != null){
                        //DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
                        //DecimalFormat dFormat = new DecimalFormat("#.00",symbols);
                        etPagoRealizado.setError(null);
                        etPagoRealizado.setText(nFormat.format(Double.parseDouble(data.getStringExtra(RESPONSE))));
                        rbIntegrantes.setChecked(true);
                        if (m.GetStr(tvMedioPago).equals("EFECTIVO")){
                            if (Double.parseDouble(m.GetStr(etPagoRealizado).replace(",","")) >= 10000)
                                llArqueoCaja.setVisibility(View.VISIBLE);
                            else
                                llArqueoCaja.setVisibility(View.GONE);
                        }
                    }
                }
                else
                    etPagoRealizado.setText("0");
                break;
            case REQUEST_CODE_IMPRESORA:
                if (resultCode == Activity.RESULT_OK){
                    if (data != null){
                        Toast.makeText(ctx, data.getStringExtra(MESSAGE), Toast.LENGTH_SHORT).show();
                        if(data.getIntExtra(RES_PRINT,0) == 1 || data.getIntExtra(RES_PRINT,0) == 2){
                            if (parent.tipo_cartera.contains("VENCIDA"))
                                folio_impreso = "CV"+session.getUser().get(0) + "-" + String.valueOf(data.getIntExtra(FOLIO,0));
                            else
                                folio_impreso = "RC"+session.getUser().get(0) + "-" + String.valueOf(data.getIntExtra(FOLIO,0));
                            etFolioRecibo.setText(folio_impreso);
                            tvImprimirRecibo.setError(null);
                            llFolioRecibo.setVisibility(View.VISIBLE);
                            res_impresion = data.getIntExtra(RES_PRINT, 0);

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
                        byteEvidencia = m.getBytesUri(ctx, imageUri, 0);

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
                            Update("evidencia", m.save(byteEvidencia, 2));
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
                        byteEvidencia = m.getBytesUri(ctx, imageUri, 0);

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
                            Update("evidencia", m.save(byteEvidencia, 2));
                            Update("tipo_imagen", "GALERIA");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } catch (Exception e) {
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
                            Update("evidencia", m.save(byteEvidencia, 2));
                            Update("tipo_imagen", "FOTOGRAFIA");
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
                            Update("arqueo_caja","1");
                            rbArqueoCaja.setChecked(true);
                        }
                    }
                }
                break;
            case 123: //Fecha de defuncion
                if (resultCode == 321){
                    if (data != null){
                        tvFechaDefuncion.setError(null);
                        tvFechaDefuncion.setText(data.getStringExtra(DATE));
                        Update("fecha_fallecimiento", m.GetStr(tvFechaDefuncion));
                    }
                }
                break;
            case 812: //Fecha de pago
                if (resultCode == 321){
                    if (data != null){
                        tvFechaDeposito.setError(null);
                        tvFechaDeposito.setText(data.getStringExtra(DATE));
                        Update("fecha_pago", m.GetStr(tvFechaDeposito));
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
                                db.update(TBL_RESPUESTAS_GPO_T, cv, "_id = ?", new String[]{parent.id_respuesta});
                            }
                        }

                        ContentValues cv = new ContentValues();if (data.hasExtra(ESTATUS)) {
                            cv.put("estatus_pago", data.getStringExtra(ESTATUS));
                            cv.put("saldo_corte", data.getStringExtra(SALDO_CORTE));
                            cv.put("saldo_actual", data.getStringExtra(SALDO_ACTUAL));
                        }
                        cv.put("dias_atraso", m.GetDiasAtraso(parent.fecha_establecida));
                        cv.put("fecha_fin", data.getStringExtra(FECHA_FIN));
                        cv.put("estatus", "1");

                        db.update(TBL_RESPUESTAS_GPO_T, cv, "id_prestamo = ? AND _id = ?" ,new String[]{parent.id_prestamo, parent.id_respuesta});

                        Cursor row;
                        String sql = "SELECT * FROM " + TBL_RESPUESTAS_GPO_T + " WHERE id_prestamo = ? AND contacto = ? AND resultado_gestion = ? AND estatus IN (?,?)";
                        row = db.rawQuery(sql, new String[]{parent.id_prestamo, "SI", "PAGO", "1", "2"});

                        if (row.getCount() > 0){
                            row.moveToFirst();

                            String sqlAmortiz = "SELECT _id, total, total_pagado, pagado, fecha, numero FROM " + TBL_AMORTIZACIONES_T + " WHERE id_prestamo = ? AND CAST(total AS DOUBLE) > CAST(total_pagado AS DOUBLE) ORDER BY numero ASC";
                            Cursor row_amortiz = db.rawQuery(sqlAmortiz, new String[]{parent.id_prestamo});
                            if (row_amortiz.getCount() > 0){
                                row_amortiz.moveToFirst();
                                Double abono;
                                if (!m.GetStr(etPagoRealizado).isEmpty() && m.GetStr(tvResultadoGestion).equals("PAGO"))
                                    abono = Double.parseDouble(m.GetStr(etPagoRealizado).replace(",", ""));
                                else
                                    abono = 0.0;

                                for (int i = 0; i < row_amortiz.getCount(); i++){

                                    Double pendiente = row_amortiz.getDouble(1) - row_amortiz.getDouble(2);

                                    if (abono > pendiente){
                                        ContentValues cv_amortiz = new ContentValues();
                                        cv_amortiz.put("total_pagado", row_amortiz.getString(1));
                                        cv_amortiz.put("pagado", "PAGADO");
                                        cv_amortiz.put("dias_atraso", m.GetDiasAtraso(row_amortiz.getString(4)));
                                        db.update(TBL_AMORTIZACIONES_T, cv_amortiz, "id_prestamo = ? AND numero = ?", new String[]{parent.id_prestamo, row_amortiz.getString(5)});
                                        abono = abono - pendiente;
                                    }
                                    else if (abono == pendiente){
                                        ContentValues cv_amortiz = new ContentValues();
                                        cv_amortiz.put("total_pagado", row_amortiz.getString(1));
                                        cv_amortiz.put("pagado", "PAGADO");
                                        cv_amortiz.put("dias_atraso", m.GetDiasAtraso(row_amortiz.getString(4)));
                                        db.update(TBL_AMORTIZACIONES_T, cv_amortiz, "id_prestamo = ? AND numero = ?", new String[]{parent.id_prestamo, row_amortiz.getString(5)});
                                        abono = 0.0;
                                    }
                                    else if (abono > 0 && abono < pendiente){
                                        ContentValues cv_amortiz = new ContentValues();
                                        cv_amortiz.put("total_pagado", (row_amortiz.getDouble(2) + abono));
                                        cv_amortiz.put("pagado", "PARCIAL");
                                        abono = 0.0;
                                        cv_amortiz.put("dias_atraso", m.GetDiasAtraso(row_amortiz.getString(4)));
                                        db.update(TBL_AMORTIZACIONES_T, cv_amortiz, "id_prestamo = ? AND numero = ?", new String[]{parent.id_prestamo, row_amortiz.getString(5)});

                                    }
                                    else
                                        break;

                                    row_amortiz.moveToNext();
                                }

                            }
                            row_amortiz.close();

                            sqlAmortiz = "SELECT SUM(a.total_pagado) AS suma_pagos, p.monto_total FROM " + TBL_AMORTIZACIONES_T + " AS a INNER JOIN "+TBL_PRESTAMOS_GPO_T+" AS p ON p.id_prestamo = a.id_prestamo WHERE a.id_prestamo = ?";
                            row_amortiz = db.rawQuery(sqlAmortiz, new String[]{parent.id_prestamo});

                            if (row_amortiz.getCount() > 0){
                                row_amortiz.moveToFirst();
                                if (row_amortiz.getDouble(0) >= row_amortiz.getDouble(1)){
                                    ContentValues c = new ContentValues();
                                    c.put("pagada", 1);
                                    db.update(TBL_PRESTAMOS_GPO_T, c, "id_prestamo = ?", new String[]{parent.id_prestamo});
                                }

                            }
                            row_amortiz.close();


                            int weekFechaEst = 0;
                            Calendar calFechaEst = Calendar.getInstance();

                            try {
                                Date dFechaEstablecida = sdf.parse(m.ObtenerFecha(FECHA.toLowerCase()));
                                calFechaEst.setTime(dFechaEstablecida);
                                weekFechaEst = calFechaEst.get(Calendar.WEEK_OF_YEAR);

                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                            double sumPago = 0;
                            for (int i = 0; i < row.getCount(); i++){
                                String[] fechaFinGes = row.getString(23).split(" ");
                                Date dFechaEstablecida = null;
                                try {
                                    dFechaEstablecida = sdf.parse(fechaFinGes[0]);
                                    calFechaEst.setTime(dFechaEstablecida);
                                    if (calFechaEst.get(Calendar.WEEK_OF_YEAR) == weekFechaEst){
                                        sumPago += row.getDouble(15);
                                    }
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }

                                row.moveToNext();
                            }
                            try {
                                if (sumPago >= parent.monto_requerido){
                                    ContentValues cvGpo = new ContentValues();
                                    cvGpo.put("is_ruta", 0);
                                    cvGpo.put("ruta_obligado", 0);

                                    db.update(TBL_CARTERA_GPO_T, cvGpo, "id_cartera = ?", new String[]{parent.grupo_id});
                                }
                            }catch (NumberFormatException e){

                            }
                        }
                        row.close();

                        HashMap<Integer, String> values = new HashMap();
                        values.put(0, parent.id_respuesta);
                        values.put(1, data.getStringExtra(NOMBRE));
                        values.put(2, parent.nombre);
                        values.put(3, "2");
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

    private void GuardarGestion(){
        Validator validator = new Validator();
        Bundle b = new Bundle();
        Log.e("Latitud", parent.latitud);
        Log.e("Longitud", parent.longitud);
        b.putString(NOMBRE, parent.nombre);
        if (latLngGestion != null || (!parent.latitud.trim().isEmpty() && !parent.longitud.trim().isEmpty())) {
            if (latLngGestion != null) {
                b.putDouble(Constants.LATITUD, latLngGestion.latitude);
                b.putDouble(Constants.LONGITUD, latLngGestion.longitude);
            }
            else {
                b.putDouble(Constants.LATITUD, 0);
                b.putDouble(Constants.LONGITUD, 0);
            }
            if (m.GetIdContacto(m.GetStr(tvContacto)) == 0){ //Si Contacto cliente
                b.putString(CONTACTO, m.GetStr(tvContacto));
                if (!m.GetStr(tvActualizarTelefono).isEmpty()){
                    if ((m.GetIdConfirmacion(m.GetStr(tvActualizarTelefono)) == 0 && !validator.validate(etActualizarTelefono, new String[]{validator.REQUIRED,validator.PHONE})) || m.GetIdConfirmacion(m.GetStr(tvActualizarTelefono)) == 1){
                        b.putString(ACTUALIZAR_TELEFONO, m.GetStr(tvActualizarTelefono));
                        if (m.GetIdConfirmacion(m.GetStr(tvActualizarTelefono)) == 0){
                            b.putString(NUEVO_TELEFONO, m.GetStr(etActualizarTelefono));
                        }
                        if (m.GetIdPago(m.GetStr(tvResultadoGestion)) == 0) { // Si pago
                            b.putString(RESULTADO_PAGO, m.GetStr(tvResultadoGestion));
                            if (m.GetMedioPagoId(m.GetStr(tvMedioPago)) >= 0 && m.GetMedioPagoId(m.GetStr(tvMedioPago)) < 6 || m.GetMedioPagoId(m.GetStr(tvMedioPago)) == 7) { // Medio de pago Bancos, Oxxo, SIDERT
                                b.putString(MEDIO_PAGO, m.GetStr(tvMedioPago));
                                if (!m.GetStr(tvFechaDeposito).isEmpty()) {
                                    b.putString(FECHA_DEPOSITO, m.GetStr(tvFechaDeposito));
                                    if (!m.GetStr(tvDetalleFicha).isEmpty()) { //Selecionó que cuenta con el detalle o no
                                        b.putString(DETALLE_FICHA, m.GetStr(tvDetalleFicha));
                                        if (!m.GetStr(etPagoRealizado).isEmpty() && Double.parseDouble(m.GetStr(etPagoRealizado).replace(",","")) > 0) { //El pago realizado es mayor a cero
                                            b.putDouble(SALDO_CORTE, parent.saldo_corte);
                                            b.putDouble(MONTO_REQUERIDO, parent.monto_requerido);
                                            b.putString(PAGO_REALIZADO, m.GetStr(etPagoRealizado).replace(",",""));

                                            if (m.GetMedioPagoId(m.GetStr(tvMedioPago)) == 7) {//SIDERT
                                                if (m.GetIdImpresion(m.GetStr(tvImprimirRecibo)) == 1) { //No imprimirá recibos
                                                    if (!m.GetStr(etFolioRecibo).isEmpty()) {
                                                        b.putString(IMPRESORA, m.GetStr(tvImprimirRecibo));
                                                        b.putString(FOLIO_TICKET, m.GetStr(etFolioRecibo));
                                                    }
                                                    else
                                                        Toast.makeText(ctx, "No ha capturado el folio del recibo manual", Toast.LENGTH_SHORT).show();
                                                }
                                            }

                                            if (byteEvidencia != null) { //Ha capturado una evidencia (Fotografía al ticket)
                                                b.putByteArray(EVIDENCIA, byteEvidencia);
                                                if (m.GetIdConfirmacion(m.GetStr(tvGerente)) == 0) { //Selecciono que si está el gerente
                                                    b.putString(GERENTE, m.GetStr(tvGerente));
                                                    if (byteFirma != null) { //Capturó una firma
                                                        b.putByteArray(FIRMA, byteFirma);
                                                        if ((m.PagoRequerido(tvDetalleFicha) == 0 && rbIntegrantes.isChecked()) || m.PagoRequerido(tvDetalleFicha) == 1) {
                                                            if (m.PagoRequerido(tvDetalleFicha) == 0) {
                                                                b.putBoolean(RESUMEN_INTEGRANTES, true);
                                                            } else {
                                                                b.putBoolean(RESUMEN_INTEGRANTES, false);
                                                            }
                                                            b.putBoolean(TERMINADO,true);

                                                        }
                                                        else
                                                            Toast.makeText(ctx, "No ha capturado el pago de los integrantes", Toast.LENGTH_SHORT).show();
                                                    } else //No ha capturado la firma
                                                        Toast.makeText(parent, "Capture la firma del gerente", Toast.LENGTH_SHORT).show();
                                                } else if (m.GetIdConfirmacion(m.GetStr(tvGerente)) == 1) { //No se encuentra el Gerente
                                                    b.putString(GERENTE, m.GetStr(tvGerente));
                                                    if ((m.PagoRequerido(tvDetalleFicha) == 0 && rbIntegrantes.isChecked()) || m.PagoRequerido(tvDetalleFicha) == 1) {

                                                        if (m.PagoRequerido(tvDetalleFicha) == 0) {
                                                            b.putBoolean(RESUMEN_INTEGRANTES, true);
                                                        } else {
                                                            b.putBoolean(RESUMEN_INTEGRANTES, false);
                                                        }
                                                        b.putBoolean(TERMINADO, true);
                                                    }
                                                    else
                                                        Toast.makeText(ctx, "No ha capturado el pago de los integrantes", Toast.LENGTH_SHORT).show();
                                                } else //No ha seleccionado si está el gerente
                                                    Toast.makeText(parent, "Confirme si el gerente está con usted", Toast.LENGTH_SHORT).show();
                                            } else //No ha capturado fotografía evidencia
                                                Toast.makeText(ctx, "No ha capturado una fotografía al ticket", Toast.LENGTH_SHORT).show();
                                        } else //El monto ingresado es igual a cero
                                            Toast.makeText(ctx, "No se pueden capturar montos iguales a cero", Toast.LENGTH_SHORT).show();
                                    } else // No ha seleccionado si tiene el detalle de la ficha
                                        Toast.makeText(ctx, "No se seleccionado si cuenta con el detalle de la ficha", Toast.LENGTH_SHORT).show();
                                } else {
                                    tvFechaDeposito.setError("");
                                    Toast.makeText(ctx, "No ha ingresado la fecha de depósito", Toast.LENGTH_SHORT).show();
                                }
                            } else if (m.GetMedioPagoId(m.GetStr(tvMedioPago)) == 6) { //Medio de pago Efectivo
                                b.putString(MEDIO_PAGO, m.GetStr(tvMedioPago));
                                if (!m.GetStr(tvDetalleFicha).isEmpty()) { //Selecionó que cuenta con el detalle o no
                                    b.putString(DETALLE_FICHA, m.GetStr(tvDetalleFicha));
                                    if (!m.GetStr(etPagoRealizado).isEmpty() && Double.parseDouble(m.GetStr(etPagoRealizado).replace(",","")) > 0) { //El pago realizado es mayor a cero
                                        b.putDouble(SALDO_CORTE, parent.saldo_corte);
                                        b.putDouble(MONTO_REQUERIDO, parent.monto_requerido);
                                        b.putString(PAGO_REALIZADO, m.GetStr(etPagoRealizado).replace(",",""));
                                        if (m.GetIdImpresion(m.GetStr(tvImprimirRecibo)) == 0) { //Si imprimirá recibos
                                            b.putString(IMPRESORA, m.GetStr(tvImprimirRecibo));
                                            if (!m.GetStr(etFolioRecibo).isEmpty()) {
                                                b.putString(FOLIO_TICKET, m.GetStr(etFolioRecibo));
                                                if (byteEvidencia != null) { //Ha capturado una evidencia (Fotografía al ticket)
                                                    b.putByteArray(EVIDENCIA, byteEvidencia);
                                                    if (m.GetIdConfirmacion(m.GetStr(tvGerente)) == 0) { //Selecciono que si está el gerente
                                                        b.putString(GERENTE, m.GetStr(tvGerente));
                                                        if (byteFirma != null) { //Capturó una firma
                                                            b.putByteArray(FIRMA, byteFirma);
                                                            if ((m.PagoRequerido(tvDetalleFicha) == 0 && rbIntegrantes.isChecked()) || m.PagoRequerido(tvDetalleFicha) == 1) {
                                                                b.putString(PAGO_REALIZADO, m.GetStr(etPagoRealizado).replace(",",""));
                                                                if (m.PagoRequerido(tvDetalleFicha) == 0) {
                                                                    b.putBoolean(RESUMEN_INTEGRANTES, true);
                                                                } else {
                                                                    b.putBoolean(RESUMEN_INTEGRANTES, false);
                                                                }
                                                                b.putBoolean(TERMINADO, true);
                                                            }
                                                            else
                                                                Toast.makeText(ctx, "No ha capturado el pago de los integrantes", Toast.LENGTH_SHORT).show();
                                                        } else //No ha capturado la firma
                                                            Toast.makeText(ctx, "Capture la firma del gerente", Toast.LENGTH_SHORT).show();
                                                    } else if (m.GetIdConfirmacion(m.GetStr(tvGerente)) == 1) { //No se encuentra el Gerente
                                                        if ((m.PagoRequerido(tvDetalleFicha) == 0 && rbIntegrantes.isChecked()) || m.PagoRequerido(tvDetalleFicha) == 1) {
                                                            if (m.PagoRequerido(tvDetalleFicha) == 0) {
                                                                b.putBoolean(RESUMEN_INTEGRANTES, true);
                                                            } else {
                                                                b.putBoolean(RESUMEN_INTEGRANTES, false);
                                                            }
                                                            b.putString(GERENTE, m.GetStr(tvGerente));
                                                            b.putBoolean(TERMINADO, true);
                                                        }
                                                        else
                                                            Toast.makeText(ctx, "No ha capturado el pago de los integrantes", Toast.LENGTH_SHORT).show();
                                                    } else //No ha seleccionado si está el gerente
                                                        Toast.makeText(ctx, "Confirme si el gerente está con usted", Toast.LENGTH_SHORT).show();
                                                } else //No ha capturado fotografía evidencia
                                                    Toast.makeText(ctx, "No ha capturado una fotografía al ticket", Toast.LENGTH_SHORT).show();
                                            } else //No ha impreso ningun ticket
                                                Toast.makeText(ctx, "No ha realizado nignuna impresión", Toast.LENGTH_SHORT).show();
                                        } else if (m.GetIdImpresion(m.GetStr(tvImprimirRecibo)) == 1) { //No imprimirá recibos
                                            b.putString(IMPRESORA, m.GetStr(tvImprimirRecibo));
                                            if (!m.GetStr(etFolioRecibo).isEmpty()) {
                                                b.putString(FOLIO_TICKET, m.GetStr(etFolioRecibo));
                                                if (byteEvidencia != null) { //Ha capturado una evidencia (Fotografía al ticket)
                                                    b.putByteArray(EVIDENCIA, byteEvidencia);
                                                    if (m.GetIdConfirmacion(m.GetStr(tvGerente)) == 0) { //Selecciono que si está el gerente
                                                        b.putString(GERENTE, m.GetStr(tvGerente));
                                                        if (byteFirma != null) { //Capturó una firma
                                                            if ((m.PagoRequerido(tvDetalleFicha) == 0 && rbIntegrantes.isChecked()) || m.PagoRequerido(tvDetalleFicha) == 1 ) {
                                                                b.putString(PAGO_REALIZADO, m.GetStr(etPagoRealizado).replace(",",""));
                                                                b.putByteArray(EVIDENCIA, byteEvidencia);
                                                                b.putString(TERMINADO, "SI");
                                                            }
                                                            else
                                                                Toast.makeText(ctx, "No ha capturado el pago de los integrantes", Toast.LENGTH_SHORT).show();
                                                        } else //No ha capturado la firma
                                                            Toast.makeText(ctx, "Capture la firma del gerente", Toast.LENGTH_SHORT).show();
                                                    } else if (m.GetIdConfirmacion(m.GetStr(tvGerente)) == 1) { //No se encuentra el Gerente
                                                        if ((m.PagoRequerido(tvDetalleFicha) == 0 && rbIntegrantes.isChecked()) || m.PagoRequerido(tvDetalleFicha) == 1) {
                                                            b.putString(PAGO_REALIZADO, m.GetStr(etPagoRealizado).replace(",",""));
                                                            b.putSerializable(GERENTE, "NO");
                                                            b.putBoolean(TERMINADO, true);
                                                        }
                                                        else
                                                            Toast.makeText(ctx, "No ha capturado el pago de los integrantes", Toast.LENGTH_SHORT).show();
                                                    } else //No ha seleccionado si está el gerente
                                                        Toast.makeText(ctx, "Confirme si el gerente está con usted", Toast.LENGTH_SHORT).show();
                                                } else //No ha capturado fotografía evidencia
                                                    Toast.makeText(ctx, "No ha capturado una fotografía al ticket", Toast.LENGTH_SHORT).show();
                                            } else
                                                Toast.makeText(ctx, "No ha capturado el folio del recibo", Toast.LENGTH_SHORT).show();
                                        } else //No ha seleccionado si imprimirá recibos
                                            Toast.makeText(ctx, "No ha confirmado si va imprimir recibos", Toast.LENGTH_SHORT).show();
                                    } else //El monto ingresado es igual a cero
                                        Toast.makeText(ctx, "No se pueden capturar montos iguales a cero", Toast.LENGTH_SHORT).show();
                                } else // No ha seleccionado si tiene el detalle de la ficha
                                    Toast.makeText(ctx, "No se seleccionado si cuenta con el detalle de la ficha", Toast.LENGTH_SHORT).show();
                            } else //No ha seleccionado algun medio de pago
                                Toast.makeText(ctx, "No ha seleccionado un medio de pago", Toast.LENGTH_SHORT).show();
                        } // =================  Termina Si Pago  ==============================================
                        else if (m.GetIdPago(m.GetStr(tvResultadoGestion)) == 1) { //No pago
                            b.putString(RESULTADO_PAGO, m.GetStr(tvResultadoGestion));
                            if (m.GetIdMotivoNoPago(m.GetStr(tvMotivoNoPago)) == 0 || m.GetIdMotivoNoPago(m.GetStr(tvMotivoNoPago)) == 2) { //Motivo de no pago Negacion u Otra
                                b.putString(MOTIVO_NO_PAGO, m.GetStr(tvMotivoNoPago));
                                if (!m.GetStr(etComentario).isEmpty()) { //El campo comentario es diferente de vacio
                                    b.putString(COMENTARIO, m.GetStr(etComentario));
                                    if (byteEvidencia != null) {
                                        b.putByteArray(EVIDENCIA, byteEvidencia);

                                        if (m.GetIdConfirmacion(m.GetStr(tvGerente)) == 0) { //Selecciono que si está el gerente
                                            b.putString(GERENTE, m.GetStr(tvGerente));
                                            if (byteFirma != null) { //Capturó una firma
                                                b.putByteArray(FIRMA, byteFirma);
                                                b.putBoolean(TERMINADO, true);
                                            } else { //No ha capturado la firma
                                                Toast.makeText(ctx, "Capture la firma del gerente", Toast.LENGTH_SHORT).show();
                                            }
                                        } else if (m.GetIdConfirmacion(m.GetStr(tvGerente)) == 1) { //No se encuentra el Gerente
                                            b.putString(GERENTE, m.GetStr(tvGerente));
                                            b.putBoolean(TERMINADO, true);
                                        } else //No ha seleccionado si está el gerente
                                            Toast.makeText(ctx, "Confirme si el gerente está con usted", Toast.LENGTH_SHORT).show();
                                    } else //no ha capturado la fotografía de la fachada
                                        Toast.makeText(ctx, "La Fotografía de la fachada es requerida.", Toast.LENGTH_SHORT).show();
                                } else // No ha ingresado alguno comentario
                                    Toast.makeText(ctx, "El campo Comentario es requerido.", Toast.LENGTH_SHORT).show();
                            } else if (m.GetIdMotivoNoPago(m.GetStr(tvMotivoNoPago)) == 1) { //Motivo de no pago fue Fallecimiento
                                b.putString(MOTIVO_NO_PAGO, m.GetStr(tvMotivoNoPago));
                                if (!m.GetStr(tvFechaDefuncion).isEmpty()) { //El campo Fecha es diferente de vacio
                                    b.putString(FECHA_DEFUNCION, m.GetStr(tvFechaDefuncion));
                                    if (!m.GetStr(etComentario).isEmpty()) { // El campo Comentario es diferente de vacio
                                        b.putString(COMENTARIO, m.GetStr(etComentario));
                                        if (byteEvidencia != null) { //Capturo una fotografia de fachada
                                            b.putByteArray(EVIDENCIA, byteEvidencia);
                                            if (m.GetIdConfirmacion(m.GetStr(tvGerente)) == 0) { //Si está el gerente
                                                b.putString(GERENTE, m.GetStr(tvGerente));
                                                if (byteFirma != null) { //Capturó un firma
                                                    b.putByteArray(FIRMA, byteFirma);
                                                    b.putBoolean(TERMINADO, true);
                                                } else //No ha Capturado un Firma
                                                    Toast.makeText(ctx, "Capture la firma del gerente", Toast.LENGTH_SHORT).show();
                                            } else if (m.GetIdConfirmacion(m.GetStr(tvGerente)) == 1) { //No está el gerente
                                                b.putString(GERENTE, m.GetStr(tvGerente));
                                                b.putBoolean(TERMINADO, true);
                                            } else //No ha seleccionado si está el gerente
                                                Toast.makeText(ctx, "Confirme si el gerente está con usted", Toast.LENGTH_SHORT).show();
                                        } else //No ha capturado una fotografia
                                            Toast.makeText(ctx, "La Fotografía de la fachada es requerida.", Toast.LENGTH_SHORT).show();
                                    } else //No ha ingresado algun comentario
                                        Toast.makeText(ctx, "El campo Comentario es requerido.", Toast.LENGTH_SHORT).show();
                                } else //No ha seleccionado la fecha de defuncion
                                    Toast.makeText(ctx, "No ha seleccionado la fecha de defunción", Toast.LENGTH_SHORT).show();
                            } else  //No ha seleccionado algun motivo de no pago
                                Toast.makeText(ctx, "No ha seleccionado motivo de no pago", Toast.LENGTH_SHORT).show();
                        } // =================  Termina No Pago  ==============================================
                        else //No ha seleccionado si pagó o no el cliente
                            Toast.makeText(ctx, "No ha seleccionado el resultado de la gestión", Toast.LENGTH_SHORT).show();
                    }
                    else //No ha ingresado el nuevo teléfono
                        Toast.makeText(ctx, "No ha ingresado el nuevo teléfono", Toast.LENGTH_SHORT).show();
                }
                else //No ha seleccionado si va actualizar el telefono
                    Toast.makeText(ctx, "No ha seleccionado si va actualizar el teléfono", Toast.LENGTH_SHORT).show();
            }// ============  Termina Si Contacto  =============================
            else if(m.GetIdContacto(m.GetStr(tvContacto)) == 1) { //No contactó al cliente
                b.putString(CONTACTO, m.GetStr(tvContacto));
                if (!m.GetStr(etComentario).isEmpty()) { //El campo comentario es diferente de vacio
                    b.putString(COMENTARIO, m.GetStr(etComentario));
                    if (byteEvidencia != null) { //Ha capturado una fotografia de la fachada
                        b.putByteArray(EVIDENCIA, byteEvidencia);
                        if (m.GetIdConfirmacion(m.GetStr(tvGerente)) == 0) { // Seleccionó que está el gerente
                            if (byteFirma != null) { // Ha capturado un firma
                                b.putString(GERENTE, m.GetStr(tvGerente));
                                b.putByteArray(FIRMA, byteFirma);
                                b.putBoolean(TERMINADO, true);
                            } else //No ha capturado un firma
                                Toast.makeText(ctx, "Capture la firma del gerente", Toast.LENGTH_SHORT).show();
                        } else if (m.GetIdConfirmacion(m.GetStr(tvGerente)) == 1) { //No se encuentra el gerente
                            b.putString(GERENTE, m.GetStr(tvGerente));
                            b.putBoolean(TERMINADO, true);
                        } else //No ha seleccionado si está el gerente
                            Toast.makeText(ctx, "Confirme si el gerente está con usted", Toast.LENGTH_SHORT).show();
                    } else // No ha capturado una fotografia de fachada
                        Toast.makeText(ctx, "La Fotografía de la fachada es requerida.", Toast.LENGTH_SHORT).show();
                } else //No ha ingresado algun comentario
                    Toast.makeText(ctx, "El campo Comentario es obligatorio", Toast.LENGTH_SHORT).show();
            } //============  Termina No Contacto  =============================
            else if(m.GetIdContacto(m.GetStr(tvContacto)) == 2) { //Seleccionó Aclaración
                b.putString(CONTACTO, m.GetStr(tvContacto));
                if (!m.GetStr(tvMotivoAclaracion).isEmpty()) { //Motivo de aclaración es diferente de vacio
                    b.putString(MOTIVO_ACLARACION, m.GetStr(tvMotivoAclaracion));
                    if (!m.GetStr(etComentario).isEmpty()) { // Ingresó algun comentario
                        b.putString(COMENTARIO, m.GetStr(etComentario));
                        if (m.GetIdConfirmacion(m.GetStr(tvGerente)) == 0) { //Seleccionó que está el gerente
                            if (byteFirma != null) { //Ha capturado una firma
                                b.putString(GERENTE, m.GetStr(tvGerente));
                                b.putByteArray(FIRMA, byteFirma);
                                b.putBoolean(TERMINADO, true);
                            }
                            else //No ha capturado una firma
                                Toast.makeText(ctx, "Capture la firma del gerente", Toast.LENGTH_SHORT).show();
                        }
                        else if (m.GetIdConfirmacion(m.GetStr(tvGerente)) == 1) { //Seleccionó que no está el gerente
                            b.putString(GERENTE, m.GetStr(tvGerente));
                            b.putBoolean(TERMINADO, true);
                        }
                        else //No ha confirmado si está el gerente
                            Toast.makeText(ctx, "Confirme si el gerente está con usted", Toast.LENGTH_SHORT).show();
                    }
                    else //No ha ingresado algun comentario
                        Toast.makeText(ctx, "El campo Comentario es obligatorio", Toast.LENGTH_SHORT).show();
                }
                else //No ha seleccionado el motivo de aclaración
                    Toast.makeText(ctx, "Seleccione el motivo de aclaración", Toast.LENGTH_SHORT).show();
            } //============  Termina Aclaración  ==============================
            else //No ha seleccionadosi conectado al cliente o es una aclaración
                Toast.makeText(ctx, "No ha seleccionado si contacto al cliente", Toast.LENGTH_SHORT).show();
        }
        else //No ha capturado la ubicación
            Toast.makeText(ctx,"Falta obtener la ubicación de la gestión", Toast.LENGTH_SHORT).show();

        if (!b.isEmpty() && b.containsKey(TERMINADO)) {
            Intent i_preview = new Intent(parent, VistaPreviaGestion.class);
            i_preview.putExtra(PARAMS, b);
            startActivityForResult(i_preview, REQUEST_CODE_PREVIEW);
        }

    }

    private void DisableFields(){
        tvContacto.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
        tvContacto.setEnabled(false);
        tvResultadoGestion.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
        tvResultadoGestion.setEnabled(false);
        tvMedioPago.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
        tvMedioPago.setEnabled(false);
        tvDetalleFicha.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
        tvDetalleFicha.setEnabled(false);
        ibIntegrantes.setEnabled(false);
        etPagoRealizado.setEnabled(false);
        etPagoRealizado.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
        tvImprimirRecibo.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
        tvImprimirRecibo.setEnabled(false);

    }
}
