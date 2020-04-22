package com.sidert.sidertmovil.fragments.view_pager;


import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
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
import com.sidert.sidertmovil.activities.PrestamosClientes;
import com.sidert.sidertmovil.adapters.adapter_fichas_pendientes;
import com.sidert.sidertmovil.database.DBhelper;
import com.sidert.sidertmovil.fragments.dialogs.dialog_details_order;
import com.sidert.sidertmovil.fragments.orders_fragment;
import com.sidert.sidertmovil.models.MCarteraGnral;
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
import static com.sidert.sidertmovil.utils.Constants.ENVIROMENT;
import static com.sidert.sidertmovil.utils.Constants.ID_CARTERA;
import static com.sidert.sidertmovil.utils.Constants.NOMBRE;
import static com.sidert.sidertmovil.utils.Constants.TBL_CARTERA_GPO;
import static com.sidert.sidertmovil.utils.Constants.TBL_CARTERA_GPO_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_CARTERA_IND;
import static com.sidert.sidertmovil.utils.Constants.TBL_CARTERA_IND_T;
import static com.sidert.sidertmovil.utils.Constants.TIPO;

public class route_fragment extends Fragment{

    private Context ctx;
    private Home boostrap;
    private RecyclerView rvFichas;
    private adapter_fichas_pendientes adapter;
    private List<ModeloFichaGeneral> _m_fichaGeneral;
    private List<MCarteraGnral> _m_carteraGral;

    private ModeloFichaGeneral mFichaGeneral;
    private MCarteraGnral mCarteraGnral;
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


    private boolean GetCartera(){
        Cursor row;
        _m_carteraGral = new ArrayList<>();
        String query;
        if (ENVIROMENT)
            query = "SELECT * FROM (SELECT id_cartera,nombre,direccion,is_ruta,ruta_obligado,dia,'' AS tesorera,asesor_nombre,'INDIVIDUAL' AS tipo,colonia FROM " + TBL_CARTERA_IND + " WHERE is_ruta = 1 UNION SELECT id_cartera,nombre, direccion,is_ruta, ruta_obligado,dia,tesorera,asesor_nombre,'GRUPAL' AS tipo, colonia FROM "+TBL_CARTERA_GPO +" WHERE is_ruta = 1) AS cartera ";
        else
            query = "SELECT * FROM (SELECT id_cartera,nombre,direccion,is_ruta,ruta_obligado,dia,'' AS tesorera,asesor_nombre,'INDIVIDUAL' AS tipo,colonia FROM " + TBL_CARTERA_IND_T + " WHERE is_ruta = 1 UNION SELECT id_cartera,nombre, direccion,is_ruta, ruta_obligado,dia,tesorera,asesor_nombre,'GRUPAL' AS tipo, colonia FROM "+TBL_CARTERA_GPO_T +" WHERE is_ruta = 1) AS cartera ";

        row = db.rawQuery(query,null);

        if (row.getCount() > 0){
            row.moveToFirst();
            for (int i = 0; i < row.getCount(); i++){
                mCarteraGnral = new MCarteraGnral();
                mCarteraGnral.setId_cliente(row.getString(0));
                mCarteraGnral.setNombre(row.getString(1));
                mCarteraGnral.setDireccion(row.getString(2));
                mCarteraGnral.setIs_ruta(row.getInt(3)==1);
                mCarteraGnral.setIs_obligatorio(row.getInt(4)==1);
                mCarteraGnral.setDiaSemana(row.getString(5));
                mCarteraGnral.setTesorera(row.getString(6));
                mCarteraGnral.setTipo(row.getString(8));

                row.moveToNext();
                _m_carteraGral.add(mCarteraGnral);
            }
        }
        row.close();

        return true;
    }

    @Override
    public void onResume() {
        super.onResume();
        GetCartera();

        adapter = new adapter_fichas_pendientes(ctx, _m_carteraGral, new adapter_fichas_pendientes.Event() {
            @Override
            public void FichaOnClick(MCarteraGnral item) {
                Intent i_prestamos = new Intent(boostrap, PrestamosClientes.class);
                i_prestamos.putExtra(ID_CARTERA, item.getId_cliente());
                i_prestamos.putExtra(TIPO, item.getTipo());
                startActivity(i_prestamos);
            }

            @Override
            public void IsRutaOnClick(MCarteraGnral item, boolean is_ruta) {
                ContentValues cv = new ContentValues();
                String tbl = "";
                int ruta = 0;
                if (is_ruta)
                    ruta = 1;

                cv.put("is_ruta", ruta);
                switch (item.getTipo()){
                    case "INDIVIDUAL":
                        if (ENVIROMENT)
                            tbl = TBL_CARTERA_IND;
                        else
                            tbl = TBL_CARTERA_IND_T;
                        break;
                    case "GRUPAL":
                        if (ENVIROMENT)
                            tbl = TBL_CARTERA_GPO;
                        else
                            tbl = TBL_CARTERA_GPO_T;
                        break;
                }
                db.update(tbl, cv, "id_cartera = ?", new String[]{item.getId_cliente()});
            }
        });
        rvFichas.setAdapter(adapter);
    }
}
