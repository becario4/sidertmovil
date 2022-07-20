package com.sidert.sidertmovil.models;

import android.database.Cursor;

import java.util.HashMap;

public class BaseModel {
    protected Cursor row;

    protected String getString(String columnName){
        return row.getString(row.getColumnIndex(columnName));
    }

    protected Integer getInt(String columnName){
        return row.getInt(row.getColumnIndex(columnName));
    }
}
