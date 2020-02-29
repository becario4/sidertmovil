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
import android.graphics.drawable.ColorDrawable;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
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
import android.widget.MultiAutoCompleteTextView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import com.sidert.sidertmovil.database.DBhelper;
import com.sidert.sidertmovil.fragments.dialogs.dialog_date_picker;
import com.sidert.sidertmovil.fragments.dialogs.dialog_registro_cli;
import com.sidert.sidertmovil.fragments.dialogs.dialog_time_picker;
import com.sidert.sidertmovil.models.ModeloCatalogoGral;
import com.sidert.sidertmovil.utils.Constants;
import com.sidert.sidertmovil.utils.Miscellaneous;
import com.sidert.sidertmovil.utils.MyCurrentListener;
import com.sidert.sidertmovil.utils.NameFragments;
import com.sidert.sidertmovil.utils.NetworkStatus;
import com.sidert.sidertmovil.utils.Popups;
import com.sidert.sidertmovil.utils.Servicios_Sincronizado;
import com.sidert.sidertmovil.utils.SessionManager;
import com.sidert.sidertmovil.utils.Validator;
import com.sidert.sidertmovil.utils.ValidatorTextView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.card.payment.CardIOActivity;

import static com.sidert.sidertmovil.utils.Constants.DATOS_AVAL_IND;
import static com.sidert.sidertmovil.utils.Constants.DATOS_AVAL_IND_T;
import static com.sidert.sidertmovil.utils.Constants.DATOS_CLIENTE_IND;
import static com.sidert.sidertmovil.utils.Constants.DATOS_CLIENTE_IND_T;
import static com.sidert.sidertmovil.utils.Constants.DATOS_CONYUGE_IND;
import static com.sidert.sidertmovil.utils.Constants.DATOS_CONYUGE_IND_T;
import static com.sidert.sidertmovil.utils.Constants.DATOS_CREDITO_IND;
import static com.sidert.sidertmovil.utils.Constants.DATOS_CREDITO_IND_T;
import static com.sidert.sidertmovil.utils.Constants.DATOS_ECONOMICOS_IND;
import static com.sidert.sidertmovil.utils.Constants.DATOS_ECONOMICOS_IND_T;
import static com.sidert.sidertmovil.utils.Constants.DATOS_NEGOCIO_IND;
import static com.sidert.sidertmovil.utils.Constants.DATOS_NEGOCIO_IND_T;
import static com.sidert.sidertmovil.utils.Constants.DATOS_REFERENCIA_IND;
import static com.sidert.sidertmovil.utils.Constants.DATOS_REFERENCIA_IND_T;
import static com.sidert.sidertmovil.utils.Constants.DOCUMENTOS;
import static com.sidert.sidertmovil.utils.Constants.DOCUMENTOS_T;
import static com.sidert.sidertmovil.utils.Constants.ENVIROMENT;
import static com.sidert.sidertmovil.utils.Constants.SOLICITUDES;
import static com.sidert.sidertmovil.utils.Constants.SOLICITUDES_T;
import static io.card.payment.CardIOActivity.RESULT_SCAN_SUPPRESSED;

public class SolicitudCreditoInd extends AppCompatActivity implements dialog_registro_cli.OnCompleteListener{

    private Context ctx;
    private Context context;

    private SessionManager session;

    private String[] _dias_semana;
    private String[] _plazo;
    private String[] _frecuencia;
    private String[] _destino;
    private String[] _estudios;
    private String[] _civil;
    private String[] _tipo_casa;
    private String[] _casa_familiar;
    private String[] _dependientes;
    private String[] _medio_contacto;
    private String[] _tipo_casa_aval;
    private String[] _parentesco;
    private String[] _tipo_identificacion;
    private ArrayList<Integer> selectedItemsDias;

    private DBhelper dBhelper;
    private SQLiteDatabase db;

    private SimpleDateFormat sdf = new SimpleDateFormat(Constants.FORMAT_DATE_GNRAL);
    private Calendar myCalendar;

    private long id_solicitud = 0;
    private String id_cliente = "0";
    private boolean is_edit = true;

    private FloatingActionButton btnContinuar0;
    private FloatingActionButton btnContinuar1;
    private FloatingActionButton btnContinuar2;
    private FloatingActionButton btnContinuar3;
    private FloatingActionButton btnContinuar4;
    private FloatingActionButton btnContinuar5;
    private FloatingActionButton btnContinuar6;

    private FloatingActionButton btnRegresar1;
    private FloatingActionButton btnRegresar2;
    private FloatingActionButton btnRegresar3;
    private FloatingActionButton btnRegresar4;
    private FloatingActionButton btnRegresar5;
    private FloatingActionButton btnRegresar6;
    private FloatingActionButton btnRegresar7;

    //======== DATOS CRÉDITO  ==================
    private TextView tvPlazo;
    private TextView tvFrecuencia;
    private TextView tvFechaDesembolso;
    private TextView tvDiaDesembolso;
    private TextView tvHoraVisita;
    private EditText etMontoPrestamo;
    private TextView tvCantidadLetra;
    private TextView tvDestino;
    //=========================================
    //======== DATOS PERSONALES ===============
    private EditText etNombreCli;
    private EditText etApPaternoCli;
    private EditText etApMaternoCli;
    private TextView tvFechaNacCli;
    private TextView tvEdadCli;
    private TextView tvGeneroCli;
    private RadioGroup rgGeneroCli;
    private TextView tvEstadoNacCli;
    private TextView tvCurpCli;
    private EditText etCurpIdCli;
    private TextView tvRfcCli;
    private TextView tvOcupacionCli;
    private TextView tvActividadEcoCli;
    private TextView tvTipoIdentificacion;
    private EditText etNumIdentifCli;
    private TextView tvEstudiosCli;
    private TextView tvEstadoCivilCli;
    private LinearLayout llBienes;
    private TextView tvBienes;
    private RadioGroup rgBienes;
    private TextView tvTipoCasaCli;
    private LinearLayout llCasaFamiliar;
    private TextView tvCasaFamiliar;
    private LinearLayout llCasaOtroCli;
    private EditText etOTroTipoCli;
    private TextView tvMapaCli;
    private ImageButton ibMapCli;
    private ProgressBar pbLoadCli;
    private MapView mapCli;
    private LatLng latLngUbiCli;
    private EditText etCalleCli;
    private EditText etNoExtCli;
    private EditText etManzanaCli;
    private EditText etNoIntCli;
    private EditText etLoteCli;
    private EditText etCpCli;
    private TextView tvColoniaCli;
    private TextView tvMunicipioCli;
    private TextView tvEstadoCli;
    private EditText etTelCasaCli;
    private EditText etCelularCli;
    private EditText etTelMensCli;
    private EditText etTelTrabajoCli;
    private EditText etTiempoSitio;
    private TextView tvDependientes;
    private TextView tvEnteroNosotros;
    private EditText etEmail;
    private TextView tvFachadaCli;
    private ImageButton ibFotoFachCli;
    private ImageView ivFotoFachCli;
    public byte[] byteFotoFachCli;
    private MultiAutoCompleteTextView etReferenciCli;
    private TextView tvFirmaCli;
    private ImageButton ibFirmaCli;
    private ImageView ivFirmaCli;
    public byte[] byteFirmaCli;
    //=========================================
    //========== CONYUGE ======================
    private EditText etNombreCony;
    private EditText etApPaternoCony;
    private EditText etApMaternoCony;
    private TextView tvOcupacionCony;
    private EditText etCelularCony;
    //========================================
    //========= DATOS ECONOMICOS =============
    private EditText etPropiedadesEco;
    private EditText etValorAproxEco;
    private EditText etUbicacionEco;
    private EditText etIngresoEco;
    //========================================
    //======== NEGOCIO =======================
    private EditText etNombreNeg;
    private TextView tvMapaNeg;
    private ImageButton ibMapNeg;
    private ProgressBar pbLoadNeg;
    private MapView mapNeg;
    private LatLng latLngUbiNeg;
    private EditText etCalleNeg;
    private EditText etNoExtNeg;
    private EditText etNoIntNeg;
    private EditText etManzanaNeg;
    private EditText etLoteNeg;
    private EditText etCpNeg;
    private TextView tvColoniaNeg;
    private TextView tvMunicipioNeg;
    private TextView tvActEconomicaNeg;
    private EditText etAntiguedadNeg;
    private EditText etIngMenNeg;
    private EditText etOtrosIngNeg;
    private EditText etGastosSemNeg;
    private EditText etGastosAguaNeg;
    private EditText etGastosLuzNeg;
    private EditText etGastosTelNeg;
    private EditText etGastosRentaNeg;
    private EditText etGastosOtrosNeg;
    private TextView tvCapacidadPagoNeg;
    private TextView tvDiasVentaNeg;
    private TextView tvFachadaNeg;
    private ImageButton ibFotoFachNeg;
    private ImageView ivFotoFachNeg;
    public byte[] byteFotoFachNeg;
    private MultiAutoCompleteTextView etReferenciNeg;
    //========================================
    //======= AVAL ===========================
    private EditText etNombreAval;
    private EditText etApPaternoAval;
    private EditText etApMaternoAval;
    private TextView tvFechaNacAval;
    private TextView tvEdadAval;
    private TextView tvGeneroAval;
    private RadioGroup rgGeneroAval;
    private TextView tvEstadoNacAval;
    private TextView tvRfcAval;
    private TextView tvCurpAval;
    private EditText etCurpIdAval;
    private TextView tvTipoIdentificacionAval;
    private EditText etNumIdentifAval;
    private TextView tvOcupacionAval;
    private TextView tvActividadEcoAval;
    private TextView tvMapaAval;
    private ImageButton ibMapAval;
    private ProgressBar pbLoadAval;
    private MapView mapAval;
    private LatLng latLngUbiAval;
    private EditText etCalleAval;
    private EditText etNoExtAval;
    private EditText etManzanaAval;
    private EditText etNoIntAval;
    private EditText etLoteAval;
    private EditText etCpAval;
    private TextView tvColoniaAval;
    private TextView tvMunicipioAval;
    private TextView tvEstadoAval;
    private TextView tvTipoCasaAval;
    private TextView tvParentescoAval;
    private LinearLayout llParentescoFamAval;
    private LinearLayout llNombreTitular;
    private EditText etNombreTitularAval;
    private EditText etIngMenAval;
    private EditText etIngOtrosAval;
    private EditText etGastosSemAval;
    private EditText etGastosAguaAval;
    private EditText etGastosLuzAval;
    private EditText etGastosTelAval;
    private EditText etGastosRentaAval;
    private EditText etGastosOtrosAval;
    private TextView tvHoraLocAval;
    private EditText etAntiguedadAval;
    private EditText etTelCasaAval;
    private EditText etCelularAval;
    private TextView tvFachadaAval;
    private ImageButton ibFotoFachAval;
    private ImageView ivFotoFachAval;
    public byte[] byteFotoFachAval;
    private MultiAutoCompleteTextView etReferenciaAval;
    private TextView tvFirmaAval;
    private ImageButton ibFirmaAval;
    private ImageView ivFirmaAval;
    public byte[] byteFirmaAval;
    //========================================
    //========== REFERENCIA ==================
    private EditText etNombreRef;
    private EditText etApPaternoRef;
    private EditText etApMaternoRef;
    private EditText etCalleRef;
    private EditText etCpRef;
    private TextView tvColoniaRef;
    private TextView tvMunicipioRef;
    private EditText etTelCelRef;
    //========================================
    //========== DOCUMENTOS ==================
    private TextView tvIneFrontal;
    private ImageButton ibIneFrontal;
    private ImageView ivIneFrontal;
    public byte[] byteIneFrontal;
    private TextView tvIneReverso;
    private ImageButton ibIneReverso;
    private ImageView ivIneReverso;
    public byte[] byteIneReverso;
    private TextView tvCurp;
    private ImageButton ibCurp;
    private ImageView ivCurp;
    public byte[] byteCurp;
    private TextView tvComprobante;
    private ImageButton ibComprobante;
    private ImageView ivComprobante;
    public byte[] byteComprobante;
    private TextView tvFirmaAsesor;
    private ImageButton ibFirmaAsesor;
    private ImageView ivFirmaAsesor;
    public byte[] byteFirmaAsesor;
    //========================================
    //================= Image View  =====================
    private ImageView ivDown1;
    private ImageView ivDown2;
    private ImageView ivDown3;
    private ImageView ivDown4;
    private ImageView ivDown5;
    private ImageView ivDown6;
    private ImageView ivDown7;
    private ImageView ivDown8;

    private ImageView ivUp1;
    private ImageView ivUp2;
    private ImageView ivUp3;
    private ImageView ivUp4;
    private ImageView ivUp5;
    private ImageView ivUp6;
    private ImageView ivUp7;
    private ImageView ivUp8;
    //========================================
    //================= Image View ERROR  =====================
    private ImageView ivError1;
    private ImageView ivError2;
    private ImageView ivError3;
    private ImageView ivError4;
    private ImageView ivError5;
    private ImageView ivError6;
    private ImageView ivError7;
    private ImageView ivError8;
    //===================================================
    //===============  LINEAR LAYOUT  ====================
    private LinearLayout llDatosCredito;
    private LinearLayout llDatosPersonales;
    private LinearLayout llDatosConyuge;
    private LinearLayout llDatosEconomicos;
    private LinearLayout llDatosNegocio;
    private LinearLayout llDatosAval;
    private LinearLayout llDatosReferencia;
    private LinearLayout llDatosDocumentos;

    private LinearLayout llCredito;
    private LinearLayout llPersonales;
    private LinearLayout llConyuge;
    private LinearLayout llPropiedades;
    private LinearLayout llEconomicos;
    private LinearLayout llNegocio;
    private LinearLayout llAval;
    private LinearLayout llReferencia;
    private LinearLayout llDocumentos;
    //====================================================

    private LocationManager locationManager;
    private MyCurrentListener locationListener;
    private GoogleMap mMapCli;
    private GoogleMap mMapNeg;
    private GoogleMap mMapAval;

    private Validator validator;
    private ValidatorTextView validatorTV;

    private Toolbar TBmain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solicitud_credito_ind);

        ctx = getApplicationContext();
        context = this;

        session = new SessionManager(ctx);

        dBhelper = new DBhelper(ctx);
        db = dBhelper.getWritableDatabase();

        _dias_semana = getResources().getStringArray(R.array.dias_semana);

        myCalendar = Calendar.getInstance();

        validator = new Validator();
        validatorTV = new ValidatorTextView();

        TBmain = findViewById(R.id.TBmain);

        _plazo = getResources().getStringArray(R.array.intervalo);
        _frecuencia = getResources().getStringArray(R.array.lapso);
        _destino = getResources().getStringArray(R.array.destino_prestamo);
        _estudios = getResources().getStringArray(R.array.nivel_estudio);
        _civil = getResources().getStringArray(R.array.estado_civil);
        _tipo_casa = getResources().getStringArray(R.array.tipo_casa_cli);
        _casa_familiar = getResources().getStringArray(R.array.casa_familiar);
        _dependientes = getResources().getStringArray(R.array.dependientes_eco);
        _medio_contacto = getResources().getStringArray(R.array.entero_nosotros);
        _tipo_casa_aval = getResources().getStringArray(R.array.tipo_casa_aval);
        _parentesco = getResources().getStringArray(R.array.casa_familiar_aval);
        _tipo_identificacion = getResources().getStringArray(R.array.tipo_identificacion);

        setSupportActionBar(TBmain);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setDisplayShowHomeEnabled(true);

        //=================================== DATOS CREDITO  =======================================
        tvPlazo             = findViewById(R.id.tvPlazo);
        tvFrecuencia        = findViewById(R.id.tvFrecuencia);
        tvFechaDesembolso   = findViewById(R.id.tvFechaDesembolso);
        tvDiaDesembolso     = findViewById(R.id.tvDiaDesembolso);
        tvHoraVisita        = findViewById(R.id.tvHoraVisita);
        etMontoPrestamo     = findViewById(R.id.etMontoPrestamo);
        tvCantidadLetra     = findViewById(R.id.tvCantidadLetra);
        tvDestino           = findViewById(R.id.tvDestino);
        //==========================================================================================
        //=================================  DATOS PERSONALES  =====================================
        etNombreCli          = findViewById(R.id.etNombreCli);
        etApPaternoCli       = findViewById(R.id.etApPaternoCli);
        etApMaternoCli       = findViewById(R.id.etApMaternoCli);
        tvFechaNacCli        = findViewById(R.id.tvFechaNacCli);
        tvEdadCli            = findViewById(R.id.tvEdadCli);
        tvGeneroCli          = findViewById(R.id.tvGeneroCli);
        rgGeneroCli          = findViewById(R.id.rgGeneroCli);
        tvEstadoNacCli       = findViewById(R.id.tvEstadoNacCli);
        tvRfcCli             = findViewById(R.id.tvRfcCli);
        tvCurpCli            = findViewById(R.id.tvCurpCli);
        etCurpIdCli          = findViewById(R.id.etCurpIdCli);
        tvOcupacionCli       = findViewById(R.id.tvOcupacionCli);
        tvActividadEcoCli    = findViewById(R.id.tvActividadEcoCli);
        tvTipoIdentificacion = findViewById(R.id.tvTipoIdentificacion);
        etNumIdentifCli      = findViewById(R.id.etNumIdentifCli);
        tvEstudiosCli        = findViewById(R.id.tvEstudiosCli);
        tvEstadoCivilCli     = findViewById(R.id.tvEstadoCivilCli);
        llBienes             = findViewById(R.id.llBienes);
        tvBienes             = findViewById(R.id.tvBienes);
        rgBienes             = findViewById(R.id.rgBienes);
        tvTipoCasaCli        = findViewById(R.id.tvTipoCasaCli);
        llCasaFamiliar       = findViewById(R.id.llCasaFamiliar);
        tvCasaFamiliar       = findViewById(R.id.tvCasaFamiliar);
        llCasaOtroCli        = findViewById(R.id.llCasaOtro);
        etOTroTipoCli        = findViewById(R.id.etOtroTipoCli);
        tvMapaCli            = findViewById(R.id.tvMapaCli);
        ibMapCli             = findViewById(R.id.ibMapCli);
        pbLoadCli            = findViewById(R.id.pbLoadCli);
        mapCli               = findViewById(R.id.mapCli);
        etCalleCli           = findViewById(R.id.etCalleCli);
        etNoExtCli           = findViewById(R.id.etNoExtCli);
        etManzanaCli         = findViewById(R.id.etManzanaCli);
        etNoIntCli           = findViewById(R.id.etNoIntCli);
        etLoteCli            = findViewById(R.id.etLoteCli);
        etCpCli              = findViewById(R.id.etCpCli);
        tvColoniaCli         = findViewById(R.id.tvColoniaCli);
        tvMunicipioCli       = findViewById(R.id.tvMunicipioCli);
        tvEstadoCli          = findViewById(R.id.tvEstadoCli);
        etTelCasaCli         = findViewById(R.id.etTelCasaCli);
        etCelularCli         = findViewById(R.id.etCelularCli);
        etTelMensCli         = findViewById(R.id.etTelMensCli);
        etTelTrabajoCli      = findViewById(R.id.etTelTrabajoCli);
        etTiempoSitio        = findViewById(R.id.etTiempoSitio);
        tvDependientes       = findViewById(R.id.tvDependientes);
        tvEnteroNosotros     = findViewById(R.id.tvEnteroNosotros);
        etEmail              = findViewById(R.id.etEmail);
        tvFachadaCli         = findViewById(R.id.tvFachadaCli);
        ibFotoFachCli        = findViewById(R.id.ibFotoFachCli);
        ivFotoFachCli        = findViewById(R.id.ivFotoFachCli);
        etReferenciCli       = findViewById(R.id.etReferenciaCli);
        tvFirmaCli           = findViewById(R.id.tvFirmaCli);
        ibFirmaCli           = findViewById(R.id.ibFirmaCli);
        ivFirmaCli           = findViewById(R.id.ivFirmaCli);
        //==========================================================================================
        //===================================  DATOS CONYUGE  ======================================
        etNombreCony        = findViewById(R.id.etNombreCony);
        etApPaternoCony     = findViewById(R.id.etApPaternoCony);
        etApMaternoCony     = findViewById(R.id.etApMaternoCony);
        tvOcupacionCony     = findViewById(R.id.tvOcupacionCony);
        etCelularCony       = findViewById(R.id.etCelularCony);
        //==========================================================================================
        //=================================  DATOS ECONOMICOS  =====================================
        etPropiedadesEco    = findViewById(R.id.etPropiedadesEco);
        etValorAproxEco     = findViewById(R.id.etValorAproxEco);
        etUbicacionEco      = findViewById(R.id.etUbicacionEco);
        etIngresoEco        = findViewById(R.id.etIngresoEco);
        //==========================================================================================
        //===================================  DATOS NEGOCIO  ======================================
        etNombreNeg         = findViewById(R.id.etNombreNeg);
        tvMapaNeg           = findViewById(R.id.tvMapaNeg);
        ibMapNeg            = findViewById(R.id.ibMapNeg);
        pbLoadNeg           = findViewById(R.id.pbLoadNeg);
        mapNeg              = findViewById(R.id.mapNeg);
        etCalleNeg          = findViewById(R.id.etCalleNeg);
        etNoExtNeg          = findViewById(R.id.etNoExtNeg);
        etNoIntNeg          = findViewById(R.id.etNoIntNeg);
        etManzanaNeg        = findViewById(R.id.etManzanaNeg);
        etLoteNeg           = findViewById(R.id.etLoteNeg);
        etCpNeg             = findViewById(R.id.etCpNeg);
        tvColoniaNeg        = findViewById(R.id.tvColoniaNeg);
        tvMunicipioNeg      = findViewById(R.id.tvMunicipioNeg);
        tvActEconomicaNeg   = findViewById(R.id.tvActEconomicaNeg);
        etAntiguedadNeg     = findViewById(R.id.etAntiguedadNeg);
        etIngMenNeg         = findViewById(R.id.etIngMenNeg);
        etOtrosIngNeg       = findViewById(R.id.etOtrosIngNeg);
        etGastosSemNeg      = findViewById(R.id.etGastosSemNeg);
        etGastosAguaNeg     = findViewById(R.id.etGastosAguaNeg);
        etGastosLuzNeg      = findViewById(R.id.etGastosLuzNeg);
        etGastosTelNeg      = findViewById(R.id.etGastosTelNeg);
        etGastosRentaNeg    = findViewById(R.id.etGastosRentaNeg);
        etGastosOtrosNeg    = findViewById(R.id.etGastosOtrosNeg);
        tvCapacidadPagoNeg  = findViewById(R.id.tvCapacidadPagoNeg);
        tvDiasVentaNeg      = findViewById(R.id.tvDiasVentaNeg);
        tvFachadaNeg        = findViewById(R.id.tvFachadaNeg);
        ibFotoFachNeg       = findViewById(R.id.ibFotoFachNeg);
        ivFotoFachNeg       = findViewById(R.id.ivFotoFachNeg);
        etReferenciNeg      = findViewById(R.id.etReferenciaNeg);
        //==========================================================================================
        //=====================================  DATOS AVAL  =======================================
        etNombreAval        = findViewById(R.id.etNombreAval);
        etApPaternoAval     = findViewById(R.id.etApPaternoAval);
        etApMaternoAval     = findViewById(R.id.etApMaternoAval);
        tvFechaNacAval      = findViewById(R.id.tvFechaNacAval);
        tvEdadAval          = findViewById(R.id.tvEdadAval);
        tvGeneroAval        = findViewById(R.id.tvGeneroAval);
        rgGeneroAval        = findViewById(R.id.rgGeneroAval);
        tvEstadoNacAval     = findViewById(R.id.tvEstadoNacAval);
        tvRfcAval           = findViewById(R.id.tvRfcAval);
        tvCurpAval          = findViewById(R.id.tvCurpAval);
        etCurpIdAval        = findViewById(R.id.etCurpIdAval);
        tvTipoIdentificacionAval = findViewById(R.id.tvTipoIdentificacionAval);
        etNumIdentifAval    = findViewById(R.id.etNumIdentifAval);
        tvOcupacionAval     = findViewById(R.id.tvOcupacionAval);
        tvActividadEcoAval  = findViewById(R.id.tvActividadEcoAval);
        tvMapaAval          = findViewById(R.id.tvMapaAval);
        ibMapAval           = findViewById(R.id.ibMapAval);
        pbLoadAval          = findViewById(R.id.pbLoadAval);
        mapAval             = findViewById(R.id.mapAval);
        etCalleAval         = findViewById(R.id.etCalleAval);
        etNoExtAval         = findViewById(R.id.etNoExtAval);
        etManzanaAval       = findViewById(R.id.etManzanaAval);
        etNoIntAval         = findViewById(R.id.etNoIntAval);
        etLoteAval          = findViewById(R.id.etLoteAval);
        etCpAval            = findViewById(R.id.etCpAval);
        tvColoniaAval       = findViewById(R.id.tvColoniaAval);
        tvMunicipioAval     = findViewById(R.id.tvMunicipioAval);
        tvEstadoAval        = findViewById(R.id.tvEstadoAval);
        tvTipoCasaAval      = findViewById(R.id.tvTipoCasaAval);
        tvParentescoAval    = findViewById(R.id.tvFamiliar);
        llParentescoFamAval = findViewById(R.id.llParentescoFamAval);
        llNombreTitular     = findViewById(R.id.llNombreTitular);
        etNombreTitularAval = findViewById(R.id.etNombreTitularAval);
        etIngMenAval        = findViewById(R.id.etIngMenAval);
        etIngOtrosAval      = findViewById(R.id.etOtrosIngAval);
        etGastosSemAval     = findViewById(R.id.etGastosSemAval);
        etGastosAguaAval    = findViewById(R.id.etGastosAguaAval);
        etGastosLuzAval     = findViewById(R.id.etGastosLuzAval);
        etGastosTelAval     = findViewById(R.id.etGastosTelAval);
        etGastosRentaAval   = findViewById(R.id.etGastosRentaAval);
        etGastosOtrosAval   = findViewById(R.id.etGastosOtrosAval);
        tvHoraLocAval       = findViewById(R.id.tvHoraLocAval);
        etAntiguedadAval    = findViewById(R.id.etAntiguedadAval);
        etTelCasaAval       = findViewById(R.id.etTelCasaAval);
        etCelularAval       = findViewById(R.id.etCelularAval);
        tvFachadaAval       = findViewById(R.id.tvFachadaAval);
        ibFotoFachAval      = findViewById(R.id.ibFotoFachAval);
        ivFotoFachAval      = findViewById(R.id.ivFotoFachAval);
        etReferenciaAval    = findViewById(R.id.etReferenciaAval);
        tvFirmaAval         = findViewById(R.id.tvFirmaAval);
        ibFirmaAval         = findViewById(R.id.ibFirmaAval);
        ivFirmaAval         = findViewById(R.id.ivFirmaAval);
        //==========================================================================================
        //==================================  DATOS REFERENCIA  ====================================
        etNombreRef         = findViewById(R.id.etNombreRef);
        etApPaternoRef      = findViewById(R.id.etApPaternoRef);
        etApMaternoRef      = findViewById(R.id.etApMaternoRef);
        etCalleRef          = findViewById(R.id.etCalleRef);
        etCpRef             = findViewById(R.id.etCpRef);
        tvColoniaRef        = findViewById(R.id.tvColoniaRef);
        tvMunicipioRef      = findViewById(R.id.tvMunicipioRef);
        etTelCelRef         = findViewById(R.id.etTelCelRef);
        //=====================================  DOCUMENTOS  =======================================
        tvIneFrontal        = findViewById(R.id.tvIneFrontal);
        ibIneFrontal        = findViewById(R.id.ibIneFrontal);
        ivIneFrontal        = findViewById(R.id.ivIneFrontal);
        tvIneReverso        = findViewById(R.id.tvIneReverso);
        ibIneReverso        = findViewById(R.id.ibIneReverso);
        ivIneReverso        = findViewById(R.id.ivIneReverso);
        tvCurp              = findViewById(R.id.tvCurp);
        ibCurp              = findViewById(R.id.ibCurp);
        ivCurp              = findViewById(R.id.ivCurp);
        tvComprobante       = findViewById(R.id.tvComprobante);
        ibComprobante       = findViewById(R.id.ibComprobante);
        ivComprobante       = findViewById(R.id.ivComprobante);
        tvFirmaAsesor       = findViewById(R.id.tvFirmaAsesor);
        ibFirmaAsesor       = findViewById(R.id.ibFirmaAsesor);
        ivFirmaAsesor       = findViewById(R.id.ivFirmaAsesor);
        //=========================================================
        //================ LINEAR LAYOUT  =========================
        llDatosCredito      = findViewById(R.id.llDatosCredito);
        llDatosPersonales   = findViewById(R.id.llDatosPersonales);
        llDatosConyuge      = findViewById(R.id.llDatosConyuge);
        llDatosEconomicos   = findViewById(R.id.llDatosEconomicos);
        llDatosNegocio      = findViewById(R.id.llDatosNegocio);
        llDatosAval         = findViewById(R.id.llDatosAval);
        llDatosReferencia   = findViewById(R.id.llDatosReferencia);
        llDatosDocumentos   = findViewById(R.id.llDatosDocumentos);

        llCredito           = findViewById(R.id.llCredito);
        llPersonales        = findViewById(R.id.llPersonales);
        llConyuge           = findViewById(R.id.llConyuge);
        llPropiedades       = findViewById(R.id.llPropiedades);
        llEconomicos        = findViewById(R.id.llEconomicos);
        llNegocio           = findViewById(R.id.llNegocio);
        llAval              = findViewById(R.id.llAval);
        llReferencia        = findViewById(R.id.llReferencia);
        llDocumentos        = findViewById(R.id.llDocumentos);
        //==========================================================================================

        //============================== LINEAR LAYOUT  ============================================
        llCredito.setOnClickListener(llCredito_OnClick);
        llPersonales.setOnClickListener(llPersonales_OnClick);
        llConyuge.setOnClickListener(llConyuge_OnClick);
        llEconomicos.setOnClickListener(llEconomicos_OnClick);
        llNegocio.setOnClickListener(llNegocio_OnClick);
        llAval.setOnClickListener(llAval_OnClick);
        llReferencia.setOnClickListener(llReferencia_OnClick);
        llDocumentos.setOnClickListener(llDocumentos_OnClick);

        mapCli.onCreate(savedInstanceState);
        mapNeg.onCreate(savedInstanceState);
        mapAval.onCreate(savedInstanceState);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        //==========================================================================================

        if (getIntent().getBooleanExtra("is_new",true)) {
            openRegistroCliente();
        }
        else{
            id_solicitud = Long.parseLong(getIntent().getStringExtra("id_solicitud"));
            initComponents(getIntent().getStringExtra("id_solicitud"));
        }

        //=================================  FLOATING BUTTON  ======================================
        btnContinuar0 = findViewById(R.id.btnContinuar0);
        btnContinuar1 = findViewById(R.id.btnContinuar1);
        btnContinuar2 = findViewById(R.id.btnContinuar2);
        btnContinuar3 = findViewById(R.id.btnContinuar3);
        btnContinuar4 = findViewById(R.id.btnContinuar4);
        btnContinuar5 = findViewById(R.id.btnContinuar5);
        btnContinuar6 = findViewById(R.id.btnContinuar6);

        btnRegresar1 = findViewById(R.id.btnRegresar1);
        btnRegresar2 = findViewById(R.id.btnRegresar2);
        btnRegresar3 = findViewById(R.id.btnRegresar3);
        btnRegresar4 = findViewById(R.id.btnRegresar4);
        btnRegresar5 = findViewById(R.id.btnRegresar5);
        btnRegresar6 = findViewById(R.id.btnRegresar6);
        btnRegresar7 = findViewById(R.id.btnRegresar7);
        //==========================================================================================
        //============================ IMAGE VIEW UP|DOWN  =========================================
        ivDown1 = findViewById(R.id.ivDown1);
        ivDown2 = findViewById(R.id.ivDown2);
        ivDown3 = findViewById(R.id.ivDown3);
        ivDown4 = findViewById(R.id.ivDown4);
        ivDown5 = findViewById(R.id.ivDown5);
        ivDown6 = findViewById(R.id.ivDown6);
        ivDown7 = findViewById(R.id.ivDown7);
        ivDown8 = findViewById(R.id.ivDown8);

        ivUp1 = findViewById(R.id.ivUp1);
        ivUp2 = findViewById(R.id.ivUp2);
        ivUp3 = findViewById(R.id.ivUp3);
        ivUp4 = findViewById(R.id.ivUp4);
        ivUp5 = findViewById(R.id.ivUp5);
        ivUp6 = findViewById(R.id.ivUp6);
        ivUp7 = findViewById(R.id.ivUp7);
        ivUp8 = findViewById(R.id.ivUp8);
        //==========================================================================================
        //============================= IMAGE VIEW ERROR  ==========================================
        ivError1 = findViewById(R.id.ivError1);
        ivError2 = findViewById(R.id.ivError2);
        ivError3 = findViewById(R.id.ivError3);
        ivError4 = findViewById(R.id.ivError4);
        ivError5 = findViewById(R.id.ivError5);
        ivError6 = findViewById(R.id.ivError6);
        ivError7 = findViewById(R.id.ivError7);
        ivError8 = findViewById(R.id.ivError8);
        //=========================================================

        //================================= FLOATING BUTTON LISTENER  ==============================
        btnContinuar0.setOnClickListener(btnContinuar0_OnClick);
        btnContinuar1.setOnClickListener(btnContinuar1_OnClick);
        btnContinuar2.setOnClickListener(btnContinuar2_OnClick);
        btnContinuar3.setOnClickListener(btnContinuar3_OnClick);
        btnContinuar4.setOnClickListener(btnContinuar4_OnClick);
        btnContinuar5.setOnClickListener(btnContinuar5_OnClick);
        btnContinuar6.setOnClickListener(btnContinuar6_OnClick);

        btnRegresar1.setOnClickListener(btnRegresar1_OnClick);
        btnRegresar2.setOnClickListener(btnRegresar2_OnClick);
        btnRegresar3.setOnClickListener(btnRegresar3_OnClick);
        btnRegresar4.setOnClickListener(btnRegresar4_OnClick);
        btnRegresar5.setOnClickListener(btnRegresar5_OnClick);
        btnRegresar6.setOnClickListener(btnRegresar6_OnClick);
        btnRegresar7.setOnClickListener(btnRegresar7_OnClick);
        //================================  CREDITO LISTENER =======================================
        tvPlazo.setOnClickListener(tvPlazo_OnClick);
        tvFrecuencia.setOnClickListener(tvFrecuencia_OnClick);
        tvFechaDesembolso.setOnClickListener(tvFechaDesembolso_OnClick);
        tvHoraVisita.setOnClickListener(tvHoraVisita_OnClick);
        ibFirmaAsesor.setOnClickListener(ibFirmaAsesor_OnClick);
        etMontoPrestamo.addTextChangedListener(new TextWatcher() {
            private final String PATTERN_MONTO_CREDITO  = "[1-9][0-9][0-9][0][0][0]|[1-9][0-9][0][0][0]|[1-9][0][0][0]";
            private Pattern pattern;
            private Matcher matcher;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length()> 0){
                    pattern = Pattern.compile(PATTERN_MONTO_CREDITO);
                    matcher = pattern.matcher(s.toString());
                    if(!matcher.matches()) {
                        tvCantidadLetra.setText("");
                        etMontoPrestamo.setError("La cantidad no corresponde a un monto de crédito válido");
                    }else{
                        ContentValues cv = new ContentValues();
                        cv.put("monto_prestamo",s.toString().trim());
                        if (ENVIROMENT)
                            db.update(DATOS_CREDITO_IND, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                        else
                            db.update(DATOS_CREDITO_IND_T, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                        tvCantidadLetra.setText((Miscellaneous.cantidadLetra(s.toString()).toUpperCase() + " PESOS MEXICANOS ").replace("  ", " "));
                        if (Integer.parseInt(s.toString()) > 30000){
                            llPropiedades.setVisibility(View.VISIBLE);
                        }
                        else{
                            llPropiedades.setVisibility(View.GONE);
                        }
                    }
                }
                else{
                    tvCantidadLetra.setText("");
                }
            }
        });
        etMontoPrestamo.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (!etMontoPrestamo.getText().toString().trim().isEmpty() && !tvCantidadLetra.getText().toString().isEmpty()){
                        ContentValues cv = new ContentValues();
                        cv.put("monto_prestamo",etMontoPrestamo.getText().toString().trim());
                        if (ENVIROMENT)
                            db.update(DATOS_CREDITO_IND, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                        else
                            db.update(DATOS_CREDITO_IND_T, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                    }
                }
            }
        });
        tvDestino.setOnClickListener(tvDestino_OnClick);
        //==============================  PERSONALES LISTENER ======================================
        etNombreCli.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                HashMap<Integer, String> params = new HashMap<>();
                if (s.length()> 0){
                    params.put(0, s.toString());
                    params.put(1, etApPaternoCli.getText().toString());
                    params.put(2, etApMaternoCli.getText().toString());
                    params.put(3, tvFechaNacCli.getText().toString());

                    if (rgGeneroCli.getCheckedRadioButtonId()==R.id.rbHombre)
                        params.put(4, "Hombre");
                    else if (rgGeneroCli.getCheckedRadioButtonId()==R.id.rbMujer)
                        params.put(4, "Mujer");
                    else
                        params.put(4, "");

                    if (!tvEstadoNacCli.getText().toString().trim().isEmpty())
                        params.put(5, tvEstadoNacCli.getText().toString().trim());
                    else
                        params.put(5,"");
                    tvCurpCli.setText(Miscellaneous.GenerarCurp(params));
                }
                else{
                    params.put(0, "");
                    params.put(1, etApPaternoCli.getText().toString());
                    params.put(2, etApMaternoCli.getText().toString());
                    params.put(3, tvFechaNacCli.getText().toString());

                    if (rgGeneroCli.getCheckedRadioButtonId()==R.id.rbHombre)
                        params.put(4, "Hombre");
                    else if (rgGeneroCli.getCheckedRadioButtonId()==R.id.rbMujer)
                        params.put(4, "Mujer");
                    else
                        params.put(4, "");

                    if (!tvEstadoNacCli.getText().toString().trim().isEmpty())
                        params.put(5, tvEstadoNacCli.getText().toString().trim());
                    else
                        params.put(5,"");
                    tvCurpCli.setText(Miscellaneous.GenerarCurp(params));
                }
            }
        });
        etApPaternoCli.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                HashMap<Integer, String> params = new HashMap<>();
                if (s.length()> 0){
                    params.put(0, etNombreCli.getText().toString());
                    params.put(1, s.toString());
                    params.put(2, etApMaternoCli.getText().toString());
                    params.put(3, tvFechaNacCli.getText().toString());

                    if (rgGeneroCli.getCheckedRadioButtonId()==R.id.rbHombre)
                        params.put(4, "Hombre");
                    else if (rgGeneroCli.getCheckedRadioButtonId()==R.id.rbMujer)
                        params.put(4, "Mujer");
                    else
                        params.put(4, "");

                    if (!tvEstadoNacCli.getText().toString().trim().isEmpty())
                        params.put(5, tvEstadoNacCli.getText().toString().trim());
                    else
                        params.put(5,"");
                    tvCurpCli.setText(Miscellaneous.GenerarCurp(params));
                }
                else{
                    params.put(0, etNombreCli.getText().toString());
                    params.put(1, "");
                    params.put(2, etApMaternoCli.getText().toString());
                    params.put(3, tvFechaNacCli.getText().toString());

                    if (rgGeneroCli.getCheckedRadioButtonId()==R.id.rbHombre)
                        params.put(4, "Hombre");
                    else if (rgGeneroCli.getCheckedRadioButtonId()==R.id.rbMujer)
                        params.put(4, "Mujer");
                    else
                        params.put(4, "");

                    if (!tvEstadoNacCli.getText().toString().trim().isEmpty())
                        params.put(5, tvEstadoNacCli.getText().toString().trim());
                    else
                        params.put(5,"");
                    tvCurpCli.setText(Miscellaneous.GenerarCurp(params));
                }
            }
        });
        etApMaternoCli.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                HashMap<Integer, String> params = new HashMap<>();
                if (s.length()> 0){
                    params.put(0, etNombreCli.getText().toString());
                    params.put(1, etApPaternoCli.getText().toString());
                    params.put(2, s.toString());
                    params.put(3, tvFechaNacCli.getText().toString());

                    if (rgGeneroCli.getCheckedRadioButtonId()==R.id.rbHombre)
                        params.put(4, "Hombre");
                    else if (rgGeneroCli.getCheckedRadioButtonId()==R.id.rbMujer)
                        params.put(4, "Mujer");
                    else
                        params.put(4, "");

                    if (!tvEstadoNacCli.getText().toString().trim().isEmpty())
                        params.put(5, tvEstadoNacCli.getText().toString().trim());
                    else
                        params.put(5,"");
                    tvCurpCli.setText(Miscellaneous.GenerarCurp(params));
                }
                else{
                    params.put(0, etNombreCli.getText().toString());
                    params.put(1, etApPaternoCli.getText().toString());
                    params.put(2, "");
                    params.put(3, tvFechaNacCli.getText().toString());

                    if (rgGeneroCli.getCheckedRadioButtonId()==R.id.rbHombre)
                        params.put(4, "Hombre");
                    else if (rgGeneroCli.getCheckedRadioButtonId()==R.id.rbMujer)
                        params.put(4, "Mujer");
                    else
                        params.put(4, "");

                    if (!tvEstadoNacCli.getText().toString().trim().isEmpty())
                        params.put(5, tvEstadoNacCli.getText().toString().trim());
                    else
                        params.put(5,"");
                    tvCurpCli.setText(Miscellaneous.GenerarCurp(params));
                }
            }
        });
        tvFechaNacCli.setOnClickListener(tvFechaNac_OnClick);
        tvEstadoNacCli.setOnClickListener(etEstadoNac_OnClick);
        tvCurpCli.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0){
                    if (s.toString().contains("Curp no válida"))
                        tvRfcCli.setText("Rfc no válida");
                    else{
                        tvRfcCli.setText(Miscellaneous.GenerarRFC(s.toString().substring(0,10), etNombreCli.getText().toString().trim(), etApPaternoCli.getText().toString().trim(), etApMaternoCli.getText().toString().trim()));

                        ContentValues cv = new ContentValues();
                        cv.put("rfc",tvRfcCli.getText().toString().trim().toUpperCase());
                        cv.put("curp",s.toString().trim().toUpperCase());
                        if (ENVIROMENT)
                            db.update(DATOS_CLIENTE_IND_T, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                        else
                            db.update(DATOS_CLIENTE_IND_T, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                    }
                }
                else
                    tvRfcCli.setText("Rfc no válida");
            }
        });
        etCurpIdCli.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (!etCurpIdCli.getText().toString().trim().isEmpty()) {
                        if (etCurpIdCli.getText().toString().trim().length() == 2) {
                            if (Miscellaneous.CurpValidador(tvCurpCli.getText() + etCurpIdCli.getText().toString().trim().toUpperCase())) {
                                ContentValues cv = new ContentValues();
                                cv.put("curp_digito_veri", etCurpIdCli.getText().toString().trim());
                                if (ENVIROMENT)
                                    db.update(DATOS_CLIENTE_IND, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                                else
                                    db.update(DATOS_CLIENTE_IND_T, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                            }
                            else
                                etCurpIdCli.setError("Curp no válida");
                        }
                        else
                            etCurpIdCli.setError("Curp no válida");
                    }
                    else
                        etCurpIdCli.setError("Este campo es requerido");
                }
            }
        });
        tvOcupacionCli.setOnClickListener(tvOcupacionClie_OnClick);
        tvTipoIdentificacion.setOnClickListener(tvTipoIdentificacion_OnClick);
        etNumIdentifCli.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    if (!etNumIdentifCli.getText().toString().trim().isEmpty()){
                        ContentValues cv = new ContentValues();
                        cv.put("no_identificacion", etNumIdentifCli.getText().toString().trim().toUpperCase());
                        if (ENVIROMENT)
                            db.update(DATOS_CLIENTE_IND, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                        else
                            db.update(DATOS_CLIENTE_IND_T, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                    }
                    else
                        etNumIdentifCli.setError("Este campo es requerido");
                }
            }
        });

        tvEstudiosCli.setOnClickListener(tvEstudiosCli_OnClick);
        tvEstadoCivilCli.setOnClickListener(tvEstadoCivilCli_OnClick);
        tvTipoCasaCli.setOnClickListener(tvTipoCasaCli_OnClick);
        tvCasaFamiliar.setOnClickListener(tvCasaFamiliar_OnClick);
        etOTroTipoCli.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    if (!validator.validate(etOTroTipoCli, new String[]{validator.REQUIRED})){
                        ContentValues cv = new ContentValues();
                        cv.put("otro_tipo_vivienda", etOTroTipoCli.getText().toString().trim().toUpperCase());
                        if (ENVIROMENT)
                            db.update(DATOS_CLIENTE_IND, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                        else
                            db.update(DATOS_CLIENTE_IND_T, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                    }
                }
            }
        });
        ibMapCli.setOnClickListener(ibMapCli_OnClick);
        etCalleCli.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    Log.e("calle", "Pierde foco");
                    if (!validator.validate(etCalleCli, new String[]{validator.REQUIRED})){
                        Log.e("calle", "guarda");
                        ContentValues cv = new ContentValues();
                        cv.put("calle", etCalleCli.getText().toString().trim().toUpperCase());
                        if (ENVIROMENT)
                            db.update(DATOS_CLIENTE_IND, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                        else
                            db.update(DATOS_CLIENTE_IND_T, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                    }
                }
            }
        });
        etNoExtCli.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    if (!validator.validate(etNoExtCli, new String[]{validator.REQUIRED})){
                        ContentValues cv = new ContentValues();
                        cv.put("no_exterior", etNoExtCli.getText().toString().trim().toUpperCase());
                        if (ENVIROMENT)
                            db.update(DATOS_CLIENTE_IND, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                        else
                            db.update(DATOS_CLIENTE_IND_T, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                    }
                }
            }
        });
        etNoIntCli.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    if (!validator.validate(etNoIntCli, new String[]{validator.REQUIRED})){
                        ContentValues cv = new ContentValues();
                        cv.put("no_interior", etNoIntCli.getText().toString().trim().toUpperCase());
                        if (ENVIROMENT)
                            db.update(DATOS_CLIENTE_IND, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                        else
                            db.update(DATOS_CLIENTE_IND_T, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                    }
                }
            }
        });
        etManzanaCli.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    ContentValues cv = new ContentValues();
                    cv.put("manzana", etManzanaCli.getText().toString().trim().toUpperCase());
                    if (ENVIROMENT)
                        db.update(DATOS_CLIENTE_IND, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                    else
                        db.update(DATOS_CLIENTE_IND_T, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});

                }
            }
        });
        etLoteCli.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    ContentValues cv = new ContentValues();
                    cv.put("lote", etLoteCli.getText().toString().trim().toUpperCase());
                    if (ENVIROMENT)
                        db.update(DATOS_CLIENTE_IND, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                    else
                        db.update(DATOS_CLIENTE_IND_T, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});

                }
            }
        });
        etCpCli.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 5){
                    Cursor row = dBhelper.getDireccionByCP(s.toString());
                    if (row.getCount() > 0){
                        ContentValues cv = new ContentValues();
                        cv.put("cp", s.toString().trim());
                        row.moveToFirst();
                        if (row.getCount() == 1){
                            cv.put("colonia", row.getString(7));
                            tvColoniaCli.setText(row.getString(7));
                            tvMunicipioCli.setText(row.getString(4));
                            tvEstadoCli.setText(row.getString(1));
                        }else {
                            tvColoniaCli.setText("");
                            tvMunicipioCli.setText(row.getString(4));
                            tvEstadoCli.setText(row.getString(1));
                        }
                        if (ENVIROMENT)
                            db.update(DATOS_CLIENTE_IND, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                        else
                            db.update(DATOS_CLIENTE_IND_T, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                    }else {
                        tvColoniaCli.setText("No se encontró información");
                        tvMunicipioCli.setText("No se encontró información");
                        tvEstadoCli.setText("No se encontró información");
                    }
                    row.close();
                }else {
                    tvColoniaCli.setText("No se encontró información");
                    tvMunicipioCli.setText("No se encontró información");
                    tvEstadoCli.setText("No se encontró información");
                }
            }
        });
        tvColoniaCli.setOnClickListener(etColonia_OnClick);
        etTelCasaCli.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0){
                    if (s.length() == 10) {
                        etTelCasaCli.setError(null);
                        ContentValues cv = new ContentValues();
                        cv.put("tel_casa", etTelCasaCli.getText().toString().trim());
                        if (ENVIROMENT)
                            db.update(DATOS_CLIENTE_IND, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                        else
                            db.update(DATOS_CLIENTE_IND_T, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                    }
                    else
                        etTelCasaCli.setError(ctx.getResources().getString(R.string.mensaje_telefono));
                }
                else
                    etTelCasaCli.setError(null);
            }
        });
        etCelularCli.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0){
                    if (s.length() == 10) {
                        etCelularCli.setError(null);
                        ContentValues cv = new ContentValues();
                        cv.put("tel_celular", etCelularCli.getText().toString().trim());
                        if (ENVIROMENT)
                            db.update(DATOS_CLIENTE_IND, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                        else
                            db.update(DATOS_CLIENTE_IND_T, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                    }
                    else
                        etCelularCli.setError(ctx.getResources().getString(R.string.mensaje_telefono));
                }
                else
                    etCelularCli.setError(null);
            }
        });
        etTelMensCli.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0){
                    if (s.length() == 10) {
                        etTelMensCli.setError(null);
                        ContentValues cv = new ContentValues();
                        cv.put("tel_mensajes", etTelMensCli.getText().toString().trim());
                        if (ENVIROMENT)
                            db.update(DATOS_CLIENTE_IND, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                        else
                            db.update(DATOS_CLIENTE_IND_T, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                    }
                    else
                        etTelMensCli.setError(ctx.getResources().getString(R.string.mensaje_telefono));
                }
                else
                    etTelMensCli.setError(null);
            }
        });
        etTelTrabajoCli.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0){
                    if (s.length() == 10) {
                        etTelTrabajoCli.setError(null);
                        ContentValues cv = new ContentValues();
                        cv.put("tel_trabajo", etTelTrabajoCli.getText().toString().trim());
                        if (ENVIROMENT)
                            db.update(DATOS_CLIENTE_IND, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                        else
                            db.update(DATOS_CLIENTE_IND_T, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                    }
                    else
                        etTelTrabajoCli.setError(ctx.getResources().getString(R.string.mensaje_telefono));
                }
                else
                    etTelTrabajoCli.setError(null);
            }
        });
        etTiempoSitio.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    if (!validator.validate(etTiempoSitio, new String[]{validator.REQUIRED, validator.ONLY_NUMBER,validator.YEARS})){
                        ContentValues cv = new ContentValues();
                        cv.put("tiempo_vivir_sitio", etTiempoSitio.getText().toString().trim());
                        if (ENVIROMENT)
                            db.update(DATOS_CLIENTE_IND, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                        else
                            db.update(DATOS_CLIENTE_IND_T, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                    }
                }
            }
        });
        tvDependientes.setOnClickListener(tvDependientes_OnClick);
        tvEnteroNosotros.setOnClickListener(tvEnteroNosotros_OnClick);
        etEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    ContentValues cv = new ContentValues();
                    cv.put("email", etEmail.getText().toString().trim());
                    if (ENVIROMENT)
                        db.update(DATOS_CLIENTE_IND, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                    else
                        db.update(DATOS_CLIENTE_IND_T, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                }
            }
        });
        etReferenciCli.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    if (!etReferenciCli.getText().toString().trim().isEmpty()){
                        ContentValues cv = new ContentValues();
                        cv.put("ref_domiciliaria", etReferenciCli.getText().toString().trim().toUpperCase());
                        if (ENVIROMENT)
                            db.update(DATOS_CLIENTE_IND, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                        else
                            db.update(DATOS_CLIENTE_IND_T, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                    }
                    else
                        etReferenciCli.setError("Este campo es requerido");
                }
            }
        });
        ibFotoFachCli.setOnClickListener(ibFotoFachCli_OnClick);
        ibFirmaCli.setOnClickListener(ibFirmaCli_OnClick);
        //==================================  CONYUGE LISTENER  ====================================
        etNombreCony.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    if (tvEstadoCivilCli.getText().toString().trim().toUpperCase().equals("CASADA(O)") ||
                    tvEstadoCivilCli.getText().toString().trim().toUpperCase().equals("UNION LIBRE")) {
                        if (!validator.validate(etNombreCony, new String[]{validator.REQUIRED, validator.ONLY_TEXT})){
                            ContentValues cv = new ContentValues();
                            cv.put("nombre", etNombreCony.getText().toString().trim().toUpperCase());
                            if (ENVIROMENT)
                                db.update(DATOS_CONYUGE_IND, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                            else
                                db.update(DATOS_CONYUGE_IND_T, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                        }
                    }
                }
            }
        });
        etApPaternoCony.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    if (tvEstadoCivilCli.getText().toString().trim().toUpperCase().equals("CASADA(O)") ||
                    tvEstadoCivilCli.getText().toString().trim().toUpperCase().equals("UNION LIBRE")) {
                        if (!validator.validate(etApPaternoCony, new String[]{validator.REQUIRED, validator.ONLY_TEXT})){
                            ContentValues cv = new ContentValues();
                            cv.put("paterno", etApPaternoCony.getText().toString().trim().toUpperCase());
                            if (ENVIROMENT)
                                db.update(DATOS_CONYUGE_IND, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                            else
                                db.update(DATOS_CONYUGE_IND_T, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                        }
                    }
                }
            }
        });
        etApMaternoCony.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    if (tvEstadoCivilCli.getText().toString().trim().toUpperCase().equals("CASADA(O)") ||
                    tvEstadoCivilCli.getText().toString().trim().toUpperCase().equals("UNION LIBRE")) {
                        if (!validator.validate(etApMaternoCony, new String[]{validator.ONLY_TEXT})){
                            ContentValues cv = new ContentValues();
                            cv.put("materno", etApMaternoCony.getText().toString().trim().toUpperCase());
                            if (ENVIROMENT)
                                db.update(DATOS_CONYUGE_IND, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                            else
                                db.update(DATOS_CONYUGE_IND_T, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                        }
                    }
                }
            }
        });
        tvOcupacionCony.setOnClickListener(tvOcupacionConyuge_OnClick);
        etCelularCony.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0){
                    if (s.length() == 10) {
                        etCelularCony.setError(null);
                        ContentValues cv = new ContentValues();
                        cv.put("tel_celular", etCelularCony.getText().toString().trim());
                        if (ENVIROMENT)
                            db.update(DATOS_CONYUGE_IND, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                        else
                            db.update(DATOS_CONYUGE_IND_T, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                    }

                    else
                        etCelularCony.setError(ctx.getResources().getString(R.string.mensaje_telefono));
                }
                else
                    etCelularCony.setError(null);
            }
        });
        //===============================  ECONOMICOS LISTENER  ====================================
        etPropiedadesEco.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    if (!etMontoPrestamo.getText().toString().isEmpty() &&
                    Integer.parseInt(etMontoPrestamo.getText().toString().trim()) >= 30000){
                        if (!etPropiedadesEco.getText().toString().trim().isEmpty()){
                            ContentValues cv = new ContentValues();
                            cv.put("propiedades", etPropiedadesEco.getText().toString().trim().toUpperCase());
                            if (ENVIROMENT)
                                db.update(DATOS_ECONOMICOS_IND, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                            else
                                db.update(DATOS_ECONOMICOS_IND_T, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                        }
                        else
                            etPropiedadesEco.setError("Este campo es requerido");
                    }
                }
            }
        });
        etValorAproxEco.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    if (!etMontoPrestamo.getText().toString().isEmpty() &&
                            Integer.parseInt(etMontoPrestamo.getText().toString().trim()) >= 30000){
                        if (!etValorAproxEco.getText().toString().trim().isEmpty() &&
                        Integer.parseInt(etValorAproxEco.getText().toString().trim()) > 0){
                            ContentValues cv = new ContentValues();
                            cv.put("valor_aproximado", etValorAproxEco.getText().toString().trim().toUpperCase());
                            if (ENVIROMENT)
                                db.update(DATOS_ECONOMICOS_IND, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                            else
                                db.update(DATOS_ECONOMICOS_IND_T, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                        }
                        else
                            etValorAproxEco.setError("Este campo es requerido");
                    }
                }
            }
        });
        etUbicacionEco.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    if (!etMontoPrestamo.getText().toString().isEmpty() &&
                            Integer.parseInt(etMontoPrestamo.getText().toString().trim()) >= 30000){
                        if (!etUbicacionEco.getText().toString().trim().isEmpty()){
                            ContentValues cv = new ContentValues();
                            cv.put("ubicacion", etUbicacionEco.getText().toString().trim().toUpperCase());
                            if (ENVIROMENT)
                                db.update(DATOS_ECONOMICOS_IND, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                            else
                                db.update(DATOS_ECONOMICOS_IND_T, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                        }
                        else
                            etUbicacionEco.setError("Este campo es requerido");
                    }
                }
            }
        });
        etIngresoEco.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    if (!etMontoPrestamo.getText().toString().isEmpty() &&
                            Integer.parseInt(etMontoPrestamo.getText().toString().trim()) >= 30000){
                        if (!etIngresoEco.getText().toString().trim().isEmpty() &&
                                Integer.parseInt(etIngresoEco.getText().toString().trim()) > 0){
                            ContentValues cv = new ContentValues();
                            cv.put("ingreso", etIngresoEco.getText().toString().trim().toUpperCase());
                            if (ENVIROMENT)
                                db.update(DATOS_ECONOMICOS_IND, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                            else
                                db.update(DATOS_ECONOMICOS_IND_T, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                        }
                        else
                            etIngresoEco.setError("Este campo es requerido");
                    }
                }
            }
        });
        //==================================  NEGOCIO LISTENER  ====================================
        etNombreNeg.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    if (!etNombreNeg.getText().toString().trim().isEmpty()){
                        ContentValues cv = new ContentValues();
                        cv.put("nombre", etNombreNeg.getText().toString().trim().toUpperCase());
                        if (ENVIROMENT)
                            db.update(DATOS_NEGOCIO_IND, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                        else
                            db.update(DATOS_NEGOCIO_IND_T, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                    }
                    else
                        etNombreNeg.setError("Este campo es requerido");
                }
            }
        });
        ibMapNeg.setOnClickListener(ibMapNeg_OnClick);
        etCalleNeg.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    if (!etCalleNeg.getText().toString().trim().isEmpty()){
                        ContentValues cv = new ContentValues();
                        cv.put("calle", etCalleNeg.getText().toString().trim().toUpperCase());
                        if (ENVIROMENT)
                            db.update(DATOS_NEGOCIO_IND, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                        else
                            db.update(DATOS_NEGOCIO_IND_T, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                    }
                    else
                        etCalleNeg.setError("Este campo es requerido");
                }
            }
        });
        etNoExtNeg.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    if (!validator.validate(etNoExtNeg, new String[]{validator.REQUIRED})){
                        ContentValues cv = new ContentValues();
                        cv.put("no_exterior", etNoExtNeg.getText().toString().trim().toUpperCase());
                        if (ENVIROMENT)
                            db.update(DATOS_NEGOCIO_IND, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                        else
                            db.update(DATOS_NEGOCIO_IND_T, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                    }
                }
            }
        });
        etNoIntNeg.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    ContentValues cv = new ContentValues();
                    cv.put("no_interior", etNoIntNeg.getText().toString().trim().toUpperCase());
                    if (ENVIROMENT)
                        db.update(DATOS_NEGOCIO_IND, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                    else
                        db.update(DATOS_NEGOCIO_IND_T, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                }
            }
        });
        etManzanaNeg.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    ContentValues cv = new ContentValues();
                    cv.put("manzana", etManzanaNeg.getText().toString().trim().toUpperCase());
                    if (ENVIROMENT)
                        db.update(DATOS_NEGOCIO_IND, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                    else
                        db.update(DATOS_NEGOCIO_IND_T, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});

                }
            }
        });
        etLoteNeg.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    ContentValues cv = new ContentValues();
                    cv.put("lote", etLoteNeg.getText().toString().trim().toUpperCase());
                    if (ENVIROMENT)
                        db.update(DATOS_NEGOCIO_IND, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                    else
                        db.update(DATOS_NEGOCIO_IND_T, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});

                }
            }
        });
        etCpNeg.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 5){
                    Cursor row = dBhelper.getDireccionByCP(s.toString());
                    if (row.getCount() > 0){
                        ContentValues cv = new ContentValues();
                        cv.put("cp", s.toString().trim());
                        row.moveToFirst();
                        if (row.getCount() == 1){
                            cv.put("colonia", row.getString(7));
                            tvColoniaNeg.setText(row.getString(7));
                            tvMunicipioNeg.setText(row.getString(4));
                        }else {
                            tvColoniaNeg.setText("");
                            tvMunicipioNeg.setText(row.getString(4));
                        }
                        if (ENVIROMENT)
                            db.update(DATOS_NEGOCIO_IND, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                        else
                            db.update(DATOS_NEGOCIO_IND_T, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                    }else {
                        tvColoniaNeg.setText("No se encontró información");
                        tvMunicipioNeg.setText("No se encontró información");
                    }
                    row.close();
                }else {
                    tvColoniaNeg.setText("No se encontró información");
                    tvMunicipioNeg.setText("No se encontró información");
                }
            }
        });
        tvColoniaNeg.setOnClickListener(etColoniaAct_OnClick);
        tvActEconomicaNeg.setOnClickListener(etActividadEco_OnClick);
        etAntiguedadNeg.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    if (!etAntiguedadNeg.getText().toString().trim().isEmpty()){
                        if (Integer.parseInt(etAntiguedadNeg.getText().toString().trim()) > 0){
                            ContentValues cv = new ContentValues();
                            cv.put("antiguedad", etAntiguedadNeg.getText().toString().trim());
                            if (ENVIROMENT)
                                db.update(DATOS_NEGOCIO_IND, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                            else
                                db.update(DATOS_NEGOCIO_IND_T, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                        }
                        else
                            etAntiguedadNeg.setError("No se permiten cantidades iguales a cero");
                    }
                    else
                        etAntiguedadNeg.setError("Este campo es requerido");
                }
            }
        });
        etIngMenNeg.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                CapacidadPagoNeg();
            }
        });
        etIngMenNeg.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    ContentValues cv = new ContentValues();
                    cv.put("ing_mensual", etIngMenNeg.getText().toString().trim());
                    if (ENVIROMENT)
                        db.update(DATOS_NEGOCIO_IND, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                    else
                        db.update(DATOS_NEGOCIO_IND_T, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                }
            }
        });
        etOtrosIngNeg.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                CapacidadPagoNeg();
            }
        });
        etOtrosIngNeg.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    ContentValues cv = new ContentValues();
                    cv.put("ing_otros", etOtrosIngNeg.getText().toString().trim());
                    if (ENVIROMENT)
                        db.update(DATOS_NEGOCIO_IND, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                    else
                        db.update(DATOS_NEGOCIO_IND_T, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                }
            }
        });
        etGastosSemNeg.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                CapacidadPagoNeg();
            }
        });
        etGastosSemNeg.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    ContentValues cv = new ContentValues();
                    cv.put("gasto_semanal", etGastosSemNeg.getText().toString().trim());
                    if (ENVIROMENT)
                        db.update(DATOS_NEGOCIO_IND, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                    else
                        db.update(DATOS_NEGOCIO_IND_T, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                }
            }
        });
        etGastosAguaNeg.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                CapacidadPagoNeg();
            }
        });
        etGastosAguaNeg.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    ContentValues cv = new ContentValues();
                    cv.put("gasto_agua", etGastosAguaNeg.getText().toString().trim());
                    if (ENVIROMENT)
                        db.update(DATOS_NEGOCIO_IND, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                    else
                        db.update(DATOS_NEGOCIO_IND_T, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                }
            }
        });
        etGastosLuzNeg.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                CapacidadPagoNeg();
            }
        });
        etGastosLuzNeg.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    ContentValues cv = new ContentValues();
                    cv.put("gasto_luz", etGastosLuzNeg.getText().toString().trim());
                    if (ENVIROMENT)
                        db.update(DATOS_NEGOCIO_IND, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                    else
                        db.update(DATOS_NEGOCIO_IND_T, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                }
            }
        });
        etGastosTelNeg.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                CapacidadPagoNeg();
            }
        });
        etGastosTelNeg.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    ContentValues cv = new ContentValues();
                    cv.put("gasto_telefono", etGastosTelNeg.getText().toString().trim());
                    if (ENVIROMENT)
                        db.update(DATOS_NEGOCIO_IND, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                    else
                        db.update(DATOS_NEGOCIO_IND_T, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                }
            }
        });
        etGastosRentaNeg.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                CapacidadPagoNeg();
            }
        });
        etGastosRentaNeg.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    ContentValues cv = new ContentValues();
                    cv.put("gasto_renta", etGastosRentaNeg.getText().toString().trim());
                    if (ENVIROMENT)
                        db.update(DATOS_NEGOCIO_IND, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                    else
                        db.update(DATOS_NEGOCIO_IND_T, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                }
            }
        });
        etGastosOtrosNeg.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                CapacidadPagoNeg();
            }
        });
        etGastosOtrosNeg.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    ContentValues cv = new ContentValues();
                    cv.put("gasto_otros", etGastosOtrosNeg.getText().toString().trim());
                    if (ENVIROMENT)
                        db.update(DATOS_NEGOCIO_IND, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                    else
                        db.update(DATOS_NEGOCIO_IND_T, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                }
            }
        });

        tvDiasVentaNeg.setOnClickListener(etDiasVenta_OnClick);
        ibFotoFachNeg.setOnClickListener(ibFotoFachNeg_OnClick);
        etReferenciNeg.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    if (!etReferenciNeg.getText().toString().trim().isEmpty()){
                        ContentValues cv = new ContentValues();
                        cv.put("ref_domiciliaria", etReferenciNeg.getText().toString().trim().toUpperCase());
                        if (ENVIROMENT)
                            db.update(DATOS_NEGOCIO_IND, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                        else
                            db.update(DATOS_NEGOCIO_IND_T, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                    }
                    else
                        etReferenciNeg.setError("Este campo es requerido");
                }
            }
        });
        //====================================  AVAL LISTENER  =====================================
        etNombreAval.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                HashMap<Integer, String> params = new HashMap<>();
                if (s.length()> 0){
                    params.put(0, s.toString());
                    params.put(1, etApPaternoAval.getText().toString());
                    params.put(2, etApMaternoAval.getText().toString());
                    params.put(3, tvFechaNacCli.getText().toString());

                    if (rgGeneroAval.getCheckedRadioButtonId()==R.id.rbHombre)
                        params.put(4, "Hombre");
                    else if (rgGeneroAval.getCheckedRadioButtonId()==R.id.rbMujer)
                        params.put(4, "Mujer");
                    else
                        params.put(4, "");

                    if (!tvEstadoNacAval.getText().toString().trim().isEmpty())
                        params.put(5, tvEstadoNacAval.getText().toString().trim());
                    else
                        params.put(5,"");
                    tvCurpAval.setText(Miscellaneous.GenerarCurp(params));
                }
                else{
                    params.put(0, "");
                    params.put(1, etApPaternoAval.getText().toString());
                    params.put(2, etApMaternoAval.getText().toString());
                    params.put(3, tvFechaNacAval.getText().toString());

                    if (rgGeneroAval.getCheckedRadioButtonId()==R.id.rbHombre)
                        params.put(4, "Hombre");
                    else if (rgGeneroAval.getCheckedRadioButtonId()==R.id.rbMujer)
                        params.put(4, "Mujer");
                    else
                        params.put(4, "");

                    if (!tvEstadoNacAval.getText().toString().trim().isEmpty())
                        params.put(5, tvEstadoNacAval.getText().toString().trim());
                    else
                        params.put(5,"");
                    tvCurpAval.setText(Miscellaneous.GenerarCurp(params));
                }
            }
        });
        etNombreAval.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    if (!etNombreAval.getText().toString().trim().isEmpty()){
                        ContentValues cv = new ContentValues();
                        cv.put("nombre", etNombreAval.getText().toString().trim().toUpperCase());
                        if (ENVIROMENT)
                            db.update(DATOS_AVAL_IND, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                        else
                            db.update(DATOS_AVAL_IND_T, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                    }
                    else
                        etNombreAval.setError("Este campo es requerido");
                }
            }
        });
        etApPaternoAval.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                HashMap<Integer, String> params = new HashMap<>();
                if (s.length()> 0){
                    params.put(0, etNombreAval.getText().toString());
                    params.put(1, s.toString());
                    params.put(2, etApMaternoAval.getText().toString());
                    params.put(3, tvFechaNacAval.getText().toString());

                    if (rgGeneroAval.getCheckedRadioButtonId()==R.id.rbHombre)
                        params.put(4, "Hombre");
                    else if (rgGeneroAval.getCheckedRadioButtonId()==R.id.rbMujer)
                        params.put(4, "Mujer");
                    else
                        params.put(4, "");

                    if (!tvEstadoNacAval.getText().toString().trim().isEmpty())
                        params.put(5, tvEstadoNacAval.getText().toString().trim());
                    else
                        params.put(5,"");
                    tvCurpAval.setText(Miscellaneous.GenerarCurp(params));
                }
                else{
                    params.put(0, etNombreAval.getText().toString());
                    params.put(1, "");
                    params.put(2, etApMaternoAval.getText().toString());
                    params.put(3, tvFechaNacAval.getText().toString());

                    if (rgGeneroAval.getCheckedRadioButtonId()==R.id.rbHombre)
                        params.put(4, "Hombre");
                    else if (rgGeneroAval.getCheckedRadioButtonId()==R.id.rbMujer)
                        params.put(4, "Mujer");
                    else
                        params.put(4, "");

                    if (!tvEstadoNacAval.getText().toString().trim().isEmpty())
                        params.put(5, tvEstadoNacAval.getText().toString().trim());
                    else
                        params.put(5,"");
                    tvCurpAval.setText(Miscellaneous.GenerarCurp(params));
                }
            }
        });
        etApPaternoAval.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    if (!etApPaternoAval.getText().toString().trim().isEmpty()){
                        ContentValues cv = new ContentValues();
                        cv.put("paterno", etApPaternoAval.getText().toString().trim().toUpperCase());
                        if (ENVIROMENT)
                            db.update(DATOS_AVAL_IND, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                        else
                            db.update(DATOS_AVAL_IND_T, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                    }
                    else
                        etApPaternoAval.setError("Este campo es requerido");
                }
            }
        });
        etApMaternoAval.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                HashMap<Integer, String> params = new HashMap<>();
                if (s.length()> 0){
                    params.put(0, etNombreAval.getText().toString());
                    params.put(1, etApPaternoAval.getText().toString());
                    params.put(2, s.toString());
                    params.put(3, tvFechaNacAval.getText().toString());

                    if (rgGeneroAval.getCheckedRadioButtonId()==R.id.rbHombre)
                        params.put(4, "Hombre");
                    else if (rgGeneroAval.getCheckedRadioButtonId()==R.id.rbMujer)
                        params.put(4, "Mujer");
                    else
                        params.put(4, "");

                    if (!tvEstadoNacAval.getText().toString().trim().isEmpty())
                        params.put(5, tvEstadoNacAval.getText().toString().trim());
                    else
                        params.put(5,"");
                    tvCurpAval.setText(Miscellaneous.GenerarCurp(params));
                }
                else{
                    params.put(0, etNombreAval.getText().toString());
                    params.put(1, etApPaternoAval.getText().toString());
                    params.put(2, "");
                    params.put(3, tvFechaNacAval.getText().toString());

                    if (rgGeneroAval.getCheckedRadioButtonId()==R.id.rbHombre)
                        params.put(4, "Hombre");
                    else if (rgGeneroAval.getCheckedRadioButtonId()==R.id.rbMujer)
                        params.put(4, "Mujer");
                    else
                        params.put(4, "");

                    if (!tvEstadoNacAval.getText().toString().trim().isEmpty())
                        params.put(5, tvEstadoNacAval.getText().toString().trim());
                    else
                        params.put(5,"");
                    tvCurpAval.setText(Miscellaneous.GenerarCurp(params));
                }
            }
        });
        etApMaternoAval.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    ContentValues cv = new ContentValues();
                    cv.put("materno", etApMaternoAval.getText().toString().trim().toUpperCase());
                    if (ENVIROMENT)
                        db.update(DATOS_AVAL_IND, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                    else
                        db.update(DATOS_AVAL_IND_T, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                }
            }
        });
        tvFechaNacAval.setOnClickListener(tvFechaNacAval_OnClick);
        tvEstadoNacAval.setOnClickListener(tvEstadoNacAval_OnClick);
        tvCurpAval.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0){
                    if (s.toString().contains("Curp no válida"))
                        tvRfcAval.setText("Rfc no válida");
                    else {
                        tvRfcAval.setText(Miscellaneous.GenerarRFC(s.toString().substring(0,10), etNombreAval.getText().toString().trim(), etApPaternoAval.getText().toString().trim(), etApMaternoAval.getText().toString().trim()));

                        ContentValues cv = new ContentValues();
                        cv.put("rfc",tvRfcAval.getText().toString().trim().toUpperCase());
                        cv.put("curp",s.toString().trim().toUpperCase());
                        if (ENVIROMENT)
                            db.update(DATOS_AVAL_IND, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                        else
                            db.update(DATOS_AVAL_IND_T, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                    }

                }
                else
                    tvRfcAval.setText("Rfc no válida");
            }
        });
        etCurpIdAval.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (!etCurpIdAval.getText().toString().trim().isEmpty()) {
                        if (etCurpIdAval.getText().toString().trim().length() == 2) {
                            if (Miscellaneous.CurpValidador(tvCurpAval.getText() + etCurpIdAval.getText().toString().trim().toUpperCase())) {
                                ContentValues cv = new ContentValues();
                                cv.put("curp_digito_veri", etCurpIdAval.getText().toString().trim());
                                if (ENVIROMENT)
                                    db.update(DATOS_AVAL_IND, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                                else
                                    db.update(DATOS_AVAL_IND_T, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                            }
                            else
                                etCurpIdAval.setError("Curp no válida");
                        }
                        else
                            etCurpIdAval.setError("Curp no válida");
                    }
                    else
                        etCurpIdAval.setError("Este campo es requerido");
                }
            }
        });
        tvTipoIdentificacionAval.setOnClickListener(tvTipoIdentificacionAval_OnClick);
        etNumIdentifAval.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    if (!etNumIdentifAval.getText().toString().trim().isEmpty()){
                        ContentValues cv = new ContentValues();
                        cv.put("no_identificacion", etNumIdentifAval.getText().toString().trim().toUpperCase());
                        if (ENVIROMENT)
                            db.update(DATOS_AVAL_IND, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                        else
                            db.update(DATOS_AVAL_IND_T, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                    }
                    else
                        etNumIdentifAval.setError("Este campo es requerido");
                }
            }
        });
        tvOcupacionAval.setOnClickListener(tvOcupacionAval_OnClick);
        ibMapAval.setOnClickListener(ibMapAval_OnClick);
        etCalleAval.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    if (!etCalleAval.getText().toString().trim().isEmpty()){
                        ContentValues cv = new ContentValues();
                        cv.put("calle", etCalleAval.getText().toString().trim().toUpperCase());
                        if (ENVIROMENT)
                            db.update(DATOS_AVAL_IND, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                        else
                            db.update(DATOS_AVAL_IND_T, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                    }
                    else
                        etCalleAval.setError("Este campo es requerido");
                }
            }
        });
        etNoExtAval.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    if (!etNoExtAval.getText().toString().trim().isEmpty()){
                        ContentValues cv = new ContentValues();
                        cv.put("no_exterior", etNoExtAval.getText().toString().trim().toUpperCase());
                        if (ENVIROMENT)
                            db.update(DATOS_AVAL_IND, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                        else
                            db.update(DATOS_AVAL_IND_T, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                    }
                    else
                        etNoExtAval.setError("Este campo es requerido");
                }
            }
        });
        etNoIntAval.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    if (!etNoIntAval.getText().toString().trim().isEmpty()){
                        ContentValues cv = new ContentValues();
                        cv.put("no_interior", etNoIntAval.getText().toString().trim().toUpperCase());
                        if (ENVIROMENT)
                            db.update(DATOS_AVAL_IND, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                        else
                            db.update(DATOS_AVAL_IND_T, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                    }
                    else
                        etNoIntAval.setError("Este campos es requerido");
                }
            }
        });
        etManzanaAval.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    ContentValues cv = new ContentValues();
                    cv.put("manzana", etManzanaAval.getText().toString().trim().toUpperCase());
                    if (ENVIROMENT)
                        db.update(DATOS_AVAL_IND, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                    else
                        db.update(DATOS_AVAL_IND_T, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});

                }
            }
        });
        etLoteAval.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    ContentValues cv = new ContentValues();
                    cv.put("lote", etLoteAval.getText().toString().trim().toUpperCase());
                    if (ENVIROMENT)
                        db.update(DATOS_AVAL_IND, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                    else
                        db.update(DATOS_AVAL_IND_T, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});

                }
            }
        });
        etCpAval.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 5){
                    Cursor row = dBhelper.getDireccionByCP(s.toString());
                    if (row.getCount() > 0){
                        ContentValues cv = new ContentValues();
                        cv.put("cp", s.toString().trim());
                        row.moveToFirst();
                        if (row.getCount() == 1){
                            cv.put("colonia", row.getString(7));
                            tvColoniaAval.setText(row.getString(7));
                            tvMunicipioAval.setText(row.getString(4));
                            tvEstadoAval.setText(row.getString(1));
                        }else {
                            tvColoniaAval.setText("");
                            tvMunicipioAval.setText(row.getString(4));
                            tvEstadoAval.setText(row.getString(1));
                        }
                        if (ENVIROMENT)
                            db.update(DATOS_AVAL_IND, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                        else
                            db.update(DATOS_AVAL_IND_T, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                    }else {
                        tvColoniaAval.setText("No se encontró información");
                        tvMunicipioAval.setText("No se encontró información");
                        tvEstadoAval.setText("No se encontró información");
                    }
                    row.close();
                }else {
                    tvColoniaAval.setText("No se encontró información");
                    tvMunicipioAval.setText("No se encontró información");
                    tvEstadoAval.setText("No se encontró información");
                }
            }
        });
        tvColoniaAval.setOnClickListener(tvColoniaAval_OnClick);
        tvTipoCasaAval.setOnClickListener(tvTipoCasaAval_OnClick);
        tvParentescoAval.setOnClickListener(tvParentescoAval_OnClick);
        etNombreTitularAval.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    if (tvTipoCasaAval.getText().toString().trim().toUpperCase().equals("CASA FAMILIAR")){
                        if (!etNombreTitularAval.getText().toString().trim().isEmpty()) {
                            ContentValues cv = new ContentValues();
                            cv.put("nombre_titular", etNombreTitularAval.getText().toString().trim().toUpperCase());
                            if (ENVIROMENT)
                                db.update(DATOS_AVAL_IND, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                            else
                                db.update(DATOS_AVAL_IND_T, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                        }
                        else
                            etNombreTitularAval.setError("Este campo es requerido");
                    }
                }
            }
        });
        etIngMenAval.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    if (!etIngMenAval.getText().toString().trim().isEmpty()) {
                        ContentValues cv = new ContentValues();
                        cv.put("ing_mensual", etIngMenAval.getText().toString().trim());
                        if (ENVIROMENT)
                            db.update(DATOS_AVAL_IND, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                        else
                            db.update(DATOS_AVAL_IND_T, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                    }
                    else
                        etIngMenAval.setError("Este campo es requerido");
                }
            }
        });
        etIngOtrosAval.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    if (!etIngOtrosAval.getText().toString().trim().isEmpty()) {
                        ContentValues cv = new ContentValues();
                        cv.put("ing_otros", etIngOtrosAval.getText().toString().trim());
                        if (ENVIROMENT)
                            db.update(DATOS_AVAL_IND, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                        else
                            db.update(DATOS_AVAL_IND_T, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                    }
                    else
                        etIngOtrosAval.setError("Este campo es requerido");
                }
            }
        });
        etGastosSemAval.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    if (!etGastosSemAval.getText().toString().trim().isEmpty()) {
                        ContentValues cv = new ContentValues();
                        cv.put("gasto_semanal", etGastosSemAval.getText().toString().trim());
                        if (ENVIROMENT)
                            db.update(DATOS_AVAL_IND, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                        else
                            db.update(DATOS_AVAL_IND_T, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                    }
                    else
                        etGastosSemAval.setError("Este campo es requerido");
                }
            }
        });
        etGastosAguaAval.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    if (!etGastosAguaAval.getText().toString().trim().isEmpty()) {
                        ContentValues cv = new ContentValues();
                        cv.put("gasto_agua", etGastosAguaAval.getText().toString().trim());
                        if (ENVIROMENT)
                            db.update(DATOS_AVAL_IND, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                        else
                            db.update(DATOS_AVAL_IND_T, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                    }
                    else
                        etGastosAguaAval.setError("Este campo es requerido");
                }
            }
        });
        etGastosLuzAval.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    if (!etGastosLuzAval.getText().toString().trim().isEmpty()) {
                        ContentValues cv = new ContentValues();
                        cv.put("gasto_luz", etGastosLuzAval.getText().toString().trim());
                        if (ENVIROMENT)
                            db.update(DATOS_AVAL_IND, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                        else
                            db.update(DATOS_AVAL_IND_T, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                    }
                    else
                        etGastosLuzAval.setError("Este campo es requerido");
                }
            }
        });
        etGastosTelAval.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    if (!etGastosTelAval.getText().toString().trim().isEmpty()) {
                        ContentValues cv = new ContentValues();
                        cv.put("gasto_telefono", etGastosTelAval.getText().toString().trim());
                        if (ENVIROMENT)
                            db.update(DATOS_AVAL_IND, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                        else
                            db.update(DATOS_AVAL_IND_T, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                    }
                    else
                        etGastosTelAval.setError("Este campo es requerido");
                }
            }
        });
        etGastosRentaAval.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    if (!etGastosRentaAval.getText().toString().trim().isEmpty()) {
                        ContentValues cv = new ContentValues();
                        cv.put("gasto_renta", etGastosRentaAval.getText().toString().trim());
                        if (ENVIROMENT)
                            db.update(DATOS_AVAL_IND, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                        else
                            db.update(DATOS_AVAL_IND_T, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                    }
                    else
                        etGastosRentaAval.setError("Este campo es requerido");
                }
            }
        });
        etGastosOtrosAval.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    if (!etGastosOtrosAval.getText().toString().trim().isEmpty()) {
                        ContentValues cv = new ContentValues();
                        cv.put("gasto_otros", etGastosOtrosAval.getText().toString().trim());
                        if (ENVIROMENT)
                            db.update(DATOS_AVAL_IND, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                        else
                            db.update(DATOS_AVAL_IND_T, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                    }
                    else
                        etGastosOtrosAval.setError("Este campo es requerido");
                }
            }
        });
        tvHoraLocAval.setOnClickListener(tvHoraLocAval_OnClick);
        etAntiguedadAval.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    if (!etAntiguedadAval.getText().toString().trim().isEmpty()) {
                        if (Integer.parseInt(etAntiguedadAval.getText().toString().trim()) > 0) {
                            ContentValues cv = new ContentValues();
                            cv.put("antiguedad", etAntiguedadAval.getText().toString().trim());
                            if (ENVIROMENT)
                                db.update(DATOS_AVAL_IND, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                            else
                                db.update(DATOS_AVAL_IND_T, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                        }
                        else
                            etAntiguedadAval.setError("No se permiten cantidades iguales a cero");
                    }
                    else
                        etAntiguedadAval.setError("Este campo es requerido");
                }
            }
        });
        etTelCasaAval.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0){
                    if (s.length() == 10) {
                        etTelCasaAval.setError(null);
                        ContentValues cv = new ContentValues();
                        cv.put("tel_casa", etTelCasaAval.getText().toString().trim());
                        if (ENVIROMENT)
                            db.update(DATOS_AVAL_IND, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                        else
                            db.update(DATOS_AVAL_IND_T, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                    }

                    else
                        etTelCasaAval.setError(ctx.getResources().getString(R.string.mensaje_telefono));
                }
                else
                    etTelCasaAval.setError(null);
            }
        });
        etCelularAval.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0){
                    if (s.length() == 10) {
                        etCelularAval.setError(null);
                        ContentValues cv = new ContentValues();
                        cv.put("tel_celular", etCelularAval.getText().toString().trim());
                        if (ENVIROMENT)
                            db.update(DATOS_AVAL_IND, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                        else
                            db.update(DATOS_AVAL_IND_T, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                    }
                    else
                        etCelularAval.setError(ctx.getResources().getString(R.string.mensaje_telefono));
                }
                else
                    etCelularAval.setError(null);
            }
        });
        ibFotoFachAval.setOnClickListener(ibFotoFachAval_OnClick);
        etReferenciaAval.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    if (!etReferenciaAval.getText().toString().trim().isEmpty()) {
                        ContentValues cv = new ContentValues();
                        cv.put("ref_domiciliaria", etReferenciaAval.getText().toString().trim().toUpperCase());
                        if (ENVIROMENT)
                            db.update(DATOS_AVAL_IND, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                        else
                            db.update(DATOS_AVAL_IND_T, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                    }
                    else
                        etReferenciaAval.setError("Este campo es requerido");
                }
            }
        });
        ibFirmaAval.setOnClickListener(ibFirmaAval_OnClick);
        //============== REFERENCIA ================================
        etNombreRef.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    if (!etNombreRef.getText().toString().trim().isEmpty()){
                        ContentValues cv = new ContentValues();
                        cv.put("nombre", etNombreRef.getText().toString().trim().toUpperCase());
                        if (ENVIROMENT)
                            db.update(DATOS_REFERENCIA_IND, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                        else
                            db.update(DATOS_REFERENCIA_IND_T, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                    }
                    else
                        etNombreRef.setError("Este campo es requerido");
                }
            }
        });
        etApPaternoRef.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    if (!etApPaternoRef.getText().toString().trim().isEmpty()){
                        ContentValues cv = new ContentValues();
                        cv.put("paterno", etApPaternoRef.getText().toString().trim().toUpperCase());
                        if (ENVIROMENT)
                            db.update(DATOS_REFERENCIA_IND, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                        else
                            db.update(DATOS_REFERENCIA_IND_T, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                    }
                    else
                        etApPaternoRef.setError("Este campo es requerido");
                }
            }
        });
        etApMaternoRef.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    ContentValues cv = new ContentValues();
                    cv.put("materno", etApMaternoRef.getText().toString().trim().toUpperCase());
                    if (ENVIROMENT)
                        db.update(DATOS_REFERENCIA_IND, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                    else
                        db.update(DATOS_REFERENCIA_IND_T, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                }
            }
        });
        etCalleRef.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    if (!etCalleRef.getText().toString().trim().isEmpty()){
                        ContentValues cv = new ContentValues();
                        cv.put("calle", etCalleRef.getText().toString().trim().toUpperCase());
                        if (ENVIROMENT)
                            db.update(DATOS_REFERENCIA_IND, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                        else
                            db.update(DATOS_REFERENCIA_IND_T, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                    }
                    else
                        etCalleRef.setError("Este campo es requerido");
                }
            }
        });
        etCpRef.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 5){
                    Cursor row = dBhelper.getDireccionByCP(s.toString());
                    if (row.getCount() > 0){
                        ContentValues cv = new ContentValues();
                        cv.put("cp", s.toString().trim());
                        row.moveToFirst();
                        if (row.getCount() == 1){
                            cv.put("colonia", row.getString(7));
                            tvColoniaRef.setText(row.getString(7));
                            tvMunicipioRef.setText(row.getString(4));
                        }else {
                            tvColoniaRef.setText("");
                            tvMunicipioRef.setText(row.getString(4));
                            cv.put("municipio", row.getString(4));
                        }
                        if (ENVIROMENT)
                            db.update(DATOS_REFERENCIA_IND, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                        else
                            db.update(DATOS_REFERENCIA_IND_T, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                    }else {
                        tvColoniaRef.setText("No se encontró información");
                        tvMunicipioRef.setText("No se encontró información");
                    }
                    row.close();
                }else {
                    tvColoniaRef.setText("No se encontró información");
                    tvMunicipioRef.setText("No se encontró información");
                }
            }
        });
        tvColoniaRef.setOnClickListener(tvColoniaRef_OnClick);
        etTelCelRef.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0){
                    if (s.length() == 10) {
                        etTelCelRef.setError(null);
                        ContentValues cv = new ContentValues();
                        cv.put("tel_celular", etTelCelRef.getText().toString().trim());
                        if (ENVIROMENT)
                            db.update(DATOS_REFERENCIA_IND, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                        else
                            db.update(DATOS_REFERENCIA_IND_T, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                    }
                    else
                        etTelCelRef.setError(ctx.getResources().getString(R.string.mensaje_telefono));
                }
                else
                    etTelCelRef.setError(null);
            }
        });
        //================================  ESCANEAR DOCUMENTOS  ===================================
        ibIneFrontal.setOnClickListener(ibIneFrontal_OnClick);
        ibIneReverso.setOnClickListener(ibIneReverso_OnClick);
        ibCurp.setOnClickListener(ibCurp_OnClick);
        ibComprobante.setOnClickListener(ibComprobante_OnClick);
        //================================  CLIENTE GENERO LISTENER  ===============================
        rgGeneroCli.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (is_edit) {
                    tvGeneroCli.setError(null);
                    ContentValues cv = new ContentValues();;
                    HashMap<Integer, String> params = new HashMap<>();
                    if (checkedId == R.id.rbHombre) {

                        params.put(0, etNombreCli.getText().toString());
                        params.put(1, etApPaternoCli.getText().toString());
                        params.put(2, etApMaternoCli.getText().toString());
                        params.put(3, tvFechaNacCli.getText().toString());
                        params.put(4, "Hombre");

                        if (!tvEstadoNacCli.getText().toString().trim().isEmpty())
                            params.put(5, tvEstadoNacCli.getText().toString().trim());
                        else
                            params.put(5, "");
                        tvCurpCli.setText(Miscellaneous.GenerarCurp(params));
                        cv.put("genero", "0");
                    } else if (checkedId == R.id.rbMujer) {
                        params.put(0, etNombreCli.getText().toString());
                        params.put(1, etApPaternoCli.getText().toString());
                        params.put(2, etApMaternoCli.getText().toString());
                        params.put(3, tvFechaNacCli.getText().toString());
                        params.put(4, "Mujer");

                        if (!tvEstadoNacCli.getText().toString().trim().isEmpty())
                            params.put(5, tvEstadoNacCli.getText().toString().trim());
                        else
                            params.put(5, "");
                        tvCurpCli.setText(Miscellaneous.GenerarCurp(params));
                        cv.put("genero", "1");
                    }
                    else {
                        params.put(0, etNombreCli.getText().toString());
                        params.put(1, etApPaternoCli.getText().toString());
                        params.put(2, etApMaternoCli.getText().toString());
                        params.put(3, tvFechaNacCli.getText().toString());
                        params.put(4, "");

                        if (!tvEstadoNacCli.getText().toString().trim().isEmpty())
                            params.put(5, tvEstadoNacCli.getText().toString().trim());
                        else
                            params.put(5, "");
                        tvCurpCli.setText(Miscellaneous.GenerarCurp(params));
                        cv.put("genero", "2");
                    }

                    if (ENVIROMENT)
                        db.update(DATOS_CLIENTE_IND, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                    else
                        db.update(DATOS_CLIENTE_IND_T, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                }
            }
        });
        rgBienes.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (is_edit) {
                    tvBienes.setError(null);
                    ContentValues cv = new ContentValues();
                    switch (checkedId) {
                        case R.id.rbMancomunados:
                            cv.put("bienes", "1");
                            break;
                        case R.id.rbSeparados:
                            cv.put("bienes", "2");
                            break;
                    }

                    if (ENVIROMENT)
                        db.update(DATOS_CLIENTE_IND, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                    else
                        db.update(DATOS_CLIENTE_IND_T, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});

                }
            }
        });
        //===========================  AVAL GENERO LISTENER  =======================================
        rgGeneroAval.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (is_edit) {
                    tvGeneroAval.setError(null);
                    ContentValues cv = new ContentValues();
                    if (checkedId == R.id.rbHombre) {
                        HashMap<Integer, String> params = new HashMap<>();
                        params.put(0, etNombreAval.getText().toString());
                        params.put(1, etApPaternoAval.getText().toString());
                        params.put(2, etApMaternoAval.getText().toString());
                        params.put(3, tvFechaNacAval.getText().toString());
                        params.put(4, "Hombre");

                        if (!tvEstadoNacAval.getText().toString().trim().isEmpty())
                            params.put(5, tvEstadoNacAval.getText().toString().trim());
                        else
                            params.put(5, "");
                        cv.put("genero", "0");
                        tvCurpAval.setText(Miscellaneous.GenerarCurp(params));
                    } else if (checkedId == R.id.rbMujer) {
                        HashMap<Integer, String> params = new HashMap<>();
                        params.put(0, etNombreAval.getText().toString());
                        params.put(1, etApPaternoAval.getText().toString());
                        params.put(2, etApMaternoAval.getText().toString());
                        params.put(3, tvFechaNacAval.getText().toString());
                        params.put(4, "Mujer");

                        if (!tvEstadoNacAval.getText().toString().trim().isEmpty())
                            params.put(5, tvEstadoNacAval.getText().toString().trim());
                        else
                            params.put(5, "");

                        cv.put("genero", "1");
                        tvCurpAval.setText(Miscellaneous.GenerarCurp(params));
                    }
                    else {
                        HashMap<Integer, String> params = new HashMap<>();
                        params.put(0, etNombreAval.getText().toString());
                        params.put(1, etApPaternoAval.getText().toString());
                        params.put(2, etApMaternoAval.getText().toString());
                        params.put(3, tvFechaNacAval.getText().toString());
                        params.put(4, "");

                        if (!tvEstadoNacAval.getText().toString().trim().isEmpty())
                            params.put(5, tvEstadoNacAval.getText().toString().trim());
                        else
                            params.put(5, "");

                        cv.put("genero", "2");
                        tvCurpAval.setText(Miscellaneous.GenerarCurp(params));
                    }

                    if (ENVIROMENT)
                        db.update(DATOS_AVAL_IND, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                    else
                        db.update(DATOS_AVAL_IND_T, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                }
            }
        });

    }

    private View.OnClickListener ibFirmaAsesor_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i_firma_asesor = new Intent(context, CapturarFirma.class);
            i_firma_asesor.putExtra(Constants.TIPO,"");
            startActivityForResult(i_firma_asesor,Constants.REQUEST_CODE_FIRMA_ASESOR);
        }
    };

    private View.OnClickListener tvPlazo_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (is_edit) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle(R.string.selected_option)
                        .setItems(R.array.intervalo, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int position) {
                                tvPlazo.setError(null);
                                tvPlazo.setText(_plazo[position]);
                                ContentValues cv = new ContentValues();
                                cv.put("plazo", tvPlazo.getText().toString().trim().toUpperCase());
                                if (ENVIROMENT)
                                    db.update(DATOS_CREDITO_IND, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                                else
                                    db.update(DATOS_CREDITO_IND_T, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                            }
                        });
                builder.create();
                builder.show();
            }
        }
    };

    private View.OnClickListener tvFrecuencia_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (is_edit) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle(R.string.selected_option)
                        .setItems(R.array.lapso, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int position) {
                                tvFrecuencia.setError(null);
                                tvFrecuencia.setText(_frecuencia[position]);
                                ContentValues cv = new ContentValues();
                                cv.put("periodicidad", tvFrecuencia.getText().toString().trim().toUpperCase());
                                if (ENVIROMENT)
                                    db.update(DATOS_CREDITO_IND, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                                else
                                    db.update(DATOS_CREDITO_IND_T, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                            }
                        });
                builder.create();
                builder.show();
            }
        }
    };

    private View.OnClickListener tvFechaDesembolso_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (is_edit) {
                dialog_date_picker dialogDatePicker = new dialog_date_picker();
                Bundle b = new Bundle();

                b.putInt(Constants.YEAR_CURRENT, myCalendar.get(Calendar.YEAR));
                b.putInt(Constants.MONTH_CURRENT, myCalendar.get(Calendar.MONTH));
                b.putInt(Constants.DAY_CURRENT, myCalendar.get(Calendar.DAY_OF_MONTH));
                b.putString(Constants.DATE_CURRENT, sdf.format(myCalendar.getTime()));
                b.putInt(Constants.IDENTIFIER, 1);
                b.putBoolean(Constants.FECHAS_POST, true);
                dialogDatePicker.setArguments(b);
                dialogDatePicker.show(getSupportFragmentManager(), NameFragments.DIALOGDATEPICKER);
            }
        }
    };

    private View.OnClickListener tvDestino_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (is_edit) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle(R.string.selected_option)
                        .setItems(R.array.destino_prestamo, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int position) {
                                tvDestino.setError(null);
                                tvDestino.setText(_destino[position]);
                                ContentValues cv = new ContentValues();
                                cv.put("destino", tvDestino.getText().toString().trim().toUpperCase());
                                if (ENVIROMENT)
                                    db.update(DATOS_CREDITO_IND, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                                else
                                    db.update(DATOS_CREDITO_IND_T, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                            }
                        });
                builder.create();
                builder.show();
            }
        }
    };

    private View.OnClickListener tvFechaNac_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            dialog_date_picker dialogDatePicker = new dialog_date_picker();
            Bundle b = new Bundle();

            b.putInt(Constants.YEAR_CURRENT,myCalendar.get(Calendar.YEAR));
            b.putInt(Constants.MONTH_CURRENT,myCalendar.get(Calendar.MONTH));
            b.putInt(Constants.DAY_CURRENT,myCalendar.get(Calendar.DAY_OF_MONTH));
            b.putString(Constants.DATE_CURRENT,sdf.format(myCalendar.getTime()));
            b.putInt(Constants.IDENTIFIER,2);
            b.putBoolean(Constants.FECHAS_POST, false);
            dialogDatePicker.setArguments(b);
            dialogDatePicker.show(getSupportFragmentManager(), NameFragments.DIALOGDATEPICKER);
        }
    };

    private View.OnClickListener ibFirmaCli_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i_firma_cli = new Intent(context, CapturarFirma.class);
            i_firma_cli.putExtra(Constants.TIPO,"CLIENTE");
            startActivityForResult(i_firma_cli,Constants.REQUEST_CODE_FIRMA_CLI);
        }
    };

    private View.OnClickListener ibMapCli_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
                Toast.makeText(ctx, "El GPS se encuentra desactivado", Toast.LENGTH_SHORT).show();
            else
                ObtenerUbicacion();
        }
    };

    private View.OnClickListener ibFotoFachCli_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i = new Intent(SolicitudCreditoInd.this, CameraVertical.class);
            i.putExtra(Constants.ORDER_ID, "SC_cliente");
            startActivityForResult(i, Constants.REQUEST_CODE_CAMARA_FACHADA_CLI);
        }
    };

    private View.OnClickListener etEstadoNac_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (is_edit) {
                Intent i_estados = new Intent(context, Catalogos.class);
                i_estados.putExtra(Constants.TITULO, Miscellaneous.ucFirst(Constants.ESTADOS));
                i_estados.putExtra(Constants.CATALOGO, Constants.ESTADOS);
                i_estados.putExtra(Constants.REQUEST_CODE, Constants.REQUEST_CODE_ESTADO_NAC);
                startActivityForResult(i_estados, Constants.REQUEST_CODE_ESTADO_NAC);
            }
        }
    };

    private View.OnClickListener etColonia_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (is_edit) {
                Intent i_colonia = new Intent(context, Catalogos.class);
                i_colonia.putExtra(Constants.TITULO, Miscellaneous.ucFirst(Constants.ESTADOS));
                i_colonia.putExtra(Constants.CATALOGO, Constants.COLONIAS);
                i_colonia.putExtra(Constants.EXTRA, etCpCli.getText().toString().trim());
                i_colonia.putExtra(Constants.REQUEST_CODE, Constants.REQUEST_CODE_COLONIA_CLIE);
                startActivityForResult(i_colonia, Constants.REQUEST_CODE_COLONIA_CLIE);
            }
        }
    };

    private View.OnClickListener tvEstudiosCli_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (is_edit) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle(R.string.selected_option)
                        .setItems(R.array.nivel_estudio, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int position) {
                                tvEstudiosCli.setError(null);
                                tvEstudiosCli.setText(_estudios[position]);
                                tvEstudiosCli.requestFocus();
                                ContentValues cv = new ContentValues();
                                cv.put("nivel_estudio", tvEstudiosCli.getText().toString().trim().toUpperCase());
                                if (ENVIROMENT)
                                    db.update(DATOS_CLIENTE_IND, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                                else
                                    db.update(DATOS_CLIENTE_IND_T, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                            }
                        });
                builder.create();
                builder.show();
            }
        }
    };

    private View.OnClickListener tvEstadoCivilCli_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (is_edit) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle(R.string.selected_option)
                        .setItems(R.array.estado_civil, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int position) {
                                tvEstadoCivilCli.setError(null);
                                tvEstadoCivilCli.setText(_civil[position]);
                                if (position == 1) {
                                    llBienes.setVisibility(View.VISIBLE);
                                    llConyuge.setVisibility(View.VISIBLE);
                                } else if (position == 4) {
                                    llConyuge.setVisibility(View.VISIBLE);
                                    llBienes.setVisibility(View.GONE);
                                } else {
                                    llConyuge.setVisibility(View.GONE);
                                    llBienes.setVisibility(View.GONE);
                                }
                                ContentValues cv = new ContentValues();
                                cv.put("estado_civil", tvEstadoCivilCli.getText().toString().trim().toUpperCase());
                                if (ENVIROMENT)
                                    db.update(DATOS_CLIENTE_IND, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                                else
                                    db.update(DATOS_CLIENTE_IND_T, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                            }
                        });
                builder.create();
                builder.show();
            }
        }
    };

    private View.OnClickListener tvTipoCasaCli_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (is_edit) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle(R.string.selected_option)
                        .setItems(R.array.tipo_casa_cli, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int position) {
                                tvTipoCasaCli.setError(null);
                                tvTipoCasaCli.setText(_tipo_casa[position]);
                                switch (position) {
                                    case 2:
                                        llCasaFamiliar.setVisibility(View.VISIBLE);
                                        llCasaOtroCli.setVisibility(View.GONE);
                                        break;
                                    case 3:
                                        llCasaOtroCli.setVisibility(View.VISIBLE);
                                        llCasaFamiliar.setVisibility(View.GONE);
                                        break;
                                    default:
                                        llCasaFamiliar.setVisibility(View.GONE);
                                        llCasaOtroCli.setVisibility(View.GONE);
                                        break;
                                }
                                ContentValues cv = new ContentValues();
                                cv.put("tipo_vivienda", tvTipoCasaCli.getText().toString().trim().toUpperCase());
                                if (ENVIROMENT)
                                    db.update(DATOS_CLIENTE_IND, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                                else
                                    db.update(DATOS_CLIENTE_IND_T, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                            }
                        });
                builder.create();
                builder.show();
            }
        }
    };

    private View.OnClickListener tvCasaFamiliar_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (is_edit) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle(R.string.selected_option)
                        .setItems(R.array.casa_familiar, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int position) {
                                tvCasaFamiliar.setError(null);
                                tvCasaFamiliar.setText(_casa_familiar[position]);
                                ContentValues cv = new ContentValues();
                                cv.put("parentesco", tvCasaFamiliar.getText().toString().trim().toUpperCase());
                                if (ENVIROMENT)
                                    db.update(DATOS_CLIENTE_IND, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                                else
                                    db.update(DATOS_CLIENTE_IND_T, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                            }
                        });
                builder.create();
                builder.show();
            }
        }
    };

    private View.OnClickListener tvDependientes_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (is_edit) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle(R.string.selected_option)
                        .setItems(R.array.dependientes_eco, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int position) {
                                tvDependientes.setError(null);
                                tvDependientes.setText(_dependientes[position]);
                                ContentValues cv = new ContentValues();
                                cv.put("dependientes", tvDependientes.getText().toString().trim().toUpperCase());
                                if (ENVIROMENT)
                                    db.update(DATOS_CLIENTE_IND, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                                else
                                    db.update(DATOS_CLIENTE_IND_T, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                            }
                        });
                builder.create();
                builder.show();
            }
        }
    };

    private View.OnClickListener tvEnteroNosotros_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle(R.string.selected_option)
                    .setItems(R.array.entero_nosotros, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int position) {
                            tvEnteroNosotros.setError(null);
                            tvEnteroNosotros.setText(_medio_contacto[position]);
                            ContentValues cv = new ContentValues();
                            cv.put("medio_contacto", tvEnteroNosotros.getText().toString().trim().toUpperCase());
                            if (ENVIROMENT)
                                db.update(DATOS_CLIENTE_IND, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                            else
                                db.update(DATOS_CLIENTE_IND_T, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                        }
                    });
            builder.create();
            builder.show();
        }
    };

    private View.OnClickListener tvColoniaAval_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (is_edit) {
                Intent i_colonia = new Intent(context, Catalogos.class);
                i_colonia.putExtra(Constants.TITULO, Miscellaneous.ucFirst(Constants.ESTADOS));
                i_colonia.putExtra(Constants.CATALOGO, Constants.COLONIAS);
                i_colonia.putExtra(Constants.EXTRA, etCpAval.getText().toString().trim());
                i_colonia.putExtra(Constants.REQUEST_CODE, Constants.REQUEST_CODE_COLONIA_AVAL);
                startActivityForResult(i_colonia, Constants.REQUEST_CODE_COLONIA_AVAL);
            }
        }
    };

    private View.OnClickListener tvTipoCasaAval_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (is_edit) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle(R.string.selected_option)
                        .setItems(R.array.tipo_casa_aval, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int position) {
                                tvTipoCasaAval.setError(null);
                                tvTipoCasaAval.setText(_tipo_casa_aval[position]);
                                switch (position) {
                                    case 0: //PROPIA
                                        llNombreTitular.setVisibility(View.GONE);
                                        llParentescoFamAval.setVisibility(View.GONE);
                                        break;
                                    case 1://FAMILIAR
                                        llNombreTitular.setVisibility(View.VISIBLE);
                                        llParentescoFamAval.setVisibility(View.VISIBLE);
                                        break;
                                    default://NINGUNO
                                        llNombreTitular.setVisibility(View.GONE);
                                        llParentescoFamAval.setVisibility(View.GONE);
                                        break;
                                }
                                ContentValues cv = new ContentValues();
                                cv.put("tipo_vivienda", tvTipoCasaAval.getText().toString().trim().toUpperCase());
                                if (ENVIROMENT)
                                    db.update(DATOS_AVAL_IND, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                                else
                                    db.update(DATOS_AVAL_IND_T, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                            }
                        });
                builder.create();
                builder.show();
            }
        }
    };

    private View.OnClickListener tvParentescoAval_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (is_edit) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle(R.string.selected_option)
                        .setItems(R.array.casa_familiar_aval, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int position) {
                                tvParentescoAval.setError(null);
                                tvParentescoAval.setText(_parentesco[position]);
                                ContentValues cv = new ContentValues();
                                cv.put("parentesco", tvParentescoAval.getText().toString().trim().toUpperCase());
                                if (ENVIROMENT)
                                    db.update(DATOS_AVAL_IND, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                                else
                                    db.update(DATOS_AVAL_IND_T, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                            }
                        });
                builder.create();
                builder.show();
            }
        }
    };

    private View.OnClickListener ibFirmaAval_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i_firma_aval = new Intent(context, CapturarFirma.class);
            i_firma_aval.putExtra(Constants.TIPO,"AVAL");
            startActivityForResult(i_firma_aval,Constants.REQUEST_CODE_FIRMA_AVAL);
        }
    };

    private View.OnClickListener etDiasVenta_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (is_edit) {
                showDiasSemana();
            }
        }
    };

    private View.OnClickListener etColoniaAct_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (is_edit) {
                Intent i_colonia = new Intent(context, Catalogos.class);
                i_colonia.putExtra(Constants.TITULO, Miscellaneous.ucFirst(Constants.COLONIAS));
                i_colonia.putExtra(Constants.CATALOGO, Constants.COLONIAS);
                i_colonia.putExtra(Constants.EXTRA, etCpNeg.getText().toString().trim());
                i_colonia.putExtra(Constants.REQUEST_CODE, Constants.REQUEST_CODE_COLONIA_NEG);
                startActivityForResult(i_colonia, Constants.REQUEST_CODE_COLONIA_NEG);
            }
        }
    };

    private View.OnClickListener ibMapNeg_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
                Toast.makeText(ctx, "El GPS se encuentra desactivado", Toast.LENGTH_SHORT).show();
            else
                ObtenerUbicacionNeg();
        }
    };

    private View.OnClickListener ibFotoFachNeg_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i = new Intent(SolicitudCreditoInd.this, CameraVertical.class);
            i.putExtra(Constants.ORDER_ID, "SC_negocio");
            startActivityForResult(i, Constants.REQUEST_CODE_CAMARA_FACHADA_NEG);
        }
    };

    private View.OnClickListener tvOcupacionClie_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (is_edit) {
                Intent i_ocupaciones = new Intent(context, Catalogos.class);
                i_ocupaciones.putExtra(Constants.TITULO, Miscellaneous.ucFirst(Constants.OCUPACIONES));
                i_ocupaciones.putExtra(Constants.CATALOGO, Constants.OCUPACIONES);
                i_ocupaciones.putExtra(Constants.REQUEST_CODE, Constants.REQUEST_CODE_OCUPACION_CLIE);
                startActivityForResult(i_ocupaciones, Constants.REQUEST_CODE_OCUPACION_CLIE);
            }
        }
    };

    private View.OnClickListener tvTipoIdentificacion_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (is_edit) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle(R.string.selected_option)
                        .setItems(R.array.tipo_identificacion, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int position) {
                                tvTipoIdentificacion.setError(null);
                                tvTipoIdentificacion.setText(_tipo_identificacion[position]);
                                ContentValues cv = new ContentValues();
                                cv.put("tipo_identificacion", tvTipoIdentificacion.getText().toString().trim().toUpperCase());
                                if (ENVIROMENT)
                                    db.update(DATOS_CLIENTE_IND, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                                else
                                    db.update(DATOS_CLIENTE_IND_T, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                            }
                        });
                builder.create();
                builder.show();
            }
        }
    };

    private View.OnClickListener tvTipoIdentificacionAval_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (is_edit) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle(R.string.selected_option)
                        .setItems(R.array.tipo_identificacion, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int position) {
                                tvTipoIdentificacionAval.setError(null);
                                tvTipoIdentificacionAval.setText(_tipo_identificacion[position]);
                                ContentValues cv = new ContentValues();
                                cv.put("tipo_identificacion", tvTipoIdentificacionAval.getText().toString().trim().toUpperCase());
                                if (ENVIROMENT)
                                    db.update(DATOS_AVAL_IND, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                                else
                                    db.update(DATOS_AVAL_IND_T, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                            }
                        });
                builder.create();
                builder.show();
            }
        }
    };

    private View.OnClickListener tvOcupacionConyuge_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (is_edit) {
                Intent i_ocupaciones = new Intent(context, Catalogos.class);
                i_ocupaciones.putExtra(Constants.TITULO, Miscellaneous.ucFirst(Constants.OCUPACIONES));
                i_ocupaciones.putExtra(Constants.CATALOGO, Constants.OCUPACIONES);
                i_ocupaciones.putExtra(Constants.REQUEST_CODE, Constants.REQUEST_CODE_OCUPACION_CONY);
                startActivityForResult(i_ocupaciones, Constants.REQUEST_CODE_OCUPACION_CONY);
            }
        }
    };

    private View.OnClickListener etActividadEco_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (is_edit) {
                Intent i_ocupaciones = new Intent(context, Catalogos.class);
                i_ocupaciones.putExtra(Constants.TITULO, Miscellaneous.ucFirst(Constants.SECTORES));
                i_ocupaciones.putExtra(Constants.CATALOGO, Constants.SECTORES);
                i_ocupaciones.putExtra(Constants.REQUEST_CODE, Constants.REQUEST_CODE_ACTIVIDAD_NEG);
                startActivityForResult(i_ocupaciones, Constants.REQUEST_CODE_ACTIVIDAD_NEG);
            }
        }
    };

    //============ REFERNCIA =================================================================
    private View.OnClickListener tvColoniaRef_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (is_edit) {
                Intent i_colonia = new Intent(context, Catalogos.class);
                i_colonia.putExtra(Constants.TITULO, Miscellaneous.ucFirst(Constants.COLONIAS));
                i_colonia.putExtra(Constants.CATALOGO, Constants.COLONIAS);
                i_colonia.putExtra(Constants.EXTRA, etCpRef.getText().toString().trim());
                i_colonia.putExtra(Constants.REQUEST_CODE, Constants.REQUEST_CODE_COLONIA_REF);
                startActivityForResult(i_colonia, Constants.REQUEST_CODE_COLONIA_REF);
            }
        }
    };

    //============ AVAL =====================
    private View.OnClickListener tvFechaNacAval_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (is_edit) {
                dialog_date_picker dialogDatePicker = new dialog_date_picker();
                Bundle b = new Bundle();

                b.putInt(Constants.YEAR_CURRENT, myCalendar.get(Calendar.YEAR));
                b.putInt(Constants.MONTH_CURRENT, myCalendar.get(Calendar.MONTH));
                b.putInt(Constants.DAY_CURRENT, myCalendar.get(Calendar.DAY_OF_MONTH));
                b.putString(Constants.DATE_CURRENT, sdf.format(myCalendar.getTime()));
                b.putInt(Constants.IDENTIFIER, 3);
                b.putBoolean(Constants.FECHAS_POST, false);
                dialogDatePicker.setArguments(b);
                dialogDatePicker.show(getSupportFragmentManager(), NameFragments.DIALOGDATEPICKER);
            }
        }
    };

    private View.OnClickListener tvEstadoNacAval_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (is_edit) {
                Intent i_estados = new Intent(context, Catalogos.class);
                i_estados.putExtra(Constants.TITULO, Miscellaneous.ucFirst(Constants.ESTADOS));
                i_estados.putExtra(Constants.CATALOGO, Constants.ESTADOS);
                i_estados.putExtra(Constants.REQUEST_CODE, Constants.REQUEST_CODE_ESTADO_NAC_AVAL);
                startActivityForResult(i_estados, Constants.REQUEST_CODE_ESTADO_NAC_AVAL);
            }
        }
    };

    private View.OnClickListener tvOcupacionAval_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (is_edit) {
                Intent i_ocupaciones = new Intent(context, Catalogos.class);
                i_ocupaciones.putExtra(Constants.TITULO, Miscellaneous.ucFirst(Constants.OCUPACIONES));
                i_ocupaciones.putExtra(Constants.CATALOGO, Constants.OCUPACIONES);
                i_ocupaciones.putExtra(Constants.REQUEST_CODE, Constants.REQUEST_CODE_OCUPACION_AVAL);
                startActivityForResult(i_ocupaciones, Constants.REQUEST_CODE_OCUPACION_AVAL);
            }
        }
    };

    private View.OnClickListener tvHoraLocAval_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (is_edit) {
                dialog_time_picker timePicker = new dialog_time_picker();
                Bundle b = new Bundle();
                b.putInt(Constants.IDENTIFIER, 2);
                timePicker.setArguments(b);
                timePicker.show(getSupportFragmentManager(), NameFragments.DIALOGTIMEPICKER);
            }
        }
    };

    private View.OnClickListener ibMapAval_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
                Toast.makeText(ctx, "El GPS se encuentra desactivado", Toast.LENGTH_SHORT).show();
            else
                ObtenerUbicacionAval();
        }
    };

    private View.OnClickListener ibFotoFachAval_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i = new Intent(SolicitudCreditoInd.this, CameraVertical.class);
            i.putExtra(Constants.ORDER_ID, "SC_aval");
            startActivityForResult(i, Constants.REQUEST_CODE_CAMARA_FACHADA_AVAL);
        }
    };

    //======================  DOCUMENTOS  ==============================
    private View.OnClickListener ibIneFrontal_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent scanIntent = new Intent(SolicitudCreditoInd.this, CardIOActivity.class);
            scanIntent.putExtra(CardIOActivity.EXTRA_SUPPRESS_SCAN,true); // supmit cuando termine de reconocer el documento
            scanIntent.putExtra(CardIOActivity.EXTRA_SUPPRESS_MANUAL_ENTRY,true); // esconder teclado
            scanIntent.putExtra(CardIOActivity.EXTRA_USE_CARDIO_LOGO,true); // cambiar logo de paypal por el de card.io
            scanIntent.putExtra(CardIOActivity.EXTRA_RETURN_CARD_IMAGE,true); // capture img
            scanIntent.putExtra(CardIOActivity.EXTRA_CAPTURED_CARD_IMAGE,true); // capturar img

            // laszar activity
            startActivityForResult(scanIntent, Constants.REQUEST_CODE_FOTO_INE_FRONTAL);
        }
    };

    private View.OnClickListener ibIneReverso_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent scanIntent = new Intent(SolicitudCreditoInd.this, CardIOActivity.class);
            scanIntent.putExtra(CardIOActivity.EXTRA_SUPPRESS_SCAN,true); // supmit cuando termine de reconocer el documento
            scanIntent.putExtra(CardIOActivity.EXTRA_SUPPRESS_MANUAL_ENTRY,true); // esconder teclado
            scanIntent.putExtra(CardIOActivity.EXTRA_USE_CARDIO_LOGO,true); // cambiar logo de paypal por el de card.io
            scanIntent.putExtra(CardIOActivity.EXTRA_RETURN_CARD_IMAGE,true); // capture img
            scanIntent.putExtra(CardIOActivity.EXTRA_CAPTURED_CARD_IMAGE,true); // capturar img

            // laszar activity
            startActivityForResult(scanIntent, Constants.REQUEST_CODE_FOTO_INE_REVERSO);
        }
    };

    private View.OnClickListener ibCurp_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i = new Intent(SolicitudCreditoInd.this, CameraVertical.class);
            i.putExtra(Constants.ORDER_ID, "O_curp");
            startActivityForResult(i, Constants.REQUEST_CODE_FOTO_CURP);
        }
    };

    private View.OnClickListener ibComprobante_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i = new Intent(SolicitudCreditoInd.this, CameraVertical.class);
            i.putExtra(Constants.ORDER_ID, "O_comprobante");
            startActivityForResult(i, Constants.REQUEST_CODE_FOTO_COMPROBATE);
        }
    };

    //================== IMAGE VIEW  ===================================
    private View.OnClickListener llCredito_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (ivDown1.getVisibility() == View.VISIBLE && ivUp1.getVisibility() == View.GONE){
                ivDown1.setVisibility(View.GONE);
                ivUp1.setVisibility(View.VISIBLE);
                llDatosCredito.setVisibility(View.VISIBLE);
            }
            else if (ivDown1.getVisibility() == View.GONE && ivUp1.getVisibility() == View.VISIBLE){
                ivDown1.setVisibility(View.VISIBLE);
                ivUp1.setVisibility(View.GONE);
                llDatosCredito.setVisibility(View.GONE);
            }
        }
    };

    private View.OnClickListener llPersonales_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (ivDown2.getVisibility() == View.VISIBLE && ivUp2.getVisibility() == View.GONE){
                ivDown2.setVisibility(View.GONE);
                ivUp2.setVisibility(View.VISIBLE);
                llDatosPersonales.setVisibility(View.VISIBLE);
                etNombreCli.requestFocus();
            }
            else if (ivDown2.getVisibility() == View.GONE && ivUp2.getVisibility() == View.VISIBLE){
                ivDown2.setVisibility(View.VISIBLE);
                ivUp2.setVisibility(View.GONE);
                llDatosPersonales.setVisibility(View.GONE);
            }
        }
    };

    private View.OnClickListener llConyuge_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (ivDown3.getVisibility() == View.VISIBLE && ivUp3.getVisibility() == View.GONE){
                ivDown3.setVisibility(View.GONE);
                ivUp3.setVisibility(View.VISIBLE);
                llDatosConyuge.setVisibility(View.VISIBLE);
                etNombreCony.requestFocus();
            }
            else if (ivDown3.getVisibility() == View.GONE && ivUp3.getVisibility() == View.VISIBLE){
                ivDown3.setVisibility(View.VISIBLE);
                ivUp3.setVisibility(View.GONE);
                llDatosConyuge.setVisibility(View.GONE);
            }
        }
    };

    private View.OnClickListener llEconomicos_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (ivDown4.getVisibility() == View.VISIBLE && ivUp4.getVisibility() == View.GONE){
                ivDown4.setVisibility(View.GONE);
                ivUp4.setVisibility(View.VISIBLE);
                llDatosEconomicos.setVisibility(View.VISIBLE);
                etPropiedadesEco.requestFocus();
            }
            else if (ivDown4.getVisibility() == View.GONE && ivUp4.getVisibility() == View.VISIBLE){
                ivDown4.setVisibility(View.VISIBLE);
                ivUp4.setVisibility(View.GONE);
                llDatosEconomicos.setVisibility(View.GONE);
            }
        }
    };

    private View.OnClickListener llNegocio_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (ivDown5.getVisibility() == View.VISIBLE && ivUp5.getVisibility() == View.GONE){
                ivDown5.setVisibility(View.GONE);
                ivUp5.setVisibility(View.VISIBLE);
                llDatosNegocio.setVisibility(View.VISIBLE);
                etNombreNeg.requestFocus();
            }
            else if (ivDown5.getVisibility() == View.GONE && ivUp5.getVisibility() == View.VISIBLE){
                ivDown5.setVisibility(View.VISIBLE);
                ivUp5.setVisibility(View.GONE);
                llDatosNegocio.setVisibility(View.GONE);
            }
        }
    };

    private View.OnClickListener llAval_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (ivDown6.getVisibility() == View.VISIBLE && ivUp6.getVisibility() == View.GONE){
                ivDown6.setVisibility(View.GONE);
                ivUp6.setVisibility(View.VISIBLE);
                llDatosAval.setVisibility(View.VISIBLE);
                etNombreAval.requestFocus();
            }
            else if (ivDown6.getVisibility() == View.GONE && ivUp6.getVisibility() == View.VISIBLE){
                ivDown6.setVisibility(View.VISIBLE);
                ivUp6.setVisibility(View.GONE);
                llDatosAval.setVisibility(View.GONE);
            }
        }
    };

    private View.OnClickListener llReferencia_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (ivDown7.getVisibility() == View.VISIBLE && ivUp7.getVisibility() == View.GONE){
                ivDown7.setVisibility(View.GONE);
                ivUp7.setVisibility(View.VISIBLE);
                llDatosReferencia.setVisibility(View.VISIBLE);
                etNombreRef.requestFocus();
            }
            else if (ivDown7.getVisibility() == View.GONE && ivUp7.getVisibility() == View.VISIBLE){
                ivDown7.setVisibility(View.VISIBLE);
                ivUp7.setVisibility(View.GONE);
                llDatosReferencia.setVisibility(View.GONE);

            }
        }
    };

    private View.OnClickListener llDocumentos_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (ivDown8.getVisibility() == View.VISIBLE && ivUp8.getVisibility() == View.GONE){
                ivDown8.setVisibility(View.GONE);
                ivUp8.setVisibility(View.VISIBLE);
                llDatosDocumentos.setVisibility(View.VISIBLE);
            }
            else if (ivDown8.getVisibility() == View.GONE && ivUp8.getVisibility() == View.VISIBLE){
                ivDown8.setVisibility(View.VISIBLE);
                ivUp8.setVisibility(View.GONE);
                llDatosDocumentos.setVisibility(View.GONE);

            }
        }
    };

    //==================================================================

    //Continuar
    private View.OnClickListener btnContinuar0_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //saveDatosCredito();
            ivDown2.setVisibility(View.GONE);
            ivUp2.setVisibility(View.VISIBLE);
            llDatosPersonales.setVisibility(View.VISIBLE);

            ivDown1.setVisibility(View.VISIBLE);
            ivUp1.setVisibility(View.GONE);
            llDatosCredito.setVisibility(View.GONE);

            etNombreCli.requestFocus();

        }
    };
    private View.OnClickListener btnContinuar1_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //saveDatosPersonales();
            if (tvEstadoCivilCli.getText().toString().trim().equals("CASADA(O)")||
                tvEstadoCivilCli.getText().toString().trim().equals("UNION LIBRE")) {
                etNombreCony.requestFocus();
                ivDown3.setVisibility(View.GONE);
                ivUp3.setVisibility(View.VISIBLE);
                llDatosConyuge.setVisibility(View.VISIBLE);
            }
            else{
                if (!etMontoPrestamo.getText().toString().trim().isEmpty() &&
                    Integer.parseInt(etMontoPrestamo.getText().toString().trim()) > 30000) {
                    etPropiedadesEco.requestFocus();
                    ivDown4.setVisibility(View.GONE);
                    ivUp4.setVisibility(View.VISIBLE);
                    llDatosEconomicos.setVisibility(View.VISIBLE);
                }
                else{
                    etNombreNeg.requestFocus();
                    ivDown5.setVisibility(View.GONE);
                    ivUp5.setVisibility(View.VISIBLE);
                    llDatosNegocio.setVisibility(View.VISIBLE);
                }
            }

            ivDown2.setVisibility(View.VISIBLE);
            ivUp2.setVisibility(View.GONE);
            llDatosPersonales.setVisibility(View.GONE);


        }
    };
    private View.OnClickListener btnContinuar2_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (!etMontoPrestamo.getText().toString().trim().isEmpty() &&
                Integer.parseInt(etMontoPrestamo.getText().toString().trim()) > 30000) {
                ivDown4.setVisibility(View.GONE);
                ivUp4.setVisibility(View.VISIBLE);
                llDatosEconomicos.setVisibility(View.VISIBLE);
                etPropiedadesEco.requestFocus();
            }
            else{
                ivDown5.setVisibility(View.GONE);
                ivUp5.setVisibility(View.VISIBLE);
                llDatosNegocio.setVisibility(View.VISIBLE);
                etNombreNeg.requestFocus();
            }
            ivDown3.setVisibility(View.VISIBLE);
            ivUp3.setVisibility(View.GONE);
            llDatosConyuge.setVisibility(View.GONE);
        }
    };
    private View.OnClickListener btnContinuar3_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ivDown5.setVisibility(View.GONE);
            ivUp5.setVisibility(View.VISIBLE);
            llDatosNegocio.setVisibility(View.VISIBLE);
            etNombreNeg.requestFocus();

            ivDown4.setVisibility(View.VISIBLE);
            ivUp4.setVisibility(View.GONE);
            llDatosEconomicos.setVisibility(View.GONE);
        }
    };
    private View.OnClickListener btnContinuar4_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ivDown6.setVisibility(View.GONE);
            ivUp6.setVisibility(View.VISIBLE);
            llDatosAval.setVisibility(View.VISIBLE);
            etNombreAval.requestFocus();

            ivDown5.setVisibility(View.VISIBLE);
            ivUp5.setVisibility(View.GONE);
            llDatosNegocio.setVisibility(View.GONE);
        }
    };
    private View.OnClickListener btnContinuar5_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ivDown7.setVisibility(View.GONE);
            ivUp7.setVisibility(View.VISIBLE);
            llDatosReferencia.setVisibility(View.VISIBLE);
            etNombreRef.requestFocus();

            ivDown6.setVisibility(View.VISIBLE);
            ivUp6.setVisibility(View.GONE);
            llDatosAval.setVisibility(View.GONE);
        }
    };
    private View.OnClickListener btnContinuar6_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ivDown8.setVisibility(View.GONE);
            ivUp8.setVisibility(View.VISIBLE);
            llDatosDocumentos.setVisibility(View.VISIBLE);

            ivDown7.setVisibility(View.VISIBLE);
            ivUp7.setVisibility(View.GONE);
            llDatosReferencia.setVisibility(View.GONE);
        }
    };

    //Regresar
    private View.OnClickListener btnRegresar1_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ivDown1.setVisibility(View.GONE);
            ivUp1.setVisibility(View.VISIBLE);
            llDatosCredito.setVisibility(View.VISIBLE);

            ivDown2.setVisibility(View.VISIBLE);
            ivUp2.setVisibility(View.GONE);
            llDatosPersonales.setVisibility(View.GONE);
        }
    };
    private View.OnClickListener btnRegresar2_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ivDown2.setVisibility(View.GONE);
            ivUp2.setVisibility(View.VISIBLE);
            llDatosPersonales.setVisibility(View.VISIBLE);
            etNombreCli.requestFocus();

            ivDown3.setVisibility(View.VISIBLE);
            ivUp3.setVisibility(View.GONE);
            llDatosConyuge.setVisibility(View.GONE);
        }
    };
    private View.OnClickListener btnRegresar3_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (tvEstadoCivilCli.getText().toString().trim().equals("CASADA(O)")||
                tvEstadoCivilCli.getText().toString().trim().equals("UNION LIBRE")) {
                ivDown3.setVisibility(View.GONE);
                ivUp3.setVisibility(View.VISIBLE);
                llDatosConyuge.setVisibility(View.VISIBLE);
                etNombreCony.requestFocus();
            }
            else{
                ivDown2.setVisibility(View.GONE);
                ivUp2.setVisibility(View.VISIBLE);
                llDatosPersonales.setVisibility(View.VISIBLE);
                etNombreCli.requestFocus();
            }

            ivDown4.setVisibility(View.VISIBLE);
            ivUp4.setVisibility(View.GONE);
            llDatosEconomicos.setVisibility(View.GONE);
        }
    };
    private View.OnClickListener btnRegresar4_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (!etMontoPrestamo.getText().toString().trim().isEmpty() &&
                Integer.parseInt(etMontoPrestamo.getText().toString().trim()) > 30000) {
                ivDown4.setVisibility(View.GONE);
                ivUp4.setVisibility(View.VISIBLE);
                llDatosEconomicos.setVisibility(View.VISIBLE);
                etPropiedadesEco.requestFocus();
            }
            else{
                if (tvEstadoCivilCli.getText().toString().trim().equals("CASADA(O)")||
                    tvEstadoCivilCli.getText().toString().trim().equals("UNION LIBRE")) {
                    ivDown3.setVisibility(View.GONE);
                    ivUp3.setVisibility(View.VISIBLE);
                    llDatosConyuge.setVisibility(View.VISIBLE);
                    etNombreCony.requestFocus();
                }
                else{
                    ivDown2.setVisibility(View.GONE);
                    ivUp2.setVisibility(View.VISIBLE);
                    llDatosPersonales.setVisibility(View.VISIBLE);
                    etNombreCli.requestFocus();
                }
            }
            ivDown5.setVisibility(View.VISIBLE);
            ivUp5.setVisibility(View.GONE);
            llDatosNegocio.setVisibility(View.GONE);
        }
    };
    private View.OnClickListener btnRegresar5_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ivDown5.setVisibility(View.GONE);
            ivUp5.setVisibility(View.VISIBLE);
            llDatosNegocio.setVisibility(View.VISIBLE);
            etNombreNeg.requestFocus();

            ivDown6.setVisibility(View.VISIBLE);
            ivUp6.setVisibility(View.GONE);
            llDatosAval.setVisibility(View.GONE);
        }
    };
    private View.OnClickListener btnRegresar6_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ivDown6.setVisibility(View.GONE);
            ivUp6.setVisibility(View.VISIBLE);
            llDatosAval.setVisibility(View.VISIBLE);
            etNombreAval.requestFocus();

            ivDown7.setVisibility(View.VISIBLE);
            ivUp7.setVisibility(View.GONE);
            llDatosReferencia.setVisibility(View.GONE);
        }
    };

    private View.OnClickListener btnRegresar7_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ivDown7.setVisibility(View.GONE);
            ivUp7.setVisibility(View.VISIBLE);
            llDatosReferencia.setVisibility(View.VISIBLE);
            etNombreRef.requestFocus();

            ivDown8.setVisibility(View.VISIBLE);
            ivUp8.setVisibility(View.GONE);
            llDatosDocumentos.setVisibility(View.GONE);
        }
    };


    private View.OnClickListener tvHoraVisita_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (is_edit) {
                dialog_time_picker timePicker = new dialog_time_picker();
                Bundle b = new Bundle();
                b.putInt(Constants.IDENTIFIER, 1);
                timePicker.setArguments(b);
                timePicker.show(getSupportFragmentManager(), NameFragments.DIALOGTIMEPICKER);
            }
        }
    };
    //========================================================================================================


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ContentValues cv = null;
        switch (requestCode){
            case Constants.REQUEST_CODE_ESTADO_NAC:
                if (resultCode == Constants.REQUEST_CODE_ESTADO_NAC){
                    if (data != null){
                        tvEstadoNacCli.setError(null);
                        tvEstadoNacCli.setText(((ModeloCatalogoGral)data.getSerializableExtra(Constants.ITEM)).getNombre());
                        HashMap<Integer, String> params = new HashMap<>();

                        params.put(0, etNombreCli.getText().toString());
                        params.put(1, etApPaternoCli.getText().toString());
                        params.put(2, etApMaternoCli.getText().toString());
                        params.put(3, tvFechaNacCli.getText().toString());

                        if (rgGeneroCli.getCheckedRadioButtonId()==R.id.rbHombre)
                            params.put(4, "Hombre");
                        else if (rgGeneroCli.getCheckedRadioButtonId()==R.id.rbMujer)
                            params.put(4, "Mujer");
                        else
                            params.put(4, "");

                        if (!tvEstadoNacCli.getText().toString().trim().isEmpty())
                            params.put(5, tvEstadoNacCli.getText().toString().trim());
                        else
                            params.put(5,"");
                        tvCurpCli.setText(Miscellaneous.GenerarCurp(params));
                        cv = new ContentValues();
                        cv.put("estado_nacimiento",tvEstadoNacCli.getText().toString().trim().toUpperCase());

                        if (ENVIROMENT)
                            db.update(DATOS_CLIENTE_IND, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                        else
                            db.update(DATOS_CLIENTE_IND_T, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                    }
                }
                break;
            case Constants.REQUEST_CODE_OCUPACION_CLIE:
                if (resultCode == Constants.REQUEST_CODE_OCUPACION_CLIE){
                    if (data != null){
                        tvOcupacionCli.setError(null);
                        tvOcupacionCli.setText(((ModeloCatalogoGral)data.getSerializableExtra(Constants.ITEM)).getNombre());
                        Cursor row = dBhelper.getRecords(Constants.SECTORES, " WHERE sector_id = " + (((ModeloCatalogoGral)data.getSerializableExtra(Constants.ITEM)).getExtra())+"","",null);
                        if (row.getCount() > 0){
                            row.moveToFirst();
                            tvActividadEcoCli.setText(row.getString(2));
                        }
                        row.close();
                        cv = new ContentValues();
                        cv.put("ocupacion",tvOcupacionCli.getText().toString().trim().toUpperCase());
                        cv.put("actividad_economica", tvActividadEcoCli.getText().toString().trim().toUpperCase());
                        if (ENVIROMENT)
                            db.update(DATOS_CLIENTE_IND, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                        else
                            db.update(DATOS_CLIENTE_IND_T, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                    }
                }
                break;
            case Constants.REQUEST_CODE_OCUPACION_CONY:
                if (resultCode == Constants.REQUEST_CODE_OCUPACION_CONY){
                    if (data != null){
                        tvOcupacionCony.setError(null);
                        tvOcupacionCony.setText(((ModeloCatalogoGral)data.getSerializableExtra(Constants.ITEM)).getNombre());
                        cv = new ContentValues();
                        cv.put("ocupacion", tvOcupacionCony.getText().toString().trim().toUpperCase());
                        if (ENVIROMENT)
                            db.update(DATOS_CONYUGE_IND, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                        else
                            db.update(DATOS_CONYUGE_IND_T, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                    }
                }
                break;
            case Constants.REQUEST_CODE_ACTIVIDAD_NEG:
                if (resultCode == Constants.REQUEST_CODE_ACTIVIDAD_NEG){
                    if (data != null){
                        tvActEconomicaNeg.setError(null);
                        tvActEconomicaNeg.setText(((ModeloCatalogoGral)data.getSerializableExtra(Constants.ITEM)).getNombre());
                        cv = new ContentValues();
                        cv.put("actividad_economica", tvActEconomicaNeg.getText().toString().trim().toUpperCase());
                        if (ENVIROMENT)
                            db.update(DATOS_NEGOCIO_IND, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                        else
                            db.update(DATOS_NEGOCIO_IND_T, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                    }
                }
                break;
            case Constants.REQUEST_CODE_ESTADO_NAC_AVAL:
                if (resultCode == Constants.REQUEST_CODE_ESTADO_NAC_AVAL){
                    if (data != null){
                        tvEstadoNacAval.setError(null);
                        tvEstadoNacAval.setText(((ModeloCatalogoGral)data.getSerializableExtra(Constants.ITEM)).getNombre());
                        HashMap<Integer, String> params = new HashMap<>();

                        params.put(0, etNombreAval.getText().toString());
                        params.put(1, etApPaternoAval.getText().toString());
                        params.put(2, etApMaternoAval.getText().toString());
                        params.put(3, tvFechaNacAval.getText().toString());

                        if (rgGeneroAval.getCheckedRadioButtonId()==R.id.rbHombre)
                            params.put(4, "Hombre");
                        else if (rgGeneroAval.getCheckedRadioButtonId()==R.id.rbMujer)
                            params.put(4, "Mujer");
                        else
                            params.put(4, "");

                        if (!tvEstadoNacAval.getText().toString().trim().isEmpty())
                            params.put(5, tvEstadoNacAval.getText().toString().trim());
                        else
                            params.put(5,"");
                        tvCurpAval.setText(Miscellaneous.GenerarCurp(params));

                        cv = new ContentValues();
                        cv.put("estado_nacimiento", tvEstadoNacAval.getText().toString().trim().toUpperCase());
                        if (ENVIROMENT)
                            db.update(DATOS_AVAL_IND, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                        else
                            db.update(DATOS_AVAL_IND_T, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                    }
                }
                break;
            case Constants.REQUEST_CODE_OCUPACION_AVAL:
                if (resultCode == Constants.REQUEST_CODE_OCUPACION_AVAL){
                    if (data != null){
                        tvOcupacionAval.setError(null);
                        tvOcupacionAval.setText(((ModeloCatalogoGral)data.getSerializableExtra(Constants.ITEM)).getNombre());
                        Cursor row = dBhelper.getRecords(Constants.SECTORES, " WHERE sector_id = " + (((ModeloCatalogoGral)data.getSerializableExtra(Constants.ITEM)).getExtra())+"","",null);
                        if (row.getCount() > 0){
                            row.moveToFirst();
                            tvActividadEcoAval.setError(null);
                            tvActividadEcoAval.setText(row.getString(2));
                            cv = new ContentValues();
                            cv.put("ocupacion", tvOcupacionAval.getText().toString().trim().toUpperCase());
                            cv.put("actividad_economica", tvActividadEcoAval.getText().toString().trim().toUpperCase());
                            if (ENVIROMENT)
                                db.update(DATOS_AVAL_IND, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                            else
                                db.update(DATOS_AVAL_IND_T, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                        }
                        row.close();
                    }
                }
                break;
            case Constants.REQUEST_CODE_COLONIA_CLIE:
                if (resultCode == Constants.REQUEST_CODE_COLONIA_CLIE){
                    if (data != null){
                        tvColoniaCli.setError(null);
                        tvColoniaCli.setText(((ModeloCatalogoGral)data.getSerializableExtra(Constants.ITEM)).getNombre());
                        cv = new ContentValues();
                        cv.put("colonia",tvColoniaCli.getText().toString().trim().toUpperCase());
                        if (ENVIROMENT)
                            db.update(DATOS_CLIENTE_IND, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                        else
                            db.update(DATOS_CLIENTE_IND_T, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                    }
                }
                break;
            case Constants.REQUEST_CODE_COLONIA_AVAL:
                if (resultCode == Constants.REQUEST_CODE_COLONIA_AVAL){
                    if (data != null){
                        tvColoniaAval.setText(((ModeloCatalogoGral)data.getSerializableExtra(Constants.ITEM)).getNombre());
                        cv = new ContentValues();
                        cv.put("colonia",tvColoniaAval.getText().toString().trim().toUpperCase());
                        if (ENVIROMENT)
                            db.update(DATOS_AVAL_IND, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                        else
                            db.update(DATOS_AVAL_IND_T, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                    }
                }
                break;
            case Constants.REQUEST_CODE_COLONIA_NEG:
                if (resultCode == Constants.REQUEST_CODE_COLONIA_NEG){
                    if (data != null){
                        tvColoniaNeg.setError(null);
                        tvColoniaNeg.setText(((ModeloCatalogoGral)data.getSerializableExtra(Constants.ITEM)).getNombre());
                        cv = new ContentValues();
                        cv.put("colonia",tvColoniaNeg.getText().toString().trim().toUpperCase());
                        if (ENVIROMENT)
                            db.update(DATOS_NEGOCIO_IND, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                        else
                            db.update(DATOS_NEGOCIO_IND_T, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                    }
                }
                break;
            case Constants.REQUEST_CODE_COLONIA_REF:
                if (resultCode == Constants.REQUEST_CODE_COLONIA_REF){
                    if (data != null){
                        tvColoniaRef.setError(null);
                        tvColoniaRef.setText(((ModeloCatalogoGral)data.getSerializableExtra(Constants.ITEM)).getNombre());
                        cv = new ContentValues();
                        cv.put("colonia",tvColoniaRef.getText().toString().trim().toUpperCase());
                        if (ENVIROMENT)
                            db.update(DATOS_REFERENCIA_IND, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                        else
                            db.update(DATOS_REFERENCIA_IND_T, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                    }
                }
                break;
            case Constants.REQUEST_CODE_CAMARA_FACHADA_CLI:
                if (resultCode == Activity.RESULT_OK){
                    if (data != null){
                        tvFachadaCli.setError(null);
                        ibFotoFachCli.setVisibility(View.GONE);
                        ivFotoFachCli.setVisibility(View.VISIBLE);
                        byteFotoFachCli = data.getByteArrayExtra(Constants.PICTURE);
                        Glide.with(ctx).load(byteFotoFachCli).centerCrop().into(ivFotoFachCli);
                        cv = new ContentValues();
                        try {
                            cv.put("foto_fachada", Miscellaneous.save(byteFotoFachCli, 1));
                            if (ENVIROMENT)
                                db.update(DATOS_CLIENTE_IND, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                            else
                                db.update(DATOS_CLIENTE_IND_T, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                break;
            case Constants.REQUEST_CODE_CAMARA_FACHADA_NEG:
                if (resultCode == Activity.RESULT_OK){
                    if (data != null){
                        tvFachadaNeg.setError(null);
                        ibFotoFachNeg.setVisibility(View.GONE);
                        ivFotoFachNeg.setVisibility(View.VISIBLE);
                        byteFotoFachNeg = data.getByteArrayExtra(Constants.PICTURE);
                        Glide.with(ctx).load(byteFotoFachNeg).centerCrop().into(ivFotoFachNeg);
                        cv = new ContentValues();
                        try {
                            cv.put("foto_fachada", Miscellaneous.save(byteFotoFachNeg, 1));
                            if (ENVIROMENT)
                                db.update(DATOS_NEGOCIO_IND, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                            else
                                db.update(DATOS_NEGOCIO_IND_T, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                break;
            case Constants.REQUEST_CODE_CAMARA_FACHADA_AVAL:
                if (resultCode == Activity.RESULT_OK){
                    if (data != null){
                        tvFachadaAval.setError(null);
                        ibFotoFachAval.setVisibility(View.GONE);
                        ivFotoFachAval.setVisibility(View.VISIBLE);
                        byteFotoFachAval = data.getByteArrayExtra(Constants.PICTURE);
                        Glide.with(ctx).load(byteFotoFachAval).centerCrop().into(ivFotoFachAval);
                        cv = new ContentValues();
                        try {
                            cv.put("foto_fachada", Miscellaneous.save(byteFotoFachAval, 1));
                            if (ENVIROMENT)
                                db.update(DATOS_AVAL_IND, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                            else
                                db.update(DATOS_AVAL_IND_T, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                break;
            case Constants.REQUEST_CODE_FIRMA_AVAL:
                if (resultCode == Activity.RESULT_OK){
                    if (data != null){
                        tvFirmaAval.setError(null);
                        ibFirmaAval.setVisibility(View.GONE);
                        ivFirmaAval.setVisibility(View.VISIBLE);
                        Glide.with(ctx)
                                .load(data.getByteArrayExtra(Constants.FIRMA_IMAGE))
                                .into(ivFirmaAval);
                        byteFirmaAval = data.getByteArrayExtra(Constants.FIRMA_IMAGE);
                        cv = new ContentValues();
                        try {
                            cv.put("firma", Miscellaneous.save(byteFirmaAval, 3));
                            if (ENVIROMENT)
                                db.update(DATOS_AVAL_IND, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                            else
                                db.update(DATOS_AVAL_IND_T, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                break;
            case Constants.REQUEST_CODE_FIRMA_CLI:
                if (resultCode == Activity.RESULT_OK){
                    if (data != null){
                        tvFirmaCli.setError(null);
                        ibFirmaCli.setVisibility(View.GONE);
                        ivFirmaCli.setVisibility(View.VISIBLE);
                        Glide.with(ctx)
                                .load(data.getByteArrayExtra(Constants.FIRMA_IMAGE))
                                .into(ivFirmaCli);
                        byteFirmaCli = data.getByteArrayExtra(Constants.FIRMA_IMAGE);
                        cv = new ContentValues();
                        try {
                            cv.put("firma", Miscellaneous.save(byteFirmaCli, 3));
                            if (ENVIROMENT)
                                db.update(DATOS_CLIENTE_IND, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                            else
                                db.update(DATOS_CLIENTE_IND_T, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                break;
            case Constants.REQUEST_CODE_FIRMA_ASESOR:
                if (resultCode == Activity.RESULT_OK){
                    if (data != null){
                        tvFirmaAsesor.setError(null);
                        ibFirmaAsesor.setVisibility(View.GONE);
                        ivFirmaAsesor.setVisibility(View.VISIBLE);
                        Glide.with(ctx)
                                .load(data.getByteArrayExtra(Constants.FIRMA_IMAGE))
                                .into(ivFirmaAsesor);
                        byteFirmaAsesor = data.getByteArrayExtra(Constants.FIRMA_IMAGE);
                        cv = new ContentValues();
                        try {
                            cv.put("nombre", Miscellaneous.save(byteFirmaAsesor, 3));
                            cv.put("estatus", "1");
                            if (ENVIROMENT)
                                db.update(DOCUMENTOS, cv, "id_cliente = ? AND tipo_documento = ?", new String[]{id_cliente, "6"});
                            else
                                db.update(DOCUMENTOS_T, cv, "id_cliente = ? AND tipo_documento = ?", new String[]{id_cliente, "6"});
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                break;
            case Constants.REQUEST_CODE_FOTO_INE_FRONTAL:
                if (resultCode == RESULT_SCAN_SUPPRESSED){
                    if (data != null){
                        tvIneFrontal.setError(null);
                        Bitmap cardIneFrontal = CardIOActivity.getCapturedCardImage(data);
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        cardIneFrontal.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                        byteIneFrontal =  baos.toByteArray();
                        ibIneFrontal.setVisibility(View.GONE);
                        ivIneFrontal.setVisibility(View.VISIBLE);
                        Glide.with(ctx).load(byteIneFrontal).centerCrop().into(ivIneFrontal);
                        cv = new ContentValues();
                        try {
                            cv.put("nombre", Miscellaneous.save(byteIneFrontal, 4));
                            cv.put("estatus", "1");
                            if (ENVIROMENT)
                                db.update(DOCUMENTOS, cv, "id_cliente = ? AND tipo_documento = ?", new String[]{id_cliente, "1"});
                            else
                                db.update(DOCUMENTOS_T, cv, "id_cliente = ? AND tipo_documento = ?", new String[]{id_cliente, "1"});
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                break;
            case Constants.REQUEST_CODE_FOTO_INE_REVERSO:
                if (resultCode == RESULT_SCAN_SUPPRESSED){
                    if (data != null){
                        tvIneReverso.setError(null);
                        Bitmap cardIneReverso = CardIOActivity.getCapturedCardImage(data);
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        cardIneReverso.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                        byteIneReverso =  baos.toByteArray();
                        ibIneReverso.setVisibility(View.GONE);
                        ivIneReverso.setVisibility(View.VISIBLE);
                        Glide.with(ctx).load(byteIneReverso).centerCrop().into(ivIneReverso);
                        cv = new ContentValues();
                        try {
                            cv.put("nombre", Miscellaneous.save(byteIneReverso, 4));
                            cv.put("estatus", "1");
                            if (ENVIROMENT)
                                db.update(DOCUMENTOS, cv, "id_cliente = ? AND tipo_documento = ?", new String[]{id_cliente, "2"});
                            else
                                db.update(DOCUMENTOS_T, cv, "id_cliente = ? AND tipo_documento = ?", new String[]{id_cliente, "2"});
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                break;
            case Constants.REQUEST_CODE_FOTO_CURP:
                if (resultCode == Activity.RESULT_OK){
                    if (data != null){
                        tvCurp.setError(null);
                        ibCurp.setVisibility(View.GONE);
                        ivCurp.setVisibility(View.VISIBLE);
                        byteCurp = data.getByteArrayExtra(Constants.PICTURE);
                        Glide.with(ctx).load(byteCurp).centerCrop().into(ivCurp);
                        cv = new ContentValues();
                        try {
                            cv.put("nombre", Miscellaneous.save(byteCurp, 4));
                            cv.put("estatus", "1");
                            if (ENVIROMENT)
                                db.update(DOCUMENTOS, cv, "id_cliente = ? AND tipo_documento = ?", new String[]{id_cliente, "3"});
                            else
                                db.update(DOCUMENTOS_T, cv, "id_cliente = ? AND tipo_documento = ?", new String[]{id_cliente, "3"});
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                break;
            case Constants.REQUEST_CODE_FOTO_COMPROBATE:
                if (resultCode == Activity.RESULT_OK){
                    if (data != null){
                        tvComprobante.setError(null);
                        ibComprobante.setVisibility(View.GONE);
                        ivComprobante.setVisibility(View.VISIBLE);
                        byteComprobante = data.getByteArrayExtra(Constants.PICTURE);
                        Glide.with(ctx).load(byteComprobante).centerCrop().into(ivComprobante);
                        cv = new ContentValues();
                        try {
                            cv.put("nombre", Miscellaneous.save(byteComprobante, 4));
                            cv.put("estatus", "1");
                            if (ENVIROMENT)
                                db.update(DOCUMENTOS, cv, "id_cliente = ? AND tipo_documento = ?", new String[]{id_cliente, "4"});
                            else
                                db.update(DOCUMENTOS_T, cv, "id_cliente = ? AND tipo_documento = ?", new String[]{id_cliente, "4"});
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                break;
        }
    }

    public void setDate (String date, String campo){
        try {
            Date strDate = sdf.parse(date);
            Calendar cal = Calendar.getInstance();
            cal.setTime(strDate);
            ContentValues cv;
            switch (campo){
                case "desembolso":
                    tvFechaDesembolso.setError(null);
                    tvFechaDesembolso.setText(sdf.format(cal.getTime()));
                    String[] fechaDes = tvFechaDesembolso.getText().toString().split("-");
                    Calendar c = Calendar.getInstance();

                    c.set(Integer.valueOf(fechaDes[0]), (Integer.valueOf(fechaDes[1]) - 1), Integer.valueOf(fechaDes[2]));
                    int nD=c.get(Calendar.DAY_OF_WEEK);
                    String diaDesembolso = "";
                    switch (nD){
                        case 1: diaDesembolso = "DOMINGO";
                            break;
                        case 2: diaDesembolso = "LUNES";
                            break;
                        case 3: diaDesembolso = "MARTES";
                            break;
                        case 4: diaDesembolso = "MIÉRCOLES";
                            break;
                        case 5: diaDesembolso = "JUEVES";
                            break;
                        case 6: diaDesembolso = "VIERNES";
                            break;
                        case 7: diaDesembolso = "SÁBADO";
                            break;
                    }
                    tvDiaDesembolso.setText(diaDesembolso);

                    cv = new ContentValues();
                    cv.put("fecha_desembolso",tvFechaDesembolso.getText().toString().trim());
                    cv.put("dia_desembolso",tvDiaDesembolso.getText().toString().trim());
                    if (ENVIROMENT)
                        db.update(DATOS_CREDITO_IND, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                    else
                        db.update(DATOS_CREDITO_IND_T, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                    break;
                case "fechaNacCli":
                    tvFechaNacCli.setError(null);
                    tvFechaNacCli.setText(date);
                    tvEdadCli.setText(Miscellaneous.GetEdad(sdf.format(cal.getTime())));
                    HashMap<Integer, String> params = new HashMap<>();

                    params.put(0, etNombreCli.getText().toString());
                    params.put(1, etApPaternoCli.getText().toString());
                    params.put(2, etApMaternoCli.getText().toString());
                    params.put(3, tvFechaNacCli.getText().toString());

                    if (rgGeneroCli.getCheckedRadioButtonId()==R.id.rbHombre)
                        params.put(4, "Hombre");
                    else if (rgGeneroCli.getCheckedRadioButtonId()==R.id.rbMujer)
                        params.put(4, "Mujer");
                    else
                        params.put(4, "");

                    if (!tvEstadoNacCli.getText().toString().trim().isEmpty())
                        params.put(5, tvEstadoNacCli.getText().toString().trim());
                    else
                        params.put(5,"");
                    cv = new ContentValues();
                    cv.put("fecha_nacimiento",tvFechaNacCli.getText().toString().trim());
                    cv.put("edad",tvEdadCli.getText().toString().trim());
                    if (ENVIROMENT)
                        db.update(DATOS_CLIENTE_IND, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                    else
                        db.update(DATOS_CLIENTE_IND_T, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                    tvCurpCli.setText(Miscellaneous.GenerarCurp(params));
                    break;
                case "fechaNacAval":
                    tvFechaNacAval.setError(null);
                    tvFechaNacAval.setText(date);
                    tvEdadAval.setText(Miscellaneous.GetEdad(sdf.format(cal.getTime())));
                    HashMap<Integer, String> paramsAval = new HashMap<>();

                    paramsAval.put(0, etNombreAval.getText().toString());
                    paramsAval.put(1, etApPaternoAval.getText().toString());
                    paramsAval.put(2, etApMaternoAval.getText().toString());
                    paramsAval.put(3, tvFechaNacAval.getText().toString());

                    if (rgGeneroAval.getCheckedRadioButtonId()==R.id.rbHombre)
                        paramsAval.put(4, "Hombre");
                    else if (rgGeneroAval.getCheckedRadioButtonId()==R.id.rbMujer)
                        paramsAval.put(4, "Mujer");
                    else
                        paramsAval.put(4, "");

                    if (!tvEstadoNacAval.getText().toString().trim().isEmpty())
                        paramsAval.put(5, tvEstadoNacAval.getText().toString().trim());
                    else
                        paramsAval.put(5,"");

                    cv = new ContentValues();
                    cv.put("fecha_nacimiento",tvFechaNacAval.getText().toString().trim());
                    cv.put("edad",tvEdadAval.getText().toString().trim());
                    if (ENVIROMENT)
                        db.update(DATOS_AVAL_IND, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                    else
                        db.update(DATOS_AVAL_IND_T, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                    tvCurpAval.setText(Miscellaneous.GenerarCurp(paramsAval));
                    break;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void setTimer(String timer, String campo){
        ContentValues cv;
        switch (campo){
            case "HoraVisita":
                tvHoraVisita.setError(null);
                tvHoraVisita.setText(timer);
                cv = new ContentValues();
                cv.put("hora_visita",tvHoraVisita.getText().toString().trim());
                if (ENVIROMENT)
                    db.update(DATOS_CREDITO_IND, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                else
                    db.update(DATOS_CREDITO_IND_T, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                break;
            case "HoraVisitaAval":
                tvHoraLocAval.setError(null);
                tvHoraLocAval.setText(timer);
                cv = new ContentValues();
                cv.put("horario_localizacion",timer);
                if (ENVIROMENT)
                    db.update(DATOS_AVAL_IND, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                else
                    db.update(DATOS_AVAL_IND_T, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                break;
        }
    }

    private void CapacidadPagoNeg (){
        double ing_mensual = (etIngMenNeg.getText().toString().trim().isEmpty())?0:Integer.parseInt(etIngMenNeg.getText().toString().trim());
        double ing_otros = (etOtrosIngNeg.getText().toString().trim().isEmpty())?0:Integer.parseInt(etOtrosIngNeg.getText().toString().trim());

        double gas_semanal = (etGastosSemNeg.getText().toString().trim().isEmpty())?0:Integer.parseInt(etGastosSemNeg.getText().toString().trim());
        double gas_agua = (etGastosAguaNeg.getText().toString().trim().isEmpty())?0:Integer.parseInt(etGastosAguaNeg.getText().toString().trim());
        double gas_luz = (etGastosLuzNeg.getText().toString().trim().isEmpty())?0:Integer.parseInt(etGastosLuzNeg.getText().toString().trim());
        double gas_telefono = (etGastosTelNeg.getText().toString().trim().isEmpty())?0:Integer.parseInt(etGastosTelNeg.getText().toString().trim());
        double gas_renta = (etGastosRentaNeg.getText().toString().trim().isEmpty())?0:Integer.parseInt(etGastosRentaNeg.getText().toString().trim());
        double gas_otro = (etGastosOtrosNeg.getText().toString().trim().isEmpty())?0:Integer.parseInt(etGastosOtrosNeg.getText().toString().trim());

        double ingreso = ing_mensual + ing_otros;
        double gastos = (gas_semanal * 4) + gas_agua + gas_luz + gas_telefono + gas_renta + gas_otro;

        tvCapacidadPagoNeg.setText(String.valueOf(ingreso - gastos));
        ContentValues cv = new ContentValues();
        cv.put("capacidad_pago", tvCapacidadPagoNeg.getText().toString().trim());
        if (ENVIROMENT)
            db.update(DATOS_NEGOCIO_IND, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
        else
            db.update(DATOS_NEGOCIO_IND_T, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
    }

    private void showDiasSemana(){
        selectedItemsDias = new ArrayList<>();
        new AlertDialog.Builder(SolicitudCreditoInd.this)
                .setTitle("Días de Venta")
                .setMultiChoiceItems(R.array.dias_semana, null, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        if (isChecked) {
                            selectedItemsDias.add(which);
                        } else if (selectedItemsDias.contains(which)) {
                            selectedItemsDias.remove(Integer.valueOf(which));
                        }
                    }
                })
                .setPositiveButton(R.string.accept, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        String dias = "";
                        Collections.sort(selectedItemsDias);
                        for (int i = 0; i < selectedItemsDias.size(); i++){
                            if(i == 0)
                                dias += _dias_semana[selectedItemsDias.get(i)];
                            else
                                dias += ", "+_dias_semana[selectedItemsDias.get(i)];
                        }
                        tvDiasVentaNeg.setError(null);
                        tvDiasVentaNeg.setText(dias);
                        ContentValues cv = new ContentValues();
                        cv.put("dias_venta", tvDiasVentaNeg.getText().toString().trim().toUpperCase());
                        if (ENVIROMENT)
                            db.update(DATOS_NEGOCIO_IND, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                        else
                            db.update(DATOS_NEGOCIO_IND_T, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                    }
                }).show();
    }

    private boolean saveDatosCredito(){
        boolean save_credito = false;
        if (!validatorTV.validate(tvPlazo, new String[]{validatorTV.REQUIRED}) &&
        !validatorTV.validate(tvFrecuencia, new String[]{validatorTV.REQUIRED}) &&
        !validatorTV.validate(tvFechaDesembolso, new String[]{validatorTV.REQUIRED}) &&
        !validatorTV.validate(tvDiaDesembolso, new String[]{validatorTV.REQUIRED}) &&
        !validatorTV.validate(tvHoraVisita, new String[]{validatorTV.REQUIRED}) &&
        !validator.validate(etMontoPrestamo, new String[]{validator.REQUIRED, validator.ONLY_NUMBER, validator.CREDITO}) &&
        !validatorTV.validate(tvDestino, new String[]{validatorTV.REQUIRED})){
            ivError1.setVisibility(View.GONE);
            ContentValues cv = new ContentValues();
            cv.put("plazo",tvPlazo.getText().toString().trim().toUpperCase());
            cv.put("periodicidad",tvFrecuencia.getText().toString().trim().toUpperCase());
            cv.put("fecha_desembolso",tvFechaDesembolso.getText().toString().trim());
            cv.put("dia_desembolso",tvDiaDesembolso.getText().toString().trim().toUpperCase());
            cv.put("hora_visita",tvHoraVisita.getText().toString().trim());
            cv.put("monto_prestamo",etMontoPrestamo.getText().toString().trim());
            cv.put("destino",tvDestino.getText().toString().trim().toUpperCase());
            cv.put("estatus_completado",0);
            if (ENVIROMENT)
                db.update(DATOS_CREDITO_IND, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
            else
                db.update(DATOS_CREDITO_IND_T, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
            save_credito = true;
        }
        else
            ivError1.setVisibility(View.VISIBLE);

        return save_credito;
    }
    private boolean saveDatosPersonales(){
        boolean save_cliente = false;
        ContentValues cv = new ContentValues();
        if (!validator.validate(etNombreCli, new String[]{validator.REQUIRED, validator.ONLY_TEXT}) &&
        !validator.validate(etApPaternoCli, new String[]{validator.REQUIRED, validator.ONLY_TEXT}) &&
        !validator.validate(etApMaternoCli, new String[]{validator.ONLY_TEXT}) &&
        !validatorTV.validate(tvFechaNacCli, new String[]{validatorTV.REQUIRED}) &&
        !validatorTV.validate(tvEdadCli, new String[]{validatorTV.REQUIRED, validatorTV.ONLY_NUMBER})){
            if (rgGeneroCli.getCheckedRadioButtonId() == R.id.rbHombre ||
                rgGeneroCli.getCheckedRadioButtonId() == R.id.rbMujer){
                tvGeneroCli.setError(null);
                if (!validatorTV.validate(tvEstadoNacCli, new String[]{validatorTV.REQUIRED}) &&
                (!validatorTV.validate(tvRfcCli, new String[]{validatorTV.REQUIRED}) &&
                 !tvRfcCli.getText().toString().trim().equals("Rfc no válida")) &&
                !validatorTV.validate(tvCurpCli, new String[]{validatorTV.REQUIRED, validatorTV.CURP}) &&
                !validator.validate(etCurpIdCli, new String[]{validator.REQUIRED, validator.CURP_ID})){
                    if (Miscellaneous.CurpValidador(tvCurpCli.getText().toString().trim().toUpperCase()+etCurpIdCli.getText().toString().trim())){
                        if(!validatorTV.validate(tvOcupacionCli, new String[]{validatorTV.REQUIRED}) &&
                        !validatorTV.validate(tvActividadEcoCli, new String[]{validatorTV.REQUIRED}) &&
                        !validatorTV.validate(tvTipoIdentificacion, new String[]{validatorTV.REQUIRED}) &&
                        !validator.validate(etNumIdentifCli, new String[]{validator.REQUIRED, validator.ALFANUMERICO}) &&
                        !validatorTV.validate(tvEstudiosCli, new String[]{validatorTV.REQUIRED}) &&
                        !validatorTV.validate(tvEstadoCivilCli, new String[]{validatorTV.REQUIRED})){
                            boolean flag_est_civil = false;
                            if (tvEstadoCivilCli.getText().toString().trim().equals("CASADA(O)")){
                                if (rgBienes.getCheckedRadioButtonId() == R.id.rbMancomunados ||
                                        rgBienes.getCheckedRadioButtonId() == R.id.rbSeparados) {
                                    tvBienes.setError(null);
                                    switch (rgBienes.getCheckedRadioButtonId()){
                                        case R.id.rbMancomunados:
                                            cv.put("bienes", 1);
                                            break;
                                        case R.id.rbSeparados:
                                            cv.put("bienes", 2);
                                            break;
                                    }
                                    flag_est_civil = true;
                                }
                            }
                            else {
                                flag_est_civil = true;
                                cv.put("bienes", 0);
                            }

                            if (flag_est_civil){
                                if (!validatorTV.validate(tvTipoCasaCli, new String[]{validatorTV.REQUIRED})){
                                    boolean flag_tipo_casa = false;
                                    cv.put("tipo_vivienda", tvTipoCasaCli.getText().toString().trim().toUpperCase());
                                    switch (tvTipoCasaCli.getText().toString().trim().toUpperCase()){
                                        case "CASA FAMILIAR":
                                            if (!validatorTV.validate(tvCasaFamiliar, new String[]{validatorTV.REQUIRED})) {
                                                flag_tipo_casa = true;
                                                cv.put("parentesco", tvTipoCasaCli.getText().toString().trim().toUpperCase());
                                                cv.put("otro_tipo_vivienda", "");
                                            }
                                            else
                                                ivError2.setVisibility(View.VISIBLE);

                                            break;
                                        case "OTRO":
                                            if (!validator.validate(etOTroTipoCli, new String[]{validator.REQUIRED})) {
                                                flag_tipo_casa = true;
                                                cv.put("parentesco", "");
                                                cv.put("otro_tipo_vivienda", etOTroTipoCli.getText().toString().trim().toUpperCase());
                                            }
                                            else
                                                ivError2.setVisibility(View.VISIBLE);
                                            break;
                                        default:
                                            flag_tipo_casa = true;
                                            cv.put("parentesco", "");
                                            cv.put("otro_tipo_vivienda", "");
                                            break;
                                    }
                                    if (flag_tipo_casa){
                                        if (latLngUbiCli != null){
                                            tvMapaCli.setError(null);
                                            if (!validator.validate(etCalleCli, new String[]{validator.REQUIRED}) &&
                                                    !validator.validate(etNoExtCli, new String[]{validator.REQUIRED}) &&
                                                    !validator.validate(etCpCli, new String[]{validator.REQUIRED, validator.ONLY_NUMBER, validator.CP}) &&
                                                    !Miscellaneous.ValidTextView(tvColoniaCli) &&
                                                    !Miscellaneous.ValidTextView(tvMunicipioCli) &&
                                                    !Miscellaneous.ValidTextView(tvEstadoCli) &&
                                                    !validator.validate(etTelCasaCli, new String[]{validator.PHONE}) &&
                                                    !validator.validate(etCelularCli, new String[]{validator.REQUIRED, validator.PHONE}) &&
                                                    !validator.validate(etTelMensCli, new String[]{validator.PHONE}) &&
                                                    !validator.validate(etTelTrabajoCli, new String[]{validator.PHONE}) &&
                                                    !validator.validate(etTiempoSitio, new String[]{validator.REQUIRED, validator.ONLY_NUMBER, validator.YEARS}) &&
                                                    !validatorTV.validate(tvDependientes, new String[]{validatorTV.REQUIRED}) &&
                                                    !validatorTV.validate(tvEnteroNosotros, new String[]{validatorTV.REQUIRED}) &&
                                                    !validator.validate(etEmail, new String[]{validator.EMAIL})){
                                                if (byteFotoFachCli != null){
                                                    tvFachadaCli.setError(null);
                                                    if (!validator.validate(etReferenciCli, new String[]{validator.REQUIRED})){
                                                        if (byteFirmaCli != null){
                                                            tvFirmaCli.setError(null);
                                                            ivError2.setVisibility(View.GONE);
                                                            cv.put("nombre", etNombreCli.getText().toString().trim().toUpperCase());
                                                            cv.put("paterno", etApPaternoCli.getText().toString().trim().toUpperCase());
                                                            cv.put("materno", etApMaternoCli.getText().toString().trim().toUpperCase());
                                                            cv.put("fecha_nacimiento", tvFechaNacCli.getText().toString().trim());
                                                            cv.put("edad", tvEdadCli.getText().toString().trim());
                                                            switch (rgGeneroCli.getCheckedRadioButtonId()){
                                                                case R.id.rbHombre:
                                                                    cv.put("genero", 0);
                                                                    break;
                                                                case R.id.rbMujer:
                                                                    cv.put("genero", 1);
                                                                    break;
                                                            }
                                                            cv.put("estado_nacimiento", tvEstadoNacCli.getText().toString().trim().toUpperCase());
                                                            cv.put("rfc", tvRfcCli.getText().toString().trim().toUpperCase());
                                                            cv.put("curp", tvCurpCli.getText().toString().trim().toUpperCase());
                                                            cv.put("curp_digito_veri", etCurpIdCli.getText().toString().trim());
                                                            cv.put("ocupacion", tvOcupacionCli.getText().toString().trim().toUpperCase());
                                                            cv.put("actividad_economica", tvActividadEcoCli.getText().toString().trim().toUpperCase());
                                                            cv.put("tipo_identificacion", tvTipoIdentificacion.getText().toString().trim().toUpperCase());
                                                            cv.put("no_identificacion", etNumIdentifCli.getText().toString().trim());
                                                            cv.put("nivel_estudio", tvEstudiosCli.getText().toString().trim().toUpperCase());
                                                            cv.put("estado_civil", tvEstadoCivilCli.getText().toString().trim().toUpperCase());
                                                            cv.put("latitud", String.valueOf(latLngUbiCli.latitude));
                                                            cv.put("longitud", String.valueOf(latLngUbiCli.longitude));
                                                            cv.put("calle", etCalleCli.getText().toString().trim().toUpperCase());
                                                            cv.put("no_exterior", etNoExtCli.getText().toString().trim().toUpperCase());
                                                            cv.put("no_interior", etNoIntCli.getText().toString().trim().toUpperCase());
                                                            cv.put("manzana", etManzanaCli.getText().toString().trim().toUpperCase());
                                                            cv.put("lote", etLoteCli.getText().toString().trim().toUpperCase());
                                                            cv.put("cp", etCpCli.getText().toString().trim().toUpperCase());
                                                            cv.put("colonia", tvColoniaCli.getText().toString().trim().toUpperCase());
                                                            cv.put("tel_casa", etTelCasaCli.getText().toString().trim().toUpperCase());
                                                            cv.put("tel_celular", etCelularCli.getText().toString().trim().toUpperCase());
                                                            cv.put("tel_mensajes", etTelMensCli.getText().toString().trim().toUpperCase());
                                                            cv.put("tel_trabajo", etTelTrabajoCli.getText().toString().trim().toUpperCase());
                                                            cv.put("tiempo_vivir_sitio", Integer.parseInt(etTiempoSitio.getText().toString().trim()));
                                                            cv.put("dependientes", tvDependientes.getText().toString().trim());
                                                            cv.put("medio_contacto", tvEnteroNosotros.getText().toString().trim().toUpperCase());
                                                            cv.put("email", etEmail.getText().toString().trim());
                                                            cv.put("ref_domiciliaria", etReferenciCli.getText().toString().trim().toUpperCase());
                                                            cv.put("estatus_completado", 1);
                                                            if (ENVIROMENT)
                                                                db.update(DATOS_CLIENTE_IND, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                                                            else
                                                                db.update(DATOS_CLIENTE_IND_T, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});

                                                            save_cliente = true;
                                                        }
                                                        else{
                                                            ivError2.setVisibility(View.VISIBLE);
                                                            tvFirmaCli.setError("");
                                                        }
                                                    }
                                                    else
                                                        ivError2.setVisibility(View.VISIBLE);
                                                }
                                                else{
                                                    ivError2.setVisibility(View.VISIBLE);
                                                    tvFachadaCli.setError("");
                                                }
                                            }
                                            else
                                                ivError2.setVisibility(View.VISIBLE);
                                        }
                                        else{
                                            ivError2.setVisibility(View.VISIBLE);
                                            tvMapaCli.setError("");
                                        }
                                    }
                                }
                                else
                                    ivError2.setVisibility(View.VISIBLE);
                            }
                            else {
                                ivError2.setVisibility(View.VISIBLE);
                                tvBienes.setError("");
                            }
                        }
                        else
                            ivError2.setVisibility(View.VISIBLE);
                    }
                    else{
                        ivError2.setVisibility(View.VISIBLE);
                        etCurpIdCli.setError("Curp no válida");
                    }
                }
                else
                    ivError2.setVisibility(View.VISIBLE);
            }
            else{
                ivError2.setVisibility(View.VISIBLE);
                tvGeneroCli.setError("");
            }
        }
        else
            ivError2.setVisibility(View.VISIBLE);

        return save_cliente;
    }
    private boolean saveConyuge(){
        boolean save_conyuge = false;
        if (!validator.validate(etNombreCony, new String[]{validator.REQUIRED, validator.ONLY_TEXT}) &&
        !validator.validate(etApPaternoCony, new String[]{validator.REQUIRED, validator.ONLY_TEXT}) &&
        !validator.validate(etApMaternoCony, new String[]{validator.GENERAL}) &&
        !validatorTV.validate(tvOcupacionCony, new String[]{validatorTV.REQUIRED}) &&
        !validator.validate(etCelularCony, new String[]{validator.REQUIRED, validator.PHONE})){
            Log.e("Conyuge", "pasa");
            ivError3.setVisibility(View.GONE);
            ContentValues cv = new ContentValues();
            cv.put("nombre", etNombreCony.getText().toString().trim().toUpperCase());
            cv.put("paterno", etApPaternoCony.getText().toString().trim().toUpperCase());
            cv.put("materno", etApMaternoCony.getText().toString().trim().toUpperCase());
            cv.put("ocupacion", tvOcupacionCony.getText().toString().trim().toUpperCase());
            cv.put("tel_celular", etCelularCony.getText().toString().trim());
            cv.put("estatus_completado", 1);
            if (ENVIROMENT)
                db.update(DATOS_CONYUGE_IND, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
            else
                db.update(DATOS_CONYUGE_IND_T, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});

            save_conyuge = true;
        }
        else
            ivError3.setVisibility(View.VISIBLE);

        return save_conyuge;
    }
    private boolean saveDatosEconomicos(){
        boolean save_economicos = false;
        if (!validator.validate(etPropiedadesEco, new String[]{validator.REQUIRED}) &&
        !validator.validate(etValorAproxEco, new String[]{validator.REQUIRED, validator.ONLY_NUMBER}) &&
        !validator.validate(etUbicacionEco, new String[]{validator.REQUIRED, validator.GENERAL}) &&
        !validator.validate(etIngresoEco, new String[]{validator.REQUIRED, validator.ONLY_NUMBER})){
            ivError4.setVisibility(View.GONE);
            ContentValues cv = new ContentValues();
            cv.put("propiedades", etPropiedadesEco.getText().toString().trim().toUpperCase());
            cv.put("valor_aproximado", etValorAproxEco.getText().toString().trim());
            cv.put("ubicacion", etUbicacionEco.getText().toString().trim().toUpperCase());
            cv.put("ingreso", etIngresoEco.getText().toString().trim());
            cv.put("estatus_completado",1);
            if (ENVIROMENT)
                db.update(DATOS_ECONOMICOS_IND, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
            else
                db.update(DATOS_ECONOMICOS_IND_T, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});

            save_economicos = true;
        }
        else
            ivError4.setVisibility(View.VISIBLE);

        return save_economicos;
    }
    private boolean saveDatosNegocio(){
        boolean save_negocio = false;
        if (!validator.validate(etNombreNeg, new String[]{validator.REQUIRED, validator.GENERAL})){
            if (latLngUbiNeg != null){
                tvMapaNeg.setError(null);
                if (!validator.validate(etCalleNeg, new String[]{validator.REQUIRED}) &&
                !validator.validate(etNoExtNeg, new String[]{validator.REQUIRED}) &&
                !validator.validate(etNoIntNeg, new String[]{validator.GENERAL}) &&
                !validator.validate(etManzanaNeg, new String[]{validator.GENERAL}) &&
                !validator.validate(etLoteNeg, new String[]{validator.GENERAL}) &&
                !validator.validate(etCpNeg, new String[]{validator.REQUIRED, validator.CP}) &&
                !Miscellaneous.ValidTextView(tvColoniaNeg) &&
                !Miscellaneous.ValidTextView(tvMunicipioNeg) &&
                !validatorTV.validate(tvActEconomicaNeg, new String[]{validatorTV.REQUIRED}) &&
                !validator.validate(etAntiguedadNeg, new String[]{validator.REQUIRED, validator.YEARS}) &&
                !validator.validate(etIngMenNeg, new String[]{validator.REQUIRED, validator.ONLY_NUMBER}) &&
                !validator.validate(etOtrosIngNeg, new String[]{validator.REQUIRED, validator.ONLY_NUMBER}) &&
                !validator.validate(etGastosSemNeg, new String[]{validator.REQUIRED, validator.ONLY_NUMBER}) &&
                !validator.validate(etGastosAguaNeg, new String[]{validator.REQUIRED, validator.ONLY_NUMBER}) &&
                !validator.validate(etGastosLuzNeg, new String[]{validator.REQUIRED, validator.ONLY_NUMBER}) &&
                !validator.validate(etGastosTelNeg, new String[]{validator.REQUIRED, validator.ONLY_NUMBER}) &&
                !validator.validate(etGastosRentaNeg, new String[]{validator.REQUIRED, validator.ONLY_NUMBER}) &&
                !validator.validate(etGastosOtrosNeg, new String[]{validator.REQUIRED, validator.ONLY_NUMBER}) &&
                !validatorTV.validate(tvCapacidadPagoNeg, new String[]{validatorTV.REQUIRED}) &&
                !validatorTV.validate(tvDiasVentaNeg, new String[]{validatorTV.REQUIRED})){
                    if (byteFotoFachNeg != null) {
                        tvFachadaNeg.setError(null);
                        if (!validator.validate(etReferenciNeg, new String[]{validator.REQUIRED, validator.GENERAL})){
                            ivError5.setVisibility(View.GONE);
                            ContentValues cv = new ContentValues();
                            cv.put("nombre", etNombreNeg.getText().toString().trim().toUpperCase());
                            cv.put("latitud", String.valueOf(latLngUbiNeg.latitude));
                            cv.put("longitud", String.valueOf(latLngUbiNeg.longitude));
                            cv.put("calle", etCalleNeg.getText().toString().trim().toUpperCase());
                            cv.put("no_exterior", etNoExtNeg.getText().toString().trim().toUpperCase());
                            cv.put("no_interior", etNoIntNeg.getText().toString().trim().toUpperCase());
                            cv.put("manzana", etManzanaNeg.getText().toString().trim().toUpperCase());
                            cv.put("lote", etLoteNeg.getText().toString().trim().toUpperCase());
                            cv.put("cp", etCpNeg.getText().toString().trim());
                            cv.put("colonia", tvColoniaNeg.getText().toString().trim().toUpperCase());
                            cv.put("actividad_economica", tvActEconomicaNeg.getText().toString().trim().toUpperCase());
                            cv.put("antiguedad", Integer.parseInt(etAntiguedadNeg.getText().toString().trim()));
                            cv.put("ing_mensual", etIngMenNeg.getText().toString().toUpperCase());
                            cv.put("ing_otros", etGastosOtrosNeg.getText().toString().trim());
                            cv.put("gasto_semanal", etGastosSemNeg.getText().toString().trim());
                            cv.put("gasto_agua", etGastosAguaNeg.getText().toString().trim());
                            cv.put("gasto_luz", etGastosLuzNeg.getText().toString().trim());
                            cv.put("gasto_telefono", etGastosTelNeg.getText().toString().trim());
                            cv.put("gasto_renta", etGastosRentaNeg.getText().toString().trim());
                            cv.put("gasto_otros", etGastosOtrosNeg.getText().toString().trim());
                            cv.put("capacidad_pago", tvCapacidadPagoNeg.getText().toString().trim());
                            cv.put("dias_venta", tvDiasVentaNeg.getText().toString().trim());
                            cv.put("ref_domiciliaria", etReferenciNeg.getText().toString().trim().toUpperCase());
                            cv.put("estatus_completado", 1);
                            if (ENVIROMENT)
                                db.update(DATOS_NEGOCIO_IND, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                            else
                                db.update(DATOS_NEGOCIO_IND_T, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});

                            save_negocio = true;
                        }
                        else
                            ivError5.setVisibility(View.VISIBLE);
                    }
                    else{
                        ivError5.setVisibility(View.VISIBLE);
                        tvFachadaNeg.setError("");
                    }
                }
                else
                    ivError5.setVisibility(View.VISIBLE);
            }
            else {
                tvMapaNeg.setError("");
                ivError5.setVisibility(View.VISIBLE);
            }
        }
        else
            ivError5.setVisibility(View.VISIBLE);

        return save_negocio;
    }
    private boolean saveDatosAval(){
        boolean save_aval = false;
        ContentValues cv = new ContentValues();
        if (!validator.validate(etNombreAval, new String[]{validator.REQUIRED, validator.ONLY_TEXT}) &&
        !validator.validate(etApPaternoAval, new String[] {validator.REQUIRED, validator.ONLY_TEXT}) &&
        !validator.validate(etApMaternoAval, new String[]{validator.GENERAL}) &&
        !validatorTV.validate(tvFechaNacAval, new String[]{validatorTV.REQUIRED}) &&
        !validatorTV.validate(tvEdadAval, new String[]{validatorTV.REQUIRED, validatorTV.ONLY_NUMBER})){
            if (rgGeneroAval.getCheckedRadioButtonId() == R.id.rbHombre ||
                rgGeneroAval.getCheckedRadioButtonId() == R.id.rbMujer) {
                tvGeneroAval.setError(null);
                if (!validatorTV.validate(tvEstadoNacAval, new String[]{validatorTV.REQUIRED}) &&
                (!tvRfcAval.getText().toString().trim().equals("Rfc no válida")) &&
                !validatorTV.validate(tvCurpAval, new String[]{validatorTV.REQUIRED, validatorTV.CURP}) &&
                !validator.validate(etCurpIdAval, new String[]{validator.REQUIRED, validator.CURP_ID})){
                    if (Miscellaneous.CurpValidador(tvCurpAval.getText() + etCurpIdAval.getText().toString().trim().toUpperCase())) {
                        if(!validatorTV.validate(tvTipoIdentificacionAval, new String[]{validatorTV.REQUIRED}) &&
                        !validator.validate(etNumIdentifAval, new String[]{validator.REQUIRED, validator.ALFANUMERICO}) &&
                        !validatorTV.validate(tvOcupacionAval, new String[]{validatorTV.REQUIRED}) &&
                        !validatorTV.validate(tvActividadEcoAval, new String[]{validatorTV.REQUIRED})){
                            if (latLngUbiAval != null){
                                tvMapaAval.setError(null);
                                if (!validator.validate(etCalleAval, new String[]{validator.REQUIRED}) &&
                                !validator.validate(etNoExtAval, new String[]{validator.REQUIRED}) &&
                                !validator.validate(etCpAval, new String[]{validator.REQUIRED, validator.ONLY_NUMBER, validator.CP}) &&
                                !Miscellaneous.ValidTextView(tvColoniaAval) &&
                                !Miscellaneous.ValidTextView(tvMunicipioAval) &&
                                !Miscellaneous.ValidTextView(tvEstadoAval) &&
                                !validatorTV.validate(tvTipoCasaAval, new String[]{validatorTV.REQUIRED})){
                                    cv.put("tipo_vivienda", tvTipoCasaAval.getText().toString().trim().toUpperCase());
                                    boolean flag_tipo_casa =  false;
                                    if (tvTipoCasaAval.getText().toString().trim().toUpperCase().equals("CASA FAMILIAR")){
                                        if (!validatorTV.validate(tvParentescoAval, new String[]{validatorTV.REQUIRED}) &&
                                                !validator.validate(etNombreTitularAval, new String[]{validator.REQUIRED, validator.ONLY_TEXT})){
                                            cv.put("nombre_titular", etNombreTitularAval.getText().toString().trim().toUpperCase());
                                            cv.put("parentesco", tvParentescoAval.getText().toString().trim().toUpperCase());
                                            flag_tipo_casa = true;
                                        }
                                        else
                                            ivError6.setVisibility(View.VISIBLE);
                                    }
                                    else{
                                        cv.put("nombre_titular", "");
                                        cv.put("parentesco", "");
                                        flag_tipo_casa = true;
                                    }

                                    if(flag_tipo_casa){
                                        if (!validator.validate(etIngMenAval, new String[]{validator.REQUIRED, validator.ONLY_NUMBER}) &&
                                        !validator.validate(etIngOtrosAval, new String[]{validator.REQUIRED, validator.ONLY_NUMBER}) &&
                                        !validator.validate(etGastosSemAval, new String[]{validator.REQUIRED, validator.ONLY_NUMBER}) &&
                                        !validator.validate(etGastosAguaAval, new String[]{validator.REQUIRED, validator.ONLY_NUMBER}) &&
                                        !validator.validate(etGastosLuzAval, new String[]{validator.REQUIRED, validator.ONLY_NUMBER}) &&
                                        !validator.validate(etGastosTelAval, new String[]{validator.REQUIRED, validator.ONLY_NUMBER}) &&
                                        !validator.validate(etGastosRentaAval, new String[]{validator.REQUIRED, validator.ONLY_NUMBER}) &&
                                        !validator.validate(etGastosOtrosAval, new String[]{validator.REQUIRED, validator.ONLY_NUMBER}) &&
                                        !validatorTV.validate(tvHoraLocAval, new String[]{validatorTV.REQUIRED}) &&
                                        !validator.validate(etAntiguedadAval, new String[]{validator.REQUIRED, validator.YEARS}) &&
                                        !validator.validate(etTelCasaAval, new String[]{validator.PHONE}) &&
                                        !validator.validate(etCelularAval, new String[]{validator.REQUIRED, validator.PHONE})){
                                            if (byteFotoFachAval != null) {
                                                tvFachadaAval.setError(null);
                                                if (!validator.validate(etReferenciaAval, new String[]{validator.REQUIRED})) {
                                                    if (byteFirmaAval != null) {
                                                        tvFirmaAval.setError(null);
                                                        ivError6.setVisibility(View.GONE);
                                                        cv.put("nombre", etNombreAval.getText().toString().trim().toUpperCase());
                                                        cv.put("paterno", etApPaternoAval.getText().toString().trim().toUpperCase());
                                                        cv.put("materno", etApMaternoAval.getText().toString().trim().toUpperCase());
                                                        cv.put("fecha_nacimiento", tvFechaNacAval.getText().toString().trim());
                                                        cv.put("edad", tvEdadAval.getText().toString().trim());
                                                        switch (rgGeneroAval.getCheckedRadioButtonId()){
                                                            case R.id.rbHombre:
                                                                cv.put("genero", 0);
                                                                break;
                                                            case R.id.rbMujer:
                                                                cv.put("genero", 1);
                                                                break;
                                                        }
                                                        cv.put("estado_nacimiento", tvEstadoNacAval.getText().toString().trim());
                                                        cv.put("rfc", tvRfcAval.getText().toString().trim().toUpperCase());
                                                        cv.put("curp", tvCurpAval.getText().toString().trim().toUpperCase());
                                                        cv.put("curp_digito_veri", etCurpIdAval.getText().toString().trim());
                                                        cv.put("no_identificacion", etNumIdentifAval.getText().toString().trim().toUpperCase());
                                                        cv.put("ocupacion", tvOcupacionAval.getText().toString().trim().toUpperCase());
                                                        cv.put("actividad_economica", tvActividadEcoAval.getText().toString().trim().toUpperCase());
                                                        cv.put("latitud", String.valueOf(latLngUbiAval.latitude));
                                                        cv.put("longitud", String.valueOf(latLngUbiAval.longitude));
                                                        cv.put("calle", etCalleAval.getText().toString().trim().toUpperCase());
                                                        cv.put("no_exterior", etNoExtAval.getText().toString().trim().toUpperCase());
                                                        cv.put("no_interior", etNoIntAval.getText().toString().trim().toUpperCase());
                                                        cv.put("manzana", etManzanaAval.getText().toString().trim().toUpperCase());
                                                        cv.put("lote", etLoteAval.getText().toString().trim().toUpperCase());
                                                        cv.put("cp", etCpAval.getText().toString().trim().toUpperCase());
                                                        cv.put("colonia", tvColoniaAval.getText().toString().trim().toUpperCase());
                                                        cv.put("ing_mensual", etIngMenAval.getText().toString().trim());
                                                        cv.put("ing_otros", etIngOtrosAval.getText().toString().trim());
                                                        cv.put("gasto_semanal", etGastosSemAval.getText().toString().trim());
                                                        cv.put("gasto_agua", etGastosAguaAval.getText().toString().trim());
                                                        cv.put("gasto_luz", etGastosLuzAval.getText().toString().trim());
                                                        cv.put("gasto_telefono", etGastosTelAval.getText().toString().trim());
                                                        cv.put("gasto_renta", etGastosRentaAval.getText().toString().trim());
                                                        cv.put("gasto_otros", etGastosOtrosAval.getText().toString().trim());
                                                        cv.put("horario_localizacion", tvHoraLocAval.getText().toString().trim());
                                                        cv.put("antiguedad", etAntiguedadAval.getText().toString().trim());
                                                        cv.put("tel_casa", etTelCasaAval.getText().toString().trim());
                                                        cv.put("tel_celular", etCelularAval.getText().toString().trim());
                                                        cv.put("ref_domiciliaria", etReferenciaAval.getText().toString().trim().toUpperCase());
                                                        cv.put("estatus_completado", 1);
                                                        if (ENVIROMENT)
                                                            db.update(DATOS_AVAL_IND, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                                                        else
                                                            db.update(DATOS_AVAL_IND_T, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});

                                                        save_aval = true;
                                                    }
                                                    else{
                                                        tvFirmaAval.setError("");
                                                    }
                                                }
                                                else
                                                    ivError6.setVisibility(View.VISIBLE);
                                            }
                                            else{
                                                tvFachadaAval.setError("");
                                                ivError6.setVisibility(View.VISIBLE);
                                            }
                                        }
                                        else
                                            ivError6.setVisibility(View.VISIBLE);
                                    }
                                }
                                else
                                    ivError6.setVisibility(View.VISIBLE);

                            }
                            else{
                                ivError6.setVisibility(View.VISIBLE);
                                tvMapaAval.setError("");
                            }
                        }
                        else
                            ivError6.setVisibility(View.VISIBLE);
                    }
                    else{
                        ivError6.setVisibility(View.VISIBLE);
                        etCurpIdAval.setError("Curp no válida");
                    }
                }
                else
                    ivError6.setVisibility(View.VISIBLE);
            }
            else{
                ivError6.setVisibility(View.VISIBLE);
                tvGeneroAval.setError("");
            }
        }
        else
            ivError6.setVisibility(View.VISIBLE);

        return save_aval;
    }
    private boolean saveReferencia() {
        boolean save_referencia = false;
        if (!validator.validate(etNombreRef, new String[]{validator.REQUIRED, validator.ONLY_TEXT}) &&
        !validator.validate(etApPaternoRef, new String[]{validator.REQUIRED, validator.ONLY_TEXT}) &&
        !validator.validate(etApMaternoRef, new String[]{validator.ONLY_TEXT}) &&
        !validator.validate(etCalleRef, new String[]{validator.REQUIRED}) &&
        !validator.validate(etCpRef, new String[]{validator.REQUIRED, validator.CP}) &&
        !Miscellaneous.ValidTextView(tvColoniaRef) &&
        !Miscellaneous.ValidTextView(tvMunicipioRef) &&
        !validator.validate(etTelCelRef, new String[]{validator.REQUIRED, validator.ONLY_NUMBER, validator.PHONE})) {
            ivError7.setVisibility(View.GONE);
            ContentValues cv = new ContentValues();
            cv.put("nombre", etNombreRef.getText().toString().trim().toUpperCase());
            cv.put("paterno", etApPaternoRef.getText().toString().trim().toUpperCase());
            cv.put("materno", etApMaternoRef.getText().toString().trim().toUpperCase());
            cv.put("calle", etCalleRef.getText().toString().trim().toUpperCase());
            cv.put("cp", etCpRef.getText().toString().trim());
            cv.put("colonia", tvColoniaRef.getText().toString().trim().toUpperCase());
            cv.put("municipio", tvMunicipioRef.getText().toString().trim().toUpperCase());
            cv.put("tel_celular", etTelCelRef.getText().toString().trim().toUpperCase());
            cv.put("estatus_completado", 1);
            if (ENVIROMENT)
                db.update(DATOS_REFERENCIA_IND, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
            else
                db.update(DATOS_REFERENCIA_IND_T, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});

            save_referencia = true;
        } else
            ivError7.setVisibility(View.VISIBLE);

        return save_referencia;
    }
    private boolean saveDocumentacion(){
        boolean save_documentacion = false;
        if (byteIneFrontal != null){
            if (byteIneReverso != null){
                if (byteCurp != null){
                    if (byteComprobante != null){
                        if (byteFirmaAsesor != null) {
                            save_documentacion = true;
                        }
                        else{
                            ivError8.setVisibility(View.VISIBLE);
                            tvFirmaAsesor.setError("");
                        }
                    }
                    else{
                        ivError8.setVisibility(View.VISIBLE);
                        tvComprobante.setError("");
                    }
                }
                else{
                    ivError8.setVisibility(View.VISIBLE);
                    tvCurp.setError("");
                }
            }
            else{
                ivError8.setVisibility(View.VISIBLE);
                tvIneReverso.setError("");
            }
        }
        else{
            ivError8.setVisibility(View.VISIBLE);
            tvIneFrontal.setError("");
        }

        return save_documentacion;
    }

    //===================== Listener GPS  =======================================================
    private void ObtenerUbicacion (){
        pbLoadCli.setVisibility(View.VISIBLE);
        ibMapCli.setEnabled(false);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        final Handler myHandler = new Handler();
        locationListener = new MyCurrentListener(new MyCurrentListener.evento() {
            @Override
            public void onComplete(String latitud, String longitud) {

                ibMapCli.setEnabled(true);
                ContentValues cv = new ContentValues();
                if (!latitud.isEmpty() && !longitud.isEmpty()){
                    mapCli.setVisibility(View.VISIBLE);
                    cv.put("latitud",String.valueOf(latitud));
                    cv.put("longitud",String.valueOf(longitud));
                    Ubicacion(Double.parseDouble(latitud), Double.parseDouble(longitud));

                }
                else{
                    cv.put("latitud", "");
                    cv.put("longitud","");
                    pbLoadCli.setVisibility(View.GONE);
                    Toast.makeText(ctx, getResources().getString(R.string.no_ubicacion), Toast.LENGTH_SHORT).show();
                }
                if (ENVIROMENT)
                    db.update(DATOS_CLIENTE_IND, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                else
                    db.update(DATOS_CLIENTE_IND_T, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                myHandler.removeCallbacksAndMessages(null);

                CancelUbicacion();

            }
        });
        if (ActivityCompat.checkSelfPermission(ctx, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(ctx, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
        }

        String provider;

        if (NetworkStatus.haveNetworkConnection(ctx)) {
            Log.e("Proveedor", "RED");
            provider = LocationManager.NETWORK_PROVIDER;
        }
        else {
            Log.e("Proveedor", "GPS");
            provider = LocationManager.GPS_PROVIDER;
        }

        locationManager.requestSingleUpdate(provider, locationListener,null);

        myHandler.postDelayed(new Runnable() {
            public void run() {
                locationManager.removeUpdates(locationListener);
                pbLoadCli.setVisibility(View.GONE);
                ibMapCli.setEnabled(true);
                Toast.makeText(ctx, "No se logró obtener la ubicación", Toast.LENGTH_SHORT).show();
            }
        }, 60000);
    }
    private void ObtenerUbicacionNeg (){
        pbLoadNeg.setVisibility(View.VISIBLE);
        ibMapNeg.setEnabled(false);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        final Handler myHandler = new Handler();
        locationListener = new MyCurrentListener(new MyCurrentListener.evento() {
            @Override
            public void onComplete(String latitud, String longitud) {

                ContentValues cv = new ContentValues();
                ibMapNeg.setEnabled(true);
                if (!latitud.isEmpty() && !longitud.isEmpty()){
                    mapNeg.setVisibility(View.VISIBLE);
                    cv.put("latitud",String.valueOf(latitud));
                    cv.put("longitud",String.valueOf(longitud));
                    UbicacionNeg(Double.parseDouble(latitud), Double.parseDouble(longitud));
                }
                else{
                    cv.put("latitud", "");
                    cv.put("longitud","");
                    pbLoadNeg.setVisibility(View.GONE);
                    Toast.makeText(ctx, getResources().getString(R.string.no_ubicacion), Toast.LENGTH_SHORT).show();
                }
                if (ENVIROMENT)
                    db.update(DATOS_NEGOCIO_IND, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                else
                    db.update(DATOS_NEGOCIO_IND_T, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                myHandler.removeCallbacksAndMessages(null);

                CancelUbicacion();

            }
        });
        if (ActivityCompat.checkSelfPermission(ctx, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(ctx, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
        }

        String provider;

        if (NetworkStatus.haveNetworkConnection(ctx)) {
            Log.e("Proveedor", "RED");
            provider = LocationManager.NETWORK_PROVIDER;
        }
        else {
            Log.e("Proveedor", "GPS");
            provider = LocationManager.GPS_PROVIDER;
        }

        locationManager.requestSingleUpdate(provider, locationListener,null);

        myHandler.postDelayed(new Runnable() {
            public void run() {
                locationManager.removeUpdates(locationListener);
                pbLoadNeg.setVisibility(View.GONE);
                ibMapNeg.setEnabled(true);
                Toast.makeText(ctx, "No se logró obtener la ubicación", Toast.LENGTH_SHORT).show();
            }
        }, 60000);
    }
    private void ObtenerUbicacionAval (){
        pbLoadAval.setVisibility(View.VISIBLE);
        ibMapAval.setEnabled(false);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        final Handler myHandler = new Handler();
        locationListener = new MyCurrentListener(new MyCurrentListener.evento() {
            @Override
            public void onComplete(String latitud, String longitud) {

                ContentValues cv = new ContentValues();
                ibMapAval.setEnabled(true);
                if (!latitud.isEmpty() && !longitud.isEmpty()){
                    mapAval.setVisibility(View.VISIBLE);
                    cv.put("latitud",String.valueOf(latitud));
                    cv.put("longitud",String.valueOf(longitud));
                    UbicacionAval(Double.parseDouble(latitud), Double.parseDouble(longitud));
                }
                else{
                    cv.put("latitud", "");
                    cv.put("longitud","");
                    pbLoadAval.setVisibility(View.GONE);
                    Toast.makeText(ctx, getResources().getString(R.string.no_ubicacion), Toast.LENGTH_SHORT).show();
                }
                if (ENVIROMENT)
                    db.update(DATOS_AVAL_IND, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                else
                    db.update(DATOS_AVAL_IND_T, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                myHandler.removeCallbacksAndMessages(null);

                CancelUbicacion();

            }
        });
        if (ActivityCompat.checkSelfPermission(ctx, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(ctx, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
        }

        String provider;

        if (NetworkStatus.haveNetworkConnection(ctx)) {
            Log.e("Proveedor", "RED");
            provider = LocationManager.NETWORK_PROVIDER;
        }
        else {
            Log.e("Proveedor", "GPS");
            provider = LocationManager.GPS_PROVIDER;
        }

        locationManager.requestSingleUpdate(provider, locationListener,null);

        myHandler.postDelayed(new Runnable() {
            public void run() {
                locationManager.removeUpdates(locationListener);
                pbLoadNeg.setVisibility(View.GONE);
                ibMapNeg.setEnabled(true);
                Toast.makeText(ctx, "No se logró obtener la ubicación", Toast.LENGTH_SHORT).show();
            }
        }, 60000);
    }

    private void Ubicacion (final double lat, final double lon){
        mapCli.onResume();
        try {
            MapsInitializer.initialize(getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mapCli.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mGooglemap) {
                mMapCli = mGooglemap;
                if (ActivityCompat.checkSelfPermission(ctx, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(ctx, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                    return;
                }
                mMapCli.setMapStyle(
                        MapStyleOptions.loadRawResourceStyle(
                                ctx, R.raw.style_json));
                mMapCli.getUiSettings().setAllGesturesEnabled(false);
                mMapCli.getUiSettings().setMapToolbarEnabled(false);

                addMarker(lat,lon);

            }
        });
    }
    private void UbicacionNeg (final double lat, final double lon){
        mapNeg.onResume();
        try {
            MapsInitializer.initialize(getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mapNeg.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mGooglemap) {
                mMapNeg = mGooglemap;
                if (ActivityCompat.checkSelfPermission(ctx, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(ctx, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                    return;
                }
                mMapNeg.setMapStyle(
                        MapStyleOptions.loadRawResourceStyle(
                                ctx, R.raw.style_json));
                mMapNeg.getUiSettings().setAllGesturesEnabled(false);
                mMapNeg.getUiSettings().setMapToolbarEnabled(false);

                addMarkerNeg(lat,lon);

            }
        });
    }
    private void UbicacionAval (final double lat, final double lon){
        mapAval.onResume();
        try {
            MapsInitializer.initialize(getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mapAval.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mGooglemap) {
                mMapAval = mGooglemap;
                if (ActivityCompat.checkSelfPermission(ctx, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(ctx, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                    return;
                }
                mMapAval.setMapStyle(
                        MapStyleOptions.loadRawResourceStyle(
                                ctx, R.raw.style_json));
                mMapAval.getUiSettings().setAllGesturesEnabled(false);
                mMapAval.getUiSettings().setMapToolbarEnabled(false);

                addMarkerAval(lat,lon);

            }
        });
    }

    private void addMarker (double lat, double lng){
        LatLng coordenadas = new LatLng(lat,lng);
        latLngUbiCli = coordenadas;
        CameraUpdate ubication = CameraUpdateFactory.newLatLngZoom(coordenadas,17);

        mMapCli.clear();
        mMapCli.addMarker(new MarkerOptions()
                .position(coordenadas)
                .title(""));

        mMapCli.animateCamera(ubication);

        pbLoadCli.setVisibility(View.GONE);
        ibMapCli.setVisibility(View.GONE);
    }
    private void addMarkerNeg (double lat, double lng){
        LatLng coordenadas = new LatLng(lat,lng);
        latLngUbiNeg = coordenadas;
        CameraUpdate ubication = CameraUpdateFactory.newLatLngZoom(coordenadas,17);

        mMapNeg.clear();
        mMapNeg.addMarker(new MarkerOptions()
                .position(coordenadas)
                .title(""));

        mMapNeg.animateCamera(ubication);

        pbLoadNeg.setVisibility(View.GONE);
        ibMapNeg.setVisibility(View.GONE);
    }
    private void addMarkerAval (double lat, double lng){
        LatLng coordenadas = new LatLng(lat,lng);
        latLngUbiAval = coordenadas;
        CameraUpdate ubication = CameraUpdateFactory.newLatLngZoom(coordenadas,17);

        mMapAval.clear();
        mMapAval.addMarker(new MarkerOptions()
                .position(coordenadas)
                .title(""));

        mMapAval.animateCamera(ubication);

        pbLoadAval.setVisibility(View.GONE);
        ibMapAval.setVisibility(View.GONE);
    }

    private void CancelUbicacion (){
        locationManager.removeUpdates(locationListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_enviar_datos, menu);
        if (!is_edit)
        {
            for (int i = 0; i < menu.size(); i++)
                menu.getItem(i).setVisible(is_edit);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:

                break;
            case R.id.enviar:
                final AlertDialog loading = Popups.showLoadingDialog(context, R.string.please_wait, R.string.loading_info);
                loading.show();
                boolean credito, cliente, conyuge, economicos, negocio, aval, referencia, documentacion;
                credito = saveDatosCredito();
                cliente = saveDatosPersonales();
                if (tvEstadoCivilCli.getText().toString().trim().toUpperCase().equals("CASADA(O)") ||
                    tvEstadoCivilCli.getText().toString().trim().toUpperCase().equals("UNION LIBRE"))
                    conyuge = saveConyuge();
                else
                    conyuge = true;
                if (!etMontoPrestamo.getText().toString().trim().isEmpty() && Integer.parseInt(etMontoPrestamo.getText().toString().trim()) >= 30000)
                    economicos = saveDatosEconomicos();
                else
                    economicos = true;
                negocio = saveDatosNegocio();
                aval = saveDatosAval();
                referencia = saveReferencia();
                documentacion = saveDocumentacion();

                if (credito && cliente && conyuge && economicos && negocio && aval && referencia && documentacion){
                    Log.e("guarda", "guradado");
                    ContentValues cv = new ContentValues();
                    cv.put("estatus", 0);
                    cv.put("fecha_termino", Miscellaneous.ObtenerFecha("timestamp"));
                    Log.e("id_solicitud", String.valueOf(id_solicitud));
                    if (ENVIROMENT)
                        db.update(SOLICITUDES, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                    else
                        db.update(SOLICITUDES_T, cv, "id_solicitud = " + id_solicitud, null);

                    Servicios_Sincronizado s = new Servicios_Sincronizado();
                    s.SendOriginacionInd(ctx, false);
                    loading.dismiss();
                    Toast.makeText(ctx, "termina guardado", Toast.LENGTH_SHORT).show();

                    finish();

                }
                else {
                    loading.dismiss();
                    Toast.makeText(ctx, "Faltan por llenar campos", Toast.LENGTH_SHORT).show();

                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (is_edit) {
            AlertDialog solicitud = Popups.showDialogConfirm(this, Constants.warning,
                    R.string.guardar_cambios, R.string.yes, new Popups.DialogMessage() {
                        @Override
                        public void OnClickListener(AlertDialog dialog) {
                            finish();
                            dialog.dismiss();
                        }
                    }, R.string.cancel, new Popups.DialogMessage() {
                        @Override
                        public void OnClickListener(AlertDialog dialog) {
                            dialog.dismiss();
                        }
                    });
            solicitud.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            solicitud.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            solicitud.setCancelable(true);
            solicitud.show();
        }
        else
            finish();

    }

    private void openRegistroCliente() {
        dialog_registro_cli registro_cli = new dialog_registro_cli();
        registro_cli.setCancelable(false);
        registro_cli.show(getSupportFragmentManager(), NameFragments.DIALOGORIGINACIONIND);
    }

    @Override
    public void onComplete(long id_solicitud, String id_cliente, String nombre, String paterno, String materno) {
        if (id_solicitud > 0) {
            this.id_solicitud = id_solicitud;
            this.id_cliente = id_cliente;
            etNombreCli.setText(nombre);
            etNombreCli.setEnabled(false);
            etApPaternoCli.setText(paterno);
            etApPaternoCli.setEnabled(false);
            etApMaternoCli.setText(materno);
            etApMaternoCli.setEnabled(false);
        }
        else
            finish();
    }

    private void initComponents (String idSolicitud){
        Cursor row = dBhelper.getOriginacionInd(idSolicitud, false);

        row.moveToFirst();

        is_edit = row.getInt(161) == 1;

        if (!is_edit)
            invalidateOptionsMenu();

        id_cliente = row.getString(10);

        //Llenado del datos del prestamo
        tvPlazo.setText(row.getString(2).toUpperCase());
        tvFrecuencia.setText(row.getString(3).toUpperCase());
        tvFechaDesembolso.setText(row.getString(4));
        tvDiaDesembolso.setText(row.getString(5));
        tvHoraVisita.setText(row.getString(6));
        etMontoPrestamo.setText(row.getString(7)); etMontoPrestamo.setEnabled(is_edit);
        if (!row.getString(7).trim().isEmpty()) {
            tvCantidadLetra.setText((Miscellaneous.cantidadLetra(row.getString(7)).toUpperCase() + " PESOS MEXICANOS ").replace("  ", " "));
            if (row.getInt(7) >= 30000)
                llPropiedades.setVisibility(View.VISIBLE);
        }
        tvDestino.setText(row.getString(8));
        //Llenado de datos del cliente
        etNombreCli.setText(row.getString(12)); etNombreCli.setEnabled(false);
        etApPaternoCli.setText(row.getString(13)); etApPaternoCli.setEnabled(false);
        etApMaternoCli.setText(row.getString(14)); etApMaternoCli.setEnabled(false);
        tvFechaNacCli.setText(row.getString(15));
        tvEdadCli.setText(row.getString(16));
        switch (row.getInt(17)){
            case 0:
                rgGeneroCli.check(R.id.rbHombre);
                break;
            case 1:
                rgGeneroCli.check(R.id.rbMujer);
                break;
        }
        tvEstadoNacCli.setText(row.getString(18));
        tvRfcCli.setText(row.getString(19));
        tvCurpCli.setText(row.getString(21));
        etCurpIdCli.setText(row.getString(22)); etCurpIdCli.setEnabled(is_edit);
        tvOcupacionCli.setText(row.getString(23));
        tvActividadEcoCli.setText(row.getString(24));
        tvTipoIdentificacion.setText(row.getString(25));
        etNumIdentifCli.setText(row.getString(26)); etNumIdentifCli.setEnabled(is_edit);
        tvEstudiosCli.setText(row.getString(27));
        tvEstadoCivilCli.setText(row.getString(28));
        switch (row.getString(28)){
            case "CASADA(O)":
                llConyuge.setVisibility(View.VISIBLE);
                llBienes.setVisibility(View.VISIBLE);
                switch (row.getInt(29)){
                    case 1:
                        rgBienes.check(R.id.rbMancomunados);
                        break;
                    case 2:
                        rgGeneroCli.check(R.id.rbSeparados);
                        break;
                }
                break;
            case "UNION LIBRE":
                llConyuge.setVisibility(View.VISIBLE);
                break;
        }

        tvTipoCasaCli.setText(row.getString(30));
        switch (row.getString(30)){
            case "CASA FAMILIAR":
                llCasaFamiliar.setVisibility(View.VISIBLE);
                tvCasaFamiliar.setText(row.getString(31));
                break;
            case "OTRO":
                llCasaOtroCli.setVisibility(View.VISIBLE);
                etOTroTipoCli.setText(row.getString(32));
                break;
        }
        if (!row.getString(33).isEmpty() && !row.getString(34).isEmpty()){
            mapCli.setVisibility(View.VISIBLE);
            Ubicacion(row.getDouble(33), row.getDouble(34));
        }
        etCalleCli.setText(row.getString(35)); etCalleCli.setEnabled(is_edit);
        etNoExtCli.setText(row.getString(36)); etNoExtCli.setEnabled(is_edit);
        etNoIntCli.setText(row.getString(37)); etNoIntCli.setEnabled(is_edit);
        etManzanaCli.setText(row.getString(38)); etManzanaCli.setEnabled(is_edit);
        etLoteCli.setText(row.getString(39)); etLoteCli.setEnabled(is_edit);
        etCpCli.setText(row.getString(40)); etCpCli.setEnabled(is_edit);
        tvColoniaCli.setText(row.getString(41));
        if (!row.getString(40).trim().isEmpty()) {
            Cursor rowColonia = dBhelper.getDireccionByCP(row.getString(40));
            if (rowColonia.getCount() > 0) {
                rowColonia.moveToFirst();
                tvMunicipioCli.setText(rowColonia.getString(4));
                tvEstadoCli.setText(rowColonia.getString(1));
            } else {
                tvColoniaCli.setText("No se encontró información");
                tvMunicipioCli.setText("No se encontró información");
                tvEstadoCli.setText("No se encontró información");
            }
            rowColonia.close();
        }
        etTelCasaCli.setText(row.getString(42)); etTelCasaCli.setEnabled(is_edit);
        etCelularCli.setText(row.getString(43)); etCelularCli.setEnabled(is_edit);
        etTelMensCli.setText(row.getString(44)); etTelMensCli.setEnabled(is_edit);
        etTelTrabajoCli.setText(row.getString(45)); etTelTrabajoCli.setEnabled(is_edit);
        etTiempoSitio.setText((row.getInt(46) > 0)?row.getString(46):""); etTiempoSitio.setEnabled(is_edit);
        tvDependientes.setText(row.getString(47));
        tvEnteroNosotros.setText(row.getString(48));
        etEmail.setText(row.getString(49)); etEmail.setEnabled(is_edit);
        if (!row.getString(50).isEmpty()){
            File fachadaFile = new File(Constants.ROOT_PATH + "Fachada/"+row.getString(50));
            Uri uriFachada = Uri.fromFile(fachadaFile);
            byteFotoFachCli = Miscellaneous.getBytesUri(ctx, uriFachada,0);
            Glide.with(ctx).load(uriFachada).into(ivFotoFachCli);
            ibFotoFachCli.setVisibility(View.GONE);
            ivFotoFachCli.setVisibility(View.VISIBLE);
        }
        etReferenciCli.setText(row.getString(51)); etReferenciCli.setEnabled(is_edit);
        if (!row.getString(52).isEmpty()){
            File firmaFile = new File(Constants.ROOT_PATH + "Firma/"+row.getString(52));
            Uri uriFirma = Uri.fromFile(firmaFile);
            byteFirmaCli = Miscellaneous.getBytesUri(ctx, uriFirma,0);
            Glide.with(ctx).load(uriFirma).into(ivFirmaCli);
            ibFirmaCli.setVisibility(View.GONE);
            ivFirmaCli.setVisibility(View.VISIBLE);
        }

        // Llenado de datos del conyuge
        etNombreCony.setText(row.getString(58)); etNombreCony.setEnabled(is_edit);
        etApPaternoCony.setText(row.getString(59)); etApPaternoCony.setEnabled(is_edit);
        etApMaternoCony.setText(row.getString(60)); etApMaternoCony.setEnabled(is_edit);
        tvOcupacionCony.setText(row.getString(61));
        etCelularCony.setText(row.getString(62)); etCelularCony.setEnabled(is_edit);

        //Llenado de datos economicos
        etPropiedadesEco.setText(row.getString(66)); etPropiedadesEco.setEnabled(is_edit);
        etValorAproxEco.setText(row.getString(67)); etValorAproxEco.setEnabled(is_edit);
        etUbicacionEco.setText(row.getString(68)); etUbicacionEco.setEnabled(is_edit);
        etIngresoEco.setText(row.getString(69)); etIngresoEco.setEnabled(is_edit);

        //Llenado de datos del negocio
        etNombreNeg.setText(row.getString(73)); etNombreNeg.setEnabled(is_edit);
        if (!row.getString(74).isEmpty() && !row.getString(75).isEmpty()){
            mapNeg.setVisibility(View.VISIBLE);
            UbicacionNeg(row.getDouble(74), row.getDouble(75));
        }
        etCalleNeg.setText(row.getString(76)); etCalleNeg.setEnabled(is_edit);
        etNoExtNeg.setText(row.getString(77)); etNoExtNeg.setEnabled(is_edit);
        etNoIntNeg.setText(row.getString(78)); etNoIntNeg.setEnabled(is_edit);
        etManzanaNeg.setText(row.getString(79)); etManzanaNeg.setEnabled(is_edit);
        etLoteNeg.setText(row.getString(80)); etLoteNeg.setEnabled(is_edit);
        etCpNeg.setText(row.getString(81)); etCpNeg.setEnabled(is_edit);
        tvColoniaNeg.setText(row.getString(82));
        if (!row.getString(81).trim().isEmpty()) {
            Cursor rowColonia = dBhelper.getDireccionByCP(row.getString(81));
            if (rowColonia.getCount() > 0) {
                rowColonia.moveToFirst();
                tvMunicipioNeg.setText(rowColonia.getString(4));

            } else {
                tvColoniaNeg.setText("No se encontró información");
                tvMunicipioNeg.setText("No se encontró información");
            }
            rowColonia.close();
        }
        tvActEconomicaNeg.setText(row.getString(83));
        if (row.getInt(84) > 0)
            etAntiguedadNeg.setText(row.getString(84)); etAntiguedadNeg.setEnabled(is_edit);
        etIngMenNeg.setText(row.getString(85)); etIngMenNeg.setEnabled(is_edit);
        etOtrosIngNeg.setText(row.getString(86)); etOtrosIngNeg.setEnabled(is_edit);
        etGastosSemNeg.setText(row.getString(87)); etGastosSemNeg.setEnabled(is_edit);
        etGastosAguaNeg.setText(row.getString(88)); etGastosAguaNeg.setEnabled(is_edit);
        etGastosLuzNeg.setText(row.getString(89)); etGastosLuzNeg.setEnabled(is_edit);
        etGastosTelNeg.setText(row.getString(90)); etGastosTelNeg.setEnabled(is_edit);
        etGastosRentaNeg.setText(row.getString(91)); etGastosRentaNeg.setEnabled(is_edit);
        etGastosOtrosNeg.setText(row.getString(92)); etGastosOtrosNeg.setEnabled(is_edit);
        tvCapacidadPagoNeg.setText(row.getString(93));
        tvDiasVentaNeg.setText(row.getString(94));
        if (!row.getString(95).isEmpty()){
            File fachadaFile = new File(Constants.ROOT_PATH + "Fachada/"+row.getString(95));
            Uri uriFachada = Uri.fromFile(fachadaFile);
            byteFotoFachNeg = Miscellaneous.getBytesUri(ctx, uriFachada,0);
            Glide.with(ctx).load(uriFachada).into(ivFotoFachNeg);
            ibFotoFachNeg.setVisibility(View.GONE);
            ivFotoFachNeg.setVisibility(View.VISIBLE);
        }
        etReferenciNeg.setText(row.getString(96)); etReferenciNeg.setEnabled(is_edit);

        //Llenado de datos del aval
        etNombreAval.setText(row.getString(100)); etNombreAval.setEnabled(is_edit);
        etApPaternoAval.setText(row.getString(101)); etApPaternoAval.setEnabled(is_edit);
        etApMaternoAval.setText(row.getString(102)); etApMaternoAval.setEnabled(is_edit);
        tvFechaNacAval.setText(row.getString(103));
        tvEdadAval.setText(row.getString(104));
        switch (row.getInt(105)){
            case 0:
                rgGeneroAval.check(R.id.rbHombre);
                break;
            case 1:
                rgGeneroAval.check(R.id.rbMujer);
                break;
        }
        tvEstadoNacAval.setText(row.getString(106));
        tvRfcAval.setText(row.getString(107));
        tvCurpAval.setText(row.getString(109));
        etCurpIdAval.setText(row.getString(110)); etCurpIdAval.setEnabled(is_edit);
        tvTipoIdentificacionAval.setText(row.getString(111));
        etNumIdentifAval.setText(row.getString(112)); etNumIdentifAval.setEnabled(is_edit);
        tvOcupacionAval.setText(row.getString(113));
        tvActividadEcoAval.setText(row.getString(114));
        if (!row.getString(115).isEmpty() && !row.getString(116).isEmpty()){
            mapAval.setVisibility(View.VISIBLE);
            UbicacionAval(row.getDouble(115), row.getDouble(116));
        }
        etCalleAval.setText(row.getString(117)); etCalleAval.setEnabled(is_edit);
        etNoExtAval.setText(row.getString(118)); etNoExtAval.setEnabled(is_edit);
        etNoIntAval.setText(row.getString(119)); etNoIntAval.setEnabled(is_edit);
        etManzanaAval.setText(row.getString(120)); etManzanaAval.setEnabled(is_edit);
        etLoteAval.setText(row.getString(121)); etLoteAval.setEnabled(is_edit);
        etCpAval.setText(row.getString(122)); etCpAval.setEnabled(is_edit);
        tvColoniaAval.setText(row.getString(123));
        if (!row.getString(122).trim().isEmpty()) {
            Cursor rowColonia = dBhelper.getDireccionByCP(row.getString(122));
            if (rowColonia.getCount() > 0) {
                rowColonia.moveToFirst();
                tvMunicipioAval.setText(rowColonia.getString(4));
                tvEstadoAval.setText(rowColonia.getString(1));
            } else {
                tvColoniaAval.setText("No se encontró información");
                tvMunicipioAval.setText("No se encontró información");
                tvEstadoAval.setText("No se encontró información");
            }
            rowColonia.close();
        }
        tvTipoCasaAval.setText(row.getString(124));

        if (row.getString(124).trim().toUpperCase().equals("CASA FAMILIAR")){
            llParentescoFamAval.setVisibility(View.VISIBLE);
            llNombreTitular.setVisibility(View.VISIBLE);
            etNombreTitularAval.setText(row.getString(125)); etNombreTitularAval.setEnabled(is_edit);
            tvParentescoAval.setText(row.getString(126));
        }
        etIngMenAval.setText(row.getString(127)); etIngMenAval.setEnabled(is_edit);
        etIngOtrosAval.setText(row.getString(128)); etIngOtrosAval.setEnabled(is_edit);
        etGastosSemAval.setText(row.getString(129)); etGastosSemAval.setEnabled(is_edit);
        etGastosAguaAval.setText(row.getString(130)); etGastosAguaAval.setEnabled(is_edit);
        etGastosLuzAval.setText(row.getString(131)); etGastosLuzAval.setEnabled(is_edit);
        etGastosTelAval.setText(row.getString(132)); etGastosTelAval.setEnabled(is_edit);
        etGastosRentaAval.setText(row.getString(133)); etGastosRentaAval.setEnabled(is_edit);
        etGastosOtrosAval.setText(row.getString(134)); etGastosOtrosAval.setEnabled(is_edit);
        tvHoraLocAval.setText(row.getString(135));
        if (row.getInt(136) > 0)
            etAntiguedadAval.setText(row.getString(136));
        etTelCasaAval.setText(row.getString(137)); etTelCasaAval.setEnabled(is_edit);
        etCelularAval.setText(row.getString(138)); etCelularAval.setEnabled(is_edit);
        if (!row.getString(139).isEmpty()){
            File fachadaFile = new File(Constants.ROOT_PATH + "Fachada/"+row.getString(139));
            Uri uriFachada = Uri.fromFile(fachadaFile);
            byteFotoFachAval = Miscellaneous.getBytesUri(ctx, uriFachada,0);
            Glide.with(ctx).load(uriFachada).into(ivFotoFachAval);
            ibFotoFachAval.setVisibility(View.GONE);
            ivFotoFachAval.setVisibility(View.VISIBLE);
        }
        etReferenciaAval.setText(row.getString(140)); etReferenciaAval.setEnabled(is_edit);
        if (!row.getString(141).isEmpty()){
            File firmaFile = new File(Constants.ROOT_PATH + "Firma/"+row.getString(141));
            Uri uriFirma = Uri.fromFile(firmaFile);
            byteFirmaAval = Miscellaneous.getBytesUri(ctx, uriFirma,0);
            Glide.with(ctx).load(uriFirma).into(ivFirmaAval);
            ibFirmaAval.setVisibility(View.GONE);
            ivFirmaAval.setVisibility(View.VISIBLE);
        }

        //Llena datos de referencia
        etNombreRef.setText(row.getString(147)); etNombreRef.setEnabled(is_edit);
        etApPaternoRef.setText(row.getString(148)); etApPaternoRef.setEnabled(is_edit);
        etApMaternoRef.setText(row.getString(149)); etApMaternoRef.setEnabled(is_edit);
        etCalleRef.setText(row.getString(150)); etCalleRef.setEnabled(is_edit);
        etCpRef.setText(row.getString(151)); etCpRef.setEnabled(is_edit);
        tvColoniaRef.setText(row.getString(152));
        tvMunicipioRef.setText(row.getString(153));
        etTelCelRef.setText(row.getString(154)); etTelCelRef.setEnabled(is_edit);

        Log.e("count filas", ""+row.getCount());
        //Llena la documentacion
        for (int i = 0; i < row.getCount(); i++){
            Log.e("fila", row.getString(12));
            switch (row.getInt(159)){
                case 1:
                    if (!row.getString(158).isEmpty() && row.getInt(160) == 1){
                        File ineFrontalFile = new File(Constants.ROOT_PATH + "Documentos/"+row.getString(158));
                        Uri uriIneFrontal = Uri.fromFile(ineFrontalFile);
                        byteIneFrontal = Miscellaneous.getBytesUri(ctx, uriIneFrontal,0);
                        Glide.with(ctx).load(uriIneFrontal).into(ivIneFrontal);
                        ibIneFrontal.setVisibility(View.GONE);
                        ivIneFrontal.setVisibility(View.VISIBLE);
                    }
                    break;
                case 2:
                    if (!row.getString(158).isEmpty() && row.getInt(160) == 1){
                        File ineReversoFile = new File(Constants.ROOT_PATH + "Documentos/"+row.getString(158));
                        Uri uriIneReverso = Uri.fromFile(ineReversoFile);
                        byteIneReverso = Miscellaneous.getBytesUri(ctx, uriIneReverso,0);
                        Glide.with(ctx).load(uriIneReverso).into(ivIneReverso);
                        ibIneReverso.setVisibility(View.GONE);
                        ivIneReverso.setVisibility(View.VISIBLE);
                    }
                    break;
                case 3:
                    if (!row.getString(158).isEmpty() && row.getInt(160) == 1){
                        File curpFile = new File(Constants.ROOT_PATH + "Documentos/"+row.getString(158));
                        Uri uriCurp = Uri.fromFile(curpFile);
                        byteCurp = Miscellaneous.getBytesUri(ctx, uriCurp,0);
                        Glide.with(ctx).load(uriCurp).into(ivCurp);
                        ibCurp.setVisibility(View.GONE);
                        ivCurp.setVisibility(View.VISIBLE);
                    }
                    break;
                case 4:
                    if (!row.getString(158).isEmpty() && row.getInt(160) == 1){
                        File comprobanteFile = new File(Constants.ROOT_PATH + "Documentos/"+row.getString(158));
                        Uri uriComprobante = Uri.fromFile(comprobanteFile);
                        byteComprobante = Miscellaneous.getBytesUri(ctx, uriComprobante,0);
                        Glide.with(ctx).load(uriComprobante).into(ivComprobante);
                        ibComprobante.setVisibility(View.GONE);
                        ivComprobante.setVisibility(View.VISIBLE);
                    }
                    break;
                case 6:
                    if (!row.getString(158).isEmpty() && row.getInt(160) == 1){
                        File firmaFile = new File(Constants.ROOT_PATH + "Firma/"+row.getString(158));
                        Uri uriFirma = Uri.fromFile(firmaFile);
                        byteFirmaAsesor = Miscellaneous.getBytesUri(ctx, uriFirma,0);
                        Glide.with(ctx).load(uriFirma).into(ivFirmaAsesor);
                        ibFirmaAsesor.setVisibility(View.GONE);
                        ivFirmaAsesor.setVisibility(View.VISIBLE);
                    }
                    break;
            }
            row.moveToNext();
        }

        if (!is_edit){
            tvPlazo.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked_left));
            tvFrecuencia.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked_right));
            tvFechaDesembolso.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
            tvHoraVisita.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
            etMontoPrestamo.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
            tvDestino.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));

            etNombreCli.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
            etApPaternoCli.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
            etApMaternoCli.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
            tvFechaNacCli.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
            for(int i = 0; i < rgGeneroCli.getChildCount(); i++){
                ((RadioButton) rgGeneroCli.getChildAt(i)).setEnabled(false);
            }
            tvEstadoNacCli.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
            etCurpIdCli.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked_right));
            tvOcupacionCli.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
            tvTipoIdentificacion.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
            etNumIdentifCli.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
            tvEstudiosCli.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
            tvEstadoCivilCli.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
            for(int i = 0; i < rgBienes.getChildCount(); i++){
                ((RadioButton) rgBienes.getChildAt(i)).setEnabled(false);
            }
            tvTipoCasaCli.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
            tvCasaFamiliar.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
            etOTroTipoCli.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
            etCalleCli.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
            etNoExtCli.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
            etNoIntCli.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
            etManzanaCli.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
            etLoteCli.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
            etCpCli.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
            tvColoniaCli.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
            etTelCasaCli.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
            etCelularCli.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
            etTelMensCli.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
            etTelTrabajoCli.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
            etTiempoSitio.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
            tvDependientes.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
            tvEnteroNosotros.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
            etEmail.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
            etReferenciCli.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));

            etNombreCony.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
            etApPaternoCony.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
            etApMaternoCony.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
            tvOcupacionCony.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
            etCelularCony.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));

            etPropiedadesEco.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
            etValorAproxEco.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
            etUbicacionEco.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
            etIngresoEco.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));

            etNombreNeg.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
            etCalleNeg.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
            etNoExtNeg.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
            etNoIntNeg.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
            etManzanaNeg.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
            etLoteNeg.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
            etCpNeg.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
            tvColoniaNeg.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
            tvActEconomicaNeg.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
            etAntiguedadNeg.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
            etIngMenNeg.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
            etOtrosIngNeg.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
            etGastosSemNeg.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
            etGastosAguaNeg.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
            etGastosLuzNeg.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
            etGastosTelNeg.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
            etGastosRentaNeg.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
            etGastosOtrosNeg.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
            tvDiasVentaNeg.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
            etReferenciNeg.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));

            etNombreAval.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
            etApPaternoAval.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
            etApMaternoAval.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
            tvFechaNacAval.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
            for(int i = 0; i < rgGeneroAval.getChildCount(); i++){
                ((RadioButton) rgGeneroAval.getChildAt(i)).setEnabled(false);
            }
            tvEstadoNacAval.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
            etCurpIdAval.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked_right));
            tvTipoIdentificacionAval.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
            etNumIdentifAval.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
            tvOcupacionAval.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
            etCalleAval.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
            etNoExtAval.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
            etNoIntAval.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
            etManzanaAval.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
            etLoteAval.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
            etCpAval.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
            tvColoniaAval.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
            tvTipoCasaAval.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
            tvParentescoAval.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
            etNombreTitularAval.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
            etIngMenAval.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
            etIngOtrosAval.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
            etGastosSemAval.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
            etGastosAguaAval.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
            etGastosLuzAval.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
            etGastosTelAval.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
            etGastosRentaAval.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
            etGastosOtrosAval.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
            tvHoraLocAval.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
            etAntiguedadAval.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
            etTelCasaAval.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
            etCelularAval.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
            etReferenciaAval.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));

            etNombreRef.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
            etApPaternoRef.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
            etApMaternoRef.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
            etCalleRef.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
            etCpRef.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
            tvColoniaRef.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
            etTelCelRef.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
        }
    }
}
