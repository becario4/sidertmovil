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
import com.sidert.sidertmovil.activities.Configuracion;
import com.sidert.sidertmovil.utils.SessionManager;


public class dialog_pass_update_apk extends DialogFragment {

    private Context ctx;

    private EditText etKeyMaster;
    private Button btnAceptar;

    private ImageView ivClose;

    private SessionManager session;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.popup_pass_update_apk, container, false);

        ctx = getContext();

        session = new SessionManager(ctx);

        etKeyMaster = v.findViewById(R.id.etKeyMaster);
        btnAceptar = v.findViewById(R.id.btnAceptar);

        ivClose = v.findViewById(R.id.ivClose);

        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        btnAceptar.setOnClickListener(btnAceptar_OnClick);
        ivClose.setOnClickListener(ivClose_OnClick);
    }

    private View.OnClickListener btnAceptar_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //if (etKeyMaster.getText().toString().trim().equals("$APKACVS20")){
            if (true){
                Configuracion configActivity = (Configuracion) getActivity();
                configActivity.DownloadApk();
                dismiss();
            }
            else
                Toast.makeText(ctx, "Contraseña Incorrecta", Toast.LENGTH_SHORT).show();
        }
    };

    private View.OnClickListener ivClose_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            dismiss();
        }
    };
}
