package com.sidert.sidertmovil.activities;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.adapters.TabsRecentsAdapter;
import com.sidert.sidertmovil.database.DBhelper;
import com.sidert.sidertmovil.fragments.view_pager.cvi_detalle_fragment;
import com.sidert.sidertmovil.fragments.view_pager.cvi_gestion_fragment;
import com.sidert.sidertmovil.fragments.view_pager.cvi_pagos_fragment;
import com.sidert.sidertmovil.fragments.view_pager.ri_gestion_fragment;
import com.sidert.sidertmovil.models.ModeloIndividual;
import com.sidert.sidertmovil.utils.Constants;
import com.sidert.sidertmovil.utils.CustomViewPager;
import com.sidert.sidertmovil.utils.Popups;
import com.sidert.sidertmovil.utils.Validator;

import org.json.JSONObject;

import java.util.Objects;

public class CarteraVencidaIndividual extends AppCompatActivity {

    private Context ctx;
    private Toolbar TBmain;
    private TabLayout mTabLayout;
    private boolean canExitApp = false;
    public boolean flagRespuesta = false;
    private CustomViewPager mViewPager;
    public ModeloIndividual ficha_cvi;
    private TabsRecentsAdapter adapter;
    private AlertDialog loading;
    int exit = 0;
    private DBhelper dBhelper;
    private SQLiteDatabase db;
    private String external_id = "";
    public int statusFicha = 0;
    public JSONObject jsonRes;
    private JSONObject jsonTemp;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Constants.ENVIROMENT)
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_catera_vencida_individual);

        ctx             = this;
        dBhelper = new DBhelper(ctx);
        db = dBhelper.getWritableDatabase();
        TBmain          = findViewById(R.id.TBmain);
        mTabLayout      = findViewById(R.id.mTabLayout);
        mViewPager      = findViewById(R.id.mViewPager);

        setSupportActionBar(TBmain);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle(getApplicationContext().getString(R.string.order));
        Bundle data = getIntent().getExtras();
        external_id = data.getString(Constants.ORDER_ID);
        Cursor row = dBhelper.getRecords(Constants.FICHAS_T, " WHERE external_id = " + external_id + "'", "", null);
        row.moveToFirst();

        if (row.getInt(23) == 2){

        }
        ficha_cvi     = (ModeloIndividual) data.getSerializable(Constants.INDIVIDUAL);
        setUpViewPager();
        Intent i_res = new Intent();
        i_res.putExtra(Constants.RESPONSE,1);
        setResult(RESULT_OK, i_res);
    }

    private void setUpViewPager() {
        TabsRecentsAdapter adapter = new TabsRecentsAdapter(getSupportFragmentManager());

        adapter.addFragment(new cvi_detalle_fragment(), "");
        adapter.addFragment(new cvi_gestion_fragment(), "");
        adapter.addFragment(new cvi_pagos_fragment(), "");

        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setVisibility(View.VISIBLE);
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);

        mViewPager.setSwipeable(false);
        mTabLayout.getTabAt(0).setIcon(R.drawable.ic_single).setContentDescription("Detalle");
        mTabLayout.getTabAt(1).setIcon(R.drawable.give_mone_white).setContentDescription("Gestión");
        mTabLayout.getTabAt(2).setIcon(R.drawable.ic_payment_log).setContentDescription("Historial de Pagos");
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
                final AlertDialog firma_dlg = Popups.showDialogConfirm(ctx, Constants.question,
                        R.string.guardar_cambios, R.string.accept, new Popups.DialogMessage() {
                            @Override
                            public void OnClickListener(AlertDialog dialog) {
                                loading = Popups.showLoadingDialog(ctx,R.string.please_wait, R.string.guardando_info);
                                loading.show();
                                GuardarTemp();
                                dialog.dismiss();

                            }
                        }, R.string.cancel, new Popups.DialogMessage() {
                            @Override
                            public void OnClickListener(AlertDialog dialog) {
                                finish();
                                dialog.dismiss();
                            }
                        });
                Objects.requireNonNull(firma_dlg.getWindow()).requestFeature(Window.FEATURE_NO_TITLE);
                firma_dlg.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                firma_dlg.show();
                break;
            case R.id.save:
                cvi_gestion_fragment cvi_data = ((cvi_gestion_fragment) mViewPager.getAdapter().instantiateItem(mViewPager, 1));
                GuardarGestion(cvi_data);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        final AlertDialog confirm_dlg = Popups.showDialogConfirm(ctx, Constants.question,
                R.string.guardar_cambios, R.string.accept, new Popups.DialogMessage() {
                    @Override
                    public void OnClickListener(AlertDialog dialog) {
                        loading = Popups.showLoadingDialog(ctx,R.string.please_wait, R.string.guardando_info);
                        loading.show();
                        GuardarTemp();
                        dialog.dismiss();

                    }
                }, R.string.cancel, new Popups.DialogMessage() {
                    @Override
                    public void OnClickListener(AlertDialog dialog) {
                        finish();
                        dialog.dismiss();
                    }
                });
        Objects.requireNonNull(confirm_dlg.getWindow()).requestFeature(Window.FEATURE_NO_TITLE);
        confirm_dlg.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        confirm_dlg.setCancelable(true);
        confirm_dlg.show();
    }

    private void GuardarTemp (){
        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
                loading.dismiss();
            }
        },3000);
    }

    private void GuardarGestion(cvi_gestion_fragment data){
        Validator validator = new Validator();
        Bundle b = new Bundle();
        if (data.latLngGestion != null){
            if (data.rgContactoCliente.getCheckedRadioButtonId() == R.id.rbSiContacto) { //Si Contacto cliente
                if (data.rgActualizarTelefono.getCheckedRadioButtonId() == R.id.rbSiActualizar || data.rgActualizarTelefono.getCheckedRadioButtonId() == R.id.rbNoActualizar){
                    if ((data.rgActualizarTelefono.getCheckedRadioButtonId() == R.id.rbSiActualizar && validator.validate(data.etActualizarTelefono, new String[]{validator.REQUIRED,validator.PHONE})) || data.rgActualizarTelefono.getCheckedRadioButtonId() == R.id.rbNoActualizar){
                        if (data.rgResultadoPago.getCheckedRadioButtonId() == R.id.rbPago){ // Si pago
                            if (data.spMedioPago.getSelectedItemPosition() > 0 && data.spMedioPago.getSelectedItemPosition() == 1 ){ // Medio de Banamex722
                                if (!data.etFechaDeposito.getText().toString().trim().isEmpty()){ //Fecha de deposito capturada
                                    if (data.rgPagaraRequerido.getCheckedRadioButtonId() == R.id.rbSiRequerido || data.rgPagaraRequerido.getCheckedRadioButtonId() == R.id.rbNoRequerido){ //Selecionó que pagará requerido o no requerido
                                        if (Double.parseDouble(data.etPagoRealizado.getText().toString()) > 0){ //El pago realizado es mayor a cero
                                            if (data.byteEvidencia != null){ //Ha capturado una evidencia (Fotografía al ticket)
                                                if (data.rgEstaGerente.getCheckedRadioButtonId() == R.id.rbSiGerente) { //Selecciono que si está el gerente
                                                    if (data.byteFirma != null) { //Capturó una firma
                                                        b.putDouble("LATITUD", data.latLngGestion.latitude);
                                                        b.putDouble("LONGITUD", data.latLngGestion.longitude);
                                                        b.putInt("CONTACTOCLIENTE", 1);
                                                        b.putInt("RESULTADOPAGO", 1);
                                                        b.putInt("POSMEDIOPAGO",data.spMedioPago.getSelectedItemPosition());
                                                        b.putString("MEDIOPAGO", data.spMedioPago.getSelectedItem().toString());
                                                        b.putString("FECHADEPOSITO", data.etFechaDeposito.getText().toString().trim());
                                                        b.putDouble("SALDOCORTE", ficha_cvi.getPrestamo().getSaldoActual());
                                                        b.putDouble("MONTOREQUERIDO", ficha_cvi.getPrestamo().getPagoSemanal());
                                                        b.putString("PAGOREALIZADO", data.etPagoRealizado.getText().toString().trim());
                                                        b.putByteArray("EVIDENCIA", data.byteEvidencia);
                                                        b.putBoolean("GERENTE", true);
                                                        b.putByteArray("FIRMA", data.byteFirma);
                                                    } else //No ha capturado la firma
                                                        Toast.makeText(ctx, "Capture la firma del gerente", Toast.LENGTH_SHORT).show();
                                                } else if (data.rgEstaGerente.getCheckedRadioButtonId() == R.id.rbNoGerente) { //No se encuentra el Gerente
                                                    b.putDouble("LATITUD", data.latLngGestion.latitude);
                                                    b.putDouble("LONGITUD", data.latLngGestion.longitude);
                                                    b.putInt("CONTACTOCLIENTE", 1);
                                                    b.putInt("RESULTADOPAGO", 1);
                                                    b.putInt("POSMEDIOPAGO",data.spMedioPago.getSelectedItemPosition());
                                                    b.putString("MEDIOPAGO", data.spMedioPago.getSelectedItem().toString());
                                                    b.putString("FECHADEPOSITO", data.etFechaDeposito.getText().toString().trim());
                                                    b.putString("PAGOREALIZADO", data.etPagoRealizado.getText().toString().trim());
                                                    b.putDouble("SALDOCORTE", ficha_cvi.getPrestamo().getSaldoActual());
                                                    b.putDouble("MONTOREQUERIDO", ficha_cvi.getPrestamo().getPagoSemanal());
                                                    b.putByteArray("EVIDENCIA", data.byteEvidencia);
                                                    b.putBoolean("GERENTE", false);
                                                } else //No ha seleccionado si está el gerente
                                                    Toast.makeText(ctx, "Confirme si el gerente está con usted", Toast.LENGTH_SHORT).show();
                                            }
                                            else //No ha capturado fotografía evidencia
                                                Toast.makeText(ctx, "No ha capturado una fotografía al ticket", Toast.LENGTH_SHORT).show();
                                        }
                                        else //El monto ingresado es igual a cero
                                            Toast.makeText(ctx, "No se pueden capturar montos iguales a cero", Toast.LENGTH_SHORT).show();
                                    }
                                    else //No ha seleccionado si pagará el pago requerido
                                        Toast.makeText(ctx, "No ha seleccionado si se pagará el requerido", Toast.LENGTH_SHORT).show();
                                }
                                else //No ha seleccionado la fecha de depostio
                                    Toast.makeText(ctx, "No ha seleccionado la fecha de deposito", Toast.LENGTH_SHORT).show();
                            }
                            else if (data.spMedioPago.getSelectedItemPosition() == 2){ //Efectivo
                                if (data.rgPagaraRequerido.getCheckedRadioButtonId() == R.id.rbSiRequerido || data.rgPagaraRequerido.getCheckedRadioButtonId() == R.id.rbNoRequerido){ //Selecionó que pagará requerido o no requerido
                                    if (Double.parseDouble(data.etPagoRealizado.getText().toString()) > 0){ //El pago realizado es mayor a cero
                                        if (data.rgRecibos.getCheckedRadioButtonId() == R.id.rbSiRecibo){ //Si imprimirá recibos
                                            if (!data.etFolioRecibo.getText().toString().trim().isEmpty()){
                                                if (data.byteEvidencia != null){ //Ha capturado una evidencia (Fotografía al ticket)
                                                    if (data.rgEstaGerente.getCheckedRadioButtonId() == R.id.rbSiGerente) { //Selecciono que si está el gerente
                                                        if (data.byteFirma != null) { //Capturó una firma
                                                            b.putDouble("LATITUD", data.latLngGestion.latitude);
                                                            b.putDouble("LONGITUD", data.latLngGestion.longitude);
                                                            b.putInt("CONTACTOCLIENTE", 1);
                                                            b.putInt("RESULTADOPAGO", 1);
                                                            b.putInt("POSMEDIOPAGO",data.spMedioPago.getSelectedItemPosition());
                                                            b.putString("MEDIOPAGO", data.spMedioPago.getSelectedItem().toString());
                                                            b.putString("PAGOREALIZADO", data.etPagoRealizado.getText().toString().trim());
                                                            b.putDouble("SALDOCORTE", ficha_cvi.getPrestamo().getSaldoActual());
                                                            b.putDouble("MONTOREQUERIDO", ficha_cvi.getPrestamo().getPagoSemanal());
                                                            b.putString("FOLIO", data.etFolioRecibo.getText().toString().trim());
                                                            b.putByteArray("EVIDENCIA", data.byteEvidencia);
                                                            b.putBoolean("GERENTE", true);
                                                            b.putByteArray("FIRMA", data.byteFirma);
                                                        } else //No ha capturado la firma
                                                            Toast.makeText(ctx, "Capture la firma del gerente", Toast.LENGTH_SHORT).show();
                                                    } else if (data.rgEstaGerente.getCheckedRadioButtonId() == R.id.rbNoGerente) { //No se encuentra el Gerente
                                                        b.putDouble("LATITUD", data.latLngGestion.latitude);
                                                        b.putDouble("LONGITUD", data.latLngGestion.longitude);
                                                        b.putInt("CONTACTOCLIENTE", 1);
                                                        b.putInt("RESULTADOPAGO", 1);
                                                        b.putInt("POSMEDIOPAGO",data.spMedioPago.getSelectedItemPosition());
                                                        b.putString("MEDIOPAGO", data.spMedioPago.getSelectedItem().toString());
                                                        b.putString("PAGOREALIZADO", data.etPagoRealizado.getText().toString().trim());
                                                        b.putDouble("SALDOCORTE", ficha_cvi.getPrestamo().getSaldoActual());
                                                        b.putDouble("MONTOREQUERIDO", ficha_cvi.getPrestamo().getPagoSemanal());
                                                        b.putString("FOLIO", data.etFolioRecibo.getText().toString().trim());
                                                        b.putByteArray("EVIDENCIA", data.byteEvidencia);
                                                        b.putBoolean("GERENTE", false);
                                                    } else //No ha seleccionado si está el gerente
                                                        Toast.makeText(ctx, "Confirme si el gerente está con usted", Toast.LENGTH_SHORT).show();
                                                }
                                                else //No ha capturado fotografía evidencia
                                                    Toast.makeText(ctx, "No ha capturado una fotografía al ticket", Toast.LENGTH_SHORT).show();
                                            }
                                            else //No ha impreso ningun ticket
                                                Toast.makeText(ctx,"No ha realizado nignuna impresión", Toast.LENGTH_SHORT).show();
                                        }
                                        else if (data.rgRecibos.getCheckedRadioButtonId() == R.id.rbNoRecibo){ //No imprimirá recibos
                                            if (!data.etFolioRecibo.getText().toString().trim().isEmpty()){
                                                if (data.byteEvidencia != null){ //Ha capturado una evidencia (Fotografía al ticket)
                                                    if (data.rgEstaGerente.getCheckedRadioButtonId() == R.id.rbSiGerente) { //Selecciono que si está el gerente
                                                        if (data.byteFirma != null) { //Capturó una firma
                                                            b.putDouble("LATITUD", data.latLngGestion.latitude);
                                                            b.putDouble("LONGITUD", data.latLngGestion.longitude);
                                                            b.putInt("CONTACTOCLIENTE", 1);
                                                            b.putInt("RESULTADOPAGO", 1);
                                                            b.putInt("POSMEDIOPAGO",data.spMedioPago.getSelectedItemPosition());
                                                            b.putString("MEDIOPAGO", data.spMedioPago.getSelectedItem().toString());
                                                            b.putString("PAGOREALIZADO", data.etPagoRealizado.getText().toString().trim());
                                                            b.putString("FOLIO", data.etFolioRecibo.getText().toString().trim());
                                                            b.putDouble("SALDOCORTE", ficha_cvi.getPrestamo().getSaldoActual());
                                                            b.putDouble("MONTOREQUERIDO", ficha_cvi.getPrestamo().getPagoSemanal());
                                                            b.putByteArray("EVIDENCIA", data.byteEvidencia);
                                                            b.putBoolean("GERENTE", true);
                                                            b.putByteArray("FIRMA", data.byteFirma);
                                                        } else //No ha capturado la firma
                                                            Toast.makeText(ctx, "Capture la firma del gerente", Toast.LENGTH_SHORT).show();
                                                    } else if (data.rgEstaGerente.getCheckedRadioButtonId() == R.id.rbNoGerente) { //No se encuentra el Gerente
                                                        b.putDouble("LATITUD", data.latLngGestion.latitude);
                                                        b.putDouble("LONGITUD", data.latLngGestion.longitude);
                                                        b.putInt("CONTACTOCLIENTE", 1);
                                                        b.putInt("RESULTADOPAGO", 1);
                                                        b.putInt("POSMEDIOPAGO",data.spMedioPago.getSelectedItemPosition());
                                                        b.putString("MEDIOPAGO", data.spMedioPago.getSelectedItem().toString());
                                                        b.putString("PAGOREALIZADO", data.etPagoRealizado.getText().toString().trim());
                                                        b.putString("FOLIO", data.etFolioRecibo.getText().toString().trim());
                                                        b.putDouble("SALDOCORTE", ficha_cvi.getPrestamo().getSaldoActual());
                                                        b.putDouble("MONTOREQUERIDO", ficha_cvi.getPrestamo().getPagoSemanal());
                                                        b.putByteArray("EVIDENCIA", data.byteEvidencia);
                                                        b.putBoolean("GERENTE", false);
                                                    } else //No ha seleccionado si está el gerente
                                                        Toast.makeText(ctx, "Confirme si el gerente está con usted", Toast.LENGTH_SHORT).show();
                                                }
                                                else //No ha capturado fotografía evidencia
                                                    Toast.makeText(ctx, "No ha capturado una fotografía al ticket", Toast.LENGTH_SHORT).show();
                                            }// No ha capturado el folio del recibo
                                            else
                                                Toast.makeText(ctx, "No ha capturado el folio del recibo", Toast.LENGTH_SHORT).show();
                                        }
                                        else //No ha seleccionado si imprimirá recibos
                                            Toast.makeText(ctx, "No ha confirmado si va imprimir recibos", Toast.LENGTH_SHORT).show();
                                    }
                                    else //El monto ingresado es igual a cero
                                        Toast.makeText(ctx, "No se pueden capturar montos iguales a cero", Toast.LENGTH_SHORT).show();
                                }
                                else //No ha seleccionado si pagará el pago requerido
                                    Toast.makeText(ctx, "No ha seleccionado si se pagará el requerido", Toast.LENGTH_SHORT).show();
                            }
                            else //No ha seleccionado algun medio de pago
                                Toast.makeText(ctx, "No ha seleccionado un medio de pago", Toast.LENGTH_SHORT).show();
                        }
                        else if (data.rgResultadoPago.getCheckedRadioButtonId() == R.id.rbNoPago){ //No pago
                            if (data.spMotivoNoPago.getSelectedItemPosition() == 1 || data.spMotivoNoPago.getSelectedItemPosition() == 3){ //Motivo de no pago Negacion u Otra
                                if (!data.etComentario.getText().toString().trim().isEmpty()){ //El campo comentario es diferente de vacio
                                    if (data.byteEvidencia != null){
                                        if (data.rgEstaGerente.getCheckedRadioButtonId() == R.id.rbSiGerente) { //Selecciono que si está el gerente
                                            if (data.byteFirma != null) { //Capturó una firma
                                                b.putDouble("LATITUD", data.latLngGestion.latitude);
                                                b.putDouble("LONGITUD", data.latLngGestion.longitude);
                                                b.putInt("CONTACTOCLIENTE", 1);
                                                b.putInt("RESULTADOPAGO", 0);
                                                b.putInt("POSMOTIVONOPAGO", data.spMotivoNoPago.getSelectedItemPosition());
                                                b.putString("MOTIVONOPAGO",data.spMotivoNoPago.getSelectedItem().toString());
                                                b.putString("COMENTARIO", data.etComentario.getText().toString());
                                                b.putByteArray("EVIDENCIA", data.byteEvidencia);
                                                b.putBoolean("GERENTE", true);
                                                b.putByteArray("FIRMA", data.byteFirma);
                                            } else //No ha capturado la firma
                                                Toast.makeText(ctx, "Capture la firma del gerente", Toast.LENGTH_SHORT).show();
                                        } else if (data.rgEstaGerente.getCheckedRadioButtonId() == R.id.rbNoGerente) { //No se encuentra el Gerente
                                            b.putDouble("LATITUD", data.latLngGestion.latitude);
                                            b.putDouble("LONGITUD", data.latLngGestion.longitude);
                                            b.putInt("CONTACTOCLIENTE", 1);
                                            b.putInt("RESULTADOPAGO", 0);
                                            b.putInt("POSMOTIVONOPAGO", data.spMotivoNoPago.getSelectedItemPosition());
                                            b.putString("MOTIVONOPAGO",data.spMotivoNoPago.getSelectedItem().toString());
                                            b.putString("COMENTARIO", data.etComentario.getText().toString());
                                            b.putByteArray("EVIDENCIA", data.byteEvidencia);
                                            b.putBoolean("GERENTE", false);
                                        } else //No ha seleccionado si está el gerente
                                            Toast.makeText(ctx, "Confirme si el gerente está con usted", Toast.LENGTH_SHORT).show();
                                    }
                                    else //no ha capturado la fotografía de la fachada
                                        Toast.makeText(ctx, "La Fotografía de la fachada es requerida.", Toast.LENGTH_SHORT).show();
                                }
                                else // No ha ingresado alguno comentario
                                    Toast.makeText(ctx, "El campo Comentario es requerido.", Toast.LENGTH_SHORT).show();
                            }
                            else if(data.spMotivoNoPago.getSelectedItemPosition() == 2) { //Motivo de no pago fue Fallecimiento
                                if (!data.etFechaDefuncion.getText().toString().trim().isEmpty()){ //El campo Fecha es diferente de vacio
                                    if (!data.etComentario.getText().toString().trim().isEmpty()){ // El campo Comentario es diferente de vacio
                                        if (data.byteEvidencia != null){ //Capturo una fotografia de fachada
                                            if (data.rgEstaGerente.getCheckedRadioButtonId() == R.id.rbSiGerente) { //Si está el gerente
                                                if (data.byteFirma != null) { //Capturó un firma
                                                    b.putDouble("LATITUD", data.latLngGestion.latitude);
                                                    b.putDouble("LONGITUD", data.latLngGestion.longitude);
                                                    b.putInt("CONTACTOCLIENTE", 1);
                                                    b.putInt("RESULTADOPAGO", 0);
                                                    b.putInt("POSMOTIVONOPAGO", data.spMotivoNoPago.getSelectedItemPosition());
                                                    b.putString("MOTIVONOPAGO",data.spMotivoNoPago.getSelectedItem().toString());
                                                    b.putString("FECHADEFUNCION", data.etFechaDefuncion.getText().toString());
                                                    b.putString("COMENTARIO", data.etComentario.getText().toString());
                                                    b.putByteArray("EVIDENCIA", data.byteEvidencia);
                                                    b.putBoolean("GERENTE", true);
                                                    b.putByteArray("FIRMA", data.byteFirma);
                                                } else //No ha Capturado un Firma
                                                    Toast.makeText(ctx, "Capture la firma del gerente", Toast.LENGTH_SHORT).show();
                                            } else if (data.rgEstaGerente.getCheckedRadioButtonId() == R.id.rbNoGerente) { //No está el gerente
                                                b.putDouble("LATITUD", data.latLngGestion.latitude);
                                                b.putDouble("LONGITUD", data.latLngGestion.longitude);
                                                b.putInt("CONTACTOCLIENTE", 1);
                                                b.putInt("RESULTADOPAGO", 0);
                                                b.putInt("POSMOTIVONOPAGO", data.spMotivoNoPago.getSelectedItemPosition());
                                                b.putString("MOTIVONOPAGO",data.spMotivoNoPago.getSelectedItem().toString());
                                                b.putString("FECHADEFUNCION", data.etFechaDefuncion.getText().toString());
                                                b.putString("COMENTARIO", data.etComentario.getText().toString());
                                                b.putByteArray("EVIDENCIA", data.byteEvidencia);
                                                b.putBoolean("GERENTE", false);
                                            } else //No ha seleccionado si está el gerente
                                                Toast.makeText(ctx, "Confirme si el gerente está con usted", Toast.LENGTH_SHORT).show();
                                        }
                                        else //No ha capturado una fotografia
                                            Toast.makeText(ctx, "La Fotografía de la fachada es requerida.", Toast.LENGTH_SHORT).show();
                                    }
                                    else //No ha ingresado algun comentario
                                        Toast.makeText(ctx, "El campo Comentario es requerido.", Toast.LENGTH_SHORT).show();
                                }
                                else //No ha seleccionado la fecha de defuncion
                                    Toast.makeText(ctx, "No ha seleccionado la fecha de defunción", Toast.LENGTH_SHORT).show();
                            }
                            else  //No ha seleccionado algun motivo de no pago
                                Toast.makeText(ctx, "No ha seleccionado motivo de no pago", Toast.LENGTH_SHORT).show();
                        }
                        else //No ha seleccionado si pagó o no el cliente
                            Toast.makeText(ctx, "No ha seleccionado el resultado de la gestion", Toast.LENGTH_SHORT).show();
                    }
                    else //No ha ingresado el nuevo teléfono
                        Toast.makeText(ctx, "No ha ingresado el nuevo teléfono", Toast.LENGTH_SHORT).show();
                }
                else //No ha seleccionado si va actualizar el telefono
                    Toast.makeText(ctx, "No ha seleccionado si va actualizar el teléfono", Toast.LENGTH_SHORT).show();
            }
            else if(data.rgContactoCliente.getCheckedRadioButtonId() == R.id.rbNoContacto) { //No contactó al cliente
                if (!data.etMotivoNoContacto.getText().toString().trim().isEmpty()){ //Capturó motivo de no contacto
                    if (!data.etComentario.getText().toString().trim().isEmpty()) { //El campo comentario es diferente de vacio
                        if (data.byteEvidencia != null) { //Ha capturado una fotografia de la fachada
                            if (data.rgEstaGerente.getCheckedRadioButtonId() == R.id.rbSiGerente) { // Seleccionó que está el gerente
                                if (data.byteFirma != null) { // Ha capturado un firma
                                    b.putDouble("LATITUD", data.latLngGestion.latitude);
                                    b.putDouble("LONGITUD", data.latLngGestion.longitude);
                                    b.putInt("CONTACTOCLIENTE", 0);
                                    b.putString("MOTIVONOCONTACTO", data.etMotivoNoContacto.getText().toString());
                                    b.putString("COMENTARIO", data.etComentario.getText().toString());
                                    b.putByteArray("EVIDENCIA", data.byteEvidencia);
                                    b.putBoolean("GERENTE", true);
                                    b.putByteArray("FIRMA", data.byteFirma);
                                } else //No ha capturado un firma
                                    Toast.makeText(ctx, "Capture la firma del gerente", Toast.LENGTH_SHORT).show();
                            } else if (data.rgEstaGerente.getCheckedRadioButtonId() == R.id.rbNoGerente) { //No se encuentra el gerente
                                b.putDouble("LATITUD", data.latLngGestion.latitude);
                                b.putDouble("LONGITUD", data.latLngGestion.longitude);
                                b.putInt("CONTACTOCLIENTE", 0);
                                b.putString("MOTIVONOCONTACTO", data.etMotivoNoContacto.getText().toString());
                                b.putString("COMENTARIO", data.etComentario.getText().toString());
                                b.putByteArray("EVIDENCIA", data.byteEvidencia);
                                b.putBoolean("GERENTE", false);
                            } else //No ha seleccionado si está el gerente
                                Toast.makeText(ctx, "Confirme si el gerente está con usted", Toast.LENGTH_SHORT).show();
                        } else // No ha capturado una fotografia de fachada
                            Toast.makeText(ctx, "La Fotografía de la fachada es requerida.", Toast.LENGTH_SHORT).show();
                    } else //No ha ingresado algun comentario
                        Toast.makeText(ctx, "El campo Comentario es obligatorio", Toast.LENGTH_SHORT).show();
                }
                else //No ha seleccionado un motivo de no contacto
                    Toast.makeText(ctx, "No ha seleccionado un motivo de no contacto", Toast.LENGTH_SHORT).show();
            }
            else if(data.rgContactoCliente.getCheckedRadioButtonId() == R.id.rbAclaracion) { //Seleccionó Aclaración
                if (!data.etMotivoAclaracion.getText().toString().trim().isEmpty()) { //Motivo de aclaración es diferente de vacio
                    if (!data.etComentario.getText().toString().trim().isEmpty()) { // Ingresó algun comentario
                        if (data.rgEstaGerente.getCheckedRadioButtonId() == R.id.rbSiGerente) { //Seleccionó que está el gerente
                            if (data.byteFirma != null) { //Ha capturado una firma
                                b.putDouble("LATITUD", data.latLngGestion.latitude);
                                b.putDouble("LONGITUD", data.latLngGestion.longitude);
                                b.putInt("CONTACTOCLIENTE", 2);
                                b.putString("MOTIVOACLARACION", data.etMotivoAclaracion.getText().toString());
                                b.putString("COMENTARIO", data.etComentario.getText().toString());
                                b.putBoolean("GERENTE", true);
                                b.putByteArray("FIRMA", data.byteFirma);

                            } else //No ha capturado una firma
                                Toast.makeText(ctx, "Capture la firma del gerente", Toast.LENGTH_SHORT).show();
                        } else if (data.rgEstaGerente.getCheckedRadioButtonId() == R.id.rbNoGerente) { //Seleccionó que no está el gerente
                            b.putDouble("LATITUD", data.latLngGestion.latitude);
                            b.putDouble("LONGITUD", data.latLngGestion.longitude);
                            b.putInt("CONTACTOCLIENTE", 2);
                            b.putString("MOTIVOACLARACION", data.etMotivoAclaracion.getText().toString());
                            b.putString("COMENTARIO", data.etComentario.getText().toString());
                            b.putBoolean("GERENTE", false);
                        } else //No ha confirmado si está el gerente
                            Toast.makeText(ctx, "Confirme si el gerente está con usted", Toast.LENGTH_SHORT).show();
                    } else //No ha ingresado algun comentario
                        Toast.makeText(ctx, "El campo Comentario es obligatorio", Toast.LENGTH_SHORT).show();
                } else //No ha seleccionado el motivo de aclaración
                    Toast.makeText(ctx, "Seleccione el motivo de aclaración", Toast.LENGTH_SHORT).show();
            }
            else //No ha seleccionadosi conectado al cliente o es una aclaración
                Toast.makeText(ctx, "No ha seleccionado si contactó al cliente", Toast.LENGTH_SHORT).show();
        }
        else //No ha capturado la ubicación
            Toast.makeText(ctx,"Falta obtener la ubicación de la gestión", Toast.LENGTH_SHORT).show();

        if (!b.isEmpty()){
            Intent i_preview = new Intent(ctx,VistaPreviaGestion.class);
            i_preview.putExtra("PARAMS",b);
            startActivityForResult(i_preview,Constants.REQUEST_CODE_PREVIEW);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case Constants.REQUEST_CODE_PREVIEW:
                if (resultCode == RESULT_OK){
                    if (data != null){
                        Toast.makeText(ctx, "Ficha Guardada con éxito.", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
                break;
        }
    }
}
