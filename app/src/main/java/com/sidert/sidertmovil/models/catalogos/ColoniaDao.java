package com.sidert.sidertmovil.models.catalogos;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.sidert.sidertmovil.database.DBhelper;

import java.util.ArrayList;
import java.util.List;

public class ColoniaDao {
    final DBhelper dbHelper;
    final SQLiteDatabase db;

    private String tbl_colonias = "cat_colonias";

    public ColoniaDao(Context ctx)
    {
        this.dbHelper = DBhelper.getInstance(ctx);
        this.db = dbHelper.getWritableDatabase();
    }

    public long store(Colonia colonia)
    {
        long id = 0;

        String sql;
        SQLiteStatement pInsert;

        db.beginTransaction();

        sql = "INSERT INTO " + tbl_colonias + "( " +
                "colonia_id," +
                "colonia_nombre," +
                "cp," +
                "municipio_id" +
                ") VALUES(?, ?, ?, ?)";

        pInsert = db.compileStatement(sql);

        pInsert.bindString(1, String.valueOf(colonia.getColoniaId()));
        pInsert.bindString(2, colonia.getNombre());
        pInsert.bindString(3, String.valueOf(colonia.getCp()));
        pInsert.bindString(4, String.valueOf(colonia.getMunicipioId()));

        id = pInsert.executeInsert();

        db.setTransactionSuccessful();
        db.endTransaction();

        return id;
    }

    public Colonia findByColoniaId(Integer coloniaId)
    {
        Colonia colonia = null;

        String sql = "SELECT * FROM " + tbl_colonias + " WHERE colonia_id = ?";

        Cursor row = db.rawQuery(sql, new String[]{String.valueOf(coloniaId)});

        if(row.getCount() > 0)
        {
            row.moveToFirst();

            colonia = new Colonia();

            Fill(row,  colonia);

        }

        row.close();

        return colonia;
    }

    public List<Colonia> findAllByCp(String cp)
    {
        List<Colonia> colonias = new ArrayList<>();

        String sql = "SELECT * FROM " + tbl_colonias + " WHERE cp = ?";

        Cursor row = db.rawQuery(sql, new String[]{cp});

        if(row.getCount() > 0)
        {
            row.moveToFirst();

            for(int i = 0; i < row.getCount(); i++) {
                Colonia colonia = new Colonia();

                Fill(row, colonia);

                colonias.add(colonia);

                row.moveToNext();
            }

        }

        row.close();

        return colonias;
    }

    private void Fill(Cursor row, Colonia colonia)
    {
        colonia.setId(row.getInt(0));
        colonia.setColoniaId(row.getInt(1));
        colonia.setNombre(row.getString(2));
        colonia.setCp(row.getInt(3));
        colonia.setMunicipioId(row.getInt(4));
    }
}
