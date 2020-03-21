package com.sidert.sidertmovil.fragments.dialogs;


import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.activities.AgregarIntegrante;
import com.sidert.sidertmovil.activities.CirculoCredito;
import com.sidert.sidertmovil.activities.FormatoRecibos;
import com.sidert.sidertmovil.models.MFormatoRecibo;
import com.sidert.sidertmovil.utils.Constants;
import com.sidert.sidertmovil.utils.Popups;
import com.sidert.sidertmovil.utils.SessionManager;
import com.sidert.sidertmovil.utils.Validator;

import java.util.Objects;

import static com.sidert.sidertmovil.utils.Constants.ITEM;
import static com.sidert.sidertmovil.utils.Constants.TICKET_CC;

public class dialog_circulo_credito extends DialogFragment {

    private Context ctx;

    private CirculoCredito boostrap;

    private EditText etNombre;
    private EditText etPaterno;
    private EditText etMaterno;

    private Button btnGuardar;
    private Button btnCancelar;

    private SessionManager session;

    private Validator validator;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.popup_circulo_credito, container, false);

        ctx = getContext();
        boostrap    = (CirculoCredito) getActivity();

        session = new SessionManager(ctx);

        etNombre    = v.findViewById(R.id.etNombre);
        etPaterno   = v.findViewById(R.id.etPaterno);
        etMaterno   = v.findViewById(R.id.etMaterno);

        btnGuardar  = v.findViewById(R.id.btnGuardar);
        btnCancelar = v.findViewById(R.id.btnCancelar);

        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        setCancelable(false);

        btnGuardar.setOnClickListener(btnGuardar_OnClick);
        btnCancelar.setOnClickListener(btnCancelar_OnClick);

        return v;
    }

    private View.OnClickListener btnGuardar_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            validator = new Validator();
            if (!validator.validate(etNombre, new String[]{validator.REQUIRED, validator.ONLY_TEXT}) &&
                    !validator.validate(etPaterno, new String[]{validator.REQUIRED, validator.ONLY_TEXT}) &&
                    !validator.validate(etMaterno, new String[]{validator.ONLY_TEXT})){
                AlertDialog guardar_info_dlg = Popups.showDialogConfirm(ctx, Constants.question,
                        R.string.datos_correctos, R.string.yes, new Popups.DialogMessage() {
                            @Override
                            public void OnClickListener(AlertDialog dialog) {
                                ImprimirTicket();
                                dialog.dismiss();

                            }
                        }, R.string.no, new Popups.DialogMessage() {
                            @Override
                            public void OnClickListener(AlertDialog dialog) {
                                dialog.dismiss();
                            }
                        });
                Objects.requireNonNull(guardar_info_dlg.getWindow()).requestFeature(Window.FEATURE_NO_TITLE);
                guardar_info_dlg.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                guardar_info_dlg.show();
            }
        }
    };

    private void ImprimirTicket (){
        String nombre = etNombre.getText().toString().trim().toUpperCase() + " " +
                etPaterno.getText().toString().trim().toUpperCase() + " " +
                etMaterno.getText().toString().trim().toUpperCase();

        Log.e("nombre", nombre.trim());
        MFormatoRecibo mRecibo = new MFormatoRecibo();
        mRecibo.setNombreCliente(nombre.trim());
        mRecibo.setTipoRecibo(1);
        mRecibo.setTipoImpresion(true);
        mRecibo.setFolio("");
        mRecibo.setReeimpresion(false);

        Intent i_formato_recibo = new Intent(boostrap, FormatoRecibos.class);
        i_formato_recibo.putExtra(TICKET_CC, mRecibo);
        startActivity(i_formato_recibo);
        getDialog().dismiss();
    }

    private View.OnClickListener btnCancelar_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            getDialog().dismiss();
        }
    };

}
