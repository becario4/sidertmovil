package com.sidert.sidertmovil.activities;

import android.Manifest;
import android.app.ProgressDialog;
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
import com.sidert.sidertmovil.utils.Servicios_Sincronizado;
import com.sidert.sidertmovil.utils.Validator;
import com.sidert.sidertmovil.utils.ValidatorTextView;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.sidert.sidertmovil.utils.Constants.FORMAT_DATE_GNRAL;
import static com.sidert.sidertmovil.utils.Constants.TBL_AVAL_IND_AUTO;
import static com.sidert.sidertmovil.utils.Constants.TBL_CLIENTE_IND_AUTO;
import static com.sidert.sidertmovil.utils.Constants.TBL_CONYUGE_IND_AUTO;
import static com.sidert.sidertmovil.utils.Constants.TBL_CREDITO_IND_AUTO;
import static com.sidert.sidertmovil.utils.Constants.TBL_CROQUIS_IND_AUTO;
import static com.sidert.sidertmovil.utils.Constants.TBL_DIRECCIONES_AUTO;
import static com.sidert.sidertmovil.utils.Constants.TBL_ECONOMICOS_IND_AUTO;
import static com.sidert.sidertmovil.utils.Constants.TBL_NEGOCIO_IND_AUTO;
import static com.sidert.sidertmovil.utils.Constants.TBL_POLITICAS_PLD_IND_AUTO;
import static com.sidert.sidertmovil.utils.Constants.TBL_REFERENCIA_IND_AUTO;
import static com.sidert.sidertmovil.utils.Constants.TBL_SOLICITUDES_AUTO;
import static com.sidert.sidertmovil.utils.Constants.warning;

public class SolicitudInd extends AppCompatActivity {

    private Context ctx;

    private DBhelper dBhelper;
    private SQLiteDatabase db;

    //private boolean is_edit = true;

    private FloatingActionButton btnContinuar0;
    private FloatingActionButton btnContinuar1;
    private FloatingActionButton btnContinuar2;
    private FloatingActionButton btnContinuar3;
    private FloatingActionButton btnContinuar4;
    private FloatingActionButton btnContinuar5;
    private FloatingActionButton btnContinuar6;
    private FloatingActionButton btnContinuar7;
    private FloatingActionButton btnContinuar8;

    private FloatingActionButton btnRegresar1;
    private FloatingActionButton btnRegresar2;
    private FloatingActionButton btnRegresar3;
    private FloatingActionButton btnRegresar4;
    private FloatingActionButton btnRegresar5;
    private FloatingActionButton btnRegresar6;
    private FloatingActionButton btnRegresar7;
    private FloatingActionButton btnRegresar8;


    //======== DATOS CRÉDITO  ==================
    private LinearLayout llTipoSolicitud;
    private TextView tvTipoSolicitud;
    private TextView tvPlazo;
    private TextView tvFrecuencia;
    private TextView tvFechaDesembolso;
    private TextView tvDiaDesembolso;
    private TextView tvHoraVisita;
    private EditText etMontoPrestamo;
    private TextView tvCantidadLetra;
    private TextView tvDestino;
    private TextView tvRiesgo;
    private LinearLayout llMontoAutorizado;
    private EditText etMontoAutorizado;
    private LinearLayout llCantidaAutorizadaLetra;
    private TextView tvCantidadAutorizadaLetra;
    private TextView tvCreditoAnterior;
    private TextView tvNumCiclo;
    private TextView tvComportamiento;
    private EditText etObservaciones;
    //=========================================
    //======== DATOS PERSONALES ===============
    private LinearLayout llComentCli;
    private TextView tvComentAdminCli;
    private EditText etNombreCli;
    private EditText etApPaternoCli;
    private EditText etApMaternoCli;
    private TextView tvFechaNacCli;
    private TextView tvEdadCli;
    private TextView tvGeneroCli;
    private RadioGroup rgGeneroCli;
    private TextView tvEstadoNacCli;
    private EditText etCurpCli;
    //private EditText etCurpIdCli;
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
    private EditText etCiudadCli;
    private TextView tvLocalidadCli;
    private TextView tvMunicipioCli;
    private TextView tvEstadoCli;
    private EditText etTelCasaCli;
    private EditText etCelularCli;
    private EditText etTelMensCli;
    private EditText etTelTrabajoCli;
    private EditText etTiempoSitio;
    private TextView tvDependientes;
    private TextView tvEnteroNosotros;
    private TextView tvEstadoCuenta;
    private EditText etEmail;
    private TextView tvFachadaCli;
    private ImageButton ibFotoFachCli;
    private ImageView ivFotoFachCli;
    private MultiAutoCompleteTextView etReferenciCli;
    private TextView tvFirmaCli;
    private ImageButton ibFirmaCli;
    private ImageView ivFirmaCli;
    //=========================================
    //========== CONYUGE ======================
    private EditText etNombreCony;
    private EditText etApPaternoCony;
    private EditText etApMaternoCony;
    private EditText etNacionalidadCony;
    private TextView tvOcupacionCony;
    private EditText etCalleCony;
    private EditText etNoExtCony;
    private EditText etManzanaCony;
    private EditText etNoIntCony;
    private EditText etLoteCony;
    private EditText etCpCony;
    private TextView tvColoniaCony;
    private EditText etCiudadCony;
    private TextView tvLocalidadCony;
    private TextView tvMunicipioCony;
    private TextView tvEstadoCony;
    private EditText etIngMenCony;
    private EditText etGastoMenCony;
    private EditText etCasaCony;
    private EditText etCelularCony;
    //========================================
    //========= DATOS ECONOMICOS =============
    private EditText etPropiedadesEco;
    private EditText etValorAproxEco;
    private EditText etUbicacionEco;
    private TextView tvIngresoEco;
    //========================================
    //======== NEGOCIO =======================
    private LinearLayout llComentNeg;
    private TextView tvComentAdminNeg;
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
    private TextView tvActEcoEspNeg;
    private TextView tvActEconomicaNeg;
    private TextView tvDestinoNeg;
    private EditText etOtroDestinoNeg;
    private EditText etAntiguedadNeg;
    private EditText etIngMenNeg;
    private EditText etOtrosIngNeg;
    private EditText etGastosMenNeg;
    private EditText etGastosAguaNeg;
    private EditText etGastosLuzNeg;
    private EditText etGastosTelNeg;
    private EditText etGastosRentaNeg;
    private EditText etGastosOtrosNeg;
    private EditText etCapacidadPagoNeg;
    private TextView tvMediosPagoNeg;
    private EditText etOtroMedioPagoNeg;
    private TextView tvMontoMaxNeg;
    private TextView tvNumOperacionNeg;
    private LinearLayout llOperacionesEfectivo;
    private EditText etNumOperacionEfectNeg;
    private TextView tvDiasVentaNeg;
    private TextView tvFachadaNeg;
    private ImageButton ibFotoFachNeg;
    private ImageView ivFotoFachNeg;
    private MultiAutoCompleteTextView etReferenciNeg;
    //========================================
    //======= AVAL ===========================
    private LinearLayout llComentAval;
    private TextView tvComentAdminAval;
    private EditText etNombreAval;
    private EditText etApPaternoAval;
    private EditText etApMaternoAval;
    private TextView tvFechaNacAval;
    private TextView tvEdadAval;
    private TextView tvGeneroAval;
    private RadioGroup rgGeneroAval;
    private TextView tvEstadoNacAval;
    private TextView tvRfcAval;
    private EditText etCurpAval;
    //private EditText etCurpIdAval;
    private TextView tvParentescoAval;
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
    private EditText etCiudadAval;
    private TextView tvLocalidadAval;
    private TextView tvMunicipioAval;
    private TextView tvEstadoAval;
    private TextView tvTipoCasaAval;
    private TextView tvFamiliarAval;
    private LinearLayout llParentescoFamAval;
    private LinearLayout llNombreTitular;
    private EditText etNombreTitularAval;
    private EditText etCaracteristicasAval;
    private TextView tvNombreNegAval;
    private RadioGroup rgNegocioAval;
    private LinearLayout llNombreNegocio;
    private EditText etNombreNegocioAval;
    private EditText etAntiguedadAval;
    private EditText etIngMenAval;
    private EditText etIngOtrosAval;
    private EditText etGastosSemAval;
    private EditText etGastosAguaAval;
    private EditText etGastosLuzAval;
    private EditText etGastosTelAval;
    private EditText etGastosRentaAval;
    private EditText etGastosOtrosAval;
    private TextView tvMediosPagoAval;
    private EditText etOtroMedioPagoAval;
    private TextView tvMontoMaxAval;
    private EditText etCapacidadPagoAval;
    private TextView tvActivosObservables;
    private TextView tvHoraLocAval;
    private EditText etTelCasaAval;
    private EditText etCelularAval;
    private EditText etTelMensAval;
    private EditText etTelTrabajoAval;
    private EditText etEmailAval;
    private TextView tvFachadaAval;
    private ImageButton ibFotoFachAval;
    private ImageView ivFotoFachAval;
    private MultiAutoCompleteTextView etReferenciaAval;
    private TextView tvFirmaAval;
    private ImageButton ibFirmaAval;
    private ImageView ivFirmaAval;
    //========================================
    //========== REFERENCIA ==================
    private LinearLayout llComentRef;
    private TextView tvComentAdminRef;
    private EditText etNombreRef;
    private EditText etApPaternoRef;
    private EditText etApMaternoRef;
    private TextView tvFechaNacRef;
    private EditText etCalleRef;
    private EditText etNoExtRef;
    private EditText etManzanaRef;
    private EditText etNoIntRef;
    private EditText etLoteRef;
    private EditText etCpRef;
    private TextView tvColoniaRef;
    private EditText etCiudadRef;
    private TextView tvLocalidadRef;
    private TextView tvMunicipioRef;
    private TextView tvEstadoRef;
    private EditText etTelCelRef;
    //========================================
    //======= CROQUIS ========================
    private LinearLayout llComentCro;
    private TextView tvComentAdminCro;
    private TextView tvCasa;
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
    //================= Image View  =====================
    private ImageView ivDown1;
    private ImageView ivDown2;
    private ImageView ivDown3;
    private ImageView ivDown4;
    private ImageView ivDown5;
    private ImageView ivDown6;
    private ImageView ivDown7;
    private ImageView ivDown8;
    private ImageView ivDown9;
    private ImageView ivDown10;

    private ImageView ivUp1;
    private ImageView ivUp2;
    private ImageView ivUp3;
    private ImageView ivUp4;
    private ImageView ivUp5;
    private ImageView ivUp6;
    private ImageView ivUp7;
    private ImageView ivUp8;
    private ImageView ivUp9;
    private ImageView ivUp10;
    //========================================

    //===============  LINEAR LAYOUT  ====================
    private LinearLayout llDatosCredito;
    private LinearLayout llDatosPersonales;
    private LinearLayout llDatosConyuge;
    private LinearLayout llDatosEconomicos;
    private LinearLayout llDatosNegocio;
    private LinearLayout llDatosAval;
    private LinearLayout llDatosReferencia;
    private LinearLayout llDatosCroquis;
    private LinearLayout llDatosPoliticas;
    private LinearLayout llDatosDocumentos;

    private LinearLayout llCredito;
    private LinearLayout llPersonales;
    private LinearLayout llConyuge;
    private LinearLayout llPropiedades;
    private LinearLayout llEconomicos;
    private LinearLayout llNegocio;
    private LinearLayout llAval;
    private LinearLayout llReferencia;
    private LinearLayout llCroquis;
    private LinearLayout llPoliticas;
    //====================================================

    private LocationManager locationManager;
    private MyCurrentListener locationListener;
    private GoogleMap mMapCli;
    private GoogleMap mMapNeg;
    private GoogleMap mMapAval;

    private Validator validator;
    private ValidatorTextView validatorTV;

    private Toolbar TBmain;

    DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
    DecimalFormat df = new DecimalFormat("##,###.##", symbols);
    DecimalFormat dfnd = new DecimalFormat("#,###", symbols);

    //========== is edit fields
    boolean isEditCre = true;

    boolean hasFractionalPart = false;

    private Long id_solicitud = 0L;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solicitud_ind);

        ProgressDialog dialog = ProgressDialog.show(SolicitudInd.this, "",
                "Cargando la información por favor espere...", true);
        dialog.show();
        df.setDecimalSeparatorAlwaysShown(true);

        ctx = this;

        dBhelper = new DBhelper(ctx);
        db = dBhelper.getWritableDatabase();

        validator = new Validator();
        validatorTV = new ValidatorTextView();

        TBmain = findViewById(R.id.TBmain);

        setSupportActionBar(TBmain);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //=================================== DATOS CREDITO  =======================================
        llTipoSolicitud     = findViewById(R.id.llTipoSolicitud);
        tvTipoSolicitud     = findViewById(R.id.tvTipoSolicitud);
        llTipoSolicitud.setVisibility(View.VISIBLE);
        tvPlazo             = findViewById(R.id.tvPlazo);
        tvFrecuencia        = findViewById(R.id.tvFrecuencia);
        tvFechaDesembolso   = findViewById(R.id.tvFechaDesembolso);
        tvDiaDesembolso     = findViewById(R.id.tvDiaDesembolso);
        tvHoraVisita        = findViewById(R.id.tvHoraVisita);
        TextView tvMontoPrestamo     = findViewById(R.id.tvMontoPrestamo);
        tvMontoPrestamo.setText("Monto Solicitado");
        etMontoPrestamo     = findViewById(R.id.etMontoPrestamo);
        tvCantidadLetra     = findViewById(R.id.tvCantidadLetra);
        tvDestino           = findViewById(R.id.tvDestino);
        tvRiesgo            = findViewById(R.id.tvRiesgo);
        llMontoAutorizado   = findViewById(R.id.llMontoAutorizado);
        etMontoAutorizado   = findViewById(R.id.etMontoAutorizado);
        llCantidaAutorizadaLetra    = findViewById(R.id.llCantidadAutorizadaLetra);
        tvCantidadAutorizadaLetra   = findViewById(R.id.tvCantidadAutorizadaLetra);
        llMontoAutorizado.setVisibility(View.VISIBLE);
        llCantidaAutorizadaLetra.setVisibility(View.VISIBLE);
        TextView tvMontoAnterior = findViewById(R.id.tvMontoAnterior);
        tvCreditoAnterior   = findViewById(R.id.tvCreditoAnterior);
        TextView tvCiclo    = findViewById(R.id.tvCiclo);
        tvNumCiclo          = findViewById(R.id.tvNumCiclo);
        TextView tvTComportamiento = findViewById(R.id.tvComportamiento);
        tvComportamiento    = findViewById(R.id.tvComportamientoPago);
        TextView tvObservaciones = findViewById(R.id.tvObservaciones);
        etObservaciones     = findViewById(R.id.etObservaciones);
        tvMontoAnterior.setVisibility(View.GONE);
        tvCreditoAnterior.setVisibility(View.GONE);
        tvCiclo.setVisibility(View.GONE);
        tvNumCiclo.setVisibility(View.GONE);
        tvTComportamiento.setVisibility(View.GONE);
        tvComportamiento.setVisibility(View.GONE);
        tvObservaciones.setVisibility(View.GONE);
        etObservaciones.setVisibility(View.GONE);
        //==========================================================================================
        //=================================  DATOS PERSONALES  =====================================
        llComentCli          = findViewById(R.id.llComentCli);
        tvComentAdminCli     = findViewById(R.id.tvComentAdminCli);
        etNombreCli          = findViewById(R.id.etNombreCli);
        etApPaternoCli       = findViewById(R.id.etApPaternoCli);
        etApMaternoCli       = findViewById(R.id.etApMaternoCli);
        tvFechaNacCli        = findViewById(R.id.tvFechaNacCli);
        tvEdadCli            = findViewById(R.id.tvEdadCli);
        tvGeneroCli          = findViewById(R.id.tvGeneroCli);
        rgGeneroCli          = findViewById(R.id.rgGeneroCli);
        tvEstadoNacCli       = findViewById(R.id.tvEstadoNacCli);
        tvRfcCli             = findViewById(R.id.tvRfcCli);
        etCurpCli            = findViewById(R.id.etCurpCli);
        //etCurpIdCli          = findViewById(R.id.etCurpIdCli);
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
        etCiudadCli          = findViewById(R.id.etCiudadCli);
        tvLocalidadCli       = findViewById(R.id.tvLocalidadCli);
        tvMunicipioCli       = findViewById(R.id.tvMunicipioCli);
        tvEstadoCli          = findViewById(R.id.tvEstadoCli);
        etTelCasaCli         = findViewById(R.id.etTelCasaCli);
        etCelularCli         = findViewById(R.id.etCelularCli);
        etTelMensCli         = findViewById(R.id.etTelMensCli);
        etTelTrabajoCli      = findViewById(R.id.etTelTrabajoCli);
        etTiempoSitio        = findViewById(R.id.etTiempoSitio);
        tvDependientes       = findViewById(R.id.tvDependientes);
        tvEnteroNosotros     = findViewById(R.id.tvEnteroNosotros);
        tvEstadoCuenta       = findViewById(R.id.tvEstadoCuenta);
        etEmail              = findViewById(R.id.etEmail);
        tvFachadaCli         = findViewById(R.id.tvFachadaCli);
        tvFachadaCli.setVisibility(View.GONE);
        ibFotoFachCli        = findViewById(R.id.ibFotoFachCli);
        ibFotoFachCli.setVisibility(View.GONE);
        ivFotoFachCli        = findViewById(R.id.ivFotoFachCli);
        etReferenciCli       = findViewById(R.id.etReferenciaCli);
        tvFirmaCli           = findViewById(R.id.tvFirmaCli);
        tvFirmaCli.setVisibility(View.GONE);
        ibFirmaCli           = findViewById(R.id.ibFirmaCli);
        ibFirmaCli.setVisibility(View.GONE);
        ivFirmaCli           = findViewById(R.id.ivFirmaCli);
        //==========================================================================================
        //===================================  DATOS CONYUGE  ======================================
        etNombreCony        = findViewById(R.id.etNombreCony);
        etApPaternoCony     = findViewById(R.id.etApPaternoCony);
        etApMaternoCony     = findViewById(R.id.etApMaternoCony);
        etNacionalidadCony  = findViewById(R.id.etNacionalidadCony);
        tvOcupacionCony     = findViewById(R.id.tvOcupacionCony);
        etCalleCony         = findViewById(R.id.etCalleCony);
        etNoExtCony         = findViewById(R.id.etNoExtCony);
        etManzanaCony       = findViewById(R.id.etManzanaCony);
        etNoIntCony         = findViewById(R.id.etNoIntCony);
        etLoteCony          = findViewById(R.id.etLoteCony);
        etCpCony            = findViewById(R.id.etCpCony);
        tvColoniaCony       = findViewById(R.id.tvColoniaCony);
        etCiudadCony        = findViewById(R.id.etCiudadCony);
        tvLocalidadCony     = findViewById(R.id.tvLocalidadCony);
        tvMunicipioCony     = findViewById(R.id.tvMunicipioCony);
        tvEstadoCony        = findViewById(R.id.tvEstadoCony);
        etIngMenCony        = findViewById(R.id.etIngMenCony);
        etGastoMenCony      = findViewById(R.id.etGastoMenCony);
        etCasaCony          = findViewById(R.id.etCasaCony);
        etCelularCony       = findViewById(R.id.etCelularCony);
        //==========================================================================================
        //=================================  DATOS ECONOMICOS  =====================================
        etPropiedadesEco    = findViewById(R.id.etPropiedadesEco);
        etValorAproxEco     = findViewById(R.id.etValorAproxEco);
        etUbicacionEco      = findViewById(R.id.etUbicacionEco);
        tvIngresoEco        = findViewById(R.id.tvIngresoEco);
        //==========================================================================================
        //===================================  DATOS NEGOCIO  ======================================
        llComentNeg          = findViewById(R.id.llComentNeg);
        tvComentAdminNeg     = findViewById(R.id.tvComentAdminNeg);
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
        tvActEcoEspNeg          = findViewById(R.id.tvActEcoEspNeg);
        tvActEconomicaNeg       = findViewById(R.id.tvActEconomicaNeg);
        tvDestinoNeg            = findViewById(R.id.tvDestinoNeg);
        etOtroDestinoNeg        = findViewById(R.id.etOtroDestinoNeg);
        etAntiguedadNeg         = findViewById(R.id.etAntiguedadNeg);
        etIngMenNeg             = findViewById(R.id.etIngMenNeg);
        etOtrosIngNeg           = findViewById(R.id.etOtrosIngNeg);
        etGastosMenNeg          = findViewById(R.id.etGastosMenNeg);
        etGastosAguaNeg         = findViewById(R.id.etGastosAguaNeg);
        etGastosLuzNeg          = findViewById(R.id.etGastosLuzNeg);
        etGastosTelNeg          = findViewById(R.id.etGastosTelNeg);
        etGastosRentaNeg        = findViewById(R.id.etGastosRentaNeg);
        etGastosOtrosNeg        = findViewById(R.id.etGastosOtrosNeg);
        etCapacidadPagoNeg      = findViewById(R.id.etCapacidadPagoNeg);
        tvMediosPagoNeg         = findViewById(R.id.tvMediosPagoNeg);
        etOtroMedioPagoNeg      = findViewById(R.id.etOtroMedioPagoNeg);
        tvMontoMaxNeg           = findViewById(R.id.tvMontoMaxNeg);
        tvNumOperacionNeg       = findViewById(R.id.tvNumOperacionNeg);
        llOperacionesEfectivo   = findViewById(R.id.llOperacionesEfectivo);
        etNumOperacionEfectNeg  = findViewById(R.id.etNumOperacionEfectNeg);
        tvDiasVentaNeg          = findViewById(R.id.tvDiasVentaNeg);
        tvFachadaNeg            = findViewById(R.id.tvFachadaNeg);
        tvFachadaNeg.setVisibility(View.GONE);
        ibFotoFachNeg           = findViewById(R.id.ibFotoFachNeg);
        ibFotoFachNeg.setVisibility(View.GONE);
        ivFotoFachNeg           = findViewById(R.id.ivFotoFachNeg);
        etReferenciNeg          = findViewById(R.id.etReferenciaNeg);
        //==========================================================================================
        //=====================================  DATOS AVAL  =======================================
        llComentAval        = findViewById(R.id.llComentAval);
        tvComentAdminAval   = findViewById(R.id.tvComentAdminAval);
        etNombreAval        = findViewById(R.id.etNombreAval);
        etApPaternoAval     = findViewById(R.id.etApPaternoAval);
        etApMaternoAval     = findViewById(R.id.etApMaternoAval);
        tvFechaNacAval      = findViewById(R.id.tvFechaNacAval);
        tvEdadAval          = findViewById(R.id.tvEdadAval);
        tvGeneroAval        = findViewById(R.id.tvGeneroAval);
        rgGeneroAval        = findViewById(R.id.rgGeneroAval);
        tvEstadoNacAval     = findViewById(R.id.tvEstadoNacAval);
        tvRfcAval           = findViewById(R.id.tvRfcAval);
        etCurpAval          = findViewById(R.id.etCurpAval);
        //etCurpIdAval        = findViewById(R.id.etCurpIdAval);
        tvParentescoAval    = findViewById(R.id.tvParentescoAval);
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
        etCiudadAval        = findViewById(R.id.etCiudadAval);
        tvLocalidadAval     = findViewById(R.id.tvLocalidadAval);
        tvMunicipioAval     = findViewById(R.id.tvMunicipioAval);
        tvEstadoAval        = findViewById(R.id.tvEstadoAval);
        tvTipoCasaAval      = findViewById(R.id.tvTipoCasaAval);
        tvFamiliarAval      = findViewById(R.id.tvFamiliar);
        llParentescoFamAval = findViewById(R.id.llParentescoFamAval);
        llNombreTitular     = findViewById(R.id.llNombreTitular);
        etNombreTitularAval = findViewById(R.id.etNombreTitularAval);
        etCaracteristicasAval   = findViewById(R.id.etCaracteristicasAval);
        tvNombreNegAval         = findViewById(R.id.tvNombreNegAval);
        rgNegocioAval           = findViewById(R.id.rgNegocioAval);
        llNombreNegocio         = findViewById(R.id.llNombreNegocio);
        etNombreNegocioAval = findViewById(R.id.etNombreNegocioAval);
        etAntiguedadAval    = findViewById(R.id.etAntiguedadAval);
        etIngMenAval        = findViewById(R.id.etIngMenAval);
        etIngOtrosAval      = findViewById(R.id.etOtrosIngAval);
        etGastosSemAval     = findViewById(R.id.etGastosSemAval);
        etGastosAguaAval    = findViewById(R.id.etGastosAguaAval);
        etGastosLuzAval     = findViewById(R.id.etGastosLuzAval);
        etGastosTelAval     = findViewById(R.id.etGastosTelAval);
        etGastosRentaAval   = findViewById(R.id.etGastosRentaAval);
        etGastosOtrosAval   = findViewById(R.id.etGastosOtrosAval);
        tvMediosPagoAval    = findViewById(R.id.tvMediosPagoAval);
        etOtroMedioPagoAval = findViewById(R.id.etOtroMedioPagoAval);
        tvMontoMaxAval      = findViewById(R.id.tvMontoMaxAval);
        etCapacidadPagoAval = findViewById(R.id.etCapacidadPagoAval);
        tvHoraLocAval       = findViewById(R.id.tvHoraLocAval);
        tvActivosObservables = findViewById(R.id.tvActivosObservables);
        etTelCasaAval       = findViewById(R.id.etTelCasaAval);
        etCelularAval       = findViewById(R.id.etCelularAval);
        etTelMensAval       = findViewById(R.id.etTelMensAval);
        etTelTrabajoAval    = findViewById(R.id.etTelTrabajoAval);
        etEmailAval         = findViewById(R.id.etEmailAval);
        tvFachadaAval       = findViewById(R.id.tvFachadaAval);
        ibFotoFachAval      = findViewById(R.id.ibFotoFachAval);
        ibFotoFachAval.setVisibility(View.GONE);
        ivFotoFachAval      = findViewById(R.id.ivFotoFachAval);
        etReferenciaAval    = findViewById(R.id.etReferenciaAval);
        tvFirmaAval         = findViewById(R.id.tvFirmaAval);
        tvFirmaAval.setVisibility(View.GONE);
        ibFirmaAval         = findViewById(R.id.ibFirmaAval);
        ibFirmaAval.setVisibility(View.GONE);
        ivFirmaAval         = findViewById(R.id.ivFirmaAval);
        //==========================================================================================
        //==================================  DATOS REFERENCIA  ====================================
        llComentRef         = findViewById(R.id.llComentRef);
        tvComentAdminRef    = findViewById(R.id.tvComentAdminRef);
        etNombreRef         = findViewById(R.id.etNombreRef);
        etApPaternoRef      = findViewById(R.id.etApPaternoRef);
        etApMaternoRef      = findViewById(R.id.etApMaternoRef);
        tvFechaNacRef       = findViewById(R.id.tvFechaNacRef);
        etCalleRef           = findViewById(R.id.etCalleRef);
        etNoExtRef           = findViewById(R.id.etNoExtRef);
        etManzanaRef         = findViewById(R.id.etManzanaRef);
        etNoIntRef           = findViewById(R.id.etNoIntRef);
        etLoteRef            = findViewById(R.id.etLoteRef);
        etCpRef              = findViewById(R.id.etCpRef);
        tvColoniaRef         = findViewById(R.id.tvColoniaRef);
        etCiudadRef          = findViewById(R.id.etCiudadRef);
        tvLocalidadRef       = findViewById(R.id.tvLocalidadRef);
        tvMunicipioRef       = findViewById(R.id.tvMunicipioRef);
        tvEstadoRef          = findViewById(R.id.tvEstadoRef);
        etTelCelRef         = findViewById(R.id.etTelCelRef);
        //==========================================================================================
        //====================================  CROQUIS   ==========================================
        llComentCro         = findViewById(R.id.llComentCro);
        tvComentAdminCro    = findViewById(R.id.tvComentAdminCro);
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
        //================ LINEAR LAYOUT  =========================
        llDatosCredito      = findViewById(R.id.llDatosCredito);
        llDatosPersonales   = findViewById(R.id.llDatosPersonales);
        llDatosConyuge      = findViewById(R.id.llDatosConyuge);
        llDatosEconomicos   = findViewById(R.id.llDatosEconomicos);
        llDatosNegocio      = findViewById(R.id.llDatosNegocio);
        llDatosAval         = findViewById(R.id.llDatosAval);
        llDatosReferencia   = findViewById(R.id.llDatosReferencia);
        llDatosCroquis      = findViewById(R.id.llDatosCroquis);
        llDatosPoliticas    = findViewById(R.id.llDatosPoliticas);
        llDatosDocumentos   = findViewById(R.id.llDatosDocumentos);

        llCredito           = findViewById(R.id.llCredito);
        llPersonales        = findViewById(R.id.llPersonales);
        llConyuge           = findViewById(R.id.llConyuge);
        llPropiedades       = findViewById(R.id.llPropiedades);
        llEconomicos        = findViewById(R.id.llEconomicos);
        llNegocio           = findViewById(R.id.llNegocio);
        llAval              = findViewById(R.id.llAval);
        llReferencia        = findViewById(R.id.llReferencia);
        llCroquis           = findViewById(R.id.llCroquis);
        llPoliticas         = findViewById(R.id.llPoliticas);
        //==========================================================================================

        //============================== LINEAR LAYOUT  ============================================
        llCredito.setOnClickListener(llCredito_OnClick);
        llPersonales.setOnClickListener(llPersonales_OnClick);
        llConyuge.setOnClickListener(llConyuge_OnClick);
        llEconomicos.setOnClickListener(llEconomicos_OnClick);
        llNegocio.setOnClickListener(llNegocio_OnClick);
        llAval.setOnClickListener(llAval_OnClick);
        llReferencia.setOnClickListener(llReferencia_OnClick);
        llCroquis.setOnClickListener(llCroquis_OnClick);
        llPoliticas.setOnClickListener(llPoliticas_OnClick);

        mapCli.onCreate(savedInstanceState);
        mapNeg.onCreate(savedInstanceState);
        mapAval.onCreate(savedInstanceState);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        //==========================================================================================

        id_solicitud = Long.parseLong(getIntent().getStringExtra("id_solicitud"));
        initComponents(getIntent().getStringExtra("id_solicitud"));

        //=================================  FLOATING BUTTON  ======================================
        btnContinuar0 = findViewById(R.id.btnContinuar0);
        btnContinuar1 = findViewById(R.id.btnContinuar1);
        btnContinuar2 = findViewById(R.id.btnContinuar2);
        btnContinuar3 = findViewById(R.id.btnContinuar3);
        btnContinuar4 = findViewById(R.id.btnContinuar4);
        btnContinuar5 = findViewById(R.id.btnContinuar5);
        btnContinuar6 = findViewById(R.id.btnContinuar6);
        btnContinuar7 = findViewById(R.id.btnContinuar7);
        btnContinuar8 = findViewById(R.id.btnContinuar8);
        btnContinuar8.hide();

        btnRegresar1 = findViewById(R.id.btnRegresar1);
        btnRegresar2 = findViewById(R.id.btnRegresar2);
        btnRegresar3 = findViewById(R.id.btnRegresar3);
        btnRegresar4 = findViewById(R.id.btnRegresar4);
        btnRegresar5 = findViewById(R.id.btnRegresar5);
        btnRegresar6 = findViewById(R.id.btnRegresar6);
        btnRegresar7 = findViewById(R.id.btnRegresar7);
        btnRegresar8 = findViewById(R.id.btnRegresar8);

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
        ivDown9 = findViewById(R.id.ivDown9);
        ivDown10 = findViewById(R.id.ivDown10);

        ivUp1 = findViewById(R.id.ivUp1);
        ivUp2 = findViewById(R.id.ivUp2);
        ivUp3 = findViewById(R.id.ivUp3);
        ivUp4 = findViewById(R.id.ivUp4);
        ivUp5 = findViewById(R.id.ivUp5);
        ivUp6 = findViewById(R.id.ivUp6);
        ivUp7 = findViewById(R.id.ivUp7);
        ivUp8 = findViewById(R.id.ivUp8);
        ivUp9 = findViewById(R.id.ivUp9);
        ivUp10 = findViewById(R.id.ivUp10);
        //==========================================================================================

        etMontoAutorizado.addTextChangedListener(new TextWatcher() {
            private final String PATTERN_MONTO_CREDITO  = "[1-9][0-9][0-9][0][0][0]|[1-9][0-9][0][0][0]|[1-9][0][0][0]";
            private Pattern pattern;
            private Matcher matcher;

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
                etMontoAutorizado.removeTextChangedListener(this);

                try {
                    int inilen, endlen;
                    inilen = etMontoAutorizado.getText().length();
                    String v = s.toString().replace(String.valueOf(df.getDecimalFormatSymbols().getGroupingSeparator()), "");
                    Number n = df.parse(v);
                    int cp = etMontoAutorizado.getSelectionStart();
                    if (hasFractionalPart) {
                        etMontoAutorizado.setText(df.format(n));
                    } else {
                        etMontoAutorizado.setText(dfnd.format(n));
                    }
                    endlen = etMontoAutorizado.getText().length();
                    int sel = (cp + (endlen - inilen));
                    if (sel > 0 && sel <= etMontoAutorizado.getText().length()) {
                        etMontoAutorizado.setSelection(sel);
                    } else {
                        // place cursor at the end?
                        etMontoAutorizado.setSelection(etMontoAutorizado.getText().length() - 1);
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
                        tvCantidadAutorizadaLetra.setText("");
                        etMontoAutorizado.setError("La cantidad no corresponde a un monto de crédito válido");
                    }else{
                        //Update("monto_prestamo", TBL_CREDITO_IND_REN, s.toString().trim().replace(",",""));
                        tvCantidadAutorizadaLetra.setText((Miscellaneous.cantidadLetra(s.toString().replace(",","")).toUpperCase() + " PESOS MEXICANOS ").replace("  ", " "));
                    }
                }
                else{
                    //Update("monto_prestamo", TBL_CREDITO_IND_REN, "");
                    tvCantidadAutorizadaLetra.setText("");
                }

                etMontoAutorizado.addTextChangedListener(this);
            }
        });

        //================================= FLOATING BUTTON LISTENER  ==============================
        btnContinuar0.setOnClickListener(btnContinuar0_OnClick);
        btnContinuar1.setOnClickListener(btnContinuar1_OnClick);
        btnContinuar2.setOnClickListener(btnContinuar2_OnClick);
        btnContinuar3.setOnClickListener(btnContinuar3_OnClick);
        btnContinuar4.setOnClickListener(btnContinuar4_OnClick);
        btnContinuar5.setOnClickListener(btnContinuar5_OnClick);
        btnContinuar6.setOnClickListener(btnContinuar6_OnClick);
        btnContinuar7.setOnClickListener(btnContinuar7_OnClick);

        btnRegresar1.setOnClickListener(btnRegresar1_OnClick);
        btnRegresar2.setOnClickListener(btnRegresar2_OnClick);
        btnRegresar3.setOnClickListener(btnRegresar3_OnClick);
        btnRegresar4.setOnClickListener(btnRegresar4_OnClick);
        btnRegresar5.setOnClickListener(btnRegresar5_OnClick);
        btnRegresar6.setOnClickListener(btnRegresar6_OnClick);
        btnRegresar7.setOnClickListener(btnRegresar7_OnClick);
        btnRegresar8.setOnClickListener(btnRegresar8_OnClick);

        dialog.dismiss();
    }

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

    private View.OnClickListener llCroquis_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (ivDown8.getVisibility() == View.VISIBLE && ivUp8.getVisibility() == View.GONE){
                ivDown8.setVisibility(View.GONE);
                ivUp8.setVisibility(View.VISIBLE);
                llDatosCroquis.setVisibility(View.VISIBLE);
            }
            else if (ivDown8.getVisibility() == View.GONE && ivUp8.getVisibility() == View.VISIBLE){
                ivDown8.setVisibility(View.VISIBLE);
                ivUp8.setVisibility(View.GONE);
                llDatosCroquis.setVisibility(View.GONE);

            }
        }
    };

    private View.OnClickListener llPoliticas_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (ivDown9.getVisibility() == View.VISIBLE && ivUp9.getVisibility() == View.GONE){
                ivDown9.setVisibility(View.GONE);
                ivUp9.setVisibility(View.VISIBLE);
                llDatosPoliticas.setVisibility(View.VISIBLE);
            }
            else if (ivDown9.getVisibility() == View.GONE && ivUp9.getVisibility() == View.VISIBLE){
                ivDown9.setVisibility(View.VISIBLE);
                ivUp9.setVisibility(View.GONE);
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
            if (tvEstadoCivilCli.getText().toString().trim().equals("CASADO(A)")||
                    tvEstadoCivilCli.getText().toString().trim().equals("UNIÓN LIBRE")) {
                etNombreCony.requestFocus();
                ivDown3.setVisibility(View.GONE);
                ivUp3.setVisibility(View.VISIBLE);
                llDatosConyuge.setVisibility(View.VISIBLE);
            }
            else{
                if (!etMontoPrestamo.getText().toString().trim().replace(",","").isEmpty() &&
                        Integer.parseInt(etMontoPrestamo.getText().toString().trim().replace(",","")) > 30000) {
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
            if (!etMontoPrestamo.getText().toString().trim().replace(",","").isEmpty() &&
                    Integer.parseInt(etMontoPrestamo.getText().toString().trim().replace(",","")) > 30000) {
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
            llDatosCroquis.setVisibility(View.VISIBLE);

            ivDown7.setVisibility(View.VISIBLE);
            ivUp7.setVisibility(View.GONE);
            llDatosReferencia.setVisibility(View.GONE);
        }
    };
    private View.OnClickListener btnContinuar7_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ivDown9.setVisibility(View.GONE);
            ivUp9.setVisibility(View.VISIBLE);
            llDatosPoliticas.setVisibility(View.VISIBLE);

            ivDown8.setVisibility(View.VISIBLE);
            ivUp8.setVisibility(View.GONE);
            llDatosCroquis.setVisibility(View.GONE);
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
            if (tvEstadoCivilCli.getText().toString().trim().equals("CASADO(A)")||
                    tvEstadoCivilCli.getText().toString().trim().equals("UNIÓN LIBRE")) {
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
            if (!etMontoPrestamo.getText().toString().trim().replace(",","").isEmpty() &&
                    Integer.parseInt(etMontoPrestamo.getText().toString().trim().replace(",","")) > 30000) {
                ivDown4.setVisibility(View.GONE);
                ivUp4.setVisibility(View.VISIBLE);
                llDatosEconomicos.setVisibility(View.VISIBLE);
                etPropiedadesEco.requestFocus();
            }
            else{
                if (tvEstadoCivilCli.getText().toString().trim().equals("CASADO(A)")||
                        tvEstadoCivilCli.getText().toString().trim().equals("UNIÓN LIBRE")) {
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
            llDatosCroquis.setVisibility(View.GONE);
        }
    };

    private View.OnClickListener btnRegresar8_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ivDown8.setVisibility(View.GONE);
            ivUp8.setVisibility(View.VISIBLE);
            llDatosCroquis.setVisibility(View.VISIBLE);

            ivDown9.setVisibility(View.VISIBLE);
            ivUp9.setVisibility(View.GONE);
            llDatosPoliticas.setVisibility(View.GONE);
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_enviar_datos, menu);
        if (!isEditCre)
        {
            for (int i = 0; i < menu.size(); i++)
                menu.getItem(i).setVisible(isEditCre);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:

                break;
            case R.id.enviar:
                final AlertDialog loading = Popups.showLoadingDialog(ctx, R.string.please_wait, R.string.loading_info);
                loading.show();


                if (!validator.validate(etMontoAutorizado, new String[]{validator.REQUIRED, validator.MONEY, validator.CREDITO})){

                    ContentValues cv = new ContentValues();
                    cv.put("estatus_completado", 1);
                    cv.put("monto_autorizado", etMontoAutorizado.getText().toString().trim().replace(",",""));
                    db.update(TBL_CREDITO_IND_AUTO, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});

                    cv = new ContentValues();
                    cv.put("estatus", 1);
                    db.update(TBL_SOLICITUDES_AUTO, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});

                    Servicios_Sincronizado ss = new Servicios_Sincronizado();
                    ss.MontoAutorizado(ctx, false);
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
                break;
        }
        return super.onOptionsItemSelected(item);
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

    private void initComponents (String idSolicitud) {

        Cursor row = dBhelper.getRecords(TBL_SOLICITUDES_AUTO, " WHERE id_solicitud = ?", "", new String[]{idSolicitud});
        row.moveToFirst();
        tvTipoSolicitud.setText(row.getString(3));

        Log.e("Rows", row.getColumnCount() + "count");

        row.close();

        //id_cliente = row.getString(11);

        //Update("id_originacion", TBL_SOLICITUDES_REN, "22");

        row = dBhelper.getRecords(TBL_CREDITO_IND_AUTO, " WHERE id_solicitud = ?", "", new String[]{idSolicitud});
        row.moveToFirst();
        Log.e("XXX", "Count"+row.getCount());
        isEditCre = row.getInt(15) == 0;

        if (!isEditCre)
            invalidateOptionsMenu();

        //Llenado del datos del prestamo
        tvPlazo.setText(row.getString(2).toUpperCase());
        tvFrecuencia.setText(row.getString(3).toUpperCase());
        tvFechaDesembolso.setText(row.getString(4));
        tvDiaDesembolso.setText(Miscellaneous.DiaSemana(row.getString(4)));
        tvHoraVisita.setText(row.getString(6));
        if (!row.getString(7).trim().isEmpty())
            etMontoPrestamo.setText(dfnd.format(row.getInt(7)));
        etMontoPrestamo.setEnabled(false);
        if (!row.getString(7).trim().isEmpty()) {
            tvCantidadLetra.setText((Miscellaneous.cantidadLetra(row.getString(7)).toUpperCase() + " PESOS MEXICANOS").replace("  ", " "));
            if (row.getInt(7) >= 30000)
                llPropiedades.setVisibility(View.VISIBLE);
        }
        tvDestino.setText(row.getString(13));
        tvRiesgo.setText(row.getString(14));
        if (!row.getString(16).trim().isEmpty()) {
            etMontoAutorizado.setText(dfnd.format(row.getInt(16)));
            tvCantidadAutorizadaLetra.setText((Miscellaneous.cantidadLetra(row.getString(16)).toUpperCase() + " PESOS MEXICANOS").replace("  ", " "));
        }
        etMontoPrestamo.setEnabled(isEditCre);

        tvCreditoAnterior.setText(dfnd.format(row.getInt(9)));
        tvNumCiclo.setText(row.getString(8));
        tvComportamiento.setText(row.getString(10));
        etObservaciones.setText(row.getString(12));
        etObservaciones.setEnabled(false);

        row.close(); //Cierra dato del credito

        //Llenado de datos del cliente
        row = dBhelper.getRecords(TBL_CLIENTE_IND_AUTO, " WHERE id_solicitud = ?", "", new String[]{idSolicitud});
        row.moveToFirst();
        if (!row.getString(36).trim().isEmpty()){
            llComentCli.setVisibility(View.VISIBLE);
            tvComentAdminCli.setText(row.getString(36).trim().toUpperCase());
        }

        etNombreCli.setText(row.getString(2));
        etNombreCli.setEnabled(false);
        etApPaternoCli.setText(row.getString(3));
        etApPaternoCli.setEnabled(false);
        etApMaternoCli.setText(row.getString(4));
        etApMaternoCli.setEnabled(false);
        tvFechaNacCli.setText(row.getString(5));
        tvFechaNacCli.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        tvEdadCli.setText(row.getString(6));
        Log.e("Sexo", row.getString(7));
        switch (row.getInt(7)) {
            case 0:
                rgGeneroCli.check(R.id.rbHombre);
                break;
            case 1:
                rgGeneroCli.check(R.id.rbMujer);
                break;
        }
        for (int i = 0; i < rgGeneroCli.getChildCount(); i++) {
            ((RadioButton) rgGeneroCli.getChildAt(i)).setEnabled(false);
        }

        tvEstadoNacCli.setText(row.getString(8));
        tvEstadoNacCli.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        tvRfcCli.setText(row.getString(9));
        etCurpCli.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        etCurpCli.setText(row.getString(10));
        etCurpCli.setEnabled(false);
        //etCurpIdCli.setText(row.getString(11)); etCurpIdCli.setEnabled(false);
        tvOcupacionCli.setText(row.getString(12));
        tvActividadEcoCli.setText(row.getString(13));
        tvTipoIdentificacion.setText(row.getString(14));
        etNumIdentifCli.setText(row.getString(15));
        tvEstudiosCli.setText(row.getString(16));
        tvEstadoCivilCli.setText(row.getString(17));
        switch (row.getString(17)) {
            case "CASADO(A)":
                llConyuge.setVisibility(View.VISIBLE);
                llBienes.setVisibility(View.VISIBLE);
                switch (row.getInt(18)) {
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

        tvTipoCasaCli.setText(row.getString(19));
        switch (row.getString(19)) {
            case "CASA FAMILIAR":
                llCasaFamiliar.setVisibility(View.VISIBLE);
                tvCasaFamiliar.setText(row.getString(20));
                break;
            case "OTRO":
                llCasaOtroCli.setVisibility(View.VISIBLE);
                etOTroTipoCli.setText(row.getString(21));
                break;
        }
        Cursor rowDir = dBhelper.getRecords(TBL_DIRECCIONES_AUTO, " WHERE id_direccion = ? AND tipo_direccion = ?", "", new String[]{row.getString(22), "CLIENTE"});
        rowDir.moveToFirst();

        if (!rowDir.getString(2).isEmpty() && !rowDir.getString(3).isEmpty()) {
            mapCli.setVisibility(View.VISIBLE);
            Ubicacion(rowDir.getDouble(2), rowDir.getDouble(3));
        }
        else{
            tvMapaCli.setVisibility(View.GONE);
            ibMapCli.setVisibility(View.GONE);
        }
        etCalleCli.setText(rowDir.getString(4));
        etNoExtCli.setText(rowDir.getString(5));
        etNoIntCli.setText(rowDir.getString(6));
        etLoteCli.setText(rowDir.getString(7));
        etManzanaCli.setText(rowDir.getString(8));
        etCpCli.setText(rowDir.getString(9));
        tvColoniaCli.setText(rowDir.getString(10));
        etCiudadCli.setText(rowDir.getString(11));
        tvLocalidadCli.setText(rowDir.getString(12));
        tvMunicipioCli.setText(rowDir.getString(13));
        tvEstadoCli.setText(rowDir.getString(14));
        rowDir.close(); //Cierra cursor de direccion del cliente
        etTelCasaCli.setText(row.getString(23));
        etCelularCli.setText(row.getString(24));
        etTelMensCli.setText(row.getString(25));
        etTelTrabajoCli.setText(row.getString(26));
        etTiempoSitio.setText((row.getInt(27) > 0) ? row.getString(27) : "");
        tvDependientes.setText(row.getString(28));
        tvEnteroNosotros.setText(row.getString(29));
        tvEstadoCuenta.setText(row.getString(30));
        etEmail.setText(row.getString(31));

        etReferenciCli.setText(row.getString(33));

        row.close(); // Cierra datos del cliente

        if (tvEstadoCivilCli.getText().toString().equals("CASADO(A)") || tvEstadoCivilCli.getText().toString().equals("UNIÓN LIBRE")) {
            // Llenado de datos del conyuge
            row = dBhelper.getRecords(TBL_CONYUGE_IND_AUTO, " WHERE id_solicitud = ?", "", new String[]{idSolicitud});
            row.moveToFirst();

            etNombreCony.setText(row.getString(2));
            etApPaternoCony.setText(row.getString(3));
            etApMaternoCony.setText(row.getString(4));
            etNacionalidadCony.setText(row.getString(5));
            tvOcupacionCony.setText(row.getString(6));
            rowDir = dBhelper.getRecords(TBL_DIRECCIONES_AUTO, " WHERE id_direccion = ? AND tipo_direccion = ?", "", new String[]{row.getString(7), "CONYUGE"});
            rowDir.moveToFirst();

            etCalleCony.setText(rowDir.getString(4));
            etNoExtCony.setText(rowDir.getString(5));
            etNoIntCony.setText(rowDir.getString(6));
            etLoteCony.setText(rowDir.getString(7));
            etManzanaCony.setText(rowDir.getString(8));
            etCpCony.setText(rowDir.getString(9));
            tvColoniaCony.setText(rowDir.getString(10));
            etCiudadCony.setText(rowDir.getString(11));
            tvLocalidadCony.setText(rowDir.getString(12));
            tvMunicipioCony.setText(rowDir.getString(13));
            tvEstadoCony.setText(rowDir.getString(14));
            rowDir.close(); //Cierra datos de direccion del conyuge
            if (!row.getString(8).trim().isEmpty())
                etIngMenCony.setText(dfnd.format(row.getInt(8)));
            if (!row.getString(9).trim().isEmpty())
                etGastoMenCony.setText(dfnd.format(row.getInt(9)));
            etCasaCony.setText(row.getString(10));
            etCelularCony.setText(row.getString(11));
            row.close(); //Cierra datos del conyuge
        }

        if (Integer.parseInt(etMontoPrestamo.getText().toString().replace(",","").replace("$","")) > 29000) {
            //Llenado de datos economicos
            row = dBhelper.getRecords(TBL_ECONOMICOS_IND_AUTO, " WHERE id_solicitud = ?", "", new String[]{idSolicitud});
            row.moveToFirst();
            etPropiedadesEco.setText(row.getString(2));
            etValorAproxEco.setText(row.getString(3));
            etUbicacionEco.setText(row.getString(4));
            tvIngresoEco.setText(row.getString(5));
            row.close(); // Cierra datos economicos
        }

        //Llenado de datos del negocio
        row = dBhelper.getRecords(TBL_NEGOCIO_IND_AUTO, " WHERE id_solicitud = ?", "", new String[]{idSolicitud});
        row.moveToFirst();
        if (!row.getString(27).trim().isEmpty()){
            llComentNeg.setVisibility(View.VISIBLE);
            tvComentAdminNeg.setText(row.getString(27).trim().toUpperCase());
        }

        etNombreNeg.setText(row.getString(2));

        rowDir = dBhelper.getRecords(TBL_DIRECCIONES_AUTO, " WHERE id_direccion = ? AND tipo_direccion = ?", "", new String[]{row.getString(3), "NEGOCIO"});
        rowDir.moveToFirst();

        if (!rowDir.getString(2).isEmpty() && !rowDir.getString(3).isEmpty()) {
            mapNeg.setVisibility(View.VISIBLE);
            UbicacionNeg(rowDir.getDouble(2), rowDir.getDouble(3));
        }
        else{
            tvMapaNeg.setVisibility(View.GONE);
            ibMapNeg.setVisibility(View.GONE);
        }
        etCalleNeg.setText(rowDir.getString(4));
        etNoExtNeg.setText(rowDir.getString(5));
        etNoIntNeg.setText(rowDir.getString(6));
        etLoteNeg.setText(rowDir.getString(7));
        etManzanaNeg.setText(rowDir.getString(8));
        etCpNeg.setText(rowDir.getString(9));
        tvColoniaNeg.setText(rowDir.getString(10));
        etCiudadNeg.setText(rowDir.getString(11));
        tvLocalidadNeg.setText(rowDir.getString(12));
        tvMunicipioNeg.setText(rowDir.getString(13));
        rowDir.close(); //Cierra datos de direccion del negocio
        tvActEcoEspNeg.setText(row.getString(4));
        tvActEconomicaNeg.setText(row.getString(5));
        tvDestinoNeg.setText(row.getString(6));
        if (row.getString(6).equals("OTRO")) {
            etOtroDestinoNeg.setText(row.getString(7));
            etOtroDestinoNeg.setVisibility(View.VISIBLE);
        }

        if (row.getInt(8) > 0)
            etAntiguedadNeg.setText(row.getString(8));

        if (!row.getString(9).trim().isEmpty())
            etIngMenNeg.setText(dfnd.format(row.getInt(9)));
        if (!row.getString(10).trim().isEmpty())
            etOtrosIngNeg.setText(dfnd.format(row.getInt(10)));
        if (!row.getString(11).trim().isEmpty())
            etGastosMenNeg.setText(dfnd.format(row.getInt(11)));
        if (!row.getString(12).trim().isEmpty())
            etGastosAguaNeg.setText(dfnd.format(row.getInt(12)));
        if (!row.getString(13).trim().isEmpty())
            etGastosLuzNeg.setText(dfnd.format(row.getInt(13)));
        if (!row.getString(14).trim().isEmpty())
            etGastosTelNeg.setText(dfnd.format(row.getInt(14)));
        if (!row.getString(15).trim().isEmpty())
            etGastosRentaNeg.setText(dfnd.format(row.getInt(15)));
        if (!row.getString(16).trim().isEmpty())
            etGastosOtrosNeg.setText(dfnd.format(row.getInt(16)));
        if (!row.getString(17).trim().isEmpty())
            etCapacidadPagoNeg.setText(dfnd.format(row.getInt(17)));
        tvMediosPagoNeg.setText(row.getString(18));
        if (row.getString(18).contains("OTRO")) {
            etOtroMedioPagoNeg.setText(row.getString(19));
            etOtroMedioPagoNeg.setVisibility(View.VISIBLE);
        }

        if (tvMediosPagoNeg.getText().toString().trim().toUpperCase().contains("EFECTIVO"))
            llOperacionesEfectivo.setVisibility(View.VISIBLE);
        else
            llOperacionesEfectivo.setVisibility(View.GONE);

        if (!row.getString(20).trim().isEmpty())
            tvMontoMaxNeg.setText(dfnd.format(row.getDouble(20)));
        tvNumOperacionNeg.setText((row.getString(21).isEmpty()) ? "" : row.getString(21));
        etNumOperacionEfectNeg.setText((row.getString(22).isEmpty()) ? "" : row.getString(22));
        tvDiasVentaNeg.setText(row.getString(23));

        etReferenciNeg.setText(row.getString(25));

        row.close(); // Cierra los datos del negocio


        //Llenado de datos del aval
        row = dBhelper.getRecords(TBL_AVAL_IND_AUTO, " WHERE id_solicitud = ?", "", new String[]{idSolicitud});
        row.moveToFirst();
        if (!row.getString(50).trim().isEmpty()){
            llComentAval.setVisibility(View.VISIBLE);
            tvComentAdminAval.setText(row.getString(50).trim().toUpperCase());
        }

        etNombreAval.setText(row.getString(2));
        etApPaternoAval.setText(row.getString(3));
        etApMaternoAval.setText(row.getString(4));
        tvFechaNacAval.setText(row.getString(5));
        tvEdadAval.setText(row.getString(6));
        switch (row.getInt(7)) {
            case 0:
                rgGeneroAval.check(R.id.rbHombre);
                break;
            case 1:
                rgGeneroAval.check(R.id.rbMujer);
                break;
        }
        tvEstadoNacAval.setText(row.getString(8));
        tvRfcAval.setText(row.getString(9));
        etCurpAval.setText(row.getString(10));
        //etCurpIdAval.setText(row.getString(11)); etCurpIdAval.setEnabled(is_edit);
        tvParentescoAval.setText(row.getString(12));
        tvTipoIdentificacionAval.setText(row.getString(13));
        etNumIdentifAval.setText(row.getString(14));
        tvOcupacionAval.setText(row.getString(15));
        tvActividadEcoAval.setText(row.getString(16));

        rowDir = dBhelper.getRecords(TBL_DIRECCIONES_AUTO, " WHERE id_direccion = ? AND tipo_direccion = ?", "", new String[]{row.getString(19), "AVAL"});
        rowDir.moveToFirst();

        if (!rowDir.getString(2).isEmpty() && !rowDir.getString(3).isEmpty()) {
            mapAval.setVisibility(View.VISIBLE);
            UbicacionAval(rowDir.getDouble(2), rowDir.getDouble(3));
        }
        else{
            tvMapaAval.setVisibility(View.GONE);
            ibMapAval.setVisibility(View.GONE);
        }
        etCalleAval.setText(rowDir.getString(4));
        etNoExtAval.setText(rowDir.getString(5));
        etNoIntAval.setText(rowDir.getString(6));
        etLoteAval.setText(rowDir.getString(7));
        etManzanaAval.setText(rowDir.getString(8));
        etCpAval.setText(rowDir.getString(9));
        tvColoniaAval.setText(rowDir.getString(10));
        etCiudadAval.setText(rowDir.getString(11));
        tvLocalidadAval.setText(rowDir.getString(12));
        tvMunicipioAval.setText(rowDir.getString(13));
        tvEstadoAval.setText(rowDir.getString(14));
        rowDir.close(); // Cierra los datos de la direccion del aval

        tvTipoCasaAval.setText(row.getString(20));

        if (row.getString(20).trim().toUpperCase().equals("CASA FAMILIAR") || row.getString(20).trim().toUpperCase().equals("CASA RENTADA")) {
            llParentescoFamAval.setVisibility(View.VISIBLE);
            llNombreTitular.setVisibility(View.VISIBLE);
            etNombreTitularAval.setText(row.getString(21));
            tvFamiliarAval.setText(row.getString(22));
        }
        etCaracteristicasAval.setText(row.getString(23));

        switch (row.getInt(25)) {
            case 1:
                rgNegocioAval.check(R.id.rbSiNeg);
                llNombreNegocio.setVisibility(View.VISIBLE);
                etNombreNegocioAval.setText(row.getString(26));
                if (row.getInt(24) > 0)
                    etAntiguedadAval.setText(row.getString(24));
                break;
            case 2:
                rgNegocioAval.check(R.id.rbNoNeg);
                break;
        }

        if (!row.getString(27).trim().isEmpty())
            etIngMenAval.setText(dfnd.format(row.getInt(27)));
        if (!row.getString(28).trim().isEmpty())
            etIngOtrosAval.setText(dfnd.format(row.getInt(28)));
        if (!row.getString(29).trim().isEmpty())
            etGastosSemAval.setText(dfnd.format(row.getInt(29)));
        if (!row.getString(30).trim().isEmpty())
            etGastosAguaAval.setText(dfnd.format(row.getInt(30)));
        if (!row.getString(31).trim().isEmpty())
            etGastosLuzAval.setText(dfnd.format(row.getInt(31)));
        if (!row.getString(32).trim().isEmpty())
            etGastosTelAval.setText(dfnd.format(row.getInt(32)));
        if (!row.getString(33).trim().isEmpty())
            etGastosRentaAval.setText(dfnd.format(row.getInt(33)));
        if (!row.getString(34).trim().isEmpty())
            etGastosOtrosAval.setText(dfnd.format(row.getInt(34)));
        if (!row.getString(38).trim().isEmpty())
            tvMontoMaxAval.setText(dfnd.format(row.getInt(38)));

        if (!row.getString(35).trim().isEmpty())
            etCapacidadPagoAval.setText(dfnd.format(row.getInt(35)));
        tvMediosPagoAval.setText(row.getString(36));
        if (row.getString(36).contains("OTRO")) {
            etOtroMedioPagoAval.setText(row.getString(37));
            etOtroMedioPagoAval.setVisibility(View.VISIBLE);
        }

        tvHoraLocAval.setText(row.getString(39));
        tvActivosObservables.setText(row.getString(40));
        etTelCasaAval.setText(row.getString(41));
        etCelularAval.setText(row.getString(42));
        etTelMensAval.setText(row.getString(43));
        etTelTrabajoAval.setText(row.getString(44));
        etEmailAval.setText(row.getString(45));

        etReferenciaAval.setText(row.getString(47));

        row.close(); //Cierra datos del aval


        //Llena datos de referencia
        row = dBhelper.getRecords(TBL_REFERENCIA_IND_AUTO, " WHERE id_solicitud = ?", "", new String[]{idSolicitud});
        row.moveToFirst();
        if (!row.getString(9).trim().isEmpty()){
            llComentRef.setVisibility(View.VISIBLE);
            tvComentAdminRef.setText(row.getString(9).trim().toUpperCase());
        }
        etNombreRef.setText(row.getString(2));
        etApPaternoRef.setText(row.getString(3));
        etApMaternoRef.setText(row.getString(4));
        tvFechaNacRef.setText(row.getString(5));

        rowDir = dBhelper.getRecords(TBL_DIRECCIONES_AUTO, " WHERE id_direccion = ? AND tipo_direccion = ?", "", new String[]{row.getString(6), "REFERENCIA"});
        rowDir.moveToFirst();

        etCalleRef.setText(rowDir.getString(4));
        etNoExtRef.setText(rowDir.getString(5));
        etNoIntRef.setText(rowDir.getString(6));
        etLoteRef.setText(rowDir.getString(7));
        etManzanaRef.setText(rowDir.getString(8));
        etCpRef.setText(rowDir.getString(9));
        tvColoniaRef.setText(rowDir.getString(10));
        etCiudadRef.setText(rowDir.getString(11));
        tvLocalidadRef.setText(rowDir.getString(12));
        tvMunicipioRef.setText(rowDir.getString(13));
        tvEstadoRef.setText(rowDir.getString(14));
        rowDir.close(); //Cierra datos de direccion del referencia
        etTelCelRef.setText(row.getString(7));
        row.close(); //Cierra datos de referencia

        row = dBhelper.getRecords(TBL_CROQUIS_IND_AUTO, " WHERE id_solicitud = ?", "", new String[]{idSolicitud});
        row.moveToFirst();
        if (!row.getString(8).trim().isEmpty()){
            llComentCro.setVisibility(View.VISIBLE);
            tvComentAdminCro.setText(row.getString(8).trim().toUpperCase());
        }
        tvPrincipal.setText(row.getString(2).trim().toUpperCase());
        tvLateraUno.setText(row.getString(3).trim().toUpperCase());
        tvLateraDos.setText(row.getString(4).trim().toUpperCase());
        tvTrasera.setText(row.getString(5).trim().toUpperCase());
        etReferencia.setText(row.getString(6));
        row.close(); //Cierra datos del croquis

        row = dBhelper.getRecords(TBL_POLITICAS_PLD_IND_AUTO, " WHERE id_solicitud = ?", "", new String[]{idSolicitud});
        row.moveToFirst();

        switch (row.getInt(2)) {
            case 1:
                rgPropietarioReal.check(R.id.rbSiPropietario);
                tvAnexoPropietario.setVisibility(View.VISIBLE);
                break;
            case 2:
                rgPropietarioReal.check(R.id.rbNoPropietario);
                break;
        }
        switch (row.getInt(3)) {
            case 1:
                rgProveedor.check(R.id.rbSiProveedor);
                tvAnexoPreveedor.setVisibility(View.VISIBLE);
                break;
            case 2:
                rgProveedor.check(R.id.rbNoProveedor);
                break;
        }
        switch (row.getInt(4)) {
            case 1:
                rgPoliticamenteExp.check(R.id.rbSiExpuesta);
                tvAnexoPoliticamenteExp.setVisibility(View.VISIBLE);
                break;
            case 2:
                rgPoliticamenteExp.check(R.id.rbNoexpuesta);
                break;
        }
        row.close(); //Cierra datos de politicas pld


        tvPlazo.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked_left));
        tvFrecuencia.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked_right));
        tvFechaDesembolso.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        tvHoraVisita.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        etMontoPrestamo.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        etMontoPrestamo.setEnabled(false);
        tvDestino.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        tvRiesgo.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        tvComportamiento.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        etObservaciones.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        etObservaciones.setEnabled(false);
        if (!isEditCre) {
            etMontoAutorizado.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etMontoAutorizado.setEnabled(false);
        }

        etNombreCli.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        etNombreCli.setEnabled(false);
        etApPaternoCli.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        etApPaternoCli.setEnabled(false);
        etApMaternoCli.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        etApMaternoCli.setEnabled(false);
        tvFechaNacCli.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        for (int i = 0; i < rgGeneroCli.getChildCount(); i++) {
            ((RadioButton) rgGeneroCli.getChildAt(i)).setEnabled(false);
        }
        tvEstadoNacCli.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        etCurpCli.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        etCurpCli.setEnabled(false);
        tvOcupacionCli.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        tvTipoIdentificacion.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        etNumIdentifCli.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        etNumIdentifCli.setEnabled(false);
        tvEstudiosCli.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        tvEstadoCivilCli.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        for (int i = 0; i < rgBienes.getChildCount(); i++) {
            ((RadioButton) rgBienes.getChildAt(i)).setEnabled(false);
        }
        tvTipoCasaCli.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        tvCasaFamiliar.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        etOTroTipoCli.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        etOTroTipoCli.setEnabled(false);
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
        etTelCasaCli.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        etTelCasaCli.setEnabled(false);
        etCelularCli.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        etCelularCli.setEnabled(false);
        etTelMensCli.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        etTelMensCli.setEnabled(false);
        etTelTrabajoCli.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        etTelTrabajoCli.setEnabled(false);
        etTiempoSitio.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        etTiempoSitio.setEnabled(false);
        tvDependientes.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        tvEnteroNosotros.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        tvEstadoCuenta.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        etEmail.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        etEmail.setEnabled(false);
        etReferenciCli.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        etReferenciCli.setEnabled(false);

        etNombreCony.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        etNombreCony.setEnabled(false);
        etApPaternoCony.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        etApPaternoCony.setEnabled(false);
        etApMaternoCony.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        etApMaternoCony.setEnabled(false);
        etNacionalidadCony.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        etNacionalidadCony.setEnabled(false);
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
        etIngMenCony.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        etIngMenCony.setEnabled(false);
        etGastoMenCony.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        etGastoMenCony.setEnabled(false);
        etCasaCony.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        etCasaCony.setEnabled(false);
        etCelularCony.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        etCelularCony.setEnabled(false);

        etPropiedadesEco.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        etPropiedadesEco.setEnabled(false);
        etValorAproxEco.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        etValorAproxEco.setEnabled(false);
        etUbicacionEco.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        etUbicacionEco.setEnabled(false);
        tvIngresoEco.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));

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
        tvActEcoEspNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        tvActEconomicaNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        tvDestinoNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        etOtroDestinoNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        etOtroDestinoNeg.setEnabled(false);
        etAntiguedadNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        etAntiguedadNeg.setEnabled(false);
        etIngMenNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        etIngMenNeg.setEnabled(false);
        etOtrosIngNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        etOtrosIngNeg.setEnabled(false);
        etGastosMenNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        etGastosMenNeg.setEnabled(false);
        etGastosAguaNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        etGastosAguaNeg.setEnabled(false);
        etGastosLuzNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        etGastosLuzNeg.setEnabled(false);
        etGastosTelNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        etGastosTelNeg.setEnabled(false);
        etGastosRentaNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        etGastosRentaNeg.setEnabled(false);
        etGastosOtrosNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        etGastosOtrosNeg.setEnabled(false);
        tvMediosPagoNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        etOtroMedioPagoNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        etOtroMedioPagoNeg.setEnabled(false);
        tvMontoMaxNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        etCapacidadPagoNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        etCapacidadPagoNeg.setEnabled(false);
        tvNumOperacionNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        etNumOperacionEfectNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        etNumOperacionEfectNeg.setEnabled(false);
        tvDiasVentaNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        etReferenciNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        etReferenciNeg.setEnabled(false);

        etNombreAval.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        etNombreAval.setEnabled(false);
        etApPaternoAval.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        etApPaternoAval.setEnabled(false);
        etApMaternoAval.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        etApMaternoAval.setEnabled(false);
        tvFechaNacAval.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        for (int i = 0; i < rgGeneroAval.getChildCount(); i++) {
            ((RadioButton) rgGeneroAval.getChildAt(i)).setEnabled(false);
        }
        tvEstadoNacAval.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        etCurpAval.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        etCurpAval.setEnabled(false);
        tvParentescoAval.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        tvTipoIdentificacionAval.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        etNumIdentifAval.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        etNumIdentifAval.setEnabled(false);
        tvOcupacionAval.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        etCalleAval.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        etCalleAval.setEnabled(false);
        etNoExtAval.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        etNoExtAval.setEnabled(false);
        etNoIntAval.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        etNoIntAval.setEnabled(false);
        etManzanaAval.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        etManzanaAval.setEnabled(false);
        etLoteAval.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        etLoteAval.setEnabled(false);
        etCpAval.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        etCpAval.setEnabled(false);
        tvColoniaAval.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        etCiudadAval.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        etCiudadAval.setEnabled(false);
        tvLocalidadAval.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        tvTipoCasaAval.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        tvFamiliarAval.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        etNombreTitularAval.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        etNombreTitularAval.setEnabled(false);
        etCaracteristicasAval.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        etCaracteristicasAval.setEnabled(false);
        for (int i = 0; i < rgNegocioAval.getChildCount(); i++) {
            ((RadioButton) rgNegocioAval.getChildAt(i)).setEnabled(false);
        }
        etNombreNegocioAval.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        etNombreNegocioAval.setEnabled(false);
        etIngMenAval.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        etIngMenAval.setEnabled(false);
        etIngOtrosAval.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        etIngOtrosAval.setEnabled(false);
        etGastosSemAval.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        etGastosSemAval.setEnabled(false);
        etGastosAguaAval.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        etGastosAguaAval.setEnabled(false);
        etGastosLuzAval.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        etGastosLuzAval.setEnabled(false);
        etGastosTelAval.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        etGastosTelAval.setEnabled(false);
        etGastosRentaAval.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        etGastosRentaAval.setEnabled(false);
        etGastosOtrosAval.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        etGastosOtrosAval.setEnabled(false);
        tvMediosPagoAval.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        etOtroMedioPagoAval.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        etOtroMedioPagoAval.setEnabled(false);
        etCapacidadPagoAval.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        etCapacidadPagoAval.setEnabled(false);
        tvHoraLocAval.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        tvActivosObservables.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        etAntiguedadAval.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        etAntiguedadAval.setEnabled(false);
        etTelCasaAval.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        etTelCasaAval.setEnabled(false);
        etCelularAval.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        etCelularAval.setEnabled(false);
        etTelMensAval.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        etTelMensAval.setEnabled(false);
        etTelTrabajoAval.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        etTelTrabajoAval.setEnabled(false);
        etEmailAval.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        etEmailAval.setEnabled(false);
        etReferenciaAval.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        etReferenciaAval.setEnabled(false);

        etNombreRef.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        etNombreRef.setEnabled(false);
        etApPaternoRef.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        etApPaternoRef.setEnabled(false);
        etApMaternoRef.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        etApMaternoRef.setEnabled(false);
        tvFechaNacRef.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        etCalleRef.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        etCalleRef.setEnabled(false);
        etNoExtRef.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        etNombreRef.setEnabled(false);
        etNoIntRef.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        etNoIntRef.setEnabled(false);
        etLoteRef.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        etLoteRef.setEnabled(false);
        etManzanaRef.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        etManzanaRef.setEnabled(false);
        etCpRef.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        etCpRef.setEnabled(false);
        tvColoniaRef.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        etCiudadRef.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        etCiudadRef.setEnabled(false);
        tvLocalidadRef.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        etTelCelRef.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        etTelCelRef.setEnabled(false);

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

    }
}
