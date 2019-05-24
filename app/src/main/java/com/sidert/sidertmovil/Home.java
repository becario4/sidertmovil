package com.sidert.sidertmovil;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.bumptech.glide.Glide;
import com.sidert.sidertmovil.activities.Profile;
import com.sidert.sidertmovil.fragments.dialogs.dialog_logout;
import com.sidert.sidertmovil.fragments.dialogs.dialog_mailbox;
import com.sidert.sidertmovil.fragments.orders_fragment;
import com.sidert.sidertmovil.utils.Constants;
import com.sidert.sidertmovil.utils.CustomDrawerLayout;
import com.sidert.sidertmovil.utils.CustomRelativeLayout;
import com.sidert.sidertmovil.utils.Miscellaneous;
import com.sidert.sidertmovil.utils.NameFragments;
import com.sidert.sidertmovil.utils.Popups;

import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class Home extends AppCompatActivity{

    private Context ctx;
    private ActionBarDrawerToggle mToggled;
    private Toolbar TBmain;
    private CustomDrawerLayout mDrawerLayout;
    private NavigationView NVmenu;
    private Menu navigationMore;
    private TabLayout mTabLayout;
    private CoordinatorLayout CLcontainer;
    private TextView tvNameUser;
    private CircleImageView civAvatar;
    private LinearLayout llProfile;
    private ImageView ivLogout;
    //private FrameLayout FLmain;
    private boolean canExitApp = false;

    public interface Sidert {
        void initTabLayout(TabLayout Tabs);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_home);
        ctx             = getApplicationContext();
        TBmain          = findViewById(R.id.TBmain);
        mDrawerLayout   = findViewById(R.id.mDrawerLayout);
        NVmenu          = findViewById(R.id.NVmenu);
        mTabLayout      = findViewById(R.id.mTabLayout);
        CLcontainer     = findViewById(R.id.CLcontainer);
        View view       = NVmenu.getHeaderView(0);
        civAvatar       = view.findViewById(R.id.civAvatar);
        llProfile       = view.findViewById(R.id.llProfile);
        ivLogout        = view.findViewById(R.id.ivLogout);

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
        setFragment(NameFragments.ORDERS, null);

        NVmenu.setNavigationItemSelectedListener(NVmenu_onClick);
        llProfile.setOnClickListener(LLprofile_OnClick);
        ivLogout.setOnClickListener(ivLogout_OnClick);

    }

    private NavigationView.OnNavigationItemSelectedListener NVmenu_onClick = new NavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.NVorders:
                    setFragment(NameFragments.ORDERS, null);
                    break;
                case R.id.NVmessages:
                    //setFragment(fragments.ORDERS, null);
                    break;
                case R.id.NVmap:
                    //setFragment(fragments.ORDERS, null);
                    break;
                case R.id.NVsettings:
                    //setFragment(fragments.ORDERS, null);
                    break;
                case R.id.NVcomplaint:
                    dialog_mailbox complaint = new dialog_mailbox();
                    complaint.show(getSupportFragmentManager(), NameFragments.DIALOGMAILBOX);
                    break;
                case R.id.NVhelp:
                    //setFragment(fragments.ORDERS, null);
                    break;
                case R.id.NVabout:
                    //setFragment(fragments.ORDERS, null);
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
                    return;;
                break;
            default:
                if (!(current instanceof orders_fragment)){
                    transaction.replace(R.id.FLmain, new orders_fragment(), NameFragments.ORDERS);
                    tokenFragment = NameFragments.ORDERS;
                } else
                    return;
                break;
        }

        if(!tokenFragment.equals(NameFragments.ORDERS)) {
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
        civAvatar                      = view.findViewById(R.id.civAvatar);
        final String pic = "";
        NVmenu.post(new Runnable() {
            @Override
            public void run() {
                if(pic != null && !pic.isEmpty()) {
                    Glide.with(ctx).load(pic).into(civAvatar);
                } else {
                    Glide.with(getApplicationContext()).load(R.drawable.ic_default_user).into(civAvatar);
                }
                //TVnameprofile.setText(session.profile().get(6)+" "+session.profile().get(1).toString());
            }
        });
    }

    private View.OnClickListener LLprofile_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i = new Intent(ctx, Profile.class);
            startActivity(i);
        }
    };

    private View.OnClickListener ivLogout_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            dialog_logout mess_confirm = new dialog_logout();
            Bundle b = new Bundle();
            b.putString(Constants.message, getApplicationContext().getString(R.string.mess_logout));
            mess_confirm.setArguments(b);
            mess_confirm.show(getSupportFragmentManager(), NameFragments.DIALOGLOGOUT);
        }
    };

}
