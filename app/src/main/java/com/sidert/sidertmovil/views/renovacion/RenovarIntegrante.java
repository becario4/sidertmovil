package com.sidert.sidertmovil.views.renovacion;

import android.Manifest;
import android.annotation.SuppressLint;
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
import androidx.annotation.Nullable;
/*import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;*/
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AlertDialog;

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
import android.widget.CompoundButton;
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
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.activities.CameraVertical;
import com.sidert.sidertmovil.activities.CapturarFirma;
import com.sidert.sidertmovil.activities.Catalogos;
import com.sidert.sidertmovil.activities.VerImagen;
import com.sidert.sidertmovil.database.DBhelper;
import com.sidert.sidertmovil.fragments.dialogs.dialog_date_picker;
import com.sidert.sidertmovil.fragments.dialogs.dialog_input_calle;
import com.sidert.sidertmovil.fragments.dialogs.dialog_renovar_integrante;
import com.sidert.sidertmovil.models.ModeloCatalogoGral;
import com.sidert.sidertmovil.models.catalogos.Colonia;
import com.sidert.sidertmovil.models.catalogos.ColoniaDao;
import com.sidert.sidertmovil.models.solicitudes.solicitudgpo.DomicilioIntegranteRen;
import com.sidert.sidertmovil.models.solicitudes.solicitudgpo.DomicilioIntegranteRenDao;
import com.sidert.sidertmovil.models.solicitudes.solicitudgpo.NegocioIntegranteRen;
import com.sidert.sidertmovil.models.solicitudes.solicitudgpo.NegocioIntegranteRenDao;
import com.sidert.sidertmovil.utils.Constants;
import com.sidert.sidertmovil.utils.Miscellaneous;
import com.sidert.sidertmovil.utils.MyCurrentListener;
import com.sidert.sidertmovil.utils.NameFragments;
import com.sidert.sidertmovil.utils.NetworkStatus;
import com.sidert.sidertmovil.utils.Popups;
import com.sidert.sidertmovil.utils.Validator;
import com.sidert.sidertmovil.utils.ValidatorTextView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.card.payment.CardIOActivity;

import static com.sidert.sidertmovil.database.SidertTables.SidertEntry.TABLE_MUNICIPIOS;
import static com.sidert.sidertmovil.utils.Constants.CALLE;
import static com.sidert.sidertmovil.utils.Constants.CATALOGO;
import static com.sidert.sidertmovil.utils.Constants.COLONIAS;
import static com.sidert.sidertmovil.utils.Constants.EXTRA;
import static com.sidert.sidertmovil.utils.Constants.IMAGEN;
import static com.sidert.sidertmovil.utils.Constants.ITEM;
import static com.sidert.sidertmovil.utils.Constants.LOCALIDADES;
import static com.sidert.sidertmovil.utils.Constants.OCUPACIONES;
import static com.sidert.sidertmovil.utils.Constants.ORDER_ID;
import static com.sidert.sidertmovil.utils.Constants.PICTURE;
import static com.sidert.sidertmovil.utils.Constants.REQUEST_CODE;
import static com.sidert.sidertmovil.utils.Constants.REQUEST_CODE_COLONIA_CONY;
import static com.sidert.sidertmovil.utils.Constants.REQUEST_CODE_FIRMA_CLI;
import static com.sidert.sidertmovil.utils.Constants.REQUEST_CODE_FOTO_COMPROBATE;
import static com.sidert.sidertmovil.utils.Constants.REQUEST_CODE_FOTO_CURP;
import static com.sidert.sidertmovil.utils.Constants.REQUEST_CODE_FOTO_INE_FRONTAL;
import static com.sidert.sidertmovil.utils.Constants.REQUEST_CODE_FOTO_INE_REVERSO;
import static com.sidert.sidertmovil.utils.Constants.REQUEST_CODE_FOTO_INE_SELFIE;
import static com.sidert.sidertmovil.utils.Constants.REQUEST_CODE_LOCALIDAD_CLIE;
import static com.sidert.sidertmovil.utils.Constants.REQUEST_CODE_LOCALIDAD_CONY;
import static com.sidert.sidertmovil.utils.Constants.REQUEST_CODE_LOCALIDAD_NEG;
import static com.sidert.sidertmovil.utils.Constants.REQUEST_CODE_OCUPACION_NEG;
import static com.sidert.sidertmovil.utils.Constants.ROOT_PATH;
import static com.sidert.sidertmovil.utils.Constants.SECTORES;
import static com.sidert.sidertmovil.utils.Constants.TBL_CONYUGE_INTEGRANTE_REN;
import static com.sidert.sidertmovil.utils.Constants.TBL_CREDITO_GPO_REN;
import static com.sidert.sidertmovil.utils.Constants.TBL_CROQUIS_GPO_REN;
import static com.sidert.sidertmovil.utils.Constants.TBL_DOCUMENTOS_INTEGRANTE_REN;
import static com.sidert.sidertmovil.utils.Constants.TBL_DOMICILIO_INTEGRANTE_REN;
import static com.sidert.sidertmovil.utils.Constants.TBL_INTEGRANTES_GPO_REN;
import static com.sidert.sidertmovil.utils.Constants.TBL_NEGOCIO_INTEGRANTE;
import static com.sidert.sidertmovil.utils.Constants.TBL_NEGOCIO_INTEGRANTE_REN;
import static com.sidert.sidertmovil.utils.Constants.TBL_OTROS_DATOS_INTEGRANTE;
import static com.sidert.sidertmovil.utils.Constants.TBL_OTROS_DATOS_INTEGRANTE_REN;
import static com.sidert.sidertmovil.utils.Constants.TBL_POLITICAS_PLD_INTEGRANTE_REN;
import static com.sidert.sidertmovil.utils.Constants.TBL_SOLICITUDES_REN;
import static com.sidert.sidertmovil.utils.Constants.TBL_TELEFONOS_INTEGRANTE_REN;
import static com.sidert.sidertmovil.utils.Constants.TIMESTAMP;
import static com.sidert.sidertmovil.utils.Constants.TIPO;
import static com.sidert.sidertmovil.utils.Constants.TIPO_SOLICITUD;
import static com.sidert.sidertmovil.utils.Constants.TITULO;
import static com.sidert.sidertmovil.utils.Constants.firma;
import static com.sidert.sidertmovil.utils.Constants.question;
import static com.sidert.sidertmovil.utils.Constants.warning;
import static io.card.payment.CardIOActivity.RESULT_SCAN_SUPPRESSED;

/**Clase del formulario de integrantes para renovacion grupal*/
public class RenovarIntegrante extends AppCompatActivity implements dialog_renovar_integrante.OnCompleteListener {

    private Context ctx;
    private DBhelper dBhelper;
    private SQLiteDatabase db;

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
    private Validator validator = new Validator();
    private ValidatorTextView validatorTV = new ValidatorTextView();

    private boolean is_edit = true;

    private String[] _estudios;
    private String[] _tipo_identificacion;
    private String[] _civil;
    private String[] _tipo_casa;
    private String[] _medio_contacto;
    private String[] _parentesco;
    private String[] _confirmacion;
    private String[] _dependientes;
    private String[] _destino_credito;
    private String[] _medios_pago;
    private String[] _riesgo;
    private String[] _max_pagos_mes;

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
    //private EditText etCurpIdCli;
    private TextView tvRfcCli;
    private TextView tvTipoIdentificacion;
    private EditText etNumIdentifCli;
    private TextView tvEstudiosCli;
    private TextView tvOcupacionCli;
    private TextView tvEstadoCivilCli;
    private LinearLayout llBienes;
    private TextView tvBienes;
    private RadioGroup rgBienes;
    private TextView tvCreditoAnterior;
    private TextView tvNumCiclo;
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
    private EditText etCiudadNeg;
    private TextView tvLocalidadNeg;
    private TextView tvMunicipioNeg;
    private TextView tvEstadoNeg;
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
    //private EditText etNumOperacionNeg;
    private TextView tvNumOperacionNeg;
    private LinearLayout llOperacionesEfectivo;
    private TextView etNumOperacionEfectNeg;
    private TextView tvFachadaNeg;
    private ImageButton ibFotoFachNeg;
    private ImageView ivFotoFachNeg;
    public byte[] byteFotoFachNeg;
    private MultiAutoCompleteTextView etReferenciNeg;
    private CheckBox cbNegEnDomCli;
    private RadioGroup rgTieneNegocio;
    private TextView tvNegocioIntegrante;
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
    private CheckBox cbCasaReuniones;
    private TextView tvFirmaCli;
    private ImageButton ibFirmaCli;
    private ImageView ivFirmaCli;
    public byte[] byteFirmaCli;
    private RadioGroup rgFirmaRuegoEncargo;
    private LinearLayout llNombreFirmaRuegoEncargo;
    private EditText etNombreFirmaRuegoEncargo;
    private EditText etMontoRefinanciar;
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
    private MultiAutoCompleteTextView etCaracteristicasDomicilio;
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
    public byte[] byteIneFrontal;
    private TextView tvIneReverso;
    private ImageButton ibIneReverso;
    private ImageView ivIneReverso;
    public byte[] byteIneReverso;
    private LinearLayout llCurp;
    private TextView tvCurp;
    private ImageButton ibCurp;
    private ImageView ivCurp;
    public byte[] byteCurp;
    private TextView tvComprobante;
    private ImageButton ibComprobante;
    private ImageView ivComprobante;
    public byte[] byteComprobante;
    private TextView tvIneSelfie;
    private ImageButton ibIneSelfie;
    private ImageView ivIneSelfie;
    public byte[] byteIneSelfie;
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
    //private LinearLayout llDatosTelefonicos;
    //private LinearLayout llDatosDomicilio;
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
    //private ImageView ivError7;
    private ImageView ivError8;
    private ImageView ivError9;
    //===================================================
    //===================  IMAGE VIEW  ========================================
    private ImageView ivDown1;
    //private ImageView ivDown2;
    //private ImageView ivDown3;
    private ImageView ivDown4;
    private ImageView ivDown5;
    private ImageView ivDown6;
    //private ImageView ivDown7;
    private ImageView ivDown8;
    private ImageView ivDown9;

    private ImageView ivUp1;
    private ImageView ivUp2;
    private ImageView ivUp3;
    private ImageView ivUp4;
    private ImageView ivUp5;
    private ImageView ivUp6;
    //private ImageView ivUp7;
    private ImageView ivUp8;
    private ImageView ivUp9;
    //=========================================================================

    private FloatingActionButton btnContinuar0;
    private FloatingActionButton btnContinuar1;
    private FloatingActionButton btnContinuar2;
    private FloatingActionButton btnContinuar3;
    private FloatingActionButton btnContinuar4;
    //private FloatingActionButton btnContinuar7;
    private FloatingActionButton btnContinuar8;
    private FloatingActionButton btnContinuar5;

    private FloatingActionButton btnRegresar0;
    //private FloatingActionButton btnRegresar1;
    //private FloatingActionButton btnRegresar2;
    private FloatingActionButton btnRegresar3;
    private FloatingActionButton btnRegresar4;
    //private FloatingActionButton btnRegresar5;
    //private FloatingActionButton btnRegresar7;
    private FloatingActionButton btnRegresar8;
    private FloatingActionButton btnRegresar6;

    private LocationManager locationManager;
    private MyCurrentListener locationListener;
    private GoogleMap mMapCli;
    private GoogleMap mMapNeg;

    private String id_credito = "";
    private String id_integrante = "";

    private ArrayList<Integer> selectedItemsMediosPago;

    DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
    DecimalFormat df = new DecimalFormat("##,###.##", symbols);
    DecimalFormat dfnd = new DecimalFormat("#,###", symbols);

    boolean hasFractionalPart = false;

    private boolean isNuevo = false;

    private Calendar myCalendar;

    boolean pushMapButtonCli = false;
    boolean pushMapButtonNeg = false;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_renovar_integrante);

        myCalendar = Calendar.getInstance();

        ctx = this;
        dBhelper = new DBhelper(ctx);
        db = dBhelper.getWritableDatabase();

        _estudios               = Miscellaneous.GetNivelesEstudio(ctx);
        _civil                  = Miscellaneous.GetEstadoCiviles(ctx);
        _tipo_identificacion    = Miscellaneous.GetIdentificacion(ctx);
        _medios_pago            = Miscellaneous.GetMediosPagoOri(ctx);
        _parentesco             = Miscellaneous.GetParentesco(ctx, "PARENTESCO");
        _tipo_casa              = Miscellaneous.GetViviendaTipos(ctx);
        _medio_contacto         = Miscellaneous.GetMediosContacto(ctx);
        _destino_credito        = Miscellaneous.GetDestinosCredito(ctx);
        _dependientes           = getResources().getStringArray(R.array.dependientes_eco);
        _riesgo                 = getResources().getStringArray(R.array.clasificacion_riesgo);
        _confirmacion           = getResources().getStringArray(R.array.confirmacion);
        _max_pagos_mes          = getResources().getStringArray(R.array.max_pagos_mes_gpo);

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
        tvCreditoAnterior   = findViewById(R.id.tvCreditoAnterior);
        tvNumCiclo          = findViewById(R.id.tvNumCiclo);
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
        ibFotoFachCli       = findViewById(R.id.ibFotoFachCli);
        ivFotoFachCli       = findViewById(R.id.ivFotoFachCli);
        etReferenciaCli     = findViewById(R.id.etReferenciaCli);
        rgFirmaRuegoEncargo = findViewById(R.id.rgFirmaRuegoEncargo);
        llNombreFirmaRuegoEncargo = findViewById(R.id.llNombreFirmaRuegoEncargo);
        etNombreFirmaRuegoEncargo = findViewById(R.id.etNombreFirmaRuegoEncargo);
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
        tvEstadoNeg             = findViewById(R.id.tvEstadoNeg);
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
        //etNumOperacionNeg       = findViewById(R.id.etNumOperacionNeg);
        tvNumOperacionNeg       = findViewById(R.id.tvNumOperacionNeg);
        llOperacionesEfectivo   = findViewById(R.id.llOperacionesEfectivo);
        etNumOperacionEfectNeg  = findViewById(R.id.etNumOperacionEfectNeg);
        tvFachadaNeg            = findViewById(R.id.tvFachadaNeg);
        ibFotoFachNeg           = findViewById(R.id.ibFotoFachNeg);
        ivFotoFachNeg           = findViewById(R.id.ivFotoFachNeg);
        etReferenciNeg          = findViewById(R.id.etReferenciaNeg);
        rgTieneNegocio          = findViewById(R.id.rgTieneNegocio);
        cbNegEnDomCli           = findViewById(R.id.cbNegEnDomCli);
        tvNegocioIntegrante     = findViewById(R.id.tvNegocioIntegrante);
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
        cbCasaReuniones     = findViewById(R.id.cbCasaReuniones);
        tvFirmaCli          = findViewById(R.id.tvFirmaCli);
        ibFirmaCli          = findViewById(R.id.ibFirmaCli);
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
        etCaracteristicasDomicilio = findViewById(R.id.etCaracteristicasDomicilio);
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
        etMontoRefinanciar  = findViewById(R.id.etMontoRefinanciar);
        tvIneSelfie         = findViewById(R.id.tvIneSelfie);
        ibIneSelfie         = findViewById(R.id.ibIneSelfie);
        ivIneSelfie         = findViewById(R.id.ivIneSelfie);
        //==========================================================================================
        //============================= IMAGE VIEW ERROR  ==========================================
        ivError1 = findViewById(R.id.ivError1);
        //ivError2 = findViewById(R.id.ivError2);
        //ivError3 = findViewById(R.id.ivError3);
        ivError4 = findViewById(R.id.ivError4);
        ivError5 = findViewById(R.id.ivError5);
        ivError6 = findViewById(R.id.ivError6);
        //ivError7 = findViewById(R.id.ivError7);
        ivError8 = findViewById(R.id.ivError8);
        ivError9 = findViewById(R.id.ivError9);
        //=========================================================
        //============================ IMAGE VIEW UP|DOWN  =========================================
        ivDown1 = findViewById(R.id.ivDown1);
        //ivDown2 = findViewById(R.id.ivDown2);
        //ivDown3 = findViewById(R.id.ivDown3);
        ivDown4 = findViewById(R.id.ivDown4);
        ivDown5 = findViewById(R.id.ivDown5);
        ivDown6 = findViewById(R.id.ivDown6);
        //ivDown7 = findViewById(R.id.ivDown7);
        ivDown8 = findViewById(R.id.ivDown8);
        ivDown9 = findViewById(R.id.ivDown9);

        ivUp1 = findViewById(R.id.ivUp1);
        //ivUp2 = findViewById(R.id.ivUp2);
        //ivUp3 = findViewById(R.id.ivUp3);
        ivUp4 = findViewById(R.id.ivUp4);
        ivUp5 = findViewById(R.id.ivUp5);
        ivUp6 = findViewById(R.id.ivUp6);
        //ivUp7 = findViewById(R.id.ivUp7);
        ivUp8 = findViewById(R.id.ivUp8);
        ivUp9 = findViewById(R.id.ivUp9);
        //=========================================================
        //================ LINEAR LAYOUT  =========================
        llDatosPersonales   = findViewById(R.id.llDatosPersonales);
        //llDatosTelefonicos  = findViewById(R.id.llDatosTelefonicos);
        //llDatosDomicilio    = findViewById(R.id.llDatosDomicilio);
        llDatosNegocio      = findViewById(R.id.llDatosNegocio);
        llDatosConyuge      = findViewById(R.id.llDatosConyuge);
        llOtrosDatos        = findViewById(R.id.llOtrosDatos);
        llDatosCroquis      = findViewById(R.id.llDatosCroquis);
        llDatosPoliticas    = findViewById(R.id.llDatosPoliticas);
        llDatosDocumentos   = findViewById(R.id.llDatosDocumentos);

        llPersonales    = findViewById(R.id.llPersonales);
        //llTelefonicos   = findViewById(R.id.llTelefonicos);
        //llDomicilio     = findViewById(R.id.llDomicilio);
        llNegocio       = findViewById(R.id.llNegocio);
        llConyuge       = findViewById(R.id.llConyuge);
        llOtros         = findViewById(R.id.llOtros);
        llCroquis       = findViewById(R.id.llCroquis);
        llPoliticas     = findViewById(R.id.llPoliticas);
        llDocumentos    = findViewById(R.id.llDocumentos);
        //=========================================================

        btnContinuar0 = findViewById(R.id.btnContinuar0);
        //btnContinuar1 = findViewById(R.id.btnContinuar1);
        //btnContinuar2 = findViewById(R.id.btnContinuar2);
        btnContinuar3 = findViewById(R.id.btnContinuar3);
        btnContinuar4 = findViewById(R.id.btnContinuar4);
        //btnContinuar7 = findViewById(R.id.btnContinuar7);
        btnContinuar8 = findViewById(R.id.btnContinuar8);
        btnContinuar5 = findViewById(R.id.btnContinuar5);

        btnRegresar0 = findViewById(R.id.btnRegresar0);
        //btnRegresar1 = findViewById(R.id.btnRegresar1);
        //btnRegresar2 = findViewById(R.id.btnRegresar2);
        btnRegresar3 = findViewById(R.id.btnRegresar3);
        btnRegresar4 = findViewById(R.id.btnRegresar4);
        //btnRegresar5 = findViewById(R.id.btnRegresar5);
        //btnRegresar7 = findViewById(R.id.btnRegresar7);
        btnRegresar8 = findViewById(R.id.btnRegresar8);
        btnRegresar6 = findViewById(R.id.btnRegresar6);

        mapCli.onCreate(savedInstanceState);
        mapNeg.onCreate(savedInstanceState);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        //etNumOperacionNeg.setEnabled(false);
        //etNumOperacionNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));

        llCurp.setVisibility(View.GONE);

        /**Valida si va ser el registro de un integrante nuevo para comenzar con el registro del nombre y el cargo*/
        if (getIntent().getBooleanExtra("is_new",true)) {
            //isNuevo =  getIntent().getBooleanExtra("is_new",true);
            /**Obtiene los datos que se pasaron entre clases*/
            id_credito = getIntent().getStringExtra("id_credito");
            /**Funcion para registrar primero el nombre y el cargo del integrante*/
            openRegistroIntegrante(id_credito);
        }
        else{/**En caso de ya estar registrado continuara con el registro de los demas datos*/
            /**Obtiene los datos que se pasaron entre clases*/
            id_credito = getIntent().getStringExtra("id_credito");
            id_integrante = getIntent().getStringExtra("id_integrante");
            /**Funcion para precargar la informacion que ha sido guardada del integrante*/
            initComponents(getIntent().getStringExtra("id_credito"), getIntent().getStringExtra("id_integrante"));

        }

        tvRiesgo.setText(_riesgo[2]);
        Update("clasificacion_riesgo", TBL_OTROS_DATOS_INTEGRANTE, Miscellaneous.GetStr(tvRiesgo), "id_integrante", id_integrante);
        tvDestinoNeg.setText("RE-INVERSION");
        tvDestinoNeg.setEnabled(false);
        Update("destino_credito", TBL_NEGOCIO_INTEGRANTE, _destino_credito[0], "id_integrante", id_integrante);

        /**Valida la periodicidad del credito para establecer el numero de operaciones en efectivo
         * y actualiza la columna*/
        switch (getIntent().getIntExtra("periodicidad", 0)){
            case 7:
                //etNumOperacionNeg.setText("4");
                tvNumOperacionNeg.setText("4");
                Update("num_ope_mensuales", TBL_NEGOCIO_INTEGRANTE_REN, "4", "id_integrante", id_integrante);
                break;
            case 14:
            case 15:
                //etNumOperacionNeg.setText("2");
                tvNumOperacionNeg.setText("2");
                Update("num_ope_mensuales", TBL_NEGOCIO_INTEGRANTE_REN, "2", "id_integrante", id_integrante);
                break;
            case 30:
                //etNumOperacionNeg.setText("1");
                tvNumOperacionNeg.setText("1");
                Update("num_ope_mensuales", TBL_NEGOCIO_INTEGRANTE_REN, "1", "id_integrante", id_integrante);
                break;
        }

        //============================== LINEAR LAYOUT LISTENER  ==================================
        /***Evento de click para mostrar u ocultar las secciones*/
        llPersonales.setOnClickListener(llPersonales_OnClick);
        //llTelefonicos.setOnClickListener(llTelefonicos_OnClick);
        //llDomicilio.setOnClickListener(llDomicilio_OnClick);
        llNegocio.setOnClickListener(llNegocio_OnClick);
        llConyuge.setOnClickListener(llConyuge_OnClick);
        llOtros.setOnClickListener(llOtros_OnClick);
        llCroquis.setOnClickListener(llCroquis_OnClick);
        llPoliticas.setOnClickListener(llPoliticas_OnClick);
        llDocumentos.setOnClickListener(llDocumentos_OnClick);
        //===========================================================================

        //==============================  PERSONALES LISTENER ======================================
        /**Evento de click o ingreso de datos personales del integrante con guardado en automatico*/
        tvFechaNacCli.setOnClickListener(tvFechaNacCli_OnClick);
        tvEstadoNacCli.setOnClickListener(tvEstadoNacCli_OnClick);
        etCurpCli.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @SuppressLint("SetTextI18n")
            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0){
                    if (s.toString().contains("Curp no válida"))
                        tvRfcCli.setText("Rfc no válida");
                    else {
                        Update("curp", TBL_INTEGRANTES_GPO_REN, s.toString().trim().toUpperCase(), "id", id_integrante);
                        if (s.toString().trim().length() >= 10) {
                            tvRfcCli.setText(Miscellaneous.GenerarRFC(s.toString().substring(0, 10), etNombreCli.getText().toString().trim(), etApPaternoCli.getText().toString().trim(), etApMaternoCli.getText().toString().trim()));
                            Update("rfc", TBL_INTEGRANTES_GPO_REN, tvRfcCli.getText().toString().trim().toUpperCase(), "id", id_integrante);
                        }
                        else{
                            tvRfcCli.setText("");
                            Update("rfc", TBL_INTEGRANTES_GPO_REN, tvRfcCli.getText().toString().trim().toUpperCase(), "id", id_integrante);
                        }
                    }
                }
                else {
                    tvRfcCli.setText("Rfc no válida");
                    Update("rfc", TBL_INTEGRANTES_GPO_REN, "", "id", id_integrante);
                    Update("curp", TBL_INTEGRANTES_GPO_REN, "", "id", id_integrante);
                }
            }
        });
        /*etCurpIdCli.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable e) {
                if (e.length() > 0)
                    Update("curp_digito_veri", TBL_INTEGRANTES_GPO_REN, e.toString(), "id", id_integrante);
                else
                    Update("curp_digito_veri", TBL_INTEGRANTES_GPO_REN, "", "id", id_integrante);

            }
        });*/
        tvTipoIdentificacion.setOnClickListener(tvTipoIdentificacion_OnClick);
        etNumIdentifCli.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable e) {
                if (e.length() > 0)
                    Update("no_identificacion", TBL_INTEGRANTES_GPO_REN, e.toString(), "id", id_integrante);
                else
                    Update("no_identificacion", TBL_INTEGRANTES_GPO_REN, e.toString(), "id", id_integrante);
            }
        });
        tvEstudiosCli.setOnClickListener(tvEstudiosCli_OnClick);
        tvOcupacionCli.setOnClickListener(tvOcupacionCli_OnClick);
        tvEstadoCivilCli.setOnClickListener(tvEstadoCivilCli_OnClick);
        //===========================================================================
        //==============================  TELEFONICOS LISTENER =====================================
        /**Evento de ingreso de datos telefonicos del integrante con guardado en automatico*/
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
                        Update("tel_casa", TBL_TELEFONOS_INTEGRANTE_REN, etTelCasaCli.getText().toString().trim(), "id_integrante", id_integrante);
                    }
                    else {
                        etTelCasaCli.setError(ctx.getResources().getString(R.string.mensaje_telefono));
                        Update("tel_casa", TBL_TELEFONOS_INTEGRANTE_REN, "", "id_integrante", id_integrante);
                    }
                }
                else {
                    etTelCasaCli.setError(null);
                    Update("tel_casa", TBL_TELEFONOS_INTEGRANTE_REN, "", "id_integrante", id_integrante);
                }
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
                        Update("tel_celular", TBL_TELEFONOS_INTEGRANTE_REN, etCelularCli.getText().toString().trim(), "id_integrante", id_integrante);
                    }
                    else {
                        etCelularCli.setError(ctx.getResources().getString(R.string.mensaje_telefono));
                        Update("tel_celular", TBL_TELEFONOS_INTEGRANTE_REN, "", "id_integrante", id_integrante);
                    }
                }
                else {
                    etCelularCli.setError(null);
                    Update("tel_celular", TBL_TELEFONOS_INTEGRANTE_REN, "", "id_integrante", id_integrante);
                }
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
                        Update("tel_mensaje", TBL_TELEFONOS_INTEGRANTE_REN, etTelMensCli.getText().toString().trim(), "id_integrante", id_integrante);
                    }
                    else {
                        etTelMensCli.setError(ctx.getResources().getString(R.string.mensaje_telefono));
                        Update("tel_mensaje", TBL_TELEFONOS_INTEGRANTE_REN, "", "id_integrante", id_integrante);
                    }
                }
                else {
                    etTelMensCli.setError(null);
                    Update("tel_mensaje", TBL_TELEFONOS_INTEGRANTE_REN, "", "id_integrante", id_integrante);
                }
            }
        });
        etTeltrabajoCli.addTextChangedListener(new TextWatcher() {
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
                        Update("tel_trabajo", TBL_TELEFONOS_INTEGRANTE_REN, etTeltrabajoCli.getText().toString().trim(), "id_integrante", id_integrante);
                    }
                    else {
                        etTelMensCli.setError(ctx.getResources().getString(R.string.mensaje_telefono));
                        Update("tel_trabajo", TBL_TELEFONOS_INTEGRANTE_REN, "", "id_integrante", id_integrante);
                    }
                }
                else {
                    etTelMensCli.setError(null);
                    Update("tel_trabajo", TBL_TELEFONOS_INTEGRANTE_REN, "", "id_integrante", id_integrante);
                }
            }
        });
        //===========================================================================
        //==============================  DOMICILIO LISTENER =======================================
        /**Evento de click o ingreso de datos del domicilio del integrante con guardado en automatico*/
        ibMapCli.setOnClickListener(ibMapCli_OnClick);
        etCalleCli.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable e) {
                if (e.length() > 0)
                    Update("calle", TBL_DOMICILIO_INTEGRANTE_REN, e.toString().trim().toUpperCase(), "id_integrante", id_integrante);
                else
                    Update("calle", TBL_DOMICILIO_INTEGRANTE_REN, "", "id_integrante", id_integrante);
            }
        });
        etNoExtCli.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable e) {
                if (e.length() > 0)
                    Update("no_exterior", TBL_DOMICILIO_INTEGRANTE_REN, e.toString().trim().toUpperCase(), "id_integrante", id_integrante);
                else
                    Update("no_exterior", TBL_DOMICILIO_INTEGRANTE_REN, "", "id_integrante", id_integrante);
            }
        });
        etNoIntCli.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable e) {
                if (e.length() > 0)
                    Update("no_interior", TBL_DOMICILIO_INTEGRANTE_REN, e.toString().trim().toUpperCase(), "id_integrante", id_integrante);
                else
                    Update("no_interior", TBL_DOMICILIO_INTEGRANTE_REN, "", "id_integrante", id_integrante);
            }
        });
        etManzanaCli.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable e) {
                if (e.length() > 0)
                    Update("manzana", TBL_DOMICILIO_INTEGRANTE_REN, e.toString().trim().toUpperCase(), "id_integrante", id_integrante);
                else
                    Update("manzana", TBL_DOMICILIO_INTEGRANTE_REN, "", "id_integrante", id_integrante);
            }
        });
        etLoteCli.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable e) {
                if (e.length() > 0)
                    Update("lote", TBL_DOMICILIO_INTEGRANTE_REN, e.toString().trim().toUpperCase(), "id_integrante", id_integrante);
                else
                    Update("lote", TBL_DOMICILIO_INTEGRANTE_REN, "", "id_integrante", id_integrante);
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
                        Update("cp", TBL_DOMICILIO_INTEGRANTE_REN, s.toString().trim(), "id_integrante", id_integrante);
                        row.moveToFirst();
                        if (row.getCount() == 1){
                            Update("colonia", TBL_DOMICILIO_INTEGRANTE_REN, row.getString(7), "id_integrante", id_integrante);
                            Update("municipio", TBL_DOMICILIO_INTEGRANTE_REN, row.getString(4), "id_integrante", id_integrante);
                            Update("estado", TBL_DOMICILIO_INTEGRANTE_REN, row.getString(1), "id_integrante", id_integrante);
                            tvColoniaCli.setText(row.getString(7));
                            tvMunicipioCli.setText(row.getString(4));
                            tvEstadoCli.setText(row.getString(1));
                        }else {
                            if(tvColoniaCli.isEnabled() && tvColoniaCli.getText().toString().equals(""))
                            {
                                Update("colonia", TBL_DOMICILIO_INTEGRANTE_REN, "", "id_integrante", id_integrante);
                                tvColoniaCli.setText("");
                            }

                            Update("municipio", TBL_DOMICILIO_INTEGRANTE_REN, row.getString(4), "id_integrante", id_integrante);
                            Update("estado", TBL_DOMICILIO_INTEGRANTE_REN, row.getString(1), "id_integrante", id_integrante);

                            tvMunicipioCli.setText(row.getString(4));
                            tvEstadoCli.setText(row.getString(1));
                        }

                    }else {
                        Update("cp", TBL_DOMICILIO_INTEGRANTE_REN, "", "id_integrante", id_integrante);
                        Update("colonia", TBL_DOMICILIO_INTEGRANTE_REN, "", "id_integrante", id_integrante);
                        Update("municipio", TBL_DOMICILIO_INTEGRANTE_REN, "", "id_integrante", id_integrante);
                        Update("estado", TBL_DOMICILIO_INTEGRANTE_REN, "", "id_integrante", id_integrante);
                        tvColoniaCli.setText(R.string.not_found);
                        tvMunicipioCli.setText(R.string.not_found);
                        tvEstadoCli.setText(R.string.not_found);
                    }
                    row.close();
                }else {
                    Update("cp", TBL_DOMICILIO_INTEGRANTE_REN, "", "id_integrante", id_integrante);
                    Update("colonia", TBL_DOMICILIO_INTEGRANTE_REN, "", "id_integrante", id_integrante);
                    Update("municipio", TBL_DOMICILIO_INTEGRANTE_REN, "", "id_integrante", id_integrante);
                    Update("estado", TBL_DOMICILIO_INTEGRANTE_REN, "", "id_integrante", id_integrante);
                    tvColoniaCli.setText(R.string.not_found);
                    tvMunicipioCli.setText(R.string.not_found);
                    tvEstadoCli.setText(R.string.not_found);
                }
            }
        });
        tvColoniaCli.setOnClickListener(etColonia_OnClick);
        etCiudadCli.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable e) {
                if (e.length() > 0)
                    Update("ciudad", TBL_DOMICILIO_INTEGRANTE_REN, e.toString().trim().toUpperCase(), "id_integrante", id_integrante);
                else
                    Update("ciudad", TBL_DOMICILIO_INTEGRANTE_REN, "", "id_integrante", id_integrante);
            }
        });
        tvLocalidadCli.setOnClickListener(tvLocalidadCli_OnClick);
        tvTipoCasaCli.setOnClickListener(tvTipoCasaCli_OnClick);
        tvCasaFamiliar.setOnClickListener(tvCasaFamiliar_OnClick);
        etTiempoSitio.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable e) {
                if (e.length() > 0) {
                    if (Integer.parseInt(e.toString()) > 0)
                        Update("tiempo_vivir_sitio", TBL_DOMICILIO_INTEGRANTE_REN, String.valueOf(Integer.parseInt(e.toString())), "id_integrante", id_integrante);
                    else
                        Update("tiempo_vivir_sitio", TBL_DOMICILIO_INTEGRANTE_REN, "", "id_integrante", id_integrante);
                }
                else
                    Update("tiempo_vivir_sitio", TBL_DOMICILIO_INTEGRANTE_REN, "", "id_integrante", id_integrante);

            }
        });
        tvDependientes.setOnClickListener(tvDependientes_OnClick);
        ibFotoFachCli.setOnClickListener(ibFotoFachCli_OnClick);
        etReferenciaCli.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable e) {
                if (e.length() > 0)
                    Update("ref_domiciliaria", TBL_DOMICILIO_INTEGRANTE_REN, e.toString().trim().toUpperCase(), "id_integrante", id_integrante);
                else
                    Update("ref_domiciliaria", TBL_DOMICILIO_INTEGRANTE_REN, "", "id_integrante", id_integrante);
            }
        });
        etNombreFirmaRuegoEncargo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable e) {
                if (e.length() > 0)
                    Update("nombre_quien_firma", TBL_OTROS_DATOS_INTEGRANTE_REN, e.toString().trim().toUpperCase(), "id_integrante", id_integrante);
                else
                    Update("nombre_quien_firma", TBL_OTROS_DATOS_INTEGRANTE_REN, "", "id_integrante", id_integrante);
            }
        });
        rgFirmaRuegoEncargo.setOnCheckedChangeListener((group, checkedId) -> {
            if (is_edit) {
                etNombreFirmaRuegoEncargo.setError(null);
                switch (checkedId) {
                    case R.id.rbSiTieneFirma:
                        etNombreFirmaRuegoEncargo.setText("");
                        Update("tiene_firma", TBL_OTROS_DATOS_INTEGRANTE_REN, "SI", "id_integrante", id_integrante);
                        Update("nombre_quien_firma", TBL_OTROS_DATOS_INTEGRANTE_REN, "", "id_integrante", id_integrante);
                        llNombreFirmaRuegoEncargo.setVisibility(View.GONE);
                        break;
                    case R.id.rbNoTieneFirma:
                        Update("tiene_firma", TBL_OTROS_DATOS_INTEGRANTE_REN, "NO", "id_integrante", id_integrante);
                        llNombreFirmaRuegoEncargo.setVisibility(View.VISIBLE);
                        break;
                }
            }
        });
        //===========================================================================
        //==================================  NEGOCIO LISTENER  ====================================
        /**Evento de click o ingreso de datos del neogocio del integrante con guardado en automatico*/
        etNombreNeg.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable e) {
                if (e.length() > 0)
                    Update("nombre", TBL_NEGOCIO_INTEGRANTE_REN, e.toString().trim().toUpperCase(), "id_integrante", id_integrante);
                else
                    Update("nombre", TBL_NEGOCIO_INTEGRANTE_REN, "", "id_integrante", id_integrante);
            }
        });
        ibMapNeg.setOnClickListener(ibMapNeg_OnClick);
        etCalleNeg.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable e) {
                if (e.length() > 0)
                    Update("calle", TBL_NEGOCIO_INTEGRANTE_REN, e.toString().trim().toUpperCase(), "id_integrante", id_integrante);
                else
                    Update("calle", TBL_NEGOCIO_INTEGRANTE_REN, "", "id_integrante", id_integrante);
            }
        });
        etNoExtNeg.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable e) {
                if (e.length() > 0)
                    Update("no_exterior", TBL_NEGOCIO_INTEGRANTE_REN, e.toString().trim().toUpperCase(), "id_integrante", id_integrante);
                else
                    Update("no_exterior", TBL_NEGOCIO_INTEGRANTE_REN, "", "id_integrante", id_integrante);
            }
        });
        etNoIntNeg.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable e) {
                if (e.length() > 0)
                    Update("no_interior", TBL_NEGOCIO_INTEGRANTE_REN, e.toString().trim().toUpperCase(), "id_integrante", id_integrante);
                else
                    Update("no_interior", TBL_NEGOCIO_INTEGRANTE_REN, "", "id_integrante", id_integrante);
            }
        });
        etManzanaNeg.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable e) {
                if (e.length() > 0)
                    Update("manzana", TBL_NEGOCIO_INTEGRANTE_REN, e.toString().trim().toUpperCase(), "id_integrante", id_integrante);
                else
                    Update("manzana", TBL_NEGOCIO_INTEGRANTE_REN, "", "id_integrante", id_integrante);
            }
        });
        etLoteNeg.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable e) {
                if (e.length() > 0)
                    Update("lote", TBL_NEGOCIO_INTEGRANTE_REN, e.toString().trim().toUpperCase(), "id_integrante", id_integrante);
                else
                    Update("lote", TBL_NEGOCIO_INTEGRANTE_REN, "", "id_integrante", id_integrante);
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
                        Update("cp", TBL_NEGOCIO_INTEGRANTE_REN, s.toString().trim(), "id_integrante", id_integrante);
                        row.moveToFirst();
                        if (row.getCount() == 1){
                            Update("colonia", TBL_NEGOCIO_INTEGRANTE_REN, row.getString(7), "id_integrante", id_integrante);
                            Update("municipio", TBL_NEGOCIO_INTEGRANTE_REN, row.getString(4), "id_integrante", id_integrante);
                            Update("estado", TBL_NEGOCIO_INTEGRANTE_REN, row.getString(1), "id_integrante", id_integrante);
                            tvColoniaNeg.setText(row.getString(7));
                            tvMunicipioNeg.setText(row.getString(4));
                            tvEstadoNeg.setText(row.getString(1));
                        }else {
                            if(tvColoniaNeg.isEnabled() && tvColoniaNeg.getText().toString().equals(""))
                            {
                                Update("colonia", TBL_NEGOCIO_INTEGRANTE_REN, "", "id_integrante", id_integrante);
                                tvColoniaNeg.setText("");
                            }

                            Update("municipio", TBL_NEGOCIO_INTEGRANTE_REN, row.getString(4), "id_integrante", id_integrante);
                            Update("estado", TBL_NEGOCIO_INTEGRANTE_REN, row.getString(1), "id_integrante", id_integrante);

                            tvMunicipioNeg.setText(row.getString(4));
                            tvEstadoNeg.setText(row.getString(1));
                        }

                    }else {
                        Update("colonia", TBL_NEGOCIO_INTEGRANTE_REN, "", "id_integrante", id_integrante);
                        Update("municipio", TBL_NEGOCIO_INTEGRANTE_REN, "", "id_integrante", id_integrante);
                        Update("estado", TBL_NEGOCIO_INTEGRANTE_REN, "", "id_integrante", id_integrante);
                        tvColoniaNeg.setText(R.string.not_found);
                        tvMunicipioNeg.setText(R.string.not_found);
                        tvEstadoNeg.setText(R.string.not_found);
                    }
                    row.close();
                }else {
                    Update("colonia", TBL_NEGOCIO_INTEGRANTE_REN, "", "id_integrante", id_integrante);
                    Update("municipio", TBL_NEGOCIO_INTEGRANTE_REN, "", "id_integrante", id_integrante);
                    Update("estado", TBL_NEGOCIO_INTEGRANTE_REN, "", "id_integrante", id_integrante);
                    tvColoniaNeg.setText(R.string.not_found);
                    tvMunicipioNeg.setText(R.string.not_found);
                    tvEstadoNeg.setText(R.string.not_found);
                }
            }
        });
        tvColoniaNeg.setOnClickListener(tvColoniaNeg_OnClick);
        etCiudadNeg.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable e) {
                if (e.length() > 0)
                    Update("ciudad",TBL_NEGOCIO_INTEGRANTE_REN, e.toString().trim().toUpperCase(), "id_integrante", id_integrante);
                else
                    Update("ciudad",TBL_NEGOCIO_INTEGRANTE_REN, "", "id_integrante", id_integrante);
            }
        });
        tvLocalidadNeg.setOnClickListener(tvLocalidadNeg_OnClick);
        tvDestinoNeg.setOnClickListener(tvDestinoNeg_OnClick);
        etOtroDestinoNeg.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable e) {
                if (e.length() > 0)
                    Update("otro_destino_credito", TBL_NEGOCIO_INTEGRANTE_REN, e.toString().trim().toUpperCase(), "id_integrante", id_integrante);
                else
                    Update("otro_destino_credito", TBL_NEGOCIO_INTEGRANTE_REN, "", "id_integrante", id_integrante);
            }
        });
        tvActEcoEspNeg.setOnClickListener(tvActEcoEspNeg_OnClick);
        etAntiguedadNeg.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable e) {
                if (e.length() > 0) {
                    if (Integer.parseInt(e.toString()) > 0)
                        Update("antiguedad", TBL_NEGOCIO_INTEGRANTE_REN, String.valueOf(Integer.parseInt(e.toString())), "id_integrante", id_integrante);
                    else
                        Update("antiguedad", TBL_NEGOCIO_INTEGRANTE_REN, "", "id_integrante", id_integrante);
                }
                else
                    Update("antiguedad", TBL_NEGOCIO_INTEGRANTE_REN, "", "id_integrante", id_integrante);
            }
        });
        etIngMenNeg.addTextChangedListener(new TextWatcher() {
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
                etIngMenNeg.removeTextChangedListener(this);
                etIngMenNeg.setError(null);
                ivError4.setVisibility(View.GONE);
                try {
                    int inilen, endlen;
                    inilen = etIngMenNeg.getText().length();
                    String v = s.toString().replace(String.valueOf(df.getDecimalFormatSymbols().getGroupingSeparator()), "");
                    Number n = df.parse(v);
                    int cp = etIngMenNeg.getSelectionStart();
                    if (hasFractionalPart) {
                        etIngMenNeg.setText(df.format(n));
                    } else {
                        etIngMenNeg.setText(dfnd.format(n));
                    }
                    endlen = etIngMenNeg.getText().length();
                    int sel = (cp + (endlen - inilen));
                    if (sel > 0 && sel <= etIngMenNeg.getText().length()) {
                        etIngMenNeg.setSelection(sel);
                    } else {
                        // place cursor at the end?
                        etIngMenNeg.setSelection(etIngMenNeg.getText().length() - 1);
                    }
                } catch (NumberFormatException nfe) {
                    // do nothing?
                } catch (ParseException pe) {
                    // do nothing?
                }

                if (s.length() > 0)
                    Update("ing_mensual", TBL_NEGOCIO_INTEGRANTE_REN, s.toString().trim().replace(",",""), "id_integrante", id_integrante);
                else
                    Update("ing_mensual", TBL_NEGOCIO_INTEGRANTE_REN, "", "id_integrante", id_integrante);

                MontoMaximoNeg();

                etIngMenNeg.addTextChangedListener(this);
            }
        });
        etOtrosIngNeg.addTextChangedListener(new TextWatcher() {
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
                etOtrosIngNeg.removeTextChangedListener(this);

                try {
                    int inilen, endlen;
                    inilen = etOtrosIngNeg.getText().length();
                    String v = s.toString().replace(String.valueOf(df.getDecimalFormatSymbols().getGroupingSeparator()), "");
                    Number n = df.parse(v);
                    int cp = etOtrosIngNeg.getSelectionStart();
                    if (hasFractionalPart) {
                        etOtrosIngNeg.setText(df.format(n));
                    } else {
                        etOtrosIngNeg.setText(dfnd.format(n));
                    }
                    endlen = etOtrosIngNeg.getText().length();
                    int sel = (cp + (endlen - inilen));
                    if (sel > 0 && sel <= etOtrosIngNeg.getText().length()) {
                        etOtrosIngNeg.setSelection(sel);
                    } else {
                        // place cursor at the end?
                        etOtrosIngNeg.setSelection(etOtrosIngNeg.getText().length() - 1);
                    }
                } catch (NumberFormatException nfe) {
                    // do nothing?
                } catch (ParseException pe) {
                    // do nothing?
                }

                if (s.length() > 0)
                    Update("ing_otros", TBL_NEGOCIO_INTEGRANTE_REN, s.toString().trim().replace(",",""), "id_integrante", id_integrante);
                else
                    Update("ing_otros", TBL_NEGOCIO_INTEGRANTE_REN, "", "id_integrante", id_integrante);

                MontoMaximoNeg();

                etOtrosIngNeg.addTextChangedListener(this);
            }
        });
        etGastosSemNeg.addTextChangedListener(new TextWatcher() {
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
                etGastosSemNeg.removeTextChangedListener(this);
                etGastosSemNeg.setError(null);
                ivError4.setVisibility(View.GONE);
                try {
                    int inilen, endlen;
                    inilen = etGastosSemNeg.getText().length();
                    String v = s.toString().replace(String.valueOf(df.getDecimalFormatSymbols().getGroupingSeparator()), "");
                    Number n = df.parse(v);
                    int cp = etGastosSemNeg.getSelectionStart();
                    if (hasFractionalPart) {
                        etGastosSemNeg.setText(df.format(n));
                    } else {
                        etGastosSemNeg.setText(dfnd.format(n));
                    }
                    endlen = etGastosSemNeg.getText().length();
                    int sel = (cp + (endlen - inilen));
                    if (sel > 0 && sel <= etGastosSemNeg.getText().length()) {
                        etGastosSemNeg.setSelection(sel);
                    } else {
                        // place cursor at the end?
                        etGastosSemNeg.setSelection(etGastosSemNeg.getText().length() - 1);
                    }
                } catch (NumberFormatException nfe) {
                    // do nothing?
                } catch (ParseException pe) {
                    // do nothing?
                }

                if (s.length() > 0)
                    Update("gasto_semanal", TBL_NEGOCIO_INTEGRANTE_REN, s.toString().trim().replace(",",""), "id_integrante", id_integrante);
                else
                    Update("gasto_semanal", TBL_NEGOCIO_INTEGRANTE_REN, "", "id_integrante", id_integrante);

                MontoMaximoNeg();

                etGastosSemNeg.addTextChangedListener(this);
            }
        });
        etCapacidadPagoNeg.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                if (s.toString().contains(String.valueOf(df.getDecimalFormatSymbols().getDecimalSeparator())))
                {
                    hasFractionalPart = true;
                } else {
                    hasFractionalPart = false;
                }
            }

            @Override
            public void afterTextChanged(Editable e) {
                etCapacidadPagoNeg.removeTextChangedListener(this);

                try {
                    int inilen, endlen;
                    inilen = etCapacidadPagoNeg.getText().length();
                    String v = e.toString().replace(String.valueOf(df.getDecimalFormatSymbols().getGroupingSeparator()), "");
                    Number n = df.parse(v);
                    int cp = etCapacidadPagoNeg.getSelectionStart();
                    if (hasFractionalPart) {
                        etCapacidadPagoNeg.setText(df.format(n));
                    } else {
                        etCapacidadPagoNeg.setText(dfnd.format(n));
                    }
                    endlen = etCapacidadPagoNeg.getText().length();
                    int sel = (cp + (endlen - inilen));
                    if (sel > 0 && sel <= etCapacidadPagoNeg.getText().length()) {
                        etCapacidadPagoNeg.setSelection(sel);
                    } else {
                        // place cursor at the end?
                        etCapacidadPagoNeg.setSelection(etCapacidadPagoNeg.getText().length() - 1);
                    }
                } catch (NumberFormatException nfe) {
                    // do nothing?
                } catch (ParseException pe) {
                    // do nothing?
                }

                if (!tvMontoMaxNeg.getText().toString().trim().isEmpty() && Double.parseDouble(tvMontoMaxNeg.getText().toString().trim().replace(",","")) > 0) {
                    if (e.length() > 0)
                        try {
                            if (Double.parseDouble(e.toString().replace(",","")) > 0)
                                if (Double.parseDouble(e.toString().replace(",","")) <= Double.parseDouble(tvMontoMaxNeg.getText().toString().trim().replace(",","")))
                                    Update("capacidad_pago", TBL_NEGOCIO_INTEGRANTE_REN, e.toString().trim().replace(",",""), "id_integrante", id_integrante);
                                else
                                    ShowMensajes("EL monto no puede superar a la capacidad de pago","NEGOCIO");

                        } catch (NumberFormatException exception) {
                            Update("capacidad_pago", TBL_NEGOCIO_INTEGRANTE_REN, "", "id_integrante", id_integrante);
                        }

                    else
                        Update("capacidad_pago", TBL_NEGOCIO_INTEGRANTE_REN, "", "id_integrante", id_integrante);
                }
                else{
                    ShowMensajes("Tiene que completar primero los ingresos y gastos del neogocio","NEGOCIO");
                }

                etCapacidadPagoNeg.addTextChangedListener(this);
            }
        });
        tvMediosPagoNeg.setOnClickListener(tvMediosPagoNeg_OnClick);
        etOtroMedioPagoNeg.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable e) {
                if (e.length() > 0)
                    Update("otro_medio_pago", TBL_NEGOCIO_INTEGRANTE_REN, e.toString().trim().toUpperCase(), "id_integrante", id_integrante);
                else
                    Update("otro_medio_pago", TBL_NEGOCIO_INTEGRANTE_REN, "", "id_integrante", id_integrante);
            }
        });
        /*etNumOperacionNeg.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable e) {
                if (e.length() > 0) {
                    try {
                        if (Integer.parseInt(e.toString()) > 0)
                            Update("num_ope_mensuales", TBL_NEGOCIO_INTEGRANTE_REN, e.toString().trim().toUpperCase(), "id_integrante", id_integrante);

                    } catch (NumberFormatException exception) {
                        Update("num_ope_mensuales", TBL_NEGOCIO_INTEGRANTE_REN, "0", "id_integrante", id_integrante);
                    }
                }
                else
                    Update("num_ope_mensuales", TBL_NEGOCIO_INTEGRANTE_REN, "0", "id_integrante", id_integrante);
            }
        });
        etNumOperacionEfectNeg.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable e) {
                if (e.length() > 0) {
                    try {
                        if (Integer.parseInt(e.toString()) > 0)
                            Update("num_ope_mensuales_efectivo", TBL_NEGOCIO_INTEGRANTE_REN, e.toString().trim().toUpperCase(), "id_integrante", id_integrante);

                    } catch (NumberFormatException exception) {
                        Update("num_ope_mensuales_efectivo", TBL_NEGOCIO_INTEGRANTE_REN, "0", "id_integrante", id_integrante);
                    }
                }
                else
                    Update("num_ope_mensuales_efectivo", TBL_NEGOCIO_INTEGRANTE_REN, "0", "id_integrante", id_integrante);
            }
        });*/
        tvNumOperacionNeg.setOnClickListener(tvNumOperacionNeg_OnClick);
        etNumOperacionEfectNeg.setOnClickListener(etNumOperacionEfectNeg_OnClick);
        ibFotoFachNeg.setOnClickListener(ibFotoFachNeg_OnClick);
        etReferenciNeg.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable e) {
                if (e.length() > 0)
                    Update("ref_domiciliaria", TBL_NEGOCIO_INTEGRANTE_REN, e.toString().trim().toUpperCase(), "id_integrante", id_integrante);
                else
                    Update("ref_domiciliaria", TBL_NEGOCIO_INTEGRANTE_REN, "", "id_integrante", id_integrante);
            }
        });
        cbNegEnDomCli.setOnClickListener(cbNegEnDomCli_OnCheck);
        rgTieneNegocio.setOnCheckedChangeListener((group, checkedId) -> {
            if (is_edit) {
                tvNegocioIntegrante.setError(null);
                switch (checkedId) {
                    case R.id.rbSiTieneNeg:
                        Update("tiene_negocio", TBL_NEGOCIO_INTEGRANTE_REN, "SI", "id_integrante", id_integrante);
                        HabilitarCamposNegocio();
                        break;
                    case R.id.rbNoTieneNeg:
                        Update("tiene_negocio", TBL_NEGOCIO_INTEGRANTE_REN, "NO", "id_integrante", id_integrante);
                        DeshabilitarCamposNegocio();
                        break;
                }
            }
        });
        //===========================================================================
        //==================================  CONYUGE LISTENER  ====================================
        /**Evento de click o ingreso de datos del conyuge del integrante con guardado en automatico*/
        etNombreCony.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable e) {
                if (e.length() > 0)
                    Update("nombre", TBL_CONYUGE_INTEGRANTE_REN, e.toString().trim().toUpperCase(), "id_integrante", id_integrante);
                else
                    Update("nombre", TBL_CONYUGE_INTEGRANTE_REN, "", "id_integrante", id_integrante);
            }
        });
        etApPaternoCony.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable e) {
                if (e.length() > 0)
                    Update("paterno", TBL_CONYUGE_INTEGRANTE_REN, e.toString().trim().toUpperCase(), "id_integrante", id_integrante);
                else
                    Update("paterno", TBL_CONYUGE_INTEGRANTE_REN, "", "id_integrante", id_integrante);
            }
        });
        etApMaternoCony.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable e) {
                if (e.length() > 0)
                    Update("materno", TBL_CONYUGE_INTEGRANTE_REN, e.toString().trim().toUpperCase(), "id_integrante", id_integrante);
                else
                    Update("materno", TBL_CONYUGE_INTEGRANTE_REN, "", "id_integrante", id_integrante);
            }
        });
        etNacionalidad.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable e) {
                if (e.length() > 0)
                    Update("nacionalidad", TBL_CONYUGE_INTEGRANTE_REN, e.toString().trim().toUpperCase(), "id_integrante", id_integrante);
                else
                    Update("nacionalidad", TBL_CONYUGE_INTEGRANTE_REN, "", "id_integrante", id_integrante);
            }
        });
        tvOcupacionCony.setOnClickListener(tvOcupacionCony_OnClick);
        etCalleCony.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable e) {
                if (e.length() > 0)
                    Update("calle", TBL_CONYUGE_INTEGRANTE_REN, e.toString().trim().toUpperCase(), "id_integrante", id_integrante);
                else
                    Update("calle", TBL_CONYUGE_INTEGRANTE_REN, "", "id_integrante", id_integrante);
            }
        });
        etNoExtCony.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable e) {
                if (e.length() > 0)
                    Update("no_exterior", TBL_CONYUGE_INTEGRANTE_REN, e.toString().trim().toUpperCase(), "id_integrante", id_integrante);
                else
                    Update("no_exterior", TBL_CONYUGE_INTEGRANTE_REN, "", "id_integrante", id_integrante);
            }
        });
        etNoIntCony.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable e) {
                if (e.length() > 0)
                    Update("no_interior", TBL_CONYUGE_INTEGRANTE_REN, e.toString().trim().toUpperCase(), "id_integrante", id_integrante);
                else
                    Update("no_interior", TBL_CONYUGE_INTEGRANTE_REN, "", "id_integrante", id_integrante);
            }
        });
        etManzanaCony.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable e) {
                if (e.length() > 0)
                    Update("manzana", TBL_CONYUGE_INTEGRANTE_REN, e.toString().trim().toUpperCase(), "id_integrante", id_integrante);
                else
                    Update("manzana", TBL_CONYUGE_INTEGRANTE_REN, "", "id_integrante", id_integrante);
            }
        });
        etLoteCony.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable e) {
                if (e.length() > 0)
                    Update("lote", TBL_CONYUGE_INTEGRANTE_REN, e.toString().trim().toUpperCase(), "id_integrante", id_integrante);
                else
                    Update("lote", TBL_CONYUGE_INTEGRANTE_REN, "", "id_integrante", id_integrante);
            }
        });
        etCpCony.addTextChangedListener(new TextWatcher() {
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
                        //ContentValues cv = new ContentValues();
                        Update("cp", TBL_CONYUGE_INTEGRANTE_REN, s.toString().trim(), "id_integrante", id_integrante);
                        //cv.put("cp", s.toString().trim());
                        row.moveToFirst();
                        if (row.getCount() == 1){
                            //cv.put("colonia", row.getString(7));
                            Update("colonia", TBL_CONYUGE_INTEGRANTE_REN, row.getString(7), "id_integrante", id_integrante);
                            Update("municipio", TBL_CONYUGE_INTEGRANTE_REN, row.getString(4), "id_integrante", id_integrante);
                            Update("estado", TBL_CONYUGE_INTEGRANTE_REN, row.getString(1), "id_integrante", id_integrante);
                            tvColoniaCony.setText(row.getString(7));
                            tvMunicipioCony.setText(row.getString(4));
                            tvEstadoCony.setText(row.getString(1));
                        }else {
                            if(tvColoniaCony.isEnabled() && tvColoniaCony.getText().toString().equals(""))
                            {
                                Update("colonia", TBL_CONYUGE_INTEGRANTE_REN, "", "id_integrante", id_integrante);
                                tvColoniaCony.setText("");
                            }

                            Update("municipio", TBL_CONYUGE_INTEGRANTE_REN, row.getString(4), "id_integrante", id_integrante);
                            Update("estado", TBL_CONYUGE_INTEGRANTE_REN, row.getString(1), "id_integrante", id_integrante);

                            tvMunicipioCony.setText(row.getString(4));
                            tvEstadoCony.setText(row.getString(1));
                        }

                    }else {
                        Update("cp", TBL_CONYUGE_INTEGRANTE_REN, "", "id_integrante", id_integrante);
                        Update("colonia", TBL_CONYUGE_INTEGRANTE_REN, "", "id_integrante", id_integrante);
                        Update("municipio", TBL_CONYUGE_INTEGRANTE_REN, "", "id_integrante", id_integrante);
                        Update("estado", TBL_CONYUGE_INTEGRANTE_REN, "", "id_integrante", id_integrante);
                        tvColoniaCony.setText(R.string.not_found);
                        tvMunicipioCony.setText(R.string.not_found);
                        tvEstadoCony.setText(R.string.not_found);
                    }
                    row.close();
                }else {
                    Update("cp", TBL_CONYUGE_INTEGRANTE_REN, "", "id_integrante", id_integrante);
                    Update("colonia", TBL_CONYUGE_INTEGRANTE_REN, "", "id_integrante", id_integrante);
                    Update("municipio", TBL_CONYUGE_INTEGRANTE_REN, "", "id_integrante", id_integrante);
                    Update("estado", TBL_CONYUGE_INTEGRANTE_REN, "", "id_integrante", id_integrante);
                    tvColoniaCony.setText(R.string.not_found);
                    tvMunicipioCony.setText(R.string.not_found);
                    tvEstadoCony.setText(R.string.not_found);
                }
            }
        });
        tvColoniaCony.setOnClickListener(tvColoniaCony_OnClick);
        etCiudadCony.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable e) {
                if (e.length() > 0)
                    Update("ciudad", TBL_CONYUGE_INTEGRANTE_REN, e.toString().trim().toUpperCase(), "id_integrante", id_integrante);
                else
                    Update("ciudad", TBL_CONYUGE_INTEGRANTE_REN, "", "id_integrante", id_integrante);
            }
        });
        tvLocalidadCony.setOnClickListener(tvLocalidadCony_OnClick);
        etIngresoCony.addTextChangedListener(new TextWatcher() {
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
            public void afterTextChanged(Editable e) {
                etIngresoCony.removeTextChangedListener(this);

                try {
                    int inilen, endlen;
                    inilen = etIngresoCony.getText().length();
                    String v = e.toString().replace(String.valueOf(df.getDecimalFormatSymbols().getGroupingSeparator()), "");
                    Number n = df.parse(v);
                    int cp = etIngresoCony.getSelectionStart();
                    if (hasFractionalPart) {
                        etIngresoCony.setText(df.format(n));
                    } else {
                        etIngresoCony.setText(dfnd.format(n));
                    }
                    endlen = etIngresoCony.getText().length();
                    int sel = (cp + (endlen - inilen));
                    if (sel > 0 && sel <= etIngresoCony.getText().length()) {
                        etIngresoCony.setSelection(sel);
                    } else {
                        // place cursor at the end?
                        etIngresoCony.setSelection(etIngresoCony.getText().length() - 1);
                    }
                } catch (NumberFormatException nfe) {
                    // do nothing?
                } catch (ParseException pe) {
                    // do nothing?
                }

                if (e.length() > 0)
                    Update("ingresos_mensual", TBL_CONYUGE_INTEGRANTE_REN, e.toString().replace(",",""), "id_integrante", id_integrante);
                else
                    Update("ingresos_mensual", TBL_CONYUGE_INTEGRANTE_REN, "", "id_integrante", id_integrante);

                etIngresoCony.addTextChangedListener(this);
            }
        });
        etGastoCony.addTextChangedListener(new TextWatcher() {
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
            public void afterTextChanged(Editable e) {
                etGastoCony.removeTextChangedListener(this);

                try {
                    int inilen, endlen;
                    inilen = etGastoCony.getText().length();
                    String v = e.toString().replace(String.valueOf(df.getDecimalFormatSymbols().getGroupingSeparator()), "");
                    Number n = df.parse(v);
                    int cp = etGastoCony.getSelectionStart();
                    if (hasFractionalPart) {
                        etGastoCony.setText(df.format(n));
                    } else {
                        etGastoCony.setText(dfnd.format(n));
                    }
                    endlen = etGastoCony.getText().length();
                    int sel = (cp + (endlen - inilen));
                    if (sel > 0 && sel <= etGastoCony.getText().length()) {
                        etGastoCony.setSelection(sel);
                    } else {
                        // place cursor at the end?
                        etGastoCony.setSelection(etGastoCony.getText().length() - 1);
                    }
                } catch (NumberFormatException nfe) {
                    // do nothing?
                } catch (ParseException pe) {
                    // do nothing?
                }

                if (e.length() > 0)
                    Update("gasto_mensual", TBL_CONYUGE_INTEGRANTE_REN, e.toString().replace(",",""), "id_integrante", id_integrante);
                else
                    Update("gasto_mensual", TBL_CONYUGE_INTEGRANTE_REN, "", "id_integrante", id_integrante);

                etGastoCony.addTextChangedListener(this);
            }
        });
        etCasaCony.addTextChangedListener(new TextWatcher() {
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
                        etCasaCony.setError(null);
                        Update("tel_trabajo", TBL_CONYUGE_INTEGRANTE_REN, s.toString(), "id_integrante", id_integrante);
                    }
                    else {
                        etCasaCony.setError(ctx.getResources().getString(R.string.mensaje_telefono));
                        Update("tel_trabajo", TBL_CONYUGE_INTEGRANTE_REN, "", "id_integrante", id_integrante);
                    }
                }
                else {
                    etCasaCony.setError(null);
                    Update("tel_trabajo", TBL_CONYUGE_INTEGRANTE_REN, "", "id_integrante", id_integrante);
                }
            }
        });
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
                        Update("tel_celular", TBL_CONYUGE_INTEGRANTE_REN, s.toString(), "id_integrante", id_integrante);
                    }
                    else {
                        etCelularCony.setError(ctx.getResources().getString(R.string.mensaje_telefono));
                        Update("tel_celular", TBL_CONYUGE_INTEGRANTE_REN, "", "id_integrante", id_integrante);
                    }
                }
                else {
                    etCelularCony.setError(null);
                    Update("tel_celular", TBL_CONYUGE_INTEGRANTE_REN, "", "id_integrante", id_integrante);
                }
            }
        });
        //===========================================================================
        //==================================  OTROS LISTENER  ======================================
        /**Evento de click o ingreso de datos en seccion de otros datos del integrante con guardado en automatico*/
        tvRiesgo.setOnClickListener(tvRiesgo_OnClick);
        etMontoRefinanciar.addTextChangedListener(new TextWatcher() {
            private final String PATTERN_MONTO_CREDITO  = "[0-9]+";
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
                etMontoRefinanciar.removeTextChangedListener(this);

                try {
                    int inilen, endlen;
                    inilen = Miscellaneous.GetStr(etMontoRefinanciar).length();
                    String v = s.toString().replace(String.valueOf(df.getDecimalFormatSymbols().getGroupingSeparator()), "");
                    Number n = df.parse(v);
                    int cp = etMontoRefinanciar.getSelectionStart();
                    if (hasFractionalPart) {
                        etMontoRefinanciar.setText(df.format(n));
                    } else {
                        etMontoRefinanciar.setText(dfnd.format(n));
                    }
                    endlen = Miscellaneous.GetStr(etMontoRefinanciar).length();
                    int sel = (cp + (endlen - inilen));
                    if (sel > 0 && sel <= Miscellaneous.GetStr(etMontoRefinanciar).length()) {
                        etMontoRefinanciar.setSelection(sel);
                    } else {
                        // place cursor at the end?
                        etMontoRefinanciar.setSelection(Miscellaneous.GetStr(etMontoRefinanciar).length() - 1);
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
                        etMontoRefinanciar.setError("La cantidad no corresponde a un monto válido");
                    }else{
                        Update("monto_refinanciar", TBL_OTROS_DATOS_INTEGRANTE_REN, s.toString().trim().replace(",",""), "id_integrante", id_integrante);
                    }
                }

                etMontoRefinanciar.addTextChangedListener(this);
            }
        });
        tvMedioContacto.setOnClickListener(tvMedioContacto_OnClick);
        tvEstadoCuenta.setOnClickListener(tvEstadoCuenta_OnClick);
        etEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable e) {
                if (e.length() > 0)
                    Update("email", TBL_OTROS_DATOS_INTEGRANTE_REN, e.toString().trim().toUpperCase(), "id_integrante", id_integrante);
            }
        });
        etCredSolicitado.addTextChangedListener(new TextWatcher() {
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
                etCredSolicitado.removeTextChangedListener(this);

                try {
                    int inilen, endlen;
                    inilen = etCredSolicitado.getText().length();
                    String v = s.toString().replace(String.valueOf(df.getDecimalFormatSymbols().getGroupingSeparator()), "");
                    Number n = df.parse(v);
                    int cp = etCredSolicitado.getSelectionStart();
                    if (hasFractionalPart) {
                        etCredSolicitado.setText(df.format(n));
                    } else {
                        etCredSolicitado.setText(dfnd.format(n));
                    }
                    endlen = etCredSolicitado.getText().length();
                    int sel = (cp + (endlen - inilen));
                    if (sel > 0 && sel <= etCredSolicitado.getText().length()) {
                        etCredSolicitado.setSelection(sel);
                    } else {
                        // place cursor at the end?
                        etCredSolicitado.setSelection(etCredSolicitado.getText().length() - 1);
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
                        tvCantidadLetra.setText("");
                        etCredSolicitado.setError("La cantidad no corresponde a un monto de crédito válido");
                        Update("monto_solicitado", TBL_OTROS_DATOS_INTEGRANTE_REN, "", "id_integrante", id_integrante);
                    }else{
                        Update("monto_solicitado", TBL_OTROS_DATOS_INTEGRANTE_REN, s.toString().trim().replace(",",""), "id_integrante", id_integrante);
                        tvCantidadLetra.setText((Miscellaneous.cantidadLetra(s.toString().replace(",","")).toUpperCase() + " PESOS MEXICANOS ").replace("  ", " "));
                    }
                }
                else{
                    tvCantidadLetra.setText("");
                    Update("monto_solicitado", TBL_OTROS_DATOS_INTEGRANTE_REN, "", "id_integrante", id_integrante);
                }

                etCredSolicitado.addTextChangedListener(this);
            }
        });
        ibFirmaCli.setOnClickListener(ibFirmaCli_OnClick);
        //===========================================================================
        //============== CROQUIS ==================================
        /**Evento de click del croquis del integrante cuando se seleccione casa de reuniones con guardado en automatico*/
        tvCasa.setOnClickListener(tvCasa_OnClick);
        tv1.setOnClickListener(tvCasa_OnClick);
        tv2.setOnClickListener(tvCasa_OnClick);
        tv3.setOnClickListener(tvCasa_OnClick);
        tvCTrasera.setOnClickListener(tvCasa_OnClick);
        tvFrontal.setOnClickListener(tvCasa_OnClick);
        tv7.setOnClickListener(tvCasa_OnClick);
        tv8.setOnClickListener(tvCasa_OnClick);
        tv9.setOnClickListener(tvCasa_OnClick);
        tvPrincipal.setOnClickListener(tvPrincipal_OnClick);
        tvTrasera.setOnClickListener(tvTrasera_OnClick);
        tvLateraUno.setOnClickListener(tvLateralUno_OnClick);
        tvLateraDos.setOnClickListener(tvLateralDos_OnClick);
        etReferencia.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable e) {
                if (e.length() > 0)
                    Update("referencias",TBL_CROQUIS_GPO_REN, e.toString().trim().toUpperCase(), "id_integrante", id_integrante);
                else
                    Update("referencias",TBL_CROQUIS_GPO_REN, "", "id_integrante", id_integrante);
            }
        });
        etCaracteristicasDomicilio.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable e) {
                if (e.length() > 0)
                    Update("caracteristicas_domicilio", TBL_CROQUIS_GPO_REN, e.toString().trim().toUpperCase(), "id_integrante", id_integrante);
                else
                    Update("caracteristicas_domicilio", TBL_CROQUIS_GPO_REN, "", "id_integrante", id_integrante);
            }
        });
        //====================================  DOCUMENTOS  ========================================
        /**Evento de click de datos del neogocio del integrante con guardado en automatico*/
        ibIneFrontal.setOnClickListener(ibIneFrontal_OnClick);
        ibIneReverso.setOnClickListener(ibIneReverso_OnClick);
        ibCurp.setOnClickListener(ibCurp_OnClick);
        ibComprobante.setOnClickListener(ibComprobante_OnClick);
        ibIneSelfie.setOnClickListener(ibIneSelfie_OnClick);
        //==========================================================================
        /**Evento de click para retroceder o avanzar en seccionesro*/
        btnContinuar0.setOnClickListener(btnContinuar0_OnClick);
        //btnContinuar1.setOnClickListener(btnContinuar1_OnClick);
        //btnContinuar2.setOnClickListener(btnContinuar2_OnClick);
        btnContinuar3.setOnClickListener(btnContinuar3_OnClick);
        btnContinuar4.setOnClickListener(btnContinuar4_OnClick);
        btnContinuar5.setOnClickListener(btnContinuar5_OnClick);
        //btnContinuar7.setOnClickListener(btnContinuar7_OnClick);
        btnContinuar8.setOnClickListener(btnContinuar8_OnClick);

        btnRegresar0.setOnClickListener(btnRegresar0_OnClick);
        //btnRegresar1.setOnClickListener(btnRegresar1_OnClick);
        //btnRegresar2.setOnClickListener(btnRegresar2_OnClick);
        btnRegresar3.setOnClickListener(btnRegresar3_OnClick);
        btnRegresar4.setOnClickListener(btnRegresar4_OnClick);
        //btnRegresar5.setOnClickListener(btnRegresar5_OnClick);
        //btnRegresar7.setOnClickListener(btnRegresar7_OnClick);
        btnRegresar8.setOnClickListener(btnRegresar8_OnClick);
        btnRegresar6.setOnClickListener(btnRegresar6_OnClick);

        //================================  CLIENTE GENERO LISTENER  ===============================
        rgGeneroCli.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                tvGeneroCli.setError(null);
                HashMap<Integer, String> params = new HashMap<>();
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

                    Update("genero", TBL_INTEGRANTES_GPO_REN, "0", "id", id_integrante);
                    etCurpCli.setText(Miscellaneous.GenerarCurp(params));
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

                    Update("genero", TBL_INTEGRANTES_GPO_REN, "1", "id", id_integrante);

                    etCurpCli.setText(Miscellaneous.GenerarCurp(params));
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

                    etCurpCli.setText(Miscellaneous.GenerarCurp(params));
                }
            }
        });

        rgBienes.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                tvBienes.setError(null);
                switch (checkedId){
                    case R.id.rbMancomunados:
                        Update("bienes", TBL_INTEGRANTES_GPO_REN, "1", "id", id_integrante);
                        break;
                    case R.id.rbSeparados:
                        Update("bienes", TBL_INTEGRANTES_GPO_REN, "2", "id", id_integrante);
                        break;
                }
            }
        });

        rgEstatus.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                tvEstatus.setError(null);
                switch (checkedId){
                    case R.id.rbNuevo:
                        Update("estatus_integrante", TBL_OTROS_DATOS_INTEGRANTE_REN, "1", "id_integrante", id_integrante);
                        break;
                    case R.id.rbRenovado:
                        Update("estatus_integrante", TBL_OTROS_DATOS_INTEGRANTE_REN, "2", "id_integrante", id_integrante);
                        break;
                    case R.id.rbCambio:
                        Update("estatus_integrante", TBL_OTROS_DATOS_INTEGRANTE_REN, "3", "id_integrante", id_integrante);
                        break;
                }
            }
        });

        cbCasaReuniones.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                cbCasaReuniones.setError(null);
                if (isChecked) {
                    llCroquis.setVisibility(View.VISIBLE);
                    Update("casa_reunion", TBL_OTROS_DATOS_INTEGRANTE_REN, "1", "id_integrante", id_integrante);
                }
                else{
                    llCroquis.setVisibility(View.GONE);
                    Update("casa_reunion", TBL_OTROS_DATOS_INTEGRANTE_REN, "0", "id_integrante", id_integrante);
                }
            }
        });

        //=============================  POLITICAS LISTENER  =======================================
        /**Evento de click para capturar la informacion de PLD*/
        rgPropietarioReal.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (is_edit) {
                    tvPropietarioReal.setError(null);
                    switch (checkedId) {
                        case R.id.rbSiPropietario:
                            tvAnexoPropietario.setVisibility(View.VISIBLE);
                            Update("propietario_real", TBL_POLITICAS_PLD_INTEGRANTE_REN, "1", "id_integrante", id_integrante);
                            break;
                        case R.id.rbNoPropietario:
                            tvAnexoPropietario.setVisibility(View.GONE);
                            Update("propietario_real", TBL_POLITICAS_PLD_INTEGRANTE_REN, "2", "id_integrante", id_integrante);
                            break;
                    }
                }
            }
        });
        rgProveedor.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (is_edit) {
                    tvProvedor.setError(null);
                    switch (checkedId) {
                        case R.id.rbSiProveedor:
                            tvAnexoPreveedor.setVisibility(View.VISIBLE);
                            Update("proveedor_recursos", TBL_POLITICAS_PLD_INTEGRANTE_REN, "1", "id_integrante", id_integrante);
                            break;
                        case R.id.rbNoProveedor:
                            tvAnexoPreveedor.setVisibility(View.GONE);
                            Update("proveedor_recursos", TBL_POLITICAS_PLD_INTEGRANTE_REN, "2", "id_integrante", id_integrante);
                            break;
                    }
                }
            }
        });
        rgPoliticamenteExp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (is_edit) {
                    tvPoliticamenteExp.setError(null);
                    switch (checkedId) {
                        case R.id.rbSiExpuesta:
                            tvAnexoPoliticamenteExp.setVisibility(View.VISIBLE);
                            Update("persona_politica", TBL_POLITICAS_PLD_INTEGRANTE_REN, "1", "id_integrante", id_integrante);
                            break;
                        case R.id.rbNoexpuesta:
                            tvAnexoPoliticamenteExp.setVisibility(View.GONE);
                            Update("persona_politica", TBL_POLITICAS_PLD_INTEGRANTE_REN, "2", "id_integrante", id_integrante);
                            break;
                    }
                }
            }
        });

        //=============================  IMAGENES LISTENER  ========================================
        /**Evento de click para volver a capturar fotografias o firmar*/
        ivFotoFachCli.setOnClickListener(ivFotoFachCli_OnClick);
        ivFotoFachNeg.setOnClickListener(ivFotoFachNeg_OnClick);
        ivFirmaCli.setOnClickListener(ivFirmaCli_OnClick);
        ivIneFrontal.setOnClickListener(ivIneFrontal_OnClick);
        ivIneReverso.setOnClickListener(ivIneReverso_OnClick);
        ivCurp.setOnClickListener(ivCurp_OnClick);
        ivComprobante.setOnClickListener(ivComprobante_OnClick);
        ivIneSelfie.setOnClickListener(ivIneSelfie_OnClik);
        //&& !isNuevo
        //if(is_edit) {
        if(true) {
            tvCargo.setBackground(ContextCompat.getDrawable(ctx, (R.drawable.et_rounded_edges)));

            tvCargo.setOnClickListener(v -> {
                openRegistroIntegrante(id_credito, id_integrante);
            });
        }
    }

    //========================  ACTION LINEAR LAYOUT  ==============================================
    /**Evento de click a los contenedores de las secciones para mostrar u ocultar la secccion*/
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
            /*if (ivDown2.getVisibility() == View.VISIBLE && ivUp2.getVisibility() == View.GONE){
                ivDown2.setVisibility(View.GONE);
                ivUp2.setVisibility(View.VISIBLE);
                llDatosTelefonicos.setVisibility(View.VISIBLE);
            }
            else if (ivDown2.getVisibility() == View.GONE && ivUp2.getVisibility() == View.VISIBLE){
                ivDown2.setVisibility(View.VISIBLE);
                ivUp2.setVisibility(View.GONE);
                llDatosTelefonicos.setVisibility(View.GONE);
            }*/
        }
    };

    private View.OnClickListener llDomicilio_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            /*if (ivDown3.getVisibility() == View.VISIBLE && ivUp3.getVisibility() == View.GONE){
                ivDown3.setVisibility(View.GONE);
                ivUp3.setVisibility(View.VISIBLE);
                llDatosDomicilio.setVisibility(View.VISIBLE);
            }
            else if (ivDown3.getVisibility() == View.GONE && ivUp3.getVisibility() == View.VISIBLE){
                ivDown3.setVisibility(View.VISIBLE);
                ivUp3.setVisibility(View.GONE);
                llDatosDomicilio.setVisibility(View.GONE);
            }*/
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
        public void onClick(View v) {/*
            if (ivDown7.getVisibility() == View.VISIBLE && ivUp7.getVisibility() == View.GONE){
                ivDown7.setVisibility(View.GONE);
                ivUp7.setVisibility(View.VISIBLE);
                llDatosCroquis.setVisibility(View.VISIBLE);
            }
            else if (ivDown7.getVisibility() == View.GONE && ivUp7.getVisibility() == View.VISIBLE){
                ivDown7.setVisibility(View.VISIBLE);
                ivUp7.setVisibility(View.GONE);
                llDatosCroquis.setVisibility(View.GONE);
            }*/
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

    private View.OnClickListener llDocumentos_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (ivDown9.getVisibility() == View.VISIBLE && ivUp9.getVisibility() == View.GONE){
                ivDown9.setVisibility(View.GONE);
                ivUp9.setVisibility(View.VISIBLE);
                llDatosDocumentos.setVisibility(View.VISIBLE);
            }
            else if (ivDown9.getVisibility() == View.GONE && ivUp9.getVisibility() == View.VISIBLE){
                ivDown9.setVisibility(View.VISIBLE);
                ivUp9.setVisibility(View.GONE);
                llDatosDocumentos.setVisibility(View.GONE);
            }
        }
    };

    //==============================================================================================
    //============================ ACTION PERSONALES  ==============================================
    /**Evento para capturar la fecha de nacimiento del cliente*/
    private View.OnClickListener tvFechaNacCli_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (is_edit) {
                dialog_date_picker dialogDatePicker = new dialog_date_picker();
                Bundle b = new Bundle();

                b.putInt(Constants.YEAR_CURRENT, ((tvFechaNacCli.getText().toString().isEmpty())?myCalendar.get(Calendar.YEAR):Integer.parseInt(tvFechaNacCli.getText().toString().substring(0,4))));
                b.putInt(Constants.MONTH_CURRENT, ((tvFechaNacCli.getText().toString().isEmpty())?myCalendar.get(Calendar.MONTH):(Integer.parseInt(tvFechaNacCli.getText().toString().substring(5,7))-1)));
                b.putInt(Constants.DAY_CURRENT, ((tvFechaNacCli.getText().toString().isEmpty())?myCalendar.get(Calendar.DAY_OF_MONTH):Integer.parseInt(tvFechaNacCli.getText().toString().substring(8,10))));
                b.putString(Constants.DATE_CURRENT, sdf.format(myCalendar.getTime()));
                b.putInt(Constants.IDENTIFIER, 18);
                b.putBoolean(Constants.FECHAS_POST, false);
                dialogDatePicker.setArguments(b);
                dialogDatePicker.show(getSupportFragmentManager(), NameFragments.DIALOGDATEPICKER);
            }
        }
    };
    /**evento de click para capturar el estado de nacimiento del cliente*/
    private View.OnClickListener tvEstadoNacCli_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (is_edit && isNuevo) {
                Intent i_estados = new Intent(ctx, Catalogos.class);
                i_estados.putExtra(TITULO, Miscellaneous.ucFirst(Constants.ESTADOS));
                i_estados.putExtra(CATALOGO, Constants.ESTADOS);
                i_estados.putExtra(REQUEST_CODE, Constants.REQUEST_CODE_ESTADO_NAC);
                startActivityForResult(i_estados, Constants.REQUEST_CODE_ESTADO_NAC);
            }
        }
    };
    /**Evento de click para seleccionar el tipo de identificacion del cliente*/
    private View.OnClickListener tvTipoIdentificacion_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (is_edit) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
                builder.setTitle(R.string.selected_option)
                        .setItems(_tipo_identificacion, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int position) {
                                tvTipoIdentificacion.setError(null);
                                tvTipoIdentificacion.setText(_tipo_identificacion[position]);
                                Update("tipo_identificacion", TBL_INTEGRANTES_GPO_REN, _tipo_identificacion[position], "id", id_integrante);
                            }
                        });
                builder.create();
                builder.show();
            }
        }
    };
    /**Evento de click para seleccionar el nivel de estudios del cliente*/
    private View.OnClickListener tvEstudiosCli_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (is_edit) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
                builder.setTitle(R.string.selected_option)
                        .setItems(_estudios, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int position) {
                                tvEstudiosCli.setError(null);
                                tvEstudiosCli.setText(_estudios[position]);
                                tvEstudiosCli.requestFocus();
                                Update("nivel_estudio", TBL_INTEGRANTES_GPO_REN, _estudios[position], "id", id_integrante);
                            }
                        });
                builder.create();
                builder.show();
            }
        }
    };
    /**Evento de click para seleccionar la ocupacion del cliente*/
    private View.OnClickListener tvOcupacionCli_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (is_edit) {
                Intent i_ocupaciones = new Intent(ctx, Catalogos.class);
                i_ocupaciones.putExtra(TITULO, Miscellaneous.ucFirst(OCUPACIONES));
                i_ocupaciones.putExtra(CATALOGO, OCUPACIONES);
                i_ocupaciones.putExtra(REQUEST_CODE, Constants.REQUEST_CODE_OCUPACION_CLIE);
                startActivityForResult(i_ocupaciones, Constants.REQUEST_CODE_OCUPACION_CLIE);
            }
        }
    };
    /**Evento de click para seleccionar el estado civil del cliente para mostrar u ocultar la seccion del conyuge*/
    private View.OnClickListener tvEstadoCivilCli_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (is_edit) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
                builder.setTitle(R.string.selected_option)
                        .setItems(_civil, new DialogInterface.OnClickListener() {
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
                                Update("estado_civil", TBL_INTEGRANTES_GPO_REN, _civil[position], "id", id_integrante);
                            }
                        });
                builder.create();
                builder.show();
            }
        }
    };
    //==============================================================================================
    //============================ ACTION DOMICILIO  ===============================================
    /**Evento de click para obtener la ubicacion del domicilio del cliente*/
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

    /**evento para seleccionar la colonia del cliente*/
    private View.OnClickListener etColonia_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (is_edit || tvColoniaCli.getText().toString().trim().isEmpty()) {
                Intent i_colonia = new Intent(ctx, Catalogos.class);
                i_colonia.putExtra(TITULO, Miscellaneous.ucFirst("Colonias"));
                i_colonia.putExtra(CATALOGO, COLONIAS);
                i_colonia.putExtra(EXTRA, etCpCli.getText().toString().trim());
                i_colonia.putExtra(REQUEST_CODE, Constants.REQUEST_CODE_COLONIA_CLIE);
                startActivityForResult(i_colonia, Constants.REQUEST_CODE_COLONIA_CLIE);
            }
        }
    };

    /**Evento de click para seleccionar la localidad del cliente*/
    private View.OnClickListener tvLocalidadCli_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //if (is_edit || tvLocalidadCli.getText().toString().trim().isEmpty()) {
            if(true)
            {
                ColoniaDao coloniaDao = new ColoniaDao(ctx);
                List<Colonia> colonias = coloniaDao.findAllByCp(etCpCli.getText().toString().trim());

                if (!tvMunicipioCli.getText().toString().trim().isEmpty() && colonias.size() > 0) {
                    Cursor row = dBhelper.getRecords(TABLE_MUNICIPIOS, " WHERE municipio_id = ?", "", new String[]{String.valueOf(colonias.get(0).getMunicipioId())});
                    row.moveToFirst();
                    Intent i_localidad = new Intent(ctx, Catalogos.class);
                    i_localidad.putExtra(TITULO, Miscellaneous.ucFirst(LOCALIDADES));
                    i_localidad.putExtra(CATALOGO, LOCALIDADES);
                    i_localidad.putExtra(EXTRA, row.getString(1));
                    i_localidad.putExtra(REQUEST_CODE, REQUEST_CODE_LOCALIDAD_CLIE);
                    startActivityForResult(i_localidad, REQUEST_CODE_LOCALIDAD_CLIE);
                }
                else{
                    final AlertDialog solicitud = Popups.showDialogMessage(ctx, warning,
                            "Seleccione el municipio del cliente", R.string.accept, new Popups.DialogMessage() {
                                @Override
                                public void OnClickListener(AlertDialog dialog) {
                                    dialog.dismiss();
                                }
                            });
                    solicitud.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                    solicitud.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                    solicitud.show();
                }
            }
        }
    };

    /**Evento de click para seleccionar el tipo de vivienda del cliente*/
    private View.OnClickListener tvTipoCasaCli_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (is_edit) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
                builder.setTitle(R.string.selected_option)
                        .setItems(_tipo_casa, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int position) {
                                tvTipoCasaCli.setError(null);
                                tvTipoCasaCli.setText(_tipo_casa[position]);
                                Update("tipo_vivienda", TBL_DOMICILIO_INTEGRANTE_REN, _tipo_casa[position], "id_integrante", id_integrante);
                                switch (position) {
                                    case 1:
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

    /**Evento de click para seleccionar el parentesco de la vivienda con el cliente*/
    private View.OnClickListener tvCasaFamiliar_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (is_edit) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
                builder.setTitle(R.string.selected_option)
                        .setItems(_parentesco, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int position) {
                                tvCasaFamiliar.setError(null);
                                tvCasaFamiliar.setText(_parentesco[position]);
                                Update("parentesco", TBL_DOMICILIO_INTEGRANTE_REN, _parentesco[position], "id_integrante", id_integrante);
                            }
                        });
                builder.create();
                builder.show();
            }
        }
    };

    /**Evento de click para seleccionar el numero de dependientes del cliente*/
    private View.OnClickListener tvDependientes_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (is_edit) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
                builder.setTitle(R.string.selected_option)
                        .setItems(R.array.dependientes_eco, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int position) {
                                tvDependientes.setError(null);
                                tvDependientes.setText(_dependientes[position]);
                                Update("dependientes", TBL_DOMICILIO_INTEGRANTE_REN, tvDependientes.getText().toString().trim().toUpperCase(),"id_integrante", id_integrante);
                            }
                        });
                builder.create();
                builder.show();
            }
        }
    };

    /**Evento de click para capturar una fotografia de fachada del cliente*/
    private View.OnClickListener ibFotoFachCli_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i = new Intent(RenovarIntegrante.this, CameraVertical.class);
            i.putExtra(Constants.ORDER_ID, "SC_cliente");
            startActivityForResult(i, Constants.REQUEST_CODE_CAMARA_FACHADA_CLI);
        }
    };
    //==============================================================================================
    //============================ ACTION NEGOCIO  =================================================
    /**Evento de click para obtener la ubicacion del negocio*/
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

    /**Evento de click para seleccionar la colonia del negocio*/
    private View.OnClickListener tvColoniaNeg_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (is_edit || tvColoniaNeg.getText().toString().trim().isEmpty()) {
                Intent i_colonia = new Intent(ctx, Catalogos.class);
                i_colonia.putExtra(TITULO, Miscellaneous.ucFirst("Colonias"));
                i_colonia.putExtra(CATALOGO, COLONIAS);
                i_colonia.putExtra(EXTRA, etCpNeg.getText().toString().trim());
                i_colonia.putExtra(REQUEST_CODE, Constants.REQUEST_CODE_COLONIA_NEG);
                startActivityForResult(i_colonia, Constants.REQUEST_CODE_COLONIA_NEG);
            }
        }
    };

    /**Evento de click para seleccionar la localidad del negocio*/
    private View.OnClickListener tvLocalidadNeg_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //if (is_edit || tvLocalidadNeg.getText().toString().trim().isEmpty()) {
            if(true)
            {
                ColoniaDao coloniaDao = new ColoniaDao(ctx);
                List<Colonia> colonias = coloniaDao.findAllByCp(etCpNeg.getText().toString().trim());

                if (!tvMunicipioNeg.getText().toString().trim().isEmpty() && colonias.size() > 0) {
                    Cursor row = dBhelper.getRecords(TABLE_MUNICIPIOS, " WHERE municipio_id = ?", "", new String[]{String.valueOf(colonias.get(0).getMunicipioId())});
                    row.moveToFirst();
                    Intent i_localidad = new Intent(ctx, Catalogos.class);
                    i_localidad.putExtra(TITULO, Miscellaneous.ucFirst(LOCALIDADES));
                    i_localidad.putExtra(CATALOGO, LOCALIDADES);
                    i_localidad.putExtra(EXTRA, row.getString(1));
                    i_localidad.putExtra(REQUEST_CODE, REQUEST_CODE_LOCALIDAD_NEG);
                    startActivityForResult(i_localidad, REQUEST_CODE_LOCALIDAD_NEG);
                }
                else{
                    final AlertDialog solicitud = Popups.showDialogMessage(ctx, warning,
                            "Seleccione el municipio del cliente", R.string.accept, new Popups.DialogMessage() {
                                @Override
                                public void OnClickListener(AlertDialog dialog) {
                                    dialog.dismiss();
                                }
                            });
                    solicitud.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                    solicitud.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                    solicitud.show();
                }
            }
        }
    };

    /**Evento de click para seleccionar el destino del credito*/
    private View.OnClickListener tvDestinoNeg_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (is_edit) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
                builder.setTitle(R.string.selected_option)
                        .setItems(_destino_credito, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int position) {
                                tvDestinoNeg.setError(null);
                                tvDestinoNeg.setText(_destino_credito[position]);
                                Update("destino_credito", TBL_NEGOCIO_INTEGRANTE_REN, _destino_credito[position], "id_integrante", id_integrante);

                                if (position == 0){
                                    etOtroDestinoNeg.setVisibility(View.GONE);
                                    etOtroDestinoNeg.setText("");
                                    Update("otro_destino_credito", TBL_NEGOCIO_INTEGRANTE_REN, "", "id_integrante", id_integrante);
                                }
                                else{
                                    etOtroDestinoNeg.setVisibility(View.VISIBLE);
                                }
                            }
                        });
                builder.create();
                builder.show();
            }
        }
    };

    /**Evento de click para seleccionar los medios de pago que puede hacer el cliente*/
    private View.OnClickListener tvMediosPagoNeg_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (is_edit) {
                selectedItemsMediosPago = new ArrayList<>();
                new AlertDialog.Builder(ctx)
                        .setTitle("Medios de Pago")
                        .setMultiChoiceItems(_medios_pago, null, new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                if (isChecked) {
                                    selectedItemsMediosPago.add(which);
                                } else if (selectedItemsMediosPago.contains(which)) {
                                    selectedItemsMediosPago.remove(Integer.valueOf(which));
                                }
                            }
                        })
                        .setPositiveButton(R.string.accept, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                String medios = "";
                                Collections.sort(selectedItemsMediosPago);
                                for (int i = 0; i < selectedItemsMediosPago.size(); i++){
                                    if(i == 0)
                                        medios += _medios_pago[selectedItemsMediosPago.get(i)];
                                    else
                                        medios += ", "+_medios_pago[selectedItemsMediosPago.get(i)];
                                }
                                tvMediosPagoNeg.setError(null);
                                tvMediosPagoNeg.setText(medios);
                                Update("medios_pago", TBL_NEGOCIO_INTEGRANTE_REN, tvMediosPagoNeg.getText().toString().trim().toUpperCase(), "id_integrante", id_integrante);

                                if (tvMediosPagoNeg.getText().toString().trim().toUpperCase().contains("EFECTIVO")){
                                    llOperacionesEfectivo.setVisibility(View.VISIBLE);
                                }
                                else{
                                    llOperacionesEfectivo.setVisibility(View.GONE);
                                }

                                if (tvMediosPagoNeg.getText().toString().trim().toUpperCase().contains("OTRO")){
                                    etOtroMedioPagoNeg.setVisibility(View.VISIBLE);
                                }
                                else{
                                    etOtroMedioPagoNeg.setVisibility(View.GONE);
                                }
                            }
                        })
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {

                            }
                        }).show();
            }
        }
    };

    /**Evento de click para seleccionar la actividad del negocio*/
    private View.OnClickListener tvActEcoEspNeg_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (is_edit) {
                Intent i_ocupaciones = new Intent(ctx, Catalogos.class);
                i_ocupaciones.putExtra(TITULO, Miscellaneous.ucFirst(OCUPACIONES));
                i_ocupaciones.putExtra(CATALOGO, OCUPACIONES);
                i_ocupaciones.putExtra(REQUEST_CODE, REQUEST_CODE_OCUPACION_NEG);
                startActivityForResult(i_ocupaciones, REQUEST_CODE_OCUPACION_NEG);
            }
        }
    };

    /**Evento de click para capturar la fotografia de fachada del negocio*/
    private View.OnClickListener ibFotoFachNeg_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i = new Intent(RenovarIntegrante.this, CameraVertical.class);
            i.putExtra(Constants.ORDER_ID, "SC_negocio");
            startActivityForResult(i, Constants.REQUEST_CODE_CAMARA_FACHADA_NEG);
        }
    };

    private View.OnClickListener cbNegEnDomCli_OnCheck = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(cbNegEnDomCli.isChecked())
            {
                //PRELLENAR DATOS
                locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
                    Toast.makeText(ctx, "El GPS se encuentra desactivado", Toast.LENGTH_SHORT).show();
                else {
                    Update("ubicado_en_dom_cli", TBL_NEGOCIO_INTEGRANTE_REN, "SI", "id_integrante", id_integrante);
                    ObtenerUbicacionNeg();
                    CopiarDatosDeClienteHaciaNegocio();
                }
            }
            else
            {
                //LIMPIAR DATOS
                Update("ubicado_en_dom_cli", TBL_NEGOCIO_INTEGRANTE_REN, "NO", "id_integrante", id_integrante);
                LimpiarDatosNegocio();
            }
        }
    };

    private View.OnClickListener tvNumOperacionNeg_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (is_edit) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
                builder.setTitle(R.string.selected_option)
                        .setItems(R.array.max_pagos_mes_gpo, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int position) {
                                tvNumOperacionNeg.setError(null);
                                tvNumOperacionNeg.setText(_max_pagos_mes[position]);
                                Update("num_ope_mensuales", TBL_NEGOCIO_INTEGRANTE_REN, Miscellaneous.GetStr(tvNumOperacionNeg), "id_integrante", id_integrante);
                            }
                        });
                builder.create();
                builder.show();
            }
        }
    };

    private View.OnClickListener etNumOperacionEfectNeg_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (is_edit) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
                builder.setTitle(R.string.selected_option)
                        .setItems(R.array.max_pagos_mes_gpo, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int position) {
                                etNumOperacionEfectNeg.setError(null);
                                etNumOperacionEfectNeg.setText(_max_pagos_mes[position]);
                                Update("num_ope_mensuales_efectivo", TBL_NEGOCIO_INTEGRANTE_REN, Miscellaneous.GetStr(etNumOperacionEfectNeg), "id_integrante", id_integrante);
                            }
                        });
                builder.create();
                builder.show();
            }
        }
    };

    //==============================================================================================
    //============================ ACTION CONYUGE  =================================================
    /**Evento de click para seleccionar la ocupacion del conyuge*/
    private View.OnClickListener tvOcupacionCony_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (is_edit) {
                Intent i_ocupaciones = new Intent(ctx, Catalogos.class);
                i_ocupaciones.putExtra(TITULO, Miscellaneous.ucFirst(OCUPACIONES));
                i_ocupaciones.putExtra(CATALOGO, OCUPACIONES);
                i_ocupaciones.putExtra(REQUEST_CODE, Constants.REQUEST_CODE_OCUPACION_CONY);
                startActivityForResult(i_ocupaciones, Constants.REQUEST_CODE_OCUPACION_CONY);
            }
        }
    };

    /**Evento de click para seleccionar la colonia del conyuge*/
    private View.OnClickListener tvColoniaCony_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (is_edit || tvColoniaCony.getText().toString().trim().isEmpty()) {
                Intent i_colonia = new Intent(ctx, Catalogos.class);
                i_colonia.putExtra(TITULO, Miscellaneous.ucFirst("Colonias"));
                i_colonia.putExtra(CATALOGO, COLONIAS);
                i_colonia.putExtra(EXTRA, etCpCony.getText().toString().trim());
                i_colonia.putExtra(REQUEST_CODE, REQUEST_CODE_COLONIA_CONY);
                startActivityForResult(i_colonia, REQUEST_CODE_COLONIA_CONY);
            }
        }
    };

    /**Evento de click para seleccionar la localidad del conyuge*/
    private View.OnClickListener tvLocalidadCony_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //if (is_edit || tvLocalidadCony.getText().toString().trim().isEmpty()) {
            if(true)
            {
                ColoniaDao coloniaDao = new ColoniaDao(ctx);
                List<Colonia> colonias = coloniaDao.findAllByCp(etCpCony.getText().toString().trim());

                if (!tvMunicipioCony.getText().toString().trim().isEmpty() && colonias.size() > 0) {
                    Cursor row = dBhelper.getRecords(TABLE_MUNICIPIOS, " WHERE municipio_id = ?", "", new String[]{String.valueOf(colonias.get(0).getMunicipioId())});
                    row.moveToFirst();
                    Intent i_localidad = new Intent(ctx, Catalogos.class);
                    i_localidad.putExtra(TITULO, Miscellaneous.ucFirst(LOCALIDADES));
                    i_localidad.putExtra(CATALOGO, LOCALIDADES);
                    i_localidad.putExtra(EXTRA, row.getString(1));
                    i_localidad.putExtra(REQUEST_CODE, REQUEST_CODE_LOCALIDAD_CONY);
                    startActivityForResult(i_localidad, REQUEST_CODE_LOCALIDAD_CONY);
                }
                else{
                    final AlertDialog solicitud = Popups.showDialogMessage(ctx, warning,
                            "Seleccione el municipio del cliente", R.string.accept, new Popups.DialogMessage() {
                                @Override
                                public void OnClickListener(AlertDialog dialog) {
                                    dialog.dismiss();
                                }
                            });
                    solicitud.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                    solicitud.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                    solicitud.show();
                }
            }
        }
    };
    //==============================================================================================
    //============================ ACTION OTROS  ===================================================
    /**Evento de click para seleccionar el nivel del riesgo*/
    private View.OnClickListener tvRiesgo_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (is_edit) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
                builder.setTitle(R.string.selected_option)
                        .setItems(R.array.clasificacion_riesgo, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int position) {
                                tvRiesgo.setError(null);
                                tvRiesgo.setText(_riesgo[position]);
                                Update("clasificacion_riesgo", TBL_OTROS_DATOS_INTEGRANTE_REN, tvRiesgo.getText().toString().trim().toUpperCase(), "id_integrante", id_integrante);
                            }
                        });
                builder.create();
                builder.show();
            }
        }
    };

    /**Evento de click para seleccionar cual fue el medio de contacto*/
    private View.OnClickListener tvMedioContacto_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (is_edit) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
                builder.setTitle(R.string.selected_option)
                        .setItems(_medio_contacto, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int position) {
                                tvMedioContacto.setError(null);
                                tvMedioContacto.setText(_medio_contacto[position]);
                                Update("medio_contacto", TBL_OTROS_DATOS_INTEGRANTE_REN, _medio_contacto[position], "id_integrante", id_integrante);
                            }
                        });
                builder.create();
                builder.show();
            }
        }
    };
    /**Evento de click para seleccionar si tiene estado de cuenta*/
    private View.OnClickListener tvEstadoCuenta_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (is_edit) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
                builder.setTitle(R.string.selected_option)
                        .setItems(R.array.confirmacion, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int position) {
                                tvEstadoCuenta.setError(null);
                                tvEstadoCuenta.setText(_confirmacion[position]);
                                Update("estado_cuenta", TBL_OTROS_DATOS_INTEGRANTE_REN, tvEstadoCuenta.getText().toString().trim().toUpperCase(), "id_integrante", id_integrante);
                            }
                        });
                builder.create();
                builder.show();
            }
        }
    };
    /**Evento de click para capturar la firma del cliente*/
    private View.OnClickListener ibFirmaCli_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i_firma_cli = new Intent(ctx, CapturarFirma.class);
            i_firma_cli.putExtra(TIPO,"CLIENTE");
            startActivityForResult(i_firma_cli, REQUEST_CODE_FIRMA_CLI);
        }
    };
    //======================  CROQUIS  ================================
    private View.OnClickListener tvCasa_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

        }
    };
    /**Evento de click para capturar el nombre de la calle donde vive el cliente*/
    private View.OnClickListener tvPrincipal_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (is_edit){
                dialog_input_calle dlgInput = new dialog_input_calle();
                Bundle b = new Bundle();
                b.putString(TIPO, "PRINCIPAL");
                b.putString(CALLE, tvPrincipal.getText().toString().trim().toUpperCase());
                b.putString(TIPO_SOLICITUD, "GRUPAL RENOVACION");
                dlgInput.setArguments(b);
                dlgInput.show(getSupportFragmentManager(), NameFragments.DIALOGINPUTCALLE);
            }
        }
    };
    /**Evento de click para capturar el nombre de la calle trasera el cliente*/
    private View.OnClickListener tvTrasera_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (is_edit){
                dialog_input_calle dlgInput = new dialog_input_calle();
                Bundle b = new Bundle();
                b.putString(TIPO, "TRASERA");
                b.putString(CALLE, tvTrasera.getText().toString().trim().toUpperCase());
                b.putString(TIPO_SOLICITUD, "GRUPAL RENOVACION");
                dlgInput.setArguments(b);
                dlgInput.show(getSupportFragmentManager(), NameFragments.DIALOGINPUTCALLE);
            }
        }
    };
    /**Evento de click para capturar el nombre de la calle de la derecha donde vive el cliente*/
    private View.OnClickListener tvLateralUno_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (is_edit){
                dialog_input_calle dlgInput = new dialog_input_calle();
                Bundle b = new Bundle();
                b.putString(TIPO, "LATERAL UNO");
                b.putString(CALLE, tvLateraUno.getText().toString().trim().toUpperCase());
                b.putString(TIPO_SOLICITUD, "GRUPAL RENOVACION");
                dlgInput.setArguments(b);
                dlgInput.show(getSupportFragmentManager(), NameFragments.DIALOGINPUTCALLE);
            }
        }
    };
    /**Evento de click para capturar el nombre de la calle de la izquierda donde vive el cliente*/
    private View.OnClickListener tvLateralDos_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (is_edit){
                dialog_input_calle dlgInput = new dialog_input_calle();
                Bundle b = new Bundle();
                b.putString(TIPO, "LATERAL DOS");
                b.putString(CALLE, tvLateraDos.getText().toString().trim().toUpperCase());
                b.putString(TIPO_SOLICITUD, "GRUPAL RENOVACION");
                dlgInput.setArguments(b);
                dlgInput.show(getSupportFragmentManager(), NameFragments.DIALOGINPUTCALLE);
            }
        }
    };
    //==============================================================================================
    //================================== ACTION DOCUMENTOS =========================================
    private View.OnClickListener ibIneSelfie_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i = new Intent(RenovarIntegrante.this, CameraVertical.class);
            i.putExtra(ORDER_ID, "O_ine_selfie");
            startActivityForResult(i, REQUEST_CODE_FOTO_INE_SELFIE);
        }
    };

    /**Evento para capturar la fotografia del ine/ife de la parte frontal*/
    private View.OnClickListener ibIneFrontal_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent scanIntent = new Intent(RenovarIntegrante.this, CardIOActivity.class);
            scanIntent.putExtra(CardIOActivity.EXTRA_SUPPRESS_SCAN,true); // supmit cuando termine de reconocer el documento
            scanIntent.putExtra(CardIOActivity.EXTRA_SUPPRESS_MANUAL_ENTRY,true); // esconder teclado
            scanIntent.putExtra(CardIOActivity.EXTRA_USE_CARDIO_LOGO,true); // cambiar logo de paypal por el de card.io
            scanIntent.putExtra(CardIOActivity.EXTRA_RETURN_CARD_IMAGE,true); // capture img
            scanIntent.putExtra(CardIOActivity.EXTRA_CAPTURED_CARD_IMAGE,true); // capturar img

            // laszar activity
            startActivityForResult(scanIntent, REQUEST_CODE_FOTO_INE_FRONTAL);
        }
    };

    /**Evento para capturar la fotografia del ine/ife de la parte reverso*/
    private View.OnClickListener ibIneReverso_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent scanIntent = new Intent(RenovarIntegrante.this, CardIOActivity.class);
            scanIntent.putExtra(CardIOActivity.EXTRA_SUPPRESS_SCAN,true); // supmit cuando termine de reconocer el documento
            scanIntent.putExtra(CardIOActivity.EXTRA_SUPPRESS_MANUAL_ENTRY,true); // esconder teclado
            scanIntent.putExtra(CardIOActivity.EXTRA_USE_CARDIO_LOGO,true); // cambiar logo de paypal por el de card.io
            scanIntent.putExtra(CardIOActivity.EXTRA_RETURN_CARD_IMAGE,true); // capture img
            scanIntent.putExtra(CardIOActivity.EXTRA_CAPTURED_CARD_IMAGE,true); // capturar img

            // laszar activity
            startActivityForResult(scanIntent, REQUEST_CODE_FOTO_INE_REVERSO);
        }
    };

    /**Evento para capturar la fotografia del curp*/
    private View.OnClickListener ibCurp_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i = new Intent(RenovarIntegrante.this, CameraVertical.class);
            i.putExtra(Constants.ORDER_ID, "SCG_Curp");
            startActivityForResult(i, REQUEST_CODE_FOTO_CURP);
        }
    };

    /**Evento para capturar la fotografia del comprobante de domicilio*/
    private View.OnClickListener ibComprobante_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i = new Intent(RenovarIntegrante.this, CameraVertical.class);
            i.putExtra(Constants.ORDER_ID, "SCG_Comprobante");
            startActivityForResult(i, REQUEST_CODE_FOTO_COMPROBATE);
        }
    };
    //==============================================================================================

    private View.OnClickListener ivFotoFachCli_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (is_edit){
                final AlertDialog evidencia_dlg = Popups.showDialogConfirmImage(ctx, Constants.question,
                        R.string.capturar_foto, R.string.fotografia, new Popups.DialogMessage() {
                            @Override
                            public void OnClickListener(AlertDialog dialog) {
                                Intent i = new Intent(ctx, CameraVertical.class);
                                startActivityForResult(i, Constants.REQUEST_CODE_CAMARA_FACHADA_CLI);
                                dialog.dismiss();

                            }
                        }, R.string.ver_imagen, new Popups.DialogMessage() {
                            @Override
                            public void OnClickListener(AlertDialog dialog) {
                                Intent i = new Intent(ctx, VerImagen.class);
                                i.putExtra(Constants.IMAGEN, byteFotoFachCli);
                                startActivity(i);
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
            else {
                final AlertDialog fachada_dlg = Popups.showDialogConfirm(ctx, Constants.question,
                        R.string.ver_fotografia, R.string.ver_imagen, new Popups.DialogMessage() {
                            @Override
                            public void OnClickListener(AlertDialog dialog) {
                                Intent i = new Intent(ctx, VerImagen.class);
                                i.putExtra(Constants.IMAGEN, byteFotoFachCli);
                                startActivity(i);
                                dialog.dismiss();

                            }
                        }, R.string.cancel, new Popups.DialogMessage() {
                            @Override
                            public void OnClickListener(AlertDialog dialog) {
                                dialog.dismiss();
                            }
                        });
                Objects.requireNonNull(fachada_dlg.getWindow()).requestFeature(Window.FEATURE_NO_TITLE);
                fachada_dlg.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                fachada_dlg.show();
            }
        }
    };
    private View.OnClickListener ivFotoFachNeg_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (is_edit){
                final AlertDialog evidencia_dlg = Popups.showDialogConfirmImage(ctx, Constants.question,
                        R.string.capturar_foto, R.string.fotografia, new Popups.DialogMessage() {
                            @Override
                            public void OnClickListener(AlertDialog dialog) {
                                Intent i = new Intent(ctx, CameraVertical.class);
                                startActivityForResult(i, Constants.REQUEST_CODE_CAMARA_FACHADA_NEG);
                                dialog.dismiss();

                            }
                        }, R.string.ver_imagen, new Popups.DialogMessage() {
                            @Override
                            public void OnClickListener(AlertDialog dialog) {
                                Intent i = new Intent(ctx, VerImagen.class);
                                i.putExtra(Constants.IMAGEN, byteFotoFachNeg);
                                startActivity(i);
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
            else {
                final AlertDialog fachada_dlg = Popups.showDialogConfirm(ctx, Constants.question,
                        R.string.ver_fotografia, R.string.ver_imagen, new Popups.DialogMessage() {
                            @Override
                            public void OnClickListener(AlertDialog dialog) {
                                Intent i = new Intent(ctx, VerImagen.class);
                                i.putExtra(Constants.IMAGEN, byteFotoFachNeg);
                                startActivity(i);
                                dialog.dismiss();

                            }
                        }, R.string.cancel, new Popups.DialogMessage() {
                            @Override
                            public void OnClickListener(AlertDialog dialog) {
                                dialog.dismiss();
                            }
                        });
                Objects.requireNonNull(fachada_dlg.getWindow()).requestFeature(Window.FEATURE_NO_TITLE);
                fachada_dlg.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                fachada_dlg.show();
            }
        }
    };
    private View.OnClickListener ivFirmaCli_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (is_edit){
                final AlertDialog firma_dlg = Popups.showDialogConfirm(ctx, firma,
                        R.string.capturar_nueva_firma, R.string.accept, new Popups.DialogMessage() {
                            @Override
                            public void OnClickListener(AlertDialog dialog) {
                                Intent sig = new Intent(ctx, CapturarFirma.class);
                                sig.putExtra(TIPO,"");
                                startActivityForResult(sig, REQUEST_CODE_FIRMA_CLI);
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
        }
    };
    private View.OnClickListener ivIneFrontal_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (is_edit){
                final AlertDialog evidencia_dlg = Popups.showDialogConfirmImage(ctx, Constants.question,
                        R.string.capturar_foto, R.string.fotografia, new Popups.DialogMessage() {
                            @Override
                            public void OnClickListener(AlertDialog dialog) {
                                Intent scanIntent = new Intent(RenovarIntegrante.this, CardIOActivity.class);
                                scanIntent.putExtra(CardIOActivity.EXTRA_SUPPRESS_SCAN,true); // supmit cuando termine de reconocer el documento
                                scanIntent.putExtra(CardIOActivity.EXTRA_SUPPRESS_MANUAL_ENTRY,true); // esconder teclado
                                scanIntent.putExtra(CardIOActivity.EXTRA_USE_CARDIO_LOGO,true); // cambiar logo de paypal por el de card.io
                                scanIntent.putExtra(CardIOActivity.EXTRA_RETURN_CARD_IMAGE,true); // capture img
                                scanIntent.putExtra(CardIOActivity.EXTRA_CAPTURED_CARD_IMAGE,true); // capturar img

                                // laszar activity
                                startActivityForResult(scanIntent, REQUEST_CODE_FOTO_INE_FRONTAL);
                                dialog.dismiss();

                            }
                        }, R.string.ver_imagen, new Popups.DialogMessage() {
                            @Override
                            public void OnClickListener(AlertDialog dialog) {
                                Intent i = new Intent(ctx, VerImagen.class);
                                i.putExtra(Constants.IMAGEN, byteIneFrontal);
                                startActivity(i);
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
                final AlertDialog fachada_dlg = Popups.showDialogConfirm(ctx, Constants.question,
                        R.string.ver_fotografia, R.string.ver_imagen, new Popups.DialogMessage() {
                            @Override
                            public void OnClickListener(AlertDialog dialog) {
                                Intent i = new Intent(ctx, VerImagen.class);
                                i.putExtra(Constants.IMAGEN, byteIneFrontal);
                                startActivity(i);
                                dialog.dismiss();

                            }
                        }, R.string.cancel, new Popups.DialogMessage() {
                            @Override
                            public void OnClickListener(AlertDialog dialog) {
                                dialog.dismiss();
                            }
                        });
                Objects.requireNonNull(fachada_dlg.getWindow()).requestFeature(Window.FEATURE_NO_TITLE);
                fachada_dlg.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                fachada_dlg.show();
            }
        }
    };
    private View.OnClickListener ivIneReverso_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (is_edit){
                final AlertDialog evidencia_dlg = Popups.showDialogConfirmImage(ctx, Constants.question,
                        R.string.capturar_foto, R.string.fotografia, new Popups.DialogMessage() {
                            @Override
                            public void OnClickListener(AlertDialog dialog) {
                                Intent scanIntent = new Intent(RenovarIntegrante.this, CardIOActivity.class);
                                scanIntent.putExtra(CardIOActivity.EXTRA_SUPPRESS_SCAN,true); // supmit cuando termine de reconocer el documento
                                scanIntent.putExtra(CardIOActivity.EXTRA_SUPPRESS_MANUAL_ENTRY,true); // esconder teclado
                                scanIntent.putExtra(CardIOActivity.EXTRA_USE_CARDIO_LOGO,true); // cambiar logo de paypal por el de card.io
                                scanIntent.putExtra(CardIOActivity.EXTRA_RETURN_CARD_IMAGE,true); // capture img
                                scanIntent.putExtra(CardIOActivity.EXTRA_CAPTURED_CARD_IMAGE,true); // capturar img

                                // laszar activity
                                startActivityForResult(scanIntent, REQUEST_CODE_FOTO_INE_REVERSO);
                                dialog.dismiss();

                            }
                        }, R.string.ver_imagen, new Popups.DialogMessage() {
                            @Override
                            public void OnClickListener(AlertDialog dialog) {
                                Intent i = new Intent(ctx, VerImagen.class);
                                i.putExtra(Constants.IMAGEN, byteIneReverso);
                                startActivity(i);
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
                final AlertDialog fachada_dlg = Popups.showDialogConfirm(ctx, Constants.question,
                        R.string.ver_fotografia, R.string.ver_imagen, new Popups.DialogMessage() {
                            @Override
                            public void OnClickListener(AlertDialog dialog) {
                                Intent i = new Intent(ctx, VerImagen.class);
                                i.putExtra(Constants.IMAGEN, byteIneReverso);
                                startActivity(i);
                                dialog.dismiss();

                            }
                        }, R.string.cancel, new Popups.DialogMessage() {
                            @Override
                            public void OnClickListener(AlertDialog dialog) {
                                dialog.dismiss();
                            }
                        });
                Objects.requireNonNull(fachada_dlg.getWindow()).requestFeature(Window.FEATURE_NO_TITLE);
                fachada_dlg.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                fachada_dlg.show();
            }
        }
    };
    private View.OnClickListener ivCurp_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (is_edit && isNuevo){
                final AlertDialog evidencia_dlg = Popups.showDialogConfirmImage(ctx, Constants.question,
                        R.string.capturar_foto, R.string.fotografia, new Popups.DialogMessage() {
                            @Override
                            public void OnClickListener(AlertDialog dialog) {
                                Intent i = new Intent(ctx, CameraVertical.class);
                                startActivityForResult(i, REQUEST_CODE_FOTO_CURP);
                                dialog.dismiss();

                            }
                        }, R.string.ver_imagen, new Popups.DialogMessage() {
                            @Override
                            public void OnClickListener(AlertDialog dialog) {
                                Intent i = new Intent(ctx, VerImagen.class);
                                i.putExtra(Constants.IMAGEN, byteCurp);
                                startActivity(i);
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
            else {
                final AlertDialog fachada_dlg = Popups.showDialogConfirm(ctx, Constants.question,
                        R.string.ver_fotografia, R.string.ver_imagen, new Popups.DialogMessage() {
                            @Override
                            public void OnClickListener(AlertDialog dialog) {
                                Intent i = new Intent(ctx, VerImagen.class);
                                i.putExtra(Constants.IMAGEN, byteCurp);
                                startActivity(i);
                                dialog.dismiss();

                            }
                        }, R.string.cancel, new Popups.DialogMessage() {
                            @Override
                            public void OnClickListener(AlertDialog dialog) {
                                dialog.dismiss();
                            }
                        });
                Objects.requireNonNull(fachada_dlg.getWindow()).requestFeature(Window.FEATURE_NO_TITLE);
                fachada_dlg.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                fachada_dlg.show();
            }
        }
    };
    private View.OnClickListener ivComprobante_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (is_edit){
                final AlertDialog evidencia_dlg = Popups.showDialogConfirmImage(ctx, Constants.question,
                        R.string.capturar_foto, R.string.fotografia, new Popups.DialogMessage() {
                            @Override
                            public void OnClickListener(AlertDialog dialog) {
                                Intent i = new Intent(ctx, CameraVertical.class);
                                startActivityForResult(i, REQUEST_CODE_FOTO_COMPROBATE);
                                dialog.dismiss();

                            }
                        }, R.string.ver_imagen, new Popups.DialogMessage() {
                            @Override
                            public void OnClickListener(AlertDialog dialog) {
                                Intent i = new Intent(ctx, VerImagen.class);
                                i.putExtra(Constants.IMAGEN, byteComprobante);
                                startActivity(i);
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
            else {
                final AlertDialog fachada_dlg = Popups.showDialogConfirm(ctx, Constants.question,
                        R.string.ver_fotografia, R.string.ver_imagen, new Popups.DialogMessage() {
                            @Override
                            public void OnClickListener(AlertDialog dialog) {
                                Intent i = new Intent(ctx, VerImagen.class);
                                i.putExtra(Constants.IMAGEN, byteComprobante);
                                startActivity(i);
                                dialog.dismiss();

                            }
                        }, R.string.cancel, new Popups.DialogMessage() {
                            @Override
                            public void OnClickListener(AlertDialog dialog) {
                                dialog.dismiss();
                            }
                        });
                Objects.requireNonNull(fachada_dlg.getWindow()).requestFeature(Window.FEATURE_NO_TITLE);
                fachada_dlg.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                fachada_dlg.show();
            }
        }
    };

    private View.OnClickListener ivIneSelfie_OnClik = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (is_edit){
                final AlertDialog evidencia_dlg = Popups.showDialogConfirmImage(ctx, question,
                        R.string.capturar_foto, R.string.fotografia, new Popups.DialogMessage() {
                            @Override
                            public void OnClickListener(AlertDialog dialog) {
                                Intent i = new Intent(ctx, CameraVertical.class);
                                startActivityForResult(i, REQUEST_CODE_FOTO_INE_SELFIE);
                                dialog.dismiss();

                            }
                        }, R.string.ver_imagen, new Popups.DialogMessage() {
                            @Override
                            public void OnClickListener(AlertDialog dialog) {
                                Intent i = new Intent(ctx, VerImagen.class);
                                i.putExtra(IMAGEN, byteIneSelfie);
                                startActivity(i);
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
                final AlertDialog fachada_dlg = Popups.showDialogConfirm(ctx, question,
                        R.string.ver_fotografia, R.string.ver_imagen, new Popups.DialogMessage() {
                            @Override
                            public void OnClickListener(AlertDialog dialog) {
                                Intent i = new Intent(ctx, VerImagen.class);
                                i.putExtra(IMAGEN, byteIneSelfie);
                                startActivity(i);
                                dialog.dismiss();

                            }
                        }, R.string.cancel, new Popups.DialogMessage() {
                            @Override
                            public void OnClickListener(AlertDialog dialog) {
                                dialog.dismiss();
                            }
                        });
                Objects.requireNonNull(fachada_dlg.getWindow()).requestFeature(Window.FEATURE_NO_TITLE);
                fachada_dlg.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                fachada_dlg.show();
            }
        }
    };

    /**Evento de click para avanzar entre las secciones*/
    //Continuar
    private View.OnClickListener btnContinuar0_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ivDown1.setVisibility(View.VISIBLE);
            ivUp1.setVisibility(View.GONE);
            llDatosPersonales.setVisibility(View.GONE);

            if (Miscellaneous.GetStr(tvEstadoCivilCli).equals("CASADO(A)") || Miscellaneous.GetStr(tvEstadoCivilCli).equals("UNIÓN LIBRE")) {
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
    private View.OnClickListener btnContinuar1_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            /*ivDown3.setVisibility(View.GONE);
            ivUp3.setVisibility(View.VISIBLE);
            llDatosDomicilio.setVisibility(View.VISIBLE);

            ivDown2.setVisibility(View.VISIBLE);
            ivUp2.setVisibility(View.GONE);
            llDatosTelefonicos.setVisibility(View.GONE);

            etCalleCli.requestFocus();*/
        }
    };
    private View.OnClickListener btnContinuar2_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            /*ivDown4.setVisibility(View.GONE);
            ivUp4.setVisibility(View.VISIBLE);
            llDatosNegocio.setVisibility(View.VISIBLE);
            etNombreNeg.requestFocus();

            ivDown3.setVisibility(View.VISIBLE);
            ivUp3.setVisibility(View.GONE);
            llDatosDomicilio.setVisibility(View.GONE);*/
        }
    };
    private View.OnClickListener btnContinuar3_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ivDown4.setVisibility(View.VISIBLE);
            ivUp4.setVisibility(View.GONE);
            llDatosNegocio.setVisibility(View.GONE);

            ivDown8.setVisibility(View.GONE);
            ivUp8.setVisibility(View.VISIBLE);
            llDatosPoliticas.setVisibility(View.VISIBLE);
            etEmail.requestFocus();
        }
    };
    private View.OnClickListener btnContinuar4_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ivDown5.setVisibility(View.VISIBLE);
            ivUp5.setVisibility(View.GONE);
            llDatosConyuge.setVisibility(View.GONE);

            ivDown4.setVisibility(View.GONE);
            ivUp4.setVisibility(View.VISIBLE);
            llDatosNegocio.setVisibility(View.VISIBLE);

            etNombreNeg.requestFocus();
        }
    };
    private View.OnClickListener btnContinuar5_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ivDown6.setVisibility(View.VISIBLE);
            ivUp6.setVisibility(View.GONE);
            llOtrosDatos.setVisibility(View.GONE);

            /*
            if (cbCasaReuniones.isChecked()){
                //ivDown7.setVisibility(View.GONE);
                //ivUp7.setVisibility(View.VISIBLE);
                llDatosCroquis.setVisibility(View.VISIBLE);
            }*/

            ivDown1.setVisibility(View.GONE);
            ivUp1.setVisibility(View.VISIBLE);
            llDatosPersonales.setVisibility(View.VISIBLE);
        }
    };

    private View.OnClickListener btnContinuar7_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            /*ivDown7.setVisibility(View.VISIBLE);
            ivUp7.setVisibility(View.GONE);
            llDatosCroquis.setVisibility(View.GONE);

            ivDown8.setVisibility(View.GONE);
            ivUp8.setVisibility(View.VISIBLE);
            llDatosPoliticas.setVisibility(View.VISIBLE);*/
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

    /**Evento de click para retroceder entre las secciones*/
    //Regresar
    private View.OnClickListener btnRegresar0_OnClick = new View.OnClickListener(){
        @Override
        public void onClick(View v){
            ivDown1.setVisibility(View.VISIBLE);
            ivUp1.setVisibility(View.GONE);
            llDatosPersonales.setVisibility(View.GONE);

            ivDown6.setVisibility(View.GONE);
            ivUp6.setVisibility(View.VISIBLE);
            llOtrosDatos.setVisibility(View.VISIBLE);
        }
    };
    private View.OnClickListener btnRegresar1_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ivDown1.setVisibility(View.GONE);
            ivUp1.setVisibility(View.VISIBLE);
            llDatosPersonales.setVisibility(View.VISIBLE);

            //ivDown2.setVisibility(View.VISIBLE);
            //ivUp2.setVisibility(View.GONE);
            //llDatosTelefonicos.setVisibility(View.GONE);
            //etNombreCli.requestFocus();
        }
    };
    private View.OnClickListener btnRegresar2_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //ivDown2.setVisibility(View.GONE);
            //ivUp2.setVisibility(View.VISIBLE);
            //llDatosTelefonicos.setVisibility(View.VISIBLE);

            //ivDown3.setVisibility(View.VISIBLE);
            //ivUp3.setVisibility(View.GONE);
            //llDatosDomicilio.setVisibility(View.GONE);
            etTelCasaCli.requestFocus();
        }
    };
    private View.OnClickListener btnRegresar3_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ivDown4.setVisibility(View.VISIBLE);
            ivUp4.setVisibility(View.GONE);
            llDatosNegocio.setVisibility(View.GONE);

            if (Miscellaneous.GetStr(tvEstadoCivilCli).equals("CASADO(A)") || Miscellaneous.GetStr(tvEstadoCivilCli).equals("UNIÓN LIBRE")) {
                ivDown5.setVisibility(View.GONE);
                ivUp5.setVisibility(View.VISIBLE);
                llDatosConyuge.setVisibility(View.VISIBLE);
                etNombreCony.requestFocus();
            }
            else{
                ivDown1.setVisibility(View.GONE);
                ivUp1.setVisibility(View.VISIBLE);
                llDatosPersonales.setVisibility(View.VISIBLE);
                etNombreCli.requestFocus();
            }
        }
    };
    private View.OnClickListener btnRegresar4_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ivDown1.setVisibility(View.GONE);
            ivUp1.setVisibility(View.VISIBLE);
            llDatosPersonales.setVisibility(View.VISIBLE);

            ivDown5.setVisibility(View.VISIBLE);
            ivUp5.setVisibility(View.GONE);
            llDatosConyuge.setVisibility(View.GONE);

            etNombreCli.requestFocus();
        }
    };
    private View.OnClickListener btnRegresar5_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ivDown6.setVisibility(View.VISIBLE);
            ivUp6.setVisibility(View.GONE);
            llOtrosDatos.setVisibility(View.GONE);

            /*if (m.GetStr(tvEstadoCivilCli).equals("CASADO(A)") || m.GetStr(tvEstadoCivilCli).equals("UNIÓN LIBRE")) {
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
            }*/
        }
    };

    private View.OnClickListener btnRegresar7_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            ivDown6.setVisibility(View.GONE);
            ivUp6.setVisibility(View.VISIBLE);
            llOtrosDatos.setVisibility(View.VISIBLE);

            //ivDown7.setVisibility(View.VISIBLE);
            //ivUp7.setVisibility(View.GONE);
            llDatosCroquis.setVisibility(View.GONE);
        }
    };

    private View.OnClickListener btnRegresar8_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ivDown8.setVisibility(View.VISIBLE);
            ivUp8.setVisibility(View.GONE);
            llDatosPoliticas.setVisibility(View.GONE);

            ivDown4.setVisibility(View.GONE);
            ivUp4.setVisibility(View.VISIBLE);
            llDatosNegocio.setVisibility(View.VISIBLE);
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

    /**funcion para obtener las respuestas de peticiones de capturas de fechas*/
    public void setDate (String date, String campo){
        try {
            Date strDate = sdf.parse(date);
            Calendar cal = Calendar.getInstance();
            cal.setTime(strDate);
            ContentValues cv;
            if (campo.equals("fechaNacCli")) {/**valida si respuesta es para la peticion de fecha de nacimiento del cliente*/
                tvFechaNacCli.setError(null);
                tvFechaNacCli.setText(date);
                /**coloca la respuesta(fecha) en el campo*/
                tvEdadCli.setText(Miscellaneous.GetEdad(sdf.format(cal.getTime())));

                /**Crea un map con los campos necesarios para generar la curp*/
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
                Log.e("AQUI", "FECHA NACIMIENTO");
                Log.e("AQUI", tvFechaNacCli.getText().toString());
                Log.e("AQUI", String.valueOf(id_integrante));
                Update("fecha_nacimiento", TBL_INTEGRANTES_GPO_REN, tvFechaNacCli.getText().toString().trim(), "id", id_integrante);
                Update("edad", TBL_INTEGRANTES_GPO_REN, tvEdadCli.getText().toString().trim(), "id", id_integrante);
                etCurpCli.setText(Miscellaneous.GenerarCurp(params));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    /**Funcion para obtener cual es el monto maximo que tiene el cliente que es la suma de los ingresos menos la suma de los gastos*/
    private void MontoMaximoNeg (){
        double ing_mensual = (etIngMenNeg.getText().toString().trim().replace(",","").isEmpty())?0:Integer.parseInt(etIngMenNeg.getText().toString().trim().replace(",",""));
        double ing_otros = (etOtrosIngNeg.getText().toString().trim().replace(",","").isEmpty())?0:Integer.parseInt(etOtrosIngNeg.getText().toString().trim().replace(",",""));

        double gas_semanal = (etGastosSemNeg.getText().toString().trim().replace(",","").isEmpty())?0:Integer.parseInt(etGastosSemNeg.getText().toString().trim().replace(",",""));

        double ingreso = ing_mensual + ing_otros;
        double gastos = gas_semanal;

        tvMontoMaxNeg.setText(dfnd.format(ingreso - gastos));
        Update("monto_maximo", TBL_NEGOCIO_INTEGRANTE_REN, tvMontoMaxNeg.getText().toString().trim().replace(",",""), "id_integrante", id_integrante);
    }

    /**funcion para abrir un dialogo para comenzar a crear los registros de las secciones*/
    private void openRegistroIntegrante(String id_credito) {
        dialog_renovar_integrante renovar_integrante = new dialog_renovar_integrante();
        Bundle b = new Bundle();
        b.putString("id_credito", String.valueOf(id_credito));
        renovar_integrante.setArguments(b);
        renovar_integrante.setCancelable(false);
        renovar_integrante.show(getSupportFragmentManager(), NameFragments.DIALOGREGISTROINTEGRANTE);
    }

    private void openRegistroIntegrante(String id_credito, String id_integrante) {
        dialog_renovar_integrante renovar_integrante = new dialog_renovar_integrante();
        Bundle b = new Bundle();
        b.putString("id_credito", String.valueOf(id_credito));
        b.putString("id_integrante", String.valueOf(id_integrante));
        b.putString("nombre", etNombreCli.getText().toString().trim().toUpperCase());
        b.putString("paterno", etApPaternoCli.getText().toString().trim().toUpperCase());
        b.putString("materno", etApMaternoCli.getText().toString().trim().toUpperCase());
        b.putString("cargo", tvCargo.getText().toString().trim().toUpperCase());
        renovar_integrante.setArguments(b);
        renovar_integrante.setCancelable(true);
        renovar_integrante.show(getSupportFragmentManager(), NameFragments.DIALOGREGISTROINTEGRANTE);
    }

    //===================== Listener GPS  =======================================================
    private void findUbicacion(String idDomicilio, String tipo)
    {
        DomicilioIntegranteRen domicilio = null;
        NegocioIntegranteRen negocio = null;

        switch(tipo) {
            case "CLIENTE":
                DomicilioIntegranteRenDao domicilioDao = new DomicilioIntegranteRenDao(ctx);
                domicilio = domicilioDao.findByIdIntegrante(Long.valueOf(idDomicilio));
                break;
            case "NEGOCIO":
                NegocioIntegranteRenDao negocioDao = new NegocioIntegranteRenDao(ctx);
                negocio = negocioDao.findByIdIntegrante(Long.valueOf(idDomicilio));
                break;
            default:
                break;
        }

        if(
            (negocio != null && !negocio.getLatitud().equals("0") && !negocio.getLatitud().equals("") )
            || (domicilio != null && !domicilio.getLatitud().equals("0") && !domicilio.getLatitud().equals("") )
        )
        {
            AlertDialog alertDialog = Popups.showDialogConfirm(this, warning,
                    R.string.guardar_cambios, R.string.yes, dialog -> {
                        switch(tipo) {
                            case "CLIENTE":
                                setUbicacion();
                                break;
                            case "NEGOCIO":
                                setUbicacionNegocio();
                                break;
                            default:
                                break;
                        }
                        dialog.dismiss();
                    }, R.string.cancel, dialog -> {
                        dialog.dismiss();
                    }
            );

            alertDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            alertDialog.setCancelable(true);
            alertDialog.show();
        }
        else
        {
            switch(tipo) {
                case "CLIENTE":
                    setUbicacion();
                    break;
                case "NEGOCIO":
                    setUbicacionNegocio();
                    break;
                default:
                    break;
            }
        }
    }

    private void setUbicacion()
    {
        pbLoadCli.setVisibility(View.VISIBLE);
        ibMapCli.setEnabled(false);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        final Handler myHandler = new Handler();
        locationListener = new MyCurrentListener(new MyCurrentListener.evento() {
            @Override
            public void onComplete(String latitud, String longitud) {

                ibMapCli.setEnabled(true);
                tvMapaCli.setError(null);
                if (!latitud.isEmpty() && !longitud.isEmpty()){
                    mapCli.setVisibility(View.VISIBLE);
                    Update("latitud", TBL_DOMICILIO_INTEGRANTE_REN, latitud, "id_integrante", id_integrante);
                    Update("longitud", TBL_DOMICILIO_INTEGRANTE_REN, longitud, "id_integrante", id_integrante);
                    Update("located_at", TBL_DOMICILIO_INTEGRANTE_REN, Miscellaneous.ObtenerFecha(TIMESTAMP), "id_integrante", id_integrante);
                    Ubicacion(Double.parseDouble(latitud), Double.parseDouble(longitud));
                }
                else{
                    latLngUbiCli = new LatLng(0,0);
                    Update("latitud", TBL_DOMICILIO_INTEGRANTE_REN, "", "id_integrante", id_integrante);
                    Update("longitud", TBL_DOMICILIO_INTEGRANTE_REN, "", "id_integrante", id_integrante);
                    pbLoadCli.setVisibility(View.GONE);
                    Toast.makeText(ctx, getResources().getString(R.string.no_ubicacion), Toast.LENGTH_SHORT).show();
                }

                myHandler.removeCallbacksAndMessages(null);

                CancelUbicacion();

            }
        });
        if (ActivityCompat.checkSelfPermission(ctx, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.checkSelfPermission(ctx, Manifest.permission.ACCESS_COARSE_LOCATION);
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
                latLngUbiCli = new LatLng(0,0);
                pushMapButtonCli = true;
                Toast.makeText(ctx, "No se logró obtener la ubicación", Toast.LENGTH_SHORT).show();
            }
        }, 60000);
    }

    private void setUbicacionNegocio()
    {
        pbLoadNeg.setVisibility(View.VISIBLE);
        ibMapNeg.setEnabled(false);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        final Handler myHandler = new Handler();
        locationListener = new MyCurrentListener(new MyCurrentListener.evento() {
            @Override
            public void onComplete(String latitud, String longitud) {

                tvMapaNeg.setError(null);
                ibMapNeg.setEnabled(true);
                if (!latitud.isEmpty() && !longitud.isEmpty()){
                    mapNeg.setVisibility(View.VISIBLE);
                    Update("latitud", TBL_NEGOCIO_INTEGRANTE_REN, latitud, "id_integrante", id_integrante);
                    Update("longitud", TBL_NEGOCIO_INTEGRANTE_REN, longitud, "id_integrante", id_integrante);
                    Update("located_at", TBL_NEGOCIO_INTEGRANTE_REN, Miscellaneous.ObtenerFecha(TIMESTAMP), "id_integrante", id_integrante);
                    UbicacionNeg(Double.parseDouble(latitud), Double.parseDouble(longitud));
                }
                else{
                    Update("latitud", TBL_NEGOCIO_INTEGRANTE_REN, "", "id_integrante", id_integrante);
                    Update("longitud", TBL_NEGOCIO_INTEGRANTE_REN, "", "id_integrante", id_integrante);
                    pbLoadNeg.setVisibility(View.GONE);
                    latLngUbiNeg = new LatLng(0,0);
                    Toast.makeText(ctx, getResources().getString(R.string.no_ubicacion), Toast.LENGTH_SHORT).show();
                }

                myHandler.removeCallbacksAndMessages(null);

                CancelUbicacion();

            }
        });
        if (ActivityCompat.checkSelfPermission(ctx, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.checkSelfPermission(ctx, Manifest.permission.ACCESS_COARSE_LOCATION);
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
                latLngUbiNeg = new LatLng(0,0);
                pushMapButtonNeg = true;
                Toast.makeText(ctx, "No se logró obtener la ubicación", Toast.LENGTH_SHORT).show();
            }
        }, 60000);
    }

    /**Funciones para obtener la ubicacion*/
    private void ObtenerUbicacion (){
        findUbicacion(id_integrante, "CLIENTE");
    }

    private void ObtenerUbicacionNeg (){
        findUbicacion(id_integrante, "NEGOCIO");
    }

    private void ObtenerUbicacionFirmaCliente (){
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        final Handler myHandler = new Handler();

        locationListener = new MyCurrentListener((latitud, longitud) -> {

            if (!latitud.isEmpty() && !longitud.isEmpty()){
                Update("latitud", TBL_OTROS_DATOS_INTEGRANTE_REN, latitud, "id_integrante", id_integrante);
                Update("longitud", TBL_OTROS_DATOS_INTEGRANTE_REN, longitud, "id_integrante", id_integrante);
                Update("located_at", TBL_OTROS_DATOS_INTEGRANTE_REN, Miscellaneous.ObtenerFecha(TIMESTAMP), "id_integrante", id_integrante);
            }

            myHandler.removeCallbacksAndMessages(null);

            CancelUbicacion();

        });
        if (ActivityCompat.checkSelfPermission(ctx, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.checkSelfPermission(ctx, Manifest.permission.ACCESS_COARSE_LOCATION);
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

        myHandler.postDelayed(() -> {
            locationManager.removeUpdates(locationListener);
        }, 60000);
    }

    /**Funciones para inicializar los mapas*/
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

    /**Funciones para colocar el pin en el mapa*/
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
        //ibMapCli.setVisibility(View.GONE);
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
        //ibMapNeg.setVisibility(View.GONE);
    }

    private void CancelUbicacion (){
        locationManager.removeUpdates(locationListener);
    }

    private void CopiarDatosDeClienteHaciaNegocio()
    {
        etCalleNeg.setText(etCalleCli.getText());
        etNoExtNeg.setText(etNoExtCli.getText());
        etNoIntNeg.setText(etNoIntCli.getText());
        etManzanaNeg.setText(etManzanaCli.getText());
        etLoteNeg.setText(etLoteCli.getText());
        etCpNeg.setText(etCpCli.getText());
        tvColoniaNeg.setText(tvColoniaCli.getText());
        etCiudadNeg.setText(etCiudadCli.getText());
        tvLocalidadNeg.setText(tvLocalidadCli.getText());
        tvMunicipioNeg.setText(tvMunicipioCli.getText());
        tvEstadoNeg.setText(tvEstadoCli.getText());
        etReferenciNeg.setText(etReferencia.getText());

        Update("calle", TBL_NEGOCIO_INTEGRANTE_REN, Miscellaneous.GetStr(etCalleNeg), "id_integrante", id_integrante);
        Update("no_exterior", TBL_NEGOCIO_INTEGRANTE_REN, Miscellaneous.GetStr(etNoExtNeg), "id_integrante", id_integrante);
        Update("no_interior", TBL_NEGOCIO_INTEGRANTE_REN, Miscellaneous.GetStr(etNoIntNeg), "id_integrante", id_integrante);
        Update("manzana", TBL_NEGOCIO_INTEGRANTE_REN, Miscellaneous.GetStr(etManzanaNeg), "id_integrante", id_integrante);
        Update("lote", TBL_NEGOCIO_INTEGRANTE_REN, Miscellaneous.GetStr(etLoteNeg), "id_integrante", id_integrante);
        Update("cp", TBL_NEGOCIO_INTEGRANTE_REN, Miscellaneous.GetStr(etCpNeg), "id_integrante", id_integrante);
        Update("colonia", TBL_NEGOCIO_INTEGRANTE_REN, Miscellaneous.GetStr(tvColoniaNeg), "id_integrante", id_integrante);
        Update("ciudad", TBL_NEGOCIO_INTEGRANTE_REN, Miscellaneous.GetStr(etCiudadNeg), "id_integrante", id_integrante);
        Update("localidad", TBL_NEGOCIO_INTEGRANTE_REN, Miscellaneous.GetStr(tvLocalidadNeg), "id_integrante", id_integrante);
        Update("municipio", TBL_NEGOCIO_INTEGRANTE_REN, Miscellaneous.GetStr(tvMunicipioNeg), "id_integrante", id_integrante);
        Update("estado", TBL_NEGOCIO_INTEGRANTE_REN, Miscellaneous.GetStr(tvEstadoNeg), "id_integrante", id_integrante);
        Update("ref_domiciliaria", TBL_NEGOCIO_INTEGRANTE_REN, Miscellaneous.GetStr(etReferenciNeg), "id_integrante", id_integrante);

    }

    private void LimpiarDatosNegocio()
    {
        pbLoadNeg.setVisibility(View.GONE);
        ibMapNeg.setEnabled(true);
        ibMapNeg.setVisibility(View.VISIBLE);
        mapNeg.setVisibility(View.GONE);

        etCalleNeg.setText("");
        etNoExtNeg.setText("");
        etNoIntNeg.setText("");
        etManzanaNeg.setText("");
        etLoteNeg.setText("");
        etCpNeg.setText("");
        tvColoniaNeg.setText("");
        etCiudadNeg.setText("");
        tvLocalidadNeg.setText("");
        tvMunicipioNeg.setText("");
        tvEstadoNeg.setText("");
        etReferenciNeg.setText("");

        Update("calle", TBL_NEGOCIO_INTEGRANTE_REN, "", "id_integrante", id_integrante);
        Update("no_exterior", TBL_NEGOCIO_INTEGRANTE_REN, "", "id_integrante", id_integrante);
        Update("no_interior", TBL_NEGOCIO_INTEGRANTE_REN,"", "id_integrante", id_integrante);
        Update("manzana", TBL_NEGOCIO_INTEGRANTE_REN, "", "id_integrante", id_integrante);
        Update("lote", TBL_NEGOCIO_INTEGRANTE_REN, "", "id_integrante", id_integrante);
        Update("cp", TBL_NEGOCIO_INTEGRANTE_REN, "", "id_integrante", id_integrante);
        Update("colonia", TBL_NEGOCIO_INTEGRANTE_REN, "", "id_integrante", id_integrante);
        Update("ciudad", TBL_NEGOCIO_INTEGRANTE_REN, "", "id_integrante", id_integrante);
        Update("localidad", TBL_NEGOCIO_INTEGRANTE_REN, "", "id_integrante", id_integrante);
        Update("municipio", TBL_NEGOCIO_INTEGRANTE_REN, "", "id_integrante", id_integrante);
        Update("estado", TBL_NEGOCIO_INTEGRANTE_REN, "", "id_integrante", id_integrante);
        Update("ref_domiciliaria", TBL_NEGOCIO_INTEGRANTE_REN, "", "id_integrante", id_integrante);
    }

    private void HabilitarCamposNegocio()
    {
        LimpiarDatosNegocio();

        cbNegEnDomCli.setEnabled(true);
        etNombreNeg.setEnabled(true);
        etAntiguedadNeg.setEnabled(true);
        ibFotoFachNeg.setEnabled(true);
        ibFotoFachNeg.setVisibility(View.VISIBLE);
        ivFotoFachNeg.setVisibility(View.GONE);
        ibMapNeg.setEnabled(true);
        ibMapNeg.setVisibility(View.VISIBLE);

        etNombreNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.et_rounded_edges));
        etAntiguedadNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.et_rounded_edges));
        ibMapNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.round_corner_blue));
        etCalleNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.et_rounded_edges));
        etNoExtNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.et_rounded_edges));
        etNoIntNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.et_rounded_edges));
        etManzanaNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.et_rounded_edges));
        etLoteNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.et_rounded_edges));
        etCpNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.et_rounded_edges));
        tvColoniaNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.et_rounded_edges));
        etCiudadNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.et_rounded_edges));
        tvLocalidadNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.et_rounded_edges));
        tvMunicipioNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.et_rounded_edges));
        tvEstadoNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.et_rounded_edges));
        etReferenciNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.et_rounded_edges));
        ibFotoFachNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.round_corner_blue));
    }

    private void DeshabilitarCamposNegocio()
    {
        LimpiarDatosNegocio();

        Update("tiene_negocio", TBL_NEGOCIO_INTEGRANTE_REN, "NO", "id_integrante", id_integrante);
        cbNegEnDomCli.setChecked(false);
        cbNegEnDomCli.setEnabled(false);
        etNombreNeg.setEnabled(false);
        etNombreNeg.setText("");
        etAntiguedadNeg.setEnabled(false);
        etAntiguedadNeg.setText("");
        ibFotoFachNeg.setEnabled(false);
        ibFotoFachNeg.setVisibility(View.VISIBLE);
        ivFotoFachNeg.setVisibility(View.GONE);
        ibMapNeg.setEnabled(false);
        ibMapNeg.setVisibility(View.VISIBLE);
        mapNeg.setVisibility(View.GONE);

        etNombreNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        etAntiguedadNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        ibMapNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        etCalleNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        etCalleNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        etNoExtNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        etNoIntNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        etManzanaNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        etLoteNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        etCpNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        tvColoniaNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        etCiudadNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        tvLocalidadNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        //tvMunicipioNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        //tvEstadoNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        etReferenciNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        ibFotoFachNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));

        Update("nombre", TBL_NEGOCIO_INTEGRANTE_REN, "", "id_integrante", id_integrante);
        Update("antiguedad", TBL_NEGOCIO_INTEGRANTE_REN, "", "id_integrante", id_integrante);
        Update("foto_fachada", TBL_NEGOCIO_INTEGRANTE_REN, "", "id_integrante", id_integrante);
    }

    /**Funcion para precargar los datos ya guardados*/
    private void initComponents (String id_credito, String id_integrante){
        //Cursor row = dBhelper.getIntegranteOri(id_credito, id_integrante);
        //row.moveToFirst();
        Log.e("IdCredito", id_credito);
        Log.e("idIntegrante", id_integrante);
        /**consulta para obtener los estatus de los registro de las secciones*/
        String sql = "SELECT " +
                "i.estatus_completado AS eIntegrante, " +
                "i.estado_civil AS civil, " +
                "t.estatus_completado AS eTelefono, " +
                "d.estatus_completado AS eDomiclio, " +
                "n.estatus_completado AS eNegocio, " +
                "c.estatus_completado AS eConyuge, " +
                "o.estatus_completado AS eOtros, " +
                "doc.estatus_completado AS eDocumentos, " +
                "p.estatus_completado AS ePoliticas, " +
                "COALESCE(cro.estatus_completado, 1) AS eCroquis, " +
                "i.ciclo, " +
                "i.monto_prestamo_anterior, " +
                "cgp.id_solicitud, " +
                "s.estatus " +
                "FROM " + TBL_INTEGRANTES_GPO_REN + " AS i "+
                "INNER JOIN " + TBL_TELEFONOS_INTEGRANTE_REN + " AS t ON i.id = t.id_integrante " +
                "INNER JOIN " + TBL_DOMICILIO_INTEGRANTE_REN + " AS d ON i.id = d.id_integrante " +
                "INNER JOIN " + TBL_NEGOCIO_INTEGRANTE_REN + " AS n ON i.id = n.id_integrante " +
                "INNER JOIN " + TBL_CONYUGE_INTEGRANTE_REN + " AS c ON i.id = c.id_integrante " +
                "INNER JOIN " + TBL_OTROS_DATOS_INTEGRANTE_REN + " AS o ON i.id = o.id_integrante " +
                "LEFT JOIN " + TBL_CROQUIS_GPO_REN + " AS cro ON i.id = cro.id_integrante AND o.casa_reunion = 1 " +
                "INNER JOIN " + TBL_POLITICAS_PLD_INTEGRANTE_REN + " AS p ON i.id = p.id_integrante " +
                "INNER JOIN " + TBL_DOCUMENTOS_INTEGRANTE_REN + " AS doc ON i.id = doc.id_integrante " +
                "INNER JOIN " + TBL_CREDITO_GPO_REN + " AS cgp ON cgp.id = i.id_credito " +
                "INNER JOIN " + TBL_SOLICITUDES_REN + " AS s ON s.id_solicitud = cgp.id_solicitud " +
                "WHERE i.id_credito = ? AND i.id = ? ";
        Cursor row = db.rawQuery(sql, new String[]{id_credito, id_integrante});
        row.moveToFirst();

        tvNumCiclo.setText(row.getString(10));
        tvCreditoAnterior.setText(row.getString(11));

        if(Miscellaneous.GetStr(tvNumCiclo).equals("0"))
        {
            rgEstatus.check(R.id.rbNuevo);
        }
        else
        {
            for(int i = 0; i < rgEstatus.getChildCount(); i++) {
                ((RadioButton) rgEstatus.getChildAt(i)).setEnabled(false);
            }
        }

        Log.e("Count", row.getCount()+" XDXDXDXDXD");

        /**valida que los estatus de los registros (secciones) esten en estatus completado para bloquear las acciones*/
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
                row.getInt(8) == 1 &&
                row.getInt(9) == 1){
            is_edit = false;
        }

        if(row.getInt(13) >= 2)
        {
            is_edit = false;
        }

        row.close(); //Cierra datos de estatus de todas las tablas

        cbNegEnDomCli.setEnabled(is_edit);

        /**obtiene los datos ya registrados de los datos personales*/
        row = dBhelper.getRecords(TBL_INTEGRANTES_GPO_REN, " WHERE id = ? AND id_credito = ?", "", new String[]{id_integrante, id_credito});
        row.moveToFirst();

        /**valida si hay un comentario de la admin porque rechazo la solicitud*/
        if (!row.getString(20).trim().isEmpty()){
            llComentCli.setVisibility(View.VISIBLE);
            tvComentAdminCli.setText(row.getString(20).toUpperCase());
        }

        /**valida el cargo del integrante*/
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

        isNuevo = row.getInt(23) == 1;

        /**Precarga los datos registrado en los campos*/
        etNombreCli.setText(row.getString(3)); etNombreCli.setEnabled(false);
        etApPaternoCli.setText(row.getString(4)); etApPaternoCli.setEnabled(false);
        etApMaternoCli.setText(row.getString(5)); etApMaternoCli.setEnabled(false);
        tvFechaNacCli.setText(row.getString(6));
        if (isNuevo) {
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
            etCurpCli.setEnabled(false);
            etCurpCli.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            if(etCurpCli.getText().toString().trim().length() < 18)
            {
                etCurpCli.setEnabled(true);
                etCurpCli.setBackground(ContextCompat.getDrawable(ctx, R.drawable.et_rounded_edges));
            }

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

        HashMap<Integer, String> paramsCurpConfirm = new HashMap<>();

        paramsCurpConfirm.put(0, Miscellaneous.GetStr(etNombreCli));
        paramsCurpConfirm.put(1, Miscellaneous.GetStr(etApPaternoCli));
        paramsCurpConfirm.put(2, Miscellaneous.GetStr(etApMaternoCli));
        paramsCurpConfirm.put(3, Miscellaneous.GetStr(tvFechaNacCli));
        if (rgGeneroCli.getCheckedRadioButtonId() == R.id.rbHombre) {
            paramsCurpConfirm.put(4, "Hombre");
        }
        else
        {
            paramsCurpConfirm.put(4, "Mujer");
        }
        paramsCurpConfirm.put(5, Miscellaneous.GetStr(tvEstadoNacCli));

        String curpConfirm = Miscellaneous.GenerarCurp(paramsCurpConfirm);
        Log.e("AQUI CURP", curpConfirm);

        if(
                etCurpCli.getText().toString().trim().length() == 18
                        && etCurpCli.getText().toString().trim().substring(1, 16).equals(curpConfirm)
        )
        {
            etCurpCli.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etCurpCli.setEnabled(false);
            tvFechaNacCli.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            tvEstadoNacCli.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        }
        else
        {
            tvFechaNacCli.setOnClickListener(tvFechaNacCli_OnClick);
            tvEstadoNacCli.setOnClickListener(tvEstadoNacCli_OnClick);
        }

        //etCurpIdCli.setText(row.getString(12));
        tvTipoIdentificacion.setText(row.getString(13));
        etNumIdentifCli.setText(row.getString(14)); etNumIdentifCli.setEnabled(is_edit);
        tvEstudiosCli.setText(row.getString(15));
        tvOcupacionCli.setText(row.getString(16));
        tvEstadoCivilCli.setText(row.getString(17));
        /**valida el estado civil de cliente para mostrar u ocultar la seccion del conyuge*/
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

        /**Obtiene los datos telefonicos del cliente*/
        //Datos telefonicos
        row = dBhelper.getRecords(TBL_TELEFONOS_INTEGRANTE_REN, " WHERE id_integrante = ?", "", new String[]{id_integrante});
        row.moveToFirst();

        /**precarga los datos de telefonos guardados*/
        etTelCasaCli.setText(row.getString(2)); etTelCasaCli.setEnabled(is_edit);
        etCelularCli.setText(row.getString(3)); etCelularCli.setEnabled(is_edit);
        etTelMensCli.setText(row.getString(4)); etTelMensCli.setEnabled(is_edit);
        etTeltrabajoCli.setText(row.getString(5)); etTeltrabajoCli.setEnabled(is_edit);
        row.close(); //Cierra datos telefonicos del integrante

        /**obtiene los datos del domicilio del cliente*/
        //Datos domicilio
        row = dBhelper.getRecords(TBL_DOMICILIO_INTEGRANTE_REN, " WHERE id_integrante = ?", "", new String[]{id_integrante});
        row.moveToFirst();
        if (!row.getString(2).isEmpty() && !row.getString(3).isEmpty()){
            mapCli.setVisibility(View.VISIBLE);
            Ubicacion(row.getDouble(2), row.getDouble(3));
        }
        /**precargar los datos del domicilio del cliente ya guardados*/
        etCalleCli.setText(row.getString(4)); etCalleCli.setEnabled(is_edit);
        etNoExtCli.setText(row.getString(5)); etNoExtCli.setEnabled(is_edit);
        etNoIntCli.setText(row.getString(6)); etNoIntCli.setEnabled(is_edit);
        etManzanaCli.setText(row.getString(7)); etManzanaCli.setEnabled(is_edit);
        etLoteCli.setText(row.getString(8)); etLoteCli.setEnabled(is_edit);
        etCpCli.setText(row.getString(9));
        //etCpCli.setEnabled(is_edit);
        etCpCli.setEnabled(true);
        tvColoniaCli.setText(row.getString(10));

        etCiudadCli.setText(row.getString(11));
        //etCiudadCli.setEnabled(is_edit);

        if(!etCiudadCli.getText().toString().trim().isEmpty())
        {
            etCiudadCli.setEnabled(is_edit);
            etCiudadCli.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        }
        else
        {
            etCiudadCli.setEnabled(true);
        }

        tvLocalidadCli.setText(row.getString(12));

        if(!tvColoniaCli.getText().toString().trim().isEmpty())
        {
            tvColoniaCli.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        }

        if(!tvLocalidadCli.getText().toString().trim().isEmpty())
        {
            tvLocalidadCli.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        }

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
            case "CASA RENTADA":
                llCasaFamiliar.setVisibility(View.VISIBLE);
                tvCasaFamiliar.setText(row.getString(16));
                break;
            case "OTRO":
                llCasaOtroCli.setVisibility(View.VISIBLE);
                etOTroTipoCli.setText(row.getString(17)); etOTroTipoCli.setEnabled(is_edit);
                break;
        }
        etTiempoSitio.setText(row.getString(18)); etTiempoSitio.setEnabled(is_edit);
        tvDependientes.setText(row.getString(22));
        if (!row.getString(19).isEmpty()){
            File fachadaFile = new File(Constants.ROOT_PATH + "Fachada/"+row.getString(19));
            Uri uriFachada = Uri.fromFile(fachadaFile);
            byteFotoFachCli = Miscellaneous.getBytesUri(ctx, uriFachada,0);
            Glide.with(ctx).load(uriFachada).into(ivFotoFachCli);
            ibFotoFachCli.setVisibility(View.GONE);
            ivFotoFachCli.setVisibility(View.VISIBLE);
        }
        etReferenciaCli.setText(row.getString(20)); etReferenciaCli.setEnabled(is_edit);
        row.close(); //Cierra datos del domicilio del integrante

        /**Obtiene los datos del negocio*/
        //Datos Negocio
        row = dBhelper.getRecords(TBL_NEGOCIO_INTEGRANTE_REN, " WHERE id_integrante = ?", "", new String[]{id_integrante});
        if(row.getCount() == 0)
        {
            rgTieneNegocio.check(R.id.rbSiTieneNeg);
        }
        row.moveToFirst();
        /**precarga los datos del negocio ya guardados*/
        etNombreNeg.setText(row.getString(2)); etNombreNeg.setEnabled(is_edit);
        if (!row.getString(3).isEmpty() && !row.getString(4).isEmpty()){
            mapNeg.setVisibility(View.VISIBLE);
            UbicacionNeg(row.getDouble(3), row.getDouble(4));
        }
        etCalleNeg.setText(row.getString(5)); etCalleNeg.setEnabled(is_edit);
        etNoExtNeg.setText(row.getString(6)); etNoExtNeg.setEnabled(is_edit);
        etNoIntNeg.setText(row.getString(7)); etNoIntNeg.setEnabled(is_edit);
        etManzanaNeg.setText(row.getString(8)); etManzanaNeg.setEnabled(is_edit);
        etLoteNeg.setText(row.getString(9)); etLoteNeg.setEnabled(is_edit);
        etCpNeg.setText(row.getString(10));
        //etCpNeg.setEnabled(is_edit);
        etCpNeg.setEnabled(true);

        tvColoniaNeg.setText(row.getString(11));
        etCiudadNeg.setText(row.getString(12));
        //etCiudadNeg.setEnabled(is_edit);
        tvLocalidadNeg.setText(row.getString(13));

        if(!etCiudadNeg.getText().toString().trim().isEmpty())
        {
            etCiudadNeg.setEnabled(is_edit);
            etCiudadNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        }
        else
        {
            etCiudadNeg.setEnabled(true);
        }

        if(!tvColoniaNeg.getText().toString().trim().isEmpty())
        {
            tvColoniaNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        }

        if(!tvLocalidadNeg.getText().toString().trim().isEmpty())
        {
            tvLocalidadNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        }

        if (!row.getString(14).trim().isEmpty())
            tvMunicipioNeg.setText(row.getString(14));
        else
            tvMunicipioNeg.setText(R.string.not_found);

        if (!row.getString(15).trim().isEmpty())
            tvEstadoNeg.setText(row.getString(15));
        else
            tvEstadoNeg.setText(R.string.not_found);

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
            etOtroMedioPagoNeg.setEnabled(is_edit);
            etOtroMedioPagoNeg.setVisibility(View.VISIBLE);
        }

        if (tvMediosPagoNeg.getText().toString().trim().toUpperCase().contains("EFECTIVO"))
            llOperacionesEfectivo.setVisibility(View.VISIBLE);
        else
            llOperacionesEfectivo.setVisibility(View.GONE);

        //etNumOperacionNeg.setText((row.getString(28).isEmpty())?"":row.getString(28)); //etNumOperacionNeg.setEnabled(is_edit);
        tvNumOperacionNeg.setText((row.getString(28).isEmpty())?"":row.getString(28)); tvNumOperacionNeg.setEnabled(is_edit);
        etNumOperacionEfectNeg.setText((row.getString(29).isEmpty())?"":row.getString(29)); etNumOperacionEfectNeg.setEnabled(is_edit);

        if (!row.getString(30).isEmpty()){
            File fachadaFile = new File(Constants.ROOT_PATH + "Fachada/"+row.getString(30));
            Uri uriFachada = Uri.fromFile(fachadaFile);
            byteFotoFachNeg = Miscellaneous.getBytesUri(ctx, uriFachada,0);
            Glide.with(ctx).load(uriFachada).into(ivFotoFachNeg);
            ibFotoFachNeg.setVisibility(View.GONE);
            ivFotoFachNeg.setVisibility(View.VISIBLE);
        }
        etReferenciNeg.setText(row.getString(31)); etReferenciNeg.setEnabled(is_edit);

        //tiene_negocio
        if(row.getString(36).trim().equals("") || row.getString(36).trim().equals("SI"))
        {
            rgTieneNegocio.check(R.id.rbSiTieneNeg);
        }
        else
        {
            DeshabilitarCamposNegocio();
            rgTieneNegocio.check(R.id.rbNoTieneNeg);
        }

        //ubicado_en_dom_cli
        if(row.getString(37).trim().equals("") || row.getString(37).trim().equals("SI"))
        {
            if(row.getString(37).trim().equals("")) Update("ubicado_en_dom_cli", TBL_NEGOCIO_INTEGRANTE_REN, "SI", "id_integrante", id_integrante);
            cbNegEnDomCli.setChecked(true);
        }

        row.close(); //Cierra datos del negocio

        /**obtiene los datos del conyuge*/
        //Datos Conyuge
        row = dBhelper.getRecords(TBL_CONYUGE_INTEGRANTE_REN, " WHERE id_integrante = ?", "", new String[]{id_integrante});
        row.moveToFirst();
        /**precarga los datos del conyuge ya guardados*/
        etNombreCony.setText(row.getString(2)); etNombreCony.setEnabled(is_edit);
        etApPaternoCony.setText(row.getString(3)); etApPaternoCony.setEnabled(is_edit);
        etApMaternoCony.setText(row.getString(4)); etApMaternoCony.setEnabled(is_edit);
        etNacionalidad.setText(row.getString(5)); etNacionalidad.setEnabled(is_edit);
        tvOcupacionCony.setText(row.getString(6));

        etCalleCony.setText(row.getString(7)); etCalleCony.setEnabled(is_edit);
        etNoExtCony.setText(row.getString(8)); etNoExtCony.setEnabled(is_edit);
        etNoIntCony.setText(row.getString(9)); etNoIntCony.setEnabled(is_edit);
        etManzanaCony.setText(row.getString(10)); etManzanaCony.setEnabled(is_edit);
        etLoteCony.setText(row.getString(11)); etLoteCony.setEnabled(is_edit);
        etCpCony.setText(row.getString(12));
        //etCpCony.setEnabled(is_edit);
        etCpCony.setEnabled(true);

        tvColoniaCony.setText(row.getString(13));
        etCiudadCony.setText(row.getString(14));
        //etCiudadCony.setEnabled(is_edit);
        tvLocalidadCony.setText(row.getString(15));

        if(!etCiudadCony.getText().toString().trim().isEmpty())
        {
            etCiudadCony.setEnabled(is_edit);
            etCiudadCony.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        }
        else
        {
            etCiudadCony.setEnabled(true);
        }

        if(!tvColoniaCony.getText().toString().trim().isEmpty())
        {
            tvColoniaCony.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        }

        if(!tvLocalidadCony.getText().toString().trim().isEmpty())
        {
            tvLocalidadCony.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        }


        if (!row.getString(16).trim().isEmpty())
            tvMunicipioCony.setText(row.getString(16));
        else
            tvMunicipioCony.setText(R.string.not_found);

        if (!row.getString(17).trim().isEmpty())
            tvEstadoCony.setText(row.getString(17));
        else
            tvEstadoCony.setText(R.string.not_found);

        if (!row.getString(18).trim().isEmpty())
            etIngresoCony.setText(dfnd.format(row.getInt(18))); etIngresoCony.setEnabled(is_edit);
        if (!row.getString(19).trim().isEmpty())
            etGastoCony.setText(dfnd.format(row.getInt(19))); etGastoCony.setEnabled(is_edit);
        etCelularCony.setText(row.getString(20)); etCelularCony.setEnabled(is_edit);
        etCasaCony.setText(row.getString(21)); etCasaCony.setEnabled(is_edit);
        row.close(); // Cierra datos del conyuge

        /**Obtiene los datos generales*/
        //Datos Otros
        row = dBhelper.getRecords(TBL_OTROS_DATOS_INTEGRANTE_REN, " WHERE id_integrante = ?", "", new String[]{id_integrante});
        if(row.getCount() == 0)
        {
            rgFirmaRuegoEncargo.check(R.id.rbSiTieneFirma);
        }
        row.moveToFirst();
        /**Precarga los datos ya guardados*/
        tvRiesgo.setText(row.getString(2));
        tvMedioContacto.setText(row.getString(3));
        tvEstadoCuenta.setText(row.getString(5));
        etEmail.setText(row.getString(4)); etEmail.setEnabled(is_edit);
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
        if (!row.getString(7).trim().isEmpty())
            etCredSolicitado.setText(dfnd.format(row.getInt(7))); etCredSolicitado.setEnabled(is_edit);
        if (!row.getString(7).trim().isEmpty())
            tvCantidadLetra.setText((Miscellaneous.cantidadLetra(row.getString(7)).toUpperCase() + " PESOS MEXICANOS ").replace("  ", " "));

        /**valida si es el integrante para la casa de reuniones para habilitar el croquis*/
        if (row.getInt(8) == 1) {
            cbCasaReuniones.setChecked(true);
            llCroquis.setVisibility(View.VISIBLE);
        }

        if(row.getString(14).trim().equals("") || row.getString(14).trim().equals("SI"))//tiene_firma
        {
            rgFirmaRuegoEncargo.check(R.id.rbSiTieneFirma);
        }
        else
        {
            rgFirmaRuegoEncargo.check(R.id.rbNoTieneFirma);
            llNombreFirmaRuegoEncargo.setVisibility(View.VISIBLE);
            etNombreFirmaRuegoEncargo.setText(row.getString(15).trim());
        }

        if (row.getString(16) != null && !row.getString(16).trim().isEmpty()) etMontoRefinanciar.setText(dfnd.format(row.getInt(16)));
        etMontoRefinanciar.setEnabled(is_edit);

        /**obtiene si hay un integrante para la casa de reuniones para deshabilitar el campo*/
        Cursor row_casa = dBhelper.customSelect(TBL_INTEGRANTES_GPO_REN + " AS i INNER JOIN " + TBL_OTROS_DATOS_INTEGRANTE_REN + " AS od ON od.id_integrante = i.id", "i.id", " WHERE i.id_credito = " + id_credito + " AND od.casa_reunion = 1", "", null);
        row_casa.moveToFirst();
        if (row_casa.getCount() > 0 && row_casa.getInt(0) != Integer.parseInt(id_integrante)){
            cbCasaReuniones.setEnabled(false);
        }
        row_casa.close();

        if (!row.getString(9).isEmpty()){
            File firmaFile = new File(Constants.ROOT_PATH + "Firma/"+row.getString(9));
            Uri uriFirma = Uri.fromFile(firmaFile);
            byteFirmaCli = Miscellaneous.getBytesUri(ctx, uriFirma,0);
            Glide.with(ctx).load(uriFirma).into(ivFirmaCli);
            ibFirmaCli.setVisibility(View.GONE);
            ivFirmaCli.setVisibility(View.VISIBLE);
        }
        row.close(); //Cierra otros datos

        /**Obtiene los datos del croquis*/
        row = dBhelper.getRecords(TBL_CROQUIS_GPO_REN, " WHERE id_integrante = ?", "", new String[]{id_integrante});
        row.moveToFirst();
        /**precarfa los datos ya guardado*/
        tvPrincipal.setText(row.getString(2).trim().toUpperCase());
        tvLateraUno.setText(row.getString(3).trim().toUpperCase());
        tvLateraDos.setText(row.getString(4).trim().toUpperCase());
        tvTrasera.setText(row.getString(5).trim().toUpperCase());
        etReferencia.setText(row.getString(6)); etReferencia.setEnabled(is_edit);
        etCaracteristicasDomicilio.setText(row.getString(8));
        etCaracteristicasDomicilio.setEnabled(is_edit);
        row.close(); //Cierra datos del croquis

        /**obtiene los datos de la politicas pld*/
        //Politicas
        row = dBhelper.getRecords(TBL_POLITICAS_PLD_INTEGRANTE_REN, " WHERE id_integrante = ?", "", new String[]{id_integrante});
        row.moveToFirst();
        /**precarga los datos que ya fueron guardados*/
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


        /**obtiene los nombres de las imagenes de la documentacion*/
        //Documentos
        row = dBhelper.getRecords(TBL_DOCUMENTOS_INTEGRANTE_REN, " WHERE id_integrante = ?", "", new String[]{id_integrante});
        row.moveToFirst();
        /**busca las imagenes en la ruta donde se guardan las imagenes*/
        if (!row.getString(2).isEmpty()){
            File ineFrontalFile = new File(Constants.ROOT_PATH + "Documentos/"+row.getString(2));
            Uri uriIneFrontal = Uri.fromFile(ineFrontalFile);
            byteIneFrontal = Miscellaneous.getBytesUri(ctx, uriIneFrontal,0);
            Glide.with(ctx).load(uriIneFrontal).into(ivIneFrontal);
            ibIneFrontal.setVisibility(View.GONE);
            ivIneFrontal.setVisibility(View.VISIBLE);
        }

        if (!row.getString(3).isEmpty()){
            File ineReversoFile = new File(Constants.ROOT_PATH + "Documentos/"+row.getString(3));
            Uri uriIneReverso = Uri.fromFile(ineReversoFile);
            byteIneReverso = Miscellaneous.getBytesUri(ctx, uriIneReverso,0);
            Glide.with(ctx).load(uriIneReverso).into(ivIneReverso);
            ibIneReverso.setVisibility(View.GONE);
            ivIneReverso.setVisibility(View.VISIBLE);
        }

        if (isNuevo) {
            if (!row.getString(4).isEmpty()){
                File curpFile = new File(Constants.ROOT_PATH + "Documentos/"+row.getString(4));
                Uri uriCurp = Uri.fromFile(curpFile);
                byteCurp = Miscellaneous.getBytesUri(ctx, uriCurp,0);
                Glide.with(ctx).load(uriCurp).into(ivCurp);
                ibCurp.setVisibility(View.GONE);
                ivCurp.setVisibility(View.VISIBLE);
            }
        }

        if (!row.getString(5).isEmpty()){
            File comprobanteFile = new File(Constants.ROOT_PATH + "Documentos/"+row.getString(5));
            Uri uriComprobante = Uri.fromFile(comprobanteFile);
            byteComprobante = Miscellaneous.getBytesUri(ctx, uriComprobante,0);
            Glide.with(ctx).load(uriComprobante).into(ivComprobante);
            ibComprobante.setVisibility(View.GONE);
            ivComprobante.setVisibility(View.VISIBLE);
        }

        if (row.getString(7) != null && !row.getString(7).isEmpty()){
            File ineSelfieFile = new File(ROOT_PATH + "Documentos/"+row.getString(7));
            Uri uriIneSelfie = Uri.fromFile(ineSelfieFile);
            byteIneSelfie = Miscellaneous.getBytesUri(ctx, uriIneSelfie, 0);
            Glide.with(ctx).load(uriIneSelfie).into(ivIneSelfie);
            ibIneSelfie.setVisibility(View.GONE);
            ivIneSelfie.setVisibility(View.VISIBLE);
        }

        row.close(); //Cierra datos de documentos del integrante


        /**Valida si ya esta guardado la solicitud para bloquear todos los campos y eventos*/
        if (!is_edit){
            invalidateOptionsMenu();
            etNombreFirmaRuegoEncargo.setEnabled(false);
            etNombreFirmaRuegoEncargo.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            for(int i = 0; i < rgFirmaRuegoEncargo.getChildCount(); i++){
                ((RadioButton) rgFirmaRuegoEncargo.getChildAt(i)).setEnabled(false);
            }
            cbNegEnDomCli.setEnabled(false);
            for(int i = 0; i < rgTieneNegocio.getChildCount(); i++){
                ((RadioButton) rgTieneNegocio.getChildAt(i)).setEnabled(false);
            }

            //tvFechaNacCli.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            for(int i = 0; i < rgGeneroCli.getChildCount(); i++){
                ((RadioButton) rgGeneroCli.getChildAt(i)).setEnabled(false);
            }
            //tvEstadoNacCli.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            //etCurpCli.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked_right));
            tvTipoIdentificacion.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etNumIdentifCli.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            tvEstudiosCli.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            tvOcupacionCli.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            tvEstadoCivilCli.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            for(int i = 0; i < rgBienes.getChildCount(); i++){
                ((RadioButton) rgBienes.getChildAt(i)).setEnabled(false);
            }

            etTelCasaCli.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etCelularCli.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etTelMensCli.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etTeltrabajoCli.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));

            etCalleCli.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etNoExtCli.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etNoIntCli.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etManzanaCli.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etLoteCli.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            //etCpCli.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            //tvColoniaCli.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            //etCiudadCli.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            //tvLocalidadCli.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            tvTipoCasaCli.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            tvCasaFamiliar.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etTiempoSitio.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            tvDependientes.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etReferenciaCli.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            ibFotoFachCli.setEnabled(false);
            ibFotoFachCli.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            //ibMapCli.setEnabled(false);
            //ibMapCli.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            ibMapCli.setVisibility(View.GONE);

            etNombreNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etCalleNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etNoExtNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etNoIntNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etManzanaNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etLoteNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            //etCpNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            //tvColoniaNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            //etCiudadNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            //tvLocalidadNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            tvDestinoNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etOtroDestinoNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            tvActEcoEspNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            tvActEconomicaNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etAntiguedadNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etIngMenNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etOtrosIngNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etGastosSemNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etCapacidadPagoNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            tvMediosPagoNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            //etNumOperacionNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            tvNumOperacionNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etNumOperacionEfectNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etReferenciNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            ibFotoFachNeg.setEnabled(false);
            ibFotoFachNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            //ibMapNeg.setEnabled(false);
            //ibMapNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            ibMapNeg.setVisibility(View.GONE);

            etNombreCony.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etApPaternoCony.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etApMaternoCony.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etNacionalidad.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            tvOcupacionCony.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etCalleCony.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etNoExtCony.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etNoIntCony.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etManzanaCony.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etLoteCony.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            //etCpCony.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            //tvColoniaCony.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            //etCiudadCony.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            //tvLocalidadCony.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etCelularCony.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etCasaCony.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etIngresoCony.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etGastoCony.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));

            tvRiesgo.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            tvMedioContacto.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            tvEstadoCuenta.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etEmail.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            for(int i = 0; i < rgEstatus.getChildCount(); i++){
                ((RadioButton) rgEstatus.getChildAt(i)).setEnabled(false);
            }
            etCredSolicitado.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            cbCasaReuniones.setEnabled(false);

            etReferencia.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etCaracteristicasDomicilio.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));

            for(int i = 0; i < rgPropietarioReal.getChildCount(); i++){
                ((RadioButton) rgPropietarioReal.getChildAt(i)).setEnabled(false);
            }
            for(int i = 0; i < rgProveedor.getChildCount(); i++){
                ((RadioButton) rgProveedor.getChildAt(i)).setEnabled(false);
            }
            for(int i = 0; i < rgPoliticamenteExp.getChildCount(); i++){
                ((RadioButton) rgPoliticamenteExp.getChildAt(i)).setEnabled(false);
            }

            etMontoRefinanciar.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        }

    }

    /**Funcion para validar los campos y actualizar la columnas del registro de los datos personales*/
    private boolean saveDatosIntegrante(){
        boolean save_integrante = false;
        ContentValues cv = new ContentValues();
        if (!validator.validate(etNombreCli, new String[]{validator.REQUIRED}) &&
                !validator.validate(etApPaternoCli, new String[]{validator.ONLY_TEXT}) &&
                !validator.validate(etApMaternoCli, new String[]{validator.ONLY_TEXT}) &&
                (
                        (
                                !validator.validate(etNombreFirmaRuegoEncargo, new String[]{validator.REQUIRED})
                                        && rgFirmaRuegoEncargo.getCheckedRadioButtonId() == R.id.rbNoTieneFirma
                        )
                                || (rgFirmaRuegoEncargo.getCheckedRadioButtonId() == R.id.rbSiTieneFirma)
                ) &&
                !validatorTV.validate(tvFechaNacCli, new String[]{validatorTV.REQUIRED}) &&
                !validatorTV.validate(tvEdadCli, new String[]{validatorTV.REQUIRED, validatorTV.ONLY_NUMBER})){
            if (rgGeneroCli.getCheckedRadioButtonId() == R.id.rbHombre ||
                    rgGeneroCli.getCheckedRadioButtonId() == R.id.rbMujer){
                tvGeneroCli.setError(null);
                if (!validatorTV.validate(tvEstadoNacCli, new String[]{validatorTV.REQUIRED}) &&
                    !validator.validate(etCurpCli, new String[]{validator.REQUIRED, validator.CURP}) &&
                    (!validatorTV.validate(tvRfcCli, new String[]{validatorTV.REQUIRED}) &&
                    !Miscellaneous.GetStr(tvRfcCli).equals("Rfc no válida"))){
                    if (Miscellaneous.CurpValidador(Miscellaneous.GetStr(etCurpCli))){
                        if (!validatorTV.validate(tvTipoIdentificacion, new String[]{validatorTV.REQUIRED}) &&
                                !validator.validate(etNumIdentifCli, new String[]{validator.REQUIRED}) &&
                                !validatorTV.validate(tvEstudiosCli, new String[]{validatorTV.REQUIRED}) &&
                                !validatorTV.validate(tvOcupacionCli, new String[]{validatorTV.REQUIRED}) &&
                                !validatorTV.validate(tvEstadoCivilCli, new String[]{validatorTV.REQUIRED})){
                            boolean flag_est_civil = false;
                            if (Miscellaneous.GetStr(tvEstadoCivilCli).equals("CASADO(A)")){
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
                                //ivError1.setVisibility(View.GONE);
                                cv.put("fecha_nacimiento", Miscellaneous.GetStr(tvFechaNacCli));
                                cv.put("edad", Integer.parseInt(Miscellaneous.GetStr(tvEdadCli)));
                                switch (rgGeneroCli.getCheckedRadioButtonId()){
                                    case R.id.rbHombre:
                                        cv.put("genero", 0);
                                        break;
                                    case R.id.rbMujer:
                                        cv.put("genero", 1);
                                        break;
                                }
                                cv.put("estado_nacimiento", Miscellaneous.GetStr(tvEstadoNacCli));
                                cv.put("rfc", Miscellaneous.GetStr(tvRfcCli));
                                cv.put("curp", Miscellaneous.GetStr(etCurpCli));
                                cv.put("tipo_identificacion", Miscellaneous.GetStr(tvTipoIdentificacion));
                                cv.put("no_identificacion", Miscellaneous.GetStr(etNumIdentifCli));
                                cv.put("nivel_estudio", Miscellaneous.GetStr(tvEstudiosCli));
                                cv.put("ocupacion", Miscellaneous.GetStr(tvOcupacionCli));

                                db.update(TBL_INTEGRANTES_GPO_REN, cv, "id = ?", new String[]{id_integrante});

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
                        etCurpCli.setError("Curp no válida");
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
    /**Funcion para validar los campos y actualizar la columnas del registro de los datos telefonicos*/
    private boolean saveDatosTelefonicos(){
        boolean save_telefonicos = false;
        if (!validator.validate(etTelCasaCli, new String[]{validator.PHONE}) &&
                !validator.validate(etCelularCli, new String[]{validator.REQUIRED, validator.ONLY_NUMBER, validator.PHONE}) &&
                !validator.validate(etTelMensCli, new String[]{validator.PHONE}) &&
                !validator.validate(etTeltrabajoCli, new String[]{validator.PHONE}) &&
                !validator.validate(etEmail, new String[]{validator.EMAIL})
        ){
            //ivError2.setVisibility(View.GONE);
            ContentValues cv = new ContentValues();
            cv.put("tel_casa", Miscellaneous.GetStr(etTelCasaCli));
            cv.put("tel_celular", Miscellaneous.GetStr(etCelularCli));
            cv.put("tel_mensaje", Miscellaneous.GetStr(etTelMensCli));
            cv.put("tel_trabajo", Miscellaneous.GetStr(etTeltrabajoCli));
            cv.put("estatus_completado", 1);

            db.update(TBL_TELEFONOS_INTEGRANTE_REN, cv, "id_integrante = ?", new String[]{id_integrante});

            save_telefonicos = true;
        }
        else {
            //ivError2.setVisibility(View.VISIBLE);
            ivError1.setVisibility(View.VISIBLE);
        }

        return save_telefonicos;
    }
    /**Funcion para validar los campos y actualizar la columnas del registro de los datos del domicilio el cliente*/
    private boolean saveDatosDomicilio(){
        boolean save_domicilio = false;
        ContentValues cv = new ContentValues();
        if (latLngUbiCli != null || pushMapButtonCli) {
            tvMapaCli.setError(null);
            if (!validator.validate(etCalleCli, new String[]{validator.REQUIRED}) &&
                    (
                            !validator.validate(etNoExtCli, new String[]{validator.REQUIRED})
                                    || (
                                    !validator.validate(etManzanaCli, new String[]{validator.REQUIRED})
                                            && !validator.validate(etLoteCli, new String[]{validator.REQUIRED})
                            )
                    ) &&
                    !validator.validate(etCpCli, new String[]{validator.REQUIRED, validator.ONLY_NUMBER, validator.CP}) &&
                    !Miscellaneous.ValidTextView(tvColoniaCli) &&
                    !validator.validate(etCiudadCli, new String[]{validator.REQUIRED}) &&
                    !validatorTV.validate(tvLocalidadCli, new String[]{validatorTV.REQUIRED}) &&
                    !Miscellaneous.ValidTextView(tvMunicipioCli) &&
                    !Miscellaneous.ValidTextView(tvEstadoCli) &&
                    !validatorTV.validate(tvTipoCasaCli, new String[]{validatorTV.REQUIRED}) &&
                    !validatorTV.validate(tvDependientes, new String[]{validatorTV.REQUIRED})){
                boolean flag_tipo_casa = false;
                cv.put("tipo_vivienda", Miscellaneous.GetStr(tvTipoCasaCli));
                switch (Miscellaneous.GetStr(tvTipoCasaCli)){
                    case "CASA FAMILIAR":
                    case "CASA RENTADA":
                        if (!validatorTV.validate(tvCasaFamiliar, new String[]{validatorTV.REQUIRED})) {
                            flag_tipo_casa = true;
                            cv.put("parentesco", Miscellaneous.GetStr(tvCasaFamiliar));
                            cv.put("otro_tipo_vivienda", "");
                        }

                        break;
                    case "OTRO":
                        if (!validator.validate(etOTroTipoCli, new String[]{validator.REQUIRED})) {
                            flag_tipo_casa = true;
                            cv.put("parentesco", "");
                            cv.put("otro_tipo_vivienda", Miscellaneous.GetStr(etOTroTipoCli));
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
                        /*if (byteFotoFachCli != null){*/
                            //if (!validator.validate(etReferenciaCli, new String[]{validator.REQUIRED})){
                                //ivError3.setVisibility(View.GONE);
                                cv.put("latitud", String.valueOf(latLngUbiCli.latitude));
                                cv.put("longitud", String.valueOf(latLngUbiCli.longitude));
                                cv.put("calle", Miscellaneous.GetStr(etCalleCli));
                                cv.put("no_exterior", Miscellaneous.GetStr(etNoExtCli));
                                cv.put("no_interior", Miscellaneous.GetStr(etNoIntCli));
                                cv.put("manzana", Miscellaneous.GetStr(etManzanaCli));
                                cv.put("lote", Miscellaneous.GetStr(etLoteCli));
                                cv.put("cp", Miscellaneous.GetStr(etCpCli));
                                cv.put("colonia", Miscellaneous.GetStr(tvColoniaCli));
                                cv.put("ciudad", Miscellaneous.GetStr(etCiudadCli));
                                cv.put("localidad", Miscellaneous.GetStr(tvLocalidadCli));
                                cv.put("municipio", Miscellaneous.GetStr(tvMunicipioCli));
                                cv.put("estado", Miscellaneous.GetStr(tvEstadoCli));
                                cv.put("ref_domiciliaria", Miscellaneous.GetStr(etReferenciaCli));
                                cv.put("dependientes", Miscellaneous.GetStr(tvDependientes));
                                cv.put("estatus_completado", 1);

                                db.update(TBL_DOMICILIO_INTEGRANTE_REN, cv, "id_integrante = ?", new String[]{id_integrante});

                                save_domicilio = true;
                            /*}
                            else
                                ivError3.setVisibility(View.VISIBLE);
                            */
                        /*}
                        else {
                            tvFachadaCli.setError("");
                            ivError1.setVisibility(View.VISIBLE);
                            //ivError3.setVisibility(View.VISIBLE);
                        }*/
                    }
                    else {
                        ivError1.setVisibility(View.VISIBLE);
                        //ivError3.setVisibility(View.VISIBLE);
                    }
                }
                else {
                    ivError1.setVisibility(View.VISIBLE);
                    //ivError3.setVisibility(View.VISIBLE);
                }
            }
            else {
                ivError1.setVisibility(View.VISIBLE);
                //ivError3.setVisibility(View.VISIBLE);
            }
        }
        else{
            ivError1.setVisibility(View.VISIBLE);
            //ivError3.setVisibility(View.VISIBLE);
            tvMapaCli.setError("");
        }

        if(
            !validator.validate(etNoExtCli, new String[]{validator.REQUIRED})
            || (
                !validator.validate(etManzanaCli, new String[]{validator.REQUIRED})
                && !validator.validate(etLoteCli, new String[]{validator.REQUIRED})
            )
        )
        {
            etNoExtCli.setError(null);
            etManzanaCli.setError(null);
            etLoteCli.setError(null);
        }

        return save_domicilio;
    }
    /**Funcion para validar los campos y actualizar la columnas del registro de los datos del negocio*/
    private boolean saveDatosNegocio(){
        boolean save_negocio = false;
        if (
            !validator.validate(etNombreNeg, new String[]{validator.REQUIRED, validator.GENERAL})
            || rgTieneNegocio.getCheckedRadioButtonId() == R.id.rbNoTieneNeg
        ){
            etNombreNeg.setError(null);
            if (latLngUbiNeg != null || rgTieneNegocio.getCheckedRadioButtonId() == R.id.rbNoTieneNeg || pushMapButtonNeg){
                tvMapaNeg.setError(null);
                if (
                        (
                            !validator.validate(etCalleNeg, new String[]{validator.REQUIRED}) &&
                            (
                                    !validator.validate(etNoExtNeg, new String[]{validator.REQUIRED})
                                            || (
                                            !validator.validate(etManzanaNeg, new String[]{validator.REQUIRED})
                                                    && !validator.validate(etLoteNeg, new String[]{validator.REQUIRED})
                                    )
                            ) &&
                            !validator.validate(etCpNeg, new String[]{validator.REQUIRED, validator.ONLY_NUMBER, validator.CP}) &&
                            !Miscellaneous.ValidTextView(tvColoniaNeg) &&
                            !validator.validate(etCiudadNeg, new String[]{validator.REQUIRED}) &&
                            !validatorTV.validate(tvLocalidadNeg, new String[]{validatorTV.REQUIRED}) &&
                            !Miscellaneous.ValidTextView(tvMunicipioNeg) &&
                            !validatorTV.validate(tvDestinoNeg, new String[]{validatorTV.REQUIRED})
                        )
                        || rgTieneNegocio.getCheckedRadioButtonId() == R.id.rbNoTieneNeg
                ){
                    etCalleNeg.setError(null);
                    etCpNeg.setError(null);
                    tvColoniaNeg.setError(null);
                    etCiudadNeg.setError(null);
                    tvLocalidadNeg.setError(null);
                    tvMunicipioNeg.setError(null);
                    tvDestinoNeg.setError(null);

                    boolean otro_destino = false;
                    if (Miscellaneous.GetStr(tvDestinoNeg).equals("OTRO")){
                        if (!validator.validate(etOtroDestinoNeg, new String[]{validator.REQUIRED})){
                            otro_destino = true;
                        }
                    }
                    else
                        otro_destino = true;
                    if (otro_destino){
                        if (
                                !validatorTV.validate(tvActEcoEspNeg, new String[]{validatorTV.REQUIRED}) &&
                                !validatorTV.validate(tvActEconomicaNeg, new String[]{validatorTV.REQUIRED}) &&
                                (!validator.validate(etAntiguedadNeg, new String[]{validator.REQUIRED, validator.ONLY_NUMBER, validator.YEARS}) || rgTieneNegocio.getCheckedRadioButtonId() == R.id.rbNoTieneNeg) &&
                                !validator.validate(etIngMenNeg, new String[]{validator.REQUIRED, validator.ONLY_NUMBER}) &&
                                !validator.validate(etOtrosIngNeg, new String[]{validator.REQUIRED, validator.ONLY_NUMBER}) &&
                                !validator.validate(etGastosSemNeg, new String[]{validator.REQUIRED, validator.ONLY_NUMBER, validator.MONEY}) &&
                                !validator.validate(etCapacidadPagoNeg, new String[]{validator.REQUIRED}) &&
                                !validatorTV.validate(tvMediosPagoNeg, new String[]{validatorTV.REQUIRED})
                        ){
                            boolean flag = true;

                            try{
                                if(Double.parseDouble(etIngMenNeg.getText().toString().replace(",", "")) <= 100)
                                {
                                    flag = false;
                                    etIngMenNeg.setError("El ingreso mensual debe ser mayor a 100!");
                                }
                            }
                            catch(Exception e)
                            {
                                etIngMenNeg.setError(e.getMessage());
                                flag = false;
                            }

                            try{
                                if(Double.parseDouble(etGastosSemNeg.getText().toString().replace(",", "")) <= 100)
                                {
                                    flag = false;
                                    etGastosSemNeg.setError("El gasto mensual debe ser mayor a 100!");
                                }
                            }
                            catch(Exception e)
                            {
                                etGastosSemNeg.setError(e.getMessage());
                                flag = false;
                            }

                            if(flag) {
                                boolean otro_medio = false;
                                if (Miscellaneous.GetStr(tvMediosPagoNeg).contains("OTRO")) {
                                    if (!validator.validate(etOtroMedioPagoNeg, new String[]{validator.REQUIRED}))
                                        otro_medio = true;
                                } else
                                    otro_medio = true;

                                if (otro_medio) {
                                    /*if (byteFotoFachNeg != null){*/
                                    if (!validator.validate(etReferenciNeg, new String[]{validator.REQUIRED}) || rgTieneNegocio.getCheckedRadioButtonId() == R.id.rbNoTieneNeg) {
                                        etReferenciNeg.setError(null);
                                        ivError4.setVisibility(View.GONE);
                                        ContentValues cv = new ContentValues();
                                        cv.put("nombre", Miscellaneous.GetStr(etNombreNeg));

                                        if (rgTieneNegocio.getCheckedRadioButtonId() == R.id.rbNoTieneNeg) {
                                            cv.put("latitud", "");
                                            cv.put("longitud", "");
                                        } else {
                                            cv.put("latitud", String.valueOf(latLngUbiNeg.latitude));
                                            cv.put("longitud", String.valueOf(latLngUbiNeg.longitude));
                                        }

                                        cv.put("calle", Miscellaneous.GetStr(etCalleNeg));
                                        cv.put("no_exterior", Miscellaneous.GetStr(etNoExtNeg));
                                        cv.put("no_interior", Miscellaneous.GetStr(etNoIntNeg));
                                        cv.put("manzana", Miscellaneous.GetStr(etManzanaNeg));
                                        cv.put("lote", Miscellaneous.GetStr(etLoteNeg));
                                        cv.put("cp", Miscellaneous.GetStr(etCpNeg));
                                        cv.put("colonia", Miscellaneous.GetStr(tvColoniaNeg));
                                        cv.put("ciudad", Miscellaneous.GetStr(etCiudadNeg));
                                        cv.put("localidad", Miscellaneous.GetStr(tvLocalidadNeg));
                                        cv.put("municipio", Miscellaneous.GetStr(tvMunicipioNeg));
                                        cv.put("destino_credito", Miscellaneous.GetStr(tvDestinoNeg));
                                        cv.put("otro_destino_credito", Miscellaneous.GetStr(etOtroDestinoNeg));
                                        cv.put("actividad_economica", Miscellaneous.GetStr(tvActEconomicaNeg));
                                        cv.put("antiguedad", Miscellaneous.GetStr(etAntiguedadNeg));
                                        cv.put("ing_mensual", Miscellaneous.GetStr(etIngMenNeg).replace(",", ""));
                                        cv.put("ing_otros", Miscellaneous.GetStr(etOtrosIngNeg).replace(",", ""));
                                        cv.put("gasto_semanal", Miscellaneous.GetStr(etGastosSemNeg).replace(",", ""));
                                        cv.put("capacidad_pago", Miscellaneous.GetStr(etCapacidadPagoNeg).replace(",", ""));
                                        cv.put("monto_maximo", Miscellaneous.GetStr(tvMontoMaxNeg).replace(",", ""));
                                        cv.put("medios_pago", Miscellaneous.GetStr(tvMediosPagoNeg));
                                        cv.put("otro_medio_pago", Miscellaneous.GetStr(etOtroMedioPagoNeg));
                                        cv.put("ref_domiciliaria", Miscellaneous.GetStr(etReferenciNeg));
                                        cv.put("estatus_completado", 1);

                                        db.update(TBL_NEGOCIO_INTEGRANTE_REN, cv, "id_integrante = ?", new String[]{id_integrante});

                                        save_negocio = true;
                                    } else
                                        ivError4.setVisibility(View.VISIBLE);
                                    /*}
                                    else {
                                        tvFachadaNeg.setError("");
                                        ivError4.setVisibility(View.VISIBLE);
                                    }*/
                                } else
                                    ivError4.setVisibility(View.VISIBLE);
                            }
                            else
                            {
                                ivError4.setVisibility(View.VISIBLE);
                            }
                        }
                        else
                            ivError4.setVisibility(View.VISIBLE);
                    }
                    else
                        ivError4.setVisibility(View.VISIBLE);
                }
                else
                    ivError4.setVisibility(View.VISIBLE);
            }
            else{
                tvMapaNeg.setError("");
                ivError4.setVisibility(View.VISIBLE);
            }
        }
        else{
            ivError4.setVisibility(View.VISIBLE);
        }

        if(
                !validator.validate(etNoExtNeg, new String[]{validator.REQUIRED})
                        || (
                        !validator.validate(etManzanaNeg, new String[]{validator.REQUIRED})
                                && !validator.validate(etLoteNeg, new String[]{validator.REQUIRED})
                )
                || rgTieneNegocio.getCheckedRadioButtonId() == R.id.rbNoTieneNeg
        )
        {
            etNoExtNeg.setError(null);
            etManzanaNeg.setError(null);
            etLoteNeg.setError(null);
        }

        return save_negocio;
    }
    /**Funcion para validar los campos y actualizar la columnas del registro de los datos del conyuge*/
    private boolean saveConyuge(){
        boolean save_conyuge = false;
        if (!validator.validate(etNombreCony, new String[]{validator.REQUIRED, validator.ONLY_TEXT}) &&
                !validator.validate(etApPaternoCony, new String[]{validator.ONLY_TEXT}) &&
                !validator.validate(etApMaternoCony, new String[]{validator.ONLY_TEXT}) &&
                !validator.validate(etNacionalidad, new String[]{validator.REQUIRED, validator.ONLY_TEXT}) &&
                !validatorTV.validate(tvOcupacionCony, new String[]{validatorTV.REQUIRED}) &&
                !validator.validate(etCalleCony, new String[]{validator.REQUIRED}) &&
                (
                        !validator.validate(etNoExtCony, new String[]{validator.REQUIRED})
                                || (
                                !validator.validate(etManzanaCony, new String[]{validator.REQUIRED})
                                        && !validator.validate(etLoteCony, new String[]{validator.REQUIRED})
                        )
                ) &&
                !validator.validate(etCpCony, new String[]{validator.REQUIRED, validator.ONLY_NUMBER, validator.CP}) &&
                !Miscellaneous.ValidTextView(tvColoniaCony) &&
                !validator.validate(etCiudadCony, new String[]{validator.REQUIRED}) &&
                !validatorTV.validate(tvLocalidadCony, new String[]{validatorTV.REQUIRED}) &&
                !Miscellaneous.ValidTextView(tvMunicipioCony) &&
                !Miscellaneous.ValidTextView(tvEstadoCony) &&
                !validator.validate(etIngresoCony, new String[]{validator.REQUIRED, validator.ONLY_NUMBER}) &&
                !validator.validate(etGastoCony, new String[]{validator.REQUIRED, validator.ONLY_NUMBER}) &&
                !validator.validate(etCasaCony, new String[]{validator.ONLY_NUMBER}) &&
                !validator.validate(etCelularCony, new String[]{validator.REQUIRED, validator.ONLY_NUMBER, validator.PHONE})){
                    ivError5.setVisibility(View.GONE);
                    ContentValues cv = new ContentValues();
                    cv.put("nombre", Miscellaneous.GetStr(etNombreCony));
                    cv.put("paterno", Miscellaneous.GetStr(etApPaternoCony));
                    cv.put("materno", Miscellaneous.GetStr(etApMaternoCli));
                    cv.put("nacionalidad", Miscellaneous.GetStr(etNacionalidad));
                    cv.put("ocupacion", Miscellaneous.GetStr(tvOcupacionCony));
                    cv.put("calle", Miscellaneous.GetStr(etCalleCony));
                    cv.put("no_exterior", Miscellaneous.GetStr(etNoExtCony));
                    cv.put("no_interior", Miscellaneous.GetStr(etNoIntCony));
                    cv.put("manzana", Miscellaneous.GetStr(etManzanaCony));
                    cv.put("lote", Miscellaneous.GetStr(etLoteCony));
                    cv.put("cp", Miscellaneous.GetStr(etCpCony));
                    cv.put("colonia", Miscellaneous.GetStr(tvColoniaCony));
                    cv.put("ciudad", Miscellaneous.GetStr(etCiudadCony));
                    cv.put("localidad", Miscellaneous.GetStr(tvLocalidadCony));
                    cv.put("municipio", Miscellaneous.GetStr(tvMunicipioCony));
                    cv.put("estado", Miscellaneous.GetStr(tvEstadoCony));
                    cv.put("ingresos_mensual", Miscellaneous.GetStr(etIngresoCony).replace(",",""));
                    cv.put("gasto_mensual", Miscellaneous.GetStr(etGastoCony).replace(",",""));
                    cv.put("tel_trabajo", Miscellaneous.GetStr(etCasaCony));
                    cv.put("tel_celular", Miscellaneous.GetStr(etCelularCony));
                    cv.put("estatus_completado", 1);

                    db.update(TBL_CONYUGE_INTEGRANTE_REN, cv, "id_integrante = ?", new String[]{id_integrante});

            save_conyuge = true;
        }
        else {
            ivError5.setVisibility(View.VISIBLE);
        }

        if(
                !validator.validate(etNoExtCony, new String[]{validator.REQUIRED})
                        || (
                        !validator.validate(etManzanaCony, new String[]{validator.REQUIRED})
                                && !validator.validate(etLoteCony, new String[]{validator.REQUIRED})
                )
        )
        {
            etNoExtCony.setError(null);
            etManzanaCony.setError(null);
            etLoteCony.setError(null);
        }

        return save_conyuge;
    }
    /**Funcion para validar los campos y actualizar la columnas del registro de los datos generales*/
    private boolean saveDatosOtros(){
        boolean save_otros = false;
        if (
            !validatorTV.validate(tvRiesgo, new String[]{validatorTV.REQUIRED}) &&
            !validatorTV.validate(tvMedioContacto, new String[]{validatorTV.REQUIRED}) &&
            !validatorTV.validate(tvEstadoCuenta, new String[]{validatorTV.REQUIRED}) &&
            !validator.validate(etMontoRefinanciar, new String[]{validator.REQUIRED})
        ){
            if (rgEstatus.getCheckedRadioButtonId() == R.id.rbNuevo ||
                    rgEstatus.getCheckedRadioButtonId() == R.id.rbRenovado ||
                    rgEstatus.getCheckedRadioButtonId() == R.id.rbCambio){
                if (!validator.validate(etCredSolicitado, new String[]{validator.REQUIRED, validator.CREDITO})){
                    if (byteFirmaCli != null){
                        ivError6.setVisibility(View.GONE);
                        ContentValues cv = new ContentValues();
                        cv.put("clasificacion_riesgo", Miscellaneous.GetStr(tvRiesgo));
                        cv.put("medio_contacto", Miscellaneous.GetStr(tvMedioContacto));
                        cv.put("estado_cuenta", Miscellaneous.GetStr(tvEstadoCuenta));
                        cv.put("email", Miscellaneous.GetStr(etEmail));
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
                        cv.put("monto_solicitado", Miscellaneous.GetStr(etCredSolicitado).replace(",",""));
                        if (cbCasaReuniones.isChecked())
                            cv.put("casa_reunion", 1);
                        else
                            cv.put("casa_reunion", 0);

                        cv.put("estatus_completado", 1);
                        cv.put("monto_refinanciar", Miscellaneous.GetStr(etMontoRefinanciar).replace(",",""));
                        db.update(TBL_OTROS_DATOS_INTEGRANTE_REN, cv, "id_integrante = ?", new String[]{id_integrante});

                        save_otros = true;

                    }
                    else{
                        tvFirmaCli.setError("");
                        ivError6.setVisibility(View.VISIBLE);
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
    /**Funcion para validar los campos y actualizar la columnas del registro de los datos del croquis*/
    private boolean saveCroquis(){
        boolean save_croquis = false;
        if (!validatorTV.validate(tvLateraUno, new String[]{validatorTV.REQUIRED, validatorTV.ALFANUMERICO}) &&
                !validatorTV.validate(tvPrincipal, new String[]{validatorTV.REQUIRED, validatorTV.ALFANUMERICO}) &&
                !validatorTV.validate(tvTrasera, new String[]{validatorTV.REQUIRED, validatorTV.ALFANUMERICO}) &&
                !validatorTV.validate(tvLateraDos, new String[]{validatorTV.REQUIRED, validatorTV.ALFANUMERICO}) &&
                !validator.validate(etReferencia, new String[]{validatorTV.REQUIRED, validatorTV.ALFANUMERICO}) &&
                !validator.validate(etCaracteristicasDomicilio, new String[]{validatorTV.REQUIRED, validatorTV.ALFANUMERICO})
        )
        {
            //ivError7.setVisibility(View.GONE);
            ContentValues cv = new ContentValues();
            cv.put("calle_principal", Miscellaneous.GetStr(tvPrincipal));
            cv.put("lateral_uno", Miscellaneous.GetStr(tvLateraUno));
            cv.put("lateral_dos", Miscellaneous.GetStr(tvLateraDos));
            cv.put("calle_trasera", Miscellaneous.GetStr(tvTrasera));
            cv.put("referencias", Miscellaneous.GetStr(etReferencia));
            cv.put("estatus_completado", 1);
            cv.put("caracteristicas_domicilio", Miscellaneous.GetStr(etCaracteristicasDomicilio));

            db.update(TBL_CROQUIS_GPO_REN, cv, "id_integrante = ?", new String[]{id_integrante});
            save_croquis = true;
        }
        else {
            //ivError7.setVisibility(View.VISIBLE);
            ivError1.setVisibility(View.VISIBLE);
        }

        return save_croquis;
    }
    /**Funcion para validar los campos y actualizar la columnas del registro de los datos de politicas pld*/
    private boolean savePoliticas(){
        boolean save_politicas = false;
        if (rgPropietarioReal.getCheckedRadioButtonId() == R.id.rbSiPropietario ||
                rgPropietarioReal.getCheckedRadioButtonId() == R.id.rbNoPropietario){
            tvPropietarioReal.setError(null);
            if (rgProveedor.getCheckedRadioButtonId() == R.id.rbSiProveedor ||
                    rgProveedor.getCheckedRadioButtonId() == R.id.rbNoProveedor){
                tvProvedor.setError(null);
                if (rgPoliticamenteExp.getCheckedRadioButtonId() == R.id.rbSiExpuesta ||
                        rgPoliticamenteExp.getCheckedRadioButtonId() == R.id.rbNoexpuesta){
                    tvPoliticamenteExp.setError(null);
                    ivError8.setVisibility(View.GONE);
                    ContentValues cv = new ContentValues();
                    switch (rgPropietarioReal.getCheckedRadioButtonId()){
                        case R.id.rbSiPropietario:
                            cv.put("propietario_real", 1);
                            break;
                        case R.id.rbNoPropietario:
                            cv.put("propietario_real", 2);
                            break;
                    }

                    switch (rgProveedor.getCheckedRadioButtonId()){
                        case R.id.rbSiProveedor:
                            cv.put("proveedor_recursos", 1);
                            break;
                        case R.id.rbNoProveedor:
                            cv.put("proveedor_recursos", 2);
                            break;
                    }

                    switch (rgPoliticamenteExp.getCheckedRadioButtonId()){
                        case R.id.rbSiExpuesta:
                            cv.put("persona_politica", 1);
                            break;
                        case R.id.rbNoexpuesta:
                            cv.put("persona_politica", 2);
                            break;
                    }
                    cv.put("estatus_completado", 1);

                    db.update(TBL_POLITICAS_PLD_INTEGRANTE_REN, cv, "id_integrante = ?", new String[]{id_integrante});

                    save_politicas = true;
                }
                else{
                    ivError8.setVisibility(View.VISIBLE);
                    tvPoliticamenteExp.setError("");
                }
            }
            else{
                ivError8.setVisibility(View.VISIBLE);
                tvProvedor.setError("");
            }
        }
        else{
            ivError8.setVisibility(View.VISIBLE);
            tvPropietarioReal.setError("");
        }
        return save_politicas;
    }
    /**Funcion para validar los campos y actualizar la columnas del registro de los datos de documentos*/
    private boolean saveDocumentos(){
        boolean save_documentos = false;
        if (byteIneFrontal != null){
            if (byteIneReverso != null){
                if ((isNuevo && byteCurp != null) || !isNuevo){
                    if (byteComprobante != null){
                        ivError9.setVisibility(View.GONE);
                        ContentValues cv = new ContentValues();
                        cv.put("estatus_completado", 1);

                        db.update(TBL_DOCUMENTOS_INTEGRANTE_REN, cv, "id_integrante = ?", new String[]{String.valueOf(id_integrante)});

                        save_documentos = true;
                    }
                    else{
                        tvComprobante.setError("");
                        ivError9.setVisibility(View.VISIBLE);
                    }
                }
                else{
                    tvCurp.setError("");
                    ivError9.setVisibility(View.VISIBLE);
                }
            }
            else{
                tvIneReverso.setError("");
                ivError9.setVisibility(View.VISIBLE);
            }
        }
        else {
            tvIneFrontal.setError(null);
            ivError9.setVisibility(View.VISIBLE);
        }

        return save_documentos;
    }

    /**funcion para mostrar u ocultar el menu cuando ya fue guardada la solicitud*/
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

    /**funcion para los eventos del menu*/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:/**menu de retroceso en toolbar <-*/
                finish();
                break;
            case R.id.save:/**menu de guardar la solicitud*/
                /**Funcion para guardar los datos de las secciones y obtener un estatus
                 * para saber si guardo o no los datos*/
                boolean datos_personales = saveDatosIntegrante();
                boolean datos_telefonicos = saveDatosTelefonicos();
                boolean datos_domiclio = saveDatosDomicilio();
                boolean datos_negocio = saveDatosNegocio();
                boolean datos_conyuge = false;
                boolean datos_croquis = true;

                if (cbCasaReuniones.isChecked())
                    datos_croquis = saveCroquis();

                if(datos_telefonicos && datos_domiclio && datos_personales)
                {
                    if(cbCasaReuniones.isChecked() && datos_croquis)
                    {
                        ivError1.setVisibility(View.GONE);
                    }

                    if(!cbCasaReuniones.isChecked())
                    {
                        ivError1.setVisibility(View.GONE);
                    }
                }

                /**se valida el estado civil del intengrante para saber si va a guardar datos del conyige*/
                if (Miscellaneous.GetStr(tvEstadoCivilCli).equals("CASADO(A)") || Miscellaneous.GetStr(tvEstadoCivilCli).equals("UNIÓN LIBRE")){
                    datos_conyuge = saveConyuge();
                }
                else
                    datos_conyuge = true;

                boolean datos_otros = saveDatosOtros();

                /**Se valida si esta seleccionado el check de la casa de reuniones
                 * para guardar datos del croquis*/


                boolean datos_politicas = savePoliticas();
                boolean datos_documentos = saveDocumentos();

                /**Se valida si todos los estatus de las secciones que fueron guardados*/
                if (datos_personales && datos_telefonicos && datos_domiclio && datos_negocio &&
                        datos_conyuge && datos_otros && datos_croquis && datos_politicas && datos_documentos){
                    /**se actualizan los estatus a completado para que despues no pueda hacer modificaciones*/
                    Update("estatus_completado", TBL_INTEGRANTES_GPO_REN, "1", "id", id_integrante);
                    /**limpa la columna de comentario de rechazo para saber que ya completo la solicitud
                     * para los casos que fueron rechazados*/
                    Update("comentario_rechazo", TBL_INTEGRANTES_GPO_REN, "","id", id_integrante);

                    finish();
                }
                else{
                    /**En caso de que alguna seccion no esta completada mostrara un mensaje*/
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
                }

                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**Funcion de respuesta a la peticion de registro de nombre y cargo del integrante*/
    @Override
    public void onComplete(long id_integrante, String nombre, String paterno, String materno, String cargo) {
        /**se valida si se crearon los registros de las secciones(datos personales, domicilio...)*/
        if (id_integrante > 0) {
            Log.e("id_Credito", "cccc"+id_integrante);
            this.id_integrante = String.valueOf(id_integrante);
            tvCargo.setText(cargo);
            etNombreCli.setText(nombre);
            etNombreCli.setEnabled(false);
            etApPaternoCli.setText(paterno);
            etApPaternoCli.setEnabled(false);
            etApMaternoCli.setText(materno);
            etApMaternoCli.setEnabled(false);
            /**Consulta para validar si ya hay una casa para reuniones para deshabilitar el checkbox*/
            Cursor row_casa = dBhelper.customSelect(TBL_INTEGRANTES_GPO_REN + " AS i INNER JOIN " + TBL_OTROS_DATOS_INTEGRANTE_REN + " AS od ON od.id_integrante = i.id", "i.id", " WHERE i.id_credito = " + id_credito + " AND od.casa_reunion = 1", "", null);
            row_casa.moveToFirst();
            if (row_casa.getCount() > 0 && row_casa.getInt(0) != id_integrante){
                cbCasaReuniones.setEnabled(false);
            }
            if(this.id_integrante.equals("")){
                isNuevo = true;
                llCurp.setVisibility(View.VISIBLE);
            }
            else llCurp.setVisibility(View.GONE);

            if(isNuevo) Log.e("AQUI", "cliente nuevo");
            else Log.e("AQUI", "cliente renovado");
        }
        else {
            /**en caso de que cancelo el registo cierra el formulario de solicitud*/
            finish();
        }
    }

    /**funcion para recibir las respuestas a las peticiones de captura de fotografias, colonias, localidades, estados etc...*/
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ContentValues cv;
        switch (requestCode) {
            case Constants.REQUEST_CODE_ESTADO_NAC:/**obtiene las respuesta a la peticion al estado de nacimiento del cliente*/
                if (resultCode == Constants.REQUEST_CODE_ESTADO_NAC) {/**valida el codigo de respuesta*/
                    if (data != null) {/**valida que la respuesta contenga un valor*/
                        tvEstadoNacCli.setError(null);
                        /**coloca la respuesta en el campo*/
                        tvEstadoNacCli.setText(((ModeloCatalogoGral) data.getSerializableExtra(ITEM)).getNombre());

                        /**crea un map con los campos necesarios para generar la curp del cliente*/
                        HashMap<Integer, String> params = new HashMap<>();
                        params.put(0, Miscellaneous.GetStr(etNombreCli));
                        params.put(1, Miscellaneous.GetStr(etApPaternoCli));
                        params.put(2, Miscellaneous.GetStr(etApMaternoCli));
                        params.put(3, Miscellaneous.GetStr(tvFechaNacCli));

                        if (rgGeneroCli.getCheckedRadioButtonId() == R.id.rbHombre)
                            params.put(4, "Hombre");
                        else if (rgGeneroCli.getCheckedRadioButtonId() == R.id.rbMujer)
                            params.put(4, "Mujer");
                        else
                            params.put(4, "");

                        if (!Miscellaneous.GetStr(tvEstadoNacCli).isEmpty())
                            params.put(5, Miscellaneous.GetStr(tvEstadoNacCli));
                        else
                            params.put(5, "");

                        Update("estado_nacimiento", TBL_INTEGRANTES_GPO_REN, Miscellaneous.GetStr(tvEstadoNacCli), "id", id_integrante);

                        etCurpCli.setText(Miscellaneous.GenerarCurp(params));
                    }
                }
                break;
            case Constants.REQUEST_CODE_CAMARA_FACHADA_CLI:/**obtiene la respuesta a la peticion a la fotografia de fachada del cliente*/
                if (resultCode == Activity.RESULT_OK){/**valida el codigo de respuesta*/
                    if (data != null){/**valida que la respuesta tenga informacion*/
                        tvFachadaCli.setError(null);
                        ibFotoFachCli.setVisibility(View.GONE);
                        ivFotoFachCli.setVisibility(View.VISIBLE);
                        /**coloca la respuesta en una variable*/
                        byteFotoFachCli = data.getByteArrayExtra(Constants.PICTURE);
                        /**coloca la respuesta en el contenedor del imageview*/
                        Glide.with(ctx).load(byteFotoFachCli).centerCrop().into(ivFotoFachCli);
                        try {
                            /**actualiza la columna con el nombre de la imagen cuando se guarda la foto*/
                            Update("foto_fachada", TBL_DOMICILIO_INTEGRANTE_REN, Miscellaneous.save(byteFotoFachCli, 1), "id_integrante", id_integrante);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                break;
            case Constants.REQUEST_CODE_COLONIA_CLIE:/**obtiene respuesta a la peticion de colonia del cliente*/
                if (resultCode == Constants.REQUEST_CODE_COLONIA_CLIE){/**valida el codigo de respuesta*/
                    if (data != null){/**valida que la respuesta contenga un valor*/
                        tvColoniaCli.setError(null);
                        /**coloca la respuesta en el campo*/
                        tvColoniaCli.setText(((ModeloCatalogoGral)data.getSerializableExtra(ITEM)).getNombre());
                        /**actualiza la columna con la respuesta*/
                        Update("colonia", TBL_DOMICILIO_INTEGRANTE_REN, Miscellaneous.GetStr(tvColoniaCli), "id_integrante", id_integrante);

                    }
                }
                break;
            case REQUEST_CODE_LOCALIDAD_CLIE:/**obtiene respuesta a la peticion de localidad del cliente*/
                if (resultCode == REQUEST_CODE_LOCALIDAD_CLIE){/**valida el codigo de respuesta*/
                    if (data != null){/**valida que la respuesta contenga un valor*/
                        tvLocalidadCli.setError(null);
                        /**coloca la respuesta en el campo*/
                        tvLocalidadCli.setText(((ModeloCatalogoGral)data.getSerializableExtra(ITEM)).getNombre());
                        /**actualiza la columna con la respuesta*/
                        Update("localidad", TBL_DOMICILIO_INTEGRANTE_REN, Miscellaneous.GetStr(tvLocalidadCli), "id_integrante", id_integrante);
                    }
                }
                break;
            case REQUEST_CODE_COLONIA_CONY:/**obtiene respuesta a la peticion de colonia del conyuge*/
                if (resultCode == REQUEST_CODE_COLONIA_CONY){/**valida el codigo de respuesta*/
                    if (data != null){/**valida que la respuesta contenga un valor*/
                        tvColoniaCony.setError(null);
                        /**coloca la respuesta en el campo*/
                        tvColoniaCony.setText(((ModeloCatalogoGral)data.getSerializableExtra(ITEM)).getNombre());
                        /**actualiza la columna con la respuesta*/
                        Update("colonia", TBL_CONYUGE_INTEGRANTE_REN, Miscellaneous.GetStr(tvColoniaCony), "id_integrante", id_integrante);
                    }
                }
                break;
            case REQUEST_CODE_LOCALIDAD_CONY:/**obtiene respuesta a la peticion de localidad del conyuge*/
                if (resultCode == REQUEST_CODE_LOCALIDAD_CONY){/**valida el codigo de respuesta*/
                    if (data != null){/**valida que la respuesta contenga un valor*/
                        tvLocalidadCony.setError(null);
                        /**coloca la respuesta en el campo*/
                        tvLocalidadCony.setText(((ModeloCatalogoGral)data.getSerializableExtra(ITEM)).getNombre());
                        /**actualiza la columna con la respuesta*/
                        Update("localidad", TBL_CONYUGE_INTEGRANTE_REN, Miscellaneous.GetStr(tvLocalidadCony), "id_integrante", id_integrante);
                    }
                }
                break;
            case Constants.REQUEST_CODE_ACTIVIDAD_NEG:/**obtiene respuesta a la peticion de actividad de negocio*/
                if (resultCode == Constants.REQUEST_CODE_ACTIVIDAD_NEG){/**valida el codigo de respuesta*/
                    if (data != null){/**valida que la respuesta contenga un valor*/
                        tvActEconomicaNeg.setError(null);
                        /**coloca la respuesta en el campo*/
                        tvActEconomicaNeg.setText(((ModeloCatalogoGral)data.getSerializableExtra(ITEM)).getNombre());
                        /**actualiza la columna con la respuesta*/
                        Update("actividad_economica", TBL_NEGOCIO_INTEGRANTE_REN, Miscellaneous.GetStr(tvActEconomicaNeg), "id_integrante", id_integrante);

                    }
                }
                break;
            case Constants.REQUEST_CODE_COLONIA_NEG:/**obtiene respuesta a la peticion de colonia del negocio*/
                if (resultCode == Constants.REQUEST_CODE_COLONIA_NEG){/**valida el codigo de respuesta*/
                    if (data != null){/**valida que la respuesta contenga un valor*/
                        tvColoniaNeg.setError(null);
                        /**coloca la respuesta en el campo*/
                        tvColoniaNeg.setText(((ModeloCatalogoGral)data.getSerializableExtra(ITEM)).getNombre());
                        /**actualiza la columna con la respuesta*/
                        Update("colonia", TBL_NEGOCIO_INTEGRANTE_REN, Miscellaneous.GetStr(tvColoniaNeg), "id_integrante", id_integrante);

                    }
                }
                break;
            case REQUEST_CODE_LOCALIDAD_NEG:/**obtiene respuesta a la peticion de localidad del negocio*/
                if (resultCode == REQUEST_CODE_LOCALIDAD_NEG){/**valida el codigo de respuesta*/
                    if (data != null){/**valida que la respuesta contenga un valor*/
                        tvLocalidadNeg.setError(null);
                        /**coloca la respuesta en el campo*/
                        tvLocalidadNeg.setText(((ModeloCatalogoGral)data.getSerializableExtra(ITEM)).getNombre());
                        /**actualiza la columna con la respuesta*/
                        Update("localidad", TBL_NEGOCIO_INTEGRANTE_REN,tvLocalidadNeg.getText().toString().trim().toUpperCase(), "id_integrante", id_integrante);
                    }
                }
                break;
            case REQUEST_CODE_OCUPACION_NEG:/**obtiene respuesta a la peticion de ocupacion del negocio*/
                if (resultCode == REQUEST_CODE_OCUPACION_NEG){/**valida el codigo de respuesta*/
                    if (data != null){/**valida que la respuesta contenga un valor*/
                        tvActEcoEspNeg.setError(null);
                        /**coloca la respuesta en el campo*/
                        tvActEcoEspNeg.setText(((ModeloCatalogoGral)data.getSerializableExtra(ITEM)).getNombre());
                        /**busca el sector dependiendo de la actividad economica seleccionada*/
                        Cursor row = dBhelper.getRecords(SECTORES, " WHERE sector_id = " + (((ModeloCatalogoGral)data.getSerializableExtra(ITEM)).getExtra())+"","",null);
                        if (row.getCount() > 0){
                            row.moveToFirst();
                            tvActEconomicaNeg.setText(row.getString(2));
                        }
                        row.close();
                        /**actualiza las columnas*/
                        Update("ocupacion", TBL_NEGOCIO_INTEGRANTE_REN, Miscellaneous.GetStr(tvActEcoEspNeg), "id_integrante", id_integrante);
                        Update("actividad_economica", TBL_NEGOCIO_INTEGRANTE_REN, Miscellaneous.GetStr(tvActEconomicaNeg), "id_integrante", id_integrante);
                    }
                }
                break;
            case Constants.REQUEST_CODE_CAMARA_FACHADA_NEG:/**Obtiene respuesta a la peticion de fotografia de fachada del negocio*/
                if (resultCode == Activity.RESULT_OK){/**valida el codigo de respuesta*/
                    if (data != null){/**valida si la respuesta contiene un valor*/
                        tvFachadaNeg.setError(null);
                        ibFotoFachNeg.setVisibility(View.GONE);
                        ivFotoFachNeg.setVisibility(View.VISIBLE);
                        /**coloca la respuesta en una variable*/
                        byteFotoFachNeg = data.getByteArrayExtra(Constants.PICTURE);
                        /**coloca la respuesta en el contenedor del imageView*/
                        Glide.with(ctx).load(byteFotoFachNeg).centerCrop().into(ivFotoFachNeg);
                        try {
                            /**actualiza la columna con el nombre de la imagen que se guardo*/
                            Update("foto_fachada", TBL_NEGOCIO_INTEGRANTE_REN, Miscellaneous.save(byteFotoFachNeg, 1), "id_integrante", id_integrante);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                }
                break;
            case Constants.REQUEST_CODE_OCUPACION_CONY:/**Obtiene respuesta a la peticion de ocupacion del conyuge*/
                if (resultCode == Constants.REQUEST_CODE_OCUPACION_CONY){/**valida la respuesta*/
                    if (data != null){/**valida que la respuesta contenga un valor*/
                        tvOcupacionCony.setError(null);
                        /**coloca la respuesta en el campo*/
                        tvOcupacionCony.setText(((ModeloCatalogoGral)data.getSerializableExtra(ITEM)).getNombre());
                        /**actualiza la columna con la respuesta*/
                        Update("ocupacion", TBL_CONYUGE_INTEGRANTE_REN, tvOcupacionCony.getText().toString().trim().toUpperCase(), "id_integrante", id_integrante);

                    }
                }
                break;
            case Constants.REQUEST_CODE_OCUPACION_CLIE:/**Obtiene respuesta a la peticion de ocupacion del cliente*/
                if (resultCode == Constants.REQUEST_CODE_OCUPACION_CLIE){/**valida la respuesta*/
                    if (data != null){/**valida que la respuesta contenga un valor*/
                        tvOcupacionCli.setError(null);
                        /**coloca la respuesta en el campo*/
                        tvOcupacionCli.setText(((ModeloCatalogoGral)data.getSerializableExtra(ITEM)).getNombre());
                        /**actualiza la columna con la respuesta*/
                        Update("ocupacion", TBL_INTEGRANTES_GPO_REN, tvOcupacionCli.getText().toString().trim().toUpperCase(), "id", id_integrante);
                    }
                }
                break;
            case REQUEST_CODE_FIRMA_CLI:/**obtiene la respuesta a la peticion de firma del cliente*/
                if (resultCode == Activity.RESULT_OK){/**valida el codigo de respuesta*/
                    if (data != null){/**valida que la respuesta contenga un valor*/
                        tvFirmaCli.setError(null);
                        ibFirmaCli.setVisibility(View.GONE);
                        ivFirmaCli.setVisibility(View.VISIBLE);
                        /**coloca la respuesta en el contenedor del imageview*/
                        Glide.with(ctx)
                                .load(data.getByteArrayExtra(Constants.FIRMA_IMAGE))
                                .into(ivFirmaCli);
                        /**coloca la respuesta en una variable*/
                        byteFirmaCli = data.getByteArrayExtra(Constants.FIRMA_IMAGE);
                        try {
                            /**actualiza la columna con el nombre de la imagen que se guardo*/
                            Update("firma", TBL_OTROS_DATOS_INTEGRANTE_REN, Miscellaneous.save(byteFirmaCli, 3), "id_integrante", id_integrante);
                            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                            if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) ObtenerUbicacionFirmaCliente();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                break;
            case REQUEST_CODE_FOTO_INE_SELFIE:
                if (resultCode == Activity.RESULT_OK){
                    if (data != null){
                        tvIneSelfie.setError(null);
                        ibIneSelfie.setVisibility(View.GONE);
                        ivIneSelfie.setVisibility(View.VISIBLE);
                        byteIneSelfie = data.getByteArrayExtra(PICTURE);
                        Glide.with(ctx).load(byteIneSelfie).centerCrop().into(ivIneSelfie);
                        try {
                            Update("ine_selfie", TBL_DOCUMENTOS_INTEGRANTE_REN, Miscellaneous.save(byteIneSelfie, 4), "id_integrante", id_integrante);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                break;
            case REQUEST_CODE_FOTO_INE_FRONTAL:/**obtiene la respuesta a la peticion de fotografia del ine frontal*/
                if (resultCode == RESULT_SCAN_SUPPRESSED){/**valida el codigo de respuesta*/
                    if (data != null){/**valida que la respuesta contenga un valor*/
                        tvIneFrontal.setError(null);
                        /** se extrae la imagen que se capturo de tipo byte array*/
                        Bitmap cardIneFrontal = CardIOActivity.getCapturedCardImage(data);
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        cardIneFrontal.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                        /**coloca la respuesta en una variable*/
                        byteIneFrontal =  baos.toByteArray();
                        ibIneFrontal.setVisibility(View.GONE);
                        ivIneFrontal.setVisibility(View.VISIBLE);
                        /**coloca la respuesta en el contenedor del imageView*/
                        Glide.with(ctx).load(byteIneFrontal).centerCrop().into(ivIneFrontal);
                        try {
                            /**actualiza la columna con el nombre que se guardo la imagen*/
                            Update("ine_frontal", TBL_DOCUMENTOS_INTEGRANTE_REN, Miscellaneous.save(byteIneFrontal, 4), "id_integrante", id_integrante);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                }
                break;
            case REQUEST_CODE_FOTO_INE_REVERSO:/**obtiene la respuesta a la peticion de fotografia del ine reverso*/
                if (resultCode == RESULT_SCAN_SUPPRESSED){/**valida el codigo de respuesta*/
                    if (data != null){/**valida que la respuesta contenga un valor*/
                        tvIneReverso.setError(null);
                        /** se extrae la imagen que se capturo de tipo byte array*/
                        Bitmap cardIneReverso = CardIOActivity.getCapturedCardImage(data);
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        cardIneReverso.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                        /**coloca la respuesta en una variable*/
                        byteIneReverso =  baos.toByteArray();
                        ibIneReverso.setVisibility(View.GONE);
                        ivIneReverso.setVisibility(View.VISIBLE);
                        /**coloca la respuesta en el contenedor del imageView*/
                        Glide.with(ctx).load(byteIneReverso).centerCrop().into(ivIneReverso);
                        try {
                            /**actualiza la columna con el nombre que se guardo la imagen*/
                            Update("ine_reverso", TBL_DOCUMENTOS_INTEGRANTE_REN, Miscellaneous.save(byteIneReverso, 4), "id_integrante", id_integrante);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                }
                break;
            case REQUEST_CODE_FOTO_CURP:/**obtiene respuesta a la peticion de captura de fotografia de la curp*/
                if (resultCode == Activity.RESULT_OK){/**valida el codigo de respuesta*/
                    if (data != null){/**valida que la respuesta contenga un valor*/
                        tvCurp.setError(null);
                        ibCurp.setVisibility(View.GONE);
                        ivCurp.setVisibility(View.VISIBLE);
                        /**coloca la respuesta en una variable*/
                        byteCurp = data.getByteArrayExtra(Constants.PICTURE);
                        /**coloca la respuesta en el contenedor del imageView*/
                        Glide.with(ctx).load(byteCurp).centerCrop().into(ivCurp);
                        try {
                            /**actualiza la columna con el nombre que se guardo la imagen*/
                            Update("curp", TBL_DOCUMENTOS_INTEGRANTE_REN, Miscellaneous.save(byteCurp, 4), "id_integrante", id_integrante);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                }
                break;
            case REQUEST_CODE_FOTO_COMPROBATE:/**obtiene respuesta a la peticion de captura de fotografia de la curp*/
                if (resultCode == Activity.RESULT_OK){/**valida el codigo de respuesta*/
                    if (data != null){/**valida que la respuesta contenga un valor*/
                        tvComprobante.setError(null);
                        ibComprobante.setVisibility(View.GONE);
                        ivComprobante.setVisibility(View.VISIBLE);
                        /**coloca la respuesta en una variable*/
                        byteComprobante = data.getByteArrayExtra(Constants.PICTURE);
                        /**coloca la respuesta en el contenedor del imageView*/
                        Glide.with(ctx).load(byteComprobante).centerCrop().into(ivComprobante);
                        try {
                            /**actualiza la columna con el nombre que se guardo la imagen*/
                            Update("comprobante", TBL_DOCUMENTOS_INTEGRANTE_REN, Miscellaneous.save(byteComprobante, 4), "id_integrante", id_integrante);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                break;
        }
    }

    /**funcion para actualizar las columnas de los registros de diferentes tablas*/
    private void Update(String key, String tabla, String value, String param, String id) {
        Log.e("update", key+": "+value);
        ContentValues cv = new ContentValues();
        cv.put(key, value);
        db.update(tabla, cv, param+" = ?", new String[]{String.valueOf(id)});

    }

    /**funcion del evento click del boton de retroceso del dispositivo*/
    @Override
    public void onBackPressed() {
        if (is_edit) {
            AlertDialog solicitud = Popups.showDialogConfirm(this, warning,
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
            Objects.requireNonNull(solicitud.getWindow()).requestFeature(Window.FEATURE_NO_TITLE);
            solicitud.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            solicitud.setCancelable(true);
            solicitud.show();
        }
        else
            finish();
    }

    /**Funcion para mostrar mensajes en un alertDialog*/
    private void ShowMensajes(String mensaje, String tipo){
        final AlertDialog solicitud;
        solicitud = Popups.showDialogMessage(this, warning,
                mensaje, R.string.accept, new Popups.DialogMessage() {
                    @Override
                    public void OnClickListener(AlertDialog dialog) {
                        dialog.dismiss();
                    }
                });
        solicitud.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        solicitud.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        solicitud.show();

        if (tipo.equals("NEGOCIO") && etCapacidadPagoNeg.isEnabled())
            etCapacidadPagoNeg.setText("");

    }

    /**Funcion que recibe la respuesta los nombres de las calles del croquis*/
    public void setCalle (String calle, String tipo){
        switch (tipo){
            case "PRINCIPAL":
                tvPrincipal.setError(null);
                tvPrincipal.setText(calle);
                Update("calle_principal",TBL_CROQUIS_GPO_REN, calle.trim().toUpperCase(), "id_integrante", id_integrante);
                break;
            case "TRASERA":
                tvTrasera.setError(null);
                tvTrasera.setText(calle);
                Update("calle_trasera",TBL_CROQUIS_GPO_REN, calle.trim().toUpperCase(), "id_integrante", id_integrante);
                break;
            case "LATERAL UNO":
                tvLateraUno.setError(null);
                tvLateraUno.setText(calle);
                Update("lateral_uno",TBL_CROQUIS_GPO_REN, calle.trim().toUpperCase(), "id_integrante", id_integrante);
                break;
            case "LATERAL DOS":
                tvLateraDos.setError(null);
                tvLateraDos.setText(calle);
                Update("lateral_dos",TBL_CROQUIS_GPO_REN, calle.trim().toUpperCase(), "id_integrante", id_integrante);
                break;
        }
    }

}
