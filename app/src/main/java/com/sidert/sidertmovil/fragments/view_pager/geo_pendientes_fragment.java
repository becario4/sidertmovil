package com.sidert.sidertmovil.fragments.view_pager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import androidx.annotation.Nullable;
/*import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;*/
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
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
import com.sidert.sidertmovil.fragments.geolocalizacion_fragment;
import com.sidert.sidertmovil.models.ModelGeolocalizacion;
import com.sidert.sidertmovil.utils.Constants;
import com.sidert.sidertmovil.utils.SessionManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.sidert.sidertmovil.utils.Constants.TBL_CARTERA_GPO_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_CARTERA_IND_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_GEO_RESPUESTAS_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_MIEMBROS_GPO_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_PRESTAMOS_GPO_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_PRESTAMOS_IND_T;

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
                Intent i_geolocalizacion;
                switch (item.getTipo_form()) {
                    case 1:
                        i_geolocalizacion = new Intent(ctx, GeolocalizacionInd.class);
                        i_geolocalizacion.putExtra(Constants.NUM_SOLICITUD, item.getNum_solicitud());
                        i_geolocalizacion.putExtra(Constants._ID, item.getId());
                        i_geolocalizacion.putExtra(Constants.MODULO, modulo);
                        i_geolocalizacion.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i_geolocalizacion);
                        break;
                    case 2:
                        i_geolocalizacion = new Intent(ctx, GeolocalizacionGpo.class);
                        i_geolocalizacion.putExtra(Constants.NUM_SOLICITUD, item.getNum_solicitud());
                        i_geolocalizacion.putExtra(Constants._ID, item.getId());
                        i_geolocalizacion.putExtra(Constants.MODULO, modulo);
                        i_geolocalizacion.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i_geolocalizacion);
                        break;
                    default:
                        Toast.makeText(ctx, "No se encuentra registrado este formulario", Toast.LENGTH_SHORT).show();
                        break;
                }
            }

        });

        //GetGeolocalizacion("");

        rvGeolocalizacion.setAdapter(adapter);

    }

    private void GetGeolocalizacion(String where) {
        Cursor row;

        String sql = "SELECT * FROM (SELECT ci.id_cartera, ci.clave, ci.nombre, ci.direccion, ci.colonia, ci.num_solicitud, ci.asesor_nombre, 1 AS tipo_ficha, 1 AS total_integrantes, 0 AS total_contestadas, COALESCE((SELECT COALESCE(g._id,'') AS id FROM "+TBL_GEO_RESPUESTAS_T+" AS g WHERE g.tipo_geolocalizacion = 'CLIENTE' AND g.id_cartera = ci.id_cartera), '') AS res_uno, COALESCE((SELECT COALESCE(g._id,'') AS id FROM "+TBL_GEO_RESPUESTAS_T+" AS g WHERE g.tipo_geolocalizacion = 'NEGOCIO' AND g.id_cartera = ci.id_cartera), '') AS res_dos, COALESCE((SELECT COALESCE(g._id,'') AS id FROM "+TBL_GEO_RESPUESTAS_T+" AS g WHERE g.tipo_geolocalizacion = 'AVAL' AND g.id_cartera = ci.id_cartera), '') AS res_tres FROM " + TBL_CARTERA_IND_T + " AS ci LEFT JOIN "+TBL_PRESTAMOS_IND_T+" AS pi ON pi.id_cliente = ci.id_cartera AND pi.tipo_cartera IN ('VIGENTE', 'AUTORIZADO')  UNION SELECT cg.id_cartera, cg.clave, cg.nombre, cg.direccion, cg.colonia, cg.num_solicitud, cg.asesor_nombre, 2 AS tipo_ficha, COUNT(m._id) AS total_integrantes, SUM(CASE WHEN gr._id IS NOT NULL THEN 1 ELSE 0 END) AS total_contestadas, COALESCE((SELECT COALESCE(g._id,'') AS id FROM "+TBL_GEO_RESPUESTAS_T+" AS g WHERE g.tipo_geolocalizacion = 'PRESIDENTE' AND g.id_cartera = cg.id_cartera), '') AS res_uno, COALESCE((SELECT COALESCE(g._id,'') AS id FROM "+TBL_GEO_RESPUESTAS_T+" AS g WHERE g.tipo_geolocalizacion = 'TESORERO' AND g.id_cartera = cg.id_cartera), '') AS res_dos, COALESCE((SELECT COALESCE(g._id,'') AS id FROM "+TBL_GEO_RESPUESTAS_T+" AS g WHERE g.tipo_geolocalizacion = 'SECRETARIO' AND g.id_cartera = cg.id_cartera), '') AS res_tres FROM " + TBL_CARTERA_GPO_T + " AS cg LEFT JOIN "+TBL_PRESTAMOS_GPO_T+" AS pg ON pg.id_grupo = cg.id_cartera AND pg.tipo_cartera IN ('VIGENTE', 'AUTORIZADO') LEFT JOIN "+TBL_MIEMBROS_GPO_T+" AS m ON m.id_prestamo = pg.id_prestamo LEFT JOIN "+TBL_GEO_RESPUESTAS_T+" AS gr ON gr.id_integrante = m.id_integrante GROUP BY cg.id_cartera, cg.clave, cg.nombre, cg.direccion, cg.colonia, cg.num_solicitud, cg.asesor_nombre ) AS geo_res" + where +" ORDER BY nombre ASC";

        row = db.rawQuery(sql, null);
        _m_geolocalizacion = new ArrayList<>();

        if (row.getCount() > 0) {
            row.moveToFirst();

            dataNombre = new String[row.getCount()];
            dataColonia = new String[row.getCount()];
            dataAsesor = new String[row.getCount()];
            List<String> nombre = new ArrayList<>();
            List<String> colonia = new ArrayList<>();
            List<String> asesor = new ArrayList<>();

            for (int i = 0; i < row.getCount(); i++) {

                switch (row.getInt(7)) { //Tipo de ficha
                    case 1: //Individuales
                        if (row.getString(10).trim().isEmpty() || row.getString(11).trim().isEmpty() || row.getString(12).trim().isEmpty()) {
                            nombre.add(row.getString(2));
                            colonia.add(row.getString(4));
                            asesor.add(row.getString(6));
                            mGeo = new ModelGeolocalizacion();
                            mGeo.setId(row.getInt(0));
                            mGeo.setAsesor_nombre(row.getString(6));
                            mGeo.setTipo_form(row.getInt(7));
                            mGeo.setNombre(row.getString(2));
                            mGeo.setNum_solicitud(row.getString(5));
                            mGeo.setDireccion(row.getString(3));
                            mGeo.setColonia(row.getString(4));
                            mGeo.setRes_uno(row.getString(10));
                            mGeo.setRes_dos(row.getString(11));
                            mGeo.setRes_tres(row.getString(12));
                            mGeo.setTotal_integrantes(row.getInt(8));
                            mGeo.setTotal_contestadas(row.getInt(9));
                            mGeo.setStatus(0);
                            _m_geolocalizacion.add(mGeo);
                        }

                        break;
                    case 2: //Grupales
                        if (row.getInt(9) < row.getInt(8)) {
                            nombre.add(row.getString(2));
                            colonia.add(row.getString(4));
                            asesor.add(row.getString(6));
                            mGeo = new ModelGeolocalizacion();
                            mGeo.setId(row.getInt(0));
                            mGeo.setAsesor_nombre(row.getString(6));
                            mGeo.setTipo_form(row.getInt(7));
                            mGeo.setNombre(row.getString(2));
                            mGeo.setNum_solicitud(row.getString(5));
                            mGeo.setDireccion(row.getString(3));
                            mGeo.setColonia(row.getString(4));
                            mGeo.setRes_uno(row.getString(10));
                            mGeo.setRes_dos(row.getString(11));
                            mGeo.setRes_tres(row.getString(12));
                            mGeo.setTotal_integrantes(row.getInt(8));
                            mGeo.setTotal_contestadas(row.getInt(9));
                            mGeo.setStatus(0);
                            _m_geolocalizacion.add(mGeo);
                        }
                        break;
                }

                row.moveToNext();
            }

            parent.SetUpBagde(0, _m_geolocalizacion.size());

            dataNombre = RemoverRepetidos(nombre);
            dataAsesor = RemoverRepetidos(asesor);
            dataColonia = RemoverRepetidos(colonia);

            adapterNombre = new ArrayAdapter<>(ctx,
                    R.layout.custom_list_item, R.id.text_view_list_item, dataNombre);

            adapterColonia = new ArrayAdapter<>(ctx,
                    R.layout.custom_list_item, R.id.text_view_list_item, dataColonia);

            adapterAsesor = new ArrayAdapter<>(ctx,
                    R.layout.custom_list_item, R.id.text_view_list_item, dataAsesor);
        }
        row.close();

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

    @SuppressLint("ClickableViewAccessibility")
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
                                    filtros.put("individual_p","1");
                                    filtros.put("grupal_p","1");
                                    cont_filtros += 2;
                                    where += " AND tipo_ficha IN (1,2)";
                                }
                                else if (cbInd.isChecked()){
                                    filtros.put("individual_p","1");
                                    filtros.put("grupal_p","0");
                                    cont_filtros += 1;
                                    where += " AND tipo_ficha = "+1;
                                }
                                else if (cbGpo.isChecked()){
                                    filtros.put("individual_p","0");
                                    filtros.put("grupal_p","1");
                                    cont_filtros += 1;
                                    where += " AND tipo_ficha = "+2;
                                }else {
                                    filtros.put("individual_p","0");
                                    filtros.put("grupal_p","0");
                                }
                                filtros.put("contador_p", String.valueOf(cont_filtros));
                                session.setFiltrosGeoPend(filtros);

                                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                                if (where.length() > 0)
                                    GetGeolocalizacion(" WHERE "+where.substring(5, where.length()));
                                else
                                    GetGeolocalizacion("");
                                dialog.dismiss();

                                break;
                            case R.id.btnBorrarFiltros:

                                cont_filtros = 0;
                                filtros = new HashMap<>();
                                filtros.put("nombre_cartera_p","");
                                filtros.put("colonia_cartera_p","");
                                filtros.put("dia_semana_p","");
                                filtros.put("asesor_cartera_p","");
                                filtros.put("individual_p","0");
                                filtros.put("grupal_p","0");
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
            @SuppressLint("ClickableViewAccessibility")
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

        Log.e("Where--->", where);

        if (where.length()> 0)
            GetGeolocalizacion(" WHERE "+where.substring(5, where.length()));
        else
            GetGeolocalizacion("");

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
