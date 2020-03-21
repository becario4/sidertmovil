package com.sidert.sidertmovil.activities;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.adapters.adapter_clientes_cc;
import com.sidert.sidertmovil.database.DBhelper;
import com.sidert.sidertmovil.fragments.dialogs.dialog_circulo_credito;
import com.sidert.sidertmovil.models.MFormatoRecibo;
import com.sidert.sidertmovil.models.MTicketCC;
import com.sidert.sidertmovil.models.ModeloCatalogoGral;
import com.sidert.sidertmovil.utils.Constants;
import com.sidert.sidertmovil.utils.Miscellaneous;
import com.sidert.sidertmovil.utils.NameFragments;

import java.util.ArrayList;

import static com.sidert.sidertmovil.utils.Constants.CATALOGO;
import static com.sidert.sidertmovil.utils.Constants.COLONIAS;
import static com.sidert.sidertmovil.utils.Constants.ENVIROMENT;
import static com.sidert.sidertmovil.utils.Constants.ESTADOS;
import static com.sidert.sidertmovil.utils.Constants.EXTRA;
import static com.sidert.sidertmovil.utils.Constants.ITEM;
import static com.sidert.sidertmovil.utils.Constants.RECIBOS_CIRCULO_CREDITO;
import static com.sidert.sidertmovil.utils.Constants.RECIBOS_CIRCULO_CREDITO_T;
import static com.sidert.sidertmovil.utils.Constants.REQUEST_CODE;
import static com.sidert.sidertmovil.utils.Constants.REQUEST_CODE_COLONIA;
import static com.sidert.sidertmovil.utils.Constants.SOLICITUDES_T;
import static com.sidert.sidertmovil.utils.Constants.TICKET_CC;
import static com.sidert.sidertmovil.utils.Constants.TITULO;
import static com.sidert.sidertmovil.utils.Constants.camara;

public class CirculoCredito extends AppCompatActivity {

    private Context ctx;
    private Toolbar tbMain;

    private RecyclerView rvClientesCC;
    private FloatingActionButton fbAgregarCC;

    private adapter_clientes_cc adapter;

    private DBhelper dBhelper;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circulo_credito);

        ctx = this;

        dBhelper = new DBhelper(ctx);
        db = dBhelper.getWritableDatabase();

        tbMain = findViewById(R.id.TBmain);

        rvClientesCC = findViewById(R.id.rvClienteCC);
        fbAgregarCC  = findViewById(R.id.fbAgregar);

        rvClientesCC.setLayoutManager(new LinearLayoutManager(ctx));
        rvClientesCC.setHasFixedSize(false);

        fbAgregarCC.setOnClickListener(fbAgregarCC_OnClick);

        init();
    }

    private View.OnClickListener fbAgregarCC_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            dialog_circulo_credito dlg_cc = new dialog_circulo_credito();
            dlg_cc.show(getSupportFragmentManager(), NameFragments.DIALOGOCIRCULOCREDITO);
        }
    };

    private void init (){
        Cursor row = null;
        if (ENVIROMENT)
            row = dBhelper.getRecords(RECIBOS_CIRCULO_CREDITO, " WHERE tipo_impresion = 'O'", "", null);
        else
            row = dBhelper.getRecords(RECIBOS_CIRCULO_CREDITO_T, " WHERE tipo_impresion = 'O'", "", null);

        if (row.getCount() > 0){
            row.moveToFirst();
            ArrayList<MTicketCC> data = new ArrayList<>();
            for(int i = 0; i < row.getCount(); i++){
                MTicketCC ticketCC = new MTicketCC();
                ticketCC.set_id(row.getString(0));
                ticketCC.setAsesor_id(row.getString(1));
                ticketCC.setTipo_impresion(row.getString(2));
                ticketCC.setNombre_cliente(row.getString(3));
                ticketCC.setFolio(row.getInt(4));
                ticketCC.setFecha_impresion(row.getString(5));
                ticketCC.setFecha_envio(row.getString(6));
                ticketCC.setEstatus(row.getInt(7));
                ticketCC.setTipo_recibo(1);
                data.add(ticketCC);
                row.moveToNext();
            }

            adapter = new adapter_clientes_cc(ctx, data, new adapter_clientes_cc.Event() {

                @Override
                public void ClienteOnClick(MTicketCC item) {
                    Cursor row;
                    if (ENVIROMENT)
                        row = dBhelper.getRecords(RECIBOS_CIRCULO_CREDITO, " WHERE nombre_cliente = '"+item.getNombre_cliente()+"'", "", null);
                    else
                        row = dBhelper.getRecords(RECIBOS_CIRCULO_CREDITO_T, " WHERE nombre_cliente = '"+item.getNombre_cliente()+"'", "", null);

                    MFormatoRecibo mRecibo = new MFormatoRecibo();
                    switch (row.getCount()){
                        case 1:
                            mRecibo.setNombreCliente(item.getNombre_cliente().trim());
                            mRecibo.setTipoRecibo(1);
                            mRecibo.setTipoImpresion(false);
                            mRecibo.setResImpresion(1);
                            mRecibo.setFolio("");
                            mRecibo.setReeimpresion(false);
                            break;
                        case 2:
                            mRecibo.setNombreCliente(item.getNombre_cliente().trim());
                            mRecibo.setTipoRecibo(1);
                            mRecibo.setTipoImpresion(true);
                            mRecibo.setFolio(String.valueOf(item.getFolio()));
                            mRecibo.setReeimpresion(true);
                            break;

                    }

                    Intent i_formato_recibo = new Intent(CirculoCredito.this, FormatoRecibos.class);
                    i_formato_recibo.putExtra(TICKET_CC, mRecibo);
                    startActivity(i_formato_recibo);
                }
            });

            rvClientesCC.setAdapter(adapter);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        init();
    }
}
