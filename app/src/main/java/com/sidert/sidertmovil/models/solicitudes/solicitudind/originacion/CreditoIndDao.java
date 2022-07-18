package com.sidert.sidertmovil.models.solicitudes.solicitudind.originacion;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.sidert.sidertmovil.database.DBhelper;

import java.util.ArrayList;
import java.util.List;

public class CreditoIndDao {
    final DBhelper dBhelper;
    final SQLiteDatabase db;

    public CreditoIndDao(Context ctx)
    {
        this.dBhelper = new DBhelper(ctx);
        this.db = dBhelper.getWritableDatabase();
    }

    public CreditoInd findByIdCredito(Integer idCredito)
    {
        String sql = "" +
            "SELECT * " +
            "FROM " + CreditoInd.TBL + " AS ci " +
            "WHERE ci." + CreditoInd.COL_ID_CREDITO + " = ? " +
            "ORDER BY ci." + CreditoInd.COL_ID_CREDITO + " " +
            "LIMIT 1; "
        ;

        Cursor row = db.rawQuery(sql, new String[]{String.valueOf(idCredito)});

        return Find(row, sql);
    }

    public CreditoInd findByIdSolicitud(Integer idSolicitud)
    {
        String sql = "" +
            "SELECT * " +
            "FROM " + CreditoInd.TBL + " AS ci " +
            "WHERE ci." + CreditoInd.COL_ID_SOLICITUD + " = ? " +
            "ORDER BY ci." + CreditoInd.COL_ID_SOLICITUD + " " +
            "LIMIT 1; "
        ;

        Cursor row = db.rawQuery(sql, new String[]{String.valueOf(idSolicitud)});

        return Find(row, sql);
    }

    private CreditoInd Find(Cursor row, String query)
    {
        CreditoInd creditoInd = null;

        if(row.getCount() > 0)
        {
            row.moveToFirst();
            creditoInd = new CreditoInd();
            Fill(row, creditoInd);
        }

        row.close();

        return creditoInd;
    }

    private List<CreditoInd> FindAll(Cursor row, String query)
    {
        List<CreditoInd> creditosInd = new ArrayList<>();

        if(row.getCount() > 0)
        {
            row.moveToFirst();

            for(int i = 0; i < row.getCount(); i++)
            {
                CreditoInd creditoInd = new CreditoInd();
                Fill(row, creditoInd);
                creditosInd.add(creditoInd);
                row.moveToNext();
            }
        }

        row.close();

        return creditosInd;
    }

    private void Fill(Cursor row, CreditoInd creditoInd)
    {
        try
        {
            creditoInd.setIdCredito(row.getInt(row.getColumnIndex(creditoInd.COL_ID_CREDITO)));
            creditoInd.setIdSolicitud(row.getInt(row.getColumnIndex(creditoInd.COL_ID_SOLICITUD)));
            creditoInd.setPlazo(row.getString(row.getColumnIndex(creditoInd.COL_PLAZO)));
            creditoInd.setPeriodicidad(row.getString(row.getColumnIndex(creditoInd.COL_PERIODICIDAD)));
            creditoInd.setFechaDesembolso(row.getString(row.getColumnIndex(creditoInd.COL_FECHA_DESEMBOLSO)));
            creditoInd.setDisDesembolso(row.getString(row.getColumnIndex(creditoInd.COL_DIA_DESEMBOLSO)));
            creditoInd.setHoraVisita(row.getString(row.getColumnIndex(creditoInd.COL_HORA_VISITA)));
            creditoInd.setMontoPrestamo(row.getString(row.getColumnIndex(creditoInd.COL_MONTO_PRESTAMO)));
            creditoInd.setDestino(row.getString(row.getColumnIndex(creditoInd.COL_DESTINO)));
            creditoInd.setClasificacionRiesgo(row.getString(row.getColumnIndex(creditoInd.COL_CLASIFICACION_RIESGO)));
            creditoInd.setEstatusCompletado(row.getInt(row.getColumnIndex(creditoInd.COL_ESTATUS_COMPLETADO)));
            creditoInd.setMontoRefinanciar(row.getString(row.getColumnIndex(creditoInd.COL_MONTO_REFINANCIAR)));

        }
        catch(Exception ex)
        {
            Log.e("AQUI CREDITO IND FILL", ex.getMessage());
        }
    }
}
