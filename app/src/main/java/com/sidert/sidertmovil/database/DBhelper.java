package com.sidert.sidertmovil.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

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
        db.execSQL(SidertTables.SidertEntry.CREATE_TABLE_RECOVERY);
        db.execSQL(SidertTables.SidertEntry.CREATE_TABLE_WALLET_EXPIRED);
        db.execSQL(SidertTables.SidertEntry.CREATE_TABLE_COLLECTION);
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
        pInsert.bindString(1, params.get(0));
        pInsert.bindLong(2, Integer.parseInt(params.get(1)));
        pInsert.bindString(3, params.get(2));
        pInsert.bindString(4, params.get(3));
        pInsert.bindString(5, params.get(4));
        pInsert.bindString(6, params.get(5));
        pInsert.bindString(7, params.get(6));
        pInsert.bindString(8, params.get(7));
        pInsert.bindString(9, params.get(8));
        pInsert.execute();

        db.setTransactionSuccessful();
        db.endTransaction();
    }

    public Cursor getDataImpresionsLog(String table, JSONObject conditionals) throws JSONException {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from " + table + Miscellaneous.readJson(conditionals), null );
        return res;
    }
}
