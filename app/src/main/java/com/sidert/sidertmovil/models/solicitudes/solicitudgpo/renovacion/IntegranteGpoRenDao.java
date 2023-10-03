package com.sidert.sidertmovil.models.solicitudes.solicitudgpo.renovacion;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.sidert.sidertmovil.database.DBhelper;

import java.util.ArrayList;
import java.util.List;

import static com.sidert.sidertmovil.utils.Constants.TBL_DATOS_BENEFICIARIO_GPO;
import static com.sidert.sidertmovil.utils.Constants.TBL_DATOS_CREDITO_CAMPANA_GPO_REN;
import static com.sidert.sidertmovil.utils.Constants.TBL_INTEGRANTES_GPO;
import static com.sidert.sidertmovil.utils.Constants.TBL_INTEGRANTES_GPO_REN;

public class IntegranteGpoRenDao {
    final DBhelper dbHelper;
    final SQLiteDatabase db;

    public IntegranteGpoRenDao(Context ctx) {
        this.dbHelper = DBhelper.getInstance(ctx);
        this.db = dbHelper.getWritableDatabase();
    }

    public IntegranteGpoRen findByIdSolicitudIntegrante(Integer idSolicitudIntegrante) {
        IntegranteGpoRen integrante = null;

        String sql = "" +
                "SELECT " +
                "* " +
                "FROM " + TBL_INTEGRANTES_GPO_REN + " AS i " +
                "WHERE i.id_solicitud_integrante = ? AND i.estatus_completado in (2, 3)";

        Cursor row = db.rawQuery(sql, new String[]{String.valueOf(idSolicitudIntegrante)});

        if (row.getCount() > 0) {
            row.moveToFirst();

            integrante = new IntegranteGpoRen();

            integrante.setId(row.getInt(0));
            integrante.setIdCredito(row.getInt(1));
            integrante.setCargo(row.getInt(2));
            integrante.setNombre(row.getString(3));
            integrante.setPaterno(row.getString(4));
            integrante.setMaterno(row.getString(5));
            integrante.setFechaNacimiento(row.getString(6));
            integrante.setEdad(row.getString(7));
            integrante.setGenero(row.getInt(8));
            integrante.setEstadoNacimiento(row.getString(9));
            integrante.setRfc(row.getString(10));
            integrante.setCurp(row.getString(11));
            integrante.setCurpDigitoVeri(row.getString(12));
            integrante.setTipoIdentificacion(row.getString(13));
            integrante.setNoIdentificacion(row.getString(14));
            integrante.setNivelEstudio(row.getString(15));
            integrante.setOcupacion(row.getString(16));
            integrante.setEstadoCivil(row.getString(17));
            integrante.setBienes(row.getInt(18));
            integrante.setEstatusRechazo(row.getInt(19));
            integrante.setComentarioRechazo(row.getString(20));
            integrante.setEstatusCompletado(row.getInt(21));
            integrante.setIdSolicitudIntegrante(row.getInt(22));
            integrante.setIsNuevo(row.getInt(23));
            integrante.setClienteId(row.getString(24));
            integrante.setCiclo(row.getInt(25));
            integrante.setMontoPrestamoAnterior(row.getString(26));
        }

        row.close();

        return integrante;
    }

    public List<IntegranteGpoRen> findByIdCredito(Integer idCredito) {
        List<IntegranteGpoRen> integrantes = new ArrayList<IntegranteGpoRen>();

        String sql = "" +
                "SELECT " +
                "* " +
                "FROM " + TBL_INTEGRANTES_GPO_REN + " AS i " +
                "WHERE i.id_credito = ? AND i.estatus_completado in (2, 3)";

        Cursor row = db.rawQuery(sql, new String[]{String.valueOf(idCredito)});

        if (row.getCount() > 0) {
            row.moveToFirst();

            for (int i = 0; i < row.getCount(); i++) {
                IntegranteGpoRen integrante = new IntegranteGpoRen();

                integrante.setId(row.getInt(0));
                integrante.setIdCredito(row.getInt(1));
                integrante.setCargo(row.getInt(2));
                integrante.setNombre(row.getString(3));
                integrante.setPaterno(row.getString(4));
                integrante.setMaterno(row.getString(5));
                integrante.setFechaNacimiento(row.getString(6));
                integrante.setEdad(row.getString(7));
                integrante.setGenero(row.getInt(8));
                integrante.setEstadoNacimiento(row.getString(9));
                integrante.setRfc(row.getString(10));
                integrante.setCurp(row.getString(11));
                integrante.setCurpDigitoVeri(row.getString(12));
                integrante.setTipoIdentificacion(row.getString(13));
                integrante.setNoIdentificacion(row.getString(14));
                integrante.setNivelEstudio(row.getString(15));
                integrante.setOcupacion(row.getString(16));
                integrante.setEstadoCivil(row.getString(17));
                integrante.setBienes(row.getInt(18));
                integrante.setEstatusRechazo(row.getInt(19));
                integrante.setComentarioRechazo(row.getString(20));
                integrante.setEstatusCompletado(row.getInt(21));
                integrante.setIdSolicitudIntegrante(row.getInt(22));
                integrante.setIsNuevo(row.getInt(23));
                integrante.setClienteId(row.getString(24));
                integrante.setCiclo(row.getInt(25));
                integrante.setMontoPrestamoAnterior(row.getString(26));

                integrantes.add(integrante);
                row.moveToNext();
            }

        }

        row.close();

        return integrantes;
    }

    public Integer countIntegrantesWihtStatusSuccessBycreditoId(Integer creditoId) {

        String query = "SELECT count(*) FROM " + TBL_INTEGRANTES_GPO_REN + " AS i0 WHERE i0.id_credito = ? AND i0.estatus_completado = 2";

        try (Cursor row = db.rawQuery(query, new String[]{creditoId.toString()})) {
            if (row.getCount() > 0) {
                return row.getInt(0);
            } else {
                return 0;
            }
        } catch (Exception ex) {
            return 0;
        }
    }

    public List<IntegranteGpoRen> findAllByIdCredito(Integer idCredito) {
        List<IntegranteGpoRen> integrantes = new ArrayList<IntegranteGpoRen>();

        String sql = "" +
                "SELECT " +
                "* " +
                "FROM " + TBL_INTEGRANTES_GPO_REN + " AS i " +
                "WHERE i.id_credito = ? " +
                "ORDER BY i.nombre, i.paterno, i.materno, i.id";

        Cursor row = db.rawQuery(sql, new String[]{String.valueOf(idCredito)});

        if (row.getCount() > 0) {
            row.moveToFirst();

            for (int i = 0; i < row.getCount(); i++) {
                IntegranteGpoRen integrante = new IntegranteGpoRen();

                integrante.setId(row.getInt(0));
                integrante.setIdCredito(row.getInt(1));
                integrante.setCargo(row.getInt(2));
                integrante.setNombre(row.getString(3));
                integrante.setPaterno(row.getString(4));
                integrante.setMaterno(row.getString(5));
                integrante.setFechaNacimiento(row.getString(6));
                integrante.setEdad(row.getString(7));
                integrante.setGenero(row.getInt(8));
                integrante.setEstadoNacimiento(row.getString(9));
                integrante.setRfc(row.getString(10));
                integrante.setCurp(row.getString(11));
                integrante.setCurpDigitoVeri(row.getString(12));
                integrante.setTipoIdentificacion(row.getString(13));
                integrante.setNoIdentificacion(row.getString(14));
                integrante.setNivelEstudio(row.getString(15));
                integrante.setOcupacion(row.getString(16));
                integrante.setEstadoCivil(row.getString(17));
                integrante.setBienes(row.getInt(18));
                integrante.setEstatusRechazo(row.getInt(19));
                integrante.setComentarioRechazo(row.getString(20));
                integrante.setEstatusCompletado(row.getInt(21));
                integrante.setIdSolicitudIntegrante(row.getInt(22));
                integrante.setIsNuevo(row.getInt(23));
                integrante.setClienteId(row.getString(24));
                integrante.setCiclo(row.getInt(25));
                integrante.setMontoPrestamoAnterior(row.getString(26));

                integrantes.add(integrante);
                row.moveToNext();
            }

        }

        row.close();

        return integrantes;
    }

    public void updateEstatus(IntegranteGpoRen integrante) {
        ContentValues cv = new ContentValues();

        //cv.put("estatus_completado", integrante.getEstatusCompletado());
        cv.put("comentario_rechazo", integrante.getComentarioRechazo());
        db.update(TBL_INTEGRANTES_GPO_REN, cv, "id = ?", new String[]{String.valueOf(integrante.getId())});
    }

    public void saveEstatus(IntegranteGpoRen integrante) {
        ContentValues cv = new ContentValues();

        cv.put("estatus_completado", integrante.getEstatusCompletado());
        db.update(TBL_INTEGRANTES_GPO_REN, cv, "id = ?", new String[]{String.valueOf(integrante.getId())});
    }

    public void setCompletado(IntegranteGpoRen integranteGpoRen, Integer idSolicitud) {
        ContentValues cv = new ContentValues();
        cv.put("id_solicitud_integrante", idSolicitud);
        cv.put("estatus_completado", 2);
        db.update(TBL_INTEGRANTES_GPO_REN, cv, "id = ?", new String[]{String.valueOf(integranteGpoRen.getId())});

        ContentValues cv1 = new ContentValues();
        cv1.put("id_solicitud_integrante", idSolicitud);
        db.update(TBL_DATOS_BENEFICIARIO_GPO, cv1, "id_integrante = ? ", new String[]{String.valueOf(integranteGpoRen.getId())});

        ContentValues cv2 = new ContentValues();
        cv2.put("id_originacion", idSolicitud);
        db.update(TBL_DATOS_CREDITO_CAMPANA_GPO_REN, cv2, "id_solicitud = ?", new String[]{String.valueOf(integranteGpoRen.getId())});

    }
}
