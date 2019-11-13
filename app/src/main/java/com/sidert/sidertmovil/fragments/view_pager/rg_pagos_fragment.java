package com.sidert.sidertmovil.fragments.view_pager;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.activities.RecuperacionGrupal;
import com.sidert.sidertmovil.adapters.adapter_pagos_gpo;
import com.sidert.sidertmovil.adapters.adapter_reporte_omega_gpo;

import org.w3c.dom.Text;

public class rg_pagos_fragment extends Fragment {

    private Context ctx;
    private TextView tvExternalID;
    private RecyclerView rvPagos;
    private adapter_pagos_gpo adapter_pagos;
    private adapter_reporte_omega_gpo adapter_omega;
    private RecuperacionGrupal parent;
    private RadioGroup rgHistorialPagos;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rg_pagos, container, false);
        ctx     = getContext();
        parent  = (RecuperacionGrupal) getActivity();
        tvExternalID        = view.findViewById(R.id.tvExternalID);
        rgHistorialPagos    = view.findViewById(R.id.rgHistorialPagos);
        rvPagos             = view.findViewById(R.id.rvPagos);
        rvPagos.setLayoutManager(new LinearLayoutManager(ctx));
        rvPagos.setHasFixedSize(false);
        tvExternalID.setText(parent.ficha_rg.getId());
        rgHistorialPagos.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                rvPagos.setAdapter(null);
                switch (checkedId){
                    case R.id.rbTablaPagos:
                        adapter_pagos = new adapter_pagos_gpo(ctx, parent.ficha_rg.getTablaPagosGrupo());
                        rvPagos.setAdapter(adapter_pagos);
                        break;
                    case R.id.rbReporteOmega:
                        adapter_omega = new adapter_reporte_omega_gpo(ctx, parent.ficha_rg.getReporteAnaliticoOmega());
                        rvPagos.setAdapter(adapter_omega);
                        break;
                }
            }
        });
        return view;
    }

}
