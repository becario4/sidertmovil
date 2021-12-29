package com.sidert.sidertmovil.models.solicitudes;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.sidert.sidertmovil.database.DBhelper;
import com.sidert.sidertmovil.utils.Constants;

public class PrestamoToRenovarDao{
    final DBhelper dbHelper;
    final SQLiteDatabase db;

    public PrestamoToRenovarDao(Context ctx)
    {
        this.dbHelper = new DBhelper(ctx);
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
