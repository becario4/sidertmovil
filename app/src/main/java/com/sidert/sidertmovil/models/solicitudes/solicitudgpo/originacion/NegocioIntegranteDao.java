package com.sidert.sidertmovil.models.solicitudes.solicitudgpo.originacion;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.sidert.sidertmovil.database.DBhelper;
import com.sidert.sidertmovil.models.BaseDao;

import static com.sidert.sidertmovil.utils.Constants.TBL_NEGOCIO_INTEGRANTE;

public class NegocioIntegranteDao extends BaseDao {
   public NegocioIntegranteDao(Context ctx){
        super(ctx);
   }

   public NegocioIntegrante findByIdIntegrante(Integer idIntegrante) {
        NegocioIntegrante negocio = new NegocioIntegrante();
        find( findByIdIntegrante(NegocioIntegrante.TBL, idIntegrante), negocio );
        return negocio;
   }


}
