package com.sidert.sidertmovil.models.catalogos;

import android.content.Context;
import android.database.Cursor;

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
        String mediosPagosIds = "";

        String sql = "" +
            "SELECT * " +
            "FROM " + MedioPago.TBL + " AS t " +
            "WHERE t.nombre IN (" + nombres +")"
        ;

        Cursor row  = db.rawQuery(sql, null);

        List<MedioPago> mediosPago = new ArrayList<>();
        for(int i = 0; i < row.getCount(); i++) mediosPago.add(new MedioPago());
        findAll(row, mediosPago );

        for(int i = 0; i < mediosPago.size(); i++)
        {
            if (i == 0)
                mediosPagosIds = String.valueOf(mediosPago.get(i).getId());
            else
                mediosPagosIds += "," + mediosPago.get(i).getId();
        }

        return mediosPagosIds;
    }
}
