package com.sidert.sidertmovil.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
/*import android.support.v4.view.MenuItemCompat;
import androidx.appcompat.app.AppCompatActivity;*/
import android.os.Bundle;
/*import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;*/
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

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

/**Clase donde se visualiza el listado de los cierre de dia eso es para gestores solamente*/
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

        session = SessionManager.getInstance(ctx);

        dBhelper = DBhelper.getInstance(ctx);
        db = dBhelper.getWritableDatabase();

        tbMain = findViewById(R.id.tbMain);

        setSupportActionBar(tbMain);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        rvMisCierres = findViewById(R.id.rvMisCierres);

        rvMisCierres.setLayoutManager(new LinearLayoutManager(ctx));
        rvMisCierres.setHasFixedSize(false);

        /**Obtiene el listado de cierres de dia*/
        GetCierresDia("");
    }

    /**Funcion para obtener el listado de los cierres de dia generados al
     * imprimir un recibo original de prestamos vencidos individual o grupal*/
    private void GetCierresDia(String where){

        /**Se prepara la consulta buscando en la tabla de TBL_CIERRE_DIA_T y JOIN con TBL_RESPUESTA_IND_V_T y
         * haciendo un UNION para buscar tambien de TBL_RESPUESTAS_INTEGRANTE_T*/
        String sql = "SELECT * FROM(SELECT cdi._id, cdi.id_respuesta, cdi.clave_cliente, cdi.tipo_cierre, cdi.tipo_prestamo, ri.fecha_inicio, ri.pago_realizado, cdi.nombre, cdi.estatus, cdi.num_prestamo, cdi.monto_depositado, cdi.evidencia, ri.estatus AS estatus_res, cdi.fecha_envio FROM " + TBL_CIERRE_DIA_T + " AS cdi LEFT JOIN " + TBL_RESPUESTAS_IND_V_T + " AS ri ON cdi.id_respuesta = ri._id WHERE cdi.tipo_cierre = 'INDIVIDUAL' UNION SELECT cd._id, cd.id_respuesta, cd.clave_cliente, cd.tipo_cierre, cd.tipo_prestamo, r.fecha_inicio, r.pago_realizado, cd.nombre, cd.estatus, cd.num_prestamo, cd.monto_depositado, cd.evidencia, r.estatus AS estatus_res, cd.fecha_envio FROM " + TBL_CIERRE_DIA_T + " AS cd LEFT JOIN " + TBL_RESPUESTAS_INTEGRANTE_T + " AS r ON cd.id_respuesta = r._id WHERE cd.tipo_cierre = 'GRUPAL') AS cierres"+where;

        Log.e("QueryCierre", sql);
        Cursor row = db.rawQuery(sql, null);

        ArrayList<MCierreDia> data = new ArrayList<>();
        if (row.getCount() > 0){
            row.moveToFirst();
            dataNombre = new String[row.getCount()];
            List<String> nombre = new ArrayList<>();

            /**Se recorre todos los cierres de dia obtenidos de la consulta y se crea un array de objectos de MCierreDia*/
            for (int i = 0; i < row.getCount(); i++){

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
                    /**Evento al dar tap a un cierre de dia ya sea para gestionarla o ver la gestion (que ya haya sido guardada)*/
                    Intent i_cierre = new Intent(ctx, CierreDeDia.class);
                    i_cierre.putExtra("cierre_dia", item);
                    startActivity(i_cierre);
                }
            });

            rvMisCierres.setAdapter(adatper);
        }
    }

    /**Funcion para filtrar el listado de cierres de dia*/
    private void GetFiltros(){

        int sizeH = 900;
        Activity activity = this;
        if (activity != null) {
            View decorateView = activity.getWindow().getDecorView();
            sizeH = (int) (decorateView.getHeight() / 2.0);
        }

        DialogPlus filtros_dg = DialogPlus.newDialog(ctx)
                .setContentHolder(new ViewHolder(R.layout.sheet_dialog_filtros_cierres))
                .setGravity(Gravity.TOP)
                .setPadding(20,10,20,10)
                .setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(DialogPlus dialog, View view) {/**Evento de click que tiene el dialog de filtros*/
                        String where = "";
                        cont_filtros = 0;
                        /**Se crea un map donde se guardaran los filtros aplicados para almacenar en variable de sesion*/
                        HashMap<String, String> filtros = new HashMap<>();
                        InputMethodManager imm = (InputMethodManager)ctx.getSystemService(Context.INPUT_METHOD_SERVICE);
                        int id = view.getId(); /**Obtiene el id de la vista(objecto al que se le da click )*/
                        if (id == R.id.btnFiltrar) {/**Selecciono el boton de filtrar*/
                            /**Se valida se el campo de nombre este lleno*/
                            if (!aetNombre.getText().toString().trim().isEmpty()) {
                                /**agrega al map el nombre para ser guardado en variable de sesion*/
                                filtros.put("nombre_cierre", aetNombre.getText().toString().trim());
                                /**aumenta el contador de filtros*/
                                cont_filtros += 1;
                                /**agrega al condicional que buscará por nombre*/
                                where = " AND nombre LIKE '%" + aetNombre.getText().toString().trim() + "%'";
                            }
                            /**Cuando esta vacio el campo nombre guarda en el map de variable de sesion vacio*/
                            else filtros.put("nombre_cierre", "");

                            /**Valida si los checkbox de contestada y pendiente de contestar esten selecionado*/
                            if (cbContestadas.isChecked() && cbPendientes.isChecked()) {
                                /**Agrega al map ambos filtros */
                                filtros.put("estatus_conte_cierre", "1");
                                filtros.put("estatus_pendi_cierre", "1");
                                /**Aumenta 2 al contador */
                                cont_filtros += 2;
                                /**agrega al condicional buscar por estatus de gestion
                                 * 0=cuando no han gestioado,
                                 * 1=cuando gestionaron pero esta en pendiente de envio
                                 * 2=cuando gestionaron y ya se guardo en el servidor*/
                                where += " AND estatus IN (0,1,2)";
                            }
                            /**Valida que solo selecciono cierres de dia contestados*/
                            else if (cbContestadas.isChecked()) {
                                /**agrega al map filtros*/
                                filtros.put("estatus_conte_cierre", "1");
                                filtros.put("estatus_pendi_cierre", "0");
                                /**aumenta el contador de filtros*/
                                cont_filtros += 1;
                                /**agrega al condicional para buscar en estatus mayor que 0*/
                                where += " AND estatus > 0";
                            }
                            /**Valida que solo selecciono cierres de dia pendientes de contestae*/
                            else if (cbPendientes.isChecked()) {
                                /**agrega al map filtros*/
                                filtros.put("estatus_conte_cierre", "0");
                                filtros.put("estatus_pendi_cierre", "1");
                                /**aumenta el contador de filtros*/
                                cont_filtros += 1;
                                /**agrega al condicional para buscar en estatus igual a 0*/
                                where += " AND estatus = 0";
                            }
                            /**En caso de no seleccionar los checklist solo guarda en el map
                             * para variables de sesion*/
                            else {
                                filtros.put("estatus_conte_cierre", "0");
                                filtros.put("estatus_pendi_cierre", "0");
                            }

                            /**Agrega el contador de filtros al map*/
                            filtros.put("contador_cierre", String.valueOf(cont_filtros));
                            /**Guarda el map de los filtros en variables de sesion*/
                            session.setFiltrosCierre(filtros);

                            /**Oculta el teclado virtal*/
                            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                            Log.e("where", where);
                            /**Valida si la variable where su contienido es mayor 4 para anteponer
                             * la palabra reservada WHERE de sqlite */
                            if (where.length() > 4)
                                GetCierresDia(" WHERE" + where.substring(4));
                            else {
                                /**En caso de no seleccionar ningun filtro se obtienen los
                                 * cierres de dia sin ningun filtro*/
                                GetCierresDia("");
                            }
                            /**Cierra el dialog de filtros*/
                            dialog.dismiss();
                        } else if (id == R.id.btnBorrarFiltros) {/**Selecciono el boton de borrar filtros*/
                            /**Limpia los valores de los filtros*/
                            cbContestadas.setChecked(false);
                            cbPendientes.setChecked(false);
                            aetNombre.setText("");
                            cont_filtros = 0;
                            /**Se crea un map de filtros vacios*/
                            filtros = new HashMap<>();
                            filtros.put("nombre_cierre", "");
                            filtros.put("estatus_conte_cierre", "0");
                            filtros.put("estatus_pendi_cierre", "0");
                            filtros.put("contador_cierre", "0");
                            /**se coloca el map de filtros en variables de sesion*/
                            session.setFiltrosCierre(filtros);

                            /**Oculta el teclado virtual*/
                            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                            /**Obtiene el listado de todos los cierres de dia sin ningun filtro*/
                            GetCierresDia("");

                            aetNombre.setAdapter(adapterNombre);
                        }
                        setupBadge();

                    }
                })
                .setExpanded(true, sizeH)
                .create();
        aetNombre       = filtros_dg.getHolderView().findViewById(R.id.aetNombre);
        cbContestadas   = filtros_dg.getHolderView().findViewById(R.id.cbContestadas);
        cbPendientes     = filtros_dg.getHolderView().findViewById(R.id.cbPendientes);

        try {
            aetNombre.setAdapter(adapterNombre);
        }catch (Exception e){

        }

        /**Evento para abrir un dropdown de los nombres e ir filtrando*/
        aetNombre.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                aetNombre.showDropDown();
                return false;
            }
        });

        try {
            /**Se valida si hay filtros ya guardado anteriormente*/
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

    /**Funcion para colocar el total de cierres de dia pendientes en el menu del toolbar*/
    private void setupBadge() {
        Log.v("contador",session.getFiltrosCierre().get(3));
        if (tvContFiltros != null) {
            Log.e("tvcontador", "visible");
            tvContFiltros.setText(String.valueOf(session.getFiltrosCierre().get(3)));
            tvContFiltros.setVisibility(View.VISIBLE);
        }

    }

    /**Funcion para remover nombres repetidos esto para filtrar*/
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
        int itemId = item.getItemId();
        if (itemId == android.R.id.home) {
            finish();
        } else if (itemId == R.id.nvFiltro) {
            GetFiltros();
        } else if (itemId == R.id.nvInfo) {
        }
        return super.onOptionsItemSelected(item);
    }

    /**Esta funcion se ejecuta cada que se entra a la vista*/
    @Override
    protected void onResume() {
        super.onResume();
        setupBadge();
        String where = "";
        /**Se busca en los filtros guardados en variables de sesion y generar el condicional*/
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
