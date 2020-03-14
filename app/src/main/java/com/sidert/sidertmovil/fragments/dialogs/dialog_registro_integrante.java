package com.sidert.sidertmovil.fragments.dialogs;


import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.activities.AgregarIntegrante;
import com.sidert.sidertmovil.activities.SolicitudCreditoInd;
import com.sidert.sidertmovil.database.DBhelper;
import com.sidert.sidertmovil.utils.Constants;
import com.sidert.sidertmovil.utils.Miscellaneous;
import com.sidert.sidertmovil.utils.NameFragments;
import com.sidert.sidertmovil.utils.Popups;
import com.sidert.sidertmovil.utils.SessionManager;
import com.sidert.sidertmovil.utils.Validator;
import com.sidert.sidertmovil.utils.ValidatorTextView;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Objects;

import static com.sidert.sidertmovil.utils.Constants.CONYUGE_INTEGRANTE;
import static com.sidert.sidertmovil.utils.Constants.CONYUGE_INTEGRANTE_T;
import static com.sidert.sidertmovil.utils.Constants.DATOS_INTEGRANTES_GPO;
import static com.sidert.sidertmovil.utils.Constants.DATOS_INTEGRANTES_GPO_T;
import static com.sidert.sidertmovil.utils.Constants.DOCUMENTOS_INTEGRANTE;
import static com.sidert.sidertmovil.utils.Constants.DOCUMENTOS_INTEGRANTE_T;
import static com.sidert.sidertmovil.utils.Constants.DOMICILIO_INTEGRANTE;
import static com.sidert.sidertmovil.utils.Constants.DOMICILIO_INTEGRANTE_T;
import static com.sidert.sidertmovil.utils.Constants.ENVIROMENT;
import static com.sidert.sidertmovil.utils.Constants.NEGOCIO_INTEGRANTE;
import static com.sidert.sidertmovil.utils.Constants.NEGOCIO_INTEGRANTE_T;
import static com.sidert.sidertmovil.utils.Constants.OTROS_DATOS_INTEGRANTE;
import static com.sidert.sidertmovil.utils.Constants.OTROS_DATOS_INTEGRANTE_T;
import static com.sidert.sidertmovil.utils.Constants.TELEFONOS_INTEGRANTE;
import static com.sidert.sidertmovil.utils.Constants.TELEFONOS_INTEGRANTE_T;


public class dialog_registro_integrante extends DialogFragment {

    public interface OnCompleteListener {
        void onComplete(long id_integrante, String nombre, String paterno, String materno, String cargo);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            this.mListener = (dialog_registro_integrante.OnCompleteListener)activity;
        }
        catch (final ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnCompleteListener");
        }
    }

    private OnCompleteListener mListener;
    private Context ctx;
    private AgregarIntegrante boostrap;
    private EditText etNombre;
    private EditText etPaterno;
    private EditText etMaterno;
    private TextView tvCargo;
    private Button btnGuardar;
    private Button btnCancelar;
    private Calendar mCalendar;

    private Validator validator;
    private ValidatorTextView validatorTV;

    private SessionManager session;

    private DBhelper dBhelper;
    private SQLiteDatabase db;

    private int id_cargo = 0;

    private int id_credito = 0;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.popup_registro_integrante, container, false);

        ctx         = getContext();

        session     = new SessionManager(ctx);
        validator   = new Validator();
        validatorTV = new ValidatorTextView();
        dBhelper    = new DBhelper(ctx);
        db          = dBhelper.getWritableDatabase();

        boostrap    = (AgregarIntegrante) getActivity();

        etNombre    = view.findViewById(R.id.etNombre);
        etPaterno   = view.findViewById(R.id.etPaterno);
        etMaterno   = view.findViewById(R.id.etMaterno);
        tvCargo     = view.findViewById(R.id.tvCargo);
        btnGuardar  = view.findViewById(R.id.btnGuardar);
        btnCancelar = view.findViewById(R.id.btnCancelar);

        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        setCancelable(false);

        id_credito = Integer.parseInt(getArguments().getString("id_credito"));

        tvCargo.setOnClickListener(tvCargo_OnClick);
        btnGuardar.setOnClickListener(btnGuardar_OnClick);
        btnCancelar.setOnClickListener(btnCancelar_OnClick);

        return view;
    }

    private View.OnClickListener tvCargo_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            dialog_cargo_integrante dlg_cargo = new dialog_cargo_integrante();
            Bundle b = new Bundle();
            Cursor row = dBhelper.getCargoGrupo(String.valueOf(id_credito));
            Log.e("cargo", String.valueOf(row.getCount())+";");
            if (row.getCount() > 0){
                row.moveToFirst();
                b.putBoolean("presidente", true);
                b.putBoolean("tesorero", true);
                b.putBoolean("secretario", true);
                b.putBoolean("integrante", true);
                for (int i = 0; i < row.getCount(); i++){
                    switch (row.getInt(0)){
                        case 1:
                            b.putBoolean("presidente", false);
                            break;
                        case 2:
                            b.putBoolean("tesorero", false);
                            break;
                        case 3:
                            b.putBoolean("secretario", false);
                            break;
                    }
                    row.moveToNext();
                }
                row.close();
                Cursor r_integrantes = dBhelper.customSelect(DATOS_INTEGRANTES_GPO_T, "COUNT(cargo)", " WHERE id_credito = ? AND cargo = ?", "", new String[]{String.valueOf(id_credito), "4"});
                if (r_integrantes.getCount() > 0){
                    r_integrantes.moveToFirst();
                    if (r_integrantes.getInt(0) == 37)
                        b.putBoolean("integrante", false);

                }
                r_integrantes.close();
            }
            else{
                b.putBoolean("presidente", true);
                b.putBoolean("tesorero", true);
                b.putBoolean("secretario", true);
                b.putBoolean("integrante", true);
            }
            dlg_cargo.setArguments(b);
            dlg_cargo.setTargetFragment(dialog_registro_integrante.this, 8652);
            dlg_cargo.show(boostrap.getSupportFragmentManager(), NameFragments.DIALOGCARGOINTEGRANTE);
        }
    };

    private View.OnClickListener btnGuardar_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (!validator.validate(etNombre, new String[]{validator.REQUIRED, validator.ONLY_TEXT}) &&
                    !validator.validate(etPaterno, new String[]{validator.REQUIRED, validator.ONLY_TEXT}) &&
                    !validator.validate(etMaterno, new String[]{validator.ONLY_TEXT}) &&
                    !validatorTV.validate(tvCargo, new String[]{validatorTV.REQUIRED})){
                AlertDialog guardar_info_dlg = Popups.showDialogConfirm(ctx, Constants.question,
                        R.string.datos_correctos, R.string.yes, new Popups.DialogMessage() {
                            @Override
                            public void OnClickListener(AlertDialog dialog) {
                                saveIntegrante();
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

    private void saveIntegrante(){
        long id = 0;
        //Inserta registro de integrante
        HashMap<Integer, String> params = new HashMap<>();
        params.put(0, String.valueOf(id_credito));
        params.put(1, String.valueOf(id_cargo));
        params.put(2, etNombre.getText().toString().trim().toUpperCase());
        params.put(3, etPaterno.getText().toString().trim().toUpperCase());
        params.put(4, etMaterno.getText().toString().trim().toUpperCase());
        params.put(5, "");
        params.put(6, "");
        params.put(7, "2");
        params.put(8, "");
        params.put(9, "");
        params.put(10, "");
        params.put(11, "");
        params.put(12, "");
        params.put(13, "");
        params.put(14, "");
        params.put(15, "");
        params.put(16, "0");
        params.put(17, "0");
        params.put(18, "");
        params.put(19, "0");

        if (ENVIROMENT)
            id = dBhelper.saveIntegrantesGpo(db, DATOS_INTEGRANTES_GPO, params);
        else
            id = dBhelper.saveIntegrantesGpo(db, DATOS_INTEGRANTES_GPO_T, params);

        //Inserta registro de datos telefonicos
        params = new HashMap<>();
        params.put(0, String.valueOf(id)); params.put(1, ""); params.put(2, "");
        params.put(3, "");params.put(4, "0");
        if (ENVIROMENT)
            dBhelper.saveDatosTelefonicos(db, TELEFONOS_INTEGRANTE, params);
        else
            dBhelper.saveDatosTelefonicos(db, TELEFONOS_INTEGRANTE_T, params);

        //Inserta registro de datos domicilio
        params = new HashMap<>();
        params.put(0, String.valueOf(id)); params.put(1, ""); params.put(2, "");
        params.put(3, ""); params.put(4, ""); params.put(5, ""); params.put(6, "");
        params.put(7, ""); params.put(8, ""); params.put(9, ""); params.put(10, "");
        params.put(11, ""); params.put(12, ""); params.put(13, ""); params.put(14, "");
        params.put(15, ""); params.put(16, "0");
        if (ENVIROMENT)
            dBhelper.saveDatosDomicilio(db, DOMICILIO_INTEGRANTE, params);
        else
            dBhelper.saveDatosDomicilio(db, DOMICILIO_INTEGRANTE_T, params);

        //Inserta registro de negocio
        params = new HashMap<>();
        params.put(0, String.valueOf(id));
        params.put(1, "");
        params.put(2, "");
        params.put(3, "");
        params.put(4, "");
        params.put(5, "");
        params.put(6, "");
        params.put(7, "");
        params.put(8, "");
        params.put(9, "");
        params.put(10, "");
        params.put(11, "");
        params.put(12, "");
        params.put(13,"");
        params.put(14,"");
        params.put(15,"");
        params.put(16,"");
        params.put(17,"");
        params.put(18,"");
        params.put(19,"0");
        params.put(20,"");
        params.put(21,"0");
        if (ENVIROMENT)
            dBhelper.saveDatosNegocioGpo(db, NEGOCIO_INTEGRANTE, params);
        else
            dBhelper.saveDatosNegocioGpo(db, NEGOCIO_INTEGRANTE_T, params);

        //Inserta registro del conyuge
        params = new HashMap<>();
        params.put(0, String.valueOf(id)); params.put(1, ""); params.put(2, "");
        params.put(3, "");params.put(4, ""); params.put(5, ""); params.put(6, "");
        params.put(7, "0");
        if (ENVIROMENT)
            dBhelper.saveDatosConyugeGpo(db, CONYUGE_INTEGRANTE, params);
        else
            dBhelper.saveDatosConyugeGpo(db, CONYUGE_INTEGRANTE_T, params);

        //Inserta otros datos del integrante
        params = new HashMap<>();
        params.put(0, String.valueOf(id)); params.put(1, ""); params.put(2, "");
        params.put(3, "0"); params.put(4, ""); params.put(5, "0"); params.put(6, "");
        params.put(7, "0");
        if (ENVIROMENT)
            dBhelper.saveDatosOtrosGpo(db, OTROS_DATOS_INTEGRANTE, params);
        else
            dBhelper.saveDatosOtrosGpo(db, OTROS_DATOS_INTEGRANTE_T, params);

        //Inserta registro de documentos de integrante
        params = new HashMap<>();
        params.put(0, String.valueOf(id));
        params.put(1, "");
        params.put(2, "");
        params.put(3, "");
        params.put(4, "");
        params.put(5, "0");
        if (ENVIROMENT)
            dBhelper.saveDocumentosIntegrante(db, DOCUMENTOS_INTEGRANTE, params);
        else
            dBhelper.saveDocumentosIntegrante(db, DOCUMENTOS_INTEGRANTE_T, params);

        mListener.onComplete(id,etNombre.getText().toString().trim().toUpperCase(),etPaterno.getText().toString().trim().toUpperCase(),etMaterno.getText().toString().trim().toUpperCase(), tvCargo.getText().toString());
        getDialog().dismiss();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 8652:
                if (resultCode == 2658){
                    if (data != null){
                        id_cargo = data.getIntExtra("id_cargo",0);
                        tvCargo.setText(data.getStringExtra("cargo"));
                    }
                }
                break;
        }
    }

    private View.OnClickListener btnCancelar_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mListener.onComplete(0, null, null, null, null);
            getDialog().dismiss();
        }
    };

}
