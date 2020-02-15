package com.sidert.sidertmovil.fragments.dialogs;


import android.app.Activity;
import android.content.Context;
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

import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.activities.SolicitudCreditoInd;
import com.sidert.sidertmovil.database.DBhelper;
import com.sidert.sidertmovil.utils.Constants;
import com.sidert.sidertmovil.utils.Miscellaneous;
import com.sidert.sidertmovil.utils.Popups;
import com.sidert.sidertmovil.utils.SessionManager;
import com.sidert.sidertmovil.utils.Validator;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Objects;


public class dialog_registro_cli extends DialogFragment {

    public interface OnCompleteListener {
        void onComplete(long id_solicitud, String nombre, String paterno, String materno);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            this.mListener = (dialog_registro_cli.OnCompleteListener)activity;
        }
        catch (final ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnCompleteListener");
        }
    }

    private OnCompleteListener mListener;
    private Context ctx;
    private SolicitudCreditoInd boostrap;
    private EditText etNombre;
    private EditText etPaterno;
    private EditText etMaterno;
    private Button btnGuardar;
    private Button btnCancelar;
    private Calendar mCalendar;

    private Validator validator;

    private SessionManager session;

    private DBhelper dBhelper;
    private SQLiteDatabase db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.popup_registro_cli, container, false);
        mCalendar   = Calendar.getInstance();

        ctx         = getContext();

        session     = new SessionManager(ctx);
        validator   = new Validator();
        dBhelper    = new DBhelper(ctx);
        db          = dBhelper.getWritableDatabase();

        boostrap    = (SolicitudCreditoInd) getActivity();

        etNombre    = view.findViewById(R.id.etNombre);
        etPaterno   = view.findViewById(R.id.etPaterno);
        etMaterno   = view.findViewById(R.id.etMaterno);
        btnGuardar  = view.findViewById(R.id.btnGuardar);
        btnCancelar = view.findViewById(R.id.btnCancelar);

        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        setCancelable(false);

        btnGuardar.setOnClickListener(btnGuardar_OnClick);
        btnCancelar.setOnClickListener(btnCancelar_OnClick);
        return view;
    }

    private View.OnClickListener btnGuardar_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (!validator.validate(etNombre, new String[]{validator.REQUIRED, validator.ONLY_TEXT}) &&
                !validator.validate(etPaterno, new String[]{validator.REQUIRED, validator.ONLY_TEXT}) &&
                !validator.validate(etMaterno, new String[]{validator.ONLY_TEXT})){
                AlertDialog guardar_info_dlg = Popups.showDialogConfirm(ctx, Constants.question,
                        R.string.datos_correctos, R.string.yes, new Popups.DialogMessage() {
                            @Override
                            public void OnClickListener(AlertDialog dialog) {
                                saveCliente();
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

    private void saveCliente(){
        HashMap<Integer, String> params = new HashMap<>();
        params.put(0,session.getUser().get(0));
        params.put(1,"1");
        params.put(2,"1");
        params.put(3,"0");
        params.put(4,"");
        params.put(5,etNombre.getText().toString().trim().toUpperCase());
        params.put(6,etPaterno.getText().toString().trim().toUpperCase());
        params.put(7,etMaterno.getText().toString().trim().toUpperCase());
        params.put(8,"");
        params.put(9,"");
        params.put(10, Miscellaneous.ObtenerFecha("timestamp"));
        params.put(11,"");
        long id = dBhelper.saveSolicitudes(db, "solicitudes_t",params);

        //Inserta registro de datos del credito
        params = new HashMap<>();
        params.put(0,String.valueOf(id));
        params.put(1,"0");
        params.put(2,"0");
        params.put(3,"");
        params.put(4,"");
        params.put(5,"");
        params.put(6,"");
        params.put(7,"0");
        params.put(8,"0");
        if (Constants.ENVIROMENT)
            Log.e("Produccion", "Registra en produccion");
            //dBhelper.saveDatosCredito(db, Constants.DATOS_CREDITO_IND, params);
        else
            dBhelper.saveDatosCredito(db, Constants.DATOS_CREDITO_IND_T, params);

        //Inserta registro de datos del cliente
        params = new HashMap<>();
        params.put(0, String.valueOf(id));
        params.put(1, etNombre.getText().toString().trim().toUpperCase());
        params.put(2, etPaterno.getText().toString().trim().toUpperCase());
        params.put(3, etMaterno.getText().toString().trim().toUpperCase());
        params.put(4, ""); params.put(5, ""); params.put(6, "2"); params.put(7, "");
        params.put(8, ""); params.put(9, ""); params.put(10, ""); params.put(11, "");
        params.put(12, ""); params.put(13, ""); params.put(14, "0"); params.put(15, "");
        params.put(16, "0"); params.put(17, "0"); params.put(18, "0"); params.put(19, "0");
        params.put(20, "0"); params.put(21, ""); params.put(22, ""); params.put(23, "");
        params.put(24, ""); params.put(25, ""); params.put(26, ""); params.put(27, "");
        params.put(28, ""); params.put(29, ""); params.put(30, ""); params.put(31, "");
        params.put(32, ""); params.put(33, ""); params.put(34, ""); params.put(35, "0");
        params.put(36, "0"); params.put(37, "0"); params.put(38, ""); params.put(39, "");
        params.put(40, ""); params.put(41, ""); params.put(42, "0"); params.put(43, "");
        params.put(44, "0");
        if (Constants.ENVIROMENT)
            Log.e("Produccion", "Registra en produccion");
            //dBhelper.saveDatosPersonales(db, Constants.DATOS_CLIENTE_IND, params);
        else
            dBhelper.saveDatosPersonales(db, Constants.DATOS_CLIENTE_IND_T, params);

        //Inserta registro de datos conyuge
        params = new HashMap<>();
        params.put(0, String.valueOf(id));
        params.put(1, "");
        params.put(2, "");
        params.put(3, "");
        params.put(4, "");
        params.put(5, "");
        params.put(6, "0");
        if (Constants.ENVIROMENT)
            Log.e("Produccion", "Registra en produccion");
            //dBhelper.saveDatosConyuge(db, Constants.DATOS_CONYUGE_IND, params);
        else
            dBhelper.saveDatosConyuge(db, Constants.DATOS_CONYUGE_IND_T, params);

        //Inserta registro de datos economicos
        params = new HashMap<>();
        params.put(0, String.valueOf(id));
        params.put(1, "");
        params.put(2, "");
        params.put(3, "");
        params.put(4, "");
        params.put(5, "0");
        if (Constants.ENVIROMENT)
            Log.e("Produccion", "Registra en produccion");
            //dBhelper.saveDatosEconomicos(db, Constants.DATOS_ECONOMICOS_IND, params);
        else
            dBhelper.saveDatosEconomicos(db, Constants.DATOS_ECONOMICOS_IND_T, params);

        //Inserta registro de negocio
        params = new HashMap<>();
        params.put(0, String.valueOf(id)); params.put(1, ""); params.put(2, "");
        params.put(3, ""); params.put(4, ""); params.put(5, ""); params.put(6, "");
        params.put(7, ""); params.put(8, ""); params.put(9, ""); params.put(10, "");
        params.put(11, ""); params.put(12,"0"); params.put(13,""); params.put(14,"");
        params.put(15,""); params.put(16,""); params.put(17,""); params.put(18,"");
        params.put(19,""); params.put(20,""); params.put(21,""); params.put(22,"");
        params.put(23,""); params.put(24,""); params.put(25,"0");
        if (Constants.ENVIROMENT)
            Log.e("Produccion", "Registra en produccion");
            //dBhelper.saveDatosNegocio(db, Constants.DATOS_NEGOCIO_IND, params);
        else
            dBhelper.saveDatosNegocio(db, Constants.DATOS_NEGOCIO_IND_T, params);

        //Inserta registro del aval
        params = new HashMap<>();
        params.put(0, String.valueOf(id)); params.put(1, ""); params.put(2, "");
        params.put(3, "");params.put(4, ""); params.put(5, ""); params.put(6, "2");
        params.put(7, ""); params.put(8, ""); params.put(9, ""); params.put(10, "");
        params.put(11, ""); params.put(12, "0"); params.put(13, ""); params.put(14, "");
        params.put(15, ""); params.put(16, ""); params.put(17, ""); params.put(18, "");
        params.put(19, ""); params.put(20, ""); params.put(21, ""); params.put(22, "");
        params.put(23, ""); params.put(24, "");params.put(25, "0");params.put(26, "");
        params.put(27, "0"); params.put(28, ""); params.put(29, ""); params.put(30, "");
        params.put(31, ""); params.put(32, ""); params.put(33, ""); params.put(34, "");
        params.put(35, ""); params.put(36, ""); params.put(37, "0"); params.put(38, "");
        params.put(39, ""); params.put(40, ""); params.put(41, ""); params.put(42, "");
        params.put(43, "0"); params.put(44, ""); params.put(45, "0");
        if (Constants.ENVIROMENT)
            Log.e("Produccion", "Registra en produccion");
            //dBhelper.saveDatosAval(db, Constants.DATOS_AVAL_IND, params);
        else
            dBhelper.saveDatosAval(db, Constants.DATOS_AVAL_IND_T, params);

        //Inserta registro de referencia
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
        params.put(9, "0");
        if (Constants.ENVIROMENT)
            Log.e("Produccion", "Registra en produccion");
            //dBhelper.saveReferencia(db, Constants.DATOS_REFERENCIA_IND, params);
        else
            dBhelper.saveReferencia(db, Constants.DATOS_REFERENCIA_IND_T, params);

        mListener.onComplete(id,etNombre.getText().toString().trim().toUpperCase(),etPaterno.getText().toString().trim().toUpperCase(),etMaterno.getText().toString().trim().toUpperCase());
        getDialog().dismiss();
    }

    private View.OnClickListener btnCancelar_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mListener.onComplete(0, null, null, null);
            getDialog().dismiss();
        }
    };

}
