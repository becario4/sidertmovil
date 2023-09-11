package com.sidert.sidertmovil.models.solicitudes.solicitudgpo.renovacion;

import static com.sidert.sidertmovil.utils.Constants.TBL_CREDITO_GPO_REN;
import static com.sidert.sidertmovil.utils.Constants.TBL_DATOS_BENEFICIARIO;
import static com.sidert.sidertmovil.utils.Constants.TBL_DATOS_BENEFICIARIO_GPO;
import static com.sidert.sidertmovil.utils.Constants.TBL_DATOS_BENEFICIARIO_GPO_REN;
import static com.sidert.sidertmovil.utils.Constants.TBL_INTEGRANTES_GPO_REN;
import static com.sidert.sidertmovil.utils.Constants.TBL_TRACKER_ASESOR_T;
import static com.sidert.sidertmovil.utils.Constants.sidert;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.sidert.sidertmovil.database.DBhelper;
import com.sidert.sidertmovil.models.BaseDao;
import com.sidert.sidertmovil.models.MResConsultaCC;

public class BeneficiarioRenDao extends BaseDao {

    private static DBhelper dBhelper1;
    private static SQLiteDatabase db1;

    final DBhelper dbHelper;
    static SQLiteDatabase db;

    public BeneficiarioRenDao(Context ctx) {
        super(ctx);
        this.dbHelper = DBhelper.getInstance(ctx);
        db = dbHelper.getWritableDatabase();
    }

    public BeneficiarioRenGPO findByIdIntegrante(Integer id_integrante) {
        BeneficiarioRenGPO be = new BeneficiarioRenGPO();
        find(beneficiarioIntegrante(BeneficiarioRenGPO.TBL, id_integrante), be);
        return be;
    }

    public static int obtenerSerieAsesor(Context ctx) {

        dBhelper1 = DBhelper.getInstance(ctx);
        db1 = dBhelper1.getWritableDatabase();

        int serie_id = 0;

        String sql = " SELECT serie_id " + " FROM " + TBL_TRACKER_ASESOR_T + " WHERE _id=1";

        Cursor row = db1.rawQuery(sql, null);

        if (row.getCount() > 0) {
            row.moveToFirst();
            serie_id = row.getInt(0);
            row.close();
        }
        return serie_id;
    }

    public static int obtenerGrupoId(long id_solicitud, Context ctx) {

        dBhelper1 = DBhelper.getInstance(ctx);
        db1 = dBhelper1.getWritableDatabase();

        int grupo_id = 0;

        String sql = " SELECT grupo_id FROM " + TBL_CREDITO_GPO_REN + " WHERE id_solicitud=?";

        Cursor row = db1.rawQuery(sql, new String[]{String.valueOf(id_solicitud)});

        if (row != null && row.getCount() > 0) {
            row.moveToFirst();
            grupo_id = row.getInt(0);
            row.close();
        }
        return grupo_id;
    }

    public static int obtenerClienteId(int id_integrante, Context ctx) {
        dBhelper1 = DBhelper.getInstance(ctx);
        db1 = dBhelper1.getWritableDatabase();

        int id_cliente = 0;

        String sql = " SELECT cliente_id FROM " + TBL_INTEGRANTES_GPO_REN + " WHERE id=?";

        Cursor row = db1.rawQuery(sql, new String[]{String.valueOf(id_integrante)});

        if (row != null && row.getCount() > 0) {
            row.moveToFirst();
            id_cliente = row.getInt(0);
            row.close();
        }

        return id_cliente;
    }

    public static boolean validarBeneficiarioGPO(int id_integrante, Context ctx) {

        dBhelper1 = DBhelper.getInstance(ctx);
        db1 = dBhelper1.getWritableDatabase();

        boolean estatus = false;
        int dato = 0;

        String sql = " SELECT  COUNT(*) FROM " + TBL_DATOS_BENEFICIARIO_GPO_REN + " WHERE id_integrante = ?";

        Cursor row = db1.rawQuery(sql, new String[]{String.valueOf(id_integrante)});

        if (row.getCount() > 0) {
            row.moveToFirst();
            dato = row.getInt(0);
            row.close();
        }

        if (dato >= 1) {
            estatus = true;
        }

        return estatus;
    }

    public static int obtenerIdSolicitud(int cliente_id, Context ctx) {
        dBhelper1 = DBhelper.getInstance(ctx);
        db1 = dBhelper1.getWritableDatabase();

        int id_solicitud_integrante = 0;

        String sql = " SELECT id_solicitud_integrante FROM " + TBL_INTEGRANTES_GPO_REN + " WHERE cliente_id = ?";

        Cursor row = db1.rawQuery(sql, new String[]{String.valueOf(cliente_id)});

        if (row.getCount() > 0) {
            row.moveToFirst();
            id_solicitud_integrante = row.getInt(0);
            row.close();
        }
        return id_solicitud_integrante;
    }

}
