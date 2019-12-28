package com.sidert.sidertmovil.fragments.view_pager;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.OnClickListener;
import com.orhanobut.dialogplus.ViewHolder;
import com.sidert.sidertmovil.Home;
import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.activities.GeolocalizacionGpo;
import com.sidert.sidertmovil.activities.GeolocalizacionInd;
import com.sidert.sidertmovil.adapters.adapter_geo_pendientes;
import com.sidert.sidertmovil.database.DBhelper;
import com.sidert.sidertmovil.fragments.geolocalizacion_fragment;
import com.sidert.sidertmovil.models.ModelGeolocalizacion;
import com.sidert.sidertmovil.utils.Constants;
import com.sidert.sidertmovil.utils.Servicios_Sincronizado;
import com.sidert.sidertmovil.utils.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class geo_completadas_fragment extends Fragment {

    private Context ctx;
    private Home boostrap;
    private TextView tvNoInfo;
    private RecyclerView rvGeolocalizacion;
    private adapter_geo_pendientes adapter;
    private List<ModelGeolocalizacion> _m_geolocalizacion;

    private ModelGeolocalizacion mGeo;
    private SQLiteDatabase db;
    private DBhelper dBhelper;

    private geolocalizacion_fragment parent;

    private SessionManager session;

    private TextView tvContFiltros;
    private AutoCompleteTextView aetNombre;
    private AutoCompleteTextView aetColonia;
    private AutoCompleteTextView aetAsesor;
    private CheckBox cbInd;
    private CheckBox cbGpo;

    private int cont_filtros = 0;
    private FrameLayout parentLayout;
    private String[] dataNombre;
    private String[] dataColonia;
    private String[] dataAsesor;
    private ArrayAdapter<String> adapterNombre;
    private ArrayAdapter<String> adapterColonia;
    private ArrayAdapter<String> adapterAsesor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_geo_pendientes, container,false);
        ctx = getContext();
        boostrap = (Home) getActivity();
        session = new SessionManager(ctx);

        parent = (geolocalizacion_fragment) getParentFragment();

        dBhelper = new DBhelper(ctx);
        db = dBhelper.getWritableDatabase();

        rvGeolocalizacion   = view.findViewById(R.id.rvGeolocalizacion);
        tvNoInfo            = view.findViewById(R.id.tvNoInfo);

        rvGeolocalizacion.setLayoutManager(new LinearLayoutManager(ctx));
        rvGeolocalizacion.setHasFixedSize(false);

        setHasOptionsMenu(true);
        return view;
    }

    private void GetGeolocalizacion(String where) {
        Cursor row;

        if (Constants.ENVIROMENT)
            row = dBhelper.getRecords(Constants.GEOLOCALIZACION, " WHERE status = 3" + where, " ORDER BY nombre ASC", null);
        else
            row = dBhelper.getRecords(Constants.GEOLOCALIZACION_T, " WHERE status = 3" + where, " ORDER BY nombre ASC", null);

        parent.SetUpBagde(1, row.getCount());
        _m_geolocalizacion = new ArrayList<>();
        row.moveToFirst();
        for (int i = 0; i < row.getCount(); i++){
            mGeo = new ModelGeolocalizacion();
            mGeo.setId(Integer.parseInt(row.getString(0)));
            mGeo.setAsesor_nombre(row.getString(2));
            mGeo.setTipo_form(Integer.parseInt(row.getString(3)));
            mGeo.setNombre(row.getString(4));
            mGeo.setNum_solicitud(row.getString(5));
            mGeo.setDireccion(row.getString(8));
            mGeo.setColonia(row.getString(9));
            mGeo.setRes_uno(row.getString(13));
            mGeo.setRes_dos(row.getString(14));
            mGeo.setRes_tres(row.getString(15));
            mGeo.setFecha_env(row.getString(19));
            mGeo.setStatus(Integer.parseInt(row.getString(21)));
            _m_geolocalizacion.add(mGeo);
            row.moveToNext();
        }

    }

    private void Filtros (){
        DialogPlus filtros_dg = DialogPlus.newDialog(boostrap)
                .setContentHolder(new ViewHolder(R.layout.sheet_dialog_filtros))
                .setGravity(Gravity.TOP)
                .setPadding(20,10,20,10)
                .setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(DialogPlus dialog, View view) {
                        String where = "";
                        cont_filtros = 0;
                        HashMap<String, String> filtros = new HashMap<>();
                        InputMethodManager imm = (InputMethodManager)boostrap.getSystemService(Context.INPUT_METHOD_SERVICE);
                        switch (view.getId()) {
                            case R.id.btnFiltrar:
                                if (!aetNombre.getText().toString().trim().isEmpty()){
                                    filtros.put("nombre_c",aetNombre.getText().toString().trim());
                                    cont_filtros += 1;
                                    where = " AND nombre LIKE '%"+aetNombre.getText().toString().trim()+"%'";
                                }
                                else filtros.put("nombre_c","");

                                if (!aetColonia.getText().toString().trim().isEmpty()) {
                                    filtros.put("colonia_c",aetColonia.getText().toString().trim());
                                    cont_filtros += 1;
                                    where += " AND colonia LIKE '%" + aetColonia.getText().toString().trim() + "%'";
                                }
                                else filtros.put("colonia_c","");

                                if (!aetAsesor.getText().toString().trim().isEmpty()) {
                                    filtros.put("asesor_c",aetAsesor.getText().toString().trim());
                                    cont_filtros += 1;
                                    where += " AND asesor_nombre LIKE '%" + aetAsesor.getText().toString().trim() + "%'";
                                }
                                else filtros.put("asesor_c","");

                                if (cbInd.isChecked() && cbGpo.isChecked()){
                                    filtros.put("individual_c","1");
                                    filtros.put("grupal_c","1");
                                    cont_filtros += 2;
                                    where += " AND tipo_ficha IN (1,2)";
                                }
                                else if (cbInd.isChecked()){
                                    filtros.put("individual_c","1");
                                    filtros.put("grupal_c","0");
                                    cont_filtros += 1;
                                    where += " AND tipo_ficha = "+1;
                                }
                                else if (cbGpo.isChecked()){
                                    filtros.put("individual_c","0");
                                    filtros.put("grupal_c","1");
                                    cont_filtros += 1;
                                    where += " AND tipo_ficha = "+2;
                                }else {
                                    filtros.put("individual_c","0");
                                    filtros.put("grupal_c","0");
                                }
                                filtros.put("contador_c", String.valueOf(cont_filtros));
                                session.setFiltrosGeoComp(filtros);

                                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                                GetGeolocalizacion(where);
                                adapter.UpdateData(_m_geolocalizacion);
                                dialog.dismiss();

                                break;
                            case R.id.btnBorrarFiltros:

                                cont_filtros = 0;
                                filtros = new HashMap<>();
                                filtros.put("nombre_c","");
                                filtros.put("colonia_c","");
                                filtros.put("asesor_c","");
                                filtros.put("individual_c","0");
                                filtros.put("grupal_c","0");
                                filtros.put("contador_c", "0");

                                session.setFiltrosGeoComp(filtros);
                                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                                GetGeolocalizacion("");
                                adapter.UpdateData(_m_geolocalizacion);
                                dialog.dismiss();

                                break;
                        }
                        setupBadge();

                    }
                })
                .setExpanded(true, 900)  // This will enable the expand feature, (similar to android L share dialog)
                .create();
        aetNombre   = filtros_dg.getHolderView().findViewById(R.id.aetNombre);
        aetColonia  = filtros_dg.getHolderView().findViewById(R.id.aetColonia);
        aetAsesor   = filtros_dg.getHolderView().findViewById(R.id.aetAsesor);
        cbInd       = filtros_dg.getHolderView().findViewById(R.id.cbInd);
        cbGpo       = filtros_dg.getHolderView().findViewById(R.id.cbGpo);

        aetNombre.setAdapter(adapterNombre);
        aetColonia.setAdapter(adapterColonia);
        aetAsesor.setAdapter(adapterAsesor);

        aetNombre.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                aetNombre.showDropDown();
                return false;
            }
        });

        aetColonia.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                aetColonia.showDropDown();
                return false;
            }
        });

        aetAsesor.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                aetAsesor.showDropDown();
                return false;
            }
        });

        if (!session.getFiltrosGeoComp().get(0).isEmpty())
            aetNombre.setText(session.getFiltrosGeoComp().get(0));
        if (!session.getFiltrosGeoComp().get(1).isEmpty())
            aetColonia.setText(session.getFiltrosGeoComp().get(1));
        if (!session.getFiltrosGeoComp().get(2).isEmpty())
            aetAsesor.setText(session.getFiltrosGeoComp().get(2));
        if (session.getFiltrosGeoComp().get(3).equals("1"))
            cbInd.setChecked(true);
        if (session.getFiltrosGeoComp().get(4).equals("1"))
            cbGpo.setChecked(true);
        filtros_dg.show();
    }

    private void setupBadge() {
        Log.v("contador",session.getFiltrosGeoComp().get(5));
        if (tvContFiltros != null) {
            Log.e("tvcontador", "visible");
            tvContFiltros.setText(String.valueOf(session.getFiltrosGeoComp().get(5)));
            tvContFiltros.setVisibility(View.VISIBLE);
        }

        /*if (tvContFiltros != null) {
            if (cont_filtros == 0) {
                if (tvContFiltros.getVisibility() != View.GONE) {
                    tvContFiltros.setVisibility(View.GONE);
                }
            } else {
                tvContFiltros.setText(String.valueOf(session.getFiltrosGeo().get(5)));
                if (tvContFiltros.getVisibility() != View.VISIBLE) {
                    tvContFiltros.setVisibility(View.VISIBLE);
                }
            }
        }*/
    }

    @Override
    public void onResume() {
        super.onResume();

        setupBadge();
        String where = "";
        if (!session.getFiltrosGeoComp().get(0).isEmpty())
            where = " AND nombre LIKE '%" + session.getFiltrosGeoComp().get(0) + "%'";

        if (!session.getFiltrosGeoComp().get(1).isEmpty()) {
            where += " AND colonia LIKE '%" + session.getFiltrosGeoComp().get(1) + "%'";
        }

        if (!session.getFiltrosGeoComp().get(2).isEmpty()) {
            where += " AND asesor_nombre LIKE '%" + session.getFiltrosGeoComp().get(2) + "%'";
        }

        if (session.getFiltrosGeoComp().get(3).equals("1") && session.getFiltrosGeoComp().get(4).equals("1")){
            where += " AND tipo_ficha IN (1,2)";
        }
        else if (session.getFiltrosGeoComp().get(3).equals("1")){
            where += " AND tipo_ficha = "+1;
        }
        else if (session.getFiltrosGeoComp().get(4).equals("1")){
            where += " AND tipo_ficha = "+2;
        }

        GetGeolocalizacion(where);
        adapter = new adapter_geo_pendientes(ctx, _m_geolocalizacion, new adapter_geo_pendientes.Event() {
            @Override
            public void GeoOnClick(ModelGeolocalizacion item, int modulo) {
                Cursor row;
                if (Constants.ENVIROMENT)
                    row = dBhelper.getRecords(Constants.GEOLOCALIZACION, " WHERE num_solicitud = '"+item.getNum_solicitud()+"'", "", null);
                else
                    row = dBhelper.getRecords(Constants.GEOLOCALIZACION_T, " WHERE num_solicitud = '"+item.getNum_solicitud()+"'", "", null);
                row.moveToFirst();
                Intent i_geolocalizacion;
                switch (item.getTipo_form()) {
                    case 1:
                        i_geolocalizacion = new Intent(ctx, GeolocalizacionInd.class);
                        i_geolocalizacion.putExtra(Constants.NUM_SOLICITUD, item.getNum_solicitud());
                        i_geolocalizacion.putExtra(Constants._ID, item.getId());
                        i_geolocalizacion.putExtra(Constants.MODULO, modulo);
                        startActivity(i_geolocalizacion);
                        break;
                    case 2:
                        i_geolocalizacion = new Intent(ctx, GeolocalizacionGpo.class);
                        i_geolocalizacion.putExtra(Constants.NUM_SOLICITUD, item.getNum_solicitud());
                        i_geolocalizacion.putExtra(Constants._ID, item.getId());
                        i_geolocalizacion.putExtra(Constants.MODULO, modulo);
                        startActivity(i_geolocalizacion);
                        break;
                    default:
                        Toast.makeText(ctx, "No se encuentra registrado este formulario", Toast.LENGTH_SHORT).show();
                        break;
                }
            }

        });
        if (_m_geolocalizacion.size() > 0){
            rvGeolocalizacion.setAdapter(adapter);
            rvGeolocalizacion.setVisibility(View.VISIBLE);
            tvNoInfo.setVisibility(View.GONE);
        }
        else{
            rvGeolocalizacion.setVisibility(View.GONE);
            tvNoInfo.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_filtro_verde, menu);
        final MenuItem menuItem = menu.findItem(R.id.nvFiltro);
        View actionView = MenuItemCompat.getActionView(menuItem);
        tvContFiltros = actionView.findViewById(R.id.filtro_bagde);
        actionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOptionsItemSelected(menuItem);
            }
        });
        setupBadge();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nvFiltro:
                Filtros();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
