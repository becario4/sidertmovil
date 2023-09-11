package com.sidert.sidertmovil.models.impresion.carteravencida;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.sidert.sidertmovil.database.DBhelper;

import static com.sidert.sidertmovil.utils.Constants.TBL_IMPRESIONES_VENCIDA_T;

public class ImpresionVencidaDao {
    final DBhelper dBhelper;
    final SQLiteDatabase db;

    public ImpresionVencidaDao(Context ctx)
    {
        this.dBhelper = DBhelper.getInstance(ctx);
        this.db = dBhelper.getWritableDatabase();
    }

    public ImpresionVencida findByNumPrestamoAndCreateAt(String numPrestamo, String createAt)
    {
        ImpresionVencida impresionVencida = null;

        String sql = "SELECT * FROM " + TBL_IMPRESIONES_VENCIDA_T + " WHERE num_prestamo = ? AND create_at = ?";
        Cursor row = db.rawQuery(sql, new String[]{numPrestamo, createAt});

        if (row.getCount() > 0) {
            row.moveToFirst();

            impresionVencida = new ImpresionVencida();

            impresionVencida.setId(row.getInt(0));
            impresionVencida.setNumPrestamoIdGestion(row.getString(1));
            impresionVencida.setAsesorId(row.getString(2));
            impresionVencida.setFolio(row.getInt(3));
            impresionVencida.setTipoImpresion(row.getString(4));
            impresionVencida.setMonto(row.getString(5));
            impresionVencida.setClaveCliente(row.getString(6));
            impresionVencida.setCreateAt(row.getString(7));
            impresionVencida.setSentAt(row.getString(8));
            impresionVencida.setEstatus(row.getInt(9));
            impresionVencida.setNumPrestamo(row.getString(10));
            impresionVencida.setCelular(row.getString(11));
        }

        return impresionVencida;
    }

    public Long store(ImpresionVencida impresionVencida)
    {
        Long id;

        db.beginTransaction();

        String sql = "INSERT INTO " + TBL_IMPRESIONES_VENCIDA_T + " (" +
            "num_prestamo_id_gestion," +
            "asesor_id," +
            "folio," +
            "tipo_impresion," +
            "monto," +
            "clave_cliente," +
            "create_at," +
            "sent_at," +
            "estatus," +
            "num_prestamo," +
            "celular" +
        ")" +
        "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        SQLiteStatement pInsert = db.compileStatement(sql);

        pInsert.bindString(1, impresionVencida.getNumPrestamoIdGestion());
        pInsert.bindString(2, impresionVencida.getAsesorId());
        pInsert.bindLong(3, impresionVencida.getFolio());
        pInsert.bindString(4, impresionVencida.getTipoImpresion());
        pInsert.bindString(5, impresionVencida.getMonto());
        pInsert.bindString(6, impresionVencida.getClaveCliente());
        pInsert.bindString(7, impresionVencida.getCreateAt());
        pInsert.bindString(8, impresionVencida.getSentAt());
        pInsert.bindLong(9, impresionVencida.getEstatus());
        pInsert.bindString(10, impresionVencida.getNumPrestamo());
        pInsert.bindString(11, impresionVencida.getCelular());

        id = pInsert.executeInsert();

        db.setTransactionSuccessful();
        db.endTransaction();

        return id;
    }


}
