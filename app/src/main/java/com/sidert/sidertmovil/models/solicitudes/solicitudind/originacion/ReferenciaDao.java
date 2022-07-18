package com.sidert.sidertmovil.models.solicitudes.solicitudind.originacion;

import android.content.Context;

import com.sidert.sidertmovil.models.BaseDao;

public class ReferenciaDao  extends BaseDao {

    public ReferenciaDao(Context ctx) {
        super(ctx);
    }

    public Referencia findByIdSolicitud(Integer idSolicitud)
    {
        Referencia referencia = new Referencia();
        find( findByIdSolicitud(Referencia.TBL, idSolicitud), referencia );
        return referencia;
    }

}
