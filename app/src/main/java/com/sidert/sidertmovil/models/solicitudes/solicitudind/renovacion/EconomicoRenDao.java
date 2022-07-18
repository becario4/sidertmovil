package com.sidert.sidertmovil.models.solicitudes.solicitudind.renovacion;

import android.content.Context;

import com.sidert.sidertmovil.models.BaseDao;

public class EconomicoRenDao extends BaseDao {

    public EconomicoRenDao(Context ctx) {
        super(ctx);
    }

    public EconomicoRen findByIdSolicitud(Integer idSolicitud)
    {
        EconomicoRen economicoRen = new EconomicoRen();
        find( findByIdSolicitud(EconomicoRen.TBL, idSolicitud), economicoRen );
        return economicoRen;
    }

}