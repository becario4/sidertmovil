package com.sidert.sidertmovil.models.cartera.amortizaciones;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.sidert.sidertmovil.database.DBhelper;

import java.util.ArrayList;
import java.util.List;

import static com.sidert.sidertmovil.utils.Constants.TBL_AMORTIZACIONES_T;

public class AmortizacionDao {
    final DBhelper dbHelper;
    final SQLiteDatabase db;

    public AmortizacionDao(Context ctx){
        this.dbHelper = DBhelper.getInstance(ctx);
        this.db = dbHelper.getWritableDatabase();
    }

    public List<Amortizacion> findByIdPrestamo(String idPrestamo)
    {
        List<Amortizacion> amortizaciones = new ArrayList<>();

        String sql = "SELECT * FROM " + TBL_AMORTIZACIONES_T + " AS a WHERE a.id_prestamo = ? ORDER BY a.numero";
        Cursor row = db.rawQuery(sql, new String[]{idPrestamo});

        if(row.getCount() > 0)
        {
            row.moveToFirst();

            for(int i = 0; i < row.getCount(); i++)
            {
                Amortizacion amortizacion = new Amortizacion();

                Fill(row, amortizacion);

                amortizaciones.add(amortizacion);

                row.moveToNext();
            }
        }

        row.close();

        return amortizaciones;
    }

    public Amortizacion findByIdAmortizacion(String idAmortizacion)
    {
        Amortizacion amortizacion = null;

        String sql = "SELECT * FROM " + TBL_AMORTIZACIONES_T + " AS a WHERE a.id_amortizacion = ?";
        Cursor row = db.rawQuery(sql, new String[]{idAmortizacion});

        if(row.getCount() > 0)
        {
            row.moveToFirst();

            amortizacion = new Amortizacion();

            Fill(row, amortizacion);
        }

        row.close();

        return amortizacion;
    }

    public Amortizacion findByIdPrestamoAndNumero(String idPrestamo, String numero)
    {
        Amortizacion amortizacion = null;

        String sql = "SELECT * FROM " + TBL_AMORTIZACIONES_T + " AS a WHERE a.id_prestamo = ? AND a.numero";
        Cursor row = db.rawQuery(sql, new String[]{idPrestamo, numero});

        if(row.getCount() > 0)
        {
            row.moveToFirst();

            amortizacion = new Amortizacion();

            Fill(row, amortizacion);
        }

        row.close();

        return amortizacion;
    }

    public long store(Amortizacion amortizacion)
    {
        Long id;
        String sql;

        db.beginTransaction();

        sql = "INSERT INTO " + TBL_AMORTIZACIONES_T + " ( " +
                "id_amortizacion," +
                "id_prestamo," +
                "fecha," +
                "fecha_pago," +
                "capital," +
                "interes," +
                "iva," +
                "comision," +
                "total," +
                "capital_pagado," +
                "interes_pagado," +
                "iva_pagado," +
                "interes_moratorio_pagado," +
                "iva_moratorio_pagado," +
                "comision_pagada," +
                "total_pagado," +
                "pagado," +
                "numero," +
                "dias_atraso," +
                "fecha_dispositivo," +
                "fecha_actualizado" +
        ") VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        SQLiteStatement pInsert = db.compileStatement(sql);

        pInsert.bindString(1, amortizacion.getIdAmortizacion());
        pInsert.bindString(2, amortizacion.getIdPrestamo());
        pInsert.bindString(3, amortizacion.getFecha());
        pInsert.bindString(4, amortizacion.getFechaPago());
        pInsert.bindString(5, amortizacion.getCapital());
        pInsert.bindString(6, amortizacion.getInteres());
        pInsert.bindString(7, amortizacion.getIva());
        pInsert.bindString(8, amortizacion.getComision());
        pInsert.bindString(9, amortizacion.getTotal());
        pInsert.bindString(10, amortizacion.getCapitalPagado());
        pInsert.bindString(11, amortizacion.getInteresPagado());
        pInsert.bindString(12, amortizacion.getIvaPagado());
        pInsert.bindString(13, amortizacion.getInteresMoratorioPagado());
        pInsert.bindString(14, amortizacion.getIvaMoratorioPagado());
        pInsert.bindString(15, amortizacion.getComisionPagada());
        pInsert.bindString(16, amortizacion.getTotalPagado());
        pInsert.bindString(17, amortizacion.getPagado());
        pInsert.bindString(18, amortizacion.getNumero());
        pInsert.bindString(19, amortizacion.getDiasAtraso());
        pInsert.bindString(20, amortizacion.getFechaDispositivo());
        pInsert.bindString(21, amortizacion.getFechaActualizado());

        id = pInsert.executeInsert();

        db.setTransactionSuccessful();
        db.endTransaction();

        return id;
    }

    public void update(Amortizacion amortizacion)
    {
        ContentValues cv = new ContentValues();

        cv.put("fecha", amortizacion.getFecha());
        cv.put("fecha_pago", amortizacion.getFechaPago());
        cv.put("capital", amortizacion.getCapital());
        cv.put("interes", amortizacion.getInteres());
        cv.put("iva", amortizacion.getIva());
        cv.put("comision", amortizacion.getComision());
        cv.put("total", amortizacion.getTotal());
        cv.put("capital_pagado", amortizacion.getCapitalPagado());
        cv.put("interes_pagado", amortizacion.getInteresPagado());
        cv.put("iva_pagado", amortizacion.getIvaPagado());
        cv.put("interes_moratorio_pagado", amortizacion.getInteresMoratorioPagado());
        cv.put("iva_moratorio_pagado", amortizacion.getIvaMoratorioPagado());
        cv.put("comision_pagada", amortizacion.getComisionPagada());
        cv.put("total_pagado", amortizacion.getTotalPagado());
        cv.put("pagado", amortizacion.getPagado());
        cv.put("numero", amortizacion.getNumero());
        cv.put("dias_atraso", amortizacion.getDiasAtraso());
        cv.put("fecha_dispositivo", amortizacion.getFechaDispositivo());
        cv.put("fecha_actualizado", amortizacion.getFechaActualizado());

        db.update(TBL_AMORTIZACIONES_T, cv, "_id = ?", new String[]{String.valueOf(amortizacion.getId())});
    }

    public void deleteByIdPrestamo(String idPrestamo)
    {
        db.delete(TBL_AMORTIZACIONES_T, "id_prestamo = ?", new String[]{idPrestamo});
    }

    private void Fill(Cursor row, Amortizacion amortizacion)
    {
        amortizacion.setId(row.getInt(0));
        amortizacion.setIdAmortizacion(row.getString(1));
        amortizacion.setIdPrestamo(row.getString(2));
        amortizacion.setFecha(row.getString(3));
        amortizacion.setFechaPago(row.getString(4));
        amortizacion.setCapital(row.getString(5));
        amortizacion.setInteres(row.getString(6));
        amortizacion.setIva(row.getString(7));
        amortizacion.setComision(row.getString(8));
        amortizacion.setTotal(row.getString(9));
        amortizacion.setCapitalPagado(row.getString(10));
        amortizacion.setInteresPagado(row.getString(11));
        amortizacion.setIvaPagado(row.getString(12));
        amortizacion.setInteresMoratorioPagado(row.getString(13));
        amortizacion.setIvaMoratorioPagado(row.getString(14));
        amortizacion.setComisionPagada(row.getString(15));
        amortizacion.setTotalPagado(row.getString(16));
        amortizacion.setPagado(row.getString(17));
        amortizacion.setNumero(row.getString(18));
        amortizacion.setDiasAtraso(row.getString(19));
        amortizacion.setFechaDispositivo(row.getString(20));
        amortizacion.setFechaActualizado(row.getString(21));
    }

}
