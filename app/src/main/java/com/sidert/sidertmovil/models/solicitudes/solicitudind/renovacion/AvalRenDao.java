package com.sidert.sidertmovil.models.solicitudes.solicitudind.renovacion;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.sidert.sidertmovil.database.DBhelper;
import com.sidert.sidertmovil.models.BaseDao;

import java.util.ArrayList;
import java.util.List;

public class AvalRenDao {
    final DBhelper dBhelper;
    final SQLiteDatabase db;

    public AvalRenDao(Context ctx)
    {
        this.dBhelper = new DBhelper(ctx);
        this.db = dBhelper.getWritableDatabase();
    }

    public AvalRen findByIdSolicitud(Integer idSolicitud)
    {
        String sql = "" +
                "SELECT * " +
                "FROM " + AvalRen.TBL + " AS a " +
                "WHERE a." + AvalRen.COL_ID_SOLICITUD + " = ? " +
                "ORDER BY a." + AvalRen.COL_ID_SOLICITUD + " " +
                "LIMIT 1; "
                ;

        Cursor row = db.rawQuery(sql, new String[]{String.valueOf(idSolicitud)});

        AvalRen avalRen = new AvalRen();
        BaseDao.find(row, avalRen);

        return avalRen;
    }

    public List<AvalRen> findAll()
    {
        String sql = "" +
                "SELECT * " +
                "FROM " + AvalRen.TBL + " AS a "
                ;

        Cursor row = db.rawQuery(sql, new String[]{});

        List<AvalRen> avalesRen = new ArrayList<>();
        for(int i = 0; i < row.getCount(); i++) avalesRen.add(new AvalRen());

        BaseDao.findAll(row, avalesRen);

        return avalesRen;
    }
}
