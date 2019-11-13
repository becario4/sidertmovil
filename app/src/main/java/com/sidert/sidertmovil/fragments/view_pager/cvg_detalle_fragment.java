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
import com.sidert.sidertmovil.activities.CarteraVencidaGrupal;
import com.sidert.sidertmovil.activities.GroupMembersInfo;
import com.sidert.sidertmovil.utils.Constants;
import com.sidert.sidertmovil.utils.Miscellaneous;


public class cvg_detalle_fragment extends Fragment {

    private Context ctx;

    private CarteraVencidaGrupal parent;

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

    private Button btnLlamarCelTesorera;
    private Button btnLlamarCelPresidenta;
    private Button btnLlamarCelSecretaria;
    private Button btnGroupMembers;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cvg_detalle, container, false);
        ctx                     = getContext();

        parent                   = (CarteraVencidaGrupal) getActivity();

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
        btnGroupMembers          = view.findViewById(R.id.btnGroupMembers);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        tvExternalID.setText(parent.ficha_cvg.getId());
        etNumeroPrestamo.setText(parent.ficha_cvg.getPrestamo().getNumeroDePrestamo());
        etFechaCreditoOtorgado.setText(parent.ficha_cvg.getPrestamo().getFechaDelCreditoOtorgado());
        etClaveGrupo.setText(parent.ficha_cvg.getGrupo().getClaveGrupo());
        etNombreGrupo.setText(parent.ficha_cvg.getGrupo().getNombreGrupo());
        etTotalIntegrantes.setText(String.valueOf(parent.ficha_cvg.getGrupo().getIntegrantesDelGrupo().size()));
        etMontoPrestamoOtorgado.setText(Miscellaneous.moneyFormat(String.valueOf(parent.ficha_cvg.getPrestamo().getMontoPrestamo())));
        etMontoTotalPrestamo.setText(Miscellaneous.moneyFormat(String.valueOf(parent.ficha_cvg.getPrestamo().getMontoTotalPrestamo())));
        etNumeroAmortizacion.setText(String.valueOf(parent.ficha_cvg.getPrestamo().getNumeroAmortizacion()));
        etPagoRequerido.setText(Miscellaneous.moneyFormat(String.valueOf(parent.ficha_cvg.getPrestamo().getPagoSemanal())));
        etMontoAmortizacion.setText(Miscellaneous.moneyFormat(String.valueOf(parent.ficha_cvg.getPrestamo().getMontoAmortizacion())));
        etFechaPagoEstablecida.setText(parent.ficha_cvg.getPrestamo().getFechaPagoEstablecida());
        etNombreTesorera.setText(parent.ficha_cvg.getTesorera().getNombre());
        etNombrePresidenta.setText(parent.ficha_cvg.getPresidenta().getNombrePresidenta());
        etNombreSecretaria.setText(parent.ficha_cvg.getSecretaria().getNombreSecretaria());
        btnLlamarCelTesorera.setOnClickListener(btnLlamarCelTesorera_onClick);
        btnLlamarCelPresidenta.setOnClickListener(btnLlamarCelPresidenta_onClick);
        btnLlamarCelSecretaria.setOnClickListener(btnLlamarCelSecretaria_onClick);
        btnGroupMembers.setOnClickListener(btnGroupMembers_OnClick);
    }

    private View.OnClickListener btnLlamarCelTesorera_onClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Call(parent.ficha_cvg.getTesorera().getTelefonoCelular());
        }
    };

    private View.OnClickListener btnLlamarCelPresidenta_onClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Call(parent.ficha_cvg.getPresidenta().getTelCelularPresidenta());
        }
    };

    private View.OnClickListener btnLlamarCelSecretaria_onClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Call(parent.ficha_cvg.getSecretaria().getTelCelularSecretaria());
        }
    };
    
    private View.OnClickListener btnGroupMembers_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent members_info = new Intent(ctx, GroupMembersInfo.class);
            startActivity(members_info);
        }
    };

    private void Call(String strPhone){
        if (ContextCompat.checkSelfPermission(ctx, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(parent, new String[]{Manifest.permission.CALL_PHONE, Manifest.permission.CALL_PHONE}, Constants.REQUEST_CODE_LLAMADA);
        } else {
            if (!strPhone.isEmpty()) {
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + strPhone));
                startActivity(intent);
            }
            else {
                Toast.makeText(ctx, "No cuenta con número de telefono", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
