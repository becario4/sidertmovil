package com.sidert.sidertmovil.activities;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.TextView;

import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.OnClickListener;
import com.orhanobut.dialogplus.ViewHolder;
import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.adapters.adapter_cierre_dia;
import com.sidert.sidertmovil.database.DBhelper;
import com.sidert.sidertmovil.models.MCierreDia;
import com.sidert.sidertmovil.utils.SessionManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.sidert.sidertmovil.utils.Constants.TBL_CIERRE_DIA_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_RESPUESTAS_IND_V_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_RESPUESTAS_INTEGRANTE_T;

public class MisCierresDeDia extends AppCompatActivity {

    private Context ctx;
    private Toolbar tbMain;
    private RecyclerView rvMisCierres;
    private adapter_cierre_dia adatper;

    private DBhelper dBhelper;
    private SQLiteDatabase db;

    private int cont_filtros = 0;

    public TextView tvContFiltros;

    private AutoCompleteTextView aetNombre;
    private CheckBox cbContestadas;
    private CheckBox cbPendientes;

    private SessionManager session;

    private String[] dataNombre;
    private ArrayAdapter<String> adapterNombre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_cierres_de_dia);

        ctx = this;

        session = new SessionManager(ctx);

        dBhelper = new DBhelper(ctx);
        db = dBhelper.getWritableDatabase();

        tbMain = findViewById(R.id.tbMain);

        setSupportActionBar(tbMain);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        rvMisCierres = findViewById(R.id.rvMisCierres);

        rvMisCierres.setLayoutManager(new LinearLayoutManager(ctx));
        rvMisCierres.setHasFixedSize(false);

        GetCierresDia("");
    }

    private void GetCierresDia(String where){
        String sql = "SELECT * FROM(SELECT cdi._id, cdi.id_respuesta, cdi.clave_cliente, cdi.tipo_cierre, cdi.tipo_prestamo, ri.fecha_inicio, ri.pago_realizado, cdi.nombre, cdi.estatus, cdi.num_prestamo, cdi.monto_depositado, cdi.evidencia, ri.estatus AS estatus_res, cdi.fecha_envio FROM " + TBL_CIERRE_DIA_T + " AS cdi LEFT JOIN " + TBL_RESPUESTAS_IND_V_T + " AS ri ON cdi.id_respuesta = ri._id WHERE cdi.tipo_cierre = 'INDIVIDUAL' UNION SELECT cd._id, cd.id_respuesta, cd.clave_cliente, cd.tipo_cierre, cd.tipo_prestamo, r.fecha_inicio, r.pago_realizado, cd.nombre, cd.estatus, cd.num_prestamo, cd.monto_depositado, cd.evidencia, r.estatus AS estatus_res, cd.fecha_envio FROM " + TBL_CIERRE_DIA_T + " AS cd LEFT JOIN " + TBL_RESPUESTAS_INTEGRANTE_T + " AS r ON cd.id_respuesta = r._id WHERE cd.tipo_cierre = 'GRUPAL') AS cierres"+where;

        Log.e("QueryCierre", sql);
        Cursor row = db.rawQuery(sql, null);

        ArrayList<MCierreDia> data = new ArrayList<>();
        if (row.getCount() > 0){
            row.moveToFirst();
            dataNombre = new String[row.getCount()];
            List<String> nombre = new ArrayList<>();

            for (int i = 0; i < row.getCount(); i++){
                Log.e("-","-------------------------------------------");
                Log.e("id_gestion", row.getString(1));
                Log.e("Null", String.valueOf(row.getString(5)));
                Log.e("Null", String.valueOf(row.getString(6)));
                Log.e("Null", String.valueOf(row.getString(12)));

                MCierreDia item = new MCierreDia();
                nombre.add(row.getString(7));
                item.setId(row.getString(0));
                item.setIdRespuesta(row.getString(1));
                item.setClaveCliente(row.getString(2));
                item.setTipoCierre(row.getInt(3));
                item.setTipoPrestamo(row.getString(4));
                item.setFecha(row.getString(5));
                item.setPago(row.getString(6));
                item.setNombre(row.getString(7));
                item.setEstatus(row.getInt(8));
                item.setNumPrestamo(row.getString(9));
                item.setMonto(row.getString(10));
                item.setEvidencia(row.getString(11));
                item.setEstatusRespuesta(row.getInt(12));
                data.add(item);
                row.moveToNext();
            }

            dataNombre = RemoverRepetidos(nombre);

            adapterNombre = new ArrayAdapter<String>(ctx,
                    R.layout.custom_list_item, R.id.text_view_list_item, dataNombre);

            adatper = new adapter_cierre_dia(ctx, data, new adapter_cierre_dia.Event() {
                @Override
                public void CierreOnClick(MCierreDia item) {
                    Intent i_cierre = new Intent(ctx, CierreDeDia.class);
                    i_cierre.putExtra("cierre_dia", item);
                    startActivity(i_cierre);
                }
            });

            rvMisCierres.setAdapter(adatper);
        }
    }

    private void GetFiltros(){
        DialogPlus filtros_dg = DialogPlus.newDialog(ctx)
                .setContentHolder(new ViewHolder(R.layout.sheet_dialog_filtros_cierres))
                .setGravity(Gravity.TOP)
                .setPadding(20,10,20,10)
                .setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(DialogPlus dialog, View view) {
                        String where = "";
                        cont_filtros = 0;
                        HashMap<String, String> filtros = new HashMap<>();
                        InputMethodManager imm = (InputMethodManager)ctx.getSystemService(Context.INPUT_METHOD_SERVICE);
                        switch (view.getId()) {
                            case R.id.btnFiltrar:
                                if (!aetNombre.getText().toString().trim().isEmpty()){
                                    filtros.put("nombre_cierre",aetNombre.getText().toString().trim());
                                    cont_filtros += 1;
                                    where = " AND nombre LIKE '%"+aetNombre.getText().toString().trim()+"%'";
                                }
                                else filtros.put("nombre_cierre","");

                                if (cbContestadas.isChecked() && cbPendientes.isChecked()){
                                    filtros.put("estatus_conte_cierre","1");
                                    filtros.put("estatus_pendi_cierre","1");
                                    cont_filtros += 2;
                                    where += " AND estatus IN (0,1,2)";
                                }
                                else if (cbContestadas.isChecked()){
                                    filtros.put("estatus_conte_cierre","1");
                                    filtros.put("estatus_pendi_cierre","0");
                                    cont_filtros += 1;
                                    where += " AND estatus > 0";
                                }
                                else if (cbPendientes.isChecked()){
                                    filtros.put("estatus_conte_cierre","0");
                                    filtros.put("estatus_pendi_cierre","1");
                                    cont_filtros += 1;
                                    where += " AND estatus = 0";
                                }else {
                                    filtros.put("estatus_conte_cierre","0");
                                    filtros.put("estatus_pendi_cierre","0");
                                }

                                filtros.put("contador_cierre", String.valueOf(cont_filtros));
                                session.setFiltrosCierre(filtros);

                                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                                Log.e("where",where);
                                if (where.length() > 4)
                                    GetCierresDia(" WHERE" + where.substring(4));
                                else
                                    GetCierresDia("");
                                dialog.dismiss();

                                break;
                            case R.id.btnBorrarFiltros:
                                cbContestadas.setChecked(false);
                                cbPendientes.setChecked(false);
                                aetNombre.setText("");
                                cont_filtros = 0;
                                filtros = new HashMap<>();
                                filtros.put("nombre_cierre","");
                                filtros.put("estatus_conte_cierre","0");
                                filtros.put("estatus_pendi_cierre","0");
                                filtros.put("contador_cierre", "0");
                                session.setFiltrosCierre(filtros);

                                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                                GetCierresDia("");

                                aetNombre.setAdapter(adapterNombre);

                                break;
                        }
                        setupBadge();

                    }
                })
                .setExpanded(true, 550)
                .create();
        aetNombre       = filtros_dg.getHolderView().findViewById(R.id.aetNombre);
        cbContestadas   = filtros_dg.getHolderView().findViewById(R.id.cbContestadas);
        cbPendientes     = filtros_dg.getHolderView().findViewById(R.id.cbPendientes);

        try {
            aetNombre.setAdapter(adapterNombre);
        }catch (Exception e){

        }

        aetNombre.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                aetNombre.showDropDown();
                return false;
            }
        });

        try {
            if (!session.getFiltrosCierre().get(2).isEmpty())
                aetNombre.setText(session.getFiltrosCierre().get(2));

            if (session.getFiltrosCierre().get(0).equals("1"))
                cbContestadas.setChecked(true);
            if (session.getFiltrosCierre().get(1).equals("1"))
                cbPendientes.setChecked(true);
        }catch (Exception e){

        }
        filtros_dg.show();
    }

    private void setupBadge() {
        Log.v("contador",session.getFiltrosCierre().get(3));
        if (tvContFiltros != null) {
            Log.e("tvcontador", "visible");
            tvContFiltros.setText(String.valueOf(session.getFiltrosCierre().get(3)));
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
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
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

        menu.getItem(1).setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.nvFiltro:
                GetFiltros();
                break;
            case R.id.nvInfo:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setupBadge();
        String where = "";
        if (!session.getFiltrosCierre().get(2).isEmpty())
            where = " AND nombre LIKE '%" + session.getFiltrosCierre().get(2) + "%'";

        if (session.getFiltrosCierre().get(0).equals("1") && session.getFiltrosCierre().get(1).equals("1")){
            where += " AND estatus IN (0, 1, 2)";
        }
        else if (session.getFiltrosCierre().get(0).equals("1")){
            where += " AND estatus = 0 ";
        }
        else if (session.getFiltrosCierre().get(1).equals("1")){
            where += " AND estatus > 0";
        }

        if (where.length() > 4)
            GetCierresDia(" WHERE" + where.substring(4));
        else
            GetCierresDia("");
    }
}
