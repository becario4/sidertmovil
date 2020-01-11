package com.sidert.sidertmovil.activities;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.github.gcacace.signaturepad.views.SignaturePad;
import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.utils.Constants;
import com.sidert.sidertmovil.utils.Popups;

import java.io.ByteArrayOutputStream;
import java.util.Objects;

public class CapturarFirma extends AppCompatActivity {

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static final int REQUEST_READ_EXTERNAL_STORAGE = 2;
    private static String[] PERMISSIONS_STORAGE = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private static String[] PERMISSIONS_READ_STORAGE = {Manifest.permission.READ_EXTERNAL_STORAGE};
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

        tvPrivacyPolices.setText(Html.fromHtml(getResources().getString(R.string.privacy_polices_lite)));

        mSignaturePad.setOnSignedListener(new SignaturePad.OnSignedListener() {
            @Override
            public void onStartSigning() {
                //Toast.makeText(CapturarFirma.this, "OnStartSigning", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSigned() {
                flag = true;
                invalidateOptionsMenu();
            }

            @Override
            public void onClear() {
                flag = false;
                invalidateOptionsMenu();
            }
        });

        tvPrivacyPolices.setOnClickListener(tvPrivacyPolices_OnClick);

    }

    private void dlg_confirm_exit (){
        final AlertDialog firma_dlg = Popups.showDialogConfirm(ctx, Constants.firma,
                R.string.confirm_exit, R.string.yes, new Popups.DialogMessage() {
                    @Override
                    public void OnClickListener(AlertDialog dialog) {
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

    private View.OnClickListener tvPrivacyPolices_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i_about = new Intent(getApplicationContext(), AcercaDe.class);
            i_about.putExtra("FLAG", true);
            startActivity(i_about);
        }
    };

    @Override
    public void onBackPressed() {
        if (flag) {
            dlg_confirm_exit();
        }
        else
            finish();
    }


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
        switch (item.getItemId()) {
            case android.R.id.home:
                if (flag){
                    dlg_confirm_exit();
                }
                else
                    finish();
                break;
            case R.id.save:
                Bitmap signatureBitmap = mSignaturePad.getSignatureBitmap();

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                signatureBitmap.compress(Bitmap.CompressFormat.JPEG,50,stream);
                byte[] byteArray = stream.toByteArray();

                Intent res = new Intent();
                res.putExtra(Constants.FIRMA_IMAGE, byteArray);
                setResult(RESULT_OK,res);
                finish();
                break;
            case R.id.clear:
                mSignaturePad.clear();
                break;

        }
        return super.onOptionsItemSelected(item);
    }
}
