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
import com.sidert.sidertmovil.activities.GroupRecovery;
import com.sidert.sidertmovil.utils.Miscellaneous;


public class group_recovery_fragment extends Fragment {

    private Button btnLlamarCelTesorera;
    private Button btnLlamarCelPresidenta;
    private Button btnLlamarCelSecretaria;

    private final int REQUEST_PERMISSION_CALL = 12312;

    private GroupRecovery parent;
    private Context ctx;

    private TextView tvExternalID;

    private EditText etNumeroPrestamo;
    private EditText etFechaCreditoOtorgado;
    private EditText etClaveGrupo;
    private EditText etNombreGrupo;
    private EditText etTotalIntegrantes;
    private EditText etMontoPrestamoOtorgado;
    private EditText etMontoTotalPrestamo;
    private EditText etNumeroAmortizacion;
    private EditText etPagoRequerido;
    private EditText etMontoAmortizacion;
    private EditText etFechaPagoEstablecida;
    private EditText etNombreTesorera;
    private EditText etNombrePresidenta;
    private EditText etNombreSecretaria;


    @Nullable
    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_group_recovery, container, false);

        ctx                      = getContext();
        parent                   = (GroupRecovery) getActivity();
        tvExternalID             = view.findViewById(R.id.tvExternalID);
        etNumeroPrestamo         = view.findViewById(R.id.etNumeroPrestamo);
        etFechaCreditoOtorgado   = view.findViewById(R.id.etFechaCreditoOtorgado);
        etClaveGrupo             = view.findViewById(R.id.etClaveGrupo);
        etNombreGrupo            = view.findViewById(R.id.etNombreGrupo);
        etTotalIntegrantes       = view.findViewById(R.id.etTotalIntegrantes);
        etMontoPrestamoOtorgado  = view.findViewById(R.id.etMontoPrestamoOtorgado);
        etMontoTotalPrestamo     = view.findViewById(R.id.etMontoTotalPrestamo);
        etNumeroAmortizacion     = view.findViewById(R.id.etNumeroAmortizacion);
        etPagoRequerido          = view.findViewById(R.id.etPagoRequerido);
        etMontoAmortizacion      = view.findViewById(R.id.etMontoAmortizacion);
        etFechaPagoEstablecida   = view.findViewById(R.id.etFechaPagoEstablecida);
        etNombreTesorera         = view.findViewById(R.id.etNombreTesorera);
        etNombrePresidenta       = view.findViewById(R.id.etNombrePresidenta);
        etNombreSecretaria       = view.findViewById(R.id.etNombreSecretaria);

        btnLlamarCelTesorera     = view.findViewById(R.id.btnLlamarCelTesorera);
        btnLlamarCelPresidenta   = view.findViewById(R.id.btnLlamarCelPresidenta);
        btnLlamarCelSecretaria   = view.findViewById(R.id.btnLlamarCelSecretaria);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        tvExternalID.setText(parent.ficha_rg.getId());
        etNumeroPrestamo.setText(parent.ficha_rg.getPrestamo().getNumeroDePrestamo());
        etFechaCreditoOtorgado.setText(parent.ficha_rg.getPrestamo().getFechaDelCreditoOtorgado());
        etClaveGrupo.setText(parent.ficha_rg.getGrupo().getClaveGrupo());
        etNombreGrupo.setText(parent.ficha_rg.getGrupo().getNombreGrupo());
        etTotalIntegrantes.setText(String.valueOf(parent.ficha_rg.getGrupo().getIntegrantesDelGrupo().size()));
        etMontoPrestamoOtorgado.setText(Miscellaneous.moneyFormat(String.valueOf(parent.ficha_rg.getPrestamo().getMontoPrestamo())));
        etMontoTotalPrestamo.setText(Miscellaneous.moneyFormat(String.valueOf(parent.ficha_rg.getPrestamo().getMontoTotalPrestamo())));
        etNumeroAmortizacion.setText(String.valueOf(parent.ficha_rg.getPrestamo().getNumeroAmortizacion()));
        etPagoRequerido.setText(Miscellaneous.moneyFormat(String.valueOf(parent.ficha_rg.getPrestamo().getPagoSemanal())));
        etMontoAmortizacion.setText(Miscellaneous.moneyFormat(String.valueOf(parent.ficha_rg.getPrestamo().getMontoAmortizacion())));
        etFechaPagoEstablecida.setText(parent.ficha_rg.getPrestamo().getFechaPagoEstablecida());
        etNombreTesorera.setText(parent.ficha_rg.getTesorera().getNombre());
        etNombrePresidenta.setText(parent.ficha_rg.getPresidenta().getNombrePresidenta());
        etNombreSecretaria.setText(parent.ficha_rg.getSecretaria().getNombreSecretaria());
        btnLlamarCelTesorera.setOnClickListener(btnLlamarCelTesorera_onClick);
        btnLlamarCelPresidenta.setOnClickListener(btnLlamarCelPresidenta_onClick);
        btnLlamarCelSecretaria.setOnClickListener(btnLlamarCelSecretaria_onClick);
    }

    private View.OnClickListener btnLlamarCelTesorera_onClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (ContextCompat.checkSelfPermission(ctx, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(parent, new String[]{Manifest.permission.CALL_PHONE, Manifest.permission.CALL_PHONE}, REQUEST_PERMISSION_CALL);
            } else {
                if (!parent.ficha_rg.getTesorera().getTelefonoCelular().isEmpty()){
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse("tel:" + parent.ficha_rg.getTesorera().getTelefonoCelular()));
                    startActivity(intent);
                }
                else {
                    Toast.makeText(ctx, "No cuenta con telefono de domicilio", Toast.LENGTH_SHORT).show();
                }

            }
        }
    };

    private View.OnClickListener btnLlamarCelPresidenta_onClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (ContextCompat.checkSelfPermission(ctx, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(parent, new String[]{Manifest.permission.CALL_PHONE, Manifest.permission.CALL_PHONE}, REQUEST_PERMISSION_CALL);
            } else {
                if (!parent.ficha_rg.getPresidenta().getTelCelularPresidenta().isEmpty()) {
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse("tel:" + parent.ficha_rg.getPresidenta().getTelCelularPresidenta()));
                    startActivity(intent);
                }
                else {
                    Toast.makeText(ctx, "No cuenta con telefono celular", Toast.LENGTH_SHORT).show();
                }
            }
        }
    };

    private View.OnClickListener btnLlamarCelSecretaria_onClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (ContextCompat.checkSelfPermission(ctx, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(parent, new String[]{Manifest.permission.CALL_PHONE, Manifest.permission.CALL_PHONE}, REQUEST_PERMISSION_CALL);
            } else {
                if (!parent.ficha_rg.getSecretaria().getTelCelularSecretaria().isEmpty()) {
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse("tel:" + parent.ficha_rg.getSecretaria().getTelCelularSecretaria()));
                    startActivity(intent);
                }
                else {
                    Toast.makeText(ctx, "No cuenta con número de telefono", Toast.LENGTH_SHORT).show();
                }
            }
        }
    };
}
