package com.sidert.sidertmovil.models.solicitudes.solicitudgpo.renovacion;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.sidert.sidertmovil.database.DBhelper;

import static com.sidert.sidertmovil.utils.Constants.TBL_CREDITO_GPO_REN;

public class CreditoGpoRenDao {
    final DBhelper dbHelper;
    final SQLiteDatabase db;

    public CreditoGpoRenDao(Context ctx){
        this.dbHelper = new DBhelper(ctx);
        this.db = dbHelper.getWritableDatabase();
    }

    public CreditoGpoRen findById(Integer id)
    {
        CreditoGpoRen credito = null;

        String sql = "" +
            "SELECT " +
            "* " +
            "FROM " + TBL_CREDITO_GPO_REN + " AS c " +
            "WHERE c.id = ? "
        ;
        Cursor row = db.rawQuery(sql, new String[]{String.valueOf(id)});

        if(row.getCount() > 0)
        {
            row.moveToFirst();

            credito = new CreditoGpoRen();

            credito.setId(row.getInt(0));
            credito.setIdSolicitud(row.getInt(1));
            credito.setNombreGrupo(row.getString(2));
            credito.setPlazo(row.getString(3));
            credito.setPeriodicidad(row.getString(4));
            credito.setFechaDesembolso(row.getString(5));
            credito.setDiaDesembolso(row.getString(6));
            credito.setHoraVisita(row.getString(7));
            credito.setEstatusCompletado(row.getInt(8));
            credito.setObservaciones(row.getString(9));
            credito.setCiclo(row.getString(10));
            credito.setGrupoId(row.getString(11));
        }

        row.close();

        return credito;
    }

    public CreditoGpoRen findByIdSolicitud(Integer idSolicitud)
    {
        CreditoGpoRen credito = null;

        String sql = "" +
                "SELECT " +
                "* " +
                "FROM " + TBL_CREDITO_GPO_REN + " AS c " +
                "WHERE c.id_solicitud = ? "
                ;
        Cursor row = db.rawQuery(sql, new String[]{String.valueOf(idSolicitud)});

        if(row.getCount() > 0)
        {
            row.moveToFirst();

            credito = new CreditoGpoRen();

            credito.setId(row.getInt(0));
            credito.setIdSolicitud(row.getInt(1));
            credito.setNombreGrupo(row.getString(2));
            credito.setPlazo(row.getString(3));
            credito.setPeriodicidad(row.getString(4));
            credito.setFechaDesembolso(row.getString(5));
            credito.setDiaDesembolso(row.getString(6));
            credito.setHoraVisita(row.getString(7));
            credito.setEstatusCompletado(row.getInt(8));
            credito.setObservaciones(row.getString(9));
            credito.setCiclo(row.getString(10));
            credito.setGrupoId(row.getString(11));
        }

        row.close();

        return credito;
    }

}
