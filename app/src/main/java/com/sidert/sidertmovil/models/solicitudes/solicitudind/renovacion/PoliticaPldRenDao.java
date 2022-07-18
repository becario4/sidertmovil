package com.sidert.sidertmovil.models.solicitudes.solicitudind.renovacion;

import android.content.Context;

import com.sidert.sidertmovil.models.BaseDao;

public class PoliticaPldRenDao extends BaseDao {

    public PoliticaPldRenDao(Context ctx) {
        super(ctx);
    }

    public PoliticaPldRen findByIdSolicitud(Integer idSolicitud)
    {
        PoliticaPldRen politicaPldRen = new PoliticaPldRen();
        find( findByIdSolicitud(PoliticaPldRen.TBL, idSolicitud), politicaPldRen );
        return politicaPldRen;
    }

}
