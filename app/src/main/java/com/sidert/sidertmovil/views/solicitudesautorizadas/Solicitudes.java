package com.sidert.sidertmovil.views.solicitudesautorizadas;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;

import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.adapters.adapter_originacion;
import com.sidert.sidertmovil.database.DBhelper;

import java.util.ArrayList;
import java.util.HashMap;

import static com.sidert.sidertmovil.utils.Constants.TBL_SOLICITUDES_AUTO;

public class Solicitudes extends AppCompatActivity {

    private Context ctx;

    private adapter_originacion adapter;
    private RecyclerView rvSolicitud;

    private DBhelper dBhelper;
    private SQLiteDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solicitudes);

        ctx = this;

        dBhelper = new DBhelper(ctx);
        db = dBhelper.getWritableDatabase();

        rvSolicitud = findViewById(R.id.rvSolicitudes);

        rvSolicitud.setLayoutManager(new LinearLayoutManager(ctx));
        rvSolicitud.setHasFixedSize(false);

    }

    private void initComponents(){
        Cursor row = dBhelper.getRecords(TBL_SOLICITUDES_AUTO, "", "", null);

        if (row.getCount() > 0){
            row.moveToFirst();
            ArrayList<HashMap<Integer,String>> data = new ArrayList<>();
            for(int i = 0; i < row.getCount(); i++){
                HashMap<Integer, String> item = new HashMap();
                Log.e("id_solicitud", row.getString(0) + " nombre: "+row.getString(5)+ " tipo_solicitud"+row.getString(1));
                item.put(0,row.getString(0));           //ID solicitud
                item.put(1, row.getString(5).trim().toUpperCase()); //Nombre
                item.put(2, row.getString(1));          //Tipo solicitud
                item.put(3, row.getString(7));          //Estatus
                item.put(4, "");                          //Fecha Termino
                item.put(5, row.getString(6));          //Fecha Envio
                item.put(6, row.getString(4));          //id_originacion
                data.add(item);
                row.moveToNext();
            }

            adapter = new adapter_originacion(ctx, data, new adapter_originacion.Event() {
                @Override
                public void FichaOnClick(HashMap<Integer, String> item) {
                    Intent i_solicitud;
                    ProgressDialog dialog = ProgressDialog.show(Solicitudes.this, "",
                            "Cargando la información por favor espere...", true);
                    dialog.setCancelable(false);
                    dialog.show();
                    switch (Integer.parseInt(item.get(2))){
                        case 1:
                            Log.e("Clic", "individual");
                            i_solicitud = new Intent(ctx, SolicitudInd.class);
                            i_solicitud.putExtra("is_new", false);
                            i_solicitud.putExtra("id_solicitud", item.get(0));
                            i_solicitud.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(i_solicitud);
                            dialog.dismiss();
                            break;
                        case 2:
                            i_solicitud = new Intent(ctx, SolicitudGpo.class);
                            i_solicitud.putExtra("is_new", false);
                            i_solicitud.putExtra("id_solicitud", item.get(0));
                            i_solicitud.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            Log.e("ID_SOLICITUD", item.get(0));
                            startActivity(i_solicitud);
                            dialog.dismiss();
                            break;
                    }

                }
            });

            rvSolicitud.setAdapter(adapter);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        initComponents();
    }
}
