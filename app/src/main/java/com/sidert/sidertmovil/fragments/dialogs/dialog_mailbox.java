package com.sidert.sidertmovil.fragments.dialogs;


import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.models.MailBoxPLD;
import com.sidert.sidertmovil.models.MailBoxResponse;
import com.sidert.sidertmovil.utils.Constants;
import com.sidert.sidertmovil.utils.ManagerInterface;
import com.sidert.sidertmovil.utils.Miscellaneous;
import com.sidert.sidertmovil.utils.NetworkStatus;
import com.sidert.sidertmovil.utils.Popups;
import com.sidert.sidertmovil.utils.RetrofitClient;
import com.sidert.sidertmovil.utils.SessionManager;
import com.sidert.sidertmovil.utils.Validator;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.sidert.sidertmovil.utils.Constants.CONTROLLER_DENUNCIAS;
import static com.sidert.sidertmovil.utils.Constants.CONTROLLER_MOVIL;

public class dialog_mailbox extends DialogFragment {

    private Context ctx;
    //private EditText etSubject;
    private Spinner spAsunto;
    private LinearLayout llOtroAsunto;
    private EditText etOtroAsunto;
    private EditText etNombreDenunciado;
    private Spinner spPuestoDenunciado;
    private MultiAutoCompleteTextView etReason;
    private TextView tvConceptoSize;
    private Button btnSend;
    private ImageView ivClose;

    private SessionManager session;
    private Validator validator;

    private String today = "";

    private SimpleDateFormat sdf = new SimpleDateFormat(Constants.FORMAT_DATE_GNRAL);
    private Calendar calendar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.popup_mailbox, container, false);
        ctx         = getContext();
        //etSubject   = view.findViewById(R.id.etSubject);
        spAsunto            = view.findViewById(R.id.spAsunto);
        llOtroAsunto        = view.findViewById(R.id.llOtroAsunto);
        etOtroAsunto        = view.findViewById(R.id.etOtroAsunto);
        etNombreDenunciado  = view.findViewById(R.id.etNombreDenunciado);
        spPuestoDenunciado  = view.findViewById(R.id.spPuestoDenunciado);
        etReason    = view.findViewById(R.id.etReason);
        tvConceptoSize  = view.findViewById(R.id.tvConceptoSize);
        btnSend     = view.findViewById(R.id.btnSend);
        ivClose    = view.findViewById(R.id.ivClose);

        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

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
        ivClose.setOnClickListener(ivClose_OnClick);

        spAsunto.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 1){
                    llOtroAsunto.setVisibility(View.VISIBLE);
                }
                else{
                    llOtroAsunto.setVisibility(View.GONE);
                    etOtroAsunto.setText("");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        etReason.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable e) {
                tvConceptoSize.setText(e.length()+"/400");
            }
        });
    }

    private View.OnClickListener btnSend_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            if (spAsunto.getSelectedItemPosition() != 0){
                if ((spAsunto.getSelectedItemPosition() > 1  &&
                !validator.validate(etNombreDenunciado, new String[] {validator.REQUIRED, validator.ONLY_TEXT}) &&
                !validator.validate(etReason, new String[] {validator.REQUIRED, validator.GENERAL})) ||
                (spAsunto.getSelectedItemPosition() == 1  &&
                !validator.validate(etOtroAsunto, new String[] {validator.REQUIRED, validator.ONLY_TEXT}) &&
                !validator.validate(etNombreDenunciado, new String[] {validator.REQUIRED, validator.ONLY_TEXT}) &&
                !validator.validate(etReason, new String[] {validator.REQUIRED, validator.GENERAL}))
                ){
                    if (spPuestoDenunciado.getSelectedItemPosition() != 0){
                        if( (session.getMailBox().get(0).equals("") || session.getMailBox().get(1).equals("0")) ||
                                (validateDate(session.getMailBox().get(0), sdf.format(calendar.getTime()), true) && Integer.parseInt(session.getMailBox().get(1)) <= Constants.LIMIT_COMPLAINTS) ||
                                (validateDate(session.getMailBox().get(0), sdf.format(calendar.getTime()), false) && Integer.parseInt(session.getMailBox().get(1)) <= Constants.LIMIT_COMPLAINTS))
                        {
                            //Toast.makeText(ctx, "Se envia mensaje", Toast.LENGTH_SHORT).show();
                            SendComplaint();
                        }
                        else{
                            final AlertDialog limitDialog = Popups.showDialogMessage(ctx, Constants.face_dissatisfied,
                                    R.string.limit_mailbox, R.string.accept, new Popups.DialogMessage() {
                                        @Override
                                        public void OnClickListener(AlertDialog dialog) {
                                            dialog.dismiss();
                                            getDialog().dismiss();
                                        }
                                    });
                            limitDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                            limitDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                            limitDialog.show();
                        }
                    }
                    else{
                        Toast.makeText(ctx, "Seleccione el puesto del denunciado", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            else
                Toast.makeText(ctx, "Seleccione el asunto", Toast.LENGTH_SHORT).show();

        }
    };

    private View.OnClickListener ivClose_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            getDialog().dismiss();
        }
    };

    private void SendComplaintPruebaSinGuardado(){
        final AlertDialog loading = Popups.showLoadingDialog(ctx,R.string.please_wait, R.string.loading_info);
        loading.show();
        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                loading.dismiss();
                session.setMailBox(sdf.format(calendar.getTime()),(session.getMailBox().get(1) + 1) );
                final AlertDialog success = Popups.showDialogMessage(ctx, Constants.face_happy,
                        R.string.success_mailbox, R.string.accept, new Popups.DialogMessage() {
                    @Override
                    public void OnClickListener(AlertDialog dialog) {
                        dialog.dismiss();
                        getDialog().dismiss();
                    }
                });
                success.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                success.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                success.show();
            }
        },3000);
    }
    private void SendComplaint(){

        if (NetworkStatus.haveNetworkConnection(ctx)){
            final AlertDialog loading = Popups.showLoadingDialog(ctx,R.string.please_wait, R.string.loading_info);
            loading.show();



            ManagerInterface api = new RetrofitClient().generalRF(CONTROLLER_MOVIL, ctx).create(ManagerInterface.class);

            MailBoxPLD obj = new MailBoxPLD("",
                    sdf.format(calendar.getTime()),
                    Miscellaneous.GetStr(etNombreDenunciado),
                    spPuestoDenunciado.getSelectedItem().toString(),
                    Miscellaneous.GetStr(etOtroAsunto),
                    spAsunto.getSelectedItemId(),
                    Miscellaneous.GetStr(etReason));

            Log.e("objeto", Miscellaneous.ConvertToJson(obj));
            Call<MailBoxResponse> call = api.setMailBox(obj);


            call.enqueue(new Callback<MailBoxResponse>() {
                @Override
                public void onResponse(Call<MailBoxResponse> call, Response<MailBoxResponse> response) {
                    Log.e("CodeDenuncia", "Code: "+response.code());

                    loading.dismiss();
                    MailBoxResponse res = response.body();
                    switch (res.getCode()){
                        case 200:
                            session.setMailBox(sdf.format(calendar.getTime()),(session.getMailBox().get(1) + 1) );
                            final AlertDialog success = Popups.showDialogMessage(ctx, Constants.face_happy,
                                    R.string.success_mailbox, R.string.accept, new Popups.DialogMessage() {
                                @Override
                                public void OnClickListener(AlertDialog dialog) {
                                    dialog.dismiss();
                                    getDialog().dismiss();
                                }
                            });
                            success.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                            success.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                            success.show();
                            break;
                        case 400:
                            final AlertDialog error = Popups.showDialogMessage(ctx, Constants.face_dissatisfied,
                                    R.string.error_contact_TI, R.string.accept, new Popups.DialogMessage() {
                                @Override
                                public void OnClickListener(AlertDialog dialog) {
                                    dialog.dismiss();
                                    getDialog().dismiss();
                                }
                            });
                            error.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                            error.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                            error.show();
                            break;
                    }
                }
                @Override
                public void onFailure(Call<MailBoxResponse> call, Throwable t) {
                    Log.v("errorService", t.getMessage());
                    loading.dismiss();
                }
            });


        }
        else {
            final AlertDialog errorInternet = Popups.showDialogMessage(ctx, Constants.not_network,
                    R.string.not_network, R.string.accept, new Popups.DialogMessage() {
                @Override
                public void OnClickListener(AlertDialog dialog) {
                    dialog.dismiss();
                }
            });
            errorInternet.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            errorInternet.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            errorInternet.show();

        }
    }

    private boolean validateDate (String dateMail, String today, boolean flag){
        Date dateMailBox = null;
        Date current = null;
        boolean res = false;
        try {
            dateMailBox = sdf.parse(dateMail);
            current = sdf.parse(today);

            if (flag){
                if (dateMailBox.equals(current))
                    res = true;
                else
                    res = false;
            }
            else{
                if (current.after(dateMailBox)) {
                    session.setMailBox(today,"0");
                    res = true;
                }
                else
                    res = false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return res;
    }
}
