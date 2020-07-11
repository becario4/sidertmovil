package com.sidert.sidertmovil.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.sidert.sidertmovil.R;

import java.util.Objects;

public class AcercaDe extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acerca_de);

        TextView tvAboutSIDERT   = findViewById(R.id.tvAboutSIDERT);
        Toolbar tbMain = findViewById(R.id.tbMain);


        setSupportActionBar(tbMain);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        if (getIntent() != null && getIntent().getBooleanExtra("FLAG",false)) {
            setTitle("Aviso de privacidad");
            tvAboutSIDERT.setText(this.getResources().getString(R.string.privacy_polices));
        }
        else {
            setTitle("Acerca de SIDERT");
            tvAboutSIDERT.setText(this.getResources().getString(R.string.about_sidert));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
