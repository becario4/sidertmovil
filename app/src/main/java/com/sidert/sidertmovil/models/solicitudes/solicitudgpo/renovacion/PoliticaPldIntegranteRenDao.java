package com.sidert.sidertmovil.models.solicitudes.solicitudgpo.renovacion;

import android.content.Context;

import com.sidert.sidertmovil.models.BaseDao;

public class PoliticaPldIntegranteRenDao extends BaseDao {
    public PoliticaPldIntegranteRenDao(Context ctx) {
        super(ctx);
    }

    public PoliticaPldIntegranteRen findByIdIntegrante(Integer idIntegrante)
    {
        PoliticaPldIntegranteRen politicaPldIntegranteRen = new PoliticaPldIntegranteRen();
        find( findByIdIntegrante(PoliticaPldIntegranteRen.TBL, idIntegrante),  politicaPldIntegranteRen);
        return politicaPldIntegranteRen;
    }
}
