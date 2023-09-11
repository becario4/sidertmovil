package com.sidert.sidertmovil.v2.domain;

import androidx.annotation.NonNull;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

public class Migrations {

    public static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("INSERT INTO tbl_catalogo_campanas (id_campana,tipo_campana,estatus) VALUES " +
                    "(2,'RESCATE Y GANA',1)," +
                    "(3,'SUMA Y GANA',1)," +
                    "(6,'NINGUNO',1);");
        }
    };
}