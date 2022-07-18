package com.sidert.sidertmovil.models.solicitudes.solicitudind.renovacion;

import android.content.Context;

import com.sidert.sidertmovil.models.BaseDao;

public class ReferenciaRenDao extends BaseDao {

    public ReferenciaRenDao(Context ctx) {
        super(ctx);
    }

    public ReferenciaRen findByIdSolicitud(Integer idSolicitud)
    {
        ReferenciaRen referenciaRen = new ReferenciaRen();
        find( findByIdSolicitud(ReferenciaRen.TBL, idSolicitud), referenciaRen );
        return referenciaRen;
    }

}
