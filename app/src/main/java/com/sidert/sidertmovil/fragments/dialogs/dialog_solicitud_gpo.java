package com.sidert.sidertmovil.fragments.dialogs;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
//import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.views.solicitudesautorizadas.SolicitudGpo;
import com.sidert.sidertmovil.database.DBhelper;
import com.sidert.sidertmovil.utils.SessionManager;

public class dialog_solicitud_gpo extends DialogFragment {

    private Context ctx;
    private SolicitudGpo boostrap;
    private EditText etNombre;
    private TextView tvPlazo;
    private TextView tvPeriodicidad;
    private TextView tvFechaDesembolso;
    private TextView tvDiaDesembolso;
    private TextView tvHoraVisita;
    private TextView tvObservaciones;
    private EditText etObservaciones;
    private Button btnGuardar;
    private Button btnCerrar;
    private DBhelper dBhelper;
    private SQLiteDatabase db;
    private SessionManager session;
    private String idSolicitud = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.popup_solicitud_gpo, container, false);

        ctx = getContext();

        dBhelper = DBhelper.getInstance(ctx);
        db = dBhelper.getWritableDatabase();

        session = SessionManager.getInstance(ctx);
        boostrap = (SolicitudGpo) getActivity();
        etNombre = view.findViewById(R.id.etNombreGpo);
        tvPlazo = view.findViewById(R.id.tvPlazo);
        tvPeriodicidad = view.findViewById(R.id.tvFrecuencia);
        tvFechaDesembolso = view.findViewById(R.id.tvFechaDesembolso);
        tvDiaDesembolso = view.findViewById(R.id.tvDiaDesembolso);
        tvHoraVisita = view.findViewById(R.id.tvHoraVisita);
        tvObservaciones = view.findViewById(R.id.tvObservaciones);
        etObservaciones = view.findViewById(R.id.etObservaciones);
        btnCerrar = view.findViewById(R.id.btnCerrar);
        btnGuardar = view.findViewById(R.id.btnGuardar);

        idSolicitud = getArguments().getString("id_solicitud");

        etNombre.setText(getArguments().getString("nombre"));

        if (!getArguments().getBoolean("is_edit")){
            etNombre.setText(getArguments().getString("nombre"));
            etNombre.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
            etNombre.setEnabled(false);
            tvPlazo.setText(getArguments().getString("plazo"));
            tvPlazo.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked_left));
            tvPeriodicidad.setText(getArguments().getString("periodicidad"));
            tvPeriodicidad.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked_right));
            tvFechaDesembolso.setText(getArguments().getString("fecha_desembolso"));
            tvFechaDesembolso.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
            tvDiaDesembolso.setText(getArguments().getString("dia_desembolso"));
            tvHoraVisita.setText(getArguments().getString("hora_visita"));
            tvHoraVisita.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
            etObservaciones.setText(getArguments().getString("observaciones"));
            etObservaciones.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
            etObservaciones.setEnabled(false);
            if (getArguments().getString("observaciones") == null) {
                tvObservaciones.setVisibility(View.GONE);
                etObservaciones.setVisibility(View.GONE);
            }
            btnGuardar.setVisibility(View.GONE);
            btnCerrar.setVisibility(View.VISIBLE);
        }

        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        setCancelable(false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        btnCerrar.setOnClickListener(btnCerrar_OnClick);
    }

    private View.OnClickListener btnCerrar_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            getDialog().dismiss();
        }
    };
}
