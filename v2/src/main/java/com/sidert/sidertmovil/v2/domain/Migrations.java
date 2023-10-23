package com.sidert.sidertmovil.v2.domain;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;
import timber.log.Timber;

public class Migrations {

    private static final Timber.Tree LOG = Timber.tag(Migrations.class.getName());

    public static final Migration MIGRATION_1_2 = new Migration(1, 2) {


        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            LOG.i("Migration from 1 to 2");

            database.execSQL("CREATE TABLE IF NOT EXISTS beneficiario(" +
                    "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "solicitud_id INTEGER," +
                    "integrante_id INTEGER," +
                    "tipo_solicitud TEXT," +
                    "solicitud_remota_id INTEGER," +
                    "nombre TEXT," +
                    "paterno TEXT," +
                    "materno TEXT," +
                    "parentesco TEXT);");

            database.execSQL("CREATE TABLE  IF NOT EXISTS solicitud_campana( " +
                    "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "solicitud_id INTEGER," +
                    "tipo_solicitud TEXT," +
                    "integrante_id INTEGER," +
                    "solicitud_remota_id INTEGER, " +
                    "campana_nombre TEXT, " +
                    "nombre_referido TEXT);");
        }
    };

}