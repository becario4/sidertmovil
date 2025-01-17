package com.sidert.sidertmovil.fragments.dialogs;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.database.DBhelper;
import com.sidert.sidertmovil.database.EntitiesCommonsContants;
import com.sidert.sidertmovil.database.entities.Beneficiario;
import com.sidert.sidertmovil.database.entities.SolicitudCampana;
import com.sidert.sidertmovil.utils.Constants;
import com.sidert.sidertmovil.utils.NameFragments;
import com.sidert.sidertmovil.utils.Popups;
import com.sidert.sidertmovil.utils.SessionManager;
import com.sidert.sidertmovil.utils.Validator;
import com.sidert.sidertmovil.utils.ValidatorTextView;
import com.sidert.sidertmovil.views.renovacion.RenovarIntegrante;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Objects;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import static com.sidert.sidertmovil.utils.Constants.TBL_INTEGRANTES_GPO_REN;

public class dialog_renovar_integrante extends DialogFragment {

    public interface OnCompleteListener {
        void onComplete(long id_integrante, String nombre, String paterno, String materno, String cargo, SolicitudCampana solicitudCampana, Beneficiario beneficiario);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            this.mListener = (dialog_renovar_integrante.OnCompleteListener) activity;
        } catch (final ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnCompleteListener");
        }
    }

    private OnCompleteListener mListener;
    private Context ctx;
    private RenovarIntegrante boostrap;
    private EditText etNombre;
    private EditText etPaterno;
    private EditText etMaterno;
    private TextView tvCargo;
    private Button btnGuardar;
    private Button btnCancelar;
    private Validator validator;
    private ValidatorTextView validatorTV;
    private DBhelper dBhelper;
    private SQLiteDatabase db;

    private int id_cargo = 0;
    private int id_credito = 0;
    private int id_integrante = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.popup_renovar_integrante, container, false);

        ctx = getContext();

        validator = new Validator();
        validatorTV = new ValidatorTextView();
        dBhelper = DBhelper.getInstance(ctx);
        db = dBhelper.getWritableDatabase();

        boostrap = (RenovarIntegrante) getActivity();

        etNombre = view.findViewById(R.id.etNombre);
        etPaterno = view.findViewById(R.id.etPaterno);
        etMaterno = view.findViewById(R.id.etMaterno);
        tvCargo = view.findViewById(R.id.tvCargo);
        btnGuardar = view.findViewById(R.id.btnGuardar);
        btnCancelar = view.findViewById(R.id.btnCancelar);

        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        setCancelable(false);

        id_credito = Integer.parseInt(getArguments().getString("id_credito"));

        String nombre = getArguments().getString("nombre");
        String paterno = getArguments().getString("paterno");
        String materno = getArguments().getString("materno");
        String cargo = getArguments().getString("cargo");

        if (nombre != null && !nombre.isEmpty()) {
            id_integrante = Integer.parseInt(getArguments().getString("id_integrante"));

            etNombre.setText(nombre);
            etPaterno.setText(paterno);
            etMaterno.setText(materno);
            tvCargo.setText(cargo);

            etNombre.setEnabled(false);
            etPaterno.setEnabled(false);
            etMaterno.setEnabled(false);
        }

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
            Cursor row = dBhelper.getCargoGrupo(String.valueOf(id_credito), 2);
            Log.e("cargo", String.valueOf(row.getCount()) + ";");
            if (row.getCount() > 0) {
                row.moveToFirst();
                b.putBoolean("presidente", true);
                b.putBoolean("tesorero", true);
                b.putBoolean("secretario", true);
                b.putBoolean("integrante", true);
                for (int i = 0; i < row.getCount(); i++) {
                    switch (row.getInt(0)) {
                        case 1:
                            b.putBoolean("presidente", false);
                            break;
                        case 3:
                            b.putBoolean("tesorero", false);
                            break;
                        case 2:
                            b.putBoolean("secretario", false);
                            break;
                    }
                    row.moveToNext();
                }
                row.close();

                Cursor r_integrantes = dBhelper.customSelect(TBL_INTEGRANTES_GPO_REN, "COUNT(cargo)", " WHERE id_credito = ? AND cargo = ?", "", new String[]{String.valueOf(id_credito), "4"});
                if (r_integrantes.getCount() > 0) {
                    r_integrantes.moveToFirst();
                    if (r_integrantes.getInt(0) == 37)
                        b.putBoolean("integrante", false);

                }
                r_integrantes.close();
            } else {
                b.putBoolean("presidente", true);
                b.putBoolean("tesorero", true);
                b.putBoolean("secretario", true);
                b.putBoolean("integrante", true);
            }
            dlg_cargo.setArguments(b);
            dlg_cargo.setTargetFragment(dialog_renovar_integrante.this, 8652);
            dlg_cargo.show(boostrap.getSupportFragmentManager(), NameFragments.DIALOGCARGOINTEGRANTE);
        }
    };

    private View.OnClickListener btnGuardar_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (!validator.validate(etNombre, new String[]{validator.REQUIRED, validator.ONLY_TEXT}) &&
                    !validator.validate(etPaterno, new String[]{validator.ONLY_TEXT}) &&
                    !validator.validate(etMaterno, new String[]{validator.ONLY_TEXT}) &&
                    !validatorTV.validate(tvCargo, new String[]{validatorTV.REQUIRED})) {
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

    private void saveIntegrante() {

        int tipo = 2;

        if (id_integrante == 0) {
            //Inserta registro de integrante
            HashMap<Integer, String> params = new HashMap<>();
            params.put(0, String.valueOf(id_credito));                              //ID CREDITO
            params.put(1, String.valueOf(id_cargo));                                //CARGO
            params.put(2, etNombre.getText().toString().trim().toUpperCase());      //NOMBRE(S)
            params.put(3, etPaterno.getText().toString().trim().toUpperCase());     //PATERNO
            params.put(4, etMaterno.getText().toString().trim().toUpperCase());     //MATERNO
            params.put(5, "");                                                      //FECHA NACIMIENTO
            params.put(6, "");                                                      //EDAD
            params.put(7, "2");                                                     //GENERO
            params.put(8, "");                                                      //ESTADO NACIMIENTO
            params.put(9, "");                                                      //RFC
            params.put(10, "");                                                     //CURP
            params.put(11, "");                                                     //CURP DIGITO VERI
            params.put(12, "");                                                     //TIPO IDENTIFICACION
            params.put(13, "");                                                     //NO IDENTIFICACION
            params.put(14, "");                                                     //NIVEL ESTUDIO
            params.put(15, "");                                                     //OCUPACION
            params.put(16, "");                                                     //ESTADO CIVIL
            params.put(17, "0");                                                    //BIENES
            params.put(18, "0");                                                    //ESTATUS RECHAZO
            params.put(19, "");                                                     //COMENTARIO RECHAZO
            params.put(20, "0");                                                    //ESTATUS COMPLETO
            params.put(21, "0");                                                    //ID SOLICITUD INTEGRANTE
            params.put(22, "1");                                                    //IS NUEVO
            params.put(23, "");                                                     //CLIENTE ID
            params.put(24, "0");                                                     //CLIENTE ID
            params.put(25, "0.00");                                                     //CLIENTE ID

            id_integrante = dBhelper.saveIntegrantesGpoRen(db, params).intValue();

            //Inserta registro de datos telefonicos
            params = new HashMap<>();
            params.put(0, String.valueOf(id_integrante));              //ID INTEGRANTE
            params.put(1, "");                              //TEL CASA
            params.put(2, "");                              //TEL CELULAR
            params.put(3, "");                              //TEL MENSAJES
            params.put(4, "");                              //TEL TRABAJO
            params.put(5, "0");                             //ESTATUS COMPLETADO

            dBhelper.saveDatosTelefonicos(db, params, tipo);

            //Inserta registro de datos domicilio
            params = new HashMap<>();
            params.put(0, String.valueOf(id_integrante));          //ID INTEGRANTE
            params.put(1, "");                          //LATITUD
            params.put(2, "");                          //LONGITUD
            params.put(3, "");                          //CALLE
            params.put(4, "");                          //NO_EXTERIOR
            params.put(5, "");                          //NO INTERIOR
            params.put(6, "");                          //MANZANA
            params.put(7, "");                          //LOTE
            params.put(8, "");                          //CP
            params.put(9, "");                          //COLONIA
            params.put(10, "");                         //CIUDAD
            params.put(11, "");                         //LOCALIDAD
            params.put(12, "");                         //MUNICIPIO
            params.put(13, "");                         //ESTADO
            params.put(14, "");                         //TIPO VIVIENDA
            params.put(15, "");                         //PARENTESCO
            params.put(16, "");                         //OTRO TIPO VIVIENDA
            params.put(17, "");                         //TIEMPO VIVIR SITIO
            params.put(18, "");                         //FOTO FACHADA
            params.put(19, "");                         //REF DOMICILIARIA
            params.put(20, "0");                        //ESTATUS COMPLETO
            params.put(21, "");                         //DEPENDIENTES ECONOMICOS

            dBhelper.saveDatosDomicilio(db, params, tipo);

            //Inserta registro de negocio
            params = new HashMap<>();
            params.put(0, String.valueOf(id_integrante));          //ID INTEGRANTE
            params.put(1, "");                          //NOMBRE
            params.put(2, "");                          //LATITID
            params.put(3, "");                          //LONGITUD
            params.put(4, "");                          //CALLE
            params.put(5, "");                          //NO EXTERIOR
            params.put(6, "");                          //NO INTERIOR
            params.put(7, "");                          //MANZANA
            params.put(8, "");                          //LOTE
            params.put(9, "");                          //CP
            params.put(10, "");                         //COLONIA
            params.put(11, "");                         //CIUDAD
            params.put(12, "");                         //LOCALIDAD
            params.put(13, "");                          //MUNICIPIO
            params.put(14, "");                          //ESTADO
            params.put(15, "");                          //DESTINO CREDITO
            params.put(16, "");                          //OTRO DESTINO CREDITO
            params.put(17, "");                          //OCUPACION
            params.put(18, "");                          //ACTIVIDAD ECONOMICA
            params.put(19, "");                          //ANTIGUEDA
            params.put(20, "");                          //INGRESO MENSUAL
            params.put(21, "");                          //INGRESOS OTROS
            params.put(22, "");                          //GASTO MENSUAL
            params.put(23, "");                          //CAPACIDAD DE PAGO
            params.put(24, "");                          //MONTO MAXIMO
            params.put(25, "");                          //MEDIOS PAGO
            params.put(26, "");                          //OTRO MEDIO DE PAGO
            params.put(27, "");                          //NUM OPERACIONES MENSUALES
            params.put(28, "");                          //NUM OPERACIONES MENSUALES EFECTIVO
            params.put(29, "");                          //FOTO FACHADA
            params.put(30, "");                          //REFERENCIA DOMICILIARIA
            params.put(31, "0");                         //ESTATUS RECHAZO
            params.put(32, "");                          //COMENTARIO RECHAZADO
            params.put(33, "0");                         //ESTATUS COMPLETADO

            dBhelper.saveDatosNegocioGpo(db, params, tipo);

            //Inserta registro del conyuge
            params = new HashMap<>();
            params.put(0, String.valueOf(id_integrante));          //ID INTEGRANTE
            params.put(1, "");                          //NOMBRE
            params.put(2, "");                          //PATERNO
            params.put(3, "");                          //MATERNO
            params.put(4, "");                          //NACIONALIDAD
            params.put(5, "");                          //OCUPACION
            params.put(6, "");                          //CALLE
            params.put(7, "");                          //NO EXTERIOR
            params.put(8, "");                          //NO INTERIOR
            params.put(9, "");                          //MANZANA
            params.put(10, "");                          //LOTE
            params.put(11, "");                          //CP
            params.put(12, "");                          //COLONIA
            params.put(13, "");                          //CIUDAD
            params.put(14, "");                          //LOCALIDAD
            params.put(15, "");                          //MUNICIPIO
            params.put(16, "");                          //ESTADO
            params.put(17, "");                          //INGRESO MENSUAL
            params.put(18, "");                          //GASTO MENSUAL
            params.put(19, "");                          //TEL CASA
            params.put(20, "");                          //TEL CELULAR
            params.put(21, "0");                         //ESTATUS COMPLETADO

            dBhelper.saveDatosConyugeGpo(db, params, tipo);

            //Inserta otros datos del integrante
            params = new HashMap<>();
            params.put(0, String.valueOf(id_integrante));          //ID INTEGRANTE
            params.put(1, "");                          //CLASIFICACION RIESGO
            params.put(2, "");                          //MEDIO CONTACTO
            params.put(3, "");                          //EMAIL
            params.put(4, "");                          //ESTADO CUENTA
            params.put(5, "0");                         //ESTATUS INTEGRANTE
            params.put(6, "");                          //MONTO SOLICITADO
            params.put(7, "0");                         //CASA REUNION
            params.put(8, "");                          //FIRMA
            params.put(9, "0");                         //ESTATUS COMPLETADO

            dBhelper.saveDatosOtrosGpo(db, params, tipo);

            //Inserta registro de croquis
            params = new HashMap<>();
            params.put(0, String.valueOf(id_integrante));                  //ID SOLICITUD
            params.put(1, "");                                  //CALLE PRINCIPAL
            params.put(2, "");                                  //LATERAL UNO
            params.put(3, "");                                  //LATERAL DOS
            params.put(4, "");                                  //CALLE TRASERA
            params.put(5, "");                                  //REFERENCIAS
            params.put(6, "0");                                 //ESTATUS COMPLETADO

            dBhelper.saveCroquisGpo(db, params, tipo);

            //Inserta registro de politicas de integrante
            params = new HashMap<>();
            params.put(0, String.valueOf(id_integrante));      //ID INTEGRANTE
            params.put(1, "0");                     //PROPIETARIO REAL
            params.put(2, "0");                     //PROVEEDOR RECURSOS
            params.put(3, "0");                     //PERSONA POLITICA
            params.put(4, "0");                     //ESTATUS COMPLETADO

            dBhelper.savePoliticasIntegrante(db, params, tipo);

            //Inserta registro de documentos de integrante
            params = new HashMap<>();
            params.put(0, String.valueOf(id_integrante));      //ID INTEGRANTE
            params.put(1, "");                      //INE FRONTAL
            params.put(2, "");                      //INE REVERSO
            params.put(3, "");                      //CURP
            params.put(4, "");                      //COMPROBANTE
            params.put(5, "0");                     //ESTATUS COMPLETADO

            dBhelper.saveDocumentosIntegrante(db, params, tipo);
        }
        SolicitudCampana solicitudCampana = dBhelper.getSolicitudCampanaDao()
                .findBySolicitudId(id_credito, id_integrante, EntitiesCommonsContants.TIPO_SOLICITUD_GRUPAL_RENOVACION)
                .orElseGet(this::saveSolicitudCamapana);
        Beneficiario beneficiario = dBhelper.getBeneficiariosDao()
                .findBySolicitudId(id_credito, id_integrante, EntitiesCommonsContants.TIPO_SOLICITUD_GRUPAL_RENOVACION)
                .orElseGet(this::saveBeneficiario);
        dBhelper.saveCargoIntegranteGpoRen(db, id_cargo, id_integrante);
        mListener.onComplete(id_integrante, etNombre.getText().toString().trim().toUpperCase(), etPaterno.getText().toString().trim().toUpperCase(), etMaterno.getText().toString().trim().toUpperCase(), tvCargo.getText().toString(), solicitudCampana, beneficiario);
        getDialog().dismiss();

    }

    private Beneficiario saveBeneficiario() {
        Beneficiario newBeneficiario = new Beneficiario();
        newBeneficiario.setSolicitudId(id_credito);
        newBeneficiario.setIntegranteId(id_integrante);
        newBeneficiario.setTipoSolicitud(EntitiesCommonsContants.TIPO_SOLICITUD_GRUPAL_RENOVACION);
        newBeneficiario.setSolicitudRemotaId(0);
        newBeneficiario.setNombre("");
        newBeneficiario.setPaterno("");
        newBeneficiario.setMaterno("");
        newBeneficiario.setParentesco("");
        Long beneficiarioId = dBhelper.getBeneficiariosDao().insert(newBeneficiario);
        newBeneficiario.setId(beneficiarioId);
        return newBeneficiario;
    }

    private SolicitudCampana saveSolicitudCamapana() {
        SolicitudCampana newSolicitudCampana = new SolicitudCampana();
        newSolicitudCampana.setSolicitudId(id_credito);
        newSolicitudCampana.setIntegranteId(id_integrante);
        newSolicitudCampana.setTipoSolicitud(EntitiesCommonsContants.TIPO_SOLICITUD_GRUPAL_RENOVACION);
        newSolicitudCampana.setSolicitudRemotaId(0);
        newSolicitudCampana.setNombreReferido("NINGUNO");
        newSolicitudCampana.setCampanaNombre("NINGUNO");
        Long solicitudCamapanaId = dBhelper.getSolicitudCampanaDao().insert(newSolicitudCampana);
        newSolicitudCampana.setId(solicitudCamapanaId);
        return newSolicitudCampana;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 8652:
                if (resultCode == 2658) {
                    if (data != null) {
                        id_cargo = data.getIntExtra("id_cargo", 0);
                        tvCargo.setText(data.getStringExtra("cargo"));
                    }
                }
                break;
        }
    }

    private View.OnClickListener btnCancelar_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (id_integrante == 0) {
                mListener.onComplete(0, null, null, null, null, null, null);
                getDialog().dismiss();
            } else {
                getDialog().dismiss();
            }

        }
    };
}
