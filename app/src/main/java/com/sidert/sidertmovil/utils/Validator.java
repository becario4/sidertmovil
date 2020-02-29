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
    public final String CREDITO         = "credito";
    public final String CURP            = "curp";
    public final String CURP_ID         = "curp_id";
    public final String HOMOCLAVE       = "homocalve";
    public final String EMAIL           = "email";
    public final String CP              = "cp";
    public final String YEARS           = "years";
    public final String ALFANUMERICO    = "alfanumerico";

    public String REQUIRED_MESSAGE       = "Este campo es requerido.";
    public String ONLY_TEXT_MESSAGE      = "Solo permite letras y espacios.";
    public String ONLY_NUMBER_MESSAGE    = "Solo permite números.";
    public String GENERAL_MESSAGE        = "Solo letras y/o números";
    public String ONLY_TEN_NUMBERS       = "Debe contener 10 carcateres numéricos";
    public String MENSAJE_MONEDA         = "Verifique el monto ingresado";
    public String MENSAJE_MONTO_CREDITO  = "La cantidad no corresponde a un monto de crédito válido";
    public String MENSAJE_CURP_NO_VALIDA = "No corresponde a una CURP válida";
    public String MSJ_FORMAT_INCORR      = "Formato incorrecto";
    public String MENSAJE_EMAIL          = "Formato de correo inválido";
    public String ALFANUMERICO_MESSAGE   = "Solo se permite letras y números";

    private final String PATTERN_ONLY_TEXT      = "[A-Za-z ÑñÁáÉéÍíÓóÚú]*";
    private final String PATTERN_ONLY_NUMBER    = "[0-9]*";
    private final String PATTERN_GENERAL        = "[0-9 A-Za-zÑñÁáÉéÍíÓóÚú&.,-_]*";
    private final String PATTERN_MONEY          = "([0-9,]+(.[0-9]{1,2})?)";
    private final String PATTERN_MONTO_CREDITO  = "[1-9][0-9][0-9][0][0][0]|[1-9][0-9][0][0][0]|[1-9][0][0][0]";
    private final String PATTERN_CURP           = "[A-Z][AEIOU][A-Z]{2}[0-9]{2}(0[1-9]|1[0-2])(0[1-9]|1[0-9]|2[0-9]|3[0-1])[HM](AS|BC|BS|CC|CS|CH|CL|CM|DF|DG|GT|GR|HG|JC|MC|MN|MS|NT|NL|OC|PL|QT|QR|SP|SL|SR|TC|TS|TL|VZ|YN|ZS|NE)[B-DF-HJ-NP-TV-Z]{3}";
    private final String PATTERN_CURP_ID        = "[0-9]{2}";
    private final String PATTERN_CP             = "[1-9][0-9]{4}";
    private final String PATTERN_HOMOCLAVE      = "[A-Z]{2}[0-9]";
    private final String PATTERN_EMAIL          = "^(.+)@(.+)$";
    private final String PATTERN_YEARS          = "[1-9]|[1-9][0-9]";
    private final String PATTERN_ALFANUMERICO   = "[0-9 A-Za-z]*";

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
                case ALFANUMERICO:
                    pattern = Pattern.compile(PATTERN_ALFANUMERICO);
                    matcher = pattern.matcher(etx.getText().toString());
                    if(!matcher.matches()) {
                        etx.setError(ALFANUMERICO_MESSAGE);
                        error = true;
                        return error;
                    }
                    break;
                case EMAIL:
                    if (etx.getText().length() > 0) {
                        pattern = Pattern.compile(PATTERN_EMAIL);
                        matcher = pattern.matcher(etx.getText().toString());
                        if (!matcher.matches()) {
                            etx.setError(MENSAJE_EMAIL);
                            error = true;
                            return error;
                        }
                    }
                    else
                        return false;
                    break;
                case CREDITO:
                    pattern = Pattern.compile(PATTERN_MONTO_CREDITO);
                    matcher = pattern.matcher(etx.getText().toString());
                    if(!matcher.matches()) {
                        etx.setError(MENSAJE_MONTO_CREDITO);
                        error = true;
                        return error;
                    }
                    break;
                case CURP:
                    pattern = Pattern.compile(PATTERN_CURP);
                    matcher = pattern.matcher(etx.getText().toString());
                    if(!matcher.matches()) {
                        etx.setError(MENSAJE_CURP_NO_VALIDA);
                        error = true;
                        return error;
                    }
                    break;
                case CURP_ID:
                    pattern = Pattern.compile(PATTERN_CURP_ID);
                    matcher = pattern.matcher(etx.getText().toString());
                    if(!matcher.matches()) {
                        etx.setError(MSJ_FORMAT_INCORR);
                        error = true;
                        return error;
                    }
                    break;
                case CP:
                    pattern = Pattern.compile(PATTERN_CP);
                    matcher = pattern.matcher(etx.getText().toString());
                    if(!matcher.matches()) {
                        etx.setError(MSJ_FORMAT_INCORR);
                        error = true;
                        return error;
                    }
                    break;
                case HOMOCLAVE:
                    pattern = Pattern.compile(PATTERN_HOMOCLAVE);
                    matcher = pattern.matcher(etx.getText().toString());
                    if(!matcher.matches()) {
                        etx.setError(MSJ_FORMAT_INCORR);
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
                case YEARS:
                    pattern = Pattern.compile(PATTERN_YEARS);
                    matcher = pattern.matcher(etx.getText().toString());
                    if(!matcher.matches()) {
                        etx.setError(MSJ_FORMAT_INCORR);
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
                    if (etx.getText().length() > 0) {
                        pattern = Pattern.compile(PATTERN_ONLY_NUMBER);
                        matcher = pattern.matcher(etx.getText().toString());
                        if (!matcher.matches()) {
                            Log.e("xxx", "xxx");
                            etx.setError(ONLY_TEN_NUMBERS);
                            error = true;
                            return error;
                        } else {
                            Log.e("ccc", "ccc");
                            if (etx.getText().length() < 10) {
                                Log.e("bbb", "bbb");
                                etx.setError(ONLY_TEN_NUMBERS);
                                error = true;
                                return error;
                            } else {
                                return false;
                            }
                        }
                    }
                    else
                        return false;
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
