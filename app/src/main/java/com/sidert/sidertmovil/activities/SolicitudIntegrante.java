package com.sidert.sidertmovil.activities;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.ColorDrawable;
import android.location.LocationManager;
/*import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;*/
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
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

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.database.DBhelper;
import com.sidert.sidertmovil.utils.Miscellaneous;
import com.sidert.sidertmovil.utils.MyCurrentListener;
import com.sidert.sidertmovil.utils.Popups;
import com.sidert.sidertmovil.utils.Validator;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.sidert.sidertmovil.utils.Constants.TBL_CONYUGE_INTEGRANTE_AUTO;
import static com.sidert.sidertmovil.utils.Constants.TBL_CROQUIS_GPO_AUTO;
import static com.sidert.sidertmovil.utils.Constants.TBL_DOMICILIO_INTEGRANTE_AUTO;
import static com.sidert.sidertmovil.utils.Constants.TBL_INTEGRANTES_GPO_AUTO;
import static com.sidert.sidertmovil.utils.Constants.TBL_NEGOCIO_INTEGRANTE_AUTO;
import static com.sidert.sidertmovil.utils.Constants.TBL_OTROS_DATOS_INTEGRANTE_AUTO;
import static com.sidert.sidertmovil.utils.Constants.TBL_POLITICAS_PLD_INTEGRANTE_AUTO;
import static com.sidert.sidertmovil.utils.Constants.TBL_TELEFONOS_INTEGRANTE_AUTO;
import static com.sidert.sidertmovil.utils.Constants.warning;

public class SolicitudIntegrante extends AppCompatActivity {

    private Context ctx;
    private DBhelper dBhelper;
    private SQLiteDatabase db;

    private Validator validator = new Validator();

    private boolean is_edit = true;

    //===================  DATOS PERSONALES  ==================================
    private LinearLayout llComentCli;
    private TextView tvComentAdminCli;
    private TextView tvCargo;
    private EditText etNombreCli;
    private EditText etApPaternoCli;
    private EditText etApMaternoCli;
    private TextView tvFechaNacCli;
    private TextView tvEdadCli;
    private TextView tvGeneroCli;
    private RadioGroup rgGeneroCli;
    private TextView tvEstadoNacCli;
    private EditText etCurpCli;
    private TextView tvRfcCli;
    private TextView tvTipoIdentificacion;
    private EditText etNumIdentifCli;
    private TextView tvEstudiosCli;
    private TextView tvOcupacionCli;
    private TextView tvEstadoCivilCli;
    private LinearLayout llBienes;
    private TextView tvBienes;
    private RadioGroup rgBienes;
    //=========================================================================
    //===================  DATOS TELEFONICOS  =================================
    private EditText etTelCasaCli;
    private EditText etCelularCli;
    private EditText etTelMensCli;
    private EditText etTeltrabajoCli;
    //=========================================================================
    //===================  DATOS DOMICILIO  ====================================
    private TextView tvMapaCli;
    private ImageButton ibMapCli;
    private ProgressBar pbLoadCli;
    private MapView mapCli;
    private LatLng latLngUbiCli;
    private EditText etCalleCli;
    private EditText etNoExtCli;
    private EditText etNoIntCli;
    private EditText etManzanaCli;
    private EditText etLoteCli;
    private EditText etCpCli;
    private TextView tvColoniaCli;
    private EditText etCiudadCli;
    private TextView tvLocalidadCli;
    private TextView tvMunicipioCli;
    private TextView tvEstadoCli;
    private TextView tvTipoCasaCli;
    private LinearLayout llCasaFamiliar;
    private TextView tvCasaFamiliar;
    private LinearLayout llCasaOtroCli;
    private EditText etOTroTipoCli;
    private EditText etTiempoSitio;
    private TextView tvDependientes;
    private TextView tvFachadaCli;
    private ImageButton ibFotoFachCli;
    private ImageView ivFotoFachCli;
    private MultiAutoCompleteTextView etReferenciaCli;
    //=========================================================================
    //===================  DATOS NEGOCIO  =====================================
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
    private EditText etCiudadNeg;
    private TextView tvLocalidadNeg;
    private TextView tvMunicipioNeg;
    private TextView tvDestinoNeg;
    private EditText etOtroDestinoNeg;
    private TextView tvActEcoEspNeg;
    private TextView tvActEconomicaNeg;
    private EditText etAntiguedadNeg;
    private EditText etIngMenNeg;
    private EditText etOtrosIngNeg;
    private EditText etGastosSemNeg;
    private EditText etCapacidadPagoNeg;
    private TextView tvMontoMaxNeg;
    private TextView tvMediosPagoNeg;
    private EditText etOtroMedioPagoNeg;
    private TextView etNumOperacionNeg;
    private LinearLayout llOperacionesEfectivo;
    private TextView etNumOperacionEfectNeg;
    private TextView tvFachadaNeg;
    private ImageButton ibFotoFachNeg;
    private ImageView ivFotoFachNeg;
    private MultiAutoCompleteTextView etReferenciNeg;
    //=========================================================================
    //===================  DATOS CONYUGE  =====================================
    private EditText etNombreCony;
    private EditText etApPaternoCony;
    private EditText etApMaternoCony;
    private EditText etNacionalidad;
    private TextView tvOcupacionCony;
    private EditText etCalleCony;
    private EditText etNoExtCony;
    private EditText etNoIntCony;
    private EditText etManzanaCony;
    private EditText etLoteCony;
    private EditText etCpCony;
    private TextView tvColoniaCony;
    private EditText etCiudadCony;
    private TextView tvLocalidadCony;
    private TextView tvMunicipioCony;
    private TextView tvEstadoCony;
    private EditText etIngresoCony;
    private EditText etGastoCony;
    private EditText etCasaCony;
    private EditText etCelularCony;
    //=========================================================================
    //===================  OTROS DATOS  =======================================
    private TextView tvRiesgo;
    private TextView tvMedioContacto;
    private TextView tvEstadoCuenta;
    private EditText etEmail;
    private TextView tvEstatus;
    private RadioGroup rgEstatus;
    private EditText etCredSolicitado;
    private TextView tvCantidadLetra;
    private LinearLayout llCreditoAutorizado;
    private EditText etCredAutorizado;
    private LinearLayout llCantidadAutorizadaLetra;
    private TextView tvCantidadAutorizadoLetra;
    private CheckBox cbCasaReuniones;
    private TextView tvFirmaCli;
    private ImageButton ibFirmaCli;
    private ImageView ivFirmaCli;
    //=========================================================================
    //======= CROQUIS ========================
    private TextView tvCasa;
    private TextView tv1;
    private TextView tv2;
    private TextView tv3;
    private TextView tvCTrasera;
    private TextView tvFrontal;
    private TextView tv7;
    private TextView tv8;
    private TextView tv9;
    private TextView tvPrincipal;
    private TextView tvTrasera;
    private TextView tvLateraUno;
    private TextView tvLateraDos;
    private MultiAutoCompleteTextView etReferencia;
    //========================================
    //======= POLITICAS ======================
    private TextView tvPropietarioReal;
    private RadioGroup rgPropietarioReal;
    private TextView tvAnexoPropietario;
    private TextView tvProvedor;
    private RadioGroup rgProveedor;
    private TextView tvAnexoPreveedor;
    private TextView tvPoliticamenteExp;
    private RadioGroup rgPoliticamenteExp;
    private TextView tvAnexoPoliticamenteExp;
    //========================================
    //===================  DATOS NEGOCIO  =====================================
    private TextView tvIneFrontal;
    private ImageButton ibIneFrontal;
    private ImageView ivIneFrontal;
    private TextView tvIneReverso;
    private ImageButton ibIneReverso;
    private ImageView ivIneReverso;
    private LinearLayout llCurp;
    private TextView tvCurp;
    private ImageButton ibCurp;
    private ImageView ivCurp;
    private TextView tvComprobante;
    private ImageButton ibComprobante;
    private ImageView ivComprobante;
    //=========================================================================
    //===================  LINEAR LAYOUT  =====================================
    private LinearLayout llPersonales;
    private LinearLayout llTelefonicos;
    private LinearLayout llDomicilio;
    private LinearLayout llNegocio;
    private LinearLayout llConyuge;
    private LinearLayout llOtros;
    private LinearLayout llCroquis;
    private LinearLayout llPoliticas;
    private LinearLayout llDocumentos;

    private LinearLayout llDatosPersonales;
    private LinearLayout llDatosTelefonicos;
    private LinearLayout llDatosDomicilio;
    private LinearLayout llDatosNegocio;
    private LinearLayout llDatosConyuge;
    private LinearLayout llOtrosDatos;
    private LinearLayout llDatosCroquis;
    private LinearLayout llDatosPoliticas;
    private LinearLayout llDatosDocumentos;
    //=========================================================================
    //================= Image View ERROR  =====================
    private ImageView ivError1;
    private ImageView ivError2;
    private ImageView ivError3;
    private ImageView ivError4;
    private ImageView ivError5;
    private ImageView ivError6;
    private ImageView ivError7;
    private ImageView ivError8;
    private ImageView ivError9;
    //===================================================
    //===================  IMAGE VIEW  ========================================
    private ImageView ivDown1;
    private ImageView ivDown2;
    private ImageView ivDown3;
    private ImageView ivDown4;
    private ImageView ivDown5;
    private ImageView ivDown6;
    private ImageView ivDown7;
    private ImageView ivDown8;
    private ImageView ivDown9;

    private ImageView ivUp1;
    private ImageView ivUp2;
    private ImageView ivUp3;
    private ImageView ivUp4;
    private ImageView ivUp5;
    private ImageView ivUp6;
    private ImageView ivUp7;
    private ImageView ivUp8;
    private ImageView ivUp9;
    //=========================================================================

    private FloatingActionButton btnContinuar0;
    private FloatingActionButton btnContinuar1;
    private FloatingActionButton btnContinuar2;
    private FloatingActionButton btnContinuar3;
    private FloatingActionButton btnContinuar4;
    private FloatingActionButton btnContinuar7;
    private FloatingActionButton btnContinuar8;
    private FloatingActionButton btnContinuar5;

    private FloatingActionButton btnRegresar1;
    private FloatingActionButton btnRegresar2;
    private FloatingActionButton btnRegresar3;
    private FloatingActionButton btnRegresar4;
    private FloatingActionButton btnRegresar5;
    private FloatingActionButton btnRegresar7;
    private FloatingActionButton btnRegresar8;
    private FloatingActionButton btnRegresar6;

    private LocationManager locationManager;
    private MyCurrentListener locationListener;
    private GoogleMap mMapCli;
    private GoogleMap mMapNeg;

    private String id_credito = "";
    private String id_integrante = "";

    DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
    DecimalFormat df = new DecimalFormat("##,###.##", symbols);
    DecimalFormat dfnd = new DecimalFormat("#,###", symbols);

    boolean hasFractionalPart = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solicitud_integrante);

        ctx = this;
        dBhelper = new DBhelper(ctx);
        db = dBhelper.getWritableDatabase();

        Toolbar TBmain = findViewById(R.id.TBmain);
        setSupportActionBar(TBmain);
        //=================================  DATOS PERSONALES  =====================================
        llComentCli         = findViewById(R.id.llComentCli);
        tvComentAdminCli    = findViewById(R.id.tvComentAdminCli);
        tvCargo             = findViewById(R.id.tvCargo);
        etNombreCli         = findViewById(R.id.etNombreCli);
        etApPaternoCli      = findViewById(R.id.etApPaternoCli);
        etApMaternoCli      = findViewById(R.id.etApMaternoCli);
        tvFechaNacCli       = findViewById(R.id.tvFechaNacCli);
        tvEdadCli           = findViewById(R.id.tvEdadCli);
        tvGeneroCli         = findViewById(R.id.tvGeneroCli);
        rgGeneroCli         = findViewById(R.id.rgGeneroCli);
        tvEstadoNacCli      = findViewById(R.id.tvEstadoNacCli);
        tvRfcCli            = findViewById(R.id.tvRfcCli);
        etCurpCli           = findViewById(R.id.etCurpCli);
        //etCurpIdCli         = findViewById(R.id.etCurpIdCli);
        tvTipoIdentificacion = findViewById(R.id.tvTipoIdentificacion);
        etNumIdentifCli     = findViewById(R.id.etNumIdentifCli);
        tvEstudiosCli       = findViewById(R.id.tvEstudiosCli);
        tvOcupacionCli      = findViewById(R.id.tvOcupacionCli);
        tvEstadoCivilCli    = findViewById(R.id.tvEstadoCivilCli);
        llBienes            = findViewById(R.id.llBienes);
        tvBienes            = findViewById(R.id.tvBienes);
        rgBienes            = findViewById(R.id.rgBienes);
        //==========================================================================================
        //==================================  DATOS TELEFONICOS  ===================================
        etTelCasaCli        = findViewById(R.id.etTelCasaCli);
        etCelularCli        = findViewById(R.id.etCelularCli);
        etTelMensCli        = findViewById(R.id.etTelMensCli);
        etTeltrabajoCli     = findViewById(R.id.etTelTrabajoCli);
        //==========================================================================================
        //==================================  DATOS DOMICILIO  =====================================
        tvMapaCli           = findViewById(R.id.tvMapaCli);
        ibMapCli            = findViewById(R.id.ibMapCli);
        pbLoadCli           = findViewById(R.id.pbLoadCli);
        mapCli              = findViewById(R.id.mapCli);
        etCalleCli          = findViewById(R.id.etCalleCli);
        etNoExtCli          = findViewById(R.id.etNoExtCli);
        etNoIntCli          = findViewById(R.id.etNoIntCli);
        etManzanaCli        = findViewById(R.id.etManzanaCli);
        etLoteCli           = findViewById(R.id.etLoteCli);
        etCpCli             = findViewById(R.id.etCpCli);
        tvColoniaCli        = findViewById(R.id.tvColoniaCli);
        etCiudadCli         = findViewById(R.id.etCiudadCli);
        tvLocalidadCli      = findViewById(R.id.tvLocalidadCli);
        tvMunicipioCli      = findViewById(R.id.tvMunicipioCli);
        tvEstadoCli         = findViewById(R.id.tvEstadoCli);
        tvTipoCasaCli       = findViewById(R.id.tvTipoCasaCli);
        llCasaFamiliar      = findViewById(R.id.llCasaFamiliar);
        tvCasaFamiliar      = findViewById(R.id.tvCasaFamiliar);
        llCasaOtroCli       = findViewById(R.id.llCasaOtro);
        etOTroTipoCli       = findViewById(R.id.etOtroTipoCli);
        etTiempoSitio       = findViewById(R.id.etTiempoSitio);
        tvDependientes      = findViewById(R.id.tvDependientes);
        tvFachadaCli        = findViewById(R.id.tvFachadaCli);
        tvFachadaCli.setVisibility(View.GONE);
        ibFotoFachCli       = findViewById(R.id.ibFotoFachCli);
        ibFotoFachCli.setVisibility(View.GONE);
        ivFotoFachCli       = findViewById(R.id.ivFotoFachCli);
        etReferenciaCli     = findViewById(R.id.etReferenciaCli);
        //==========================================================================================
        //===================================  DATOS NEGOCIO  ======================================
        etNombreNeg             = findViewById(R.id.etNombreNeg);
        tvMapaNeg               = findViewById(R.id.tvMapaNeg);
        ibMapNeg                = findViewById(R.id.ibMapNeg);
        pbLoadNeg               = findViewById(R.id.pbLoadNeg);
        mapNeg                  = findViewById(R.id.mapNeg);
        etCalleNeg              = findViewById(R.id.etCalleNeg);
        etNoExtNeg              = findViewById(R.id.etNoExtNeg);
        etNoIntNeg              = findViewById(R.id.etNoIntNeg);
        etManzanaNeg            = findViewById(R.id.etManzanaNeg);
        etLoteNeg               = findViewById(R.id.etLoteNeg);
        etCpNeg                 = findViewById(R.id.etCpNeg);
        tvColoniaNeg            = findViewById(R.id.tvColoniaNeg);
        etCiudadNeg             = findViewById(R.id.etCiudadNeg);
        tvLocalidadNeg          = findViewById(R.id.tvLocalidadNeg);
        tvMunicipioNeg          = findViewById(R.id.tvMunicipioNeg);
        tvDestinoNeg            = findViewById(R.id.tvDestinoNeg);
        etOtroDestinoNeg        = findViewById(R.id.etOtroDestinoNeg);
        tvActEcoEspNeg          = findViewById(R.id.tvActEcoEspNeg);
        tvActEconomicaNeg       = findViewById(R.id.tvActEconomicaNeg);
        etAntiguedadNeg         = findViewById(R.id.etAntiguedadNeg);
        etIngMenNeg             = findViewById(R.id.etIngMenNeg);
        etOtrosIngNeg           = findViewById(R.id.etOtrosIngNeg);
        etGastosSemNeg          = findViewById(R.id.etGastosSemNeg);
        etCapacidadPagoNeg      = findViewById(R.id.etCapacidadPagoNeg);
        tvMontoMaxNeg           = findViewById(R.id.tvMontoMaxNeg);
        tvMediosPagoNeg         = findViewById(R.id.tvMediosPagoNeg);
        etOtroMedioPagoNeg      = findViewById(R.id.etOtroMedioPagoNeg);
        //etNumOperacionNeg       = findViewById(R.id.etNumOperacionNeg);//SE CAMBIA A TEXT VIEW
        llOperacionesEfectivo   = findViewById(R.id.llOperacionesEfectivo);
        etNumOperacionEfectNeg  = findViewById(R.id.etNumOperacionEfectNeg);//SE CAMBIA A TEXT VIEW
        tvFachadaNeg            = findViewById(R.id.tvFachadaNeg);
        tvFachadaNeg.setVisibility(View.GONE);
        ibFotoFachNeg           = findViewById(R.id.ibFotoFachNeg);
        ibFotoFachNeg.setVisibility(View.GONE);
        ivFotoFachNeg           = findViewById(R.id.ivFotoFachNeg);
        etReferenciNeg          = findViewById(R.id.etReferenciaNeg);
        //==========================================================================================
        //===================================  DATOS CONYUGE  ======================================
        etNombreCony        = findViewById(R.id.etNombreCony);
        etApPaternoCony     = findViewById(R.id.etApPaternoCony);
        etApMaternoCony     = findViewById(R.id.etApMaternoCony);
        etNacionalidad      = findViewById(R.id.etNacionalidad);
        tvOcupacionCony     = findViewById(R.id.tvOcupacionCony);
        etCalleCony         = findViewById(R.id.etCalleCony);
        etNoExtCony         = findViewById(R.id.etNoExtCony);
        etNoIntCony         = findViewById(R.id.etNoIntCony);
        etManzanaCony       = findViewById(R.id.etManzanaCony);
        etLoteCony          = findViewById(R.id.etLoteCony);
        etCpCony            = findViewById(R.id.etCpCony);
        tvColoniaCony       = findViewById(R.id.tvColoniaCony);
        etCiudadCony        = findViewById(R.id.etCiudadCony);
        tvLocalidadCony     = findViewById(R.id.tvLocalidadCony);
        tvMunicipioCony     = findViewById(R.id.tvMunicipioCony);
        tvEstadoCony        = findViewById(R.id.tvEstadoCony);
        etIngresoCony       = findViewById(R.id.etIngresoCony);
        etGastoCony         = findViewById(R.id.etGastoCony);
        etCasaCony          = findViewById(R.id.etCasaCony);
        etCelularCony       = findViewById(R.id.etCelularCony);

        //==========================================================================================
        //===================================  DATOS OTROS  ========================================
        tvRiesgo            = findViewById(R.id.tvRiesgo);
        tvMedioContacto     = findViewById(R.id.tvMedioContacto);
        tvEstadoCuenta      = findViewById(R.id.tvEstadoCuenta);
        etEmail             = findViewById(R.id.etEmail);
        tvEstatus           = findViewById(R.id.tvEstatus);
        rgEstatus           = findViewById(R.id.rgEstatus);
        etCredSolicitado    = findViewById(R.id.etCredSolicitado);
        tvCantidadLetra     = findViewById(R.id.tvCantidadLetra);
        llCreditoAutorizado = findViewById(R.id.llCreditoAutorizado);
        llCreditoAutorizado.setVisibility(View.VISIBLE);
        etCredAutorizado    = findViewById(R.id.etCredAutorizado);
        llCantidadAutorizadaLetra = findViewById(R.id.llCantidadAutorizadaLetra);
        llCantidadAutorizadaLetra.setVisibility(View.VISIBLE);
        tvCantidadAutorizadoLetra   = findViewById(R.id.tvCantidadAutorizadoLetra);
        cbCasaReuniones     = findViewById(R.id.cbCasaReuniones);
        tvFirmaCli          = findViewById(R.id.tvFirmaCli);
        tvFirmaCli.setVisibility(View.GONE);
        ibFirmaCli          = findViewById(R.id.ibFirmaCli);
        ibFirmaCli.setVisibility(View.GONE);
        ivFirmaCli          = findViewById(R.id.ivFirmaCli);
        //==========================================================================================
        //====================================  CROQUIS   ==========================================
        tv1                 = findViewById(R.id.tv1);
        tv2                 = findViewById(R.id.tv2);
        tv3                 = findViewById(R.id.tv3);
        tvCTrasera          = findViewById(R.id.tvCTrasera);
        tvFrontal           = findViewById(R.id.tvFrontal);
        tv7                 = findViewById(R.id.tv7);
        tv8                 = findViewById(R.id.tv8);
        tv9                 = findViewById(R.id.tv9);
        tvCasa              = findViewById(R.id.tvCasa);
        tvPrincipal         = findViewById(R.id.tvPrincipal);
        tvTrasera           = findViewById(R.id.tvTrasera);
        tvLateraUno         = findViewById(R.id.tvLateralUno);
        tvLateraDos         = findViewById(R.id.tvLateralDos);
        etReferencia        = findViewById(R.id.etReferencia);
        //==========================================================================================
        //==================================  DATOS POLITICAS   ====================================
        tvPropietarioReal       = findViewById(R.id.tvPropietarioReal);
        rgPropietarioReal       = findViewById(R.id.rgPropietarioReal);
        tvAnexoPropietario      = findViewById(R.id.tvAnexoPropietario);
        tvProvedor              = findViewById(R.id.tvProvedor);
        rgProveedor             = findViewById(R.id.rgProveedor);
        tvAnexoPreveedor        = findViewById(R.id.tvAnexoPreveedor);
        tvPoliticamenteExp      = findViewById(R.id.tvPoliticamenteExp);
        rgPoliticamenteExp      = findViewById(R.id.rgPoliticamenteExp);
        tvAnexoPoliticamenteExp = findViewById(R.id.tvAnexoPoliticamenteExp);
        //==========================================================================================
        //===================================  DOCUMENTOS  ========================================
        tvIneFrontal          = findViewById(R.id.tvIneFrontal);
        ibIneFrontal          = findViewById(R.id.ibIneFrontal);
        ivIneFrontal          = findViewById(R.id.ivIneFrontal);
        tvIneReverso          = findViewById(R.id.tvIneReverso);
        ibIneReverso          = findViewById(R.id.ibIneReverso);
        ivIneReverso          = findViewById(R.id.ivIneReverso);
        llCurp                = findViewById(R.id.llCurp);
        tvCurp                = findViewById(R.id.tvCurp);
        ibCurp                = findViewById(R.id.ibCurp);
        ivCurp                = findViewById(R.id.ivCurp);
        tvComprobante         = findViewById(R.id.tvComprobante);
        ibComprobante         = findViewById(R.id.ibComprobante);
        ivComprobante         = findViewById(R.id.ivComprobante);
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
        ivError9 = findViewById(R.id.ivError9);
        //=========================================================
        //============================ IMAGE VIEW UP|DOWN  =========================================
        ivDown1 = findViewById(R.id.ivDown1);
        ivDown2 = findViewById(R.id.ivDown2);
        ivDown3 = findViewById(R.id.ivDown3);
        ivDown4 = findViewById(R.id.ivDown4);
        ivDown5 = findViewById(R.id.ivDown5);
        ivDown6 = findViewById(R.id.ivDown6);
        ivDown7 = findViewById(R.id.ivDown7);
        ivDown8 = findViewById(R.id.ivDown8);
        ivDown9 = findViewById(R.id.ivDown9);

        ivUp1 = findViewById(R.id.ivUp1);
        ivUp2 = findViewById(R.id.ivUp2);
        ivUp3 = findViewById(R.id.ivUp3);
        ivUp4 = findViewById(R.id.ivUp4);
        ivUp5 = findViewById(R.id.ivUp5);
        ivUp6 = findViewById(R.id.ivUp6);
        ivUp7 = findViewById(R.id.ivUp7);
        ivUp8 = findViewById(R.id.ivUp8);
        ivUp9 = findViewById(R.id.ivUp9);
        //=========================================================
        //================ LINEAR LAYOUT  =========================
        llDatosPersonales   = findViewById(R.id.llDatosPersonales);
        llDatosTelefonicos  = findViewById(R.id.llDatosTelefonicos);
        llDatosDomicilio    = findViewById(R.id.llDatosDomicilio);
        llDatosNegocio      = findViewById(R.id.llDatosNegocio);
        llDatosConyuge      = findViewById(R.id.llDatosConyuge);
        llOtrosDatos        = findViewById(R.id.llOtrosDatos);
        llDatosCroquis      = findViewById(R.id.llDatosCroquis);
        llDatosPoliticas    = findViewById(R.id.llDatosPoliticas);
        llDatosDocumentos   = findViewById(R.id.llDatosDocumentos);
        llDatosDocumentos.setVisibility(View.GONE);

        llPersonales    = findViewById(R.id.llPersonales);
        llTelefonicos   = findViewById(R.id.llTelefonicos);
        llDomicilio     = findViewById(R.id.llDomicilio);
        llNegocio       = findViewById(R.id.llNegocio);
        llConyuge       = findViewById(R.id.llConyuge);
        llOtros         = findViewById(R.id.llOtros);
        llCroquis       = findViewById(R.id.llCroquis);
        llPoliticas     = findViewById(R.id.llPoliticas);
        llDocumentos    = findViewById(R.id.llDocumentos);
        llDocumentos.setVisibility(View.GONE);
        //=========================================================

        btnContinuar0 = findViewById(R.id.btnContinuar0);
        btnContinuar1 = findViewById(R.id.btnContinuar1);
        btnContinuar2 = findViewById(R.id.btnContinuar2);
        btnContinuar3 = findViewById(R.id.btnContinuar3);
        btnContinuar4 = findViewById(R.id.btnContinuar4);
        btnContinuar7 = findViewById(R.id.btnContinuar7);
        btnContinuar8 = findViewById(R.id.btnContinuar8);
        btnContinuar8.hide();
        btnContinuar5 = findViewById(R.id.btnContinuar5);

        btnRegresar1 = findViewById(R.id.btnRegresar1);
        btnRegresar2 = findViewById(R.id.btnRegresar2);
        btnRegresar3 = findViewById(R.id.btnRegresar3);
        btnRegresar4 = findViewById(R.id.btnRegresar4);
        btnRegresar5 = findViewById(R.id.btnRegresar5);
        btnRegresar7 = findViewById(R.id.btnRegresar7);
        btnRegresar8 = findViewById(R.id.btnRegresar8);
        btnRegresar6 = findViewById(R.id.btnRegresar6);

        mapCli.onCreate(savedInstanceState);
        mapNeg.onCreate(savedInstanceState);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        etNumOperacionNeg.setEnabled(false);
        etNumOperacionNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));

        llCurp.setVisibility(View.GONE);

        id_credito = getIntent().getStringExtra("id_credito");
        id_integrante = getIntent().getStringExtra("id_integrante");

        initComponents(getIntent().getStringExtra("id_credito"), getIntent().getStringExtra("id_integrante"));

        //============================== LINEAR LAYOUT LISTENER  ==================================
        llPersonales.setOnClickListener(llPersonales_OnClick);
        llTelefonicos.setOnClickListener(llTelefonicos_OnClick);
        llDomicilio.setOnClickListener(llDomicilio_OnClick);
        llNegocio.setOnClickListener(llNegocio_OnClick);
        llConyuge.setOnClickListener(llConyuge_OnClick);
        llOtros.setOnClickListener(llOtros_OnClick);
        llCroquis.setOnClickListener(llCroquis_OnClick);
        llPoliticas.setOnClickListener(llPoliticas_OnClick);
        //===========================================================================
        //==================================  OTROS LISTENER  ======================================
        etCredAutorizado.addTextChangedListener(new TextWatcher() {
            final String PATTERN_MONTO_CREDITO  = "[1-9][0-9][0-9][0][0][0]|[1-9][0-9][0][0][0]|[1-9][0][0][0]";
            Pattern pattern;
            Matcher matcher;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().contains(String.valueOf(df.getDecimalFormatSymbols().getDecimalSeparator())))
                {
                    hasFractionalPart = true;
                } else {
                    hasFractionalPart = false;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                etCredAutorizado.removeTextChangedListener(this);

                try {
                    int inilen, endlen;
                    inilen = etCredAutorizado.getText().length();
                    String v = s.toString().replace(String.valueOf(df.getDecimalFormatSymbols().getGroupingSeparator()), "");
                    Number n = df.parse(v);
                    int cp = etCredAutorizado.getSelectionStart();
                    if (hasFractionalPart) {
                        etCredAutorizado.setText(df.format(n));
                    } else {
                        etCredAutorizado.setText(dfnd.format(n));
                    }
                    endlen = etCredAutorizado.getText().length();
                    int sel = (cp + (endlen - inilen));
                    if (sel > 0 && sel <= etCredAutorizado.getText().length()) {
                        etCredAutorizado.setSelection(sel);
                    } else {
                        // place cursor at the end?
                        etCredAutorizado.setSelection(etCredAutorizado.getText().length() - 1);
                    }
                } catch (NumberFormatException nfe) {
                    // do nothing?
                } catch (ParseException e) {
                    // do nothing?
                }

                if (s.length()> 0){
                    pattern = Pattern.compile(PATTERN_MONTO_CREDITO);
                    matcher = pattern.matcher(s.toString().replace(",",""));
                    if(!matcher.matches()) {
                        tvCantidadAutorizadoLetra.setText("");
                        etCredAutorizado.setError("La cantidad no corresponde a un monto de crédito válido");
                        Update("monto_autorizado", TBL_OTROS_DATOS_INTEGRANTE_AUTO, "", "id_integrante", id_integrante);
                    }else{
                        Update("monto_autorizado", TBL_OTROS_DATOS_INTEGRANTE_AUTO, s.toString().trim().replace(",",""), "id_integrante", id_integrante);
                        tvCantidadAutorizadoLetra.setText((Miscellaneous.cantidadLetra(s.toString().replace(",","")).toUpperCase() + " PESOS MEXICANOS ").replace("  ", " "));
                    }
                }
                else{
                    tvCantidadAutorizadoLetra.setText("");
                    Update("monto_autorizado", TBL_OTROS_DATOS_INTEGRANTE_AUTO, "", "id_integrante", id_integrante);
                }

                etCredAutorizado.addTextChangedListener(this);
            }
        });

        //===========================================================================
        btnContinuar0.setOnClickListener(btnContinuar0_OnClick);
        btnContinuar1.setOnClickListener(btnContinuar1_OnClick);
        btnContinuar2.setOnClickListener(btnContinuar2_OnClick);
        btnContinuar3.setOnClickListener(btnContinuar3_OnClick);
        btnContinuar4.setOnClickListener(btnContinuar4_OnClick);
        btnContinuar5.setOnClickListener(btnContinuar5_OnClick);
        btnContinuar7.setOnClickListener(btnContinuar7_OnClick);
        btnContinuar8.setOnClickListener(btnContinuar8_OnClick);

        btnRegresar1.setOnClickListener(btnRegresar1_OnClick);
        btnRegresar2.setOnClickListener(btnRegresar2_OnClick);
        btnRegresar3.setOnClickListener(btnRegresar3_OnClick);
        btnRegresar4.setOnClickListener(btnRegresar4_OnClick);
        btnRegresar5.setOnClickListener(btnRegresar5_OnClick);
        btnRegresar7.setOnClickListener(btnRegresar7_OnClick);
        btnRegresar8.setOnClickListener(btnRegresar8_OnClick);
        btnRegresar6.setOnClickListener(btnRegresar6_OnClick);
    }

    //========================  ACTION LINEAR LAYOUT  ==============================================
    private View.OnClickListener llPersonales_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (ivDown1.getVisibility() == View.VISIBLE && ivUp1.getVisibility() == View.GONE){
                ivDown1.setVisibility(View.GONE);
                ivUp1.setVisibility(View.VISIBLE);
                llDatosPersonales.setVisibility(View.VISIBLE);
            }
            else if (ivDown1.getVisibility() == View.GONE && ivUp1.getVisibility() == View.VISIBLE){
                ivDown1.setVisibility(View.VISIBLE);
                ivUp1.setVisibility(View.GONE);
                llDatosPersonales.setVisibility(View.GONE);
            }
        }
    };

    private View.OnClickListener llTelefonicos_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (ivDown2.getVisibility() == View.VISIBLE && ivUp2.getVisibility() == View.GONE){
                ivDown2.setVisibility(View.GONE);
                ivUp2.setVisibility(View.VISIBLE);
                llDatosTelefonicos.setVisibility(View.VISIBLE);
            }
            else if (ivDown2.getVisibility() == View.GONE && ivUp2.getVisibility() == View.VISIBLE){
                ivDown2.setVisibility(View.VISIBLE);
                ivUp2.setVisibility(View.GONE);
                llDatosTelefonicos.setVisibility(View.GONE);
            }
        }
    };

    private View.OnClickListener llDomicilio_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (ivDown3.getVisibility() == View.VISIBLE && ivUp3.getVisibility() == View.GONE){
                ivDown3.setVisibility(View.GONE);
                ivUp3.setVisibility(View.VISIBLE);
                llDatosDomicilio.setVisibility(View.VISIBLE);
            }
            else if (ivDown3.getVisibility() == View.GONE && ivUp3.getVisibility() == View.VISIBLE){
                ivDown3.setVisibility(View.VISIBLE);
                ivUp3.setVisibility(View.GONE);
                llDatosDomicilio.setVisibility(View.GONE);
            }
        }
    };

    private View.OnClickListener llNegocio_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (ivDown4.getVisibility() == View.VISIBLE && ivUp4.getVisibility() == View.GONE){
                ivDown4.setVisibility(View.GONE);
                ivUp4.setVisibility(View.VISIBLE);
                llDatosNegocio.setVisibility(View.VISIBLE);
            }
            else if (ivDown4.getVisibility() == View.GONE && ivUp4.getVisibility() == View.VISIBLE){
                ivDown4.setVisibility(View.VISIBLE);
                ivUp4.setVisibility(View.GONE);
                llDatosNegocio.setVisibility(View.GONE);
            }
        }
    };

    private View.OnClickListener llConyuge_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (ivDown5.getVisibility() == View.VISIBLE && ivUp5.getVisibility() == View.GONE){
                ivDown5.setVisibility(View.GONE);
                ivUp5.setVisibility(View.VISIBLE);
                llDatosConyuge.setVisibility(View.VISIBLE);
            }
            else if (ivDown5.getVisibility() == View.GONE && ivUp5.getVisibility() == View.VISIBLE){
                ivDown5.setVisibility(View.VISIBLE);
                ivUp5.setVisibility(View.GONE);
                llDatosConyuge.setVisibility(View.GONE);
            }
        }
    };

    private View.OnClickListener llOtros_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (ivDown6.getVisibility() == View.VISIBLE && ivUp6.getVisibility() == View.GONE){
                ivDown6.setVisibility(View.GONE);
                ivUp6.setVisibility(View.VISIBLE);
                llOtrosDatos.setVisibility(View.VISIBLE);
            }
            else if (ivDown6.getVisibility() == View.GONE && ivUp6.getVisibility() == View.VISIBLE){
                ivDown6.setVisibility(View.VISIBLE);
                ivUp6.setVisibility(View.GONE);
                llOtrosDatos.setVisibility(View.GONE);
            }
        }
    };

    private View.OnClickListener llCroquis_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (ivDown7.getVisibility() == View.VISIBLE && ivUp7.getVisibility() == View.GONE){
                ivDown7.setVisibility(View.GONE);
                ivUp7.setVisibility(View.VISIBLE);
                llDatosCroquis.setVisibility(View.VISIBLE);
            }
            else if (ivDown7.getVisibility() == View.GONE && ivUp7.getVisibility() == View.VISIBLE){
                ivDown7.setVisibility(View.VISIBLE);
                ivUp7.setVisibility(View.GONE);
                llDatosCroquis.setVisibility(View.GONE);
            }
        }
    };

    private View.OnClickListener llPoliticas_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (ivDown8.getVisibility() == View.VISIBLE && ivUp8.getVisibility() == View.GONE){
                ivDown8.setVisibility(View.GONE);
                ivUp8.setVisibility(View.VISIBLE);
                llDatosPoliticas.setVisibility(View.VISIBLE);
            }
            else if (ivDown8.getVisibility() == View.GONE && ivUp8.getVisibility() == View.VISIBLE){
                ivDown8.setVisibility(View.VISIBLE);
                ivUp8.setVisibility(View.GONE);
                llDatosPoliticas.setVisibility(View.GONE);
            }
        }
    };


    //Continuar
    private View.OnClickListener btnContinuar0_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ivDown2.setVisibility(View.GONE);
            ivUp2.setVisibility(View.VISIBLE);
            llDatosTelefonicos.setVisibility(View.VISIBLE);

            ivDown1.setVisibility(View.VISIBLE);
            ivUp1.setVisibility(View.GONE);
            llDatosPersonales.setVisibility(View.GONE);

            etTelCasaCli.requestFocus();
        }
    };
    private View.OnClickListener btnContinuar1_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ivDown3.setVisibility(View.GONE);
            ivUp3.setVisibility(View.VISIBLE);
            llDatosDomicilio.setVisibility(View.VISIBLE);

            ivDown2.setVisibility(View.VISIBLE);
            ivUp2.setVisibility(View.GONE);
            llDatosTelefonicos.setVisibility(View.GONE);

            etCalleCli.requestFocus();
        }
    };
    private View.OnClickListener btnContinuar2_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ivDown4.setVisibility(View.GONE);
            ivUp4.setVisibility(View.VISIBLE);
            llDatosNegocio.setVisibility(View.VISIBLE);
            etNombreNeg.requestFocus();

            ivDown3.setVisibility(View.VISIBLE);
            ivUp3.setVisibility(View.GONE);
            llDatosDomicilio.setVisibility(View.GONE);
        }
    };
    private View.OnClickListener btnContinuar3_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ivDown4.setVisibility(View.VISIBLE);
            ivUp4.setVisibility(View.GONE);
            llDatosNegocio.setVisibility(View.GONE);

            if (tvEstadoCivilCli.getText().toString().equals("CASADO(A)") ||
                    tvEstadoCivilCli.getText().toString().equals("UNIÓN LIBRE")) {
                ivDown5.setVisibility(View.GONE);
                ivUp5.setVisibility(View.VISIBLE);
                llDatosConyuge.setVisibility(View.VISIBLE);
                etNombreCony.requestFocus();
            }
            else{
                ivDown6.setVisibility(View.GONE);
                ivUp6.setVisibility(View.VISIBLE);
                llOtrosDatos.setVisibility(View.VISIBLE);
                etEmail.requestFocus();
            }
        }
    };
    private View.OnClickListener btnContinuar4_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ivDown6.setVisibility(View.GONE);
            ivUp6.setVisibility(View.VISIBLE);
            llOtrosDatos.setVisibility(View.VISIBLE);

            ivDown5.setVisibility(View.VISIBLE);
            ivUp5.setVisibility(View.GONE);
            llDatosConyuge.setVisibility(View.GONE);

            etEmail.requestFocus();
        }
    };
    private View.OnClickListener btnContinuar5_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ivDown6.setVisibility(View.VISIBLE);
            ivUp6.setVisibility(View.GONE);
            llOtrosDatos.setVisibility(View.GONE);
            if (cbCasaReuniones.isChecked()){
                ivDown7.setVisibility(View.GONE);
                ivUp7.setVisibility(View.VISIBLE);
                llDatosCroquis.setVisibility(View.VISIBLE);
            }
            else{
                ivDown8.setVisibility(View.GONE);
                ivUp8.setVisibility(View.VISIBLE);
                llDatosPoliticas.setVisibility(View.VISIBLE);
            }
        }
    };

    private View.OnClickListener btnContinuar7_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ivDown7.setVisibility(View.VISIBLE);
            ivUp7.setVisibility(View.GONE);
            llDatosCroquis.setVisibility(View.GONE);

            ivDown8.setVisibility(View.GONE);
            ivUp8.setVisibility(View.VISIBLE);
            llDatosPoliticas.setVisibility(View.VISIBLE);
        }
    };

    private View.OnClickListener btnContinuar8_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ivDown8.setVisibility(View.VISIBLE);
            ivUp8.setVisibility(View.GONE);
            llDatosPoliticas.setVisibility(View.GONE);

            ivDown9.setVisibility(View.GONE);
            ivUp9.setVisibility(View.VISIBLE);
            llDatosDocumentos.setVisibility(View.VISIBLE);

        }
    };

    //Regresar
    private View.OnClickListener btnRegresar1_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ivDown1.setVisibility(View.GONE);
            ivUp1.setVisibility(View.VISIBLE);
            llDatosPersonales.setVisibility(View.VISIBLE);

            ivDown2.setVisibility(View.VISIBLE);
            ivUp2.setVisibility(View.GONE);
            llDatosTelefonicos.setVisibility(View.GONE);
            //etNombreCli.requestFocus();
        }
    };
    private View.OnClickListener btnRegresar2_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ivDown2.setVisibility(View.GONE);
            ivUp2.setVisibility(View.VISIBLE);
            llDatosTelefonicos.setVisibility(View.VISIBLE);

            ivDown3.setVisibility(View.VISIBLE);
            ivUp3.setVisibility(View.GONE);
            llDatosDomicilio.setVisibility(View.GONE);
            etTelCasaCli.requestFocus();
        }
    };
    private View.OnClickListener btnRegresar3_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ivDown3.setVisibility(View.GONE);
            ivUp3.setVisibility(View.VISIBLE);
            llDatosDomicilio.setVisibility(View.VISIBLE);

            ivDown4.setVisibility(View.VISIBLE);
            ivUp4.setVisibility(View.GONE);
            llDatosNegocio.setVisibility(View.GONE);
            etCalleCli.requestFocus();
        }
    };
    private View.OnClickListener btnRegresar4_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ivDown4.setVisibility(View.GONE);
            ivUp4.setVisibility(View.VISIBLE);
            llDatosNegocio.setVisibility(View.VISIBLE);

            ivDown5.setVisibility(View.VISIBLE);
            ivUp5.setVisibility(View.GONE);
            llDatosConyuge.setVisibility(View.GONE);
            etNombreNeg.requestFocus();
        }
    };
    private View.OnClickListener btnRegresar5_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ivDown6.setVisibility(View.VISIBLE);
            ivUp6.setVisibility(View.GONE);
            llOtrosDatos.setVisibility(View.GONE);

            if (tvEstadoCivilCli.getText().toString().equals("CASADO(A)") ||
                    tvEstadoCivilCli.getText().toString().equals("UNIÓN LIBRE")) {
                ivDown5.setVisibility(View.GONE);
                ivUp5.setVisibility(View.VISIBLE);
                llDatosConyuge.setVisibility(View.VISIBLE);
                etNombreCony.requestFocus();
            }
            else{
                ivDown4.setVisibility(View.GONE);
                ivUp4.setVisibility(View.VISIBLE);
                llDatosNegocio.setVisibility(View.VISIBLE);
                etNombreNeg.requestFocus();
            }

        }
    };

    private View.OnClickListener btnRegresar7_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            ivDown6.setVisibility(View.GONE);
            ivUp6.setVisibility(View.VISIBLE);
            llOtrosDatos.setVisibility(View.VISIBLE);

            ivDown7.setVisibility(View.VISIBLE);
            ivUp7.setVisibility(View.GONE);
            llDatosCroquis.setVisibility(View.GONE);
        }
    };

    private View.OnClickListener btnRegresar8_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (cbCasaReuniones.isChecked()){
                ivDown7.setVisibility(View.GONE);
                ivUp7.setVisibility(View.VISIBLE);
                llDatosCroquis.setVisibility(View.VISIBLE);
            }
            else {
                ivDown6.setVisibility(View.GONE);
                ivUp6.setVisibility(View.VISIBLE);
                llOtrosDatos.setVisibility(View.VISIBLE);
            }

            ivDown8.setVisibility(View.VISIBLE);
            ivUp8.setVisibility(View.GONE);
            llDatosPoliticas.setVisibility(View.GONE);
        }
    };

    private View.OnClickListener btnRegresar6_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ivDown9.setVisibility(View.VISIBLE);
            ivUp9.setVisibility(View.GONE);
            llDatosDocumentos.setVisibility(View.GONE);

            ivDown8.setVisibility(View.GONE);
            ivUp8.setVisibility(View.VISIBLE);
            llDatosPoliticas.setVisibility(View.VISIBLE);
        }
    };

    //===================== Listener GPS  =======================================================
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

    private void initComponents (String id_credito, String id_integrante){
        //                                                0                          1                               2                                    3                                  4                                             5                                6                                 7                                                8
        String sql = "SELECT i.estatus_completado AS eIntegrante, i.estado_civil AS civil, t.estatus_completado AS eTelefono, d.estatus_completado AS eDomiclio, n.estatus_completado AS eNegocio, COALESCE(c.estatus_completado, 1) AS eConyuge, o.estatus_completado AS eOtros, p.estatus_completado AS ePoliticas, COALESCE(cro.estatus_completado, 1) AS eCroquis FROM " + TBL_INTEGRANTES_GPO_AUTO + " AS i "+
                "INNER JOIN " + TBL_TELEFONOS_INTEGRANTE_AUTO + " AS t ON i.id = t.id_integrante " +
                "INNER JOIN " + TBL_DOMICILIO_INTEGRANTE_AUTO + " AS d ON i.id = d.id_integrante " +
                "INNER JOIN " + TBL_NEGOCIO_INTEGRANTE_AUTO + " AS n ON i.id = n.id_integrante " +
                "LEFT JOIN " + TBL_CONYUGE_INTEGRANTE_AUTO + " AS c ON i.id = c.id_integrante " +
                "INNER JOIN " + TBL_OTROS_DATOS_INTEGRANTE_AUTO + " AS o ON i.id = o.id_integrante " +
                "LEFT JOIN " + TBL_CROQUIS_GPO_AUTO + " AS cro ON i.id = cro.id_integrante AND o.casa_reunion = 1 " +
                "INNER JOIN " + TBL_POLITICAS_PLD_INTEGRANTE_AUTO + " AS p ON i.id = p.id_integrante " +
                "WHERE i.id_credito = ? AND i.id = ? ";
        Cursor row = db.rawQuery(sql, new String[]{id_credito, id_integrante});
        row.moveToFirst();

        if ((row.getInt(0) == 1 || row.getInt(0) == 2 || row.getInt(0) == 3) &&
                row.getInt(2) == 1 &&
                row.getInt(3) == 1 &&
                row.getInt(4) == 1 &&
                ((row.getString(1).equals("CASADO(A)") ||
                        row.getString(1).equals("UNIÓN LIBRE") &&
                                row.getInt(5) == 1) ||
                        (row.getString(1).equals("SOLTERO(A)") ||
                                row.getString(1).equals("VIUDO(A)") ||
                                row.getString(1).equals("DIVORCIADO(A)"))) &&
                row.getInt(6) == 1 &&
                row.getInt(7) == 1 &&
                row.getInt(8) == 1){
            is_edit = false;
        }

        invalidateOptionsMenu();
        //Toast.makeText(ctx, String.valueOf(is_edit), Toast.LENGTH_SHORT).show();
        row.close(); //Cierra datos de estatus de todas las tablas

        row = dBhelper.getRecords(TBL_INTEGRANTES_GPO_AUTO, " WHERE id = ? AND id_credito = ?", "", new String[]{id_integrante, id_credito});
        row.moveToFirst();

        if (!row.getString(20).trim().isEmpty()){
            llComentCli.setVisibility(View.VISIBLE);
            tvComentAdminCli.setText(row.getString(20).toUpperCase());
        }

        switch (row.getInt(2)){
            case 1:
                tvCargo.setText(getResources().getString(R.string.presidente).toUpperCase());
                break;
            case 3:
                tvCargo.setText(getResources().getString(R.string.tesorera).toUpperCase());
                break;
            case 2:
                tvCargo.setText(getResources().getString(R.string.secretaria).toUpperCase());
                break;
            case 4:
                tvCargo.setText(getResources().getString(R.string.integrante).toUpperCase());
                break;
        }

        //isNuevo = row.getInt(23) == 1;
        //is_edit = false;

        etNombreCli.setText(row.getString(3)); etNombreCli.setEnabled(false);
        etApPaternoCli.setText(row.getString(4)); etApPaternoCli.setEnabled(false);
        etApMaternoCli.setText(row.getString(5)); etApMaternoCli.setEnabled(false);
        tvFechaNacCli.setText(row.getString(6));
        if (is_edit) {
            tvFechaNacCli.setBackground(ContextCompat.getDrawable(ctx, R.drawable.et_rounded_edges));
            tvEstadoNacCli.setBackground(ContextCompat.getDrawable(ctx, R.drawable.et_rounded_edges));
            etCurpCli.setEnabled(is_edit);
            etCurpCli.setBackground(ContextCompat.getDrawable(ctx, R.drawable.et_rounded_edges));
            llCurp.setVisibility(View.VISIBLE);
        }
        else {
            for(int i = 0; i < rgGeneroCli.getChildCount(); i++){
                ((RadioButton) rgGeneroCli.getChildAt(i)).setEnabled(false);
            }
            tvFechaNacCli.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            tvEstadoNacCli.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etCurpCli.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        }
        tvEdadCli.setText(row.getString(7));
        switch (row.getInt(8)){
            case 0:
                rgGeneroCli.check(R.id.rbHombre);
                break;
            case 1:
                rgGeneroCli.check(R.id.rbMujer);
                break;
        }

        tvEstadoNacCli.setText(row.getString(9));
        tvRfcCli.setText(row.getString(10));
        etCurpCli.setText(row.getString(11));
        //etCurpIdCli.setText(row.getString(12));
        tvTipoIdentificacion.setText(row.getString(13));
        etNumIdentifCli.setText(row.getString(14));
        tvEstudiosCli.setText(row.getString(15));
        tvOcupacionCli.setText(row.getString(16));
        tvEstadoCivilCli.setText(row.getString(17));
        switch (row.getString(17)){
            case "CASADO(A)":
                llConyuge.setVisibility(View.VISIBLE);
                llBienes.setVisibility(View.VISIBLE);
                switch (row.getInt(18)){
                    case 1:
                        rgBienes.check(R.id.rbMancomunados);
                        break;
                    case 2:
                        rgBienes.check(R.id.rbSeparados);
                        break;
                }
                break;
            case "UNIÓN LIBRE":
                llConyuge.setVisibility(View.VISIBLE);
                break;
        }
        row.close(); //Cierra datos personales del integrante

        //Datos telefonicos
        row = dBhelper.getRecords(TBL_TELEFONOS_INTEGRANTE_AUTO, " WHERE id_integrante = ?", "", new String[]{id_integrante});
        row.moveToFirst();

        etTelCasaCli.setText(row.getString(2));
        etCelularCli.setText(row.getString(3));
        etTelMensCli.setText(row.getString(4));
        etTeltrabajoCli.setText(row.getString(5));
        row.close(); //Cierra datos telefonicos del integrante

        //Datos domicilio
        row = dBhelper.getRecords(TBL_DOMICILIO_INTEGRANTE_AUTO, " WHERE id_integrante = ?", "", new String[]{id_integrante});
        row.moveToFirst();
        if (!row.getString(2).isEmpty() && !row.getString(3).isEmpty()){
            mapCli.setVisibility(View.VISIBLE);
            Ubicacion(row.getDouble(2), row.getDouble(3));
        }
        etCalleCli.setText(row.getString(4));
        etNoExtCli.setText(row.getString(5));
        etNoIntCli.setText(row.getString(6));
        etManzanaCli.setText(row.getString(7));
        etLoteCli.setText(row.getString(8));
        etCpCli.setText(row.getString(9));
        tvColoniaCli.setText(row.getString(10));
        etCiudadCli.setText(row.getString(11));
        tvLocalidadCli.setText(row.getString(12));
        if (!row.getString(13).trim().isEmpty())
            tvMunicipioCli.setText(row.getString(13));
        else
            tvMunicipioCli.setText(R.string.not_found);

        if (!row.getString(14).trim().isEmpty())
            tvEstadoCli.setText(row.getString(14));
        else
            tvEstadoCli.setText(R.string.not_found);

        tvTipoCasaCli.setText(row.getString(15));
        switch (row.getString(15)){
            case "CASA FAMILIAR":
                llCasaFamiliar.setVisibility(View.VISIBLE);
                tvCasaFamiliar.setText(row.getString(16));
                break;
            case "OTRO":
                llCasaOtroCli.setVisibility(View.VISIBLE);
                etOTroTipoCli.setText(row.getString(17));
                break;
        }
        etTiempoSitio.setText(row.getString(18));
        tvDependientes.setText(row.getString(22));
        etReferenciaCli.setText(row.getString(20));
        row.close(); //Cierra datos del domicilio del integrante

        //Datos Negocio
        row = dBhelper.getRecords(TBL_NEGOCIO_INTEGRANTE_AUTO, " WHERE id_integrante = ?", "", new String[]{id_integrante});
        row.moveToFirst();
        etNombreNeg.setText(row.getString(2));
        if (!row.getString(3).isEmpty() && !row.getString(4).isEmpty()){
            mapNeg.setVisibility(View.VISIBLE);
            UbicacionNeg(row.getDouble(3), row.getDouble(4));
        }
        etCalleNeg.setText(row.getString(5));
        etNoExtNeg.setText(row.getString(6));
        etNoIntNeg.setText(row.getString(7));
        etManzanaNeg.setText(row.getString(8));
        etLoteNeg.setText(row.getString(9));
        etCpNeg.setText(row.getString(10));
        tvColoniaNeg.setText(row.getString(11));
        etCiudadNeg.setText(row.getString(12));
        tvLocalidadNeg.setText(row.getString(13));
        if (!row.getString(14).trim().isEmpty())
            tvMunicipioNeg.setText(row.getString(14));
        else
            tvMunicipioNeg.setText(R.string.not_found);

        tvDestinoNeg.setText(row.getString(16));
        if (row.getString(16).equals("OTRO")) {
            etOtroDestinoNeg.setText(row.getString(17));
            etOtroDestinoNeg.setEnabled(is_edit);
            etOtroDestinoNeg.setVisibility(View.VISIBLE);
        }
        tvActEcoEspNeg.setText(row.getString(18));
        tvActEconomicaNeg.setText(row.getString(19));
        etAntiguedadNeg.setText(row.getString(20)); etAntiguedadNeg.setEnabled(is_edit);
        if (!row.getString(21).trim().isEmpty())
            etIngMenNeg.setText(dfnd.format(row.getInt(21))); etIngMenNeg.setEnabled(is_edit);
        if (!row.getString(22).trim().isEmpty())
            etOtrosIngNeg.setText(dfnd.format(row.getInt(22))); etOtrosIngNeg.setEnabled(is_edit);
        if (!row.getString(23).trim().isEmpty())
            etGastosSemNeg.setText(dfnd.format(row.getInt(23))); etGastosSemNeg.setEnabled(is_edit);
        if (!row.getString(24).trim().isEmpty())
            etCapacidadPagoNeg.setText(dfnd.format(row.getInt(24))); etCapacidadPagoNeg.setEnabled(is_edit);
        if (!row.getString(25).trim().isEmpty())
            tvMontoMaxNeg.setText(dfnd.format(row.getInt(25)));
        tvMediosPagoNeg.setText(row.getString(26));
        if (row.getString(26).contains("OTRO")){
            etOtroMedioPagoNeg.setText(row.getString(27));
            etOtroMedioPagoNeg.setVisibility(View.VISIBLE);
        }

        if (tvMediosPagoNeg.getText().toString().trim().toUpperCase().contains("EFECTIVO"))
            llOperacionesEfectivo.setVisibility(View.VISIBLE);
        else
            llOperacionesEfectivo.setVisibility(View.GONE);

        etNumOperacionNeg.setText((row.getString(28).isEmpty())?"":row.getString(28));
        etNumOperacionEfectNeg.setText((row.getString(29).isEmpty())?"":row.getString(29));

        etReferenciNeg.setText(row.getString(31));
        row.close(); //Cierra datos del negocio

        //Datos Conyuge
        if (tvEstadoCivilCli.getText().equals("CASADO(A)") || tvEstadoCivilCli.getText().equals("UNIÓN LIBRE")) {
            row = dBhelper.getRecords(TBL_CONYUGE_INTEGRANTE_AUTO, " WHERE id_integrante = ?", "", new String[]{id_integrante});
            row.moveToFirst();
            etNombreCony.setText(row.getString(2));
            etApPaternoCony.setText(row.getString(3));
            etApMaternoCony.setText(row.getString(4));
            etNacionalidad.setText(row.getString(5));
            tvOcupacionCony.setText(row.getString(6));

            etCalleCony.setText(row.getString(7));
            etNoExtCony.setText(row.getString(8));
            etNoIntCony.setText(row.getString(9));
            etManzanaCony.setText(row.getString(10));
            etLoteCony.setText(row.getString(11));
            etCpCony.setText(row.getString(12));
            tvColoniaCony.setText(row.getString(13));
            etCiudadCony.setText(row.getString(14));
            tvLocalidadCony.setText(row.getString(15));
            if (!row.getString(16).trim().isEmpty())
                tvMunicipioCony.setText(row.getString(16));
            else
                tvMunicipioCony.setText(R.string.not_found);

            if (!row.getString(17).trim().isEmpty())
                tvEstadoCony.setText(row.getString(17));
            else
                tvEstadoCony.setText(R.string.not_found);

            if (!row.getString(18).trim().isEmpty())
                etIngresoCony.setText(dfnd.format(row.getInt(18)));
            etIngresoCony.setEnabled(is_edit);
            if (!row.getString(19).trim().isEmpty())
                etGastoCony.setText(dfnd.format(row.getInt(19)));
            etGastoCony.setEnabled(is_edit);
            etCelularCony.setText(row.getString(20));
            etCelularCony.setEnabled(is_edit);
            etCasaCony.setText(row.getString(21));
            etCasaCony.setEnabled(is_edit);
            row.close(); // Cierra datos del conyuge
        }

        //Datos Otros
        row = dBhelper.getRecords(TBL_OTROS_DATOS_INTEGRANTE_AUTO, " WHERE id_integrante = ?", "", new String[]{id_integrante});
        row.moveToFirst();
        tvRiesgo.setText(row.getString(2));
        tvMedioContacto.setText(row.getString(3));
        tvEstadoCuenta.setText(row.getString(5));
        etEmail.setText(row.getString(4));
        switch (row.getInt(6)){
            case 1:
                rgEstatus.check(R.id.rbNuevo);
                break;
            case 2:
                rgEstatus.check(R.id.rbRenovado);
                break;
            case 3:
                rgEstatus.check(R.id.rbCambio);
                break;
        }
        if (!row.getString(7).trim().isEmpty()) {
            etCredSolicitado.setText(dfnd.format(row.getInt(7)));
            etCredSolicitado.setEnabled(is_edit);
            tvCantidadLetra.setText((Miscellaneous.cantidadLetra(row.getString(7)).toUpperCase() + " PESOS MEXICANOS ").replace("  ", " "));
        }

        if (!row.getString(11).trim().isEmpty()) {
            etCredAutorizado.setText(dfnd.format(row.getInt(11)));
            etCredAutorizado.setEnabled(false);
            tvCantidadAutorizadoLetra.setText((Miscellaneous.cantidadLetra(row.getString(11)).toUpperCase() + " PESOS MEXICANOS ").replace("  ", " "));
            etCredAutorizado.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        }

        if (row.getInt(8) == 1) {
            cbCasaReuniones.setChecked(true);
            llCroquis.setVisibility(View.VISIBLE);
        }
        Cursor row_casa = dBhelper.customSelect(TBL_INTEGRANTES_GPO_AUTO + " AS i INNER JOIN " + TBL_OTROS_DATOS_INTEGRANTE_AUTO + " AS od ON od.id_integrante = i.id", "i.id", " WHERE i.id_credito = " + id_credito + " AND od.casa_reunion = 1", "", null);
        row_casa.moveToFirst();
        if (row_casa.getCount() > 0 && row_casa.getInt(0) != Integer.parseInt(id_integrante)){
            cbCasaReuniones.setEnabled(false);
        }
        row_casa.close();

        if (cbCasaReuniones.isChecked()) {
            row = dBhelper.getRecords(TBL_CROQUIS_GPO_AUTO, " WHERE id_integrante = ?", "", new String[]{id_integrante});
            row.moveToFirst();
            tvPrincipal.setText(row.getString(2).trim().toUpperCase());
            tvLateraUno.setText(row.getString(3).trim().toUpperCase());
            tvLateraDos.setText(row.getString(4).trim().toUpperCase());
            tvTrasera.setText(row.getString(5).trim().toUpperCase());
            etReferencia.setText(row.getString(6));
            etReferencia.setEnabled(is_edit);
            row.close(); //Cierra datos del croquis
        }

        //Politicas
        row = dBhelper.getRecords(TBL_POLITICAS_PLD_INTEGRANTE_AUTO, " WHERE id_integrante = ?", "", new String[]{id_integrante});
        row.moveToFirst();
        switch (row.getInt(2)){
            case 1:
                rgPropietarioReal.check(R.id.rbSiPropietario);
                tvAnexoPropietario.setVisibility(View.VISIBLE);
                break;
            case 2:
                rgPropietarioReal.check(R.id.rbNoPropietario);
                break;
        }
        switch (row.getInt(3)){
            case 1:
                rgProveedor.check(R.id.rbSiProveedor);
                tvAnexoPreveedor.setVisibility(View.VISIBLE);
                break;
            case 2:
                rgProveedor.check(R.id.rbNoProveedor);
                break;
        }
        switch (row.getInt(4)){
            case 1:
                rgPoliticamenteExp.check(R.id.rbSiExpuesta);
                tvAnexoPoliticamenteExp.setVisibility(View.VISIBLE);
                break;
            case 2:
                rgPoliticamenteExp.check(R.id.rbNoexpuesta);
                break;
        }
        row.close(); //Cierra datos de politicas pld

        /*if (!is_edit){
            invalidateOptionsMenu();*/

            tvFechaNacCli.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            for(int i = 0; i < rgGeneroCli.getChildCount(); i++){
                ((RadioButton) rgGeneroCli.getChildAt(i)).setEnabled(false);
            }
            tvEstadoNacCli.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etCurpCli.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etCurpCli.setEnabled(false);
            tvTipoIdentificacion.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etNumIdentifCli.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etNumIdentifCli.setEnabled(false);
            tvEstudiosCli.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            tvOcupacionCli.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            tvEstadoCivilCli.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            for(int i = 0; i < rgBienes.getChildCount(); i++){
                ((RadioButton) rgBienes.getChildAt(i)).setEnabled(false);
            }

            etTelCasaCli.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etTelCasaCli.setEnabled(false);
            etCelularCli.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etCelularCli.setEnabled(false);
            etTelMensCli.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etTelMensCli.setEnabled(false);
            etTeltrabajoCli.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etTeltrabajoCli.setEnabled(false);

            etCalleCli.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etCalleCli.setEnabled(false);
            etNoExtCli.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etNoExtCli.setEnabled(false);
            etNoIntCli.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etNoIntCli.setEnabled(false);
            etManzanaCli.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etManzanaCli.setEnabled(false);
            etLoteCli.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etLoteCli.setEnabled(false);
            etCpCli.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etCpCli.setEnabled(false);
            tvColoniaCli.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etCiudadCli.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etCiudadCli.setEnabled(false);
            tvLocalidadCli.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            tvTipoCasaCli.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            tvCasaFamiliar.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etTiempoSitio.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etTiempoSitio.setEnabled(false);
            tvDependientes.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etReferenciaCli.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etReferenciaCli.setEnabled(false);

            etNombreNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etNombreNeg.setEnabled(false);
            etCalleNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etCalleNeg.setEnabled(false);
            etNoExtNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etNoExtNeg.setEnabled(false);
            etNoIntNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etNoIntNeg.setEnabled(false);
            etManzanaNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etManzanaNeg.setEnabled(false);
            etLoteNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etLoteNeg.setEnabled(false);
            etCpNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etCpNeg.setEnabled(false);
            tvColoniaNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etCiudadNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etCiudadNeg.setEnabled(false);
            tvLocalidadNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            tvDestinoNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etOtroDestinoNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etOtroDestinoNeg.setEnabled(false);
            tvActEcoEspNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            tvActEconomicaNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etAntiguedadNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etAntiguedadNeg.setEnabled(false);
            etIngMenNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etIngMenNeg.setEnabled(false);
            etOtrosIngNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etOtrosIngNeg.setEnabled(false);
            etGastosSemNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etGastosSemNeg.setEnabled(false);
            etCapacidadPagoNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etCapacidadPagoNeg.setEnabled(false);
            tvMediosPagoNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etNumOperacionNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etNumOperacionNeg.setEnabled(false);
            etNumOperacionEfectNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etNumOperacionEfectNeg.setEnabled(false);
            etReferenciNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etReferenciNeg.setEnabled(false);

            etNombreCony.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etNombreCony.setEnabled(false);
            etApPaternoCony.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etApPaternoCony.setEnabled(false);
            etApMaternoCony.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etApMaternoCony.setEnabled(false);
            etNacionalidad.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etNacionalidad.setEnabled(false);
            tvOcupacionCony.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etCalleCony.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etCalleCony.setEnabled(false);
            etNoExtCony.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etNoExtCony.setEnabled(false);
            etNoIntCony.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etNoIntCony.setEnabled(false);
            etManzanaCony.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etManzanaCony.setEnabled(false);
            etLoteCony.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etLoteCony.setEnabled(false);
            etCpCony.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etCpCony.setEnabled(false);
            tvColoniaCony.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etCiudadCony.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etCiudadCony.setEnabled(false);
            tvLocalidadCony.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etCelularCony.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etCelularCony.setEnabled(false);
            etCasaCony.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etCasaCony.setEnabled(false);
            etIngresoCony.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etIngresoCony.setEnabled(false);
            etGastoCony.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etGastoCony.setEnabled(false);

            tvRiesgo.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            tvMedioContacto.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            tvEstadoCuenta.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etEmail.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etEmail.setEnabled(false);
            for(int i = 0; i < rgEstatus.getChildCount(); i++){
                ((RadioButton) rgEstatus.getChildAt(i)).setEnabled(false);
            }
            etCredSolicitado.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etCredSolicitado.setEnabled(false);
            cbCasaReuniones.setEnabled(false);

            etReferencia.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etReferencia.setEnabled(false);

            for(int i = 0; i < rgPropietarioReal.getChildCount(); i++){
                ((RadioButton) rgPropietarioReal.getChildAt(i)).setEnabled(false);
            }
            for(int i = 0; i < rgProveedor.getChildCount(); i++){
                ((RadioButton) rgProveedor.getChildAt(i)).setEnabled(false);
            }
            for(int i = 0; i < rgPoliticamenteExp.getChildCount(); i++){
                ((RadioButton) rgPoliticamenteExp.getChildAt(i)).setEnabled(false);
            }


        //}

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_save, menu);
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
                finish();
                break;
            case R.id.save:
                final AlertDialog loading = Popups.showLoadingDialog(ctx, R.string.please_wait, R.string.loading_info);
                loading.show();


                if (!validator.validate(etCredAutorizado, new String[]{validator.REQUIRED, validator.MONEY, validator.CREDITO})){

                    ContentValues cv = new ContentValues();
                    cv.put("estatus_completado", 1);
                    cv.put("monto_autorizado", etCredAutorizado.getText().toString().trim().replace(",",""));
                    db.update(TBL_OTROS_DATOS_INTEGRANTE_AUTO, cv, "id_integrante = ?", new String[]{String.valueOf(id_integrante)});

                    loading.dismiss();
                    //Toast.makeText(ctx, "termina guardado", Toast.LENGTH_SHORT).show();

                    finish();

                }
                else {
                    loading.dismiss();
                    final AlertDialog solicitud;
                    solicitud = Popups.showDialogMessage(this, warning,
                            "Faltan colocar el monto autorizado", R.string.accept, new Popups.DialogMessage() {
                                @Override
                                public void OnClickListener(AlertDialog dialog) {
                                    dialog.dismiss();
                                }
                            });
                    solicitud.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                    solicitud.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                    solicitud.show();

                }
                /*boolean datos_personales = saveDatosIntegrante();
                boolean datos_telefonicos = saveDatosTelefonicos();
                boolean datos_domiclio = saveDatosDomicilio();
                boolean datos_negocio = saveDatosNegocio();
                boolean datos_conyuge = false;
                if (tvEstadoCivilCli.getText().toString().equals("CASADO(A)") ||
                        tvEstadoCivilCli.getText().toString().equals("UNIÓN LIBRE")){
                    datos_conyuge = saveConyuge();
                }
                else
                    datos_conyuge = true;
                boolean datos_otros = saveDatosOtros();

                boolean datos_croquis = true;
                if (cbCasaReuniones.isChecked())
                    datos_croquis = saveCroquis();

                boolean datos_politicas = savePoliticas();
                boolean datos_documentos = saveDocumentos();
                if (datos_personales && datos_telefonicos && datos_domiclio && datos_negocio &&
                        datos_conyuge && datos_otros && datos_croquis && datos_politicas && datos_documentos){
                    Update("estatus_completado", TBL_INTEGRANTES_GPO_REN, "1", "id", id_integrante);

                    Update("comentario_rechazo", TBL_INTEGRANTES_GPO_REN, "","id", id_integrante);

                    finish();
                }
                else{
                    final AlertDialog solicitud;
                    solicitud = Popups.showDialogMessage(this, warning,
                            "Faltan por llenar campos de la solicitud", R.string.accept, new Popups.DialogMessage() {
                                @Override
                                public void OnClickListener(AlertDialog dialog) {
                                    dialog.dismiss();
                                }
                            });
                    solicitud.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                    solicitud.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                    solicitud.show();
                }*/

                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void Update(String key, String tabla, String value, String param, String id) {
        Log.e("update", key+": "+value);
        ContentValues cv = new ContentValues();
        cv.put(key, value);
        db.update(tabla, cv, param+" = ?", new String[]{String.valueOf(id)});

    }
}
