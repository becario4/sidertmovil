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
import com.sidert.sidertmovil.adapters.adapter_pagos_clientes;

public class payment_log_fragment extends Fragment{

    private Context ctx;
    private RecyclerView rvPagosCliente;
    private adapter_pagos_clientes adapter;
    private IndividualRecovery parent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_payment_log, container, false);
        ctx     = getContext();
        parent  = (IndividualRecovery) getActivity();
        rvPagosCliente    = view.findViewById(R.id.rvPagosCiente);
        rvPagosCliente.setLayoutManager(new LinearLayoutManager(ctx));
        rvPagosCliente.setHasFixedSize(false);

        adapter = new adapter_pagos_clientes(ctx, parent.ficha.getTablaPagosCliente());
        rvPagosCliente.setAdapter(adapter);
        return view;
    }

}
