package com.sidert.sidertmovil.activities;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.adapters.TabsRecentsAdapter;
import com.sidert.sidertmovil.fragments.view_pager.financial_info_fragment;
import com.sidert.sidertmovil.fragments.view_pager.group_management_fragment;
import com.sidert.sidertmovil.fragments.view_pager.group_recovery_fragment;
import com.sidert.sidertmovil.fragments.view_pager.payment_log_fragment;
import com.sidert.sidertmovil.utils.Constants;
import com.sidert.sidertmovil.utils.CustomViewPager;

public class GroupRecovery extends AppCompatActivity {

    private Context ctx;
    private Toolbar TBmain;
    private TabLayout mTabLayout;
    private boolean canExitApp = false;
    private CustomViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Constants.ENVIROMENT)
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_group_recovery);
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

        adapter.addFragment(new group_recovery_fragment(), "");
        adapter.addFragment(new group_management_fragment(), "");
        adapter.addFragment(new payment_log_fragment(), "");
        adapter.addFragment(new financial_info_fragment(), "");
        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setVisibility(View.VISIBLE);
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);

        mViewPager.setSwipeable(false);
        mTabLayout.getTabAt(0).setIcon(R.drawable.ic_group);
        mTabLayout.getTabAt(1).setIcon(R.drawable.ic_attach_money);
        mTabLayout.getTabAt(2).setIcon(R.drawable.ic_payment_log);
        mTabLayout.getTabAt(3).setIcon(R.drawable.ic_checklist_white);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
