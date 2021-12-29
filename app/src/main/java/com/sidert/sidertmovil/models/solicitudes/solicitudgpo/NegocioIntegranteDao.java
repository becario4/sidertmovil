package com.sidert.sidertmovil.models.solicitudes.solicitudgpo;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.sidert.sidertmovil.database.DBhelper;

import static com.sidert.sidertmovil.utils.Constants.TBL_NEGOCIO_INTEGRANTE;

public class NegocioIntegranteDao {
    final DBhelper dbHelper;
    final SQLiteDatabase db;

    public NegocioIntegranteDao(Context ctx){
        this.dbHelper = new DBhelper(ctx);
        this.db = dbHelper.getWritableDatabase();
    }

    public NegocioIntegrante findByIdIntegrante(Long idIntegrante)
    {
        NegocioIntegrante negocio = null;

        String sql = "SELECT * FROM " + TBL_NEGOCIO_INTEGRANTE + " WHERE id_integrante = ?";

        Cursor row = db.rawQuery(sql, new String[]{String.valueOf(idIntegrante)});

        if(row.getCount() > 0)
        {
            row.moveToFirst();

            negocio = new NegocioIntegrante();

            fill(row, negocio);
        }

        row.close();

        return negocio;
    }

    public void fill(Cursor row, NegocioIntegrante negocio)
    {
        negocio.setIdNegocio(row.getInt(0));
        negocio.setIdIntegrante(row.getInt(1));
        negocio.setNombre(row.getString(2));
        negocio.setLatitud(row.getString(3));
        negocio.setLongitud(row.getString(4));
        negocio.setCalle(row.getString(5));
        negocio.setNoExterior(row.getString(6));
        negocio.setNoInterior(row.getString(7));
        negocio.setManzana(row.getString(8));
        negocio.setLote(row.getString(9));
        negocio.setCp(row.getString(10));
        negocio.setColonia(row.getString(11));
        negocio.setCiudad(row.getString(12));
        negocio.setLocalidad(row.getString(13));
        negocio.setMunicipio(row.getString(14));
        negocio.setEstado(row.getString(15));
    }
}
