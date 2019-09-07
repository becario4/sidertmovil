package com.sidert.sidertmovil.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.WindowManager;

import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.adapters.adapter_integrantes_gpo;
import com.sidert.sidertmovil.models.ModeloGrupal;
import com.sidert.sidertmovil.utils.Constants;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class IntegrantesGpo extends AppCompatActivity {

    @SuppressLint("UseSparseArrays")
    private HashMap<Integer, ModeloGrupal.IntegrantesDelGrupo> _pagos = new HashMap<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_integrantes_gpo);
        Context ctx = this;
        Toolbar tbMain = findViewById(R.id.tbMain);

        RecyclerView rvIntegrantesGpo = findViewById(R.id.rvIntegrantesGpo);
        rvIntegrantesGpo.setLayoutManager(new LinearLayoutManager(ctx));
        rvIntegrantesGpo.setHasFixedSize(false);

        setSupportActionBar(tbMain);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        ModeloGrupal.Grupo grupo = (ModeloGrupal.Grupo) getIntent().getSerializableExtra(Constants.INTEGRANTES_GRUPO);

        setTitle(grupo.getNombreGrupo());

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        adapter_integrantes_gpo adapter = new adapter_integrantes_gpo(ctx, grupo.getIntegrantesDelGrupo(), new adapter_integrantes_gpo.Event() {
            @Override
            public void Integrante(ModeloGrupal.IntegrantesDelGrupo item, int position) {

                if (_pagos.size() > 0) {
                    if (_pagos.containsKey(position)) {
                        _pagos.remove(position);
                        _pagos.put(position, item);
                    } else {
                        _pagos.put(position, item);
                    }
                } else {
                    _pagos.put(position, item);
                }



                /*dialog_integrante_gpo dg_integrante = new dialog_integrante_gpo();
                Bundle b = new Bundle();
                b.putSerializable(Constants.CLIENTE, item);
                dg_integrante.setArguments(b);
                dg_integrante.show(getSupportFragmentManager(), NameFragments.DIALOGINTEGRANTE);*/
            }
        });

        rvIntegrantesGpo.setAdapter(adapter);
        rvIntegrantesGpo.setItemViewCacheSize(20);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (_pagos.size() > 0){
                    double total = 0;
                    for (Map.Entry<Integer, ModeloGrupal.IntegrantesDelGrupo> entry : _pagos.entrySet()) {
                        total += entry.getValue().getPagoRealizado();
                    }
                    Intent i_resultado = new Intent();
                    i_resultado.putExtra(Constants.RESPONSE, total);
                    Log.v("Total de pago", String.valueOf(total));
                    setResult(RESULT_OK,i_resultado);
                    finish();
                }
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (_pagos.size() > 0){
            double total = 0;
            for (Map.Entry<Integer, ModeloGrupal.IntegrantesDelGrupo> entry : _pagos.entrySet()) {
                total += entry.getValue().getPagoRealizado();
            }
            Intent i_resultado = new Intent();
            i_resultado.putExtra(Constants.RESPONSE, total);
            Log.v("Total de pago", String.valueOf(total));
            setResult(RESULT_OK,i_resultado);
            finish();

        }
        super.onBackPressed();
    }
}
