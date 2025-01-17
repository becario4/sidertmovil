package com.sidert.sidertmovil.activities;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
//import androidx.appcompat.app.AlertDialog;

import androidx.appcompat.app.AlertDialog;

import com.sidert.sidertmovil.utils.Constants;

import me.dm7.barcodescanner.zbar.Result;
import me.dm7.barcodescanner.zbar.ZBarScannerView;

/**Clase del lector de barras que se ocupa para originacion*/
public class LectorCodigoBarras extends Activity implements ZBarScannerView.ResultHandler {
    private ZBarScannerView mScannerView;
    private AlertDialog.Builder builder;

    private String code = "";


    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        mScannerView = new ZBarScannerView(this);    // Programmatically initialize the scanner view
        setContentView(mScannerView);                // Set the scanner view as the content view
    }

    /**Abre la camara para comenzar a leer el codigo de barrar*/
    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
        mScannerView.startCamera();          // Start camera on resume
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();           // Stop camera on pause
    }

    /**Cuando logra obtener el valor de codigo se valida con el primer codigo para confirmacion
     * de que si se leyó bien el código y retonar ese codigo*/
    @Override
    public void handleResult(Result rawResult) {
        // Do something with the result here
        if (!code.isEmpty()){
            if (code.equals(rawResult.getContents())) {
                if (builder != null){
                    builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
                        @Override
                        public void onCancel(DialogInterface dialog) {
                            dialog.dismiss();
                        }
                    });
                }
                Intent i_result = new Intent();
                i_result.putExtra(Constants.CODEBARS, code);
                setResult(RESULT_OK,i_result);
                finish();
            }
            else {

                if (builder != null){
                    builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
                        @Override
                        public void onCancel(DialogInterface dialog) {
                            dialog.dismiss();
                        }
                    });
                }
                builder = new AlertDialog.Builder(this);

                builder.setMessage("El código no coincide con la primer captura, vuelva a capturar el código.")
                        .setPositiveButton("Aceptar",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                }).create().show();
            }
        }
        else{
            code = rawResult.getContents();
            if (builder != null){
                builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        dialog.dismiss();
                    }
                });
            }
            builder = new AlertDialog.Builder(this);

            builder.setMessage("Vuelva a capturar el código para confirmar.")
                    .setPositiveButton("Aceptar",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).create().show();
            //Toast.makeText(this, "Vuelve a capturar el codigo para confirmar.", Toast.LENGTH_LONG).show();
        }

        // If you would like to resume scanning, call this method below:
        mScannerView.resumeCameraPreview(this);
    }
}
