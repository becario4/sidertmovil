package com.sidert.sidertmovil.models.solicitudes.solicitudgpo.originacion;

import android.content.Context;

import com.sidert.sidertmovil.models.BaseDao;

public class PoliticaPldIntegranteDao extends BaseDao {

    public PoliticaPldIntegranteDao(Context ctx) {
        super(ctx);
    }

    public PoliticaPldIntegrante findByIdIntegrante(Integer idIntegrante)
    {
        PoliticaPldIntegrante politicaPldIntegrante = new PoliticaPldIntegrante();
        find( findByIdIntegrante(PoliticaPldIntegrante.TBL, idIntegrante), politicaPldIntegrante );
        return politicaPldIntegrante;
    }


}
