package com.sidert.sidertmovil.models.catalogos;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.sidert.sidertmovil.database.DBhelper;

import java.util.ArrayList;
import java.util.List;

public class LocalidadDao {
    final DBhelper dbHelper;
    final SQLiteDatabase db;
    private String tbl_localidades = "localidades";

    public LocalidadDao(Context ctx)
    {
        this.dbHelper = new DBhelper(ctx);
        this.db = dbHelper.getWritableDatabase();
    }

    public long store(Localidad localidad)
    {
        long id = 0;

        String sql;
        SQLiteStatement pInsert;

        db.beginTransaction();

        sql = "INSERT INTO " + tbl_localidades + "( " +
                "id_localidad," +
                "nombre," +
                "id_municipio" +
                ") VALUES(?, ?, ?)";

        pInsert = db.compileStatement(sql);

        pInsert.bindString(1, String.valueOf(localidad.getLocalidadId()));
        pInsert.bindString(2, localidad.getNombre());
        pInsert.bindString(3, String.valueOf(localidad.getMunicipioId()));

        id = pInsert.executeInsert();

        db.setTransactionSuccessful();
        db.endTransaction();

        return id;
    }

    public void update(int id, Localidad localidad)
    {

    }

    public List<Localidad> findAll()
    {
        List<Localidad> localidades = new ArrayList<>();

        String sql = "SELECT * FROM " + tbl_localidades;

        Cursor row = db.rawQuery(sql, new String[]{});

        if(row.getCount() > 0)
        {
            row.moveToFirst();

            for(int i = 0; i < row.getCount(); i++)
            {
                Localidad l = new Localidad();

                l.setId(row.getInt(0));
                l.setLocalidadId(row.getInt(1));
                l.setNombre(row.getString(2));
                l.setMunicipioId(row.getInt(3));

                localidades.add(l);

                row.moveToNext();
            }
        }

        row.close();

        return localidades;
    }
}
