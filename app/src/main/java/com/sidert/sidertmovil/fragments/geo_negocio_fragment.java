package com.sidert.sidertmovil.fragments;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.ColorDrawable;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
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
import com.sidert.sidertmovil.activities.GeolocalizacionInd;
import com.sidert.sidertmovil.activities.LectorCodigoBarras;
import com.sidert.sidertmovil.activities.VerImagen;
import com.sidert.sidertmovil.database.DBhelper;
import com.sidert.sidertmovil.utils.Constants;
import com.sidert.sidertmovil.utils.Miscellaneous;
import com.sidert.sidertmovil.utils.MyCurrentListener;
import com.sidert.sidertmovil.utils.NetworkStatus;
import com.sidert.sidertmovil.utils.Popups;
import com.sidert.sidertmovil.utils.Servicios_Sincronizado;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

import static com.sidert.sidertmovil.utils.Constants.TBL_CARTERA_IND_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_GEO_RESPUESTAS_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_PRESTAMOS_IND_T;
import static com.sidert.sidertmovil.utils.Constants.TIMESTAMP;


public class geo_negocio_fragment extends Fragment {

    private Context ctx;
    private GeolocalizacionInd boostrap;
    private TextView tvMapa;
    private TextView tvDireccionCap;
    private EditText etNombre;
    public EditText etCodigoBarras;
    public EditText etComentario;
    private EditText etDireccionCap;
    private EditText etFechaFinalizacion;
    private EditText etFechaEnvio;
    private ImageButton ibUbicacion;
    private ImageButton ibCodigoBarras;
    private ImageButton ibFotoFachada;
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

    private SQLiteDatabase db;
    private DBhelper dBhelper;

    public boolean flag_edit = true;
    public boolean isUbicacion = false;

    //private String ficha_id;
    private String id_cartera;
    //private int status;
    private String direccion = "No se encontró la dirección";

    private boolean isSave = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_geo_negocio, container, false);
        ctx = getContext();
        boostrap = (GeolocalizacionInd) getActivity();

        dBhelper = new DBhelper(ctx);
        db = dBhelper.getWritableDatabase();

        tvMapa          = view.findViewById(R.id.tvMapa);
        tvDireccionCap  = view.findViewById(R.id.tvDireccionCap);

        etNombre = view.findViewById(R.id.etNombre);
        etCodigoBarras = view.findViewById(R.id.etCodigoBarras);

        etComentario   = view.findViewById(R.id.etComentario);
        etDireccionCap = view.findViewById(R.id.etDireccionCap);

        etFechaFinalizacion = view.findViewById(R.id.etFechaFinalizacion);
        etFechaEnvio        = view.findViewById(R.id.etFechaEnvio);

        ibUbicacion = view.findViewById(R.id.ibUbicacion);
        ibCodigoBarras = view.findViewById(R.id.ibCodigoBarras);
        ibFotoFachada = view.findViewById(R.id.ibFotoFachada);

        ivFotoFachada = view.findViewById(R.id.ivFotoFachada);

        pbLoading       = view.findViewById(R.id.pbLoading);

        mapUbicacion = view.findViewById(R.id.mapUbicacion);

        llFechaFinalizacion = view.findViewById(R.id.llFechaFinalizacion);
        llFechaEnvio = view.findViewById(R.id.llFechaEnvio);

        btnGuardar = view.findViewById(R.id.btnGuardar);

        mapUbicacion.onCreate(savedInstanceState);
        locationManager = (LocationManager) boostrap.getSystemService(Context.LOCATION_SERVICE);

        initComponents();

       return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ibUbicacion.setOnClickListener(ibUbicacion_OnClick);
        ibCodigoBarras.setOnClickListener(ibCodigoBarras_OnClick);
        ibFotoFachada.setOnClickListener(ibFotoFachada_OnClick);
        ivFotoFachada.setOnClickListener(ivFotoFachada_OnClick);
        btnGuardar.setOnClickListener(btnGuardar_OnClick);


        mapUbicacion.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(LatLng latLng) {
                        if(flag_edit) {
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
                        }
                        else{
                            final AlertDialog ubicacion_dlg = Popups.showDialogConfirm(ctx, Constants.question,
                                    R.string.ver_en_maps, R.string.ver_maps, new Popups.DialogMessage() {
                                        @Override
                                        public void OnClickListener(AlertDialog dialog) {
                                            Intent i_maps = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse("geo:"+latLngUbicacion.latitude+","+latLngUbicacion.longitude+"?z=16&q="+latLngUbicacion.latitude+","+latLngUbicacion.longitude+"()"));
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

    private View.OnClickListener ibFotoFachada_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i = new Intent(boostrap, CameraVertical.class);
            i.putExtra(Constants.ORDER_ID, "_negocio");
            startActivityForResult(i, Constants.REQUEST_CODE_CAMARA_FACHADA);
        }
    };

    private View.OnClickListener ivFotoFachada_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (flag_edit) {
                final AlertDialog evidencia_dlg = Popups.showDialogConfirmImage(ctx, Constants.question,
                        R.string.capturar_foto, R.string.fotografia, new Popups.DialogMessage() {
                            @Override
                            public void OnClickListener(AlertDialog dialog) {
                                Intent i = new Intent(boostrap, CameraVertical.class);
                                i.putExtra(Constants.ORDER_ID, "_negocio");
                                startActivityForResult(i, Constants.REQUEST_CODE_CAMARA_FACHADA);
                                dialog.dismiss();

                            }
                        }, R.string.ver_imagen, new Popups.DialogMessage() {
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
                Objects.requireNonNull(evidencia_dlg.getWindow()).requestFeature(Window.FEATURE_NO_TITLE);
                evidencia_dlg.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                evidencia_dlg.show();

            }
            else {
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
    private void ObtenerUbicacion (){
        pbLoading.setVisibility(View.VISIBLE);
        ibUbicacion.setEnabled(false);
        locationManager = (LocationManager) boostrap.getSystemService(Context.LOCATION_SERVICE);
        final Handler myHandler = new Handler();
        locationListener = new MyCurrentListener(new MyCurrentListener.evento() {
            @Override
            public void onComplete(String latitud, String longitud) {

                ibUbicacion.setEnabled(true);
                if (!latitud.isEmpty() && !longitud.isEmpty()){
                    mapUbicacion.setVisibility(View.VISIBLE);
                    ColocarUbicacionGestion(Double.parseDouble(latitud), Double.parseDouble(longitud));
                }
                else{
                    pbLoading.setVisibility(View.GONE);
                    Toast.makeText(ctx, getResources().getString(R.string.no_ubicacion), Toast.LENGTH_SHORT).show();
                }
                myHandler.removeCallbacksAndMessages(null);

                flagUbicacion = true;
                if (flagUbicacion){
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
        }
        else {
            Log.e("Proveedor", "GPS");
            provider = LocationManager.GPS_PROVIDER;
        }

        locationManager.requestSingleUpdate(provider, locationListener,null);

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

    private void ColocarUbicacionGestion (final double lat, final double lon){
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

                addMarker(lat,lon);

            }
        });
    }

    private void addMarker (double lat, double lng){
        if (flag_edit)
            etDireccionCap.setText(Miscellaneous.ObtenerDireccion(ctx, lat, lng));
        else
            etDireccionCap.setText(direccion);
        etDireccionCap.setVisibility(View.VISIBLE);
        tvDireccionCap.setVisibility(View.VISIBLE);
        tvDireccionCap.setTextColor(getResources().getColor(R.color.black));
        LatLng coordenadas = new LatLng(lat,lng);
        latLngUbicacion = coordenadas;
        //LatLng coordenada = new LatLng(19.201745,-96.162134);
        CameraUpdate ubication = CameraUpdateFactory.newLatLngZoom(coordenadas,17);

        mMap.clear();
        mMap.addMarker(new MarkerOptions()
                .position(coordenadas)
                .title(""));

        mMap.animateCamera(ubication);

        pbLoading.setVisibility(View.GONE);
        ibUbicacion.setVisibility(View.GONE);
    }

    private void CancelUbicacion (){
        if (flagUbicacion)
            locationManager.removeUpdates(locationListener);
    }

    //====================== For activity Result  ==============================================

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case Constants.REQUEST_CODE_CODEBARS:
                if (resultCode == boostrap.RESULT_OK){
                    if (data != null){
                        etCodigoBarras.setVisibility(View.VISIBLE);
                        etCodigoBarras.setText(data.getStringExtra(Constants.CODEBARS));
                    }
                }
                break;
            case Constants.REQUEST_CODE_CAMARA_FACHADA:
                if (resultCode == Activity.RESULT_OK){
                    if (data != null){
                        ibFotoFachada.setVisibility(View.GONE);
                        ivFotoFachada.setVisibility(View.VISIBLE);
                        byteFotoFachada = data.getByteArrayExtra(Constants.PICTURE);
                        Glide.with(ctx).load(byteFotoFachada).centerCrop().into(ivFotoFachada);
                    }
                }
                break;
        }
    }

    private void SendMessError (String mess){
        Toast.makeText(ctx, mess, Toast.LENGTH_SHORT).show();
    }

    public void GuardarGeo () {

        try {
            HashMap<Integer, String> params = new HashMap<>();
            params.put(0, id_cartera);
            params.put(1, getArguments().getString(Constants.NUM_SOLICITUD));
            params.put(2, "1");
            params.put(3, "NEGOCIO");
            params.put(4, "0");
            params.put(5, etNombre.getText().toString().trim().toUpperCase());
            params.put(6, "");
            params.put(7, ((isUbicacion)?"0":String.valueOf(latLngUbicacion.latitude)));
            params.put(8, ((isUbicacion)?"0":String.valueOf(latLngUbicacion.longitude)));
            params.put(9, etDireccionCap.getText().toString().trim().toUpperCase());
            params.put(10, etCodigoBarras.getText().toString().trim());
            params.put(11, Miscellaneous.save(byteFotoFachada, 1));
            params.put(12, etComentario.getText().toString().trim().toUpperCase());
            params.put(13, Miscellaneous.ObtenerFecha(TIMESTAMP));
            params.put(14, "");
            params.put(15, "0");
            params.put(16, "0");

            if (isSave)
                dBhelper.saveGeoRespuestas(db, params);
            else
                Toast.makeText(ctx, "No hay Cliente registrado, no se puede guardar la geolocalización", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Servicios_Sincronizado servicios = new Servicios_Sincronizado();
        servicios.SaveGeolocalizacion(ctx, false);
        initComponents();


    }

    private void initComponents (){
        Cursor row;

        String sql = "SELECT c.id_cartera FROM " + TBL_CARTERA_IND_T + " AS c LEFT JOIN " + TBL_PRESTAMOS_IND_T + " AS p ON p.id_cliente = c.id_cartera WHERE c.num_solicitud = ?";

        row = db.rawQuery(sql, new String[]{getArguments().getString(Constants.NUM_SOLICITUD)});

        if (row.getCount() > 0) {
            row.moveToFirst();
            id_cartera = row.getString(0);
            isSave = true;
        }

        row.close();

        sql = "SELECT * FROM " + TBL_GEO_RESPUESTAS_T +" WHERE id_cartera = ? AND tipo_ficha = 1 AND tipo_geolocalizacion = 'NEGOCIO'";

        row = db.rawQuery(sql, new String[]{id_cartera});

        if (row.getCount() > 0){
            row.moveToFirst();
            flag_edit = false;

            etNombre.setEnabled(false);
            etNombre.setText(row.getString(6));
            etNombre.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
            direccion = row.getString(10);
            if (row.getDouble(8) == 0 && row.getDouble(9) == 0){
                ibUbicacion.setVisibility(View.GONE);
                tvMapa.setVisibility(View.GONE);
                tvDireccionCap.setTextColor(getResources().getColor(R.color.black));
                tvDireccionCap.setText(getResources().getString(R.string.direccion));
                tvDireccionCap.setVisibility(View.VISIBLE);
                etDireccionCap.setText(direccion);
                etDireccionCap.setVisibility(View.VISIBLE);
            }
            else {
                mapUbicacion.setVisibility(View.VISIBLE);
                ColocarUbicacionGestion(row.getDouble(8), row.getDouble(9));
            }

            ibCodigoBarras.setVisibility(View.GONE);
            etCodigoBarras.setVisibility(View.VISIBLE);
            etCodigoBarras.setText(row.getString(11));
            etCodigoBarras.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
            ibFotoFachada.setVisibility(View.GONE);
            File fachadaFile = new File(Constants.ROOT_PATH + "Fachada/"+row.getString(12));
            Uri uriFachada = Uri.fromFile(fachadaFile);
            byteFotoFachada = Miscellaneous.getBytesUri(ctx, uriFachada,0);
            Glide.with(ctx).load(uriFachada).into(ivFotoFachada);
            ivFotoFachada.setVisibility(View.VISIBLE);
            etComentario.setText(row.getString(13));
            etComentario.setEnabled(false);
            etComentario.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
            llFechaFinalizacion.setVisibility(View.VISIBLE);
            llFechaEnvio.setVisibility(View.VISIBLE);
            etFechaFinalizacion.setText(row.getString(14));
            etFechaEnvio.setText((!row.getString(15).isEmpty())?row.getString(15):"Pendiente por enviar");
            btnGuardar.setVisibility(View.GONE);

        }
    }

    public void ValidarInformacion() {
        if (!etNombre.getText().toString().trim().isEmpty()) {
            if (latLngUbicacion != null || isUbicacion) {
                if (byteFotoFachada != null) {
                    if (!etComentario.getText().toString().trim().isEmpty()) {
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
        } else
            SendMessError("Falta ingresar el nombre del negocio");
    }
}
