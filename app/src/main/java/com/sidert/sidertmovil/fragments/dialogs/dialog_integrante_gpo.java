package com.sidert.sidertmovil.fragments.dialogs;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sidert.sidertmovil.R;


public class dialog_integrante_gpo extends DialogFragment {

    private Context ctx;

    private EditText etNombre;
    private EditText etPagoSemanal;
    private EditText etPagoRealizado;
    private EditText etPagoSolidario;
    private EditText etPagoAdelanto;

    private Button btnGuardar;

    private double pagoSemanal, pagoRealizado, pagoSolidario = 0, pagoAdelanto = 0;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.popup_integrante_gpo, container, false);

        ctx = getContext();
        etNombre    = view.findViewById(R.id.etNombre);
        etPagoSemanal   = view.findViewById(R.id.etPagoSemanal);
        etPagoRealizado = view.findViewById(R.id.etPagoRealizado);
        etPagoSolidario = view.findViewById(R.id.etPagoSolidario);
        etPagoAdelanto  = view.findViewById(R.id.etPagoAdelanto);
        btnGuardar      = view.findViewById(R.id.btnGuardar);
        //getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        //getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        setCancelable(false);

        btnGuardar.setOnClickListener(btnGuardar_OnClick);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        etPagoRealizado.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                EvalMontos();
            }
        });

        etPagoSolidario.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                EvalMontos();
            }
        });
    }

    private View.OnClickListener btnGuardar_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (!etPagoRealizado.getText().toString().trim().isEmpty()){
                Toast.makeText(ctx, "Pago Realizado: " + etPagoRealizado.getText(), Toast.LENGTH_SHORT).show();
                getDialog().dismiss();
            }else{
                etPagoRealizado.setError("Este campo es requerido");
            }
        }
    };

    private void EvalMontos (){

    }
}
