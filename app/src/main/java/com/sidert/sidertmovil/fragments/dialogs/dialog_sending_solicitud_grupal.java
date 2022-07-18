package com.sidert.sidertmovil.fragments.dialogs;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.utils.SessionManager;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import static com.sidert.sidertmovil.utils.Constants.ES_RENOVACION;
import static com.sidert.sidertmovil.utils.Constants.ID_SOLICITUD;

public class dialog_sending_solicitud_grupal extends DialogFragment {
    private Context ctx;
    private SessionManager session;

    ImageView ivClose;
    ProgressBar pbSending;
    TextView tvError;
    TextView tvTitle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.popup_sending_solicitud_grupal, container, false);

        ctx     = getContext();
        session = new SessionManager(ctx);

        ivClose   = v.findViewById(R.id.ivClose);
        pbSending = v.findViewById(R.id.pbSending);
        tvError   = v.findViewById(R.id.tvError);
        tvTitle   = v.findViewById(R.id.tvTitle);

        Long idSolicitud = getArguments().getLong(ID_SOLICITUD);
        boolean esRenovacion = getArguments().getBoolean(ES_RENOVACION);

        if(esRenovacion)
        {
            SendRenovacion(idSolicitud);
        }
        else
        {
            SendOriginacion(idSolicitud);
        }

        return v;
    }

    private void SendRenovacion(long idSolicitud)
    {

    }

    private void SendOriginacion(long idSolicitud)
    {

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ivClose.setOnClickListener(ivClose_OnClick);
    }

    private View.OnClickListener ivClose_OnClick = view -> {
        dismiss();
    };
}
