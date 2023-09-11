package com.sidert.sidertmovil.models.gestion.carteravencida;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.sidert.sidertmovil.database.DBhelper;

import static com.sidert.sidertmovil.utils.Constants.TBL_RESPUESTAS_IND_V_T;

public class GestionIndividualDao {
    final DBhelper dBhelper;
    final SQLiteDatabase db;

    public GestionIndividualDao (Context ctx) {
        this.dBhelper = DBhelper.getInstance(ctx);
        this.db = dBhelper.getWritableDatabase();
    }

    public GestionIndividual findByFolioAndIdPrestamo(String folio, String idPrestamo)
    {
        GestionIndividual gestionIndividual = null;

        String sql = "SELECT * FROM " + TBL_RESPUESTAS_IND_V_T + " WHERE folio = ? AND id_prestamo = ?";
        Cursor row = db.rawQuery(sql, new String[]{folio, idPrestamo});

        if(row.getCount() > 0)
        {
            row.moveToFirst();

            gestionIndividual = new GestionIndividual();

            gestionIndividual.setId(row.getInt(0));
            gestionIndividual.setIdPrestamo(row.getString(1));
            gestionIndividual.setLatitud(row.getString(2));
            gestionIndividual.setLongitud(row.getString(3));
            gestionIndividual.setContacto(row.getString(4));
            gestionIndividual.setComentario(row.getString(5));
            gestionIndividual.setActualizarTelefono(row.getString(6));
            gestionIndividual.setNuevoTelefono(row.getString(7));
            gestionIndividual.setResultadoGestion(row.getString(8));
            gestionIndividual.setMotivoNoPago(row.getString(9));
            gestionIndividual.setFechaFallecimiento(row.getString(10));
            gestionIndividual.setFechaMontoPromesa(row.getString(11));
            gestionIndividual.setMontoPromesa(row.getString(12));
            gestionIndividual.setMedioPago(row.getString(13));
            gestionIndividual.setFechaPago(row.getString(14));
            gestionIndividual.setPagaraRequerido(row.getString(15));
            gestionIndividual.setPagoRealizado(row.getString(16));
            gestionIndividual.setImprimirRecibo(row.getString(17));
            gestionIndividual.setFolio(row.getString(18));
            gestionIndividual.setEvidencia(row.getString(19));
            gestionIndividual.setTipoImagen(row.getString(20));
            gestionIndividual.setGerente(row.getString(21));
            gestionIndividual.setFirma(row.getString(22));
            gestionIndividual.setFechaInicio(row.getString(23));
            gestionIndividual.setFechaFin(row.getString(24));
            gestionIndividual.setFechaEnvio(row.getString(25));
            gestionIndividual.setEstatus(row.getString(26));
            gestionIndividual.setResImpresion(row.getString(27));
            gestionIndividual.setEstatusPago(row.getString(28));
            gestionIndividual.setSaldoCorte(row.getString(29));
            gestionIndividual.setSaldoActual(row.getString(30));
            gestionIndividual.setDiasAtraso(row.getString(31));
            gestionIndividual.setSerialId(row.getString(32));
        }

        row.close();

        return gestionIndividual;
    }


    public Long store(GestionIndividual gestionIndividual)
    {
        Long id;

        db.beginTransaction();

        String sql = "INSERT INTO " + TBL_RESPUESTAS_IND_V_T + " (" +
            "id_prestamo," +
            "latitud," +
            "longitud," +
            "contacto," +
            "comentario," +
            "actualizar_telefono," +
            "nuevo_telefono," +
            "resultado_gestion," +
            "motivo_no_pago," +
            "fecha_fallecimiento," +
            "fecha_monto_promesa," +
            "monto_promesa," +
            "medio_pago," +
            "fecha_pago," +
            "pagara_requerido," +
            "pago_realizado," +
            "imprimir_recibo," +
            "folio," +
            "evidencia," +
            "tipo_imagen," +
            "gerente," +
            "firma," +
            "fecha_inicio," +
            "fecha_fin," +
            "fecha_envio," +
            "estatus," +
            "res_impresion," +
            "estatus_pago," +
            "saldo_corte," +
            "saldo_actual," +
            "dias_atraso," +
            "serial_id" +
        ") " +
        "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"
        ;

        SQLiteStatement pInsert = db.compileStatement(sql);

        pInsert.bindString(1, gestionIndividual.getIdPrestamo());
        pInsert.bindString(2, gestionIndividual.getLatitud());
        pInsert.bindString(3, gestionIndividual.getLongitud());
        pInsert.bindString(4, gestionIndividual.getContacto());
        pInsert.bindString(5, gestionIndividual.getComentario());
        pInsert.bindString(6, gestionIndividual.getActualizarTelefono());
        pInsert.bindString(7, gestionIndividual.getNuevoTelefono());
        pInsert.bindString(8, gestionIndividual.getResultadoGestion());
        pInsert.bindString(9, gestionIndividual.getMotivoNoPago());
        pInsert.bindString(10, gestionIndividual.getFechaFallecimiento());
        pInsert.bindString(11, gestionIndividual.getFechaMontoPromesa());
        pInsert.bindString(12, gestionIndividual.getMontoPromesa());
        pInsert.bindString(13, gestionIndividual.getMedioPago());
        pInsert.bindString(14, gestionIndividual.getFechaPago());
        pInsert.bindString(15, gestionIndividual.getPagaraRequerido());
        pInsert.bindString(16, gestionIndividual.getPagoRealizado());
        pInsert.bindString(17, gestionIndividual.getImprimirRecibo());
        pInsert.bindString(18, gestionIndividual.getFolio());
        pInsert.bindString(19, gestionIndividual.getEvidencia());
        pInsert.bindString(20, gestionIndividual.getTipoImagen());
        pInsert.bindString(21, gestionIndividual.getGerente());
        pInsert.bindString(22, gestionIndividual.getFirma());
        pInsert.bindString(23, gestionIndividual.getFechaInicio());
        pInsert.bindString(24, gestionIndividual.getFechaFin());
        pInsert.bindString(25, gestionIndividual.getFechaEnvio());
        pInsert.bindString(26, gestionIndividual.getEstatus());
        pInsert.bindString(27, gestionIndividual.getResImpresion());
        pInsert.bindString(28, gestionIndividual.getEstatusPago());
        pInsert.bindString(29, gestionIndividual.getSaldoCorte());
        pInsert.bindString(30, gestionIndividual.getSaldoActual());
        pInsert.bindString(31, gestionIndividual.getDiasAtraso());
        pInsert.bindString(32, gestionIndividual.getSerialId());

        id = pInsert.executeInsert();

        db.setTransactionSuccessful();
        db.endTransaction();

        return id;
    }

}
