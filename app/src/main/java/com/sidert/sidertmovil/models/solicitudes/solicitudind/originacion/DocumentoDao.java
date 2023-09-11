package com.sidert.sidertmovil.models.solicitudes.solicitudind.originacion;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.sidert.sidertmovil.database.DBhelper;
import com.sidert.sidertmovil.models.BaseDao;

public class DocumentoDao {
    final DBhelper dBhelper;
    final SQLiteDatabase db;

    public DocumentoDao(Context ctx)
    {
        this.dBhelper = DBhelper.getInstance(ctx);
        this.db = dBhelper.getWritableDatabase();
    }

    public Documento findByIdSolicitud(Integer idSolicitud)
    {
        String sql = "" +
            "SELECT * " +
            "FROM " + Documento.TBL + " AS d " +
            "WHERE d." + Documento.COL_ID_SOLICITUD + " = ? " +
            "ORDER BY d." + Documento.COL_ID_SOLICITUD + " " +
            "LIMIT 1; "
        ;

        Cursor row = db.rawQuery(sql, new String[]{String.valueOf(idSolicitud)});

        Documento documento = new Documento();
        BaseDao.find(row, documento);
        return documento;
    }
}
