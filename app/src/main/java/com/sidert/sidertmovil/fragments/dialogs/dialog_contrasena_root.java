package com.sidert.sidertmovil.fragments.dialogs;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.activities.Login;

public class dialog_contrasena_root extends DialogFragment {

    private Context ctx;

    private EditText etPassword;
    private Button btnAceptar;
    private Button btnCancelar;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.popup_contrasena_root, container, false);

        ctx = getContext();

        etPassword = v.findViewById(R.id.etPassword);
        btnAceptar = v.findViewById(R.id.btnAceptar);
        btnCancelar = v.findViewById(R.id.btnCancelar);

        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        btnAceptar.setOnClickListener(btnAceptar_OnClick);
        btnCancelar.setOnClickListener(btnCancelar_OnClick);
    }

    private View.OnClickListener btnAceptar_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (etPassword.getText().toString().trim().equals("Qvv123")){
                Login loginActivity = (Login) getActivity();
                loginActivity.SetPass(true);
                dismiss();
            }
            else
                Toast.makeText(ctx, "Contraseña Incorrecta", Toast.LENGTH_SHORT).show();
        }
    };

    private View.OnClickListener btnCancelar_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Login loginActivity = (Login) getActivity();
            loginActivity.SetPass(false);
            dismiss();
        }
    };
}
