package com.sidert.sidertmovil.models.solicitudes.solicitudgpo.originacion;

import android.content.Context;

import com.sidert.sidertmovil.models.BaseDao;

public class CroquisIntegranteDao extends BaseDao {
    public CroquisIntegranteDao(Context ctx) {
        super(ctx);
    }

    public CroquisIntegrante findByIdIntegrante(Integer idIntegrante)
    {
        CroquisIntegrante croquis = new CroquisIntegrante();
        find( findByIdIntegrante(CroquisIntegrante.TBL, idIntegrante), croquis );
        return croquis;
    }
}
