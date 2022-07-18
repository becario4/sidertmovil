package com.sidert.sidertmovil.models.solicitudes.solicitudind.originacion;

import android.content.Context;

import com.sidert.sidertmovil.models.BaseDao;

public class NegocioDao extends BaseDao {

    public NegocioDao(Context ctx) {
        super(ctx);
    }

    public Negocio findByIdSolicitud(Integer idSolicitud)
    {
        Negocio negocio = new Negocio();
        find( findByIdSolicitud(Negocio.TBL, idSolicitud), negocio );
        return negocio;
    }
}
