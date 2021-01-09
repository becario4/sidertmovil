package com.sidert.sidertmovil.activities;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;

import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.adapters.adapter_consultas_cc;
import com.sidert.sidertmovil.database.DBhelper;
import com.sidert.sidertmovil.utils.NetworkStatus;
import com.sidert.sidertmovil.utils.Popups;
import com.sidert.sidertmovil.utils.Servicios_Sincronizado;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.sidert.sidertmovil.utils.Constants.TBL_CONSULTA_CC;
import static com.sidert.sidertmovil.utils.Constants.not_network;
import static com.sidert.sidertmovil.utils.Constants.warning;

/**Clase para ver las consultas de CC realizadas por el asesor
 * Aun no se esta ocupando esta vista*/
public class ConsultadosCC extends AppCompatActivity {

    private Context ctx;
    private Toolbar tbMain;

    private RecyclerView rvConsultados;

    private FloatingActionButton fbConsultar;

    private adapter_consultas_cc adapter;

    private DBhelper dBhelper;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultados_c_c);

        ctx = this;

        dBhelper = new DBhelper(ctx);
        db = dBhelper.getWritableDatabase();

        tbMain           = findViewById(R.id.tbMain);
        rvConsultados    = findViewById(R.id.rvConsultados);
        fbConsultar      = findViewById(R.id.fbConsultar);

        setSupportActionBar(tbMain);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        rvConsultados.setLayoutManager(new LinearLayoutManager(ctx));
        rvConsultados.setHasFixedSize(false);

        /**Evento de click para crear una nueva consulta de CC*/
        fbConsultar.setOnClickListener(fbConsultar_OnClick);

    }

    /**Evento para ccrear una nueva consulta de CC*/
    private View.OnClickListener fbConsultar_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            fbConsultar.setEnabled(false);
            Intent i_consultar = new Intent(ctx, ConsultarCC.class);
            startActivity(i_consultar);
        }
    };

    /**Funcion para obtener las consultas de CC realizadas por el asesor*/
    private void initComponets(){
        String sql = "SELECT * FROM " + TBL_CONSULTA_CC + " ORDER BY fecha_termino DESC";
        Cursor row = db.rawQuery(sql, null);
        if (row.getCount() > 0){
            row.moveToFirst();
            List<HashMap<String, String>> consultados = new ArrayList<>();
            for (int i = 0; i < row.getCount(); i++){
                HashMap<String, String> item = new HashMap<>();
                String nombre =row.getString(3) + " " + row.getString(4);
                nombre = nombre.trim() + " " + row.getString(5) + " " + row.getString(6);
                String direccion = row.getString(12)+", "+row.getString(14)+", "+row.getString(13)+", "+row.getString(15)+", "+row.getString(17);

                item.put("nombre", nombre.trim());
                item.put("direccion", direccion.trim());
                item.put("producto", row.getString(1));
                item.put("estatus", row.getString(20));
                item.put("errores", row.getString(26));
                item.put("saldo_actual", row.getString(21));
                item.put("saldo_vencido", row.getString(22));
                item.put("creditos_abiertos", row.getString(24));
                item.put("creditos_cerrados", row.getString(25));
                item.put("peor_pago", row.getString(23));
                item.put("fecha_termino", row.getString(18));
                item.put("fecha_envio", row.getString(19));
                consultados.add(item);
                row.moveToNext();
            }

            /**Agregar todas las consultas obtenidas de CC*/
            adapter = new adapter_consultas_cc(ctx, consultados, new adapter_consultas_cc.Event() {
                @Override
                public void OnClick(HashMap<String, String> item) {
                    /**Evento al dar click a una cosulta para ver la respuesta de CC*/
                    String mess = "";
                    if (!item.get("errores").trim().isEmpty() && item.get("estatus").equals("1"))
                    {
                        mess = "Nombre: "+item.get("nombre")+
                                "\n Mensaje: "+item.get("errores")+
                                "\n Fecha Término: "+item.get("fecha_termino")+
                                "\n Fecha Envío: "+item.get("fecha_envio");
                    }
                    else if(item.get("errores").trim().isEmpty() && item.get("estatus").equals("1"))
                    {
                        mess = "Nombre: "+item.get("nombre")+
                                "\n Saldo Actual: "+item.get("saldo_actual")+
                                "\n Saldo Vencido: "+item.get("saldo_vencido")+
                                "\n Créditos Abiertos: "+item.get("creditos_abiertos")+
                                "\n Créditos Cerrados: "+item.get("creditos_cerrados")+
                                "\n Peor Pago: "+item.get("peor_pago")+
                                "\n Fecha Término: "+item.get("fecha_termino")+
                                "\n Fecha Envío: "+item.get("fecha_envio");
                    }
                    else{
                        mess = "Nombre: "+item.get("nombre")+
                                "\n Fecha Término: "+item.get("fecha_termino")+
                                "\n Pendiente de Envío";
                    }

                    final AlertDialog info = Popups.showDialogMessage(ctx, warning,
                            mess, R.string.accept, new Popups.DialogMessage() {
                                @Override
                                public void OnClickListener(AlertDialog dialog) {
                                    dialog.dismiss();
                                }
                            });
                    info.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                    info.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                    info.show();
                }
            });
            rvConsultados.setAdapter(adapter);
        }
        row.close();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_actualizar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.actualizar:
                /**Envia las consulta que estan en pendiente de envio*/
                String sql = "SELECT * FROM " + TBL_CONSULTA_CC + " WHERE estatus = ?";
                Cursor r = db.rawQuery(sql, new String[]{"0"});
                if (r.getCount() > 0){
                    Servicios_Sincronizado ss = new Servicios_Sincronizado();
                    ss.SendConsultaCC(ctx, true);
                }
                r.close();
                initComponets();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        fbConsultar.setEnabled(true);
        initComponets();
    }
}
