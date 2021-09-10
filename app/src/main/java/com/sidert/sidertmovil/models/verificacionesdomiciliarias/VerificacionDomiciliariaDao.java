package com.sidert.sidertmovil.models.verificacionesdomiciliarias;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.sidert.sidertmovil.database.DBhelper;

import java.util.ArrayList;
import java.util.List;

import static com.sidert.sidertmovil.utils.Constants.TBL_GESTIONES_VER_DOM;
import static com.sidert.sidertmovil.utils.Constants.TBL_VERIFICACIONES_DOMICILIARIAS;

public class VerificacionDomiciliariaDao {
    final DBhelper dbHelper;
    final SQLiteDatabase db;

    public VerificacionDomiciliariaDao(Context ctx)
    {
        this.dbHelper = new DBhelper(ctx);
        this.db = dbHelper.getWritableDatabase();
    }

    public long store(VerificacionDomiciliaria verificacion)
    {
        Long id;
        String sql;

        db.beginTransaction();

        sql = "INSERT INTO " + TBL_VERIFICACIONES_DOMICILIARIAS + "(" +
                "verificacion_domiciliaria_id," +
                "prestamo_id," +
                "cliente_id," +
                "cliente_nombre," +
                "cliente_nacionalidad," +
                "cliente_fecha_nacimiento," +
                "domicilio_direccion," +
                "domicilio_referencia," +
                "monto_solicitado," +
                "horario_localizacion," +
                "verificacion_tipo_id," +
                "estatus," +
                "solicitante_id," +
                "solicitud_id," +
                "asesor_serie_id, " +
                "asesor_nombre," +
                "usuario_id," +
                "sucursal_id," +
                "sucursal_nombre," +
                "fecha_asignacion," +
                "fecha_expiracion," +
                "created_at," +
                "grupo_id," +
                "grupo_nombre," +
                "num_solicitud" +
                ") VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        SQLiteStatement pInsert = db.compileStatement(sql);

        pInsert.bindLong(1, verificacion.getVerificacionDomiciliariaId());
        pInsert.bindLong(2, verificacion.getPrestamoId());
        pInsert.bindLong(3, verificacion.getClienteId());
        pInsert.bindString(4, verificacion.getClienteNombre());
        pInsert.bindString(5, verificacion.getClienteNacionalidad());
        pInsert.bindString(6, verificacion.getClienteFechaNacimiento());
        pInsert.bindString(7, verificacion.getDomicilioDireccion());
        pInsert.bindString(8, verificacion.getDomicilioReferencia());
        pInsert.bindString(9, verificacion.getMontoSolicitado());
        pInsert.bindString(10, verificacion.getHorarioLocalizacion());
        pInsert.bindLong(11, verificacion.getVerificacionTipoId());
        pInsert.bindLong(12, verificacion.getEstatus());
        pInsert.bindLong(13, verificacion.getSolicitanteId());
        pInsert.bindLong(14, verificacion.getSolicitudId());
        pInsert.bindString(15, verificacion.getAsesorSerieId());
        pInsert.bindString(16, verificacion.getAsesorNombre());
        pInsert.bindLong(17, verificacion.getUsuarioId());
        pInsert.bindLong(18, verificacion.getSucursalId());
        pInsert.bindString(19, verificacion.getSucursalNombre());
        pInsert.bindString(20, verificacion.getFechaAsignacion());
        pInsert.bindString(21, verificacion.getFechaExpiracion());
        pInsert.bindString(22, verificacion.getCreatedAt());
        pInsert.bindLong(23, verificacion.getGrupoId());
        pInsert.bindString(24, verificacion.getGrupoNombre());
        pInsert.bindLong(25, verificacion.getNumSolicitud());

        id = pInsert.executeInsert();

        db.setTransactionSuccessful();
        db.endTransaction();

        return id;
    }

    public void update(VerificacionDomiciliaria verificacion)
    {
        ContentValues cv = new ContentValues();
        cv.put("prestamo_id", verificacion.getPrestamoId());
        cv.put("num_solicitud", verificacion.getNumSolicitud());
        cv.put("grupo_id", verificacion.getGrupoId());
        cv.put("grupo_nombre", verificacion.getGrupoNombre());
        cv.put("cliente_id", verificacion.getClienteId());
        cv.put("cliente_nombre", verificacion.getClienteNombre());
        cv.put("cliente_nacionalidad", verificacion.getClienteNacionalidad());
        cv.put("cliente_fecha_nacimiento", verificacion.getClienteFechaNacimiento());
        cv.put("domicilio_direccion", verificacion.getDomicilioDireccion());
        cv.put("domicilio_referencia", verificacion.getDomicilioReferencia());
        cv.put("monto_solicitado", verificacion.getMontoSolicitado());
        cv.put("horario_localizacion", verificacion.getHorarioLocalizacion());
        cv.put("verificacion_tipo_id", verificacion.getVerificacionTipoId());
        cv.put("estatus", verificacion.getEstatus());
        cv.put("solicitante_id", verificacion.getSolicitanteId());
        cv.put("solicitud_id", verificacion.getSolicitudId());
        cv.put("asesor_serie_id", verificacion.getAsesorSerieId());
        cv.put("asesor_nombre", verificacion.getAsesorNombre());
        cv.put("usuario_id", verificacion.getUsuarioId());
        cv.put("sucursal_id", verificacion.getSucursalId());
        cv.put("sucursal_nombre", verificacion.getSucursalNombre());
        cv.put("fecha_asignacion", verificacion.getFechaAsignacion());
        cv.put("fecha_expiracion", verificacion.getFechaExpiracion());
        cv.put("created_at", verificacion.getCreatedAt());

        db.update(TBL_VERIFICACIONES_DOMICILIARIAS, cv, "_id = ?", new String[]{String.valueOf(verificacion.getId())});
    }

    public VerificacionDomiciliaria findByVerificacionDomiciliariaId(Long verificacionDomiciliariaId)
    {
        VerificacionDomiciliaria verificacion = null;

        String sql = "SELECT " +
                "  * " +
                "FROM " + TBL_VERIFICACIONES_DOMICILIARIAS + " " +
                "WHERE verificacion_domiciliaria_id = ? "
        ;
        Cursor row = db.rawQuery(sql, new String[]{String.valueOf(verificacionDomiciliariaId)});

        if(row.getCount() > 0)
        {
            row.moveToFirst();

            verificacion = new VerificacionDomiciliaria();

            verificacion.setId(row.getLong(0));
            verificacion.setVerificacionDomiciliariaId(row.getLong(1));
            verificacion.setPrestamoId(row.getLong(2));
            verificacion.setClienteId(row.getLong(3));
            verificacion.setClienteNombre(row.getString(4));
            verificacion.setClienteNacionalidad(row.getString(5));
            verificacion.setClienteFechaNacimiento(row.getString(6));
            verificacion.setDomicilioDireccion(row.getString(7));
            verificacion.setDomicilioReferencia(row.getString(8));
            verificacion.setMontoSolicitado(row.getString(9));
            verificacion.setHorarioLocalizacion(row.getString(10));
            verificacion.setVerificacionTipoId(row.getInt(11));
            verificacion.setEstatus(row.getInt(12));
            verificacion.setSolicitanteId(row.getLong(13));
            verificacion.setSolicitudId(row.getLong(14));
            verificacion.setAsesorSerieId(row.getString(15));
            verificacion.setAsesorNombre(row.getString(16));
            verificacion.setUsuarioId(row.getLong(17));
            verificacion.setSucursalId(row.getLong(18));
            verificacion.setSucursalNombre(row.getString(19));
            verificacion.setFechaAsignacion(row.getString(20));
            verificacion.setFechaExpiracion(row.getString(21));
            verificacion.setCreatedAt(row.getString(22));
            verificacion.setGrupoId(row.getLong(23));
            verificacion.setGrupoNombre(row.getString(24));
            verificacion.setNumSolicitud(row.getLong(25));
        }

        row.close();

        return verificacion;
    }

    public VerificacionDomiciliaria findByVerificacionDomiciliariaIdAndVerificacionTipoId(Long verificacionDomiciliariaId, Integer verificacionTipoId)
    {
        VerificacionDomiciliaria verificacion = null;

        String sql = "SELECT " +
                "  * " +
                "FROM " + TBL_VERIFICACIONES_DOMICILIARIAS + " " +
                "WHERE verificacion_domiciliaria_id = ? " +
                "AND verificacion_tipo_id = ? "
                ;
        Cursor row = db.rawQuery(sql, new String[]{String.valueOf(verificacionDomiciliariaId), String.valueOf(verificacionTipoId)});

        if(row.getCount() > 0)
        {
            row.moveToFirst();

            verificacion = new VerificacionDomiciliaria();

            verificacion.setId(row.getLong(0));
            verificacion.setVerificacionDomiciliariaId(row.getLong(1));
            verificacion.setPrestamoId(row.getLong(2));
            verificacion.setClienteId(row.getLong(3));
            verificacion.setClienteNombre(row.getString(4));
            verificacion.setClienteNacionalidad(row.getString(5));
            verificacion.setClienteFechaNacimiento(row.getString(6));
            verificacion.setDomicilioDireccion(row.getString(7));
            verificacion.setDomicilioReferencia(row.getString(8));
            verificacion.setMontoSolicitado(row.getString(9));
            verificacion.setHorarioLocalizacion(row.getString(10));
            verificacion.setVerificacionTipoId(row.getInt(11));
            verificacion.setEstatus(row.getInt(12));
            verificacion.setSolicitanteId(row.getLong(13));
            verificacion.setSolicitudId(row.getLong(14));
            verificacion.setAsesorSerieId(row.getString(15));
            verificacion.setAsesorNombre(row.getString(16));
            verificacion.setUsuarioId(row.getLong(17));
            verificacion.setSucursalId(row.getLong(18));
            verificacion.setSucursalNombre(row.getString(19));
            verificacion.setFechaAsignacion(row.getString(20));
            verificacion.setFechaExpiracion(row.getString(21));
            verificacion.setCreatedAt(row.getString(22));
            verificacion.setGrupoId(row.getLong(23));
            verificacion.setGrupoNombre(row.getString(24));
            verificacion.setNumSolicitud(row.getLong(25));
        }

        row.close();

        return verificacion;
    }

    public VerificacionDomiciliaria findByGrupoIdAndNumSolicitudAndVerificacionTipoId(Long grupoId, Long numSolicitud, Integer verificacionTipoId)
    {
        VerificacionDomiciliaria verificacion = null;

        String sql = "SELECT " +
                "  * " +
                "FROM " + TBL_VERIFICACIONES_DOMICILIARIAS + " " +
                "WHERE grupo_id = ? " +
                "AND num_solicitud = ? " +
                "AND verificacion_tipo_id = ? "
                ;
        Cursor row = db.rawQuery(sql, new String[]{
                String.valueOf(grupoId),
                String.valueOf(numSolicitud),
                String.valueOf(verificacionTipoId)
        });

        if(row.getCount() > 0)
        {
            row.moveToFirst();

            verificacion = new VerificacionDomiciliaria();

            verificacion.setId(row.getLong(0));
            verificacion.setVerificacionDomiciliariaId(row.getLong(1));
            verificacion.setPrestamoId(row.getLong(2));
            verificacion.setClienteId(row.getLong(3));
            verificacion.setClienteNombre(row.getString(4));
            verificacion.setClienteNacionalidad(row.getString(5));
            verificacion.setClienteFechaNacimiento(row.getString(6));
            verificacion.setDomicilioDireccion(row.getString(7));
            verificacion.setDomicilioReferencia(row.getString(8));
            verificacion.setMontoSolicitado(row.getString(9));
            verificacion.setHorarioLocalizacion(row.getString(10));
            verificacion.setVerificacionTipoId(row.getInt(11));
            verificacion.setEstatus(row.getInt(12));
            verificacion.setSolicitanteId(row.getLong(13));
            verificacion.setSolicitudId(row.getLong(14));
            verificacion.setAsesorSerieId(row.getString(15));
            verificacion.setAsesorNombre(row.getString(16));
            verificacion.setUsuarioId(row.getLong(17));
            verificacion.setSucursalId(row.getLong(18));
            verificacion.setSucursalNombre(row.getString(19));
            verificacion.setFechaAsignacion(row.getString(20));
            verificacion.setFechaExpiracion(row.getString(21));
            verificacion.setCreatedAt(row.getString(22));
            verificacion.setGrupoId(row.getLong(23));
            verificacion.setGrupoNombre(row.getString(24));
            verificacion.setNumSolicitud(row.getLong(25));
        }

        row.close();

        return verificacion;
    }

    public List<VerificacionDomiciliaria> findDisponibles(String[] filters)
    {
        List<VerificacionDomiciliaria> verificaciones = new ArrayList<>();

        String sql = "select * from(" +
                "SELECT " +
                "  min(vdg._id) as id, " +
                "  min(vdg.verificacion_domiciliaria_id) as verificacion_domiciliaria_id, " +
                "  vdg.grupo_nombre as nombre, " +
                "  vdg.num_solicitud, " +
                "  vdg.grupo_id, " +
                "  max(vdg.verificacion_tipo_id) as verificacion_tipo_id " +
                "FROM " + TBL_VERIFICACIONES_DOMICILIARIAS + " AS vdg " +
                "LEFT JOIN " + TBL_GESTIONES_VER_DOM + " AS gg ON gg.verificacion_domiciliaria_id = vdg.verificacion_domiciliaria_id " +
                "WHERE gg._id is null " +
                "AND (vdg.grupo_nombre like '%'||?||'%' AND vdg.grupo_id > 1) " +
                "AND vdg.grupo_id > 1 " +
                "AND strftime('%Y-%m-%d', 'now') <= substr(vdg.fecha_expiracion , 1, 10) " +
                "AND vdg.estatus = 1 " +
                "GROUP BY vdg.grupo_nombre, vdg.num_solicitud, vdg.grupo_id " +
                "UNION " +
                "SELECT " +
                "  vdi._id as id, " +
                "  vdi.verificacion_domiciliaria_id, " +
                "  vdi.cliente_nombre as nombre, " +
                "  vdi.num_solicitud, " +
                "  vdi.grupo_id, " +
                "  vdi.verificacion_tipo_id " +
                "FROM " + TBL_VERIFICACIONES_DOMICILIARIAS + " AS vdi " +
                "LEFT JOIN " + TBL_GESTIONES_VER_DOM + " AS gi ON gi.verificacion_domiciliaria_id = vdi.verificacion_domiciliaria_id " +
                "WHERE gi._id is null " +
                "AND (vdi.cliente_nombre LIKE '%'||?||'%' AND vdi.grupo_id = 1) " +
                "AND vdi.grupo_id = 1 " +
                "AND strftime('%Y-%m-%d', 'now') <= substr(vdi.fecha_expiracion , 1, 10) " +
                "AND vdi.estatus = 1 " +
                ") as pivot " +
                "WHERE (grupo_id = 1 OR ? = '0') " +
                "AND (grupo_id > 1 OR ? = '0') " +
                "ORDER BY nombre ";
        Cursor row = db.rawQuery(sql, filters);

        if(row.getCount() > 0)
        {
            row.moveToFirst();

            for(int i = 0; i < row.getCount(); i++)
            {
                VerificacionDomiciliaria verificacion = new VerificacionDomiciliaria();
                verificacion.setId(row.getLong(0));
                verificacion.setVerificacionDomiciliariaId(row.getLong(1));
                verificacion.setClienteNombre(row.getString(2));
                verificacion.setGrupoNombre(row.getString(2));
                verificacion.setNumSolicitud(row.getLong(3));
                verificacion.setGrupoId(row.getLong(4));
                verificacion.setVerificacionTipoId(row.getInt(5));

                verificaciones.add(verificacion);

                row.moveToNext();
            }
        }

        row.close();

        return verificaciones;
    }

    public List<VerificacionDomiciliaria> findGestionadas(String[] filters)
    {
        List<VerificacionDomiciliaria> verificaciones = new ArrayList<>();

        String sql = "select * from(" +
                "SELECT " +
                "  vdg._id as id, " +
                "  vdg.verificacion_domiciliaria_id as verificacion_domiciliaria_id, " +
                "  vdg.grupo_nombre as nombre, " +
                "  vdg.num_solicitud," +
                "  vdg.grupo_id, " +
                "  vdg.verificacion_tipo_id as verificacion_tipo_id " +
                "FROM " + TBL_VERIFICACIONES_DOMICILIARIAS + " AS vdg " +
                "LEFT JOIN " + TBL_GESTIONES_VER_DOM + " AS gg ON gg.verificacion_domiciliaria_id = vdg.verificacion_domiciliaria_id " +
                "WHERE gg._id is not null " +
                "AND (vdg.grupo_nombre like '%'||?||'%' AND vdg.grupo_id > 1) " +
                "UNION " +
                "SELECT " +
                "  vdi._id as id, " +
                "  vdi.verificacion_domiciliaria_id, " +
                "  vdi.cliente_nombre as nombre, " +
                "  vdi.num_solicitud, " +
                "  vdi.grupo_id, " +
                "  vdi.verificacion_tipo_id " +
                "FROM " + TBL_VERIFICACIONES_DOMICILIARIAS + " AS vdi " +
                "LEFT JOIN " + TBL_GESTIONES_VER_DOM + " AS gi ON gi.verificacion_domiciliaria_id = vdi.verificacion_domiciliaria_id " +
                "WHERE gi._id is not null " +
                "AND (vdi.cliente_nombre LIKE '%'||?||'%' AND vdi.grupo_id = 1) " +
                ") as pivot " +
                "WHERE (grupo_id > 1 OR ? = '0') " +
                "AND (grupo_id = 1 OR ? = '0') " +
                "ORDER BY nombre ";
        Cursor row = db.rawQuery(sql, filters);

        if(row.getCount() > 0)
        {
            row.moveToFirst();

            for(int i = 0; i < row.getCount(); i++)
            {
                VerificacionDomiciliaria verificacion = new VerificacionDomiciliaria();
                verificacion.setId(row.getLong(0));
                verificacion.setVerificacionDomiciliariaId(row.getLong(1));
                verificacion.setClienteNombre(row.getString(2));
                verificacion.setGrupoNombre(row.getString(2));
                verificacion.setNumSolicitud(row.getLong(3));
                verificacion.setGrupoId(row.getLong(4));
                verificacion.setVerificacionTipoId(row.getInt(5));

                verificaciones.add(verificacion);

                row.moveToNext();
            }
        }

        row.close();

        return verificaciones;
    }

    public List<String> showNombresDisponibles()
    {
        List<String> nombres = new ArrayList<>();

        String sql = "select distinct(nombre) from(" +
                "SELECT " +
                "  min(vdg._id) as id, " +
                "  min(vdg.verificacion_domiciliaria_id) as verificacion_domiciliaria_id, " +
                "  vdg.grupo_nombre as nombre, " +
                "  vdg.num_solicitud " +
                "FROM " + TBL_VERIFICACIONES_DOMICILIARIAS + " AS vdg " +
                "LEFT JOIN " + TBL_GESTIONES_VER_DOM + " AS gg ON gg.verificacion_domiciliaria_id = vdg.verificacion_domiciliaria_id " +
                "WHERE gg._id is null " +
                "AND vdg.grupo_id > 1 " +
                "GROUP BY vdg.grupo_nombre, vdg.num_solicitud " +
                "UNION " +
                "SELECT " +
                "  vdi._id as id, " +
                "  vdi.verificacion_domiciliaria_id, " +
                "  vdi.cliente_nombre as nombre, " +
                "  vdi.num_solicitud " +
                "FROM " + TBL_VERIFICACIONES_DOMICILIARIAS + " AS vdi " +
                "LEFT JOIN " + TBL_GESTIONES_VER_DOM + " AS gi ON gi.verificacion_domiciliaria_id = vdi.verificacion_domiciliaria_id " +
                "WHERE gi._id is null " +
                "AND vdi.grupo_id = 1 " +
                ") as pivot ORDER BY nombre ";
        Cursor row = db.rawQuery(sql, new String[]{});

        if(row.getCount() > 0)
        {
            row.moveToFirst();

            for(int i = 0; i < row.getCount(); i++)
            {
                nombres.add(row.getString(0));
                row.moveToNext();
            }
        }

        row.close();

        return nombres;
    }

    public List<String> showNombresGestionados()
    {
        List<String> nombres = new ArrayList<>();

        String sql = "select distinct(nombre) from(" +
                "SELECT " +
                "  min(vdg._id) as id, " +
                "  min(vdg.verificacion_domiciliaria_id) as verificacion_domiciliaria_id, " +
                "  vdg.grupo_nombre as nombre, " +
                "  vdg.num_solicitud," +
                "  vdg.grupo_id " +
                "FROM " + TBL_VERIFICACIONES_DOMICILIARIAS + " AS vdg " +
                "LEFT JOIN " + TBL_GESTIONES_VER_DOM + " AS gg ON gg.verificacion_domiciliaria_id = vdg.verificacion_domiciliaria_id " +
                "WHERE gg._id is not null " +
                "AND vdg.grupo_id > 1 " +
                "GROUP BY vdg.grupo_nombre, vdg.num_solicitud, vdg.grupo_id " +
                "UNION " +
                "SELECT " +
                "  vdi._id as id, " +
                "  vdi.verificacion_domiciliaria_id, " +
                "  vdi.cliente_nombre as nombre, " +
                "  vdi.num_solicitud, " +
                "  vdi.grupo_id  " +
                "FROM " + TBL_VERIFICACIONES_DOMICILIARIAS + " AS vdi " +
                "LEFT JOIN " + TBL_GESTIONES_VER_DOM + " AS gi ON gi.verificacion_domiciliaria_id = vdi.verificacion_domiciliaria_id " +
                "WHERE gi._id is not null " +
                "AND vdi.grupo_id = 1 " +
                ") as pivot ORDER BY nombre ";
        Cursor row = db.rawQuery(sql,  new String[]{});

        if(row.getCount() > 0)
        {
            row.moveToFirst();

            for(int i = 0; i < row.getCount(); i++)
            {
                nombres.add(row.getString(0));
                row.moveToNext();
            }
        }

        row.close();

        return nombres;
    }
}
