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
import com.sidert.sidertmovil.activities.CarteraVencidaGrupal;
import com.sidert.sidertmovil.adapters.adapter_pagos_gpo;
import com.sidert.sidertmovil.adapters.adapter_reporte_omega_gpo;


public class cvg_pagos_fragment extends Fragment {

    private Context ctx;
    private TextView tvExternalID;
    private RecyclerView rvPagos;
    private adapter_pagos_gpo adapter_pagos;
    private adapter_reporte_omega_gpo adapter_omega;
    private CarteraVencidaGrupal parent;
    private RadioGroup rgHistorialPagos;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cvg_pagos, container, false);
        ctx     = getContext();
        parent  = (CarteraVencidaGrupal) getActivity();
        tvExternalID        = view.findViewById(R.id.tvExternalID);
        rgHistorialPagos    = view.findViewById(R.id.rgHistorialPagos);
        rvPagos             = view.findViewById(R.id.rvPagosCiente);
        rvPagos.setLayoutManager(new LinearLayoutManager(ctx));
        rvPagos.setHasFixedSize(false);

        tvExternalID.setText(parent.ficha_cvg.getId());

        rgHistorialPagos.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                rvPagos.setAdapter(null);
                switch (checkedId){
                    case R.id.rbTablaPagos:
                        adapter_pagos = new adapter_pagos_gpo(ctx, parent.ficha_cvg.getTablaPagosGrupo());
                        rvPagos.setAdapter(adapter_pagos);
                        break;
                    case R.id.rbReporteOmega:
                        adapter_omega = new adapter_reporte_omega_gpo(ctx, parent.ficha_cvg.getReporteAnaliticoOmega());
                        rvPagos.setAdapter(adapter_omega);
                        break;
                }
            }
        });
        return view;
    }

}
