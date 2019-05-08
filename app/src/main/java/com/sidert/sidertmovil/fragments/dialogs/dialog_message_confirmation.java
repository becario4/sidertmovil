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

public class dialog_message_confirmation extends DialogFragment {

    private Button btnAccept, btnCancel;
    private TextView tvMessage;
    private Context ctx;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.popup_message_confirmation,container,false);
        ctx                  = getContext();
        tvMessage            = view.findViewById(R.id.tvMessage);
        btnAccept            = view.findViewById(R.id.btnAccept);
        btnCancel            = view.findViewById(R.id.btnCancel);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        btnCancel.setOnClickListener(btnCancel_onClick);
    }

    private View.OnClickListener btnCancel_onClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            getDialog().dismiss();
        }
    };
}
