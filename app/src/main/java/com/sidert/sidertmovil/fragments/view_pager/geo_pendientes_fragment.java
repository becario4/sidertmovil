package com.sidert.sidertmovil.fragments.view_pager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
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
import com.sidert.sidertmovil.activities.ResumenGeo;
import com.sidert.sidertmovil.adapters.adapter_geo_pendientes;
import com.sidert.sidertmovil.database.DBhelper;
import com.sidert.sidertmovil.database.SidertTables;
import com.sidert.sidertmovil.fragments.geolocalizacion_fragment;
import com.sidert.sidertmovil.models.ModelGeolocalizacion;
import com.sidert.sidertmovil.models.ModeloGeolocalizacion;
import com.sidert.sidertmovil.utils.Constants;
import com.sidert.sidertmovil.utils.ManagerInterface;
import com.sidert.sidertmovil.utils.Miscellaneous;
import com.sidert.sidertmovil.utils.Popups;
import com.sidert.sidertmovil.utils.RetrofitClient;
import com.sidert.sidertmovil.utils.SessionManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class geo_pendientes_fragment extends Fragment {

    private Context ctx;
    private Home boostrap;
    private RecyclerView rvGeolocalizacion;
    private adapter_geo_pendientes adapter;
    private List<ModelGeolocalizacion> _m_geolocalizacion;

    private HashMap<Integer, String> _geolocalizacion;

    private JSONObject geolocalizacion = null;

    private ModelGeolocalizacion mGeo;
    private SQLiteDatabase db;
    private DBhelper dBhelper;

    private TextView tvNoInfo;
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

    private geolocalizacion_fragment parent;
    private JSONArray nameAsesor;

    private SessionManager session;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_geo_pendientes, container,false);
        ctx = getContext();
        boostrap = (Home) getActivity();
        session = new SessionManager(ctx);

        parent = (geolocalizacion_fragment)getParentFragment();

        dBhelper = new DBhelper(ctx);
        db = dBhelper.getWritableDatabase();

        tvNoInfo            = view.findViewById(R.id.tvNoInfo);
        rvGeolocalizacion = view.findViewById(R.id.rvGeolocalizacion);

        rvGeolocalizacion.setLayoutManager(new LinearLayoutManager(ctx));
        rvGeolocalizacion.setHasFixedSize(false);

        parentLayout = view.findViewById(R.id.activity_use);
        _m_geolocalizacion = new ArrayList<>();

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);

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

        Cursor row;
        if (Constants.ENVIROMENT)
            row = dBhelper.getRecords(Constants.GEOLOCALIZACION, " WHERE status < 3", "", null);
        else
            row = dBhelper.getRecords(Constants.GEOLOCALIZACION_T, " WHERE status < 3", "", null);

        Log.e("contRows", String.valueOf(row.getCount()));

        if (row.getCount() == 0){
            if (!Miscellaneous.JobServiceEnable(ctx, Constants.ID_JOB_SINCRONIZADO, "SINCRONIZADO")) {
                GuardarGeolocalizacion();
            }
            else{
                GuardarGeolocalizacion();
            }
        }
        else {
            GetGeolocalizacion("");
        }


        rvGeolocalizacion.setAdapter(adapter);

    }

    private void GuardarGeolocalizacion (){

        final AlertDialog loading = Popups.showLoadingDialog(ctx,R.string.please_wait, R.string.loading_info);
        loading.show();

        ManagerInterface api = new RetrofitClient().generalRF(Constants.CONTROLLER_FICHAS).create(ManagerInterface.class);

        Call<ModeloGeolocalizacion> call = api.getGeolocalizcion("1",
                                                                false,
                                                                "Bearer "+ session.getUser().get(7));
        call.enqueue(new Callback<ModeloGeolocalizacion>() {
            @Override
            public void onResponse(Call<ModeloGeolocalizacion> call, Response<ModeloGeolocalizacion> response) {

                Log.e("CODE GETGEO", String.valueOf(response.code()));
                switch (response.code()) {
                    case 200:
                        ModeloGeolocalizacion modeloGeo = response.body();
                        HashMap<Integer, String> params;
                        if (modeloGeo.getGrupales().size() > 0){
                            for (int i = 0; i < modeloGeo.getGrupales().size(); i++){
                                Cursor rowGeo;
                                if (Constants.ENVIROMENT)
                                    rowGeo = dBhelper.getRecords(SidertTables.SidertEntry.TABLE_GEOLOCALIZACION, " WHERE ficha_id = '"+modeloGeo.getGrupales().get(i).getFicha_id()+"'", "", null);
                                else
                                    rowGeo = dBhelper.getRecords(SidertTables.SidertEntry.TABLE_GEOLOCALIZACION_T, " WHERE ficha_id = '"+modeloGeo.getGrupales().get(i).getFicha_id()+"'", "", null);
                                if (rowGeo.getCount() == 0) {
                                    Log.e("item", "gruaples "+i+" "+modeloGeo.getGrupales().get(i).getGrupoNombre());
                                    params = new HashMap<>();
                                    params.put(0, String.valueOf(modeloGeo.getGrupales().get(i).getFicha_id()));
                                    params.put(1, modeloGeo.getGrupales().get(i).getAsesor_nombre());
                                    params.put(2, "2");
                                    params.put(3, modeloGeo.getGrupales().get(i).getGrupoNombre());
                                    params.put(4, String.valueOf(modeloGeo.getGrupales().get(i).getNumSolicitud()));
                                    params.put(5, String.valueOf(modeloGeo.getGrupales().get(i).getGrupoId()));
                                    params.put(6, String.valueOf(modeloGeo.getGrupales().get(i).getGrupoId()));
                                    params.put(7, Miscellaneous.GetIntegrante(modeloGeo.getGrupales().get(i).getIntegrantes(), "TESORERO").getClienteDireccion());
                                    params.put(8, Miscellaneous.GetIntegrante(modeloGeo.getGrupales().get(i).getIntegrantes(), "TESORERO").getClienteColonia());
                                    params.put(9, modeloGeo.getGrupales().get(i).getFechaEntrega());
                                    params.put(10, modeloGeo.getGrupales().get(i).getFechaVencimiento());
                                    params.put(11, Miscellaneous.JsonConvertGpo(modeloGeo.getGrupales().get(i)));
                                    params.put(12, "");
                                    params.put(13, "");
                                    params.put(14, "");
                                    params.put(15, "");
                                    params.put(16, "");
                                    params.put(17, "");
                                    params.put(18, "");
                                    params.put(19, "");
                                    params.put(20, "0");
                                    params.put(21, Miscellaneous.ObtenerFecha("timestamp"));
                                    if (Constants.ENVIROMENT)
                                        dBhelper.saveRecordsGeo(db, SidertTables.SidertEntry.TABLE_GEOLOCALIZACION, params);
                                    else
                                        dBhelper.saveRecordsGeo(db, SidertTables.SidertEntry.TABLE_GEOLOCALIZACION_T, params);
                                }

                            }
                        }

                        if (modeloGeo.getIndividuales().size() > 0){
                            for (int i = 0; i < modeloGeo.getIndividuales().size(); i++){
                                Cursor rowGeo;
                                if (Constants.ENVIROMENT)
                                    rowGeo = dBhelper.getRecords(SidertTables.SidertEntry.TABLE_GEOLOCALIZACION, " WHERE ficha_id = '"+modeloGeo.getIndividuales().get(i).getFicha_id()+"'", "", null);
                                else
                                    rowGeo = dBhelper.getRecords(SidertTables.SidertEntry.TABLE_GEOLOCALIZACION_T, " WHERE ficha_id = '"+modeloGeo.getIndividuales().get(i).getFicha_id()+"'", "", null);
                                if (rowGeo.getCount() == 0) {
                                    params = new HashMap<>();
                                    params.put(0, String.valueOf(modeloGeo.getIndividuales().get(i).getFicha_id()));
                                    params.put(1, modeloGeo.getIndividuales().get(i).getAsesor_nombre());
                                    params.put(2, "1");
                                    params.put(3, modeloGeo.getIndividuales().get(i).getClienteNombre());
                                    params.put(4, String.valueOf(modeloGeo.getIndividuales().get(i).getNumSolicitud()));
                                    params.put(5, String.valueOf(modeloGeo.getIndividuales().get(i).getClienteId()));
                                    params.put(6, String.valueOf(modeloGeo.getIndividuales().get(i).getClienteClave()));
                                    params.put(7, modeloGeo.getIndividuales().get(i).getClienteDireccion());
                                    params.put(8, modeloGeo.getIndividuales().get(i).getClienteColonia());
                                    params.put(9, modeloGeo.getIndividuales().get(i).getFechaEntrega());
                                    params.put(10, modeloGeo.getIndividuales().get(i).getFechaVencimiento());
                                    params.put(11, Miscellaneous.JsonConvertInd(modeloGeo.getIndividuales().get(i)));
                                    params.put(12, "");
                                    params.put(13, "");
                                    params.put(14, "");
                                    params.put(15, "");
                                    params.put(16, "");
                                    params.put(17, "");
                                    params.put(18, "");
                                    params.put(19, "");
                                    params.put(20, "0");
                                    params.put(21, Miscellaneous.ObtenerFecha("timestamp"));
                                    if (Constants.ENVIROMENT)
                                        dBhelper.saveRecordsGeo(db, SidertTables.SidertEntry.TABLE_GEOLOCALIZACION, params);
                                    else
                                        dBhelper.saveRecordsGeo(db, SidertTables.SidertEntry.TABLE_GEOLOCALIZACION_T, params);
                                }
                            }
                        }

                        GetGeolocalizacion("");
                        break;
                    default:
                        break;
                }

                loading.dismiss();
            }

            @Override
            public void onFailure(Call<ModeloGeolocalizacion> call, Throwable t) {

            }
        });
    }

    private void GetGeolocalizacion(String where) {
        Cursor row;

        if (Constants.ENVIROMENT)
            row = dBhelper.getRecords(Constants.GEOLOCALIZACION, " WHERE status in (0,1,2)" + where, " ORDER BY nombre ASC", null);
        else
            row = dBhelper.getRecords(Constants.GEOLOCALIZACION_T, " WHERE status in (0,1,2)" + where, " ORDER BY nombre ASC", null);
        _m_geolocalizacion = new ArrayList<>();
        Log.e("rowGet", String.valueOf(row.getCount()));
        row.moveToFirst();
        parent.SetUpBagde(0, row.getCount());
        dataNombre = new String[row.getCount()];
        dataColonia = new String[row.getCount()];
        dataAsesor = new String[row.getCount()];
        List<String> nombre = new ArrayList<>();
        List<String> colonia = new ArrayList<>();
        List<String> asesor = new ArrayList<>();
        for (int i = 0; i < row.getCount(); i++){
            nombre.add(row.getString(4));
            colonia.add(row.getString(9));
            asesor.add(row.getString(2));
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
            mGeo.setStatus(Integer.parseInt(row.getString(21)));
            _m_geolocalizacion.add(mGeo);
            row.moveToNext();
        }

        dataNombre = RemoverRepetidos(nombre);
        dataAsesor = RemoverRepetidos(asesor);
        dataColonia = RemoverRepetidos(colonia);

        adapterNombre = new ArrayAdapter<String>(ctx,
                R.layout.custom_list_item, R.id.text_view_list_item, dataNombre);

        adapterColonia = new ArrayAdapter<String>(ctx,
                R.layout.custom_list_item, R.id.text_view_list_item, dataColonia);

        adapterAsesor = new ArrayAdapter<String>(ctx,
                R.layout.custom_list_item, R.id.text_view_list_item, dataAsesor);

        if(_m_geolocalizacion.size() > 0) {
            adapter.UpdateData(_m_geolocalizacion);
            rvGeolocalizacion.setVisibility(View.VISIBLE);
            tvNoInfo.setVisibility(View.GONE);
        }
        else {
            rvGeolocalizacion.setVisibility(View.GONE);
            tvNoInfo.setVisibility(View.VISIBLE);
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
                                    filtros.put("nombre_p",aetNombre.getText().toString().trim());
                                    cont_filtros += 1;
                                    where = " AND nombre LIKE '%"+aetNombre.getText().toString().trim()+"%'";
                                }
                                else filtros.put("nombre_p","");

                                if (!aetColonia.getText().toString().trim().isEmpty()) {
                                    filtros.put("colonia_p",aetColonia.getText().toString().trim());
                                    cont_filtros += 1;
                                    where += " AND colonia LIKE '%" + aetColonia.getText().toString().trim() + "%'";
                                }
                                else filtros.put("colonia_p","");

                                if (!aetAsesor.getText().toString().trim().isEmpty()) {
                                    filtros.put("asesor_p",aetAsesor.getText().toString().trim());
                                    cont_filtros += 1;
                                    where += " AND asesor_nombre LIKE '%" + aetAsesor.getText().toString().trim() + "%'";
                                }
                                else filtros.put("asesor_p","");

                                if (cbInd.isChecked() && cbGpo.isChecked()){
                                    filtros.put("tipo_cartera_ind_p","1");
                                    filtros.put("tipo_cartera_gpo_p","1");
                                    cont_filtros += 2;
                                    where += " AND tipo_ficha IN (1,2)";
                                }
                                else if (cbInd.isChecked()){
                                    filtros.put("tipo_cartera_ind_p","1");
                                    filtros.put("tipo_cartera_gpo_p","0");
                                    cont_filtros += 1;
                                    where += " AND tipo_ficha = "+1;
                                }
                                else if (cbGpo.isChecked()){
                                    filtros.put("tipo_cartera_ind_p","0");
                                    filtros.put("tipo_cartera_gpo_p","1");
                                    cont_filtros += 1;
                                    where += " AND tipo_ficha = "+2;
                                }else {
                                    filtros.put("tipo_cartera_ind_p","0");
                                    filtros.put("tipo_cartera_gpo_p","0");
                                }
                                filtros.put("contador_p", String.valueOf(cont_filtros));
                                session.setFiltrosGeoPend(filtros);

                                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                                GetGeolocalizacion(where);
                                dialog.dismiss();

                                break;
                            case R.id.btnBorrarFiltros:

                                cont_filtros = 0;
                                filtros = new HashMap<>();
                                filtros.put("nombre_cartera_p","");
                                filtros.put("colonia_cartera_p","");
                                filtros.put("dia_semana_p","");
                                filtros.put("asesor_cartera_p","");
                                filtros.put("tipo_cartera_ind_p","0");
                                filtros.put("tipo_cartera_gpo_p","0");
                                filtros.put("contador_cartera_p", "0");

                                session.setFiltrosGeoPend(filtros);
                                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                                GetGeolocalizacion("");
                                dialog.dismiss();

                                break;
                        }
                        setupBadge();

                    }
                })
                .setExpanded(true, 900)
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

        if (!session.getFiltrosGeoPend().get(0).isEmpty())
            aetNombre.setText(session.getFiltrosGeoPend().get(0));
        if (!session.getFiltrosGeoPend().get(1).isEmpty())
            aetColonia.setText(session.getFiltrosGeoPend().get(1));
        if (!session.getFiltrosGeoPend().get(2).isEmpty())
            aetAsesor.setText(session.getFiltrosGeoPend().get(2));
        if (session.getFiltrosGeoPend().get(3).equals("1"))
            cbInd.setChecked(true);
        if (session.getFiltrosGeoPend().get(4).equals("1"))
            cbGpo.setChecked(true);
        filtros_dg.show();
    }

    private void setupBadge() {
        Log.v("contador",session.getFiltrosGeoPend().get(5));
        if (tvContFiltros != null) {
            Log.e("tvcontador", "visible");
            tvContFiltros.setText(String.valueOf(session.getFiltrosGeoPend().get(5)));
            tvContFiltros.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_filtro, menu);
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
            case R.id.nvInfo:
                Intent i_resumen = new Intent(boostrap, ResumenGeo.class);
                startActivity(i_resumen);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        setupBadge();
        String where = "";
        if (!session.getFiltrosGeoPend().get(0).isEmpty())
            where = " AND nombre LIKE '%" + session.getFiltrosGeoPend().get(0) + "%'";

        if (!session.getFiltrosGeoPend().get(1).isEmpty()) {
            where += " AND colonia LIKE '%" + session.getFiltrosGeoPend().get(1) + "%'";
        }

        if (!session.getFiltrosGeoPend().get(2).isEmpty()) {
            where += " AND asesor_nombre LIKE '%" + session.getFiltrosGeoPend().get(2) + "%'";
        }

        if (session.getFiltrosGeoPend().get(3).equals("1") && session.getFiltrosGeoPend().get(4).equals("1")){
            where += " AND tipo_ficha IN (1,2)";
        }
        else if (session.getFiltrosGeoPend().get(3).equals("1")){
            where += " AND tipo_ficha = "+1;
        }
        else if (session.getFiltrosGeoPend().get(4).equals("1")){
            where += " AND tipo_ficha = "+2;
        }

        GetGeolocalizacion(where);

    }

    private String[] RemoverRepetidos(List<String> nombres){
        String[] data;
        List<String> nombreUnico = new ArrayList<>();

        for (int i = 0; i < nombres.size(); i++){
            String nombre = nombres.get(i);
            if (nombreUnico.indexOf(nombre) < 0) {
                nombreUnico.add(nombre);
            }
        }

        data = new String[nombreUnico.size()];
        for (int j = 0; j < nombreUnico.size(); j++){
            data[j] = nombreUnico.get(j);
        }

        return data;
    }
}
