package com.sidert.sidertmovil.utils;

import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.widget.EditText;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class CustomWatcher implements TextWatcher {

    private EditText et;
    private String prefix = "$";
    private static final int MAX_LENGTH = 10;
    private static final int MAX_DECIMAL = 2;
    private String previousCleanString;

    public CustomWatcher(EditText et) {
        this.et = et;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        /*if (!s.toString().matches("^\\$(\\d{1,3}(\\,\\d{3})*|(\\d+))(\\.\\d{2})?$")) {
            String userInput = "" + s.toString().replaceAll("[^\\d]", "");
            StringBuilder cashAmountBuilder = new StringBuilder(userInput);

            while (cashAmountBuilder.length() > 3 && cashAmountBuilder.charAt(0) == '0') {
                cashAmountBuilder.deleteCharAt(0);
            }
            while (cashAmountBuilder.length() < 3) {
                cashAmountBuilder.insert(0, '0');
            }
            cashAmountBuilder.insert(cashAmountBuilder.length() - 2, '.');

            et.removeTextChangedListener(this);
            et.setText(cashAmountBuilder.toString());

            et.setTextKeepState("$" + cashAmountBuilder.toString());
            Selection.setSelection(et.getText(), cashAmountBuilder.toString().length() + 1);

            et.addTextChangedListener(this);
        }*/

        String str = s.toString();
        if (str.length() < prefix.length()) {
            et.setText(prefix);
            et.setSelection(prefix.length());
            return;
        }
        if (str.equals(prefix)) {
            return;
        }
        // cleanString this the string which not contain prefix and ,
        String cleanString = str.replace(prefix, "").replaceAll("[,]", "");
        // for prevent afterTextChanged recursive call
        if (cleanString.equals(previousCleanString) || cleanString.isEmpty()) {
            return;
        }
        previousCleanString = cleanString;

        String formattedString;
        if (cleanString.contains(".")) {
            formattedString = formatDecimal(cleanString);
        } else {
            formattedString = formatInteger(cleanString);
        }
        et.removeTextChangedListener(this); // Remove listener
        et.setText(formattedString);
        handleSelection();
        et.addTextChangedListener(this); // Add back the listener
    }

    private String formatInteger(String str) {
        BigDecimal parsed = new BigDecimal(str);
        DecimalFormat formatter =
                new DecimalFormat(prefix + "###", new DecimalFormatSymbols(Locale.US));
        return formatter.format(parsed);
    }

    private String formatDecimal(String str) {
        if (str.equals(".")) {
            return prefix + ".";
        }
        BigDecimal parsed = new BigDecimal(str);
        // example pattern VND #,###.00
        DecimalFormat formatter = new DecimalFormat(prefix + "###." + getDecimalPattern(str),
                new DecimalFormatSymbols(Locale.US));
        formatter.setRoundingMode(RoundingMode.DOWN);
        return formatter.format(parsed);
    }

    private String getDecimalPattern(String str) {
        int decimalCount = str.length() - str.indexOf(".") - 1;
        StringBuilder decimalPattern = new StringBuilder();
        for (int i = 0; i < decimalCount && i < MAX_DECIMAL; i++) {
            decimalPattern.append("0");
        }
        return decimalPattern.toString();
    }

    private void handleSelection() {
        if (et.getText().length() <= MAX_LENGTH) {
            et.setSelection(et.getText().length());
        } else {
            et.setSelection(MAX_LENGTH);
        }
    }
}
