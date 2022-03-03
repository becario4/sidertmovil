package com.sidert.sidertmovil.models.solicitudes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.sidert.sidertmovil.database.DBhelper;
import com.sidert.sidertmovil.models.solicitudes.solicitudgpo.SolicitudDetalleEstatusGpo;

import java.util.ArrayList;
import java.util.List;

import static com.sidert.sidertmovil.utils.Constants.TBL_CREDITO_GPO_REN;
import static com.sidert.sidertmovil.utils.Constants.TBL_CREDITO_IND_REN;
import static com.sidert.sidertmovil.utils.Constants.TBL_INTEGRANTES_GPO_REN;
import static com.sidert.sidertmovil.utils.Constants.TBL_PRESTAMOS_TO_RENOVAR;
import static com.sidert.sidertmovil.utils.Constants.TBL_SOLICITUDES;
import static com.sidert.sidertmovil.utils.Constants.TBL_SOLICITUDES_REN;

public class SolicitudRenDao {
    final DBhelper dbHelper;
    final SQLiteDatabase db;

    public SolicitudRenDao(Context ctx){
        this.dbHelper = new DBhelper(ctx);
        this.db = dbHelper.getWritableDatabase();
    }

    public SolicitudRen findByNombre(String nombre)
    {
        SolicitudRen solicitud = null;

        String sql = "" +
                "SELECT " +
                "* " +
                "FROM " + TBL_SOLICITUDES_REN + " AS s " +
                "WHERE s.nombre = ? "
                ;

        Cursor row = db.rawQuery(sql, new String[]{String.valueOf(nombre)});

        if(row.getCount() > 0)
        {
            row.moveToFirst();

            solicitud = new SolicitudRen();

            Fill(row, solicitud);
        }

        row.close();

        return solicitud;
    }

    public List<SolicitudRen> findAllOrderByFechaVencimiento()
    {
        List<SolicitudRen> solicitudes = new ArrayList<SolicitudRen>();

        String sql = "" +
            "SELECT " +
            "s.* " +
            "FROM " + TBL_SOLICITUDES_REN + " AS s " +
            "INNER JOIN " + TBL_PRESTAMOS_TO_RENOVAR + " AS pr ON pr.cliente_nombre = s.nombre " +
            "ORDER BY pr.fecha_vencimiento "
        ;

        Cursor row = db.rawQuery(sql, new String[]{});

        if(row.getCount() > 0)
        {
            row.moveToFirst();

            for(int i = 0; i < row.getCount(); i++)
            {
                SolicitudRen solicitud = new SolicitudRen();

                Fill(row, solicitud);

                solicitudes.add(solicitud);
                row.moveToNext();
            }

        }

        row.close();

        return solicitudes;
    }

    public List<SolicitudRen> findAllByFilters(
            String sMenor45,
            String sGrupales,
            String sIndividuales,
            String sNombre,
            int iEstatus
    )
    {
        List<SolicitudRen> solicitudes = new ArrayList<SolicitudRen>();

        String sql = "SELECT * FROM ( " +
                    "SELECT " +
                    "s.*, c.estatus_completado, pr.fecha_vencimiento, pr.grupo_id, c.plazo " +
                    "FROM " + TBL_SOLICITUDES_REN + " AS s " +
                    "INNER JOIN " + TBL_PRESTAMOS_TO_RENOVAR + " AS pr ON pr.cliente_nombre = s.nombre " +
                    "INNER JOIN " + TBL_CREDITO_GPO_REN + " AS c ON c.id_solicitud = s.id_solicitud " +
                    "UNION " +
                    "SELECT " +
                    "s.*, c.estatus_completado, pr.fecha_vencimiento, pr.grupo_id, c.plazo " +
                    "FROM " + TBL_SOLICITUDES_REN + " AS s " +
                    "INNER JOIN " + TBL_PRESTAMOS_TO_RENOVAR + " AS pr ON pr.cliente_nombre = s.nombre " +
                    "INNER JOIN " + TBL_CREDITO_IND_REN + " AS c ON c.id_solicitud = s.id_solicitud " +
                ") AS x " +
                "WHERE ((x.fecha_vencimiento > DATE('now', '-45 day') AND ? = '1') OR ? = '0') " +
                "AND ((x.grupo_id > 1 AND ? = '1') OR ? = '0') " +
                "AND ((x.grupo_id = 1 AND ? = '1') OR ? = '0') " +
                "AND (x.nombre like '%'||?||'%') " +
                "AND (" +
                "   (x.estatus_completado = 0 AND ? = '0') " +
                "   OR ((x.estatus_completado = 1 OR x.plazo <> '') AND ? = '1' AND x.estatus <= 2) " +
                "   OR ((x.estatus_completado = 2 OR x.plazo <> '') AND ? = '1' AND x.estatus <= 2)" +
                "   OR (x.estatus > 2 AND ? = '3')" +
                ")" +
                "ORDER BY x.fecha_vencimiento "
                ;

        Cursor row = db.rawQuery(sql, new String[]{sMenor45, sMenor45, sGrupales, sGrupales, sIndividuales, sIndividuales, sNombre, String.valueOf(iEstatus), String.valueOf(iEstatus), String.valueOf(iEstatus), String.valueOf(iEstatus)});

        if(row.getCount() > 0)
        {
            row.moveToFirst();

            for(int i = 0; i < row.getCount(); i++)
            {
                SolicitudRen solicitud = new SolicitudRen();

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
                "FROM " + TBL_SOLICITUDES_REN + " AS s " +
                "INNER JOIN " + TBL_PRESTAMOS_TO_RENOVAR + " AS pr ON pr.cliente_nombre = s.nombre " +
                "INNER JOIN " + TBL_CREDITO_GPO_REN + " AS c ON c.id_solicitud = s.id_solicitud " +
                "WHERE " +
                "(" +
                "   (c.estatus_completado = 0 AND ? = '0') " +
                "   OR ((c.estatus_completado = 1 OR c.plazo <> '') AND ? = '1' AND s.estatus <= 2) " +
                "   OR ((c.estatus_completado = 2 OR c.plazo <> '') AND ? = '1' AND s.estatus <= 2)" +
                "   OR (s.estatus > 2 AND ? = '3')" +
                ")" +
                "UNION " +
                "SELECT " +
                "distinct(s.nombre) as nombre " +
                "FROM " + TBL_SOLICITUDES_REN + " AS s " +
                "INNER JOIN " + TBL_PRESTAMOS_TO_RENOVAR + " AS pr ON pr.cliente_nombre = s.nombre " +
                "INNER JOIN " + TBL_CREDITO_IND_REN + " AS c ON c.id_solicitud = s.id_solicitud " +
                "WHERE " +
                "(" +
                "   (c.estatus_completado = 0 AND ? = '0') " +
                "   OR ((c.estatus_completado = 1 OR c.plazo <> '') AND ? = '1' AND s.estatus <= 2) " +
                "   OR ((c.estatus_completado = 2 OR c.plazo <> '') AND ? = '1' AND s.estatus <= 2)" +
                "   OR (s.estatus > 2 AND ? = '3')" +
                ")"
                ;

        Cursor row = db.rawQuery(sql, new String[]{String.valueOf(iEstatus), String.valueOf(iEstatus), String.valueOf(iEstatus), String.valueOf(iEstatus),
                String.valueOf(iEstatus), String.valueOf(iEstatus), String.valueOf(iEstatus), String.valueOf(iEstatus)
        });

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

    public List<SolicitudRen> findAllOrderByFechaVencimientoMayor90()
    {
        List<SolicitudRen> solicitudes = new ArrayList<SolicitudRen>();

        String sql = "" +
                "SELECT " +
                "s.* " +
                "FROM " + TBL_SOLICITUDES_REN + " AS s " +
                "INNER JOIN " + TBL_PRESTAMOS_TO_RENOVAR + " AS pr ON pr.cliente_nombre = s.nombre " +
                //"WHERE pr.fecha_vencimiento > DATE('now', '-45 day')" +
                "ORDER BY pr.fecha_vencimiento "
                ;

        Cursor row = db.rawQuery(sql, new String[]{});

        if(row.getCount() > 0)
        {
            row.moveToFirst();

            for(int i = 0; i < row.getCount(); i++)
            {
                SolicitudRen solicitud = new SolicitudRen();

                Fill(row, solicitud);

                solicitudes.add(solicitud);
                row.moveToNext();
            }

        }

        row.close();

        return solicitudes;
    }

    public SolicitudRen findByIdSolicitud(int idSolicitud)
    {
        SolicitudRen solicitud = null;

        String sql = "" +
            "SELECT " +
            "* " +
            "FROM " + TBL_SOLICITUDES_REN + " AS s " +
            "WHERE s.id_solicitud = ? "
        ;

        Cursor row = db.rawQuery(sql, new String[]{String.valueOf(idSolicitud)});

        if(row.getCount() > 0)
        {
            row.moveToFirst();

            solicitud = new SolicitudRen();

            Fill(row, solicitud);
        }

        row.close();

        return solicitud;
    }

    public SolicitudRen findByIdOriginacion(int idOriginacion)
    {
        SolicitudRen solicitud = null;

        String sql = "" +
                "SELECT " +
                "* " +
                "FROM " + TBL_SOLICITUDES_REN + " AS s " +
                "WHERE s.id_originacion = ? "
                ;

        Cursor row = db.rawQuery(sql, new String[]{String.valueOf(idOriginacion)});

        if(row.getCount() > 0)
        {
            row.moveToFirst();

            solicitud = new SolicitudRen();

            Fill(row, solicitud);
        }

        row.close();

        return solicitud;
    }

    public void updateEstatus(SolicitudRen solicitud)
    {
        ContentValues cv = new ContentValues();

        cv.put("estatus", solicitud.getEstatus());
        if(solicitud.getIdOriginacion() > 0) cv.put("id_originacion", String.valueOf(solicitud.getIdOriginacion()));
        cv.put("fecha_termino", solicitud.getFechaTermino());
        //cv.put("fecha_envio", solicitud.getFechaEnvio());
        //cv.put("fecha_guardado", solicitud.getFechaGuardado());

        db.update(TBL_SOLICITUDES_REN, cv, "id_solicitud = ?", new String[]{String.valueOf(solicitud.getIdSolicitud())});
    }

    private void Fill(Cursor row, SolicitudRen solicitud)
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
