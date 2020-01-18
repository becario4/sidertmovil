package com.sidert.sidertmovil.activities;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.utils.Constants;

public class SolicitudCreditoGpo extends AppCompatActivity {

    private Context ctx;
    private RecyclerView rvIntegrantes;
    private FloatingActionButton fabAgregar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solicitud_credito_gpo);

        ctx = this;
        rvIntegrantes = findViewById(R.id.rvIntegrantes);
        fabAgregar = findViewById(R.id.fabAgregar);

        fabAgregar.setOnClickListener(fabAgregar_OnClick);
    }

    private View.OnClickListener fabAgregar_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i_agregar_integrante = new Intent(ctx, AgregarIntegrante.class);
            startActivityForResult(i_agregar_integrante, Constants.REQUEST_CODE_ADD_INTEGRANTE);
        }
    };
}
