package com.sidert.sidertmovil.activities;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.adapters.TabsRecentsAdapter;
import com.sidert.sidertmovil.fragments.view_pager.ci_detalle_fragment;
import com.sidert.sidertmovil.fragments.view_pager.ci_gestion_fragment;
import com.sidert.sidertmovil.fragments.view_pager.ci_pagos_fragment;
import com.sidert.sidertmovil.models.ModeloIndividual;
import com.sidert.sidertmovil.utils.Constants;
import com.sidert.sidertmovil.utils.CustomViewPager;

public class CobranzaIndividual extends AppCompatActivity {

    private Context ctx;
    private Toolbar TBmain;
    private TabLayout mTabLayout;
    private boolean canExitApp = false;
    private CustomViewPager mViewPager;
    public ModeloIndividual ficha_ci;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Constants.ENVIROMENT)
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_cobranza_individual);
        ctx             = getApplicationContext();
        TBmain          = findViewById(R.id.TBmain);
        mTabLayout      = findViewById(R.id.mTabLayout);
        mViewPager      = findViewById(R.id.mViewPager);

        setSupportActionBar(TBmain);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle(getApplicationContext().getString(R.string.order));
        Bundle data = getIntent().getExtras();
        ficha_ci     = (ModeloIndividual) data.getSerializable(Constants.INDIVIDUAL);
        setUpViewPager();
        Intent i_res = new Intent();
        i_res.putExtra(Constants.RESPONSE,1);
        setResult(RESULT_OK, i_res);
    }

    private void setUpViewPager() {
        TabsRecentsAdapter adapter = new TabsRecentsAdapter(getSupportFragmentManager());

        adapter.addFragment(new ci_detalle_fragment(), "");
        adapter.addFragment(new ci_gestion_fragment(), "");
        adapter.addFragment(new ci_pagos_fragment(), "");;

        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setVisibility(View.VISIBLE);
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);

        mViewPager.setSwipeable(true);
        mTabLayout.getTabAt(0).setIcon(R.drawable.ic_single).setContentDescription("Detalle");
        mTabLayout.getTabAt(1).setIcon(R.drawable.ic_attach_money).setContentDescription("Gestión");
        mTabLayout.getTabAt(2).setIcon(R.drawable.ic_payment_log).setContentDescription("Historial de Pagos");
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
