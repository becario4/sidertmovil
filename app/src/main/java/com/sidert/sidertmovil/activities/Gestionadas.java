package com.sidert.sidertmovil.activities;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.adapters.adapter_gestionadas_ind;
import com.sidert.sidertmovil.database.DBhelper;
import com.sidert.sidertmovil.models.MGestionada;
import com.sidert.sidertmovil.utils.Miscellaneous;

import java.util.ArrayList;

import static com.sidert.sidertmovil.utils.Constants.ACTUALIZAR_TELEFONO;
import static com.sidert.sidertmovil.utils.Constants.COMENTARIO;
import static com.sidert.sidertmovil.utils.Constants.CONTACTO;
import static com.sidert.sidertmovil.utils.Constants.EVIDENCIA;
import static com.sidert.sidertmovil.utils.Constants.FECHA_DEFUNCION;
import static com.sidert.sidertmovil.utils.Constants.FECHA_DEPOSITO;
import static com.sidert.sidertmovil.utils.Constants.FIRMA;
import static com.sidert.sidertmovil.utils.Constants.FOLIO_TICKET;
import static com.sidert.sidertmovil.utils.Constants.GERENTE;
import static com.sidert.sidertmovil.utils.Constants.ID_PRESTAMO;
import static com.sidert.sidertmovil.utils.Constants.IMPRESORA;
import static com.sidert.sidertmovil.utils.Constants.LATITUD;
import static com.sidert.sidertmovil.utils.Constants.LONGITUD;
import static com.sidert.sidertmovil.utils.Constants.MEDIO_PAGO;
import static com.sidert.sidertmovil.utils.Constants.MOTIVO_ACLARACION;
import static com.sidert.sidertmovil.utils.Constants.MOTIVO_NO_PAGO;
import static com.sidert.sidertmovil.utils.Constants.NUEVO_TELEFONO;
import static com.sidert.sidertmovil.utils.Constants.PAGO_REALIZADO;
import static com.sidert.sidertmovil.utils.Constants.PAGO_REQUERIDO;
import static com.sidert.sidertmovil.utils.Constants.PARAMS;
import static com.sidert.sidertmovil.utils.Constants.RESULTADO_PAGO;
import static com.sidert.sidertmovil.utils.Constants.TBL_NAME;
import static com.sidert.sidertmovil.utils.Constants.TIPO_IMAGEN;

public class Gestionadas extends AppCompatActivity {

    private Context ctx;
    private Toolbar tbMain;

    private RecyclerView rvGestionadas;

    private adapter_gestionadas_ind adapter;

    private DBhelper dBhelper;
    private SQLiteDatabase db;

    private String id_prestamo = "0";

    private String tbl = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestiondas);

        ctx = this;

        dBhelper = new DBhelper(ctx);
        db = dBhelper.getWritableDatabase();

        tbMain = findViewById(R.id.TBmain);

        rvGestionadas = findViewById(R.id.rvGestionadas);

        rvGestionadas.setLayoutManager(new LinearLayoutManager(ctx));
        rvGestionadas.setHasFixedSize(false);

        id_prestamo = getIntent().getStringExtra(ID_PRESTAMO);
        tbl = getIntent().getStringExtra(TBL_NAME);

        init();
    }

    private void init (){
        Cursor row = null;
        row = dBhelper.getRecords(tbl, " WHERE id_prestamo = ? AND estatus  IN(?,?)", " ORDER BY fecha_inicio DESC", new String[]{id_prestamo, "1","2" });
        /*if (ENVIROMENT)
            row = dBhelper.getRecords(TBL_RESPUESTAS_IND, " WHERE id_prestamo = ? AND estatus  IN(?,?)", " ORDER BY fecha_inicio DESC", new String[]{id_prestamo, "1","2" });
        else
            row = dBhelper.getRecords(TBL_RESPUESTAS_IND_T, " WHERE id_prestamo = ? AND estatus  IN(?,?)", " ORDER BY fecha_inicio DESC", new String[]{id_prestamo, "1","2" });*/

        Log.e("xxxxx","asad"+row.getCount());
        if (row.getCount() > 0){
            row.moveToFirst();
            ArrayList<MGestionada> data = new ArrayList<>();
            for(int i = 0; i < row.getCount(); i++){
                MGestionada gestionadas = new MGestionada();
                gestionadas.setIdGestion(row.getString(0));
                gestionadas.setFechaGestion(row.getString(23));
                switch (row.getString(4)){
                    case "SI":
                        gestionadas.setContacto("SI CONTACTO");
                        gestionadas.setResultado(row.getString(9));
                        if (row.getString(9).equals("PAGO")) {
                            gestionadas.setComentarioBanco("MEDIO PAGO: " + row.getString(12));
                            gestionadas.setMonto(Miscellaneous.moneyFormat(row.getString(15)));
                        }
                        else {
                            gestionadas.setComentarioBanco("COMENTARIO: " + row.getString(6));
                            gestionadas.setMonto("");
                        }
                        break;
                    case "NO":
                        gestionadas.setContacto("NO CONTACTO");
                        gestionadas.setComentarioBanco("COMENTARIO: " + row.getString(6));
                        gestionadas.setResultado(row.getString(9));
                        gestionadas.setMonto("");
                        break;
                    case "ACLARACION":
                        gestionadas.setContacto("ACLARACION");
                        gestionadas.setComentarioBanco("MOTIVO: " + row.getString(5));
                        gestionadas.setResultado("");
                        gestionadas.setMonto("");
                        break;
                }

                data.add(gestionadas);
                row.moveToNext();
            }

            adapter = new adapter_gestionadas_ind(ctx, data, new adapter_gestionadas_ind.Event() {

                @Override
                public void GestionadaClick(MGestionada item) {
                    Cursor row;
                    row = dBhelper.getRecords(tbl, " WHERE _id = ?", "", new String[]{item.getIdGestion()});
                    /*if (ENVIROMENT)
                        row = dBhelper.getRecords(TBL_RESPUESTAS_IND, " WHERE _id = ?", "", new String[]{item.getId_gestion()});
                    else
                        row = dBhelper.getRecords(TBL_RESPUESTAS_IND_T, " WHERE _id = ?", "", new String[]{item.getId_gestion()});*/

                    Log.e("count",""+row.getCount()+"ccccc");
                    if (row.getCount() > 0){
                        row.moveToFirst();
                        Bundle b = new Bundle();
                        b.putDouble(LATITUD, row.getDouble(2));
                        b.putDouble(LONGITUD, row.getDouble(3));
                        b.putString(CONTACTO, row.getString(4));
                        switch (row.getString(4)){
                            case "SI":
                                b.putString(ACTUALIZAR_TELEFONO, row.getString(7));
                                if (row.getString(7).equals("SI"))
                                    b.putString(NUEVO_TELEFONO, row.getString(8));

                                b.putString(RESULTADO_PAGO, row.getString(9));
                                switch (row.getString(9)){
                                    case "PAGO":
                                        b.putString(MEDIO_PAGO, row.getString(12));
                                        b.putString(FECHA_DEPOSITO,row.getString(13));
                                        b.putString(PAGO_REQUERIDO, row.getString(14));
                                        b.putString(PAGO_REALIZADO, row.getString(15));
                                        if (row.getString(12).equals("EFECTIVO") || row.getString(12).equals("SIDERT")) {
                                            b.putString(IMPRESORA, row.getString(16));
                                            b.putString(FOLIO_TICKET, row.getString(17));
                                        }
                                        break;
                                    case "NO PAGO":
                                        b.putString(MOTIVO_NO_PAGO, row.getString(10));
                                        if (row.getString(10).equals("FALLECIMIENTO"))
                                            b.putString(FECHA_DEFUNCION, row.getString(11));
                                        b.putString(COMENTARIO, row.getString(6));
                                        break;
                                }
                                break;
                            case "NO":
                                b.putString(COMENTARIO, row.getString(6));
                                break;
                            case "ACLARACION":
                                b.putString(MOTIVO_ACLARACION, row.getString(5));
                                b.putString(COMENTARIO, row.getString(6));
                                break;
                        }

                        b.putString(EVIDENCIA, row.getString(18));
                        b.putString(TIPO_IMAGEN, row.getString(19));
                        b.putString(GERENTE, row.getString(20));
                        if (row.getString(20).equals("SI"))
                            b.putString(FIRMA, row.getString(21));

                        Log.e("SIDERTMOVIL", b.toString());

                        Intent i_preview = new Intent(ctx, VistaGestion.class);
                        i_preview.putExtra(PARAMS,b);
                        startActivity(i_preview);
                    }
                }
            });

            rvGestionadas.setAdapter(adapter);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        init();
    }
}
