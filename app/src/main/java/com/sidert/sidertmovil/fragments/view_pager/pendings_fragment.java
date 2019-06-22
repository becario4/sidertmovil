package com.sidert.sidertmovil.fragments.view_pager;


import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sidert.sidertmovil.Home;
import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.activities.IndividualRecovery;
import com.sidert.sidertmovil.database.DBhelper;
import com.sidert.sidertmovil.fragments.dialogs.dialog_details_order;
import com.sidert.sidertmovil.fragments.orders_fragment;
import com.sidert.sidertmovil.utils.Constants;
import com.sidert.sidertmovil.utils.NameFragments;

import java.util.HashMap;


public class pendings_fragment extends Fragment{

    private Context ctx;
    private Home boostrap;
    private LinearLayout llSingle;
    private LinearLayout llGpo;
    private LinearLayout llCvg;
    private LinearLayout llCvi;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pendings, container,false);
        ctx = getContext();
        boostrap = (Home) getActivity();

        llSingle    = view.findViewById(R.id.llSingle);
        llGpo       = view.findViewById(R.id.llgpo);
        llCvg       = view.findViewById(R.id.llCvg);
        llCvi       = view.findViewById(R.id.llCvi);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //saveRecords ();
        llSingle.setOnClickListener(LLsingle_onClick);
        llGpo.setOnClickListener(LLgpo_onClick);
        llCvg.setOnClickListener(LLcvg_OnClick);
        llCvi.setOnClickListener(LLcvi_OnClick);
    }

    private View.OnClickListener LLsingle_onClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ShowOrderSelected (0);
        }
    };

    private View.OnClickListener LLgpo_onClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ShowOrderSelected (1);
        }
    };

    private View.OnClickListener LLcvi_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ShowOrderSelected (4);
        }
    };

    private View.OnClickListener LLcvg_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ShowOrderSelected (5);
        }
    };

    public void ShowOrderSelected (int type){
        dialog_details_order detailsOrder = new dialog_details_order();
        Bundle b = new Bundle();
        b.putInt(Constants.type, type);
        detailsOrder.setArguments(b);
        detailsOrder.show(getChildFragmentManager(), NameFragments.DIALOGDETAILSORDER);
    }
}
