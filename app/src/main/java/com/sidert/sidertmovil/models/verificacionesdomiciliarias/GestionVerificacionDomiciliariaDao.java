package com.sidert.sidertmovil.models.verificacionesdomiciliarias;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.sidert.sidertmovil.database.DBhelper;

import java.util.ArrayList;
import java.util.List;

import static com.sidert.sidertmovil.utils.Constants.TBL_GESTIONES_VER_DOM;

public class GestionVerificacionDomiciliariaDao {
    final DBhelper dbHelper;
    final SQLiteDatabase db;

    public GestionVerificacionDomiciliariaDao(Context ctx)
    {
        this.dbHelper = DBhelper.getInstance(ctx);
        this.db = dbHelper.getWritableDatabase();
    }

    public long store(GestionVerificacionDomiciliaria gestion)
    {
        Long id;
        String sql;

        db.beginTransaction();

        sql = "INSERT INTO " + TBL_GESTIONES_VER_DOM + "(" +
                "verificacion_domiciliaria_id," +
                "latitud," +
                "longitud," +
                "foto_fachada," +
                "domicilio_coincide," +
                "comentario," +
                "estatus," +
                "usuario_id," +
                "usuario_nombre," +
                "fecha_inicio," +
                "fecha_fin," +
                "fecha_envio," +
                "created_at" +
                ") VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        SQLiteStatement pInsert = db.compileStatement(sql);

        pInsert.bindLong(1, gestion.getVerificacionDomiciliariaId());
        pInsert.bindString(2, gestion.getLatitud());
        pInsert.bindString(3, gestion.getLongitud());
        pInsert.bindString(4, gestion.getFotoFachada());
        pInsert.bindLong(5, gestion.getDomicilioCoincide());
        pInsert.bindString(6, gestion.getComentario());
        pInsert.bindLong(7, gestion.getEstatus());
        pInsert.bindLong(8, gestion.getUsuarioId());
        pInsert.bindString(9, gestion.getUsuarioNombre());
        pInsert.bindString(10, gestion.getFechaInicio());
        pInsert.bindString(11, gestion.getFechaFin());
        pInsert.bindString(12, gestion.getFechaEnvio());
        pInsert.bindString(13, gestion.getCreatedAt());

        id = pInsert.executeInsert();

        db.setTransactionSuccessful();
        db.endTransaction();

        return id;
    }

    public void update(GestionVerificacionDomiciliaria gestion)
    {
        ContentValues cv = new ContentValues();
        cv.put("latitud", gestion.getLatitud());
        cv.put("longitud", gestion.getLongitud());
        cv.put("foto_fachada", gestion.getFotoFachada());
        cv.put("domicilio_coincide", gestion.getDomicilioCoincide());
        cv.put("comentario", gestion.getComentario());
        cv.put("estatus", gestion.getEstatus());
        cv.put("usuario_id", gestion.getUsuarioId());
        cv.put("usuario_nombre", gestion.getUsuarioNombre());
        cv.put("fecha_inicio", gestion.getFechaInicio());
        cv.put("fecha_fin", gestion.getFechaFin());

        db.update(TBL_GESTIONES_VER_DOM, cv, "_id = ?", new String[]{String.valueOf(gestion.getId())});
    }

    public List<GestionVerificacionDomiciliaria> findAllByEstatus(Long estatus)
    {
        List<GestionVerificacionDomiciliaria> gestiones = new ArrayList<>();

        String sql = "SELECT " +
                "* " +
                "FROM " + TBL_GESTIONES_VER_DOM + " " +
                "WHERE estatus = ?"
        ;

        Cursor row = db.rawQuery(sql, new String[]{String.valueOf(estatus)});

        if(row.getCount() > 0)
        {
            row.moveToFirst();

            GestionVerificacionDomiciliaria gestion = new GestionVerificacionDomiciliaria();
            gestion.setId(row.getLong(0));
            gestion.setVerificacionDomiciliariaId(row.getLong(1));
            gestion.setLatitud(row.getString(2));
            gestion.setLongitud(row.getString(3));
            gestion.setFotoFachada(row.getString(4));
            gestion.setDomicilioCoincide(row.getInt(5));
            gestion.setComentario(row.getString(6));
            gestion.setEstatus(row.getInt(7));
            gestion.setUsuarioId(row.getLong(8));
            gestion.setUsuarioNombre(row.getString(9));
            gestion.setFechaInicio(row.getString(10));
            gestion.setFechaFin(row.getString(11));
            gestion.setFechaEnvio(row.getString(12));
            gestion.setCreatedAt(row.getString(13));

            gestiones.add(gestion);
        }

        row.close();

        return gestiones;
    }

    public GestionVerificacionDomiciliaria findByVerificacionDomiciliariaId(Long verificacionDomiciliariaId)
    {
        GestionVerificacionDomiciliaria gestion = null;

        String sql = "SELECT " +
                "* " +
                "FROM " + TBL_GESTIONES_VER_DOM + " " +
                "WHERE verificacion_domiciliaria_id = ?"
                ;

        Cursor row = db.rawQuery(sql, new String[]{String.valueOf(verificacionDomiciliariaId)});

        if(row.getCount() > 0)
        {
            row.moveToFirst();

            gestion = new GestionVerificacionDomiciliaria();
            gestion.setId(row.getLong(0));
            gestion.setVerificacionDomiciliariaId(row.getLong(1));
            gestion.setLatitud(row.getString(2));
            gestion.setLongitud(row.getString(3));
            gestion.setFotoFachada(row.getString(4));
            gestion.setDomicilioCoincide(row.getInt(5));
            gestion.setComentario(row.getString(6));
            gestion.setEstatus(row.getInt(7));
            gestion.setUsuarioId(row.getLong(8));
            gestion.setUsuarioNombre(row.getString(9));
            gestion.setFechaInicio(row.getString(10));
            gestion.setFechaFin(row.getString(11));
            gestion.setFechaEnvio(row.getString(12));
            gestion.setCreatedAt(row.getString(13));
        }

        row.close();

        return gestion;
    }
}
