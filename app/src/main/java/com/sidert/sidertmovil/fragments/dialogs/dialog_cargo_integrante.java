package com.sidert.sidertmovil.fragments.dialogs;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
/*import androidx.appcompat.app.AppCompatActivity;
import android.support.v7.widget.CardView;*/
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sidert.sidertmovil.R;


public class dialog_cargo_integrante extends DialogFragment {

    private Context ctx;

    private CardView cvPresidente;
    private CardView cvTesorero;
    private CardView cvSecretario;
    private CardView cvIntegrante;

    private TextView tvPresidente;
    private TextView tvTesorero;
    private TextView tvSecretario;
    private TextView tvIntegrante;

    private View vPresidente;
    private View vTesorero;
    private View vSecretario;
    private View vIntegrante;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.popup_cargo_integrante, container, false);

        ctx = getContext();
        cvPresidente = view.findViewById(R.id.cvPresidente);
        cvTesorero = view.findViewById(R.id.cvTesorero);
        cvSecretario = view.findViewById(R.id.cvSecretario);
        cvIntegrante = view.findViewById(R.id.cvIntegrante);

        tvPresidente = view.findViewById(R.id.tvPresidente);
        tvTesorero = view.findViewById(R.id.tvTesorero);
        tvSecretario = view.findViewById(R.id.tvSecretario);
        tvIntegrante = view.findViewById(R.id.tvIntegrante);

        vPresidente = view.findViewById(R.id.vPresidente);
        vTesorero = view.findViewById(R.id.vTesorero);
        vSecretario = view.findViewById(R.id.vSecretario);
        vIntegrante = view.findViewById(R.id.vIntegrante);

        if (!getArguments().getBoolean("presidente")) {
            tvPresidente.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(ctx,R.drawable.ic_done_black), null);
            vPresidente.setVisibility(View.VISIBLE);
        }
        if (!getArguments().getBoolean("tesorero")) {
            tvTesorero.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(ctx,R.drawable.ic_done_black), null);
            vTesorero.setVisibility(View.VISIBLE);
        }
        if (!getArguments().getBoolean("secretario")) {
            tvSecretario.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(ctx,R.drawable.ic_done_black), null);
            vSecretario.setVisibility(View.VISIBLE);
        }
        if (!getArguments().getBoolean("integrante")) {
            tvIntegrante.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(ctx,R.drawable.ic_done_black), null);
            vIntegrante.setVisibility(View.VISIBLE);
        }

        cvPresidente.setEnabled(getArguments().getBoolean("presidente"));
        cvTesorero.setEnabled(getArguments().getBoolean("tesorero"));
        cvSecretario.setEnabled(getArguments().getBoolean("secretario"));
        cvIntegrante.setEnabled(getArguments().getBoolean("integrante"));
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        cvPresidente.setOnClickListener(cvPresidente_OnClick);
        cvTesorero.setOnClickListener(cvTesorero_OnClick);
        cvSecretario.setOnClickListener(cvSecretario_OnClick);
        cvIntegrante.setOnClickListener(cvIntegrante_OnClick);
    }

    private View.OnClickListener cvPresidente_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i_result = new Intent();
            i_result.putExtra("id_cargo", 1);
            i_result.putExtra("cargo", "PRESIDENTE");
            getTargetFragment().onActivityResult(8652,2658,i_result);
            getDialog().dismiss();
        }
    };

    private View.OnClickListener cvTesorero_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i_result = new Intent();
            i_result.putExtra("id_cargo", 3);
            i_result.putExtra("cargo", "TESORERO");
            getTargetFragment().onActivityResult(8652,2658,i_result);
            getDialog().dismiss();
        }
    };

    private View.OnClickListener cvSecretario_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i_result = new Intent();
            i_result.putExtra("id_cargo", 2);
            i_result.putExtra("cargo", "SECRETARIO");
            getTargetFragment().onActivityResult(8652,2658,i_result);
            getDialog().dismiss();
        }
    };

    private View.OnClickListener cvIntegrante_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i_result = new Intent();
            i_result.putExtra("id_cargo", 4);
            i_result.putExtra("cargo", "INTEGRANTE");
            getTargetFragment().onActivityResult(8652,2658,i_result);
            getDialog().dismiss();
        }
    };
}
