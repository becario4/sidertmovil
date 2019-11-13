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
import android.text.InputType;
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
import com.sidert.sidertmovil.activities.CarteraVencidaIndividual;
import com.sidert.sidertmovil.activities.PrintSeewoo;
import com.sidert.sidertmovil.activities.CapturarFirma;
import com.sidert.sidertmovil.models.OrderModel;
import com.sidert.sidertmovil.utils.Constants;
import com.sidert.sidertmovil.utils.Miscellaneous;
import com.sidert.sidertmovil.utils.MyCurrentListener;
import com.sidert.sidertmovil.utils.Popups;
import com.sidert.sidertmovil.utils.Validator;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class cvi_gestion_fragment extends Fragment {

    private Context ctx;

    private TextView tvExternalID;
    private TextView tvContactoCliente;
    private TextView tvMotivoNoContacto;
    private TextView tvActualizarTelefono;
    private TextView tvResultadoGestion;
    private TextView tvMedioPago;
    private TextView tvPagaraRequerido;
    private TextView tvArqueoCaja;
    private TextView tvMapa;
    private TextView tvImprimirRecibo;
    private TextView tvFotoGaleria;
    private TextView tvFirma;
    private TextView tvMotivoAclaracion;
    private TextView tvEstaGerente;
    private TextView tvFotoFachada;
    private TextView tvMotivoNoPago;

    public EditText etActualizarTelefono;
    public EditText etComentario;
    public EditText etMotivoAclaracion;
    public EditText etMotivoNoContacto;
    public EditText etMontoPagoRequerido;
    public EditText etFechaDefuncion;
    public EditText etPagoRealizado;
    public EditText etFechaDeposito;
    public EditText etFolioRecibo;

    public Spinner spMotivoNoPago;
    public Spinner spMedioPago;

    public RadioGroup rgContactoCliente;
    public RadioGroup rgActualizarTelefono;
    public RadioGroup rgResultadoPago;
    public RadioGroup rgPagaraRequerido;
    public RadioGroup rgRecibos;
    public RadioGroup rgEstaGerente;

    public RadioButton rbSiRequerido;
    public RadioButton rbNoRequerido;
    public RadioButton rbArqueoCaja;
    public RadioButton rbSiGerente;
    public RadioButton rbNoGerente;

    private ImageButton ibMap;
    private ImageButton ibFoto;
    private ImageButton ibFotoFachada;
    private ImageButton ibGaleria;
    private ImageButton ibFirma;
    private ImageButton ibImprimir;
    private ImageButton ibArqueoCaja;

    private ImageView ivFotoFachada;
    private ImageView ivFirma;
    private ImageView ivEvidencia;

    private LinearLayout llActualizarTelefono;
    private LinearLayout llMotivoNoContacto;
    private LinearLayout llComentario;
    private LinearLayout llFotoFachada;
    private LinearLayout llDatosDefuncion;
    private LinearLayout llMotivoAclaracion;
    private LinearLayout llMotivoNoPago;
    private LinearLayout llMontoPagoRequerido;
    private LinearLayout llMedioPago;
    private LinearLayout llResultadoGestion;
    private LinearLayout llArqueoCaja;
    private LinearLayout llPagaraRequerido;
    private LinearLayout llMontoPagoRealizado;
    private LinearLayout llFechaDeposito;
    private LinearLayout llImprimirRecibo;
    private LinearLayout llFolioRecibo;
    private LinearLayout llFotoGaleria;
    private LinearLayout llEstaGerente;
    private LinearLayout llFirma;

    private String[] _motivo_aclaracion;
    private String[] _motivoNoContacto;

    private Calendar myCalendar;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
    private Date minDate;

    private final int REQUEST_CODE_SIGNATURE = 456;

    CarteraVencidaIndividual parent;
    private Validator validator;

    private boolean flagUbicacion = false;

    private LocationManager locationManager;
    private MyCurrentListener locationListener;

    private ProgressBar pbLoading;

    private MapView mapView;
    private GoogleMap mMap;
    private Marker mMarker;
    public LatLng latLngGestion;

    private Uri imageUri;

    public byte[] byteEvidencia;
    public byte[] byteFirma;

    private String folio_impreso = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cvi_gestion,container, false );

        ctx             = getContext();

        parent                = (CarteraVencidaIndividual) getActivity();
        assert parent != null;
        parent.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        tvExternalID            = view.findViewById(R.id.tvExternalID);
        tvMotivoNoContacto      = view.findViewById(R.id.tvMotivoNoContacto);
        tvContactoCliente       = view.findViewById(R.id.tvContactoCliente);
        tvActualizarTelefono     = view.findViewById(R.id.tvActualizarTelefono);
        tvResultadoGestion      = view.findViewById(R.id.tvResultadoGestion);
        tvMedioPago             = view.findViewById(R.id.tvMedioPago);
        tvPagaraRequerido       = view.findViewById(R.id.tvPagaraRequerido);
        tvArqueoCaja            = view.findViewById(R.id.tvArqueoCaja);
        tvMotivoAclaracion      = view.findViewById(R.id.tvMotivoAclaracion);
        tvEstaGerente           = view.findViewById(R.id.tvEstaGerente);
        tvFotoFachada           = view.findViewById(R.id.tvFotoFachada);
        tvMotivoNoPago          = view.findViewById(R.id.tvMotivoNoPago);
        tvMapa                  = view.findViewById(R.id.tvMapa);
        tvImprimirRecibo        = view.findViewById(R.id.tvImprimirRecibo);
        tvFotoGaleria           = view.findViewById(R.id.tvFotoGaleria);
        tvFirma                 = view.findViewById(R.id.tvFirma);

        etActualizarTelefono    = view.findViewById(R.id.etActualizarTelefono);
        etComentario            = view.findViewById(R.id.etComentario);
        etMotivoAclaracion      = view.findViewById(R.id.etMotivoAclaracion);
        etMotivoNoContacto      = view.findViewById(R.id.etMotivoNoContacto);
        etMontoPagoRequerido    = view.findViewById(R.id.etMontoPagoRequerido);
        etFechaDefuncion        = view.findViewById(R.id.etFechaDefuncion);
        etPagoRealizado         = view.findViewById(R.id.etPagoRealizado);
        etFechaDeposito         = view.findViewById(R.id.etFechaDeposito);
        etFolioRecibo           = view.findViewById(R.id.etFolioRecibo);

        spMedioPago             = view.findViewById(R.id.spMedioPago);
        spMotivoNoPago          = view.findViewById(R.id.spMotivoNoPago);

        rgContactoCliente       = view.findViewById(R.id.rgContactoCliente);
        rgActualizarTelefono    = view.findViewById(R.id.rgActualizarTelefono);
        rgResultadoPago         = view.findViewById(R.id.rgResultadoPago);
        rgPagaraRequerido       = view.findViewById(R.id.rgPagaraRequerido);
        rgRecibos               = view.findViewById(R.id.rgRecibos);
        rgEstaGerente           = view.findViewById(R.id.rgEstaGerente);

        rbSiRequerido   = view.findViewById(R.id.rbSiRequerido);
        rbNoRequerido   = view.findViewById(R.id.rbNoRequerido);
        rbArqueoCaja    = view.findViewById(R.id.rbArqueoCaja);
        rbSiGerente     = view.findViewById(R.id.rbSiGerente);
        rbNoGerente     = view.findViewById(R.id.rbNoGerente);

        ibMap           = view.findViewById(R.id.ibMap);
        ibFotoFachada   = view.findViewById(R.id.ibFotoFachada);
        ibFoto          = view.findViewById(R.id.ibFoto);
        ibGaleria       = view.findViewById(R.id.ibGaleria);
        ibFirma         = view.findViewById(R.id.ibFirma);
        ibArqueoCaja    = view.findViewById(R.id.ibArqueoCaja);
        ibImprimir      = view.findViewById(R.id.ibImprimir);
        ibArqueoCaja   = view.findViewById(R.id.ibArqueoCaja);

        ivFotoFachada   = view.findViewById(R.id.ivFotoFachada);
        ivFirma     = view.findViewById(R.id.ivFirma);
        ivEvidencia = view.findViewById(R.id.ivEvidencia);

        pbLoading       = view.findViewById(R.id.pbLoading);

        mapView = view.findViewById(R.id.mapGestion);

        llMotivoNoContacto      = view.findViewById(R.id.llMotivoNoContacto);
        llComentario            = view.findViewById(R.id.llComentario);
        llActualizarTelefono    = view.findViewById(R.id.llActualizarTelefono);
        llResultadoGestion      = view.findViewById(R.id.llResultadoGestion);
        llFotoFachada           = view.findViewById(R.id.llFotoFachada);
        llDatosDefuncion        = view.findViewById(R.id.llDatosDefuncion);
        llMotivoAclaracion      = view.findViewById(R.id.llMotivoAclaracion);
        llMotivoNoPago          = view.findViewById(R.id.llMotivoNoPago);
        llMontoPagoRequerido    = view.findViewById(R.id.llMontoPagoRequerido);
        llMedioPago             = view.findViewById(R.id.llMedioPago);
        llArqueoCaja            = view.findViewById(R.id.llArqueoCaja);
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
            minDate = sdf.parse("01/08/2019");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        _motivo_aclaracion = getResources().getStringArray(R.array.outdated_information);
        _motivoNoContacto = getResources().getStringArray(R.array.motivo_no_contacto);

        // EditText Click
        etMotivoAclaracion.setOnClickListener(etMotivoAclracion_OnClick);
        etMotivoNoContacto.setOnClickListener(etMotivoNoContacto_OnClick);
        etFechaDefuncion.setOnClickListener(etFechaDefuncion_OnClick);
        etFechaDeposito.setOnClickListener(etFechaDeposito_OnClick);

        // ImageButton Click
        ibMap.setOnClickListener(ibMap_OnClick);
        ibFoto.setOnClickListener(ibFoto_OnClick);
        ibGaleria.setOnClickListener(ibGaleria_OnClick);
        ibFirma.setOnClickListener(ibFirma_OnClick);
        ibFotoFachada.setOnClickListener(ibFotoFachada_OnClick);
        ibFirma.setOnClickListener(ibFirma_OnClick);
        ibImprimir.setOnClickListener(ibImprimir_OnClick);
        ibArqueoCaja.setOnClickListener(ibArqueoCaja_OnClick);


        // RadioGroup Click
        rgContactoCliente.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                tvContactoCliente.setError(null);
                tvActualizarTelefono.setError(null);
                tvMotivoAclaracion.setError(null);
                etMotivoAclaracion.setError(null);
                etComentario.setError(null);
                tvFotoFachada.setError(null);
                switch (checkedId) {
                    case R.id.rbSiContacto:
                        SelectContactoCliente(1);
                        break;
                    case R.id.rbNoContacto:
                        SelectContactoCliente(0);
                        break;
                    case R.id.rbAclaracion:
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

        rgPagaraRequerido.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                tvPagaraRequerido.setError(null);
                switch (checkedId){
                    case R.id.rbSiRequerido:
                        if (Double.parseDouble(etMontoPagoRequerido.getText().toString().trim()) > 0){
                            SelectPagoRequerido(1);
                        }
                        else {
                            Toast.makeText(ctx, "No se pueden capturar pagos iguales a cero", Toast.LENGTH_SHORT).show();
                            rbSiRequerido.setChecked(false);
                            tvPagaraRequerido.setError("");
                            SelectPagoRequerido(-1);
                        }
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
                SelectMedioPago(position);
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
        ivEvidencia.setOnClickListener(ivEvidencia_OnClick);
        ivFotoFachada.setOnClickListener(ivFotoFachada_OnClick);
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

    private View.OnClickListener ivEvidencia_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (spMedioPago.getSelectedItemPosition() > 0 && spMedioPago.getSelectedItemPosition() < 7){
                final AlertDialog evidencia_dlg = Popups.showDialogConfirmImage(ctx, Constants.question,
                        R.string.capturar_foto_galeria, R.string.fotografia, new Popups.DialogMessage() {
                            @Override
                            public void OnClickListener(AlertDialog dialog) {
                                Intent i = new Intent(parent, CameraVertical.class);
                                i.putExtra(Constants.ORDER_ID, parent.ficha_cvi.getId());
                                startActivityForResult(i, Constants.REQUEST_CODE_CAMARA_TICKET);
                                dialog.dismiss();

                            }
                        }, R.string.galeria, new Popups.DialogMessage() {
                            @Override
                            public void OnClickListener(AlertDialog dialog) {
                                Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                                gallery.setAction(Intent.ACTION_GET_CONTENT);
                                startActivityForResult(Intent.createChooser(gallery, "Select Picture"), Constants.REQUEST_CODE_GALERIA);
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
                final AlertDialog evidencia_dlg = Popups.showDialogConfirm(ctx, Constants.question,
                        R.string.capturar_nueva_fotografia, R.string.fotografia, new Popups.DialogMessage() {
                            @Override
                            public void OnClickListener(AlertDialog dialog) {
                                Intent i = new Intent(parent, CameraVertical.class);
                                i.putExtra(Constants.ORDER_ID, parent.ficha_cvi.getId());
                                startActivityForResult(i, Constants.REQUEST_CODE_CAMARA_TICKET);
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
        }
    };

    private View.OnClickListener ivFotoFachada_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            final AlertDialog fachada_cam_dlg = Popups.showDialogConfirm(ctx, Constants.camara,
                    R.string.capturar_nueva_fotografia, R.string.accept, new Popups.DialogMessage() {
                        @Override
                        public void OnClickListener(AlertDialog dialog) {
                            Intent i = new Intent(parent, CameraVertical.class);
                            i.putExtra(Constants.ORDER_ID, parent.ficha_cvi.getId());
                            startActivityForResult(i, Constants.REQUEST_CODE_CAMARA_FACHADA);
                            dialog.dismiss();

                        }
                    }, R.string.cancel, new Popups.DialogMessage() {
                        @Override
                        public void OnClickListener(AlertDialog dialog) {
                            dialog.dismiss();
                        }
                    });
            Objects.requireNonNull(fachada_cam_dlg.getWindow()).requestFeature(Window.FEATURE_NO_TITLE);
            fachada_cam_dlg.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            fachada_cam_dlg.show();
        }
    };

    private View.OnClickListener etMotivoAclracion_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
            builder.setTitle(R.string.selected_option)
                    .setItems(R.array.outdated_information, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int position) {
                            tvMotivoAclaracion.setError(null);
                            etMotivoAclaracion.setText(_motivo_aclaracion[position]);
                        }
                    });
            builder.create();
            builder.show();
        }
    };

    private View.OnClickListener etMotivoNoContacto_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
            builder.setTitle(R.string.selected_option)
                    .setItems(R.array.motivo_no_contacto, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int position) {
                            tvMotivoNoContacto.setError(null);
                            etMotivoNoContacto.setText(_motivoNoContacto[position]);
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
                        tvMapa.setError(null);
                        mapView.setVisibility(View.VISIBLE);
                        ColocarUbicacionGestion(Double.parseDouble(latitud), Double.parseDouble(longitud));
                    }
                    else{
                        pbLoading.setVisibility(View.GONE);
                        tvMapa.setError("");
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

            locationManager.requestSingleUpdate(LocationManager.NETWORK_PROVIDER, locationListener,null);
        }
    };

    private View.OnClickListener ibFoto_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i = new Intent(parent, CameraVertical.class);
            i.putExtra(Constants.ORDER_ID, parent.ficha_cvi.getId());
            startActivityForResult(i, Constants.REQUEST_CODE_CAMARA_TICKET);
        }
    };

    private View.OnClickListener ibFotoFachada_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i = new Intent(parent, CameraVertical.class);
            i.putExtra(Constants.ORDER_ID, parent.ficha_cvi.getId());
            startActivityForResult(i, Constants.REQUEST_CODE_CAMARA_FACHADA);
        }
    };

    private View.OnClickListener ibGaleria_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
            gallery.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(gallery, "Select Picture"), Constants.REQUEST_CODE_GALERIA);
        }
    };

    private View.OnClickListener ibFirma_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent sig = new Intent(ctx, CapturarFirma.class);
            startActivityForResult(sig, REQUEST_CODE_SIGNATURE);
        }
    };

    // Button Click Event
    private View.OnClickListener ibArqueoCaja_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i_arqueoCaja = new Intent(ctx, ArqueoDeCaja.class);
            i_arqueoCaja.putExtra(Constants.PAGO_REALIZADO, Double.parseDouble(etPagoRealizado.getText().toString()));
            i_arqueoCaja.putExtra(Constants.PAGO_REQUERIDO, parent.ficha_cvi.getPrestamo().getPagoSemanal());
            i_arqueoCaja.putExtra(Constants.NOMBRE_GRUPO, parent.ficha_cvi.getCliente().getNombre());
            i_arqueoCaja.putExtra(Constants.ORDER_ID, parent.ficha_cvi.getId());
            startActivityForResult(i_arqueoCaja, Constants.REQUEST_CODE_ARQUEO_CAJA);
        }
    };

    private View.OnClickListener ibImprimir_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (!etPagoRealizado.getText().toString().trim().isEmpty() && Double.parseDouble(etPagoRealizado.getText().toString().trim()) > 0){
                Intent i = new Intent(ctx, PrintSeewoo.class);
                OrderModel order = new OrderModel(parent.ficha_cvi.getId(),
                        "002",
                        parent.ficha_cvi.getPrestamo().getMontoPrestamo(),
                        parent.ficha_cvi.getPrestamo().getPagoSemanal(),
                        Miscellaneous.doubleFormat(etPagoRealizado),
                        parent.ficha_cvi.getCliente().getNumeroCliente(),
                        parent.ficha_cvi.getPrestamo().getNumeroDePrestamo(),
                        parent.ficha_cvi.getCliente().getNombre(),
                        "NOMBRE DEL ALGUN ASESOR",
                        0);

                i.putExtra("order",order);
                i.putExtra("tag",true);

                startActivityForResult(i,Constants.REQUEST_CODE_IMPRESORA);
            }
            else
                Toast.makeText(ctx, "No ha capturado el pago realizado del cliente", Toast.LENGTH_SHORT).show();

        }
    };

    // EditText Click Event
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
        tvExternalID.setText(parent.ficha_cvi.getId());
        tvMapa.setError("");
        tvContactoCliente.setError("");
        etMontoPagoRequerido.setText(String.valueOf(parent.ficha_cvi.getPrestamo().getPagoSemanal()));
    }

    //=========================  Comportamientos  ================================================
    private void SelectContactoCliente (int pos){
        if (rbSiGerente.isChecked() || rbNoGerente.isChecked()) tvEstaGerente.setError(null);
        else tvEstaGerente.setError("");
        switch (pos){
            case 0: // No contacto cliente
                tvFotoFachada.setError("");
                etMotivoAclaracion.setText("");
                rgResultadoPago.clearCheck();
                SelectResultadoGestion(-1);
                rgActualizarTelefono.clearCheck();
                SelectActualizarTelefono(-1);
                llActualizarTelefono.setVisibility(View.GONE);
                rgEstaGerente.clearCheck();
                SelectEstaGerente(-1);
                byteEvidencia = null;
                ivFotoFachada.setVisibility(View.GONE);
                ibFotoFachada.setVisibility(View.VISIBLE);
                llResultadoGestion.setVisibility(View.GONE);
                llComentario.setVisibility(View.VISIBLE);
                etComentario.setError(getResources().getString(R.string.campo_requerido));
                llFotoFachada.setVisibility(View.VISIBLE);
                llEstaGerente.setVisibility(View.VISIBLE);
                llMotivoAclaracion.setVisibility(View.GONE);
                tvMotivoNoContacto.setError("");
                etMotivoNoContacto.setText("");
                llMotivoNoContacto.setVisibility(View.VISIBLE);
                break;
            case 1: // Si contacto cliente
                tvActualizarTelefono.setError("");
                tvResultadoGestion.setError("");
                etMotivoAclaracion.setText("");
                rgResultadoPago.clearCheck();
                SelectResultadoGestion(-1);
                rgEstaGerente.clearCheck();
                SelectEstaGerente(-1);
                llResultadoGestion.setVisibility(View.VISIBLE);
                llActualizarTelefono.setVisibility(View.VISIBLE);
                llMotivoNoContacto.setVisibility(View.GONE);
                llComentario.setVisibility(View.GONE);
                llFotoFachada.setVisibility(View.GONE);
                llEstaGerente.setVisibility(View.GONE);
                llFirma.setVisibility(View.GONE);
                llMotivoAclaracion.setVisibility(View.GONE);
                break;
            case 2: // Aclaración
                tvMotivoAclaracion.setError("");
                etMotivoAclaracion.setText("");
                etComentario.setError(getResources().getString(R.string.campo_requerido));
                etComentario.setText("");
                etMotivoNoContacto.setText("");
                rgResultadoPago.clearCheck();
                SelectResultadoGestion(-1);
                rgEstaGerente.clearCheck();
                SelectEstaGerente(-1);
                byteEvidencia = null;
                ivFotoFachada.setVisibility(View.GONE);
                ibFotoFachada.setVisibility(View.VISIBLE);
                rgActualizarTelefono.clearCheck();
                SelectActualizarTelefono(-1);
                llMotivoNoContacto.setVisibility(View.GONE);
                llActualizarTelefono.setVisibility(View.GONE);
                llResultadoGestion.setVisibility(View.GONE);
                llComentario.setVisibility(View.VISIBLE);
                llFotoFachada.setVisibility(View.GONE);
                llEstaGerente.setVisibility(View.VISIBLE);
                llMotivoAclaracion.setVisibility(View.VISIBLE);
                break;
            default: //Sin seleccionar una opción o cualquier otro valor
                etMotivoAclaracion.setText("");
                rgResultadoPago.clearCheck();
                rgActualizarTelefono.clearCheck();
                SelectActualizarTelefono(-1);
                etMotivoNoContacto.setText("");
                llMotivoNoContacto.setVisibility(View.GONE);
                llActualizarTelefono.setVisibility(View.GONE);
                llResultadoGestion.setVisibility(View.GONE);
                llComentario.setVisibility(View.GONE);
                llFotoFachada.setVisibility(View.GONE);
                llEstaGerente.setVisibility(View.GONE);
                llMotivoAclaracion.setVisibility(View.GONE);
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
                SelectMedioPago(0);
                rgPagaraRequerido.clearCheck();
                SelectPagoRequerido(-1);
                llMedioPago.setVisibility(View.GONE);
                llMontoPagoRequerido.setVisibility(View.GONE);
                llComentario.setVisibility(View.VISIBLE);
                etComentario.setError(getResources().getString(R.string.campo_requerido));
                llMotivoNoPago.setVisibility(View.VISIBLE);
                llFotoFachada.setVisibility(View.VISIBLE);
                llEstaGerente.setVisibility(View.VISIBLE);
                break;
            case 1: //Si Pago
                spMotivoNoPago.setSelection(0);
                SelectMotivoNoPago(0);
                llComentario.setVisibility(View.GONE);
                llMedioPago.setVisibility(View.VISIBLE);
                llMontoPagoRequerido.setVisibility(View.VISIBLE);
                llMotivoNoPago.setVisibility(View.GONE);
                llFotoFachada.setVisibility(View.GONE);
                llEstaGerente.setVisibility(View.GONE);
                break;
            default: //Sin seleccionar una opción o cualquier otro valor
                tvResultadoGestion.setError("");
                spMedioPago.setSelection(0);
                SelectMedioPago(0);
                spMotivoNoPago.setSelection(0);
                SelectMotivoNoPago(0);
                rgPagaraRequerido.clearCheck();
                SelectPagoRequerido(-1);
                llMedioPago.setVisibility(View.GONE);
                llMontoPagoRequerido.setVisibility(View.GONE);
                llComentario.setVisibility(View.GONE);
                llMotivoNoPago.setVisibility(View.GONE);
                llFotoFachada.setVisibility(View.GONE);
                llEstaGerente.setVisibility(View.GONE);
                break;
        }
    }

    private void SelectMedioPago (int pos){
        if (rgContactoCliente.getChildAt(0).isEnabled()){
            etFechaDeposito.setText("");
        }
        else {
            spMedioPago.setEnabled(false);
            if (pos == 1) {
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
                llFotoGaleria.setVisibility(View.GONE);
                llImprimirRecibo.setVisibility(View.GONE);
                llEstaGerente.setVisibility(View.GONE);
                break;
            case 1: // Banamex 722
                if (byteEvidencia != null)
                    tvFotoGaleria.setError(null);
                else
                    tvFotoGaleria.setError("");
                ibGaleria.setEnabled(true);
                ibGaleria.setBackground(ctx.getResources().getDrawable(R.drawable.round_corner_blue));
                if (etFechaDeposito.getText().toString().isEmpty())
                    etFechaDeposito.setError(getResources().getString(R.string.campo_requerido));
                llPagaraRequerido.setVisibility(View.VISIBLE);
                llFechaDeposito.setVisibility(View.VISIBLE);
                llMontoPagoRequerido.setVisibility(View.VISIBLE);
                llImprimirRecibo.setVisibility(View.GONE);
                llFotoGaleria.setVisibility(View.VISIBLE);
                llEstaGerente.setVisibility(View.VISIBLE);
                break;
            case 2: // Efectivo
                if (byteEvidencia != null)
                    tvFotoGaleria.setError(null);
                else
                    tvFotoGaleria.setError("");
                ibGaleria.setEnabled(false);
                ibGaleria.setBackground(ctx.getResources().getDrawable(R.drawable.btn_disable));
                if (!etFolioRecibo.getText().toString().trim().isEmpty())
                    tvImprimirRecibo.setError(null);
                else
                    tvImprimirRecibo.setError("");
                llPagaraRequerido.setVisibility(View.VISIBLE);
                llFechaDeposito.setVisibility(View.GONE);
                llMontoPagoRequerido.setVisibility(View.VISIBLE);
                llImprimirRecibo.setVisibility(View.VISIBLE);
                llFotoGaleria.setVisibility(View.VISIBLE);
                llEstaGerente.setVisibility(View.VISIBLE);
                break;
            default: //Sin seleccionar una opción o cualquier otro valor
                tvMedioPago.setError("");
                rgPagaraRequerido.clearCheck();
                rgRecibos.clearCheck();
                SelectImprimirRecibos(-1);
                SelectPagoRequerido(-1);
                ivEvidencia.setVisibility(View.GONE);
                llPagaraRequerido.setVisibility(View.GONE);
                llFechaDeposito.setVisibility(View.GONE);
                llFotoGaleria.setVisibility(View.GONE);
                llImprimirRecibo.setVisibility(View.GONE);
                llEstaGerente.setVisibility(View.GONE);
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
            case 1: // Insolvente
            case 2: // Negación de pago
            case 4: // Otro
                llDatosDefuncion.setVisibility(View.GONE);
                break;
            case 3: //Fallecimiento
                etFechaDefuncion.setError("");
                llDatosDefuncion.setVisibility(View.VISIBLE);
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
                etPagoRealizado.setText(String.valueOf(parent.ficha_cvi.getPrestamo().getPagoRealizado()));
                etPagoRealizado.setEnabled(false);
                llMontoPagoRealizado.setVisibility(View.GONE);
                break;
            case 0: // No pagará requerido
                etPagoRealizado.setEnabled(true);
                llMontoPagoRealizado.setVisibility(View.VISIBLE);
                break;
            case 1: // Si pagará requerido
                etPagoRealizado.setText(String.valueOf(parent.ficha_cvi.getPrestamo().getPagoRealizado()));
                etPagoRealizado.setEnabled(false);
                llMontoPagoRealizado.setVisibility(View.VISIBLE);
                break;
        }
    }

    private void SelectImprimirRecibos(int pos){
        switch (pos){
            case 0: //No cuenta con bateria la impresora
                etFolioRecibo.setError(getResources().getString(R.string.campo_requerido));
                tvImprimirRecibo.setError(null);
                llFolioRecibo.setVisibility(View.VISIBLE);
                etFolioRecibo.setText("");
                etFolioRecibo.setEnabled(true);
                etFolioRecibo.setHint(R.string.folio_sidert);
                etFolioRecibo.setInputType(InputType.TYPE_CLASS_NUMBER);
                ibImprimir.setVisibility(View.GONE);
                break;
            case 1: // Imprimir Recibos
                ibImprimir.setVisibility(View.VISIBLE);
                if (!folio_impreso.trim().isEmpty()) {
                    tvImprimirRecibo.setError(null);
                    etFolioRecibo.setText(folio_impreso);
                    etFolioRecibo.setEnabled(false);
                    llFolioRecibo.setVisibility(View.VISIBLE);
                }
                else {
                    tvImprimirRecibo.setError("");
                    llFolioRecibo.setVisibility(View.GONE);
                }
                break;
            default: // Sin seleccionar alguna opción o cualquier valor diferente
                tvImprimirRecibo.setError("");
                llFolioRecibo.setVisibility(View.GONE);
                ibImprimir.setVisibility(View.GONE);
                break;
        }

    }

    private void SelectEstaGerente (int pos){
        switch (pos){
            case 0: // No está el gerente
                llFirma.setVisibility(View.GONE);
                break;
            case 1: // Si está el gerente
                if (ivFirma.getVisibility() == View.VISIBLE && byteFirma != null)
                    tvFirma.setError(null);
                else
                    tvFirma.setError("");
                llFirma.setVisibility(View.VISIBLE);
                break;
            default: // Sin seleccionar alguna opción o cualquier valor diferente
                byteFirma = null;
                ivFirma.setVisibility(View.GONE);
                ibFirma.setVisibility(View.VISIBLE);
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
        latLngGestion = coordenadas;
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
                        tvFirma.setError(null);
                        Glide.with(ctx)
                                .load(data.getByteArrayExtra(Constants.FIRMA_IMAGE))
                                .into(ivFirma);
                        byteFirma = data.getByteArrayExtra(Constants.FIRMA_IMAGE);
                    }
                }
                break;
            case Constants.REQUEST_CODE_IMPRESORA:
                if (resultCode == Activity.RESULT_OK){
                    if (data != null){
                        Toast.makeText(ctx, data.getStringExtra(Constants.MESSAGE), Toast.LENGTH_SHORT).show();
                        if(data.getIntExtra(Constants.RES_PRINT,0) == 1 || data.getIntExtra(Constants.RES_PRINT,0) == 2){
                            folio_impreso = "Gestor586-" + String.valueOf(data.getIntExtra(Constants.FOLIO,0));
                            etFolioRecibo.setText(folio_impreso);
                            tvImprimirRecibo.setError(null);
                            llFolioRecibo.setVisibility(View.VISIBLE);
                        }
                    }
                }
                break;
            case Constants.REQUEST_CODE_GALERIA:
                if (data != null){
                    ibFoto.setVisibility(View.GONE);
                    ibGaleria.setVisibility(View.GONE);
                    tvFotoGaleria.setError(null);
                    imageUri = data.getData();
                    ivEvidencia.setVisibility(View.VISIBLE);
                    Glide.with(ctx).load(imageUri).centerCrop().into(ivEvidencia);

                    try {
                        InputStream iStream =   ctx.getContentResolver().openInputStream(imageUri);
                        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
                        int bufferSize = 1024;
                        byte[] buffer = new byte[bufferSize];

                        int len = 0;
                        while ((len = iStream.read(buffer)) != -1) {
                            byteBuffer.write(buffer, 0, len);
                        }
                        byteEvidencia = byteBuffer.toByteArray();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case Constants.REQUEST_CODE_CAMARA_TICKET:
                if (resultCode == Activity.RESULT_OK){
                    if (data != null){
                        ibFoto.setVisibility(View.GONE);
                        ibGaleria.setVisibility(View.GONE);
                        tvFotoGaleria.setError(null);
                        ivEvidencia.setVisibility(View.VISIBLE);
                        byteEvidencia = data.getByteArrayExtra(Constants.PICTURE);
                        Glide.with(ctx).load(byteEvidencia).centerCrop().into(ivEvidencia);
                    }
                }
                break;
            case Constants.REQUEST_CODE_CAMARA_FACHADA:
                if (resultCode == Activity.RESULT_OK){
                    if (data != null){
                        ibFotoFachada.setVisibility(View.GONE);
                        tvFotoFachada.setError(null);
                        ivFotoFachada.setVisibility(View.VISIBLE);
                        byteEvidencia = data.getByteArrayExtra(Constants.PICTURE);
                        Glide.with(ctx).load(byteEvidencia).centerCrop().into(ivFotoFachada);
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
