package com.sidert.sidertmovil.utils;

//import android.support.design.widget.TextInputLayout;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputLayout;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidatorTIL {

    public final String REQUIRED        = "required";
    public final String ONLY_TEXT       = "text";
    public final String ONLY_NUMBER     = "number";
    public final String GENERAL         = "general";
    public final String PHONE           = "phone";

    public  String REQUIRED_MESSAGE       = "Este campo es requerido.";
    public  String ONLY_TEXT_MESSAGE      = "Solo permite letras y espacios.";
    public  String ONLY_NUMBER_MESSAGE    = "Solo permite números.";
    public  String GENERAL_MESSAGE        = "Solo letras y/o números";
    public  String ONLY_TEN_NUMBERS       = "Debe contener 10 carcateres numéricos";

    private final String PATTERN_ONLY_TEXT      = "[A-Za-z ÑñÁáÉéÍíÓóÚú]*";
    private final String PATTERN_ONLY_NUMBER    = "[0-9]*";
    private final String PATTERN_GENERAL        = "[0-9 A-Za-zÑñÁáÉéÍíÓóÚú&]*";

    private Pattern pattern;
    private Matcher matcher;

    public boolean validate(TextInputLayout til, EditText etx, String[] RULS) {
        Boolean error = false;

        for (int i = 0; i< RULS.length; i++) {
            switch (RULS[i]) {
                case REQUIRED:
                    if(etx.getText().toString().trim().length() == 0) {
                        til.setError(REQUIRED_MESSAGE);
                        error = true;
                        return error;
                    }
                    break;
                case ONLY_TEXT:
                    pattern = Pattern.compile(PATTERN_ONLY_TEXT);
                    matcher = pattern.matcher(etx.getText().toString());
                    if(!matcher.matches()) {
                        til.setError(ONLY_TEXT_MESSAGE);
                        error = true;
                        return error;
                    }
                    break;
                case ONLY_NUMBER:
                    pattern = Pattern.compile(PATTERN_ONLY_NUMBER);
                    matcher = pattern.matcher(etx.getText().toString());
                    if(!matcher.matches()) {
                        til.setError(ONLY_NUMBER_MESSAGE);
                        error = true;
                        return error;
                    }
                    break;
                case PHONE:
                    if(etx.getText().toString().trim().length() > 0 && etx.getText().toString().trim().length() < 10){
                        til.setError(ONLY_TEN_NUMBERS);
                        error = true;
                        return error;
                    } else if (etx.getText().toString().trim().length() == 0)
                        return false;
                    else {
                        pattern = Pattern.compile(PATTERN_ONLY_NUMBER);
                        matcher = pattern.matcher(etx.getText().toString());
                        if (!matcher.matches()) {
                            til.setError(ONLY_TEN_NUMBERS);
                            error = true;
                            return error;
                        } else {
                            if (!(etx.getText().length() == 10)) {
                                til.setError(ONLY_TEN_NUMBERS);
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
                        til.setError(GENERAL_MESSAGE);
                        error = true;
                        return error;
                    }
                    break;
            }
            til.setErrorEnabled(false);
        }
        return error;
    }
}
