package com.sidert.sidertmovil.fragments;


import android.content.Context;
import android.os.Bundle;
//import android.support.design.widget.TabLayout;
//import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.android.material.tabs.TabLayout;
import com.sidert.sidertmovil.Home;
import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.adapters.TabsRecentsAdapter;
import com.sidert.sidertmovil.fragments.view_pager.answers_fragment;
//import com.sidert.sidertmovil.fragments.view_pager.fichas_pendientes_fragment;
import com.sidert.sidertmovil.fragments.view_pager.fichas_pendientes_fragment;
import com.sidert.sidertmovil.fragments.view_pager.route_fragment;
import com.sidert.sidertmovil.utils.CustomViewPager;
import com.sidert.sidertmovil.utils.Miscellaneous;

/**Clase de ViewPager donde se visualizan Cartera, Ruta, Contestadas*/
public class orders_fragment extends Fragment {

    private Home boostrap;
    private Context ctx;
    private CustomViewPager mViewPager;
    private View view;
    private TextView tvContFiltros;
    private TabLayout mTabs;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view        = inflater.inflate(R.layout.viewpager_fragment, container, false);
        boostrap    = (Home) getActivity();
        ctx         = getContext();
        mViewPager  = view.findViewById(R.id.mViewPager);

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
                adapter.addFragment(new fichas_pendientes_fragment(), ctx.getString(R.string.cartera));
                adapter.addFragment(new route_fragment(), ctx.getString(R.string.route));
                adapter.addFragment(new answers_fragment(), ctx.getString(R.string.answers));
                mViewPager.setAdapter(adapter);
                Tabs.setupWithViewPager(mViewPager);
                Tabs.setVisibility(View.VISIBLE);
                Tabs.setTabMode(TabLayout.MODE_FIXED);
                mViewPager.setSwipeable(true);
                mTabs = Tabs;
                mTabs.getTabAt(0).setCustomView(R.layout.view_badge_tab_green);
                mTabs.getTabAt(1).setCustomView(R.layout.view_badge_tab_green);
                mTabs.getTabAt(2).setCustomView(R.layout.view_tab_default);
                TextView tvTituloUno = mTabs.getTabAt(0).getCustomView().findViewById(R.id.tvTitulo);
                TextView tvCountUno = mTabs.getTabAt(0).getCustomView().findViewById(R.id.tvCount);
                TextView tvTituloDos = mTabs.getTabAt(1).getCustomView().findViewById(R.id.tvTitulo);
                TextView tvCountDos = mTabs.getTabAt(1).getCustomView().findViewById(R.id.tvCount);
                TextView tvTituloTres = mTabs.getTabAt(2).getCustomView().findViewById(R.id.tvTitulo);
                tvTituloUno.setText(ctx.getString(R.string.cartera));
                tvCountUno.setText("0");
                tvTituloDos.setText(ctx.getString(R.string.route));
                tvCountDos.setText("0");
                tvTituloTres.setText(ctx.getString(R.string.answers));

            }
        });
        boostrap.setTitle(Miscellaneous.ucFirst(ctx.getString(R.string.orders)));
    }

    /**Aqui se coloca el contador de fichas que se visualizan dependiendo a la vista
     * index
     * 0 = Cartera
     * 1 = Ruta
     * 2 = Contestadas
     * y count es el total de fichas que se visualizan*/
    public void SetUpBagde(int index, int count){
        TextView tvBadge = mTabs.getTabAt(index).getCustomView().findViewById(R.id.tvCount);
        tvBadge.setText(String.valueOf(count));
    }

}
