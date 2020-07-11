package com.sidert.sidertmovil.activities;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.adapters.adapter_clientes_cc;
import com.sidert.sidertmovil.database.DBhelper;
import com.sidert.sidertmovil.models.MFormatoRecibo;
import com.sidert.sidertmovil.models.MTicketCC;

import java.util.ArrayList;

import static com.sidert.sidertmovil.utils.Constants.TBL_RECIBOS;
import static com.sidert.sidertmovil.utils.Constants.TICKET_CC;
import static com.sidert.sidertmovil.utils.Constants.TIPO;

public class CirculoCredito extends AppCompatActivity {

    private Context ctx;

    private RecyclerView rvClientesCC;
    private FloatingActionButton fbAgregarCC;

    private LinearLayout llCC;
    private LinearLayout llAGF;

    private DBhelper dBhelper;
    private SQLiteDatabase db;

    private boolean fabExpanded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circulo_credito);


        ctx = this;

        dBhelper = new DBhelper(ctx);
        db = dBhelper.getWritableDatabase();

        rvClientesCC = findViewById(R.id.rvClienteCC);
        fbAgregarCC  = findViewById(R.id.fbAgregar);

        llCC         = findViewById(R.id.llCC);
        llAGF        = findViewById(R.id.llAGF);

        rvClientesCC.setLayoutManager(new LinearLayoutManager(ctx));
        rvClientesCC.setHasFixedSize(false);

        fbAgregarCC.setOnClickListener(fbAgregarCC_OnClick);

        llCC.setOnClickListener(llCC_OnClick);
        llAGF.setOnClickListener(llAGF_OnClick);

        //init();
    }

    private View.OnClickListener fbAgregarCC_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (fabExpanded){
                closeSubMenusFab();
            } else {
                openSubMenusFab();
            }
        }
    };

    private View.OnClickListener llCC_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent i_generarCurp = new Intent(ctx, GenerarCurp.class);
            i_generarCurp.putExtra(TIPO, "CC");
            startActivity(i_generarCurp);
        }
    };

    private View.OnClickListener llAGF_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent i_generarCurp = new Intent(ctx, GenerarCurp.class);
            i_generarCurp.putExtra(TIPO, "AGF");
            startActivity(i_generarCurp);
        }
    };

    private void closeSubMenusFab(){
        llCC.setVisibility(View.INVISIBLE);
        llAGF.setVisibility(View.INVISIBLE);
        fbAgregarCC.setImageResource(R.drawable.ic_add_black);
        fabExpanded = false;
    }

    //Opens FAB submenus
    private void openSubMenusFab(){
        llCC.setVisibility(View.VISIBLE);
        llAGF.setVisibility(View.VISIBLE);

        //Change settings icon to 'X' icon
        fbAgregarCC.setImageResource(R.drawable.ic_close_black);
        fabExpanded = true;
    }

    private void init (){
        String sql = "SELECT r1.*, CASE(SELECT COUNT(*) FROM "+TBL_RECIBOS+" AS r2 WHERE r2.folio = r1.folio) WHEN 1 THEN 'O' ELSE 'O|C' END AS folio FROM " + TBL_RECIBOS + " AS r1 WHERE r1.tipo_impresion = (SELECT r3.tipo_impresion FROM "+TBL_RECIBOS+" AS r3 WHERE r3.folio = r1.folio ORDER BY r3.tipo_impresion DESC LIMIT 1)";

        Cursor row = db.rawQuery(sql, null);
        Log.e("CC", row.getCount()+"asdasd");
        if (row.getCount() > 0){
            row.moveToFirst();
            ArrayList<MTicketCC> data = new ArrayList<>();
            for(int i = 0; i < row.getCount(); i++){
                Log.e("TipoRe", row.getString(3));
                MTicketCC ticketCC = new MTicketCC();
                ticketCC.setId(row.getString(0));
                ticketCC.setPrestamoId(row.getString(1));
                ticketCC.setAsesorId(row.getString(2));
                ticketCC.setTipoRecibo(row.getString(3));
                ticketCC.setTipoImpresion(row.getString(15));
                ticketCC.setFolio(row.getInt(5));
                ticketCC.setMonto(row.getString(6));
                ticketCC.setClave(row.getString(7));
                ticketCC.setNombreCliente(row.getString(8));
                ticketCC.setFechaImpresion(row.getString(11));
                ticketCC.setFechaEnvio(row.getString(12));
                ticketCC.setEstatus(row.getInt(13));
                ticketCC.setCurp(row.getString(14));

                data.add(ticketCC);
                row.moveToNext();
            }

            adapter_clientes_cc adapter = new adapter_clientes_cc(ctx, data, new adapter_clientes_cc.Event() {

                @Override
                public void ClienteOnClick(MTicketCC item) {
                    Cursor row;
                    Log.e("CurpTipo", item.getCurp() + " " + item.getTipoRecibo());
                    row = dBhelper.getRecords(TBL_RECIBOS, " WHERE curp = ? AND tipo_recibo = ?", "", new String[]{item.getCurp(), item.getTipoRecibo()});
                    row.moveToFirst();
                    MFormatoRecibo mRecibo = new MFormatoRecibo();
                    Log.e("CountRecubo", String.valueOf(row.getCount()));
                    if (row.getCount() == 1){
                        mRecibo.setIdPrestamo(row.getString(1));
                        mRecibo.setNombreCliente(item.getNombreCliente().trim());
                        mRecibo.setTipoRecibo(item.getTipoRecibo());
                        mRecibo.setTipoImpresion(false);
                        mRecibo.setClave(row.getString(7));
                        mRecibo.setMonto(item.getMonto());
                        mRecibo.setResImpresion(1);
                        mRecibo.setCurp(item.getCurp());
                        mRecibo.setFolio(row.getString(5));
                        mRecibo.setReeimpresion(false);
                    }
                    else if(row.getCount() >= 2){
                        mRecibo.setIdPrestamo(item.getPrestamoId());
                        mRecibo.setNombreCliente(item.getNombreCliente().trim());
                        mRecibo.setTipoRecibo(item.getTipoRecibo());
                        mRecibo.setTipoImpresion(true);
                        mRecibo.setClave(item.getClave());
                        mRecibo.setMonto(item.getMonto());
                        mRecibo.setResImpresion(2);
                        mRecibo.setCurp(item.getCurp());
                        mRecibo.setFolio(String.valueOf(item.getFolio()));
                        mRecibo.setReeimpresion(true);
                    }

                    Log.e("tipoRecibo", mRecibo.getTipoRecibo());
                    Intent i_formato_recibo = new Intent(CirculoCredito.this, FormatoRecibos.class);
                    i_formato_recibo.putExtra(TICKET_CC, mRecibo);
                    startActivity(i_formato_recibo);
                }
            });

            rvClientesCC.setAdapter(adapter);
        }
        row.close();
    }

    @Override
    protected void onResume() {
        super.onResume();
        init();
    }
}
