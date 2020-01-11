package com.sidert.sidertmovil;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
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
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.crashlytics.android.Crashlytics;
import com.sidert.sidertmovil.activities.AcercaDe;
import com.sidert.sidertmovil.activities.OriginacionI;
import com.sidert.sidertmovil.activities.Perfil;
import com.sidert.sidertmovil.activities.SolicitudCredito;
import com.sidert.sidertmovil.fragments.ComplaintTemp;
import com.sidert.sidertmovil.fragments.dialogs.dialog_logout;
import com.sidert.sidertmovil.fragments.dialogs.dialog_mailbox;
import com.sidert.sidertmovil.fragments.dialogs.dialog_synchronize_db;
import com.sidert.sidertmovil.fragments.erase_table_fragment;
import com.sidert.sidertmovil.fragments.geolocalizacion_fragment;
import com.sidert.sidertmovil.fragments.impression_history_fragment;
import com.sidert.sidertmovil.fragments.orders_fragment;
import com.sidert.sidertmovil.fragments.solicitud_credito_fragment;
import com.sidert.sidertmovil.utils.BkgJobServiceLogout;
import com.sidert.sidertmovil.utils.BkgJobServiceSincronizado;
import com.sidert.sidertmovil.utils.Constants;
import com.sidert.sidertmovil.utils.CustomDrawerLayout;
import com.sidert.sidertmovil.utils.CustomRelativeLayout;
import com.sidert.sidertmovil.utils.Miscellaneous;
import com.sidert.sidertmovil.utils.NameFragments;
import com.sidert.sidertmovil.utils.SessionManager;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import io.fabric.sdk.android.Fabric;


public class Home extends AppCompatActivity{

    private Context ctx;
    private ActionBarDrawerToggle mToggled;
    private Toolbar TBmain;
    private CustomDrawerLayout mDrawerLayout;
    private NavigationView NVmenu;
    private TabLayout mTabLayout;
    private CoordinatorLayout CLcontainer;
    private TextView tvNameUser;
    private LinearLayout llProfile;
    private ImageView ivLogout;
    private boolean canExitApp = false;
    private SessionManager session;

    public interface Sidert {
        void initTabLayout(TabLayout Tabs);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ctx             = getApplicationContext();
        Fabric.with(this, new Crashlytics());
        //logUserFabric();
        session         = new SessionManager(ctx);
        TBmain          = findViewById(R.id.TBmain);
        mDrawerLayout   = findViewById(R.id.mDrawerLayout);
        NVmenu          = findViewById(R.id.NVmenu);
        mTabLayout      = findViewById(R.id.mTabLayout);
        CLcontainer     = findViewById(R.id.CLcontainer);
        View view       = NVmenu.getHeaderView(0);
        tvNameUser      = view.findViewById(R.id.tvName);
        llProfile       = view.findViewById(R.id.llProfile);
        ivLogout        = view.findViewById(R.id.ivLogout);

        Bundle data = getIntent().getExtras();
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

            tvNameUser.setText(session.getUser().get(1)+" "+session.getUser().get(2)+" "+session.getUser().get(3));
            if(session.getUser().get(5) != null && (session.getUser().get(5).contains("ROLE_GERENTESUCURSAL") || session.getUser().get(5).contains("ROLE_GERENTEREGIONAL") || session.getUser().get(5).contains("ROLE_COORDINADOR") || session.getUser().get(5).contains("ROLE_DIRECCION"))) {
                NVmenu.getMenu().clear();
                NVmenu.inflateMenu(R.menu.navigation_menu_gerente_sucursal);
                if (!Miscellaneous.JobServiceEnable(ctx, Constants.ID_JOB_SINCRONIZADO, "SINCRONIZADO")) {
                    Log.e("sincronizado", "On Start Service Job");
                    ComponentName serviceComponent;
                    serviceComponent = new ComponentName(ctx, BkgJobServiceSincronizado.class);
                    JobInfo.Builder builder = new JobInfo.Builder(Constants.ID_JOB_SINCRONIZADO, serviceComponent);
                    builder.setPeriodic(10 * 60 * 1000);
                    //builder.setMinimumLatency(100);
                    //builder.setOverrideDeadline(100);
                    JobScheduler jobScheduler = (JobScheduler) ctx.getSystemService(Context.JOB_SCHEDULER_SERVICE);
                    jobScheduler.schedule(builder.build());
                } else
                    Log.e("Login", "Enable Service Job");
                setFragment(NameFragments.GEOLOCALIZACION, null);
            }
            else if(session.getUser().get(5) != null && session.getUser().get(5).contains("PROGRAMADOR")){
                NVmenu.getMenu().clear();
                NVmenu.inflateMenu(R.menu.navigation_menu_programador);
                setFragment(NameFragments.ERASE_TABLES, null);
            }
            else if(session.getUser().get(5) != null && session.getUser().get(5).contains("ROLE_ANALISTA")){
                NVmenu.getMenu().clear();
                NVmenu.inflateMenu(R.menu.navigation_menu_analista);
                if (!Miscellaneous.JobServiceEnable(ctx, Constants.ID_JOB_SINCRONIZADO, "SINCRONIZADO")) {
                    Log.e("Login", "On Start Service Job");
                    ComponentName serviceComponent;
                    serviceComponent = new ComponentName(ctx, BkgJobServiceSincronizado.class);
                    JobInfo.Builder builder = new JobInfo.Builder(Constants.ID_JOB_SINCRONIZADO, serviceComponent);
                    builder.setPeriodic(10 * 60 * 1000);
                    //builder.setMinimumLatency(100);
                    //builder.setOverrideDeadline(100);
                    JobScheduler jobScheduler = (JobScheduler) ctx.getSystemService(Context.JOB_SCHEDULER_SERVICE);
                    jobScheduler.schedule(builder.build());
                } else
                    Log.e("Login", "Enable Service Job");
                setFragment(NameFragments.GEOLOCALIZACION, null);
            }
            else if(session.getUser().get(5) != null && session.getUser().get(5).contains("ROLE_SUPER")){
                NVmenu.getMenu().clear();
                NVmenu.inflateMenu(R.menu.navigation_menu_super);
                /*if (!Miscellaneous.JobServiceEnable(ctx, Constants.ID_JOB_SINCRONIZADO, "SINCRONIZADO")) {
                    Log.e("Login", "On Start Service Job");
                    ComponentName serviceComponent;
                    serviceComponent = new ComponentName(ctx, BkgJobServiceSincronizado.class);
                    JobInfo.Builder builder = new JobInfo.Builder(Constants.ID_JOB_SINCRONIZADO, serviceComponent);
                    builder.setPeriodic(10 * 60 * 1000);
                    //builder.setMinimumLatency(100);
                    //builder.setOverrideDeadline(100);
                    JobScheduler jobScheduler = (JobScheduler) ctx.getSystemService(Context.JOB_SCHEDULER_SERVICE);
                    jobScheduler.schedule(builder.build());
                } else
                    Log.e("Login", "Enable Service Job");*/
                setFragment(NameFragments.ERASE_TABLES, null);
            }
            else {
                setFragment(NameFragments.ORDERS, null);
            }

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
                case R.id.NVorders:
                    setFragment(NameFragments.ORDERS, null);
                    break;
                case R.id.NVsolicitudCredito:
                    //setFragment(NameFragments.SOLICITUD_CREDITO, null);
                    Intent i_solicitud = new Intent(getApplicationContext(), SolicitudCredito.class);
                    startActivity(i_solicitud);
                    break;
                /*case R.id.NVoriginacion:
                    Intent i_originacion = new Intent(getApplicationContext(), OriginacionI.class);
                    startActivity(i_originacion);
                    break;*/
                /*case R.id.NVsettings:
                    //setFragment(fragments.ORDERS, null);
                    break;*/
                case R.id.NVcomplaint:
                    dialog_mailbox complaint = new dialog_mailbox();
                    complaint.show(getSupportFragmentManager(), NameFragments.DIALOGMAILBOX);
                    break;
                /*case R.id.NVhelp:
                    //setFragment(fragments.ORDERS, null);
                    break;*/
                case R.id.NVabout:
                    Intent i_about = new Intent(getApplicationContext(), AcercaDe.class);
                    startActivity(i_about);
                    break;
                case R.id.NVsynchronized:
                    dialog_synchronize_db synchronize_db = new dialog_synchronize_db();
                    synchronize_db.show(getSupportFragmentManager(), NameFragments.DIALOGSYNCHRONIZE);
                    break;
                case R.id.NVlogPrint:
                    setFragment(NameFragments.IMPRESSION_HISTORY, null);
                    break;
                /*case R.id.NVimpresiones:
                    setFragment(NameFragments.IMPRESSION_HISTORY, null);
                    break;*/
                case R.id.NVeraseTable:
                    setFragment(NameFragments.ERASE_TABLES, null);
                    break;
                case R.id.NVgeolocalizacion:
                    setFragment(NameFragments.GEOLOCALIZACION, null);
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
            case NameFragments.ORDERS:
                mTabLayout.setVisibility(View.VISIBLE);
                if (!(current instanceof orders_fragment)){
                    orders_fragment myAppointments = new orders_fragment();
                    myAppointments.setArguments(extras);
                    transaction.replace(R.id.FLmain, myAppointments, NameFragments.ORDERS);
                    tokenFragment = NameFragments.ORDERS;
                } else
                    return;
                break;
            case NameFragments.COMPLAINT_TEMP:
                mTabLayout.setVisibility(View.VISIBLE);
                if (!(current instanceof orders_fragment)){
                    ComplaintTemp complaintTemp = new ComplaintTemp();
                    complaintTemp.setArguments(extras);
                    transaction.replace(R.id.FLmain, complaintTemp, NameFragments.COMPLAINT_TEMP);
                    tokenFragment = NameFragments.COMPLAINT_TEMP;
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
            case NameFragments.ERASE_TABLES:
                if (!(current instanceof erase_table_fragment)){
                    erase_table_fragment erase_table = new erase_table_fragment();
                    erase_table.setArguments(extras);
                    transaction.replace(R.id.FLmain, erase_table, NameFragments.ERASE_TABLES);
                    tokenFragment = NameFragments.ERASE_TABLES;
                } else
                    return;
                break;
            case NameFragments.TABLAS:
                if (!(current instanceof erase_table_fragment)){
                    erase_table_fragment erase_table = new erase_table_fragment();
                    erase_table.setArguments(extras);
                    transaction.replace(R.id.FLmain, erase_table, NameFragments.TABLAS);
                    tokenFragment = NameFragments.TABLAS;
                } else
                    return;
                break;
            case NameFragments.GEOLOCALIZACION:
                mTabLayout.setVisibility(View.VISIBLE);
                if (!(current instanceof geolocalizacion_fragment)){
                    geolocalizacion_fragment geolocalizacion = new geolocalizacion_fragment();
                    geolocalizacion.setArguments(extras);
                    transaction.replace(R.id.FLmain, geolocalizacion, NameFragments.GEOLOCALIZACION);
                    tokenFragment = NameFragments.GEOLOCALIZACION;
                } else
                    return;
                break;
            case NameFragments.SOLICITUD_CREDITO:
                mTabLayout.setVisibility(View.VISIBLE);
                if (!(current instanceof solicitud_credito_fragment)){
                    solicitud_credito_fragment solicitud_credito = new solicitud_credito_fragment();
                    solicitud_credito.setArguments(extras);
                    transaction.replace(R.id.FLmain, solicitud_credito, NameFragments.SOLICITUD_CREDITO);
                    tokenFragment = NameFragments.SOLICITUD_CREDITO;
                } else
                    return;
                break;
            default:
                if (!(current instanceof orders_fragment)){
                    transaction.replace(R.id.FLmain, new orders_fragment(), NameFragments.ORDERS);
                    tokenFragment = NameFragments.ORDERS;
                } else
                    return;
                break;
        }

        if(!tokenFragment.equals(NameFragments.COMPLAINT_TEMP) && !tokenFragment.equals(NameFragments.IMPRESSION_HISTORY) && !tokenFragment.equals(NameFragments.ORDERS) && !tokenFragment.equals(NameFragments.GEOLOCALIZACION)) {
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
        Crashlytics.setUserIdentifier("12345");
        Crashlytics.setUserName("Alejandro Isaias Lopez Jim");
    }

}
