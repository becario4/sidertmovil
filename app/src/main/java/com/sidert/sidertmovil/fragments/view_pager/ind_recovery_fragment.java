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
import android.widget.TextView;
import android.widget.Toast;

import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.activities.IndividualRecovery;
import com.sidert.sidertmovil.utils.Miscellaneous;


public class ind_recovery_fragment extends Fragment {

    private Button btnCallHome;
    private Button btnCallCell;
    private Button btnCallEndorsement;

    private final int REQUEST_PERMISSION_CALL = 12312;

    private IndividualRecovery boostrap;
    private Context ctx;

    private TextView etExternalID;
    private TextView etNumeroPrestamo;
    private TextView etFechaCreditoOtorgado;
    private TextView etNumeroCliente;
    private TextView etNombreCliente;
    private TextView etMontoPrestamoOtorgado;
    private TextView etMontoTotalPrestamo;
    private TextView etNumeroAmortizacion;
    private TextView etPagoRequerido;
    private TextView etMontoAmortizacion;
    private TextView etFechaPagoEstablecida;
    private TextView etNombreAval;
    private TextView etParentescoAval;


    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ind_recovery, container, false);
        ctx                      = getContext();
        boostrap                 = (IndividualRecovery) getActivity();
        etExternalID             = view.findViewById(R.id.etExternalID);
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
        etExternalID.setText(boostrap.ficha.getId());
        etNumeroPrestamo.setText(boostrap.ficha.getPrestamo().getNumeroDePrestamo());
        etFechaCreditoOtorgado.setText(boostrap.ficha.getPrestamo().getFechaDelCreditoOtorgado());
        etNumeroCliente.setText(boostrap.ficha.getCliente().getNumeroCliente());
        etNombreCliente.setText(boostrap.ficha.getCliente().getNombre());
        etMontoPrestamoOtorgado.setText(Miscellaneous.moneyFormat(String.valueOf(boostrap.ficha.getPrestamo().getMontoPrestamo())));
        etMontoTotalPrestamo.setText(Miscellaneous.moneyFormat(String.valueOf(boostrap.ficha.getPrestamo().getMontoTotalPrestamo())));
        etNumeroAmortizacion.setText(String.valueOf(boostrap.ficha.getPrestamo().getNumeroAmortizacion()));
        etPagoRequerido.setText(Miscellaneous.moneyFormat(String.valueOf(boostrap.ficha.getPrestamo().getPagoSemanal())));
        etMontoAmortizacion.setText(Miscellaneous.moneyFormat(String.valueOf(boostrap.ficha.getPrestamo().getMontoAmortizacion())));
        etFechaPagoEstablecida.setText(boostrap.ficha.getPrestamo().getFechaPagoEstablecida());
        etNombreAval.setText(boostrap.ficha.getAval().getNombreCompletoAval());
        etParentescoAval.setText(boostrap.ficha.getAval().getParentescoAval());
        btnCallHome.setOnClickListener(btnCallHome_onClick);
        btnCallCell.setOnClickListener(btnCallCell_onClick);
        btnCallEndorsement.setOnClickListener(btnCallEndorsement_onClick);
        //getPost();
    }

    private View.OnClickListener btnCallHome_onClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (ContextCompat.checkSelfPermission(ctx, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(boostrap, new String[]{Manifest.permission.CALL_PHONE, Manifest.permission.CALL_PHONE}, REQUEST_PERMISSION_CALL);
            } else {
                if (!boostrap.ficha.getCliente().getTelDomicilio().isEmpty()){
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse("tel:" + boostrap.ficha.getCliente().getTelDomicilio()));
                    startActivity(intent);
                }
                else {
                    Toast.makeText(ctx, "No cuenta con telefono de domicilio", Toast.LENGTH_SHORT).show();
                }

            }
        }
    };

    private View.OnClickListener btnCallCell_onClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (ContextCompat.checkSelfPermission(ctx, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(boostrap, new String[]{Manifest.permission.CALL_PHONE, Manifest.permission.CALL_PHONE}, REQUEST_PERMISSION_CALL);
            } else {
                if (!boostrap.ficha.getCliente().getTelCelular().isEmpty()) {
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse("tel:" + boostrap.ficha.getCliente().getTelCelular()));
                    startActivity(intent);
                }
                else {
                    Toast.makeText(ctx, "No cuenta con telefono celular", Toast.LENGTH_SHORT).show();
                }
            }
        }
    };

    private View.OnClickListener btnCallEndorsement_onClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (ContextCompat.checkSelfPermission(ctx, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(boostrap, new String[]{Manifest.permission.CALL_PHONE, Manifest.permission.CALL_PHONE}, REQUEST_PERMISSION_CALL);
            } else {
                if (!boostrap.ficha.getAval().getTelefonoAval().isEmpty()) {
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse("tel:" + boostrap.ficha.getAval().getTelefonoAval()));
                    startActivity(intent);
                }
                else {
                    Toast.makeText(ctx, "No cuenta con número de telefono", Toast.LENGTH_SHORT).show();
                }
            }
        }
    };


}
