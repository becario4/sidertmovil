package com.sidert.sidertmovil.models.solicitudes.solicitudind.renovacion;

import android.content.Context;

import com.sidert.sidertmovil.models.BaseDao;

public class NegocioRenDao extends BaseDao {

    public NegocioRenDao(Context ctx) {
        super(ctx);
    }

    public NegocioRen findByIdSolicitud(Integer idSolicitud)
    {
        NegocioRen negocioRen = new NegocioRen();
        find( findByIdSolicitud(NegocioRen.TBL, idSolicitud), negocioRen );
        return negocioRen;
    }

}
