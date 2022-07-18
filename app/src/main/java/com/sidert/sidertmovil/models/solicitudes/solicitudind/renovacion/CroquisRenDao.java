package com.sidert.sidertmovil.models.solicitudes.solicitudind.renovacion;

import android.content.Context;

import com.sidert.sidertmovil.models.BaseDao;

public class CroquisRenDao extends BaseDao {
    public CroquisRenDao(Context ctx) {
        super(ctx);
    }

    public CroquisRen findByIdSolicitud(Integer idSolicitud)
    {
        CroquisRen croquisRen = new CroquisRen();
        find( findByIdSolicitud(CroquisRen.TBL, idSolicitud), croquisRen );
        return croquisRen;
    }
}