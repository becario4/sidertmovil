package com.sidert.sidertmovil.fragments.dialogs;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.fragment.app.DialogFragment;
//import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.views.originacion.AgregarIntegrante;
import com.sidert.sidertmovil.views.renovacion.RenovacionCreditoInd;
import com.sidert.sidertmovil.views.renovacion.RenovarIntegrante;
import com.sidert.sidertmovil.views.originacion.SolicitudCreditoInd;
import com.sidert.sidertmovil.utils.Validator;

import static com.sidert.sidertmovil.utils.Constants.CALLE;
import static com.sidert.sidertmovil.utils.Constants.TIPO;
import static com.sidert.sidertmovil.utils.Constants.TIPO_SOLICITUD;


public class dialog_input_calle extends DialogFragment {

    private Context ctx;
    private TextView tvMensaje;
    private EditText etCalle;
    private ImageView ivClose;
    private Button btnAceptar;

    private String tipoCalle;
    private String tipoSolicitud;
    private Validator validator;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.popup_input_calle, container, false);
        ctx = getContext();
        etCalle = v.findViewById(R.id.etCalle);
        tvMensaje = v.findViewById(R.id.tvMensaje);
        ivClose = v.findViewById(R.id.ivClose);
        btnAceptar = v.findViewById(R.id.btnAceptar);

        tipoCalle    = getArguments().getString(TIPO);
        etCalle.setText(getArguments().getString(CALLE));
        tipoSolicitud = getArguments().getString(TIPO_SOLICITUD);

        tvMensaje.setText("Ingrese el nombre de la calle " + tipoCalle.toLowerCase());

        validator = new Validator();
        btnAceptar.setOnClickListener(btnAceptar_OnClick);
        ivClose.setOnClickListener(ivClose_OnClick);

        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        return v;
    }

    private View.OnClickListener ivClose_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            getDialog().dismiss();
        }
    };

    private View.OnClickListener btnAceptar_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (!validator.validate(etCalle, new String[]{validator.REQUIRED})){
                if (tipoSolicitud.equals("INDIVIDUAL")) {
                    SolicitudCreditoInd creditoInd = (SolicitudCreditoInd) getActivity();
                    creditoInd.setCalle(etCalle.getText().toString().trim().toUpperCase(), tipoCalle);
                }
                else if (tipoSolicitud.equals("INDIVIDUAL RENOVACION")){
                    RenovacionCreditoInd renCreditoInd = (RenovacionCreditoInd) getActivity();
                    renCreditoInd.setCalle(etCalle.getText().toString().trim().toUpperCase(), tipoCalle);
                }
                else if (tipoSolicitud.equals("GRUPAL RENOVACION")){
                    RenovarIntegrante renCreditoInt = (RenovarIntegrante) getActivity();
                    renCreditoInt.setCalle(etCalle.getText().toString().trim().toUpperCase(), tipoCalle);
                }
                else{
                    AgregarIntegrante creditoInt = (AgregarIntegrante) getActivity();
                    creditoInt.setCalle(etCalle.getText().toString().trim().toUpperCase(), tipoCalle);
                }
                dismiss();
            }
        }
    };
}
