package com.sidert.sidertmovil.database;

import android.provider.BaseColumns;

public class SidertTables {

    public static class SidertEntry implements BaseColumns {

        // == TABLE
        public static final String TABLE_NAME_LOG_R    = "log_impressions_r"; //Tabla de Recuperación
        public static final String TABLE_NAME_LOG_CV   = "log_impressions_cv"; //Tabla de Cartera Vencida
        public static final String TABLE_NAME_LOG_C   = "log_impressions_c"; //Tabla de Cobranza

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
        static final String CREATE_TABLE_RECOVERY   = "CREATE TABLE " + TABLE_NAME_LOG_R + "("
                + SidertEntry._ID               + " INTEGER PRIMARY KEY,"
                + SidertEntry.FOLIO             + " INTEGER,"
                + SidertEntry.ASSESOR_ID        + " TEXT,"
                + SidertEntry.EXTERNAL_ID       + " TEXT,"
                + SidertEntry.AMOUNT            + " TEXT,"
                + SidertEntry.TYPE_IMPRESSION   + " TEXT,"
                + SidertEntry.ERRORS            + " TEXT,"
                + SidertEntry.GENERATED_AT      + " TEXT,"
                + SidertEntry.SENT_AT           + " TEXT,"
                + SidertEntry.STATUS            + " TEXT)";

        static final String CREATE_TABLE_WALLET_EXPIRED   = "CREATE TABLE " + TABLE_NAME_LOG_CV + "("
                + SidertEntry._ID               + " INTEGER PRIMARY KEY,"
                + SidertEntry.FOLIO             + " INTEGER,"
                + SidertEntry.ASSESOR_ID        + " TEXT,"
                + SidertEntry.EXTERNAL_ID       + " TEXT,"
                + SidertEntry.AMOUNT            + " TEXT,"
                + SidertEntry.TYPE_IMPRESSION   + " TEXT,"
                + SidertEntry.ERRORS            + " TEXT,"
                + SidertEntry.GENERATED_AT      + " TEXT,"
                + SidertEntry.SENT_AT           + " TEXT,"
                + SidertEntry.STATUS            + " TEXT)";

        static final String CREATE_TABLE_COLLECTION   = "CREATE TABLE " + TABLE_NAME_LOG_C + "("
                + SidertEntry._ID               + " INTEGER PRIMARY KEY,"
                + SidertEntry.FOLIO             + " INTEGER,"
                + SidertEntry.ASSESOR_ID        + " TEXT,"
                + SidertEntry.EXTERNAL_ID       + " TEXT,"
                + SidertEntry.AMOUNT            + " TEXT,"
                + SidertEntry.TYPE_IMPRESSION   + " TEXT,"
                + SidertEntry.ERRORS            + " TEXT,"
                + SidertEntry.GENERATED_AT      + " TEXT,"
                + SidertEntry.SENT_AT           + " TEXT,"
                + SidertEntry.STATUS            + " TEXT)";
    }
}
