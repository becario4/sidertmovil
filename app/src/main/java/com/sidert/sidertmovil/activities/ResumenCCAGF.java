package com.sidert.sidertmovil.activities;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.database.DBhelper;

import java.util.Objects;

import static com.sidert.sidertmovil.utils.Constants.TBL_PRESTAMOS;
import static com.sidert.sidertmovil.utils.Constants.TBL_RECIBOS_AGF_CC;
import static com.sidert.sidertmovil.utils.Constants.TBL_RECUPERACION_RECIBOS;

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

    private void getResumen(){
        int prestamos = 0, grupales = 0, individuales = 0;
        int gestionadas = 0;
        int enviadas = 0;
        int pendientesEnvio = 0, parcial = 0;
        int impresiones = 0, impresionesO = 0, impresionesC = 0, impresionesEnv = 0, impresionesPen = 0;

        String sql = "";
        Cursor row = null;

        sql = "SELECT * FROM " + TBL_PRESTAMOS;
        row = db.rawQuery(sql, null);
        prestamos = row.getCount();
        row.close();

        sql = "SELECT * FROM " + TBL_PRESTAMOS + " WHERE grupo_id > ?";
        row = db.rawQuery(sql,new String[]{"1"});
        grupales = row.getCount();
        row.close();

        sql = "SELECT * FROM " + TBL_PRESTAMOS + " WHERE grupo_id = ?";
        row = db.rawQuery(sql,new String[]{"1"});
        individuales = row.getCount();
        row.close();

        sql = "SELECT * FROM " + TBL_RECUPERACION_RECIBOS + " WHERE estatus IN (?,?)";
        row = db.rawQuery(sql,new String[]{"1","2"});
        gestionadas = row.getCount();
        row.close();

        sql = "SELECT * FROM " + TBL_RECUPERACION_RECIBOS + " WHERE estatus = ?";
        row = db.rawQuery(sql,new String[]{"2"});
        enviadas = row.getCount();
        row.close();

        sql = "SELECT * FROM " + TBL_RECUPERACION_RECIBOS + " WHERE estatus = ?";
        row = db.rawQuery(sql,new String[]{"1"});
        pendientesEnvio = row.getCount();
        row.close();

        row = db.rawQuery(sql,new String[]{"0"});
        parcial = row.getCount();
        row.close();

        sql = "SELECT * FROM " + TBL_RECIBOS_AGF_CC;
        row = db.rawQuery(sql,null);
        impresiones = row.getCount();
        row.close();

        sql = "SELECT * FROM " + TBL_RECIBOS_AGF_CC + " WHERE tipo_impresion = ?";
        row = db.rawQuery(sql,new String[]{"O"});
        impresionesO = row.getCount();
        row.close();

        row = db.rawQuery(sql,new String[]{"C"});
        impresionesC = row.getCount();
        row.close();

        sql = "SELECT * FROM " + TBL_RECIBOS_AGF_CC + " WHERE estatus = ?";
        row = db.rawQuery(sql,new String[]{"1"});
        impresionesEnv = row.getCount();
        row.close();

        row = db.rawQuery(sql,new String[]{"0"});
        impresionesPen = row.getCount();
        row.close();

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
