package com.sidert.sidertmovil.models.solicitudes.solicitudgpo.originacion;

import static com.sidert.sidertmovil.models.BaseDao.find;

import android.content.Context;

import com.sidert.sidertmovil.models.BaseDao;

public class BeneficiarioIntegranteDao extends BaseDao {

    public BeneficiarioIntegranteDao(Context ctx){super(ctx);}
    public BeneficiarioIntegrante findByIdIntegrante(Integer id_integrante){
        BeneficiarioIntegrante benint = new BeneficiarioIntegrante();
        find(findByIdIntegrante(BeneficiarioIntegrante.TBL,id_integrante),benint);
        return benint;
    }

}
