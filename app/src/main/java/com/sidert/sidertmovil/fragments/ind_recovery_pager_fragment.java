package com.sidert.sidertmovil.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.activities.IndividualRecovery;
import com.sidert.sidertmovil.adapters.TabsRecentsAdapter;
import com.sidert.sidertmovil.fragments.view_pager.financial_info_fragment;
import com.sidert.sidertmovil.fragments.view_pager.ind_management_fragment;
import com.sidert.sidertmovil.fragments.view_pager.ind_recovery_fragment;
import com.sidert.sidertmovil.fragments.view_pager.payment_log_fragment;
import com.sidert.sidertmovil.utils.CustomViewPager;


public class ind_recovery_pager_fragment extends Fragment {

    private IndividualRecovery boostrap;
    private Context ctx;
    private CustomViewPager mViewPager;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.viewpager_fragment, container, false);
        boostrap    = (IndividualRecovery) getActivity();
        ctx         = getContext();
        mViewPager  = view.findViewById(R.id.mViewPager);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //initView();
        setHasOptionsMenu(true);
        boostrap.setTitle(ctx.getString(R.string.order));
        boostrap.getSupportActionBar().show();
    }




}
