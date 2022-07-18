package com.sidert.sidertmovil.models.solicitudes.solicitudind.renovacion;

import android.content.Context;

import com.sidert.sidertmovil.models.BaseDao;

public class CreditoIndRenDao extends BaseDao {

    public CreditoIndRenDao(Context ctx) {
        super(ctx);
    }

    public CreditoIndRen findByIdSolicitud(Integer idSolicitud)
    {
        CreditoIndRen creditoIndRen = new CreditoIndRen();
        find( findByIdSolicitud(CreditoIndRen.TBL, idSolicitud), creditoIndRen );
        return creditoIndRen;
    }

}