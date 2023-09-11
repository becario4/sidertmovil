package com.sidert.sidertmovil.models.apoyogastosfunerarios;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.sidert.sidertmovil.database.DBhelper;

import java.util.ArrayList;
import java.util.List;

import static com.sidert.sidertmovil.utils.Constants.TBL_RECIBOS_AGF_CC;
import static com.sidert.sidertmovil.utils.Constants.TBL_RECUPERACION_RECIBOS;

public class GestionDao {
    final DBhelper dbHelper;
    final SQLiteDatabase db;

    public GestionDao(Context ctx)
    {
        this.dbHelper = DBhelper.getInstance(ctx);
        this.db = dbHelper.getWritableDatabase();
    }

    public long store(Gestion gestion)
    {
        Long id;
        String sql;

        db.beginTransaction();

        sql = "INSERT INTO " + TBL_RECUPERACION_RECIBOS + "(" +
                "grupo_id," +
                "num_solicitud," +
                "medio_pago," +
                "evidencia," +
                "tipo_imagen," +
                "fecha_termino," +
                "fecha_envio," +
                "tipo," +
                "nombre," +
                "estatus," +
                "monto," +
                "imprimir_recibo," +
                "folio_manual," +
                "cliente_id," +
                "total_integrantes, " +
                "total_integrantes_manual" +
                ") VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        SQLiteStatement pInsert = db.compileStatement(sql);

        pInsert.bindString(1, gestion.getGrupoId());
        pInsert.bindString(2, gestion.getNumSolicitud());
        pInsert.bindString(3, gestion.getMedioPago());
        pInsert.bindString(4, gestion.getEvidencia());
        pInsert.bindString(5, gestion.getTipoImagen());
        pInsert.bindString(6, gestion.getFechaTermino());
        pInsert.bindString(7, gestion.getFechaEnvio());
        pInsert.bindString(8, gestion.getTipo());
        pInsert.bindString(9, gestion.getNombre());
        pInsert.bindLong(10, gestion.getEstatus());
        pInsert.bindString(11, gestion.getMonto());
        pInsert.bindString(12, gestion.getImprimirRecibo());
        pInsert.bindString(13, gestion.getFolioManual());
        pInsert.bindString(14, gestion.getClienteId());
        pInsert.bindString(15, String.valueOf(gestion.getTotalIntegrantes()));
        pInsert.bindString(16, String.valueOf(gestion.getTotalIntegrantesManual()));


        id = pInsert.executeInsert();

        db.setTransactionSuccessful();
        db.endTransaction();

        return id;
    }

    public void update(int id, Gestion gestion)
    {
        ContentValues cv = new ContentValues();
        cv.put("tipo_imagen", gestion.getTipoImagen());
        cv.put("evidencia", gestion.getEvidencia());
        cv.put("fecha_termino", gestion.getFechaTermino());
        cv.put("fecha_envio", gestion.getFechaEnvio());
        cv.put("estatus", gestion.getEstatus());
        cv.put("medio_pago", gestion.getMedioPago());
        cv.put("monto", gestion.getMonto());
        cv.put("imprimir_recibo", gestion.getImprimirRecibo());
        cv.put("folio_manual", gestion.getFolioManual());
        cv.put("total_integrantes", gestion.getTotalIntegrantes());
        cv.put("total_integrantes_manual", gestion.getTotalIntegrantesManual());

        db.update(TBL_RECUPERACION_RECIBOS, cv, "_id = ?", new String[]{String.valueOf(id)});
    }

    public Gestion findById(Integer id)
    {
        Gestion gestion = null;

        String sql = "SELECT rr.* " +
                "FROM " + TBL_RECUPERACION_RECIBOS + " AS rr " +
                "WHERE rr._id = ? ";
        Cursor row = db.rawQuery(sql, new String[]{String.valueOf(id)});

        if(row.getCount() > 0)
        {
            row.moveToFirst();

            gestion = new Gestion();
            gestion.setId(row.getInt(0));
            gestion.setGrupoId(row.getString(1));
            gestion.setNumSolicitud(row.getString(2));
            gestion.setMedioPago(row.getString(3));
            gestion.setEvidencia(row.getString(4));
            gestion.setTipoImagen(row.getString(5));
            gestion.setFechaTermino(row.getString(6));
            gestion.setFechaEnvio(row.getString(7));
            gestion.setTipo(row.getString(8));
            gestion.setNombre(row.getString(9));
            gestion.setEstatus(row.getInt(10));
            gestion.setMonto(row.getString(11));
            gestion.setImprimirRecibo(row.getString(12));
            gestion.setFolioManual(row.getString(13));
            gestion.setClienteId(row.getString(14));
            gestion.setTotalIntegrantes(row.getInt(15));
            gestion.setTotalIntegrantesManual(row.getInt(16));
        }

        row.close();

        return gestion;
    }

    public List<Gestion> findAll()
    {
        List<Gestion> gestiones = new ArrayList<>();

        String sql = "SELECT rr.* " +
                "FROM " + TBL_RECUPERACION_RECIBOS + " AS rr " +
                "ORDER BY rr.fecha_termino DESC ";
        Cursor row = db.rawQuery(sql, new String[]{});

        if(row.getCount() > 0)
        {
            row.moveToFirst();

            for(int i = 0; i < row.getCount(); i++)
            {
                Gestion g = new Gestion();
                g.setId(row.getInt(0));
                g.setGrupoId(row.getString(1));
                g.setNumSolicitud(row.getString(2));
                g.setMedioPago(row.getString(3));
                g.setEvidencia(row.getString(4));
                g.setTipoImagen(row.getString(5));
                g.setFechaTermino(row.getString(6));
                g.setFechaEnvio(row.getString(7));
                g.setTipo(row.getString(8));
                g.setNombre(row.getString(9));
                g.setEstatus(row.getInt(10));
                g.setMonto(row.getString(11));
                g.setImprimirRecibo(row.getString(12));
                g.setFolioManual(row.getString(13));
                g.setClienteId(row.getString(14));
                g.setTotalIntegrantes(row.getInt(15));
                g.setTotalIntegrantesManual(row.getInt(16));

                gestiones.add(g);

                row.moveToNext();
            }
        }

        row.close();

        return gestiones;
    }

    public List<Gestion> findAllByEstatus(List<Integer> estatus)
    {
        List<Gestion> gestiones = new ArrayList<>();
        String where = "WHERE rr.estatus IN (";

        for(int i = 0; i < estatus.size(); i++)
        {
            where = where + estatus.get(i);

            if(i < estatus.size() - 1) where = where + ", ";
        }

        String sql = "SELECT rr.* " +
                "FROM " + TBL_RECUPERACION_RECIBOS + " AS rr " + where +
                ") ORDER BY rr.fecha_termino DESC ";
        Cursor row = db.rawQuery(sql, new String[]{});

        if(row.getCount() > 0)
        {
            row.moveToFirst();

            for(int i = 0; i < row.getCount(); i++)
            {
                Gestion g = new Gestion();
                g.setId(row.getInt(0));
                g.setGrupoId(row.getString(1));
                g.setNumSolicitud(row.getString(2));
                g.setMedioPago(row.getString(3));
                g.setEvidencia(row.getString(4));
                g.setTipoImagen(row.getString(5));
                g.setFechaTermino(row.getString(6));
                g.setFechaEnvio(row.getString(7));
                g.setTipo(row.getString(8));
                g.setNombre(row.getString(9));
                g.setEstatus(row.getInt(10));
                g.setMonto(row.getString(11));
                g.setImprimirRecibo(row.getString(12));
                g.setFolioManual(row.getString(13));
                g.setClienteId(row.getString(14));
                g.setTotalIntegrantes(row.getInt(15));
                g.setTotalIntegrantesManual(row.getInt(16));

                gestiones.add(g);

                row.moveToNext();
            }
        }

        row.close();

        return gestiones;
    }

    public List<Gestion> findAllByEstatusLastWeek(List<Integer> estatus)
    {
        List<Gestion> gestiones = new ArrayList<>();
        String where = "WHERE rr.estatus IN (";

        for(int i = 0; i < estatus.size(); i++)
        {
            where = where + estatus.get(i);

            if(i < estatus.size() - 1) where = where + ", ";
        }

        String sql = "SELECT rr.* " +
                "FROM " + TBL_RECUPERACION_RECIBOS + " AS rr " + where +
                ") " +
                "and date('now', '%w') in (0, 6) " +
                "and rr.fecha_envio <=  date('now')" +
                "ORDER BY rr.fecha_termino DESC ";
        Cursor row = db.rawQuery(sql, new String[]{});

        if(row.getCount() > 0)
        {
            row.moveToFirst();

            for(int i = 0; i < row.getCount(); i++)
            {
                Gestion g = new Gestion();
                g.setId(row.getInt(0));
                g.setGrupoId(row.getString(1));
                g.setNumSolicitud(row.getString(2));
                g.setMedioPago(row.getString(3));
                g.setEvidencia(row.getString(4));
                g.setTipoImagen(row.getString(5));
                g.setFechaTermino(row.getString(6));
                g.setFechaEnvio(row.getString(7));
                g.setTipo(row.getString(8));
                g.setNombre(row.getString(9));
                g.setEstatus(row.getInt(10));
                g.setMonto(row.getString(11));
                g.setImprimirRecibo(row.getString(12));
                g.setFolioManual(row.getString(13));
                g.setClienteId(row.getString(14));
                g.setTotalIntegrantes(row.getInt(15));
                g.setTotalIntegrantesManual(row.getInt(16));

                gestiones.add(g);

                row.moveToNext();
            }
        }

        row.close();

        return gestiones;
    }

    public List<Gestion> findAllByCustomFilters(String[] filters)
    {
        List<Gestion> gestiones = new ArrayList<>();

        String sql = "SELECT rr.*, COALESCE(r.folio, '') AS folioImpresion " +
                "FROM " + TBL_RECUPERACION_RECIBOS + " AS rr " +
                "LEFT JOIN " + TBL_RECIBOS_AGF_CC + " AS r ON r.grupo_id = rr.grupo_id AND r.num_solicitud = rr.num_solicitud AND r.tipo_recibo = rr.tipo AND r.tipo_impresion = 'O' " +
                "WHERE rr.estatus in (1, 2) " +
                "AND rr.nombre LIKE '%'||?||'%' " +
                "AND (rr.estatus = 2 OR ? = '0') " +
                "AND (rr.estatus = 1 OR ? = '0') " +
                "ORDER BY rr.fecha_termino DESC ";
        Cursor row = db.rawQuery(sql, filters);

        if(row.getCount() > 0)
        {
            row.moveToFirst();

            for(int i = 0; i < row.getCount(); i++)
            {
                Gestion g = new Gestion();
                g.setId(row.getInt(0));
                g.setGrupoId(row.getString(1));
                g.setNumSolicitud(row.getString(2));
                g.setMedioPago(row.getString(3));
                g.setEvidencia(row.getString(4));
                g.setTipoImagen(row.getString(5));
                g.setFechaTermino(row.getString(6));
                g.setFechaEnvio(row.getString(7));
                g.setTipo(row.getString(8));
                g.setNombre(row.getString(9));
                g.setEstatus(row.getInt(10));
                g.setMonto(row.getString(11));
                g.setImprimirRecibo(row.getString(12));
                g.setFolioManual(row.getString(13));
                g.setClienteId(row.getString(14));
                g.setTotalIntegrantes(row.getInt(15));
                g.setTotalIntegrantesManual(row.getInt(16));
                g.setFolioImpresion(row.getString(17));

                gestiones.add(g);

                row.moveToNext();
            }
        }

        row.close();

        return gestiones;
    }

    public Gestion findByClienteIdAndNumSolicitud(String clienteId, String numSolicitud)
    {
        Gestion gestion = null;

        String sql = "SELECT rr.* " +
                "FROM " + TBL_RECUPERACION_RECIBOS + " AS rr " +
                "WHERE rr.cliente_id = ? " +
                "AND rr.num_solicitud = ? " +
                "ORDER BY rr.fecha_termino DESC ";
        Cursor row = db.rawQuery(sql, new String[]{clienteId, numSolicitud});

        if(row.getCount() > 0)
        {
            row.moveToFirst();

            gestion = new Gestion();
            gestion.setId(row.getInt(0));
            gestion.setGrupoId(row.getString(1));
            gestion.setNumSolicitud(row.getString(2));
            gestion.setMedioPago(row.getString(3));
            gestion.setEvidencia(row.getString(4));
            gestion.setTipoImagen(row.getString(5));
            gestion.setFechaTermino(row.getString(6));
            gestion.setFechaEnvio(row.getString(7));
            gestion.setTipo(row.getString(8));
            gestion.setNombre(row.getString(9));
            gestion.setEstatus(row.getInt(10));
            gestion.setMonto(row.getString(11));
            gestion.setImprimirRecibo(row.getString(12));
            gestion.setFolioManual(row.getString(13));
            gestion.setClienteId(row.getString(14));
            gestion.setTotalIntegrantes(row.getInt(15));
            gestion.setTotalIntegrantesManual(row.getInt(16));
        }

        row.close();

        return gestion;
    }

    public Gestion findByGrupoIdAndNumSolicitud(String grupoId, String numSolicitud)
    {
        Gestion gestion = null;

        String sql = "SELECT rr.* " +
                "FROM " + TBL_RECUPERACION_RECIBOS + " AS rr " +
                "WHERE rr.grupo_id = ? " +
                "AND rr.num_solicitud = ? " +
                "ORDER BY rr.fecha_termino DESC ";
        Cursor row = db.rawQuery(sql, new String[]{grupoId, numSolicitud});

        if(row.getCount() > 0)
        {
            row.moveToFirst();

            gestion = new Gestion();
            gestion.setId(row.getInt(0));
            gestion.setGrupoId(row.getString(1));
            gestion.setNumSolicitud(row.getString(2));
            gestion.setMedioPago(row.getString(3));
            gestion.setEvidencia(row.getString(4));
            gestion.setTipoImagen(row.getString(5));
            gestion.setFechaTermino(row.getString(6));
            gestion.setFechaEnvio(row.getString(7));
            gestion.setTipo(row.getString(8));
            gestion.setNombre(row.getString(9));
            gestion.setEstatus(row.getInt(10));
            gestion.setMonto(row.getString(11));
            gestion.setImprimirRecibo(row.getString(12));
            gestion.setFolioManual(row.getString(13));
            gestion.setClienteId(row.getString(14));
            gestion.setTotalIntegrantes(row.getInt(15));
            gestion.setTotalIntegrantesManual(row.getInt(16));
        }

        row.close();

        return gestion;
    }

    public List<String> showNombres()
    {
        List<String> nombres = new ArrayList<>();

        String sql = "SELECT DISTINCT(rr.nombre) AS nombre FROM " + TBL_RECUPERACION_RECIBOS + " AS rr ";
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
