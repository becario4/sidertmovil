package com.sidert.sidertmovil;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
/*import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;*/
import androidx.appcompat.app.AppCompatActivity;
/*import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;*/
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

/*import io.fabric.sdk.android.Fabric;
import com.crashlytics.android.Crashlytics;*/
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.FirebaseApp;
import com.sidert.sidertmovil.activities.ConsultarCC;
import com.sidert.sidertmovil.views.apoyogastosfunerarios.ApoyoGastosFunerariosActivity;
import com.sidert.sidertmovil.activities.CobrosCC;
import com.sidert.sidertmovil.activities.Configuracion;
import com.sidert.sidertmovil.activities.Perfil;
import com.sidert.sidertmovil.activities.RenovacionCredito;
import com.sidert.sidertmovil.activities.ReporteInicioSesion;
import com.sidert.sidertmovil.activities.SolicitudCredito;
import com.sidert.sidertmovil.activities.Solicitudes;
import com.sidert.sidertmovil.activities.TrackerAsesor;
import com.sidert.sidertmovil.database.DBhelper;
import com.sidert.sidertmovil.fragments.autorizaciones_cc_fragment;
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
import com.sidert.sidertmovil.views.pdfreader.PdfReaderActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.sidert.sidertmovil.utils.Constants.SINCRONIZADO_T;
import static com.sidert.sidertmovil.utils.NameFragments.GEOLOCALIZACION;
import static com.sidert.sidertmovil.utils.NameFragments.MESA_AYUDA;
import static com.sidert.sidertmovil.utils.NameFragments.ORDERS;


public class Home extends AppCompatActivity {

    private Context ctx;
    private ActionBarDrawerToggle mToggled;
    private Toolbar TBmain;
    private CustomDrawerLayout mDrawerLayout;
    private NavigationView NVmenu;
    private TextView tvVersionAppAmbiente;
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

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ctx             = getApplicationContext();
        session         = new SessionManager(ctx);
        FirebaseApp.initializeApp(this);

        //Fabric.with(this, new Crashlytics());
        //logUserFabric();

        TBmain          = findViewById(R.id.TBmain);
        mDrawerLayout   = findViewById(R.id.mDrawerLayout);
        NVmenu          = findViewById(R.id.NVmenu);
        tvVersionAppAmbiente = findViewById(R.id.tvVersionAppAmbiente);
        mTabLayout      = findViewById(R.id.mTabLayout);
        CLcontainer     = findViewById(R.id.CLcontainer);
        View view       = NVmenu.getHeaderView(0);
        tvNameUser      = view.findViewById(R.id.tvName);
        tvUltimaSincro  = view.findViewById(R.id.tvultimaSincro);
        llProfile       = view.findViewById(R.id.llProfile);
        ivLogout        = view.findViewById(R.id.ivLogout);

        Log.e("MAC", session.getMacAddress());

        /**Se valida si el dominio y el puerto guardado es diferente al de produccion
         * por lo tanto esta apuntando a un ambiente de pruebas y muestra la leyenda de pruebas*/
        if (!session.getDominio().get(0).contains("sidert.ddns.net") || !session.getDominio().get(1).equals("83")) {
            tvVersionAppAmbiente.setText(getResources().getString(R.string.app_version) + " PRUEBAS");
            tvVersionAppAmbiente.setTextColor(ContextCompat.getColor(ctx, R.color.red));
        }

        final Bundle data = getIntent().getExtras();
        if (true){/** Siempre entra a esta condición , es el menu general de sidert movil */
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

                        /**Se obtiene el log de sincronnizaciones para colocar la fecha y hora de la ultima sincronizacion*/
                        Cursor row = dBhelper.getRecords(SINCRONIZADO_T, ""," ORDER BY _id DESC", null);

                        if (row.getCount() > 0){
                            row.moveToFirst();
                            /**Coloca la fecha y hora de la ultima sincronizacion*/
                            tvUltimaSincro.setText("Última Sincronización: " + row.getString(2));
                            for (int i = 0; i < row.getCount(); i++){
                                Log.e("Sincronizado"+i, row.getString(2));
                                row.moveToNext();
                            }

                        }

                        /*row = dBhelper.getRecords(TBL_TRACKER_ASESOR_T, ""," ORDER BY _id DESC", null);

                        if (row.getCount() > 0){
                            row.moveToFirst();

                            for (int i = 0; i < row.getCount(); i++){
                                Log.e("Bateria"+i, row.getString(6));
                                Log.e("Timestamp"+i, row.getString(7));
                                Log.e("Estatus"+i, row.getString(9));
                                Log.e("-","---------------------------------------");
                                row.moveToNext();
                            }

                        }*/

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

            /**Funcion para validar que secciones del menu tiene permitido ver el usuario*/
            ShowMenuItems();

            /**Coloca el nombre del usuario que inicio sesion en el menu lateral*/
            tvNameUser.setText(session.getUser().get(1)+" "+session.getUser().get(2)+" "+session.getUser().get(3));

            /**Coloca la vista de cartera por defecto*/
            setFragment(ORDERS, null);

            /**Evento de click para abrir el menu lateral*/
            NVmenu.setNavigationItemSelectedListener(NVmenu_onClick);
            /**Evento de click para abrir vista de perfil aun no se ocupa*/
            llProfile.setOnClickListener(LLprofile_OnClick);
            /**Evento de click para cerrar sesion*/
            ivLogout.setOnClickListener(ivLogout_OnClick);
        }
        else{/** esto era cuando se lanzo por primera vez sidert movil y solo tenia Denuncia PLD pero
                ya no se ocupa esta vista*/
            mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            TBmain.setVisibility(View.GONE);
            mTabLayout.setVisibility(View.GONE);
            setFragment(NameFragments.COMPLAINT_TEMP, null);
        }

    }

    /**Listener el menu lateral... Si se agrega otro menu tendrá que colocarse aqui
     * para que funcione el eventro de la transicion de una vista a otra
     * */
    private NavigationView.OnNavigationItemSelectedListener NVmenu_onClick = new NavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.nvCartera:/**Cuando seleccioan el menu Cartera: Cartera que tiene asignado el asesor/gestor*/
                    setFragment(ORDERS, null);
                    break;
                case R.id.nvOriginacion:/**Cuando seleccioan el menu Originación para crear una nueva solicitud Individual o grupal*/
                    Intent i_solicitud = new Intent(getApplicationContext(), SolicitudCredito.class);
                    startActivity(i_solicitud);
                    break;
                case R.id.nvRenovacion:/**Cuando seleccioan el menu Renovación para crear un reonvacion individal o grupal*/
                    Intent i_renovacion = new Intent(getApplicationContext(), RenovacionCredito.class);
                    startActivity(i_renovacion);
                    break;
                case R.id.nvSolicitudes:/**Cuando seleccioan el menu Solicitudes son las que ve el gerentes que ya fueron autorizadas por la admin y solo falta que el autorice*/
                    Intent i_solicitudes = new Intent(getApplicationContext(), Solicitudes.class);
                    startActivity(i_solicitudes);
                    break;
                case R.id.nvConfiguraciones:/**Cuando seleccionan Configuraciones es para Sincronizado manual (envio/descarga de datos) que quedaron pendiente de envio*/
                    Intent i_config = new Intent(getApplicationContext(), Configuracion.class);
                    startActivity(i_config);
                    break;
                case R.id.nvImpresiones:/**Cuando seleccionan Impresion es para ver el log de impresiones realizadas Recuperacion/Cobranza/Vencida*/
                    setFragment(NameFragments.IMPRESSION_HISTORY, null);
                    //setFragment(NameFragments.AUTORIZAR_CC, null);
                    break;
                /*case R.id.nvCC:
                    //Intent i_cc = new Intent(getApplicationContext(), CirculoCredito.class);
                    //Intent i_cc = new Intent(getApplicationContext(), RecuperacionCC.class);
                    Intent i_cc = new Intent(getApplicationContext(), CobrosCC.class);
                    //Intent i_cc = new Intent(getApplicationContext(), ConsultadosCC.class);
                    startActivity(i_cc);
                    break;*/
                case R.id.nvCobroCC:/**Cuando seleccioan Cobro CC es para realizar un cobro en efectivo e impresion de CC*/
                    Intent i_cc = new Intent(getApplicationContext(), CobrosCC.class);
                    startActivity(i_cc);
                    break;
                case R.id.nvCobroAGF:/**Cuando seleccioan Cobro AGF es para realizar un cobro en efectivo e impresion de AGF*/
                    Intent i_agf = new Intent(getApplicationContext(), ApoyoGastosFunerariosActivity.class);
                    startActivity(i_agf);
                    break;
                case R.id.nvRuta:/**Cuando seleccioan Ruta es para poder buscar la ruta que ha realizado el asesor por dia*/
                    Intent i_ruta = new Intent(getApplicationContext(), TrackerAsesor.class);
                    startActivity(i_ruta);
                    break;
                case R.id.nvGeolocalizar:/**Cuando seleccionan Geolocalizar para poder realizar la geolocalizaciones de los clientes*/
                    setFragment(GEOLOCALIZACION, null);
                    break;
                case R.id.nvLogin:/**Cuando seleccionan Sesiones podran visualizar los inicios de sesiones de los asesores por dia*/
                    Intent i_log_login = new Intent(getApplicationContext(), ReporteInicioSesion.class);
                    startActivity(i_log_login);
                    break;
                case R.id.nvMesaAyuda:/**Cuando seleccionan Mesa de ayuda los asesores podran reportar detalles que tengan con el equipo/cartera/impresiones*/
                    setFragment(MESA_AYUDA, null);
                    break;
                case R.id.nvGuiaRapida:/**Cuando seleccionan Mesa de ayuda los asesores podran reportar detalles que tengan con el equipo/cartera/impresiones*/
                    Intent help = new Intent(ctx, PdfReaderActivity.class);
                    startActivity(help);
                    break;
      /*          case R.id.nvConsultaCC: --Cuando seleccionan Mesa de ayuda los asesores podran reportar detalles que tengan con el equipo/cartera/impresiones
                    Intent consulta_cc = new Intent(getApplicationContext(), ConsultarCC.class);
                    startActivity(consulta_cc);
                    break;
                    */
                default:/**Manda directo a la validacion de inicio de sesion o cartera*/
                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                    finish();
                    break;
            }
            if(!mDrawerLayout.isLocked()) mDrawerLayout.closeDrawer(NVmenu);
            return true;
        }
    };

    /**Funcion para hacer las trancisiones de vistas solo de los Fragments*/
    public void setFragment(String fragment, Bundle extras) {
        mTabLayout.setVisibility(View.GONE);
        Fragment current = getSupportFragmentManager().findFragmentById(R.id.FLmain);
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        String tokenFragment = "";
        switch (fragment) {
            case ORDERS:/**Fragmento de Cartera*/
                mTabLayout.setVisibility(View.VISIBLE);
                if (!(current instanceof orders_fragment)){
                    orders_fragment myAppointments = new orders_fragment();
                    myAppointments.setArguments(extras);
                    transaction.replace(R.id.FLmain, myAppointments, ORDERS);
                    tokenFragment = ORDERS;
                } else
                    return;
                break;
            case NameFragments.IMPRESSION_HISTORY:/**Fragmento de Impresiones*/
                if (!(current instanceof impression_history_fragment)){
                    impression_history_fragment impression_history = new impression_history_fragment();
                    impression_history.setArguments(extras);
                    transaction.replace(R.id.FLmain, impression_history, NameFragments.IMPRESSION_HISTORY);
                    tokenFragment = NameFragments.IMPRESSION_HISTORY;
                } else
                    return;
                break;
            case NameFragments.AUTORIZAR_CC:/**Fragmento de autorizaciones de CC*/
                if (!(current instanceof autorizaciones_cc_fragment)){
                    autorizaciones_cc_fragment impression_history = new autorizaciones_cc_fragment();
                    impression_history.setArguments(extras);
                    transaction.replace(R.id.FLmain, impression_history, NameFragments.AUTORIZAR_CC);
                    tokenFragment = NameFragments.AUTORIZAR_CC;
                } else
                    return;
                break;
            case MESA_AYUDA:/**Fragmento de Mesa de ayuda*/
                if (!(current instanceof mesa_ayuda_fragment)){
                    mesa_ayuda_fragment mesa_ayuda = new mesa_ayuda_fragment();
                    mesa_ayuda.setArguments(extras);
                    transaction.replace(R.id.FLmain, mesa_ayuda, MESA_AYUDA);
                    tokenFragment = MESA_AYUDA;
                } else
                    return;
                break;
            case GEOLOCALIZACION:/**Fragmento de Geolocalizaciones*/
                mTabLayout.setVisibility(View.VISIBLE);
                if (!(current instanceof geolocalizacion_fragment)){
                    geolocalizacion_fragment geolocalizacion = new geolocalizacion_fragment();
                    geolocalizacion.setArguments(extras);
                    transaction.replace(R.id.FLmain, geolocalizacion, GEOLOCALIZACION);
                    tokenFragment = GEOLOCALIZACION;
                } else
                    return;
                break;
            default:/**Fragmento de Cartera por default*/
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

    /**Inicializacion de variables del menu lateral como el nombre d usuario*/
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

    /**Evento para abrir la vista del perfil del usuario no se está ocupando*/
    private View.OnClickListener LLprofile_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i = new Intent(ctx, Perfil.class);
            //startActivity(i);
        }
    };

    /**Evento que abre un dialogFragment para cerrar sesion*/
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

    /**Colocar el un identificador y nombre de usuario en los crash cuando se cierra la app*/
    /*private void logUserFabric() {
        Crashlytics.setUserIdentifier(session.getUser().get(0));
        Crashlytics.setUserName(session.getUser().get(1)+" "+session.getUser().get(2)+" "+session.getUser().get(3));
    }*/

    @Override
    public void onBackPressed() {
        if(mDrawerLayout.isDrawerOpen(Gravity.LEFT)){
            mDrawerLayout.closeDrawer(Gravity.LEFT);
        }else{
            super.onBackPressed();
        }
    }

    /**Funcion para validar que menus se van a mostrar de acuerdo a los permisos asignados al usuario*/
    private void ShowMenuItems (){
        try {
            if (session.getUser().get(8) != null) {
                /**Se obtienen los mudulos asignados guardado en la variable de sesion*/
                JSONArray jsonModulos = new JSONArray(session.getUser().get(8));
                for (int i = 0; i < jsonModulos.length(); i++) {
                    JSONObject item = jsonModulos.getJSONObject(i);
                    switch (item.getString("nombre").toLowerCase()) {
                        case "cartera":/**Si tiene permiso de cartera*/
                            menuGeneral.getItem(0).setVisible(true);
                            break;
                        case "geolocalizar":/**Si tiene permiso de geolocalizar*/
                            menuGeneral.getItem(1).setVisible(true);
                            break;
                        case "impresion":/**Si tiene permiso de impresion*/
                            menuGeneral.getItem(2).setVisible(true);
                            break;
                        case "ruta":/**Si tiene permiso de ruta*/
                            menuGeneral.getItem(3).setVisible(true);
                            break;
                        case "recuperacion agf":/**Si tiene permiso de recuperacion agf*/
                            menuGeneral.getItem(4).setVisible(true);
                            break;
                        case "recuperacion cc":/**Si tiene permiso de recuperacion cc*/
                            menuGeneral.getItem(5).setVisible(true);
                            break;
                        case "originacion":/**Si tiene permiso de originacion*/
                            menuGeneral.getItem(6).setVisible(true);
                            break;
                        case "renovacion":/**Si tiene permiso de renovacion*/
                            menuGeneral.getItem(7).setVisible(true);
                            break;
                        case "solicitudes":/**Si tiene permiso de solicitudes*/
                            //menuGeneral.getItem(8).setVisible(true);
                            break;
                        case "sesiones":/**Si tiene permiso de sesiones*/
                            menuGeneral.getItem(9).setVisible(true);
                            break;
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
