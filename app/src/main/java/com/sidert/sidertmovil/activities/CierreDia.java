package com.sidert.sidertmovil.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.sidert.sidertmovil.R;

public class CierreDia extends AppCompatActivity {

    private Toolbar tbMain;

    private Context ctx;
    private Context context;

    private EditText etNoPrestamo;
    private EditText etNombreGrupo;
    private EditText etNoCliente;
    private EditText etNombreCliente;
    private EditText etFechaPago;
    private EditText etPagoRealizado;
    private EditText etNoRecibo;
    private EditText etBanco;
    private EditText etMontoDepositado;

    private ImageButton ibFotoTicket;

    private String[] _banks;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cierra_dia);
        context             = this;
        ctx                 = getApplicationContext();
        tbMain              = findViewById(R.id.tbMain);
        etNoPrestamo        = findViewById(R.id.etNoPrestamo);
        etNombreGrupo       = findViewById(R.id.etNombreGrupo);
        etNoCliente         = findViewById(R.id.etNoCliente);
        etNombreCliente     = findViewById(R.id.etNombreCliente);
        etFechaPago         = findViewById(R.id.etFechaPago);
        etPagoRealizado     = findViewById(R.id.etPagoRealizado);
        etNoRecibo          = findViewById(R.id.etNoRecibo);
        etBanco             = findViewById(R.id.etBanco);
        etMontoDepositado   = findViewById(R.id.etMontoDepositado);
        ibFotoTicket        = findViewById(R.id.ibFotoTicket);

        setSupportActionBar(tbMain);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("Cierre del Día");

        _banks = ctx.getResources().getStringArray(R.array.banks);

        etBanco.setOnClickListener(etBank_OnClick);
        ibFotoTicket.setOnClickListener(ibFotoTicket_OnClick);

    }

    private View.OnClickListener etBank_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle(R.string.selected_option)
                    .setItems(R.array.banks, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int position) {
                            etBanco.setText(_banks[position]);
                        }
                    });
            builder.create();
            builder.show();
        }
    };



    private View.OnClickListener ibFotoTicket_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.v("Monto", etMontoDepositado.getText().toString().replace("$",""));
        }
    };

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
