package com.sidert.sidertmovil.activities;

import android.Manifest;
import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.ColorDrawable;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.IntegerRes;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MultiAutoCompleteTextView;
import android.widget.ProgressBar;
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
import com.sidert.sidertmovil.database.DBhelper;
import com.sidert.sidertmovil.fragments.dialogs.dialog_date_picker;
import com.sidert.sidertmovil.fragments.dialogs.dialog_originacion_gpo;
import com.sidert.sidertmovil.fragments.dialogs.dialog_registro_cli;
import com.sidert.sidertmovil.fragments.dialogs.dialog_time_picker;
import com.sidert.sidertmovil.models.ModeloCatalogoGral;
import com.sidert.sidertmovil.utils.Constants;
import com.sidert.sidertmovil.utils.Miscellaneous;
import com.sidert.sidertmovil.utils.MyCurrentListener;
import com.sidert.sidertmovil.utils.NameFragments;
import com.sidert.sidertmovil.utils.NetworkStatus;
import com.sidert.sidertmovil.utils.Popups;
import com.sidert.sidertmovil.utils.SessionManager;
import com.sidert.sidertmovil.utils.Validator;
import com.sidert.sidertmovil.utils.ValidatorTextView;

import org.json.JSONObject;

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

public class SolicitudCreditoInd extends AppCompatActivity implements dialog_registro_cli.OnCompleteListener{

    private Context ctx;
    private Context context;

    private SessionManager session;

    private String[] _dias_semana;
    private ArrayList<Integer> selectedItemsDias;

    private DBhelper dBhelper;
    private SQLiteDatabase db;

    private SimpleDateFormat sdf = new SimpleDateFormat(Constants.FORMAT_DATE_GNRAL);
    private Calendar myCalendar;

    private long id_solicitud = 0;
    private boolean is_create = true;

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

    //======== DATOS CRÉDITO  ==================
    private TextView tvPlazo;
    private Spinner spPlazo;
    private Spinner spFrecuencia;
    private TextView tvFechaDesembolso;
    private TextView tvDiaDesembolso;
    private TextView tvHoraVisita;
    private EditText etMontoPrestamo;
    private TextView tvCantidadLetra;
    private TextView tvDestino;
    private Spinner spDestino;
    private TextView tvFirmaCre;
    private ImageButton ibFirmaCre;
    private ImageView ivFirmaCre;
    public byte[] byteFirmaCre;
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
    private EditText etNumIdentifCli;
    private TextView tvEstudiosCli;
    private Spinner spEstudiosCli;
    private TextView tvEstadoCivilCli;
    private Spinner spEstadoCivilCli;
    private LinearLayout llBienes;
    private TextView tvBienes;
    private RadioGroup rgBienes;
    private TextView tvTipoCasaCli;
    private Spinner spTipoCasaCli;
    private LinearLayout llCasaFamiliar;
    private Spinner spCasaFamiliar;
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
    private Spinner spDependientes;
    private Spinner spEnteroNosotros;
    private EditText etEmail;
    private ImageButton ibFotoFachCli;
    private ImageView ivFotoFachCli;
    public byte[] byteFotoFachCli;
    private MultiAutoCompleteTextView etReferenciCli;
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
    private RadioGroup rgGeneroAval;
    private TextView tvEstadoNacAval;
    private TextView tvRfcAval;
    private TextView tvCurpAval;
    private EditText etCurpIdAval;
    private EditText etNumIdentifAval;
    private TextView tvOcupacionAval;
    private TextView tvActividadEcoAval;
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
    private Spinner spTipoCasaAval;
    private Spinner spParentescoAval;
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
    private ImageButton ibFotoFachAval;
    private ImageView ivFotoFachAval;
    public byte[] byteFotoFachAval;
    private MultiAutoCompleteTextView etReferenciaAval;
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
    //================= Image View  =====================
    private ImageView ivDown1;
    private ImageView ivDown2;
    private ImageView ivDown3;
    private ImageView ivDown4;
    private ImageView ivDown5;
    private ImageView ivDown6;
    private ImageView ivDown7;

    private ImageView ivUp1;
    private ImageView ivUp2;
    private ImageView ivUp3;
    private ImageView ivUp4;
    private ImageView ivUp5;
    private ImageView ivUp6;
    private ImageView ivUp7;
    //===================================================
    //===============  LINEAR LAYOUT  ====================
    private LinearLayout llDatosCredito;
    private LinearLayout llDatosPersonales;
    private LinearLayout llDatosConyuge;
    private LinearLayout llDatosEconomicos;
    private LinearLayout llDatosNegocio;
    private LinearLayout llDatosAval;
    private LinearLayout llDatosReferencia;

    private LinearLayout llCredito;
    private LinearLayout llPersonales;
    private LinearLayout llConyuge;
    private LinearLayout llPropiedades;
    private LinearLayout llEconomicos;
    private LinearLayout llNegocio;
    private LinearLayout llAval;
    private LinearLayout llReferencia;
    //====================================================

    private LocationManager locationManager;
    private MyCurrentListener locationListener;
    private GoogleMap mMapCli;
    private GoogleMap mMapNeg;
    private GoogleMap mMapAval;

    private Validator validator;
    private ValidatorTextView validatorTV;

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

        //=================================== DATOS CREDITO  =======================================
        tvPlazo             = findViewById(R.id.tvPlazo);
        spPlazo             = findViewById(R.id.spPlazo);
        spFrecuencia        = findViewById(R.id.spFrecuencia);
        tvFechaDesembolso   = findViewById(R.id.tvFechaDesembolso);
        tvDiaDesembolso     = findViewById(R.id.tvDiaDesembolso);
        tvHoraVisita        = findViewById(R.id.tvHoraVisita);
        etMontoPrestamo     = findViewById(R.id.etMontoPrestamo);
        tvCantidadLetra     = findViewById(R.id.tvCantidadLetra);
        tvDestino           = findViewById(R.id.tvDestino);
        spDestino           = findViewById(R.id.spDestino);
        tvFirmaCre          = findViewById(R.id.tvFirmaCre);
        ibFirmaCre          = findViewById(R.id.ibFirmaCre);
        ivFirmaCre          = findViewById(R.id.ivFirmaCre);
        //==========================================================================================
        //=================================  DATOS PERSONALES  =====================================
        etNombreCli         = findViewById(R.id.etNombreCli);
        etApPaternoCli      = findViewById(R.id.etApPaternoCli);
        etApMaternoCli      = findViewById(R.id.etApMaternoCli);
        tvFechaNacCli       = findViewById(R.id.tvFechaNacCli);
        tvEdadCli           = findViewById(R.id.tvEdadCli);
        tvGeneroCli         = findViewById(R.id.tvGeneroCli);
        rgGeneroCli         = findViewById(R.id.rgGeneroCli);
        tvEstadoNacCli      = findViewById(R.id.tvEstadoNacCli);
        tvRfcCli            = findViewById(R.id.tvRfcCli);
        tvCurpCli           = findViewById(R.id.tvCurpCli);
        etCurpIdCli         = findViewById(R.id.etCurpIdCli);
        tvOcupacionCli      = findViewById(R.id.tvOcupacionCli);
        tvActividadEcoCli   = findViewById(R.id.tvActividadEcoCli);
        etNumIdentifCli     = findViewById(R.id.etNumIdentifCli);
        tvEstudiosCli       = findViewById(R.id.tvEstudiosCli);
        spEstudiosCli       = findViewById(R.id.spEstudiosCli);
        tvEstadoCivilCli    = findViewById(R.id.tvEstadoCivilCli);
        spEstadoCivilCli    = findViewById(R.id.spEstadoCivilCli);
        llBienes            = findViewById(R.id.llBienes);
        tvBienes            = findViewById(R.id.tvBienes);
        rgBienes            = findViewById(R.id.rgBienes);
        tvTipoCasaCli       = findViewById(R.id.tvTipoCasaCli);
        spTipoCasaCli       = findViewById(R.id.spTipoCasaCli);
        llCasaFamiliar      = findViewById(R.id.llCasaFamiliar);
        spCasaFamiliar      = findViewById(R.id.spCasaFamiliar);
        llCasaOtroCli       = findViewById(R.id.llCasaOtro);
        etOTroTipoCli       = findViewById(R.id.etOtroTipoCli);
        tvMapaCli           = findViewById(R.id.tvMapaCli);
        ibMapCli            = findViewById(R.id.ibMapCli);
        pbLoadCli           = findViewById(R.id.pbLoadCli);
        mapCli              = findViewById(R.id.mapCli);
        etCalleCli          = findViewById(R.id.etCalleCli);
        etNoExtCli          = findViewById(R.id.etNoExtCli);
        etManzanaCli        = findViewById(R.id.etManzanaCli);
        etNoIntCli          = findViewById(R.id.etNoIntCli);
        etLoteCli           = findViewById(R.id.etLoteCli);
        etCpCli             = findViewById(R.id.etCpCli);
        tvColoniaCli        = findViewById(R.id.tvColoniaCli);
        tvMunicipioCli      = findViewById(R.id.tvMunicipioCli);
        tvEstadoCli         = findViewById(R.id.tvEstadoCli);
        etTelCasaCli        = findViewById(R.id.etTelCasaCli);
        etCelularCli        = findViewById(R.id.etCelularCli);
        etTelMensCli        = findViewById(R.id.etTelMensCli);
        etTelTrabajoCli     = findViewById(R.id.etTelTrabajoCli);
        etTiempoSitio       = findViewById(R.id.etTiempoSitio);
        spDependientes      = findViewById(R.id.spDependientes);
        spEnteroNosotros    = findViewById(R.id.spEnteroNosotros);
        etEmail             = findViewById(R.id.etEmail);
        ibFotoFachCli       = findViewById(R.id.ibFotoFachCli);
        ivFotoFachCli       = findViewById(R.id.ivFotoFachCli);
        etReferenciCli      = findViewById(R.id.etReferenciaCli);
        ibFirmaCli          = findViewById(R.id.ibFirmaCli);
        ivFirmaCli          = findViewById(R.id.ivFirmaCli);
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
        etGastosSemNeg       = findViewById(R.id.etGastosSemNeg);
        etGastosAguaNeg     = findViewById(R.id.etGastosAguaNeg);
        etGastosLuzNeg      = findViewById(R.id.etGastosLuzNeg);
        etGastosTelNeg      = findViewById(R.id.etGastosTelNeg);
        etGastosRentaNeg    = findViewById(R.id.etGastosRentaNeg);
        etGastosOtrosNeg    = findViewById(R.id.etGastosOtrosNeg);
        tvCapacidadPagoNeg  = findViewById(R.id.tvCapacidadPagoNeg);
        tvDiasVentaNeg      = findViewById(R.id.tvDiasVentaNeg);
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
        rgGeneroAval        = findViewById(R.id.rgGeneroAval);
        tvEstadoNacAval     = findViewById(R.id.tvEstadoNacAval);
        tvRfcAval           = findViewById(R.id.tvRfcAval);
        tvCurpAval          = findViewById(R.id.tvCurpAval);
        etCurpIdAval        = findViewById(R.id.etCurpIdAval);
        etNumIdentifAval    = findViewById(R.id.etNumIdentifAval);
        tvOcupacionAval     = findViewById(R.id.tvOcupacionAval);
        tvActividadEcoAval  = findViewById(R.id.tvActividadEcoAval);
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
        spTipoCasaAval      = findViewById(R.id.spTipoCasaAval);
        spParentescoAval    = findViewById(R.id.spFamiliar);
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
        ibFotoFachAval      = findViewById(R.id.ibFotoFachAval);
        ivFotoFachAval      = findViewById(R.id.ivFotoFachAval);
        etReferenciaAval    = findViewById(R.id.etReferenciaAval);
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

        //================ LINEAR LAYOUT  =========================
        llDatosCredito      = findViewById(R.id.llDatosCredito);
        llDatosPersonales   = findViewById(R.id.llDatosPersonales);
        llDatosConyuge      = findViewById(R.id.llDatosConyuge);
        llDatosEconomicos   = findViewById(R.id.llDatosEconomicos);
        llDatosNegocio      = findViewById(R.id.llDatosNegocio);
        llDatosAval         = findViewById(R.id.llDatosAval);
        llDatosReferencia   = findViewById(R.id.llDatosReferencia);

        llCredito           = findViewById(R.id.llCredito);
        llPersonales        = findViewById(R.id.llPersonales);
        llConyuge           = findViewById(R.id.llConyuge);
        llPropiedades       = findViewById(R.id.llPropiedades);
        llEconomicos        = findViewById(R.id.llEconomicos);
        llNegocio           = findViewById(R.id.llNegocio);
        llAval              = findViewById(R.id.llAval);
        llReferencia        = findViewById(R.id.llReferencia);
        //=========================================================

        //============================== LINEAR LAYOUT  ============================================
        llCredito.setOnClickListener(llCredito_OnClick);
        llPersonales.setOnClickListener(llPersonales_OnClick);
        llConyuge.setOnClickListener(llConyuge_OnClick);
        llEconomicos.setOnClickListener(llEconomicos_OnClick);
        llNegocio.setOnClickListener(llNegocio_OnClick);
        llAval.setOnClickListener(llAval_OnClick);
        llReferencia.setOnClickListener(llReferencia_OnClick);

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
        //==========================================================================================
        //============================ IMAGE VIEW UP|DOWN  =========================================
        ivDown1 = findViewById(R.id.ivDown1);
        ivDown2 = findViewById(R.id.ivDown2);
        ivDown3 = findViewById(R.id.ivDown3);
        ivDown4 = findViewById(R.id.ivDown4);
        ivDown5 = findViewById(R.id.ivDown5);
        ivDown6 = findViewById(R.id.ivDown6);
        ivDown7 = findViewById(R.id.ivDown7);

        ivUp1 = findViewById(R.id.ivUp1);
        ivUp2 = findViewById(R.id.ivUp2);
        ivUp3 = findViewById(R.id.ivUp3);
        ivUp4 = findViewById(R.id.ivUp4);
        ivUp5 = findViewById(R.id.ivUp5);
        ivUp6 = findViewById(R.id.ivUp6);
        ivUp7 = findViewById(R.id.ivUp7);
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
        //================================  CREDITO LISTENER =======================================
        spPlazo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                view.requestFocus();
                ContentValues cv = new ContentValues();
                cv.put("plazo", spPlazo.getSelectedItemPosition());
                db.update("datos_credito_ind_t", cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spFrecuencia.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                view.requestFocus();
                ContentValues cv = new ContentValues();
                cv.put("periodicidad", spFrecuencia.getSelectedItemPosition());
                db.update("datos_credito_ind_t", cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        tvFechaDesembolso.setOnClickListener(tvFechaDesembolso_OnClick);
        tvHoraVisita.setOnClickListener(tvHoraVisita_OnClick);
        ibFirmaCre.setOnClickListener(ibFirmaCre_OnClick);
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
                        db.update("datos_credito_ind_t", cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
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
                        db.update("datos_credito_ind_t", cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                    }
                }
            }
        });
        spDestino.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                ContentValues cv = new ContentValues();
                cv.put("destino", spDestino.getSelectedItemPosition());
                db.update("datos_credito_ind_t", cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                etMontoPrestamo.clearFocus();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
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
                        db.update("datos_cliente_ind_t", cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
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
                                db.update("datos_cliente_ind_t", cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
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
        etNumIdentifCli.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    if (!etNumIdentifCli.getText().toString().trim().isEmpty()){
                        ContentValues cv = new ContentValues();
                        cv.put("no_identificacion", etNumIdentifCli.getText().toString().trim().toUpperCase());
                        db.update("datos_cliente_ind_t", cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                    }
                    else
                        etNumIdentifCli.setError("Este campo es requerido");
                }
            }
        });
        spEstudiosCli.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                view.requestFocus();
                ContentValues cv = new ContentValues();
                cv.put("nivel_estudio", position);
                db.update("datos_cliente_ind_t", cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spEstadoCivilCli.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                view.requestFocus();
                ContentValues cv = new ContentValues();
                cv.put("estado_civil", position);
                db.update("datos_cliente_ind_t", cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                if (position == 2) {
                    llBienes.setVisibility(View.VISIBLE);
                    llConyuge.setVisibility(View.VISIBLE);
                }
                else if (position == 5)
                    llConyuge.setVisibility(View.VISIBLE);
                else {
                    llConyuge.setVisibility(View.GONE);
                    llBienes.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spTipoCasaCli.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                view.requestFocus();
                ContentValues cv = new ContentValues();
                cv.put("tipo_vivienda", position);
                db.update("datos_cliente_ind_t", cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                switch (position){
                    case 3:
                        llCasaFamiliar.setVisibility(View.VISIBLE);
                        llCasaOtroCli.setVisibility(View.GONE);
                        break;
                    case 4:
                        llCasaOtroCli.setVisibility(View.VISIBLE);
                        llCasaFamiliar.setVisibility(View.GONE);
                        break;
                    default:
                        llCasaFamiliar.setVisibility(View.GONE);
                        llCasaOtroCli.setVisibility(View.GONE);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spCasaFamiliar.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                view.requestFocus();
                ContentValues cv = new ContentValues();
                cv.put("parentesco", position);
                db.update("datos_cliente_ind_t", cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        etOTroTipoCli.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    if (!validator.validate(etOTroTipoCli, new String[]{validator.REQUIRED})){
                        ContentValues cv = new ContentValues();
                        cv.put("otro_tipo_vivienda", etOTroTipoCli.getText().toString().trim().toUpperCase());
                        db.update("datos_cliente_ind_t", cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
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
                        db.update("datos_cliente_ind_t", cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
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
                        db.update("datos_cliente_ind_t", cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
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
                        db.update("datos_cliente_ind_t", cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
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
                    db.update("datos_cliente_ind_t", cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});

                }
            }
        });
        etLoteCli.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    ContentValues cv = new ContentValues();
                    cv.put("lote", etLoteCli.getText().toString().trim().toUpperCase());
                    db.update("datos_cliente_ind_t", cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});

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
                        db.update("datos_cliente_ind_t", cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
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
                        db.update("datos_cliente_ind_t", cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
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
                        db.update("datos_cliente_ind_t", cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
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
                        db.update("datos_cliente_ind_t", cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
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
                        db.update("datos_cliente_ind_t", cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
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
                    if (Integer.parseInt(etTiempoSitio.getText().toString().trim()) > 0) {
                        ContentValues cv = new ContentValues();
                        cv.put("tiempo_vivir_sitio", etTiempoSitio.getText().toString().trim());
                        db.update("datos_cliente_ind_t", cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                    }
                    else{
                        etTiempoSitio.setError("No se permiten cantidades iguales a cero");
                    }
                }
            }
        });
        spDependientes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                ContentValues cv = new ContentValues();
                cv.put("dependientes", spDependientes.getSelectedItemPosition());
                db.update("datos_cliente_ind_t", cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spEnteroNosotros.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spEnteroNosotros.setFocusable(true);
                ContentValues cv = new ContentValues();
                cv.put("medio_contacto", spEnteroNosotros.getSelectedItemPosition());
                db.update("datos_cliente_ind_t", cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        etEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    ContentValues cv = new ContentValues();
                    cv.put("email", etEmail.getText().toString().trim().toUpperCase());
                    db.update("datos_cliente_ind_t", cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
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
                        db.update("datos_cliente_ind_t", cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
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
                    if (spEstadoCivilCli.getSelectedItemPosition() == 2 || spEstadoCivilCli.getSelectedItemPosition() == 5) {
                        if (!validator.validate(etNombreCony, new String[]{validator.REQUIRED, validator.ONLY_TEXT})){
                            ContentValues cv = new ContentValues();
                            cv.put("nombre", etNombreCony.getText().toString().trim().toUpperCase());
                            db.update("datos_conyuge_ind_t", cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                        }
                    }
                }
            }
        });
        etApPaternoCony.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    if (spEstadoCivilCli.getSelectedItemPosition() == 2 || spEstadoCivilCli.getSelectedItemPosition() == 5) {
                        if (!validator.validate(etApPaternoCony, new String[]{validator.REQUIRED, validator.ONLY_TEXT})){
                            ContentValues cv = new ContentValues();
                            cv.put("paterno", etApPaternoCony.getText().toString().trim().toUpperCase());
                            db.update("datos_conyuge_ind_t", cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                        }
                    }
                }
            }
        });
        etApMaternoCony.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    if (spEstadoCivilCli.getSelectedItemPosition() == 2 || spEstadoCivilCli.getSelectedItemPosition() == 5) {
                        if (!validator.validate(etApMaternoCony, new String[]{validator.ONLY_TEXT})){
                            ContentValues cv = new ContentValues();
                            cv.put("materno", etApMaternoCony.getText().toString().trim().toUpperCase());
                            db.update("datos_conyuge_ind_t", cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
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
                        db.update("datos_conyuge_ind_t", cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
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
                            db.update("datos_economicos_ind_t", cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
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
                            db.update("datos_economicos_ind_t", cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
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
                            db.update("datos_economicos_ind_t", cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
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
                            db.update("datos_economicos_ind_t", cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
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
                        db.update("datos_negocio_ind_t", cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
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
                        db.update("datos_negocio_ind_t", cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
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
                        db.update("datos_negocio_ind_t", cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                    }
                }
            }
        });
        etNoIntNeg.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    if (!validator.validate(etNoIntNeg, new String[]{validator.REQUIRED})){
                        ContentValues cv = new ContentValues();
                        cv.put("no_interior", etNoIntNeg.getText().toString().trim().toUpperCase());
                        db.update("datos_negocio_ind_t", cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                    }
                }
            }
        });
        etManzanaNeg.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    ContentValues cv = new ContentValues();
                    cv.put("manzana", etManzanaNeg.getText().toString().trim().toUpperCase());
                    db.update("datos_negocio_ind_t", cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});

                }
            }
        });
        etLoteNeg.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    ContentValues cv = new ContentValues();
                    cv.put("lote", etLoteNeg.getText().toString().trim().toUpperCase());
                    db.update("datos_negocio_ind_t", cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});

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
                        db.update("datos_negocio_ind_t", cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
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
                            db.update("datos_negocio_ind_t", cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
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
                    db.update("datos_negocio_ind_t", cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
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
                    db.update("datos_negocio_ind_t", cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
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
                    db.update("datos_negocio_ind_t", cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
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
                    db.update("datos_negocio_ind_t", cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
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
                    db.update("datos_negocio_ind_t", cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
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
                    db.update("datos_negocio_ind_t", cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
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
                    db.update("datos_negocio_ind_t", cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
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
                    db.update("datos_negocio_ind_t", cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
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
                        db.update("datos_negocio_ind_t", cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                    }
                    else
                        etReferenciCli.setError("Este campo es requerido");
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
                        db.update("datos_aval_ind_t", cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
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
                        db.update("datos_aval_ind_t", cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
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
                    db.update("datos_aval_ind_t", cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
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
                        db.update("datos_aval_ind_t", cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
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
                                db.update("datos_aval_ind_t", cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
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
        etNumIdentifAval.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    if (!etNumIdentifAval.getText().toString().trim().isEmpty()){
                        ContentValues cv = new ContentValues();
                        cv.put("no_identificacion", etNumIdentifAval.getText().toString().trim().toUpperCase());
                        db.update("datos_aval_ind_t", cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
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
                        db.update("datos_aval_ind_t", cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
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
                        db.update("datos_aval_ind_t", cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
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
                        db.update("datos_aval_ind_t", cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
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
                    db.update("datos_aval_ind_t", cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});

                }
            }
        });
        etLoteAval.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    ContentValues cv = new ContentValues();
                    cv.put("lote", etLoteAval.getText().toString().trim().toUpperCase());
                    db.update("datos_aval_ind_t", cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});

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
                        db.update("datos_aval_ind_t", cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
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
        spTipoCasaAval.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                ContentValues cv = new ContentValues();
                cv.put("tipo_vivienda", spTipoCasaAval.getSelectedItemPosition());
                db.update("datos_aval_ind_t", cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                switch (position){
                    case 1: //PROPIA
                        llNombreTitular.setVisibility(View.GONE);
                        llParentescoFamAval.setVisibility(View.GONE);
                        break;
                    case 2://FAMILIAR
                        llNombreTitular.setVisibility(View.VISIBLE);
                        llParentescoFamAval.setVisibility(View.VISIBLE);
                        break;
                    default://NINGUNO
                        llNombreTitular.setVisibility(View.GONE);
                        llParentescoFamAval.setVisibility(View.GONE);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spParentescoAval.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                ContentValues cv = new ContentValues();
                cv.put("parentesco", spParentescoAval.getSelectedItemPosition());
                db.update("datos_aval_ind_t", cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        etNombreTitularAval.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    if (spTipoCasaAval.getSelectedItemPosition() == 2){
                        if (!etNombreTitularAval.getText().toString().trim().isEmpty()) {
                            ContentValues cv = new ContentValues();
                            cv.put("nombre_titular", etNombreTitularAval.getText().toString().trim().toUpperCase());
                            db.update("datos_aval_ind_t", cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
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
                        db.update("datos_aval_ind_t", cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
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
                        db.update("datos_aval_ind_t", cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
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
                        db.update("datos_aval_ind_t", cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
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
                        db.update("datos_aval_ind_t", cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
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
                        db.update("datos_aval_ind_t", cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
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
                        db.update("datos_aval_ind_t", cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
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
                        db.update("datos_aval_ind_t", cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
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
                        db.update("datos_aval_ind_t", cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
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
                            db.update("datos_aval_ind_t", cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
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
                        db.update("datos_aval_ind_t", cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
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
                        db.update("datos_aval_ind_t", cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
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
                        db.update("datos_aval_ind_t", cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
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
                        db.update("datos_referencia_ind_t", cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
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
                        db.update("datos_referencia_ind_t", cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
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
                    db.update("datos_referencia_ind_t", cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
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
                        db.update("datos_referencia_ind_t", cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
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
                        db.update("datos_referencia_ind_t", cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
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
                        db.update("datos_referencia_ind_t", cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                    }
                    else
                        etTelCelRef.setError(ctx.getResources().getString(R.string.mensaje_telefono));
                }
                else
                    etTelCelRef.setError(null);
            }
        });
        //================================  CLIENTE GENERO LISTENER  ===============================
        rgGeneroCli.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                ContentValues cv = null;
                HashMap<Integer, String> params = new HashMap<>();
                if (checkedId == R.id.rbHombre){

                    params.put(0, etNombreCli.getText().toString());
                    params.put(1, etApPaternoCli.getText().toString());
                    params.put(2, etApMaternoCli.getText().toString());
                    params.put(3, tvFechaNacCli.getText().toString());
                    params.put(4, "Hombre");

                    if (!tvEstadoNacCli.getText().toString().trim().isEmpty())
                        params.put(5, tvEstadoNacCli.getText().toString().trim());
                    else
                        params.put(5,"");
                    tvCurpCli.setText(Miscellaneous.GenerarCurp(params));
                    cv = new ContentValues();
                    cv.put("genero","0");
                    db.update("datos_cliente_ind_t", cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                }
                else if(checkedId == R.id.rbMujer){
                    params.put(0, etNombreCli.getText().toString());
                    params.put(1, etApPaternoCli.getText().toString());
                    params.put(2, etApMaternoCli.getText().toString());
                    params.put(3, tvFechaNacCli.getText().toString());
                    params.put(4, "Mujer");

                    if (!tvEstadoNacCli.getText().toString().trim().isEmpty())
                        params.put(5, tvEstadoNacCli.getText().toString().trim());
                    else
                        params.put(5,"");
                    tvCurpCli.setText(Miscellaneous.GenerarCurp(params));
                    cv = new ContentValues();
                    cv.put("genero","1");
                    db.update("datos_cliente_ind_t", cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
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
                        params.put(5,"");
                    tvCurpCli.setText(Miscellaneous.GenerarCurp(params));

                    cv = new ContentValues();
                    cv.put("genero","2");
                    db.update("datos_cliente_ind_t", cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                }
            }
        });
        rgBienes.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                ContentValues cv = new ContentValues();
                switch (checkedId){
                    case R.id.rbMancomunados:
                        cv.put("bienes","1");
                        db.update("datos_cliente_ind_t", cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                        break;
                    case R.id.rbSeparados:
                        cv.put("bienes","2");
                        db.update("datos_cliente_ind_t", cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                        break;
                }
            }
        });
        //===========================  AVAL GENERO LISTENER  =======================================
        rgGeneroAval.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                ContentValues cv;
                if (checkedId == R.id.rbHombre){
                    HashMap<Integer, String> params = new HashMap<>();
                    params.put(0, etNombreAval.getText().toString());
                    params.put(1, etApPaternoAval.getText().toString());
                    params.put(2, etApMaternoAval.getText().toString());
                    params.put(3, tvFechaNacAval.getText().toString());
                    params.put(4, "Hombre");

                    if (!tvEstadoNacAval.getText().toString().trim().isEmpty())
                        params.put(5, tvEstadoNacAval.getText().toString().trim());
                    else
                        params.put(5,"");
                    cv = new ContentValues();
                    cv.put("genero","0");
                    db.update("datos_aval_ind_t", cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                    tvCurpAval.setText(Miscellaneous.GenerarCurp(params));
                }
                else if(checkedId == R.id.rbMujer){
                    HashMap<Integer, String> params = new HashMap<>();
                    params.put(0, etNombreAval.getText().toString());
                    params.put(1, etApPaternoAval.getText().toString());
                    params.put(2, etApMaternoAval.getText().toString());
                    params.put(3, tvFechaNacAval.getText().toString());
                    params.put(4, "Mujer");

                    if (!tvEstadoNacAval.getText().toString().trim().isEmpty())
                        params.put(5, tvEstadoNacAval.getText().toString().trim());
                    else
                        params.put(5,"");

                    cv = new ContentValues();
                    cv.put("genero","1");
                    db.update("datos_aval_ind_t", cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
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
                        params.put(5,"");

                    cv = new ContentValues();
                    cv.put("genero","2");
                    db.update("datos_aval_ind_t", cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                    tvCurpAval.setText(Miscellaneous.GenerarCurp(params));
                }
            }
        });

    }

    private View.OnClickListener ibFirmaCre_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i_firma_cre = new Intent(context, CapturarFirma.class);
            i_firma_cre.putExtra(Constants.TIPO,"");
            startActivityForResult(i_firma_cre,Constants.REQUEST_CODE_FIRMA_CRE);
        }
    };

    private View.OnClickListener tvFechaDesembolso_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            dialog_date_picker dialogDatePicker = new dialog_date_picker();
            Bundle b = new Bundle();

            b.putInt(Constants.YEAR_CURRENT,myCalendar.get(Calendar.YEAR));
            b.putInt(Constants.MONTH_CURRENT,myCalendar.get(Calendar.MONTH));
            b.putInt(Constants.DAY_CURRENT,myCalendar.get(Calendar.DAY_OF_MONTH));
            b.putString(Constants.DATE_CURRENT,sdf.format(myCalendar.getTime()));
            b.putInt(Constants.IDENTIFIER,1);
            b.putBoolean(Constants.FECHAS_POST, true);
            dialogDatePicker.setArguments(b);
            dialogDatePicker.show(getSupportFragmentManager(), NameFragments.DIALOGDATEPICKER);
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
            Intent i_estados = new Intent(context, Catalogos.class);
            i_estados.putExtra(Constants.TITULO,Miscellaneous.ucFirst(Constants.ESTADOS));
            i_estados.putExtra(Constants.CATALOGO,Constants.ESTADOS);
            i_estados.putExtra(Constants.REQUEST_CODE, Constants.REQUEST_CODE_ESTADO_NAC);
            startActivityForResult(i_estados,Constants.REQUEST_CODE_ESTADO_NAC);
        }
    };

    private View.OnClickListener etColonia_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i_colonia = new Intent(context, Catalogos.class);
            i_colonia.putExtra(Constants.TITULO,Miscellaneous.ucFirst(Constants.ESTADOS));
            i_colonia.putExtra(Constants.CATALOGO,Constants.COLONIAS);
            i_colonia.putExtra(Constants.EXTRA, etCpCli.getText().toString().trim());
            i_colonia.putExtra(Constants.REQUEST_CODE, Constants.REQUEST_CODE_COLONIA_CLIE);
            startActivityForResult(i_colonia,Constants.REQUEST_CODE_COLONIA_CLIE);
        }
    };

    private View.OnClickListener tvColoniaAval_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i_colonia = new Intent(context, Catalogos.class);
            i_colonia.putExtra(Constants.TITULO,Miscellaneous.ucFirst(Constants.ESTADOS));
            i_colonia.putExtra(Constants.CATALOGO,Constants.COLONIAS);
            i_colonia.putExtra(Constants.EXTRA, etCpAval.getText().toString().trim());
            i_colonia.putExtra(Constants.REQUEST_CODE, Constants.REQUEST_CODE_COLONIA_AVAL);
            startActivityForResult(i_colonia,Constants.REQUEST_CODE_COLONIA_AVAL);
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
            showDiasSemana();
        }
    };

    private View.OnClickListener etColoniaAct_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i_colonia = new Intent(context, Catalogos.class);
            i_colonia.putExtra(Constants.TITULO,Miscellaneous.ucFirst(Constants.COLONIAS));
            i_colonia.putExtra(Constants.CATALOGO,Constants.COLONIAS);
            i_colonia.putExtra(Constants.EXTRA, etCpNeg.getText().toString().trim());
            i_colonia.putExtra(Constants.REQUEST_CODE, Constants.REQUEST_CODE_COLONIA_NEG);
            startActivityForResult(i_colonia,Constants.REQUEST_CODE_COLONIA_NEG);
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
            Intent i_ocupaciones = new Intent(context, Catalogos.class);
            i_ocupaciones.putExtra(Constants.TITULO,Miscellaneous.ucFirst(Constants.OCUPACIONES));
            i_ocupaciones.putExtra(Constants.CATALOGO,Constants.OCUPACIONES);
            i_ocupaciones.putExtra(Constants.REQUEST_CODE, Constants.REQUEST_CODE_OCUPACION_CLIE);
            startActivityForResult(i_ocupaciones,Constants.REQUEST_CODE_OCUPACION_CLIE);
        }
    };

    private View.OnClickListener tvOcupacionConyuge_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i_ocupaciones = new Intent(context, Catalogos.class);
            i_ocupaciones.putExtra(Constants.TITULO,Miscellaneous.ucFirst(Constants.OCUPACIONES));
            i_ocupaciones.putExtra(Constants.CATALOGO,Constants.OCUPACIONES);
            i_ocupaciones.putExtra(Constants.REQUEST_CODE, Constants.REQUEST_CODE_OCUPACION_CONY);
            startActivityForResult(i_ocupaciones,Constants.REQUEST_CODE_OCUPACION_CONY);
        }
    };

    private View.OnClickListener etActividadEco_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i_ocupaciones = new Intent(context, Catalogos.class);
            i_ocupaciones.putExtra(Constants.TITULO,Miscellaneous.ucFirst(Constants.SECTORES));
            i_ocupaciones.putExtra(Constants.CATALOGO,Constants.SECTORES);
            i_ocupaciones.putExtra(Constants.REQUEST_CODE, Constants.REQUEST_CODE_ACTIVIDAD_NEG);
            startActivityForResult(i_ocupaciones,Constants.REQUEST_CODE_ACTIVIDAD_NEG);
        }
    };

    //============ REFERNCIA =================================================================
    private View.OnClickListener tvColoniaRef_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i_colonia = new Intent(context, Catalogos.class);
            i_colonia.putExtra(Constants.TITULO,Miscellaneous.ucFirst(Constants.COLONIAS));
            i_colonia.putExtra(Constants.CATALOGO,Constants.COLONIAS);
            i_colonia.putExtra(Constants.EXTRA, etCpRef.getText().toString().trim());
            i_colonia.putExtra(Constants.REQUEST_CODE, Constants.REQUEST_CODE_COLONIA_REF);
            startActivityForResult(i_colonia,Constants.REQUEST_CODE_COLONIA_REF);
        }
    };

    //============ AVAL =====================
    private View.OnClickListener tvFechaNacAval_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            dialog_date_picker dialogDatePicker = new dialog_date_picker();
            Bundle b = new Bundle();

            b.putInt(Constants.YEAR_CURRENT,myCalendar.get(Calendar.YEAR));
            b.putInt(Constants.MONTH_CURRENT,myCalendar.get(Calendar.MONTH));
            b.putInt(Constants.DAY_CURRENT,myCalendar.get(Calendar.DAY_OF_MONTH));
            b.putString(Constants.DATE_CURRENT,sdf.format(myCalendar.getTime()));
            b.putInt(Constants.IDENTIFIER,3);
            b.putBoolean(Constants.FECHAS_POST, false);
            dialogDatePicker.setArguments(b);
            dialogDatePicker.show(getSupportFragmentManager(), NameFragments.DIALOGDATEPICKER);
        }
    };

    private View.OnClickListener tvEstadoNacAval_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i_estados = new Intent(context, Catalogos.class);
            i_estados.putExtra(Constants.TITULO,Miscellaneous.ucFirst(Constants.ESTADOS));
            i_estados.putExtra(Constants.CATALOGO,Constants.ESTADOS);
            i_estados.putExtra(Constants.REQUEST_CODE, Constants.REQUEST_CODE_ESTADO_NAC_AVAL);
            startActivityForResult(i_estados,Constants.REQUEST_CODE_ESTADO_NAC_AVAL);
        }
    };

    private View.OnClickListener tvOcupacionAval_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i_ocupaciones = new Intent(context, Catalogos.class);
            i_ocupaciones.putExtra(Constants.TITULO,Miscellaneous.ucFirst(Constants.OCUPACIONES));
            i_ocupaciones.putExtra(Constants.CATALOGO,Constants.OCUPACIONES);
            i_ocupaciones.putExtra(Constants.REQUEST_CODE, Constants.REQUEST_CODE_OCUPACION_AVAL);
            startActivityForResult(i_ocupaciones,Constants.REQUEST_CODE_OCUPACION_AVAL);
        }
    };

    private View.OnClickListener tvHoraLocAval_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            dialog_time_picker timePicker = new dialog_time_picker();
            Bundle b = new Bundle();
            b.putInt(Constants.IDENTIFIER,2);
            timePicker.setArguments(b);
            timePicker.show(getSupportFragmentManager(), NameFragments.DIALOGTIMEPICKER);
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

    //==================================================================

    //Continuar
    private View.OnClickListener btnContinuar0_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            saveDatosCredito();
            /*ivDown2.setVisibility(View.GONE);
            ivUp2.setVisibility(View.VISIBLE);
            llDatosPersonales.setVisibility(View.VISIBLE);

            ivDown1.setVisibility(View.VISIBLE);
            ivUp1.setVisibility(View.GONE);
            llDatosCredito.setVisibility(View.GONE);

            etNombreCli.requestFocus();*/

        }
    };
    private View.OnClickListener btnContinuar1_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //saveDatosPersonales();
            if (spEstadoCivilCli.getSelectedItemPosition() == 2 || spEstadoCivilCli.getSelectedItemPosition() == 5) {
                etNombreCony.requestFocus();
                ivDown3.setVisibility(View.GONE);
                ivUp3.setVisibility(View.VISIBLE);
                llDatosConyuge.setVisibility(View.VISIBLE);
            }
            else{
                if (!etMontoPrestamo.getText().toString().trim().isEmpty() && Integer.parseInt(etMontoPrestamo.getText().toString().trim()) > 30000) {
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
            if (!etMontoPrestamo.getText().toString().trim().isEmpty() && Integer.parseInt(etMontoPrestamo.getText().toString().trim()) > 30000) {
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
            Toast.makeText(ctx,"Termina proceso", Toast.LENGTH_SHORT).show();

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
            if (spEstadoCivilCli.getSelectedItemPosition() == 2 || spEstadoCivilCli.getSelectedItemPosition() == 5) {
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
            if (!etMontoPrestamo.getText().toString().trim().isEmpty() && Integer.parseInt(etMontoPrestamo.getText().toString().trim()) > 30000) {
                ivDown4.setVisibility(View.GONE);
                ivUp4.setVisibility(View.VISIBLE);
                llDatosEconomicos.setVisibility(View.VISIBLE);
                etPropiedadesEco.requestFocus();
            }
            else{
                if (spEstadoCivilCli.getSelectedItemPosition() == 2 || spEstadoCivilCli.getSelectedItemPosition() == 5) {
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


    private View.OnClickListener tvHoraVisita_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            dialog_time_picker timePicker = new dialog_time_picker();
            Bundle b = new Bundle();
            b.putInt(Constants.IDENTIFIER,1);
            timePicker.setArguments(b);
            timePicker.show(getSupportFragmentManager(), NameFragments.DIALOGTIMEPICKER);
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
                        db.update("datos_cliente_ind_t", cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                    }
                }
                break;
            case Constants.REQUEST_CODE_OCUPACION_CLIE:
                if (resultCode == Constants.REQUEST_CODE_OCUPACION_CLIE){
                    if (data != null){
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
                        db.update("datos_cliente_ind_t", cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                    }
                }
                break;
            case Constants.REQUEST_CODE_OCUPACION_CONY:
                if (resultCode == Constants.REQUEST_CODE_OCUPACION_CONY){
                    if (data != null){
                        tvOcupacionCony.setText(((ModeloCatalogoGral)data.getSerializableExtra(Constants.ITEM)).getNombre());
                        cv = new ContentValues();
                        cv.put("ocupacion", tvOcupacionCony.getText().toString().trim().toUpperCase());
                        db.update("datos_conyuge_ind_t", cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                    }
                }
                break;
            case Constants.REQUEST_CODE_ACTIVIDAD_NEG:
                if (resultCode == Constants.REQUEST_CODE_ACTIVIDAD_NEG){
                    if (data != null){
                        tvActEconomicaNeg.setText(((ModeloCatalogoGral)data.getSerializableExtra(Constants.ITEM)).getNombre());
                        cv = new ContentValues();
                        cv.put("actividad_economica", tvActEconomicaNeg.getText().toString().trim().toUpperCase());
                        db.update("datos_negocio_ind_t", cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                    }
                }
                break;
            case Constants.REQUEST_CODE_ESTADO_NAC_AVAL:
                if (resultCode == Constants.REQUEST_CODE_ESTADO_NAC_AVAL){
                    if (data != null){
                        tvEstadoNacAval.setText(((ModeloCatalogoGral)data.getSerializableExtra(Constants.ITEM)).getNombre());
                        cv = new ContentValues();
                        cv.put("estado_nacimiento", tvEstadoNacAval.getText().toString().trim().toUpperCase());
                        db.update("datos_aval_ind_t", cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
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
                    }
                }
                break;
            case Constants.REQUEST_CODE_OCUPACION_AVAL:
                if (resultCode == Constants.REQUEST_CODE_OCUPACION_AVAL){
                    if (data != null){
                        tvOcupacionAval.setText(((ModeloCatalogoGral)data.getSerializableExtra(Constants.ITEM)).getNombre());
                        Cursor row = dBhelper.getRecords(Constants.SECTORES, " WHERE sector_id = " + (((ModeloCatalogoGral)data.getSerializableExtra(Constants.ITEM)).getExtra())+"","",null);
                        if (row.getCount() > 0){
                            row.moveToFirst();
                            tvActividadEcoAval.setText(row.getString(2));
                            cv = new ContentValues();
                            cv.put("ocupacion", tvOcupacionAval.getText().toString().trim().toUpperCase());
                            cv.put("actividad_economica", tvActividadEcoAval.getText().toString().trim().toUpperCase());
                            db.update("datos_aval_ind_t", cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                        }
                        row.close();
                    }
                }
                break;
            case Constants.REQUEST_CODE_COLONIA_CLIE:
                if (resultCode == Constants.REQUEST_CODE_COLONIA_CLIE){
                    if (data != null){
                        tvColoniaCli.setText(((ModeloCatalogoGral)data.getSerializableExtra(Constants.ITEM)).getNombre());
                        cv = new ContentValues();
                        cv.put("colonia",tvColoniaCli.getText().toString().trim().toUpperCase());
                        db.update("datos_cliente_ind_t", cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                    }
                }
                break;
            case Constants.REQUEST_CODE_COLONIA_AVAL:
                if (resultCode == Constants.REQUEST_CODE_COLONIA_AVAL){
                    if (data != null){
                        tvColoniaAval.setText(((ModeloCatalogoGral)data.getSerializableExtra(Constants.ITEM)).getNombre());
                        cv = new ContentValues();
                        cv.put("colonia",tvColoniaAval.getText().toString().trim().toUpperCase());
                        db.update("datos_aval_ind_t", cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                    }
                }
                break;
            case Constants.REQUEST_CODE_COLONIA_NEG:
                if (resultCode == Constants.REQUEST_CODE_COLONIA_NEG){
                    if (data != null){
                        tvColoniaNeg.setText(((ModeloCatalogoGral)data.getSerializableExtra(Constants.ITEM)).getNombre());
                        cv = new ContentValues();
                        cv.put("colonia",tvColoniaNeg.getText().toString().trim().toUpperCase());
                        db.update("datos_negocio_ind_t", cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                    }
                }
                break;
            case Constants.REQUEST_CODE_COLONIA_REF:
                if (resultCode == Constants.REQUEST_CODE_COLONIA_REF){
                    if (data != null){
                        tvColoniaRef.setText(((ModeloCatalogoGral)data.getSerializableExtra(Constants.ITEM)).getNombre());
                        cv = new ContentValues();
                        cv.put("colonia",tvColoniaRef.getText().toString().trim().toUpperCase());
                        db.update("datos_referencia_ind_t", cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                    }
                }
                break;
            case Constants.REQUEST_CODE_CAMARA_FACHADA_CLI:
                if (resultCode == Activity.RESULT_OK){
                    if (data != null){
                        ibFotoFachCli.setVisibility(View.GONE);
                        ivFotoFachCli.setVisibility(View.VISIBLE);
                        byteFotoFachCli = data.getByteArrayExtra(Constants.PICTURE);
                        Glide.with(ctx).load(byteFotoFachCli).centerCrop().into(ivFotoFachCli);
                        cv = new ContentValues();
                        try {
                            cv.put("foto_fachada", Miscellaneous.save(byteFotoFachCli, 1));
                            db.update("datos_cliente_ind_t", cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                }
                break;
            case Constants.REQUEST_CODE_CAMARA_FACHADA_NEG:
                if (resultCode == Activity.RESULT_OK){
                    if (data != null){
                        ibFotoFachNeg.setVisibility(View.GONE);
                        ivFotoFachNeg.setVisibility(View.VISIBLE);
                        byteFotoFachNeg = data.getByteArrayExtra(Constants.PICTURE);
                        Glide.with(ctx).load(byteFotoFachNeg).centerCrop().into(ivFotoFachNeg);
                        cv = new ContentValues();
                        try {
                            cv.put("foto_fachada", Miscellaneous.save(byteFotoFachNeg, 1));
                            db.update("datos_negocio_ind_t", cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                break;
            case Constants.REQUEST_CODE_CAMARA_FACHADA_AVAL:
                if (resultCode == Activity.RESULT_OK){
                    if (data != null){
                        ibFotoFachAval.setVisibility(View.GONE);
                        ivFotoFachAval.setVisibility(View.VISIBLE);
                        byteFotoFachAval = data.getByteArrayExtra(Constants.PICTURE);
                        Glide.with(ctx).load(byteFotoFachAval).centerCrop().into(ivFotoFachAval);
                        cv = new ContentValues();
                        try {
                            cv.put("foto_fachada", Miscellaneous.save(byteFotoFachAval, 1));
                            db.update("datos_aval_ind_t", cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                break;
            case Constants.REQUEST_CODE_FIRMA_AVAL:
                if (resultCode == Activity.RESULT_OK){
                    if (data != null){
                        ibFirmaAval.setVisibility(View.GONE);
                        ivFirmaAval.setVisibility(View.VISIBLE);
                        Glide.with(ctx)
                                .load(data.getByteArrayExtra(Constants.FIRMA_IMAGE))
                                .into(ivFirmaAval);
                        byteFirmaAval = data.getByteArrayExtra(Constants.FIRMA_IMAGE);
                        cv = new ContentValues();
                        try {
                            cv.put("firma", Miscellaneous.save(byteFirmaCli, 3));
                            db.update("datos_aval_ind_t", cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                break;
            case Constants.REQUEST_CODE_FIRMA_CLI:
                if (resultCode == Activity.RESULT_OK){
                    if (data != null){
                        ibFirmaCli.setVisibility(View.GONE);
                        ivFirmaCli.setVisibility(View.VISIBLE);
                        Glide.with(ctx)
                                .load(data.getByteArrayExtra(Constants.FIRMA_IMAGE))
                                .into(ivFirmaCli);
                        byteFirmaCli = data.getByteArrayExtra(Constants.FIRMA_IMAGE);
                        cv = new ContentValues();
                        try {
                            cv.put("firma", Miscellaneous.save(byteFirmaCli, 3));
                            db.update("datos_cliente_ind_t", cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                break;
            case Constants.REQUEST_CODE_FIRMA_CRE:
                if (resultCode == Activity.RESULT_OK){
                    if (data != null){
                        tvFirmaCre.setError(null);
                        ibFirmaCre.setVisibility(View.GONE);
                        ivFirmaCre.setVisibility(View.VISIBLE);
                        Glide.with(ctx)
                                .load(data.getByteArrayExtra(Constants.FIRMA_IMAGE))
                                .into(ivFirmaCre);
                        byteFirmaCre = data.getByteArrayExtra(Constants.FIRMA_IMAGE);
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
                    db.update("datos_credito_ind_t", cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                    break;
                case "fechaNacCli":
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
                    db.update("datos_cliente_ind_t", cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                    tvCurpCli.setText(Miscellaneous.GenerarCurp(params));
                    break;
                case "fechaNacAval":
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
                    db.update("datos_aval_ind_t", cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
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
                db.update("datos_credito_ind_t", cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                break;
            case "HoraVisitaAval":
                tvHoraLocAval.setText(timer);
                cv = new ContentValues();
                cv.put("horario_localizacion",timer);
                db.update("datos_aval_ind_t", cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
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
        double gas_renta = 0;
        double gas_otro = (etGastosOtrosNeg.getText().toString().trim().isEmpty())?0:Integer.parseInt(etGastosOtrosNeg.getText().toString().trim());

        double ingreso = ing_mensual + ing_otros;
        double gastos = (gas_semanal * 4) + gas_agua + gas_luz + gas_telefono + gas_renta + gas_otro;

        tvCapacidadPagoNeg.setText(String.valueOf(ingreso - gastos));
        ContentValues cv = new ContentValues();
        cv.put("capacidad_pago", tvCapacidadPagoNeg.getText().toString().trim());
        db.update("datos_negocio_ind_t", cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
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
                        tvDiasVentaNeg.setText(dias);
                        ContentValues cv = new ContentValues();
                        cv.put("dias_venta", tvDiasVentaNeg.getText().toString().trim().toUpperCase());
                        db.update("datos_negocio_ind_t", cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                    }
                }).show();
    }

    private void saveDatosCredito(){
        JSONObject datos_credito = new JSONObject();
        if (spPlazo.getSelectedItemPosition() > 0){
            if (spFrecuencia.getSelectedItemPosition() > 0){
                tvPlazo.setError(null);
                if (!validatorTV.validate(tvFechaDesembolso, new String[]{validatorTV.REQUIRED}) &&
                        !validator.validate(etMontoPrestamo, new String[]{validator.REQUIRED, validator.ONLY_NUMBER, validator.CREDITO})){
                    if (spDestino.getSelectedItemPosition() > 0){
                        tvDestino.setError(null);
                        if(byteFirmaCre != null){
                            ivDown2.setVisibility(View.GONE);
                            ivUp2.setVisibility(View.VISIBLE);
                            llDatosPersonales.setVisibility(View.VISIBLE);

                            ivDown1.setVisibility(View.VISIBLE);
                            ivUp1.setVisibility(View.GONE);
                            llDatosCredito.setVisibility(View.GONE);

                            etNombreCli.requestFocus();
                        }
                        else
                            tvFirmaCre.setError("Falta Capturar la Firma");
                    }
                    else{
                        tvDestino.setError("");
                        spDestino.setFocusable(true);
                        spDestino.setFocusableInTouchMode(true);
                        spDestino.requestFocus();
                    }
                }
            }
            else {
                tvPlazo.setError("");
                spFrecuencia.setFocusable(true);
                spFrecuencia.setFocusableInTouchMode(true);
                spFrecuencia.requestFocus();
            }
        }
        else{
            tvPlazo.setError("");
            spPlazo.setFocusable(true);
            spPlazo.setFocusableInTouchMode(true);
            spPlazo.requestFocus();
        }
    }
    private void saveDatosPersonales(){
        if (!validator.validate(etNombreCli, new String[]{validator.REQUIRED, validator.ONLY_TEXT}) &&
        !validator.validate(etApPaternoCli, new String[]{validator.REQUIRED, validator.ONLY_TEXT}) &&
        !validator.validate(etApMaternoCli, new String[]{validator.ONLY_TEXT})){
            if (rgGeneroCli.getCheckedRadioButtonId() == R.id.rbHombre || rgGeneroCli.getCheckedRadioButtonId() == R.id.rbMujer){
                if (!validator.validate(etCurpIdCli, new String[]{validator.REQUIRED, validator.CURP_ID}) &&
                !validator.validate(etNumIdentifCli, new String[]{validator.REQUIRED})){
                    if (spEstudiosCli.getSelectedItemPosition() > 0){
                        tvEstudiosCli.setError(null);
                        if (spEstadoCivilCli.getSelectedItemPosition() > 0){
                            tvEstadoCivilCli.setError(null);
                            boolean flag_estado_civil = false;
                            if (spEstadoCivilCli.getSelectedItemPosition() == 2 || spEstadoCivilCli.getSelectedItemPosition() == 5){
                                if (rgBienes.getCheckedRadioButtonId() == R.id.rbMancomunados ||
                                    rgBienes.getCheckedRadioButtonId() == R.id.rbSeparados){
                                    flag_estado_civil = true;
                                    tvBienes.setError(null);
                                }
                                else {
                                    tvBienes.setError("");
                                }
                            }else
                                flag_estado_civil = true;

                            if (flag_estado_civil){
                                Toast.makeText(ctx, "verdadero", Toast.LENGTH_SHORT).show();
                            }
                            else
                                tvBienes.setError("");
                        }
                        else{
                            tvEstadoCivilCli.setError("");
                            spEstadoCivilCli.setFocusable(true);
                            spEstadoCivilCli.setFocusableInTouchMode(true);
                            spEstadoCivilCli.requestFocus();
                        }
                    }
                    else{
                        tvEstudiosCli.setError("");
                        spEstudiosCli.setFocusable(true);
                        spEstudiosCli.setFocusableInTouchMode(true);
                        spEstudiosCli.requestFocus();
                    }
                }
            }
            else tvGeneroCli.setError("");
        }
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
                db.update("datos_cliente_ind_t", cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
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
                db.update("datos_negocio_ind_t", cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
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
                db.update("datos_aval_ind_t", cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
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
    public void onBackPressed() {
        AlertDialog solicitud = Popups.showDialogConfirm(this, Constants.warning,
                R.string.guardar_cambios, R.string.yes, new Popups.DialogMessage() {
                    @Override
                    public void OnClickListener(AlertDialog dialog) {
                        finish();
                        dialog.dismiss();
                    }
                }, R.string.no, new Popups.DialogMessage() {
                    @Override
                    public void OnClickListener(AlertDialog dialog) {
                        finish();
                        dialog.dismiss();
                    }
                });
        solicitud.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        solicitud.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        solicitud.setCancelable(true);
        solicitud.show();

        //if (flag[0])
        //    super.onBackPressed();

    }

    private void openRegistroCliente() {
        dialog_registro_cli registro_cli = new dialog_registro_cli();
        registro_cli.setCancelable(false);

        registro_cli.show(getSupportFragmentManager(), NameFragments.DIALOGORIGINACIONIND);
    }

    @Override
    public void onComplete(long id_solicitud, String nombre, String paterno, String materno) {
        if (id_solicitud > 0) {
            this.id_solicitud = id_solicitud;
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
        Cursor row = dBhelper.getOriginacionInd(idSolicitud);

        Log.e("count", row.getCount()+"");

        row.moveToFirst();

        //Llenado del datos del prestamo
        spPlazo.setSelection(row.getInt(2));
        spFrecuencia.setSelection(row.getInt(3));
        tvFechaDesembolso.setText(row.getString(4));
        tvDiaDesembolso.setText(row.getString(5));
        tvHoraVisita.setText(row.getString(6));
        etMontoPrestamo.setText(row.getString(7));
        if (!row.getString(7).trim().isEmpty()) {
            tvCantidadLetra.setText((Miscellaneous.cantidadLetra(row.getString(7)).toUpperCase() + " PESOS MEXICANOS ").replace("  ", " "));
            if (row.getInt(7) >= 30000)
                llPropiedades.setVisibility(View.VISIBLE);
        }
        spDestino.setSelection(row.getInt(8));
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
        etCurpIdCli.setText(row.getString(22));
        tvOcupacionCli.setText(row.getString(23));
        tvActividadEcoCli.setText(row.getString(24));
        etNumIdentifCli.setText(row.getString(26));
        spEstudiosCli.setSelection(row.getInt(27));
        spEstadoCivilCli.setSelection(row.getInt(28));
        switch (row.getInt(28)){
            case 2:
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
            case 5:
                llConyuge.setVisibility(View.VISIBLE);
                break;
        }

        spTipoCasaCli.setSelection(row.getInt(30));
        switch (row.getInt(30)){
            case 3:
                llCasaFamiliar.setVisibility(View.VISIBLE);
                spCasaFamiliar.setSelection(row.getInt(31));
                break;
            case 4:
                llCasaOtroCli.setVisibility(View.VISIBLE);
                etOTroTipoCli.setText(row.getString(32));
                break;
        }
        if (!row.getString(33).isEmpty() && !row.getString(34).isEmpty()){
            mapCli.setVisibility(View.VISIBLE);
            Ubicacion(row.getDouble(33), row.getDouble(34));
        }
        etCalleCli.setText(row.getString(35));
        etNoExtCli.setText(row.getString(36));
        etNoIntCli.setText(row.getString(37));
        etManzanaCli.setText(row.getString(38));
        etLoteCli.setText(row.getString(39));
        etCpCli.setText(row.getString(40));
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
        etTelCasaCli.setText(row.getString(42));
        etCelularCli.setText(row.getString(43));
        etTelMensCli.setText(row.getString(44));
        etTelTrabajoCli.setText(row.getString(45));
        etTiempoSitio.setText((row.getInt(46) > 0)?row.getString(46):"");
        spDependientes.setSelection(row.getInt(47));
        spEnteroNosotros.setSelection(row.getInt(48));
        etEmail.setText(row.getString(49));
        if (!row.getString(50).isEmpty()){
            File fachadaFile = new File(Constants.ROOT_PATH + "Fachada/"+row.getString(50));
            Uri uriFachada = Uri.fromFile(fachadaFile);
            byteFotoFachCli = Miscellaneous.getBytesUri(ctx, uriFachada,0);
            Glide.with(ctx).load(uriFachada).into(ivFotoFachCli);
            ibFotoFachCli.setVisibility(View.GONE);
            ivFotoFachCli.setVisibility(View.VISIBLE);
        }
        etReferenciCli.setText(row.getString(51));
        if (!row.getString(52).isEmpty()){
            File firmaFile = new File(Constants.ROOT_PATH + "Firma/"+row.getString(52));
            Uri uriFirma = Uri.fromFile(firmaFile);
            byteFirmaCli = Miscellaneous.getBytesUri(ctx, uriFirma,0);
            Glide.with(ctx).load(uriFirma).into(ivFirmaCli);
            ibFirmaCli.setVisibility(View.GONE);
            ivFirmaCli.setVisibility(View.VISIBLE);
        }

        // Llenado de datos del conyuge
        etNombreCony.setText(row.getString(58));
        etApPaternoCony.setText(row.getString(59));
        etApMaternoCony.setText(row.getString(60));
        tvOcupacionCony.setText(row.getString(61));
        etCelularCony.setText(row.getString(62));

        //Llenado de datos economicos
        etPropiedadesEco.setText(row.getString(66));
        etValorAproxEco.setText(row.getString(67));
        etUbicacionEco.setText(row.getString(68));
        etIngresoEco.setText(row.getString(69));

        //Llenado de datos del negocio
        etNombreNeg.setText(row.getString(73));
        if (!row.getString(74).isEmpty() && !row.getString(75).isEmpty()){
            mapNeg.setVisibility(View.VISIBLE);
            UbicacionNeg(row.getDouble(74), row.getDouble(75));
        }
        etCalleNeg.setText(row.getString(76));
        etNoExtNeg.setText(row.getString(77));
        etNoIntNeg.setText(row.getString(78));
        etManzanaNeg.setText(row.getString(79));
        etLoteNeg.setText(row.getString(80));
        etCpNeg.setText(row.getString(81));
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
            etAntiguedadNeg.setText(row.getString(84));
        etIngMenNeg.setText(row.getString(85));
        etOtrosIngNeg.setText(row.getString(86));
        etGastosSemNeg.setText(row.getString(87));
        etGastosAguaNeg.setText(row.getString(88));
        etGastosLuzNeg.setText(row.getString(89));
        etGastosTelNeg.setText(row.getString(90));
        etGastosRentaNeg.setText(row.getString(91));
        etGastosOtrosNeg.setText(row.getString(92));
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
        etReferenciNeg.setText(row.getString(96));

        //Llenado de datos del aval
        etNombreAval.setText(row.getString(100));
        etApPaternoAval.setText(row.getString(101));
        etApMaternoAval.setText(row.getString(102));
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
        etCurpIdAval.setText(row.getString(110));
        etNumIdentifAval.setText(row.getString(112));
        tvOcupacionAval.setText(row.getString(113));
        tvActividadEcoAval.setText(row.getString(114));
        if (!row.getString(115).isEmpty() && !row.getString(116).isEmpty()){
            mapAval.setVisibility(View.VISIBLE);
            UbicacionAval(row.getDouble(115), row.getDouble(116));
        }
        etCalleAval.setText(row.getString(117));
        etNoExtAval.setText(row.getString(118));
        etNoIntAval.setText(row.getString(119));
        etManzanaAval.setText(row.getString(120));
        etLoteAval.setText(row.getString(121));
        etCpAval.setText(row.getString(122));
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
        spTipoCasaAval.setSelection(row.getInt(124));
        if (row.getInt(124) == 2){
            llParentescoFamAval.setVisibility(View.VISIBLE);
            llNombreTitular.setVisibility(View.VISIBLE);
            etNombreTitularAval.setText(row.getString(125));
            spParentescoAval.setSelection(row.getInt(126));
        }
        etIngMenAval.setText(row.getString(127));
        etIngOtrosAval.setText(row.getString(128));
        etGastosSemAval.setText(row.getString(129));
        etGastosAguaAval.setText(row.getString(130));
        etGastosLuzAval.setText(row.getString(131));
        etGastosTelAval.setText(row.getString(132));
        etGastosRentaAval.setText(row.getString(133));
        etGastosOtrosAval.setText(row.getString(134));
        tvHoraLocAval.setText(row.getString(135));
        if (row.getInt(136) > 0)
            etAntiguedadAval.setText(row.getString(136));
        etTelCasaAval.setText(row.getString(137));
        etCelularAval.setText(row.getString(138));
        if (!row.getString(139).isEmpty()){
            File fachadaFile = new File(Constants.ROOT_PATH + "Fachada/"+row.getString(139));
            Uri uriFachada = Uri.fromFile(fachadaFile);
            byteFotoFachAval = Miscellaneous.getBytesUri(ctx, uriFachada,0);
            Glide.with(ctx).load(uriFachada).into(ivFotoFachAval);
            ibFotoFachAval.setVisibility(View.GONE);
            ivFotoFachAval.setVisibility(View.VISIBLE);
        }
        etReferenciaAval.setText(row.getString(140));
        if (!row.getString(141).isEmpty()){
            File firmaFile = new File(Constants.ROOT_PATH + "Firma/"+row.getString(141));
            Uri uriFirma = Uri.fromFile(firmaFile);
            byteFirmaAval = Miscellaneous.getBytesUri(ctx, uriFirma,0);
            Glide.with(ctx).load(uriFirma).into(ivFirmaAval);
            ibFirmaAval.setVisibility(View.GONE);
            ivFirmaAval.setVisibility(View.VISIBLE);
        }

        //Llena datos de referencia
        etNombreRef.setText(row.getString(147));
        etApPaternoRef.setText(row.getString(148));
        etApMaternoRef.setText(row.getString(149));
        etCalleRef.setText(row.getString(150));
        etCpRef.setText(row.getString(151));
        tvColoniaRef.setText(row.getString(152));
        tvMunicipioRef.setText(row.getString(153));
        etTelCelRef.setText(row.getString(154));

    }
}
