package com.sidert.sidertmovil.models.catalogos;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.sidert.sidertmovil.models.BaseDao;

import java.util.ArrayList;
import java.util.List;

import static com.sidert.sidertmovil.utils.Constants.K_MEDIOS_PAGOS;

public class MedioPagoDao extends BaseDao {

    public MedioPagoDao(Context ctx) {
        super(ctx);
    }

    public String findIdsByNombres(String nombresMediosPago)
    {
        String nombres = MedioPago.getIdMediosPago(nombresMediosPago);
        Log.e("AQUI MEDIOS PAGO", nombres);
        String mediosPagosIds = "";

        String sql = "" +
            "SELECT * " +
            "FROM " + MedioPago.TBL + " AS t " +
            "WHERE t.nombre IN (" + nombres +")"
        ;

        Cursor row  = db.rawQuery(sql, null);

        Log.e("AQUI MEDIOS PAGO", "" + row.getCount());
        List<MedioPago> mediosPago = new ArrayList<>();
        for(int i = 0; i < row.getCount(); i++) mediosPago.add(new MedioPago());
        findAll(row, mediosPago );
        Log.e("AQUI MEDIOS PAGO", "" + mediosPago.size());
        for(int i = 0; i < mediosPago.size(); i++)
        {
            if (i == 0)
                mediosPagosIds = String.valueOf(mediosPago.get(i).getId());
            else
                mediosPagosIds += "," + mediosPago.get(i).getId();
        }

        Log.e("AQUI MEDIOS PAGO", mediosPagosIds);
        return mediosPagosIds;
    }
}
