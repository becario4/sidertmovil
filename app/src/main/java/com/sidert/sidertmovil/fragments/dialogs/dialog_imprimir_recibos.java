package com.sidert.sidertmovil.fragments.dialogs;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.activities.FormatoRecibos;
import com.sidert.sidertmovil.activities.Integrantes;
import com.sidert.sidertmovil.database.DBhelper;
import com.sidert.sidertmovil.models.MFormatoRecibo;
import com.sidert.sidertmovil.utils.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.sidert.sidertmovil.utils.Constants.ID_INTEGRANTE;
import static com.sidert.sidertmovil.utils.Constants.ID_PRESTAMO;
import static com.sidert.sidertmovil.utils.Constants.TBL_AMORTIZACIONES_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_CARTERA_GPO_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_MIEMBROS_GPO_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_PRESTAMOS_GPO_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_RECIBOS;
import static com.sidert.sidertmovil.utils.Constants.TICKET_CC;


public class dialog_imprimir_recibos extends DialogFragment {

    private Context ctx;

    private Button btnCC;
    private Button btnAGF;

    private ImageView ivClose;

    private SessionManager session;

    private String idIntegrante = "";
    private String idPrestamo = "";

    private DBhelper dBhelper;
    private SQLiteDatabase db;
    private Integrantes parent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.popup_imprimir_recibos, container, false);

        ctx = getContext();

        parent = (Integrantes)getActivity();

        dBhelper = new DBhelper(ctx);
        db = dBhelper.getWritableDatabase();

        session = new SessionManager(ctx);

        btnCC = v.findViewById(R.id.btnCC);
        btnAGF = v.findViewById(R.id.btnAGF);

        ivClose = v.findViewById(R.id.ivClose);

        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        idIntegrante = getArguments().getString(ID_INTEGRANTE);
        idPrestamo   = getArguments().getString(ID_PRESTAMO);

        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        btnCC.setOnClickListener(btnCC_OnClick);
        btnAGF.setOnClickListener(btnAGF_OnClick);
        ivClose.setOnClickListener(ivClose_OnClick);
    }

    private View.OnClickListener btnAGF_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Cursor row;
            String sql;
            boolean imprimir = true;

            sql = "SELECT agf FROM " + TBL_CARTERA_GPO_T + " AS c INNER JOIN " + TBL_PRESTAMOS_GPO_T + " AS p ON p.id_grupo = c.id_cartera WHERE p.id_prestamo = ?";
            row = db.rawQuery(sql, new String[]{idPrestamo});
            if (row.getCount() > 0 ){
                row.moveToFirst();
                String[] integrantes = row.getString(0).replace("{", "").replace("}", "").split(",");
                if (integrantes.length > 0){
                    for (int i = 0; i < integrantes.length; i++){
                        if (integrantes[i].equals(idIntegrante)){
                            imprimir = false;
                            break;
                        }
                    }
                }
            }

            if (imprimir) {
                MFormatoRecibo ticket = new MFormatoRecibo();
                ticket.setIdPrestamo(idPrestamo);
                ticket.setAsesorId(session.getUser().get(0));
                ticket.setTipoRecibo("AGF");

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");


                sql = "SELECT nombre, clave, curp FROM " + TBL_MIEMBROS_GPO_T + " WHERE id_prestamo = ? AND id_integrante = ?";
                row = db.rawQuery(sql, new String[]{idPrestamo, idIntegrante});

                if (row.getCount() > 0) {
                    row.moveToFirst();
                    ticket.setNombreCliente(row.getString(0));
                    ticket.setClave(row.getString(1));
                    ticket.setCurp(row.getString(2));

                }
                row.close();
//303941
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

    private View.OnClickListener btnCC_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Cursor row;
            String sql;

            boolean imprimir = true;
            sql = "SELECT cc FROM " + TBL_CARTERA_GPO_T + " AS c INNER JOIN " + TBL_PRESTAMOS_GPO_T + " AS p ON p.id_grupo = c.id_cartera WHERE p.id_prestamo = ?";
            row = db.rawQuery(sql, new String[]{idPrestamo});
            if (row.getCount() > 0 ){
                row.moveToFirst();
                String[] integrantes = row.getString(0).replace("{", "").replace("}", "").split(",");
                if (integrantes.length > 0){
                    for (int i = 0; i < integrantes.length; i++){
                        if (integrantes[i].equals(idIntegrante)){
                            imprimir = false;
                            break;
                        }
                    }
                }
            }


            if (imprimir) {
                MFormatoRecibo ticket = new MFormatoRecibo();
                ticket.setIdPrestamo(idPrestamo);
                ticket.setAsesorId(session.getUser().get(0));
                ticket.setTipoRecibo("CC");
                ticket.setMonto("17.5");


                sql = "SELECT nombre, clave, curp FROM " + TBL_MIEMBROS_GPO_T + " WHERE id_prestamo = ? AND id_integrante = ?";
                row = db.rawQuery(sql, new String[]{idPrestamo, idIntegrante});

                if (row.getCount() > 0) {
                    row.moveToFirst();
                    ticket.setNombreCliente(row.getString(0));
                    ticket.setClave(row.getString(1));
                    ticket.setCurp(row.getString(2));

                }
                row.close();

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

    private View.OnClickListener ivClose_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            getDialog().dismiss();
        }
    };
}
