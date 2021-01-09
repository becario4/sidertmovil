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
import com.sidert.sidertmovil.utils.Miscellaneous;
import com.sidert.sidertmovil.utils.Popups;

import java.util.HashMap;
import java.util.Objects;

import static com.sidert.sidertmovil.utils.Constants.ID_GESTION;
import static com.sidert.sidertmovil.utils.Constants.TBL_ARQUEO_CAJA_T;

/**Clase para realizar un arqueo de caja para los clientes que pagaron en efectivo y pagos mayores a $10,000*/
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
    
    private Miscellaneous m;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arqueo_de_caja);
        Context ctx = getApplicationContext();
        dBhelper = new DBhelper(ctx);
        db = dBhelper.getWritableDatabase();
        
        m = new Miscellaneous();

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

        /**Se crea un array de objetos de tipo EditText para evento de listener*/
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

        /**Coloca el nombre del grupo y el pago realizado */
        etNombreGrupo.setText(getIntent().getStringExtra(Constants.NOMBRE_GRUPO));
        etPagoRealizado.setText(String.valueOf(pagoRealizado));

        /**Evento al momento de ingresar valores a los edittext para hacer la suma de los billetes y/o monedas ingresadas
         * de manera automatica*/
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
        /**-------------------------------------------------------------------------------*/

        /**Si ya se habia guardado un arque de caja se busca para obtener los valores ya registrados*/
        Cursor row = dBhelper.getRecords(TBL_ARQUEO_CAJA_T, " WHERE id_gestion = ?", "", new String[]{id_gestion});

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

    /**Funcion para guardar los datos de arqueo de caja*/
    private void guardarArqueCaja (){
        final Intent i_save_caja = new Intent();
        final AlertDialog dialog_confirm = Popups.showDialogConfirm(ArqueoDeCaja.this, Constants.question,
                R.string.guardar_cambios, R.string.save, new Popups.DialogMessage() {
            @Override
            public void OnClickListener(AlertDialog dialog) {
                /**Valida si el total de arqueo de caja es igual al monto registrado en la gestion se guarda*/
                if (Double.parseDouble(m.GetStr(etPagoRealizado)) >= Double.parseDouble(m.GetStr(etTotal))) {
                    boolean b = ArqueoCaja();
                    i_save_caja.putExtra(Constants.SAVE, b);
                    setResult(RESULT_OK, i_save_caja);
                    finish();
                    dialog.dismiss();
                }
                else {
                    dialog.dismiss();
                    /**Muestra mensaje de que no coinciden los montos*/
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

    /**Funcion para Guardar los datos de arqueo de caja o actualizar por si ya se habia registrado*/
    private boolean ArqueoCaja(){
        Cursor row = dBhelper.getRecords(TBL_ARQUEO_CAJA_T, " WHERE id_gestion = ?", "", new String[]{id_gestion});

        if (row.getCount() > 0){ //Actualiza
            ContentValues cv = new ContentValues();
            cv.put("mil", m.GetStr(etBmil));
            cv.put("quinientos", m.GetStr(etBquinientos));
            cv.put("doscientos", m.GetStr(etBdocientos));
            cv.put("cien", m.GetStr(etBcien));
            cv.put("cincuenta", m.GetStr(etBcincuenta));
            cv.put("veinte", m.GetStr(etBveinte));
            cv.put("diez", m.GetStr(etPdiez));
            cv.put("cinco", m.GetStr(etPcinco));
            cv.put("dos", m.GetStr(etPdos));
            cv.put("peso", m.GetStr(etPuno));
            cv.put("c_cincuenta", m.GetStr(etCcincuenta));
            cv.put("c_veinte", m.GetStr(etCveinte));
            cv.put("c_diez", m.GetStr(etCdiez));

            db.update(TBL_ARQUEO_CAJA_T, cv, "id_gestion = ?", new String[]{id_gestion});
        }
        else{ //Registra
            HashMap<Integer, String> params = new HashMap<>();
            params.put(0, id_gestion);
            params.put(1, m.GetStr(etBmil));
            params.put(2, m.GetStr(etBquinientos));
            params.put(3, m.GetStr(etBdocientos));
            params.put(4, m.GetStr(etBcien));
            params.put(5, m.GetStr(etBcincuenta));
            params.put(6, m.GetStr(etBveinte));
            params.put(7, m.GetStr(etPdiez));
            params.put(8, m.GetStr(etPcinco));
            params.put(9, m.GetStr(etPdos));
            params.put(10, m.GetStr(etPuno));
            params.put(11, m.GetStr(etCcincuenta));
            params.put(12, m.GetStr(etCveinte));
            params.put(13, m.GetStr(etCdiez));

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
            case android.R.id.home:/**Menu de retroceso del toolbar <- */
                finish();
                break;
            case R.id.save:
                /**Valida que el total sea diferente de vacio para continuar con la validacion de montos*/
                if (!m.GetStr(etTotal).isEmpty()){
                    guardarArqueCaja();
                }
                else {
                    /**Si no ha ingresado ninguna cantidad de billetes y/o monedas*/
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
