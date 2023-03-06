package com.sidert.sidertmovil.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.ColorDrawable;
/*import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.view.MenuItemCompat;*/
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.adapters.adapter_inicio_sesion;
import com.sidert.sidertmovil.database.DBhelper;
import com.sidert.sidertmovil.fragments.calculadoraPrestamo;
import com.sidert.sidertmovil.fragments.dialogs.dialog_date_picker;
import com.sidert.sidertmovil.models.MLogLogin;
import com.sidert.sidertmovil.models.MSucursal;
import com.sidert.sidertmovil.utils.Constants;
import com.sidert.sidertmovil.utils.ManagerInterface;
import com.sidert.sidertmovil.utils.Miscellaneous;
import com.sidert.sidertmovil.utils.NetworkStatus;
import com.sidert.sidertmovil.utils.Popups;
import com.sidert.sidertmovil.utils.RetrofitClient;
import com.sidert.sidertmovil.utils.SessionManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.sidert.sidertmovil.utils.Constants.CONTROLLER_API;
import static com.sidert.sidertmovil.utils.Constants.FECHA;
import static com.sidert.sidertmovil.utils.Constants.FORMAT_DATE_GNRAL;
import static com.sidert.sidertmovil.utils.Constants.TBL_REPORTE_SESIONES;
import static com.sidert.sidertmovil.utils.Constants.TBL_SUCURSALES;
import static com.sidert.sidertmovil.utils.NameFragments.DIALOGDATEPICKER;

/**clase para ver los inicios de sesion de los asesores*/
public class ReporteInicioSesion extends AppCompatActivity {

    private Context ctx;
    private Toolbar tbMain;

    private RecyclerView rvLogin;
    private adapter_inicio_sesion adatper;

    private TextView tvFecha;
    private TextView tvRegion;
    private TextView tvSucursal;
    private TextView tvAsesorGestor;


    private String[] sucursales;
    private boolean[] checkSucursales;
    private List<String> sucursalesChecked = new ArrayList<>();

    private String[] asesoresGestores;
    private boolean[] checkAsesores;
    private List<String> asesoresChecked = new ArrayList<>();

    private String[] regiones = new String[]{" TODOS", "1.VERACRUZ", "2.PUEBLA", "3.TLAXCALA"};
    private List<String> regionesChecked = new ArrayList<>();
    private boolean[] checkRegiones = new boolean[]{false, false, false, false};

    private SessionManager session;
    private ArrayList<MLogLogin> data;

    private DBhelper dBhelper;
    private SQLiteDatabase db;

    private String nombres_asesores = "";
    private String nombres_sucursales = "";
    private String nombres_regiones = "";

    private String fechaSelect = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reporte_inicio_sesion);
        ctx = this;

        dBhelper = new DBhelper(ctx);
        db = dBhelper.getWritableDatabase();

        session = new SessionManager(ctx);

        tbMain = findViewById(R.id.tbMain);

        setSupportActionBar(tbMain);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        rvLogin = findViewById(R.id.rvLogin);
        rvLogin.setLayoutManager(new LinearLayoutManager(ctx));
        rvLogin.setHasFixedSize(false);

        /**valida si tiene conexion a internet para consumir el servicio para obtener las sucursales*/
        if (NetworkStatus.haveNetworkConnection(ctx)){
            /**funcion para obtener las sucursales*/
            GetMisSucursales();
        }
        else{
            /**En caso de no tener conexion a internet muestra un mensaje y despues cierra la vista*/
            final AlertDialog not_network = Popups.showDialogMessage(ctx, Constants.not_network,
                    R.string.not_network, R.string.accept, new Popups.DialogMessage() {
                        @Override
                        public void OnClickListener(AlertDialog dialog) {
                            dialog.dismiss();
                            finish();
                        }
                    });
            not_network.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            not_network.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            not_network.show();
        }
    }

    /**Funcion que realiza la peticion para obtener a las sucursales que tiene acceso el usuario*/
    public void GetMisSucursales(){

        /**se crea un dialogo de loading en lo que se espera la respuesta*/
        final AlertDialog loading = Popups.showLoadingDialog(ctx, R.string.please_wait, R.string.loading_info);
        loading.show();

        /**se prepara la interfaz para realizar la peticion*/
        ManagerInterface api = new RetrofitClient().generalRF(CONTROLLER_API, ctx).create(ManagerInterface.class);

        /**se prepara el servicio con los parametros*/
        Call<List<MSucursal>> call = api.getMisSucursales(Integer.parseInt(session.getUser().get(9)));

        /**se realiza la peticion al servidor para obtener las sucursales que tiene acceso el usuario*/
        call.enqueue(new Callback<List<MSucursal>>() {
            @Override
            public void onResponse(Call<List<MSucursal>> call, Response<List<MSucursal>> response) {
                /**valida los codigos de respuesta del servidor*/
                switch (response.code()){
                    case 200:/**success*/
                        /**coloca la respuesta en una variable*/
                        List<MSucursal> data = response.body();

                        //calculadoraPrestamo n = new calculadoraPrestamo();

                        //n.getSucursalIdA(data);

                        /**se crea un array para colocar las sucursales*/
                        sucursales = new String[data.size() + 1];
                        /**se crea un array para saber cuales sucursales fueron marcadas para los filtros*/
                        checkSucursales = new boolean[data.size()+1];
                        /**agrega un elemento a los array*/
                        sucursales[0] = " TODOS";
                        checkSucursales[0] = false;
                        fechaSelect = Miscellaneous.ObtenerFecha(FECHA.toLowerCase());
                        /**se crea map para los parametros para la peticion de obtencion del log de inicio de sesiones*/
                        HashMap<String, String> params = new HashMap<>();
                        params.put("fecha_inicio", fechaSelect);
                        params.put("fecha_fin", fechaSelect);
                        params.put("region", "0");
                        params.put("asesor_gestor", "0");
                        String ids = "";
                        /**se recorre el listado de las sucursales*/
                        for (int i = 0; i< data.size(); i++){
                            /**se guardan las sucursales*/
                            HashMap<Integer, String> params_suc = new HashMap<>();
                            params_suc.put(0, String.valueOf(data.get(i).getId()));
                            params_suc.put(1, data.get(i).getNombre());
                            params_suc.put(2, String.valueOf(data.get(i).getRegionId()));
                            params_suc.put(3, String.valueOf(data.get(i).getCentrocostoId()));
                            dBhelper.saveSucursales(db, params_suc);

                            sucursales[i+1] = data.get(i).getNombre();
                            checkSucursales[i+1] = false;

                            if (i == 0)
                                ids = String.valueOf(data.get(i).getId());
                            else
                                ids += ","+data.get(i).getId();
                        }
                        params.put("sucursal", ids);
                        Log.e("Paramas", params.toString());
                        /**Despues comenzará a obtener el log de inicio de sesion de los asesores*/
                        GetReporteLogin(params);

                        break;
                    default:
                        Log.e("Error", response.code()+" xxx");
                        break;
                }
                loading.dismiss();
            }

            @Override
            public void onFailure(Call<List<MSucursal>> call, Throwable t) {
                loading.dismiss();
                Log.e("Error", "Ha ocurrido un error, intentar de nuevo");
            }
        });
    }

    /**Funcion para obtener el log de inicio de sesiones*/
    private void GetReporteLogin(final HashMap<String, String> params){
        /**valida si tiene conexion a internet*/
        if (NetworkStatus.haveNetworkConnection(ctx)){
            final AlertDialog loading = Popups.showLoadingDialog(ctx, R.string.please_wait, R.string.loading_info);
            loading.show();

            /**prepara la interfaz para realizar peticiones */
            ManagerInterface api = new RetrofitClient().generalRF(CONTROLLER_API, ctx).create(ManagerInterface.class);

            /**prepara la peticion con los parametros necesarios*/
            Call<List<MLogLogin>> call = api.getLogAsesores(params.get("fecha_inicio"),
                                                            params.get("fecha_fin"),
                                                            params.get("region"),
                                                            params.get("sucursal"),
                                                            params.get("asesor_gestor"));

            /**se realiza la peticion al servidor*/
            call.enqueue(new Callback<List<MLogLogin>>() {
                @Override
                public void onResponse(Call<List<MLogLogin>> call, Response<List<MLogLogin>> response) {
                    /**se valida el codigo de respuesta del servidor*/
                    switch (response.code()){
                        case 200:/**success*/
                            /**coloca la respuesta(listado de inicio de sesiones) en una variable*/
                            List<MLogLogin> logLogin = response.body();
                            data = new ArrayList<>();

                            if (logLogin.size() > 0){
                                /**recorre el listado de inicio de sesiones*/
                                for (MLogLogin item : logLogin){
                                    /**valida que el listado solo sera para usuarios de tipo ASESOR y GESTOR para eliminar a los GERENTES, CORDINADORES, etc*/
                                    if (item.getUsuario().contains("ASESOR") || item.getUsuario().contains("GESTOR")) {
                                        /**Guarda el log de inicio de sesion de los asesores y gestores*/
                                        HashMap<Integer, String> params_sesion = new HashMap<>();
                                        params_sesion.put(0, String.valueOf(item.getId()));
                                        params_sesion.put(1, item.getSerieId());
                                        params_sesion.put(2, item.getNombreAsesor());
                                        params_sesion.put(3, item.getUsuario());
                                        params_sesion.put(4, item.getSucursal());
                                        params_sesion.put(5, item.getSucursalid());
                                        params_sesion.put(6, Miscellaneous.validStr(item.getHorariologin()));
                                        params_sesion.put(7, Miscellaneous.validStr(item.getNivelbateria()));
                                        params_sesion.put(8, Miscellaneous.validStr(item.getVersionapp()));
                                        params_sesion.put(9, Miscellaneous.validStr(item.getPrimeragestion()));
                                        params_sesion.put(10, Miscellaneous.validStr(item.getUltimagestion()));
                                        params_sesion.put(11, Miscellaneous.validStr(item.getTotalgestiones()));

                                        dBhelper.saveReporteSesiones(db, params_sesion);
                                    }
                                }/**Fin ciclo for*/

                                /**Consulta para obtener el log de inicio de sesiones de los asesores y gestores*/
                                String sql = "SELECT * FROM " + TBL_REPORTE_SESIONES + " ORDER BY sucursal, horariologin";

                                Cursor row = db.rawQuery(sql, null);
                                row.moveToFirst();
                                data = new ArrayList<>();
                                asesoresGestores = new String[row.getCount()+1];
                                checkAsesores = new boolean[row.getCount()+1];
                                asesoresGestores[0] = "TODOS";
                                checkAsesores[0] = false;
                                /**recorre el resultado de la consulta para agregarlo a una lista*/
                                for(int i = 0; i < row.getCount(); i++){
                                    asesoresGestores[i+1] = row.getString(3);
                                    checkAsesores[i+1] = false;

                                    MLogLogin item = new MLogLogin();
                                    item.setId(row.getInt(1));
                                    item.setSerieId(row.getString(2));
                                    item.setNombreAsesor(row.getString(3));
                                    item.setUsuario(row.getString(4));
                                    item.setSucursal(row.getString(5));
                                    item.setSucursalid(row.getString(6));
                                    item.setHorariologin(row.getString(7));
                                    item.setNivelbateria(row.getString(8));
                                    item.setVersionapp(row.getString(9));
                                    item.setPrimeragestion(row.getString(10));
                                    item.setUltimagestion(row.getString(11));
                                    item.setTotalgestiones(row.getString(12));
                                    data.add(item);
                                    row.moveToNext();

                                }

                                /**coloca el listado en el adaptador*/
                                adatper = new adapter_inicio_sesion(ctx, data);
                                rvLogin.setAdapter(adatper);

                            }
                            break;
                    }
                    loading.dismiss();
                }

                @Override
                public void onFailure(Call<List<MLogLogin>> call, Throwable t) {
                    loading.dismiss();
                }
            });
        }
        else{
            /**en caso de no tener internet mostrara un mensaje*/
            final AlertDialog not_network = Popups.showDialogMessage(ctx, Constants.not_network,
                    R.string.not_network, R.string.accept, new Popups.DialogMessage() {
                        @Override
                        public void OnClickListener(AlertDialog dialog) {
                            dialog.dismiss();
                        }
                    });
            not_network.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            not_network.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            not_network.show();
        }
    }

    /**Funcion para aplicar filtros*/
    private void ShowFiltros(){
        BottomSheetDialog sheetDlgFiltros = new BottomSheetDialog(ctx);
        View parentView         = getLayoutInflater().inflate(R.layout.sheet_dialog_filtros_login,null);
        tvFecha        = parentView.findViewById(R.id.tvFecha);
        tvRegion       = parentView.findViewById(R.id.tvRegion);
        tvSucursal     = parentView.findViewById(R.id.tvSucursal);
        tvAsesorGestor = parentView.findViewById(R.id.tvAsesorGestor);
        LinearLayout llRegion = parentView.findViewById(R.id.llRegion);
        LinearLayout llSucursal = parentView.findViewById(R.id.llSucursal);
        Button btnBorrarFiltros = parentView.findViewById(R.id.btnBorrarFiltros);

        tvFecha.setText(fechaSelect);
        tvRegion.setText(nombres_regiones.replace("'", ""));
        tvSucursal.setText(nombres_sucursales.replace("'", ""));
        tvAsesorGestor.setText(nombres_asesores.replace("'",""));

        sheetDlgFiltros.setContentView(parentView);
        BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from((View) parentView.getParent());
        bottomSheetBehavior.setPeekHeight((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,700,getResources().getDisplayMetrics()));
        sheetDlgFiltros.show();

        /**si el usuario contiene estos roles para mostrar un campo de region*/
        if (session.getUser().get(5).contains("ROLE_SUPER") ||
                session.getUser().get(5).contains("ROLE_ANALISTA") ||
                session.getUser().get(5).contains("DIRECCION")){
            llRegion.setVisibility(View.VISIBLE);
        }

        /**si el usuario contiene estos roles para mostrar un campo de sucursales*/
        if (session.getUser().get(5).contains("ROLE_SUPER") ||
                session.getUser().get(5).contains("ROLE_ANALISTA") ||
                session.getUser().get(5).contains("ROLE_DIRECCION") ||
                session.getUser().get(5).contains("ROLE_GERENTEREGIONAL")){
            llSucursal.setVisibility(View.VISIBLE);
        }

        /**eventos de click*/
        tvFecha.setOnClickListener(tvFecha_onClick());
        tvRegion.setOnClickListener(tvRegion_onClick());
        tvSucursal.setOnClickListener(tvSucursal_onClick());
        tvAsesorGestor.setOnClickListener(tvAsesorGestor_onClick());
        btnBorrarFiltros.setOnClickListener(btnBorrarFiltros_onClick());
    }

    /**evento de click para seleccionar una fecha para busqueda*/
    private View.OnClickListener tvFecha_onClick () {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_DATE_GNRAL);
                Calendar cal = Calendar.getInstance();
                dialog_date_picker dialogDatePicker = new dialog_date_picker();
                Bundle b = new Bundle();

                b.putInt(Constants.YEAR_CURRENT, cal.get(Calendar.YEAR));
                b.putInt(Constants.MONTH_CURRENT, cal.get(Calendar.MONTH));
                b.putInt(Constants.DAY_CURRENT, cal.get(Calendar.DAY_OF_MONTH));
                b.putString(Constants.DATE_CURRENT, sdf.format(cal.getTime()));
                b.putInt(Constants.IDENTIFIER, 12);
                b.putBoolean(Constants.FECHAS_POST, false);
                dialogDatePicker.setArguments(b);
                dialogDatePicker.show(getSupportFragmentManager(), DIALOGDATEPICKER);
            }
        };
    }

    /**evento de click para seleccionar la region*/
    private View.OnClickListener tvRegion_onClick () {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
                builder.setTitle("Regiones")
                        .setMultiChoiceItems(regiones, checkRegiones,
                                new DialogInterface.OnMultiChoiceClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which,
                                                        boolean isChecked) {
                                        /**Evento cuando seleccionan un elemento de la lista
                                         * valida cuales elementos fueron seleccionados para crear un string con dichos elementos */
                                        if (isChecked) {
                                            if (which == 0){
                                                regionesChecked = new ArrayList<>();

                                                /**recorre el listado regiones para agregar cuales fueron seleccionados*/
                                                for(int i = 0; i < checkRegiones.length; i++){
                                                    checkRegiones[i] = true;
                                                    if (i > 0)
                                                        regionesChecked.add(regiones[i]);
                                                }
                                                nombres_regiones = "";
                                                String nombres_reg = "";
                                                /**recorre el listado de regiones seleccionados y crea un string con los ids de regiones separados por comas
                                                 * y otro string con los nombre de las regiones*/
                                                for (int i = 0; i < regionesChecked.size(); i++) {
                                                    if (i == 0) {
                                                        nombres_reg = regionesChecked.get(i);
                                                        nombres_regiones = "'"+regionesChecked.get(i)+"'";
                                                    }
                                                    else {
                                                        nombres_reg += ","+regionesChecked.get(i);
                                                        nombres_regiones += "," + "'" + regionesChecked.get(i) + "'";
                                                    }

                                                }
                                                /**coloca en el campo el nombre de las regiones que fueron seleccionados*/
                                                tvRegion.setText(nombres_reg);
                                                dialog.dismiss();
                                            }
                                            else {
                                                checkRegiones[which] = true;
                                                regionesChecked.add(regiones[which]);
                                            }
                                        }/**cuando fue desmarcado un elementos para ser removido del listado de seleccionados*/
                                        else {
                                            if (which == 0){
                                                nombres_regiones = "";
                                                for(int i = 0; i < checkRegiones.length; i++){
                                                    checkRegiones[i] = false;
                                                    regionesChecked.remove(regiones[i]);
                                                }
                                                tvRegion.setText("");
                                                dialog.dismiss();
                                            }
                                            else {
                                                checkRegiones[0] = false;
                                                if (regionesChecked.contains(regiones[0]))
                                                    regionesChecked.remove(regiones[0]);
                                                checkRegiones[which] = false;
                                                regionesChecked.remove(regiones[which]);
                                            }

                                        }

                                        if (regionesChecked.size() > 0) {
                                            String nombres_reg = "";
                                            for (int i = 0; i < regionesChecked.size(); i++) {
                                                if (i == 0) {
                                                    nombres_regiones = "'" + regionesChecked.get(i) + "'";
                                                    nombres_reg = regionesChecked.get(i);
                                                } else {
                                                    nombres_regiones += "," + "'" + regionesChecked.get(i) + "'";
                                                    nombres_reg += "," + regionesChecked.get(i);
                                                }
                                            }

                                            nombres_sucursales = "";
                                            nombres_asesores = "";
                                            tvRegion.setText(nombres_reg);
                                            tvSucursal.setText("");
                                            tvAsesorGestor.setText("");
                                        }/**para cuando no hay ningun elemento seleccionado se limpian los valores*/
                                        else{
                                            nombres_regiones = "";
                                            nombres_asesores = "";
                                            nombres_sucursales = "";
                                            tvRegion.setText("");
                                            tvSucursal.setText("");
                                            tvAsesorGestor.setText("");

                                        }
                                    }
                                })
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                /**Evento para cuando ya establecieron que elementos quieren de array y comenzar a obtener los registro con base a las regiones seleccionadas*/
                                Log.e("Regioneschecek", String.valueOf(regionesChecked.size()));
                                if (regionesChecked.size() > 0) {
                                    String nombres_reg = "";
                                    for (int i = 0; i < regionesChecked.size(); i++) {
                                        if (i == 0) {
                                            nombres_regiones = "'" + regionesChecked.get(i) + "'";
                                            nombres_reg = regionesChecked.get(i);
                                        }
                                        else {
                                            nombres_regiones += "," + "'" + regionesChecked.get(i) + "'";
                                            nombres_reg += "," + regionesChecked.get(i);
                                        }
                                    }

                                    String[] id_region = nombres_reg.replace(".",";").split(";");
                                    if (id_region.length > 0){
                                        String ids_region = "";
                                        for (int i = 0; i < id_region.length; i+=2){
                                            if (i == 0)
                                                ids_region = id_region[i];
                                            else
                                                ids_region += ","+id_region[i];
                                        }

                                        Cursor row;
                                        /**consulta para obtener las sucursales de las regiones seleccionadas*/
                                        String sql = "SELECT nombre FROM " + TBL_SUCURSALES + " WHERE region_id IN ("+ids_region+")";
                                        row = db.rawQuery(sql, null);
                                        if (row.getCount() > 0){
                                            row.moveToFirst();
                                            sucursales = new String[row.getCount() + 1];
                                            checkSucursales = new boolean[row.getCount() + 1];
                                            sucursales[0] = " TODOS";
                                            for(int i = 0; i < row.getCount(); i++){
                                                sucursales[i+1] = row.getString(0);
                                                checkSucursales[i+1] = false;
                                                row.moveToNext();
                                            }
                                        }
                                        row.close();

                                        /**conulta para obtener el log de sesiones de los asesores del resultado de las sucursales obtenidas*/
                                        sql = "SELECT l.* FROM " + TBL_REPORTE_SESIONES + " AS l INNER JOIN " + TBL_SUCURSALES + " AS s ON l.sucursalid = s.id WHERE s.region_id IN ("+ids_region+") ORDER BY l.sucursal, l.horariologin";
                                        row = db.rawQuery(sql, null);

                                        if (row.getCount() > 0){
                                            row.moveToFirst();
                                            data = new ArrayList<>();
                                            asesoresGestores = new String[row.getCount()+1];
                                            checkAsesores = new boolean[row.getCount()+1];
                                            asesoresGestores[0] = "TODOS";
                                            checkAsesores[0] = false;
                                            /**recorre el resultado de la consulta para agregarlo a un listado y pasarlo al adaptador*/
                                            for(int i = 0; i < row.getCount(); i++){
                                                asesoresGestores[i+1] = row.getString(3);
                                                checkAsesores[i+1] = false;

                                                MLogLogin item = new MLogLogin();
                                                item.setId(row.getInt(1));
                                                item.setSerieId(row.getString(2));
                                                item.setNombreAsesor(row.getString(3));
                                                item.setUsuario(row.getString(4));
                                                item.setSucursal(row.getString(5));
                                                item.setSucursalid(row.getString(6));
                                                item.setHorariologin(row.getString(7));
                                                item.setNivelbateria(row.getString(8));
                                                item.setVersionapp(row.getString(9));
                                                item.setPrimeragestion(row.getString(10));
                                                item.setUltimagestion(row.getString(11));
                                                item.setTotalgestiones(row.getString(12));
                                                data.add(item);
                                                row.moveToNext();
                                            }

                                            adatper.UpdateData(data);
                                            adatper.notifyDataSetChanged();
                                        }

                                        tvSucursal.setText("");
                                        tvAsesorGestor.setText("");
                                    }
                                }/**en caso de que no haya seleccionado ninguna region se mostraran todos los registros del log de sesiones sin filtros*/
                                else{
                                    nombres_regiones = "";
                                    nombres_sucursales = "";
                                    nombres_asesores = "";

                                    tvRegion.setText("");
                                    tvSucursal.setText("");
                                    tvAsesorGestor.setText("");
                                    Cursor row;
                                    String sql = "SELECT nombre FROM " + TBL_SUCURSALES ;
                                    row = db.rawQuery(sql, null);
                                    if (row.getCount() > 0){
                                        row.moveToFirst();
                                        sucursales = new String[row.getCount() + 1];
                                        checkSucursales = new boolean[row.getCount() + 1];
                                        sucursales[0] = " TODOS";
                                        for(int i = 0; i < row.getCount(); i++){
                                            sucursales[i+1] = row.getString(0);
                                            checkSucursales[i+1] = false;
                                            row.moveToNext();
                                        }
                                    }
                                    row.close();

                                    sql = "SELECT l.* FROM " + TBL_REPORTE_SESIONES + " AS l ORDER BY l.sucursal, l.horariologin";
                                    row = db.rawQuery(sql, null);

                                    if (row.getCount() > 0){
                                        row.moveToFirst();
                                        data = new ArrayList<>();
                                        asesoresGestores = new String[row.getCount()+1];
                                        checkAsesores = new boolean[row.getCount()+1];
                                        asesoresGestores[0] = "TODOS";
                                        checkAsesores[0] = false;
                                        for(int i = 0; i < row.getCount(); i++){
                                            asesoresGestores[i+1] = row.getString(3);
                                            checkAsesores[i+1] = false;

                                            MLogLogin item = new MLogLogin();
                                            item.setId(row.getInt(1));
                                            item.setSerieId(row.getString(2));
                                            item.setNombreAsesor(row.getString(3));
                                            item.setUsuario(row.getString(4));
                                            item.setSucursal(row.getString(5));
                                            item.setSucursalid(row.getString(6));
                                            item.setHorariologin(row.getString(7));
                                            item.setNivelbateria(row.getString(8));
                                            item.setVersionapp(row.getString(9));
                                            item.setPrimeragestion(row.getString(10));
                                            item.setUltimagestion(row.getString(11));
                                            item.setTotalgestiones(row.getString(12));
                                            data.add(item);
                                            row.moveToNext();
                                        }

                                        adatper.UpdateData(data);
                                        adatper.notifyDataSetChanged();
                                    }
                                }
                            }
                        });
                builder.setCancelable(false);
                builder.create().show();
            }
        };
    }

    /**Evento de click para seleccionar las sucursales a filtrar*/
    private View.OnClickListener tvSucursal_onClick () {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
                builder.setTitle("Sucursales")
                        .setMultiChoiceItems(sucursales, checkSucursales,
                                new DialogInterface.OnMultiChoiceClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which,
                                                        boolean isChecked) {
                                        if (isChecked) {
                                            if (which == 0){
                                                sucursalesChecked = new ArrayList<>();

                                                for(int i = 0; i < checkSucursales.length; i++){
                                                    checkSucursales[i] = true;
                                                    if (i > 0)
                                                        sucursalesChecked.add(sucursales[i]);
                                                }
                                                nombres_sucursales = "";
                                                String nombres_suc = "";
                                                for (int i = 0; i < sucursalesChecked.size(); i++) {
                                                    if (i == 0) {
                                                        nombres_suc = sucursalesChecked.get(i);
                                                        nombres_sucursales = "'"+sucursalesChecked.get(i)+"'";
                                                    }
                                                    else {
                                                        nombres_suc += ","+sucursalesChecked.get(i);
                                                        nombres_sucursales += "," + "'" + sucursalesChecked.get(i) + "'";
                                                    }

                                                }
                                                tvSucursal.setText(nombres_suc);
                                                dialog.dismiss();
                                            }
                                            else {
                                                checkSucursales[which] = true;
                                                sucursalesChecked.add(sucursales[which]);
                                            }
                                        }
                                        else {
                                            if (which == 0){
                                                nombres_sucursales = "";
                                                for(int i = 0; i < checkSucursales.length; i++){
                                                    checkSucursales[i] = false;
                                                    sucursalesChecked.remove(sucursales[i]);
                                                }
                                                tvSucursal.setText("");
                                                dialog.dismiss();
                                            }
                                            else {
                                                checkSucursales[0] = false;
                                                if (sucursalesChecked.contains(sucursales[0]))
                                                    sucursalesChecked.remove(sucursales[0]);
                                                checkSucursales[which] = false;
                                                sucursalesChecked.remove(sucursales[which]);
                                            }

                                        }

                                        if (sucursalesChecked.size() > 0) {
                                            String nombres_suc = "";
                                            for (int i = 0; i < sucursalesChecked.size(); i++) {
                                                if (i == 0) {
                                                    nombres_sucursales = "'" + sucursalesChecked.get(i) + "'";
                                                    nombres_suc = sucursalesChecked.get(i);
                                                } else {
                                                    nombres_sucursales += "," + "'" + sucursalesChecked.get(i) + "'";
                                                    nombres_suc += "," + sucursalesChecked.get(i);
                                                }
                                            }

                                            nombres_asesores = "";
                                            tvSucursal.setText(nombres_suc);
                                            tvAsesorGestor.setText("");
                                        }
                                        else{
                                            nombres_asesores = "";
                                            nombres_sucursales = "";
                                            tvSucursal.setText("");
                                            tvAsesorGestor.setText("");

                                        }
                                    }
                                })
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                Log.e("Sucursaleschecek", String.valueOf(sucursalesChecked.size()));
                                if (sucursalesChecked.size() > 0) {
                                    String nombres_suc = "";
                                    for (int i = 0; i < sucursalesChecked.size(); i++) {
                                        if (i == 0) {
                                            nombres_sucursales = "'" + sucursalesChecked.get(i) + "'";
                                            nombres_suc = sucursalesChecked.get(i);
                                        }
                                        else {
                                            nombres_sucursales += "," + "'" + sucursalesChecked.get(i) + "'";
                                            nombres_suc += "," + sucursalesChecked.get(i);
                                        }
                                    }

                                    String sql = "SELECT l.* FROM " + TBL_REPORTE_SESIONES + " AS l INNER JOIN " + TBL_SUCURSALES + " AS s ON l.sucursalid = s.id WHERE s.nombre IN ("+nombres_sucursales+") ORDER BY l.sucursal, l.horariologin";
                                    Cursor row = db.rawQuery(sql, null);

                                    if (row.getCount() > 0){
                                        row.moveToFirst();
                                        data = new ArrayList<>();
                                        asesoresGestores = new String[row.getCount()+1];
                                        checkAsesores = new boolean[row.getCount()+1];
                                        asesoresGestores[0] = "TODOS";
                                        checkAsesores[0] = false;
                                        for(int i = 0; i < row.getCount(); i++){
                                            asesoresGestores[i+1] = row.getString(3);
                                            checkAsesores[i+1] = false;

                                            MLogLogin item = new MLogLogin();
                                            item.setId(row.getInt(1));
                                            item.setSerieId(row.getString(2));
                                            item.setNombreAsesor(row.getString(3));
                                            item.setUsuario(row.getString(4));
                                            item.setSucursal(row.getString(5));
                                            item.setSucursalid(row.getString(6));
                                            item.setHorariologin(row.getString(7));
                                            item.setNivelbateria(row.getString(8));
                                            item.setVersionapp(row.getString(9));
                                            item.setPrimeragestion(row.getString(10));
                                            item.setUltimagestion(row.getString(11));
                                            item.setTotalgestiones(row.getString(12));
                                            data.add(item);
                                            row.moveToNext();
                                        }

                                        adatper.UpdateData(data);
                                        adatper.notifyDataSetChanged();
                                    }

                                    tvSucursal.setText(nombres_suc);
                                    tvAsesorGestor.setText("");
                                }
                            }
                        });
                builder.setCancelable(false);
                builder.create().show();
            }
        };
    }

    /**evento click para seleccionar que asesores quiere mostrar en la lista*/
    private View.OnClickListener tvAsesorGestor_onClick () {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
                builder.setTitle("Asesores/Gestores")
                        .setMultiChoiceItems(asesoresGestores, checkAsesores,
                                new DialogInterface.OnMultiChoiceClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                        if (isChecked) {
                                            if (which == 0){
                                                asesoresChecked = new ArrayList<>();

                                                for(int i = 0; i < checkAsesores.length; i++){
                                                    checkAsesores[i] = true;
                                                    if (i > 0)
                                                        asesoresChecked.add(asesoresGestores[i]);
                                                }
                                                nombres_asesores = "";
                                                String nombres = "";
                                                for (int i = 0; i < asesoresChecked.size(); i++) {
                                                    if (i == 0) {
                                                        nombres = asesoresChecked.get(i);
                                                        nombres_asesores = "'"+asesoresChecked.get(i)+"'";
                                                    }
                                                    else {
                                                        nombres += ","+asesoresChecked.get(i);
                                                        nombres_asesores += "," + "'" + asesoresChecked.get(i) + "'";
                                                    }

                                                }
                                                tvAsesorGestor.setText(nombres);
                                                dialog.dismiss();
                                            }
                                            else {
                                                checkAsesores[which] = true;
                                                asesoresChecked.add(asesoresGestores[which]);
                                            }
                                        }
                                        else {
                                            if (which == 0){
                                                nombres_asesores = "";
                                                for(int i = 0; i < checkAsesores.length; i++){
                                                    checkAsesores[i] = false;
                                                    asesoresChecked.remove(asesoresGestores[i]);
                                                }
                                                tvAsesorGestor.setText("");
                                                dialog.dismiss();
                                            }
                                            else {
                                                checkAsesores[0] = false;
                                                if (asesoresChecked.contains(asesoresGestores[0]))
                                                    asesoresChecked.remove(asesoresGestores[0]);
                                                checkAsesores[which] = false;
                                                asesoresChecked.remove(asesoresGestores[which]);
                                            }

                                        }
                                    }
                                })
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                Log.e("ASesoreschecek", String.valueOf(asesoresChecked.size()));
                                if (asesoresChecked.size() > 0) {
                                    String nombres = "";
                                    for (int i = 0; i < asesoresChecked.size(); i++) {
                                        if (i == 0) {
                                            nombres_asesores = "'" + asesoresChecked.get(i) + "'";
                                            nombres = asesoresChecked.get(i);
                                        }
                                        else {
                                            nombres_asesores += "," + "'" + asesoresChecked.get(i) + "'";
                                            nombres += "," + asesoresChecked.get(i);
                                        }
                                    }
                                    tvAsesorGestor.setText(nombres);

                                    String sql = "SELECT * FROM " + TBL_REPORTE_SESIONES + " WHERE nombre_asesor IN (" + nombres_asesores + ") ORDER BY sucursal, horariologin";
                                    Cursor row = db.rawQuery(sql, null);

                                    if (row.getCount() > 0) {
                                        row.moveToFirst();
                                        data = new ArrayList<>();
                                        for (int i = 0; i < row.getCount(); i++) {
                                            MLogLogin item = new MLogLogin();
                                            item.setId(row.getInt(1));
                                            item.setSerieId(row.getString(2));
                                            item.setNombreAsesor(row.getString(3));
                                            item.setUsuario(row.getString(4));
                                            item.setSucursal(row.getString(5));
                                            item.setSucursalid(row.getString(6));
                                            item.setHorariologin(row.getString(7));
                                            item.setNivelbateria(row.getString(8));
                                            item.setVersionapp(row.getString(9));
                                            item.setPrimeragestion(row.getString(10));
                                            item.setUltimagestion(row.getString(11));
                                            item.setTotalgestiones(row.getString(12));
                                            data.add(item);
                                            row.moveToNext();
                                        }
                                        adatper.UpdateData(data);
                                        adatper.notifyDataSetChanged();
                                        dialog.dismiss();
                                    }
                                }
                            }
                        });
                builder.setCancelable(false);
                builder.create().show();
            }
        };
    }

    /**evento de click para borrar los filtros aplicados*/
    private View.OnClickListener btnBorrarFiltros_onClick () {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fechaSelect = Miscellaneous.ObtenerFecha(FECHA.toLowerCase());
                nombres_regiones = "";
                nombres_sucursales = "";
                nombres_asesores = "";

                tvRegion.setText("");
                tvSucursal.setText("");
                tvAsesorGestor.setText("");
                regionesChecked = new ArrayList<>();
                sucursalesChecked = new ArrayList<>();
                asesoresChecked = new ArrayList<>();
                for (int i = 0; i < checkRegiones.length; i++){
                    checkRegiones[i] = false;
                }

                Cursor row;
                String sql = "SELECT nombre FROM " + TBL_SUCURSALES ;
                row = db.rawQuery(sql, null);
                if (row.getCount() > 0){
                    row.moveToFirst();
                    sucursales = new String[row.getCount() + 1];
                    checkSucursales = new boolean[row.getCount() + 1];
                    sucursales[0] = " TODOS";
                    for(int i = 0; i < row.getCount(); i++){
                        sucursales[i+1] = row.getString(0);
                        checkSucursales[i+1] = false;
                        row.moveToNext();
                    }
                }
                row.close();


                HashMap<String, String> params = new HashMap<>();
                params.put("fecha_inicio", fechaSelect);
                params.put("fecha_fin", fechaSelect);
                params.put("region", "0");
                params.put("asesor_gestor", "0");
                String ids = "";

                sql = "SELECT DISTINCT(sucursalid) FROM " + TBL_REPORTE_SESIONES;
                row = db.rawQuery(sql, null);
                if (row.getCount() > 0) {
                    row.moveToFirst();
                    for (int i = 0; i < row.getCount(); i++) {
                        if (i == 0)
                            ids = row.getString(0);
                        else
                            ids += "," + row.getString(0);

                        row.moveToNext();
                    }
                    params.put("sucursal", ids);
                    Log.e("Paramas", params.toString());
                    db.delete(TBL_REPORTE_SESIONES, "", null);
                    GetReporteLogin(params);
                }
            }
        };
    }

    /**funcion para recibir la respuesta(fecha) a la peticion de fecha*/
    public void setDate(String fecha){
        tvFecha.setText(fecha);
        if (!fecha.equals(fechaSelect)) {
            fechaSelect = tvFecha.getText().toString().trim();
            nombres_regiones = "";
            nombres_sucursales = "";
            nombres_asesores = "";
            tvRegion.setText("");
            tvSucursal.setText("");
            tvAsesorGestor.setText("");
            /**se crea un map con los parametros para realizar la peticion de obtencion de log de sesiones*/
            HashMap<String, String> params = new HashMap<>();
            params.put("fecha_inicio", fechaSelect);
            params.put("fecha_fin", fechaSelect);
            params.put("region", "0");
            params.put("asesor_gestor", "0");
            String ids = "";

            String sql = "SELECT DISTINCT(sucursalid) FROM " + TBL_REPORTE_SESIONES;
            Cursor row = db.rawQuery(sql, null);
            if (row.getCount() > 0) {
                row.moveToFirst();
                for (int i = 0; i < row.getCount(); i++) {
                    if (i == 0)
                        ids = row.getString(0);
                    else
                        ids += "," + row.getString(0);

                    row.moveToNext();
                }
                params.put("sucursal", ids);
                Log.e("Paramas", params.toString());
                /**borra el contenido de la tabla de reporte de sesiones para la nueva peticion*/
                db.delete(TBL_REPORTE_SESIONES, "", null);
                /**Funcion para realizar la peticion*/
                GetReporteLogin(params);
            }
        }

    }

    /**funcion para mostrar el menu*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_filtro, menu);
        menu.getItem(1).setVisible(false);

        final MenuItem menuItem = menu.findItem(R.id.nvFiltro);
        View actionView = MenuItemCompat.getActionView(menuItem);
        TextView tvContFiltros = actionView.findViewById(R.id.filtro_bagde);
        tvContFiltros.setVisibility(View.GONE);
        actionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOptionsItemSelected(menuItem);
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:/**menu de retroceso toolbar <-*/
                /**borra el contenido de las tablas sucursales y reporte de sesiones para no guardar esa informacion*/
                db.delete(TBL_SUCURSALES, "", null);
                db.delete(TBL_REPORTE_SESIONES,"", null);
                finish();
                break;
            case R.id.nvFiltro:/**menu para mostrar filtros */
                ShowFiltros();
                //Toast.makeText(ctx, "Filtro", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**funcion de retroceso del bton del dispositivo*/
    @Override
    public void onBackPressed() {
        /**borra el contenido de las tablas sucursales y reporte de sesiones para no guardar esa informacion*/
        db.delete(TBL_SUCURSALES, "", null);
        db.delete(TBL_REPORTE_SESIONES,"", null);
        super.onBackPressed();
    }
}
