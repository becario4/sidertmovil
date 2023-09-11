package com.sidert.sidertmovil.models.apoyogastosfunerarios;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.sidert.sidertmovil.database.DBhelper;

import static com.sidert.sidertmovil.utils.Constants.TBL_RECIBOS_AGF_CC;

import java.util.ArrayList;
import java.util.List;

public class ReciboDao {
    final DBhelper dbHelper;
    final SQLiteDatabase db;

    public ReciboDao(Context ctx)
    {
        this.dbHelper = DBhelper.getInstance(ctx);
        this.db = dbHelper.getWritableDatabase();
    }

    public List<Recibo> show()
    {
        List<Recibo> recibos = new ArrayList<>();

        String sql = "SELECT * FROM " + TBL_RECIBOS_AGF_CC;
        Cursor row = db.rawQuery(sql, new String[]{});

        if(row.getCount() > 0)
        {
            row.moveToFirst();

            for(int i = 0; i < row.getCount(); i++)
            {
                Recibo r = new Recibo();
                r.setId(row.getInt(0));
                r.setGrupoId(row.getString(1));
                r.setNumSolicitud(row.getString(2));
                r.setMonto(row.getString(3));
                r.setFolio(row.getString(4));
                r.setTipoRecibo(row.getString(5));
                r.setTipoImpresion(row.getString(6));
                r.setFechaImpresion(row.getString(7));
                r.setFechaEnvio(row.getString(8));
                r.setEstatus(row.getInt(9));
                r.setNombre(row.getString(10));
                r.setPlazo(row.getInt(11));

                recibos.add(r);

                row.moveToNext();
            }
        }

        row.close();

        return recibos;
    }

    public long store(Recibo recibo)
    {
        long id;

        db.beginTransaction();

        String sql = "INSERT INTO " + TBL_RECIBOS_AGF_CC + "(" +
                "grupo_id, " +
                "num_solicitud, " +
                "monto, " +
                "folio, " +
                "tipo_recibo, " +
                "tipo_impresion, " +
                "fecha_impresion, " +
                "fecha_envio, " +
                "estatus, " +
                "nombre, " +
                "plazo " +
                ") " +
                "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        SQLiteStatement pInsert = db.compileStatement(sql);

        pInsert.bindString(1, recibo.getGrupoId());
        pInsert.bindString(2, recibo.getNumSolicitud());
        pInsert.bindString(3, recibo.getMonto());
        pInsert.bindString(4, recibo.getFolio());
        pInsert.bindString(5, recibo.getTipoRecibo());
        pInsert.bindString(6, recibo.getTipoImpresion());
        pInsert.bindString(7, recibo.getFechaImpresion());
        pInsert.bindString(8, recibo.getFechaEnvio());
        pInsert.bindString(9, String.valueOf(recibo.getEstatus()));
        pInsert.bindString(10, recibo.getNombre());
        pInsert.bindString(11, String.valueOf(recibo.getPlazo()));

        id = pInsert.executeInsert();

        db.setTransactionSuccessful();
        db.endTransaction();

        return id;
    }

    public void update(int id, Recibo recibo)
    {
        ContentValues cv = new ContentValues();
        cv.put("fecha_envio", recibo.getFechaEnvio());
        cv.put("estatus", recibo.getEstatus());

        db.update(TBL_RECIBOS_AGF_CC, cv, "_id = ?", new String[]{String.valueOf(id)});
    }

    public Recibo findByGrupoIdAndNumSolicitudAndTipoImpresion(String grupoId, String numSolicitud, String tipoImpresion)
    {
        Recibo recibo = null;

        String sql = "SELECT r.* " +
                "FROM " + TBL_RECIBOS_AGF_CC + " AS r " +
                "WHERE r.grupo_id = ? " +
                "AND r.num_solicitud = ? " +
                "AND r.tipo_impresion = ? " +
                "ORDER BY r.fecha_impresion DESC " +
                "LIMIT 1";
        Cursor row = db.rawQuery(sql, new String[]{grupoId, numSolicitud, tipoImpresion});

        if(row.getCount() > 0)
        {
            row.moveToFirst();

            recibo = new Recibo();
            recibo.setId(row.getInt(0));
            recibo.setGrupoId(row.getString(1));
            recibo.setNumSolicitud(row.getString(2));
            recibo.setMonto(row.getString(3));
            recibo.setFolio(row.getString(4));
            recibo.setTipoRecibo(row.getString(5));
            recibo.setTipoImpresion(row.getString(6));
            recibo.setFechaImpresion(row.getString(7));
            recibo.setFechaEnvio(row.getString(8));
            recibo.setEstatus(row.getInt(9));
            recibo.setNombre(row.getString(10));
            recibo.setPlazo(row.getInt(11));
        }

        row.close();

        return recibo;
    }

    public Recibo findByNombreAndNumSolicitudAndTipoImpresion(String nombre, String numSolicitud, String tipoImpresion)
    {
        Recibo recibo = null;

        String sql = "SELECT r.* " +
                "FROM " + TBL_RECIBOS_AGF_CC + " AS r " +
                "WHERE r.nombre = ? " +
                "AND r.num_solicitud = ? " +
                "AND r.tipo_impresion = ? " +
                "ORDER BY r.fecha_impresion DESC " +
                "LIMIT 1";
        Cursor row = db.rawQuery(sql, new String[]{nombre, numSolicitud, tipoImpresion});

        if(row.getCount() > 0)
        {
            row.moveToFirst();

            recibo = new Recibo();
            recibo.setId(row.getInt(0));
            recibo.setGrupoId(row.getString(1));
            recibo.setNumSolicitud(row.getString(2));
            recibo.setMonto(row.getString(3));
            recibo.setFolio(row.getString(4));
            recibo.setTipoRecibo(row.getString(5));
            recibo.setTipoImpresion(row.getString(6));
            recibo.setFechaImpresion(row.getString(7));
            recibo.setFechaEnvio(row.getString(8));
            recibo.setEstatus(row.getInt(9));
            recibo.setNombre(row.getString(10));
            recibo.setPlazo(row.getInt(11));
        }

        row.close();

        return recibo;
    }

    public List<Recibo> findAllByGrupoIdAndNumSolicitud(String grupoId, String numSolicitud)
    {
        List<Recibo> recibos = new ArrayList<>();

        String sql = "SELECT r.* " +
                "FROM " + TBL_RECIBOS_AGF_CC + " AS r " +
                "WHERE r.grupo_id = ? " +
                "AND r.num_solicitud = ? " +
                "ORDER BY r.fecha_impresion DESC ";
        Cursor row = db.rawQuery(sql, new String[]{grupoId, numSolicitud});

        if(row.getCount() > 0)
        {
            row.moveToFirst();

            for(int i = 0; i < row.getCount(); i++)
            {
                Recibo r = new Recibo();
                r.setId(row.getInt(0));
                r.setGrupoId(row.getString(1));
                r.setNumSolicitud(row.getString(2));
                r.setMonto(row.getString(3));
                r.setFolio(row.getString(4));
                r.setTipoRecibo(row.getString(5));
                r.setTipoImpresion(row.getString(6));
                r.setFechaImpresion(row.getString(7));
                r.setFechaEnvio(row.getString(8));
                r.setEstatus(row.getInt(9));
                r.setNombre(row.getString(10));
                r.setPlazo(row.getInt(11));

                recibos.add(r);

                row.moveToNext();
            }
        }

        row.close();

        return recibos;
    }

    public List<Recibo> findAllByNombreAndNumSolicitud(String nombre, String numSolicitud)
    {
        List<Recibo> recibos = new ArrayList<>();

        String sql = "SELECT r.* " +
                "FROM " + TBL_RECIBOS_AGF_CC + " AS r " +
                "WHERE r.nombre = ? " +
                "AND r.num_solicitud = ? " +
                "ORDER BY r.fecha_impresion DESC ";
        Cursor row = db.rawQuery(sql, new String[]{nombre, numSolicitud});

        if(row.getCount() > 0)
        {
            row.moveToFirst();

            for(int i = 0; i < row.getCount(); i++)
            {
                Recibo r = new Recibo();
                r.setId(row.getInt(0));
                r.setGrupoId(row.getString(1));
                r.setNumSolicitud(row.getString(2));
                r.setMonto(row.getString(3));
                r.setFolio(row.getString(4));
                r.setTipoRecibo(row.getString(5));
                r.setTipoImpresion(row.getString(6));
                r.setFechaImpresion(row.getString(7));
                r.setFechaEnvio(row.getString(8));
                r.setEstatus(row.getInt(9));
                r.setNombre(row.getString(10));
                r.setPlazo(row.getInt(11));

                recibos.add(r);

                row.moveToNext();
            }
        }

        row.close();

        return recibos;
    }

    public List<Recibo> findAllByCustomFilters(String[] filters)
    {
        List<Recibo> recibos = new ArrayList<>();

        String sql = "SELECT r.*, CASE(SELECT COUNT(*) FROM " + TBL_RECIBOS_AGF_CC + " AS r2 WHERE r2.folio = r.folio) WHEN 1 THEN 'O' ELSE 'O|C' END AS impresiones " +
                "FROM " + TBL_RECIBOS_AGF_CC + " AS r " +
                "WHERE r.tipo_impresion = 'O' " +
                "AND r.nombre LIKE '%'||?||'%' " +
                "AND (r.folio = ? OR ? = '') " +
                "AND (r.estatus = 1 OR ? = '0') " +
                "AND (r.estatus = 0 OR ? = '0') " +
                "ORDER BY r.fecha_impresion DESC";
        Cursor row = db.rawQuery(sql, filters);

        if(row.getCount() > 0)
        {
            row.moveToFirst();

            for(int i = 0; i < row.getCount(); i++)
            {
                Recibo r = new Recibo();
                r.setId(row.getInt(0));
                r.setGrupoId(row.getString(1));
                r.setNumSolicitud(row.getString(2));
                r.setMonto(row.getString(3));
                r.setFolio(row.getString(4));
                r.setTipoRecibo(row.getString(5));
                r.setTipoImpresion(row.getString(6));
                r.setFechaImpresion(row.getString(7));
                r.setFechaEnvio(row.getString(8));
                r.setEstatus(row.getInt(9));
                r.setNombre(row.getString(10));
                r.setPlazo(row.getInt(11));
                r.setImpresiones(row.getString(12));

                recibos.add(r);

                row.moveToNext();
            }
        }

        row.close();

        return recibos;
    }

    public List<String> showNombres()
    {
        List<String> nombres = new ArrayList<>();

        String sql = "SELECT DISTINCT(r.nombre) AS nombre FROM " + TBL_RECIBOS_AGF_CC + " AS r ";
        Cursor row = db.rawQuery(sql, new String[]{});

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
}
