package com.sidert.sidertmovil.fragments.dialogs;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.models.MRespuestaCC;
import com.sidert.sidertmovil.utils.Miscellaneous;
import com.sidert.sidertmovil.utils.NetworkStatus;
import com.sidert.sidertmovil.utils.SessionManager;

import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class dialog_info_consulta_cc extends DialogFragment {

    private Context ctx;
    private TextView tvSaldoActual;
    private TextView tvSaldoVencido;
    private TextView tvMontoPagar;
    private TextView tvPeorPago;
    private TextView tvCreditosAbiertos;
    private TextView tvCreditosCerrados;
    private TextView tvMontoSolicitado;
    protected EditText etMontoAutorizado;


    private Button btnDescargarPdf;

    private MRespuestaCC respuesta;

    private SessionManager session;

    private String filePdf = "";

    DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
    DecimalFormat df = new DecimalFormat("##,###.##", symbols);
    DecimalFormat dfnd = new DecimalFormat("#,###", symbols);

    boolean hasFractionalPart = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.popup_info_consulta_cc, container, false);

        ctx = getContext();

        session = new SessionManager(ctx);

        tvSaldoActual       = v.findViewById(R.id.tvSaldoActual);
        tvSaldoVencido      = v.findViewById(R.id.tvSaldoVencido);
        tvMontoPagar        = v.findViewById(R.id.tvMontoPagar);
        tvPeorPago          = v.findViewById(R.id.tvPeorPago);
        tvCreditosAbiertos  = v.findViewById(R.id.tvCreditosAbiertos);
        tvCreditosCerrados  = v.findViewById(R.id.tvCreditosCerrados);
        tvMontoSolicitado   = v.findViewById(R.id.tvMontoSoli);
        etMontoAutorizado   = v.findViewById(R.id.etMontoAutorizado);

        btnDescargarPdf     = v.findViewById(R.id.btnDescargarPdf);

        filePdf = getArguments().getString("file_pdf");

        respuesta = new Gson().fromJson(getArguments().getString("info"), MRespuestaCC.class);

        int credAbierto = 0;
        int credCerrado = 0;
        float saldVencido = 0;
        float saldActual = 0;
        Double peorPago = 0.0;
        Double montoPagar = 0.0;


        int i = 1;
        for (MRespuestaCC.Credito credito : respuesta.getCreditos()){
            if (!Miscellaneous.validStr(credito.getFechaCierreCuenta()).isEmpty())
                credCerrado += 1;
            else
                credAbierto += 1;

            saldVencido += credito.getSaldoVencido();

            saldActual +=  credito.getSaldoActual();

            montoPagar += Miscellaneous.validDbl(credito.getMontoPagar());

            if (credito.getPeorAtraso() != null) {
                if (Miscellaneous.validDbl(credito.getPeorAtraso()) > peorPago) {
                    peorPago = Miscellaneous.validDbl(credito.getPeorAtraso());
                }
            }
            i++;
        }

        tvSaldoActual.setText(Miscellaneous.moneyFormat(String.valueOf(saldActual)).replace("$", ""));
        tvSaldoVencido.setText(Miscellaneous.moneyFormat(String.valueOf(saldVencido)).replace("$", ""));
        tvMontoPagar.setText(Miscellaneous.moneyFormat(String.valueOf(montoPagar)).replace("$", ""));
        tvCreditosAbiertos.setText(String.valueOf(credAbierto));
        tvCreditosCerrados.setText(String.valueOf(credCerrado));
        tvPeorPago.setText(Miscellaneous.moneyFormat(String.valueOf(peorPago)).replace("$", ""));
        tvMontoSolicitado.setText(Miscellaneous.moneyFormat(getArguments().getString("monto_solicitado")).replace("$", ""));

        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        setCancelable(true);

        if (!Miscellaneous.validStr(filePdf).isEmpty())
            btnDescargarPdf.setOnClickListener(btnDescargarPdf_OnClick);
        else{
            btnDescargarPdf.setVisibility(View.GONE);
        }

        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        etMontoAutorizado.addTextChangedListener(new TextWatcher() {
            private final String PATTERN_MONTO_CREDITO  = "[1-9][0-9][0-9][0][0][0]|[1-9][0-9][0][0][0]|[1-9][0][0][0]";
            private Pattern pattern;
            private Matcher matcher;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().contains(String.valueOf(df.getDecimalFormatSymbols().getDecimalSeparator())))
                {
                    hasFractionalPart = true;
                } else {
                    hasFractionalPart = false;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                etMontoAutorizado.removeTextChangedListener(this);

                try {
                    int inilen, endlen;
                    inilen = Miscellaneous.GetStr(etMontoAutorizado).length();
                    String v = s.toString().replace(String.valueOf(df.getDecimalFormatSymbols().getGroupingSeparator()), "");
                    Number n = df.parse(v);
                    int cp = etMontoAutorizado.getSelectionStart();
                    if (hasFractionalPart) {
                        etMontoAutorizado.setText(df.format(n));
                    } else {
                        etMontoAutorizado.setText(dfnd.format(n));
                    }
                    endlen = Miscellaneous.GetStr(etMontoAutorizado).length();
                    int sel = (cp + (endlen - inilen));
                    if (sel > 0 && sel <= Miscellaneous.GetStr(etMontoAutorizado).length()) {
                        etMontoAutorizado.setSelection(sel);
                    } else {
                        // place cursor at the end?
                        etMontoAutorizado.setSelection(Miscellaneous.GetStr(etMontoAutorizado).length() - 1);
                    }
                } catch (NumberFormatException nfe) {
                    // do nothing?
                } catch (ParseException e) {
                    // do nothing?
                }

                if (s.length()> 0){
                    pattern = Pattern.compile(PATTERN_MONTO_CREDITO);
                    matcher = pattern.matcher(s.toString().replace(",",""));
                    if(!matcher.matches()) {
                        etMontoAutorizado.setError("La cantidad no corresponde a un monto de crédito válido");
                    }
                }

                etMontoAutorizado.addTextChangedListener(this);
            }
        });
    }

    private View.OnClickListener btnDescargarPdf_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Log.e("URL", session.getDominio().get(0)+session.getDominio().get(1)+"/api/"+filePdf.replace("\\","/"));
            if (NetworkStatus.haveWifi(ctx)){
                try {


                    URL url = new URL(session.getDominio().get(0)+session.getDominio().get(1)+"/api/"+filePdf.replace("\\","/"));

                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url.toString())));

                } catch (IOException e) {
                    Log.d("PdfManager", "Error: " + e);
                }
                Log.v("PdfManager", "Check: ");

            }
            else{
                Toast.makeText(ctx, "Solo con WIFI podrá descargar el PDF", Toast.LENGTH_SHORT).show();
            }
        }
    };
}
