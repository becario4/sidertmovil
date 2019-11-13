package com.sidert.sidertmovil.fragments.view_pager;


import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
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
import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.activities.ArqueoDeCaja;
import com.sidert.sidertmovil.activities.CameraVertical;
import com.sidert.sidertmovil.activities.CapturarFirma;
import com.sidert.sidertmovil.activities.CobranzaIndividual;
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

public class ci_gestion_fragment extends Fragment {

    private Context ctx;

    private TextView tvExternalID;
    private TextView tvContactoCliente;
    private TextView tvActualizarTelefono;
    private TextView tvResultadoGestion;
    private TextView tvMedioPago;
    private TextView tvPagaraRequerido;
    private TextView tvMontoCorrecto;
    private TextView tvArqueoCaja;
    private TextView tvInfoDesfasada;
    private TextView tvEstaGerente;
    private TextView tvFotoFachada;
    private TextView tvMotivoNoPago;

    private EditText etActualizarTelefono;
    private EditText etInfoDesfasada;
    private EditText etComentario;
    private EditText etMontoPagoRequerido;
    private EditText etSaldoCorte;
    private EditText etSaldoActual;
    private EditText etFechaDefuncion;
    private EditText etPagoRealizado;
    private EditText etFechaDeposito;
    private EditText etEstatusPago;

    private Spinner spMotivoNoPago;
    private Spinner spMedioPago;

    private RadioGroup rgContactoCliente;
    private RadioGroup rgActualizarTelefono;
    private RadioGroup rgResultadoPago;
    private RadioGroup rgPagaraRequerido;
    private RadioGroup rgMontoCorrecto;
    private RadioGroup rgRecibos;
    private RadioGroup rgEstaGerente;

    private RadioButton rbSiRequerido;
    private RadioButton rbNoRequerido;
    private RadioButton rbSiCorrecto;
    private RadioButton rbNoCorrecto;
    private RadioButton rbArqueoCaja;
    private RadioButton rbSiGerente;
    private RadioButton rbNoGerente;

    private ImageButton ibMap;
    private ImageButton ibFoto;
    private ImageButton ibGaleria;
    private ImageButton ibFirma;

    private Button btnArqueoCaja;

    private ImageView ivFirma;
    private ImageView ivEvidencia;

    private LinearLayout llComentario;
    private LinearLayout llActualizarTelefono;
    private LinearLayout llFotoFachada;
    private LinearLayout llDatosDefuncion;
    private LinearLayout llInfoDesfasada;
    private LinearLayout llMotivoNoPago;
    private LinearLayout llMontoPagoRequerido;
    private LinearLayout llMedioPago;
    private LinearLayout llResultadoGestion;
    private LinearLayout llMontoCorrecto;
    private LinearLayout llArqueoCaja;
    private LinearLayout llEstatusPago;
    private LinearLayout llSaldoCorte;
    private LinearLayout llSaldoActual;
    private LinearLayout llPagaraRequerido;
    private LinearLayout llMontoPagoRealizado;
    private LinearLayout llFechaDeposito;
    private LinearLayout llImprimirRecibo;
    private LinearLayout llFolioRecibo;
    private LinearLayout llFotoGaleria;
    private LinearLayout llEstaGerente;
    private LinearLayout llFirma;

    private String[] _outdate_info;

    private Calendar myCalendar;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
    private Date minDate;

    private final int REQUEST_CODE_SIGNATURE = 456;

    CobranzaIndividual parent;
    private Validator validator;

    private boolean flagUbicacion = false;

    private LocationManager locationManager;
    private MyCurrentListener locationListener;

    private ProgressBar pbLoading;

    private MapView mapView;
    private GoogleMap mMap;
    private Marker mMarker;

    private Uri imageUri;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ci_gestion, container, false);

        ctx             = getContext();

        parent                = (CobranzaIndividual) getActivity();
        assert parent != null;
        parent.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        tvExternalID            = view.findViewById(R.id.tvExternalID);
        tvContactoCliente       = view.findViewById(R.id.tvContactoCliente);
        tvActualizarTelefono    = view.findViewById(R.id.tvActualizarTelefono);
        tvResultadoGestion      = view.findViewById(R.id.tvResultadoGestion);
        tvMedioPago             = view.findViewById(R.id.tvMedioPago);
        tvPagaraRequerido       = view.findViewById(R.id.tvPagaraRequerido);
        tvMontoCorrecto         = view.findViewById(R.id.tvMontoCorrecto);
        tvArqueoCaja            = view.findViewById(R.id.tvArqueoCaja);
        tvInfoDesfasada         = view.findViewById(R.id.tvInfoDesfasada);
        tvEstaGerente           = view.findViewById(R.id.tvEstaGerente);
        tvFotoFachada           = view.findViewById(R.id.tvFotoFachada);
        tvMotivoNoPago          = view.findViewById(R.id.tvMotivoNoPago);

        etActualizarTelefono    = view.findViewById(R.id.etActualizarTelefono);
        etInfoDesfasada         = view.findViewById(R.id.etInfoDesfasada);
        etComentario            = view.findViewById(R.id.etComentario);
        etMontoPagoRequerido    = view.findViewById(R.id.etMontoPagoRequerido);
        etSaldoCorte            = view.findViewById(R.id.etSaldoCorte);
        etSaldoActual           = view.findViewById(R.id.etSaldoActual);
        etFechaDefuncion        = view.findViewById(R.id.etFechaDefuncion);
        etPagoRealizado         = view.findViewById(R.id.etPagoRealizado);
        etFechaDeposito         = view.findViewById(R.id.etFechaDeposito);
        etEstatusPago           = view.findViewById(R.id.etEstatusPago);

        spMedioPago             = view.findViewById(R.id.spMedioPago);
        spMotivoNoPago          = view.findViewById(R.id.spMotivoNoPago);

        rgContactoCliente       = view.findViewById(R.id.rgContactoCliente);
        rgActualizarTelefono    = view.findViewById(R.id.rgActualizarTelefono);
        rgResultadoPago         = view.findViewById(R.id.rgResultadoPago);
        rgPagaraRequerido       = view.findViewById(R.id.rgPagaraRequerido);
        rgMontoCorrecto         = view.findViewById(R.id.rgMontoCorrecto);
        rgRecibos               = view.findViewById(R.id.rgRecibos);
        rgEstaGerente           = view.findViewById(R.id.rgEstaGerente);

        rbSiRequerido   = view.findViewById(R.id.rbSiRequerido);
        rbNoRequerido   = view.findViewById(R.id.rbNoRequerido);
        rbSiCorrecto    = view.findViewById(R.id.rbSiCorrecto);
        rbNoCorrecto    = view.findViewById(R.id.rbNoCorrecto);
        rbArqueoCaja    = view.findViewById(R.id.rbArqueoCaja);
        rbSiGerente     = view.findViewById(R.id.rbSiGerente);
        rbNoGerente     = view.findViewById(R.id.rbNoGerente);

        ibMap          = view.findViewById(R.id.imbMap);
        ibFoto          = view.findViewById(R.id.ibFoto);
        ibGaleria       = view.findViewById(R.id.ibGaleria);
        ibFirma         = view.findViewById(R.id.ibFirma);

        btnArqueoCaja   = view.findViewById(R.id.btnArqueoCaja);

        ivFirma     = view.findViewById(R.id.ivFirma);
        ivEvidencia = view.findViewById(R.id.ivEvidencia);

        mapView         = view.findViewById(R.id.mapGestion);
        pbLoading       = view.findViewById(R.id.pbLoading);

        llComentario            = view.findViewById(R.id.llComentario);
        llActualizarTelefono    = view.findViewById(R.id.llActualizarTelefono);
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
        llPagaraRequerido       = view.findViewById(R.id.llPagaraRequerido);
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

        // EditText Click
        etInfoDesfasada.setOnClickListener(etInfoDesfasada_OnClick);
        etFechaDefuncion.setOnClickListener(etFechaDefuncion_OnClick);
        etFechaDeposito.setOnClickListener(etFechaDeposito_OnClick);

        // ImageButton Click
        ibMap.setOnClickListener(ibMap_OnClick);
        ibFoto.setOnClickListener(ibFoto_OnClick);
        ibGaleria.setOnClickListener(ibGaleria_OnClick);
        ibFirma.setOnClickListener(ibFirma_OnClick);

        //Button Click
        btnArqueoCaja.setOnClickListener(btnArqueoCaja_OnClick);

        // RadioGroup Click
        rgContactoCliente.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                tvContactoCliente.setError(null);
                tvActualizarTelefono.setError(null);
                tvInfoDesfasada.setError(null);
                etInfoDesfasada.setError(null);
                etComentario.setError(null);
                tvFotoFachada.setError(null);
                switch (checkedId) {
                    case R.id.rbNoContacto:
                        SelectContactoCliente(0);
                        break;
                    case R.id.rbSiContacto:
                        SelectContactoCliente(1);
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
                    case R.id.rbNoActualizar:
                        SelectActualizarTelefono(0);
                        break;
                    case R.id.rbSiActualizar:
                        SelectActualizarTelefono(1);
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

        rgPagaraRequerido.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                tvPagaraRequerido.setError(null);
                switch (checkedId){
                    case R.id.rbSiRequerido:
                        SelectPagoRequerido(1);
                        break;
                    case R.id.rbNoRequerido:
                        SelectPagoRequerido(0);
                        break;
                    default:
                        tvPagaraRequerido.setError("");
                        SelectPagoRequerido(-1);
                        break;
                }
            }
        });

        rgMontoCorrecto.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (spMedioPago.getSelectedItemPosition() > 0 && spMedioPago.getSelectedItemPosition() < 7) {
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

        // Spinner Click
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

        // ImageView Click
        ivFirma.setOnClickListener(ivFirma_OnClick);
    }

    private View.OnClickListener ivFirma_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            final AlertDialog firma_dlg = Popups.showDialogConfirm(ctx, Constants.firma,
                    R.string.capturar_nueva_firma, R.string.accept, new Popups.DialogMessage() {
                @Override
                public void OnClickListener(AlertDialog dialog) {
                    Intent sig = new Intent(ctx, CapturarFirma.class);
                    startActivityForResult(sig, REQUEST_CODE_SIGNATURE);
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
            //locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);

        }
    };

    private View.OnClickListener ibFoto_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i = new Intent(parent, CameraVertical.class);
            i.putExtra(Constants.ORDER_ID, parent.ficha_ci.getId());
            startActivityForResult(i, Constants.REQUEST_CODE_CAMARA_TICKET);
            /*Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            i.putExtra(MediaStore.EXTRA_SCREEN_ORIENTATION, ActivityInfo.SCREEN_ORIENTATION_LOCKED);
            //i.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
            startActivityForResult(i, 432);*/
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
            startActivityForResult(sig, REQUEST_CODE_SIGNATURE);
        }
    };

    // Button Click
    private View.OnClickListener btnArqueoCaja_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i_arqueoCaja = new Intent(ctx, ArqueoDeCaja.class);
            i_arqueoCaja.putExtra(Constants.PAGO_REALIZADO, Double.parseDouble(etPagoRealizado.getText().toString()));
            i_arqueoCaja.putExtra(Constants.PAGO_REQUERIDO, parent.ficha_ci.getPrestamo().getPagoSemanal());
            i_arqueoCaja.putExtra(Constants.NOMBRE_GRUPO, parent.ficha_ci.getCliente().getNombre());
            i_arqueoCaja.putExtra(Constants.ORDER_ID, parent.ficha_ci.getId());
            startActivityForResult(i_arqueoCaja, Constants.REQUEST_CODE_ARQUEO_CAJA);
        }
    };

    // EditText Click
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

    private void setDatePicked(EditText et){
        sdf.setTimeZone(myCalendar.getTimeZone());
        et.setError(null);
        et.setText(sdf.format(myCalendar.getTime()));
    }

    private void initComponents(){
        tvExternalID.setText(parent.ficha_ci.getId());
        tvContactoCliente.setError("");
        etMontoPagoRequerido.setText(Miscellaneous.moneyFormat(String.valueOf(parent.ficha_ci.getPrestamo().getPagoSemanal())));
        etSaldoCorte.setText(Miscellaneous.moneyFormat(String.valueOf(parent.ficha_ci.getPrestamo().getSaldoActual())));
    }

    //=========================  Comportamientos  ================================================
    private void SelectContactoCliente (int pos){

        if (rbSiGerente.isChecked() || rbNoGerente.isChecked()) tvEstaGerente.setError(null);
        else tvEstaGerente.setError("");
        switch (pos){
            case 0: // No contacto cliente
                etComentario.setText("");
                etComentario.setError(getResources().getString(R.string.campo_requerido));
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
                tvActualizarTelefono.setError("");
                tvResultadoGestion.setError("");
                etInfoDesfasada.setText("");
                etComentario.setText("");
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
                llComentario.setVisibility(View.VISIBLE);
                llFotoFachada.setVisibility(View.GONE);
                llEstaGerente.setVisibility(View.VISIBLE);
                llInfoDesfasada.setVisibility(View.VISIBLE);
                break;
            default: //Sin seleccionar una opción o cualquier otro valor
                etInfoDesfasada.setText("");
                etComentario.setText("");
                rgResultadoPago.clearCheck();
                SelectPagoRequerido(-1);
                rgActualizarTelefono.clearCheck();
                SelectActualizarTelefono(-1);
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
                rgPagaraRequerido.clearCheck();
                SelectPagoRequerido(-1);
                llMedioPago.setVisibility(View.GONE);
                llMontoPagoRequerido.setVisibility(View.GONE);
                llSaldoCorte.setVisibility(View.GONE);
                llSaldoActual.setVisibility(View.GONE);
                llComentario.setVisibility(View.VISIBLE);
                etComentario.setError(getResources().getString(R.string.campo_requerido));
                llMotivoNoPago.setVisibility(View.VISIBLE);
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
                rgPagaraRequerido.clearCheck();
                SelectPagoRequerido(-1);
                llMedioPago.setVisibility(View.GONE);
                llMontoPagoRequerido.setVisibility(View.GONE);
                llSaldoCorte.setVisibility(View.GONE);
                llSaldoActual.setVisibility(View.GONE);
                llMotivoNoPago.setVisibility(View.GONE);
                llComentario.setVisibility(View.GONE);
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
            if (pos > 0 && pos < 7) {
                etFechaDeposito.setEnabled(false);
            }else {
                etFechaDeposito.setEnabled(true);
            }
        }
        if (rbSiRequerido.isChecked() || rbNoRequerido.isChecked()) tvPagaraRequerido.setError(null);
        else tvPagaraRequerido.setError("");

        tvMedioPago.setError(null);
        switch (pos){
            case 0: // Opción "Seleccione una opción"
                tvMedioPago.setError("");
                rgPagaraRequerido.clearCheck();
                SelectPagoRequerido(-1);
                llPagaraRequerido.setVisibility(View.GONE);
                llFechaDeposito.setVisibility(View.GONE);
                break;
            case 1: // Banamex
            case 2: // Banorte
            case 3: // Telecom
            case 4: // Bansefi
            case 5: // Bancomer
            case 6: // Oxxo
                llPagaraRequerido.setVisibility(View.VISIBLE);
                llFechaDeposito.setVisibility(View.VISIBLE);
                break;
            case 7: // Efectivo
            case 8: // Sidert
                llPagaraRequerido.setVisibility(View.VISIBLE);
                llFechaDeposito.setVisibility(View.GONE);
                break;
            default: //Sin seleccionar una opción o cualquier otro valor
                tvMedioPago.setError("");
                rgPagaraRequerido.clearCheck();
                SelectPagoRequerido(-1);
                llPagaraRequerido.setVisibility(View.GONE);
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

    private void SelectPagoRequerido (int pos){
        switch (pos){
            case -1: //Sin seleccionar una opción o cualquier otro valor
                etPagoRealizado.setText(String.valueOf(parent.ficha_ci.getPrestamo().getPagoRealizado()));
                etPagoRealizado.setEnabled(false);
                llMontoPagoRealizado.setVisibility(View.GONE);
                llMontoCorrecto.setVisibility(View.GONE);
                rgMontoCorrecto.clearCheck();
                SelectMontoCorrecto(-1);
                break;
            case 0: // No pagará requerido
                tvMontoCorrecto.setError("");
                etPagoRealizado.setEnabled(true);
                llMontoPagoRealizado.setVisibility(View.VISIBLE);
                llMontoCorrecto.setVisibility(View.VISIBLE);
                break;
            case 1: // Si pagará requerido
                tvMontoCorrecto.setError("");
                etPagoRealizado.setText(String.valueOf(parent.ficha_ci.getPrestamo().getPagoRealizado()));
                etPagoRealizado.setEnabled(false);
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

                for (int i = 0; i < rgPagaraRequerido.getChildCount(); i++) {
                    rgPagaraRequerido.getChildAt(i).setEnabled(true);
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
                for (int i = 0; i < rgPagaraRequerido.getChildCount(); i++) {
                    rgPagaraRequerido.getChildAt(i).setEnabled(false);
                }
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
                if (spMedioPago.getSelectedItemPosition() >  0 && spMedioPago.getSelectedItemPosition() < 7){
                    etFechaDeposito.setEnabled(true);
                }
                for (int i = 0; i < rgPagaraRequerido.getChildCount(); i++) {
                    rgPagaraRequerido.getChildAt(i).setEnabled(true);
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
                OrderModel order = new OrderModel(parent.ficha_ci.getId(),
                        "002",
                        parent.ficha_ci.getPrestamo().getMontoPrestamo(),
                        parent.ficha_ci.getPrestamo().getPagoSemanal(),
                        Miscellaneous.doubleFormat(etPagoRealizado),
                        parent.ficha_ci.getCliente().getNumeroCliente(),
                        parent.ficha_ci.getPrestamo().getNumeroDePrestamo(),
                        parent.ficha_ci.getCliente().getNombre(),
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

    //===================== Listener GPS  =======================================================

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

//        Polyline line = mMap.addPolyline(new PolylineOptions()
//                .add(new LatLng(lat, lng), new LatLng(19.201745,-96.162134))
//                .width(5)
//                .color(Color.RED));

        pbLoading.setVisibility(View.GONE);
        ibMap.setVisibility(View.GONE);
    }

    private void CancelUbicacion (){
        if (flagUbicacion)
            locationManager.removeUpdates(locationListener);
    }

    //===================  Resultado de activities  ===========================================
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case REQUEST_CODE_SIGNATURE:
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
