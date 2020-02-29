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
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MultiAutoCompleteTextView;
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
import com.google.android.gms.maps.model.MarkerOptions;
import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.database.DBhelper;
import com.sidert.sidertmovil.fragments.dialogs.dialog_cargo_integrante;
import com.sidert.sidertmovil.fragments.dialogs.dialog_date_picker;
import com.sidert.sidertmovil.fragments.dialogs.dialog_originacion_gpo;
import com.sidert.sidertmovil.fragments.dialogs.dialog_registro_integrante;
import com.sidert.sidertmovil.models.ModeloCatalogoGral;
import com.sidert.sidertmovil.utils.Constants;
import com.sidert.sidertmovil.utils.Miscellaneous;
import com.sidert.sidertmovil.utils.MyCurrentListener;
import com.sidert.sidertmovil.utils.NameFragments;
import com.sidert.sidertmovil.utils.NetworkStatus;
import com.sidert.sidertmovil.utils.Popups;
import com.sidert.sidertmovil.utils.Validator;
import com.sidert.sidertmovil.utils.ValidatorTextView;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import moe.feng.common.stepperview.VerticalStepperItemView;

import static com.sidert.sidertmovil.utils.Constants.CONYUGE_INTEGRANTE;
import static com.sidert.sidertmovil.utils.Constants.CONYUGE_INTEGRANTE_T;
import static com.sidert.sidertmovil.utils.Constants.DATOS_CLIENTE_IND;
import static com.sidert.sidertmovil.utils.Constants.DATOS_CLIENTE_IND_T;
import static com.sidert.sidertmovil.utils.Constants.DATOS_INTEGRANTES_GPO;
import static com.sidert.sidertmovil.utils.Constants.DATOS_INTEGRANTES_GPO_T;
import static com.sidert.sidertmovil.utils.Constants.DOMICILIO_INTEGRANTE;
import static com.sidert.sidertmovil.utils.Constants.DOMICILIO_INTEGRANTE_T;
import static com.sidert.sidertmovil.utils.Constants.ENVIROMENT;
import static com.sidert.sidertmovil.utils.Constants.NEGOCIO_INTEGRANTE;
import static com.sidert.sidertmovil.utils.Constants.NEGOCIO_INTEGRANTE_T;
import static com.sidert.sidertmovil.utils.Constants.OTROS_DATOS_INTEGRANTE;
import static com.sidert.sidertmovil.utils.Constants.OTROS_DATOS_INTEGRANTE_T;
import static com.sidert.sidertmovil.utils.Constants.TELEFONOS_INTEGRANTE;
import static com.sidert.sidertmovil.utils.Constants.TELEFONOS_INTEGRANTE_T;
import static com.sidert.sidertmovil.utils.Constants.camara;
import static com.sidert.sidertmovil.utils.Constants.cash;

public class AgregarIntegrante extends AppCompatActivity implements dialog_registro_integrante.OnCompleteListener {

    private Context ctx;
    private Toolbar TBmain;
    private DBhelper dBhelper;
    private SQLiteDatabase db;
    private Calendar myCalendar;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private Validator validator = new Validator();
    private ValidatorTextView validatorTV = new ValidatorTextView();

    private boolean is_edit = true;

    private String[] _estudios;
    private String[] _tipo_identificacion;
    private String[] _civil;
    private String[] _tipo_casa;
    private String[] _casa_familiar;
    private String[] _medio_contacto;
    private String[] _parentesco;

    //===================  DATOS PERSONALES  ==================================
    private TextView tvCargo;
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
    private TextView tvTipoIdentificacion;
    private EditText etNumIdentifCli;
    private TextView tvEstudiosCli;
    private TextView tvEstadoCivilCli;
    private LinearLayout llBienes;
    private TextView tvBienes;
    private RadioGroup rgBienes;
    //=========================================================================
    //===================  DATOS TELEFONICOS  =================================
    private EditText etTelCasaCli;
    private EditText etCelularCli;
    private EditText etTelMensCli;
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
    private TextView tvMunicipioCli;
    private TextView tvEstadoCli;
    private TextView tvTipoCasaCli;
    private LinearLayout llCasaFamiliar;
    private TextView tvCasaFamiliar;
    private LinearLayout llCasaOtroCli;
    private EditText etOTroTipoCli;
    private EditText etTiempoSitio;
    private TextView tvFachadaCli;
    private ImageButton ibFotoFachCli;
    private ImageView ivFotoFachCli;
    public byte[] byteFotoFachCli;
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
    private TextView tvMunicipioNeg;
    private TextView tvActEconomicaNeg;
    private EditText etAntiguedadNeg;
    private EditText etIngMenNeg;
    private EditText etOtrosIngNeg;
    private EditText etGastosSemNeg;
    private TextView tvCapacidadPagoNeg;
    private TextView tvFachadaNeg;
    private ImageButton ibFotoFachNeg;
    private ImageView ivFotoFachNeg;
    public byte[] byteFotoFachNeg;
    private MultiAutoCompleteTextView etReferenciNeg;
    //=========================================================================
    //===================  DATOS CONYUGE  =====================================
    private EditText etNombreCony;
    private EditText etApPaternoCony;
    private EditText etApMaternoCony;
    private TextView tvOcupacionCony;
    private EditText etCelularCony;
    private EditText etIngresosCony;
    //=========================================================================
    //===================  OTROS DATOS  =======================================
    private TextView tvMedioContacto;
    private EditText etEmail;
    private TextView tvEstatus;
    private RadioGroup rgEstatus;
    private EditText etCredSolicitado;
    private TextView tvCantidadLetra;
    private CheckBox cbCasaReuniones;
    private TextView tvFirmaCli;
    private ImageButton ibFirmaCli;
    private ImageView ivFirmaCli;
    public byte[] byteFirmaCli;
    //=========================================================================
    //===================  LINEAR LAYOUT  =====================================
    private LinearLayout llPersonales;
    private LinearLayout llTelefonicos;
    private LinearLayout llDomicilio;
    private LinearLayout llNegocio;
    private LinearLayout llConyuge;
    private LinearLayout llOtros;

    private LinearLayout llDatosPersonales;
    private LinearLayout llDatosTelefonicos;
    private LinearLayout llDatosDomicilio;
    private LinearLayout llDatosNegocio;
    private LinearLayout llDatosConyuge;
    private LinearLayout llOtrosDatos;
    //=========================================================================
    //================= Image View ERROR  =====================
    private ImageView ivError1;
    private ImageView ivError2;
    private ImageView ivError3;
    private ImageView ivError4;
    private ImageView ivError5;
    private ImageView ivError6;
    //===================================================
    //===================  IMAGE VIEW  ========================================
    private ImageView ivDown1;
    private ImageView ivDown2;
    private ImageView ivDown3;
    private ImageView ivDown4;
    private ImageView ivDown5;
    private ImageView ivDown6;

    private ImageView ivUp1;
    private ImageView ivUp2;
    private ImageView ivUp3;
    private ImageView ivUp4;
    private ImageView ivUp5;
    private ImageView ivUp6;
    //=========================================================================

    private FloatingActionButton btnContinuar0;
    private FloatingActionButton btnContinuar1;
    private FloatingActionButton btnContinuar2;
    private FloatingActionButton btnContinuar3;
    private FloatingActionButton btnContinuar4;
    private FloatingActionButton btnContinuar5;

    private FloatingActionButton btnRegresar1;
    private FloatingActionButton btnRegresar2;
    private FloatingActionButton btnRegresar3;
    private FloatingActionButton btnRegresar4;
    private FloatingActionButton btnRegresar5;

    private LocationManager locationManager;
    private MyCurrentListener locationListener;
    private GoogleMap mMapCli;
    private GoogleMap mMapNeg;

    private String id_credito = "";
    private String id_integrante = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_integrante);

        ctx = this;
        dBhelper = new DBhelper(ctx);
        db = dBhelper.getWritableDatabase();
        myCalendar = Calendar.getInstance();

        _estudios = getResources().getStringArray(R.array.nivel_estudio);
        _civil = getResources().getStringArray(R.array.estado_civil);
        _tipo_casa = getResources().getStringArray(R.array.tipo_casa_cli);
        _casa_familiar = getResources().getStringArray(R.array.casa_familiar);
        _medio_contacto = getResources().getStringArray(R.array.entero_nosotros);
        _parentesco = getResources().getStringArray(R.array.casa_familiar_aval);
        _tipo_identificacion = getResources().getStringArray(R.array.tipo_identificacion);

        TBmain = findViewById(R.id.TBmain);
        setSupportActionBar(TBmain);
        //=================================  DATOS PERSONALES  =====================================
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
        tvCurpCli           = findViewById(R.id.tvCurpCli);
        etCurpIdCli         = findViewById(R.id.etCurpIdCli);
        tvTipoIdentificacion = findViewById(R.id.tvTipoIdentificacion);
        etNumIdentifCli     = findViewById(R.id.etNumIdentifCli);
        tvEstudiosCli       = findViewById(R.id.tvEstudiosCli);
        tvEstadoCivilCli    = findViewById(R.id.tvEstadoCivilCli);
        llBienes            = findViewById(R.id.llBienes);
        tvBienes            = findViewById(R.id.tvBienes);
        rgBienes            = findViewById(R.id.rgBienes);
        //==========================================================================================
        //==================================  DATOS TELEFONICOS  ===================================
        etTelCasaCli        = findViewById(R.id.etTelCasaCli);
        etCelularCli        = findViewById(R.id.etCelularCli);
        etTelMensCli        = findViewById(R.id.etTelMensCli);
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
        tvMunicipioCli      = findViewById(R.id.tvMunicipioCli);
        tvEstadoCli         = findViewById(R.id.tvEstadoCli);
        tvTipoCasaCli       = findViewById(R.id.tvTipoCasaCli);
        llCasaFamiliar      = findViewById(R.id.llCasaFamiliar);
        tvCasaFamiliar      = findViewById(R.id.tvCasaFamiliar);
        llCasaOtroCli       = findViewById(R.id.llCasaOtro);
        etOTroTipoCli       = findViewById(R.id.etOtroTipoCli);
        etTiempoSitio       = findViewById(R.id.etTiempoSitio);
        tvFachadaCli        = findViewById(R.id.tvFachadaCli);
        ibFotoFachCli       = findViewById(R.id.ibFotoFachCli);
        ivFotoFachCli       = findViewById(R.id.ivFotoFachCli);
        etReferenciaCli     = findViewById(R.id.etReferenciaCli);
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
        tvCapacidadPagoNeg  = findViewById(R.id.tvCapacidadPagoNeg);
        tvFachadaNeg        = findViewById(R.id.tvFachadaNeg);
        ibFotoFachNeg       = findViewById(R.id.ibFotoFachNeg);
        ivFotoFachNeg       = findViewById(R.id.ivFotoFachNeg);
        etReferenciNeg      = findViewById(R.id.etReferenciaNeg);
        //==========================================================================================
        //===================================  DATOS CONYUGE  ======================================
        etNombreCony        = findViewById(R.id.etNombreCony);
        etApPaternoCony     = findViewById(R.id.etApPaternoCony);
        etApMaternoCony     = findViewById(R.id.etApMaternoCony);
        tvOcupacionCony     = findViewById(R.id.tvOcupacionCony);
        etCelularCony       = findViewById(R.id.etCelularCony);
        etIngresosCony      = findViewById(R.id.etIngresosCony);
        //==========================================================================================
        //===================================  DATOS OTROS  ========================================
        tvMedioContacto     = findViewById(R.id.tvMedioContacto);
        etEmail             = findViewById(R.id.etEmail);
        tvEstatus           = findViewById(R.id.tvEstatus);
        rgEstatus           = findViewById(R.id.rgEstatus);
        etCredSolicitado    = findViewById(R.id.etCredSolicitado);
        tvCantidadLetra     = findViewById(R.id.tvCantidadLetra);
        cbCasaReuniones     = findViewById(R.id.cbCasaReuniones);
        tvFirmaCli          = findViewById(R.id.tvFirmaCli);
        ibFirmaCli          = findViewById(R.id.ibFirmaCli);
        ivFirmaCli          = findViewById(R.id.ivFirmaCli);
        //==========================================================================================
        //============================= IMAGE VIEW ERROR  ==========================================
        ivError1 = findViewById(R.id.ivError1);
        ivError2 = findViewById(R.id.ivError2);
        ivError3 = findViewById(R.id.ivError3);
        ivError4 = findViewById(R.id.ivError4);
        ivError5 = findViewById(R.id.ivError5);
        ivError6 = findViewById(R.id.ivError6);
        //=========================================================
        //============================ IMAGE VIEW UP|DOWN  =========================================
        ivDown1 = findViewById(R.id.ivDown1);
        ivDown2 = findViewById(R.id.ivDown2);
        ivDown3 = findViewById(R.id.ivDown3);
        ivDown4 = findViewById(R.id.ivDown4);
        ivDown5 = findViewById(R.id.ivDown5);
        ivDown6 = findViewById(R.id.ivDown6);

        ivUp1 = findViewById(R.id.ivUp1);
        ivUp2 = findViewById(R.id.ivUp2);
        ivUp3 = findViewById(R.id.ivUp3);
        ivUp4 = findViewById(R.id.ivUp4);
        ivUp5 = findViewById(R.id.ivUp5);
        ivUp6 = findViewById(R.id.ivUp6);
        //=========================================================
        //================ LINEAR LAYOUT  =========================
        llDatosPersonales   = findViewById(R.id.llDatosPersonales);
        llDatosTelefonicos  = findViewById(R.id.llDatosTelefonicos);
        llDatosDomicilio    = findViewById(R.id.llDatosDomicilio);
        llDatosNegocio      = findViewById(R.id.llDatosNegocio);
        llDatosConyuge      = findViewById(R.id.llDatosConyuge);
        llOtrosDatos        = findViewById(R.id.llOtrosDatos);

        llPersonales    = findViewById(R.id.llPersonales);
        llTelefonicos   = findViewById(R.id.llTelefonicos);
        llDomicilio     = findViewById(R.id.llDomicilio);
        llNegocio       = findViewById(R.id.llNegocio);
        llConyuge       = findViewById(R.id.llConyuge);
        llOtros         = findViewById(R.id.llOtros);
        //=========================================================

        btnContinuar0 = findViewById(R.id.btnContinuar0);
        btnContinuar1 = findViewById(R.id.btnContinuar1);
        btnContinuar2 = findViewById(R.id.btnContinuar2);
        btnContinuar3 = findViewById(R.id.btnContinuar3);
        btnContinuar4 = findViewById(R.id.btnContinuar4);
        btnContinuar5 = findViewById(R.id.btnContinuar5);

        btnRegresar1 = findViewById(R.id.btnRegresar1);
        btnRegresar2 = findViewById(R.id.btnRegresar2);
        btnRegresar3 = findViewById(R.id.btnRegresar3);
        btnRegresar4 = findViewById(R.id.btnRegresar4);
        btnRegresar5 = findViewById(R.id.btnRegresar5);

        mapCli.onCreate(savedInstanceState);
        mapNeg.onCreate(savedInstanceState);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (getIntent().getBooleanExtra("is_new",true)) {
            id_credito = getIntent().getStringExtra("id_credito");
            openRegistroIntegrante(id_credito);
        }
        else{
            id_credito = getIntent().getStringExtra("id_credito");
            id_integrante = getIntent().getStringExtra("id_integrante");
            initComponents(getIntent().getStringExtra("id_credito"), getIntent().getStringExtra("id_integrante"));
        }

        //============================== LINEAR LAYOUT LISTENER  ==================================
        llPersonales.setOnClickListener(llPersonales_OnClick);
        llTelefonicos.setOnClickListener(llTelefonicos_OnClick);
        llDomicilio.setOnClickListener(llDomicilio_OnClick);
        llNegocio.setOnClickListener(llNegocio_OnClick);
        llConyuge.setOnClickListener(llConyuge_OnClick);
        llOtros.setOnClickListener(llOtros_OnClick);
        //===========================================================================
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
        tvFechaNacCli.setOnClickListener(tvFechaNacCli_OnClick);
        tvEstadoNacCli.setOnClickListener(tvEstadoNacCli_OnClick);
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
                    else {
                        tvRfcCli.setText(Miscellaneous.GenerarRFC(s.toString().substring(0,10), etNombreCli.getText().toString().trim(), etApPaternoCli.getText().toString().trim(), etApMaternoCli.getText().toString().trim()));
                        ContentValues cv = new ContentValues();
                        cv.put("rfc",tvRfcCli.getText().toString().trim().toUpperCase());
                        cv.put("curp",s.toString().trim().toUpperCase());
                        if (ENVIROMENT)
                            db.update(DATOS_INTEGRANTES_GPO, cv, "id = ?", new String[]{id_integrante});
                        else
                            db.update(DATOS_INTEGRANTES_GPO_T, cv, "id = ?", new String[]{id_integrante});
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
                                    db.update(DATOS_INTEGRANTES_GPO, cv, "id = ?", new String[]{id_integrante});
                                else
                                    db.update(DATOS_INTEGRANTES_GPO_T, cv, "id = ?", new String[]{id_integrante});
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
        tvTipoIdentificacion.setOnClickListener(tvTipoIdentificacion_OnClick);
        etNumIdentifCli.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    if (!etNumIdentifCli.getText().toString().trim().isEmpty()){
                        ContentValues cv = new ContentValues();
                        cv.put("no_identificacion", etNumIdentifCli.getText().toString().trim().toUpperCase());
                        if (ENVIROMENT)
                            db.update(DATOS_INTEGRANTES_GPO, cv, "id = ?", new String[]{id_integrante});
                        else
                            db.update(DATOS_INTEGRANTES_GPO_T, cv, "id = ?", new String[]{id_integrante});
                    }
                    else
                        etNumIdentifCli.setError("Este campo es requerido");
                }
            }
        });
        tvEstudiosCli.setOnClickListener(tvEstudiosCli_OnClick);
        tvEstadoCivilCli.setOnClickListener(tvEstadoCivilCli_OnClick);
        //===========================================================================
        //==============================  TELEFONICOS LISTENER =====================================
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
                            db.update(TELEFONOS_INTEGRANTE, cv, "id_integrante = ?", new String[]{id_integrante});
                        else
                            db.update(TELEFONOS_INTEGRANTE_T, cv, "id_integrante = ?", new String[]{id_integrante});
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
                            db.update(TELEFONOS_INTEGRANTE, cv, "id_integrante = ?", new String[]{id_integrante});
                        else
                            db.update(TELEFONOS_INTEGRANTE_T, cv, "id_integrante = ?", new String[]{id_integrante});
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
                        cv.put("tel_mensaje", etTelMensCli.getText().toString().trim());
                        if (ENVIROMENT)
                            db.update(TELEFONOS_INTEGRANTE, cv, "id_integrante = ?", new String[]{id_integrante});
                        else
                            db.update(TELEFONOS_INTEGRANTE_T, cv, "id_integrante = ?", new String[]{id_integrante});
                    }
                    else
                        etTelMensCli.setError(ctx.getResources().getString(R.string.mensaje_telefono));
                }
                else
                    etTelMensCli.setError(null);
            }
        });
        //===========================================================================
        //==============================  DOMICILIO LISTENER =======================================
        ibMapCli.setOnClickListener(ibMapCli_OnClick);
        etCalleCli.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    if (!validator.validate(etCalleCli, new String[]{validator.REQUIRED})){
                        ContentValues cv = new ContentValues();
                        cv.put("calle", etCalleCli.getText().toString().trim().toUpperCase());
                        if (ENVIROMENT)
                            db.update(DOMICILIO_INTEGRANTE, cv, "id_integrante = ?", new String[]{id_integrante});
                        else
                            db.update(DOMICILIO_INTEGRANTE_T, cv, "id_integrante = ?", new String[]{id_integrante});
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
                            db.update(DOMICILIO_INTEGRANTE, cv, "id_integrante = ?", new String[]{id_integrante});
                        else
                            db.update(DOMICILIO_INTEGRANTE_T, cv, "id_integrante = ?", new String[]{id_integrante});
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
                            db.update(DOMICILIO_INTEGRANTE, cv, "id_integrante = ?", new String[]{id_integrante});
                        else
                            db.update(DOMICILIO_INTEGRANTE_T, cv, "id_integrante = ?", new String[]{id_integrante});
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
                        db.update(DOMICILIO_INTEGRANTE, cv, "id_integrante = ?", new String[]{id_integrante});
                    else
                        db.update(DOMICILIO_INTEGRANTE_T, cv, "id_integrante = ?", new String[]{id_integrante});

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
                        db.update(DOMICILIO_INTEGRANTE, cv, "id_integrante = ?", new String[]{id_integrante});
                    else
                        db.update(DOMICILIO_INTEGRANTE_T, cv, "id_integrante = ?", new String[]{id_integrante});

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
                            db.update(DOMICILIO_INTEGRANTE, cv, "id_integrante = ?", new String[]{id_integrante});
                        else
                            db.update(DOMICILIO_INTEGRANTE_T, cv, "id_integrante = ?", new String[]{id_integrante});
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
        tvTipoCasaCli.setOnClickListener(tvTipoCasaCli_OnClick);
        tvCasaFamiliar.setOnClickListener(tvCasaFamiliar_OnClick);
        etTiempoSitio.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    if (!etTiempoSitio.getText().toString().trim().isEmpty() && Integer.parseInt(etTiempoSitio.getText().toString().trim()) > 0) {
                        ContentValues cv = new ContentValues();
                        cv.put("tiempo_vivir_sitio", etTiempoSitio.getText().toString().trim());
                        if (ENVIROMENT)
                            db.update(DOMICILIO_INTEGRANTE, cv, "id_integrante = ?", new String[]{id_integrante});
                        else
                            db.update(DOMICILIO_INTEGRANTE_T, cv, "id_integrante = ?", new String[]{id_integrante});
                    }
                    else{
                        etTiempoSitio.setError("No se permiten cantidades iguales a cero");
                    }
                }
            }
        });
        ibFotoFachCli.setOnClickListener(ibFotoFachCli_OnClick);
        etReferenciaCli.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    if (!etReferenciaCli.getText().toString().trim().isEmpty()){
                        ContentValues cv = new ContentValues();
                        cv.put("ref_domiciliaria", etReferenciaCli.getText().toString().trim().toUpperCase());
                        if (ENVIROMENT)
                            db.update(DOMICILIO_INTEGRANTE, cv, "id_integrante = ?", new String[]{id_integrante});
                        else
                            db.update(DOMICILIO_INTEGRANTE_T, cv, "id_integrante = ?", new String[]{id_integrante});
                    }
                    else
                        etReferenciaCli.setError("Este campo es requerido");
                }
            }
        });
        //===========================================================================
        //==================================  NEGOCIO LISTENER  ====================================
        etNombreNeg.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    if (!etNombreNeg.getText().toString().trim().isEmpty()){
                        ContentValues cv = new ContentValues();
                        cv.put("nombre", etNombreNeg.getText().toString().trim().toUpperCase());
                        if (ENVIROMENT)
                            db.update(NEGOCIO_INTEGRANTE, cv, "id_integrante = ?", new String[]{id_integrante});
                        else
                            db.update(NEGOCIO_INTEGRANTE_T, cv, "id_integrante = ?", new String[]{id_integrante});
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
                            db.update(NEGOCIO_INTEGRANTE, cv, "id_integrante = ?", new String[]{id_integrante});
                        else
                            db.update(NEGOCIO_INTEGRANTE_T, cv, "id_integrante = ?", new String[]{id_integrante});
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
                            db.update(NEGOCIO_INTEGRANTE, cv, "id_integrante = ?", new String[]{id_integrante});
                        else
                            db.update(NEGOCIO_INTEGRANTE_T, cv, "id_integrante = ?", new String[]{id_integrante});
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
                        if (ENVIROMENT)
                            db.update(NEGOCIO_INTEGRANTE, cv, "id_integrante = ?", new String[]{id_integrante});
                        else
                            db.update(NEGOCIO_INTEGRANTE_T, cv, "id_integrante = ?", new String[]{id_integrante});
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
                    if (ENVIROMENT)
                        db.update(NEGOCIO_INTEGRANTE, cv, "id_integrante = ?", new String[]{id_integrante});
                    else
                        db.update(NEGOCIO_INTEGRANTE_T, cv, "id_integrante = ?", new String[]{id_integrante});

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
                        db.update(NEGOCIO_INTEGRANTE, cv, "id_integrante = ?", new String[]{id_integrante});
                    else
                        db.update(NEGOCIO_INTEGRANTE_T, cv, "id_integrante = ?", new String[]{id_integrante});

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
                            db.update(NEGOCIO_INTEGRANTE, cv, "id_integrante = ?", new String[]{id_integrante});
                        else
                            db.update(NEGOCIO_INTEGRANTE_T, cv, "id_integrante = ?", new String[]{id_integrante});
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
        tvColoniaNeg.setOnClickListener(tvColoniaNeg_OnClick);
        tvActEconomicaNeg.setOnClickListener(tvActEconomicaNeg_OnClick);
        etAntiguedadNeg.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    if (!etAntiguedadNeg.getText().toString().trim().isEmpty()){
                        if (Integer.parseInt(etAntiguedadNeg.getText().toString().trim()) > 0){
                            ContentValues cv = new ContentValues();
                            cv.put("antiguedad", etAntiguedadNeg.getText().toString().trim());
                            if (ENVIROMENT)
                                db.update(NEGOCIO_INTEGRANTE, cv, "id_integrante = ?", new String[]{id_integrante});
                            else
                                db.update(NEGOCIO_INTEGRANTE_T, cv, "id_integrante = ?", new String[]{id_integrante});
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
                        db.update(NEGOCIO_INTEGRANTE, cv, "id_integrante = ?", new String[]{id_integrante});
                    else
                        db.update(NEGOCIO_INTEGRANTE_T, cv, "id_integrante = ?", new String[]{id_integrante});
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
                        db.update(NEGOCIO_INTEGRANTE, cv, "id_integrante = ?", new String[]{id_integrante});
                    else
                        db.update(NEGOCIO_INTEGRANTE_T, cv, "id_integrante = ?", new String[]{id_integrante});
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
                        db.update(NEGOCIO_INTEGRANTE, cv, "id_integrante = ?", new String[]{id_integrante});
                    else
                        db.update(NEGOCIO_INTEGRANTE_T, cv, "id_integrante = ?", new String[]{id_integrante});
                }
            }
        });
        ibFotoFachNeg.setOnClickListener(ibFotoFachNeg_OnClick);
        etReferenciNeg.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    if (!etReferenciNeg.getText().toString().trim().isEmpty()){
                        ContentValues cv = new ContentValues();
                        cv.put("ref_domiciliaria", etReferenciNeg.getText().toString().trim().toUpperCase());
                        if (ENVIROMENT)
                            db.update(NEGOCIO_INTEGRANTE, cv, "id_integrante = ?", new String[]{id_integrante});
                        else
                            db.update(NEGOCIO_INTEGRANTE_T, cv, "id_integrante = ?", new String[]{id_integrante});
                    }
                    else
                        etReferenciNeg.setError("Este campo es requerido");
                }
            }
        });
        //===========================================================================
        //==================================  CONYUGE LISTENER  ====================================
        etNombreCony.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    if (tvEstadoCivilCli.getText().toString().equals("CASADA(O)") ||
                    tvEstadoCivilCli.getText().toString().equals("UNION LIBRE")) {
                        if (!validator.validate(etNombreCony, new String[]{validator.REQUIRED, validator.ONLY_TEXT})){
                            ContentValues cv = new ContentValues();
                            cv.put("nombre", etNombreCony.getText().toString().trim().toUpperCase());
                            if (ENVIROMENT)
                                db.update(CONYUGE_INTEGRANTE, cv, "id_integrante = ?", new String[]{id_integrante});
                            else
                                db.update(CONYUGE_INTEGRANTE_T, cv, "id_integrante = ?", new String[]{id_integrante});
                        }
                    }
                }
            }
        });
        etApPaternoCony.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    if (tvEstadoCivilCli.getText().toString().equals("CASADA(O)") ||
                    tvEstadoCivilCli.getText().toString().equals("UNION LIBRE")) {
                        if (!validator.validate(etApPaternoCony, new String[]{validator.REQUIRED, validator.ONLY_TEXT})){
                            ContentValues cv = new ContentValues();
                            cv.put("paterno", etApPaternoCony.getText().toString().trim().toUpperCase());
                            if (ENVIROMENT)
                                db.update(CONYUGE_INTEGRANTE, cv, "id_integrante = ?", new String[]{id_integrante});
                            else
                                db.update(CONYUGE_INTEGRANTE_T, cv, "id_integrante = ?", new String[]{id_integrante});
                        }
                    }
                }
            }
        });
        etApMaternoCony.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    if (tvEstadoCivilCli.getText().toString().equals("CASADA(O)") ||
                    tvEstadoCivilCli.getText().toString().equals("UNION LIBRE")) {
                        if (!validator.validate(etApMaternoCony, new String[]{validator.REQUIRED, validator.ONLY_TEXT})){
                            ContentValues cv = new ContentValues();
                            cv.put("materno", etApMaternoCony.getText().toString().trim().toUpperCase());
                            if (ENVIROMENT)
                                db.update(CONYUGE_INTEGRANTE, cv, "id_integrante = ?", new String[]{id_integrante});
                            else
                                db.update(CONYUGE_INTEGRANTE_T, cv, "id_integrante = ?", new String[]{id_integrante});
                        }
                    }
                }
            }
        });
        tvOcupacionCony.setOnClickListener(tvOcupacionCony_OnClick);
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
                            db.update(CONYUGE_INTEGRANTE, cv, "id_integrante = ?", new String[]{id_integrante});
                        else
                            db.update(CONYUGE_INTEGRANTE_T, cv, "id_integrante = ?", new String[]{id_integrante});
                    }
                    else
                        etCelularCony.setError(ctx.getResources().getString(R.string.mensaje_telefono));
                }
                else
                    etCelularCony.setError(null);
            }
        });
        etIngresosCony.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    if (tvEstadoCivilCli.getText().toString().equals("CASADA(O)") ||
                    tvEstadoCivilCli.getText().toString().equals("UNION LIBRE")) {
                        if (!validator.validate(etIngresosCony, new String[]{validator.REQUIRED})){
                            ContentValues cv = new ContentValues();
                            cv.put("ingresos", etIngresosCony.getText().toString().trim().toUpperCase());
                            if (ENVIROMENT)
                                db.update(CONYUGE_INTEGRANTE, cv, "id_integrante = ?", new String[]{id_integrante});
                            else
                                db.update(CONYUGE_INTEGRANTE_T, cv, "id_integrante = ?", new String[]{id_integrante});
                        }
                    }
                }
            }
        });
        //===========================================================================
        //==================================  OTROS LISTENER  ======================================
        tvMedioContacto.setOnClickListener(tvMedioContacto_OnClick);
        etEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    if (!validator.validate(etEmail, new String[]{validator.EMAIL})){
                        ContentValues cv = new ContentValues();
                        cv.put("email", etEmail.getText().toString().trim().toUpperCase());
                        if (ENVIROMENT)
                            db.update(OTROS_DATOS_INTEGRANTE, cv, "id_integrante = ?", new String[]{id_integrante});
                        else
                            db.update(OTROS_DATOS_INTEGRANTE_T, cv, "id_integrante = ?", new String[]{id_integrante});
                    }
                }
            }
        });
        etCredSolicitado.addTextChangedListener(new TextWatcher() {
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
                        etCredSolicitado.setError("La cantidad no corresponde a un monto de crédito válido");
                    }else{
                        ContentValues cv = new ContentValues();
                        cv.put("monto_solicitado",s.toString().trim());
                        if (ENVIROMENT)
                            db.update(OTROS_DATOS_INTEGRANTE, cv, "id_integrante = ?", new String[]{id_integrante});
                        else
                            db.update(OTROS_DATOS_INTEGRANTE_T, cv, "id_integrante = ?", new String[]{id_integrante});
                        tvCantidadLetra.setText((Miscellaneous.cantidadLetra(s.toString()).toUpperCase() + " PESOS MEXICANOS ").replace("  ", " "));
                    }
                }
                else{
                    tvCantidadLetra.setText("");
                }
            }
        });
        etCredSolicitado.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (!etCredSolicitado.getText().toString().trim().isEmpty() && !tvCantidadLetra.getText().toString().isEmpty()){
                        ContentValues cv = new ContentValues();
                        cv.put("monto_solicitado",etCredSolicitado.getText().toString().trim());
                        if (ENVIROMENT)
                            db.update(OTROS_DATOS_INTEGRANTE, cv, "id_integrante = ?", new String[]{id_integrante});
                        else
                            db.update(OTROS_DATOS_INTEGRANTE_T, cv, "id_integrante = ?", new String[]{id_integrante});
                    }
                }
            }
        });
        ibFirmaCli.setOnClickListener(ibFirmaCli_OnClick);
        //==========================================================================

        btnContinuar0.setOnClickListener(btnContinuar0_OnClick);
        btnContinuar1.setOnClickListener(btnContinuar1_OnClick);
        btnContinuar2.setOnClickListener(btnContinuar2_OnClick);
        btnContinuar3.setOnClickListener(btnContinuar3_OnClick);
        btnContinuar4.setOnClickListener(btnContinuar4_OnClick);
        btnContinuar5.setOnClickListener(btnContinuar5_OnClick);

        btnRegresar1.setOnClickListener(btnRegresar1_OnClick);
        btnRegresar2.setOnClickListener(btnRegresar2_OnClick);
        btnRegresar3.setOnClickListener(btnRegresar3_OnClick);
        btnRegresar4.setOnClickListener(btnRegresar4_OnClick);
        btnRegresar5.setOnClickListener(btnRegresar5_OnClick);

        //================================  CLIENTE GENERO LISTENER  ===============================
        rgGeneroCli.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                tvGeneroCli.setError(null);
                HashMap<Integer, String> params = new HashMap<>();
                ContentValues cv;
                if (checkedId == R.id.rbHombre){

                    params.put(0, etNombreCli.getText().toString().trim().toUpperCase());
                    params.put(1, etApPaternoCli.getText().toString());
                    params.put(2, etApMaternoCli.getText().toString());
                    params.put(3, tvFechaNacCli.getText().toString());
                    params.put(4, "Hombre");

                    if (!tvEstadoNacCli.getText().toString().trim().isEmpty())
                        params.put(5, tvEstadoNacCli.getText().toString().trim());
                    else
                        params.put(5,"");

                    cv = new ContentValues();
                    cv.put("genero","0");
                    tvCurpCli.setText(Miscellaneous.GenerarCurp(params));
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

                    cv = new ContentValues();
                    cv.put("genero","1");
                    tvCurpCli.setText(Miscellaneous.GenerarCurp(params));
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

                    cv = new ContentValues();
                    cv.put("genero","2");
                    tvCurpCli.setText(Miscellaneous.GenerarCurp(params));
                }

                if (ENVIROMENT)
                    db.update(DATOS_INTEGRANTES_GPO, cv, "id = ?", new String[]{id_integrante});
                else
                    db.update(DATOS_INTEGRANTES_GPO_T, cv, "id = ?", new String[]{id_integrante});
            }
        });

        rgBienes.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                tvBienes.setError(null);
                ContentValues cv = new ContentValues();
                switch (checkedId){
                    case R.id.rbMancomunados:
                        cv.put("bienes","1");
                        break;
                    case R.id.rbSeparados:
                        cv.put("bienes","2");
                        break;
                }
                if (ENVIROMENT)
                    db.update(DATOS_INTEGRANTES_GPO, cv, "id = ?", new String[]{id_integrante});
                else
                    db.update(DATOS_INTEGRANTES_GPO_T, cv, "id = ?", new String[]{id_integrante});
            }
        });

        rgEstatus.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                tvEstatus.setError(null);
                ContentValues cv = new ContentValues();
                switch (checkedId){
                    case R.id.rbNuevo:
                        cv.put("estatus_integrante","1");
                        break;
                    case R.id.rbRenovado:
                        cv.put("estatus_integrante","2");
                        break;
                    case R.id.rbCambio:
                        cv.put("estatus_integrante","3");
                        break;
                }

                if (ENVIROMENT)
                    db.update(OTROS_DATOS_INTEGRANTE, cv, "id_integrante = ?", new String[]{id_integrante});
                else
                    db.update(OTROS_DATOS_INTEGRANTE_T, cv, "id_integrante = ?", new String[]{id_integrante});
            }
        });

        cbCasaReuniones.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                cbCasaReuniones.setError(null);
                ContentValues cv = new ContentValues();
                if (isChecked){
                    cv.put("casa_reunion", "1");
                }
                else{
                    cv.put("casa_reunion","0");
                }

                if (ENVIROMENT)
                    db.update(OTROS_DATOS_INTEGRANTE, cv, "id_integrante = ?", new String[]{id_integrante});
                else
                    db.update(OTROS_DATOS_INTEGRANTE_T, cv, "id_integrante = ?", new String[]{id_integrante});
            }
        });
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
    //==============================================================================================
    //============================ ACTION PERSONALES  ==============================================
    private View.OnClickListener tvFechaNacCli_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (is_edit) {
                dialog_date_picker dialogDatePicker = new dialog_date_picker();
                Bundle b = new Bundle();

                b.putInt(Constants.YEAR_CURRENT, myCalendar.get(Calendar.YEAR));
                b.putInt(Constants.MONTH_CURRENT, myCalendar.get(Calendar.MONTH));
                b.putInt(Constants.DAY_CURRENT, myCalendar.get(Calendar.DAY_OF_MONTH));
                b.putString(Constants.DATE_CURRENT, sdf.format(myCalendar.getTime()));
                b.putInt(Constants.IDENTIFIER, 4);
                b.putBoolean(Constants.FECHAS_POST, false);
                dialogDatePicker.setArguments(b);
                dialogDatePicker.show(getSupportFragmentManager(), NameFragments.DIALOGDATEPICKER);
            }
        }
    };
    private View.OnClickListener tvEstadoNacCli_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (is_edit) {
                Intent i_estados = new Intent(ctx, Catalogos.class);
                i_estados.putExtra(Constants.TITULO, Miscellaneous.ucFirst(Constants.ESTADOS));
                i_estados.putExtra(Constants.CATALOGO, Constants.ESTADOS);
                i_estados.putExtra(Constants.REQUEST_CODE, Constants.REQUEST_CODE_ESTADO_NAC);
                startActivityForResult(i_estados, Constants.REQUEST_CODE_ESTADO_NAC);
            }
        }
    };
    private View.OnClickListener tvTipoIdentificacion_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (is_edit) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
                builder.setTitle(R.string.selected_option)
                        .setItems(R.array.tipo_identificacion, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int position) {
                                tvTipoIdentificacion.setError(null);
                                tvTipoIdentificacion.setText(_tipo_identificacion[position]);
                                ContentValues cv = new ContentValues();
                                cv.put("tipo_identificacion", tvTipoIdentificacion.getText().toString().trim().toUpperCase());
                                if (ENVIROMENT)
                                    db.update(DATOS_INTEGRANTES_GPO, cv, "id = ?", new String[]{id_integrante});
                                else
                                    db.update(DATOS_INTEGRANTES_GPO_T, cv, "id = ?", new String[]{id_integrante});
                            }
                        });
                builder.create();
                builder.show();
            }
        }
    };
    private View.OnClickListener tvEstudiosCli_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (is_edit) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
                builder.setTitle(R.string.selected_option)
                        .setItems(R.array.nivel_estudio, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int position) {
                                tvEstudiosCli.setError(null);
                                tvEstudiosCli.setText(_estudios[position]);
                                tvEstudiosCli.requestFocus();
                                ContentValues cv = new ContentValues();
                                cv.put("nivel_estudio", tvEstudiosCli.getText().toString().trim().toUpperCase());
                                if (ENVIROMENT)
                                    db.update(DATOS_INTEGRANTES_GPO, cv, "id = ?", new String[]{id_integrante});
                                else
                                    db.update(DATOS_INTEGRANTES_GPO_T, cv, "id = ?", new String[]{id_integrante});
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
                AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
                builder.setTitle(R.string.selected_option)
                        .setItems(R.array.estado_civil, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int position) {
                                tvEstadoCivilCli.setError(null);
                                tvEstadoCivilCli.setText(_civil[position]);
                                if (position == 1) {
                                    llBienes.setVisibility(View.VISIBLE);
                                    llConyuge.setVisibility(View.VISIBLE);
                                } else if (position == 4)
                                    llConyuge.setVisibility(View.VISIBLE);
                                else {
                                    llConyuge.setVisibility(View.GONE);
                                    llBienes.setVisibility(View.GONE);
                                }
                                ContentValues cv = new ContentValues();
                                cv.put("estado_civil", tvEstadoCivilCli.getText().toString().trim().toUpperCase());
                                if (ENVIROMENT)
                                    db.update(DATOS_INTEGRANTES_GPO, cv, "id = ?", new String[]{id_integrante});
                                else
                                    db.update(DATOS_INTEGRANTES_GPO_T, cv, "id = ?", new String[]{id_integrante});
                            }
                        });
                builder.create();
                builder.show();
            }
        }
    };
    //==============================================================================================
    //============================ ACTION DOMICILIO  ===============================================
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

    private View.OnClickListener etColonia_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (is_edit) {
                Intent i_colonia = new Intent(ctx, Catalogos.class);
                i_colonia.putExtra(Constants.TITULO, Miscellaneous.ucFirst(Constants.ESTADOS));
                i_colonia.putExtra(Constants.CATALOGO, Constants.COLONIAS);
                i_colonia.putExtra(Constants.EXTRA, etCpCli.getText().toString().trim());
                i_colonia.putExtra(Constants.REQUEST_CODE, Constants.REQUEST_CODE_COLONIA_CLIE);
                startActivityForResult(i_colonia, Constants.REQUEST_CODE_COLONIA_CLIE);
            }
        }
    };

    private View.OnClickListener tvTipoCasaCli_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (is_edit) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
                builder.setTitle(R.string.selected_option)
                        .setItems(R.array.tipo_casa_cli, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int position) {
                                tvTipoCasaCli.setError(null);
                                tvTipoCasaCli.setText(_tipo_casa[position]);
                                ContentValues cv = new ContentValues();
                                cv.put("tipo_vivienda", tvTipoCasaCli.getText().toString().trim().toUpperCase());
                                if (ENVIROMENT)
                                    db.update(DOMICILIO_INTEGRANTE, cv, "id_integrante = ?", new String[]{id_integrante});
                                else
                                    db.update(DOMICILIO_INTEGRANTE_T, cv, "id_integrante = ?", new String[]{id_integrante});

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
                AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
                builder.setTitle(R.string.selected_option)
                        .setItems(R.array.casa_familiar, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int position) {
                                tvCasaFamiliar.setError(null);
                                tvCasaFamiliar.setText(_parentesco[position]);
                                ContentValues cv = new ContentValues();
                                cv.put("parentesco", tvCasaFamiliar.getText().toString().trim().toUpperCase());
                                if (ENVIROMENT)
                                    db.update(DOMICILIO_INTEGRANTE, cv, "id_integrante = ?", new String[]{id_integrante});
                                else
                                    db.update(DOMICILIO_INTEGRANTE_T, cv, "id_integrante = ?", new String[]{id_integrante});

                            }
                        });
                builder.create();
                builder.show();
            }
        }
    };

    private View.OnClickListener ibFotoFachCli_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i = new Intent(AgregarIntegrante.this, CameraVertical.class);
            i.putExtra(Constants.ORDER_ID, "SC_cliente");
            startActivityForResult(i, Constants.REQUEST_CODE_CAMARA_FACHADA_CLI);
        }
    };
    //==============================================================================================
    //============================ ACTION NEGOCIO  =================================================
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

    private View.OnClickListener tvColoniaNeg_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (is_edit) {
                Intent i_colonia = new Intent(ctx, Catalogos.class);
                i_colonia.putExtra(Constants.TITULO, Miscellaneous.ucFirst(Constants.COLONIAS));
                i_colonia.putExtra(Constants.CATALOGO, Constants.COLONIAS);
                i_colonia.putExtra(Constants.EXTRA, etCpNeg.getText().toString().trim());
                i_colonia.putExtra(Constants.REQUEST_CODE, Constants.REQUEST_CODE_COLONIA_NEG);
                startActivityForResult(i_colonia, Constants.REQUEST_CODE_COLONIA_NEG);
            }
        }
    };

    private View.OnClickListener tvActEconomicaNeg_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (is_edit) {
                Intent i_ocupaciones = new Intent(ctx, Catalogos.class);
                i_ocupaciones.putExtra(Constants.TITULO, Miscellaneous.ucFirst(Constants.SECTORES));
                i_ocupaciones.putExtra(Constants.CATALOGO, Constants.SECTORES);
                i_ocupaciones.putExtra(Constants.REQUEST_CODE, Constants.REQUEST_CODE_ACTIVIDAD_NEG);
                startActivityForResult(i_ocupaciones, Constants.REQUEST_CODE_ACTIVIDAD_NEG);
            }
        }
    };

    private View.OnClickListener ibFotoFachNeg_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i = new Intent(AgregarIntegrante.this, CameraVertical.class);
            i.putExtra(Constants.ORDER_ID, "SC_negocio");
            startActivityForResult(i, Constants.REQUEST_CODE_CAMARA_FACHADA_NEG);
        }
    };
    //==============================================================================================
    //============================ ACTION CONYUGE  =================================================
    private View.OnClickListener tvOcupacionCony_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (is_edit) {
                Intent i_ocupaciones = new Intent(ctx, Catalogos.class);
                i_ocupaciones.putExtra(Constants.TITULO, Miscellaneous.ucFirst(Constants.OCUPACIONES));
                i_ocupaciones.putExtra(Constants.CATALOGO, Constants.OCUPACIONES);
                i_ocupaciones.putExtra(Constants.REQUEST_CODE, Constants.REQUEST_CODE_OCUPACION_CONY);
                startActivityForResult(i_ocupaciones, Constants.REQUEST_CODE_OCUPACION_CONY);
            }
        }
    };
    //==============================================================================================
    //============================ ACTION OTROS  ===================================================
    private View.OnClickListener tvMedioContacto_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (is_edit) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
                builder.setTitle(R.string.selected_option)
                        .setItems(R.array.entero_nosotros, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int position) {
                                tvMedioContacto.setError(null);
                                tvMedioContacto.setText(_medio_contacto[position]);
                                ContentValues cv = new ContentValues();
                                cv.put("medio_contacto", tvMedioContacto.getText().toString().trim().toUpperCase());
                                if (ENVIROMENT)
                                    db.update(OTROS_DATOS_INTEGRANTE, cv, "id_integrante = ?", new String[]{id_integrante});
                                else
                                    db.update(OTROS_DATOS_INTEGRANTE_T, cv, "id_integrante = ?", new String[]{id_integrante});

                            }
                        });
                builder.create();
                builder.show();
            }
        }
    };
    private View.OnClickListener ibFirmaCli_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i_firma_cli = new Intent(ctx, CapturarFirma.class);
            i_firma_cli.putExtra(Constants.TIPO,"CLIENTE");
            startActivityForResult(i_firma_cli,Constants.REQUEST_CODE_FIRMA_CLI);
        }
    };
    //==============================================================================================

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

            if (tvEstadoCivilCli.getText().toString().equals("CASADA(O)") ||
            tvEstadoCivilCli.getText().toString().equals("UNION LIBRE")) {
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
            Toast.makeText(ctx, "Terminar proceso", Toast.LENGTH_SHORT).show();
        }
    };

    //Regresar
    private View.   OnClickListener btnRegresar1_OnClick = new View.OnClickListener() {
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

            if (tvEstadoCivilCli.getText().toString().equals("CASADA(O)") ||
            tvEstadoCivilCli.getText().toString().equals("UNION LIBRE")) {
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

    public void setDate (String date, String campo){
        try {
            Date strDate = sdf.parse(date);
            Calendar cal = Calendar.getInstance();
            cal.setTime(strDate);
            ContentValues cv;
            switch (campo){
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
                        db.update(DATOS_INTEGRANTES_GPO, cv, "id = ?", new String[]{String.valueOf(id_integrante)});
                    else
                        db.update(DATOS_INTEGRANTES_GPO_T, cv, "id = ?", new String[]{String.valueOf(id_integrante)});
                    tvCurpCli.setText(Miscellaneous.GenerarCurp(params));
                    break;
            }


        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    private void CapacidadPagoNeg (){
        double ing_mensual = (etIngMenNeg.getText().toString().trim().isEmpty())?0:Integer.parseInt(etIngMenNeg.getText().toString().trim());
        double ing_otros = (etOtrosIngNeg.getText().toString().trim().isEmpty())?0:Integer.parseInt(etOtrosIngNeg.getText().toString().trim());

        double gas_semanal = (etGastosSemNeg.getText().toString().trim().isEmpty())?0:Integer.parseInt(etGastosSemNeg.getText().toString().trim());

        double ingreso = ing_mensual + ing_otros;
        double gastos = (gas_semanal * 4);

        tvCapacidadPagoNeg.setText(String.valueOf(ingreso - gastos));
        ContentValues cv = new ContentValues();
        cv.put("capacidad_pago", tvCapacidadPagoNeg.getText().toString().trim());
        if (ENVIROMENT)
            db.update(NEGOCIO_INTEGRANTE, cv, "id_integrante = ?", new String[]{id_integrante});
        else
            db.update(NEGOCIO_INTEGRANTE_T, cv, "id_integrante = ?", new String[]{id_integrante});
    }

    private void openRegistroIntegrante(String id_credito) {
        dialog_registro_integrante registro_inte = new dialog_registro_integrante();
        Bundle b = new Bundle();
        b.putString("id_credito", String.valueOf(id_credito));
        registro_inte.setArguments(b);
        registro_inte.setCancelable(false);
        registro_inte.show(getSupportFragmentManager(), NameFragments.DIALOGREGISTROINTEGRANTE);
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
                tvMapaCli.setError(null);
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
                    db.update(DOMICILIO_INTEGRANTE, cv, "id_integrante = ?", new String[]{id_integrante});
                else
                    db.update(DOMICILIO_INTEGRANTE_T, cv, "id_integrante = ?", new String[]{id_integrante});
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

                tvMapaNeg.setError(null);
                ibMapNeg.setEnabled(true);
                ContentValues cv = new ContentValues();
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
                    db.update(NEGOCIO_INTEGRANTE, cv, "id_integrante = ?", new String[]{id_integrante});
                else
                    db.update(NEGOCIO_INTEGRANTE_T, cv, "id_integrante = ?", new String[]{id_integrante});
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

    private void CancelUbicacion (){
        locationManager.removeUpdates(locationListener);
    }

    private void initComponents (String id_credito, String id_integrante){
        Cursor row = dBhelper.getIntegranteOri(id_credito, id_integrante);
        row.moveToFirst();

        Log.e("personales", row.getString(20));
        Log.e("telefonos", row.getString(26));
        Log.e("domicilio", row.getString(44));
        Log.e("negocio", row.getString(67));
        Log.e("conyuge", row.getString(76));
        Log.e("otros", row.getString(85));



        if (row.getInt(20) == 1 &&
        row.getInt(26) == 1 &&
        row.getInt(44) == 1 &&
        row.getInt(67) == 1 &&
        ((row.getString(16).equals("CASADA(O)") ||
        row.getString(16).equals("UNION LIBRE") &&
        row.getInt(76) == 1) ||
        (row.getString(16).equals("SOLTERA(O)") ||
        row.getString(16).equals("VIUDA(O)") ||
        row.getString(16).equals("DIVORCIADA(O)"))) &&
        row.getInt(85) == 1){
            is_edit = false;
        }

        switch (row.getInt(2)){
            case 1:
                tvCargo.setText("PRESIDENTE");
                break;
            case 2:
                tvCargo.setText("TESORERO");
                break;
            case 3:
                tvCargo.setText("SECRETARIO");
                break;
            case 4:
                tvCargo.setText("INTEGRANTE");
                break;
        }

        etNombreCli.setText(row.getString(3)); etNombreCli.setEnabled(false);
        etApPaternoCli.setText(row.getString(4)); etApPaternoCli.setEnabled(false);
        etApMaternoCli.setText(row.getString(5)); etApMaternoCli.setEnabled(false);
        tvFechaNacCli.setText(row.getString(6));
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
        tvCurpCli.setText(row.getString(11));
        etCurpIdCli.setText(row.getString(12)); etCurpIdCli.setEnabled(is_edit);
        tvTipoIdentificacion.setText(row.getString(13));
        etNumIdentifCli.setText(row.getString(14)); etNumIdentifCli.setEnabled(is_edit);
        tvEstudiosCli.setText(row.getString(15));
        tvEstadoCivilCli.setText(row.getString(16));
        switch (row.getString(16)){
            case "CASADA(O)":
                llConyuge.setVisibility(View.VISIBLE);
                llBienes.setVisibility(View.VISIBLE);
                switch (row.getInt(17)){
                    case 1:
                        rgBienes.check(R.id.rbMancomunados);
                        break;
                    case 2:
                        rgBienes.check(R.id.rbSeparados);
                        break;
                }
                break;
            case "UNION LIBRE":
                llConyuge.setVisibility(View.VISIBLE);
                break;
        }

        //Datos telefonicos
        etTelCasaCli.setText(row.getString(23)); etTelCasaCli.setEnabled(is_edit);
        etCelularCli.setText(row.getString(24)); etCelularCli.setEnabled(is_edit);
        etTelMensCli.setText(row.getString(25)); etTelMensCli.setEnabled(is_edit);

        //Datos domicilio
        if (!row.getString(29).isEmpty() && !row.getString(30).isEmpty()){
            mapCli.setVisibility(View.VISIBLE);
            Ubicacion(row.getDouble(29), row.getDouble(30));
        }
        etCalleCli.setText(row.getString(31)); etCalleCli.setEnabled(is_edit);
        etNoExtCli.setText(row.getString(32)); etNoExtCli.setEnabled(is_edit);
        etNoIntCli.setText(row.getString(33)); etNoIntCli.setEnabled(is_edit);
        etManzanaCli.setText(row.getString(34)); etManzanaCli.setEnabled(is_edit);
        etLoteCli.setText(row.getString(35)); etLoteCli.setEnabled(is_edit);
        etCpCli.setText(row.getString(36)); etCpCli.setEnabled(is_edit);
        tvColoniaCli.setText(row.getString(37));
        if (!row.getString(36).trim().isEmpty()) {
            Cursor rowColonia = dBhelper.getDireccionByCP(row.getString(36));
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
        tvTipoCasaCli.setText(row.getString(38));
        switch (row.getString(38)){
            case "CASA FAMILIAR":
                llCasaFamiliar.setVisibility(View.VISIBLE);
                tvCasaFamiliar.setText(row.getString(39));
                break;
            case "OTRO":
                llCasaOtroCli.setVisibility(View.VISIBLE);
                etOTroTipoCli.setText(row.getString(40)); etOTroTipoCli.setEnabled(is_edit);
                break;
        }
        etTiempoSitio.setText(row.getString(41)); etTiempoSitio.setEnabled(is_edit);
        if (!row.getString(42).isEmpty()){
            File fachadaFile = new File(Constants.ROOT_PATH + "Fachada/"+row.getString(42));
            Uri uriFachada = Uri.fromFile(fachadaFile);
            byteFotoFachCli = Miscellaneous.getBytesUri(ctx, uriFachada,0);
            Glide.with(ctx).load(uriFachada).into(ivFotoFachCli);
            ibFotoFachCli.setVisibility(View.GONE);
            ivFotoFachCli.setVisibility(View.VISIBLE);
        }
        etReferenciaCli.setText(row.getString(43)); etReferenciaCli.setEnabled(is_edit);

        //Datos Negocio
        etNombreNeg.setText(row.getString(47)); etNombreNeg.setEnabled(is_edit);
        if (!row.getString(48).isEmpty() && !row.getString(49).isEmpty()){
            mapNeg.setVisibility(View.VISIBLE);
            UbicacionNeg(row.getDouble(48), row.getDouble(49));
        }
        etCalleNeg.setText(row.getString(50)); etCalleNeg.setEnabled(is_edit);
        etNoExtNeg.setText(row.getString(51)); etNoExtNeg.setEnabled(is_edit);
        etNoIntNeg.setText(row.getString(52)); etNoIntNeg.setEnabled(is_edit);
        etManzanaNeg.setText(row.getString(53)); etManzanaNeg.setEnabled(is_edit);
        etLoteNeg.setText(row.getString(54)); etLoteNeg.setEnabled(is_edit);
        etCpNeg.setText(row.getString(55)); etCpNeg.setEnabled(is_edit);
        tvColoniaNeg.setText(row.getString(56));
        if (!row.getString(55).trim().isEmpty()) {
            Cursor rowColonia = dBhelper.getDireccionByCP(row.getString(55));
            if (rowColonia.getCount() > 0) {
                rowColonia.moveToFirst();
                tvMunicipioNeg.setText(rowColonia.getString(4));

            } else {
                tvColoniaNeg.setText("No se encontró información");
                tvMunicipioNeg.setText("No se encontró información");
            }
            rowColonia.close();
        }
        tvActEconomicaNeg.setText(row.getString(57));
        etAntiguedadNeg.setText(row.getString(58)); etAntiguedadNeg.setEnabled(is_edit);
        etIngMenNeg.setText(row.getString(59)); etIngMenNeg.setEnabled(is_edit);
        etOtrosIngNeg.setText(row.getString(60)); etOtrosIngNeg.setEnabled(is_edit);
        etGastosSemNeg.setText(row.getString(61)); etGastosSemNeg.setEnabled(is_edit);
        tvCapacidadPagoNeg.setText(row.getString(62));
        if (!row.getString(63).isEmpty()){
            File fachadaFile = new File(Constants.ROOT_PATH + "Fachada/"+row.getString(63));
            Uri uriFachada = Uri.fromFile(fachadaFile);
            byteFotoFachNeg = Miscellaneous.getBytesUri(ctx, uriFachada,0);
            Glide.with(ctx).load(uriFachada).into(ivFotoFachNeg);
            ibFotoFachNeg.setVisibility(View.GONE);
            ivFotoFachNeg.setVisibility(View.VISIBLE);
        }
        etReferenciNeg.setText(row.getString(64)); etReferenciNeg.setEnabled(is_edit);

        //Datos Conyuge
        etNombreCony.setText(row.getString(70)); etNombreCony.setEnabled(is_edit);
        etApPaternoCony.setText(row.getString(71)); etApPaternoCony.setEnabled(is_edit);
        etApMaternoCony.setText(row.getString(72)); etApMaternoCony.setEnabled(is_edit);
        tvOcupacionCony.setText(row.getString(73));
        etCelularCony.setText(row.getString(74)); etCelularCony.setEnabled(is_edit);
        etIngresosCony.setText(row.getString(75)); etIngresosCony.setEnabled(is_edit);

        //Datos Otros
        tvMedioContacto.setText(row.getString(79));
        etEmail.setText(row.getString(80)); etEmail.setEnabled(is_edit);
        switch (row.getInt(81)){
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
        etCredSolicitado.setText(row.getString(82)); etCredSolicitado.setEnabled(is_edit);
        if (!row.getString(82).trim().isEmpty())
            tvCantidadLetra.setText((Miscellaneous.cantidadLetra(row.getString(82)).toUpperCase() + " PESOS MEXICANOS ").replace("  ", " "));

        if (row.getInt(83) == 1)
            cbCasaReuniones.setChecked(true);
        Cursor row_casa = dBhelper.customSelect(Constants.DATOS_INTEGRANTES_GPO_T + " AS i INNER JOIN " + Constants.OTROS_DATOS_INTEGRANTE_T + " AS od ON od.id_integrante = i.id", "i.id", " WHERE i.id_credito = " + id_credito + " AND od.casa_reunion = 1", "", null);
        row_casa.moveToFirst();
        if (row_casa.getCount() > 0 && row_casa.getInt(0) != Integer.parseInt(id_integrante)){
            cbCasaReuniones.setEnabled(false);
        }

        if (!row.getString(84).isEmpty()){
            File firmaFile = new File(Constants.ROOT_PATH + "Firma/"+row.getString(84));
            Uri uriFirma = Uri.fromFile(firmaFile);
            byteFirmaCli = Miscellaneous.getBytesUri(ctx, uriFirma,0);
            Glide.with(ctx).load(uriFirma).into(ivFirmaCli);
            ibFirmaCli.setVisibility(View.GONE);
            ivFirmaCli.setVisibility(View.VISIBLE);
        }

        if (!is_edit){
            invalidateOptionsMenu();

            tvFechaNacCli.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
            for(int i = 0; i < rgGeneroCli.getChildCount(); i++){
                ((RadioButton) rgGeneroCli.getChildAt(i)).setEnabled(false);
            }
            tvEstadoNacCli.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
            etCurpIdCli.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked_right));
            tvTipoIdentificacion.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
            etNumIdentifCli.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
            tvEstudiosCli.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
            tvEstadoCivilCli.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
            for(int i = 0; i < rgBienes.getChildCount(); i++){
                ((RadioButton) rgBienes.getChildAt(i)).setEnabled(false);
            }

            etTelCasaCli.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
            etCelularCli.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
            etTelMensCli.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));

            etCalleCli.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
            etNoExtCli.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
            etNoIntCli.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
            etManzanaCli.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
            etLoteCli.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
            etCpCli.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
            tvColoniaCli.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
            tvTipoCasaCli.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
            tvCasaFamiliar.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
            etTiempoSitio.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
            etReferenciaCli.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));

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
            etReferenciNeg.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));

            etNombreCony.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
            etApPaternoCony.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
            etApMaternoCony.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
            tvOcupacionCony.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
            etCelularCony.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
            etIngresosCony.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));

            tvMedioContacto.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
            etEmail.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
            for(int i = 0; i < rgEstatus.getChildCount(); i++){
                ((RadioButton) rgEstatus.getChildAt(i)).setEnabled(false);
            }
            etCredSolicitado.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
            cbCasaReuniones.setEnabled(false);
        }

    }

    private boolean saveDatosIntegrante(){
        boolean save_integrante = false;
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
                        if (!validatorTV.validate(tvTipoIdentificacion, new String[]{validatorTV.REQUIRED}) &&
                        !validator.validate(etNumIdentifCli, new String[]{validator.REQUIRED}) &&
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
                                ivError1.setVisibility(View.GONE);
                                cv.put("fecha_nacimiento", tvFechaNacCli.getText().toString());
                                cv.put("edad", Integer.parseInt(tvEdadCli.getText().toString()));
                                switch (rgGeneroCli.getCheckedRadioButtonId()){
                                    case R.id.rbHombre:
                                        cv.put("genero", 0);
                                        break;
                                    case R.id.rbMujer:
                                        cv.put("genero", 1);
                                        break;
                                }
                                cv.put("estado_nacimiento", tvEstadoNacCli.getText().toString().trim());
                                cv.put("rfc", tvRfcCli.getText().toString().trim());
                                cv.put("curp", tvCurpCli.getText().toString().trim());
                                cv.put("curp_digito_veri", etCurpIdCli.getText().toString().trim());
                                cv.put("tipo_identificacion", tvTipoIdentificacion.getText().toString().trim());
                                cv.put("no_identificacion", etNumIdentifCli.getText().toString().trim().toUpperCase());
                                cv.put("nivel_estudio", tvEstudiosCli.getText().toString());
                                cv.put("estatus_completado", 1);

                                if (ENVIROMENT)
                                    db.update(DATOS_INTEGRANTES_GPO, cv, "id = ?", new String[]{id_integrante});
                                else
                                    db.update(DATOS_INTEGRANTES_GPO_T, cv, "id = ?", new String[]{id_integrante});

                                save_integrante = true;
                            }
                            else{
                                ivError1.setVisibility(View.VISIBLE);
                                tvBienes.setError("");
                            }
                        }
                        else
                            ivError1.setVisibility(View.VISIBLE);
                    }
                    else{
                        ivError1.setVisibility(View.VISIBLE);
                        etCurpIdCli.setError("Curp no válida");
                    }
                }
                else
                    ivError1.setVisibility(View.VISIBLE);
            }
            else{
                ivError1.setVisibility(View.VISIBLE);
                tvGeneroCli.setError("");
            }
        }
        else
            ivError1.setVisibility(View.VISIBLE);


        return save_integrante;
    }
    private boolean saveDatosTelefonicos(){
        boolean save_telefonicos = false;
        if (!validator.validate(etTelCasaCli, new String[]{validator.PHONE}) &&
        !validator.validate(etCelularCli, new String[]{validator.REQUIRED, validator.ONLY_NUMBER, validator.PHONE}) &&
        !validator.validate(etTelMensCli, new String[]{validator.PHONE})){
            ivError2.setVisibility(View.GONE);
            ContentValues cv = new ContentValues();
            cv.put("tel_casa", etTelCasaCli.getText().toString().trim());
            cv.put("tel_celular", etCelularCli.getText().toString());
            cv.put("tel_mensaje", etTelMensCli.getText().toString());
            cv.put("estatus_completado", 1);

            if (ENVIROMENT)
                db.update(TELEFONOS_INTEGRANTE, cv, "id_integrante = ?", new String[]{id_integrante});
            else
                db.update(TELEFONOS_INTEGRANTE_T, cv, "id_integrante = ?", new String[]{id_integrante});

            save_telefonicos = true;
        }
        else
            ivError2.setVisibility(View.VISIBLE);
        return save_telefonicos;
    }
    private boolean saveDatosDomicilio(){
        boolean save_domicilio = false;
        ContentValues cv = new ContentValues();
        if (latLngUbiCli != null) {
            tvMapaCli.setError(null);
            if (!validator.validate(etCalleCli, new String[]{validator.REQUIRED}) &&
            !validator.validate(etNoExtCli, new String[]{validator.REQUIRED}) &&
            !validator.validate(etCpCli, new String[]{validator.REQUIRED, validator.ONLY_NUMBER, validator.CP}) &&
            !Miscellaneous.ValidTextView(tvColoniaCli) &&
            !Miscellaneous.ValidTextView(tvMunicipioCli) &&
            !Miscellaneous.ValidTextView(tvEstadoCli) &&
            !validatorTV.validate(tvTipoCasaCli, new String[]{validatorTV.REQUIRED})){
                boolean flag_tipo_casa = false;
                cv.put("tipo_vivienda", tvTipoCasaCli.getText().toString().trim().toUpperCase());
                switch (tvTipoCasaCli.getText().toString().trim().toUpperCase()){
                    case "CASA FAMILIAR":
                        if (!validatorTV.validate(tvCasaFamiliar, new String[]{validatorTV.REQUIRED})) {
                            flag_tipo_casa = true;
                            cv.put("parentesco", tvCasaFamiliar.getText().toString().trim().toUpperCase());
                            cv.put("otro_tipo_vivienda", "");
                        }

                        break;
                    case "OTRO":
                        if (!validator.validate(etOTroTipoCli, new String[]{validator.REQUIRED})) {
                            flag_tipo_casa = true;
                            cv.put("parentesco", "");
                            cv.put("otro_tipo_vivienda", etOTroTipoCli.getText().toString().trim().toUpperCase());
                        }
                        break;
                    default:
                        flag_tipo_casa = true;
                        cv.put("parentesco", "");
                        cv.put("otro_tipo_vivienda", "");
                        break;
                }

                if (flag_tipo_casa){
                    if (!validator.validate(etTiempoSitio, new String[]{validator.REQUIRED, validator.ONLY_NUMBER, validator.YEARS})){
                        if (byteFotoFachCli != null){
                            if (!validator.validate(etReferenciaCli, new String[]{validator.REQUIRED})){
                                ivError3.setVisibility(View.GONE);
                                cv.put("latitud", String.valueOf(latLngUbiCli.latitude));
                                cv.put("longitud", String.valueOf(latLngUbiCli.longitude));
                                cv.put("calle", etCalleCli.getText().toString().trim().toUpperCase());
                                cv.put("no_exterior", etNoExtCli.getText().toString().trim().toUpperCase());
                                cv.put("no_interior", etNoIntCli.getText().toString().trim().toUpperCase());
                                cv.put("manzana", etManzanaCli.getText().toString().trim().toUpperCase());
                                cv.put("lote", etLoteCli.getText().toString().trim().toUpperCase());
                                cv.put("cp", etCpCli.getText().toString().trim());
                                cv.put("colonia", tvColoniaCli.getText().toString().trim().toUpperCase());
                                cv.put("ref_domiciliaria", etReferenciaCli.getText().toString().trim().toUpperCase());
                                cv.put("estatus_completado", 1);
                                if (ENVIROMENT)
                                    db.update(DOMICILIO_INTEGRANTE, cv, "id_integrante = ?", new String[]{id_integrante});
                                else
                                    db.update(DOMICILIO_INTEGRANTE_T, cv, "id_integrante = ?", new String[]{id_integrante});

                                save_domicilio = true;
                            }
                            else
                                ivError3.setVisibility(View.VISIBLE);
                        }
                        else {
                            tvFachadaCli.setError("");
                            ivError3.setVisibility(View.VISIBLE);
                        }
                    }
                    else
                        ivError3.setVisibility(View.VISIBLE);
                }
                else
                    ivError3.setVisibility(View.VISIBLE);
            }
            else
                ivError3.setVisibility(View.VISIBLE);
        }
        else{
            ivError3.setVisibility(View.VISIBLE);
            tvMapaCli.setError("");
        }
        return save_domicilio;
    }
    private boolean saveDatosNegocio(){
        boolean save_negocio = false;
        if (!validator.validate(etNombreNeg, new String[]{validator.REQUIRED, validator.GENERAL})){
            if (latLngUbiNeg != null){
                tvMapaNeg.setError(null);
                if (!validator.validate(etCalleNeg, new String[]{validator.REQUIRED}) &&
                !validator.validate(etNoExtNeg, new String[]{validator.REQUIRED}) &&
                !validator.validate(etCpNeg, new String[]{validator.REQUIRED, validator.ONLY_NUMBER, validator.CP}) &&
                !Miscellaneous.ValidTextView(tvColoniaNeg) &&
                !Miscellaneous.ValidTextView(tvMunicipioNeg) &&
                !validatorTV.validate(tvActEconomicaNeg, new String[]{validatorTV.REQUIRED}) &&
                !validator.validate(etAntiguedadNeg, new String[]{validator.REQUIRED, validator.ONLY_NUMBER, validator.YEARS}) &&
                !validator.validate(etIngMenNeg, new String[]{validator.REQUIRED, validator.ONLY_NUMBER}) &&
                !validator.validate(etOtrosIngNeg, new String[]{validator.REQUIRED, validator.ONLY_NUMBER}) &&
                !validator.validate(etGastosSemNeg, new String[]{validator.REQUIRED, validator.ONLY_NUMBER}) &&
                !validatorTV.validate(tvActEconomicaNeg, new String[]{validatorTV.REQUIRED})){
                    if (byteFotoFachNeg != null){
                        if (!validator.validate(etReferenciNeg, new String[]{validator.REQUIRED, validator.ALFANUMERICO})){
                            ivError4.setVisibility(View.GONE);
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
                            cv.put("colonia", tvColoniaNeg.getText().toString().toUpperCase());
                            cv.put("actividad_economica", tvActEconomicaNeg.getText().toString().trim().toUpperCase());
                            cv.put("antiguedad", etAntiguedadNeg.getText().toString().trim());
                            cv.put("ing_mensual", etIngMenNeg.getText().toString().trim());
                            cv.put("ing_otros", etOtrosIngNeg.getText().toString().trim());
                            cv.put("gasto_semanal", etGastosSemNeg.getText().toString().trim());
                            cv.put("capacidad_pago", tvCapacidadPagoNeg.getText().toString().trim());
                            cv.put("ref_domiciliaria", etReferenciNeg.getText().toString().trim().toUpperCase());
                            cv.put("estatus_completado", 1);
                            if (ENVIROMENT)
                                db.update(NEGOCIO_INTEGRANTE, cv, "id_integrante = ?", new String[]{id_integrante});
                            else
                                db.update(NEGOCIO_INTEGRANTE_T, cv, "id_integrante = ?", new String[]{id_integrante});

                            save_negocio = true;
                        }
                        else
                            ivError4.setVisibility(View.VISIBLE);
                    }
                    else {
                        tvFachadaNeg.setError("");
                        ivError4.setVisibility(View.VISIBLE);
                    }
                }
                else
                    ivError4.setVisibility(View.VISIBLE);
            }
            else{
                tvMapaNeg.setError("");
                ivError4.setVisibility(View.VISIBLE);
            }
        }
        else
            ivError4.setVisibility(View.VISIBLE);

        return save_negocio;
    }
    private boolean saveConyuge(){
        Toast.makeText(ctx, "Guarda datos conyuge", Toast.LENGTH_SHORT).show();
        boolean save_conyuge = false;
        if (!validator.validate(etNombreCony, new String[]{validator.REQUIRED, validator.ONLY_TEXT}) &&
        !validator.validate(etApPaternoCony, new String[]{validator.REQUIRED, validator.ONLY_TEXT}) &&
        !validator.validate(etApMaternoCony, new String[]{validator.REQUIRED, validator.ONLY_TEXT}) &&
        !validatorTV.validate(tvOcupacionCony, new String[]{validatorTV.REQUIRED}) &&
        !validator.validate(etCelularCony, new String[]{validator.REQUIRED, validator.ONLY_NUMBER, validator.PHONE}) &&
        !validator.validate(etIngresosCony, new String[]{validator.REQUIRED, validator.ONLY_NUMBER})){
            ivError5.setVisibility(View.GONE);
            ContentValues cv = new ContentValues();
            cv.put("nombre", etNombreCony.getText().toString().trim().toUpperCase());
            cv.put("paterno", etApPaternoCony.getText().toString().trim().toUpperCase());
            cv.put("materno", etApMaternoCli.getText().toString().trim().toUpperCase());
            cv.put("ocupacion", tvOcupacionCony.getText().toString().trim().toUpperCase());
            cv.put("tel_celular", etCelularCony.getText().toString().trim());
            cv.put("ingresos", etIngresosCony.getText().toString().trim());
            cv.put("estatus_completado", 1);

            if (ENVIROMENT)
                db.update(CONYUGE_INTEGRANTE, cv, "id_integrante = ?", new String[]{id_integrante});
            else
                db.update(CONYUGE_INTEGRANTE_T, cv, "id_integrante = ?", new String[]{id_integrante});

            save_conyuge = true;
        }
        else
            ivError5.setVisibility(View.VISIBLE);

        return save_conyuge;
    }
    private boolean saveDatosOtros(){
        boolean save_otros = false;
        if (!validatorTV.validate(tvMedioContacto, new String[]{validatorTV.REQUIRED}) &&
        !validator.validate(etEmail, new String[]{validator.EMAIL})){
            if (rgEstatus.getCheckedRadioButtonId() == R.id.rbNuevo ||
            rgEstatus.getCheckedRadioButtonId() == R.id.rbRenovado ||
            rgEstatus.getCheckedRadioButtonId() == R.id.rbCambio){
                if (!validator.validate(etCredSolicitado, new String[]{validator.REQUIRED, validator.CREDITO})){
                    if (!cbCasaReuniones.isEnabled() || cbCasaReuniones.isChecked()){
                        if (byteFirmaCli != null){
                            ivError6.setVisibility(View.GONE);
                            ContentValues cv = new ContentValues();
                            cv.put("medio_contacto", tvMedioContacto.getText().toString().trim().toUpperCase());
                            cv.put("email", etEmail.getText().toString().trim());
                            switch (rgEstatus.getCheckedRadioButtonId()){
                                case R.id.rbNuevo:
                                    cv.put("estatus_integrante", 1);
                                    break;
                                case R.id.rbRenovado:
                                    cv.put("estatus_integrante", 2);
                                    break;
                                case R.id.rbCambio:
                                    cv.put("estatus_integrante", 3);
                                    break;
                            }
                            cv.put("monto_solicitado", etCredSolicitado.getText().toString().trim());
                            if (cbCasaReuniones.isChecked())
                                cv.put("casa_reunion", 1);
                            else
                                cv.put("casa_reunion", 0);
                            cv.put("estatus_completado", 1);

                            if (ENVIROMENT)
                                db.update(OTROS_DATOS_INTEGRANTE, cv, "id_integrante = ?", new String[]{id_integrante});
                            else
                                db.update(OTROS_DATOS_INTEGRANTE_T, cv, "id_integrante = ?", new String[]{id_integrante});

                        }
                        else{
                            tvFirmaCli.setError("");
                            ivError6.setVisibility(View.VISIBLE);
                        }
                    }
                    else {
                        ivError6.setVisibility(View.VISIBLE);
                        cbCasaReuniones.setError("");
                    }
                }
                else
                    ivError6.setVisibility(View.VISIBLE);
            }
            else {
                tvEstatus.setError("");
                ivError6.setVisibility(View.VISIBLE);
            }
        }
        else
            ivError6.setVisibility(View.VISIBLE);

        return save_otros;
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

                break;
            case R.id.save:
                boolean datos_personales = saveDatosIntegrante();
                boolean datos_telefonicos = saveDatosTelefonicos();
                boolean datos_domiclio = saveDatosDomicilio();
                boolean datos_negocio = saveDatosNegocio();
                boolean datos_conyuge = false;
                if (tvEstadoCivilCli.getText().toString().equals("CASADA(O)") ||
                tvEstadoCivilCli.getText().toString().equals("UNION LIBRE")){
                    datos_conyuge = saveConyuge();
                }
                else
                    datos_conyuge = true;
                boolean datos_otros = saveDatosOtros();

                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onComplete(long id_integrante, String nombre, String paterno, String materno, String cargo) {
        if (id_integrante > 0) {
            Log.e("id_Credito", "cccc"+id_integrante);
            tvCargo.setText(cargo);
            etNombreCli.setText(nombre);
            etNombreCli.setEnabled(false);
            etApPaternoCli.setText(paterno);
            etApPaternoCli.setEnabled(false);
            etApMaternoCli.setText(materno);
            etApMaternoCli.setEnabled(false);
            Cursor row_casa = dBhelper.customSelect(Constants.DATOS_INTEGRANTES_GPO_T + " AS i INNER JOIN " + Constants.OTROS_DATOS_INTEGRANTE_T + " AS od ON od.id_integrante = i.id", "i.id", " WHERE i.id_credito = " + id_credito + " AND od.casa_reunion = 1", "", null);
            row_casa.moveToFirst();
            if (row_casa.getCount() > 0 && row_casa.getInt(0) != id_integrante){
                cbCasaReuniones.setEnabled(false);
            }
        }
        else
            finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ContentValues cv = null;
        switch (requestCode) {
            case Constants.REQUEST_CODE_ESTADO_NAC:
                if (resultCode == Constants.REQUEST_CODE_ESTADO_NAC) {
                    if (data != null) {
                        tvEstadoNacCli.setError(null);
                        tvEstadoNacCli.setText(((ModeloCatalogoGral) data.getSerializableExtra(Constants.ITEM)).getNombre());
                        HashMap<Integer, String> params = new HashMap<>();

                        params.put(0, etNombreCli.getText().toString());
                        params.put(1, etApPaternoCli.getText().toString());
                        params.put(2, etApMaternoCli.getText().toString());
                        params.put(3, tvFechaNacCli.getText().toString());

                        if (rgGeneroCli.getCheckedRadioButtonId() == R.id.rbHombre)
                            params.put(4, "Hombre");
                        else if (rgGeneroCli.getCheckedRadioButtonId() == R.id.rbMujer)
                            params.put(4, "Mujer");
                        else
                            params.put(4, "");

                        if (!tvEstadoNacCli.getText().toString().trim().isEmpty())
                            params.put(5, tvEstadoNacCli.getText().toString().trim());
                        else
                            params.put(5, "");

                        cv = new ContentValues();
                        cv.put("estado_nacimiento",tvEstadoNacCli.getText().toString().trim().toUpperCase());
                        if (ENVIROMENT)
                            db.update(DATOS_INTEGRANTES_GPO, cv, "id = ?", new String[]{id_integrante});
                        else
                            db.update(DATOS_INTEGRANTES_GPO_T, cv, "id = ?", new String[]{id_integrante});
                        tvCurpCli.setText(Miscellaneous.GenerarCurp(params));
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
                                db.update(DOMICILIO_INTEGRANTE, cv, "id_integrante = ?", new String[]{id_integrante});
                            else
                                db.update(DOMICILIO_INTEGRANTE_T, cv, "id_integrante = ?", new String[]{id_integrante});
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
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
                            db.update(DOMICILIO_INTEGRANTE, cv, "id_integrante = ?", new String[]{id_integrante});
                        else
                            db.update(DOMICILIO_INTEGRANTE_T, cv, "id_integrante = ?", new String[]{id_integrante});
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
                            db.update(NEGOCIO_INTEGRANTE, cv, "id_integrante = ?", new String[]{id_integrante});
                        else
                            db.update(NEGOCIO_INTEGRANTE_T, cv, "id_integrante = ?", new String[]{id_integrante});
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
                            db.update(NEGOCIO_INTEGRANTE, cv, "id_integrante = ?", new String[]{id_integrante});
                        else
                            db.update(NEGOCIO_INTEGRANTE_T, cv, "id_integrante = ?", new String[]{id_integrante});
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
                                db.update(NEGOCIO_INTEGRANTE, cv, "id_integrante = ?", new String[]{id_integrante});
                            else
                                db.update(NEGOCIO_INTEGRANTE_T, cv, "id_integrante = ?", new String[]{id_integrante});
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
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
                            db.update(CONYUGE_INTEGRANTE, cv, "id_integrante = ?", new String[]{id_integrante});
                        else
                            db.update(CONYUGE_INTEGRANTE_T, cv, "id_integrante = ?", new String[]{id_integrante});
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
                                db.update(OTROS_DATOS_INTEGRANTE, cv, "id_integrante = ?", new String[]{id_integrante});
                            else
                                db.update(OTROS_DATOS_INTEGRANTE_T, cv, "id_integrante = ?", new String[]{id_integrante});
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                break;
        }
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
        }
    }
}
