package com.sidert.sidertmovil.fragments.view_pager;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.activities.CobranzaIndividual;
import com.sidert.sidertmovil.utils.Constants;
import com.sidert.sidertmovil.utils.Miscellaneous;


public class ci_detalle_fragment extends Fragment {

    private Button btnCallHome;
    private Button btnCallCell;
    private Button btnCallEndorsement;

    private CobranzaIndividual parent;
    private Context ctx;

    private TextView tvExternalID;

    private EditText etNumeroPrestamo;
    private EditText etFechaCreditoOtorgado;
    private EditText etNumeroCliente;
    private EditText etNombreCliente;
    private EditText etMontoPrestamoOtorgado;
    private EditText etMontoTotalPrestamo;
    private EditText etNumeroAmortizacion;
    private EditText etPagoRequerido;
    private EditText etMontoAmortizacion;
    private EditText etFechaPagoEstablecida;
    private EditText etNombreAval;
    private EditText etParentescoAval;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ci_detalle, container, false);
        ctx                      = getContext();
        parent                   = (CobranzaIndividual) getActivity();
        tvExternalID             = view.findViewById(R.id.tvExternalID);
        etNumeroPrestamo         = view.findViewById(R.id.etNumeroPrestamo);
        etFechaCreditoOtorgado   = view.findViewById(R.id.etFechaCreditoOtorgado);
        etNumeroCliente          = view.findViewById(R.id.etNumeroCliente);
        etNombreCliente          = view.findViewById(R.id.etNombreCliente);
        etMontoPrestamoOtorgado  = view.findViewById(R.id.etMontoPrestamoOtorgado);
        etMontoTotalPrestamo     = view.findViewById(R.id.etMontoTotalPrestamo);
        etNumeroAmortizacion     = view.findViewById(R.id.etNumeroAmortizacion);
        etPagoRequerido          = view.findViewById(R.id.etPagoRequerido);
        etMontoAmortizacion      = view.findViewById(R.id.etMontoAmortizacion);
        etFechaPagoEstablecida   = view.findViewById(R.id.etFechaPagoEstablecida);
        etNombreAval             = view.findViewById(R.id.etNombreAval);
        etParentescoAval         = view.findViewById(R.id.etParentescoAval);

        btnCallHome             = view.findViewById(R.id.btnCallHome);
        btnCallCell             = view.findViewById(R.id.btnCallCell);
        btnCallEndorsement      = view.findViewById(R.id.btnCallEndorsement);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        tvExternalID.setText(parent.ficha_ci.getId());
        etNumeroPrestamo.setText(parent.ficha_ci.getPrestamo().getNumeroDePrestamo());
        etFechaCreditoOtorgado.setText(parent.ficha_ci.getPrestamo().getFechaDelCreditoOtorgado());
        etNumeroCliente.setText(parent.ficha_ci.getCliente().getNumeroCliente());
        etNombreCliente.setText(parent.ficha_ci.getCliente().getNombre());
        etMontoPrestamoOtorgado.setText(Miscellaneous.moneyFormat(String.valueOf(parent.ficha_ci.getPrestamo().getMontoPrestamo())));
        etMontoTotalPrestamo.setText(Miscellaneous.moneyFormat(String.valueOf(parent.ficha_ci.getPrestamo().getMontoTotalPrestamo())));
        etNumeroAmortizacion.setText(String.valueOf(parent.ficha_ci.getPrestamo().getNumeroAmortizacion()));
        etPagoRequerido.setText(Miscellaneous.moneyFormat(String.valueOf(parent.ficha_ci.getPrestamo().getPagoSemanal())));
        etMontoAmortizacion.setText(Miscellaneous.moneyFormat(String.valueOf(parent.ficha_ci.getPrestamo().getMontoAmortizacion())));
        etFechaPagoEstablecida.setText(parent.ficha_ci.getPrestamo().getFechaPagoEstablecida());
        etNombreAval.setText(parent.ficha_ci.getAval().getNombreCompletoAval());
        etParentescoAval.setText(parent.ficha_ci.getAval().getParentescoAval());
        btnCallHome.setOnClickListener(btnCallHome_onClick);
        btnCallCell.setOnClickListener(btnCallCell_onClick);
        btnCallEndorsement.setOnClickListener(btnCallEndorsement_onClick);
    }

    private View.OnClickListener btnCallHome_onClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Call(parent.ficha_ci.getCliente().getTelDomicilio());
        }
    };

    private View.OnClickListener btnCallCell_onClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Call(parent.ficha_ci.getCliente().getTelCelular());
        }
    };

    private View.OnClickListener btnCallEndorsement_onClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Call(parent.ficha_ci.getAval().getTelefonoAval());
        }
    };

    private void Call (String strPhoneNumber){
        if (ContextCompat.checkSelfPermission(ctx, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(parent, new String[]{Manifest.permission.CALL_PHONE, Manifest.permission.CALL_PHONE}, Constants.REQUEST_CODE_LLAMADA);
        } else {
            if (!strPhoneNumber.isEmpty()) {
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + strPhoneNumber));
                startActivity(intent);
            }
            else {
                Toast.makeText(ctx, getResources().getString(R.string.no_telefono), Toast.LENGTH_SHORT).show();
            }
        }
    }

}
