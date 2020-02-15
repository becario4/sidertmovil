package com.sidert.sidertmovil.fragments.dialogs;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.activities.SolicitudCreditoGpo;
import com.sidert.sidertmovil.utils.Constants;
import com.sidert.sidertmovil.utils.NameFragments;

import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class dialog_originacion_gpo extends DialogFragment {

    public interface OnCompleteListener {
        void onComplete(String nombre, int plazo, int periodicidad, String fecha, String dia, String hora);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            this.mListener = (dialog_originacion_gpo.OnCompleteListener)activity;
        }
        catch (final ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnCompleteListener");
        }
    }

    private OnCompleteListener mListener;
    private Context ctx;
    private SolicitudCreditoGpo boostrap;
    private EditText etNombre;
    private Spinner spPlazo;
    private Spinner spPeriodicidad;
    private TextView tvFechaDesembolso;
    private TextView tvDiaDesembolso;
    private TextView tvHoraVisita;
    private boolean is_edit = true;
    private Button btnGuardar;
    private Button btnCerrar;
    private Calendar myCalendar;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.popup_originacion_gpo, container, false);

        myCalendar = Calendar.getInstance();
        ctx = getContext();
        boostrap = (SolicitudCreditoGpo) getActivity();
        etNombre = view.findViewById(R.id.etNombreGpo);
        spPlazo = view.findViewById(R.id.spPlazo);
        spPeriodicidad = view.findViewById(R.id.spFrecuencia);
        tvFechaDesembolso = view.findViewById(R.id.tvFechaDesembolso);
        tvDiaDesembolso = view.findViewById(R.id.tvDiaDesembolso);
        tvHoraVisita = view.findViewById(R.id.tvHoraVisita);
        btnCerrar = view.findViewById(R.id.btnCerrar);
        btnGuardar = view.findViewById(R.id.btnGuardar);

        if (!getArguments().getBoolean("is_edit")){
            etNombre.setText(getArguments().getString("nombre"));
            etNombre.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
            etNombre.setFocusable(false);
            spPlazo.setSelection(getArguments().getInt("plazo"));
            spPlazo.setEnabled(false);
            spPeriodicidad.setSelection(getArguments().getInt("periodicidad"));
            spPeriodicidad.setEnabled(false);
            tvFechaDesembolso.setText(getArguments().getString("fecha_desembolso"));
            tvFechaDesembolso.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
            tvDiaDesembolso.setText(getArguments().getString("dia_desembolso"));
            tvHoraVisita.setText(getArguments().getString("hora_visita"));
            tvHoraVisita.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
            is_edit = false;
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

        tvFechaDesembolso.setOnClickListener(tvFechaDesembolso_OnClik);
        tvHoraVisita.setOnClickListener(tvHoraVisita_OnClik);

        btnGuardar.setOnClickListener(btnGuardar_OnClick);
        btnCerrar.setOnClickListener(btnCerrar_OnClick);
    }

    private View.OnClickListener tvFechaDesembolso_OnClik = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (is_edit){
                dialog_date_picker dialogDatePicker = new dialog_date_picker();
                Bundle b = new Bundle();
                b.putInt(Constants.YEAR_CURRENT,myCalendar.get(Calendar.YEAR));
                b.putInt(Constants.MONTH_CURRENT,myCalendar.get(Calendar.MONTH));
                b.putInt(Constants.DAY_CURRENT,myCalendar.get(Calendar.DAY_OF_MONTH));
                b.putString(Constants.DATE_CURRENT,sdf.format(myCalendar.getTime()));
                b.putBoolean(Constants.FECHAS_POST, true);
                dialogDatePicker.setArguments(b);
                dialogDatePicker.setTargetFragment(dialog_originacion_gpo.this,123);
                dialogDatePicker.show(boostrap.getSupportFragmentManager(), NameFragments.DIALOGDATEPICKER);
            }
        }
    };

    private View.OnClickListener tvHoraVisita_OnClik = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (is_edit){
                dialog_time_picker timePicker = new dialog_time_picker();
                Bundle b = new Bundle();
                b.putInt(Constants.IDENTIFIER,3);
                timePicker.setArguments(b);
                timePicker.setTargetFragment(dialog_originacion_gpo.this,159);
                timePicker.show(boostrap.getSupportFragmentManager(), NameFragments.DIALOGTIMEPICKER);
            }
        }
    };

    private View.OnClickListener btnGuardar_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (!etNombre.getText().toString().trim().isEmpty()){
                if (spPlazo.getSelectedItemPosition() > 0){
                    if (spPeriodicidad.getSelectedItemPosition() > 0){
                        if (!tvFechaDesembolso.getText().toString().trim().isEmpty()){
                            if (!tvHoraVisita.getText().toString().trim().isEmpty()){
                                mListener.onComplete(etNombre.getText().toString().trim(),
                                        spPlazo.getSelectedItemPosition(),
                                        spPeriodicidad.getSelectedItemPosition(),
                                        tvFechaDesembolso.getText().toString().trim(),
                                        tvDiaDesembolso.getText().toString().trim(),
                                        tvHoraVisita.getText().toString().trim());
                                getDialog().dismiss();
                            }
                            else
                                tvHoraVisita.setError("Este campo es requerido");
                        }
                        else
                            tvFechaDesembolso.setError("Este campo es requerido");
                    }
                    else
                        Toast.makeText(ctx, "Seleccione la periodicidad del crédito", Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(ctx, "Seleccione el plazo del crédito", Toast.LENGTH_SHORT).show();
            }
            else
                etNombre.setError("Este campo es requerido");
        }
    };

    private View.OnClickListener btnCerrar_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            getDialog().dismiss();
        }
    };

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (123  == requestCode){
            if (resultCode == 321){
                if (data != null){
                    tvFechaDesembolso.setText(data.getStringExtra("fecha"));
                    tvFechaDesembolso.setError(null);
                    String[] fechaDes = tvFechaDesembolso.getText().toString().split("-");
                    Calendar c = Calendar.getInstance();

                    c.set(Integer.valueOf(fechaDes[0]), (Integer.valueOf(fechaDes[1]) - 1), Integer.valueOf(fechaDes[2]));
                    int nD=c.get(Calendar.DAY_OF_WEEK);
                    String diaDesembolso = "";
                    switch (nD){
                        case 1: diaDesembolso = "DOMINGO";
                            break;
                        case 2: diaDesembolso = "LUNES";
                            break;
                        case 3: diaDesembolso = "MARTES";
                            break;
                        case 4: diaDesembolso = "MIÉRCOLES";
                            break;
                        case 5: diaDesembolso = "JUEVES";
                            break;
                        case 6: diaDesembolso = "VIERNES";
                            break;
                        case 7: diaDesembolso = "SÁBADO";
                            break;
                    }
                    tvDiaDesembolso.setText(diaDesembolso);
                }
            }
        }
        else if (159 == requestCode){
            if (resultCode == 753){
                if (data != null){
                    tvHoraVisita.setError(null);
                    tvHoraVisita.setText(data.getStringExtra("hora"));
                }
            }
        }
    }
}
