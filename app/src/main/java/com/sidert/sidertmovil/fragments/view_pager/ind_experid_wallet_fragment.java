package com.sidert.sidertmovil.fragments.view_pager;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sidert.sidertmovil.R;


public class ind_experid_wallet_fragment extends Fragment {

    private Context ctx;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ind_experid_wallet, container, false);
        ctx = getContext();
        return view;
    }

}
