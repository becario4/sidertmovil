package com.sidert.sidertmovil.models.solicitudes.solicitudind.originacion;

import android.content.Context;

import com.sidert.sidertmovil.models.BaseDao;

public class CroquisDao extends BaseDao {
    public CroquisDao(Context ctx) {
        super(ctx);
    }

    public Croquis findByIdSolicitud(Integer idSolicitud)
    {
        Croquis croquis = new Croquis();
        find( findByIdSolicitud(Croquis.TBL, idSolicitud), croquis );
        return croquis;
    }
}
