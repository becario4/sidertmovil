package com.sidert.sidertmovil.models.solicitudes.solicitudgpo.originacion;

import android.content.Context;

import com.sidert.sidertmovil.models.BaseDao;

public class ConyugueIntegranteDao extends BaseDao {

    public ConyugueIntegranteDao(Context ctx) {
        super(ctx);
    }

    public ConyugueIntegrante findByIdSolicitud(Integer idSolicitud)
    {
        ConyugueIntegrante conyugueIntegrante = new ConyugueIntegrante();
        find( findByIdSolicitud(ConyugueIntegrante.TBL, idSolicitud), conyugueIntegrante );
        return conyugueIntegrante;
    }

}
