package com.sidert.sidertmovil.activities;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.database.DBhelper;
import com.sidert.sidertmovil.utils.SessionManager;

import static com.sidert.sidertmovil.utils.Constants.TBL_CARTERA_GPO_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_CARTERA_IND_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_PRESTAMOS_GPO_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_PRESTAMOS_IND_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_RESPUESTAS_GPO_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_RESPUESTAS_IND_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_RESPUESTAS_IND_V_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_RESPUESTAS_INTEGRANTE_T;

/**Clase para ver el resumen de informacion de la cartera que tiene asignado el asesor*/
public class ResumenCartera extends AppCompatActivity {

    private Context ctx;

    private TextView tvCartera;
    private TextView tvIndividuales;
    private TextView tvGrupales;
    private TextView tvGestionadas;
    private TextView tvEnviadas;
    private TextView tvPendientes;
    private TextView tvTotalParcial;
    private TextView tvParcialInd;
    private TextView tvParcialGpo;

    private SessionManager session;

    private DBhelper dBhelper;
    private SQLiteDatabase db;

    private int cartera = 0, individuales = 0, grupales = 0;
    private int gestionadas = 0, enviadas = 0, pendientes = 0;
    private int totalParcial = 0, parcialInd = 0, parcialGpo = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resumen_cartera);

        ctx = this;

        session = SessionManager.getInstance(ctx);

        dBhelper    = DBhelper.getInstance(ctx);
        db          = dBhelper.getWritableDatabase();

        tvCartera           = findViewById(R.id.tvCartera);
        tvIndividuales      = findViewById(R.id.tvIndividuales);
        tvGrupales          = findViewById(R.id.tvGrupales);
        tvGestionadas       = findViewById(R.id.tvGestionadas);
        tvEnviadas          = findViewById(R.id.tvEnviadas);
        tvPendientes        = findViewById(R.id.tvPendientes);
        tvTotalParcial      = findViewById(R.id.tvTotalParcial);
        tvParcialInd        = findViewById(R.id.tvParcialInd);
        tvParcialGpo        = findViewById(R.id.tvParcialGpo);
        GetResumenCartera();
    }

    /**Funcion para obtener la informacion de la cartera, como por ejemplo el total de cartera asignada
     * total de individuales, total de grupales, etc...*/
    private void GetResumenCartera(){
        Cursor row;
        String sql;

        /**Consulta para obtener el total de cartera de individuales y grupales*/
        sql = "SELECT * FROM (SELECT COUNT(ci._id) AS total, 1 AS tipo_cartera FROM " + TBL_CARTERA_IND_T + " AS ci UNION SELECT COUNT(cg._id) AS total, 2 AS total_cartera FROM " + TBL_CARTERA_GPO_T + " AS cg) AS cartera";
        row = db.rawQuery(sql, null);

        if (row.getCount() > 0){
            row.moveToFirst();

            /**recorre el resultado de la consulta*/
            for(int i = 0; i < row.getCount(); i++){
                if (row.getInt(1) == 1)//valida si es individual
                    individuales = row.getInt(0);
                if (row.getInt(1) == 2)//valida si es grupal
                    grupales = row.getInt(0);
                row.moveToNext();
            }

            /**suma el total de individuales y grupales para obtener el total de la cartera*/
            cartera = individuales + grupales;
        }
        row.close();

        /**consulta para obtener el total de gestionadas de las tablas de TBL_RESPUESTAS_IND_T(vigente, cobranza), TBL_RESPUESTAS_GPO_T(vigente, cobranza), TBL_RESPUESTAS_IND_V_T(vencida individual), TBL_RESPUESTAS_INTEGRANTE_T(vencida integrante de grupales)*/
        sql = "SELECT * FROM (SELECT _id, estatus, 1 AS tipo_cartera FROM " + TBL_RESPUESTAS_IND_T + " WHERE estatus IN (1,2) UNION SELECT _id, estatus, 2 AS tipo_cartera FROM " + TBL_RESPUESTAS_GPO_T + " WHERE estatus IN (1,2) UNION SELECT _id, estatus, 1 AS tipo_cartera FROM "+TBL_RESPUESTAS_IND_V_T+" WHERE estatus IN (1,2) UNION SELECT _id, estatus, 1 AS tipo_cartera FROM "+TBL_RESPUESTAS_INTEGRANTE_T+" WHERE estatus IN (1,2)) AS respuesta";
        row = db.rawQuery(sql, null);

        gestionadas = row.getCount();
        if (row.getCount() > 0){
            row.moveToFirst();

            for (int i = 0; i < row.getCount(); i++){
                if (row.getInt(1) == 1)//valida si es individuales
                    pendientes += 1;
                if (row.getInt(1) == 2)//valida si es grupal
                    enviadas += 1;

                row.moveToNext();
            }
        }
        row.close();

        /**consulta para obtener el total de gestionadas en estatus parcial de las tablas de TBL_RESPUESTAS_IND_T(vigente, cobranza), TBL_RESPUESTAS_GPO_T(vigente, cobranza), TBL_RESPUESTAS_IND_V_T(vencida individual), TBL_RESPUESTAS_INTEGRANTE_T(vencida integrante de grupales)*/
        sql = "SELECT * FROM (SELECT 1 AS tipo, ri.estatus, ri.id_prestamo AS parcial FROM " + TBL_RESPUESTAS_IND_T + " AS ri WHERE ri.estatus = '0' UNION SELECT 2 AS tipo, rg.estatus, rg.id_prestamo AS parcial FROM "+TBL_RESPUESTAS_GPO_T +" AS rg WHERE rg.estatus = '0' UNION SELECT 1 AS tipo, rvi.estatus AS parcial, rvi.id_prestamo FROM " + TBL_RESPUESTAS_IND_V_T + " AS rvi WHERE rvi.estatus = '0' UNION SELECT 2 AS tipo,rvg.estatus AS parcial, rvg.id_prestamo FROM " + TBL_RESPUESTAS_INTEGRANTE_T + " AS rvg WHERE rvg.estatus = '0') AS cartera";
        row = db.rawQuery(sql, null);
        totalParcial = row.getCount();
        if (row.getCount() > 0){
            row.moveToFirst();
            for (int i = 0; i < row.getCount(); i++){
                Log.e("Parcial", row.getString(1));

                if (row.getInt(0) == 1 && row.getInt(1) == 0)//valida si es individual y estatus parcial
                    parcialInd += 1;
                if (row.getInt(0) == 2 && row.getInt(1) == 0)//valida si es grupal y estatus parcial
                    parcialGpo += 1;
                row.moveToNext();
            }
        }
        row.close();

        /**coloca los totales para mostrar*/
        tvCartera.setText("Cartera: " + cartera);
        tvIndividuales.setText("Individuales: " + individuales);
        tvGrupales.setText("Grupales: " + grupales);
        tvGestionadas.setText("Gestionadas: " + gestionadas);
        tvEnviadas.setText("Enviadas: " + enviadas);
        tvPendientes.setText("Pendientes por Enviar: " + pendientes);
        tvTotalParcial.setText("Total Parciales: " + totalParcial);
        tvParcialInd.setText("Parciales Individuales: " + parcialInd);
        tvParcialGpo.setText("Parciales Grupales: " + parcialGpo);


    }
}
