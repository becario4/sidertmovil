package com.sidert.sidertmovil.utils;

import android.util.Log;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {

    public final String REQUIRED        = "required";
    public final String ONLY_TEXT       = "text";
    public final String ONLY_NUMBER     = "number";
    public final String GENERAL         = "general";
    public final String PHONE           = "phone";
    public final String MONEY           = "money";

    public  String REQUIRED_MESSAGE       = "Este campo es requerido.";
    public  String ONLY_TEXT_MESSAGE      = "Solo permite letras y espacios.";
    public  String ONLY_NUMBER_MESSAGE    = "Solo permite números.";
    public  String GENERAL_MESSAGE        = "Solo letras y/o números";
    public  String ONLY_TEN_NUMBERS       = "Debe contener 10 carcateres numéricos";
    public  String MENSAJE_MONEDA         = "Verifique el monto ingresado";

    private final String PATTERN_ONLY_TEXT      = "[A-Za-z ÑñÁáÉéÍíÓóÚú]*";
    private final String PATTERN_ONLY_NUMBER    = "[0-9]*";
    private final String PATTERN_GENERAL        = "[0-9 A-Za-zÑñÁáÉéÍíÓóÚú&.,-_]*";
    private final String PATTERN_MONEY          = "([0-9,]+(.[0-9]{1,2})?)";

    private Pattern pattern;
    private Matcher matcher;

    public boolean validate(EditText etx, String[] RULS) {
        Boolean error = false;

        for (int i = 0; i< RULS.length; i++) {
            switch (RULS[i]) {
                case REQUIRED:
                    if(etx.getText().toString().trim().length() == 0) {
                        etx.setError(REQUIRED_MESSAGE);
                        error = true;
                        return error;
                    }
                    break;
                case ONLY_TEXT:
                    pattern = Pattern.compile(PATTERN_ONLY_TEXT);
                    matcher = pattern.matcher(etx.getText().toString());
                    if(!matcher.matches()) {
                        etx.setError(ONLY_TEXT_MESSAGE);
                        error = true;
                        return error;
                    }
                    break;
                case ONLY_NUMBER:
                    pattern = Pattern.compile(PATTERN_ONLY_NUMBER);
                    matcher = pattern.matcher(etx.getText().toString());
                    if(!matcher.matches()) {
                        etx.setError(ONLY_NUMBER_MESSAGE);
                        error = true;
                        return error;
                    }
                    break;
                case MONEY:
                    pattern = Pattern.compile(PATTERN_MONEY);
                    matcher = pattern.matcher(etx.getText().toString());
                    if(!matcher.matches()) {
                        etx.setError(MENSAJE_MONEDA);
                        error = true;
                        return error;
                    }
                    else {
                        if (Double.parseDouble(etx.getText().toString()) == 0){
                            etx.setError("No se permiten cantidades iguales a 0");
                            error = true;
                            return error;
                        }
                    }
                    break;
                case PHONE:
                    if(etx.getText().toString().trim().length() > 0 && etx.getText().toString().trim().length() < 10){
                        etx.setError(ONLY_TEN_NUMBERS);
                        error = true;
                        return error;
                    } else if (etx.getText().toString().trim().length() == 0)
                        return false;
                    else {
                        pattern = Pattern.compile(PATTERN_ONLY_NUMBER);
                        matcher = pattern.matcher(etx.getText().toString());
                        if (!matcher.matches()) {
                            etx.setError(ONLY_TEN_NUMBERS);
                            error = true;
                            return error;
                        } else {
                            if (!(etx.getText().length() == 10)) {
                                etx.setError(ONLY_TEN_NUMBERS);
                                error = true;
                                return error;
                            }
                        }
                    }
                    break;
                case GENERAL:
                    pattern = Pattern.compile(PATTERN_GENERAL);
                    matcher = pattern.matcher(etx.getText().toString().toLowerCase());
                    if(!matcher.matches()) {
                        etx.setError(GENERAL_MESSAGE);
                        error = true;
                        return error;
                    }
                    break;
            }
        }

        return error;
    }
}
