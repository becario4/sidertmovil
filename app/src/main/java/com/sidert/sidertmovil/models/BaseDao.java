package com.sidert.sidertmovil.models;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.sidert.sidertmovil.database.DBhelper;
import com.sidert.sidertmovil.models.solicitudes.solicitudind.originacion.Conyugue;

import java.util.List;

public class BaseDao<T>{
    final DBhelper dBhelper;
    final public SQLiteDatabase db;

    public BaseDao(Context ctx)
    {
        this.dBhelper = new DBhelper(ctx);
        this.db = dBhelper.getWritableDatabase();
    }

    protected Cursor findByIdSolicitud(String table, Integer idSolicitud)
    {
        String sql = "" +
            "SELECT * " +
            "FROM " + table + " AS t " +
            "WHERE t.id_solicitud = ? " +
            "ORDER BY t.id_solicitud " +
            "LIMIT 1; "
        ;

        Cursor row = db.rawQuery(sql, new String[]{String.valueOf(idSolicitud)});

        return row;
    }

    protected Cursor findByIdIntegrante(String table, Integer idIntegrante)
    {
        String sql = "" +
                "SELECT * " +
                "FROM " + table + " AS t " +
                "WHERE t.id_integrante = ? " +
                "ORDER BY t.id_integrante " +
                "LIMIT 1; "
                ;

        Cursor row = db.rawQuery(sql, new String[]{String.valueOf(idIntegrante)});

        return row;
    }

    public static <T extends IFillModel> void find(Cursor row, T t)
    {
        if(row.getCount() > 0)
        {
            row.moveToFirst();

            t.fill(row);
        }
        else
        {
            t = null;
        }

        row.close();
    }

    public static <T extends IFillModel> void findAll(Cursor row, List<T> lt)
    {
        if(row.getCount() > 0)
        {
            row.moveToFirst();

            for(int i = 0; i < row.getCount(); i++) {
                lt.get(i).fill(row);

                row.moveToNext();
            }
        }

        row.close();
    }

}
