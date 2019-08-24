package com.sidert.sidertmovil.activities;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.WindowManager;

import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.adapters.adapter_integrantes_gpo;
import com.sidert.sidertmovil.fragments.dialogs.dialog_integrante_gpo;
import com.sidert.sidertmovil.models.ModeloGrupal;
import com.sidert.sidertmovil.utils.Constants;
import com.sidert.sidertmovil.utils.NameFragments;

import java.util.List;

public class IntegrantesGpo extends AppCompatActivity {

    private Context ctx;
    private Toolbar tbMain;

    private RecyclerView rvIntegrantesGpo;

    private ModeloGrupal.Grupo grupo;
    private adapter_integrantes_gpo adapter;

    private List<ModeloGrupal.IntegrantesDelGrupo> _pagoIntegrantes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_integrantes_gpo);
        ctx             = this;
        tbMain           = findViewById(R.id.tbMain);

        rvIntegrantesGpo = findViewById(R.id.rvIntegrantesGpo);
        rvIntegrantesGpo.setLayoutManager(new LinearLayoutManager(ctx));
        rvIntegrantesGpo.setHasFixedSize(false);

        setSupportActionBar(tbMain);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        grupo = (ModeloGrupal.Grupo) getIntent().getSerializableExtra(Constants.INTEGRANTES_GRUPO);

        setTitle(grupo.getNombreGrupo());

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        adapter = new adapter_integrantes_gpo(ctx, grupo.getIntegrantesDelGrupo(), new adapter_integrantes_gpo.Event() {
            @Override
            public void Integrante(ModeloGrupal.IntegrantesDelGrupo item) {
                Log.v("IntegranteRealizado", String.valueOf(item.getPagoRealizado()));
                Log.v("IntegranteSolidario", String.valueOf(item.getPagoSolidario()));
                Log.v("IntegranteAdelanto", String.valueOf(item.getPagoAdelanto()));
                _pagoIntegrantes.add(item);

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
}
