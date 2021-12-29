package com.sidert.sidertmovil.models.cierrededia;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.sidert.sidertmovil.database.DBhelper;

import static com.sidert.sidertmovil.utils.Constants.TBL_CIERRE_DIA_T;

public class CierreDeDiaDao {
    final DBhelper dBhelper;
    final SQLiteDatabase db;

    public CierreDeDiaDao(Context ctx)
    {
        this.dBhelper = new DBhelper(ctx);
        this.db = dBhelper.getWritableDatabase();
    }

    public CierreDeDia findByIdRespuestaAndAsesorId(String idRespuesta, String asesorId)
    {
        CierreDeDia cierreDeDia = null;

        String sql = "SELECT * FROM " + TBL_CIERRE_DIA_T + " WHERE id_respuesta = ? AND asesor_id = ? ";
        Cursor row = db.rawQuery(sql, new String[]{idRespuesta, asesorId});

        if(row.getCount() > 0)
        {
            row.moveToFirst();

            cierreDeDia = new CierreDeDia();
            cierreDeDia.setId(row.getInt(0));
            cierreDeDia.setAsesorId(row.getString(1));
            cierreDeDia.setIdRespuesta(row.getString(2));
            cierreDeDia.setNumPrestamo(row.getString(3));
            cierreDeDia.setClaveCliente(row.getString(4));
            cierreDeDia.setMedioPago(row.getString(5));
            cierreDeDia.setMontoDepositado(row.getString(6));
            cierreDeDia.setEvidencia(row.getString(7));
            cierreDeDia.setTipoCierre(row.getString(8));
            cierreDeDia.setTipoPrestamo(row.getString(9));
            cierreDeDia.setFechaInicio(row.getString(10));
            cierreDeDia.setFechaFin(row.getString(11));
            cierreDeDia.setFechaEnvio(row.getString(12));
            cierreDeDia.setEstatus(row.getInt(13));
            cierreDeDia.setNombre(row.getString(14));
            cierreDeDia.setSerialId(row.getString(15));

        }

        row.close();

        return cierreDeDia;
    }

    public Long store(CierreDeDia cierreDeDia)
    {
        Long id;

        db.beginTransaction();

        String sql = "INSERT INTO " + TBL_CIERRE_DIA_T + " (" +
            "asesor_id," +
            "id_respuesta," +
            "num_prestamo," +
            "clave_cliente," +
            "medio_pago," +
            "monto_depositado," +
            "evidencia," +
            "tipo_cierre," +
            "tipo_prestamo," +
            "fecha_inicio," +
            "fecha_fin," +
            "fecha_envio," +
            "estatus," +
            "nombre," +
            "serial_id" +
        ") " +
        "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        SQLiteStatement pInsert = db.compileStatement(sql);

        pInsert.bindString(1, cierreDeDia.getAsesorId());
        pInsert.bindString(2, cierreDeDia.getIdRespuesta());
        pInsert.bindString(3, cierreDeDia.getNumPrestamo());
        pInsert.bindString(4, cierreDeDia.getClaveCliente());
        pInsert.bindString(5, cierreDeDia.getMedioPago());
        pInsert.bindString(6, cierreDeDia.getMontoDepositado());
        pInsert.bindString(7, cierreDeDia.getEvidencia());
        pInsert.bindString(8, cierreDeDia.getTipoCierre());
        pInsert.bindString(9, cierreDeDia.getTipoPrestamo());
        pInsert.bindString(10, cierreDeDia.getFechaInicio());
        pInsert.bindString(11, cierreDeDia.getFechaFin());
        pInsert.bindString(12, cierreDeDia.getFechaEnvio());
        pInsert.bindLong(13, cierreDeDia.getEstatus());
        pInsert.bindString(14, cierreDeDia.getNombre());
        pInsert.bindString(15, cierreDeDia.getSerialId());

        id = pInsert.executeInsert();

        db.setTransactionSuccessful();
        db.endTransaction();

        return id;
    }
}
