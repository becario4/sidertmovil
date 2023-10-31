package com.sidert.sidertmovil.database.dao.impl;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.sidert.sidertmovil.database.DBhelper;
import com.sidert.sidertmovil.database.dao.SolicitudCampanaDao;
import com.sidert.sidertmovil.database.entities.Beneficiario;
import com.sidert.sidertmovil.database.entities.SolicitudCampana;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import timber.log.Timber;

public class SolicitudCampanaDaoImpl implements SolicitudCampanaDao {

    private final SQLiteDatabase sqLiteDatabase;
    private static volatile SolicitudCampanaDao instance;

    private SolicitudCampanaDaoImpl(DBhelper dBhelper) {
        this.sqLiteDatabase = dBhelper.getWritableDatabase();
    }

    public static synchronized SolicitudCampanaDao getInstance(DBhelper dBhelper) {
        if (instance == null) {
            instance = new SolicitudCampanaDaoImpl(dBhelper);
        }
        return instance;
    }

    @Override
    public List<SolicitudCampana> getAll() {
        String query = "SELECT * FROM " + SolicitudCampana.TABLE;
        try (final Cursor _cursor = this.sqLiteDatabase.rawQuery(query, null)) {
            final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow(SolicitudCampana.ID);
            final int _cursorIndexOfSolicitudId = _cursor.getColumnIndexOrThrow(SolicitudCampana.SOLICITUD_ID);
            final int _cursorIndexOfIntegranteId = _cursor.getColumnIndexOrThrow(SolicitudCampana.INTEGRANTE_ID);
            final int _cursorIndexOfTipoSolicitudId = _cursor.getColumnIndexOrThrow(SolicitudCampana.TIPO_SOLICITUD);
            final int _cursorIndexOfSolicitudRemotaId = _cursor.getColumnIndexOrThrow(SolicitudCampana.SOLICITUD_REMOTA_ID);
            final int _cursorIndexOfCamapanNombre = _cursor.getColumnIndexOrThrow(SolicitudCampana.CAMAPANA_NOMBRE);
            final int _cursorIndexOfNombreReferido = _cursor.getColumnIndexOrThrow(SolicitudCampana.NOMBRE_REFERIDO);
            final List<SolicitudCampana> _result = new ArrayList<>(_cursor.getCount());
            while (_cursor.moveToNext()) {
                final SolicitudCampana _item = new SolicitudCampana();

                if (_cursor.isNull(_cursorIndexOfId)) {
                    _item.setId(null);
                } else {
                    _item.setId(_cursor.getLong(_cursorIndexOfId));
                }

                if (_cursor.isNull(_cursorIndexOfSolicitudId)) {
                    _item.setSolicitudId(null);
                } else {
                    _item.setSolicitudId(_cursor.getInt(_cursorIndexOfSolicitudId));
                }

                if (_cursor.isNull(_cursorIndexOfIntegranteId)) {
                    _item.setSolicitudId(null);
                } else {
                    _item.setIntegranteId(_cursor.getInt(_cursorIndexOfIntegranteId));
                }

                if (_cursor.isNull(_cursorIndexOfTipoSolicitudId)) {
                    _item.setTipoSolicitud(null);
                } else {
                    _item.setTipoSolicitud(_cursor.getString(_cursorIndexOfTipoSolicitudId));
                }

                if (_cursor.isNull(_cursorIndexOfSolicitudRemotaId)) {
                    _item.setSolicitudId(null);
                } else {
                    _item.setSolicitudRemotaId(_cursor.getInt(_cursorIndexOfSolicitudRemotaId));
                }

                if (_cursor.isNull(_cursorIndexOfCamapanNombre)) {
                    _item.setSolicitudId(null);
                } else {
                    _item.setCampanaNombre(_cursor.getString(_cursorIndexOfCamapanNombre));
                }

                if (_cursor.isNull(_cursorIndexOfNombreReferido)) {
                    _item.setSolicitudId(null);
                } else {
                    _item.setNombreReferido(_cursor.getString(_cursorIndexOfNombreReferido));
                }

                _result.add(_item);
            }
            return _result;
        }
    }

    @Override
    public Optional<SolicitudCampana> findById(Long id) {
        String query = "SELECT * FROM " + SolicitudCampana.TABLE + " t0 WHERE t0._id = ?";
        try (final Cursor _cursor = this.sqLiteDatabase.rawQuery(query, new String[]{id.toString()})) {
            final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow(SolicitudCampana.ID);
            final int _cursorIndexOfSolicitudId = _cursor.getColumnIndexOrThrow(SolicitudCampana.SOLICITUD_ID);
            final int _cursorIndexOfIntegranteId = _cursor.getColumnIndexOrThrow(SolicitudCampana.INTEGRANTE_ID);
            final int _cursorIndexOfTipoSolicitudId = _cursor.getColumnIndexOrThrow(SolicitudCampana.TIPO_SOLICITUD);
            final int _cursorIndexOfSolicitudRemotaId = _cursor.getColumnIndexOrThrow(SolicitudCampana.SOLICITUD_REMOTA_ID);
            final int _cursorIndexOfCamapanNombre = _cursor.getColumnIndexOrThrow(SolicitudCampana.CAMAPANA_NOMBRE);
            final int _cursorIndexOfNombreReferido = _cursor.getColumnIndexOrThrow(SolicitudCampana.NOMBRE_REFERIDO);
            SolicitudCampana _item;
            if (_cursor.moveToFirst()) {
                _item = new SolicitudCampana();
                if (_cursor.isNull(_cursorIndexOfId)) {
                    _item.setId(null);
                } else {
                    _item.setId(_cursor.getLong(_cursorIndexOfId));
                }

                if (_cursor.isNull(_cursorIndexOfSolicitudId)) {
                    _item.setSolicitudId(null);
                } else {
                    _item.setSolicitudId(_cursor.getInt(_cursorIndexOfSolicitudId));
                }

                if (_cursor.isNull(_cursorIndexOfIntegranteId)) {
                    _item.setIntegranteId(null);
                } else {
                    _item.setIntegranteId(_cursor.getInt(_cursorIndexOfIntegranteId));
                }

                if (_cursor.isNull(_cursorIndexOfTipoSolicitudId)) {
                    _item.setTipoSolicitud(null);
                } else {
                    _item.setTipoSolicitud(_cursor.getString(_cursorIndexOfTipoSolicitudId));
                }

                if (_cursor.isNull(_cursorIndexOfSolicitudRemotaId)) {
                    _item.setSolicitudId(null);
                } else {
                    _item.setSolicitudRemotaId(_cursor.getInt(_cursorIndexOfSolicitudRemotaId));
                }

                if (_cursor.isNull(_cursorIndexOfCamapanNombre)) {
                    _item.setSolicitudId(null);
                } else {
                    _item.setCampanaNombre(_cursor.getString(_cursorIndexOfCamapanNombre));
                }

                if (_cursor.isNull(_cursorIndexOfNombreReferido)) {
                    _item.setSolicitudId(null);
                } else {
                    _item.setNombreReferido(_cursor.getString(_cursorIndexOfNombreReferido));
                }
            } else {
                _item = null;
            }
            return Optional.ofNullable(_item);
        }

    }

    @Override
    public void insertAll(List<SolicitudCampana> entities) {

    }

    @Override
    public Long insert(SolicitudCampana entity) {
        Long result = null;
        String sql = " INSERT OR REPLACE INTO " + SolicitudCampana.TABLE + " (" +
                SolicitudCampana.SOLICITUD_ID + "," +
                SolicitudCampana.TIPO_SOLICITUD + "," +
                SolicitudCampana.INTEGRANTE_ID + "," +
                SolicitudCampana.CAMAPANA_NOMBRE + "," +
                SolicitudCampana.NOMBRE_REFERIDO + ")" +
                " VALUES (?, ?, ?, ? , ?)";

        this.sqLiteDatabase.beginTransaction();
        try {
            SQLiteStatement insert = this.sqLiteDatabase.compileStatement(sql);
            int c = 1;
            insert.bindLong(c++, entity.getSolicitudId());
            insert.bindString(c++, entity.getTipoSolicitud());
            insert.bindLong(c++, entity.getIntegranteId());
            insert.bindString(c++, entity.getCampanaNombre());
            insert.bindString(c, entity.getNombreReferido());
            result = insert.executeInsert();
            this.sqLiteDatabase.setTransactionSuccessful();
        } catch (SQLException ex) {
            Timber.tag(this.getClass().getName()).e(ex);
        } finally {
            this.sqLiteDatabase.endTransaction();
        }
        return result;
    }

    @Override
    public void update(SolicitudCampana entity) {
        String sql = "UPDATE OR ABORT " + SolicitudCampana.TABLE +
                " SET " +
                SolicitudCampana.CAMAPANA_NOMBRE + " = CASE WHEN ? IS NULL THEN " + SolicitudCampana.CAMAPANA_NOMBRE + " ELSE ? END," +
                SolicitudCampana.NOMBRE_REFERIDO + " = ?" +
                " WHERE " + SolicitudCampana.ID + " = ?";

        this.sqLiteDatabase.beginTransaction();
        try {
            SQLiteStatement update = this.sqLiteDatabase.compileStatement(sql);
            int c = 1;

            String campanaNombre = entity.getCampanaNombre();
            if (campanaNombre != null && !campanaNombre.isEmpty()) {
                update.bindString(c++, campanaNombre);
                update.bindString(c++, campanaNombre);
            } else {
                update.bindNull(c++);
                update.bindString(c++, "");
            }

            String nombreReferido = entity.getNombreReferido();
            update.bindString(c++, nombreReferido);

            update.bindLong(c, entity.getId());
            update.executeUpdateDelete();
            this.sqLiteDatabase.setTransactionSuccessful();
        } catch (SQLException ex) {
            Timber.tag(this.getClass().getName()).e(ex);
        } finally {
            this.sqLiteDatabase.endTransaction();
        }
    }

    @Override
    public void delete(SolicitudCampana entity) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public Optional<SolicitudCampana> findBySolicitudId(Integer solicitudId, Integer integranteId, String tipoSolicitud) {
        String query = "SELECT * FROM " + SolicitudCampana.TABLE + " t0 " +
                "WHERE t0. " + SolicitudCampana.SOLICITUD_ID + " = ? " +
                "and t0." + SolicitudCampana.INTEGRANTE_ID + "  = ? " +
                "and t0." + SolicitudCampana.TIPO_SOLICITUD + " = ? ";
        try (final Cursor _cursor = this.sqLiteDatabase.rawQuery(query, new String[]{solicitudId.toString(), integranteId.toString(), tipoSolicitud})) {
            final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow(SolicitudCampana.ID);
            final int _cursorIndexOfSolicitudId = _cursor.getColumnIndexOrThrow(SolicitudCampana.SOLICITUD_ID);
            final int _cursorIndexOfIntegranteId = _cursor.getColumnIndexOrThrow(SolicitudCampana.INTEGRANTE_ID);
            final int _cursorIndexOfSolicitudRemotaId = _cursor.getColumnIndexOrThrow(SolicitudCampana.SOLICITUD_REMOTA_ID);
            final int _cursorIndexOfCamapanNombre = _cursor.getColumnIndexOrThrow(SolicitudCampana.CAMAPANA_NOMBRE);
            final int _cursorIndexOfNombreReferido = _cursor.getColumnIndexOrThrow(SolicitudCampana.NOMBRE_REFERIDO);
            SolicitudCampana _item;
            if (_cursor.moveToFirst()) {
                _item = new SolicitudCampana();
                if (_cursor.isNull(_cursorIndexOfId)) {
                    _item.setId(null);
                } else {
                    _item.setId(_cursor.getLong(_cursorIndexOfId));
                }

                if (_cursor.isNull(_cursorIndexOfSolicitudId)) {
                    _item.setSolicitudId(null);
                } else {
                    _item.setSolicitudId(_cursor.getInt(_cursorIndexOfSolicitudId));
                }

                if (_cursor.isNull(_cursorIndexOfIntegranteId)) {
                    _item.setIntegranteId(null);
                } else {
                    _item.setIntegranteId(_cursor.getInt(_cursorIndexOfIntegranteId));
                }

                if (_cursor.isNull(_cursorIndexOfSolicitudRemotaId)) {
                    _item.setSolicitudRemotaId(null);
                } else {
                    _item.setSolicitudRemotaId(_cursor.getInt(_cursorIndexOfSolicitudRemotaId));
                }

                if (_cursor.isNull(_cursorIndexOfCamapanNombre)) {
                    _item.setCampanaNombre(null);
                } else {
                    _item.setCampanaNombre(_cursor.getString(_cursorIndexOfCamapanNombre));
                }

                if (_cursor.isNull(_cursorIndexOfNombreReferido)) {
                    _item.setNombreReferido(null);
                } else {
                    _item.setNombreReferido(_cursor.getString(_cursorIndexOfNombreReferido));
                }
            } else {
                _item = null;
            }
            return Optional.ofNullable(_item);
        }

    }

}
