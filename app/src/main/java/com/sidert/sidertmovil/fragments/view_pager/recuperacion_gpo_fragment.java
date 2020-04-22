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
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
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
import com.sidert.sidertmovil.activities.CapturarFirma;
import com.sidert.sidertmovil.activities.IntegrantesGpo;
import com.sidert.sidertmovil.activities.PrintSeewoo;
import com.sidert.sidertmovil.activities.RecuperacionGrupal;
import com.sidert.sidertmovil.activities.VistaPreviaGestion;
import com.sidert.sidertmovil.database.DBhelper;
import com.sidert.sidertmovil.fragments.dialogs.dialog_date_picker;
import com.sidert.sidertmovil.models.MImpresion;
import com.sidert.sidertmovil.models.MIntegrantePago;
import com.sidert.sidertmovil.models.OrderModel;
import com.sidert.sidertmovil.utils.CanvasCustom;
import com.sidert.sidertmovil.utils.Constants;
import com.sidert.sidertmovil.utils.Miscellaneous;
import com.sidert.sidertmovil.utils.MyCurrentListener;
import com.sidert.sidertmovil.utils.NameFragments;
import com.sidert.sidertmovil.utils.Popups;
import com.sidert.sidertmovil.utils.SessionManager;
import com.sidert.sidertmovil.utils.Validator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
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
import static com.sidert.sidertmovil.utils.Constants.EFECTIVO;
import static com.sidert.sidertmovil.utils.Constants.ENVIROMENT;
import static com.sidert.sidertmovil.utils.Constants.ESTATUS;
import static com.sidert.sidertmovil.utils.Constants.EVIDENCIA;
import static com.sidert.sidertmovil.utils.Constants.FECHAS_POST;
import static com.sidert.sidertmovil.utils.Constants.FECHA_DEFUNCION;
import static com.sidert.sidertmovil.utils.Constants.FECHA_DEPOSITO;
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
import static com.sidert.sidertmovil.utils.Constants.LATITUD;
import static com.sidert.sidertmovil.utils.Constants.LONGITUD;
import static com.sidert.sidertmovil.utils.Constants.MEDIO_PAGO;
import static com.sidert.sidertmovil.utils.Constants.MONTH_CURRENT;
import static com.sidert.sidertmovil.utils.Constants.MOTIVO_ACLARACION;
import static com.sidert.sidertmovil.utils.Constants.MOTIVO_NO_PAGO;
import static com.sidert.sidertmovil.utils.Constants.NOMBRE_GRUPO;
import static com.sidert.sidertmovil.utils.Constants.NUEVO_TELEFONO;
import static com.sidert.sidertmovil.utils.Constants.ORDER_ID;
import static com.sidert.sidertmovil.utils.Constants.PAGO_REALIZADO;
import static com.sidert.sidertmovil.utils.Constants.PAGO_REQUERIDO;
import static com.sidert.sidertmovil.utils.Constants.PARAMS;
import static com.sidert.sidertmovil.utils.Constants.PICTURE;
import static com.sidert.sidertmovil.utils.Constants.REQUEST_CODE_ARQUEO_CAJA;
import static com.sidert.sidertmovil.utils.Constants.REQUEST_CODE_CAMARA_FACHADA;
import static com.sidert.sidertmovil.utils.Constants.REQUEST_CODE_CAMARA_TICKET;
import static com.sidert.sidertmovil.utils.Constants.REQUEST_CODE_FIRMA;
import static com.sidert.sidertmovil.utils.Constants.REQUEST_CODE_GALERIA;
import static com.sidert.sidertmovil.utils.Constants.REQUEST_CODE_IMPRESORA;
import static com.sidert.sidertmovil.utils.Constants.REQUEST_CODE_INTEGRANTES_GPO;
import static com.sidert.sidertmovil.utils.Constants.RESULTADO_PAGO;
import static com.sidert.sidertmovil.utils.Constants.RESUMEN_INTEGRANTES;
import static com.sidert.sidertmovil.utils.Constants.RES_PRINT;
import static com.sidert.sidertmovil.utils.Constants.ROOT_PATH;
import static com.sidert.sidertmovil.utils.Constants.SALDO_ACTUAL;
import static com.sidert.sidertmovil.utils.Constants.SALDO_CORTE;
import static com.sidert.sidertmovil.utils.Constants.SAVE;
import static com.sidert.sidertmovil.utils.Constants.TBL_MIEMBROS_GPO;
import static com.sidert.sidertmovil.utils.Constants.TBL_MIEMBROS_GPO_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_MIEMBROS_PAGOS;
import static com.sidert.sidertmovil.utils.Constants.TBL_MIEMBROS_PAGOS_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_RESPUESTAS_GPO;
import static com.sidert.sidertmovil.utils.Constants.TBL_RESPUESTAS_GPO_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_RESPUESTAS_IND;
import static com.sidert.sidertmovil.utils.Constants.TBL_RESPUESTAS_IND_T;
import static com.sidert.sidertmovil.utils.Constants.TERMINADO;
import static com.sidert.sidertmovil.utils.Constants.TIMESTAMP;
import static com.sidert.sidertmovil.utils.Constants.TIPO;
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


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_recuperacion_gpo, container, false);

        ctx = getContext();
        session         = new SessionManager(ctx);
        dBhelper        = new DBhelper(ctx);
        db              = dBhelper.getWritableDatabase();

        _contacto = getResources().getStringArray(R.array.contacto_cliente);
        _motivo_aclaracion = getResources().getStringArray(R.array.outdated_information);
        _confirmacion = getResources().getStringArray(R.array.confirmacion);
        _resultado_gestion = getResources().getStringArray(R.array.resultado_gestion);
        _motivo_no_pago = getResources().getStringArray(R.array.reason_no_pay);
        _medio_pago = getResources().getStringArray(R.array.medio_pago);
        _imprimir = getResources().getStringArray(R.array.imprimir);

        parent                = (RecuperacionGrupal) getActivity();
        assert parent != null;
        parent.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

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
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    if (!etPagoRealizado.getText().toString().trim().isEmpty()) {
                        if (Double.parseDouble(etPagoRealizado.getText().toString().trim()) > 0) {
                            Update("pago_realizado", etPagoRealizado.getText().toString().trim());
                            if (Double.parseDouble(etPagoRealizado.getText().toString().trim()) >= 10000 && Miscellaneous.MedioPago(tvMedioPago) == 6)
                                llArqueoCaja.setVisibility(View.VISIBLE);
                            else
                                llArqueoCaja.setVisibility(View.GONE);
                        }
                    }
                }
                else{
                    Update("pago_realizado", "");
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
                if (Miscellaneous.MedioPago(tvMedioPago) == 7){
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

                    Cursor row;
                    if (ENVIROMENT)
                        row = dBhelper.getRecords(TBL_RESPUESTAS_GPO, " WHERE id_prestamo = ?", " ORDER BY _id ASC",new String[]{parent.id_prestamo});
                    else
                        row = dBhelper.getRecords(TBL_RESPUESTAS_GPO_T, " WHERE id_prestamo = ?", " ORDER BY _id ASC",new String[]{parent.id_prestamo});
                    row.moveToLast();
                    if (row.getCount() == 0){
                        HashMap<Integer, String> params = new HashMap<>();
                        params.put(0,parent.id_prestamo);
                        params.put(1, latitud);
                        params.put(2, longitud);
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
                        params.put(21, Miscellaneous.ObtenerFecha(TIMESTAMP));
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
                        if (row.getInt(25) == 1 || row.getInt(25) == 2){
                            HashMap<Integer, String> params = new HashMap<>();
                            params.put(0,parent.id_prestamo);
                            params.put(1, latitud);
                            params.put(2, longitud);
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
                            params.put(21, Miscellaneous.ObtenerFecha(TIMESTAMP));
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

            locationManager.requestSingleUpdate(LocationManager.NETWORK_PROVIDER, locationListener,null);

        }
    };

    private View.OnClickListener ibIntegrantes_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Cursor row;
            if (ENVIROMENT)
                row = dBhelper.getRecords(TBL_MIEMBROS_PAGOS, " WHERE id_gestion = ?", "", new String[]{parent.id_respuesta});
            else
                row = dBhelper.getRecords(TBL_MIEMBROS_PAGOS_T, " WHERE id_gestion = ?", "", new String[]{parent.id_respuesta});

            if (row.getCount() == 0){
                row.close();
                if (ENVIROMENT)
                    row = dBhelper.getRecords(TBL_MIEMBROS_GPO, " WHERE id_prestamo = ?", "", new String[]{parent.id_prestamo});
                else
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

            if (ENVIROMENT)
                row = dBhelper.getRecords(TBL_MIEMBROS_PAGOS, " WHERE id_gestion = ?", "", new String[]{parent.id_respuesta});
            else
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
            i_arqueoCaja.putExtra(PAGO_REALIZADO, Double.parseDouble(etPagoRealizado.getText().toString()));
            i_arqueoCaja.putExtra(Constants.NOMBRE_GRUPO, parent.nombre);
            i_arqueoCaja.putExtra(ID_GESTION, parent.id_respuesta);
            startActivityForResult(i_arqueoCaja, REQUEST_CODE_ARQUEO_CAJA);
        }
    };

    private View.OnClickListener ibImprimir_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (!etPagoRealizado.getText().toString().trim().isEmpty() && Double.parseDouble(etPagoRealizado.getText().toString().trim()) > 0){
                Intent i = new Intent(ctx, PrintSeewoo.class);
                MImpresion mImpresion = new MImpresion();
                mImpresion.setIdPrestamo(parent.id_prestamo);
                mImpresion.setIdGestion(parent.id_respuesta);
                mImpresion.setMonto(String.valueOf(Math.ceil(Double.parseDouble(etPagoRealizado.getText().toString().trim()))));
                mImpresion.setMontoPrestamo(parent.monto_prestamo);
                mImpresion.setNumeroPrestamo(parent.num_prestamo);
                mImpresion.setNumeroCliente(parent.num_prestamo);
                mImpresion.setNombre(parent.nombre);
                mImpresion.setPagoRequerido(String.valueOf(parent.monto_requerido));
                mImpresion.setNombreAsesor(session.getUser().get(1));
                mImpresion.setAsesorId(session.getUser().get(0));
                mImpresion.setTipoPrestamo("VIGENTE");
                mImpresion.setTipoGestion("GRUPAL");
                mImpresion.setNombreFirma(parent.tesorera);
                mImpresion.setResultPrint(res_impresion);
                mImpresion.setClaveCliente(parent.clave_grupo);

                i.putExtra("order", mImpresion);
                i.putExtra("tag",true);

                startActivityForResult(i,REQUEST_CODE_IMPRESORA);
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
            i.putExtra(Constants.ORDER_ID, parent.id_prestamo);
            startActivityForResult(i, Constants.REQUEST_CODE_CAMARA_TICKET);
        }
    };

    private View.OnClickListener ibGaleria_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
            gallery.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(gallery, "Select Picture"), REQUEST_CODE_GALERIA);
        }
    };

    private View.OnClickListener ibFachada_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i = new Intent(parent, CameraVertical.class);
            i.putExtra(Constants.ORDER_ID, parent.id_prestamo);
            startActivityForResult(i, Constants.REQUEST_CODE_CAMARA_FACHADA);
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
                            SelectEstaGerente(Miscellaneous.Gerente(tvGerente));

                            Cursor row;
                            if (ENVIROMENT)
                                row = dBhelper.getRecords(TBL_RESPUESTAS_GPO, " WHERE id_prestamo = ?", " ORDER BY _id ASC", new String[]{parent.id_prestamo});
                            else
                                row = dBhelper.getRecords(TBL_RESPUESTAS_GPO_T, " WHERE id_prestamo = ?", " ORDER BY _id ASC", new String[]{parent.id_prestamo});
                            row.moveToLast();
                            Log.e("RespuestaGpo", row.getCount()+" count");
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
                                params.put(21, Miscellaneous.ObtenerFecha(TIMESTAMP));
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
                                if (row.getInt(25) == 1 || row.getInt(25) == 2) {
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
                                    params.put(21, Miscellaneous.ObtenerFecha(TIMESTAMP));
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
                                    Log.e("ACtualizaContacto", "xxxxxxxxxxxxxxxxxxxxxx");
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
                            /*try {
                                jsonResponse.put(ACTUALIZAR_TELEFONO, pos);
                                UpdateTemporal(jsonResponse);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }*/
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
                    .setItems(R.array.medio_pago, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int position) {
                            tvMedioPago.setError(null);
                            tvMedioPago.setText(_medio_pago[position]);
                            if (position == 6 && medio_pago_anterio >= 0) {
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
                            else if(position >= 0 && medio_pago_anterio == 6){
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
                            SelectMedioPago(position);
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
                            /*try {
                                jsonResponse.put(MOTIVO_ACLARACION, _motivo_aclaracion[position].toUpperCase());
                                UpdateTemporal(jsonResponse);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }*/
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
        if (!tvGerente.getText().toString().trim().isEmpty()) tvGerente.setError(null);
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
                if (!tvFechaDefuncion.getText().toString().trim().isEmpty())
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
            case 2: // Telecom
            case 3: // Bansefi
            case 4: // Bancomer
                if (byteEvidencia != null)
                    tvFotoGaleria.setError(null);
                else
                    tvFotoGaleria.setError("");
                if (tvFechaDeposito.getText().toString().isEmpty())
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
            case 5: // OXXO
                if (byteEvidencia != null)
                    tvFotoGaleria.setError(null);
                else
                    tvFotoGaleria.setError("");
                if (tvFechaDeposito.getText().toString().isEmpty())
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
                if (!etFolioRecibo.getText().toString().trim().isEmpty())
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
                SelectImprimirRecibos(Miscellaneous.Impresion(tvImprimirRecibo));
                llFotoGaleria.setVisibility(View.VISIBLE);
                llGerente.setVisibility(View.VISIBLE);
                break;
            case 6: // Efectivo
                if (byteEvidencia != null)
                    tvFotoGaleria.setError(null);
                else
                    tvFotoGaleria.setError("");

                if (!etPagoRealizado.getText().toString().trim().isEmpty() && Double.parseDouble(etPagoRealizado.getText().toString().trim()) > 10000) {
                    llArqueoCaja.setVisibility(View.VISIBLE);
                }
                llDetalleFicha.setVisibility(View.VISIBLE);
                ibGaleria.setEnabled(false);
                ibGaleria.setBackground(ctx.getResources().getDrawable(R.drawable.btn_disable));
                if (!etFolioRecibo.getText().toString().trim().isEmpty())
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
            case -1: //Sin seleccionar una opción o cualquier otro valor
                etPagoRealizado.setEnabled(false);
                llMontoPagoRealizado.setVisibility(View.GONE);
                llImprimirRecibo.setVisibility(View.GONE);
                llFolioRecibo.setVisibility(View.GONE);
                break;
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

    private void init(){
        setHasOptionsMenu(true);
        parent.getSupportActionBar().show();
        tvMontoPagoRequerido.setText(String.valueOf(parent.monto_requerido));

        if (!parent.id_respuesta.isEmpty()){
            Cursor row;
            if (ENVIROMENT)
                row = dBhelper.getRecords(TBL_RESPUESTAS_GPO, " WHERE _id = ? AND id_prestamo = ?", "", new String[]{parent.id_respuesta, parent.id_prestamo});
            else
                row = dBhelper.getRecords(TBL_RESPUESTAS_GPO_T, " WHERE _id = ? AND id_prestamo = ?", "", new String[]{parent.id_respuesta, parent.id_prestamo});

            if (row.getCount() > 0) {
                row.moveToFirst();

                Log.e("res_impresion", row.getString(26));
                res_impresion = row.getInt(26);

                if (!row.getString(2).isEmpty() && !row.getString(3).isEmpty()){
                    tvmapa.setError(null);
                    mapView.setVisibility(View.VISIBLE);
                    ColocarUbicacionGestion(row.getDouble(2), row.getDouble(3));
                }

                Log.e("Contacto", String.valueOf(!row.getString(4).isEmpty()));
                if (!row.getString(4).isEmpty()){
                    tvContacto.setText(row.getString(4));
                    Log.e("Contacto",""+Miscellaneous.ContactoCliente(tvContacto));
                    switch (Miscellaneous.ContactoCliente(tvContacto)) {
                        case 0: //SI CONTACTO
                            SelectContactoCliente(Miscellaneous.ContactoCliente(tvContacto));

                            if (!row.getString(7).isEmpty()){//ACTUALIZAR TELEFONO
                                tvActualizarTelefono.setText(row.getString(7));
                                if (Miscellaneous.ActualizarTelefono(tvActualizarTelefono) == 0){
                                    etActualizarTelefono.setVisibility(View.VISIBLE);
                                    if (!row.getString(8).isEmpty()){//NUEVO TELEFONO
                                        etActualizarTelefono.setText(row.getString(8));
                                        etActualizarTelefono.setError(null);
                                    }
                                }
                            }

                            if (!row.getString(9).isEmpty()){//RESULTADO PAGO
                                tvResultadoGestion.setText(row.getString(9));
                                SelectResultadoGestion(Miscellaneous.ResultadoGestion(tvResultadoGestion));
                                switch (Miscellaneous.ResultadoGestion(tvResultadoGestion)){
                                    case 1: //No Pago

                                        tvMotivoNoPago.setText(row.getString(10));

                                        if (!row.getString(11).isEmpty()){//FECHA DE DEFUNCION
                                            SelectMotivoNoPago(Miscellaneous.MotivoNoPago(tvMotivoNoPago));
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
                                            byteEvidencia = Miscellaneous.getBytesUri(ctx, uriFachada, 1);
                                            tvFachada.setError(null);
                                        }

                                        tvGerente.setVisibility(View.VISIBLE);
                                        if (!row.getString(20).isEmpty()){//ESTA GERENTE
                                            tvGerente.setText(row.getString(20));
                                            SelectEstaGerente(Miscellaneous.Gerente(tvGerente));

                                            if (Miscellaneous.Gerente(tvGerente) == 0){

                                                if (!row.getString(21).isEmpty()){//FIRMA
                                                    File firmaFile = new File(ROOT_PATH + "Firma/"+row.getString(21));
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
                                        if (!row.getString(12).isEmpty()){//MEDIO PAGO
                                            tvMedioPago.setText(row.getString(12));
                                            medio_pago_anterio = Miscellaneous.MedioPago(tvMedioPago);
                                            SelectMedioPago(Miscellaneous.MedioPago(tvMedioPago));
                                            if (!row.getString(14).isEmpty()){//DETALLE DE FICHA

                                                tvDetalleFicha.setText(row.getString(14));
                                                SelectDetalleFicha(Miscellaneous.PagoRequerido(tvDetalleFicha));
                                                if (Miscellaneous.PagoRequerido(tvDetalleFicha) == 0){
                                                    Cursor row_pago;
                                                    if (ENVIROMENT)
                                                        row_pago = dBhelper.getRecords(TBL_MIEMBROS_PAGOS, " WHERE id_gestion = ?", "", new String[]{parent.id_respuesta});
                                                    else
                                                        row_pago = dBhelper.getRecords(TBL_MIEMBROS_PAGOS_T, " WHERE id_gestion = ?", "", new String[]{parent.id_respuesta});

                                                    Log.e("pagoIntegrante", row_pago.getCount()+"");
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

                                            Log.e("medioPAgo", String.valueOf(Miscellaneous.MedioPago(tvMedioPago) == 6));
                                            if (Miscellaneous.MedioPago(tvMedioPago) == 6){ //EFECTIVO
                                                if (!row.getString(16).isEmpty()){//IMPRIMIRA RECIBOS
                                                    tvImprimirRecibo.setText(row.getString(16));
                                                    SelectImprimirRecibos(Miscellaneous.Impresion(tvImprimirRecibo));
                                                    etFolioRecibo.setEnabled(true);
                                                    rbArqueoCaja.setChecked(row.getInt(27) == 1);

                                                    if (Miscellaneous.Impresion(tvImprimirRecibo) == 0){ //SI IMPRIMIRA RECIBOS

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
                                            else if (Miscellaneous.MedioPago(tvMedioPago) == 7){
                                                Log.e("ELSE", "xxxxxxxxxxxxxxxx");
                                                ibImprimir.setVisibility(View.GONE);
                                                llFolioRecibo.setVisibility(View.VISIBLE);
                                                Log.e("Folioxxx", row.getString(17)+"dsfdsfs");
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
                                                byteEvidencia = Miscellaneous.getBytesUri(ctx, uriEvidencia, 1);
                                                tvFotoGaleria.setError(null);
                                            }

                                            if (!row.getString(20).isEmpty()){//ESTA GERENTE
                                                tvGerente.setText(row.getString(20));

                                                SelectEstaGerente(Miscellaneous.Gerente(tvGerente));
                                                if (Miscellaneous.Gerente(tvGerente) == 0){//SI ESTA GERENTE
                                                    if (!row.getString(21).isEmpty()){//FIRMA
                                                        File firmaFile = new File(ROOT_PATH + "Firma/"+row.getString(21));
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
                                byteEvidencia = Miscellaneous.getBytesUri(ctx, uriFachada, 1);
                                tvFachada.setError(null);
                            }

                            tvGerente.setVisibility(View.VISIBLE);
                            if (!row.getString(20).isEmpty()){//ESTA GERENTE
                                tvGerente.setText(row.getString(20));
                                SelectEstaGerente(Miscellaneous.Gerente(tvGerente));

                                if (Miscellaneous.Gerente(tvGerente) == 0){
                                    if (!row.getString(21).isEmpty()){
                                        File firmaFile = new File(ROOT_PATH + "Firma/"+row.getString(21));
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
                            Log.e("aclaracion",row.getString(5));
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
                                SelectEstaGerente(Miscellaneous.Gerente(tvGerente));
                                if (Miscellaneous.Gerente(tvGerente) == 0){
                                    if (!row.getString(21).isEmpty()){
                                        File firmaFile = new File(ROOT_PATH + "Firma/"+row.getString(21));
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
            row.close();
        }
    }

    private void Update(String key, String value) {
        Log.e("update", key+": "+value);
        ContentValues cv = new ContentValues();
        cv.put(key, value);
        if (ENVIROMENT)
            db.update(TBL_RESPUESTAS_GPO, cv, "id_prestamo = ? AND _id = ?" ,new String[]{parent.id_prestamo, parent.id_respuesta});
        else
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
                            Update("firma", Miscellaneous.save(byteFirma, 3));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                break;
            case Constants.REQUEST_CODE_CAMARA_FACHADA:
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
            case REQUEST_CODE_INTEGRANTES_GPO:
                if (resultCode == Activity.RESULT_OK){
                    if (data != null){
                        etPagoRealizado.setError(null);
                        etPagoRealizado.setText(String.valueOf(data.getStringExtra(Constants.RESPONSE)));
                        rbIntegrantes.setChecked(true);
                        if (tvMedioPago.getText().toString().trim().equals("EFECTIVO")){
                            if (Double.parseDouble(etPagoRealizado.getText().toString().trim()) >= 10000)
                                llArqueoCaja.setVisibility(View.VISIBLE);
                            else
                                llArqueoCaja.setVisibility(View.GONE);
                        }
                    }
                }
                break;
            case REQUEST_CODE_IMPRESORA:
                if (resultCode == Activity.RESULT_OK){
                    if (data != null){
                        Toast.makeText(ctx, data.getStringExtra(Constants.MESSAGE), Toast.LENGTH_SHORT).show();
                        if(data.getIntExtra(Constants.RES_PRINT,0) == 1 || data.getIntExtra(Constants.RES_PRINT,0) == 2){
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
            case Constants.REQUEST_CODE_GALERIA:
                if (data != null){
                    ibFoto.setVisibility(View.GONE);
                    ibGaleria.setVisibility(View.GONE);
                    tvFotoGaleria.setError(null);
                    imageUri = data.getData();
                    ivEvidencia.setVisibility(View.VISIBLE);

                    byteEvidencia = Miscellaneous.getBytesUri(ctx, imageUri, 0);

                    View vCanvas = new CanvasCustom(ctx,new SimpleDateFormat(FORMAT_TIMESTAMP).format(Calendar.getInstance().getTime()));

                    Bitmap newBitMap = null;
                    Bitmap bitmap = BitmapFactory.decodeByteArray(byteEvidencia, 0, byteEvidencia.length);

                    Bitmap.Config config = bitmap.getConfig();

                    newBitMap = Bitmap.createBitmap(bitmap.getWidth(),bitmap.getHeight(),config);
                    Canvas canvas = new Canvas(newBitMap);
                    canvas.drawBitmap(bitmap,0,0, null);

                    vCanvas.draw(canvas);

                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    newBitMap.compress(Bitmap.CompressFormat.JPEG, 100, baos);

                    byteEvidencia = baos.toByteArray();

                    Glide.with(ctx).load(baos.toByteArray()).centerCrop().into(ivEvidencia);

                    try {
                        Update("evidencia", Miscellaneous.save(byteEvidencia, 2));
                        Update("tipo_imagen", "GALERIA");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case Constants.REQUEST_CODE_CAMARA_TICKET:
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
                        Update("fecha_fallecimiento", tvFechaDefuncion.getText().toString());
                    }
                }
                break;
            case 812: //Fecha de pago
                if (resultCode == 321){
                    if (data != null){
                        tvFechaDeposito.setError(null);
                        tvFechaDeposito.setText(data.getStringExtra(DATE));
                        Update("fecha_pago", tvFechaDeposito.getText().toString());
                    }
                }
                break;
            case Constants.REQUEST_CODE_PREVIEW:
                if (resultCode == Activity.RESULT_OK){
                    if (data != null){
                        ContentValues cv = new ContentValues();if (data.hasExtra(ESTATUS)) {
                            cv.put("estatus", data.getStringExtra(ESTATUS));
                            cv.put("saldo_corte", data.getStringExtra(SALDO_CORTE));
                            cv.put("saldo_actual", data.getStringExtra(SALDO_ACTUAL));
                        }
                        cv.put("dias_atraso", Miscellaneous.GetDiasAtraso(parent.fecha_establecida));
                        cv.put("fecha_fin", Miscellaneous.ObtenerFecha("timestamp"));
                        cv.put("estatus", "1");
                        if (ENVIROMENT)
                            db.update(TBL_RESPUESTAS_GPO, cv, "id_prestamo = ? AND _id = ?" ,new String[]{parent.id_prestamo, parent.id_respuesta});
                        else
                            db.update(TBL_RESPUESTAS_GPO_T, cv, "id_prestamo = ? AND _id = ?" ,new String[]{parent.id_prestamo, parent.id_respuesta});

                        Toast.makeText(ctx, "Ficha Guardada con éxito.", Toast.LENGTH_SHORT).show();
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
        Miscellaneous m = new Miscellaneous();
        if (latLngGestion != null) {
            b.putDouble(LATITUD, latLngGestion.latitude);
            b.putDouble(LONGITUD, latLngGestion.longitude);
            if (m.ContactoCliente(tvContacto) == 0){ //Si Contacto cliente
                b.putString(CONTACTO, tvContacto.getText().toString());
                if (!tvActualizarTelefono.getText().toString().isEmpty()){
                    if ((m.ActualizarTelefono(tvActualizarTelefono) == 0 && !validator.validate(etActualizarTelefono, new String[]{validator.REQUIRED,validator.PHONE})) || m.ActualizarTelefono(tvActualizarTelefono) == 1){
                        b.putString(ACTUALIZAR_TELEFONO, tvActualizarTelefono.getText().toString());
                        if (m.ActualizarTelefono(tvActualizarTelefono) == 0){
                            b.putString(NUEVO_TELEFONO, etActualizarTelefono.getText().toString().trim());
                        }
                        if (m.ResultadoGestion(tvResultadoGestion) == 0) { // Si pago
                            b.putString(RESULTADO_PAGO, tvResultadoGestion.getText().toString());
                            if (m.MedioPago(tvMedioPago) >= 0 && m.MedioPago(tvMedioPago) < 6 || m.MedioPago(tvMedioPago) == 7) { // Medio de pago Bancos, Oxxo, SIDERT
                                b.putString(MEDIO_PAGO, tvMedioPago.getText().toString());
                                if (!tvFechaDeposito.getText().toString().trim().isEmpty()) {
                                    b.putString(FECHA_DEPOSITO, tvFechaDeposito.getText().toString().trim());
                                    if (!tvDetalleFicha.getText().toString().isEmpty()) { //Selecionó que cuenta con el detalle o no
                                        b.putString(DETALLE_FICHA, tvDetalleFicha.getText().toString());
                                        if (!etPagoRealizado.getText().toString().trim().isEmpty() && Double.parseDouble(etPagoRealizado.getText().toString()) > 0) { //El pago realizado es mayor a cero
                                            b.putDouble(SALDO_CORTE, parent.saldo_corte);
                                            b.putDouble(PAGO_REQUERIDO, parent.monto_requerido);
                                            b.putString(PAGO_REALIZADO, etPagoRealizado.getText().toString().trim());

                                            if (m.MedioPago(tvMedioPago) == 7) {//SIDERT
                                                if (m.Impresion(tvImprimirRecibo) == 1) { //No imprimirá recibos
                                                    if (!etFolioRecibo.getText().toString().trim().isEmpty()) {
                                                        b.putString(Constants.IMPRESORA, tvImprimirRecibo.getText().toString());
                                                        b.putString(Constants.FOLIO_TICKET, etFolioRecibo.getText().toString().trim());
                                                    }
                                                    else
                                                        Toast.makeText(ctx, "No ha capturado el folio del recibo manual", Toast.LENGTH_SHORT).show();
                                                }
                                            }

                                            if (byteEvidencia != null) { //Ha capturado una evidencia (Fotografía al ticket)
                                                b.putByteArray(EVIDENCIA, byteEvidencia);
                                                if (m.Gerente(tvGerente) == 0) { //Selecciono que si está el gerente
                                                    b.putString(GERENTE, tvGerente.getText().toString());
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
                                                } else if (m.Gerente(tvGerente) == 1) { //No se encuentra el Gerente
                                                    b.putString(GERENTE, tvGerente.getText().toString());
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
                            } else if (m.MedioPago(tvMedioPago) == 6) { //Medio de pago Efectivo
                                b.putString(MEDIO_PAGO, tvMedioPago.getText().toString());
                                if (!tvDetalleFicha.getText().toString().isEmpty()) { //Selecionó que cuenta con el detalle o no
                                    b.putString(DETALLE_FICHA, tvDetalleFicha.getText().toString());
                                    if (!etPagoRealizado.getText().toString().trim().isEmpty() && Double.parseDouble(etPagoRealizado.getText().toString()) > 0) { //El pago realizado es mayor a cero
                                        b.putDouble(SALDO_CORTE, parent.saldo_corte);
                                        b.putDouble(PAGO_REQUERIDO, parent.monto_requerido);
                                        if (m.Impresion(tvImprimirRecibo) == 0) { //Si imprimirá recibos
                                            b.putString(IMPRESORA, tvImprimirRecibo.getText().toString());
                                            if (!etFolioRecibo.getText().toString().trim().isEmpty()) {
                                                b.putString(FOLIO_TICKET, etFolioRecibo.getText().toString().trim());
                                                if (byteEvidencia != null) { //Ha capturado una evidencia (Fotografía al ticket)
                                                    b.putByteArray(EVIDENCIA, byteEvidencia);
                                                    if (m.Gerente(tvGerente) == 0) { //Selecciono que si está el gerente
                                                        b.putString(GERENTE, tvGerente.getText().toString());
                                                        if (byteFirma != null) { //Capturó una firma
                                                            b.putByteArray(FIRMA, byteFirma);
                                                            if ((m.PagoRequerido(tvDetalleFicha) == 0 && rbIntegrantes.isChecked()) || m.PagoRequerido(tvDetalleFicha) == 1) {
                                                                b.putString(PAGO_REALIZADO, etPagoRealizado.getText().toString().trim());
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
                                                    } else if (m.Gerente(tvGerente) == 1) { //No se encuentra el Gerente
                                                        if ((m.PagoRequerido(tvDetalleFicha) == 0 && rbIntegrantes.isChecked()) || m.PagoRequerido(tvDetalleFicha) == 1) {
                                                            if (m.PagoRequerido(tvDetalleFicha) == 0) {
                                                                b.putBoolean(RESUMEN_INTEGRANTES, true);
                                                            } else {
                                                                b.putBoolean(RESUMEN_INTEGRANTES, false);
                                                            }
                                                            b.putString(GERENTE, tvGerente.getText().toString());
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
                                        } else if (m.Impresion(tvImprimirRecibo) == 1) { //No imprimirá recibos
                                            b.putString(IMPRESORA, tvImprimirRecibo.getText().toString());
                                            if (!etFolioRecibo.getText().toString().trim().isEmpty()) {
                                                b.putString(FOLIO_TICKET, etFolioRecibo.getText().toString().trim());
                                                if (byteEvidencia != null) { //Ha capturado una evidencia (Fotografía al ticket)
                                                    b.putByteArray(EVIDENCIA, byteEvidencia);
                                                    if (m.Gerente(tvGerente) == 1) { //Selecciono que si está el gerente
                                                        b.putString(GERENTE, tvGerente.getText().toString());
                                                        if (byteFirma != null) { //Capturó una firma
                                                            if ((m.PagoRequerido(tvDetalleFicha) == 0 && rbIntegrantes.isChecked()) || m.PagoRequerido(tvDetalleFicha) == 0 ) {
                                                                b.putString(PAGO_REALIZADO, etPagoRealizado.getText().toString().trim());
                                                                b.putByteArray(EVIDENCIA, byteEvidencia);
                                                                b.putBoolean(TERMINADO, true);
                                                            }
                                                            else
                                                                Toast.makeText(ctx, "No ha capturado el pago de los integrantes", Toast.LENGTH_SHORT).show();
                                                        } else //No ha capturado la firma
                                                            Toast.makeText(ctx, "Capture la firma del gerente", Toast.LENGTH_SHORT).show();
                                                    } else if (m.Gerente(tvGerente) == 1) { //No se encuentra el Gerente
                                                        if ((m.PagoRequerido(tvDetalleFicha) == 0 && rbIntegrantes.isChecked()) || m.PagoRequerido(tvDetalleFicha) == 0) {
                                                            b.putString(PAGO_REALIZADO, etPagoRealizado.getText().toString().trim());
                                                            b.putBoolean(GERENTE, false);
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
                        else if (m.ResultadoGestion(tvResultadoGestion) == 1) { //No pago
                            b.putString(RESULTADO_PAGO, tvResultadoGestion.getText().toString());
                            if (m.MotivoNoPago(tvMotivoNoPago) == 0 || m.MotivoNoPago(tvMotivoNoPago) == 2) { //Motivo de no pago Negacion u Otra
                                b.putString(MOTIVO_NO_PAGO, tvMotivoNoPago.getText().toString());
                                if (!etComentario.getText().toString().trim().isEmpty()) { //El campo comentario es diferente de vacio
                                    b.putString(COMENTARIO, etComentario.getText().toString());
                                    if (byteEvidencia != null) {
                                        b.putByteArray(EVIDENCIA, byteEvidencia);

                                        if (m.Gerente(tvGerente) == 0) { //Selecciono que si está el gerente
                                            b.putString(GERENTE, tvGerente.getText().toString());
                                            if (byteFirma != null) { //Capturó una firma
                                                b.putByteArray(FIRMA, byteFirma);
                                                b.putBoolean(TERMINADO, true);
                                            } else { //No ha capturado la firma
                                                Toast.makeText(ctx, "Capture la firma del gerente", Toast.LENGTH_SHORT).show();
                                                Log.e("aaaaaaaaaa","aaaaaaaaaaaaaaaaa");
                                            }
                                        } else if (m.Gerente(tvGerente) == 1) { //No se encuentra el Gerente
                                            b.putString(GERENTE, tvGerente.getText().toString());
                                            b.putBoolean(TERMINADO, true);
                                        } else //No ha seleccionado si está el gerente
                                            Toast.makeText(ctx, "Confirme si el gerente está con usted", Toast.LENGTH_SHORT).show();
                                    } else //no ha capturado la fotografía de la fachada
                                        Toast.makeText(ctx, "La Fotografía de la fachada es requerida.", Toast.LENGTH_SHORT).show();
                                } else // No ha ingresado alguno comentario
                                    Toast.makeText(ctx, "El campo Comentario es requerido.", Toast.LENGTH_SHORT).show();
                            } else if (m.MotivoNoPago(tvMotivoNoPago) == 1) { //Motivo de no pago fue Fallecimiento
                                b.putString(MOTIVO_NO_PAGO, tvMotivoNoPago.getText().toString());
                                if (!tvFechaDefuncion.getText().toString().trim().isEmpty()) { //El campo Fecha es diferente de vacio
                                    b.putString(FECHA_DEFUNCION, tvFechaDefuncion.getText().toString());
                                    if (!etComentario.getText().toString().trim().isEmpty()) { // El campo Comentario es diferente de vacio
                                        b.putString(COMENTARIO, etComentario.getText().toString());
                                        if (byteEvidencia != null) { //Capturo una fotografia de fachada
                                            b.putByteArray(EVIDENCIA, byteEvidencia);
                                            if (m.Gerente(tvGerente) == 0) { //Si está el gerente
                                                b.putString(GERENTE, tvGerente.getText().toString());
                                                if (byteFirma != null) { //Capturó un firma
                                                    b.putByteArray(FIRMA, byteFirma);
                                                    b.putBoolean(TERMINADO, true);
                                                } else //No ha Capturado un Firma
                                                    Toast.makeText(ctx, "Capture la firma del gerente", Toast.LENGTH_SHORT).show();
                                            } else if (m.Gerente(tvGerente) == 1) { //No está el gerente
                                                b.putString(GERENTE, tvGerente.getText().toString());
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
            else if(m.ContactoCliente(tvContacto) == 1) { //No contactó al cliente
                b.putString(Constants.CONTACTO, tvContacto.getText().toString());
                if (!etComentario.getText().toString().trim().isEmpty()) { //El campo comentario es diferente de vacio
                    b.putString(COMENTARIO, etComentario.getText().toString());
                    if (byteEvidencia != null) { //Ha capturado una fotografia de la fachada
                        b.putByteArray(EVIDENCIA, byteEvidencia);
                        if (m.Gerente(tvGerente) == 0) { // Seleccionó que está el gerente
                            if (byteFirma != null) { // Ha capturado un firma
                                b.putString(GERENTE, tvGerente.getText().toString());
                                b.putByteArray(FIRMA, byteFirma);
                                b.putBoolean(TERMINADO, true);
                            } else //No ha capturado un firma
                                Toast.makeText(ctx, "Capture la firma del gerente", Toast.LENGTH_SHORT).show();
                        } else if (m.Gerente(tvGerente) == 1) { //No se encuentra el gerente
                            b.putString(GERENTE, tvGerente.getText().toString());
                            b.putBoolean(TERMINADO, true);
                        } else //No ha seleccionado si está el gerente
                            Toast.makeText(ctx, "Confirme si el gerente está con usted", Toast.LENGTH_SHORT).show();
                    } else // No ha capturado una fotografia de fachada
                        Toast.makeText(ctx, "La Fotografía de la fachada es requerida.", Toast.LENGTH_SHORT).show();
                } else //No ha ingresado algun comentario
                    Toast.makeText(ctx, "El campo Comentario es obligatorio", Toast.LENGTH_SHORT).show();
            } //============  Termina No Contacto  =============================
            else if(m.ContactoCliente(tvContacto) == 2) { //Seleccionó Aclaración
                b.putString(Constants.CONTACTO, tvContacto.getText().toString());
                if (!tvMotivoAclaracion.getText().toString().trim().isEmpty()) { //Motivo de aclaración es diferente de vacio
                    b.putString(MOTIVO_ACLARACION, tvMotivoAclaracion.getText().toString());
                    if (!etComentario.getText().toString().trim().isEmpty()) { // Ingresó algun comentario
                        b.putString(COMENTARIO, etComentario.getText().toString());
                        if (m.Gerente(tvGerente) == 0) { //Seleccionó que está el gerente
                            if (byteFirma != null) { //Ha capturado una firma
                                b.putString(GERENTE, tvGerente.getText().toString());
                                b.putByteArray(FIRMA, byteFirma);
                                b.putBoolean(TERMINADO, true);
                            }
                            else //No ha capturado una firma
                                Toast.makeText(ctx, "Capture la firma del gerente", Toast.LENGTH_SHORT).show();
                        }
                        else if (m.Gerente(tvGerente) == 1) { //Seleccionó que no está el gerente
                            b.putString(GERENTE, tvGerente.getText().toString());
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

        if (!b.isEmpty() && b.containsKey(TERMINADO)){
            Intent i_preview = new Intent(parent, VistaPreviaGestion.class);
            i_preview.putExtra(PARAMS,b);
            startActivityForResult(i_preview,Constants.REQUEST_CODE_PREVIEW);
        }
        else{
            //Toast.makeText(ctx, "No contiene el parámetro TERMINADO", Toast.LENGTH_SHORT).show();
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
