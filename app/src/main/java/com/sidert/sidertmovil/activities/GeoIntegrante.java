package com.sidert.sidertmovil.activities;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.ColorDrawable;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
/*import android.support.v4.app.ActivityCompat;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;*/
import android.os.Bundle;
//import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

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
import com.sidert.sidertmovil.database.DBhelper;
import com.sidert.sidertmovil.fragments.geo_tesorera_fragment;
import com.sidert.sidertmovil.models.ModeloCatalogoGral;
import com.sidert.sidertmovil.utils.CanvasCustom;
import com.sidert.sidertmovil.utils.Constants;
import com.sidert.sidertmovil.utils.Miscellaneous;
import com.sidert.sidertmovil.utils.MyCurrentListener;
import com.sidert.sidertmovil.utils.NetworkStatus;
import com.sidert.sidertmovil.utils.Popups;
import com.sidert.sidertmovil.utils.Servicios_Sincronizado;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Objects;

import static com.sidert.sidertmovil.utils.Constants.CARTERAEN;
import static com.sidert.sidertmovil.utils.Constants.CATALOGO;
import static com.sidert.sidertmovil.utils.Constants.FORMAT_TIMESTAMP;
import static com.sidert.sidertmovil.utils.Constants.ID_INTEGRANTE;
import static com.sidert.sidertmovil.utils.Constants.ITEM;
import static com.sidert.sidertmovil.utils.Constants.NUM_SOLICITUD;
import static com.sidert.sidertmovil.utils.Constants.REQUEST_CODE;
import static com.sidert.sidertmovil.utils.Constants.REQUEST_CODE_CARTERAEN;
import static com.sidert.sidertmovil.utils.Constants.TBL_GEO_RESPUESTAS_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_MIEMBROS_GPO_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_PRESTAMOS_GPO_T;
import static com.sidert.sidertmovil.utils.Constants.TIMESTAMP;
import static com.sidert.sidertmovil.utils.Constants.TITULO;

/**
 * Clase para realizar las geolocalizaciones de integrantes
 */
public class GeoIntegrante extends AppCompatActivity {

    private Toolbar tbMain;

    private Context ctx;

    private DBhelper dBhelper;
    private SQLiteDatabase db;
    private TextView tvMapa;
    private TextView tvDireccionCap;
    private EditText etNombre;
    public EditText etCodigoBarras;
    private EditText etDireccion;
    public EditText etComentario;
    private EditText etFechaFinalizacion;
    private EditText etFechaEnvio;
    private EditText etDireccionCap;
    private TextView txtCarteraEnIntegrante;
    private ImageButton ibUbicacion;
    private ImageButton ibCodigoBarras;
    private ImageButton ibFotoFachada;
    private ImageButton ibGaleriaFachada;
    private ImageView ivFotoFachada;
    private MapView mapUbicacion;
    private LinearLayout llFechaFinalizacion;
    private LinearLayout llFechaEnvio;

    private LocationManager locationManager;
    private MyCurrentListener locationListener;

    private boolean flagUbicacion = false;
    private ProgressBar pbLoading;
    private GoogleMap mMap;
    private Marker mMarker;
    public LatLng latLngUbicacion;

    private Button btnGuardar;

    public byte[] byteFotoFachada;

    public boolean flag_edit = true;
    public boolean isUbicacion = false;

    private String idCartera = "0";
    private String idIntegrante = "0";
    private String numSolicitud;

    private String cliente_clv = "0";
    private String direccion = "No se encontró la dirección";

    private boolean isSave = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_geo_integrante);

        ctx = this;
        dBhelper = DBhelper.getInstance(ctx);
        db = dBhelper.getWritableDatabase();

        tbMain = findViewById(R.id.tbMain);

        tvMapa = findViewById(R.id.tvMapa);
        tvDireccionCap = findViewById(R.id.tvDireccionCap);

        etNombre = findViewById(R.id.etNombre);
        etCodigoBarras = findViewById(R.id.etCodigoBarras);

        etDireccion = findViewById(R.id.etDireccion);
        etComentario = findViewById(R.id.etComentario);
        etDireccionCap = findViewById(R.id.etDireccionCap);

        etFechaFinalizacion = findViewById(R.id.etFechaFinalizacion);
        etFechaEnvio = findViewById(R.id.etFechaEnvio);

        txtCarteraEnIntegrante = findViewById(R.id.txtCarteraEnIntegrante);

        ibUbicacion = findViewById(R.id.ibUbicacion);
        ibCodigoBarras = findViewById(R.id.ibCodigoBarras);
        ibFotoFachada = findViewById(R.id.ibFotoFachada);
        ibGaleriaFachada = findViewById(R.id.ibGaleriaFachada);


        ivFotoFachada = findViewById(R.id.ivFotoFachada);

        pbLoading = findViewById(R.id.pbLoading);

        mapUbicacion = findViewById(R.id.mapUbicacion);

        llFechaFinalizacion = findViewById(R.id.llFechaFinalizacion);
        llFechaEnvio = findViewById(R.id.llFechaEnvio);

        btnGuardar = findViewById(R.id.btnGuardar);

        mapUbicacion.onCreate(savedInstanceState);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        /**Obtiene los datos que se pasaron entre clases*/
        numSolicitud = getIntent().getStringExtra(NUM_SOLICITUD);
        idIntegrante = getIntent().getStringExtra(ID_INTEGRANTE);

        ibGaleriaFachada.setEnabled(false);

        /**Funcion para saber si existe alguna informacion de geolocalizacion*/
        initComponents();

        setSupportActionBar(tbMain);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("Geolocalización");

        /**EVento de click para Ubicacion GPS, Codigos Barras, Fotografia, Guardar informacion*/
        ibUbicacion.setOnClickListener(ibUbicacion_OnClick);
        ibCodigoBarras.setOnClickListener(ibCodigoBarras_OnClick);
        txtCarteraEnIntegrante.setOnClickListener(txtCarteraEnIntegrante_OnClick);
        ibFotoFachada.setOnClickListener(ibFotoFachada_OnClick);
        ibGaleriaFachada.setOnClickListener(ibGaleriaFachada_OnClick);
        ivFotoFachada.setOnClickListener(ivFotoFachada_OnClick);
        btnGuardar.setOnClickListener(btnGuardar_OnClick);


        /**Evento del pin en el mapa ya sea para volver a capturar la ubicacion o
         * abrir la ubicacion con google maps en caso de que ya este guardada la geolocalizacion*/
        mapUbicacion.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(LatLng latLng) {
                        if (flag_edit) {
                            final AlertDialog ubicacion_dlg = Popups.showDialogConfirm(ctx, Constants.question,
                                    R.string.capturar_nueva_ubicacion, R.string.ubicacion, new Popups.DialogMessage() {
                                        @Override
                                        public void OnClickListener(AlertDialog dialog) {
                                            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                                            if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
                                                Toast.makeText(ctx, "El GPS se encuentra desactivado", Toast.LENGTH_SHORT).show();
                                            else
                                                ObtenerUbicacion();
                                            dialog.dismiss();

                                        }
                                    }, R.string.cancel, new Popups.DialogMessage() {
                                        @Override
                                        public void OnClickListener(AlertDialog dialog) {
                                            dialog.dismiss();
                                        }
                                    });
                            Objects.requireNonNull(ubicacion_dlg.getWindow()).requestFeature(Window.FEATURE_NO_TITLE);
                            ubicacion_dlg.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                            ubicacion_dlg.show();
                        } else {
                            final AlertDialog ubicacion_dlg = Popups.showDialogConfirm(ctx, Constants.question,
                                    R.string.ver_en_maps, R.string.ver_maps, new Popups.DialogMessage() {
                                        @Override
                                        public void OnClickListener(AlertDialog dialog) {
                                            Intent i_maps = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse("geo:" + latLngUbicacion.latitude + "," + latLngUbicacion.longitude + "?z=16&q=" + latLngUbicacion.latitude + "," + latLngUbicacion.longitude + "()"));
                                            i_maps.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
                                            startActivity(i_maps);
                                            dialog.dismiss();

                                        }
                                    }, R.string.cancel, new Popups.DialogMessage() {
                                        @Override
                                        public void OnClickListener(AlertDialog dialog) {
                                            dialog.dismiss();
                                        }
                                    });
                            Objects.requireNonNull(ubicacion_dlg.getWindow()).requestFeature(Window.FEATURE_NO_TITLE);
                            ubicacion_dlg.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                            ubicacion_dlg.show();

                        }
                    }
                });
            }
        });
    }

    /**
     * Evento para obtener la ubicacion del GPS
     */
    private View.OnClickListener ibUbicacion_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            /**Valida si el GPS se encuentra activo*/
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
                Toast.makeText(ctx, "El GPS se encuentra desactivado", Toast.LENGTH_SHORT).show();
            else
                ObtenerUbicacion();
        }
    };

    /**
     * Evento de lector de codigo de barras
     */
    private View.OnClickListener ibCodigoBarras_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i_codigo_barras = new Intent(ctx, LectorCodigoBarras.class);
            startActivityForResult(i_codigo_barras, Constants.REQUEST_CODE_CODEBARS);
        }
    };

    private View.OnClickListener txtCarteraEnIntegrante_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent i_carteraEn = new Intent(ctx, Catalogos.class);
            i_carteraEn.putExtra(TITULO, Miscellaneous.ucFirst(CARTERAEN));
            i_carteraEn.putExtra(CATALOGO, CARTERAEN);
            i_carteraEn.putExtra(REQUEST_CODE, REQUEST_CODE_CARTERAEN);
            startActivityForResult(i_carteraEn, REQUEST_CODE_CARTERAEN);
        }
    };

    /**
     * Evento de fotografia para evidencia
     */
    private View.OnClickListener ibFotoFachada_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i = new Intent(ctx, CameraVertical.class);
            i.putExtra(Constants.ORDER_ID, "_presidente");
            startActivityForResult(i, Constants.REQUEST_CODE_CAMARA_FACHADA);
        }
    };

    private final View.OnClickListener ibGaleriaFachada_OnClick = ignored -> {
        String model = Build.MANUFACTURER;
        int compress = 10;

        if (model != null && model.equalsIgnoreCase("SAMSUNG")) {
            compress = 40;
        }

        CropImage.activity()
                .setAutoZoomEnabled(true)
                .setMinCropWindowSize(3000, 4000)
                .setOutputCompressQuality(compress)
                .start(this);
    };

    /**
     * En caso de que ya fue capturada la fotografia, puede volver a capturar una nueva foto o poder visualizarla
     */
    private View.OnClickListener ivFotoFachada_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (flag_edit) {
                final AlertDialog evidencia_dlg = Popups.showDialogConfirmImage(ctx, Constants.question,
                        R.string.capturar_foto_galeria, R.string.fotografia, new Popups.DialogMessage() {
                            @Override
                            public void OnClickListener(AlertDialog dialog) {
                                Intent i = new Intent(ctx, CameraVertical.class);
                                i.putExtra(Constants.ORDER_ID, "_presidente");
                                startActivityForResult(i, Constants.REQUEST_CODE_CAMARA_FACHADA);
                                dialog.dismiss();

                            }
                        },
                        R.string.galeria, new Popups.DialogMessage() {
                            @Override
                            public void OnClickListener(AlertDialog dialog) {
                                int compress = 10;
                                if (Build.MANUFACTURER.toUpperCase().equals("SAMSUNG"))
                                    compress = 40;
                                CropImage.activity()
                                        .setAutoZoomEnabled(true)
                                        .setMinCropWindowSize(3000, 4000)
                                        .setOutputCompressQuality(compress)
                                        .start(GeoIntegrante.this);
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
                final AlertDialog fachada_dlg = Popups.showDialogConfirm(ctx, Constants.question,
                        R.string.ver_fotografia, R.string.ver_imagen, new Popups.DialogMessage() {
                            @Override
                            public void OnClickListener(AlertDialog dialog) {
                                Intent i = new Intent(ctx, VerImagen.class);
                                i.putExtra(Constants.IMAGEN, byteFotoFachada);
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
     * Evento para validar los campos para poder guardar
     */
    private View.OnClickListener btnGuardar_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ValidarInformacion();
        }
    };

    //===================== Listener GPS  =======================================================
    private void ObtenerUbicacion() {
        pbLoading.setVisibility(View.VISIBLE);
        ibUbicacion.setEnabled(false);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        final Handler myHandler = new Handler();
        locationListener = new MyCurrentListener(new MyCurrentListener.evento() {
            @Override
            public void onComplete(String latitud, String longitud) {

                ibUbicacion.setEnabled(true);
                if (!latitud.isEmpty() && !longitud.isEmpty()) {
                    mapUbicacion.setVisibility(View.VISIBLE);
                    ColocarUbicacionGestion(Double.parseDouble(latitud), Double.parseDouble(longitud));
                } else {
                    pbLoading.setVisibility(View.GONE);
                    Toast.makeText(ctx, getResources().getString(R.string.no_ubicacion), Toast.LENGTH_SHORT).show();
                }
                myHandler.removeCallbacksAndMessages(null);

                flagUbicacion = true;
                if (flagUbicacion) {
                    CancelUbicacion();
                }
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
                pbLoading.setVisibility(View.GONE);
                ibUbicacion.setEnabled(true);
                etDireccionCap.setText("No se logró obtener la ubicación");
                tvDireccionCap.setText("No se logró obtener la ubicación");
                tvDireccionCap.setTextColor(getResources().getColor(R.color.red));
                tvDireccionCap.setVisibility(View.VISIBLE);
                isUbicacion = true;
                Toast.makeText(ctx, "No se logró obtener la ubicación", Toast.LENGTH_SHORT).show();
            }
        }, 60000);
    }

    private void ColocarUbicacionGestion(final double lat, final double lon) {
        mapUbicacion.onResume();
        try {
            MapsInitializer.initialize(ctx);
        } catch (Exception e) {
            e.printStackTrace();
        }

        mapUbicacion.getMapAsync(new OnMapReadyCallback() {
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

                addMarker(lat, lon);

            }
        });
    }

    private void addMarker(double lat, double lng) {
        if (flag_edit)
            etDireccionCap.setText(Miscellaneous.ObtenerDireccion(ctx, lat, lng));
        else
            etDireccionCap.setText(direccion);
        etDireccionCap.setVisibility(View.VISIBLE);
        tvDireccionCap.setVisibility(View.VISIBLE);
        tvDireccionCap.setTextColor(getResources().getColor(R.color.black));
        LatLng coordenadas = new LatLng(lat, lng);
        latLngUbicacion = coordenadas;
        CameraUpdate ubication = CameraUpdateFactory.newLatLngZoom(coordenadas, 17);

        mMap.clear();
        mMap.addMarker(new MarkerOptions()
                .position(coordenadas)
                .title(""));

        mMap.animateCamera(ubication);

        pbLoading.setVisibility(View.GONE);
        ibUbicacion.setVisibility(View.GONE);
    }

    private void CancelUbicacion() {
        if (flagUbicacion)
            locationManager.removeUpdates(locationListener);
    }

    //====================== For activity Result  ==============================================
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case Constants.REQUEST_CODE_CODEBARS:
                if (resultCode == RESULT_OK) {
                    if (data != null) {
                        etCodigoBarras.setVisibility(View.VISIBLE);
                        etCodigoBarras.setText(data.getStringExtra(Constants.CODEBARS));
                    }
                }
                break;


            case REQUEST_CODE_CARTERAEN:
                if (resultCode == REQUEST_CODE_CARTERAEN) {
                    if (data != null) {
                        Integer id_carteraEn = 0;
                        ContentValues cv = new ContentValues();
                        txtCarteraEnIntegrante.setError(null);
                        txtCarteraEnIntegrante.setText(((ModeloCatalogoGral) data.getSerializableExtra(ITEM)).getNombre());
                        id_carteraEn = Miscellaneous.selectCampana(ctx, Miscellaneous.GetStr(txtCarteraEnIntegrante));

                    }
                } else {
                    txtCarteraEnIntegrante.setText(" ");
                    Toast.makeText(ctx, "ESTE CAMPO ES REQUERIDO", Toast.LENGTH_SHORT).show();
                }
                break;

            case Constants.REQUEST_CODE_CAMARA_FACHADA:
                if (resultCode == Activity.RESULT_OK) {
                    if (data != null) {
                        ibFotoFachada.setVisibility(View.GONE);
                        ibGaleriaFachada.setVisibility(View.GONE);
                        ivFotoFachada.setVisibility(View.VISIBLE);
                        byteFotoFachada = data.getByteArrayExtra(Constants.PICTURE);
                        Glide.with(ctx).load(byteFotoFachada).centerCrop().into(ivFotoFachada);
                    }
                }
                break;
            case CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE:/**Recibe el archivo a adjuntar*/
                if (data != null) {/**Valida que se esté recibiendo el archivo*/
                    try {
                        Log.e("AQUI", "CROP IMAGE");
                        CropImage.ActivityResult result = CropImage.getActivityResult(data);
                        Uri imageUri = result.getUri();

                        /**Convierte la iamgen adjuntada a un array de byte*/
                        byteFotoFachada = Miscellaneous.getBytesUri(ctx, imageUri, 0);

                        ibFotoFachada.setVisibility(View.GONE);
                        ivFotoFachada.setVisibility(View.VISIBLE);
                        ibGaleriaFachada.setVisibility(View.GONE);

                        /**Al array de byte (imagen adjuntada) se le agrega la fecha y hora de cuando fue adjuntad para crear una nueva imagen con fecha y hora*/
                        View vCanvas = new CanvasCustom(ctx, new SimpleDateFormat(FORMAT_TIMESTAMP).format(Calendar.getInstance().getTime()));

                        Bitmap newBitMap = null;

                        Bitmap bitmap = BitmapFactory.decodeByteArray(byteFotoFachada, 0, byteFotoFachada.length);

                        Bitmap.Config config = bitmap.getConfig();

                        newBitMap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), config);
                        /**Aqui le coloca la fecha y hora a la imagen*/
                        Canvas canvas = new Canvas(newBitMap);
                        canvas.drawBitmap(bitmap, 0, 0, null);

                        vCanvas.draw(canvas);

                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        /**Se comprime la imagen para que no este tan pesada */
                        newBitMap.compress(Bitmap.CompressFormat.JPEG, 50, baos);

                        /**Se extraen la nueva imagen en array de byte para guardar posteriormente*/
                        byteFotoFachada = baos.toByteArray();
                        /**Coloca la imagen en el contededor del imageView*/
                        Glide.with(ctx).load(baos.toByteArray()).centerCrop().into(ivFotoFachada);

                    } catch (Exception e) {
                        /**En caso de que haya adjuntado un archivo  con diferente formato al JPEG*/
                        AlertDialog success = Popups.showDialogMessage(ctx, "", R.string.error_image, R.string.accept, dialog -> dialog.dismiss());
                        success.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                        success.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                        success.show();
                    }

                }
                break;
        }
    }

    private void SendMessError(String mess) {
        Toast.makeText(ctx, mess, Toast.LENGTH_SHORT).show();
    }

    public void GuardarGeo() {
        try {
            HashMap<Integer, String> params = new HashMap<>();
            params.put(0, idCartera);
            params.put(1, numSolicitud);
            params.put(2, "2");
            params.put(3, "INTEGRANTE");
            params.put(4, idIntegrante);
            params.put(5, etNombre.getText().toString().trim().toUpperCase());
            params.put(6, etDireccion.getText().toString().trim().toUpperCase());
            params.put(7, ((isUbicacion) ? "0" : String.valueOf(latLngUbicacion.latitude)));
            params.put(8, ((isUbicacion) ? "0" : String.valueOf(latLngUbicacion.longitude)));
            params.put(9, etDireccionCap.getText().toString().trim().toUpperCase());
            params.put(10, etCodigoBarras.getText().toString().trim());
            params.put(11, Miscellaneous.save(byteFotoFachada, 1));
            params.put(12, etComentario.getText().toString().trim().toUpperCase());
            params.put(13, Miscellaneous.ObtenerFecha(TIMESTAMP));
            params.put(14, "");
            params.put(15, "0");
            params.put(16, cliente_clv);
            params.put(17, String.valueOf(Miscellaneous.selectCarteraEn(ctx, Miscellaneous.GetStr(txtCarteraEnIntegrante))));

            if (isSave)
                dBhelper.saveGeoRespuestas(db, params);
            else
                Toast.makeText(ctx, "No hay Integrante registrado, no se puede guardar la geolocalización", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Log.e("Error", e.getMessage() + " ....");
            e.printStackTrace();
        }

        Servicios_Sincronizado servicios = new Servicios_Sincronizado();
        servicios.SaveGeolocalizacion(ctx, false);
        initComponents();


    }

    private void initComponents() {
        Cursor row;

        String sql = "SELECT p.id_grupo, m.id_integrante, COALESCE(m.nombre, ''), COALESCE(m.direccion, ''), m.clave FROM " + TBL_MIEMBROS_GPO_T + " AS m LEFT JOIN " + TBL_PRESTAMOS_GPO_T + " AS p ON p.id_prestamo = m.id_prestamo WHERE m.tipo_integrante = 'INTEGRANTE' AND m.id_integrante = ? AND p.num_solicitud = ?";

        row = db.rawQuery(sql, new String[]{idIntegrante, numSolicitud});

        if (row.getCount() > 0) {
            row.moveToFirst();
            isSave = true;
            idCartera = row.getString(0);
            etNombre.setText(row.getString(2));
            etDireccion.setText(row.getString(3));
            cliente_clv = row.getString(4);
        }
        row.close();

        sql = "SELECT * FROM " + TBL_GEO_RESPUESTAS_T + " WHERE id_cartera = ? AND id_integrante = ? AND tipo_ficha = 2 AND tipo_geolocalizacion = 'INTEGRANTE'";

        row = db.rawQuery(sql, new String[]{idCartera, idIntegrante});
        if (row.getCount() > 0) {
            flag_edit = false;
            row.moveToFirst();
            direccion = row.getString(10);

            if (row.getDouble(8) == 0 && row.getDouble(9) == 0) {
                ibUbicacion.setVisibility(View.GONE);
                tvMapa.setVisibility(View.GONE);
                tvDireccionCap.setTextColor(getResources().getColor(R.color.black));
                tvDireccionCap.setText(getResources().getString(R.string.direccion));
                tvDireccionCap.setVisibility(View.VISIBLE);
                etDireccionCap.setText(direccion);
                etDireccionCap.setVisibility(View.VISIBLE);
            } else {
                mapUbicacion.setVisibility(View.VISIBLE);
                ColocarUbicacionGestion(row.getDouble(8), row.getDouble(9));
            }

            ibCodigoBarras.setVisibility(View.GONE);
            etCodigoBarras.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
            etCodigoBarras.setVisibility(View.VISIBLE);
            etCodigoBarras.setText(row.getString(11));
            ibFotoFachada.setVisibility(View.GONE);
            ibGaleriaFachada.setVisibility(View.GONE);
            File fachadaFile = new File(Constants.ROOT_PATH + "Fachada/" + row.getString(12));
            Uri uriFachada = Uri.fromFile(fachadaFile);
            byteFotoFachada = Miscellaneous.getBytesUri(ctx, uriFachada, 0);
            Glide.with(ctx).load(uriFachada).into(ivFotoFachada);
            ivFotoFachada.setVisibility(View.VISIBLE);
            etComentario.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
            etComentario.setText(row.getString(13));
            etComentario.setEnabled(false);
            etFechaFinalizacion.setText(row.getString(14));
            etFechaEnvio.setText((!row.getString(15).isEmpty()) ? row.getString(15) : "Pendiente por enviar");
            llFechaFinalizacion.setVisibility(View.VISIBLE);
            llFechaEnvio.setVisibility(View.VISIBLE);
            btnGuardar.setVisibility(View.GONE);
            txtCarteraEnIntegrante.setText(Miscellaneous.tipoEntregaCartera(ctx, row.getString(18)));
            txtCarteraEnIntegrante.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
            txtCarteraEnIntegrante.setEnabled(false);

        }

    }

    public void ValidarInformacion() {
        /**Valida si se logro obtener la ubicacion o por lo menos se intento obtenerla*/
        if (latLngUbicacion != null || isUbicacion) {
            /**Valida si ya tomo la fotografia*/
            if (byteFotoFachada != null) {
                /**Si agrego el comentario*/
                if (!etComentario.getText().toString().trim().isEmpty()) {
                    /**Mensaje de confirmacion para reactificar los datos y poder guardar*/
                    final AlertDialog guardar_dlg = Popups.showDialogConfirm(ctx, Constants.question,
                            R.string.guardar_geo, R.string.save, new Popups.DialogMessage() {
                                @Override
                                public void OnClickListener(AlertDialog dialog) {
                                    GuardarGeo();
                                    dialog.dismiss();
                                }
                            }, R.string.cancel, new Popups.DialogMessage() {
                                @Override
                                public void OnClickListener(AlertDialog dialog) {
                                    dialog.dismiss();
                                }
                            });
                    Objects.requireNonNull(guardar_dlg.getWindow()).requestFeature(Window.FEATURE_NO_TITLE);
                    guardar_dlg.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                    guardar_dlg.show();
                } else
                    SendMessError("Falta capturar el comentario");
            } else
                SendMessError("Falta Capturar la foto de fachada.");
        } else
            SendMessError("Falta obtener la ubicación actual.");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:/**Menu de retroceso del toolbar <- */
                setResult(RESULT_OK); /**Se retorna un valor a la vista anterior*/
                finish();/**Cierra la vista actual*/
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Funcion de Back del boton de retroceso del dispositivo
     */
    @Override
    public void onBackPressed() {
        setResult(RESULT_OK); /**Se retorna un valor a la vista anterior*/
        super.onBackPressed();/**Cierra la vista actual*/
    }
}
