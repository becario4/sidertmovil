package com.sidert.sidertmovil.models.solicitudes.solicitudgpo.renovacion;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.sidert.sidertmovil.database.DBhelper;

import static com.sidert.sidertmovil.utils.Constants.TBL_DOMICILIO_INTEGRANTE_REN;

public class DomicilioIntegranteRenDao {
    final DBhelper dbHelper;
    final SQLiteDatabase db;

    public DomicilioIntegranteRenDao(Context ctx){
        this.dbHelper = new DBhelper(ctx);
        this.db = dbHelper.getWritableDatabase();
    }

    public DomicilioIntegranteRen findByIdIntegrante(Long idIntegrante)
    {
        DomicilioIntegranteRen domicilio = null;

        String sql = "SELECT * FROM " + TBL_DOMICILIO_INTEGRANTE_REN + " WHERE id_integrante = ?";

        Cursor row = db.rawQuery(sql, new String[]{String.valueOf(idIntegrante)});

        if(row.getCount() > 0)
        {
            row.moveToFirst();

            domicilio = new DomicilioIntegranteRen();

            fill(row, domicilio);
        }

        row.close();

        return domicilio;
    }

    public void fill(Cursor row, DomicilioIntegranteRen domicilio)
    {
        domicilio.setIdDomicilio(row.getInt(0));
        domicilio.setIdIntegrante(row.getInt(1));
        domicilio.setLatitud(row.getString(2));
        domicilio.setLongitud(row.getString(3));
        domicilio.setCalle(row.getString(4));
        domicilio.setNoExterior(row.getString(5));
        domicilio.setNoInterior(row.getString(6));
        domicilio.setManzana(row.getString(7));
        domicilio.setLote(row.getString(8));
        domicilio.setCp(row.getString(9));
        domicilio.setColonia(row.getString(10));
        domicilio.setCiudad(row.getString(11));
        domicilio.setLocalidad(row.getString(12));
        domicilio.setMunicipio(row.getString(13));
        domicilio.setEstado(row.getString(14));
        domicilio.setTipoVivienda(row.getString(15));
        domicilio.setParentesco(row.getString(16));
        domicilio.setOtroTipoVivienda(row.getString(17));
        domicilio.setTiempoVivirSitio(row.getString(18));
        domicilio.setFotoFachada(row.getString(19));
        domicilio.setRefDomiciliaria(row.getString(20));
        domicilio.setEstatusCompletado(row.getInt(21));
        domicilio.setDependientes(row.getString(22));
        domicilio.setLocatedAt(row.getString(23));

    }
}
