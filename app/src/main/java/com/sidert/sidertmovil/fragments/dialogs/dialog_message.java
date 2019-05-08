package com.sidert.sidertmovil.fragments.dialogs;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.utils.Constants;

public class dialog_message extends DialogFragment {

    private Button btnAccept;
    private TextView tvMessage;
    private Context ctx;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.popup_message,container,false);
        ctx                  = getContext();
        tvMessage            = view.findViewById(R.id.tvMessage);
        btnAccept            = view.findViewById(R.id.btnAccept);
        //getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        tvMessage.setText(getArguments().getString(Constants.message));
        btnAccept.setOnClickListener(btnAccept_onClick);
    }

    private View.OnClickListener btnAccept_onClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            getDialog().dismiss();
        }
    };
}