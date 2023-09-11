package com.sidert.sidertmovil.models.serviciossincronizados;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.sidert.sidertmovil.database.DBhelper;

import static com.sidert.sidertmovil.utils.Constants.TBL_SERVICIOS_SINCRONIZADOS;

public class ServicioSincronizadoDao {
    final DBhelper dbHelper;
    final SQLiteDatabase db;

    public ServicioSincronizadoDao(Context ctx){
        this.dbHelper = DBhelper.getInstance(ctx);
        this.db = dbHelper.getWritableDatabase();
    }

    public Long store(ServicioSincronizado servicioSincronizado)
    {
        Long id;
        String sql;
        SQLiteStatement pInsert;

        db.beginTransaction();

        sql = "INSERT INTO " + TBL_SERVICIOS_SINCRONIZADOS + "(_id, nombre, estatus, posicion, ejecutado) " +
                "VALUES(?, ?, ?, ?, ?)"
        ;

        pInsert = db.compileStatement(sql);

        pInsert.bindLong(1, servicioSincronizado.getId());
        pInsert.bindString(2, servicioSincronizado.getNombre());
        pInsert.bindLong(3, servicioSincronizado.getEstatus());
        pInsert.bindLong(4, servicioSincronizado.getPosicion());
        pInsert.bindLong(5, servicioSincronizado.getEjecutado());

        id = pInsert.executeInsert();

        db.setTransactionSuccessful();
        db.endTransaction();

        return id;
    }

    public void update(ServicioSincronizado servicioSincronizado)
    {
        ContentValues cv = new ContentValues();
        cv.put("nombre", servicioSincronizado.getNombre());
        cv.put("estatus", servicioSincronizado.getEstatus());
        cv.put("posicion", servicioSincronizado.getPosicion());
        cv.put("ejecutado", servicioSincronizado.getEjecutado());

        db.update(TBL_SERVICIOS_SINCRONIZADOS, cv, "_id = ?", new String[]{String.valueOf(servicioSincronizado.getId())});
    }

    public void restart()
    {
        ContentValues cv = new ContentValues();
        cv.put("ejecutado", 0);

        db.update(TBL_SERVICIOS_SINCRONIZADOS, cv, "", new String[]{});
    }

    public ServicioSincronizado findById(Integer id)
    {
        ServicioSincronizado servicioSincronizado = null;

        String sql = "SELECT ss.* " +
                "FROM " + TBL_SERVICIOS_SINCRONIZADOS + " ss " +
                "WHERE ss._id = ? "
                ;

        Cursor row = db.rawQuery(sql, new String[]{String.valueOf(id)});

        if(row.getCount() > 0)
        {
            row.moveToFirst();
            servicioSincronizado = new ServicioSincronizado();
            fill(row, servicioSincronizado);
        }

        row.close();

        return servicioSincronizado;
    }

    public ServicioSincronizado findNextToSynchronize()
    {
        ServicioSincronizado servicioSincronizado = null;

        String sql = "SELECT ss.* " +
            "FROM " + TBL_SERVICIOS_SINCRONIZADOS + " ss " +
            "WHERE ss.ejecutado = 0 " +
            "AND ss.estatus = 1 " +
            "ORDER BY ss.posicion " +
            "LIMIT 1"
        ;

        Cursor row = db.rawQuery(sql, new String[]{});

        if(row.getCount() > 0)
        {
            row.moveToFirst();
            servicioSincronizado = new ServicioSincronizado();
            fill(row, servicioSincronizado);
        }

        row.close();

        return servicioSincronizado;
    }

    private void fill(Cursor row, ServicioSincronizado servicioSincronizado)
    {
        servicioSincronizado.setId(row.getInt(0));
        servicioSincronizado.setNombre(row.getString(1));
        servicioSincronizado.setEstatus(row.getInt(2));
        servicioSincronizado.setPosicion(row.getInt(3));
        servicioSincronizado.setEjecutado(row.getInt(4));
    }
}
