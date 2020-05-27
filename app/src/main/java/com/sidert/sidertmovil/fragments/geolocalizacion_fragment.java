package com.sidert.sidertmovil.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sidert.sidertmovil.Home;
import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.adapters.TabsRecentsAdapter;
import com.sidert.sidertmovil.fragments.view_pager.answers_fragment;
import com.sidert.sidertmovil.fragments.view_pager.fichas_pendientes_fragment;
import com.sidert.sidertmovil.fragments.view_pager.geo_completadas_fragment;
import com.sidert.sidertmovil.fragments.view_pager.geo_pendientes_fragment;
import com.sidert.sidertmovil.fragments.view_pager.route_fragment;
import com.sidert.sidertmovil.utils.CustomViewPager;
import com.sidert.sidertmovil.utils.Miscellaneous;


public class geolocalizacion_fragment extends Fragment {

    private Home boostrap;
    private Context ctx;
    private CustomViewPager mViewPager;
    private View view;
    private TabLayout mTabs;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.viewpager_fragment, container, false);
        boostrap    = (Home) getActivity();
        ctx         = getContext();
        mViewPager  = view.findViewById(R.id.mViewPager);
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        boostrap.getSupportActionBar().show();
    }

    public void initView(){
        boostrap.hasTabLayout(new Home.Sidert() {
            @Override
            public void initTabLayout(TabLayout Tabs) {

                final TabsRecentsAdapter adapter = new TabsRecentsAdapter(getChildFragmentManager());
                adapter.addFragment(new geo_pendientes_fragment(), ctx.getString(R.string.pendings));
                adapter.addFragment(new geo_completadas_fragment(), "Geolocalizadas");
                mViewPager.setAdapter(adapter);
                Tabs.setupWithViewPager(mViewPager);
                Tabs.setVisibility(View.VISIBLE);
                Tabs.setTabMode(TabLayout.MODE_FIXED);
                mViewPager.setSwipeable(true);
                mTabs = Tabs;
                mTabs.getTabAt(0).setCustomView(R.layout.view_badge_tab_text);
                mTabs.getTabAt(1).setCustomView(R.layout.view_badge_tab_green);
                TextView tvTituloUno = mTabs.getTabAt(0).getCustomView().findViewById(R.id.tvTitulo);
                TextView tvCountUno = mTabs.getTabAt(0).getCustomView().findViewById(R.id.tvCount);
                TextView tvTituloDos = mTabs.getTabAt(1).getCustomView().findViewById(R.id.tvTitulo);
                TextView tvCountDos = mTabs.getTabAt(1).getCustomView().findViewById(R.id.tvCount);
                tvTituloUno.setText(ctx.getString(R.string.pendings));
                tvCountUno.setText("0");
                tvTituloDos.setText("Geolocalizadas");
                tvCountDos.setText("0");

            }
        });
        boostrap.setTitle(ctx.getString(R.string.geolocalizacion));
    }

    public void SetUpBagde(int index, int count){
        TextView tvBadge = mTabs.getTabAt(index).getCustomView().findViewById(R.id.tvCount);
        tvBadge.setText(String.valueOf(count));
    }


}
