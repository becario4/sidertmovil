package com.sidert.sidertmovil.activities;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.database.DBhelper;
import com.sidert.sidertmovil.utils.SessionManager;

import static com.sidert.sidertmovil.utils.Constants.TBL_CARTERA_GPO_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_CARTERA_IND_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_RESPUESTAS_GPO_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_RESPUESTAS_IND_T;

public class ResumenCartera extends AppCompatActivity {

    private Context ctx;

    private TextView tvCartera;
    private TextView tvIndividuales;
    private TextView tvGrupales;
    private TextView tvGestionadas;
    private TextView tvEnviadas;
    private TextView tvPendientes;

    private SessionManager session;

    private DBhelper dBhelper;
    private SQLiteDatabase db;

    private int cartera = 0, individuales = 0, grupales = 0;
    private int gestionadas = 0, enviadas = 0, pendientes = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resumen_cartera);

        ctx = this;

        session = new SessionManager(ctx);

        dBhelper    = new DBhelper(ctx);
        db          = dBhelper.getWritableDatabase();

        tvCartera           = findViewById(R.id.tvCartera);
        tvIndividuales      = findViewById(R.id.tvIndividuales);
        tvGrupales          = findViewById(R.id.tvGrupales);
        tvGestionadas       = findViewById(R.id.tvGestionadas);
        tvEnviadas          = findViewById(R.id.tvEnviadas);
        tvPendientes        = findViewById(R.id.tvPendientes);

        GetResumenCartera();
    }

    private void GetResumenCartera(){
        Cursor row;
        String sql;

        sql = "SELECT * FROM (SELECT COUNT(ci._id) AS total, 1 AS tipo_cartera FROM " + TBL_CARTERA_IND_T + " AS ci UNION SELECT COUNT(cg._id) AS total, 2 AS total_cartera FROM " + TBL_CARTERA_GPO_T + " AS cg) AS cartera";
        row = db.rawQuery(sql, null);

        if (row.getCount() > 0){
            row.moveToFirst();

            for(int i = 0; i < row.getCount(); i++){
                if (row.getInt(1) == 1)
                    individuales = row.getInt(0);
                if (row.getInt(1) == 2)
                    grupales = row.getInt(0);
                row.moveToNext();
            }

            cartera = individuales + grupales;
        }
        row.close();

        sql = "SELECT * FROM (SELECT _id, estatus, 1 AS tipo_cartera FROM " + TBL_RESPUESTAS_IND_T + " UNION SELECT _id, estatus, 2 AS tipo_cartera FROM " + TBL_RESPUESTAS_GPO_T + ") AS respuesta";
        row = db.rawQuery(sql, null);

        gestionadas = row.getCount();
        if (row.getCount() > 0){
            row.moveToFirst();

            for (int i = 0; i < row.getCount(); i++){
                if (row.getInt(1) == 1)
                    pendientes += 1;
                if (row.getInt(1) == 2)
                    enviadas += 1;

                row.moveToNext();
            }
        }

        tvCartera.setText("Cartera: " + cartera);
        tvIndividuales.setText("Individuales: " + individuales);
        tvGrupales.setText("Grupales: " + grupales);
        tvGestionadas.setText("Gestionadas: " + gestionadas);
        tvEnviadas.setText("Enviadas: " + enviadas);
        tvPendientes.setText("Pendientes por Enviar: " + pendientes);


    }
}
