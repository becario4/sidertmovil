package com.sidert.sidertmovil.activities;

import android.Manifest;
import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.ColorDrawable;
import android.location.LocationManager;
import android.os.Handler;
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
import com.sidert.sidertmovil.fragments.dialogs.dialog_time_picker;
import com.sidert.sidertmovil.models.ModeloCatalogoGral;
import com.sidert.sidertmovil.utils.Constants;
import com.sidert.sidertmovil.utils.Miscellaneous;
import com.sidert.sidertmovil.utils.MyCurrentListener;
import com.sidert.sidertmovil.utils.NameFragments;
import com.sidert.sidertmovil.utils.NetworkStatus;
import com.sidert.sidertmovil.utils.Popups;
import com.sidert.sidertmovil.utils.Validator;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;

public class SolicitudCreditoInd extends AppCompatActivity {

    private Context ctx;
    private Context context;

    private String[] _activos;
    private ArrayList<Integer> selectedItemsActivo;
    private String[] _dias_semana;
    private ArrayList<Integer> selectedItemsDias;

    private DBhelper dBhelper;
    private SQLiteDatabase db;

    private SimpleDateFormat sdf = new SimpleDateFormat(Constants.FORMAT_DATE_GNRAL);
    private Calendar myCalendar;

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
    private EditText etFechaDesembolso;
    private EditText etDiaDesembolso;
    private EditText etHoraVisita;
    private EditText etMontoPrestamo;
    private MultiAutoCompleteTextView etCantidadLetra;
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
    private EditText etFechaNacCli;
    private EditText etEdadCli;
    private TextView tvGeneroCli;
    private RadioGroup rgGeneroCli;
    private EditText etEstadoNacCli;
    private EditText etCurpCli;
    private EditText etCurpIdCli;
    private EditText etRfcClaveCli;
    private EditText etRfcCli;
    private EditText etOcupacionCli;
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
    private LinearLayout llOtroTipoCli;
    private EditText etOtroTipoCli;
    private TextView tvMapaCli;
    private ImageButton ibMapCli;
    private ProgressBar pbLoadCli;
    private MapView mapCli;
    private LatLng latLngUbiCli;
    private EditText etCalleCli;
    private EditText etNoExtCli;
    private EditText etNoIntCli;
    private EditText etCpCli;
    private EditText etColoniaCli;
    private EditText etMunicipioCli;
    private EditText etEstadoCli;
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
    private EditText etOcupacionCony;
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
    private EditText etCpNeg;
    private EditText etColoniaNeg;
    private EditText etMunicipioNeg;
    private EditText etActEconomicaNeg;
    private EditText etAntiguedadNeg;
    private EditText etIngSemNeg;
    private EditText etOtrosIngNeg;
    private EditText etGastoSemNeg;
    private EditText etGastosFijosNeg;
    private EditText etGastosAguaNeg;
    private EditText etGastosLuzNeg;
    private EditText etGastosTelNeg;
    private EditText etRentaNeg;
    private EditText etDiasVentaNeg;
    private ImageButton ibFotoFachNeg;
    private ImageView ivFotoFachNeg;
    public byte[] byteFotoFachNeg;
    private MultiAutoCompleteTextView etReferenciNeg;
    //========================================
    //======= AVAL ===========================
    private EditText etNombreAval;
    private EditText etApPaternoAval;
    private EditText etApMaternoAval;
    private Spinner spParentescoAval;
    private EditText etFechaNacAval;
    private EditText etEdadAval;
    private RadioGroup rgGeneroAval;
    private EditText etEstadoNacAval;
    private EditText etRfcAval;
    private EditText etRfcClaveAval;
    private EditText etCurpAval;
    private EditText etCurpIdAval;
    private EditText etNumIdentifAval;
    private EditText etOcupacionAval;
    private EditText etActividadEcoAval;
    private ImageButton ibMapAval;
    private ProgressBar pbLoadAval;
    private MapView mapAval;
    private LatLng latLngUbiAval;
    private EditText etCalleAval;
    private EditText etNoExtAval;
    private EditText etNoIntAval;
    private EditText etCpAval;
    private EditText etColoniaAval;
    private EditText etMunicipioAval;
    private EditText etEstadoAval;
    private Spinner spTipoCasaAval;
    private LinearLayout llOtroTipoAval;
    private EditText etOtroTipoAval;
    private EditText etNombreTitularAval;
    private Spinner spParentescoTitularAval;
    private EditText etGastosFijosAval;
    private EditText etGastosAguaAval;
    private EditText etGastosLuzAval;
    private EditText etGastosTelAval;
    private EditText etRentaAval;
    private EditText etHoraLocAval;
    private EditText etActivosAval;
    private EditText etAntiguedadAval;
    private EditText etIngSemAval;
    private EditText etOtrosIngAval;
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
    private EditText etColoniaRef;
    private EditText etMunicipioRef;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solicitud_credito_ind);

        ctx = getApplicationContext();
        context = this;

        dBhelper = new DBhelper(ctx);
        db = dBhelper.getWritableDatabase();

        _activos = getResources().getStringArray(R.array.activos);
        _dias_semana = getResources().getStringArray(R.array.dias_semana);

        myCalendar = Calendar.getInstance();

        validator = new Validator();

        //=================================== DATOS CREDITO  =======================================
        tvPlazo             = findViewById(R.id.tvPlazo);
        spPlazo             = findViewById(R.id.spPlazo);
        spFrecuencia        = findViewById(R.id.spFrecuencia);
        etFechaDesembolso   = findViewById(R.id.etFechaDesembolso);
        etDiaDesembolso     = findViewById(R.id.etDiaDesembolso);
        etHoraVisita        = findViewById(R.id.etHoraVisita);
        etMontoPrestamo     = findViewById(R.id.etMontoPrestamo);
        etCantidadLetra     = findViewById(R.id.etCantidadLetra);
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
        etFechaNacCli       = findViewById(R.id.etFechaNacCli);
        etEdadCli           = findViewById(R.id.etEdadCli);
        tvGeneroCli         = findViewById(R.id.tvGeneroCli);
        rgGeneroCli         = findViewById(R.id.rgGeneroCli);
        etEstadoNacCli      = findViewById(R.id.etEstadoNacCli);
        etRfcCli            = findViewById(R.id.etRfcCli);
        etRfcClaveCli       = findViewById(R.id.etRfcClaveCli);
        etCurpCli           = findViewById(R.id.etCurpCli);
        etCurpIdCli         = findViewById(R.id.etCurpIdCli);
        etOcupacionCli      = findViewById(R.id.etOcupacionCli);
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
        llOtroTipoCli       = findViewById(R.id.llOtroTipoCli);
        etOtroTipoCli       = findViewById(R.id.etOtroTipoCli);
        tvMapaCli           = findViewById(R.id.tvMapaCli);
        ibMapCli            = findViewById(R.id.ibMapCli);
        pbLoadCli           = findViewById(R.id.pbLoadCli);
        mapCli              = findViewById(R.id.mapCli);
        etCalleCli          = findViewById(R.id.etCalleCli);
        etNoExtCli          = findViewById(R.id.etNoExtCli);
        etNoIntCli          = findViewById(R.id.etNoIntCli);
        etCpCli             = findViewById(R.id.etCpCli);
        etColoniaCli        = findViewById(R.id.etColoniaCli);
        etMunicipioCli      = findViewById(R.id.etMunicipioCli);
        etEstadoCli         = findViewById(R.id.etEstadoCli);
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
        etOcupacionCony     = findViewById(R.id.etOcupacionCony);
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
        etCpNeg             = findViewById(R.id.etCpNeg);
        etColoniaNeg        = findViewById(R.id.etColoniaNeg);
        etMunicipioNeg      = findViewById(R.id.etMunicipioNeg);
        etActEconomicaNeg   = findViewById(R.id.etActEconomicaNeg);
        etAntiguedadNeg     = findViewById(R.id.etAntiguedadNeg);
        etIngSemNeg         = findViewById(R.id.etIngSemNeg);
        etOtrosIngNeg       = findViewById(R.id.etOtrosIngNeg);
        etGastoSemNeg       = findViewById(R.id.etGastoSemNeg);
        etGastosFijosNeg    = findViewById(R.id.etGastosFijosNeg);
        etGastosAguaNeg     = findViewById(R.id.etGastosAguaNeg);
        etGastosLuzNeg      = findViewById(R.id.etGastosLuzNeg);
        etGastosTelNeg      = findViewById(R.id.etGastosTelNeg);
        etRentaNeg          = findViewById(R.id.etRentaNeg);
        etDiasVentaNeg      = findViewById(R.id.etDiasVentaNeg);
        ibFotoFachNeg       = findViewById(R.id.ibFotoFachNeg);
        ivFotoFachNeg       = findViewById(R.id.ivFotoFachNeg);
        etReferenciNeg      = findViewById(R.id.etReferenciaNeg);
        //==========================================================================================
        //=====================================  DATOS AVAL  =======================================
        etNombreAval        = findViewById(R.id.etNombreAval);
        etApPaternoAval     = findViewById(R.id.etApPaternoAval);
        etApMaternoAval     = findViewById(R.id.etApMaternoAval);
        spParentescoAval    = findViewById(R.id.spParentescoAval);
        etFechaNacAval      = findViewById(R.id.etFechaNacAval);
        etEdadAval          = findViewById(R.id.etEdadAval);
        rgGeneroAval        = findViewById(R.id.rgGeneroAval);
        etEstadoNacAval     = findViewById(R.id.etEstadoNacAval);
        etRfcAval           = findViewById(R.id.etRfcAval);
        etRfcClaveAval      = findViewById(R.id.etRfcClaveAval);
        etCurpAval          = findViewById(R.id.etCurpAval);
        etCurpIdAval        = findViewById(R.id.etCurpIdAval);
        etNumIdentifAval    = findViewById(R.id.etNumIdentifAval);
        etOcupacionAval     = findViewById(R.id.etOcupacionAval);
        etActividadEcoAval  = findViewById(R.id.etActividadEcoAval);
        ibMapAval           = findViewById(R.id.ibMapAval);
        pbLoadAval          = findViewById(R.id.pbLoadAval);
        mapAval             = findViewById(R.id.mapAval);
        etCalleAval         = findViewById(R.id.etCalleAval);
        etNoExtAval         = findViewById(R.id.etNoExtAval);
        etNoIntAval         = findViewById(R.id.etNoIntAval);
        etCpAval            = findViewById(R.id.etCpAval);
        etColoniaAval       = findViewById(R.id.etColoniaAval);
        etMunicipioAval     = findViewById(R.id.etMunicipioAval);
        etEstadoAval        = findViewById(R.id.etEstadoAval);
        spTipoCasaAval      = findViewById(R.id.spTipoCasaAval);
        llOtroTipoAval      = findViewById(R.id.llOtroTipoAval);
        etOtroTipoAval      = findViewById(R.id.etOtroTipoAval);
        etNombreTitularAval = findViewById(R.id.etNombreTitularAval);
        spParentescoTitularAval = findViewById(R.id.spParentescoTitularAval);
        etGastosFijosAval   = findViewById(R.id.etGastosFijosAval);
        etGastosAguaAval    = findViewById(R.id.etGastosAguaAval);
        etGastosLuzAval     = findViewById(R.id.etGastosLuzAval);
        etGastosTelAval     = findViewById(R.id.etGastosTelAval);
        etRentaAval         = findViewById(R.id.etRentaAval);
        etHoraLocAval       = findViewById(R.id.etHoraLocAval);
        etActivosAval       = findViewById(R.id.etActivosAval);
        etAntiguedadAval    = findViewById(R.id.etAntiguedadAval);
        etIngSemAval        = findViewById(R.id.etIngSemAval);
        etOtrosIngAval      = findViewById(R.id.etOtrosIngAval);
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
        etColoniaRef        = findViewById(R.id.etColoniaRef);
        etMunicipioRef      = findViewById(R.id.etMunicipioRef);
        etTelCelRef         = findViewById(R.id.etTelCelRef);
        //==========================================================================================
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
        etFechaDesembolso.setOnClickListener(etFechaDesembolso_OnClick);
        etHoraVisita.setOnClickListener(etHoraVisita_OnClick);
        ibFirmaCre.setOnClickListener(ibFirmaCre_OnClick);
        etMontoPrestamo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length()> 0){
                    etCantidadLetra.setText((Miscellaneous.cantidadLetra(s.toString()).toUpperCase() + " PESOS MEXICANOS ").replace("  ", " "));
                    if (Integer.parseInt(s.toString()) > 30000){
                        llPropiedades.setVisibility(View.VISIBLE);
                    }
                    else{
                        llPropiedades.setVisibility(View.GONE);
                    }
                }
                else{
                    etCantidadLetra.setText("");
                }
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
                    params.put(3, etFechaNacCli.getText().toString());

                    if (rgGeneroCli.getCheckedRadioButtonId()==R.id.rbHombre)
                        params.put(4, "Hombre");
                    else if (rgGeneroCli.getCheckedRadioButtonId()==R.id.rbMujer)
                        params.put(4, "Mujer");
                    else
                        params.put(4, "");

                    if (!etEstadoNacCli.getText().toString().trim().isEmpty())
                        params.put(5, etEstadoNacCli.getText().toString().trim());
                    else
                        params.put(5,"");
                    etCurpCli.setText(Miscellaneous.GenerarCurp(params));
                }
                else{
                    params.put(0, "");
                    params.put(1, etApPaternoCli.getText().toString());
                    params.put(2, etApMaternoCli.getText().toString());
                    params.put(3, etFechaNacCli.getText().toString());

                    if (rgGeneroCli.getCheckedRadioButtonId()==R.id.rbHombre)
                        params.put(4, "Hombre");
                    else if (rgGeneroCli.getCheckedRadioButtonId()==R.id.rbMujer)
                        params.put(4, "Mujer");
                    else
                        params.put(4, "");

                    if (!etEstadoNacCli.getText().toString().trim().isEmpty())
                        params.put(5, etEstadoNacCli.getText().toString().trim());
                    else
                        params.put(5,"");
                    etCurpCli.setText(Miscellaneous.GenerarCurp(params));
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
                    params.put(3, etFechaNacCli.getText().toString());

                    if (rgGeneroCli.getCheckedRadioButtonId()==R.id.rbHombre)
                        params.put(4, "Hombre");
                    else if (rgGeneroCli.getCheckedRadioButtonId()==R.id.rbMujer)
                        params.put(4, "Mujer");
                    else
                        params.put(4, "");

                    if (!etEstadoNacCli.getText().toString().trim().isEmpty())
                        params.put(5, etEstadoNacCli.getText().toString().trim());
                    else
                        params.put(5,"");
                    etCurpCli.setText(Miscellaneous.GenerarCurp(params));
                }
                else{
                    params.put(0, etNombreCli.getText().toString());
                    params.put(1, "");
                    params.put(2, etApMaternoCli.getText().toString());
                    params.put(3, etFechaNacCli.getText().toString());

                    if (rgGeneroCli.getCheckedRadioButtonId()==R.id.rbHombre)
                        params.put(4, "Hombre");
                    else if (rgGeneroCli.getCheckedRadioButtonId()==R.id.rbMujer)
                        params.put(4, "Mujer");
                    else
                        params.put(4, "");

                    if (!etEstadoNacCli.getText().toString().trim().isEmpty())
                        params.put(5, etEstadoNacCli.getText().toString().trim());
                    else
                        params.put(5,"");
                    etCurpCli.setText(Miscellaneous.GenerarCurp(params));
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
                    params.put(3, etFechaNacCli.getText().toString());

                    if (rgGeneroCli.getCheckedRadioButtonId()==R.id.rbHombre)
                        params.put(4, "Hombre");
                    else if (rgGeneroCli.getCheckedRadioButtonId()==R.id.rbMujer)
                        params.put(4, "Mujer");
                    else
                        params.put(4, "");

                    if (!etEstadoNacCli.getText().toString().trim().isEmpty())
                        params.put(5, etEstadoNacCli.getText().toString().trim());
                    else
                        params.put(5,"");
                    etCurpCli.setText(Miscellaneous.GenerarCurp(params));
                }
                else{
                    params.put(0, etNombreCli.getText().toString());
                    params.put(1, etApPaternoCli.getText().toString());
                    params.put(2, "");
                    params.put(3, etFechaNacCli.getText().toString());

                    if (rgGeneroCli.getCheckedRadioButtonId()==R.id.rbHombre)
                        params.put(4, "Hombre");
                    else if (rgGeneroCli.getCheckedRadioButtonId()==R.id.rbMujer)
                        params.put(4, "Mujer");
                    else
                        params.put(4, "");

                    if (!etEstadoNacCli.getText().toString().trim().isEmpty())
                        params.put(5, etEstadoNacCli.getText().toString().trim());
                    else
                        params.put(5,"");
                    etCurpCli.setText(Miscellaneous.GenerarCurp(params));
                }
            }
        });
        etFechaNacCli.setOnClickListener(etFechaNac_OnClick);
        etEstadoNacCli.setOnClickListener(etEstadoNac_OnClick);
        etCurpCli.addTextChangedListener(new TextWatcher() {
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
                        etRfcCli.setText("Rfc no válida");
                    else
                        etRfcCli.setText(s.toString().substring(0,10));

                }
                else
                    etRfcCli.setText("Rfc no válida");
            }
        });
        etOcupacionCli.setOnClickListener(etOcupacionClie_OnClick);
        spEstadoCivilCli.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
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
                if (position == 3)
                    llOtroTipoCli.setVisibility(View.VISIBLE);
                else
                    llOtroTipoCli.setVisibility(View.GONE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ibMapCli.setOnClickListener(ibMapCli_OnClick);
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
                        row.moveToFirst();
                        if (row.getCount() == 1){
                            etColoniaCli.setText(row.getString(7));
                            etMunicipioCli.setText(row.getString(4));
                            etEstadoCli.setText(row.getString(1));
                        }else {
                            etColoniaCli.setText("");
                            etMunicipioCli.setText(row.getString(4));
                            etEstadoCli.setText(row.getString(1));
                        }
                    }else {
                        etColoniaCli.setText("No se encontró información");
                        etMunicipioCli.setText("No se encontró información");
                        etEstadoCli.setText("No se encontró información");
                    }
                    row.close();
                }else {
                    etColoniaCli.setText("No se encontró información");
                    etMunicipioCli.setText("No se encontró información");
                    etEstadoCli.setText("No se encontró información");
                }
            }
        });
        etColoniaCli.setOnClickListener(etColonia_OnClick);
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
                    if (s.length() == 10)
                        etTelCasaCli.setError(null);
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
                    if (s.length() == 10)
                        etCelularCli.setError(null);
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
                    if (s.length() == 10)
                        etTelMensCli.setError(null);
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
                    if (s.length() == 10)
                        etTelTrabajoCli.setError(null);
                    else
                        etTelTrabajoCli.setError(ctx.getResources().getString(R.string.mensaje_telefono));
                }
                else
                    etTelTrabajoCli.setError(null);
            }
        });
        ibFotoFachCli.setOnClickListener(ibFotoFachCli_OnClick);
        ibFirmaCli.setOnClickListener(ibFirmaCli_OnClick);
        //==================================  CONYUGE LISTENER  ====================================
        etOcupacionCony.setOnClickListener(etOcupacionConyuge_OnClick);
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
                    if (s.length() == 10)
                        etCelularCony.setError(null);
                    else
                        etCelularCony.setError(ctx.getResources().getString(R.string.mensaje_telefono));
                }
                else
                    etCelularCony.setError(null);
            }
        });
        //===============================  ECONOMICOS LISTENER  ====================================
        //==================================  NEGOCIO LISTENER  ====================================
        ibMapNeg.setOnClickListener(ibMapNeg_OnClick);
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
                        row.moveToFirst();
                        if (row.getCount() == 1){
                            etColoniaNeg.setText(row.getString(7));
                            etMunicipioNeg.setText(row.getString(4));
                        }else {
                            etColoniaNeg.setText("");
                            etMunicipioNeg.setText(row.getString(4));
                        }
                    }else {
                        etColoniaNeg.setText("No se encontró información");
                        etMunicipioNeg.setText("No se encontró información");
                    }
                    row.close();
                }else {
                    etColoniaNeg.setText("No se encontró información");
                    etMunicipioNeg.setText("No se encontró información");
                }
            }
        });
        etColoniaNeg.setOnClickListener(etColoniaAct_OnClick);
        etActEconomicaNeg.setOnClickListener(etActividadEco_OnClick);
        etDiasVentaNeg.setOnClickListener(etDiasVenta_OnClick);
        ibFotoFachNeg.setOnClickListener(ibFotoFachNeg_OnClick);
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
                    params.put(3, etFechaNacCli.getText().toString());

                    if (rgGeneroAval.getCheckedRadioButtonId()==R.id.rbHombre)
                        params.put(4, "Hombre");
                    else if (rgGeneroAval.getCheckedRadioButtonId()==R.id.rbMujer)
                        params.put(4, "Mujer");
                    else
                        params.put(4, "");

                    if (!etEstadoNacAval.getText().toString().trim().isEmpty())
                        params.put(5, etEstadoNacAval.getText().toString().trim());
                    else
                        params.put(5,"");
                    etCurpAval.setText(Miscellaneous.GenerarCurp(params));
                }
                else{
                    params.put(0, "");
                    params.put(1, etApPaternoAval.getText().toString());
                    params.put(2, etApMaternoAval.getText().toString());
                    params.put(3, etFechaNacAval.getText().toString());

                    if (rgGeneroAval.getCheckedRadioButtonId()==R.id.rbHombre)
                        params.put(4, "Hombre");
                    else if (rgGeneroAval.getCheckedRadioButtonId()==R.id.rbMujer)
                        params.put(4, "Mujer");
                    else
                        params.put(4, "");

                    if (!etEstadoNacAval.getText().toString().trim().isEmpty())
                        params.put(5, etEstadoNacAval.getText().toString().trim());
                    else
                        params.put(5,"");
                    etCurpAval.setText(Miscellaneous.GenerarCurp(params));
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
                    params.put(3, etFechaNacAval.getText().toString());

                    if (rgGeneroAval.getCheckedRadioButtonId()==R.id.rbHombre)
                        params.put(4, "Hombre");
                    else if (rgGeneroAval.getCheckedRadioButtonId()==R.id.rbMujer)
                        params.put(4, "Mujer");
                    else
                        params.put(4, "");

                    if (!etEstadoNacAval.getText().toString().trim().isEmpty())
                        params.put(5, etEstadoNacAval.getText().toString().trim());
                    else
                        params.put(5,"");
                    etCurpAval.setText(Miscellaneous.GenerarCurp(params));
                }
                else{
                    params.put(0, etNombreAval.getText().toString());
                    params.put(1, "");
                    params.put(2, etApMaternoAval.getText().toString());
                    params.put(3, etFechaNacAval.getText().toString());

                    if (rgGeneroAval.getCheckedRadioButtonId()==R.id.rbHombre)
                        params.put(4, "Hombre");
                    else if (rgGeneroAval.getCheckedRadioButtonId()==R.id.rbMujer)
                        params.put(4, "Mujer");
                    else
                        params.put(4, "");

                    if (!etEstadoNacAval.getText().toString().trim().isEmpty())
                        params.put(5, etEstadoNacAval.getText().toString().trim());
                    else
                        params.put(5,"");
                    etCurpAval.setText(Miscellaneous.GenerarCurp(params));
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
                    params.put(3, etFechaNacAval.getText().toString());

                    if (rgGeneroAval.getCheckedRadioButtonId()==R.id.rbHombre)
                        params.put(4, "Hombre");
                    else if (rgGeneroAval.getCheckedRadioButtonId()==R.id.rbMujer)
                        params.put(4, "Mujer");
                    else
                        params.put(4, "");

                    if (!etEstadoNacAval.getText().toString().trim().isEmpty())
                        params.put(5, etEstadoNacAval.getText().toString().trim());
                    else
                        params.put(5,"");
                    etCurpAval.setText(Miscellaneous.GenerarCurp(params));
                }
                else{
                    params.put(0, etNombreAval.getText().toString());
                    params.put(1, etApPaternoAval.getText().toString());
                    params.put(2, "");
                    params.put(3, etFechaNacAval.getText().toString());

                    if (rgGeneroAval.getCheckedRadioButtonId()==R.id.rbHombre)
                        params.put(4, "Hombre");
                    else if (rgGeneroAval.getCheckedRadioButtonId()==R.id.rbMujer)
                        params.put(4, "Mujer");
                    else
                        params.put(4, "");

                    if (!etEstadoNacAval.getText().toString().trim().isEmpty())
                        params.put(5, etEstadoNacAval.getText().toString().trim());
                    else
                        params.put(5,"");
                    etCurpAval.setText(Miscellaneous.GenerarCurp(params));
                }
            }
        });
        etFechaNacAval.setOnClickListener(etFechaNacAval_OnClick);
        etEstadoNacAval.setOnClickListener(etEstadoNacAval_OnClick);
        etCurpAval.addTextChangedListener(new TextWatcher() {
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
                        etRfcAval.setText("Rfc no válida");
                    else
                        etRfcAval.setText(s.toString().substring(0,10));

                }
                else
                    etRfcAval.setText("Rfc no válida");
            }
        });
        etOcupacionAval.setOnClickListener(etOcupacionAval_OnClick);
        ibMapAval.setOnClickListener(ibMapAval_OnClick);
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
                        row.moveToFirst();
                        if (row.getCount() == 1){
                            etColoniaAval.setText(row.getString(7));
                            etMunicipioAval.setText(row.getString(4));
                            etEstadoAval.setText(row.getString(1));
                        }else {
                            etColoniaAval.setText("");
                            etMunicipioAval.setText(row.getString(4));
                            etEstadoAval.setText(row.getString(1));
                        }
                    }else {
                        etColoniaAval.setText("No se encontró información");
                        etMunicipioAval.setText("No se encontró información");
                        etEstadoAval.setText("No se encontró información");
                    }
                    row.close();
                }else {
                    etColoniaAval.setText("No se encontró información");
                    etMunicipioAval.setText("No se encontró información");
                    etEstadoAval.setText("No se encontró información");
                }
            }
        });
        etColoniaAval.setOnClickListener(etColoniaAval_OnClick);
        spTipoCasaAval.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 3)
                    llOtroTipoAval.setVisibility(View.VISIBLE);
                else
                    llOtroTipoAval.setVisibility(View.GONE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        etHoraLocAval.setOnClickListener(etHoraLocAval_OnClick);
        etActivosAval.setOnClickListener(etActivos_OnClick);
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
                    if (s.length() == 10)
                        etTelCasaAval.setError(null);
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
                    if (s.length() == 10)
                        etCelularAval.setError(null);
                    else
                        etCelularAval.setError(ctx.getResources().getString(R.string.mensaje_telefono));
                }
                else
                    etCelularAval.setError(null);
            }
        });
        ibFotoFachAval.setOnClickListener(ibFotoFachAval_OnClick);
        ibFirmaAval.setOnClickListener(ibFirmaAval_OnClick);
        //============== REFERENCIA ================================
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
                        row.moveToFirst();
                        if (row.getCount() == 1){
                            etColoniaRef.setText(row.getString(7));
                            etMunicipioRef.setText(row.getString(4));
                        }else {
                            etColoniaRef.setText("");
                            etMunicipioRef.setText(row.getString(4));
                        }
                    }else {
                        etColoniaRef.setText("No se encontró información");
                        etMunicipioRef.setText("No se encontró información");
                    }
                    row.close();
                }else {
                    etColoniaRef.setText("No se encontró información");
                    etMunicipioRef.setText("No se encontró información");
                }
            }
        });
        etColoniaRef.setOnClickListener(etColoniaRef_OnClick);
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
                    if (s.length() == 10)
                        etTelCelRef.setError(null);
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
                HashMap<Integer, String> params = new HashMap<>();
                if (checkedId == R.id.rbHombre){

                    params.put(0, etNombreCli.getText().toString());
                    params.put(1, etApPaternoCli.getText().toString());
                    params.put(2, etApMaternoCli.getText().toString());
                    params.put(3, etFechaNacCli.getText().toString());
                    params.put(4, "Hombre");

                    if (!etEstadoNacCli.getText().toString().trim().isEmpty())
                        params.put(5, etEstadoNacCli.getText().toString().trim());
                    else
                        params.put(5,"");
                    etCurpCli.setText(Miscellaneous.GenerarCurp(params));
                }
                else if(checkedId == R.id.rbMujer){
                    params.put(0, etNombreCli.getText().toString());
                    params.put(1, etApPaternoCli.getText().toString());
                    params.put(2, etApMaternoCli.getText().toString());
                    params.put(3, etFechaNacCli.getText().toString());
                    params.put(4, "Mujer");

                    if (!etEstadoNacCli.getText().toString().trim().isEmpty())
                        params.put(5, etEstadoNacCli.getText().toString().trim());
                    else
                        params.put(5,"");
                    etCurpCli.setText(Miscellaneous.GenerarCurp(params));
                }
                else {
                    params.put(0, etNombreCli.getText().toString());
                    params.put(1, etApPaternoCli.getText().toString());
                    params.put(2, etApMaternoCli.getText().toString());
                    params.put(3, etFechaNacCli.getText().toString());
                    params.put(4, "");

                    if (!etEstadoNacCli.getText().toString().trim().isEmpty())
                        params.put(5, etEstadoNacCli.getText().toString().trim());
                    else
                        params.put(5,"");
                    etCurpCli.setText(Miscellaneous.GenerarCurp(params));
                }
            }
        });
        //===========================  AVAL GENERO LISTENER  =======================================
        rgGeneroAval.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rbHombre){
                    HashMap<Integer, String> params = new HashMap<>();
                    params.put(0, etNombreAval.getText().toString());
                    params.put(1, etApPaternoAval.getText().toString());
                    params.put(2, etApMaternoAval.getText().toString());
                    params.put(3, etFechaNacAval.getText().toString());
                    params.put(4, "Hombre");

                    if (!etEstadoNacAval.getText().toString().trim().isEmpty())
                        params.put(5, etEstadoNacAval.getText().toString().trim());
                    else
                        params.put(5,"");
                    etCurpAval.setText(Miscellaneous.GenerarCurp(params));
                }
                else if(checkedId == R.id.rbMujer){
                    HashMap<Integer, String> params = new HashMap<>();
                    params.put(0, etNombreAval.getText().toString());
                    params.put(1, etApPaternoAval.getText().toString());
                    params.put(2, etApMaternoAval.getText().toString());
                    params.put(3, etFechaNacAval.getText().toString());
                    params.put(4, "Mujer");

                    if (!etEstadoNacAval.getText().toString().trim().isEmpty())
                        params.put(5, etEstadoNacAval.getText().toString().trim());
                    else
                        params.put(5,"");
                    etCurpAval.setText(Miscellaneous.GenerarCurp(params));
                }
                else {
                    HashMap<Integer, String> params = new HashMap<>();
                    params.put(0, etNombreAval.getText().toString());
                    params.put(1, etApPaternoAval.getText().toString());
                    params.put(2, etApMaternoAval.getText().toString());
                    params.put(3, etFechaNacAval.getText().toString());
                    params.put(4, "");

                    if (!etEstadoNacAval.getText().toString().trim().isEmpty())
                        params.put(5, etEstadoNacAval.getText().toString().trim());
                    else
                        params.put(5,"");
                    etCurpAval.setText(Miscellaneous.GenerarCurp(params));
                }
            }
        });

        mapCli.onCreate(savedInstanceState);
        mapNeg.onCreate(savedInstanceState);
        mapAval.onCreate(savedInstanceState);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
    }

    private View.OnClickListener ibFirmaCre_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i_firma_cre = new Intent(context, CapturarFirma.class);
            i_firma_cre.putExtra(Constants.TIPO,"");
            startActivityForResult(i_firma_cre,Constants.REQUEST_CODE_FIRMA_CRE);
        }
    };

    private View.OnClickListener etFechaDesembolso_OnClick = new View.OnClickListener() {
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

    private View.OnClickListener etFechaNac_OnClick = new View.OnClickListener() {
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

    private View.OnClickListener etColoniaAval_OnClick = new View.OnClickListener() {
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

    private View.OnClickListener etActivos_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            showActivos();
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
            i_colonia.putExtra(Constants.TITULO,Miscellaneous.ucFirst(Constants.ESTADOS));
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

    private View.OnClickListener etOcupacionClie_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i_ocupaciones = new Intent(context, Catalogos.class);
            i_ocupaciones.putExtra(Constants.TITULO,Miscellaneous.ucFirst(Constants.OCUPACIONES));
            i_ocupaciones.putExtra(Constants.CATALOGO,Constants.OCUPACIONES);
            i_ocupaciones.putExtra(Constants.REQUEST_CODE, Constants.REQUEST_CODE_OCUPACION_CLIE);
            startActivityForResult(i_ocupaciones,Constants.REQUEST_CODE_OCUPACION_CLIE);
        }
    };

    private View.OnClickListener etOcupacionConyuge_OnClick = new View.OnClickListener() {
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
            i_ocupaciones.putExtra(Constants.TITULO,Miscellaneous.ucFirst(Constants.OCUPACIONES));
            i_ocupaciones.putExtra(Constants.CATALOGO,Constants.OCUPACIONES);
            i_ocupaciones.putExtra(Constants.REQUEST_CODE, Constants.REQUEST_CODE_OCUPACION_NEG);
            startActivityForResult(i_ocupaciones,Constants.REQUEST_CODE_OCUPACION_NEG);
        }
    };

    //============ REFERNCIA =================================================================
    private View.OnClickListener etColoniaRef_OnClick = new View.OnClickListener() {
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
    private View.OnClickListener etFechaNacAval_OnClick = new View.OnClickListener() {
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

    private View.OnClickListener etEstadoNacAval_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i_estados = new Intent(context, Catalogos.class);
            i_estados.putExtra(Constants.TITULO,Miscellaneous.ucFirst(Constants.ESTADOS));
            i_estados.putExtra(Constants.CATALOGO,Constants.ESTADOS);
            i_estados.putExtra(Constants.REQUEST_CODE, Constants.REQUEST_CODE_ESTADO_NAC_AVAL);
            startActivityForResult(i_estados,Constants.REQUEST_CODE_ESTADO_NAC_AVAL);
        }
    };

    private View.OnClickListener etOcupacionAval_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i_ocupaciones = new Intent(context, Catalogos.class);
            i_ocupaciones.putExtra(Constants.TITULO,Miscellaneous.ucFirst(Constants.OCUPACIONES));
            i_ocupaciones.putExtra(Constants.CATALOGO,Constants.OCUPACIONES);
            i_ocupaciones.putExtra(Constants.REQUEST_CODE, Constants.REQUEST_CODE_OCUPACION_AVAL);
            startActivityForResult(i_ocupaciones,Constants.REQUEST_CODE_OCUPACION_AVAL);
        }
    };

    private View.OnClickListener etHoraLocAval_OnClick = new View.OnClickListener() {
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
            saveDatosPersonales();
            /*if (spEstadoCivilCli.getSelectedItemPosition() == 2 || spEstadoCivilCli.getSelectedItemPosition() == 5) {
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
            llDatosPersonales.setVisibility(View.GONE);*/


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


    private View.OnClickListener etHoraVisita_OnClick = new View.OnClickListener() {
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
        switch (requestCode){
            case Constants.REQUEST_CODE_ESTADO_NAC:
                if (resultCode == Constants.REQUEST_CODE_ESTADO_NAC){
                    if (data != null){
                        etEstadoNacCli.setText(((ModeloCatalogoGral)data.getSerializableExtra(Constants.ITEM)).getNombre());
                        HashMap<Integer, String> params = new HashMap<>();

                        params.put(0, etNombreCli.getText().toString());
                        params.put(1, etApPaternoCli.getText().toString());
                        params.put(2, etApMaternoCli.getText().toString());
                        params.put(3, etFechaNacCli.getText().toString());

                        if (rgGeneroCli.getCheckedRadioButtonId()==R.id.rbHombre)
                            params.put(4, "Hombre");
                        else if (rgGeneroCli.getCheckedRadioButtonId()==R.id.rbMujer)
                            params.put(4, "Mujer");
                        else
                            params.put(4, "");

                        if (!etEstadoNacCli.getText().toString().trim().isEmpty())
                            params.put(5, etEstadoNacCli.getText().toString().trim());
                        else
                            params.put(5,"");
                        etCurpCli.setText(Miscellaneous.GenerarCurp(params));
                    }
                }
                break;
            case Constants.REQUEST_CODE_OCUPACION_CLIE:
                if (resultCode == Constants.REQUEST_CODE_OCUPACION_CLIE){
                    if (data != null){
                        etOcupacionCli.setText(((ModeloCatalogoGral)data.getSerializableExtra(Constants.ITEM)).getNombre());
                    }
                }
                break;
            case Constants.REQUEST_CODE_OCUPACION_CONY:
                if (resultCode == Constants.REQUEST_CODE_OCUPACION_CONY){
                    if (data != null){
                        etOcupacionCony.setText(((ModeloCatalogoGral)data.getSerializableExtra(Constants.ITEM)).getNombre());
                    }
                }
                break;
            case Constants.REQUEST_CODE_OCUPACION_NEG:
                if (resultCode == Constants.REQUEST_CODE_OCUPACION_NEG){
                    if (data != null){
                        etActEconomicaNeg.setText(((ModeloCatalogoGral)data.getSerializableExtra(Constants.ITEM)).getNombre());
                    }
                }
                break;
            case Constants.REQUEST_CODE_ESTADO_NAC_AVAL:
                if (resultCode == Constants.REQUEST_CODE_ESTADO_NAC_AVAL){
                    if (data != null){
                        etEstadoNacAval.setText(((ModeloCatalogoGral)data.getSerializableExtra(Constants.ITEM)).getNombre());
                        HashMap<Integer, String> params = new HashMap<>();

                        params.put(0, etNombreAval.getText().toString());
                        params.put(1, etApPaternoAval.getText().toString());
                        params.put(2, etApMaternoAval.getText().toString());
                        params.put(3, etFechaNacAval.getText().toString());

                        if (rgGeneroAval.getCheckedRadioButtonId()==R.id.rbHombre)
                            params.put(4, "Hombre");
                        else if (rgGeneroAval.getCheckedRadioButtonId()==R.id.rbMujer)
                            params.put(4, "Mujer");
                        else
                            params.put(4, "");

                        if (!etEstadoNacAval.getText().toString().trim().isEmpty())
                            params.put(5, etEstadoNacAval.getText().toString().trim());
                        else
                            params.put(5,"");
                        etCurpAval.setText(Miscellaneous.GenerarCurp(params));
                    }
                }
                break;
            case Constants.REQUEST_CODE_OCUPACION_AVAL:
                if (resultCode == Constants.REQUEST_CODE_OCUPACION_AVAL){
                    if (data != null){
                        etOcupacionAval.setText(((ModeloCatalogoGral)data.getSerializableExtra(Constants.ITEM)).getNombre());
                        Cursor row = dBhelper.getRecords(Constants.SECTORES, " WHERE sector_id = " + (((ModeloCatalogoGral)data.getSerializableExtra(Constants.ITEM)).getExtra())+"","",null);
                        if (row.getCount() > 0){
                            row.moveToFirst();
                            etActividadEcoAval.setText(row.getString(2));
                        }
                        row.close();
                    }
                }
                break;
            case Constants.REQUEST_CODE_COLONIA_CLIE:
                if (resultCode == Constants.REQUEST_CODE_COLONIA_CLIE){
                    if (data != null){
                        etColoniaCli.setText(((ModeloCatalogoGral)data.getSerializableExtra(Constants.ITEM)).getNombre());
                    }
                }
                break;
            case Constants.REQUEST_CODE_COLONIA_AVAL:
                if (resultCode == Constants.REQUEST_CODE_COLONIA_AVAL){
                    if (data != null){
                        etColoniaAval.setText(((ModeloCatalogoGral)data.getSerializableExtra(Constants.ITEM)).getNombre());
                    }
                }
                break;
            case Constants.REQUEST_CODE_COLONIA_NEG:
                if (resultCode == Constants.REQUEST_CODE_COLONIA_NEG){
                    if (data != null){
                        etColoniaNeg.setText(((ModeloCatalogoGral)data.getSerializableExtra(Constants.ITEM)).getNombre());
                    }
                }
                break;
            case Constants.REQUEST_CODE_COLONIA_REF:
                if (resultCode == Constants.REQUEST_CODE_COLONIA_REF){
                    if (data != null){
                        etColoniaRef.setText(((ModeloCatalogoGral)data.getSerializableExtra(Constants.ITEM)).getNombre());
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
            switch (campo){
                case "desembolso":
                    etFechaDesembolso.setError(null);
                    etFechaDesembolso.setText(sdf.format(cal.getTime()));
                    String[] fechaDes = etFechaDesembolso.getText().toString().split("-");
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
                    etDiaDesembolso.setText(diaDesembolso);
                    break;
                case "fechaNacCli":
                    etFechaNacCli.setText(date);
                    etEdadCli.setText(Miscellaneous.GetEdad(sdf.format(cal.getTime())));
                    HashMap<Integer, String> params = new HashMap<>();

                    params.put(0, etNombreCli.getText().toString());
                    params.put(1, etApPaternoCli.getText().toString());
                    params.put(2, etApMaternoCli.getText().toString());
                    params.put(3, etFechaNacCli.getText().toString());

                    if (rgGeneroCli.getCheckedRadioButtonId()==R.id.rbHombre)
                        params.put(4, "Hombre");
                    else if (rgGeneroCli.getCheckedRadioButtonId()==R.id.rbMujer)
                        params.put(4, "Mujer");
                    else
                        params.put(4, "");

                    if (!etEstadoNacCli.getText().toString().trim().isEmpty())
                        params.put(5, etEstadoNacCli.getText().toString().trim());
                    else
                        params.put(5,"");
                    etCurpCli.setText(Miscellaneous.GenerarCurp(params));
                    break;
                case "fechaNacAval":
                    etFechaNacAval.setText(date);
                    etEdadAval.setText(Miscellaneous.GetEdad(sdf.format(cal.getTime())));
                    HashMap<Integer, String> paramsAval = new HashMap<>();

                    paramsAval.put(0, etNombreAval.getText().toString());
                    paramsAval.put(1, etApPaternoAval.getText().toString());
                    paramsAval.put(2, etApMaternoAval.getText().toString());
                    paramsAval.put(3, etFechaNacAval.getText().toString());

                    if (rgGeneroAval.getCheckedRadioButtonId()==R.id.rbHombre)
                        paramsAval.put(4, "Hombre");
                    else if (rgGeneroAval.getCheckedRadioButtonId()==R.id.rbMujer)
                        paramsAval.put(4, "Mujer");
                    else
                        paramsAval.put(4, "");

                    if (!etEstadoNacAval.getText().toString().trim().isEmpty())
                        paramsAval.put(5, etEstadoNacAval.getText().toString().trim());
                    else
                        paramsAval.put(5,"");
                    etCurpAval.setText(Miscellaneous.GenerarCurp(paramsAval));
                    break;
            }


        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    public void setTimer(String timer, String campo){
        switch (campo){
            case "HoraVisita":
                etHoraVisita.setError(null);
                etHoraVisita.setText(timer);
                break;
            case "HoraVisitaAval":
                etHoraLocAval.setText(timer);
                break;
        }
    }

    private void showActivos(){
        selectedItemsActivo = new ArrayList<>();
        new AlertDialog.Builder(SolicitudCreditoInd.this)
                .setTitle("Activos Observables")
                .setMultiChoiceItems(R.array.activos, null, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        if (isChecked) {
                            selectedItemsActivo.add(which);
                        } else if (selectedItemsActivo.contains(which)) {
                            selectedItemsActivo.remove(Integer.valueOf(which));
                        }
                    }
                })
                .setPositiveButton(R.string.accept, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        String activos = "";
                        Collections.sort(selectedItemsActivo);
                        for (int i = 0; i < selectedItemsActivo.size(); i++){
                            if(i == 0)
                                activos += _activos[selectedItemsActivo.get(i)];
                            else
                                activos += ", "+_activos[selectedItemsActivo.get(i)];
                            Log.e("Activos", _activos[selectedItemsActivo.get(i)]);
                        }
                        etActivosAval.setText(activos);
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                    }
                }).show();
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
                            Log.e("Dias de Venta", _dias_semana[selectedItemsDias.get(i)]);
                        }
                        etDiasVentaNeg.setText(dias);
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
                if (!validator.validate(etFechaDesembolso,new String[]{validator.REQUIRED}) &&
                !validator.validate(etDiaDesembolso,new String[]{validator.REQUIRED}) &&
                !validator.validate(etHoraVisita, new String[]{validator.REQUIRED}) &&
                !validator.validate(etMontoPrestamo, new String[]{validator.REQUIRED, validator.ONLY_NUMBER, validator.CREDITO}) &&
                !validator.validate(etCantidadLetra, new String[]{validator.REQUIRED, validator.ONLY_TEXT})){
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
        !validator.validate(etApMaternoCli, new String[]{validator.ONLY_TEXT}) &&
        !validator.validate(etFechaNacCli, new String[]{validator.REQUIRED}) &&
        !validator.validate(etEdadCli, new String[]{validator.REQUIRED, validator.ONLY_NUMBER})){
            if (rgGeneroCli.getCheckedRadioButtonId() == R.id.rbHombre || rgGeneroCli.getCheckedRadioButtonId() == R.id.rbMujer){
                if (!validator.validate(etEstadoNacCli, new String[]{validator.REQUIRED}) &&
                !validator.validate(etRfcCli, new String[]{validator.REQUIRED}) &&
                !validator.validate(etRfcClaveCli, new String[]{validator.HOMOCLAVE}) &&
                !validator.validate(etCurpCli, new String[]{validator.REQUIRED, validator.CURP}) &&
                !validator.validate(etCurpIdCli, new String[]{validator.REQUIRED, validator.CURP_ID}) &&
                !validator.validate(etOcupacionCli, new String[]{validator.REQUIRED}) &&
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
                                Toast.makeText(ctx, "verdatero", Toast.LENGTH_SHORT).show();
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
                if (!latitud.isEmpty() && !longitud.isEmpty()){
                    mapCli.setVisibility(View.VISIBLE);
                    Ubicacion(Double.parseDouble(latitud), Double.parseDouble(longitud));
                }
                else{
                    pbLoadCli.setVisibility(View.GONE);
                    Toast.makeText(ctx, getResources().getString(R.string.no_ubicacion), Toast.LENGTH_SHORT).show();
                }
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

                ibMapNeg.setEnabled(true);
                if (!latitud.isEmpty() && !longitud.isEmpty()){
                    mapNeg.setVisibility(View.VISIBLE);
                    UbicacionNeg(Double.parseDouble(latitud), Double.parseDouble(longitud));
                }
                else{
                    pbLoadNeg.setVisibility(View.GONE);
                    Toast.makeText(ctx, getResources().getString(R.string.no_ubicacion), Toast.LENGTH_SHORT).show();
                }
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

                ibMapAval.setEnabled(true);
                if (!latitud.isEmpty() && !longitud.isEmpty()){
                    mapAval.setVisibility(View.VISIBLE);
                    UbicacionAval(Double.parseDouble(latitud), Double.parseDouble(longitud));
                }
                else{
                    pbLoadAval.setVisibility(View.GONE);
                    Toast.makeText(ctx, getResources().getString(R.string.no_ubicacion), Toast.LENGTH_SHORT).show();
                }
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
}
