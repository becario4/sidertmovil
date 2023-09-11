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

import static com.sidert.sidertmovil.utils.Constants.TBL_PRESTAMOS;
import static com.sidert.sidertmovil.utils.Constants.TBL_RECIBOS_AGF_CC;
import static com.sidert.sidertmovil.utils.Constants.TBL_RECUPERACION_RECIBOS;

public class PrestamoDao {
    final DBhelper dBhelper;
    final SQLiteDatabase db;

    public PrestamoDao(Context ctx)
    {
        this.dBhelper = DBhelper.getInstance(ctx);
        this.db = dBhelper.getWritableDatabase();
    }

    public Prestamo findByGrupoIdAndNumSolicitud(int grupoId, int numSolicitud)
    {
        Prestamo prestamo = null;

        String sql = "SELECT * FROM " + TBL_PRESTAMOS + " WHERE grupo_id = ? AND num_solicitud = ?";
        Cursor row = db.rawQuery(sql, new String[]{String.valueOf(grupoId), String.valueOf(numSolicitud)});

        if (row.getCount() > 0){
            row.moveToFirst();
            prestamo = new Prestamo();
            prestamo.setId(row.getInt(0));
            prestamo.setNombreGrupo(row.getString(1));
            prestamo.setGrupoId(row.getInt(2));
            prestamo.setClienteId(row.getString(3));
            prestamo.setNumSolicitud(row.getInt(4));
            prestamo.setPeriodicidad(row.getInt(5));
            prestamo.setNumPagos(row.getInt(6));
            prestamo.setEstadoNacimientoId(row.getString(7));
            prestamo.setGenero(row.getString(8));
            prestamo.setNombreCliente(row.getString(9));
            prestamo.setFechaNacimiento(row.getString(10));
            prestamo.setEdad(row.getString(11));
            prestamo.setMonto(row.getString(12));
            prestamo.setFechaEntrega(row.getString(13));
            prestamo.setEstatusPrestamoId(row.getInt(14));
        }

        row.close();

        return prestamo;
    }

    public Prestamo findByClienteIdAndNumSolicitud(String clienteId, int numSolicitud)
    {
        Prestamo prestamo = null;

        String sql = "SELECT * FROM " + TBL_PRESTAMOS + " WHERE cliente_id = ? AND num_solicitud = ?";
        Cursor row = db.rawQuery(sql, new String[]{clienteId, String.valueOf(numSolicitud)});

        if (row.getCount() > 0){
            row.moveToFirst();
            prestamo = new Prestamo();
            prestamo.setId(row.getInt(0));
            prestamo.setNombreGrupo(row.getString(1));
            prestamo.setGrupoId(row.getInt(2));
            prestamo.setClienteId(row.getString(3));
            prestamo.setNumSolicitud(row.getInt(4));
            prestamo.setPeriodicidad(row.getInt(5));
            prestamo.setNumPagos(row.getInt(6));
            prestamo.setEstadoNacimientoId(row.getString(7));
            prestamo.setGenero(row.getString(8));
            prestamo.setNombreCliente(row.getString(9));
            prestamo.setFechaNacimiento(row.getString(10));
            prestamo.setEdad(row.getString(11));
            prestamo.setMonto(row.getString(12));
            prestamo.setFechaEntrega(row.getString(13));
            prestamo.setEstatusPrestamoId(row.getInt(14));
        }

        row.close();

        return prestamo;
    }

    public List<Prestamo> findAllByCustomFilters(String[] filters)
    {
        List<Prestamo> prestamos = new ArrayList<>();

        String sql = "SELECT p.*, COALESCE(r.estatus, -1) AS estatus " +
                "FROM " + TBL_PRESTAMOS + " AS p " +
                "LEFT JOIN " + TBL_RECUPERACION_RECIBOS + " AS r on r.grupo_id = p.grupo_id AND r.num_solicitud = p.num_solicitud " +
                "WHERE (r.tipo is null or r.tipo = 'AGF') " +
                "AND (r._id is null or r.estatus < 2) " +
                "AND ((p.nombre_grupo like '%'||?||'%' AND p.grupo_id > 1) OR (p.nombre_cliente LIKE '%'||?||'%' AND p.grupo_id = 1)) " +
                "AND (p.grupo_id > 1 OR ? = '0') " +
                "AND (p.grupo_id = 1 OR ? = '0')" +
                "AND p.fecha_entrega between DATE('now', '-10 day') and DATE('now', '+10 day')" +
                "AND p.estatus_prestamo_id in (2, 3)";
        Cursor row = db.rawQuery(sql, filters);

        if (row.getCount() > 0){
            row.moveToFirst();

            for(int i = 0; i < row.getCount(); i++)
            {
                Prestamo p = new Prestamo();
                p.setId(row.getInt(0));
                p.setNombreGrupo(row.getString(1));
                p.setGrupoId(row.getInt(2));
                p.setClienteId(row.getString(3));
                p.setNumSolicitud(row.getInt(4));
                p.setPeriodicidad(row.getInt(5));
                p.setNumPagos(row.getInt(6));
                p.setEstadoNacimientoId(row.getString(7));
                p.setGenero(row.getString(8));
                p.setNombreCliente(row.getString(9));
                p.setFechaNacimiento(row.getString(10));
                p.setEdad(row.getString(11));
                p.setMonto(row.getString(12));
                p.setFechaEntrega(row.getString(13));
                p.setEstatusPrestamoId(row.getInt(14));
                p.setEstatusRecibo(row.getInt(15));

                prestamos.add(p);

                row.moveToNext();
            }
        }

        row.close();

        return prestamos;
    }

    public void store(Prestamo prestamo)
    {
        db.beginTransaction();

        String sql = "INSERT INTO " + TBL_PRESTAMOS + "(" +
            "nombre_grupo, " +
            "grupo_id, " +
            "cliente_id, " +
            "num_solicitud, " +
            "periodicidad, " +
            "num_pagos, " +
            "estado_nacimiento, " +
            "genero, " +
            "nombre_cliente, " +
            "fecha_nacimiento, " +
            "edad, " +
            "monto," +
            "fecha_entrega," +
            "estatus_prestamo_id" +
        ") " +
        "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        SQLiteStatement pInsert = db.compileStatement(sql);

        pInsert.bindString(1, prestamo.getNombreGrupo());
        pInsert.bindString(2, String.valueOf(prestamo.getGrupoId()));
        pInsert.bindString(3, prestamo.getClienteId());
        pInsert.bindString(4, String.valueOf(prestamo.getNumSolicitud()));
        pInsert.bindString(5, String.valueOf(prestamo.getPeriodicidad()));
        pInsert.bindString(6, String.valueOf(prestamo.getNumPagos()));
        pInsert.bindString(7, prestamo.getEstadoNacimientoId());
        pInsert.bindString(8, prestamo.getGenero());
        pInsert.bindString(9, prestamo.getNombreCliente());
        pInsert.bindString(10, prestamo.getFechaNacimiento());
        pInsert.bindString(11, prestamo.getEdad());
        pInsert.bindString(12, prestamo.getMonto());
        pInsert.bindString(13, prestamo.getFechaEntrega());
        pInsert.bindString(14, String.valueOf(prestamo.getEstatusPrestamoId()));

        pInsert.execute();

        db.setTransactionSuccessful();
        db.endTransaction();
    }

    public void update(int id, Prestamo prestamo)
    {
        ContentValues cv = new ContentValues();
        cv.put("cliente_id", prestamo.getClienteId());
        cv.put("periodicidad", prestamo.getPeriodicidad());
        cv.put("num_pagos", prestamo.getNumPagos());
        cv.put("estado_nacimiento", prestamo.getEstadoNacimientoId());
        cv.put("genero", prestamo.getGenero());
        cv.put("nombre_cliente", prestamo.getNombreCliente());
        cv.put("fecha_nacimiento", prestamo.getFechaNacimiento());
        cv.put("edad", prestamo.getEdad());
        cv.put("monto", prestamo.getMonto());
        cv.put("fecha_entrega", prestamo.getFechaEntrega());
        cv.put("estatus_prestamo_id", prestamo.getEstatusPrestamoId());

        db.update(TBL_PRESTAMOS, cv, "_id = ?", new String[]{String.valueOf(id)});
    }

    public List<String> showNombres()
    {
        List<String> nombres = new ArrayList<>();

        String sql = "SELECT DISTINCT(pivot.nombre) AS nombre FROM (" +
                "SELECT (CASE WHEN p.grupo_id = 1 THEN substr(p.nombre_cliente, 2) ELSE p.nombre_grupo END) AS nombre FROM " + TBL_PRESTAMOS + " AS p " +
                "where p.fecha_entrega between DATE('now', '-180 day') and DATE('now', '+10 day')" +
                "AND p.estatus_prestamo_id in (2, 3) " +
                ") AS pivot";
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
