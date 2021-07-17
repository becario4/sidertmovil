package com.sidert.sidertmovil.models.solicitudes.solicitudgpo;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.sidert.sidertmovil.database.DBhelper;

import static com.sidert.sidertmovil.utils.Constants.TBL_CREDITO_GPO;

public class CreditoGpoDao {
    final DBhelper dbHelper;
    final SQLiteDatabase db;

    public CreditoGpoDao(Context ctx){
        this.dbHelper = new DBhelper(ctx);
        this.db = dbHelper.getWritableDatabase();
    }

    public CreditoGpo findById(Integer id)
    {
        CreditoGpo credito = null;

        String sql = "" +
                "SELECT " +
                "* " +
                "FROM " + TBL_CREDITO_GPO + " AS c " +
                "WHERE c.id = ? "
                ;
        Cursor row = db.rawQuery(sql, new String[]{String.valueOf(id)});

        if(row.getCount() > 0)
        {
            row.moveToFirst();

            credito = new CreditoGpo();

            credito.setId(row.getInt(0));
            credito.setIdSolicitud(row.getInt(1));
            credito.setNombreGrupo(row.getString(2));
            credito.setPlazo(row.getString(3));
            credito.setPeriodicidad(row.getString(4));
            credito.setFechaDesembolso(row.getString(5));
            credito.setDisDesembolso(row.getString(6));
            credito.setHoraVisita(row.getString(7));
            credito.setEstatusCompletado(row.getInt(8));
        }

        row.close();

        return credito;
    }
}
