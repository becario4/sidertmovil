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
import com.sidert.sidertmovil.activities.RecuperacionIndividual;
import com.sidert.sidertmovil.utils.Constants;
import com.sidert.sidertmovil.utils.Miscellaneous;


public class ri_detalle_fragment extends Fragment {

    private Button btnCallHome;
    private Button btnCallCell;
    private Button btnCallEndorsement;

    private RecuperacionIndividual parent;
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
    public View onCreateView(@Nullable LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ri_detalle, container, false);
        ctx                      = getContext();
        parent                   = (RecuperacionIndividual) getActivity();
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
        tvExternalID.setText(parent.ficha_ri.getId());
        etNumeroPrestamo.setText(parent.ficha_ri.getPrestamo().getNumeroDePrestamo());
        etFechaCreditoOtorgado.setText(parent.ficha_ri.getPrestamo().getFechaDelCreditoOtorgado());
        etNumeroCliente.setText(parent.ficha_ri.getCliente().getNumeroCliente());
        etNombreCliente.setText(parent.ficha_ri.getCliente().getNombre());
        etMontoPrestamoOtorgado.setText(Miscellaneous.moneyFormat(String.valueOf(parent.ficha_ri.getPrestamo().getMontoPrestamo())));
        etMontoTotalPrestamo.setText(Miscellaneous.moneyFormat(String.valueOf(parent.ficha_ri.getPrestamo().getMontoTotalPrestamo())));
        etNumeroAmortizacion.setText(String.valueOf(parent.ficha_ri.getPrestamo().getNumeroAmortizacion()));
        etPagoRequerido.setText(Miscellaneous.moneyFormat(String.valueOf(parent.ficha_ri.getPrestamo().getPagoSemanal())));
        etMontoAmortizacion.setText(Miscellaneous.moneyFormat(String.valueOf(parent.ficha_ri.getPrestamo().getMontoAmortizacion())));
        etFechaPagoEstablecida.setText(parent.ficha_ri.getPrestamo().getFechaPagoEstablecida());
        etNombreAval.setText(parent.ficha_ri.getAval().getNombreCompletoAval());
        etParentescoAval.setText(parent.ficha_ri.getAval().getParentescoAval());
        btnCallHome.setOnClickListener(btnCallHome_onClick);
        btnCallCell.setOnClickListener(btnCallCell_onClick);
        btnCallEndorsement.setOnClickListener(btnCallEndorsement_onClick);
        //getPost();
    }

    private View.OnClickListener btnCallHome_onClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (ContextCompat.checkSelfPermission(ctx, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(parent, new String[]{Manifest.permission.CALL_PHONE, Manifest.permission.CALL_PHONE}, Constants.REQUEST_CODE_LLAMADA);
            } else {
                if (!parent.ficha_ri.getCliente().getTelDomicilio().isEmpty()){
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse("tel:" + parent.ficha_ri.getCliente().getTelDomicilio()));
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
                ActivityCompat.requestPermissions(parent, new String[]{Manifest.permission.CALL_PHONE, Manifest.permission.CALL_PHONE}, Constants.REQUEST_CODE_LLAMADA);
            } else {
                if (!parent.ficha_ri.getCliente().getTelCelular().isEmpty()) {
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse("tel:" + parent.ficha_ri.getCliente().getTelCelular()));
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
                ActivityCompat.requestPermissions(parent, new String[]{Manifest.permission.CALL_PHONE, Manifest.permission.CALL_PHONE}, Constants.REQUEST_CODE_LLAMADA);
            } else {
                if (!parent.ficha_ri.getAval().getTelefonoAval().isEmpty()) {
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse("tel:" + parent.ficha_ri.getAval().getTelefonoAval()));
                    startActivity(intent);
                }
                else {
                    Toast.makeText(ctx, "No cuenta con número de telefono", Toast.LENGTH_SHORT).show();
                }
            }
        }
    };


}
