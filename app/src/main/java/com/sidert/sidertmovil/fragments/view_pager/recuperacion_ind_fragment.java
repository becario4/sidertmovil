package com.sidert.sidertmovil.fragments.view_pager;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.ColorDrawable;
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
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
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
import com.sidert.sidertmovil.activities.CameraVertical;
import com.sidert.sidertmovil.activities.CapturarFirma;
import com.sidert.sidertmovil.activities.PrintSeewoo;
import com.sidert.sidertmovil.activities.RecuperacionIndividual;
import com.sidert.sidertmovil.database.DBhelper;
import com.sidert.sidertmovil.fragments.dialogs.dialog_date_picker;
import com.sidert.sidertmovil.models.OrderModel;
import com.sidert.sidertmovil.utils.CanvasCustom;
import com.sidert.sidertmovil.utils.Constants;
import com.sidert.sidertmovil.utils.Miscellaneous;
import com.sidert.sidertmovil.utils.MyCurrentListener;
import com.sidert.sidertmovil.utils.NameFragments;
import com.sidert.sidertmovil.utils.Popups;

import org.json.JSONException;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;

import static com.sidert.sidertmovil.utils.Constants.ACTUALIZAR_TELEFONO;
import static com.sidert.sidertmovil.utils.Constants.COMENTARIO;
import static com.sidert.sidertmovil.utils.Constants.CONTACTO;
import static com.sidert.sidertmovil.utils.Constants.DATE;
import static com.sidert.sidertmovil.utils.Constants.DATE_CURRENT;
import static com.sidert.sidertmovil.utils.Constants.DAY_CURRENT;
import static com.sidert.sidertmovil.utils.Constants.EVIDENCIA;
import static com.sidert.sidertmovil.utils.Constants.FACHADA;
import static com.sidert.sidertmovil.utils.Constants.FECHAS_POST;
import static com.sidert.sidertmovil.utils.Constants.FECHA_DEFUNCION;
import static com.sidert.sidertmovil.utils.Constants.FECHA_DEPOSITO;
import static com.sidert.sidertmovil.utils.Constants.FIRMA;
import static com.sidert.sidertmovil.utils.Constants.FIRMA_IMAGE;
import static com.sidert.sidertmovil.utils.Constants.FOLIO;
import static com.sidert.sidertmovil.utils.Constants.FOLIO_TICKET;
import static com.sidert.sidertmovil.utils.Constants.FORMAT_DATE_GNRAL;
import static com.sidert.sidertmovil.utils.Constants.FORMAT_TIMESTAMP;
import static com.sidert.sidertmovil.utils.Constants.GERENTE;
import static com.sidert.sidertmovil.utils.Constants.IDENTIFIER;
import static com.sidert.sidertmovil.utils.Constants.IMPRESORA;
import static com.sidert.sidertmovil.utils.Constants.LATITUD;
import static com.sidert.sidertmovil.utils.Constants.LONGITUD;
import static com.sidert.sidertmovil.utils.Constants.MEDIO_PAGO;
import static com.sidert.sidertmovil.utils.Constants.MESSAGE;
import static com.sidert.sidertmovil.utils.Constants.MONTH_CURRENT;
import static com.sidert.sidertmovil.utils.Constants.MOTIVO_ACLARACION;
import static com.sidert.sidertmovil.utils.Constants.MOTIVO_NO_PAGO;
import static com.sidert.sidertmovil.utils.Constants.NUEVO_TELEFONO;
import static com.sidert.sidertmovil.utils.Constants.ORDER_ID;
import static com.sidert.sidertmovil.utils.Constants.PAGO_REALIZADO;
import static com.sidert.sidertmovil.utils.Constants.PAGO_REQUERIDO;
import static com.sidert.sidertmovil.utils.Constants.PICTURE;
import static com.sidert.sidertmovil.utils.Constants.POS_MEDIO_PAGO;
import static com.sidert.sidertmovil.utils.Constants.REQUEST_CODE_CAMARA_FACHADA;
import static com.sidert.sidertmovil.utils.Constants.REQUEST_CODE_CAMARA_TICKET;
import static com.sidert.sidertmovil.utils.Constants.REQUEST_CODE_FIRMA;
import static com.sidert.sidertmovil.utils.Constants.REQUEST_CODE_FIRMA_ASESOR;
import static com.sidert.sidertmovil.utils.Constants.REQUEST_CODE_GALERIA;
import static com.sidert.sidertmovil.utils.Constants.REQUEST_CODE_IMPRESORA;
import static com.sidert.sidertmovil.utils.Constants.RESULTADO_PAGO;
import static com.sidert.sidertmovil.utils.Constants.RES_PRINT;
import static com.sidert.sidertmovil.utils.Constants.ROOT_PATH;
import static com.sidert.sidertmovil.utils.Constants.TIPO;
import static com.sidert.sidertmovil.utils.Constants.TIPO_IMAGEN;
import static com.sidert.sidertmovil.utils.Constants.YEAR_CURRENT;
import static com.sidert.sidertmovil.utils.Constants.camara;
import static com.sidert.sidertmovil.utils.Constants.firma;
import static com.sidert.sidertmovil.utils.Constants.question;


public class recuperacion_ind_fragment extends Fragment {

    private Context ctx;
    RecuperacionIndividual parent;

    public String[] _contacto;
    public String[] _motivo_aclaracion;
    public String[] _confirmacion;
    public String[] _resultado_gestion;
    public String[] _medio_pago;
    public String[] _motivo_no_pago;
    public String[] _imprimir;

    public byte[] byteFirma;
    public byte[] byteEvidencia;

    private Uri imageUri;

    public TextView tvmapa;
    public TextView tvContacto;
    public TextView tvActualizarTelefono;
    public TextView tvResultadoGestion;
    public TextView tvMedioPago;
    public TextView tvFechaDeposito;
    public TextView tvMontoPagoRequerido;
    public TextView tvPagaraRequerido;
    public TextView tvImprimirRecibo;
    public TextView tvFotoGaleria;
    public TextView tvMotivoAclaracion;
    public TextView tvMotivoNoPago;
    public TextView tvFechaDefuncion;
    public TextView tvFachada;
    public TextView tvGerente;
    public TextView tvFirma;

    private ImageButton ibMap;
    private ImageButton ibFoto;
    private ImageButton ibImprimir;
    private ImageButton ibGaleria;
    private ImageButton ibFachada;
    private ImageButton ibFirma;

    private ImageView ivEvidencia;
    private ImageView ivFachada;
    private ImageView ivFirma;

    public EditText etActualizarTelefono;
    public EditText etPagoRealizado;
    public EditText etFolioRecibo;
    public EditText etComentario;

    private LinearLayout llActualizarTelefono;
    private LinearLayout llResultadoGestion;
    private LinearLayout llMedioPago;
    private LinearLayout llFechaDeposito;
    private LinearLayout llMontoPagoRequerido;
    private LinearLayout llPagaraRequerido;
    private LinearLayout llMontoPagoRealizado;
    private LinearLayout llImprimirRecibo;
    private LinearLayout llFolioRecibo;
    private LinearLayout llFotoGaleria;
    private LinearLayout llMotivoAclaracion;
    private LinearLayout llMotivoNoPago;
    private LinearLayout llDefuncion;
    private LinearLayout llComentario;
    private LinearLayout llFachada;
    private LinearLayout llGerente;
    private LinearLayout llFirma;

    private boolean flagUbicacion = false;

    private LocationManager locationManager;
    private MyCurrentListener locationListener;

    private ProgressBar pbLoading;

    private MapView mapView;
    private GoogleMap mMap;
    private Marker mMarker;
    public LatLng latLngGestion;

    private String folio_impreso = "";

    private DBhelper dBhelper;
    private SQLiteDatabase db;

    private Calendar myCalendar;
    private SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_DATE_GNRAL);


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_recuperacion_ind, container, false);

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

        parent                = (RecuperacionIndividual) getActivity();
        assert parent != null;
        parent.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        tvmapa                  = v.findViewById(R.id.tvMapa);
        tvContacto              = v.findViewById(R.id.tvContacto);
        tvActualizarTelefono    = v.findViewById(R.id.tvActualizarTelefono);
        tvResultadoGestion      = v.findViewById(R.id.tvResultadoGestion);
        tvMedioPago             = v.findViewById(R.id.tvMedioPago);
        tvFechaDeposito         = v.findViewById(R.id.tvFechaDeposito);
        tvMontoPagoRequerido    = v.findViewById(R.id.tvMontoPagoRequerido);
        tvPagaraRequerido       = v.findViewById(R.id.tvPagaraRequerido);
        tvImprimirRecibo        = v.findViewById(R.id.tvImprimirRecibo);
        tvFotoGaleria           = v.findViewById(R.id.tvFotoGaleria);
        tvMotivoAclaracion      = v.findViewById(R.id.tvMotivoAclaracion);
        tvMotivoNoPago          = v.findViewById(R.id.tvMotivoNoPago);
        tvFechaDefuncion        = v.findViewById(R.id.tvFechaDefuncion);
        tvFachada               = v.findViewById(R.id.tvFachada);
        tvGerente               = v.findViewById(R.id.tvGerente);
        tvFirma                 = v.findViewById(R.id.tvFirma);

        etActualizarTelefono    = v.findViewById(R.id.etActualizarTelefono);
        etPagoRealizado         = v.findViewById(R.id.etPagoRealizado);
        etFolioRecibo           = v.findViewById(R.id.etFolioRecibo);
        etComentario        = v.findViewById(R.id.etComentario);

        ibMap           = v.findViewById(R.id.ibMap);
        ibImprimir      = v.findViewById(R.id.ibImprimir);
        ibFoto          = v.findViewById(R.id.ibFoto);
        ibGaleria       = v.findViewById(R.id.ibGaleria);
        ibFachada       = v.findViewById(R.id.ibFachada);
        ibFirma         = v.findViewById(R.id.ibFirma);

        ivEvidencia     = v.findViewById(R.id.ivEvidencia);
        ivFachada       = v.findViewById(R.id.ivFachada);
        ivFirma         = v.findViewById(R.id.ivFirma);

        pbLoading       = v.findViewById(R.id.pbLoading);

        mapView         = v.findViewById(R.id.mapGestion);

        llActualizarTelefono    = v.findViewById(R.id.llActualizarTelefono);
        llResultadoGestion      = v.findViewById(R.id.llResultadoGestion);
        llMedioPago             = v.findViewById(R.id.llMedioPago);
        llFechaDeposito         = v.findViewById(R.id.llFechaDeposito);
        llMontoPagoRequerido    = v.findViewById(R.id.llMontoPagoRequerido);
        llPagaraRequerido       = v.findViewById(R.id.llPagaraRequerido);
        llMontoPagoRealizado    = v.findViewById(R.id.llMontoPagoRealizado);
        llImprimirRecibo        = v.findViewById(R.id.llImprimirRecibo);
        llFolioRecibo           = v.findViewById(R.id.llFolioRecibo);
        llFotoGaleria           = v.findViewById(R.id.llFotoGaleria);
        llMotivoAclaracion = v.findViewById(R.id.llMotivoAclaracion);
        llMotivoNoPago      = v.findViewById(R.id.llMotivoNoPago);
        llDefuncion         = v.findViewById(R.id.llDatosDefuncion);
        llComentario        = v.findViewById(R.id.llComentario);
        llFachada           = v.findViewById(R.id.llFachada);
        llGerente           = v.findViewById(R.id.llGerente);
        llFirma             = v.findViewById(R.id.llFirma);

        mapView.onCreate(savedInstanceState);
        locationManager = (LocationManager) parent.getSystemService(Context.LOCATION_SERVICE);

        // ImageButton Click
        ibMap.setOnClickListener(ibMap_OnClick);
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
        tvPagaraRequerido.setOnClickListener(tvPagaraRequerido_OnClick);
        tvImprimirRecibo.setOnClickListener(tvImprimirRecibo_OnClick);
        tvMotivoAclaracion.setOnClickListener(tvMotivoAclaracion_OnClick);
        tvMotivoNoPago.setOnClickListener(tvMotivoNoPago_OnClick);
        tvFechaDefuncion.setOnClickListener(tvFechaDefuncion_OnClick);
        tvGerente.setOnClickListener(tvGerente_OnClick);

        if (parent.flagRespuesta) {
            ivFirma.setOnClickListener(ivFirma_OnClick);
            ivEvidencia.setOnClickListener(ivEvidencia_OnClick);
            ivFachada.setOnClickListener(ivFotoFachada_OnClick);
        }

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

    private View.OnClickListener ibImprimir_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (!etPagoRealizado.getText().toString().trim().isEmpty() && Double.parseDouble(etPagoRealizado.getText().toString().trim()) > 0){
                Intent i = new Intent(ctx, PrintSeewoo.class);
                OrderModel order = new OrderModel(parent.ficha_ri.getId(),
                        "002",
                        parent.ficha_ri.getPrestamo().getMontoPrestamo(),
                        parent.ficha_ri.getPrestamo().getPagoSemanal(),
                        Miscellaneous.doubleFormat(etPagoRealizado),
                        parent.ficha_ri.getCliente().getNumeroCliente(),
                        parent.ficha_ri.getPrestamo().getNumeroDePrestamo(),
                        parent.ficha_ri.getCliente().getNombre(),
                        "NOMBRE DEL ALGUN ASESOR",
                        0);

                i.putExtra("order",order);
                i.putExtra("tag",true);

                startActivityForResult(i,REQUEST_CODE_IMPRESORA);
            }
            else
                Toast.makeText(ctx, "No ha capturado el pago realizado del cliente", Toast.LENGTH_SHORT).show();
        }
    };

    private View.OnClickListener ibFoto_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i = new Intent(parent, CameraVertical.class);
            i.putExtra(ORDER_ID, parent.ficha_ri.getId());
            startActivityForResult(i, REQUEST_CODE_CAMARA_TICKET);
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
            i.putExtra(ORDER_ID, parent.ficha_ri.getId());
            startActivityForResult(i, REQUEST_CODE_CAMARA_FACHADA);
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
                            tvActualizarTelefono.setError(null);
                            tvMotivoAclaracion.setError(null);
                            tvMotivoAclaracion.setError(null);
                            tvFachada.setError(null);

                            /*try {
                                jsonResponse.put(CONTACTO, position);
                                UpdateTemporal(jsonResponse);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }*/

                            Log.e("----------","Contacto Cliente  "  + position);
                            SelectContactoCliente(position);
                            /*ContentValues cv = new ContentValues();
                            cv.put("periodicidad", tvFrecuencia.getText().toString().trim().toUpperCase());
                            if (ENVIROMENT)
                                db.update(DATOS_CREDITO_IND, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});
                            else
                                db.update(DATOS_CREDITO_IND_T, cv, "id_solicitud = ?", new String[]{String.valueOf(id_solicitud)});*/
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
                b.putInt(IDENTIFIER, 6);
                b.putBoolean(FECHAS_POST, false);
                dialogDatePicker.setArguments(b);
                dialogDatePicker.setTargetFragment(recuperacion_ind_fragment.this,812);
                dialogDatePicker.show(parent.getSupportFragmentManager(), NameFragments.DIALOGDATEPICKER);
            }
        }
    };

    private View.OnClickListener tvPagaraRequerido_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
            builder.setTitle(R.string.selected_option)
                    .setItems(R.array.confirmacion, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int position) {
                            tvPagaraRequerido.setError(null);
                            tvPagaraRequerido.setText(_confirmacion[position]);
                            switch (position){
                                case 0:
                                    if (Double.parseDouble(etPagoRealizado.getText().toString().trim()) > 0){
                                        SelectPagoRequerido(0);
                                    }
                                    else {
                                        Toast.makeText(ctx, "No se pueden capturar pagos iguales a cero", Toast.LENGTH_SHORT).show();
                                        tvPagaraRequerido.setText("");
                                        tvPagaraRequerido.setError("");
                                        SelectPagoRequerido(-1);
                                    }
                                    break;
                                case 1:
                                    SelectPagoRequerido(1);
                                    break;
                                default:
                                    tvPagaraRequerido.setError("");
                                    SelectPagoRequerido(-1);
                                    break;
                            }

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
                b.putInt(IDENTIFIER, 5);
                b.putBoolean(FECHAS_POST, false);
                dialogDatePicker.setArguments(b);
                dialogDatePicker.setTargetFragment(recuperacion_ind_fragment.this,123);
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

    private View.OnClickListener ivFirma_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            final AlertDialog firma_dlg = Popups.showDialogConfirm(ctx, firma,
                    R.string.capturar_nueva_firma, R.string.accept, new Popups.DialogMessage() {
                        @Override
                        public void OnClickListener(AlertDialog dialog) {
                            Intent sig = new Intent(ctx, CapturarFirma.class);
                            startActivityForResult(sig, REQUEST_CODE_FIRMA);
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
            if (tvMedioPago.getText().toString().trim().toUpperCase().equals("EFECTIVO")){
                final AlertDialog evidencia_dlg = Popups.showDialogConfirm(ctx, question,
                        R.string.capturar_nueva_fotografia, R.string.fotografia, new Popups.DialogMessage() {
                            @Override
                            public void OnClickListener(AlertDialog dialog) {
                                Intent i = new Intent(parent, CameraVertical.class);
                                i.putExtra(ORDER_ID, parent.ficha_ri.getId());
                                startActivityForResult(i, REQUEST_CODE_CAMARA_TICKET);
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
                final AlertDialog evidencia_dlg = Popups.showDialogConfirmImage(ctx, question,
                        R.string.capturar_foto_galeria, R.string.fotografia, new Popups.DialogMessage() {
                            @Override
                            public void OnClickListener(AlertDialog dialog) {
                                Intent i = new Intent(parent, CameraVertical.class);
                                i.putExtra(ORDER_ID, parent.ficha_ri.getId());
                                startActivityForResult(i, REQUEST_CODE_CAMARA_TICKET);
                                dialog.dismiss();

                            }
                        }, R.string.galeria, new Popups.DialogMessage() {
                            @Override
                            public void OnClickListener(AlertDialog dialog) {
                                Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                                gallery.setAction(Intent.ACTION_GET_CONTENT);
                                startActivityForResult(Intent.createChooser(gallery, "Select Picture"), REQUEST_CODE_GALERIA);
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
            final AlertDialog fachada_cam_dlg = Popups.showDialogConfirm(ctx, camara,
                    R.string.capturar_nueva_fotografia, R.string.accept, new Popups.DialogMessage() {
                        @Override
                        public void OnClickListener(AlertDialog dialog) {
                            Intent i = new Intent(parent, CameraVertical.class);
                            i.putExtra(ORDER_ID, parent.ficha_ri.getId());
                            startActivityForResult(i, REQUEST_CODE_CAMARA_FACHADA);
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

    //=========================  Comportamientos  ================================================
    private void SelectContactoCliente (int pos){
        if (!tvGerente.getText().toString().trim().isEmpty()) tvGerente.setError(null);
        else tvGerente.setError("");
        switch (pos){
            case 0: // Si contacto cliente
                tvActualizarTelefono.setError("");
                tvResultadoGestion.setError("");
                tvMotivoAclaracion.setText("");
                if (parent.jsonRes != null) {
                    try {
                        Log.e("1ResultadoGestion", parent.jsonRes.getString(RESULTADO_PAGO));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                tvResultadoGestion.setText("");
                if (parent.jsonRes != null) {
                    try {
                        Log.e("2ResultadoGestion", parent.jsonRes.getString(RESULTADO_PAGO));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                SelectResultadoGestion(-1);
                tvGerente.setText("");
                SelectEstaGerente(-1);
                llResultadoGestion.setVisibility(View.VISIBLE);
                llActualizarTelefono.setVisibility(View.VISIBLE);
                llComentario.setVisibility(View.GONE);
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
                tvActualizarTelefono.setText("");
                SelectActualizarTelefono(-1);
                llActualizarTelefono.setVisibility(View.GONE);
                tvGerente.setText("");
                SelectEstaGerente(-1);
                byteEvidencia = null;
                ivFachada.setVisibility(View.GONE);
                ibFachada.setVisibility(View.VISIBLE);
                llResultadoGestion.setVisibility(View.GONE);
                llComentario.setVisibility(View.VISIBLE);
                etComentario.setError(getResources().getString(R.string.campo_requerido));
                llFachada.setVisibility(View.VISIBLE);
                llGerente.setVisibility(View.VISIBLE);
                llMotivoAclaracion.setVisibility(View.GONE);
                break;
            case 2: // Aclaración
                tvMotivoAclaracion.setError("");
                etComentario.setError(getResources().getString(R.string.campo_requerido));
                etComentario.setText("");
                tvResultadoGestion.setText("");
                SelectResultadoGestion(-1);
                tvGerente.setText("");
                SelectEstaGerente(-1);
                byteEvidencia = null;
                ivFachada.setVisibility(View.GONE);
                ibFachada.setVisibility(View.VISIBLE);
                tvActualizarTelefono.setText("");
                SelectActualizarTelefono(-1);
                llActualizarTelefono.setVisibility(View.GONE);
                llResultadoGestion.setVisibility(View.GONE);
                llComentario.setVisibility(View.VISIBLE);
                llFachada.setVisibility(View.GONE);
                llGerente.setVisibility(View.VISIBLE);
                llMotivoAclaracion.setVisibility(View.VISIBLE);
                break;
            default: //Sin seleccionar una opción o cualquier otro valor
                tvMotivoAclaracion.setText("");
                tvResultadoGestion.setText("");
                tvActualizarTelefono.setText("");
                SelectActualizarTelefono(-1);
                llActualizarTelefono.setVisibility(View.GONE);
                llResultadoGestion.setVisibility(View.GONE);
                llComentario.setVisibility(View.GONE);
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
    private void SelectResultadoGestion(int pos){
        switch (pos){
            case 0: //Si Pago
                tvMotivoNoPago.setText("");
                SelectMotivoNoPago(-1);
                llComentario.setVisibility(View.GONE);
                llMedioPago.setVisibility(View.VISIBLE);
                llMontoPagoRequerido.setVisibility(View.VISIBLE);
                llMotivoNoPago.setVisibility(View.GONE);
                llFachada.setVisibility(View.GONE);
                llGerente.setVisibility(View.GONE);
                break;
            case 1: //No Pago
                tvMotivoNoPago.setError("");
                tvFachada.setError("");
                tvMedioPago.setText("");
                SelectMedioPago(-1);
                tvPagaraRequerido.setText("");
                SelectPagoRequerido(-1);
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
                tvPagaraRequerido.setText("");
                SelectPagoRequerido(-1);
                llMedioPago.setVisibility(View.GONE);
                llMontoPagoRequerido.setVisibility(View.GONE);
                llComentario.setVisibility(View.GONE);
                llMotivoNoPago.setVisibility(View.GONE);
                llFachada.setVisibility(View.GONE);
                llGerente.setVisibility(View.GONE);
                break;
        }
    }
    private void SelectMedioPago (int pos){
        Log.e("MEdio pafo", ""+pos);
        if (!parent.flagRespuesta) {
            tvMedioPago.setEnabled(false);
            if (pos > 0 && pos < 6 || pos == 7) {
                tvFechaDeposito.setEnabled(false);
            } else {
                tvFechaDeposito.setEnabled(true);
            }
        }

        if (!tvPagaraRequerido.getText().toString().trim().isEmpty()) tvPagaraRequerido.setError(null);
        else tvPagaraRequerido.setError("");

        tvMedioPago.setError(null);
        switch (pos){
            case -1: // Opción "Seleccione una opción"
                tvMedioPago.setError("");
                tvPagaraRequerido.setText("");;
                SelectPagoRequerido(-1);
                llPagaraRequerido.setVisibility(View.GONE);
                llFechaDeposito.setVisibility(View.GONE);
                llFotoGaleria.setVisibility(View.GONE);
                llImprimirRecibo.setVisibility(View.GONE);
                llFolioRecibo.setVisibility(View.GONE);
                llGerente.setVisibility(View.GONE);
                break;
            case 0: // Banamex
            case 1: // Banorte
            case 2: // Telecom
            case 3: // Bansefi
            case 4: // Bancomer
            case 7: // Sidert
                if (byteEvidencia != null)
                    tvFotoGaleria.setError(null);
                else
                    tvFotoGaleria.setError("");

                Log.v("Aqui paso"," primero");
                if (tvFechaDeposito.getText().toString().isEmpty())
                    tvFechaDeposito.setError(getResources().getString(R.string.campo_requerido));
                ibGaleria.setEnabled(true);
                ibGaleria.setBackground(ctx.getResources().getDrawable(R.drawable.round_corner_blue));
                tvPagaraRequerido.setEnabled(true);
                llPagaraRequerido.setVisibility(View.VISIBLE);
                llFechaDeposito.setVisibility(View.VISIBLE);
                llMontoPagoRequerido.setVisibility(View.VISIBLE);
                llImprimirRecibo.setVisibility(View.GONE);
                llFotoGaleria.setVisibility(View.VISIBLE);
                llGerente.setVisibility(View.VISIBLE);
                break;
            case 5: // Oxxo
                if (byteEvidencia != null)
                    tvFotoGaleria.setError(null);
                else
                    tvFotoGaleria.setError("");

                Log.v("Aqui paso"," primero");
                if (tvFechaDeposito.getText().toString().isEmpty())
                    tvFechaDeposito.setError(getResources().getString(R.string.campo_requerido));
                ibGaleria.setEnabled(true);
                ibGaleria.setBackground(ctx.getResources().getDrawable(R.drawable.round_corner_blue));
                llPagaraRequerido.setVisibility(View.VISIBLE);
                llFechaDeposito.setVisibility(View.VISIBLE);
                llMontoPagoRequerido.setVisibility(View.VISIBLE);
                tvPagaraRequerido.setText(_confirmacion[0]);
                tvPagaraRequerido.setEnabled(false);
                SelectPagoRequerido(0);
                llImprimirRecibo.setVisibility(View.GONE);
                llFotoGaleria.setVisibility(View.VISIBLE);
                llGerente.setVisibility(View.VISIBLE);
                break;
            case 6: // Efectivo
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
                tvPagaraRequerido.setEnabled(true);
                llMontoPagoRequerido.setVisibility(View.VISIBLE);
                llImprimirRecibo.setVisibility(View.VISIBLE);
                llFotoGaleria.setVisibility(View.VISIBLE);
                llGerente.setVisibility(View.VISIBLE);
                break;
            default: //Sin seleccionar una opción o cualquier otro valor
                tvMedioPago.setError("");
                tvPagaraRequerido.setText("");
                tvImprimirRecibo.setText("");
                SelectImprimirRecibos(-1);
                SelectPagoRequerido(-1);
                ivEvidencia.setVisibility(View.GONE);
                tvPagaraRequerido.setEnabled(true);
                llPagaraRequerido.setVisibility(View.GONE);
                llFechaDeposito.setVisibility(View.GONE);
                llFotoGaleria.setVisibility(View.GONE);
                llImprimirRecibo.setVisibility(View.GONE);
                llFolioRecibo.setVisibility(View.GONE);
                llGerente.setVisibility(View.GONE);
                break;
        }
    }
    private void SelectPagoRequerido (int pos){
        switch (pos){
            case -1: //Sin seleccionar una opción o cualquier otro valor
                etPagoRealizado.setText(String.valueOf(parent.ficha_ri.getPrestamo().getPagoRealizado()));
                etPagoRealizado.setEnabled(false);
                llMontoPagoRealizado.setVisibility(View.GONE);
                llImprimirRecibo.setVisibility(View.GONE);
                llFolioRecibo.setVisibility(View.GONE);
                break;
            case 0: // Si pagará requerido
                etPagoRealizado.setText(String.valueOf(parent.ficha_ri.getPrestamo().getPagoRealizado()));
                etPagoRealizado.setEnabled(false);
                llMontoPagoRealizado.setVisibility(View.VISIBLE);
                break;
            case 1: // No pagará requerido
                etPagoRealizado.setEnabled(true);
                llMontoPagoRealizado.setVisibility(View.VISIBLE);
                break;
            default:
                etPagoRealizado.setText(String.valueOf(parent.ficha_ri.getPrestamo().getPagoRealizado()));
                etPagoRealizado.setEnabled(false);
                llMontoPagoRealizado.setVisibility(View.GONE);
                llImprimirRecibo.setVisibility(View.GONE);
                llFolioRecibo.setVisibility(View.GONE);
                break;
        }
    }
    private void SelectImprimirRecibos(int pos){
        switch (pos){
            case 0: // Imprimir Recibos
                ibImprimir.setVisibility(View.VISIBLE);
                if (!folio_impreso.trim().isEmpty()) {
                    tvImprimirRecibo.setError(null);
                    etFolioRecibo.setText(folio_impreso);
                    etFolioRecibo.setEnabled(false);
                    etFolioRecibo.setError(null);
                    llFolioRecibo.setVisibility(View.VISIBLE);
                }
                else {
                    tvImprimirRecibo.setError("");
                    llFolioRecibo.setVisibility(View.GONE);
                }
                break;
            case 1: //No cuenta con bateria la impresora
                Log.v("aqui pasa", "Select Imprimir recibos");
                etFolioRecibo.setError(getResources().getString(R.string.campo_requerido));
                tvImprimirRecibo.setError(null);
                llFolioRecibo.setVisibility(View.VISIBLE);
                etFolioRecibo.setText("");
                etFolioRecibo.setEnabled(true);
                etFolioRecibo.setHint(R.string.folio_sidert);
                etFolioRecibo.setInputType(InputType.TYPE_CLASS_NUMBER);
                ibImprimir.setVisibility(View.GONE);
                break;
            default: // Sin seleccionar alguna opción o cualquier valor diferente
                tvImprimirRecibo.setError("");
                llFolioRecibo.setVisibility(View.GONE);
                ibImprimir.setVisibility(View.GONE);
                break;
        }

    }
    private void SelectMotivoNoPago (int pos){
        tvMotivoNoPago.setError(null);
        switch (pos){
            case 0: // Negación de pago
            case 2: // Otro
                llDefuncion.setVisibility(View.GONE);
                break;
            case 1: //Fallecimiento
                tvFechaDefuncion.setError("");
                if (!tvFechaDefuncion.getText().toString().trim().isEmpty())
                    tvFechaDefuncion.setError(null);
                llDefuncion.setVisibility(View.VISIBLE);
                break;
            default: //Sin seleccionar una opción o cualquier otro valor
                tvMotivoNoPago.setError("");
                llDefuncion.setVisibility(View.GONE);
                break;
        }
    }
    private void SelectEstaGerente (int pos){
        switch (pos){
            case 0: // Si está el gerente
                if (ivFirma.getVisibility() == View.VISIBLE && byteFirma != null)
                    tvFirma.setError(null);
                else
                    tvFirma.setError("");
                llFirma.setVisibility(View.VISIBLE);
                break;
            case 1: // No está el gerente
                llFirma.setVisibility(View.GONE);
                break;
            default: // Sin seleccionar alguna opción o cualquier valor diferente
                byteFirma = null;
                ivFirma.setVisibility(View.GONE);
                ibFirma.setVisibility(View.VISIBLE);
                llFirma.setVisibility(View.GONE);
                break;
        }
    }

    private void init() throws JSONException {
        tvmapa.setError("");
        tvContacto.setError("");
        tvMontoPagoRequerido.setText(String.valueOf(parent.ficha_ri.getPrestamo().getPagoSemanal()));

        if (parent.jsonRes != null){
            //jsonResponse = parent.jsonRes;
            Log.e("jsonParent", parent.jsonRes.toString());
            if (parent.jsonRes.has(LATITUD) && parent.jsonRes.has(LONGITUD)){
                try {
                    tvmapa.setError(null);
                    mapView.setVisibility(View.VISIBLE);
                    ColocarUbicacionGestion(parent.jsonRes.getDouble(LATITUD), parent.jsonRes.getDouble(LONGITUD));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            if (parent.jsonRes.has(CONTACTO)){
                if (!parent.flagRespuesta){
                    tvContacto.setEnabled(false);
                    /*for(int i = 0; i < rgContactoCliente.getChildCount(); i++){
                        ((RadioButton) rgContactoCliente.getChildAt(i)).setEnabled(false);
                    }*/
                }
                tvContacto.setText(parent.jsonRes.getString(CONTACTO));
                switch (Miscellaneous.ContactoCliente(tvContacto)) {
                    case 1: //NO CONTACTO
                        SelectContactoCliente(Miscellaneous.ContactoCliente(tvContacto));
                        if (parent.jsonRes.has(COMENTARIO)){
                            etComentario.setText(parent.jsonRes.getString(COMENTARIO));
                            etComentario.setVisibility(View.VISIBLE);
                            etComentario.setError(null);
                            if (!parent.flagRespuesta)
                                etComentario.setEnabled(false);
                        }

                        if (parent.jsonRes.has(FACHADA)){
                            File fachadaFile = new File(ROOT_PATH + "Fachada/"+parent.jsonRes.getString(FACHADA));
                            Uri uriFachada = Uri.fromFile(fachadaFile);
                            Glide.with(ctx).load(uriFachada).into(ivFachada);
                            ibFachada.setVisibility(View.GONE);
                            ivFachada.setVisibility(View.VISIBLE);
                            byteEvidencia = Miscellaneous.getBytesUri(ctx, uriFachada, 1);
                            tvFachada.setError(null);
                        }

                        tvGerente.setVisibility(View.VISIBLE);
                        if (parent.jsonRes.has(GERENTE)){
                            SelectEstaGerente(Miscellaneous.Gerente(tvGerente));
                            tvGerente.setText(parent.jsonRes.getString(GERENTE));
                            if (!parent.flagRespuesta){
                                tvGerente.setEnabled(false);
                            }
                            if (Miscellaneous.Gerente(tvGerente) == 0){
                                if (parent.jsonRes.has(FIRMA)){
                                    File firmaFile = new File(ROOT_PATH + "Firma/"+parent.jsonRes.getString(FIRMA));
                                    Uri uriFirma = Uri.fromFile(firmaFile);
                                    Glide.with(ctx).load(uriFirma).into(ivFirma);
                                    ibFirma.setVisibility(View.GONE);
                                    ivFirma.setVisibility(View.VISIBLE);
                                    byteFirma = Miscellaneous.getBytesUri(ctx, uriFirma, 1);
                                    tvFirma.setError(null);
                                }
                            }
                        }
                        break;
                    case 0: //SI CONTACTO
                        SelectContactoCliente(Miscellaneous.ContactoCliente(tvContacto));
                        if (!parent.flagRespuesta){
                            tvActualizarTelefono.setEnabled(false);
                            etActualizarTelefono.setEnabled(false);
                        }

                        Log.e("0xxxxxxx", parent.jsonRes.toString());

                        Log.e("0.5xxxxxxx", parent.jsonRes.toString());
                        if (parent.jsonRes.has(ACTUALIZAR_TELEFONO)){
                            tvActualizarTelefono.setText(parent.jsonRes.getString(ACTUALIZAR_TELEFONO));
                            if (Miscellaneous.ActualizarTelefono(tvActualizarTelefono) == 0){
                                if (parent.jsonRes.has(NUEVO_TELEFONO)){
                                    etActualizarTelefono.setText(parent.jsonRes.getString(NUEVO_TELEFONO));
                                    etActualizarTelefono.setError(null);
                                    etActualizarTelefono.setVisibility(View.VISIBLE);
                                }
                            }
                        }

                        if (parent.jsonRes.has(RESULTADO_PAGO)){
                            if (!parent.flagRespuesta){
                                tvResultadoGestion.setEnabled(false);
                            }
                            tvResultadoGestion.setText(parent.jsonRes.getString(RESULTADO_PAGO));
                            SelectResultadoGestion(Miscellaneous.ResultadoGestion(tvResultadoGestion));
                            switch (Miscellaneous.ResultadoGestion(tvResultadoGestion)){
                                case 1: //No Pago
                                    if (!parent.flagRespuesta) {
                                        etComentario.setEnabled(false);
                                        tvFechaDefuncion.setEnabled(false);
                                    }

                                    //SelectResultadoGestion(Miscellaneous.ResultadoGestion(tvResultadoGestion));
                                    tvMotivoNoPago.setText(parent.jsonRes.getString(MOTIVO_NO_PAGO));

                                    if (parent.jsonRes.has(FECHA_DEFUNCION)){
                                        SelectMotivoNoPago(Miscellaneous.MotivoNoPago(tvMotivoNoPago));
                                        tvFechaDefuncion.setText(parent.jsonRes.getString(FECHA_DEFUNCION));
                                        tvFechaDefuncion.setError(null);
                                        tvFechaDefuncion.setVisibility(View.VISIBLE);
                                    }

                                    if (parent.jsonRes.has(COMENTARIO)){
                                        etComentario.setText(parent.jsonRes.getString(COMENTARIO));
                                        etComentario.setVisibility(View.VISIBLE);
                                        etComentario.setError(null);
                                        if (!parent.flagRespuesta)
                                            etComentario.setEnabled(false);
                                    }

                                    if (parent.jsonRes.has(FACHADA)){
                                        File fachadaFile = new File(ROOT_PATH + "Fachada/"+parent.jsonRes.getString(FACHADA));
                                        Uri uriFachada = Uri.fromFile(fachadaFile);
                                        Glide.with(ctx).load(uriFachada).into(ivFachada);
                                        ibFachada.setVisibility(View.GONE);
                                        ivFachada.setVisibility(View.VISIBLE);
                                        byteEvidencia = Miscellaneous.getBytesUri(ctx, uriFachada, 1);
                                        tvFachada.setError(null);
                                    }

                                    tvGerente.setVisibility(View.VISIBLE);
                                    if (parent.jsonRes.has(GERENTE)){
                                        tvGerente.setText(parent.jsonRes.getString(GERENTE));
                                        SelectEstaGerente(Miscellaneous.Gerente(tvGerente));
                                        if (!parent.flagRespuesta){
                                            tvGerente.setEnabled(false);
                                        }
                                        if (parent.jsonRes.getString(GERENTE).equals("SI")){

                                            if (parent.jsonRes.has(FIRMA)){
                                                File firmaFile = new File(ROOT_PATH + "Firma/"+parent.jsonRes.getString(FIRMA));
                                                Uri uriFirma = Uri.fromFile(firmaFile);
                                                Glide.with(ctx).load(uriFirma).into(ivFirma);
                                                ibFirma.setVisibility(View.GONE);
                                                ivFirma.setVisibility(View.VISIBLE);
                                                byteFirma = Miscellaneous.getBytesUri(ctx, uriFirma, 1);
                                                tvFirma.setError(null);
                                            }
                                        }
                                    }
                                    break;
                                case 0: // Si Pago
                                    if (parent.jsonRes.has(MEDIO_PAGO)){
                                        tvMedioPago.setText(parent.jsonRes.getString(MEDIO_PAGO));
                                        SelectMedioPago(Miscellaneous.MedioPago(tvMedioPago));
                                        if (parent.jsonRes.has(PAGO_REQUERIDO)){

                                            if (!parent.flagRespuesta){
                                                tvPagaraRequerido.setEnabled(false);
                                                etPagoRealizado.setEnabled(false);
                                            }
                                            tvPagaraRequerido.setText(parent.jsonRes.getString(PAGO_REQUERIDO));
                                            SelectPagoRequerido(Miscellaneous.PagoRequerido(tvPagaraRequerido));
                                            etPagoRealizado.setText(parent.jsonRes.getString(PAGO_REALIZADO));
                                        }

                                        if (parent.jsonRes.has(FECHA_DEPOSITO)){
                                            tvFechaDeposito.setText(parent.jsonRes.getString(FECHA_DEPOSITO));
                                            tvFechaDeposito.setError(null);
                                        }

                                        if (parent.jsonRes.getString(MEDIO_PAGO).equals("EFECTIVO")){
                                            if (parent.jsonRes.has(IMPRESORA)){
                                                tvImprimirRecibo.setText(parent.jsonRes.getString(IMPRESORA));
                                                SelectImprimirRecibos(Miscellaneous.Impresion(tvImprimirRecibo));
                                                etFolioRecibo.setEnabled(true);
                                                if (!parent.flagRespuesta){
                                                    tvImprimirRecibo.setEnabled(false);
                                                    etFolioRecibo.setEnabled(false);
                                                }
                                                if (parent.jsonRes.getString(IMPRESORA).equals("SI")){

                                                    if (parent.jsonRes.has(FOLIO_TICKET)){
                                                        ibImprimir.setVisibility(View.VISIBLE);
                                                        if (!parent.flagRespuesta)
                                                            ibImprimir.setVisibility(View.GONE);
                                                        etFolioRecibo.setEnabled(false);
                                                        llFolioRecibo.setVisibility(View.VISIBLE);
                                                        etFolioRecibo.setText(parent.jsonRes.getString(FOLIO_TICKET));
                                                        etFolioRecibo.setError(null);
                                                        folio_impreso = parent.jsonRes.getString(FOLIO_TICKET);
                                                    }
                                                    else {
                                                        ibImprimir.setVisibility(View.VISIBLE);
                                                    }
                                                }
                                                else{
                                                    ibImprimir.setVisibility(View.GONE);
                                                    llFolioRecibo.setVisibility(View.VISIBLE);
                                                    etFolioRecibo.setText(parent.jsonRes.getString(FOLIO_TICKET));
                                                    etFolioRecibo.setError(null);
                                                }
                                            }
                                        }

                                        if (parent.jsonRes.has(EVIDENCIA)){
                                            Log.v("PATH_EVIDENCIA", ROOT_PATH + "Evidencia/"+parent.jsonRes.getString(EVIDENCIA));
                                            File evidenciaFile = new File(ROOT_PATH + "Evidencia/"+parent.jsonRes.getString(EVIDENCIA));
                                            Uri uriEvidencia = Uri.fromFile(evidenciaFile);
                                            Glide.with(ctx).load(uriEvidencia).centerCrop().into(ivEvidencia);
                                            ibFoto.setVisibility(View.GONE);
                                            ibGaleria.setVisibility(View.GONE);
                                            ivEvidencia.setVisibility(View.VISIBLE);
                                            byteEvidencia = Miscellaneous.getBytesUri(ctx, uriEvidencia, 1);
                                            tvFotoGaleria.setError(null);
                                        }

                                        if (parent.jsonRes.has(GERENTE)){
                                            tvGerente.setText(parent.jsonRes.getString(GERENTE));

                                            if (!parent.flagRespuesta){
                                                tvGerente.setEnabled(false);
                                            }
                                            SelectEstaGerente(Miscellaneous.Gerente(tvGerente));
                                            if (parent.jsonRes.getString(GERENTE).equals("SI")){
                                                if (parent.jsonRes.has(FIRMA)){
                                                    Log.v("PATH_FIRMA", ROOT_PATH + "Firma/"+parent.jsonRes.getString(FIRMA));
                                                    File firmaFile = new File(ROOT_PATH + "Firma/"+parent.jsonRes.getString(FIRMA));
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
                                    break;
                            }
                        }

                        break;
                    case 2:
                        SelectContactoCliente(Miscellaneous.ContactoCliente(tvContacto));
                        if (parent.jsonRes.has(MOTIVO_ACLARACION)){
                            tvMotivoAclaracion.setText(parent.jsonRes.getString(MOTIVO_ACLARACION));
                            tvMotivoAclaracion.setVisibility(View.VISIBLE);
                            tvMotivoAclaracion.setError(null);
                            if (!parent.flagRespuesta)
                                tvMotivoAclaracion.setEnabled(false);
                        }
                        if (parent.jsonRes.has(COMENTARIO)){
                            etComentario.setText(parent.jsonRes.getString(COMENTARIO));
                            etComentario.setVisibility(View.VISIBLE);
                            etComentario.setError(null);
                            if (!parent.flagRespuesta)
                                etComentario.setEnabled(false);
                        }

                        tvGerente.setVisibility(View.VISIBLE);
                        if (parent.jsonRes.has(GERENTE)){
                            tvGerente.setText(parent.jsonRes.getString(GERENTE));
                            if (!parent.flagRespuesta){
                                tvGerente.setEnabled(false);
                            }
                            SelectEstaGerente(Miscellaneous.Gerente(tvGerente));
                            if (parent.jsonRes.getString(GERENTE).equals("SI")){
                                if (parent.jsonRes.has(FIRMA)){
                                    File firmaFile = new File(ROOT_PATH + "Firma/"+parent.jsonRes.getString(FIRMA));
                                    Uri uriFirma = Uri.fromFile(firmaFile);
                                    Glide.with(ctx).load(uriFirma).into(ivFirma);
                                    ibFirma.setVisibility(View.GONE);
                                    ivFirma.setVisibility(View.VISIBLE);
                                    byteFirma = Miscellaneous.getBytesUri(ctx, uriFirma, 1);
                                    tvFirma.setError(null);
                                }
                            }
                        }

                        break;
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
            case REQUEST_CODE_CAMARA_FACHADA:
                if (resultCode == Activity.RESULT_OK){
                    if (data != null){
                        ibFachada.setVisibility(View.GONE);
                        tvFachada.setError(null);
                        ivFachada.setVisibility(View.VISIBLE);
                        byteEvidencia = data.getByteArrayExtra(PICTURE);
                        Glide.with(ctx).load(byteEvidencia).centerCrop().into(ivFachada);

                        /*try {
                            jsonResponse.put(TIPO_IMAGEN,  "FACHADA");
                            jsonResponse.put(FACHADA,  Miscellaneous.save(byteEvidencia, 1));
                            UpdateTemporal(jsonResponse);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }*/
                    }
                }
                break;
            case REQUEST_CODE_IMPRESORA:
                if (resultCode == Activity.RESULT_OK){
                    //if (data != null){
                    if (false){
                        Toast.makeText(ctx, data.getStringExtra(MESSAGE), Toast.LENGTH_SHORT).show();
                        if(data.getIntExtra(RES_PRINT,0) == 1 || data.getIntExtra(RES_PRINT,0) == 2){
                            folio_impreso = "Asesor951-" + String.valueOf(data.getIntExtra(FOLIO,0));
                            etFolioRecibo.setText(folio_impreso);
                            tvImprimirRecibo.setError(null);
                            llFolioRecibo.setVisibility(View.VISIBLE);
                        }
                    }
                    else{
                        ibImprimir.setVisibility(View.GONE);
                        folio_impreso = "Asesor951-" +1;
                        etFolioRecibo.setText(folio_impreso);
                        tvImprimirRecibo.setError(null);
                        llFolioRecibo.setVisibility(View.VISIBLE);
                    }
                }
                break;
            case REQUEST_CODE_GALERIA:
                if (data != null){
                    ibFoto.setVisibility(View.GONE);
                    ibGaleria.setVisibility(View.GONE);
                    tvFotoGaleria.setError(null);
                    imageUri = data.getData();
                    ivEvidencia.setVisibility(View.VISIBLE);

                    byteEvidencia = Miscellaneous.getBytesUri(ctx, imageUri, 0);

                    View vCanvas = new CanvasCustom(ctx,new SimpleDateFormat(FORMAT_TIMESTAMP).format(Calendar.getInstance().getTime()));

                    Bitmap newBitMap = null;
                    Bitmap bitmap = BitmapFactory.decodeByteArray(byteEvidencia, 0, byteEvidencia.length);

                    Bitmap.Config config = bitmap.getConfig();

                    newBitMap = Bitmap.createBitmap(bitmap.getWidth(),bitmap.getHeight(),config);
                    Canvas canvas = new Canvas(newBitMap);
                    canvas.drawBitmap(bitmap,0,0, null);

                    vCanvas.draw(canvas);

                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    newBitMap.compress(Bitmap.CompressFormat.JPEG, 100, baos);

                    byteEvidencia = baos.toByteArray();

                    Glide.with(ctx).load(baos.toByteArray()).centerCrop().into(ivEvidencia);
                }
                break;
            case REQUEST_CODE_CAMARA_TICKET:
                if (resultCode == Activity.RESULT_OK){
                    if (data != null){
                        ibFoto.setVisibility(View.GONE);
                        ibGaleria.setVisibility(View.GONE);
                        tvFotoGaleria.setError(null);
                        ivEvidencia.setVisibility(View.VISIBLE);
                        byteEvidencia = data.getByteArrayExtra(PICTURE);
                        Glide.with(ctx).load(byteEvidencia).centerCrop().into(ivEvidencia);

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
