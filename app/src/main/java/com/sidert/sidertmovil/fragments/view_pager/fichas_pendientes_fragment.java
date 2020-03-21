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
import com.sidert.sidertmovil.models.ModeloFichaGeneral;
import com.sidert.sidertmovil.utils.NameFragments;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.sidert.sidertmovil.utils.Constants.ADDRESS;
import static com.sidert.sidertmovil.utils.Constants.ASSIGN_DATE;
import static com.sidert.sidertmovil.utils.Constants.AVAL;
import static com.sidert.sidertmovil.utils.Constants.CALLE;
import static com.sidert.sidertmovil.utils.Constants.CARTERA_VENCIDA_GPO;
import static com.sidert.sidertmovil.utils.Constants.CARTERA_VENCIDA_IND;
import static com.sidert.sidertmovil.utils.Constants.CLAVE_GRUPO;
import static com.sidert.sidertmovil.utils.Constants.CLIENTE;
import static com.sidert.sidertmovil.utils.Constants.COBRANZA_GPO;
import static com.sidert.sidertmovil.utils.Constants.COBRANZA_IND;
import static com.sidert.sidertmovil.utils.Constants.COLONIA;
import static com.sidert.sidertmovil.utils.Constants.DIA_SEMANA;
import static com.sidert.sidertmovil.utils.Constants.DIRECCION;
import static com.sidert.sidertmovil.utils.Constants.FECHA_PAGO_ESTABLECIDA;
import static com.sidert.sidertmovil.utils.Constants.FICHAS_T;
import static com.sidert.sidertmovil.utils.Constants.GRUPAL;
import static com.sidert.sidertmovil.utils.Constants.GRUPO;
import static com.sidert.sidertmovil.utils.Constants.INDIVIDUAL;
import static com.sidert.sidertmovil.utils.Constants.NOMBRE;
import static com.sidert.sidertmovil.utils.Constants.NOMBRE_GRUPO;
import static com.sidert.sidertmovil.utils.Constants.NUMERO_CLIENTE;
import static com.sidert.sidertmovil.utils.Constants.ORDER_ID;
import static com.sidert.sidertmovil.utils.Constants.PRESIDENTA;
import static com.sidert.sidertmovil.utils.Constants.PRESTAMO;
import static com.sidert.sidertmovil.utils.Constants.RECUPERACION_GPO;
import static com.sidert.sidertmovil.utils.Constants.RECUPERACION_IND;
import static com.sidert.sidertmovil.utils.Constants.REPORTE_ANALITICO_OMEGA;
import static com.sidert.sidertmovil.utils.Constants.SECRETARIA;
import static com.sidert.sidertmovil.utils.Constants.TABLA_PAGOS_CLIENTE;
import static com.sidert.sidertmovil.utils.Constants.TABLA_PAGOS_GRUPO;
import static com.sidert.sidertmovil.utils.Constants.TESORERA;
import static com.sidert.sidertmovil.utils.Constants.TYPE;


public class fichas_pendientes_fragment extends Fragment{

    private Context ctx;
    private Home boostrap;
    private RecyclerView rvFichas;
    private adapter_fichas_pendientes adapter;
    private List<ModeloFichaGeneral> _m_fichaGeneral;
    //private ArrayList<ModeloIndividual> _m_individual;
    //private ArrayList<ModeloGrupal> _m_grupal;
    private HashMap<Integer, String> _fichas;

    private JSONObject fichas = null;

    private ModeloFichaGeneral mFichaGeneral;
    private SQLiteDatabase db;
    private DBhelper dBhelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fichas_pendientes, container,false);
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

        Cursor row = dBhelper.getRecords(FICHAS_T, "", "", null);

        if (row.getCount() == 0){
            GuardarFichas();
        }
        else {
            GetFichas();
        }

        adapter = new adapter_fichas_pendientes(ctx, _m_fichaGeneral, new adapter_fichas_pendientes.Event() {
            @Override
            public void FichaOnClick(ModeloFichaGeneral item) {
                Cursor row = dBhelper.getRecords(FICHAS_T, " WHERE external_id = '"+item.getTipoFicha()+"'", "", null);
                row.moveToFirst();
                switch (item.getTipoFormulario()) {
                    case RECUPERACION_IND:
                    case COBRANZA_IND:
                    case CARTERA_VENCIDA_IND:
                        ShowOrderSelected(row.getString(2),row.getString(3));
                        break;
                    case RECUPERACION_GPO:
                    case COBRANZA_GPO:
                    case CARTERA_VENCIDA_GPO:
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
                db.update(FICHAS_T, cv, "external_id = ?", new String[]{item.getTipoFicha()});
            }
        });
        rvFichas.setAdapter(adapter);
    }

    //public void ShowOrderSelected (ModeloIndividual ind, ModeloGrupal gpo){
    public void ShowOrderSelected (String id, String formName){
        dialog_details_order detailsOrder = new dialog_details_order();
        Bundle b = new Bundle();
        b.putString(ORDER_ID,id);
        b.putString(TYPE, formName);
        /*if (ind != null)
            b.putSerializable(Constants.INDIVIDUAL,ind);
        else
            b.putSerializable(Constants.GRUPO,gpo);*/
        detailsOrder.setArguments(b);
        detailsOrder.show(getChildFragmentManager(), NameFragments.DIALOGDETAILSORDER);
    }

    private void GuardarFichas (){
        InputStream is = ctx.getResources().openRawResource(R.raw.fichas_ri);
        try {
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String json = new String(buffer, "UTF-8");
            fichas = new JSONObject(json);

            JSONArray ind = fichas.getJSONArray(INDIVIDUAL);
            JSONArray gpo = fichas.getJSONArray(GRUPAL);

            _m_fichaGeneral = new ArrayList<>();

            if (ind.length() > 0){
                //_m_individual = new ArrayList<>();

                for(int i = 0; i < ind.length(); i++){
                    JSONObject item_ri = ind.getJSONObject(i);
                    _fichas = new HashMap<>();
                    _fichas.put(0,"002");
                    _fichas.put(1,item_ri.getString(ORDER_ID));
                    _fichas.put(2, item_ri.getString(TYPE));
                    _fichas.put(3, item_ri.getString(ASSIGN_DATE));
                    _fichas.put(4, item_ri.getJSONObject(PRESTAMO).getString(DIA_SEMANA));
                    _fichas.put(5, item_ri.getJSONObject(PRESTAMO).getString(FECHA_PAGO_ESTABLECIDA));
                    _fichas.put(6, item_ri.getJSONObject(PRESTAMO).toString());
                    _fichas.put(7, item_ri.getJSONObject(CLIENTE).getString(NOMBRE));
                    _fichas.put(8, item_ri.getJSONObject(CLIENTE).getString(NUMERO_CLIENTE));
                    _fichas.put(9, item_ri.getJSONObject(CLIENTE).toString());
                    _fichas.put(10, item_ri.getJSONObject(AVAL).toString());
                    _fichas.put(11, ""); // Datos de presidenta
                    _fichas.put(12, ""); //Datos de Tesorera
                    _fichas.put(13, ""); // Datos de Secretaria
                    _fichas.put(14, item_ri.getJSONArray(REPORTE_ANALITICO_OMEGA).toString());
                    _fichas.put(15, item_ri.getJSONArray(TABLA_PAGOS_CLIENTE).toString());
                    _fichas.put(16, "0"); // Impresion
                    _fichas.put(17, ""); // Fecha Inicio
                    _fichas.put(18, ""); // Fecha Terminado
                    _fichas.put(19, ""); // Fecha Enviado
                    _fichas.put(20, ""); // Fecha Confirmado
                    _fichas.put(21, ""); // Respuesta
                    _fichas.put(22, "0"); // Estatus
                    _fichas.put(23, "");  //Timestamp

                    dBhelper.saveRecordsFichas(db, FICHAS_T, _fichas);
                }

            }

            if (gpo.length() > 0){
                //_m_grupal = new ArrayList<>();
                for (int i = 0; i < gpo.length(); i++) {
                    JSONObject item_rg = gpo.getJSONObject(i);
                    Log.v("--","-------------------------------------------------------------");
                    Log.v("GRUPO", item_rg.getJSONObject(GRUPO).toString());
                    Log.v("--","-------------------------------------------------------------");
                    _fichas = new HashMap<>();
                    _fichas.put(0,"002");
                    _fichas.put(1,item_rg.getString(ORDER_ID));
                    _fichas.put(2, item_rg.getString(TYPE));
                    _fichas.put(3, item_rg.getString(ASSIGN_DATE));
                    _fichas.put(4, item_rg.getJSONObject(PRESTAMO).getString(DIA_SEMANA));
                    _fichas.put(5, item_rg.getJSONObject(PRESTAMO).getString(FECHA_PAGO_ESTABLECIDA));
                    _fichas.put(6, item_rg.getJSONObject(PRESTAMO).toString());
                    _fichas.put(7, item_rg.getJSONObject(GRUPO).getString(NOMBRE_GRUPO));
                    _fichas.put(8, item_rg.getJSONObject(GRUPO).getString(CLAVE_GRUPO));
                    _fichas.put(9, item_rg.getJSONObject(GRUPO).toString());
                    _fichas.put(10, ""); // Datos de aval
                    _fichas.put(11, item_rg.getJSONObject(PRESIDENTA).toString()); // Datos de presidenta
                    _fichas.put(12, item_rg.getJSONObject(TESORERA).toString()); //Datos de Tesorera
                    _fichas.put(13, item_rg.getJSONObject(SECRETARIA).toString()); // Datos de Secretaria
                    _fichas.put(14, item_rg.getJSONArray(REPORTE_ANALITICO_OMEGA).toString());
                    _fichas.put(15, item_rg.getJSONArray(TABLA_PAGOS_GRUPO).toString());
                    _fichas.put(16, "0"); // Impresion
                    _fichas.put(17, ""); // Fecha inicio
                    _fichas.put(18, ""); // Fecha Terminado
                    _fichas.put(19, ""); // Fecha Enviado
                    _fichas.put(20, ""); // Fecha Confirmado
                    _fichas.put(21, ""); // Respuesta
                    _fichas.put(22, "0"); // Estatus
                    _fichas.put(23, "");  // Timestamp
                    dBhelper.saveRecordsFichas(db, FICHAS_T, _fichas);
                }
            }

            GetFichas();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void GetFichas() {
        Cursor row = dBhelper.getRecords(FICHAS_T, " WHERE status IN (0,1)", "", null);
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
}



