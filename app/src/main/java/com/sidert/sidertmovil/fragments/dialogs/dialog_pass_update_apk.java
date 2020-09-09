package com.sidert.sidertmovil.fragments.dialogs;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
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
import com.sidert.sidertmovil.utils.Validator;


public class dialog_pass_update_apk extends DialogFragment {

    private Context ctx;

    private EditText etKeyMaster;
    private Button btnAceptar;

    private ImageView ivClose;

    private SessionManager session;
    private Validator validator;

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

        validator = new Validator();
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
            if (!validator.validate(etKeyMaster, new String[]{validator.REQUIRED})){
                Configuracion configActivity = (Configuracion) getActivity();
                configActivity.DownloadApk(etKeyMaster.getText().toString().trim().toUpperCase());
                dismiss();
            }

        }
    };

    private View.OnClickListener ivClose_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            dismiss();
        }
    };
}
