package com.sidert.sidertmovil.activities;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.ActionViewTarget;
import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.fragments.dialogs.dialog_originacion_gpo;
import com.sidert.sidertmovil.utils.Constants;
import com.sidert.sidertmovil.utils.NameFragments;

import org.json.JSONException;

public class SolicitudCreditoGpo extends AppCompatActivity implements dialog_originacion_gpo.OnCompleteListener {

    private Context ctx;
    private RecyclerView rvIntegrantes;
    private FloatingActionButton fabAgregar;

    private String nombre_gpo;
    private int plazo;
    private int periodicidad;
    private String fecha_desembolso;
    private String dia_desembolso;
    private String hora_visita;
    private boolean is_edit = false;

    private TextView tvInfoCredito;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solicitud_credito_gpo);

        ctx = this;
        tvInfoCredito = findViewById(R.id.tvInfoCredito);
        rvIntegrantes = findViewById(R.id.rvIntegrantes);
        fabAgregar = findViewById(R.id.fabAgregar);



        tvInfoCredito.setOnClickListener(tvInfoCredito_OnClick);
        fabAgregar.setOnClickListener(fabAgregar_OnClick);
        if (getIntent().getBooleanExtra("is_new",true)) {
            is_edit = true;
            openInfoOriginacion();
        }
        else{
            nombre_gpo = getIntent().getStringExtra("nombre");
            plazo = getIntent().getIntExtra("plazo",0);
            periodicidad = getIntent().getIntExtra("periodicidad", 0);
            fecha_desembolso = getIntent().getStringExtra("fecha_desembolso");
            dia_desembolso = getIntent().getStringExtra("dia_desembolso");
            hora_visita = getIntent().getStringExtra("hora_visita");
            is_edit = false;
        }
    }

    private View.OnClickListener tvInfoCredito_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            openInfoOriginacion();
        }
    };

    private View.OnClickListener fabAgregar_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i_agregar_integrante = new Intent(ctx, AgregarIntegrante.class);
            startActivityForResult(i_agregar_integrante, Constants.REQUEST_CODE_ADD_INTEGRANTE);
        }
    };

    private void openInfoOriginacion() {
        dialog_originacion_gpo originacion_gpo = new dialog_originacion_gpo();
        originacion_gpo.setCancelable(false);
        Bundle b = new Bundle();
        if (!is_edit) {
            b.putBoolean("is_edit", false);
            b.putString("nombre", nombre_gpo);
            b.putInt("plazo", plazo);
            b.putInt("periodicidad", periodicidad);
            b.putString("fecha_desembolso", fecha_desembolso);
            b.putString("dia_desembolso", dia_desembolso);
            b.putString("hora_visita", hora_visita);
            originacion_gpo.setArguments(b);
        }
        else
            b.putBoolean("is_edit", true);
        originacion_gpo.setArguments(b);
        originacion_gpo.show(getSupportFragmentManager(), NameFragments.DIALOGORIGINACIONGPO);
    }

    @Override
    public void onComplete(String nombre, int plazo, int periodicidad, String fecha, String dia, String hora) {
        nombre_gpo = nombre;
        this.plazo = plazo;
        this.periodicidad = periodicidad;
        this.fecha_desembolso = fecha;
        this.dia_desembolso = dia;
        this.hora_visita = hora;
        is_edit = false;
    }
}
