package com.sidert.sidertmovil.fragments.view_pager;


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
import android.widget.TextView;

import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.OnClickListener;
import com.orhanobut.dialogplus.ViewHolder;
import com.sidert.sidertmovil.Home;
import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.activities.MisCierresDeDia;
import com.sidert.sidertmovil.activities.ResumenCartera;
import com.sidert.sidertmovil.adapters.adapter_ficha_contestadas;
import com.sidert.sidertmovil.database.DBhelper;
import com.sidert.sidertmovil.models.MFichaContestada;
import com.sidert.sidertmovil.utils.Constants;
import com.sidert.sidertmovil.utils.NetworkStatus;
import com.sidert.sidertmovil.utils.Popups;
import com.sidert.sidertmovil.utils.Servicios_Sincronizado;
import com.sidert.sidertmovil.utils.SessionManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.sidert.sidertmovil.utils.Constants.TBL_CARTERA_GPO_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_CARTERA_IND_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_CIERRE_DIA_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_PRESTAMOS_GPO_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_PRESTAMOS_IND_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_RESPUESTAS_GPO_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_RESPUESTAS_IND_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_RESPUESTAS_IND_V_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_RESPUESTAS_INTEGRANTE_T;

public class answers_fragment extends Fragment{

    private Context ctx;
    private Home boostrap;
    private RecyclerView rvFichas;
    private adapter_ficha_contestadas adapter;
    private List<MFichaContestada> _m_fichaContestada;

    private SQLiteDatabase db;
    private DBhelper dBhelper;

    private SessionManager session;

    public TextView tvContFiltros;
    private TextView tvContCierre;
    private AutoCompleteTextView aetNombre;
    private CheckBox cbInd;
    private CheckBox cbGpo;
    private CheckBox cbEnv;
    private CheckBox cbPen;
    private int cont_filtros = 0;

    private String[] dataNombre;
    private ArrayAdapter<String> adapterNombre;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_answers, container, false);
        ctx = getContext();
        boostrap = (Home) getActivity();

        session = new SessionManager(ctx);

        dBhelper = new DBhelper(ctx);
        db = dBhelper.getWritableDatabase();

        rvFichas = view.findViewById(R.id.rvFichas);

        rvFichas.setLayoutManager(new LinearLayoutManager(ctx));
        rvFichas.setHasFixedSize(false);

        _m_fichaContestada = new ArrayList<>();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
        adapter = new adapter_ficha_contestadas(ctx, _m_fichaContestada);

        rvFichas.setAdapter(adapter);
    }

    private void GetCartera(String where){
        Cursor row;
        String query;

        query = "SELECT * FROM (SELECT r._id AS id, p.id_prestamo, r.contacto AS estatus_gestion, r.fecha_fin AS timestamp, r.estatus AS estatus_ficha, p.fecha_establecida AS dia_pago, c.nombre, 'INDIVIDUAL' AS tipo_ficha, p.monto_requerido, r.pago_realizado, r.resultado_gestion, r.fecha_inicio FROM " + TBL_RESPUESTAS_IND_T + " AS r INNER JOIN "+ TBL_PRESTAMOS_IND_T + " AS p ON r.id_prestamo = p.id_prestamo INNER JOIN " + TBL_CARTERA_IND_T + " AS c ON p.id_cliente = c.id_cartera WHERE r.estatus > 0 UNION SELECT rg._id AS id, pg.id_prestamo, rg.contacto AS estatus_gestion, rg.fecha_fin AS timestamp, rg.estatus AS estatus_ficha, pg.fecha_establecida AS dia_pago, cg.nombre, 'GRUPAL' AS tipo_ficha, pg.monto_requerido, rg.pago_realizado,rg.resultado_gestion, rg.fecha_inicio FROM "+ TBL_RESPUESTAS_GPO_T + " AS rg INNER JOIN " + TBL_PRESTAMOS_GPO_T + " AS pg ON rg.id_prestamo = pg.id_prestamo INNER JOIN " + TBL_CARTERA_GPO_T + " AS cg ON pg.id_grupo = cg.id_cartera WHERE rg.estatus > 0 UNION SELECT rvi._id AS id, pvi.id_prestamo, rvi.contacto AS estatus_gestion, rvi.fecha_fin AS timestamp, rvi.estatus AS estatus_ficha, pvi.fecha_establecida AS dia_pago, cvi.nombre, 'INDIVIDUAL' AS tipo_ficha, pvi.monto_requerido, rvi.pago_realizado, rvi.resultado_gestion, rvi.fecha_inicio FROM " + TBL_RESPUESTAS_IND_V_T + " AS rvi INNER JOIN "+ TBL_PRESTAMOS_IND_T + " AS pvi ON rvi.id_prestamo = pvi.id_prestamo INNER JOIN " + TBL_CARTERA_IND_T + " AS cvi ON pvi.id_cliente = cvi.id_cartera WHERE rvi.estatus > 0 UNION SELECT rvg._id AS id, pvg.id_prestamo, rvg.contacto AS estatus_gestion, rvg.fecha_fin AS timestamp, rvg.estatus AS estatus_ficha, pvg.fecha_establecida AS dia_pago, cvg.nombre, 'GRUPAL' AS tipo_ficha, pvg.monto_requerido, rvg.pago_realizado,rvg.resultado_gestion, rvg.fecha_inicio FROM " + TBL_RESPUESTAS_INTEGRANTE_T + " AS rvg INNER JOIN " + TBL_PRESTAMOS_GPO_T + " AS pvg ON rvg.id_prestamo = pvg.id_prestamo INNER JOIN " + TBL_CARTERA_GPO_T + " AS cvg ON pvg.id_grupo = cvg.id_cartera WHERE rvg.estatus > 0) AS contestadas" + where + " ORDER BY timestamp DESC";


        row = db.rawQuery(query, null);
        if (row.getCount() > 0){
            _m_fichaContestada = new ArrayList<>();
            row.moveToFirst();
            dataNombre = new String[row.getCount()];
            List<String> nombre = new ArrayList<>();
            for (int i = 0; i < row.getCount(); i++){
                MFichaContestada item = new MFichaContestada();
                nombre.add(row.getString(6));
                item.setIdGestion(row.getString(0));
                item.setIdPrestamo(row.getString(1));
                item.setNombre(row.getString(6));
                item.setTipoFicha(row.getString(7));
                item.setEstatusFicha(row.getString(4));
                item.setFechaInicio(row.getString(11));
                switch (row.getString(2)){
                    case "SI":
                        if (row.getString(10).equals("PAGO")){
                            if (row.getDouble(9) >= row.getDouble(8)){
                                item.setEstatusGestion("CERRADA");
                            }
                            else{
                                item.setEstatusGestion("ABIERTA");
                            }
                        }
                        else if (row.getString(10).equals("NO PAGO")){
                            item.setEstatusGestion("ABIERTA");
                        }
                        break;
                    case "NO":
                        item.setEstatusGestion("ABIERTA");
                        break;
                    case "ACLARACION":
                        item.setEstatusGestion(row.getString(2));
                        break;
                }

                item.setDiaPago(row.getString(5));
                item.setTimestamp(row.getString(3));
                _m_fichaContestada.add(item);
                row.moveToNext();
            }
            dataNombre = RemoverRepetidos(nombre);

            adapterNombre = new ArrayAdapter<String>(ctx,
                    R.layout.custom_list_item, R.id.text_view_list_item, dataNombre);

            //adapter = new adapter_ficha_contestadas(ctx, _m_fichaContestada);
           // rvFichas.setAdapter(adapter);
        }
        row.close();

        if(_m_fichaContestada.size() > 0) {
            adapter.UpdateData(_m_fichaContestada);
            rvFichas.setVisibility(View.VISIBLE);
            //tvNoInfo.setVisibility(View.GONE);
        }
        else {
            rvFichas.setVisibility(View.GONE);
            //tvNoInfo.setVisibility(View.VISIBLE);
        }
    }

    private void Filtros (){
        DialogPlus filtros_dg = DialogPlus.newDialog(boostrap)
                .setContentHolder(new ViewHolder(R.layout.sheet_dialog_filtros_contestadas))
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
                                    filtros.put("nombre_cartera_c",aetNombre.getText().toString().trim());
                                    cont_filtros += 1;
                                    where = " AND nombre LIKE '%"+aetNombre.getText().toString().trim()+"%'";
                                }
                                else filtros.put("nombre_cartera_c","");

                                if (cbInd.isChecked() && cbGpo.isChecked()){
                                    filtros.put("tipo_cartera_ind_c","1");
                                    filtros.put("tipo_cartera_gpo_c","1");
                                    cont_filtros += 2;
                                    where += " AND tipo_ficha IN ('INDIVIDUAL','GRUPAL')";
                                }
                                else if (cbInd.isChecked()){
                                    filtros.put("tipo_cartera_ind_c","1");
                                    filtros.put("tipo_cartera_gpo_c","0");
                                    cont_filtros += 1;
                                    where += " AND tipo_ficha = 'INDIVIDUAL'";
                                }
                                else if (cbGpo.isChecked()){
                                    filtros.put("tipo_cartera_ind_c","0");
                                    filtros.put("tipo_cartera_gpo_c","1");
                                    cont_filtros += 1;
                                    where += " AND tipo_ficha = 'GRUPAL'";
                                }else {
                                    filtros.put("tipo_cartera_ind_c","0");
                                    filtros.put("tipo_cartera_gpo_c","0");
                                }

                                if (cbEnv.isChecked() && cbPen.isChecked()){
                                    filtros.put("estatus_ficha_env_c","1");
                                    filtros.put("estatus_ficha_pen_c","1");
                                    cont_filtros += 2;
                                    where += " AND estatus_ficha IN ('1','2')";
                                }
                                else if (cbEnv.isChecked()){
                                    filtros.put("estatus_ficha_env_c","1");
                                    filtros.put("estatus_ficha_pen_c","0");
                                    cont_filtros += 1;
                                    where += " AND estatus_ficha = '2'";
                                }
                                else if (cbPen.isChecked()){
                                    filtros.put("estatus_ficha_env_c","0");
                                    filtros.put("estatus_ficha_pen_c","1");
                                    cont_filtros += 1;
                                    where += " AND estatus_ficha = '1'";
                                }else {
                                    filtros.put("estatus_ficha_env_c","0");
                                    filtros.put("estatus_ficha_pen_c","0");
                                }

                                filtros.put("contador_cartera_c", String.valueOf(cont_filtros));
                                session.setFiltrosCarteraContestadas(filtros);

                                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                                Log.e("where",where);
                                if (where.length() > 4)
                                    GetCartera(" WHERE" + where.substring(4));
                                else
                                    GetCartera("");
                                dialog.dismiss();

                                break;
                            case R.id.btnBorrarFiltros:
                                cbInd.setChecked(false);
                                cbGpo.setChecked(false);
                                cbEnv.setChecked(false);
                                cbPen.setChecked(false);
                                aetNombre.setText("");

                                cont_filtros = 0;
                                filtros = new HashMap<>();
                                filtros.put("nombre_cartera_c","");
                                filtros.put("tipo_cartera_ind_c","0");
                                filtros.put("tipo_cartera_gpo_c","0");
                                filtros.put("estatus_ficha_env_c","0");
                                filtros.put("estatus_ficha_pen_c","0");
                                filtros.put("contador_cartera_c", "0");
                                session.setFiltrosCarteraContestadas(filtros);

                                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                                GetCartera("");

                                aetNombre.setAdapter(adapterNombre);
                                //dialog.dismiss();
                                break;
                        }
                        setupBadge();

                    }
                })
                .setExpanded(true, 900)
                .create();
        aetNombre   = filtros_dg.getHolderView().findViewById(R.id.aetNombre);
        cbInd       = filtros_dg.getHolderView().findViewById(R.id.cbInd);
        cbGpo       = filtros_dg.getHolderView().findViewById(R.id.cbGpo);
        cbPen       = filtros_dg.getHolderView().findViewById(R.id.cbPen);
        cbEnv       = filtros_dg.getHolderView().findViewById(R.id.cbEnv);

        try {
            aetNombre.setAdapter(adapterNombre);
        } catch (Exception e){

        }


        aetNombre.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                aetNombre.showDropDown();
                return false;
            }
        });


        try {
            if (!session.getFiltrosCarteraContestadas().get(4).isEmpty())
                aetNombre.setText(session.getFiltrosCarteraContestadas().get(4));
            if (session.getFiltrosCarteraContestadas().get(0).equals("1"))
                cbInd.setChecked(true);
            if (session.getFiltrosCarteraContestadas().get(1).equals("1"))
                cbGpo.setChecked(true);
            if (session.getFiltrosCarteraContestadas().get(2).equals("1"))
                cbEnv.setChecked(true);
            if (session.getFiltrosCarteraContestadas().get(3).equals("1"))
                cbPen.setChecked(true);
        }catch (Exception e){

        }

        filtros_dg.show();
    }

    private void setupBadge() {

        if (tvContFiltros != null) {
            Log.e("tvcontador", "visible");
            tvContFiltros.setText(String.valueOf(session.getFiltrosCarteraContestadas().get(5)));
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
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        onResume();
        inflater.inflate(R.menu.menu_cartera, menu);
        if (session.getUser().get(5).contains("ROLE_GESTOR"))
            menu.getItem(0).setVisible(true);

        final MenuItem menuItem = menu.findItem(R.id.nvFiltro);
        View actionView = MenuItemCompat.getActionView(menuItem);
        tvContFiltros = actionView.findViewById(R.id.filtro_bagde);
        actionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOptionsItemSelected(menuItem);
            }
        });

        final MenuItem menuItemCierre = menu.findItem(R.id.nvCierreDia);
        View actionViewCierre = MenuItemCompat.getActionView(menuItemCierre);
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
            case R.id.nvCierreDia:
                Intent i_cierre_dia = new Intent(ctx, MisCierresDeDia.class);
                startActivity(i_cierre_dia);
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
        if (!session.getFiltrosCarteraContestadas().get(4).isEmpty())
            where = " AND nombre LIKE '%" + session.getFiltrosCarteraContestadas().get(4) + "%'";


        if (session.getFiltrosCarteraContestadas().get(0).equals("1") && session.getFiltrosCarteraContestadas().get(1).equals("1")){
            where += " AND tipo_ficha IN ('INDIVIDUAL','GRUPAL')";
        }
        else if (session.getFiltrosCarteraContestadas().get(0).equals("1")){
            where += " AND tipo_ficha = 'INDIVIDUAL' ";
        }
        else if (session.getFiltrosCarteraContestadas().get(1).equals("1")){
            where += " AND tipo_ficha = 'GRUPAL'";
        }

        if (session.getFiltrosCarteraContestadas().get(2).equals("1") && session.getFiltrosCarteraContestadas().get(3).equals("1")){
            where += " AND estatus_ficha IN ('2','1')";
        }
        else if (session.getFiltrosCarteraContestadas().get(2).equals("1")){
            where += " AND estatus_ficha = '2' ";
        }
        else if (session.getFiltrosCarteraContestadas().get(3).equals("1")){
            where += " AND estatus_ficha = '1'";
        }

        if (where.length() > 4)
            GetCartera(" WHERE" + where.substring(4));
        else
            GetCartera("");
    }
}
