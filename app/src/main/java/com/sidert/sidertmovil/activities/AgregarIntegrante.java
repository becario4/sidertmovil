package com.sidert.sidertmovil.activities;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.LocationManager;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
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
import com.google.android.gms.maps.model.MarkerOptions;
import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.database.DBhelper;
import com.sidert.sidertmovil.fragments.dialogs.dialog_date_picker;
import com.sidert.sidertmovil.models.ModeloCatalogoGral;
import com.sidert.sidertmovil.utils.Constants;
import com.sidert.sidertmovil.utils.Miscellaneous;
import com.sidert.sidertmovil.utils.MyCurrentListener;
import com.sidert.sidertmovil.utils.NameFragments;
import com.sidert.sidertmovil.utils.NetworkStatus;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import moe.feng.common.stepperview.VerticalStepperItemView;

public class AgregarIntegrante extends AppCompatActivity {

    private Context ctx;
    private DBhelper dBhelper;
    private SQLiteDatabase db;
    private Calendar myCalendar;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    //===================  DATOS PERSONALES  ==================================
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
    private EditText etRfcClaveCli;
    private TextView tvRfcCli;
    private TextView tvEstadoCivilCli;
    private Spinner spEstadoCivilCli;
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
    private EditText etCpCli;
    private TextView tvColoniaCli;
    private TextView tvMunicipioCli;
    private TextView tvEstadoCli;
    private TextView tvTipoCasaCli;
    private Spinner spTipoCasaCli;
    private LinearLayout llCasaFamiliar;
    private Spinner spCasaFamiliar;
    private LinearLayout llCasaOtroCli;
    private EditText etOTroTipoCli;
    private ImageButton ibFotoFachCli;
    private ImageView ivFotoFachCli;
    public byte[] byteFotoFachCli;
    //=========================================================================
    //===================  DATOS NEGOCIO  =====================================
    private EditText etNombreNeg;
    private ImageButton ibMapNeg;
    private ProgressBar pbLoadNeg;
    private MapView mapNeg;
    private LatLng latLngUbiNeg;
    private EditText etCalleNeg;
    private EditText etNoExtNeg;
    private EditText etNoIntNeg;
    private EditText etCpNeg;
    private TextView tvColoniaNeg;
    private TextView tvMunicipioNeg;
    private TextView tvActEconomicaNeg;
    private EditText etAntiguedadNeg;
    private EditText etIngMenNeg;
    private EditText etOtrosIngNeg;
    private EditText etGastosSemNeg;
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
    private EditText etEmail;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_integrante);

        ctx = this;
        dBhelper = new DBhelper(ctx);
        db = dBhelper.getWritableDatabase();
        myCalendar = Calendar.getInstance();

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
        etRfcClaveCli       = findViewById(R.id.etRfcClaveCli);
        tvCurpCli           = findViewById(R.id.tvCurpCli);
        etCurpIdCli         = findViewById(R.id.etCurpIdCli);
        tvEstadoCivilCli    = findViewById(R.id.tvEstadoCivilCli);
        spEstadoCivilCli    = findViewById(R.id.spEstadoCivilCli);
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
        etCpCli             = findViewById(R.id.etCpCli);
        tvColoniaCli        = findViewById(R.id.tvColoniaCli);
        tvMunicipioCli      = findViewById(R.id.tvMunicipioCli);
        tvEstadoCli         = findViewById(R.id.tvEstadoCli);
        tvTipoCasaCli       = findViewById(R.id.tvTipoCasaCli);
        spTipoCasaCli       = findViewById(R.id.spTipoCasaCli);
        llCasaFamiliar      = findViewById(R.id.llCasaFamiliar);
        spCasaFamiliar      = findViewById(R.id.spCasaFamiliar);
        llCasaOtroCli       = findViewById(R.id.llCasaOtro);
        etOTroTipoCli       = findViewById(R.id.etOtroTipoCli);
        ibFotoFachCli       = findViewById(R.id.ibFotoFachCli);
        ivFotoFachCli       = findViewById(R.id.ivFotoFachCli);
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
        tvColoniaNeg        = findViewById(R.id.tvColoniaNeg);
        tvMunicipioNeg      = findViewById(R.id.tvMunicipioNeg);
        tvActEconomicaNeg   = findViewById(R.id.tvActEconomicaNeg);
        etAntiguedadNeg     = findViewById(R.id.etAntiguedadNeg);
        etIngMenNeg         = findViewById(R.id.etIngMenNeg);
        etOtrosIngNeg       = findViewById(R.id.etOtrosIngNeg);
        etGastosSemNeg      = findViewById(R.id.etGastosSemNeg);
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
        etEmail             = findViewById(R.id.etEmail);
        ibFirmaCli          = findViewById(R.id.ibFirmaCli);
        ivFirmaCli          = findViewById(R.id.ivFirmaCli);
        //==========================================================================================
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
        llDatosPersonales = findViewById(R.id.llDatosPersonales);
        llDatosTelefonicos = findViewById(R.id.llDatosTelefonicos);
        llDatosDomicilio = findViewById(R.id.llDatosDomicilio);
        llDatosNegocio = findViewById(R.id.llDatosNegocio);
        llDatosConyuge = findViewById(R.id.llDatosConyuge);
        llOtrosDatos = findViewById(R.id.llOtrosDatos);

        llPersonales = findViewById(R.id.llPersonales);
        llTelefonicos = findViewById(R.id.llTelefonicos);
        llDomicilio = findViewById(R.id.llDomicilio);
        llNegocio = findViewById(R.id.llNegocio);
        llConyuge = findViewById(R.id.llConyuge);
        llOtros = findViewById(R.id.llOtros);
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
                    else
                        tvRfcCli.setText(s.toString().substring(0,10));

                }
                else
                    tvRfcCli.setText("Rfc no válida");
            }
        });
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
        //===========================================================================
        //==============================  DOMICILIO LISTENER =======================================
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
                            tvColoniaCli.setText(row.getString(7));
                            tvMunicipioCli.setText(row.getString(4));
                            tvEstadoCli.setText(row.getString(1));
                        }else {
                            tvColoniaCli.setText("");
                            tvMunicipioCli.setText(row.getString(4));
                            tvEstadoCli.setText(row.getString(1));
                        }
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
        spTipoCasaCli.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
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
        ibFotoFachCli.setOnClickListener(ibFotoFachCli_OnClick);
        //===========================================================================
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
                            tvColoniaNeg.setText(row.getString(7));
                            tvMunicipioNeg.setText(row.getString(4));
                        }else {
                            tvColoniaNeg.setText("");
                            tvMunicipioNeg.setText(row.getString(4));
                        }
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

        ibFotoFachNeg.setOnClickListener(ibFotoFachNeg_OnClick);
        //===========================================================================
        //==================================  CONYUGE LISTENER  ====================================
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
                    if (s.length() == 10)
                        etCelularCony.setError(null);
                    else
                        etCelularCony.setError(ctx.getResources().getString(R.string.mensaje_telefono));
                }
                else
                    etCelularCony.setError(null);
            }
        });
        //===========================================================================
        //==================================  OTROS LISTENER  ======================================
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
                }
            }
        });

        mapCli.onCreate(savedInstanceState);
        mapNeg.onCreate(savedInstanceState);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
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
            dialog_date_picker dialogDatePicker = new dialog_date_picker();
            Bundle b = new Bundle();

            b.putInt(Constants.YEAR_CURRENT,myCalendar.get(Calendar.YEAR));
            b.putInt(Constants.MONTH_CURRENT,myCalendar.get(Calendar.MONTH));
            b.putInt(Constants.DAY_CURRENT,myCalendar.get(Calendar.DAY_OF_MONTH));
            b.putString(Constants.DATE_CURRENT,sdf.format(myCalendar.getTime()));
            b.putInt(Constants.IDENTIFIER,4);
            b.putBoolean(Constants.FECHAS_POST, false);
            dialogDatePicker.setArguments(b);
            dialogDatePicker.show(getSupportFragmentManager(), NameFragments.DIALOGDATEPICKER);
        }
    };
    private View.OnClickListener tvEstadoNacCli_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i_estados = new Intent(ctx, Catalogos.class);
            i_estados.putExtra(Constants.TITULO,Miscellaneous.ucFirst(Constants.ESTADOS));
            i_estados.putExtra(Constants.CATALOGO,Constants.ESTADOS);
            i_estados.putExtra(Constants.REQUEST_CODE, Constants.REQUEST_CODE_ESTADO_NAC);
            startActivityForResult(i_estados,Constants.REQUEST_CODE_ESTADO_NAC);
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
            Intent i_colonia = new Intent(ctx, Catalogos.class);
            i_colonia.putExtra(Constants.TITULO,Miscellaneous.ucFirst(Constants.ESTADOS));
            i_colonia.putExtra(Constants.CATALOGO,Constants.COLONIAS);
            i_colonia.putExtra(Constants.EXTRA, etCpCli.getText().toString().trim());
            i_colonia.putExtra(Constants.REQUEST_CODE, Constants.REQUEST_CODE_COLONIA_CLIE);
            startActivityForResult(i_colonia,Constants.REQUEST_CODE_COLONIA_CLIE);
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
            Intent i_colonia = new Intent(ctx, Catalogos.class);
            i_colonia.putExtra(Constants.TITULO,Miscellaneous.ucFirst(Constants.COLONIAS));
            i_colonia.putExtra(Constants.CATALOGO,Constants.COLONIAS);
            i_colonia.putExtra(Constants.EXTRA, etCpNeg.getText().toString().trim());
            i_colonia.putExtra(Constants.REQUEST_CODE, Constants.REQUEST_CODE_COLONIA_NEG);
            startActivityForResult(i_colonia,Constants.REQUEST_CODE_COLONIA_NEG);
        }
    };

    private View.OnClickListener tvActEconomicaNeg_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i_ocupaciones = new Intent(ctx, Catalogos.class);
            i_ocupaciones.putExtra(Constants.TITULO,Miscellaneous.ucFirst(Constants.SECTORES));
            i_ocupaciones.putExtra(Constants.CATALOGO,Constants.SECTORES);
            i_ocupaciones.putExtra(Constants.REQUEST_CODE, Constants.REQUEST_CODE_ACTIVIDAD_NEG);
            startActivityForResult(i_ocupaciones,Constants.REQUEST_CODE_ACTIVIDAD_NEG);
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
            Intent i_ocupaciones = new Intent(ctx, Catalogos.class);
            i_ocupaciones.putExtra(Constants.TITULO,Miscellaneous.ucFirst(Constants.OCUPACIONES));
            i_ocupaciones.putExtra(Constants.CATALOGO,Constants.OCUPACIONES);
            i_ocupaciones.putExtra(Constants.REQUEST_CODE, Constants.REQUEST_CODE_OCUPACION_CONY);
            startActivityForResult(i_ocupaciones,Constants.REQUEST_CODE_OCUPACION_CONY);
        }
    };
    //==============================================================================================
    //============================ ACTION OTROS  ===================================================
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

            if (spEstadoCivilCli.getSelectedItemPosition() == 2 || spEstadoCivilCli.getSelectedItemPosition() == 5){
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
            etNombreCli.requestFocus();
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

            if (spEstadoCivilCli.getSelectedItemPosition() == 2 || spEstadoCivilCli.getSelectedItemPosition() == 5){
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
            switch (campo){
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
                    tvCurpCli.setText(Miscellaneous.GenerarCurp(params));
                    break;
            }


        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case Constants.REQUEST_CODE_ESTADO_NAC:
                if (resultCode == Constants.REQUEST_CODE_ESTADO_NAC) {
                    if (data != null) {
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
                        tvCurpCli.setText(Miscellaneous.GenerarCurp(params));
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
            case Constants.REQUEST_CODE_COLONIA_CLIE:
                if (resultCode == Constants.REQUEST_CODE_COLONIA_CLIE){
                    if (data != null){
                        tvColoniaCli.setText(((ModeloCatalogoGral)data.getSerializableExtra(Constants.ITEM)).getNombre());
                    }
                }
                break;
            case Constants.REQUEST_CODE_ACTIVIDAD_NEG:
                if (resultCode == Constants.REQUEST_CODE_ACTIVIDAD_NEG){
                    if (data != null){
                        tvActEconomicaNeg.setText(((ModeloCatalogoGral)data.getSerializableExtra(Constants.ITEM)).getNombre());
                    }
                }
                break;
            case Constants.REQUEST_CODE_COLONIA_NEG:
                if (resultCode == Constants.REQUEST_CODE_COLONIA_NEG){
                    if (data != null){
                        tvColoniaNeg.setText(((ModeloCatalogoGral)data.getSerializableExtra(Constants.ITEM)).getNombre());
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
            case Constants.REQUEST_CODE_OCUPACION_CONY:
                if (resultCode == Constants.REQUEST_CODE_OCUPACION_CONY){
                    if (data != null){
                        tvOcupacionCony.setText(((ModeloCatalogoGral)data.getSerializableExtra(Constants.ITEM)).getNombre());
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
}
