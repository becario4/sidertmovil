package com.sidert.sidertmovil.models.solicitudes.solicitudgpo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.sidert.sidertmovil.database.DBhelper;

import static com.sidert.sidertmovil.utils.Constants.TBL_INTEGRANTES_GPO_REN;

public class IntegranteGpoRenDao {
    final DBhelper dbHelper;
    final SQLiteDatabase db;

    public IntegranteGpoRenDao(Context ctx){
        this.dbHelper = new DBhelper(ctx);
        this.db = dbHelper.getWritableDatabase();
    }

    public IntegranteGpoRen findByIdSolicitudIntegrante(Integer idSolicitudIntegrante)
    {
        IntegranteGpoRen integrante = null;

        String sql = "" +
            "SELECT " +
            "* " +
            "FROM " + TBL_INTEGRANTES_GPO_REN + " AS i " +
            "WHERE i.id_solicitud_integrante = ? AND i.estatus_completado in (2, 3)"
        ;

        Cursor row = db.rawQuery(sql, new String[]{String.valueOf(idSolicitudIntegrante)});

        if(row.getCount() > 0)
        {
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

    public void updateEstatus(IntegranteGpoRen integrante)
    {
        ContentValues cv = new ContentValues();

        //cv.put("estatus_completado", integrante.getEstatusCompletado());
        cv.put("comentario_rechazo", integrante.getComentarioRechazo());
        db.update(TBL_INTEGRANTES_GPO_REN, cv, "id = ?", new String[]{String.valueOf(integrante.getId())});
    }
}
