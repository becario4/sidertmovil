package com.sidert.sidertmovil.fragments.view_pager;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
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
import com.sidert.sidertmovil.activities.IntegrantesGpo;
import com.sidert.sidertmovil.activities.PrintSeewoo;
import com.sidert.sidertmovil.activities.RecuperacionGrupal;
import com.sidert.sidertmovil.database.DBhelper;
import com.sidert.sidertmovil.fragments.dialogs.dialog_date_picker;
import com.sidert.sidertmovil.models.OrderModel;
import com.sidert.sidertmovil.utils.Constants;
import com.sidert.sidertmovil.utils.Miscellaneous;
import com.sidert.sidertmovil.utils.MyCurrentListener;
import com.sidert.sidertmovil.utils.NameFragments;
import com.sidert.sidertmovil.utils.Validator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static com.sidert.sidertmovil.utils.Constants.ACTUALIZAR_TELEFONO;
import static com.sidert.sidertmovil.utils.Constants.DATE;
import static com.sidert.sidertmovil.utils.Constants.DATE_CURRENT;
import static com.sidert.sidertmovil.utils.Constants.DAY_CURRENT;
import static com.sidert.sidertmovil.utils.Constants.DETALLE_FICHA;
import static com.sidert.sidertmovil.utils.Constants.FECHAS_POST;
import static com.sidert.sidertmovil.utils.Constants.FIRMA_IMAGE;
import static com.sidert.sidertmovil.utils.Constants.FORMAT_DATE_GNRAL;
import static com.sidert.sidertmovil.utils.Constants.GERENTE;
import static com.sidert.sidertmovil.utils.Constants.IDENTIFIER;
import static com.sidert.sidertmovil.utils.Constants.IMPRESORA;
import static com.sidert.sidertmovil.utils.Constants.MONTH_CURRENT;
import static com.sidert.sidertmovil.utils.Constants.REQUEST_CODE_FIRMA;
import static com.sidert.sidertmovil.utils.Constants.REQUEST_CODE_GALERIA;
import static com.sidert.sidertmovil.utils.Constants.RESULTADO_PAGO;
import static com.sidert.sidertmovil.utils.Constants.TIPO;
import static com.sidert.sidertmovil.utils.Constants.YEAR_CURRENT;

public class recuperacion_gpo_fragment extends Fragment {

    private Context ctx;

    public String[] _contacto;
    public String[] _motivo_aclaracion;
    public String[] _confirmacion;
    public String[] _resultado_gestion;
    public String[] _medio_pago;
    public String[] _motivo_no_pago;
    public String[] _imprimir;

    //private TextView tvExternalID;
    public TextView tvContacto;
    public TextView tvActualizarTelefono;
    public TextView tvResultadoGestion;
    public TextView tvMedioPago;
    public TextView tvFechaDeposito;
    public TextView tvMontoPagoRequerido;
    public TextView tvDetalleFicha;
    private TextView tvArqueoCaja;
    public TextView tvMotivoAclaracion;
    public TextView tvFechaDefuncion;
    public TextView tvGerente;
    private TextView tvFachada;
    public TextView tvMotivoNoPago;
    private TextView tvmapa;
    private TextView tvFirma;
    public TextView tvImprimirRecibo;
    private TextView tvFotoGaleria;

    public EditText etActualizarTelefono;
    public EditText etComentario;

    public EditText etPagoRealizado;
    public EditText etFolioRecibo;

    private ImageButton ibMap;
    private ImageButton ibFachada;
    private ImageButton ibFoto;
    private ImageButton ibGaleria;
    private ImageButton ibFirma;
    private ImageButton ibIntegrantes;
    private ImageButton ibArqueoCaja;
    private ImageButton ibImprimir;

    private ImageView ivFachada;
    private ImageView ivEvidencia;
    private ImageView ivFirma;

    private Uri imageUri;

    public RadioButton rbIntegrantes;

    private LinearLayout llActualizarTelefono;
    private LinearLayout llComentario;
    private LinearLayout llFachada;
    private LinearLayout llDatosDefuncion;
    private LinearLayout llMotivoAclaracion;
    private LinearLayout llMotivoNoPago;
    private LinearLayout llMontoPagoRequerido;
    private LinearLayout llMedioPago;
    private LinearLayout llResultadoGestion;
    private LinearLayout llArqueoCaja;
    private LinearLayout llFotoGaleria;
    private LinearLayout llIntegrantes;
    private LinearLayout llDetalleFicha;
    private LinearLayout llMontoPagoRealizado;
    private LinearLayout llFechaDeposito;
    private LinearLayout llImprimirRecibo;
    private LinearLayout llFolioRecibo;
    private LinearLayout llGerente;
    private LinearLayout llFirma;

    private Calendar myCalendar;
    private SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_DATE_GNRAL, Locale.US);
    private Date minDate;

    private Validator validator;

    private boolean flagUbicacion = false;

    private ProgressBar pbLoading;

    private MapView mapView;
    private GoogleMap mMap;
    private Marker mMarker;

    private LocationManager locationManager;
    private MyCurrentListener locationListener;
    public LatLng latLngGestion;

    RecuperacionGrupal parent;

    private DBhelper dBhelper;
    private SQLiteDatabase db;

    public byte[] byteEvidencia;
    public byte[] byteFirma;
    public String _Integrantes = "";

    public String folio_impreso = "";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_recuperacion_gpo, container, false);

        ctx = getContext();
        dBhelper        = new DBhelper(ctx);
        db              = dBhelper.getWritableDatabase();

        _contacto = getResources().getStringArray(R.array.contacto_cliente);
        _motivo_aclaracion = getResources().getStringArray(R.array.outdated_information);
        _confirmacion = getResources().getStringArray(R.array.confirmacion);
        _resultado_gestion = getResources().getStringArray(R.array.resultado_gestion);
        _motivo_no_pago = getResources().getStringArray(R.array.reason_no_pay);
        _medio_pago = getResources().getStringArray(R.array.medio_pago);
        _imprimir = getResources().getStringArray(R.array.imprimir);

        parent                = (RecuperacionGrupal) getActivity();
        assert parent != null;
        parent.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        tvActualizarTelefono    = v.findViewById(R.id.tvActualizarTelefono);
        //tvExternalID            = v.findViewById(R.id.tvExternalID);
        tvContacto              = v.findViewById(R.id.tvContacto);
        tvResultadoGestion      = v.findViewById(R.id.tvResultadoGestion);
        tvMedioPago             = v.findViewById(R.id.tvMedioPago);
        tvFechaDeposito         = v.findViewById(R.id.tvFechaDeposito);
        tvMontoPagoRequerido    = v.findViewById(R.id.tvMontoPagoRequerido);
        tvDetalleFicha          = v.findViewById(R.id.tvDetalleFicha);
        tvArqueoCaja            = v.findViewById(R.id.tvArqueoCaja);
        tvMotivoAclaracion      = v.findViewById(R.id.tvMotivoAclaracion);
        tvGerente               = v.findViewById(R.id.tvGerente);
        tvFachada               = v.findViewById(R.id.tvFachada);
        tvMotivoNoPago          = v.findViewById(R.id.tvMotivoNoPago);
        tvFechaDefuncion        = v.findViewById(R.id.tvFechaDefuncion);
        tvmapa                  = v.findViewById(R.id.tvMapa);
        tvFirma                 = v.findViewById(R.id.tvFirma);
        tvImprimirRecibo        = v.findViewById(R.id.tvImprimirRecibo);
        tvFotoGaleria           = v.findViewById(R.id.tvFotoGaleria);

        etActualizarTelefono    = v.findViewById(R.id.etActualizarTelefono);
        etComentario            = v.findViewById(R.id.etComentario);
        etPagoRealizado         = v.findViewById(R.id.etPagoRealizado);
        etFolioRecibo           = v.findViewById(R.id.etFolioRecibo);

        ibMap           = v.findViewById(R.id.ibMap);
        ibFachada       = v.findViewById(R.id.ibFachada);
        ibFoto          = v.findViewById(R.id.ibFoto);
        ibGaleria       = v.findViewById(R.id.ibGaleria);
        ibFirma         = v.findViewById(R.id.ibFirma);
        ibIntegrantes   = v.findViewById(R.id.ibIntegrantes);
        ibArqueoCaja    = v.findViewById(R.id.ibArqueoCaja);
        ibImprimir      = v.findViewById(R.id.ibImprimir);

        ivFachada       = v.findViewById(R.id.ivFachada);
        ivEvidencia     = v.findViewById(R.id.ivEvidencia);
        ivFirma         = v.findViewById(R.id.ivFirma);

        pbLoading   = v.findViewById(R.id.pbLoading);

        mapView     = v.findViewById(R.id.mapGestion);

        rbIntegrantes   = v.findViewById(R.id.rbIntegrantes);

        llActualizarTelefono    = v.findViewById(R.id.llActualizarTelefono);
        llComentario            = v.findViewById(R.id.llComentario);
        llIntegrantes           = v.findViewById(R.id.llIntegrantes);
        llResultadoGestion      = v.findViewById(R.id.llResultadoGestion);
        llFachada               = v.findViewById(R.id.llFachada);
        llDatosDefuncion        = v.findViewById(R.id.llDatosDefuncion);
        llMotivoAclaracion      = v.findViewById(R.id.llMotivoAclaracion);
        llMotivoNoPago          = v.findViewById(R.id.llMotivoNoPago);
        llMontoPagoRequerido    = v.findViewById(R.id.llMontoPagoRequerido);
        llMedioPago             = v.findViewById(R.id.llMedioPago);
        llArqueoCaja            = v.findViewById(R.id.llArqueoCaja);
        llDetalleFicha          = v.findViewById(R.id.llDetalleFicha);
        llMontoPagoRealizado    = v.findViewById(R.id.llMontoPagoRealizado);
        llImprimirRecibo        = v.findViewById(R.id.llImprimirRecibo);
        llFolioRecibo           = v.findViewById(R.id.llFolioRecibo);
        llFechaDeposito         = v.findViewById(R.id.llFechaDeposito);
        llFotoGaleria           = v.findViewById(R.id.llFotoGaleria);
        llGerente               = v.findViewById(R.id.llGerente);
        llFirma                 = v.findViewById(R.id.llFirma);

        myCalendar      = Calendar.getInstance();

        mapView.onCreate(savedInstanceState);
        locationManager = (LocationManager) parent.getSystemService(Context.LOCATION_SERVICE);

        // ImageButton Click
        ibMap.setOnClickListener(ibMap_OnClick);
        ibIntegrantes.setOnClickListener(ibIntegrantes_OnClick);
        ibArqueoCaja.setOnClickListener(ibArqueoCaja_OnClick);
        ibImprimir.setOnClickListener(ibImprimir_OnClick);
        ibFoto.setOnClickListener(ibFoto_OnClick);
        ibGaleria.setOnClickListener(ibGaleria_OnClick);
        ibFachada.setOnClickListener(ibFachada_OnClick);
        ibFirma.setOnClickListener(ibFirma_OnClick);

        // TextView Click
        tvContacto.setOnClickListener(tvContacto_OnClick);
        tvActualizarTelefono.setOnClickListener(tvActualizarTelefono_OnClick);
        tvResultadoGestion.setOnClickListener(tvResultadoGestion_OnClick);
        tvMedioPago.setOnClickListener(tvMedioPago_OnClick);
        tvFechaDeposito.setOnClickListener(tvFechaDeposito_OnClick);
        tvDetalleFicha.setOnClickListener(tvDetalleFicha_OnClick);
        tvImprimirRecibo.setOnClickListener(tvImprimirRecibo_OnClick);
        tvMotivoNoPago.setOnClickListener(tvMotivoNoPago_OnClick);
        tvMotivoAclaracion.setOnClickListener(tvMotivoAclaracion_OnClick);
        tvFechaDefuncion.setOnClickListener(tvFechaDefuncion_OnClick);
        tvGerente.setOnClickListener(tvGerente_OnClick);

        try {
            init();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return v;
    }

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
                        tvmapa.setError(null);
                        mapView.setVisibility(View.VISIBLE);
                        ColocarUbicacionGestion(Double.parseDouble(latitud), Double.parseDouble(longitud));
                    }
                    else{
                        pbLoading.setVisibility(View.GONE);
                        tvmapa.setError("");
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

    private View.OnClickListener ibIntegrantes_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i_integrantes = new Intent(ctx, IntegrantesGpo.class);
            try {
                if (rbIntegrantes.isChecked()){
                    Log.v("-","-------------------------------------------------------------");
                    Log.v("Pago Integrantes", "true");
                    Log.v("-","-------------------------------------------------------------");
                    JSONArray _integrantes_pago = new JSONArray(_Integrantes);
                    for (int i = 0; i < _integrantes_pago.length(); i++){
                        JSONObject item = _integrantes_pago.getJSONObject(i);
                        parent.ficha_rg.getGrupo().getIntegrantesDelGrupo().get(Integer.parseInt(item.getString("clave_cliente"))-1).setPagoRealizado(item.getDouble("pago"));
                        parent.ficha_rg.getGrupo().getIntegrantesDelGrupo().get(Integer.parseInt(item.getString("clave_cliente"))-1).setPagoSolidario(item.getDouble("solidario"));
                        parent.ficha_rg.getGrupo().getIntegrantesDelGrupo().get(Integer.parseInt(item.getString("clave_cliente"))-1).setPagoAdelanto(item.getDouble("adelanto"));
                    }
                    i_integrantes.putExtra(Constants.EDITABLE, parent.flagRespuesta);
                    i_integrantes.putExtra(Constants.INTEGRANTES_GRUPO, parent.ficha_rg.getGrupo());
                }
                else{
                    Log.v("-","-------------------------------------------------------------");
                    Log.v("Pago Integrantes", "false");
                    Log.v("-","-------------------------------------------------------------");
                    i_integrantes.putExtra(Constants.INTEGRANTES_GRUPO, parent.ficha_rg.getGrupo());
                }
                i_integrantes.putExtra(Constants.EDITABLE, parent.flagRespuesta);
                startActivityForResult(i_integrantes, Constants.REQUEST_CODE_INTEGRANTES_GPO);
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    };

    private View.OnClickListener ibArqueoCaja_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i_arqueoCaja = new Intent(ctx, ArqueoDeCaja.class);
            i_arqueoCaja.putExtra(Constants.PAGO_REALIZADO, Double.parseDouble(etPagoRealizado.getText().toString()));
            i_arqueoCaja.putExtra(Constants.NOMBRE_GRUPO, parent.ficha_rg.getGrupo().getNombreGrupo());
            i_arqueoCaja.putExtra(Constants.ORDER_ID, parent.ficha_rg.getId());
            startActivityForResult(i_arqueoCaja, Constants.REQUEST_CODE_ARQUEO_CAJA);
        }
    };

    private View.OnClickListener ibImprimir_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (!etPagoRealizado.getText().toString().trim().isEmpty() && Double.parseDouble(etPagoRealizado.getText().toString().trim()) > 0){
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
            }
            else {
                Toast.makeText(ctx, "No ha capturado el pago realizado del cliente", Toast.LENGTH_SHORT).show();
            }

        }
    };

    private View.OnClickListener ibFoto_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i = new Intent(parent, CameraVertical.class);
            i.putExtra(Constants.ORDER_ID, parent.ficha_rg.getId());
            startActivityForResult(i, Constants.REQUEST_CODE_CAMARA_TICKET);
        }
    };

    private View.OnClickListener ibGaleria_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
            gallery.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(gallery, "Select Picture"), REQUEST_CODE_GALERIA);
        }
    };

    private View.OnClickListener ibFachada_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i = new Intent(parent, CameraVertical.class);
            i.putExtra(Constants.ORDER_ID, parent.ficha_rg.getId());
            startActivityForResult(i, Constants.REQUEST_CODE_CAMARA_FACHADA);
        }
    };

    private View.OnClickListener ibFirma_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent sig = new Intent(ctx, CapturarFirma.class);
            sig.putExtra(TIPO,"");
            startActivityForResult(sig, REQUEST_CODE_FIRMA);
        }
    };


    private View.OnClickListener tvContacto_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
            builder.setTitle(R.string.selected_option)
                    .setItems(R.array.contacto_cliente, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int position) {
                            tvContacto.setError(null);
                            tvContacto.setText(_contacto[position]);
                            tvMotivoAclaracion.setError(null);
                            tvFachada.setError(null);
                            SelectContactoCliente(position);

                        }
                    });
            builder.create();
            builder.show();
        }
    };

    private View.OnClickListener tvActualizarTelefono_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
            builder.setTitle(R.string.selected_option)
                    .setItems(R.array.confirmacion, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int position) {
                            tvActualizarTelefono.setError(null);
                            tvActualizarTelefono.setText(_confirmacion[position]);
                            /*try {
                                jsonResponse.put(ACTUALIZAR_TELEFONO, pos);
                                UpdateTemporal(jsonResponse);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }*/
                            SelectActualizarTelefono(position);
                        }
                    });
            builder.create();
            builder.show();
        }
    };

    private View.OnClickListener tvResultadoGestion_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
            builder.setTitle(R.string.selected_option)
                    .setItems(R.array.resultado_gestion, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int position) {
                            tvResultadoGestion.setError(null);
                            tvResultadoGestion.setText(_resultado_gestion[position]);
                            /*try {
                                jsonResponse.put(ACTUALIZAR_TELEFONO, pos);
                                UpdateTemporal(jsonResponse);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }*/
                            SelectResultadoGestion(position);
                        }
                    });
            builder.create();
            builder.show();
        }
    };

    private View.OnClickListener tvMedioPago_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
            builder.setTitle(R.string.selected_option)
                    .setItems(R.array.medio_pago, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int position) {
                            tvMedioPago.setError(null);
                            tvMedioPago.setText(_medio_pago[position]);
                            if (position == 6){
                                byteEvidencia = null;

                                ibGaleria.setEnabled(false);
                                ibGaleria.setBackground(ctx.getResources().getDrawable(R.drawable.btn_disable));
                                ibFoto.setVisibility(View.VISIBLE);
                                ibGaleria.setVisibility(View.VISIBLE);
                                llFotoGaleria.setVisibility(View.VISIBLE);
                                ivEvidencia.setVisibility(View.GONE);
                            }
                            SelectMedioPago(position);
                        }
                    });
            builder.create();
            builder.show();
        }
    };

    private View.OnClickListener tvFechaDeposito_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (parent.flagRespuesta) {
                myCalendar = Calendar.getInstance();
                dialog_date_picker dialogDatePicker = new dialog_date_picker();
                Bundle b = new Bundle();

                b.putInt(YEAR_CURRENT, myCalendar.get(Calendar.YEAR));
                b.putInt(MONTH_CURRENT, myCalendar.get(Calendar.MONTH));
                b.putInt(DAY_CURRENT, myCalendar.get(Calendar.DAY_OF_MONTH));
                b.putString(DATE_CURRENT, sdf.format(myCalendar.getTime()));
                b.putInt(IDENTIFIER, 8);
                b.putBoolean(FECHAS_POST, false);
                dialogDatePicker.setArguments(b);
                dialogDatePicker.setTargetFragment(recuperacion_gpo_fragment.this,812);
                dialogDatePicker.show(parent.getSupportFragmentManager(), NameFragments.DIALOGDATEPICKER);
            }
        }
    };

    private View.OnClickListener tvDetalleFicha_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
            builder.setTitle(R.string.selected_option)
                    .setItems(R.array.confirmacion, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int position) {
                            tvDetalleFicha.setError(null);
                            tvDetalleFicha.setText(_confirmacion[position]);
                            SelectDetalleFicha(position);
                        }
                    });
            builder.create();
            builder.show();
        }
    };

    private View.OnClickListener tvImprimirRecibo_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
            builder.setTitle(R.string.selected_option)
                    .setItems(R.array.imprimir, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int position) {
                            tvImprimirRecibo.setError(null);
                            tvImprimirRecibo.setText(_imprimir[position]);
                            SelectImprimirRecibos(position);
                        }
                    });
            builder.create();
            builder.show();

        }
    };

    private View.OnClickListener tvMotivoNoPago_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
            builder.setTitle(R.string.selected_option)
                    .setItems(R.array.reason_no_pay, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int position) {
                            tvMotivoNoPago.setError(null);
                            tvMotivoNoPago.setText(_motivo_no_pago[position]);
                            /*try {
                                jsonResponse.put(MOTIVO_NO_PAGO, _motivo_no_pago[position].toUpperCase());
                                UpdateTemporal(jsonResponse);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }*/

                            SelectMotivoNoPago(position);
                        }
                    });
            builder.create();
            builder.show();
        }
    };

    private View.OnClickListener tvMotivoAclaracion_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
            builder.setTitle(R.string.selected_option)
                    .setItems(R.array.outdated_information, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int position) {
                            tvMotivoAclaracion.setError(null);
                            tvMotivoAclaracion.setText(_motivo_aclaracion[position]);
                            /*try {
                                jsonResponse.put(MOTIVO_ACLARACION, _motivo_aclaracion[position].toUpperCase());
                                UpdateTemporal(jsonResponse);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }*/
                        }
                    });
            builder.create();
            builder.show();
        }
    };

    private View.OnClickListener tvFechaDefuncion_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (parent.flagRespuesta) {
                myCalendar = Calendar.getInstance();
                dialog_date_picker dialogDatePicker = new dialog_date_picker();
                Bundle b = new Bundle();

                b.putInt(YEAR_CURRENT, myCalendar.get(Calendar.YEAR));
                b.putInt(MONTH_CURRENT, myCalendar.get(Calendar.MONTH));
                b.putInt(DAY_CURRENT, myCalendar.get(Calendar.DAY_OF_MONTH));
                b.putString(DATE_CURRENT, sdf.format(myCalendar.getTime()));
                b.putInt(IDENTIFIER, 7);
                b.putBoolean(FECHAS_POST, false);
                dialogDatePicker.setArguments(b);
                dialogDatePicker.setTargetFragment(recuperacion_gpo_fragment.this,123);
                dialogDatePicker.show(parent.getSupportFragmentManager(), NameFragments.DIALOGDATEPICKER);
            }
        }
    };

    private View.OnClickListener tvGerente_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
            builder.setTitle(R.string.selected_option)
                    .setItems(R.array.confirmacion, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int position) {
                            tvGerente.setError(null);
                            tvGerente.setText(_confirmacion[position]);

                            /*try {
                                jsonResponse.put(GERENTE, pos);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }*/

                            //UpdateTemporal(jsonResponse);
                            SelectEstaGerente(position);
                            /*try {
                                jsonResponse.put(MOTIVO_ACLARACION, _motivo_aclaracion[position].toUpperCase());
                                UpdateTemporal(jsonResponse);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }*/
                        }
                    });
            builder.create();
            builder.show();
        }
    };

    //===================== Comportamientos  ===============================================
    private void SelectContactoCliente (int pos){
        if (!tvGerente.getText().toString().trim().isEmpty()) tvGerente.setError(null);
        else tvGerente.setError("");
        switch (pos){
            case 0: // Si contacto cliente
                tvResultadoGestion.setError("");
                tvMotivoAclaracion.setText("");
                tvResultadoGestion.setText("");
                SelectResultadoGestion(-1);
                llComentario.setVisibility(View.GONE);
                tvActualizarTelefono.setText("");
                SelectActualizarTelefono(-1);
                llActualizarTelefono.setVisibility(View.VISIBLE);
                llResultadoGestion.setVisibility(View.VISIBLE);
                llFachada.setVisibility(View.GONE);
                llGerente.setVisibility(View.GONE);
                llFirma.setVisibility(View.GONE);
                llMotivoAclaracion.setVisibility(View.GONE);
                break;
            case 1: // No contacto cliente
                tvFachada.setError("");
                tvMotivoAclaracion.setText("");
                tvResultadoGestion.setText("");
                SelectResultadoGestion(-1);
                llComentario.setVisibility(View.VISIBLE);
                etComentario.setError(getResources().getString(R.string.campo_requerido));
                tvActualizarTelefono.setText("");
                SelectActualizarTelefono(-1);
                llActualizarTelefono.setVisibility(View.GONE);
                llResultadoGestion.setVisibility(View.GONE);
                llFachada.setVisibility(View.VISIBLE);
                llGerente.setVisibility(View.VISIBLE);
                llMotivoAclaracion.setVisibility(View.GONE);
                break;
            case 2: // Aclaración
                tvMotivoAclaracion.setError("");
                etComentario.setError(getResources().getString(R.string.campo_requerido));
                etComentario.setText("");
                tvActualizarTelefono.setText("");
                SelectActualizarTelefono(-1);
                llActualizarTelefono.setVisibility(View.GONE);
                tvResultadoGestion.setText("");
                SelectResultadoGestion(-1);
                llResultadoGestion.setVisibility(View.GONE);
                llFachada.setVisibility(View.GONE);
                llComentario.setVisibility(View.VISIBLE);
                llGerente.setVisibility(View.VISIBLE);
                llMotivoAclaracion.setVisibility(View.VISIBLE);
                break;
            default: //Sin seleccionar una opción o cualquier otro valor
                tvMotivoAclaracion.setText("");
                tvResultadoGestion.setText("");
                tvActualizarTelefono.setText("");
                SelectActualizarTelefono(-1);
                llActualizarTelefono.setVisibility(View.GONE);
                llComentario.setVisibility(View.GONE);
                llResultadoGestion.setVisibility(View.GONE);
                llFachada.setVisibility(View.GONE);
                llGerente.setVisibility(View.GONE);
                llMotivoAclaracion.setVisibility(View.GONE);
                break;
        }
    }
    private void SelectActualizarTelefono(int pos){
        switch (pos){
            case 0:
                etActualizarTelefono.setVisibility(View.VISIBLE);
                etActualizarTelefono.setError(getResources().getString(R.string.campo_requerido));
                break;
            case 1:
                etActualizarTelefono.setText("");
                etActualizarTelefono.setVisibility(View.GONE);
                break;
            default:
                tvActualizarTelefono.setError("");
                etActualizarTelefono.setText("");
                etActualizarTelefono.setVisibility(View.GONE);
                break;
        }
    }
    private void SelectResultadoGestion (int pos){
        switch (pos){
            case 0: //Si pago
                tvMotivoNoPago.setText("");
                SelectMotivoNoPago(-1);
                llMedioPago.setVisibility(View.VISIBLE);
                llMontoPagoRequerido.setVisibility(View.VISIBLE);
                llComentario.setVisibility(View.GONE);
                llMotivoNoPago.setVisibility(View.GONE);
                llFachada.setVisibility(View.GONE);
                llGerente.setVisibility(View.GONE);
                break;
            case 1: // No Pago
                tvMotivoNoPago.setError("");
                tvFachada.setError("");
                tvMedioPago.setText("");
                SelectMotivoNoPago(-1);
                tvDetalleFicha.setText("");
                SelectDetalleFicha(-1);
                llMedioPago.setVisibility(View.GONE);
                llMontoPagoRequerido.setVisibility(View.GONE);
                llComentario.setVisibility(View.VISIBLE);
                etComentario.setError(getResources().getString(R.string.campo_requerido));
                llMotivoNoPago.setVisibility(View.VISIBLE);
                llFachada.setVisibility(View.VISIBLE);
                llGerente.setVisibility(View.VISIBLE);
                break;
            default: //Sin seleccionar una opción o cualquier otro valor
                tvResultadoGestion.setError("");
                tvMedioPago.setText("");
                SelectMedioPago(-1);
                tvMotivoNoPago.setText("");
                SelectMotivoNoPago(-1);
                tvDetalleFicha.setText("");
                SelectDetalleFicha(-1);
                llMedioPago.setVisibility(View.GONE);
                llMontoPagoRequerido.setVisibility(View.GONE);
                llComentario.setVisibility(View.GONE);
                llMotivoNoPago.setVisibility(View.GONE);
                llFachada.setVisibility(View.GONE);
                llGerente.setVisibility(View.GONE);
                break;
        }
    }
    private void SelectMotivoNoPago (int pos){
        tvMotivoNoPago.setError(null);
        switch (pos){
            case -1: // Opción "Seleccione una opcion"
                tvMotivoNoPago.setError("");
                tvMotivoNoPago.setText("");
                llDatosDefuncion.setVisibility(View.GONE);
                break;
            case 0: // Negación de pago
            case 2: // Otro
                llDatosDefuncion.setVisibility(View.GONE);
                break;
            case 1: //Fallecimiento
                tvFechaDefuncion.setError("");
                if (!tvFechaDefuncion.getText().toString().trim().isEmpty())
                    tvFechaDefuncion.setError(null);
                llDatosDefuncion.setVisibility(View.VISIBLE);
                break;
            default: //Sin seleccionar una opción o cualquier otro valor
                tvMotivoNoPago.setError("");
                llDatosDefuncion.setVisibility(View.GONE);
                tvMotivoNoPago.setText("");
                break;
        }
    }
    private void SelectMedioPago (int pos){
        if (!parent.flagRespuesta) {
            tvMedioPago.setEnabled(false);
            if (pos >= 0 && pos < 6 || pos == 7) {
                tvFechaDeposito.setEnabled(false);
            } else {
                tvFechaDeposito.setEnabled(true);
            }
        }

        tvMedioPago.setError(null);
        switch (pos) {
            case -1: // Opción "Seleccione una opción"
                tvMedioPago.setError("");
                tvDetalleFicha.setText("");
                SelectDetalleFicha(-1);
                llDetalleFicha.setVisibility(View.GONE);
                llFechaDeposito.setVisibility(View.GONE);
                llFotoGaleria.setVisibility(View.GONE);
                llImprimirRecibo.setVisibility(View.GONE);
                llFolioRecibo.setVisibility(View.GONE);
                break;
            case 0: // Banamex
            case 1: // Banorte
            case 2: // Telecom
            case 3: // Bansefi
            case 4: // Bancomer
                if (byteEvidencia != null)
                    tvFotoGaleria.setError(null);
                else
                    tvFotoGaleria.setError("");
                if (tvFechaDeposito.getText().toString().isEmpty())
                    tvFechaDeposito.setError(getResources().getString(R.string.campo_requerido));
                llDetalleFicha.setVisibility(View.VISIBLE);
                tvDetalleFicha.setEnabled(true);
                llFechaDeposito.setVisibility(View.VISIBLE);
                ibGaleria.setEnabled(true);
                ibGaleria.setBackground(ctx.getResources().getDrawable(R.drawable.round_corner_blue));
                llMontoPagoRequerido.setVisibility(View.VISIBLE);
                llImprimirRecibo.setVisibility(View.GONE);
                llFotoGaleria.setVisibility(View.VISIBLE);
                llGerente.setVisibility(View.VISIBLE);
                if (!etPagoRealizado.getText().toString().trim().isEmpty()) {
                    llArqueoCaja.setVisibility(View.GONE);
                    //rbArqueoCaja.setChecked(false);
                }
                break;
            case 5: // Oxoo
                if (byteEvidencia != null)
                    tvFotoGaleria.setError(null);
                else
                    tvFotoGaleria.setError("");
                if (tvFechaDeposito.getText().toString().isEmpty())
                    tvFechaDeposito.setError(getResources().getString(R.string.campo_requerido));
                llDetalleFicha.setVisibility(View.VISIBLE);
                llFechaDeposito.setVisibility(View.VISIBLE);
                tvDetalleFicha.setText(_confirmacion[0]);
                tvDetalleFicha.setEnabled(false);
                etPagoRealizado.setText(parent.ficha_rg.getPrestamo().getPagoSemanal().toString());
                SelectDetalleFicha(0);
                ibGaleria.setEnabled(true);
                ibGaleria.setBackground(ctx.getResources().getDrawable(R.drawable.round_corner_blue));
                llMontoPagoRequerido.setVisibility(View.VISIBLE);
                llImprimirRecibo.setVisibility(View.GONE);
                llFotoGaleria.setVisibility(View.VISIBLE);
                llGerente.setVisibility(View.VISIBLE);
                if (!etPagoRealizado.getText().toString().trim().isEmpty()) {
                    llArqueoCaja.setVisibility(View.GONE);
                    //rbArqueoCaja.setChecked(false);
                }
                break;
            case 7:
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
                llDetalleFicha.setVisibility(View.VISIBLE);
                llFechaDeposito.setVisibility(View.VISIBLE);
                tvDetalleFicha.setEnabled(false);
                llMontoPagoRequerido.setVisibility(View.VISIBLE);
                llImprimirRecibo.setVisibility(View.VISIBLE);
                tvImprimirRecibo.setText(_imprimir[1]);
                tvImprimirRecibo.setEnabled(false);
                SelectImprimirRecibos(Miscellaneous.Impresion(tvImprimirRecibo));
                llFotoGaleria.setVisibility(View.VISIBLE);
                llGerente.setVisibility(View.VISIBLE);
                break;
            case 6: // Efectivo
                if (byteEvidencia != null)
                    tvFotoGaleria.setError(null);
                else{
                    tvFotoGaleria.setError("");
                }

                if (!etPagoRealizado.getText().toString().trim().isEmpty() && Double.parseDouble(etPagoRealizado.getText().toString().trim()) > 10000) {
                    llArqueoCaja.setVisibility(View.VISIBLE);
                    //rbArqueoCaja.setChecked(false);
                }
                llDetalleFicha.setVisibility(View.VISIBLE);
                ibGaleria.setEnabled(false);
                ibGaleria.setBackground(ctx.getResources().getDrawable(R.drawable.btn_disable));
                if (!etFolioRecibo.getText().toString().trim().isEmpty())
                    tvImprimirRecibo.setError(null);
                else
                    tvImprimirRecibo.setError("");
                llFechaDeposito.setVisibility(View.GONE);
                llMontoPagoRequerido.setVisibility(View.VISIBLE);
                tvDetalleFicha.setEnabled(true);
                llImprimirRecibo.setVisibility(View.VISIBLE);
                llFotoGaleria.setVisibility(View.VISIBLE);
                llGerente.setVisibility(View.VISIBLE);
                break;
            default: //Sin seleccionar una opción o cualquier otro valor
                tvMedioPago.setError("");
                tvDetalleFicha.setText("");
                SelectDetalleFicha(-1);
                tvImprimirRecibo.setText("");
                SelectImprimirRecibos(-1);
                ivEvidencia.setVisibility(View.GONE);
                llDetalleFicha.setVisibility(View.GONE);
                llFechaDeposito.setVisibility(View.GONE);
                llFotoGaleria.setVisibility(View.GONE);
                llImprimirRecibo.setVisibility(View.GONE);
                llFolioRecibo.setVisibility(View.GONE);
                break;
        }
    }
    private void SelectDetalleFicha (int pos){
        switch (pos){
            case -1: //Sin seleccionar una opción o cualquier otro valor
                //etPagoRealizado.setText(String.valueOf(parent.ficha_rg.getPrestamo().getPagoRealizado()));
                etPagoRealizado.setEnabled(false);
                llIntegrantes.setVisibility(View.GONE);
                llMontoPagoRealizado.setVisibility(View.GONE);
                break;
            case 0: // Si cuenta con detalle
                //etPagoRealizado.setText(String.valueOf(parent.ficha_rg.getPrestamo().getPagoRealizado()));
                etPagoRealizado.setEnabled(false);
                llIntegrantes.setVisibility(View.VISIBLE);
                llMontoPagoRealizado.setVisibility(View.VISIBLE);
                break;
            case 1: // No cuenta con detalle
                etPagoRealizado.setError(null);
                etPagoRealizado.setEnabled(true);
                llIntegrantes.setVisibility(View.GONE);
                llMontoPagoRealizado.setVisibility(View.VISIBLE);
                break;
        }
    }
    private void SelectImprimirRecibos(int pos){
        switch (pos){
            case 0: // Imprimir Recibos
                ibImprimir.setVisibility(View.VISIBLE);
                if (!folio_impreso.trim().isEmpty()) {
                    tvImprimirRecibo.setError(null);
                    llFolioRecibo.setVisibility(View.VISIBLE);
                    etFolioRecibo.setText(folio_impreso);
                    etFolioRecibo.setEnabled(false);
                }
                else {
                    tvImprimirRecibo.setError("");
                    llFolioRecibo.setVisibility(View.GONE);
                }
                break;
            case 1: //No cuenta con bateria la impresora
                tvImprimirRecibo.setError(null);
                llFolioRecibo.setVisibility(View.GONE);
                ibImprimir.setVisibility(View.GONE);
                llFolioRecibo.setVisibility(View.VISIBLE);
                etFolioRecibo.setText("");
                etFolioRecibo.setHint(R.string.folio_sidert);
                etFolioRecibo.setError(getResources().getString(R.string.campo_requerido));
                etFolioRecibo.setInputType(InputType.TYPE_CLASS_NUMBER);
                etFolioRecibo.setEnabled(true);
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
            case 0: // Si está el gerente
                tvFirma.setError("");
                if (ivFirma.getVisibility() == View.VISIBLE)
                    tvFirma.setError(null);
                else
                    tvFirma.setError("");
                llFirma.setVisibility(View.VISIBLE);
                break;
            case 1: // No está el gerente
                llFirma.setVisibility(View.GONE);
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

    private void init() throws JSONException {
        tvmapa.setError("");
        tvContacto.setError("");
        tvMontoPagoRequerido.setText(String.valueOf(parent.ficha_rg.getPrestamo().getPagoSemanal()));

        if (parent.jsonRes != null){
            Log.e("jsonPArent", parent.jsonRes.toString());
            if (parent.jsonRes.has(Constants.LATITUD) && parent.jsonRes.has(Constants.LONGITUD)){
                try {
                    tvmapa.setError(null);
                    mapView.setVisibility(View.VISIBLE);
                    ColocarUbicacionGestion(parent.jsonRes.getDouble(Constants.LATITUD), parent.jsonRes.getDouble(Constants.LONGITUD));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            if (parent.jsonRes.has(Constants.CONTACTO)){
                if (!parent.flagRespuesta){
                    tvContacto.setEnabled(false);
                }

                tvContacto.setText(parent.jsonRes.getString(Constants.CONTACTO));
                SelectContactoCliente(Miscellaneous.ContactoCliente(tvContacto));
                switch (Miscellaneous.ContactoCliente(tvContacto)) {
                    case 1: //====================================================================== No Contacto
                        if (parent.jsonRes.has(Constants.COMENTARIO)){
                            etComentario.setText(parent.jsonRes.getString(Constants.COMENTARIO));
                            etComentario.setVisibility(View.VISIBLE);
                            etComentario.setError(null);
                            if (!parent.flagRespuesta)
                                etComentario.setEnabled(false);
                        }

                        if (parent.jsonRes.has(Constants.FACHADA)){
                            File fachadaFile = new File(Constants.ROOT_PATH + "Fachada/"+parent.jsonRes.getString(Constants.FACHADA));
                            Uri uriFachada = Uri.fromFile(fachadaFile);
                            Glide.with(ctx).load(uriFachada).into(ivFachada);
                            ibFachada.setVisibility(View.GONE);
                            ivFachada.setVisibility(View.VISIBLE);
                            byteEvidencia = Miscellaneous.getBytesUri(ctx, uriFachada, 1);
                            tvFachada.setError(null);
                        }
                        tvGerente.setVisibility(View.VISIBLE);
                        break;
                    case 0: //====================================================================== Si Contacto
                        if (!parent.flagRespuesta){
                            tvActualizarTelefono.setEnabled(false);
                            etActualizarTelefono.setEnabled(false);
                        }

                        if (parent.jsonRes.has(ACTUALIZAR_TELEFONO)){
                            tvActualizarTelefono.setText(parent.jsonRes.getString(ACTUALIZAR_TELEFONO));
                            if (Miscellaneous.ActualizarTelefono(tvActualizarTelefono) == 0){
                                if (parent.jsonRes.has(Constants.NUEVO_TELEFONO)){
                                    etActualizarTelefono.setText(parent.jsonRes.getString(Constants.NUEVO_TELEFONO));
                                    etActualizarTelefono.setError(null);
                                    etActualizarTelefono.setVisibility(View.VISIBLE);
                                }
                            }
                        }

                        if (parent.jsonRes.has(Constants.RESULTADO_PAGO)){
                            if (!parent.flagRespuesta){
                                tvResultadoGestion.setEnabled(false);
                            }
                            tvResultadoGestion.setText(parent.jsonRes.getString(RESULTADO_PAGO));
                            SelectResultadoGestion(Miscellaneous.ResultadoGestion(tvResultadoGestion));
                            switch (Miscellaneous.ResultadoGestion(tvResultadoGestion)){
                                case 1: //========================================================== No Pago
                                    if (!parent.flagRespuesta) {
                                        tvMotivoNoPago.setEnabled(false);
                                        etComentario.setEnabled(false);
                                        tvFechaDefuncion.setEnabled(false);
                                    }

                                    tvMotivoNoPago.setVisibility(View.VISIBLE);
                                    tvMotivoNoPago.setText(parent.jsonRes.getString(Constants.MOTIVO_NO_PAGO));

                                    SelectMotivoNoPago(Miscellaneous.MotivoNoPago(tvMotivoNoPago));
                                    if (parent.jsonRes.has(Constants.FECHA_DEFUNCION)){
                                        tvFechaDefuncion.setText(parent.jsonRes.getString(Constants.FECHA_DEFUNCION));
                                        tvFechaDefuncion.setError(null);
                                        tvFechaDefuncion.setVisibility(View.VISIBLE);
                                    }

                                    if (parent.jsonRes.has(Constants.COMENTARIO)){
                                        etComentario.setText(parent.jsonRes.getString(Constants.COMENTARIO));
                                        etComentario.setVisibility(View.VISIBLE);
                                        etComentario.setError(null);
                                    }

                                    if (parent.jsonRes.has(Constants.FACHADA)){
                                        File fachadaFile = new File(Constants.ROOT_PATH + "Fachada/"+parent.jsonRes.getString(Constants.FACHADA));
                                        Uri uriFachada = Uri.fromFile(fachadaFile);
                                        Glide.with(ctx).load(uriFachada).into(ivFachada);
                                        ibFachada.setVisibility(View.GONE);
                                        ivFachada.setVisibility(View.VISIBLE);
                                        byteEvidencia = Miscellaneous.getBytesUri(ctx, uriFachada, 1);
                                        tvFachada.setError(null);
                                    }
                                    break;
                                case 0: //========================================================== Si Pago
                                    if (parent.jsonRes.has(Constants.MEDIO_PAGO)){
                                        tvMedioPago.setText(parent.jsonRes.getString(Constants.MEDIO_PAGO));

                                        SelectMedioPago(Miscellaneous.MedioPago(tvMedioPago));
                                        if (parent.jsonRes.has(DETALLE_FICHA)){
                                            tvDetalleFicha.setText(parent.jsonRes.getString(DETALLE_FICHA));

                                            SelectDetalleFicha(Miscellaneous.PagoRequerido(tvDetalleFicha));
                                            if (!parent.flagRespuesta){
                                                tvDetalleFicha.setEnabled(false);
                                                etPagoRealizado.setEnabled(false);
                                            }
                                            if (Miscellaneous.PagoRequerido(tvDetalleFicha) == 0){
                                                if (parent.jsonRes.has(Constants.INTEGRANTES)){
                                                    _Integrantes = parent.jsonRes.getString(Constants.INTEGRANTES);
                                                    rbIntegrantes.setChecked(true);
                                                    Log.v("-","-----------------------------------------------------------");
                                                    Log.v("Detalle de integrantes","entra a detalle de ficha");
                                                    Log.v("Detalle de integrantes",_Integrantes);
                                                    Log.v("-","-----------------------------------------------------------");

                                                }
                                            }

                                            etPagoRealizado.setText(parent.jsonRes.getString(Constants.PAGO_REALIZADO));

                                        }

                                        if (parent.jsonRes.has(Constants.FECHA_DEPOSITO)){
                                            tvFechaDeposito.setText(parent.jsonRes.getString(Constants.FECHA_DEPOSITO));
                                            tvFechaDeposito.setError(null);
                                        }

                                        if (Miscellaneous.MedioPago(tvMedioPago) == 6){
                                            if (parent.jsonRes.has(Constants.IMPRESORA)){
                                                tvImprimirRecibo.setText(parent.jsonRes.getString(IMPRESORA));
                                                SelectImprimirRecibos(Miscellaneous.Impresion(tvImprimirRecibo));
                                                etFolioRecibo.setEnabled(true);
                                                if (!parent.flagRespuesta){
                                                    tvImprimirRecibo.setEnabled(false);
                                                    etFolioRecibo.setEnabled(false);
                                                }
                                                if (Miscellaneous.Impresion(tvImprimirRecibo) == 0){


                                                    if (parent.jsonRes.has(Constants.FOLIO_TICKET)){
                                                        ibImprimir.setVisibility(View.VISIBLE);
                                                        if (!parent.flagRespuesta)
                                                            ibImprimir.setVisibility(View.GONE);
                                                        llFolioRecibo.setVisibility(View.VISIBLE);
                                                        etFolioRecibo.setEnabled(false);
                                                        etFolioRecibo.setText(parent.jsonRes.getString(Constants.FOLIO_TICKET));
                                                        etFolioRecibo.setError(null);
                                                        folio_impreso = parent.jsonRes.getString(Constants.FOLIO_TICKET);
                                                    }
                                                    else {
                                                        ibImprimir.setVisibility(View.VISIBLE);
                                                    }
                                                }
                                                else{
                                                    ibImprimir.setVisibility(View.GONE);
                                                    llFolioRecibo.setVisibility(View.VISIBLE);
                                                    etFolioRecibo.setText(parent.jsonRes.getString(Constants.FOLIO_TICKET));
                                                    etFolioRecibo.setError(null);
                                                }

                                            }
                                        }

                                        if (parent.jsonRes.has(Constants.EVIDENCIA)){
                                            Log.v("PATH_EVIDENCIA", Constants.ROOT_PATH + "Evidencia/"+parent.jsonRes.getString(Constants.EVIDENCIA));
                                            File evidenciaFile = new File(Constants.ROOT_PATH + "Evidencia/"+parent.jsonRes.getString(Constants.EVIDENCIA));
                                            Uri uriEvidencia = Uri.fromFile(evidenciaFile);
                                            Glide.with(ctx).load(uriEvidencia).into(ivEvidencia);
                                            ibFoto.setVisibility(View.GONE);
                                            ibGaleria.setVisibility(View.GONE);
                                            ivEvidencia.setVisibility(View.VISIBLE);
                                            byteEvidencia = Miscellaneous.getBytesUri(ctx, uriEvidencia, 1);
                                            tvFotoGaleria.setError(null);
                                        }
                                    }
                                    break;
                            }
                        }
                        break;
                    case 2: //====================================================================== Aclaracion
                        if (parent.jsonRes.has(Constants.MOTIVO_ACLARACION)){
                            tvMotivoAclaracion.setText(parent.jsonRes.getString(Constants.MOTIVO_ACLARACION));
                            tvMotivoAclaracion.setVisibility(View.VISIBLE);
                            tvMotivoAclaracion.setError(null);
                            if (!parent.flagRespuesta)
                                tvMotivoAclaracion.setEnabled(false);
                        }
                        if (parent.jsonRes.has(Constants.COMENTARIO)){
                            etComentario.setText(parent.jsonRes.getString(Constants.COMENTARIO));
                            etComentario.setVisibility(View.VISIBLE);
                            etComentario.setError(null);
                            if (!parent.flagRespuesta)
                                etComentario.setEnabled(false);
                        }
                        tvGerente.setVisibility(View.VISIBLE);
                        break;
                }

                if (parent.jsonRes.has(Constants.GERENTE)){
                    tvGerente.setText(parent.jsonRes.getString(GERENTE));

                    if (!parent.flagRespuesta){
                        tvGerente.setEnabled(false);
                    }
                    if (Miscellaneous.Gerente(tvGerente) == 0){
                        SelectEstaGerente(Miscellaneous.Gerente(tvGerente));
                        if (parent.jsonRes.has(Constants.FIRMA)){
                            File firmaFile = new File(Constants.ROOT_PATH + "Firma/"+parent.jsonRes.getString(Constants.FIRMA));
                            Uri uriFirma = Uri.fromFile(firmaFile);
                            Glide.with(ctx).load(uriFirma).into(ivFirma);
                            ibFirma.setVisibility(View.GONE);
                            ivFirma.setVisibility(View.VISIBLE);
                            byteFirma = Miscellaneous.getBytesUri(ctx, uriFirma, 1);
                            tvFirma.setError(null);
                        }
                    }
                }
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case REQUEST_CODE_FIRMA:
                if (resultCode == Activity.RESULT_OK){
                    if (data != null){
                        ibFirma.setVisibility(View.GONE);
                        ivFirma.setVisibility(View.VISIBLE);
                        tvFirma.setError(null);
                        Glide.with(ctx)
                                .load(data.getByteArrayExtra(FIRMA_IMAGE))
                                .into(ivFirma);
                        byteFirma = data.getByteArrayExtra(FIRMA_IMAGE);

                        /*try {
                            jsonResponse.put(FIRMA,  Miscellaneous.save(byteFirma, 3));
                            UpdateTemporal(jsonResponse);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }*/
                    }
                }
                break;
            case Constants.REQUEST_CODE_CAMARA_FACHADA:
                if (resultCode == Activity.RESULT_OK){
                    if (data != null){
                        ibFachada.setVisibility(View.GONE);
                        tvFachada.setError(null);
                        ivFachada.setVisibility(View.VISIBLE);
                        byteEvidencia = data.getByteArrayExtra(Constants.PICTURE);
                        Glide.with(ctx).load(byteEvidencia).centerCrop().into(ivFachada);
                    }
                }
                break;
            case Constants.REQUEST_CODE_INTEGRANTES_GPO:
                if (resultCode == Activity.RESULT_OK){
                    if (data != null){
                        etPagoRealizado.setError(null);
                        etPagoRealizado.setText(String.valueOf(data.getStringExtra(Constants.RESPONSE)));
                        _Integrantes = data.getStringExtra("integrantes");
                        rbIntegrantes.setChecked(true);
                        Log.v("/","/////////////////////////////////////////////////////////////");
                        Log.v("RespuestaIntegrantes", _Integrantes);
                        Log.v("/","/////////////////////////////////////////////////////////////");
                        if (tvMedioPago.getText().toString().trim().equals("EFECTIVO")){
                            if (Double.parseDouble(etPagoRealizado.getText().toString().trim()) > 10000){
                                llArqueoCaja.setVisibility(View.VISIBLE);
                            }
                            else {
                                llArqueoCaja.setVisibility(View.GONE);
                                //rbArqueoCaja.setChecked(false);
                            }
                        }
                    }
                }
                break;
            case Constants.REQUEST_CODE_IMPRESORA:
                if (resultCode == Activity.RESULT_OK){
                    if (data != null){
                        Toast.makeText(ctx, data.getStringExtra(Constants.MESSAGE), Toast.LENGTH_SHORT).show();
                        if(data.getIntExtra(Constants.RES_PRINT,0) == 1 || data.getIntExtra(Constants.RES_PRINT,0) == 2){
                            folio_impreso = "Asesor002-" + String.valueOf(data.getIntExtra(Constants.FOLIO,0));
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
            case Constants.REQUEST_CODE_ARQUEO_CAJA:
                if (resultCode == Activity.RESULT_OK){
                    if (data != null){
                        if (data.getBooleanExtra(Constants.SAVE,false)){
                            tvArqueoCaja.setError(null);
                            //rbArqueoCaja.setChecked(true);
                        }
                        else {
                            tvArqueoCaja.setError("");
                            //rbArqueoCaja.setChecked(false);
                        }
                    }
                }
                break;
            case 123:
                if (resultCode == 321){
                    if (data != null){
                        tvFechaDefuncion.setError(null);
                        tvFechaDefuncion.setText(data.getStringExtra(DATE));
                    }
                }
                break;
            case 812:
                if (resultCode == 321){
                    if (data != null){
                        tvFechaDeposito.setError(null);
                        tvFechaDeposito.setText(data.getStringExtra(DATE));
                    }
                }
                break;
        }
    }
}
