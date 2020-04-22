package com.sidert.sidertmovil.fragments.view_pager;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.activities.CarteraVencidaIndividual;
import com.sidert.sidertmovil.adapters.adapter_pagos_ind;
import com.sidert.sidertmovil.adapters.adapter_reporte_omega_ind;


public class cvi_pagos_fragment extends Fragment {

    private Context ctx;
    private TextView tvExternalID;
    private RecyclerView rvPagos;
    private adapter_pagos_ind adapter_pagos;
    private adapter_reporte_omega_ind adapter_omega;
    private CarteraVencidaIndividual parent;
    private RadioGroup rgHistorialPagos;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cvi_pagos, container, false);
        ctx     = getContext();
        parent  = (CarteraVencidaIndividual) getActivity();
        tvExternalID        = view.findViewById(R.id.tvExternalID);
        rgHistorialPagos    = view.findViewById(R.id.rgHistorialPagos);
        rvPagos             = view.findViewById(R.id.rvPagosCiente);
        rvPagos.setLayoutManager(new LinearLayoutManager(ctx));
        rvPagos.setHasFixedSize(false);

        tvExternalID.setText(parent.ficha_cvi.getId());

        rgHistorialPagos.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                rvPagos.setAdapter(null);
                switch (checkedId){
                    case R.id.rbTablaPagos:
                        //adapter_pagos = new adapter_pagos_ind(ctx, parent.ficha_cvi.getTablaPagosCliente());
                        //rvPagos.setAdapter(adapter_pagos);
                        break;
                    case R.id.rbReporteOmega:
                        //adapter_omega = new adapter_reporte_omega_ind(ctx, parent.ficha_cvi.getReporteAnaliticoOmega());
                        //rvPagos.setAdapter(adapter_omega);
                        break;
                }
            }
        });
        return view;
    }

}
