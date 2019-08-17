package com.sidert.sidertmovil.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.sidert.sidertmovil.R;

public class OriginacionI extends AppCompatActivity {

    private Toolbar tbMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_originacion_i);

        tbMain              = findViewById(R.id.tbMain);

        setSupportActionBar(tbMain);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("Originación Individual");
    }
}
