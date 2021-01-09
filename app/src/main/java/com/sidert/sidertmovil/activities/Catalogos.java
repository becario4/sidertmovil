package com.sidert.sidertmovil.activities;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;

import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.adapters.adapter_catalogos;
import com.sidert.sidertmovil.database.DBhelper;
import com.sidert.sidertmovil.models.ModeloCatalogoGral;
import com.sidert.sidertmovil.utils.Constants;

import java.util.ArrayList;

import static com.sidert.sidertmovil.utils.Constants.COLONIAS;
import static com.sidert.sidertmovil.utils.Constants.ESTADOS;
import static com.sidert.sidertmovil.utils.Constants.ITEM;
import static com.sidert.sidertmovil.utils.Constants.LOCALIDADES;
import static com.sidert.sidertmovil.utils.Constants.OCUPACIONES;
import static com.sidert.sidertmovil.utils.Constants.SECTORES;

/**Clase para visualizar catalogos dinamicos se le manda un valor para saber que catalogo mostrar*/
public class Catalogos extends AppCompatActivity {

    public Context ctx;
    private RecyclerView mRecycler;
    private adapter_catalogos adapter;
    private String tipo_catalogo;
    private DBhelper dBhelper;

    private SearchView searchView;
    private int request_code;
    private String extra = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalogos);
        ctx = this;
        Toolbar tbMain = findViewById(R.id.tbMain);
        mRecycler           = findViewById(R.id.mRecycler);
        mRecycler.setLayoutManager(new LinearLayoutManager(ctx));
        setSupportActionBar(tbMain);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        dBhelper = new DBhelper(ctx);

        Bundle data = getIntent().getExtras();
        if (data != null){
            setTitle(data.getString(Constants.TITULO));
            tipo_catalogo = data.getString(Constants.CATALOGO);
            extra = data.getString(Constants.EXTRA,"");
            request_code = data.getInt(Constants.REQUEST_CODE);
            if (!extra.isEmpty() && tipo_catalogo.equals(COLONIAS))
                getCatalogo(" WHERE cp = '"+extra+"'");
            else if(!extra.isEmpty() && tipo_catalogo.equals(LOCALIDADES))
                getCatalogo(" WHERE id_municipio = '"+extra+"'");
            else
                getCatalogo("");
        } else {
            finish();
        }
    }

    /**Obtiene el catalogo dependiendo el tipo de catalogo qeu quiere mostrar*/
    private void getCatalogo(String value){
        Cursor row;
        ArrayList<ModeloCatalogoGral> catalogo = new ArrayList<>();
        switch (tipo_catalogo){
            case ESTADOS:
                row = dBhelper.getRecords(ESTADOS,value," ORDER BY estado_nombre ASC", null);
                if (row.getCount() > 0){
                    row.moveToFirst();
                    for (int i = 0; i < row.getCount(); i++){
                        ModeloCatalogoGral mCatalogoGral = new ModeloCatalogoGral();
                        mCatalogoGral.setId(row.getString(1));
                        mCatalogoGral.setNombre(row.getString(2));
                        mCatalogoGral.setExtra("");
                        mCatalogoGral.setCatalogo(ESTADOS);
                        catalogo.add(mCatalogoGral);
                        row.moveToNext();
                    }
                }
                break;
            case OCUPACIONES:
                row = dBhelper.getRecords(OCUPACIONES,value," ORDER BY ocupacion_nombre ASC", null);
                if (row.getCount() > 0){
                    row.moveToFirst();
                    for (int i = 0; i < row.getCount(); i++){
                        ModeloCatalogoGral mCatalogoGral = new ModeloCatalogoGral();
                        mCatalogoGral.setId(row.getString(1));
                        mCatalogoGral.setNombre(row.getString(2));
                        mCatalogoGral.setExtra(row.getString(5));
                        mCatalogoGral.setCatalogo(OCUPACIONES);
                        catalogo.add(mCatalogoGral);
                        row.moveToNext();
                    }
                }
                break;
            case Constants.COLONIAS:
                row = dBhelper.getRecords(Constants.COLONIAS,value," ORDER BY colonia_nombre ASC", null);
                if (row.getCount() > 0){
                    row.moveToFirst();
                    for (int i = 0; i < row.getCount(); i++){
                        ModeloCatalogoGral mCatalogoGral = new ModeloCatalogoGral();
                        mCatalogoGral.setId(row.getString(1));
                        mCatalogoGral.setNombre(row.getString(2));
                        mCatalogoGral.setExtra("");
                        mCatalogoGral.setCatalogo(Constants.COLONIAS);
                        catalogo.add(mCatalogoGral);
                        row.moveToNext();
                    }
                }
                break;
            case LOCALIDADES:
                row = dBhelper.getRecords(LOCALIDADES,value," ORDER BY id_municipio ASC", null);
                if (row.getCount() > 0){
                    row.moveToFirst();
                    for (int i = 0; i < row.getCount(); i++){
                        ModeloCatalogoGral mCatalogoGral = new ModeloCatalogoGral();
                        mCatalogoGral.setId(row.getString(1));
                        mCatalogoGral.setNombre(row.getString(2));
                        mCatalogoGral.setExtra("");
                        mCatalogoGral.setCatalogo(LOCALIDADES);
                        catalogo.add(mCatalogoGral);
                        row.moveToNext();
                    }
                }
                break;
            case SECTORES:
                row = dBhelper.getRecords(SECTORES,value," ORDER BY sector_nombre ASC", null);
                if (row.getCount() > 0){
                    row.moveToFirst();
                    for (int i = 0; i < row.getCount(); i++){
                        ModeloCatalogoGral mCatalogoGral = new ModeloCatalogoGral();
                        mCatalogoGral.setId(row.getString(1));
                        mCatalogoGral.setNombre(row.getString(2));
                        mCatalogoGral.setExtra("");
                        mCatalogoGral.setCatalogo(SECTORES);
                        catalogo.add(mCatalogoGral);
                        row.moveToNext();
                    }
                }
                break;
        }

        adapter = new adapter_catalogos(ctx, catalogo, new adapter_catalogos.Event() {
            @Override
            public void FichaOnClick(ModeloCatalogoGral item) {
                Intent data = new Intent();
                switch (item.getCatalogo()){
                    case ESTADOS:
                    case OCUPACIONES:
                    case COLONIAS:
                    case SECTORES:
                    case LOCALIDADES:
                        data.putExtra(ITEM, item);
                        setResult(request_code, data);
                        finish();
                        break;

                }
            }
        });

        mRecycler.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_busqueda, menu);
        menu.getItem(0).setTitle("Buscar");
        /**Funcion para buscar algun elemento en tiempo real*/
        SearchManager searchManager = (SearchManager) ctx.getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setQueryHint("Buscar . . .");
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                InputMethodManager imm = (InputMethodManager)ctx.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getWindow().getDecorView().getRootView().getWindowToken(), 0);

                //getCatalogo(" WHERE estado_nombre like '%"+query+"%'");
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                switch (tipo_catalogo){
                    case ESTADOS:
                        getCatalogo(" WHERE estado_nombre like '%"+newText+"%'");
                        break;
                    case OCUPACIONES:
                        getCatalogo(" WHERE ocupacion_nombre like '%"+newText+"%'");
                        break;
                    case Constants.COLONIAS:
                        getCatalogo(" WHERE colonia_nombre like '%"+newText+"%' AND cp = '"+extra+"'");
                        break;
                    case LOCALIDADES:
                        getCatalogo(" WHERE nombre like '%"+newText+"%' AND id_municipio = '"+extra+"'");
                        break;
                    case SECTORES:
                        getCatalogo(" WHERE sector_nombre like '%"+newText+"%'");
                        break;
                }

                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();

        return super.onOptionsItemSelected(item);
    }
}
