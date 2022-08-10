package com.sidert.sidertmovil.models.solicitudes.solicitudgpo.renovacion;

import android.content.Context;

import com.sidert.sidertmovil.models.BaseDao;

public class CroquisIntegranteRenDao extends BaseDao {
    public CroquisIntegranteRenDao(Context ctx) {
        super(ctx);
    }

    public CroquisIntegranteRen findByIdIntegrante(Integer idIntegrante)
    {
        CroquisIntegranteRen croquis = new CroquisIntegranteRen();
        find( findByIdIntegrante(CroquisIntegranteRen.TBL, idIntegrante), croquis );
        return croquis;
    }
}
