package com.sidert.sidertmovil.models.solicitudes.solicitudind.renovacion;

import static com.sidert.sidertmovil.utils.Constants.TBL_CLIENTE_IND;
import static com.sidert.sidertmovil.utils.Constants.TBL_CLIENTE_IND_REN;
import static com.sidert.sidertmovil.utils.Constants.TBL_DATOS_BENEFICIARIO;
import static com.sidert.sidertmovil.utils.Constants.TBL_DATOS_BENEFICIARIO_REN;
import static com.sidert.sidertmovil.utils.Constants.TBL_SOLICITUDES;
import static com.sidert.sidertmovil.utils.Constants.TBL_SOLICITUDES_REN;
import static com.sidert.sidertmovil.utils.Constants.TBL_TRACKER_ASESOR_T;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.sidert.sidertmovil.database.DBhelper;
import com.sidert.sidertmovil.models.BaseDao;
import com.sidert.sidertmovil.models.solicitudes.solicitudind.originacion.Beneficiario;

public class BeneficiarioIndRenDao extends BaseDao {

    private static DBhelper dBhelper;
    private static SQLiteDatabase db;

    public BeneficiarioIndRenDao(Context ctx){super(ctx);}

    public Beneficiario findByIdSolicitud(Integer idSolicitud){
        Beneficiario beneficiario = new Beneficiario();
        find(findByIdSolicitud(Beneficiario.TBL, idSolicitud), beneficiario);
        return beneficiario;
    }

    public static int obtenerSerieAsesor(Context ctx){

        dBhelper = DBhelper.getInstance(ctx);
        db = dBhelper.getWritableDatabase();

        int serie_id = 0;

        String sql = " SELECT serie_id " + " FROM " + TBL_TRACKER_ASESOR_T + " WHERE _id=1";

        Cursor row = db.rawQuery(sql,null);

        if(row.getCount()>0){
            row.moveToFirst();
            serie_id = row.getInt(0);
            row.close();
        }
        return serie_id;
    }

    public static int obtenerClienteIndRen(int id_solicitud,Context ctx){
        dBhelper = DBhelper.getInstance(ctx);
        db = dBhelper.getWritableDatabase();
        int id_cliente = 0;

        String sql = " SELECT id_cliente FROM " + TBL_CLIENTE_IND_REN + " WHERE id_solicitud=?";

        Cursor row = db.rawQuery( sql, new String[]{String.valueOf(id_solicitud)});

        if(row != null && row.getCount()>0){
            row.moveToFirst();
            id_cliente = row.getInt(0);
            row.close();
        }
        return id_cliente;
    }


    public static boolean validarBeneficiarioIndRen(int id_solicitud,Context ctx){
        dBhelper = DBhelper.getInstance(ctx);
        db = dBhelper.getWritableDatabase();
        boolean estatus = false;
        int dato = 0;

        String sql = " SELECT COUNT(*) FROM " + TBL_DATOS_BENEFICIARIO_REN + " WHERE id_solicitud=?";

        Cursor row = db.rawQuery(sql,new String[]{String.valueOf(id_solicitud)});

        if(row.getCount()>0){
            row.moveToFirst();
            dato = row.getInt(0);
            row.close();
        }

        if(dato >= 1){
            estatus = true;
        }
        return estatus;
    }

    public static int obtenerIdOriginacionRen(int id_solicitud, Context ctx){
        dBhelper = DBhelper.getInstance(ctx);
        db = dBhelper.getWritableDatabase();

        int id_originacion = 0;

        String sql = " SELECT id_originacion FROM " + TBL_SOLICITUDES_REN + " WHERE id_solicitud = ?";

        Cursor row = db.rawQuery(sql, new String[]{String.valueOf(id_solicitud)});

        if(row.getCount()>0){
            row.moveToFirst();
            id_originacion = row.getInt(0);
            row.close();
        }
        return id_originacion;
    }

    public Beneficiario getBeneficiario(int id_solicitud, int id_originacion, Context ctx){
        dBhelper = DBhelper.getInstance(ctx);
        db = dBhelper.getWritableDatabase();

        Beneficiario beneficiario = null;

        String sql = " SELECT * FROM " + TBL_DATOS_BENEFICIARIO_REN + " WHERE id_solicitud=? AND id_originacion=?";

        Cursor row = db.rawQuery(sql, new String[]{String.valueOf(id_solicitud),String.valueOf(id_originacion)});

        if(row.getCount()>0){
            row.moveToFirst();

            beneficiario = new Beneficiario();

            Fill(row, beneficiario);
        }

        row.close();

        return beneficiario;
    }

    private void Fill(Cursor row, Beneficiario beneficiario){
        beneficiario.setIdBeneficiario(row.getInt(0));
        beneficiario.setIdSolicitud(row.getInt(1));
        beneficiario.setId_originacion(row.getInt(2));
        beneficiario.setCliente_id(row.getInt(3));
        beneficiario.setGrupo_id(row.getInt(4));
        beneficiario.setNombre(row.getString(5));
        beneficiario.setPaterno(row.getString(6));
        beneficiario.setMaterno(row.getString(7));
        beneficiario.setParentesco(row.getString(8));
        beneficiario.setSerieId(row.getInt(9));

    }

}
