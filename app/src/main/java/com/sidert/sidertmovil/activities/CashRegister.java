package com.sidert.sidertmovil.activities;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.EditText;

import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.utils.CustomWatcherTotal;
import com.sidert.sidertmovil.utils.Miscellaneous;

public class CashRegister extends AppCompatActivity {

    private Toolbar tbMain;

    private Context ctx;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cash_register);
        ctx     = getApplicationContext();

        tbMain              = findViewById(R.id.tbMain);
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
        setTitle("Arqueo de Caja");

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
        double pagoRealizado = getIntent().getDoubleExtra("PagoRealizado",0);

        etPagoRealizado.setText(Miscellaneous.moneyFormat(String.valueOf(pagoRealizado)));

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

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
