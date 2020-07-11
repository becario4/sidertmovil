package com.sidert.sidertmovil.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
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

import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.adapters.adapter_inicio_sesion;
import com.sidert.sidertmovil.database.DBhelper;
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

        if (NetworkStatus.haveNetworkConnection(ctx)){
            GetMisSucursales();
        }
        else{
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

    private void GetMisSucursales(){
        final AlertDialog loading = Popups.showLoadingDialog(ctx, R.string.please_wait, R.string.loading_info);
        loading.show();

        ManagerInterface api = new RetrofitClient().generalRF(CONTROLLER_API, ctx).create(ManagerInterface.class);

        Call<List<MSucursal>> call = api.getMisSucursales(Integer.parseInt(session.getUser().get(9)));

        call.enqueue(new Callback<List<MSucursal>>() {
            @Override
            public void onResponse(Call<List<MSucursal>> call, Response<List<MSucursal>> response) {
                switch (response.code()){
                    case 200:
                        List<MSucursal> data = response.body();
                        sucursales = new String[data.size() + 1];
                        checkSucursales = new boolean[data.size()+1];
                        sucursales[0] = " TODOS";
                        checkSucursales[0] = false;
                        fechaSelect = Miscellaneous.ObtenerFecha(FECHA.toLowerCase());
                        HashMap<String, String> params = new HashMap<>();
                        params.put("fecha_inicio", fechaSelect);
                        params.put("fecha_fin", fechaSelect);
                        params.put("region", "0");
                        params.put("asesor_gestor", "0");
                        String ids = "";
                        for (int i = 0; i< data.size(); i++){
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

    private void GetReporteLogin(final HashMap<String, String> params){
        if (NetworkStatus.haveNetworkConnection(ctx)){
            final AlertDialog loading = Popups.showLoadingDialog(ctx, R.string.please_wait, R.string.loading_info);
            loading.show();

            ManagerInterface api = new RetrofitClient().generalRF(CONTROLLER_API, ctx).create(ManagerInterface.class);

            Call<List<MLogLogin>> call = api.getLogAsesores(params.get("fecha_inicio"),
                                                            params.get("fecha_fin"),
                                                            params.get("region"),
                                                            params.get("sucursal"),
                                                            params.get("asesor_gestor"));

            call.enqueue(new Callback<List<MLogLogin>>() {
                @Override
                public void onResponse(Call<List<MLogLogin>> call, Response<List<MLogLogin>> response) {
                    switch (response.code()){
                        case 200:
                            List<MLogLogin> logLogin = response.body();
                            data = new ArrayList<>();
                            if (logLogin.size() > 0){
                                for (MLogLogin item : logLogin){
                                    if (item.getUsuario().contains("ASESOR") || item.getUsuario().contains("GESTOR")) {
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
                                }

                                String sql = "SELECT * FROM " + TBL_REPORTE_SESIONES + " ORDER BY sucursal, horariologin";

                                Cursor row = db.rawQuery(sql, null);
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

        if (session.getUser().get(5).contains("ROLE_SUPER") ||
                session.getUser().get(5).contains("ROLE_ANALISTA") ||
                session.getUser().get(5).contains("DIRECCION")){
            llRegion.setVisibility(View.VISIBLE);
        }

        if (session.getUser().get(5).contains("ROLE_SUPER") ||
                session.getUser().get(5).contains("ROLE_ANALISTA") ||
                session.getUser().get(5).contains("ROLE_DIRECCION") ||
                session.getUser().get(5).contains("ROLE_GERENTEREGIONAL")){
            llSucursal.setVisibility(View.VISIBLE);
        }

        tvFecha.setOnClickListener(tvFecha_onClick());
        tvRegion.setOnClickListener(tvRegion_onClick());
        tvSucursal.setOnClickListener(tvSucursal_onClick());
        tvAsesorGestor.setOnClickListener(tvAsesorGestor_onClick());
        btnBorrarFiltros.setOnClickListener(btnBorrarFiltros_onClick());
    }

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
                                        if (isChecked) {
                                            if (which == 0){
                                                regionesChecked = new ArrayList<>();

                                                for(int i = 0; i < checkRegiones.length; i++){
                                                    checkRegiones[i] = true;
                                                    if (i > 0)
                                                        regionesChecked.add(regiones[i]);
                                                }
                                                nombres_regiones = "";
                                                String nombres_reg = "";
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
                                                tvRegion.setText(nombres_reg);
                                                dialog.dismiss();
                                            }
                                            else {
                                                checkRegiones[which] = true;
                                                regionesChecked.add(regiones[which]);
                                            }
                                        }
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
                                        }
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



                                        sql = "SELECT l.* FROM " + TBL_REPORTE_SESIONES + " AS l INNER JOIN " + TBL_SUCURSALES + " AS s ON l.sucursalid = s.id WHERE s.region_id IN ("+ids_region+") ORDER BY l.sucursal, l.horariologin";
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

                                        tvSucursal.setText("");
                                        tvAsesorGestor.setText("");
                                    }
                                }
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
                db.delete(TBL_REPORTE_SESIONES, "", null);
                GetReporteLogin(params);
            }
        }

    }

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
            case android.R.id.home:
                db.delete(TBL_SUCURSALES, "", null);
                db.delete(TBL_REPORTE_SESIONES,"", null);
                finish();
                break;
            case R.id.nvFiltro:
                ShowFiltros();
                //Toast.makeText(ctx, "Filtro", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        db.delete(TBL_SUCURSALES, "", null);
        db.delete(TBL_REPORTE_SESIONES,"", null);
        super.onBackPressed();
    }
}
