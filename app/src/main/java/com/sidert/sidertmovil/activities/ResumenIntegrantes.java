package com.sidert.sidertmovil.activities;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.database.DBhelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Locale;

import static com.sidert.sidertmovil.utils.Constants.ENVIROMENT;
import static com.sidert.sidertmovil.utils.Constants.ID_GESTION;
import static com.sidert.sidertmovil.utils.Constants.ID_PRESTAMO;
import static com.sidert.sidertmovil.utils.Constants.TBL_MIEMBROS_PAGOS;
import static com.sidert.sidertmovil.utils.Constants.TBL_MIEMBROS_PAGOS_T;
import static com.sidert.sidertmovil.utils.Constants.TOTAL;

public class ResumenIntegrantes extends AppCompatActivity {

    private Context ctx;

    private Toolbar tbMain;

    private TextView tvTotal;
    private ListView lvIntegrantes;

    ArrayList<String> resumen = new ArrayList<String>();

    private String total = "";
    DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
    private DecimalFormat dFormat = new DecimalFormat("#.00",symbols);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resumen_integrantes);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        ctx = this;

        tbMain          = findViewById(R.id.tbMain);
        tvTotal         = findViewById(R.id.tvTotal);
        lvIntegrantes   = findViewById(R.id.lvIntegrantes);

        setSupportActionBar(tbMain);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        total = getIntent().getStringExtra(TOTAL);
        tvTotal.setText("Total: $" + dFormat.format(Double.parseDouble(total)));
        String id_prestamo =  getIntent().getStringExtra(ID_PRESTAMO);
        String id_gestion =  getIntent().getStringExtra(ID_GESTION);
        DBhelper dBhelper = new DBhelper(ctx);

        Cursor row;
        if (ENVIROMENT)
            row = dBhelper.getRecords(TBL_MIEMBROS_PAGOS, " WHERE id_prestamo = ? AND id_gestion = ?", "", new String[]{id_prestamo, id_gestion});
        else
            row = dBhelper.getRecords(TBL_MIEMBROS_PAGOS_T, " WHERE id_prestamo = ? AND id_gestion = ?", "", new String[]{id_prestamo, id_gestion});

        if (row.getCount() > 0){
            row.moveToFirst();

            for (int i = 0; i < row.getCount(); i++){
                resumen.add(row.getString(4) + ": $" + row.getString(6));
                row.moveToNext();
            }
            ArrayAdapter<String> arrayAdapter =
                    new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, resumen);
            lvIntegrantes.setAdapter(arrayAdapter);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent i_result = new Intent();
                i_result.putExtra(TOTAL, total);
                setResult(RESULT_OK, i_result);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent i_result = new Intent();
        i_result.putExtra(TOTAL, total);
        setResult(RESULT_OK, i_result);
        super.onBackPressed();
    }
}
