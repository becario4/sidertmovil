package com.sidert.sidertmovil.models.solicitudes.solicitudgpo.renovacion;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.sidert.sidertmovil.database.DBhelper;
import com.sidert.sidertmovil.models.BaseDao;

import static com.sidert.sidertmovil.utils.Constants.TBL_NEGOCIO_INTEGRANTE_REN;

public class NegocioIntegranteRenDao extends BaseDao {
    public NegocioIntegranteRenDao(Context ctx){
        super(ctx);
    }

    public NegocioIntegranteRen findByIdIntegrante(Integer idIntegrante)
    {
        NegocioIntegranteRen negocio = new NegocioIntegranteRen();
        find( findByIdIntegrante(NegocioIntegranteRen.TBL, idIntegrante), negocio );
        return negocio;
    }


}
