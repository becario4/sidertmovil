package com.sidert.sidertmovil.views.originacion;

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
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MultiAutoCompleteTextView;
import android.widget.ProgressBar;
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
import com.google.gson.Gson;
import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.activities.CameraVertical;
import com.sidert.sidertmovil.activities.CapturarFirma;
import com.sidert.sidertmovil.activities.Catalogos;
import com.sidert.sidertmovil.activities.VerImagen;
import com.sidert.sidertmovil.database.DBhelper;
import com.sidert.sidertmovil.fragments.dialogs.DialogSelectorFecha;
import com.sidert.sidertmovil.fragments.dialogs.dialog_date_picker;
import com.sidert.sidertmovil.fragments.dialogs.dialog_input_calle;
import com.sidert.sidertmovil.fragments.dialogs.dialog_registro_cli;
import com.sidert.sidertmovil.fragments.dialogs.dialog_sending_solicitud_individual;
import com.sidert.sidertmovil.fragments.dialogs.dialog_time_picker;
import com.sidert.sidertmovil.models.ApiResponse;
import com.sidert.sidertmovil.models.MSolicitudRechazoInd;
import com.sidert.sidertmovil.models.ModeloCatalogoGral;
import com.sidert.sidertmovil.models.catalogos.Colonia;
import com.sidert.sidertmovil.models.catalogos.ColoniaDao;
import com.sidert.sidertmovil.models.datosCampañas.datosCampanaDao;
import com.sidert.sidertmovil.models.permiso.PermisoResponse;
import com.sidert.sidertmovil.models.solicitudes.Solicitud;
import com.sidert.sidertmovil.models.solicitudes.SolicitudDao;
import com.sidert.sidertmovil.models.solicitudes.solicitudgpo.renovacion.BeneficiarioRenDao;
import com.sidert.sidertmovil.models.solicitudes.solicitudind.SolicitudDetalleEstatusInd;
import com.sidert.sidertmovil.models.solicitudes.solicitudind.originacion.Aval;
import com.sidert.sidertmovil.models.solicitudes.solicitudind.originacion.AvalDao;
import com.sidert.sidertmovil.models.solicitudes.solicitudind.originacion.Beneficiario;
import com.sidert.sidertmovil.models.solicitudes.solicitudind.originacion.BeneficiarioDao;
import com.sidert.sidertmovil.models.solicitudes.solicitudind.originacion.Cliente;
import com.sidert.sidertmovil.models.solicitudes.solicitudind.originacion.ClienteDao;
import com.sidert.sidertmovil.models.solicitudes.solicitudind.originacion.Conyugue;
import com.sidert.sidertmovil.models.solicitudes.solicitudind.originacion.ConyugueDao;
import com.sidert.sidertmovil.models.solicitudes.solicitudind.originacion.CreditoInd;
import com.sidert.sidertmovil.models.solicitudes.solicitudind.originacion.CreditoIndDao;
import com.sidert.sidertmovil.models.solicitudes.solicitudind.originacion.Direccion;
import com.sidert.sidertmovil.models.solicitudes.solicitudind.originacion.DireccionDao;
import com.sidert.sidertmovil.models.solicitudes.solicitudind.originacion.Documento;
import com.sidert.sidertmovil.models.solicitudes.solicitudind.originacion.DocumentoDao;
import com.sidert.sidertmovil.models.solicitudes.solicitudind.originacion.Economico;
import com.sidert.sidertmovil.models.solicitudes.solicitudind.originacion.EconomicoDao;
import com.sidert.sidertmovil.models.solicitudes.solicitudind.originacion.Negocio;
import com.sidert.sidertmovil.models.solicitudes.solicitudind.originacion.NegocioDao;
import com.sidert.sidertmovil.models.solicitudes.solicitudind.originacion.PoliticaPld;
import com.sidert.sidertmovil.models.solicitudes.solicitudind.originacion.PoliticaPldDao;
import com.sidert.sidertmovil.models.solicitudes.solicitudind.originacion.Referencia;
import com.sidert.sidertmovil.models.solicitudes.solicitudind.originacion.ReferenciaDao;
import com.sidert.sidertmovil.services.permiso.IPermisoService;
import com.sidert.sidertmovil.services.solicitud.solicitudind.SolicitudIndService;
import com.sidert.sidertmovil.utils.Constants;
import com.sidert.sidertmovil.utils.ManagerInterface;
import com.sidert.sidertmovil.utils.Miscellaneous;
import com.sidert.sidertmovil.utils.MyCurrentListener;
import com.sidert.sidertmovil.utils.NameFragments;
import com.sidert.sidertmovil.utils.NetworkStatus;
import com.sidert.sidertmovil.utils.Popups;
import com.sidert.sidertmovil.utils.RetrofitClient;
import com.sidert.sidertmovil.utils.SessionManager;
import com.sidert.sidertmovil.utils.Validator;
import com.sidert.sidertmovil.utils.ValidatorTextView;

import org.json.JSONObject;

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

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import io.card.payment.CardIOActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.sidert.sidertmovil.database.SidertTables.SidertEntry.TABLE_MUNICIPIOS;
import static com.sidert.sidertmovil.utils.Constants.CALLE;
import static com.sidert.sidertmovil.utils.Constants.CAMPANAS;
import static com.sidert.sidertmovil.utils.Constants.CATALOGO;
import static com.sidert.sidertmovil.utils.Constants.COLONIAS;
import static com.sidert.sidertmovil.utils.Constants.CONTROLLER_SOLICITUDES;
import static com.sidert.sidertmovil.utils.Constants.DATE_CURRENT;
import static com.sidert.sidertmovil.utils.Constants.ESTADOS;
import static com.sidert.sidertmovil.utils.Constants.ES_RENOVACION;
import static com.sidert.sidertmovil.utils.Constants.EXTRA;
import static com.sidert.sidertmovil.utils.Constants.FECHAS_POST;
import static com.sidert.sidertmovil.utils.Constants.FIRMA_IMAGE;
import static com.sidert.sidertmovil.utils.Constants.FORMAT_DATE_GNRAL;
import static com.sidert.sidertmovil.utils.Constants.IDENTIFIER;
import static com.sidert.sidertmovil.utils.Constants.ID_SOLICITUD;
import static com.sidert.sidertmovil.utils.Constants.IMAGEN;
import static com.sidert.sidertmovil.utils.Constants.ITEM;
import static com.sidert.sidertmovil.utils.Constants.LOCALIDADES;
import static com.sidert.sidertmovil.utils.Constants.OCUPACIONES;
import static com.sidert.sidertmovil.utils.Constants.ORDER_ID;
import static com.sidert.sidertmovil.utils.Constants.PICTURE;
import static com.sidert.sidertmovil.utils.Constants.REQUEST_CODE;
import static com.sidert.sidertmovil.utils.Constants.REQUEST_CODE_ACTIVIDAD_NEG;
import static com.sidert.sidertmovil.utils.Constants.REQUEST_CODE_CAMAPANAS;
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
import static com.sidert.sidertmovil.utils.Constants.REQUEST_CODE_FOTO_COMPROBANTE_AVAL;
import static com.sidert.sidertmovil.utils.Constants.REQUEST_CODE_FOTO_COMPROBATE;
import static com.sidert.sidertmovil.utils.Constants.REQUEST_CODE_FOTO_COMPROBATE_GARANTIA;
import static com.sidert.sidertmovil.utils.Constants.REQUEST_CODE_FOTO_CURP;
import static com.sidert.sidertmovil.utils.Constants.REQUEST_CODE_FOTO_CURP_AVAL;
import static com.sidert.sidertmovil.utils.Constants.REQUEST_CODE_FOTO_INE_FRONTAL;
import static com.sidert.sidertmovil.utils.Constants.REQUEST_CODE_FOTO_INE_FRONTAL_AVAL;
import static com.sidert.sidertmovil.utils.Constants.REQUEST_CODE_FOTO_INE_REVERSO;
import static com.sidert.sidertmovil.utils.Constants.REQUEST_CODE_FOTO_INE_REVERSO_AVAL;
import static com.sidert.sidertmovil.utils.Constants.REQUEST_CODE_FOTO_INE_SELFIE;
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
import static com.sidert.sidertmovil.utils.Constants.TBL_AVAL_IND;
import static com.sidert.sidertmovil.utils.Constants.TBL_CLIENTE_IND;
import static com.sidert.sidertmovil.utils.Constants.TBL_CONYUGE_IND;
import static com.sidert.sidertmovil.utils.Constants.TBL_CREDITO_IND;
import static com.sidert.sidertmovil.utils.Constants.TBL_CROQUIS_IND;
import static com.sidert.sidertmovil.utils.Constants.TBL_DATOS_BENEFICIARIO;
import static com.sidert.sidertmovil.utils.Constants.TBL_DATOS_CREDITO_CAMPANA;
import static com.sidert.sidertmovil.utils.Constants.TBL_DIRECCIONES;
import static com.sidert.sidertmovil.utils.Constants.TBL_DOCUMENTOS;
import static com.sidert.sidertmovil.utils.Constants.TBL_ECONOMICOS_IND;
import static com.sidert.sidertmovil.utils.Constants.TBL_NEGOCIO_IND;
import static com.sidert.sidertmovil.utils.Constants.TBL_POLITICAS_PLD_IND;
import static com.sidert.sidertmovil.utils.Constants.TBL_REFERENCIA_IND;
import static com.sidert.sidertmovil.utils.Constants.TBL_SOLICITUDES;
import static com.sidert.sidertmovil.utils.Constants.TIMESTAMP;
import static com.sidert.sidertmovil.utils.Constants.TIPO;
import static com.sidert.sidertmovil.utils.Constants.TIPO_SOLICITUD;
import static com.sidert.sidertmovil.utils.Constants.TITULO;
import static com.sidert.sidertmovil.utils.Constants.firma;
import static com.sidert.sidertmovil.utils.Constants.question;
import static com.sidert.sidertmovil.utils.Constants.warning;
import static io.card.payment.CardIOActivity.RESULT_SCAN_SUPPRESSED;

public class SolicitudCreditoInd extends AppCompatActivity implements dialog_registro_cli.OnCompleteListener {

    private static final String SIN_REFERENCIA = "NINGUNO";
    private Context ctx;
    private Context context;
    private final Integer ind = 1;
    private String[] _dias_semana;
    private String[] _plazo;
    private String[] _frecuencia;
    private String[] _destino;
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
    private String[] _max_pagos_mes;
    private ArrayList<Integer> selectedItemsDias;
    private ArrayList<Integer> selectedItemsMediosPago;
    private ArrayList<Integer> selectedItemsActivos;
    private DBhelper dBhelper;
    private SQLiteDatabase db;
    private final SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_DATE_GNRAL);
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
    private FloatingActionButton btnContinuarBeni;

    private FloatingActionButton btnRegresar1;
    private FloatingActionButton btnRegresar2;
    private FloatingActionButton btnRegresar3;
    private FloatingActionButton btnRegresar4;
    private FloatingActionButton btnRegresar5;
    private FloatingActionButton btnRegresar6;
    private FloatingActionButton btnRegresar7;
    private FloatingActionButton btnRegresar8;
    private FloatingActionButton btnRegresar9;
    private FloatingActionButton btnRegresarBeni;

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
    private EditText etMontoRefinanciar;
    private TextView txtCampana;
    private EditText txtNombreRefiero;

    //=========================================
    //======== DATOS PERSONALES ===============
    private LinearLayout llComentAdminCli;
    private TextView tvComentAdminCli;
    private EditText etNombreCli;
    private EditText etApPaternoCli;
    private EditText etApMaternoCli;
    private TextView tvFechaNacCli;
    private DialogSelectorFecha date;
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

    //========================================
    //========== BENEFICIARIO ================

    private LinearLayout llBeneficiario;
    private LinearLayout llDatosBeneficiario;
    private EditText txtNomBeneficiario;
    private EditText txtApellidoPaternoBeneficiario;
    private EditText txtApellidoMaternoBeneficiario;
    private TextView txtParentescoBeneficiario;
    private TextView txtEjemeplo;
    Beneficiario beneficiario;
    private Button btnRegistrar;

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
    private LinearLayout llComentAdminNeg;
    private TextView tvComentAdminNeg;
    private CheckBox cbNegEnDomCli;
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
    private TextView etNumOperacionEfectNeg;
    private TextView tvDiasVentaNeg;
    private TextView tvFachadaNeg;
    private ImageButton ibFotoFachNeg;
    private ImageView ivFotoFachNeg;
    public byte[] byteFotoFachNeg;
    private MultiAutoCompleteTextView etReferenciNeg;
    //========================================
    //======= AVAL ===========================
    private LinearLayout llComentAdminAval;
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
    private EditText etAntiguedadAval;
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
    private TextView tvIneFrontalAval;
    private ImageButton ibIneFrontalAval;
    private ImageView ivIneFrontalAval;
    public byte[] byteIneFrontalAval;
    private TextView tvIneReversoAval;
    private ImageButton ibIneReversoAval;
    private ImageView ivIneReversoAval;
    public byte[] byteIneReversoAval;
    private LinearLayout llFotoCurpAval;
    private TextView tvCurpAval;
    private ImageButton ibCurpAval;
    private ImageView ivCurpAval;
    public byte[] byteCurpAval;
    private TextView tvComprobanteAval;
    private ImageButton ibComprobanteAval;
    private ImageView ivComprobanteAval;
    public byte[] byteComprobanteAval;

    //========================================
    //========== REFERENCIA ==================
    private LinearLayout llComentAdminRef;
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
    private LinearLayout llComentAdminCro;
    private TextView tvComentAdminCro;
    private TextView tvCasa;
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
    private TextView tvIneSelfie;
    private ImageButton ibIneSelfie;
    private ImageView ivIneSelfie;
    public byte[] byteIneSelfie;
    private TextView tvComprobanteGarantia;
    private ImageButton ibComprobanteGarantia;
    private ImageView ivComprobanteGarantia;
    public byte[] byteComprobanteGarantia;
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
    private ImageView ivDownBeni;
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
    private ImageView ivUpBeni;
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
    private ImageView ivError11;
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
    boolean hasFractionalPart = false;
    //========== is edit fields
    boolean isEditCre = true;
    boolean isEditCli = true;
    boolean isEditCon = true;
    boolean isEditBen = true;
    boolean isEditEco = true;
    boolean isEditNeg = true;
    boolean isEditAva = true;
    boolean isEditRef = true;
    boolean isEditCro = true;
    boolean isEditPol = true;
    boolean isEditDoc = true;
    boolean pushMapButtonCli = false;
    boolean pushMapButtonNeg = false;
    boolean pushMapButtonAval = false;
    private final int MENU_INDEX_DEVMODE = 3;
    private final int MENU_INDEX_DESBLOQUEAR = 2;
    private final int MENU_INDEX_UPDATE_ESTATUS = 1;
    private final int MENU_INDEX_ENVIAR = 0;
    private boolean modoSuperUsuario = false;
    private final String PATTERN_MONTO_CREDITO = "[1-9][0-9][0-9][0][0][0]|[1-9][0-9][0][0][0]|[1-9][0][0][0]";
    private final Pattern pattern0 = Pattern.compile(PATTERN_MONTO_CREDITO);
    private final String PATTERN_MONTO_CREDITO_02 = "[0-9]+";
    private final Pattern pattern1 = Pattern.compile(PATTERN_MONTO_CREDITO_02);

    //======================================================

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solicitud_credito_ind);

        ProgressDialog dialog = ProgressDialog.show(SolicitudCreditoInd.this, "",
                "Cargando la información por favor espere...", true);
        dialog.show();
        df.setDecimalSeparatorAlwaysShown(true);

        ctx = getApplicationContext();
        context = this;

        dBhelper = DBhelper.getInstance(ctx);
        db = dBhelper.getWritableDatabase();

        //PASAR ESTE MÉTODO AL DAO BENEFICIARIO
        int serieA = BeneficiarioRenDao.obtenerSerieAsesor(ctx); //Miscellaneous.obtenerSerieAsesor(ctx);

        Log.e("AQUI:", String.valueOf(serieA));

        _dias_semana = getResources().getStringArray(R.array.dias_semana);

        myCalendar = Calendar.getInstance();

        validator = new Validator();
        validatorTV = new ValidatorTextView();

        TBmain = findViewById(R.id.TBmain);

        _plazo = getResources().getStringArray(R.array.intervalo);
        _frecuencia = getResources().getStringArray(R.array.lapso);
        _destino = getResources().getStringArray(R.array.destino_prestamo);
        _estudios = Miscellaneous.GetNivelesEstudio(ctx);
        _civil = Miscellaneous.GetEstadoCiviles(ctx);
        _tipo_identificacion = Miscellaneous.GetIdentificacion(ctx);
        _medios_pago = Miscellaneous.GetMediosPagoOri(ctx);
        _parentesco = Miscellaneous.GetParentesco(ctx, "PARENTESCO");
        _parentesco_casa = Miscellaneous.GetParentesco(ctx, "CASA");
        _tipo_casa = Miscellaneous.GetViviendaTipos(ctx);
        _medio_contacto = Miscellaneous.GetMediosContacto(ctx);
        _destino_credito = Miscellaneous.GetDestinosCredito(ctx);
        _dependientes = getResources().getStringArray(R.array.dependientes_eco);
        _riesgo = getResources().getStringArray(R.array.clasificacion_riesgo);
        _confirmacion = getResources().getStringArray(R.array.confirmacion);
        _activos = getResources().getStringArray(R.array.activos);
        _max_pagos_mes = getResources().getStringArray(R.array.max_pagos_mes);


        setSupportActionBar(TBmain);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setDisplayShowHomeEnabled(true);

        //=================================== DATOS CREDITO  =======================================
        tvPlazo = findViewById(R.id.tvPlazo);
        tvFrecuencia = findViewById(R.id.tvFrecuencia);
        tvFechaDesembolso = findViewById(R.id.tvFechaDesembolso);
        tvDiaDesembolso = findViewById(R.id.tvDiaDesembolso);
        tvHoraVisita = findViewById(R.id.tvHoraVisita);
        etMontoPrestamo = findViewById(R.id.etMontoPrestamo);
        tvCantidadLetra = findViewById(R.id.tvCantidadLetra);
        tvDestino = findViewById(R.id.tvDestino);
        tvRiesgo = findViewById(R.id.tvRiesgo);
        etMontoRefinanciar = findViewById(R.id.etMontoRefinanciar);
        txtCampana = findViewById(R.id.txtCampana);
        txtNombreRefiero = findViewById(R.id.txtNombreRefiero);
        //==========================================================================================
        //=================================  DATOS PERSONALES  =====================================
        llComentAdminCli = findViewById(R.id.llComentCli);
        tvComentAdminCli = findViewById(R.id.tvComentAdminCli);
        etNombreCli = findViewById(R.id.etNombreCli);
        etApPaternoCli = findViewById(R.id.etApPaternoCli);
        etApMaternoCli = findViewById(R.id.etApMaternoCli);
        tvFechaNacCli = findViewById(R.id.tvFechaNacCli);
        tvEdadCli = findViewById(R.id.tvEdadCli);
        tvGeneroCli = findViewById(R.id.tvGeneroCli);
        rgGeneroCli = findViewById(R.id.rgGeneroCli);
        tvEstadoNacCli = findViewById(R.id.tvEstadoNacCli);
        tvRfcCli = findViewById(R.id.tvRfcCli);
        etCurpCli = findViewById(R.id.etCurpCli);
        //etCurpIdCli          = findViewById(R.id.etCurpIdCli);
        tvOcupacionCli = findViewById(R.id.tvOcupacionCli);
        tvActividadEcoCli = findViewById(R.id.tvActividadEcoCli);
        tvTipoIdentificacion = findViewById(R.id.tvTipoIdentificacion);
        etNumIdentifCli = findViewById(R.id.etNumIdentifCli);
        tvEstudiosCli = findViewById(R.id.tvEstudiosCli);
        tvEstadoCivilCli = findViewById(R.id.tvEstadoCivilCli);
        llBienes = findViewById(R.id.llBienes);
        tvBienes = findViewById(R.id.tvBienes);
        rgBienes = findViewById(R.id.rgBienes);
        tvTipoCasaCli = findViewById(R.id.tvTipoCasaCli);
        llCasaFamiliar = findViewById(R.id.llCasaFamiliar);
        tvCasaFamiliar = findViewById(R.id.tvCasaFamiliar);
        llCasaOtroCli = findViewById(R.id.llCasaOtro);
        etOTroTipoCli = findViewById(R.id.etOtroTipoCli);
        tvMapaCli = findViewById(R.id.tvMapaCli);
        ibMapCli = findViewById(R.id.ibMapCli);
        pbLoadCli = findViewById(R.id.pbLoadCli);
        mapCli = findViewById(R.id.mapCli);
        etCalleCli = findViewById(R.id.etCalleCli);
        etNoExtCli = findViewById(R.id.etNoExtCli);
        etManzanaCli = findViewById(R.id.etManzanaCli);
        etNoIntCli = findViewById(R.id.etNoIntCli);
        etLoteCli = findViewById(R.id.etLoteCli);
        etCpCli = findViewById(R.id.etCpCli);
        tvColoniaCli = findViewById(R.id.tvColoniaCli);
        etCiudadCli = findViewById(R.id.etCiudadCli);
        tvLocalidadCli = findViewById(R.id.tvLocalidadCli);
        tvMunicipioCli = findViewById(R.id.tvMunicipioCli);
        tvEstadoCli = findViewById(R.id.tvEstadoCli);
        etTelCasaCli = findViewById(R.id.etTelCasaCli);
        etCelularCli = findViewById(R.id.etCelularCli);
        etTelMensCli = findViewById(R.id.etTelMensCli);
        etTelTrabajoCli = findViewById(R.id.etTelTrabajoCli);
        etTiempoSitio = findViewById(R.id.etTiempoSitio);
        tvDependientes = findViewById(R.id.tvDependientes);
        tvEnteroNosotros = findViewById(R.id.tvEnteroNosotros);
        tvEstadoCuenta = findViewById(R.id.tvEstadoCuenta);
        etEmail = findViewById(R.id.etEmail);
        tvFachadaCli = findViewById(R.id.tvFachadaCli);
        ibFotoFachCli = findViewById(R.id.ibFotoFachCli);
        ivFotoFachCli = findViewById(R.id.ivFotoFachCli);
        etReferenciCli = findViewById(R.id.etReferenciaCli);
        tvFirmaCli = findViewById(R.id.tvFirmaCli);
        ibFirmaCli = findViewById(R.id.ibFirmaCli);
        ivFirmaCli = findViewById(R.id.ivFirmaCli);

        //==========================================================================================
        //==================================  DATOS BENEFICIARIO  ==================================

        llBeneficiario = findViewById(R.id.llBeneficiario);
        llDatosBeneficiario = findViewById(R.id.llDatosBeneficiario);
        txtNomBeneficiario = findViewById(R.id.txtNombreBeneficiario);
        txtApellidoPaternoBeneficiario = findViewById(R.id.txtApellidoPaternoBeneficiario);
        txtApellidoMaternoBeneficiario = findViewById(R.id.txtApellidoMaternoBeneficiario);
        txtParentescoBeneficiario = findViewById(R.id.txtParentezcoBeneficiario);
        //btnRegistrar = findViewById(R.id.btnRegistrar);

        //==========================================================================================
        //===================================  DATOS CONYUGE  ======================================
        etNombreCony = findViewById(R.id.etNombreCony);
        etApPaternoCony = findViewById(R.id.etApPaternoCony);
        etApMaternoCony = findViewById(R.id.etApMaternoCony);
        etNacionalidadCony = findViewById(R.id.etNacionalidadCony);
        tvOcupacionCony = findViewById(R.id.tvOcupacionCony);
        etCalleCony = findViewById(R.id.etCalleCony);
        etNoExtCony = findViewById(R.id.etNoExtCony);
        etManzanaCony = findViewById(R.id.etManzanaCony);
        etNoIntCony = findViewById(R.id.etNoIntCony);
        etLoteCony = findViewById(R.id.etLoteCony);
        etCpCony = findViewById(R.id.etCpCony);
        tvColoniaCony = findViewById(R.id.tvColoniaCony);
        etCiudadCony = findViewById(R.id.etCiudadCony);
        tvLocalidadCony = findViewById(R.id.tvLocalidadCony);
        tvMunicipioCony = findViewById(R.id.tvMunicipioCony);
        tvEstadoCony = findViewById(R.id.tvEstadoCony);
        etIngMenCony = findViewById(R.id.etIngMenCony);
        etGastoMenCony = findViewById(R.id.etGastoMenCony);
        etCasaCony = findViewById(R.id.etCasaCony);
        etCelularCony = findViewById(R.id.etCelularCony);
        //==========================================================================================
        //=================================  DATOS ECONOMICOS  =====================================
        etPropiedadesEco = findViewById(R.id.etPropiedadesEco);
        etValorAproxEco = findViewById(R.id.etValorAproxEco);
        etUbicacionEco = findViewById(R.id.etUbicacionEco);
        tvIngresoEco = findViewById(R.id.tvIngresoEco);
        //==========================================================================================
        //===================================  DATOS NEGOCIO  ======================================
        llComentAdminNeg = findViewById(R.id.llComentNeg);
        tvComentAdminNeg = findViewById(R.id.tvComentAdminNeg);
        cbNegEnDomCli = findViewById(R.id.cbNegEnDomCli);
        etNombreNeg = findViewById(R.id.etNombreNeg);
        tvMapaNeg = findViewById(R.id.tvMapaNeg);
        ibMapNeg = findViewById(R.id.ibMapNeg);
        pbLoadNeg = findViewById(R.id.pbLoadNeg);
        mapNeg = findViewById(R.id.mapNeg);
        etCalleNeg = findViewById(R.id.etCalleNeg);
        etNoExtNeg = findViewById(R.id.etNoExtNeg);
        etNoIntNeg = findViewById(R.id.etNoIntNeg);
        etManzanaNeg = findViewById(R.id.etManzanaNeg);
        etLoteNeg = findViewById(R.id.etLoteNeg);
        etCpNeg = findViewById(R.id.etCpNeg);
        tvColoniaNeg = findViewById(R.id.tvColoniaNeg);
        etCiudadNeg = findViewById(R.id.etCiudadNeg);
        tvLocalidadNeg = findViewById(R.id.tvLocalidadNeg);
        tvMunicipioNeg = findViewById(R.id.tvMunicipioNeg);
        tvEstadoNeg = findViewById(R.id.tvEstadoNeg);
        tvActEcoEspNeg = findViewById(R.id.tvActEcoEspNeg);
        tvActEconomicaNeg = findViewById(R.id.tvActEconomicaNeg);
        tvDestinoNeg = findViewById(R.id.tvDestinoNeg);
        etOtroDestinoNeg = findViewById(R.id.etOtroDestinoNeg);
        etAntiguedadNeg = findViewById(R.id.etAntiguedadNeg);
        etIngMenNeg = findViewById(R.id.etIngMenNeg);
        etOtrosIngNeg = findViewById(R.id.etOtrosIngNeg);
        etGastosMenNeg = findViewById(R.id.etGastosMenNeg);
        etGastosAguaNeg = findViewById(R.id.etGastosAguaNeg);
        etGastosLuzNeg = findViewById(R.id.etGastosLuzNeg);
        etGastosTelNeg = findViewById(R.id.etGastosTelNeg);
        etGastosRentaNeg = findViewById(R.id.etGastosRentaNeg);
        etGastosOtrosNeg = findViewById(R.id.etGastosOtrosNeg);
        etCapacidadPagoNeg = findViewById(R.id.etCapacidadPagoNeg);
        tvMediosPagoNeg = findViewById(R.id.tvMediosPagoNeg);
        etOtroMedioPagoNeg = findViewById(R.id.etOtroMedioPagoNeg);
        tvMontoMaxNeg = findViewById(R.id.tvMontoMaxNeg);
        tvNumOperacionNeg = findViewById(R.id.tvNumOperacionNeg);
        llOperacionesEfectivo = findViewById(R.id.llOperacionesEfectivo);
        etNumOperacionEfectNeg = findViewById(R.id.etNumOperacionEfectNeg);
        tvDiasVentaNeg = findViewById(R.id.tvDiasVentaNeg);
        tvFachadaNeg = findViewById(R.id.tvFachadaNeg);
        ibFotoFachNeg = findViewById(R.id.ibFotoFachNeg);
        ivFotoFachNeg = findViewById(R.id.ivFotoFachNeg);
        etReferenciNeg = findViewById(R.id.etReferenciaNeg);
        //==========================================================================================
        //=====================================  DATOS AVAL  =======================================
        llComentAdminAval = findViewById(R.id.llComentAval);
        tvComentAdminAval = findViewById(R.id.tvComentAdminAval);
        etNombreAval = findViewById(R.id.etNombreAval);
        etApPaternoAval = findViewById(R.id.etApPaternoAval);
        etApMaternoAval = findViewById(R.id.etApMaternoAval);
        tvFechaNacAval = findViewById(R.id.tvFechaNacAval);
        tvEdadAval = findViewById(R.id.tvEdadAval);
        tvGeneroAval = findViewById(R.id.tvGeneroAval);
        rgGeneroAval = findViewById(R.id.rgGeneroAval);
        tvEstadoNacAval = findViewById(R.id.tvEstadoNacAval);
        tvRfcAval = findViewById(R.id.tvRfcAval);
        etCurpAval = findViewById(R.id.etCurpAval);
        //etCurpIdAval        = findViewById(R.id.etCurpIdAval);
        tvParentescoAval = findViewById(R.id.tvParentescoAval);
        tvTipoIdentificacionAval = findViewById(R.id.tvTipoIdentificacionAval);
        etNumIdentifAval = findViewById(R.id.etNumIdentifAval);
        tvOcupacionAval = findViewById(R.id.tvOcupacionAval);
        tvActividadEcoAval = findViewById(R.id.tvActividadEcoAval);
        tvMapaAval = findViewById(R.id.tvMapaAval);
        ibMapAval = findViewById(R.id.ibMapAval);
        pbLoadAval = findViewById(R.id.pbLoadAval);
        mapAval = findViewById(R.id.mapAval);
        etCalleAval = findViewById(R.id.etCalleAval);
        etNoExtAval = findViewById(R.id.etNoExtAval);
        etManzanaAval = findViewById(R.id.etManzanaAval);
        etNoIntAval = findViewById(R.id.etNoIntAval);
        etLoteAval = findViewById(R.id.etLoteAval);
        etCpAval = findViewById(R.id.etCpAval);
        tvColoniaAval = findViewById(R.id.tvColoniaAval);
        etCiudadAval = findViewById(R.id.etCiudadAval);
        tvLocalidadAval = findViewById(R.id.tvLocalidadAval);
        tvMunicipioAval = findViewById(R.id.tvMunicipioAval);
        tvEstadoAval = findViewById(R.id.tvEstadoAval);
        tvTipoCasaAval = findViewById(R.id.tvTipoCasaAval);
        tvFamiliarAval = findViewById(R.id.tvFamiliar);
        llParentescoFamAval = findViewById(R.id.llParentescoFamAval);
        llNombreTitular = findViewById(R.id.llNombreTitular);
        etNombreTitularAval = findViewById(R.id.etNombreTitularAval);
        etCaracteristicasAval = findViewById(R.id.etCaracteristicasAval);
        tvNombreNegAval = findViewById(R.id.tvNombreNegAval);
        rgNegocioAval = findViewById(R.id.rgNegocioAval);
        llNombreNegocio = findViewById(R.id.llNombreNegocio);
        etNombreNegocioAval = findViewById(R.id.etNombreNegocioAval);
        etAntiguedadAval = findViewById(R.id.etAntiguedadAval);
        etIngMenAval = findViewById(R.id.etIngMenAval);
        etIngOtrosAval = findViewById(R.id.etOtrosIngAval);
        etGastosSemAval = findViewById(R.id.etGastosSemAval);
        etGastosAguaAval = findViewById(R.id.etGastosAguaAval);
        etGastosLuzAval = findViewById(R.id.etGastosLuzAval);
        etGastosTelAval = findViewById(R.id.etGastosTelAval);
        etGastosRentaAval = findViewById(R.id.etGastosRentaAval);
        etGastosOtrosAval = findViewById(R.id.etGastosOtrosAval);
        tvMediosPagoAval = findViewById(R.id.tvMediosPagoAval);
        etOtroMedioPagoAval = findViewById(R.id.etOtroMedioPagoAval);
        tvMontoMaxAval = findViewById(R.id.tvMontoMaxAval);
        etCapacidadPagoAval = findViewById(R.id.etCapacidadPagoAval);
        tvHoraLocAval = findViewById(R.id.tvHoraLocAval);
        tvActivosObservables = findViewById(R.id.tvActivosObservables);

        etTelCasaAval = findViewById(R.id.etTelCasaAval);
        etCelularAval = findViewById(R.id.etCelularAval);
        etTelMensAval = findViewById(R.id.etTelMensAval);
        etTelTrabajoAval = findViewById(R.id.etTelTrabajoAval);
        etEmailAval = findViewById(R.id.etEmailAval);
        tvFachadaAval = findViewById(R.id.tvFachadaAval);
        ibFotoFachAval = findViewById(R.id.ibFotoFachAval);
        ivFotoFachAval = findViewById(R.id.ivFotoFachAval);
        etReferenciaAval = findViewById(R.id.etReferenciaAval);
        tvFirmaAval = findViewById(R.id.tvFirmaAval);
        ibFirmaAval = findViewById(R.id.ibFirmaAval);
        ivFirmaAval = findViewById(R.id.ivFirmaAval);

        tvIneFrontalAval = findViewById(R.id.tvIneFrontalAval);
        ibIneFrontalAval = findViewById(R.id.ibIneFrontalAval);
        ivIneFrontalAval = findViewById(R.id.ivIneFrontalAval);
        tvIneReversoAval = findViewById(R.id.tvIneReversoAval);
        ibIneReversoAval = findViewById(R.id.ibIneReversoAval);
        ivIneReversoAval = findViewById(R.id.ivIneReversoAval);
        llFotoCurpAval = findViewById(R.id.llFotoCurpAval);
        tvCurpAval = findViewById(R.id.tvCurpAval);
        ibCurpAval = findViewById(R.id.ibCurpAval);
        ivCurpAval = findViewById(R.id.ivCurpAval);
        tvComprobanteAval = findViewById(R.id.tvComprobanteAval);
        ibComprobanteAval = findViewById(R.id.ibComprobanteAval);
        ivComprobanteAval = findViewById(R.id.ivComprobanteAval);
        //==========================================================================================
        //==================================  DATOS REFERENCIA  ====================================
        llComentAdminRef = findViewById(R.id.llComentRef);
        tvComentAdminRef = findViewById(R.id.tvComentAdminRef);
        etNombreRef = findViewById(R.id.etNombreRef);
        etApPaternoRef = findViewById(R.id.etApPaternoRef);
        etApMaternoRef = findViewById(R.id.etApMaternoRef);
        tvFechaNacRef = findViewById(R.id.tvFechaNacRef);
        etCalleRef = findViewById(R.id.etCalleRef);
        etNoExtRef = findViewById(R.id.etNoExtRef);
        etManzanaRef = findViewById(R.id.etManzanaRef);
        etNoIntRef = findViewById(R.id.etNoIntRef);
        etLoteRef = findViewById(R.id.etLoteRef);
        etCpRef = findViewById(R.id.etCpRef);
        tvColoniaRef = findViewById(R.id.tvColoniaRef);
        etCiudadRef = findViewById(R.id.etCiudadRef);
        tvLocalidadRef = findViewById(R.id.tvLocalidadRef);
        tvMunicipioRef = findViewById(R.id.tvMunicipioRef);
        tvEstadoRef = findViewById(R.id.tvEstadoRef);
        etTelCelRef = findViewById(R.id.etTelCelRef);
        //==========================================================================================
        //====================================  CROQUIS   ==========================================
        llComentAdminCro = findViewById(R.id.llComentCro);
        tvComentAdminCro = findViewById(R.id.tvComentAdminCro);
        tvCasa = findViewById(R.id.tvCasa);
        tvPrincipal = findViewById(R.id.tvPrincipal);
        tvTrasera = findViewById(R.id.tvTrasera);
        tvLateraUno = findViewById(R.id.tvLateralUno);
        tvLateraDos = findViewById(R.id.tvLateralDos);
        etReferencia = findViewById(R.id.etReferencia);
        etCaracteristicasDomicilio = findViewById(R.id.etCaracteristicasDomicilio);
        //==========================================================================================
        //==================================  DATOS POLITICAS   ====================================
        tvPropietarioReal = findViewById(R.id.tvPropietarioReal);
        rgPropietarioReal = findViewById(R.id.rgPropietarioReal);
        tvAnexoPropietario = findViewById(R.id.tvAnexoPropietario);
        tvProvedor = findViewById(R.id.tvProvedor);
        rgProveedor = findViewById(R.id.rgProveedor);
        tvAnexoPreveedor = findViewById(R.id.tvAnexoPreveedor);
        tvPoliticamenteExp = findViewById(R.id.tvPoliticamenteExp);
        rgPoliticamenteExp = findViewById(R.id.rgPoliticamenteExp);
        tvAnexoPoliticamenteExp = findViewById(R.id.tvAnexoPoliticamenteExp);
        //==========================================================================================
        //=====================================  DOCUMENTOS  =======================================
        tvIneFrontal = findViewById(R.id.tvIneFrontal);
        ibIneFrontal = findViewById(R.id.ibIneFrontal);
        ivIneFrontal = findViewById(R.id.ivIneFrontal);
        tvIneReverso = findViewById(R.id.tvIneReverso);
        ibIneReverso = findViewById(R.id.ibIneReverso);
        ivIneReverso = findViewById(R.id.ivIneReverso);
        tvCurp = findViewById(R.id.tvCurp);
        ibCurp = findViewById(R.id.ibCurp);
        ivCurp = findViewById(R.id.ivCurp);
        tvComprobante = findViewById(R.id.tvComprobante);
        ibComprobante = findViewById(R.id.ibComprobante);
        ivComprobante = findViewById(R.id.ivComprobante);
        tvFirmaAsesor = findViewById(R.id.tvFirmaAsesor);
        ibFirmaAsesor = findViewById(R.id.ibFirmaAsesor);
        ivFirmaAsesor = findViewById(R.id.ivFirmaAsesor);
        tvIneSelfie = findViewById(R.id.tvIneSelfie);
        ibIneSelfie = findViewById(R.id.ibIneSelfie);
        ivIneSelfie = findViewById(R.id.ivIneSelfie);
        tvComprobanteGarantia = findViewById(R.id.tvComprobanteGarantia);
        ibComprobanteGarantia = findViewById(R.id.ibComprobanteGarantia);
        ivComprobanteGarantia = findViewById(R.id.ivComprobanteGarantia);
        //=========================================================
        //================ LINEAR LAYOUT  =========================
        llDatosCredito = findViewById(R.id.llDatosCredito);
        llDatosPersonales = findViewById(R.id.llDatosPersonales);
        llDatosConyuge = findViewById(R.id.llDatosConyuge);
        llDatosEconomicos = findViewById(R.id.llDatosEconomicos);
        llDatosNegocio = findViewById(R.id.llDatosNegocio);
        llDatosAval = findViewById(R.id.llDatosAval);
        llDatosReferencia = findViewById(R.id.llDatosReferencia);
        llDatosCroquis = findViewById(R.id.llDatosCroquis);
        llDatosPoliticas = findViewById(R.id.llDatosPoliticas);
        llDatosDocumentos = findViewById(R.id.llDatosDocumentos);
        llCredito = findViewById(R.id.llCredito);
        llPersonales = findViewById(R.id.llPersonales);
        llConyuge = findViewById(R.id.llConyuge);
        llPropiedades = findViewById(R.id.llPropiedades);
        llEconomicos = findViewById(R.id.llEconomicos);
        llNegocio = findViewById(R.id.llNegocio);
        llAval = findViewById(R.id.llAval);
        llReferencia = findViewById(R.id.llReferencia);
        //llCroquis           = findViewById(R.id.llCroquis);
        llPoliticas = findViewById(R.id.llPoliticas);
        llDocumentos = findViewById(R.id.llDocumentos);
        //==========================================================================================

        //============================== LINEAR LAYOUT  ============================================
        llCredito.setOnClickListener(llCredito_OnClick);
        llPersonales.setOnClickListener(llPersonales_OnClick);
        llConyuge.setOnClickListener(llConyuge_OnClick);
        llEconomicos.setOnClickListener(llEconomicos_OnClick);
        llNegocio.setOnClickListener(llNegocio_OnClick);
        llAval.setOnClickListener(llAval_OnClick);
        llReferencia.setOnClickListener(llReferencia_OnClick);
        //llCroquis.setOnClickListener(llCroquis_OnClick);
        llPoliticas.setOnClickListener(llPoliticas_OnClick);
        llDocumentos.setOnClickListener(llDocumentos_OnClick);
        mapCli.onCreate(savedInstanceState);
        mapNeg.onCreate(savedInstanceState);
        mapAval.onCreate(savedInstanceState);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        //==========================================================================================

        //=================================  FLOATING BUTTON  ======================================
        btnContinuar0 = findViewById(R.id.btnContinuarDatosCreditoInd);
        btnContinuar1 = findViewById(R.id.btnContinuarDatosPersonalesInd);
        btnContinuar2 = findViewById(R.id.btnContinuarDatosConyugeInd);
        btnContinuarBeni = findViewById(R.id.btnContinuarBeneficiario);
        btnContinuar3 = findViewById(R.id.btnContinuarDatosEconomicosInd);
        btnContinuar4 = findViewById(R.id.btnContinuarDatosNegocioInd);
        btnContinuar5 = findViewById(R.id.btnContinuarDatosAvalInd);
        btnContinuar6 = findViewById(R.id.btnContinuarDatosRefSolInd);
        btnContinuar7 = findViewById(R.id.btnContinuar7);
        btnContinuar8 = findViewById(R.id.btnContinuarPoliticasSolicitantes);

        btnRegresar1 = findViewById(R.id.btnRegresarDatosPersonalesInd);
        btnRegresar2 = findViewById(R.id.btnRegresarDatosConyugeInd);
        btnRegresarBeni = findViewById(R.id.btnRegresarBeneficiario);
        btnRegresar3 = findViewById(R.id.btnRegresarDatosEconomicosInd);
        btnRegresar4 = findViewById(R.id.btnRegresarDatosNegocioInd);
        btnRegresar5 = findViewById(R.id.btnRegresarDatosAvalInd);
        btnRegresar6 = findViewById(R.id.btnRegresarDatosRefSolInd);
        btnRegresar7 = findViewById(R.id.btnRegresar7);
        btnRegresar8 = findViewById(R.id.btnRegresarPoliticiasSolicitantes);
        btnRegresar9 = findViewById(R.id.btnRegresarDatosDocumentosInd);

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
        ivDownBeni = findViewById(R.id.ivDownBeni);


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
        ivUpBeni = findViewById(R.id.ivUpBeni);

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
        ivError11 = findViewById(R.id.ivError11);
        //=========================================================

        if (getIntent().getBooleanExtra("is_new", true)) {
            openRegistroCliente();
        } else {
            id_solicitud = Long.parseLong(getIntent().getStringExtra("id_solicitud"));
            initComponents(getIntent().getStringExtra("id_solicitud"));
        }

        tvRiesgo.setText(_riesgo[2]);
        Update("clasificacion_riesgo", TBL_CREDITO_IND, Miscellaneous.GetStr(tvRiesgo));
        tvDestinoNeg.setText("RE-INVERSION");
        Update("destino_credito", TBL_NEGOCIO_IND, _destino_credito[0]);

        tvDestino.setText(_destino[1]);
        Update("destino", TBL_CREDITO_IND, Miscellaneous.GetStr(tvDestino));

        //================================= FLOATING BUTTON LISTENER  ==============================
        btnContinuar0.setOnClickListener(btnContinuar0_OnClick);
        btnContinuar1.setOnClickListener(btnContinuar1_OnClick);
        btnContinuar2.setOnClickListener(btnContinuar2_OnClick);
        btnContinuar3.setOnClickListener(btnContinuar3_OnClick);
        btnContinuar4.setOnClickListener(btnContinuar4_OnClick);
        btnContinuar5.setOnClickListener(btnContinuar5_OnClick);
        btnContinuar6.setOnClickListener(btnContinuar6_OnClick);
        //btnContinuar7.setOnClickListener(btnContinuar7_OnClick);
        btnContinuar8.setOnClickListener(btnContinuar8_OnClick);
        btnContinuarBeni.setOnClickListener(btnContinuarBeni_OnClick);

        btnRegresar1.setOnClickListener(btnRegresar1_OnClick);
        btnRegresar2.setOnClickListener(btnRegresar2_OnClick);
        btnRegresar3.setOnClickListener(btnRegresar3_OnClick);
        btnRegresar4.setOnClickListener(btnRegresar4_OnClick);
        btnRegresar5.setOnClickListener(btnRegresar5_OnClick);
        btnRegresar6.setOnClickListener(btnRegresar6_OnClick);
        //btnRegresar7.setOnClickListener(btnRegresar7_OnClick);
        btnRegresar8.setOnClickListener(btnRegresar8_OnClick);
        btnRegresar9.setOnClickListener(btnRegresar9_OnClick);
        btnRegresarBeni.setOnClickListener(btnRegresarBeni_OnClick);

        dialog.dismiss();
        //================================  CREDITO LISTENER =======================================
        tvPlazo.setOnClickListener(tvPlazo_OnClick);
        tvFrecuencia.setOnClickListener(tvFrecuencia_OnClick);
        tvFechaDesembolso.setOnClickListener(tvFechaDesembolso_OnClick);
        tvHoraVisita.setOnClickListener(tvHoraVisita_OnClick);
        ibFirmaAsesor.setOnClickListener(ibFirmaAsesor_OnClick);
        etMontoPrestamo.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().contains(String.valueOf(df.getDecimalFormatSymbols().getDecimalSeparator()))) {
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
                    inilen = Miscellaneous.GetStr(etMontoPrestamo).length();
                    String v = s.toString().replace(String.valueOf(df.getDecimalFormatSymbols().getGroupingSeparator()), "");
                    Number n = df.parse(v);
                    int cp = etMontoPrestamo.getSelectionStart();
                    if (hasFractionalPart) {
                        etMontoPrestamo.setText(df.format(n));
                    } else {
                        etMontoPrestamo.setText(dfnd.format(n));
                    }
                    endlen = Miscellaneous.GetStr(etMontoPrestamo).length();
                    int sel = (cp + (endlen - inilen));
                    if (sel > 0 && sel <= Miscellaneous.GetStr(etMontoPrestamo).length()) {
                        etMontoPrestamo.setSelection(sel);
                    } else {
                        // place cursor at the end?
                        etMontoPrestamo.setSelection(Miscellaneous.GetStr(etMontoPrestamo).length() - 1);
                    }
                } catch (NumberFormatException | ParseException ignored) {
                    // do nothing?
                }

                if (s.length() > 0) {
                    Matcher matcher = pattern0.matcher(s.toString().replace(",", ""));
                    if (!matcher.matches()) {
                        tvCantidadLetra.setText("");
                        etMontoPrestamo.setError("La cantidad no corresponde a un monto de crédito válido");
                    } else {
                        Update("monto_prestamo", TBL_CREDITO_IND, s.toString().trim().replace(",", ""));
                        tvCantidadLetra.setText((Miscellaneous.cantidadLetra(s.toString().replace(",", "")).toUpperCase() + " PESOS MEXICANOS ").replace("  ", " "));
                        if (Integer.parseInt(s.toString().replace(",", "")) > 30000) {
                            llPropiedades.setVisibility(View.VISIBLE);
                        } else {
                            llPropiedades.setVisibility(View.GONE);
                        }
                    }
                } else {
                    Update("monto_prestamo", TBL_CREDITO_IND, "");
                    tvCantidadLetra.setText("");
                }

                etMontoPrestamo.addTextChangedListener(this);
            }
        });
        tvDestino.setOnClickListener(tvDestino_OnClick);
        tvRiesgo.setOnClickListener(tvRiesgo_OnClick);
        etMontoRefinanciar.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().contains(String.valueOf(df.getDecimalFormatSymbols().getDecimalSeparator()))) {
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
                } catch (NumberFormatException | ParseException ignored) {
                    // do nothing?
                }

                if (s.length() > 0) {
                    Matcher matcher = pattern1.matcher(s.toString().replace(",", ""));
                    if (!matcher.matches()) {
                        etMontoRefinanciar.setError("La cantidad no corresponde a un monto válido");
                    } else {
                        Update("monto_refinanciar", TBL_CREDITO_IND, s.toString().trim().replace(",", ""));
                    }
                }

                etMontoRefinanciar.addTextChangedListener(this);
            }
        });

        txtCampana.setOnClickListener(txtCampana_OnClick);

        txtNombreRefiero.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                /** CODE.... */
                if (editable.length() > 0) {
                    Update("nombre_refiero", TBL_DATOS_CREDITO_CAMPANA, editable.toString().trim().toUpperCase());
                } else
                    Update("nombre_refiero", TBL_DATOS_CREDITO_CAMPANA, "");

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
                if (s.length() > 0) {
                    params.put(0, s.toString());
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
                    etCurpCli.setText(Miscellaneous.GenerarCurp(params));
                } else {
                    params.put(0, "");
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
                if (s.length() > 0) {
                    params.put(0, Miscellaneous.GetStr(etNombreCli));
                    params.put(1, s.toString());
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
                    etCurpCli.setText(Miscellaneous.GenerarCurp(params));
                } else {
                    params.put(0, Miscellaneous.GetStr(etNombreCli));
                    params.put(1, "");
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
                if (s.length() > 0) {
                    params.put(0, Miscellaneous.GetStr(etNombreCli));
                    params.put(1, Miscellaneous.GetStr(etApPaternoCli));
                    params.put(2, s.toString());
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
                    etCurpCli.setText(Miscellaneous.GenerarCurp(params));
                } else {
                    params.put(0, Miscellaneous.GetStr(etNombreCli));
                    params.put(1, Miscellaneous.GetStr(etApPaternoCli));
                    params.put(2, "");
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
                    etCurpCli.setText(Miscellaneous.GenerarCurp(params));
                }
            }
        });

        tvFechaNacCli.setOnClickListener(tvFechaNac_OnClick);
        tvEstadoNacCli.setOnClickListener(etEstadoNac_OnClick);
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
                    if (s.toString().contains("Curp no válida")) {
                        tvRfcCli.setText("Rfc no válida");
                        Update("rfc", TBL_CLIENTE_IND, "");
                        Update("curp", TBL_CLIENTE_IND, "");
                    } else {
                        Update("curp", TBL_CLIENTE_IND, s.toString().trim().toUpperCase());
                        if (s.toString().trim().length() >= 10) {
                            tvRfcCli.setText(Miscellaneous.GenerarRFC(s.toString().substring(0, 10), Miscellaneous.GetStr(etNombreCli), Miscellaneous.GetStr(etApPaternoCli), Miscellaneous.GetStr(etApMaternoCli)));
                            Update("rfc", TBL_CLIENTE_IND, Miscellaneous.GetStr(tvRfcCli));
                        } else {
                            tvRfcCli.setText("");
                            Update("rfc", TBL_CLIENTE_IND, Miscellaneous.GetStr(tvRfcCli));
                        }

                    }
                } else {
                    tvRfcCli.setText("Rfc no válida");
                    Update("rfc", TBL_CLIENTE_IND, "");
                    Update("curp", TBL_CLIENTE_IND, "");
                }
            }
        });

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
                if (e.length() > 0) {
                    Update("no_identificacion", TBL_CLIENTE_IND, e.toString());
                } else
                    Update("no_identificacion", TBL_CLIENTE_IND, "");
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
                if (e.length() > 0) {
                    Update("otro_tipo_vivienda", TBL_CLIENTE_IND, e.toString());
                } else
                    Update("otro_tipo_vivienda", TBL_CLIENTE_IND, "");
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
                if (e.length() == 5) {
                    Cursor row = dBhelper.getDireccionByCP(e.toString());
                    if (row.getCount() > 0) {
                        UpdateDireccion("cp", e.toString(), direccionIdCli, "CLIENTE");
                        row.moveToFirst();
                        if (row.getCount() == 1) {
                            UpdateDireccion("colonia", row.getString(7), direccionIdCli, "CLIENTE");
                            UpdateDireccion("municipio", row.getString(4), direccionIdCli, "CLIENTE");
                            UpdateDireccion("estado", row.getString(1), direccionIdCli, "CLIENTE");
                            tvColoniaCli.setText(row.getString(7));
                            tvMunicipioCli.setText(row.getString(4));
                            tvEstadoCli.setText(row.getString(1));
                        } else {
                            if (tvColoniaCli.isEnabled() && tvColoniaCli.getText().toString().equals("")) {
                                UpdateDireccion("colonia", "", direccionIdCli, "CLIENTE");
                                tvColoniaCli.setText("");
                            }
                            UpdateDireccion("municipio", row.getString(4), direccionIdCli, "CLIENTE");
                            UpdateDireccion("estado", row.getString(1), direccionIdCli, "CLIENTE");

                            tvMunicipioCli.setText(row.getString(4));
                            tvEstadoCli.setText(row.getString(1));
                        }
                    } else {
                        UpdateDireccion("cp", "", direccionIdCli, "CLIENTE");
                        UpdateDireccion("colonia", "", direccionIdCli, "CLIENTE");
                        UpdateDireccion("municipio", "", direccionIdCli, "CLIENTE");
                        UpdateDireccion("estado", "", direccionIdCli, "CLIENTE");
                        tvColoniaCli.setText(R.string.not_found);
                        tvMunicipioCli.setText(R.string.not_found);
                        tvEstadoCli.setText(R.string.not_found);
                    }
                    row.close();
                } else {
                    UpdateDireccion("cp", "", direccionIdCli, "CLIENTE");
                    UpdateDireccion("colonia", "", direccionIdCli, "CLIENTE");
                    UpdateDireccion("municipio", "", direccionIdCli, "CLIENTE");
                    UpdateDireccion("estado", "", direccionIdCli, "CLIENTE");
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
                if (s.length() > 0) {
                    if (s.length() == 10) {
                        etTelCasaCli.setError(null);
                        Update("tel_casa", TBL_CLIENTE_IND, Miscellaneous.GetStr(etTelCasaCli));
                    } else {
                        Update("tel_casa", TBL_CLIENTE_IND, "");
                        etTelCasaCli.setError(ctx.getResources().getString(R.string.mensaje_telefono));
                    }
                } else {
                    Update("tel_casa", TBL_CLIENTE_IND, "");
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
                if (e.length() > 0) {
                    if (e.length() == 10) {
                        etCelularCli.setError(null);
                        Update("tel_celular", TBL_CLIENTE_IND, e.toString());
                    } else {
                        Update("tel_celular", TBL_CLIENTE_IND, "");
                        etCelularCli.setError(ctx.getResources().getString(R.string.mensaje_telefono));
                    }
                } else {
                    Update("tel_celular", TBL_CLIENTE_IND, "");
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
                if (e.length() > 0) {
                    if (e.length() == 10) {
                        etTelMensCli.setError(null);
                        Update("tel_mensajes", TBL_CLIENTE_IND, e.toString());
                    } else {
                        Update("tel_mensajes", TBL_CLIENTE_IND, "");
                        etTelMensCli.setError(ctx.getResources().getString(R.string.mensaje_telefono));
                    }
                } else {
                    Update("tel_mensajes", TBL_CLIENTE_IND, "");
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
                if (e.length() > 0) {
                    if (e.length() == 10) {
                        etTelTrabajoCli.setError(null);
                        Update("tel_trabajo", TBL_CLIENTE_IND, e.toString());
                    } else {
                        Update("tel_trabajo", TBL_CLIENTE_IND, "");
                        etTelTrabajoCli.setError(ctx.getResources().getString(R.string.mensaje_telefono));
                    }
                } else {
                    Update("tel_trabajo", TBL_CLIENTE_IND, "");
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
                if (!validator.validate(etTiempoSitio, new String[]{validator.REQUIRED, validator.ONLY_NUMBER, validator.YEARS})) {
                    Update("tiempo_vivir_sitio", TBL_CLIENTE_IND, e.toString());
                } else {
                    Update("tiempo_vivir_sitio", TBL_CLIENTE_IND, "0");
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
                if (!validator.validate(etEmail, new String[]{validator.EMAIL})) {
                    Update("email", TBL_CLIENTE_IND, e.toString());
                } else {
                    Update("email", TBL_CLIENTE_IND, "");
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
                    Update("ref_domiciliaria", TBL_CLIENTE_IND, e.toString());
                else
                    Update("ref_domiciliaria", TBL_CLIENTE_IND, "");
            }
        });
        ibFotoFachCli.setOnClickListener(ibFotoFachCli_OnClick);
        ibFirmaCli.setOnClickListener(ibFirmaCli_OnClick);

        //============== BENEFICIARIO ================================

        llBeneficiario.setOnClickListener(llBeneficiario_OnClik);
        //llDatosBeneficiario.setOnClickListener(llDatosBeneficiario_OnClick);
        txtParentescoBeneficiario.setOnClickListener(parentescoBeneficiario_OnClik);
        txtNomBeneficiario.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {


            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() > 0) {
                    Update("nombre", TBL_DATOS_BENEFICIARIO, editable.toString().trim().toUpperCase());
                } else
                    Update("nombre", TBL_DATOS_BENEFICIARIO, "");
            }
        });
        txtApellidoPaternoBeneficiario.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() > 0) {
                    Update("paterno", TBL_DATOS_BENEFICIARIO, editable.toString().trim().toUpperCase());
                } else
                    Update("paterno", TBL_DATOS_BENEFICIARIO, "");
            }
        });
        txtApellidoMaternoBeneficiario.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() > 0) {
                    Update("materno", TBL_DATOS_BENEFICIARIO, editable.toString().trim().toUpperCase());

                } else
                    Update("materno", TBL_DATOS_BENEFICIARIO, editable.toString().trim().toUpperCase());
            }
        });
        txtParentescoBeneficiario.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() > 0) {
                    Update("parentesco", TBL_DATOS_BENEFICIARIO, editable.toString().trim().toUpperCase());
                } else
                    Update("parentesco", TBL_DATOS_BENEFICIARIO, " ");
            }
        });

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
                    Update("nombre", TBL_CONYUGE_IND, e.toString());
                else
                    Update("nombre", TBL_CONYUGE_IND, "");
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
                    Update("paterno", TBL_CONYUGE_IND, e.toString());
                else
                    Update("paterno", TBL_CONYUGE_IND, "");
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
                if (e.length() > 0) {
                    Update("materno", TBL_CONYUGE_IND, e.toString());
                } else
                    Update("materno", TBL_CONYUGE_IND, "");
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
                if (e.length() > 0) {
                    Update("nacionalidad", TBL_CONYUGE_IND, e.toString());
                } else
                    Update("nacionalidad", TBL_CONYUGE_IND, "");
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
                if (s.length() == 5) {
                    Cursor row = dBhelper.getDireccionByCP(s.toString());
                    if (row.getCount() > 0) {
                        UpdateDireccion("cp", s.toString(), direccionIdCony, "CONYUGE");
                        row.moveToFirst();
                        if (row.getCount() == 1) {
                            UpdateDireccion("colonia", row.getString(7), direccionIdCony, "CONYUGE");
                            UpdateDireccion("municipio", row.getString(4), direccionIdCony, "CONYUGE");
                            UpdateDireccion("estado", row.getString(1), direccionIdCony, "CONYUGE");
                            tvColoniaCony.setText(row.getString(7));
                            tvMunicipioCony.setText(row.getString(4));
                            tvEstadoCony.setText(row.getString(1));
                        } else {
                            if (tvColoniaCony.isEnabled() && tvColoniaCony.getText().toString().equals("")) {
                                UpdateDireccion("colonia", "", direccionIdCony, "CONYUGE");
                                tvColoniaCony.setText("");
                            }
                            UpdateDireccion("municipio", row.getString(4), direccionIdCony, "CONYUGE");
                            UpdateDireccion("estado", row.getString(1), direccionIdCony, "CONYUGE");

                            tvMunicipioCony.setText(row.getString(4));
                            tvEstadoCony.setText(row.getString(1));
                        }
                    } else {
                        UpdateDireccion("cp", "", direccionIdCony, "CONYUGE");
                        UpdateDireccion("colonia", "", direccionIdCony, "CONYUGE");
                        UpdateDireccion("municipio", "", direccionIdCony, "CONYUGE");
                        UpdateDireccion("estado", "", direccionIdCony, "CONYUGE");
                        tvColoniaCony.setText(R.string.not_found);
                        tvMunicipioCony.setText(R.string.not_found);
                        tvEstadoCony.setText(R.string.not_found);
                    }
                    row.close();
                } else {
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
                if (s.toString().contains(String.valueOf(df.getDecimalFormatSymbols().getDecimalSeparator()))) {
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
                    inilen = Miscellaneous.GetStr(etIngMenCony).length();
                    String v = e.toString().replace(String.valueOf(df.getDecimalFormatSymbols().getGroupingSeparator()), "");
                    Number n = df.parse(v);
                    int cp = etIngMenCony.getSelectionStart();
                    if (hasFractionalPart) {
                        etIngMenCony.setText(df.format(n));
                    } else {
                        etIngMenCony.setText(dfnd.format(n));
                    }
                    endlen = Miscellaneous.GetStr(etIngMenCony).length();
                    int sel = (cp + (endlen - inilen));
                    if (sel > 0 && sel <= Miscellaneous.GetStr(etIngMenCony).length()) {
                        etIngMenCony.setSelection(sel);
                    } else {
                        // place cursor at the end?
                        etIngMenCony.setSelection(Miscellaneous.GetStr(etIngMenCony).length() - 1);
                    }
                } catch (NumberFormatException nfe) {
                    // do nothing?
                } catch (ParseException pe) {
                    // do nothing?
                }

                if (e.length() > 0)
                    Update("ing_mensual", TBL_CONYUGE_IND, e.toString().replace(",", ""));
                else
                    Update("ing_mensual", TBL_CONYUGE_IND, "");

                etIngMenCony.addTextChangedListener(this);
            }
        });
        etGastoMenCony.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().contains(String.valueOf(df.getDecimalFormatSymbols().getDecimalSeparator()))) {
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
                    inilen = Miscellaneous.GetStr(etGastoMenCony).length();
                    String v = e.toString().replace(String.valueOf(df.getDecimalFormatSymbols().getGroupingSeparator()), "");
                    Number n = df.parse(v);
                    int cp = etGastoMenCony.getSelectionStart();
                    if (hasFractionalPart) {
                        etGastoMenCony.setText(df.format(n));
                    } else {
                        etGastoMenCony.setText(dfnd.format(n));
                    }
                    endlen = Miscellaneous.GetStr(etGastoMenCony).length();
                    int sel = (cp + (endlen - inilen));
                    if (sel > 0 && sel <= Miscellaneous.GetStr(etGastoMenCony).length()) {
                        etGastoMenCony.setSelection(sel);
                    } else {
                        // place cursor at the end?
                        etGastoMenCony.setSelection(Miscellaneous.GetStr(etGastoMenCony).length() - 1);
                    }
                } catch (NumberFormatException nfe) {
                    // do nothing?
                } catch (ParseException pe) {
                    // do nothing?
                }

                if (e.length() > 0)
                    Update("gasto_mensual", TBL_CONYUGE_IND, e.toString().replace(",", ""));
                else
                    Update("gasto_mensual", TBL_CONYUGE_IND, "");

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
                if (s.length() > 0) {
                    if (s.length() == 10) {
                        Update("tel_casa", TBL_CONYUGE_IND, s.toString());
                        etCasaCony.setError(null);
                    } else {
                        Update("tel_casa", TBL_CONYUGE_IND, "");
                        etCasaCony.setError(ctx.getResources().getString(R.string.mensaje_telefono));
                    }
                } else {
                    Update("tel_casa", TBL_CONYUGE_IND, "");
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
                if (s.length() > 0) {
                    if (s.length() == 10) {
                        etCelularCony.setError(null);
                        Update("tel_celular", TBL_CONYUGE_IND, s.toString());
                    } else {
                        Update("tel_celular", TBL_CONYUGE_IND, "");
                        etCelularCony.setError(ctx.getResources().getString(R.string.mensaje_telefono));
                    }
                } else {
                    Update("tel_celular", TBL_CONYUGE_IND, "");
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
                    Update("propiedades", TBL_ECONOMICOS_IND, e.toString());
                else
                    Update("propiedades", TBL_ECONOMICOS_IND, "");
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
                    Update("valor_aproximado", TBL_ECONOMICOS_IND, e.toString());
                else
                    Update("valor_aproximado", TBL_ECONOMICOS_IND, "");
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
                    Update("ubicacion", TBL_ECONOMICOS_IND, e.toString());
                else
                    Update("ubicacion", TBL_ECONOMICOS_IND, "");
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
                    Update("nombre", TBL_NEGOCIO_IND, e.toString());
                else
                    Update("nombre", TBL_NEGOCIO_IND, "");
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
                if (e.length() == 5) {
                    Cursor row = dBhelper.getDireccionByCP(e.toString());
                    if (row.getCount() > 0) {
                        UpdateDireccion("cp", e.toString(), direccionIdNeg, "NEGOCIO");
                        row.moveToFirst();
                        if (row.getCount() == 1) {
                            UpdateDireccion("colonia", row.getString(7), direccionIdNeg, "NEGOCIO");
                            UpdateDireccion("municipio", row.getString(4), direccionIdNeg, "NEGOCIO");
                            UpdateDireccion("estado", row.getString(1), direccionIdNeg, "NEGOCIO");
                            tvColoniaNeg.setText(row.getString(7));
                            tvMunicipioNeg.setText(row.getString(4));
                            tvEstadoNeg.setText(row.getString(1));
                        } else {
                            if (tvColoniaNeg.isEnabled() && tvColoniaNeg.getText().toString().equals("")) {
                                UpdateDireccion("colonia", "", direccionIdNeg, "NEGOCIO");
                                tvColoniaNeg.setText("");
                            }

                            UpdateDireccion("municipio", row.getString(4), direccionIdNeg, "NEGOCIO");
                            UpdateDireccion("estado", row.getString(1), direccionIdNeg, "NEGOCIO");

                            tvMunicipioNeg.setText(row.getString(4));
                            tvEstadoNeg.setText(row.getString(1));
                        }
                    } else {
                        UpdateDireccion("colonia", "", direccionIdNeg, "NEGOCIO");
                        UpdateDireccion("municipio", "", direccionIdNeg, "NEGOCIO");
                        UpdateDireccion("estado", "", direccionIdNeg, "NEGOCIO");
                        tvColoniaNeg.setText(R.string.not_found);
                        tvMunicipioNeg.setText(R.string.not_found);
                        tvEstadoNeg.setText(R.string.not_found);
                    }
                    row.close();
                } else {
                    UpdateDireccion("colonia", "", direccionIdNeg, "NEGOCIO");
                    UpdateDireccion("municipio", "", direccionIdNeg, "NEGOCIO");
                    UpdateDireccion("estado", "", direccionIdNeg, "NEGOCIO");
                    tvColoniaNeg.setText(R.string.not_found);
                    tvMunicipioNeg.setText(R.string.not_found);
                    tvEstadoNeg.setText(R.string.not_found);
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
                    Update("otro_destino", TBL_NEGOCIO_IND, s.toString().trim().toUpperCase());
                else
                    Update("otro_destino", TBL_NEGOCIO_IND, "");
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
                if (e.length() > 0) {
                    if (Integer.parseInt(Miscellaneous.GetStr(etAntiguedadNeg)) > 0) {
                        Update("antiguedad", TBL_NEGOCIO_IND, e.toString());
                    } else {
                        Update("antiguedad", TBL_NEGOCIO_IND, "0");
                        etAntiguedadNeg.setError("No se permiten cantidades iguales a cero");
                    }
                } else
                    Update("antiguedad", TBL_NEGOCIO_IND, "0");
            }
        });
        etIngMenNeg.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().contains(String.valueOf(df.getDecimalFormatSymbols().getDecimalSeparator()))) {
                    hasFractionalPart = true;
                } else {
                    hasFractionalPart = false;
                }
            }

            @Override
            public void afterTextChanged(Editable e) {
                etIngMenNeg.removeTextChangedListener(this);
                etIngMenNeg.setError(null);
                ivError5.setVisibility(View.GONE);
                try {
                    int inilen, endlen;
                    inilen = Miscellaneous.GetStr(etIngMenNeg).length();
                    String v = e.toString().replace(String.valueOf(df.getDecimalFormatSymbols().getGroupingSeparator()), "");
                    Number n = df.parse(v);
                    int cp = etIngMenNeg.getSelectionStart();
                    if (hasFractionalPart) {
                        etIngMenNeg.setText(df.format(n));
                    } else {
                        etIngMenNeg.setText(dfnd.format(n));
                    }
                    endlen = Miscellaneous.GetStr(etIngMenNeg).length();
                    int sel = (cp + (endlen - inilen));
                    if (sel > 0 && sel <= Miscellaneous.GetStr(etIngMenNeg).length()) {
                        etIngMenNeg.setSelection(sel);
                    } else {
                        // place cursor at the end?
                        etIngMenNeg.setSelection(Miscellaneous.GetStr(etIngMenNeg).length() - 1);
                    }
                } catch (NumberFormatException nfe) {
                    // do nothing?
                } catch (ParseException pe) {
                    // do nothing?
                }

                if (e.length() > 0)
                    Update("ing_mensual", TBL_NEGOCIO_IND, e.toString().replace(",", ""));
                else
                    Update("ing_mensual", TBL_NEGOCIO_IND, "");

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
                if (s.toString().contains(String.valueOf(df.getDecimalFormatSymbols().getDecimalSeparator()))) {
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
                    inilen = Miscellaneous.GetStr(etOtrosIngNeg).length();
                    String v = e.toString().replace(String.valueOf(df.getDecimalFormatSymbols().getGroupingSeparator()), "");
                    Number n = df.parse(v);
                    int cp = etOtrosIngNeg.getSelectionStart();
                    if (hasFractionalPart) {
                        etOtrosIngNeg.setText(df.format(n));
                    } else {
                        etOtrosIngNeg.setText(dfnd.format(n));
                    }
                    endlen = Miscellaneous.GetStr(etOtrosIngNeg).length();
                    int sel = (cp + (endlen - inilen));
                    if (sel > 0 && sel <= Miscellaneous.GetStr(etOtrosIngNeg).length()) {
                        etOtrosIngNeg.setSelection(sel);
                    } else {
                        // place cursor at the end?
                        etOtrosIngNeg.setSelection(Miscellaneous.GetStr(etOtrosIngNeg).length() - 1);
                    }
                } catch (NumberFormatException nfe) {
                    // do nothing?
                } catch (ParseException pe) {
                    // do nothing?
                }

                if (e.length() > 0)
                    Update("ing_otros", TBL_NEGOCIO_IND, e.toString().replace(",", ""));
                else
                    Update("ing_otros", TBL_NEGOCIO_IND, "");
                MontoMaximoNeg();

                etOtrosIngNeg.addTextChangedListener(this);
            }
        });
        etGastosMenNeg.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (s.toString().contains(String.valueOf(df.getDecimalFormatSymbols().getDecimalSeparator()))) {
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
                etGastosMenNeg.setError(null);
                ivError5.setVisibility(View.GONE);
                try {
                    int inilen, endlen;
                    inilen = Miscellaneous.GetStr(etGastosMenNeg).length();
                    String v = e.toString().replace(String.valueOf(df.getDecimalFormatSymbols().getGroupingSeparator()), "");
                    Number n = df.parse(v);
                    int cp = etGastosMenNeg.getSelectionStart();
                    if (hasFractionalPart) {
                        etGastosMenNeg.setText(df.format(n));
                    } else {
                        etGastosMenNeg.setText(dfnd.format(n));
                    }
                    endlen = Miscellaneous.GetStr(etGastosMenNeg).length();
                    int sel = (cp + (endlen - inilen));
                    if (sel > 0 && sel <= Miscellaneous.GetStr(etGastosMenNeg).length()) {
                        etGastosMenNeg.setSelection(sel);
                    } else {
                        // place cursor at the end?
                        etGastosMenNeg.setSelection(Miscellaneous.GetStr(etGastosMenNeg).length() - 1);
                    }
                } catch (NumberFormatException nfe) {
                    // do nothing?
                } catch (ParseException pe) {
                    // do nothing?
                }

                if (e.length() > 0)
                    Update("gasto_semanal", TBL_NEGOCIO_IND, e.toString().replace(",", ""));
                else
                    Update("gasto_semanal", TBL_NEGOCIO_IND, "");
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
                if (s.toString().contains(String.valueOf(df.getDecimalFormatSymbols().getDecimalSeparator()))) {
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
                    inilen = Miscellaneous.GetStr(etGastosAguaNeg).length();
                    String v = e.toString().replace(String.valueOf(df.getDecimalFormatSymbols().getGroupingSeparator()), "");
                    Number n = df.parse(v);
                    int cp = etGastosAguaNeg.getSelectionStart();
                    if (hasFractionalPart) {
                        etGastosAguaNeg.setText(df.format(n));
                    } else {
                        etGastosAguaNeg.setText(dfnd.format(n));
                    }
                    endlen = Miscellaneous.GetStr(etGastosAguaNeg).length();
                    int sel = (cp + (endlen - inilen));
                    if (sel > 0 && sel <= Miscellaneous.GetStr(etGastosAguaNeg).length()) {
                        etGastosAguaNeg.setSelection(sel);
                    } else {
                        // place cursor at the end?
                        etGastosAguaNeg.setSelection(Miscellaneous.GetStr(etGastosAguaNeg).length() - 1);
                    }
                } catch (NumberFormatException nfe) {
                    // do nothing?
                } catch (ParseException pe) {
                    // do nothing?
                }

                if (e.length() > 0)
                    Update("gasto_agua", TBL_NEGOCIO_IND, e.toString().replace(",", ""));
                else
                    Update("gasto_agua", TBL_NEGOCIO_IND, "");
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
                if (s.toString().contains(String.valueOf(df.getDecimalFormatSymbols().getDecimalSeparator()))) {
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
                    inilen = Miscellaneous.GetStr(etGastosLuzNeg).length();
                    String v = e.toString().replace(String.valueOf(df.getDecimalFormatSymbols().getGroupingSeparator()), "");
                    Number n = df.parse(v);
                    int cp = etGastosLuzNeg.getSelectionStart();
                    if (hasFractionalPart) {
                        etGastosLuzNeg.setText(df.format(n));
                    } else {
                        etGastosLuzNeg.setText(dfnd.format(n));
                    }
                    endlen = Miscellaneous.GetStr(etGastosLuzNeg).length();
                    int sel = (cp + (endlen - inilen));
                    if (sel > 0 && sel <= Miscellaneous.GetStr(etGastosLuzNeg).length()) {
                        etGastosLuzNeg.setSelection(sel);
                    } else {
                        // place cursor at the end?
                        etGastosLuzNeg.setSelection(Miscellaneous.GetStr(etGastosLuzNeg).length() - 1);
                    }
                } catch (NumberFormatException nfe) {
                    // do nothing?
                } catch (ParseException pe) {
                    // do nothing?
                }

                if (e.length() > 0)
                    Update("gasto_luz", TBL_NEGOCIO_IND, e.toString().replace(",", ""));
                else
                    Update("gasto_luz", TBL_NEGOCIO_IND, "");
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
                if (s.toString().contains(String.valueOf(df.getDecimalFormatSymbols().getDecimalSeparator()))) {
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
                    inilen = Miscellaneous.GetStr(etGastosTelNeg).length();
                    String v = e.toString().replace(String.valueOf(df.getDecimalFormatSymbols().getGroupingSeparator()), "");
                    Number n = df.parse(v);
                    int cp = etGastosTelNeg.getSelectionStart();
                    if (hasFractionalPart) {
                        etGastosTelNeg.setText(df.format(n));
                    } else {
                        etGastosTelNeg.setText(dfnd.format(n));
                    }
                    endlen = Miscellaneous.GetStr(etGastosTelNeg).length();
                    int sel = (cp + (endlen - inilen));
                    if (sel > 0 && sel <= Miscellaneous.GetStr(etGastosTelNeg).length()) {
                        etGastosTelNeg.setSelection(sel);
                    } else {
                        // place cursor at the end?
                        etGastosTelNeg.setSelection(Miscellaneous.GetStr(etGastosTelNeg).length() - 1);
                    }
                } catch (NumberFormatException nfe) {
                    // do nothing?
                } catch (ParseException pe) {
                    // do nothing?
                }

                if (e.length() > 0)
                    Update("gasto_telefono", TBL_NEGOCIO_IND, e.toString().replace(",", ""));
                else
                    Update("gasto_telefono", TBL_NEGOCIO_IND, "");
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
                if (s.toString().contains(String.valueOf(df.getDecimalFormatSymbols().getDecimalSeparator()))) {
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
                    inilen = Miscellaneous.GetStr(etGastosRentaNeg).length();
                    String v = e.toString().replace(String.valueOf(df.getDecimalFormatSymbols().getGroupingSeparator()), "");
                    Number n = df.parse(v);
                    int cp = etGastosRentaNeg.getSelectionStart();
                    if (hasFractionalPart) {
                        etGastosRentaNeg.setText(df.format(n));
                    } else {
                        etGastosRentaNeg.setText(dfnd.format(n));
                    }
                    endlen = Miscellaneous.GetStr(etGastosRentaNeg).length();
                    int sel = (cp + (endlen - inilen));
                    if (sel > 0 && sel <= Miscellaneous.GetStr(etGastosRentaNeg).length()) {
                        etGastosRentaNeg.setSelection(sel);
                    } else {
                        // place cursor at the end?
                        etGastosRentaNeg.setSelection(Miscellaneous.GetStr(etGastosRentaNeg).length() - 1);
                    }
                } catch (NumberFormatException nfe) {
                    // do nothing?
                } catch (ParseException pe) {
                    // do nothing?
                }

                if (e.length() > 0)
                    Update("gasto_renta", TBL_NEGOCIO_IND, e.toString().replace(",", ""));
                else
                    Update("gasto_renta", TBL_NEGOCIO_IND, "");
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
                if (s.toString().contains(String.valueOf(df.getDecimalFormatSymbols().getDecimalSeparator()))) {
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
                    inilen = Miscellaneous.GetStr(etGastosOtrosNeg).length();
                    String v = e.toString().replace(String.valueOf(df.getDecimalFormatSymbols().getGroupingSeparator()), "");
                    Number n = df.parse(v);
                    int cp = etGastosOtrosNeg.getSelectionStart();
                    if (hasFractionalPart) {
                        etGastosOtrosNeg.setText(df.format(n));
                    } else {
                        etGastosOtrosNeg.setText(dfnd.format(n));
                    }
                    endlen = Miscellaneous.GetStr(etGastosOtrosNeg).length();
                    int sel = (cp + (endlen - inilen));
                    if (sel > 0 && sel <= Miscellaneous.GetStr(etGastosOtrosNeg).length()) {
                        etGastosOtrosNeg.setSelection(sel);
                    } else {
                        // place cursor at the end?
                        etGastosOtrosNeg.setSelection(Miscellaneous.GetStr(etGastosOtrosNeg).length() - 1);
                    }
                } catch (NumberFormatException nfe) {
                    // do nothing?
                } catch (ParseException pe) {
                    // do nothing?
                }

                if (e.length() > 0)
                    Update("gasto_otros", TBL_NEGOCIO_IND, e.toString().replace(",", ""));
                else
                    Update("gasto_otros", TBL_NEGOCIO_IND, "");
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
                    Update("otro_medio_pago", TBL_NEGOCIO_IND, e.toString().trim().toUpperCase());
                else
                    Update("otro_medio_pago", TBL_NEGOCIO_IND, "");
            }
        });
        etCapacidadPagoNeg.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int i, int i1, int i2) {
                if (s.toString().contains(String.valueOf(df.getDecimalFormatSymbols().getDecimalSeparator()))) {
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
                    inilen = Miscellaneous.GetStr(etCapacidadPagoNeg).length();
                    String v = e.toString().replace(String.valueOf(df.getDecimalFormatSymbols().getGroupingSeparator()), "");
                    Number n = df.parse(v);
                    int cp = etCapacidadPagoNeg.getSelectionStart();
                    if (hasFractionalPart) {
                        etCapacidadPagoNeg.setText(df.format(n));
                    } else {
                        etCapacidadPagoNeg.setText(dfnd.format(n));
                    }
                    endlen = Miscellaneous.GetStr(etCapacidadPagoNeg).length();
                    int sel = (cp + (endlen - inilen));
                    if (sel > 0 && sel <= Miscellaneous.GetStr(etCapacidadPagoNeg).length()) {
                        etCapacidadPagoNeg.setSelection(sel);
                    } else {
                        // place cursor at the end?
                        etCapacidadPagoNeg.setSelection(Miscellaneous.GetStr(etCapacidadPagoNeg).length() - 1);
                    }
                } catch (NumberFormatException nfe) {
                    // do nothing?
                } catch (ParseException pe) {
                    // do nothing?
                }

                if (!Miscellaneous.GetStr(tvMontoMaxNeg).isEmpty() && Double.parseDouble(Miscellaneous.GetStr(tvMontoMaxNeg).replace(",", "")) > 0) {
                    if (e.length() > 0)
                        try {
                            if (Double.parseDouble(e.toString().replace(",", "")) > 0)
                                if (Double.parseDouble(e.toString().replace(",", "")) <= Double.parseDouble(Miscellaneous.GetStr(tvMontoMaxNeg).replace(",", "")))
                                    Update("capacidad_pago", TBL_NEGOCIO_IND, e.toString().trim().replace(",", ""));
                                else
                                    ShowMensajes("EL monto no puede superar a la capacidad de pago", "NEGOCIO");

                        } catch (NumberFormatException exception) {
                            Update("capacidad_pago", TBL_NEGOCIO_IND, "");
                        }

                    else
                        Update("capacidad_pago", TBL_NEGOCIO_IND, "");
                } else {
                    ShowMensajes("Tiene que completar primero los ingresos y gastos del neogocio", "NEGOCIO");
                }

                etCapacidadPagoNeg.addTextChangedListener(this);
            }
        });
        /*etNumOperacionEfectNeg.addTextChangedListener(new TextWatcher() {
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
                            Update("num_operacion_efectivo", TBL_NEGOCIO_IND, e.toString().trim().toUpperCase());

                    }catch (NumberFormatException exception){
                        Update("num_operacion_efectivo", TBL_NEGOCIO_IND, "0");
                    }

                else
                    Update("num_operacion_efectivo", TBL_NEGOCIO_IND, "0");
            }
        });*/
        tvNumOperacionNeg.setOnClickListener(tvNumOperacionNeg_OnClick);
        etNumOperacionEfectNeg.setOnClickListener(etNumOperacionEfectNeg_OnClick);
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
                    Update("ref_domiciliaria", TBL_NEGOCIO_IND, e.toString());
                else
                    Update("ref_domiciliaria", TBL_NEGOCIO_IND, "");
            }
        });
        cbNegEnDomCli.setOnClickListener(cbNegEnDomCli_OnCheck);
        //====================================  AVAL LISTENER  =====================================
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
                if (e.length() > 0) {
                    Update("paterno", TBL_AVAL_IND, e.toString().toUpperCase());
                    params.put(0, Miscellaneous.GetStr(etNombreAval));
                    params.put(1, e.toString());
                    params.put(2, Miscellaneous.GetStr(etApMaternoAval));
                    params.put(3, Miscellaneous.GetStr(tvFechaNacAval));

                    if (rgGeneroAval.getCheckedRadioButtonId() == R.id.rbHombre)
                        params.put(4, "Hombre");
                    else if (rgGeneroAval.getCheckedRadioButtonId() == R.id.rbMujer)
                        params.put(4, "Mujer");
                    else
                        params.put(4, "");

                    if (!Miscellaneous.GetStr(tvEstadoNacAval).isEmpty())
                        params.put(5, Miscellaneous.GetStr(tvEstadoNacAval));
                    else
                        params.put(5, "");
                    etCurpAval.setText(Miscellaneous.GenerarCurp(params));
                } else {
                    Update("paterno", TBL_AVAL_IND, "");
                    params.put(0, Miscellaneous.GetStr(etNombreAval));
                    params.put(1, "");
                    params.put(2, Miscellaneous.GetStr(etApMaternoAval));
                    params.put(3, Miscellaneous.GetStr(tvFechaNacAval));

                    if (rgGeneroAval.getCheckedRadioButtonId() == R.id.rbHombre)
                        params.put(4, "Hombre");
                    else if (rgGeneroAval.getCheckedRadioButtonId() == R.id.rbMujer)
                        params.put(4, "Mujer");
                    else
                        params.put(4, "");

                    if (!Miscellaneous.GetStr(tvEstadoNacAval).isEmpty())
                        params.put(5, Miscellaneous.GetStr(tvEstadoNacAval));
                    else
                        params.put(5, "");
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
                if (e.length() > 0) {
                    Update("materno", TBL_AVAL_IND, e.toString());
                    params.put(0, Miscellaneous.GetStr(etNombreAval));
                    params.put(1, Miscellaneous.GetStr(etApPaternoAval));
                    params.put(2, e.toString());
                    params.put(3, Miscellaneous.GetStr(tvFechaNacAval));

                    if (rgGeneroAval.getCheckedRadioButtonId() == R.id.rbHombre)
                        params.put(4, "Hombre");
                    else if (rgGeneroAval.getCheckedRadioButtonId() == R.id.rbMujer)
                        params.put(4, "Mujer");
                    else
                        params.put(4, "");

                    if (!Miscellaneous.GetStr(tvEstadoNacAval).isEmpty())
                        params.put(5, Miscellaneous.GetStr(tvEstadoNacAval).trim());
                    else
                        params.put(5, "");
                    etCurpAval.setText(Miscellaneous.GenerarCurp(params));
                } else {
                    Update("materno", TBL_AVAL_IND, "");
                    params.put(0, Miscellaneous.GetStr(etNombreAval));
                    params.put(1, Miscellaneous.GetStr(etApPaternoAval));
                    params.put(2, "");
                    params.put(3, Miscellaneous.GetStr(tvFechaNacAval));

                    if (rgGeneroAval.getCheckedRadioButtonId() == R.id.rbHombre)
                        params.put(4, "Hombre");
                    else if (rgGeneroAval.getCheckedRadioButtonId() == R.id.rbMujer)
                        params.put(4, "Mujer");
                    else
                        params.put(4, "");

                    if (!Miscellaneous.GetStr(tvEstadoNacAval).isEmpty())
                        params.put(5, Miscellaneous.GetStr(tvEstadoNacAval));
                    else
                        params.put(5, "");
                    etCurpAval.setText(Miscellaneous.GenerarCurp(params));
                }
            }
        });

        //date = new DialogSelectorFecha(SolicitudCreditoInd.this,tvFechaNacAval);
        tvFechaNacAval.setOnClickListener(tvFechaNacAval_OnClick);
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
                if (e.length() > 0) {
                    Update("nombre", TBL_AVAL_IND, e.toString().trim().toUpperCase());
                    params.put(0, e.toString());
                    params.put(1, Miscellaneous.GetStr(etApPaternoAval));
                    params.put(2, Miscellaneous.GetStr(etApMaternoAval));
                    params.put(3, Miscellaneous.GetStr(tvFechaNacCli));

                    if (rgGeneroAval.getCheckedRadioButtonId() == R.id.rbHombre)
                        params.put(4, "Hombre");
                    else if (rgGeneroAval.getCheckedRadioButtonId() == R.id.rbMujer)
                        params.put(4, "Mujer");
                    else
                        params.put(4, "");

                    if (!Miscellaneous.GetStr(tvEstadoNacAval).isEmpty())
                        params.put(5, Miscellaneous.GetStr(tvEstadoNacAval));
                    else
                        params.put(5, "");
                    etCurpAval.setText(Miscellaneous.GenerarCurp(params));
                } else {
                    Update("nombre", TBL_AVAL_IND, "");
                    params.put(0, "");
                    params.put(1, Miscellaneous.GetStr(etApPaternoAval));
                    params.put(2, Miscellaneous.GetStr(etApMaternoAval));
                    params.put(3, Miscellaneous.GetStr(tvFechaNacAval));

                    if (rgGeneroAval.getCheckedRadioButtonId() == R.id.rbHombre)
                        params.put(4, "Hombre");
                    else if (rgGeneroAval.getCheckedRadioButtonId() == R.id.rbMujer)
                        params.put(4, "Mujer");
                    else
                        params.put(4, "");

                    if (!Miscellaneous.GetStr(tvEstadoNacAval).isEmpty())
                        params.put(5, Miscellaneous.GetStr(tvEstadoNacAval));
                    else
                        params.put(5, "");
                    etCurpAval.setText(Miscellaneous.GenerarCurp(params));
                }
            }
        });
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
                if (e.length() > 0) {
                    if (e.toString().contains("Curp no válida")) {
                        tvRfcAval.setText("Rfc no válida");
                        Update("rfc", TBL_AVAL_IND, "");
                        Update("curp", TBL_AVAL_IND, "");
                    } else {
                        Update("curp", TBL_AVAL_IND, e.toString().trim().toUpperCase());
                        if (e.toString().length() > 10) {
                            tvRfcAval.setText(Miscellaneous.GenerarRFC(e.toString().substring(0, 10), Miscellaneous.GetStr(etNombreAval), Miscellaneous.GetStr(etApPaternoAval), Miscellaneous.GetStr(etApMaternoAval)));
                            Update("rfc", TBL_AVAL_IND, Miscellaneous.GetStr(tvRfcAval));
                        } else {
                            tvRfcAval.setText("");
                            Update("rfc", TBL_AVAL_IND, Miscellaneous.GetStr(tvRfcAval));
                        }

                    }

                } else {
                    Update("rfc", TBL_AVAL_IND, "");
                    Update("curp", TBL_AVAL_IND, "");
                    tvRfcAval.setText("Rfc no válida");
                }
            }
        });

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
                    Update("no_identificacion", TBL_AVAL_IND, e.toString());
                else
                    Update("no_identificacion", TBL_AVAL_IND, "");
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
                if (e.length() > 0)
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
                if (e.length() == 5) {
                    Cursor row = dBhelper.getDireccionByCP(e.toString());
                    if (row.getCount() > 0) {
                        UpdateDireccion("cp", e.toString(), direccionIdAval, "AVAL");
                        row.moveToFirst();
                        if (row.getCount() == 1) {
                            UpdateDireccion("colonia", row.getString(7), direccionIdAval, "AVAL");
                            UpdateDireccion("municipio", row.getString(4), direccionIdAval, "AVAL");
                            UpdateDireccion("estado", row.getString(1), direccionIdAval, "AVAL");
                            tvColoniaAval.setText(row.getString(7));
                            tvMunicipioAval.setText(row.getString(4));
                            tvEstadoAval.setText(row.getString(1));
                        } else {
                            if (tvColoniaAval.isEnabled() && tvColoniaAval.getText().toString().equals("")) {
                                UpdateDireccion("colonia", "", direccionIdAval, "AVAL");
                                tvColoniaAval.setText("");
                            }

                            UpdateDireccion("municipio", row.getString(4), direccionIdAval, "AVAL");
                            UpdateDireccion("estado", row.getString(1), direccionIdAval, "AVAL");
                            tvMunicipioAval.setText(row.getString(4));
                            tvEstadoAval.setText(row.getString(1));
                        }
                    } else {
                        UpdateDireccion("cp", "", direccionIdAval, "AVAL");
                        UpdateDireccion("colonia", "", direccionIdAval, "AVAL");
                        UpdateDireccion("municipio", "", direccionIdAval, "AVAL");
                        UpdateDireccion("estado", "", direccionIdAval, "AVAL");
                        tvColoniaAval.setText(R.string.not_found);
                        tvMunicipioAval.setText(R.string.not_found);
                        tvEstadoAval.setText(R.string.not_found);
                    }
                    row.close();
                } else {
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
                    Update("nombre_titular", TBL_AVAL_IND, e.toString().trim().toUpperCase());
                else
                    Update("nombre_titular", TBL_AVAL_IND, "");
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
                    Update("caracteristicas_domicilio", TBL_AVAL_IND, e.toString().trim().toUpperCase());
                else
                    Update("caracteristicas_domicilio", TBL_AVAL_IND, "");
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
                    Update("nombre_negocio", TBL_AVAL_IND, e.toString().trim().toUpperCase());
                else
                    Update("nombre_negocio", TBL_AVAL_IND, "");

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
                if (e.length() > 0) {
                    if (Integer.parseInt(e.toString().trim()) > 0)
                        Update("antigueda", TBL_AVAL_IND, e.toString().trim());
                    else
                        Update("antigueda", TBL_AVAL_IND, "0");
                } else
                    Update("antigueda", TBL_AVAL_IND, "0");
            }
        });
        etIngMenAval.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                if (s.toString().contains(String.valueOf(df.getDecimalFormatSymbols().getDecimalSeparator()))) {
                    hasFractionalPart = true;
                } else {
                    hasFractionalPart = false;
                }
            }

            @Override
            public void afterTextChanged(Editable e) {
                etIngMenAval.removeTextChangedListener(this);
                etIngMenAval.setError(null);
                ivError6.setVisibility(View.GONE);
                try {
                    int inilen, endlen;
                    inilen = Miscellaneous.GetStr(etIngMenAval).length();
                    String v = e.toString().replace(String.valueOf(df.getDecimalFormatSymbols().getGroupingSeparator()), "");
                    Number n = df.parse(v);
                    int cp = etIngMenAval.getSelectionStart();
                    if (hasFractionalPart) {
                        etIngMenAval.setText(df.format(n));
                    } else {
                        etIngMenAval.setText(dfnd.format(n));
                    }
                    endlen = Miscellaneous.GetStr(etIngMenAval).length();
                    int sel = (cp + (endlen - inilen));
                    if (sel > 0 && sel <= Miscellaneous.GetStr(etIngMenAval).length()) {
                        etIngMenAval.setSelection(sel);
                    } else {
                        // place cursor at the end?
                        etIngMenAval.setSelection(Miscellaneous.GetStr(etIngMenAval).length() - 1);
                    }
                } catch (NumberFormatException nfe) {
                    // do nothing?
                } catch (ParseException pe) {
                    // do nothing?
                }

                if (e.length() > 0)
                    Update("ing_mensual", TBL_AVAL_IND, e.toString().replace(",", ""));
                else
                    Update("ing_mensual", TBL_AVAL_IND, "");

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
                if (s.toString().contains(String.valueOf(df.getDecimalFormatSymbols().getDecimalSeparator()))) {
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
                    inilen = Miscellaneous.GetStr(etIngOtrosAval).length();
                    String v = e.toString().replace(String.valueOf(df.getDecimalFormatSymbols().getGroupingSeparator()), "");
                    Number n = df.parse(v);
                    int cp = etIngOtrosAval.getSelectionStart();
                    if (hasFractionalPart) {
                        etIngOtrosAval.setText(df.format(n));
                    } else {
                        etIngOtrosAval.setText(dfnd.format(n));
                    }
                    endlen = Miscellaneous.GetStr(etIngOtrosAval).length();
                    int sel = (cp + (endlen - inilen));
                    if (sel > 0 && sel <= Miscellaneous.GetStr(etIngOtrosAval).length()) {
                        etIngOtrosAval.setSelection(sel);
                    } else {
                        // place cursor at the end?
                        etIngOtrosAval.setSelection(Miscellaneous.GetStr(etIngOtrosAval).length() - 1);
                    }
                } catch (NumberFormatException nfe) {
                    // do nothing?
                } catch (ParseException pe) {
                    // do nothing?
                }

                if (e.length() > 0)
                    Update("ing_otros", TBL_AVAL_IND, e.toString().replace(",", ""));
                else
                    Update("ing_otros", TBL_AVAL_IND, "");

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
                if (s.toString().contains(String.valueOf(df.getDecimalFormatSymbols().getDecimalSeparator()))) {
                    hasFractionalPart = true;
                } else {
                    hasFractionalPart = false;
                }
            }

            @Override
            public void afterTextChanged(Editable e) {
                etGastosSemAval.removeTextChangedListener(this);
                etGastosSemAval.setError(null);
                ivError6.setVisibility(View.GONE);
                try {
                    int inilen, endlen;
                    inilen = Miscellaneous.GetStr(etGastosSemAval).length();
                    String v = e.toString().replace(String.valueOf(df.getDecimalFormatSymbols().getGroupingSeparator()), "");
                    Number n = df.parse(v);
                    int cp = etGastosSemAval.getSelectionStart();
                    if (hasFractionalPart) {
                        etGastosSemAval.setText(df.format(n));
                    } else {
                        etGastosSemAval.setText(dfnd.format(n));
                    }
                    endlen = Miscellaneous.GetStr(etGastosSemAval).length();
                    int sel = (cp + (endlen - inilen));
                    if (sel > 0 && sel <= Miscellaneous.GetStr(etGastosSemAval).length()) {
                        etGastosSemAval.setSelection(sel);
                    } else {
                        // place cursor at the end?
                        etGastosSemAval.setSelection(Miscellaneous.GetStr(etGastosSemAval).length() - 1);
                    }
                } catch (NumberFormatException nfe) {
                    // do nothing?
                } catch (ParseException pe) {
                    // do nothing?
                }

                if (e.length() > 0)
                    Update("gasto_semanal", TBL_AVAL_IND, e.toString().trim().replace(",", ""));
                else
                    Update("gasto_semanal", TBL_AVAL_IND, "");

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
                if (s.toString().contains(String.valueOf(df.getDecimalFormatSymbols().getDecimalSeparator()))) {
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
                    inilen = Miscellaneous.GetStr(etGastosAguaAval).length();
                    String v = e.toString().replace(String.valueOf(df.getDecimalFormatSymbols().getGroupingSeparator()), "");
                    Number n = df.parse(v);
                    int cp = etGastosAguaAval.getSelectionStart();
                    if (hasFractionalPart) {
                        etGastosAguaAval.setText(df.format(n));
                    } else {
                        etGastosAguaAval.setText(dfnd.format(n));
                    }
                    endlen = Miscellaneous.GetStr(etGastosAguaAval).length();
                    int sel = (cp + (endlen - inilen));
                    if (sel > 0 && sel <= Miscellaneous.GetStr(etGastosAguaAval).length()) {
                        etGastosAguaAval.setSelection(sel);
                    } else {
                        // place cursor at the end?
                        etGastosAguaAval.setSelection(Miscellaneous.GetStr(etGastosAguaAval).length() - 1);
                    }
                } catch (NumberFormatException nfe) {
                    // do nothing?
                } catch (ParseException pe) {
                    // do nothing?
                }

                if (e.length() > 0)
                    Update("gasto_agua", TBL_AVAL_IND, e.toString().trim().replace(",", ""));
                else
                    Update("gasto_agua", TBL_AVAL_IND, "");

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
                if (s.toString().contains(String.valueOf(df.getDecimalFormatSymbols().getDecimalSeparator()))) {
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
                    inilen = Miscellaneous.GetStr(etGastosLuzAval).length();
                    String v = e.toString().replace(String.valueOf(df.getDecimalFormatSymbols().getGroupingSeparator()), "");
                    Number n = df.parse(v);
                    int cp = etGastosLuzAval.getSelectionStart();
                    if (hasFractionalPart) {
                        etGastosLuzAval.setText(df.format(n));
                    } else {
                        etGastosLuzAval.setText(dfnd.format(n));
                    }
                    endlen = Miscellaneous.GetStr(etGastosLuzAval).length();
                    int sel = (cp + (endlen - inilen));
                    if (sel > 0 && sel <= Miscellaneous.GetStr(etGastosLuzAval).length()) {
                        etGastosLuzAval.setSelection(sel);
                    } else {
                        // place cursor at the end?
                        etGastosLuzAval.setSelection(Miscellaneous.GetStr(etGastosLuzAval).length() - 1);
                    }
                } catch (NumberFormatException nfe) {
                    // do nothing?
                } catch (ParseException pe) {
                    // do nothing?
                }

                if (e.length() > 0)
                    Update("gasto_luz", TBL_AVAL_IND, e.toString().trim().replace(",", ""));
                else
                    Update("gasto_luz", TBL_AVAL_IND, "");

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
                if (s.toString().contains(String.valueOf(df.getDecimalFormatSymbols().getDecimalSeparator()))) {
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
                    inilen = Miscellaneous.GetStr(etGastosTelAval).length();
                    String v = e.toString().replace(String.valueOf(df.getDecimalFormatSymbols().getGroupingSeparator()), "");
                    Number n = df.parse(v);
                    int cp = etGastosTelAval.getSelectionStart();
                    if (hasFractionalPart) {
                        etGastosTelAval.setText(df.format(n));
                    } else {
                        etGastosTelAval.setText(dfnd.format(n));
                    }
                    endlen = Miscellaneous.GetStr(etGastosTelAval).length();
                    int sel = (cp + (endlen - inilen));
                    if (sel > 0 && sel <= Miscellaneous.GetStr(etGastosTelAval).length()) {
                        etGastosTelAval.setSelection(sel);
                    } else {
                        // place cursor at the end?
                        etGastosTelAval.setSelection(Miscellaneous.GetStr(etGastosTelAval).length() - 1);
                    }
                } catch (NumberFormatException nfe) {
                    // do nothing?
                } catch (ParseException pe) {
                    // do nothing?
                }

                if (e.length() > 0)
                    Update("gasto_telefono", TBL_AVAL_IND, e.toString().trim().replace(",", ""));
                else
                    Update("gasto_telefono", TBL_AVAL_IND, "");

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
                if (s.toString().contains(String.valueOf(df.getDecimalFormatSymbols().getDecimalSeparator()))) {
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
                    inilen = Miscellaneous.GetStr(etGastosRentaAval).length();
                    String v = e.toString().replace(String.valueOf(df.getDecimalFormatSymbols().getGroupingSeparator()), "");
                    Number n = df.parse(v);
                    int cp = etGastosRentaAval.getSelectionStart();
                    if (hasFractionalPart) {
                        etGastosRentaAval.setText(df.format(n));
                    } else {
                        etGastosRentaAval.setText(dfnd.format(n));
                    }
                    endlen = Miscellaneous.GetStr(etGastosRentaAval).length();
                    int sel = (cp + (endlen - inilen));
                    if (sel > 0 && sel <= Miscellaneous.GetStr(etGastosRentaAval).length()) {
                        etGastosRentaAval.setSelection(sel);
                    } else {
                        // place cursor at the end?
                        etGastosRentaAval.setSelection(Miscellaneous.GetStr(etGastosRentaAval).length() - 1);
                    }
                } catch (NumberFormatException nfe) {
                    // do nothing?
                } catch (ParseException pe) {
                    // do nothing?
                }

                if (e.length() > 0)
                    Update("gasto_renta", TBL_AVAL_IND, e.toString().trim().replace(",", ""));
                else
                    Update("gasto_renta", TBL_AVAL_IND, "");

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
                if (s.toString().contains(String.valueOf(df.getDecimalFormatSymbols().getDecimalSeparator()))) {
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
                    inilen = Miscellaneous.GetStr(etGastosOtrosAval).length();
                    String v = e.toString().replace(String.valueOf(df.getDecimalFormatSymbols().getGroupingSeparator()), "");
                    Number n = df.parse(v);
                    int cp = etGastosOtrosAval.getSelectionStart();
                    if (hasFractionalPart) {
                        etGastosOtrosAval.setText(df.format(n));
                    } else {
                        etGastosOtrosAval.setText(dfnd.format(n));
                    }
                    endlen = Miscellaneous.GetStr(etGastosOtrosAval).length();
                    int sel = (cp + (endlen - inilen));
                    if (sel > 0 && sel <= Miscellaneous.GetStr(etGastosOtrosAval).length()) {
                        etGastosOtrosAval.setSelection(sel);
                    } else {
                        // place cursor at the end?
                        etGastosOtrosAval.setSelection(Miscellaneous.GetStr(etGastosOtrosAval).length() - 1);
                    }
                } catch (NumberFormatException nfe) {
                    // do nothing?
                } catch (ParseException pe) {
                    // do nothing?
                }

                if (e.length() > 0)
                    Update("gasto_otros", TBL_AVAL_IND, e.toString().trim().replace(",", ""));
                else
                    Update("gasto_otros", TBL_AVAL_IND, "");

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
                    Update("otro_medio_pago", TBL_AVAL_IND, e.toString().trim().toUpperCase());
                else
                    Update("otro_medio_pago", TBL_AVAL_IND, "");
            }
        });
        etCapacidadPagoAval.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                if (s.toString().contains(String.valueOf(df.getDecimalFormatSymbols().getDecimalSeparator()))) {
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
                    inilen = Miscellaneous.GetStr(etCapacidadPagoAval).length();
                    String v = e.toString().replace(String.valueOf(df.getDecimalFormatSymbols().getGroupingSeparator()), "");
                    Number n = df.parse(v);
                    int cp = etCapacidadPagoAval.getSelectionStart();
                    if (hasFractionalPart) {
                        etCapacidadPagoAval.setText(df.format(n));
                    } else {
                        etCapacidadPagoAval.setText(dfnd.format(n));
                    }
                    endlen = Miscellaneous.GetStr(etCapacidadPagoAval).length();
                    int sel = (cp + (endlen - inilen));
                    if (sel > 0 && sel <= Miscellaneous.GetStr(etCapacidadPagoAval).length()) {
                        etCapacidadPagoAval.setSelection(sel);
                    } else {
                        // place cursor at the end?
                        etCapacidadPagoAval.setSelection(Miscellaneous.GetStr(etGastosRentaAval).length() - 1);
                    }
                } catch (NumberFormatException nfe) {
                    // do nothing?
                } catch (ParseException pe) {
                    // do nothing?
                }

                if (!Miscellaneous.GetStr(tvMontoMaxAval).isEmpty() && Double.parseDouble(Miscellaneous.GetStr(tvMontoMaxAval).replace(",", "")) > 0) {
                    if (e.length() > 0)
                        try {
                            if (Double.parseDouble(e.toString().replace(",", "")) > 0)
                                if (Double.parseDouble(e.toString().replace(",", "")) <= Double.parseDouble(Miscellaneous.GetStr(tvMontoMaxAval).replace(",", "")))
                                    Update("capacidad_pagos", TBL_AVAL_IND, e.toString().trim().replace(",", ""));
                                else
                                    ShowMensajes("EL monto no puede superar a la capacidad de pago", "AVAL");

                        } catch (NumberFormatException exception) {
                            Update("capacidad_pagos", TBL_AVAL_IND, "");
                        }

                    else
                        Update("capacidad_pagos", TBL_AVAL_IND, "");
                } else {
                    ShowMensajes("Tiene que completar primero los ingresos y gastos del aval", "AVAL");
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
                if (e.length() > 0) {
                    if (e.length() == 10) {
                        etTelCasaAval.setError(null);
                        Update("tel_casa", TBL_AVAL_IND, e.toString().trim());
                    } else {
                        Update("tel_casa", TBL_AVAL_IND, "");
                        etTelCasaAval.setError(ctx.getResources().getString(R.string.mensaje_telefono));
                    }
                } else {
                    Update("tel_casa", TBL_AVAL_IND, "");
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
                if (e.length() > 0) {
                    if (e.length() == 10) {
                        etCelularAval.setError(null);
                        Update("tel_celular", TBL_AVAL_IND, e.toString().trim());
                    } else {
                        Update("tel_casa", TBL_AVAL_IND, "");
                        etCelularAval.setError(ctx.getResources().getString(R.string.mensaje_telefono));
                    }
                } else {
                    Update("tel_casa", TBL_AVAL_IND, "");
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
                if (e.length() > 0) {
                    if (e.length() == 10) {
                        etTelMensAval.setError(null);
                        Update("tel_mensajes", TBL_AVAL_IND, e.toString());
                    } else {
                        Update("tel_mensajes", TBL_AVAL_IND, "");
                        etTelMensAval.setError(ctx.getResources().getString(R.string.mensaje_telefono));
                    }
                } else {
                    Update("tel_mensajes", TBL_AVAL_IND, "");
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
                if (e.length() > 0) {
                    if (e.length() == 10) {
                        etTelTrabajoAval.setError(null);
                        Update("tel_trabajo", TBL_AVAL_IND, e.toString());
                    } else {
                        Update("tel_trabajo", TBL_AVAL_IND, "");
                        etTelTrabajoAval.setError(ctx.getResources().getString(R.string.mensaje_telefono));
                    }
                } else {
                    Update("tel_trabajo", TBL_AVAL_IND, "");
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
                if (!validator.validate(etEmail, new String[]{validator.EMAIL})) {
                    Update("email", TBL_AVAL_IND, e.toString());
                } else {
                    Update("email", TBL_AVAL_IND, "");
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
                    Update("ref_domiciliaria", TBL_AVAL_IND, e.toString().trim().toUpperCase());
                else
                    Update("ref_domiciliaria", TBL_AVAL_IND, "");
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
                    Update("nombre", TBL_REFERENCIA_IND, e.toString().trim().toUpperCase());
                else
                    Update("nombre", TBL_REFERENCIA_IND, "");
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
                    Update("paterno", TBL_REFERENCIA_IND, e.toString().trim().toUpperCase());
                else
                    Update("paterno", TBL_REFERENCIA_IND, "");
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
                    Update("materno", TBL_REFERENCIA_IND, e.toString().trim().toUpperCase());
                else
                    Update("materno", TBL_REFERENCIA_IND, "");
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
                if (e.length() == 5) {
                    Cursor row = dBhelper.getDireccionByCP(e.toString());
                    if (row.getCount() > 0) {
                        UpdateDireccion("cp", e.toString(), direccionIdRef, "REFERENCIA");
                        row.moveToFirst();
                        if (row.getCount() == 1) {
                            UpdateDireccion("colonia", row.getString(7), direccionIdRef, "REFERENCIA");
                            UpdateDireccion("municipio", row.getString(4), direccionIdRef, "REFERENCIA");
                            UpdateDireccion("estado", row.getString(1), direccionIdRef, "REFERENCIA");
                            tvColoniaRef.setText(row.getString(7));
                            tvMunicipioRef.setText(row.getString(4));
                            tvEstadoRef.setText(row.getString(1));
                        } else {
                            if (tvColoniaRef.isEnabled() && tvColoniaRef.getText().toString().equals("")) {
                                UpdateDireccion("colonia", "", direccionIdRef, "REFERENCIA");
                                tvColoniaRef.setText("");
                            }

                            UpdateDireccion("municipio", row.getString(4), direccionIdRef, "REFERENCIA");
                            UpdateDireccion("estado", row.getString(1), direccionIdRef, "REFERENCIA");

                            tvMunicipioRef.setText(row.getString(4));
                            tvEstadoRef.setText(row.getString(1));
                        }
                    } else {
                        UpdateDireccion("cp", "", direccionIdRef, "REFERENCIA");
                        UpdateDireccion("colonia", "", direccionIdRef, "REFERENCIA");
                        UpdateDireccion("municipio", "", direccionIdRef, "REFERENCIA");
                        UpdateDireccion("estado", "", direccionIdRef, "REFERENCIA");
                        tvColoniaRef.setText(R.string.not_found);
                        tvMunicipioRef.setText(R.string.not_found);
                        tvEstadoRef.setText(R.string.not_found);
                    }
                    row.close();
                } else {
                    UpdateDireccion("cp", "", direccionIdRef, "REFERENCIA");
                    UpdateDireccion("colonia", "", direccionIdRef, "REFERENCIA");
                    UpdateDireccion("municipio", "", direccionIdRef, "REFERENCIA");
                    UpdateDireccion("estado", "", direccionIdRef, "REFERENCIA");
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
                if (e.length() > 0) {
                    if (e.length() == 10) {
                        etTelCelRef.setError(null);
                        Update("tel_celular", TBL_REFERENCIA_IND, e.toString().trim());
                    } else {
                        Update("tel_celular", TBL_REFERENCIA_IND, "");
                        etTelCelRef.setError(ctx.getResources().getString(R.string.mensaje_telefono));
                    }
                } else {
                    Update("tel_celular", TBL_REFERENCIA_IND, "");
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
                    Update("referencias", TBL_CROQUIS_IND, e.toString().trim().toUpperCase());
                else
                    Update("referencias", TBL_CROQUIS_IND, "");
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
                    Update("caracteristicas_domicilio", TBL_CROQUIS_IND, e.toString().trim().toUpperCase());
                else
                    Update("caracteristicas_domicilio", TBL_CROQUIS_IND, "");
            }
        });
        //================================  ESCANEAR DOCUMENTOS  ===================================
        ibIneFrontal.setOnClickListener(ibIneFrontal_OnClick);
        ibIneReverso.setOnClickListener(ibIneReverso_OnClick);
        ibCurp.setOnClickListener(ibCurp_OnClick);
        ibComprobante.setOnClickListener(ibComprobante_OnClick);
        ibIneSelfie.setOnClickListener(ibIneSelfie_OnClick);
        ibComprobanteGarantia.setOnClickListener(ibComprobanteGarantia_OnClick);
        ibIneFrontalAval.setOnClickListener(ibIneFrontalAval_OnClick);
        ibIneReversoAval.setOnClickListener(ibIneReversoAval_OnClick);
        ibCurpAval.setOnClickListener(ibCurpAval_OnClick);
        ibComprobanteAval.setOnClickListener(ibComprobanteAval_OnClick);
        //================================  CLIENTE GENERO LISTENER  ===============================
        rgGeneroCli.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (isEditCli || modoSuperUsuario) {
                    tvGeneroCli.setError(null);
                    HashMap<Integer, String> params = new HashMap<>();
                    if (checkedId == R.id.rbHombre) {

                        params.put(0, Miscellaneous.GetStr(etNombreCli));
                        params.put(1, Miscellaneous.GetStr(etApPaternoCli));
                        params.put(2, Miscellaneous.GetStr(etApMaternoCli));
                        params.put(3, Miscellaneous.GetStr(tvFechaNacCli));
                        params.put(4, "Hombre");

                        if (!Miscellaneous.GetStr(tvEstadoNacCli).isEmpty())
                            params.put(5, Miscellaneous.GetStr(tvEstadoNacCli));
                        else
                            params.put(5, "");
                        etCurpCli.setText(Miscellaneous.GenerarCurp(params));
                        Update("genero", TBL_CLIENTE_IND, "0");
                    } else if (checkedId == R.id.rbMujer) {
                        params.put(0, Miscellaneous.GetStr(etNombreCli));
                        params.put(1, Miscellaneous.GetStr(etApPaternoCli));
                        params.put(2, Miscellaneous.GetStr(etApMaternoCli));
                        params.put(3, Miscellaneous.GetStr(tvFechaNacCli));
                        params.put(4, "Mujer");

                        if (!Miscellaneous.GetStr(tvEstadoNacCli).isEmpty())
                            params.put(5, Miscellaneous.GetStr(tvEstadoNacCli));
                        else
                            params.put(5, "");
                        etCurpCli.setText(Miscellaneous.GenerarCurp(params));
                        Update("genero", TBL_CLIENTE_IND, "1");
                    } else {
                        params.put(0, Miscellaneous.GetStr(etNombreCli));
                        params.put(1, Miscellaneous.GetStr(etApPaternoCli));
                        params.put(2, Miscellaneous.GetStr(etApMaternoCli));
                        params.put(3, Miscellaneous.GetStr(tvFechaNacCli));
                        params.put(4, "");

                        if (!Miscellaneous.GetStr(tvEstadoNacCli).isEmpty())
                            params.put(5, Miscellaneous.GetStr(tvEstadoNacCli));
                        else
                            params.put(5, "");
                        etCurpCli.setText(Miscellaneous.GenerarCurp(params));
                        Update("genero", TBL_CLIENTE_IND, "2");
                    }
                }
            }
        });
        rgBienes.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (isEditCli || modoSuperUsuario) {
                    tvBienes.setError(null);
                    if (checkedId == R.id.rbMancomunados) {
                        Update("bienes", TBL_CLIENTE_IND, "1");
                    } else if (checkedId == R.id.rbSeparados) {
                        Update("bienes", TBL_CLIENTE_IND, "2");
                    }
                }
            }
        });
        //===========================  AVAL GENERO LISTENER  =======================================
        rgGeneroAval.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (isEditAva || modoSuperUsuario) {
                    tvGeneroAval.setError(null);
                    if (checkedId == R.id.rbHombre) {
                        HashMap<Integer, String> params = new HashMap<>();
                        params.put(0, Miscellaneous.GetStr(etNombreAval));
                        params.put(1, Miscellaneous.GetStr(etApPaternoAval));
                        params.put(2, Miscellaneous.GetStr(etApMaternoAval));
                        params.put(3, Miscellaneous.GetStr(tvFechaNacAval));
                        params.put(4, "Hombre");

                        if (!Miscellaneous.GetStr(tvEstadoNacAval).isEmpty())
                            params.put(5, Miscellaneous.GetStr(tvEstadoNacAval));
                        else
                            params.put(5, "");
                        Update("genero", TBL_AVAL_IND, "0");
                        etCurpAval.setText(Miscellaneous.GenerarCurp(params));
                    } else if (checkedId == R.id.rbMujer) {
                        HashMap<Integer, String> params = new HashMap<>();
                        params.put(0, Miscellaneous.GetStr(etNombreAval));
                        params.put(1, Miscellaneous.GetStr(etApPaternoAval));
                        params.put(2, Miscellaneous.GetStr(etApMaternoAval));
                        params.put(3, Miscellaneous.GetStr(tvFechaNacAval));
                        params.put(4, "Mujer");

                        if (!Miscellaneous.GetStr(tvEstadoNacAval).isEmpty())
                            params.put(5, Miscellaneous.GetStr(tvEstadoNacAval));
                        else
                            params.put(5, "");

                        Update("genero", TBL_AVAL_IND, "1");
                        etCurpAval.setText(Miscellaneous.GenerarCurp(params));
                    } else {
                        HashMap<Integer, String> params = new HashMap<>();
                        params.put(0, Miscellaneous.GetStr(etNombreAval));
                        params.put(1, Miscellaneous.GetStr(etApPaternoAval));
                        params.put(2, Miscellaneous.GetStr(etApMaternoAval));
                        params.put(3, Miscellaneous.GetStr(tvFechaNacAval));
                        params.put(4, "");

                        if (!Miscellaneous.GetStr(tvEstadoNacAval).isEmpty())
                            params.put(5, Miscellaneous.GetStr(tvEstadoNacAval));
                        else
                            params.put(5, "");

                        Update("genero", TBL_AVAL_IND, "2");
                        etCurpAval.setText(Miscellaneous.GenerarCurp(params));
                    }
                }
            }
        });

        rgNegocioAval.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (isEditAva || modoSuperUsuario) {
                    tvNombreNegAval.setError(null);
                    if (checkedId == R.id.rbSiNeg) {
                        llNombreNegocio.setVisibility(View.VISIBLE);
                        etNombreNegocioAval.setText("");
                        Update("tiene_negocio", TBL_AVAL_IND, "1");
                        Update("nombre_negocio", TBL_AVAL_IND, "");
                    } else if (checkedId == R.id.rbNoNeg) {
                        llNombreNegocio.setVisibility(View.GONE);
                        Update("tiene_negocio", TBL_AVAL_IND, "2");
                    }
                }
            }
        });
        //=============================  POLITICAS LISTENER  =======================================
        rgPropietarioReal.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (isEditPol || modoSuperUsuario) {
                    tvPropietarioReal.setError(null);
                    if (checkedId == R.id.rbSiPropietario) {
                        tvAnexoPropietario.setVisibility(View.VISIBLE);
                        Update("propietario_real", TBL_POLITICAS_PLD_IND, "1");
                    } else if (checkedId == R.id.rbNoPropietario) {
                        tvAnexoPropietario.setVisibility(View.GONE);
                        Update("propietario_real", TBL_POLITICAS_PLD_IND, "2");
                    }
                }
            }
        });
        rgProveedor.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (isEditPol || modoSuperUsuario) {
                    tvProvedor.setError(null);
                    if (checkedId == R.id.rbSiProveedor) {
                        tvAnexoPreveedor.setVisibility(View.VISIBLE);
                        Update("proveedor_recursos", TBL_POLITICAS_PLD_IND, "1");
                    } else if (checkedId == R.id.rbNoProveedor) {
                        tvAnexoPreveedor.setVisibility(View.GONE);
                        Update("proveedor_recursos", TBL_POLITICAS_PLD_IND, "2");
                    }
                }
            }
        });
        rgPoliticamenteExp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (isEditPol || modoSuperUsuario) {
                    tvPoliticamenteExp.setError(null);
                    if (checkedId == R.id.rbSiExpuesta) {
                        tvAnexoPoliticamenteExp.setVisibility(View.VISIBLE);
                        Update("persona_politica", TBL_POLITICAS_PLD_IND, "1");
                    } else if (checkedId == R.id.rbNoexpuesta) {
                        tvAnexoPoliticamenteExp.setVisibility(View.GONE);
                        Update("persona_politica", TBL_POLITICAS_PLD_IND, "2");
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
        ivCurp.setOnClickListener(ivCurp_OnClick);
        ivComprobante.setOnClickListener(ivComprobante_OnClick);
        ivFirmaAsesor.setOnClickListener(ivFirmaAsesor_OnClick);
        ivIneSelfie.setOnClickListener(ivIneSelfie_OnClik);
        ivComprobanteGarantia.setOnClickListener(ivComprobanteGarantia_OnClick);
        ivIneFrontalAval.setOnClickListener(ivIneFrontalAval_OnClik);
        ivIneReversoAval.setOnClickListener(ivIneReversoAval_OnClick);
        ivCurpAval.setOnClickListener(ivCurpAval_OnClick);
        ivComprobanteAval.setOnClickListener(ivComprobanteAval_OnClick);
        //sendBeneficiarioOrininacionInd();

       /* Log.e("EVENTO", "AGREGANDO EVENTO AL BUTTON DE REGISTRO EN BENEFICIARIO");
        btnRegistrar.setOnClickListener((View view) -> {
            Log.e("AQUI", "TOUCH BUTTON");
            enviarBeneficiario(id_solicitud);
        });*/

    }

    private View.OnClickListener ibFirmaAsesor_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i_firma_asesor = new Intent(context, CapturarFirma.class);
            i_firma_asesor.putExtra(TIPO, "");
            startActivityForResult(i_firma_asesor, REQUEST_CODE_FIRMA_ASESOR);
        }
    };

    private View.OnClickListener tvPlazo_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (isEditCre || modoSuperUsuario) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle(R.string.selected_option)
                        .setItems(R.array.intervalo, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int position) {
                                tvPlazo.setError(null);
                                tvPlazo.setText(_plazo[position]);
                                Update("plazo", TBL_CREDITO_IND, Miscellaneous.GetStr(tvPlazo));
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
            if (isEditCre || modoSuperUsuario) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle(R.string.selected_option)
                        .setItems(R.array.lapso, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int position) {
                                tvFrecuencia.setError(null);
                                tvFrecuencia.setText(_frecuencia[position]);
                                Update("periodicidad", TBL_CREDITO_IND, Miscellaneous.GetStr(tvFrecuencia));
                                switch (position) {
                                    case 0:
                                        tvNumOperacionNeg.setText("4");
                                        Update("num_operacion_mensuales", TBL_NEGOCIO_IND, "4");
                                        break;
                                    case 1:
                                    case 2:
                                        tvNumOperacionNeg.setText("2");
                                        Update("num_operacion_mensuales", TBL_NEGOCIO_IND, "2");
                                        break;
                                    case 3:
                                        tvNumOperacionNeg.setText("1");
                                        Update("num_operacion_mensuales", TBL_NEGOCIO_IND, "1");
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
            if (isEditCre || modoSuperUsuario) {
                dialog_date_picker dialogDatePicker = new dialog_date_picker();
                Bundle b = new Bundle();

                /*b.putInt(YEAR_CURRENT, myCalendar.get(Calendar.YEAR));
                b.putInt(MONTH_CURRENT, myCalendar.get(Calendar.MONTH));
                b.putInt(DAY_CURRENT, myCalendar.get(Calendar.DAY_OF_MONTH));*/
                b.putInt(Constants.YEAR_CURRENT, ((Miscellaneous.GetStr(tvFechaDesembolso).isEmpty()) ? myCalendar.get(Calendar.YEAR) : Integer.parseInt(Miscellaneous.GetStr(tvFechaDesembolso).substring(0, 4))));
                b.putInt(Constants.MONTH_CURRENT, ((Miscellaneous.GetStr(tvFechaDesembolso).isEmpty()) ? myCalendar.get(Calendar.MONTH) : (Integer.parseInt(Miscellaneous.GetStr(tvFechaDesembolso).substring(5, 7)) - 1)));
                b.putInt(Constants.DAY_CURRENT, ((Miscellaneous.GetStr(tvFechaDesembolso).isEmpty()) ? myCalendar.get(Calendar.DAY_OF_MONTH) : Integer.parseInt(Miscellaneous.GetStr(tvFechaDesembolso).substring(8, 10))));
                b.putString(DATE_CURRENT, sdf.format(myCalendar.getTime()));
                b.putInt(IDENTIFIER, 1);
                b.putBoolean(FECHAS_POST, true);
                dialogDatePicker.setArguments(b);
                dialogDatePicker.show(getSupportFragmentManager(), NameFragments.DIALOGDATEPICKER);
            }
        }
    };

    private View.OnClickListener tvDestino_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (isEditCre || modoSuperUsuario) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle(R.string.selected_option)
                        .setItems(R.array.destino_prestamo, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int position) {
                                tvDestino.setError(null);
                                tvDestino.setText(_destino[position]);
                                Update("destino", TBL_CREDITO_IND, Miscellaneous.GetStr(tvDestino));
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
            if (isEditCre || modoSuperUsuario) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle(R.string.selected_option)
                        .setItems(R.array.clasificacion_riesgo, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int position) {
                                tvRiesgo.setError(null);
                                tvRiesgo.setText(_riesgo[position]);
                                Update("clasificacion_riesgo", TBL_CREDITO_IND, Miscellaneous.GetStr(tvRiesgo));
                            }
                        });
                builder.create();
                builder.show();
            }
        }
    };

    private View.OnClickListener txtCampana_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent i_campanas = new Intent(context, Catalogos.class);
            i_campanas.putExtra(TITULO, Miscellaneous.ucFirst(CAMPANAS));
            i_campanas.putExtra(CATALOGO, CAMPANAS);
            i_campanas.putExtra(REQUEST_CODE, REQUEST_CODE_CAMAPANAS);
            startActivityForResult(i_campanas, REQUEST_CODE_CAMAPANAS);
        }
    };

    private View.OnClickListener tvFechaNac_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (isEditCli || modoSuperUsuario) {
                //dialog_date_picker dialogDatePicker = new dialog_date_picker();
                //Bundle b = new Bundle();

                int dato1 = 1;

                date = new DialogSelectorFecha(SolicitudCreditoInd.this, tvFechaNacCli, dato1, ind);

                String fechaMostrada = tvFechaNacCli.getText().toString().trim();
                if (!fechaMostrada.isEmpty()) {
                    date.recuperarFecha(fechaMostrada);
                } else {
                    date.showDatePickerDialog();
                }
              /*b.putInt(YEAR_CURRENT, myCalendar.get(Calendar.YEAR));
                b.putInt(MONTH_CURRENT, myCalendar.get(Calendar.MONTH));
                b.putInt(DAY_CURRENT, myCalendar.get(Calendar.DAY_OF_MONTH));*/

               /* b.putInt(Constants.YEAR_CURRENT, ((Miscellaneous.GetStr(tvFechaNacCli).isEmpty()) ? myCalendar.get(Calendar.YEAR) : Integer.parseInt(Miscellaneous.GetStr(tvFechaNacCli).substring(0, 4))));
                b.putInt(Constants.MONTH_CURRENT, ((Miscellaneous.GetStr(tvFechaNacCli).isEmpty()) ? myCalendar.get(Calendar.MONTH) : (Integer.parseInt(Miscellaneous.GetStr(tvFechaNacCli).substring(5, 7)) - 1)));
                b.putInt(Constants.DAY_CURRENT, ((Miscellaneous.GetStr(tvFechaNacCli).isEmpty()) ? myCalendar.get(Calendar.DAY_OF_MONTH) : Integer.parseInt(Miscellaneous.GetStr(tvFechaNacCli).substring(8, 10))));
                b.putString(DATE_CURRENT, sdf.format(myCalendar.getTime()));
                b.putInt(IDENTIFIER, 2);
                b.putBoolean(FECHAS_POST, false);
                dialogDatePicker.setArguments(b);
                dialogDatePicker.show(getSupportFragmentManager(), NameFragments.DIALOGDATEPICKER);*/

            }
        }
    };

    private View.OnClickListener ibFirmaCli_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i_firma_cli = new Intent(context, CapturarFirma.class);
            i_firma_cli.putExtra(TIPO, "CLIENTE");
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
            //RESULTADO = 123;
            //openImagePicker();
            Intent i = new Intent(SolicitudCreditoInd.this, CameraVertical.class);
            i.putExtra(ORDER_ID, "SC_cliente");
            startActivityForResult(i, REQUEST_CODE_CAMARA_FACHADA_CLI);
        }
    };

    private View.OnClickListener etEstadoNac_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (isEditCli || modoSuperUsuario) {
                Intent i_estados = new Intent(context, Catalogos.class);
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
            if (isEditCli || tvColoniaCli.getText().toString().equals("") || modoSuperUsuario) {
                Intent i_colonia = new Intent(context, Catalogos.class);
                i_colonia.putExtra(TITULO, Miscellaneous.ucFirst(ESTADOS));
                i_colonia.putExtra(CATALOGO, COLONIAS);
                i_colonia.putExtra(EXTRA, Miscellaneous.GetStr(etCpCli));
                i_colonia.putExtra(REQUEST_CODE, REQUEST_CODE_COLONIA_CLIE);
                startActivityForResult(i_colonia, REQUEST_CODE_COLONIA_CLIE);
            }
        }
    };

    private View.OnClickListener tvLocalidadCli_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (isEditCli || modoSuperUsuario) {
                ColoniaDao coloniaDao = new ColoniaDao(ctx);
                List<Colonia> colonias = coloniaDao.findAllByCp(etCpCli.getText().toString().trim());

                if (!Miscellaneous.GetStr(tvMunicipioCli).isEmpty() && colonias.size() > 0) {
                    Cursor row = dBhelper.getRecords(TABLE_MUNICIPIOS, " WHERE municipio_id = ?", "", new String[]{String.valueOf(colonias.get(0).getMunicipioId())});
                    //row = dBhelper.getRecords(TBL_SUCURSALES_LOCALIDADES, " WHERE id_municipio = ? "," ",new String[]{String.valueOf(colonias.get(0).getMunicipioId())});
                    //row = dBhelper.getRecordsData(TBL_SUCURSALES_LOCALIDADES," WHERE id_municipio = ? ", " ORDER BY localidad ASC LIMIT 1", new String[]{String.valueOf(colonias.get(0).getMunicipioId())});

                    row.moveToFirst();
                    Intent i_localidad = new Intent(context, Catalogos.class);
                    i_localidad.putExtra(TITULO, Miscellaneous.ucFirst(LOCALIDADES));
                    i_localidad.putExtra(CATALOGO, LOCALIDADES);
                    i_localidad.putExtra(EXTRA, row.getString(1));
                    i_localidad.putExtra(REQUEST_CODE, REQUEST_CODE_LOCALIDAD_CLIE);
                    startActivityForResult(i_localidad, REQUEST_CODE_LOCALIDAD_CLIE);
                } else {
                    final AlertDialog solicitud = Popups.showDialogMessage(context, warning,
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
            if (isEditCli || modoSuperUsuario) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle(R.string.selected_option)
                        .setItems(_estudios, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int position) {
                                tvEstudiosCli.setError(null);
                                tvEstudiosCli.setText(_estudios[position]);
                                tvEstudiosCli.requestFocus();
                                Update("nivel_estudio", TBL_CLIENTE_IND, Miscellaneous.GetStr(tvEstudiosCli));
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
            if (isEditCli || modoSuperUsuario) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
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
                                Update("estado_civil", TBL_CLIENTE_IND, Miscellaneous.GetStr(tvEstadoCivilCli));
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
            if (isEditCli || modoSuperUsuario) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle(R.string.selected_option)
                        .setItems(_tipo_casa, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int position) {
                                tvTipoCasaCli.setError(null);
                                tvTipoCasaCli.setText(_tipo_casa[position]);
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
                                Update("tipo_vivienda", TBL_CLIENTE_IND, Miscellaneous.GetStr(tvTipoCasaCli));
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
            if (isEditCli || modoSuperUsuario) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle(R.string.selected_option)
                        .setItems(_parentesco, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int position) {
                                tvCasaFamiliar.setError(null);
                                tvCasaFamiliar.setText(_parentesco[position]);
                                Update("parentesco", TBL_CLIENTE_IND, Miscellaneous.GetStr(tvCasaFamiliar));
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
            if (isEditCli || modoSuperUsuario) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle(R.string.selected_option)
                        .setItems(R.array.dependientes_eco, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int position) {
                                tvDependientes.setError(null);
                                tvDependientes.setText(_dependientes[position]);
                                Update("dependientes", TBL_CLIENTE_IND, Miscellaneous.GetStr(tvDependientes));
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
            if (isEditCli || modoSuperUsuario) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle(R.string.selected_option)
                        .setItems(R.array.confirmacion, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int position) {
                                tvEstadoCuenta.setError(null);
                                tvEstadoCuenta.setText(_confirmacion[position]);
                                Update("estado_cuenta", TBL_CLIENTE_IND, Miscellaneous.GetStr(tvEstadoCuenta));
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
            if (isEditCli || modoSuperUsuario) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle(R.string.selected_option)
                        .setItems(_medio_contacto, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int position) {
                                tvEnteroNosotros.setError(null);
                                tvEnteroNosotros.setText(_medio_contacto[position]);
                                Update("medio_contacto", TBL_CLIENTE_IND, Miscellaneous.GetStr(tvEnteroNosotros));
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
            if (isEditAva || tvColoniaAval.getText().toString().equals("") || modoSuperUsuario) {
                Intent i_colonia = new Intent(context, Catalogos.class);
                i_colonia.putExtra(TITULO, Miscellaneous.ucFirst(ESTADOS));
                i_colonia.putExtra(CATALOGO, COLONIAS);
                i_colonia.putExtra(EXTRA, Miscellaneous.GetStr(etCpAval));
                i_colonia.putExtra(REQUEST_CODE, REQUEST_CODE_COLONIA_AVAL);
                startActivityForResult(i_colonia, REQUEST_CODE_COLONIA_AVAL);
            }
        }
    };

    private View.OnClickListener tvLocalidadAval_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (isEditAva || modoSuperUsuario) {
                ColoniaDao coloniaDao = new ColoniaDao(ctx);
                List<Colonia> colonias = coloniaDao.findAllByCp(etCpAval.getText().toString().trim());

                if (!Miscellaneous.GetStr(tvMunicipioAval).isEmpty() && colonias.size() > 0) {
                    Cursor row = dBhelper.getRecords(TABLE_MUNICIPIOS, " WHERE municipio_id = ?", "", new String[]{String.valueOf(colonias.get(0).getMunicipioId())});
                    row.moveToFirst();
                    Intent i_localidad = new Intent(context, Catalogos.class);
                    i_localidad.putExtra(TITULO, Miscellaneous.ucFirst(LOCALIDADES));
                    i_localidad.putExtra(CATALOGO, LOCALIDADES);
                    i_localidad.putExtra(EXTRA, row.getString(1));
                    i_localidad.putExtra(REQUEST_CODE, REQUEST_CODE_LOCALIDAD_AVAL);
                    startActivityForResult(i_localidad, REQUEST_CODE_LOCALIDAD_AVAL);
                } else {
                    final AlertDialog solicitud = Popups.showDialogMessage(context, warning,
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
            if (isEditAva || modoSuperUsuario) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
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
                                Update("tipo_vivienda", TBL_AVAL_IND, Miscellaneous.GetStr(tvTipoCasaAval));
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
            if (isEditAva || modoSuperUsuario) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle(R.string.selected_option)
                        .setItems(_parentesco, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int position) {
                                tvParentescoAval.setError(null);
                                tvParentescoAval.setText(_parentesco[position]);
                                Update("parentesco_cliente", TBL_AVAL_IND, Miscellaneous.GetStr(tvParentescoAval));
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
            if (isEditAva || modoSuperUsuario) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle(R.string.selected_option)
                        .setItems(_parentesco_casa, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int position) {
                                tvFamiliarAval.setError(null);
                                tvFamiliarAval.setText(_parentesco_casa[position]);
                                Update("parentesco", TBL_AVAL_IND, Miscellaneous.GetStr(tvFamiliarAval));
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
            if (isEditAva || modoSuperUsuario) {
                selectedItemsMediosPago = new ArrayList<>();
                new AlertDialog.Builder(SolicitudCreditoInd.this)
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
                                for (int i = 0; i < selectedItemsMediosPago.size(); i++) {
                                    if (i == 0)
                                        medios += _medios_pago[selectedItemsMediosPago.get(i)];
                                    else
                                        medios += ", " + _medios_pago[selectedItemsMediosPago.get(i)];
                                }
                                tvMediosPagoAval.setError(null);
                                tvMediosPagoAval.setText(medios);
                                Update("medio_pago", TBL_AVAL_IND, Miscellaneous.GetStr(tvMediosPagoAval));

                                if (Miscellaneous.GetStr(tvMediosPagoAval).contains("OTROS")) {
                                    etOtroMedioPagoAval.setVisibility(View.VISIBLE);
                                } else {
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
            if (isEditAva || modoSuperUsuario) {
                selectedItemsActivos = new ArrayList<>();
                new AlertDialog.Builder(SolicitudCreditoInd.this)
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
                                for (int i = 0; i < selectedItemsActivos.size(); i++) {
                                    if (i == 0)
                                        medios += _activos[selectedItemsActivos.get(i)];
                                    else
                                        medios += ", " + _activos[selectedItemsActivos.get(i)];
                                }
                                tvActivosObservables.setError(null);
                                tvActivosObservables.setText(medios);
                                Update("activos_observables", TBL_AVAL_IND, Miscellaneous.GetStr(tvActivosObservables));

                                if (Miscellaneous.GetStr(tvMediosPagoAval).contains("OTRO")) {
                                    etOtroMedioPagoAval.setVisibility(View.VISIBLE);
                                } else {
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
            Intent i_firma_aval = new Intent(context, CapturarFirma.class);
            i_firma_aval.putExtra(TIPO, "AVAL");
            startActivityForResult(i_firma_aval, REQUEST_CODE_FIRMA_AVAL);
        }
    };

    private View.OnClickListener etDiasVenta_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (isEditNeg || modoSuperUsuario) {
                showDiasSemana();
            }
        }
    };

    private View.OnClickListener etColoniaAct_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (isEditNeg || tvColoniaNeg.getText().toString().equals("") || modoSuperUsuario) {
                Intent i_colonia = new Intent(context, Catalogos.class);
                i_colonia.putExtra(TITULO, Miscellaneous.ucFirst(COLONIAS));
                i_colonia.putExtra(CATALOGO, COLONIAS);
                i_colonia.putExtra(EXTRA, Miscellaneous.GetStr(etCpNeg));
                i_colonia.putExtra(REQUEST_CODE, REQUEST_CODE_COLONIA_NEG);
                startActivityForResult(i_colonia, REQUEST_CODE_COLONIA_NEG);
            }
        }
    };

    private View.OnClickListener tvActEcoEspNeg_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (isEditNeg || modoSuperUsuario) {
                Intent i_ocupaciones = new Intent(context, Catalogos.class);
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
            if (isEditNeg || modoSuperUsuario) {
                ColoniaDao coloniaDao = new ColoniaDao(ctx);
                List<Colonia> colonias = coloniaDao.findAllByCp(etCpNeg.getText().toString().trim());

                if (!Miscellaneous.GetStr(tvMunicipioNeg).isEmpty() && colonias.size() > 0) {

                    Cursor row = dBhelper.getRecords(TABLE_MUNICIPIOS, " WHERE municipio_id = ?", "", new String[]{String.valueOf(colonias.get(0).getMunicipioId())});
                    row.moveToFirst();
                    Intent i_localidad = new Intent(context, Catalogos.class);
                    i_localidad.putExtra(TITULO, Miscellaneous.ucFirst(LOCALIDADES));
                    i_localidad.putExtra(CATALOGO, LOCALIDADES);
                    i_localidad.putExtra(EXTRA, row.getString(1));
                    i_localidad.putExtra(REQUEST_CODE, REQUEST_CODE_LOCALIDAD_NEG);
                    startActivityForResult(i_localidad, REQUEST_CODE_LOCALIDAD_NEG);
                } else {
                    final AlertDialog solicitud = Popups.showDialogMessage(context, warning,
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
            Intent i = new Intent(SolicitudCreditoInd.this, CameraVertical.class);
            i.putExtra(ORDER_ID, "SC_negocio");
            startActivityForResult(i, REQUEST_CODE_CAMARA_FACHADA_NEG);
        }
    };

    private View.OnClickListener tvOcupacionClie_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (isEditCli || modoSuperUsuario) {
                Intent i_ocupaciones = new Intent(context, Catalogos.class);
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
            if (isEditCli || modoSuperUsuario) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle(R.string.selected_option)
                        .setItems(_tipo_identificacion, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int position) {
                                tvTipoIdentificacion.setError(null);
                                tvTipoIdentificacion.setText(_tipo_identificacion[position]);
                                Update("tipo_identificacion", TBL_CLIENTE_IND, Miscellaneous.GetStr(tvTipoIdentificacion));
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
            if (isEditAva || modoSuperUsuario) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle(R.string.selected_option)
                        .setItems(_tipo_identificacion, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int position) {
                                tvTipoIdentificacionAval.setError(null);
                                tvTipoIdentificacionAval.setText(_tipo_identificacion[position]);
                                Update("tipo_identificacion", TBL_AVAL_IND, Miscellaneous.GetStr(tvTipoIdentificacionAval));
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
            if (isEditCon || modoSuperUsuario) {
                Intent i_ocupaciones = new Intent(context, Catalogos.class);
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
            if (isEditCon || tvColoniaCony.getText().toString().equals("") || modoSuperUsuario) {
                Intent i_colonia = new Intent(context, Catalogos.class);
                i_colonia.putExtra(TITULO, Miscellaneous.ucFirst(ESTADOS));
                i_colonia.putExtra(CATALOGO, COLONIAS);
                i_colonia.putExtra(EXTRA, Miscellaneous.GetStr(etCpCony));
                i_colonia.putExtra(REQUEST_CODE, REQUEST_CODE_COLONIA_CONY);
                startActivityForResult(i_colonia, REQUEST_CODE_COLONIA_CONY);
            }
        }
    };

    private View.OnClickListener tvLocalidadCony_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (isEditCon || modoSuperUsuario) {
                ColoniaDao coloniaDao = new ColoniaDao(ctx);
                List<Colonia> colonias = coloniaDao.findAllByCp(etCpCony.getText().toString().trim());

                if (!Miscellaneous.GetStr(tvMunicipioCony).isEmpty() && colonias.size() > 0) {
                    Cursor row = dBhelper.getRecords(TABLE_MUNICIPIOS, " WHERE municipio_id = ?", "", new String[]{String.valueOf(colonias.get(0).getMunicipioId())});
                    row.moveToFirst();
                    Intent i_localidad = new Intent(context, Catalogos.class);
                    i_localidad.putExtra(TITULO, Miscellaneous.ucFirst(LOCALIDADES));
                    i_localidad.putExtra(CATALOGO, LOCALIDADES);
                    i_localidad.putExtra(EXTRA, row.getString(1));
                    i_localidad.putExtra(REQUEST_CODE, REQUEST_CODE_LOCALIDAD_CONY);
                    startActivityForResult(i_localidad, REQUEST_CODE_LOCALIDAD_CONY);
                } else {
                    final AlertDialog solicitud = Popups.showDialogMessage(context, warning,
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
            if (isEditNeg || modoSuperUsuario) {
                Intent i_ocupaciones = new Intent(context, Catalogos.class);
                i_ocupaciones.putExtra(TITULO, Miscellaneous.ucFirst(SECTORES));
                i_ocupaciones.putExtra(CATALOGO, SECTORES);
                i_ocupaciones.putExtra(REQUEST_CODE, REQUEST_CODE_ACTIVIDAD_NEG);
                startActivityForResult(i_ocupaciones, REQUEST_CODE_ACTIVIDAD_NEG);
            }
        }
    };

    private View.OnClickListener tvNumOperacionNeg_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (isEditCre || modoSuperUsuario) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle(R.string.selected_option)
                        .setItems(R.array.max_pagos_mes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int position) {
                                tvNumOperacionNeg.setError(null);
                                tvNumOperacionNeg.setText(_max_pagos_mes[position]);
                                Update("num_operacion_mensuales", TBL_NEGOCIO_IND, Miscellaneous.GetStr(tvNumOperacionNeg));
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
            if (isEditCre || modoSuperUsuario) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle(R.string.selected_option)
                        .setItems(R.array.max_pagos_mes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int position) {
                                etNumOperacionEfectNeg.setError(null);
                                etNumOperacionEfectNeg.setText(_max_pagos_mes[position]);
                                Update("num_operacion_efectivo", TBL_NEGOCIO_IND, Miscellaneous.GetStr(etNumOperacionEfectNeg));
                            }
                        });
                builder.create();
                builder.show();
            }
        }
    };

    private View.OnClickListener tvDestinoNeg_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (isEditNeg || modoSuperUsuario) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle(R.string.selected_option)
                        .setItems(_destino_credito, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int position) {
                                tvDestinoNeg.setError(null);
                                tvDestinoNeg.setText(_destino_credito[position]);
                                Update("destino_credito", TBL_NEGOCIO_IND, _destino_credito[position]);

                                tvDestino.setText(_destino[1]);
                                Update("destino", TBL_CREDITO_IND, Miscellaneous.GetStr(tvDestino));

                                /*
                                if (position == 0){
                                    etOtroDestinoNeg.setVisibility(View.GONE);
                                    etOtroDestinoNeg.setText("");
                                    Update("otro_destino", TBL_NEGOCIO_IND, "");
                                }
                                else{
                                    etOtroDestinoNeg.setVisibility(View.VISIBLE);
                                }*/
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
            if (isEditNeg || modoSuperUsuario) {
                selectedItemsMediosPago = new ArrayList<>();
                new AlertDialog.Builder(SolicitudCreditoInd.this)
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
                                for (int i = 0; i < selectedItemsMediosPago.size(); i++) {
                                    if (i == 0)
                                        medios += _medios_pago[selectedItemsMediosPago.get(i)];
                                    else
                                        medios += ", " + _medios_pago[selectedItemsMediosPago.get(i)];
                                }
                                tvMediosPagoNeg.setError(null);
                                tvMediosPagoNeg.setText(medios);
                                Update("medio_pago", TBL_NEGOCIO_IND, Miscellaneous.GetStr(tvMediosPagoNeg));

                                if (Miscellaneous.GetStr(tvMediosPagoNeg).contains("EFECTIVO")) {
                                    llOperacionesEfectivo.setVisibility(View.VISIBLE);
                                } else {
                                    llOperacionesEfectivo.setVisibility(View.GONE);
                                }

                                if (Miscellaneous.GetStr(tvMediosPagoNeg).contains("OTRO")) {
                                    etOtroMedioPagoNeg.setVisibility(View.VISIBLE);
                                } else {
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

    private View.OnClickListener cbNegEnDomCli_OnCheck = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (cbNegEnDomCli.isChecked()) {
                //PRELLENAR DATOS
                locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
                    Toast.makeText(ctx, "El GPS se encuentra desactivado", Toast.LENGTH_SHORT).show();
                else {
                    Update("ubicado_en_dom_cli", TBL_NEGOCIO_IND, "SI");
                    ObtenerUbicacionNeg();
                    CopiarDatosDeClienteHaciaNegocio();
                }
            } else {
                //LIMPIAR DATOS
                Update("ubicado_en_dom_cli", TBL_NEGOCIO_IND, "NO");
                LimpiarDatosNegocio();
            }
        }
    };

    //============================  BENEFICIARIO  ================================================

    private View.OnClickListener parentescoBeneficiario_OnClik = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle(R.string.selected_option)
                    .setItems(_parentesco, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int position) {
                            txtParentescoBeneficiario.setError(null);
                            txtParentescoBeneficiario.setText(_parentesco[position]);
                        }
                    });
            builder.create();
            builder.show();
        }
    };

    //============ REFERNCIA =================================================================
    private View.OnClickListener tvFechaNacRef_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (isEditRef || modoSuperUsuario) {
                dialog_date_picker dialogDatePicker = new dialog_date_picker();
                Bundle b = new Bundle();

               /* b.putInt(YEAR_CURRENT, myCalendar.get(Calendar.YEAR));
                b.putInt(MONTH_CURRENT, myCalendar.get(Calendar.MONTH));
                b.putInt(DAY_CURRENT, myCalendar.get(Calendar.DAY_OF_MONTH));
                b.putString(DATE_CURRENT, sdf.format(myCalendar.getTime()));
                b.putInt(IDENTIFIER, 15);
                b.putBoolean(FECHAS_POST, false);
                dialogDatePicker.setArguments(b);
                dialogDatePicker.show(getSupportFragmentManager(), NameFragments.DIALOGDATEPICKER);*/
                int dato3 = 3;

                date = new DialogSelectorFecha(SolicitudCreditoInd.this, tvFechaNacRef, dato3, ind);

                String fechaMostrada = tvFechaNacRef.getText().toString().trim();
                if (!fechaMostrada.isEmpty()) {
                    date.recuperarFecha(fechaMostrada);
                } else {
                    date.showDatePickerDialog();
                }

            }
        }
    };

    private View.OnClickListener tvColoniaRef_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (isEditRef || tvColoniaRef.getText().toString().equals("") || modoSuperUsuario) {
                Intent i_colonia = new Intent(context, Catalogos.class);
                i_colonia.putExtra(TITULO, Miscellaneous.ucFirst(COLONIAS));
                i_colonia.putExtra(CATALOGO, COLONIAS);
                i_colonia.putExtra(EXTRA, Miscellaneous.GetStr(etCpRef));
                i_colonia.putExtra(REQUEST_CODE, REQUEST_CODE_COLONIA_REF);
                startActivityForResult(i_colonia, REQUEST_CODE_COLONIA_REF);
            }
        }
    };

    private View.OnClickListener tvLocalidadRef_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (isEditRef || modoSuperUsuario) {
                ColoniaDao coloniaDao = new ColoniaDao(ctx);
                List<Colonia> colonias = coloniaDao.findAllByCp(etCpRef.getText().toString().trim());

                if (!Miscellaneous.GetStr(tvMunicipioRef).isEmpty() && colonias.size() > 0) {
                    Cursor row = dBhelper.getRecords(TABLE_MUNICIPIOS, " WHERE municipio_id = ?", "", new String[]{String.valueOf(colonias.get(0).getMunicipioId())});
                    row.moveToFirst();
                    Intent i_localidad = new Intent(context, Catalogos.class);
                    i_localidad.putExtra(TITULO, Miscellaneous.ucFirst(LOCALIDADES));
                    i_localidad.putExtra(CATALOGO, LOCALIDADES);
                    i_localidad.putExtra(EXTRA, row.getString(1));
                    i_localidad.putExtra(REQUEST_CODE, REQUEST_CODE_LOCALIDAD_REF);
                    startActivityForResult(i_localidad, REQUEST_CODE_LOCALIDAD_REF);
                } else {
                    final AlertDialog solicitud = Popups.showDialogMessage(context, warning,
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
            if (isEditAva || modoSuperUsuario) {
                dialog_date_picker dialogDatePicker = new dialog_date_picker();
                Bundle b = new Bundle();

                /*b.putInt(YEAR_CURRENT, myCalendar.get(Calendar.YEAR));
                b.putInt(MONTH_CURRENT, myCalendar.get(Calendar.MONTH));
                b.putInt(DAY_CURRENT, myCalendar.get(Calendar.DAY_OF_MONTH));*/
               /* b.putInt(Constants.YEAR_CURRENT, ((Miscellaneous.GetStr(tvFechaNacAval).isEmpty()) ? myCalendar.get(Calendar.YEAR) : Integer.parseInt(Miscellaneous.GetStr(tvFechaNacAval).substring(0, 4))));
                b.putInt(Constants.MONTH_CURRENT, ((Miscellaneous.GetStr(tvFechaNacAval).isEmpty()) ? myCalendar.get(Calendar.MONTH) : (Integer.parseInt(Miscellaneous.GetStr(tvFechaNacAval).substring(5, 7)) - 1)));
                b.putInt(Constants.DAY_CURRENT, ((Miscellaneous.GetStr(tvFechaNacAval).isEmpty()) ? myCalendar.get(Calendar.DAY_OF_MONTH) : Integer.parseInt(Miscellaneous.GetStr(tvFechaNacAval).substring(8, 10))));
                b.putString(DATE_CURRENT, sdf.format(myCalendar.getTime()));
                b.putInt(IDENTIFIER, 3);
                b.putBoolean(FECHAS_POST, false);
                dialogDatePicker.setArguments(b);
                dialogDatePicker.show(getSupportFragmentManager(), NameFragments.DIALOGDATEPICKER);*/
                int dato2 = 2;

                date = new DialogSelectorFecha(SolicitudCreditoInd.this, tvFechaNacAval, dato2, ind);

                String fechaMostrada = tvFechaNacAval.getText().toString().trim();
                if (!fechaMostrada.isEmpty()) {
                    date.recuperarFecha(fechaMostrada);
                } else {
                    date.showDatePickerDialog();
                }

            }
        }
    };

    private View.OnClickListener tvEstadoNacAval_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (isEditAva || modoSuperUsuario) {
                Intent i_estados = new Intent(context, Catalogos.class);
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
            if (isEditAva || modoSuperUsuario) {
                Intent i_ocupaciones = new Intent(context, Catalogos.class);
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
            if (isEditAva || modoSuperUsuario) {
                dialog_time_picker timePicker = new dialog_time_picker();
                Bundle b = new Bundle();
                b.putInt(IDENTIFIER, 2);
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
            if (isEditCro || modoSuperUsuario) {
                dialog_input_calle dlgInput = new dialog_input_calle();
                Bundle b = new Bundle();
                b.putString(TIPO, "PRINCIPAL");
                b.putString(CALLE, Miscellaneous.GetStr(tvPrincipal));
                b.putString(TIPO_SOLICITUD, "INDIVIDUAL");
                dlgInput.setArguments(b);
                dlgInput.show(getSupportFragmentManager(), NameFragments.DIALOGINPUTCALLE);
            }
        }
    };
    private View.OnClickListener tvTrasera_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (isEditCro || modoSuperUsuario) {
                dialog_input_calle dlgInput = new dialog_input_calle();
                Bundle b = new Bundle();
                b.putString(TIPO, "TRASERA");
                b.putString(CALLE, Miscellaneous.GetStr(tvTrasera));
                b.putString(TIPO_SOLICITUD, "INDIVIDUAL");
                dlgInput.setArguments(b);
                dlgInput.show(getSupportFragmentManager(), NameFragments.DIALOGINPUTCALLE);
            }
        }
    };
    private View.OnClickListener tvLateralUno_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (isEditCro || modoSuperUsuario) {
                dialog_input_calle dlgInput = new dialog_input_calle();
                Bundle b = new Bundle();
                b.putString(TIPO, "LATERAL UNO");
                b.putString(CALLE, Miscellaneous.GetStr(tvLateraUno));
                b.putString(TIPO_SOLICITUD, "INDIVIDUAL");
                dlgInput.setArguments(b);
                dlgInput.show(getSupportFragmentManager(), NameFragments.DIALOGINPUTCALLE);
            }
        }
    };
    private View.OnClickListener tvLateralDos_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (isEditCro || modoSuperUsuario) {
                dialog_input_calle dlgInput = new dialog_input_calle();
                Bundle b = new Bundle();
                b.putString(TIPO, "LATERAL DOS");
                b.putString(CALLE, Miscellaneous.GetStr(tvLateraDos));
                b.putString(TIPO_SOLICITUD, "INDIVIDUAL");
                dlgInput.setArguments(b);
                dlgInput.show(getSupportFragmentManager(), NameFragments.DIALOGINPUTCALLE);
            }
        }
    };

    //======================  DOCUMENTOS  ==============================
    private View.OnClickListener ibIneFrontal_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent scanIntent = new Intent(SolicitudCreditoInd.this, CardIOActivity.class);
            scanIntent.putExtra(CardIOActivity.EXTRA_SUPPRESS_SCAN, true); // supmit cuando termine de reconocer el documento
            scanIntent.putExtra(CardIOActivity.EXTRA_SUPPRESS_MANUAL_ENTRY, true); // esconder teclado
            scanIntent.putExtra(CardIOActivity.EXTRA_USE_CARDIO_LOGO, true); // cambiar logo de paypal por el de card.io
            scanIntent.putExtra(CardIOActivity.EXTRA_RETURN_CARD_IMAGE, true); // capture img
            scanIntent.putExtra(CardIOActivity.EXTRA_CAPTURED_CARD_IMAGE, true); // capturar img

            startActivityForResult(scanIntent, REQUEST_CODE_FOTO_INE_FRONTAL);
        }
    };

    private View.OnClickListener ibIneReverso_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent scanIntent = new Intent(SolicitudCreditoInd.this, CardIOActivity.class);
            scanIntent.putExtra(CardIOActivity.EXTRA_SUPPRESS_SCAN, true); // supmit cuando termine de reconocer el documento
            scanIntent.putExtra(CardIOActivity.EXTRA_SUPPRESS_MANUAL_ENTRY, true); // esconder teclado
            scanIntent.putExtra(CardIOActivity.EXTRA_USE_CARDIO_LOGO, true); // cambiar logo de paypal por el de card.io
            scanIntent.putExtra(CardIOActivity.EXTRA_RETURN_CARD_IMAGE, true); // capture img
            scanIntent.putExtra(CardIOActivity.EXTRA_CAPTURED_CARD_IMAGE, true); // capturar img

            startActivityForResult(scanIntent, REQUEST_CODE_FOTO_INE_REVERSO);
        }
    };

    private View.OnClickListener ibCurp_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i = new Intent(SolicitudCreditoInd.this, CameraVertical.class);
            i.putExtra(ORDER_ID, "O_curp");
            startActivityForResult(i, REQUEST_CODE_FOTO_CURP);
        }
    };

    private View.OnClickListener ibComprobante_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i = new Intent(SolicitudCreditoInd.this, CameraVertical.class);
            i.putExtra(ORDER_ID, "O_comprobante");
            startActivityForResult(i, REQUEST_CODE_FOTO_COMPROBATE);
        }
    };

    private View.OnClickListener ibIneSelfie_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i = new Intent(SolicitudCreditoInd.this, CameraVertical.class);
            i.putExtra(ORDER_ID, "O_ine_selfie");
            startActivityForResult(i, REQUEST_CODE_FOTO_INE_SELFIE);
        }
    };

    private View.OnClickListener ibComprobanteGarantia_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i = new Intent(SolicitudCreditoInd.this, CameraVertical.class);
            i.putExtra(ORDER_ID, "O_comprobante_garantia");
            startActivityForResult(i, REQUEST_CODE_FOTO_COMPROBATE_GARANTIA);
        }
    };

    private View.OnClickListener ibIneFrontalAval_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent scanIntent = new Intent(SolicitudCreditoInd.this, CardIOActivity.class);
            scanIntent.putExtra(CardIOActivity.EXTRA_SUPPRESS_SCAN, true); // supmit cuando termine de reconocer el documento
            scanIntent.putExtra(CardIOActivity.EXTRA_SUPPRESS_MANUAL_ENTRY, true); // esconder teclado
            scanIntent.putExtra(CardIOActivity.EXTRA_USE_CARDIO_LOGO, true); // cambiar logo de paypal por el de card.io
            scanIntent.putExtra(CardIOActivity.EXTRA_RETURN_CARD_IMAGE, true); // capture img
            scanIntent.putExtra(CardIOActivity.EXTRA_CAPTURED_CARD_IMAGE, true); // capturar img
            startActivityForResult(scanIntent, REQUEST_CODE_FOTO_INE_FRONTAL_AVAL);
        }
    };

    private View.OnClickListener ibIneReversoAval_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent scanIntent = new Intent(SolicitudCreditoInd.this, CardIOActivity.class);
            scanIntent.putExtra(CardIOActivity.EXTRA_SUPPRESS_SCAN, true); // supmit cuando termine de reconocer el documento
            scanIntent.putExtra(CardIOActivity.EXTRA_SUPPRESS_MANUAL_ENTRY, true); // esconder teclado
            scanIntent.putExtra(CardIOActivity.EXTRA_USE_CARDIO_LOGO, true); // cambiar logo de paypal por el de card.io
            scanIntent.putExtra(CardIOActivity.EXTRA_RETURN_CARD_IMAGE, true); // capture img
            scanIntent.putExtra(CardIOActivity.EXTRA_CAPTURED_CARD_IMAGE, true); // capturar img
            startActivityForResult(scanIntent, REQUEST_CODE_FOTO_INE_REVERSO_AVAL);
        }
    };

    private View.OnClickListener ibCurpAval_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i = new Intent(SolicitudCreditoInd.this, CameraVertical.class);
            i.putExtra(ORDER_ID, "O_curp_aval");
            startActivityForResult(i, REQUEST_CODE_FOTO_CURP_AVAL);
        }
    };

    private View.OnClickListener ibComprobanteAval_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i = new Intent(SolicitudCreditoInd.this, CameraVertical.class);
            i.putExtra(ORDER_ID, "O_comprobante_aval");
            startActivityForResult(i, REQUEST_CODE_FOTO_COMPROBANTE_AVAL);
        }
    };

    //================== IMAGE VIEW LISTENER  ======================================================
    private View.OnClickListener ivFotoFachCli_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (isEditCli || modoSuperUsuario) {
                final AlertDialog evidencia_dlg = Popups.showDialogConfirmImage(context, question,
                        R.string.capturar_foto, R.string.fotografia, new Popups.DialogMessage() {
                            @Override
                            public void OnClickListener(AlertDialog dialog) {
                                Intent i = new Intent(context, CameraVertical.class);
                                startActivityForResult(i, REQUEST_CODE_CAMARA_FACHADA_CLI);
                                dialog.dismiss();

                            }
                        }, R.string.ver_imagen, new Popups.DialogMessage() {
                            @Override
                            public void OnClickListener(AlertDialog dialog) {
                                Intent i = new Intent(context, VerImagen.class);
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
            } else {
                final AlertDialog fachada_dlg = Popups.showDialogConfirm(context, question,
                        R.string.ver_fotografia, R.string.ver_imagen, new Popups.DialogMessage() {
                            @Override
                            public void OnClickListener(AlertDialog dialog) {
                                Intent i = new Intent(context, VerImagen.class);
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
            if (isEditCli || modoSuperUsuario) {
                final AlertDialog firma_dlg = Popups.showDialogConfirm(context, firma,
                        R.string.capturar_nueva_firma, R.string.accept, new Popups.DialogMessage() {
                            @Override
                            public void OnClickListener(AlertDialog dialog) {
                                Intent sig = new Intent(context, CapturarFirma.class);
                                sig.putExtra(TIPO, "");
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
            if (isEditNeg || modoSuperUsuario) {
                final AlertDialog evidencia_dlg = Popups.showDialogConfirmImage(context, question,
                        R.string.capturar_foto, R.string.fotografia, new Popups.DialogMessage() {
                            @Override
                            public void OnClickListener(AlertDialog dialog) {
                                Intent i = new Intent(context, CameraVertical.class);
                                startActivityForResult(i, REQUEST_CODE_CAMARA_FACHADA_NEG);
                                dialog.dismiss();

                            }
                        }, R.string.ver_imagen, new Popups.DialogMessage() {
                            @Override
                            public void OnClickListener(AlertDialog dialog) {
                                Intent i = new Intent(context, VerImagen.class);
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
            } else {
                final AlertDialog fachada_dlg = Popups.showDialogConfirm(context, question,
                        R.string.ver_fotografia, R.string.ver_imagen, new Popups.DialogMessage() {
                            @Override
                            public void OnClickListener(AlertDialog dialog) {
                                Intent i = new Intent(context, VerImagen.class);
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
            if (isEditAva || modoSuperUsuario) {
                final AlertDialog evidencia_dlg = Popups.showDialogConfirmImage(context, question,
                        R.string.capturar_foto, R.string.fotografia, new Popups.DialogMessage() {
                            @Override
                            public void OnClickListener(AlertDialog dialog) {
                                Intent i = new Intent(context, CameraVertical.class);
                                startActivityForResult(i, REQUEST_CODE_CAMARA_FACHADA_AVAL);
                                dialog.dismiss();

                            }
                        }, R.string.ver_imagen, new Popups.DialogMessage() {
                            @Override
                            public void OnClickListener(AlertDialog dialog) {
                                Intent i = new Intent(context, VerImagen.class);
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
            } else {
                final AlertDialog fachada_dlg = Popups.showDialogConfirm(context, question,
                        R.string.ver_fotografia, R.string.ver_imagen, new Popups.DialogMessage() {
                            @Override
                            public void OnClickListener(AlertDialog dialog) {
                                Intent i = new Intent(context, VerImagen.class);
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
            if (isEditAva || modoSuperUsuario) {
                final AlertDialog firma_dlg = Popups.showDialogConfirm(context, firma,
                        R.string.capturar_nueva_firma, R.string.accept, new Popups.DialogMessage() {
                            @Override
                            public void OnClickListener(AlertDialog dialog) {
                                Intent sig = new Intent(context, CapturarFirma.class);
                                sig.putExtra(TIPO, "");
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
            if (isEditDoc || modoSuperUsuario) {
                final AlertDialog evidencia_dlg = Popups.showDialogConfirmImage(context, question,
                        R.string.capturar_foto, R.string.fotografia, new Popups.DialogMessage() {
                            @Override
                            public void OnClickListener(AlertDialog dialog) {
                                Intent scanIntent = new Intent(SolicitudCreditoInd.this, CardIOActivity.class);
                                scanIntent.putExtra(CardIOActivity.EXTRA_SUPPRESS_SCAN, true); // supmit cuando termine de reconocer el documento
                                scanIntent.putExtra(CardIOActivity.EXTRA_SUPPRESS_MANUAL_ENTRY, true); // esconder teclado
                                scanIntent.putExtra(CardIOActivity.EXTRA_USE_CARDIO_LOGO, true); // cambiar logo de paypal por el de card.io
                                scanIntent.putExtra(CardIOActivity.EXTRA_RETURN_CARD_IMAGE, true); // capture img
                                scanIntent.putExtra(CardIOActivity.EXTRA_CAPTURED_CARD_IMAGE, true); // capturar img
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
            } else {
                final AlertDialog fachada_dlg = Popups.showDialogConfirm(context, question,
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
    private View.OnClickListener ivIneSelfie_OnClik = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (isEditDoc || modoSuperUsuario) {
                final AlertDialog evidencia_dlg = Popups.showDialogConfirmImage(context, question,
                        R.string.capturar_foto, R.string.fotografia, new Popups.DialogMessage() {
                            @Override
                            public void OnClickListener(AlertDialog dialog) {
                                Intent i = new Intent(context, CameraVertical.class);
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
            } else {
                final AlertDialog fachada_dlg = Popups.showDialogConfirm(context, question,
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
    private View.OnClickListener ivIneReverso_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (isEditDoc || modoSuperUsuario) {
                final AlertDialog evidencia_dlg = Popups.showDialogConfirmImage(context, question,
                        R.string.capturar_foto, R.string.fotografia, new Popups.DialogMessage() {
                            @Override
                            public void OnClickListener(AlertDialog dialog) {
                                Intent scanIntent = new Intent(SolicitudCreditoInd.this, CardIOActivity.class);
                                scanIntent.putExtra(CardIOActivity.EXTRA_SUPPRESS_SCAN, true); // supmit cuando termine de reconocer el documento
                                scanIntent.putExtra(CardIOActivity.EXTRA_SUPPRESS_MANUAL_ENTRY, true); // esconder teclado
                                scanIntent.putExtra(CardIOActivity.EXTRA_USE_CARDIO_LOGO, true); // cambiar logo de paypal por el de card.io
                                scanIntent.putExtra(CardIOActivity.EXTRA_RETURN_CARD_IMAGE, true); // capture img
                                scanIntent.putExtra(CardIOActivity.EXTRA_CAPTURED_CARD_IMAGE, true); // capturar img
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
            } else {
                final AlertDialog fachada_dlg = Popups.showDialogConfirm(context, question,
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
    private View.OnClickListener ivCurp_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (isEditDoc || modoSuperUsuario) {
                final AlertDialog evidencia_dlg = Popups.showDialogConfirmImage(context, question,
                        R.string.capturar_foto, R.string.fotografia, new Popups.DialogMessage() {
                            @Override
                            public void OnClickListener(AlertDialog dialog) {
                                Intent i = new Intent(context, CameraVertical.class);
                                startActivityForResult(i, REQUEST_CODE_FOTO_CURP);
                                dialog.dismiss();

                            }
                        }, R.string.ver_imagen, new Popups.DialogMessage() {
                            @Override
                            public void OnClickListener(AlertDialog dialog) {
                                Intent i = new Intent(context, VerImagen.class);
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
            } else {
                final AlertDialog fachada_dlg = Popups.showDialogConfirm(context, question,
                        R.string.ver_fotografia, R.string.ver_imagen, new Popups.DialogMessage() {
                            @Override
                            public void OnClickListener(AlertDialog dialog) {
                                Intent i = new Intent(context, VerImagen.class);
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
    };
    private View.OnClickListener ivComprobante_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (isEditDoc || modoSuperUsuario) {
                final AlertDialog evidencia_dlg = Popups.showDialogConfirmImage(context, question,
                        R.string.capturar_foto, R.string.fotografia, new Popups.DialogMessage() {
                            @Override
                            public void OnClickListener(AlertDialog dialog) {
                                Intent i = new Intent(context, CameraVertical.class);
                                startActivityForResult(i, REQUEST_CODE_FOTO_COMPROBATE);
                                dialog.dismiss();

                            }
                        }, R.string.ver_imagen, new Popups.DialogMessage() {
                            @Override
                            public void OnClickListener(AlertDialog dialog) {
                                Intent i = new Intent(context, VerImagen.class);
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
            } else {
                final AlertDialog fachada_dlg = Popups.showDialogConfirm(context, question,
                        R.string.ver_fotografia, R.string.ver_imagen, new Popups.DialogMessage() {
                            @Override
                            public void OnClickListener(AlertDialog dialog) {
                                Intent i = new Intent(context, VerImagen.class);
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
    private View.OnClickListener ivComprobanteGarantia_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (isEditDoc || modoSuperUsuario) {
                final AlertDialog evidencia_dlg = Popups.showDialogConfirmImage(context, question,
                        R.string.capturar_foto, R.string.fotografia, new Popups.DialogMessage() {
                            @Override
                            public void OnClickListener(AlertDialog dialog) {
                                Intent i = new Intent(context, CameraVertical.class);
                                startActivityForResult(i, REQUEST_CODE_FOTO_COMPROBATE_GARANTIA);
                                dialog.dismiss();

                            }
                        }, R.string.ver_imagen, new Popups.DialogMessage() {
                            @Override
                            public void OnClickListener(AlertDialog dialog) {
                                Intent i = new Intent(context, VerImagen.class);
                                i.putExtra(IMAGEN, byteComprobanteGarantia);
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
            } else {
                final AlertDialog fachada_dlg = Popups.showDialogConfirm(context, question,
                        R.string.ver_fotografia, R.string.ver_imagen, new Popups.DialogMessage() {
                            @Override
                            public void OnClickListener(AlertDialog dialog) {
                                Intent i = new Intent(context, VerImagen.class);
                                i.putExtra(IMAGEN, byteComprobanteGarantia);
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
    private View.OnClickListener ivIneFrontalAval_OnClik = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (isEditAva || modoSuperUsuario) {
                final AlertDialog evidencia_dlg = Popups.showDialogConfirmImage(context, question,
                        R.string.capturar_foto, R.string.fotografia, new Popups.DialogMessage() {
                            @Override
                            public void OnClickListener(AlertDialog dialog) {
                                Intent scanIntent = new Intent(SolicitudCreditoInd.this, CardIOActivity.class);
                                scanIntent.putExtra(CardIOActivity.EXTRA_SUPPRESS_SCAN, true); // supmit cuando termine de reconocer el documento
                                scanIntent.putExtra(CardIOActivity.EXTRA_SUPPRESS_MANUAL_ENTRY, true); // esconder teclado
                                scanIntent.putExtra(CardIOActivity.EXTRA_USE_CARDIO_LOGO, true); // cambiar logo de paypal por el de card.io
                                scanIntent.putExtra(CardIOActivity.EXTRA_RETURN_CARD_IMAGE, true); // capture img
                                scanIntent.putExtra(CardIOActivity.EXTRA_CAPTURED_CARD_IMAGE, true); // capturar img
                                startActivityForResult(scanIntent, REQUEST_CODE_FOTO_INE_FRONTAL_AVAL);
                                dialog.dismiss();

                            }
                        }, R.string.ver_imagen, new Popups.DialogMessage() {
                            @Override
                            public void OnClickListener(AlertDialog dialog) {
                                Intent i = new Intent(ctx, VerImagen.class);
                                i.putExtra(IMAGEN, byteIneFrontalAval);
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
            } else {
                final AlertDialog fachada_dlg = Popups.showDialogConfirm(context, question,
                        R.string.ver_fotografia, R.string.ver_imagen, new Popups.DialogMessage() {
                            @Override
                            public void OnClickListener(AlertDialog dialog) {
                                Intent i = new Intent(ctx, VerImagen.class);
                                i.putExtra(IMAGEN, byteIneFrontalAval);
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
    private View.OnClickListener ivIneReversoAval_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (isEditAva || modoSuperUsuario) {
                final AlertDialog evidencia_dlg = Popups.showDialogConfirmImage(context, question,
                        R.string.capturar_foto, R.string.fotografia, new Popups.DialogMessage() {
                            @Override
                            public void OnClickListener(AlertDialog dialog) {
                                Intent scanIntent = new Intent(SolicitudCreditoInd.this, CardIOActivity.class);
                                scanIntent.putExtra(CardIOActivity.EXTRA_SUPPRESS_SCAN, true); // supmit cuando termine de reconocer el documento
                                scanIntent.putExtra(CardIOActivity.EXTRA_SUPPRESS_MANUAL_ENTRY, true); // esconder teclado
                                scanIntent.putExtra(CardIOActivity.EXTRA_USE_CARDIO_LOGO, true); // cambiar logo de paypal por el de card.io
                                scanIntent.putExtra(CardIOActivity.EXTRA_RETURN_CARD_IMAGE, true); // capture img
                                scanIntent.putExtra(CardIOActivity.EXTRA_CAPTURED_CARD_IMAGE, true); // capturar img
                                startActivityForResult(scanIntent, REQUEST_CODE_FOTO_INE_REVERSO_AVAL);
                                dialog.dismiss();

                            }
                        }, R.string.ver_imagen, new Popups.DialogMessage() {
                            @Override
                            public void OnClickListener(AlertDialog dialog) {
                                Intent i = new Intent(ctx, VerImagen.class);
                                i.putExtra(IMAGEN, byteIneReversoAval);
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
            } else {
                final AlertDialog fachada_dlg = Popups.showDialogConfirm(context, question,
                        R.string.ver_fotografia, R.string.ver_imagen, new Popups.DialogMessage() {
                            @Override
                            public void OnClickListener(AlertDialog dialog) {
                                Intent i = new Intent(ctx, VerImagen.class);
                                i.putExtra(IMAGEN, byteIneReversoAval);
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
    private View.OnClickListener ivCurpAval_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (isEditAva || modoSuperUsuario) {
                final AlertDialog evidencia_dlg = Popups.showDialogConfirmImage(context, question,
                        R.string.capturar_foto, R.string.fotografia, new Popups.DialogMessage() {
                            @Override
                            public void OnClickListener(AlertDialog dialog) {
                                Intent i = new Intent(context, CameraVertical.class);
                                startActivityForResult(i, REQUEST_CODE_FOTO_CURP_AVAL);
                                dialog.dismiss();

                            }
                        }, R.string.ver_imagen, new Popups.DialogMessage() {
                            @Override
                            public void OnClickListener(AlertDialog dialog) {
                                Intent i = new Intent(context, VerImagen.class);
                                i.putExtra(IMAGEN, byteCurpAval);
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
            } else {
                final AlertDialog fachada_dlg = Popups.showDialogConfirm(context, question,
                        R.string.ver_fotografia, R.string.ver_imagen, new Popups.DialogMessage() {
                            @Override
                            public void OnClickListener(AlertDialog dialog) {
                                Intent i = new Intent(context, VerImagen.class);
                                i.putExtra(IMAGEN, byteCurpAval);
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
            if (isEditDoc || modoSuperUsuario) {
                final AlertDialog firma_dlg = Popups.showDialogConfirm(context, firma,
                        R.string.capturar_nueva_firma, R.string.accept, new Popups.DialogMessage() {
                            @Override
                            public void OnClickListener(AlertDialog dialog) {
                                Intent sig = new Intent(context, CapturarFirma.class);
                                sig.putExtra(TIPO, "");
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
    private View.OnClickListener ivComprobanteAval_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (isEditAva || modoSuperUsuario) {
                final AlertDialog evidencia_dlg = Popups.showDialogConfirmImage(context, question,
                        R.string.capturar_foto, R.string.fotografia, new Popups.DialogMessage() {
                            @Override
                            public void OnClickListener(AlertDialog dialog) {
                                Intent i = new Intent(context, CameraVertical.class);
                                startActivityForResult(i, REQUEST_CODE_FOTO_COMPROBANTE_AVAL);
                                dialog.dismiss();

                            }
                        }, R.string.ver_imagen, new Popups.DialogMessage() {
                            @Override
                            public void OnClickListener(AlertDialog dialog) {
                                Intent i = new Intent(context, VerImagen.class);
                                i.putExtra(IMAGEN, byteComprobanteAval);
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
            } else {
                final AlertDialog fachada_dlg = Popups.showDialogConfirm(context, question,
                        R.string.ver_fotografia, R.string.ver_imagen, new Popups.DialogMessage() {
                            @Override
                            public void OnClickListener(AlertDialog dialog) {
                                Intent i = new Intent(context, VerImagen.class);
                                i.putExtra(IMAGEN, byteComprobanteAval);
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

    // ================== IMAGE VIEW  ===================================
    private View.OnClickListener llCredito_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (ivDown1.getVisibility() == View.VISIBLE && ivUp1.getVisibility() == View.GONE) {
                ivDown1.setVisibility(View.GONE);
                ivUp1.setVisibility(View.VISIBLE);
                llDatosCredito.setVisibility(View.VISIBLE);
            } else if (ivDown1.getVisibility() == View.GONE && ivUp1.getVisibility() == View.VISIBLE) {
                ivDown1.setVisibility(View.VISIBLE);
                ivUp1.setVisibility(View.GONE);
                llDatosCredito.setVisibility(View.GONE);
            }
        }
    };
    private View.OnClickListener llPersonales_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (ivDown2.getVisibility() == View.VISIBLE && ivUp2.getVisibility() == View.GONE) {
                ivDown2.setVisibility(View.GONE);
                ivUp2.setVisibility(View.VISIBLE);
                llDatosPersonales.setVisibility(View.VISIBLE);
                etNombreCli.requestFocus();
            } else if (ivDown2.getVisibility() == View.GONE && ivUp2.getVisibility() == View.VISIBLE) {
                ivDown2.setVisibility(View.VISIBLE);
                ivUp2.setVisibility(View.GONE);
                llDatosPersonales.setVisibility(View.GONE);
            }
        }
    };
    private View.OnClickListener llConyuge_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (ivDown3.getVisibility() == View.VISIBLE && ivUp3.getVisibility() == View.GONE) {
                ivDown3.setVisibility(View.GONE);
                ivUp3.setVisibility(View.VISIBLE);
                llDatosConyuge.setVisibility(View.VISIBLE);
                etNombreCony.requestFocus();
            } else if (ivDown3.getVisibility() == View.GONE && ivUp3.getVisibility() == View.VISIBLE) {
                ivDown3.setVisibility(View.VISIBLE);
                ivUp3.setVisibility(View.GONE);
                llDatosConyuge.setVisibility(View.GONE);
            }
        }
    };
    private View.OnClickListener llEconomicos_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (ivDown4.getVisibility() == View.VISIBLE && ivUp4.getVisibility() == View.GONE) {
                ivDown4.setVisibility(View.GONE);
                ivUp4.setVisibility(View.VISIBLE);
                llDatosEconomicos.setVisibility(View.VISIBLE);
                etPropiedadesEco.requestFocus();
            } else if (ivDown4.getVisibility() == View.GONE && ivUp4.getVisibility() == View.VISIBLE) {
                ivDown4.setVisibility(View.VISIBLE);
                ivUp4.setVisibility(View.GONE);
                llDatosEconomicos.setVisibility(View.GONE);
            }
        }
    };
    private View.OnClickListener llNegocio_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (ivDown5.getVisibility() == View.VISIBLE && ivUp5.getVisibility() == View.GONE) {
                ivDown5.setVisibility(View.GONE);
                ivUp5.setVisibility(View.VISIBLE);
                llDatosNegocio.setVisibility(View.VISIBLE);
                etNombreNeg.requestFocus();
            } else if (ivDown5.getVisibility() == View.GONE && ivUp5.getVisibility() == View.VISIBLE) {
                ivDown5.setVisibility(View.VISIBLE);
                ivUp5.setVisibility(View.GONE);
                llDatosNegocio.setVisibility(View.GONE);
            }
        }
    };
    private View.OnClickListener llAval_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (ivDown6.getVisibility() == View.VISIBLE && ivUp6.getVisibility() == View.GONE) {
                ivDown6.setVisibility(View.GONE);
                ivUp6.setVisibility(View.VISIBLE);
                llDatosAval.setVisibility(View.VISIBLE);
                etNombreAval.requestFocus();
            } else if (ivDown6.getVisibility() == View.GONE && ivUp6.getVisibility() == View.VISIBLE) {
                ivDown6.setVisibility(View.VISIBLE);
                ivUp6.setVisibility(View.GONE);
                llDatosAval.setVisibility(View.GONE);
            }
        }
    };
    private View.OnClickListener llReferencia_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (ivDown7.getVisibility() == View.VISIBLE && ivUp7.getVisibility() == View.GONE) {
                ivDown7.setVisibility(View.GONE);
                ivUp7.setVisibility(View.VISIBLE);
                llDatosReferencia.setVisibility(View.VISIBLE);
                etNombreRef.requestFocus();
            } else if (ivDown7.getVisibility() == View.GONE && ivUp7.getVisibility() == View.VISIBLE) {
                ivDown7.setVisibility(View.VISIBLE);
                ivUp7.setVisibility(View.GONE);
                llDatosReferencia.setVisibility(View.GONE);

            }
        }
    };
    private View.OnClickListener llBeneficiario_OnClik = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (ivDownBeni.getVisibility() == View.VISIBLE && ivUpBeni.getVisibility() == View.GONE) {
                ivDownBeni.setVisibility(View.GONE);
                ivUpBeni.setVisibility(View.VISIBLE);
                txtNomBeneficiario.requestFocus();
                llDatosBeneficiario.setVisibility(View.VISIBLE);
            } else if (ivDownBeni.getVisibility() == View.GONE && ivUpBeni.getVisibility() == View.VISIBLE) {
                ivDownBeni.setVisibility(View.VISIBLE);
                ivUpBeni.setVisibility(View.GONE);
                llDatosBeneficiario.setVisibility(View.GONE);
            }
        }
    };
    private View.OnClickListener llCroquis_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (ivDown8.getVisibility() == View.VISIBLE && ivUp8.getVisibility() == View.GONE) {
                ivDown8.setVisibility(View.GONE);
                ivUp8.setVisibility(View.VISIBLE);
                llDatosCroquis.setVisibility(View.VISIBLE);
            } else if (ivDown8.getVisibility() == View.GONE && ivUp8.getVisibility() == View.VISIBLE) {
                ivDown8.setVisibility(View.VISIBLE);
                ivUp8.setVisibility(View.GONE);
                llDatosCroquis.setVisibility(View.GONE);

            }
        }
    };
    private View.OnClickListener llPoliticas_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (ivDown9.getVisibility() == View.VISIBLE && ivUp9.getVisibility() == View.GONE) {
                ivDown9.setVisibility(View.GONE);
                ivUp9.setVisibility(View.VISIBLE);
                llDatosPoliticas.setVisibility(View.VISIBLE);
            } else if (ivDown9.getVisibility() == View.GONE && ivUp9.getVisibility() == View.VISIBLE) {
                ivDown9.setVisibility(View.VISIBLE);
                ivUp9.setVisibility(View.GONE);
                llDatosPoliticas.setVisibility(View.GONE);

            }
        }
    };
    private View.OnClickListener llDocumentos_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (ivDown10.getVisibility() == View.VISIBLE && ivUp10.getVisibility() == View.GONE) {
                ivDown10.setVisibility(View.GONE);
                ivUp10.setVisibility(View.VISIBLE);
                llDatosDocumentos.setVisibility(View.VISIBLE);
            } else if (ivDown10.getVisibility() == View.GONE && ivUp10.getVisibility() == View.VISIBLE) {
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
            if (Miscellaneous.GetStr(tvEstadoCivilCli).equals("CASADO(A)") ||
                    Miscellaneous.GetStr(tvEstadoCivilCli).equals("UNIN LIBRE")) {
                etNombreCony.requestFocus();
                ivDown3.setVisibility(View.GONE);
                ivUp3.setVisibility(View.VISIBLE);
                llDatosConyuge.setVisibility(View.VISIBLE);
            } else {
                if (!Miscellaneous.GetStr(etMontoPrestamo).replace(",", "").isEmpty() &&
                        Integer.parseInt(Miscellaneous.GetStr(etMontoPrestamo).replace(",", "")) > 30000) {
                    etPropiedadesEco.requestFocus();
                    ivDown4.setVisibility(View.GONE);
                    ivUp4.setVisibility(View.VISIBLE);
                    llDatosEconomicos.setVisibility(View.VISIBLE);
                } else {
                    ivDownBeni.setVisibility(View.GONE);
                    ivUpBeni.setVisibility(View.VISIBLE);
                    llDatosBeneficiario.setVisibility(View.VISIBLE);
                    txtNomBeneficiario.requestFocus();
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
            if (!Miscellaneous.GetStr(etMontoPrestamo).replace(",", "").isEmpty() &&
                    Integer.parseInt(Miscellaneous.GetStr(etMontoPrestamo).replace(",", "")) > 30000) {
                ivDown4.setVisibility(View.GONE);
                ivUp4.setVisibility(View.VISIBLE);
                llDatosEconomicos.setVisibility(View.VISIBLE);
                etPropiedadesEco.requestFocus();
            } else {
                ivDownBeni.setVisibility(View.GONE);
                ivUpBeni.setVisibility(View.VISIBLE);
                llDatosBeneficiario.setVisibility(View.VISIBLE);
                txtNomBeneficiario.requestFocus();
            }
            ivDown3.setVisibility(View.VISIBLE);
            ivUp3.setVisibility(View.GONE);
            llDatosConyuge.setVisibility(View.GONE);
        }
    };
    private View.OnClickListener btnContinuar3_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //ivDown5.setVisibility(View.GONE);
            //ivUp5.setVisibility(View.VISIBLE);
            //llDatosNegocio.setVisibility(View.VISIBLE);
            //etNombreNeg.requestFocus();
            ivDownBeni.setVisibility(View.GONE);
            ivUpBeni.setVisibility(View.VISIBLE);
            llDatosBeneficiario.setVisibility(View.VISIBLE);
            txtNomBeneficiario.requestFocus();

            ivDown4.setVisibility(View.VISIBLE);
            ivUp4.setVisibility(View.GONE);
            llDatosEconomicos.setVisibility(View.GONE);
        }
    };
    private View.OnClickListener btnContinuarBeni_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            etNombreNeg.requestFocus();
            ivDown5.setVisibility(View.GONE);
            ivUp5.setVisibility(View.VISIBLE);
            llDatosNegocio.setVisibility(View.VISIBLE);

            ivDownBeni.setVisibility(View.VISIBLE);
            ivUpBeni.setVisibility(View.GONE);
            llDatosBeneficiario.setVisibility(View.GONE);
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
            //ivDown8.setVisibility(View.GONE);
            //ivUp8.setVisibility(View.VISIBLE);
            //llDatosCroquis.setVisibility(View.VISIBLE);
            ivDown9.setVisibility(View.GONE);
            ivUp9.setVisibility(View.VISIBLE);
            llDatosPoliticas.setVisibility(View.VISIBLE);

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
    private View.OnClickListener btnRegresarBeni_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            ivDown2.setVisibility(View.GONE);
            ivUp2.setVisibility(View.VISIBLE);
            llDatosPersonales.setVisibility(View.VISIBLE);

            ivDownBeni.setVisibility(View.VISIBLE);
            ivUpBeni.setVisibility(View.GONE);
            llDatosBeneficiario.setVisibility(View.GONE);

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
            if (Miscellaneous.GetStr(tvEstadoCivilCli).equals("CASADO(A)") ||
                    Miscellaneous.GetStr(tvEstadoCivilCli).equals("UNION LIBRE")) {
                ivDown3.setVisibility(View.GONE);
                ivUp3.setVisibility(View.VISIBLE);
                llDatosConyuge.setVisibility(View.VISIBLE);
                etNombreCony.requestFocus();
            } else {
                llPersonales.setVisibility(View.GONE);
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
            if (!Miscellaneous.GetStr(etMontoPrestamo).replace(",", "").isEmpty() &&
                    Integer.parseInt(Miscellaneous.GetStr(etMontoPrestamo).replace(",", "")) > 30000) {
                ivDown4.setVisibility(View.GONE);
                ivUp4.setVisibility(View.VISIBLE);
                llDatosEconomicos.setVisibility(View.VISIBLE);
                etPropiedadesEco.requestFocus();
            } else {
                if (Miscellaneous.GetStr(tvEstadoCivilCli).equals("CASADO(A)") ||
                        Miscellaneous.GetStr(tvEstadoCivilCli).equals("UNION LIBRE")) {
                    ivDown3.setVisibility(View.GONE);
                    ivUp3.setVisibility(View.VISIBLE);
                    llDatosConyuge.setVisibility(View.VISIBLE);
                    etNombreCony.requestFocus();
                } else {
                    ivDownBeni.setVisibility(View.GONE);
                    ivUpBeni.setVisibility(View.VISIBLE);
                    llDatosBeneficiario.setVisibility(View.VISIBLE);
                    txtNomBeneficiario.requestFocus();
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
            //ivDown8.setVisibility(View.GONE);
            //ivUp8.setVisibility(View.VISIBLE);
            //llDatosCroquis.setVisibility(View.VISIBLE);
            ivDown7.setVisibility(View.GONE);
            ivUp7.setVisibility(View.VISIBLE);
            llDatosReferencia.setVisibility(View.VISIBLE);

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
            if (isEditCre || modoSuperUsuario) {
                dialog_time_picker timePicker = new dialog_time_picker();
                Bundle b = new Bundle();
                b.putInt(IDENTIFIER, 1);
                timePicker.setArguments(b);
                timePicker.show(getSupportFragmentManager(), NameFragments.DIALOGTIMEPICKER);
            }
        }
    };
    //========================================================================================================

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQUEST_CODE_ESTADO_NAC:
                if (resultCode == REQUEST_CODE_ESTADO_NAC) {
                    if (data != null) {
                        tvEstadoNacCli.setError(null);
                        tvEstadoNacCli.setText(((ModeloCatalogoGral) data.getSerializableExtra(ITEM)).getNombre());
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
                        etCurpCli.setText(Miscellaneous.GenerarCurp(params));
                        Update("estado_nacimiento", TBL_CLIENTE_IND, Miscellaneous.GetStr(tvEstadoNacCli));
                    }
                } else {
                    tvEstadoNacCli.setError(" ");
                    Toast.makeText(ctx, "ESTE CAMPO ES REQUERIDO", Toast.LENGTH_SHORT).show();
                }
                break;
            case REQUEST_CODE_OCUPACION_CLIE:
                if (resultCode == REQUEST_CODE_OCUPACION_CLIE) {
                    if (data != null) {
                        tvOcupacionCli.setError(null);
                        tvOcupacionCli.setText(((ModeloCatalogoGral) data.getSerializableExtra(ITEM)).getNombre());
                        Cursor row = dBhelper.getRecords(SECTORES, " WHERE sector_id = " + (((ModeloCatalogoGral) data.getSerializableExtra(ITEM)).getExtra()) + "", "", null);
                        if (row.getCount() > 0) {
                            row.moveToFirst();
                            tvActividadEcoCli.setText(row.getString(2));
                        }
                        row.close();
                        Update("ocupacion", TBL_CLIENTE_IND, Miscellaneous.GetStr(tvOcupacionCli));
                        Update("actividad_economica", TBL_CLIENTE_IND, Miscellaneous.GetStr(tvActividadEcoCli));
                    }
                } else {
                    tvOcupacionCli.setError(" ");
                    Toast.makeText(ctx, "ESTE CAMPO ES REQUERIDO", Toast.LENGTH_SHORT).show();
                }
                break;
            case REQUEST_CODE_OCUPACION_NEG:
                if (resultCode == REQUEST_CODE_OCUPACION_NEG) {
                    if (data != null) {
                        tvActEcoEspNeg.setError(null);
                        tvActEcoEspNeg.setText(((ModeloCatalogoGral) data.getSerializableExtra(ITEM)).getNombre());
                        Cursor row = dBhelper.getRecords(SECTORES, " WHERE sector_id = " + (((ModeloCatalogoGral) data.getSerializableExtra(ITEM)).getExtra()) + "", "", null);
                        if (row.getCount() > 0) {
                            row.moveToFirst();
                            tvActEconomicaNeg.setText(row.getString(2));
                        }
                        row.close();
                        Update("ocupacion", TBL_NEGOCIO_IND, Miscellaneous.GetStr(tvActEcoEspNeg));
                        Update("actividad_economica", TBL_NEGOCIO_IND, Miscellaneous.GetStr(tvActEconomicaNeg));
                    }
                } else {
                    tvActEcoEspNeg.setError(" ");
                    Toast.makeText(ctx, "ESTE CAMPO ES REQUERIDO", Toast.LENGTH_SHORT).show();
                }
                break;
            case REQUEST_CODE_OCUPACION_CONY:
                if (resultCode == REQUEST_CODE_OCUPACION_CONY) {
                    if (data != null) {
                        tvOcupacionCony.setError(null);
                        tvOcupacionCony.setText(((ModeloCatalogoGral) data.getSerializableExtra(ITEM)).getNombre());
                        Update("ocupacion", TBL_CONYUGE_IND, Miscellaneous.GetStr(tvOcupacionCony));
                    }
                } else {
                    tvOcupacionCony.setError(" ");
                    Toast.makeText(ctx, "ESTE CAMPO ES REQUERIDO", Toast.LENGTH_SHORT).show();
                }
                break;
            case REQUEST_CODE_ACTIVIDAD_NEG:
                if (resultCode == REQUEST_CODE_ACTIVIDAD_NEG) {
                    if (data != null) {
                        tvActEconomicaNeg.setError(null);
                        tvActEconomicaNeg.setText(((ModeloCatalogoGral) data.getSerializableExtra(ITEM)).getNombre());
                        Update("actividad_economica", TBL_NEGOCIO_IND, Miscellaneous.GetStr(tvActEconomicaNeg));
                    }
                } else {
                    tvActEconomicaNeg.setError(" ");
                    Toast.makeText(ctx, "ESTE CAMPO ES REQUERIDO", Toast.LENGTH_SHORT).show();
                }
                break;
            case REQUEST_CODE_ESTADO_NAC_AVAL:
                if (resultCode == REQUEST_CODE_ESTADO_NAC_AVAL) {
                    if (data != null) {
                        tvEstadoNacAval.setError(null);
                        tvEstadoNacAval.setText(((ModeloCatalogoGral) data.getSerializableExtra(ITEM)).getNombre());
                        HashMap<Integer, String> params = new HashMap<>();

                        params.put(0, Miscellaneous.GetStr(etNombreAval));
                        params.put(1, Miscellaneous.GetStr(etApPaternoAval));
                        params.put(2, Miscellaneous.GetStr(etApMaternoAval));
                        params.put(3, Miscellaneous.GetStr(tvFechaNacAval));

                        if (rgGeneroAval.getCheckedRadioButtonId() == R.id.rbHombre)
                            params.put(4, "Hombre");
                        else if (rgGeneroAval.getCheckedRadioButtonId() == R.id.rbMujer)
                            params.put(4, "Mujer");
                        else
                            params.put(4, "");

                        if (!Miscellaneous.GetStr(tvEstadoNacAval).isEmpty())
                            params.put(5, Miscellaneous.GetStr(tvEstadoNacAval));
                        else
                            params.put(5, "");
                        etCurpAval.setText(Miscellaneous.GenerarCurp(params));
                        Update("estado_nacimiento", TBL_AVAL_IND, Miscellaneous.GetStr(tvEstadoNacAval));
                    }
                } else {
                    tvEstadoNacAval.setError(" ");
                    Toast.makeText(ctx, "ESTE CAMPO ES REQUERIDO", Toast.LENGTH_SHORT).show();
                }
                break;
            case REQUEST_CODE_OCUPACION_AVAL:
                if (resultCode == REQUEST_CODE_OCUPACION_AVAL) {
                    if (data != null) {
                        tvOcupacionAval.setError(null);
                        tvOcupacionAval.setText(((ModeloCatalogoGral) data.getSerializableExtra(ITEM)).getNombre());
                        Cursor row = dBhelper.getRecords(SECTORES, " WHERE sector_id = " + (((ModeloCatalogoGral) data.getSerializableExtra(ITEM)).getExtra()) + "", "", null);
                        if (row.getCount() > 0) {
                            row.moveToFirst();
                            tvActividadEcoAval.setError(null);
                            tvActividadEcoAval.setText(row.getString(2));
                            Update("ocupacion", TBL_AVAL_IND, Miscellaneous.GetStr(tvOcupacionAval));
                            Update("actividad_economica", TBL_AVAL_IND, Miscellaneous.GetStr(tvActividadEcoAval));
                        }
                        row.close();
                    }
                } else {
                    tvOcupacionAval.setError(" ");
                    Toast.makeText(ctx, "ESTE CAMPO ES REQUERIDO", Toast.LENGTH_SHORT).show();
                }
                break;
            case REQUEST_CODE_COLONIA_CLIE:
                if (resultCode == REQUEST_CODE_COLONIA_CLIE) {
                    if (data != null) {
                        tvColoniaCli.setError(null);
                        tvColoniaCli.setText(((ModeloCatalogoGral) data.getSerializableExtra(ITEM)).getNombre());
                        UpdateDireccion("colonia", Miscellaneous.GetStr(tvColoniaCli), direccionIdCli, "CLIENTE");
                    }
                } else {
                    tvColoniaCli.setError(" ");
                    Toast.makeText(ctx, "ESTE CAMPO ES REQUERIDO", Toast.LENGTH_SHORT).show();
                }
                break;
            case REQUEST_CODE_CAMAPANAS:
                if (data != null) {
                    ModeloCatalogoGral modeloCatalogoGral = (ModeloCatalogoGral) data.getSerializableExtra(ITEM);
                    if (modeloCatalogoGral == null) return;
                    String campanaNombre = modeloCatalogoGral.getNombre();
                    Integer campanaId = Miscellaneous.selectCampana(ctx, campanaNombre);

                    if (campanaNombre.equals("NINGUNO")) {
                        txtNombreRefiero.setText(SIN_REFERENCIA);
                        txtNombreRefiero.setEnabled(false);
                    } else {
                        txtNombreRefiero.setText("");
                        txtNombreRefiero.setEnabled(true);
                    }

                    ContentValues cv = new ContentValues();
                    cv.put("id_campana", campanaId);
                    db.update(TBL_CREDITO_IND, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                    txtCampana.setError(null);
                    txtCampana.setText(campanaNombre);
                    txtCampana.setTag(modeloCatalogoGral);


                } else {
                    txtCampana.setText(" ");
                    Toast.makeText(ctx, "ESTE CAMPO ES REQUERIDO", Toast.LENGTH_SHORT).show();
                }
                break;
            case REQUEST_CODE_LOCALIDAD_CLIE:
                if (resultCode == REQUEST_CODE_LOCALIDAD_CLIE) {
                    if (data != null) {
                        tvLocalidadCli.setError(null);
                        tvLocalidadCli.setText(((ModeloCatalogoGral) data.getSerializableExtra(ITEM)).getNombre());
                        UpdateDireccion("localidad", Miscellaneous.GetStr(tvLocalidadCli), direccionIdCli, "CLIENTE");
                    }
                } else {
                    tvLocalidadCli.setError(" ");
                    Toast.makeText(ctx, "ESTE CAMPO ES REQUERIDO", Toast.LENGTH_SHORT).show();
                }
                break;
            case REQUEST_CODE_LOCALIDAD_CONY:
                if (resultCode == REQUEST_CODE_LOCALIDAD_CONY) {
                    if (data != null) {
                        tvLocalidadCony.setError(null);
                        tvLocalidadCony.setText(((ModeloCatalogoGral) data.getSerializableExtra(ITEM)).getNombre());
                        UpdateDireccion("localidad", Miscellaneous.GetStr(tvLocalidadCony), direccionIdCony, "CONYUGE");
                    }
                } else {
                    tvLocalidadCony.setError(" ");
                    Toast.makeText(ctx, "ESTE CAMPO ES REQUERIDO", Toast.LENGTH_SHORT).show();
                }
                break;
            case REQUEST_CODE_LOCALIDAD_NEG:
                if (resultCode == REQUEST_CODE_LOCALIDAD_NEG) {
                    if (data != null) {
                        tvLocalidadNeg.setError(null);
                        tvLocalidadNeg.setText(((ModeloCatalogoGral) data.getSerializableExtra(ITEM)).getNombre());
                        UpdateDireccion("localidad", Miscellaneous.GetStr(tvLocalidadNeg), direccionIdNeg, "NEGOCIO");
                    }
                } else {
                    tvLocalidadNeg.setError(" ");
                    Toast.makeText(ctx, "ESTE CAMPO ES REQUERIDO", Toast.LENGTH_SHORT).show();
                }
                break;
            case REQUEST_CODE_LOCALIDAD_AVAL:
                if (resultCode == REQUEST_CODE_LOCALIDAD_AVAL) {
                    if (data != null) {
                        tvLocalidadAval.setError(null);
                        tvLocalidadAval.setText(((ModeloCatalogoGral) data.getSerializableExtra(ITEM)).getNombre());
                        UpdateDireccion("localidad", Miscellaneous.GetStr(tvLocalidadAval), direccionIdAval, "AVAL");
                    }
                } else {
                    tvLocalidadAval.setError(" ");
                    Toast.makeText(ctx, "ESTE CAMPO ES REQUERIDO", Toast.LENGTH_SHORT).show();
                }
                break;
            case REQUEST_CODE_LOCALIDAD_REF:
                if (resultCode == REQUEST_CODE_LOCALIDAD_REF) {
                    if (data != null) {
                        tvLocalidadRef.setError(null);
                        tvLocalidadRef.setText(((ModeloCatalogoGral) data.getSerializableExtra(ITEM)).getNombre());
                        UpdateDireccion("localidad", Miscellaneous.GetStr(tvLocalidadRef), direccionIdRef, "REFERENCIA");
                    }
                } else {
                    tvLocalidadRef.setError(" ");
                    Toast.makeText(ctx, "ESTE CAMPO ES REQUERIDO", Toast.LENGTH_SHORT).show();
                }
                break;
            case REQUEST_CODE_COLONIA_CONY:
                if (resultCode == REQUEST_CODE_COLONIA_CONY) {
                    if (data != null) {
                        tvColoniaCony.setError(null);
                        tvColoniaCony.setText(((ModeloCatalogoGral) data.getSerializableExtra(ITEM)).getNombre());
                        UpdateDireccion("colonia", Miscellaneous.GetStr(tvColoniaCony), direccionIdCony, "CONYUGE");
                    }
                } else {
                    tvColoniaCony.setError(" ");
                    Toast.makeText(ctx, "ESTE CAMPO ES REQUERIDO", Toast.LENGTH_SHORT).show();
                }
                break;
            case REQUEST_CODE_COLONIA_AVAL:
                if (resultCode == REQUEST_CODE_COLONIA_AVAL) {
                    if (data != null) {
                        tvColoniaAval.setText(((ModeloCatalogoGral) data.getSerializableExtra(ITEM)).getNombre());
                        UpdateDireccion("colonia", Miscellaneous.GetStr(tvColoniaAval), direccionIdAval, "AVAL");
                    }
                } else {
                    tvColoniaAval.setError(" ");
                    Toast.makeText(ctx, "ESTE CAMPO ES REQUERIDO", Toast.LENGTH_SHORT).show();
                }
                break;
            case REQUEST_CODE_COLONIA_NEG:
                if (resultCode == REQUEST_CODE_COLONIA_NEG) {
                    if (data != null) {
                        tvColoniaNeg.setError(null);
                        tvColoniaNeg.setText(((ModeloCatalogoGral) data.getSerializableExtra(ITEM)).getNombre());
                        UpdateDireccion("colonia", Miscellaneous.GetStr(tvColoniaNeg), direccionIdNeg, "NEGOCIO");
                    }
                } else {
                    tvColoniaNeg.setError(" ");
                    Toast.makeText(ctx, "ESTE CAMPO ES REQUERIDO", Toast.LENGTH_SHORT).show();
                }
                break;
            case REQUEST_CODE_COLONIA_REF:
                if (resultCode == REQUEST_CODE_COLONIA_REF) {
                    if (data != null) {
                        tvColoniaRef.setError(null);
                        tvColoniaRef.setText(((ModeloCatalogoGral) data.getSerializableExtra(ITEM)).getNombre());
                        UpdateDireccion("colonia", Miscellaneous.GetStr(tvColoniaRef), direccionIdRef, "REFERENCIA");
                    }
                } else {
                    tvColoniaRef.setError(" ");
                    Toast.makeText(ctx, "ESTE CAMPO ES REQUERIDO", Toast.LENGTH_SHORT).show();
                }
                break;
            case REQUEST_CODE_CAMARA_FACHADA_CLI:
                if (resultCode == Activity.RESULT_OK) {
                    if (data != null) {
                        tvFachadaCli.setError(null);
                        ibFotoFachCli.setVisibility(View.GONE);
                        ivFotoFachCli.setVisibility(View.VISIBLE);
                        byteFotoFachCli = data.getByteArrayExtra(PICTURE);
                        byteFotoFachCli = Miscellaneous.etiquetasFotoNormales(byteFotoFachCli, ctx);
                        Glide.with(this).load(byteFotoFachCli).centerCrop().into(ivFotoFachCli);
                        try {
                            Update("foto_fachada", TBL_CLIENTE_IND, Miscellaneous.save(byteFotoFachCli, 1));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                } else {
                    tvFachadaCli.setError(" ");
                    Toast.makeText(ctx, "ESTE CAMPO ES RQUERIDO", Toast.LENGTH_SHORT).show();
                }
                break;
            case REQUEST_CODE_CAMARA_FACHADA_NEG:
                if (resultCode == Activity.RESULT_OK) {
                    if (data != null) {
                        tvFachadaNeg.setError(null);
                        ibFotoFachNeg.setVisibility(View.GONE);
                        ivFotoFachNeg.setVisibility(View.VISIBLE);
                        byteFotoFachNeg = data.getByteArrayExtra(PICTURE);
                        byteFotoFachNeg = Miscellaneous.etiquetasFotoNormales(byteFotoFachNeg, ctx);
                        Glide.with(ctx).load(byteFotoFachNeg).centerCrop().into(ivFotoFachNeg);
                        try {
                            Update("foto_fachada", TBL_NEGOCIO_IND, Miscellaneous.save(byteFotoFachNeg, 1));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    tvFachadaNeg.setError("CAMPO REQUERIDO");
                    Toast.makeText(ctx, "ESTE CAMPO ES REQUERIDO", Toast.LENGTH_SHORT).show();
                }
                break;
            case REQUEST_CODE_CAMARA_FACHADA_AVAL:
                if (resultCode == Activity.RESULT_OK) {
                    if (data != null) {
                        tvFachadaAval.setError(null);
                        ibFotoFachAval.setVisibility(View.GONE);
                        ivFotoFachAval.setVisibility(View.VISIBLE);
                        byteFotoFachAval = data.getByteArrayExtra(PICTURE);
                        byteFotoFachAval = Miscellaneous.etiquetasFotoNormales(byteFotoFachAval, ctx);
                        Glide.with(ctx).load(byteFotoFachAval).centerCrop().into(ivFotoFachAval);
                        try {
                            Update("foto_fachada", TBL_AVAL_IND, Miscellaneous.save(byteFotoFachAval, 1));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    tvFachadaAval.setError("CAMPO REQUERIDO");
                    Toast.makeText(ctx, "ESTE CAMPO ES REQUERIDO", Toast.LENGTH_SHORT).show();
                }
                break;
            case REQUEST_CODE_FIRMA_AVAL:
                if (resultCode == Activity.RESULT_OK) {
                    if (data != null) {
                        tvFirmaAval.setError(null);
                        ibFirmaAval.setVisibility(View.GONE);
                        ivFirmaAval.setVisibility(View.VISIBLE);
                        Glide.with(ctx).load(data.getByteArrayExtra(FIRMA_IMAGE)).into(ivFirmaAval);
                        byteFirmaAval = data.getByteArrayExtra(FIRMA_IMAGE);
                        try {
                            Update("firma", TBL_AVAL_IND, Miscellaneous.save(byteFirmaAval, 3));
                            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                            if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
                                ObtenerUbicacionFirmaAval();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    tvFirmaAval.setError("CAMPO REQUERIDO");
                    Toast.makeText(ctx, "ESTE CAMPO ES REQUERIDO", Toast.LENGTH_SHORT).show();
                }
                break;
            case REQUEST_CODE_FIRMA_CLI:
                if (resultCode == Activity.RESULT_OK) {
                    if (data != null) {
                        tvFirmaCli.setError(null);
                        ibFirmaCli.setVisibility(View.GONE);
                        ivFirmaCli.setVisibility(View.VISIBLE);
                        Glide.with(ctx)
                                .load(data.getByteArrayExtra(FIRMA_IMAGE))
                                .into(ivFirmaCli);
                        byteFirmaCli = data.getByteArrayExtra(FIRMA_IMAGE);
                        try {
                            Update("firma", TBL_CLIENTE_IND, Miscellaneous.save(byteFirmaCli, 3));
                            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                            if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
                                ObtenerUbicacionFirmaCliente();

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    tvFirmaCli.setError("CAMPO REQUERIDO");
                    Toast.makeText(ctx, "ESTE CAMPO ES REQUERIDO", Toast.LENGTH_SHORT).show();
                }
                break;
            case REQUEST_CODE_FIRMA_ASESOR:
                if (resultCode == Activity.RESULT_OK) {
                    if (data != null) {
                        tvFirmaAsesor.setError(null);
                        ibFirmaAsesor.setVisibility(View.GONE);
                        ivFirmaAsesor.setVisibility(View.VISIBLE);
                        Glide.with(ctx).load(data.getByteArrayExtra(FIRMA_IMAGE)).into(ivFirmaAsesor);
                        byteFirmaAsesor = data.getByteArrayExtra(FIRMA_IMAGE);
                        try {
                            Update("firma_asesor", TBL_DOCUMENTOS, Miscellaneous.save(byteFirmaAsesor, 3));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    tvFirmaAsesor.setError("CAMPO REQUERIDO");
                    Toast.makeText(ctx, "ESTE CAMPO ES REQUERIDO", Toast.LENGTH_SHORT).show();
                }
                break;
            case REQUEST_CODE_FOTO_INE_SELFIE:
                if (resultCode == Activity.RESULT_OK) {
                    if (data != null) {
                        tvIneSelfie.setError(null);
                        ibIneSelfie.setVisibility(View.GONE);
                        ivIneSelfie.setVisibility(View.VISIBLE);
                        byteIneSelfie = data.getByteArrayExtra(PICTURE);
                        byteIneSelfie = Miscellaneous.etiquetasFotoNormales(byteIneSelfie, ctx);
                        Glide.with(ctx).load(byteIneSelfie).centerCrop().into(ivIneSelfie);
                        try {
                            Update("ine_selfie", TBL_DOCUMENTOS, Miscellaneous.save(byteIneSelfie, 4));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    tvIneSelfie.setError("CAMPO REQUERIDO");
                    Toast.makeText(ctx, "ESTE CAMPO ES REQUERIDO", Toast.LENGTH_SHORT).show();
                }
                break;
            case REQUEST_CODE_FOTO_INE_FRONTAL:
                if (resultCode == RESULT_SCAN_SUPPRESSED) {
                    if (data != null) {
                        tvIneFrontal.setError(null);
                        ibIneFrontal.setVisibility(View.GONE);
                        ivIneFrontal.setVisibility(View.VISIBLE);
                        Bitmap cardIneFrontal = CardIOActivity.getCapturedCardImage(data);
                        byteIneFrontal = Miscellaneous.etiquetasIne(cardIneFrontal, ctx);
                        Glide.with(ctx).load(byteIneFrontal).centerCrop().into(ivIneFrontal);
                        try {
                            Update("ine_frontal", TBL_DOCUMENTOS, Miscellaneous.save(byteIneFrontal, 4));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    tvIneSelfie.setError("CAMPO REQUERIDO");
                    Toast.makeText(ctx, "CAMPO REQUERIDO", Toast.LENGTH_SHORT).show();
                }
                break;
            case REQUEST_CODE_FOTO_INE_REVERSO:
                if (resultCode == RESULT_SCAN_SUPPRESSED) {
                    if (data != null) {
                        tvIneReverso.setError(null);
                        ibIneReverso.setVisibility(View.GONE);
                        ivIneReverso.setVisibility(View.VISIBLE);
                        Bitmap cardIneReverso = CardIOActivity.getCapturedCardImage(data);
                        byteIneReverso = Miscellaneous.etiquetasIne(cardIneReverso, ctx);
                        Glide.with(ctx).load(byteIneReverso).centerCrop().into(ivIneReverso);
                        try {
                            Update("ine_reverso", TBL_DOCUMENTOS, Miscellaneous.save(byteIneReverso, 4));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    tvIneReverso.setError("CAMPO REQUERIDO");
                    Toast.makeText(ctx, "ESTE CAMPO ES REQUERIDO", Toast.LENGTH_SHORT).show();
                }
                break;
            case REQUEST_CODE_FOTO_CURP:
                if (resultCode == Activity.RESULT_OK) {
                    if (data != null) {
                        tvCurp.setError(null);
                        ibCurp.setVisibility(View.GONE);
                        ivCurp.setVisibility(View.VISIBLE);
                        byteCurp = data.getByteArrayExtra(PICTURE);
                        byteCurp = Miscellaneous.etiquetasFotoNormales(byteCurp, ctx);
                        Glide.with(ctx).load(byteCurp).centerCrop().into(ivCurp);
                        try {
                            Update("curp", TBL_DOCUMENTOS, Miscellaneous.save(byteCurp, 4));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    tvCurp.setError("CAMPO REQUERIDO");
                    Toast.makeText(ctx, "ESTE CAMPO ES REQUERIDO", Toast.LENGTH_SHORT).show();
                }
                break;
            case REQUEST_CODE_FOTO_COMPROBATE:
                if (resultCode == Activity.RESULT_OK) {
                    if (data != null) {
                        tvComprobante.setError(null);
                        ibComprobante.setVisibility(View.GONE);
                        ivComprobante.setVisibility(View.VISIBLE);
                        byteComprobante = data.getByteArrayExtra(PICTURE);
                        byteComprobante = Miscellaneous.etiquetasFotoNormales(byteComprobante, ctx);
                        Glide.with(ctx).load(byteComprobante).centerCrop().into(ivComprobante);
                        try {
                            Update("comprobante", TBL_DOCUMENTOS, Miscellaneous.save(byteComprobante, 4));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    tvComprobante.setError("CAMPO REQUERIDO");
                    Toast.makeText(ctx, "ESTE CAMPO ES REQUERIDO", Toast.LENGTH_SHORT).show();
                }
                break;
            case REQUEST_CODE_FOTO_COMPROBATE_GARANTIA:
                if (resultCode == Activity.RESULT_OK) {
                    if (data != null) {
                        tvComprobanteGarantia.setError(null);
                        ibComprobanteGarantia.setVisibility(View.GONE);
                        ivComprobanteGarantia.setVisibility(View.VISIBLE);
                        byteComprobanteGarantia = data.getByteArrayExtra(PICTURE);
                        byteComprobanteGarantia = Miscellaneous.etiquetasFotoNormales(byteComprobanteGarantia, ctx);
                        Glide.with(ctx).load(byteComprobanteGarantia).centerCrop().into(ivComprobanteGarantia);
                        try {
                            Update("comprobante_garantia", TBL_DOCUMENTOS, Miscellaneous.save(byteComprobanteGarantia, 4));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    tvComprobanteGarantia.setError("CAMPO REQUERIDO");
                    Toast.makeText(ctx, "ESTE CAMPO ES REQUERIDO", Toast.LENGTH_SHORT).show();
                }
                break;
            case REQUEST_CODE_FOTO_INE_FRONTAL_AVAL:
                if (resultCode == RESULT_SCAN_SUPPRESSED) {
                    if (data != null) {
                        tvIneFrontalAval.setError(null);
                        ibIneFrontalAval.setVisibility(View.GONE);
                        ivIneFrontalAval.setVisibility(View.VISIBLE);
                        Bitmap cardIneFrontalAval = CardIOActivity.getCapturedCardImage(data);
                        byteIneFrontalAval = Miscellaneous.etiquetasIne(cardIneFrontalAval, ctx);
                        Glide.with(ctx).load(byteIneFrontalAval).centerCrop().into(ivIneFrontalAval);

                        try {
                            Update("ine_frontal_aval", TBL_DOCUMENTOS, Miscellaneous.save(byteIneFrontalAval, 4));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    tvIneFrontalAval.setError("CAMPO REQUERIDO");
                    Toast.makeText(ctx, "ESTE CAMPO ES REQUERIDO", Toast.LENGTH_SHORT).show();
                }
                break;
            case REQUEST_CODE_FOTO_INE_REVERSO_AVAL:
                if (resultCode == RESULT_SCAN_SUPPRESSED) {
                    if (data != null) {
                        tvIneReversoAval.setError(null);
                        ibIneReversoAval.setVisibility(View.GONE);
                        ivIneReversoAval.setVisibility(View.VISIBLE);
                        Bitmap cardIneReversoAval = CardIOActivity.getCapturedCardImage(data);
                        byteIneReversoAval = Miscellaneous.etiquetasIne(cardIneReversoAval, ctx);
                        Glide.with(ctx).load(byteIneReversoAval).centerCrop().into(ivIneReversoAval);
                        try {
                            Update("ine_reverso_aval", TBL_DOCUMENTOS, Miscellaneous.save(byteIneReversoAval, 4));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    tvIneReversoAval.setError("CAMPO REQUERIDO");
                    Toast.makeText(ctx, "ESTE CAMPO ES REQUERIDO", Toast.LENGTH_SHORT).show();
                }
                break;
            case REQUEST_CODE_FOTO_CURP_AVAL:
                if (resultCode == Activity.RESULT_OK) {
                    if (data != null) {
                        tvCurpAval.setError(null);
                        ibCurpAval.setVisibility(View.GONE);
                        ivCurpAval.setVisibility(View.VISIBLE);
                        byteCurpAval = data.getByteArrayExtra(PICTURE);
                        byteCurpAval = Miscellaneous.etiquetasFotoNormales(byteCurpAval, ctx);
                        Glide.with(ctx).load(byteCurpAval).centerCrop().into(ivCurpAval);
                        try {
                            Update("curp_aval", TBL_DOCUMENTOS, Miscellaneous.save(byteCurpAval, 4));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    tvCurpAval.setError("CAMPO REQUERIDO");
                    Toast.makeText(ctx, "ESTE CAMPO ES REQUERIDO", Toast.LENGTH_SHORT).show();
                }
                break;
            case REQUEST_CODE_FOTO_COMPROBANTE_AVAL:
                if (resultCode == Activity.RESULT_OK) {
                    if (data != null) {
                        tvComprobanteAval.setError(null);
                        ibComprobanteAval.setVisibility(View.GONE);
                        ivComprobanteAval.setVisibility(View.VISIBLE);
                        byteComprobanteAval = data.getByteArrayExtra(PICTURE);
                        byteComprobanteAval = Miscellaneous.etiquetasFotoNormales(byteComprobanteAval, ctx);
                        Glide.with(ctx).load(byteComprobanteAval).centerCrop().into(ivComprobanteAval);
                        try {
                            Update("comprobante_aval", TBL_DOCUMENTOS, Miscellaneous.save(byteComprobanteAval, 4));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    tvComprobanteAval.setError("CAMPO REQUERIDO");
                    Toast.makeText(ctx, "ESTE CAMPO ES REQUERIDO", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    public void mostrarEdad(String edad, String campo) {
        try {
            switch (campo) {
                case "fechaNacCli":
                    tvFechaNacCli.setError(null);
                    tvFechaNacCli.setText(edad);
                    tvEdadCli.setText(Miscellaneous.GetEdad2(edad));
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
                    Update("fecha_nacimiento", TBL_CLIENTE_IND, Miscellaneous.GetStr(tvFechaNacCli));
                    Update("edad", TBL_CLIENTE_IND, Miscellaneous.GetStr(tvEdadCli));
                    etCurpCli.setText(Miscellaneous.GenerarCurp(params));
                    break;
                case "fechaNacAval":
                    tvFechaNacAval.setError(null);
                    tvFechaNacAval.setText(edad);
                    tvEdadAval.setText(Miscellaneous.GetEdad2(edad));
                    HashMap<Integer, String> paramsAval = new HashMap<>();

                    paramsAval.put(0, Miscellaneous.GetStr(etNombreAval));
                    paramsAval.put(1, Miscellaneous.GetStr(etApPaternoAval));
                    paramsAval.put(2, Miscellaneous.GetStr(etApMaternoAval));
                    paramsAval.put(3, Miscellaneous.GetStr(tvFechaNacAval));

                    if (rgGeneroAval.getCheckedRadioButtonId() == R.id.rbHombre)
                        paramsAval.put(4, "Hombre");
                    else if (rgGeneroAval.getCheckedRadioButtonId() == R.id.rbMujer)
                        paramsAval.put(4, "Mujer");
                    else
                        paramsAval.put(4, "");

                    if (!Miscellaneous.GetStr(tvEstadoNacAval).isEmpty())
                        paramsAval.put(5, Miscellaneous.GetStr(tvEstadoNacAval));
                    else
                        paramsAval.put(5, "");

                    Update("fecha_nacimiento", TBL_AVAL_IND, Miscellaneous.GetStr(tvFechaNacAval));
                    Update("edad", TBL_AVAL_IND, Miscellaneous.GetStr(tvEdadAval));
                    etCurpAval.setText(Miscellaneous.GenerarCurp(paramsAval));
                    break;
                case "fechaNacRef":
                    tvFechaNacRef.setError(null);
                    tvFechaNacRef.setText(edad);
                    Update("fecha_nacimiento", TBL_REFERENCIA_IND, Miscellaneous.GetStr(tvFechaNacRef));
                    break;

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setDate(String date, String campo) {
        try {
            Date strDate = sdf.parse(date);
            Calendar cal = Calendar.getInstance();
            cal.setTime(strDate);
            switch (campo) {
                case "desembolso":
                    tvFechaDesembolso.setError(null);
                    tvFechaDesembolso.setText(sdf.format(cal.getTime()));
                    String[] fechaDes = Miscellaneous.GetStr(tvFechaDesembolso).split("-");
                    Calendar c = Calendar.getInstance();

                    c.set(Integer.valueOf(fechaDes[0]), (Integer.valueOf(fechaDes[1]) - 1), Integer.valueOf(fechaDes[2]));
                    int nD = c.get(Calendar.DAY_OF_WEEK);
                    String diaDesembolso = "";
                    switch (nD) {
                        case 1:
                            diaDesembolso = "DOMINGO";
                            break;
                        case 2:
                            diaDesembolso = "LUNES";
                            break;
                        case 3:
                            diaDesembolso = "MARTES";
                            break;
                        case 4:
                            diaDesembolso = "MIÉRCOLES";
                            break;
                        case 5:
                            diaDesembolso = "JUEVES";
                            break;
                        case 6:
                            diaDesembolso = "VIERNES";
                            break;
                        case 7:
                            diaDesembolso = "SÁBADO";
                            break;
                    }
                    tvDiaDesembolso.setText(diaDesembolso);

                    Update("fecha_desembolso", TBL_CREDITO_IND, Miscellaneous.GetStr(tvFechaDesembolso));
                    Update("dia_desembolso", TBL_CREDITO_IND, Miscellaneous.GetStr(tvDiaDesembolso));
                    break;
                case "fechaNacCli":
                    tvFechaNacCli.setError(null);
                    tvFechaNacCli.setText(date);
                    tvEdadCli.setText(Miscellaneous.GetEdad2(date));
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
                    Update("fecha_nacimiento", TBL_CLIENTE_IND, Miscellaneous.GetStr(tvFechaNacCli));
                    Update("edad", TBL_CLIENTE_IND, Miscellaneous.GetStr(tvEdadCli));
                    etCurpCli.setText(Miscellaneous.GenerarCurp(params));
                    break;
                case "fechaNacAval":
                    tvFechaNacAval.setError(null);
                    tvFechaNacAval.setText(date);
                    tvEdadAval.setText(Miscellaneous.GetEdad(sdf.format(cal.getTime())));
                    HashMap<Integer, String> paramsAval = new HashMap<>();

                    paramsAval.put(0, Miscellaneous.GetStr(etNombreAval));
                    paramsAval.put(1, Miscellaneous.GetStr(etApPaternoAval));
                    paramsAval.put(2, Miscellaneous.GetStr(etApMaternoAval));
                    paramsAval.put(3, Miscellaneous.GetStr(tvFechaNacAval));

                    if (rgGeneroAval.getCheckedRadioButtonId() == R.id.rbHombre)
                        paramsAval.put(4, "Hombre");
                    else if (rgGeneroAval.getCheckedRadioButtonId() == R.id.rbMujer)
                        paramsAval.put(4, "Mujer");
                    else
                        paramsAval.put(4, "");

                    if (!Miscellaneous.GetStr(tvEstadoNacAval).isEmpty())
                        paramsAval.put(5, Miscellaneous.GetStr(tvEstadoNacAval));
                    else
                        paramsAval.put(5, "");

                    Update("fecha_nacimiento", TBL_AVAL_IND, Miscellaneous.GetStr(tvFechaNacAval));
                    Update("edad", TBL_AVAL_IND, Miscellaneous.GetStr(tvEdadAval));
                    etCurpAval.setText(Miscellaneous.GenerarCurp(paramsAval));
                    break;
                case "fechaNacRef":
                    tvFechaNacRef.setError(null);
                    tvFechaNacRef.setText(date);
                    Update("fecha_nacimiento", TBL_REFERENCIA_IND, Miscellaneous.GetStr(tvFechaNacRef));
                    break;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void setTimer(String timer, String campo) {
        ContentValues cv;
        switch (campo) {
            case "HoraVisita":
                tvHoraVisita.setError(null);
                tvHoraVisita.setText(timer);
                Update("hora_visita", TBL_CREDITO_IND, Miscellaneous.GetStr(tvHoraVisita));
                break;
            case "HoraVisitaAval":
                tvHoraLocAval.setError(null);
                tvHoraLocAval.setText(timer);
                Update("horario_localizacion", TBL_AVAL_IND, timer);
                break;
        }
    }

    private void MontoMaximoNeg() {
        double ing_mensual = (Miscellaneous.GetStr(etIngMenNeg).isEmpty()) ? 0 : Integer.parseInt(Miscellaneous.GetStr(etIngMenNeg).replace(",", ""));
        double ing_otros = (Miscellaneous.GetStr(etOtrosIngNeg).isEmpty()) ? 0 : Integer.parseInt(Miscellaneous.GetStr(etOtrosIngNeg).replace(",", ""));

        double gas_mensual = (Miscellaneous.GetStr(etGastosMenNeg).isEmpty()) ? 0 : Integer.parseInt(Miscellaneous.GetStr(etGastosMenNeg).replace(",", ""));
        double gas_agua = (Miscellaneous.GetStr(etGastosAguaNeg).isEmpty()) ? 0 : Integer.parseInt(Miscellaneous.GetStr(etGastosAguaNeg).replace(",", ""));
        double gas_luz = (Miscellaneous.GetStr(etGastosLuzNeg).isEmpty()) ? 0 : Integer.parseInt(Miscellaneous.GetStr(etGastosLuzNeg).replace(",", ""));
        double gas_telefono = (Miscellaneous.GetStr(etGastosTelNeg).isEmpty()) ? 0 : Integer.parseInt(Miscellaneous.GetStr(etGastosTelNeg).replace(",", ""));
        double gas_renta = (Miscellaneous.GetStr(etGastosRentaNeg).isEmpty()) ? 0 : Integer.parseInt(Miscellaneous.GetStr(etGastosRentaNeg).replace(",", ""));
        double gas_otro = (Miscellaneous.GetStr(etGastosOtrosNeg).isEmpty()) ? 0 : Integer.parseInt(Miscellaneous.GetStr(etGastosOtrosNeg).replace(",", ""));

        double ingreso = ing_mensual + ing_otros;
        double gastos = gas_mensual + gas_agua + gas_luz + gas_telefono + gas_renta + gas_otro;

        tvMontoMaxNeg.setText(dfnd.format(ingreso - gastos));
        Update("monto_maximo", TBL_NEGOCIO_IND, Miscellaneous.GetStr(tvMontoMaxNeg).replace(",", ""));

        tvIngresoEco.setText(dfnd.format(ingreso));
        Update("ingreso", TBL_ECONOMICOS_IND, Miscellaneous.GetStr(tvIngresoEco).replace(",", ""));

    }

    private void MontoMaximoAval() {
        double ing_mensual = (Miscellaneous.GetStr(etIngMenAval).isEmpty()) ? 0 : Integer.parseInt(Miscellaneous.GetStr(etIngMenAval).replace(",", ""));
        double ing_otros = (Miscellaneous.GetStr(etIngOtrosAval).isEmpty()) ? 0 : Integer.parseInt(Miscellaneous.GetStr(etIngOtrosAval).replace(",", ""));

        double gas_mensual = (Miscellaneous.GetStr(etGastosSemAval).isEmpty()) ? 0 : Integer.parseInt(Miscellaneous.GetStr(etGastosSemAval).replace(",", ""));
        double gas_agua = (Miscellaneous.GetStr(etGastosAguaAval).isEmpty()) ? 0 : Integer.parseInt(Miscellaneous.GetStr(etGastosAguaAval).replace(",", ""));
        double gas_luz = (Miscellaneous.GetStr(etGastosLuzAval).isEmpty()) ? 0 : Integer.parseInt(Miscellaneous.GetStr(etGastosLuzAval).replace(",", ""));
        double gas_telefono = (Miscellaneous.GetStr(etGastosTelAval).isEmpty()) ? 0 : Integer.parseInt(Miscellaneous.GetStr(etGastosTelAval).replace(",", ""));
        double gas_renta = (Miscellaneous.GetStr(etGastosRentaAval).isEmpty()) ? 0 : Integer.parseInt(Miscellaneous.GetStr(etGastosRentaAval).replace(",", ""));
        double gas_otro = (Miscellaneous.GetStr(etGastosOtrosAval).isEmpty()) ? 0 : Integer.parseInt(Miscellaneous.GetStr(etGastosOtrosAval).replace(",", ""));

        double ingreso = ing_mensual + ing_otros;
        double gastos = gas_mensual + gas_agua + gas_luz + gas_telefono + gas_renta + gas_otro;

        tvMontoMaxAval.setText(dfnd.format(ingreso - gastos));
        Update("monto_maximo", TBL_AVAL_IND, Miscellaneous.GetStr(tvMontoMaxAval).replace(",", ""));
    }

    private void showDiasSemana() {
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
                        for (int i = 0; i < selectedItemsDias.size(); i++) {
                            if (i == 0)
                                dias += _dias_semana[selectedItemsDias.get(i)];
                            else
                                dias += ", " + _dias_semana[selectedItemsDias.get(i)];
                        }
                        tvDiasVentaNeg.setError(null);
                        tvDiasVentaNeg.setText(dias);
                        Update("dias_venta", TBL_NEGOCIO_IND, Miscellaneous.GetStr(tvDiasVentaNeg));
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                    }
                }).show();
    }

    private boolean saveDatosCredito() {
        boolean save_credito = false;
        if (
                !validatorTV.validate(tvPlazo, new String[]{validatorTV.REQUIRED}) &&
                        !validatorTV.validate(tvFrecuencia, new String[]{validatorTV.REQUIRED}) &&
                        !validatorTV.validate(tvFechaDesembolso, new String[]{validatorTV.REQUIRED}) &&
                        !validatorTV.validate(tvDiaDesembolso, new String[]{validatorTV.REQUIRED}) &&
                        !validatorTV.validate(tvHoraVisita, new String[]{validatorTV.REQUIRED}) &&
                        !validator.validate(etMontoPrestamo, new String[]{validator.REQUIRED, validator.MONEY, validator.CREDITO}) &&
                        !validatorTV.validate(tvDestino, new String[]{validatorTV.REQUIRED}) &&
                        !validatorTV.validate(tvRiesgo, new String[]{validatorTV.REQUIRED}) &&
                        !validator.validate(etMontoRefinanciar, new String[]{validator.REQUIRED}) &&
                        !validatorTV.validate(txtCampana, new String[]{validator.ONLY_TEXT}) &&
                        !validator.validate(txtNombreRefiero, new String[]{validator.ONLY_TEXT})
        ) {
            String txtCampanaValue = txtCampana.getText().toString();
            String txtNombreRefieroValue = txtNombreRefiero.getText().toString();

            if
            (
                    (txtCampanaValue.equals("SUMA Y GANA") || txtCampanaValue.equals("RESCATE Y GANA")) && txtNombreRefieroValue.equals(SIN_REFERENCIA)
            ) {
                txtCampana.setError("");
                txtNombreRefiero.setError("");
                Toast.makeText(ctx, "NO SE PUEDE ASOCIAR LA CAMPAÑA", Toast.LENGTH_SHORT).show();
            } else {
                ivError1.setVisibility(View.GONE);
                ContentValues cv = new ContentValues();
                cv.put("plazo", Miscellaneous.GetStr(tvPlazo));
                cv.put("periodicidad", Miscellaneous.GetStr(tvFrecuencia));
                cv.put("fecha_desembolso", Miscellaneous.GetStr(tvFechaDesembolso));
                cv.put("dia_desembolso", Miscellaneous.GetStr(tvDiaDesembolso));
                cv.put("hora_visita", Miscellaneous.GetStr(tvHoraVisita));
                cv.put("monto_prestamo", Miscellaneous.GetStr(etMontoPrestamo).replace(",", ""));
                cv.put("destino", Miscellaneous.GetStr(tvDestino));
                cv.put("clasificacion_riesgo", Miscellaneous.GetStr(tvRiesgo));
                cv.put("monto_refinanciar", Miscellaneous.GetStr(etMontoRefinanciar).replace(",", ""));
                cv.put("id_campana", Miscellaneous.selectCampana(ctx, Miscellaneous.GetStr(txtCampana)));

                db.update(TBL_CREDITO_IND, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                save_credito = true;
            }

        } else {
            ivError1.setVisibility(View.VISIBLE);
        }

        return save_credito;
    }

    private boolean saveDatosPersonales() {

        boolean save_cliente = false;
        ContentValues cv = new ContentValues();
        if (!validator.validate(etNombreCli, new String[]{validator.REQUIRED}) &&
                !validator.validate(etApPaternoCli, new String[]{validator.ONLY_TEXT}) &&
                !validator.validate(etApMaternoCli, new String[]{validator.ONLY_TEXT}) &&
                !validatorTV.validate(tvFechaNacCli, new String[]{validatorTV.REQUIRED}) &&
                !validatorTV.validate(tvEdadCli, new String[]{validatorTV.REQUIRED, validatorTV.ONLY_NUMBER})) {
            if (rgGeneroCli.getCheckedRadioButtonId() == R.id.rbHombre ||
                    rgGeneroCli.getCheckedRadioButtonId() == R.id.rbMujer) {
                tvGeneroCli.setError(null);
                if (!validatorTV.validate(tvEstadoNacCli, new String[]{validatorTV.REQUIRED}) &&
                        !validator.validate(etCurpCli, new String[]{validator.REQUIRED, validator.CURP}) &&
                        (!validatorTV.validate(tvRfcCli, new String[]{validatorTV.REQUIRED}) &&
                                !Miscellaneous.GetStr(tvRfcCli).equals("Rfc no válida"))) {
                    if (Miscellaneous.CurpValidador(Miscellaneous.GetStr(etCurpCli))) {
                        if (!validatorTV.validate(tvOcupacionCli, new String[]{validatorTV.REQUIRED}) &&
                                !validatorTV.validate(tvActividadEcoCli, new String[]{validatorTV.REQUIRED}) &&
                                !validatorTV.validate(tvTipoIdentificacion, new String[]{validatorTV.REQUIRED}) &&
                                !validator.validate(etNumIdentifCli, new String[]{validator.REQUIRED, validator.ALFANUMERICO}) &&
                                !validatorTV.validate(tvEstudiosCli, new String[]{validatorTV.REQUIRED}) &&
                                !validatorTV.validate(tvEstadoCivilCli, new String[]{validatorTV.REQUIRED})) {
                            boolean flag_est_civil = false;
                            if (Miscellaneous.GetStr(tvEstadoCivilCli).equals("CASADO(A)")) {
                                if (rgBienes.getCheckedRadioButtonId() == R.id.rbMancomunados ||
                                        rgBienes.getCheckedRadioButtonId() == R.id.rbSeparados) {
                                    tvBienes.setError(null);
                                    int checkedRadioButtonId = rgBienes.getCheckedRadioButtonId();
                                    if (checkedRadioButtonId == R.id.rbMancomunados) {
                                        cv.put("bienes", 1);
                                    } else if (checkedRadioButtonId == R.id.rbSeparados) {
                                        cv.put("bienes", 2);
                                    }
                                    flag_est_civil = true;
                                }
                            } else {
                                flag_est_civil = true;
                                cv.put("bienes", 0);
                            }

                            if (flag_est_civil) {
                                if (!validatorTV.validate(tvTipoCasaCli, new String[]{validatorTV.REQUIRED})) {
                                    boolean flag_tipo_casa = false;
                                    cv.put("tipo_vivienda", Miscellaneous.GetStr(tvTipoCasaCli));
                                    switch (Miscellaneous.GetStr(tvTipoCasaCli)) {
                                        case "CASA FAMILIAR":
                                        case "CASA RENTADA":
                                            if (!validatorTV.validate(tvCasaFamiliar, new String[]{validatorTV.REQUIRED})) {
                                                flag_tipo_casa = true;
                                                cv.put("parentesco", Miscellaneous.GetStr(tvCasaFamiliar));
                                                cv.put("otro_tipo_vivienda", "");
                                            } else
                                                ivError2.setVisibility(View.VISIBLE);

                                            break;
                                        case "OTRO":
                                            if (!validator.validate(etOTroTipoCli, new String[]{validator.REQUIRED})) {
                                                flag_tipo_casa = true;
                                                cv.put("parentesco", "");
                                                cv.put("otro_tipo_vivienda", Miscellaneous.GetStr(etOTroTipoCli));
                                            } else
                                                ivError2.setVisibility(View.VISIBLE);
                                            break;
                                        default:
                                            flag_tipo_casa = true;
                                            cv.put("parentesco", "");
                                            cv.put("otro_tipo_vivienda", "");
                                            break;
                                    }
                                    if (flag_tipo_casa) {
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
                                                    !validator.validate(etEmail, new String[]{validator.EMAIL})) {
                                                /*if (byteFotoFachCli != null){
                                                    tvFachadaCli.setError(null);*/
                                                //if (!validator.validate(etReferenciCli, new String[]{validator.REQUIRED})){
                                                if (byteFirmaCli != null) {
                                                    tvFirmaCli.setError(null);
                                                    ivError2.setVisibility(View.GONE);
                                                    cv.put("nombre", Miscellaneous.GetStr(etNombreCli));
                                                    cv.put("paterno", Miscellaneous.GetStr(etApPaternoCli));
                                                    cv.put("materno", Miscellaneous.GetStr(etApMaternoCli));
                                                    cv.put("fecha_nacimiento", Miscellaneous.GetStr(tvFechaNacCli));
                                                    cv.put("edad", Miscellaneous.GetStr(tvEdadCli));
                                                    int checkedRadioButtonId = rgGeneroCli.getCheckedRadioButtonId();
                                                    if (checkedRadioButtonId == R.id.rbHombre) {
                                                        cv.put("genero", 0);
                                                    } else if (checkedRadioButtonId == R.id.rbMujer) {
                                                        cv.put("genero", 1);
                                                    }
                                                    cv.put("estado_nacimiento", Miscellaneous.GetStr(tvEstadoNacCli));
                                                    cv.put("rfc", Miscellaneous.GetStr(tvRfcCli));
                                                    cv.put("curp", Miscellaneous.GetStr(etCurpCli));
                                                    cv.put("ocupacion", Miscellaneous.GetStr(tvOcupacionCli));
                                                    cv.put("actividad_economica", Miscellaneous.GetStr(tvActividadEcoCli));
                                                    cv.put("tipo_identificacion", Miscellaneous.GetStr(tvTipoIdentificacion));
                                                    cv.put("no_identificacion", Miscellaneous.GetStr(etNumIdentifCli));
                                                    cv.put("nivel_estudio", Miscellaneous.GetStr(tvEstudiosCli));
                                                    cv.put("estado_civil", Miscellaneous.GetStr(tvEstadoCivilCli));
                                                    cv.put("tel_casa", Miscellaneous.GetStr(etTelCasaCli));
                                                    cv.put("tel_celular", Miscellaneous.GetStr(etCelularCli));
                                                    cv.put("tel_mensajes", Miscellaneous.GetStr(etTelMensCli));
                                                    cv.put("tel_trabajo", Miscellaneous.GetStr(etTelTrabajoCli));
                                                    cv.put("tiempo_vivir_sitio", Integer.parseInt(Miscellaneous.GetStr(etTiempoSitio)));
                                                    cv.put("dependientes", Miscellaneous.GetStr(tvDependientes));
                                                    cv.put("medio_contacto", Miscellaneous.GetStr(tvEnteroNosotros));
                                                    cv.put("estado_cuenta", Miscellaneous.GetStr(tvEstadoCuenta));
                                                    cv.put("email", Miscellaneous.GetStr(etEmail));
                                                    cv.put("ref_domiciliaria", Miscellaneous.GetStr(etReferenciCli));

                                                    db.update(TBL_CLIENTE_IND, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});

                                                    cv = new ContentValues();
                                                    cv.put("latitud", String.valueOf(latLngUbiCli.latitude));
                                                    cv.put("longitud", String.valueOf(latLngUbiCli.longitude));
                                                    cv.put("calle", Miscellaneous.GetStr(etCalleCli));
                                                    cv.put("num_exterior", Miscellaneous.GetStr(etNoExtCli));
                                                    cv.put("num_interior", Miscellaneous.GetStr(etNoIntCli));
                                                    cv.put("manzana", Miscellaneous.GetStr(etManzanaCli));
                                                    cv.put("lote", Miscellaneous.GetStr(etLoteCli));
                                                    cv.put("cp", Miscellaneous.GetStr(etCpCli));
                                                    cv.put("colonia", Miscellaneous.GetStr(tvColoniaCli));
                                                    cv.put("ciudad", Miscellaneous.GetStr(etCiudadCli));
                                                    cv.put("localidad", Miscellaneous.GetStr(tvLocalidadCli));
                                                    cv.put("municipio", Miscellaneous.GetStr(tvMunicipioCli));
                                                    cv.put("estado", Miscellaneous.GetStr(tvEstadoCli));
                                                    db.update(TBL_DIRECCIONES, cv, "id_direccion = ? AND tipo_direccion = ?", new String[]{direccionIdCli, "CLIENTE"});
                                                    save_cliente = true;
                                                } else {
                                                    ivError2.setVisibility(View.VISIBLE);
                                                    tvFirmaCli.setError("");
                                                }
                                                    /*}
                                                    else
                                                        ivError2.setVisibility(View.VISIBLE);*/
                                                /*}
                                                else{
                                                    ivError2.setVisibility(View.VISIBLE);
                                                    tvFachadaCli.setError("");
                                                }*/
                                            } else
                                                ivError2.setVisibility(View.VISIBLE);
                                        } else {
                                            ivError2.setVisibility(View.VISIBLE);
                                            tvMapaCli.setError("CAMPO REQUERIDO");
                                            Toast.makeText(ctx, "ESTE CAMPO ES REQUERIDO", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                } else
                                    ivError2.setVisibility(View.VISIBLE);
                            } else {
                                ivError2.setVisibility(View.VISIBLE);
                                tvBienes.setError("");
                            }
                        } else
                            ivError2.setVisibility(View.VISIBLE);
                    } else {
                        ivError2.setVisibility(View.VISIBLE);
                        etCurpCli.setError("Curp no válida");
                    }
                } else
                    ivError2.setVisibility(View.VISIBLE);
            } else {
                ivError2.setVisibility(View.VISIBLE);
                tvGeneroCli.setError("");
            }
        } else
            ivError2.setVisibility(View.VISIBLE);

        if (
                !validator.validate(etNoExtCli, new String[]{validator.REQUIRED})
                        || (
                        !validator.validate(etManzanaCli, new String[]{validator.REQUIRED})
                                && !validator.validate(etLoteCli, new String[]{validator.REQUIRED})
                )
        ) {
            etNoExtCli.setError(null);
            etManzanaCli.setError(null);
            etLoteCli.setError(null);
        }

        return save_cliente;
    }

    private boolean saveDatosCampana() {
        ContentValues cv = new ContentValues();
        String txtCampanaValue = txtCampana.getText().toString();
        String txtNombreRefieroValue = txtNombreRefiero.getText().toString();
        boolean validationHasErrors = validatorTV.validate(txtCampana, new String[]{validator.REQUIRED}) && validator.validate(txtNombreRefiero, new String[]{validator.REQUIRED});

        if (validationHasErrors) {
            txtCampana.setError("Esta campo es requerido");
            txtNombreRefiero.setError("Este campo es requerido");
            return false;
        }

        if (
                (txtCampanaValue.equals("SUMA Y GANA") || txtCampanaValue.equals("RESCATE Y GANA")) &&
                        txtNombreRefieroValue.equals(SIN_REFERENCIA)
        ) {
            txtCampana.setError("");
            txtNombreRefiero.setError("");
            Toast.makeText(ctx, "NO SE PUEDE ASOCIAR LA CAMPAÑA", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            cv.put("id_solicitud", id_solicitud);
            cv.put("id_campana", Miscellaneous.selectCampana(ctx, Miscellaneous.GetStr(txtCampana)));
            cv.put("tipo_campana", Miscellaneous.GetStr(txtCampana));
            cv.put("nombre_refiero", Miscellaneous.GetStr(txtNombreRefiero));

            boolean auxiliar = datosCampanaDao.validarEstatus(ctx, Integer.parseInt(String.valueOf(id_solicitud)));
            if (auxiliar) {
                db.update(TBL_DATOS_CREDITO_CAMPANA, cv, " id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
            } else {
                db.insert(TBL_DATOS_CREDITO_CAMPANA, null, cv);
            }
            return true;
        }
    }

    private boolean saveBeneficiario() {
        ContentValues cv = new ContentValues();
        int grupo_id = 1;
        boolean estatus = BeneficiarioDao.validarBeneficiarioInd(Integer.parseInt(String.valueOf(id_solicitud)), ctx);
        int serieIdA = BeneficiarioDao.obtenerSerieAsesor(ctx);//Miscellaneous.obtenerSerieAsesor(ctx);
        boolean save_beneficiario = false;

        if (!validator.validate(txtNomBeneficiario, new String[]{validator.REQUIRED})) {
            if (!validator.validate(txtApellidoPaternoBeneficiario, new String[]{validator.ONLY_TEXT})) {
                if (!validator.validate(txtApellidoMaternoBeneficiario, new String[]{validator.ONLY_TEXT})) {
                    if (!validatorTV.validate(txtParentescoBeneficiario, new String[]{validatorTV.REQUIRED})) {

                        cv.put("id_solicitud", id_solicitud);
                        cv.put("id_originacion", 0);
                        cv.put("id_cliente", 0);
                        cv.put("id_grupo", grupo_id);
                        cv.put("nombre", Miscellaneous.GetStr(txtNomBeneficiario));
                        cv.put("paterno", Miscellaneous.GetStr(txtApellidoPaternoBeneficiario));
                        cv.put("materno", Miscellaneous.GetStr(txtApellidoMaternoBeneficiario));
                        cv.put("parentesco", Miscellaneous.GetStr(txtParentescoBeneficiario));
                        cv.put("serieid", serieIdA);

                        if (!estatus) {
                            db.insert(TBL_DATOS_BENEFICIARIO, null, cv);
                        }

                        if (estatus) {
                            db.update(TBL_DATOS_BENEFICIARIO, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                        }

                        save_beneficiario = true;
                    } else {
                        ivError11.setVisibility(View.VISIBLE);
                        txtParentescoBeneficiario.setError("CAMPO REQUERIDO");
                        showError(txtParentescoBeneficiario, "CAMPO REQUERIDO");
                    }
                } else {
                    ivError11.setVisibility(View.VISIBLE);
                    txtApellidoPaternoBeneficiario.setError("CAMPO REQUERIDO");
                    showError(txtApellidoMaternoBeneficiario, "CAMPO REQUERIDO");
                }
            } else {
                ivError11.setVisibility(View.VISIBLE);
                showError(txtApellidoPaternoBeneficiario, "CAMPO REQUERIDO");
            }
        } else {
            ivError11.setVisibility(View.VISIBLE);
            showError(txtNomBeneficiario, "CAMPO REQUERIDO");
        }
        return save_beneficiario;
    }

    private boolean saveConyuge() {
        boolean save_conyuge = false;
        if (!validator.validate(etNombreCony, new String[]{validator.REQUIRED, validator.ONLY_TEXT}) &&
                !validator.validate(etApPaternoCony, new String[]{validator.ONLY_TEXT}) &&
                !validator.validate(etApMaternoCony, new String[]{validator.ONLY_TEXT}) &&
                !validator.validate(etNacionalidadCony, new String[]{validator.REQUIRED, validator.ONLY_TEXT}) &&
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
                !validator.validate(etCiudadCony, new String[]{validator.REQUIRED, validator.ONLY_TEXT}) &&
                !validatorTV.validate(tvLocalidadCony, new String[]{validatorTV.REQUIRED}) &&
                !Miscellaneous.ValidTextView(tvMunicipioCony) &&
                !Miscellaneous.ValidTextView(tvEstadoCony) &&
                !validator.validate(etIngMenCony, new String[]{validator.REQUIRED, validator.ONLY_NUMBER}) &&
                !validator.validate(etGastoMenCony, new String[]{validator.REQUIRED, validator.ONLY_NUMBER}) &&
                !validator.validate(etCasaCony, new String[]{validator.PHONE}) &&
                !validator.validate(etCelularCony, new String[]{validator.REQUIRED, validator.PHONE})) {
            Log.e("Conyuge", "pasa");
            ivError3.setVisibility(View.GONE);
            ContentValues cv = new ContentValues();
            cv.put("nombre", Miscellaneous.GetStr(etNombreCony));
            cv.put("paterno", Miscellaneous.GetStr(etApPaternoCony));
            cv.put("materno", Miscellaneous.GetStr(etApMaternoCony));
            cv.put("nacionalidad", Miscellaneous.GetStr(etNacionalidadCony));
            cv.put("ocupacion", Miscellaneous.GetStr(tvOcupacionCony));
            cv.put("ing_mensual", Miscellaneous.GetStr(etIngMenCony).replace(",", ""));
            cv.put("gasto_mensual", Miscellaneous.GetStr(etGastoMenCony).toUpperCase().replace(",", ""));
            cv.put("tel_casa", Miscellaneous.GetStr(etCasaCony));
            cv.put("tel_celular", Miscellaneous.GetStr(etCelularCony));

            db.update(TBL_CONYUGE_IND, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});

            cv = new ContentValues();
            cv.put("calle", Miscellaneous.GetStr(etCalleCony));
            cv.put("num_exterior", Miscellaneous.GetStr(etNoExtCony));
            cv.put("num_interior", Miscellaneous.GetStr(etNoIntCony));
            cv.put("manzana", Miscellaneous.GetStr(etManzanaCony));
            cv.put("lote", Miscellaneous.GetStr(etLoteCony));
            cv.put("cp", Miscellaneous.GetStr(etCpCony));
            cv.put("colonia", Miscellaneous.GetStr(tvColoniaCony));
            cv.put("ciudad", Miscellaneous.GetStr(etCiudadCony));
            cv.put("localidad", Miscellaneous.GetStr(tvLocalidadCony));
            cv.put("municipio", Miscellaneous.GetStr(tvMunicipioCony));
            cv.put("estado", Miscellaneous.GetStr(tvEstadoCony));
            db.update(TBL_DIRECCIONES, cv, "id_direccion = ? AND tipo_direccion = ?", new String[]{direccionIdCony, "CONYUGE"});

            save_conyuge = true;
        } else
            ivError3.setVisibility(View.VISIBLE);

        if (
                !validator.validate(etNoExtCony, new String[]{validator.REQUIRED})
                        || (
                        !validator.validate(etManzanaCony, new String[]{validator.REQUIRED})
                                && !validator.validate(etLoteCony, new String[]{validator.REQUIRED})
                )
        ) {
            etNoExtCony.setError(null);
            etManzanaCony.setError(null);
            etLoteCony.setError(null);
        }

        return save_conyuge;
    }

    private boolean saveDatosEconomicos() {
        boolean save_economicos = false;
        if (!validator.validate(etPropiedadesEco, new String[]{validator.REQUIRED}) &&
                !validator.validate(etValorAproxEco, new String[]{validator.REQUIRED, validator.ONLY_NUMBER}) &&
                !validator.validate(etUbicacionEco, new String[]{validator.REQUIRED, validator.GENERAL}) &&
                !validatorTV.validate(tvIngresoEco, new String[]{validatorTV.REQUIRED})) {
            ivError4.setVisibility(View.GONE);
            ContentValues cv = new ContentValues();
            cv.put("propiedades", Miscellaneous.GetStr(etPropiedadesEco));
            cv.put("valor_aproximado", Miscellaneous.GetStr(etValorAproxEco));
            cv.put("ubicacion", Miscellaneous.GetStr(etUbicacionEco));
            cv.put("ingreso", Miscellaneous.GetStr(tvIngresoEco).replace(",", ""));
            cv.put("estatus_completado", 1);

            db.update(TBL_ECONOMICOS_IND, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});

            save_economicos = true;
        } else
            ivError4.setVisibility(View.VISIBLE);

        return save_economicos;
    }

    private boolean saveDatosNegocio() {
        boolean save_negocio = false;
        if (!validator.validate(etNombreNeg, new String[]{validator.REQUIRED, validator.GENERAL})) {
            if (latLngUbiNeg != null || pushMapButtonNeg) {
                tvMapaNeg.setError(null);
                if (!validator.validate(etCalleNeg, new String[]{validator.REQUIRED}) &&
                        (
                                !validator.validate(etNoExtNeg, new String[]{validator.REQUIRED})
                                        || (
                                        !validator.validate(etManzanaNeg, new String[]{validator.REQUIRED})
                                                && !validator.validate(etLoteNeg, new String[]{validator.REQUIRED})
                                )
                        ) &&
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
                        !validatorTV.validate(tvDestinoNeg, new String[]{validatorTV.REQUIRED})) {
                    boolean otro_destino = false;
                    if (Miscellaneous.GetStr(tvDestinoNeg).equals("OTRO")) {
                        if (!validator.validate(etOtroDestinoNeg, new String[]{validator.REQUIRED})) {
                            otro_destino = true;
                        }
                    } else
                        otro_destino = true;
                    if (otro_destino) {
                        if (!validator.validate(etAntiguedadNeg, new String[]{validator.REQUIRED, validator.YEARS}) &&
                                !validator.validate(etIngMenNeg, new String[]{validator.REQUIRED, validator.ONLY_NUMBER}) &&
                                !validator.validate(etOtrosIngNeg, new String[]{validator.REQUIRED, validator.ONLY_NUMBER}) &&
                                !validator.validate(etGastosMenNeg, new String[]{validator.REQUIRED, validator.ONLY_NUMBER}) &&
                                !validator.validate(etGastosAguaNeg, new String[]{validator.REQUIRED, validator.ONLY_NUMBER}) &&
                                !validator.validate(etGastosLuzNeg, new String[]{validator.REQUIRED, validator.ONLY_NUMBER}) &&
                                !validator.validate(etGastosTelNeg, new String[]{validator.REQUIRED, validator.ONLY_NUMBER}) &&
                                !validator.validate(etGastosRentaNeg, new String[]{validator.REQUIRED, validator.ONLY_NUMBER}) &&
                                !validator.validate(etGastosOtrosNeg, new String[]{validator.REQUIRED, validator.ONLY_NUMBER})) {
                            if (getGastoMen()) {
                                if (!validator.validate(etCapacidadPagoNeg, new String[]{validator.REQUIRED, validator.MONEY}) &&
                                        !validatorTV.validate(tvMediosPagoNeg, new String[]{validatorTV.REQUIRED})) {
                                    boolean otro_medio = false;
                                    if (Miscellaneous.GetStr(tvMediosPagoNeg).contains("OTRO")) {
                                        if (!validator.validate(etOtroMedioPagoNeg, new String[]{validator.REQUIRED}))
                                            otro_medio = true;
                                    } else
                                        otro_medio = true;

                                    if (otro_medio) {
                                        if (!validatorTV.validate(tvMontoMaxNeg, new String[]{validatorTV.REQUIRED}) &&
                                                !validatorTV.validate(tvNumOperacionNeg, new String[]{validatorTV.REQUIRED}) &&
                                                (Miscellaneous.GetStr(tvMediosPagoNeg).contains("EFECTIVO") && !validatorTV.validate(etNumOperacionEfectNeg, new String[]{validatorTV.REQUIRED})) || !Miscellaneous.GetStr(tvMediosPagoNeg).contains("EFECTIVO")) {
                                            if (!validatorTV.validate(tvDiasVentaNeg, new String[]{validatorTV.REQUIRED})) {
                                                boolean flag = true;

                                                try {
                                                    if (Double.parseDouble(etIngMenNeg.getText().toString().replace(",", "")) <= 100) {
                                                        flag = false;
                                                        etIngMenNeg.setError("El ingreso mensual debe ser mayor a 100!");
                                                    }
                                                } catch (Exception e) {
                                                    etIngMenNeg.setError(e.getMessage());
                                                    flag = false;
                                                }

                                                try {
                                                    if (Double.parseDouble(etGastosMenNeg.getText().toString().replace(",", "")) <= 100) {
                                                        flag = false;
                                                        etGastosMenNeg.setError("El gasto mensual debe ser mayor a 100!");
                                                    }
                                                } catch (Exception e) {
                                                    etGastosMenNeg.setError(e.getMessage());
                                                    flag = false;
                                                }

                                                if (flag) {
                                                            /*if (byteFotoFachNeg != null) {
                                                                tvFachadaNeg.setError(null);*/
                                                    if (!validator.validate(etReferenciNeg, new String[]{validator.REQUIRED, validator.GENERAL})) {
                                                        ivError5.setVisibility(View.GONE);
                                                        ContentValues cv = new ContentValues();
                                                        cv.put("nombre", Miscellaneous.GetStr(etNombreNeg));
                                                        cv.put("actividad_economica", Miscellaneous.GetStr(tvActEconomicaNeg));
                                                        cv.put("destino_credito", Miscellaneous.GetStr(tvDestinoNeg));
                                                        cv.put("otro_destino", Miscellaneous.GetStr(etOtroDestinoNeg));
                                                        cv.put("antiguedad", Integer.parseInt(Miscellaneous.GetStr(etAntiguedadNeg)));
                                                        cv.put("ing_mensual", Miscellaneous.GetStr(etIngMenNeg).replace(",", ""));
                                                        cv.put("ing_otros", Miscellaneous.GetStr(etOtrosIngNeg).replace(",", ""));
                                                        cv.put("gasto_semanal", Miscellaneous.GetStr(etGastosMenNeg).replace(",", ""));
                                                        cv.put("gasto_agua", Miscellaneous.GetStr(etGastosAguaNeg).replace(",", ""));
                                                        cv.put("gasto_luz", Miscellaneous.GetStr(etGastosLuzNeg).replace(",", ""));
                                                        cv.put("gasto_telefono", Miscellaneous.GetStr(etGastosTelNeg).replace(",", ""));
                                                        cv.put("gasto_renta", Miscellaneous.GetStr(etGastosRentaNeg).replace(",", ""));
                                                        cv.put("gasto_otros", Miscellaneous.GetStr(etGastosOtrosNeg).replace(",", ""));
                                                        cv.put("capacidad_pago", Miscellaneous.GetStr(etCapacidadPagoNeg).replace(",", ""));
                                                        cv.put("medio_pago", Miscellaneous.GetStr(tvMediosPagoNeg));
                                                        cv.put("otro_medio_pago", Miscellaneous.GetStr(etOtroMedioPagoNeg));
                                                        cv.put("monto_maximo", Miscellaneous.GetStr(tvMontoMaxNeg).replace(",", ""));
                                                        cv.put("dias_venta", Miscellaneous.GetStr(tvDiasVentaNeg));
                                                        cv.put("ref_domiciliaria", Miscellaneous.GetStr(etReferenciNeg));

                                                        db.update(TBL_NEGOCIO_IND, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});

                                                        cv = new ContentValues();
                                                        cv.put("calle", Miscellaneous.GetStr(etCalleNeg));
                                                        cv.put("num_exterior", Miscellaneous.GetStr(etNoExtNeg));
                                                        cv.put("num_interior", Miscellaneous.GetStr(etNoIntNeg));
                                                        cv.put("manzana", Miscellaneous.GetStr(etManzanaNeg));
                                                        cv.put("lote", Miscellaneous.GetStr(etLoteNeg));
                                                        cv.put("cp", Miscellaneous.GetStr(etCpNeg));
                                                        cv.put("colonia", Miscellaneous.GetStr(tvColoniaNeg));
                                                        cv.put("ciudad", Miscellaneous.GetStr(etCiudadNeg));
                                                        cv.put("localidad", Miscellaneous.GetStr(tvLocalidadNeg));
                                                        cv.put("municipio", Miscellaneous.GetStr(tvMunicipioNeg));
                                                        db.update(TBL_DIRECCIONES, cv, "id_direccion = ? AND tipo_direccion = ?", new String[]{direccionIdNeg, "NEGOCIO"});

                                                        save_negocio = true;
                                                    } else
                                                        ivError5.setVisibility(View.VISIBLE);
                                                            /*} else {
                                                                ivError5.setVisibility(View.VISIBLE);
                                                                tvFachadaNeg.setError("");
                                                            }*/
                                                } else {
                                                    ivError5.setVisibility(View.VISIBLE);
                                                }
                                            } else
                                                ivError5.setVisibility(View.VISIBLE);
                                        } else
                                            ivError5.setVisibility(View.VISIBLE);
                                    } else
                                        ivError5.setVisibility(View.VISIBLE);
                                } else
                                    ivError5.setVisibility(View.VISIBLE);
                            } else {
                                etGastosMenNeg.setError("La suma de todos los gastos no puede ser cero (0).");
                                ivError5.setVisibility(View.VISIBLE);
                            }
                        } else
                            ivError5.setVisibility(View.VISIBLE);
                    } else
                        ivError5.setVisibility(View.VISIBLE);
                } else
                    ivError5.setVisibility(View.VISIBLE);
            } else {
                tvMapaNeg.setError("");
                ivError5.setVisibility(View.VISIBLE);
            }
        } else
            ivError5.setVisibility(View.VISIBLE);

        if (
                !validator.validate(etNoExtNeg, new String[]{validator.REQUIRED})
                        || (
                        !validator.validate(etManzanaNeg, new String[]{validator.REQUIRED})
                                && !validator.validate(etLoteNeg, new String[]{validator.REQUIRED})
                )
        ) {
            etNoExtNeg.setError(null);
            etManzanaNeg.setError(null);
            etLoteNeg.setError(null);
        }

        return save_negocio;
    }

    private boolean saveDatosAval() {
        boolean save_aval = false;
        ContentValues cv = new ContentValues();

        if (
                !validator.validate(etNombreAval, new String[]{validator.REQUIRED, validator.ONLY_TEXT}) &&
                        !validator.validate(etApPaternoAval, new String[]{validator.ONLY_TEXT}) &&
                        !validator.validate(etApMaternoAval, new String[]{validator.ONLY_TEXT}) &&
                        !validatorTV.validate(tvFechaNacAval, new String[]{validatorTV.REQUIRED}) &&
                        !validatorTV.validate(tvEdadAval, new String[]{validatorTV.REQUIRED, validatorTV.ONLY_NUMBER})
        ) {
            if (
                    rgGeneroAval.getCheckedRadioButtonId() == R.id.rbHombre ||
                            rgGeneroAval.getCheckedRadioButtonId() == R.id.rbMujer
            ) {
                tvGeneroAval.setError(null);

                if (
                        !validatorTV.validate(tvEstadoNacAval, new String[]{validatorTV.REQUIRED}) &&
                                !validator.validate(etCurpAval, new String[]{validator.REQUIRED, validator.CURP}) &&
                                (!Miscellaneous.GetStr(tvRfcAval).equals("Rfc no válida"))
                ) {
                    if (Miscellaneous.CurpValidador(Miscellaneous.GetStr(etCurpAval))) {
                        if (
                                !validatorTV.validate(tvParentescoAval, new String[]{validatorTV.REQUIRED}) &&
                                        !validatorTV.validate(tvTipoIdentificacionAval, new String[]{validatorTV.REQUIRED}) &&
                                        !validator.validate(etNumIdentifAval, new String[]{validator.REQUIRED, validator.ALFANUMERICO}) &&
                                        !validatorTV.validate(tvOcupacionAval, new String[]{validatorTV.REQUIRED}) &&
                                        !validatorTV.validate(tvActividadEcoAval, new String[]{validatorTV.REQUIRED})
                        ) {
                            if (latLngUbiAval != null || pushMapButtonAval) {
                                tvMapaAval.setError(null);
                                if (
                                        !validator.validate(etCalleAval, new String[]{validator.REQUIRED}) &&
                                                (
                                                        !validator.validate(etNoExtAval, new String[]{validator.REQUIRED})
                                                                || (
                                                                !validator.validate(etManzanaAval, new String[]{validator.REQUIRED})
                                                                        && !validator.validate(etLoteAval, new String[]{validator.REQUIRED})
                                                        )
                                                ) &&
                                                !validator.validate(etCpAval, new String[]{validator.REQUIRED, validator.ONLY_NUMBER, validator.CP}) &&
                                                !Miscellaneous.ValidTextView(tvColoniaAval) &&
                                                !validator.validate(etCiudadAval, new String[]{validator.REQUIRED, validator.ONLY_TEXT}) &&
                                                !validatorTV.validate(tvLocalidadAval, new String[]{validatorTV.REQUIRED}) &&
                                                !Miscellaneous.ValidTextView(tvMunicipioAval) &&
                                                !Miscellaneous.ValidTextView(tvEstadoAval) &&
                                                !validatorTV.validate(tvTipoCasaAval, new String[]{validatorTV.REQUIRED})
                                ) {
                                    cv.put("tipo_vivienda", Miscellaneous.GetStr(tvTipoCasaAval));
                                    boolean flag_tipo_casa = false;
                                    if (
                                            Miscellaneous.GetStr(tvTipoCasaAval).equals("CASA FAMILIAR") ||
                                                    Miscellaneous.GetStr(tvTipoCasaAval).equals("CASA RENTADA")
                                    ) {
                                        if (
                                                !validatorTV.validate(tvFamiliarAval, new String[]{validatorTV.REQUIRED}) &&
                                                        !validator.validate(etNombreTitularAval, new String[]{validator.REQUIRED, validator.ONLY_TEXT})
                                        ) {
                                            cv.put("nombre_titular", Miscellaneous.GetStr(etNombreTitularAval));
                                            cv.put("parentesco", Miscellaneous.GetStr(tvFamiliarAval));
                                            flag_tipo_casa = true;
                                        } else {
                                            ivError6.setVisibility(View.VISIBLE);
                                        }
                                    } else {
                                        cv.put("nombre_titular", "");
                                        cv.put("parentesco", "");
                                        flag_tipo_casa = true;
                                    }

                                    if (flag_tipo_casa) {
                                        if (!validator.validate(etCaracteristicasAval, new String[]{validator.REQUIRED})) {
                                            if (
                                                    rgNegocioAval.getCheckedRadioButtonId() == R.id.rbSiNeg ||
                                                            rgNegocioAval.getCheckedRadioButtonId() == R.id.rbNoNeg
                                            ) {
                                                tvNombreNegAval.setError(null);

                                                if (
                                                        (
                                                                (
                                                                        rgNegocioAval.getCheckedRadioButtonId() == R.id.rbSiNeg
                                                                                && !validator.validate(etNombreNegocioAval, new String[]{validator.REQUIRED})
                                                                                && !validator.validate(etAntiguedadAval, new String[]{validator.REQUIRED, validator.YEARS})
                                                                )
                                                                        || rgNegocioAval.getCheckedRadioButtonId() == R.id.rbNoNeg
                                                        ) &&
                                                                !validator.validate(etIngMenAval, new String[]{validator.REQUIRED, validator.ONLY_NUMBER}) &&
                                                                !validator.validate(etIngOtrosAval, new String[]{validator.REQUIRED, validator.ONLY_NUMBER}) &&
                                                                !validator.validate(etGastosSemAval, new String[]{validator.REQUIRED, validator.ONLY_NUMBER}) &&
                                                                !validator.validate(etGastosAguaAval, new String[]{validator.REQUIRED, validator.ONLY_NUMBER}) &&
                                                                !validator.validate(etGastosLuzAval, new String[]{validator.REQUIRED, validator.ONLY_NUMBER}) &&
                                                                !validator.validate(etGastosTelAval, new String[]{validator.REQUIRED, validator.ONLY_NUMBER}) &&
                                                                !validator.validate(etGastosRentaAval, new String[]{validator.REQUIRED, validator.ONLY_NUMBER}) &&
                                                                !validator.validate(etGastosOtrosAval, new String[]{validator.REQUIRED, validator.ONLY_NUMBER}) &&
                                                                !validatorTV.validate(tvMediosPagoAval, new String[]{validatorTV.REQUIRED}) &&
                                                                (
                                                                        !Miscellaneous.GetStr(tvMediosPagoAval).contains("OTRO") ||
                                                                                !validator.validate(etOtroMedioPagoAval, new String[]{validator.REQUIRED})
                                                                ) &&
                                                                !validatorTV.validate(tvMontoMaxAval, new String[]{validatorTV.REQUIRED}) &&
                                                                !validator.validate(etCapacidadPagoAval, new String[]{validator.REQUIRED, validator.MONEY}) &&
                                                                !validatorTV.validate(tvHoraLocAval, new String[]{validatorTV.REQUIRED}) &&
                                                                //!validatorTV.validate(tvActivosObservables, new String[]{validatorTV.REQUIRED}) &&
                                                                !validator.validate(etTelCasaAval, new String[]{validator.PHONE}) &&
                                                                !validator.validate(etCelularAval, new String[]{validator.REQUIRED, validator.PHONE}) &&
                                                                !validator.validate(etTelMensAval, new String[]{validator.PHONE}) &&
                                                                !validator.validate(etTelTrabajoAval, new String[]{validator.PHONE}) &&
                                                                !validator.validate(etEmailAval, new String[]{validator.EMAIL})
                                                ) {

                                                    boolean flag = true;

                                                    try {
                                                        if (Double.parseDouble(etIngMenAval.getText().toString().replace(",", "")) <= 100) {
                                                            flag = false;
                                                            etIngMenAval.setError("El ingreso mensual debe ser mayor a 100!");
                                                        }
                                                    } catch (Exception e) {
                                                        etIngMenAval.setError(e.getMessage());
                                                        flag = false;
                                                    }

                                                    try {
                                                        if (Double.parseDouble(etGastosSemAval.getText().toString().replace(",", "")) <= 100) {
                                                            flag = false;
                                                            etGastosSemAval.setError("El gasto mensual debe ser mayor a 100!");
                                                        }
                                                    } catch (Exception e) {
                                                        etGastosSemAval.setError(e.getMessage());
                                                        flag = false;
                                                    }


                                                    if (byteIneFrontalAval != null) {
                                                        if (byteIneReversoAval != null) {
                                                            if (byteCurpAval != null) {
                                                                if (byteComprobanteAval == null) {
                                                                    flag = false;
                                                                    tvComprobanteAval.setError("");
                                                                }
                                                            } else {
                                                                flag = false;
                                                                tvCurpAval.setError("");
                                                            }
                                                        } else {
                                                            flag = false;
                                                            tvIneReversoAval.setError("");
                                                        }
                                                    } else {
                                                        flag = false;
                                                        tvIneFrontalAval.setError("");
                                                    }

                                                    if (flag) {

                                                        /*if (byteFotoFachAval != null) {
                                                            tvFachadaAval.setError(null);*/
                                                        if (!validator.validate(etReferenciaAval, new String[]{validator.REQUIRED})) {
                                                            if (byteFirmaAval != null) {
                                                                tvFirmaAval.setError(null);
                                                                ivError6.setVisibility(View.GONE);
                                                                cv.put("nombre", Miscellaneous.GetStr(etNombreAval));
                                                                cv.put("paterno", Miscellaneous.GetStr(etApPaternoAval));
                                                                cv.put("materno", Miscellaneous.GetStr(etApMaternoAval));
                                                                cv.put("fecha_nacimiento", Miscellaneous.GetStr(tvFechaNacAval));
                                                                cv.put("edad", Miscellaneous.GetStr(tvEdadAval));
                                                                int radioButtonId = rgGeneroAval.getCheckedRadioButtonId();
                                                                if (radioButtonId == R.id.rbHombre) {
                                                                    cv.put("genero", 0);
                                                                } else if (radioButtonId == R.id.rbMujer) {
                                                                    cv.put("genero", 1);
                                                                }
                                                                cv.put("estado_nacimiento", Miscellaneous.GetStr(tvEstadoNacAval));
                                                                cv.put("rfc", Miscellaneous.GetStr(tvRfcAval));
                                                                cv.put("curp", Miscellaneous.GetStr(etCurpAval));
                                                                cv.put("parentesco_cliente", Miscellaneous.GetStr(tvParentescoAval));
                                                                cv.put("tipo_identificacion", Miscellaneous.GetStr(tvTipoIdentificacionAval));
                                                                cv.put("no_identificacion", Miscellaneous.GetStr(etNumIdentifAval));
                                                                cv.put("ocupacion", Miscellaneous.GetStr(tvOcupacionAval));
                                                                cv.put("actividad_economica", Miscellaneous.GetStr(tvActividadEcoAval));
                                                                cv.put("caracteristicas_domicilio", Miscellaneous.GetStr(etCaracteristicasAval));
                                                                int checkedRadioButtonId = rgNegocioAval.getCheckedRadioButtonId();
                                                                if (checkedRadioButtonId == R.id.rbSiNeg) {
                                                                    cv.put("tiene_negocio", 1);
                                                                    cv.put("nombre_negocio", Miscellaneous.GetStr(etNombreNegocioAval));
                                                                } else if (checkedRadioButtonId == R.id.rbNoNeg) {
                                                                    cv.put("tiene_negocio", 2);
                                                                }
                                                                cv.put("ing_mensual", Miscellaneous.GetStr(etIngMenAval).replace(",", ""));
                                                                cv.put("ing_otros", Miscellaneous.GetStr(etIngOtrosAval).replace(",", ""));
                                                                cv.put("gasto_semanal", Miscellaneous.GetStr(etGastosSemAval).replace(",", ""));
                                                                cv.put("gasto_agua", Miscellaneous.GetStr(etGastosAguaAval).replace(",", ""));
                                                                cv.put("gasto_luz", Miscellaneous.GetStr(etGastosLuzAval).replace(",", ""));
                                                                cv.put("gasto_telefono", Miscellaneous.GetStr(etGastosTelAval).replace(",", ""));
                                                                cv.put("gasto_renta", Miscellaneous.GetStr(etGastosRentaAval).replace(",", ""));
                                                                cv.put("gasto_otros", Miscellaneous.GetStr(etGastosOtrosAval).replace(",", ""));
                                                                cv.put("medio_pago", Miscellaneous.GetStr(tvMediosPagoAval));
                                                                cv.put("otro_medio_pago", Miscellaneous.GetStr(etOtroMedioPagoAval));
                                                                cv.put("monto_maximo", Miscellaneous.GetStr(tvMontoMaxAval).replace(",", ""));
                                                                cv.put("capacidad_pagos", Miscellaneous.GetStr(etCapacidadPagoAval).replace(",", ""));
                                                                cv.put("horario_localizacion", Miscellaneous.GetStr(tvHoraLocAval));
                                                                cv.put("activos_observables", Miscellaneous.GetStr(tvActivosObservables));
                                                                cv.put("antigueda", Miscellaneous.GetStr(etAntiguedadAval));
                                                                cv.put("tel_casa", Miscellaneous.GetStr(etTelCasaAval));
                                                                cv.put("tel_celular", Miscellaneous.GetStr(etCelularAval));
                                                                cv.put("tel_mensajes", Miscellaneous.GetStr(etTelMensAval));
                                                                cv.put("tel_trabajo", Miscellaneous.GetStr(etTelTrabajoAval));
                                                                cv.put("email", Miscellaneous.GetStr(etEmailAval));
                                                                cv.put("ref_domiciliaria", Miscellaneous.GetStr(etReferenciaAval));

                                                                db.update(TBL_AVAL_IND, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});

                                                                cv = new ContentValues();
                                                                cv.put("latitud", String.valueOf(latLngUbiAval.latitude));
                                                                cv.put("longitud", String.valueOf(latLngUbiAval.longitude));
                                                                cv.put("calle", Miscellaneous.GetStr(etCalleAval));
                                                                cv.put("num_exterior", Miscellaneous.GetStr(etNoExtAval));
                                                                cv.put("num_interior", Miscellaneous.GetStr(etNoIntAval));
                                                                cv.put("manzana", Miscellaneous.GetStr(etManzanaAval));
                                                                cv.put("lote", Miscellaneous.GetStr(etLoteAval));
                                                                cv.put("cp", Miscellaneous.GetStr(etCpAval));
                                                                cv.put("colonia", Miscellaneous.GetStr(tvColoniaAval));
                                                                cv.put("ciudad", Miscellaneous.GetStr(etCiudadAval));
                                                                cv.put("localidad", Miscellaneous.GetStr(tvLocalidadAval));
                                                                cv.put("municipio", Miscellaneous.GetStr(tvMunicipioAval));
                                                                cv.put("estado", Miscellaneous.GetStr(tvEstadoAval));
                                                                db.update(TBL_DIRECCIONES, cv, "id_direccion = ? AND tipo_direccion = ?", new String[]{direccionIdAval, "AVAL"});
                                                                save_aval = true;
                                                            } else {
                                                                tvFirmaAval.setError("");
                                                            }
                                                        } else {
                                                            ivError6.setVisibility(View.VISIBLE);
                                                        }
                                                        /*}
                                                        else{
                                                            tvFachadaAval.setError("");
                                                            ivError6.setVisibility(View.VISIBLE);
                                                        }*/
                                                    } else {
                                                        ivError6.setVisibility(View.VISIBLE);
                                                    }

                                                } else {
                                                    ivError6.setVisibility(View.VISIBLE);
                                                }
                                            } else {
                                                tvNombreNegAval.setError("");
                                                ivError6.setVisibility(View.VISIBLE);
                                            }
                                        } else {
                                            ivError6.setVisibility(View.VISIBLE);
                                        }
                                    }
                                } else {
                                    ivError6.setVisibility(View.VISIBLE);
                                }

                            } else {
                                ivError6.setVisibility(View.VISIBLE);
                                tvMapaAval.setError("");
                            }
                        } else {
                            ivError6.setVisibility(View.VISIBLE);
                        }
                    } else {
                        ivError6.setVisibility(View.VISIBLE);
                        etCurpAval.setError("Curp no válida");
                    }
                } else {
                    ivError6.setVisibility(View.VISIBLE);
                }
            } else {
                ivError6.setVisibility(View.VISIBLE);
                tvGeneroAval.setError("");
            }
        } else {
            ivError6.setVisibility(View.VISIBLE);
        }

        if (
                !validator.validate(etNoExtAval, new String[]{validator.REQUIRED})
                        || (
                        !validator.validate(etManzanaAval, new String[]{validator.REQUIRED})
                                && !validator.validate(etLoteAval, new String[]{validator.REQUIRED})
                )
        ) {
            etNoExtAval.setError(null);
            etManzanaAval.setError(null);
            etLoteAval.setError(null);
        }

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
            cv.put("nombre", Miscellaneous.GetStr(etNombreRef));
            cv.put("paterno", Miscellaneous.GetStr(etApPaternoRef));
            cv.put("materno", Miscellaneous.GetStr(etApMaternoRef));
            cv.put("fecha_nacimiento", Miscellaneous.GetStr(tvFechaNacRef));
            cv.put("tel_celular", Miscellaneous.GetStr(etTelCelRef));

            db.update(TBL_REFERENCIA_IND, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});

            cv = new ContentValues();
            cv.put("calle", Miscellaneous.GetStr(etCalleRef));
            cv.put("num_exterior", Miscellaneous.GetStr(etNoExtRef));
            cv.put("num_interior", Miscellaneous.GetStr(etNoIntRef));
            cv.put("manzana", Miscellaneous.GetStr(etManzanaRef));
            cv.put("lote", Miscellaneous.GetStr(etLoteRef));
            cv.put("cp", Miscellaneous.GetStr(etCpRef));
            cv.put("colonia", Miscellaneous.GetStr(tvColoniaRef));
            cv.put("ciudad", Miscellaneous.GetStr(etCiudadRef));
            cv.put("localidad", Miscellaneous.GetStr(tvLocalidadRef));
            cv.put("municipio", Miscellaneous.GetStr(tvMunicipioRef));
            cv.put("estado", Miscellaneous.GetStr(tvEstadoRef));
            db.update(TBL_DIRECCIONES, cv, "id_direccion = ? AND tipo_direccion = ?", new String[]{direccionIdRef, "REFERENCIA"});

            save_referencia = true;
        } else
            ivError7.setVisibility(View.VISIBLE);

        return save_referencia;
    }

    private boolean saveCroquis() {
        boolean save_croquis = false;
        if (
                !validatorTV.validate(tvLateraUno, new String[]{validatorTV.REQUIRED, validatorTV.ALFANUMERICO}) &&
                        !validatorTV.validate(tvPrincipal, new String[]{validatorTV.ALFANUMERICO, validatorTV.ALFANUMERICO}) &&
                        !validatorTV.validate(tvTrasera, new String[]{validatorTV.REQUIRED, validatorTV.ALFANUMERICO}) &&
                        !validatorTV.validate(tvLateraDos, new String[]{validatorTV.REQUIRED, validatorTV.ALFANUMERICO}) &&
                        !validator.validate(etReferencia, new String[]{validatorTV.REQUIRED, validatorTV.ALFANUMERICO}) &&
                        !validator.validate(etCaracteristicasDomicilio, new String[]{validatorTV.REQUIRED, validatorTV.ALFANUMERICO})
        ) {
            //ivError8.setVisibility(View.GONE);
            ivError2.setVisibility(View.GONE);
            ContentValues cv = new ContentValues();
            cv.put("calle_principal", Miscellaneous.GetStr(tvPrincipal));
            cv.put("lateral_uno", Miscellaneous.GetStr(tvLateraUno));
            cv.put("lateral_dos", Miscellaneous.GetStr(tvLateraDos));
            cv.put("calle_trasera", Miscellaneous.GetStr(tvTrasera));
            cv.put("referencias", Miscellaneous.GetStr(etReferencia));
            cv.put("caracteristicas_domicilio", Miscellaneous.GetStr(etCaracteristicasDomicilio));

            db.update(TBL_CROQUIS_IND, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
            save_croquis = true;
        } else
            //ivError8.setVisibility(View.VISIBLE);
            ivError2.setVisibility(View.VISIBLE);
        return save_croquis;
    }

    private boolean savePoliticas() {
        boolean save_politicas = false;
        if (rgPropietarioReal.getCheckedRadioButtonId() == R.id.rbSiPropietario ||
                rgPropietarioReal.getCheckedRadioButtonId() == R.id.rbNoPropietario) {
            tvPropietarioReal.setError(null);
            if (rgProveedor.getCheckedRadioButtonId() == R.id.rbSiProveedor ||
                    rgProveedor.getCheckedRadioButtonId() == R.id.rbNoProveedor) {
                tvProvedor.setError(null);
                if (rgPoliticamenteExp.getCheckedRadioButtonId() == R.id.rbSiExpuesta ||
                        rgPoliticamenteExp.getCheckedRadioButtonId() == R.id.rbNoexpuesta) {
                    tvPoliticamenteExp.setError(null);
                    ivError9.setVisibility(View.GONE);
                    ContentValues cv = new ContentValues();
                    int buttonId = rgPropietarioReal.getCheckedRadioButtonId();
                    if (buttonId == R.id.rbSiPropietario) {
                        cv.put("propietario_real", 1);
                    } else if (buttonId == R.id.rbNoPropietario) {
                        cv.put("propietario_real", 2);
                    }

                    int radioButtonId = rgProveedor.getCheckedRadioButtonId();
                    if (radioButtonId == R.id.rbSiProveedor) {
                        cv.put("proveedor_recursos", 1);
                    } else if (radioButtonId == R.id.rbNoProveedor) {
                        cv.put("proveedor_recursos", 2);
                    }

                    int checkedRadioButtonId = rgPoliticamenteExp.getCheckedRadioButtonId();
                    if (checkedRadioButtonId == R.id.rbSiExpuesta) {
                        cv.put("persona_politica", 1);
                    } else if (checkedRadioButtonId == R.id.rbNoexpuesta) {
                        cv.put("persona_politica", 2);
                    }

                    db.update(TBL_POLITICAS_PLD_IND, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});

                    save_politicas = true;
                } else {
                    ivError9.setVisibility(View.VISIBLE);
                    tvPoliticamenteExp.setError("");
                }
            } else {
                ivError9.setVisibility(View.VISIBLE);
                tvProvedor.setError("");
            }
        } else {
            ivError9.setVisibility(View.VISIBLE);
            tvPropietarioReal.setError("");
        }
        return save_politicas;
    }

    private boolean saveDocumentacion() {
        if (byteIneSelfie == null) {
            showErrorIconAndMessage(tvIneSelfie, "FOTOGRAFIA DEL INE CON SELFIE REQUERIDA");
            return false;
        }

        if (byteIneFrontal == null) {
            showErrorIconAndMessage(tvIneFrontal, "FOTOGRAFIA DEL INE FRONTAL REQUERIDA");
            return false;
        }

        if (byteIneReverso == null) {
            showErrorIconAndMessage(tvIneReverso, "FOTOGRAFIA DEL INE REVERSO REQUERIDA");
            return false;
        }

        if (byteCurp == null) {
            showErrorIconAndMessage(tvCurp, "FOTOGRAFIA DEL CURP REQUERIDA");
            return false;
        }

        if (byteComprobante == null) {
            showErrorIconAndMessage(tvComprobante, "FOTOGRAFIA DEL COMPROBANTE REQUERIDA");
            return false;
        }

        if (byteFirmaAsesor == null) {
            showErrorIconAndMessage(tvFirmaAsesor, "FOTOGRAFIA DE LA FIRMA DEL ASESOR REQUERIDA");
            return false;
        }

        return true;
    }

    private void showErrorIconAndMessage(TextView textView, String message) {
        ivError10.setVisibility(View.VISIBLE);
        showError(textView, message);
    }

    private void showError(TextView textView, String n) {
        textView.setError(n);
        textView.requestFocus();
        Toast.makeText(ctx, "FOTOGRAFIA REQUERIDA", Toast.LENGTH_SHORT).show();
    }


    //===================== Listener GPS  =======================================================
    private void ObtenerUbicacion() {
        findUbicacion(direccionIdCli, "CLIENTE");
    }

    private void findUbicacion(String idDireccion, String tipo) {
        DireccionDao direccionDao = new DireccionDao(ctx);
        Direccion direccion = direccionDao.findByIdDireccion(Long.parseLong(idDireccion));

        if (direccion != null && !direccion.getLatitud().equals("0") && !direccion.getLatitud().equals("")) {
            AlertDialog alertDialog = Popups.showDialogConfirm(this, warning,
                    R.string.guardar_cambios, R.string.yes, dialog -> {
                        switch (tipo) {
                            case "CLIENTE":
                                setUbicacion();
                                break;
                            case "AVAL":
                                setUbicacionAval();
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
        } else {
            switch (tipo) {
                case "CLIENTE":
                    setUbicacion();
                    break;
                case "AVAL":
                    setUbicacionAval();
                    break;
                case "NEGOCIO":
                    setUbicacionNegocio();
                    break;
                default:
                    break;
            }
        }
    }

    private void setUbicacion() {
        pbLoadCli.setVisibility(View.VISIBLE);
        ibMapCli.setEnabled(false);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        final Handler myHandler = new Handler();
        locationListener = new MyCurrentListener(new MyCurrentListener.evento() {
            @Override
            public void onComplete(String latitud, String longitud) {

                ibMapCli.setEnabled(true);
                if (!latitud.isEmpty() && !longitud.isEmpty()) {
                    mapCli.setVisibility(View.VISIBLE);
                    UpdateDireccion("latitud", latitud, direccionIdCli, "CLIENTE");
                    UpdateDireccion("longitud", longitud, direccionIdCli, "CLIENTE");
                    UpdateDireccion("located_at", Miscellaneous.ObtenerFecha(TIMESTAMP), direccionIdCli, "CLIENTE");
                    Ubicacion(Double.parseDouble(latitud), Double.parseDouble(longitud));

                } else {
                    latLngUbiCli = new LatLng(0, 0);
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
            provider = LocationManager.NETWORK_PROVIDER;
        } else {
            provider = LocationManager.GPS_PROVIDER;
        }

        locationManager.requestSingleUpdate(provider, locationListener, null);

        myHandler.postDelayed(new Runnable() {
            public void run() {
                locationManager.removeUpdates(locationListener);
                pbLoadCli.setVisibility(View.GONE);
                ibMapCli.setEnabled(true);
                latLngUbiCli = new LatLng(0, 0);
                pushMapButtonCli = true;
                Toast.makeText(ctx, "No se logró obtener la ubicación", Toast.LENGTH_SHORT).show();
            }
        }, 60000);

    }

    private void setUbicacionAval() {
        pbLoadAval.setVisibility(View.VISIBLE);
        ibMapAval.setEnabled(false);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        final Handler myHandler = new Handler();
        locationListener = new MyCurrentListener(new MyCurrentListener.evento() {
            @Override
            public void onComplete(String latitud, String longitud) {

                ibMapAval.setEnabled(true);
                if (!latitud.isEmpty() && !longitud.isEmpty()) {
                    mapAval.setVisibility(View.VISIBLE);
                    UpdateDireccion("latitud", latitud, direccionIdAval, "AVAL");
                    UpdateDireccion("longitud", longitud, direccionIdAval, "AVAL");
                    UpdateDireccion("located_at", Miscellaneous.ObtenerFecha(TIMESTAMP), direccionIdAval, "AVAL");
                    UbicacionAval(Double.parseDouble(latitud), Double.parseDouble(longitud));
                } else {
                    latLngUbiAval = new LatLng(0, 0);
                    UpdateDireccion("latitud", "", direccionIdAval, "AVAL");
                    UpdateDireccion("longitud", "", direccionIdAval, "AVAL");
                    UpdateDireccion("located_at", Miscellaneous.ObtenerFecha(TIMESTAMP), direccionIdAval, "AVAL");
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
        } else {
            Log.e("Proveedor", "GPS");
            provider = LocationManager.GPS_PROVIDER;
        }

        locationManager.requestSingleUpdate(provider, locationListener, null);

        myHandler.postDelayed(new Runnable() {
            public void run() {
                locationManager.removeUpdates(locationListener);
                pbLoadAval.setVisibility(View.GONE);
                ibMapAval.setEnabled(true);
                latLngUbiAval = new LatLng(0, 0);
                pushMapButtonAval = true;
                Toast.makeText(ctx, "No se logró obtener la ubicación", Toast.LENGTH_SHORT).show();
            }
        }, 60000);
    }

    private void setUbicacionNegocio() {
        pbLoadNeg.setVisibility(View.VISIBLE);
        ibMapNeg.setEnabled(false);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        final Handler myHandler = new Handler();
        locationListener = new MyCurrentListener(new MyCurrentListener.evento() {
            @Override
            public void onComplete(String latitud, String longitud) {

                ibMapNeg.setEnabled(true);
                if (!latitud.isEmpty() && !longitud.isEmpty()) {
                    mapNeg.setVisibility(View.VISIBLE);
                    UpdateDireccion("latitud", latitud, direccionIdNeg, "NEGOCIO");
                    UpdateDireccion("longitud", longitud, direccionIdNeg, "NEGOCIO");
                    UpdateDireccion("located_at", Miscellaneous.ObtenerFecha(TIMESTAMP), direccionIdNeg, "NEGOCIO");
                    UbicacionNeg(Double.parseDouble(latitud), Double.parseDouble(longitud));
                } else {
                    latLngUbiNeg = new LatLng(0, 0);
                    UpdateDireccion("latitud", "", direccionIdNeg, "NEGOCIO");
                    UpdateDireccion("longitud", "", direccionIdNeg, "NEGOCIO");
                    UpdateDireccion("located_at", Miscellaneous.ObtenerFecha(TIMESTAMP), direccionIdNeg, "NEGOCIO");
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
        } else {
            Log.e("Proveedor", "GPS");
            provider = LocationManager.GPS_PROVIDER;
        }

        locationManager.requestSingleUpdate(provider, locationListener, null);

        myHandler.postDelayed(new Runnable() {
            public void run() {
                locationManager.removeUpdates(locationListener);
                pbLoadNeg.setVisibility(View.GONE);
                ibMapNeg.setEnabled(true);
                latLngUbiNeg = new LatLng(0, 0);
                pushMapButtonNeg = true;
                Toast.makeText(ctx, "No se logró obtener la ubicación", Toast.LENGTH_SHORT).show();
            }
        }, 60000);
    }

    private void ObtenerUbicacionNeg() {
        findUbicacion(direccionIdNeg, "NEGOCIO");
    }

    private void ObtenerUbicacionAval() {
        findUbicacion(direccionIdAval, "AVAL");
    }

    private void ObtenerUbicacionFirmaCliente() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        final Handler myHandler = new Handler();

        locationListener = new MyCurrentListener((latitud, longitud) -> {

            if (!latitud.isEmpty() && !longitud.isEmpty()) {
                Update("latitud", TBL_CLIENTE_IND, latitud);
                Update("longitud", TBL_CLIENTE_IND, longitud);
                Update("located_at", TBL_CLIENTE_IND, Miscellaneous.ObtenerFecha(TIMESTAMP));
            }

            myHandler.removeCallbacksAndMessages(null);

            CancelUbicacion();
        });

        if (ActivityCompat.checkSelfPermission(ctx, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(ctx, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
        }

        String provider;

        if (NetworkStatus.haveNetworkConnection(ctx)) {
            Log.e("Proveedor", "RED");
            provider = LocationManager.NETWORK_PROVIDER;
        } else {
            Log.e("Proveedor", "GPS");
            provider = LocationManager.GPS_PROVIDER;
        }

        locationManager.requestSingleUpdate(provider, locationListener, null);

        myHandler.postDelayed(() -> {
            locationManager.removeUpdates(locationListener);
        }, 60000);
    }

    private void ObtenerUbicacionFirmaAval() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        final Handler myHandler = new Handler();

        locationListener = new MyCurrentListener((latitud, longitud) -> {

            if (!latitud.isEmpty() && !longitud.isEmpty()) {
                Update("latitud", TBL_AVAL_IND, latitud);
                Update("longitud", TBL_AVAL_IND, longitud);
                Update("located_at", TBL_AVAL_IND, Miscellaneous.ObtenerFecha(TIMESTAMP));
            }

            myHandler.removeCallbacksAndMessages(null);

            CancelUbicacion();
        });

        if (ActivityCompat.checkSelfPermission(ctx, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(ctx, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
        }

        String provider;

        if (NetworkStatus.haveNetworkConnection(ctx)) {
            Log.e("Proveedor", "RED");
            provider = LocationManager.NETWORK_PROVIDER;
        } else {
            Log.e("Proveedor", "GPS");
            provider = LocationManager.GPS_PROVIDER;
        }

        locationManager.requestSingleUpdate(provider, locationListener, null);

        myHandler.postDelayed(() -> {
            locationManager.removeUpdates(locationListener);
        }, 60000);
    }

    private void Ubicacion(final double lat, final double lon) {
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

                addMarker(lat, lon);

            }
        });
    }

    private void UbicacionNeg(final double lat, final double lon) {
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

                addMarkerNeg(lat, lon);

            }
        });
    }

    private void UbicacionAval(final double lat, final double lon) {
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

                addMarkerAval(lat, lon);

            }
        });
    }

    private void addMarker(double lat, double lng) {
        LatLng coordenadas = new LatLng(lat, lng);
        latLngUbiCli = coordenadas;
        CameraUpdate ubication = CameraUpdateFactory.newLatLngZoom(coordenadas, 17);

        mMapCli.clear();
        mMapCli.addMarker(new MarkerOptions()
                .position(coordenadas)
                .title(""));

        mMapCli.animateCamera(ubication);

        pbLoadCli.setVisibility(View.GONE);
        //ibMapCli.setVisibility(View.GONE);
        if (ubication == null) {
            tvMapaCli.setError("CAMPO REQUERIDO");
            Toast.makeText(ctx, "ESTE CAMPO ES REQUERIDO", Toast.LENGTH_SHORT).show();
        }
    }

    private void addMarkerNeg(double lat, double lng) {
        LatLng coordenadas = new LatLng(lat, lng);
        latLngUbiNeg = coordenadas;
        CameraUpdate ubication = CameraUpdateFactory.newLatLngZoom(coordenadas, 17);

        mMapNeg.clear();
        mMapNeg.addMarker(new MarkerOptions()
                .position(coordenadas)
                .title(""));

        mMapNeg.animateCamera(ubication);

        pbLoadNeg.setVisibility(View.GONE);
        //ibMapNeg.setVisibility(View.GONE);
    }

    private void addMarkerAval(double lat, double lng) {
        LatLng coordenadas = new LatLng(lat, lng);
        latLngUbiAval = coordenadas;
        CameraUpdate ubication = CameraUpdateFactory.newLatLngZoom(coordenadas, 17);

        mMapAval.clear();
        mMapAval.addMarker(new MarkerOptions()
                .position(coordenadas)
                .title(""));

        mMapAval.animateCamera(ubication);

        pbLoadAval.setVisibility(View.GONE);
        //ibMapAval.setVisibility(View.GONE);
    }

    private void CancelUbicacion() {
        locationManager.removeUpdates(locationListener);
    }

    private void CopiarDatosDeClienteHaciaNegocio() {
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

        UpdateDireccion("calle", Miscellaneous.GetStr(etCalleNeg), direccionIdNeg, "NEGOCIO");
        UpdateDireccion("num_exterior", Miscellaneous.GetStr(etNoExtNeg), direccionIdNeg, "NEGOCIO");
        UpdateDireccion("num_interior", Miscellaneous.GetStr(etNoIntNeg), direccionIdNeg, "NEGOCIO");
        UpdateDireccion("manzana", Miscellaneous.GetStr(etManzanaNeg), direccionIdNeg, "NEGOCIO");
        UpdateDireccion("lote", Miscellaneous.GetStr(etLoteNeg), direccionIdNeg, "NEGOCIO");
        UpdateDireccion("cp", Miscellaneous.GetStr(etCpNeg), direccionIdNeg, "NEGOCIO");
        UpdateDireccion("colonia", Miscellaneous.GetStr(tvColoniaNeg), direccionIdNeg, "NEGOCIO");
        UpdateDireccion("ciudad", Miscellaneous.GetStr(etCiudadNeg), direccionIdNeg, "NEGOCIO");
        UpdateDireccion("localidad", Miscellaneous.GetStr(tvLocalidadNeg), direccionIdNeg, "NEGOCIO");
        UpdateDireccion("municipio", Miscellaneous.GetStr(tvMunicipioNeg), direccionIdNeg, "NEGOCIO");
        UpdateDireccion("estado", Miscellaneous.GetStr(tvEstadoNeg), direccionIdNeg, "NEGOCIO");
        Update("ref_domiciliaria", TBL_NEGOCIO_IND, Miscellaneous.GetStr(etReferenciNeg));

    }

    private void LimpiarDatosNegocio() {
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

        UpdateDireccion("calle", "", direccionIdNeg, "NEGOCIO");
        UpdateDireccion("num_exterior", "", direccionIdNeg, "NEGOCIO");
        UpdateDireccion("num_interior", "", direccionIdNeg, "NEGOCIO");
        UpdateDireccion("manzana", "", direccionIdNeg, "NEGOCIO");
        UpdateDireccion("lote", "", direccionIdNeg, "NEGOCIO");
        UpdateDireccion("cp", "", direccionIdNeg, "NEGOCIO");
        UpdateDireccion("colonia", "", direccionIdNeg, "NEGOCIO");
        UpdateDireccion("ciudad", "", direccionIdNeg, "NEGOCIO");
        UpdateDireccion("localidad", "", direccionIdNeg, "NEGOCIO");
        UpdateDireccion("municipio", "", direccionIdNeg, "NEGOCIO");
        UpdateDireccion("estado", "", direccionIdNeg, "NEGOCIO");
        Update("ref_domiciliaria", TBL_NEGOCIO_IND, "");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_enviar_datos, menu);

        if (!is_edit) {
            menu.getItem(MENU_INDEX_ENVIAR).setVisible(is_edit);
        }

        SolicitudDao solicitudDao = new SolicitudDao(ctx);
        Solicitud solicitud = solicitudDao.findByIdSolicitud(Integer.parseInt(String.valueOf(id_solicitud)));

        if (TBmain.getMenu().size() > 1) {
            if (solicitud != null && solicitud.getEstatus() > 1)
                TBmain.getMenu().getItem(MENU_INDEX_UPDATE_ESTATUS).setVisible(true);
            else
                TBmain.getMenu().getItem(MENU_INDEX_UPDATE_ESTATUS).setVisible(false);
        }

        menu.getItem(MENU_INDEX_DEVMODE).setVisible(modoSuperUsuario);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == android.R.id.home) {
        } else if (itemId == R.id.devmode) {//senDataBeneficiario(id_solicitud);
            enviarJSONObjects();
        } else if (itemId == R.id.desbloquear) {
            if (modoSuperUsuario) {
                desactivarModoSuper();
                deshabilitarSolicitud();
            } else activarModoSuper();
        } else if (itemId == R.id.updateEstatus) {
            obtenerEstatusSolicitud();
        } else if (itemId == R.id.enviar) {//senDataBeneficiario(id_solicitud);
            sendSolicitud();
        }
        return super.onOptionsItemSelected(item);
    }

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
        } else
            finish();
        saveBeneficiario();
    }

    private void openRegistroCliente() {
        dialog_registro_cli registro_cli = new dialog_registro_cli();
        registro_cli.setCancelable(false);
        registro_cli.show(getSupportFragmentManager(), NameFragments.DIALOGORIGINACIONIND);
    }

    @Override
    public void onComplete(long id_solicitud, String id_cliente, String nombre, String paterno, String materno, long dirCli, long dirCony, long dirNeg, long dirAval, long dirRef) {
        if (id_solicitud > 0) {
            this.id_solicitud = id_solicitud;
            //this.id_cliente = id_cliente;
            etNombreCli.setText(nombre);
            etNombreCli.setEnabled(false);
            etApPaternoCli.setText(paterno);
            etApPaternoCli.setEnabled(false);
            etApMaternoCli.setText(materno);
            etApMaternoCli.setEnabled(false);
            direccionIdCli = String.valueOf(dirCli);
            direccionIdCony = String.valueOf(dirCony);
            direccionIdNeg = String.valueOf(dirNeg);
            direccionIdAval = String.valueOf(dirAval);
            direccionIdRef = String.valueOf(dirRef);

        } else finish();
    }

    private void initComponents(String idSolicitud) {
        Cursor row = dBhelper.getRecords(TBL_SOLICITUDES, " WHERE id_solicitud = ?", "", new String[]{idSolicitud});
        row.moveToFirst();
        is_edit = row.getInt(11) <= 1;
        row.close();

        //id_cliente = row.getString(11);

        row = dBhelper.getRecords(TBL_CREDITO_IND, " WHERE id_solicitud = ?", "", new String[]{idSolicitud});
        row.moveToFirst();
        isEditCre = row.getInt(10) == 0;

        //Llenado del datos del prestamo
        tvPlazo.setText(row.getString(2).toUpperCase());
        tvFrecuencia.setText(row.getString(3).toUpperCase());
        tvFechaDesembolso.setText(row.getString(4));
        tvDiaDesembolso.setText(row.getString(5));
        tvHoraVisita.setText(row.getString(6));

        if (!row.getString(7).trim().isEmpty())
            etMontoPrestamo.setText(dfnd.format(row.getInt(7)));

        if (!row.getString(7).trim().isEmpty()) {
            tvCantidadLetra.setText((Miscellaneous.cantidadLetra(row.getString(7)).toUpperCase() + " PESOS MEXICANOS").replace("  ", " "));
            if (row.getInt(7) >= 30000)
                llPropiedades.setVisibility(View.VISIBLE);
        }

        tvDestino.setText(row.getString(8));
        tvRiesgo.setText(row.getString(9));

        if (row.getString(11) != null && !row.getString(11).trim().isEmpty())
            etMontoRefinanciar.setText(dfnd.format(row.getInt(11)));

        txtCampana.setText(Miscellaneous.tipoCampana(ctx, row.getString(12)));
        txtNombreRefiero.setText(Miscellaneous.tipoCampana(ctx, row.getString(12)));

        row.close(); //Cierra dato del credito


        row = dBhelper.getRecords(TBL_DATOS_CREDITO_CAMPANA, " WHERE id_solicitud = ?", " ", new String[]{idSolicitud});

        if (row.getCount() > 0) {
            row.moveToFirst();
            txtNombreRefiero.setText(row.getString(5));
            row.close();
        }


        //Llenado de datos del cliente
        row = dBhelper.getRecords(TBL_CLIENTE_IND, " WHERE id_solicitud = ?", "", new String[]{idSolicitud});
        row.moveToFirst();
        isEditCli = row.getInt(37) == 0;

        if (!row.getString(36).trim().isEmpty()) {
            llComentAdminCli.setVisibility(View.VISIBLE);
            tvComentAdminCli.setText(row.getString(36).toUpperCase());
        }

        etNombreCli.setText(row.getString(2));
        etApPaternoCli.setText(row.getString(3));
        etApMaternoCli.setText(row.getString(4));
        tvFechaNacCli.setText(row.getString(5));
        tvEdadCli.setText(row.getString(6));

        switch (row.getInt(7)) {
            case 0:
                rgGeneroCli.check(R.id.rbHombre);
                break;
            case 1:
                rgGeneroCli.check(R.id.rbMujer);
                break;
        }

        tvEstadoNacCli.setText(row.getString(8));
        tvRfcCli.setText(row.getString(9));
        etCurpCli.setText(row.getString(10));
        //etCurpIdCli.setText(row.getString(11)); etCurpIdCli.setEnabled(is_edit);
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
            case "UNION LIBRE":
                llConyuge.setVisibility(View.VISIBLE);
                break;
        }

        tvTipoCasaCli.setText(row.getString(19));
        switch (row.getString(19)) {
            case "CASA FAMILIAR":
            case "CASA RENTADA":
                llCasaFamiliar.setVisibility(View.VISIBLE);
                tvCasaFamiliar.setText(row.getString(20));
                break;
            case "OTRO":
                llCasaOtroCli.setVisibility(View.VISIBLE);
                etOTroTipoCli.setText(row.getString(21));
                break;
        }

        Cursor rowDir = dBhelper.getRecords(TBL_DIRECCIONES, " WHERE id_direccion = ? AND tipo_direccion = ?", "", new String[]{row.getString(22), "CLIENTE"});
        rowDir.moveToFirst();

        direccionIdCli = rowDir.getString(0);

        if (!rowDir.getString(2).isEmpty() && !rowDir.getString(3).isEmpty()) {
            mapCli.setVisibility(View.VISIBLE);
            Ubicacion(rowDir.getDouble(2), rowDir.getDouble(3));
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

        if (!row.getString(32).isEmpty()) {
            File fachadaFile = new File(ROOT_PATH + "Fachada/" + row.getString(32));
            Uri uriFachada = Uri.fromFile(fachadaFile);
            byteFotoFachCli = Miscellaneous.getBytesUri(ctx, uriFachada, 0);
            Glide.with(ctx).load(uriFachada).into(ivFotoFachCli);
            ibFotoFachCli.setVisibility(View.GONE);
            ivFotoFachCli.setVisibility(View.VISIBLE);
        }

        etReferenciCli.setText(row.getString(33));

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
        row = dBhelper.getRecords(TBL_CONYUGE_IND, " WHERE id_solicitud = ?", "", new String[]{idSolicitud});
        row.moveToFirst();

        isEditCon = row.getInt(12) == 0;
        etNombreCony.setText(row.getString(2));
        etApPaternoCony.setText(row.getString(3));
        etApMaternoCony.setText(row.getString(4));
        etNacionalidadCony.setText(row.getString(5));
        tvOcupacionCony.setText(row.getString(6));

        rowDir = dBhelper.getRecords(TBL_DIRECCIONES, " WHERE id_direccion = ? AND tipo_direccion = ?", "", new String[]{row.getString(7), "CONYUGE"});
        rowDir.moveToFirst();

        direccionIdCony = rowDir.getString(0);
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

        //llenando de datos beneficiario

        //=============================================DATOS DEL BENEFICIARIO==============================================\\

        row = dBhelper.getRecords(TBL_DATOS_BENEFICIARIO, " WHERE id_solicitud = ?", " ", new String[]{idSolicitud});

        if (row.getCount() > 0) {
            row.moveToFirst();

            txtNomBeneficiario.setText(row.getString(5).trim().toUpperCase());
            txtApellidoPaternoBeneficiario.setText(row.getString(6).trim().toUpperCase());
            txtApellidoMaternoBeneficiario.setText(row.getString(7).trim().toUpperCase());
            txtParentescoBeneficiario.setText(row.getString(8).trim().toUpperCase());

            row.close();
        }

        //==================================================================================================

        //Llenado de datos economicos
        row = dBhelper.getRecords(TBL_ECONOMICOS_IND, " WHERE id_solicitud = ?", "", new String[]{idSolicitud});
        row.moveToFirst();
        isEditEco = row.getInt(6) == 0;
        etPropiedadesEco.setText(row.getString(2));
        etValorAproxEco.setText(row.getString(3));
        etUbicacionEco.setText(row.getString(4));
        tvIngresoEco.setText(row.getString(5));
        row.close(); // Cierra datos economicos

        //Llenado de datos del negocio
        row = dBhelper.getRecords(TBL_NEGOCIO_IND, " WHERE id_solicitud = ?", "", new String[]{idSolicitud});
        row.moveToFirst();
        isEditNeg = row.getInt(26) == 0;
        if (!row.getString(27).trim().isEmpty()) {
            llComentAdminNeg.setVisibility(View.VISIBLE);
            tvComentAdminNeg.setText(row.getString(27).toUpperCase());
        }
        etNombreNeg.setText(row.getString(2));

        rowDir = dBhelper.getRecords(TBL_DIRECCIONES, " WHERE id_direccion = ? AND tipo_direccion = ?", "", new String[]{row.getString(3), "NEGOCIO"});
        rowDir.moveToFirst();
        direccionIdNeg = rowDir.getString(0);
        if (!rowDir.getString(2).isEmpty() && !rowDir.getString(3).isEmpty()) {
            mapNeg.setVisibility(View.VISIBLE);
            UbicacionNeg(rowDir.getDouble(2), rowDir.getDouble(3));
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
        tvEstadoNeg.setText(rowDir.getString(14));
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

        if (Miscellaneous.GetStr(tvMediosPagoNeg).contains("EFECTIVO"))
            llOperacionesEfectivo.setVisibility(View.VISIBLE);
        else
            llOperacionesEfectivo.setVisibility(View.GONE);

        if (!row.getString(20).trim().isEmpty())
            tvMontoMaxNeg.setText(dfnd.format(row.getDouble(20)));

        tvNumOperacionNeg.setText((row.getString(21).isEmpty()) ? "" : row.getString(21));
        etNumOperacionEfectNeg.setText((row.getString(22).isEmpty()) ? "" : row.getString(22));
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

        //ubicado_en_dom_cli
        if (row.getString(28).trim().equals("") || row.getString(28).trim().equals("SI")) {
            if (row.getString(28).trim().equals(""))
                Update("ubicado_en_dom_cli", TBL_NEGOCIO_IND, "SI");
            cbNegEnDomCli.setChecked(true);
        }

        row.close(); // Cierra los datos del negocio


        //Llenado de datos del aval
        row = dBhelper.getRecords(TBL_AVAL_IND, " WHERE id_solicitud = ?", "", new String[]{idSolicitud});
        row.moveToFirst();

        isEditAva = row.getInt(51) == 0;

        if (!row.getString(50).trim().isEmpty()) {
            llComentAdminAval.setVisibility(View.VISIBLE);
            tvComentAdminAval.setText(row.getString(50).toUpperCase());
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

        rowDir = dBhelper.getRecords(TBL_DIRECCIONES, " WHERE id_direccion = ? AND tipo_direccion = ?", "", new String[]{row.getString(19), "AVAL"});
        rowDir.moveToFirst();
        direccionIdAval = rowDir.getString(0);
        if (!rowDir.getString(2).isEmpty() && !rowDir.getString(3).isEmpty()) {
            mapAval.setVisibility(View.VISIBLE);
            UbicacionAval(rowDir.getDouble(2), rowDir.getDouble(3));
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

        if (!row.getString(46).isEmpty()) {
            File fachadaFile = new File(ROOT_PATH + "Fachada/" + row.getString(46));
            Uri uriFachada = Uri.fromFile(fachadaFile);
            byteFotoFachAval = Miscellaneous.getBytesUri(ctx, uriFachada, 0);
            Glide.with(ctx).load(uriFachada).into(ivFotoFachAval);
            ibFotoFachAval.setVisibility(View.GONE);
            ivFotoFachAval.setVisibility(View.VISIBLE);
        }
        etReferenciaAval.setText(row.getString(47));

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
        row = dBhelper.getRecords(TBL_REFERENCIA_IND, " WHERE id_solicitud = ?", "", new String[]{idSolicitud});
        row.moveToFirst();
        isEditRef = row.getInt(8) == 0;
        if (!row.getString(9).trim().isEmpty()) {
            llComentAdminRef.setVisibility(View.VISIBLE);
            tvComentAdminRef.setText(row.getString(9).toUpperCase());
        }
        etNombreRef.setText(row.getString(2));
        etApPaternoRef.setText(row.getString(3));
        etApMaternoRef.setText(row.getString(4));
        tvFechaNacRef.setText(row.getString(5));

        rowDir = dBhelper.getRecords(TBL_DIRECCIONES, " WHERE id_direccion = ? AND tipo_direccion = ?", "", new String[]{row.getString(6), "REFERENCIA"});
        rowDir.moveToFirst();

        direccionIdRef = rowDir.getString(0);
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

        row = dBhelper.getRecords(TBL_CROQUIS_IND, " WHERE id_solicitud = ?", "", new String[]{idSolicitud});
        row.moveToFirst();
        isEditCro = row.getInt(7) == 0;
        if (!row.getString(8).trim().isEmpty()) {
            llComentAdminCro.setVisibility(View.VISIBLE);
            tvComentAdminCro.setText(row.getString(8).toUpperCase());
        }
        tvPrincipal.setText(row.getString(2).trim().toUpperCase());
        tvLateraUno.setText(row.getString(3).trim().toUpperCase());
        tvLateraDos.setText(row.getString(4).trim().toUpperCase());
        tvTrasera.setText(row.getString(5).trim().toUpperCase());
        etReferencia.setText(row.getString(6));
        etCaracteristicasDomicilio.setText(row.getString(9));

        row.close(); //Cierra datos del croquis

        row = dBhelper.getRecords(TBL_POLITICAS_PLD_IND, " WHERE id_solicitud = ?", "", new String[]{idSolicitud});
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
        row = dBhelper.getRecords(TBL_DOCUMENTOS, " WHERE id_solicitud = ?", "", new String[]{idSolicitud});
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

        if (!row.getString(4).isEmpty()) {
            File curpFile = new File(ROOT_PATH + "Documentos/" + row.getString(4));
            Uri uriCurp = Uri.fromFile(curpFile);
            byteCurp = Miscellaneous.getBytesUri(ctx, uriCurp, 0);
            Glide.with(ctx).load(uriCurp).into(ivCurp);
            ibCurp.setVisibility(View.GONE);
            ivCurp.setVisibility(View.VISIBLE);
        }

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

        if (row.getString(9) != null && !row.getString(9).isEmpty()) {
            File ineSelfieFile = new File(ROOT_PATH + "Documentos/" + row.getString(9));
            Uri uriIneSelfie = Uri.fromFile(ineSelfieFile);
            byteIneSelfie = Miscellaneous.getBytesUri(ctx, uriIneSelfie, 0);
            Glide.with(ctx).load(uriIneSelfie).into(ivIneSelfie);
            ibIneSelfie.setVisibility(View.GONE);
            ivIneSelfie.setVisibility(View.VISIBLE);
        }

        if (row.getString(10) != null && !row.getString(10).isEmpty()) {
            File comprobanteGarantia = new File(ROOT_PATH + "Documentos/" + row.getString(10));
            Uri uriComprobanteGarantia = Uri.fromFile(comprobanteGarantia);
            byteComprobanteGarantia = Miscellaneous.getBytesUri(ctx, uriComprobanteGarantia, 0);
            Glide.with(ctx).load(uriComprobanteGarantia).into(ivComprobanteGarantia);
            ibComprobanteGarantia.setVisibility(View.GONE);
            ivComprobanteGarantia.setVisibility(View.VISIBLE);
        }

        if (row.getString(11) != null && !row.getString(11).isEmpty()) {
            File ineFrontalAvalFile = new File(ROOT_PATH + "Documentos/" + row.getString(11));
            Uri uriIneFrontalAval = Uri.fromFile(ineFrontalAvalFile);
            byteIneFrontalAval = Miscellaneous.getBytesUri(ctx, uriIneFrontalAval, 0);
            Glide.with(ctx).load(uriIneFrontalAval).into(ivIneFrontalAval);
            ibIneFrontalAval.setVisibility(View.GONE);
            ivIneFrontalAval.setVisibility(View.VISIBLE);
        }

        if (row.getString(12) != null && !row.getString(12).isEmpty()) {
            File ineReversoAvalFile = new File(ROOT_PATH + "Documentos/" + row.getString(12));
            Uri uriIneReversoAval = Uri.fromFile(ineReversoAvalFile);
            byteIneReversoAval = Miscellaneous.getBytesUri(ctx, uriIneReversoAval, 0);
            Glide.with(ctx).load(uriIneReversoAval).into(ivIneReversoAval);
            ibIneReversoAval.setVisibility(View.GONE);
            ivIneReversoAval.setVisibility(View.VISIBLE);
        }

        if (row.getString(13) != null && !row.getString(13).isEmpty()) {
            File curpAvalFile = new File(ROOT_PATH + "Documentos/" + row.getString(13));
            Uri uriCurpAval = Uri.fromFile(curpAvalFile);
            byteCurpAval = Miscellaneous.getBytesUri(ctx, uriCurpAval, 0);
            Glide.with(ctx).load(uriCurpAval).into(ivCurpAval);
            ibCurpAval.setVisibility(View.GONE);
            ivCurpAval.setVisibility(View.VISIBLE);
        }

        if (row.getString(14) != null && !row.getString(14).isEmpty()) {
            File comprobanteAvalFile = new File(ROOT_PATH + "Documentos/" + row.getString(14));
            Uri uriComprobanteAval = Uri.fromFile(comprobanteAvalFile);
            byteComprobanteAval = Miscellaneous.getBytesUri(ctx, uriComprobanteAval, 0);
            Glide.with(ctx).load(uriComprobanteAval).into(ivComprobanteAval);
            ibComprobanteAval.setVisibility(View.GONE);
            ivComprobanteAval.setVisibility(View.VISIBLE);
        }

        row.close(); //Cierra datos de los documentos

        deshabilitarCampos();

    }

    private void ShowMensajes(String mensaje, String tipo) {
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

        if (tipo.equals("NEGOCIO") && isEditNeg)
            etCapacidadPagoNeg.setText("");
        else if (tipo.equals("AVAL") && isEditAva)
            etCapacidadPagoAval.setText("");
    }

    private void Update(String key, String tabla, String value) {
        Log.e("update", key + ": " + value);
        ContentValues cv = new ContentValues();
        cv.put(key, value);
        db.update(tabla, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});

    }

    private void UpdateDireccion(String key, String value, String direccion_id, String tipo) {
        Log.e("update", key + ": " + value);
        ContentValues cv = new ContentValues();
        cv.put(key, value);
        db.update(TBL_DIRECCIONES, cv, "id_direccion = ? AND tipo_direccion = ?", new String[]{direccion_id, tipo});

    }

    public void setCalle(String calle, String tipo) {
        switch (tipo) {
            case "PRINCIPAL":
                tvPrincipal.setError(null);
                tvPrincipal.setText(calle);
                Update("calle_principal", TBL_CROQUIS_IND, calle.trim().toUpperCase());
                break;
            case "TRASERA":
                tvTrasera.setError(null);
                tvTrasera.setText(calle);
                Update("calle_trasera", TBL_CROQUIS_IND, calle.trim().toUpperCase());
                break;
            case "LATERAL UNO":
                tvLateraUno.setError(null);
                tvLateraUno.setText(calle);
                Update("lateral_uno", TBL_CROQUIS_IND, calle.trim().toUpperCase());
                break;
            case "LATERAL DOS":
                tvLateraDos.setError(null);
                tvLateraDos.setText(calle);
                Update("lateral_dos", TBL_CROQUIS_IND, calle.trim().toUpperCase());
                break;
        }
    }

    private boolean getGastoMen() {

        double sum = Double.parseDouble(Miscellaneous.GetStr(etGastosMenNeg).replace(",", ""))
                + Double.parseDouble(Miscellaneous.GetStr(etGastosAguaNeg).replace(",", ""))
                + Double.parseDouble(Miscellaneous.GetStr(etGastosLuzNeg).replace(",", ""))
                + Double.parseDouble(Miscellaneous.GetStr(etGastosTelNeg).replace(",", ""))
                + Double.parseDouble(Miscellaneous.GetStr(etGastosRentaNeg).replace(",", ""))
                + Double.parseDouble(Miscellaneous.GetStr(etGastosOtrosNeg).replace(",", ""));

        return (sum > 0);
    }

    private void activarModoSuper() {
        final AlertDialog loadingDesbloqueo = Popups.showLoadingDialog(context, R.string.please_wait, R.string.loading_info);
        loadingDesbloqueo.show();

        SessionManager session = SessionManager.getInstance(ctx);
        IPermisoService permisoService = RetrofitClient.newInstance(ctx).create(IPermisoService.class);
        Call<PermisoResponse> call = permisoService.isSuperEnabled("Bearer " + session.getUser().get(7));

        call.enqueue(new Callback<PermisoResponse>() {
            @Override
            public void onResponse(Call<PermisoResponse> call, Response<PermisoResponse> response) {
                PermisoResponse permisoResponse;

                switch (response.code()) {
                    case 200:
                        permisoResponse = response.body();
                        loadingDesbloqueo.dismiss();
                        if (permisoResponse.getData() != null) {
                            modoSuperUsuario = true;
                            TBmain.getMenu().getItem(MENU_INDEX_DESBLOQUEAR).setIcon(ContextCompat.getDrawable(ctx, R.drawable.ic_baseline_lock_open_24_white));
                            TBmain.getMenu().getItem(MENU_INDEX_DEVMODE).setVisible(modoSuperUsuario);

                            if (habilitarSolicitud()) {
                                habilitarCampos();
                                Toast.makeText(ctx, "Modo super usuario activado", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(ctx, "¡La solicitud no pudo habilitarse en modo super usuario!", Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            Toast.makeText(ctx, permisoResponse.getMensaje(), Toast.LENGTH_SHORT).show();
                        }
                        break;
                    default:
                        loadingDesbloqueo.dismiss();
                        Toast.makeText(ctx, response.message(), Toast.LENGTH_SHORT).show();
                        break;
                }
            }

            @Override
            public void onFailure(Call<PermisoResponse> call, Throwable t) {
                loadingDesbloqueo.dismiss();
                Log.e("AQUI ERROR", t.getMessage());
                Toast.makeText(ctx, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void desactivarModoSuper() {
        modoSuperUsuario = false;
        deshabilitarCampos();
        TBmain.getMenu().getItem(MENU_INDEX_DESBLOQUEAR).setIcon(ContextCompat.getDrawable(ctx, R.drawable.ic_baseline_lock_24_white));
        TBmain.getMenu().getItem(MENU_INDEX_DEVMODE).setVisible(modoSuperUsuario);
    }

    private void habilitarCampos() {
        TBmain.getMenu().getItem(MENU_INDEX_ENVIAR).setVisible(true);
        TBmain.getMenu().getItem(MENU_INDEX_UPDATE_ESTATUS).setVisible(false);


        cbNegEnDomCli.setEnabled(true);
        etMontoPrestamo.setEnabled(true);
        etMontoRefinanciar.setEnabled(true);
        tvFechaNacCli.setEnabled(true);
        tvEstadoNacCli.setEnabled(true);
        etCurpCli.setEnabled(true);
        for (int i = 0; i < rgGeneroCli.getChildCount(); i++) {
            rgGeneroCli.getChildAt(i).setEnabled(true);
        }
        txtCampana.setEnabled(true);
        txtNombreRefiero.setEnabled(true);
        etNombreCli.setEnabled(true);
        etApPaternoCli.setEnabled(true);
        etApMaternoCli.setEnabled(true);
        etNumIdentifCli.setEnabled(true);
        etCalleCli.setEnabled(true);
        etNoExtCli.setEnabled(true);
        etNoIntCli.setEnabled(true);
        etLoteCli.setEnabled(true);
        etManzanaCli.setEnabled(true);
        etCpCli.setEnabled(true);
        etCiudadCli.setEnabled(true);
        etTelCasaCli.setEnabled(true);
        etCelularCli.setEnabled(true);
        etTelMensCli.setEnabled(true);
        etTelTrabajoCli.setEnabled(true);
        etTiempoSitio.setEnabled(true);
        etEmail.setEnabled(true);
        etReferenciCli.setEnabled(true);
        ibFotoFachCli.setEnabled(true);

        for (int i = 0; i < rgGeneroCli.getChildCount(); i++) {
            rgGeneroCli.getChildAt(i).setEnabled(true);
        }
        for (int i = 0; i < rgBienes.getChildCount(); i++) {
            rgBienes.getChildAt(i).setEnabled(false);
        }

        etNombreCony.setEnabled(true);
        etApPaternoCony.setEnabled(true);
        etApMaternoCony.setEnabled(true);
        etNacionalidadCony.setEnabled(true);
        etCalleCony.setEnabled(true);
        etNoExtCony.setEnabled(true);
        etNoIntCony.setEnabled(true);
        etLoteCony.setEnabled(true);
        etManzanaCony.setEnabled(true);
        etCpCony.setEnabled(true);
        etCiudadCony.setEnabled(true);
        etIngMenCony.setEnabled(true);
        etGastoMenCony.setEnabled(true);
        etCasaCony.setEnabled(true);
        etCelularCony.setEnabled(true);

        txtNomBeneficiario.setEnabled(true);
        txtApellidoPaternoBeneficiario.setEnabled(true);
        txtApellidoMaternoBeneficiario.setEnabled(true);
        txtParentescoBeneficiario.setEnabled(true);

        etPropiedadesEco.setEnabled(true);
        etValorAproxEco.setEnabled(true);
        etUbicacionEco.setEnabled(true);

        etNombreNeg.setEnabled(true);
        etCalleNeg.setEnabled(true);
        etNoExtNeg.setEnabled(true);
        etNoIntNeg.setEnabled(true);
        etLoteNeg.setEnabled(true);
        etManzanaNeg.setEnabled(true);
        etCpNeg.setEnabled(true);
        etCiudadNeg.setEnabled(true);

        cbNegEnDomCli.setEnabled(true);
        etOtroDestinoNeg.setEnabled(true);
        etAntiguedadNeg.setEnabled(true);
        etIngMenNeg.setEnabled(true);
        etOtrosIngNeg.setEnabled(true);
        etGastosMenNeg.setEnabled(true);
        etGastosAguaNeg.setEnabled(true);
        etGastosLuzNeg.setEnabled(true);
        etGastosTelNeg.setEnabled(true);
        etGastosRentaNeg.setEnabled(true);
        etGastosOtrosNeg.setEnabled(true);
        etCapacidadPagoNeg.setEnabled(true);
        etOtroMedioPagoNeg.setEnabled(true);
        tvMontoMaxNeg.setEnabled(true);
        tvNumOperacionNeg.setEnabled(true);
        etNumOperacionEfectNeg.setEnabled(true);
        etReferenciNeg.setEnabled(true);
        ibFotoFachNeg.setEnabled(true);

        etNombreAval.setEnabled(true);
        etApPaternoAval.setEnabled(true);
        etApMaternoAval.setEnabled(true);
        etCurpAval.setEnabled(true);
        etNumIdentifAval.setEnabled(true);
        etCalleAval.setEnabled(true);
        etNoExtAval.setEnabled(true);
        etNoIntAval.setEnabled(true);
        etLoteAval.setEnabled(true);
        etManzanaAval.setEnabled(true);
        etCpAval.setEnabled(true);
        etCiudadAval.setEnabled(true);
        etNombreTitularAval.setEnabled(true);
        etCaracteristicasAval.setEnabled(true);
        etNombreNegocioAval.setEnabled(true);
        etAntiguedadAval.setEnabled(true);
        etIngMenAval.setEnabled(true);
        etIngOtrosAval.setEnabled(true);
        etGastosSemAval.setEnabled(true);
        etGastosAguaAval.setEnabled(true);
        etGastosLuzAval.setEnabled(true);
        etGastosTelAval.setEnabled(true);
        etGastosRentaAval.setEnabled(true);
        etGastosOtrosAval.setEnabled(true);
        etCapacidadPagoAval.setEnabled(true);
        etOtroMedioPagoAval.setEnabled(true);
        etTelCasaAval.setEnabled(true);
        etCelularAval.setEnabled(true);
        etTelMensAval.setEnabled(true);
        etTelTrabajoAval.setEnabled(true);
        etEmailAval.setEnabled(true);
        etReferenciaAval.setEnabled(true);
        ibFotoFachAval.setEnabled(true);

        for (int i = 0; i < rgGeneroAval.getChildCount(); i++) {
            rgGeneroAval.getChildAt(i).setEnabled(true);
        }

        for (int i = 0; i < rgNegocioAval.getChildCount(); i++) {
            rgNegocioAval.getChildAt(i).setEnabled(true);
        }

        etNombreRef.setEnabled(true);
        etApPaternoRef.setEnabled(true);
        etApMaternoRef.setEnabled(true);
        etCalleRef.setEnabled(true);
        etNoExtRef.setEnabled(true);
        etNoIntRef.setEnabled(true);
        etLoteRef.setEnabled(true);
        etManzanaRef.setEnabled(true);
        etCpRef.setEnabled(true);
        etCiudadRef.setEnabled(true);
        etTelCelRef.setEnabled(true);

        for (int i = 0; i < rgPropietarioReal.getChildCount(); i++) {
            rgPropietarioReal.getChildAt(i).setEnabled(true);
        }
        for (int i = 0; i < rgProveedor.getChildCount(); i++) {
            rgProveedor.getChildAt(i).setEnabled(true);
        }
        for (int i = 0; i < rgPoliticamenteExp.getChildCount(); i++) {
            rgPoliticamenteExp.getChildAt(i).setEnabled(true);
        }

        tvPlazo.setBackground(ContextCompat.getDrawable(ctx, R.drawable.et_rounded_left));
        tvFrecuencia.setBackground(ContextCompat.getDrawable(ctx, R.drawable.et_rounded_right));
        tvFechaDesembolso.setBackground(ContextCompat.getDrawable(ctx, R.drawable.et_rounded_edges));
        tvHoraVisita.setBackground(ContextCompat.getDrawable(ctx, R.drawable.et_rounded_edges));
        etMontoPrestamo.setBackground(ContextCompat.getDrawable(ctx, R.drawable.et_rounded_edges));
        tvDestino.setBackground(ContextCompat.getDrawable(ctx, R.drawable.et_rounded_edges));
        tvRiesgo.setBackground(ContextCompat.getDrawable(ctx, R.drawable.et_rounded_edges));
        etMontoRefinanciar.setBackground(ContextCompat.getDrawable(ctx, R.drawable.et_rounded_edges));
        txtCampana.setBackground(ContextCompat.getDrawable(ctx, R.drawable.et_rounded_edges));
        txtNombreRefiero.setBackground(ContextCompat.getDrawable(ctx, R.drawable.et_rounded_edges));

        etNombreCli.setBackground(ContextCompat.getDrawable(ctx, R.drawable.et_rounded_edges));
        etApPaternoCli.setBackground(ContextCompat.getDrawable(ctx, R.drawable.et_rounded_edges));
        etApMaternoCli.setBackground(ContextCompat.getDrawable(ctx, R.drawable.et_rounded_edges));
        tvFechaNacCli.setBackground(ContextCompat.getDrawable(ctx, R.drawable.et_rounded_edges));
        tvEstadoNacCli.setBackground(ContextCompat.getDrawable(ctx, R.drawable.et_rounded_edges));
        etCurpCli.setBackground(ContextCompat.getDrawable(ctx, R.drawable.et_rounded_edges));
        tvOcupacionCli.setBackground(ContextCompat.getDrawable(ctx, R.drawable.et_rounded_edges));
        tvTipoIdentificacion.setBackground(ContextCompat.getDrawable(ctx, R.drawable.et_rounded_edges));
        etNumIdentifCli.setBackground(ContextCompat.getDrawable(ctx, R.drawable.et_rounded_edges));
        tvEstudiosCli.setBackground(ContextCompat.getDrawable(ctx, R.drawable.et_rounded_edges));
        tvEstadoCivilCli.setBackground(ContextCompat.getDrawable(ctx, R.drawable.et_rounded_edges));

        tvTipoCasaCli.setBackground(ContextCompat.getDrawable(ctx, R.drawable.et_rounded_edges));
        tvCasaFamiliar.setBackground(ContextCompat.getDrawable(ctx, R.drawable.et_rounded_edges));
        etOTroTipoCli.setBackground(ContextCompat.getDrawable(ctx, R.drawable.et_rounded_edges));
        etCalleCli.setBackground(ContextCompat.getDrawable(ctx, R.drawable.et_rounded_edges));
        etNoExtCli.setBackground(ContextCompat.getDrawable(ctx, R.drawable.et_rounded_edges));
        etNoIntCli.setBackground(ContextCompat.getDrawable(ctx, R.drawable.et_rounded_edges));
        etManzanaCli.setBackground(ContextCompat.getDrawable(ctx, R.drawable.et_rounded_edges));
        etLoteCli.setBackground(ContextCompat.getDrawable(ctx, R.drawable.et_rounded_edges));
        etCpCli.setBackground(ContextCompat.getDrawable(ctx, R.drawable.et_rounded_edges));
        tvColoniaCli.setBackground(ContextCompat.getDrawable(ctx, R.drawable.et_rounded_edges));
        etCiudadCli.setBackground(ContextCompat.getDrawable(ctx, R.drawable.et_rounded_edges));
        tvLocalidadCli.setBackground(ContextCompat.getDrawable(ctx, R.drawable.et_rounded_edges));
        etTelCasaCli.setBackground(ContextCompat.getDrawable(ctx, R.drawable.et_rounded_edges));
        etCelularCli.setBackground(ContextCompat.getDrawable(ctx, R.drawable.et_rounded_edges));
        etTelMensCli.setBackground(ContextCompat.getDrawable(ctx, R.drawable.et_rounded_edges));
        etTelTrabajoCli.setBackground(ContextCompat.getDrawable(ctx, R.drawable.et_rounded_edges));
        etTiempoSitio.setBackground(ContextCompat.getDrawable(ctx, R.drawable.et_rounded_edges));
        tvDependientes.setBackground(ContextCompat.getDrawable(ctx, R.drawable.et_rounded_edges));
        tvEnteroNosotros.setBackground(ContextCompat.getDrawable(ctx, R.drawable.et_rounded_edges));
        tvEstadoCuenta.setBackground(ContextCompat.getDrawable(ctx, R.drawable.et_rounded_edges));
        etEmail.setBackground(ContextCompat.getDrawable(ctx, R.drawable.et_rounded_edges));
        etReferenciCli.setBackground(ContextCompat.getDrawable(ctx, R.drawable.et_rounded_edges));
        ibFotoFachCli.setBackground(ContextCompat.getDrawable(ctx, R.drawable.round_corner_blue));

        etNombreCony.setBackground(ContextCompat.getDrawable(ctx, R.drawable.et_rounded_edges));
        etApPaternoCony.setBackground(ContextCompat.getDrawable(ctx, R.drawable.et_rounded_edges));
        etApMaternoCony.setBackground(ContextCompat.getDrawable(ctx, R.drawable.et_rounded_edges));
        etNacionalidadCony.setBackground(ContextCompat.getDrawable(ctx, R.drawable.et_rounded_edges));
        tvOcupacionCony.setBackground(ContextCompat.getDrawable(ctx, R.drawable.et_rounded_edges));
        etCalleCony.setBackground(ContextCompat.getDrawable(ctx, R.drawable.et_rounded_edges));
        etNoExtCony.setBackground(ContextCompat.getDrawable(ctx, R.drawable.et_rounded_edges));
        etNoIntCony.setBackground(ContextCompat.getDrawable(ctx, R.drawable.et_rounded_edges));
        etManzanaCony.setBackground(ContextCompat.getDrawable(ctx, R.drawable.et_rounded_edges));
        etLoteCony.setBackground(ContextCompat.getDrawable(ctx, R.drawable.et_rounded_edges));
        etCpCony.setBackground(ContextCompat.getDrawable(ctx, R.drawable.et_rounded_edges));
        tvColoniaCony.setBackground(ContextCompat.getDrawable(ctx, R.drawable.et_rounded_edges));
        etCiudadCony.setBackground(ContextCompat.getDrawable(ctx, R.drawable.et_rounded_edges));
        tvLocalidadCony.setBackground(ContextCompat.getDrawable(ctx, R.drawable.et_rounded_edges));
        etIngMenCony.setBackground(ContextCompat.getDrawable(ctx, R.drawable.et_rounded_edges));
        etGastoMenCony.setBackground(ContextCompat.getDrawable(ctx, R.drawable.et_rounded_edges));
        etCasaCony.setBackground(ContextCompat.getDrawable(ctx, R.drawable.et_rounded_edges));
        etCelularCony.setBackground(ContextCompat.getDrawable(ctx, R.drawable.et_rounded_edges));

        txtNomBeneficiario.setBackground(ContextCompat.getDrawable(ctx, R.drawable.et_rounded_edges));
        txtApellidoPaternoBeneficiario.setBackground(ContextCompat.getDrawable(ctx, R.drawable.et_rounded_edges));
        txtApellidoMaternoBeneficiario.setBackground(ContextCompat.getDrawable(ctx, R.drawable.et_rounded_edges));
        txtParentescoBeneficiario.setBackground(ContextCompat.getDrawable(ctx, R.drawable.et_rounded_edges));

        etPropiedadesEco.setBackground(ContextCompat.getDrawable(ctx, R.drawable.et_rounded_edges));
        etValorAproxEco.setBackground(ContextCompat.getDrawable(ctx, R.drawable.et_rounded_edges));
        etUbicacionEco.setBackground(ContextCompat.getDrawable(ctx, R.drawable.et_rounded_edges));
        tvIngresoEco.setBackground(ContextCompat.getDrawable(ctx, R.drawable.et_rounded_edges));

        etNombreNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.et_rounded_edges));
        etCalleNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.et_rounded_edges));
        etNoExtNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.et_rounded_edges));
        etNoIntNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.et_rounded_edges));
        etManzanaNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.et_rounded_edges));
        etLoteNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.et_rounded_edges));
        etCpNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.et_rounded_edges));
        tvColoniaNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.et_rounded_edges));
        etCiudadNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.et_rounded_edges));
        tvLocalidadNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.et_rounded_edges));
        tvActEcoEspNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.et_rounded_edges));
        tvActEconomicaNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.et_rounded_edges));
        tvDestinoNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.et_rounded_edges));
        etOtroDestinoNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.et_rounded_edges));
        etAntiguedadNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.et_rounded_edges));
        etIngMenNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.et_rounded_edges));
        etOtrosIngNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.et_rounded_edges));
        etGastosMenNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.et_rounded_edges));
        etGastosAguaNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.et_rounded_edges));
        etGastosLuzNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.et_rounded_edges));
        etGastosTelNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.et_rounded_edges));
        etGastosRentaNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.et_rounded_edges));
        etGastosOtrosNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.et_rounded_edges));
        tvMediosPagoNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.et_rounded_edges));
        etOtroMedioPagoNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.et_rounded_edges));
        tvMontoMaxNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.et_rounded_edges));
        etCapacidadPagoNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.et_rounded_edges));
        tvNumOperacionNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.et_rounded_edges));
        etNumOperacionEfectNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.et_rounded_edges));
        tvDiasVentaNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.et_rounded_edges));
        etReferenciNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.et_rounded_edges));
        ibFotoFachNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.round_corner_blue));

        etNombreAval.setBackground(ContextCompat.getDrawable(ctx, R.drawable.et_rounded_edges));
        etApPaternoAval.setBackground(ContextCompat.getDrawable(ctx, R.drawable.et_rounded_edges));
        etApMaternoAval.setBackground(ContextCompat.getDrawable(ctx, R.drawable.et_rounded_edges));
        tvFechaNacAval.setBackground(ContextCompat.getDrawable(ctx, R.drawable.et_rounded_edges));
        tvEstadoNacAval.setBackground(ContextCompat.getDrawable(ctx, R.drawable.et_rounded_edges));
        etCurpAval.setBackground(ContextCompat.getDrawable(ctx, R.drawable.et_rounded_edges));
        tvParentescoAval.setBackground(ContextCompat.getDrawable(ctx, R.drawable.et_rounded_edges));
        tvTipoIdentificacionAval.setBackground(ContextCompat.getDrawable(ctx, R.drawable.et_rounded_edges));
        etNumIdentifAval.setBackground(ContextCompat.getDrawable(ctx, R.drawable.et_rounded_edges));
        tvOcupacionAval.setBackground(ContextCompat.getDrawable(ctx, R.drawable.et_rounded_edges));
        etCalleAval.setBackground(ContextCompat.getDrawable(ctx, R.drawable.et_rounded_edges));
        etNoExtAval.setBackground(ContextCompat.getDrawable(ctx, R.drawable.et_rounded_edges));
        etNoIntAval.setBackground(ContextCompat.getDrawable(ctx, R.drawable.et_rounded_edges));
        etManzanaAval.setBackground(ContextCompat.getDrawable(ctx, R.drawable.et_rounded_edges));
        etLoteAval.setBackground(ContextCompat.getDrawable(ctx, R.drawable.et_rounded_edges));
        etCpAval.setBackground(ContextCompat.getDrawable(ctx, R.drawable.et_rounded_edges));
        tvColoniaAval.setBackground(ContextCompat.getDrawable(ctx, R.drawable.et_rounded_edges));
        etCiudadAval.setBackground(ContextCompat.getDrawable(ctx, R.drawable.et_rounded_edges));
        tvLocalidadAval.setBackground(ContextCompat.getDrawable(ctx, R.drawable.et_rounded_edges));
        tvTipoCasaAval.setBackground(ContextCompat.getDrawable(ctx, R.drawable.et_rounded_edges));
        tvFamiliarAval.setBackground(ContextCompat.getDrawable(ctx, R.drawable.et_rounded_edges));
        etNombreTitularAval.setBackground(ContextCompat.getDrawable(ctx, R.drawable.et_rounded_edges));
        etCaracteristicasAval.setBackground(ContextCompat.getDrawable(ctx, R.drawable.et_rounded_edges));

        etNombreNegocioAval.setBackground(ContextCompat.getDrawable(ctx, R.drawable.et_rounded_edges));
        etIngMenAval.setBackground(ContextCompat.getDrawable(ctx, R.drawable.et_rounded_edges));
        etIngOtrosAval.setBackground(ContextCompat.getDrawable(ctx, R.drawable.et_rounded_edges));
        etGastosSemAval.setBackground(ContextCompat.getDrawable(ctx, R.drawable.et_rounded_edges));
        etGastosAguaAval.setBackground(ContextCompat.getDrawable(ctx, R.drawable.et_rounded_edges));
        etGastosLuzAval.setBackground(ContextCompat.getDrawable(ctx, R.drawable.et_rounded_edges));
        etGastosTelAval.setBackground(ContextCompat.getDrawable(ctx, R.drawable.et_rounded_edges));
        etGastosRentaAval.setBackground(ContextCompat.getDrawable(ctx, R.drawable.et_rounded_edges));
        etGastosOtrosAval.setBackground(ContextCompat.getDrawable(ctx, R.drawable.et_rounded_edges));
        tvMediosPagoAval.setBackground(ContextCompat.getDrawable(ctx, R.drawable.et_rounded_edges));
        etOtroMedioPagoAval.setBackground(ContextCompat.getDrawable(ctx, R.drawable.et_rounded_edges));
        etCapacidadPagoAval.setBackground(ContextCompat.getDrawable(ctx, R.drawable.et_rounded_edges));
        tvHoraLocAval.setBackground(ContextCompat.getDrawable(ctx, R.drawable.et_rounded_edges));
        tvActivosObservables.setBackground(ContextCompat.getDrawable(ctx, R.drawable.et_rounded_edges));
        etAntiguedadAval.setBackground(ContextCompat.getDrawable(ctx, R.drawable.et_rounded_edges));
        etTelCasaAval.setBackground(ContextCompat.getDrawable(ctx, R.drawable.et_rounded_edges));
        etCelularAval.setBackground(ContextCompat.getDrawable(ctx, R.drawable.et_rounded_edges));
        etTelMensAval.setBackground(ContextCompat.getDrawable(ctx, R.drawable.et_rounded_edges));
        etTelTrabajoAval.setBackground(ContextCompat.getDrawable(ctx, R.drawable.et_rounded_edges));
        etEmailAval.setBackground(ContextCompat.getDrawable(ctx, R.drawable.et_rounded_edges));
        etReferenciaAval.setBackground(ContextCompat.getDrawable(ctx, R.drawable.et_rounded_edges));
        ibFotoFachAval.setBackground(ContextCompat.getDrawable(ctx, R.drawable.round_corner_blue));

        etNombreRef.setBackground(ContextCompat.getDrawable(ctx, R.drawable.et_rounded_edges));
        etApPaternoRef.setBackground(ContextCompat.getDrawable(ctx, R.drawable.et_rounded_edges));
        etApMaternoRef.setBackground(ContextCompat.getDrawable(ctx, R.drawable.et_rounded_edges));
        tvFechaNacRef.setBackground(ContextCompat.getDrawable(ctx, R.drawable.et_rounded_edges));
        etCalleRef.setBackground(ContextCompat.getDrawable(ctx, R.drawable.et_rounded_edges));
        etNoExtRef.setBackground(ContextCompat.getDrawable(ctx, R.drawable.et_rounded_edges));
        etNoIntRef.setBackground(ContextCompat.getDrawable(ctx, R.drawable.et_rounded_edges));
        etLoteRef.setBackground(ContextCompat.getDrawable(ctx, R.drawable.et_rounded_edges));
        etManzanaRef.setBackground(ContextCompat.getDrawable(ctx, R.drawable.et_rounded_edges));
        etCpRef.setBackground(ContextCompat.getDrawable(ctx, R.drawable.et_rounded_edges));
        tvColoniaRef.setBackground(ContextCompat.getDrawable(ctx, R.drawable.et_rounded_edges));
        etCiudadRef.setBackground(ContextCompat.getDrawable(ctx, R.drawable.et_rounded_edges));
        tvLocalidadRef.setBackground(ContextCompat.getDrawable(ctx, R.drawable.et_rounded_edges));
        etTelCelRef.setBackground(ContextCompat.getDrawable(ctx, R.drawable.et_rounded_edges));

        etReferencia.setBackground(ContextCompat.getDrawable(ctx, R.drawable.et_rounded_edges));
        etCaracteristicasDomicilio.setBackground(ContextCompat.getDrawable(ctx, R.drawable.et_rounded_edges));

    }

    private void deshabilitarCampos() {
        SolicitudDao solicitudDao = new SolicitudDao(ctx);
        Solicitud solicitud = solicitudDao.findByIdSolicitud(Integer.parseInt(String.valueOf(id_solicitud)));
        if (solicitud != null && solicitud.getEstatus() > 1) {
            if (TBmain.getMenu().size() > 1)
                TBmain.getMenu().getItem(MENU_INDEX_UPDATE_ESTATUS).setVisible(true);
        } else {
            if (TBmain.getMenu().size() > 1)
                TBmain.getMenu().getItem(MENU_INDEX_UPDATE_ESTATUS).setVisible(false);
        }

        if (!is_edit) {
            cbNegEnDomCli.setEnabled(false);
            invalidateOptionsMenu();
        }

        etMontoPrestamo.setEnabled(isEditCre);
        etMontoRefinanciar.setEnabled(isEditCre);

        Cursor row = dBhelper.getRecords(TBL_CLIENTE_IND, " WHERE id_solicitud = ?", "", new String[]{String.valueOf(id_solicitud)});
        row.moveToFirst();
        if (!row.getString(36).trim().isEmpty()) {
            tvFechaNacCli.setEnabled(false);
            tvEstadoNacCli.setEnabled(false);
            etCurpCli.setEnabled(false);
            tvFechaNacCli.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            tvEstadoNacCli.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etCurpCli.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            for (int i = 0; i < rgGeneroCli.getChildCount(); i++) {
                rgGeneroCli.getChildAt(i).setEnabled(false);
            }
        } else {
            etCurpCli.setEnabled(isEditCli);
        }

        row.close();

        txtCampana.setEnabled(isEditCre);
        txtNombreRefiero.setEnabled(isEditCre);

        etNombreCli.setEnabled(false);
        etApPaternoCli.setEnabled(false);
        etApMaternoCli.setEnabled(false);
        etNumIdentifCli.setEnabled(isEditCli);
        etCalleCli.setEnabled(isEditCli);
        etNoExtCli.setEnabled(isEditCli);
        etNoIntCli.setEnabled(isEditCli);
        etLoteCli.setEnabled(isEditCli);
        etManzanaCli.setEnabled(isEditCli);
        etCpCli.setEnabled(isEditCli);
        etCiudadCli.setEnabled(isEditCli);
        etTelCasaCli.setEnabled(isEditCli);
        etCelularCli.setEnabled(isEditCli);
        etTelMensCli.setEnabled(isEditCli);
        etTelTrabajoCli.setEnabled(isEditCli);
        etTiempoSitio.setEnabled(isEditCli);
        etEmail.setEnabled(isEditCli);
        etReferenciCli.setEnabled(isEditCli);

        etNombreCony.setEnabled(isEditCon);
        etApPaternoCony.setEnabled(isEditCon);
        etApMaternoCony.setEnabled(isEditCon);
        etNacionalidadCony.setEnabled(isEditCon);
        etCalleCony.setEnabled(isEditCon);
        etNoExtCony.setEnabled(isEditCon);
        etNoIntCony.setEnabled(isEditCon);
        etLoteCony.setEnabled(isEditCon);
        etManzanaCony.setEnabled(isEditCon);
        etCpCony.setEnabled(isEditCon);
        etCiudadCony.setEnabled(isEditCon);
        etIngMenCony.setEnabled(isEditCon);
        etGastoMenCony.setEnabled(isEditCon);
        etCasaCony.setEnabled(isEditCon);
        etCelularCony.setEnabled(isEditCon);

        etPropiedadesEco.setEnabled(isEditEco);
        etValorAproxEco.setEnabled(isEditEco);
        etUbicacionEco.setEnabled(isEditEco);

/*        txtNomBeneficiario.setEnabled(false);
        txtApellidoPaternoBeneficiario.setEnabled(false);
        txtApellidoMaternoBeneficiario.setEnabled(false);
        txtParentescoBeneficiario.setEnabled(false);*/

        etNombreNeg.setEnabled(isEditNeg);
        etCalleNeg.setEnabled(isEditNeg);
        etNoExtNeg.setEnabled(isEditNeg);
        etNoIntNeg.setEnabled(isEditNeg);
        etLoteNeg.setEnabled(isEditNeg);
        etManzanaNeg.setEnabled(isEditNeg);
        etCpNeg.setEnabled(isEditNeg);
        etCiudadNeg.setEnabled(isEditNeg);

        etOtroDestinoNeg.setEnabled(isEditNeg);
        etAntiguedadNeg.setEnabled(isEditNeg);
        etIngMenNeg.setEnabled(isEditNeg);
        etOtrosIngNeg.setEnabled(isEditNeg);
        etGastosMenNeg.setEnabled(isEditNeg);
        etGastosAguaNeg.setEnabled(isEditNeg);
        etGastosLuzNeg.setEnabled(isEditNeg);
        etGastosTelNeg.setEnabled(isEditNeg);
        etGastosRentaNeg.setEnabled(isEditNeg);
        etGastosOtrosNeg.setEnabled(isEditNeg);
        etCapacidadPagoNeg.setEnabled(isEditNeg);
        etOtroMedioPagoNeg.setEnabled(isEditNeg);
        tvMontoMaxNeg.setEnabled(isEditNeg);
        tvNumOperacionNeg.setEnabled(isEditNeg);
        etNumOperacionEfectNeg.setEnabled(isEditNeg);
        etReferenciNeg.setEnabled(isEditNeg);

        etNombreAval.setEnabled(isEditAva);
        etApPaternoAval.setEnabled(isEditAva);
        etApMaternoAval.setEnabled(isEditAva);
        etCurpAval.setEnabled(isEditAva);
        etNumIdentifAval.setEnabled(isEditAva);
        etCalleAval.setEnabled(isEditAva);
        etNoExtAval.setEnabled(isEditAva);
        etNoIntAval.setEnabled(isEditAva);
        etLoteAval.setEnabled(isEditAva);
        etManzanaAval.setEnabled(isEditAva);
        etCpAval.setEnabled(isEditAva);
        etCiudadAval.setEnabled(isEditAva);
        etNombreTitularAval.setEnabled(isEditAva);
        etCaracteristicasAval.setEnabled(isEditAva);
        etNombreNegocioAval.setEnabled(isEditAva);
        etAntiguedadAval.setEnabled(isEditAva);
        etIngMenAval.setEnabled(isEditAva);
        etIngOtrosAval.setEnabled(isEditAva);
        etGastosSemAval.setEnabled(isEditAva);
        etGastosAguaAval.setEnabled(isEditAva);
        etGastosLuzAval.setEnabled(isEditAva);
        etGastosTelAval.setEnabled(isEditAva);
        etGastosRentaAval.setEnabled(isEditAva);
        etGastosOtrosAval.setEnabled(isEditAva);
        etCapacidadPagoAval.setEnabled(isEditAva);
        etOtroMedioPagoAval.setEnabled(isEditAva);
        etTelCasaAval.setEnabled(isEditAva);
        etCelularAval.setEnabled(isEditAva);
        etTelMensAval.setEnabled(isEditAva);
        etTelTrabajoAval.setEnabled(isEditAva);
        etEmailAval.setEnabled(isEditAva);
        etReferenciaAval.setEnabled(isEditAva);

        etNombreRef.setEnabled(isEditRef);
        etApPaternoRef.setEnabled(isEditRef);
        etApMaternoRef.setEnabled(isEditRef);
        etCalleRef.setEnabled(isEditRef);
        etNoExtRef.setEnabled(isEditRef);
        etNoIntRef.setEnabled(isEditRef);
        etLoteRef.setEnabled(isEditRef);
        etManzanaRef.setEnabled(isEditRef);
        etCpRef.setEnabled(isEditRef);
        etCiudadRef.setEnabled(isEditRef);
        etTelCelRef.setEnabled(isEditRef);

        if (!isEditCre) {
            tvPlazo.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked_left));
            tvFrecuencia.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked_right));
            tvFechaDesembolso.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            tvHoraVisita.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etMontoPrestamo.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            tvDestino.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            tvRiesgo.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etMontoRefinanciar.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            txtCampana.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            txtNombreRefiero.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));

        } else
            ivError1.setVisibility(View.VISIBLE);

        if (!isEditCli) {
            etNombreCli.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etApPaternoCli.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etApMaternoCli.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            tvFechaNacCli.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            for (int i = 0; i < rgGeneroCli.getChildCount(); i++) {
                rgGeneroCli.getChildAt(i).setEnabled(false);
            }
            tvEstadoNacCli.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etCurpCli.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            tvOcupacionCli.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            tvTipoIdentificacion.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etNumIdentifCli.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            tvEstudiosCli.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            tvEstadoCivilCli.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            for (int i = 0; i < rgBienes.getChildCount(); i++) {
                rgBienes.getChildAt(i).setEnabled(false);
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
            ibFotoFachCli.setEnabled(false);
            ibFotoFachCli.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            ibMapCli.setVisibility(View.GONE);

        } else
            ivError2.setVisibility(View.VISIBLE);

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
        } else
            ivError3.setVisibility(View.VISIBLE);

        txtNomBeneficiario.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        txtApellidoPaternoBeneficiario.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        txtApellidoMaternoBeneficiario.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        txtParentescoBeneficiario.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));

        if (!isEditEco) {
            etPropiedadesEco.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etValorAproxEco.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etUbicacionEco.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            tvIngresoEco.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        } else
            ivError4.setVisibility(View.VISIBLE);

        if (!isEditNeg) {
            cbNegEnDomCli.setEnabled(false);
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
            ibFotoFachNeg.setEnabled(false);
            ibFotoFachNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            ibMapNeg.setVisibility(View.GONE);

        } else
            ivError5.setVisibility(View.VISIBLE);

        if (!isEditAva) {
            etNombreAval.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etApPaternoAval.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etApMaternoAval.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            tvFechaNacAval.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            for (int i = 0; i < rgGeneroAval.getChildCount(); i++) {
                rgGeneroAval.getChildAt(i).setEnabled(false);
            }
            tvEstadoNacAval.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etCurpAval.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
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
                rgNegocioAval.getChildAt(i).setEnabled(false);
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
            ibFotoFachAval.setEnabled(false);
            ibFotoFachAval.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            ibMapAval.setVisibility(View.GONE);
        } else
            ivError6.setVisibility(View.VISIBLE);

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
        } else
            ivError7.setVisibility(View.VISIBLE);

        if (!isEditCro) {
            etReferencia.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etCaracteristicasDomicilio.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        }

        if (!isEditPol) {
            for (int i = 0; i < rgPropietarioReal.getChildCount(); i++) {
                rgPropietarioReal.getChildAt(i).setEnabled(false);
            }
            for (int i = 0; i < rgProveedor.getChildCount(); i++) {
                rgProveedor.getChildAt(i).setEnabled(false);
            }
            for (int i = 0; i < rgPoliticamenteExp.getChildCount(); i++) {
                rgPoliticamenteExp.getChildAt(i).setEnabled(false);
            }
        }

    }

    private boolean habilitarSolicitud() {
        boolean solicitudActivada = false;

        if (id_solicitud <= 0) return solicitudActivada;

        SolicitudDao solicitudDao = new SolicitudDao(ctx);
        Solicitud solicitud = solicitudDao.findByIdSolicitud(Integer.parseInt(String.valueOf(id_solicitud)));

        if (solicitud != null) {
            CreditoIndDao creditoIndDao = new CreditoIndDao(ctx);
            CreditoInd creditoInd = creditoIndDao.findByIdSolicitud(solicitud.getIdSolicitud());

            if (creditoInd != null) {
                solicitud.setEstatus(0);
                solicitudDao.updateEstatus(solicitud);
                solicitudActivada = true;
            }
        }

        return solicitudActivada;
    }

    private boolean deshabilitarSolicitud() {
        TBmain.getMenu().getItem(MENU_INDEX_ENVIAR).setVisible(false);

        boolean solicitudBloqueada = false;

        if (id_solicitud <= 0) return solicitudBloqueada;

        SolicitudDao solicitudDao = new SolicitudDao(ctx);
        Solicitud solicitud = solicitudDao.findByIdSolicitud(Integer.parseInt(String.valueOf(id_solicitud)));

        if (solicitud != null) {
            CreditoIndDao creditoIndDao = new CreditoIndDao(ctx);
            CreditoInd creditoInd = creditoIndDao.findByIdSolicitud(solicitud.getIdSolicitud());

            if (creditoInd != null) {
                solicitud.setEstatus(2);
                solicitudDao.updateEstatus(solicitud);
                solicitudBloqueada = true;
            }
        }

        return solicitudBloqueada;
    }

    private void enviarJSONObjects() {
        JSONObject json_solicitud = new JSONObject();
        SolicitudDao solicitudDao = new SolicitudDao(ctx);
        Solicitud solicitud = solicitudDao.findByIdSolicitud(Integer.parseInt(String.valueOf(id_solicitud)));

        if (solicitud != null) {
            try {
                json_solicitud.put(Solicitud.TBL, solicitud);

                CreditoIndDao creditoIndDao = new CreditoIndDao(ctx);
                CreditoInd creditoInd = creditoIndDao.findByIdSolicitud(solicitud.getIdSolicitud());
                if (creditoInd != null) json_solicitud.put(CreditoInd.TBL, creditoInd);

                ClienteDao clienteDao = new ClienteDao(ctx);
                Cliente cliente = clienteDao.findByIdSolicitud(solicitud.getIdSolicitud());
                if (cliente != null) json_solicitud.put(Cliente.TBL, cliente);

                DireccionDao direccionDao = new DireccionDao(ctx);
                Direccion direccionCliente = direccionDao.findByIdDireccion(Long.parseLong(cliente.getDireccionId()));
                if (direccionCliente != null)
                    json_solicitud.put(Direccion.TBL + "_cliente", direccionCliente);

                ConyugueDao conyugueDao = new ConyugueDao(ctx);
                Conyugue conyugue = conyugueDao.findByIdSolicitud(solicitud.getIdSolicitud());
                if (conyugue != null) {
                    json_solicitud.put(Conyugue.TBL, conyugue);
                    Direccion direccionConyugue = direccionDao.findByIdDireccion(Long.parseLong(conyugue.getDireccionId()));
                    if (direccionConyugue != null)
                        json_solicitud.put(Direccion.TBL + "_conyugue", direccionConyugue);
                }

                AvalDao avalDao = new AvalDao(ctx);
                Aval aval = avalDao.findByIdSolicitud(solicitud.getIdSolicitud());
                if (aval != null) {
                    json_solicitud.put(Aval.TBL, aval);
                    Direccion direccionAval = direccionDao.findByIdDireccion(Long.parseLong(aval.getDireccionId()));
                    if (direccionAval != null)
                        json_solicitud.put(Direccion.TBL + "_aval", direccionAval);
                }

                DocumentoDao documentoDao = new DocumentoDao(ctx);
                Documento documento = documentoDao.findByIdSolicitud(solicitud.getIdSolicitud());
                if (documento != null) json_solicitud.put(Documento.TBL, documento);

                EconomicoDao economicoDao = new EconomicoDao(ctx);
                Economico economico = economicoDao.findByIdSolicitud(solicitud.getIdSolicitud());
                if (economico != null) json_solicitud.put(Economico.TBL, economico);

                NegocioDao negocioDao = new NegocioDao(ctx);
                Negocio negocio = negocioDao.findByIdSolicitud(solicitud.getIdSolicitud());
                if (negocio != null) {
                    json_solicitud.put(Negocio.TBL, negocio);

                    Direccion direccionNegocio = direccionDao.findByIdDireccion(Long.parseLong(negocio.getDireccionId()));
                    if (direccionNegocio != null)
                        json_solicitud.put(Direccion.TBL + "_negocio", direccionNegocio);
                }

                PoliticaPldDao politicaPldDao = new PoliticaPldDao(ctx);
                PoliticaPld politicaPld = politicaPldDao.findByIdSolicitud(solicitud.getIdSolicitud());
                if (politicaPld != null) json_solicitud.put(PoliticaPld.TBL, politicaPld);

                ReferenciaDao referenciaDao = new ReferenciaDao(ctx);
                Referencia referencia = referenciaDao.findByIdSolicitud(solicitud.getIdSolicitud());
                if (referencia != null) {
                    json_solicitud.put(Referencia.TBL, referencia);

                    Direccion direccionReferencia = direccionDao.findByIdDireccion(Long.parseLong(referencia.getDireccionId()));
                    if (direccionReferencia != null)
                        json_solicitud.put(Direccion.TBL + "_referencia", direccionReferencia);
                }


            } catch (Exception e) {
                Toast.makeText(ctx, e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            final AlertDialog loadingSendData = Popups.showLoadingDialog(context, R.string.please_wait, R.string.loading_info);
            loadingSendData.show();

            SessionManager session = SessionManager.getInstance(ctx);
            SolicitudIndService solicitudIndService = RetrofitClient.newInstance(ctx).create(SolicitudIndService.class);
            Call<ApiResponse> call = solicitudIndService.jsonOriginacionInd("Bearer " + session.getUser().get(7), new Gson().toJson(json_solicitud));

            call.enqueue(new Callback<ApiResponse>() {
                @Override
                public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                    ApiResponse apiResponse;

                    switch (response.code()) {
                        case 200:
                            apiResponse = response.body();
                            loadingSendData.dismiss();

                            if (apiResponse.getError() == null) {
                                Toast.makeText(ctx, "¡Solicitud compartida!", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(ctx, apiResponse.getMensaje(), Toast.LENGTH_SHORT).show();
                            }
                            break;
                        default:
                            loadingSendData.dismiss();
                            Toast.makeText(ctx, response.message(), Toast.LENGTH_SHORT).show();
                            break;
                    }
                }

                @Override
                public void onFailure(Call<ApiResponse> call, Throwable t) {
                    loadingSendData.dismiss();
                    Log.e("AQUI ERROR", t.getMessage());
                    Toast.makeText(ctx, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void sendSolicitud() {
        if (esSolicitudValida()) {
            Update("estatus_completado", TBL_CREDITO_IND, "1");
            Update("estatus_completado", TBL_CLIENTE_IND, "1");
            Update("estatus_completado", TBL_CONYUGE_IND, "1");
            Update("estatus_completado", TBL_ECONOMICOS_IND, "1");
            Update("estatus_completado", TBL_NEGOCIO_IND, "1");
            Update("estatus_completado", TBL_AVAL_IND, "1");
            Update("estatus_completado", TBL_REFERENCIA_IND, "1");
            Update("estatus_completado", TBL_CROQUIS_IND, "1");
            Update("estatus_completado", TBL_POLITICAS_PLD_IND, "1");
            Update("estatus_completado", TBL_DOCUMENTOS, "1");

            Update("comentario_rechazo", TBL_CLIENTE_IND, "");
            Update("comentario_rechazo", TBL_NEGOCIO_IND, "");
            Update("comentario_rechazo", TBL_AVAL_IND, "");
            Update("comentario_rechazo", TBL_REFERENCIA_IND, "");
            Update("comentario_rechazo", TBL_CROQUIS_IND, "");

            ContentValues cv = new ContentValues();
            cv.put("estatus", 1);
            cv.put("fecha_termino", Miscellaneous.ObtenerFecha("timestamp"));

            db.update(TBL_SOLICITUDES, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});

            //VERSION PRODUCCION
            /*
            Servicios_Sincronizado ss = new Servicios_Sincronizado();
            ss.SendOriginacionInd(ctx, true);
            Toast.makeText(ctx, "Termina guardado de solicitud", Toast.LENGTH_SHORT).show();

            finish();
            */

            //PRUEBAS PARA NUEVA VERSION
            dialog_sending_solicitud_individual dialogSendSI = new dialog_sending_solicitud_individual();
            Bundle b = new Bundle();
            b.putLong(ID_SOLICITUD, id_solicitud);
            b.putBoolean(ES_RENOVACION, false);
            dialogSendSI.setArguments(b);
            dialogSendSI.setCancelable(false);
            dialogSendSI.show(getSupportFragmentManager(), NameFragments.DIALOGSENDINGSOLICITUDINDIVIDUAL);
            //dialogSendSI.enviarBeneficiario(id_solicitud);
        }
    }

    private boolean esSolicitudValida() {
        boolean flag = false;

        boolean credito, cliente, conyuge, beneficiario, economicos, negocio, aval, referencia, croquis, politicas, documentacion, campana;
        credito = saveDatosCredito();
        campana = saveDatosCampana();
        croquis = saveCroquis();
        cliente = saveDatosPersonales();
        beneficiario = saveBeneficiario(); //AGREGAR DETALLES FINALES EN EL FORMULARIO - FLAG IN FALSE


        if (!croquis || !cliente) {
            ivError2.setVisibility(View.VISIBLE);
        } else {
            ivError2.setVisibility(View.GONE);
        }

        if (Miscellaneous.GetStr(tvEstadoCivilCli).equals("CASADO(A)") || Miscellaneous.GetStr(tvEstadoCivilCli).equals("UNION LIBRE")) {
            conyuge = saveConyuge();
        } else {
            conyuge = true;
        }

        if (!Miscellaneous.GetStr(etMontoPrestamo).replace(",", "").isEmpty() && Integer.parseInt(Miscellaneous.GetStr(etMontoPrestamo).replace(",", "")) >= 30000) {
            economicos = saveDatosEconomicos();
        } else {
            economicos = true;
        }

        negocio = saveDatosNegocio();
        aval = saveDatosAval();
        referencia = saveReferencia();
        politicas = savePoliticas();
        documentacion = saveDocumentacion();

        if (credito && campana && cliente && beneficiario && conyuge && economicos && negocio && aval && referencia && croquis && politicas && documentacion) {
            flag = true;
        } else {
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

        return flag;
    }

    private void obtenerEstatusSolicitud() {
        final AlertDialog loadingEstatus = Popups.showLoadingDialog(context, R.string.please_wait, R.string.loading_info);
        loadingEstatus.show();

        SolicitudDao solicitudDao = new SolicitudDao(ctx);
        Solicitud solicitud = solicitudDao.findByIdSolicitud(Integer.parseInt(String.valueOf(id_solicitud)));

        if (solicitud != null && solicitud.getEstatus() > 1) {

            SessionManager session = SessionManager.getInstance(ctx);
            SolicitudIndService solicitudIndService = RetrofitClient.newInstance(ctx).create(SolicitudIndService.class);
            Call<List<SolicitudDetalleEstatusInd>> call = solicitudIndService.showEstatusSolicitudes("Bearer " + session.getUser().get(7));

            call.enqueue(new Callback<List<SolicitudDetalleEstatusInd>>() {
                @Override
                public void onResponse(Call<List<SolicitudDetalleEstatusInd>> call, Response<List<SolicitudDetalleEstatusInd>> response) {
                    switch (response.code()) {
                        case 200:
                            List<SolicitudDetalleEstatusInd> solicitudes = response.body();

                            for (SolicitudDetalleEstatusInd se : solicitudes) {
                                if (se.getTipoSolicitud() == 1 && Integer.compare(se.getId(), solicitud.getIdOriginacion()) == 0) {
                                    ClienteDao clienteDao = new ClienteDao(ctx);
                                    SolicitudDao solicitudDao = new SolicitudDao(ctx);

                                    Cliente cliente = null;
                                    Solicitud solicitudTemp = solicitudDao.findByIdOriginacion(se.getId());

                                    if (solicitudTemp != null)
                                        cliente = clienteDao.findByIdSolicitud(solicitudTemp.getIdSolicitud());

                                    if (cliente != null) {
                                        String comentario = "";

                                        Log.e("AQUI CLIENTE ID", String.valueOf(cliente.getIdCliente()));
                                        Log.e("AQUI SOLICITUD ESTADO", String.valueOf(se.getSolicitudEstadoId()));

                                        if (se.getSolicitudEstadoId() == 1) {
                                            solicitudTemp.setEstatus(2);
                                            comentario = "EN REVISIÓN";
                                        } else if (se.getSolicitudEstadoId() == 3) {
                                            solicitudTemp.setEstatus(3);
                                            comentario = "VALIDADO";
                                        } else {
                                            //solicitud.setEstatus(3);
                                            //comentario = cliente.getComentarioRechazo();
                                        }

                                        if (se.getSolicitudEstadoId() == 2)
                                            solicitudTemp.setEstatus(5);


                                        Log.e("AQUI comentario", comentario);

                                        cliente.setComentarioRechazo(comentario);
                                        clienteDao.updateEstatus(cliente);

                                        solicitudDao.updateEstatus(solicitudTemp);
                                    }
                                }
                            }
                            obtenerRechazo(loadingEstatus, solicitud);
                            break;
                        default:
                            obtenerRechazo(loadingEstatus, solicitud);
                            Log.e("AQUI ", response.message());
                            break;
                    }
                }

                @Override
                public void onFailure(Call<List<SolicitudDetalleEstatusInd>> call, Throwable t) {
                    obtenerRechazo(loadingEstatus, solicitud);
                    Log.e("Error", "failAGF" + t.getMessage());
                }
            });

        }

    }

    private void obtenerRechazo(AlertDialog alert, Solicitud solicitud) {
        SessionManager session = SessionManager.getInstance(ctx);
        ManagerInterface api = RetrofitClient.generalRF(CONTROLLER_SOLICITUDES, ctx).create(ManagerInterface.class);
        Call<List<MSolicitudRechazoInd>> call = api.getSolicitudRechazoInd("Bearer " + session.getUser().get(7));
        call.enqueue(new Callback<List<MSolicitudRechazoInd>>() {
            @Override
            public void onResponse(Call<List<MSolicitudRechazoInd>> call, Response<List<MSolicitudRechazoInd>> response) {
                switch (response.code()) {
                    case 200:
                        List<MSolicitudRechazoInd> solicitudes = response.body();
                        if (solicitudes.size() > 0) {
                            for (MSolicitudRechazoInd item : solicitudes) {
                                ContentValues cv;
                                String sql = "";
                                Cursor row = null;
                                if (item.getTipoSolicitud() == 1 && Integer.compare(item.getId(), solicitud.getIdOriginacion()) == 0) {

                                    Log.e("EstautsXXXXXX", item.getSolicitudEstadoId() + " XXXXXXXXXx");
                                    //                 0                1               2              3                4                 5             6             7               8
                                    sql = "SELECT s.id_solicitud, cre.id_credito, cli.id_cliente, con.id_conyuge, eco.id_economico, neg.id_negocio, ava.id_aval, ref.id_referencia, cro.id FROM " + TBL_SOLICITUDES + " AS s " +
                                            "JOIN " + TBL_CREDITO_IND + " AS cre ON s.id_solicitud = cre.id_solicitud " +
                                            "JOIN " + TBL_CLIENTE_IND + " AS cli ON s.id_solicitud = cli.id_solicitud " +
                                            "JOIN " + TBL_CONYUGE_IND + " AS con ON s.id_solicitud = con.id_solicitud " +
                                            "JOIN " + TBL_ECONOMICOS_IND + " AS eco ON s.id_solicitud = eco.id_solicitud " +
                                            "JOIN " + TBL_NEGOCIO_IND + " AS neg ON s.id_solicitud = neg.id_solicitud " +
                                            "JOIN " + TBL_AVAL_IND + " AS ava ON s.id_solicitud = ava.id_solicitud " +
                                            "JOIN " + TBL_REFERENCIA_IND + " AS ref ON s.id_solicitud = ref.id_solicitud " +
                                            "JOIN " + TBL_CROQUIS_IND + " AS cro ON s.id_solicitud = cro.id_solicitud " +
                                            "WHERE s.id_originacion = ? AND s.estatus >= 2";
                                    row = db.rawQuery(sql, new String[]{String.valueOf(item.getId())});

                                    Log.e("XXXXCount", row.getCount() + " Total");
                                    if (row.getCount() > 0) {
                                        row.moveToFirst();
                                        if (item.getSolicitudEstadoId() == 4) { //Actualiza solicitudes de originacion que fueron rechazadas por error de datos

                                            cv = new ContentValues();
                                            cv.put("comentario_rechazo", "");
                                            db.update(TBL_CLIENTE_IND, cv, "id_solicitud = ? AND id_cliente = ?", new String[]{row.getString(0), row.getString(2)});

                                            if (item.getEstatusCliente() != null && !(Boolean) item.getEstatusCliente()) {
                                                cv = new ContentValues();
                                                cv.put("estatus", 0);
                                                cv.put("fecha_guardado", "");
                                                cv.put("fecha_termino", "");
                                                db.update(TBL_SOLICITUDES, cv, "id_solicitud = ?", new String[]{row.getString(0)});

                                                cv = new ContentValues();
                                                cv.put("estatus_completado", 0);
                                                cv.put("comentario_rechazo", Miscellaneous.validStr(item.getComentarioAdminCliente()));
                                                int i_update = db.update(TBL_CLIENTE_IND, cv, "id_solicitud = ? AND id_cliente = ?", new String[]{row.getString(0), row.getString(2)});
                                                Log.e("Update", "Update: " + i_update);

                                                cv = new ContentValues();
                                                cv.put("estatus_completado", 0);
                                                db.update(TBL_CONYUGE_IND, cv, "id_solicitud = ? AND id_conyuge = ?", new String[]{row.getString(0), row.getString(3)});

                                                cv = new ContentValues();
                                                cv.put("estatus_completado", 0);
                                                db.update(TBL_DOCUMENTOS, cv, "id_solicitud = ? ", new String[]{row.getString(0)});
                                            }

                                            if (item.getEstatusNegocio() != null && !(Boolean) item.getEstatusNegocio()) {
                                                cv = new ContentValues();
                                                cv.put("estatus", 0);
                                                cv.put("fecha_guardado", "");
                                                cv.put("fecha_termino", "");
                                                db.update(TBL_SOLICITUDES, cv, "id_solicitud = ?", new String[]{row.getString(0)});

                                                cv = new ContentValues();
                                                cv.put("estatus_completado", 0);
                                                cv.put("comentario_rechazo", Miscellaneous.validStr(item.getComentarioAdminNegocio()));
                                                db.update(TBL_NEGOCIO_IND, cv, "id_solicitud = ? AND id_negocio = ?", new String[]{row.getString(0), row.getString(5)});
                                            }

                                            Log.e("NEgocioValieCli", String.valueOf((item.getEstatusCliente() == null || (item.getEstatusCliente() != null && !(Boolean) item.getEstatusCliente()))));
                                            Log.e("negocioVal", String.valueOf(item.getEstatusAval() != null && !(Boolean) item.getEstatusAval()));
                                            if (item.getEstatusAval() != null && !(Boolean) item.getEstatusAval()) {
                                                cv = new ContentValues();
                                                cv.put("estatus", 0);
                                                cv.put("fecha_guardado", "");
                                                cv.put("fecha_termino", "");
                                                db.update(TBL_SOLICITUDES, cv, "id_solicitud = ?", new String[]{row.getString(0)});

                                                cv = new ContentValues();
                                                cv.put("estatus_completado", 0);
                                                cv.put("comentario_rechazo", Miscellaneous.validStr(item.getComentarioAdminAval()));
                                                db.update(TBL_AVAL_IND, cv, "id_solicitud = ? AND id_aval = ?", new String[]{row.getString(0), row.getString(6)});
                                            }

                                            Log.e("ReferenciaXXX", String.valueOf((item.getEstatusCliente() == null || (item.getEstatusCliente() != null && !(Boolean) item.getEstatusCliente()))
                                                    && item.getEstatusReferencia() != null && !(Boolean) item.getEstatusReferencia()));

                                            if (item.getEstatusReferencia() != null && !(Boolean) item.getEstatusReferencia()) {
                                                cv = new ContentValues();
                                                cv.put("estatus", 0);
                                                cv.put("fecha_guardado", "");
                                                cv.put("fecha_termino", "");
                                                db.update(TBL_SOLICITUDES, cv, "id_solicitud = ?", new String[]{row.getString(0)});

                                                cv = new ContentValues();
                                                cv.put("estatus_completado", 0);
                                                cv.put("comentario_rechazo", Miscellaneous.validStr(item.getComentarioAdminReferencia()));
                                                db.update(TBL_REFERENCIA_IND, cv, "id_solicitud = ? AND id_referencia = ?", new String[]{row.getString(0), row.getString(7)});
                                            }

                                            if (item.getEstatusCroquis() != null && !(Boolean) item.getEstatusCroquis()) {
                                                cv = new ContentValues();
                                                cv.put("estatus", 0);
                                                cv.put("fecha_guardado", "");
                                                cv.put("fecha_termino", "");
                                                db.update(TBL_SOLICITUDES, cv, "id_solicitud = ?", new String[]{row.getString(0)});

                                                cv = new ContentValues();
                                                cv.put("estatus_completado", 0);
                                                cv.put("comentario_rechazo", Miscellaneous.validStr(item.getComentarioAdminCroquis()));
                                                db.update(TBL_CROQUIS_IND, cv, "id_solicitud = ? AND id = ?", new String[]{row.getString(0), row.getString(8)});
                                            }
                                        } else if (item.getSolicitudEstadoId() == 2) { //Actualiza solicitudes de originacion que fueron solicitudes rechazadas
                                            Log.e("XXXXXXXX", "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
                                            Log.e("Comentario", Miscellaneous.validStr(item.getComentarioAdminCliente()));
                                            cv = new ContentValues();
                                            cv.put("estatus", 5);
                                            db.update(TBL_SOLICITUDES, cv, "id_solicitud = ?", new String[]{row.getString(0)});

                                            cv = new ContentValues();
                                            cv.put("comentario_rechazo", Miscellaneous.validStr("NO AUTORIZADO - " + item.getComentarioAdminCliente()));
                                            db.update(TBL_CLIENTE_IND, cv, "id_solicitud = ? AND id_cliente = ?", new String[]{row.getString(0), row.getString(8)});
                                        }
                                    }
                                    row.close();
                                }
                            }
                        }
                        alert.dismiss();
                        finish();
                        break;
                    default:
                        alert.dismiss();
                        break;
                }
            }

            @Override
            public void onFailure(Call<List<MSolicitudRechazoInd>> call, Throwable t) {
                alert.dismiss();
            }
        });

    }
}
