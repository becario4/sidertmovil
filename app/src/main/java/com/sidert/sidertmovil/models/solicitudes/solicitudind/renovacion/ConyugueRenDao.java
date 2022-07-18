package com.sidert.sidertmovil.models.solicitudes.solicitudind.renovacion;

import android.content.Context;

import com.sidert.sidertmovil.models.BaseDao;

public class ConyugueRenDao extends BaseDao {

    public ConyugueRenDao(Context ctx) {
        super(ctx);
    }

    public ConyugueRen findByIdSolicitud(Integer idSolicitud)
    {
        ConyugueRen conyugueRen = new ConyugueRen();
        find( findByIdSolicitud(ConyugueRen.TBL, idSolicitud), conyugueRen );
        return conyugueRen;
    }

}
