package com.sidert.sidertmovil.models.solicitudes.solicitudind.originacion;

import android.content.Context;

import com.sidert.sidertmovil.models.BaseDao;

public class EconomicoDao extends BaseDao {

    public EconomicoDao(Context ctx) {
        super(ctx);
    }

    public Economico findByIdSolicitud(Integer idSolicitud)
    {
            Economico economico = new Economico();
            find( findByIdSolicitud(Economico.TBL, idSolicitud), economico );
            return economico;
    }


}
