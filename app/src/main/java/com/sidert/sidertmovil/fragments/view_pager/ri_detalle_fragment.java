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
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.activities.FormatoRecibos;
import com.sidert.sidertmovil.activities.RecuperacionIndividual;
import com.sidert.sidertmovil.database.DBhelper;
import com.sidert.sidertmovil.models.MAval;
import com.sidert.sidertmovil.models.MFormatoRecibo;
import com.sidert.sidertmovil.models.MPrestamoRes;
import com.sidert.sidertmovil.utils.Constants;
import com.sidert.sidertmovil.utils.Miscellaneous;
import com.sidert.sidertmovil.utils.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.sidert.sidertmovil.utils.Constants.TBL_AMORTIZACIONES_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_AVAL_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_CARTERA_IND_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_PRESTAMOS_IND_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_RECIBOS;
import static com.sidert.sidertmovil.utils.Constants.TICKET_CC;


public class ri_detalle_fragment extends Fragment {

    private Button btnCallHome;
    private Button btnCallCell;
    private Button btnCallEndorsement;
    private Button btnCC;
    private Button btnAGF;

    private RecuperacionIndividual parent;
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
    private String telCasa = "";
    private String telCelular = "";

    private String idPrestamo = "";

    private SessionManager session;


    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ri_detalle, container, false);
        ctx                      = getContext();

        dBhelper                 = new DBhelper(ctx);
        db                       = dBhelper.getWritableDatabase();

        session                  = new SessionManager(ctx);

        mPrestamo                = new MPrestamoRes();
        mAval                    = new MAval();

        parent                   = (RecuperacionIndividual) getActivity();
        assert parent != null;
        parent.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

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
        btnCC                   = view.findViewById(R.id.btnCC);
        btnAGF                  = view.findViewById(R.id.btnAGF);

        btnCC.setVisibility(View.GONE);
        btnAGF.setVisibility(View.GONE);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Cursor row = dBhelper.customSelect(TBL_PRESTAMOS_IND_T + " AS p", "p.*, a.*, c.nombre, c.clave", " LEFT JOIN "+TBL_AVAL_T+" AS a ON p.id_prestamo = a.id_prestamo INNER JOIN "+TBL_CARTERA_IND_T + " AS c ON p.id_cliente = c.id_cartera WHERE p.id_prestamo = ?", "", new String[]{parent.id_prestamo});

        if (row.getCount() > 0){
            row.moveToFirst();

            idPrestamo = row.getString(1);
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
        row.close();

        btnCallHome.setOnClickListener(btnCallHome_onClick);
        btnCallCell.setOnClickListener(btnCallCell_onClick);
        btnCallEndorsement.setOnClickListener(btnCallEndorsement_onClick);
        btnCC.setOnClickListener(btnCC_onClick);
        btnAGF.setOnClickListener(btnAGF_onClick);
    }

    private View.OnClickListener btnCallHome_onClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Call(parent.telCliente);
        }
    };

    private View.OnClickListener btnCallCell_onClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Call(parent.telCelular);
        }
    };

    private View.OnClickListener btnCallEndorsement_onClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Call(tel_aval);
        }
    };

    private View.OnClickListener btnCC_onClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Cursor row;
            String sql;

            boolean imprimir = true;
            String nombre = "";
            String clave = "";
            String curp = "";
            sql = "SELECT nombre, cc, clave, curp FROM " + TBL_CARTERA_IND_T + " AS c INNER JOIN " + TBL_PRESTAMOS_IND_T + " AS p ON p.id_cliente = c.id_cartera WHERE p.id_prestamo = ?";
            row = db.rawQuery(sql, new String[]{idPrestamo});
            if (row.getCount() > 0 ){
                row.moveToFirst();
                if(row.getInt(1) == 1)
                    imprimir = false;

                nombre = row.getString(0);
                clave = row.getString(2);
                curp = row.getString(3);


            }

            if (imprimir) {
                MFormatoRecibo ticket = new MFormatoRecibo();
                ticket.setIdPrestamo(idPrestamo);
                ticket.setAsesorId(session.getUser().get(0));
                ticket.setTipoRecibo("CC");
                ticket.setMonto("17.5");
                ticket.setNombreCliente(nombre);
                ticket.setClave(clave);
                ticket.setCurp(curp);


                row = dBhelper.getRecords(TBL_RECIBOS, " WHERE curp = ? AND prestamo_id = ? AND tipo_recibo = ?", "", new String[]{ticket.getCurp(), ticket.getIdPrestamo(), "CC"});

                switch (row.getCount()) {
                    case 0:
                        ticket.setTipoImpresion(true);
                        ticket.setResImpresion(0);
                        ticket.setFolio("");
                        ticket.setReeimpresion(false);
                        break;
                    case 1:
                        row.moveToFirst();
                        ticket.setTipoImpresion(false);
                        ticket.setResImpresion(1);
                        ticket.setFolio(row.getString(5));
                        ticket.setReeimpresion(false);
                        break;
                    case 2:
                        row.moveToFirst();
                        ticket.setTipoImpresion(true);
                        ticket.setResImpresion(2);
                        ticket.setFolio(row.getString(5));
                        ticket.setReeimpresion(true);
                        break;

                }


                Log.e("ResImpres1", String.valueOf(ticket.getResImpresion()));
                Intent i_formato_recibo = new Intent(parent, FormatoRecibos.class);
                i_formato_recibo.putExtra(TICKET_CC, ticket);
                startActivity(i_formato_recibo);
            }
            else{
                Toast.makeText(ctx, "Ya se imprimió el recibo de Circulo de Crédito", Toast.LENGTH_SHORT).show();
            }
        }
    };

    private View.OnClickListener btnAGF_onClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Cursor row;
            String sql;
            boolean imprimir = true;
            String nombre = "";
            String clave = "";
            String curp = "";
            sql = "SELECT nombre, cc, clave, curp FROM " + TBL_CARTERA_IND_T + " AS c INNER JOIN " + TBL_PRESTAMOS_IND_T + " AS p ON p.id_cliente = c.id_cartera WHERE p.id_prestamo = ?";
            row = db.rawQuery(sql, new String[]{idPrestamo});
            if (row.getCount() > 0 ){
                row.moveToFirst();
                if(row.getInt(1) == 1)
                    imprimir = false;

                nombre = row.getString(0);
                clave = row.getString(2);
                curp = row.getString(3);


            }

            if (imprimir) {
                MFormatoRecibo ticket = new MFormatoRecibo();
                ticket.setIdPrestamo(idPrestamo);
                ticket.setAsesorId(session.getUser().get(0));
                ticket.setTipoRecibo("AGF");
                ticket.setNombreCliente(nombre);
                ticket.setClave(clave);
                ticket.setCurp(curp);

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

                Log.e("idPrestamo", idPrestamo);
                sql = "SELECT MIN(DATE(SUBSTR(fecha,7)||'-'||SUBSTR(fecha,4,2)||'-'||SUBSTR(fecha,1,2))) as fIni, MAX(DATE(SUBSTR(fecha,7)||'-'||SUBSTR(fecha,4,2)||'-'||SUBSTR(fecha,1,2))) AS fFin FROM " + TBL_AMORTIZACIONES_T + " WHERE id_prestamo = ? GROUP BY id_prestamo";
                row = db.rawQuery(sql, new String[]{idPrestamo});

                Log.e("rowMonto", String.valueOf(row.getCount()));
                if (row.getCount() > 0) {
                    row.moveToFirst();
                    String fechaUno = row.getString(0);
                    String fechaDos = row.getString(1);

                    Log.e("Fecha1", fechaUno);
                    Log.e("Fecha2", fechaDos);

                    try {
                        Date fechaInicial = dateFormat.parse(fechaUno);
                        Date fechaFinal = dateFormat.parse(fechaDos);
                        int dias = (int) ((fechaFinal.getTime() - fechaInicial.getTime()) / 86400000);

                        int meses = Math.round(dias / 30);
                        double seguroAGF = 15;
                        for (int i = 0; i < session.getSucursales().length(); i++) {
                            try {
                                JSONObject item = session.getSucursales().getJSONObject(i);
                                if (item.getString("nombre").equals("2.2 MECAPALAPA") || item.getString("nombre").equals("2.3 LA MESA")) {
                                    seguroAGF = 12.5;
                                    break;
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        ticket.setMonto(String.valueOf((meses * seguroAGF)));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                }
                row.close();

                row = dBhelper.getRecords(TBL_RECIBOS, " WHERE curp = ? AND prestamo_id = ? AND tipo_recibo = ?", "", new String[]{ticket.getCurp(), ticket.getIdPrestamo(), "AGF"});
                //MFormatoRecibo mRecibo = new MFormatoRecibo();
                Log.e("CountRecubo", String.valueOf(row.getCount()));
                switch (row.getCount()) {
                    case 0:
                        ticket.setTipoImpresion(true);
                        ticket.setResImpresion(0);
                        ticket.setFolio("");
                        ticket.setReeimpresion(false);
                        break;
                    case 1:
                        row.moveToFirst();
                        ticket.setTipoImpresion(false);
                        ticket.setResImpresion(1);
                        ticket.setFolio(row.getString(5));
                        ticket.setReeimpresion(false);
                        break;
                    case 2:
                        row.moveToFirst();
                        ticket.setTipoImpresion(true);
                        ticket.setResImpresion(2);
                        ticket.setFolio(row.getString(5));
                        ticket.setReeimpresion(true);
                        break;

                }


                Log.e("ResImpres1", String.valueOf(ticket.getResImpresion()));
                Intent i_formato_recibo = new Intent(parent, FormatoRecibos.class);
                i_formato_recibo.putExtra(TICKET_CC, ticket);
                startActivity(i_formato_recibo);
            }
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
