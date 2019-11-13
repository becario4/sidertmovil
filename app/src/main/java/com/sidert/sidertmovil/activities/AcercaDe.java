package com.sidert.sidertmovil.activities;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.MenuItem;
import android.widget.TextView;

import com.sidert.sidertmovil.R;

public class AcercaDe extends AppCompatActivity {

    private Toolbar tbMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acerca_de);

        TextView tvAboutSIDERT   = findViewById(R.id.tvAboutSIDERT);
        tbMain              = findViewById(R.id.tbMain);


        setSupportActionBar(tbMain);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
