package com.sidert.sidertmovil;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

import com.sidert.sidertmovil.database.DBhelper;
import com.sidert.sidertmovil.utils.Constants;
import com.crashlytics.android.Crashlytics;
import com.sidert.sidertmovil.utils.Miscellaneous;
import com.sidert.sidertmovil.utils.SessionManager;

import io.fabric.sdk.android.Fabric;

import static com.sidert.sidertmovil.utils.Constants.LOGIN_REPORT_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_CARTERA_GPO_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_CARTERA_IND_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_PRESTAMOS_GPO_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_PRESTAMOS_IND_T;

public class SplashSidertActivity extends AppCompatActivity {

    private Context ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        DBhelper dBhelper = new DBhelper(this);
        SQLiteDatabase db = dBhelper.getWritableDatabase();
        dBhelper.onUpgrade(db, 1, 2);

        /*if (Constants.ENVIROMENT)
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);*/
        setContentView(R.layout.activity_splash_sidert);
        ctx = getApplicationContext();

        SessionManager session = new SessionManager(this);

        String sqlPrestamos = "SELECT * FROM (SELECT ci._id, ci.id_cartera, coalesce(pi.id_prestamo, 'no existe') AS id_prestamo, 'INDIVIDUAL' AS tipo FROM " + TBL_CARTERA_IND_T + " AS ci LEFT JOIN "+TBL_PRESTAMOS_IND_T+" AS pi ON pi.id_cliente = ci.id_cartera UNION SELECT cg._id, cg.id_cartera, coalesce(pg.id_prestamo, 'no existe') as id_prestamo, 'GRUPAL' AS tipo FROM "+TBL_CARTERA_GPO_T+" AS cg LEFT JOIN "+TBL_PRESTAMOS_GPO_T +" AS pg ON pg.id_grupo = cg.id_cartera) AS cartera";
        Cursor rowPrestamos = db.rawQuery(sqlPrestamos, null);
        Log.e("CountPrestamo", String.valueOf(rowPrestamos.getCount()));
        if (rowPrestamos.getCount() > 0){
            rowPrestamos.moveToFirst();
            for (int i = 0; i < rowPrestamos.getCount(); i++){
                Log.e("-", "_----------------------------------------");
                Log.e(rowPrestamos.getColumnName(0), rowPrestamos.getString(0) );
                Log.e(rowPrestamos.getColumnName(1), rowPrestamos.getString(1) );
                Log.e(rowPrestamos.getColumnName(2), rowPrestamos.getString(2) );
                Log.e(rowPrestamos.getColumnName(3), rowPrestamos.getString(3) );
                Log.e("-", "_----------------------------------------");
                rowPrestamos.moveToNext();
            }
        }



        String sql = "SELECT * FROM " + LOGIN_REPORT_T + " ORDER BY login_timestamp DESC limit 1";
        Cursor row = db.rawQuery(sql, null);

        if (row.getCount() > 0){
            row.moveToFirst();

            String[] fechaLogin = row.getString(3).split(" ");
            String[] newFecha = fechaLogin[0].split("-");
            int dias = Miscellaneous.GetDiasAtraso(newFecha[2]+"-"+newFecha[1]+"-"+newFecha[0]);

            if (dias >= 1){
                session.deleteUser();
            }
            Log.e("DiasLogin", String.valueOf(dias));
        }

        Miscellaneous.BorrarDuplicadosGeo(this);

        Handler handler_home=new Handler();
        handler_home.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent start = new Intent(ctx, MainActivity.class);
                start.putExtra("login", false);
                startActivity(start);
                finish();
            }
        },3000);


    }
}
