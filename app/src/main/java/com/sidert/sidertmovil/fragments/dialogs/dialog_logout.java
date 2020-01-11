package com.sidert.sidertmovil.fragments.dialogs;

import android.app.job.JobScheduler;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.sidert.sidertmovil.Home;
import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.utils.Constants;
import com.sidert.sidertmovil.utils.Miscellaneous;
import com.sidert.sidertmovil.utils.SessionManager;

import static android.content.Context.JOB_SCHEDULER_SERVICE;

public class dialog_logout extends DialogFragment {

    private Context ctx;
    private SessionManager session;
    private Button btnAccept, btnCancel;
    private Home boostrap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.popup_logout,container,false);
        ctx                  = getContext();
        boostrap             = (Home) getActivity();
        session              = new SessionManager(ctx);
        btnAccept            = view.findViewById(R.id.btnAccept);
        btnCancel            = view.findViewById(R.id.btnCancel);
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

    private View.OnClickListener btnAccept_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            session.setUser(session.getUser().get(0),
                    session.getUser().get(1),
                    session.getUser().get(2),
                    session.getUser().get(3),
                    session.getUser().get(4),
                    session.getUser().get(5),
                    false,
                    session.getUser().get(7));
            JobScheduler scheduler = (JobScheduler) ctx.getSystemService(JOB_SCHEDULER_SERVICE);
            scheduler.cancel(Constants.ID_JOB_SINCRONIZADO);
            Log.e("Servicio Activo", Miscellaneous.JobServiceEnable(ctx, Constants.ID_JOB_SINCRONIZADO, "Sincronizado") +" Enable");
            triggerRebirth();
            getDialog().dismiss();
        }
    };

    private View.OnClickListener btnCancel_onClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            getDialog().dismiss();
        }
    };

    public void triggerRebirth() {
        Intent i = boostrap.getPackageManager().getLaunchIntentForPackage(boostrap.getPackageName());
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ActivityCompat.finishAffinity(boostrap);
        startActivity(i);
    }
}
