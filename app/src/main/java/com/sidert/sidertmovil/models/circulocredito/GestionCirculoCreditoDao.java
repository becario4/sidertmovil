package com.sidert.sidertmovil.models.circulocredito;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.sidert.sidertmovil.database.DBhelper;

import java.util.ArrayList;
import java.util.List;

import static com.sidert.sidertmovil.utils.Constants.TBL_RECUPERACION_RECIBOS_CC;

public class GestionCirculoCreditoDao {
    final DBhelper dbHelper;
    final SQLiteDatabase db;

    public GestionCirculoCreditoDao(Context ctx){
        this.dbHelper = new DBhelper(ctx);
        this.db = dbHelper.getWritableDatabase();
    }

    public List<GestionCirculoCredito> show()
    {
        List<GestionCirculoCredito> gestionCirculoCreditos = new ArrayList<>();

        return gestionCirculoCreditos;
    }

    public long store(GestionCirculoCredito gestionCC)
    {
        Long id;
        String sql;
        SQLiteStatement pInsert;

        db.beginTransaction();

        sql = "INSERT INTO " + TBL_RECUPERACION_RECIBOS_CC + "( " +
                "tipo_credito," +
                "nombre_uno," +
                "curp," +
                "nombre_dos," +
                "integrantes," +
                "monto," +
                "medio_pago," +
                "imprimir_recibo," +
                "folio," +
                "evidencia," +
                "tipo_imagen," +
                "fecha_termino," +
                "fecha_envio," +
                "estatus," +
                "costo_consulta" +
                ") VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        pInsert = db.compileStatement(sql);

        pInsert.bindString(1, String.valueOf(gestionCC.getTipoCredito()));
        pInsert.bindString(2, gestionCC.getNombreUno());
        pInsert.bindString(3, gestionCC.getCurp());
        pInsert.bindString(4, gestionCC.getNombreDos());
        pInsert.bindString(5, String.valueOf(gestionCC.getIntegrantes()));
        pInsert.bindString(6, gestionCC.getMonto());
        pInsert.bindString(7, gestionCC.getMedioPago());
        pInsert.bindString(8, gestionCC.getImprimirRecibo());
        pInsert.bindString(9, String.valueOf(gestionCC.getFolio()));
        pInsert.bindString(10, gestionCC.getEvidencia());
        pInsert.bindString(11, gestionCC.getTipoImagen());
        pInsert.bindString(12, gestionCC.getFechaTermino());
        pInsert.bindString(13, gestionCC.getFechaEnvio());
        pInsert.bindString(14, String.valueOf(gestionCC.getEstatus()));
        pInsert.bindString(15, gestionCC.getCostoConsulta());

        id = pInsert.executeInsert();

        db.setTransactionSuccessful();
        db.endTransaction();

        return id;
    }

    public void update(int id, GestionCirculoCredito gestionCC)
    {
        ContentValues cv = new ContentValues();
        cv.put("evidencia", gestionCC.getMedioPago());
        cv.put("evidencia", gestionCC.getEvidencia());
        cv.put("tipo_imagen", gestionCC.getTipoImagen());
        cv.put("fecha_termino", gestionCC.getFechaTermino());
        cv.put("fecha_envio", gestionCC.getFechaEnvio());
        cv.put("folio", gestionCC.getFolio());
        cv.put("estatus", gestionCC.getEstatus());

        db.update(TBL_RECUPERACION_RECIBOS_CC, cv, "_id = ?", new String[]{String.valueOf(id)});
    }

    public GestionCirculoCredito findById(Integer id){
        GestionCirculoCredito gestionCC = null;

        String sql = "SELECT rc.* " +
                "FROM " + TBL_RECUPERACION_RECIBOS_CC + " rc " +
                "WHERE rc._id = ?"
                ;
        Cursor row = db.rawQuery(sql, new String[]{String.valueOf(id)});

        if(row.getCount() > 0)
        {
            row.moveToFirst();

            gestionCC = new GestionCirculoCredito();
            gestionCC.setId(row.getInt(0));
            gestionCC.setTipoCredito(row.getInt(1));
            gestionCC.setNombreUno(row.getString(2));
            gestionCC.setCurp(row.getString(3));
            gestionCC.setNombreDos(row.getString(4));
            gestionCC.setIntegrantes(row.getInt(5));
            gestionCC.setMonto(row.getString(6));
            gestionCC.setMedioPago(row.getString(7));
            gestionCC.setImprimirRecibo(row.getString(8));
            gestionCC.setFolio(row.getInt(9));
            gestionCC.setEvidencia(row.getString(10));
            gestionCC.setTipoImagen(row.getString(11));
            gestionCC.setFechaTermino(row.getString(12));
            gestionCC.setFechaEnvio(row.getString(13));
            gestionCC.setEstatus(row.getInt(14));
            gestionCC.setCostoConsulta(row.getString(15));
        }

        row.close();

        return gestionCC;
    }

    public GestionCirculoCredito findByCurp(String curp){
        GestionCirculoCredito gestionCC = null;

        String sql = "SELECT rc.* " +
            "FROM " + TBL_RECUPERACION_RECIBOS_CC + " rc " +
            "WHERE rc.curp = ? "
            //+ "and rc.curp <> 'LEFF911008HVZDRR09' "
        ;
        Cursor row = db.rawQuery(sql, new String[]{curp});

        if(row.getCount() > 0)
        {
            row.moveToFirst();

            gestionCC = new GestionCirculoCredito();
            gestionCC.setId(row.getInt(0));
            gestionCC.setTipoCredito(row.getInt(1));
            gestionCC.setNombreUno(row.getString(2));
            gestionCC.setCurp(row.getString(3));
            gestionCC.setNombreDos(row.getString(4));
            gestionCC.setIntegrantes(row.getInt(5));
            gestionCC.setMonto(row.getString(6));
            gestionCC.setMedioPago(row.getString(7));
            gestionCC.setImprimirRecibo(row.getString(8));
            gestionCC.setFolio(row.getInt(9));
            gestionCC.setEvidencia(row.getString(10));
            gestionCC.setTipoImagen(row.getString(11));
            gestionCC.setFechaTermino(row.getString(12));
            gestionCC.setFechaEnvio(row.getString(13));
            gestionCC.setEstatus(row.getInt(14));
            gestionCC.setCostoConsulta(row.getString(15));
        }

        row.close();

        return gestionCC;
    }

    public List<GestionCirculoCredito> findAllByEstatus(List<Integer> estatus)
    {
        List<GestionCirculoCredito> gestionesCirculoCredito = new ArrayList<>();

        String where = "WHERE rr.estatus IN (";

        for(int i = 0; i < estatus.size(); i++)
        {
            where = where + estatus.get(i);

            if(i < estatus.size() - 1) where = where + ", ";
        }

        String sql = "SELECT rr.* " +
                "FROM " + TBL_RECUPERACION_RECIBOS_CC + " AS rr " + where +
                ") ORDER BY rr.fecha_termino DESC ";
        Cursor row = db.rawQuery(sql, new String[]{});

        if(row.getCount() > 0)
        {
            row.moveToFirst();

            for(int i = 0; i < row.getCount(); i++)
            {
                GestionCirculoCredito gcc = new GestionCirculoCredito();
                gcc.setId(row.getInt(0));
                gcc.setTipoCredito(row.getInt(1));
                gcc.setNombreUno(row.getString(2));
                gcc.setCurp(row.getString(3));
                gcc.setNombreDos(row.getString(4));
                gcc.setIntegrantes(row.getInt(5));
                gcc.setMonto(row.getString(6));
                gcc.setMedioPago(row.getString(7));
                gcc.setImprimirRecibo(row.getString(8));
                gcc.setFolio(row.getInt(9));
                gcc.setEvidencia(row.getString(10));
                gcc.setTipoImagen(row.getString(11));
                gcc.setFechaTermino(row.getString(12));
                gcc.setFechaEnvio(row.getString(13));
                gcc.setEstatus(row.getInt(14));
                gcc.setCostoConsulta(row.getString(15));

                gestionesCirculoCredito.add(gcc);

                row.moveToNext();
            }
        }

        row.close();

        return gestionesCirculoCredito;
    }
}
