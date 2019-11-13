package com.sidert.sidertmovil.fragments.view_pager;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.sidert.sidertmovil.Home;
import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.adapters.adapter_ficha_contestadas;
import com.sidert.sidertmovil.adapters.adapter_fichas_pendientes;
import com.sidert.sidertmovil.database.DBhelper;
import com.sidert.sidertmovil.fragments.dialogs.dialog_details_order;
import com.sidert.sidertmovil.fragments.orders_fragment;
import com.sidert.sidertmovil.models.ModelFichaContestada;
import com.sidert.sidertmovil.models.ModeloFichaGeneral;
import com.sidert.sidertmovil.utils.Constants;
import com.sidert.sidertmovil.utils.NameFragments;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class answers_fragment extends Fragment{

    private Context ctx;
    private Home boostrap;
    private RecyclerView rvFichas;
    private adapter_ficha_contestadas adapter;
    private List<ModelFichaContestada> _m_fichaContestada;

    private ModelFichaContestada mFichaContestada;
    private SQLiteDatabase db;
    private DBhelper dBhelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_answers, container, false);
        ctx = getContext();
        boostrap = (Home) getActivity();

        dBhelper = new DBhelper(ctx);
        db = dBhelper.getWritableDatabase();

        rvFichas = view.findViewById(R.id.rvFichas);

        rvFichas.setLayoutManager(new LinearLayoutManager(ctx));
        rvFichas.setHasFixedSize(false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Cursor row = dBhelper.getRecords(Constants.FICHAS_T, " WHERE status not in (0,1)", "", null);

        if (row.getCount() > 0){
            _m_fichaContestada = new ArrayList<>();
            row.moveToFirst();
            for (int i = 0; i < row.getCount(); i++){
                Log.v("Row",row.getString(4)+" "+
                        row.getString(8)+" "+
                        "Alguna direccion"+" "+
                        row.getString(6)+" "+
                        row.getString(5)+" "+
                        row.getString(2)+" "+
                        row.getString(18)+" "+
                        row.getString(19)+" "+
                        row.getString(20)+" "+
                        row.getString(21)+" "+
                        row.getString(23)+" "+
                        i+ " "+false);
                mFichaContestada = new ModelFichaContestada();
                mFichaContestada.setTipoFormulario(row.getString(3));
                mFichaContestada.setNombreClienteGpo(row.getString(8));
                mFichaContestada.setFechaIni(row.getString(18));
                mFichaContestada.setFechaTer(row.getString(19));
                mFichaContestada.setFechaEnv(row.getString(20));
                mFichaContestada.setFechaFin(row.getString(21));
                mFichaContestada.setTipoFicha(row.getString(2));
                mFichaContestada.setPosicion(i);

                _m_fichaContestada.add(mFichaContestada);
                row.moveToNext();
            }

            adapter = new adapter_ficha_contestadas(ctx, _m_fichaContestada, new adapter_ficha_contestadas.Event() {
                @Override
                public void FichaOnClick(ModelFichaContestada item) {
                    Cursor row = dBhelper.getRecords(Constants.FICHAS_T, " WHERE external_id = '"+item.getTipoFicha()+"'", "", null);
                    row.moveToFirst();
                    switch (item.getTipoFormulario()) {
                        case Constants.RECUPERACION_IND:
                        case Constants.COBRANZA_IND:
                        case Constants.CARTERA_VENCIDA_IND:
                            ShowOrderSelected(row.getString(2),row.getString(3));
                            break;
                        case Constants.RECUPERACION_GPO:
                        case Constants.COBRANZA_GPO:
                        case Constants.CARTERA_VENCIDA_GPO:
                            ShowOrderSelected(row.getString(2),row.getString(3));
                            //ShowOrderSelected(null, _m_grupal.get(item.getPosicion()));
                            break;
                        default:
                            Toast.makeText(ctx, "No se encuentra registrado este formulario", Toast.LENGTH_SHORT).show();
                            break;
                    }
                }
            });
            rvFichas.setAdapter(adapter);
        }
    }

    public void ShowOrderSelected (String id, String formName){
        dialog_details_order detailsOrder = new dialog_details_order();
        Bundle b = new Bundle();
        b.putString(Constants.ORDER_ID,id);
        b.putString(Constants.TYPE, formName);
        /*if (ind != null)
            b.putSerializable(Constants.INDIVIDUAL,ind);
        else
            b.putSerializable(Constants.GRUPO,gpo);*/
        detailsOrder.setArguments(b);
        detailsOrder.show(getChildFragmentManager(), NameFragments.DIALOGDETAILSORDER);
    }
}
