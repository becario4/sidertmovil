package com.sidert.sidertmovil.fragments.dialogs;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;

import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.activities.GroupCollection;
import com.sidert.sidertmovil.activities.GroupExpiredWallet;
import com.sidert.sidertmovil.activities.GroupRecovery;
import com.sidert.sidertmovil.activities.IndividualCollection;
import com.sidert.sidertmovil.activities.IndividualExpiredWallet;
import com.sidert.sidertmovil.activities.IndividualRecovery;
import com.sidert.sidertmovil.models.ModeloIndividual;
import com.sidert.sidertmovil.utils.Constants;
import com.sidert.sidertmovil.utils.Miscellaneous;

public class dialog_details_order extends DialogFragment {

    private ImageButton ibOrderDetails, ibClose;
    private  TextView tvExternalID,tvNombre, tvDireccion, tvNumeroPrestamo, tvMontoPrestamo, tvFechaPagoEstablecida, tvTelefono;
    private Context ctx;
    ModeloIndividual ficha;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.details_order_popup,container,false);
        ctx                     = getContext();
        tvExternalID            = view.findViewById(R.id.tvExternalID);
        tvNombre                = view.findViewById(R.id.tvNombre);
        tvDireccion             = view.findViewById(R.id.tvDireccion);
        tvNumeroPrestamo        = view.findViewById(R.id.tvNumeroPrestamo);
        tvMontoPrestamo         = view.findViewById(R.id.tvMontoPrestamo);
        tvFechaPagoEstablecida  = view.findViewById(R.id.tvFechaPagoEstablecida);
        tvTelefono              = view.findViewById(R.id.tvTelefono);
        ibOrderDetails          = view.findViewById(R.id.ibDetails);
        ibClose                 = view.findViewById(R.id.ibClose);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        ficha = (ModeloIndividual) getArguments().getSerializable(Constants.FICHA);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        tvExternalID.setText(ficha.getId());
        tvNombre.setText(ficha.getCliente().getNombre());
        tvDireccion.setText(ficha.getCliente().getDireccion().getCalle() + ", "+
                ficha.getCliente().getDireccion().getColonia() + ", " +
                ficha.getCliente().getDireccion().getCiudad());
        tvNumeroPrestamo.setText(ficha.getPrestamo().getNumeroDePrestamo());
        tvMontoPrestamo.setText(Miscellaneous.moneyFormat(String.valueOf(ficha.getPrestamo().getMontoPrestamo())));
        tvFechaPagoEstablecida.setText(ficha.getPrestamo().getFechaPagoEstablecida());
        tvTelefono.setText(ficha.getCliente().getTelCelular());

        ibOrderDetails.setOnClickListener(ibOrderDetails_OnClick);
        ibClose.setOnClickListener(ibClose_OnClick);
    }

    private View.OnClickListener ibOrderDetails_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent_order = null;
            switch (ficha.getType()){
                case Constants.RECUPERACION_INDIVIDUAL:
                    intent_order = new Intent(ctx, IndividualRecovery.class);
                    intent_order.putExtra(Constants.FICHA, ficha);
                    startActivity(intent_order);
                    break;
                case Constants.RECUPERACION_GRUPAL:
                    intent_order = new Intent(ctx, GroupRecovery.class);
                    intent_order.putExtra(Constants.FICHA, ficha);
                    startActivity(intent_order);
                    break;
            }

        }
    };

    private View.OnClickListener ibClose_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            getDialog().dismiss();
        }
    };
}
