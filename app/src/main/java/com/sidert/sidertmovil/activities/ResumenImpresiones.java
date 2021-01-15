package com.sidert.sidertmovil.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;

import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.database.DBhelper;

import static com.sidert.sidertmovil.utils.Constants.TBL_CARTERA_GPO_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_CARTERA_IND_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_IMPRESIONES_VENCIDA_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_IMPRESIONES_VIGENTE_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_PRESTAMOS_GPO_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_PRESTAMOS_IND_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_REIMPRESION_VIGENTE_T;
import static com.sidert.sidertmovil.utils.Constants.TIPO;

/**clase para obtener el resumen de informacion de las impresiones realizadas de recuperaciones de vigente y vencida*/
public class ResumenImpresiones extends AppCompatActivity {

    private Context ctx;

    private Toolbar tbMain;

    private TextView tvImpresiones;
    private TextView tvIndividuales;
    private TextView tvIndOriginal;
    private TextView tvIndCopia;
    private TextView tvGrupales;
    private TextView tvGpoOriginal;
    private TextView tvGpoCopia;
    private TextView tvEnviadas;
    private TextView tvPendientes;

    private DBhelper dBhelper;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resumen_impresiones);

        ctx = this;

        dBhelper    = new DBhelper(ctx);
        db          = dBhelper.getWritableDatabase();

        tbMain  = findViewById(R.id.tbMain);

        tvImpresiones       = findViewById(R.id.tvImpresiones);
        tvIndividuales      = findViewById(R.id.tvIndividuales);
        tvIndOriginal       = findViewById(R.id.tvIndOriginal);
        tvIndCopia          = findViewById(R.id.tvIndCopias);
        tvGrupales          = findViewById(R.id.tvGrupales);
        tvGpoOriginal       = findViewById(R.id.tvGpoOriginal);
        tvGpoCopia          = findViewById(R.id.tvGpoCopias);
        tvEnviadas          = findViewById(R.id.tvEnviadas);
        tvPendientes        = findViewById(R.id.tvPendientes);

        switch (getIntent().getIntExtra(TIPO, 0)){
            case 1:
                tbMain.setTitle("Resumen Impresiones Vigente");
                break;
            case 2:
                tbMain.setTitle("Resumen Impresiones Vencida");
                break;
            case 3:
                tbMain.setTitle("Resumen Reimpresiones");
                break;
        }


        GetResumenImpresiones(getIntent().getIntExtra(TIPO, 0));
    }

    /**Funcion para obtener el resumen de las impresiones dependiendo de que vista*/
    @SuppressLint("SetTextI18n")
    private void GetResumenImpresiones(int tipo){
        int impresiones = 0, individuales = 0, grupales = 0;
        int enviadas = 0, pendientes = 0;
        int indOriginal = 0, indCopias = 0;
        int gpoOriginal = 0, gpoCopias = 0;



        String sql = "";
        Cursor row;

        switch (tipo) {
            case 1:/**si es la vista de RECUPERACION*/
                /**consulta para obtener el total de impresiones realizadas entre originales y copias*/
                sql = "SELECT COUNT(*) FROM " + TBL_IMPRESIONES_VIGENTE_T;
                row = db.rawQuery(sql, null);

                row.moveToFirst();
                impresiones = row.getInt(0);
                row.close();

                /**consulta para obtener las impresiones realizadas solo de recuperaciones individuales*/
                sql = "SELECT * FROM (SELECT COUNT(*) FROM " + TBL_IMPRESIONES_VIGENTE_T + " AS ivi1 LEFT JOIN " +
                        TBL_PRESTAMOS_IND_T + " AS pi ON ivi1.num_prestamo = pi.num_prestamo LEFT JOIN " +
                        TBL_CARTERA_IND_T + " AS ci ON pi.id_cliente = ci.id_cartera WHERE ivi1.num_prestamo LIKE '%-L%' AND ivi1.tipo_impresion = (SELECT i3.tipo_impresion FROM " + TBL_IMPRESIONES_VIGENTE_T + " AS i3 WHERE i3.folio = ivi1.folio ORDER BY i3.tipo_impresion DESC LIMIT 1)" +
                        ") AS impresiones";
                row = db.rawQuery(sql, null);

                row.moveToFirst();
                individuales = row.getInt(0);
                row.close();

                /**consulta para obtener las impresiones realizadas solo de recuperaciones individuales de tipo ORIGINAL*/
                sql = "SELECT * FROM (SELECT * FROM " + TBL_IMPRESIONES_VIGENTE_T + " AS ivi1 LEFT JOIN " +
                        TBL_PRESTAMOS_IND_T + " AS pi ON ivi1.num_prestamo = pi.num_prestamo LEFT JOIN " +
                        TBL_CARTERA_IND_T + " AS ci ON pi.id_cliente = ci.id_cartera WHERE ivi1.num_prestamo LIKE '%-L%' AND ivi1.tipo_impresion = (SELECT i3.tipo_impresion FROM " + TBL_IMPRESIONES_VIGENTE_T + " AS i3 WHERE i3.folio = ivi1.folio ORDER BY i3.tipo_impresion DESC LIMIT 1)" +
                        ") AS impresiones WHERE tipo_impresion = 'O'";

                row = db.rawQuery(sql, null);

                indOriginal = row.getCount();

                Log.e("Original", "total: " + indOriginal);
                row.close();

                /**consulta para obtener las impresiones realizadas solo de recuperaciones individuales de tipo COPIA*/
                sql = "SELECT * FROM (SELECT * FROM " + TBL_IMPRESIONES_VIGENTE_T + " AS ivi1 LEFT JOIN " +
                        TBL_PRESTAMOS_IND_T + " AS pi ON ivi1.num_prestamo = pi.num_prestamo LEFT JOIN " +
                        TBL_CARTERA_IND_T + " AS ci ON pi.id_cliente = ci.id_cartera WHERE ivi1.num_prestamo LIKE '%-L%' AND ivi1.tipo_impresion = (SELECT i3.tipo_impresion FROM " + TBL_IMPRESIONES_VIGENTE_T + " AS i3 WHERE i3.folio = ivi1.folio ORDER BY i3.tipo_impresion DESC LIMIT 1)" +
                        ") AS impresiones WHERE tipo_impresion = 'C'";

                row = db.rawQuery(sql, null);

                indCopias = row.getCount();
                row.close();

                /**consulta para obtener las impresiones realizadas solo de recuperaciones grupales*/
                sql = "SELECT * FROM (SELECT COUNT(*) FROM " + TBL_IMPRESIONES_VIGENTE_T + " AS ivi2 LEFT JOIN " +
                        TBL_PRESTAMOS_GPO_T + " AS pg ON ivi2.num_prestamo = pg.num_prestamo LEFT JOIN " +
                        TBL_CARTERA_GPO_T + " AS cg ON pg.id_grupo = cg.id_cartera WHERE ivi2.num_prestamo NOT LIKE '%-L%' AND ivi2.tipo_impresion = (SELECT i2.tipo_impresion FROM " + TBL_IMPRESIONES_VIGENTE_T + " AS i2 WHERE i2.folio = ivi2.folio ORDER BY i2.tipo_impresion DESC LIMIT 1)) AS impresiones";

                row = db.rawQuery(sql, null);

                row.moveToFirst();
                grupales = row.getInt(0);
                row.close();

                /**consulta para obtener las impresiones realizadas solo de recuperaciones grupales de tipo ORIGINAL*/
                sql = "SELECT * FROM (SELECT ivi2.* FROM " + TBL_IMPRESIONES_VIGENTE_T + " AS ivi2 LEFT JOIN " +
                        TBL_PRESTAMOS_GPO_T + " AS pg ON ivi2.num_prestamo = pg.num_prestamo LEFT JOIN " +
                        TBL_CARTERA_GPO_T + " AS cg ON pg.id_grupo = cg.id_cartera WHERE ivi2.num_prestamo NOT LIKE '%-L%' AND ivi2.tipo_impresion = (SELECT i2.tipo_impresion FROM " + TBL_IMPRESIONES_VIGENTE_T + " AS i2 WHERE i2.folio = ivi2.folio ORDER BY i2.tipo_impresion DESC LIMIT 1)) AS impresiones  WHERE tipo_impresion = 'O'";
                row = db.rawQuery(sql, null);

                gpoOriginal = row.getCount();
                row.close();

                /**consulta para obtener las impresiones realizadas solo de recuperaciones grupales de tipo COPIA*/
                sql = "SELECT * FROM (SELECT ivi2.* FROM " + TBL_IMPRESIONES_VIGENTE_T + " AS ivi2 LEFT JOIN " +
                        TBL_PRESTAMOS_GPO_T + " AS pg ON ivi2.num_prestamo = pg.num_prestamo LEFT JOIN " +
                        TBL_CARTERA_GPO_T + " AS cg ON pg.id_grupo = cg.id_cartera WHERE ivi2.num_prestamo NOT LIKE '%-L%' AND ivi2.tipo_impresion = (SELECT i2.tipo_impresion FROM " + TBL_IMPRESIONES_VIGENTE_T + " AS i2 WHERE i2.folio = ivi2.folio ORDER BY i2.tipo_impresion DESC LIMIT 1)) AS impresiones  WHERE tipo_impresion = 'C'";
                row = db.rawQuery(sql, null);

                gpoCopias = row.getCount();
                row.close();

                /**consulta para obtener todas las impresiones en estatus de enviadas*/
                sql = "SELECT COUNT(*) FROM " + TBL_IMPRESIONES_VIGENTE_T + " WHERE estatus = '1'";
                row = db.rawQuery(sql, null);

                row.moveToFirst();
                enviadas = row.getInt(0);
                row.close();

                /**consulta para obtener todas las impresiones en estatus de pendiente de envio*/
                sql = "SELECT COUNT(*) FROM " + TBL_IMPRESIONES_VIGENTE_T + " WHERE estatus = '0'";
                row = db.rawQuery(sql, null);

                row.moveToFirst();
                pendientes = row.getInt(0);
                row.close();
                break;
            case 2:/**si es la vista de VENCIDA*/
                /**consulta para obtener todas las impresiones de tipo vencida*/
                sql = "SELECT COUNT(*) FROM " + TBL_IMPRESIONES_VENCIDA_T;
                row = db.rawQuery(sql, null);

                row.moveToFirst();
                impresiones = row.getInt(0);
                row.close();

                /**consulta para obtener el total de impresiones individuales de vencida*/
                sql = "SELECT * FROM (SELECT COUNT(*) FROM " + TBL_IMPRESIONES_VENCIDA_T + " AS ivi1 LEFT JOIN " +
                        TBL_PRESTAMOS_IND_T + " AS pi ON ivi1.num_prestamo = pi.num_prestamo LEFT JOIN "+
                        TBL_CARTERA_IND_T + " AS ci ON pi.id_cliente = ci.id_cartera WHERE ivi1.num_prestamo LIKE '%-L%' AND ivi1.tipo_impresion = (SELECT i3.tipo_impresion FROM " + TBL_IMPRESIONES_VENCIDA_T + " AS i3 WHERE i3.folio = ivi1.folio ORDER BY i3.tipo_impresion DESC LIMIT 1)" +
                        ") AS impresiones ";
                row = db.rawQuery(sql, null);

                row.moveToFirst();
                individuales = row.getInt(0);
                row.close();

                /**consulta para obtener el total de impresiones individuales de tipo ORIGINAL de vencida*/
                sql = "SELECT * FROM (SELECT * FROM " + TBL_IMPRESIONES_VENCIDA_T + " AS ivi1 LEFT JOIN " +
                        TBL_PRESTAMOS_IND_T + " AS pi ON ivi1.num_prestamo = pi.num_prestamo LEFT JOIN "+
                        TBL_CARTERA_IND_T + " AS ci ON pi.id_cliente = ci.id_cartera WHERE ivi1.num_prestamo LIKE '%-L%' AND ivi1.tipo_impresion = (SELECT i3.tipo_impresion FROM " + TBL_IMPRESIONES_VENCIDA_T + " AS i3 WHERE i3.folio = ivi1.folio ORDER BY i3.tipo_impresion DESC LIMIT 1)" +
                        ") AS impresiones WHERE tipo_impresion = 'O'";

                row = db.rawQuery(sql, null);

                indOriginal = row.getCount();

                row.close();

                /**consulta para obtener el total de impresiones individuales de tipo COPIAS de vencida*/
                sql = "SELECT * FROM (SELECT * FROM " + TBL_IMPRESIONES_VENCIDA_T + " AS ivi1 LEFT JOIN " +
                        TBL_PRESTAMOS_IND_T + " AS pi ON ivi1.num_prestamo = pi.num_prestamo LEFT JOIN "+
                        TBL_CARTERA_IND_T + " AS ci ON pi.id_cliente = ci.id_cartera WHERE ivi1.num_prestamo LIKE '%-L%' AND ivi1.tipo_impresion = (SELECT i3.tipo_impresion FROM " + TBL_IMPRESIONES_VENCIDA_T + " AS i3 WHERE i3.folio = ivi1.folio ORDER BY i3.tipo_impresion DESC LIMIT 1)" +
                        ") AS impresiones WHERE tipo_impresion = 'C'";

                row = db.rawQuery(sql, null);

                indCopias = row.getCount();
                row.close();

                /**consulta para obtener el total de impresiones grupales de vencida*/
                sql = "SELECT * FROM (SELECT COUNT(*) FROM " + TBL_IMPRESIONES_VENCIDA_T + " AS ivi2 LEFT JOIN " +
                        TBL_PRESTAMOS_GPO_T + " AS pg ON ivi2.num_prestamo = pg.num_prestamo LEFT JOIN " +
                        TBL_CARTERA_GPO_T + " AS cg ON pg.id_grupo = cg.id_cartera WHERE ivi2.num_prestamo NOT LIKE '%-L%' AND ivi2.tipo_impresion = (SELECT i2.tipo_impresion FROM "+TBL_IMPRESIONES_VENCIDA_T+" AS i2 WHERE i2.folio = ivi2.folio ORDER BY i2.tipo_impresion DESC LIMIT 1)) AS impresiones";

                row = db.rawQuery(sql, null);

                row.moveToFirst();
                grupales = row.getInt(0);
                row.close();

                /**consulta para obtener el total de impresiones grupales de tipo GRUPAL de vencida*/
                sql = "SELECT * FROM (SELECT * FROM " + TBL_IMPRESIONES_VENCIDA_T + " AS ivi2 LEFT JOIN " +
                        TBL_PRESTAMOS_GPO_T + " AS pg ON ivi2.num_prestamo = pg.num_prestamo LEFT JOIN " +
                        TBL_CARTERA_GPO_T + " AS cg ON pg.id_grupo = cg.id_cartera WHERE ivi2.num_prestamo NOT LIKE '%-L%' AND ivi2.tipo_impresion = (SELECT i2.tipo_impresion FROM "+TBL_IMPRESIONES_VENCIDA_T+" AS i2 WHERE i2.folio = ivi2.folio ORDER BY i2.tipo_impresion DESC LIMIT 1)) AS impresiones WHERE tipo_impresion = 'O'";
                row = db.rawQuery(sql, null);

                gpoOriginal = row.getCount();
                row.close();

                /**consulta para obtener el total de impresiones grupales de tipo COPIAS de vencida*/
                sql = "SELECT * FROM (SELECT * FROM " + TBL_IMPRESIONES_VENCIDA_T + " AS ivi2 LEFT JOIN " +
                        TBL_PRESTAMOS_GPO_T + " AS pg ON ivi2.num_prestamo = pg.num_prestamo LEFT JOIN " +
                        TBL_CARTERA_GPO_T + " AS cg ON pg.id_grupo = cg.id_cartera WHERE ivi2.num_prestamo NOT LIKE '%-L%' AND ivi2.tipo_impresion = (SELECT i2.tipo_impresion FROM "+TBL_IMPRESIONES_VENCIDA_T+" AS i2 WHERE i2.folio = ivi2.folio ORDER BY i2.tipo_impresion DESC LIMIT 1)) AS impresiones WHERE tipo_impresion = 'C'";

                row = db.rawQuery(sql, null);

                gpoCopias = row.getCount();
                row.close();

                /**consulta para obtener todas las impresiones en estatus de enviada*/
                sql = "SELECT COUNT(*) FROM " + TBL_IMPRESIONES_VENCIDA_T + " WHERE estatus = '1'";
                row = db.rawQuery(sql, null);

                row.moveToFirst();
                enviadas = row.getInt(0);
                row.close();

                /**consulta para obtener todas las impresiones en estatus de pendiente de envio*/
                sql = "SELECT COUNT(*) FROM " + TBL_IMPRESIONES_VENCIDA_T + " WHERE estatus = '0'";
                row = db.rawQuery(sql, null);

                row.moveToFirst();
                pendientes = row.getInt(0);
                row.close();
                break;
            case 3:/**si es la vista de REIMPRESION (vigente y vencidas)*/
                /**consulta para obtener todas las reimpresiones realizadas*/
                sql = "SELECT COUNT(*) FROM " + TBL_REIMPRESION_VIGENTE_T;
                row = db.rawQuery(sql, null);

                row.moveToFirst();
                impresiones = row.getInt(0);
                row.close();

                /**consulta para obtener todas las reimpresiones individuales*/
                sql = "SELECT * FROM (SELECT COUNT(*) FROM " + TBL_REIMPRESION_VIGENTE_T + " AS rim1 LEFT JOIN " +
                        TBL_PRESTAMOS_IND_T + " AS pi ON rim1.num_prestamo = pi.num_prestamo LEFT JOIN "+
                        TBL_CARTERA_IND_T + " AS ci ON pi.id_cliente = ci.id_cartera WHERE rim1.num_prestamo LIKE '%-L%' AND rim1.tipo_reimpresion = (SELECT i3.tipo_reimpresion FROM " + TBL_REIMPRESION_VIGENTE_T + " AS i3 WHERE i3.folio = rim1.folio ORDER BY i3.tipo_reimpresion DESC LIMIT 1)" +
                        ") AS impresiones";
                row = db.rawQuery(sql, null);

                row.moveToFirst();
                individuales = row.getInt(0);
                row.close();

                /**consulta para obtener todas las reimpresiones individuales de tipo ORIGINAL*/
                sql = "SELECT * FROM (SELECT * FROM " + TBL_REIMPRESION_VIGENTE_T + " AS rim1 LEFT JOIN " +
                        TBL_PRESTAMOS_IND_T + " AS pi ON rim1.num_prestamo = pi.num_prestamo LEFT JOIN "+
                        TBL_CARTERA_IND_T + " AS ci ON pi.id_cliente = ci.id_cartera WHERE rim1.num_prestamo LIKE '%-L%' AND rim1.tipo_reimpresion = (SELECT i3.tipo_reimpresion FROM " + TBL_REIMPRESION_VIGENTE_T + " AS i3 WHERE i3.folio = rim1.folio ORDER BY i3.tipo_reimpresion DESC LIMIT 1)" +
                        ") AS impresiones WHERE tipo_reimpresion = 'O'";

                row = db.rawQuery(sql, null);

                indOriginal = row.getCount();

                row.close();

                /**consulta para obtener todas las reimpresiones individuales de tipo COPIA*/
                sql = "SELECT * FROM (SELECT * FROM " + TBL_REIMPRESION_VIGENTE_T + " AS rim1 LEFT JOIN " +
                        TBL_PRESTAMOS_IND_T + " AS pi ON rim1.num_prestamo = pi.num_prestamo LEFT JOIN "+
                        TBL_CARTERA_IND_T + " AS ci ON pi.id_cliente = ci.id_cartera WHERE rim1.num_prestamo LIKE '%-L%' AND rim1.tipo_reimpresion = (SELECT i3.tipo_reimpresion FROM " + TBL_REIMPRESION_VIGENTE_T + " AS i3 WHERE i3.folio = rim1.folio ORDER BY i3.tipo_reimpresion DESC LIMIT 1)" +
                        ") AS impresiones WHERE tipo_reimpresion = 'C'";

                row = db.rawQuery(sql, null);

                indCopias = row.getCount();
                row.close();

                /**consulta para obtener todas las reimpresiones grupales*/
                sql = "SELECT * FROM (SELECT COUNT(*) FROM " + TBL_REIMPRESION_VIGENTE_T + " AS rim2 LEFT JOIN " +
                        TBL_PRESTAMOS_GPO_T + " AS pg ON rim2.num_prestamo = pg.num_prestamo LEFT JOIN " +
                        TBL_CARTERA_GPO_T + " AS cg ON pg.id_grupo = cg.id_cartera WHERE rim2.num_prestamo NOT LIKE '%-L%' AND rim2.tipo_reimpresion = (SELECT i2.tipo_reimpresion FROM "+TBL_REIMPRESION_VIGENTE_T+" AS i2 WHERE i2.folio = rim2.folio ORDER BY i2.tipo_reimpresion DESC LIMIT 1)) AS impresiones";

                row = db.rawQuery(sql, null);

                row.moveToFirst();
                grupales = row.getInt(0);
                row.close();

                /**consulta para obtener el total de reimpresiones grupales de tipo ORIGINAL*/
                sql = "SELECT * FROM (SELECT * FROM " + TBL_REIMPRESION_VIGENTE_T + " AS rim2 LEFT JOIN " +
                        TBL_PRESTAMOS_GPO_T + " AS pg ON rim2.num_prestamo = pg.num_prestamo LEFT JOIN " +
                        TBL_CARTERA_GPO_T + " AS cg ON pg.id_grupo = cg.id_cartera WHERE rim2.num_prestamo NOT LIKE '%-L%' AND rim2.tipo_reimpresion = (SELECT i2.tipo_reimpresion FROM "+TBL_REIMPRESION_VIGENTE_T+" AS i2 WHERE i2.folio = rim2.folio ORDER BY i2.tipo_reimpresion DESC LIMIT 1)) AS impresiones WHERE tipo_reimpresion = 'O'";
                row = db.rawQuery(sql, null);

                gpoOriginal = row.getCount();
                row.close();

                /**consulta para obtener el total de reimpresiones grupales de tipo COPIA*/
                sql = "SELECT * FROM (SELECT * FROM " + TBL_REIMPRESION_VIGENTE_T + " AS rim2 LEFT JOIN " +
                        TBL_PRESTAMOS_GPO_T + " AS pg ON rim2.num_prestamo = pg.num_prestamo LEFT JOIN " +
                        TBL_CARTERA_GPO_T + " AS cg ON pg.id_grupo = cg.id_cartera WHERE rim2.num_prestamo NOT LIKE '%-L%' AND rim2.tipo_reimpresion = (SELECT i2.tipo_reimpresion FROM "+TBL_REIMPRESION_VIGENTE_T+" AS i2 WHERE i2.folio = rim2.folio ORDER BY i2.tipo_reimpresion DESC LIMIT 1)) AS impresiones WHERE tipo_reimpresion = 'C'";

                row = db.rawQuery(sql, null);

                gpoCopias = row.getCount();
                row.close();

                /**consulta para obtener el total de impresiones*/
                sql = "SELECT COUNT(*) FROM " + TBL_REIMPRESION_VIGENTE_T + " WHERE estatus = '1'";
                row = db.rawQuery(sql, null);

                row.moveToFirst();
                enviadas = row.getInt(0);
                row.close();

                sql = "SELECT COUNT(*) FROM " + TBL_REIMPRESION_VIGENTE_T + " WHERE estatus = '0'";
                row = db.rawQuery(sql, null);

                row.moveToFirst();
                pendientes = row.getInt(0);
                row.close();
                break;
        }

        tvImpresiones.setText("Impresiones: " + impresiones);
        tvIndividuales.setText("Individuales: " + individuales);
        tvIndOriginal.setText("Originales: " + indOriginal);
        tvIndCopia.setText("Copias: " + indCopias);
        tvGrupales.setText("Grupales: " + grupales);
        tvGpoOriginal.setText("Originales: " + gpoOriginal);
        tvGpoCopia.setText("Copias: " + gpoCopias);
        tvEnviadas.setText("Enviadas: " + enviadas);
        tvPendientes.setText("Pendientes de Envío: " + pendientes);
    }
}
