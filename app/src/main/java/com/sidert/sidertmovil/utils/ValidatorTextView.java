package com.sidert.sidertmovil.utils;

import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidatorTextView {

    public final String REQUIRED        = "required";
    public final String ONLY_TEXT       = "text";
    public final String ONLY_NUMBER     = "number";
    public final String GENERAL         = "general";
    public final String PHONE           = "phone";
    public final String MONEY           = "money";
    public final String CREDITO         = "credito";
    public final String CURP            = "curp";
    public final String CURP_ID         = "curp_id";
    public final String RFC             = "rfc";


    public  String REQUIRED_MESSAGE       = "Este campo es requerido.";
    public  String ONLY_TEXT_MESSAGE      = "Solo permite letras y espacios.";
    public  String ONLY_NUMBER_MESSAGE    = "Solo permite números.";
    public  String GENERAL_MESSAGE        = "Solo letras y/o números";
    public  String ONLY_TEN_NUMBERS       = "Debe contener 10 carcateres numéricos";
    public  String MENSAJE_MONEDA         = "Verifique el monto ingresado";
    public  String MENSAJE_MONTO_CREDITO  = "La cantidad no corresponde a un monto de crédito válido";
    public  String MENSAJE_CURP_NO_VALIDA = "No corresponde a una CURP válida";
    public  String MENSAJE_CURP_ID        = "Formato incorrecto";
    public  String MENSAJE_RFC            = "No corresponde a un RFC válido";

    private final String PATTERN_ONLY_TEXT      = "[A-Za-z ÑñÁáÉéÍíÓóÚú]*";
    private final String PATTERN_ONLY_NUMBER    = "[0-9]*";
    private final String PATTERN_GENERAL        = "[0-9 A-Za-zÑñÁáÉéÍíÓóÚú&.,-_]*";
    private final String PATTERN_MONEY          = "([0-9,]+(.[0-9]{1,2})?)";
    private final String PATTERN_MONTO_CREDITO  = "[1-9][0-9][0-9][0][0][0]|[1-9][0-9][0][0][0]|[1-9][0][0][0]";
    private final String PATTERN_CURP           = "[A-Z][AEIOU][A-Z]{2}[0-9]{2}(0[1-9]|1[0-2])(0[1-9]|1[0-9]|2[0-9]|3[0-1])[HM](AS|BC|BS|CC|CS|CH|CL|CM|DF|DG|GT|GR|HG|JC|MC|MN|MS|NT|NL|OC|PL|QT|QR|SP|SL|SR|TC|TS|TL|VZ|YN|ZS|NE)[B-DF-HJ-NP-TV-Z]{3}";
    private final String PATTERN_CURP_ID        = "[0-9]{2}";
    private final String PATTERN_RFC            = "[A-Z][AEIOU][A-Z]{2}[0-9]{2}(0[1-9]|1[0-2])(0[1-9]|1[0-9]|2[0-9]|3[0-1])|[A-Z][AEIOU][A-Z]{2}[0-9]{2}(0[1-9]|1[0-2])(0[1-9]|1[0-9]|2[0-9]|3[0-1])([A-Zd]{2})(Ad)";

    private Pattern pattern;
    private Matcher matcher;

    public boolean validate(TextView tvx, String[] RULS) {
        Boolean error = false;

        for (int i = 0; i< RULS.length; i++) {
            switch (RULS[i]) {
                case REQUIRED:
                    if(tvx.getText().toString().trim().length() == 0) {
                        tvx.setError(REQUIRED_MESSAGE);
                        tvx.setFocusable(true);
                        tvx.setFocusableInTouchMode(true);
                        tvx.requestFocus();
                        error = true;
                        return error;
                    }
                    break;
                case ONLY_TEXT:
                    pattern = Pattern.compile(PATTERN_ONLY_TEXT);
                    matcher = pattern.matcher(tvx.getText().toString());
                    if(!matcher.matches()) {
                        tvx.setError(ONLY_TEXT_MESSAGE);
                        tvx.setFocusable(true);
                        tvx.setFocusableInTouchMode(true);
                        tvx.requestFocus();
                        error = true;
                        return error;
                    }
                    break;
                case CREDITO:
                    pattern = Pattern.compile(PATTERN_MONTO_CREDITO);
                    matcher = pattern.matcher(tvx.getText().toString());
                    if(!matcher.matches()) {
                        tvx.setError(MENSAJE_MONTO_CREDITO);
                        tvx.requestFocus();
                        tvx.setFocusable(true);
                        tvx.setFocusableInTouchMode(true);
                        error = true;
                        return error;
                    }
                    break;
                case CURP:
                    pattern = Pattern.compile(PATTERN_CURP);
                    matcher = pattern.matcher(tvx.getText().toString());
                    if(!matcher.matches()) {
                        tvx.setError(MENSAJE_CURP_NO_VALIDA);
                        tvx.requestFocus();
                        error = true;
                        return error;
                    }
                    break;
                case CURP_ID:
                    pattern = Pattern.compile(PATTERN_CURP_ID);
                    matcher = pattern.matcher(tvx.getText().toString());
                    if(!matcher.matches()) {
                        tvx.setError(MENSAJE_CURP_ID);
                        tvx.requestFocus();
                        tvx.setFocusable(true);
                        tvx.setFocusableInTouchMode(true);
                        error = true;
                        return error;
                    }
                    break;
                case RFC:
                    pattern = Pattern.compile(PATTERN_RFC);
                    matcher = pattern.matcher(tvx.getText().toString());
                    if(!matcher.matches()) {
                        tvx.setError(MENSAJE_RFC);
                        tvx.setFocusable(true);
                        tvx.setFocusableInTouchMode(true);
                        error = true;
                        return error;
                    }
                    break;
                case ONLY_NUMBER:
                    pattern = Pattern.compile(PATTERN_ONLY_NUMBER);
                    matcher = pattern.matcher(tvx.getText().toString());
                    if(!matcher.matches()) {
                        tvx.setError(ONLY_NUMBER_MESSAGE);
                        tvx.setFocusable(true);
                        tvx.setFocusableInTouchMode(true);
                        tvx.requestFocus();
                        error = true;
                        return error;
                    }
                    break;
                case MONEY:
                    pattern = Pattern.compile(PATTERN_MONEY);
                    matcher = pattern.matcher(tvx.getText().toString());
                    if(!matcher.matches()) {
                        tvx.setError(MENSAJE_MONEDA);
                        tvx.setFocusable(true);
                        tvx.setFocusableInTouchMode(true);
                        tvx.setFocusable(true);
                        error = true;
                        return error;
                    }
                    else {
                        if (Double.parseDouble(tvx.getText().toString()) == 0){
                            tvx.setError("No se permiten cantidades iguales a 0");
                            tvx.setFocusable(true);
                            tvx.setFocusableInTouchMode(true);
                            tvx.requestFocus();
                            error = true;
                            return error;
                        }
                    }
                    break;
                case PHONE:
                    pattern = Pattern.compile(PATTERN_ONLY_NUMBER);
                    matcher = pattern.matcher(tvx.getText().toString());
                    if (!matcher.matches()) {
                        tvx.setError(ONLY_TEN_NUMBERS);
                        tvx.setFocusable(true);
                        tvx.setFocusableInTouchMode(true);
                        tvx.requestFocus();
                        error = true;
                        return error;
                    } else {
                        if (!(tvx.getText().length() == 10)) {
                            tvx.setError(ONLY_TEN_NUMBERS);
                            tvx.setFocusable(true);
                            tvx.setFocusableInTouchMode(true);
                            tvx.requestFocus();
                            error = false;
                            return error;
                        }
                        else {
                            return true;
                        }
                    }
                case GENERAL:
                    pattern = Pattern.compile(PATTERN_GENERAL);
                    matcher = pattern.matcher(tvx.getText().toString().toLowerCase());
                    if(!matcher.matches()) {
                        tvx.setError(GENERAL_MESSAGE);
                        tvx.setFocusable(true);
                        tvx.setFocusableInTouchMode(true);
                        tvx.requestFocus();
                        error = true;
                        return error;
                    }
                    break;
            }
        }

        return error;
    }
}
