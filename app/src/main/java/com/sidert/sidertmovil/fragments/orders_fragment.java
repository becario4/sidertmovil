package com.sidert.sidertmovil.fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.OnClickListener;
import com.orhanobut.dialogplus.ViewHolder;
import com.sidert.sidertmovil.Home;
import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.activities.ResumenGeo;
import com.sidert.sidertmovil.adapters.TabsRecentsAdapter;
import com.sidert.sidertmovil.fragments.view_pager.answers_fragment;
import com.sidert.sidertmovil.fragments.view_pager.fichas_pendientes_fragment;
import com.sidert.sidertmovil.fragments.view_pager.route_fragment;
import com.sidert.sidertmovil.utils.CustomViewPager;
import com.sidert.sidertmovil.utils.Miscellaneous;

import java.util.HashMap;


public class orders_fragment extends Fragment {

    private Home boostrap;
    private Context ctx;
    private CustomViewPager mViewPager;
    private View view;
    private TextView tvContFiltros;

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
                adapter.addFragment(new fichas_pendientes_fragment(), ctx.getString(R.string.pendings));
                adapter.addFragment(new route_fragment(), ctx.getString(R.string.route));
                adapter.addFragment(new answers_fragment(), ctx.getString(R.string.answers));
                mViewPager.setAdapter(adapter);
                Tabs.setupWithViewPager(mViewPager);
                Tabs.setVisibility(View.VISIBLE);
                Tabs.setTabMode(TabLayout.MODE_FIXED);
                mViewPager.setSwipeable(true);

            }
        });
        boostrap.setTitle(Miscellaneous.ucFirst(ctx.getString(R.string.orders)));
    }

}
