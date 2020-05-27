package com.sidert.sidertmovil.fragments.view_pager;


import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.ColorDrawable;
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
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.events.Event;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.OnClickListener;
import com.orhanobut.dialogplus.ViewHolder;
import com.sidert.sidertmovil.Home;
import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.activities.PrestamosClientes;
import com.sidert.sidertmovil.activities.ResumenCartera;
import com.sidert.sidertmovil.adapters.adapter_ficha_ruta;
import com.sidert.sidertmovil.adapters.adapter_fichas_pendientes;
import com.sidert.sidertmovil.database.DBhelper;
import com.sidert.sidertmovil.fragments.dialogs.dialog_details_order;
import com.sidert.sidertmovil.fragments.orders_fragment;
import com.sidert.sidertmovil.models.MCarteraGnral;
import com.sidert.sidertmovil.models.ModeloFichaGeneral;
import com.sidert.sidertmovil.utils.Constants;
import com.sidert.sidertmovil.utils.NameFragments;
import com.sidert.sidertmovil.utils.NetworkStatus;
import com.sidert.sidertmovil.utils.Popups;
import com.sidert.sidertmovil.utils.Servicios_Sincronizado;
import com.sidert.sidertmovil.utils.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.sidert.sidertmovil.utils.Constants.ADDRESS;
import static com.sidert.sidertmovil.utils.Constants.CALLE;
import static com.sidert.sidertmovil.utils.Constants.COLONIA;
import static com.sidert.sidertmovil.utils.Constants.DIRECCION;
import static com.sidert.sidertmovil.utils.Constants.ENVIROMENT;
import static com.sidert.sidertmovil.utils.Constants.ID_CARTERA;
import static com.sidert.sidertmovil.utils.Constants.NOMBRE;
import static com.sidert.sidertmovil.utils.Constants.TBL_CARTERA_GPO;
import static com.sidert.sidertmovil.utils.Constants.TBL_CARTERA_GPO_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_CARTERA_IND;
import static com.sidert.sidertmovil.utils.Constants.TBL_CARTERA_IND_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_PRESTAMOS_GPO;
import static com.sidert.sidertmovil.utils.Constants.TBL_PRESTAMOS_GPO_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_PRESTAMOS_IND;
import static com.sidert.sidertmovil.utils.Constants.TBL_PRESTAMOS_IND_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_RESPUESTAS_GPO;
import static com.sidert.sidertmovil.utils.Constants.TBL_RESPUESTAS_GPO_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_RESPUESTAS_IND;
import static com.sidert.sidertmovil.utils.Constants.TBL_RESPUESTAS_IND_T;
import static com.sidert.sidertmovil.utils.Constants.TIPO;

public class route_fragment extends Fragment{

    private Context ctx;
    private Home boostrap;
    private RecyclerView rvFichas;
    private adapter_fichas_pendientes adapter;
    private List<MCarteraGnral> _m_carteraGral;

    private MCarteraGnral mCarteraGnral;
    private SQLiteDatabase db;
    private DBhelper dBhelper;

    private SessionManager session;

    private TextView tvNoInfo;
    public TextView tvContFiltros;
    private AutoCompleteTextView aetNombre;
    private AutoCompleteTextView aetDia;
    private AutoCompleteTextView aetColonia;
    private Spinner spAsesor;
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

    private orders_fragment parent;
    List<String> asesor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_route, container, false);
        ctx = getContext();
        boostrap = (Home) getActivity();

        session = new SessionManager(ctx);

        parent = (orders_fragment) getParentFragment();

        dBhelper = new DBhelper(ctx);
        db = dBhelper.getWritableDatabase();

        rvFichas = v.findViewById(R.id.rvFichas);

        rvFichas.setLayoutManager(new LinearLayoutManager(ctx));
        rvFichas.setHasFixedSize(false);

        _m_carteraGral = new ArrayList<>();

        return v;
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
            public void IsRutaOnClick(MCarteraGnral item, boolean is_ruta, int pos) {
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

                if (!is_ruta) {
                    adapter.removeItem(pos);
                    parent.SetUpBagde(1, adapter.getItemCount());
                }

            }
        });

        GetCartera("");

        rvFichas.setAdapter(adapter);
    }

    private boolean GetCartera(String where){
        Cursor row;
        _m_carteraGral = new ArrayList<>();
        String query;
        if (ENVIROMENT)
            query = "SELECT * FROM (SELECT id_cartera,nombre,direccion,is_ruta,ruta_obligado,dia,'' AS tesorera,asesor_nombre,'INDIVIDUAL' AS tipo,colonia, pi.tipo_cartera, COALESCE(ri.estatus, -1) AS parcial FROM " + TBL_CARTERA_IND + " AS ci INNER JOIN "+ TBL_PRESTAMOS_IND + " AS pi ON ci.id_cartera = pi.id_cliente LEFT JOIN " + TBL_RESPUESTAS_IND + " AS ri ON pi.id_prestamo = ri.id_prestamo WHERE is_ruta = 1 AND (ri._id = (SELECT ri2._id FROM " + TBL_RESPUESTAS_IND + " AS ri2 WHERE ri2.id_prestamo = pi.id_prestamo ORDER BY ri2._id DESC LIMIT 1) OR ri._id IS NULL) UNION SELECT id_cartera,nombre, direccion,is_ruta, ruta_obligado,dia,tesorera,asesor_nombre,'GRUPAL' AS tipo, colonia, pg.tipo_cartera, COALESCE(rg.estatus, -1) AS parcial FROM "+TBL_CARTERA_GPO + " AS cg INNER JOIN " + TBL_PRESTAMOS_GPO + " AS pg ON cg.id_cartera = pg.id_grupo LEFT JOIN " + TBL_RESPUESTAS_GPO + " AS rg ON pg.id_prestamo = rg.id_prestamo WHERE is_ruta = 1 AND (rg._id = (SELECT rg2._id FROM " + TBL_RESPUESTAS_GPO + " AS rg2 WHERE rg2.id_prestamo = pg.id_prestamo ORDER BY rg2._id DESC LIMIT 1) OR rg._id IS NULL)) AS cartera " + where;
        else
            query = "SELECT * FROM (SELECT id_cartera,nombre,direccion,is_ruta,ruta_obligado,dia,'' AS tesorera,asesor_nombre,'INDIVIDUAL' AS tipo,colonia, pi.tipo_cartera, COALESCE(ri.estatus, -1) AS parcial FROM " + TBL_CARTERA_IND_T + " AS ci INNER JOIN "+ TBL_PRESTAMOS_IND_T + " AS pi ON ci.id_cartera = pi.id_cliente LEFT JOIN " + TBL_RESPUESTAS_IND_T + " AS ri ON pi.id_prestamo = ri.id_prestamo WHERE is_ruta = 1 AND (ri._id = (SELECT ri2._id FROM " + TBL_RESPUESTAS_IND_T + " AS ri2 WHERE ri2.id_prestamo = pi.id_prestamo ORDER BY ri2._id DESC LIMIT 1) OR ri._id IS NULL) UNION SELECT id_cartera,nombre, direccion,is_ruta, ruta_obligado,dia,tesorera,asesor_nombre,'GRUPAL' AS tipo, colonia, pg.tipo_cartera, COALESCE(rg.estatus, -1) AS parcial FROM "+TBL_CARTERA_GPO_T + " AS cg INNER JOIN " + TBL_PRESTAMOS_GPO_T + " AS pg ON cg.id_cartera = pg.id_grupo LEFT JOIN " + TBL_RESPUESTAS_GPO_T + " AS rg ON pg.id_prestamo = rg.id_prestamo WHERE is_ruta = 1 AND (rg._id = (SELECT rg2._id FROM " + TBL_RESPUESTAS_GPO_T + " AS rg2 WHERE rg2.id_prestamo = pg.id_prestamo ORDER BY rg2._id DESC LIMIT 1) OR rg._id IS NULL)) AS cartera " + where;

        row = db.rawQuery(query,null);

        parent.SetUpBagde(1, row.getCount());
        asesor = new ArrayList<>();
        asesor.add("");
        if (row.getCount() > 0){
            row.moveToFirst();
            dataNombre = new String[row.getCount()];
            dataDia = new String[row.getCount()];
            dataAsesor = new String[row.getCount()];
            List<String> nombre = new ArrayList<>();
            List<String> dia = new ArrayList<>();
            List<String> colonia = new ArrayList<>();

            for (int i = 0; i < row.getCount(); i++){
                nombre.add(row.getString(1));
                dia.add(row.getString(5));
                colonia.add(row.getString(9));
                asesor.add(row.getString(7));
                mCarteraGnral = new MCarteraGnral();
                mCarteraGnral.setId_cliente(row.getString(0));
                mCarteraGnral.setNombre(row.getString(1));
                mCarteraGnral.setDireccion(row.getString(2));
                mCarteraGnral.setIs_ruta(row.getInt(3)==1);
                mCarteraGnral.setIs_obligatorio(row.getInt(4)==1);
                mCarteraGnral.setDiaSemana(row.getString(5));
                mCarteraGnral.setTesorera(row.getString(6));
                mCarteraGnral.setTipo(row.getString(8));
                mCarteraGnral.setTipoPrestamo(row.getString(10));
                mCarteraGnral.setParcial(row.getInt(11));

                row.moveToNext();
                _m_carteraGral.add(mCarteraGnral);
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
        else{
            dataAsesor = new String[1];
            dataAsesor[0] = "";
            adapterAsesor = new ArrayAdapter<String>(ctx,
                    R.layout.custom_list_item, R.id.text_view_list_item, dataAsesor);
        }
        row.close();

        if(_m_carteraGral.size() > 0) {
            Log.e("as","................................");
            adapter.UpdateData(_m_carteraGral);
            rvFichas.setVisibility(View.VISIBLE);
            //tvNoInfo.setVisibility(View.GONE);
        }
        else {
            Log.e("as",",,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,");
            rvFichas.setVisibility(View.GONE);
            //tvNoInfo.setVisibility(View.VISIBLE);
        }

        return true;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        onResume();
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
                //Toast.makeText(ctx, "Estamos trabajando . . .", Toast.LENGTH_SHORT).show();
                Intent i_resumen = new Intent(boostrap, ResumenCartera.class);
                startActivity(i_resumen);
                return true;
            case R.id.nvSynchronized:
                if (NetworkStatus.haveNetworkConnection(ctx)) {
                    Servicios_Sincronizado ss = new Servicios_Sincronizado();
                    ss.SaveRespuestaGestion(boostrap, true);
                }
                else{
                    final AlertDialog error_network = Popups.showDialogMessage(boostrap, Constants.not_network,
                            R.string.not_network, R.string.accept, new Popups.DialogMessage() {
                                @Override
                                public void OnClickListener(AlertDialog dialog) {
                                    dialog.dismiss();
                                }
                            });
                    error_network.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                    error_network.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                    error_network.show();
                }
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
                                    filtros.put("nombre_cartera_r",aetNombre.getText().toString().trim());
                                    cont_filtros += 1;
                                    where = " AND nombre LIKE '%"+aetNombre.getText().toString().trim()+"%'";
                                }
                                else filtros.put("nombre_cartera_r","");

                                if (!aetDia.getText().toString().trim().isEmpty()) {
                                    filtros.put("dia_semana_r",aetDia.getText().toString().trim());
                                    cont_filtros += 1;
                                    where += " AND dia LIKE '%" + aetDia.getText().toString().trim() + "%'";
                                }
                                else filtros.put("dia_semana_r","");

                                if (!aetColonia.getText().toString().trim().isEmpty()) {
                                    filtros.put("colonia_cartera_r",aetColonia.getText().toString().trim());
                                    cont_filtros += 1;
                                    where += " AND colonia LIKE '%" + aetColonia.getText().toString().trim() + "%'";
                                }
                                else filtros.put("colonia_cartera_r","");

                                if (!spAsesor.getSelectedItem().toString().trim().isEmpty()) {
                                    filtros.put("asesor_cartera_r",spAsesor.getSelectedItemPosition()+"");
                                    cont_filtros += 1;
                                    where += " AND asesor_nombre LIKE '%" + spAsesor.getSelectedItem().toString().trim() + "%'";
                                }
                                else filtros.put("asesor_cartera_r","0");

                                if (cbInd.isChecked() && cbGpo.isChecked()){
                                    filtros.put("tipo_cartera_ind_r","1");
                                    filtros.put("tipo_cartera_gpo_r","1");
                                    cont_filtros += 2;
                                    where += " AND tipo IN ('INDIVIDUAL','GRUPAL')";
                                }
                                else if (cbInd.isChecked()){
                                    filtros.put("tipo_cartera_ind_r","1");
                                    filtros.put("tipo_cartera_gpo_r","0");
                                    cont_filtros += 1;
                                    where += " AND tipo = 'INDIVIDUAL'";
                                }
                                else if (cbGpo.isChecked()){
                                    filtros.put("tipo_cartera_ind_r","0");
                                    filtros.put("tipo_cartera_gpo_r","1");
                                    cont_filtros += 1;
                                    where += " AND tipo = 'GRUPAL'";
                                }else {
                                    filtros.put("tipo_cartera_ind_r","0");
                                    filtros.put("tipo_cartera_gpo_r","0");
                                }
                                filtros.put("contador_cartera_r", String.valueOf(cont_filtros));
                                session.setFiltrosCarteraRuta(filtros);

                                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                                Log.e("where",where);
                                if (where.length() > 4)
                                    GetCartera("WHERE" + where.substring(4));
                                else
                                    GetCartera("");
                                dialog.dismiss();

                                break;
                            case R.id.btnBorrarFiltros:
                                cbInd.setChecked(false);
                                cbGpo.setChecked(false);
                                aetNombre.setText("");
                                aetDia.setText("");
                                aetColonia.setText("");
                                spAsesor.setSelection(0);
                                cont_filtros = 0;
                                filtros = new HashMap<>();
                                filtros.put("nombre_cartera_r","");
                                filtros.put("dia_semana_r","");
                                filtros.put("colonia_carteta_r","");
                                filtros.put("asesor_cartera_r","0");
                                filtros.put("tipo_cartera_ind_r","0");
                                filtros.put("tipo_cartera_gpo_r","0");
                                filtros.put("contador_cartera_r", "0");
                                session.setFiltrosCarteraRuta(filtros);

                                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                                GetCartera("");
                                //dialog.dismiss();

                                aetNombre.setAdapter(adapterNombre);
                                aetDia.setAdapter(adapterDia);
                                aetColonia.setAdapter(adapterColonia);
                                spAsesor.setAdapter(adapterAsesor);

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
        spAsesor    = filtros_dg.getHolderView().findViewById(R.id.spAsesor);
        cbInd       = filtros_dg.getHolderView().findViewById(R.id.cbInd);
        cbGpo       = filtros_dg.getHolderView().findViewById(R.id.cbGpo);

        try {
            aetNombre.setAdapter(adapterNombre);
            aetDia.setAdapter(adapterDia);
            aetColonia.setAdapter(adapterColonia);
            spAsesor.setAdapter(adapterAsesor);
        }catch (Exception e){

        }


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

        try {
            if (!session.getFiltrosCarteraRuta().get(2).isEmpty())
                aetNombre.setText(session.getFiltrosCarteraRuta().get(2));
            if (!session.getFiltrosCarteraRuta().get(3).isEmpty())
                aetDia.setText(session.getFiltrosCarteraRuta().get(3));
            if (!session.getFiltrosCarteraRuta().get(4).isEmpty())
                aetColonia.setText(session.getFiltrosCarteraRuta().get(4));
            if (!session.getFiltrosCarteraRuta().get(5).isEmpty())
                spAsesor.setSelection(Integer.parseInt(session.getFiltrosCarteraRuta().get(5)));
            if (session.getFiltrosCarteraRuta().get(0).equals("1"))
                cbInd.setChecked(true);
            if (session.getFiltrosCarteraRuta().get(1).equals("1"))
                cbGpo.setChecked(true);
        }catch (Exception e){

        }

        filtros_dg.show();
    }

    private void setupBadge() {
        Log.v("contador ruta",session.getFiltrosCarteraRuta().get(6));
        if (tvContFiltros != null) {
            Log.e("tvcontador", "visible");
            tvContFiltros.setText(String.valueOf(session.getFiltrosCarteraRuta().get(6)));
            tvContFiltros.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onResume() {
        super.onResume();

        setupBadge();
        String where = "";
        if (!session.getFiltrosCarteraRuta().get(2).isEmpty())
            where = " AND nombre LIKE '%" + session.getFiltrosCarteraRuta().get(2) + "%'";

        if (!session.getFiltrosCarteraRuta().get(3).isEmpty()) {
            where += " AND dia LIKE '%" + session.getFiltrosCarteraRuta().get(3) + "%'";
        }

        if (!session.getFiltrosCarteraRuta().get(4).isEmpty()) {
            where += " AND colonia LIKE '%" + session.getFiltrosCarteraRuta().get(4) + "%'";
        }

        if (!session.getFiltrosCarteraRuta().get(5).isEmpty() && Integer.parseInt(session.getFiltrosCarteraRuta().get(5)) > 0) {
            where += " AND asesor_nombre LIKE '%" + session.getFiltrosCarteraRuta().get(5) + "%'";
        }

        if (session.getFiltrosCarteraRuta().get(0).equals("1") && session.getFiltrosCarteraRuta().get(1).equals("1")){
            where += " AND tipo IN ('INDIVIDUAL','GRUPAL')";
        }
        else if (session.getFiltrosCarteraRuta().get(0).equals("1")){
            where += " AND tipo = 'INDIVIDUAL' ";
        }
        else if (session.getFiltrosCarteraRuta().get(1).equals("1")){
            where += " AND tipo = 'GRUPAL'";
        }

        if (where.length() > 4)
            GetCartera("WHERE" + where.substring(4));
        else
            GetCartera("");
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
