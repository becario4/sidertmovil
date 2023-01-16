package com.sidert.sidertmovil.models.solicitudes.solicitudgpo.renovacion;

import android.content.Context;

import com.sidert.sidertmovil.models.BaseDao;

public class OtrosDatosIntegranteRenDao extends BaseDao {
    public OtrosDatosIntegranteRenDao(Context ctx){ super(ctx); }

    public OtrosDatosIntegranteRen findByIdIntegrante(Integer idIntegrante)
    {
        OtrosDatosIntegranteRen otrosDatosIntegranteRen = new OtrosDatosIntegranteRen();
        find( findByIdIntegrante(OtrosDatosIntegranteRen.TBL, idIntegrante), otrosDatosIntegranteRen );
        return otrosDatosIntegranteRen;
    }

}
