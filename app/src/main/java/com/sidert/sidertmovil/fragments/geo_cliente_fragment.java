package com.sidert.sidertmovil.fragments;


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
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SimpleCursorAdapter;
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
import com.sidert.sidertmovil.activities.Catalogos;
import com.sidert.sidertmovil.activities.GeolocalizacionInd;
import com.sidert.sidertmovil.activities.LectorCodigoBarras;
import com.sidert.sidertmovil.activities.VerImagen;
import com.sidert.sidertmovil.database.DBhelper;
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

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import static com.sidert.sidertmovil.utils.Constants.CARTERAEN;
import static com.sidert.sidertmovil.utils.Constants.CATALOGO;
import static com.sidert.sidertmovil.utils.Constants.FORMAT_TIMESTAMP;
import static com.sidert.sidertmovil.utils.Constants.ITEM;
import static com.sidert.sidertmovil.utils.Constants.REQUEST_CODE;
import static com.sidert.sidertmovil.utils.Constants.REQUEST_CODE_CARTERAEN;
import static com.sidert.sidertmovil.utils.Constants.TBL_CARTERA_IND_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_GEO_RESPUESTAS_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_PRESTAMOS_IND_T;
import static com.sidert.sidertmovil.utils.Constants.TIMESTAMP;
import static com.sidert.sidertmovil.utils.Constants.TITULO;

public class geo_cliente_fragment extends Fragment {

    private Context ctx;
    private GeolocalizacionInd boostrap;
    private TextView tvMapa;
    private TextView tvDireccionCap;
    private EditText etNombre;
    public EditText etCodigoBarras;
    private EditText etDireccion;
    public EditText etComentario;
    private EditText etDireccionCap;
    private EditText etFechaFinalizacion;
    private EditText etFechaEnvio;
    private ImageButton ibUbicacion;
    private ImageButton ibCodigoBarras;
    private ImageButton ibFotoFachada;
    private ImageButton ibGaleriaFachada;
    private ImageView ivFotoFachada;
    private MapView mapUbicacion;

    private TextView txtCarteraEnCliente;
    SimpleCursorAdapter carteraEnSpinner;

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

    private SQLiteDatabase db;
    private DBhelper dBhelper;

    public boolean flag_edit = true;
    public boolean isUbicacion = false;

    //private String ficha_id;
    private String id_cartera;
    private int status;
    private String direccion = "No se encontró la dirección";
    private boolean isSave = false;
    private int position;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_geo_cliente, container, false);
        ctx = getContext();
        boostrap = (GeolocalizacionInd) getActivity();

        dBhelper = DBhelper.getInstance(ctx);
        db = dBhelper.getWritableDatabase();

        tvMapa = view.findViewById(R.id.tvMapa);
        tvDireccionCap = view.findViewById(R.id.tvDireccionCap);

        etNombre = view.findViewById(R.id.etNombre);
        etCodigoBarras = view.findViewById(R.id.etCodigoBarras);

        etDireccion = view.findViewById(R.id.etDireccion);
        etComentario = view.findViewById(R.id.etComentario);
        etDireccionCap = view.findViewById(R.id.etDireccionCap);

        etFechaFinalizacion = view.findViewById(R.id.etFechaFinalizacion);
        etFechaEnvio = view.findViewById(R.id.etFechaEnvio);

        ibUbicacion = view.findViewById(R.id.ibUbicacion);
        ibCodigoBarras = view.findViewById(R.id.ibCodigoBarras);
        ibFotoFachada = view.findViewById(R.id.ibFotoFachada);
        ibGaleriaFachada = view.findViewById(R.id.ibGaleriaFachada);

        ivFotoFachada = view.findViewById(R.id.ivFotoFachada);

        pbLoading = view.findViewById(R.id.pbLoading);

        mapUbicacion = view.findViewById(R.id.mapUbicacion);

        txtCarteraEnCliente = view.findViewById(R.id.txtCarteraEnCliente);

        btnGuardar = view.findViewById(R.id.btnGuardar);

        llFechaFinalizacion = view.findViewById(R.id.llFechaFinalizacion);
        llFechaEnvio = view.findViewById(R.id.llFechaEnvio);

        mapUbicacion.onCreate(savedInstanceState);
        locationManager = (LocationManager) boostrap.getSystemService(Context.LOCATION_SERVICE);

        ibGaleriaFachada.setEnabled(false);

        initComponents();


        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ibUbicacion.setOnClickListener(ibUbicacion_OnClick);
        txtCarteraEnCliente.setOnClickListener(txtCarteraEnCliente_OnClick);
        ibCodigoBarras.setOnClickListener(ibCodigoBarras_OnClick);
        ibFotoFachada.setOnClickListener(ibFotoFachada_OnClick);
        ibGaleriaFachada.setOnClickListener(ibGaleriaFachada_OnClick);
        ivFotoFachada.setOnClickListener(ivFotoFachada_OnClick);
        btnGuardar.setOnClickListener(btnGuardar_OnClick);

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
                                            locationManager = (LocationManager) boostrap.getSystemService(Context.LOCATION_SERVICE);
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

    private View.OnClickListener ibUbicacion_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            locationManager = (LocationManager) boostrap.getSystemService(Context.LOCATION_SERVICE);
            if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
                Toast.makeText(ctx, "El GPS se encuentra desactivado", Toast.LENGTH_SHORT).show();
            else
                ObtenerUbicacion();
        }
    };

    private View.OnClickListener ibCodigoBarras_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i_codigo_barras = new Intent(boostrap, LectorCodigoBarras.class);
            startActivityForResult(i_codigo_barras, Constants.REQUEST_CODE_CODEBARS);
        }
    };


    private View.OnClickListener txtCarteraEnCliente_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent i_carteraEn = new Intent(ctx, Catalogos.class);
            i_carteraEn.putExtra(TITULO, Miscellaneous.ucFirst(CARTERAEN));
            i_carteraEn.putExtra(CATALOGO, CARTERAEN);
            i_carteraEn.putExtra(REQUEST_CODE, REQUEST_CODE_CARTERAEN);
            startActivityForResult(i_carteraEn, REQUEST_CODE_CARTERAEN);
        }
    };


    private View.OnClickListener ibFotoFachada_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i = new Intent(boostrap, CameraVertical.class);
            i.putExtra(Constants.ORDER_ID, "_cliente");
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
                .start(ctx, this);
    };

    private View.OnClickListener ivFotoFachada_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (flag_edit) {
                final AlertDialog evidencia_dlg = Popups.showDialogConfirmImage(ctx, Constants.question,
                        R.string.capturar_foto_galeria, R.string.fotografia, new Popups.DialogMessage() {
                            @Override
                            public void OnClickListener(AlertDialog dialog) {
                                Intent i = new Intent(boostrap, CameraVertical.class);
                                i.putExtra(Constants.ORDER_ID, "_cliente");
                                startActivityForResult(i, Constants.REQUEST_CODE_CAMARA_FACHADA);
                                dialog.dismiss();

                            }
                        }, R.string.galeria, new Popups.DialogMessage() {
                            @Override
                            public void OnClickListener(AlertDialog dialog) {
                                int compress = 10;
                                if (Build.MANUFACTURER.toUpperCase().equals("SAMSUNG"))
                                    compress = 40;
                                CropImage.activity()
                                        .setAutoZoomEnabled(true)
                                        .setMinCropWindowSize(3000, 4000)
                                        .setOutputCompressQuality(compress)
                                        .start(ctx, geo_cliente_fragment.this);
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
                                Intent i = new Intent(boostrap, VerImagen.class);
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
        locationManager = (LocationManager) boostrap.getSystemService(Context.LOCATION_SERVICE);
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
            MapsInitializer.initialize(boostrap.getApplicationContext());
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
        //LatLng coordenada = new LatLng(19.201745,-96.162134);
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
                if (resultCode == boostrap.RESULT_OK) {
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
                        txtCarteraEnCliente.setError(null);
                        txtCarteraEnCliente.setText(((ModeloCatalogoGral) data.getSerializableExtra(ITEM)).getNombre());
                        id_carteraEn = Miscellaneous.selectCampana(ctx, Miscellaneous.GetStr(txtCarteraEnCliente));

                    }
                } else {
                    txtCarteraEnCliente.setText(" ");
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
            params.put(0, id_cartera);
            params.put(1, getArguments().getString(Constants.NUM_SOLICITUD));
            params.put(2, "1");
            params.put(3, "CLIENTE");
            params.put(4, "0");
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
            params.put(16, "0");
            params.put(17, String.valueOf(Miscellaneous.selectCarteraEn(ctx, Miscellaneous.GetStr(txtCarteraEnCliente))));

            if (isSave)
                dBhelper.saveGeoRespuestas(db, params);
            else
                Toast.makeText(ctx, "No hay Cliente registrado, no se puede guardar la geolocalización", Toast.LENGTH_SHORT).show();
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

        String sql = "SELECT c.id_cartera, COALESCE(c.nombre, ''), COALESCE(c.direccion, '') FROM " + TBL_CARTERA_IND_T + " AS c LEFT JOIN " + TBL_PRESTAMOS_IND_T + " AS p ON p.id_cliente = c.id_cartera WHERE c.num_solicitud = ?";

        row = db.rawQuery(sql, new String[]{getArguments().getString(Constants.NUM_SOLICITUD)});

        if (row.getCount() > 0) {
            row.moveToFirst();
            isSave = true;
            id_cartera = row.getString(0);
            etNombre.setText(row.getString(1));
            etDireccion.setText(row.getString(2));
        }
        row.close();

        sql = "SELECT * FROM " + TBL_GEO_RESPUESTAS_T + " WHERE id_cartera = ? AND tipo_ficha = 1 AND tipo_geolocalizacion = 'CLIENTE'";

        row = db.rawQuery(sql, new String[]{id_cartera});
        Log.e("RowGuardado", row.getCount() + " geoGuardado");
        if (row.getCount() > 0) {
            row.moveToFirst();
            flag_edit = false;
            direccion = row.getString(10);
            if (row.getDouble(8) == 0 && row.getDouble(9) == 0) {
                ibUbicacion.setVisibility(View.GONE);
                tvMapa.setVisibility(View.GONE);
                tvDireccionCap.setTextColor(getResources().getColor(R.color.black));
                tvDireccionCap.setText(getResources().getString(R.string.direccion));
                tvDireccionCap.setVisibility(View.VISIBLE);
                etDireccionCap.setText(row.getString(10));
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

            Log.e("Row12Cliente", row.getString(12) + "  ....");
            File fachadaFile = new File(Constants.ROOT_PATH + "Fachada/" + row.getString(12));
            Uri uriFachada = Uri.fromFile(fachadaFile);
            byteFotoFachada = Miscellaneous.getBytesUri(ctx, uriFachada, 0);
            Glide.with(ctx).load(uriFachada).into(ivFotoFachada);
            ivFotoFachada.setVisibility(View.VISIBLE);
            etComentario.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
            etComentario.setText(row.getString(13));
            etComentario.setEnabled(false);
            llFechaFinalizacion.setVisibility(View.VISIBLE);
            llFechaEnvio.setVisibility(View.VISIBLE);
            etFechaFinalizacion.setText(row.getString(14));
            Log.e("row15", row.getString(15) + " xxx");
            etFechaEnvio.setText((!row.getString(15).isEmpty()) ? row.getString(15) : "Pendiente por enviar");
            btnGuardar.setVisibility(View.GONE);
            txtCarteraEnCliente.setText(Miscellaneous.tipoEntregaCartera(ctx, row.getString(18)));
            txtCarteraEnCliente.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
            txtCarteraEnCliente.setEnabled(false);

        }

        row.close();

        row = dBhelper.getRecords(TBL_GEO_RESPUESTAS_T, " WHERE id_cartera = ?", "", new String[]{id_cartera});
        Log.e("Registros", "cantidad: " + row.getCount());

    }

    public void ValidarInformacion() {
        if (latLngUbicacion != null || isUbicacion) {
            if (byteFotoFachada != null) {
                if (!etComentario.getText().toString().trim().isEmpty()) {
                    if (!txtCarteraEnCliente.getText().toString().isEmpty()) {
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
                        SendMessError("Falta capturar el tipo de entrega");
                } else
                    SendMessError("Falta capturar el comentario");
            } else
                SendMessError("Falta Capturar la foto de fachada.");
        } else
            SendMessError("Falta obtener la ubicación actual.");
    }

}
