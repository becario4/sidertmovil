package com.sidert.sidertmovil.models.datosCampañas;

import static com.sidert.sidertmovil.utils.Constants.TBL_DATOS_CREDITO_CAMPANA;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.sidert.sidertmovil.database.DBhelper;
import com.sidert.sidertmovil.models.BaseDao;

public class datosCampanaDao extends BaseDao {

    public static DBhelper dBhelper;
    public static SQLiteDatabase db;

    public datosCampanaDao(Context ctx){super(ctx);}

    public datoCampana findByIdSolictud(Integer id_solicitud){
        datoCampana dato = new datoCampana();
        find(findByIdSolicitud(datoCampana.TBL,id_solicitud),dato);
        return dato;
    }

    public static boolean validarEstatus(Context ctx, int id_solicitud){
        dBhelper = DBhelper.getInstance(ctx);
        db = dBhelper.getWritableDatabase();

        boolean estatus = false;

        int dato = 0;

        String sql =  " SELECT COUNT(*) FROM " + TBL_DATOS_CREDITO_CAMPANA + " WHERE id_solicitud = ? ";

        Cursor row = db.rawQuery(sql,new String[]{String.valueOf(id_solicitud)});

        if(row.getCount()>0){
            row.moveToFirst();
            dato = row.getInt(0);
            row.close();
        }

        if(dato>= 1){
            estatus = true;
        }

        return estatus;
    }

}
