package com.sidert.sidertmovil.models.solicitudes.solicitudgpo.originacion;

import static com.sidert.sidertmovil.models.BaseDao.find;
import static com.sidert.sidertmovil.utils.Constants.TBL_DATOS_BENEFICIARIO_GPO;
import static com.sidert.sidertmovil.utils.Constants.TBL_INTEGRANTES_GPO;
import static com.sidert.sidertmovil.utils.Constants.TBL_INTEGRANTES_GPO_REN;
import static com.sidert.sidertmovil.utils.Constants.TBL_TRACKER_ASESOR_T;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.sidert.sidertmovil.database.DBhelper;
import com.sidert.sidertmovil.models.BaseDao;

public class BeneficiarioIntegranteDao extends BaseDao {

    private static DBhelper dBhelper1;
    private static SQLiteDatabase db1;


    public BeneficiarioIntegranteDao(Context ctx){super(ctx);}
    public BeneficiarioIntegrante findByIdIntegrante(Integer id_integrante){
        BeneficiarioIntegrante benint = new BeneficiarioIntegrante();
        find(findByIdIntegrante(BeneficiarioIntegrante.TBL,id_integrante),benint);
        return benint;
    }

    public static int obtenerSerieAsesor(Context ctx){

        dBhelper1 = DBhelper.getInstance(ctx);
        db1 = dBhelper1.getWritableDatabase();

        int serie_id = 0;

        String sql = " SELECT serie_id " + " FROM " + TBL_TRACKER_ASESOR_T + " WHERE _id=1";

        Cursor row = db1.rawQuery(sql,null);

        if(row.getCount()>0){
            row.moveToFirst();
            serie_id = row.getInt(0);
            row.close();
        }
        return serie_id;
    }

    public static boolean validarBeneficiarioGPO(int id_integrante, Context ctx){

        dBhelper1 = DBhelper.getInstance(ctx);
        db1 = dBhelper1.getWritableDatabase();

        boolean estatus = false;
        int dato = 0;

        String sql = " SELECT  COUNT(*) FROM " + TBL_DATOS_BENEFICIARIO_GPO + " WHERE id_integrante = ?";

        Cursor row = db1.rawQuery(sql, new String[]{String.valueOf(id_integrante)});

        if(row.getCount()>0){
            row.moveToFirst();
            dato = row.getInt(0);
            row.close();
        }

        if(dato >= 1){
            estatus = true;
        }

        return estatus;
    }

    public static int obtenerIdSolicitud(int id, Context ctx){
        dBhelper1 = DBhelper.getInstance(ctx);
        db1 = dBhelper1.getWritableDatabase();

        int id_solicitud_integrante = 0;

        String sql = " SELECT id_solicitud_integrante FROM " + TBL_INTEGRANTES_GPO + " WHERE id = ?";

        Cursor row = db1.rawQuery(sql, new String[]{String.valueOf(id)});

        if(row.getCount()>0){
            row.moveToFirst();
            id_solicitud_integrante = row.getInt(0);
            row.close();
        }
        return id_solicitud_integrante;
    }


}
