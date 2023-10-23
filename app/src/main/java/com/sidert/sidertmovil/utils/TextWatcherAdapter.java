package com.sidert.sidertmovil.utils;

import android.text.TextWatcher;

public interface TextWatcherAdapter extends TextWatcher {

    @Override
    default void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    default void onTextChanged(CharSequence s, int start, int before, int count) {
    }
}
