package com.sidert.sidertmovil.models.apoyogastosfunerarios;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.sidert.sidertmovil.database.DBhelper;

import static com.sidert.sidertmovil.utils.Constants.TBL_PRESTAMOS;
import static com.sidert.sidertmovil.utils.Constants.TBL_RECIBOS;

public class ApoyoGastosFunerariosDao {
    final DBhelper dBhelper;
    final SQLiteDatabase db;

    public ApoyoGastosFunerariosDao(Context ctx)
    {
        this.dBhelper = new DBhelper(ctx);
        this.db = dBhelper.getWritableDatabase();
    }

    public ApoyoGastosFunerarios findByGrupoIdAndNumSolicitud(int grupoId, int numSolicitud)
    {
        ApoyoGastosFunerarios agf = null;

        String sql = "SELECT r.* " +
                "FROM " + TBL_RECIBOS + " AS r " +
                "JOIN " + TBL_PRESTAMOS + " AS p ON p._id = r.prestamo_id " +
                "WHERE p.grupo_id = ? AND p.num_solicitud = ?";
        Cursor row = db.rawQuery(sql, new String[]{String.valueOf(grupoId), String.valueOf(numSolicitud)});

        if (row.getCount() > 0){
            row.moveToFirst();

            agf = new ApoyoGastosFunerarios();
            agf.setId(row.getInt(0));
            agf.setPrestamoId(row.getString(1));
            agf.setAsesorId(row.getString(2));
            agf.setTipoRecibo(row.getString(3));
            agf.setTipoImpresion(row.getString(4));
            agf.setFolio(row.getString(5));
            agf.setMonto(row.getString(6));
            agf.setClave(row.getString(7));
            agf.setNombre(row.getString(8));
            agf.setApPaterno(row.getString(9));
            agf.setApMaterno(row.getString(10));
            agf.setFechaImpreso(row.getString(11));
            agf.setFechaEnvio(row.getString(12));
            agf.setEstatus(row.getInt(13));
            agf.setCurp(row.getString(14));
        }

        row.close();

        return agf;
    }

    public ApoyoGastosFunerarios findByClienteIdAndNumSolicitud(String clienteId, int numSolicitud)
    {
        ApoyoGastosFunerarios agf = null;

        String sql = "SELECT r.* " +
                "FROM " + TBL_RECIBOS + " AS r " +
                "JOIN " + TBL_PRESTAMOS + " AS p ON p._id = r.prestamo_id " +
                "WHERE p.cliente_id = ? AND p.num_solicitud = ?";
        Cursor row = db.rawQuery(sql, new String[]{clienteId, String.valueOf(numSolicitud)});

        if (row.getCount() > 0){
            row.moveToFirst();

            agf = new ApoyoGastosFunerarios();
            agf.setId(row.getInt(0));
            agf.setPrestamoId(row.getString(1));
            agf.setAsesorId(row.getString(2));
            agf.setTipoRecibo(row.getString(3));
            agf.setTipoImpresion(row.getString(4));
            agf.setFolio(row.getString(5));
            agf.setMonto(row.getString(6));
            agf.setClave(row.getString(7));
            agf.setNombre(row.getString(8));
            agf.setApPaterno(row.getString(9));
            agf.setApMaterno(row.getString(10));
            agf.setFechaImpreso(row.getString(11));
            agf.setFechaEnvio(row.getString(12));
            agf.setEstatus(row.getInt(13));
            agf.setCurp(row.getString(14));
        }

        row.close();

        return agf;
    }
}
