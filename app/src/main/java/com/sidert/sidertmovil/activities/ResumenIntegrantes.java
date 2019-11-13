package com.sidert.sidertmovil.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.sidert.sidertmovil.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ResumenIntegrantes extends AppCompatActivity {

    private Context ctx;

    private Toolbar tbMain;

    private ListView lvIntegrantes;

    private JSONArray integrantes;
    ArrayList<String> resumen = new ArrayList<String>();

    private String total = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resumen_integrantes);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        ctx = this;

        tbMain          = findViewById(R.id.tbMain);
        lvIntegrantes   = findViewById(R.id.lvIntegrantes);

        setSupportActionBar(tbMain);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        total = getIntent().getStringExtra("TOTAL");

        try {
            integrantes = new JSONArray(getIntent().getStringExtra("INTEGRANTES"));
            for (int i = 0; i < integrantes.length(); i++){
                JSONObject item = integrantes.getJSONObject(i);
                resumen.add(item.getString( "nombre") + ": $" + item.getString("pago"));
            }
            ArrayAdapter<String> arrayAdapter =
                    new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, resumen);
            // Set The Adapter
            lvIntegrantes.setAdapter(arrayAdapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent i_result = new Intent();
                i_result.putExtra("TOTAL", total);
                setResult(RESULT_OK, i_result);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent i_result = new Intent();
        i_result.putExtra("TOTAL", total);
        setResult(RESULT_OK, i_result);
        super.onBackPressed();
    }
}
