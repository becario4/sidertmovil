package com.sidert.sidertmovil.activities;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
/*import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;*/
import android.os.Bundle;
//import androidx.appcompat.widget.Toolbar;
import android.text.Html;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.github.gcacace.signaturepad.views.SignaturePad;
import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.utils.Constants;
import com.sidert.sidertmovil.utils.Popups;

import java.io.ByteArrayOutputStream;
import java.util.Objects;

/**Clase para capturar firma digital*/
public class CapturarFirma extends AppCompatActivity {

    private static final int REQUEST_EXTERNAL_STORAGE = 1;

    private SignaturePad mSignaturePad;

    private Context ctx;

    private Toolbar tbMain;

    private boolean flag = false;

    private TextView tvPrivacyPolices;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capturar_firma);

        ctx = this;

        tbMain = findViewById(R.id.tbMain);

        mSignaturePad = findViewById(R.id.signature_pad);

        tvPrivacyPolices = findViewById(R.id.tvPrivacyPolices);

        setSupportActionBar(tbMain);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Bundle data = getIntent().getExtras();
        assert data != null;

        /**Si en los datos que se mandan a la clase viene la llave Tipo que es mas para Originacion/Renovacion*/
        if (data.containsKey(Constants.TIPO)) {
            final AlertDialog solicitud;

            switch (data.getString(Constants.TIPO, "")) {
                case "CLIENTE":
                case "AVAL":/**Si es Tipo (AVAL,CLIENTE) se muestra un mensaje*/
                    solicitud = Popups.showDialogMessage(this, Constants.warning,
                            R.string.mess_solicitud, R.string.accept, new Popups.DialogMessage() {
                                @Override
                                public void OnClickListener(AlertDialog dialog) {
                                    dialog.dismiss();
                                }
                            });
                    solicitud.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                    solicitud.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                    solicitud.show();
                    break;
            }
        }

        tvPrivacyPolices.setText(Html.fromHtml(getResources().getString(R.string.privacy_polices_lite)));

        mSignaturePad.setOnSignedListener(new SignaturePad.OnSignedListener() {
            @Override
            public void onStartSigning() {
                //Toast.makeText(CapturarFirma.this, "OnStartSigning", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSigned() {
                /**Comenzo a capturar la firma y habilita el menu*/
                flag = true;
                invalidateOptionsMenu();
            }

            @Override
            public void onClear() {
                /**Se limpia el canvas de la firma y oculta el menu*/
                flag = false;
                invalidateOptionsMenu();
            }
        });

        //tvPrivacyPolices.setOnClickListener(tvPrivacyPolices_OnClick);

    }

    /**Cuando el usuario quiere salir de la vista y ha realizado cambios y no los ha guardado
     * se muestra un mensaje de confirmacion*/
    private void dlg_confirm_exit (){
        final AlertDialog firma_dlg = Popups.showDialogConfirm(ctx, Constants.firma,
                R.string.confirm_exit, R.string.yes, new Popups.DialogMessage() {
                    @Override
                    public void OnClickListener(AlertDialog dialog) {
                        /**El usuario confirma que va a guardar la firma digital y retorna el byteArray para visualizar la firma*/
                        Bitmap signatureBitmap = mSignaturePad.getSignatureBitmap();

                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        signatureBitmap.compress(Bitmap.CompressFormat.JPEG,50,stream);
                        byte[] byteArray = stream.toByteArray();

                        Intent res = new Intent();
                        res.putExtra(Constants.FIRMA_IMAGE, byteArray);
                        setResult(RESULT_OK,res);
                        finish();
                        dialog.dismiss();

                    }
                }, R.string.cancel, new Popups.DialogMessage() {
                    @Override
                    public void OnClickListener(AlertDialog dialog) {

                        dialog.dismiss();
                    }
                });
        Objects.requireNonNull(firma_dlg.getWindow()).requestFeature(Window.FEATURE_NO_TITLE);
        firma_dlg.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        firma_dlg.show();
    }

    /**Evento para mostrar las politicas de privacidad no se ocupa */
    private View.OnClickListener tvPrivacyPolices_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i_about = new Intent(getApplicationContext(), AcercaDe.class);
            i_about.putExtra("FLAG", true);
            startActivity(i_about);
        }
    };

    /**Funcion para el boton de Back del dispositivo*/
    @Override
    public void onBackPressed() {
        if (flag) {
            dlg_confirm_exit();
        }
        else
            finish();
    }


    /**Valida si tiene permisos de almacenamientos*/
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_EXTERNAL_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length <= 0
                        || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(ctx, "Cannot write images to external storage", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    /**Infla el menu de acuerdo si ha realizado cambios (firmo)*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_firma, menu);
        if (!flag)
        {
            for (int i = 0; i < menu.size(); i++)
                menu.getItem(i).setVisible(flag);
        }
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == android.R.id.home) {/**Selecciono Retroceso del Toolbar <- */
            if (flag) {
                dlg_confirm_exit();
            } else
                finish();
        } else if (itemId == R.id.save) {/**Guarda la firma obteniendo la firma en byteArray para retornarlo*/
            Bitmap signatureBitmap = mSignaturePad.getSignatureBitmap();

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            signatureBitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream);
            byte[] byteArray = stream.toByteArray();

            Intent res = new Intent();
            res.putExtra(Constants.FIRMA_IMAGE, byteArray);
            setResult(RESULT_OK, res);
            finish();
        } else if (itemId == R.id.clear) {/**Limpia el canvas para poder seguir dibujando*/
            mSignaturePad.clear();
        }
        return super.onOptionsItemSelected(item);
    }
}
