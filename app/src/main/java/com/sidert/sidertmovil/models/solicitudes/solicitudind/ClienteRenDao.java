package com.sidert.sidertmovil.models.solicitudes.solicitudind;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.sidert.sidertmovil.database.DBhelper;

import static com.sidert.sidertmovil.utils.Constants.TBL_CLIENTE_IND_REN;

public class ClienteRenDao {
    final DBhelper dbHelper;
    final SQLiteDatabase db;

    public ClienteRenDao(Context ctx){
        this.dbHelper = new DBhelper(ctx);
        this.db = dbHelper.getWritableDatabase();
    }

    public ClienteRen findById(Integer id)
    {
        ClienteRen cliente = null;

        String sql = "" +
                "SELECT " +
                "* " +
                "FROM " + TBL_CLIENTE_IND_REN + " AS c " +
                "WHERE c.id_cliente = ? "
                ;
        Cursor row = db.rawQuery(sql, new String[]{String.valueOf(id)});

        if(row.getCount() > 0)
        {
            row.moveToFirst();

            cliente = new ClienteRen();

            Fill(row, cliente);
        }

        row.close();

        return cliente;
    }

    public ClienteRen findByIdSolicitud(Integer idSolicitud)
    {
        ClienteRen cliente = null;

        String sql = "" +
                "SELECT " +
                "* " +
                "FROM " + TBL_CLIENTE_IND_REN + " AS c " +
                "WHERE c.id_solicitud = ? "
                ;
        Cursor row = db.rawQuery(sql, new String[]{String.valueOf(idSolicitud)});

        if(row.getCount() > 0)
        {
            row.moveToFirst();

            cliente = new ClienteRen();

            Fill(row, cliente);
        }

        row.close();

        return cliente;
    }

    public void updateEstatus(ClienteRen cliente)
    {
        ContentValues cv = new ContentValues();

        cv.put("comentario_rechazo", cliente.getComentarioRechazo());
        db.update(TBL_CLIENTE_IND_REN, cv, "id_cliente = ?", new String[]{String.valueOf(cliente.getIdCliente())});
    }

    private void Fill(Cursor row, ClienteRen cliente)
    {
        cliente.setIdCliente(row.getInt(0));
        cliente.setIdSolicitud(row.getInt(1));
        cliente.setNombre(row.getString(2));
        cliente.setPaterno(row.getString(3));
        cliente.setMaterno(row.getString(4));
        cliente.setFechaNacimiento(row.getString(5));
        cliente.setEdad(row.getString(6));
        cliente.setGenero(row.getInt(7));
        cliente.setEstadoNacimiento(row.getString(8));
        cliente.setRfc(row.getString(9));
        cliente.setCurp(row.getString(10));
        cliente.setCurpDigitoVeri(row.getString(11));
        cliente.setOcupacion(row.getString(12));
        cliente.setActividadEconomica(row.getString(13));
        cliente.setTipoIdentificacion(row.getString(14));
        cliente.setNoIdentificacion(row.getString(15));
        cliente.setNivelEstudio(row.getString(16));
        cliente.setEstadoCivil(row.getString(17));
        cliente.setBienes(row.getInt(18));
        cliente.setTipoVivienda(row.getString(19));
        cliente.setParentesco(row.getString(20));
        cliente.setOtroTipoVivienda(row.getString(21));
        cliente.setDireccionId(row.getString(22));
        cliente.setTelCasa(row.getString(23));
        cliente.setTelCelular(row.getString(24));
        cliente.setTelMensajes(row.getString(25));
        cliente.setTelTrabajo(row.getString(26));
        cliente.setTiempoVivirSitio(row.getInt(27));
        cliente.setDependientes(row.getString(28));
        cliente.setMedioContacto(row.getString(29));
        cliente.setEstadoCuenta(row.getString(30));
        cliente.setEmail(row.getString(31));
        cliente.setFotoFachada(row.getString(32));
        cliente.setRefDomiciliaria(row.getString(33));
        cliente.setFirma(row.getString(34));
        cliente.setEstatusRechazo(row.getInt(35));
        cliente.setComentarioRechazo(row.getString(36));
        cliente.setEstatusCompletado(row.getInt(37));
        cliente.setLatitud(row.getString(38));
        cliente.setLongitud(row.getString(39));
        cliente.setLocatedAt(row.getString(40));
    }
}
