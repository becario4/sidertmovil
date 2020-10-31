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

import static com.sidert.sidertmovil.utils.Constants.TIMESTAMP;


public class dialog_registro_cli extends DialogFragment {

    public interface OnCompleteListener {
        void onComplete(long id_solicitud, String id_cliente, String nombre, String paterno, String materno, long dirClie, long dirCony, long dirNeg, long dirAval, long dirRef);
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
        long id_direccion_cli = 0;
        long id_direccion_cony = 0;
        long id_direccion_neg = 0;
        long id_direccion_aval = 0;
        long id_direccion_ref = 0;


        String nombre = (etNombre.getText().toString().trim().toUpperCase() + " " + etPaterno.getText().toString().trim().toUpperCase() + " " + etMaterno.getText().toString().trim().toUpperCase()).trim();
        HashMap<Integer, String> params = new HashMap<>();
        params.put(0, getString(R.string.vol_solicitud));                               //VOL SOLICITUD
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

        id = dBhelper.saveSolicitudes(db, params, 1);

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

        //Inserta registro de direccion del cliente
        params = new HashMap<>();
        params.put(0, "CLIENTE");                                              //TIPO DIRECCION
        params.put(1, "");                                                     //LATITUD
        params.put(2, "");                                                     //LONGITUD
        params.put(3, "");                                                     //CALLE
        params.put(4, "");                                                     //NO EXTERIOR
        params.put(5, "");                                                     //NO INTERIOR
        params.put(6, "");                                                     //MANZANA
        params.put(7, "");                                                     //LOTE
        params.put(8, "");                                                     //CP
        params.put(9, "");                                                     //COLONIA
        params.put(10, "");                                                    //CIUDAD
        params.put(11, "");                                                    //LOCALIDAD
        params.put(12, "");                                                    //MUNICIPIO
        params.put(13, "");                                                    //ESTADO

        id_direccion_cli = dBhelper.saveDirecciones(db, params, 1);

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
        params.put(21, String.valueOf(id_direccion_cli));                           //DIRECCION ID
        params.put(22, "");                                                     //TEL CASA
        params.put(23, "");                                                     //TEL CELULAR
        params.put(24, "");                                                     //TEL MENSAJES
        params.put(25, "");                                                     //TEL TRABAJO
        params.put(26, "0");                                                    //TIEMPO VIVIR SITIO
        params.put(27, "");                                                     //DEPENDIENTES
        params.put(28, "");                                                     //MEDIO CONTACTO
        params.put(29, "");                                                     //ESTADO CUENTA
        params.put(30, "");                                                     //EMAIL
        params.put(31, "");                                                     //FOTO FACHADA
        params.put(32, "");                                                     //REF DOMICILIARIA
        params.put(33, "");                                                     //FIRMA
        params.put(34, "0");                                                    //ESTATUS RECHAZO
        params.put(35, "");                                                     //COMENTARIO RECHAZO
        params.put(36, "0");                                                    //ESTATUS COMPLETO

        id_cliente = dBhelper.saveDatosPersonales(db, params, 1);

        //Inserta registro de direccion del cliente
        params = new HashMap<>();
        params.put(0, "CONYUGE");                                              //TIPO DIRECCION
        params.put(1, "");                                                     //LATITUD
        params.put(2, "");                                                     //LONGITUD
        params.put(3, "");                                                     //CALLE
        params.put(4, "");                                                     //NO EXTERIOR
        params.put(5, "");                                                     //NO INTERIOR
        params.put(6, "");                                                     //MANZANA
        params.put(7, "");                                                     //LOTE
        params.put(8, "");                                                     //CP
        params.put(9, "");                                                     //COLONIA
        params.put(10, "");                                                    //CIUDAD
        params.put(11, "");                                                    //LOCALIDAD
        params.put(12, "");                                                    //MUNICIPIO
        params.put(13, "");                                                    //ESTADO

        id_direccion_cony = dBhelper.saveDirecciones(db, params, 1);

        //Inserta registro de datos conyuge
        params = new HashMap<>();
        params.put(0, String.valueOf(id));                      //ID SOLICITUD
        params.put(1, "");                                      //NOMBRE
        params.put(2, "");                                      //PATERNO
        params.put(3, "");                                      //MATERNO
        params.put(4, "");                                      //NACIONALIDAD
        params.put(5, "");                                      //OCUPACION
        params.put(6, String.valueOf(id_direccion_cony));       //DIRECCION ID
        params.put(7, "");                                      //ING MENSUAL
        params.put(8, "");                                      //GASTO MENSUAL
        params.put(9, "");                                      //TEL CASA
        params.put(10, "");                                     //TEL CELULAR
        params.put(11, "0");                                    //ESTATUS COMPLETADO

        dBhelper.saveDatosConyuge(db, params, 1);

        //Inserta registro de datos economicos
        params = new HashMap<>();
        params.put(0, String.valueOf(id));                      //ID SOLICITUD
        params.put(1, "");                                      //PROPIEDADES
        params.put(2, "");                                      //VALOR APROXIMADO
        params.put(3, "");                                      //UBICACION
        params.put(4, "");                                      //INGRESO
        params.put(5, "0");                                     //ESTATUS COMPLETADO

        dBhelper.saveDatosEconomicos(db, params, 1);

        //Inserta registro de direccion del negocio
        params = new HashMap<>();
        params.put(0, "NEGOCIO");                                              //TIPO DIRECCION
        params.put(1, "");                                                     //LATITUD
        params.put(2, "");                                                     //LONGITUD
        params.put(3, "");                                                     //CALLE
        params.put(4, "");                                                     //NO EXTERIOR
        params.put(5, "");                                                     //NO INTERIOR
        params.put(6, "");                                                     //MANZANA
        params.put(7, "");                                                     //LOTE
        params.put(8, "");                                                     //CP
        params.put(9, "");                                                     //COLONIA
        params.put(10, "");                                                    //CIUDAD
        params.put(11, "");                                                    //LOCALIDAD
        params.put(12, "");                                                    //MUNICIPIO
        params.put(13, "");                                                    //ESTADO

        id_direccion_neg = dBhelper.saveDirecciones(db, params, 1);

        //Inserta registro de negocio
        params = new HashMap<>();
        params.put(0, String.valueOf(id));                  //ID SOLICITUD
        params.put(1, "");                                  //NOMBRE
        params.put(2, String.valueOf(id_direccion_neg));        //DIRECCION ID
        params.put(3, "");                                  //OCUPACION
        params.put(4, "");                                  //ACTIVIDAD ECONOMICA
        params.put(5, "");                                  //DESTINO CREDITO
        params.put(6, "");                                  //OTRO DESTINO
        params.put(7,"0");                                  //ANTIGUEDAD
        params.put(8,"");                                   //ING MENSUAL
        params.put(9,"");                                   //ING OTROS
        params.put(10,"");                                   //GASTO SEMANAL
        params.put(11,"");                                  //GASTO AGUA
        params.put(12,"");                                  //GASTO LUZ
        params.put(13,"");                                  //GASTO TELEFONO
        params.put(14,"");                                  //GASTO RENTA
        params.put(15,"");                                  //GASTO OTROS
        params.put(16,"");                                  //CAPACIDAD PAGO
        params.put(17,"");                                  //MEDIO PAGO
        params.put(18,"");                                  //OTRO MEDIO PAGO
        params.put(19,"");                                  //MONTO MAXIMO
        params.put(20,"0");                                 //NUM OPERACION MENSUALES
        params.put(21,"0");                                 //NUM OPERACION EFECTIVO
        params.put(22,"");                                  //DIAS VENTA
        params.put(23,"");                                  //FOTO FACHADA
        params.put(24,"");                                  //REF DOMICILIARIA
        params.put(25,"0");                                 //ESTATUS COMPLETADO
        params.put(26,"");                                  //COMENTARIO RECHAZO

        dBhelper.saveDatosNegocio(db, params, 1);

        //Inserta registro de direccion del aval
        params = new HashMap<>();
        params.put(0, "AVAL");                                                 //TIPO DIRECCION
        params.put(1, "");                                                     //LATITUD
        params.put(2, "");                                                     //LONGITUD
        params.put(3, "");                                                     //CALLE
        params.put(4, "");                                                     //NO EXTERIOR
        params.put(5, "");                                                     //NO INTERIOR
        params.put(6, "");                                                     //MANZANA
        params.put(7, "");                                                     //LOTE
        params.put(8, "");                                                     //CP
        params.put(9, "");                                                     //COLONIA
        params.put(10, "");                                                    //CIUDAD
        params.put(11, "");                                                    //LOCALIDAD
        params.put(12, "");                                                    //MUNICIPIO
        params.put(13, "");                                                    //ESTADO

        id_direccion_aval = dBhelper.saveDirecciones(db, params, 1);

        //Inserta registro del aval
        params = new HashMap<>();
        params.put(0, String.valueOf(id));                  //ID SOLICITUD
        params.put(1, "");                                  //NOMBRE
        params.put(2, "");                                  //PATERNO
        params.put(3, "");                                  //MATERNO
        params.put(4, "");                                  //FECHA NACIMIENTO
        params.put(5, "");                                  //EDAD
        params.put(6, "2");                                 //GENERO
        params.put(7, "");                                  //ESTADO NACIMIENTO
        params.put(8, "");                                  //RFC
        params.put(9, "");                                  //CURP
        params.put(10, "");                                 //CURP DIGITO
        params.put(11, "");                                 //PARENTESCO CLIENTE
        params.put(12, "");                                 //TIPO IDENTIFICACION
        params.put(13, "");                                 //NUM IDENTIFICACION
        params.put(14, "");                                 //OCUPACION
        params.put(15, "");                                 //ACTIVIDAD ECONOMICA
        params.put(16, "");                                 //DESTINO CREDITO
        params.put(17, "");                                 //OTRO DESTINO
        params.put(18, String.valueOf(id_direccion_aval));  //DIRECCION ID
        params.put(19, "");                                 //TIPO VIVIENDA
        params.put(20, "");                                 //NOMBRE TITULAR
        params.put(21, "");                                 //PARENTESCO
        params.put(22, "");                                 //CARACTERISTICAS DOMICILIO
        params.put(23, "0");                                //ANTIGUEDAD
        params.put(24, "0");                                //TIENE NEGOCIO
        params.put(25, "");                                 //NOMBRE NEGOCIO
        params.put(26, "");                                 //ING MENSUAL
        params.put(27, "");                                 //ING OTROS
        params.put(28, "");                                 //GASTO SEMANAL
        params.put(29, "");                                 //GASTO AGUA
        params.put(30, "");                                 //GASTO LUZ
        params.put(31, "");                                 //GASTO TELEFONO
        params.put(32, "");                                 //GASTO RENTA
        params.put(33, "");                                 //GASTO OTROS
        params.put(34, "");                                 //CAPACIDAD PAGOS
        params.put(35, "");                                 //MEDIO PAGO
        params.put(36, "");                                 //OTRO MEDIO PAGO
        params.put(37, "");                                 //MONTO MAXIMO
        params.put(38, "");                                 //HORARIO LOCALIZACION
        params.put(39, "");                                 //ACTIVOS OBSERVABLES
        params.put(40, "");                                 //TEL CASA
        params.put(41, "");                                 //TEL CELULAR
        params.put(42, "");                                 //TEL MENSAJES
        params.put(43, "");                                 //TEL TRABAJO
        params.put(44, "");                                 //EMAIL
        params.put(45, "");                                 //FOTO FACHADA
        params.put(46, "");                                 //REF DOMICILIARIA
        params.put(47, "");                                 //FIRMA
        params.put(48, "0");                                //ESTATUS RECHAZO
        params.put(49, "");                                 //COMENTARIO RECHAZO
        params.put(50, "0");                                //ESTATUS RECHAZO

        dBhelper.saveDatosAval(db, params, 1);

        //Inserta registro de direccion del referencia
        params = new HashMap<>();
        params.put(0, "REFERENCIA");                                           //TIPO DIRECCION
        params.put(1, "");                                                     //LATITUD
        params.put(2, "");                                                     //LONGITUD
        params.put(3, "");                                                     //CALLE
        params.put(4, "");                                                     //NO EXTERIOR
        params.put(5, "");                                                     //NO INTERIOR
        params.put(6, "");                                                     //MANZANA
        params.put(7, "");                                                     //LOTE
        params.put(8, "");                                                     //CP
        params.put(9, "");                                                     //COLONIA
        params.put(10, "");                                                    //CIUDAD
        params.put(11, "");                                                    //LOCALIDAD
        params.put(12, "");                                                    //MUNICIPIO
        params.put(13, "");                                                    //ESTADO

        id_direccion_ref = dBhelper.saveDirecciones(db, params, 1);

        //Inserta registro de referencia
        params = new HashMap<>();
        params.put(0, String.valueOf(id));                  //ID SOLICITUD
        params.put(1, "");                                  //NOMBRE
        params.put(2, "");                                  //PATERNO
        params.put(3, "");                                  //MATERNO
        params.put(4, "");                                  //FECHA NACIMIENTO
        params.put(5, String.valueOf(id_direccion_ref));    //DIRECCION ID
        params.put(6, "");                                  //TEL_CELULAR
        params.put(7, "0");                                 //ESTATUS COMPLETADO
        params.put(8, "");                                  //COMENTARIO RECHAZO

        dBhelper.saveReferencia(db, params, 1);

        //Inserta registro de croquis
        params = new HashMap<>();
        params.put(0, String.valueOf(id));                  //ID SOLICITUD
        params.put(1, "");                                  //CALLE PRINCIPAL
        params.put(2, "");                                  //LATERAL UNO
        params.put(3, "");                                  //LATERAL DOS
        params.put(4, "");                                  //CALLE TRASERA
        params.put(5, "");                                  //REFERENCIAS
        params.put(6, "0");                                 //ESTATUS COMPLETADO
        params.put(7, "");                                  //COMENTARIO RECHAZO

        dBhelper.saveCroquisInd(db, params, 1);

        //Inserta registro de politicas
        params = new HashMap<>();
        params.put(0, String.valueOf(id));                  //ID SOLICITUD
        params.put(1, "0");                                 //PROPIERATIO REAL
        params.put(2, "0");                                 //PROVEEDOR RECURSOS
        params.put(3, "0");                                 //PERSONA POLITICA
        params.put(4, "0");                                 //ESTATUS COMPLETADO

        dBhelper.savePoliticasInd(db, params, 1);

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

        dBhelper.saveDocumentosClientes(db, params, 1);

        mListener.onComplete(id, String.valueOf(id_cliente),
                etNombre.getText().toString().trim().toUpperCase(),
                etPaterno.getText().toString().trim().toUpperCase(),
                etMaterno.getText().toString().trim().toUpperCase(),
                id_direccion_cli,
                id_direccion_cony,
                id_direccion_neg,
                id_direccion_aval,
                id_direccion_ref);

        getDialog().dismiss();
    }

    private View.OnClickListener btnCancelar_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mListener.onComplete(0, "0", null, null, null, 0,0,0,0,0);
            getDialog().dismiss();
        }
    };

}
