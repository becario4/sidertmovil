package com.sidert.sidertmovil.models.solicitudes.solicitudgpo.renovacion;

import android.content.Context;

import com.sidert.sidertmovil.models.BaseDao;

public class CroquisDao extends BaseDao {
    public CroquisDao(Context ctx) {
        super(ctx);
    }

    public Croquis findByIdIntegrante(Integer idIntegrante)
    {
        Croquis croquis = new Croquis();
        find( findByIdIntegrante(Croquis.TBL, idIntegrante), croquis );
        return croquis;
    }
}
