package com.sidert.sidertmovil.fragments.dialogs;

import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.database.DBhelper;
import com.sidert.sidertmovil.utils.Constants;
import com.sidert.sidertmovil.utils.Popups;
import com.sidert.sidertmovil.utils.SessionManager;
import com.sidert.sidertmovil.utils.ValidatorTextView;
import com.sidert.sidertmovil.views.renovacion.RenovacionCreditoInd;

import java.util.Objects;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class dialog_plazo_ind extends DialogFragment {
    public interface OnCompleteListener {
        void onComplete(String plazo, String periodicidad);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            this.mListener = (dialog_plazo_ind.OnCompleteListener) activity;
        }
        catch (final ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnCompleteListener");
        }
    }

    private dialog_plazo_ind.OnCompleteListener mListener;
    private String[] _plazo;
    private String[] _frecuencia;
    private Context ctx;
    private RenovacionCreditoInd boostrap;
    private TextView tvPlazo;
    private TextView tvPeriodicidad;

    private Button btnGuardar;
    private Button btnCerrar;

    private DBhelper dBhelper;
    private SQLiteDatabase db;
    private SessionManager session;

    private ValidatorTextView validatorTV = new ValidatorTextView();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.popup_plazo_renovacion_ind, container, false);

        ctx = getContext();
        dBhelper = DBhelper.getInstance(ctx);
        db = dBhelper.getWritableDatabase();

        _plazo = getResources().getStringArray(R.array.intervalo);
        _frecuencia = getResources().getStringArray(R.array.lapso_grupal);

        session = SessionManager.getInstance(ctx);

        boostrap = (RenovacionCreditoInd) getActivity();

        tvPlazo = view.findViewById(R.id.tvPlazo);
        tvPeriodicidad = view.findViewById(R.id.tvFrecuencia);

        btnCerrar = view.findViewById(R.id.btnCerrar);
        btnGuardar = view.findViewById(R.id.btnGuardar);

        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        setCancelable(false);
        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        tvPlazo.setOnClickListener(tvPlazo_OnClick);
        tvPeriodicidad.setOnClickListener(tvPeriodicidad_OnClick);

        btnGuardar.setOnClickListener(btnGuardar_OnClick);
        btnCerrar.setOnClickListener(btnCerrar_OnClick);
    }

    private View.OnClickListener tvPlazo_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
            builder.setTitle(R.string.selected_option)
                    .setItems(R.array.intervalo, (dialog, position) -> {
                        tvPlazo.setError(null);
                        tvPlazo.setText(_plazo[position]);
                    });
            builder.create();
            builder.show();
        }
    };

    private View.OnClickListener tvPeriodicidad_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
            builder.setTitle(R.string.selected_option)
                .setItems(R.array.lapso_grupal, (dialog, position) -> {
                    tvPeriodicidad.setError(null);
                    tvPeriodicidad.setText(_frecuencia[position]);
                });
            builder.create();
            builder.show();
        }
    };

    private View.OnClickListener btnGuardar_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (
                !validatorTV.validate(tvPlazo, new String[]{validatorTV.REQUIRED}) &&
                !validatorTV.validate(tvPeriodicidad, new String[]{validatorTV.REQUIRED})
            )
            {
                AlertDialog guardar_info_dlg = Popups.showDialogConfirm(ctx, Constants.question,
                    R.string.datos_correctos, R.string.yes, dialog -> {
                        savePlazo();
                        dialog.dismiss();

                    }, R.string.no, dialog -> dialog.dismiss()
                );
                Objects.requireNonNull(guardar_info_dlg.getWindow()).requestFeature(Window.FEATURE_NO_TITLE);
                guardar_info_dlg.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                guardar_info_dlg.show();
            }
        }
    };

    private View.OnClickListener btnCerrar_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mListener.onComplete(tvPlazo.getText().toString(), tvPeriodicidad.getText().toString());

            getDialog().dismiss();
        }
    };

    private void savePlazo()
    {
        mListener.onComplete(tvPlazo.getText().toString(), tvPeriodicidad.getText().toString());
        getDialog().dismiss();
    }
}
