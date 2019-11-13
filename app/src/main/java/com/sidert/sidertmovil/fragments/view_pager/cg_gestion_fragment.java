package com.sidert.sidertmovil.fragments.view_pager;


import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.activities.ArqueoDeCaja;
import com.sidert.sidertmovil.activities.CameraVertical;
import com.sidert.sidertmovil.activities.CapturarFirma;
import com.sidert.sidertmovil.activities.CobranzaGrupal;
import com.sidert.sidertmovil.activities.IntegrantesGpo;
import com.sidert.sidertmovil.activities.PrintSeewoo;
import com.sidert.sidertmovil.models.OrderModel;
import com.sidert.sidertmovil.utils.Constants;
import com.sidert.sidertmovil.utils.Miscellaneous;
import com.sidert.sidertmovil.utils.MyCurrentListener;
import com.sidert.sidertmovil.utils.Popups;
import com.sidert.sidertmovil.utils.Validator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class cg_gestion_fragment extends Fragment {

    private Context ctx;

    private TextView tvExternalID;
    private TextView tvContactoCliente;
    private TextView tvActualizarTelefono;
    private TextView tvResultadoGestion;
    private TextView tvMedioPago;
    private TextView tvDetalleFicha;
    private TextView tvMontoCorrecto;
    private TextView tvArqueoCaja;
    private TextView tvInfoDesfasada;
    private TextView tvEstaGerente;
    private TextView tvFotoFachada;
    private TextView tvMotivoNoPago;

    private EditText etActualizarTelefono;
    private EditText etInfoDesfasada;
    private EditText etFechaDefuncion;
    private EditText etPagoRealizado;
    private EditText etFechaDeposito;
    private EditText etComentario;
    private EditText etMontoPagoRequerido;
    private EditText etSaldoCorte;
    private EditText etSaldoActual;
    private EditText etEstatusPago;

    private Spinner spMotivoNoPago;
    private Spinner spMedioPago;

    private RadioGroup rgContactoCliente;
    private RadioGroup rgActualizarTelefono;
    private RadioGroup rgMontoCorrecto;
    private RadioGroup rgEstaGerente;
    private RadioGroup rgResultadoPago;
    private RadioGroup rgDetalleFicha;
    private RadioGroup rgRecibos;

    private RadioButton rbSiDetalle;
    private RadioButton rbNoDetalle;
    private RadioButton rbSiGerente;
    private RadioButton rbNoGerente;
    private RadioButton rbSiCorrecto;
    private RadioButton rbNoCorrecto;
    private RadioButton rbArqueoCaja;

    private ImageButton ibMap;
    private ImageButton ibFoto;
    private ImageButton ibGaleria;
    private ImageButton ibFirma;

    private Button btnIntegrantes;
    private Button btnArqueoCaja;

    private ImageView ivEvidencia;
    private ImageView ivFirma;

    private Uri imageUri;

    private LinearLayout llActualizarTelefono;
    private LinearLayout llComentario;
    private LinearLayout llFotoFachada;
    private LinearLayout llDatosDefuncion;
    private LinearLayout llInfoDesfasada;
    private LinearLayout llMotivoNoPago;
    private LinearLayout llMontoPagoRequerido;
    private LinearLayout llMedioPago;
    private LinearLayout llResultadoGestion;
    private LinearLayout llSaldoCorte;
    private LinearLayout llSaldoActual;
    private LinearLayout llFotoGaleria;
    private LinearLayout llIntegrantes;
    private LinearLayout llMontoCorrecto;
    private LinearLayout llArqueoCaja;
    private LinearLayout llEstatusPago;
    private LinearLayout llDetalleFicha;
    private LinearLayout llMontoPagoRealizado;
    private LinearLayout llFechaDeposito;
    private LinearLayout llImprimirRecibo;
    private LinearLayout llFolioRecibo;
    private LinearLayout llEstaGerente;
    private LinearLayout llFirma;

    private String[] _outdate_info;

    private Calendar myCalendar;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
    private Date minDate;

    private Validator validator;

    private boolean flagUbicacion = false;

    private ProgressBar pbLoading;

    private MapView mapView;
    private GoogleMap mMap;
    private Marker mMarker;

    private LocationManager locationManager;
    private MyCurrentListener locationListener;

    CobranzaGrupal parent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cg_gestion, container, false);

        ctx           = getContext();

        parent        = (CobranzaGrupal) getActivity();
        assert parent != null;
        parent.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        tvExternalID            = view.findViewById(R.id.tvExternalID);
        tvContactoCliente       = view.findViewById(R.id.tvContactoCliente);
        tvActualizarTelefono    = view.findViewById(R.id.tvActualizarTelefono);
        tvResultadoGestion      = view.findViewById(R.id.tvResultadoGestion);
        tvMedioPago             = view.findViewById(R.id.tvMedioPago);
        tvDetalleFicha          = view.findViewById(R.id.tvDetalleFicha);
        tvMontoCorrecto         = view.findViewById(R.id.tvMontoCorrecto);
        tvArqueoCaja            = view.findViewById(R.id.tvArqueoCaja);
        tvInfoDesfasada         = view.findViewById(R.id.tvInfoDesfasada);
        tvEstaGerente           = view.findViewById(R.id.tvEstaGerente);
        tvFotoFachada           = view.findViewById(R.id.tvFotoFachada);
        tvMotivoNoPago          = view.findViewById(R.id.tvMotivoNoPago);

        etActualizarTelefono    = view.findViewById(R.id.etActualizarTelefono);
        etInfoDesfasada         = view.findViewById(R.id.etInfoDesfasada);
        etFechaDefuncion        = view.findViewById(R.id.etFechaDefuncion);
        etPagoRealizado         = view.findViewById(R.id.etPagoRealizado);
        etFechaDeposito         = view.findViewById(R.id.etFechaDeposito);
        etComentario            = view.findViewById(R.id.etComentario);
        etMontoPagoRequerido    = view.findViewById(R.id.etMontoPagoRequerido);
        etSaldoCorte            = view.findViewById(R.id.etSaldoCorte);
        etSaldoActual           = view.findViewById(R.id.etSaldoActual);
        etEstatusPago           = view.findViewById(R.id.etEstatusPago);

        spMotivoNoPago  = view.findViewById(R.id.spMotivoNoPago);
        spMedioPago     = view.findViewById(R.id.spMedioPago);

        rgContactoCliente   = view.findViewById(R.id.rgContactoCliente);
        rgActualizarTelefono    = view.findViewById(R.id.rgActualizarTelefono);
        rgResultadoPago     = view.findViewById(R.id.rgResultadoPago);
        rgDetalleFicha      = view.findViewById(R.id.rgDetalleFicha);
        rgMontoCorrecto     = view.findViewById(R.id.rgMontoCorrecto);
        rgRecibos           = view.findViewById(R.id.rgRecibos);
        rgEstaGerente       = view.findViewById(R.id.rgEstaGerente);

        rbSiDetalle     = view.findViewById(R.id.rbSiDetalle);
        rbNoDetalle     = view.findViewById(R.id.rbNoDetalle);
        rbSiCorrecto    = view.findViewById(R.id.rbSiCorrecto);
        rbArqueoCaja    = view.findViewById(R.id.rbArqueoCaja);
        rbNoCorrecto    = view.findViewById(R.id.rbNoCorrecto);
        rbSiGerente     = view.findViewById(R.id.rbSiGerente);
        rbNoGerente     = view.findViewById(R.id.rbNoGerente);

        ibMap           = view.findViewById(R.id.ibMap);
        ibFoto          = view.findViewById(R.id.ibFoto);
        ibGaleria       = view.findViewById(R.id.ibGaleria);
        ibFirma         = view.findViewById(R.id.ibFirma);

        btnIntegrantes  = view.findViewById(R.id.btnIntegrantes);
        btnArqueoCaja   = view.findViewById(R.id.btnArqueoCaja);

        ivEvidencia = view.findViewById(R.id.ivEvidencia);
        ivFirma     = view.findViewById(R.id.ivFirma);

        pbLoading   = view.findViewById(R.id.pbLoading);

        mapView     = view.findViewById(R.id.mapGestion);

        llActualizarTelefono    = view.findViewById(R.id.llActualizarTelefono);
        llIntegrantes           = view.findViewById(R.id.llIntegrantes);
        llComentario            = view.findViewById(R.id.llComentario);
        llResultadoGestion      = view.findViewById(R.id.llResultadoGestion);
        llFotoFachada           = view.findViewById(R.id.llFotoFachada);
        llDatosDefuncion        = view.findViewById(R.id.llDatosDefuncion);
        llInfoDesfasada         = view.findViewById(R.id.llInfoDesfasada);
        llMotivoNoPago          = view.findViewById(R.id.llMotivoNoPago);
        llMontoPagoRequerido    = view.findViewById(R.id.llMontoPagoRequerido);
        llMedioPago             = view.findViewById(R.id.llMedioPago);
        llMontoCorrecto         = view.findViewById(R.id.llMontoCorrecto);
        llArqueoCaja            = view.findViewById(R.id.llArqueoCaja);
        llEstatusPago           = view.findViewById(R.id.llEstatusPago);
        llSaldoCorte            = view.findViewById(R.id.llSaldoCorte);
        llSaldoActual           = view.findViewById(R.id.llSaldoActual);
        llDetalleFicha          = view.findViewById(R.id.llDetalleFicha);
        llMontoPagoRealizado    = view.findViewById(R.id.llMontoPagoRealizado);
        llImprimirRecibo        = view.findViewById(R.id.llImprimirRecibo);
        llFolioRecibo           = view.findViewById(R.id.llFolioRecibo);
        llFechaDeposito         = view.findViewById(R.id.llFechaDeposito);
        llFotoGaleria           = view.findViewById(R.id.llFotoGaleria);
        llEstaGerente           = view.findViewById(R.id.llEstaGerente);
        llFirma                 = view.findViewById(R.id.llFirma);

        myCalendar      = Calendar.getInstance();

        spMedioPago.setPrompt(getResources().getString(R.string.payment_method));
        spMotivoNoPago.setPrompt(getResources().getString(R.string.reason_no_payment));

        mapView.onCreate(savedInstanceState);
        locationManager = (LocationManager) parent.getSystemService(Context.LOCATION_SERVICE);
        validator = new Validator();
        
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initComponents();
        try {
            minDate = sdf.parse("01/01/2019");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        _outdate_info = getResources().getStringArray(R.array.outdated_information);

        //etPagoRealizado.addTextChangedListener(new CustomWatcher(etPagoRealizado));

        //EditText Click
        etInfoDesfasada.setOnClickListener(etInfoDesfasada_OnClick);
        etFechaDefuncion.setOnClickListener(etFechaDefuncion_OnClick);
        etFechaDeposito.setOnClickListener(etFechaDeposito_OnClick);

        // ImageView Click
        ivFirma.setOnClickListener(ivFirma_OnClick);

        //ImageButton Click
        ibMap.setOnClickListener(ibMap_OnClick);
        ibFoto.setOnClickListener(ibFoto_OnClick);
        ibGaleria.setOnClickListener(ibGaleria_OnClick);
        ibFirma.setOnClickListener(ibFirma_OnClick);

        // Button Click
        btnIntegrantes.setOnClickListener(btnIntegrantes_OnClick);
        btnArqueoCaja.setOnClickListener(btnArqueoCaja_OnClick);

        //RadioGroup Click
        rgContactoCliente.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                tvContactoCliente.setError(null);
                tvInfoDesfasada.setError(null);
                etInfoDesfasada.setError(null);
                etComentario.setError(null);
                tvFotoFachada.setError(null);
                switch (checkedId) {
                    case R.id.rbSiContacto:
                        SelectContactoCliente(1);
                        break;
                    case R.id.rbNoContacto:
                        SelectContactoCliente(0);
                        break;
                    case R.id.rbAclaración:
                        SelectContactoCliente(2);
                        break;
                    default:
                        tvContactoCliente.setError("");
                        SelectContactoCliente(-1);
                        break;
                }
            }
        });

        rgActualizarTelefono.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                tvActualizarTelefono.setError(null);
                switch (checkedId){
                    case R.id.rbSiActualizar:
                        SelectActualizarTelefono(1);
                        break;
                    case R.id.rbNoActualizar:
                        SelectActualizarTelefono(0);
                        break;
                    default:
                        etActualizarTelefono.setText("");
                        SelectActualizarTelefono(-1);
                        break;
                }
            }
        });

        rgResultadoPago.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                tvResultadoGestion.setError(null);
                switch (checkedId) {
                    case R.id.rbNoPago:
                        SelectResultadoGestion(0);
                        break;
                    case R.id.rbPago:
                        SelectResultadoGestion(1);
                        break;
                    default:
                        SelectResultadoGestion(-1);
                        break;
                }
            }
        });

        rgDetalleFicha.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                tvDetalleFicha.setError(null);
                switch (checkedId){
                    case R.id.rbSiDetalle:
                        SelectDetalleFicha(1);
                        break;
                    case R.id.rbNoDetalle:
                        SelectDetalleFicha(0);
                        break;
                    default:
                        tvDetalleFicha.setError("");
                        SelectDetalleFicha(-1);
                        break;
                }
            }
        });

        rgMontoCorrecto.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (spMedioPago.getSelectedItemPosition() > 0 && spMedioPago.getSelectedItemPosition() < 7 ) {
                    if(!validator.validate(etFechaDeposito, new String[] {validator.REQUIRED}) &&
                            !validator.validate(etPagoRealizado, new String[] {validator.REQUIRED, validator.MONEY})) {
                        switch (checkedId) {
                            case R.id.rbSiCorrecto:
                                rbSiCorrecto.setChecked(true);
                                SelectMontoCorrecto(1);
                                break;
                            case R.id.rbNoCorrecto:
                                SelectMontoCorrecto(0);
                                break;
                            default:
                                SelectMontoCorrecto(-1);
                                break;
                        }
                    }
                    else{
                        rbSiCorrecto.setChecked(false);
                        rbNoCorrecto.setChecked(false);
                        Toast.makeText(ctx, getResources().getString(R.string.complete_campos_faltantes), Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    if (!validator.validate(etPagoRealizado, new String[] {validator.REQUIRED,validator.MONEY})){
                        switch (checkedId) {
                            case R.id.rbSiCorrecto:
                                rbSiCorrecto.setChecked(true);
                                SelectMontoCorrecto(1);
                                break;
                            case R.id.rbNoCorrecto:
                                SelectMontoCorrecto(0);
                                break;
                            default:
                                SelectMontoCorrecto(-1);
                                break;
                        }
                    }
                    else {
                        rbSiCorrecto.setChecked(false);
                        rbNoCorrecto.setChecked(false);
                        Toast.makeText(ctx, getResources().getString(R.string.complete_campos_faltantes), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        rgRecibos.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rbSiRecibo:
                        SelectImprimirRecibos(1);
                        break;
                    case R.id.rbNoRecibo:
                        SelectImprimirRecibos(0);
                        break;
                    default:
                        SelectImprimirRecibos(-1);
                        break;
                }
            }
        });

        rgEstaGerente.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                tvEstaGerente.setError(null);
                switch (checkedId){
                    case R.id.rbSiGerente:
                        SelectEstaGerente(1);
                        break;
                    case R.id.rbNoGerente:
                        SelectEstaGerente(0);
                        break;
                    default:
                        tvEstaGerente.setError("");
                        SelectEstaGerente(-1);
                        break;
                }
            }
        });

        //Spinner Click
        spMedioPago.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SelecteMedioPago(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spMotivoNoPago.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SelectMotivoNoPago(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    /*
     * Evento click de EditText
     * */
    private View.OnClickListener etInfoDesfasada_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
            builder.setTitle(R.string.selected_option)
                    .setItems(R.array.outdated_information, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int position) {
                            tvInfoDesfasada.setError(null);
                            etInfoDesfasada.setText(_outdate_info[position]);
                        }
                    });
            builder.create();
            builder.show();
        }
    };

    private View.OnClickListener etFechaDefuncion_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            DatePickerDialog dpd = new DatePickerDialog(ctx, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    myCalendar.set(Calendar.YEAR,year);
                    myCalendar.set(Calendar.MONTH,month);
                    myCalendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                    setDatePicked(etFechaDefuncion);

                }
            },myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH));
            dpd.getDatePicker().setMaxDate(Calendar.getInstance().getTimeInMillis());
            dpd.show();
        }
    };

    private View.OnClickListener etFechaDeposito_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            DatePickerDialog dpd = new DatePickerDialog(ctx, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                    myCalendar.set(Calendar.YEAR,year);
                    myCalendar.set(Calendar.MONTH,month);
                    myCalendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                    setDatePicked(etFechaDeposito);

                }
            },myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH));
            dpd.getDatePicker().setMaxDate(new Date().getTime());
            dpd.getDatePicker().setMinDate(minDate.getTime());
            dpd.show();
        }
    };


    // Fin de EditText

    /*
     * Evento click de RadioButton
     * */

    // Fin de RadioButton

    /*
     * Evento de click Button
     * */

    // Fin de Button

    /*
     * Evento click de ImageView
     * */
    private View.OnClickListener ivFirma_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            final AlertDialog firma_dlg = Popups.showDialogConfirm(ctx, Constants.firma,
                    R.string.capturar_nueva_firma, R.string.accept, new Popups.DialogMessage() {
                @Override
                public void OnClickListener(AlertDialog dialog) {
                    Intent sig = new Intent(ctx, CapturarFirma.class);
                    startActivityForResult(sig, Constants.REQUEST_CODE_FIRMA);
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
    };
    // Fin de ImageView

    /*
     * Evento de ImageButton
     * */
    private View.OnClickListener ibMap_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            pbLoading.setVisibility(View.VISIBLE);
            ibMap.setEnabled(false);
            locationManager = (LocationManager) parent.getSystemService(Context.LOCATION_SERVICE);

            locationListener = new MyCurrentListener(new MyCurrentListener.evento() {
                @Override
                public void onComplete(String latitud, String longitud) {

                    ibMap.setEnabled(true);
                    if (!latitud.isEmpty() && !longitud.isEmpty()){
                        mapView.setVisibility(View.VISIBLE);
                        ColocarUbicacionGestion(Double.parseDouble(latitud), Double.parseDouble(longitud));
                    }
                    else{
                        pbLoading.setVisibility(View.GONE);
                        Toast.makeText(ctx, getResources().getString(R.string.no_ubicacion), Toast.LENGTH_SHORT).show();
                    }

                    flagUbicacion = true;
                    if (flagUbicacion){
                        CancelUbicacion();
                    }
                }
            });
            if (ActivityCompat.checkSelfPermission(ctx, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(ctx, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            }
            locationManager.requestSingleUpdate( LocationManager.GPS_PROVIDER, locationListener, null );
        }
    };

    private View.OnClickListener ibFoto_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i = new Intent(parent, CameraVertical.class);
            i.putExtra(Constants.ORDER_ID, parent.ficha_cg.getId());
            startActivityForResult(i, Constants.REQUEST_CODE_CAMARA_TICKET);
        }
    };

    private View.OnClickListener ibGaleria_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
            startActivityForResult(gallery, Constants.REQUEST_CODE_GALERIA);
        }
    };

    private View.OnClickListener ibFirma_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent sig = new Intent(ctx, CapturarFirma.class);
            startActivityForResult(sig, Constants.REQUEST_CODE_FIRMA);
        }
    };

    //Fin de ImageButton

    /*
     * Evento de Button
     * */
    private View.OnClickListener btnIntegrantes_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Intent i_integrantes = new Intent(ctx, IntegrantesGpo.class);
            i_integrantes.putExtra(Constants.INTEGRANTES_GRUPO, parent.ficha_cg.getGrupo());
            startActivityForResult(i_integrantes, Constants.REQUEST_CODE_INTEGRANTES_GPO);
        }
    };

    private View.OnClickListener btnArqueoCaja_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i_arqueoCaja = new Intent(ctx, ArqueoDeCaja.class);
            i_arqueoCaja.putExtra(Constants.PAGO_REALIZADO, Double.parseDouble(etPagoRealizado.getText().toString()));
            i_arqueoCaja.putExtra(Constants.PAGO_REQUERIDO, parent.ficha_cg.getPrestamo().getPagoSemanal());
            i_arqueoCaja.putExtra(Constants.NOMBRE_GRUPO, parent.ficha_cg.getGrupo().getNombreGrupo());
            i_arqueoCaja.putExtra(Constants.ORDER_ID, parent.ficha_cg.getId());
            startActivityForResult(i_arqueoCaja, Constants.REQUEST_CODE_ARQUEO_CAJA);
        }
    };
    // Fin de Button

    //===================== Comportamientos  ===============================================
    private void SelectContactoCliente (int pos){

        if (rbSiGerente.isChecked() || rbNoGerente.isChecked()) tvEstaGerente.setError(null);
        else tvEstaGerente.setError("");
        switch (pos){
            case 0: // No contacto cliente
                etComentario.setError(getResources().getString(R.string.campo_requerido));
                etComentario.setText("");
                tvFotoFachada.setError("");
                etInfoDesfasada.setText("");
                rgResultadoPago.clearCheck();
                SelectResultadoGestion(-1);
                rgActualizarTelefono.clearCheck();
                SelectActualizarTelefono(-1);
                llActualizarTelefono.setVisibility(View.GONE);
                llResultadoGestion.setVisibility(View.GONE);
                llComentario.setVisibility(View.VISIBLE);
                llFotoFachada.setVisibility(View.VISIBLE);
                llEstaGerente.setVisibility(View.VISIBLE);
                llInfoDesfasada.setVisibility(View.GONE);
                break;
            case 1: // Si contacto cliente
                tvResultadoGestion.setError("");
                tvActualizarTelefono.setError("");
                etComentario.setText("");
                etInfoDesfasada.setText("");
                llActualizarTelefono.setVisibility(View.VISIBLE);
                llResultadoGestion.setVisibility(View.VISIBLE);
                llComentario.setVisibility(View.GONE);
                llFotoFachada.setVisibility(View.GONE);
                llEstaGerente.setVisibility(View.GONE);
                llInfoDesfasada.setVisibility(View.GONE);
                break;
            case 2: // Aclaración
                tvInfoDesfasada.setError("");
                etComentario.setError(getResources().getString(R.string.campo_requerido));
                etComentario.setText("");
                etInfoDesfasada.setText("");
                rgResultadoPago.clearCheck();
                SelectResultadoGestion(-1);
                rgActualizarTelefono.clearCheck();
                SelectActualizarTelefono(-1);
                llActualizarTelefono.setVisibility(View.GONE);
                llResultadoGestion.setVisibility(View.GONE);
                llFotoFachada.setVisibility(View.GONE);
                llEstaGerente.setVisibility(View.VISIBLE);
                llInfoDesfasada.setVisibility(View.VISIBLE);
                llComentario.setVisibility(View.VISIBLE);
                break;
            default: //Sin seleccionar una opción o cualquier otro valor
                etComentario.setText("");
                etInfoDesfasada.setText("");
                rgResultadoPago.clearCheck();
                llActualizarTelefono.setVisibility(View.GONE);
                llResultadoGestion.setVisibility(View.GONE);
                llComentario.setVisibility(View.GONE);
                llFotoFachada.setVisibility(View.GONE);
                llEstaGerente.setVisibility(View.GONE);
                llInfoDesfasada.setVisibility(View.GONE);
                break;
        }
    }

    private void SelectActualizarTelefono(int pos){
        switch (pos){
            case 0:
                etActualizarTelefono.setText("");
                etActualizarTelefono.setVisibility(View.GONE);
                break;
            case 1:
                etActualizarTelefono.setVisibility(View.VISIBLE);
                etActualizarTelefono.setError(getResources().getString(R.string.campo_requerido));
                break;
            default:
                tvActualizarTelefono.setError("");
                etActualizarTelefono.setText("");
                etActualizarTelefono.setVisibility(View.GONE);
                break;
        }
    }

    private void SelectResultadoGestion(int pos){
        switch (pos){
            case 0: //No Pago
                tvMotivoNoPago.setError("");
                tvFotoFachada.setError("");
                spMedioPago.setSelection(0);
                SelecteMedioPago(0);
                llMedioPago.setVisibility(View.GONE);
                llMontoPagoRequerido.setVisibility(View.GONE);
                llSaldoCorte.setVisibility(View.GONE);
                llSaldoActual.setVisibility(View.GONE);
                llMotivoNoPago.setVisibility(View.VISIBLE);
                llComentario.setVisibility(View.VISIBLE);
                etComentario.setError(getResources().getString(R.string.campo_requerido));
                llFotoFachada.setVisibility(View.VISIBLE);
                llEstaGerente.setVisibility(View.VISIBLE);
                break;
            case 1: //Si Pago
                spMotivoNoPago.setSelection(0);
                SelectMotivoNoPago(0);
                llMedioPago.setVisibility(View.VISIBLE);
                llMontoPagoRequerido.setVisibility(View.VISIBLE);
                llSaldoCorte.setVisibility(View.VISIBLE);
                llSaldoActual.setVisibility(View.VISIBLE);
                llMotivoNoPago.setVisibility(View.GONE);
                llComentario.setVisibility(View.GONE);
                llFotoFachada.setVisibility(View.GONE);
                llEstaGerente.setVisibility(View.GONE);
                break;
            default: //Sin seleccionar una opción o cualquier otro valor
                tvResultadoGestion.setError("");
                spMedioPago.setSelection(0);
                SelecteMedioPago(0);
                llMedioPago.setVisibility(View.GONE);
                llMontoPagoRequerido.setVisibility(View.GONE);
                llComentario.setVisibility(View.GONE);
                llSaldoCorte.setVisibility(View.GONE);
                llSaldoActual.setVisibility(View.GONE);
                llMotivoNoPago.setVisibility(View.GONE);
                llFotoFachada.setVisibility(View.GONE);
                llEstaGerente.setVisibility(View.GONE);
                break;
        }
    }

    private void SelecteMedioPago (int pos){
        if (rgContactoCliente.getChildAt(0).isEnabled()){
            rgMontoCorrecto.clearCheck();
            etFechaDeposito.setText("");
        }
        else {
            spMedioPago.setEnabled(false);
            if (pos > 0 && pos < 6) {
                etFechaDeposito.setEnabled(false);
            }else {
                etFechaDeposito.setEnabled(true);
            }
        }
        if (rbSiDetalle.isChecked() || rbNoDetalle.isChecked()) tvDetalleFicha.setError(null);
        else tvDetalleFicha.setError("");

        tvMedioPago.setError(null);
        switch (pos){
            case 0: // Opción "Seleccione una opción"
                tvMedioPago.setError("");
                rgDetalleFicha.clearCheck();
                SelectDetalleFicha(-1);
                llDetalleFicha.setVisibility(View.GONE);
                llFechaDeposito.setVisibility(View.GONE);
                break;
            case 1: // Banamex
            case 2: // Banorte
            case 3: // Telecom
            case 4: // Bansefi
            case 5: // Bancomer
            case 6: // Oxxo
                llDetalleFicha.setVisibility(View.VISIBLE);
                llFechaDeposito.setVisibility(View.VISIBLE);
                break;
            case 7: // Efectivo
            case 8: // Sidert
                llDetalleFicha.setVisibility(View.VISIBLE);
                llFechaDeposito.setVisibility(View.GONE);
                break;
            default: //Sin seleccionar una opción o cualquier otro valor
                tvMedioPago.setError("");
                rgDetalleFicha.clearCheck();
                SelectDetalleFicha(-1);
                llDetalleFicha.setVisibility(View.GONE);
                llFechaDeposito.setVisibility(View.GONE);
                break;
        }
    }

    private void SelectMotivoNoPago (int pos){
        tvMotivoNoPago.setError(null);
        switch (pos){
            case 0: // Opción "Seleccione una opcion"
                tvMotivoNoPago.setError("");
                llDatosDefuncion.setVisibility(View.GONE);
                break;
            case 1: // Negación de pago
                llDatosDefuncion.setVisibility(View.GONE);
                break;
            case 2: //Fallecimiento
                etFechaDefuncion.setError("");
                llDatosDefuncion.setVisibility(View.VISIBLE);
                break;
            case 3: // Otro
                llDatosDefuncion.setVisibility(View.GONE);
                break;
            default: //Sin seleccionar una opción o cualquier otro valor
                tvMotivoNoPago.setError("");
                llDatosDefuncion.setVisibility(View.GONE);
                break;
        }
    }

    private void SelectDetalleFicha (int pos){
        switch (pos){
            case -1: //Sin seleccionar una opción o cualquier otro valor
                //etPagoRealizado.setText(Miscellaneous.moneyFormat(String.valueOf(parent.ficha_cg.getPrestamo().getPagoRealizado())));
                etPagoRealizado.setText(String.valueOf(parent.ficha_cg.getPrestamo().getPagoRealizado()));
                etPagoRealizado.setEnabled(false);
                llIntegrantes.setVisibility(View.GONE);
                llMontoPagoRealizado.setVisibility(View.GONE);
                llMontoCorrecto.setVisibility(View.GONE);
                rgMontoCorrecto.clearCheck();
                SelectMontoCorrecto(-1);
                break;
            case 0: // No cuenta con detalle
                etPagoRealizado.setError(null);
                tvMontoCorrecto.setError("");
                etPagoRealizado.setEnabled(true);
                llIntegrantes.setVisibility(View.GONE);
                llMontoPagoRealizado.setVisibility(View.VISIBLE);
                llMontoCorrecto.setVisibility(View.VISIBLE);
                break;
            case 1: // Si cuenta con detalle
                tvMontoCorrecto.setError("");
                //etPagoRealizado.setText(Miscellaneous.moneyFormat(String.valueOf(parent.ficha_cg.getPrestamo().getPagoRealizado())));
                etPagoRealizado.setText(String.valueOf(parent.ficha_cg.getPrestamo().getPagoRealizado()));
                etPagoRealizado.setEnabled(false);
                llIntegrantes.setVisibility(View.VISIBLE);
                llMontoPagoRealizado.setVisibility(View.VISIBLE);
                llMontoCorrecto.setVisibility(View.VISIBLE);
                break;

        }
    }

    private void SelectMontoCorrecto(int pos){
        etFechaDeposito.setError(null);
        tvMontoCorrecto.setError(null);
        switch (pos){
            case 0: //No Es Correcto
                for (int i = 0; i < rgContactoCliente.getChildCount(); i++) {
                    rgContactoCliente.getChildAt(i).setEnabled(true);
                }
                for (int i = 0; i < rgResultadoPago.getChildCount(); i++) {
                    rgResultadoPago.getChildAt(i).setEnabled(true);
                }
                spMedioPago.setEnabled(true);
                if (spMedioPago.getSelectedItemPosition() > 0 && spMedioPago.getSelectedItemPosition() < 7){
                    etFechaDeposito.setEnabled(true);
                }

                for (int i = 0; i < rgDetalleFicha.getChildCount(); i++) {
                    rgDetalleFicha.getChildAt(i).setEnabled(true);
                }

                if (rgDetalleFicha.getChildAt(0).isEnabled()){
                    btnIntegrantes.setEnabled(true);
                    btnIntegrantes.setVisibility(View.VISIBLE);
                }else {
                    btnIntegrantes.setEnabled(false);
                    btnIntegrantes.setVisibility(View.GONE);
                }
                etPagoRealizado.setEnabled(true);

                llArqueoCaja.setVisibility(View.GONE);
                llEstatusPago.setVisibility(View.GONE);
                llImprimirRecibo.setVisibility(View.GONE);
                llFolioRecibo.setVisibility(View.GONE);
                llFotoGaleria.setVisibility(View.GONE);
                llEstaGerente.setVisibility(View.GONE);
                rgEstaGerente.clearCheck();
                SelectEstaGerente(-1);
                break;
            case 1: //Si es Correcto
                for (int i = 0; i < rgContactoCliente.getChildCount(); i++) {
                    rgContactoCliente.getChildAt(i).setEnabled(false);
                }
                for (int i = 0; i < rgResultadoPago.getChildCount(); i++) {
                    rgResultadoPago.getChildAt(i).setEnabled(false);
                }
                spMedioPago.setEnabled(false);
                if (spMedioPago.getSelectedItemPosition() > 0 && spMedioPago.getSelectedItemPosition() < 7){
                    etFechaDeposito.setEnabled(false);
                }
                for (int i = 0; i < rgDetalleFicha.getChildCount(); i++) {
                    rgDetalleFicha.getChildAt(i).setEnabled(false);
                }
                btnIntegrantes.setEnabled(false);
                etPagoRealizado.setEnabled(false);
                if (Miscellaneous.doubleFormat(etMontoPagoRequerido) - Miscellaneous.doubleFormat(etPagoRealizado) == 0)
                    etEstatusPago.setText(ctx.getResources().getString(R.string.pago_completo));
                else if (Miscellaneous.doubleFormat(etMontoPagoRequerido) - Miscellaneous.doubleFormat(etPagoRealizado) < 0)
                    etEstatusPago.setText(ctx.getResources().getString(R.string.pago_completo_adelanto));
                else if (Miscellaneous.doubleFormat(etMontoPagoRequerido) - Miscellaneous.doubleFormat(etPagoRealizado) > 0)
                    etEstatusPago.setText(ctx.getResources().getString(R.string.pago_parcial));
                else
                    etEstatusPago.setText(ctx.getResources().getString(R.string.pay_status));
                llEstatusPago.setVisibility(View.VISIBLE);

                etSaldoActual.setText(Miscellaneous.moneyFormat(String.valueOf(Miscellaneous.doubleFormat(etSaldoCorte) - Miscellaneous.doubleFormat(etPagoRealizado))
                ));

                if (spMedioPago.getSelectedItemPosition() > 0 && spMedioPago.getSelectedItemPosition() < 7){
                    llFotoGaleria.setVisibility(View.VISIBLE);
                    ibFoto.setEnabled(true);
                    ibGaleria.setEnabled(true);
                    ibGaleria.setBackground(ctx.getResources().getDrawable(R.drawable.round_corner_blue));
                }else {
                    llImprimirRecibo.setVisibility(View.VISIBLE);
                    llFolioRecibo.setVisibility(View.VISIBLE);
                    llFotoGaleria.setVisibility(View.VISIBLE);
                    ibFoto.setEnabled(true);
                    ibGaleria.setEnabled(false);
                    ibGaleria.setBackground(ctx.getResources().getDrawable(R.drawable.btn_disable));
                }

                if (Double.parseDouble(etPagoRealizado.getText().toString()) >= 10000 && spMedioPago.getSelectedItemPosition() == 7) {
                    tvArqueoCaja.setError(rbArqueoCaja.isChecked()?null : "");
                    llArqueoCaja.setVisibility(View.VISIBLE);
                }
                else
                    llArqueoCaja.setVisibility(View.GONE);

                llEstaGerente.setVisibility(View.VISIBLE);
                break;
            default: //Sin seleccionar una opción o cualquier otro valor
                tvMontoCorrecto.setError("");
                for (int i = 0; i < rgContactoCliente.getChildCount(); i++) {
                    rgContactoCliente.getChildAt(i).setEnabled(true);
                }
                for (int i = 0; i < rgResultadoPago.getChildCount(); i++) {
                    rgResultadoPago.getChildAt(i).setEnabled(true);
                }
                spMedioPago.setEnabled(true);
                if (spMedioPago.getSelectedItemPosition() > 0 && spMedioPago.getSelectedItemPosition() < 7){
                    etFechaDeposito.setEnabled(true);
                }
                for (int i = 0; i < rgDetalleFicha.getChildCount(); i++) {
                    rgDetalleFicha.getChildAt(i).setEnabled(true);
                }
                etPagoRealizado.setEnabled(true);
                llArqueoCaja.setVisibility(View.GONE);
                llEstatusPago.setVisibility(View.GONE);
                llImprimirRecibo.setVisibility(View.GONE);
                llFolioRecibo.setVisibility(View.GONE);
                llFotoGaleria.setVisibility(View.GONE);
                llEstaGerente.setVisibility(View.GONE);
                break;
        }
    }

    private void SelectImprimirRecibos(int pos){
        switch (pos){
            case 0: //No cuenta con bateria la impresora
                llFolioRecibo.setVisibility(View.GONE);
                break;
            case 1: // Imprimir Recibos
                llFolioRecibo.setVisibility(View.VISIBLE);
                Intent i = new Intent(ctx, PrintSeewoo.class);
                OrderModel order = new OrderModel(parent.ficha_cg.getId(),
                        "002",
                        parent.ficha_cg.getPrestamo().getMontoPrestamo(),
                        parent.ficha_cg.getPrestamo().getPagoSemanal(),
                        Miscellaneous.doubleFormat(etPagoRealizado),
                        parent.ficha_cg.getGrupo().getClaveGrupo(),
                        parent.ficha_cg.getPrestamo().getNumeroDePrestamo(),
                        parent.ficha_cg.getGrupo().getNombreGrupo(),
                        "NOMBRE DEL ALGUN ASESOR",
                        0);

                i.putExtra("order",order);
                i.putExtra("tag",true);

                startActivityForResult(i,Constants.REQUEST_CODE_IMPRESORA);
                break;
            default: // Sin seleccionar alguna opción o cualquier valor diferente
                llFolioRecibo.setVisibility(View.GONE);
                break;
        }

    }

    private void SelectEstaGerente (int pos){
        switch (pos){
            case 0: // No está el gerente
                llFirma.setVisibility(View.GONE);
                break;
            case 1: // Si está el gerente
                llFirma.setVisibility(View.VISIBLE);
                break;
            default: // Sin seleccionar alguna opción o cualquier valor diferente
                llFirma.setVisibility(View.GONE);
                break;
        }
    }


    //======================  Otros Métodos  =================================
    private void initComponents(){
        tvExternalID.setText(parent.ficha_cg.getId());
        tvContactoCliente.setError("");
        etMontoPagoRequerido.setText(String.valueOf(parent.ficha_cg.getPrestamo().getPagoSemanal()));
        etSaldoCorte.setText(Miscellaneous.moneyFormat(String.valueOf(parent.ficha_cg.getPrestamo().getSaldoActual())));
    }

    private void setDatePicked(EditText et){
        sdf.setTimeZone(myCalendar.getTimeZone());
        et.setError(null);
        et.setText(sdf.format(myCalendar.getTime()));
    }

    //===============  GPS  =======================================================================
    private void ColocarUbicacionGestion (final double lat, final double lon){
        mapView.onResume();
        try {
            MapsInitializer.initialize(parent.getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mGooglemap) {
                mMap = mGooglemap;
                if (ActivityCompat.checkSelfPermission(ctx, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(ctx, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                    return;
                }
                mMap.setMapStyle(
                        MapStyleOptions.loadRawResourceStyle(
                                ctx, R.raw.style_json));
                mMap.getUiSettings().setAllGesturesEnabled(false);
                mMap.getUiSettings().setMapToolbarEnabled(false);

                addMarker(lat,lon);

            }
        });
    }

    private void addMarker (double lat, double lng){
        LatLng coordenadas = new LatLng(lat,lng);
        //LatLng coordenada = new LatLng(19.201745,-96.162134);
        CameraUpdate ubication = CameraUpdateFactory.newLatLngZoom(coordenadas,15);

        mMap.addMarker(new MarkerOptions()
                .position(coordenadas)
                .title(""));

//        mMap.addMarker(new MarkerOptions()
//                .position(coordenada)
//                .title(""));

        mMap.animateCamera(ubication);

        Polyline line = mMap.addPolyline(new PolylineOptions()
                .add(new LatLng(lat, lng), new LatLng(19.201745,-96.162134))
                .width(5)
                .color(Color.RED));

        pbLoading.setVisibility(View.GONE);
        ibMap.setVisibility(View.GONE);
    }

    private void CancelUbicacion (){
        if (flagUbicacion)
            locationManager.removeUpdates(locationListener);
    }

    //==============  Resultado de actividades  ======================================
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case Constants.REQUEST_CODE_FIRMA:
                if (resultCode == Activity.RESULT_OK){
                    if (data != null){
                        ibFirma.setVisibility(View.GONE);
                        ivFirma.setVisibility(View.VISIBLE);
                        Glide.with(ctx)
                                .load(data.getByteArrayExtra(Constants.FIRMA_IMAGE))
                                .into(ivFirma);
                    }
                }
                break;
            case Constants.REQUEST_CODE_INTEGRANTES_GPO:
                if (resultCode == Activity.RESULT_OK){
                    if (data != null){
                        etPagoRealizado.setError(null);
                        etPagoRealizado.setText(String.valueOf(data.getDoubleExtra(Constants.RESPONSE,0)));
                    }
                }
                break;
            case Constants.REQUEST_CODE_IMPRESORA:
                if (resultCode == Activity.RESULT_OK){
                    if (data != null){
                        Toast.makeText(ctx, data.getStringExtra(Constants.MESSAGE), Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case Constants.REQUEST_CODE_GALERIA:
                if (data != null){
                    imageUri = data.getData();
                    ivEvidencia.setVisibility(View.VISIBLE);
                    Glide.with(ctx).load(imageUri).centerCrop().into(ivEvidencia);
                }
                break;
            case Constants.REQUEST_CODE_CAMARA_TICKET:
                if (resultCode == Activity.RESULT_OK){
                    if (data != null){
                        Glide.with(ctx).load(data.getByteArrayExtra(Constants.PICTURE)).centerCrop().into(ivEvidencia);
                    }
                }
                break;
            case Constants.REQUEST_CODE_ARQUEO_CAJA:
                if (resultCode == Activity.RESULT_OK){
                    if (data != null){
                        if (data.getBooleanExtra(Constants.SAVE,false)){
                            tvArqueoCaja.setError(null);
                            rbArqueoCaja.setChecked(true);
                        }
                        else {
                            tvArqueoCaja.setError("");
                            rbArqueoCaja.setChecked(false);
                        }
                    }
                }
                break;

        }
    }
}
