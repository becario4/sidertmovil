package com.sidert.sidertmovil.models.solicitudes.solicitudgpo.renovacion;

import android.content.Context;

import com.sidert.sidertmovil.models.BaseDao;

public class ConyugueIntegranteRenDao extends BaseDao {

    public ConyugueIntegranteRenDao(Context ctx) {
        super(ctx);
    }

    public ConyugueIntegranteRen findByIdSolicitud(Integer idSolicitud)
    {
        ConyugueIntegranteRen conyugueIntegranteRen = new ConyugueIntegranteRen();
        find( findByIdSolicitud(ConyugueIntegranteRen.TBL, idSolicitud), conyugueIntegranteRen );
        return conyugueIntegranteRen;
    }

}