package com.sidert.sidertmovil.utils;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public class CustomWatcherTotal implements TextWatcher {

    private double pagoRealizado;
    private EditText[] views;

    public CustomWatcherTotal(double pagoRealizado, EditText[] v) {
        this.pagoRealizado = pagoRealizado;
        this.views = v;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
            evalMontos();
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    private void evalMontos (){
        int mil = (!views[0].getText().toString().trim().isEmpty())? Integer.parseInt(views[0].getText().toString()) : 0;
        int qui = (!views[1].getText().toString().trim().isEmpty())? Integer.parseInt(views[1].getText().toString()) : 0;
        int dos = (!views[2].getText().toString().trim().isEmpty())? Integer.parseInt(views[2].getText().toString()) : 0;
        int cie = (!views[3].getText().toString().trim().isEmpty())? Integer.parseInt(views[3].getText().toString()) : 0;
        int cin = (!views[4].getText().toString().trim().isEmpty())? Integer.parseInt(views[4].getText().toString()) : 0;
        int vei = (!views[5].getText().toString().trim().isEmpty())? Integer.parseInt(views[5].getText().toString()) : 0;

       int subTB = (mil * 1000) + (qui * 500) + (dos * 200) + (cie * 100) + (cin * 50) + (vei * 20);

       views[6].setText(String.valueOf(subTB));

        int diezP   = (!views[7].getText().toString().trim().isEmpty())? Integer.parseInt(views[7].getText().toString()) : 0;
        int cincoP  = (!views[8].getText().toString().trim().isEmpty())? Integer.parseInt(views[8].getText().toString()) : 0;
        int dosP    = (!views[9].getText().toString().trim().isEmpty())? Integer.parseInt(views[9].getText().toString()) : 0;
        int unoP    = (!views[10].getText().toString().trim().isEmpty())? Integer.parseInt(views[10].getText().toString()) : 0;
        int cincuC  = (!views[11].getText().toString().trim().isEmpty())? Integer.parseInt(views[11].getText().toString()) : 0;
        int veinteC = (!views[12].getText().toString().trim().isEmpty())? Integer.parseInt(views[12].getText().toString()) : 0;
        int diezC   = (!views[13].getText().toString().trim().isEmpty())? Integer.parseInt(views[13].getText().toString()) : 0;

        double subTM = (diezP * 10) + (cincoP * 5) + (dosP * 2) + unoP + (cincuC * 0.50) + (veinteC * 0.20) + (diezC * 0.10);

        views[14].setText(String.valueOf(subTM));

        double total = subTB + subTM;

        views[15].setText(String.valueOf(total));

        double cambio = total - pagoRealizado;

        if (cambio > 0)
            views[16].setText(String.valueOf(cambio));
        else
            views[16].setText("0");

    }

}
