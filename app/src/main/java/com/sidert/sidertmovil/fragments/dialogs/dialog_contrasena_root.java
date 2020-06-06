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
import android.widget.ImageView;
import android.widget.Toast;

import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.activities.Login;
import com.sidert.sidertmovil.utils.SessionManager;
import com.sidert.sidertmovil.utils.Validator;

public class dialog_contrasena_root extends DialogFragment {

    private Context ctx;

    private EditText etKeyMaster;
    private Button btnAceptar;

    private EditText etDominio;
    private EditText etPuerto;
    private Button btnGuardar;

    private ImageView ivClose;

    SessionManager session;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.popup_contrasena_root, container, false);

        ctx = getContext();

        session = new SessionManager(ctx);

        etKeyMaster = v.findViewById(R.id.etKeyMaster);
        btnAceptar = v.findViewById(R.id.btnAceptar);

        etDominio = v.findViewById(R.id.etDominio);
        etPuerto = v.findViewById(R.id.etPuerto);
        btnGuardar = v.findViewById(R.id.btnGuardar);

        ivClose = v.findViewById(R.id.ivClose);

        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        btnAceptar.setOnClickListener(btnAceptar_OnClick);
        btnGuardar.setOnClickListener(btnGuardar_OnClick);
        ivClose.setOnClickListener(ivClose_OnClick);
    }

    private View.OnClickListener btnAceptar_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (etKeyMaster.getText().toString().trim().equals("Qvv123")){
                Login loginActivity = (Login) getActivity();
                loginActivity.SetPass(true);
                dismiss();
            }
            else
                Toast.makeText(ctx, "Contraseña Incorrecta", Toast.LENGTH_SHORT).show();
        }
    };

    private View.OnClickListener btnGuardar_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Validator validator = new Validator();
            if (!validator.validate(etDominio, new String[]{validator.REQUIRED}) &&
                !validator.validate(etPuerto, new String[]{validator.REQUIRED})){
                session.setDominio(etDominio.getText().toString().trim(), etPuerto.getText().toString().trim());
                Login loginActivity = (Login) getActivity();
                loginActivity.SetPass(false);
                dismiss();
            }
        }
    };

    private View.OnClickListener ivClose_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Login loginActivity = (Login) getActivity();
            loginActivity.SetPass(false);
            dismiss();
        }
    };
}
