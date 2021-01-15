package com.sidert.sidertmovil.activities;

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
import android.widget.LinearLayout;

import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.adapters.adapter_renovacion;
import com.sidert.sidertmovil.database.DBhelper;
import com.sidert.sidertmovil.utils.Constants;

import java.util.ArrayList;
import java.util.HashMap;

import static com.sidert.sidertmovil.utils.Constants.TBL_CREDITO_GPO_REN;
import static com.sidert.sidertmovil.utils.Constants.TBL_SOLICITUDES_REN;

/**Clase donde se visualizaran todos los créditos por renovar de individual y grupal*/
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

        //initComponents();
    }

    /**Funcion para extraer todas las solicitudes de renovacion de individual y grupal*/
    private void initComponents(){
        /**Se prepara la consulta para obtener la solicitudes (SELECT * FROM TBL_SOLICITUDES_REN)*/
        Cursor row = dBhelper.getRecords(TBL_SOLICITUDES_REN, "", "", null);

        /**Valida si encontró algun registro*/
        if (row.getCount() > 0){
            row.moveToFirst();

            ArrayList<HashMap<Integer,String>> data = new ArrayList<>();
            for(int i = 0; i < row.getCount(); i++){/**Se recorre los resultados*/
                HashMap<Integer, String> item = new HashMap();
                Log.e("id_solicitud", row.getString(0) + " nombre: "+row.getString(5));
                item.put(0,row.getString(0));           //ID solicitud
                item.put(1, row.getString(5).trim().toUpperCase()); //Nombre
                item.put(2, row.getString(3));          //Tipo solicitud
                item.put(3, row.getString(11));         //Estatus
                item.put(4, row.getString(7));          //Fecha Termino
                item.put(5, row.getString(10));         //Fecha Envio

                data.add(item);/**Se agrega el map al array */
                row.moveToNext();
            }/**Termina el ciclo for*/

            /**Agrega el listado de solicitudes*/
            adapter = new adapter_renovacion(ctx, data, new adapter_renovacion.Event() {
                @Override
                public void FichaOnClick(HashMap<Integer, String> item) {
                    /**Evento de click para mostrar el formulario de solicitud de renovacion*/
                    Intent i_renovacion;
                    ProgressDialog dialog = ProgressDialog.show(RenovacionCredito.this, "",
                            "Cargando la información por favor espere...", true);
                    dialog.setCancelable(false);
                    dialog.show();
                    /**Valida el tipo de solicitud */
                    switch (Integer.parseInt(item.get(2))){
                        case 1:/**Individual*/
                            /**Abre vista de RenovacionCreditoInd*/
                            i_renovacion = new Intent(ctx, RenovacionCreditoInd.class);
                            /**Se manda el id de la solicitud de renovacion*/
                            i_renovacion.putExtra("is_new", false);
                            i_renovacion.putExtra("id_solicitud", item.get(0));
                            i_renovacion.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(i_renovacion);
                            dialog.dismiss();
                            break;
                        case 2:/**Grupal*/
                            /**Obtiene los datos del credito grupal*/
                            Cursor row = dBhelper.getRecords(TBL_CREDITO_GPO_REN, " WHERE id_solicitud = ?", "", new String[]{item.get(0)});
                            row.moveToFirst();
                            boolean completed = false;
                            if (row.getInt(8) == 0)/**valida el estatus del credito para saber si ya completo los datos del credito*/
                                completed = true;

                            /**Abre la vista de RenovacionCreditoGpo*/
                            i_renovacion = new Intent(ctx, RenovacionCreditoGpo.class);
                            i_renovacion.putExtra("is_new", completed);/**Bandera para validar si mostrara datos del credito o ya podrá continuar con los integrantes*/
                            i_renovacion.putExtra("id_solicitud", item.get(0));/**Agrega el id de la solicitud del credito*/
                            i_renovacion.putExtra("nombre", item.get(1));/**Nombre del grupo*/
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

    @Override
    protected void onResume() {
        super.onResume();
        initComponents();
    }
}
