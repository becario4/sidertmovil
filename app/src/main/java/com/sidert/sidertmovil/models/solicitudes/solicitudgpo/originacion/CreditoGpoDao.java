package com.sidert.sidertmovil.models.solicitudes.solicitudgpo.originacion;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.sidert.sidertmovil.database.DBhelper;

import static com.sidert.sidertmovil.utils.Constants.TBL_CREDITO_GPO;

public class CreditoGpoDao {
    final DBhelper dbHelper;
    final SQLiteDatabase db;

    public CreditoGpoDao(Context ctx){
        this.dbHelper = DBhelper.getInstance(ctx);
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

            Fill(row, credito);
        }

        row.close();

        return credito;
    }

    public CreditoGpo findByIdSolicitud(Integer idSolicitud)
    {
        CreditoGpo credito = null;

        String sql = "" +
                "SELECT " +
                "* " +
                "FROM " + TBL_CREDITO_GPO + " AS c " +
                "WHERE c.id_solicitud = ? "
                ;
        Cursor row = db.rawQuery(sql, new String[]{String.valueOf(idSolicitud)});

        if(row.getCount() > 0)
        {
            row.moveToFirst();

            credito = new CreditoGpo();

            Fill(row, credito);
        }

        row.close();

        return credito;
    }

    public void update(CreditoGpo creditoGpo)
    {
        ContentValues cv = new ContentValues();

        cv.put("plazo", creditoGpo.getPlazo());
        cv.put("periodicidad", creditoGpo.getPeriodicidad());
        cv.put("fecha_desembolso", creditoGpo.getFechaDesembolso());
        cv.put("dia_desembolso", creditoGpo.getDiaDesembolso());
        cv.put("hora_visita", creditoGpo.getHoraVisita());
        cv.put("estatus_completado", creditoGpo.getEstatusCompletado());

        db.update(TBL_CREDITO_GPO, cv, "id = ?", new String[]{String.valueOf(creditoGpo.getId())});
    }

    private void Fill(Cursor row, CreditoGpo creditoGpo)
    {
        creditoGpo.setId(row.getInt(0));
        creditoGpo.setIdSolicitud(row.getInt(1));
        creditoGpo.setNombreGrupo(row.getString(2));
        creditoGpo.setPlazo(row.getString(3));
        creditoGpo.setPeriodicidad(row.getString(4));
        creditoGpo.setFechaDesembolso(row.getString(5));
        creditoGpo.setDiaDesembolso(row.getString(6));
        creditoGpo.setHoraVisita(row.getString(7));
        creditoGpo.setEstatusCompletado(row.getInt(8));

    }
}
