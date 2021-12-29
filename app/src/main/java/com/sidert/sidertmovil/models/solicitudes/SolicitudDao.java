package com.sidert.sidertmovil.models.solicitudes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.sidert.sidertmovil.database.DBhelper;

import static com.sidert.sidertmovil.utils.Constants.TBL_SOLICITUDES;

public class SolicitudDao {
    final DBhelper dbHelper;
    final SQLiteDatabase db;

    public SolicitudDao(Context ctx){
        this.dbHelper = new DBhelper(ctx);
        this.db = dbHelper.getWritableDatabase();
    }

    public Solicitud findByIdSolicitud(int idSolicitud)
    {
        Solicitud solicitud = null;

        String sql = "" +
                "SELECT " +
                "* " +
                "FROM " + TBL_SOLICITUDES + " AS s " +
                "WHERE s.id_solicitud = ? "
                ;

        Cursor row = db.rawQuery(sql, new String[]{String.valueOf(idSolicitud)});

        if(row.getCount() > 0)
        {
            row.moveToFirst();

            solicitud = new Solicitud();

            Fill(row, solicitud);
        }

        row.close();

        return solicitud;
    }

    public Solicitud findByIdOriginacion(int idOriginacion)
    {
        Solicitud solicitud = null;

        String sql = "" +
                "SELECT " +
                "* " +
                "FROM " + TBL_SOLICITUDES + " AS s " +
                "WHERE s.id_originacion = ? "
                ;

        Cursor row = db.rawQuery(sql, new String[]{String.valueOf(idOriginacion)});

        if(row.getCount() > 0)
        {
            row.moveToFirst();

            solicitud = new Solicitud();

            Fill(row, solicitud);
        }

        row.close();

        return solicitud;
    }

    public void updateEstatus(Solicitud solicitud)
    {
        ContentValues cv = new ContentValues();

        cv.put("estatus", solicitud.getEstatus());
        if(solicitud.getIdOriginacion() > 0) cv.put("id_originacion", String.valueOf(solicitud.getIdOriginacion()));
        cv.put("fecha_termino", solicitud.getFechaTermino());
        //cv.put("fecha_envio", solicitud.getFechaEnvio());
        //cv.put("fecha_guardado", solicitud.getFechaGuardado());

        db.update(TBL_SOLICITUDES, cv, "id_solicitud = ?", new String[]{String.valueOf(solicitud.getIdSolicitud())});
    }

    private void Fill(Cursor row, Solicitud solicitud)
    {
        solicitud.setIdSolicitud(row.getInt(0));
        solicitud.setVolSolicitud(row.getString(1));
        solicitud.setUsuarioId(row.getInt(2));
        solicitud.setTipoSolicitud(row.getInt(3));
        solicitud.setIdOriginacion(row.getInt(4));
        solicitud.setNombre(row.getString(5));
        solicitud.setFechaInicio(row.getString(6));
        solicitud.setFechaTermino(row.getString(7));
        solicitud.setFechaEnvio(row.getString(8));
        solicitud.setFechaDispositivo(row.getString(9));
        solicitud.setFechaGuardado(row.getString(10));
        solicitud.setEstatus(row.getInt(11));
    }
}