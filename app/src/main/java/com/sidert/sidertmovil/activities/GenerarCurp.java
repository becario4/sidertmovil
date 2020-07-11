package com.sidert.sidertmovil.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.database.DBhelper;
import com.sidert.sidertmovil.fragments.dialogs.dialog_date_picker;
import com.sidert.sidertmovil.models.MFormatoRecibo;
import com.sidert.sidertmovil.models.MPlazos;
import com.sidert.sidertmovil.models.ModeloCatalogoGral;
import com.sidert.sidertmovil.utils.Constants;
import com.sidert.sidertmovil.utils.Miscellaneous;
import com.sidert.sidertmovil.utils.Popups;
import com.sidert.sidertmovil.utils.SessionManager;
import com.sidert.sidertmovil.utils.Validator;
import com.sidert.sidertmovil.utils.ValidatorTextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Objects;

import static com.sidert.sidertmovil.utils.Constants.ESTADOS;
import static com.sidert.sidertmovil.utils.Constants.FORMAT_DATE_GNRAL;
import static com.sidert.sidertmovil.utils.Constants.REQUEST_CODE_ESTADO_NAC;
import static com.sidert.sidertmovil.utils.Constants.TBL_PLAZOS_PRESTAMOS;
import static com.sidert.sidertmovil.utils.Constants.TBL_RECIBOS;
import static com.sidert.sidertmovil.utils.Constants.TICKET_CC;
import static com.sidert.sidertmovil.utils.Constants.TIPO;
import static com.sidert.sidertmovil.utils.NameFragments.DIALOGDATEPICKER;

public class GenerarCurp extends AppCompatActivity {

    private Context ctx;

    private String[] _sexo;

    private LinearLayout llPlazo;

    private Spinner spPlazo;

    private EditText etNombre;
    private EditText etPaterno;
    private EditText etMaterno;

    private TextView tvFechaNac;
    private TextView tvEstadoNac;
    private TextView tvSexo;
    private TextView tvCurp;

    private EditText etDigito;

    private Button btnGuardar;

    private SessionManager session;

    private Validator validator;
    private ValidatorTextView validatorTV;

    private Calendar myCalendar = Calendar.getInstance();
    private SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_DATE_GNRAL);

    private DBhelper dBhelper;
    private SQLiteDatabase db;

    private ArrayList<MPlazos> _plazos = new ArrayList<>();

    private String tipo = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generar_curp);

        ctx = this;

        dBhelper = new DBhelper(ctx);
        db = dBhelper.getWritableDatabase();

        session = new SessionManager(ctx);

        _sexo = getResources().getStringArray(R.array.sexo);

        llPlazo = findViewById(R.id.llPlazo);

        spPlazo = findViewById(R.id.spPlazo);

        etNombre    = findViewById(R.id.etNombre);
        etPaterno   = findViewById(R.id.etPaterno);
        etMaterno   = findViewById(R.id.etMaterno);

        tvFechaNac  = findViewById(R.id.tvFechaNac);
        tvEstadoNac = findViewById(R.id.tvEstadoNac);
        tvSexo      = findViewById(R.id.tvSexo);
        tvCurp      = findViewById(R.id.tvCurp);

        etDigito    = findViewById(R.id.etDigito);

        btnGuardar  = findViewById(R.id.btnGuardar);

        etNombre.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                HashMap<Integer, String> params = new HashMap<>();
                if (s.length()> 0){
                    params.put(0, s.toString());
                    params.put(1, etPaterno.getText().toString());
                    params.put(2, etMaterno.getText().toString());
                    params.put(3, tvFechaNac.getText().toString());
                    params.put(4, tvSexo.getText().toString());
                    params.put(5, tvEstadoNac.getText().toString().trim());

                    tvCurp.setText(Miscellaneous.GenerarCurp(params));
                }
                else{
                    params.put(0, "");
                    params.put(1, etPaterno.getText().toString());
                    params.put(2, etMaterno.getText().toString());
                    params.put(3, tvFechaNac.getText().toString());
                    params.put(4, tvSexo.getText().toString());
                    params.put(5, tvEstadoNac.getText().toString().trim());

                    tvCurp.setText(Miscellaneous.GenerarCurp(params));
                }
            }
        });
        etPaterno.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                HashMap<Integer, String> params = new HashMap<>();
                if (s.length()> 0){
                    params.put(0, etNombre.getText().toString());
                    params.put(1, s.toString());
                    params.put(2, etMaterno.getText().toString());
                    params.put(3, tvFechaNac.getText().toString());
                    params.put(4, tvSexo.getText().toString());
                    params.put(5, tvEstadoNac.getText().toString().trim());

                    tvCurp.setText(Miscellaneous.GenerarCurp(params));
                }
                else{
                    params.put(0, etNombre.getText().toString());
                    params.put(1, "");
                    params.put(2, etMaterno.getText().toString());
                    params.put(3, tvFechaNac.getText().toString());
                    params.put(4, tvSexo.getText().toString());
                    params.put(5, tvEstadoNac.getText().toString().trim());

                    tvCurp.setText(Miscellaneous.GenerarCurp(params));
                }
            }
        });
        etMaterno.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                HashMap<Integer, String> params = new HashMap<>();
                if (s.length()> 0){
                    params.put(0, etNombre.getText().toString());
                    params.put(1, etPaterno.getText().toString());
                    params.put(2, s.toString());
                    params.put(3, tvFechaNac.getText().toString());
                    params.put(4, tvSexo.getText().toString());
                    params.put(5, tvEstadoNac.getText().toString().trim());

                    tvCurp.setText(Miscellaneous.GenerarCurp(params));
                }
                else{
                    params.put(0, etNombre.getText().toString());
                    params.put(1, etPaterno.getText().toString());
                    params.put(2, "");
                    params.put(3, tvFechaNac.getText().toString());
                    params.put(4, tvSexo.getText().toString());
                    params.put(5, tvEstadoNac.getText().toString().trim());

                    tvCurp.setText(Miscellaneous.GenerarCurp(params));
                }
            }
        });
        tvFechaNac.setOnClickListener(tvFechaNac_OnClick);
        tvEstadoNac.setOnClickListener(tvEstado_OnClick);
        tvSexo.setOnClickListener(tvSexo_OnClick);
        btnGuardar.setOnClickListener(btnGuardar_OnClick);

        tipo = getIntent().getStringExtra(TIPO);

        if (tipo.equals("CC"))
            llPlazo.setVisibility(View.GONE);

        String sql = "SELECT * FROM " + TBL_PLAZOS_PRESTAMOS + " WHERE estatus = ?";
        Cursor row = db.rawQuery(sql, new String[]{"1"});
        MPlazos plazo = new MPlazos();
        plazo.setNombre("SELECCIONE UNA OPCIÓN");
        plazo.setId(0);
        plazo.setNumeroMeses(0);
        _plazos.add(plazo);
        if (row.getCount() > 0){
            row.moveToFirst();
            for (int i = 0; i < row.getCount(); i++){
                plazo = new MPlazos();
                plazo.setNombre(row.getString(2));
                plazo.setId(row.getInt(1));
                plazo.setNumeroMeses(row.getInt(3));
                _plazos.add(plazo);
                row.moveToNext();
            }
        }
        row.close();
        ArrayAdapter<MPlazos> adapter = new ArrayAdapter<>(ctx, android.R.layout.simple_list_item_1, _plazos);
        spPlazo.setAdapter(adapter);

    }

    private View.OnClickListener tvFechaNac_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            dialog_date_picker dialogDatePicker = new dialog_date_picker();
            Bundle b = new Bundle();

            b.putInt(Constants.YEAR_CURRENT, myCalendar.get(Calendar.YEAR));
            b.putInt(Constants.MONTH_CURRENT, myCalendar.get(Calendar.MONTH));
            b.putInt(Constants.DAY_CURRENT, myCalendar.get(Calendar.DAY_OF_MONTH));
            b.putString(Constants.DATE_CURRENT, sdf.format(myCalendar.getTime()));
            b.putInt(Constants.IDENTIFIER, 13);
            b.putBoolean(Constants.FECHAS_POST, false);
            dialogDatePicker.setArguments(b);
            dialogDatePicker.show(getSupportFragmentManager(), DIALOGDATEPICKER);
        }
    };

    private View.OnClickListener tvEstado_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent i_estados = new Intent(ctx, Catalogos.class);
            i_estados.putExtra(Constants.TITULO, Miscellaneous.ucFirst(ESTADOS));
            i_estados.putExtra(Constants.CATALOGO, ESTADOS);
            i_estados.putExtra(Constants.REQUEST_CODE, REQUEST_CODE_ESTADO_NAC);
            startActivityForResult(i_estados, REQUEST_CODE_ESTADO_NAC);
        }
    };

    private View.OnClickListener tvSexo_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
            builder.setTitle(R.string.selected_option)
                    .setItems(R.array.sexo, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int position) {
                            tvSexo.setError(null);
                            tvSexo.setText(_sexo[position]);

                            HashMap<Integer, String> params = new HashMap<>();

                            params.put(0, etNombre.getText().toString());
                            params.put(1, etPaterno.getText().toString());
                            params.put(2, etMaterno.getText().toString());
                            params.put(3, tvFechaNac.getText().toString());
                            params.put(4, tvSexo.getText().toString());
                            params.put(5, tvEstadoNac.getText().toString().trim());

                            tvCurp.setText(Miscellaneous.GenerarCurp(params));
                        }
                    });
            builder.create();
            builder.show();
        }
    };

    private View.OnClickListener btnGuardar_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            validator = new Validator();
            validatorTV = new ValidatorTextView();
            if ((tipo.equals("AGF") && spPlazo.getSelectedItemPosition() > 0) || tipo.equals("CC")) {
                if (!validator.validate(etNombre, new String[]{validator.REQUIRED, validator.ONLY_TEXT}) &&
                        !validator.validate(etPaterno, new String[]{validator.REQUIRED, validator.ONLY_TEXT}) &&
                        !validator.validate(etMaterno, new String[]{validator.ONLY_TEXT}) &&
                        !validatorTV.validate(tvFechaNac, new String[]{validatorTV.REQUIRED}) &&
                        !validatorTV.validate(tvEstadoNac, new String[]{validatorTV.REQUIRED}) &&
                        !validatorTV.validate(tvSexo, new String[]{validatorTV.REQUIRED}) &&
                        !validator.validate(etDigito, new String[]{validator.REQUIRED})) {
                    if (Miscellaneous.CurpValidador(tvCurp.getText().toString().trim() + etDigito.getText().toString().trim())) {
                        AlertDialog guardar_info_dlg = Popups.showDialogConfirm(ctx, Constants.question,
                                R.string.datos_correctos, R.string.yes, new Popups.DialogMessage() {
                                    @Override
                                    public void OnClickListener(AlertDialog dialog) {
                                        ImprimirTicket();
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
                    } else {
                        etDigito.setError("No es una Curp válida");
                    }
                }
            }
            else
                Toast.makeText(ctx, "Seleccione el plazo del prestamo", Toast.LENGTH_SHORT).show();
        }
    };

    private void ImprimirTicket (){
        String nombre = etNombre.getText().toString().trim().toUpperCase() + " " +
                etPaterno.getText().toString().trim().toUpperCase() + " " +
                etMaterno.getText().toString().trim().toUpperCase();

        MFormatoRecibo mRecibo = new MFormatoRecibo();
        mRecibo.setNombreCliente(nombre.trim());
        mRecibo.setTipoRecibo(tipo);

        mRecibo.setAsesorId(session.getUser().get(0));
        mRecibo.setClave("");
        mRecibo.setIdPrestamo("");
        mRecibo.setCurp(tvCurp.getText().toString().trim()+etDigito.getText().toString().trim());

        Cursor row = dBhelper.getRecords(TBL_RECIBOS, " WHERE curp = ? AND tipo_recibo = ?", "", new String[]{tvCurp.getText().toString().trim()+etDigito.getText().toString().trim(), tipo});

        switch (row.getCount()) {
            case 0:
                mRecibo.setTipoImpresion(true);
                mRecibo.setResImpresion(0);
                mRecibo.setFolio("");
                mRecibo.setReeimpresion(false);
                if (tipo.equals("CC"))
                    mRecibo.setMonto("17.5");
                else{
                    double seguroAGF = 15;
                    for (int i = 0; i < session.getSucursales().length(); i++) {
                        try {
                            JSONObject item = session.getSucursales().getJSONObject(i);
                            if (item.getString("nombre").equals("2.2 MECAPALAPA") || item.getString("nombre").equals("2.3 LA MESA")) {
                                seguroAGF = 12.5;
                                break;
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    mRecibo.setMonto(String.valueOf((((MPlazos)spPlazo.getSelectedItem()).getNumeroMeses() * seguroAGF)));
                }
                break;
            case 1:
                row.moveToFirst();
                mRecibo.setTipoImpresion(false);
                mRecibo.setResImpresion(1);
                mRecibo.setFolio(row.getString(5));
                mRecibo.setReeimpresion(false);
                mRecibo.setMonto(row.getString(6));
                break;
            case 2:
                row.moveToFirst();
                mRecibo.setTipoImpresion(true);
                mRecibo.setResImpresion(2);
                mRecibo.setFolio(row.getString(5));
                mRecibo.setReeimpresion(true);
                mRecibo.setMonto(row.getString(6));
                break;

        }
        row.close();

        Intent i_formato_recibo = new Intent(ctx, FormatoRecibos.class);
        i_formato_recibo.putExtra(TICKET_CC, mRecibo);
        startActivity(i_formato_recibo);
        finish();
    }

    public void setDate(String date){
        tvFechaNac.setError(null);
        tvFechaNac.setText(date);

        HashMap<Integer, String> params = new HashMap<>();

        params.put(0, etNombre.getText().toString());
        params.put(1, etPaterno.getText().toString());
        params.put(2, etMaterno.getText().toString());
        params.put(3, tvFechaNac.getText().toString());
        params.put(4, tvSexo.getText().toString());
        params.put(5, tvEstadoNac.getText().toString().trim());

        tvCurp.setText(Miscellaneous.GenerarCurp(params));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE_ESTADO_NAC:
                if (resultCode == REQUEST_CODE_ESTADO_NAC) {
                    if (data != null) {
                        tvEstadoNac.setError(null);
                        tvEstadoNac.setText(((ModeloCatalogoGral) data.getSerializableExtra(Constants.ITEM)).getNombre());
                        HashMap<Integer, String> params = new HashMap<>();

                        params.put(0, etNombre.getText().toString());
                        params.put(1, etPaterno.getText().toString());
                        params.put(2, etMaterno.getText().toString());
                        params.put(3, tvFechaNac.getText().toString());
                        params.put(4, tvSexo.getText().toString());
                        params.put(5, tvEstadoNac.getText().toString().trim());

                        tvCurp.setText(Miscellaneous.GenerarCurp(params));

                    }
                }
                break;
        }
    }
}
