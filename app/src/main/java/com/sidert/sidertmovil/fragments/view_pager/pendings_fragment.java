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

    private void saveRecords (){
        DBhelper dBhelper = new DBhelper(ctx);
        SQLiteDatabase db = dBhelper.getWritableDatabase();

        HashMap<Integer, String> p = new HashMap<>();
        p.put(0,"1");
        p.put(1,"500");
        p.put(2,"3484181-L007ri");
        p.put(3,"456");
        p.put(4,"O");
        p.put(5,"[]");
        p.put(6,"2019-05-27 08:15:52");
        p.put(7,"");
        p.put(8,"0");

        dBhelper.saveRecordsImpressionsLog(db, "log_impressions_r",p);

        p = new HashMap<>();
        p.put(0,"1");
        p.put(1,"500");
        p.put(2,"3484181-L007ri");
        p.put(3,"456");
        p.put(4,"C");
        p.put(5,"[]");
        p.put(6,"2019-05-27 08:16:52");
        p.put(7,"");
        p.put(8,"0");

        dBhelper.saveRecordsImpressionsLog(db, "log_impressions_r",p);
        //-----------------------------------------------------------------------------------
        p = new HashMap<>();
        p.put(0,"2");
        p.put(1,"500");
        p.put(2,"348418241-L007ri");
        p.put(3,"556");
        p.put(4,"O");
        p.put(5,"[]");
        p.put(6,"2019-05-27 08:17:52");
        p.put(7,"");
        p.put(8,"0");

        dBhelper.saveRecordsImpressionsLog(db, "log_impressions_r",p);

        p = new HashMap<>();
        p.put(0,"2");
        p.put(1,"500");
        p.put(2,"348418241-L007ri");
        p.put(3,"556");
        p.put(4,"C");
        p.put(5,"[]");
        p.put(6,"2019-05-27 08:18:52");
        p.put(7,"");
        p.put(8,"0");

        dBhelper.saveRecordsImpressionsLog(db, "log_impressions_r",p);
        //-----------------------------------------------------------------------------------------
        p = new HashMap<>();
        p.put(0,"3");
        p.put(1,"500");
        p.put(2,"34845618241-L007ri");
        p.put(3,"656");
        p.put(4,"O");
        p.put(5,"[]");
        p.put(6,"2019-05-27 08:19:52");
        p.put(7,"");
        p.put(8,"0");

        dBhelper.saveRecordsImpressionsLog(db, "log_impressions_r",p);

        p = new HashMap<>();
        p.put(0,"3");
        p.put(1,"500");
        p.put(2,"34845618241-L007ri");
        p.put(3,"656");
        p.put(4,"C");
        p.put(5,"[]");
        p.put(6,"2019-05-27 08:20:52");
        p.put(7,"");
        p.put(8,"0");

        dBhelper.saveRecordsImpressionsLog(db, "log_impressions_r",p);

        //-----------------------------------------------------------------------------------------
        p = new HashMap<>();
        p.put(0,"4");
        p.put(1,"500");
        p.put(2,"3484565418241-L007ri");
        p.put(3,"100");
        p.put(4,"O");
        p.put(5,"[]");
        p.put(6,"2019-05-27 08:21:52");
        p.put(7,"");
        p.put(8,"0");

        dBhelper.saveRecordsImpressionsLog(db, "log_impressions_r",p);

        p = new HashMap<>();
        p.put(0,"4");
        p.put(1,"500");
        p.put(2,"3484565418241-L007ri");
        p.put(3,"100");
        p.put(4,"C");
        p.put(5,"[]");
        p.put(6,"2019-05-27 08:22:52");
        p.put(7,"");
        p.put(8,"0");

        dBhelper.saveRecordsImpressionsLog(db, "log_impressions_r",p);

        //-----------------------------------------------------------------------------------------
        p = new HashMap<>();
        p.put(0,"5");
        p.put(1,"500");
        p.put(2,"348456554418241-L007ri");
        p.put(3,"100");
        p.put(4,"O");
        p.put(5,"[]");
        p.put(6,"2019-05-27 08:23:52");
        p.put(7,"");
        p.put(8,"0");

        dBhelper.saveRecordsImpressionsLog(db, "log_impressions_r",p);

        p = new HashMap<>();
        p.put(0,"5");
        p.put(1,"500");
        p.put(2,"348456554418241-L007ri");
        p.put(3,"100");
        p.put(4,"C");
        p.put(5,"[]");
        p.put(6,"2019-05-27 08:24:52");
        p.put(7,"");
        p.put(8,"0");

        dBhelper.saveRecordsImpressionsLog(db, "log_impressions_r",p);

        //------------------------------------------------------------------------------------------
        p = new HashMap<>();
        p.put(0,"6");
        p.put(1,"500");
        p.put(2,"34845654554418241-L007ri");
        p.put(3,"200");
        p.put(4,"O");
        p.put(5,"[]");
        p.put(6,"2019-05-27 08:25:52");
        p.put(7,"");
        p.put(8,"0");

        dBhelper.saveRecordsImpressionsLog(db, "log_impressions_r",p);

        p = new HashMap<>();
        p.put(0,"6");
        p.put(1,"500");
        p.put(2,"34845654554418241-L007ri");
        p.put(3,"200");
        p.put(4,"C");
        p.put(5,"[]");
        p.put(6,"2019-05-27 08:26:52");
        p.put(7,"");
        p.put(8,"0");

        dBhelper.saveRecordsImpressionsLog(db, "log_impressions_r",p);
    }
}
