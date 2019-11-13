package com.sidert.sidertmovil.activities;

import android.content.Context;
import android.content.Intent;
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
import android.widget.TextView;

import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.utils.Constants;
import com.sidert.sidertmovil.utils.CustomWatcherTotal;
import com.sidert.sidertmovil.utils.Miscellaneous;
import com.sidert.sidertmovil.utils.Popups;
import com.sidert.sidertmovil.utils.Validator;

import java.util.Objects;

public class ArqueoDeCaja extends AppCompatActivity {

    private Toolbar tbMain;

    private Context ctx;

    private TextView tvExternalID;

    private EditText etNombreGrupo;
    private EditText etPagoRealizado;
    private EditText etBmil;
    private EditText etBquinientos;
    private EditText etBdocientos;
    private EditText etBcien;
    private EditText etBcincuenta;
    private EditText etBveinte;
    private EditText etTotalBilletes;
    private EditText etPdiez;
    private EditText etPcinco;
    private EditText etPdos;
    private EditText etPuno;
    private EditText etCcincuenta;
    private EditText etCveinte;
    private EditText etCdiez;
    private EditText etTotalMonedas;
    private EditText etTotal;
    private EditText etCambio;

    private Validator validator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arqueo_de_caja);
        ctx     = getApplicationContext();

        tbMain              = findViewById(R.id.tbMain);

        tvExternalID        = findViewById(R.id.tvExternalID);
        etNombreGrupo       = findViewById(R.id.etNombreGrupo);
        etPagoRealizado     = findViewById(R.id.etPagoRealizado);
        etBmil              = findViewById(R.id.etBmil);
        etBquinientos       = findViewById(R.id.etBquinientos);
        etBdocientos        = findViewById(R.id.etBdoscientos);
        etBcien             = findViewById(R.id.etBcien);
        etBcincuenta        = findViewById(R.id.etBcincuenta);
        etBveinte           = findViewById(R.id.etBveinte);
        etTotalBilletes     = findViewById(R.id.etTotalBilletes);
        etPdiez             = findViewById(R.id.etPdiez);
        etPcinco            = findViewById(R.id.etPcinco);
        etPdos              = findViewById(R.id.etPdos);
        etPuno              = findViewById(R.id.etPuno);
        etCcincuenta        = findViewById(R.id.etCcincuenta);
        etCveinte           = findViewById(R.id.etCveinte);
        etCdiez             = findViewById(R.id.etCdiez);
        etTotalMonedas      = findViewById(R.id.etTotalMonedas);
        etTotal             = findViewById(R.id.etTotal);
        etCambio            = findViewById(R.id.etCambio);

        setSupportActionBar(tbMain);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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

        tvExternalID.setText(getIntent().getStringExtra(Constants.ORDER_ID));
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

        validator = new Validator();

    }

    private void guardarArqueCaja (){
        final Intent i_save_caja = new Intent();
        final AlertDialog dialog_confirm = Popups.showDialogConfirm(ArqueoDeCaja.this, Constants.question,
                R.string.guardar_cambios, R.string.save, new Popups.DialogMessage() {
            @Override
            public void OnClickListener(AlertDialog dialog) {
                i_save_caja.putExtra(Constants.SAVE,true);
                setResult(RESULT_OK, i_save_caja);
                finish();
                dialog.dismiss();

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
