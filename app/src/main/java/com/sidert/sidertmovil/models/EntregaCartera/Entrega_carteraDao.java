package com.sidert.sidertmovil.models.EntregaCartera;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.sidert.sidertmovil.database.DBhelper;
import com.sidert.sidertmovil.models.BaseDao;

public class Entrega_carteraDao extends BaseDao {
    public static DBhelper dBhelper;
    public static SQLiteDatabase db;

    public Entrega_carteraDao(Context ctx){super(ctx);}

}
