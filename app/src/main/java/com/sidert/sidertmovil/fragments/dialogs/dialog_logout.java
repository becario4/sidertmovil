package com.sidert.sidertmovil.fragments.dialogs;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.Nullable;
//import android.support.v4.app.ActivityCompat;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.DialogFragment;
import timber.log.Timber;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;

import com.sidert.sidertmovil.Home;
import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.utils.Constants;
import com.sidert.sidertmovil.utils.Miscellaneous;
import com.sidert.sidertmovil.utils.SessionManager;

public class dialog_logout extends DialogFragment {

    private static final String TAG = dialog_logout.class.getName();
    private Context ctx;
    private SessionManager session;
    private Button btnAccept, btnCancel;
    private Home boostrap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.popup_logout, container, false);
        ctx = getContext();
        boostrap = (Home) getActivity();
        session = SessionManager.getInstance(ctx);
        btnAccept = view.findViewById(R.id.btnAccept);
        btnCancel = view.findViewById(R.id.btnCancel);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        btnAccept.setOnClickListener(btnAccept_OnClick);
        btnCancel.setOnClickListener(btnCancel_onClick);
    }

    private final View.OnClickListener btnAccept_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //WorkManager mWorkManager = WorkManager.getInstance();
            ////mWorkManager.cancelAllWork();
            //Log.e("Servicio Activo", Miscellaneous.JobServiceEnable(ctx, Constants.ID_JOB_SINCRONIZADO, "Sincronizado") +" Enable");
            Handler handler = new Handler();
            handler.postDelayed(() -> {
                session.deleteUser();
                try {
                    Class<?> loginActivityV2 = Class.forName("com.sidert.sidertmovil.v2.ui.LoginActivity");
                    Timber.tag(TAG).i(loginActivityV2.getName());
                    Intent i = new Intent(ctx, loginActivityV2);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);
                } catch (ClassNotFoundException e) {
                    Log.e(TAG, "TriggerRebirth", e);
                }
            }, 3_500);
        }
    };

    private final View.OnClickListener btnCancel_onClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            getDialog().dismiss();
        }
    };

}
