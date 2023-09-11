package com.sidert.sidertmovil.models.solicitudes.solicitudind.originacion;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.sidert.sidertmovil.database.DBhelper;
import com.sidert.sidertmovil.models.BaseDao;

import java.util.ArrayList;
import java.util.List;

public class AvalDao {
    final DBhelper dBhelper;
    final SQLiteDatabase db;

    public AvalDao(Context ctx)
    {
        this.dBhelper = DBhelper.getInstance(ctx);
        this.db = dBhelper.getWritableDatabase();
    }

    public Aval findByIdSolicitud(Integer idSolicitud)
    {
        String sql = "" +
            "SELECT * " +
            "FROM " + Aval.TBL + " AS a " +
            "WHERE a." + Aval.COL_ID_SOLICITUD + " = ? " +
            "ORDER BY a." + Aval.COL_ID_SOLICITUD + " " +
            "LIMIT 1; "
        ;

        Cursor row = db.rawQuery(sql, new String[]{String.valueOf(idSolicitud)});

        Aval aval = new Aval();
        BaseDao.find(row, aval);

        return aval;
    }

    public List<Aval> findAll()
    {
        String sql = "" +
            "SELECT * " +
            "FROM " + Aval.TBL + " AS a "
        ;

        Cursor row = db.rawQuery(sql, new String[]{});

        List<Aval> avales = new ArrayList<>();
        for(int i = 0; i < row.getCount(); i++) avales.add(new Aval());

        BaseDao.findAll(row, avales);

        return avales;
    }
}
