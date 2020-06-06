package com.sidert.sidertmovil.activities;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.database.DBhelper;
import com.sidert.sidertmovil.models.MCierreDia;
import com.sidert.sidertmovil.utils.Constants;
import com.sidert.sidertmovil.utils.Miscellaneous;
import com.sidert.sidertmovil.utils.NumberFormatTextWatcher;
import com.sidert.sidertmovil.utils.Popups;
import com.sidert.sidertmovil.utils.Servicios_Sincronizado;
import com.sidert.sidertmovil.utils.SessionManager;
import com.sidert.sidertmovil.utils.Validator;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

import static com.sidert.sidertmovil.utils.Constants.PICTURE;
import static com.sidert.sidertmovil.utils.Constants.REQUEST_CODE_CAMERA_CIERRE_DIA;
import static com.sidert.sidertmovil.utils.Constants.ROOT_PATH;
import static com.sidert.sidertmovil.utils.Constants.TBL_CIERRE_DIA_T;
import static com.sidert.sidertmovil.utils.Constants.TIMESTAMP;
import static com.sidert.sidertmovil.utils.Constants.warning;

public class CierreDeDia extends AppCompatActivity {

    private Context ctx;
    private Toolbar tbMain;

    private DBhelper dBhelper;
    private SQLiteDatabase db;

    private MCierreDia item;

    private EditText etMontoDepositado;

    private ImageButton ibEvidencia;
    private ImageView ivEvidencia;

    public byte[] byteEvidencia;

    private boolean isEdit = true;

    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cierre_de_dia);

        ctx = this;

        dBhelper = new DBhelper(ctx);
        db = dBhelper.getWritableDatabase();

        session = new SessionManager(ctx);

        tbMain = findViewById(R.id.tbMain);

        TextView tvNumPrestamo = findViewById(R.id.tvNumPrestamo);
        TextView tvNombre       = findViewById(R.id.tvNombre);
        TextView tvFechaGestion = findViewById(R.id.tvFechaGestion);
        TextView tvPagoRealizado    = findViewById(R.id.tvPagoRealizado);
        TextView tvBanco            = findViewById(R.id.tvBanco);
        etMontoDepositado  = findViewById(R.id.etMontoDepositado);
        ibEvidencia     = findViewById(R.id.ibEvidencia);
        ivEvidencia       = findViewById(R.id.ivEvidencia);

        setSupportActionBar(tbMain);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        item = (MCierreDia) getIntent().getSerializableExtra("cierre_dia");

        Log.e("Cierre", Miscellaneous.ConvertToJson(item));

        tvNumPrestamo.setText(item.getNumPrestamo());
        tvNombre.setText(item.getNombre());
        tvFechaGestion.setText(item.getFecha());
        tvPagoRealizado.setText("$ "+item.getPago());
        tvBanco.setText("BANAMEX722");

        isEdit = (item.getEstatus() > 0)?false:true;

        if (!isEdit) {
            invalidateOptionsMenu();
            etMontoDepositado.setText(item.getMonto());
            etMontoDepositado.setEnabled(false);
            etMontoDepositado.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));

            Log.e("Evidencia", ROOT_PATH + "CierreDia/"+item.getEvidencia());
            File evidenciaFile = new File(ROOT_PATH + "CierreDia/"+item.getEvidencia());
            Uri uriEvidencia = Uri.fromFile(evidenciaFile);
            byteEvidencia = Miscellaneous.getBytesUri(ctx, uriEvidencia,0);
            Glide.with(ctx).load(uriEvidencia).into(ivEvidencia);
            ibEvidencia.setVisibility(View.GONE);
            ivEvidencia.setVisibility(View.VISIBLE);
        }

        etMontoDepositado.addTextChangedListener(new NumberFormatTextWatcher(etMontoDepositado));

        ibEvidencia.setOnClickListener(ibEvidencia_OnClick);
        ivEvidencia.setOnClickListener(ivEvidencia_OnClick);


    }

    private View.OnClickListener ibEvidencia_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent i = new Intent(ctx, CameraVertical.class);
            startActivityForResult(i, REQUEST_CODE_CAMERA_CIERRE_DIA);
        }
    };

    private View.OnClickListener ivEvidencia_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (isEdit) {
                final AlertDialog evidencia_dlg = Popups.showDialogConfirmImage(ctx, Constants.question,
                        R.string.capturar_foto, R.string.fotografia, new Popups.DialogMessage() {
                            @Override
                            public void OnClickListener(AlertDialog dialog) {
                                Intent i = new Intent(ctx, CameraVertical.class);
                                startActivityForResult(i, REQUEST_CODE_CAMERA_CIERRE_DIA);
                                dialog.dismiss();

                            }
                        }, R.string.ver_imagen, new Popups.DialogMessage() {
                            @Override
                            public void OnClickListener(AlertDialog dialog) {
                                Intent i = new Intent(ctx, VerImagen.class);
                                i.putExtra(Constants.IMAGEN, byteEvidencia);
                                startActivity(i);
                                dialog.dismiss();
                            }
                        }, R.string.cancel, new Popups.DialogMessage() {
                            @Override
                            public void OnClickListener(AlertDialog dialog) {
                                dialog.dismiss();
                            }
                        });
                Objects.requireNonNull(evidencia_dlg.getWindow()).requestFeature(Window.FEATURE_NO_TITLE);
                evidencia_dlg.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                evidencia_dlg.show();

            }
            else {
                final AlertDialog fachada_dlg = Popups.showDialogConfirm(ctx, Constants.question,
                        R.string.ver_fotografia, R.string.ver_imagen, new Popups.DialogMessage() {
                            @Override
                            public void OnClickListener(AlertDialog dialog) {
                                Intent i = new Intent(ctx, VerImagen.class);
                                i.putExtra(Constants.IMAGEN, byteEvidencia);
                                startActivity(i);
                                dialog.dismiss();

                            }
                        }, R.string.cancel, new Popups.DialogMessage() {
                            @Override
                            public void OnClickListener(AlertDialog dialog) {
                                dialog.dismiss();
                            }
                        });
                Objects.requireNonNull(fachada_dlg.getWindow()).requestFeature(Window.FEATURE_NO_TITLE);
                fachada_dlg.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                fachada_dlg.show();
            }
        }
    };

    private void ValidarCampos(){
        Validator validator = new Validator();
        if (!validator.validate(etMontoDepositado, new String[]{validator.REQUIRED, validator.MONEY})){
            try {
                String montoDepo = etMontoDepositado.getText().toString().replace(",", "");
                if (montoDepo.equals(item.getPago().replace(",",""))){
                    if (byteEvidencia != null) {
                        Guardar();
                    }
                    else
                        ShowMessage(3);
                }
                else
                    ShowMessage(2);
            }catch (NumberFormatException e){
                ShowMessage(1);
            }

        }
    }

    private void Guardar(){

        try {
            ContentValues cv = new ContentValues();
            cv.put("monto_depositado", etMontoDepositado.getText().toString().trim().replace(",", ""));
            cv.put("evidencia", Miscellaneous.save(byteEvidencia, 5));
            cv.put("fecha_inicio", Miscellaneous.ObtenerFecha(TIMESTAMP));
            cv.put("fecha_fin", Miscellaneous.ObtenerFecha(TIMESTAMP));
            cv.put("medio_pago", "BANAMEX722");
            cv.put("estatus", 1);
            cv.put("serial_id", item.getFecha().replace("-","").replace(" ", "").replace(":","")+"-"+
                    session.getUser().get(0)+"-"+item.getNumPrestamo()+"-"+item.getClaveCliente());
            etMontoDepositado.setEnabled(false);
            etMontoDepositado.setBackground(getResources().getDrawable(R.drawable.bkg_rounded_edges_blocked));

            item.setEstatus(1);

            db.update(TBL_CIERRE_DIA_T, cv, "_id = ?", new String[]{item.getId()});

            isEdit = false;
            invalidateOptionsMenu();

            Toast.makeText(ctx, "Cierre Guardado con éxito", Toast.LENGTH_SHORT).show();

            Servicios_Sincronizado ss = new Servicios_Sincronizado();
            ss.SaveCierreDia(ctx, false);
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("Error", e.getMessage() + "Warning");
            ShowMessage(4);
        }

    }

    private void ShowMessage(int tipoMensaje){
        String mensaje = "";
        switch (tipoMensaje){
            case 1:
                mensaje = "Por favor ingrese un monto correcto";
                break;
            case 2:
                mensaje = "El monto depositado no coincide con el pago del cliente, favor de verificar";
                break;
            case 3:
                mensaje = "No ha capturado la fotografía del recibo de pago";
                break;
            case 4:
                mensaje = "Ocurrio un error al guardar los datos";
                break;
            case 5:
                mensaje = "No ha completado la gestión de esta ficha, favor de guardar primero la gestión para despues contestar el cierre de día";
                break;
        }

        AlertDialog success = Popups.showDialogMessage(ctx, warning,
                mensaje, R.string.accept, new Popups.DialogMessage() {
                    @Override
                    public void OnClickListener(AlertDialog dialog) {
                        dialog.dismiss();
                    }
                });
        success.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        success.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        success.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE_CAMERA_CIERRE_DIA:
                if (resultCode == Activity.RESULT_OK) {
                    if (data != null) {
                        ibEvidencia.setVisibility(View.GONE);

                        ivEvidencia.setVisibility(View.VISIBLE);
                        byteEvidencia = data.getByteArrayExtra(PICTURE);
                        Glide.with(ctx).load(byteEvidencia).centerCrop().into(ivEvidencia);

                        /*try {
                            Update("evidencia", Miscellaneous.save(byteEvidencia, 1));
                            Update("tipo_imagen", "FACHADA");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }*/
                    }
                }
                break;
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_save, menu);
        if (!isEdit)
            menu.getItem(0).setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.save:
                if (this.item.getEstatusRespuesta() > 0)
                    ValidarCampos();
                else
                    ShowMessage(5);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}