package com.sidert.sidertmovil.fragments.dialogs;


import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;

import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.utils.Constants;
import com.sidert.sidertmovil.utils.NetworkStatus;
import com.sidert.sidertmovil.utils.Popups;
import com.sidert.sidertmovil.utils.SessionManager;
import com.sidert.sidertmovil.utils.Validator;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class dialog_mailbox extends DialogFragment {

    private Context ctx;
    private EditText etSubject;
    private MultiAutoCompleteTextView etReason;
    private Button btnSend;
    private Button btnClose;

    private SessionManager session;
    private Validator validator;

    private String today = "";

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private Calendar calendar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.popup_mailbox, container, false);
        ctx         = getContext();
        etSubject   = view.findViewById(R.id.etSubject);
        etReason    = view.findViewById(R.id.etReason);
        btnSend     = view.findViewById(R.id.btnSend);
        btnClose    = view.findViewById(R.id.btnClose);

        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        setCancelable(false);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        validator = new Validator();
        session = new SessionManager(ctx);
        calendar = Calendar.getInstance();

        today = sdf.format(calendar.getTime());

        btnSend.setOnClickListener(btnSend_OnClick);
        btnClose.setOnClickListener(btnClose_OnClick);
    }

    private View.OnClickListener btnSend_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(!validator.validate(etSubject, new String[] {validator.REQUIRED}) &&
               !validator.validate(etReason, new String[] {validator.REQUIRED})) {
                SendComplaint();
            }
        }
    };

    private View.OnClickListener btnClose_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            getDialog().dismiss();
        }
    };

    private void SendComplaint(){
        if (NetworkStatus.haveNetworkConnection(ctx)){

        }
        else {
            final AlertDialog errorInternet = Popups.showDialogMessage(ctx, Constants.not_network, ctx.getResources().getString(R.string.not_network), ctx.getResources().getString(R.string.accept), new Popups.DialogMessage() {
                @Override
                public void OnClickListener(AlertDialog dialog) {
                    dialog.dismiss();
                }
            }, null, null);
            errorInternet.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            errorInternet.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            errorInternet.show();

        }
    }
}
