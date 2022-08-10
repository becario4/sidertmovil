package com.sidert.sidertmovil.models.solicitudes.solicitudind.originacion;

import android.content.Context;

import com.sidert.sidertmovil.models.BaseDao;

public class PoliticaPldDao extends BaseDao {

    public PoliticaPldDao(Context ctx) {
        super(ctx);
    }

    public PoliticaPld findByIdSolicitud(Integer idSolicitud)
    {
        PoliticaPld politicaPld = new PoliticaPld();
        find( findByIdSolicitud(PoliticaPld.TBL, idSolicitud), politicaPld );
        return politicaPld;
    }

}
