package com.sidert.sidertmovil.fragments.view_pager;


import android.content.ContentValues;
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
import com.sidert.sidertmovil.adapters.adapter_fichas_pendientes;
import com.sidert.sidertmovil.database.DBhelper;
import com.sidert.sidertmovil.fragments.dialogs.dialog_details_order;
import com.sidert.sidertmovil.fragments.orders_fragment;
import com.sidert.sidertmovil.models.ModeloFichaGeneral;
import com.sidert.sidertmovil.utils.Constants;
import com.sidert.sidertmovil.utils.NameFragments;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.sidert.sidertmovil.utils.Constants.ADDRESS;
import static com.sidert.sidertmovil.utils.Constants.CALLE;
import static com.sidert.sidertmovil.utils.Constants.COLONIA;
import static com.sidert.sidertmovil.utils.Constants.DIRECCION;
import static com.sidert.sidertmovil.utils.Constants.NOMBRE;

public class route_fragment extends Fragment{

    private Context ctx;
    private Home boostrap;
    private RecyclerView rvFichas;
    private adapter_fichas_pendientes adapter;
    private List<ModeloFichaGeneral> _m_fichaGeneral;

    private ModeloFichaGeneral mFichaGeneral;
    private SQLiteDatabase db;
    private DBhelper dBhelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_route, container, false);
        ctx = getContext();
        boostrap = (Home) getActivity();

        dBhelper = new DBhelper(ctx);
        db = dBhelper.getWritableDatabase();

        rvFichas = v.findViewById(R.id.rvFichas);

        rvFichas.setLayoutManager(new LinearLayoutManager(ctx));
        rvFichas.setHasFixedSize(false);
        return v;
    }


    private void GetFichas (){
        Cursor row = dBhelper.getRecords(Constants.FICHAS_T, " WHERE is_ruta = 1 AND status IN (0,1)", "", null);

        _m_fichaGeneral = new ArrayList<>();
        row.moveToFirst();
        for (int i = 0; i < row.getCount(); i++){
            mFichaGeneral = new ModeloFichaGeneral();
            mFichaGeneral.setTipoFormulario(row.getString(3));
            mFichaGeneral.setNombreClienteGpo(row.getString(8));

            mFichaGeneral.setFechaPago(row.getString(6));
            mFichaGeneral.setDiaSemana(row.getString(5));
            mFichaGeneral.setTipoFicha(row.getString(2));

            if(!row.getString(13).isEmpty()) {
                try {
                    JSONObject jsonTesorera = new JSONObject(row.getString(13));
                    mFichaGeneral.setNombreTesorera(jsonTesorera.getString(NOMBRE));
                    mFichaGeneral.setDireccion(jsonTesorera.getJSONObject(DIRECCION).getString(ADDRESS));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            else {
                try {
                    JSONObject jsonCliente = new JSONObject(row.getString(10));
                    mFichaGeneral.setDireccion(jsonCliente.getJSONObject(DIRECCION).getString(CALLE)+", col. "+jsonCliente.getJSONObject(DIRECCION).getString(COLONIA));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                mFichaGeneral.setNombreTesorera("");

            }

            mFichaGeneral.setPosicion(i);
            mFichaGeneral.setStatus(row.getString(23));
            mFichaGeneral.setChecked(row.getInt(25) == 1);
            _m_fichaGeneral.add(mFichaGeneral);
            row.moveToNext();
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

    @Override
    public void onResume() {
        super.onResume();
        GetFichas();

        adapter = new adapter_fichas_pendientes(ctx, _m_fichaGeneral, new adapter_fichas_pendientes.Event() {
            @Override
            public void FichaOnClick(ModeloFichaGeneral item) {
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

            @Override
            public void IsRutaOnClick(ModeloFichaGeneral item, boolean is_ruta) {
                ContentValues cv = new ContentValues();
                int ruta = 0;
                if (is_ruta)
                    ruta = 1;

                cv.put("is_ruta", ruta);
                db.update(Constants.FICHAS_T, cv, "external_id = ?", new String[]{item.getTipoFicha()});
            }
        });
        rvFichas.setAdapter(adapter);
    }
}
