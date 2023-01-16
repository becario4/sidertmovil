package com.sidert.sidertmovil.models.solicitudes.solicitudgpo.originacion;

import android.content.Context;

import com.sidert.sidertmovil.models.BaseDao;

public class TelefonoIntegranteDao extends BaseDao {
    public TelefonoIntegranteDao(Context ctx) {
        super(ctx);
    }

    public TelefonoIntegrante findByIdIntegrante(Integer idIntegrante)
    {
        TelefonoIntegrante telefonoIntegrante = new TelefonoIntegrante();

        find( findByIdIntegrante(TelefonoIntegrante.TBL, idIntegrante), telefonoIntegrante );

        return telefonoIntegrante;
    }


}
