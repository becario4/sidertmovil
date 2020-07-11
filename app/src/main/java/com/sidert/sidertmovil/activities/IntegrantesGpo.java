package com.sidert.sidertmovil.activities;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.adapters.adapter_integrantes_gpo;
import com.sidert.sidertmovil.database.DBhelper;
import com.sidert.sidertmovil.models.MIntegrantePago;
import com.sidert.sidertmovil.utils.Constants;
import com.sidert.sidertmovil.utils.Popups;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Objects;

import static com.sidert.sidertmovil.utils.Constants.ENVIROMENT;
import static com.sidert.sidertmovil.utils.Constants.ID_GESTION;
import static com.sidert.sidertmovil.utils.Constants.ID_PRESTAMO;
import static com.sidert.sidertmovil.utils.Constants.INTEGRANTES;
import static com.sidert.sidertmovil.utils.Constants.REQUEST_CODE_RESUMEN_INTEGRANTES_GPO;
import static com.sidert.sidertmovil.utils.Constants.TBL_MIEMBROS_PAGOS;
import static com.sidert.sidertmovil.utils.Constants.TBL_MIEMBROS_PAGOS_T;
import static com.sidert.sidertmovil.utils.Constants.TIPO_CARTERA;
import static com.sidert.sidertmovil.utils.Constants.TOTAL;

public class IntegrantesGpo extends AppCompatActivity {

    private ArrayList<MIntegrantePago> integrantePagos;
    private String id_prestamo = "";
    private String id_gestion = "";
    private String tipo_cartera = "";

    private CheckBox cbSelectAll;

    private Context ctx;

    private adapter_integrantes_gpo adapter;

    boolean flag = false;
    double total = 0;

    private DBhelper dBhelper;
    private SQLiteDatabase db;

    private DecimalFormat dFormat = new DecimalFormat("#.00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_integrantes_gpo);
        ctx = this;
        dBhelper = new DBhelper(ctx);
        db = dBhelper.getWritableDatabase();
        Toolbar tbMain = findViewById(R.id.tbMain);

        cbSelectAll     = findViewById(R.id.cbSelectAll);

        RecyclerView rvIntegrantesGpo = findViewById(R.id.rvIntegrantesGpo);
        rvIntegrantesGpo.setLayoutManager(new LinearLayoutManager(ctx));
        rvIntegrantesGpo.setHasFixedSize(false);

        setSupportActionBar(tbMain);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        integrantePagos = (ArrayList<MIntegrantePago>) getIntent().getSerializableExtra(INTEGRANTES);
        id_prestamo =  getIntent().getStringExtra(ID_PRESTAMO);
        id_gestion  =  getIntent().getStringExtra(ID_GESTION);
        tipo_cartera = getIntent().getStringExtra(TIPO_CARTERA);
        invalidateOptionsMenu();

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        adapter = new adapter_integrantes_gpo(ctx, integrantePagos);

        rvIntegrantesGpo.setAdapter(adapter);
        rvIntegrantesGpo.setItemViewCacheSize(20);

        cbSelectAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                //adapter.selectAllItem(isChecked);

                //adapter.notifyDataSetChanged();
                final AlertDialog loading = Popups.showLoadingDialog(ctx, R.string.please_wait, R.string.loading_info);
                loading.show();
                for (int i=0;i<integrantePagos.size();i++){

                    if (isChecked)
                        integrantePagos.get(i).setPagoRealizado(dFormat.format(Double.parseDouble(integrantePagos.get(i).getMontoRequerido()))+"");
                    else
                        integrantePagos.get(i).setPagoRealizado(dFormat.format(Double.parseDouble("0"))+"");

                    integrantePagos.get(i).setPagoRequerido(isChecked);

                    Log.e("PagoIntegrante", dFormat.format(Double.parseDouble(integrantePagos.get(i).getPagoRealizado()))+"##############");

                }

                adapter.notifyDataSetChanged();
                loading.dismiss();


            }
        });
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_save, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case  R.id.save:
            case android.R.id.home:
                    SumarPagos();

                    if (flag && !tipo_cartera.contains("VENCIDA")) {
                        final double finalTotal = total;
                        final AlertDialog confirm_dlg = Popups.showDialogConfirm(IntegrantesGpo.this, Constants.question,
                                R.string.guardar_cambios, R.string.accept, new Popups.DialogMessage() {
                                    @Override
                                    public void OnClickListener(AlertDialog dialog) {
                                        for(int i = 0; i < integrantePagos.size(); i++){
                                            ContentValues cv = new ContentValues();
                                            cv.put("pago_realizado", integrantePagos.get(i).getPagoRealizado());
                                            cv.put("adelanto", integrantePagos.get(i).getAdelanto());
                                            cv.put("solidario", integrantePagos.get(i).getSolidario());
                                            cv.put("pago_requerido", (integrantePagos.get(i).isPagoRequerido())?"1":"0");
                                            if (ENVIROMENT)
                                                db.update(TBL_MIEMBROS_PAGOS, cv, "id_gestion = ? AND id_integrante = ? AND id_prestamo = ?", new String[]{integrantePagos.get(i).getIdGestion(), integrantePagos.get(i).getIdIntegrante(), integrantePagos.get(i).getIdPrestamo()});
                                            else
                                                db.update(TBL_MIEMBROS_PAGOS_T, cv, "id_gestion = ? AND id_integrante = ? AND id_prestamo = ?", new String[]{integrantePagos.get(i).getIdGestion(), integrantePagos.get(i).getIdIntegrante(), integrantePagos.get(i).getIdPrestamo()});
                                        }
                                        Intent i_resumen_int = new Intent(IntegrantesGpo.this, ResumenIntegrantes.class);
                                        i_resumen_int.putExtra(ID_PRESTAMO, id_prestamo);
                                        i_resumen_int.putExtra(ID_GESTION, id_gestion);
                                        i_resumen_int.putExtra(TOTAL, String.valueOf(finalTotal));
                                        startActivityForResult(i_resumen_int, REQUEST_CODE_RESUMEN_INTEGRANTES_GPO);
                                        dialog.dismiss();

                                    }
                                }, R.string.cancel, new Popups.DialogMessage() {
                                    @Override
                                    public void OnClickListener(AlertDialog dialog) {
                                        finish();
                                        dialog.dismiss();
                                    }
                                });
                        confirm_dlg.setCancelable(true);
                        confirm_dlg.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                        confirm_dlg.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                        confirm_dlg.show();
                    }
                    else if (flag && tipo_cartera.contains("VENCIDA")){
                        int count = 0;
                        for (int x = 0; x < integrantePagos.size(); x++){
                            double pago = 0;
                            try{
                                pago = Double.parseDouble(integrantePagos.get(x).getPagoRealizado());
                            }catch (NumberFormatException e){
                                pago = 0;
                            }
                            if (pago > 0){
                                count += 1;
                            }
                        }

                        if (count > 1){
                            final AlertDialog mess_dlg = Popups.showDialogMessage(IntegrantesGpo.this, "default", R.string.mess_integrantes_cv, R.string.accept, new Popups.DialogMessage() {
                                @Override
                                public void OnClickListener(AlertDialog dg) {
                                    dg.dismiss();
                                }
                            });
                            mess_dlg.setCancelable(true);
                            mess_dlg.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                            mess_dlg.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                            mess_dlg.show();
                        }else{
                            final double finalTotal = total;
                            final AlertDialog confirm_dlg = Popups.showDialogConfirm(IntegrantesGpo.this, Constants.question,
                                    R.string.guardar_cambios, R.string.accept, new Popups.DialogMessage() {
                                        @Override
                                        public void OnClickListener(AlertDialog dialog) {
                                            for(int i = 0; i < integrantePagos.size(); i++){
                                                ContentValues cv = new ContentValues();
                                                cv.put("pago_realizado", integrantePagos.get(i).getPagoRealizado());
                                                cv.put("adelanto", integrantePagos.get(i).getAdelanto());
                                                cv.put("solidario", integrantePagos.get(i).getSolidario());
                                                cv.put("pago_requerido", (integrantePagos.get(i).isPagoRequerido())?"1":"0");
                                                if (ENVIROMENT)
                                                    db.update(TBL_MIEMBROS_PAGOS, cv, "id_gestion = ? AND id_integrante = ? AND id_prestamo = ?", new String[]{integrantePagos.get(i).getIdGestion(), integrantePagos.get(i).getIdIntegrante(), integrantePagos.get(i).getIdPrestamo()});
                                                else
                                                    db.update(TBL_MIEMBROS_PAGOS_T, cv, "id_gestion = ? AND id_integrante = ? AND id_prestamo = ?", new String[]{integrantePagos.get(i).getIdGestion(), integrantePagos.get(i).getIdIntegrante(), integrantePagos.get(i).getIdPrestamo()});
                                            }
                                            Intent i_resumen_int = new Intent(IntegrantesGpo.this, ResumenIntegrantes.class);
                                            i_resumen_int.putExtra(ID_PRESTAMO, id_prestamo);
                                            i_resumen_int.putExtra(ID_GESTION, id_gestion);
                                            i_resumen_int.putExtra(TOTAL, String.valueOf(finalTotal));
                                            startActivityForResult(i_resumen_int, REQUEST_CODE_RESUMEN_INTEGRANTES_GPO);
                                            dialog.dismiss();

                                        }
                                    }, R.string.cancel, new Popups.DialogMessage() {
                                        @Override
                                        public void OnClickListener(AlertDialog dialog) {
                                            finish();
                                            dialog.dismiss();
                                        }
                                    });
                            confirm_dlg.setCancelable(true);
                            confirm_dlg.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                            confirm_dlg.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                            confirm_dlg.show();
                        }

                    }
                    else
                        finish();

                break;


                /*SumarPagos();

                if (flag){
                    final double finalTotal = total;
                    final AlertDialog confirm_dlg = Popups.showDialogConfirm(IntegrantesGpo.this, Constants.question,
                            R.string.guardar_cambios, R.string.accept, new Popups.DialogMessage() {
                                @Override
                                public void OnClickListener(AlertDialog dialog) {
                                    for(int i = 0; i < integrantePagos.size(); i++){
                                        ContentValues cv = new ContentValues();
                                        cv.put("pago_realizado", integrantePagos.get(i).getPagoRealizado());
                                        cv.put("adelanto", integrantePagos.get(i).getAdelanto());
                                        cv.put("solidario", integrantePagos.get(i).getSolidario());
                                        cv.put("pago_requerido", (integrantePagos.get(i).isPagoRequerido())?"1":"0");
                                        if (ENVIROMENT)
                                            db.update(TBL_MIEMBROS_PAGOS, cv, "id_gestion = ? AND id_integrante = ? AND id_prestamo = ?", new String[]{integrantePagos.get(i).getIdGestion(), integrantePagos.get(i).getIdIntegrante(), integrantePagos.get(i).getIdPrestamo()});
                                        else
                                            db.update(TBL_MIEMBROS_PAGOS_T, cv, "id_gestion = ? AND id_integrante = ? AND id_prestamo = ?", new String[]{integrantePagos.get(i).getIdGestion(), integrantePagos.get(i).getIdIntegrante(), integrantePagos.get(i).getIdPrestamo()});
                                    }
                                    Intent i_resumen_int = new Intent(IntegrantesGpo.this, ResumenIntegrantes.class);
                                    i_resumen_int.putExtra(ID_PRESTAMO, id_prestamo);
                                    i_resumen_int.putExtra(ID_GESTION, id_gestion);
                                    i_resumen_int.putExtra(TOTAL, String.valueOf(finalTotal));
                                    startActivityForResult(i_resumen_int, REQUEST_CODE_RESUMEN_INTEGRANTES_GPO);
                                    dialog.dismiss();
                                }
                            }, R.string.cancel, new Popups.DialogMessage() {
                                @Override
                                public void OnClickListener(AlertDialog dialog) {
                                    dialog.dismiss();
                                }
                            });
                    confirm_dlg.setCancelable(true);
                    confirm_dlg.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                    confirm_dlg.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                    confirm_dlg.show();
                }
                else {
                    final AlertDialog dlg_error = Popups.showDialogMessage(this, Constants.warning,
                            R.string.no_capturado_pagos, R.string.accept, new Popups.DialogMessage() {
                                @Override
                                public void OnClickListener(AlertDialog dialog) {
                                    dialog.dismiss();
                                }
                            });
                    dlg_error.setCancelable(false);
                    dlg_error.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                    dlg_error.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                    dlg_error.show();
                }*/
        }
        return super.onOptionsItemSelected(item);
    }

    private void SumarPagos() {
       JSONArray jsonIntegrantes = new JSONArray();

       for (int i = 0; i < integrantePagos.size(); i++){
           Log.e("PAgoRealizado", integrantePagos.get(i).getPagoRealizado());
           if (Double.parseDouble(integrantePagos.get(i).getPagoRealizado()) > 0){
               JSONObject itemIntegrante = new JSONObject();
               try {
                   itemIntegrante.put("nombre", integrantePagos.get(i).getNombre());
                   itemIntegrante.put("pago", integrantePagos.get(i).getPagoRealizado());
                   itemIntegrante.put("adelanto", integrantePagos.get(i).getAdelanto());
                   itemIntegrante.put("solidario", integrantePagos.get(i).getSolidario());
                   itemIntegrante.put("id_integrante", integrantePagos.get(i).getIdIntegrante());
               } catch (JSONException e) {
                   e.printStackTrace();
               }
               total += Double.parseDouble(integrantePagos.get(i).getPagoRealizado());
               jsonIntegrantes.put(itemIntegrante);
               flag = true;
           }
           Log.e("Flag", String.valueOf(flag));
       }

        /*for(int i = 0; i < adapter.data.size(); i++){
            Log.e("PAgoRealizado", adapter.data.get(i).getPagoRealizado());
            if (Double.parseDouble(adapter.data.get(i).getPagoRealizado()) > 0){
                JSONObject itemIntegrante = new JSONObject();
                try {
                    itemIntegrante.put("nombre", adapter.data.get(i).getNombre());
                    itemIntegrante.put("pago", adapter.data.get(i).getPagoRealizado());
                    itemIntegrante.put("adelanto", adapter.data.get(i).getAdelanto());
                    itemIntegrante.put("solidario", adapter.data.get(i).getSolidario());
                    itemIntegrante.put("id_integrante", adapter.data.get(i).getIdIntegrante());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                total += Double.parseDouble(adapter.data.get(i).getPagoRealizado());
                jsonIntegrantes.put(itemIntegrante);
                flag = true;
            }
        }*/
    }

    @Override
    public void onBackPressed() {
        SumarPagos();

        if (flag && !tipo_cartera.contains("VENCIDA")) {
            final double finalTotal = total;
            final AlertDialog confirm_dlg = Popups.showDialogConfirm(IntegrantesGpo.this, Constants.question,
                    R.string.guardar_cambios, R.string.accept, new Popups.DialogMessage() {
                        @Override
                        public void OnClickListener(AlertDialog dialog) {
                            for(int i = 0; i < integrantePagos.size(); i++){
                                ContentValues cv = new ContentValues();
                                cv.put("pago_realizado", integrantePagos.get(i).getPagoRealizado());
                                cv.put("adelanto", integrantePagos.get(i).getAdelanto());
                                cv.put("solidario", integrantePagos.get(i).getSolidario());
                                cv.put("pago_requerido", (integrantePagos.get(i).isPagoRequerido())?"1":"0");
                                if (ENVIROMENT)
                                    db.update(TBL_MIEMBROS_PAGOS, cv, "id_gestion = ? AND id_integrante = ? AND id_prestamo = ?", new String[]{integrantePagos.get(i).getIdGestion(), integrantePagos.get(i).getIdIntegrante(), integrantePagos.get(i).getIdPrestamo()});
                                else
                                    db.update(TBL_MIEMBROS_PAGOS_T, cv, "id_gestion = ? AND id_integrante = ? AND id_prestamo = ?", new String[]{integrantePagos.get(i).getIdGestion(), integrantePagos.get(i).getIdIntegrante(), integrantePagos.get(i).getIdPrestamo()});
                            }
                            Intent i_resumen_int = new Intent(IntegrantesGpo.this, ResumenIntegrantes.class);
                            i_resumen_int.putExtra(ID_PRESTAMO, id_prestamo);
                            i_resumen_int.putExtra(ID_GESTION, id_gestion);
                            i_resumen_int.putExtra(TOTAL, String.valueOf(finalTotal));
                            startActivityForResult(i_resumen_int, REQUEST_CODE_RESUMEN_INTEGRANTES_GPO);
                            dialog.dismiss();

                        }
                    }, R.string.cancel, new Popups.DialogMessage() {
                        @Override
                        public void OnClickListener(AlertDialog dialog) {
                            finish();
                            dialog.dismiss();
                        }
                    });
            confirm_dlg.setCancelable(true);
            confirm_dlg.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            confirm_dlg.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            confirm_dlg.show();
        }
        else if (flag && tipo_cartera.contains("VENCIDA")){
            int count = 0;
            for (int x = 0; x < integrantePagos.size(); x++){
                double pago = 0;
                try{
                    pago = Double.parseDouble(integrantePagos.get(x).getPagoRealizado());
                }catch (NumberFormatException e){
                    pago = 0;
                }
                if (pago > 0){
                    count += 1;
                }
            }

            if (count > 1){
                final AlertDialog mess_dlg = Popups.showDialogMessage(IntegrantesGpo.this, "default", R.string.mess_integrantes_cv, R.string.accept, new Popups.DialogMessage() {
                    @Override
                    public void OnClickListener(AlertDialog dg) {
                        dg.dismiss();
                    }
                });
                mess_dlg.setCancelable(true);
                mess_dlg.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                mess_dlg.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                mess_dlg.show();
            }else{
                final double finalTotal = total;
                final AlertDialog confirm_dlg = Popups.showDialogConfirm(IntegrantesGpo.this, Constants.question,
                        R.string.guardar_cambios, R.string.accept, new Popups.DialogMessage() {
                            @Override
                            public void OnClickListener(AlertDialog dialog) {
                                for(int i = 0; i < integrantePagos.size(); i++){
                                    ContentValues cv = new ContentValues();
                                    cv.put("pago_realizado", integrantePagos.get(i).getPagoRealizado());
                                    cv.put("adelanto", integrantePagos.get(i).getAdelanto());
                                    cv.put("solidario", integrantePagos.get(i).getSolidario());
                                    cv.put("pago_requerido", (integrantePagos.get(i).isPagoRequerido())?"1":"0");
                                    if (ENVIROMENT)
                                        db.update(TBL_MIEMBROS_PAGOS, cv, "id_gestion = ? AND id_integrante = ? AND id_prestamo = ?", new String[]{integrantePagos.get(i).getIdGestion(), integrantePagos.get(i).getIdIntegrante(), integrantePagos.get(i).getIdPrestamo()});
                                    else
                                        db.update(TBL_MIEMBROS_PAGOS_T, cv, "id_gestion = ? AND id_integrante = ? AND id_prestamo = ?", new String[]{integrantePagos.get(i).getIdGestion(), integrantePagos.get(i).getIdIntegrante(), integrantePagos.get(i).getIdPrestamo()});
                                }
                                Intent i_resumen_int = new Intent(IntegrantesGpo.this, ResumenIntegrantes.class);
                                i_resumen_int.putExtra(ID_PRESTAMO, id_prestamo);
                                i_resumen_int.putExtra(ID_GESTION, id_gestion);
                                i_resumen_int.putExtra(TOTAL, String.valueOf(finalTotal));
                                startActivityForResult(i_resumen_int, REQUEST_CODE_RESUMEN_INTEGRANTES_GPO);
                                dialog.dismiss();

                            }
                        }, R.string.cancel, new Popups.DialogMessage() {
                            @Override
                            public void OnClickListener(AlertDialog dialog) {
                                finish();
                                dialog.dismiss();
                            }
                        });
                confirm_dlg.setCancelable(true);
                confirm_dlg.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                confirm_dlg.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                confirm_dlg.show();
            }

        }
        else
            super.onBackPressed();
            /*SumarPagos();
            if (flag) {
                final double finalTotal = total;
                final AlertDialog confirm_dlg = Popups.showDialogConfirm(IntegrantesGpo.this, Constants.question,
                        R.string.guardar_cambios, R.string.accept, new Popups.DialogMessage() {
                            @Override
                            public void OnClickListener(AlertDialog dialog) {
                                for(int i = 0; i < integrantePagos.size(); i++){
                                    ContentValues cv = new ContentValues();
                                    cv.put("pago_realizado", integrantePagos.get(i).getPagoRealizado());
                                    cv.put("adelanto", integrantePagos.get(i).getAdelanto());
                                    cv.put("solidario", integrantePagos.get(i).getSolidario());
                                    cv.put("pago_requerido", (integrantePagos.get(i).isPagoRequerido())?"1":"0");
                                    if (ENVIROMENT)
                                        db.update(TBL_MIEMBROS_PAGOS, cv, "id_gestion = ? AND id_integrante = ? AND id_prestamo = ?", new String[]{integrantePagos.get(i).getIdGestion(), integrantePagos.get(i).getIdIntegrante(), integrantePagos.get(i).getIdPrestamo()});
                                    else
                                        db.update(TBL_MIEMBROS_PAGOS_T, cv, "id_gestion = ? AND id_integrante = ? AND id_prestamo = ?", new String[]{integrantePagos.get(i).getIdGestion(), integrantePagos.get(i).getIdIntegrante(), integrantePagos.get(i).getIdPrestamo()});
                                }
                                Intent i_resumen_int = new Intent(IntegrantesGpo.this, ResumenIntegrantes.class);
                                i_resumen_int.putExtra(ID_PRESTAMO, id_prestamo);
                                i_resumen_int.putExtra(ID_GESTION, id_gestion);
                                i_resumen_int.putExtra(TOTAL, String.valueOf(finalTotal));
                                startActivityForResult(i_resumen_int, REQUEST_CODE_RESUMEN_INTEGRANTES_GPO);
                                dialog.dismiss();

                            }
                        }, R.string.cancel, new Popups.DialogMessage() {
                            @Override
                            public void OnClickListener(AlertDialog dialog) {
                                finish();
                                dialog.dismiss();
                            }
                        });
                confirm_dlg.setCancelable(true);
                confirm_dlg.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                confirm_dlg.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                confirm_dlg.show();
            } else
                super.onBackPressed();*/
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case Constants.REQUEST_CODE_RESUMEN_INTEGRANTES_GPO:
                Intent i_resultado = new Intent();
                i_resultado.putExtra(Constants.RESPONSE, data.getStringExtra(TOTAL));
                setResult(RESULT_OK,i_resultado);
                finish();
                break;
        }
    }
}
