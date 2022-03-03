package com.sidert.sidertmovil.fragments.dialogs;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
//import android.support.v4.app.Fragment;
import androidx.appcompat.app.AlertDialog;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.views.renovacion.RenovacionCreditoGpo;
import com.sidert.sidertmovil.database.DBhelper;
import com.sidert.sidertmovil.utils.Constants;
import com.sidert.sidertmovil.utils.NameFragments;
import com.sidert.sidertmovil.utils.Popups;
import com.sidert.sidertmovil.utils.SessionManager;
import com.sidert.sidertmovil.utils.Validator;
import com.sidert.sidertmovil.utils.ValidatorTextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;

import static com.sidert.sidertmovil.utils.Constants.TBL_CREDITO_GPO_REN;


public class dialog_renovacion_gpo extends DialogFragment {

    public interface OnCompleteListener {
        void onComplete(String plazo, String periodicidad, String fecha, String dia, String hora, String observaciones, Boolean isEdit);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            this.mListener = (dialog_renovacion_gpo.OnCompleteListener)activity;
        }
        catch (final ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnCompleteListener");
        }
    }

    private String[] _plazo;
    private String[] _frecuencia;

    private OnCompleteListener mListener;
    private Context ctx;
    private RenovacionCreditoGpo boostrap;
    private EditText etNombre;
    private TextView tvPlazo;
    private TextView tvPeriodicidad;
    private TextView tvFechaDesembolso;
    private TextView tvDiaDesembolso;
    private TextView tvHoraVisita;
    private EditText etObservaciones;
    private boolean is_edit = true;
    private Button btnGuardar;
    private Button btnCerrar;
    private Calendar myCalendar;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    private DBhelper dBhelper;
    private SQLiteDatabase db;
    private SessionManager session;

    private Validator validator = new Validator();
    private ValidatorTextView validatorTV = new ValidatorTextView();

    private String idSolicitud = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.popup_renovacion_gpo, container, false);
        myCalendar = Calendar.getInstance();
        ctx = getContext();

        dBhelper = new DBhelper(ctx);
        db = dBhelper.getWritableDatabase();

        _plazo = getResources().getStringArray(R.array.intervalo);
        _frecuencia = getResources().getStringArray(R.array.lapso_grupal);

        session = new SessionManager(ctx);
        boostrap = (RenovacionCreditoGpo) getActivity();
        etNombre = view.findViewById(R.id.etNombreGpo);
        tvPlazo = view.findViewById(R.id.tvPlazo);
        tvPeriodicidad = view.findViewById(R.id.tvFrecuencia);
        tvFechaDesembolso = view.findViewById(R.id.tvFechaDesembolso);
        tvDiaDesembolso = view.findViewById(R.id.tvDiaDesembolso);
        tvHoraVisita = view.findViewById(R.id.tvHoraVisita);
        etObservaciones = view.findViewById(R.id.etObservaciones);
        btnCerrar = view.findViewById(R.id.btnCerrar);
        btnGuardar = view.findViewById(R.id.btnGuardar);

        idSolicitud = getArguments().getString("id_solicitud");

        etNombre.setText(getArguments().getString("nombre"));

        //if (!getArguments().getBoolean("is_edit")){
        if (getArguments().containsKey("plazo")){
            //etNombre.setText(getArguments().getString("nombre"));
            tvPlazo.setText(getArguments().getString("plazo"));
            tvPeriodicidad.setText(getArguments().getString("periodicidad"));
            tvFechaDesembolso.setText(getArguments().getString("fecha_desembolso"));
            tvDiaDesembolso.setText(getArguments().getString("dia_desembolso"));
            tvHoraVisita.setText(getArguments().getString("hora_visita"));
            etObservaciones.setText(getArguments().getString("observaciones"));

            //is_edit = false;
            is_edit = getArguments().getBoolean("is_edit");

            if(!is_edit)
            {
                btnGuardar.setVisibility(View.GONE);
                etNombre.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
                etNombre.setEnabled(false);
                tvPlazo.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked_left));
                tvPlazo.setEnabled(false);
                tvPeriodicidad.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked_right));
                tvPeriodicidad.setEnabled(false);
                tvFechaDesembolso.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
                tvFechaDesembolso.setEnabled(false);
                tvHoraVisita.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
                tvHoraVisita.setEnabled(false);
                etObservaciones.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));
                etObservaciones.setEnabled(false);
            }

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

        tvPlazo.setOnClickListener(tvPlazo_OnClick);
        tvPeriodicidad.setOnClickListener(tvPeriodicidad_OnClick);
        tvFechaDesembolso.setOnClickListener(tvFechaDesembolso_OnClik);
        tvHoraVisita.setOnClickListener(tvHoraVisita_OnClik);

        btnGuardar.setOnClickListener(btnGuardar_OnClick);
        btnCerrar.setOnClickListener(btnCerrar_OnClick);
    }

    private View.OnClickListener tvPlazo_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (is_edit) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
                builder.setTitle(R.string.selected_option)
                        .setItems(R.array.intervalo, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int position) {
                                tvPlazo.setError(null);
                                tvPlazo.setText(_plazo[position]);
                            }
                        });
                builder.create();
                builder.show();
            }
        }
    };

    private View.OnClickListener tvPeriodicidad_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (is_edit) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
                builder.setTitle(R.string.selected_option)
                        .setItems(R.array.lapso_grupal, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int position) {
                                tvPeriodicidad.setError(null);
                                tvPeriodicidad.setText(_frecuencia[position]);
                            }
                        });
                builder.create();
                builder.show();
            }
        }
    };

    private View.OnClickListener tvFechaDesembolso_OnClik = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (is_edit){
                dialog_date_picker dialogDatePicker = new dialog_date_picker();
                Bundle b = new Bundle();
                b.putInt(Constants.YEAR_CURRENT, ((tvFechaDesembolso.getText().toString().isEmpty())?myCalendar.get(Calendar.YEAR):Integer.parseInt(tvFechaDesembolso.getText().toString().substring(0,4))));
                b.putInt(Constants.MONTH_CURRENT, ((tvFechaDesembolso.getText().toString().isEmpty())?myCalendar.get(Calendar.MONTH):(Integer.parseInt(tvFechaDesembolso.getText().toString().substring(5,7))-1)));
                b.putInt(Constants.DAY_CURRENT, ((tvFechaDesembolso.getText().toString().isEmpty())?myCalendar.get(Calendar.DAY_OF_MONTH):Integer.parseInt(tvFechaDesembolso.getText().toString().substring(8,10))));
                b.putString(Constants.DATE_CURRENT,sdf.format(myCalendar.getTime()));
                b.putBoolean(Constants.FECHAS_POST, true);
                dialogDatePicker.setArguments(b);
                dialogDatePicker.setTargetFragment(dialog_renovacion_gpo.this,123);
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
                timePicker.setTargetFragment(dialog_renovacion_gpo.this,159);
                timePicker.show(boostrap.getSupportFragmentManager(), NameFragments.DIALOGTIMEPICKER);
            }
        }
    };

    private View.OnClickListener btnGuardar_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (!validator.validate(etNombre, new String[]{validator.REQUIRED}) &&
                    !validatorTV.validate(tvPlazo, new String[]{validatorTV.REQUIRED}) &&
                    !validatorTV.validate(tvPeriodicidad, new String[]{validatorTV.REQUIRED}) &&
                    !validatorTV.validate(tvFechaDesembolso, new String[]{validatorTV.REQUIRED}) &&
                    !validatorTV.validate(tvHoraVisita, new String[]{validatorTV.REQUIRED}) &&
                    !validator.validate(etObservaciones, new String[]{validator.REQUIRED, validator.GENERAL})) {
                AlertDialog guardar_info_dlg = Popups.showDialogConfirm(ctx, Constants.question,
                        R.string.datos_correctos, R.string.yes, new Popups.DialogMessage() {
                            @Override
                            public void OnClickListener(AlertDialog dialog) {
                                saveGrupo();
                                dialog.dismiss();

                            }
                        }, R.string.no, new Popups.DialogMessage() {
                            @Override
                            public void OnClickListener(AlertDialog dialog) {
                                dialog.dismiss();
                            }
                        });
                Objects.requireNonNull(guardar_info_dlg.getWindow()).requestFeature(Window.FEATURE_NO_TITLE);
                guardar_info_dlg.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                guardar_info_dlg.show();
            }
        }
    };

    private View.OnClickListener btnCerrar_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (!is_edit)
                mListener.onComplete(tvPlazo.getText().toString(), tvPeriodicidad.getText().toString(), tvFechaDesembolso.getText().toString().trim(), tvDiaDesembolso.getText().toString(), tvHoraVisita.getText().toString(), etObservaciones.getText().toString(), is_edit);
            else
                mListener.onComplete( null, null, null, null, null, null, null);
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
                        case 4: diaDesembolso = "MIERCOLES";
                            break;
                        case 5: diaDesembolso = "JUEVES";
                            break;
                        case 6: diaDesembolso = "VIERNES";
                            break;
                        case 7: diaDesembolso = "SABADO";
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

    private void saveGrupo (){
        ContentValues cv = new ContentValues();
        cv.put("plazo", tvPlazo.getText().toString());
        cv.put("periodicidad", tvPeriodicidad.getText().toString());
        cv.put("fecha_desembolso", tvFechaDesembolso.getText().toString().trim());
        cv.put("dia_desembolso", tvDiaDesembolso.getText().toString().trim());
        cv.put("hora_visita", tvHoraVisita.getText().toString().trim());
        cv.put("observaciones", etObservaciones.getText().toString().trim().toUpperCase());
        cv.put("estatus_completado", 1);

        db.update(TBL_CREDITO_GPO_REN, cv, "id_solicitud = ?", new String[]{idSolicitud});

        mListener.onComplete(
                tvPlazo.getText().toString(),
                tvPeriodicidad.getText().toString(),
                tvFechaDesembolso.getText().toString().trim(),
                tvDiaDesembolso.getText().toString().trim(),
                tvHoraVisita.getText().toString().trim(),
                etObservaciones.getText().toString().trim().toLowerCase(),
                is_edit
        );
        getDialog().dismiss();

    }
}
