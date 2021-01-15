package com.sidert.sidertmovil.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
/*import android.support.design.widget.FloatingActionButton;
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
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.OnClickListener;
import com.orhanobut.dialogplus.ViewHolder;
import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.adapters.adapter_clientes_cc;
import com.sidert.sidertmovil.adapters.adapter_gestiones_agf_cc;
import com.sidert.sidertmovil.adapters.adapter_prestamos_agf;
import com.sidert.sidertmovil.database.DBhelper;
import com.sidert.sidertmovil.models.MTicketCC;
import com.sidert.sidertmovil.utils.Miscellaneous;
import com.sidert.sidertmovil.utils.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.sidert.sidertmovil.utils.Constants.TBL_PRESTAMOS;
import static com.sidert.sidertmovil.utils.Constants.TBL_RECIBOS;
import static com.sidert.sidertmovil.utils.Constants.TBL_RECIBOS_AGF_CC;
import static com.sidert.sidertmovil.utils.Constants.TBL_RECUPERACION_RECIBOS;
import static com.sidert.sidertmovil.utils.Constants.TIPO;

public class CobroAGF extends AppCompatActivity {

    private Context ctx;

    private RecyclerView rvClientesCC;
    private FloatingActionButton fbAgregarCC;

    private RadioButton rbPrestamos;
    private RadioButton rbRecibos;
    private RadioButton rbGestiones;

    private LinearLayout llCC;
    private LinearLayout llAGF;

    private DBhelper dBhelper;
    private SQLiteDatabase db;

    private boolean fabExpanded = false;

    private SessionManager session;

    private Toolbar tbMain;

    //Prestamos
    private CheckBox cbInd;
    private CheckBox cbGpo;
    private AutoCompleteTextView aetNombre;
    private ArrayAdapter<String> adapterNombre;
    private int cont_filtros = 0;
    public TextView tvContFiltros;
    private String[] dataNombre;
    private List<String> nombre;

    //Recibos
    private CheckBox cbAgf;
    private CheckBox cbCc;
    private CheckBox cbEnv;
    private CheckBox cbPen;
    private AutoCompleteTextView aetNombreRec;
    private AutoCompleteTextView aetFolio;
    private ArrayAdapter<String> adapterNombreRec;
    private int cont_filtros_recibo = 0;

    private String[] dataNombreRec;
    private List<String> nombreRec;

    //Gestiones
    private CheckBox cbAgfGes;
    private CheckBox cbCcGes;
    private CheckBox cbEnvGes;
    private CheckBox cbPenGes;
    private AutoCompleteTextView aetNombreGes;
    private ArrayAdapter<String> adapterNombreGes;
    private int cont_filtros_ges = 0;
    private String[] dataNombreGes;
    private List<String> nombreGes;

    private int tipoSeccion = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circulo_credito);


        ctx = this;

        tbMain  = findViewById(R.id.TBmain);

        dBhelper = new DBhelper(ctx);
        db = dBhelper.getWritableDatabase();
        session = new SessionManager(ctx);

        rbPrestamos     = findViewById(R.id.rbPrestamos);
        rbRecibos       = findViewById(R.id.rbRecibos);
        rbGestiones     = findViewById(R.id.rbGestiones);

        rvClientesCC = findViewById(R.id.rvClienteCC);
        fbAgregarCC  = findViewById(R.id.fbAgregar);

        llCC         = findViewById(R.id.llCC);
        llAGF        = findViewById(R.id.llAGF);

        rvClientesCC.setLayoutManager(new LinearLayoutManager(ctx));
        rvClientesCC.setHasFixedSize(false);

        fbAgregarCC.setOnClickListener(fbAgregarCC_OnClick);

        llCC.setOnClickListener(llCC_OnClick);
        llAGF.setOnClickListener(llAGF_OnClick);

        setSupportActionBar(tbMain);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("Solicitud de Soporte");

        invalidateOptionsMenu();

        rbPrestamos.setChecked(true);

        rbPrestamos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tipoSeccion = 1;
                setupBadge();
                String where = "";
                if (!session.getFiltrosCCAGF().get(0).isEmpty()){
                    where = " AND (p.nombre_grupo LIKE '%"+session.getFiltrosCCAGF().get(0)+"%' OR p.nombre_cliente LIKE '%"+session.getFiltrosCCAGF().get(0)+"%')";
                }

                if (session.getFiltrosCCAGF().get(1).equals("1") && session.getFiltrosCCAGF().get(2).equals("1")){
                    where += " AND (grupo_id = 1 OR grupo_id > 1)";
                }
                else if (session.getFiltrosCCAGF().get(2).equals("1")){
                    where += " AND p.grupo_id = 1";
                }
                else if (session.getFiltrosCCAGF().get(1).equals("1")){
                    where += " AND p.grupo_id > 1";
                }

                if (where.length() > 0)
                    getPrestamos(" WHERE" + where.substring(4) + " AND (rr._id is null or rr.estatus = 0)");
                else
                    getPrestamos(" WHERE (rr._id is null or rr.estatus = 0)");

            }
        });

        rbRecibos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tipoSeccion = 2;
                setupBadge();
                String where = "";
                if (!session.getFiltrosRecibosCCAGF().get(0).isEmpty()){
                    where = " AND nombre LIKE '%"+session.getFiltrosRecibosCCAGF().get(0)+"%'";
                }

                if (!session.getFiltrosRecibosCCAGF().get(1).isEmpty()){
                    where = " AND folio = "+session.getFiltrosRecibosCCAGF().get(1);
                }

                if (session.getFiltrosRecibosCCAGF().get(2).equals("1") && session.getFiltrosRecibosCCAGF().get(3).equals("1")){
                    where += " AND tipo_recibo IN ('AGF','CC')";
                }
                else if (session.getFiltrosRecibosCCAGF().get(2).equals("1")){
                    where += " AND tipo_recibo = 'AGF'";
                }
                else if (session.getFiltrosRecibosCCAGF().get(3).equals("1")) {
                    where += " AND tipo_recibo = 'CC'";
                }

                if (session.getFiltrosRecibosCCAGF().get(4).equals("1") && session.getFiltrosRecibosCCAGF().get(5).equals("1")){
                    where += " AND estatus IN (0,1)";
                }
                else if (session.getFiltrosRecibosCCAGF().get(4).equals("1")){
                    where += " AND estatus = 1";
                }
                else if (session.getFiltrosRecibosCCAGF().get(5).equals("1")){
                    where += " AND estatus = 0";
                }

                if (where.length() > 0)
                    getRecibos(where);
                else
                    getRecibos("");
            }
        });

        rbGestiones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String where = "";
                tipoSeccion = 3;
                setupBadge();
                if (!session.getFiltrosGestionCCAGF().get(0).isEmpty()){
                    where = " AND r.nombre LIKE '%"+session.getFiltrosGestionCCAGF().get(0)+"%'";
                }

                if (session.getFiltrosRecibosCCAGF().get(1).equals("1") && session.getFiltrosRecibosCCAGF().get(2).equals("1")){
                    where += " AND r.tipo IN ('AGF','CC')";
                }
                else if (session.getFiltrosRecibosCCAGF().get(1).equals("1")){
                    where += " AND r.tipo = 'AGF'";
                }
                else if (session.getFiltrosRecibosCCAGF().get(2).equals("1")){
                    where += " AND r.tipo = 'CC'";
                }

                if (session.getFiltrosRecibosCCAGF().get(3).equals("1") && session.getFiltrosRecibosCCAGF().get(4).equals("1")){
                    where += " AND r.estatus IN (1,2)";
                }
                else if (session.getFiltrosRecibosCCAGF().get(3).equals("1")){
                    where += " AND r.estatus = 2";
                }
                else if (session.getFiltrosRecibosCCAGF().get(4).equals("1")){
                    where += " AND r.estatus = 1";
                }

                if (where.length() > 4)
                    getGestionados(where);
                else
                    getGestionados("");
            }
        });

    }

    private View.OnClickListener fbAgregarCC_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (fabExpanded){
                closeSubMenusFab();
            } else {
                openSubMenusFab();
            }
        }
    };

    private View.OnClickListener llCC_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent i_generarCurp = new Intent(ctx, GenerarCurp.class);
            i_generarCurp.putExtra(TIPO, "CC");
            startActivity(i_generarCurp);
        }
    };

    private View.OnClickListener llAGF_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent i_generarCurp = new Intent(ctx, GenerarCurp.class);
            i_generarCurp.putExtra(TIPO, "AGF");
            startActivity(i_generarCurp);
        }
    };

    private void closeSubMenusFab(){
        llCC.setVisibility(View.INVISIBLE);
        llAGF.setVisibility(View.INVISIBLE);
        fbAgregarCC.setImageResource(R.drawable.ic_add_black);
        fabExpanded = false;
    }

    //Opens FAB submenus
    private void openSubMenusFab(){
        llCC.setVisibility(View.VISIBLE);
        llAGF.setVisibility(View.VISIBLE);

        //Change settings icon to 'X' icon
        fbAgregarCC.setImageResource(R.drawable.ic_close_black);
        fabExpanded = true;
    }

    private void getPrestamos( String where){
        Log.e("WHERE", where+ " asd");
        rvClientesCC.setAdapter(null);
        String sql = "SELECT p.*,COALESCE(rr.estatus, -1) AS estatus FROM " + TBL_PRESTAMOS + " AS p " +
                "LEFT JOIN " + TBL_RECUPERACION_RECIBOS + " AS rr ON rr.grupo_id = p.grupo_id AND rr.num_solicitud = p.num_solicitud" + where;
        Log.e("SqlCC", sql);
        final Cursor row = db.rawQuery(sql, null);
        Log.e("SqlCC", "Count "+ row.getCount());
        if (row.getCount() > 0){
            row.moveToFirst();
            ArrayList<HashMap<Integer, String>> prestamos = new ArrayList<>();
            for (int i = 0; i < row.getCount(); i++){
                HashMap<Integer, String> items = new HashMap<>();
                items.put(0, row.getString(1)); //NOMBRE GRUPO
                items.put(1, row.getString(2)); //GRUPO ID
                items.put(2, row.getString(3)); //CLIENTE ID
                items.put(3, row.getString(4)); //NUM SOLICITUD
                items.put(4, row.getString(5)); //PERIODICIDAD
                items.put(5, row.getString(6)); //NUM PAGOS
                items.put(6, row.getString(7)); //ESTADO NACIMIENTO ID
                items.put(7, row.getString(8)); //GENERO
                items.put(8, row.getString(9)); //NOMBRE CLIENTE
                items.put(9, row.getString(10)); //FECHA NACIMIENTO
                items.put(10, row.getString(11)); //EDAD
                items.put(11, row.getString(12)); //MONTOS
                items.put(12, row.getString(13)); //ESTATUS RECUPERACION
                prestamos.add(items);
                row.moveToNext();
            }

            adapter_prestamos_agf adapter = new adapter_prestamos_agf(ctx, prestamos, new adapter_prestamos_agf.Event() {
                @Override
                public void AgfOnClick(HashMap<Integer, String> item) {
                    //Toast.makeText(ctx, item.get(11), Toast.LENGTH_SHORT).show();
                    String nombre = "";
                    String nombreFirma = "";
                    int edad = 0;
                    String monto = "";
                    int meses = 0;
                    int totalIntegrantes = 0;
                    boolean printRecibo = true; // bandera para saber si se imprime saber si el monto otorgado es menor o igual a 29 mil

                    if (Long.parseLong(item.get(1)) > 1) { //Grupales

                        nombre = item.get(0);
                        String[] nomIntegrantes = item.get(8).split(",");
                        String[] edades = item.get(10).split(",");
                        String[] montos = item.get(11).split(",");
                        int integrantes = 0;

                        //totalIntegrantes = edades.length;

                        Log.e("TotalIntegrantes", "Total: "+edades.length);
                        for (int i = 0; i < edades.length; i++){
                            Log.e("EdadGpo", edades[i]);
                            if (Integer.parseInt(edades[i]) < 80){
                                Log.e("XXXXXXX",montos[i]+"   "+(Long.parseLong(montos[i]) <= 29000));
                                if (Long.parseLong(montos[i]) <= 29000)
                                    integrantes += 1;
                            }

                            if (nomIntegrantes[i].contains("3"))
                                nombreFirma = nomIntegrantes[i].substring(1);
                        }

                        totalIntegrantes = integrantes;
                        meses = (Integer.parseInt(item.get(4)) * Integer.parseInt(item.get(5))) / 30;

                        double seguroAGF = 15;
                        for (int i = 0; i < session.getSucursales().length(); i++) {
                            try {
                                JSONObject sucursales = session.getSucursales().getJSONObject(i);
                                if (sucursales.getString("nombre").equals("2.2 MECAPALAPA") || sucursales.getString("nombre").equals("2.3 LA MESA")) {
                                    seguroAGF = 12.5;
                                    break;
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        monto = String.valueOf((meses * seguroAGF) * integrantes);

                    }
                    else{ //Individuales
                        nombre = item.get(8).substring(1);
                        nombreFirma = item.get(8).substring(1);
                        edad = Integer.parseInt(item.get(10));
                        printRecibo = Long.parseLong(item.get(11)) <= 29000;

                        meses = (Integer.parseInt(item.get(4)) * Integer.parseInt(item.get(5))) / 30;

                        Log.e("MESES", String.valueOf(meses));

                        double seguroAGF = 15;
                        for (int i = 0; i < session.getSucursales().length(); i++) {
                            try {
                                JSONObject sucursales = session.getSucursales().getJSONObject(i);
                                if (sucursales.getString("nombre").equals("2.2 MECAPALAPA") || sucursales.getString("nombre").equals("2.3 LA MESA")) {
                                    seguroAGF = 12.5;
                                    break;
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        monto = String.valueOf((meses * seguroAGF));
                    }


                    Log.e("Integrantes", "XXXXX: "+totalIntegrantes);
                    Log.e("Monto", "XXXXX: "+monto);

                    if ((Long.parseLong(item.get(1)) == 1 && edad < 80) || Long.parseLong(item.get(1)) > 1) {
                        Intent i_formato_recibo = new Intent(ctx, RecuperacionRecibos.class);
                        i_formato_recibo.putExtra("item", item);
                        i_formato_recibo.putExtra("integrantes", totalIntegrantes);
                        i_formato_recibo.putExtra("grupo_id", item.get(1));
                        i_formato_recibo.putExtra("num_solicitud", item.get(3));
                        i_formato_recibo.putExtra("nombre", nombre);
                        i_formato_recibo.putExtra("nombre_firma", nombreFirma);
                        i_formato_recibo.putExtra("monto", monto);
                        i_formato_recibo.putExtra("tipo", "AGF");
                        i_formato_recibo.putExtra("meses", meses);
                        i_formato_recibo.putExtra("res_impresion", 0);
                        i_formato_recibo.putExtra("is_reeimpresion", false);
                        i_formato_recibo.putExtra("print_recibo", printRecibo);
                        i_formato_recibo.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i_formato_recibo);
                    }
                    else{
                        Toast.makeText(ctx, "No se hace cobro de apoyo a gastos funerarios", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            rvClientesCC.setAdapter(adapter);
        }
        row.close();

    }

    private void getRecibos( String where){
        rvClientesCC.setAdapter(null);
        String sql = "SELECT r.nombre, r.folio, r.fecha_impresion, r.fecha_envio, r.tipo_recibo, CASE(SELECT COUNT(*) FROM "+TBL_RECIBOS_AGF_CC+" AS r2 WHERE r2.folio = r.folio) WHEN 1 THEN 'O' ELSE 'O|C' END AS impresiones FROM "+TBL_RECIBOS_AGF_CC+" r LEFT JOIN "+TBL_RECIBOS_AGF_CC+" p ON p.num_solicitud = r.num_solicitud AND p.grupo_id = r.grupo_id WHERE r.tipo_impresion = (SELECT r3.tipo_impresion FROM "+TBL_RECIBOS_AGF_CC+" AS r3 WHERE r3.folio = r.folio ORDER BY r3.tipo_impresion DESC LIMIT 1) "+where+" group by r.nombre, r.folio, r.fecha_impresion, r.fecha_envio, r.tipo_recibo ORDER BY r.fecha_impresion DESC";
        //String sql = "SELECT r.nombre, r.folio, r.fecha_impresion, r.fecha_envio, r.tipo_recibo, r.tipo_impresion FROM " + TBL_RECIBOS_AGF_CC + " AS r";
        Cursor row = db.rawQuery(sql, null);
        Log.e("CountRecibos", "Total: "+row.getCount());
        if (row.getCount() > 0){
            row.moveToFirst();

            ArrayList<HashMap<Integer,String>> data = new ArrayList<>();
            for(int i = 0; i < row.getCount(); i++){
                HashMap<Integer,String> ticketCC = new HashMap<>();

                ticketCC.put(0,row.getString(0));
                ticketCC.put(1,row.getString(1));
                ticketCC.put(2,row.getString(2));
                ticketCC.put(3,row.getString(3));
                ticketCC.put(4,row.getString(4));
                ticketCC.put(5,row.getString(5));

                data.add(ticketCC);
                row.moveToNext();
            }

            adapter_clientes_cc adapter = new adapter_clientes_cc(ctx, data);

            rvClientesCC.setAdapter(adapter);
        }
        row.close();
    }

    private void getGestionados( String where){
        rvClientesCC.setAdapter(null);
        String sql = "SELECT r.*, COALESCE(r1.folio, '') FROM " + TBL_RECUPERACION_RECIBOS + " AS r LEFT JOIN "+TBL_RECIBOS_AGF_CC+" AS r1 on r1.grupo_id = r.grupo_id AND r1.num_solicitud = r.num_solicitud AND r1.tipo_recibo = r.tipo AND r1.tipo_impresion = 'O'  WHERE r.estatus in (1,2) "+where+" ORDER BY r.fecha_termino DESC";
        Log.e("sqlGestionadas", sql);
        final Cursor row = db.rawQuery(sql, null);
        if (row.getCount() > 0){
            row.moveToFirst();
            ArrayList<HashMap<Integer, String>> prestamos = new ArrayList<>();
            for (int i = 0; i < row.getCount(); i++){
                HashMap<Integer, String> items = new HashMap<>();
                items.put(0, row.getString(9)); //NOMBRE
                items.put(1, row.getString(8)); //TIPO
                items.put(2, row.getString(11)); //MONTO
                items.put(3, row.getString(3)); //MEDIO PAGO
                Log.e("Value", String.valueOf(row.getString(3).equals("EFECTIVO") && row.getString(12).equals("NO")));
                Log.e("Valores",row.getString(3).equals("EFECTIVO") +"  "+ row.getString(12).equals("NO")+"   "+row.getString(15));
                if (row.getString(3).equals("EFECTIVO") && row.getString(12).equals("NO"))
                    items.put(4, row.getString(13)); //FOLIO manual
                else
                    items.put(4, row.getString(15)); //FOLIO
                items.put(5, row.getString(6)); //FECHA TERMINO
                items.put(6, row.getString(7)); //FECHA ENVIO
                prestamos.add(items);
                row.moveToNext();
            }

            adapter_gestiones_agf_cc adapter = new adapter_gestiones_agf_cc(ctx, prestamos);
            rvClientesCC.setAdapter(adapter);
        }
        row.close();
    }

    private void GetClientes (){
        String sql = "SELECT grupo_id, nombre_grupo, nombre_cliente FROM "+TBL_PRESTAMOS;
        Cursor row = db.rawQuery(sql, null);
        nombre = new ArrayList<>();
        if (row.getCount() > 0){
            row.moveToFirst();
            dataNombre = new String[row.getCount()];
            for(int i = 0; i < row.getCount(); i++){
                if (row.getLong(0) > 1)
                    nombre.add(row.getString(1));
                else
                    nombre.add(row.getString(2).substring(1));
                row.moveToNext();
            }
            dataNombre = Miscellaneous.RemoverRepetidos(nombre);

            adapterNombre = new ArrayAdapter<>(ctx,
                    R.layout.custom_list_item, R.id.text_view_list_item, dataNombre);
        }
        else {
            dataNombre = new String[1];
            dataNombre[0] = "";
            adapterNombre = new ArrayAdapter<>(ctx,
                    R.layout.custom_list_item, R.id.text_view_list_item, dataNombre);
        }
        row.close();
    }

    private void GetClientesRecibos(){
        String sql = "SELECT nombre FROM "+TBL_RECIBOS_AGF_CC;
        Cursor row = db.rawQuery(sql, null);
        nombreRec = new ArrayList<>();
        if (row.getCount() > 0){
            row.moveToFirst();
            dataNombreRec = new String[row.getCount()];
            for(int i = 0; i < row.getCount(); i++){
                nombreRec.add(row.getString(0));
                row.moveToNext();
            }
            dataNombreRec = Miscellaneous.RemoverRepetidos(nombreRec);

            adapterNombreRec = new ArrayAdapter<>(ctx,
                    R.layout.custom_list_item, R.id.text_view_list_item, dataNombreRec);
        }
        else {
            dataNombreRec = new String[1];
            dataNombreRec[0] = "";
            adapterNombreRec = new ArrayAdapter<>(ctx,
                    R.layout.custom_list_item, R.id.text_view_list_item, dataNombreRec);
        }
        row.close();
    }

    private void GetClientesGestiones(){
        String sql = "SELECT nombre FROM "+TBL_RECUPERACION_RECIBOS;
        Cursor row = db.rawQuery(sql, null);
        nombreGes = new ArrayList<>();
        if (row.getCount() > 0){
            row.moveToFirst();
            dataNombreGes = new String[row.getCount()];
            for(int i = 0; i < row.getCount(); i++){
                nombreGes.add(row.getString(0));
                row.moveToNext();
            }
            dataNombreGes = Miscellaneous.RemoverRepetidos(nombreGes);

            adapterNombreGes = new ArrayAdapter<>(ctx,
                    R.layout.custom_list_item, R.id.text_view_list_item, dataNombreGes);
        }
        else {
            dataNombreGes = new String[1];
            dataNombreGes[0] = "";
            adapterNombreGes = new ArrayAdapter<>(ctx,
                    R.layout.custom_list_item, R.id.text_view_list_item, dataNombreGes);
        }
        row.close();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void Filtros (){
        int view = 0;
        int sizeH = 0;
        switch (tipoSeccion){
            case 1:
                sizeH = 600;
                view = R.layout.sheet_dialog_filtros_prestamos_cc_agf;
                break;
            case 2:
                sizeH = 970;
                view = R.layout.sheet_dialog_filtros_recibos_cc_agf;
                break;
            case 3:
                sizeH = 900;
                view = R.layout.sheet_dialog_filtros_gestiones_cc_agf;
                break;
        }
        DialogPlus filtros_dg = DialogPlus.newDialog(ctx)
                .setContentHolder(new ViewHolder(view))
                .setGravity(Gravity.TOP)
                .setPadding(20,40,20,10)
                .setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(DialogPlus dialog, View view) {
                        String where = "";
                        cont_filtros = 0;
                        cont_filtros_recibo = 0;
                        cont_filtros_ges = 0;
                        HashMap<String, String> filtros = new HashMap<>();
                        InputMethodManager imm = (InputMethodManager)ctx.getSystemService(Context.INPUT_METHOD_SERVICE);
                        switch (view.getId()) {
                            case R.id.btnFiltrar:
                                switch (tipoSeccion){
                                    case 1:
                                        if (!aetNombre.getText().toString().trim().isEmpty()){
                                            filtros.put("nombre_cc_agf",aetNombre.getText().toString().trim());
                                            cont_filtros += 1;
                                            where = " AND (p.nombre_grupo LIKE '%"+aetNombre.getText().toString().trim()+"%' OR p.nombre_cliente LIKE '%"+aetNombre.getText().toString().trim()+"%')";
                                        }
                                        else filtros.put("nombre_cc_agf","");

                                        if (cbInd.isChecked() && cbGpo.isChecked()){
                                            filtros.put("tipo_prestamo_ind_cc_agf","1");
                                            filtros.put("tipo_prestamo_gpo_cc_agf","1");
                                            cont_filtros += 2;
                                            where += " AND (p.grupo_id = 1 OR p.grupo_id > 1)";
                                        }
                                        else if (cbInd.isChecked()){
                                            filtros.put("tipo_prestamo_ind_cc_agf","1");
                                            filtros.put("tipo_prestamo_gpo_cc_agf","0");
                                            cont_filtros += 1;
                                            where += " AND p.grupo_id = 1";
                                        }
                                        else if (cbGpo.isChecked()){
                                            filtros.put("tipo_prestamo_ind_cc_agf","0");
                                            filtros.put("tipo_prestamo_gpo_cc_agf","1");
                                            cont_filtros += 1;
                                            where += " AND p.grupo_id > 1";
                                        }else {
                                            filtros.put("tipo_prestamo_ind_cc_agf","0");
                                            filtros.put("tipo_prestamo_gpo_cc_agf","0");
                                        }
                                        filtros.put("contador_cc_agf", String.valueOf(cont_filtros));
                                        session.setFiltrosCCAGF(filtros);

                                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                                        Log.e("where",where);
                                        if (where.length() > 4)
                                            getPrestamos(" WHERE" + where.substring(4) + " AND (rr._id is null or rr.estatus = 0)");
                                        else
                                            getPrestamos(" WHERE (rr._id is null or rr.estatus = 0)");
                                        break;
                                    case 2:
                                        if (!aetNombreRec.getText().toString().trim().isEmpty()){
                                            filtros.put("nombre_recibo_cc_agf",aetNombreRec.getText().toString().trim());
                                            cont_filtros_recibo += 1;
                                            where = " AND nombre LIKE '%"+aetNombreRec.getText().toString().trim()+"%'";
                                        }
                                        else filtros.put("nombre_recibo_cc_agf","");

                                        if (!aetFolio.getText().toString().trim().isEmpty()){
                                            filtros.put("folio_recibo_cc_agf",aetFolio.getText().toString().trim());
                                            cont_filtros_recibo += 1;
                                            where = " AND folio = "+aetFolio.getText().toString().trim();
                                        }
                                        else filtros.put("folio_recibo_cc_agf","");


                                        if (cbAgf.isChecked() && cbCc.isChecked()){
                                            filtros.put("recibo_agf","1");
                                            filtros.put("recibo_cc","1");
                                            cont_filtros_recibo += 2;
                                            where += " AND tipo_recibo IN ('AGF','CC')";
                                        }
                                        else if (cbAgf.isChecked()){
                                            filtros.put("recibo_agf","1");
                                            filtros.put("recibo_cc","0");
                                            cont_filtros_recibo += 1;
                                            where += " AND tipo_recibo = 'AGF'";
                                        }
                                        else if (cbCc.isChecked()){
                                            filtros.put("recibo_agf","0");
                                            filtros.put("recibo_cc","1");
                                            cont_filtros_recibo += 1;
                                            where += " AND tipo_recibo = 'CC'";
                                        }else {
                                            filtros.put("recibo_agf","0");
                                            filtros.put("recibo_cc","0");
                                        }

                                        if (cbEnv.isChecked() && cbPen.isChecked()){
                                            filtros.put("recibo_enviada_cc_agf","1");
                                            filtros.put("recibo_pendiente_cc_agf","1");
                                            cont_filtros_recibo += 2;
                                            where += " AND estatus IN (0,1)";
                                        }
                                        else if (cbEnv.isChecked()){
                                            filtros.put("recibo_enviada_cc_agf","1");
                                            filtros.put("recibo_pendiente_cc_agf","0");
                                            cont_filtros_recibo += 1;
                                            where += " AND estatus = 1";
                                        }
                                        else if (cbPen.isChecked()){
                                            filtros.put("recibo_enviada_cc_agf","0");
                                            filtros.put("recibo_pendiente_cc_agf","1");
                                            cont_filtros_recibo += 1;
                                            where += " AND estatus = 0";
                                        }else {
                                            filtros.put("recibo_enviada_cc_agf","0");
                                            filtros.put("recibo_pendiente_cc_agf","0");
                                        }

                                        filtros.put("contador_recibo_cc_agf", String.valueOf(cont_filtros_recibo));
                                        session.setFiltrosRecibosCCAGF(filtros);

                                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                                        Log.e("where",where);
                                        if (where.length() > 4)
                                            getRecibos(where);
                                        else
                                            getRecibos("");
                                        break;
                                    case 3:
                                        if (!aetNombreGes.getText().toString().trim().isEmpty()){
                                            filtros.put("nombre_gestion_cc_agf",aetNombreGes.getText().toString().trim());
                                            cont_filtros_ges += 1;
                                            where = " AND r.nombre LIKE '%"+aetNombreGes.getText().toString().trim()+"%'";
                                        }
                                        else filtros.put("nombre_gestion_cc_agf","");

                                        if (cbAgfGes.isChecked() && cbCcGes.isChecked()){
                                            filtros.put("gestion_agf","1");
                                            filtros.put("gestion_cc","1");
                                            cont_filtros_ges += 2;
                                            where += " AND r.tipo IN ('AGF','CC')";
                                        }
                                        else if (cbAgfGes.isChecked()){
                                            filtros.put("gestion_agf","1");
                                            filtros.put("gestion_cc","0");
                                            cont_filtros_ges += 1;
                                            where += " AND r.tipo = 'AGF'";
                                        }
                                        else if (cbCcGes.isChecked()){
                                            filtros.put("gestion_agf","0");
                                            filtros.put("gestion_cc","1");
                                            cont_filtros_ges += 1;
                                            where += " AND r.tipo = 'CC'";
                                        }else {
                                            filtros.put("gestion_agf","0");
                                            filtros.put("gestion_cc","0");
                                        }

                                        if (cbEnvGes.isChecked() && cbPenGes.isChecked()){
                                            filtros.put("gestion_enviada_cc_agf","1");
                                            filtros.put("gestion_pendiente_cc_agf","1");
                                            cont_filtros_ges += 2;
                                            where += " AND r.estatus IN (1,2)";
                                        }
                                        else if (cbEnvGes.isChecked()){
                                            filtros.put("gestion_enviada_cc_agf","1");
                                            filtros.put("gestion_pendiente_cc_agf","0");
                                            cont_filtros_ges += 1;
                                            where += " AND r.estatus = 2";
                                        }
                                        else if (cbPenGes.isChecked()){
                                            filtros.put("gestion_enviada_cc_agf","0");
                                            filtros.put("gestion_pendiente_cc_agf","1");
                                            cont_filtros_ges += 1;
                                            where += " AND r.estatus = 1";
                                        }else {
                                            filtros.put("gestion_enviada_cc_agf","0");
                                            filtros.put("gestion_pendiente_cc_agf","0");
                                        }

                                        filtros.put("gestion_recibo_cc_agf", String.valueOf(cont_filtros_ges));
                                        session.setFiltrosGestionesCCAGF(filtros);

                                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                                        Log.e("where",where);
                                        if (where.length() > 4)
                                            getGestionados(where);
                                        else
                                            getGestionados("");
                                        break;
                                }

                                dialog.dismiss();

                                break;
                            case R.id.btnBorrar:
                                switch (tipoSeccion){
                                    case 1:
                                        cbInd.setChecked(false);
                                        cbGpo.setChecked(false);
                                        aetNombre.setText("");

                                        cont_filtros = 0;
                                        filtros = new HashMap<>();
                                        filtros.put("nombre_cc_agf","");
                                        filtros.put("tipo_prestamo_ind_cc_agf","0");
                                        filtros.put("tipo_prestamo_gpo_cc_agf","0");
                                        filtros.put("contador_cc_agf", "0");
                                        session.setFiltrosCCAGF(filtros);

                                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                                        getPrestamos(" WHERE (rr._id is null or rr.estatus = 0)");

                                        aetNombre.setAdapter(adapterNombre);
                                        break;
                                    case 2:
                                        cbAgf.setChecked(false);
                                        cbCc.setChecked(false);
                                        cbEnv.setChecked(false);
                                        cbPen.setChecked(false);
                                        aetNombreRec.setAdapter(adapterNombreRec);
                                        aetFolio.setText("");

                                        cont_filtros_recibo = 0;
                                        filtros = new HashMap<>();
                                        filtros.put("nombre_recibo_cc_agf","");
                                        filtros.put("folio_recibo_cc_agf","");
                                        filtros.put("recibo_agf","0");
                                        filtros.put("recibo_cc","0");
                                        filtros.put("recibo_enviada_cc_agf","0");
                                        filtros.put("recibo_pendiente_cc_agf","0");
                                        filtros.put("contador_recibo_cc_agf", "0");
                                        session.setFiltrosRecibosCCAGF(filtros);

                                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                                        getRecibos("");

                                        break;
                                    case 3:
                                        cbAgfGes.setChecked(false);
                                        cbCcGes.setChecked(false);
                                        cbEnvGes.setChecked(false);
                                        cbPenGes.setChecked(false);
                                        aetNombreGes.setAdapter(adapterNombreGes);

                                        cont_filtros_ges = 0;
                                        filtros = new HashMap<>();
                                        filtros.put("nombre_recibo_cc_agf","");
                                        filtros.put("folio_recibo_cc_agf","");
                                        filtros.put("recibo_agf","0");
                                        filtros.put("recibo_cc","0");
                                        filtros.put("recibo_enviada_cc_agf","0");
                                        filtros.put("recibo_pendiente_cc_agf","0");
                                        filtros.put("contador_recibo_cc_agf", "0");
                                        session.setFiltrosGestionesCCAGF(filtros);

                                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                                        getGestionados("");
                                        break;
                                }

                                //dialog.dismiss();

                                break;
                        }
                        setupBadge();

                    }
                })
                .setExpanded(true, sizeH)
                .create();

        switch (tipoSeccion){
            case 1:
                aetNombre   = filtros_dg.getHolderView().findViewById(R.id.aetNombre);
                cbInd       = filtros_dg.getHolderView().findViewById(R.id.cbInd);
                cbGpo       = filtros_dg.getHolderView().findViewById(R.id.cbGpo);

                aetNombre.setAdapter(adapterNombre);

                aetNombre.setOnTouchListener(new View.OnTouchListener() {
                    @SuppressLint("ClickableViewAccessibility")
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        aetNombre.showDropDown();
                        return false;
                    }
                });

                if (!session.getFiltrosCCAGF().get(0).isEmpty())
                    aetNombre.setText(session.getFiltrosCCAGF().get(0));
                if (session.getFiltrosCCAGF().get(1).equals("1"))
                    cbGpo.setChecked(true);
                if (session.getFiltrosCCAGF().get(2).equals("1"))
                    cbInd.setChecked(true);
                break;
            case 2:
                aetNombreRec   = filtros_dg.getHolderView().findViewById(R.id.aetNombreRecibo);
                aetFolio   = filtros_dg.getHolderView().findViewById(R.id.aetFolio);
                cbAgf          = filtros_dg.getHolderView().findViewById(R.id.cbAGF);
                cbCc           = filtros_dg.getHolderView().findViewById(R.id.cbCC);
                cbEnv          = filtros_dg.getHolderView().findViewById(R.id.cbEnv);
                cbPen          = filtros_dg.getHolderView().findViewById(R.id.cbPen);

                aetNombreRec.setAdapter(adapterNombreRec);

                aetNombreRec.setOnTouchListener(new View.OnTouchListener() {
                    @SuppressLint("ClickableViewAccessibility")
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        aetNombreRec.showDropDown();
                        return false;
                    }
                });

                if (!session.getFiltrosRecibosCCAGF().get(0).isEmpty())
                    aetNombreRec.setText(session.getFiltrosRecibosCCAGF().get(0));
                if (!session.getFiltrosRecibosCCAGF().get(1).isEmpty())
                    aetFolio.setText(session.getFiltrosRecibosCCAGF().get(1));
                if (session.getFiltrosRecibosCCAGF().get(2).equals("1"))
                    cbAgf.setChecked(true);
                if (session.getFiltrosRecibosCCAGF().get(3).equals("1"))
                    cbCc.setChecked(true);
                if (session.getFiltrosRecibosCCAGF().get(4).equals("1"))
                    cbEnv.setChecked(true);
                if (session.getFiltrosRecibosCCAGF().get(5).equals("1"))
                    cbPen.setChecked(true);
                break;
            case 3:
                aetNombreGes   = filtros_dg.getHolderView().findViewById(R.id.aetNombreGes);
                cbAgfGes       = filtros_dg.getHolderView().findViewById(R.id.cbAGFges);
                cbCcGes        = filtros_dg.getHolderView().findViewById(R.id.cbCCges);
                cbEnvGes       = filtros_dg.getHolderView().findViewById(R.id.cbEnvGes);
                cbPenGes       = filtros_dg.getHolderView().findViewById(R.id.cbPenGes);

                aetNombreGes.setAdapter(adapterNombreGes);

                aetNombreGes.setOnTouchListener(new View.OnTouchListener() {
                    @SuppressLint("ClickableViewAccessibility")
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        aetNombreGes.showDropDown();
                        return false;
                    }
                });

                if (!session.getFiltrosGestionCCAGF().get(0).isEmpty())
                    aetNombreGes.setText(session.getFiltrosGestionCCAGF().get(0));
                if (session.getFiltrosGestionCCAGF().get(1).equals("1"))
                    cbAgfGes.setChecked(true);
                if (session.getFiltrosGestionCCAGF().get(2).equals("1"))
                    cbCcGes.setChecked(true);
                if (session.getFiltrosGestionCCAGF().get(3).equals("1"))
                    cbEnvGes.setChecked(true);
                if (session.getFiltrosGestionCCAGF().get(4).equals("1"))
                    cbPenGes.setChecked(true);
                break;
        }

        filtros_dg.show();
    }

    private void setupBadge() {
        switch (tipoSeccion){
            case 2:
                if (tvContFiltros != null) {
                    tvContFiltros.setText(String.valueOf(session.getFiltrosRecibosCCAGF().get(6)));
                    tvContFiltros.setVisibility(View.VISIBLE);
                }
                break;
            case 3:
                if (tvContFiltros != null) {
                    tvContFiltros.setText(String.valueOf(session.getFiltrosGestionCCAGF().get(5)));
                    tvContFiltros.setVisibility(View.VISIBLE);
                }
                break;
            default: //1
                Log.e("TVFILTRO", String.valueOf(tvContFiltros != null));
                if (tvContFiltros != null) {
                    tvContFiltros.setText(String.valueOf(session.getFiltrosCCAGF().get(3)));
                    tvContFiltros.setVisibility(View.VISIBLE);
                }
                break;
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_filtro, menu);

        final MenuItem menuItem = menu.findItem(R.id.nvFiltro);
        View actionView = menuItem.getActionView();
        tvContFiltros = actionView.findViewById(R.id.filtro_bagde);
        actionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOptionsItemSelected(menuItem);
            }
        });

        setupBadge();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.nvFiltro:
                Filtros();
                break;
            case R.id.nvInfo:
                Intent i_resumen_imp = new Intent(ctx, ResumenCCAGF.class);
                startActivity(i_resumen_imp);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        GetClientes();
        GetClientesRecibos();
        GetClientesGestiones();
        Log.e("TipoSeccion", String.valueOf(tipoSeccion));

        rbPrestamos.setChecked(true);

        String where = "";
        if (!session.getFiltrosCCAGF().get(0).isEmpty())
            where = " AND p.nombre_grupo LIKE '%"+session.getFiltrosCCAGF().get(0).trim()+"%' OR p.nombre_cliente LIKE '%"+session.getFiltrosCCAGF().get(0).trim()+"%'";

        if (session.getFiltrosCCAGF().get(1).equals("1") && session.getFiltrosCCAGF().get(2).equals("1")){
            where += " AND (p.grupo_id = 1 OR p.grupo_id > 1)";
        }
        else if (session.getFiltrosCCAGF().get(2).equals("1")){
            where += " AND p.grupo_id = 1";
        }
        else if (session.getFiltrosCCAGF().get(1).equals("1")){
            where += " AND p.grupo_id > 1";
        }

        if (where.length() > 4)
            getPrestamos(" WHERE" + where.substring(4) + " AND (rr._id is null or rr.estatus = 0)");
        else
            getPrestamos(" WHERE (rr._id is null or rr.estatus = 0)");

    }
}
