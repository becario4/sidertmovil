package com.sidert.sidertmovil.models.solicitudes.solicitudgpo.originacion;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.sidert.sidertmovil.database.DBhelper;

import java.util.ArrayList;
import java.util.List;

import static com.sidert.sidertmovil.utils.Constants.TBL_DATOS_BENEFICIARIO_GPO;
import static com.sidert.sidertmovil.utils.Constants.TBL_DATOS_BENEFICIARIO_GPO_REN;
import static com.sidert.sidertmovil.utils.Constants.TBL_DATOS_CREDITO_CAMPANA_GPO;
import static com.sidert.sidertmovil.utils.Constants.TBL_INTEGRANTES_GPO;

public class IntegranteGpoDao {
    final DBhelper dbHelper;
    final SQLiteDatabase db;

    public IntegranteGpoDao(Context ctx){
        this.dbHelper = DBhelper.getInstance(ctx);
        this.db = dbHelper.getWritableDatabase();
    }

    public IntegranteGpo findByIdSolicitudIntegrante(Integer idSolicitudIntegrante)
    {
        IntegranteGpo integrante = null;

        String sql = "" +
                "SELECT " +
                "* " +
                "FROM " + TBL_INTEGRANTES_GPO + " AS i " +
                "WHERE i.id_solicitud_integrante = ? AND i.id_solicitud_integrante > 0"
                ;

        Cursor row = db.rawQuery(sql, new String[]{String.valueOf(idSolicitudIntegrante)});

        if(row.getCount() > 0)
        {
            row.moveToFirst();

            integrante = new IntegranteGpo();

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
        }

        row.close();

        return integrante;
    }

    public List<IntegranteGpo> findAllByIdCredito(Integer idCredito)
    {
        List<IntegranteGpo> integranteGpos = new ArrayList<>();

        String sql = "" +
            "SELECT " +
            "* " +
            "FROM " + TBL_INTEGRANTES_GPO + " AS i " +
            "WHERE i.id_credito = ? "
        ;

        Cursor row = db.rawQuery(sql, new String[]{String.valueOf(idCredito)});

        if(row.getCount() > 0)
        {
            row.moveToFirst();

            for(int i = 0; i < row.getCount(); i++)
            {
                IntegranteGpo integrante = new IntegranteGpo();

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

                integranteGpos.add(integrante);
                row.moveToNext();
            }

        }

        row.close();

        return integranteGpos;
    }

    public void updateEstatus(IntegranteGpo integrante)
    {
        ContentValues cv = new ContentValues();
        //cv.put("estatus_completado", integrante.getEstatusCompletado());
        cv.put("comentario_rechazo", integrante.getComentarioRechazo());
        db.update(TBL_INTEGRANTES_GPO, cv, "id = ?", new String[]{String.valueOf(integrante.getId())});
    }

    public void saveEstatus(IntegranteGpo integrante)
    {
        ContentValues cv = new ContentValues();

        cv.put("estatus_completado", integrante.getEstatusCompletado());
        db.update(TBL_INTEGRANTES_GPO, cv, "id = ?", new String[]{String.valueOf(integrante.getId())});

    }

    public void setCompletado(IntegranteGpo integranteGpo, Integer idSolicitud, Integer id_grupo,Integer id_cliente)
    {
        ContentValues cv = new ContentValues();
        cv.put("id_solicitud_integrante", idSolicitud);
        cv.put("estatus_completado",2);
        db.update(TBL_INTEGRANTES_GPO, cv, "id = ?", new String[]{String.valueOf(integranteGpo.getId())});

        ContentValues cv1 = new ContentValues();
        cv1.put("id_solicitud_integrante", idSolicitud);
        cv1.put("id_grupo",id_grupo);
        cv1.put("id_cliente",id_cliente);
        db.update(TBL_DATOS_BENEFICIARIO_GPO,cv1,"id_integrante = ?", new String[]{String.valueOf(integranteGpo.getId())});

        ContentValues cv2 = new ContentValues();
        cv2.put("id_solicitud_integrante", idSolicitud);
        db.update(TBL_DATOS_BENEFICIARIO_GPO_REN,cv2,"id_integrante = ?", new String[]{String.valueOf(integranteGpo.getId())});

        ContentValues cv3 = new ContentValues();
        cv3.put("id_originacion",idSolicitud);
        db.update(TBL_DATOS_CREDITO_CAMPANA_GPO, cv3,"id_solicitud = ?", new String[]{String.valueOf(integranteGpo.getId())});







    }

}
