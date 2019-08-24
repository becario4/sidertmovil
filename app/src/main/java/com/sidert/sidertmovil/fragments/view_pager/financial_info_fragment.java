package com.sidert.sidertmovil.fragments.view_pager;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.activities.IndividualRecovery;
import com.sidert.sidertmovil.adapters.adapter_ind_omega_report;


public class financial_info_fragment extends Fragment{

    private Context ctx;
    private RecyclerView rvIndOmegaReport;
    private adapter_ind_omega_report adapter;
    private IndividualRecovery parent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_financial_info, container, false);
        ctx     = getContext();
        parent  = (IndividualRecovery) getActivity();
        rvIndOmegaReport    = view.findViewById(R.id.rvIndOmegaReport);
        rvIndOmegaReport.setLayoutManager(new LinearLayoutManager(ctx));
        rvIndOmegaReport.setHasFixedSize(false);

        adapter = new adapter_ind_omega_report(ctx, parent.ficha_ri.getReporteAnaliticoOmega());
        rvIndOmegaReport.setAdapter(adapter);
        return view;
    }

}
