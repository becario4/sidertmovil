package com.sidert.sidertmovil.fragments.view_pager;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.OnClickListener;
import com.orhanobut.dialogplus.ViewHolder;
import com.sidert.sidertmovil.Home;
import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.activities.MisCierresDeDia;
import com.sidert.sidertmovil.activities.PrestamosClientes;
import com.sidert.sidertmovil.activities.ResumenCartera;
import com.sidert.sidertmovil.adapters.adapter_fichas_pendientes;
import com.sidert.sidertmovil.database.DBhelper;
import com.sidert.sidertmovil.fragments.orders_fragment;
import com.sidert.sidertmovil.models.MCarteraGnral;
import com.sidert.sidertmovil.utils.Constants;
import com.sidert.sidertmovil.utils.Miscellaneous;
import com.sidert.sidertmovil.utils.NetworkStatus;
import com.sidert.sidertmovil.utils.Popups;
import com.sidert.sidertmovil.utils.Servicios_Sincronizado;
import com.sidert.sidertmovil.utils.SessionManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import static com.sidert.sidertmovil.utils.Constants.ENVIROMENT;
import static com.sidert.sidertmovil.utils.Constants.ID_CARTERA;
import static com.sidert.sidertmovil.utils.Constants.TBL_CARTERA_GPO;
import static com.sidert.sidertmovil.utils.Constants.TBL_CARTERA_GPO_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_CARTERA_IND;
import static com.sidert.sidertmovil.utils.Constants.TBL_CARTERA_IND_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_CIERRE_DIA_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_PRESTAMOS_GPO_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_PRESTAMOS_IND_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_RESPUESTAS_GPO_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_RESPUESTAS_IND_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_RESPUESTAS_IND_V_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_RESPUESTAS_INTEGRANTE_T;
import static com.sidert.sidertmovil.utils.Constants.TIPO;

public class fichas_pendientes_fragment extends Fragment{

    private Context ctx;
    private Home boostrap;
    private RecyclerView rvFichas;
    private adapter_fichas_pendientes adapter;

    private List<MCarteraGnral> _m_carteraGral;
    private SQLiteDatabase db;
    private DBhelper dBhelper;

    private SessionManager session;

    private TextView tvNoInfo;
    public TextView tvContFiltros;
    public TextView tvContCierre;
    private AutoCompleteTextView aetNombre;
    private AutoCompleteTextView aetDia;
    private AutoCompleteTextView aetColonia;
    private Spinner spAsesor;
    private CheckBox cbInd;
    private CheckBox cbGpo;
    private int cont_filtros = 0;

    private String[] dataAsesor;
    private ArrayAdapter<String> adapterNombre;
    private ArrayAdapter<String> adapterDia;
    private ArrayAdapter<String> adapterColonia;
    private ArrayAdapter<String> adapterAsesor;

    private orders_fragment parent;

    List<String> asesor;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fichas_pendientes, container,false);
        ctx = getContext();
        boostrap = (Home) getActivity();

        parent = (orders_fragment) getParentFragment();

        session = new SessionManager(ctx);

        dBhelper = new DBhelper(ctx);
        db = dBhelper.getWritableDatabase();

        tvNoInfo = view.findViewById(R.id.tvNoInfo);
        rvFichas = view.findViewById(R.id.rvFichas);

        rvFichas.setLayoutManager(new LinearLayoutManager(ctx));
        //rvFichas.setHasFixedSize(false);

        _m_carteraGral = new ArrayList<>();

        GetAsesores();

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
                i_prestamos.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
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
            }
        });
        //GetCartera("");

        rvFichas.setAdapter(adapter);

    }

    private void GetCartera(String where){
        Cursor row;
        _m_carteraGral = new ArrayList<>();

        String query = "SELECT * FROM (SELECT id_cartera,nombre, direccion,is_ruta, ruta_obligado,dia,'' as tesorera,asesor_nombre,'INDIVIDUAL' AS tipo, colonia, COALESCE(pi.tipo_cartera,'NO ENCONTRADO'), COALESCE(ri.estatus, -1) AS parcial, ci.dias_atraso FROM " + TBL_CARTERA_IND_T + " AS ci LEFT JOIN " + TBL_PRESTAMOS_IND_T + " AS pi ON ci.id_cartera = pi.id_cliente LEFT JOIN " + TBL_RESPUESTAS_IND_T + " AS ri ON pi.id_prestamo = ri.id_prestamo WHERE ri._id = (SELECT ri2._id FROM " + TBL_RESPUESTAS_IND_T +" AS ri2 WHERE ri2.id_prestamo = pi.id_prestamo ORDER BY ri2._id DESC LIMIT 1) OR ri._id is null AND pi.tipo_cartera IN ('VIGENTE', 'COBRANZA') AND ci.estatus = '1' UNION SELECT id_cartera,nombre, direccion,is_ruta, ruta_obligado,dia,tesorera,asesor_nombre,'GRUPAL' AS tipo, colonia, COALESCE(pg.tipo_cartera,'NO ENCONTRADO'), COALESCE(rg.estatus, -1) AS parcial, cg.dias_atraso FROM "+TBL_CARTERA_GPO_T +" AS cg LEFT JOIN " + TBL_PRESTAMOS_GPO_T + " AS pg ON cg.id_cartera = pg.id_grupo LEFT JOIN " + TBL_RESPUESTAS_GPO_T +" AS rg ON pg.id_prestamo = rg.id_prestamo WHERE rg._id = (SELECT rg2._id FROM " + TBL_RESPUESTAS_GPO_T + " AS rg2 WHERE rg2.id_prestamo = pg.id_prestamo ORDER by rg2._id DESC LIMIT 1) OR rg._id IS null AND pg.tipo_cartera IN ('VIGENTE', 'COBRANZA') AND cg.estatus = '1' UNION SELECT cvi.id_cartera, cvi.nombre, cvi.direccion, cvi.is_ruta, cvi.ruta_obligado, cvi.dia, '' as tesorera, cvi.asesor_nombre, 'INDIVIDUAL' AS tipo, cvi.colonia, COALESCE(pvi.tipo_cartera,'NO ENCONTRADO'), COALESCE(rvi.estatus, -1) AS parcial, cvi.dias_atraso FROM " + TBL_CARTERA_IND_T + " AS cvi LEFT JOIN " + TBL_PRESTAMOS_IND_T + " AS pvi ON cvi.id_cartera = pvi.id_cliente LEFT JOIN " + TBL_RESPUESTAS_IND_V_T + " AS rvi ON pvi.id_prestamo = rvi.id_prestamo WHERE rvi._id = (SELECT rvi2._id FROM " + TBL_RESPUESTAS_IND_V_T + " AS rvi2 WHERE rvi2.id_prestamo = pvi.id_prestamo ORDER BY rvi2._id DESC LIMIT 1) OR rvi._id is null AND pvi.tipo_cartera IN ('VENCIDA') AND cvi.estatus = '1' UNION SELECT cvg.id_cartera,cvg.nombre, cvg.direccion,cvg.is_ruta, cvg.ruta_obligado,cvg.dia,cvg.tesorera,cvg.asesor_nombre,'GRUPAL' AS tipo, cvg.colonia, COALESCE(pvg.tipo_cartera,'NO ENCONTRADO'), COALESCE(rvg.estatus, -1) AS parcial, cvg.dias_atraso FROM " + TBL_CARTERA_GPO_T + " AS cvg LEFT JOIN " + TBL_PRESTAMOS_GPO_T + " AS pvg ON cvg.id_cartera = pvg.id_grupo LEFT JOIN " + TBL_RESPUESTAS_INTEGRANTE_T +" AS rvg ON pvg.id_prestamo = rvg.id_prestamo WHERE rvg._id = (SELECT rvg2._id FROM " + TBL_RESPUESTAS_INTEGRANTE_T + " AS rvg2 WHERE rvg2.id_prestamo = pvg.id_prestamo ORDER by rvg2._id DESC LIMIT 1) OR rvg._id IS null AND pvg.tipo_cartera IN ('VENCIDA') AND cvg.estatus = '1') AS cartera "+where+" GROUP BY id_cartera";

        Log.e("QUERYCartera", query);
        row = db.rawQuery(query, null);

        parent.SetUpBagde(0, row.getCount());

        String[] dataNombre;
        String[] dataDia;
        String[] dataColonia;
        if (row.getCount() > 0){
            row.moveToFirst();
            List<String> nombre = new ArrayList<>();
            List<String> dia = new ArrayList<>();
            List<String> colonia = new ArrayList<>();

            for (int i = 0; i < row.getCount(); i++){
                nombre.add(row.getString(1));
                dia.add(row.getString(5));
                colonia.add(row.getString(9));
                MCarteraGnral mCarteraGeneral = new MCarteraGnral();
                mCarteraGeneral.setId_cliente(row.getString(0));
                mCarteraGeneral.setTipo(row.getString(8));
                mCarteraGeneral.setNombre(row.getString(1));
                mCarteraGeneral.setTesorera(row.getString(6));
                mCarteraGeneral.setDireccion(row.getString(2));
                mCarteraGeneral.setDiaSemana(row.getString(5));
                mCarteraGeneral.setIs_ruta(row.getInt(3)==1);
                mCarteraGeneral.setIs_obligatorio(row.getInt(4)==1);
                mCarteraGeneral.setTipoPrestamo(row.getString(10));
                mCarteraGeneral.setParcial(row.getInt(11));
                mCarteraGeneral.setDiasMora(Miscellaneous.Rango(row.getInt(12)));
                _m_carteraGral.add(mCarteraGeneral);
                row.moveToNext();
            }

            dataNombre = Miscellaneous.RemoverRepetidos(nombre);
            dataColonia = Miscellaneous.RemoverRepetidos(colonia);
            dataDia = Miscellaneous.RemoverRepetidos(dia);

            adapterNombre = new ArrayAdapter<>(ctx,
                    R.layout.custom_list_item, R.id.text_view_list_item, dataNombre);

            adapterDia = new ArrayAdapter<>(ctx,
                    R.layout.custom_list_item, R.id.text_view_list_item, dataDia);

            adapterColonia = new ArrayAdapter<>(ctx,
                    R.layout.custom_list_item, R.id.text_view_list_item, dataColonia);

        }
        else{

            dataNombre = new String[1];
            dataNombre[0] = "";
            adapterNombre = new ArrayAdapter<>(ctx,
                    R.layout.custom_list_item, R.id.text_view_list_item, dataNombre);

            dataDia = new String[1];
            dataDia[0] = "";
            adapterDia = new ArrayAdapter<>(ctx,
                    R.layout.custom_list_item, R.id.text_view_list_item, dataDia);

            dataColonia = new String[1];
            dataColonia[0] = "";
            adapterColonia = new ArrayAdapter<>(ctx,
                    R.layout.custom_list_item, R.id.text_view_list_item, dataColonia);
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
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        onResume();
        inflater.inflate(R.menu.menu_cartera, menu);

        if (session.getUser().get(5).contains("ROLE_GESTOR"))
            menu.getItem(0).setVisible(true);

        final MenuItem menuItem = menu.findItem(R.id.nvFiltro);
        View actionView = menuItem.getActionView();
        tvContFiltros = actionView.findViewById(R.id.filtro_bagde);
        actionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOptionsItemSelected(menuItem);
            }
        });

        final MenuItem menuItemCierre = menu.findItem(R.id.nvCierreDia);
        View actionViewCierre = menuItemCierre.getActionView();
        tvContCierre = actionViewCierre.findViewById(R.id.filtro_bagde);
        actionViewCierre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOptionsItemSelected(menuItemCierre);
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
                    Objects.requireNonNull(error_network.getWindow()).requestFeature(Window.FEATURE_NO_TITLE);
                    error_network.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                    error_network.show();
                }
                return true;
            case R.id.nvCierreDia:
                Intent i_cierre_dia = new Intent(ctx, MisCierresDeDia.class);
                startActivity(i_cierre_dia);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
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

                                if (!spAsesor.getSelectedItem().toString().trim().isEmpty()) {
                                    filtros.put("asesor_cartera_p",spAsesor.getSelectedItemPosition()+"");
                                    cont_filtros += 1;
                                    where += " AND asesor_nombre LIKE '%" + spAsesor.getSelectedItem().toString().trim() + "%'";
                                }
                                else filtros.put("asesor_cartera_p","0");

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
                                cbInd.setChecked(false);
                                cbGpo.setChecked(false);
                                aetNombre.setText("");
                                aetDia.setText("");
                                aetColonia.setText("");
                                spAsesor.setSelection(0);
                                cont_filtros = 0;
                                filtros = new HashMap<>();
                                filtros.put("nombre_cartera_p","");
                                filtros.put("dia_semana_p","");
                                filtros.put("colonia_carteta_p","");
                                filtros.put("asesor_cartera_p","0");
                                filtros.put("tipo_cartera_ind_p","0");
                                filtros.put("tipo_cartera_gpo_p","0");
                                filtros.put("contador_cartera_p", "0");
                                session.setFiltrosCartera(filtros);

                                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                                GetCartera("");

                                aetNombre.setAdapter(adapterNombre);
                                aetDia.setAdapter(adapterDia);
                                aetColonia.setAdapter(adapterColonia);
                                spAsesor.setAdapter(adapterAsesor);
                                //dialog.dismiss();

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

        aetNombre.setAdapter(adapterNombre);
        aetDia.setAdapter(adapterDia);
        aetColonia.setAdapter(adapterColonia);
        spAsesor.setAdapter(adapterAsesor);

        aetDia.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                aetDia.showDropDown();
                return false;
            }
        });

        aetNombre.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                aetNombre.showDropDown();
                return false;
            }
        });

        aetColonia.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                aetColonia.showDropDown();
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
            spAsesor.setSelection(Integer.parseInt(session.getFiltrosCartera().get(5)));
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

        if (tvContCierre != null){
            Cursor row = dBhelper.getRecords(TBL_CIERRE_DIA_T, " WHERE estatus = 0", "", null);
            if (row.getCount() > 0){
                Log.e("Cierre", row.getCount()+" zzz");
                tvContCierre.setText(String.valueOf(row.getCount()));
                tvContCierre.setVisibility(View.VISIBLE);
            }

        }

    }

    private void GetAsesores (){
        String sql = "SELECT * FROM (SELECT ci.asesor_nombre FROM "+TBL_CARTERA_IND_T + " AS ci WHERE ci.estatus = ? UNION SELECT cg.asesor_nombre FROM " + TBL_CARTERA_GPO_T + " AS cg WHERE cg.estatus = ?) AS asesores ORDER BY asesor_nombre ASC";
        Cursor row = db.rawQuery(sql, new String[]{"1","1"});
        asesor = new ArrayList<>();
        asesor.add("");
        if (row.getCount() > 0){
            row.moveToFirst();
            dataAsesor = new String[row.getCount()];
            for(int i = 0; i < row.getCount(); i++){
                asesor.add(row.getString(0));
                row.moveToNext();
            }
            dataAsesor = Miscellaneous.RemoverRepetidos(asesor);

            adapterAsesor = new ArrayAdapter<>(ctx,
                    R.layout.custom_list_item, R.id.text_view_list_item, dataAsesor);
        }
        else {
            dataAsesor = new String[1];
            dataAsesor[0] = "";
            adapterAsesor = new ArrayAdapter<>(ctx,
                    R.layout.custom_list_item, R.id.text_view_list_item, dataAsesor);
        }
        row.close();
        Log.e("SizeASesores", asesor.size()+" aws");
    }


    @Override
    public void onResume() {
        super.onResume();
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

        Log.e("SizeAsesires"," tamaño:" + asesor.size());
        if (!session.getFiltrosCartera().get(5).isEmpty() && Integer.parseInt(session.getFiltrosCartera().get(5)) > 0 && asesor.size() > 0) {
            where += " AND asesor_nombre LIKE '%" + asesor.get(Integer.parseInt(session.getFiltrosCartera().get(5))) + "%'";
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



