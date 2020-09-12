package com.sidert.sidertmovil.fragments.view_pager;


import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.activities.RecuperacionGrupal;
import com.sidert.sidertmovil.adapters.adapter_pagos_gpo;
import com.sidert.sidertmovil.adapters.adapter_reporte_omega_gpo;
import com.sidert.sidertmovil.database.DBhelper;
import com.sidert.sidertmovil.models.MAmortizacion;
import com.sidert.sidertmovil.models.MPago;

import java.util.ArrayList;

import static com.sidert.sidertmovil.utils.Constants.ENVIROMENT;
import static com.sidert.sidertmovil.utils.Constants.TBL_AMORTIZACIONES;
import static com.sidert.sidertmovil.utils.Constants.TBL_AMORTIZACIONES_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_PAGOS;
import static com.sidert.sidertmovil.utils.Constants.TBL_PAGOS_T;

public class rg_pagos_fragment extends Fragment {

    private Context ctx;
    private RecyclerView rvPagos;
    private adapter_pagos_gpo adapter_pagos;
    private adapter_reporte_omega_gpo adapter_omega;
    private RecuperacionGrupal parent;
    private RadioGroup rgHistorialPagos;

    private DBhelper dBhelper;

    private String id_prestamo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rg_pagos, container, false);
        ctx     = getContext();
        dBhelper = new DBhelper(ctx);
        parent  = (RecuperacionGrupal) getActivity();
        rgHistorialPagos    = view.findViewById(R.id.rgHistorialPagos);
        rvPagos             = view.findViewById(R.id.rvPagos);
        rvPagos.setLayoutManager(new LinearLayoutManager(ctx));
        rvPagos.setHasFixedSize(false);

        id_prestamo = parent.id_prestamo;

        rgHistorialPagos.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                rvPagos.setAdapter(null);
                switch (checkedId){
                    case R.id.rbTablaPagos:
                        GetPagos();
                        break;
                    case R.id.rbReporteOmega:
                        GetAmortiz();
                        break;
                }
            }
        });
        return view;
    }

    private void GetPagos(){
        Cursor row = dBhelper.getRecords(TBL_PAGOS_T, " WHERE id_prestamo = ?", " ORDER BY fecha ASC", new String[]{id_prestamo});

        ArrayList<MPago> _pagos = new ArrayList<>();
        if (row.getCount() > 0){
            row.moveToFirst();

            for (int i = 0; i < row.getCount(); i++){
                MPago mPago = new MPago();
                mPago.setPrestamoId(row.getInt(1));
                mPago.setFecha(row.getString(2));
                mPago.setMonto(row.getDouble(3));
                mPago.setBanco(row.getString(4));
                _pagos.add(mPago);
                row.moveToNext();
            }
        }

        adapter_pagos = new adapter_pagos_gpo(ctx, _pagos);
        rvPagos.setAdapter(adapter_pagos);
    }

    private void GetAmortiz(){
        Cursor row = dBhelper.getRecords(TBL_AMORTIZACIONES_T, " WHERE id_prestamo = ?", " ORDER BY numero ASC", new String[]{id_prestamo});

        ArrayList<MAmortizacion> _amortiz = new ArrayList<>();
        if (row.getCount() > 0){
            row.moveToFirst();

            for (int i = 0; i < row.getCount(); i++){
                MAmortizacion mAmortizacion = new MAmortizacion();
                mAmortizacion.setId(row.getInt(1));
                mAmortizacion.setPrestamoId(row.getInt(2));
                mAmortizacion.setFecha(row.getString(3));
                mAmortizacion.setFechaPago(row.getString(4));
                mAmortizacion.setCapital(row.getDouble(5));
                mAmortizacion.setInteres(row.getDouble(6));
                mAmortizacion.setIva(row.getDouble(7));
                mAmortizacion.setComision(row.getDouble(8));
                mAmortizacion.setTotal(row.getDouble(9));
                mAmortizacion.setCapitalPagado(row.getDouble(10));
                mAmortizacion.setInteresPagado(row.getDouble(11));
                mAmortizacion.setIvaPagado(row.getDouble(12));
                mAmortizacion.setInteresMoratorioPagado(row.getDouble(13));
                mAmortizacion.setIvaMoratorioPagado(row.getDouble(14));
                mAmortizacion.setComisionPagada(row.getDouble(15));
                mAmortizacion.setTotalPagado(row.getDouble(16));
                Log.e("TotalPagado", row.getString(16));
                mAmortizacion.setPagado(row.getString(17));
                mAmortizacion.setNumero(row.getInt(18));
                mAmortizacion.setDiasAtraso(row.getString(19));
                _amortiz.add(mAmortizacion);
                row.moveToNext();
            }
        }

        adapter_omega = new adapter_reporte_omega_gpo(ctx, _amortiz);
        rvPagos.setAdapter(adapter_omega);
    }

}
