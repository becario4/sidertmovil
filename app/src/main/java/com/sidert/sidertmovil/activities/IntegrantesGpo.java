package com.sidert.sidertmovil.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
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
import android.widget.ListView;

import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.adapters.adapter_integrantes_gpo;
import com.sidert.sidertmovil.adapters.adapter_list_integrantes;
import com.sidert.sidertmovil.models.ModeloGrupal;
import com.sidert.sidertmovil.utils.Constants;
import com.sidert.sidertmovil.utils.Popups;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class IntegrantesGpo extends AppCompatActivity {

    @SuppressLint("UseSparseArrays")
    private HashMap<Integer, ModeloGrupal.IntegrantesDelGrupo> _pagos = new HashMap<>();

    private JSONArray jsonIntegrantes;

    private CheckBox cbSelectAll;

    private Context ctx;

    private adapter_integrantes_gpo adapter;

    boolean flag = false;
    double total = 0;
    boolean isEditable = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_integrantes_gpo);
        ctx = this;
        Toolbar tbMain = findViewById(R.id.tbMain);

        cbSelectAll     = findViewById(R.id.cbSelectAll);

        RecyclerView rvIntegrantesGpo = findViewById(R.id.rvIntegrantesGpo);
        rvIntegrantesGpo.setLayoutManager(new LinearLayoutManager(ctx));
        rvIntegrantesGpo.setHasFixedSize(false);

        setSupportActionBar(tbMain);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        final ModeloGrupal.Grupo grupo = (ModeloGrupal.Grupo) getIntent().getSerializableExtra(Constants.INTEGRANTES_GRUPO);
        isEditable = getIntent().getBooleanExtra(Constants.EDITABLE, false);
        invalidateOptionsMenu();
        setTitle(grupo.getNombreGrupo());

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        adapter = new adapter_integrantes_gpo(ctx, isEditable, grupo.getIntegrantesDelGrupo());

        cbSelectAll.setEnabled(isEditable);

        rvIntegrantesGpo.setAdapter(adapter);
        rvIntegrantesGpo.setItemViewCacheSize(20);

        cbSelectAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                for (int i=0;i<grupo.getIntegrantesDelGrupo().size();i++){
                    if (grupo.getIntegrantesDelGrupo().get(i).isPagoCompleto()){
                        adapter.selectAllItem(false);
                        adapter.notifyDataSetChanged();
                    }
                    else {
                        adapter.selectAllItem(true);
                        adapter.notifyDataSetChanged();
                    }
                    break;
                }
            }
        });
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_save, menu);

        for (int i = 0; i < menu.size(); i++)
            menu.getItem(i).setVisible(isEditable);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (isEditable) {
                    SumarPagos();

                    if (flag) {
                        final double finalTotal = total;
                        final AlertDialog confirm_dlg = Popups.showDialogConfirm(IntegrantesGpo.this, Constants.question,
                                R.string.guardar_cambios, R.string.accept, new Popups.DialogMessage() {
                                    @Override
                                    public void OnClickListener(AlertDialog dialog) {
                                        Intent i_resumen_int = new Intent(IntegrantesGpo.this, ResumenIntegrantes.class);
                                        i_resumen_int.putExtra("INTEGRANTES", jsonIntegrantes.toString());
                                        i_resumen_int.putExtra("TOTAL", finalTotal + "");
                                        startActivityForResult(i_resumen_int, Constants.REQUEST_CODE_RESUMEN_INTEGRANTES_GPO);
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
                        finish();
                }
                else
                    finish();
                break;
            case  R.id.save:
                SumarPagos();

                if (flag){
                    final double finalTotal = total;
                    final AlertDialog confirm_dlg = Popups.showDialogConfirm(IntegrantesGpo.this, Constants.question,
                            R.string.guardar_cambios, R.string.accept, new Popups.DialogMessage() {
                                @Override
                                public void OnClickListener(AlertDialog dialog) {
                                    Intent i_resumen_int = new Intent(IntegrantesGpo.this, ResumenIntegrantes.class);
                                    i_resumen_int.putExtra("INTEGRANTES", jsonIntegrantes.toString());
                                    i_resumen_int.putExtra("TOTAL", finalTotal+"");
                                    startActivityForResult(i_resumen_int, Constants.REQUEST_CODE_RESUMEN_INTEGRANTES_GPO);
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
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void SumarPagos() {
        jsonIntegrantes = new JSONArray();

        for(int i = 0; i < adapter.data.size(); i++){
            if (Double.parseDouble(adapter.data.get(i).getPagoRealizado().toString()) > 0){
                JSONObject itemIntegrante = new JSONObject();
                try {
                    itemIntegrante.put("nombre", adapter.data.get(i).getNombre());
                    itemIntegrante.put("pago", adapter.data.get(i).getPagoRealizado());
                    itemIntegrante.put("adelanto", adapter.data.get(i).getPagoAdelanto());
                    itemIntegrante.put("solidario", adapter.data.get(i).getPagoSolidario());
                    itemIntegrante.put("clave_cliente", adapter.data.get(i).getClaveCliente());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                total += adapter.data.get(i).getPagoRealizado();
                jsonIntegrantes.put(itemIntegrante);
                flag = true;
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (isEditable) {
            SumarPagos();
            if (flag) {
                final double finalTotal = total;
                final AlertDialog confirm_dlg = Popups.showDialogConfirm(IntegrantesGpo.this, Constants.question,
                        R.string.guardar_cambios, R.string.accept, new Popups.DialogMessage() {
                            @Override
                            public void OnClickListener(AlertDialog dialog) {
                                Intent i_resumen_int = new Intent(IntegrantesGpo.this, ResumenIntegrantes.class);
                                i_resumen_int.putExtra("INTEGRANTES", jsonIntegrantes.toString());
                                i_resumen_int.putExtra("TOTAL", finalTotal + "");
                                startActivityForResult(i_resumen_int, Constants.REQUEST_CODE_RESUMEN_INTEGRANTES_GPO);
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
                super.onBackPressed();
        }
        else
            super.onBackPressed();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case Constants.REQUEST_CODE_RESUMEN_INTEGRANTES_GPO:
                Intent i_resultado = new Intent();
                i_resultado.putExtra(Constants.RESPONSE, data.getStringExtra("TOTAL"));
                i_resultado.putExtra("integrantes", jsonIntegrantes.toString());
                setResult(RESULT_OK,i_resultado);
                finish();
                break;
        }
    }
}
