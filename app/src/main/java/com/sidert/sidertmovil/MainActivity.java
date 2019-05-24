package com.sidert.sidertmovil;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

import com.sidert.sidertmovil.activities.Login;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_main);

        Intent i;

        if (true) {
            i = new Intent(getApplication(), Home.class);
            startActivity(i);
            finish();
        }
        else{
            i = new Intent(getApplication(), Login.class);
            startActivity(i);
            finish();
        }
    }
}
