package com.sidert.sidertmovil.activities;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.database.DBhelper;
import com.sidert.sidertmovil.fragments.dialogs.dialog_date_picker;
import com.sidert.sidertmovil.models.ModeloCatalogoGral;
import com.sidert.sidertmovil.utils.Constants;
import com.sidert.sidertmovil.utils.Miscellaneous;
import com.sidert.sidertmovil.utils.NetworkStatus;
import com.sidert.sidertmovil.utils.Popups;
import com.sidert.sidertmovil.utils.Servicios_Sincronizado;
import com.sidert.sidertmovil.utils.Validator;
import com.sidert.sidertmovil.utils.ValidatorTextView;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.sidert.sidertmovil.utils.Constants.CATALOGO;
import static com.sidert.sidertmovil.utils.Constants.COLONIA;
import static com.sidert.sidertmovil.utils.Constants.COLONIAS;
import static com.sidert.sidertmovil.utils.Constants.DATE_CURRENT;
import static com.sidert.sidertmovil.utils.Constants.DAY_CURRENT;
import static com.sidert.sidertmovil.utils.Constants.ESTADOS;
import static com.sidert.sidertmovil.utils.Constants.EXTRA;
import static com.sidert.sidertmovil.utils.Constants.FECHAS_POST;
import static com.sidert.sidertmovil.utils.Constants.FORMAT_DATE_GNRAL;
import static com.sidert.sidertmovil.utils.Constants.IDENTIFIER;
import static com.sidert.sidertmovil.utils.Constants.ITEM;
import static com.sidert.sidertmovil.utils.Constants.MONTH_CURRENT;
import static com.sidert.sidertmovil.utils.Constants.REQUEST_CODE;
import static com.sidert.sidertmovil.utils.Constants.REQUEST_CODE_COLONIA;
import static com.sidert.sidertmovil.utils.Constants.REQUEST_CODE_ESTADO_AVAL;
import static com.sidert.sidertmovil.utils.Constants.REQUEST_CODE_ESTADO_NAC;
import static com.sidert.sidertmovil.utils.Constants.TIMESTAMP;
import static com.sidert.sidertmovil.utils.Constants.TITULO;
import static com.sidert.sidertmovil.utils.Constants.YEAR_CURRENT;
import static com.sidert.sidertmovil.utils.Constants.not_network;
import static com.sidert.sidertmovil.utils.Constants.warning;
import static com.sidert.sidertmovil.utils.NameFragments.DIALOGDATEPICKER;

/**Clase para realizar la consulta a CC por el asesor*/
public class ConsultarCC extends AppCompatActivity {

    private Context ctx;
    private Toolbar tbMain;

    private TextView tvProducto;
    private RadioGroup rgProducto;
    private EditText etCredSolicitado;
    private EditText etPriNombre;
    private EditText etSegNombre;
    private EditText etApPaterno;
    private EditText etApMaterno;
    private TextView tvFechaNac;
    private TextView tvGenero;
    private RadioGroup rgGenero;
    private TextView tvEstadoNac;
    private EditText etCurp;
    private EditText tvRfc;

    private EditText etCalle;
    private EditText tvColonia;
    private TextView tvMunicipio;
    private EditText etCiudad;
    private TextView tvEstado;
    private EditText etCp;

    private SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_DATE_GNRAL);
    private Calendar myCal;
    private Miscellaneous m;
    private Validator validator;
    private ValidatorTextView validatorTV;

    private DBhelper dBhelper;
    private SQLiteDatabase db;

    private boolean hasFractionalPart = false;
    DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
    DecimalFormat dfnd = new DecimalFormat("#,###", symbols);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar_c_c);

        ctx = this;

        dBhelper = new DBhelper(ctx);
        db = dBhelper.getWritableDatabase();

        tbMain           = findViewById(R.id.tbMain);

        tvProducto       = findViewById(R.id.tvProducto);
        rgProducto       = findViewById(R.id.rgProducto);
        etCredSolicitado = findViewById(R.id.etCredSolicitado);
        etPriNombre      = findViewById(R.id.etPriNombre);
        etSegNombre      = findViewById(R.id.etSegNombre);
        etApPaterno      = findViewById(R.id.etApPaterno);
        etApMaterno      = findViewById(R.id.etApMaterno);
        tvFechaNac       = findViewById(R.id.tvFechaNac);
        tvGenero         = findViewById(R.id.tvGenero);
        rgGenero         = findViewById(R.id.rgGenero);
        tvEstadoNac      = findViewById(R.id.tvEstadoNac);
        etCurp           = findViewById(R.id.etCurp);
        tvRfc            = findViewById(R.id.tvRfc);

        etCalle         = findViewById(R.id.etCalle);
        tvColonia       = findViewById(R.id.tvColonia);
        tvMunicipio     = findViewById(R.id.tvMunicipio);
        etCiudad        = findViewById(R.id.etCiudad);
        tvEstado        = findViewById(R.id.tvEstado);
        etCp            = findViewById(R.id.etCp);

        setSupportActionBar(tbMain);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        /**valida que sea un monto de credito valida*/
        etCredSolicitado.addTextChangedListener(new TextWatcher() {
            private final String PATTERN_MONTO_CREDITO  = "[1-9][0-9][0-9][0][0][0]|[1-9][0-9][0][0][0]|[1-9][0][0][0]";
            private Pattern pattern;
            private Matcher matcher;

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence cs, int i, int i1, int i2) {
                if (cs.toString().contains(String.valueOf(dfnd.getDecimalFormatSymbols().getDecimalSeparator())))
                {
                    hasFractionalPart = true;
                } else {
                    hasFractionalPart = false;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                etCredSolicitado.removeTextChangedListener(this);

                try {
                    int inilen, endlen;
                    inilen = etCredSolicitado.getText().length();
                    String v = s.toString().replace(String.valueOf(dfnd.getDecimalFormatSymbols().getGroupingSeparator()), "");
                    Number n = dfnd.parse(v);
                    int cp = etCredSolicitado.getSelectionStart();
                    if (hasFractionalPart) {
                        etCredSolicitado.setText(dfnd.format(n));
                    } else {
                        etCredSolicitado.setText(dfnd.format(n));
                    }
                    endlen = etCredSolicitado.getText().length();
                    int sel = (cp + (endlen - inilen));
                    if (sel > 0 && sel <= etCredSolicitado.getText().length()) {
                        etCredSolicitado.setSelection(sel);
                    } else {
                        // place cursor at the end?
                        etCredSolicitado.setSelection(etCredSolicitado.getText().length() - 1);
                    }
                } catch (NumberFormatException nfe) {
                    // do nothing?
                } catch (ParseException e) {
                    // do nothing?
                }

                if (s.length()> 0){
                    pattern = Pattern.compile(PATTERN_MONTO_CREDITO);
                    matcher = pattern.matcher(s.toString().replace(",",""));
                    if(!matcher.matches()) {
                        etCredSolicitado.setError("La cantidad no corresponde a un monto de crédito válido");
                    }
                }

                etCredSolicitado.addTextChangedListener(this);
            }
        });

        /**Selecciona el tipo de producto individual y grupal*/
        rgProducto.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                tvProducto.setError(null);
            }
        });

        /**Seleciona el genero del cliente*/
        rgGenero.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                tvGenero.setError(null);
                /**Genera en automatico la curp solo deja pendiente los ultimos 2 digitos*/
                GenerarCurp();
            }
        });

        /**Evento de click para abrir el calendario*/
        tvFechaNac.setOnClickListener(tvFechaNac_OnClick);
        /**Evento de click para abrir el catalogo de estados*/
        tvEstadoNac.setOnClickListener(tvEstadoNac_OnClick);
        //tvColonia.setOnClickListener(tvColonia_OnClick);
        /**Evento de click para abrir el catalogo de estados*/
        tvEstado.setOnClickListener(tvEstado_OnClick);

        myCal = Calendar.getInstance();

    }

    /**Evento para abrir el dialogFragment de calendario*/
    private View.OnClickListener tvFechaNac_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            dialog_date_picker dialogDatePicker = new dialog_date_picker();

            Bundle b = new Bundle();
            b.putInt(YEAR_CURRENT, ((m.GetStr(tvFechaNac).isEmpty())?myCal.get(Calendar.YEAR):Integer.parseInt(m.GetStr(tvFechaNac).substring(0,4))));
            b.putInt(MONTH_CURRENT, ((m.GetStr(tvFechaNac).isEmpty())?myCal.get(Calendar.MONTH):(Integer.parseInt(m.GetStr(tvFechaNac).substring(5,7))-1)));
            b.putInt(DAY_CURRENT, ((m.GetStr(tvFechaNac).isEmpty())?myCal.get(Calendar.DAY_OF_MONTH):Integer.parseInt(m.GetStr(tvFechaNac).substring(8,10))));
            b.putString(DATE_CURRENT, sdf.format(myCal.getTime()));
            b.putInt(IDENTIFIER, 19);
            b.putBoolean(FECHAS_POST, false);
            dialogDatePicker.setArguments(b);
            dialogDatePicker.show(getSupportFragmentManager(), DIALOGDATEPICKER);
        }
    };

    /**Evento para abrir el catalogo de estados*/
    private View.OnClickListener tvEstadoNac_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent i_estados = new Intent(ctx, Catalogos.class);
            i_estados.putExtra(TITULO, Miscellaneous.ucFirst(ESTADOS));
            i_estados.putExtra(CATALOGO, ESTADOS);
            i_estados.putExtra(REQUEST_CODE, REQUEST_CODE_ESTADO_NAC);
            startActivityForResult(i_estados, REQUEST_CODE_ESTADO_NAC);
        }
    };

    /**Evento para abrir el catalogo de estados*/
    private View.OnClickListener tvEstado_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent i_estados = new Intent(ctx, Catalogos.class);
            i_estados.putExtra(TITULO, Miscellaneous.ucFirst(ESTADOS));
            i_estados.putExtra(CATALOGO, ESTADOS);
            i_estados.putExtra(REQUEST_CODE, REQUEST_CODE_ESTADO_AVAL);
            startActivityForResult(i_estados, REQUEST_CODE_ESTADO_AVAL);
        }
    };

    /**Evento de catalogo de colonia no se ocupa porque lo escribe manual*/
    private View.OnClickListener tvColonia_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent i_colonia = new Intent(ctx, Catalogos.class);
            i_colonia.putExtra(TITULO, "Colonias");
            i_colonia.putExtra(CATALOGO, COLONIAS);
            i_colonia.putExtra(EXTRA, etCp.getText().toString().trim());
            i_colonia.putExtra(REQUEST_CODE, REQUEST_CODE_COLONIA);
            startActivityForResult(i_colonia, REQUEST_CODE_COLONIA);
        }
    };

    /**Guarda el registro de la consulta para posterior ser enviado*/
    private void ConsultarDatos(Boolean enviarDatos){
        String producto = (rgProducto.getCheckedRadioButtonId() == R.id.rbCreditoInd)?"CREDITO INDIVIDUAL":"CREDITO GRUPAL";
        String genero = (rgGenero.getCheckedRadioButtonId() == R.id.rbHombre)?"HOMBRE":"MUJER";

        HashMap<Integer, String> params = new HashMap<>();
        params.put(0, producto); //producto credito
        params.put(1, m.GetStr(etCredSolicitado).replace(",","")); //monto solicitado
        params.put(2, m.GetStr(etPriNombre));  //primer nombre
        params.put(3, m.GetStr(etSegNombre));  //segundo nombre
        params.put(4, m.GetStr(etApPaterno));   //primer apellido
        params.put(5, m.GetStr(etApMaterno));   //segundo apellido
        params.put(6, m.GetStr(tvFechaNac));    //fecha nacimiento
        params.put(7, genero);                  //genero
        params.put(8, m.GetStr(tvEstadoNac));   //estado nacimiento
        params.put(9, m.GetStr(etCurp));        //Curp
        params.put(10, m.GetStr(tvRfc));        //Rfc
        params.put(11, m.GetStr(etCalle));      //Calle
        params.put(12, m.GetStr(etCp));         //Cp
        params.put(13, m.GetStr(tvColonia));    //colonia
        params.put(14, m.GetStr(tvMunicipio));  //municipio
        params.put(15, m.GetStr(etCiudad));     //ciudad
        params.put(16, m.GetStr(tvEstado));     //estado
        params.put(17, Miscellaneous.ObtenerFecha(TIMESTAMP)); //fecha termino
        params.put(18, "");                     //fecha envio
        params.put(19, "0");                     //estatus

        params.put(20, "");                     //saldo_actual
        params.put(21, "");                     //saldo_vencido
        params.put(22, "");                     //peor_pago
        params.put(23, "");                     //creditos_abiertos
        params.put(24, "");                     //credito_cerrados
        params.put(25, "");                     //errores

        dBhelper.saveConsultaCC(db, params);
        //Log.e("ParamsConsultaCC", params.toString());

        /**Si es nuevo la consulta se envia la consulta de CC*/
        Servicios_Sincronizado ss = new Servicios_Sincronizado();
        if (enviarDatos)
            ss.SendConsultaCC(ctx, true);

        finish();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_enviar_datos, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                /**Si el usuario quiere salir de la vista y hay campos llenos se valida si estan vacios cierra la venta*/
                if (FillFields())
                    finish();
                else{
                    /**En el caso de que hay campo que esten llenos mostrará un mensaje si quiere salir o terminar de consultar*/
                    final AlertDialog dlg = Popups.showDialogConfirm(ctx, warning,
                            "Existen algunos campos llenados, si sale del formulario se perderá la información, ¿Está seguro que desea salir?.", R.string.accept, new Popups.DialogMessage() {
                                @Override
                                public void OnClickListener(AlertDialog dialog) {
                                    dialog.dismiss();
                                    finish();
                                }
                            }, R.string.cancel, new Popups.DialogMessage() {
                                @Override
                                public void OnClickListener(AlertDialog dialog) {
                                    dialog.dismiss();
                                }
                            });
                    dlg.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                    dlg.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                    dlg.show();
                }

                break;
            case R.id.enviar:
                /**Valida que los campos que son requeridos esten llenos para poder realizar la consulta*/
                validator = new Validator();
                validatorTV = new ValidatorTextView();
                if (rgProducto.getCheckedRadioButtonId() == R.id.rbCreditoInd || rgProducto.getCheckedRadioButtonId() == R.id.rbCreditoGpo) {
                    if (!validator.validate(etCredSolicitado, new String[]{validator.REQUIRED, validator.CREDITO}) &&
                        !validator.validate(etPriNombre, new String[]{validator.REQUIRED, validator.ONLY_TEXT}) &&
                        !validator.validate(etSegNombre, new String[]{validator.ONLY_TEXT}) &&
                        !validator.validate(etApPaterno, new String[]{validator.ONLY_TEXT}) &&
                        !validator.validate(etApMaterno, new String[]{validator.ONLY_TEXT}) &&
                        !validatorTV.validate(tvFechaNac, new String[]{validatorTV.REQUIRED})) {
                        if ( true || rgGenero.getCheckedRadioButtonId() == R.id.rbHombre || rgGenero.getCheckedRadioButtonId() == R.id.rbMujer) {
                            if (!validatorTV.validate(tvEstadoNac, new String[]{validatorTV.ONLY_TEXT}) &&
                                true) {
                                /**estas lineas que estan comentadas hay que descomentarlas y eliminar las 2 lineas que tiene true la 389 y 393*/
                               // !validator.validate(etCurp, new String[]{validator.REQUIRED, validator.CURP})) {
                                //if (m.CurpValidador(m.GetStr(etCurp))) {
                                if (true) {
                                    if (!validator.validate(tvRfc, new String[]{validator.REQUIRED}) &&
                                        !validator.validate(etCalle, new String[]{validator.REQUIRED}) &&
                                        !validator.validate(etCp, new String[]{validator.REQUIRED, validator.CP}) &&
                                        !validatorTV.validate(tvColonia, new String[]{validatorTV.REQUIRED}) &&
                                        !validatorTV.validate(tvMunicipio, new String[]{validatorTV.REQUIRED}) &&
                                        !validator.validate(etCiudad, new String[]{validator.REQUIRED}) &&
                                        !validatorTV.validate(tvEstado, new String[]{validatorTV.REQUIRED})) {

                                        String nombre = m.GetStr(etPriNombre) + " " + m.GetStr(etSegNombre);
                                        nombre = nombre.trim() + " " + m.GetStr(etApPaterno) + " " + m.GetStr(etApMaterno);
                                        String genero = "";
                                        switch (rgGenero.getCheckedRadioButtonId()){
                                            case R.id.rbHombre:
                                                genero = "HOMBRE";
                                                break;
                                            case R.id.rbMujer:
                                                genero = "MUJER";
                                                break;
                                        }

                                        /**Muestra mensaje de confirmacion de datos antes de hacer la peticion de la consulta*/
                                        final AlertDialog solicitud = Popups.showDialogConfirm(ctx, warning,
                                                "Los datos son los correctos:" +
                                                        "\n Nombre: " + nombre.trim() +
                                                        "\n Fecha Nacimiento: " + m.GetStr(tvFechaNac) +
                                                        "\n Género: " + genero +
                                                        "\n Estado: " + m.GetStr(tvEstadoNac) +
                                                        "\n CURP: " + m.GetStr(etCurp) +
                                                        "\n RFC: " + m.GetStr(tvRfc) +
                                                        "\n Dirección: " + m.GetStr(etCalle) + ", " + m.GetStr(etCp) + ", " +
                                                        m.GetStr(tvMunicipio) + ", " + m.GetStr(tvEstado), R.string.accept, new Popups.DialogMessage() {
                                                    @Override
                                                    public void OnClickListener(AlertDialog dialog) {
                                                        dialog.dismiss();
                                                        if (NetworkStatus.haveNetworkConnection(ctx)){
                                                            ConsultarDatos(true);
                                                        }
                                                        else{
                                                            final AlertDialog dlgSave= Popups.showDialogMessage(ctx, not_network,
                                                                    "Error de conexión, no cuenta con conexión a internet para realizar " +
                                                                            "la consulta a círculo de crédito, se guardarán los datos para que en el momento " +
                                                                            "de reconexión se realice la consulta en automático.", R.string.accept, new Popups.DialogMessage() {
                                                                        @Override
                                                                        public void OnClickListener(AlertDialog d) {
                                                                            d.dismiss();
                                                                            ConsultarDatos(false);
                                                                        }
                                                                    });
                                                            dlgSave.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                                                            dlgSave.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                                                            dlgSave.show();
                                                        }

                                                    }
                                                }, R.string.cancel, new Popups.DialogMessage() {
                                                    @Override
                                                    public void OnClickListener(AlertDialog dialog) {
                                                        dialog.dismiss();
                                                    }
                                                });
                                        solicitud.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                                        solicitud.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                                        solicitud.show();
                                    }
                                } else
                                    etCurp.setError("No corresponde a una Curp válida");
                            }
                        } else
                            tvGenero.setError("");
                    }
                }
                else
                    tvProducto.setError("");
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case REQUEST_CODE_ESTADO_NAC:
                if (data != null){
                    tvEstadoNac.setError(null);
                    tvEstadoNac.setText(((ModeloCatalogoGral)data.getSerializableExtra(ITEM)).getNombre());
                    GenerarCurp();
                }
                break;
            case REQUEST_CODE_ESTADO_AVAL:
                if (data != null){
                    tvEstado.setError(null);
                    tvEstado.setText(((ModeloCatalogoGral)data.getSerializableExtra(ITEM)).getNombre());
                }
                break;
            case REQUEST_CODE_COLONIA:
                if (resultCode == REQUEST_CODE_COLONIA){
                    if (data != null){
                        tvColonia.setError(null);
                        tvColonia.setText(((ModeloCatalogoGral)data.getSerializableExtra(ITEM)).getNombre());
                    }
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (FillFields())
            finish();
        else{
            final AlertDialog dlg = Popups.showDialogConfirm(ctx, warning,
                    "Existen algunos campos llenados, si sale del formulario se perderá la información, ¿Está seguro que desea salir?.", R.string.accept, new Popups.DialogMessage() {
                        @Override
                        public void OnClickListener(AlertDialog dialog) {
                            dialog.dismiss();
                            finish();
                        }
                    }, R.string.cancel, new Popups.DialogMessage() {
                        @Override
                        public void OnClickListener(AlertDialog dialog) {
                            dialog.dismiss();
                        }
                    });
            dlg.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            dlg.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            dlg.show();
        }
    }

    public void setDate(String fecha){
        tvFechaNac.setText(fecha);
        tvFechaNac.setError(null);
        GenerarCurp();
    }

    /**Funcion para generar la curp*/
    private void GenerarCurp(){
        HashMap<Integer, String> params = new HashMap<>();

        String nombres = m.GetStr(etPriNombre) + " " + m.GetStr(etSegNombre);
        params.put(0, nombres.trim());
        params.put(1, m.GetStr(etApPaterno));
        params.put(2, m.GetStr(etApMaterno));
        params.put(3, m.GetStr(tvFechaNac));

        if (rgGenero.getCheckedRadioButtonId()==R.id.rbHombre)
            params.put(4, "Hombre");
        else if (rgGenero.getCheckedRadioButtonId()==R.id.rbMujer)
            params.put(4, "Mujer");
        else
            params.put(4, "");

        params.put(5, m.GetStr(tvEstadoNac));

        etCurp.setText(Miscellaneous.GenerarCurp(params));
    }

    /**Funcion para validar si algun campo esta lleno*/
    private Boolean FillFields(){
        Boolean isClose = true;

        if (rgProducto.getCheckedRadioButtonId() == R.id.rbCreditoInd ||
            rgProducto.getCheckedRadioButtonId() == R.id.rbCreditoGpo ||
            !Miscellaneous.GetStr(etCredSolicitado).isEmpty() ||
            !Miscellaneous.GetStr(etPriNombre).isEmpty() ||
            !Miscellaneous.GetStr(etSegNombre).isEmpty() ||
            !Miscellaneous.GetStr(etApPaterno).isEmpty() ||
            !Miscellaneous.GetStr(etApMaterno).isEmpty() ||
            !Miscellaneous.GetStr(tvFechaNac).isEmpty() ||
            rgGenero.getCheckedRadioButtonId() == R.id.rbHombre ||
            rgGenero.getCheckedRadioButtonId() == R.id.rbMujer ||
            !Miscellaneous.GetStr(tvEstadoNac).isEmpty() ||
            !Miscellaneous.GetStr(etCurp).isEmpty() ||
            !Miscellaneous.GetStr(tvRfc).isEmpty() ||
                !Miscellaneous.GetStr(etCalle).isEmpty() ||
                !Miscellaneous.GetStr(etCp).isEmpty() ||
                !Miscellaneous.GetStr(tvColonia).isEmpty() ||
                !Miscellaneous.GetStr(tvMunicipio).isEmpty() ||
                !Miscellaneous.GetStr(etCiudad).isEmpty() ||
                !Miscellaneous.GetStr(tvEstado).isEmpty()

        )
            isClose = false;

        return isClose;
    }
}
