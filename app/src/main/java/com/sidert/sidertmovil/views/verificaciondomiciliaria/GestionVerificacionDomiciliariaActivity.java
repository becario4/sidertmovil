package com.sidert.sidertmovil.views.verificaciondomiciliaria;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.activities.CameraVertical;
import com.sidert.sidertmovil.models.apoyogastosfunerarios.Recibo;
import com.sidert.sidertmovil.models.verificacionesdomiciliarias.GestionVerificacionDomiciliaria;
import com.sidert.sidertmovil.models.verificacionesdomiciliarias.GestionVerificacionDomiciliariaDao;
import com.sidert.sidertmovil.models.verificacionesdomiciliarias.VerificacionDomiciliaria;
import com.sidert.sidertmovil.models.verificacionesdomiciliarias.VerificacionDomiciliariaDao;
import com.sidert.sidertmovil.utils.Constants;
import com.sidert.sidertmovil.utils.Miscellaneous;
import com.sidert.sidertmovil.utils.MyCurrentListener;
import com.sidert.sidertmovil.utils.NetworkStatus;
import com.sidert.sidertmovil.utils.Servicios_Sincronizado;
import com.sidert.sidertmovil.utils.SessionManager;
import com.sidert.sidertmovil.utils.Validator;
import com.sidert.sidertmovil.utils.ValidatorTextView;

import java.io.File;
import java.io.IOException;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import static com.sidert.sidertmovil.utils.Constants.PICTURE;
import static com.sidert.sidertmovil.utils.Constants.REQUEST_CODE_CAMARA_FACHADA;
import static com.sidert.sidertmovil.utils.Constants.REQUEST_CODE_CAMARA_TICKET;
import static com.sidert.sidertmovil.utils.Constants.TBL_DOMICILIO_INTEGRANTE;
import static com.sidert.sidertmovil.utils.Constants.TIMESTAMP;

public class GestionVerificacionDomiciliariaActivity extends AppCompatActivity {
    private Context ctx;
    Toolbar tbMain;
    private SessionManager session;
    private LocationManager locationManager;
    private MyCurrentListener locationListener;

    private GestionVerificacionDomiciliariaDao gestionDao;
    private VerificacionDomiciliariaDao verificacionDao;
    private GestionVerificacionDomiciliaria gestion;
    private VerificacionDomiciliaria verificacion;
    public String[] _verificaciones_tipos;

    private Validator validator = new Validator();
    private ValidatorTextView validatorTV = new ValidatorTextView();

    //INFORMACION
    private EditText etSucursalNombre;
    private EditText etAsesorNombre;
    private EditText etClienteFechaNacimiento;
    private EditText etClienteNacionalidad;
    private EditText etClienteNombre;
    private EditText etDomicilioDireccion;
    private EditText etDomicilioReferencia;
    private EditText etHorarioLocalizacion;
    private EditText etMontoSolicitado;

    //FORMULARIO
    private LinearLayout llTipoIntegrante;
    private EditText etComentario;
    private ImageButton ibUbicacion;
    private ImageButton ibFotoFachada;
    private ImageView ivFotoFachada;
    private MapView mapUbicacion;
    private ProgressBar pbLoadMap;
    private RadioGroup rgCoincideDomicilio;
    private TextView tvFotoFachada;
    private TextView tvUbicacion;
    private TextView tvTipoIntegrante;
    private TextView tvCoincideDomicilio;
    private GoogleMap gmMap;
    private LatLng latLngUbiCli;


    public byte[] byteFotoFachada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_gestion_verificacion_domiciliaria);

        ctx = this;
        session = SessionManager.getInstance(ctx);
        tbMain = findViewById(R.id.tbMain);

        gestionDao = new GestionVerificacionDomiciliariaDao(ctx);
        verificacionDao = new VerificacionDomiciliariaDao(ctx);

        etSucursalNombre = findViewById(R.id.etSucursalNombre);
        etAsesorNombre = findViewById(R.id.etAsesorNombre);
        etClienteFechaNacimiento = findViewById(R.id.etClienteFechaNacimiento);
        etClienteNacionalidad = findViewById(R.id.etClienteNacionalidad);
        etClienteNombre = findViewById(R.id.etClienteNombre);
        etDomicilioDireccion = findViewById(R.id.etDomicilioDireccion);
        etDomicilioReferencia = findViewById(R.id.etDomicilioReferencia);
        etHorarioLocalizacion = findViewById(R.id.etHorarioLocalizacion);
        etMontoSolicitado = findViewById(R.id.etMontoSolicitado);

        llTipoIntegrante = findViewById(R.id.llTipoIntegrante);
        etComentario = findViewById(R.id.etComentario);
        ibUbicacion = findViewById(R.id.ibUbicacion);
        ibFotoFachada = findViewById(R.id.ibFotoFachada);
        ivFotoFachada = findViewById(R.id.ivFotoFachada);
        mapUbicacion = findViewById(R.id.mapUbicacion);
        pbLoadMap = findViewById(R.id.pbLoadMap);
        rgCoincideDomicilio = findViewById(R.id.rgCoincideDomicilio);
        tvFotoFachada = findViewById(R.id.tvFotoFachada);
        tvUbicacion = findViewById(R.id.tvUbicacion);
        tvTipoIntegrante = findViewById(R.id.tvTipoIntegrante);
        tvCoincideDomicilio = findViewById(R.id.tvCoincideDomicilio);

        mapUbicacion.onCreate(savedInstanceState);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);


        tvTipoIntegrante.setOnClickListener(tvTipoIntegrante_OnClick);
        ibFotoFachada.setOnClickListener(ibFotoFachada_OnClick);
        ibUbicacion.setOnClickListener(ibUbicacion_OnClick);

        VerificacionDomiciliaria verificacionTemp = (VerificacionDomiciliaria) getIntent().getExtras().get("ver_dom");

        if(verificacionTemp != null && verificacionTemp.getVerificacionDomiciliariaId() != null)
        {
            verificacion = verificacionDao.findByVerificacionDomiciliariaId(verificacionTemp.getVerificacionDomiciliariaId());

            if(verificacion != null) {
                gestion = gestionDao.findByVerificacionDomiciliariaId(verificacion.getVerificacionDomiciliariaId());

                if(verificacion.getGrupoId() == 1)
                {
                    _verificaciones_tipos = getResources().getStringArray(R.array.verificaciones_tipos);
                    llTipoIntegrante.setVisibility(View.GONE);
                }
                else
                {
                    _verificaciones_tipos = getResources().getStringArray(R.array.verificaciones_tipos_grupales);
                }
            }
        }

        if(verificacion.getGrupoId() == 1)
        {
            tvTipoIntegrante.setText(_verificaciones_tipos[verificacion.getVerificacionTipoId() - 1]);
        }
        else
        {
            tvTipoIntegrante.setText(_verificaciones_tipos[verificacion.getVerificacionTipoId() - 2]);
        }

        if(gestion == null) {
            gestion = new GestionVerificacionDomiciliaria();
            gestion.setVerificacionDomiciliariaId(verificacion.getVerificacionDomiciliariaId());
            gestion.setFechaInicio(Miscellaneous.ObtenerFecha("timestamp"));
        }

        setSupportActionBar(tbMain);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        initComponents();
    }

    private View.OnClickListener tvTipoIntegrante_OnClick = v -> {
        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        builder.setTitle(R.string.selected_option)
            .setItems(_verificaciones_tipos, (dialog, position) -> {
                tvTipoIntegrante.setError(null);
                tvTipoIntegrante.setText(_verificaciones_tipos[position]);

                VerificacionDomiciliaria verificacionTemp = verificacionDao.findByGrupoIdAndNumSolicitudAndVerificacionTipoId(
                        verificacion.getGrupoId(),
                        verificacion.getNumSolicitud(),
                        position + 2
                );
                verificacion.setId(verificacionTemp.getId());
                verificacion.setVerificacionDomiciliariaId(verificacionTemp.getVerificacionDomiciliariaId());

                GestionVerificacionDomiciliaria gestionTemp = gestionDao.findByVerificacionDomiciliariaId(verificacion.getVerificacionDomiciliariaId());

                if(gestionTemp != null && gestionTemp.getId() != null && gestionTemp.getId() > 0)
                {
                    gestion = gestionTemp;
                    fillForm();

                    tvTipoIntegrante.setBackground(ContextCompat.getDrawable(ctx, R.drawable.et_rounded_edges));
                    tvTipoIntegrante.setEnabled(true);
                }
                else
                {
                    if(gestion.getId() != null)
                    {
                        gestion = new GestionVerificacionDomiciliaria();

                        enabledForm();
                    }

                    gestion.setVerificacionDomiciliariaId(verificacion.getVerificacionDomiciliariaId());
                    gestion.setFechaInicio(Miscellaneous.ObtenerFecha("timestamp"));
                }

            });
        builder.create();
        builder.show();
    };

    private View.OnClickListener ibFotoFachada_OnClick = v -> {
        Intent i = new Intent(ctx, CameraVertical.class);
        startActivityForResult(i, REQUEST_CODE_CAMARA_FACHADA);
    };

    private View.OnClickListener ibUbicacion_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
                Toast.makeText(ctx, "El GPS se encuentra desactivado", Toast.LENGTH_SHORT).show();
            else
                ObtenerUbicacion();
        }
    };

    private void initComponents()
    {
        if(verificacion != null)
        {
            etSucursalNombre.setText(verificacion.getSucursalNombre());
            etAsesorNombre.setText(verificacion.getAsesorSerieId() + " - " + verificacion.getAsesorNombre());
            etClienteFechaNacimiento.setText(verificacion.getClienteFechaNacimiento());
            etClienteNacionalidad.setText(verificacion.getClienteNacionalidad());

            if(verificacion.getGrupoId() == 1) etClienteNombre.setText(verificacion.getClienteNombre());
            else etClienteNombre.setText(verificacion.getGrupoNombre());

            etDomicilioDireccion.setText(verificacion.getDomicilioDireccion());
            etDomicilioReferencia.setText(verificacion.getDomicilioReferencia());
            etHorarioLocalizacion.setText(verificacion.getHorarioLocalizacion());
            etMontoSolicitado.setText(verificacion.getMontoSolicitado());
        }
        else
        {
            disabledForm();
        }

        if(gestion != null && gestion.getId() != null && gestion.getId() > 0)
        {
            fillForm();
        }
        else
        {
            gestion.setFechaInicio(Miscellaneous.ObtenerFecha("timestamp"));

        }
    }

    private void enabledForm()
    {
        etComentario.setBackground(ContextCompat.getDrawable(ctx, R.drawable.et_rounded_edges));
        etComentario.setEnabled(true);
        etComentario.setText("");
        ibUbicacion.setBackground(ContextCompat.getDrawable(ctx, R.drawable.btn_rounded_blue));
        ibUbicacion.setEnabled(true);
        ibUbicacion.setVisibility(View.VISIBLE);
        ibFotoFachada.setBackground(ContextCompat.getDrawable(ctx, R.drawable.btn_rounded_blue));
        ibFotoFachada.setEnabled(true);
        ibFotoFachada.setVisibility(View.VISIBLE);
        ivFotoFachada.setVisibility(View.GONE);
        mapUbicacion.setVisibility(View.GONE);
        pbLoadMap.setVisibility(View.GONE);

        for(int i = 0; i < rgCoincideDomicilio.getChildCount(); i++){
            rgCoincideDomicilio.getChildAt(i).setEnabled(true);
            ((RadioButton) rgCoincideDomicilio.getChildAt(i)).setChecked(false);
        }

        if(tbMain.getMenu() != null && tbMain.getMenu().size() > 0) tbMain.getMenu().getItem(0).setVisible(true);
    }

    private void disabledForm()
    {
        etComentario.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        etComentario.setEnabled(false);
        ibUbicacion.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        ibUbicacion.setEnabled(false);
        ibFotoFachada.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        ibFotoFachada.setEnabled(false);

        for(int i = 0; i < rgCoincideDomicilio.getChildCount(); i++){
            rgCoincideDomicilio.getChildAt(i).setEnabled(false);
        }

        tvTipoIntegrante.setBackground(ContextCompat.getDrawable(ctx, R.drawable.bkg_rounded_edges_blocked));
        tvTipoIntegrante.setEnabled(false);

        if(tbMain.getMenu() != null && tbMain.getMenu().size() > 0) tbMain.getMenu().getItem(0).setVisible(false);
    }

    private void fillForm()
    {
        etComentario.setText(gestion.getComentario());

        if (!gestion.getFotoFachada().isEmpty()){
            File fachadaFile = new File(Constants.ROOT_PATH + "Fachada/"+ gestion.getFotoFachada());
            Uri uriFachada = Uri.fromFile(fachadaFile);
            byteFotoFachada = Miscellaneous.getBytesUri(ctx, uriFachada,0);
            Glide.with(ctx).load(uriFachada).into(ivFotoFachada);
            ibFotoFachada.setVisibility(View.GONE);
            ivFotoFachada.setVisibility(View.VISIBLE);
        }

        mapUbicacion.setVisibility(View.VISIBLE);
        Ubicacion(Double.parseDouble(gestion.getLatitud()), Double.parseDouble(gestion.getLongitud()));

        if (gestion.getDomicilioCoincide() == 1) ((RadioButton) rgCoincideDomicilio.getChildAt(0)).setChecked(true);
        else ((RadioButton) rgCoincideDomicilio.getChildAt(1)).setChecked(true);

        if(verificacion.getGrupoId() == 1)
        {
            tvTipoIntegrante.setText(_verificaciones_tipos[verificacion.getVerificacionTipoId() - 1]);
        }
        else
        {
            tvTipoIntegrante.setText(_verificaciones_tipos[verificacion.getVerificacionTipoId() - 2]);
        }

        disabledForm();

    }

    private void Ubicacion(final double latitud, final double longitud)
    {
        mapUbicacion.onResume();
        try {
            MapsInitializer.initialize(getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mapUbicacion.getMapAsync(mGooglemap -> {
            gmMap = mGooglemap;
            if (ActivityCompat.checkSelfPermission(ctx, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(ctx, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                return;
            }
            gmMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            ctx, R.raw.style_json));
            gmMap.getUiSettings().setAllGesturesEnabled(false);
            gmMap.getUiSettings().setMapToolbarEnabled(false);

            addMarker(latitud, longitud);
        });
    }

    private void addMarker(double latitud, double longitud)
    {
        LatLng coordenadas = new LatLng(latitud, longitud);
        latLngUbiCli = coordenadas;
        CameraUpdate ubication = CameraUpdateFactory.newLatLngZoom(coordenadas,17);

        gmMap.clear();
        gmMap.addMarker(new MarkerOptions()
                .position(coordenadas)
                .title(""));

        gmMap.animateCamera(ubication);

        pbLoadMap.setVisibility(View.GONE);
        ibUbicacion.setVisibility(View.GONE);
    }

    private void ObtenerUbicacion()
    {
        pbLoadMap.setVisibility(View.VISIBLE);
        ibUbicacion.setEnabled(false);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        final Handler myHandler = new Handler();

        locationListener = new MyCurrentListener(new MyCurrentListener.evento() {
            @Override
            public void onComplete(String latitud, String longitud) {

                ibUbicacion.setEnabled(true);
                tvUbicacion.setError(null);

                if (!latitud.isEmpty() && !longitud.isEmpty())
                {
                    mapUbicacion.setVisibility(View.VISIBLE);
                    gestion.setLatitud(latitud);
                    gestion.setLongitud(longitud);

                    Ubicacion(Double.parseDouble(latitud), Double.parseDouble(longitud));
                }
                else
                {
                    latLngUbiCli = new LatLng(0,0);
                    gestion.setLatitud("");
                    gestion.setLongitud("");

                    pbLoadMap.setVisibility(View.GONE);
                    Toast.makeText(ctx, getResources().getString(R.string.no_ubicacion), Toast.LENGTH_SHORT).show();
                }

                myHandler.removeCallbacksAndMessages(null);

                CancelUbicacion();
            }
        });

        if (ActivityCompat.checkSelfPermission(ctx, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.checkSelfPermission(ctx, Manifest.permission.ACCESS_COARSE_LOCATION);
        }

        String provider;
        if (NetworkStatus.haveNetworkConnection(ctx)) {
            provider = LocationManager.NETWORK_PROVIDER;
        }
        else {
            provider = LocationManager.GPS_PROVIDER;
        }

        locationManager.requestSingleUpdate(provider, locationListener,null);

        myHandler.postDelayed(() -> {
            locationManager.removeUpdates(locationListener);
            pbLoadMap.setVisibility(View.GONE);
            ibUbicacion.setEnabled(true);
            latLngUbiCli = new LatLng(0,0);
            Toast.makeText(ctx, "No se logró obtener la ubicación", Toast.LENGTH_SHORT).show();
        }, 60000);
    }

    private void CancelUbicacion (){
        locationManager.removeUpdates(locationListener);
    }

    private void Guardar()
    {
        Boolean bValido = true;

        if(((RadioButton) rgCoincideDomicilio.getChildAt(0)).isChecked()) gestion.setDomicilioCoincide(1);
        if(((RadioButton) rgCoincideDomicilio.getChildAt(1)).isChecked()) gestion.setDomicilioCoincide(0);
        gestion.setComentario(Miscellaneous.GetStr(etComentario));

        if(gestion.getLatitud() == null || gestion.getLatitud().equals(""))
        {
            bValido = false;
            tvUbicacion.setError("Este campo es requerido.");
        }
        else
        {
            tvUbicacion.setError(null);
        }

        if(gestion.getFotoFachada() == null || gestion.getFotoFachada().isEmpty())
        {
            bValido = false;
            tvFotoFachada.setError("Este campo es requerido.");
        }
        else
        {
            tvFotoFachada.setError(null);
        }

        if(gestion.getDomicilioCoincide() == null)
        {
            bValido = false;
            tvCoincideDomicilio.setError("Este campo es requerido.");
        }
        else
        {
            tvCoincideDomicilio.setError(null);
        }

        if(gestion.getComentario() == null || gestion.getComentario().isEmpty())
        {
            bValido = false;
            etComentario.setError("Este campo es requerido.");
        }
        else
        {
            etComentario.setError(null);
        }

        if(bValido)
        {
            gestion.setVerificacionDomiciliariaId(verificacion.getVerificacionDomiciliariaId());
            gestion.setUsuarioId(Long.parseLong(session.getUser().get(9)));
            gestion.setUsuarioNombre(session.getUser().get(1) + " " + session.getUser().get(2) + " " + session.getUser().get(3));
            gestion.setUsuarioNombre(gestion.getUsuarioNombre().trim());
            gestion.setFechaFin(Miscellaneous.ObtenerFecha("timestamp"));
            gestion.setFechaEnvio(Miscellaneous.ObtenerFecha("timestamp"));
            gestion.setCreatedAt(Miscellaneous.ObtenerFecha("timestamp"));
            gestion.setEstatus(1);//DISPONIBLE PARA ENVIO

            if(gestion.getId() == null || gestion.getId() == 0) gestionDao.store(gestion);
            else gestionDao.update(gestion);

            Servicios_Sincronizado ss = new Servicios_Sincronizado();
            ss.SendGestionesVerDom(ctx, true);
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE_CAMARA_FACHADA:
                if (resultCode == Activity.RESULT_OK){
                    if (data != null){
                        ibFotoFachada.setVisibility(View.GONE);
                        tvFotoFachada.setError(null);
                        ivFotoFachada.setVisibility(View.VISIBLE);
                        byteFotoFachada = data.getByteArrayExtra(PICTURE);

                        Glide.with(ctx).load(byteFotoFachada).centerCrop().into(ivFotoFachada);

                        try {
                            gestion.setFotoFachada(Miscellaneous.save(byteFotoFachada, 1));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_save, menu);
        if (gestion.getId() != null || verificacion == null)
            menu.getItem(0).setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == android.R.id.home) {
            finish();
        } else if (itemId == R.id.save) {
            Guardar();
        }
        return super.onOptionsItemSelected(item);
    }
}
