package com.sidert.sidertmovil.activities;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.widget.EditText;

import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.database.DBhelper;
import com.sidert.sidertmovil.utils.Constants;
import com.sidert.sidertmovil.utils.CustomWatcherTotal;
import com.sidert.sidertmovil.utils.Popups;

import java.util.HashMap;
import java.util.Objects;

import static com.sidert.sidertmovil.utils.Constants.ENVIROMENT;
import static com.sidert.sidertmovil.utils.Constants.ID_GESTION;
import static com.sidert.sidertmovil.utils.Constants.TBL_ARQUEO_CAJA;
import static com.sidert.sidertmovil.utils.Constants.TBL_ARQUEO_CAJA_T;

public class ArqueoDeCaja extends AppCompatActivity {

    private DBhelper dBhelper;
    private SQLiteDatabase db;

    private EditText etPagoRealizado;
    private EditText etBmil;
    private EditText etBquinientos;
    private EditText etBdocientos;
    private EditText etBcien;
    private EditText etBcincuenta;
    private EditText etBveinte;
    private EditText etPdiez;
    private EditText etPcinco;
    private EditText etPdos;
    private EditText etPuno;
    private EditText etCcincuenta;
    private EditText etCveinte;
    private EditText etCdiez;
    private EditText etTotal;

    private String id_gestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arqueo_de_caja);
        Context ctx = getApplicationContext();
        dBhelper = new DBhelper(ctx);
        db = dBhelper.getWritableDatabase();

        Toolbar tbMain = findViewById(R.id.tbMain);

        EditText etNombreGrupo = findViewById(R.id.etNombreGrupo);
        etPagoRealizado     = findViewById(R.id.etPagoRealizado);
        etBmil              = findViewById(R.id.etBmil);
        etBquinientos       = findViewById(R.id.etBquinientos);
        etBdocientos        = findViewById(R.id.etBdoscientos);
        etBcien             = findViewById(R.id.etBcien);
        etBcincuenta        = findViewById(R.id.etBcincuenta);
        etBveinte           = findViewById(R.id.etBveinte);
        EditText etTotalBilletes = findViewById(R.id.etTotalBilletes);
        etPdiez             = findViewById(R.id.etPdiez);
        etPcinco            = findViewById(R.id.etPcinco);
        etPdos              = findViewById(R.id.etPdos);
        etPuno              = findViewById(R.id.etPuno);
        etCcincuenta        = findViewById(R.id.etCcincuenta);
        etCveinte           = findViewById(R.id.etCveinte);
        etCdiez             = findViewById(R.id.etCdiez);
        EditText etTotalMonedas = findViewById(R.id.etTotalMonedas);
        etTotal             = findViewById(R.id.etTotal);
        EditText etCambio = findViewById(R.id.etCambio);

        setSupportActionBar(tbMain);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle(getResources().getString(R.string.arqueo_caja));

        EditText[] etArr = new EditText[] {
                etBmil,
                etBquinientos,
                etBdocientos,
                etBcien,
                etBcincuenta,
                etBveinte,
                etTotalBilletes,
                etPdiez,
                etPcinco,
                etPdos,
                etPuno,
                etCcincuenta,
                etCveinte,
                etCdiez,
                etTotalMonedas,
                etTotal,
                etCambio
        };
        double pagoRealizado = getIntent().getDoubleExtra(Constants.PAGO_REALIZADO,0);

        id_gestion = getIntent().getStringExtra(ID_GESTION);

        etNombreGrupo.setText(getIntent().getStringExtra(Constants.NOMBRE_GRUPO));
        etPagoRealizado.setText(String.valueOf(pagoRealizado));

        etBmil.addTextChangedListener(new CustomWatcherTotal(pagoRealizado,etArr));
        etBquinientos.addTextChangedListener(new CustomWatcherTotal(pagoRealizado,etArr));
        etBdocientos.addTextChangedListener(new CustomWatcherTotal(pagoRealizado,etArr));
        etBcien.addTextChangedListener(new CustomWatcherTotal(pagoRealizado,etArr));
        etBcincuenta.addTextChangedListener(new CustomWatcherTotal(pagoRealizado,etArr));
        etBveinte.addTextChangedListener(new CustomWatcherTotal(pagoRealizado,etArr));
        etPdiez.addTextChangedListener(new CustomWatcherTotal(pagoRealizado,etArr));
        etPcinco.addTextChangedListener(new CustomWatcherTotal(pagoRealizado,etArr));
        etPdos.addTextChangedListener(new CustomWatcherTotal(pagoRealizado,etArr));
        etPuno.addTextChangedListener(new CustomWatcherTotal(pagoRealizado,etArr));
        etCcincuenta.addTextChangedListener(new CustomWatcherTotal(pagoRealizado,etArr));
        etCveinte.addTextChangedListener(new CustomWatcherTotal(pagoRealizado,etArr));
        etCdiez.addTextChangedListener(new CustomWatcherTotal(pagoRealizado,etArr));

        Cursor row;
        if (ENVIROMENT)
            row = dBhelper.getRecords(TBL_ARQUEO_CAJA, " WHERE id_gestion = ?", "", new String[]{id_gestion});
        else
            row = dBhelper.getRecords(TBL_ARQUEO_CAJA_T, " WHERE id_gestion = ?", "", new String[]{id_gestion});

        if (row.getCount() > 0){
            row.moveToFirst();

            etBmil.setText(row.getString(2));
            etBquinientos.setText(row.getString(3));
            etBdocientos.setText(row.getString(4));
            etBcien.setText(row.getString(5));
            etBcincuenta.setText(row.getString(6));
            etBveinte.setText(row.getString(7));
            etPdiez.setText(row.getString(8));
            etPcinco.setText(row.getString(9));
            etPdos.setText(row.getString(10));
            etPuno.setText(row.getString(11));
            etCcincuenta.setText(row.getString(12));
            etCveinte.setText(row.getString(13));
            etCdiez.setText(row.getString(14));
        }
        row.close();

    }

    private void guardarArqueCaja (){
        final Intent i_save_caja = new Intent();
        final AlertDialog dialog_confirm = Popups.showDialogConfirm(ArqueoDeCaja.this, Constants.question,
                R.string.guardar_cambios, R.string.save, new Popups.DialogMessage() {
            @Override
            public void OnClickListener(AlertDialog dialog) {
                if (Double.parseDouble(etPagoRealizado.getText().toString()) >= Double.parseDouble(etTotal.getText().toString())) {
                    boolean b = ArqueoCaja();
                    i_save_caja.putExtra(Constants.SAVE, b);
                    setResult(RESULT_OK, i_save_caja);
                    finish();
                    dialog.dismiss();
                }
                else {
                    dialog.dismiss();
                    AlertDialog dlg = Popups.showDialogMessage(ArqueoDeCaja.this, "default", R.string.mess_arqueo_caja, R.string.accept, new Popups.DialogMessage() {
                        @Override
                        public void OnClickListener(AlertDialog dialog) {
                            dialog.dismiss();
                        }
                    });
                    Objects.requireNonNull(dlg.getWindow()).requestFeature(Window.FEATURE_NO_TITLE);
                    dlg.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                    dlg.show();
                }
            }
        }, R.string.cancel, new Popups.DialogMessage() {
            @Override
            public void OnClickListener(AlertDialog dialog) {
                i_save_caja.putExtra(Constants.SAVE,false);
                setResult(RESULT_OK, i_save_caja);
                finish();
                dialog.dismiss();
            }
        });
        Objects.requireNonNull(dialog_confirm.getWindow()).requestFeature(Window.FEATURE_NO_TITLE);
        dialog_confirm.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog_confirm.show();
    }

    private boolean ArqueoCaja(){
        Cursor row;
        if (ENVIROMENT)
            row = dBhelper.getRecords(TBL_ARQUEO_CAJA, " WHERE id_gestion = ?", "", new String[]{id_gestion});
        else
            row = dBhelper.getRecords(TBL_ARQUEO_CAJA_T, " WHERE id_gestion = ?", "", new String[]{id_gestion});

        if (row.getCount() > 0){ //Actualiza
            ContentValues cv = new ContentValues();
            cv.put("mil", etBmil.getText().toString().trim());
            cv.put("quinientos", etBquinientos.getText().toString().trim());
            cv.put("doscientos", etBdocientos.getText().toString().trim());
            cv.put("cien", etBcien.getText().toString().trim());
            cv.put("cincuenta", etBcincuenta.getText().toString().trim());
            cv.put("veinte", etBveinte.getText().toString().trim());
            cv.put("diez", etPdiez.getText().toString().trim());
            cv.put("cinco", etPcinco.getText().toString().trim());
            cv.put("dos", etPdos.getText().toString().trim());
            cv.put("peso", etPuno.getText().toString().trim());
            cv.put("c_cincuenta", etCcincuenta.getText().toString().trim());
            cv.put("c_veinte", etCveinte.getText().toString().trim());
            cv.put("c_diez", etCdiez.getText().toString().trim());

            if (ENVIROMENT)
                db.update(TBL_ARQUEO_CAJA, cv, "id_gestion = ?", new String[]{id_gestion});
            else
                db.update(TBL_ARQUEO_CAJA_T, cv, "id_gestion = ?", new String[]{id_gestion});
        }
        else{ //Registra
            HashMap<Integer, String> params = new HashMap<>();
            params.put(0, id_gestion);
            params.put(1, etBmil.getText().toString().trim());
            params.put(2, etBquinientos.getText().toString().trim());
            params.put(3, etBdocientos.getText().toString().trim());
            params.put(4, etBcien.getText().toString().trim());
            params.put(5, etBcincuenta.getText().toString().trim());
            params.put(6, etBveinte.getText().toString().trim());
            params.put(7, etPdiez.getText().toString().trim());
            params.put(8, etPcinco.getText().toString().trim());
            params.put(9, etPdos.getText().toString().trim());
            params.put(10, etPuno.getText().toString().trim());
            params.put(11, etCcincuenta.getText().toString().trim());
            params.put(12, etCveinte.getText().toString().trim());
            params.put(13, etCdiez.getText().toString().trim());

            dBhelper.saveArqueoCaja(db, params);
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_save, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.save:
                if (!etTotal.getText().toString().isEmpty()){
                    guardarArqueCaja();
                }
                else {
                    final AlertDialog dialog_error = Popups.showDialogMessage(ArqueoDeCaja.this,
                            Constants.money, R.string.error_guardar_arqueo,
                            R.string.accept, new Popups.DialogMessage() {
                                @Override
                                public void OnClickListener(AlertDialog dialog) {
                                    dialog.dismiss();
                                }
                            });
                    Objects.requireNonNull(dialog_error.getWindow()).requestFeature(Window.FEATURE_NO_TITLE);
                    dialog_error.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                    dialog_error.show();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
