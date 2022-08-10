package com.sidert.sidertmovil.models.solicitudes.solicitudgpo.originacion;

import android.content.Context;

import com.sidert.sidertmovil.models.BaseDao;

public class OtrosDatosIntegranteDao extends BaseDao {

    public OtrosDatosIntegranteDao(Context ctx) {
        super(ctx);
    }

    public OtrosDatosIntegrante findByIdIntegrante(Integer idIntegrante)
    {
        OtrosDatosIntegrante otrosDatosIntegrante = new OtrosDatosIntegrante();
        find( findByIdIntegrante(OtrosDatosIntegrante.TBL, idIntegrante), otrosDatosIntegrante );
        return otrosDatosIntegrante;
    }


}
