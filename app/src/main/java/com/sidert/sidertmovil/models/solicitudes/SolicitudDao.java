package com.sidert.sidertmovil.models.solicitudes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.sidert.sidertmovil.database.DBhelper;
import com.sidert.sidertmovil.utils.Miscellaneous;

import java.util.ArrayList;
import java.util.List;

import static com.sidert.sidertmovil.utils.Constants.TBL_SOLICITUDES;
import static com.sidert.sidertmovil.utils.Constants.TIMESTAMP;

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

    public Solicitud findLikeNombre(String nombre)
    {
        Solicitud solicitud = null;

        String sql = "" +
                "SELECT " +
                "* " +
                "FROM " + TBL_SOLICITUDES + " AS s " +
                "WHERE s.nombre = ? "
                ;

        Cursor row = db.rawQuery(sql, new String[]{nombre});

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

    public List<Solicitud> findAllByFilters(
            String sMenor45,
            String sGrupales,
            String sIndividuales,
            String sNombre,
            int iEstatus
    )
    {
        List<Solicitud> solicitudes = new ArrayList<>();

        String sql = "" +
                "SELECT " +
                "s.* " +
                "FROM " + TBL_SOLICITUDES + " AS s " +
                "WHERE ((s.fecha_inicio > DATE('now', '-45 day') AND ? = '1') OR ? = '0') " +
                "AND ((s.tipo_solicitud > 1 AND ? = '1') OR ? = '0') " +
                "AND ((s.tipo_solicitud = 1 AND ? = '1') OR ? = '0') " +
                "AND (s.nombre like '%'||?||'%') " +
                "AND (" +
                "   (s.estatus = 0 AND ? = '0') " +
                "   OR (s.estatus <= 1 AND ? = '1') " +
                "   OR (s.estatus <= 2 AND ? = '1')" +
                "   OR (s.estatus > 2 AND ? = '3')" +
                ")" +
                "ORDER BY s.fecha_inicio "
                ;

        Cursor row = db.rawQuery(sql, new String[]{sMenor45, sMenor45, sGrupales, sGrupales, sIndividuales, sIndividuales, sNombre, String.valueOf(iEstatus), String.valueOf(iEstatus), String.valueOf(iEstatus), String.valueOf(iEstatus)});

        if(row.getCount() > 0)
        {
            row.moveToFirst();

            for(int i = 0; i < row.getCount(); i++)
            {
                Solicitud solicitud = new Solicitud();

                Fill(row, solicitud);

                solicitudes.add(solicitud);
                row.moveToNext();
            }

        }

        row.close();

        return solicitudes;
    }

    public List<String> showNombres(int iEstatus)
    {
        List<String> nombres = new ArrayList<>();

        String sql = "" +
                "SELECT " +
                "distinct(s.nombre) as nombre " +
                "FROM " + TBL_SOLICITUDES + " AS s " +
                "WHERE " +
                "(" +
                "   (s.estatus = 0 AND ? = '0') " +
                "   OR (s.estatus <= 1 AND ? = '1') " +
                "   OR (s.estatus <= 2 AND ? = '1')" +
                "   OR (s.estatus > 2 AND ? = '3')" +
                ")" +
                "ORDER BY s.fecha_inicio "
                ;

        Cursor row = db.rawQuery(sql, new String[]{String.valueOf(iEstatus), String.valueOf(iEstatus), String.valueOf(iEstatus), String.valueOf(iEstatus)});

        if (row.getCount() > 0){
            row.moveToFirst();

            for(int i = 0; i < row.getCount(); i++)
            {
                nombres.add(row.getString(0));
                row.moveToNext();
            }
        }

        row.close();

        return nombres;
    }


    public void updateIdOriginacion(Solicitud solicitud)
    {
        ContentValues cv = new ContentValues();

        if(solicitud.getIdOriginacion() > 0) {
            cv.put("id_originacion", String.valueOf(solicitud.getIdOriginacion()));
            db.update(TBL_SOLICITUDES, cv, "id_solicitud = ?", new String[]{String.valueOf(solicitud.getIdSolicitud())});
        }
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

    public void solicitudEnviada(Solicitud solicitud)
    {
        ContentValues cv = new ContentValues();

        cv.put("estatus", solicitud.getEstatus());
        if(solicitud.getIdOriginacion() > 0) cv.put("id_originacion", String.valueOf(solicitud.getIdOriginacion()));
        cv.put("fecha_guardado", Miscellaneous.ObtenerFecha(TIMESTAMP));

        db.update(TBL_SOLICITUDES, cv, "id_solicitud = ?", new String[]{String.valueOf(solicitud.getIdSolicitud())});
    }

    public void setCompletado(Solicitud solicitud)
    {
        ContentValues cv = new ContentValues();
        cv.put("estatus", "2");
        cv.put("fecha_guardado", Miscellaneous.ObtenerFecha(TIMESTAMP));
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
