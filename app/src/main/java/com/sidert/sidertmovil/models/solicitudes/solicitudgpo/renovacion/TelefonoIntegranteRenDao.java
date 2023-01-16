package com.sidert.sidertmovil.models.solicitudes.solicitudgpo.renovacion;

import android.content.Context;

import com.sidert.sidertmovil.models.BaseDao;

public class TelefonoIntegranteRenDao extends BaseDao {
    public TelefonoIntegranteRenDao(Context ctx) { super(ctx); }

    public TelefonoIntegranteRen findByIdIntegrante(Integer idIntegrante)
    {
        TelefonoIntegranteRen telefonoIntegranteRen = new TelefonoIntegranteRen();
        find( findByIdIntegrante(TelefonoIntegranteRen.TBL, idIntegrante), telefonoIntegranteRen );
        return telefonoIntegranteRen;
    }
}
