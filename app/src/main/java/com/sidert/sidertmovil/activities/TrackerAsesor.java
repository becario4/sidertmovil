package com.sidert.sidertmovil.activities;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.dynamic.IFragmentWrapper;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.fragments.dialogs.dialog_date_picker;
import com.sidert.sidertmovil.models.AsesorID;
import com.sidert.sidertmovil.models.MAsesor;
import com.sidert.sidertmovil.models.MSpiner;
import com.sidert.sidertmovil.models.MSucursales;
import com.sidert.sidertmovil.models.MTrackerAsesor;
import com.sidert.sidertmovil.utils.Constants;
import com.sidert.sidertmovil.utils.CustomInfoWindowAdapter;
import com.sidert.sidertmovil.utils.ManagerInterface;
import com.sidert.sidertmovil.utils.NameFragments;
import com.sidert.sidertmovil.utils.NetworkStatus;
import com.sidert.sidertmovil.utils.Popups;
import com.sidert.sidertmovil.utils.RetrofitClient;
import com.sidert.sidertmovil.utils.SessionManager;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.sidert.sidertmovil.utils.Constants.CONTROLLER_MOVIL;
import static com.sidert.sidertmovil.utils.Constants.FORMAT_DATE_GNRAL;
import static com.sidert.sidertmovil.utils.Constants.cash;
import static com.sidert.sidertmovil.utils.Constants.success;

public class TrackerAsesor extends AppCompatActivity {

    private Context ctx;

    private Toolbar tbMain;
    private Spinner spSucursal;
    private Spinner spAsesor;
    private TextView tvFecha;
    private MapView mapTracker;
    private SessionManager session;
    private GoogleMap mMap;

    private ArrayList<MSpiner> _sucursales = new ArrayList<>();

    private HashMap<Integer, List<MSpiner>> mapAsesores = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracker_asesor);
        ctx = this;
        session = new SessionManager(ctx);

        tbMain       = findViewById(R.id.tbMain);
        spSucursal   = findViewById(R.id.spSucursal);
        spAsesor     = findViewById(R.id.spAsesor);
        tvFecha      = findViewById(R.id.tvFecha);

        mapTracker  = findViewById(R.id.mapTracker);

        mapTracker.onCreate(savedInstanceState);

        setSupportActionBar(tbMain);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        if (NetworkStatus.haveNetworkConnection(ctx))
            GetSucursales();
        else{
            final AlertDialog not_network = Popups.showDialogMessage(ctx, Constants.not_network,
                    R.string.not_network, R.string.accept, new Popups.DialogMessage() {
                        @Override
                        public void OnClickListener(AlertDialog dialog) {
                            finish();
                            dialog.dismiss();
                        }
                    });
            not_network.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            not_network.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            not_network.show();
        }

        tvFecha.setOnClickListener(tvFecha_OnClick);

        spSucursal.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i > 0) {
                    ArrayAdapter<MSpiner> adapter = new ArrayAdapter<MSpiner>(ctx, android.R.layout.simple_list_item_1, mapAsesores.get(i));
                    spAsesor.setAdapter(adapter);
                }
                else
                    spAsesor.setAdapter(null);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    private void GetSucursales(){
        final AlertDialog loading = Popups.showLoadingDialog(ctx, R.string.please_wait, R.string.loading_info);
        loading.show();

        ManagerInterface api = new RetrofitClient().generalRF(CONTROLLER_MOVIL, ctx).create(ManagerInterface.class);

        Call<List<MSucursales>> call = api.getSucursales(Integer.parseInt(session.getUser().get(9)),
                                                        "Bearer "+ session.getUser().get(7));

        call.enqueue(new Callback<List<MSucursales>>() {
            @Override
            public void onResponse(Call<List<MSucursales>> call, Response<List<MSucursales>> response) {
                switch (response.code()){
                    case 200:
                        List<MSucursales> sucursales = response.body();
                        if (sucursales.size() > 0){
                            int i = 0;
                            MSpiner suc = new MSpiner();
                            suc.setNombre("SELECCIONE UNA OPCIÓN");
                            suc.setId(0);
                            _sucursales.add(suc);
                            for (MSucursales item : sucursales){
                                suc = new MSpiner();
                                suc.setNombre(item.getNombre());
                                suc.setId(i+1);
                                _sucursales.add(suc);
                                ArrayList<MSpiner> _asesores = new ArrayList<>();
                                MSpiner ase = new MSpiner();
                                ase.setNombre("SELECCIONE UNA OPCIÓN");
                                ase.setId(0);
                                _asesores.add(ase);
                                for(int j = 0; j < item.getAsesores().size(); j++){
                                    ase = new MSpiner();
                                    ase.setNombre(item.getAsesores().get(j).getNombreAsesor());
                                    ase.setId(item.getAsesores().get(j).getUserId());
                                    _asesores.add(ase);
                                }

                                mapAsesores.put(i+1, _asesores);
                                i++;
                            }
                            ArrayAdapter<MSpiner> adapter = new ArrayAdapter<MSpiner>(ctx, android.R.layout.simple_list_item_1, _sucursales);
                            spSucursal.setAdapter(adapter);


                        }
                        break;
                    case 404:
                        AlertDialog dialog_mess = Popups.showDialogMessage(ctx, Constants.login,
                                R.string.not_found, R.string.accept, new Popups.DialogMessage() {
                                    @Override

                                    public void OnClickListener(AlertDialog dialog) {
                                        dialog.dismiss();
                                    }
                                });
                        dialog_mess.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                        dialog_mess.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                        dialog_mess.show();
                        break;
                }
                loading.dismiss();
            }

            @Override
            public void onFailure(Call<List<MSucursales>> call, Throwable t) {
                loading.dismiss();
            }
        });
    }

    private void GetTracker(int user_id, String fecha){

        Log.e("Ruta","id"+user_id+" "+fecha);

        final AlertDialog loading = Popups.showLoadingDialog(ctx, R.string.please_wait, R.string.loading_info);
        loading.show();

        ManagerInterface api = new RetrofitClient().generalRF(CONTROLLER_MOVIL, ctx).create(ManagerInterface.class);

        Call<List<MTrackerAsesor>> call = api.getTrackerAsesor(user_id,
                                                               fecha,
                                                               "Bearer "+ session.getUser().get(7));

        call.enqueue(new Callback<List<MTrackerAsesor>>() {
            @Override
            public void onResponse(Call<List<MTrackerAsesor>> call, Response<List<MTrackerAsesor>> response) {
                Log.e("ruta", "Code"+response.code());
                switch (response.code()){
                    case 200:
                        List<MTrackerAsesor> _trackerAsesor = response.body();
                        if (_trackerAsesor.size() > 0){
                            SetTracker(_trackerAsesor);
                        }
                        break;
                    case 404:
                        final AlertDialog not_found = Popups.showDialogMessage(ctx, "",
                                R.string.not_found, R.string.accept, new Popups.DialogMessage() {
                                    @Override
                                    public void OnClickListener(AlertDialog dialog) {

                                        dialog.dismiss();
                                    }
                                });
                        not_found.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                        not_found.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                        not_found.show();
                        break;
                }
                loading.dismiss();
            }

            @Override
            public void onFailure(Call<List<MTrackerAsesor>> call, Throwable t) {
                loading.dismiss();
            }
        });
    }

    private void SetTracker(final List<MTrackerAsesor> _tracks){
        mapTracker.onResume();
        try {
            MapsInitializer.initialize(getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mapTracker.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mGooglemap) {
                mMap = mGooglemap;

                Polyline line;
                PolylineOptions options = new PolylineOptions().width(3f).color(Color.RED);
                //for (MTrackerAsesor item: _tracks) {
                for (int i = 0; i < _tracks.size(); i++){
                    MTrackerAsesor item = _tracks.get(i);
                    String[] generatedAt = item.getGeneratedAt().split(" ");
                    String title = generatedAt[1]+" "+item.getBateria()+"%";
                    addMarker(item.getLatitud(), item.getLongitud(), (i == 0), (i == _tracks.size()-1), title);

                    LatLng point = new LatLng(item.getLatitud(), item.getLongitud());
                    options.add(point);
                }
                mMap.addPolyline(options);

                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(_tracks.get(0).getLatitud(), _tracks.get(0).getLongitud()), 15));

                //mMap.setInfoWindowAdapter(new CustomInfoWindowAdapter(LayoutInflater.from(ctx)));
                /*mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {

                        return true;
                    }
                });*/

            }
        });

    }

    private void addMarker (double lat, double lng, boolean isFirst, boolean isLast, String title){
        LatLng coordenadas = new LatLng(lat,lng);

        //CameraUpdate ubication = CameraUpdateFactory.newLatLngZoom(coordenadas,17);

        if (isFirst){
            CameraUpdate ubication = CameraUpdateFactory.newLatLngZoom(coordenadas,15);
            mMap.addMarker(new MarkerOptions()
                    .draggable(true)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_street_view_green))
                    .position(coordenadas)
                    .title(title));

            mMap.addCircle(new CircleOptions()
                    .center(coordenadas)
                    .radius(6.0)
                    .strokeWidth(3f)
                    .fillColor(Color.GREEN));
            mMap.animateCamera(ubication);
        }
        else if (isLast){
            mMap.addMarker(new MarkerOptions()
                    .draggable(true)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_street_view_red))
                    .position(coordenadas)
                    .title(title));

            mMap.addCircle(new CircleOptions()
                    .center(coordenadas)
                    .radius(6.0)
                    .strokeWidth(3f)
                    .fillColor(Color.RED));
        }
        else{
            mMap.addMarker(new MarkerOptions()
                    .draggable(true)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_street_view_orange))
                    .position(coordenadas)
                    .title(title));
        }


    }

    private View.OnClickListener tvFecha_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_DATE_GNRAL);
            Calendar cal = Calendar.getInstance();
            dialog_date_picker dialogDatePicker = new dialog_date_picker();
            Bundle b = new Bundle();

            b.putInt(Constants.YEAR_CURRENT, cal.get(Calendar.YEAR));
            b.putInt(Constants.MONTH_CURRENT, cal.get(Calendar.MONTH));
            b.putInt(Constants.DAY_CURRENT, cal.get(Calendar.DAY_OF_MONTH));
            b.putString(Constants.DATE_CURRENT, sdf.format(cal.getTime()));
            b.putInt(Constants.IDENTIFIER, 9);
            b.putBoolean(Constants.FECHAS_POST, false);
            dialogDatePicker.setArguments(b);
            dialogDatePicker.show(getSupportFragmentManager(), NameFragments.DIALOGDATEPICKER);
        }
    };

    public void setDate (String date){
        tvFecha.setText(date);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_tracker_asesor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.buscar:
                Log.e("Sucursal", ((MSpiner)spSucursal.getSelectedItem()).getId()+"asdasd");
                if (((MSpiner)spSucursal.getSelectedItem()).getId() > 0){
                    if (((MSpiner)spAsesor.getSelectedItem()).getId() > 0){
                        if (!tvFecha.getText().toString().isEmpty()){
                            if (mMap != null)
                                mMap.clear();
                            if (NetworkStatus.haveNetworkConnection(ctx))
                                GetTracker(((MSpiner)spAsesor.getSelectedItem()).getId(), tvFecha.getText().toString());
                            else{
                                final AlertDialog not_network = Popups.showDialogMessage(ctx, Constants.not_network,
                                        R.string.not_network, R.string.accept, new Popups.DialogMessage() {
                                            @Override
                                            public void OnClickListener(AlertDialog dialog) {
                                                finish();
                                                dialog.dismiss();
                                            }
                                        });
                                not_network.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                                not_network.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                                not_network.show();
                            }
                        }
                        else
                            Toast.makeText(ctx, "Falta seleccionar la fecha", Toast.LENGTH_SHORT).show();
                    }
                    else
                        Toast.makeText(ctx, "Falta seleccionar asesor", Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(ctx, "Falta seleccionar sucursal", Toast.LENGTH_SHORT).show();
                break;
            case R.id.limpiar:
                spAsesor.setSelection(0);
                tvFecha.setText("");
                if (mMap != null)
                    mMap.clear();

                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
