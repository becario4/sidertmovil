package com.sidert.sidertmovil.activities;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.widget.TextView;

import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.database.DBhelper;
import com.sidert.sidertmovil.utils.Constants;
import com.sidert.sidertmovil.utils.SessionManager;

import org.w3c.dom.Text;

import static com.sidert.sidertmovil.utils.Constants.TBL_CARTERA_GPO_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_CARTERA_IND_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_GEO_RESPUESTAS_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_MIEMBROS_GPO_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_PRESTAMOS_GPO_T;

public class ResumenGeo extends AppCompatActivity {

    private Context ctx;

    private TextView tvClientes;
    private TextView tvIndividuales;
    private TextView tvGrupales;
    private TextView tvTerminadas;
    private TextView tvTerminadasInd;
    private TextView tvTerminadasGpo;
    private TextView tvEnviadas;
    private TextView tvPendientes;
    private TextView tvSinGeolocalizar;

    private SessionManager session;

    private DBhelper dBhelper;
    private SQLiteDatabase db;

    private int clientes = 0, individuales = 0, grupales = 0;
    private int terminadas = 0, terminadasInd = 0, terminadasGpo = 0;
    private int enviadas = 0, pendientes = 0, sinGeolocalizar = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resumen_geo);

        ctx = this;

        session = new SessionManager(ctx);

        dBhelper    = new DBhelper(ctx);
        db          = dBhelper.getWritableDatabase();

        tvClientes          = findViewById(R.id.tvClientes);
        tvIndividuales      = findViewById(R.id.tvIndividuales);
        tvGrupales          = findViewById(R.id.tvGrupales);
        tvTerminadas        = findViewById(R.id.tvTerminadas);
        tvTerminadasInd     = findViewById(R.id.tvTerminadasInd);
        tvTerminadasGpo     = findViewById( R.id.tvTerminadasGpo);
        tvEnviadas          = findViewById(R.id.tvEnviadas);
        tvPendientes        = findViewById(R.id.tvPendientes);
        tvSinGeolocalizar   = findViewById(R.id.tvSinGeolocalizar);

        //GetResumenGeo();
        GetNewResumen();
    }

    private void GetNewResumen(){
        Cursor row;

        String sql = "SELECT * FROM (SELECT ci.id_cartera, ci.clave, ci.nombre, ci.direccion, ci.colonia, ci.num_solicitud, ci.asesor_nombre, 1 AS tipo_ficha, 1 AS total_integrantes, 0 AS total_contestadas, COALESCE((SELECT COALESCE(g._id,'') AS id FROM "+TBL_GEO_RESPUESTAS_T+" AS g WHERE g.tipo_geolocalizacion = 'CLIENTE' AND g.id_cartera = ci.id_cartera), '') AS res_uno, COALESCE((SELECT COALESCE(g._id,'') AS id FROM "+TBL_GEO_RESPUESTAS_T+" AS g WHERE g.tipo_geolocalizacion = 'NEGOCIO' AND g.id_cartera = ci.id_cartera), '') AS res_dos, COALESCE((SELECT COALESCE(g._id,'') AS id FROM "+TBL_GEO_RESPUESTAS_T+" AS g WHERE g.tipo_geolocalizacion = 'AVAL' AND g.id_cartera = ci.id_cartera), '') AS res_tres FROM " + TBL_CARTERA_IND_T + " AS ci UNION SELECT cg.id_cartera, cg.clave, cg.nombre, cg.direccion, cg.colonia, cg.num_solicitud, cg.asesor_nombre, 2 AS tipo_ficha, COUNT(m._id) AS total_integrantes, SUM(CASE WHEN gr._id IS NOT NULL THEN 1 ELSE 0 END) AS total_contestadas, COALESCE((SELECT COALESCE(g._id,'') AS id FROM "+TBL_GEO_RESPUESTAS_T+" AS g WHERE g.tipo_geolocalizacion = 'PRESIDENTE' AND g.id_cartera = cg.id_cartera), '') AS res_uno, COALESCE((SELECT COALESCE(g._id,'') AS id FROM "+TBL_GEO_RESPUESTAS_T+" AS g WHERE g.tipo_geolocalizacion = 'TESORERO' AND g.id_cartera = cg.id_cartera), '') AS res_dos, COALESCE((SELECT COALESCE(g._id,'') AS id FROM "+TBL_GEO_RESPUESTAS_T+" AS g WHERE g.tipo_geolocalizacion = 'SECRETARIO' AND g.id_cartera = cg.id_cartera), '') AS res_tres FROM " + TBL_CARTERA_GPO_T + " AS cg LEFT JOIN "+TBL_PRESTAMOS_GPO_T+" AS pg ON pg.id_grupo = cg.id_cartera LEFT JOIN "+TBL_MIEMBROS_GPO_T+" AS m ON m.id_prestamo = pg.id_prestamo LEFT JOIN "+TBL_GEO_RESPUESTAS_T+" AS gr ON gr.id_integrante = m.id_integrante GROUP BY cg.id_cartera, cg.clave, cg.nombre, cg.direccion, cg.colonia, cg.num_solicitud, cg.asesor_nombre ) AS geo_res";

        row = db.rawQuery(sql, null);
        if (row.getCount() > 0){
            row.moveToFirst();
            clientes = row.getCount();

            for (int i = 0; i < row.getCount(); i++){
                if (row.getInt(7) == 1) {
                    individuales += 1;
                    if (row.getString(10).trim().isEmpty())
                        sinGeolocalizar += 1;
                    if (row.getString(11).trim().isEmpty())
                        sinGeolocalizar += 1;
                    if (row.getString(12).trim().isEmpty())
                        sinGeolocalizar += 1;
                }
                if (row.getInt(7) == 2) {
                    grupales += 1;
                    sinGeolocalizar += (row.getInt(8) - row.getInt(9));
                }

                if (!row.getString(10).trim().isEmpty() && row.getInt(7) == 1)
                    terminadasInd += 1;
                if (!row.getString(11).trim().isEmpty() && row.getInt(7) == 1)
                    terminadasInd += 1;
                if (!row.getString(12).trim().isEmpty() && row.getInt(7) == 1)
                    terminadasInd += 1;

                if (row.getInt(7) == 2)
                    terminadasGpo += row.getInt(9);

                row.moveToNext();
            }
        }
        row.close();

        sql = "SELECT * FROM " + TBL_GEO_RESPUESTAS_T ;
        row = db.rawQuery(sql, null);
        if (row.getCount() > 0){
            row.moveToFirst();
            terminadas = row.getCount();
            for (int i = 0; i < row.getCount(); i++){
                if (row.getInt(16) == 1)
                    enviadas += 1;
                if (row.getInt(16) == 0)
                    pendientes += 1;

                row.moveToNext();
            }
        }
        row.close();

        tvClientes.setText("Clientes: " + clientes);
        tvIndividuales.setText("Individuales: " + individuales);
        tvGrupales.setText("Grupales: " + grupales);
        tvTerminadas.setText("Terminadas: " + terminadas);
        tvTerminadasInd.setText("Terminadas Individuales: " + terminadasInd);
        tvTerminadasGpo.setText("Terminadas Grupales: "+ terminadasGpo);
        tvEnviadas.setText("Enviadas: " + enviadas);
        tvPendientes.setText("Pendientes por Enviar: " + pendientes);
        tvSinGeolocalizar.setText("Sin Geolocalizar: " + sinGeolocalizar);
    }

    private void GetResumenGeo (){
        Cursor row;
        if (Constants.ENVIROMENT)
            row = dBhelper.getRecords(Constants.GEOLOCALIZACION, "", "", null);
        else
            row = dBhelper.getRecords(Constants.GEOLOCALIZACION_T, "", "", null);

        if (row.getCount() > 0){
            row.moveToFirst();
            clientes = row.getCount();
            for (int i = 0; i < row.getCount(); i++){
                if (row.getInt(3) == 1)
                    individuales += 1;
                if (row.getInt(3) == 2)
                    grupales += 1;


                if (!row.getString(13).trim().isEmpty())
                    terminadas += 1;
                if (!row.getString(14).trim().isEmpty())
                    terminadas += 1;
                if (!row.getString(15).trim().isEmpty())
                    terminadas += 1;

                if (!row.getString(13).trim().isEmpty() && row.getInt(3) == 1)
                    terminadasInd += 1;
                if (!row.getString(14).trim().isEmpty() && row.getInt(3) == 1)
                    terminadasInd += 1;
                if (!row.getString(15).trim().isEmpty() && row.getInt(3) == 1)
                    terminadasInd += 1;

                if (!row.getString(13).trim().isEmpty() && row.getInt(3) == 2)
                    terminadasGpo += 1;
                if (!row.getString(14).trim().isEmpty() && row.getInt(3) == 2)
                    terminadasGpo += 1;
                if (!row.getString(15).trim().isEmpty() && row.getInt(3) == 2)
                    terminadasGpo += 1;

                if (!row.getString(13).trim().isEmpty() && !row.getString(16).trim().isEmpty())
                    enviadas += 1;
                if (!row.getString(14).trim().isEmpty() && !row.getString(17).trim().isEmpty())
                    enviadas += 1;
                if (!row.getString(15).trim().isEmpty() && !row.getString(18).trim().isEmpty())
                    enviadas += 1;

                if (!row.getString(13).trim().isEmpty() && row.getString(16).trim().isEmpty())
                    pendientes += 1;
                if (!row.getString(14).trim().isEmpty() && row.getString(17).trim().isEmpty())
                    pendientes += 1;
                if (!row.getString(15).trim().isEmpty() && row.getString(18).trim().isEmpty())
                    pendientes += 1;

                if (row.getString(13).trim().isEmpty())
                    sinGeolocalizar += 1;
                if (row.getString(14).trim().isEmpty())
                    sinGeolocalizar += 1;
                if (row.getString(15).trim().isEmpty())
                    sinGeolocalizar += 1;

                row.moveToNext();
            }
        }

        tvClientes.setText("Clientes: " + clientes);
        tvIndividuales.setText("Individuales: " + individuales);
        tvGrupales.setText("Grupales: " + grupales);
        tvTerminadas.setText("Terminadas: " + terminadas);
        tvTerminadasInd.setText("Terminadas Individuales: " + terminadasInd);
        tvTerminadasGpo.setText("Terminadas Grupales: "+ terminadasGpo);
        tvEnviadas.setText("Enviadas: " + enviadas);
        tvPendientes.setText("Pendientes por Enviar: " + pendientes);
        tvSinGeolocalizar.setText("Sin Geolocalizar: " + sinGeolocalizar);
    }
}
