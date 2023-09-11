package com.sidert.sidertmovil.models.solicitudes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.sidert.sidertmovil.database.DBhelper;
import com.sidert.sidertmovil.utils.Constants;

import static com.sidert.sidertmovil.utils.Constants.TBL_SOLICITUDES_REN;

public class PrestamoToRenovarDao{
    final DBhelper dbHelper;
    final SQLiteDatabase db;

    public PrestamoToRenovarDao(Context ctx)
    {
        this.dbHelper = DBhelper.getInstance(ctx);
        this.db = dbHelper.getWritableDatabase();
    }

    public PrestamoToRenovar findByClienteNombre(String clienteNombre)
    {
        PrestamoToRenovar prestamoToRenovar = null;

        String sql = "" +
            "SELECT * " +
            "FROM " + Constants.TBL_PRESTAMOS_TO_RENOVAR + " AS p " +
            "WHERE p.cliente_nombre = ? " +
            "ORDER BY p.fecha_vencimiento "
        ;

        Cursor row = db.rawQuery(sql, new String[]{clienteNombre});

        if(row.getCount() > 0)
        {
            row.moveToFirst();

            prestamoToRenovar = new PrestamoToRenovar();

            fill(row, prestamoToRenovar);
        }

        row.close();

        return prestamoToRenovar;

    }

    public PrestamoToRenovar findLikeClienteNombreAndFechaVencimiento(String clienteNombre, String fechaVencimiento)
    {
        PrestamoToRenovar prestamoToRenovar = null;

        String sql = "" +
                "SELECT * " +
                "FROM " + Constants.TBL_PRESTAMOS_TO_RENOVAR + " AS p " +
                "WHERE p.cliente_nombre like '%'||?||'%' " +
                "AND p.fecha_vencimiento = ?" +
                "ORDER BY p.fecha_vencimiento "
                ;

        Cursor row = db.rawQuery(sql, new String[]{clienteNombre, fechaVencimiento});

        if(row.getCount() > 0)
        {
            row.moveToFirst();

            prestamoToRenovar = new PrestamoToRenovar();

            fill(row, prestamoToRenovar);
        }

        row.close();

        return prestamoToRenovar;
    }

    public PrestamoToRenovar findLikeClienteNombre(String clienteNombre)
    {
        PrestamoToRenovar prestamoToRenovar = null;

        String sql = "" +
                "SELECT * " +
                "FROM " + Constants.TBL_PRESTAMOS_TO_RENOVAR + " AS p " +
                "WHERE p.cliente_nombre like '%'||?||'%' " +
                "ORDER BY p.fecha_vencimiento "
                ;

        Cursor row = db.rawQuery(sql, new String[]{clienteNombre});

        if(row.getCount() > 0)
        {
            row.moveToFirst();

            prestamoToRenovar = new PrestamoToRenovar();

            fill(row, prestamoToRenovar);
        }

        row.close();

        return prestamoToRenovar;

    }

    public PrestamoToRenovar findByPrestamoId(Integer prestamoId)
    {
        PrestamoToRenovar prestamoToRenovar = null;

        String sql = "" +
                "SELECT * " +
                "FROM " + Constants.TBL_PRESTAMOS_TO_RENOVAR + " AS p " +
                "WHERE p.prestamo_id = ? " +
                "ORDER BY p.fecha_vencimiento "
                ;

        Cursor row = db.rawQuery(sql, new String[]{String.valueOf(prestamoId)});

        if(row.getCount() > 0)
        {
            row.moveToFirst();

            prestamoToRenovar = new PrestamoToRenovar();

            fill(row, prestamoToRenovar);
        }

        row.close();

        return prestamoToRenovar;

    }

    public PrestamoToRenovar findByClienteNombreAndPosition(String clienteNombre, Integer position)
    {
        PrestamoToRenovar prestamoToRenovar = null;

        String sql = "" +
                "SELECT * " +
                "FROM " + Constants.TBL_PRESTAMOS_TO_RENOVAR + " AS p " +
                "WHERE trim(p.cliente_nombre) = trim(?) " +
                "ORDER BY p.fecha_vencimiento " +
                "LIMIT 1 " +
                "OFFSET " + position
                ;

        Cursor row = db.rawQuery(sql, new String[]{clienteNombre});

        if(row.getCount() > 0)
        {
            row.moveToFirst();

            prestamoToRenovar = new PrestamoToRenovar();

            fill(row, prestamoToRenovar);
        }

        row.close();

        return prestamoToRenovar;

    }

    public void update(PrestamoToRenovar prestamoToRenovar) {
        ContentValues cv = new ContentValues();

        cv.put("cliente_nombre", prestamoToRenovar.getClienteNombre());

        db.update(Constants.TBL_PRESTAMOS_TO_RENOVAR, cv, "cliente_id = ?", new String[]{String.valueOf(prestamoToRenovar.getClienteId())});
    }

    public void fill(Cursor row, PrestamoToRenovar prestamoToRenovar)
    {
        prestamoToRenovar.setId(row.getInt(0));
        prestamoToRenovar.setAsesorId(row.getString(1));
        prestamoToRenovar.setPrestamoId(row.getInt(2));
        prestamoToRenovar.setClienteId(row.getInt(3));
        prestamoToRenovar.setClienteNombre(row.getString(4));
        prestamoToRenovar.setNoPrestamo(row.getString(5));
        prestamoToRenovar.setFechaVencimiento(row.getString(6));
        prestamoToRenovar.setNumPagos(row.getInt(7));
        prestamoToRenovar.setDescargado(row.getInt(8));
        prestamoToRenovar.setTipoPrestamo(row.getInt(9));
        prestamoToRenovar.setGrupoId(row.getString(10));

    }

}
