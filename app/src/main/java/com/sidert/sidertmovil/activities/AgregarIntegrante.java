package com.sidert.sidertmovil.activities;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.sidert.sidertmovil.R;

import moe.feng.common.stepperview.VerticalStepperItemView;

public class AgregarIntegrante extends AppCompatActivity {

    private Context ctx;

    private VerticalStepperItemView mSteppers[] = new VerticalStepperItemView[6];

    private FloatingActionButton btnContinuar0;
    private FloatingActionButton btnContinuar1;
    private FloatingActionButton btnContinuar2;
    private FloatingActionButton btnContinuar3;
    private FloatingActionButton btnContinuar4;
    private FloatingActionButton btnContinuar5;

    private FloatingActionButton btnRegresar1;
    private FloatingActionButton btnRegresar2;
    private FloatingActionButton btnRegresar3;
    private FloatingActionButton btnRegresar4;
    private FloatingActionButton btnRegresar5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_integrante);

        ctx = this;

        mSteppers[0] = findViewById(R.id.stepper_0);
        mSteppers[1] = findViewById(R.id.stepper_1);
        mSteppers[2] = findViewById(R.id.stepper_2);
        mSteppers[3] = findViewById(R.id.stepper_3);
        mSteppers[4] = findViewById(R.id.stepper_4);
        mSteppers[5] = findViewById(R.id.stepper_5);

        btnContinuar0 = findViewById(R.id.btnContinuar0);
        btnContinuar1 = findViewById(R.id.btnContinuar1);
        btnContinuar2 = findViewById(R.id.btnContinuar2);
        btnContinuar3 = findViewById(R.id.btnContinuar3);
        btnContinuar4 = findViewById(R.id.btnContinuar4);
        btnContinuar5 = findViewById(R.id.btnContinuar5);

        btnRegresar1 = findViewById(R.id.btnRegresar1);
        btnRegresar2 = findViewById(R.id.btnRegresar2);
        btnRegresar3 = findViewById(R.id.btnRegresar3);
        btnRegresar4 = findViewById(R.id.btnRegresar4);
        btnRegresar5 = findViewById(R.id.btnRegresar5);

        VerticalStepperItemView.bindSteppers(mSteppers);

        btnContinuar0.setOnClickListener(btnContinuar0_OnClick);
        btnContinuar1.setOnClickListener(btnContinuar1_OnClick);
        btnContinuar2.setOnClickListener(btnContinuar2_OnClick);
        btnContinuar3.setOnClickListener(btnContinuar3_OnClick);
        btnContinuar4.setOnClickListener(btnContinuar4_OnClick);
        btnContinuar5.setOnClickListener(btnContinuar5_OnClick);

        btnRegresar1.setOnClickListener(btnRegresar1_OnClick);
        btnRegresar2.setOnClickListener(btnRegresar2_OnClick);
        btnRegresar3.setOnClickListener(btnRegresar3_OnClick);
        btnRegresar4.setOnClickListener(btnRegresar4_OnClick);
        btnRegresar5.setOnClickListener(btnRegresar5_OnClick);
    }

    //Continuar
    private View.OnClickListener btnContinuar0_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mSteppers[0].nextStep();
        }
    };
    private View.OnClickListener btnContinuar1_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mSteppers[1].nextStep();
        }
    };
    private View.OnClickListener btnContinuar2_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mSteppers[2].nextStep();
        }
    };
    private View.OnClickListener btnContinuar3_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mSteppers[3].nextStep();
        }
    };
    private View.OnClickListener btnContinuar4_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mSteppers[4].nextStep();
        }
    };
    private View.OnClickListener btnContinuar5_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast.makeText(ctx, "Terminar procesos", Toast.LENGTH_SHORT).show();
        }
    };

    //Regresar
    private View.OnClickListener btnRegresar1_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mSteppers[1].prevStep();
        }
    };
    private View.OnClickListener btnRegresar2_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mSteppers[2].prevStep();
        }
    };
    private View.OnClickListener btnRegresar3_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mSteppers[3].prevStep();
        }
    };
    private View.OnClickListener btnRegresar4_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mSteppers[4].prevStep();
        }
    };
    private View.OnClickListener btnRegresar5_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mSteppers[5].prevStep();
        }
    };
}
