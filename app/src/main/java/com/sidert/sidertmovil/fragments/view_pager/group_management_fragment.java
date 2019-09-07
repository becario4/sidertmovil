package com.sidert.sidertmovil.fragments.view_pager;


import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.ColorDrawable;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.activities.GroupRecovery;
import com.sidert.sidertmovil.activities.IntegrantesGpo;
import com.sidert.sidertmovil.activities.PrintSeewoo;
import com.sidert.sidertmovil.activities.Signature;
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

public class group_management_fragment extends Fragment {

    private Context ctx;

    private TextView tvExternalID;
    private TextView tvContactoCliente;
    private TextView tvResultadoGestion;
    private TextView tvMedioPago;
    private TextView tvDetalleFicha;
    private TextView tvMontoCorrecto;
    private TextView tvInfoDesfasada;
    private TextView tvEstaGerente;
    private TextView tvFotoFachada;
    private TextView tvMotivoNoPago;

    private EditText etInfoDesfasada;
    private EditText etFechaDefuncion;
    private EditText etBanco;
    private EditText etPagoRealizado;
    private EditText etFechaDeposito;
    private EditText etComentarioNoContacto;
    private EditText etMontoPagoRequerido;
    private EditText etComentarioInfoDesfa;
    private EditText etSaldoCorte;
    private EditText etSaldoActual;
    private EditText etMotivoDefuncion;
    private EditText etEspecificaCausa;
    private EditText etEstatusPago;

    private Spinner spMotivoNoPago;
    private Spinner spMedioPago;

    private RadioGroup rgContactoCliente;
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

    private ImageButton ibMap;
    private ImageButton ibFoto;
    private ImageButton imbGallery;
    private ImageButton ibFirma;
    private ImageButton ibFotoCopia;
    private ImageButton ibGaleriaCopia;

    private Button btnIntegrantes;

    private ImageView ivFirma;
    
    private LinearLayout llComentarioNoContacto;
    private LinearLayout llFotoFachada;
    private LinearLayout llDatosDefuncion;
    private LinearLayout llInfoDesfasada;
    private LinearLayout llMotivoNoPago;
    private LinearLayout llMontoPagoRequerido;
    private LinearLayout llBanco;
    private LinearLayout llMedioPago;
    private LinearLayout llResultadoGestion;
    private LinearLayout llEspecificaCausa;
    private LinearLayout llSaldoCorte;
    private LinearLayout llSaldoActual;
    private LinearLayout llFotoGaleria;
    private LinearLayout llFotoGaleriaBoton;
    private LinearLayout llIntegrantes;
    private LinearLayout llComentarioAclaracion;
    private LinearLayout llMontoCorrecto;
    private LinearLayout llEstatusPago;
    private LinearLayout llDetalleFicha;
    private LinearLayout llMontoPagoRealizado;
    private LinearLayout llFechaDeposito;
    private LinearLayout llImprimirRecibo;
    private LinearLayout llFolioRecibo;
    private LinearLayout llEstaGerente;
    private LinearLayout llFirma;

    private String[] _outdate_info;
    private String[] _banks;
    private String[] _photo_galery;

    private Calendar myCalendar;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
    private Date minDate;

    private Validator validator;

    private boolean flagUbicacion = false;

    private LocationManager locationManager;
    private MyCurrentListener locationListener;



    GroupRecovery parent;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_group_management, container, false);
        ctx           = getContext();
        
        parent        = (GroupRecovery) getActivity();
        assert parent != null;
        parent.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        tvExternalID            = view.findViewById(R.id.tvExternalID);
        tvContactoCliente       = view.findViewById(R.id.tvContactoCliente);
        tvResultadoGestion      = view.findViewById(R.id.tvResultadoGestion);
        tvMedioPago             = view.findViewById(R.id.tvMedioPago);
        tvDetalleFicha          = view.findViewById(R.id.tvDetalleFicha);
        tvMontoCorrecto         = view.findViewById(R.id.tvMontoCorrecto);
        tvInfoDesfasada         = view.findViewById(R.id.tvInfoDesfasada);
        tvEstaGerente           = view.findViewById(R.id.tvEstaGerente);
        tvFotoFachada           = view.findViewById(R.id.tvFotoFachada);
        tvMotivoNoPago          = view.findViewById(R.id.tvMotivoNoPago);
        
        etInfoDesfasada         = view.findViewById(R.id.etInfoDesfasada);
        etBanco                 = view.findViewById(R.id.etBanco);
        etFechaDefuncion        = view.findViewById(R.id.etFechaDefuncion);
        etPagoRealizado         = view.findViewById(R.id.etPagoRealizado);
        etFechaDeposito         = view.findViewById(R.id.etFechaDeposito);
        etComentarioNoContacto  = view.findViewById(R.id.etComentarioNoContacto);
        etMontoPagoRequerido    = view.findViewById(R.id.etMontoPagoRequerido);
        etComentarioInfoDesfa   = view.findViewById(R.id.etComentarioInfoDesfa);
        etSaldoCorte            = view.findViewById(R.id.etSaldoCorte);
        etSaldoActual           = view.findViewById(R.id.etSaldoActual);
        etMotivoDefuncion       = view.findViewById(R.id.etMotivoDefuncion);
        etEspecificaCausa       = view.findViewById(R.id.etEspecificaCausa);
        etEstatusPago           = view.findViewById(R.id.etEstatusPago);
        
        spMotivoNoPago  = view.findViewById(R.id.spMotivoNoPago);
        spMedioPago     = view.findViewById(R.id.spMedioPago);

        rgContactoCliente   = view.findViewById(R.id.rgContactoCliente);
        rgResultadoPago     = view.findViewById(R.id.rgResultadoPago);
        rgDetalleFicha      = view.findViewById(R.id.rgDetalleFicha);
        rgMontoCorrecto     = view.findViewById(R.id.rgMontoCorrecto);
        rgRecibos           = view.findViewById(R.id.rgRecibos);
        rgEstaGerente       = view.findViewById(R.id.rgEstaGerente);

        rbSiDetalle     = view.findViewById(R.id.rbSiDetalle);
        rbNoDetalle     = view.findViewById(R.id.rbNoDetalle);
        rbSiCorrecto    = view.findViewById(R.id.rbSiCorrecto);
        rbNoCorrecto    = view.findViewById(R.id.rbNoCorrecto);
        rbSiGerente     = view.findViewById(R.id.rbSiGerente);
        rbNoGerente     = view.findViewById(R.id.rbNoGerente);
        
        ibMap           = view.findViewById(R.id.ibMap);
        ibFoto          = view.findViewById(R.id.ibFoto);
        imbGallery      = view.findViewById(R.id.imbGallery);
        ibFotoCopia     = view.findViewById(R.id.ibFotoCopia);
        ibGaleriaCopia  = view.findViewById(R.id.ibGaleriaCopia);
        ibFirma         = view.findViewById(R.id.ibFirma);

        btnIntegrantes  = view.findViewById(R.id.btnIntegrantes);

        ivFirma     = view.findViewById(R.id.ivFirma);

        llIntegrantes           = view.findViewById(R.id.llIntegrantes);
        llComentarioNoContacto  = view.findViewById(R.id.llComentarioNoContacto);
        llComentarioAclaracion  = view.findViewById(R.id.llComentarioAclaracion);
        llResultadoGestion      = view.findViewById(R.id.llResultadoGestion);
        llFotoFachada           = view.findViewById(R.id.llFotoFachada);
        llDatosDefuncion        = view.findViewById(R.id.llDatosDefuncion);
        llInfoDesfasada         = view.findViewById(R.id.llInfoDesfasada);
        llMotivoNoPago          = view.findViewById(R.id.llMotivoNoPago);
        llMontoPagoRequerido    = view.findViewById(R.id.llMontoPagoRequerido);
        llBanco                 = view.findViewById(R.id.llBanco);
        llMedioPago             = view.findViewById(R.id.llMedioPago);
        llEspecificaCausa       = view.findViewById(R.id.llEspecificaCausa);
        llMontoCorrecto         = view.findViewById(R.id.llMontoCorrecto);
        llEstatusPago           = view.findViewById(R.id.llEstatusPago);
        llSaldoCorte            = view.findViewById(R.id.llSaldoCorte);
        llSaldoActual           = view.findViewById(R.id.llSaldoActual);
        llDetalleFicha       = view.findViewById(R.id.llDetalleFicha);
        llMontoPagoRealizado    = view.findViewById(R.id.llMontoPagoRealizado);
        llImprimirRecibo        = view.findViewById(R.id.llImprimirRecibo);
        llFolioRecibo           = view.findViewById(R.id.llFolioRecibo);
        llFechaDeposito         = view.findViewById(R.id.llFechaDeposito);
        llFotoGaleria           = view.findViewById(R.id.llFotoGaleria);
        llEstaGerente           = view.findViewById(R.id.llEstaGerente);
        llFirma                 = view.findViewById(R.id.llFirma);
        llFotoGaleriaBoton      = view.findViewById(R.id.llFotoGaleriaBoton);

        myCalendar      = Calendar.getInstance();

        spMedioPago.setPrompt("Medio de pago");
        spMotivoNoPago.setPrompt("Motivo no pago");

        //mapView.onCreate(savedInstanceState);
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
        _banks = getResources().getStringArray(R.array.banks);
        _photo_galery = getResources().getStringArray(R.array.files);

        //etPagoRealizado.addTextChangedListener(new CustomWatcher(etPagoRealizado));

        //EditText Click
        etInfoDesfasada.setOnClickListener(etInfoDesfasada_OnClick);
        etFechaDefuncion.setOnClickListener(etFechaDefuncion_OnClick);
        etBanco.setOnClickListener(etBanco_OnClick);
        etFechaDeposito.setOnClickListener(etFechaDeposito_OnClick);

        // ImageView Click
        ivFirma.setOnClickListener(ivFirma_OnClick);

        //ImageButton Click
        ibMap.setOnClickListener(ibMap_OnClick);
        ibFoto.setOnClickListener(ibFoto_OnClick);
        ibFirma.setOnClickListener(ibFirma_OnClick);

        // Button Click
        btnIntegrantes.setOnClickListener(btnIntegrantes_OnClick);

        //RadioGroup Click
        rgContactoCliente.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                tvContactoCliente.setError(null);
                tvInfoDesfasada.setError(null);
                etInfoDesfasada.setError(null);
                etComentarioNoContacto.setError(null);
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

        rgResultadoPago.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                tvResultadoGestion.setError(null);
                switch (checkedId) {
                    case R.id.rbPago:
                        spMotivoNoPago.setSelection(0);
                        SelectMotivoNoPago(0);
                        llMedioPago.setVisibility(View.VISIBLE);
                        llMontoPagoRequerido.setVisibility(View.VISIBLE);
                        llSaldoCorte.setVisibility(View.VISIBLE);
                        llSaldoActual.setVisibility(View.VISIBLE);
                        llMotivoNoPago.setVisibility(View.GONE);
                        llFotoFachada.setVisibility(View.GONE);
                        llEstaGerente.setVisibility(View.GONE);
                        break;
                    case R.id.rbNoPago:
                        tvMotivoNoPago.setError("");
                        tvFotoFachada.setError("");
                        spMedioPago.setSelection(0);
                        SelecteMedioPago(0);
                        llMedioPago.setVisibility(View.GONE);
                        llMontoPagoRequerido.setVisibility(View.GONE);
                        llSaldoCorte.setVisibility(View.GONE);
                        llSaldoActual.setVisibility(View.GONE);
                        llMotivoNoPago.setVisibility(View.VISIBLE);
                        llFotoFachada.setVisibility(View.VISIBLE);
                        llEstaGerente.setVisibility(View.VISIBLE);
                        break;
                    default:
                        tvResultadoGestion.setError("");
                        spMedioPago.setSelection(0);
                        SelecteMedioPago(0);
                        llMedioPago.setVisibility(View.GONE);
                        llMontoPagoRequerido.setVisibility(View.GONE);
                        llSaldoCorte.setVisibility(View.GONE);
                        llSaldoActual.setVisibility(View.GONE);
                        llMotivoNoPago.setVisibility(View.GONE);
                        llFotoFachada.setVisibility(View.GONE);
                        llEstaGerente.setVisibility(View.GONE);
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
                if (spMedioPago.getSelectedItemPosition() == 1 || spMedioPago.getSelectedItemPosition() == 2 ) {
                    if(!validator.validate(etBanco, new String[] {validator.REQUIRED}) &&
                       !validator.validate(etFechaDeposito, new String[] {validator.REQUIRED}) &&
                       !validator.validate(etPagoRealizado, new String[] {validator.REQUIRED, validator.MONEY})) {
                        switch (checkedId) {
                            case R.id.rbSiCorrecto:
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
                        etFechaDeposito.setError("");
                        rbSiCorrecto.setChecked(false);
                        rbNoCorrecto.setChecked(false);
                        Toast.makeText(ctx, "Complete los campos faltantes", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    if (!validator.validate(etPagoRealizado, new String[] {validator.REQUIRED,validator.MONEY})){
                        switch (checkedId) {
                            case R.id.rbSiCorrecto:
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

    private View.OnClickListener etBanco_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
            builder.setTitle(R.string.selected_option)
                    .setItems(R.array.banks, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int position) {
                            etBanco.setError(null);
                            etBanco.setText(_banks[position]);
                        }
                    });
            builder.create();
            builder.show();
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
            final AlertDialog file_not_exist = Popups.showDialogMessage(ctx, Constants.not_network, ctx.getResources().getString(R.string.capturar_nueva_firma), ctx.getResources().getString(R.string.accept), new Popups.DialogMessage() {
                @Override
                public void OnClickListener(AlertDialog dialog) {
                    Intent sig = new Intent(ctx, Signature.class);
                    startActivityForResult(sig, Constants.REQUEST_CODE_FIRMA);
                    dialog.dismiss();

                }
            }, ctx.getResources().getString(R.string.cancel), new Popups.DialogMessage() {
                @Override
                public void OnClickListener(AlertDialog dialog) {
                    dialog.dismiss();
                }
            });
            Objects.requireNonNull(file_not_exist.getWindow()).requestFeature(Window.FEATURE_NO_TITLE);
            file_not_exist.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            file_not_exist.show();
        }
    };
    // Fin de ImageView

    /*
    * Evento de ImageButton
    * */
    private View.OnClickListener ibMap_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast.makeText(ctx, "Estamos trabajando . . .", Toast.LENGTH_SHORT).show();
            /*locationManager = (LocationManager) parent.getSystemService(Context.LOCATION_SERVICE);

            locationListener = new MyCurrentListener(new MyCurrentListener.evento() {
                @Override
                public void onComplete(String latitud, String longitud) {
                    Toast.makeText(ctx, "Mi ubiciación: " + "lat: " + latitud + "Lon: " + longitud, Toast.LENGTH_SHORT).show();
                    flagUbicacion = true;
                    if (flagUbicacion){
                        CancelUbicacion();
                    }
                }
            });
            if (ActivityCompat.checkSelfPermission(ctx, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(ctx, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            }
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
            */
        }
    };

    private View.OnClickListener ibFoto_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            i.putExtra(MediaStore.EXTRA_SCREEN_ORIENTATION, ActivityInfo.SCREEN_ORIENTATION_LOCKED);
            //i.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
            startActivityForResult(i, 432);
        }
    };

    private View.OnClickListener ibFirma_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent sig = new Intent(ctx, Signature.class);
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
            i_integrantes.putExtra(Constants.INTEGRANTES_GRUPO, parent.ficha_rg.getGrupo());
            startActivityForResult(i_integrantes, Constants.REQUEST_INTEGRANTES_GPO);
        }
    };
    // Fin de Button

    //===================== Comportamientos  ===============================================
    private void SelectContactoCliente (int pos){

        if (rbSiGerente.isChecked() || rbNoGerente.isChecked()) tvEstaGerente.setError(null);
        else tvEstaGerente.setError("");
        switch (pos){
            case 0: // No contacto cliente
                etComentarioNoContacto.setError("Este campo es requerido");
                tvFotoFachada.setError("");
                etComentarioInfoDesfa.setText("");
                etInfoDesfasada.setText("");
                etComentarioNoContacto.setText("");
                rgResultadoPago.clearCheck();
                llResultadoGestion.setVisibility(View.GONE);
                llComentarioNoContacto.setVisibility(View.VISIBLE);
                llFotoFachada.setVisibility(View.VISIBLE);
                llEstaGerente.setVisibility(View.VISIBLE);
                llInfoDesfasada.setVisibility(View.GONE);
                llComentarioAclaracion.setVisibility(View.GONE);
                break;
            case 1: // Si contacto cliente
                tvResultadoGestion.setError("");
                etComentarioInfoDesfa.setText("");
                etInfoDesfasada.setText("");
                etComentarioNoContacto.setText("");
                llResultadoGestion.setVisibility(View.VISIBLE);
                llComentarioNoContacto.setVisibility(View.GONE);
                llFotoFachada.setVisibility(View.GONE);
                llEstaGerente.setVisibility(View.GONE);
                llInfoDesfasada.setVisibility(View.GONE);
                llComentarioAclaracion.setVisibility(View.GONE);
                break;
            case 2: // Aclaración
                tvInfoDesfasada.setError("");
                etComentarioInfoDesfa.setError("Este campo es requerido");
                etComentarioInfoDesfa.setText("");
                etInfoDesfasada.setText("");
                etComentarioNoContacto.setText("");
                rgResultadoPago.clearCheck();
                llResultadoGestion.setVisibility(View.GONE);
                llComentarioNoContacto.setVisibility(View.GONE);
                llFotoFachada.setVisibility(View.GONE);
                llEstaGerente.setVisibility(View.VISIBLE);
                llInfoDesfasada.setVisibility(View.VISIBLE);
                llComentarioAclaracion.setVisibility(View.VISIBLE);
                break;
            default: //Sin seleccionar una opción o cualquier otro valor
                etComentarioInfoDesfa.setText("");
                etInfoDesfasada.setText("");
                etComentarioNoContacto.setText("");
                rgResultadoPago.clearCheck();
                llResultadoGestion.setVisibility(View.GONE);
                llComentarioNoContacto.setVisibility(View.GONE);
                llFotoFachada.setVisibility(View.GONE);
                llEstaGerente.setVisibility(View.GONE);
                llInfoDesfasada.setVisibility(View.GONE);
                llComentarioAclaracion.setVisibility(View.GONE);
                etComentarioInfoDesfa.setText("");
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
            if (pos == 1) {
                etBanco.setEnabled(false);
                etFechaDeposito.setEnabled(false);
            }else {
                etBanco.setEnabled(true);
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
                llBanco.setVisibility(View.GONE);
                llDetalleFicha.setVisibility(View.GONE);
                llFechaDeposito.setVisibility(View.GONE);
                break;
            case 1: // Banco
                llBanco.setVisibility(View.VISIBLE);
                llDetalleFicha.setVisibility(View.VISIBLE);
                llFechaDeposito.setVisibility(View.VISIBLE);
                break;
            case 2: // Oxxo
                llBanco.setVisibility(View.GONE);
                llDetalleFicha.setVisibility(View.VISIBLE);
                llFechaDeposito.setVisibility(View.VISIBLE);
                break;
            case 3: // Efectivo
                llBanco.setVisibility(View.GONE);
                llDetalleFicha.setVisibility(View.VISIBLE);
                llFechaDeposito.setVisibility(View.GONE);
                break;
            case 4: // SIDERT
                llBanco.setVisibility(View.GONE);
                llDetalleFicha.setVisibility(View.VISIBLE);
                llFechaDeposito.setVisibility(View.GONE);
                break;
            default: //Sin seleccionar una opción o cualquier otro valor
                tvMedioPago.setError("");
                rgDetalleFicha.clearCheck();
                SelectDetalleFicha(-1);
                llBanco.setVisibility(View.GONE);
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
                llEspecificaCausa.setVisibility(View.GONE);
                break;
            case 1: // Negación de pago
                llDatosDefuncion.setVisibility(View.GONE);
                llEspecificaCausa.setVisibility(View.GONE);
                break;
            case 2: //Fallecimiento
                etMotivoDefuncion.setError("Este campo es requerido");
                etFechaDefuncion.setError("");
                llDatosDefuncion.setVisibility(View.VISIBLE);
                llEspecificaCausa.setVisibility(View.GONE);
                break;
            case 3: // Otro
                etEspecificaCausa.setError("Este campo es requerido");
                llEspecificaCausa.setVisibility(View.VISIBLE);
                llDatosDefuncion.setVisibility(View.GONE);
                break;
            default: //Sin seleccionar una opción o cualquier otro valor
                tvMotivoNoPago.setError("");
                llDatosDefuncion.setVisibility(View.GONE);
                llEspecificaCausa.setVisibility(View.GONE);
                break;
        }
    }

    private void SelectDetalleFicha (int pos){
        switch (pos){
            case -1: //Sin seleccionar una opción o cualquier otro valor
                //etPagoRealizado.setText(Miscellaneous.moneyFormat(String.valueOf(parent.ficha_rg.getPrestamo().getPagoRealizado())));
                etPagoRealizado.setText(String.valueOf(parent.ficha_rg.getPrestamo().getPagoRealizado()));
                etPagoRealizado.setEnabled(false);
                llIntegrantes.setVisibility(View.GONE);
                llMontoPagoRealizado.setVisibility(View.GONE);
                llMontoCorrecto.setVisibility(View.GONE);
                rgMontoCorrecto.clearCheck();
                SelectMontoCorrecto(-1);
                break;
            case 0: // No cuenta con detalle
                tvMontoCorrecto.setError("");
                etPagoRealizado.setEnabled(true);
                llIntegrantes.setVisibility(View.GONE);
                llMontoPagoRealizado.setVisibility(View.VISIBLE);
                llMontoCorrecto.setVisibility(View.VISIBLE);
                break;
            case 1: // Si cuenta con detalle
                tvMontoCorrecto.setError("");
                //etPagoRealizado.setText(Miscellaneous.moneyFormat(String.valueOf(parent.ficha_rg.getPrestamo().getPagoRealizado())));
                etPagoRealizado.setText(String.valueOf(parent.ficha_rg.getPrestamo().getPagoRealizado()));
                etPagoRealizado.setEnabled(false);
                llIntegrantes.setVisibility(View.VISIBLE);
                llMontoPagoRealizado.setVisibility(View.VISIBLE);
                llMontoCorrecto.setVisibility(View.VISIBLE);
                break;

        }
    }

    private void SelectMontoCorrecto(int pos){
        etBanco.setError(null);
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
                if (spMedioPago.getSelectedItemPosition() == 1){
                    etBanco.setEnabled(true);
                    etFechaDeposito.setEnabled(true);
                }

                for (int i = 0; i < rgDetalleFicha.getChildCount(); i++) {
                    rgDetalleFicha.getChildAt(i).setEnabled(true);
                }
                etPagoRealizado.setEnabled(true);

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
                if (spMedioPago.getSelectedItemPosition() == 1){
                    etBanco.setEnabled(false);
                    etFechaDeposito.setEnabled(false);
                }
                for (int i = 0; i < rgDetalleFicha.getChildCount(); i++) {
                    rgDetalleFicha.getChildAt(i).setEnabled(false);
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

                if (spMedioPago.getSelectedItemPosition() == 1 || spMedioPago.getSelectedItemPosition() == 2){
                    llFotoGaleria.setVisibility(View.VISIBLE);
                    ibFotoCopia.setEnabled(true);
                    ibGaleriaCopia.setEnabled(true);
                    ibGaleriaCopia.setBackground(ctx.getResources().getDrawable(R.drawable.btn_rounded_blue));
                }else {
                    llImprimirRecibo.setVisibility(View.VISIBLE);
                    llFolioRecibo.setVisibility(View.VISIBLE);
                    llFotoGaleria.setVisibility(View.VISIBLE);
                    ibFotoCopia.setEnabled(true);
                    ibGaleriaCopia.setEnabled(false);
                    ibGaleriaCopia.setBackground(ctx.getResources().getDrawable(R.drawable.btn_disable));
                }
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
                if (spMedioPago.getSelectedItemPosition() == 1){
                    etBanco.setEnabled(true);
                    etFechaDeposito.setEnabled(true);
                }
                for (int i = 0; i < rgDetalleFicha.getChildCount(); i++) {
                    rgDetalleFicha.getChildAt(i).setEnabled(true);
                }
                etPagoRealizado.setEnabled(true);
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
                OrderModel order = new OrderModel(parent.ficha_rg.getId(),
                        "002",
                        parent.ficha_rg.getPrestamo().getMontoPrestamo(),
                        parent.ficha_rg.getPrestamo().getPagoSemanal(),
                        Miscellaneous.doubleFormat(etPagoRealizado),
                        parent.ficha_rg.getGrupo().getClaveGrupo(),
                        parent.ficha_rg.getPrestamo().getNumeroDePrestamo(),
                        parent.ficha_rg.getGrupo().getNombreGrupo(),
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
        tvExternalID.setText(parent.ficha_rg.getId());
        tvContactoCliente.setError("");
        etMontoPagoRequerido.setText(String.valueOf(parent.ficha_rg.getPrestamo().getPagoSemanal()));
        etSaldoCorte.setText(Miscellaneous.moneyFormat(String.valueOf(parent.ficha_rg.getPrestamo().getSaldoActual())));
    }

    private void setDatePicked(EditText et){
        sdf.setTimeZone(myCalendar.getTimeZone());
        et.setError(null);
        et.setText(sdf.format(myCalendar.getTime()));
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
                if (resultCode == parent.RESULT_OK){
                    if (data != null){
                        ibFirma.setVisibility(View.GONE);
                        ivFirma.setVisibility(View.VISIBLE);
                        Glide.with(ctx)
                                .load(data.getStringExtra(Constants.uri_signature))
                                .into(ivFirma);
                    }
                }
                break;
            case Constants.REQUEST_INTEGRANTES_GPO:
                if (resultCode == parent.RESULT_OK){
                    if (data != null){
                        //etPagoRealizado.setText(Miscellaneous.moneyFormat(String.valueOf(data.getDoubleExtra(Constants.RESPONSE,(double) 0))));
                        etPagoRealizado.setText(String.valueOf(parent.ficha_rg.getPrestamo().getPagoRealizado()));
                    }
                }
                break;
            case Constants.REQUEST_CODE_IMPRESORA:
                if (resultCode == parent.RESULT_OK){
                    if (data != null){
                        Toast.makeText(ctx, data.getStringExtra(Constants.MESSAGE), Toast.LENGTH_SHORT).show();
                    }
                }
                break;

        }
    }
}
