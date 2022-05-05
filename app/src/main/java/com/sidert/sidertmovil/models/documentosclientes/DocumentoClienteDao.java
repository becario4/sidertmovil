package com.sidert.sidertmovil.models.documentosclientes;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.sidert.sidertmovil.database.DBhelper;

import java.util.ArrayList;
import java.util.List;

import static com.sidert.sidertmovil.utils.Constants.TBL_AMORTIZACIONES_T;

public class DocumentoClienteDao {
    final DBhelper dBhelper;
    final SQLiteDatabase db;

    public DocumentoClienteDao(Context ctx)
    {
        this.dBhelper = new DBhelper(ctx);
        this.db = dBhelper.getWritableDatabase();
    }

    public long store(DocumentoCliente documentoCliente)
    {
        Long id;
        String sql;

        db.beginTransaction();

        sql = "INSERT INTO " + documentoCliente.TBL + " (" +
            documentoCliente.COL_GRUPO_ID +"," +
            documentoCliente.COL_CLIENTE_ID +"," +
            documentoCliente.COL_CLAVECLIENTE +"," +
            documentoCliente.COL_PRESTAMO_ID +"," +
            documentoCliente.COL_NUM_SOLICITUD +"," +
            documentoCliente.COL_FECHA +"," +
            documentoCliente.COL_TIPO +"," +
            documentoCliente.COL_ARCHIVO_BASE64 +
        ") VALUES (?, ?, ?, ?, ?, ?, ?, ?)"
        ;

        SQLiteStatement pInsert = db.compileStatement(sql);

        pInsert.bindLong(1, documentoCliente.getGrupoId());
        pInsert.bindLong(2, documentoCliente.getClienteId());
        pInsert.bindString(3, documentoCliente.getClavecliente());
        pInsert.bindLong(4, documentoCliente.getPrestamoId());
        pInsert.bindLong(5, documentoCliente.getNumSolicitud());
        pInsert.bindString(6, documentoCliente.getFecha());
        pInsert.bindString(7, documentoCliente.getTipo());
        pInsert.bindString(8, documentoCliente.getArchivoBase64());

        id = pInsert.executeInsert();

        db.setTransactionSuccessful();
        db.endTransaction();

        return id;
    }

    public DocumentoCliente findByPrestamoIdAndTipo(Integer prestamoId, String tipo)
    {
        String sql = "" +
                "SELECT * " +
                "FROM " + DocumentoCliente.TBL + " AS dc " +
                "WHERE dc." + DocumentoCliente.COL_PRESTAMO_ID + " = ? " +
                "AND dc." + DocumentoCliente.COL_TIPO + " = ? " +
                "ORDER BY dc." + DocumentoCliente.COL_FECHA + " " +
                "LIMIT 1"
                ;
        Cursor row = db.rawQuery(sql, new String[]{String.valueOf(prestamoId), tipo});

        return Find(row, sql);
    }

    public List<DocumentoCliente> findAllByClienteId(Integer clienteId)
    {
        String sql = "" +
            "SELECT * " +
            "FROM " + DocumentoCliente.TBL + " AS dc " +
            "WHERE dc." + DocumentoCliente.COL_CLIENTE_ID + " = ? " +
            "ORDER BY dc." + DocumentoCliente.COL_FECHA
        ;
        Cursor row = db.rawQuery(sql, new String[]{String.valueOf(clienteId)});

        return FindAll(row, sql);
    }

    public List<DocumentoCliente> findAllByPrestamoId(Integer prestamoId)
    {
        String sql = "" +
                "SELECT * " +
                "FROM " + DocumentoCliente.TBL + " AS dc " +
                "WHERE dc." + DocumentoCliente.COL_PRESTAMO_ID + " = ? " +
                " ORDER BY dc." + DocumentoCliente.COL_FECHA
                ;
        Cursor row = db.rawQuery(sql, new String[]{String.valueOf(prestamoId)});

        return FindAll(row, sql);
    }

    private DocumentoCliente Find(Cursor row, String query)
    {
        DocumentoCliente documentoCliente = null;

        if(row.getCount() > 0)
        {
            row.moveToFirst();

            documentoCliente = new DocumentoCliente();

            Fill(row, documentoCliente);
        }

        row.close();

        return documentoCliente;
    }

    private List<DocumentoCliente> FindAll(Cursor row, String query)
    {
        List<DocumentoCliente> documentosCliente = new ArrayList<>();

        if(row.getCount() > 0) {
            row.moveToFirst();

            for (int i = 0; i < row.getCount(); i++) {
                DocumentoCliente documentoCliente = new DocumentoCliente();

                Fill(row, documentoCliente);

                documentosCliente.add(documentoCliente);

                row.moveToNext();
            }
        }

        row.close();

        return documentosCliente;
    }

    private void Fill(Cursor row, DocumentoCliente documentoCliente)
    {
        documentoCliente.setId(row.getInt(row.getColumnIndex(documentoCliente.COL_ID)));
        documentoCliente.setGrupoId(row.getInt(row.getColumnIndex(documentoCliente.COL_GRUPO_ID)));
        documentoCliente.setClienteId(row.getInt(row.getColumnIndex(documentoCliente.COL_CLIENTE_ID)));
        documentoCliente.setClavecliente(row.getString(row.getColumnIndex(documentoCliente.COL_CLAVECLIENTE)));
        documentoCliente.setPrestamoId(row.getInt(row.getColumnIndex(documentoCliente.COL_PRESTAMO_ID)));
        documentoCliente.setNumSolicitud(row.getInt(row.getColumnIndex(documentoCliente.COL_NUM_SOLICITUD)));
        documentoCliente.setFecha(row.getString(row.getColumnIndex(documentoCliente.COL_FECHA)));
        documentoCliente.setTipo(row.getString(row.getColumnIndex(documentoCliente.COL_TIPO)));
        documentoCliente.setArchivoBase64(row.getString(row.getColumnIndex(documentoCliente.COL_ARCHIVO_BASE64)));
    }
}