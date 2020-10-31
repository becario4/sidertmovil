package com.sidert.sidertmovil.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.adapters.adapter_integrantes;
import com.sidert.sidertmovil.adapters.adapter_miembros_cc;
import com.sidert.sidertmovil.database.DBhelper;

import java.sql.Struct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import static com.sidert.sidertmovil.utils.Constants.ID_PRESTAMO;
import static com.sidert.sidertmovil.utils.Constants.NOMBRE_GRUPO;

public class IntegrantesCC extends AppCompatActivity {

    private Context ctx;
    private Toolbar tbMain;

    private TextView tvNombreGpo;

    private RecyclerView rvIntegrantes;

    private DBhelper dBhelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_integrantes_c_c);

        ctx = this;
        dBhelper = new DBhelper(ctx);
        tbMain = findViewById(R.id.tbMain);

        tvNombreGpo = findViewById(R.id.tvnombreGpo);

        rvIntegrantes = findViewById(R.id.rvIntegrantes);
        rvIntegrantes.setLayoutManager(new LinearLayoutManager(ctx));
        rvIntegrantes.setHasFixedSize(false);

        setSupportActionBar(tbMain);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        tvNombreGpo.setText(getIntent().getStringExtra("nombre_gpo"));

        String nombreGpo      = getIntent().getStringExtra("nombre_gpo");
        String integrantes      = getIntent().getStringExtra("integrantes");
        String clientesId        = getIntent().getStringExtra("cliente_id");
        final String grupoId          = getIntent().getStringExtra("grupo_id");
        final String numSolicitud     = getIntent().getStringExtra("num_solicitud");



        if (Long.parseLong(grupoId) > 0){
            String[] miembros = integrantes.split(",");
            String[] clienteId = clientesId.split(",");
            ArrayList<HashMap<Integer, String>> data = new ArrayList<>();
            for(int i = 0; i < miembros.length; i++){
                HashMap<Integer, String> item = new HashMap<>();
                item.put(0, clienteId[i]);
                item.put(1, miembros[i].substring(1));

                data.add(item);
            }

            adapter_miembros_cc adapter = new adapter_miembros_cc(ctx, data, new adapter_miembros_cc.Event() {
                @Override
                public void ccOnClick(HashMap<Integer, String> item) {
                    Intent i_formato_recibo = new Intent(ctx, RecuperacionRecibos.class);

                    i_formato_recibo.putExtra("grupo_id", grupoId);
                    i_formato_recibo.putExtra("num_solicitud", numSolicitud);
                    i_formato_recibo.putExtra("nombre", item.get(1));
                    i_formato_recibo.putExtra("nombre_firma", item.get(1));
                    i_formato_recibo.putExtra("monto", "17.5");
                    i_formato_recibo.putExtra("tipo", "CC");
                    i_formato_recibo.putExtra("cliente_id", item.get(0));
                    i_formato_recibo.putExtra("res_impresion", 0);
                    i_formato_recibo.putExtra("is_reeimpresion", false);
                    startActivity(i_formato_recibo);
                }
            });
            rvIntegrantes.setAdapter(adapter);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
