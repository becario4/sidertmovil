package com.sidert.sidertmovil.models.catalogos;

import static com.sidert.sidertmovil.utils.Constants.TBL_CENTRO_COSTO;
import static com.sidert.sidertmovil.utils.Constants.TBL_SUCURSALES_LOCALIDADES;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.sidert.sidertmovil.database.DBhelper;
import com.sidert.sidertmovil.database.SidertTables;
import com.sidert.sidertmovil.models.MCentroCosto;

import java.util.ArrayList;
import java.util.List;

public class SucursalesLocalidadesDao {
    final DBhelper dBhelper;
    final SQLiteDatabase db;

    public SucursalesLocalidadesDao(Context ctx){
        this.dBhelper = DBhelper.getInstance(ctx);
        this.db = dBhelper.getWritableDatabase();
    }

    public Integer saveData(SucursalesLocalidades sll){
        int id = 0;
        String sql;
        SQLiteStatement pInsert;

        db.beginTransaction();

        sql = "INSERT INTO " + SidertTables.SidertEntry.TABLE_SUCURSALES_LOCALIDADES + " ( " +
                "id," +
                "centroCosto," +
                "id_municipio," +
                "localidad," +
                "colonia," +
                "codigo_postal) " +
                " VALUES ( ?, ?, ?, ?, ?, ?)";

        pInsert = db.compileStatement(sql);

        //pInsert.bindString(1,String.valueOf(sll.getId()));
        //pInsert.bindString(2,String.valueOf(sll.getCentroCosto()));
       // pInsert.bindString(3,String.valueOf(sll.getId_municipio()));
       // pInsert.bindString(4,sll.getLocalidad());
       // pInsert.bindString(5,sll.getColonia());
       // pInsert.bindString(6,String.valueOf(sll.getCodigo_postal()));

        id = (int) pInsert.executeInsert();

        db.setTransactionSuccessful();
        db.endTransaction();

        return id;
    }

    public Integer obtenerCentroCosto(){
        int centroCosto = 0;

        String sql = " SELECT * FROM " + TBL_CENTRO_COSTO;

        Cursor row = db.rawQuery(sql,null);

        if(row.getCount()>0){
            row.moveToFirst();
            MCentroCosto mc = new MCentroCosto();
            centroCosto = row.getInt(1);
        }
        row.close();

        return centroCosto;
    }

    public List<SucursalesLocalidades> findAll(){
        List<SucursalesLocalidades> sll = new ArrayList<>();

        String sql = " SELECT * FROM " + TBL_SUCURSALES_LOCALIDADES;

        Cursor row = db.rawQuery(sql,new String[]{});

        if(row.getCount()>0){
            row.moveToFirst();

            for(int i = 0;i< row.getCount();i++){
                SucursalesLocalidades suc = new SucursalesLocalidades();

                Fill(row, suc);

                sll.add(suc);

                row.moveToNext();
            }
        }
        row.close();
        return sll;
    }

    public SucursalesLocalidades findBySucursalId(Integer id){
        SucursalesLocalidades sll = null;

        String sql = "SELECT * FROM " + TBL_CENTRO_COSTO + " WHERE id = ? ";


        Cursor row = db.rawQuery(sql,new String[]{String.valueOf(id)});


        if(row.getCount()> 0){
            row.moveToFirst();

            sll = new SucursalesLocalidades();


           // sll.setId(row.getInt(1));
           // sll.setCentroCosto(row.getInt(2));
           // sll.setId_municipio(row.getInt(3));
           // sll.setLocalidad(row.getString(4));
           // sll.setColonia(row.getString(5));
           // sll.setCodigo_postal(row.getInt(6));
        }
        row.close();

        return sll;
    }



    private void Fill(Cursor row, SucursalesLocalidades suc){
        //suc.setId(row.getInt(1));
        //suc.setCentroCosto(row.getInt(2));
        //suc.setId_municipio(row.getInt(3));
        //suc.setLocalidad(row.getString(4));
        //suc.setColonia(row.getString(5));
        //suc.setCodigo_postal(row.getInt(6));
    }
}
