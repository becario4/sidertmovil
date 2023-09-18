package com.sidert.sidertmovil.views.renovacion;

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
import com.sidert.sidertmovil.fragments.dialogs.dialog_plazo_ind;
import com.sidert.sidertmovil.fragments.dialogs.dialog_sending_solicitud_individual;
import com.sidert.sidertmovil.fragments.dialogs.dialog_time_picker;
import com.sidert.sidertmovil.models.ApiResponse;
import com.sidert.sidertmovil.models.MSolicitudRechazoInd;
import com.sidert.sidertmovil.models.ModeloCatalogoGral;
import com.sidert.sidertmovil.models.catalogos.Colonia;
import com.sidert.sidertmovil.models.catalogos.ColoniaDao;
import com.sidert.sidertmovil.models.datosCampañas.datosCampanaDao;
import com.sidert.sidertmovil.models.permiso.PermisoResponse;
import com.sidert.sidertmovil.models.solicitudes.SolicitudRen;
import com.sidert.sidertmovil.models.solicitudes.SolicitudRenDao;
import com.sidert.sidertmovil.models.solicitudes.solicitudind.SolicitudDetalleEstatusInd;
import com.sidert.sidertmovil.models.solicitudes.solicitudind.renovacion.AvalRen;
import com.sidert.sidertmovil.models.solicitudes.solicitudind.renovacion.AvalRenDao;
import com.sidert.sidertmovil.models.solicitudes.solicitudind.renovacion.BeneficiarioIndRenDao;
import com.sidert.sidertmovil.models.solicitudes.solicitudind.renovacion.ClienteRen;
import com.sidert.sidertmovil.models.solicitudes.solicitudind.renovacion.ClienteRenDao;
import com.sidert.sidertmovil.models.solicitudes.solicitudind.renovacion.ConyugueRen;
import com.sidert.sidertmovil.models.solicitudes.solicitudind.renovacion.ConyugueRenDao;
import com.sidert.sidertmovil.models.solicitudes.solicitudind.renovacion.CreditoIndRen;
import com.sidert.sidertmovil.models.solicitudes.solicitudind.renovacion.CreditoIndRenDao;
import com.sidert.sidertmovil.models.solicitudes.solicitudind.renovacion.DireccionRen;
import com.sidert.sidertmovil.models.solicitudes.solicitudind.renovacion.DireccionRenDao;
import com.sidert.sidertmovil.models.solicitudes.solicitudind.renovacion.DocumentoRen;
import com.sidert.sidertmovil.models.solicitudes.solicitudind.renovacion.DocumentoRenDao;
import com.sidert.sidertmovil.models.solicitudes.solicitudind.renovacion.EconomicoRen;
import com.sidert.sidertmovil.models.solicitudes.solicitudind.renovacion.EconomicoRenDao;
import com.sidert.sidertmovil.models.solicitudes.solicitudind.renovacion.NegocioRen;
import com.sidert.sidertmovil.models.solicitudes.solicitudind.renovacion.NegocioRenDao;
import com.sidert.sidertmovil.models.solicitudes.solicitudind.renovacion.PoliticaPldRen;
import com.sidert.sidertmovil.models.solicitudes.solicitudind.renovacion.PoliticaPldRenDao;
import com.sidert.sidertmovil.models.solicitudes.solicitudind.renovacion.ReferenciaRen;
import com.sidert.sidertmovil.models.solicitudes.solicitudind.renovacion.ReferenciaRenDao;
import com.sidert.sidertmovil.services.permiso.IPermisoService;
import com.sidert.sidertmovil.services.solicitud.solicitudind.SolicitudIndService;
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
import static com.sidert.sidertmovil.utils.Constants.COLONIA;
import static com.sidert.sidertmovil.utils.Constants.COLONIAS;
import static com.sidert.sidertmovil.utils.Constants.CONTROLLER_SOLICITUDES;
import static com.sidert.sidertmovil.utils.Constants.DATE_CURRENT;
import static com.sidert.sidertmovil.utils.Constants.DAY_CURRENT;
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
import static com.sidert.sidertmovil.utils.Constants.MONTH_CURRENT;
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
import static com.sidert.sidertmovil.utils.Constants.TBL_AVAL_IND_REN;
import static com.sidert.sidertmovil.utils.Constants.TBL_CLIENTE_IND;
import static com.sidert.sidertmovil.utils.Constants.TBL_CLIENTE_IND_REN;
import static com.sidert.sidertmovil.utils.Constants.TBL_CONYUGE_IND_REN;
import static com.sidert.sidertmovil.utils.Constants.TBL_CREDITO_IND;
import static com.sidert.sidertmovil.utils.Constants.TBL_CREDITO_IND_REN;
import static com.sidert.sidertmovil.utils.Constants.TBL_CROQUIS_IND_REN;
import static com.sidert.sidertmovil.utils.Constants.TBL_DATOS_BENEFICIARIO_REN;
import static com.sidert.sidertmovil.utils.Constants.TBL_DATOS_CREDITO_CAMPANA;
import static com.sidert.sidertmovil.utils.Constants.TBL_DIRECCIONES_REN;
import static com.sidert.sidertmovil.utils.Constants.TBL_DOCUMENTOS_INTEGRANTE_REN;
import static com.sidert.sidertmovil.utils.Constants.TBL_DOCUMENTOS_REN;
import static com.sidert.sidertmovil.utils.Constants.TBL_ECONOMICOS_IND_REN;
import static com.sidert.sidertmovil.utils.Constants.TBL_NEGOCIO_IND;
import static com.sidert.sidertmovil.utils.Constants.TBL_NEGOCIO_IND_REN;
import static com.sidert.sidertmovil.utils.Constants.TBL_POLITICAS_PLD_IND_REN;
import static com.sidert.sidertmovil.utils.Constants.TBL_REFERENCIA_IND;
import static com.sidert.sidertmovil.utils.Constants.TBL_REFERENCIA_IND_REN;
import static com.sidert.sidertmovil.utils.Constants.TBL_SOLICITUDES_REN;
import static com.sidert.sidertmovil.utils.Constants.TIMESTAMP;
import static com.sidert.sidertmovil.utils.Constants.TIPO;
import static com.sidert.sidertmovil.utils.Constants.TIPO_SOLICITUD;
import static com.sidert.sidertmovil.utils.Constants.TITULO;
import static com.sidert.sidertmovil.utils.Constants.YEAR_CURRENT;
import static com.sidert.sidertmovil.utils.Constants.firma;
import static com.sidert.sidertmovil.utils.Constants.question;
import static com.sidert.sidertmovil.utils.Constants.warning;
import static io.card.payment.CardIOActivity.RESULT_SCAN_SUPPRESSED;

/**
 * Clase para guardar los datos de la solicitud de renovacion individual
 */
public class RenovacionCreditoInd extends AppCompatActivity implements dialog_plazo_ind.OnCompleteListener {
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
    private String[] _max_pagos_mes;
    private String[] _campana;
    private Integer indRen = 3;
    private ArrayList<Integer> selectedItemsDias;
    private ArrayList<Integer> selectedItemsMediosPago;
    private ArrayList<Integer> selectedItemsActivos;
    private DBhelper dBhelper;
    private SQLiteDatabase db;
    private SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_DATE_GNRAL);
    private Calendar myCalendar;
    private long id_solicitud = 0;
    private Integer cliente_id = 0;
    private String direccionIdCli, direccionIdCony, direccionIdNeg, direccionIdAval, direccionIdRef;
    private boolean is_edit = true;
    /**
     * Botones para avanzar o retroceder en las secciones del formulario
     */
    private FloatingActionButton btnContinuar0;
    private FloatingActionButton btnContinuar1;
    private FloatingActionButton btnContinuar2;
    private FloatingActionButton btnContinuar3;
    private FloatingActionButton btnContinuar4;
    private FloatingActionButton btnContinuar5;
    private FloatingActionButton btnContinuar6;
    //private FloatingActionButton btnContinuar7;
    private FloatingActionButton btnContinuar8;
    private FloatingActionButton btnContinuarBeni;
    private FloatingActionButton btnRegresar1;
    private FloatingActionButton btnRegresar2;
    private FloatingActionButton btnRegresar3;
    private FloatingActionButton btnRegresar4;
    private FloatingActionButton btnRegresar5;
    private FloatingActionButton btnRegresar6;
    //private FloatingActionButton btnRegresar7;
    private FloatingActionButton btnRegresar8;
    private FloatingActionButton btnRegresarBeni;
    private FloatingActionButton btnRegresar10;
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
    private EditText etMontoRefinanciar;
    private TextView txtCampanaRen;
    private EditText txtNombreRefieroRen;
    //=========================================
    //======== DATOS PERSONALES ===============
    private LinearLayout llComentCli;
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
    //========== BENEFICIARIO =================
    private EditText txtNombreBeneficiario;
    private EditText txtApellidoPaterno;
    private EditText txtApellidoMaterno;
    private TextView txtParentescoBeneficiario;
    private LinearLayout llBeneficiario;
    private LinearLayout llDatosBeneficiario;
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

    private LinearLayout llFotoIneFrontal;
    private TextView tvIneFrontal;
    private ImageButton ibIneFrontal;
    private ImageView ivIneFrontal;
    public byte[] byteIneFrontal;

    private LinearLayout llFotoIneReverso;
    private TextView tvIneReverso;
    private ImageButton ibIneReverso;
    private ImageView ivIneReverso;
    public byte[] byteIneReverso;

    private LinearLayout llFotoIneSelfie;
    private TextView tvIneSelfie;
    private ImageButton ibIneSelfie;
    private ImageView ivIneSelfie;
    public byte[] byteIneSelfie;

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

    private TextView tvComprobanteGarantia;
    private ImageButton ibComprobanteGarantia;
    private ImageView ivComprobanteGarantia;
    public byte[] byteComprobanteGarantia;
    //========================================
    /**
     * ImageView para las fechas de las secciones para saber si el formulario esta visible u oculto
     */
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
    private ImageView ivDown11;
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
    private ImageView ivUp11;
    //========================================
    /**
     * ImageView para saber si en la seccion hay un datos faltante
     */
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
    /**
     * Contenedores para mostrar u ocultar las secciones
     */
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
    //private LinearLayout llCroquis;
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
    /**
     * Banderas para saber si ya fueron competadas las secciones para
     * posteriormente saber cuales fueron rechazadas por la ADMIN
     */
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
    boolean isEditBen = true;
    boolean hasFractionalPart = false;
    boolean pushMapButtonCli = false;
    boolean pushMapButtonAval = false;
    boolean pushMapButtonNeg = false;

    private final int MENU_INDEX_DEVMODE = 3;
    private final int MENU_INDEX_DESBLOQUEAR = 2;
    private final int MENU_INDEX_UPDATE_ESTATUS = 1;
    private final int MENU_INDEX_ENVIAR = 0;
    private boolean modoSuperUsuario = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_renovacion_credito_ind);

        ProgressDialog dialog = ProgressDialog.show(RenovacionCreditoInd.this, "",
                "Cargando la información por favor espere...", true);
        dialog.show();
        df.setDecimalSeparatorAlwaysShown(true);

        ctx = this;
        dBhelper = DBhelper.getInstance(ctx);
        db = dBhelper.getWritableDatabase();
        _dias_semana = getResources().getStringArray(R.array.dias_semana);
        myCalendar = Calendar.getInstance();
        validator = new Validator();
        validatorTV = new ValidatorTextView();
        TBmain = findViewById(R.id.TBmain);
        ClienteRen ren = new ClienteRen();
        cliente_id = ren.getIdCliente();


        /**Se cargan catalogos de tipo dialog*/
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
        _comportamiento = getResources().getStringArray(R.array.comportamiento_pago);
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
        tvCreditoAnterior = findViewById(R.id.tvCreditoAnterior);
        tvNumCiclo = findViewById(R.id.tvNumCiclo);
        tvComportamiento = findViewById(R.id.tvComportamientoPago);
        etObservaciones = findViewById(R.id.etObservaciones);
        etMontoRefinanciar = findViewById(R.id.etMontoRefinanciar);
        txtCampanaRen = findViewById(R.id.txtCampanaRen);
        txtNombreRefieroRen = findViewById(R.id.txtNombreRefieroRen);

        //==========================================================================================
        //=================================  DATOS PERSONALES  =====================================
        llComentCli = findViewById(R.id.llComentCli);
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
        //=================================  DATOS BENEFICIARIO  ===================================

        txtNombreBeneficiario = findViewById(R.id.txtNombreBeneficiario);
        txtApellidoPaterno = findViewById(R.id.txtApellidoPaternoBeneficiario);
        txtApellidoMaterno = findViewById(R.id.txtApellidoMaternoBeneficiario);
        txtParentescoBeneficiario = findViewById(R.id.txtParentezcoBeneficiario);

        //==========================================================================================
        //===================================  DATOS NEGOCIO  ======================================
        llComentNeg = findViewById(R.id.llComentNeg);
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
        llComentAval = findViewById(R.id.llComentAval);
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
        llComentRef = findViewById(R.id.llComentRef);
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
        llComentCro = findViewById(R.id.llComentCro);
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

        llFotoIneFrontal = findViewById(R.id.llineFrotalInd);
        llFotoIneReverso = findViewById(R.id.llineReversoInd);
        llFotoIneSelfie = findViewById(R.id.llineSelfieInd);

        tvIneFrontal = findViewById(R.id.tvIneFrontal);
        ibIneFrontal = findViewById(R.id.ibIneFrontal);
        ivIneFrontal = findViewById(R.id.ivIneFrontal);

        tvIneReverso = findViewById(R.id.tvIneReverso);
        ibIneReverso = findViewById(R.id.ibIneReverso);
        ivIneReverso = findViewById(R.id.ivIneReverso);


        llFotoCurp = findViewById(R.id.llFotoCurp);
        /*tvCurp              = findViewById(R.id.tvCurp);
        ibCurp              = findViewById(R.id.ibCurp);
        ivCurp              = findViewById(R.id.ivCurp);*/
        tvComprobante = findViewById(R.id.tvComprobante);
        ibComprobante = findViewById(R.id.ibComprobante);
        ivComprobante = findViewById(R.id.ivComprobante);
        tvFirmaAsesor = findViewById(R.id.tvFirmaAsesor);
        ibFirmaAsesor = findViewById(R.id.ibFirmaAsesor);
        ivFirmaAsesor = findViewById(R.id.ivFirmaAsesor);

        ivComprobanteGarantia = findViewById(R.id.ivComprobanteGarantia);
        ibComprobanteGarantia = findViewById(R.id.ibComprobanteGarantia);

        tvIneSelfie = findViewById(R.id.tvIneSelfie);
        ibIneSelfie = findViewById(R.id.ibIneSelfie);
        ivIneSelfie = findViewById(R.id.ivIneSelfie);
        tvComprobanteGarantia = findViewById(R.id.tvComprobanteGarantia);
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
        llDatosBeneficiario = findViewById(R.id.llDatosBeneficiario);

        llCredito = findViewById(R.id.llCredito);
        llPersonales = findViewById(R.id.llPersonales);
        llConyuge = findViewById(R.id.llConyuge);
        llPropiedades = findViewById(R.id.llPropiedades);
        llEconomicos = findViewById(R.id.llEconomicos);
        llNegocio = findViewById(R.id.llNegocio);
        llAval = findViewById(R.id.llAval);
        llReferencia = findViewById(R.id.llReferencia);
        //llCroquis = findViewById(R.id.llCroquis);
        llPoliticas = findViewById(R.id.llPoliticas);
        llDocumentos = findViewById(R.id.llDocumentos);
        llBeneficiario = findViewById(R.id.llBeneficiario);
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
        llBeneficiario.setOnClickListener(llBeneficiario_OnClick);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        //==========================================================================================
        /** LINEAR LAYOUTS QUE YA NO SE MUESTRAN*/

        mapCli.onCreate(savedInstanceState);
        mapNeg.onCreate(savedInstanceState);
        mapAval.onCreate(savedInstanceState);

        llFotoIneSelfie.setVisibility(View.VISIBLE);
        llFotoIneFrontal.setVisibility(View.VISIBLE);
        llFotoIneReverso.setVisibility(View.VISIBLE);

        llFotoCurp.setVisibility(View.GONE);


        Intent intent = this.getIntent();
        if (intent != null) {
            String solicitudId = intent.getStringExtra("id_solicitud");
            id_solicitud = Long.parseLong(solicitudId);
            initComponents(solicitudId);
        }

        //=================================  FLOATING BUTTON  ======================================
        btnContinuar0 = findViewById(R.id.btnContinuar0);
        btnContinuar1 = findViewById(R.id.btnContinuarDatosPersonalesInd);
        btnContinuar2 = findViewById(R.id.btnContinuarDatosConyugeInd);
        btnContinuarBeni = findViewById(R.id.btnContinuarBeneficiario);
        btnContinuar3 = findViewById(R.id.btnContinuarDatosEconomicosInd);
        btnContinuar4 = findViewById(R.id.btnContinuarDatosNegocioInd);
        btnContinuar5 = findViewById(R.id.btnContinuarDatosAvalInd);
        btnContinuar6 = findViewById(R.id.btnContinuarDatosRefSolInd);
        //btnContinuar7 = findViewById(R.id.btnContinuar7);
        btnContinuar8 = findViewById(R.id.btnContinuarPoliticasSolicitantes);


        btnRegresar1 = findViewById(R.id.btnRegresarDatosPersonalesInd);
        btnRegresar2 = findViewById(R.id.btnRegresarDatosConyugeInd);
        btnRegresarBeni = findViewById(R.id.btnRegresarBeneficiario);
        btnRegresar3 = findViewById(R.id.btnRegresarDatosEconomicosInd);
        btnRegresar4 = findViewById(R.id.btnRegresarDatosNegocioInd);
        btnRegresar5 = findViewById(R.id.btnRegresarDatosAvalInd);
        btnRegresar6 = findViewById(R.id.btnRegresarDatosRefSolInd);
        //btnRegresar7 = findViewById(R.id.btnRegresar7);
        btnRegresar8 = findViewById(R.id.btnRegresarPoliticiasSolicitantes);
        //btnRegresar9 = findViewById(R.id.btnRegresarDatosDocumentosInd);
        btnRegresar10 = findViewById(R.id.btnRegresarDatosDocumentosInd);
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

        ivDown11 = findViewById(R.id.ivDownBeni);


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

        ivUp11 = findViewById(R.id.ivUpBeni);


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

        //================================= FLOATING BUTTON LISTENER  ==============================
        /**Evento click para avanzar o retroceder en las secciones*/
        btnContinuar0.setOnClickListener(btnContinuar0_OnClick);
        btnContinuar1.setOnClickListener(btnContinuar1_OnClick);
        btnContinuar2.setOnClickListener(btnContinuar2_OnClick);
        btnContinuarBeni.setOnClickListener(btnContinuarBeni_OnClick);
        btnContinuar3.setOnClickListener(btnContinuar3_OnClick);
        btnContinuar4.setOnClickListener(btnContinuar4_OnClick);
        btnContinuar5.setOnClickListener(btnContinuar5_OnClick);
        btnContinuar6.setOnClickListener(btnContinuar6_OnClick);
        //btnContinuar7.setOnClickListener(btnContinuar7_OnClick);
        btnContinuar8.setOnClickListener(btnContinuar8_OnClick);


        btnRegresar1.setOnClickListener(btnRegresar1_OnClick);
        btnRegresar2.setOnClickListener(btnRegresar2_OnClick);
        btnRegresarBeni.setOnClickListener(btnRegresarBeni_OnClick);
        btnRegresar3.setOnClickListener(btnRegresar3_OnClick);
        btnRegresar4.setOnClickListener(btnRegresar4_OnClick);
        btnRegresar5.setOnClickListener(btnRegresar5_OnClick);
        btnRegresar6.setOnClickListener(btnRegresar6_OnClick);
        //btnRegresar7.setOnClickListener(btnRegresar7_OnClick);
        btnRegresar8.setOnClickListener(btnRegresar8_OnClick);
        btnRegresar10.setOnClickListener(btnRegresar10_OnClick);

        dialog.dismiss();
        //==========================================================================================
        //================================  CREDITO LISTENER =======================================
        /**Evento click y escuchadores(editText o textView) en la seccion del credito para cambios al momento
         * en los escuchas de .addTextChangedListener se van actualizando las columnas al momento de hacer algun cambio*/
        tvPlazo.setOnClickListener(tvPlazo_OnClick);
        tvFrecuencia.setOnClickListener(tvFrecuencia_OnClick);
        tvFechaDesembolso.setOnClickListener(tvFechaDesembolso_OnClick);
        tvHoraVisita.setOnClickListener(tvHoraVisita_OnClick);
        ibFirmaAsesor.setOnClickListener(ibFirmaAsesor_OnClick);
        etMontoPrestamo.addTextChangedListener(new TextWatcher() {
            private final String PATTERN_MONTO_CREDITO = "[1-9][0-9][0-9][0][0][0]|[1-9][0-9][0][0][0]|[1-9][0][0][0]";
            private Pattern pattern;
            private Matcher matcher;

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
                } catch (NumberFormatException nfe) {
                    // do nothing?
                } catch (ParseException e) {
                    // do nothing?
                }

                if (s.length() > 0) {
                    pattern = Pattern.compile(PATTERN_MONTO_CREDITO);
                    matcher = pattern.matcher(s.toString().replace(",", ""));
                    if (!matcher.matches()) {
                        tvCantidadLetra.setText("");
                        etMontoPrestamo.setError("La cantidad no corresponde a un monto de crédito válido");
                    } else {
                        Update("monto_prestamo", TBL_CREDITO_IND_REN, s.toString().trim().replace(",", ""));
                        tvCantidadLetra.setText((Miscellaneous.cantidadLetra(s.toString().replace(",", "")).toUpperCase() + " PESOS MEXICANOS ").replace("  ", " "));
                        if (Integer.parseInt(s.toString().replace(",", "")) > 30000) {
                            llPropiedades.setVisibility(View.VISIBLE);
                        } else {
                            llPropiedades.setVisibility(View.GONE);
                        }
                    }
                } else {
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
                if (e.length() > 0) {
                    Update("observaciones", TBL_CREDITO_IND_REN, e.toString().trim().toUpperCase());
                } else
                    Update("observaciones", TBL_CREDITO_IND_REN, "");
            }
        });
        etMontoRefinanciar.addTextChangedListener(new TextWatcher() {
            private final String PATTERN_MONTO_CREDITO = "[0-9]+";
            private Pattern pattern;
            private Matcher matcher;

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
                } catch (NumberFormatException nfe) {
                    // do nothing?
                } catch (ParseException e) {
                    // do nothing?
                }

                if (s.length() > 0) {
                    pattern = Pattern.compile(PATTERN_MONTO_CREDITO);
                    matcher = pattern.matcher(s.toString().replace(",", ""));
                    if (!matcher.matches()) {
                        etMontoRefinanciar.setError("La cantidad no corresponde a un monto válido");
                    } else {
                        Update("monto_refinanciar", TBL_CREDITO_IND_REN, s.toString().trim().replace(",", ""));
                    }
                }

                etMontoRefinanciar.addTextChangedListener(this);
            }
        });
        txtCampanaRen.setOnClickListener(txtCamapana_OnClick);
        txtNombreRefieroRen.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() > 0) {
                    Update("nombre_refiero", TBL_DATOS_CREDITO_CAMPANA, editable.toString().trim().toUpperCase());
                } else {
                    Update("nombre_refiero", TBL_DATOS_CREDITO_CAMPANA, " ");
                }
            }

        });
        //==============================  PERSONALES LISTENER ======================================
        /**Evento click y escuchadores(editText o textView) en la seccion de datos personales para cambios al momento
         * en los escuchas de .addTextChangedListener se van actualizando las columnas al momento de hacer algun cambio*/
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
                        Update("rfc", TBL_CLIENTE_IND_REN, "");
                        Update("curp", TBL_CLIENTE_IND_REN, "");
                    } else {
                        Update("curp", TBL_CLIENTE_IND_REN, s.toString().trim().toUpperCase());
                        if (s.toString().trim().length() >= 10) {
                            tvRfcCli.setText(Miscellaneous.GenerarRFC(s.toString().substring(0, 10), Miscellaneous.GetStr(etNombreCli), Miscellaneous.GetStr(etApPaternoCli), Miscellaneous.GetStr(etApMaternoCli)));
                            Update("rfc", TBL_CLIENTE_IND_REN, Miscellaneous.GetStr(tvRfcCli));
                        } else {
                            tvRfcCli.setText("");
                            Update("rfc", TBL_CLIENTE_IND_REN, Miscellaneous.GetStr(tvRfcCli));
                        }

                    }
                } else {
                    tvRfcCli.setText("Rfc no válida");
                    Update("rfc", TBL_CLIENTE_IND_REN, "");
                    Update("curp", TBL_CLIENTE_IND_REN, "");
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
                    Update("no_identificacion", TBL_CLIENTE_IND_REN, e.toString());
                } else
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
                if (e.length() > 0) {
                    Update("otro_tipo_vivienda", TBL_CLIENTE_IND_REN, e.toString());
                } else
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
                        Update("tel_casa", TBL_CLIENTE_IND_REN, Miscellaneous.GetStr(etTelCasaCli));
                    } else {
                        Update("tel_casa", TBL_CLIENTE_IND_REN, "");
                        etTelCasaCli.setError(ctx.getResources().getString(R.string.mensaje_telefono));
                    }
                } else {
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
                if (e.length() > 0) {
                    if (e.length() == 10) {
                        etCelularCli.setError(null);
                        Update("tel_celular", TBL_CLIENTE_IND_REN, e.toString());
                    } else {
                        Update("tel_celular", TBL_CLIENTE_IND_REN, "");
                        etCelularCli.setError(ctx.getResources().getString(R.string.mensaje_telefono));
                    }
                } else {
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
                if (e.length() > 0) {
                    if (e.length() == 10) {
                        etTelMensCli.setError(null);
                        Update("tel_mensajes", TBL_CLIENTE_IND_REN, e.toString());
                    } else {
                        Update("tel_mensajes", TBL_CLIENTE_IND_REN, "");
                        etTelMensCli.setError(ctx.getResources().getString(R.string.mensaje_telefono));
                    }
                } else {
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
                if (e.length() > 0) {
                    if (e.length() == 10) {
                        etTelTrabajoCli.setError(null);
                        Update("tel_trabajo", TBL_CLIENTE_IND_REN, e.toString());
                    } else {
                        Update("tel_trabajo", TBL_CLIENTE_IND_REN, "");
                        etTelTrabajoCli.setError(ctx.getResources().getString(R.string.mensaje_telefono));
                    }
                } else {
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
                if (!validator.validate(etTiempoSitio, new String[]{validator.REQUIRED, validator.ONLY_NUMBER, validator.YEARS})) {
                    Update("tiempo_vivir_sitio", TBL_CLIENTE_IND_REN, e.toString());
                } else {
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
                if (!validator.validate(etEmail, new String[]{validator.EMAIL})) {
                    Update("email", TBL_CLIENTE_IND_REN, e.toString());
                } else {
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
        /**Evento click y escuchadores(editText o textView) en la seccion del conyuge para cambios al momento
         * en los escuchas de .addTextChangedListener se van actualizando las columnas al momento de hacer algun cambio*/
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
                if (e.length() > 0) {
                    Update("materno", TBL_CONYUGE_IND_REN, e.toString());
                } else
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
                if (e.length() > 0) {
                    Update("nacionalidad", TBL_CONYUGE_IND_REN, e.toString());
                } else
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
                    Update("ing_mensual", TBL_CONYUGE_IND_REN, e.toString().replace(",", ""));
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
                if (s.length() > 0) {
                    if (s.length() == 10) {
                        Update("tel_casa", TBL_CONYUGE_IND_REN, s.toString());
                        etCasaCony.setError(null);
                    } else {
                        Update("tel_casa", TBL_CONYUGE_IND_REN, "");
                        etCasaCony.setError(ctx.getResources().getString(R.string.mensaje_telefono));
                    }
                } else {
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
                if (s.length() > 0) {
                    if (s.length() == 10) {
                        etCelularCony.setError(null);
                        Update("tel_celular", TBL_CONYUGE_IND_REN, s.toString());
                    } else {
                        Update("tel_celular", TBL_CONYUGE_IND_REN, "");
                        etCelularCony.setError(ctx.getResources().getString(R.string.mensaje_telefono));
                    }
                } else {
                    Update("tel_celular", TBL_CONYUGE_IND_REN, "");
                    etCelularCony.setError(null);
                }
            }
        });
        //===============================  ECONOMICOS LISTENER  ====================================
        /**Evento click y escuchadores(editText o textView) en la seccion de datos economicos para cambios al momento
         * en los escuchas de .addTextChangedListener se van actualizando las columnas al momento de hacer algun cambio*/
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
        //==============================  BENEFICIARIO LISTENER ======================================
        txtNombreBeneficiario.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {


            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() > 0) {
                    Update("nombre", TBL_DATOS_BENEFICIARIO_REN, editable.toString().trim().toUpperCase());
                } else
                    Update("nombre", TBL_DATOS_BENEFICIARIO_REN, " ");
            }
        });
        txtApellidoPaterno.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() > 0) {
                    Update("paterno", TBL_DATOS_BENEFICIARIO_REN, editable.toString().trim().toUpperCase());
                } else
                    Update("paterno", TBL_DATOS_BENEFICIARIO_REN, " ");
            }
        });
        txtApellidoMaterno.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() > 0) {
                    Update("materno", TBL_DATOS_BENEFICIARIO_REN, editable.toString().trim().toUpperCase());
                } else
                    Update("materno", TBL_DATOS_BENEFICIARIO_REN, " ");
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
                    Update("parentesco", TBL_DATOS_BENEFICIARIO_REN, editable.toString().trim().toUpperCase());
                } else
                    Update("parentesco", TBL_DATOS_BENEFICIARIO_REN, " ");
            }
        });
        txtParentescoBeneficiario.setOnClickListener(txtParentescoBen_OnClick);
        //==================================  NEGOCIO LISTENER  ====================================
        /**Evento click y escuchadores(editText o textView) en la seccion del negocio para cambios al momento
         * en los escuchas de .addTextChangedListener se van actualizando las columnas al momento de hacer algun cambio*/
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
        tvColoniaNeg.setOnClickListener(etColoniaNeg_OnClick);
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
                if (e.length() > 0) {
                    if (Integer.parseInt(Miscellaneous.GetStr(etAntiguedadNeg)) > 0) {
                        Update("antiguedad", TBL_NEGOCIO_IND_REN, e.toString());
                    } else {
                        Update("antiguedad", TBL_NEGOCIO_IND_REN, "0");
                        etAntiguedadNeg.setError("No se permiten cantidades iguales a cero");
                    }
                } else
                    Update("antiguedad", TBL_NEGOCIO_IND_REN, "0");
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
                    Update("ing_mensual", TBL_NEGOCIO_IND_REN, e.toString().replace(",", ""));
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
                    Update("ing_otros", TBL_NEGOCIO_IND_REN, e.toString().replace(",", ""));
                else
                    Update("ing_otros", TBL_NEGOCIO_IND_REN, "");
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
                    Update("gasto_semanal", TBL_NEGOCIO_IND_REN, e.toString().replace(",", ""));
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
                    Update("gasto_agua", TBL_NEGOCIO_IND_REN, e.toString().replace(",", ""));
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
                    Update("gasto_luz", TBL_NEGOCIO_IND_REN, e.toString().replace(",", ""));
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
                    Update("gasto_telefono", TBL_NEGOCIO_IND_REN, e.toString().replace(",", ""));
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
                    Update("gasto_renta", TBL_NEGOCIO_IND_REN, e.toString().replace(",", ""));
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
                    Update("gasto_otros", TBL_NEGOCIO_IND_REN, e.toString().replace(",", ""));
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
                                    Update("capacidad_pago", TBL_NEGOCIO_IND_REN, e.toString().trim().replace(",", ""));
                                else
                                    ShowMensajes("EL monto no puede superar a la capacidad de pago", "NEGOCIO");

                        } catch (NumberFormatException exception) {
                            Update("capacidad_pago", TBL_NEGOCIO_IND_REN, "");
                        }

                    else
                        Update("capacidad_pago", TBL_NEGOCIO_IND_REN, "");
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
                            Update("num_operacion_efectivo", TBL_NEGOCIO_IND_REN, e.toString().trim().toUpperCase());

                    } catch (NumberFormatException exception) {
                        Update("num_operacion_efectivo", TBL_NEGOCIO_IND_REN, "0");
                    }

                else
                    Update("num_operacion_efectivo", TBL_NEGOCIO_IND_REN, "0");
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
                    Update("ref_domiciliaria", TBL_NEGOCIO_IND_REN, e.toString());
                else
                    Update("ref_domiciliaria", TBL_NEGOCIO_IND_REN, "");
            }
        });
        cbNegEnDomCli.setOnClickListener(cbNegEnDomCli_OnCheck);
        //====================================  AVAL LISTENER  =====================================
        /**Evento click y escuchadores(editText o textView) en la seccion del aval para cambios al momento
         * en los escuchas de .addTextChangedListener se van actualizando las columnas al momento de hacer algun cambio*/
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
                    Update("nombre", TBL_AVAL_IND_REN, e.toString().trim().toUpperCase());
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
                    Update("nombre", TBL_AVAL_IND_REN, "");
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
                    Update("paterno", TBL_AVAL_IND_REN, e.toString().toUpperCase());
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
                    Update("paterno", TBL_AVAL_IND_REN, "");
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
                    Update("materno", TBL_AVAL_IND_REN, e.toString());
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
                        params.put(5, Miscellaneous.GetStr(tvEstadoNacAval));
                    else
                        params.put(5, "");
                    etCurpAval.setText(Miscellaneous.GenerarCurp(params));
                } else {
                    Update("materno", TBL_AVAL_IND_REN, "");
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
                        Update("rfc", TBL_AVAL_IND_REN, "");
                        Update("curp", TBL_AVAL_IND_REN, "");
                    } else {
                        Update("curp", TBL_AVAL_IND_REN, e.toString().trim().toUpperCase());
                        if (e.toString().length() >= 10) {
                            tvRfcAval.setText(Miscellaneous.GenerarRFC(e.toString().substring(0, 10), Miscellaneous.GetStr(etNombreAval), Miscellaneous.GetStr(etApPaternoAval), Miscellaneous.GetStr(etApMaternoAval)));

                            Update("rfc", TBL_AVAL_IND_REN, Miscellaneous.GetStr(tvRfcAval));
                        } else {
                            tvRfcAval.setText("");

                            Update("rfc", TBL_AVAL_IND_REN, Miscellaneous.GetStr(tvRfcAval));
                        }

                    }

                } else {
                    Update("rfc", TBL_AVAL_IND_REN, "");
                    Update("curp", TBL_AVAL_IND_REN, "");
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
                if (e.length() > 0) {
                    if (Integer.parseInt(e.toString().trim()) > 0)
                        Update("antigueda", TBL_AVAL_IND_REN, e.toString().trim());
                    else
                        Update("antigueda", TBL_AVAL_IND_REN, "0");
                } else
                    Update("antigueda", TBL_AVAL_IND_REN, "0");
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
                    Update("ing_mensual", TBL_AVAL_IND_REN, e.toString().replace(",", ""));
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
                    Update("ing_otros", TBL_AVAL_IND_REN, e.toString().replace(",", ""));
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
                    Update("gasto_semanal", TBL_AVAL_IND_REN, e.toString().trim().replace(",", ""));
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
                    Update("gasto_agua", TBL_AVAL_IND_REN, e.toString().trim().replace(",", ""));
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
                    Update("gasto_luz", TBL_AVAL_IND_REN, e.toString().trim().replace(",", ""));
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
                    Update("gasto_telefono", TBL_AVAL_IND_REN, e.toString().trim().replace(",", ""));
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
                    Update("gasto_renta", TBL_AVAL_IND_REN, e.toString().trim().replace(",", ""));
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
                    Update("gasto_otros", TBL_AVAL_IND_REN, e.toString().trim().replace(",", ""));
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
                                    Update("capacidad_pagos", TBL_AVAL_IND_REN, e.toString().trim().replace(",", ""));
                                else
                                    ShowMensajes("EL monto no puede superar a la capacidad de pago", "AVAL");

                        } catch (NumberFormatException exception) {
                            Update("capacidad_pagos", TBL_AVAL_IND_REN, "");
                        }

                    else
                        Update("capacidad_pagos", TBL_AVAL_IND_REN, "");
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
                        Update("tel_casa", TBL_AVAL_IND_REN, e.toString().trim());
                    } else {
                        Update("tel_casa", TBL_AVAL_IND_REN, "");
                        etTelCasaAval.setError(ctx.getResources().getString(R.string.mensaje_telefono));
                    }
                } else {
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
                if (e.length() > 0) {
                    if (e.length() == 10) {
                        etCelularAval.setError(null);
                        Update("tel_celular", TBL_AVAL_IND_REN, e.toString().trim());
                    } else {
                        Update("tel_casa", TBL_AVAL_IND_REN, "");
                        etCelularAval.setError(ctx.getResources().getString(R.string.mensaje_telefono));
                    }
                } else {
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
                if (e.length() > 0) {
                    if (e.length() == 10) {
                        etTelMensAval.setError(null);
                        Update("tel_mensajes", TBL_AVAL_IND_REN, e.toString());
                    } else {
                        Update("tel_mensajes", TBL_AVAL_IND_REN, "");
                        etTelMensAval.setError(ctx.getResources().getString(R.string.mensaje_telefono));
                    }
                } else {
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
                if (e.length() > 0) {
                    if (e.length() == 10) {
                        etTelTrabajoAval.setError(null);
                        Update("tel_trabajo", TBL_AVAL_IND_REN, e.toString());
                    } else {
                        Update("tel_trabajo", TBL_AVAL_IND_REN, "");
                        etTelTrabajoAval.setError(ctx.getResources().getString(R.string.mensaje_telefono));
                    }
                } else {
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
                if (!validator.validate(etEmail, new String[]{validator.EMAIL})) {
                    Update("email", TBL_AVAL_IND_REN, e.toString());
                } else {
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
        /**Evento click y escuchadores(editText o textView) en la seccion de la referencia para cambios al momento
         * en los escuchas de .addTextChangedListener se van actualizando las columnas al momento de hacer algun cambio*/
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
                        Update("tel_celular", TBL_REFERENCIA_IND_REN, e.toString().trim());
                    } else {
                        Update("tel_celular", TBL_REFERENCIA_IND_REN, "");
                        etTelCelRef.setError(ctx.getResources().getString(R.string.mensaje_telefono));
                    }
                } else {
                    Update("tel_celular", TBL_REFERENCIA_IND_REN, "");
                    etTelCelRef.setError(null);
                }
            }
        });
        //============== CROQUIS ==================================
        /**Evento click y escuchadores(editText o textView) en la seccion del croquis para cambios al momento
         * en los escuchas de .addTextChangedListener se van actualizando las columnas al momento de hacer algun cambio*/
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
                    Update("referencias", TBL_CROQUIS_IND_REN, e.toString().trim().toUpperCase());
                else
                    Update("referencias", TBL_CROQUIS_IND_REN, "");
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
                    Update("caracteristicas_domicilio", TBL_CROQUIS_IND_REN, e.toString().trim().toUpperCase());
                else
                    Update("caracteristicas_domicilio", TBL_CROQUIS_IND_REN, "");
            }
        });
        //================================  ESCANEAR DOCUMENTOS  ===================================
        /**Evento click en la seccion de documentos para cambios al momento*/
        ibIneFrontal.setOnClickListener(ibIneFrontal_OnClick);
        ibIneReverso.setOnClickListener(ibIneReverso_OnClick);
        ibIneSelfie.setOnClickListener(ibIneSelfie_OnClick);

        //ibCurp.setOnClickListener(ibCurp_OnClick);
        ibComprobante.setOnClickListener(ibComprobante_OnClick);
        ibComprobanteGarantia.setOnClickListener(ibComprobanteGarantia_OnClick);
        ibIneFrontalAval.setOnClickListener(ibIneFrontalAval_OnClick);
        ibIneReversoAval.setOnClickListener(ibIneReversoAval_OnClick);
        ibCurpAval.setOnClickListener(ibCurpAval_OnClick);
        ibComprobanteAval.setOnClickListener(ibComprobanteAval_OnClick);
        //================================  CLIENTE GENERO LISTENER  ===============================
        rgGeneroCli.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (isEditCli) {
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
                        Update("genero", TBL_CLIENTE_IND_REN, "0");
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
                        Update("genero", TBL_CLIENTE_IND_REN, "1");
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
                    if (checkedId == R.id.rbMancomunados) {
                        Update("bienes", TBL_CLIENTE_IND_REN, "1");
                    } else if (checkedId == R.id.rbSeparados) {
                        Update("bienes", TBL_CLIENTE_IND_REN, "2");
                    } else {
                        throw new IllegalStateException("Unexpected value: " + checkedId);
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
                        params.put(0, Miscellaneous.GetStr(etNombreAval));
                        params.put(1, Miscellaneous.GetStr(etApPaternoAval));
                        params.put(2, Miscellaneous.GetStr(etApMaternoAval));
                        params.put(3, Miscellaneous.GetStr(tvFechaNacAval));
                        params.put(4, "Hombre");

                        if (!Miscellaneous.GetStr(tvEstadoNacAval).isEmpty())
                            params.put(5, Miscellaneous.GetStr(tvEstadoNacAval));
                        else
                            params.put(5, "");
                        Update("genero", TBL_AVAL_IND_REN, "0");
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

                        Update("genero", TBL_AVAL_IND_REN, "1");
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
                    if (checkedId == R.id.rbSiNeg) {
                        llNombreNegocio.setVisibility(View.VISIBLE);
                        etNombreNegocioAval.setText("");
                        Update("tiene_negocio", TBL_AVAL_IND_REN, "1");
                        Update("nombre_negocio", TBL_AVAL_IND_REN, "");
                    } else if (checkedId == R.id.rbNoNeg) {
                        llNombreNegocio.setVisibility(View.GONE);
                        Update("tiene_negocio", TBL_AVAL_IND_REN, "2");
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
                    if (checkedId == R.id.rbSiPropietario) {
                        tvAnexoPropietario.setVisibility(View.VISIBLE);
                        Update("propietario_real", TBL_POLITICAS_PLD_IND_REN, "1");
                    } else if (checkedId == R.id.rbNoPropietario) {
                        tvAnexoPropietario.setVisibility(View.GONE);
                        Update("propietario_real", TBL_POLITICAS_PLD_IND_REN, "2");
                    }
                }
            }
        });
        rgProveedor.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (isEditPol) {
                    tvProvedor.setError(null);
                    if (checkedId == R.id.rbSiProveedor) {
                        tvAnexoPreveedor.setVisibility(View.VISIBLE);
                        Update("proveedor_recursos", TBL_POLITICAS_PLD_IND_REN, "1");
                    } else if (checkedId == R.id.rbNoProveedor) {
                        tvAnexoPreveedor.setVisibility(View.GONE);
                        Update("proveedor_recursos", TBL_POLITICAS_PLD_IND_REN, "2");
                    }
                }
            }
        });
        rgPoliticamenteExp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (isEditPol) {
                    tvPoliticamenteExp.setError(null);
                    if (checkedId == R.id.rbSiExpuesta) {
                        tvAnexoPoliticamenteExp.setVisibility(View.VISIBLE);
                        Update("persona_politica", TBL_POLITICAS_PLD_IND_REN, "1");
                    } else if (checkedId == R.id.rbNoexpuesta) {
                        tvAnexoPoliticamenteExp.setVisibility(View.GONE);
                        Update("persona_politica", TBL_POLITICAS_PLD_IND_REN, "2");
                    }
                }
            }
        });

        /**Evento de click para capturar fotografias o firmas digitales*/
        ivFotoFachCli.setOnClickListener(ivFotoFachCli_OnClick);
        ivFirmaCli.setOnClickListener(ivFirmaCli_OnClick);
        ivFotoFachNeg.setOnClickListener(ivFotoFachNeg_OnClick);
        ivFotoFachAval.setOnClickListener(ivFotoFachAval_OnClick);
        ivFirmaAval.setOnClickListener(ivFirmaAval_OnClick);

        ivIneFrontal.setOnClickListener(ivIneFrontal_OnClik);
        ivIneReverso.setOnClickListener(ivIneReverso_OnClick);
        ivIneSelfie.setOnClickListener(ivIneSelfie_OnClik);

        //ivCurp.setOnClickListener(ivCurp_OnClick);
        ivComprobante.setOnClickListener(ivComprobante_OnClick);
        ivFirmaAsesor.setOnClickListener(ivFirmaAsesor_OnClick);

        ivComprobanteGarantia.setOnClickListener(ivComprobanteGarantia_OnClick);
        ivIneFrontalAval.setOnClickListener(ivIneFrontalAval_OnClik);
        ivIneReversoAval.setOnClickListener(ivIneReversoAval_OnClick);
        ivCurpAval.setOnClickListener(ivCurpAval_OnClick);
        ;
        ivComprobanteAval.setOnClickListener(ivComprobanteAval_OnClick);

        tvRiesgo.setText(_riesgo[2]);
        Update("clasificacion_riesgo", TBL_CREDITO_IND, Miscellaneous.GetStr(tvRiesgo));
        tvDestinoNeg.setText("RE-INVERSION");
        Update("destino_credito", TBL_NEGOCIO_IND, _destino_credito[0]);

        tvDestino.setText(_destino[1]);
        Update("destino", TBL_CREDITO_IND, Miscellaneous.GetStr(tvDestino));

        if (tvPlazo.getText().equals("")) {
            dialog_plazo_ind dialogPlazoInd = new dialog_plazo_ind();
            dialogPlazoInd.setCancelable(false);
            Bundle b = new Bundle();

            dialogPlazoInd.setArguments(b);
            dialogPlazoInd.show(getSupportFragmentManager(), NameFragments.DIALOGPLAZOIND);
        }

        if (etCpNeg.getText() != null && Miscellaneous.GetStr(etCpNeg).equals("") == false) {
            Cursor rowDireccion = dBhelper.getDireccionByCP(etCpNeg.getText().toString());
            if (rowDireccion.getCount() > 0) {
                rowDireccion.moveToFirst();

                if (rowDireccion.getCount() == 1) {
                    UpdateDireccion("estado", rowDireccion.getString(1), direccionIdNeg, "NEGOCIO");
                    tvEstadoNeg.setText(rowDireccion.getString(1));
                }
            }
            rowDireccion.close();
        }
    }

    @Override
    public void onBackPressed() {
        if (is_edit) {
            AlertDialog solicitud = Popups.showDialogConfirm(this, warning,
                    R.string.guardar_cambios, R.string.yes, dialog -> {
                        finish();
                        dialog.dismiss();
                    }, R.string.cancel, dialog -> dialog.dismiss());
            solicitud.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            solicitud.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            solicitud.setCancelable(true);
            solicitud.show();
        } else {
            finish();
        }
        saveBeneficiario();
    }


    /**
     * Evento para obtener la firma del asesor
     */
    private View.OnClickListener ibFirmaAsesor_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i_firma_asesor = new Intent(ctx, CapturarFirma.class);
            i_firma_asesor.putExtra(TIPO, "");
            startActivityForResult(i_firma_asesor, REQUEST_CODE_FIRMA_ASESOR);
        }
    };

    /**
     * Evento para seleccionar el plazo del prestamo
     */
    private View.OnClickListener tvPlazo_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (isEditCre) {/**Valida si el estatus es para editar*/
                /**Selector de tipo dialogo para el plazo del prestamo*/
                AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
                builder.setTitle(R.string.selected_option)
                        .setItems(R.array.intervalo, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int position) {
                                tvPlazo.setError(null);
                                tvPlazo.setText(_plazo[position]);
                                /**Actualiza la columa del plazo*/
                                Update("plazo", TBL_CREDITO_IND_REN, Miscellaneous.GetStr(tvPlazo));
                            }
                        });
                builder.create();
                builder.show();
            }
        }
    };

    /**
     * Evento para seleccionar la frecuencia de pago del prestamo
     */
    private View.OnClickListener tvFrecuencia_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (isEditCre) {/**Valida el estatus para saber si puede seleccionar el plazo*/
                /**Selector de tipo dialogo par la frecuencia de pago*/
                AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
                builder.setTitle(R.string.selected_option)
                        .setItems(R.array.lapso, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int position) {
                                tvFrecuencia.setError(null);
                                tvFrecuencia.setText(_frecuencia[position]);
                                /**Actualiza en la columna del periodicidad*/
                                Update("periodicidad", TBL_CREDITO_IND_REN, Miscellaneous.GetStr(tvFrecuencia));
                                /**Valida la frecuencia de pago para definir el numero de operaciones en efectivo  y actualiza la columna*/
                                switch (position) {
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

    /**
     * Evento para seleccionar la fecha desembolso
     */
    private View.OnClickListener tvFechaDesembolso_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (isEditCre) {
                dialog_date_picker dialogDatePicker = new dialog_date_picker();
                Bundle b = new Bundle();

                b.putInt(YEAR_CURRENT, ((Miscellaneous.GetStr(tvFechaDesembolso).isEmpty()) ? myCalendar.get(Calendar.YEAR) : Integer.parseInt(Miscellaneous.GetStr(tvFechaDesembolso).substring(0, 4))));
                b.putInt(MONTH_CURRENT, ((Miscellaneous.GetStr(tvFechaDesembolso).isEmpty()) ? myCalendar.get(Calendar.MONTH) : (Integer.parseInt(Miscellaneous.GetStr(tvFechaDesembolso).substring(5, 7)) - 1)));
                b.putInt(DAY_CURRENT, ((Miscellaneous.GetStr(tvFechaDesembolso).isEmpty()) ? myCalendar.get(Calendar.DAY_OF_MONTH) : Integer.parseInt(Miscellaneous.GetStr(tvFechaDesembolso).substring(8, 10))));
                b.putString(DATE_CURRENT, sdf.format(myCalendar.getTime()));
                b.putInt(IDENTIFIER, 13);
                b.putBoolean(FECHAS_POST, true);
                dialogDatePicker.setArguments(b);
                dialogDatePicker.show(getSupportFragmentManager(), NameFragments.DIALOGDATEPICKER);
            }
        }
    };

    /**
     * Evento para seleccionar el destiono del credito
     */
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
                                Update("destino", TBL_CREDITO_IND_REN, Miscellaneous.GetStr(tvDestino));
                            }
                        });
                builder.create();
                builder.show();
            }
        }
    };

    /**
     * Evento para seleccionar el nivel de riesgo
     */
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
                                Update("clasificacion_riesgo", TBL_CREDITO_IND_REN, Miscellaneous.GetStr(tvRiesgo));
                            }
                        });
                builder.create();
                builder.show();
            }
        }
    };

    /**
     * Evento para seleccionar el comportamiento del cliente en sus pagos
     */
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
                                Update("comportamiento_pago", TBL_CREDITO_IND_REN, Miscellaneous.GetStr(tvComportamiento));
                            }
                        });
                builder.create();
                builder.show();
            }
        }
    };

    /**
     * Evento para seleccionar la fecha de nacimiento pero como es renovacion ya se tiene la fecha y no puede editarla
     */
    private View.OnClickListener tvFechaNac_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (isEditCli) {
                dialog_date_picker dialogDatePicker = new dialog_date_picker();
                Bundle b = new Bundle();

               /* b.putInt(YEAR_CURRENT, ((Miscellaneous.GetStr(tvFechaNacCli).isEmpty()) ? myCalendar.get(Calendar.YEAR) : Integer.parseInt(Miscellaneous.GetStr(tvFechaNacCli).substring(0, 4))));
                b.putInt(MONTH_CURRENT, ((Miscellaneous.GetStr(tvFechaNacCli).isEmpty()) ? myCalendar.get(Calendar.MONTH) : (Integer.parseInt(Miscellaneous.GetStr(tvFechaNacCli).substring(5, 7)) - 1)));
                b.putInt(DAY_CURRENT, ((Miscellaneous.GetStr(tvFechaNacCli).isEmpty()) ? myCalendar.get(Calendar.DAY_OF_MONTH) : Integer.parseInt(Miscellaneous.GetStr(tvFechaNacCli).substring(8, 10))));
                b.putString(DATE_CURRENT, sdf.format(myCalendar.getTime()));
                b.putInt(IDENTIFIER, 2);
                b.putBoolean(FECHAS_POST, false);
                dialogDatePicker.setArguments(b);
                dialogDatePicker.show(getSupportFragmentManager(), NameFragments.DIALOGDATEPICKER);*/

                int dato5 = 5;

                date = new DialogSelectorFecha(RenovacionCreditoInd.this, tvFechaNacCli, dato5, indRen);

                String fechaMostrada = tvFechaNacCli.getText().toString().trim();
                if (!fechaMostrada.isEmpty()) {
                    date.recuperarFecha(fechaMostrada);
                } else {
                    date.showDatePickerDialog();
                }

            }
        }
    };

    /**
     * Evento para capturar la firma de cliente
     */
    private View.OnClickListener ibFirmaCli_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i_firma_cli = new Intent(ctx, CapturarFirma.class);
            i_firma_cli.putExtra(TIPO, "CLIENTE");
            startActivityForResult(i_firma_cli, REQUEST_CODE_FIRMA_CLI);
        }
    };

    /**
     * Evento para obtener la ubicacion del cliente
     */
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

    /**
     * Evento para capturar la fotografia de fachada del cliente
     */
    private View.OnClickListener ibFotoFachCli_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i = new Intent(RenovacionCreditoInd.this, CameraVertical.class);
            i.putExtra(ORDER_ID, "SC_cliente");
            startActivityForResult(i, REQUEST_CODE_CAMARA_FACHADA_CLI);
        }
    };

    /**
     * Evento para capturar el estado de nacimiento pero como ya se obtiene el dato no puede cambiar el dato
     */
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

    /**
     * Evento para seleccionar la colonia donode vive el cliente
     */
    private View.OnClickListener etColonia_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (isEditCli || tvColoniaCli.getText().toString().trim().isEmpty()) {
                Intent i_colonia = new Intent(ctx, Catalogos.class);
                i_colonia.putExtra(TITULO, Miscellaneous.ucFirst(COLONIA + "s"));
                i_colonia.putExtra(CATALOGO, COLONIAS);
                i_colonia.putExtra(EXTRA, Miscellaneous.GetStr(etCpCli));
                i_colonia.putExtra(REQUEST_CODE, REQUEST_CODE_COLONIA_CLIE);
                startActivityForResult(i_colonia, REQUEST_CODE_COLONIA_CLIE);
            }
        }
    };

    /**
     * Evento para seleccionar la localidad donde vive el cliente
     */
    private View.OnClickListener tvLocalidadCli_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (isEditCli || tvLocalidadCli.getText().toString().trim().isEmpty()) {
                ColoniaDao coloniaDao = new ColoniaDao(ctx);
                List<Colonia> colonias = coloniaDao.findAllByCp(etCpCli.getText().toString().trim());
                ;

                if (!Miscellaneous.GetStr(tvMunicipioCli).isEmpty() && colonias.size() > 0) {
                    Cursor row = dBhelper.getRecords(TABLE_MUNICIPIOS, " WHERE municipio_id = ?", "", new String[]{String.valueOf(colonias.get(0).getMunicipioId())});
                    row.moveToFirst();
                    Intent i_localidad = new Intent(ctx, Catalogos.class);
                    i_localidad.putExtra(TITULO, Miscellaneous.ucFirst(LOCALIDADES));
                    i_localidad.putExtra(CATALOGO, LOCALIDADES);
                    i_localidad.putExtra(EXTRA, row.getString(1));
                    i_localidad.putExtra(REQUEST_CODE, REQUEST_CODE_LOCALIDAD_CLIE);
                    startActivityForResult(i_localidad, REQUEST_CODE_LOCALIDAD_CLIE);
                } else {
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

    /**
     * Evento para seleccionar el nivel de estudio del cliente
     */
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
                                Update("nivel_estudio", TBL_CLIENTE_IND_REN, Miscellaneous.GetStr(tvEstudiosCli));
                            }
                        });
                builder.create();
                builder.show();
            }
        }
    };

    private View.OnClickListener txtCamapana_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent i_campanas = new Intent(ctx, Catalogos.class);
            i_campanas.putExtra(TITULO, Miscellaneous.ucFirst(CAMPANAS));
            i_campanas.putExtra(CATALOGO, CAMPANAS);
            i_campanas.putExtra(REQUEST_CODE, REQUEST_CODE_CAMAPANAS);
            startActivityForResult(i_campanas, REQUEST_CODE_CAMAPANAS);
        }
    };

    /**
     * Evento para seleccionar el estado civil del cliente y definir se se va a mostrar o no el formulario del conyuge
     */
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
                                Update("estado_civil", TBL_CLIENTE_IND_REN, Miscellaneous.GetStr(tvEstadoCivilCli));
                            }
                        });
                builder.create();
                builder.show();
            }
        }
    };

    /**
     * Evento para seleccionar el tipo de casa del cliente
     */
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
                                Update("tipo_vivienda", TBL_CLIENTE_IND_REN, Miscellaneous.GetStr(tvTipoCasaCli));
                            }
                        });
                builder.create();
                builder.show();
            }
        }
    };

    /**
     * Evento para selecionar el parentesco con el dueño de la casa
     */
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
                                Update("parentesco", TBL_CLIENTE_IND_REN, Miscellaneous.GetStr(tvCasaFamiliar));
                            }
                        });
                builder.create();
                builder.show();
            }
        }
    };

    /**
     * Evento para seleccionar le # de dependientes del cliente
     */
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
                                Update("dependientes", TBL_CLIENTE_IND_REN, Miscellaneous.GetStr(tvDependientes));
                            }
                        });
                builder.create();
                builder.show();
            }
        }
    };

    /**
     * Evento para seleccionar si el cliente cuenta con estado de cuenta
     */
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
                                Update("estado_cuenta", TBL_CLIENTE_IND_REN, Miscellaneous.GetStr(tvEstadoCuenta));
                            }
                        });
                builder.create();
                builder.show();
            }
        }
    };

    /**
     * Evento para seleccionar como se entero de la empresa
     */
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
                                Update("medio_contacto", TBL_CLIENTE_IND_REN, Miscellaneous.GetStr(tvEnteroNosotros));
                            }
                        });
                builder.create();
                builder.show();
            }
        }
    };

    /**
     * Evento para seleccionar la colonia donde vive el aval
     */
    private View.OnClickListener tvColoniaAval_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (isEditAva || tvColoniaAval.getText().toString().isEmpty()) {
                Intent i_colonia = new Intent(ctx, Catalogos.class);
                i_colonia.putExtra(TITULO, Miscellaneous.ucFirst(COLONIA + "s"));
                i_colonia.putExtra(CATALOGO, COLONIAS);
                i_colonia.putExtra(EXTRA, Miscellaneous.GetStr(etCpAval));
                i_colonia.putExtra(REQUEST_CODE, REQUEST_CODE_COLONIA_AVAL);
                startActivityForResult(i_colonia, REQUEST_CODE_COLONIA_AVAL);
            }
        }
    };

    /**
     * Evento para seleccionar lo localidad donde vive el aval
     */
    private View.OnClickListener tvLocalidadAval_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (isEditAva || tvLocalidadAval.getText().toString().trim().isEmpty()) {
                ColoniaDao coloniaDao = new ColoniaDao(ctx);
                List<Colonia> colonias = coloniaDao.findAllByCp(etCpAval.getText().toString().trim());

                if (!Miscellaneous.GetStr(tvMunicipioAval).isEmpty() && colonias.size() > 0) {
                    Cursor row = dBhelper.getRecords(TABLE_MUNICIPIOS, " WHERE municipio_id = ?", "", new String[]{String.valueOf(colonias.get(0).getMunicipioId())});
                    row.moveToFirst();
                    Intent i_localidad = new Intent(ctx, Catalogos.class);
                    i_localidad.putExtra(TITULO, Miscellaneous.ucFirst(LOCALIDADES));
                    i_localidad.putExtra(CATALOGO, LOCALIDADES);
                    i_localidad.putExtra(EXTRA, row.getString(1));
                    i_localidad.putExtra(REQUEST_CODE, REQUEST_CODE_LOCALIDAD_AVAL);
                    startActivityForResult(i_localidad, REQUEST_CODE_LOCALIDAD_AVAL);
                } else {
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

    /**
     * EVento para seleccionar el tipo de casa donde vivie el aval
     */
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
                                Update("tipo_vivienda", TBL_AVAL_IND_REN, Miscellaneous.GetStr(tvTipoCasaAval));
                            }
                        });
                builder.create();
                builder.show();
            }
        }
    };

    /**
     * Evento para seleccionar el parentesco del aval con el cliente
     */
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
                                Update("parentesco_cliente", TBL_AVAL_IND_REN, Miscellaneous.GetStr(tvParentescoAval));
                            }
                        });
                builder.create();
                builder.show();
            }
        }
    };

    /**
     * Evento para seleccionar el parentesco de la casa con el aval
     */
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
                                Update("parentesco", TBL_AVAL_IND_REN, Miscellaneous.GetStr(tvFamiliarAval));
                            }
                        });
                builder.create();
                builder.show();
            }
        }
    };

    /**
     * Evento para seleccioanr los medios de pago que puede realizar el aval
     */
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
                                for (int i = 0; i < selectedItemsMediosPago.size(); i++) {
                                    if (i == 0)
                                        medios += _medios_pago[selectedItemsMediosPago.get(i)];
                                    else
                                        medios += ", " + _medios_pago[selectedItemsMediosPago.get(i)];
                                }
                                tvMediosPagoAval.setError(null);
                                tvMediosPagoAval.setText(medios);
                                Update("medio_pago", TBL_AVAL_IND_REN, Miscellaneous.GetStr(tvMediosPagoAval));

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

    /**
     * Evento para seleccionar los activos observables del aval
     */
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
                                for (int i = 0; i < selectedItemsActivos.size(); i++) {
                                    if (i == 0)
                                        medios += _activos[selectedItemsActivos.get(i)];
                                    else
                                        medios += ", " + _activos[selectedItemsActivos.get(i)];
                                }
                                tvActivosObservables.setError(null);
                                tvActivosObservables.setText(medios);
                                Update("activos_observables", TBL_AVAL_IND_REN, Miscellaneous.GetStr(tvActivosObservables));

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

    /**
     * Evento para capurar la firma del aval
     */
    private View.OnClickListener ibFirmaAval_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i_firma_aval = new Intent(ctx, CapturarFirma.class);
            i_firma_aval.putExtra(TIPO, "AVAL");
            startActivityForResult(i_firma_aval, REQUEST_CODE_FIRMA_AVAL);
        }
    };

    /**
     * Evento para seleccionar los dias de venta del negocio del cliente
     */
    private View.OnClickListener etDiasVenta_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (isEditNeg) {
                showDiasSemana();
            }
        }
    };

    /**
     * Evento para seleccionar la colonia del negocio
     */
    private View.OnClickListener etColoniaNeg_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (isEditNeg || tvColoniaNeg.getText().toString().trim().isEmpty()) {
                Intent i_colonia = new Intent(ctx, Catalogos.class);
                i_colonia.putExtra(TITULO, Miscellaneous.ucFirst(COLONIA + "s"));
                i_colonia.putExtra(CATALOGO, COLONIAS);
                i_colonia.putExtra(EXTRA, Miscellaneous.GetStr(etCpNeg));
                i_colonia.putExtra(REQUEST_CODE, REQUEST_CODE_COLONIA_NEG);
                startActivityForResult(i_colonia, REQUEST_CODE_COLONIA_NEG);
            }
        }
    };

    /**
     * Evento para seleccionar la actividad economica del negocio
     */
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

    /**
     * Evento para seleccionar la localidad del negocio
     */
    private View.OnClickListener tvLocalidadNeg_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (isEditNeg || tvLocalidadNeg.getText().toString().trim().isEmpty()) {
                ColoniaDao coloniaDao = new ColoniaDao(ctx);
                List<Colonia> colonias = coloniaDao.findAllByCp(etCpNeg.getText().toString().trim());

                if (!Miscellaneous.GetStr(tvMunicipioNeg).isEmpty() && colonias.size() > 0) {
                    Cursor row = dBhelper.getRecords(TABLE_MUNICIPIOS, " WHERE municipio_id = ?", "", new String[]{String.valueOf(colonias.get(0).getMunicipioId())});
                    row.moveToFirst();
                    Intent i_localidad = new Intent(ctx, Catalogos.class);
                    i_localidad.putExtra(TITULO, Miscellaneous.ucFirst(LOCALIDADES));
                    i_localidad.putExtra(CATALOGO, LOCALIDADES);
                    i_localidad.putExtra(EXTRA, row.getString(1));
                    i_localidad.putExtra(REQUEST_CODE, REQUEST_CODE_LOCALIDAD_NEG);
                    startActivityForResult(i_localidad, REQUEST_CODE_LOCALIDAD_NEG);
                } else {
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

    /**
     * Evento para obtener la ubicacion del negocio
     */
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

    /**
     * Evento para capturar la fotografia del negocio
     */
    private View.OnClickListener ibFotoFachNeg_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i = new Intent(RenovacionCreditoInd.this, CameraVertical.class);
            i.putExtra(ORDER_ID, "SC_negocio");
            startActivityForResult(i, REQUEST_CODE_CAMARA_FACHADA_NEG);
        }
    };

    /**
     * Evento para seleccionar la ocupacion del cliente
     */
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

    /**
     * Evento para seleccionar el tipo de identificacion del cliente
     */
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
                                Update("tipo_identificacion", TBL_CLIENTE_IND_REN, Miscellaneous.GetStr(tvTipoIdentificacion));
                            }
                        });
                builder.create();
                builder.show();
            }
        }
    };

    /**
     * Evento para seleccionar el tipo de identificacion del aval
     */
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
                                Update("tipo_identificacion", TBL_AVAL_IND_REN, Miscellaneous.GetStr(tvTipoIdentificacionAval));
                            }
                        });
                builder.create();
                builder.show();
            }
        }
    };

    /**
     * Evento para seleccionar la ocupacion del conyuge
     */
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

    /**
     * Evento para seleccionar lo colonia del conyuge
     */
    private View.OnClickListener tvColoniaCony_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (isEditCon || tvColoniaCony.getText().toString().trim().isEmpty()) {
                Intent i_colonia = new Intent(ctx, Catalogos.class);
                i_colonia.putExtra(TITULO, Miscellaneous.ucFirst(COLONIA + "s"));
                i_colonia.putExtra(CATALOGO, COLONIAS);
                i_colonia.putExtra(EXTRA, Miscellaneous.GetStr(etCpCony));
                i_colonia.putExtra(REQUEST_CODE, REQUEST_CODE_COLONIA_CONY);
                startActivityForResult(i_colonia, REQUEST_CODE_COLONIA_CONY);
            }
        }
    };

    /**
     * Evento para seleccionar la localidad del conyuge
     */
    private View.OnClickListener tvLocalidadCony_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (isEditCon || tvLocalidadCony.getText().toString().trim().isEmpty()) {
                ColoniaDao coloniaDao = new ColoniaDao(ctx);
                List<Colonia> colonias = coloniaDao.findAllByCp(etCpCony.getText().toString().trim());

                if (!Miscellaneous.GetStr(tvMunicipioCony).isEmpty() && colonias.size() > 0) {
                    Cursor row = dBhelper.getRecords(TABLE_MUNICIPIOS, " WHERE municipio_id = ?", "", new String[]{String.valueOf(colonias.get(0).getMunicipioId())});
                    row.moveToFirst();
                    Intent i_localidad = new Intent(ctx, Catalogos.class);
                    i_localidad.putExtra(TITULO, Miscellaneous.ucFirst(LOCALIDADES));
                    i_localidad.putExtra(CATALOGO, LOCALIDADES);
                    i_localidad.putExtra(EXTRA, row.getString(1));
                    i_localidad.putExtra(REQUEST_CODE, REQUEST_CODE_LOCALIDAD_CONY);
                    startActivityForResult(i_localidad, REQUEST_CODE_LOCALIDAD_CONY);
                } else {
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

    private View.OnClickListener tvNumOperacionNeg_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (isEditCre) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
                builder.setTitle(R.string.selected_option)
                        .setItems(R.array.max_pagos_mes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int position) {
                                tvNumOperacionNeg.setError(null);
                                tvNumOperacionNeg.setText(_max_pagos_mes[position]);
                                Update("num_operacion_mensuales", TBL_NEGOCIO_IND_REN, Miscellaneous.GetStr(tvNumOperacionNeg));
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
            if (isEditCre) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
                builder.setTitle(R.string.selected_option)
                        .setItems(R.array.max_pagos_mes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int position) {
                                etNumOperacionEfectNeg.setError(null);
                                etNumOperacionEfectNeg.setText(_max_pagos_mes[position]);
                                Update("num_operacion_efectivo", TBL_NEGOCIO_IND_REN, Miscellaneous.GetStr(etNumOperacionEfectNeg));
                            }
                        });
                builder.create();
                builder.show();
            }
        }
    };

    /**
     * Evento para seleccioanr el destino del negocio
     */
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

                                if (position == 0) {
                                    etOtroDestinoNeg.setVisibility(View.GONE);
                                    etOtroDestinoNeg.setText("");
                                    Update("otro_destino", TBL_NEGOCIO_IND_REN, "");
                                } else {
                                    etOtroDestinoNeg.setVisibility(View.VISIBLE);
                                }
                            }
                        });
                builder.create();
                builder.show();
            }
        }
    };

    /**
     * Evento para selecionar los medios de pago
     */
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
                                for (int i = 0; i < selectedItemsMediosPago.size(); i++) {
                                    if (i == 0)
                                        medios += _medios_pago[selectedItemsMediosPago.get(i)];
                                    else
                                        medios += ", " + _medios_pago[selectedItemsMediosPago.get(i)];
                                }
                                tvMediosPagoNeg.setError(null);
                                tvMediosPagoNeg.setText(medios);
                                Update("medio_pago", TBL_NEGOCIO_IND_REN, Miscellaneous.GetStr(tvMediosPagoNeg));

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
                    Update("ubicado_en_dom_cli", TBL_NEGOCIO_IND_REN, "SI");
                    ObtenerUbicacionNeg();
                    CopiarDatosDeClienteHaciaNegocio();
                }
            } else {
                //LIMPIAR DATOS
                Update("ubicado_en_dom_cli", TBL_NEGOCIO_IND_REN, "NO");
                LimpiarDatosNegocio();
            }
        }
    };

    //============ REFERNCIA =================================================================
    /**
     * Evento para seleccionar la fecha de nacimiento de la referencia
     */
    private View.OnClickListener tvFechaNacRef_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (isEditRef) {
                /*dialog_date_picker dialogDatePicker = new dialog_date_picker();
                Bundle b = new Bundle();

                b.putInt(YEAR_CURRENT, myCalendar.get(Calendar.YEAR));
                b.putInt(MONTH_CURRENT, myCalendar.get(Calendar.MONTH));
                b.putInt(DAY_CURRENT, myCalendar.get(Calendar.DAY_OF_MONTH));
                b.putString(DATE_CURRENT, sdf.format(myCalendar.getTime()));
                b.putInt(IDENTIFIER, 17);
                b.putBoolean(FECHAS_POST, false);
                dialogDatePicker.setArguments(b);
                dialogDatePicker.show(getSupportFragmentManager(), NameFragments.DIALOGDATEPICKER);*/

                int dato7 = 7;

                date = new DialogSelectorFecha(RenovacionCreditoInd.this, tvFechaNacRef, dato7, indRen);

                String fechaMostrada = tvFechaNacRef.getText().toString().trim();
                if (!fechaMostrada.isEmpty()) {
                    date.recuperarFecha(fechaMostrada);
                } else {
                    date.showDatePickerDialog();
                }


            }
        }
    };

    /**
     * Evento para seleccionar la colonia de la referencia
     */
    private View.OnClickListener tvColoniaRef_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (isEditRef || tvColoniaRef.getText().toString().trim().isEmpty()) {
                Intent i_colonia = new Intent(ctx, Catalogos.class);
                i_colonia.putExtra(TITULO, Miscellaneous.ucFirst(COLONIA + "s"));
                i_colonia.putExtra(CATALOGO, COLONIAS);
                i_colonia.putExtra(EXTRA, Miscellaneous.GetStr(etCpRef));
                i_colonia.putExtra(REQUEST_CODE, REQUEST_CODE_COLONIA_REF);
                startActivityForResult(i_colonia, REQUEST_CODE_COLONIA_REF);
            }
        }
    };

    /**
     * Evento para seleccionar la localidad de la referencia
     */
    private View.OnClickListener tvLocalidadRef_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (isEditRef || tvLocalidadRef.getText().toString().trim().isEmpty()) {
                ColoniaDao coloniaDao = new ColoniaDao(ctx);
                List<Colonia> colonias = coloniaDao.findAllByCp(etCpRef.getText().toString().trim());

                if (!Miscellaneous.GetStr(tvMunicipioRef).isEmpty() && colonias.size() > 0) {
                    Cursor row = dBhelper.getRecords(TABLE_MUNICIPIOS, " WHERE municipio_id = ?", "", new String[]{String.valueOf(colonias.get(0).getMunicipioId())});
                    row.moveToFirst();
                    Intent i_localidad = new Intent(ctx, Catalogos.class);
                    i_localidad.putExtra(TITULO, Miscellaneous.ucFirst(LOCALIDADES));
                    i_localidad.putExtra(CATALOGO, LOCALIDADES);
                    i_localidad.putExtra(EXTRA, row.getString(1));
                    i_localidad.putExtra(REQUEST_CODE, REQUEST_CODE_LOCALIDAD_REF);
                    startActivityForResult(i_localidad, REQUEST_CODE_LOCALIDAD_REF);
                } else {
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
    /**
     * Evento para seleccionar la fecha de nacimiento del aval
     */
    private View.OnClickListener tvFechaNacAval_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (isEditAva) {
               /* dialog_date_picker dialogDatePicker = new dialog_date_picker();
                Bundle b = new Bundle();

                /*b.putInt(YEAR_CURRENT, myCalendar.get(Calendar.YEAR));
                b.putInt(MONTH_CURRENT, myCalendar.get(Calendar.MONTH));
                b.putInt(DAY_CURRENT, myCalendar.get(Calendar.DAY_OF_MONTH));
                b.putInt(YEAR_CURRENT, ((Miscellaneous.GetStr(tvFechaNacAval).isEmpty()) ? myCalendar.get(Calendar.YEAR) : Integer.parseInt(Miscellaneous.GetStr(tvFechaNacAval).substring(0, 4))));
                b.putInt(MONTH_CURRENT, ((Miscellaneous.GetStr(tvFechaNacAval).isEmpty()) ? myCalendar.get(Calendar.MONTH) : (Integer.parseInt(Miscellaneous.GetStr(tvFechaNacAval).substring(5, 7)) - 1)));
                b.putInt(DAY_CURRENT, ((Miscellaneous.GetStr(tvFechaNacAval).isEmpty()) ? myCalendar.get(Calendar.DAY_OF_MONTH) : Integer.parseInt(Miscellaneous.GetStr(tvFechaNacAval).substring(8, 10))));
                b.putString(DATE_CURRENT, sdf.format(myCalendar.getTime()));
                b.putInt(IDENTIFIER, 16);
                b.putBoolean(FECHAS_POST, false);
                dialogDatePicker.setArguments(b);
                dialogDatePicker.show(getSupportFragmentManager(), NameFragments.DIALOGDATEPICKER);*/

                int dato6 = 6;

                date = new DialogSelectorFecha(RenovacionCreditoInd.this, tvFechaNacAval, dato6, indRen);

                String fechaMostrada = tvFechaNacAval.getText().toString().trim();
                if (!fechaMostrada.isEmpty()) {
                    date.recuperarFecha(fechaMostrada);
                } else {
                    date.showDatePickerDialog();
                }

            }
        }
    };

    /**
     * Evento para seleccioanr el estado de nacimiento del aval
     */
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

    /**
     * Evento para seleccionar la ocupacion del aval
     */
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

    /**
     * Evento para seleccionar la hora de localizacion del aval
     */
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

    /**
     * Evento para obtener la ubicacion del domicilio del aval
     */
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

    /**
     * Evento para capturar la fotografia de la fachada del aval
     */
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
    /**
     * Evento para capturar la calle principal de la casa del cliente
     */
    private View.OnClickListener tvPrincipal_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (isEditCro) {
                dialog_input_calle dlgInput = new dialog_input_calle();
                Bundle b = new Bundle();
                b.putString(TIPO, "PRINCIPAL");
                b.putString(CALLE, Miscellaneous.GetStr(tvPrincipal));
                b.putString(TIPO_SOLICITUD, "INDIVIDUAL RENOVACION");
                dlgInput.setArguments(b);
                dlgInput.show(getSupportFragmentManager(), NameFragments.DIALOGINPUTCALLE);
            }
        }
    };
    /**
     * Evento para capturar la calle trasera de la casa del cliente
     */
    private View.OnClickListener tvTrasera_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (isEditCro) {
                dialog_input_calle dlgInput = new dialog_input_calle();
                Bundle b = new Bundle();
                b.putString(TIPO, "TRASERA");
                b.putString(CALLE, Miscellaneous.GetStr(tvTrasera));
                b.putString(TIPO_SOLICITUD, "INDIVIDUAL RENOVACION");
                dlgInput.setArguments(b);
                dlgInput.show(getSupportFragmentManager(), NameFragments.DIALOGINPUTCALLE);
            }
        }
    };
    /**
     * Evento para capturar la calle de la derecha de la casa del cliente
     */
    private View.OnClickListener tvLateralUno_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (isEditCro) {
                dialog_input_calle dlgInput = new dialog_input_calle();
                Bundle b = new Bundle();
                b.putString(TIPO, "LATERAL UNO");
                b.putString(CALLE, Miscellaneous.GetStr(tvLateraUno));
                b.putString(TIPO_SOLICITUD, "INDIVIDUAL RENOVACION");
                dlgInput.setArguments(b);
                dlgInput.show(getSupportFragmentManager(), NameFragments.DIALOGINPUTCALLE);
            }
        }
    };
    /**
     * Evento para capturar la calle de la izquierda de la casa del cliente
     */
    private View.OnClickListener tvLateralDos_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (isEditCro) {
                dialog_input_calle dlgInput = new dialog_input_calle();
                Bundle b = new Bundle();
                b.putString(TIPO, "LATERAL DOS");
                b.putString(CALLE, Miscellaneous.GetStr(tvLateraDos));
                b.putString(TIPO_SOLICITUD, "INDIVIDUAL RENOVACION");
                dlgInput.setArguments(b);
                dlgInput.show(getSupportFragmentManager(), NameFragments.DIALOGINPUTCALLE);
            }
        }
    };
    //======================  DOCUMENTOS  ==============================
    /**
     * Evento para capturar la fotografia frontal del INE/IFE
     */
    private View.OnClickListener ibIneFrontal_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent scanIntent = new Intent(RenovacionCreditoInd.this, CardIOActivity.class);
            scanIntent.putExtra(CardIOActivity.EXTRA_SUPPRESS_SCAN, true); // supmit cuando termine de reconocer el documento
            scanIntent.putExtra(CardIOActivity.EXTRA_SUPPRESS_MANUAL_ENTRY, true); // esconder teclado
            scanIntent.putExtra(CardIOActivity.EXTRA_USE_CARDIO_LOGO, true); // cambiar logo de paypal por el de card.io
            scanIntent.putExtra(CardIOActivity.EXTRA_RETURN_CARD_IMAGE, true); // capture img
            scanIntent.putExtra(CardIOActivity.EXTRA_CAPTURED_CARD_IMAGE, true); // capturar img
            startActivityForResult(scanIntent, REQUEST_CODE_FOTO_INE_FRONTAL);
        }
    };
    /**
     * Evento para capturar la fotografia reverso del INE/IFE
     */
    private View.OnClickListener ibIneReverso_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent scanIntent = new Intent(RenovacionCreditoInd.this, CardIOActivity.class);
            scanIntent.putExtra(CardIOActivity.EXTRA_SUPPRESS_SCAN, true); // supmit cuando termine de reconocer el documento
            scanIntent.putExtra(CardIOActivity.EXTRA_SUPPRESS_MANUAL_ENTRY, true); // esconder teclado
            scanIntent.putExtra(CardIOActivity.EXTRA_USE_CARDIO_LOGO, true); // cambiar logo de paypal por el de card.io
            scanIntent.putExtra(CardIOActivity.EXTRA_RETURN_CARD_IMAGE, true); // capture img
            scanIntent.putExtra(CardIOActivity.EXTRA_CAPTURED_CARD_IMAGE, true); // capturar img

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

    /**
     * Evento para capturar la fotografia comprobante del domicilio
     */
    private View.OnClickListener ibComprobante_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i = new Intent(RenovacionCreditoInd.this, CameraVertical.class);
            i.putExtra(ORDER_ID, "O_comprobante");
            startActivityForResult(i, REQUEST_CODE_FOTO_COMPROBATE);
        }
    };

    private View.OnClickListener ibIneSelfie_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i = new Intent(RenovacionCreditoInd.this, CameraVertical.class);
            i.putExtra(ORDER_ID, "O_ine_selfie");
            startActivityForResult(i, REQUEST_CODE_FOTO_INE_SELFIE);
        }
    };

    private View.OnClickListener ibComprobanteGarantia_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i = new Intent(RenovacionCreditoInd.this, CameraVertical.class);
            i.putExtra(ORDER_ID, "O_comprobante_garantia");
            startActivityForResult(i, REQUEST_CODE_FOTO_COMPROBATE_GARANTIA);
        }
    };

    private View.OnClickListener ibIneFrontalAval_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent scanIntent = new Intent(RenovacionCreditoInd.this, CardIOActivity.class);
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
            Intent scanIntent = new Intent(RenovacionCreditoInd.this, CardIOActivity.class);
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
            Intent i = new Intent(RenovacionCreditoInd.this, CameraVertical.class);
            i.putExtra(ORDER_ID, "O_curp_aval");
            startActivityForResult(i, REQUEST_CODE_FOTO_CURP_AVAL);
        }
    };

    private View.OnClickListener ibComprobanteAval_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i = new Intent(RenovacionCreditoInd.this, CameraVertical.class);
            i.putExtra(ORDER_ID, "O_comprobante_aval");
            startActivityForResult(i, REQUEST_CODE_FOTO_COMPROBANTE_AVAL);
        }
    };

    //================== IMAGE VIEW LISTENER  ======================================================
    /**
     * Evento para volver a capturar o visualizar la fotografia de fachada del cliente
     */
    private View.OnClickListener ivFotoFachCli_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (isEditCli) {
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
            } else {
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
    /**
     * Evento para volver a capturar la firma del cliente
     */
    private View.OnClickListener ivFirmaCli_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (isEditCli) {
                final AlertDialog firma_dlg = Popups.showDialogConfirm(ctx, firma,
                        R.string.capturar_nueva_firma, R.string.accept, new Popups.DialogMessage() {
                            @Override
                            public void OnClickListener(AlertDialog dialog) {
                                Intent sig = new Intent(ctx, CapturarFirma.class);
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
    /**
     * Evento para volver a capturar o visualizar la fotografia de fachada del negocio
     */
    private View.OnClickListener ivFotoFachNeg_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (isEditNeg) {
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
            } else {
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
    /**
     * Evento para volver a capturar o visualizar la fotografia de fachada del aval
     */
    private View.OnClickListener ivFotoFachAval_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (isEditAva) {
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
            } else {
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
    /**
     * Evento para volver a capturar la firma del aval
     */
    private View.OnClickListener ivFirmaAval_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (isEditAva) {
                final AlertDialog firma_dlg = Popups.showDialogConfirm(ctx, firma,
                        R.string.capturar_nueva_firma, R.string.accept, new Popups.DialogMessage() {
                            @Override
                            public void OnClickListener(AlertDialog dialog) {
                                Intent sig = new Intent(ctx, CapturarFirma.class);
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
    /**
     * Evento para volver a capturar o visualizar la fotografia de INE/IFE frontal
     */
    private View.OnClickListener ivIneFrontal_OnClik = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (isEditDoc) {
                final AlertDialog evidencia_dlg = Popups.showDialogConfirmImage(ctx, question,
                        R.string.capturar_foto, R.string.fotografia, new Popups.DialogMessage() {
                            @Override
                            public void OnClickListener(AlertDialog dialog) {
                                Intent scanIntent = new Intent(RenovacionCreditoInd.this, CardIOActivity.class);
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
                                //i.putExtra(IMAGEN, byteIneFrontal);
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
                final AlertDialog fachada_dlg = Popups.showDialogConfirm(ctx, question,
                        R.string.ver_fotografia, R.string.ver_imagen, new Popups.DialogMessage() {
                            @Override
                            public void OnClickListener(AlertDialog dialog) {
                                Intent i = new Intent(ctx, VerImagen.class);
                                //i.putExtra(IMAGEN, byteIneFrontal);
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
    /**
     * Evento para volver a capturar o visualizar la fotografia de INE/IFE reverso
     */
    private View.OnClickListener ivIneReverso_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (isEditDoc) {
                final AlertDialog evidencia_dlg = Popups.showDialogConfirmImage(ctx, question,
                        R.string.capturar_foto, R.string.fotografia, new Popups.DialogMessage() {
                            @Override
                            public void OnClickListener(AlertDialog dialog) {
                                Intent scanIntent = new Intent(RenovacionCreditoInd.this, CardIOActivity.class);
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
                                //i.putExtra(IMAGEN, byteIneReverso);
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
                final AlertDialog fachada_dlg = Popups.showDialogConfirm(ctx, question,
                        R.string.ver_fotografia, R.string.ver_imagen, new Popups.DialogMessage() {
                            @Override
                            public void OnClickListener(AlertDialog dialog) {
                                Intent i = new Intent(ctx, VerImagen.class);
                                //i.putExtra(IMAGEN, byteIneReverso);
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
    /**
     * Evento para volver a capturar o visualizar la fotografia del comprobante
     */
    private View.OnClickListener ivComprobante_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (isEditDoc) {
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
            } else {
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
    /**
     * Evento para volver a capturar la firma del asesor
     */
    private View.OnClickListener ivFirmaAsesor_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (isEditDoc) {
                final AlertDialog firma_dlg = Popups.showDialogConfirm(ctx, firma,
                        R.string.capturar_nueva_firma, R.string.accept, new Popups.DialogMessage() {
                            @Override
                            public void OnClickListener(AlertDialog dialog) {
                                Intent sig = new Intent(ctx, CapturarFirma.class);
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

    private View.OnClickListener ivIneSelfie_OnClik = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (isEditDoc) {
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
                                //i.putExtra(IMAGEN, byteIneSelfie);
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
                final AlertDialog fachada_dlg = Popups.showDialogConfirm(ctx, question,
                        R.string.ver_fotografia, R.string.ver_imagen, new Popups.DialogMessage() {
                            @Override
                            public void OnClickListener(AlertDialog dialog) {
                                Intent i = new Intent(ctx, VerImagen.class);
                                //i.putExtra(IMAGEN, byteIneSelfie);
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
            if (isEditDoc) {
                final AlertDialog evidencia_dlg = Popups.showDialogConfirmImage(ctx, question,
                        R.string.capturar_foto, R.string.fotografia, new Popups.DialogMessage() {
                            @Override
                            public void OnClickListener(AlertDialog dialog) {
                                Intent i = new Intent(ctx, CameraVertical.class);
                                startActivityForResult(i, REQUEST_CODE_FOTO_COMPROBATE_GARANTIA);
                                dialog.dismiss();

                            }
                        }, R.string.ver_imagen, new Popups.DialogMessage() {
                            @Override
                            public void OnClickListener(AlertDialog dialog) {
                                Intent i = new Intent(ctx, VerImagen.class);
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
                final AlertDialog fachada_dlg = Popups.showDialogConfirm(ctx, question,
                        R.string.ver_fotografia, R.string.ver_imagen, new Popups.DialogMessage() {
                            @Override
                            public void OnClickListener(AlertDialog dialog) {
                                Intent i = new Intent(ctx, VerImagen.class);
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
            if (isEditAva) {
                final AlertDialog evidencia_dlg = Popups.showDialogConfirmImage(ctx, question,
                        R.string.capturar_foto, R.string.fotografia, new Popups.DialogMessage() {
                            @Override
                            public void OnClickListener(AlertDialog dialog) {
                                Intent scanIntent = new Intent(RenovacionCreditoInd.this, CardIOActivity.class);
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
                final AlertDialog fachada_dlg = Popups.showDialogConfirm(ctx, question,
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
            if (isEditAva) {
                final AlertDialog evidencia_dlg = Popups.showDialogConfirmImage(ctx, question,
                        R.string.capturar_foto, R.string.fotografia, new Popups.DialogMessage() {
                            @Override
                            public void OnClickListener(AlertDialog dialog) {
                                Intent scanIntent = new Intent(RenovacionCreditoInd.this, CardIOActivity.class);
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
                final AlertDialog fachada_dlg = Popups.showDialogConfirm(ctx, question,
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
            if (isEditAva) {
                final AlertDialog evidencia_dlg = Popups.showDialogConfirmImage(ctx, question,
                        R.string.capturar_foto, R.string.fotografia, new Popups.DialogMessage() {
                            @Override
                            public void OnClickListener(AlertDialog dialog) {
                                Intent i = new Intent(ctx, CameraVertical.class);
                                startActivityForResult(i, REQUEST_CODE_FOTO_CURP_AVAL);
                                dialog.dismiss();

                            }
                        }, R.string.ver_imagen, new Popups.DialogMessage() {
                            @Override
                            public void OnClickListener(AlertDialog dialog) {
                                Intent i = new Intent(ctx, VerImagen.class);
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
                final AlertDialog fachada_dlg = Popups.showDialogConfirm(ctx, question,
                        R.string.ver_fotografia, R.string.ver_imagen, new Popups.DialogMessage() {
                            @Override
                            public void OnClickListener(AlertDialog dialog) {
                                Intent i = new Intent(ctx, VerImagen.class);
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

    private View.OnClickListener ivComprobanteAval_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (isEditAva) {
                final AlertDialog evidencia_dlg = Popups.showDialogConfirmImage(ctx, question,
                        R.string.capturar_foto, R.string.fotografia, new Popups.DialogMessage() {
                            @Override
                            public void OnClickListener(AlertDialog dialog) {
                                Intent i = new Intent(ctx, CameraVertical.class);
                                startActivityForResult(i, REQUEST_CODE_FOTO_COMPROBANTE_AVAL);
                                dialog.dismiss();

                            }
                        }, R.string.ver_imagen, new Popups.DialogMessage() {
                            @Override
                            public void OnClickListener(AlertDialog dialog) {
                                Intent i = new Intent(ctx, VerImagen.class);
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
                final AlertDialog fachada_dlg = Popups.showDialogConfirm(ctx, question,
                        R.string.ver_fotografia, R.string.ver_imagen, new Popups.DialogMessage() {
                            @Override
                            public void OnClickListener(AlertDialog dialog) {
                                Intent i = new Intent(ctx, VerImagen.class);
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
    /**
     * Eventos de click en los contenedores para mostrar u ocultar las secciones del formulario
     */
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

    private View.OnClickListener llBeneficiario_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            if (ivDown10.getVisibility() == View.VISIBLE && ivUp10.getVisibility() == View.GONE) {
                ivDown10.setVisibility(View.GONE);
                ivUp10.setVisibility(View.VISIBLE);
                txtNombreBeneficiario.requestFocus();
                llDatosBeneficiario.setVisibility(View.VISIBLE);
            } else if (ivDown10.getVisibility() == View.GONE && ivUp10.getVisibility() == View.VISIBLE) {
                ivDown10.setVisibility(View.VISIBLE);
                ivUp10.setVisibility(View.GONE);
                llDatosBeneficiario.setVisibility(View.GONE);
            }
        }
    };
    //=============================================================================================

    private View.OnClickListener txtParentescoBen_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
            builder.setTitle("Seleccione una opción")
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
    //==================================================================
    /**
     * Eventos solo para avanzar de secciones del formulario
     */
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
            if (Miscellaneous.GetStr(tvEstadoCivilCli).equals("CASADO(A)") || Miscellaneous.GetStr(tvEstadoCivilCli).equals("UNION LIBRE")) {
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
                    ivDown11.setVisibility(View.GONE);
                    ivUp11.setVisibility(View.VISIBLE);
                    llDatosBeneficiario.setVisibility(View.VISIBLE);
                    txtNombreBeneficiario.requestFocus();
                }
            }

            ivDown2.setVisibility(View.VISIBLE);
            ivUp2.setVisibility(View.GONE);
            llDatosPersonales.setVisibility(View.GONE);


        }
    };
    private View.OnClickListener btnContinuar9_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            etNombreNeg.requestFocus();
            ivDown5.setVisibility(View.GONE);
            ivUp5.setVisibility(View.VISIBLE);
            llDatosNegocio.setVisibility(View.VISIBLE);

            ivDown11.setVisibility(View.VISIBLE);
            ivUp11.setVisibility(View.GONE);
            llDatosBeneficiario.setVisibility(View.GONE);
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
                ivDown11.setVisibility(View.GONE);
                ivUp11.setVisibility(View.VISIBLE);
                llDatosBeneficiario.setVisibility(View.VISIBLE);
                txtNombreBeneficiario.requestFocus();

            }
            ivDown3.setVisibility(View.VISIBLE);
            ivUp3.setVisibility(View.GONE);
            llDatosConyuge.setVisibility(View.GONE);
        }
    };
    private View.OnClickListener btnContinuarBeni_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            ivDown11.setVisibility(View.VISIBLE);
            ivUp11.setVisibility(View.GONE);
            llDatosBeneficiario.setVisibility(View.GONE);

            ivDown4.setVisibility(View.GONE);
            ivUp4.setVisibility(View.VISIBLE);
            llDatosNegocio.setVisibility(View.VISIBLE);
            etNombreNeg.requestFocus();
        }
    };
    private View.OnClickListener btnContinuar3_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //ivDown5.setVisibility(View.GONE);
            //ivUp5.setVisibility(View.VISIBLE);
            //llDatosNegocio.setVisibility(View.VISIBLE);
            //etNombreNeg.requestFocus();
            ivDown11.setVisibility(View.GONE);
            ivUp11.setVisibility(View.VISIBLE);
            llDatosBeneficiario.setVisibility(View.VISIBLE);
            txtNombreBeneficiario.requestFocus();

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
    /**
     * Eventos solo para retroceder en secciones del formulario
     */
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
            if (Miscellaneous.GetStr(tvEstadoCivilCli).equals("CASADO(A)") ||
                    Miscellaneous.GetStr(tvEstadoCivilCli).equals("UNION LIBRE")) {
                ivDown3.setVisibility(View.GONE);
                ivUp3.setVisibility(View.VISIBLE);
                llDatosConyuge.setVisibility(View.VISIBLE);
                etNombreCony.requestFocus();
            } else {
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
    private View.OnClickListener btnRegresarBeni_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (!Miscellaneous.GetStr(etMontoPrestamo).replace(",", " ").isEmpty() && Integer.parseInt(Miscellaneous.GetStr(etMontoPrestamo).replace(",", "")) > 30000) {
                ivDown4.setVisibility(View.GONE);
                ivUp4.setVisibility(View.VISIBLE);
                llDatosEconomicos.setVisibility(View.VISIBLE);
                etPropiedadesEco.requestFocus();
            } else if (Miscellaneous.GetStr(tvEstadoCivilCli).equals("CASADO(A)") || Miscellaneous.GetStr(tvEstadoCivilCli).equals("UNION LIBRE")) {
                ivDown5.setVisibility(View.GONE);
                ivUp5.setVisibility(View.VISIBLE);
                llDatosConyuge.setVisibility(View.VISIBLE);
                etNombreCony.requestFocus();
            } else {
                ivDown1.setVisibility(View.GONE);
                ivUp1.setVisibility(View.VISIBLE);
                llDatosPersonales.setVisibility(View.VISIBLE);
                etNombreCli.requestFocus();
            }

            ivDown11.setVisibility(View.VISIBLE);
            ivUp11.setVisibility(View.GONE);
            llDatosBeneficiario.setVisibility(View.GONE);
        }
    };
    private View.OnClickListener btnRegresar4_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            ivDown11.setVisibility(View.GONE);
            ivUp11.setVisibility(View.VISIBLE);
            llDatosBeneficiario.setVisibility(View.VISIBLE);
            txtNombreBeneficiario.requestFocus();


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
    private View.OnClickListener btnRegresar10_OnClick = new View.OnClickListener() {
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
    /**
     * Evento para seleccionar la hora de visita del cliente
     */
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

    /**
     * Funcion para validar y guardar los datos de la seccion del credito
     */
    private boolean saveDatosCredito() {
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
                !validator.validate(etObservaciones, new String[]{validator.GENERAL}) &&
                !validator.validate(etMontoRefinanciar, new String[]{validator.REQUIRED}) &&
                !validatorTV.validate(txtCampanaRen, new String[]{validatorTV.REQUIRED}) &&
                !validator.validate(txtNombreRefieroRen, new String[]{validator.REQUIRED})
        ) {
            ivError1.setVisibility(View.GONE);
            ContentValues cv = new ContentValues();
            cv.put("plazo", Miscellaneous.GetStr(tvPlazo));
            cv.put("periodicidad", Miscellaneous.GetStr(tvFrecuencia));
            cv.put("fecha_desembolso", Miscellaneous.GetStr(tvFechaDesembolso));
            cv.put("dia_desembolso", Miscellaneous.GetStr(tvDiaDesembolso));
            cv.put("hora_visita", Miscellaneous.GetStr(tvHoraVisita));
            cv.put("monto_prestamo", Miscellaneous.GetStr(etMontoPrestamo).replace(",", ""));
            cv.put("destino", Miscellaneous.GetStr(tvDestino).toUpperCase());
            cv.put("clasificacion_riesgo", Miscellaneous.GetStr(tvRiesgo));
            cv.put("clasificacion_riesgo", Miscellaneous.GetStr(tvRiesgo));
            cv.put("comportamiento_pago", Miscellaneous.GetStr(tvComportamiento));
            cv.put("observaciones", Miscellaneous.GetStr(etObservaciones));
            cv.put("monto_refinanciar", Miscellaneous.GetStr(etMontoRefinanciar).replace(",", ""));
            cv.put("id_campana", Miscellaneous.selectCampana(ctx, Miscellaneous.GetStr(txtCampanaRen)));

            db.update(TBL_CREDITO_IND_REN, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
            save_credito = true;
        } else
            ivError1.setVisibility(View.VISIBLE);

        return save_credito;
    }

    private boolean saveDatosCampana() {
        boolean save_campana = false;
        ContentValues cv = new ContentValues();

        boolean auxiliar = datosCampanaDao.validarEstatus(ctx, Integer.parseInt(String.valueOf(id_solicitud)));

        if (!validatorTV.validate(txtCampanaRen, new String[]{validator.ONLY_TEXT}) && !validator.validate(txtNombreRefieroRen, new String[]{validator.ONLY_TEXT})) {
            cv.put("id_solicitud", id_solicitud);
            cv.put("id_campana", Miscellaneous.selectCampana(ctx, Miscellaneous.GetStr(txtCampanaRen)));
            cv.put("tipo_campana", Miscellaneous.GetStr(txtCampanaRen));
            cv.put("nombre_refiero", Miscellaneous.GetStr(txtNombreRefieroRen));

            if (!auxiliar) {
                db.insert(TBL_DATOS_CREDITO_CAMPANA, null, cv);
            }

            if (auxiliar) {
                db.update(TBL_DATOS_CREDITO_CAMPANA, cv, " id_solicitud  = ? ", new String[]{String.valueOf(id_solicitud)});
            }
            save_campana = true;
        } else {
            txtCampanaRen.setError("Este campo es requerido");
            txtNombreRefieroRen.setError("Este campo es requerido");
        }
        return save_campana;
    }

    /**
     * Funcion para validar y guardar los datos de la seccion de datos personales
     */
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
                                                    !validator.validate(etEmail, new String[]{validator.EMAIL})) {
                                                /*if (byteFotoFachCli != null) {
                                                    tvFachadaCli.setError(null);*/
                                                //if (!validator.validate(etReferenciCli, new String[]{validator.REQUIRED})) {
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

                                                    db.update(TBL_CLIENTE_IND_REN, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});

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
                                                    db.update(TBL_DIRECCIONES_REN, cv, "id_direccion = ? AND tipo_direccion = ?", new String[]{direccionIdCli, "CLIENTE"});
                                                    save_cliente = true;
                                                } else {
                                                    ivError2.setVisibility(View.VISIBLE);
                                                    tvFirmaCli.setError("");
                                                }
                                                    /*} else
                                                        ivError2.setVisibility(View.VISIBLE);*/
                                                /*} else {
                                                    ivError2.setVisibility(View.VISIBLE);
                                                    tvFachadaCli.setError("");
                                                }*/
                                            } else
                                                ivError2.setVisibility(View.VISIBLE);
                                        } else {
                                            ivError2.setVisibility(View.VISIBLE);
                                            tvMapaCli.setError("");
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

    /**
     * Funcion para validar y guardar los datos de la seccion del conyuge
     */
    private boolean saveConyuge() {
        boolean save_conyuge = false;
        if (
                !validator.validate(etNombreCony, new String[]{validator.REQUIRED, validator.ONLY_TEXT}) &&
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
                        !validator.validate(etCelularCony, new String[]{validator.REQUIRED, validator.PHONE})
        ) {

            etNoExtCony.setError(null);
            etManzanaCony.setError(null);
            etLoteCony.setError(null);
            Log.e("Conyuge", "pasa");
            ivError3.setVisibility(View.GONE);
            ContentValues cv = new ContentValues();
            cv.put("nombre", Miscellaneous.GetStr(etNombreCony));
            cv.put("paterno", Miscellaneous.GetStr(etApPaternoCony));
            cv.put("materno", Miscellaneous.GetStr(etApMaternoCony));
            cv.put("nacionalidad", Miscellaneous.GetStr(etNacionalidadCony));
            cv.put("ocupacion", Miscellaneous.GetStr(tvOcupacionCony));
            cv.put("ing_mensual", Miscellaneous.GetStr(etIngMenCony).replace(",", ""));
            cv.put("gasto_mensual", Miscellaneous.GetStr(etGastoMenCony).replace(",", ""));
            cv.put("tel_casa", Miscellaneous.GetStr(etCasaCony));
            cv.put("tel_celular", Miscellaneous.GetStr(etCelularCony));

            db.update(TBL_CONYUGE_IND_REN, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});

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
            db.update(TBL_DIRECCIONES_REN, cv, "id_direccion = ? AND tipo_direccion = ?", new String[]{direccionIdCony, "CONYUGE"});

            save_conyuge = true;
        } else {
            ivError3.setVisibility(View.VISIBLE);
        }

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

    /**
     * Funcion para validar y guardar los datos de la seccion de datos economicos
     */
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

            db.update(TBL_ECONOMICOS_IND_REN, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});

            save_economicos = true;
        } else
            ivError4.setVisibility(View.VISIBLE);

        return save_economicos;
    }

    /**
     * Funcion para validar y guardar los datos de la seccion del negocio
     */
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
                                !validator.validate(etGastosOtrosNeg, new String[]{validator.REQUIRED, validator.ONLY_NUMBER})
                        ) {
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

                                if (getGastoMen()) {
                                    if (
                                            !validator.validate(etCapacidadPagoNeg, new String[]{validator.REQUIRED, validator.MONEY}) &&
                                                    !validatorTV.validate(tvMediosPagoNeg, new String[]{validatorTV.REQUIRED})
                                    ) {
                                        boolean otro_medio = false;
                                        if (Miscellaneous.GetStr(tvMediosPagoNeg).contains("OTRO")) {
                                            if (!validator.validate(etOtroMedioPagoNeg, new String[]{validator.REQUIRED}))
                                                otro_medio = true;
                                        } else {
                                            otro_medio = true;
                                        }

                                        if (otro_medio) {
                                            if (
                                                    !validatorTV.validate(tvMontoMaxNeg, new String[]{validatorTV.REQUIRED}) &&
                                                            !validatorTV.validate(tvNumOperacionNeg, new String[]{validatorTV.REQUIRED}) &&
                                                            (Miscellaneous.GetStr(tvMediosPagoNeg).contains("EFECTIVO") && !validatorTV.validate(etNumOperacionEfectNeg, new String[]{validatorTV.REQUIRED})) || !Miscellaneous.GetStr(tvMediosPagoNeg).contains("EFECTIVO")
                                            ) {
                                                if (!validatorTV.validate(tvDiasVentaNeg, new String[]{validatorTV.REQUIRED})) {
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

                                                        db.update(TBL_NEGOCIO_IND_REN, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});

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
                                                        db.update(TBL_DIRECCIONES_REN, cv, "id_direccion = ? AND tipo_direccion = ?", new String[]{direccionIdNeg, "NEGOCIO"});

                                                        save_negocio = true;
                                                    } else {
                                                        ivError5.setVisibility(View.VISIBLE);
                                                    }
                                                    /*} else {
                                                        ivError5.setVisibility(View.VISIBLE);
                                                        tvFachadaNeg.setError("");
                                                    }*/
                                                } else {
                                                    ivError5.setVisibility(View.VISIBLE);
                                                }
                                            } else {
                                                ivError5.setVisibility(View.VISIBLE);
                                            }
                                        } else {
                                            ivError5.setVisibility(View.VISIBLE);
                                        }
                                    } else
                                        ivError5.setVisibility(View.VISIBLE);
                                } else {
                                    etGastosMenNeg.setError("La suma de todos los gastos no puede ser cero (0).");
                                    ivError5.setVisibility(View.VISIBLE);
                                }
                            } else {
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

    /**
     * Funcion para validar y guardar los datos de la seccion del aval
     */
    private boolean saveDatosAval() {
        boolean save_aval = false;
        ContentValues cv = new ContentValues();
        if (!validator.validate(etNombreAval, new String[]{validator.REQUIRED, validator.ONLY_TEXT}) &&
                !validator.validate(etApPaternoAval, new String[]{validator.ONLY_TEXT}) &&
                !validator.validate(etApMaternoAval, new String[]{validator.ONLY_TEXT}) &&
                !validatorTV.validate(tvFechaNacAval, new String[]{validatorTV.REQUIRED}) &&
                !validatorTV.validate(tvEdadAval, new String[]{validatorTV.REQUIRED, validatorTV.ONLY_NUMBER})) {
            if (rgGeneroAval.getCheckedRadioButtonId() == R.id.rbHombre ||
                    rgGeneroAval.getCheckedRadioButtonId() == R.id.rbMujer) {
                tvGeneroAval.setError(null);
                if (!validatorTV.validate(tvEstadoNacAval, new String[]{validatorTV.REQUIRED}) &&
                        !validator.validate(etCurpAval, new String[]{validator.REQUIRED, validator.CURP}) &&
                        (!Miscellaneous.GetStr(tvRfcAval).equals("Rfc no válida"))) {
                    if (Miscellaneous.CurpValidador(Miscellaneous.GetStr(etCurpAval))) {
                        etCurpAval.setError(null);
                        if (!validatorTV.validate(tvParentescoAval, new String[]{validatorTV.REQUIRED}) &&
                                !validatorTV.validate(tvTipoIdentificacionAval, new String[]{validatorTV.REQUIRED}) &&
                                !validator.validate(etNumIdentifAval, new String[]{validator.REQUIRED, validator.ALFANUMERICO}) &&
                                !validatorTV.validate(tvOcupacionAval, new String[]{validatorTV.REQUIRED}) &&
                                !validatorTV.validate(tvActividadEcoAval, new String[]{validatorTV.REQUIRED})) {
                            if (latLngUbiAval != null || pushMapButtonAval) {
                                tvMapaAval.setError(null);
                                if (!validator.validate(etCalleAval, new String[]{validator.REQUIRED}) &&
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
                                        !validatorTV.validate(tvTipoCasaAval, new String[]{validatorTV.REQUIRED})) {
                                    cv.put("tipo_vivienda", Miscellaneous.GetStr(tvTipoCasaAval));
                                    boolean flag_tipo_casa = false;
                                    if (Miscellaneous.GetStr(tvTipoCasaAval).equals("CASA FAMILIAR") ||
                                            Miscellaneous.GetStr(tvTipoCasaAval).equals("CASA RENTADA")) {
                                        if (!validatorTV.validate(tvFamiliarAval, new String[]{validatorTV.REQUIRED}) &&
                                                !validator.validate(etNombreTitularAval, new String[]{validator.REQUIRED, validator.ONLY_TEXT})) {
                                            cv.put("nombre_titular", Miscellaneous.GetStr(etNombreTitularAval));
                                            cv.put("parentesco", Miscellaneous.GetStr(tvFamiliarAval));
                                            flag_tipo_casa = true;
                                        } else
                                            ivError6.setVisibility(View.VISIBLE);
                                    } else {
                                        cv.put("nombre_titular", "");
                                        cv.put("parentesco", "");
                                        flag_tipo_casa = true;
                                    }

                                    if (flag_tipo_casa) {
                                        if (!validator.validate(etCaracteristicasAval, new String[]{validator.REQUIRED})) {
                                            if (rgNegocioAval.getCheckedRadioButtonId() == R.id.rbSiNeg ||
                                                    rgNegocioAval.getCheckedRadioButtonId() == R.id.rbNoNeg) {
                                                tvNombreNegAval.setError(null);

                                                if (
                                                        ((rgNegocioAval.getCheckedRadioButtonId() == R.id.rbSiNeg && !validator.validate(etNombreNegocioAval, new String[]{validator.REQUIRED}) && !validator.validate(etAntiguedadAval, new String[]{validator.REQUIRED, validator.YEARS})) || rgNegocioAval.getCheckedRadioButtonId() == R.id.rbNoNeg) &&
                                                                !validator.validate(etIngMenAval, new String[]{validator.REQUIRED, validator.ONLY_NUMBER}) &&
                                                                !validator.validate(etIngOtrosAval, new String[]{validator.REQUIRED, validator.ONLY_NUMBER}) &&
                                                                !validator.validate(etGastosSemAval, new String[]{validator.REQUIRED, validator.ONLY_NUMBER}) &&
                                                                !validator.validate(etGastosAguaAval, new String[]{validator.REQUIRED, validator.ONLY_NUMBER}) &&
                                                                !validator.validate(etGastosLuzAval, new String[]{validator.REQUIRED, validator.ONLY_NUMBER}) &&
                                                                !validator.validate(etGastosTelAval, new String[]{validator.REQUIRED, validator.ONLY_NUMBER}) &&
                                                                !validator.validate(etGastosRentaAval, new String[]{validator.REQUIRED, validator.ONLY_NUMBER}) &&
                                                                !validator.validate(etGastosOtrosAval, new String[]{validator.REQUIRED, validator.ONLY_NUMBER}) &&
                                                                !validatorTV.validate(tvMediosPagoAval, new String[]{validatorTV.REQUIRED}) &&
                                                                (!Miscellaneous.GetStr(tvMediosPagoAval).contains("OTRO") ||
                                                                        !validator.validate(etOtroMedioPagoAval, new String[]{validator.REQUIRED})) &&
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

                                                    /*
                                                    if (byteIneFrontalAval != null) {
                                                        if (byteIneReversoAval != null) {
                                                            if (byteCurpAval != null) {
                                                                if (byteComprobanteAval != null) {

                                                                } else {
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
                                                    }*/

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
                                                                int checkedRadioButtonId = rgGeneroAval.getCheckedRadioButtonId();
                                                                if (checkedRadioButtonId == R.id.rbHombre) {
                                                                    cv.put("genero", 0);
                                                                } else if (checkedRadioButtonId == R.id.rbMujer) {
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
                                                                int radioButtonId = rgNegocioAval.getCheckedRadioButtonId();
                                                                if (radioButtonId == R.id.rbSiNeg) {
                                                                    cv.put("tiene_negocio", 1);
                                                                    cv.put("nombre_negocio", Miscellaneous.GetStr(etNombreNegocioAval));
                                                                } else if (radioButtonId == R.id.rbNoNeg) {
                                                                    cv.put("tiene_negocio", 2);
                                                                }
                                                                cv.put("ing_mensual", Miscellaneous.GetStr(etIngMenAval).replace(",", ""));
                                                                cv.put("ing_otros", Miscellaneous.GetStr(etIngOtrosAval).replace(",", ""));
                                                                cv.put("gasto_semanal", Miscellaneous.GetStr(etGastosSemAval).replace(",", ""));
                                                                cv.put("gasto_agua", Miscellaneous.GetStr(etGastosAguaAval).trim().replace(",", ""));
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

                                                                db.update(TBL_AVAL_IND_REN, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});

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
                                                                db.update(TBL_DIRECCIONES_REN, cv, "id_direccion = ? AND tipo_direccion = ?", new String[]{direccionIdAval, "AVAL"});
                                                                save_aval = true;
                                                            } else {
                                                                tvFirmaAval.setError("");
                                                            }
                                                        } else {
                                                            ivError6.setVisibility(View.VISIBLE);
                                                        }
                                                            /*} else {
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
                        } else
                            ivError6.setVisibility(View.VISIBLE);
                    } else {
                        ivError6.setVisibility(View.VISIBLE);
                        etCurpAval.setError("Curp no válida");
                    }
                } else
                    ivError6.setVisibility(View.VISIBLE);
            } else {
                ivError6.setVisibility(View.VISIBLE);
                tvGeneroAval.setError("");
            }
        } else
            ivError6.setVisibility(View.VISIBLE);

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

    /**
     * Funcion para validar y guardar los datos de la seccion de referencia
     */
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

            db.update(TBL_REFERENCIA_IND_REN, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});

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
            db.update(TBL_DIRECCIONES_REN, cv, "id_direccion = ? AND tipo_direccion = ?", new String[]{direccionIdRef, "REFERENCIA"});

            save_referencia = true;
        } else
            ivError7.setVisibility(View.VISIBLE);

        return save_referencia;
    }

    /**
     * Funcion para validar y guardar los datos de la seccion del croquis
     */
    private boolean saveCroquis() {
        boolean save_croquis = false;
        if (!validatorTV.validate(tvLateraUno, new String[]{validatorTV.REQUIRED, validatorTV.ALFANUMERICO}) &&
                !validatorTV.validate(tvPrincipal, new String[]{validatorTV.REQUIRED, validatorTV.ALFANUMERICO}) &&
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

            db.update(TBL_CROQUIS_IND_REN, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
            save_croquis = true;
        } else
            ivError2.setVisibility(View.GONE);
        //ivError8.setVisibility(View.VISIBLE);
        return save_croquis;
    }

    /**
     * Funcion para validar y guardar los datos de la seccion de politicas PLD
     */
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
                    int checkedRadioButtonId = rgPropietarioReal.getCheckedRadioButtonId();
                    if (checkedRadioButtonId == R.id.rbSiPropietario) {
                        cv.put("propietario_real", 1);
                    } else if (checkedRadioButtonId == R.id.rbNoPropietario) {
                        cv.put("propietario_real", 2);
                    }

                    int radioButtonId = rgProveedor.getCheckedRadioButtonId();
                    if (radioButtonId == R.id.rbSiProveedor) {
                        cv.put("proveedor_recursos", 1);
                    } else if (radioButtonId == R.id.rbNoProveedor) {
                        cv.put("proveedor_recursos", 2);
                    }

                    int buttonId = rgPoliticamenteExp.getCheckedRadioButtonId();
                    if (buttonId == R.id.rbSiExpuesta) {
                        cv.put("persona_politica", 1);
                    } else if (buttonId == R.id.rbNoexpuesta) {
                        cv.put("persona_politica", 2);
                    }

                    db.update(TBL_POLITICAS_PLD_IND_REN, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});

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

    /**
     * Funcion para validar y guardar los datos de la seccion de documentos
     */
    private boolean saveDocumentacion() {

        boolean flagByteIneFrontal = byteIneFrontal != null;
        boolean flagByteIneReverso = byteIneReverso != null;
        boolean flagByteIneSelfie = byteIneSelfie != null;
        boolean faglByteFirmaCli = byteFirmaCli != null;
        boolean flagByteComprobante = byteComprobante != null;

        if (!flagByteIneFrontal) {
            tvIneFrontal.setError("");
        }
        if (!flagByteIneReverso) {
            tvIneReverso.setError("");
        }
        if (!flagByteIneSelfie) {
            tvIneSelfie.setError("");
        }
        if (!flagByteComprobante) {
            tvComprobante.setError("");
        }
        if (!faglByteFirmaCli) {
            tvFirmaCli.setError("");
        }

        if (flagByteIneFrontal && flagByteIneReverso && flagByteIneSelfie && faglByteFirmaCli && flagByteComprobante) {
            ivError9.setVisibility(View.GONE);
            return true;
        } else {
            ivError9.setVisibility(View.VISIBLE);
            return false;
        }

    }

    private boolean saveBeneficiario() {

        ContentValues cv = new ContentValues();

        int grupo_id = 1;

        int id_cliente = BeneficiarioIndRenDao.obtenerClienteIndRen(Integer.parseInt(String.valueOf(id_solicitud)), ctx);

        boolean estatus = BeneficiarioIndRenDao.validarBeneficiarioIndRen(Integer.parseInt(String.valueOf(id_solicitud)), ctx);

        int serieIdA = BeneficiarioIndRenDao.obtenerSerieAsesor(ctx);//Miscellaneous.obtenerSerieAsesor(ctx);

        int id_originacion = BeneficiarioIndRenDao.obtenerIdOriginacionRen(Integer.parseInt(String.valueOf(id_solicitud)), ctx);

        boolean save_beneficiario = false;


        if (!validator.validate(txtNombreBeneficiario, new String[]{validator.REQUIRED})) {
            if (!validator.validate(txtApellidoPaterno, new String[]{validator.ONLY_TEXT})) {
                if (!validator.validate(txtApellidoMaterno, new String[]{validator.ONLY_TEXT})) {
                    if (!validatorTV.validate(txtParentescoBeneficiario, new String[]{validatorTV.REQUIRED})) {

                        cv.put("id_solicitud", id_solicitud);
                        cv.put("id_originacion", id_originacion);
                        cv.put("id_cliente", id_cliente);
                        cv.put("id_grupo", grupo_id);
                        cv.put("nombre", Miscellaneous.GetStr(txtNombreBeneficiario));
                        cv.put("paterno", Miscellaneous.GetStr(txtApellidoPaterno));
                        cv.put("materno", Miscellaneous.GetStr(txtApellidoMaterno));
                        cv.put("parentesco", Miscellaneous.GetStr(txtParentescoBeneficiario));
                        cv.put("serieid", serieIdA);

                        if (!estatus) {
                            db.insert(TBL_DATOS_BENEFICIARIO_REN, null, cv);
                        }

                        if (estatus) {
                            db.update(TBL_DATOS_BENEFICIARIO_REN, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                        }

                        save_beneficiario = true;
                    } else {
                        ivError11.setVisibility(View.VISIBLE);
                        txtParentescoBeneficiario.setError("CAMPO REQUERIDO");

                    }
                } else {
                    ivError11.setVisibility(View.VISIBLE);
                    txtApellidoMaterno.setError("CAMPO REQUERIDO");
                }
            } else {
                ivError11.setVisibility(View.VISIBLE);
                txtApellidoPaterno.setError("CAMPO REQUERIDO");
            }
        } else {
            ivError11.setVisibility(View.VISIBLE);
            txtNombreBeneficiario.setError("CAMPO REQUERIDO");
        }
        return save_beneficiario;
    }

    /**
     * Funcion para mostrar u ocultar el menu
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_enviar_datos, menu);

        if (!is_edit) {
            menu.getItem(MENU_INDEX_ENVIAR).setVisible(is_edit);
        }

        SolicitudRenDao solicitudRenDao = new SolicitudRenDao(ctx);
        SolicitudRen solicitudRen = solicitudRenDao.findByIdSolicitud(Integer.parseInt(String.valueOf(id_solicitud)));

        if (TBmain.getMenu().size() > 1) {
            if (solicitudRen != null && solicitudRen.getEstatus() > 1)
                TBmain.getMenu().getItem(MENU_INDEX_UPDATE_ESTATUS).setVisible(true);
            else
                TBmain.getMenu().getItem(MENU_INDEX_UPDATE_ESTATUS).setVisible(false);
        }

        menu.getItem(MENU_INDEX_DEVMODE).setVisible(modoSuperUsuario);

        return true;
    }

    /**
     * Funcion para las acciones del menu
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == android.R.id.home) {
            finish();
        } else if (itemId == R.id.devmode) {
            enviarJSONObjects();
            //senDataBeneficiarioRen(id_solicitud);
        } else if (itemId == R.id.desbloquear) {
            if (modoSuperUsuario) {
                desactivarModoSuper();
                deshabilitarSolicitud();
            } else activarModoSuper();
        } else if (itemId == R.id.updateEstatus) {
            obtenerEstatusSolicitud();
        } else if (itemId == R.id.enviar) {
            sendSolicitud();
            //senDataBeneficiarioRen(id_solicitud);
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Funcion que recibe todas las respuestas de las acciones de otras vistas como de fotografias, firmas, direcciones...etc
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE_ESTADO_NAC:/**obtiene respuesta a la peticion de estado de nacimiento del cliente*/
                if (resultCode == REQUEST_CODE_ESTADO_NAC) {/**Valida el codigo de respuesta*/
                    if (data != null) {/**Valida que la respuesta tenga informacion*/
                        tvEstadoNacCli.setError(null);
                        /**Coloca el estado que viene de la respuesta*/
                        tvEstadoNacCli.setText(((ModeloCatalogoGral) data.getSerializableExtra(ITEM)).getNombre());
                        /**Crea un map con los datos esenciales para generar la CURP*/
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
                        /**Actualiza la columa del estado de nacimiento*/
                        Update("estado_nacimiento", TBL_CLIENTE_IND_REN, Miscellaneous.GetStr(tvEstadoNacCli));
                    }
                }
                break;
            case REQUEST_CODE_OCUPACION_CLIE:/**Obtiene respuesta a la peticion de ocupacion del cliente*/
                if (resultCode == REQUEST_CODE_OCUPACION_CLIE) {/**Valida el codigo de respuesta*/
                    if (data != null) {/**Valida que la respuesta contenga informacion*/
                        tvOcupacionCli.setError(null);
                        /**Coloca la respuesta en el campo de ocupacion*/
                        tvOcupacionCli.setText(((ModeloCatalogoGral) data.getSerializableExtra(ITEM)).getNombre());
                        /**Busca el sector dependiendo de la ocupacion*/
                        Cursor row = dBhelper.getRecords(SECTORES, " WHERE sector_id = " + (((ModeloCatalogoGral) data.getSerializableExtra(ITEM)).getExtra()) + "", "", null);
                        if (row.getCount() > 0) {
                            row.moveToFirst();
                            tvActividadEcoCli.setText(row.getString(2));
                        }
                        row.close();
                        /**Actualiza las columnas de ocupacion y actividad economica*/
                        Update("ocupacion", TBL_CLIENTE_IND_REN, Miscellaneous.GetStr(tvOcupacionCli));
                        Update("actividad_economica", TBL_CLIENTE_IND_REN, Miscellaneous.GetStr(tvActividadEcoCli));
                    }
                }
                break;
            case REQUEST_CODE_OCUPACION_NEG:/**Obtiene respuesta a la peticion de ocupacion del negocio*/
                if (resultCode == REQUEST_CODE_OCUPACION_NEG) {/**Valida el codigo de respuesta*/
                    if (data != null) {/**Valida que la respuesta tenga informacion*/
                        tvActEcoEspNeg.setError(null);
                        /**Coloca la respuesta en el campo*/
                        tvActEcoEspNeg.setText(((ModeloCatalogoGral) data.getSerializableExtra(ITEM)).getNombre());
                        /**Obtiene el sector dependiente la respuesta selecciona*/
                        Cursor row = dBhelper.getRecords(SECTORES, " WHERE sector_id = " + (((ModeloCatalogoGral) data.getSerializableExtra(ITEM)).getExtra()) + "", "", null);
                        if (row.getCount() > 0) {
                            row.moveToFirst();
                            tvActEconomicaNeg.setText(row.getString(2));
                        }
                        row.close();
                        /**Actualiza las columnas de la tabla de TBL_NEGOCIO_IND_REN*/
                        Update("ocupacion", TBL_NEGOCIO_IND_REN, Miscellaneous.GetStr(tvActEcoEspNeg));
                        Update("actividad_economica", TBL_NEGOCIO_IND_REN, Miscellaneous.GetStr(tvActEconomicaNeg));
                    }
                }
                break;
            case REQUEST_CODE_OCUPACION_CONY:/**Obtiene respuesta a la peticion de ocupacion del conyuge*/
                if (resultCode == REQUEST_CODE_OCUPACION_CONY) {/**Valida el codigo de respuesta*/
                    if (data != null) {/**Valida que ña respuesta tenga informacion*/
                        tvOcupacionCony.setError(null);
                        /**Coloca la respuesta en el campo*/
                        tvOcupacionCony.setText(((ModeloCatalogoGral) data.getSerializableExtra(ITEM)).getNombre());
                        /**Actualiza la columna de ocupacion del conyuge*/
                        Update("ocupacion", TBL_CONYUGE_IND_REN, Miscellaneous.GetStr(tvOcupacionCony));
                    }
                }
                break;
            case REQUEST_CODE_ACTIVIDAD_NEG:/**Obtiene la respuesta a la peticion de la actividad del negocio*/
                if (resultCode == REQUEST_CODE_ACTIVIDAD_NEG) {/**Valida el codigo de la respuesta*/
                    if (data != null) {/**Valida que la respuesta contenga un valor*/
                        tvActEconomicaNeg.setError(null);
                        /**Coloca la respuesta en el campo*/
                        tvActEconomicaNeg.setText(((ModeloCatalogoGral) data.getSerializableExtra(ITEM)).getNombre());
                        /**Actualiza la columna*/
                        Update("actividad_economica", TBL_NEGOCIO_IND_REN, Miscellaneous.GetStr(tvActEconomicaNeg));
                    }
                }
                break;
            case REQUEST_CODE_ESTADO_NAC_AVAL:/**Obtiene la respuesta a la peticion del estado de nacimiento del aval*/
                if (resultCode == REQUEST_CODE_ESTADO_NAC_AVAL) {/**Valida el codigo de respuesta*/
                    if (data != null) {/**Valida que la respuesta contenga un valor*/
                        tvEstadoNacAval.setError(null);
                        /**Coloca la respuesta en el campo*/
                        tvEstadoNacAval.setText(((ModeloCatalogoGral) data.getSerializableExtra(ITEM)).getNombre());
                        /**Se crea un map con los campos necesarios para generar la curp del aval*/
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
                        /**Actualiza la columna del estado de nacimiento del aval*/
                        Update("estado_nacimiento", TBL_AVAL_IND_REN, Miscellaneous.GetStr(tvEstadoNacAval));
                    }
                }
                break;
            case REQUEST_CODE_OCUPACION_AVAL:/**Obtiene la respuesta a la peticion de la ocupacion del aval*/
                if (resultCode == REQUEST_CODE_OCUPACION_AVAL) {/**Valida el codigo de respuesta*/
                    if (data != null) {/**Valida que la respuesta contenga informacion*/
                        tvOcupacionAval.setError(null);
                        /**Coloca la respuesta en el campo*/
                        tvOcupacionAval.setText(((ModeloCatalogoGral) data.getSerializableExtra(ITEM)).getNombre());
                        /**Obtiene el sector dependiendo a la ocupacion seleccionada*/
                        Cursor row = dBhelper.getRecords(SECTORES, " WHERE sector_id = " + (((ModeloCatalogoGral) data.getSerializableExtra(ITEM)).getExtra()) + "", "", null);
                        if (row.getCount() > 0) {
                            row.moveToFirst();
                            tvActividadEcoAval.setError(null);
                            tvActividadEcoAval.setText(row.getString(2));
                            /**Actualiza las columnas*/
                            Update("ocupacion", TBL_AVAL_IND_REN, Miscellaneous.GetStr(tvOcupacionAval));
                            Update("actividad_economica", TBL_AVAL_IND_REN, Miscellaneous.GetStr(tvActividadEcoAval));
                        }
                        row.close();
                    }
                }
                break;
            case REQUEST_CODE_COLONIA_CLIE:/**Obtiene la respuesta a la peticion de la colonia del cliente*/
                if (resultCode == REQUEST_CODE_COLONIA_CLIE) {/**Valida el codigo de respuesta*/
                    if (data != null) {/**Valida que la respuesta contenga un valor*/
                        tvColoniaCli.setError(null);
                        /**Coloca la respuesta en el campo*/
                        tvColoniaCli.setText(((ModeloCatalogoGral) data.getSerializableExtra(ITEM)).getNombre());
                        /**Actualiza la columna de la colonia de la direccion del cliente*/
                        UpdateDireccion("colonia", Miscellaneous.GetStr(tvColoniaCli), direccionIdCli, "CLIENTE");
                    }
                }
                break;

            case REQUEST_CODE_CAMAPANAS:
                if (data != null) {
                    Integer id_campana;
                    ModeloCatalogoGral modeloCatalogoGral = (ModeloCatalogoGral) data.getSerializableExtra(ITEM);
                    if (modeloCatalogoGral != null) {
                        String campanaNombre = modeloCatalogoGral.getNombre();
                        txtCampanaRen.setError(null);
                        txtCampanaRen.setText(campanaNombre);

                        if (campanaNombre.equals("NINGUNO")) {
                            txtNombreRefieroRen.setEnabled(false);
                            txtNombreRefieroRen.setText("SIN REFERENCIA");
                        } else {
                            txtNombreRefieroRen.setEnabled(true);
                            txtNombreRefieroRen.setText("");
                        }

                        ContentValues cv = new ContentValues();
                        id_campana = Miscellaneous.selectCampana(ctx, Miscellaneous.GetStr(txtCampanaRen));
                        cv.put("id_campana", id_campana);
                        db.update(TBL_CREDITO_IND, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                    }
                }
                break;

            case REQUEST_CODE_LOCALIDAD_CLIE:/**Obtiene respuesta a la peticion de localidad del cliente*/
                if (resultCode == REQUEST_CODE_LOCALIDAD_CLIE) {/**Valida el codigo de respuesta*/
                    if (data != null) {/**Valida que la respuesta contenga un valor*/
                        tvLocalidadCli.setError(null);
                        /**Coloca la respuesta en el campo de localidad del cliente*/
                        tvLocalidadCli.setText(((ModeloCatalogoGral) data.getSerializableExtra(ITEM)).getNombre());
                        /**Actualiza la columna de la localidad de direccion del cliente*/
                        UpdateDireccion("localidad", Miscellaneous.GetStr(tvLocalidadCli), direccionIdCli, "CLIENTE");
                    }
                }
                break;
            case REQUEST_CODE_LOCALIDAD_CONY:/**Obtiene la respuesta a la peticion de la localidad del conyuge*/
                if (resultCode == REQUEST_CODE_LOCALIDAD_CONY) {/**Valida el codigo de respuesta*/
                    if (data != null) {/**Valida que la respuesta contenga un valor*/
                        tvLocalidadCony.setError(null);
                        /**Coloca la respuesta en el campo*/
                        tvLocalidadCony.setText(((ModeloCatalogoGral) data.getSerializableExtra(ITEM)).getNombre());
                        /**Actualiza la columna*/
                        UpdateDireccion("localidad", Miscellaneous.GetStr(tvLocalidadCony), direccionIdCony, "CONYUGE");
                    }
                }
                break;
            case REQUEST_CODE_LOCALIDAD_NEG:/**Obtiene la respuesta a la peticion de la localidad del negocio*/
                if (resultCode == REQUEST_CODE_LOCALIDAD_NEG) {/**Valida la respuesta*/
                    if (data != null) {/**Valida que la respuesta contenga un valor*/
                        tvLocalidadNeg.setError(null);
                        /**Coloca la respuesta en el campo*/
                        tvLocalidadNeg.setText(((ModeloCatalogoGral) data.getSerializableExtra(ITEM)).getNombre());
                        /**Actualzia la columna*/
                        UpdateDireccion("localidad", Miscellaneous.GetStr(tvLocalidadNeg), direccionIdNeg, "NEGOCIO");
                    }
                }
                break;
            case REQUEST_CODE_LOCALIDAD_AVAL:/**Obtiene la respuesta a la peticion de la localidad del aval*/
                if (resultCode == REQUEST_CODE_LOCALIDAD_AVAL) {/**Valida el codigo de respuesta*/
                    if (data != null) {/**Valida que la respuesta contenga un valor*/
                        tvLocalidadAval.setError(null);
                        /**Coloca la respuesta en el campo*/
                        tvLocalidadAval.setText(((ModeloCatalogoGral) data.getSerializableExtra(ITEM)).getNombre());
                        /**Actualiza la columna localidad de la tabal de direccion de tipo ava*/
                        UpdateDireccion("localidad", Miscellaneous.GetStr(tvLocalidadAval), direccionIdAval, "AVAL");
                    }
                }
                break;
            case REQUEST_CODE_LOCALIDAD_REF:/**Obtienen la respuesta a la peticion de la localidad de la referencia*/
                if (resultCode == REQUEST_CODE_LOCALIDAD_REF) {/**Valida el codigo de respuesta*/
                    if (data != null) {/**Valida que la respuesta contenga un valor*/
                        tvLocalidadRef.setError(null);
                        /**Coloca la respuesta en el campo*/
                        tvLocalidadRef.setText(((ModeloCatalogoGral) data.getSerializableExtra(ITEM)).getNombre());
                        /**Actualiza la columna de localidad de direccion de la referencia*/
                        UpdateDireccion("localidad", Miscellaneous.GetStr(tvLocalidadRef), direccionIdRef, "REFERENCIA");
                    }
                }
                break;
            case REQUEST_CODE_COLONIA_CONY:/**Obtiene respuesta a la peticion de la colonia del conyuge*/
                if (resultCode == REQUEST_CODE_COLONIA_CONY) {/**Valida el codigo de respuesta*/
                    if (data != null) {/**Valida que la respuesta contenga un valor*/
                        tvColoniaCony.setError(null);
                        /**Coloca la respuesta en el campo*/
                        tvColoniaCony.setText(((ModeloCatalogoGral) data.getSerializableExtra(ITEM)).getNombre());
                        /**Actualiza la columna de la colinia en la tabla de direccion de tipo conyuge*/
                        UpdateDireccion("colonia", Miscellaneous.GetStr(tvColoniaCony), direccionIdCony, "CONYUGE");
                    }
                }
                break;
            case REQUEST_CODE_COLONIA_AVAL:/**Obtiene respuesta a la peticion de colonia del aval*/
                if (resultCode == REQUEST_CODE_COLONIA_AVAL) {/**Valida el codigo de la respuesto */
                    if (data != null) {/**Valida que la respuesta contenga algun valor*/
                        tvColoniaAval.setError(null);
                        /**Coloca la respuesta en el campo*/
                        tvColoniaAval.setText(((ModeloCatalogoGral) data.getSerializableExtra(ITEM)).getNombre());
                        /**Actualiza la columna de la colonia de la direccion de tipo aval */
                        UpdateDireccion("colonia", Miscellaneous.GetStr(tvColoniaAval), direccionIdAval, "AVAL");
                    }
                }
                break;
            case REQUEST_CODE_COLONIA_NEG:/**Obtiene la respuesta a la peticion de la colonia del negocio*/
                if (resultCode == REQUEST_CODE_COLONIA_NEG) {/**Valida el codigo de respuesta*/
                    if (data != null) {/**Valida que la respuesta contenga un valor*/
                        tvColoniaNeg.setError(null);
                        /**Coloca la respuesta en el campo*/
                        tvColoniaNeg.setText(((ModeloCatalogoGral) data.getSerializableExtra(ITEM)).getNombre());
                        /**Actualiza la columna */
                        UpdateDireccion("colonia", Miscellaneous.GetStr(tvColoniaNeg), direccionIdNeg, "NEGOCIO");
                    }
                }
                break;
            case REQUEST_CODE_COLONIA_REF:/**Obtiene la respuesta a la peticion de la colonia de la referencia*/
                if (resultCode == REQUEST_CODE_COLONIA_REF) {/**valida el codigo de respuesta*/
                    if (data != null) {/**valida que la respuesta contengo un valor*/
                        tvColoniaRef.setError(null);
                        /**Coloca la respuesta en el campo*/
                        tvColoniaRef.setText(((ModeloCatalogoGral) data.getSerializableExtra(ITEM)).getNombre());
                        /**Actualiza la columna en al tabla de direcciones de tipo referencia*/
                        UpdateDireccion("colonia", Miscellaneous.GetStr(tvColoniaRef), direccionIdRef, "REFERENCIA");
                    }
                }
                break;
            case REQUEST_CODE_CAMARA_FACHADA_CLI:/**Obtiene la respuesta a la peticion de la fotografia de la fachada del cliente*/
                if (resultCode == Activity.RESULT_OK) {/**Valida el codigo de respuesta*/
                    if (data != null) {/**Valida que la respuesta contenga un valor*/
                        /**Muestra y oculta ciertos campos*/
                        tvFachadaCli.setError(null);
                        ibFotoFachCli.setVisibility(View.GONE);
                        ivFotoFachCli.setVisibility(View.VISIBLE);
                        /**Guarda la respuesta en una variable*/
                        byteFotoFachCli = data.getByteArrayExtra(PICTURE);
                        byteFotoFachCli = Miscellaneous.etiquetasFotoNormales(byteFotoFachCli, ctx);
                        /**Coloca la respuesta(imagen) en el contenedor del ImageView*/
                        Glide.with(ctx).load(byteFotoFachCli).centerCrop().into(ivFotoFachCli);
                        try {
                            /**Actualiza la columna colocando el nombre de la imagen que se guardo en formato JPG*/
                            Update("foto_fachada", TBL_CLIENTE_IND_REN, Miscellaneous.save(byteFotoFachCli, 1));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                break;
            case REQUEST_CODE_CAMARA_FACHADA_NEG:/**Obtiene la respuesta a la peticion de la fotografia a la fachada del negocio*/
                if (resultCode == Activity.RESULT_OK) {/**Valida el codigo de respuesta*/
                    if (data != null) {/**Valida que la respuesta contenga un valor*/
                        /**Muestra y oculta algunos campos*/
                        tvFachadaNeg.setError(null);
                        ibFotoFachNeg.setVisibility(View.GONE);
                        ivFotoFachNeg.setVisibility(View.VISIBLE);
                        /**Guarda la respuesta en una variable*/
                        byteFotoFachNeg = data.getByteArrayExtra(PICTURE);
                        byteFotoFachNeg = Miscellaneous.etiquetasFotoNormales(byteFotoFachNeg, ctx);
                        /**Coloca la respuesta en el contenedor del ImageView*/
                        Glide.with(ctx).load(byteFotoFachNeg).centerCrop().into(ivFotoFachNeg);
                        try {
                            /**Actualiza la columna colocando el nombre de la imagen que se guardo*/
                            Update("foto_fachada", TBL_NEGOCIO_IND_REN, Miscellaneous.save(byteFotoFachNeg, 1));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                break;
            case REQUEST_CODE_CAMARA_FACHADA_AVAL:/**Obtiene respuesta a la peticion de la fotografia de fachada del aval*/
                if (resultCode == Activity.RESULT_OK) {/**Valida el codigo de respuesta*/
                    if (data != null) {/**Valida que la respuesta contenga un valor*/
                        /**Oculta y muestra cierto campos*/
                        tvFachadaAval.setError(null);
                        ibFotoFachAval.setVisibility(View.GONE);
                        ivFotoFachAval.setVisibility(View.VISIBLE);
                        /**Guarda la respuesta en una variable*/
                        byteFotoFachAval = data.getByteArrayExtra(PICTURE);
                        byteFotoFachAval = Miscellaneous.etiquetasFotoNormales(byteFotoFachAval, ctx);
                        /**Coloca la respuesta en el contenedor de ImageView*/
                        Glide.with(ctx).load(byteFotoFachAval).centerCrop().into(ivFotoFachAval);
                        try {
                            /**Actualiza la columna de foto_fachada colocando el nombre de la imagen que se guardo*/
                            Update("foto_fachada", TBL_AVAL_IND_REN, Miscellaneous.save(byteFotoFachAval, 1));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                break;
            case REQUEST_CODE_FIRMA_AVAL:/**Obtiene respuesta a la peticion de firma del aval*/
                if (resultCode == Activity.RESULT_OK) {/**Valid el codigo de respuesta*/
                    if (data != null) {/**Valida que la respuesta contenga un valor*/
                        /**Muestra y oculta ciertos campos*/
                        tvFirmaAval.setError(null);
                        ibFirmaAval.setVisibility(View.GONE);
                        ivFirmaAval.setVisibility(View.VISIBLE);
                        /**Coloca la respuesta en el contenedor del ImageView*/
                        Glide.with(ctx)
                                .load(data.getByteArrayExtra(FIRMA_IMAGE))
                                .into(ivFirmaAval);
                        /**coloca la respuesta en una variable*/
                        byteFirmaAval = data.getByteArrayExtra(FIRMA_IMAGE);
                        try {
                            /**Actualiza la columna y guardando la respuesta (imagen)*/
                            Update("firma", TBL_AVAL_IND_REN, Miscellaneous.save(byteFirmaAval, 3));
                            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                            if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
                                ObtenerUbicacionFirmaAval();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                break;
            case REQUEST_CODE_FIRMA_CLI:/**Obtiene la respuesta a la peticion a la firma del cliente*/
                if (resultCode == Activity.RESULT_OK) {/**Valida el codigo de la respuesta*/
                    if (data != null) {/**Valida que la respuesta contengo algun valor*/
                        /**Muestra y oculta ciertos campos*/
                        tvFirmaCli.setError(null);
                        ibFirmaCli.setVisibility(View.GONE);
                        ivFirmaCli.setVisibility(View.VISIBLE);
                        /**Coloca la respuesta en el contenedor del ImageView*/
                        Glide.with(ctx)
                                .load(data.getByteArrayExtra(FIRMA_IMAGE))
                                .into(ivFirmaCli);
                        /**Coloca la respuesta en una variable*/
                        byteFirmaCli = data.getByteArrayExtra(FIRMA_IMAGE);
                        try {
                            /**Actualiza la columna y guarda la imagen*/
                            Update("firma", TBL_CLIENTE_IND_REN, Miscellaneous.save(byteFirmaCli, 3));
                            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                            if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
                                ObtenerUbicacionFirmaCliente();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                break;
            case REQUEST_CODE_FIRMA_ASESOR:/**Obtiene la respuesta de la peticion de la firma del asesor*/
                if (resultCode == Activity.RESULT_OK) {/**Valida el codigo de respuesta*/
                    if (data != null) {/**Valida que la respuesta contenga algun valor*/
                        /**Oculta y muestra ciertos valores*/
                        tvFirmaAsesor.setError(null);
                        ibFirmaAsesor.setVisibility(View.GONE);
                        ivFirmaAsesor.setVisibility(View.VISIBLE);
                        /**Coloca la respuesta en el contenedor del ImageView*/
                        Glide.with(ctx)
                                .load(data.getByteArrayExtra(FIRMA_IMAGE))
                                .into(ivFirmaAsesor);
                        /**Coloca la respuesta en una variable*/
                        byteFirmaAsesor = data.getByteArrayExtra(FIRMA_IMAGE);
                        try {
                            /**Actualiza la columna y guarda la imagen*/
                            Update("firma_asesor", TBL_DOCUMENTOS_REN, Miscellaneous.save(byteFirmaAsesor, 3));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
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
                            Update("ine_selfie", TBL_DOCUMENTOS_REN, Miscellaneous.save(byteIneSelfie, 4));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                break;
            case REQUEST_CODE_FOTO_INE_FRONTAL:
                if (resultCode == RESULT_SCAN_SUPPRESSED) {
                    if (data != null) {
                        ibIneFrontal.setVisibility(View.GONE);
                        ivIneFrontal.setVisibility(View.VISIBLE);
                        tvIneFrontal.setError(null);
                        Bitmap cardIneFrontal = CardIOActivity.getCapturedCardImage(data);
                        byteIneFrontal = Miscellaneous.etiquetasIne(cardIneFrontal, ctx);
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
                if (resultCode == RESULT_SCAN_SUPPRESSED) {
                    if (data != null) {
                        tvIneReverso.setError(null);

                        Bitmap cardIneReverso = CardIOActivity.getCapturedCardImage(data);
                        byteIneReverso = Miscellaneous.etiquetasIne(cardIneReverso, ctx);
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
                if (resultCode == Activity.RESULT_OK) {
                    if (data != null) {
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
            case REQUEST_CODE_FOTO_COMPROBATE:/**Obtencion de respuesta a la peticion de foto de comprobante*/
                if (resultCode == Activity.RESULT_OK) {/**Validacion de codigo de respuesta*/
                    if (data != null) {/**Valida que la respuesta contengo un valor*/
                        tvComprobante.setError(null);
                        /**Oculta y muestra ciertos campos*/
                        ibComprobante.setVisibility(View.GONE);
                        ivComprobante.setVisibility(View.VISIBLE);
                        /**Coloca la respuesta en una variable*/
                        byteComprobante = data.getByteArrayExtra(PICTURE);
                        byteComprobante = Miscellaneous.etiquetasFotoNormales(byteComprobante, ctx);
                        /**Coloca la respuesta en el contenedor del ImageView*/
                        Glide.with(ctx).load(byteComprobante).centerCrop().into(ivComprobante);
                        try {
                            /**Actualiza la columna con el nombre de la imagen que se guardo*/
                            Update("comprobante", TBL_DOCUMENTOS_REN, Miscellaneous.save(byteComprobante, 4));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
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
                            Update("comprobante_garantia", TBL_DOCUMENTOS_REN, Miscellaneous.save(byteComprobanteGarantia, 4));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                break;
            case REQUEST_CODE_FOTO_INE_FRONTAL_AVAL:
                if (resultCode == RESULT_SCAN_SUPPRESSED) {
                    if (data != null) {
                        tvIneFrontalAval.setError(null);
                        Bitmap cardIneFrontalAval = CardIOActivity.getCapturedCardImage(data);
                        byteIneFrontalAval = Miscellaneous.etiquetasIne(cardIneFrontalAval, ctx);
                        ibIneFrontalAval.setVisibility(View.GONE);
                        ivIneFrontalAval.setVisibility(View.VISIBLE);
                        Glide.with(ctx).load(byteIneFrontalAval).centerCrop().into(ivIneFrontalAval);
                        try {
                            Update("ine_frontal_aval", TBL_DOCUMENTOS_REN, Miscellaneous.save(byteIneFrontalAval, 4));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                break;
            case REQUEST_CODE_FOTO_INE_REVERSO_AVAL:
                if (resultCode == RESULT_SCAN_SUPPRESSED) {
                    if (data != null) {
                        tvIneReversoAval.setError(null);
                        Bitmap cardIneReversoAval = CardIOActivity.getCapturedCardImage(data);
                        byteIneReversoAval = Miscellaneous.etiquetasIne(cardIneReversoAval, ctx);
                        ibIneReversoAval.setVisibility(View.GONE);
                        ivIneReversoAval.setVisibility(View.VISIBLE);
                        Glide.with(ctx).load(byteIneReversoAval).centerCrop().into(ivIneReversoAval);
                        try {
                            Update("ine_reverso_aval", TBL_DOCUMENTOS_REN, Miscellaneous.save(byteIneReversoAval, 4));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
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
                            Update("curp_aval", TBL_DOCUMENTOS_REN, Miscellaneous.save(byteCurpAval, 4));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
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
                            Update("comprobante_aval", TBL_DOCUMENTOS_REN, Miscellaneous.save(byteComprobanteAval, 4));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
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

    /**
     * Funcion que recibe como respuesta la fecha selecciona en el calendario
     */
    public void setDate(String date, String campo) {
        try {
            Date strDate = sdf.parse(date);
            Calendar cal = Calendar.getInstance();
            cal.setTime(strDate);
            switch (campo) {
                case "desembolso":/**Es para el campo de fecha de desembolso**/
                    tvFechaDesembolso.setError(null);
                    tvFechaDesembolso.setText(sdf.format(cal.getTime()));
                    String[] fechaDes = Miscellaneous.GetStr(tvFechaDesembolso).split("-");
                    Calendar c = Calendar.getInstance();

                    c.set(Integer.valueOf(fechaDes[0]), (Integer.valueOf(fechaDes[1]) - 1), Integer.valueOf(fechaDes[2]));
                    int nD = c.get(Calendar.DAY_OF_WEEK);
                    String diaDesembolso = "";
                    /**Valida la fecha que selecciono en el dia de la semana*/
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

                    Update("fecha_desembolso", TBL_CREDITO_IND_REN, Miscellaneous.GetStr(tvFechaDesembolso));
                    Update("dia_desembolso", TBL_CREDITO_IND_REN, Miscellaneous.GetStr(tvDiaDesembolso));
                    break;
                case "fechaNacCli":/**Para el campo de fecha de nacimiento del cliente*/
                    tvFechaNacCli.setError(null);
                    tvFechaNacCli.setText(date);
                    tvEdadCli.setText(Miscellaneous.GetEdad(sdf.format(cal.getTime())));
                    /**Crea un map con los valores necesarios para generar la curp*/
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
                    /**Actualiza las columnas */
                    Update("fecha_nacimiento", TBL_CLIENTE_IND_REN, Miscellaneous.GetStr(tvFechaNacCli));
                    Update("edad", TBL_CLIENTE_IND_REN, Miscellaneous.GetStr(tvEdadCli));
                    etCurpCli.setText(Miscellaneous.GenerarCurp(params));
                    break;
                case "fechaNacAval":/**Para el campo de la fecha de nacimiento del aval*/
                    tvFechaNacAval.setError(null);
                    tvFechaNacAval.setText(date);
                    tvEdadAval.setText(Miscellaneous.GetEdad(sdf.format(cal.getTime())));
                    /**Crea un map con los valores necesarios para generar la curp*/
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

                    /**Actualiza las columnas*/
                    Update("fecha_nacimiento", TBL_AVAL_IND_REN, Miscellaneous.GetStr(tvFechaNacAval));
                    Update("edad", TBL_AVAL_IND_REN, Miscellaneous.GetStr(tvEdadAval));
                    etCurpAval.setText(Miscellaneous.GenerarCurp(paramsAval));
                    break;
                case "fechaNacRef":/**Para el campo de la fecha de nacimiento de la referencia*/
                    tvFechaNacRef.setError(null);
                    tvFechaNacRef.setText(date);
                    /**Actualiza la columna*/
                    Update("fecha_nacimiento", TBL_REFERENCIA_IND_REN, Miscellaneous.GetStr(tvFechaNacRef));
                    break;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * Funcion para obtener la respuesta a la peticion de horas de vistas
     */
    public void setTimer(String timer, String campo) {
        ContentValues cv;
        switch (campo) {
            case "HoraVisita":/**Para el campo de hora de vista*/
                tvHoraVisita.setError(null);
                tvHoraVisita.setText(timer);
                Update("hora_visita", TBL_CREDITO_IND_REN, Miscellaneous.GetStr(tvHoraVisita));
                break;
            case "HoraVisitaAval":/**Para el campo de la hora de vista del aval*/
                tvHoraLocAval.setError(null);
                tvHoraLocAval.setText(timer);
                Update("horario_localizacion", TBL_AVAL_IND_REN, timer);
                break;
        }
    }

    @Override
    public void onComplete(String plazo, String periodicidad) {
        if (plazo.equals("")) finish();

        Log.e("AQUI PLAZO", plazo);
        Log.e("AQUI PERIODICIDAD", periodicidad);

        tvPlazo.setText(plazo);
        tvFrecuencia.setText(periodicidad);

        Update("plazo", TBL_CREDITO_IND_REN, Miscellaneous.GetStr(tvPlazo));
        Update("periodicidad", TBL_CREDITO_IND_REN, Miscellaneous.GetStr(tvFrecuencia));

        int indice = 0;

        for (int i = 0; i < _frecuencia.length; i++) {
            if (_frecuencia[i].equals(tvFrecuencia.getText().toString())) indice = i;
        }

        switch (indice) {
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

    //===================== Listener GPS  =======================================================
    private void findUbicacion(String idDireccion, String tipo) {
        DireccionRenDao direccionDao = new DireccionRenDao(ctx);
        DireccionRen direccion = direccionDao.findByIdDireccion(Long.parseLong(idDireccion));

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
        /**Interfaz para pbtener la ubicacion*/
        locationListener = new MyCurrentListener(new MyCurrentListener.evento() {
            @Override
            public void onComplete(String latitud, String longitud) {
                ibMapCli.setEnabled(true);
                /**Valida si obtuvo la ubicacion*/
                if (!latitud.isEmpty() && !longitud.isEmpty()) {
                    mapCli.setVisibility(View.VISIBLE);
                    /**Actualiza las columnas*/
                    UpdateDireccion("latitud", latitud, direccionIdCli, "CLIENTE");
                    UpdateDireccion("longitud", longitud, direccionIdCli, "CLIENTE");
                    /**Coloca el pin en el mapa*/
                    Ubicacion(Double.parseDouble(latitud), Double.parseDouble(longitud));

                } else {
                    latLngUbiCli = new LatLng(0, 0);
                    /**Actualiza las columnas con vacio*/
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
        /**Valida si cuenta con internet para seleccionar el proveedor de red de lo contrario solo ocupara GPS*/
        if (NetworkStatus.haveNetworkConnection(ctx)) {
            Log.e("Proveedor", "RED");
            provider = LocationManager.NETWORK_PROVIDER;
        } else {
            Log.e("Proveedor", "GPS");
            provider = LocationManager.GPS_PROVIDER;
        }

        locationManager.requestSingleUpdate(provider, locationListener, null);

        /**Se ejecuta el hilo para validar que en un minuto si no encuenta se cancele la peticion a obtecion de ubicacion*/
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
                    //UbicacionAval(Double.parseDouble(latitud), Double.parseDouble(longitud));
                } else {
                    latLngUbiAval = new LatLng(0, 0);
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
        /**Interfaz para obtener la ubicacion actual*/
        locationListener = new MyCurrentListener(new MyCurrentListener.evento() {
            @Override
            public void onComplete(String latitud, String longitud) {

                ibMapNeg.setEnabled(true);
                /**Valida si obtuvo respuesta de la ubicacion*/
                if (!latitud.isEmpty() && !longitud.isEmpty()) {
                    mapNeg.setVisibility(View.VISIBLE);
                    /**Actualiza las columnas de la ubicacion*/
                    UpdateDireccion("latitud", latitud, direccionIdNeg, "NEGOCIO");
                    UpdateDireccion("longitud", longitud, direccionIdNeg, "NEGOCIO");
                    UpdateDireccion("located_at", Miscellaneous.ObtenerFecha(TIMESTAMP), direccionIdNeg, "NEGOCIO");
                    /**funcion para colocar el pin en el mapa*/
                    UbicacionNeg(Double.parseDouble(latitud), Double.parseDouble(longitud));
                } else {
                    /**En caso de no obtener la ubicacion actualiza columnas con vacio*/
                    latLngUbiNeg = new LatLng(0, 0);
                    UpdateDireccion("latitud", "", direccionIdNeg, "NEGOCIO");
                    UpdateDireccion("longitud", "", direccionIdNeg, "NEGOCIO");
                    pbLoadNeg.setVisibility(View.GONE);
                    Toast.makeText(ctx, getResources().getString(R.string.no_ubicacion), Toast.LENGTH_SHORT).show();
                }

                myHandler.removeCallbacksAndMessages(null);

                CancelUbicacion();

            }
        });

        String provider;
        /**Si cuenta con conexion a internet el proveedor de lo contrario seria con el GPS*/
        if (NetworkStatus.haveNetworkConnection(ctx)) {
            Log.e("Proveedor", "RED");
            provider = LocationManager.NETWORK_PROVIDER;
        } else {
            Log.e("Proveedor", "GPS");
            provider = LocationManager.GPS_PROVIDER;
        }
        if (ActivityCompat.checkSelfPermission(ctx, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(ctx, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
        }


        /**Solo hará una peticion para obtener la ubicacion*/
        locationManager.requestSingleUpdate(provider, locationListener, null);

        /**Se ejecuta el hilo para cancelar la peticion de ubicacion en caso de no obtener nada en un minuto*/
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

    /**
     * Funcion para obtener la ubicacion del cliente
     */
    private void ObtenerUbicacion() {
        findUbicacion(direccionIdCli, "CLIENTE");
    }

    /**
     * Funcion para obtener la ubicacion del cliente
     */
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
                Update("latitud", TBL_CLIENTE_IND_REN, latitud);
                Update("longitud", TBL_CLIENTE_IND_REN, longitud);
                Update("located_at", TBL_CLIENTE_IND_REN, Miscellaneous.ObtenerFecha(TIMESTAMP));
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
                Update("latitud", TBL_AVAL_IND_REN, latitud);
                Update("longitud", TBL_AVAL_IND_REN, longitud);
                Update("located_at", TBL_AVAL_IND_REN, Miscellaneous.ObtenerFecha(TIMESTAMP));
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

    //========================================================================================================

    /**
     * Funcion para mostrar mensajes para la capacidad de pago
     */
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

        if (tipo.equals("NEGOCIO") && etCapacidadPagoNeg.isEnabled())
            etCapacidadPagoNeg.setText("");
        else if (tipo.equals("AVAL") && etCapacidadPagoAval.isEnabled())
            etCapacidadPagoAval.setText("");
    }

    /**
     * Funcion para actualizar columnas recibiendo el nombre de la columna, la tabla y el valor
     */
    private void Update(String key, String tabla, String value) {
        Log.e("update", key + ": " + value);
        ContentValues cv = new ContentValues();
        cv.put(key, value);
        db.update(tabla, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});

    }

    /**
     * Funcion para actualizar columnas de direccion recibiendo el nombre de la columna, el valor, el id del registro y el tipo de direccion
     */
    private void UpdateDireccion(String key, String value, String direccion_id, String tipo) {
        Log.e("update", key + ": " + value);
        ContentValues cv = new ContentValues();
        cv.put(key, value);
        db.update(TBL_DIRECCIONES_REN, cv, "id_direccion = ? AND tipo_direccion = ?", new String[]{direccion_id, tipo});

    }

    /**
     * Funcion para recibir como respuesta el nombre de la calle y la posicion
     */
    public void setCalle(String calle, String tipo) {
        switch (tipo) {
            case "PRINCIPAL":
                tvPrincipal.setError(null);
                tvPrincipal.setText(calle);
                Update("calle_principal", TBL_CROQUIS_IND_REN, calle.trim().toUpperCase());
                break;
            case "TRASERA":
                tvTrasera.setError(null);
                tvTrasera.setText(calle);
                Update("calle_trasera", TBL_CROQUIS_IND_REN, calle.trim().toUpperCase());
                break;
            case "LATERAL UNO":
                tvLateraUno.setError(null);
                tvLateraUno.setText(calle);
                Update("lateral_uno", TBL_CROQUIS_IND_REN, calle.trim().toUpperCase());
                break;
            case "LATERAL DOS":
                tvLateraDos.setError(null);
                tvLateraDos.setText(calle);
                Update("lateral_dos", TBL_CROQUIS_IND_REN, calle.trim().toUpperCase());
                break;
        }
    }

    /**
     * Funcion para obtener el monto maximo que puede  tener el cliente con base a los ingresos y gastos
     * que es la suma de los ingresos, menos la suma de los gastos
     */
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
        Update("monto_maximo", TBL_NEGOCIO_IND_REN, Miscellaneous.GetStr(tvMontoMaxNeg).replace(",", ""));

        tvIngresoEco.setText(dfnd.format(ingreso));
        Update("ingreso", TBL_ECONOMICOS_IND_REN, Miscellaneous.GetStr(tvIngresoEco).replace(",", ""));

    }

    /**
     * Funcion para obtener el monto maximo que puede  tener el aval con base a los ingresos y gastos
     * que es la suma de los ingresos, menos la suma de los gastos
     */
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
        Update("monto_maximo", TBL_AVAL_IND_REN, Miscellaneous.GetStr(tvMontoMaxAval).replace(",", ""));
    }

    /**
     * Funcion para mostrar un dialog para seleccionar dias de la semana
     */
    private void showDiasSemana() {
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
                        for (int i = 0; i < selectedItemsDias.size(); i++) {
                            if (i == 0)
                                dias += _dias_semana[selectedItemsDias.get(i)];
                            else
                                dias += ", " + _dias_semana[selectedItemsDias.get(i)];
                        }
                        tvDiasVentaNeg.setError(null);
                        tvDiasVentaNeg.setText(dias);
                        Update("dias_venta", TBL_NEGOCIO_IND_REN, Miscellaneous.GetStr(tvDiasVentaNeg));
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                    }
                }).show();
    }

    /**
     * Funcion para inicializar componentes o incluso si ya hay datos guardados precargalos
     */
    private void initComponents(String idSolicitud) {

        /**Consulta para obtener datos de la solicitud*/
        Cursor row = dBhelper.getRecords(TBL_SOLICITUDES_REN, " WHERE id_solicitud = ?", "", new String[]{idSolicitud});
        row.moveToFirst();

        Log.e("Rows", row.getColumnCount() + "count");
        is_edit = row.getInt(11) <= 1;/**Valida si el estatus esta parcial*/

        row.close();

        /**Precarga el menu para validar si se mostrara el menu o se oculta en caso de que ya este guardada la solicitud*/
        if (!is_edit) {
            cbNegEnDomCli.setEnabled(false);
            invalidateOptionsMenu();
        }

        //id_cliente = row.getString(11);

        //Update("id_originacion", TBL_SOLICITUDES_REN, "22");

        /**Obtiene los datos del credito*/
        row = dBhelper.getRecords(TBL_CREDITO_IND_REN, " WHERE id_solicitud = ?", "", new String[]{idSolicitud});
        row.moveToFirst();
        isEditCre = row.getInt(15) == 0;/**Valida que estatus del credito este en parcial*/

        //Llenado del datos del prestamo
        /**Precarga los datos que hayan sido guardados*/
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

        if (row.getString(16) != null && !row.getString(16).trim().isEmpty())
            etMontoRefinanciar.setText(dfnd.format(row.getInt(16)));
        //etMontoRefinanciar.setEnabled(isEditCre);

        tvCreditoAnterior.setText(dfnd.format(row.getInt(9)));
        tvNumCiclo.setText(row.getString(8));
        tvComportamiento.setText(row.getString(10));
        etObservaciones.setText(row.getString(12));
        etObservaciones.setEnabled(isEditCre);

        txtCampanaRen.setText(Miscellaneous.tipoCampana(ctx, row.getString(17)));

        row.close(); //Cierra dato del credito

        row = dBhelper.getRecords(TBL_DATOS_CREDITO_CAMPANA, " WHERE id_solicitud = ?", " ", new String[]{idSolicitud});

        if (row.getCount() > 0) {
            row.moveToFirst();
            txtNombreRefieroRen.setText(row.getString(5));
            row.close();
        }

        /***Obtiene los datos personales del cliente*/
        //Llenado de datos del cliente
        row = dBhelper.getRecords(TBL_CLIENTE_IND_REN, " WHERE id_solicitud = ?", "", new String[]{idSolicitud});
        row.moveToFirst();
        /**Valida si hay comentarios de rechazo por parte de la administradora*/
        if (!row.getString(36).trim().isEmpty()) {
            llComentCli.setVisibility(View.VISIBLE);
            tvComentAdminCli.setText(row.getString(36).trim().toUpperCase());
        }
        isEditCli = row.getInt(37) == 0;/**Valida el estatus del cliente en parcial*/
        /**Precarga los datos guardado*/
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

        tvRfcCli.setText(row.getString(9));

        etCurpCli.setText(row.getString(10));

        HashMap<Integer, String> paramsCurpConfirm = new HashMap<>();

        paramsCurpConfirm.put(0, Miscellaneous.GetStr(etNombreCli));
        paramsCurpConfirm.put(1, Miscellaneous.GetStr(etApPaternoCli));
        paramsCurpConfirm.put(2, Miscellaneous.GetStr(etApMaternoCli));
        paramsCurpConfirm.put(3, Miscellaneous.GetStr(tvFechaNacCli));
        if (rgGeneroCli.getCheckedRadioButtonId() == R.id.rbHombre) {
            paramsCurpConfirm.put(4, "Hombre");
        } else {
            paramsCurpConfirm.put(4, "Mujer");
        }
        paramsCurpConfirm.put(5, Miscellaneous.GetStr(tvEstadoNacCli));

        String curpConfirm = Miscellaneous.GenerarCurp(paramsCurpConfirm);
        Log.e("AQUI CURP", curpConfirm);

        if (
                etCurpCli.getText().toString().trim().length() == 18
                        && etCurpCli.getText().toString().trim().substring(1, 16).equals(curpConfirm)
        ) {
            etCurpCli.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etCurpCli.setEnabled(false);
            tvFechaNacCli.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            tvEstadoNacCli.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        } else {
            tvFechaNacCli.setOnClickListener(tvFechaNac_OnClick);
            tvEstadoNacCli.setOnClickListener(etEstadoNac_OnClick);
        }

        //etCurpIdCli.setText(row.getString(11)); etCurpIdCli.setEnabled(false);
        tvOcupacionCli.setText(row.getString(12));
        tvActividadEcoCli.setText(row.getString(13));
        tvTipoIdentificacion.setText(row.getString(14));
        etNumIdentifCli.setText(row.getString(15));
        etNumIdentifCli.setEnabled(isEditCli);
        tvEstudiosCli.setText(row.getString(16));
        tvEstadoCivilCli.setText(row.getString(17));

        /**Valida el estado civil del cliente para mostrar u cultar el formulario del conyuge*/
        switch (row.getString(17)) {
            case "CASADO(A)":
                llConyuge.setVisibility(View.VISIBLE);
                llBienes.setVisibility(View.VISIBLE);
                switch (row.getInt(18)) {
                    case 1:
                    case 3:
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
        /**Obtiene los datos de la direccion del cliente para precargar los datos guardados*/
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
        //etCpCli.setEnabled(isEditCli);
        etCpCli.setEnabled(true);
        tvColoniaCli.setText(rowDir.getString(10));
        etCiudadCli.setText(rowDir.getString(11));

        if (!etCiudadCli.getText().toString().trim().isEmpty()) {
            etCiudadCli.setEnabled(isEditCli);
            etCiudadCli.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        } else {
            etCiudadCli.setEnabled(true);
        }

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

        /**Obtiene los datos del conyuge para precargar los datos
         * en caso de que no este casado ni en union libre no se mostrara la seccion del conyuge*/
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
        //etCpCony.setEnabled(isEditCon);
        etCpCony.setEnabled(true);
        tvColoniaCony.setText(rowDir.getString(10));
        etCiudadCony.setText(rowDir.getString(11));

        if (!etCiudadCony.getText().toString().trim().isEmpty()) {
            etCiudadCony.setEnabled(isEditCon);
            etCiudadCony.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        } else {
            etCiudadCony.setEnabled(true);
        }

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

        /** LLENADO DE LOS DATOS BENEFICIARIO */

        //=============================================DATOS DEL BENEFICIARIO==============================================\\

        row = dBhelper.getRecords(TBL_DATOS_BENEFICIARIO_REN, " where id_solicitud = ?", " ", new String[]{idSolicitud});

        if (row.getCount() > 0) {
            row.moveToFirst();

            txtNombreBeneficiario.setText(row.getString(5).trim().toUpperCase());
            txtApellidoPaterno.setText(row.getString(6).trim().toUpperCase());
            txtApellidoMaterno.setText(row.getString(7).trim().toUpperCase());
            txtParentescoBeneficiario.setText(row.getString(8).trim().toUpperCase());

            row.close();
        }

        /**Obtiene los datos economicos para precargarlos, solo cuando el credito es mayor a $29,000
         * de lo contrario esta seccion se oculta*/
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

        /**Obtiene los datos del negocio para precarha¿garlos*/
        //Llenado de datos del negocio
        row = dBhelper.getRecords(TBL_NEGOCIO_IND_REN, " WHERE id_solicitud = ?", "", new String[]{idSolicitud});
        row.moveToFirst();
        if (!row.getString(27).trim().isEmpty()) {
            llComentNeg.setVisibility(View.VISIBLE);
            tvComentAdminNeg.setText(row.getString(27).trim().toUpperCase());
        }

        isEditNeg = row.getInt(26) == 0;
        etNombreNeg.setText(row.getString(2));
        etNombreNeg.setEnabled(isEditNeg);

        /**Obtiene los datos de la direccion del negocio para precargarlos*/
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
        //etCpNeg.setEnabled(isEditNeg);
        etCpNeg.setEnabled(true);
        tvColoniaNeg.setText(rowDir.getString(10));
        etCiudadNeg.setText(rowDir.getString(11));

        if (!etCiudadNeg.getText().toString().trim().isEmpty()) {
            etCiudadNeg.setEnabled(isEditNeg);
            etCiudadNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        } else {
            etCiudadNeg.setEnabled(true);
        }

        tvLocalidadNeg.setText(rowDir.getString(12));
        tvMunicipioNeg.setText(rowDir.getString(13));
        tvEstadoNeg.setText(rowDir.getString(14));
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

        if (Miscellaneous.GetStr(tvMediosPagoNeg).contains("EFECTIVO"))
            llOperacionesEfectivo.setVisibility(View.VISIBLE);
        else
            llOperacionesEfectivo.setVisibility(View.GONE);

        if (!row.getString(20).trim().isEmpty())
            tvMontoMaxNeg.setText(dfnd.format(row.getDouble(20)));
        tvMontoMaxNeg.setEnabled(isEditNeg);
        tvNumOperacionNeg.setText((row.getString(21).isEmpty()) ? "" : row.getString(21));
        tvNumOperacionNeg.setEnabled(isEditNeg);
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

        //ubicado_en_dom_cli
        if (row.getString(28).trim().equals("") || row.getString(28).trim().equals("SI")) {
            if (row.getString(28).trim().equals(""))
                Update("ubicado_en_dom_cli", TBL_NEGOCIO_IND_REN, "SI");
            cbNegEnDomCli.setChecked(true);
        }

        row.close(); // Cierra los datos del negocio


        /**Obtiene los datos del aval para precargarlos*/
        //Llenado de datos del aval
        row = dBhelper.getRecords(TBL_AVAL_IND_REN, " WHERE id_solicitud = ?", "", new String[]{idSolicitud});
        row.moveToFirst();
        if (!row.getString(50).trim().isEmpty()) {
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


        //etCurpIdAval.setText(row.getString(11)); etCurpIdAval.setEnabled(is_edit);
        tvParentescoAval.setText(row.getString(12));
        tvTipoIdentificacionAval.setText(row.getString(13));
        etNumIdentifAval.setText(row.getString(14));
        etNumIdentifAval.setEnabled(isEditAva);
        tvOcupacionAval.setText(row.getString(15));
        tvActividadEcoAval.setText(row.getString(16));

        /**Obtiene los datos de la direccion del aval para precargarlos*/
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
        //etCpAval.setEnabled(isEditAva);
        etCpAval.setEnabled(true);

        tvColoniaAval.setText(rowDir.getString(10));
        etCiudadAval.setText(rowDir.getString(11));

        if (!etCiudadAval.getText().toString().trim().isEmpty()) {
            etCiudadAval.setEnabled(isEditAva);
            etCiudadAval.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        } else {
            etCiudadAval.setEnabled(true);
        }

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


        /**Obtiene los datos de la referencia para precargalos*/
        //Llena datos de referencia
        row = dBhelper.getRecords(TBL_REFERENCIA_IND_REN, " WHERE id_solicitud = ?", "", new String[]{idSolicitud});
        row.moveToFirst();
        if (!row.getString(9).trim().isEmpty()) {
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

        /**Obtiene los datos de la direccion de la referencia para precargarlos*/
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
        //etCpRef.setEnabled(isEditRef);
        etCpRef.setEnabled(true);
        tvColoniaRef.setText(rowDir.getString(10));
        etCiudadRef.setText(rowDir.getString(11));

        if (!etCiudadRef.getText().toString().trim().isEmpty()) {
            etCiudadRef.setEnabled(isEditRef);
            etCiudadRef.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        } else {
            etCiudadRef.setEnabled(true);
        }

        tvLocalidadRef.setText(rowDir.getString(12));
        tvMunicipioRef.setText(rowDir.getString(13));
        tvEstadoRef.setText(rowDir.getString(14));
        rowDir.close(); //Cierra datos de direccion del referencia
        etTelCelRef.setText(row.getString(7));
        etTelCelRef.setEnabled(isEditRef);
        row.close(); //Cierra datos de referencia

        /**Obtiene los datos del croquis para precargarlos*/
        row = dBhelper.getRecords(TBL_CROQUIS_IND_REN, " WHERE id_solicitud = ?", "", new String[]{idSolicitud});
        row.moveToFirst();
        if (!row.getString(8).trim().isEmpty()) {
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
        etCaracteristicasDomicilio.setText(row.getString(9));
        etCaracteristicasDomicilio.setEnabled(isEditCro);
        row.close(); //Cierra datos del croquis

        /**Obtiene los datos de politicas PLD para precargarlo*/
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

        /**Obtiene los datos de la documentacion para precargarla*/
        //Llena la documentacion
        row = dBhelper.getRecords(TBL_DOCUMENTOS_REN, " WHERE id_solicitud = ?", "", new String[]{idSolicitud});
        row.moveToFirst();
        isEditDoc = row.getInt(8) == 0;
        if (!row.getString(2).isEmpty()) {
            File ineFrontalFile = new File(ROOT_PATH + "Documentos/" + row.getString(2));
            Uri uriIneFrontal = Uri.fromFile(ineFrontalFile);
            byteIneFrontal = Miscellaneous.getBytesUri(ctx, uriIneFrontal, 0);
            //Glide.with(ctx).load(uriIneFrontal).into(ivIneFrontal);
            //ibIneFrontal.setVisibility(View.GONE);
            //ivIneFrontal.setVisibility(View.VISIBLE);
        }

        if (!row.getString(3).isEmpty()) {
            File ineReversoFile = new File(ROOT_PATH + "Documentos/" + row.getString(3));
            Uri uriIneReverso = Uri.fromFile(ineReversoFile);
            byteIneReverso = Miscellaneous.getBytesUri(ctx, uriIneReverso, 0);
            //Glide.with(ctx).load(uriIneReverso).into(ivIneReverso);
            //ibIneReverso.setVisibility(View.GONE);
            //ivIneReverso.setVisibility(View.VISIBLE);
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

        if (row.getString(9) != null && !row.getString(9).isEmpty()) {
            File ineSelfieFile = new File(ROOT_PATH + "Documentos/" + row.getString(9));
            Uri uriIneSelfie = Uri.fromFile(ineSelfieFile);
            byteIneSelfie = Miscellaneous.getBytesUri(ctx, uriIneSelfie, 0);
            //Glide.with(ctx).load(uriIneSelfie).into(ivIneSelfie);
            //ibIneSelfie.setVisibility(View.GONE);
            //ivIneSelfie.setVisibility(View.VISIBLE);
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
        final AlertDialog loadingDesbloqueo = Popups.showLoadingDialog(ctx, R.string.please_wait, R.string.loading_info);
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

        tvPlazo.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked_left));
        tvFrecuencia.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked_right));
        tvFechaDesembolso.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        tvHoraVisita.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        etMontoPrestamo.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        tvDestino.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        tvRiesgo.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        tvComportamiento.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        etObservaciones.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        txtCampanaRen.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        txtNombreRefieroRen.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));

        isEditCli = true;
        isEditCon = true;
        isEditEco = true;
        isEditNeg = true;
        isEditAva = true;
        isEditRef = true;
        isEditPol = true;
        isEditCro = true;
        isEditBen = true;

        if (isEditBen) {
            txtNombreBeneficiario.setEnabled(true);
            txtApellidoPaterno.setEnabled(true);
            txtApellidoMaterno.setEnabled(true);
            txtParentescoBeneficiario.setEnabled(true);
        }

        if (isEditCli) {
            etObservaciones.setEnabled(isEditCli);
            etObservaciones.setBackground(ContextCompat.getDrawable(ctx, R.drawable.et_rounded_edges));
        }

        if (isEditCli) {
            etNombreCli.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etApPaternoCli.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etApMaternoCli.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));

            for (int i = 0; i < rgGeneroCli.getChildCount(); i++) {
                ((RadioButton) rgGeneroCli.getChildAt(i)).setEnabled(true);
            }

            //etCurpCli.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            tvOcupacionCli.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            tvTipoIdentificacion.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etNumIdentifCli.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            tvEstudiosCli.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            tvEstadoCivilCli.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            for (int i = 0; i < rgBienes.getChildCount(); i++) {
                ((RadioButton) rgBienes.getChildAt(i)).setEnabled(true);
            }
            tvTipoCasaCli.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            tvCasaFamiliar.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etOTroTipoCli.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etCalleCli.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etNoExtCli.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etNoIntCli.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etManzanaCli.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etLoteCli.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            //etCpCli.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));

            if (!tvColoniaCli.getText().toString().trim().isEmpty()) {
                tvColoniaCli.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            }

            if (!tvLocalidadCli.getText().toString().trim().isEmpty()) {
                tvLocalidadCli.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            }

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
            ibFotoFachCli.setEnabled(true);
            ibFotoFachCli.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            ibMapCli.setVisibility(View.VISIBLE);
        }

        /**Valida si la seccion del conyuge ya fue guardada para bloquear los campos*/
        if (isEditCon) {
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
            ///etCpCony.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));

            if (!tvColoniaCony.getText().toString().trim().isEmpty()) {
                tvColoniaCony.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            }

            //etCiudadCony.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));

            if (!tvLocalidadCony.getText().toString().trim().isEmpty()) {
                tvLocalidadCony.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            }

            etIngMenCony.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etGastoMenCony.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etCasaCony.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etCelularCony.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        }

        txtNombreBeneficiario.setBackground(ContextCompat.getDrawable(ctx, R.drawable.et_rounded_edges));
        txtApellidoPaterno.setBackground(ContextCompat.getDrawable(ctx, R.drawable.et_rounded_edges));
        txtApellidoMaterno.setBackground(ContextCompat.getDrawable(ctx, R.drawable.et_rounded_edges));
        txtParentescoBeneficiario.setBackground(ContextCompat.getDrawable(ctx, R.drawable.et_rounded_edges));


        /**Valida si la seccion de datos economicos ya fue guardada para bloquear los campos*/
        if (isEditEco) {
            etPropiedadesEco.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etValorAproxEco.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etUbicacionEco.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            tvIngresoEco.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        }

        /**Valida si la seccion del negocio ya fue guardada para bloquear los campos*/
        if (isEditNeg) {
            cbNegEnDomCli.setEnabled(true);
            etNombreNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etCalleNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etNoExtNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etNoIntNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etManzanaNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etLoteNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            //etCpNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));

            if (!tvColoniaNeg.getText().toString().trim().isEmpty()) {
                tvColoniaNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            }

            if (!tvLocalidadNeg.getText().toString().trim().isEmpty()) {
                tvLocalidadNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            }

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
            ibFotoFachNeg.setEnabled(true);
            ibFotoFachNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            ibMapNeg.setVisibility(View.VISIBLE);
        }

        /**Valida si la seccion del aval ya fue guardada para bloquear los campos*/
        if (isEditAva) {
            etNombreAval.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etApPaternoAval.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etApMaternoAval.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etNombreAval.setEnabled(true);
            etApPaternoAval.setEnabled(true);
            etApMaternoAval.setEnabled(true);

            for (int i = 0; i < rgGeneroAval.getChildCount(); i++) {
                ((RadioButton) rgGeneroAval.getChildAt(i)).setEnabled(true);
            }

            HashMap<Integer, String> paramsCurpAvalConfirm = new HashMap<>();

            paramsCurpAvalConfirm.put(0, Miscellaneous.GetStr(etNombreAval));
            paramsCurpAvalConfirm.put(1, Miscellaneous.GetStr(etApPaternoAval));
            paramsCurpAvalConfirm.put(2, Miscellaneous.GetStr(etApMaternoAval));
            paramsCurpAvalConfirm.put(3, Miscellaneous.GetStr(tvFechaNacAval));

            if (rgGeneroAval.getCheckedRadioButtonId() == R.id.rbHombre) {
                paramsCurpAvalConfirm.put(4, "Hombre");
            } else {
                paramsCurpAvalConfirm.put(4, "Mujer");
            }
            paramsCurpAvalConfirm.put(5, Miscellaneous.GetStr(tvEstadoNacAval));

            String curpAvalConfirm = Miscellaneous.GenerarCurp(paramsCurpAvalConfirm);
            Log.e("AQUI CURP", curpAvalConfirm);

            if (
                    etCurpAval.getText().toString().trim().length() == 18
                            && etCurpAval.getText().toString().trim().substring(1, 16).equals(curpAvalConfirm)
            ) {
                etCurpAval.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
                etCurpAval.setEnabled(true);
                tvFechaNacAval.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
                tvEstadoNacAval.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            } else {
                tvFechaNacAval.setOnClickListener(tvFechaNacAval_OnClick);
                tvEstadoNacAval.setOnClickListener(tvEstadoNacAval_OnClick);
            }

            tvParentescoAval.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            tvTipoIdentificacionAval.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etNumIdentifAval.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            tvOcupacionAval.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etCalleAval.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etNoExtAval.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etNoIntAval.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etManzanaAval.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etLoteAval.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            //etCpAval.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            //tvColoniaAval.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));

            if (!tvColoniaAval.getText().toString().trim().isEmpty()) {
                tvColoniaAval.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            }

            if (!tvLocalidadAval.getText().toString().trim().isEmpty()) {
                tvLocalidadAval.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            }

            tvTipoCasaAval.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            tvFamiliarAval.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etNombreTitularAval.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etCaracteristicasAval.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            for (int i = 0; i < rgNegocioAval.getChildCount(); i++) {
                ((RadioButton) rgNegocioAval.getChildAt(i)).setEnabled(true);
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
            ibFotoFachAval.setEnabled(true);
            ibFotoFachAval.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            ibMapAval.setVisibility(View.VISIBLE);
            tvFechaNacAval.setOnClickListener(tvFechaNacAval_OnClick);
            tvEstadoNacAval.setOnClickListener(tvEstadoNacAval_OnClick);
        }

        /**Valida si la seccion de la referencia ya fue guardada para bloquear los campos*/
        if (isEditRef) {
            etNombreRef.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etApPaternoRef.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etApMaternoRef.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            tvFechaNacRef.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etCalleRef.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etNoExtRef.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etNoIntRef.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etLoteRef.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etManzanaRef.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            //etCpRef.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));

            if (!tvLocalidadRef.getText().toString().trim().isEmpty()) {
                tvLocalidadRef.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            }

            etTelCelRef.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        }

        /**Valida si la seccion del croquis ya fue guardada para bloquear los campos*/
        if (isEditCro) {
            etReferencia.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etCaracteristicasDomicilio.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etReferencia.setEnabled(true);
            etCaracteristicasDomicilio.setEnabled(true);
        }

        /**Valida si la seccion de politicas PLD ya fue guardada para bloquear los campos*/
        if (isEditPol) {
            for (int i = 0; i < rgPropietarioReal.getChildCount(); i++) {
                ((RadioButton) rgPropietarioReal.getChildAt(i)).setEnabled(true);
            }
            for (int i = 0; i < rgProveedor.getChildCount(); i++) {
                ((RadioButton) rgProveedor.getChildAt(i)).setEnabled(true);
            }
            for (int i = 0; i < rgPoliticamenteExp.getChildCount(); i++) {
                ((RadioButton) rgPoliticamenteExp.getChildAt(i)).setEnabled(true);
            }
        }
    }

    private void deshabilitarCampos() {
        SolicitudRenDao solicitudRenDao = new SolicitudRenDao(ctx);
        SolicitudRen solicitudRen = solicitudRenDao.findByIdSolicitud(Integer.parseInt(String.valueOf(id_solicitud)));
        if (solicitudRen != null && solicitudRen.getEstatus() > 1)
            if (TBmain.getMenu().size() > 1)
                TBmain.getMenu().getItem(MENU_INDEX_UPDATE_ESTATUS).setVisible(true);
            else if (TBmain.getMenu().size() > 1)
                TBmain.getMenu().getItem(MENU_INDEX_UPDATE_ESTATUS).setVisible(false);

        /**Valida si la seccion del credito ya fue guardada para bloquear los campos*/
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
            txtCampanaRen.setEnabled(isEditCre);
            txtCampanaRen.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            txtNombreRefieroRen.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            txtNombreRefieroRen.setEnabled(isEditCre);
            //etMontoRefinanciar.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        }

        if (isEditCli) {
            etObservaciones.setEnabled(isEditCli);
            etObservaciones.setBackground(ContextCompat.getDrawable(ctx, R.drawable.et_rounded_edges));
        }

        /**Valida si la seccion de datos personales ya fue guardada para bloquear los campos*/
        if (!isEditCli) {
            etNombreCli.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etApPaternoCli.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etApMaternoCli.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));

            etNombreAval.setEnabled(false);
            etApPaternoAval.setEnabled(false);
            etApMaternoAval.setEnabled(false);

            for (int i = 0; i < rgGeneroCli.getChildCount(); i++) {
                ((RadioButton) rgGeneroCli.getChildAt(i)).setEnabled(false);
            }

            //etCurpCli.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
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
            //etCpCli.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));

            if (!tvColoniaCli.getText().toString().trim().isEmpty()) {
                tvColoniaCli.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            }

            if (!tvLocalidadCli.getText().toString().trim().isEmpty()) {
                tvLocalidadCli.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            }

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
        }

        /**Valida si la seccion del conyuge ya fue guardada para bloquear los campos*/
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
            ///etCpCony.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));

            if (!tvColoniaCony.getText().toString().trim().isEmpty()) {
                tvColoniaCony.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            }

            //etCiudadCony.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));

            if (!tvLocalidadCony.getText().toString().trim().isEmpty()) {
                tvLocalidadCony.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            }

            etIngMenCony.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etGastoMenCony.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etCasaCony.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etCelularCony.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        }

        /**Valida si la seccion de datos economicos ya fue guardada para bloquear los campos*/
        if (!isEditEco) {
            etPropiedadesEco.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etValorAproxEco.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etUbicacionEco.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            tvIngresoEco.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        }


        /**Valida si la seccion del negocio ya fue guardada para bloquear los campos*/
        if (!isEditNeg) {
            cbNegEnDomCli.setEnabled(false);
            etNombreNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etCalleNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etNoExtNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etNoIntNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etManzanaNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etLoteNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            //etCpNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));

            if (!tvColoniaNeg.getText().toString().trim().isEmpty()) {
                tvColoniaNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            }

            if (!tvLocalidadNeg.getText().toString().trim().isEmpty()) {
                tvLocalidadNeg.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            }

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
        }

        /**Valida si la seccion del aval ya fue guardada para bloquear los campos*/
        if (!isEditAva) {
            etNombreAval.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etApPaternoAval.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etApMaternoAval.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));

            for (int i = 0; i < rgGeneroAval.getChildCount(); i++) {
                ((RadioButton) rgGeneroAval.getChildAt(i)).setEnabled(false);
            }

            HashMap<Integer, String> paramsCurpAvalConfirm = new HashMap<>();

            paramsCurpAvalConfirm.put(0, Miscellaneous.GetStr(etNombreAval));
            paramsCurpAvalConfirm.put(1, Miscellaneous.GetStr(etApPaternoAval));
            paramsCurpAvalConfirm.put(2, Miscellaneous.GetStr(etApMaternoAval));
            paramsCurpAvalConfirm.put(3, Miscellaneous.GetStr(tvFechaNacAval));

            if (rgGeneroAval.getCheckedRadioButtonId() == R.id.rbHombre) {
                paramsCurpAvalConfirm.put(4, "Hombre");
            } else {
                paramsCurpAvalConfirm.put(4, "Mujer");
            }
            paramsCurpAvalConfirm.put(5, Miscellaneous.GetStr(tvEstadoNacAval));

            String curpAvalConfirm = Miscellaneous.GenerarCurp(paramsCurpAvalConfirm);
            Log.e("AQUI CURP", curpAvalConfirm);

            if (
                    etCurpAval.getText().toString().trim().length() == 18
                            && etCurpAval.getText().toString().trim().substring(1, 16).equals(curpAvalConfirm)
            ) {
                etCurpAval.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
                etCurpAval.setEnabled(false);
                tvFechaNacAval.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
                tvEstadoNacAval.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            } else {
                tvFechaNacAval.setOnClickListener(tvFechaNacAval_OnClick);
                tvEstadoNacAval.setOnClickListener(tvEstadoNacAval_OnClick);
            }

            tvParentescoAval.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            tvTipoIdentificacionAval.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etNumIdentifAval.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            tvOcupacionAval.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etCalleAval.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etNoExtAval.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etNoIntAval.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etManzanaAval.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etLoteAval.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            //etCpAval.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            //tvColoniaAval.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));

            if (!tvColoniaAval.getText().toString().trim().isEmpty()) {
                tvColoniaAval.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            }

            if (!tvLocalidadAval.getText().toString().trim().isEmpty()) {
                tvLocalidadAval.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            }

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
            ibFotoFachAval.setEnabled(false);
            ibFotoFachAval.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            ibMapAval.setVisibility(View.GONE);
        } else {
            tvFechaNacAval.setOnClickListener(tvFechaNacAval_OnClick);
            tvEstadoNacAval.setOnClickListener(tvEstadoNacAval_OnClick);
        }

        /**Valida si la seccion de la referencia ya fue guardada para bloquear los campos*/
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
            //etCpRef.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));

            if (!tvLocalidadRef.getText().toString().trim().isEmpty()) {
                tvLocalidadRef.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            }

            etTelCelRef.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        }

        /**Valida si la seccion del croquis ya fue guardada para bloquear los campos*/
        if (!isEditCro) {
            etReferencia.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
            etCaracteristicasDomicilio.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        }

        /**Valida si la seccion de politicas PLD ya fue guardada para bloquear los campos*/
        if (!isEditPol) {
            for (int i = 0; i < rgPropietarioReal.getChildCount(); i++) {
                ((RadioButton) rgPropietarioReal.getChildAt(i)).setEnabled(false);
            }
            for (int i = 0; i < rgProveedor.getChildCount(); i++) {
                ((RadioButton) rgProveedor.getChildAt(i)).setEnabled(false);
            }
            for (int i = 0; i < rgPoliticamenteExp.getChildCount(); i++) {
                ((RadioButton) rgPoliticamenteExp.getChildAt(i)).setEnabled(false);
            }
        }
    }

    private boolean habilitarSolicitud() {
        boolean solicitudActivada = false;

        if (id_solicitud <= 0) return solicitudActivada;

        SolicitudRenDao solicitudRenDao = new SolicitudRenDao(ctx);
        SolicitudRen solicitudRen = solicitudRenDao.findByIdSolicitud(Integer.parseInt(String.valueOf(id_solicitud)));

        if (solicitudRen != null) {
            CreditoIndRenDao creditoIndRenDao = new CreditoIndRenDao(ctx);
            CreditoIndRen creditoIndRen = creditoIndRenDao.findByIdSolicitud(solicitudRen.getIdSolicitud());

            if (creditoIndRen != null) {
                solicitudRen.setEstatus(0);
                solicitudRenDao.updateEstatus(solicitudRen);
                solicitudActivada = true;
            }
        }
        return solicitudActivada;
    }

    private boolean deshabilitarSolicitud() {
        TBmain.getMenu().getItem(MENU_INDEX_ENVIAR).setVisible(false);

        boolean solicitudBloqueada = false;

        if (id_solicitud <= 0) return solicitudBloqueada;

        SolicitudRenDao solicitudRenDao = new SolicitudRenDao(ctx);
        SolicitudRen solicitudRen = solicitudRenDao.findByIdSolicitud(Integer.parseInt(String.valueOf(id_solicitud)));

        if (solicitudRen != null) {
            CreditoIndRenDao creditoIndRenDao = new CreditoIndRenDao(ctx);
            CreditoIndRen creditoIndRen = creditoIndRenDao.findByIdSolicitud(solicitudRen.getIdSolicitud());

            if (creditoIndRen != null) {
                solicitudRen.setEstatus(2);
                solicitudRenDao.updateEstatus(solicitudRen);
                solicitudBloqueada = true;
            }
        }
        return solicitudBloqueada;
    }

    private void enviarJSONObjects() {
        JSONObject json_solicitud = new JSONObject();
        SolicitudRenDao solicitudRenDao = new SolicitudRenDao(ctx);
        SolicitudRen solicitudRen = solicitudRenDao.findByIdSolicitud(Integer.parseInt(String.valueOf(id_solicitud)));

        if (solicitudRen != null) {
            try {
                json_solicitud.put(SolicitudRen.TBL, solicitudRen);

                CreditoIndRenDao creditoIndRenDao = new CreditoIndRenDao(ctx);
                CreditoIndRen creditoIndRen = creditoIndRenDao.findByIdSolicitud(solicitudRen.getIdSolicitud());
                if (creditoIndRen != null) json_solicitud.put(CreditoIndRen.TBL, creditoIndRen);

                ClienteRenDao clienteRenDao = new ClienteRenDao(ctx);
                ClienteRen clienteRen = clienteRenDao.findByIdSolicitud(solicitudRen.getIdSolicitud());
                if (clienteRen != null) json_solicitud.put(ClienteRen.TBL, clienteRen);

                DireccionRenDao direccionRenDao = new DireccionRenDao(ctx);
                DireccionRen direccionClienteRen = direccionRenDao.findByIdDireccion(Long.parseLong(clienteRen.getDireccionId()));
                if (direccionClienteRen != null)
                    json_solicitud.put(DireccionRen.TBL + "_cliente", direccionClienteRen);

                ConyugueRenDao conyugueRenDao = new ConyugueRenDao(ctx);
                ConyugueRen conyugueRen = conyugueRenDao.findByIdSolicitud(solicitudRen.getIdSolicitud());
                if (conyugueRen != null) {
                    json_solicitud.put(ConyugueRen.TBL, conyugueRen);
                    DireccionRen direccionConyugueRen = direccionRenDao.findByIdDireccion(Long.parseLong(conyugueRen.getDireccionId()));
                    if (direccionConyugueRen != null)
                        json_solicitud.put(DireccionRen.TBL + "_conyugue", direccionConyugueRen);
                }

                AvalRenDao avalRenDao = new AvalRenDao(ctx);
                AvalRen avalRen = avalRenDao.findByIdSolicitud(solicitudRen.getIdSolicitud());
                if (avalRen != null) {
                    json_solicitud.put(AvalRen.TBL, avalRen);
                    DireccionRen direccionAvalRen = direccionRenDao.findByIdDireccion(Long.parseLong(avalRen.getDireccionId()));
                    if (direccionAvalRen != null)
                        json_solicitud.put(DireccionRen.TBL + "_aval", direccionAvalRen);
                }

                DocumentoRenDao documentoRenDao = new DocumentoRenDao(ctx);
                DocumentoRen documentoRen = documentoRenDao.findByIdSolicitud(solicitudRen.getIdSolicitud());
                if (documentoRen != null) json_solicitud.put(DocumentoRen.TBL, documentoRen);

                EconomicoRenDao economicoRenDao = new EconomicoRenDao(ctx);
                EconomicoRen economicoRen = economicoRenDao.findByIdSolicitud(solicitudRen.getIdSolicitud());
                if (economicoRen != null) json_solicitud.put(EconomicoRen.TBL, economicoRen);

                NegocioRenDao negocioRenDao = new NegocioRenDao(ctx);
                NegocioRen negocioRen = negocioRenDao.findByIdSolicitud(solicitudRen.getIdSolicitud());
                if (negocioRen != null) {
                    json_solicitud.put(NegocioRen.TBL, negocioRen);

                    DireccionRen direccionNegocioRen = direccionRenDao.findByIdDireccion(Long.parseLong(negocioRen.getDireccionId()));
                    if (direccionNegocioRen != null)
                        json_solicitud.put(DireccionRen.TBL + "_negocio", direccionNegocioRen);
                }

                PoliticaPldRenDao politicaPldRenDao = new PoliticaPldRenDao(ctx);
                PoliticaPldRen politicaPldRen = politicaPldRenDao.findByIdSolicitud(solicitudRen.getIdSolicitud());
                if (politicaPldRen != null) json_solicitud.put(PoliticaPldRen.TBL, politicaPldRen);

                ReferenciaRenDao referenciaRenDao = new ReferenciaRenDao(ctx);
                ReferenciaRen referenciaRen = referenciaRenDao.findByIdSolicitud(solicitudRen.getIdSolicitud());
                if (referenciaRen != null) {
                    json_solicitud.put(ReferenciaRen.TBL, referenciaRen);

                    DireccionRen direccionReferenciaRen = direccionRenDao.findByIdDireccion(Long.parseLong(referenciaRen.getDireccionId()));
                    if (direccionReferenciaRen != null)
                        json_solicitud.put(DireccionRen.TBL + "_referencia", direccionReferenciaRen);
                }


            } catch (Exception e) {
                Toast.makeText(ctx, e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            final AlertDialog loadingSendData = Popups.showLoadingDialog(ctx, R.string.please_wait, R.string.loading_info);
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
            /**Actualiza los estatus a completado de las tablas a completado*/
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

            db.update(TBL_SOLICITUDES_REN, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});

            //PRUEBAS PARA NUEVA VERSION
            dialog_sending_solicitud_individual dialogSendSI = new dialog_sending_solicitud_individual();
            Bundle b = new Bundle();
            b.putLong(ID_SOLICITUD, id_solicitud);
            b.putBoolean(ES_RENOVACION, true);
            dialogSendSI.setArguments(b);
            dialogSendSI.setCancelable(false);
            dialogSendSI.show(getSupportFragmentManager(), NameFragments.DIALOGSENDINGSOLICITUDINDIVIDUAL);
        }
    }

    private boolean esSolicitudValida() {
        boolean flag = false;

        boolean credito, cliente, campana, beneficiario, conyuge, economicos, negocio, aval, referencia, croquis, politicas, documentacion;
        credito = saveDatosCredito();
        campana = saveDatosCampana();
        croquis = saveCroquis();
        cliente = saveDatosPersonales();
        if (Miscellaneous.GetStr(tvEstadoCivilCli).equals("CASADO(A)") ||
                Miscellaneous.GetStr(tvEstadoCivilCli).equals("UNION LIBRE"))
            conyuge = saveConyuge();
        else
            conyuge = true;
        if (!Miscellaneous.GetStr(etMontoPrestamo).replace(",", "").isEmpty() && Integer.parseInt(Miscellaneous.GetStr(etMontoPrestamo).replace(",", "")) >= 30000)
            economicos = saveDatosEconomicos();
        else
            economicos = true;

        beneficiario = saveBeneficiario();
        negocio = saveDatosNegocio();
        aval = saveDatosAval();
        referencia = saveReferencia();
        politicas = savePoliticas();
        documentacion = saveDocumentacion();

        if (credito && campana && cliente && beneficiario && conyuge && economicos && negocio && aval && referencia && croquis && politicas && documentacion) {
            flag = true;
        } else {
            final AlertDialog solicitud = Popups.showDialogMessage(this, warning,
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
        final AlertDialog loadingEstatus = Popups.showLoadingDialog(ctx, R.string.please_wait, R.string.loading_info);
        loadingEstatus.show();

        SolicitudRenDao solicitudRenDao = new SolicitudRenDao(ctx);
        SolicitudRen solicitudRen = solicitudRenDao.findByIdSolicitud(Integer.parseInt(String.valueOf(id_solicitud)));

        if (solicitudRen != null && solicitudRen.getEstatus() > 1) {
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
                                if (se.getTipoSolicitud() != 1 && Integer.compare(se.getId(), solicitudRen.getIdOriginacion()) == 0) {
                                    ClienteRenDao clienteDao = new ClienteRenDao(ctx);
                                    SolicitudRenDao solicitudDao = new SolicitudRenDao(ctx);

                                    ClienteRen cliente = null;
                                    SolicitudRen solicitudTemp = solicitudDao.findByIdOriginacion(se.getId());

                                    if (solicitudTemp != null)
                                        cliente = clienteDao.findByIdSolicitud(solicitudTemp.getIdSolicitud());

                                    if (cliente != null) {
                                        String comentario = "";

                                        if (se.getSolicitudEstadoId() == 1) {
                                            comentario = "EN REVISIÓN";
                                            solicitudTemp.setEstatus(2);
                                        } else if (se.getSolicitudEstadoId() == 3) {
                                            comentario = "VALIDADO";
                                            solicitudTemp.setEstatus(3);
                                        } else {
                                            //comentario = cliente.getComentarioRechazo();
                                            //solicitud.setEstatus(3);
                                        }

                                        if (se.getSolicitudEstadoId() == 2)
                                            solicitudTemp.setEstatus(5);

                                        cliente.setComentarioRechazo(comentario);
                                        clienteDao.updateEstatus(cliente);

                                        solicitudDao.updateEstatus(solicitudTemp);
                                    }
                                }
                            }
                            obtenerRechazo(loadingEstatus, solicitudRen);
                            break;
                        default:
                            obtenerRechazo(loadingEstatus, solicitudRen);
                            Log.e("AQUI ", response.message());
                            break;
                    }
                }

                @Override
                public void onFailure(Call<List<SolicitudDetalleEstatusInd>> call, Throwable t) {
                    obtenerRechazo(loadingEstatus, solicitudRen);
                    Log.e("Error", "failAGF" + t.getMessage());
                }
            });
        }
    }

    private void obtenerRechazo(AlertDialog alert, SolicitudRen solicitudRen) {
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

                                if (item.getTipoSolicitud() != 1 && Integer.compare(item.getId(), solicitudRen.getIdOriginacion()) == 0) {
                                    sql = "SELECT s.id_solicitud, cre.id_credito, cli.id_cliente, con.id_conyuge, eco.id_economico, neg.id_negocio, ava.id_aval, ref.id_referencia, cro.id FROM " + TBL_SOLICITUDES_REN + " AS s " +
                                            "JOIN " + TBL_CREDITO_IND_REN + " AS cre ON s.id_solicitud = cre.id_solicitud " +
                                            "JOIN " + TBL_CLIENTE_IND_REN + " AS cli ON s.id_solicitud = cli.id_solicitud " +
                                            "JOIN " + TBL_CONYUGE_IND_REN + " AS con ON s.id_solicitud = con.id_solicitud " +
                                            "JOIN " + TBL_ECONOMICOS_IND_REN + " AS eco ON s.id_solicitud = eco.id_solicitud " +
                                            "JOIN " + TBL_NEGOCIO_IND_REN + " AS neg ON s.id_solicitud = neg.id_solicitud " +
                                            "JOIN " + TBL_AVAL_IND_REN + " AS ava ON s.id_solicitud = ava.id_solicitud " +
                                            "JOIN " + TBL_REFERENCIA_IND_REN + " AS ref ON s.id_solicitud = ref.id_solicitud " +
                                            "JOIN " + TBL_CROQUIS_IND_REN + " AS cro ON s.id_solicitud = cro.id_solicitud " +
                                            "WHERE s.id_originacion = ? AND s.estatus >= 2";
                                    row = db.rawQuery(sql, new String[]{String.valueOf(item.getId())});

                                    if (row.getCount() > 0) {
                                        row.moveToFirst();
                                        if (item.getSolicitudEstadoId() == 4) {//Actualiza solicitud de originacion que rechazo la adminitradora para correccion de datos

                                            cv = new ContentValues();
                                            cv.put("comentario_rechazo", "");
                                            db.update(TBL_CLIENTE_IND_REN, cv, "id_solicitud = ? AND id_cliente = ?", new String[]{row.getString(0), row.getString(2)});

                                            if (item.getEstatusCliente() != null && !(Boolean) item.getEstatusCliente()) {
                                                cv = new ContentValues();
                                                cv.put("estatus", 0);
                                                cv.put("fecha_guardado", "");
                                                cv.put("fecha_termino", "");
                                                db.update(TBL_SOLICITUDES_REN, cv, "id_solicitud = ?", new String[]{row.getString(0)});

                                                cv = new ContentValues();
                                                cv.put("estatus_completado", 0);
                                                cv.put("comentario_rechazo", Miscellaneous.validStr(item.getComentarioAdminCliente()));
                                                int i_update = db.update(TBL_CLIENTE_IND_REN, cv, "id_solicitud = ? AND id_cliente = ?", new String[]{row.getString(0), row.getString(2)});
                                                Log.e("Update", "Update: " + i_update);

                                                cv = new ContentValues();
                                                cv.put("estatus_completado", 0);
                                                db.update(TBL_CONYUGE_IND_REN, cv, "id_solicitud = ? AND id_conyuge = ?", new String[]{row.getString(0), row.getString(3)});

                                                cv = new ContentValues();
                                                cv.put("estatus_completado", 0);
                                                db.update(TBL_DOCUMENTOS_REN, cv, "id_solicitud = ? ", new String[]{row.getString(0)});
                                            }

                                            if (item.getEstatusNegocio() != null && !(Boolean) item.getEstatusNegocio()) {
                                                cv = new ContentValues();
                                                cv.put("estatus", 0);
                                                cv.put("fecha_guardado", "");
                                                cv.put("fecha_termino", "");
                                                db.update(TBL_SOLICITUDES_REN, cv, "id_solicitud = ?", new String[]{row.getString(0)});

                                                cv = new ContentValues();
                                                cv.put("estatus_completado", 0);
                                                cv.put("comentario_rechazo", Miscellaneous.validStr(item.getComentarioAdminNegocio()));
                                                db.update(TBL_NEGOCIO_IND_REN, cv, "id_solicitud = ? AND id_negocio = ?", new String[]{row.getString(0), row.getString(5)});
                                            }

                                            if (item.getEstatusAval() != null && !(Boolean) item.getEstatusAval()) {
                                                cv = new ContentValues();
                                                cv.put("estatus", 0);
                                                cv.put("fecha_guardado", "");
                                                cv.put("fecha_termino", "");
                                                db.update(TBL_SOLICITUDES_REN, cv, "id_solicitud = ?", new String[]{row.getString(0)});

                                                cv = new ContentValues();
                                                cv.put("estatus_completado", 0);
                                                cv.put("comentario_rechazo", Miscellaneous.validStr(item.getComentarioAdminAval()));
                                                db.update(TBL_AVAL_IND_REN, cv, "id_solicitud = ? AND id_aval = ?", new String[]{row.getString(0), row.getString(6)});
                                            }

                                            if (item.getEstatusReferencia() != null && !(Boolean) item.getEstatusReferencia()) {
                                                cv = new ContentValues();
                                                cv.put("estatus", 0);
                                                cv.put("fecha_guardado", "");
                                                cv.put("fecha_termino", "");
                                                db.update(TBL_SOLICITUDES_REN, cv, "id_solicitud = ?", new String[]{row.getString(0)});

                                                cv = new ContentValues();
                                                cv.put("estatus_completado", 0);
                                                cv.put("comentario_rechazo", Miscellaneous.validStr(item.getComentarioAdminReferencia()));
                                                db.update(TBL_REFERENCIA_IND_REN, cv, "id_solicitud = ? AND id_referencia = ?", new String[]{row.getString(0), row.getString(7)});
                                            }

                                            if (item.getEstatusCroquis() != null && !(Boolean) item.getEstatusCroquis()) {
                                                cv = new ContentValues();
                                                cv.put("estatus", 0);
                                                cv.put("fecha_guardado", "");
                                                cv.put("fecha_termino", "");
                                                db.update(TBL_SOLICITUDES_REN, cv, "id_solicitud = ?", new String[]{row.getString(0)});

                                                cv = new ContentValues();
                                                cv.put("estatus_completado", 0);
                                                cv.put("comentario_rechazo", Miscellaneous.validStr(item.getComentarioAdminCroquis()));
                                                db.update(TBL_CROQUIS_IND_REN, cv, "id_solicitud = ? AND id = ?", new String[]{row.getString(0), row.getString(8)});
                                            }
                                        } else if (item.getSolicitudEstadoId() == 2) { //Solicitud de renovacion rechazada
                                            Log.e("AQUI RECHAZI IND idsol", row.getString(0));
                                            Log.e("AQUI RECHAZI IND idcli", row.getString(8));

                                            cv = new ContentValues();
                                            cv.put("estatus", 5);
                                            db.update(TBL_SOLICITUDES_REN, cv, "id_solicitud = ?", new String[]{row.getString(0)});

                                            cv = new ContentValues();
                                            cv.put("comentario_rechazo", Miscellaneous.validStr("NO AUTORIZADO - " + item.getComentarioAdminCliente()));
                                            db.update(TBL_CLIENTE_IND_REN, cv, "id_solicitud = ? AND id_cliente = ?", new String[]{row.getString(0), row.getString(8)});
                                        }
                                    }
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
