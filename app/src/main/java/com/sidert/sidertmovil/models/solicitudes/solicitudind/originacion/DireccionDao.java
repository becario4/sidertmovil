package com.sidert.sidertmovil.models.solicitudes.solicitudind.originacion;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.sidert.sidertmovil.database.DBhelper;

import static com.sidert.sidertmovil.utils.Constants.TBL_DIRECCIONES;

public class DireccionDao {
    final DBhelper dbHelper;
    final SQLiteDatabase db;

    public DireccionDao(Context ctx){
        this.dbHelper = DBhelper.getInstance(ctx);
        this.db = dbHelper.getWritableDatabase();
    }

    public Direccion findByIdDireccion(Long idDireccion)
    {
        Direccion direccion = null;

        String sql = "SELECT * FROM " + TBL_DIRECCIONES + " WHERE id_direccion = ?";

        Cursor row = db.rawQuery(sql, new String[]{String.valueOf(idDireccion)});

        if(row.getCount() > 0)
        {
            row.moveToFirst();

            direccion = new Direccion();

            fill(row, direccion);
        }

        row.close();

        return direccion;
    }

    public void fill(Cursor row, Direccion direccion)
    {
        direccion.setIdDireccion(row.getInt(0));
        direccion.setTipoDireccion(row.getString(1));
        direccion.setLatitud(row.getString(2));
        direccion.setLongitud(row.getString(3));
        direccion.setCalle(row.getString(4));
        direccion.setNumExterior(row.getString(5));
        direccion.setNumInterior(row.getString(6));
        direccion.setLote(row.getString(7));
        direccion.setManzana(row.getString(8));
        direccion.setCp(row.getString(9));
        direccion.setColonia(row.getString(10));
        direccion.setCiudad(row.getString(11));
        direccion.setLocalidad(row.getString(12));
        direccion.setMunicipio(row.getString(13));
        direccion.setEstado(row.getString(14));
        direccion.setLocatedAt(row.getString(15));

    }

}
