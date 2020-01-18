package com.sidert.sidertmovil;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

import com.sidert.sidertmovil.database.DBhelper;
import com.sidert.sidertmovil.utils.Constants;
import com.crashlytics.android.Crashlytics;
import com.sidert.sidertmovil.utils.Miscellaneous;

import io.fabric.sdk.android.Fabric;

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

        Miscellaneous.BorrarDuplicadosGeo(this);

        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent start = new Intent(ctx, MainActivity.class);
                startActivity(start);
                finish();
            }
        },3000);


    }
}
