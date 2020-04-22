package com.sidert.sidertmovil.fragments.view_pager;

import android.content.ContentValues;
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
import android.widget.TextView;
import android.widget.Toast;

import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.OnClickListener;
import com.orhanobut.dialogplus.ViewHolder;
import com.sidert.sidertmovil.Home;
import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.activities.PrestamosClientes;
import com.sidert.sidertmovil.adapters.adapter_fichas_pendientes;
import com.sidert.sidertmovil.database.DBhelper;
import com.sidert.sidertmovil.fragments.dialogs.dialog_details_order;
import com.sidert.sidertmovil.models.MCartera;
import com.sidert.sidertmovil.models.MCarteraGnral;
import com.sidert.sidertmovil.models.ModeloFichaGeneral;
import com.sidert.sidertmovil.utils.NameFragments;
import com.sidert.sidertmovil.utils.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.sidert.sidertmovil.utils.Constants.ADDRESS;
import static com.sidert.sidertmovil.utils.Constants.ASSIGN_DATE;
import static com.sidert.sidertmovil.utils.Constants.AVAL;
import static com.sidert.sidertmovil.utils.Constants.CALLE;
import static com.sidert.sidertmovil.utils.Constants.CARTERA_VENCIDA_GPO;
import static com.sidert.sidertmovil.utils.Constants.CARTERA_VENCIDA_IND;
import static com.sidert.sidertmovil.utils.Constants.CLAVE_GRUPO;
import static com.sidert.sidertmovil.utils.Constants.CLIENTE;
import static com.sidert.sidertmovil.utils.Constants.COBRANZA_GPO;
import static com.sidert.sidertmovil.utils.Constants.COBRANZA_IND;
import static com.sidert.sidertmovil.utils.Constants.COLONIA;
import static com.sidert.sidertmovil.utils.Constants.DIA_SEMANA;
import static com.sidert.sidertmovil.utils.Constants.DIRECCION;
import static com.sidert.sidertmovil.utils.Constants.ENVIROMENT;
import static com.sidert.sidertmovil.utils.Constants.EXTERNAL_ID;
import static com.sidert.sidertmovil.utils.Constants.FECHA_PAGO_ESTABLECIDA;
import static com.sidert.sidertmovil.utils.Constants.FICHAS_T;
import static com.sidert.sidertmovil.utils.Constants.GRUPAL;
import static com.sidert.sidertmovil.utils.Constants.GRUPO;
import static com.sidert.sidertmovil.utils.Constants.ID_CARTERA;
import static com.sidert.sidertmovil.utils.Constants.INDIVIDUAL;
import static com.sidert.sidertmovil.utils.Constants.NOMBRE;
import static com.sidert.sidertmovil.utils.Constants.NOMBRE_GRUPO;
import static com.sidert.sidertmovil.utils.Constants.NUMERO_CLIENTE;
import static com.sidert.sidertmovil.utils.Constants.ORDER_ID;
import static com.sidert.sidertmovil.utils.Constants.PRESIDENTA;
import static com.sidert.sidertmovil.utils.Constants.PRESTAMO;
import static com.sidert.sidertmovil.utils.Constants.RECUPERACION_GPO;
import static com.sidert.sidertmovil.utils.Constants.RECUPERACION_IND;
import static com.sidert.sidertmovil.utils.Constants.REPORTE_ANALITICO_OMEGA;
import static com.sidert.sidertmovil.utils.Constants.SECRETARIA;
import static com.sidert.sidertmovil.utils.Constants.TABLA_PAGOS_CLIENTE;
import static com.sidert.sidertmovil.utils.Constants.TABLA_PAGOS_GRUPO;
import static com.sidert.sidertmovil.utils.Constants.TBL_CARTERA_GPO;
import static com.sidert.sidertmovil.utils.Constants.TBL_CARTERA_GPO_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_CARTERA_IND;
import static com.sidert.sidertmovil.utils.Constants.TBL_CARTERA_IND_T;
import static com.sidert.sidertmovil.utils.Constants.TESORERA;
import static com.sidert.sidertmovil.utils.Constants.TIPO;
import static com.sidert.sidertmovil.utils.Constants.TYPE;


public class fichas_pendientes_fragment extends Fragment{

    private Context ctx;
    private Home boostrap;
    private RecyclerView rvFichas;
    private adapter_fichas_pendientes adapter;
    private List<ModeloFichaGeneral> _m_fichaGeneral;
    //private ArrayList<ModeloIndividual> _m_individual;
    //private ArrayList<ModeloGrupal> _m_grupal;
    private HashMap<Integer, String> _fichas;

    private JSONObject fichas = null;

    private ModeloFichaGeneral mFichaGeneral;
    private MCarteraGnral mCarteraGeneral;
    private List<MCarteraGnral> _m_carteraGral;
    private SQLiteDatabase db;
    private DBhelper dBhelper;

    private SessionManager session;

    private TextView tvNoInfo;
    public TextView tvContFiltros;
    private AutoCompleteTextView aetNombre;
    private AutoCompleteTextView aetDia;
    private AutoCompleteTextView aetColonia;
    private AutoCompleteTextView aetAsesor;
    private CheckBox cbInd;
    private CheckBox cbGpo;
    private int cont_filtros = 0;

    private String[] dataNombre;
    private String[] dataDia;
    private String[] dataColonia;
    private String[] dataAsesor;
    private ArrayAdapter<String> adapterNombre;
    private ArrayAdapter<String> adapterDia;
    private ArrayAdapter<String> adapterColonia;
    private ArrayAdapter<String> adapterAsesor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fichas_pendientes, container,false);
        ctx = getContext();
        boostrap = (Home) getActivity();

        session = new SessionManager(ctx);

        dBhelper = new DBhelper(ctx);
        db = dBhelper.getWritableDatabase();

        tvNoInfo = view.findViewById(R.id.tvNoInfo);
        rvFichas = view.findViewById(R.id.rvFichas);

        rvFichas.setLayoutManager(new LinearLayoutManager(ctx));
        rvFichas.setHasFixedSize(false);

        _m_carteraGral = new ArrayList<>();

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);


        adapter = new adapter_fichas_pendientes(ctx, _m_carteraGral, new adapter_fichas_pendientes.Event() {
            @Override
            public void FichaOnClick(MCarteraGnral item) {

                Intent i_prestamos = new Intent(boostrap, PrestamosClientes.class);
                i_prestamos.putExtra(ID_CARTERA, item.getId_cliente());
                i_prestamos.putExtra(TIPO, item.getTipo());
                startActivity(i_prestamos);
            }

            @Override
            public void IsRutaOnClick(MCarteraGnral item, boolean is_ruta) {
                ContentValues cv = new ContentValues();
                String tbl = "";
                int ruta = 0;
                if (is_ruta)
                    ruta = 1;

                cv.put("is_ruta", ruta);

                switch (item.getTipo()){
                    case "INDIVIDUAL":
                        if (ENVIROMENT)
                            tbl = TBL_CARTERA_IND;
                        else
                            tbl = TBL_CARTERA_IND_T;
                        break;
                    case "GRUPAL":
                        if (ENVIROMENT)
                            tbl = TBL_CARTERA_GPO;
                        else
                            tbl = TBL_CARTERA_GPO_T;
                        break;
                }
                db.update(tbl, cv, "id_cartera = ?", new String[]{item.getId_cliente()});
            }
        });
        GetCartera("");


        rvFichas.setAdapter(adapter);
    }

    private boolean GetCartera(String where){
        Cursor row;
        _m_carteraGral = new ArrayList<>();

        String query = "";
        //Log.e("queryFichas", query);
        if (ENVIROMENT)
            query = "SELECT * FROM (SELECT id_cartera,nombre, direccion,is_ruta, ruta_obligado,dia,'' as tesorera,asesor_nombre,'INDIVIDUAL' AS tipo, colonia FROM " + TBL_CARTERA_IND + " UNION SELECT id_cartera,nombre, direccion,is_ruta, ruta_obligado,dia,tesorera,asesor_nombre,'GRUPAL' AS tipo, colonia FROM "+TBL_CARTERA_GPO +") AS cartera "+where;
        else
            query = "SELECT * FROM (SELECT id_cartera,nombre, direccion,is_ruta, ruta_obligado,dia,'' as tesorera,asesor_nombre,'INDIVIDUAL' AS tipo, colonia FROM " + TBL_CARTERA_IND_T + " UNION SELECT id_cartera,nombre, direccion,is_ruta, ruta_obligado,dia,tesorera,asesor_nombre,'GRUPAL' AS tipo, colonia FROM "+TBL_CARTERA_GPO_T +") AS cartera "+where;

        row = dBhelper.executeQuery(query);

        if (row.getCount() > 0){
            row.moveToFirst();
            dataNombre = new String[row.getCount()];
            dataDia = new String[row.getCount()];
            dataAsesor = new String[row.getCount()];
            List<String> nombre = new ArrayList<>();
            List<String> dia = new ArrayList<>();
            List<String> colonia = new ArrayList<>();
            List<String> asesor = new ArrayList<>();
            for (int i = 0; i < row.getCount(); i++){
                nombre.add(row.getString(1));
                dia.add(row.getString(5));
                colonia.add(row.getString(9));
                asesor.add(row.getString(7));
                mCarteraGeneral = new MCarteraGnral();
                mCarteraGeneral.setId_cliente(row.getString(0));
                mCarteraGeneral.setTipo(row.getString(8));
                mCarteraGeneral.setNombre(row.getString(1));
                mCarteraGeneral.setTesorera(row.getString(6));
                mCarteraGeneral.setDireccion(row.getString(2));
                mCarteraGeneral.setDiaSemana(row.getString(5));
                mCarteraGeneral.setIs_ruta(row.getInt(3)==1);
                mCarteraGeneral.setIs_obligatorio(row.getInt(4)==1);
                _m_carteraGral.add(mCarteraGeneral);
                row.moveToNext();
            }

            dataNombre = RemoverRepetidos(nombre);
            dataColonia = RemoverRepetidos(colonia);
            dataAsesor = RemoverRepetidos(asesor);
            dataDia = RemoverRepetidos(dia);

            adapterNombre = new ArrayAdapter<String>(ctx,
                    R.layout.custom_list_item, R.id.text_view_list_item, dataNombre);

            adapterDia = new ArrayAdapter<String>(ctx,
                    R.layout.custom_list_item, R.id.text_view_list_item, dataDia);

            adapterColonia = new ArrayAdapter<String>(ctx,
                    R.layout.custom_list_item, R.id.text_view_list_item, dataColonia);

            adapterAsesor = new ArrayAdapter<String>(ctx,
                    R.layout.custom_list_item, R.id.text_view_list_item, dataAsesor);
        }
        row.close();

        if(_m_carteraGral.size() > 0) {
            adapter.UpdateData(_m_carteraGral);
            rvFichas.setVisibility(View.VISIBLE);
            tvNoInfo.setVisibility(View.GONE);
        }
        else {
            rvFichas.setVisibility(View.GONE);
            tvNoInfo.setVisibility(View.VISIBLE);
        }

        return true;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_cartera, menu);
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
                Toast.makeText(ctx, "Estamos trabajando . . .", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.nvSynchronized:
                Toast.makeText(ctx, "Estamos trabajando . . .", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void Filtros (){
        DialogPlus filtros_dg = DialogPlus.newDialog(boostrap)
                .setContentHolder(new ViewHolder(R.layout.sheet_dialog_filtros_cartera))
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
                                    filtros.put("nombre_cartera_p",aetNombre.getText().toString().trim());
                                    cont_filtros += 1;
                                    where = " AND nombre LIKE '%"+aetNombre.getText().toString().trim()+"%'";
                                }
                                else filtros.put("nombre_cartera_p","");

                                if (!aetDia.getText().toString().trim().isEmpty()) {
                                    filtros.put("dia_semana_p",aetDia.getText().toString().trim());
                                    cont_filtros += 1;
                                    where += " AND dia LIKE '%" + aetDia.getText().toString().trim() + "%'";
                                }
                                else filtros.put("dia_semana_p","");

                                if (!aetColonia.getText().toString().trim().isEmpty()) {
                                    filtros.put("colonia_cartera_p",aetColonia.getText().toString().trim());
                                    cont_filtros += 1;
                                    where += " AND colonia LIKE '%" + aetColonia.getText().toString().trim() + "%'";
                                }
                                else filtros.put("colonia_cartera_p","");

                                if (!aetAsesor.getText().toString().trim().isEmpty()) {
                                    filtros.put("asesor_cartera_p",aetAsesor.getText().toString().trim());
                                    cont_filtros += 1;
                                    where += " AND asesor_nombre LIKE '%" + aetAsesor.getText().toString().trim() + "%'";
                                }
                                else filtros.put("asesor_cartera_p","");

                                if (cbInd.isChecked() && cbGpo.isChecked()){
                                    filtros.put("tipo_cartera_ind_p","1");
                                    filtros.put("tipo_cartera_gpo_p","1");
                                    cont_filtros += 2;
                                    where += " AND tipo IN ('INDIVIDUAL','GRUPAL')";
                                }
                                else if (cbInd.isChecked()){
                                    filtros.put("tipo_cartera_ind_p","1");
                                    filtros.put("tipo_cartera_gpo_p","0");
                                    cont_filtros += 1;
                                    where += " AND tipo = 'INDIVIDUAL'";
                                }
                                else if (cbGpo.isChecked()){
                                    filtros.put("tipo_cartera_ind_p","0");
                                    filtros.put("tipo_cartera_gpo_p","1");
                                    cont_filtros += 1;
                                    where += " AND tipo = 'GRUPAL'";
                                }else {
                                    filtros.put("tipo_cartera_ind_p","0");
                                    filtros.put("tipo_cartera_gpo_p","0");
                                }
                                filtros.put("contador_cartera_p", String.valueOf(cont_filtros));
                                session.setFiltrosCartera(filtros);

                                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                                Log.e("where",where);
                                if (where.length() > 4)
                                    GetCartera("WHERE" + where.substring(4));
                                else
                                    GetCartera("");
                                dialog.dismiss();

                                break;
                            case R.id.btnBorrarFiltros:

                                cont_filtros = 0;
                                filtros = new HashMap<>();
                                filtros.put("nombre_cartera_p","");
                                filtros.put("dia_semana_p","");
                                filtros.put("colonia_carteta_p","");
                                filtros.put("asesor_cartera_p","");
                                filtros.put("tipo_cartera_ind_p","0");
                                filtros.put("tipo_cartera_gpo_p","0");
                                filtros.put("contador_cartera_p", "0");

                                session.setFiltrosGeoPend(filtros);
                                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                                GetCartera("");
                                dialog.dismiss();

                                break;
                        }
                        setupBadge();

                    }
                })
                .setExpanded(true, 900)
                .create();
        aetNombre   = filtros_dg.getHolderView().findViewById(R.id.aetNombre);
        aetDia      = filtros_dg.getHolderView().findViewById(R.id.aetDia);
        aetColonia  = filtros_dg.getHolderView().findViewById(R.id.aetColonia);
        aetAsesor   = filtros_dg.getHolderView().findViewById(R.id.aetAsesor);
        cbInd       = filtros_dg.getHolderView().findViewById(R.id.cbInd);
        cbGpo       = filtros_dg.getHolderView().findViewById(R.id.cbGpo);

        aetNombre.setAdapter(adapterNombre);
        aetDia.setAdapter(adapterDia);
        aetColonia.setAdapter(adapterColonia);
        aetAsesor.setAdapter(adapterAsesor);

        aetDia.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                aetDia.showDropDown();
                return false;
            }
        });

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

        if (!session.getFiltrosCartera().get(2).isEmpty())
            aetNombre.setText(session.getFiltrosCartera().get(2));
        if (!session.getFiltrosCartera().get(3).isEmpty())
            aetDia.setText(session.getFiltrosCartera().get(3));
        if (!session.getFiltrosCartera().get(4).isEmpty())
            aetColonia.setText(session.getFiltrosCartera().get(4));
        if (!session.getFiltrosCartera().get(5).isEmpty())
            aetAsesor.setText(session.getFiltrosCartera().get(5));
        if (session.getFiltrosCartera().get(0).equals("1"))
            cbInd.setChecked(true);
        if (session.getFiltrosCartera().get(1).equals("1"))
            cbGpo.setChecked(true);
        filtros_dg.show();
    }

    private void setupBadge() {
        Log.v("contador",session.getFiltrosCartera().get(6));
        if (tvContFiltros != null) {
            Log.e("tvcontador", "visible");
            tvContFiltros.setText(String.valueOf(session.getFiltrosCartera().get(6)));
            tvContFiltros.setVisibility(View.VISIBLE);
        }

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

    @Override
    public void onResume() {
        super.onResume();
        Log.e("-----------","Resumen pendientes");
        setupBadge();
        String where = "";
        if (!session.getFiltrosCartera().get(2).isEmpty())
            where = " AND nombre LIKE '%" + session.getFiltrosCartera().get(2) + "%'";

        if (!session.getFiltrosCartera().get(3).isEmpty()) {
            where += " AND dia LIKE '%" + session.getFiltrosCartera().get(3) + "%'";
        }

        if (!session.getFiltrosCartera().get(4).isEmpty()) {
            where += " AND colonia LIKE '%" + session.getFiltrosCartera().get(4) + "%'";
        }

        if (!session.getFiltrosCartera().get(5).isEmpty()) {
            where += " AND asesor_nombre LIKE '%" + session.getFiltrosCartera().get(5) + "%'";
        }

        if (session.getFiltrosCartera().get(0).equals("1") && session.getFiltrosCartera().get(1).equals("1")){
            where += " AND tipo IN ('INDIVIDUAL','GRUPAL')";
        }
        else if (session.getFiltrosCartera().get(0).equals("1")){
            where += " AND tipo = 'INDIVIDUAL' ";
        }
        else if (session.getFiltrosCartera().get(1).equals("1")){
            where += " AND tipo = 'GRUPAL'";
        }

        if (where.length() > 4)
            GetCartera("WHERE" + where.substring(4));
        else
            GetCartera("");
    }
}



