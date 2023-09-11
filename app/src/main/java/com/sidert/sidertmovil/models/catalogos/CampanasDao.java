package com.sidert.sidertmovil.models.catalogos;

import static com.sidert.sidertmovil.utils.Constants.TBL_CATALOGOS_CAMPANAS;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.sidert.sidertmovil.database.DBhelper;
import com.sidert.sidertmovil.models.BaseDao;

public class CampanasDao extends BaseDao {

    private static DBhelper dBhelper;
    private static SQLiteDatabase db;


    public CampanasDao(Context ctx) {
        super(ctx);
    }

    public static boolean validarCatalogo(int id_campanas,String tipo_campana, Context ctx){
        dBhelper = DBhelper.getInstance(ctx);
        db = dBhelper.getWritableDatabase();
        boolean estatus = false;
        int dato = 0;

        String sql = "SELECT COUNT(*) FROM " + TBL_CATALOGOS_CAMPANAS + " WHERE id_campanas = ? AND tipo_campana = ?";

        Cursor row = db.rawQuery(sql,new String[]{String.valueOf(id_campanas),tipo_campana});

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



}
