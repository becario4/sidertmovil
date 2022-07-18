package com.sidert.sidertmovil.models;

import android.database.Cursor;

import java.util.HashMap;

public class BaseModel {
    protected Cursor row;

    protected String getString(String key){
        return row.getString(row.getColumnIndex(key));
    }

    protected Integer getInt(String key){
        return row.getInt(row.getColumnIndex(key));
    }
}
