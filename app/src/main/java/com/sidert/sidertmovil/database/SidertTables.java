package com.sidert.sidertmovil.database;

import android.provider.BaseColumns;

import com.sidert.sidertmovil.utils.Constants;

public class SidertTables {

    public static class SidertEntry implements BaseColumns {

        // == TABLE
        public static final String TABLE_NAME_LOG_ASESSOR    = Constants.LOG_ASESSOR; //Tabla de Recuperación y Cobranza
        public static final String TABLE_NAME_LOG_MANAGER   = Constants.LOG_MANAGER; //Tabla de Cartera Vencida

        // == COLUMNS IMPRESSIONS LOG
        public static final String FOLIO            = "folio";
        public static final String ASSESOR_ID       = "assesor_id";
        public static final String EXTERNAL_ID      = "external_id";
        public static final String AMOUNT           = "amount";
        public static final String TYPE_IMPRESSION  = "type_impression";
        public static final String ERRORS           = "errors";
        public static final String GENERATED_AT     = "generated_at";
        public static final String SENT_AT          = "sent_at";
        public static final String STATUS           = "status";

        // == QUERIES CREATE
        static final String CREATE_TABLE_ASESSORS   = "CREATE TABLE " + TABLE_NAME_LOG_ASESSOR + "("
                + SidertEntry._ID               + " INTEGER PRIMARY KEY,"
                + SidertEntry.FOLIO             + " INTEGER,"
                + SidertEntry.ASSESOR_ID        + " TEXT,"
                + SidertEntry.EXTERNAL_ID       + " TEXT,"
                + SidertEntry.AMOUNT            + " TEXT,"
                + SidertEntry.TYPE_IMPRESSION   + " TEXT,"
                + SidertEntry.ERRORS            + " TEXT,"
                + SidertEntry.GENERATED_AT      + " TEXT,"
                + SidertEntry.SENT_AT           + " TEXT,"
                + SidertEntry.STATUS            + " INTEGER)";

        static final String CREATE_TABLE_GESTORS   = "CREATE TABLE " + TABLE_NAME_LOG_MANAGER + "("
                + SidertEntry._ID               + " INTEGER PRIMARY KEY,"
                + SidertEntry.FOLIO             + " INTEGER,"
                + SidertEntry.ASSESOR_ID        + " TEXT,"
                + SidertEntry.EXTERNAL_ID       + " TEXT,"
                + SidertEntry.AMOUNT            + " TEXT,"
                + SidertEntry.TYPE_IMPRESSION   + " TEXT,"
                + SidertEntry.ERRORS            + " TEXT,"
                + SidertEntry.GENERATED_AT      + " TEXT,"
                + SidertEntry.SENT_AT           + " TEXT,"
                + SidertEntry.STATUS            + " INTEGER)";
    }
}
