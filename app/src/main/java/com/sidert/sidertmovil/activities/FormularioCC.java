package com.sidert.sidertmovil.activities;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
/*import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;*/
import android.os.Bundle;
//import androidx.appcompat.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
//import android.util.Log;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
//import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.adapters.adapter_gestiones_cc;
import com.sidert.sidertmovil.database.DBhelper;
import com.sidert.sidertmovil.fragments.dialogs.dialog_date_picker;
import com.sidert.sidertmovil.models.ModeloCatalogoGral;
import com.sidert.sidertmovil.models.circulocredito.GestionCirculoCredito;
import com.sidert.sidertmovil.models.circulocredito.GestionCirculoCreditoDao;
import com.sidert.sidertmovil.utils.Constants;
import com.sidert.sidertmovil.utils.Miscellaneous;
import com.sidert.sidertmovil.utils.NetworkStatus;
import com.sidert.sidertmovil.utils.Popups;
import com.sidert.sidertmovil.utils.Servicios_Sincronizado;
import com.sidert.sidertmovil.utils.Validator;
import com.sidert.sidertmovil.utils.ValidatorTextView;
import com.sidert.sidertmovil.views.circulocredito.CirculoCreditoActivity;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import static com.sidert.sidertmovil.utils.Constants.TBL_CONSULTA_CC;
import static com.sidert.sidertmovil.utils.Constants.TBL_RECIBOS_CC;
import static com.sidert.sidertmovil.utils.Constants.TBL_RECUPERACION_RECIBOS_CC;
import static com.sidert.sidertmovil.utils.Constants.TIMESTAMP;
import static com.sidert.sidertmovil.utils.Constants.TITULO;
import static com.sidert.sidertmovil.utils.Constants.YEAR_CURRENT;
import static com.sidert.sidertmovil.utils.Constants.not_network;
import static com.sidert.sidertmovil.utils.Constants.warning;
import static com.sidert.sidertmovil.utils.NameFragments.DIALOGDATEPICKER;

/**Clase para realizar la consulta a CC por el asesor*/
public class FormularioCC extends AppCompatActivity {
    private GestionCirculoCredito gestionCC;
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
    private EditText tvDireccion;
    private SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_DATE_GNRAL);
    private Calendar myCal;
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

        setContentView(R.layout.activity_formulario_c_c);

        ctx = this;
        dBhelper         = DBhelper.getInstance(ctx);
        db               = dBhelper.getWritableDatabase();
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
        tvDireccion     = findViewById(R.id.tvDireccion);

        setSupportActionBar(tbMain);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Bundle b = getIntent().getExtras();
        Log.e("objeto","ESTE UN LOG");
        if(b != null)
        {
     //   String id=  getIntent().getExtras().get("id").toString();

        gestionCC = (GestionCirculoCredito) getIntent().getExtras().get("circulo_credito");

/*
         etCredSolicitado.setText(getIntent().getExtras().get("monto_solicitado").toString());

         etPriNombre.setText(getIntent().getExtras().get("primer_nombre").toString());
         etSegNombre.setText(getIntent().getExtras().get("primer_nombre").toString());
         etApPaterno.setText(getIntent().getExtras().get("ap_paterno").toString());
         etApMaterno.setText(getIntent().getExtras().get("ap_materno").toString());
*/
         // FECHA DE NACIMIENTO

           //GENERO
        //ESTADO
        /*    tvEstadoNac.setText(getIntent().getExtras().get("estado_nac").toString());
           // etCredSolicitado.setText((Integer) getIntent().getExtras().get("monto_solicitado"));

            getIntent().getExtras().get("producto_credito");
            getIntent().getExtras().get("monto_solicitado");
            getIntent().getExtras().get("primer_nombre");
            getIntent().getExtras().get("segundo_nombre");
            getIntent().getExtras().get("ap_paterno");
            getIntent().getExtras().get("ap_materno");
            getIntent().getExtras().get("fecha_nac");
            getIntent().getExtras().get("genero");
            getIntent().getExtras().get("estado_nac");
            getIntent().getExtras().get("curp");
            getIntent().getExtras().get("rfc");
            getIntent().getExtras().get("direccion");
            getIntent().getExtras().get("colonia");
            getIntent().getExtras().get("municipio");
            getIntent().getExtras().get("ciudad");
            getIntent().getExtras().get("estado");
            getIntent().getExtras().get("cp");
            */
       
        }
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
    private View.OnClickListener fbAgregarCC_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i_cc = new Intent(getApplicationContext(), CirculoCreditoActivity.class);
            i_cc.putExtra("circulo_credito", new GestionCirculoCredito());
            startActivity(i_cc);
        }
    };
    /**Evento para abrir el dialogFragment de calendario*/
    private View.OnClickListener tvFechaNac_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            dialog_date_picker dialogDatePicker = new dialog_date_picker();
 
            Bundle b = new Bundle();
            b.putInt(YEAR_CURRENT, ((Miscellaneous.GetStr(tvFechaNac).isEmpty())?myCal.get(Calendar.YEAR):Integer.parseInt(Miscellaneous.GetStr(tvFechaNac).substring(0,4))));
            b.putInt(MONTH_CURRENT, ((Miscellaneous.GetStr(tvFechaNac).isEmpty())?myCal.get(Calendar.MONTH):(Integer.parseInt(Miscellaneous.GetStr(tvFechaNac).substring(5,7))-1)));
            b.putInt(DAY_CURRENT, ((Miscellaneous.GetStr(tvFechaNac).isEmpty())?myCal.get(Calendar.DAY_OF_MONTH):Integer.parseInt(Miscellaneous.GetStr(tvFechaNac).substring(8,10))));
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
        //DATOS DEL PRODUCTO
        params.put(0, producto); //producto credito
        params.put(1, Miscellaneous.GetStr(etCredSolicitado).replace(",","")); //monto solicitado
        //DATOS GENERALES
        params.put(2, Miscellaneous.GetStr(etPriNombre));  //primer nombre
        params.put(3, Miscellaneous.GetStr(etSegNombre));  //segundo nombre
        params.put(4, Miscellaneous.GetStr(etApPaterno));   //primer apellido
        params.put(5, Miscellaneous.GetStr(etApMaterno));   //segundo apellido
        params.put(6, Miscellaneous.GetStr(tvFechaNac));    //fecha nacimiento
        params.put(7, genero);                  //genero
        params.put(8, Miscellaneous.GetStr(tvEstadoNac));   //estado nacimiento
        params.put(9, Miscellaneous.GetStr(etCurp));        //Curp
        params.put(10, Miscellaneous.GetStr(tvRfc));        //Rfc
        //DOMICILIO
        params.put(11, Miscellaneous.GetStr(tvDireccion));      //Direccion
        params.put(12, Miscellaneous.GetStr(tvColonia));    //colonia
        params.put(13, Miscellaneous.GetStr(tvMunicipio));  //municipio
        params.put(14, Miscellaneous.GetStr(etCiudad));     //ciudad
        params.put(15, Miscellaneous.GetStr(tvEstado));     //estado
        params.put(16, Miscellaneous.GetStr(etCp));         //Cp
        params.put(17, Miscellaneous.ObtenerFecha(TIMESTAMP)); //fecha termino
        params.put(18, Miscellaneous.ObtenerFecha(TIMESTAMP)); //fecha termino
        params.put(19,"0");
        params.put(20,"");
        //fecha envio

        dBhelper.saveConsultaCC(db, params);
        //Log.e("ParamsConsultaCC", params.toString());

        /**Si es nuevo la consulta se envia la consulta de CC*/
        Servicios_Sincronizado ss = new Servicios_Sincronizado();
        if (enviarDatos){
            ss.SendConsultaCC(ctx, true);
        }


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
        int itemId = item.getItemId();
        if (itemId == android.R.id.home) {/**Si el usuario quiere salir de la vista y hay campos llenos se valida si estan vacios cierra la venta*/
            if (FillFields())
                finish();
            else {
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
                dlg.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dlg.show();
            }
        } else if (itemId == R.id.enviar) {/**Valida que los campos que son requeridos esten llenos para poder realizar la consulta*/
            validator = new Validator();
            validatorTV = new ValidatorTextView();
            if (rgProducto.getCheckedRadioButtonId() == R.id.rbCreditoInd || rgProducto.getCheckedRadioButtonId() == R.id.rbCreditoGpo) {
                if (!validator.validate(etCredSolicitado, new String[]{validator.REQUIRED, validator.CREDITO}) &&
                        !validator.validate(etPriNombre, new String[]{validator.REQUIRED, validator.ONLY_TEXT}) &&
                        !validator.validate(etSegNombre, new String[]{validator.ONLY_TEXT}) &&
                        !validator.validate(etApPaterno, new String[]{validator.ONLY_TEXT}) &&
                        !validator.validate(etApMaterno, new String[]{validator.ONLY_TEXT}) &&
                        !validatorTV.validate(tvFechaNac, new String[]{validatorTV.REQUIRED})) {
                    if (true || rgGenero.getCheckedRadioButtonId() == R.id.rbHombre || rgGenero.getCheckedRadioButtonId() == R.id.rbMujer) {
                        if (!validatorTV.validate(tvEstadoNac, new String[]{validatorTV.ONLY_TEXT})) {
                            /**estas lineas que estan comentadas hay que descomentarlas y eliminar las 2 lineas que tiene true la 389 y 393*/
                            // !validator.validate(etCurp, new String[]{validator.REQUIRED, validator.CURP})) {
                            //if (m.CurpValidador(m.GetStr(etCurp))) {
                            if (true) {
                                if (!validator.validate(tvRfc, new String[]{validator.REQUIRED}) &&
                                        !validator.validate(tvDireccion, new String[]{validator.REQUIRED}) &&
                                        !validator.validate(etCp, new String[]{validator.REQUIRED, validator.CP}) &&
                                        !validatorTV.validate(tvColonia, new String[]{validatorTV.REQUIRED}) &&
                                        !validatorTV.validate(tvMunicipio, new String[]{validatorTV.REQUIRED}) &&
                                        !validator.validate(etCiudad, new String[]{validator.REQUIRED}) &&
                                        !validatorTV.validate(tvEstado, new String[]{validatorTV.REQUIRED})) {

                                    String nombre = Miscellaneous.GetStr(etPriNombre) + " " + Miscellaneous.GetStr(etSegNombre);
                                    nombre = nombre.trim() + " " + Miscellaneous.GetStr(etApPaterno) + " " + Miscellaneous.GetStr(etApMaterno);
                                    String genero = "";
                                    int checkedRadioButtonId = rgGenero.getCheckedRadioButtonId();
                                    if (checkedRadioButtonId == R.id.rbHombre) {
                                        genero = "HOMBRE";
                                    } else if (checkedRadioButtonId == R.id.rbMujer) {
                                        genero = "MUJER";
                                    }
                                    String producto = (rgProducto.getCheckedRadioButtonId() == R.id.rbCreditoInd) ? "CREDITO INDIVIDUAL" : "CREDITO GRUPAL";

                                    /**Muestra mensaje de confirmacion de datos antes de hacer la peticion de la consulta*/
                                    final AlertDialog solicitud = Popups.showDialogConfirm(ctx, warning,
                                            "Los datos son los correctos:" +
                                                    "\n Producto: " + producto +
                                                    "\n Monto Solicitado: " + Miscellaneous.GetStr(etCredSolicitado) +
                                                    "\n Nombre: " + nombre.trim() +
                                                    "\n Fecha Nacimiento: " + Miscellaneous.GetStr(tvFechaNac) +
                                                    "\n Género: " + genero +
                                                    "\n Estado Nacimiento: " + Miscellaneous.GetStr(tvEstadoNac) +
                                                    "\n CURP: " + Miscellaneous.GetStr(etCurp) +
                                                    "\n RFC: " + Miscellaneous.GetStr(tvRfc) +
                                                    "\n Dirección: " + Miscellaneous.GetStr(tvDireccion) +
                                                    "\n Colonia: " + Miscellaneous.GetStr(tvColonia) +
                                                    "\n Municipio:" + Miscellaneous.GetStr(tvMunicipio) +
                                                    "\n Ciudad:" + Miscellaneous.GetStr(etCiudad) +
                                                    "\n Estado:" + Miscellaneous.GetStr(tvEstado) +
                                                    "\n Cp:" + Miscellaneous.GetStr(etCp), R.string.accept, new Popups.DialogMessage() {
                                                @Override
                                                public void OnClickListener(AlertDialog dialog) {
                                                    dialog.dismiss();
                                                    if (NetworkStatus.haveNetworkConnection(ctx)) {
                                                        ConsultarDatos(true);
                                                    } else {
                                                        final AlertDialog dlgSave = Popups.showDialogMessage(ctx, not_network,
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
                                                        dlgSave.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
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
                                    solicitud.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                    solicitud.show();
                                }
                            } else
                                etCurp.setError("No corresponde a una Curp válida");
                        }
                    } else
                        tvGenero.setError("");
                }
            } else
                tvProducto.setError("");
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

        String nombres = Miscellaneous.GetStr(etPriNombre) + " " + Miscellaneous.GetStr(etSegNombre);
        params.put(0, nombres.trim());
        params.put(1, Miscellaneous.GetStr(etApPaterno));
        params.put(2, Miscellaneous.GetStr(etApMaterno));
        params.put(3, Miscellaneous.GetStr(tvFechaNac));

        if (rgGenero.getCheckedRadioButtonId()==R.id.rbHombre)
            params.put(4, "Hombre");
        else if (rgGenero.getCheckedRadioButtonId()==R.id.rbMujer)
            params.put(4, "Mujer");
        else
            params.put(4, "");

        params.put(5, Miscellaneous.GetStr(tvEstadoNac));

        etCurp.setText(Miscellaneous.GenerarCurp(params));
        tvRfc.setText(Miscellaneous.GenerarCurp(params));
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
                !Miscellaneous.GetStr(tvDireccion).isEmpty() ||
                !Miscellaneous.GetStr(etCp).isEmpty() ||
                !Miscellaneous.GetStr(tvColonia).isEmpty() ||
                !Miscellaneous.GetStr(tvMunicipio).isEmpty() ||
                !Miscellaneous.GetStr(etCiudad).isEmpty() ||
                !Miscellaneous.GetStr(tvEstado).isEmpty()

        )
            isClose = false;

        return isClose;
    }
    private void getConsultas( String where){
        // rvClientesCC.setAdapter(null);

        //  String sql = "SELECT rr.*, COALESCE(r1.folio, '') FROM " + TBL_RECUPERACION_RECIBOS_CC + " AS rr LEFT JOIN "+TBL_RECIBOS_CC+" AS r1 on r1.tipo_credito = rr.tipo_credito AND r1.curp = rr.curp AND r1.nombre_dos = rr.nombre_dos AND r1.tipo_impresion = 'O' WHERE rr.estatus in (1,2) "+where+" ORDER BY rr.fecha_termino DESC, rr.fecha_envio DESC";
        String sql = "SELECT rr.* FROM " + TBL_CONSULTA_CC + " AS rr"+" ORDER BY rr.fecha_termino DESC, rr.fecha_envio DESC";

        final Cursor row = db.rawQuery(sql, null);

        if (row.getCount() > 0){
            row.moveToFirst();
            ArrayList<HashMap<Integer, String>> circulo = new ArrayList<>();
            for (int i = 0; i < row.getCount(); i++){

                HashMap<Integer, String> items = new HashMap<>();
                items.put(0, row.getString(0)); //_id
                items.put(1, row.getString(1)); //PRODUCTO
                items.put(2, row.getString(2));  //MONTO_SOLICITADO
                items.put(3, row.getString(3));  //PRIMER_NOMBRE
                items.put(4, row.getString(4));  //SEGUNDO_NOMBRE
                items.put(5, row.getString(5)); // AP_PARTERNO
                items.put(6, row.getString(6));  //AP_MATERNO
                items.put(7, row.getString(7)); //FECHA_NACIMIENTO
                items.put(8, row.getString(9)); //GENERO
                items.put(9, row.getString(10)); //ESTADO_NACIMIENTO
                items.put(10, row.getString(11)); //CURP
                items.put(11, row.getString(12)); //RFC
                items.put(12, row.getString(13)); //DIRECCION
                items.put(13, row.getString(14)); //COLONIA
                items.put(14, row.getString(15)); //MUNICIPIO
                items.put(15, row.getString(16)); //CIUDAD
                items.put(16, row.getString(17)); //ESTADO
                items.put(17, row.getString(18)); //CP
                items.put(18, row.getString(19)); //FECHA_TERMINO
                items.put(19, row.getString(20)); //FECHA_ENVIO
                items.put(20, row.getString(21)); //SALDO ACTUAL
                items.put(21, row.getString(22)); //SALDO VENCIDO
                items.put(22, row.getString(23)); //PEOR_PAGO
                items.put(23, row.getString(24)); //CREDITOS_ABIERTOS
                items.put(24, row.getString(25)); //CREDITOS_CERRADOS
                items.put(25, row.getString(26)); //PREAUTORIZACION
                items.put(26, row.getString(27)); //ESTATUS
                items.put(27, row.getString(28)); //ERRORES
                circulo.add(items);
                row.moveToNext();
            }

            /*    adapter_gestiones_cc adapter = new adapter_gestiones_cc(ctx, circulo, new adapter_gestiones_cc.Event() {
             @Override
               public void FichaOnClick(HashMap<Integer, String> item)
                {
                 //   CirculoCreditdo gestionCirculoCreditoDao = new GestionCirculoCreditoDao(ctx);
                 //   GestionCirculoCredito Consultacc = gestionCirculoCreditoDao.findByCurp(item.get(3));
               //     if(gestionCC == null) gestionCC = new GestionCirculoCredito();

                    Intent i_cc = new Intent(getApplicationContext(), CirculoCreditoActivity.class);
                    i_cc.putExtra("terminado", true);
                    i_cc.putExtra("tipo_credito", item.get(1));
                    i_cc.putExtra("cliente_grupo", item.get(2));
                    i_cc.putExtra("aval_representante", item.get(4));
                    i_cc.putExtra("curp", item.get(3));
                    i_cc.putExtra("id_respuesta", Long.parseLong(item.get(0)));
                    i_cc.putExtra("circulo_credito", gestionCC);
                    startActivity(i_cc);

                }
            });*/
            // rvClientesCC.setAdapter(adapter);
        }
        row.close();
    }

}
