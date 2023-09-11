package com.sidert.sidertmovil.models.solicitudes.solicitudind.originacion;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.sidert.sidertmovil.database.DBhelper;
import com.sidert.sidertmovil.models.BaseDao;

import java.util.ArrayList;
import java.util.List;

public class ConyugueDao {
    final DBhelper dBhelper;
    final SQLiteDatabase db;

    public ConyugueDao(Context ctx)
    {
        this.dBhelper = DBhelper.getInstance(ctx);
        this.db = dBhelper.getWritableDatabase();
    }

    public Conyugue findByIdSolicitud(Integer idSolicitud)
    {
        String sql = "" +
                "SELECT * " +
                "FROM " + Conyugue.TBL + " AS c " +
                "WHERE c." + Conyugue.COL_ID_SOLICITUD + " = ? " +
                "ORDER BY c." + Conyugue.COL_ID_SOLICITUD + " " +
                "LIMIT 1; "
                ;

        Cursor row = db.rawQuery(sql, new String[]{String.valueOf(idSolicitud)});

        Conyugue conyugue = new Conyugue();
        BaseDao.find(row, conyugue);

        return conyugue;
    }

    public List<Conyugue> findAll()
    {
        String sql = "" +
            "SELECT * " +
            "FROM " + Conyugue.TBL + " AS c "
        ;

        Cursor row = db.rawQuery(sql, new String[]{});

        List<Conyugue> conyugues = new ArrayList<>();
        for(int i = 0; i < row.getCount(); i++) conyugues.add(new Conyugue());

        BaseDao.findAll(row, conyugues);

        return conyugues;
    }
}
