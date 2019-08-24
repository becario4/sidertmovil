package com.sidert.sidertmovil.fragments.dialogs;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
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
import com.sidert.sidertmovil.models.ModeloGrupal;
import com.sidert.sidertmovil.models.ModeloIndividual;
import com.sidert.sidertmovil.utils.Constants;
import com.sidert.sidertmovil.utils.Miscellaneous;

public class dialog_details_order extends DialogFragment {

    private ImageButton ibOrderDetails, ibClose;
    private  TextView tvExternalID,tvNombre, tvDireccion, tvNumeroPrestamo, tvMontoPrestamo, tvFechaPagoEstablecida, tvTelefono;
    private Context ctx;
    ModeloIndividual ind;
    ModeloGrupal gpo;

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

        if (getArguments().containsKey(Constants.INDIVIDUAL))
            ind = (ModeloIndividual) getArguments().getSerializable(Constants.INDIVIDUAL);
        else
            gpo = (ModeloGrupal) getArguments().getSerializable(Constants.GRUPO);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        String id = "";
        String nombre = "";
        String direccion = "";
        String numeroPrestamo = "";
        String montoPrestamo = "";
        String fechaPagoEstablecida = "";
        String telefono = "";
        if (ind != null){
            id = ind.getId();
            nombre = ind.getCliente().getNombre();
            direccion = ind.getCliente().getDireccion().getCalle() + ", " + ind.getCliente().getDireccion().getColonia() + ", " + ind.getCliente().getDireccion().getMunicipio();
            numeroPrestamo = ind.getPrestamo().getNumeroDePrestamo();
            montoPrestamo = String.valueOf(ind.getPrestamo().getMontoPrestamo());
            fechaPagoEstablecida = ind.getPrestamo().getFechaPagoEstablecida();
            telefono = ind.getCliente().getTelCelular();

        }
        else {
            id = gpo.getId();
            nombre = gpo.getTesorera().getNombre();
            direccion = gpo.getTesorera().getDireccion().getAddress();
            numeroPrestamo = gpo.getPrestamo().getNumeroDePrestamo();
            montoPrestamo = String.valueOf(gpo.getPrestamo().getMontoPrestamo());
            fechaPagoEstablecida = gpo.getPrestamo().getFechaPagoEstablecida();
            telefono = gpo.getTesorera().getTelefonoCelular();
        }

        tvExternalID.setText(id);
        tvNombre.setText(nombre);
        tvDireccion.setText(direccion);
        tvNumeroPrestamo.setText(numeroPrestamo);
        tvMontoPrestamo.setText(Miscellaneous.moneyFormat(montoPrestamo));
        tvFechaPagoEstablecida.setText(fechaPagoEstablecida);
        tvTelefono.setText(telefono);

        ibOrderDetails.setOnClickListener(ibOrderDetails_OnClick);
        ibClose.setOnClickListener(ibClose_OnClick);
    }

    private View.OnClickListener ibOrderDetails_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent_order = null;
            if (ind != null){
                intent_order = new Intent(ctx, IndividualRecovery.class);
                intent_order.putExtra(Constants.INDIVIDUAL, ind);
                startActivity(intent_order);
            }
            else{
                intent_order = new Intent(ctx, GroupRecovery.class);
                intent_order.putExtra(Constants.GRUPO, gpo);
                startActivity(intent_order);
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
