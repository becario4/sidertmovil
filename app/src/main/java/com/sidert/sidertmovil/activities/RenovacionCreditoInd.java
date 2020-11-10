package com.sidert.sidertmovil.activities;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
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
import android.support.v4.content.ContextCompat;
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
import com.sidert.sidertmovil.fragments.dialogs.dialog_input_calle;
import com.sidert.sidertmovil.fragments.dialogs.dialog_time_picker;
import com.sidert.sidertmovil.models.ModeloCatalogoGral;
import com.sidert.sidertmovil.utils.Miscellaneous;
import com.sidert.sidertmovil.utils.MyCurrentListener;
import com.sidert.sidertmovil.utils.NameFragments;
import com.sidert.sidertmovil.utils.NetworkStatus;
import com.sidert.sidertmovil.utils.Popups;
import com.sidert.sidertmovil.utils.Servicios_Sincronizado;
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
import java.util.Locale;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.card.payment.CardIOActivity;

import static com.sidert.sidertmovil.database.SidertTables.SidertEntry.TABLE_MUNICIPIOS;
import static com.sidert.sidertmovil.utils.Constants.CALLE;
import static com.sidert.sidertmovil.utils.Constants.CATALOGO;
import static com.sidert.sidertmovil.utils.Constants.COLONIA;
import static com.sidert.sidertmovil.utils.Constants.COLONIAS;
import static com.sidert.sidertmovil.utils.Constants.DATE_CURRENT;
import static com.sidert.sidertmovil.utils.Constants.DAY_CURRENT;
import static com.sidert.sidertmovil.utils.Constants.ESTADOS;
import static com.sidert.sidertmovil.utils.Constants.EXTRA;
import static com.sidert.sidertmovil.utils.Constants.FECHAS_POST;
import static com.sidert.sidertmovil.utils.Constants.FIRMA_IMAGE;
import static com.sidert.sidertmovil.utils.Constants.FORMAT_DATE_GNRAL;
import static com.sidert.sidertmovil.utils.Constants.IDENTIFIER;
import static com.sidert.sidertmovil.utils.Constants.IMAGEN;
import static com.sidert.sidertmovil.utils.Constants.ITEM;
import static com.sidert.sidertmovil.utils.Constants.LOCALIDADES;
import static com.sidert.sidertmovil.utils.Constants.MONTH_CURRENT;
import static com.sidert.sidertmovil.utils.Constants.OCUPACIONES;
import static com.sidert.sidertmovil.utils.Constants.ORDER_ID;
import static com.sidert.sidertmovil.utils.Constants.PICTURE;
import static com.sidert.sidertmovil.utils.Constants.REQUEST_CODE;
import static com.sidert.sidertmovil.utils.Constants.REQUEST_CODE_ACTIVIDAD_NEG;
import static com.sidert.sidertmovil.utils.Constants.REQUEST_CODE_CAMARA_FACHADA_AVAL;
import static com.sidert.sidertmovil.utils.Constants.REQUEST_CODE_CAMARA_FACHADA_CLI;
import static com.sidert.sidertmovil.utils.Constants.REQUEST_CODE_CAMARA_FACHADA_NEG;
import static com.sidert.sidertmovil.utils.Constants.REQUEST_CODE_COLONIA_AVAL;
import static com.sidert.sidertmovil.utils.Constants.REQUEST_CODE_COLONIA_CLIE;
import static com.sidert.sidertmovil.utils.Constants.REQUEST_CODE_COLONIA_CONY;
import static com.sidert.sidertmovil.utils.Constants.REQUEST_CODE_COLONIA_NEG;
import static com.sidert.sidertmovil.utils.Constants.REQUEST_CODE_COLONIA_REF;
import static com.sidert.sidertmovil.utils.Constants.REQUEST_CODE_ESTADO_NAC;
import static com.sidert.sidertmovil.utils.Constants.REQUEST_CODE_ESTADO_NAC_AVAL;
import static com.sidert.sidertmovil.utils.Constants.REQUEST_CODE_FIRMA_ASESOR;
import static com.sidert.sidertmovil.utils.Constants.REQUEST_CODE_FIRMA_AVAL;
import static com.sidert.sidertmovil.utils.Constants.REQUEST_CODE_FIRMA_CLI;
import static com.sidert.sidertmovil.utils.Constants.REQUEST_CODE_FOTO_COMPROBATE;
import static com.sidert.sidertmovil.utils.Constants.REQUEST_CODE_FOTO_CURP;
import static com.sidert.sidertmovil.utils.Constants.REQUEST_CODE_FOTO_INE_FRONTAL;
import static com.sidert.sidertmovil.utils.Constants.REQUEST_CODE_FOTO_INE_REVERSO;
import static com.sidert.sidertmovil.utils.Constants.REQUEST_CODE_LOCALIDAD_AVAL;
import static com.sidert.sidertmovil.utils.Constants.REQUEST_CODE_LOCALIDAD_CLIE;
import static com.sidert.sidertmovil.utils.Constants.REQUEST_CODE_LOCALIDAD_CONY;
import static com.sidert.sidertmovil.utils.Constants.REQUEST_CODE_LOCALIDAD_NEG;
import static com.sidert.sidertmovil.utils.Constants.REQUEST_CODE_LOCALIDAD_REF;
import static com.sidert.sidertmovil.utils.Constants.REQUEST_CODE_OCUPACION_AVAL;
import static com.sidert.sidertmovil.utils.Constants.REQUEST_CODE_OCUPACION_CLIE;
import static com.sidert.sidertmovil.utils.Constants.REQUEST_CODE_OCUPACION_CONY;
import static com.sidert.sidertmovil.utils.Constants.REQUEST_CODE_OCUPACION_NEG;
import static com.sidert.sidertmovil.utils.Constants.ROOT_PATH;
import static com.sidert.sidertmovil.utils.Constants.SECTORES;
import static com.sidert.sidertmovil.utils.Constants.TBL_AVAL_IND_REN;
import static com.sidert.sidertmovil.utils.Constants.TBL_CLIENTE_IND_REN;
import static com.sidert.sidertmovil.utils.Constants.TBL_CONYUGE_IND_REN;
import static com.sidert.sidertmovil.utils.Constants.TBL_CREDITO_IND_REN;
import static com.sidert.sidertmovil.utils.Constants.TBL_CROQUIS_IND_REN;
import static com.sidert.sidertmovil.utils.Constants.TBL_DIRECCIONES_REN;
import static com.sidert.sidertmovil.utils.Constants.TBL_DOCUMENTOS_REN;
import static com.sidert.sidertmovil.utils.Constants.TBL_ECONOMICOS_IND_REN;
import static com.sidert.sidertmovil.utils.Constants.TBL_NEGOCIO_IND_REN;
import static com.sidert.sidertmovil.utils.Constants.TBL_POLITICAS_PLD_IND_REN;
import static com.sidert.sidertmovil.utils.Constants.TBL_REFERENCIA_IND_REN;
import static com.sidert.sidertmovil.utils.Constants.TBL_SOLICITUDES_REN;
import static com.sidert.sidertmovil.utils.Constants.TIPO;
import static com.sidert.sidertmovil.utils.Constants.TIPO_SOLICITUD;
import static com.sidert.sidertmovil.utils.Constants.TITULO;
import static com.sidert.sidertmovil.utils.Constants.YEAR_CURRENT;
import static com.sidert.sidertmovil.utils.Constants.firma;
import static com.sidert.sidertmovil.utils.Constants.question;
import static com.sidert.sidertmovil.utils.Constants.warning;
import static io.card.payment.CardIOActivity.RESULT_SCAN_SUPPRESSED;

public class RenovacionCreditoInd extends AppCompatActivity {

    private Context ctx;

    private String[] _dias_semana;
    private String[] _plazo;
    private String[] _frecuencia;
    private String[] _destino;
    private String[] _comportamiento;
    private String[] _estudios;
    private String[] _civil;
    private String[] _tipo_casa;
    private String[] _dependientes;
    private String[] _medio_contacto;
    private String[] _parentesco;
    private String[] _parentesco_casa;
    private String[] _tipo_identificacion;
    private String[] _destino_credito;
    private String[] _medios_pago;
    private String[] _riesgo;
    private String[] _confirmacion;
    private String[] _activos;

    private ArrayList<Integer> selectedItemsDias;
    private ArrayList<Integer> selectedItemsMediosPago;
    private ArrayList<Integer> selectedItemsActivos;

    private DBhelper dBhelper;
    private SQLiteDatabase db;

    private SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_DATE_GNRAL);
    private Calendar myCalendar;

    private long id_solicitud = 0;
    private String direccionIdCli, direccionIdCony, direccionIdNeg, direccionIdAval, direccionIdRef;
    private boolean is_edit = true;

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
    private FloatingActionButton btnRegresar9;


    //======== DATOS CRÉDITO  ==================
    private TextView tvPlazo;
    private TextView tvFrecuencia;
    private TextView tvFechaDesembolso;
    private TextView tvDiaDesembolso;
    private TextView tvHoraVisita;
    private EditText etMontoPrestamo;
    private TextView tvCantidadLetra;
    private TextView tvDestino;
    private TextView tvRiesgo;
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
    public byte[] byteFotoFachNeg;
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
    public byte[] byteFotoFachAval;
    private MultiAutoCompleteTextView etReferenciaAval;
    private TextView tvFirmaAval;
    private ImageButton ibFirmaAval;
    private ImageView ivFirmaAval;
    public byte[] byteFirmaAval;
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
    //========== DOCUMENTOS ==================
    private TextView tvIneFrontal;
    private ImageButton ibIneFrontal;
    private ImageView ivIneFrontal;
    public byte[] byteIneFrontal;
    private TextView tvIneReverso;
    private ImageButton ibIneReverso;
    private ImageView ivIneReverso;
    public byte[] byteIneReverso;
    private LinearLayout llFotoCurp;
    /*private TextView tvCurp;
    private ImageButton ibCurp;
    private ImageView ivCurp;
    public byte[] byteCurp;*/
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
    private ImageView ivError10;
    //===================================================
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

    DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
    DecimalFormat df = new DecimalFormat("##,###.##", symbols);
    DecimalFormat dfnd = new DecimalFormat("#,###", symbols);

    //========== is edit fields
    boolean isEditCre = true;
    boolean isEditCli = true;
    boolean isEditCon = true;
    boolean isEditEco = true;
    boolean isEditNeg = true;
    boolean isEditAva = true;
    boolean isEditRef = true;
    boolean isEditCro = true;
    boolean isEditPol = true;
    boolean isEditDoc = true;

    boolean hasFractionalPart = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_renovacion_credito_ind);

        ProgressDialog dialog = ProgressDialog.show(RenovacionCreditoInd.this, "",
                "Cargando la información por favor espere...", true);
        dialog.show();
        df.setDecimalSeparatorAlwaysShown(true);

        ctx = this;

        dBhelper = new DBhelper(ctx);
        db = dBhelper.getWritableDatabase();

        _dias_semana = getResources().getStringArray(R.array.dias_semana);

        myCalendar = Calendar.getInstance();

        validator = new Validator();
        validatorTV = new ValidatorTextView();

        TBmain = findViewById(R.id.TBmain);

        _plazo                  = getResources().getStringArray(R.array.intervalo);
        _frecuencia             = getResources().getStringArray(R.array.lapso);
        _destino                = getResources().getStringArray(R.array.destino_prestamo);
        _estudios               = Miscellaneous.GetNivelesEstudio(ctx);
        _civil                  = Miscellaneous.GetEstadoCiviles(ctx);
        _tipo_identificacion    = Miscellaneous.GetIdentificacion(ctx);
        _medios_pago            = Miscellaneous.GetMediosPagoOri(ctx);
        _parentesco             = Miscellaneous.GetParentesco(ctx, "PARENTESCO");
        _parentesco_casa        = Miscellaneous.GetParentesco(ctx, "CASA");
        _tipo_casa              = Miscellaneous.GetViviendaTipos(ctx);
        _medio_contacto         = Miscellaneous.GetMediosContacto(ctx);
        _destino_credito        = Miscellaneous.GetDestinosCredito(ctx);
        _dependientes           = getResources().getStringArray(R.array.dependientes_eco);
        _riesgo                 = getResources().getStringArray(R.array.clasificacion_riesgo);
        _confirmacion           = getResources().getStringArray(R.array.confirmacion);
        _activos                = getResources().getStringArray(R.array.activos);
        _comportamiento         = getResources().getStringArray(R.array.comportamiento_pago);

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
        tvRiesgo            = findViewById(R.id.tvRiesgo);
        tvCreditoAnterior   = findViewById(R.id.tvCreditoAnterior);
        tvNumCiclo          = findViewById(R.id.tvNumCiclo);
        tvComportamiento    = findViewById(R.id.tvComportamientoPago);
        etObservaciones     = findViewById(R.id.etObservaciones);
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
        ibFotoFachNeg           = findViewById(R.id.ibFotoFachNeg);
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
        ivFotoFachAval      = findViewById(R.id.ivFotoFachAval);
        etReferenciaAval    = findViewById(R.id.etReferenciaAval);
        tvFirmaAval         = findViewById(R.id.tvFirmaAval);
        ibFirmaAval         = findViewById(R.id.ibFirmaAval);
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
        //=====================================  DOCUMENTOS  =======================================
        tvIneFrontal        = findViewById(R.id.tvIneFrontal);
        ibIneFrontal        = findViewById(R.id.ibIneFrontal);
        ivIneFrontal        = findViewById(R.id.ivIneFrontal);
        tvIneReverso        = findViewById(R.id.tvIneReverso);
        ibIneReverso        = findViewById(R.id.ibIneReverso);
        ivIneReverso        = findViewById(R.id.ivIneReverso);
        llFotoCurp          = findViewById(R.id.llFotoCurp);
        /*tvCurp              = findViewById(R.id.tvCurp);
        ibCurp              = findViewById(R.id.ibCurp);
        ivCurp              = findViewById(R.id.ivCurp);*/
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
        llCroquis.setOnClickListener(llCroquis_OnClick);
        llPoliticas.setOnClickListener(llPoliticas_OnClick);
        llDocumentos.setOnClickListener(llDocumentos_OnClick);

        mapCli.onCreate(savedInstanceState);
        mapNeg.onCreate(savedInstanceState);
        mapAval.onCreate(savedInstanceState);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        //==========================================================================================

        llFotoCurp.setVisibility(View.GONE);
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

        btnRegresar1 = findViewById(R.id.btnRegresar1);
        btnRegresar2 = findViewById(R.id.btnRegresar2);
        btnRegresar3 = findViewById(R.id.btnRegresar3);
        btnRegresar4 = findViewById(R.id.btnRegresar4);
        btnRegresar5 = findViewById(R.id.btnRegresar5);
        btnRegresar6 = findViewById(R.id.btnRegresar6);
        btnRegresar7 = findViewById(R.id.btnRegresar7);
        btnRegresar8 = findViewById(R.id.btnRegresar8);
        btnRegresar9 = findViewById(R.id.btnRegresar9);
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
        ivError10 = findViewById(R.id.ivError10);
        //=========================================================

        //================================= FLOATING BUTTON LISTENER  ==============================
        btnContinuar0.setOnClickListener(btnContinuar0_OnClick);
        btnContinuar1.setOnClickListener(btnContinuar1_OnClick);
        btnContinuar2.setOnClickListener(btnContinuar2_OnClick);
        btnContinuar3.setOnClickListener(btnContinuar3_OnClick);
        btnContinuar4.setOnClickListener(btnContinuar4_OnClick);
        btnContinuar5.setOnClickListener(btnContinuar5_OnClick);
        btnContinuar6.setOnClickListener(btnContinuar6_OnClick);
        btnContinuar7.setOnClickListener(btnContinuar7_OnClick);
        btnContinuar8.setOnClickListener(btnContinuar8_OnClick);

        btnRegresar1.setOnClickListener(btnRegresar1_OnClick);
        btnRegresar2.setOnClickListener(btnRegresar2_OnClick);
        btnRegresar3.setOnClickListener(btnRegresar3_OnClick);
        btnRegresar4.setOnClickListener(btnRegresar4_OnClick);
        btnRegresar5.setOnClickListener(btnRegresar5_OnClick);
        btnRegresar6.setOnClickListener(btnRegresar6_OnClick);
        btnRegresar7.setOnClickListener(btnRegresar7_OnClick);
        btnRegresar8.setOnClickListener(btnRegresar8_OnClick);
        btnRegresar9.setOnClickListener(btnRegresar9_OnClick);

        dialog.dismiss();
        //============================================================================================
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
                if (s.toString().contains(String.valueOf(df.getDecimalFormatSymbols().getDecimalSeparator())))
                {
                    hasFractionalPart = true;
                } else {
                    hasFractionalPart = false;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                etMontoPrestamo.removeTextChangedListener(this);

                try {
                    int inilen, endlen;
                    inilen = etMontoPrestamo.getText().length();
                    String v = s.toString().replace(String.valueOf(df.getDecimalFormatSymbols().getGroupingSeparator()), "");
                    Number n = df.parse(v);
                    int cp = etMontoPrestamo.getSelectionStart();
                    if (hasFractionalPart) {
                        etMontoPrestamo.setText(df.format(n));
                    } else {
                        etMontoPrestamo.setText(dfnd.format(n));
                    }
                    endlen = etMontoPrestamo.getText().length();
                    int sel = (cp + (endlen - inilen));
                    if (sel > 0 && sel <= etMontoPrestamo.getText().length()) {
                        etMontoPrestamo.setSelection(sel);
                    } else {
                        // place cursor at the end?
                        etMontoPrestamo.setSelection(etMontoPrestamo.getText().length() - 1);
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
                        etMontoPrestamo.setError("La cantidad no corresponde a un monto de crédito válido");
                    }else{
                        Update("monto_prestamo", TBL_CREDITO_IND_REN, s.toString().trim().replace(",",""));
                        tvCantidadLetra.setText((Miscellaneous.cantidadLetra(s.toString().replace(",","")).toUpperCase() + " PESOS MEXICANOS ").replace("  ", " "));
                        if (Integer.parseInt(s.toString().replace(",","")) > 30000){
                            llPropiedades.setVisibility(View.VISIBLE);
                        }
                        else{
                            llPropiedades.setVisibility(View.GONE);
                        }
                    }
                }
                else{
                    Update("monto_prestamo", TBL_CREDITO_IND_REN, "");
                    tvCantidadLetra.setText("");
                }

                etMontoPrestamo.addTextChangedListener(this);
            }
        });
        tvDestino.setOnClickListener(tvDestino_OnClick);
        tvRiesgo.setOnClickListener(tvRiesgo_OnClick);
        tvComportamiento.setOnClickListener(tvComportamiento_OnClick);
        etObservaciones.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable e) {
                if (e.length() > 0){
                    Update("observaciones", TBL_CREDITO_IND_REN, e.toString().trim().toUpperCase());
                }
                else
                    Update("observaciones", TBL_CREDITO_IND_REN, "");
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
                    etCurpCli.setText(Miscellaneous.GenerarCurp(params));
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
                    etCurpCli.setText(Miscellaneous.GenerarCurp(params));
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
                    etCurpCli.setText(Miscellaneous.GenerarCurp(params));
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
                    etCurpCli.setText(Miscellaneous.GenerarCurp(params));
                }
            }
        });
        //tvFechaNacCli.setOnClickListener(tvFechaNac_OnClick);
        //tvEstadoNacCli.setOnClickListener(etEstadoNac_OnClick);
        etCurpCli.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    if (s.toString().contains("Curp no válida")){
                        tvRfcCli.setText("Rfc no válida");
                        Update("rfc", TBL_CLIENTE_IND_REN, "");
                        Update("curp", TBL_CLIENTE_IND_REN, "");
                    }
                    else{
                        Update("curp", TBL_CLIENTE_IND_REN, s.toString().trim().toUpperCase());
                        if (s.toString().trim().length() >= 10) {
                            tvRfcCli.setText(Miscellaneous.GenerarRFC(s.toString().substring(0, 10), etNombreCli.getText().toString().trim(), etApPaternoCli.getText().toString().trim(), etApMaternoCli.getText().toString().trim()));
                            Update("rfc", TBL_CLIENTE_IND_REN, tvRfcCli.getText().toString().trim().toUpperCase());
                        }
                        else{
                            tvRfcCli.setText("");
                            Update("rfc", TBL_CLIENTE_IND_REN, tvRfcCli.getText().toString().trim().toUpperCase());
                        }

                    }
                }
                else {
                    tvRfcCli.setText("Rfc no válida");
                    Update("rfc", TBL_CLIENTE_IND_REN, "");
                    Update("curp", TBL_CLIENTE_IND_REN, "");
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
                    Update("curp_digito_veri", TBL_CLIENTE_IND_REN, e.toString());
                else
                    Update("curp_digito_veri", TBL_CLIENTE_IND_REN, "");
            }
        });*/
        tvOcupacionCli.setOnClickListener(tvOcupacionClie_OnClick);
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
                if (e.length() > 0){
                    Update("no_identificacion", TBL_CLIENTE_IND_REN, e.toString());
                }
                else
                    Update("no_identificacion", TBL_CLIENTE_IND_REN, "");
            }
        });
        tvEstudiosCli.setOnClickListener(tvEstudiosCli_OnClick);
        tvEstadoCivilCli.setOnClickListener(tvEstadoCivilCli_OnClick);
        tvTipoCasaCli.setOnClickListener(tvTipoCasaCli_OnClick);
        tvCasaFamiliar.setOnClickListener(tvCasaFamiliar_OnClick);
        etOTroTipoCli.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable e) {
                if (e.length() > 0){
                    Update("otro_tipo_vivienda", TBL_CLIENTE_IND_REN, e.toString());
                }
                else
                    Update("otro_tipo_vivienda", TBL_CLIENTE_IND_REN, "");
            }
        });
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
                    UpdateDireccion("calle", e.toString(), direccionIdCli, "CLIENTE");
                else
                    UpdateDireccion("calle", "", direccionIdCli, "CLIENTE");
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
                    UpdateDireccion("num_exterior", e.toString(), direccionIdCli, "CLIENTE");
                else
                    UpdateDireccion("num_exterior", "", direccionIdCli, "CLIENTE");
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
                    UpdateDireccion("num_interior", e.toString(), direccionIdCli, "CLIENTE");
                else
                    UpdateDireccion("num_interior", "", direccionIdCli, "CLIENTE");
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
                    UpdateDireccion("manzana", e.toString(), direccionIdCli, "CLIENTE");
                else
                    UpdateDireccion("manzana", "", direccionIdCli, "CLIENTE");
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
                    UpdateDireccion("lote", e.toString(), direccionIdCli, "CLIENTE");
                else
                    UpdateDireccion("lote", "", direccionIdCli, "CLIENTE");

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
            public void afterTextChanged(Editable e) {
                if (e.length() == 5){
                    Cursor row = dBhelper.getDireccionByCP(e.toString());
                    if (row.getCount() > 0){
                        UpdateDireccion("cp", e.toString(), direccionIdCli, "CLIENTE");
                        row.moveToFirst();
                        if (row.getCount() == 1){
                            UpdateDireccion("colonia", row.getString(7), direccionIdCli, "CLIENTE");
                            UpdateDireccion("municipio", row.getString(4), direccionIdCli,"CLIENTE");
                            UpdateDireccion("estado", row.getString(1), direccionIdCli,"CLIENTE");
                            tvColoniaCli.setText(row.getString(7));
                            tvMunicipioCli.setText(row.getString(4));
                            tvEstadoCli.setText(row.getString(1));
                        }else {
                            UpdateDireccion("colonia", "", direccionIdCli, "CLIENTE");
                            UpdateDireccion("municipio", row.getString(4), direccionIdCli,"CLIENTE");
                            UpdateDireccion("estado", row.getString(1), direccionIdCli,"CLIENTE");
                            tvColoniaCli.setText("");
                            tvMunicipioCli.setText(row.getString(4));
                            tvEstadoCli.setText(row.getString(1));
                        }
                    }else {
                        UpdateDireccion("cp", "", direccionIdCli, "CLIENTE");
                        UpdateDireccion("colonia", "", direccionIdCli, "CLIENTE");
                        UpdateDireccion("municipio", "", direccionIdCli,"CLIENTE");
                        UpdateDireccion("estado", "", direccionIdCli,"CLIENTE");
                        tvColoniaCli.setText(R.string.not_found);
                        tvMunicipioCli.setText(R.string.not_found);
                        tvEstadoCli.setText(R.string.not_found);
                    }
                    row.close();
                }else {
                    UpdateDireccion("cp", "", direccionIdCli, "CLIENTE");
                    UpdateDireccion("colonia", "", direccionIdCli, "CLIENTE");
                    UpdateDireccion("municipio", "", direccionIdCli,"CLIENTE");
                    UpdateDireccion("estado", "", direccionIdCli,"CLIENTE");
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
                    UpdateDireccion("ciudad", e.toString(), direccionIdCli, "CLIENTE");
                else
                    UpdateDireccion("ciudad", "", direccionIdCli, "CLIENTE");
            }
        });
        tvLocalidadCli.setOnClickListener(tvLocalidadCli_OnClick);
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
                        Update("tel_casa", TBL_CLIENTE_IND_REN, etTelCasaCli.getText().toString().trim());
                    }
                    else {
                        Update("tel_casa", TBL_CLIENTE_IND_REN, "");
                        etTelCasaCli.setError(ctx.getResources().getString(R.string.mensaje_telefono));
                    }
                }
                else {
                    Update("tel_casa", TBL_CLIENTE_IND_REN, "");
                    etTelCasaCli.setError(null);
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
            public void afterTextChanged(Editable e) {
                if (e.length() > 0){
                    if (e.length() == 10) {
                        etCelularCli.setError(null);
                        Update("tel_celular", TBL_CLIENTE_IND_REN, e.toString());
                    }
                    else {
                        Update("tel_celular", TBL_CLIENTE_IND_REN, "");
                        etCelularCli.setError(ctx.getResources().getString(R.string.mensaje_telefono));
                    }
                }
                else {
                    Update("tel_celular", TBL_CLIENTE_IND_REN, "");
                    etCelularCli.setError(null);
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
            public void afterTextChanged(Editable e) {
                if (e.length() > 0){
                    if (e.length() == 10) {
                        etTelMensCli.setError(null);
                        Update("tel_mensajes", TBL_CLIENTE_IND_REN, e.toString());
                    }
                    else {
                        Update("tel_mensajes", TBL_CLIENTE_IND_REN, "");
                        etTelMensCli.setError(ctx.getResources().getString(R.string.mensaje_telefono));
                    }
                }
                else {
                    Update("tel_mensajes", TBL_CLIENTE_IND_REN, "");
                    etTelMensCli.setError(null);
                }
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
            public void afterTextChanged(Editable e) {
                if (e.length() > 0){
                    if (e.length() == 10) {
                        etTelTrabajoCli.setError(null);
                        Update("tel_trabajo", TBL_CLIENTE_IND_REN, e.toString());
                    }
                    else {
                        Update("tel_trabajo", TBL_CLIENTE_IND_REN, "");
                        etTelTrabajoCli.setError(ctx.getResources().getString(R.string.mensaje_telefono));
                    }
                }
                else {
                    Update("tel_trabajo", TBL_CLIENTE_IND_REN, "");
                    etTelTrabajoCli.setError(null);
                }
            }
        });
        etTiempoSitio.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable e) {
                if (!validator.validate(etTiempoSitio, new String[]{validator.REQUIRED, validator.ONLY_NUMBER,validator.YEARS})){
                    Update("tiempo_vivir_sitio", TBL_CLIENTE_IND_REN, e.toString());
                }
                else {
                    Update("tiempo_vivir_sitio", TBL_CLIENTE_IND_REN, "0");
                }
            }
        });
        tvDependientes.setOnClickListener(tvDependientes_OnClick);
        tvEnteroNosotros.setOnClickListener(tvEnteroNosotros_OnClick);
        tvEstadoCuenta.setOnClickListener(tvEstadoCuenta_OnClick);
        tvEstudiosCli.setOnClickListener(tvEstudiosCli_OnClick);
        etEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable e) {
                if (!validator.validate(etEmail, new String[]{validator.EMAIL})){
                    Update("email", TBL_CLIENTE_IND_REN, e.toString());
                }
                else {
                    Update("email", TBL_CLIENTE_IND_REN, "");
                }
            }
        });
        etReferenciCli.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable e) {
                if (e.length() > 0)
                    Update("ref_domiciliaria", TBL_CLIENTE_IND_REN, e.toString());
                else
                    Update("ref_domiciliaria", TBL_CLIENTE_IND_REN, "");
            }
        });
        ibFotoFachCli.setOnClickListener(ibFotoFachCli_OnClick);
        ibFirmaCli.setOnClickListener(ibFirmaCli_OnClick);
        //==================================  CONYUGE LISTENER  ====================================
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
                    Update("nombre", TBL_CONYUGE_IND_REN, e.toString());
                else
                    Update("nombre", TBL_CONYUGE_IND_REN, "");
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
                    Update("paterno", TBL_CONYUGE_IND_REN, e.toString());
                else
                    Update("paterno", TBL_CONYUGE_IND_REN, "");
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
                if (e.length() > 0){
                    Update("materno", TBL_CONYUGE_IND_REN, e.toString());
                }
                else
                    Update("materno", TBL_CONYUGE_IND_REN, "");
            }
        });
        etNacionalidadCony.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable e) {
                if (e.length() > 0){
                    Update("nacionalidad", TBL_CONYUGE_IND_REN, e.toString());
                }
                else
                    Update("nacionalidad", TBL_CONYUGE_IND_REN, "");
            }
        });
        tvOcupacionCony.setOnClickListener(tvOcupacionConyuge_OnClick);
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
                    UpdateDireccion("calle", e.toString().trim().toUpperCase(), direccionIdCony, "CONYUGE");
                else
                    UpdateDireccion("calle", "", direccionIdCony, "CONYUGE");
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
                    UpdateDireccion("num_exterior", e.toString().trim().toUpperCase(), direccionIdCony, "CONYUGE");
                else
                    UpdateDireccion("num_exterior", "", direccionIdCony, "CONYUGE");
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
                    UpdateDireccion("num_interior", e.toString().trim().toUpperCase(), direccionIdCony, "CONYUGE");
                else
                    UpdateDireccion("num_interior", "", direccionIdCony, "CONYUGE");
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
                    UpdateDireccion("manzana", e.toString().trim().toUpperCase(), direccionIdCony, "CONYUGE");
                else
                    UpdateDireccion("manzana", "", direccionIdCony, "CONYUGE");
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
                    UpdateDireccion("lote", e.toString().trim().toUpperCase(), direccionIdCony, "CONYUGE");
                else
                    UpdateDireccion("lote", "", direccionIdCony, "CONYUGE");
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
                        UpdateDireccion("cp", s.toString(), direccionIdCony, "CONYUGE");
                        row.moveToFirst();
                        if (row.getCount() == 1){
                            UpdateDireccion("colonia", row.getString(7), direccionIdCony, "CONYUGE");
                            UpdateDireccion("municipio", row.getString(4), direccionIdCony, "CONYUGE");
                            UpdateDireccion("estado", row.getString(1), direccionIdCony, "CONYUGE");
                            tvColoniaCony.setText(row.getString(7));
                            tvMunicipioCony.setText(row.getString(4));
                            tvEstadoCony.setText(row.getString(1));
                        }else {
                            UpdateDireccion("colonia", "", direccionIdCony, "CONYUGE");
                            UpdateDireccion("municipio", row.getString(4), direccionIdCony, "CONYUGE");
                            UpdateDireccion("estado", row.getString(1), direccionIdCony, "CONYUGE");
                            tvColoniaCony.setText("");
                            tvMunicipioCony.setText(row.getString(4));
                            tvEstadoCony.setText(row.getString(1));
                        }
                    }else {
                        UpdateDireccion("cp", "", direccionIdCony, "CONYUGE");
                        UpdateDireccion("colonia", "", direccionIdCony, "CONYUGE");
                        UpdateDireccion("municipio", "", direccionIdCony, "CONYUGE");
                        UpdateDireccion("estado", "", direccionIdCony, "CONYUGE");
                        tvColoniaCony.setText(R.string.not_found);
                        tvMunicipioCony.setText(R.string.not_found);
                        tvEstadoCony.setText(R.string.not_found);
                    }
                    row.close();
                }else {
                    UpdateDireccion("cp", "", direccionIdCony, "CONYUGE");
                    UpdateDireccion("colonia", "", direccionIdCony, "CONYUGE");
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
                    UpdateDireccion("ciudad", e.toString().trim().toUpperCase(), direccionIdCony, "CONYUGE");
                else
                    UpdateDireccion("ciudad", "", direccionIdCony, "CONYUGE");
            }
        });
        tvLocalidadCony.setOnClickListener(tvLocalidadCony_OnClick);
        etIngMenCony.addTextChangedListener(new TextWatcher() {
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
                etIngMenCony.removeTextChangedListener(this);

                try {
                    int inilen, endlen;
                    inilen = etIngMenCony.getText().length();
                    String v = e.toString().replace(String.valueOf(df.getDecimalFormatSymbols().getGroupingSeparator()), "");
                    Number n = df.parse(v);
                    int cp = etIngMenCony.getSelectionStart();
                    if (hasFractionalPart) {
                        etIngMenCony.setText(df.format(n));
                    } else {
                        etIngMenCony.setText(dfnd.format(n));
                    }
                    endlen = etIngMenCony.getText().length();
                    int sel = (cp + (endlen - inilen));
                    if (sel > 0 && sel <= etIngMenCony.getText().length()) {
                        etIngMenCony.setSelection(sel);
                    } else {
                        // place cursor at the end?
                        etIngMenCony.setSelection(etIngMenCony.getText().length() - 1);
                    }
                } catch (NumberFormatException nfe) {
                    // do nothing?
                } catch (ParseException pe) {
                    // do nothing?
                }

                if (e.length() > 0)
                    Update("ing_mensual", TBL_CONYUGE_IND_REN, e.toString().replace(",",""));
                else
                    Update("ing_mensual", TBL_CONYUGE_IND_REN, "");

                etIngMenCony.addTextChangedListener(this);
            }
        });
        etGastoMenCony.addTextChangedListener(new TextWatcher() {
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
                etGastoMenCony.removeTextChangedListener(this);

                try {
                    int inilen, endlen;
                    inilen = etGastoMenCony.getText().length();
                    String v = e.toString().replace(String.valueOf(df.getDecimalFormatSymbols().getGroupingSeparator()), "");
                    Number n = df.parse(v);
                    int cp = etGastoMenCony.getSelectionStart();
                    if (hasFractionalPart) {
                        etGastoMenCony.setText(df.format(n));
                    } else {
                        etGastoMenCony.setText(dfnd.format(n));
                    }
                    endlen = etGastoMenCony.getText().length();
                    int sel = (cp + (endlen - inilen));
                    if (sel > 0 && sel <= etGastoMenCony.getText().length()) {
                        etGastoMenCony.setSelection(sel);
                    } else {
                        // place cursor at the end?
                        etGastoMenCony.setSelection(etGastoMenCony.getText().length() - 1);
                    }
                } catch (NumberFormatException nfe) {
                    // do nothing?
                } catch (ParseException pe) {
                    // do nothing?
                }

                if (e.length() > 0)
                    Update("gasto_mensual", TBL_CONYUGE_IND_REN, e.toString().replace(",", ""));
                else
                    Update("gasto_mensual", TBL_CONYUGE_IND_REN, "");

                etGastoMenCony.addTextChangedListener(this);
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
                        Update("tel_casa", TBL_CONYUGE_IND_REN, s.toString());
                        etCasaCony.setError(null);
                    }
                    else {
                        Update("tel_casa", TBL_CONYUGE_IND_REN, "");
                        etCasaCony.setError(ctx.getResources().getString(R.string.mensaje_telefono));
                    }
                }
                else {
                    Update("tel_casa", TBL_CONYUGE_IND_REN, "");
                    etCasaCony.setError(null);
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
                        Update("tel_celular", TBL_CONYUGE_IND_REN, s.toString());
                    }

                    else {
                        Update("tel_celular", TBL_CONYUGE_IND_REN, "");
                        etCelularCony.setError(ctx.getResources().getString(R.string.mensaje_telefono));
                    }
                }
                else {
                    Update("tel_celular", TBL_CONYUGE_IND_REN, "");
                    etCelularCony.setError(null);
                }
            }
        });
        //===============================  ECONOMICOS LISTENER  ====================================
        etPropiedadesEco.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable e) {
                if (e.length() > 0)
                    Update("propiedades", TBL_ECONOMICOS_IND_REN, e.toString());
                else
                    Update("propiedades", TBL_ECONOMICOS_IND_REN, "");
            }
        });
        etValorAproxEco.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable e) {
                if (e.length() > 0)
                    Update("valor_aproximado", TBL_ECONOMICOS_IND_REN, e.toString());
                else
                    Update("valor_aproximado", TBL_ECONOMICOS_IND_REN, "");
            }
        });
        etUbicacionEco.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable e) {
                if (e.length() > 0)
                    Update("ubicacion", TBL_ECONOMICOS_IND_REN, e.toString());
                else
                    Update("ubicacion", TBL_ECONOMICOS_IND_REN, "");
            }
        });
        //==================================  NEGOCIO LISTENER  ====================================
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
                    Update("nombre", TBL_NEGOCIO_IND_REN, e.toString());
                else
                    Update("nombre", TBL_NEGOCIO_IND_REN, "");
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
                    UpdateDireccion("calle", e.toString(), direccionIdNeg, "NEGOCIO");
                else
                    UpdateDireccion("calle", "", direccionIdNeg, "NEGOCIO");
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
                    UpdateDireccion("num_exterior", e.toString(), direccionIdNeg, "NEGOCIO");
                else
                    UpdateDireccion("num_exterior", "", direccionIdNeg, "NEGOCIO");
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
                    UpdateDireccion("num_interior", e.toString(), direccionIdNeg, "NEGOCIO");
                else
                    UpdateDireccion("num_interior", "", direccionIdNeg, "NEGOCIO");
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
                    UpdateDireccion("manzana", e.toString(), direccionIdNeg, "NEGOCIO");
                else
                    UpdateDireccion("manzana", "", direccionIdNeg, "NEGOCIO");
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
                    UpdateDireccion("lote", e.toString(), direccionIdNeg, "NEGOCIO");
                else
                    UpdateDireccion("lote", "", direccionIdNeg, "NEGOCIO");
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
            public void afterTextChanged(Editable e) {
                if (e.length() == 5){
                    Cursor row = dBhelper.getDireccionByCP(e.toString());
                    if (row.getCount() > 0){
                        UpdateDireccion("cp", e.toString(), direccionIdNeg, "NEGOCIO");
                        row.moveToFirst();
                        if (row.getCount() == 1){
                            UpdateDireccion("colonia", row.getString(7), direccionIdNeg, "NEGOCIO");
                            UpdateDireccion("municipio", row.getString(4), direccionIdNeg, "NEGOCIO");
                            tvColoniaNeg.setText(row.getString(7));
                            tvMunicipioNeg.setText(row.getString(4));
                        }else {
                            UpdateDireccion("colonia", "", direccionIdNeg, "NEGOCIO");
                            UpdateDireccion("municipio", row.getString(4), direccionIdNeg, "NEGOCIO");
                            tvColoniaNeg.setText("");
                            tvMunicipioNeg.setText(row.getString(4));
                        }
                    }else {
                        UpdateDireccion("colonia", "", direccionIdNeg, "NEGOCIO");
                        UpdateDireccion("municipio", "", direccionIdNeg, "NEGOCIO");
                        tvColoniaNeg.setText(R.string.not_found);
                        tvMunicipioNeg.setText(R.string.not_found);
                    }
                    row.close();
                }else {
                    UpdateDireccion("colonia", "", direccionIdNeg, "NEGOCIO");
                    UpdateDireccion("municipio", "", direccionIdNeg, "NEGOCIO");
                    tvColoniaNeg.setText(R.string.not_found);
                    tvMunicipioNeg.setText(R.string.not_found);
                }
            }
        });
        tvColoniaNeg.setOnClickListener(etColoniaAct_OnClick);
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
                    UpdateDireccion("ciudad", e.toString().trim().toUpperCase(), direccionIdNeg, "NEGOCIO");
                else
                    UpdateDireccion("ciudad", "", direccionIdNeg, "NEGOCIO");
            }
        });
        tvLocalidadNeg.setOnClickListener(tvLocalidadNeg_OnClick);
        tvActEcoEspNeg.setOnClickListener(tvActEcoEspNeg_OnClick);
        tvDestinoNeg.setOnClickListener(tvDestinoNeg_OnClick);
        etOtroDestinoNeg.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0)
                    Update("otro_destino", TBL_NEGOCIO_IND_REN, s.toString().trim().toUpperCase());
                else
                    Update("otro_destino", TBL_NEGOCIO_IND_REN, "");
            }
        });
        etAntiguedadNeg.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable e) {
                if (e.length() > 0){
                    if (Integer.parseInt(etAntiguedadNeg.getText().toString().trim()) > 0){
                        Update("antiguedad", TBL_NEGOCIO_IND_REN, e.toString());
                    }
                    else {
                        Update("antiguedad", TBL_NEGOCIO_IND_REN, "0");
                        etAntiguedadNeg.setError("No se permiten cantidades iguales a cero");
                    }
                }
                else
                    Update("antiguedad", TBL_NEGOCIO_IND_REN, "0");
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
            public void afterTextChanged(Editable e) {
                etIngMenNeg.removeTextChangedListener(this);

                try {
                    int inilen, endlen;
                    inilen = etIngMenNeg.getText().length();
                    String v = e.toString().replace(String.valueOf(df.getDecimalFormatSymbols().getGroupingSeparator()), "");
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

                if (e.length() > 0)
                    Update("ing_mensual", TBL_NEGOCIO_IND_REN, e.toString().replace(",",""));
                else
                    Update("ing_mensual", TBL_NEGOCIO_IND_REN, "");

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
            public void afterTextChanged(Editable e) {
                etOtrosIngNeg.removeTextChangedListener(this);

                try {
                    int inilen, endlen;
                    inilen = etOtrosIngNeg.getText().length();
                    String v = e.toString().replace(String.valueOf(df.getDecimalFormatSymbols().getGroupingSeparator()), "");
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

                if (e.length() > 0)
                    Update("ing_otros", TBL_NEGOCIO_IND_REN, e.toString().replace(",",""));
                else
                    Update("ing_otros", TBL_NEGOCIO_IND_REN, "");
                MontoMaximoNeg();

                etOtrosIngNeg.addTextChangedListener(this);
            }
        });
        etGastosMenNeg.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (s.toString().contains(String.valueOf(df.getDecimalFormatSymbols().getDecimalSeparator())))
                {
                    hasFractionalPart = true;
                } else {
                    hasFractionalPart = false;
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable e) {
                etGastosMenNeg.removeTextChangedListener(this);

                try {
                    int inilen, endlen;
                    inilen = etGastosMenNeg.getText().length();
                    String v = e.toString().replace(String.valueOf(df.getDecimalFormatSymbols().getGroupingSeparator()), "");
                    Number n = df.parse(v);
                    int cp = etGastosMenNeg.getSelectionStart();
                    if (hasFractionalPart) {
                        etGastosMenNeg.setText(df.format(n));
                    } else {
                        etGastosMenNeg.setText(dfnd.format(n));
                    }
                    endlen = etGastosMenNeg.getText().length();
                    int sel = (cp + (endlen - inilen));
                    if (sel > 0 && sel <= etGastosMenNeg.getText().length()) {
                        etGastosMenNeg.setSelection(sel);
                    } else {
                        // place cursor at the end?
                        etGastosMenNeg.setSelection(etGastosMenNeg.getText().length() - 1);
                    }
                } catch (NumberFormatException nfe) {
                    // do nothing?
                } catch (ParseException pe) {
                    // do nothing?
                }

                if (e.length() > 0)
                    Update("gasto_semanal", TBL_NEGOCIO_IND_REN, e.toString().replace(",",""));
                else
                    Update("gasto_semanal", TBL_NEGOCIO_IND_REN, "");
                MontoMaximoNeg();

                etGastosMenNeg.addTextChangedListener(this);
            }
        });
        etGastosAguaNeg.addTextChangedListener(new TextWatcher() {
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
                etGastosAguaNeg.removeTextChangedListener(this);

                try {
                    int inilen, endlen;
                    inilen = etGastosAguaNeg.getText().length();
                    String v = e.toString().replace(String.valueOf(df.getDecimalFormatSymbols().getGroupingSeparator()), "");
                    Number n = df.parse(v);
                    int cp = etGastosAguaNeg.getSelectionStart();
                    if (hasFractionalPart) {
                        etGastosAguaNeg.setText(df.format(n));
                    } else {
                        etGastosAguaNeg.setText(dfnd.format(n));
                    }
                    endlen = etGastosAguaNeg.getText().length();
                    int sel = (cp + (endlen - inilen));
                    if (sel > 0 && sel <= etGastosAguaNeg.getText().length()) {
                        etGastosAguaNeg.setSelection(sel);
                    } else {
                        // place cursor at the end?
                        etGastosAguaNeg.setSelection(etGastosAguaNeg.getText().length() - 1);
                    }
                } catch (NumberFormatException nfe) {
                    // do nothing?
                } catch (ParseException pe) {
                    // do nothing?
                }

                if (e.length() > 0)
                    Update("gasto_agua", TBL_NEGOCIO_IND_REN, e.toString().replace(",",""));
                else
                    Update("gasto_agua", TBL_NEGOCIO_IND_REN, "");
                MontoMaximoNeg();

                etGastosAguaNeg.addTextChangedListener(this);
            }
        });
        etGastosLuzNeg.addTextChangedListener(new TextWatcher() {
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
                etGastosLuzNeg.removeTextChangedListener(this);

                try {
                    int inilen, endlen;
                    inilen = etGastosLuzNeg.getText().length();
                    String v = e.toString().replace(String.valueOf(df.getDecimalFormatSymbols().getGroupingSeparator()), "");
                    Number n = df.parse(v);
                    int cp = etGastosLuzNeg.getSelectionStart();
                    if (hasFractionalPart) {
                        etGastosLuzNeg.setText(df.format(n));
                    } else {
                        etGastosLuzNeg.setText(dfnd.format(n));
                    }
                    endlen = etGastosLuzNeg.getText().length();
                    int sel = (cp + (endlen - inilen));
                    if (sel > 0 && sel <= etGastosLuzNeg.getText().length()) {
                        etGastosLuzNeg.setSelection(sel);
                    } else {
                        // place cursor at the end?
                        etGastosLuzNeg.setSelection(etGastosLuzNeg.getText().length() - 1);
                    }
                } catch (NumberFormatException nfe) {
                    // do nothing?
                } catch (ParseException pe) {
                    // do nothing?
                }

                if (e.length() > 0)
                    Update("gasto_luz", TBL_NEGOCIO_IND_REN, e.toString().replace(",",""));
                else
                    Update("gasto_luz", TBL_NEGOCIO_IND_REN, "");
                MontoMaximoNeg();

                etGastosLuzNeg.addTextChangedListener(this);
            }
        });
        etGastosTelNeg.addTextChangedListener(new TextWatcher() {
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
                etGastosTelNeg.removeTextChangedListener(this);

                try {
                    int inilen, endlen;
                    inilen = etGastosTelNeg.getText().length();
                    String v = e.toString().replace(String.valueOf(df.getDecimalFormatSymbols().getGroupingSeparator()), "");
                    Number n = df.parse(v);
                    int cp = etGastosTelNeg.getSelectionStart();
                    if (hasFractionalPart) {
                        etGastosTelNeg.setText(df.format(n));
                    } else {
                        etGastosTelNeg.setText(dfnd.format(n));
                    }
                    endlen = etGastosTelNeg.getText().length();
                    int sel = (cp + (endlen - inilen));
                    if (sel > 0 && sel <= etGastosTelNeg.getText().length()) {
                        etGastosTelNeg.setSelection(sel);
                    } else {
                        // place cursor at the end?
                        etGastosTelNeg.setSelection(etGastosTelNeg.getText().length() - 1);
                    }
                } catch (NumberFormatException nfe) {
                    // do nothing?
                } catch (ParseException pe) {
                    // do nothing?
                }

                if (e.length() > 0)
                    Update("gasto_telefono", TBL_NEGOCIO_IND_REN, e.toString().replace(",",""));
                else
                    Update("gasto_telefono", TBL_NEGOCIO_IND_REN, "");
                MontoMaximoNeg();

                etGastosTelNeg.addTextChangedListener(this);
            }
        });
        etGastosRentaNeg.addTextChangedListener(new TextWatcher() {
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
                etGastosRentaNeg.removeTextChangedListener(this);

                try {
                    int inilen, endlen;
                    inilen = etGastosRentaNeg.getText().length();
                    String v = e.toString().replace(String.valueOf(df.getDecimalFormatSymbols().getGroupingSeparator()), "");
                    Number n = df.parse(v);
                    int cp = etGastosRentaNeg.getSelectionStart();
                    if (hasFractionalPart) {
                        etGastosRentaNeg.setText(df.format(n));
                    } else {
                        etGastosRentaNeg.setText(dfnd.format(n));
                    }
                    endlen = etGastosRentaNeg.getText().length();
                    int sel = (cp + (endlen - inilen));
                    if (sel > 0 && sel <= etGastosRentaNeg.getText().length()) {
                        etGastosRentaNeg.setSelection(sel);
                    } else {
                        // place cursor at the end?
                        etGastosRentaNeg.setSelection(etGastosRentaNeg.getText().length() - 1);
                    }
                } catch (NumberFormatException nfe) {
                    // do nothing?
                } catch (ParseException pe) {
                    // do nothing?
                }

                if (e.length() > 0)
                    Update("gasto_renta", TBL_NEGOCIO_IND_REN, e.toString().replace(",",""));
                else
                    Update("gasto_renta", TBL_NEGOCIO_IND_REN, "");
                MontoMaximoNeg();

                etGastosRentaNeg.addTextChangedListener(this);
            }
        });
        etGastosOtrosNeg.addTextChangedListener(new TextWatcher() {
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
                etGastosOtrosNeg.removeTextChangedListener(this);

                try {
                    int inilen, endlen;
                    inilen = etGastosOtrosNeg.getText().length();
                    String v = e.toString().replace(String.valueOf(df.getDecimalFormatSymbols().getGroupingSeparator()), "");
                    Number n = df.parse(v);
                    int cp = etGastosOtrosNeg.getSelectionStart();
                    if (hasFractionalPart) {
                        etGastosOtrosNeg.setText(df.format(n));
                    } else {
                        etGastosOtrosNeg.setText(dfnd.format(n));
                    }
                    endlen = etGastosOtrosNeg.getText().length();
                    int sel = (cp + (endlen - inilen));
                    if (sel > 0 && sel <= etGastosOtrosNeg.getText().length()) {
                        etGastosOtrosNeg.setSelection(sel);
                    } else {
                        // place cursor at the end?
                        etGastosOtrosNeg.setSelection(etGastosOtrosNeg.getText().length() - 1);
                    }
                } catch (NumberFormatException nfe) {
                    // do nothing?
                } catch (ParseException pe) {
                    // do nothing?
                }

                if (e.length() > 0)
                    Update("gasto_otros", TBL_NEGOCIO_IND_REN, e.toString().replace(",",""));
                else
                    Update("gasto_otros", TBL_NEGOCIO_IND_REN, "");
                MontoMaximoNeg();

                etGastosOtrosNeg.addTextChangedListener(this);
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
                    Update("otro_medio_pago", TBL_NEGOCIO_IND_REN, e.toString().trim().toUpperCase());
                else
                    Update("otro_medio_pago", TBL_NEGOCIO_IND_REN, "");
            }
        });
        etCapacidadPagoNeg.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int i, int i1, int i2) {
                if (s.toString().contains(String.valueOf(df.getDecimalFormatSymbols().getDecimalSeparator())))
                {
                    hasFractionalPart = true;
                } else {
                    hasFractionalPart = false;
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

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
                            if (Double.parseDouble(e.toString()) > 0)
                                if (Double.parseDouble(e.toString()) <= Double.parseDouble(tvMontoMaxNeg.getText().toString().trim().replace(",","")))
                                    Update("capacidad_pago", TBL_NEGOCIO_IND_REN, e.toString().trim().replace(",", ""));
                                else
                                    ShowMensajes("EL monto no puede superar a la capacidad de pago","NEGOCIO");

                        } catch (NumberFormatException exception) {
                            Update("capacidad_pago", TBL_NEGOCIO_IND_REN, "");
                        }

                    else
                        Update("capacidad_pago", TBL_NEGOCIO_IND_REN, "");
                }
                else{
                    ShowMensajes("Tiene que completar primero los ingresos y gastos del neogocio","NEGOCIO");
                }

                etCapacidadPagoNeg.addTextChangedListener(this);
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
                if (e.length() > 0)
                    try {
                        if (Integer.parseInt(e.toString()) > 0)
                            Update("num_operacion_efectivo", TBL_NEGOCIO_IND_REN, e.toString().trim().toUpperCase());

                    }catch (NumberFormatException exception){
                        Update("num_operacion_efectivo", TBL_NEGOCIO_IND_REN, "0");
                    }

                else
                    Update("num_operacion_efectivo", TBL_NEGOCIO_IND_REN, "0");
            }
        });
        tvDiasVentaNeg.setOnClickListener(etDiasVenta_OnClick);
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
                    Update("ref_domiciliaria", TBL_NEGOCIO_IND_REN, e.toString());
                else
                    Update("ref_domiciliaria", TBL_NEGOCIO_IND_REN, "");
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
            public void afterTextChanged(Editable e) {
                HashMap<Integer, String> params = new HashMap<>();
                if (e.length()> 0){
                    Update("nombre", TBL_AVAL_IND_REN, e.toString().trim().toUpperCase());
                    params.put(0, e.toString());
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
                    etCurpAval.setText(Miscellaneous.GenerarCurp(params));
                }
                else{
                    Update("nombre", TBL_AVAL_IND_REN, "");
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
            public void afterTextChanged(Editable e) {
                HashMap<Integer, String> params = new HashMap<>();
                if (e.length()> 0){
                    Update("paterno", TBL_AVAL_IND_REN, e.toString().toUpperCase());
                    params.put(0, etNombreAval.getText().toString());
                    params.put(1, e.toString());
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
                    etCurpAval.setText(Miscellaneous.GenerarCurp(params));
                }
                else{
                    Update("paterno", TBL_AVAL_IND_REN, "");
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
            public void afterTextChanged(Editable e) {
                HashMap<Integer, String> params = new HashMap<>();
                if (e.length()> 0){
                    Update("materno", TBL_AVAL_IND_REN, e.toString());
                    params.put(0, etNombreAval.getText().toString());
                    params.put(1, etApPaternoAval.getText().toString());
                    params.put(2, e.toString());
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
                    etCurpAval.setText(Miscellaneous.GenerarCurp(params));
                }
                else{
                    Update("materno", TBL_AVAL_IND_REN, "");
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
                    etCurpAval.setText(Miscellaneous.GenerarCurp(params));
                }
            }
        });
        tvFechaNacAval.setOnClickListener(tvFechaNacAval_OnClick);
        tvEstadoNacAval.setOnClickListener(tvEstadoNacAval_OnClick);
        etCurpAval.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable e) {
                if (e.length() > 0){
                    if (e.toString().contains("Curp no válida")) {
                        tvRfcAval.setText("Rfc no válida");
                        Update("rfc", TBL_AVAL_IND_REN, "");
                        Update("curp", TBL_AVAL_IND_REN, "");
                    }
                    else {
                        Update("curp", TBL_AVAL_IND_REN, e.toString().trim().toUpperCase());
                        if (e.toString().length() >= 10){
                            tvRfcAval.setText(Miscellaneous.GenerarRFC(e.toString().substring(0,10), etNombreAval.getText().toString().trim(), etApPaternoAval.getText().toString().trim(), etApMaternoAval.getText().toString().trim()));

                            Update("rfc", TBL_AVAL_IND_REN, tvRfcAval.getText().toString().trim().toUpperCase());
                        }
                        else{
                            tvRfcAval.setText("");

                            Update("rfc", TBL_AVAL_IND_REN, tvRfcAval.getText().toString().trim().toUpperCase());
                        }


                        /*ContentValues cv = new ContentValues();
                        cv.put("rfc",tvRfcAval.getText().toString().trim().toUpperCase());
                        cv.put("curp",e.toString().trim().toUpperCase());
                        db.update(TBL_AVAL_IND_REN, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});*/
                    }

                }
                else {
                    Update("rfc", TBL_AVAL_IND_REN, "");
                    Update("curp", TBL_AVAL_IND_REN, "");
                    tvRfcAval.setText("Rfc no válida");
                }
            }
        });
        /*etCurpIdAval.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable e) {
                if (e.length() == 2){
                    if (!validator.validate(etCurpIdAval, new String[]{validator.REQUIRED, validator.CURP_ID})) {
                        if (Miscellaneous.CurpValidador(tvCurpAval.getText() + etCurpIdAval.getText().toString().trim().toUpperCase()))
                            Update("curp_digito", TBL_AVAL_IND_REN, e.toString());
                        else
                            Update("curp_digito", TBL_AVAL_IND_REN, "");
                    }
                }
                else
                    Update("curp_digito", TBL_AVAL_IND_REN, "");
            }
        });*/
        tvParentescoAval.setOnClickListener(tvParentescoAval_OnClick);
        tvTipoIdentificacionAval.setOnClickListener(tvTipoIdentificacionAval_OnClick);
        etNumIdentifAval.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable e) {
                if (e.length() > 0)
                    Update("no_identificacion", TBL_AVAL_IND_REN, e.toString());
                else
                    Update("no_identificacion", TBL_AVAL_IND_REN, "");
            }
        });
        tvOcupacionAval.setOnClickListener(tvOcupacionAval_OnClick);
        ibMapAval.setOnClickListener(ibMapAval_OnClick);
        etCalleAval.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable e) {
                if (e.length() > 0)
                    UpdateDireccion("calle", e.toString().trim().toUpperCase(), direccionIdAval, "AVAL");
                else
                    UpdateDireccion("calle", "", direccionIdAval, "AVAL");
            }
        });
        etNoExtAval.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable e) {
                if (e.length() > 0)
                    UpdateDireccion("num_exterior", e.toString(), direccionIdAval, "AVAL");
                else
                    UpdateDireccion("num_exterior", "", direccionIdAval, "AVAL");
            }
        });
        etNoIntAval.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable e) {
                if (e.length() > 0)
                    UpdateDireccion("num_interior", e.toString(), direccionIdAval, "AVAL");
                else
                    UpdateDireccion("num_interior", "", direccionIdAval, "AVAL");
            }
        });
        etManzanaAval.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable e) {
                if(e.length() > 0)
                    UpdateDireccion("manzana", e.toString().trim().toUpperCase(), direccionIdAval, "AVAL");
                else
                    UpdateDireccion("manzana", "", direccionIdAval, "AVAL");
            }
        });
        etLoteAval.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable e) {
                if (e.length() > 0)
                    UpdateDireccion("lote", e.toString().trim().toUpperCase(), direccionIdAval, "AVAL");
                else
                    UpdateDireccion("lote", "", direccionIdAval, "AVAL");
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
            public void afterTextChanged(Editable e) {
                if (e.length() == 5){
                    Cursor row = dBhelper.getDireccionByCP(e.toString());
                    if (row.getCount() > 0){
                        UpdateDireccion("cp", e.toString(), direccionIdAval, "AVAL");
                        row.moveToFirst();
                        if (row.getCount() == 1){
                            UpdateDireccion("colonia", row.getString(7), direccionIdAval, "AVAL");
                            UpdateDireccion("municipio", row.getString(4), direccionIdAval, "AVAL");
                            UpdateDireccion("estado", row.getString(1), direccionIdAval, "AVAL");
                            tvColoniaAval.setText(row.getString(7));
                            tvMunicipioAval.setText(row.getString(4));
                            tvEstadoAval.setText(row.getString(1));
                        }else {
                            UpdateDireccion("colonia", "", direccionIdAval, "AVAL");
                            tvColoniaAval.setText("");
                            UpdateDireccion("municipio", row.getString(4), direccionIdAval, "AVAL");
                            UpdateDireccion("estado", row.getString(1), direccionIdAval, "AVAL");
                            tvMunicipioAval.setText(row.getString(4));
                            tvEstadoAval.setText(row.getString(1));
                        }
                    }else {
                        UpdateDireccion("cp", "", direccionIdAval, "AVAL");
                        UpdateDireccion("colonia", "", direccionIdAval, "AVAL");
                        UpdateDireccion("municipio", "", direccionIdAval, "AVAL");
                        UpdateDireccion("estado", "", direccionIdAval, "AVAL");
                        tvColoniaAval.setText(R.string.not_found);
                        tvMunicipioAval.setText(R.string.not_found);
                        tvEstadoAval.setText(R.string.not_found);
                    }
                    row.close();
                }else {
                    UpdateDireccion("cp", "", direccionIdAval, "AVAL");
                    UpdateDireccion("colonia", "", direccionIdAval, "AVAL");
                    UpdateDireccion("municipio", "", direccionIdAval, "AVAL");
                    UpdateDireccion("estado", "", direccionIdAval, "AVAL");
                    tvColoniaAval.setText(R.string.not_found);
                    tvMunicipioAval.setText(R.string.not_found);
                    tvEstadoAval.setText(R.string.not_found);
                }
            }
        });
        tvColoniaAval.setOnClickListener(tvColoniaAval_OnClick);
        etCiudadAval.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable e) {
                if (e.length() > 0)
                    UpdateDireccion("ciudad", e.toString().trim().toUpperCase(), direccionIdAval, "AVAL");
                else
                    UpdateDireccion("ciudad", "", direccionIdAval, "AVAL");
            }
        });
        tvLocalidadAval.setOnClickListener(tvLocalidadAval_OnClick);
        tvTipoCasaAval.setOnClickListener(tvTipoCasaAval_OnClick);
        tvFamiliarAval.setOnClickListener(tvFamiliarAval_OnClick);
        etNombreTitularAval.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable e) {
                if (e.length() > 0)
                    Update("nombre_titular", TBL_AVAL_IND_REN, e.toString().trim().toUpperCase());
                else
                    Update("nombre_titular", TBL_AVAL_IND_REN, "");
            }
        });
        etCaracteristicasAval.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable e) {
                if (e.length() > 0)
                    Update("caracteristicas_domicilio", TBL_AVAL_IND_REN, e.toString().trim().toUpperCase());
                else
                    Update("caracteristicas_domicilio", TBL_AVAL_IND_REN, "");
            }
        });
        etNombreNegocioAval.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable e) {
                if (e.length() > 0)
                    Update("nombre_negocio", TBL_AVAL_IND_REN, e.toString().trim().toUpperCase());
                else
                    Update("nombre_negocio", TBL_AVAL_IND_REN, "");

            }
        });
        etAntiguedadAval.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable e) {
                if (e.length() > 0){
                    if (Integer.parseInt(e.toString().trim()) > 0)
                        Update("antigueda", TBL_AVAL_IND_REN, e.toString().trim());
                    else
                        Update("antigueda", TBL_AVAL_IND_REN, "0");
                }
                else
                    Update("antigueda", TBL_AVAL_IND_REN, "0");
            }
        });
        etIngMenAval.addTextChangedListener(new TextWatcher() {
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
                etIngMenAval.removeTextChangedListener(this);

                try {
                    int inilen, endlen;
                    inilen = etIngMenAval.getText().length();
                    String v = e.toString().replace(String.valueOf(df.getDecimalFormatSymbols().getGroupingSeparator()), "");
                    Number n = df.parse(v);
                    int cp = etIngMenAval.getSelectionStart();
                    if (hasFractionalPart) {
                        etIngMenAval.setText(df.format(n));
                    } else {
                        etIngMenAval.setText(dfnd.format(n));
                    }
                    endlen = etIngMenAval.getText().length();
                    int sel = (cp + (endlen - inilen));
                    if (sel > 0 && sel <= etIngMenAval.getText().length()) {
                        etIngMenAval.setSelection(sel);
                    } else {
                        // place cursor at the end?
                        etIngMenAval.setSelection(etIngMenAval.getText().length() - 1);
                    }
                } catch (NumberFormatException nfe) {
                    // do nothing?
                } catch (ParseException pe) {
                    // do nothing?
                }

                if (e.length() > 0)
                    Update("ing_mensual", TBL_AVAL_IND_REN, e.toString().replace(",",""));
                else
                    Update("ing_mensual", TBL_AVAL_IND_REN, "");

                MontoMaximoAval();

                etIngMenAval.addTextChangedListener(this);
            }
        });
        etIngOtrosAval.addTextChangedListener(new TextWatcher() {
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
                etIngOtrosAval.removeTextChangedListener(this);

                try {
                    int inilen, endlen;
                    inilen = etIngOtrosAval.getText().length();
                    String v = e.toString().replace(String.valueOf(df.getDecimalFormatSymbols().getGroupingSeparator()), "");
                    Number n = df.parse(v);
                    int cp = etIngOtrosAval.getSelectionStart();
                    if (hasFractionalPart) {
                        etIngOtrosAval.setText(df.format(n));
                    } else {
                        etIngOtrosAval.setText(dfnd.format(n));
                    }
                    endlen = etIngOtrosAval.getText().length();
                    int sel = (cp + (endlen - inilen));
                    if (sel > 0 && sel <= etIngOtrosAval.getText().length()) {
                        etIngOtrosAval.setSelection(sel);
                    } else {
                        // place cursor at the end?
                        etIngOtrosAval.setSelection(etIngOtrosAval.getText().length() - 1);
                    }
                } catch (NumberFormatException nfe) {
                    // do nothing?
                } catch (ParseException pe) {
                    // do nothing?
                }

                if (e.length() > 0)
                    Update("ing_otros", TBL_AVAL_IND_REN, e.toString().replace(",",""));
                else
                    Update("ing_otros", TBL_AVAL_IND_REN, "");

                MontoMaximoAval();

                etIngOtrosAval.addTextChangedListener(this);
            }
        });
        etGastosSemAval.addTextChangedListener(new TextWatcher() {
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
                etGastosSemAval.removeTextChangedListener(this);

                try {
                    int inilen, endlen;
                    inilen = etGastosSemAval.getText().length();
                    String v = e.toString().replace(String.valueOf(df.getDecimalFormatSymbols().getGroupingSeparator()), "");
                    Number n = df.parse(v);
                    int cp = etGastosSemAval.getSelectionStart();
                    if (hasFractionalPart) {
                        etGastosSemAval.setText(df.format(n));
                    } else {
                        etGastosSemAval.setText(dfnd.format(n));
                    }
                    endlen = etGastosSemAval.getText().length();
                    int sel = (cp + (endlen - inilen));
                    if (sel > 0 && sel <= etGastosSemAval.getText().length()) {
                        etGastosSemAval.setSelection(sel);
                    } else {
                        // place cursor at the end?
                        etGastosSemAval.setSelection(etGastosSemAval.getText().length() - 1);
                    }
                } catch (NumberFormatException nfe) {
                    // do nothing?
                } catch (ParseException pe) {
                    // do nothing?
                }

                if (e.length() > 0)
                    Update("gasto_semanal", TBL_AVAL_IND_REN, e.toString().trim().replace(",",""));
                else
                    Update("gasto_semanal", TBL_AVAL_IND_REN, "");

                MontoMaximoAval();

                etGastosSemAval.addTextChangedListener(this);
            }
        });
        etGastosAguaAval.addTextChangedListener(new TextWatcher() {
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
                etGastosAguaAval.removeTextChangedListener(this);

                try {
                    int inilen, endlen;
                    inilen = etGastosAguaAval.getText().length();
                    String v = e.toString().replace(String.valueOf(df.getDecimalFormatSymbols().getGroupingSeparator()), "");
                    Number n = df.parse(v);
                    int cp = etGastosAguaAval.getSelectionStart();
                    if (hasFractionalPart) {
                        etGastosAguaAval.setText(df.format(n));
                    } else {
                        etGastosAguaAval.setText(dfnd.format(n));
                    }
                    endlen = etGastosAguaAval.getText().length();
                    int sel = (cp + (endlen - inilen));
                    if (sel > 0 && sel <= etGastosAguaAval.getText().length()) {
                        etGastosAguaAval.setSelection(sel);
                    } else {
                        // place cursor at the end?
                        etGastosAguaAval.setSelection(etGastosAguaAval.getText().length() - 1);
                    }
                } catch (NumberFormatException nfe) {
                    // do nothing?
                } catch (ParseException pe) {
                    // do nothing?
                }

                if (e.length() > 0)
                    Update("gasto_agua", TBL_AVAL_IND_REN, e.toString().trim().replace(",",""));
                else
                    Update("gasto_agua", TBL_AVAL_IND_REN, "");

                MontoMaximoAval();

                etGastosAguaAval.addTextChangedListener(this);
            }
        });
        etGastosLuzAval.addTextChangedListener(new TextWatcher() {
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
                etGastosLuzAval.removeTextChangedListener(this);

                try {
                    int inilen, endlen;
                    inilen = etGastosLuzAval.getText().length();
                    String v = e.toString().replace(String.valueOf(df.getDecimalFormatSymbols().getGroupingSeparator()), "");
                    Number n = df.parse(v);
                    int cp = etGastosLuzAval.getSelectionStart();
                    if (hasFractionalPart) {
                        etGastosLuzAval.setText(df.format(n));
                    } else {
                        etGastosLuzAval.setText(dfnd.format(n));
                    }
                    endlen = etGastosLuzAval.getText().length();
                    int sel = (cp + (endlen - inilen));
                    if (sel > 0 && sel <= etGastosLuzAval.getText().length()) {
                        etGastosLuzAval.setSelection(sel);
                    } else {
                        // place cursor at the end?
                        etGastosLuzAval.setSelection(etGastosLuzAval.getText().length() - 1);
                    }
                } catch (NumberFormatException nfe) {
                    // do nothing?
                } catch (ParseException pe) {
                    // do nothing?
                }

                if (e.length() > 0)
                    Update("gasto_luz", TBL_AVAL_IND_REN, e.toString().trim().replace(",",""));
                else
                    Update("gasto_luz", TBL_AVAL_IND_REN, "");

                MontoMaximoAval();

                etGastosLuzAval.addTextChangedListener(this);
            }
        });
        etGastosTelAval.addTextChangedListener(new TextWatcher() {
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
                etGastosTelAval.removeTextChangedListener(this);

                try {
                    int inilen, endlen;
                    inilen = etGastosTelAval.getText().length();
                    String v = e.toString().replace(String.valueOf(df.getDecimalFormatSymbols().getGroupingSeparator()), "");
                    Number n = df.parse(v);
                    int cp = etGastosTelAval.getSelectionStart();
                    if (hasFractionalPart) {
                        etGastosTelAval.setText(df.format(n));
                    } else {
                        etGastosTelAval.setText(dfnd.format(n));
                    }
                    endlen = etGastosTelAval.getText().length();
                    int sel = (cp + (endlen - inilen));
                    if (sel > 0 && sel <= etGastosTelAval.getText().length()) {
                        etGastosTelAval.setSelection(sel);
                    } else {
                        // place cursor at the end?
                        etGastosTelAval.setSelection(etGastosTelAval.getText().length() - 1);
                    }
                } catch (NumberFormatException nfe) {
                    // do nothing?
                } catch (ParseException pe) {
                    // do nothing?
                }

                if (e.length() > 0)
                    Update("gasto_telefono", TBL_AVAL_IND_REN, e.toString().trim().replace(",",""));
                else
                    Update("gasto_telefono", TBL_AVAL_IND_REN, "");

                MontoMaximoAval();

                etGastosTelAval.addTextChangedListener(this);
            }
        });
        etGastosRentaAval.addTextChangedListener(new TextWatcher() {
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
                etGastosRentaAval.removeTextChangedListener(this);

                try {
                    int inilen, endlen;
                    inilen = etGastosRentaAval.getText().length();
                    String v = e.toString().replace(String.valueOf(df.getDecimalFormatSymbols().getGroupingSeparator()), "");
                    Number n = df.parse(v);
                    int cp = etGastosRentaAval.getSelectionStart();
                    if (hasFractionalPart) {
                        etGastosRentaAval.setText(df.format(n));
                    } else {
                        etGastosRentaAval.setText(dfnd.format(n));
                    }
                    endlen = etGastosRentaAval.getText().length();
                    int sel = (cp + (endlen - inilen));
                    if (sel > 0 && sel <= etGastosRentaAval.getText().length()) {
                        etGastosRentaAval.setSelection(sel);
                    } else {
                        // place cursor at the end?
                        etGastosRentaAval.setSelection(etGastosRentaAval.getText().length() - 1);
                    }
                } catch (NumberFormatException nfe) {
                    // do nothing?
                } catch (ParseException pe) {
                    // do nothing?
                }

                if (e.length() > 0)
                    Update("gasto_renta", TBL_AVAL_IND_REN, e.toString().trim().replace(",",""));
                else
                    Update("gasto_renta", TBL_AVAL_IND_REN, "");

                MontoMaximoAval();

                etGastosRentaAval.addTextChangedListener(this);
            }
        });
        etGastosOtrosAval.addTextChangedListener(new TextWatcher() {
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
                etGastosOtrosAval.removeTextChangedListener(this);

                try {
                    int inilen, endlen;
                    inilen = etGastosOtrosAval.getText().length();
                    String v = e.toString().replace(String.valueOf(df.getDecimalFormatSymbols().getGroupingSeparator()), "");
                    Number n = df.parse(v);
                    int cp = etGastosOtrosAval.getSelectionStart();
                    if (hasFractionalPart) {
                        etGastosOtrosAval.setText(df.format(n));
                    } else {
                        etGastosOtrosAval.setText(dfnd.format(n));
                    }
                    endlen = etGastosOtrosAval.getText().length();
                    int sel = (cp + (endlen - inilen));
                    if (sel > 0 && sel <= etGastosOtrosAval.getText().length()) {
                        etGastosOtrosAval.setSelection(sel);
                    } else {
                        // place cursor at the end?
                        etGastosOtrosAval.setSelection(etGastosOtrosAval.getText().length() - 1);
                    }
                } catch (NumberFormatException nfe) {
                    // do nothing?
                } catch (ParseException pe) {
                    // do nothing?
                }

                if (e.length() > 0)
                    Update("gasto_otros", TBL_AVAL_IND_REN, e.toString().trim().replace(",",""));
                else
                    Update("gasto_otros", TBL_AVAL_IND_REN, "");

                MontoMaximoAval();

                etGastosOtrosAval.addTextChangedListener(this);
            }
        });
        tvMediosPagoAval.setOnClickListener(tvMediosPagoAval_OnClick);
        etOtroMedioPagoAval.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable e) {
                if (e.length() > 0)
                    Update("otro_medio_pago", TBL_AVAL_IND_REN, e.toString().trim().toUpperCase());
                else
                    Update("otro_medio_pago", TBL_AVAL_IND_REN, "");
            }
        });
        etCapacidadPagoAval.addTextChangedListener(new TextWatcher() {
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
                etCapacidadPagoAval.removeTextChangedListener(this);

                try {
                    int inilen, endlen;
                    inilen = etCapacidadPagoAval.getText().length();
                    String v = e.toString().replace(String.valueOf(df.getDecimalFormatSymbols().getGroupingSeparator()), "");
                    Number n = df.parse(v);
                    int cp = etCapacidadPagoAval.getSelectionStart();
                    if (hasFractionalPart) {
                        etCapacidadPagoAval.setText(df.format(n));
                    } else {
                        etCapacidadPagoAval.setText(dfnd.format(n));
                    }
                    endlen = etCapacidadPagoAval.getText().length();
                    int sel = (cp + (endlen - inilen));
                    if (sel > 0 && sel <= etCapacidadPagoAval.getText().length()) {
                        etCapacidadPagoAval.setSelection(sel);
                    } else {
                        // place cursor at the end?
                        etCapacidadPagoAval.setSelection(etGastosRentaAval.getText().length() - 1);
                    }
                } catch (NumberFormatException nfe) {
                    // do nothing?
                } catch (ParseException pe) {
                    // do nothing?
                }

                if (!tvMontoMaxAval.getText().toString().trim().isEmpty() && Double.parseDouble(tvMontoMaxAval.getText().toString().trim().replace(",","")) > 0) {
                    if (e.length() > 0)
                        try {
                            if (Double.parseDouble(e.toString()) > 0 )
                                if (Double.parseDouble(e.toString()) <= Double.parseDouble(tvMontoMaxAval.getText().toString().trim().replace(",","")))
                                    Update("capacidad_pagos", TBL_AVAL_IND_REN, e.toString().trim().replace(",", ""));
                                else
                                    ShowMensajes("EL monto no puede superar a la capacidad de pago","AVAL");

                        } catch (NumberFormatException exception) {
                            Update("capacidad_pagos", TBL_AVAL_IND_REN, "");
                        }

                    else
                        Update("capacidad_pagos", TBL_AVAL_IND_REN, "");
                }
                else{
                    ShowMensajes("Tiene que completar primero los ingresos y gastos del aval","AVAL");
                }

                etCapacidadPagoAval.addTextChangedListener(this);
            }
        });
        tvHoraLocAval.setOnClickListener(tvHoraLocAval_OnClick);
        tvActivosObservables.setOnClickListener(tvActivosObservables_OnClick);
        etTelCasaAval.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable e) {
                if (e.length() > 0){
                    if (e.length() == 10) {
                        etTelCasaAval.setError(null);
                        Update("tel_casa", TBL_AVAL_IND_REN, e.toString().trim());
                    }
                    else {
                        Update("tel_casa", TBL_AVAL_IND_REN, "");
                        etTelCasaAval.setError(ctx.getResources().getString(R.string.mensaje_telefono));
                    }
                }
                else {
                    Update("tel_casa", TBL_AVAL_IND_REN, "");
                    etTelCasaAval.setError(null);
                }
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
            public void afterTextChanged(Editable e) {
                if (e.length() > 0){
                    if (e.length() == 10) {
                        etCelularAval.setError(null);
                        Update("tel_celular", TBL_AVAL_IND_REN, e.toString().trim());
                    }
                    else {
                        Update("tel_casa", TBL_AVAL_IND_REN, "");
                        etCelularAval.setError(ctx.getResources().getString(R.string.mensaje_telefono));
                    }
                }
                else {
                    Update("tel_casa", TBL_AVAL_IND_REN, "");
                    etCelularAval.setError(null);
                }
            }
        });
        etTelMensAval.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable e) {
                if (e.length() > 0){
                    if (e.length() == 10) {
                        etTelMensAval.setError(null);
                        Update("tel_mensajes", TBL_AVAL_IND_REN, e.toString());
                    }
                    else {
                        Update("tel_mensajes", TBL_AVAL_IND_REN, "");
                        etTelMensAval.setError(ctx.getResources().getString(R.string.mensaje_telefono));
                    }
                }
                else {
                    Update("tel_mensajes", TBL_AVAL_IND_REN, "");
                    etTelMensAval.setError(null);
                }
            }
        });
        etTelTrabajoAval.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable e) {
                if (e.length() > 0){
                    if (e.length() == 10) {
                        etTelTrabajoAval.setError(null);
                        Update("tel_trabajo", TBL_AVAL_IND_REN, e.toString());
                    }
                    else {
                        Update("tel_trabajo", TBL_AVAL_IND_REN, "");
                        etTelTrabajoAval.setError(ctx.getResources().getString(R.string.mensaje_telefono));
                    }
                }
                else {
                    Update("tel_trabajo", TBL_AVAL_IND_REN, "");
                    etTelTrabajoAval.setError(null);
                }
            }
        });
        etEmailAval.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable e) {
                if (!validator.validate(etEmail, new String[]{validator.EMAIL})){
                    Update("email", TBL_AVAL_IND_REN, e.toString());
                }
                else {
                    Update("email", TBL_AVAL_IND_REN, "");
                }
            }
        });
        ibFotoFachAval.setOnClickListener(ibFotoFachAval_OnClick);
        etReferenciaAval.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable e) {
                if (e.length() > 0)
                    Update("ref_domiciliaria", TBL_AVAL_IND_REN, e.toString().trim().toUpperCase());
                else
                    Update("ref_domiciliaria", TBL_AVAL_IND_REN, "");
            }
        });
        ibFirmaAval.setOnClickListener(ibFirmaAval_OnClick);
        //============== REFERENCIA ================================
        etNombreRef.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable e) {
                if (e.length() > 0)
                    Update("nombre", TBL_REFERENCIA_IND_REN, e.toString().trim().toUpperCase());
                else
                    Update("nombre", TBL_REFERENCIA_IND_REN, "");
            }
        });
        etApPaternoRef.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable e) {
                if (e.length() > 0)
                    Update("paterno", TBL_REFERENCIA_IND_REN, e.toString().trim().toUpperCase());
                else
                    Update("paterno", TBL_REFERENCIA_IND_REN, "");
            }
        });
        etApMaternoRef.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable e) {
                if (e.length() > 0)
                    Update("materno", TBL_REFERENCIA_IND_REN, e.toString().trim().toUpperCase());
                else
                    Update("materno", TBL_REFERENCIA_IND_REN, "");
            }
        });
        tvFechaNacRef.setOnClickListener(tvFechaNacRef_OnClick);
        etCalleRef.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable e) {
                if (e.length() > 0)
                    UpdateDireccion("calle", e.toString(), direccionIdRef, "REFERENCIA");
                else
                    UpdateDireccion("calle", "", direccionIdRef, "REFERENCIA");
            }
        });
        etNoExtRef.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable e) {
                if (e.length() > 0)
                    UpdateDireccion("num_exterior", e.toString(), direccionIdRef, "REFERENCIA");
                else
                    UpdateDireccion("num_exterior", "", direccionIdRef, "REFERENCIA");
            }
        });
        etNoIntRef.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable e) {
                if (e.length() > 0)
                    UpdateDireccion("num_interior", e.toString(), direccionIdRef, "REFERENCIA");
                else
                    UpdateDireccion("num_interior", "", direccionIdRef, "REFERENCIA");
            }
        });
        etManzanaRef.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable e) {
                if (e.length() > 0)
                    UpdateDireccion("manzana", e.toString(), direccionIdRef, "REFERENCIA");
                else
                    UpdateDireccion("manzana", "", direccionIdRef, "REFERENCIA");
            }
        });
        etLoteRef.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable e) {
                if (e.length() > 0)
                    UpdateDireccion("lote", e.toString(), direccionIdRef, "REFERENCIA");
                else
                    UpdateDireccion("lote", "", direccionIdRef, "REFERENCIA");

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
            public void afterTextChanged(Editable e) {
                if (e.length() == 5){
                    Cursor row = dBhelper.getDireccionByCP(e.toString());
                    if (row.getCount() > 0){
                        UpdateDireccion("cp", e.toString(), direccionIdRef, "REFERENCIA");
                        row.moveToFirst();
                        if (row.getCount() == 1){
                            UpdateDireccion("colonia", row.getString(7), direccionIdRef, "REFERENCIA");
                            UpdateDireccion("municipio", row.getString(4), direccionIdRef,"REFERENCIA");
                            UpdateDireccion("estado", row.getString(1), direccionIdRef,"REFERENCIA");
                            tvColoniaRef.setText(row.getString(7));
                            tvMunicipioRef.setText(row.getString(4));
                            tvEstadoRef.setText(row.getString(1));
                        }else {
                            UpdateDireccion("colonia", "", direccionIdRef, "REFERENCIA");
                            UpdateDireccion("municipio", row.getString(4), direccionIdRef,"REFERENCIA");
                            UpdateDireccion("estado", row.getString(1), direccionIdRef,"REFERENCIA");
                            tvColoniaRef.setText("");
                            tvMunicipioRef.setText(row.getString(4));
                            tvEstadoRef.setText(row.getString(1));
                        }
                    }else {
                        UpdateDireccion("cp", "", direccionIdRef, "REFERENCIA");
                        UpdateDireccion("colonia", "", direccionIdRef, "REFERENCIA");
                        UpdateDireccion("municipio", "", direccionIdRef,"REFERENCIA");
                        UpdateDireccion("estado", "", direccionIdRef,"REFERENCIA");
                        tvColoniaRef.setText(R.string.not_found);
                        tvMunicipioRef.setText(R.string.not_found);
                        tvEstadoRef.setText(R.string.not_found);
                    }
                    row.close();
                }else {
                    UpdateDireccion("cp", "", direccionIdRef, "REFERENCIA");
                    UpdateDireccion("colonia", "", direccionIdRef, "REFERENCIA");
                    UpdateDireccion("municipio", "", direccionIdRef,"REFERENCIA");
                    UpdateDireccion("estado", "", direccionIdRef,"REFERENCIA");
                    tvColoniaRef.setText(R.string.not_found);
                    tvMunicipioRef.setText(R.string.not_found);
                    tvEstadoRef.setText(R.string.not_found);
                }
            }
        });
        tvColoniaRef.setOnClickListener(tvColoniaRef_OnClick);
        etCiudadRef.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable e) {
                if (e.length() > 0)
                    UpdateDireccion("ciudad", e.toString(), direccionIdRef, "REFERENCIA");
                else
                    UpdateDireccion("ciudad", "", direccionIdRef, "REFERENCIA");
            }
        });
        tvLocalidadRef.setOnClickListener(tvLocalidadRef_OnClick);
        etTelCelRef.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable e) {
                if (e.length() > 0){
                    if (e.length() == 10) {
                        etTelCelRef.setError(null);
                        Update("tel_celular", TBL_REFERENCIA_IND_REN, e.toString().trim());
                    }
                    else {
                        Update("tel_celular", TBL_REFERENCIA_IND_REN, "");
                        etTelCelRef.setError(ctx.getResources().getString(R.string.mensaje_telefono));
                    }
                }
                else {
                    Update("tel_celular", TBL_REFERENCIA_IND_REN, "");
                    etTelCelRef.setError(null);
                }
            }
        });
        //============== CROQUIS ==================================
        tvCasa.setOnClickListener(tvCasa_OnClick);
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
                    Update("referencias",TBL_CROQUIS_IND_REN, e.toString().trim().toUpperCase());
                else
                    Update("referencias",TBL_CROQUIS_IND_REN, "");
            }
        });
        //================================  ESCANEAR DOCUMENTOS  ===================================
        ibIneFrontal.setOnClickListener(ibIneFrontal_OnClick);
        ibIneReverso.setOnClickListener(ibIneReverso_OnClick);
        //ibCurp.setOnClickListener(ibCurp_OnClick);
        ibComprobante.setOnClickListener(ibComprobante_OnClick);
        //================================  CLIENTE GENERO LISTENER  ===============================
        rgGeneroCli.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (isEditCli) {
                    tvGeneroCli.setError(null);
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
                        etCurpCli.setText(Miscellaneous.GenerarCurp(params));
                        Update("genero", TBL_CLIENTE_IND_REN, "0");
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
                        etCurpCli.setText(Miscellaneous.GenerarCurp(params));
                        Update("genero", TBL_CLIENTE_IND_REN, "1");
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
                        etCurpCli.setText(Miscellaneous.GenerarCurp(params));
                        Update("genero", TBL_CLIENTE_IND_REN, "2");
                    }
                }
            }
        });
        rgBienes.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (isEditCli) {
                    tvBienes.setError(null);
                    switch (checkedId) {
                        case R.id.rbMancomunados:
                            Update("bienes", TBL_CLIENTE_IND_REN, "1");
                            break;
                        case R.id.rbSeparados:
                            Update("bienes", TBL_CLIENTE_IND_REN, "2");
                            break;
                    }
                }
            }
        });
        //===========================  AVAL GENERO LISTENER  =======================================
        rgGeneroAval.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (isEditAva) {
                    tvGeneroAval.setError(null);
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
                        Update("genero", TBL_AVAL_IND_REN, "0");
                        etCurpAval.setText(Miscellaneous.GenerarCurp(params));
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

                        Update("genero", TBL_AVAL_IND_REN, "1");
                        etCurpAval.setText(Miscellaneous.GenerarCurp(params));
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

                        Update("genero", TBL_AVAL_IND_REN, "2");
                        etCurpAval.setText(Miscellaneous.GenerarCurp(params));
                    }
                }
            }
        });
        rgNegocioAval.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (isEditAva) {
                    tvNombreNegAval.setError(null);
                    switch (checkedId) {
                        case R.id.rbSiNeg:
                            llNombreNegocio.setVisibility(View.VISIBLE);
                            etNombreNegocioAval.setText("");
                            Update("tiene_negocio", TBL_AVAL_IND_REN, "1");
                            Update("nombre_negocio", TBL_AVAL_IND_REN, "");
                            break;
                        case R.id.rbNoNeg:
                            llNombreNegocio.setVisibility(View.GONE);
                            Update("tiene_negocio", TBL_AVAL_IND_REN, "2");
                            break;
                    }
                }
            }
        });
        //=============================  POLITICAS LISTENER  =======================================
        rgPropietarioReal.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (isEditPol) {
                    tvPropietarioReal.setError(null);
                    switch (checkedId) {
                        case R.id.rbSiPropietario:
                            tvAnexoPropietario.setVisibility(View.VISIBLE);
                            Update("propietario_real", TBL_POLITICAS_PLD_IND_REN, "1");
                            break;
                        case R.id.rbNoPropietario:
                            tvAnexoPropietario.setVisibility(View.GONE);
                            Update("propietario_real", TBL_POLITICAS_PLD_IND_REN, "2");
                            break;
                    }
                }
            }
        });
        rgProveedor.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (isEditPol) {
                    tvProvedor.setError(null);
                    switch (checkedId) {
                        case R.id.rbSiProveedor:
                            tvAnexoPreveedor.setVisibility(View.VISIBLE);
                            Update("proveedor_recursos", TBL_POLITICAS_PLD_IND_REN, "1");
                            break;
                        case R.id.rbNoProveedor:
                            tvAnexoPreveedor.setVisibility(View.GONE);
                            Update("proveedor_recursos", TBL_POLITICAS_PLD_IND_REN, "2");
                            break;
                    }
                }
            }
        });
        rgPoliticamenteExp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (isEditPol) {
                    tvPoliticamenteExp.setError(null);
                    switch (checkedId) {
                        case R.id.rbSiExpuesta:
                            tvAnexoPoliticamenteExp.setVisibility(View.VISIBLE);
                            Update("persona_politica", TBL_POLITICAS_PLD_IND_REN, "1");
                            break;
                        case R.id.rbNoexpuesta:
                            tvAnexoPoliticamenteExp.setVisibility(View.GONE);
                            Update("persona_politica", TBL_POLITICAS_PLD_IND_REN, "2");
                            break;
                    }
                }
            }
        });

        ivFotoFachCli.setOnClickListener(ivFotoFachCli_OnClick);
        ivFirmaCli.setOnClickListener(ivFirmaCli_OnClick);
        ivFotoFachNeg.setOnClickListener(ivFotoFachNeg_OnClick);
        ivFotoFachAval.setOnClickListener(ivFotoFachAval_OnClick);
        ivFirmaAval.setOnClickListener(ivFirmaAval_OnClick);
        ivIneFrontal.setOnClickListener(ivIneFrontal_OnClik);
        ivIneReverso.setOnClickListener(ivIneReverso_OnClick);
        //ivCurp.setOnClickListener(ivCurp_OnClick);
        ivComprobante.setOnClickListener(ivComprobante_OnClick);
        ivFirmaAval.setOnClickListener(ivFirmaAsesor_OnClick);
    }

    private View.OnClickListener ibFirmaAsesor_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i_firma_asesor = new Intent(ctx, CapturarFirma.class);
            i_firma_asesor.putExtra(TIPO,"");
            startActivityForResult(i_firma_asesor, REQUEST_CODE_FIRMA_ASESOR);
        }
    };

    private View.OnClickListener tvPlazo_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (isEditCre) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
                builder.setTitle(R.string.selected_option)
                        .setItems(R.array.intervalo, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int position) {
                                tvPlazo.setError(null);
                                tvPlazo.setText(_plazo[position]);
                                Update("plazo", TBL_CREDITO_IND_REN, tvPlazo.getText().toString().trim().toUpperCase());
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
            if (isEditCre) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
                builder.setTitle(R.string.selected_option)
                        .setItems(R.array.lapso, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int position) {
                                tvFrecuencia.setError(null);
                                tvFrecuencia.setText(_frecuencia[position]);
                                Update("periodicidad", TBL_CREDITO_IND_REN, tvFrecuencia.getText().toString().trim().toUpperCase());
                                switch (position){
                                    case 0:
                                        tvNumOperacionNeg.setText("4");
                                        Update("num_operacion_mensuales", TBL_NEGOCIO_IND_REN, "4");
                                        break;
                                    case 1:
                                    case 2:
                                        tvNumOperacionNeg.setText("2");
                                        Update("num_operacion_mensuales", TBL_NEGOCIO_IND_REN, "2");
                                        break;
                                    case 3:
                                        tvNumOperacionNeg.setText("1");
                                        Update("num_operacion_mensuales", TBL_NEGOCIO_IND_REN, "1");
                                        break;
                                }
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
            if (isEditCre) {
                dialog_date_picker dialogDatePicker = new dialog_date_picker();
                Bundle b = new Bundle();

                /*b.putInt(YEAR_CURRENT, myCalendar.get(Calendar.YEAR));
                b.putInt(MONTH_CURRENT, myCalendar.get(Calendar.MONTH));
                b.putInt(DAY_CURRENT, myCalendar.get(Calendar.DAY_OF_MONTH));*/
                b.putInt(YEAR_CURRENT, ((tvFechaDesembolso.getText().toString().isEmpty())?myCalendar.get(Calendar.YEAR):Integer.parseInt(tvFechaDesembolso.getText().toString().substring(0,4))));
                b.putInt(MONTH_CURRENT, ((tvFechaDesembolso.getText().toString().isEmpty())?myCalendar.get(Calendar.MONTH):(Integer.parseInt(tvFechaDesembolso.getText().toString().substring(5,7))-1)));
                b.putInt(DAY_CURRENT, ((tvFechaDesembolso.getText().toString().isEmpty())?myCalendar.get(Calendar.DAY_OF_MONTH):Integer.parseInt(tvFechaDesembolso.getText().toString().substring(8,10))));
                b.putString(DATE_CURRENT, sdf.format(myCalendar.getTime()));
                b.putInt(IDENTIFIER, 13);
                b.putBoolean(FECHAS_POST, true);
                dialogDatePicker.setArguments(b);
                dialogDatePicker.show(getSupportFragmentManager(), NameFragments.DIALOGDATEPICKER);
            }
        }
    };

    private View.OnClickListener tvDestino_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (isEditCre) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
                builder.setTitle(R.string.selected_option)
                        .setItems(R.array.destino_prestamo, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int position) {
                                tvDestino.setError(null);
                                tvDestino.setText(_destino[position]);
                                Update("destino", TBL_CREDITO_IND_REN, tvDestino.getText().toString().trim().toUpperCase());
                            }
                        });
                builder.create();
                builder.show();
            }
        }
    };

    private View.OnClickListener tvRiesgo_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (isEditCre) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
                builder.setTitle(R.string.selected_option)
                        .setItems(R.array.clasificacion_riesgo, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int position) {
                                tvRiesgo.setError(null);
                                tvRiesgo.setText(_riesgo[position]);
                                Update("clasificacion_riesgo", TBL_CREDITO_IND_REN, tvRiesgo.getText().toString().trim().toUpperCase());
                            }
                        });
                builder.create();
                builder.show();
            }
        }
    };

    private View.OnClickListener tvComportamiento_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (isEditCre) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
                builder.setTitle(R.string.selected_option)
                        .setItems(R.array.comportamiento_pago, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int position) {
                                tvComportamiento.setError(null);
                                tvComportamiento.setText(_comportamiento[position]);
                                Update("comportamiento_pago", TBL_CREDITO_IND_REN, tvComportamiento.getText().toString().trim().toUpperCase());
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
            if (isEditCli) {
                dialog_date_picker dialogDatePicker = new dialog_date_picker();
                Bundle b = new Bundle();

                /*b.putInt(YEAR_CURRENT, myCalendar.get(Calendar.YEAR));
                b.putInt(MONTH_CURRENT, myCalendar.get(Calendar.MONTH));
                b.putInt(DAY_CURRENT, myCalendar.get(Calendar.DAY_OF_MONTH));*/
                b.putInt(YEAR_CURRENT, ((tvFechaNacCli.getText().toString().isEmpty())?myCalendar.get(Calendar.YEAR):Integer.parseInt(tvFechaNacCli.getText().toString().substring(0,4))));
                b.putInt(MONTH_CURRENT, ((tvFechaNacCli.getText().toString().isEmpty())?myCalendar.get(Calendar.MONTH):(Integer.parseInt(tvFechaNacCli.getText().toString().substring(5,7))-1)));
                b.putInt(DAY_CURRENT, ((tvFechaNacCli.getText().toString().isEmpty())?myCalendar.get(Calendar.DAY_OF_MONTH):Integer.parseInt(tvFechaNacCli.getText().toString().substring(8,10))));
                b.putString(DATE_CURRENT, sdf.format(myCalendar.getTime()));
                b.putInt(IDENTIFIER, 2);
                b.putBoolean(FECHAS_POST, false);
                dialogDatePicker.setArguments(b);
                dialogDatePicker.show(getSupportFragmentManager(), NameFragments.DIALOGDATEPICKER);
            }
        }
    };

    private View.OnClickListener ibFirmaCli_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i_firma_cli = new Intent(ctx, CapturarFirma.class);
            i_firma_cli.putExtra(TIPO,"CLIENTE");
            startActivityForResult(i_firma_cli, REQUEST_CODE_FIRMA_CLI);
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
            Intent i = new Intent(RenovacionCreditoInd.this, CameraVertical.class);
            i.putExtra(ORDER_ID, "SC_cliente");
            startActivityForResult(i, REQUEST_CODE_CAMARA_FACHADA_CLI);
        }
    };

    private View.OnClickListener etEstadoNac_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (isEditCli) {
                Intent i_estados = new Intent(ctx, Catalogos.class);
                i_estados.putExtra(TITULO, Miscellaneous.ucFirst(ESTADOS));
                i_estados.putExtra(CATALOGO, ESTADOS);
                i_estados.putExtra(REQUEST_CODE, REQUEST_CODE_ESTADO_NAC);
                startActivityForResult(i_estados, REQUEST_CODE_ESTADO_NAC);
            }
        }
    };

    private View.OnClickListener etColonia_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (isEditCli) {
                Intent i_colonia = new Intent(ctx, Catalogos.class);
                i_colonia.putExtra(TITULO, Miscellaneous.ucFirst(COLONIA+"s"));
                i_colonia.putExtra(CATALOGO, COLONIAS);
                i_colonia.putExtra(EXTRA, etCpCli.getText().toString().trim());
                i_colonia.putExtra(REQUEST_CODE, REQUEST_CODE_COLONIA_CLIE);
                startActivityForResult(i_colonia, REQUEST_CODE_COLONIA_CLIE);
            }
        }
    };

    private View.OnClickListener tvLocalidadCli_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (isEditCli) {
                if (!tvMunicipioCli.getText().toString().trim().isEmpty()) {
                    Cursor row = dBhelper.getRecords(TABLE_MUNICIPIOS, " WHERE municipio_nombre = ?", "", new String[]{tvMunicipioCli.getText().toString().trim().toUpperCase()});
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

    private View.OnClickListener tvEstudiosCli_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (isEditCli) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
                builder.setTitle(R.string.selected_option)
                        .setItems(_estudios, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int position) {
                                tvEstudiosCli.setError(null);
                                tvEstudiosCli.setText(_estudios[position]);
                                tvEstudiosCli.requestFocus();
                                Update("nivel_estudio", TBL_CLIENTE_IND_REN, tvEstudiosCli.getText().toString().trim().toUpperCase());
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
            if (isEditCli) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
                builder.setTitle(R.string.selected_option)
                        .setItems(_civil, new DialogInterface.OnClickListener() {
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
                                Update("estado_civil", TBL_CLIENTE_IND_REN, tvEstadoCivilCli.getText().toString().trim().toUpperCase());
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
            if (isEditCli) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
                builder.setTitle(R.string.selected_option)
                        .setItems(_tipo_casa, new DialogInterface.OnClickListener() {
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
                                Update("tipo_vivienda", TBL_CLIENTE_IND_REN, tvTipoCasaCli.getText().toString().trim().toUpperCase());
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
            if (isEditCli) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
                builder.setTitle(R.string.selected_option)
                        .setItems(_parentesco, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int position) {
                                tvCasaFamiliar.setError(null);
                                tvCasaFamiliar.setText(_parentesco[position]);
                                Update("parentesco", TBL_CLIENTE_IND_REN, tvCasaFamiliar.getText().toString().trim().toUpperCase());
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
            if (isEditCli) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
                builder.setTitle(R.string.selected_option)
                        .setItems(R.array.dependientes_eco, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int position) {
                                tvDependientes.setError(null);
                                tvDependientes.setText(_dependientes[position]);
                                Update("dependientes", TBL_CLIENTE_IND_REN, tvDependientes.getText().toString().trim().toUpperCase());
                            }
                        });
                builder.create();
                builder.show();
            }
        }
    };

    private View.OnClickListener tvEstadoCuenta_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (isEditCli) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
                builder.setTitle(R.string.selected_option)
                        .setItems(R.array.confirmacion, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int position) {
                                tvEstadoCuenta.setError(null);
                                tvEstadoCuenta.setText(_confirmacion[position]);
                                Update("estado_cuenta", TBL_CLIENTE_IND_REN, tvEstadoCuenta.getText().toString().trim().toUpperCase());
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
            if (isEditCli) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
                builder.setTitle(R.string.selected_option)
                        .setItems(_medio_contacto, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int position) {
                                tvEnteroNosotros.setError(null);
                                tvEnteroNosotros.setText(_medio_contacto[position]);
                                Update("medio_contacto", TBL_CLIENTE_IND_REN, tvEnteroNosotros.getText().toString().trim().toUpperCase());
                            }
                        });
                builder.create();
                builder.show();
            }
        }
    };

    private View.OnClickListener tvColoniaAval_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (isEditAva) {
                Intent i_colonia = new Intent(ctx, Catalogos.class);
                i_colonia.putExtra(TITULO, Miscellaneous.ucFirst(COLONIA+"s"));
                i_colonia.putExtra(CATALOGO, COLONIAS);
                i_colonia.putExtra(EXTRA, etCpAval.getText().toString().trim());
                i_colonia.putExtra(REQUEST_CODE, REQUEST_CODE_COLONIA_AVAL);
                startActivityForResult(i_colonia, REQUEST_CODE_COLONIA_AVAL);
            }
        }
    };

    private View.OnClickListener tvLocalidadAval_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (isEditAva) {
                if (!tvMunicipioAval.getText().toString().trim().isEmpty()) {
                    Cursor row = dBhelper.getRecords(TABLE_MUNICIPIOS, " WHERE municipio_nombre = ?", "", new String[]{tvMunicipioAval.getText().toString().trim().toUpperCase()});
                    row.moveToFirst();
                    Intent i_localidad = new Intent(ctx, Catalogos.class);
                    i_localidad.putExtra(TITULO, Miscellaneous.ucFirst(LOCALIDADES));
                    i_localidad.putExtra(CATALOGO, LOCALIDADES);
                    i_localidad.putExtra(EXTRA, row.getString(1));
                    i_localidad.putExtra(REQUEST_CODE, REQUEST_CODE_LOCALIDAD_AVAL);
                    startActivityForResult(i_localidad, REQUEST_CODE_LOCALIDAD_AVAL);
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

    private View.OnClickListener tvTipoCasaAval_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (isEditAva) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
                builder.setTitle(R.string.selected_option)
                        .setItems(_tipo_casa, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int position) {
                                tvTipoCasaAval.setError(null);
                                tvTipoCasaAval.setText(_tipo_casa[position]);
                                switch (position) {
                                    case 1:
                                    case 2:
                                        llNombreTitular.setVisibility(View.VISIBLE);
                                        llParentescoFamAval.setVisibility(View.VISIBLE);
                                        break;
                                    default:
                                        llNombreTitular.setVisibility(View.GONE);
                                        llParentescoFamAval.setVisibility(View.GONE);
                                        break;
                                }
                                /*switch (position) {
                                    case 1://FAMILIAR
                                        llNombreTitular.setVisibility(View.VISIBLE);
                                        llParentescoFamAval.setVisibility(View.VISIBLE);
                                        break;
                                    case 0: //PROPIA
                                    default://NINGUNO
                                        llNombreTitular.setVisibility(View.GONE);
                                        llParentescoFamAval.setVisibility(View.GONE);
                                        break;
                                }*/
                                Update("tipo_vivienda", TBL_AVAL_IND_REN, tvTipoCasaAval.getText().toString().trim().toUpperCase());
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
            if (isEditAva) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
                builder.setTitle(R.string.selected_option)
                        .setItems(_parentesco, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int position) {
                                tvParentescoAval.setError(null);
                                tvParentescoAval.setText(_parentesco[position]);
                                Update("parentesco_cliente", TBL_AVAL_IND_REN, tvParentescoAval.getText().toString().trim().toUpperCase());
                            }
                        });
                builder.create();
                builder.show();
            }
        }
    };

    private View.OnClickListener tvFamiliarAval_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (isEditAva) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
                builder.setTitle(R.string.selected_option)
                        .setItems(_parentesco_casa, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int position) {
                                tvFamiliarAval.setError(null);
                                tvFamiliarAval.setText(_parentesco_casa[position]);
                                Update("parentesco", TBL_AVAL_IND_REN, tvFamiliarAval.getText().toString().trim().toUpperCase());
                            }
                        });
                builder.create();
                builder.show();
            }
        }
    };

    private View.OnClickListener tvMediosPagoAval_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (isEditAva) {
                selectedItemsMediosPago = new ArrayList<>();
                new AlertDialog.Builder(RenovacionCreditoInd.this)
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
                                tvMediosPagoAval.setError(null);
                                tvMediosPagoAval.setText(medios);
                                Update("medio_pago", TBL_AVAL_IND_REN, tvMediosPagoAval.getText().toString().trim().toUpperCase());

                                if (tvMediosPagoAval.getText().toString().trim().toUpperCase().contains("OTROS")){
                                    etOtroMedioPagoAval.setVisibility(View.VISIBLE);
                                }
                                else{
                                    etOtroMedioPagoAval.setVisibility(View.GONE);
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

    private View.OnClickListener tvActivosObservables_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (isEditAva) {
                selectedItemsActivos = new ArrayList<>();
                new AlertDialog.Builder(RenovacionCreditoInd.this)
                        .setTitle("Activos Observables")
                        .setMultiChoiceItems(_activos, null, new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                if (isChecked) {
                                    selectedItemsActivos.add(which);
                                } else if (selectedItemsActivos.contains(which)) {
                                    selectedItemsActivos.remove(Integer.valueOf(which));
                                }
                            }
                        })
                        .setPositiveButton(R.string.accept, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                String medios = "";
                                Collections.sort(selectedItemsActivos);
                                for (int i = 0; i < selectedItemsActivos.size(); i++){
                                    if(i == 0)
                                        medios += _activos[selectedItemsActivos.get(i)];
                                    else
                                        medios += ", "+_activos[selectedItemsActivos.get(i)];
                                }
                                tvActivosObservables.setError(null);
                                tvActivosObservables.setText(medios);
                                Update("activos_observables", TBL_AVAL_IND_REN, tvActivosObservables.getText().toString().trim().toUpperCase());

                                if (tvMediosPagoAval.getText().toString().trim().toUpperCase().contains("OTRO")){
                                    etOtroMedioPagoAval.setVisibility(View.VISIBLE);
                                }
                                else{
                                    etOtroMedioPagoAval.setVisibility(View.GONE);
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

    private View.OnClickListener ibFirmaAval_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i_firma_aval = new Intent(ctx, CapturarFirma.class);
            i_firma_aval.putExtra(TIPO,"AVAL");
            startActivityForResult(i_firma_aval, REQUEST_CODE_FIRMA_AVAL);
        }
    };

    private View.OnClickListener etDiasVenta_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (isEditNeg) {
                showDiasSemana();
            }
        }
    };

    private View.OnClickListener etColoniaAct_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (isEditNeg) {
                Intent i_colonia = new Intent(ctx, Catalogos.class);
                i_colonia.putExtra(TITULO, Miscellaneous.ucFirst(COLONIA+"s"));
                i_colonia.putExtra(CATALOGO, COLONIAS);
                i_colonia.putExtra(EXTRA, etCpNeg.getText().toString().trim());
                i_colonia.putExtra(REQUEST_CODE, REQUEST_CODE_COLONIA_NEG);
                startActivityForResult(i_colonia, REQUEST_CODE_COLONIA_NEG);
            }
        }
    };

    private View.OnClickListener tvActEcoEspNeg_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (isEditNeg) {
                Intent i_ocupaciones = new Intent(ctx, Catalogos.class);
                i_ocupaciones.putExtra(TITULO, Miscellaneous.ucFirst(OCUPACIONES));
                i_ocupaciones.putExtra(CATALOGO, OCUPACIONES);
                i_ocupaciones.putExtra(REQUEST_CODE, REQUEST_CODE_OCUPACION_NEG);
                startActivityForResult(i_ocupaciones, REQUEST_CODE_OCUPACION_NEG);
            }
        }
    };

    private View.OnClickListener tvLocalidadNeg_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (isEditNeg) {
                if (!tvMunicipioNeg.getText().toString().trim().isEmpty()) {
                    Cursor row = dBhelper.getRecords(TABLE_MUNICIPIOS, " WHERE municipio_nombre = ?", "", new String[]{tvMunicipioNeg.getText().toString().trim().toUpperCase()});
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
            Intent i = new Intent(RenovacionCreditoInd.this, CameraVertical.class);
            i.putExtra(ORDER_ID, "SC_negocio");
            startActivityForResult(i, REQUEST_CODE_CAMARA_FACHADA_NEG);
        }
    };

    private View.OnClickListener tvOcupacionClie_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (isEditCli) {
                Intent i_ocupaciones = new Intent(ctx, Catalogos.class);
                i_ocupaciones.putExtra(TITULO, Miscellaneous.ucFirst(OCUPACIONES));
                i_ocupaciones.putExtra(CATALOGO, OCUPACIONES);
                i_ocupaciones.putExtra(REQUEST_CODE, REQUEST_CODE_OCUPACION_CLIE);
                startActivityForResult(i_ocupaciones, REQUEST_CODE_OCUPACION_CLIE);
            }
        }
    };

    private View.OnClickListener tvTipoIdentificacion_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (isEditCli) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
                builder.setTitle(R.string.selected_option)
                        .setItems(_tipo_identificacion, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int position) {
                                tvTipoIdentificacion.setError(null);
                                tvTipoIdentificacion.setText(_tipo_identificacion[position]);
                                Update("tipo_identificacion", TBL_CLIENTE_IND_REN, tvTipoIdentificacion.getText().toString().trim().toUpperCase());
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
            if (isEditAva) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
                builder.setTitle(R.string.selected_option)
                        .setItems(_tipo_identificacion, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int position) {
                                tvTipoIdentificacionAval.setError(null);
                                tvTipoIdentificacionAval.setText(_tipo_identificacion[position]);
                                Update("tipo_identificacion", TBL_AVAL_IND_REN, tvTipoIdentificacionAval.getText().toString().trim().toUpperCase());
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
            if (isEditCon) {
                Intent i_ocupaciones = new Intent(ctx, Catalogos.class);
                i_ocupaciones.putExtra(TITULO, Miscellaneous.ucFirst(OCUPACIONES));
                i_ocupaciones.putExtra(CATALOGO, OCUPACIONES);
                i_ocupaciones.putExtra(REQUEST_CODE, REQUEST_CODE_OCUPACION_CONY);
                startActivityForResult(i_ocupaciones, REQUEST_CODE_OCUPACION_CONY);
            }
        }
    };

    private View.OnClickListener tvColoniaCony_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (isEditCon) {
                Intent i_colonia = new Intent(ctx, Catalogos.class);
                i_colonia.putExtra(TITULO, Miscellaneous.ucFirst(COLONIA+"s"));
                i_colonia.putExtra(CATALOGO, COLONIAS);
                i_colonia.putExtra(EXTRA, etCpCony.getText().toString().trim());
                i_colonia.putExtra(REQUEST_CODE, REQUEST_CODE_COLONIA_CONY);
                startActivityForResult(i_colonia, REQUEST_CODE_COLONIA_CONY);
            }
        }
    };

    private View.OnClickListener tvLocalidadCony_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (isEditCon) {
                if (!tvMunicipioCony.getText().toString().trim().isEmpty()) {
                    Cursor row = dBhelper.getRecords(TABLE_MUNICIPIOS, " WHERE municipio_nombre = ?", "", new String[]{tvMunicipioCony.getText().toString().trim().toUpperCase()});
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

    private View.OnClickListener etActividadEco_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (isEditNeg) {
                Intent i_ocupaciones = new Intent(ctx, Catalogos.class);
                i_ocupaciones.putExtra(TITULO, Miscellaneous.ucFirst(SECTORES));
                i_ocupaciones.putExtra(CATALOGO, SECTORES);
                i_ocupaciones.putExtra(REQUEST_CODE, REQUEST_CODE_ACTIVIDAD_NEG);
                startActivityForResult(i_ocupaciones, REQUEST_CODE_ACTIVIDAD_NEG);
            }
        }
    };

    private View.OnClickListener tvDestinoNeg_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (isEditNeg) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
                builder.setTitle(R.string.selected_option)
                        .setItems(_destino_credito, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int position) {
                                tvDestinoNeg.setError(null);
                                tvDestinoNeg.setText(_destino_credito[position]);
                                Update("destino_credito", TBL_NEGOCIO_IND_REN, _destino_credito[position]);

                                if (position == 0){
                                    etOtroDestinoNeg.setVisibility(View.GONE);
                                    etOtroDestinoNeg.setText("");
                                    Update("otro_destino", TBL_NEGOCIO_IND_REN, "");
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

    private View.OnClickListener tvMediosPagoNeg_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (isEditNeg) {
                selectedItemsMediosPago = new ArrayList<>();
                new AlertDialog.Builder(RenovacionCreditoInd.this)
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
                                Update("medio_pago", TBL_NEGOCIO_IND_REN, tvMediosPagoNeg.getText().toString().trim().toUpperCase());

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

    //============ REFERNCIA =================================================================
    private View.OnClickListener tvFechaNacRef_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (isEditRef) {
                dialog_date_picker dialogDatePicker = new dialog_date_picker();
                Bundle b = new Bundle();

                b.putInt(YEAR_CURRENT, myCalendar.get(Calendar.YEAR));
                b.putInt(MONTH_CURRENT, myCalendar.get(Calendar.MONTH));
                b.putInt(DAY_CURRENT, myCalendar.get(Calendar.DAY_OF_MONTH));
                b.putString(DATE_CURRENT, sdf.format(myCalendar.getTime()));
                b.putInt(IDENTIFIER, 17);
                b.putBoolean(FECHAS_POST, false);
                dialogDatePicker.setArguments(b);
                dialogDatePicker.show(getSupportFragmentManager(), NameFragments.DIALOGDATEPICKER);
            }
        }
    };

    private View.OnClickListener tvColoniaRef_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (isEditRef) {
                Intent i_colonia = new Intent(ctx, Catalogos.class);
                i_colonia.putExtra(TITULO, Miscellaneous.ucFirst(COLONIA+"s"));
                i_colonia.putExtra(CATALOGO, COLONIAS);
                i_colonia.putExtra(EXTRA, etCpRef.getText().toString().trim());
                i_colonia.putExtra(REQUEST_CODE, REQUEST_CODE_COLONIA_REF);
                startActivityForResult(i_colonia, REQUEST_CODE_COLONIA_REF);
            }
        }
    };

    private View.OnClickListener tvLocalidadRef_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (isEditRef) {
                if (!tvMunicipioRef.getText().toString().trim().isEmpty()) {
                    Cursor row = dBhelper.getRecords(TABLE_MUNICIPIOS, " WHERE municipio_nombre = ?", "", new String[]{tvMunicipioRef.getText().toString().trim().toUpperCase()});
                    row.moveToFirst();
                    Intent i_localidad = new Intent(ctx, Catalogos.class);
                    i_localidad.putExtra(TITULO, Miscellaneous.ucFirst(LOCALIDADES));
                    i_localidad.putExtra(CATALOGO, LOCALIDADES);
                    i_localidad.putExtra(EXTRA, row.getString(1));
                    i_localidad.putExtra(REQUEST_CODE, REQUEST_CODE_LOCALIDAD_REF);
                    startActivityForResult(i_localidad, REQUEST_CODE_LOCALIDAD_REF);
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

    //============ AVAL =====================
    private View.OnClickListener tvFechaNacAval_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (isEditAva) {
                dialog_date_picker dialogDatePicker = new dialog_date_picker();
                Bundle b = new Bundle();

                /*b.putInt(YEAR_CURRENT, myCalendar.get(Calendar.YEAR));
                b.putInt(MONTH_CURRENT, myCalendar.get(Calendar.MONTH));
                b.putInt(DAY_CURRENT, myCalendar.get(Calendar.DAY_OF_MONTH));*/
                b.putInt(YEAR_CURRENT, ((tvFechaNacAval.getText().toString().isEmpty())?myCalendar.get(Calendar.YEAR):Integer.parseInt(tvFechaNacAval.getText().toString().substring(0,4))));
                b.putInt(MONTH_CURRENT, ((tvFechaNacAval.getText().toString().isEmpty())?myCalendar.get(Calendar.MONTH):(Integer.parseInt(tvFechaNacAval.getText().toString().substring(5,7))-1)));
                b.putInt(DAY_CURRENT, ((tvFechaNacAval.getText().toString().isEmpty())?myCalendar.get(Calendar.DAY_OF_MONTH):Integer.parseInt(tvFechaNacAval.getText().toString().substring(8,10))));
                b.putString(DATE_CURRENT, sdf.format(myCalendar.getTime()));
                b.putInt(IDENTIFIER, 16);
                b.putBoolean(FECHAS_POST, false);
                dialogDatePicker.setArguments(b);
                dialogDatePicker.show(getSupportFragmentManager(), NameFragments.DIALOGDATEPICKER);
            }
        }
    };

    private View.OnClickListener tvEstadoNacAval_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (isEditAva) {
                Intent i_estados = new Intent(ctx, Catalogos.class);
                i_estados.putExtra(TITULO, Miscellaneous.ucFirst(ESTADOS));
                i_estados.putExtra(CATALOGO, ESTADOS);
                i_estados.putExtra(REQUEST_CODE, REQUEST_CODE_ESTADO_NAC_AVAL);
                startActivityForResult(i_estados, REQUEST_CODE_ESTADO_NAC_AVAL);
            }
        }
    };

    private View.OnClickListener tvOcupacionAval_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (isEditAva) {
                Intent i_ocupaciones = new Intent(ctx, Catalogos.class);
                i_ocupaciones.putExtra(TITULO, Miscellaneous.ucFirst(OCUPACIONES));
                i_ocupaciones.putExtra(CATALOGO, OCUPACIONES);
                i_ocupaciones.putExtra(REQUEST_CODE, REQUEST_CODE_OCUPACION_AVAL);
                startActivityForResult(i_ocupaciones, REQUEST_CODE_OCUPACION_AVAL);
            }
        }
    };

    private View.OnClickListener tvHoraLocAval_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (isEditAva) {
                dialog_time_picker timePicker = new dialog_time_picker();
                Bundle b = new Bundle();
                b.putInt(IDENTIFIER, 5);
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
            Intent i = new Intent(RenovacionCreditoInd.this, CameraVertical.class);
            i.putExtra(ORDER_ID, "SC_aval");
            startActivityForResult(i, REQUEST_CODE_CAMARA_FACHADA_AVAL);
        }
    };

    //======================  CROQUIS  ================================
    private View.OnClickListener tvCasa_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

        }
    };
    private View.OnClickListener tvPrincipal_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (isEditCro){
                dialog_input_calle dlgInput = new dialog_input_calle();
                Bundle b = new Bundle();
                b.putString(TIPO, "PRINCIPAL");
                b.putString(CALLE, tvPrincipal.getText().toString().trim().toUpperCase());
                b.putString(TIPO_SOLICITUD, "INDIVIDUAL RENOVACION");
                dlgInput.setArguments(b);
                dlgInput.show(getSupportFragmentManager(), NameFragments.DIALOGINPUTCALLE);
            }
        }
    };
    private View.OnClickListener tvTrasera_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (isEditCro){
                dialog_input_calle dlgInput = new dialog_input_calle();
                Bundle b = new Bundle();
                b.putString(TIPO, "TRASERA");
                b.putString(CALLE, tvTrasera.getText().toString().trim().toUpperCase());
                b.putString(TIPO_SOLICITUD, "INDIVIDUAL RENOVACION");
                dlgInput.setArguments(b);
                dlgInput.show(getSupportFragmentManager(), NameFragments.DIALOGINPUTCALLE);
            }
        }
    };
    private View.OnClickListener tvLateralUno_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (isEditCro){
                dialog_input_calle dlgInput = new dialog_input_calle();
                Bundle b = new Bundle();
                b.putString(TIPO, "LATERAL UNO");
                b.putString(CALLE, tvLateraUno.getText().toString().trim().toUpperCase());
                b.putString(TIPO_SOLICITUD, "INDIVIDUAL RENOVACION");
                dlgInput.setArguments(b);
                dlgInput.show(getSupportFragmentManager(), NameFragments.DIALOGINPUTCALLE);
            }
        }
    };
    private View.OnClickListener tvLateralDos_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (isEditCro){
                dialog_input_calle dlgInput = new dialog_input_calle();
                Bundle b = new Bundle();
                b.putString(TIPO, "LATERAL DOS");
                b.putString(CALLE, tvLateraDos.getText().toString().trim().toUpperCase());
                b.putString(TIPO_SOLICITUD, "INDIVIDUAL RENOVACION");
                dlgInput.setArguments(b);
                dlgInput.show(getSupportFragmentManager(), NameFragments.DIALOGINPUTCALLE);
            }
        }
    };

    //======================  DOCUMENTOS  ==============================
    private View.OnClickListener ibIneFrontal_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent scanIntent = new Intent(RenovacionCreditoInd.this, CardIOActivity.class);
            scanIntent.putExtra(CardIOActivity.EXTRA_SUPPRESS_SCAN,true); // supmit cuando termine de reconocer el documento
            scanIntent.putExtra(CardIOActivity.EXTRA_SUPPRESS_MANUAL_ENTRY,true); // esconder teclado
            scanIntent.putExtra(CardIOActivity.EXTRA_USE_CARDIO_LOGO,true); // cambiar logo de paypal por el de card.io
            scanIntent.putExtra(CardIOActivity.EXTRA_RETURN_CARD_IMAGE,true); // capture img
            scanIntent.putExtra(CardIOActivity.EXTRA_CAPTURED_CARD_IMAGE,true); // capturar img
            startActivityForResult(scanIntent, REQUEST_CODE_FOTO_INE_FRONTAL);
        }
    };

    private View.OnClickListener ibIneReverso_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent scanIntent = new Intent(RenovacionCreditoInd.this, CardIOActivity.class);
            scanIntent.putExtra(CardIOActivity.EXTRA_SUPPRESS_SCAN,true); // supmit cuando termine de reconocer el documento
            scanIntent.putExtra(CardIOActivity.EXTRA_SUPPRESS_MANUAL_ENTRY,true); // esconder teclado
            scanIntent.putExtra(CardIOActivity.EXTRA_USE_CARDIO_LOGO,true); // cambiar logo de paypal por el de card.io
            scanIntent.putExtra(CardIOActivity.EXTRA_RETURN_CARD_IMAGE,true); // capture img
            scanIntent.putExtra(CardIOActivity.EXTRA_CAPTURED_CARD_IMAGE,true); // capturar img
            startActivityForResult(scanIntent, REQUEST_CODE_FOTO_INE_REVERSO);
        }
    };

    /*private View.OnClickListener ibCurp_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i = new Intent(RenovacionCreditoInd.this, CameraVertical.class);
            i.putExtra(ORDER_ID, "O_curp");
            startActivityForResult(i, REQUEST_CODE_FOTO_CURP);
        }
    };*/

    private View.OnClickListener ibComprobante_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i = new Intent(RenovacionCreditoInd.this, CameraVertical.class);
            i.putExtra(ORDER_ID, "O_comprobante");
            startActivityForResult(i, REQUEST_CODE_FOTO_COMPROBATE);
        }
    };

    //================== IMAGE VIEW LISTENER  ======================================================
    private View.OnClickListener ivFotoFachCli_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (isEditCli){
                final AlertDialog evidencia_dlg = Popups.showDialogConfirmImage(ctx, question,
                        R.string.capturar_foto, R.string.fotografia, new Popups.DialogMessage() {
                            @Override
                            public void OnClickListener(AlertDialog dialog) {
                                Intent i = new Intent(ctx, CameraVertical.class);
                                startActivityForResult(i, REQUEST_CODE_CAMARA_FACHADA_CLI);
                                dialog.dismiss();

                            }
                        }, R.string.ver_imagen, new Popups.DialogMessage() {
                            @Override
                            public void OnClickListener(AlertDialog dialog) {
                                Intent i = new Intent(ctx, VerImagen.class);
                                i.putExtra(IMAGEN, byteFotoFachCli);
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
                final AlertDialog fachada_dlg = Popups.showDialogConfirm(ctx, question,
                        R.string.ver_fotografia, R.string.ver_imagen, new Popups.DialogMessage() {
                            @Override
                            public void OnClickListener(AlertDialog dialog) {
                                Intent i = new Intent(ctx, VerImagen.class);
                                i.putExtra(IMAGEN, byteFotoFachCli);
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
            if (isEditCli){
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
    private View.OnClickListener ivFotoFachNeg_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (isEditNeg){
                final AlertDialog evidencia_dlg = Popups.showDialogConfirmImage(ctx, question,
                        R.string.capturar_foto, R.string.fotografia, new Popups.DialogMessage() {
                            @Override
                            public void OnClickListener(AlertDialog dialog) {
                                Intent i = new Intent(ctx, CameraVertical.class);
                                startActivityForResult(i, REQUEST_CODE_CAMARA_FACHADA_NEG);
                                dialog.dismiss();

                            }
                        }, R.string.ver_imagen, new Popups.DialogMessage() {
                            @Override
                            public void OnClickListener(AlertDialog dialog) {
                                Intent i = new Intent(ctx, VerImagen.class);
                                i.putExtra(IMAGEN, byteFotoFachNeg);
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
                final AlertDialog fachada_dlg = Popups.showDialogConfirm(ctx, question,
                        R.string.ver_fotografia, R.string.ver_imagen, new Popups.DialogMessage() {
                            @Override
                            public void OnClickListener(AlertDialog dialog) {
                                Intent i = new Intent(ctx, VerImagen.class);
                                i.putExtra(IMAGEN, byteFotoFachNeg);
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
    private View.OnClickListener ivFotoFachAval_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (isEditAva){
                final AlertDialog evidencia_dlg = Popups.showDialogConfirmImage(ctx, question,
                        R.string.capturar_foto, R.string.fotografia, new Popups.DialogMessage() {
                            @Override
                            public void OnClickListener(AlertDialog dialog) {
                                Intent i = new Intent(ctx, CameraVertical.class);
                                startActivityForResult(i, REQUEST_CODE_CAMARA_FACHADA_AVAL);
                                dialog.dismiss();

                            }
                        }, R.string.ver_imagen, new Popups.DialogMessage() {
                            @Override
                            public void OnClickListener(AlertDialog dialog) {
                                Intent i = new Intent(ctx, VerImagen.class);
                                i.putExtra(IMAGEN, byteFotoFachAval);
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
                final AlertDialog fachada_dlg = Popups.showDialogConfirm(ctx, question,
                        R.string.ver_fotografia, R.string.ver_imagen, new Popups.DialogMessage() {
                            @Override
                            public void OnClickListener(AlertDialog dialog) {
                                Intent i = new Intent(ctx, VerImagen.class);
                                i.putExtra(IMAGEN, byteFotoFachAval);
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
    private View.OnClickListener ivFirmaAval_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (isEditAva){
                final AlertDialog firma_dlg = Popups.showDialogConfirm(ctx, firma,
                        R.string.capturar_nueva_firma, R.string.accept, new Popups.DialogMessage() {
                            @Override
                            public void OnClickListener(AlertDialog dialog) {
                                Intent sig = new Intent(ctx, CapturarFirma.class);
                                sig.putExtra(TIPO,"");
                                startActivityForResult(sig, REQUEST_CODE_FIRMA_AVAL);
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
    private View.OnClickListener ivIneFrontal_OnClik = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (isEditDoc){
                final AlertDialog evidencia_dlg = Popups.showDialogConfirmImage(ctx, question,
                        R.string.capturar_foto, R.string.fotografia, new Popups.DialogMessage() {
                            @Override
                            public void OnClickListener(AlertDialog dialog) {
                                Intent scanIntent = new Intent(RenovacionCreditoInd.this, CardIOActivity.class);
                                scanIntent.putExtra(CardIOActivity.EXTRA_SUPPRESS_SCAN,true); // supmit cuando termine de reconocer el documento
                                scanIntent.putExtra(CardIOActivity.EXTRA_SUPPRESS_MANUAL_ENTRY,true); // esconder teclado
                                scanIntent.putExtra(CardIOActivity.EXTRA_USE_CARDIO_LOGO,true); // cambiar logo de paypal por el de card.io
                                scanIntent.putExtra(CardIOActivity.EXTRA_RETURN_CARD_IMAGE,true); // capture img
                                scanIntent.putExtra(CardIOActivity.EXTRA_CAPTURED_CARD_IMAGE,true); // capturar img
                                startActivityForResult(scanIntent, REQUEST_CODE_FOTO_INE_FRONTAL);
                                dialog.dismiss();

                            }
                        }, R.string.ver_imagen, new Popups.DialogMessage() {
                            @Override
                            public void OnClickListener(AlertDialog dialog) {
                                Intent i = new Intent(ctx, VerImagen.class);
                                i.putExtra(IMAGEN, byteIneFrontal);
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
                                i.putExtra(IMAGEN, byteIneFrontal);
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
            if (isEditDoc){
                final AlertDialog evidencia_dlg = Popups.showDialogConfirmImage(ctx, question,
                        R.string.capturar_foto, R.string.fotografia, new Popups.DialogMessage() {
                            @Override
                            public void OnClickListener(AlertDialog dialog) {
                                Intent scanIntent = new Intent(RenovacionCreditoInd.this, CardIOActivity.class);
                                scanIntent.putExtra(CardIOActivity.EXTRA_SUPPRESS_SCAN,true); // supmit cuando termine de reconocer el documento
                                scanIntent.putExtra(CardIOActivity.EXTRA_SUPPRESS_MANUAL_ENTRY,true); // esconder teclado
                                scanIntent.putExtra(CardIOActivity.EXTRA_USE_CARDIO_LOGO,true); // cambiar logo de paypal por el de card.io
                                scanIntent.putExtra(CardIOActivity.EXTRA_RETURN_CARD_IMAGE,true); // capture img
                                scanIntent.putExtra(CardIOActivity.EXTRA_CAPTURED_CARD_IMAGE,true); // capturar img
                                startActivityForResult(scanIntent, REQUEST_CODE_FOTO_INE_REVERSO);
                                dialog.dismiss();

                            }
                        }, R.string.ver_imagen, new Popups.DialogMessage() {
                            @Override
                            public void OnClickListener(AlertDialog dialog) {
                                Intent i = new Intent(ctx, VerImagen.class);
                                i.putExtra(IMAGEN, byteIneReverso);
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
                                i.putExtra(IMAGEN, byteIneReverso);
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
    /*private View.OnClickListener ivCurp_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (is_edit){
                final AlertDialog evidencia_dlg = Popups.showDialogConfirmImage(ctx, question,
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
                                i.putExtra(IMAGEN, byteCurp);
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
                final AlertDialog fachada_dlg = Popups.showDialogConfirm(ctx, question,
                        R.string.ver_fotografia, R.string.ver_imagen, new Popups.DialogMessage() {
                            @Override
                            public void OnClickListener(AlertDialog dialog) {
                                Intent i = new Intent(ctx, VerImagen.class);
                                i.putExtra(IMAGEN, byteCurp);
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
    };*/
    private View.OnClickListener ivComprobante_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (isEditDoc){
                final AlertDialog evidencia_dlg = Popups.showDialogConfirmImage(ctx, question,
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
                                i.putExtra(IMAGEN, byteComprobante);
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
                final AlertDialog fachada_dlg = Popups.showDialogConfirm(ctx, question,
                        R.string.ver_fotografia, R.string.ver_imagen, new Popups.DialogMessage() {
                            @Override
                            public void OnClickListener(AlertDialog dialog) {
                                Intent i = new Intent(ctx, VerImagen.class);
                                i.putExtra(IMAGEN, byteComprobante);
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
    private View.OnClickListener ivFirmaAsesor_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (isEditDoc){
                final AlertDialog firma_dlg = Popups.showDialogConfirm(ctx, firma,
                        R.string.capturar_nueva_firma, R.string.accept, new Popups.DialogMessage() {
                            @Override
                            public void OnClickListener(AlertDialog dialog) {
                                Intent sig = new Intent(ctx, CapturarFirma.class);
                                sig.putExtra(TIPO,"");
                                startActivityForResult(sig, REQUEST_CODE_FIRMA_ASESOR);
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

    // ================== IMAGE VIEW  ===================================
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

    private View.OnClickListener llDocumentos_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (ivDown10.getVisibility() == View.VISIBLE && ivUp10.getVisibility() == View.GONE){
                ivDown10.setVisibility(View.GONE);
                ivUp10.setVisibility(View.VISIBLE);
                llDatosDocumentos.setVisibility(View.VISIBLE);
            }
            else if (ivDown10.getVisibility() == View.GONE && ivUp10.getVisibility() == View.VISIBLE){
                ivDown10.setVisibility(View.VISIBLE);
                ivUp10.setVisibility(View.GONE);
                llDatosDocumentos.setVisibility(View.GONE);

            }
        }
    };

    //==================================================================

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
    private View.OnClickListener btnContinuar8_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ivDown10.setVisibility(View.GONE);
            ivUp10.setVisibility(View.VISIBLE);
            llDatosDocumentos.setVisibility(View.VISIBLE);

            ivDown9.setVisibility(View.VISIBLE);
            ivUp9.setVisibility(View.GONE);
            llDatosPoliticas.setVisibility(View.GONE);
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

    private View.OnClickListener btnRegresar9_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ivDown9.setVisibility(View.GONE);
            ivUp9.setVisibility(View.VISIBLE);
            llDatosPoliticas.setVisibility(View.VISIBLE);

            ivDown10.setVisibility(View.VISIBLE);
            ivUp10.setVisibility(View.GONE);
            llDatosDocumentos.setVisibility(View.GONE);
        }
    };


    private View.OnClickListener tvHoraVisita_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (isEditCre) {
                dialog_time_picker timePicker = new dialog_time_picker();
                Bundle b = new Bundle();
                b.putInt(IDENTIFIER, 4);
                timePicker.setArguments(b);
                timePicker.show(getSupportFragmentManager(), NameFragments.DIALOGTIMEPICKER);
            }
        }
    };

    private boolean saveDatosCredito(){
        boolean save_credito = false;
        if (!validatorTV.validate(tvPlazo, new String[]{validatorTV.REQUIRED}) &&
                !validatorTV.validate(tvFrecuencia, new String[]{validatorTV.REQUIRED}) &&
                !validatorTV.validate(tvFechaDesembolso, new String[]{validatorTV.REQUIRED}) &&
                !validatorTV.validate(tvDiaDesembolso, new String[]{validatorTV.REQUIRED}) &&
                !validatorTV.validate(tvHoraVisita, new String[]{validatorTV.REQUIRED}) &&
                !validator.validate(etMontoPrestamo, new String[]{validator.REQUIRED, validator.MONEY, validator.CREDITO}) &&
                !validatorTV.validate(tvDestino, new String[]{validatorTV.REQUIRED}) &&
                !validatorTV.validate(tvRiesgo, new String[]{validatorTV.REQUIRED}) &&
                !validatorTV.validate(tvComportamiento, new String[]{validatorTV.REQUIRED}) &&
                !validator.validate(etObservaciones, new String[]{validator.GENERAL})){
            ivError1.setVisibility(View.GONE);
            ContentValues cv = new ContentValues();
            cv.put("plazo",tvPlazo.getText().toString().trim().toUpperCase());
            cv.put("periodicidad",tvFrecuencia.getText().toString().trim().toUpperCase());
            cv.put("fecha_desembolso",tvFechaDesembolso.getText().toString().trim());
            cv.put("dia_desembolso",tvDiaDesembolso.getText().toString().trim().toUpperCase());
            cv.put("hora_visita",tvHoraVisita.getText().toString().trim());
            cv.put("monto_prestamo",etMontoPrestamo.getText().toString().trim().replace(",",""));
            cv.put("destino",tvDestino.getText().toString().trim().toUpperCase());
            cv.put("clasificacion_riesgo", tvRiesgo.getText().toString().trim().toUpperCase());
            cv.put("clasificacion_riesgo", tvRiesgo.getText().toString().trim().toUpperCase());
            cv.put("comportamiento_pago", tvComportamiento.getText().toString().trim().toUpperCase());
            cv.put("observaciones", etObservaciones.getText().toString().trim().toUpperCase());

            db.update(TBL_CREDITO_IND_REN, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
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
                !validator.validate(etApPaternoCli, new String[]{validator.ONLY_TEXT}) &&
                !validator.validate(etApMaternoCli, new String[]{validator.ONLY_TEXT}) &&
                !validatorTV.validate(tvFechaNacCli, new String[]{validatorTV.REQUIRED}) &&
                !validatorTV.validate(tvEdadCli, new String[]{validatorTV.REQUIRED, validatorTV.ONLY_NUMBER})){
            if (rgGeneroCli.getCheckedRadioButtonId() == R.id.rbHombre ||
                    rgGeneroCli.getCheckedRadioButtonId() == R.id.rbMujer){
                tvGeneroCli.setError(null);
                if (!validatorTV.validate(tvEstadoNacCli, new String[]{validatorTV.REQUIRED}) &&
                    !validator.validate(etCurpCli, new String[]{validator.REQUIRED, validator.CURP}) &&
                    (!validatorTV.validate(tvRfcCli, new String[]{validatorTV.REQUIRED}) &&
                    !tvRfcCli.getText().toString().trim().equals("Rfc no válida"))){
                    if (Miscellaneous.CurpValidador(etCurpCli.getText().toString().trim().toUpperCase())){
                        if(!validatorTV.validate(tvOcupacionCli, new String[]{validatorTV.REQUIRED}) &&
                                !validatorTV.validate(tvActividadEcoCli, new String[]{validatorTV.REQUIRED}) &&
                                !validatorTV.validate(tvTipoIdentificacion, new String[]{validatorTV.REQUIRED}) &&
                                !validator.validate(etNumIdentifCli, new String[]{validator.REQUIRED, validator.ALFANUMERICO}) &&
                                !validatorTV.validate(tvEstudiosCli, new String[]{validatorTV.REQUIRED}) &&
                                !validatorTV.validate(tvEstadoCivilCli, new String[]{validatorTV.REQUIRED})){
                            boolean flag_est_civil = false;
                            if (tvEstadoCivilCli.getText().toString().trim().equals("CASADO(A)")){
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
                                                    !validator.validate(etCiudadCli, new String[]{validator.REQUIRED, validator.ONLY_TEXT}) &&
                                                    !validatorTV.validate(tvLocalidadCli, new String[]{validatorTV.REQUIRED}) &&
                                                    !Miscellaneous.ValidTextView(tvMunicipioCli) &&
                                                    !Miscellaneous.ValidTextView(tvEstadoCli) &&
                                                    !validator.validate(etTelCasaCli, new String[]{validator.PHONE}) &&
                                                    !validator.validate(etCelularCli, new String[]{validator.REQUIRED, validator.PHONE}) &&
                                                    !validator.validate(etTelMensCli, new String[]{validator.PHONE}) &&
                                                    !validator.validate(etTelTrabajoCli, new String[]{validator.PHONE}) &&
                                                    !validator.validate(etTiempoSitio, new String[]{validator.REQUIRED, validator.ONLY_NUMBER, validator.YEARS}) &&
                                                    !validatorTV.validate(tvDependientes, new String[]{validatorTV.REQUIRED}) &&
                                                    !validatorTV.validate(tvEnteroNosotros, new String[]{validatorTV.REQUIRED}) &&
                                                    !validatorTV.validate(tvEstadoCuenta, new String[]{validatorTV.REQUIRED}) &&
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
                                                            cv.put("curp", etCurpCli.getText().toString().trim().toUpperCase());
                                                            //cv.put("curp_digito_veri", etCurpIdCli.getText().toString().trim());
                                                            cv.put("ocupacion", tvOcupacionCli.getText().toString().trim().toUpperCase());
                                                            cv.put("actividad_economica", tvActividadEcoCli.getText().toString().trim().toUpperCase());
                                                            cv.put("tipo_identificacion", tvTipoIdentificacion.getText().toString().trim().toUpperCase());
                                                            cv.put("no_identificacion", etNumIdentifCli.getText().toString().trim());
                                                            cv.put("nivel_estudio", tvEstudiosCli.getText().toString().trim().toUpperCase());
                                                            cv.put("estado_civil", tvEstadoCivilCli.getText().toString().trim().toUpperCase());
                                                            cv.put("tel_casa", etTelCasaCli.getText().toString().trim().toUpperCase());
                                                            cv.put("tel_celular", etCelularCli.getText().toString().trim().toUpperCase());
                                                            cv.put("tel_mensajes", etTelMensCli.getText().toString().trim().toUpperCase());
                                                            cv.put("tel_trabajo", etTelTrabajoCli.getText().toString().trim().toUpperCase());
                                                            cv.put("tiempo_vivir_sitio", Integer.parseInt(etTiempoSitio.getText().toString().trim()));
                                                            cv.put("dependientes", tvDependientes.getText().toString().trim());
                                                            cv.put("medio_contacto", tvEnteroNosotros.getText().toString().trim().toUpperCase());
                                                            cv.put("estado_cuenta", tvEstadoCuenta.getText().toString().trim());
                                                            cv.put("email", etEmail.getText().toString().trim());
                                                            cv.put("ref_domiciliaria", etReferenciCli.getText().toString().trim().toUpperCase());

                                                            db.update(TBL_CLIENTE_IND_REN, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});

                                                            cv = new ContentValues();
                                                            cv.put("latitud", String.valueOf(latLngUbiCli.latitude));
                                                            cv.put("longitud", String.valueOf(latLngUbiCli.longitude));
                                                            cv.put("calle", etCalleCli.getText().toString().trim().toUpperCase());
                                                            cv.put("num_exterior", etNoExtCli.getText().toString().trim().toUpperCase());
                                                            cv.put("num_interior", etNoIntCli.getText().toString().trim().toUpperCase());
                                                            cv.put("manzana", etManzanaCli.getText().toString().trim().toUpperCase());
                                                            cv.put("lote", etLoteCli.getText().toString().trim().toUpperCase());
                                                            cv.put("cp", etCpCli.getText().toString().trim().toUpperCase());
                                                            cv.put("colonia", tvColoniaCli.getText().toString().trim().toUpperCase());
                                                            cv.put("ciudad", etCiudadCli.getText().toString().trim().toUpperCase());
                                                            cv.put("localidad", tvLocalidadCli.getText().toString().trim().toUpperCase());
                                                            cv.put("municipio", tvMunicipioCli.getText().toString().trim().toUpperCase());
                                                            cv.put("estado", tvEstadoCli.getText().toString().trim().toUpperCase());
                                                            db.update(TBL_DIRECCIONES_REN, cv,"id_direccion = ? AND tipo_direccion = ?", new String[]{direccionIdCli, "CLIENTE"});
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
                        etCurpCli.setError("Curp no válida");
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
                !validator.validate(etApPaternoCony, new String[]{validator.ONLY_TEXT}) &&
                !validator.validate(etApMaternoCony, new String[]{validator.ONLY_TEXT}) &&
                !validator.validate(etNacionalidadCony, new String[]{validator.REQUIRED, validator.ONLY_TEXT}) &&
                !validatorTV.validate(tvOcupacionCony, new String[]{validatorTV.REQUIRED}) &&
                !validator.validate(etCalleCony, new String[]{validator.REQUIRED}) &&
                !validator.validate(etNoExtCony, new String[]{validator.REQUIRED}) &&
                !validator.validate(etCpCony, new String[]{validator.REQUIRED, validator.ONLY_NUMBER, validator.CP}) &&
                !Miscellaneous.ValidTextView(tvColoniaCony) &&
                !validator.validate(etCiudadCony, new String[]{validator.REQUIRED,  validator.ONLY_TEXT}) &&
                !validatorTV.validate(tvLocalidadCony, new String[]{validatorTV.REQUIRED}) &&
                !Miscellaneous.ValidTextView(tvMunicipioCony) &&
                !Miscellaneous.ValidTextView(tvEstadoCony) &&
                !validator.validate(etIngMenCony, new String[]{validator.REQUIRED, validator.ONLY_NUMBER}) &&
                !validator.validate(etGastoMenCony, new String[]{validator.REQUIRED, validator.ONLY_NUMBER}) &&
                !validator.validate(etCasaCony, new String[]{validator.PHONE}) &&
                !validator.validate(etCelularCony, new String[]{validator.REQUIRED, validator.PHONE})){
            Log.e("Conyuge", "pasa");
            ivError3.setVisibility(View.GONE);
            ContentValues cv = new ContentValues();
            cv.put("nombre", etNombreCony.getText().toString().trim().toUpperCase());
            cv.put("paterno", etApPaternoCony.getText().toString().trim().toUpperCase());
            cv.put("materno", etApMaternoCony.getText().toString().trim().toUpperCase());
            cv.put("nacionalidad", etNacionalidadCony.getText().toString().trim().toUpperCase());
            cv.put("ocupacion", tvOcupacionCony.getText().toString().trim().toUpperCase());
            cv.put("ing_mensual", etIngMenCony.getText().toString().trim().toUpperCase().replace(",",""));
            cv.put("gasto_mensual", etGastoMenCony.getText().toString().trim().toUpperCase().replace(",",""));
            cv.put("tel_casa", etCasaCony.getText().toString().trim().toUpperCase());
            cv.put("tel_celular", etCelularCony.getText().toString().trim());

            db.update(TBL_CONYUGE_IND_REN, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});

            cv = new ContentValues();
            cv.put("calle", etCalleCony.getText().toString().trim().toUpperCase());
            cv.put("num_exterior", etNoExtCony.getText().toString().trim().toUpperCase());
            cv.put("num_interior", etNoIntCony.getText().toString().trim().toUpperCase());
            cv.put("manzana", etManzanaCony.getText().toString().trim().toUpperCase());
            cv.put("lote", etLoteCony.getText().toString().trim().toUpperCase());
            cv.put("cp", etCpCony.getText().toString().trim().toUpperCase());
            cv.put("colonia", tvColoniaCony.getText().toString().trim().toUpperCase());
            cv.put("ciudad", etCiudadCony.getText().toString().trim().toUpperCase());
            cv.put("localidad", tvLocalidadCony.getText().toString().trim().toUpperCase());
            cv.put("municipio", tvMunicipioCony.getText().toString().trim().toUpperCase());
            cv.put("estado", tvEstadoCony.getText().toString().trim().toUpperCase());
            db.update(TBL_DIRECCIONES_REN, cv,"id_direccion = ? AND tipo_direccion = ?", new String[]{direccionIdCony, "CONYUGE"});

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
                !validatorTV.validate(tvIngresoEco, new String[]{validatorTV.REQUIRED})){
            ivError4.setVisibility(View.GONE);
            ContentValues cv = new ContentValues();
            cv.put("propiedades", etPropiedadesEco.getText().toString().trim().toUpperCase());
            cv.put("valor_aproximado", etValorAproxEco.getText().toString().trim());
            cv.put("ubicacion", etUbicacionEco.getText().toString().trim().toUpperCase());
            cv.put("ingreso", tvIngresoEco.getText().toString().trim().replace(",",""));

            db.update(TBL_ECONOMICOS_IND_REN, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});

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
                        !validator.validate(etCiudadNeg, new String[]{validator.REQUIRED, validator.ONLY_TEXT}) &&
                        !validatorTV.validate(tvLocalidadNeg, new String[]{validatorTV.REQUIRED}) &&
                        !Miscellaneous.ValidTextView(tvMunicipioNeg) &&
                        !validatorTV.validate(tvActEcoEspNeg, new String[]{validatorTV.REQUIRED}) &&
                        !validatorTV.validate(tvActEconomicaNeg, new String[]{validatorTV.REQUIRED}) &&
                        !validatorTV.validate(tvDestinoNeg, new String[]{validatorTV.REQUIRED})){
                    boolean otro_destino = false;
                    if (tvDestinoNeg.getText().toString().trim().toUpperCase().equals("OTRO")){
                        if (!validator.validate(etOtroDestinoNeg, new String[]{validator.REQUIRED})){
                            otro_destino = true;
                        }
                    }
                    else
                        otro_destino = true;
                    if (otro_destino){
                        if(!validator.validate(etAntiguedadNeg, new String[]{validator.REQUIRED, validator.YEARS}) &&
                                !validator.validate(etIngMenNeg, new String[]{validator.REQUIRED, validator.ONLY_NUMBER}) &&
                                !validator.validate(etOtrosIngNeg, new String[]{validator.REQUIRED, validator.ONLY_NUMBER}) &&
                                !validator.validate(etGastosMenNeg, new String[]{validator.REQUIRED, validator.ONLY_NUMBER}) &&
                                !validator.validate(etGastosAguaNeg, new String[]{validator.REQUIRED, validator.ONLY_NUMBER}) &&
                                !validator.validate(etGastosLuzNeg, new String[]{validator.REQUIRED, validator.ONLY_NUMBER}) &&
                                !validator.validate(etGastosTelNeg, new String[]{validator.REQUIRED, validator.ONLY_NUMBER}) &&
                                !validator.validate(etGastosRentaNeg, new String[]{validator.REQUIRED, validator.ONLY_NUMBER}) &&
                                !validator.validate(etGastosOtrosNeg, new String[]{validator.REQUIRED, validator.ONLY_NUMBER}) &&
                                !validator.validate(etCapacidadPagoNeg, new String[]{validator.REQUIRED, validator.MONEY}) &&
                                !validatorTV.validate(tvMediosPagoNeg, new String[]{validatorTV.REQUIRED})){
                            boolean otro_medio = false;
                            if (tvMediosPagoNeg.getText().toString().trim().contains("OTRO")){
                                if (!validator.validate(etOtroMedioPagoNeg, new String[]{validator.REQUIRED}))
                                    otro_medio = true;
                            }
                            else
                                otro_medio = true;

                            if (otro_medio){
                                if (!validatorTV.validate(tvMontoMaxNeg, new String[]{validatorTV.REQUIRED}) &&
                                        !validatorTV.validate(tvNumOperacionNeg, new String[]{validatorTV.REQUIRED}) &&
                                        (tvMediosPagoNeg.getText().toString().contains("EFECTIVO") && !validator.validate(etNumOperacionEfectNeg, new String[]{validator.REQUIRED, validator.YEARS})) || !tvMediosPagoNeg.getText().toString().contains("EFECTIVO")){
                                    if(!validatorTV.validate(tvDiasVentaNeg, new String[]{validatorTV.REQUIRED})){
                                        if (byteFotoFachNeg != null) {
                                            tvFachadaNeg.setError(null);
                                            if (!validator.validate(etReferenciNeg, new String[]{validator.REQUIRED, validator.GENERAL})){
                                                ivError5.setVisibility(View.GONE);
                                                ContentValues cv = new ContentValues();
                                                cv.put("nombre", etNombreNeg.getText().toString().trim().toUpperCase());
                                                cv.put("actividad_economica", tvActEconomicaNeg.getText().toString().trim().toUpperCase());
                                                cv.put("destino_credito", tvDestinoNeg.getText().toString().trim());
                                                cv.put("otro_destino", etOtroDestinoNeg.getText().toString().trim().toUpperCase());
                                                cv.put("antiguedad", Integer.parseInt(etAntiguedadNeg.getText().toString().trim()));
                                                cv.put("ing_mensual", etIngMenNeg.getText().toString().toUpperCase().replace(",",""));
                                                cv.put("ing_otros", etGastosOtrosNeg.getText().toString().trim().replace(",",""));
                                                cv.put("gasto_semanal", etGastosMenNeg.getText().toString().trim().replace(",",""));
                                                cv.put("gasto_agua", etGastosAguaNeg.getText().toString().trim().replace(",",""));
                                                cv.put("gasto_luz", etGastosLuzNeg.getText().toString().trim().replace(",",""));
                                                cv.put("gasto_telefono", etGastosTelNeg.getText().toString().trim().replace(",",""));
                                                cv.put("gasto_renta", etGastosRentaNeg.getText().toString().trim().replace(",",""));
                                                cv.put("gasto_otros", etGastosOtrosNeg.getText().toString().trim().replace(",",""));
                                                cv.put("capacidad_pago", etCapacidadPagoNeg.getText().toString().trim().replace(",",""));
                                                cv.put("medio_pago", tvMediosPagoNeg.getText().toString().trim());
                                                cv.put("otro_medio_pago", etOtroMedioPagoNeg.getText().toString().trim().toUpperCase());
                                                cv.put("monto_maximo", tvMontoMaxNeg.getText().toString().trim().replace(",",""));
                                                cv.put("dias_venta", tvDiasVentaNeg.getText().toString().trim());
                                                cv.put("ref_domiciliaria", etReferenciNeg.getText().toString().trim().toUpperCase());

                                                db.update(TBL_NEGOCIO_IND_REN, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});

                                                cv = new ContentValues();
                                                cv.put("calle", etCalleNeg.getText().toString().trim().toUpperCase());
                                                cv.put("num_exterior", etNoExtNeg.getText().toString().trim().toUpperCase());
                                                cv.put("num_interior", etNoIntNeg.getText().toString().trim().toUpperCase());
                                                cv.put("manzana", etManzanaNeg.getText().toString().trim().toUpperCase());
                                                cv.put("lote", etLoteNeg.getText().toString().trim().toUpperCase());
                                                cv.put("cp", etCpNeg.getText().toString().trim().toUpperCase());
                                                cv.put("colonia", tvColoniaNeg.getText().toString().trim().toUpperCase());
                                                cv.put("ciudad", etCiudadNeg.getText().toString().trim().toUpperCase());
                                                cv.put("localidad", tvLocalidadNeg.getText().toString().trim().toUpperCase());
                                                cv.put("municipio", tvMunicipioNeg.getText().toString().trim().toUpperCase());
                                                db.update(TBL_DIRECCIONES_REN, cv,"id_direccion = ? AND tipo_direccion = ?", new String[]{direccionIdNeg, "NEGOCIO"});

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
                                else
                                    ivError5.setVisibility(View.VISIBLE);
                            }
                            else
                                ivError5.setVisibility(View.VISIBLE);
                        }
                        else
                            ivError5.setVisibility(View.VISIBLE);
                    }else
                        ivError5.setVisibility(View.VISIBLE);
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
                !validator.validate(etApPaternoAval, new String[] {validator.ONLY_TEXT}) &&
                !validator.validate(etApMaternoAval, new String[]{validator.ONLY_TEXT}) &&
                !validatorTV.validate(tvFechaNacAval, new String[]{validatorTV.REQUIRED}) &&
                !validatorTV.validate(tvEdadAval, new String[]{validatorTV.REQUIRED, validatorTV.ONLY_NUMBER})){
            if (rgGeneroAval.getCheckedRadioButtonId() == R.id.rbHombre ||
                    rgGeneroAval.getCheckedRadioButtonId() == R.id.rbMujer) {
                tvGeneroAval.setError(null);
                if (!validatorTV.validate(tvEstadoNacAval, new String[]{validatorTV.REQUIRED}) &&
                        !validator.validate(etCurpAval, new String[]{validator.REQUIRED, validator.CURP}) &&
                        (!tvRfcAval.getText().toString().trim().equals("Rfc no válida"))){
                    if (Miscellaneous.CurpValidador(etCurpAval.getText().toString().trim().toUpperCase())) {
                        etCurpAval.setError(null);
                        if(!validatorTV.validate(tvParentescoAval, new String[]{validatorTV.REQUIRED}) &&
                                !validatorTV.validate(tvTipoIdentificacionAval, new String[]{validatorTV.REQUIRED}) &&
                                !validator.validate(etNumIdentifAval, new String[]{validator.REQUIRED, validator.ALFANUMERICO}) &&
                                !validatorTV.validate(tvOcupacionAval, new String[]{validatorTV.REQUIRED}) &&
                                !validatorTV.validate(tvActividadEcoAval, new String[]{validatorTV.REQUIRED})){
                            if (latLngUbiAval != null){
                                tvMapaAval.setError(null);
                                if (!validator.validate(etCalleAval, new String[]{validator.REQUIRED}) &&
                                        !validator.validate(etNoExtAval, new String[]{validator.REQUIRED}) &&
                                        !validator.validate(etCpAval, new String[]{validator.REQUIRED, validator.ONLY_NUMBER, validator.CP}) &&
                                        !Miscellaneous.ValidTextView(tvColoniaAval) &&
                                        !validator.validate(etCiudadAval, new String[]{validator.REQUIRED, validator.ONLY_TEXT}) &&
                                        !validatorTV.validate(tvLocalidadAval, new String[]{validatorTV.REQUIRED}) &&
                                        !Miscellaneous.ValidTextView(tvMunicipioAval) &&
                                        !Miscellaneous.ValidTextView(tvEstadoAval) &&
                                        !validatorTV.validate(tvTipoCasaAval, new String[]{validatorTV.REQUIRED})){
                                    cv.put("tipo_vivienda", tvTipoCasaAval.getText().toString().trim().toUpperCase());
                                    boolean flag_tipo_casa =  false;
                                    if (tvTipoCasaAval.getText().toString().trim().toUpperCase().equals("CASA FAMILIAR") ||
                                            tvTipoCasaAval.getText().toString().trim().toUpperCase().equals("CASA RENTADA")){
                                        if (!validatorTV.validate(tvFamiliarAval, new String[]{validatorTV.REQUIRED}) &&
                                                !validator.validate(etNombreTitularAval, new String[]{validator.REQUIRED, validator.ONLY_TEXT})){
                                            cv.put("nombre_titular", etNombreTitularAval.getText().toString().trim().toUpperCase());
                                            cv.put("parentesco", tvFamiliarAval.getText().toString().trim().toUpperCase());
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
                                        if (!validator.validate(etCaracteristicasAval, new String[]{validator.REQUIRED})){
                                            if (rgNegocioAval.getCheckedRadioButtonId() == R.id.rbSiNeg ||
                                                    rgNegocioAval.getCheckedRadioButtonId() == R.id.rbNoNeg) {
                                                tvNombreNegAval.setError(null);

                                                if(((rgNegocioAval.getCheckedRadioButtonId() == R.id.rbSiNeg && !validator.validate(etNombreNegocioAval, new String[]{validator.REQUIRED}) && !validator.validate(etAntiguedadAval, new String[]{validator.REQUIRED, validator.YEARS}) ) || rgNegocioAval.getCheckedRadioButtonId() == R.id.rbNoNeg) &&
                                                        !validator.validate(etIngMenAval, new String[]{validator.REQUIRED, validator.ONLY_NUMBER}) &&
                                                        !validator.validate(etIngOtrosAval, new String[]{validator.REQUIRED, validator.ONLY_NUMBER}) &&
                                                        !validator.validate(etGastosSemAval, new String[]{validator.REQUIRED, validator.ONLY_NUMBER}) &&
                                                        !validator.validate(etGastosAguaAval, new String[]{validator.REQUIRED, validator.ONLY_NUMBER}) &&
                                                        !validator.validate(etGastosLuzAval, new String[]{validator.REQUIRED, validator.ONLY_NUMBER}) &&
                                                        !validator.validate(etGastosTelAval, new String[]{validator.REQUIRED, validator.ONLY_NUMBER}) &&
                                                        !validator.validate(etGastosRentaAval, new String[]{validator.REQUIRED, validator.ONLY_NUMBER}) &&
                                                        !validator.validate(etGastosOtrosAval, new String[]{validator.REQUIRED, validator.ONLY_NUMBER}) &&
                                                        !validatorTV.validate(tvMediosPagoAval, new String[]{validatorTV.REQUIRED}) &&
                                                        (!tvMediosPagoAval.getText().toString().trim().contains("OTRO") ||
                                                                !validator.validate(etOtroMedioPagoAval, new String[]{validator.REQUIRED})) &&
                                                        !validatorTV.validate(tvMontoMaxAval, new String[]{validatorTV.REQUIRED}) &&
                                                        !validator.validate(etCapacidadPagoAval, new String[]{validator.REQUIRED, validator.MONEY}) &&
                                                        !validatorTV.validate(tvHoraLocAval, new String[]{validatorTV.REQUIRED}) &&
                                                        !validatorTV.validate(tvActivosObservables, new String[]{validatorTV.REQUIRED}) &&
                                                        !validator.validate(etTelCasaAval, new String[]{validator.PHONE}) &&
                                                        !validator.validate(etCelularAval, new String[]{validator.REQUIRED, validator.PHONE}) &&
                                                        !validator.validate(etTelMensAval, new String[]{validator.PHONE}) &&
                                                        !validator.validate(etTelTrabajoAval, new String[]{validator.PHONE}) &&
                                                        !validator.validate(etEmailAval, new String[]{validator.EMAIL})){
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
                                                                cv.put("curp", etCurpAval.getText().toString().trim().toUpperCase());
                                                                //cv.put("curp_digito", etCurpIdAval.getText().toString().trim());
                                                                cv.put("parentesco_cliente", tvParentescoAval.getText().toString().trim().toUpperCase());
                                                                cv.put("tipo_identificacion", tvTipoIdentificacionAval.getText().toString().trim().toUpperCase());
                                                                cv.put("no_identificacion", etNumIdentifAval.getText().toString().trim().toUpperCase());
                                                                cv.put("ocupacion", tvOcupacionAval.getText().toString().trim().toUpperCase());
                                                                cv.put("actividad_economica", tvActividadEcoAval.getText().toString().trim().toUpperCase());
                                                                cv.put("caracteristicas_domicilio", etCaracteristicasAval.getText().toString().trim());
                                                                switch (rgNegocioAval.getCheckedRadioButtonId()){
                                                                    case R.id.rbSiNeg:
                                                                        cv.put("tiene_negocio", 1);
                                                                        cv.put("nombre_negocio", etNombreNegocioAval.getText().toString().trim().toUpperCase());
                                                                        break;
                                                                    case R.id.rbNoNeg:
                                                                        cv.put("tiene_negocio", 2);
                                                                        break;
                                                                }
                                                                cv.put("ing_mensual", etIngMenAval.getText().toString().trim().replace(",",""));
                                                                cv.put("ing_otros", etIngOtrosAval.getText().toString().trim().replace(",",""));
                                                                cv.put("gasto_semanal", etGastosSemAval.getText().toString().trim().replace(",",""));
                                                                cv.put("gasto_agua", etGastosAguaAval.getText().toString().trim().replace(",",""));
                                                                cv.put("gasto_luz", etGastosLuzAval.getText().toString().trim().replace(",",""));
                                                                cv.put("gasto_telefono", etGastosTelAval.getText().toString().trim().replace(",",""));
                                                                cv.put("gasto_renta", etGastosRentaAval.getText().toString().trim().replace(",",""));
                                                                cv.put("gasto_otros", etGastosOtrosAval.getText().toString().trim().replace(",",""));
                                                                cv.put("medio_pago", tvMediosPagoAval.getText().toString().trim());
                                                                cv.put("otro_medio_pago", etOtroMedioPagoAval.getText().toString().trim());
                                                                cv.put("monto_maximo", tvMontoMaxAval.getText().toString().trim().replace(",",""));
                                                                cv.put("capacidad_pagos", etCapacidadPagoAval.getText().toString().trim().replace(",",""));
                                                                cv.put("horario_localizacion", tvHoraLocAval.getText().toString().trim());
                                                                cv.put("activos_observables", tvActivosObservables.getText().toString().trim().toUpperCase());
                                                                cv.put("antigueda", etAntiguedadAval.getText().toString().trim());
                                                                cv.put("tel_casa", etTelCasaAval.getText().toString().trim());
                                                                cv.put("tel_celular", etCelularAval.getText().toString().trim());
                                                                cv.put("tel_mensajes", etTelMensAval.getText().toString().trim());
                                                                cv.put("tel_trabajo", etTelTrabajoAval.getText().toString().trim());
                                                                cv.put("email", etEmailAval.getText().toString().trim());
                                                                cv.put("ref_domiciliaria", etReferenciaAval.getText().toString().trim().toUpperCase());

                                                                db.update(TBL_AVAL_IND_REN, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});

                                                                cv = new ContentValues();
                                                                cv.put("latitud", String.valueOf(latLngUbiAval.latitude));
                                                                cv.put("longitud", String.valueOf(latLngUbiAval.longitude));
                                                                cv.put("calle", etCalleAval.getText().toString().trim().toUpperCase());
                                                                cv.put("num_exterior", etNoExtAval.getText().toString().trim().toUpperCase());
                                                                cv.put("num_interior", etNoIntAval.getText().toString().trim().toUpperCase());
                                                                cv.put("manzana", etManzanaAval.getText().toString().trim().toUpperCase());
                                                                cv.put("lote", etLoteAval.getText().toString().trim().toUpperCase());
                                                                cv.put("cp", etCpAval.getText().toString().trim().toUpperCase());
                                                                cv.put("colonia", tvColoniaAval.getText().toString().trim().toUpperCase());
                                                                cv.put("ciudad", etCiudadAval.getText().toString().trim().toUpperCase());
                                                                cv.put("localidad", tvLocalidadAval.getText().toString().trim().toUpperCase());
                                                                cv.put("municipio", tvMunicipioAval.getText().toString().trim().toUpperCase());
                                                                cv.put("estado", tvEstadoAval.getText().toString().trim().toUpperCase());
                                                                db.update(TBL_DIRECCIONES_REN, cv, "id_direccion = ? AND tipo_direccion = ?", new String[]{direccionIdAval, "AVAL"});
                                                                save_aval = true;
                                                            }
                                                            else{
                                                                tvFirmaAval.setError("");
                                                            }
                                                        }
                                                        else {
                                                            ivError6.setVisibility(View.VISIBLE);
                                                        }
                                                    }
                                                    else{
                                                        tvFachadaAval.setError("");
                                                        ivError6.setVisibility(View.VISIBLE);
                                                    }
                                                }
                                                else {
                                                    ivError6.setVisibility(View.VISIBLE);
                                                }
                                            }
                                            else{
                                                tvNombreNegAval.setError("");
                                                ivError6.setVisibility(View.VISIBLE);
                                            }
                                        }
                                        else {
                                            ivError6.setVisibility(View.VISIBLE);
                                        }
                                    }
                                }
                                else {
                                    ivError6.setVisibility(View.VISIBLE);
                                }

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
                        etCurpAval.setError("Curp no válida");
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
                !validator.validate(etApPaternoRef, new String[]{validator.ONLY_TEXT}) &&
                !validator.validate(etApMaternoRef, new String[]{validator.ONLY_TEXT}) &&
                !validatorTV.validate(tvFechaNacRef, new String[]{validator.REQUIRED}) &&
                !validator.validate(etCalleRef, new String[]{validator.REQUIRED}) &&
                !validator.validate(etCpRef, new String[]{validator.REQUIRED, validator.CP}) &&
                !Miscellaneous.ValidTextView(tvColoniaRef) &&
                !validator.validate(etCiudadRef, new String[]{validator.REQUIRED, validator.ONLY_TEXT}) &&
                !validatorTV.validate(tvLocalidadRef, new String[]{validatorTV.REQUIRED}) &&
                !Miscellaneous.ValidTextView(tvMunicipioRef) &&
                !Miscellaneous.ValidTextView(tvEstadoRef) &&
                !validator.validate(etTelCelRef, new String[]{validator.REQUIRED, validator.ONLY_NUMBER, validator.PHONE})) {
            ivError7.setVisibility(View.GONE);
            ContentValues cv = new ContentValues();
            cv.put("nombre", etNombreRef.getText().toString().trim().toUpperCase());
            cv.put("paterno", etApPaternoRef.getText().toString().trim().toUpperCase());
            cv.put("materno", etApMaternoRef.getText().toString().trim().toUpperCase());
            cv.put("fecha_nacimiento", tvFechaNacRef.getText().toString().trim().toUpperCase());
            cv.put("tel_celular", etTelCelRef.getText().toString().trim().toUpperCase());

            db.update(TBL_REFERENCIA_IND_REN, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});

            cv = new ContentValues();
            cv.put("calle", etCalleRef.getText().toString().trim().toUpperCase());
            cv.put("num_exterior", etNoExtRef.getText().toString().trim().toUpperCase());
            cv.put("num_interior", etNoIntRef.getText().toString().trim().toUpperCase());
            cv.put("manzana", etManzanaRef.getText().toString().trim().toUpperCase());
            cv.put("lote", etLoteRef.getText().toString().trim().toUpperCase());
            cv.put("cp", etCpRef.getText().toString().trim().toUpperCase());
            cv.put("colonia", tvColoniaRef.getText().toString().trim().toUpperCase());
            cv.put("ciudad", etCiudadRef.getText().toString().trim().toUpperCase());
            cv.put("localidad", tvLocalidadRef.getText().toString().trim().toUpperCase());
            cv.put("municipio", tvMunicipioRef.getText().toString().trim().toUpperCase());
            cv.put("estado", tvEstadoRef.getText().toString().trim().toUpperCase());
            db.update(TBL_DIRECCIONES_REN, cv, "id_direccion = ? AND tipo_direccion = ?", new String[]{direccionIdRef, "REFERENCIA"});

            save_referencia = true;
        } else
            ivError7.setVisibility(View.VISIBLE);

        return save_referencia;
    }
    private boolean saveCroquis(){
        boolean save_croquis = false;
        if (!validatorTV.validate(tvLateraUno, new String[]{validatorTV.REQUIRED}) &&
                !validatorTV.validate(tvPrincipal, new String[]{validatorTV.REQUIRED}) &&
                !validatorTV.validate(tvTrasera, new String[]{validatorTV.REQUIRED}) &&
                !validatorTV.validate(tvLateraDos, new String[]{validatorTV.REQUIRED}) &&
                !validator.validate(etReferencia, new String[]{validatorTV.REQUIRED})){
            ivError8.setVisibility(View.GONE);
            ContentValues cv = new ContentValues();
            cv.put("calle_principal", tvPrincipal.getText().toString().trim().toUpperCase());
            cv.put("lateral_uno", tvLateraUno.getText().toString().trim().toUpperCase());
            cv.put("lateral_dos", tvLateraDos.getText().toString().trim().toUpperCase());
            cv.put("calle_trasera", tvTrasera.getText().toString().trim().toUpperCase());
            cv.put("referencias", etReferencia.getText().toString().trim().toUpperCase());

            db.update(TBL_CROQUIS_IND_REN, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
            save_croquis = true;
        }
        else
            ivError8.setVisibility(View.VISIBLE);
        return save_croquis;
    }
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
                    ivError9.setVisibility(View.GONE);
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

                    db.update(TBL_POLITICAS_PLD_IND_REN, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});

                    save_politicas = true;
                }
                else{
                    ivError9.setVisibility(View.VISIBLE);
                    tvPoliticamenteExp.setError("");
                }
            }
            else{
                ivError9.setVisibility(View.VISIBLE);
                tvProvedor.setError("");
            }
        }
        else{
            ivError9.setVisibility(View.VISIBLE);
            tvPropietarioReal.setError("");
        }
        return save_politicas;
    }
    private boolean saveDocumentacion(){
        boolean save_documentacion = false;
        if (byteIneFrontal != null){
            if (byteIneReverso != null){
                if (byteComprobante != null){
                    if (byteFirmaAsesor != null) {
                        save_documentacion = true;
                    }
                    else{
                        ivError10.setVisibility(View.VISIBLE);
                        tvFirmaAsesor.setError("");
                    }
                }
                else{
                    ivError10.setVisibility(View.VISIBLE);
                    tvComprobante.setError("");
                }
            }
            else{
                ivError10.setVisibility(View.VISIBLE);
                tvIneReverso.setError("");
            }
        }
        else{
            ivError10.setVisibility(View.VISIBLE);
            tvIneFrontal.setError("");
        }

        return save_documentacion;
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
                final AlertDialog loading = Popups.showLoadingDialog(ctx, R.string.please_wait, R.string.loading_info);
                loading.show();
                boolean credito, cliente, conyuge, economicos, negocio, aval, referencia, croquis, politicas, documentacion;
                credito = saveDatosCredito();
                cliente = saveDatosPersonales();
                if (tvEstadoCivilCli.getText().toString().trim().toUpperCase().equals("CASADO(A)") ||
                        tvEstadoCivilCli.getText().toString().trim().toUpperCase().equals("UNIÓN LIBRE"))
                    conyuge = saveConyuge();
                else
                    conyuge = true;
                if (!etMontoPrestamo.getText().toString().trim().replace(",","").isEmpty() && Integer.parseInt(etMontoPrestamo.getText().toString().trim().replace(",","")) >= 30000)
                    economicos = saveDatosEconomicos();
                else
                    economicos = true;
                negocio = saveDatosNegocio();
                aval = saveDatosAval();
                referencia = saveReferencia();
                croquis = saveCroquis();
                politicas = savePoliticas();
                documentacion = saveDocumentacion();

                if (credito && cliente && conyuge && economicos && negocio && aval && referencia && croquis && politicas && documentacion){

                    Update("estatus_completado", TBL_CREDITO_IND_REN, "1");
                    Update("estatus_completado", TBL_CLIENTE_IND_REN, "1");
                    Update("estatus_completado", TBL_CONYUGE_IND_REN, "1");
                    Update("estatus_completado", TBL_ECONOMICOS_IND_REN, "1");
                    Update("estatus_completado", TBL_NEGOCIO_IND_REN, "1");
                    Update("estatus_completado", TBL_AVAL_IND_REN, "1");
                    Update("estatus_completado", TBL_REFERENCIA_IND_REN, "1");
                    Update("estatus_completado", TBL_CROQUIS_IND_REN, "1");
                    Update("estatus_completado", TBL_POLITICAS_PLD_IND_REN, "1");
                    Update("estatus_completado", TBL_DOCUMENTOS_REN, "1");

                    Update("comentario_rechazo", TBL_CLIENTE_IND_REN, "");
                    Update("comentario_rechazo", TBL_NEGOCIO_IND_REN, "");
                    Update("comentario_rechazo", TBL_AVAL_IND_REN, "");
                    Update("comentario_rechazo", TBL_REFERENCIA_IND_REN, "");
                    Update("comentario_rechazo", TBL_CROQUIS_IND_REN, "");

                    ContentValues cv = new ContentValues();
                    cv.put("estatus", 1);
                    cv.put("fecha_termino", Miscellaneous.ObtenerFecha("timestamp"));

                    db.update(TBL_SOLICITUDES_REN, cv, "id_solicitud = ?" , new String[]{String.valueOf(id_solicitud)});

                    Servicios_Sincronizado ss = new Servicios_Sincronizado();
                    ss.SendRenovacionInd(ctx, false);
                    loading.dismiss();
                    Toast.makeText(ctx, "termina guardado", Toast.LENGTH_SHORT).show();

                    finish();

                }
                else {
                    loading.dismiss();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case REQUEST_CODE_ESTADO_NAC:
                if (resultCode == REQUEST_CODE_ESTADO_NAC){
                    if (data != null){
                        tvEstadoNacCli.setError(null);
                        tvEstadoNacCli.setText(((ModeloCatalogoGral)data.getSerializableExtra(ITEM)).getNombre());
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
                        etCurpCli.setText(Miscellaneous.GenerarCurp(params));
                        Update("estado_nacimiento", TBL_CLIENTE_IND_REN, tvEstadoNacCli.getText().toString().trim().toUpperCase());
                    }
                }
                break;
            case REQUEST_CODE_OCUPACION_CLIE:
                if (resultCode == REQUEST_CODE_OCUPACION_CLIE){
                    if (data != null){
                        tvOcupacionCli.setError(null);
                        tvOcupacionCli.setText(((ModeloCatalogoGral)data.getSerializableExtra(ITEM)).getNombre());
                        Cursor row = dBhelper.getRecords(SECTORES, " WHERE sector_id = " + (((ModeloCatalogoGral)data.getSerializableExtra(ITEM)).getExtra())+"","",null);
                        if (row.getCount() > 0){
                            row.moveToFirst();
                            tvActividadEcoCli.setText(row.getString(2));
                        }
                        row.close();
                        Update("ocupacion", TBL_CLIENTE_IND_REN, tvOcupacionCli.getText().toString().trim().toUpperCase());
                        Update("actividad_economica", TBL_CLIENTE_IND_REN, tvActividadEcoCli.getText().toString().trim().toUpperCase());
                    }
                }
                break;
            case REQUEST_CODE_OCUPACION_NEG:
                if (resultCode == REQUEST_CODE_OCUPACION_NEG){
                    if (data != null){
                        tvActEcoEspNeg.setError(null);
                        tvActEcoEspNeg.setText(((ModeloCatalogoGral)data.getSerializableExtra(ITEM)).getNombre());
                        Cursor row = dBhelper.getRecords(SECTORES, " WHERE sector_id = " + (((ModeloCatalogoGral)data.getSerializableExtra(ITEM)).getExtra())+"","",null);
                        if (row.getCount() > 0){
                            row.moveToFirst();
                            tvActEconomicaNeg.setText(row.getString(2));
                        }
                        row.close();
                        Update("ocupacion", TBL_NEGOCIO_IND_REN, tvActEcoEspNeg.getText().toString().trim().toUpperCase());
                        Update("actividad_economica", TBL_NEGOCIO_IND_REN, tvActEconomicaNeg.getText().toString().trim().toUpperCase());
                    }
                }
                break;
            case REQUEST_CODE_OCUPACION_CONY:
                if (resultCode == REQUEST_CODE_OCUPACION_CONY){
                    if (data != null){
                        tvOcupacionCony.setError(null);
                        tvOcupacionCony.setText(((ModeloCatalogoGral)data.getSerializableExtra(ITEM)).getNombre());
                        Update("ocupacion", TBL_CONYUGE_IND_REN, tvOcupacionCony.getText().toString().trim().toUpperCase());
                    }
                }
                break;
            case REQUEST_CODE_ACTIVIDAD_NEG:
                if (resultCode == REQUEST_CODE_ACTIVIDAD_NEG){
                    if (data != null){
                        tvActEconomicaNeg.setError(null);
                        tvActEconomicaNeg.setText(((ModeloCatalogoGral)data.getSerializableExtra(ITEM)).getNombre());
                        Update("actividad_economica", TBL_NEGOCIO_IND_REN, tvActEconomicaNeg.getText().toString().trim().toUpperCase());
                    }
                }
                break;
            case REQUEST_CODE_ESTADO_NAC_AVAL:
                if (resultCode == REQUEST_CODE_ESTADO_NAC_AVAL){
                    if (data != null){
                        tvEstadoNacAval.setError(null);
                        tvEstadoNacAval.setText(((ModeloCatalogoGral)data.getSerializableExtra(ITEM)).getNombre());
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
                        etCurpAval.setText(Miscellaneous.GenerarCurp(params));
                        Update("estado_nacimiento", TBL_AVAL_IND_REN, tvEstadoNacAval.getText().toString().trim().toUpperCase());
                    }
                }
                break;
            case REQUEST_CODE_OCUPACION_AVAL:
                if (resultCode == REQUEST_CODE_OCUPACION_AVAL){
                    if (data != null){
                        tvOcupacionAval.setError(null);
                        tvOcupacionAval.setText(((ModeloCatalogoGral)data.getSerializableExtra(ITEM)).getNombre());
                        Cursor row = dBhelper.getRecords(SECTORES, " WHERE sector_id = " + (((ModeloCatalogoGral)data.getSerializableExtra(ITEM)).getExtra())+"","",null);
                        if (row.getCount() > 0){
                            row.moveToFirst();
                            tvActividadEcoAval.setError(null);
                            tvActividadEcoAval.setText(row.getString(2));
                            Update("ocupacion", TBL_AVAL_IND_REN, tvOcupacionAval.getText().toString().trim().toUpperCase());
                            Update("actividad_economica", TBL_AVAL_IND_REN, tvActividadEcoAval.getText().toString().trim().toUpperCase());
                        }
                        row.close();
                    }
                }
                break;
            case REQUEST_CODE_COLONIA_CLIE:
                if (resultCode == REQUEST_CODE_COLONIA_CLIE){
                    if (data != null){
                        tvColoniaCli.setError(null);
                        tvColoniaCli.setText(((ModeloCatalogoGral)data.getSerializableExtra(ITEM)).getNombre());
                        UpdateDireccion("colonia", tvColoniaCli.getText().toString().trim().toUpperCase(), direccionIdCli, "CLIENTE");
                    }
                }
                break;
            case REQUEST_CODE_LOCALIDAD_CLIE:
                if (resultCode == REQUEST_CODE_LOCALIDAD_CLIE){
                    if (data != null){
                        tvLocalidadCli.setError(null);
                        tvLocalidadCli.setText(((ModeloCatalogoGral)data.getSerializableExtra(ITEM)).getNombre());
                        UpdateDireccion("localidad", tvLocalidadCli.getText().toString().trim().toUpperCase(), direccionIdCli, "CLIENTE");
                    }
                }
                break;
            case REQUEST_CODE_LOCALIDAD_CONY:
                if (resultCode == REQUEST_CODE_LOCALIDAD_CONY){
                    if (data != null){
                        tvLocalidadCony.setError(null);
                        tvLocalidadCony.setText(((ModeloCatalogoGral)data.getSerializableExtra(ITEM)).getNombre());
                        UpdateDireccion("localidad", tvLocalidadCony.getText().toString().trim().toUpperCase(), direccionIdCony, "CONYUGE");
                    }
                }
                break;
            case REQUEST_CODE_LOCALIDAD_NEG:
                if (resultCode == REQUEST_CODE_LOCALIDAD_NEG){
                    if (data != null){
                        tvLocalidadNeg.setError(null);
                        tvLocalidadNeg.setText(((ModeloCatalogoGral)data.getSerializableExtra(ITEM)).getNombre());
                        UpdateDireccion("localidad", tvLocalidadNeg.getText().toString().trim().toUpperCase(), direccionIdNeg, "NEGOCIO");
                    }
                }
                break;
            case REQUEST_CODE_LOCALIDAD_AVAL:
                if (resultCode == REQUEST_CODE_LOCALIDAD_AVAL){
                    if (data != null){
                        tvLocalidadAval.setError(null);
                        tvLocalidadAval.setText(((ModeloCatalogoGral)data.getSerializableExtra(ITEM)).getNombre());
                        UpdateDireccion("localidad", tvLocalidadAval.getText().toString().trim().toUpperCase(), direccionIdAval, "AVAL");
                    }
                }
                break;
            case REQUEST_CODE_LOCALIDAD_REF:
                if (resultCode == REQUEST_CODE_LOCALIDAD_REF){
                    if (data != null){
                        tvLocalidadRef.setError(null);
                        tvLocalidadRef.setText(((ModeloCatalogoGral)data.getSerializableExtra(ITEM)).getNombre());
                        UpdateDireccion("localidad", tvLocalidadRef.getText().toString().trim().toUpperCase(), direccionIdRef, "REFERENCIA");
                    }
                }
                break;
            case REQUEST_CODE_COLONIA_CONY:
                if (resultCode == REQUEST_CODE_COLONIA_CONY){
                    if (data != null){
                        tvColoniaCony.setError(null);
                        tvColoniaCony.setText(((ModeloCatalogoGral)data.getSerializableExtra(ITEM)).getNombre());
                        UpdateDireccion("colonia", tvColoniaCony.getText().toString().trim().toUpperCase(), direccionIdCony, "CONYUGE");
                    }
                }
                break;
            case REQUEST_CODE_COLONIA_AVAL:
                if (resultCode == REQUEST_CODE_COLONIA_AVAL){
                    if (data != null){
                        tvColoniaAval.setText(((ModeloCatalogoGral)data.getSerializableExtra(ITEM)).getNombre());
                        UpdateDireccion("colonia", tvColoniaAval.getText().toString().trim().toUpperCase(), direccionIdAval, "AVAL");
                    }
                }
                break;
            case REQUEST_CODE_COLONIA_NEG:
                if (resultCode == REQUEST_CODE_COLONIA_NEG){
                    if (data != null){
                        tvColoniaNeg.setError(null);
                        tvColoniaNeg.setText(((ModeloCatalogoGral)data.getSerializableExtra(ITEM)).getNombre());
                        UpdateDireccion("colonia", tvColoniaNeg.getText().toString().trim().toUpperCase(), direccionIdNeg, "NEGOCIO");
                    }
                }
                break;
            case REQUEST_CODE_COLONIA_REF:
                if (resultCode == REQUEST_CODE_COLONIA_REF){
                    if (data != null){
                        tvColoniaRef.setError(null);
                        tvColoniaRef.setText(((ModeloCatalogoGral)data.getSerializableExtra(ITEM)).getNombre());
                        UpdateDireccion("colonia", tvColoniaRef.getText().toString().trim().toUpperCase(), direccionIdRef, "REFERENCIA");
                    }
                }
                break;
            case REQUEST_CODE_CAMARA_FACHADA_CLI:
                if (resultCode == Activity.RESULT_OK){
                    if (data != null){
                        tvFachadaCli.setError(null);
                        ibFotoFachCli.setVisibility(View.GONE);
                        ivFotoFachCli.setVisibility(View.VISIBLE);
                        byteFotoFachCli = data.getByteArrayExtra(PICTURE);
                        Glide.with(ctx).load(byteFotoFachCli).centerCrop().into(ivFotoFachCli);
                        try {
                            Update("foto_fachada", TBL_CLIENTE_IND_REN, Miscellaneous.save(byteFotoFachCli, 1));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                break;
            case REQUEST_CODE_CAMARA_FACHADA_NEG:
                if (resultCode == Activity.RESULT_OK){
                    if (data != null){
                        tvFachadaNeg.setError(null);
                        ibFotoFachNeg.setVisibility(View.GONE);
                        ivFotoFachNeg.setVisibility(View.VISIBLE);
                        byteFotoFachNeg = data.getByteArrayExtra(PICTURE);
                        Glide.with(ctx).load(byteFotoFachNeg).centerCrop().into(ivFotoFachNeg);
                        try {
                            Update("foto_fachada", TBL_NEGOCIO_IND_REN, Miscellaneous.save(byteFotoFachNeg, 1));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                break;
            case REQUEST_CODE_CAMARA_FACHADA_AVAL:
                if (resultCode == Activity.RESULT_OK){
                    if (data != null){
                        tvFachadaAval.setError(null);
                        ibFotoFachAval.setVisibility(View.GONE);
                        ivFotoFachAval.setVisibility(View.VISIBLE);
                        byteFotoFachAval = data.getByteArrayExtra(PICTURE);
                        Glide.with(ctx).load(byteFotoFachAval).centerCrop().into(ivFotoFachAval);
                        try {
                            Update("foto_fachada", TBL_AVAL_IND_REN, Miscellaneous.save(byteFotoFachAval, 1));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                break;
            case REQUEST_CODE_FIRMA_AVAL:
                if (resultCode == Activity.RESULT_OK){
                    if (data != null){
                        tvFirmaAval.setError(null);
                        ibFirmaAval.setVisibility(View.GONE);
                        ivFirmaAval.setVisibility(View.VISIBLE);
                        Glide.with(ctx)
                                .load(data.getByteArrayExtra(FIRMA_IMAGE))
                                .into(ivFirmaAval);
                        byteFirmaAval = data.getByteArrayExtra(FIRMA_IMAGE);
                        try {
                            Update("firma", TBL_AVAL_IND_REN, Miscellaneous.save(byteFirmaAval, 3));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                break;
            case REQUEST_CODE_FIRMA_CLI:
                if (resultCode == Activity.RESULT_OK){
                    if (data != null){
                        tvFirmaCli.setError(null);
                        ibFirmaCli.setVisibility(View.GONE);
                        ivFirmaCli.setVisibility(View.VISIBLE);
                        Glide.with(ctx)
                                .load(data.getByteArrayExtra(FIRMA_IMAGE))
                                .into(ivFirmaCli);
                        byteFirmaCli = data.getByteArrayExtra(FIRMA_IMAGE);
                        try {
                            Update("firma", TBL_CLIENTE_IND_REN, Miscellaneous.save(byteFirmaCli, 3));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                break;
            case REQUEST_CODE_FIRMA_ASESOR:
                if (resultCode == Activity.RESULT_OK){
                    if (data != null){
                        tvFirmaAsesor.setError(null);
                        ibFirmaAsesor.setVisibility(View.GONE);
                        ivFirmaAsesor.setVisibility(View.VISIBLE);
                        Glide.with(ctx)
                                .load(data.getByteArrayExtra(FIRMA_IMAGE))
                                .into(ivFirmaAsesor);
                        byteFirmaAsesor = data.getByteArrayExtra(FIRMA_IMAGE);
                        try {
                            Update("firma_asesor", TBL_DOCUMENTOS_REN, Miscellaneous.save(byteFirmaAsesor, 3));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                break;
            case REQUEST_CODE_FOTO_INE_FRONTAL:
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
                        try {
                            Update("ine_frontal", TBL_DOCUMENTOS_REN, Miscellaneous.save(byteIneFrontal, 4));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                break;
            case REQUEST_CODE_FOTO_INE_REVERSO:
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
                        try {
                            Update("ine_reverso", TBL_DOCUMENTOS_REN, Miscellaneous.save(byteIneReverso, 4));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                break;
            case REQUEST_CODE_FOTO_CURP:
                if (resultCode == Activity.RESULT_OK){
                    if (data != null){
                        /*tvCurp.setError(null);
                        ibCurp.setVisibility(View.GONE);
                        ivCurp.setVisibility(View.VISIBLE);
                        byteCurp = data.getByteArrayExtra(PICTURE);
                        Glide.with(ctx).load(byteCurp).centerCrop().into(ivCurp);*/
                        /*try {
                            Update("curp", TBL_DOCUMENTOS_REN, Miscellaneous.save(byteCurp, 4));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }*/
                    }
                }
                break;
            case REQUEST_CODE_FOTO_COMPROBATE:
                if (resultCode == Activity.RESULT_OK){
                    if (data != null){
                        tvComprobante.setError(null);
                        ibComprobante.setVisibility(View.GONE);
                        ivComprobante.setVisibility(View.VISIBLE);
                        byteComprobante = data.getByteArrayExtra(PICTURE);
                        Glide.with(ctx).load(byteComprobante).centerCrop().into(ivComprobante);
                        try {
                            Update("comprobante", TBL_DOCUMENTOS_REN, Miscellaneous.save(byteComprobante, 4));
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

                    Update("fecha_desembolso", TBL_CREDITO_IND_REN, tvFechaDesembolso.getText().toString().trim());
                    Update("dia_desembolso", TBL_CREDITO_IND_REN, tvDiaDesembolso.getText().toString().trim());
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
                    Update("fecha_nacimiento", TBL_CLIENTE_IND_REN, tvFechaNacCli.getText().toString().trim());
                    Update("edad", TBL_CLIENTE_IND_REN, tvEdadCli.getText().toString().trim());
                    etCurpCli.setText(Miscellaneous.GenerarCurp(params));
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

                    Update("fecha_nacimiento", TBL_AVAL_IND_REN, tvFechaNacAval.getText().toString().trim());
                    Update("edad", TBL_AVAL_IND_REN, tvEdadAval.getText().toString().trim());
                    etCurpAval.setText(Miscellaneous.GenerarCurp(paramsAval));
                    break;
                case "fechaNacRef":
                    tvFechaNacRef.setError(null);
                    tvFechaNacRef.setText(date);
                    Update("fecha_nacimiento", TBL_REFERENCIA_IND_REN, tvFechaNacRef.getText().toString().trim());
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
                Update("hora_visita", TBL_CREDITO_IND_REN, tvHoraVisita.getText().toString().trim());
                break;
            case "HoraVisitaAval":
                tvHoraLocAval.setError(null);
                tvHoraLocAval.setText(timer);
                Update("horario_localizacion", TBL_AVAL_IND_REN, timer);
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
                    UpdateDireccion("latitud", latitud, direccionIdCli, "CLIENTE");
                    UpdateDireccion("longitud", longitud, direccionIdCli, "CLIENTE");
                    Ubicacion(Double.parseDouble(latitud), Double.parseDouble(longitud));

                }
                else{
                    latLngUbiCli = new LatLng(0,0);
                    UpdateDireccion("latitud", "", direccionIdCli, "CLIENTE");
                    UpdateDireccion("longitud", "", direccionIdCli, "CLIENTE");
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
                latLngUbiCli = new LatLng(0,0);
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
                    UpdateDireccion("latitud", latitud, direccionIdNeg, "NEGOCIO");
                    UpdateDireccion("longitud", longitud, direccionIdNeg, "NEGOCIO");
                    UbicacionNeg(Double.parseDouble(latitud), Double.parseDouble(longitud));
                }
                else{
                    latLngUbiNeg = new LatLng(0,0);
                    UpdateDireccion("latitud", "", direccionIdNeg, "NEGOCIO");
                    UpdateDireccion("longitud", "", direccionIdNeg, "NEGOCIO");
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
                latLngUbiNeg = new LatLng(0,0);
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
                    UpdateDireccion("latitud", latitud, direccionIdAval, "AVAL");
                    UpdateDireccion("longitud", longitud, direccionIdAval, "AVAL");
                    UbicacionAval(Double.parseDouble(latitud), Double.parseDouble(longitud));
                }
                else{
                    latLngUbiAval = new LatLng(0,0);
                    UpdateDireccion("latitud", "", direccionIdAval, "AVAL");
                    UpdateDireccion("longitud", "", direccionIdAval, "AVAL");
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
                latLngUbiAval = new LatLng(0,0);
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
    //========================================================================================================

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

        if (tipo.equals("NEGOCIO"))
            etCapacidadPagoNeg.setText("");
        else if(tipo.equals("AVAL"))
            etCapacidadPagoAval.setText("");
    }

    private void Update(String key, String tabla, String value) {
        Log.e("update", key+": "+value);
        ContentValues cv = new ContentValues();
        cv.put(key, value);
        db.update(tabla, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});

    }

    private void UpdateDireccion(String key, String value, String direccion_id, String tipo) {
        Log.e("update", key+": "+value);
        ContentValues cv = new ContentValues();
        cv.put(key, value);
        db.update(TBL_DIRECCIONES_REN, cv, "id_direccion = ? AND tipo_direccion = ?", new String[]{direccion_id, tipo});

    }

    public void setCalle (String calle, String tipo){
        switch (tipo){
            case "PRINCIPAL":
                tvPrincipal.setError(null);
                tvPrincipal.setText(calle);
                Update("calle_principal",TBL_CROQUIS_IND_REN, calle.trim().toUpperCase());
                break;
            case "TRASERA":
                tvTrasera.setError(null);
                tvTrasera.setText(calle);
                Update("calle_trasera",TBL_CROQUIS_IND_REN, calle.trim().toUpperCase());
                break;
            case "LATERAL UNO":
                tvLateraUno.setError(null);
                tvLateraUno.setText(calle);
                Update("lateral_uno",TBL_CROQUIS_IND_REN, calle.trim().toUpperCase());
                break;
            case "LATERAL DOS":
                tvLateraDos.setError(null);
                tvLateraDos.setText(calle);
                Update("lateral_dos",TBL_CROQUIS_IND_REN, calle.trim().toUpperCase());
                break;
        }
    }

    private void MontoMaximoNeg (){
        double ing_mensual = (etIngMenNeg.getText().toString().trim().isEmpty())?0:Integer.parseInt(etIngMenNeg.getText().toString().trim().replace(",",""));
        double ing_otros = (etOtrosIngNeg.getText().toString().trim().isEmpty())?0:Integer.parseInt(etOtrosIngNeg.getText().toString().trim().replace(",",""));

        double gas_mensual = (etGastosMenNeg.getText().toString().trim().isEmpty())?0:Integer.parseInt(etGastosMenNeg.getText().toString().trim().replace(",",""));
        double gas_agua = (etGastosAguaNeg.getText().toString().trim().isEmpty())?0:Integer.parseInt(etGastosAguaNeg.getText().toString().trim().replace(",",""));
        double gas_luz = (etGastosLuzNeg.getText().toString().trim().isEmpty())?0:Integer.parseInt(etGastosLuzNeg.getText().toString().trim().replace(",",""));
        double gas_telefono = (etGastosTelNeg.getText().toString().trim().isEmpty())?0:Integer.parseInt(etGastosTelNeg.getText().toString().trim().replace(",",""));
        double gas_renta = (etGastosRentaNeg.getText().toString().trim().isEmpty())?0:Integer.parseInt(etGastosRentaNeg.getText().toString().trim().replace(",",""));
        double gas_otro = (etGastosOtrosNeg.getText().toString().trim().isEmpty())?0:Integer.parseInt(etGastosOtrosNeg.getText().toString().trim().replace(",",""));

        double ingreso = ing_mensual + ing_otros;
        double gastos = gas_mensual + gas_agua + gas_luz + gas_telefono + gas_renta + gas_otro;

        tvMontoMaxNeg.setText(dfnd.format(ingreso - gastos));
        Update("monto_maximo", TBL_NEGOCIO_IND_REN, tvMontoMaxNeg.getText().toString().trim().replace(",",""));

        tvIngresoEco.setText(dfnd.format(ingreso));
        Update("ingreso", TBL_ECONOMICOS_IND_REN, tvIngresoEco.getText().toString().trim().replace(",",""));

    }

    private void MontoMaximoAval (){
        double ing_mensual = (etIngMenAval.getText().toString().trim().isEmpty())?0:Integer.parseInt(etIngMenAval.getText().toString().trim().replace(",",""));
        double ing_otros = (etIngOtrosAval.getText().toString().trim().isEmpty())?0:Integer.parseInt(etIngOtrosAval.getText().toString().trim().replace(",",""));

        double gas_mensual = (etGastosSemAval.getText().toString().trim().isEmpty())?0:Integer.parseInt(etGastosSemAval.getText().toString().trim().replace(",",""));
        double gas_agua = (etGastosAguaAval.getText().toString().trim().isEmpty())?0:Integer.parseInt(etGastosAguaAval.getText().toString().trim().replace(",",""));
        double gas_luz = (etGastosLuzAval.getText().toString().trim().isEmpty())?0:Integer.parseInt(etGastosLuzAval.getText().toString().trim().replace(",",""));
        double gas_telefono = (etGastosTelAval.getText().toString().trim().isEmpty())?0:Integer.parseInt(etGastosTelAval.getText().toString().trim().replace(",",""));
        double gas_renta = (etGastosRentaAval.getText().toString().trim().isEmpty())?0:Integer.parseInt(etGastosRentaAval.getText().toString().trim().replace(",",""));
        double gas_otro = (etGastosOtrosAval.getText().toString().trim().isEmpty())?0:Integer.parseInt(etGastosOtrosAval.getText().toString().trim().replace(",",""));

        double ingreso = ing_mensual + ing_otros;
        double gastos = gas_mensual + gas_agua + gas_luz + gas_telefono + gas_renta + gas_otro;

        tvMontoMaxAval.setText(dfnd.format(ingreso - gastos));
        Update("monto_maximo", TBL_AVAL_IND_REN, tvMontoMaxAval.getText().toString().trim().replace(",",""));
    }

    private void showDiasSemana(){
        selectedItemsDias = new ArrayList<>();
        new AlertDialog.Builder(RenovacionCreditoInd.this)
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
                        Update("dias_venta", TBL_NEGOCIO_IND_REN, tvDiasVentaNeg.getText().toString().trim().toUpperCase());
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                    }
                }).show();
    }

    private void initComponents (String idSolicitud) {

        Cursor row = dBhelper.getRecords(TBL_SOLICITUDES_REN, " WHERE id_solicitud = ?", "", new String[]{idSolicitud});
        row.moveToFirst();

        Log.e("Rows", row.getColumnCount() + "count");
        is_edit = row.getInt(11) == 0;

        row.close();

        if (!is_edit)
            invalidateOptionsMenu();

        //id_cliente = row.getString(11);

        //Update("id_originacion", TBL_SOLICITUDES_REN, "22");

        row = dBhelper.getRecords(TBL_CREDITO_IND_REN, " WHERE id_solicitud = ?", "", new String[]{idSolicitud});
        row.moveToFirst();
        isEditCre = row.getInt(15) == 0;
        //Llenado del datos del prestamo
        tvPlazo.setText(row.getString(2).toUpperCase());
        tvFrecuencia.setText(row.getString(3).toUpperCase());
        tvFechaDesembolso.setText(row.getString(4));
        tvDiaDesembolso.setText(row.getString(5));
        tvHoraVisita.setText(row.getString(6));
        if (!row.getString(7).trim().isEmpty())
            etMontoPrestamo.setText(dfnd.format(row.getInt(7)));
        etMontoPrestamo.setEnabled(isEditCre);
        if (!row.getString(7).trim().isEmpty()) {
            tvCantidadLetra.setText((Miscellaneous.cantidadLetra(row.getString(7)).toUpperCase() + " PESOS MEXICANOS").replace("  ", " "));
            if (row.getInt(7) >= 30000)
                llPropiedades.setVisibility(View.VISIBLE);
        }
        tvDestino.setText(row.getString(13));
        tvRiesgo.setText(row.getString(14));

        tvCreditoAnterior.setText(dfnd.format(row.getInt(9)));
        tvNumCiclo.setText(row.getString(8));
        tvComportamiento.setText(row.getString(10));
        etObservaciones.setText(row.getString(12));
        etObservaciones.setEnabled(isEditCre);

        row.close(); //Cierra dato del credito

        //Llenado de datos del cliente
        row = dBhelper.getRecords(TBL_CLIENTE_IND_REN, " WHERE id_solicitud = ?", "", new String[]{idSolicitud});
        row.moveToFirst();
        if (!row.getString(36).trim().isEmpty()){
            llComentCli.setVisibility(View.VISIBLE);
            tvComentAdminCli.setText(row.getString(36).trim().toUpperCase());
        }
        isEditCli = row.getInt(37) == 0;
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
        etNumIdentifCli.setEnabled(isEditCli);
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
        Cursor rowDir = dBhelper.getRecords(TBL_DIRECCIONES_REN, " WHERE id_direccion = ? AND tipo_direccion = ?", "", new String[]{row.getString(22), "CLIENTE"});
        rowDir.moveToFirst();
        direccionIdCli = rowDir.getString(0);
        if (!rowDir.getString(2).isEmpty() && !rowDir.getString(3).isEmpty()) {
            mapCli.setVisibility(View.VISIBLE);
            Ubicacion(rowDir.getDouble(2), rowDir.getDouble(3));
        }
        etCalleCli.setText(rowDir.getString(4));
        etCalleCli.setEnabled(isEditCli);
        etNoExtCli.setText(rowDir.getString(5));
        etNoExtCli.setEnabled(isEditCli);
        etNoIntCli.setText(rowDir.getString(6));
        etNoIntCli.setEnabled(isEditCli);
        etLoteCli.setText(rowDir.getString(7));
        etLoteCli.setEnabled(isEditCli);
        etManzanaCli.setText(rowDir.getString(8));
        etManzanaCli.setEnabled(isEditCli);
        etCpCli.setText(rowDir.getString(9));
        etCpCli.setEnabled(isEditCli);
        tvColoniaCli.setText(rowDir.getString(10));
        etCiudadCli.setText(rowDir.getString(11));
        etCiudadCli.setEnabled(isEditCli);
        tvLocalidadCli.setText(rowDir.getString(12));
        tvMunicipioCli.setText(rowDir.getString(13));
        tvEstadoCli.setText(rowDir.getString(14));
        rowDir.close(); //Cierra cursor de direccion del cliente
        etTelCasaCli.setText(row.getString(23));
        etTelCasaCli.setEnabled(isEditCli);
        etCelularCli.setText(row.getString(24));
        etCelularCli.setEnabled(isEditCli);
        etTelMensCli.setText(row.getString(25));
        etTelMensCli.setEnabled(isEditCli);
        etTelTrabajoCli.setText(row.getString(26));
        etTelTrabajoCli.setEnabled(isEditCli);
        etTiempoSitio.setText((row.getInt(27) > 0) ? row.getString(27) : "");
        etTiempoSitio.setEnabled(isEditCli);
        tvDependientes.setText(row.getString(28));
        tvEnteroNosotros.setText(row.getString(29));
        tvEstadoCuenta.setText(row.getString(30));
        etEmail.setText(row.getString(31));
        etEmail.setEnabled(isEditCli);
        if (!row.getString(32).isEmpty()) {
            File fachadaFile = new File(ROOT_PATH + "Fachada/" + row.getString(32));
            Uri uriFachada = Uri.fromFile(fachadaFile);
            byteFotoFachCli = Miscellaneous.getBytesUri(ctx, uriFachada, 0);
            Glide.with(ctx).load(uriFachada).into(ivFotoFachCli);
            ibFotoFachCli.setVisibility(View.GONE);
            ivFotoFachCli.setVisibility(View.VISIBLE);
        }
        etReferenciCli.setText(row.getString(33));
        etReferenciCli.setEnabled(isEditCli);
        if (!row.getString(34).isEmpty()) {
            File firmaFile = new File(ROOT_PATH + "Firma/" + row.getString(34));
            Uri uriFirma = Uri.fromFile(firmaFile);
            byteFirmaCli = Miscellaneous.getBytesUri(ctx, uriFirma, 0);
            Glide.with(ctx).load(uriFirma).into(ivFirmaCli);
            ibFirmaCli.setVisibility(View.GONE);
            ivFirmaCli.setVisibility(View.VISIBLE);
        }
        row.close(); // Cierra datos del cliente

        // Llenado de datos del conyuge
        row = dBhelper.getRecords(TBL_CONYUGE_IND_REN, " WHERE id_solicitud = ?", "", new String[]{idSolicitud});
        row.moveToFirst();
        isEditCon = row.getInt(12) == 0;
        etNombreCony.setText(row.getString(2));
        etNombreCony.setEnabled(isEditCon);
        etApPaternoCony.setText(row.getString(3));
        etApPaternoCony.setEnabled(isEditCon);
        etApMaternoCony.setText(row.getString(4));
        etApMaternoCony.setEnabled(isEditCon);
        etNacionalidadCony.setText(row.getString(5));
        etNacionalidadCony.setEnabled(isEditCon);
        tvOcupacionCony.setText(row.getString(6));
        rowDir = dBhelper.getRecords(TBL_DIRECCIONES_REN, " WHERE id_direccion = ? AND tipo_direccion = ?", "", new String[]{row.getString(7), "CONYUGE"});
        rowDir.moveToFirst();
        direccionIdCony = rowDir.getString(0);
        etCalleCony.setText(rowDir.getString(4));
        etCalleCony.setEnabled(isEditCon);
        etNoExtCony.setText(rowDir.getString(5));
        etNoExtCony.setEnabled(isEditCon);
        etNoIntCony.setText(rowDir.getString(6));
        etNoIntCony.setEnabled(isEditCon);
        etLoteCony.setText(rowDir.getString(7));
        etLoteCony.setEnabled(isEditCon);
        etManzanaCony.setText(rowDir.getString(8));
        etManzanaCony.setEnabled(isEditCon);
        etCpCony.setText(rowDir.getString(9));
        etCpCony.setEnabled(isEditCon);
        tvColoniaCony.setText(rowDir.getString(10));
        etCiudadCony.setText(rowDir.getString(11));
        etCiudadCony.setEnabled(isEditCon);
        tvLocalidadCony.setText(rowDir.getString(12));
        tvMunicipioCony.setText(rowDir.getString(13));
        tvEstadoCony.setText(rowDir.getString(14));
        rowDir.close(); //Cierra datos de direccion del conyuge
        if (!row.getString(8).trim().isEmpty())
            etIngMenCony.setText(dfnd.format(row.getInt(8)));
        etIngMenCony.setEnabled(isEditCon);
        if (!row.getString(9).trim().isEmpty())
            etGastoMenCony.setText(dfnd.format(row.getInt(9)));
        etGastoMenCony.setEnabled(isEditCon);
        etCasaCony.setText(row.getString(10));
        etCasaCony.setEnabled(isEditCon);
        etCelularCony.setText(row.getString(11));
        etCelularCony.setEnabled(isEditCon);
        row.close(); //Cierra datos del conyuge

        //Llenado de datos economicos
        row = dBhelper.getRecords(TBL_ECONOMICOS_IND_REN, " WHERE id_solicitud = ?", "", new String[]{idSolicitud});
        row.moveToFirst();
        isEditEco = row.getInt(6) == 0;
        etPropiedadesEco.setText(row.getString(2));
        etPropiedadesEco.setEnabled(isEditEco);
        etValorAproxEco.setText(row.getString(3));
        etValorAproxEco.setEnabled(isEditEco);
        etUbicacionEco.setText(row.getString(4));
        etUbicacionEco.setEnabled(isEditEco);
        tvIngresoEco.setText(row.getString(5));
        row.close(); // Cierra datos economicos

        //Llenado de datos del negocio
        row = dBhelper.getRecords(TBL_NEGOCIO_IND_REN, " WHERE id_solicitud = ?", "", new String[]{idSolicitud});
        row.moveToFirst();
        if (!row.getString(27).trim().isEmpty()){
            llComentNeg.setVisibility(View.VISIBLE);
            tvComentAdminNeg.setText(row.getString(27).trim().toUpperCase());
        }

        isEditNeg = row.getInt(26) == 0;
        etNombreNeg.setText(row.getString(2));
        etNombreNeg.setEnabled(isEditNeg);

        rowDir = dBhelper.getRecords(TBL_DIRECCIONES_REN, " WHERE id_direccion = ? AND tipo_direccion = ?", "", new String[]{row.getString(3), "NEGOCIO"});
        rowDir.moveToFirst();
        direccionIdNeg = rowDir.getString(0);
        if (!rowDir.getString(2).isEmpty() && !rowDir.getString(3).isEmpty()) {
            mapNeg.setVisibility(View.VISIBLE);
            UbicacionNeg(rowDir.getDouble(2), rowDir.getDouble(3));
        }
        etCalleNeg.setText(rowDir.getString(4));
        etCalleNeg.setEnabled(isEditNeg);
        etNoExtNeg.setText(rowDir.getString(5));
        etNoExtNeg.setEnabled(isEditNeg);
        etNoIntNeg.setText(rowDir.getString(6));
        etNoIntNeg.setEnabled(isEditNeg);
        etLoteNeg.setText(rowDir.getString(7));
        etLoteNeg.setEnabled(isEditNeg);
        etManzanaNeg.setText(rowDir.getString(8));
        etManzanaNeg.setEnabled(isEditNeg);
        etCpNeg.setText(rowDir.getString(9));
        etCpNeg.setEnabled(isEditNeg);
        tvColoniaNeg.setText(rowDir.getString(10));
        etCiudadNeg.setText(rowDir.getString(11));
        etCiudadNeg.setEnabled(isEditNeg);
        tvLocalidadNeg.setText(rowDir.getString(12));
        tvMunicipioNeg.setText(rowDir.getString(13));
        rowDir.close(); //Cierra datos de direccion del negocio
        tvActEcoEspNeg.setText(row.getString(4));
        tvActEconomicaNeg.setText(row.getString(5));
        tvDestinoNeg.setText(row.getString(6));
        if (row.getString(6).equals("OTRO")) {
            etOtroDestinoNeg.setText(row.getString(7));
            etOtroDestinoNeg.setEnabled(isEditNeg);
            etOtroDestinoNeg.setVisibility(View.VISIBLE);
        }

        if (row.getInt(8) > 0)
            etAntiguedadNeg.setText(row.getString(8));
        etAntiguedadNeg.setEnabled(isEditNeg);

        if (!row.getString(9).trim().isEmpty())
            etIngMenNeg.setText(dfnd.format(row.getInt(9)));
        etIngMenNeg.setEnabled(isEditNeg);
        if (!row.getString(10).trim().isEmpty())
            etOtrosIngNeg.setText(dfnd.format(row.getInt(10)));
        etOtrosIngNeg.setEnabled(isEditNeg);
        if (!row.getString(11).trim().isEmpty())
            etGastosMenNeg.setText(dfnd.format(row.getInt(11)));
        etGastosMenNeg.setEnabled(isEditNeg);
        if (!row.getString(12).trim().isEmpty())
            etGastosAguaNeg.setText(dfnd.format(row.getInt(12)));
        etGastosAguaNeg.setEnabled(isEditNeg);
        if (!row.getString(13).trim().isEmpty())
            etGastosLuzNeg.setText(dfnd.format(row.getInt(13)));
        etGastosLuzNeg.setEnabled(isEditNeg);
        if (!row.getString(14).trim().isEmpty())
            etGastosTelNeg.setText(dfnd.format(row.getInt(14)));
        etGastosTelNeg.setEnabled(isEditNeg);
        if (!row.getString(15).trim().isEmpty())
            etGastosRentaNeg.setText(dfnd.format(row.getInt(15)));
        etGastosRentaNeg.setEnabled(isEditNeg);
        if (!row.getString(16).trim().isEmpty())
            etGastosOtrosNeg.setText(dfnd.format(row.getInt(16)));
        etGastosOtrosNeg.setEnabled(isEditNeg);
        if (!row.getString(17).trim().isEmpty())
            etCapacidadPagoNeg.setText(dfnd.format(row.getInt(17)));
        etCapacidadPagoNeg.setEnabled(isEditNeg);
        tvMediosPagoNeg.setText(row.getString(18));
        if (row.getString(18).contains("OTRO")) {
            etOtroMedioPagoNeg.setText(row.getString(19));
            etOtroMedioPagoNeg.setEnabled(isEditNeg);
            etOtroMedioPagoNeg.setVisibility(View.VISIBLE);
        }

        if (tvMediosPagoNeg.getText().toString().trim().toUpperCase().contains("EFECTIVO"))
            llOperacionesEfectivo.setVisibility(View.VISIBLE);
        else
            llOperacionesEfectivo.setVisibility(View.GONE);

        if (!row.getString(20).trim().isEmpty())
            tvMontoMaxNeg.setText(dfnd.format(row.getDouble(20)));
        tvMontoMaxNeg.setEnabled(isEditNeg);
        tvNumOperacionNeg.setText((row.getString(21).isEmpty()) ? "" : row.getString(21));
        etNumOperacionEfectNeg.setText((row.getString(22).isEmpty()) ? "" : row.getString(22));
        etNumOperacionEfectNeg.setEnabled(isEditNeg);
        tvDiasVentaNeg.setText(row.getString(23));

        if (!row.getString(24).isEmpty()) {
            File fachadaFile = new File(ROOT_PATH + "Fachada/" + row.getString(24));
            Uri uriFachada = Uri.fromFile(fachadaFile);
            byteFotoFachNeg = Miscellaneous.getBytesUri(ctx, uriFachada, 0);
            Glide.with(ctx).load(uriFachada).into(ivFotoFachNeg);
            ibFotoFachNeg.setVisibility(View.GONE);
            ivFotoFachNeg.setVisibility(View.VISIBLE);
        }
        etReferenciNeg.setText(row.getString(25));
        etReferenciNeg.setEnabled(isEditNeg);

        row.close(); // Cierra los datos del negocio


        //Llenado de datos del aval
        row = dBhelper.getRecords(TBL_AVAL_IND_REN, " WHERE id_solicitud = ?", "", new String[]{idSolicitud});
        row.moveToFirst();
        if (!row.getString(50).trim().isEmpty()){
            llComentAval.setVisibility(View.VISIBLE);
            tvComentAdminAval.setText(row.getString(50).trim().toUpperCase());
        }
        isEditAva = row.getInt(51) == 0;
        etNombreAval.setText(row.getString(2));
        etNombreAval.setEnabled(isEditAva);
        etApPaternoAval.setText(row.getString(3));
        etApPaternoAval.setEnabled(isEditAva);
        etApMaternoAval.setText(row.getString(4));
        etApMaternoAval.setEnabled(isEditAva);
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
        etCurpAval.setEnabled(isEditAva);
        //etCurpIdAval.setText(row.getString(11)); etCurpIdAval.setEnabled(is_edit);
        tvParentescoAval.setText(row.getString(12));
        tvTipoIdentificacionAval.setText(row.getString(13));
        etNumIdentifAval.setText(row.getString(14));
        etNumIdentifAval.setEnabled(isEditAva);
        tvOcupacionAval.setText(row.getString(15));
        tvActividadEcoAval.setText(row.getString(16));

        rowDir = dBhelper.getRecords(TBL_DIRECCIONES_REN, " WHERE id_direccion = ? AND tipo_direccion = ?", "", new String[]{row.getString(19), "AVAL"});
        rowDir.moveToFirst();
        direccionIdAval = rowDir.getString(0);
        if (!rowDir.getString(2).isEmpty() && !rowDir.getString(3).isEmpty()) {
            mapAval.setVisibility(View.VISIBLE);
            UbicacionAval(rowDir.getDouble(2), rowDir.getDouble(3));
        }
        etCalleAval.setText(rowDir.getString(4));
        etCalleAval.setEnabled(isEditAva);
        etNoExtAval.setText(rowDir.getString(5));
        etNoExtAval.setEnabled(isEditAva);
        etNoIntAval.setText(rowDir.getString(6));
        etNoIntAval.setEnabled(isEditAva);
        etLoteAval.setText(rowDir.getString(7));
        etLoteAval.setEnabled(isEditAva);
        etManzanaAval.setText(rowDir.getString(8));
        etManzanaAval.setEnabled(isEditAva);
        etCpAval.setText(rowDir.getString(9));
        etCpAval.setEnabled(isEditAva);
        tvColoniaAval.setText(rowDir.getString(10));
        etCiudadAval.setText(rowDir.getString(11));
        etCiudadAval.setEnabled(isEditAva);
        tvLocalidadAval.setText(rowDir.getString(12));
        tvMunicipioAval.setText(rowDir.getString(13));
        tvEstadoAval.setText(rowDir.getString(14));
        rowDir.close(); // Cierra los datos de la direccion del aval

        tvTipoCasaAval.setText(row.getString(20));

        if (row.getString(20).trim().toUpperCase().equals("CASA FAMILIAR") || row.getString(20).trim().toUpperCase().equals("CASA RENTADA")) {
            llParentescoFamAval.setVisibility(View.VISIBLE);
            llNombreTitular.setVisibility(View.VISIBLE);
            etNombreTitularAval.setText(row.getString(21));
            etNombreTitularAval.setEnabled(isEditAva);
            tvFamiliarAval.setText(row.getString(22));
        }
        etCaracteristicasAval.setText(row.getString(23));
        etCaracteristicasAval.setEnabled(isEditAva);

        switch (row.getInt(25)) {
            case 1:
                rgNegocioAval.check(R.id.rbSiNeg);
                llNombreNegocio.setVisibility(View.VISIBLE);
                etNombreNegocioAval.setText(row.getString(26));
                etNombreNegocioAval.setEnabled(isEditAva);
                if (row.getInt(24) > 0)
                    etAntiguedadAval.setText(row.getString(24));
                etAntiguedadAval.setEnabled(isEditAva);
                break;
            case 2:
                rgNegocioAval.check(R.id.rbNoNeg);
                break;
        }

        if (!row.getString(27).trim().isEmpty())
            etIngMenAval.setText(dfnd.format(row.getInt(27)));
        etIngMenAval.setEnabled(isEditAva);
        if (!row.getString(28).trim().isEmpty())
            etIngOtrosAval.setText(dfnd.format(row.getInt(28)));
        etIngOtrosAval.setEnabled(isEditAva);
        if (!row.getString(29).trim().isEmpty())
            etGastosSemAval.setText(dfnd.format(row.getInt(29)));
        etGastosSemAval.setEnabled(isEditAva);
        if (!row.getString(30).trim().isEmpty())
            etGastosAguaAval.setText(dfnd.format(row.getInt(30)));
        etGastosAguaAval.setEnabled(isEditAva);
        if (!row.getString(31).trim().isEmpty())
            etGastosLuzAval.setText(dfnd.format(row.getInt(31)));
        etGastosLuzAval.setEnabled(isEditAva);
        if (!row.getString(32).trim().isEmpty())
            etGastosTelAval.setText(dfnd.format(row.getInt(32)));
        etGastosTelAval.setEnabled(isEditAva);
        if (!row.getString(33).trim().isEmpty())
            etGastosRentaAval.setText(dfnd.format(row.getInt(33)));
        etGastosRentaAval.setEnabled(isEditAva);
        if (!row.getString(34).trim().isEmpty())
            etGastosOtrosAval.setText(dfnd.format(row.getInt(34)));
        etGastosOtrosAval.setEnabled(isEditAva);
        if (!row.getString(38).trim().isEmpty())
            tvMontoMaxAval.setText(dfnd.format(row.getInt(38)));

        if (!row.getString(35).trim().isEmpty())
            etCapacidadPagoAval.setText(dfnd.format(row.getInt(35)));
        etCapacidadPagoAval.setEnabled(isEditAva);
        tvMediosPagoAval.setText(row.getString(36));
        if (row.getString(36).contains("OTRO")) {
            etOtroMedioPagoAval.setText(row.getString(37));
            etOtroMedioPagoAval.setEnabled(isEditAva);
            etOtroMedioPagoAval.setVisibility(View.VISIBLE);
        }

        tvHoraLocAval.setText(row.getString(39));
        tvActivosObservables.setText(row.getString(40));
        etTelCasaAval.setText(row.getString(41));
        etTelCasaAval.setEnabled(isEditAva);
        etCelularAval.setText(row.getString(42));
        etCelularAval.setEnabled(isEditAva);
        etTelMensAval.setText(row.getString(43));
        etTelMensAval.setEnabled(isEditAva);
        etTelTrabajoAval.setText(row.getString(44));
        etTelTrabajoAval.setEnabled(isEditAva);
        etEmailAval.setText(row.getString(45));
        etEmailAval.setEnabled(isEditAva);
        if (!row.getString(46).isEmpty()) {
            File fachadaFile = new File(ROOT_PATH + "Fachada/" + row.getString(46));
            Uri uriFachada = Uri.fromFile(fachadaFile);
            byteFotoFachAval = Miscellaneous.getBytesUri(ctx, uriFachada, 0);
            Glide.with(ctx).load(uriFachada).into(ivFotoFachAval);
            ibFotoFachAval.setVisibility(View.GONE);
            ivFotoFachAval.setVisibility(View.VISIBLE);
        }
        etReferenciaAval.setText(row.getString(47));
        etReferenciaAval.setEnabled(isEditAva);
        if (!row.getString(48).isEmpty()) {
            File firmaFile = new File(ROOT_PATH + "Firma/" + row.getString(48));
            Uri uriFirma = Uri.fromFile(firmaFile);
            byteFirmaAval = Miscellaneous.getBytesUri(ctx, uriFirma, 0);
            Glide.with(ctx).load(uriFirma).into(ivFirmaAval);
            ibFirmaAval.setVisibility(View.GONE);
            ivFirmaAval.setVisibility(View.VISIBLE);
        }
        row.close(); //Cierra datos del aval


        //Llena datos de referencia
        row = dBhelper.getRecords(TBL_REFERENCIA_IND_REN, " WHERE id_solicitud = ?", "", new String[]{idSolicitud});
        row.moveToFirst();
        if (!row.getString(9).trim().isEmpty()){
            llComentRef.setVisibility(View.VISIBLE);
            tvComentAdminRef.setText(row.getString(9).trim().toUpperCase());
        }
        isEditRef = row.getInt(8) == 0;
        etNombreRef.setText(row.getString(2));
        etNombreRef.setEnabled(isEditRef);
        etApPaternoRef.setText(row.getString(3));
        etApPaternoRef.setEnabled(isEditRef);
        etApMaternoRef.setText(row.getString(4));
        etApMaternoRef.setEnabled(isEditRef);
        tvFechaNacRef.setText(row.getString(5));

        rowDir = dBhelper.getRecords(TBL_DIRECCIONES_REN, " WHERE id_direccion = ? AND tipo_direccion = ?", "", new String[]{row.getString(6), "REFERENCIA"});
        rowDir.moveToFirst();

        direccionIdRef = rowDir.getString(0);
        etCalleRef.setText(rowDir.getString(4));
        etCalleRef.setEnabled(isEditRef);
        etNoExtRef.setText(rowDir.getString(5));
        etNoExtRef.setEnabled(isEditRef);
        etNoIntRef.setText(rowDir.getString(6));
        etNoIntRef.setEnabled(isEditRef);
        etLoteRef.setText(rowDir.getString(7));
        etLoteRef.setEnabled(isEditRef);
        etManzanaRef.setText(rowDir.getString(8));
        etManzanaRef.setEnabled(isEditRef);
        etCpRef.setText(rowDir.getString(9));
        etCpRef.setEnabled(isEditRef);
        tvColoniaRef.setText(rowDir.getString(10));
        etCiudadRef.setText(rowDir.getString(11));
        etCiudadRef.setEnabled(isEditRef);
        tvLocalidadRef.setText(rowDir.getString(12));
        tvMunicipioRef.setText(rowDir.getString(13));
        tvEstadoRef.setText(rowDir.getString(14));
        rowDir.close(); //Cierra datos de direccion del referencia
        etTelCelRef.setText(row.getString(7));
        etTelCelRef.setEnabled(isEditRef);
        row.close(); //Cierra datos de referencia

        row = dBhelper.getRecords(TBL_CROQUIS_IND_REN, " WHERE id_solicitud = ?", "", new String[]{idSolicitud});
        row.moveToFirst();
        if (!row.getString(8).trim().isEmpty()){
            llComentCro.setVisibility(View.VISIBLE);
            tvComentAdminCro.setText(row.getString(8).trim().toUpperCase());
        }
        isEditCro = row.getInt(7) == 0;
        tvPrincipal.setText(row.getString(2).trim().toUpperCase());
        tvLateraUno.setText(row.getString(3).trim().toUpperCase());
        tvLateraDos.setText(row.getString(4).trim().toUpperCase());
        tvTrasera.setText(row.getString(5).trim().toUpperCase());
        etReferencia.setText(row.getString(6));
        etReferencia.setEnabled(isEditCro);
        row.close(); //Cierra datos del croquis

        row = dBhelper.getRecords(TBL_POLITICAS_PLD_IND_REN, " WHERE id_solicitud = ?", "", new String[]{idSolicitud});
        row.moveToFirst();
        isEditPol = row.getInt(5) == 0;
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

        //Llena la documentacion
        row = dBhelper.getRecords(TBL_DOCUMENTOS_REN, " WHERE id_solicitud = ?", "", new String[]{idSolicitud});
        row.moveToFirst();
        isEditDoc = row.getInt(8) == 0;
        if (!row.getString(2).isEmpty()) {
            File ineFrontalFile = new File(ROOT_PATH + "Documentos/" + row.getString(2));
            Uri uriIneFrontal = Uri.fromFile(ineFrontalFile);
            byteIneFrontal = Miscellaneous.getBytesUri(ctx, uriIneFrontal, 0);
            Glide.with(ctx).load(uriIneFrontal).into(ivIneFrontal);
            ibIneFrontal.setVisibility(View.GONE);
            ivIneFrontal.setVisibility(View.VISIBLE);
        }

        if (!row.getString(3).isEmpty()) {
            File ineReversoFile = new File(ROOT_PATH + "Documentos/" + row.getString(3));
            Uri uriIneReverso = Uri.fromFile(ineReversoFile);
            byteIneReverso = Miscellaneous.getBytesUri(ctx, uriIneReverso, 0);
            Glide.with(ctx).load(uriIneReverso).into(ivIneReverso);
            ibIneReverso.setVisibility(View.GONE);
            ivIneReverso.setVisibility(View.VISIBLE);
        }

        /*if (!row.getString(4).isEmpty()){
            File curpFile = new File(ROOT_PATH + "Documentos/"+row.getString(4));
            Uri uriCurp = Uri.fromFile(curpFile);
            byteCurp = Miscellaneous.getBytesUri(ctx, uriCurp,0);
            Glide.with(ctx).load(uriCurp).into(ivCurp);
            ibCurp.setVisibility(View.GONE);
            ivCurp.setVisibility(View.VISIBLE);
        }*/

        if (!row.getString(5).isEmpty()) {
            File comprobanteFile = new File(ROOT_PATH + "Documentos/" + row.getString(5));
            Uri uriComprobante = Uri.fromFile(comprobanteFile);
            byteComprobante = Miscellaneous.getBytesUri(ctx, uriComprobante, 0);
            Glide.with(ctx).load(uriComprobante).into(ivComprobante);
            ibComprobante.setVisibility(View.GONE);
            ivComprobante.setVisibility(View.VISIBLE);
        }

        if (!row.getString(7).isEmpty()) {
            File firmaFile = new File(ROOT_PATH + "Firma/" + row.getString(7));
            Uri uriFirma = Uri.fromFile(firmaFile);
            byteFirmaAsesor = Miscellaneous.getBytesUri(ctx, uriFirma, 0);
            Glide.with(ctx).load(uriFirma).into(ivFirmaAsesor);
            ibFirmaAsesor.setVisibility(View.GONE);
            ivFirmaAsesor.setVisibility(View.VISIBLE);
        }
        row.close(); //Cierra datos de los documentos

        if (!isEditCre) {
            tvPlazo.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked_left));
            tvFrecuencia.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked_right));
            tvFechaDesembolso.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            tvHoraVisita.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etMontoPrestamo.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            tvDestino.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            tvRiesgo.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            tvComportamiento.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etObservaciones.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        }

        if (!isEditCli) {
            etNombreCli.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etApPaternoCli.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etApMaternoCli.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            tvFechaNacCli.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            for (int i = 0; i < rgGeneroCli.getChildCount(); i++) {
                ((RadioButton) rgGeneroCli.getChildAt(i)).setEnabled(false);
            }
            tvEstadoNacCli.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etCurpCli.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            tvOcupacionCli.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            tvTipoIdentificacion.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etNumIdentifCli.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            tvEstudiosCli.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            tvEstadoCivilCli.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            for (int i = 0; i < rgBienes.getChildCount(); i++) {
                ((RadioButton) rgBienes.getChildAt(i)).setEnabled(false);
            }
            tvTipoCasaCli.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            tvCasaFamiliar.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etOTroTipoCli.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etCalleCli.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etNoExtCli.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etNoIntCli.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etManzanaCli.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etLoteCli.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etCpCli.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            tvColoniaCli.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etCiudadCli.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            tvLocalidadCli.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etTelCasaCli.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etCelularCli.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etTelMensCli.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etTelTrabajoCli.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etTiempoSitio.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            tvDependientes.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            tvEnteroNosotros.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            tvEstadoCuenta.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etEmail.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etReferenciCli.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        }

        if (!isEditCon) {
            etNombreCony.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etApPaternoCony.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etApMaternoCony.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etNacionalidadCony.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            tvOcupacionCony.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etCalleCony.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etNoExtCony.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etNoIntCony.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etManzanaCony.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etLoteCony.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etCpCony.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            tvColoniaCony.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etCiudadCony.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            tvLocalidadCony.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etIngMenCony.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etGastoMenCony.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etCasaCony.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etCelularCony.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        }

        if (!isEditEco) {
            etPropiedadesEco.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etValorAproxEco.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etUbicacionEco.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            tvIngresoEco.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        }

        if (!isEditNeg) {
            etNombreNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etCalleNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etNoExtNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etNoIntNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etManzanaNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etLoteNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etCpNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            tvColoniaNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etCiudadNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            tvLocalidadNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            tvActEcoEspNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            tvActEconomicaNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            tvDestinoNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etOtroDestinoNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etAntiguedadNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etIngMenNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etOtrosIngNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etGastosMenNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etGastosAguaNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etGastosLuzNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etGastosTelNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etGastosRentaNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etGastosOtrosNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            tvMediosPagoNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etOtroMedioPagoNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            tvMontoMaxNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etCapacidadPagoNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            tvNumOperacionNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etNumOperacionEfectNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            tvDiasVentaNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etReferenciNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        }

        if (!isEditAva){
            etNombreAval.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etApPaternoAval.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etApMaternoAval.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            tvFechaNacAval.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            for (int i = 0; i < rgGeneroAval.getChildCount(); i++) {
                ((RadioButton) rgGeneroAval.getChildAt(i)).setEnabled(false);
            }
            tvEstadoNacAval.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etCurpAval.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked_right));
            tvParentescoAval.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            tvTipoIdentificacionAval.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etNumIdentifAval.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            tvOcupacionAval.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etCalleAval.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etNoExtAval.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etNoIntAval.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etManzanaAval.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etLoteAval.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etCpAval.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            tvColoniaAval.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etCiudadAval.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            tvLocalidadAval.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            tvTipoCasaAval.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            tvFamiliarAval.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etNombreTitularAval.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etCaracteristicasAval.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            for (int i = 0; i < rgNegocioAval.getChildCount(); i++) {
                ((RadioButton) rgNegocioAval.getChildAt(i)).setEnabled(false);
            }
            etNombreNegocioAval.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etIngMenAval.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etIngOtrosAval.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etGastosSemAval.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etGastosAguaAval.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etGastosLuzAval.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etGastosTelAval.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etGastosRentaAval.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etGastosOtrosAval.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            tvMediosPagoAval.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etOtroMedioPagoAval.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etCapacidadPagoAval.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            tvHoraLocAval.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            tvActivosObservables.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etAntiguedadAval.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etTelCasaAval.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etCelularAval.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etTelMensAval.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etTelTrabajoAval.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etEmailAval.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etReferenciaAval.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        }

        if (!isEditRef) {
            etNombreRef.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etApPaternoRef.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etApMaternoRef.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            tvFechaNacRef.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etCalleRef.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etNoExtRef.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etNoIntRef.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etLoteRef.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etManzanaRef.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etCpRef.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            tvColoniaRef.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etCiudadRef.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            tvLocalidadRef.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etTelCelRef.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        }

        if (!isEditCro) {
            etReferencia.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        }

        if (!isEditPol){
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
}
