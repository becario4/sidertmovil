package com.sidert.sidertmovil.fragments.dialogs;


import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
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

import static com.sidert.sidertmovil.utils.Constants.TBL_DOCUMENTOS;
import static com.sidert.sidertmovil.utils.Constants.TIMESTAMP;


public class dialog_registro_cli extends DialogFragment {

    public interface OnCompleteListener {
        void onComplete(long id_solicitud, String id_cliente, String nombre, String paterno, String materno);
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
        long id = 0;
        long id_cliente = 0;
        String nombre = (etNombre.getText().toString().trim().toUpperCase() + " " + etPaterno.getText().toString().trim().toUpperCase() + " " + etMaterno.getText().toString().trim().toUpperCase()).trim();
        HashMap<Integer, String> params = new HashMap<>();
        params.put(0, getString(R.string.vol_solicitud));       //VOL SOLICITUD
        params.put(1,session.getUser().get(9));                 //USUARIO ID
        params.put(2,"1");                                      //TIPO SOLICITUD
        params.put(3,"0");                                      //ID ORIGINACION
        params.put(4, nombre);                                  //NOMBRE
        params.put(5, Miscellaneous.ObtenerFecha(TIMESTAMP));   //FECHA INICIO
        params.put(6,"");                                       //FECHA TERMINO
        params.put(7,"");                                       //FECHA ENVIO
        params.put(8, Miscellaneous.ObtenerFecha(TIMESTAMP));   //FECHA DISPOSITIVO
        params.put(9, "");                                      //FECHA GUARDADO
        params.put(10, "0");                                    //ESTATUS

        id = dBhelper.saveSolicitudes(db, params);

        //Inserta registro de datos del credito
        params = new HashMap<>();
        params.put(0,String.valueOf(id));                       //ID SOLICITUD
        params.put(1,"");                                       //PLAZO
        params.put(2,"");                                       //PERIODICIDAD
        params.put(3,"");                                       //FECHA DESEMBOLSO
        params.put(4,"");                                       //DIA DESEMBOLSO
        params.put(5,"");                                       //HORA VISITA
        params.put(6,"");                                       //MONTO PRESTAMO
        params.put(7,"");                                       //DESTINO
        params.put(8,"");                                       //CLASIFICACION RIESGO
        params.put(9,"0");                                      //ESTATUS COMPLETO

        dBhelper.saveDatosCredito(db, params);

        //Inserta registro de datos del cliente
        params = new HashMap<>();
        params.put(0, String.valueOf(id));                                      //ID SOLICITUD
        params.put(1, etNombre.getText().toString().trim().toUpperCase());      //NOMBRE
        params.put(2, etPaterno.getText().toString().trim().toUpperCase());     //PATERNO
        params.put(3, etMaterno.getText().toString().trim().toUpperCase());     //MATERNO
        params.put(4, "");                                                      //FECHA NACIMIENTO
        params.put(5, "");                                                      //EDAD
        params.put(6, "2");                                                     //GENERO
        params.put(7, "");                                                      //ESTADO NACIMIENTO
        params.put(8, "");                                                      //RFC
        params.put(9, "");                                                      //CURP
        params.put(10, "");                                                     //CURP DIGITO VERI
        params.put(11, "");                                                     //OCUPACION
        params.put(12, "");                                                     //ACTIVIDAD ECONOMICA
        params.put(13, "");                                                     //TIPO IDENTIFICACION
        params.put(14, "");                                                     //NUM IDENTIFICACION
        params.put(15, "");                                                     //NIVEL ESTUDIO
        params.put(16, "");                                                     //ESTATUS CIVIL
        params.put(17, "0");                                                    //BIENES
        params.put(18, "");                                                     //TIPO VIVIENDA
        params.put(19, "");                                                     //PARENTESCO
        params.put(20, "");                                                     //OTRO TIPO VIVIENDA
        params.put(21, "");                                                     //LATITUD
        params.put(22, "");                                                     //LONGITUD
        params.put(23, "");                                                     //CALLE
        params.put(24, "");                                                     //NO EXTERIOR
        params.put(25, "");                                                     //NO INTERIOR
        params.put(26, "");                                                     //MANZANA
        params.put(27, "");                                                     //LOTE
        params.put(28, "");                                                     //CP
        params.put(29, "");                                                     //COLONIA
        params.put(30, "");                                                     //TEL CASA
        params.put(31, "");                                                     //TEL CELULAR
        params.put(32, "");                                                     //TEL MENSAJES
        params.put(33, "");                                                     //TEL TRABAJO
        params.put(34, "0");                                                    //TIEMPO VIVIR SITIO
        params.put(35, "");                                                     //DEPENDIENTES
        params.put(36, "");                                                     //MEDIO CONTACTO
        params.put(37, "");                                                     //EMAIL
        params.put(38, "");                                                     //FOTO FACHADA
        params.put(39, "");                                                     //REF DOMICILIARIA
        params.put(40, "");                                                     //FIRMA
        params.put(41, "0");                                                    //ESTATUS RECHAZO
        params.put(42, "");                                                     //COMENTARIO RECHAZO
        params.put(43, "0");                                                    //ESTATUS COMPLETO

        id_cliente = dBhelper.saveDatosPersonales(db, params);

        //Inserta registro de datos conyuge
        params = new HashMap<>();
        params.put(0, String.valueOf(id));                      //ID SOLICITUD
        params.put(1, "");                                      //NOMBRE
        params.put(2, "");                                      //PATERNO
        params.put(3, "");                                      //MATERNO
        params.put(4, "");                                      //OCUPACION
        params.put(5, "");                                      //CALLE
        params.put(6, "");                                      //NO EXTERIOR
        params.put(7, "");                                      //NO INTERIOR
        params.put(8, "");                                      //MANZANA
        params.put(9, "");                                      //LOTE
        params.put(10, "");                                     //CP
        params.put(11, "");                                     //COLONIA
        params.put(12, "");                                     //ING MENSUAL
        params.put(13, "");                                     //GASTO MENSUAL
        params.put(14, "");                                     //TEL CASA
        params.put(15, "");                                     //TEL CELULAR
        params.put(16, "0");                                    //ESTATUS COMPLETADO

        dBhelper.saveDatosConyuge(db, params);

        //Inserta registro de datos economicos
        params = new HashMap<>();
        params.put(0, String.valueOf(id));                      //ID SOLICITUD
        params.put(1, "");                                      //PROPIEDADES
        params.put(2, "");                                      //VALOR APROXIMADO
        params.put(3, "");                                      //UBICACION
        params.put(4, "");                                      //INGRESO
        params.put(5, "0");                                     //ESTATUS COMPLETADO

            dBhelper.saveDatosEconomicos(db, params);

        //Inserta registro de negocio
        params = new HashMap<>();
        params.put(0, String.valueOf(id));                  //ID SOLICITUD
        params.put(1, "");                                  //NOMBRE
        params.put(2, "");                                  //LATITUD
        params.put(3, "");                                  //LONGITUD
        params.put(4, "");                                  //CALLE
        params.put(5, "");                                  //NO EXTERIOR
        params.put(6, "");                                  //NO INTERIOR
        params.put(7, "");                                  //MANZANA
        params.put(8, "");                                  //LOTE
        params.put(9, "");                                  //CP
        params.put(10, "");                                 //COLONIA
        params.put(11, "");                                 //ACTIVIDAD ECONOMICA
        params.put(12, "");                                 //DESTINO CREDITO
        params.put(13, "");                                 //OTRO DESTINO
        params.put(14,"0");                                 //ANTIGUEDAD
        params.put(15,"");                                  //ING MENSUAL
        params.put(16,"");                                  //ING OTROS
        params.put(17,"");                                  //GASTO SEMANAL
        params.put(18,"");                                  //GASTO AGUA
        params.put(19,"");                                  //GASTO LUZ
        params.put(20,"");                                  //GASTO TELEFONO
        params.put(21,"");                                  //GASTO RENTA
        params.put(22,"");                                  //GASTO OTROS
        params.put(23,"");                                  //CAPACIDAD PAGO
        params.put(24,"");                                  //MEDIO PAGO
        params.put(25,"");                                  //OTRO MEDIO PAGO
        params.put(26,"");                                  //MONTO MAXIMO
        params.put(27,"0");                                 //NUM OPERACION MENSUALES
        params.put(28,"0");                                 //NUM OPERACION EFECTIVO
        params.put(29,"");                                  //DIAS VENTA
        params.put(30,"");                                  //FOTO FACHADA
        params.put(31,"");                                  //REF DOMICILIARIA
        params.put(32,"0");                                 //ESTATUS COMPLETADO

        dBhelper.saveDatosNegocio(db, params);

        //Inserta registro del aval
        params = new HashMap<>();
        params.put(0, String.valueOf(id));  //ID SOLICITUD
        params.put(1, "");                  //NOMBRE
        params.put(2, "");                  //PATERNO
        params.put(3, "");                  //MATERNO
        params.put(4, "");                  //FECHA NACIMIENTO
        params.put(5, "");                  //EDAD
        params.put(6, "2");                 //GENERO
        params.put(7, "");                  //ESTADO NACIMIENTO
        params.put(8, "");                  //RFC
        params.put(9, "");                  //CURP
        params.put(10, "");                 //CURP DIGITO
        params.put(11, "");                 //TIPO IDENTIFICACION
        params.put(12, "");                 //NUM IDENTIFICACION
        params.put(13, "");                 //OCUPACION
        params.put(14, "");                 //ACTIVIDAD ECONOMICA
        params.put(15, "");                 //DESTINO CREDITO
        params.put(16, "");                 //OTRO DESTINO
        params.put(17, "");                 //LATITUD
        params.put(18, "");                 //LONGITUD
        params.put(19, "");                 //CALLE
        params.put(20, "");                 //NO EXTERIOR
        params.put(21, "");                 //NO INTERIOR
        params.put(22, "");                 //MANZANA
        params.put(23, "");                 //LOTE
        params.put(24, "");                 //CP
        params.put(25, "");                 //COLONIA
        params.put(26, "");                 //TIPO VIVIENDA
        params.put(27, "");                 //NOMBRE TITULAR
        params.put(28, "");                 //PARENTESCO
        params.put(29, "0");                //ANTIGUEDAD
        params.put(30, "");                 //ING MENSUAL
        params.put(31, "");                 //ING OTROS
        params.put(32, "");                 //GASTO SEMANAL
        params.put(33, "");                 //GASTO AGUA
        params.put(34, "");                 //GASTO LUZ
        params.put(35, "");                 //GASTO TELEFONO
        params.put(36, "");                 //GASTO RENTA
        params.put(37, "");                 //GASTO OTROS
        params.put(38, "");                 //CAPACIDAD PAGOS
        params.put(39, "");                 //MEDIO PAGO
        params.put(40, "");                 //OTRO MEDIO PAGO
        params.put(41, "");                 //MONTO MAXIMO
        params.put(42, "");                 //HORARIO LOCALIZACION
        params.put(43, "");                 //TEL CASA
        params.put(44, "");                 //TEL CELULAR
        params.put(45, "");                 //FOTO FACHADA
        params.put(46, "");                 //REF DOMICILIARIA
        params.put(47, "");                 //FIRMA
        params.put(48, "0");                //ESTATUS RECHAZO
        params.put(49, "");                 //COMENTARIO RECHAZO
        params.put(50, "0");                //ESTATUS RECHAZO

        dBhelper.saveDatosAval(db, params);

        //Inserta registro de referencia
        params = new HashMap<>();
        params.put(0, String.valueOf(id));      //ID SOLICITUD
        params.put(1, "");                      //NOMBRE
        params.put(2, "");                      //PATERNO
        params.put(3, "");                      //MATERNO
        params.put(4, "");                      //CALLE
        params.put(5, "");                      //CP
        params.put(6, "");                      //COLONIA
        params.put(7, "");                      //MUNICIPIO
        params.put(8, "");                      //TEL_CELULAR
        params.put(9, "0");                     //ESTATUS COMPLETADO

        dBhelper.saveReferencia(db, params);

        //Inseta registro de documentos
        params = new HashMap<>();
        params.put(0,String.valueOf(id));       //ID SOLICITUD
        params.put(1, "");                      //INE FRONTAL
        params.put(2, "");                      //INE REVERSO
        params.put(3, "");                      //CURP
        params.put(4, "");                      //COMPROBANTE
        params.put(5, "");                      //CODIGO BARRAS
        params.put(6, "");                      //FIRMA ASESOR
        params.put(7, "0");                     //ESTATUS COMPLETADO

            dBhelper.saveDocumentosClientes(db, params);

        mListener.onComplete(id, String.valueOf(id_cliente),
                etNombre.getText().toString().trim().toUpperCase(),
                etPaterno.getText().toString().trim().toUpperCase(),
                etMaterno.getText().toString().trim().toUpperCase());
        getDialog().dismiss();
    }

    private View.OnClickListener btnCancelar_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mListener.onComplete(0, "0", null, null, null);
            getDialog().dismiss();
        }
    };

}
