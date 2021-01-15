package com.sidert.sidertmovil.activities;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.database.DBhelper;

import java.util.Objects;

import static com.sidert.sidertmovil.utils.Constants.TBL_PRESTAMOS;
import static com.sidert.sidertmovil.utils.Constants.TBL_RECIBOS_AGF_CC;
import static com.sidert.sidertmovil.utils.Constants.TBL_RECUPERACION_RECIBOS;

/**clase para ver el resumen de informacion de AGF y CC para ver el total de prestamos, cuantos son individuales, gruaples, enviadas, etc...*/
public class ResumenCCAGF extends AppCompatActivity {

    private Context ctx;

    private Toolbar tbMain;

    private TextView tvPrestamos;
    private TextView tvGrupales;
    private TextView tvIndividuales;
    private TextView tvGestionadas;
    private TextView tvTotalEnviadas;
    private TextView tvPendientesEnvio;
    private TextView tvParcial;
    private TextView tvImpresiones;
    private TextView tvImpresionesO;
    private TextView tvImpresionesC;
    private TextView tvImpresionesEnv;
    private TextView tvImpresionesPen;

    private DBhelper dBhelper;
    private SQLiteDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resumen_c_c_a_g_f);

        ctx = this;

        dBhelper    = new DBhelper(ctx);
        db          = dBhelper.getWritableDatabase();

        tbMain  = findViewById(R.id.tbMain);

        tvPrestamos         = findViewById(R.id.tvPrestamos);
        tvIndividuales      = findViewById(R.id.tvIndividuales);
        tvGrupales          = findViewById(R.id.tvGrupales);
        tvGestionadas       = findViewById(R.id.tvGestionadas);
        tvTotalEnviadas     = findViewById(R.id.tvEnviadas);
        tvPendientesEnvio   = findViewById(R.id.tvPendientes);
        tvParcial           = findViewById(R.id.tvTotalParcial);
        tvImpresiones       = findViewById(R.id.tvImpresiones);
        tvImpresionesO      = findViewById(R.id.tvImpresionesO);
        tvImpresionesC      = findViewById(R.id.tvImpresionesC);
        tvImpresionesEnv    = findViewById(R.id.tvImpresionesEnv);
        tvImpresionesPen    = findViewById(R.id.tvImpresionesPen);

        setSupportActionBar(tbMain);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getResumen();
    }

    /**funcion para obtener el resumen d*/
    private void getResumen(){
        int prestamos = 0, grupales = 0, individuales = 0;
        int gestionadas = 0;
        int enviadas = 0;
        int pendientesEnvio = 0, parcial = 0;
        int impresiones = 0, impresionesO = 0, impresionesC = 0, impresionesEnv = 0, impresionesPen = 0;

        String sql = "";
        Cursor row = null;

        /**consulta para obtener los prestamos(grupales e individuales)*/
        sql = "SELECT * FROM " + TBL_PRESTAMOS;
        row = db.rawQuery(sql, null);
        prestamos = row.getCount();/**coloca el total de prestamo*/
        row.close();

        /**consulta para obtener el total de prestamos grupales*/
        sql = "SELECT * FROM " + TBL_PRESTAMOS + " WHERE grupo_id > ?";
        row = db.rawQuery(sql,new String[]{"1"});
        grupales = row.getCount();/**coloca el total de prestamos grupales*/
        row.close();

        /**consulta para obtener el total de prestamos individuales*/
        sql = "SELECT * FROM " + TBL_PRESTAMOS + " WHERE grupo_id = ?";
        row = db.rawQuery(sql,new String[]{"1"});
        individuales = row.getCount();/**coloca el total de prestamos grupales*/
        row.close();

        /**consulta para obtener el total de gestiones realizadas grupales e individuales*/
        sql = "SELECT * FROM " + TBL_RECUPERACION_RECIBOS + " WHERE estatus IN (?,?)";
        row = db.rawQuery(sql,new String[]{"1","2"});
        gestionadas = row.getCount();/**coloca el total de gestiones grupales e individuales*/
        row.close();

        /**consulta para obtener el total de gestiones realizadas grupales*/
        sql = "SELECT * FROM " + TBL_RECUPERACION_RECIBOS + " WHERE estatus = ?";
        row = db.rawQuery(sql,new String[]{"2"});
        enviadas = row.getCount();/**coloca el total de gestiones grupales*/
        row.close();

        /**consulta para obtener el total de gestiones realizadas individuales*/
        sql = "SELECT * FROM " + TBL_RECUPERACION_RECIBOS + " WHERE estatus = ?";
        row = db.rawQuery(sql,new String[]{"1"});
        pendientesEnvio = row.getCount();/**coloca el total de gestiones individuales*/
        row.close();

        /**Ocupa la consulta anterior solo se cambia el parametro de condicion con 0 para obtener parciales*/
        row = db.rawQuery(sql,new String[]{"0"});
        parcial = row.getCount();/**coloca el total de gestiones parciales*/
        row.close();

        /**consulta para obtener el total de impresiones realizadas*/
        sql = "SELECT * FROM " + TBL_RECIBOS_AGF_CC;
        row = db.rawQuery(sql,null);
        impresiones = row.getCount();/**coloca el total de impresiones*/
        row.close();

        /**consulta para obtener el total de impresiones originales*/
        sql = "SELECT * FROM " + TBL_RECIBOS_AGF_CC + " WHERE tipo_impresion = ?";
        row = db.rawQuery(sql,new String[]{"O"});
        impresionesO = row.getCount();/**coloca el total de impresiones originales*/
        row.close();

        /**se ocupa la consulta anterior solo se cambia el parametro de condicion para obtener las impresiones de tipo copia*/
        row = db.rawQuery(sql,new String[]{"C"});
        impresionesC = row.getCount();/**coloca el total de impresiones de tipo copia*/
        row.close();

        /**consulta para obtener las impresiones en estatus de enviada*/
        sql = "SELECT * FROM " + TBL_RECIBOS_AGF_CC + " WHERE estatus = ?";
        row = db.rawQuery(sql,new String[]{"1"});
        impresionesEnv = row.getCount();/**coloca el total de impresiones en estatus enviada*/
        row.close();

        /**consultas para obtener las impresiones en estatus de pendiente de envio*/
        row = db.rawQuery(sql,new String[]{"0"});
        impresionesPen = row.getCount();/**coloca el total de impresiones en estatus de pendiente de envio*/
        row.close();


        /**coloca los totales para mostrar al usuario*/
        tvPrestamos.setText("Prestamos: "+prestamos);
        tvGrupales.setText("Grupales: "+grupales);
        tvIndividuales.setText("Individuales: "+individuales);
        tvGestionadas.setText("Gestionadas: "+gestionadas);
        tvTotalEnviadas.setText("Enviadas: "+enviadas);
        tvPendientesEnvio.setText("Pendientes: "+pendientesEnvio);

        tvParcial.setText("Parciales: "+parcial);
        tvImpresiones.setText("Impresiones: "+impresiones);
        tvImpresionesO.setText("Originales: "+impresionesO);
        tvImpresionesC.setText("Copias: "+impresionesC);
        tvImpresionesEnv.setText("Enviadas: "+impresionesEnv);
        tvImpresionesPen.setText("Pendientes: "+impresionesPen);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
