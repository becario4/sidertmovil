package com.sidert.sidertmovil.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.activities.GroupRecovery;
import com.sidert.sidertmovil.utils.CustomViewPager;


public class group_recovery_pager_fragment extends Fragment {

    private GroupRecovery boostrap;
    private Context ctx;
    private CustomViewPager mViewPager;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.viewpager_fragment, container, false);
        boostrap    = (GroupRecovery) getActivity();
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
