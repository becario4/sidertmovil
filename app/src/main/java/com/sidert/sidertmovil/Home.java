package com.sidert.sidertmovil;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import io.fabric.sdk.android.Fabric;
import com.crashlytics.android.Crashlytics;
import com.sidert.sidertmovil.activities.CirculoCredito;
import com.sidert.sidertmovil.activities.Configuracion;
import com.sidert.sidertmovil.activities.Perfil;
import com.sidert.sidertmovil.activities.RenovacionCredito;
import com.sidert.sidertmovil.activities.ReporteInicioSesion;
import com.sidert.sidertmovil.activities.SolicitudCredito;
import com.sidert.sidertmovil.activities.TrackerAsesor;
import com.sidert.sidertmovil.database.DBhelper;
import com.sidert.sidertmovil.fragments.dialogs.dialog_logout;
import com.sidert.sidertmovil.fragments.geolocalizacion_fragment;
import com.sidert.sidertmovil.fragments.impression_history_fragment;
import com.sidert.sidertmovil.fragments.mesa_ayuda_fragment;
import com.sidert.sidertmovil.fragments.orders_fragment;
import com.sidert.sidertmovil.utils.Constants;
import com.sidert.sidertmovil.utils.CustomDrawerLayout;
import com.sidert.sidertmovil.utils.CustomRelativeLayout;
import com.sidert.sidertmovil.utils.NameFragments;
import com.sidert.sidertmovil.utils.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.sidert.sidertmovil.utils.Constants.SINCRONIZADO;
import static com.sidert.sidertmovil.utils.Constants.SINCRONIZADO_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_TRACKER_ASESOR;
import static com.sidert.sidertmovil.utils.Constants.TBL_TRACKER_ASESOR_T;
import static com.sidert.sidertmovil.utils.NameFragments.GEOLOCALIZACION;
import static com.sidert.sidertmovil.utils.NameFragments.MESA_AYUDA;
import static com.sidert.sidertmovil.utils.NameFragments.ORDERS;


public class Home extends AppCompatActivity{

    private Context ctx;
    private ActionBarDrawerToggle mToggled;
    private Toolbar TBmain;
    private CustomDrawerLayout mDrawerLayout;
    private NavigationView NVmenu;
    private TabLayout mTabLayout;
    private CoordinatorLayout CLcontainer;
    private TextView tvUltimaSincro;
    private TextView tvNameUser;
    private LinearLayout llProfile;
    private ImageView ivLogout;
    private boolean canExitApp = false;
    private SessionManager session;
    private Menu menuGeneral;

    public interface Sidert {
        void initTabLayout(TabLayout Tabs);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ctx             = getApplicationContext();
        session         = new SessionManager(ctx);

        Fabric.with(this, new Crashlytics());
        logUserFabric();

        TBmain          = findViewById(R.id.TBmain);
        mDrawerLayout   = findViewById(R.id.mDrawerLayout);
        NVmenu          = findViewById(R.id.NVmenu);
        mTabLayout      = findViewById(R.id.mTabLayout);
        CLcontainer     = findViewById(R.id.CLcontainer);
        View view       = NVmenu.getHeaderView(0);
        tvNameUser      = view.findViewById(R.id.tvName);
        tvUltimaSincro  = view.findViewById(R.id.tvultimaSincro);
        llProfile       = view.findViewById(R.id.llProfile);
        ivLogout        = view.findViewById(R.id.ivLogout);

        //menuGeneral = NVmenu.getMenu();
        final Bundle data = getIntent().getExtras();
        if (true){
            initNavigationDrawer();
            setSupportActionBar(TBmain);
            final DrawerLayout.LayoutParams CLparams = (DrawerLayout.LayoutParams) CLcontainer.getLayoutParams();
            if(CLparams.getMarginStart() == (int)getResources().getDimension(R.dimen.drawermenu)) {
                mDrawerLayout.setLocked(true);
                mDrawerLayout.setDrawerShadow(ContextCompat.getDrawable(getApplicationContext(), R.drawable.shadow), GravityCompat.START);
                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_OPEN, NVmenu);
                mDrawerLayout.setScrimColor(Color.TRANSPARENT);
            }
            if(!mDrawerLayout.isLocked()) {
                mToggled = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close) {
                    @Override
                    public void onDrawerOpened(View drawerView) {
                        super.onDrawerOpened(drawerView);
                        DBhelper dBhelper = new DBhelper(ctx);
                        SQLiteDatabase db = dBhelper.getWritableDatabase();

                        Cursor row = dBhelper.getRecords(SINCRONIZADO_T, ""," ORDER BY _id DESC", null);

                        if (row.getCount() > 0){
                            row.moveToFirst();
                            tvUltimaSincro.setText("Última Sincronización: " + row.getString(2));
                            for (int i = 0; i < row.getCount(); i++){
                                Log.e("Sincronizado"+i, row.getString(2));
                                row.moveToNext();
                            }

                        }

                        row = dBhelper.getRecords(TBL_TRACKER_ASESOR_T, ""," ORDER BY _id DESC", null);

                        if (row.getCount() > 0){
                            row.moveToFirst();

                            for (int i = 0; i < row.getCount(); i++){
                                Log.e("Bateria"+i, row.getString(6));
                                Log.e("Timestamp"+i, row.getString(7));
                                Log.e("Estatus"+i, row.getString(9));
                                Log.e("-","---------------------------------------");
                                row.moveToNext();
                            }

                        }

                    }

                    @Override
                    public void onDrawerClosed(View drawerView) {
                        super.onDrawerClosed(drawerView);
                    }
                };
                mDrawerLayout.addDrawerListener(mToggled);
                getSupportActionBar().setHomeButtonEnabled(true);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                mDrawerLayout.post(new Runnable() {
                    @Override
                    public void run() {
                        mToggled.syncState();
                    }
                });
            }

            menuGeneral = NVmenu.getMenu();
            ShowMenuItems();
            tvNameUser.setText(session.getUser().get(1)+" "+session.getUser().get(2)+" "+session.getUser().get(3));

            setFragment(ORDERS, null);

            NVmenu.setNavigationItemSelectedListener(NVmenu_onClick);
            llProfile.setOnClickListener(LLprofile_OnClick);
            ivLogout.setOnClickListener(ivLogout_OnClick);
        }
        else{
            mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            TBmain.setVisibility(View.GONE);
            mTabLayout.setVisibility(View.GONE);
            setFragment(NameFragments.COMPLAINT_TEMP, null);
        }

    }

    private NavigationView.OnNavigationItemSelectedListener NVmenu_onClick = new NavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.nvCartera:
                    setFragment(ORDERS, null);
                    break;
                case R.id.nvOriginacion:
                    Intent i_solicitud = new Intent(getApplicationContext(), SolicitudCredito.class);
                    startActivity(i_solicitud);
                    break;
                case R.id.nvRenovacion:
                    Intent i_renovacion = new Intent(getApplicationContext(), RenovacionCredito.class);
                    startActivity(i_renovacion);
                    break;
                case R.id.nvConfiguraciones:
                    Intent i_config = new Intent(getApplicationContext(), Configuracion.class);
                    startActivity(i_config);
                    break;
                case R.id.nvImpresiones:
                    setFragment(NameFragments.IMPRESSION_HISTORY, null);
                    break;
                case R.id.nvCC:
                    Intent i_cc = new Intent(getApplicationContext(), CirculoCredito.class);
                    startActivity(i_cc);
                    break;
                case R.id.nvRuta:
                    Intent i_ruta = new Intent(getApplicationContext(), TrackerAsesor.class);
                    startActivity(i_ruta);
                    break;
                case R.id.nvGeolocalizar:
                    setFragment(GEOLOCALIZACION, null);
                    break;
                case R.id.nvLogin:
                    Intent i_log_login = new Intent(getApplicationContext(), ReporteInicioSesion.class);
                    startActivity(i_log_login);
                    break;
                case R.id.nvMesaAyuda:
                    setFragment(MESA_AYUDA, null);
                    break;
                default:
                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                    finish();
                    break;
            }
            if(!mDrawerLayout.isLocked()) mDrawerLayout.closeDrawer(NVmenu);
            return true;
        }
    };

    public void setFragment(String fragment, Bundle extras) {
        mTabLayout.setVisibility(View.GONE);
        Fragment current = getSupportFragmentManager().findFragmentById(R.id.FLmain);
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        String tokenFragment = "";
        switch (fragment) {
            case ORDERS:
                mTabLayout.setVisibility(View.VISIBLE);
                if (!(current instanceof orders_fragment)){
                    orders_fragment myAppointments = new orders_fragment();
                    myAppointments.setArguments(extras);
                    transaction.replace(R.id.FLmain, myAppointments, ORDERS);
                    tokenFragment = ORDERS;
                } else
                    return;
                break;
            case NameFragments.IMPRESSION_HISTORY:
                if (!(current instanceof impression_history_fragment)){
                    impression_history_fragment impression_history = new impression_history_fragment();
                    impression_history.setArguments(extras);
                    transaction.replace(R.id.FLmain, impression_history, NameFragments.IMPRESSION_HISTORY);
                    tokenFragment = NameFragments.IMPRESSION_HISTORY;
                } else
                    return;
                break;
            case MESA_AYUDA:
                if (!(current instanceof mesa_ayuda_fragment)){
                    mesa_ayuda_fragment mesa_ayuda = new mesa_ayuda_fragment();
                    mesa_ayuda.setArguments(extras);
                    transaction.replace(R.id.FLmain, mesa_ayuda, MESA_AYUDA);
                    tokenFragment = MESA_AYUDA;
                } else
                    return;
                break;
            case GEOLOCALIZACION:
                mTabLayout.setVisibility(View.VISIBLE);
                if (!(current instanceof geolocalizacion_fragment)){
                    geolocalizacion_fragment geolocalizacion = new geolocalizacion_fragment();
                    geolocalizacion.setArguments(extras);
                    transaction.replace(R.id.FLmain, geolocalizacion, GEOLOCALIZACION);
                    tokenFragment = GEOLOCALIZACION;
                } else
                    return;
                break;
            default:
                if (!(current instanceof orders_fragment)){
                    transaction.replace(R.id.FLmain, new orders_fragment(), ORDERS);
                    tokenFragment = ORDERS;
                } else
                    return;
                break;
        }

        //if(!tokenFragment.equals(NameFragments.COMPLAINT_TEMP) && !tokenFragment.equals(NameFragments.IMPRESSION_HISTORY) && !tokenFragment.equals(NameFragments.ORDERS) && !tokenFragment.equals(NameFragments.GEOLOCALIZACION)) {
        if(!tokenFragment.equals(ORDERS)) {
            int count = manager.getBackStackEntryCount();
            if(count > 0) {
                int index = count - 1;
                String tag = manager.getBackStackEntryAt(index).getName();
                if(!tag.equals(tokenFragment)) {
                    transaction.addToBackStack(tokenFragment);
                }
            } else {
                transaction.addToBackStack(tokenFragment);
            }
        } else {
            cleanFragments();
        }
        transaction.commit();

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if(!mDrawerLayout.isLocked()) mToggled.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(!mDrawerLayout.isLocked()) {
            if (mToggled.onOptionsItemSelected(item)) {
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    public void cleanFragments() {
        FragmentManager fm = getSupportFragmentManager();
        fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    public void hasTabLayout(Sidert callback) {
        callback.initTabLayout(mTabLayout);
    }

    public void setActionBar(Toolbar TBmain, HashMap<String, String> options) {
        setSupportActionBar(TBmain);
        if(options != null) {
            for (Map.Entry<String, String> entry : options.entrySet()) {
                switch (entry.getKey()) {
                    case Constants.TBtitle:
                        setTitle(options.get(Constants.TBtitle));
                        break;
                    case Constants.TBhasBack:
                        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                        break;
                }
            }
        }
    }

    private void initNavigationDrawer() {
        View view                      = NVmenu.getHeaderView(0);
        final CustomRelativeLayout HV  = view.findViewById(R.id.HV);
        tvNameUser                     = view.findViewById(R.id.tvName);
        final String pic = "";
        /*NVmenu.post(new Runnable() {
            @Override
            public void run() {
                if(pic != null && !pic.isEmpty()) {
                    Glide.with(ctx).load(pic).into(civAvatar);
                } else {
                    Glide.with(getApplicationContext()).load(R.drawable.ic_default_user).into(civAvatar);
                }
                //TVnameprofile.setText(session.profile().get(6)+" "+session.profile().get(1).toString());
            }
        });*/
    }

    private View.OnClickListener LLprofile_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i = new Intent(ctx, Perfil.class);
            //startActivity(i);
        }
    };

    private View.OnClickListener ivLogout_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            dialog_logout mess_confirm = new dialog_logout();
            Bundle b = new Bundle();
            b.putString(Constants.MESSAGE, getApplicationContext().getString(R.string.mess_logout));
            mess_confirm.setArguments(b);
            mess_confirm.show(getSupportFragmentManager(), NameFragments.DIALOGLOGOUT);
        }
    };

    private void logUserFabric() {
        Crashlytics.setUserIdentifier(session.getUser().get(0));
        Crashlytics.setUserName(session.getUser().get(1)+" "+session.getUser().get(2)+" "+session.getUser().get(3));
    }

    @Override
    public void onBackPressed() {
        if(mDrawerLayout.isDrawerOpen(Gravity.LEFT)){
            mDrawerLayout.closeDrawer(Gravity.LEFT);
        }else{
            super.onBackPressed();
        }
    }

    private void ShowMenuItems (){
        try {
            if (session.getUser().get(8) != null) {
                JSONArray jsonModulos = new JSONArray(session.getUser().get(8));
                for (int i = 0; i < jsonModulos.length(); i++) {
                    JSONObject item = jsonModulos.getJSONObject(i);
                    switch (item.getString("nombre").toLowerCase()) {
                        case "cartera":
                            menuGeneral.getItem(0).setVisible(true);
                            break;
                        case "geolocalizar":
                            menuGeneral.getItem(1).setVisible(true);
                            break;
                        case "impresion":
                            menuGeneral.getItem(2).setVisible(true);
                            break;
                        case "ruta":
                            menuGeneral.getItem(3).setVisible(true);
                            break;
                        case "circulocredito":
                            menuGeneral.getItem(4).setVisible(true);
                            break;
                        case "agf":
                            menuGeneral.getItem(5).setVisible(true);
                            break;
                        case "originacion":
                            menuGeneral.getItem(6).setVisible(true);
                            break;
                        case "sesiones":
                            menuGeneral.getItem(7).setVisible(true);
                            break;
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
