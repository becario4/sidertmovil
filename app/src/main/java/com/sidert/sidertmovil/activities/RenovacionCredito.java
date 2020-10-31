package com.sidert.sidertmovil.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.LinearLayout;

import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.adapters.adapter_renovacion;
import com.sidert.sidertmovil.database.DBhelper;
import com.sidert.sidertmovil.utils.Constants;

import java.util.ArrayList;
import java.util.HashMap;

import static com.sidert.sidertmovil.utils.Constants.TBL_CREDITO_GPO_REN;
import static com.sidert.sidertmovil.utils.Constants.TBL_SOLICITUDES_REN;

public class RenovacionCredito extends AppCompatActivity {

    private Context ctx;

    private adapter_renovacion adapter;
    private RecyclerView rvRenovacion;

    private DBhelper dBhelper;
    private SQLiteDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_renovacion_credito);

        ctx = this;

        dBhelper = new DBhelper(ctx);
        db = dBhelper.getWritableDatabase();

        rvRenovacion = findViewById(R.id.rvRenovacion);

        rvRenovacion.setLayoutManager(new LinearLayoutManager(ctx));
        rvRenovacion.setHasFixedSize(false);

        initComponents();
    }

    private void initComponents(){
        Cursor row = dBhelper.getRecords(TBL_SOLICITUDES_REN, "", "", null);

        if (row.getCount() > 0){
            row.moveToFirst();
            ArrayList<HashMap<Integer,String>> data = new ArrayList<>();
            for(int i = 0; i < row.getCount(); i++){
                HashMap<Integer, String> item = new HashMap();
                Log.e("id_solicitud", row.getString(0) + " nombre: "+row.getString(5));
                item.put(0,row.getString(0));           //ID solicitud
                item.put(1, row.getString(5).trim().toUpperCase()); //Nombre
                item.put(2, row.getString(3));          //Tipo solicitud
                item.put(3, row.getString(11));         //Estatus
                item.put(4, row.getString(7));          //Fecha Termino
                item.put(5, row.getString(10));         //Fecha Envio
                data.add(item);
                row.moveToNext();
            }

            adapter = new adapter_renovacion(ctx, data, new adapter_renovacion.Event() {
                @Override
                public void FichaOnClick(HashMap<Integer, String> item) {
                    Intent i_renovacion;
                    ProgressDialog dialog = ProgressDialog.show(RenovacionCredito.this, "",
                            "Cargando la información por favor espere...", true);
                    dialog.setCancelable(false);
                    dialog.show();
                    switch (Integer.parseInt(item.get(2))){
                        case 1:
                            i_renovacion = new Intent(ctx, RenovacionCreditoInd.class);
                            i_renovacion.putExtra("is_new", false);
                            i_renovacion.putExtra("id_solicitud", item.get(0));
                            i_renovacion.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(i_renovacion);
                            dialog.dismiss();
                            break;
                        case 2:
                            Cursor row = dBhelper.getRecords(TBL_CREDITO_GPO_REN, " WHERE id_solicitud = ?", "", new String[]{item.get(0)});
                            row.moveToFirst();
                            boolean completed = false;
                            if (row.getInt(8) == 0)
                                completed = true;

                            i_renovacion = new Intent(ctx, RenovacionCreditoGpo.class);
                            i_renovacion.putExtra("is_new", completed);
                            i_renovacion.putExtra("id_solicitud", item.get(0));
                            i_renovacion.putExtra("nombre", item.get(1));
                            i_renovacion.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(i_renovacion);
                            dialog.dismiss();
                            row.close();
                            break;
                    }

                }
            });

            rvRenovacion.setAdapter(adapter);
            //initSwipe();
        }
    }
}
