package com.sidert.sidertmovil.fragments.dialogs;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.activities.CodigosOxxo;
import com.sidert.sidertmovil.utils.Miscellaneous;

import static com.sidert.sidertmovil.utils.Constants.FECHA_AMORTIZACION;
import static com.sidert.sidertmovil.utils.Constants.MONTO_AMORTIZACION;
import static com.sidert.sidertmovil.utils.Constants.NOMBRE;
import static com.sidert.sidertmovil.utils.Constants.RESPONSE;


public class dialog_generar_codigo_oxxo extends DialogFragment {

    private Context ctx;

    private ImageView ivClose;

    private TextView tvNombre;
    private TextView tvFechaAmortiz;
    private TextView tvMontoAmortiz;

    private Button btnGenerar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.popup_generar_codigo_oxxo, container, false);

        ctx     = getContext();

        ivClose = v.findViewById(R.id.ivClose);

        tvNombre = v.findViewById(R.id.tvNombre);
        tvFechaAmortiz = v.findViewById(R.id.tvFechaAmortiz);
        tvMontoAmortiz  = v.findViewById(R.id.tvMontoAmortiz);

        btnGenerar      = v.findViewById(R.id.btnGenerar);

        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        //setCancelable(false);

        tvNombre.setText(getArguments().getString(NOMBRE));
        tvFechaAmortiz.setText(getArguments().getString(FECHA_AMORTIZACION));
        tvMontoAmortiz.setText(Miscellaneous.moneyFormat(getArguments().getString(MONTO_AMORTIZACION)).replace("$",""));

        btnGenerar.setOnClickListener(btnGenerar_OnClick);
        ivClose.setOnClickListener(ivClose_Click);
        return v;
    }

    private View.OnClickListener btnGenerar_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent i = new Intent();
            i.putExtra(RESPONSE,true);
            CodigosOxxo oxxo = (CodigosOxxo) getActivity();
            oxxo.GenerarCodigo();
            getDialog().dismiss();
        }
    };

    private View.OnClickListener ivClose_Click = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            getDialog().dismiss();
        }
    };
}
