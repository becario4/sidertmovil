package com.sidert.sidertmovil.models.circulocredito;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.sidert.sidertmovil.database.DBhelper;

import java.util.ArrayList;
import java.util.List;

import static com.sidert.sidertmovil.utils.Constants.TBL_RECIBOS_CC;

public class ReciboCirculoCreditoDao {
    final DBhelper dbHelper;
    final SQLiteDatabase db;

    public ReciboCirculoCreditoDao(Context ctx)
    {
        this.dbHelper = DBhelper.getInstance(ctx);
        this.db = dbHelper.getWritableDatabase();
    }

    public long store(ReciboCirculoCredito reciboCC)
    {
        long id;

        db.beginTransaction();

        String sql = "INSERT INTO " + TBL_RECIBOS_CC + "(" +
        "tipo_credito, " +
        "nombre_uno, " +
        "curp, " +
        "nombre_dos, " +
        "total_integrantes, " +
        "monto, " +
        "tipo_impresion, " +
        "folio, " +
        "fecha_impresion, " +
        "fecha_envio, " +
        "estatus " +
        ") " +
        "VALUES(?,?,?,?,?,?,?,?,?,?,?)";

        SQLiteStatement pInsert = db.compileStatement(sql);

        pInsert.bindString(1, String.valueOf(reciboCC.getTipoCredito()));
        pInsert.bindString(2, reciboCC.getNombreUno());
        pInsert.bindString(3, reciboCC.getCurp());
        pInsert.bindString(4, reciboCC.getNombreDos());
        pInsert.bindString(5, String.valueOf(reciboCC.getTotalIntegrantes()));
        pInsert.bindString(6, reciboCC.getMonto());
        pInsert.bindString(7, reciboCC.getTipoImpresion());
        pInsert.bindString(8, String.valueOf(reciboCC.getFolio()));
        pInsert.bindString(9, reciboCC.getFechaImpresion());
        pInsert.bindString(10, reciboCC.getFechaEnvio());
        pInsert.bindString(11, String.valueOf(reciboCC.getEstatus()));

        id = pInsert.executeInsert();

        db.setTransactionSuccessful();
        db.endTransaction();

        return id;
    }

    public void update(Integer id, ReciboCirculoCredito reciboCC)
    {
        ContentValues cv = new ContentValues();
        cv.put("fecha_envio", reciboCC.getFechaEnvio());
        cv.put("estatus", reciboCC.getEstatus());

        db.update(TBL_RECIBOS_CC, cv, "_id = ?", new String[]{String.valueOf(id)});
    }

    public ReciboCirculoCredito findByCurpAndTipoImpresion(String curp, String tipoImpresion)
    {
        ReciboCirculoCredito reciboCirculoCredito = null;

        String sql = "SELECT r.* " +
                "FROM " + TBL_RECIBOS_CC + " AS r " +
                "WHERE r.curp = ? " +
                "AND r.tipo_impresion = ? "
                ;

        Cursor row = db.rawQuery(sql, new String[]{curp, tipoImpresion});

        if(row.getCount() > 0)
        {
            row.moveToFirst();

            reciboCirculoCredito = new ReciboCirculoCredito();

            reciboCirculoCredito.setId(row.getInt(0));
            reciboCirculoCredito.setTipoCredito(row.getInt(1));
            reciboCirculoCredito.setNombreUno(row.getString(2));
            reciboCirculoCredito.setCurp(row.getString(3));
            reciboCirculoCredito.setNombreDos(row.getString(4));
            reciboCirculoCredito.setTotalIntegrantes(row.getInt(5));
            reciboCirculoCredito.setMonto(row.getString(6));
            reciboCirculoCredito.setTipoImpresion(row.getString(7));
            reciboCirculoCredito.setFolio(row.getInt(8));
            reciboCirculoCredito.setFechaImpresion(row.getString(9));
            reciboCirculoCredito.setFechaEnvio(row.getString(10));
            reciboCirculoCredito.setEstatus(row.getInt(11));
        }

        row.close();

        return reciboCirculoCredito;
    }

    public List<ReciboCirculoCredito> findAllByCurp(String curp)
    {
        List<ReciboCirculoCredito> recibosCirculoCredito = new ArrayList<>();

        String sql = "SELECT r.* " +
                "FROM " + TBL_RECIBOS_CC + " AS r " +
                "WHERE r.curp = ? ";

        Cursor row = db.rawQuery(sql, new String[]{curp});

        if(row.getCount() > 0)
        {
            row.moveToFirst();

            for(int i = 0; i < row.getCount(); i++)
            {
                ReciboCirculoCredito rcc = new ReciboCirculoCredito();

                rcc.setId(row.getInt(0));
                rcc.setTipoCredito(row.getInt(1));
                rcc.setNombreUno(row.getString(2));
                rcc.setCurp(row.getString(3));
                rcc.setNombreDos(row.getString(4));
                rcc.setTotalIntegrantes(row.getInt(5));
                rcc.setMonto(row.getString(6));
                rcc.setTipoImpresion(row.getString(7));
                rcc.setFolio(row.getInt(8));
                rcc.setFechaImpresion(row.getString(9));
                rcc.setFechaEnvio(row.getString(10));
                rcc.setEstatus(row.getInt(11));

                recibosCirculoCredito.add(rcc);

                row.moveToNext();
            }
        }

        row.close();

        return recibosCirculoCredito;
    }
}
