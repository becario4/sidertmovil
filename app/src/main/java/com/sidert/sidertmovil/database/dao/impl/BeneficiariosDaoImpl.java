package com.sidert.sidertmovil.database.dao.impl;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.sidert.sidertmovil.database.DBhelper;
import com.sidert.sidertmovil.database.dao.BeneficiariosDao;
import com.sidert.sidertmovil.database.entities.Beneficiario;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import timber.log.Timber;

public class BeneficiariosDaoImpl
        implements BeneficiariosDao {

    private final SQLiteDatabase sqLiteDatabase;
    private static volatile BeneficiariosDao instance;

    private BeneficiariosDaoImpl(DBhelper dBhelper) {
        this.sqLiteDatabase = dBhelper.getWritableDatabase();
    }

    public static synchronized BeneficiariosDao getInstance(DBhelper dBhelper) {
        if (instance == null) {
            instance = new BeneficiariosDaoImpl(dBhelper);
        }
        return instance;
    }

    @Override
    public List<Beneficiario> getAll() {
        String query = "SELECT * FROM " + Beneficiario.TABLE;
        try (final Cursor _cursor = this.sqLiteDatabase.rawQuery(query, null)) {
            final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow(Beneficiario.ID);
            final int _cursorIndexOfSolicitudId = _cursor.getColumnIndexOrThrow(Beneficiario.SOLICITUD_ID);
            final int _cursorIndexOfIntegranteId = _cursor.getColumnIndexOrThrow(Beneficiario.INTEGRANTE_ID);
            final int _cursorIndexOfTipoSolicitud = _cursor.getColumnIndexOrThrow(Beneficiario.TIPO_SOLICITUD);
            final int _cursorIndexOfSolicitudRemotaId = _cursor.getColumnIndexOrThrow(Beneficiario.SOLICITUD_REMOTA_ID);
            final int _cursorIndexOfNombre = _cursor.getColumnIndexOrThrow(Beneficiario.NOMBRE);
            final int _cursorIndexOfPaterno = _cursor.getColumnIndexOrThrow(Beneficiario.PATERNO);
            final int _cursorIndexOfMaterno = _cursor.getColumnIndexOrThrow(Beneficiario.MATERNO);
            final int _cursorIndexOfParentesco = _cursor.getColumnIndexOrThrow(Beneficiario.PARENTESCO);
            final List<Beneficiario> _result = new ArrayList<>(_cursor.getCount());
            while (_cursor.moveToNext()) {
                final Beneficiario _item = new Beneficiario();
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

                if (_cursor.isNull(_cursorIndexOfTipoSolicitud)) {
                    _item.setTipoSolicitud(null);
                } else {
                    _item.setTipoSolicitud(_cursor.getString(_cursorIndexOfTipoSolicitud));
                }

                if (_cursor.isNull(_cursorIndexOfSolicitudRemotaId)) {
                    _item.setSolicitudRemotaId(null);
                } else {
                    _item.setSolicitudRemotaId(_cursor.getInt(_cursorIndexOfSolicitudRemotaId));
                }

                if (_cursor.isNull(_cursorIndexOfNombre)) {
                    _item.setNombre(null);
                } else {
                    _item.setNombre(_cursor.getString(_cursorIndexOfNombre));
                }

                if (_cursor.isNull(_cursorIndexOfPaterno)) {
                    _item.setPaterno(null);
                } else {
                    _item.setPaterno(_cursor.getString(_cursorIndexOfPaterno));
                }

                if (_cursor.isNull(_cursorIndexOfMaterno)) {
                    _item.setMaterno(null);
                } else {
                    _item.setMaterno(_cursor.getString(_cursorIndexOfMaterno));
                }

                if (_cursor.isNull(_cursorIndexOfParentesco)) {
                    _item.setParentesco(null);
                } else {
                    _item.setParentesco(_cursor.getString(_cursorIndexOfParentesco));
                }

                _result.add(_item);
            }
            return _result;
        }

    }

    @Override
    public Optional<Beneficiario> findById(Long id) {
        String query = "SELECT * FROM " + Beneficiario.TABLE + " t0 WHERE t0._id = ?";
        try (final Cursor _cursor = this.sqLiteDatabase.rawQuery(query, new String[]{id.toString()})) {
            final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow(Beneficiario.ID);
            final int _cursorIndexOfSolicitudId = _cursor.getColumnIndexOrThrow(Beneficiario.SOLICITUD_ID);
            final int _cursorIndexOfIntegranteId = _cursor.getColumnIndexOrThrow(Beneficiario.INTEGRANTE_ID);
            final int _cursorIndexOfTipoSolicitud = _cursor.getColumnIndexOrThrow(Beneficiario.TIPO_SOLICITUD);
            final int _cursorIndexOfSolicitudRemotaId = _cursor.getColumnIndexOrThrow(Beneficiario.SOLICITUD_REMOTA_ID);
            final int _cursorIndexOfNombre = _cursor.getColumnIndexOrThrow(Beneficiario.NOMBRE);
            final int _cursorIndexOfPaterno = _cursor.getColumnIndexOrThrow(Beneficiario.PATERNO);
            final int _cursorIndexOfMaterno = _cursor.getColumnIndexOrThrow(Beneficiario.MATERNO);
            final int _cursorIndexOfParentesco = _cursor.getColumnIndexOrThrow(Beneficiario.PARENTESCO);
            Beneficiario _item;

            if (_cursor.moveToFirst()) {
                _item = new Beneficiario();
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

                if (_cursor.isNull(_cursorIndexOfTipoSolicitud)) {
                    _item.setTipoSolicitud(null);
                } else {
                    _item.setTipoSolicitud(_cursor.getString(_cursorIndexOfTipoSolicitud));
                }

                if (_cursor.isNull(_cursorIndexOfSolicitudRemotaId)) {
                    _item.setSolicitudRemotaId(null);
                } else {
                    _item.setSolicitudRemotaId(_cursor.getInt(_cursorIndexOfSolicitudRemotaId));
                }

                if (_cursor.isNull(_cursorIndexOfNombre)) {
                    _item.setNombre(null);
                } else {
                    _item.setNombre(_cursor.getString(_cursorIndexOfNombre));
                }

                if (_cursor.isNull(_cursorIndexOfPaterno)) {
                    _item.setPaterno(null);
                } else {
                    _item.setPaterno(_cursor.getString(_cursorIndexOfPaterno));
                }

                if (_cursor.isNull(_cursorIndexOfMaterno)) {
                    _item.setMaterno(null);
                } else {
                    _item.setMaterno(_cursor.getString(_cursorIndexOfMaterno));
                }

                if (_cursor.isNull(_cursorIndexOfParentesco)) {
                    _item.setParentesco(null);
                } else {
                    _item.setParentesco(_cursor.getString(_cursorIndexOfParentesco));
                }

            } else {
                _item = null;
            }
            return Optional.ofNullable(_item);
        }
    }

    @Override
    public Long insert(Beneficiario entity) {
        Long result = null;
        String sql = " INSERT OR REPLACE INTO " + Beneficiario.TABLE + " (" +
                Beneficiario.SOLICITUD_ID + "," +
                Beneficiario.INTEGRANTE_ID + "," +
                Beneficiario.TIPO_SOLICITUD + "," +
                Beneficiario.NOMBRE + "," +
                Beneficiario.PATERNO + "," +
                Beneficiario.MATERNO + "," +
                Beneficiario.PARENTESCO + ")" +
                " VALUES (?, ?, ?, ?, ?, ?, ?) ";

        this.sqLiteDatabase.beginTransaction();
        try {
            SQLiteStatement insert = this.sqLiteDatabase.compileStatement(sql);
            int c = 1;
            insert.bindLong(c++, entity.getSolicitudId());
            insert.bindLong(c++, entity.getIntegranteId());
            insert.bindString(c++, entity.getTipoSolicitud());
            insert.bindString(c++, entity.getNombre());
            insert.bindString(c++, entity.getPaterno());
            insert.bindString(c++, entity.getMaterno());
            insert.bindString(c, entity.getParentesco());
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
    public void update(Beneficiario entity) {
        String sql = "UPDATE OR ABORT " + Beneficiario.TABLE +
                " SET " +
                Beneficiario.SOLICITUD_REMOTA_ID + " = ?," +
                Beneficiario.NOMBRE + " = CASE WHEN ? IS NULL THEN " + Beneficiario.NOMBRE + " ELSE ? END," +
                Beneficiario.PATERNO + " = CASE WHEN ? IS NULL THEN " + Beneficiario.PATERNO + " ELSE ? END," +
                Beneficiario.MATERNO + " = CASE WHEN ? IS NULL THEN " + Beneficiario.MATERNO + " ELSE ? END," +
                Beneficiario.PARENTESCO + " = CASE WHEN ? IS NULL THEN " + Beneficiario.PARENTESCO + " ELSE ? END" +
                " WHERE " + Beneficiario.ID + " = ?";

        this.sqLiteDatabase.beginTransaction();
        try {
            SQLiteStatement update = this.sqLiteDatabase.compileStatement(sql);
            int c = 1;

            Integer solicitudRemotaId = entity.getSolicitudRemotaId();
            solicitudRemotaId = (solicitudRemotaId == null) ? 0 : solicitudRemotaId;
            update.bindLong(c++, solicitudRemotaId);

            String benficiarioNombre = entity.getNombre();
            if (benficiarioNombre != null && !benficiarioNombre.isEmpty()) {
                update.bindString(c++, benficiarioNombre);
                update.bindString(c++, benficiarioNombre);
            } else {
                update.bindNull(c++);
                update.bindString(c++, "");
            }

            String beneficiarioPaterno = entity.getPaterno();
            if (beneficiarioPaterno != null && !beneficiarioPaterno.isEmpty()) {
                update.bindString(c++, beneficiarioPaterno);
                update.bindString(c++, beneficiarioPaterno);
            } else {
                update.bindNull(c++);
                update.bindString(c++, "");
            }

            String beneficiarioMaterno = entity.getMaterno();
            if (beneficiarioMaterno != null && !beneficiarioMaterno.isEmpty()) {
                update.bindString(c++, beneficiarioMaterno);
                update.bindString(c++, beneficiarioMaterno);
            } else {
                update.bindNull(c++);
                update.bindString(c++, "");
            }

            String beneficiarioParentesco = entity.getParentesco();
            if (beneficiarioParentesco != null && !beneficiarioParentesco.isEmpty()) {
                update.bindString(c++, beneficiarioParentesco);
                update.bindString(c++, beneficiarioParentesco);
            } else {
                update.bindNull(c++);
                update.bindString(c++, "");
            }

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
    public void delete(Beneficiario entity) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public Optional<Beneficiario> findBySolicitudId(Integer solicitudId, Integer integranteId, String tipoSolicitud) {
        String query = "SELECT * FROM " + Beneficiario.TABLE + " t0 " +
                "WHERE t0." + Beneficiario.SOLICITUD_ID + " = ? " +
                "AND t0." + Beneficiario.INTEGRANTE_ID + " = ? " +
                "AND t0." + Beneficiario.TIPO_SOLICITUD + " = ?";
        try (final Cursor _cursor = this.sqLiteDatabase.rawQuery(query, new String[]{solicitudId.toString(), integranteId.toString(), tipoSolicitud})) {
            final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow(Beneficiario.ID);
            final int _cursorIndexOfSolicitudId = _cursor.getColumnIndexOrThrow(Beneficiario.SOLICITUD_ID);
            final int _cursorIndexOfIntegranteId = _cursor.getColumnIndexOrThrow(Beneficiario.INTEGRANTE_ID);
            final int _cursorIndexOfSolicitudRemotaId = _cursor.getColumnIndexOrThrow(Beneficiario.SOLICITUD_REMOTA_ID);
            final int _cursorIndexOfNombre = _cursor.getColumnIndexOrThrow(Beneficiario.NOMBRE);
            final int _cursorIndexOfPaterno = _cursor.getColumnIndexOrThrow(Beneficiario.PATERNO);
            final int _cursorIndexOfMaterno = _cursor.getColumnIndexOrThrow(Beneficiario.MATERNO);
            final int _cursorIndexOfParentesco = _cursor.getColumnIndexOrThrow(Beneficiario.PARENTESCO);
            Beneficiario _item;

            if (_cursor.moveToFirst()) {
                _item = new Beneficiario();
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

                if (_cursor.isNull(_cursorIndexOfNombre)) {
                    _item.setNombre(null);
                } else {
                    _item.setNombre(_cursor.getString(_cursorIndexOfNombre));
                }

                if (_cursor.isNull(_cursorIndexOfPaterno)) {
                    _item.setPaterno(null);
                } else {
                    _item.setPaterno(_cursor.getString(_cursorIndexOfPaterno));
                }

                if (_cursor.isNull(_cursorIndexOfMaterno)) {
                    _item.setMaterno(null);
                } else {
                    _item.setMaterno(_cursor.getString(_cursorIndexOfMaterno));
                }

                if (_cursor.isNull(_cursorIndexOfParentesco)) {
                    _item.setParentesco(null);
                } else {
                    _item.setParentesco(_cursor.getString(_cursorIndexOfParentesco));
                }

            } else {
                _item = null;
            }
            return Optional.ofNullable(_item);
        }

    }
}
