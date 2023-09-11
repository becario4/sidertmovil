package com.sidert.sidertmovil.fragments.view_pager;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
/*import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;*/
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.activities.Integrantes;
import com.sidert.sidertmovil.activities.RecuperacionGrupal;
import com.sidert.sidertmovil.database.DBhelper;
import com.sidert.sidertmovil.utils.Constants;
import com.sidert.sidertmovil.utils.Miscellaneous;

import static com.sidert.sidertmovil.utils.Constants.ID_PRESTAMO;
import static com.sidert.sidertmovil.utils.Constants.NOMBRE_GRUPO;
import static com.sidert.sidertmovil.utils.Constants.TBL_CARTERA_GPO;
import static com.sidert.sidertmovil.utils.Constants.TBL_CARTERA_GPO_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_MIEMBROS_GPO;
import static com.sidert.sidertmovil.utils.Constants.TBL_MIEMBROS_GPO_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_PRESTAMOS_GPO;
import static com.sidert.sidertmovil.utils.Constants.TBL_PRESTAMOS_GPO_T;


public class rg_detalle_fragment extends Fragment {

    private Button btnLlamarCelTesorera;
    private Button btnLlamarCelPresidenta;
    private Button btnLlamarCelSecretaria;
    private Button btnIntegrantes;

    private RecuperacionGrupal parent;
    private Context ctx;

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
    private EditText etDireccionTesorera;
    private EditText etDireccionPresidenta;
    private EditText etDireccionSecretaria;

    private DBhelper dBhelper;

    private String nombre_tesorera = "";
    private String nombre_presidenta = "";
    private String nombre_secretaria = "";
    private String tel_tesorera = "";
    private String tel_presidenta = "";
    private String tel_secretaria = "";
    private String direccion_tesorera = "";
    private String direccion_presidenta = "";
    private String direccion_secretaria = "";

    @Nullable
    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rg_detalle, container, false);

        ctx                      = getContext();

        dBhelper                 = DBhelper.getInstance(ctx);

        parent                   = (RecuperacionGrupal) getActivity();
        assert parent != null;
        parent.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

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
        etDireccionTesorera      = view.findViewById(R.id.etDireccionTesorera);
        etDireccionPresidenta    = view.findViewById(R.id.etDireccionPresidente);
        etDireccionSecretaria    = view.findViewById(R.id.etDireccionSecretaria);

        btnLlamarCelTesorera     = view.findViewById(R.id.btnLlamarCelTesorera);
        btnLlamarCelPresidenta   = view.findViewById(R.id.btnLlamarCelPresidenta);
        btnLlamarCelSecretaria   = view.findViewById(R.id.btnLlamarCelSecretaria);
        btnIntegrantes           = view.findViewById(R.id.btnIntegrantes);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Cursor row = dBhelper.customSelect(TBL_PRESTAMOS_GPO_T + " AS p", "p.*, c.nombre, c.clave, (select count(i._id) from " + TBL_MIEMBROS_GPO_T + " AS i WHERE id_prestamo = ?) as total_integrantes", " INNER JOIN "+TBL_CARTERA_GPO_T + " AS c ON p.id_grupo = c.id_cartera WHERE p.id_prestamo = ?", "", new String[]{parent.id_prestamo,parent.id_prestamo});

        if (row.getCount() > 0){
            row.moveToFirst();
            etNumeroPrestamo.setText(row.getString(3));
            etFechaCreditoOtorgado.setText(row.getString(5));
            etClaveGrupo.setText(row.getString(17));
            etNombreGrupo.setText(row.getString(16));
            etMontoPrestamoOtorgado.setText(Miscellaneous.moneyFormat(row.getString(6)));
            etMontoTotalPrestamo.setText(Miscellaneous.moneyFormat(String.valueOf(row.getString(7))));
            etNumeroAmortizacion.setText(row.getString(10));
            etPagoRequerido.setText(Miscellaneous.moneyFormat(String.valueOf(row.getString(9))));
            etMontoAmortizacion.setText(Miscellaneous.moneyFormat(String.valueOf(row.getString(8))));
            etFechaPagoEstablecida.setText(row.getString(11));
            etTotalIntegrantes.setText(row.getString(18));
        }
        row.close();

        row = dBhelper.customSelect(TBL_MIEMBROS_GPO_T, "*", " WHERE tipo_integrante in ('PRESIDENTE', 'TESORERO', 'SECRETARIO') AND id_prestamo = ?", "", new String[]{parent.id_prestamo});

        if (row.getCount() > 0){
            row.moveToFirst();

            for (int i = 0; i < row.getCount(); i++){
                switch (row.getString(9)){
                    case "PRESIDENTE":
                        nombre_presidenta = row.getString(5);
                        tel_presidenta = row.getString(8);
                        direccion_presidenta = row.getString(6);
                        break;
                    case "TESORERO":
                        nombre_tesorera = row.getString(5);
                        tel_tesorera = row.getString(8);
                        direccion_tesorera = row.getString(6);
                        break;
                    case "SECRETARIO":
                        nombre_secretaria = row.getString(5);
                        tel_secretaria = row.getString(8);
                        direccion_secretaria = row.getString(6);
                        break;
                }

                row.moveToNext();
            }
        }
        row.close();

        etNombreTesorera.setText(nombre_tesorera);
        etNombrePresidenta.setText(nombre_presidenta);
        etNombreSecretaria.setText(nombre_secretaria);
        etDireccionTesorera.setText(direccion_tesorera);
        etDireccionPresidenta.setText(direccion_presidenta);
        etDireccionSecretaria.setText(direccion_secretaria);

        btnLlamarCelTesorera.setOnClickListener(btnLlamarCelTesorera_onClick);
        btnLlamarCelPresidenta.setOnClickListener(btnLlamarCelPresidenta_onClick);
        btnLlamarCelSecretaria.setOnClickListener(btnLlamarCelSecretaria_onClick);
        btnIntegrantes.setOnClickListener(btnIntegrantes_OnClick);
    }

    private View.OnClickListener btnLlamarCelTesorera_onClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Call(tel_tesorera);
        }
    };

    private View.OnClickListener btnLlamarCelPresidenta_onClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Call(tel_presidenta);
        }
    };

    private View.OnClickListener btnLlamarCelSecretaria_onClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Call(tel_secretaria);
        }
    };

    private View.OnClickListener btnIntegrantes_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i_integrantes = new Intent(parent, Integrantes.class);
            i_integrantes.putExtra(NOMBRE_GRUPO, etNombreGrupo.getText().toString().toUpperCase());
            i_integrantes.putExtra(ID_PRESTAMO, parent.id_prestamo);
            startActivity(i_integrantes);
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
