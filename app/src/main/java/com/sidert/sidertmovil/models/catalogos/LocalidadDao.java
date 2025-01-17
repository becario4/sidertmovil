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
        this.dbHelper = DBhelper.getInstance(ctx);
        this.db = dbHelper.getWritableDatabase();
    }

    public long store(Localidad localidad)
    {
        long id = 0;

        String sql;
        SQLiteStatement pInsert;

        db.beginTransaction();

        sql = "INSERT INTO " + tbl_localidades + "( " +
                "id," +
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
                Localidad localidad = new Localidad();

                Fill(row, localidad);

                localidades.add(localidad);

                row.moveToNext();
            }
        }

        row.close();

        return localidades;
    }

    public List<Localidad> findAllByIdMunicipio(Integer idMunicipio)
    {
        List<Localidad> localidades = new ArrayList<>();

        String sql = "SELECT l.* " +
            "FROM " + tbl_localidades + " AS l " +
            "WHERE l.id_municipio = ? "
        ;

        Cursor row = db.rawQuery(sql, new String[]{String.valueOf(idMunicipio)});

        if(row.getCount() > 0)
        {
            row.moveToFirst();

            for(int i = 0; i < row.getCount(); i++)
            {
                Localidad localidad = new Localidad();

                Fill(row, localidad);

                localidades.add(localidad);

                row.moveToNext();
            }
        }

        row.close();

        return localidades;
    }

    public Localidad findByLocalidadId(Integer localidadId)
    {
        Localidad localidad = null;

        String sql = "SELECT * FROM " + tbl_localidades + " WHERE id_localidad = ?";

        Cursor row = db.rawQuery(sql, new String[]{String.valueOf(localidadId)});

        if(row.getCount() > 0)
        {
            row.moveToFirst();

            localidad = new Localidad();

            Fill(row, localidad);
        }

        row.close();

        return localidad;
    }

    private void Fill(Cursor row, Localidad localidad)
    {
        localidad.setId(row.getInt(0));
        localidad.setLocalidadId(row.getInt(1));
        localidad.setNombre(row.getString(2));
        localidad.setMunicipioId(row.getInt(3));
    }
}
