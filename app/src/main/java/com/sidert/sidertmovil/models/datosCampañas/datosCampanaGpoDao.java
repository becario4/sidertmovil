package com.sidert.sidertmovil.models.datosCampañas;

import static com.sidert.sidertmovil.utils.Constants.TBL_DATOS_CREDITO_CAMPANA_GPO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.sidert.sidertmovil.database.DBhelper;
import com.sidert.sidertmovil.models.BaseDao;

public class datosCampanaGpoDao extends BaseDao{

    public static DBhelper dBhelper;
    public static SQLiteDatabase db;


    public datosCampanaGpoDao(Context ctx){ super(ctx); }

    public datosCampanaGpo findByIdSolicitud(Integer id_solicitud){
        datosCampanaGpo dato = new datosCampanaGpo();
        find(findByIdSolicitud(datosCampanaGpo.TBL,id_solicitud),dato);
        return dato;
    }

    public static boolean validarEstatus(Context ctx, int id_solicitud){
        dBhelper = DBhelper.getInstance(ctx);
        db = dBhelper.getWritableDatabase();

        boolean estatus = false;

        int dato = 0;

        String sql = " SELECT COUNT(*) FROM " + TBL_DATOS_CREDITO_CAMPANA_GPO + " WHERE id_solicitud = ?";

        Cursor row = db.rawQuery(sql,new String[]{String.valueOf(id_solicitud)});

        if(row.getCount()>0){

            row.moveToFirst();
            dato = row.getInt(0);
            row.close();
        }

        if(dato>=1){
            estatus = true;
        }
        return estatus;
    }













}
