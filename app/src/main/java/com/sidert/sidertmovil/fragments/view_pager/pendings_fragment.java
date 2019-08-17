package com.sidert.sidertmovil.fragments.view_pager;


import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.sidert.sidertmovil.Home;
import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.activities.IndividualRecovery;
import com.sidert.sidertmovil.adapters.adapter_fichas_pendientes;
import com.sidert.sidertmovil.database.DBhelper;
import com.sidert.sidertmovil.fragments.dialogs.dialog_details_order;
import com.sidert.sidertmovil.fragments.orders_fragment;
import com.sidert.sidertmovil.models.ModeloIndividual;
import com.sidert.sidertmovil.utils.Constants;
import com.sidert.sidertmovil.utils.NameFragments;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class pendings_fragment extends Fragment{

    private Context ctx;
    private Home boostrap;
    private RecyclerView rvFichas;
    private adapter_fichas_pendientes adapter;

    //private LinearLayout llSingle;
    //private LinearLayout llGpo;
    //private LinearLayout llCvg;
    // LinearLayout llCvi;

    private JSONArray fichas = null;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pendings, container,false);
        ctx = getContext();
        boostrap = (Home) getActivity();

        //llSingle    = view.findViewById(R.id.llSingle);
        //llGpo       = view.findViewById(R.id.llgpo);
        //llCvg       = view.findViewById(R.id.llCvg);
        //llCvi       = view.findViewById(R.id.llCvi);

        rvFichas = view.findViewById(R.id.rvFichas);

        rvFichas.setLayoutManager(new LinearLayoutManager(ctx));
        rvFichas.setHasFixedSize(false);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //saveRecords ();
        InputStream is = ctx.getResources().openRawResource(R.raw.fichas_ri);
        try {
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String json = new String(buffer, "UTF-8");
           fichas = new JSONArray(json);

           if (fichas.length() > 0){
               ArrayList<ModeloIndividual> _m_individual = new ArrayList<>();
               for(int i = 0; i < fichas.length(); i++){
                ModeloIndividual m_individual = new ModeloIndividual();
                JSONObject item_ri = fichas.getJSONObject(i);
                m_individual.setId(item_ri.getString(Constants.ORDER_ID));
                m_individual.setType(item_ri.getString(Constants.TYPE));
                m_individual.setAssignDate(item_ri.getString(Constants.ASSIGN_DATE));
                m_individual.setExpirationDate(item_ri.getString(Constants.EXPIRATION_DATE));
                m_individual.setCancellationDate(item_ri.getString(Constants.CANCELLATION_DATE));

                ModeloIndividual.Cliente cliente = new ModeloIndividual.Cliente();
                cliente.setNumeroCliente(item_ri.getJSONObject(Constants.CLIENTE).getString(Constants.NUMERO_CLIENTE));
                cliente.setNombre(item_ri.getJSONObject(Constants.CLIENTE).getString(Constants.NOMBRE));
                cliente.setTelCelular(item_ri.getJSONObject(Constants.CLIENTE).getString(Constants.TEL_CELULAR));
                cliente.setTelDomicilio(item_ri.getJSONObject(Constants.CLIENTE).getString(Constants.TEL_DOMICILIO));

                ModeloIndividual.Direccion direccion = new ModeloIndividual.Direccion();
                direccion.setCalle(item_ri.getJSONObject(Constants.CLIENTE).getJSONObject(Constants.DIRECCION).getString(Constants.CALLE));
                direccion.setCiudad(item_ri.getJSONObject(Constants.CLIENTE).getJSONObject(Constants.DIRECCION).getString(Constants.CIUDAD));
                direccion.setCodigoPostal(item_ri.getJSONObject(Constants.CLIENTE).getJSONObject(Constants.DIRECCION).getString(Constants.CODIGO_POSTAL));
                direccion.setColonia(item_ri.getJSONObject(Constants.CLIENTE).getJSONObject(Constants.DIRECCION).getString(Constants.COLONIA));
                direccion.setMunicipio(item_ri.getJSONObject(Constants.CLIENTE).getJSONObject(Constants.DIRECCION).getString(Constants.MUNICIPIO));
                direccion.setEstado(item_ri.getJSONObject(Constants.CLIENTE).getJSONObject(Constants.DIRECCION).getString(Constants.ESTADO));
                direccion.setLatitude(item_ri.getJSONObject(Constants.CLIENTE).getJSONObject(Constants.DIRECCION).getString(Constants.LATITUDE));
                direccion.setLongitude(item_ri.getJSONObject(Constants.CLIENTE).getJSONObject(Constants.DIRECCION).getString(Constants.LONGITUD));

                cliente.setDireccion(direccion);
                m_individual.setCliente(cliente);

                ModeloIndividual.Aval aval = new ModeloIndividual.Aval();
                aval.setNombreCompletoAval(item_ri.getJSONObject(Constants.AVAL).getString(Constants.NOMBRE_COMPLETO_AVAL));
                aval.setTelefonoAval(item_ri.getJSONObject(Constants.AVAL).getString(Constants.TELEFONO_AVAL));
                aval.setAddressAval(item_ri.getJSONObject(Constants.AVAL).getString(Constants.ADDRESS_AVAL));
                aval.setParentescoAval(item_ri.getJSONObject(Constants.AVAL).getString(Constants.PARENTESCO_AVAL));

                m_individual.setAval(aval);

                ModeloIndividual.Prestamo prestamo = new ModeloIndividual.Prestamo();
                prestamo.setNumeroDePrestamo(item_ri.getJSONObject(Constants.PRESTAMO).getString(Constants.NUMERO_DE_PRESTAMO));
                prestamo.setFechaDelCreditoOtorgado(item_ri.getJSONObject(Constants.PRESTAMO).getString(Constants.FECHA_CREDITO_OTORGADO));
                prestamo.setMontoTotalPrestamo(item_ri.getJSONObject(Constants.PRESTAMO).getDouble(Constants.MONTO_TOTAL_PRESTAMO));
                prestamo.setMontoPrestamo(item_ri.getJSONObject(Constants.PRESTAMO).getDouble(Constants.MONTO_PRESTAMO));
                prestamo.setPagoSemanal(item_ri.getJSONObject(Constants.PRESTAMO).getDouble(Constants.PAGO_SEMANAL));
                prestamo.setPagoRealizado(item_ri.getJSONObject(Constants.PRESTAMO).getDouble(Constants.PAGO_REALIZADO));
                prestamo.setNumeroAmortizacion(item_ri.getJSONObject(Constants.PRESTAMO).getInt(Constants.NUMERO_AMORTIZACION));
                prestamo.setMontoAmortizacion(item_ri.getJSONObject(Constants.PRESTAMO).getDouble(Constants.MONTO_AMORTIZACION));
                prestamo.setFechaPagoEstablecida(item_ri.getJSONObject(Constants.PRESTAMO).getString(Constants.FECHA_PAGO_ESTABLECIDA));
                prestamo.setHoraPagoEstablecida(item_ri.getJSONObject(Constants.PRESTAMO).getString(Constants.HORA_PAGO_ESTABLECIDA));
                prestamo.setSaldoActual(item_ri.getJSONObject(Constants.PRESTAMO).getDouble(Constants.SALDO_ACTUAL));
                prestamo.setSumaDePagos(item_ri.getJSONObject(Constants.PRESTAMO).getDouble(Constants.SUMA_DE_PAGOS));
                prestamo.setCuentaConGarantia(item_ri.getJSONObject(Constants.PRESTAMO).getString(Constants.CUENTA_CON_GARANTIA));
                prestamo.setTipoGarantia(item_ri.getJSONObject(Constants.PRESTAMO).getString(Constants.TIPO_GARANTIA));
                prestamo.setDiasAtraso(item_ri.getJSONObject(Constants.PRESTAMO).getInt(Constants.DIAS_ATRASO));
                prestamo.setFrecuencia(item_ri.getJSONObject(Constants.PRESTAMO).getString(Constants.FRECUENCIA));
                prestamo.setDiaSemana(item_ri.getJSONObject(Constants.PRESTAMO).getString(Constants.DIA_SEMANA));
                prestamo.setPerteneceGarantia(item_ri.getJSONObject(Constants.PRESTAMO).getString(Constants.PERTENECE_GARANTIA));

                m_individual.setPrestamo(prestamo);

                List<ModeloIndividual.ReporteAnaliticoOmega> _reporteOmega = new ArrayList<>();
                for (int j = 0; j < item_ri.getJSONArray(Constants.REPORTE_ANALITICO_OMEGA).length(); j++){
                    JSONObject item_ri_omega = item_ri.getJSONArray(Constants.REPORTE_ANALITICO_OMEGA).getJSONObject(j);
                    ModeloIndividual.ReporteAnaliticoOmega reporteOmega = new ModeloIndividual.ReporteAnaliticoOmega();
                    reporteOmega.setNo(item_ri_omega.getInt(Constants.NO));
                    reporteOmega.setFechaAmortizacion(item_ri_omega.getString(Constants.FECHA_AMORTIZACION));
                    reporteOmega.setFechaPago(item_ri_omega.getString(Constants.FECHA_PAGO));
                    reporteOmega.setEstatus(item_ri_omega.getString(Constants.ESTATUS));
                    reporteOmega.setDias(item_ri_omega.getInt(Constants.DIAS));
                    _reporteOmega.add(reporteOmega);
                }

                m_individual.setReporteAnaliticoOmega(_reporteOmega);

                List<ModeloIndividual.TablaPagosCliente> _pagosCliente = new ArrayList<>();
                for (int k = 0; k < item_ri.getJSONArray(Constants.TABLA_PAGOS_CLIENTE).length(); k++){
                    JSONObject item_ri_pagos = item_ri.getJSONArray(Constants.TABLA_PAGOS_CLIENTE).getJSONObject(k);
                    ModeloIndividual.TablaPagosCliente pagosCliente = new ModeloIndividual.TablaPagosCliente();
                    pagosCliente.setFecha(item_ri_pagos.getString(Constants.FECHA));
                    pagosCliente.setPago(item_ri_pagos.getDouble(Constants.PAGO));
                    pagosCliente.setBanco(item_ri_pagos.getString(Constants.BANCO));
                    _pagosCliente.add(pagosCliente);
                }

                m_individual.setTablaPagosCliente(_pagosCliente);

                _m_individual.add(m_individual);

               }

               adapter = new adapter_fichas_pendientes(ctx, _m_individual, new adapter_fichas_pendientes.Event() {
                   @Override
                   public void FichaOnClick(ModeloIndividual item) {
                       ShowOrderSelected(item);
                   }
               });

               rvFichas.setAdapter(adapter);

           }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //llSingle.setOnClickListener(LLsingle_onClick);
        //llGpo.setOnClickListener(LLgpo_onClick);
        //llCvg.setOnClickListener(LLcvg_OnClick);
        //llCvi.setOnClickListener(LLcvi_OnClick);
    }

    /*private View.OnClickListener LLsingle_onClick = new View.OnClickListener() {
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
    };*/

    public void ShowOrderSelected (ModeloIndividual ficha){
        dialog_details_order detailsOrder = new dialog_details_order();
        Bundle b = new Bundle();
        b.putSerializable(Constants.FICHA,ficha);
        detailsOrder.setArguments(b);
        detailsOrder.show(getChildFragmentManager(), NameFragments.DIALOGDETAILSORDER);
    }
}
