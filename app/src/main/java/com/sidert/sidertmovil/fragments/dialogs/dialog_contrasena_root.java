package com.sidert.sidertmovil.fragments.dialogs;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
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

    private TextView tvMacAddress;

    private SessionManager session;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.popup_contrasena_root, container, false);

        ctx = getContext();

        session = SessionManager.getInstance(ctx);

        etKeyMaster = v.findViewById(R.id.etKeyMaster);
        btnAceptar = v.findViewById(R.id.btnAceptar);

        etDominio = v.findViewById(R.id.etDominio);
        etPuerto = v.findViewById(R.id.etPuerto);
        btnGuardar = v.findViewById(R.id.btnGuardar);

        tvMacAddress = v.findViewById(R.id.tvMacAddress);

        ivClose = v.findViewById(R.id.ivClose);

        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        tvMacAddress.setText("MAC:" + session.getMacAddress());

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
            if (etKeyMaster.getText().toString().trim().equals("Qvv123")) {
                Login loginActivity = (Login) getActivity();
                loginActivity.SetPass(true);
                dismiss();
            } else
                Toast.makeText(ctx, "Contraseña Incorrecta", Toast.LENGTH_SHORT).show();
        }
    };

    private View.OnClickListener btnGuardar_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Validator validator = new Validator();
            if (!validator.validate(etKeyMaster, new String[]{validator.REQUIRED}) &&
                    !validator.validate(etDominio, new String[]{validator.REQUIRED}) &&
                    !validator.validate(etPuerto, new String[]{validator.REQUIRED})) {
                if (etKeyMaster.getText().toString().trim().equals("Qvv123")) {
                    session.setDominio("http://" + etDominio.getText().toString().trim() + ":" + etPuerto.getText().toString().trim());
                    Login loginActivity = (Login) getActivity();
                    loginActivity.SetPass(false);
                    dismiss();
                } else {
                    etKeyMaster.setError("La Clave es incorrecta");
                }
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
