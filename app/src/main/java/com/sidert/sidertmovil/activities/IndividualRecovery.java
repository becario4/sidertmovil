package com.sidert.sidertmovil.activities;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.adapters.TabsRecentsAdapter;
import com.sidert.sidertmovil.fragments.view_pager.financial_info_fragment;
import com.sidert.sidertmovil.fragments.view_pager.ind_management_fragment;
import com.sidert.sidertmovil.fragments.view_pager.ind_recovery_fragment;
import com.sidert.sidertmovil.fragments.view_pager.payment_log_fragment;
import com.sidert.sidertmovil.utils.CustomViewPager;

public class IndividualRecovery extends AppCompatActivity {

    private Context ctx;
    private Toolbar TBmain;
    private TabLayout mTabLayout;
    private boolean canExitApp = false;
    private CustomViewPager mViewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_individual_recovery);
        ctx             = getApplicationContext();
        TBmain          = findViewById(R.id.TBmain);
        mTabLayout      = findViewById(R.id.mTabLayout);
        mViewPager      = findViewById(R.id.mViewPager);

        setSupportActionBar(TBmain);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle(getApplicationContext().getString(R.string.order));

        setUpViewPager();
    }

    private void setUpViewPager() {
        TabsRecentsAdapter adapter = new TabsRecentsAdapter(getSupportFragmentManager());

        adapter.addFragment(new ind_recovery_fragment(), "");
        adapter.addFragment(new ind_management_fragment(), "");
        adapter.addFragment(new payment_log_fragment(), "");
        adapter.addFragment(new financial_info_fragment(), "");
        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setVisibility(View.VISIBLE);
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);

        mViewPager.setSwipeable(false);
        mTabLayout.getTabAt(0).setIcon(R.drawable.ic_single);
        mTabLayout.getTabAt(1).setIcon(R.drawable.ic_attach_money);
        mTabLayout.getTabAt(2).setIcon(R.drawable.ic_payment_log);
        mTabLayout.getTabAt(3).setIcon(R.drawable.ic_checklist_white);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_save, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.save:

                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
