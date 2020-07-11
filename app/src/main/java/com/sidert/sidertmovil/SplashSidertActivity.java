package com.sidert.sidertmovil;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.sidert.sidertmovil.database.DBhelper;
import com.sidert.sidertmovil.utils.Miscellaneous;
import com.sidert.sidertmovil.utils.SessionManager;

import static com.sidert.sidertmovil.utils.Constants.LOGIN_REPORT_T;

public class SplashSidertActivity extends AppCompatActivity {

    private Context ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DBhelper dBhelper = new DBhelper(this);
        SQLiteDatabase db = dBhelper.getWritableDatabase();
        dBhelper.onUpgrade(db, 1, 2);

        setContentView(R.layout.activity_splash_sidert);
        ctx = getApplicationContext();

        SessionManager session = new SessionManager(this);

        //session.setDominio("http://192.168.0.83:", "8080");

        String sql = "SELECT * FROM " + LOGIN_REPORT_T + " ORDER BY login_timestamp DESC limit 1";
        Cursor row = db.rawQuery(sql, null);

        if (row.getCount() > 0){
            row.moveToFirst();

            String[] fechaLogin = row.getString(3).split(" ");
            String[] newFecha = fechaLogin[0].split("-");
            int dias = Miscellaneous.GetDiasAtraso(newFecha[2]+"-"+newFecha[1]+"-"+newFecha[0]);

            if (dias >= 1){
                //session.deleteUser();
                session.setUser(session.getUser().get(0),
                        session.getUser().get(1),
                        session.getUser().get(2),
                        session.getUser().get(3),
                        session.getUser().get(4),
                        session.getUser().get(5),
                        false,
                        session.getUser().get(7),
                        session.getUser().get(8),
                        session.getUser().get(9));
            }
            Log.e("DiasLogin", String.valueOf(dias));
        }

        //Miscellaneous.BorrarDuplicadosGeo(this);

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
