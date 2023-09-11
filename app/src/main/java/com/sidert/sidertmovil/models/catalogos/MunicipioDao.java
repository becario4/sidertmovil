package com.sidert.sidertmovil.models.catalogos;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.sidert.sidertmovil.database.DBhelper;

public class MunicipioDao {
    final DBhelper dbHelper;
    final SQLiteDatabase db;

    private String tbl_municiios = "municipios";

    public MunicipioDao(Context ctx)
    {
        this.dbHelper = DBhelper.getInstance(ctx);
        this.db = dbHelper.getWritableDatabase();
    }

    public long store(Municipio municipio)
    {
        long id = 0;

        String sql;
        SQLiteStatement pInsert;

        db.beginTransaction();

        sql = "INSERT INTO " + tbl_municiios + "( " +
                "municipio_id," +
                "municipio_nombre," +
                "estado_id" +
                ") VALUES(?, ?, ?)";

        pInsert = db.compileStatement(sql);

        pInsert.bindString(1, String.valueOf(municipio.getMunicipioId()));
        pInsert.bindString(2, municipio.getNombre());
        pInsert.bindString(3, String.valueOf(municipio.getEstadoId()));

        id = pInsert.executeInsert();

        db.setTransactionSuccessful();
        db.endTransaction();

        return id;
    }

    public Municipio findByMunicipioId(Integer municipioId)
    {
        Municipio municipio = null;

        String sql = "SELECT * FROM " + tbl_municiios + " WHERE municipio_id = ?";

        Cursor row = db.rawQuery(sql, new String[]{String.valueOf(municipioId)});

        if(row.getCount() > 0)
        {
            row.moveToFirst();

            municipio = new Municipio();

            municipio.setId(row.getInt(0));
            municipio.setMunicipioId(row.getInt(1));
            municipio.setNombre(row.getString(2));
            municipio.setEstadoId(row.getInt(3));
        }

        row.close();

        return municipio;
    }


}
