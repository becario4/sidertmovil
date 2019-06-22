package com.sidert.sidertmovil.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.sidert.sidertmovil.utils.Constants;
import com.sidert.sidertmovil.utils.Miscellaneous;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class DBhelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "sidert.db";

    public DBhelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SidertTables.SidertEntry.CREATE_TABLE_ASESSORS);
        db.execSQL(SidertTables.SidertEntry.CREATE_TABLE_GESTORS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > DATABASE_VERSION){

        }
    }

    public void saveRecordsImpressionsLog(SQLiteDatabase db, String table_name, HashMap<Integer, String> params){
        db.beginTransaction();
        String sql = "INSERT INTO " +table_name + "(" +
                SidertTables.SidertEntry.FOLIO + ", " +
                SidertTables.SidertEntry.ASSESOR_ID + ", " +
                SidertTables.SidertEntry.EXTERNAL_ID + ", " +
                SidertTables.SidertEntry.AMOUNT + ", " +
                SidertTables.SidertEntry.TYPE_IMPRESSION + ", " +
                SidertTables.SidertEntry.ERRORS + ", " +
                SidertTables.SidertEntry.GENERATED_AT + ", " +
                SidertTables.SidertEntry.SENT_AT + ", " +
                SidertTables.SidertEntry.STATUS + ") VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
        SQLiteStatement pInsert = db.compileStatement(sql);
        pInsert.bindLong(1, Integer.parseInt(params.get(0)));   //FOLIO
        pInsert.bindString(2, params.get(1));                     //ID ASESOR
        pInsert.bindString(3, params.get(2));                   //ID ORDEN
        pInsert.bindString(4, params.get(3));                   //MONTO
        pInsert.bindString(5, params.get(4));                   //TIPO IMPRESION
        pInsert.bindString(6, params.get(5));                   //ERRORES
        pInsert.bindString(7, params.get(6));                   //FECHA IMPRESO
        pInsert.bindString(8, params.get(7));                   //FECHA ENVIADO
        pInsert.bindLong(9, Integer.parseInt(params.get(8)));   //ESTADO 0=Enviado, 1=NoEnviado
        pInsert.execute();

        db.setTransactionSuccessful();
        db.endTransaction();
    }

    public Cursor getDataImpresionsLog(String table, String where, String order, String[] args){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "SELECT * FROM " + table + where + order, args );
        return res;
    }

    public int updateImpressionsLog(String table, JSONObject params) throws JSONException {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        String conditionals = "";

        JSONArray jsonParams =(JSONArray) params.get(Constants.PARAMS);
        JSONArray jsonCon =(JSONArray) params.get(Constants.CONDITIONALS);

        for (int i = 0; i < jsonParams.length(); i++){
            JSONObject item = jsonParams.getJSONObject(i);
            values.put(item.getString(Constants.KEY),item.getString(Constants.VALUE));
        }

        for (int j = 0; j < jsonCon.length(); j++){
            JSONObject item = jsonCon.getJSONObject(j);
            conditionals += item.getString(Constants.KEY) + item.getString(Constants.VALUE);
        }
        return db.update(table, values, conditionals, null);
    }
}
