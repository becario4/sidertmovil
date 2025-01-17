package com.sidert.sidertmovil.fragments.view_pager;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
/*import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;*/
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.activities.VencidaIndividual;
import com.sidert.sidertmovil.database.DBhelper;
import com.sidert.sidertmovil.models.MAval;
import com.sidert.sidertmovil.models.MPrestamoRes;
import com.sidert.sidertmovil.utils.Constants;
import com.sidert.sidertmovil.utils.Miscellaneous;

import static com.sidert.sidertmovil.utils.Constants.TBL_AVAL;
import static com.sidert.sidertmovil.utils.Constants.TBL_AVAL_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_CARTERA_IND_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_PRESTAMOS_IND_T;


public class cvi_detalle_fragment extends Fragment {

    private Button btnCallHome;
    private Button btnCallCell;
    private Button btnCallEndorsement;

    private VencidaIndividual parent;
    private Context ctx;

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
    private EditText etDireccionAval;
    private EditText etParentescoAval;

    private DBhelper dBhelper;
    private SQLiteDatabase db;
    private MPrestamoRes mPrestamo;
    private MAval mAval;

    private String tel_aval = "";
    private String tel_cliente = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cvi_detalle, container, false);
        ctx                      = getContext();

        dBhelper                 = DBhelper.getInstance(ctx);
        db                       = dBhelper.getWritableDatabase();

        mPrestamo                = new MPrestamoRes();
        mAval                    = new MAval();

        parent                   = (VencidaIndividual) getActivity();
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
        etDireccionAval          = view.findViewById(R.id.etDireccionAval);
        etParentescoAval         = view.findViewById(R.id.etParentescoAval);

        btnCallHome             = view.findViewById(R.id.btnCallHome);
        btnCallCell             = view.findViewById(R.id.btnCallCell);
        btnCallEndorsement      = view.findViewById(R.id.btnCallEndorsement);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Cursor row = dBhelper.customSelect(TBL_PRESTAMOS_IND_T + " AS p", "p.*, a.*, c.nombre, c.clave", " LEFT JOIN "+TBL_AVAL_T+" AS a ON p.id_prestamo = a.id_prestamo INNER JOIN "+TBL_CARTERA_IND_T + " AS c ON p.id_cliente = c.id_cartera WHERE p.id_prestamo = ?", "", new String[]{parent.id_prestamo});

        if (row.getCount() > 0){
            row.moveToFirst();

            Log.e("Detalle", row.getString(0));
            etNumeroPrestamo.setText(row.getString(3));
            etFechaCreditoOtorgado.setText(row.getString(5));
            etNumeroCliente.setText(row.getString(26));
            etNombreCliente.setText(row.getString(25));
            etMontoPrestamoOtorgado.setText(Miscellaneous.moneyFormat(row.getString(6)));
            etMontoTotalPrestamo.setText(Miscellaneous.moneyFormat(String.valueOf(row.getString(7))));
            etNumeroAmortizacion.setText(row.getString(10));
            etPagoRequerido.setText(Miscellaneous.moneyFormat(String.valueOf(row.getString(9))));
            etMontoAmortizacion.setText(Miscellaneous.moneyFormat(String.valueOf(row.getString(8))));
            etFechaPagoEstablecida.setText(row.getString(11));
            etNombreAval.setText(row.getString(19));
            etDireccionAval.setText(row.getString(21));
            etParentescoAval.setText(row.getString(20));

            tel_aval = row.getString(22);

        }

        btnCallHome.setOnClickListener(btnCallHome_onClick);
        btnCallCell.setOnClickListener(btnCallCell_onClick);
        btnCallEndorsement.setOnClickListener(btnCallEndorsement_onClick);
    }


    private View.OnClickListener btnCallHome_onClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (ContextCompat.checkSelfPermission(ctx, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(parent, new String[]{Manifest.permission.CALL_PHONE, Manifest.permission.CALL_PHONE}, Constants.REQUEST_CODE_LLAMADA);
            } else {
                if (!parent.telCliente.isEmpty()){
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse("tel:" + parent.telCliente));
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
                if (!parent.telCelular.isEmpty()) {
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse("tel:" + parent.telCelular));
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
                /*if (!parent.ficha_ri.getAval().getTelefonoAval().isEmpty()) {
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse("tel:" + parent.ficha_ri.getAval().getTelefonoAval()));
                    startActivity(intent);
                }
                else {
                    Toast.makeText(ctx, "No cuenta con número de telefono", Toast.LENGTH_SHORT).show();
                }*/
            }
        }
    };
}
